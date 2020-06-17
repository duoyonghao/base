package com.hudh.ykzz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.dao.YkzzDrugsInDetailDao;
import com.hudh.ykzz.dao.YkzzDrugsOutDao;
import com.hudh.ykzz.dao.YkzzDrugsOutDetailDao;
import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.entity.YkzzDrugsOut;
import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
import com.hudh.ykzz.service.IYkzzDrugsOutService;
import com.hudh.ykzz.service.IYkzzDrugsService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Service
public class YkzzDrugsOutServiceImpl implements IYkzzDrugsOutService {
	/**
	 * 入库
	 */
	@Autowired
	private YkzzDrugsOutDao ykzzDrugsOutDao;
	
	/**
	 * 出库明细
	 */
	@Autowired
	private YkzzDrugsOutDetailDao ykzzDrugsOutDetailDao;
	
	/**
	 * 商品dao
	 */
	@Autowired
	private IYkzzDrugsService ykzzDrugsService;
	
	@Autowired
	private YkzzDrugsInDetailDao ykzzDrugsInDetailDao;
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void insertDrugsOut(YkzzDrugsOut ykzzDrugsOut,String drugsOutdetails,HttpServletRequest request) throws Exception {
		//出库表数据
		String id = YZUtility.getUUID();
		ykzzDrugsOut.setId(id);
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		ykzzDrugsOut.setCreator(person.getSeqId());
		ykzzDrugsOut.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		ykzzDrugsOut.setStatus(0);
		ykzzDrugsOut.setOrganization(organization);
		//处理明细数据
		drugsOutdetails = java.net.URLDecoder.decode(drugsOutdetails, "UTF-8");
		List<YkzzDrugsOutDetail> drugsOutDetailList = HUDHUtil.parseJsonToObjectList(drugsOutdetails, YkzzDrugsOutDetail.class);
		//处理对应的每条药品数据
		for(YkzzDrugsOutDetail ykzzDrugsOutDetail : drugsOutDetailList) {
			String batchnumToDrugsNum = ykzzDrugsOutDetail.getBatchToNumber();
			//批号对应的药品数量
			Integer number = Integer.valueOf(batchnumToDrugsNum);
			if ( ykzzDrugsOutDetail.getCkQuantity() > number ) {
				throw new Exception("出库数量大于入库批号对应的数量，请从新填写出库数量！");
			}
			//批号的id
			String Id = ykzzDrugsOutDetail.getBatchToId();
//			String inDetail = Id.substring(3);
			String inDetail = Id.substring(3, 39);
			//出库数量
			Integer ckNum = ykzzDrugsOutDetail.getCkQuantity();
			YkzzDrugsInDetail ykzzDrugsIn = ykzzDrugsInDetailDao.findYkzzDrugsInDatailByInDetail(inDetail);
			//入库明细表批号数量
			Integer batchnoNumber = ykzzDrugsIn.getBatchnoNum();
			int num = batchnoNumber - ckNum;
//			ykzzDrugsIn.setId(Id.substring(3));
			ykzzDrugsIn.setBatchnoNum(num);
			ykzzDrugsInDetailDao.updateDrugsInDetail(ykzzDrugsIn);
			
			ykzzDrugsOutDetail.setId(YZUtility.getUUID());
			ykzzDrugsOutDetail.setParentid(id);
			ykzzDrugsOutDetail.setStatus(0);
			ykzzDrugsOutDetail.setCreatetime(ykzzDrugsOut.getCreatetime());
			ykzzDrugsOutDetail.setOrganization(organization);
		}
		
		//处理库存单价库存数量库存总价更新到商品表中
		//查询出所有药品的库存信息
		List<YkzzDrugs> drugsList = ykzzDrugsService.selectDrugsOutByIdStr(drugsOutDetailList);
		Map<String,YkzzDrugs> drugsMap = new HashMap<String,YkzzDrugs>();
		//将药品集合转成Map
		for(YkzzDrugs ykzzDrugs : drugsList) {
			drugsMap.put(ykzzDrugs.getId(), ykzzDrugs);
		}
		YkzzDrugs drugsTemp = null;
		int num = 0;
		BigDecimal price;
		for(YkzzDrugsOutDetail ykzzDrugsOutDetail : drugsOutDetailList) {
			drugsTemp = drugsMap.get(ykzzDrugsOutDetail.getDrugsId());
			num = drugsTemp.getDrug_total_num() - ykzzDrugsOutDetail.getCkQuantity();
			//总价-出库金额
			price = drugsTemp.getDrgs_total_money().subtract(ykzzDrugsOutDetail.getAmount());
			if(num < 0) {
				throw new Exception("库存不足");
			}
			if(drugsTemp.getDrgs_total_money().compareTo(ykzzDrugsOutDetail.getAmount()) < 0) {
				throw new Exception("库存金额不足");
			}
			if(num==0){
				drugsTemp.setNuit_price(new BigDecimal(0.000));
			}else{
				drugsTemp.setNuit_price(price.divide(new BigDecimal(num),3, BigDecimal.ROUND_HALF_UP));
			}
			drugsTemp.setDrug_total_num(num);
			drugsTemp.setDrgs_total_money(price);
			drugsMap.put(ykzzDrugsOutDetail.getDrugsId(),drugsTemp);
		}
		List<YkzzDrugs>	tempList = new ArrayList<YkzzDrugs>();
		for(Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet()) {
			tempList.add(entry.getValue());
		}
		//保存数据
		//添加出库信息
		ykzzDrugsOutDao.insertDrugsOut(ykzzDrugsOut);
		//添加出库明细
		ykzzDrugsOutDetailDao.batchSaveOutDetail(drugsOutDetailList);
		
		ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public String deleteDrugsOut(String id) throws Exception {
		String backMsg = "";
		//获取入库表数据
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("id", id);
		List<JSONObject> drugsInObj = ykzzDrugsOutDao.findAllDrugsOut(dataMap);
		if(null == drugsInObj || drugsInObj.size() <= 0) {
			backMsg = "出库单不存在";
			return backMsg;
		}
		
		//获取明细数据
		List<JSONObject> drugsOutDetailObj = ykzzDrugsOutDetailDao.findDetailByParendId(drugsInObj.get(0).getString("id"));
		List<YkzzDrugsOutDetail> drugsOutDetailList = HUDHUtil.parseJsonToObjectList(JSON.toJSONString(drugsOutDetailObj), YkzzDrugsOutDetail.class);
		//获取药品信息
		List<YkzzDrugs> drugsList = ykzzDrugsService.selectDrugsOutByIdStr(drugsOutDetailList);
		//将药品集合转成Map
		Map<String,YkzzDrugs> drugsMap = new HashMap<String,YkzzDrugs>();
		for(YkzzDrugs drug : drugsList) {
			drugsMap.put(drug.getId(), drug);
		}
		
		//处理每个商品的数据
		YkzzDrugs drugsTemp = null;
		for(YkzzDrugsOutDetail ykzzDrugsOutDetail : drugsOutDetailList){
			drugsTemp = drugsMap.get(ykzzDrugsOutDetail.getDrugsId());
			drugsTemp.setDrug_total_num(drugsTemp.getDrug_total_num() + ykzzDrugsOutDetail.getCkQuantity());
			drugsTemp.setDrgs_total_money( drugsTemp.getDrgs_total_money().add(ykzzDrugsOutDetail.getAmount()));
			
			drugsMap.put(ykzzDrugsOutDetail.getDrugsId(),drugsTemp);
		}
		List<YkzzDrugs>	tempList = new ArrayList<YkzzDrugs>();
		for(Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet()) {
			tempList.add(entry.getValue());
		}
		
		//注销入库表和明细表数据及更新药品表
		deleteDrugsOutById(id);
		deleteDrugsOutDetailByParendId(id);
		ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
		return "操作成功";
	}
	
	@Override
	public List<JSONObject> findAllDrugsOut(Map<String, String> dataMap) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsOutDao.findAllDrugsOut(dataMap);
		return list;
	}

	@Override
	public List<JSONObject> findDetailByParendId(String parentid) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsOutDetailDao.findDetailByParendId(parentid);
		return list;
	}

	@Override
	public void deleteDrugsOutById(String id) throws Exception {
		ykzzDrugsOutDao.deleteDrugsOut(id);
		
	}

	@Override
	public void deleteDrugsOutDetailByParendId(String id) throws Exception {
		ykzzDrugsOutDetailDao.deleteDrugsOut(id);
	}

	@Override
	public String getDrugsInBatchnum(String order_no, String outnum) throws Exception {
		// TODO Auto-generated method stub
		String batchno = "";
		Integer outNum = Integer.valueOf(outnum);//本次出库药品数量
		List<YkzzDrugsInDetail> drugsInDetailList = ykzzDrugsInDetailDao.findDrugsInDetailByOrderno(order_no);
		int totalInValue = drugsInDetailList.stream().mapToInt(YkzzDrugsInDetail::getQuantity).sum();
		List<YkzzDrugsOutDetail> drugsOutDetailList = ykzzDrugsOutDetailDao.findDrugsOutDetailByOrderno(order_no);
		int totalOutValue = drugsOutDetailList.stream().mapToInt(YkzzDrugsOutDetail::getCkQuantity).sum();
		if ( drugsOutDetailList.size() == 0 || drugsOutDetailList.size() < 0 ) {
			int firstNum = drugsInDetailList.get(0).getQuantity();//同种药品首次入库库存数量
			if ( outNum < firstNum ) {
				batchno = drugsInDetailList.get(0).getBatchnum();
			} else {
				for (int i = 0; i < drugsInDetailList.size(); i++) {
					int inDrugsNum = drugsInDetailList.get(i).getQuantity();
					String batchnum = drugsInDetailList.get(i).getBatchnum();
					batchno = batchnum + "," + batchno;
				}
			}
		} else {
//			Integer firstNum = 0;
			for (int j = 0; j < drugsInDetailList.size(); j++) {
				Integer firstNum = drugsInDetailList.get(0).getQuantity();//同种药品首次入库库存数量
				int secondNum = drugsInDetailList.get(j).getQuantity();
//				int thirdNum = drugsInDetailList.get(2).getQuantity();
				if ( firstNum > totalOutValue ) {
					batchno = drugsInDetailList.get(0).getBatchnum();
				} else {
					String batchnum = drugsInDetailList.get(j).getBatchnum();
					batchno = batchnum + "," + batchno;
				}
			}
			int num = totalInValue - totalOutValue - outNum;
			if ( num < 0 ) {
				throw new Exception(drugsInDetailList.get(0).getChemistryName() + "入库库存不足！");
			}
		}
		return batchno;
	}
	/**
	 * 2019-08-23 lwg
	 * 查找批号药品当天的出库数量
	 */
	@Override
	public String findOutNumByOrderno(String orderno,String batchnum) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		String list=null;
		if(orderno!=null&&orderno!=""&&batchnum!=null&&batchnum!=""){
			map.put("orderno", orderno);
			map.put("batchnum", batchnum);
			String date = YZUtility.getDateStr(new Date());
			map.put("starttime", date + ConstUtil.TIME_START);
			map.put("endtime",date +  ConstUtil.TIME_END);
			list = ykzzDrugsOutDetailDao.findDrugsOutDetailByOrdernoAndBatchnum(map);
		}
		return list;
	}
	/**
	 * 2019-08-23 lwg
	 * 查找药品当天的总出库数量
	 */
	@Override
	public String findOutNumsByAll() throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		String date = YZUtility.getDateStr(new Date());
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime",date +  ConstUtil.TIME_END);
		String i = ykzzDrugsOutDetailDao.findOutNumsByAll(map);
		return i;
	}
	
	/**
	 * 2019-08-23 lwg
	 * 查找批号药品当天的出库数量
	 */
	@Override
	public String findBatchnumSaveOutNumsByOrdernoAndBatchnum(String orderno,String batchnum) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		String list=null;
		if(orderno!=null&&orderno!=""&&batchnum!=null&&batchnum!=""){
			map.put("orderno", orderno);
			map.put("batchnum", batchnum);
			String date = YZUtility.getDateStr(new Date());
			map.put("starttime", date + ConstUtil.TIME_START);
			map.put("endtime",date +  ConstUtil.TIME_END);
			list = ykzzDrugsOutDetailDao.findBatchnumSaveOutNumsByOrdernoAndBatchnum(map);
		}
		return list;
	}
	/**
	 * 2019-08-23 lwg
	 * 查找药品当天的总出库数量
	 */
	@Override
	public String findOutNumsByBatchnumSave() throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		String date = YZUtility.getDateStr(new Date());
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime",date +  ConstUtil.TIME_END);
		String i = ykzzDrugsOutDetailDao.findOutNumsByBatchnumSave(map);
		return i;
	}
	
}
