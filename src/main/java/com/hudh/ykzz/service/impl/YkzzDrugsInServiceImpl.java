package com.hudh.ykzz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.hudh.lclj.StaticVar;
import com.hudh.lclj.dao.SysParaDao;
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.dao.YkzzDrugsBatchnumSaveDao;
import com.hudh.ykzz.dao.YkzzDrugsInDao;
import com.hudh.ykzz.dao.YkzzDrugsInDetailDao;
import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsBatchnumSave;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.service.IYkzzDrugsInService;
import com.hudh.ykzz.service.IYkzzDrugsService;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Service
public class YkzzDrugsInServiceImpl implements IYkzzDrugsInService {
	/**
	 * 入库
	 */
	@Autowired
	private YkzzDrugsInDao ykzzDrugsInDao;
	
	/**
	 * 入库明细
	 */
	@Autowired
	private YkzzDrugsInDetailDao ykzzDrugsInDetailDao;
	
	/**
	 * 商品dao
	 */
	@Autowired
	private IYkzzDrugsService ykzzDrugsService;
	
	/**
	 * 配置信息Dao
	 */
	@Autowired
	private SysParaDao sysParaDao;
	
	@Autowired
	private YkzzDrugsBatchnumSaveDao batchnumDao;
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void insertDrugsIn(YkzzDrugsIn ykzzDrugsIn,String drugsIndetails,HttpServletRequest request) throws Exception {
		//入库表数据
		String id = YZUtility.getUUID();
		ykzzDrugsIn.setId(id);
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		ykzzDrugsIn.setCreator(person.getSeqId());
		ykzzDrugsIn.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		ykzzDrugsIn.setStatus(0);
		ykzzDrugsIn.setCheckStatus(0);
		ykzzDrugsIn.setOrganization(organization);
		
		//处理明细数据
		drugsIndetails = java.net.URLDecoder.decode(drugsIndetails, "UTF-8");
		List<YkzzDrugsInDetail> drugsInDetailList = HUDHUtil.parseJsonToObjectList(drugsIndetails, YkzzDrugsInDetail.class);
		for(YkzzDrugsInDetail ykzzDrugsInDetail : drugsInDetailList) {
			ykzzDrugsInDetail.setId(YZUtility.getUUID());
			ykzzDrugsInDetail.setParentid(id);
			ykzzDrugsInDetail.setStatus(0);
			ykzzDrugsInDetail.setCreatetime(ykzzDrugsIn.getCreatetime());
			ykzzDrugsInDetail.setBatchnoNum(ykzzDrugsInDetail.getQuantity());
			ykzzDrugsInDetail.setOrganization(organization);
		}
		
		/*//处理库存单价库存数量库存总价更新到商品表中
		List<YkzzDrugs> drugsList = ykzzDrugsService.selectDrugsByIdStr(drugsInDetailList);
		Map<String,YkzzDrugs> drugsMap = new HashMap<String,YkzzDrugs>();
		//将药品集合转成Map
		for(YkzzDrugs ykzzDrugs : drugsList) {
			drugsMap.put(ykzzDrugs.getId(), ykzzDrugs);
		}
		YkzzDrugs drugsTemp = null;
		for(YkzzDrugsInDetail ykzzDrugsInDetail : drugsInDetailList) {
			drugsTemp = drugsMap.get(ykzzDrugsInDetail.getDrugsId());
			drugsTemp.setDrug_total_num(drugsTemp.getDrug_total_num() + ykzzDrugsInDetail.getQuantity());
			drugsTemp.setDrgs_total_money(drugsTemp.getDrgs_total_money().add(ykzzDrugsInDetail.getAmount()));
			drugsTemp.setNuit_price(drugsTemp.getDrgs_total_money().divide(
					new BigDecimal(drugsTemp.getDrug_total_num().toString()),2,BigDecimal.ROUND_UP));
			drugsMap.put(ykzzDrugsInDetail.getDrugsId(),drugsTemp);
		}
			
		List<YkzzDrugs>	tempList = new ArrayList<YkzzDrugs>();
		for(Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet()) {
			tempList.add(entry.getValue());
		}*/
		//保存数据
		ykzzDrugsInDao.insertDrugsIn(ykzzDrugsIn);
		ykzzDrugsInDetailDao.batchSaveInDetail(drugsInDetailList);
		/*ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);*/
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void drugsAddInStock(String drugsInId) throws Exception{
		if(YZUtility.isNullorEmpty(drugsInId)) {
			throw new Exception("入库单不存在");
		}
		List<JSONObject> drugsIndetails = ykzzDrugsInDetailDao.findDetailByParendId(drugsInId);
		List<YkzzDrugsInDetail> drugsInDetailList = HUDHUtil.parseJsonToObjectList(JSON.toJSONString(drugsIndetails), YkzzDrugsInDetail.class);
		//处理库存单价库存数量库存总价更新到商品表中
		List<YkzzDrugs> drugsList = ykzzDrugsService.selectDrugsByIdStr(drugsInDetailList);
		Map<String,YkzzDrugs> drugsMap = new HashMap<String,YkzzDrugs>();
		//将药品集合转成Map
		for(YkzzDrugs ykzzDrugs : drugsList) {
			drugsMap.put(ykzzDrugs.getId(), ykzzDrugs);
		}
		YkzzDrugs drugsTemp = null;
		for(YkzzDrugsInDetail ykzzDrugsInDetail : drugsInDetailList) {
			drugsTemp = drugsMap.get(ykzzDrugsInDetail.getDrugsId());
			drugsTemp.setDrug_total_num(drugsTemp.getDrug_total_num() + ykzzDrugsInDetail.getQuantity());
			drugsTemp.setDrgs_total_money(drugsTemp.getDrgs_total_money().add(ykzzDrugsInDetail.getAmount()));
			drugsTemp.setNuit_price(drugsTemp.getDrgs_total_money().divide(new BigDecimal(drugsTemp.getDrug_total_num().toString()),3,BigDecimal.ROUND_UP));
			drugsMap.put(ykzzDrugsInDetail.getDrugsId(),drugsTemp);
		}
			
		List<YkzzDrugs>	tempList = new ArrayList<YkzzDrugs>();
		for(Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet()) {
			tempList.add(entry.getValue());
		}
		ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public String deleteDrugsIn(String id) throws Exception {
		String backMsg = "";
		//获取入库表数据
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("id", id);
		List<JSONObject> drugsInObj = ykzzDrugsInDao.findAllDrugsIn(dataMap);
		if(null == drugsInObj || drugsInObj.size() <= 0) {
			backMsg = "入库单不存在";
			return backMsg;
		}
		
		//获取明细数据
		List<JSONObject> drugsInDetailObj = ykzzDrugsInDetailDao.findDetailByParendId(drugsInObj.get(0).getString("id"));
		List<YkzzDrugsInDetail> drugsInDetailList = HUDHUtil.parseJsonToObjectList(JSON.toJSONString(drugsInDetailObj), YkzzDrugsInDetail.class);
		//获取药品信息
		List<YkzzDrugs> drugsList = ykzzDrugsService.selectDrugsByIdStr(drugsInDetailList);
		//将药品集合转成Map
		Map<String,YkzzDrugs> drugsMap = new HashMap<String,YkzzDrugs>();
		for(YkzzDrugs drug : drugsList) {
			drugsMap.put(drug.getId(), drug);
		}
		
		//处理每个商品的数据
		YkzzDrugs drugsTemp = null;
		for(YkzzDrugsInDetail ykzzDrugsInDetail : drugsInDetailList){
			drugsTemp = drugsMap.get(ykzzDrugsInDetail.getDrugsId());
			int drugTotalNum = drugsTemp.getDrug_total_num();
			BigDecimal drugTotalMoney = drugsTemp.getDrgs_total_money();
			
			//数量
			drugTotalNum = drugTotalNum - ykzzDrugsInDetail.getQuantity();
			if(drugTotalNum < 0) {
				backMsg = drugsTemp.getChemistry_name() + "库存数量不足";
				return backMsg;
			}
			drugsTemp.setDrug_total_num(drugTotalNum);
			
			//库存总价
			if(drugTotalMoney.compareTo(ykzzDrugsInDetail.getAmount()) < 0) {
				backMsg = drugsTemp.getChemistry_name() + "库存金额不足";
				return backMsg;
			}
			drugsTemp.setDrgs_total_money(drugTotalMoney.subtract(ykzzDrugsInDetail.getAmount()));
			drugsTemp.setNuit_price(drugsTemp.getDrgs_total_money().divide(
					new BigDecimal(drugsTemp.getDrug_total_num().toString()),3,BigDecimal.ROUND_UP));
			drugsMap.put(ykzzDrugsInDetail.getDrugsId(),drugsTemp);
		}
		List<YkzzDrugs>	tempList = new ArrayList<YkzzDrugs>();
		for(Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet()) {
			tempList.add(entry.getValue());
		}
		
		//注销入库表和明细表数据及更新药品表
		deleteDrugsInById(id);
		deleteDrugsInDetailByParendId(id);
		ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
		backMsg = "操作成功";
		return backMsg;
	}
	
	@Override
	public List<JSONObject> findAllDrugsIn(Map<String, String> dataMap) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsInDao.findAllDrugsIn(dataMap);
		return list;
	}

	@Override
	public List<JSONObject> findDetailByParendId(String parentid) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsInDetailDao.findDetailByParendId(parentid);
		return list;
	}
	
	@Override
	public String findByParendId(String parentid) throws Exception {
		
		String list = ykzzDrugsInDao.findByParendId(parentid);
		return list;
	}
	@Override
	public void deleteDrugsInById(String id) throws Exception {
		ykzzDrugsInDao.deleteDrugsIn(id);
		
	}

	@Override
	public void deleteDrugsInDetailByParendId(String id) throws Exception {
		ykzzDrugsInDetailDao.deleteDrugsIn(id);
	}

	@Override
	public List<JSONObject> findAllCostOrder(Map<String, Object> dataMap) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsInDao.findAllCostOrder(dataMap);
		return list;
	}
	
	@Override
	public List<JSONObject> findCostOrderDetailByCostno(String costno) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsInDao.findCostOrderDetailByCostno(costno);
		return list;
	}
	@Override
	public KqdsCostorderDetail findCostOrderDetailBySeqid(String seqId) throws Exception {
		KqdsCostorderDetail kqdsCostorderDetail= ykzzDrugsInDao.findCostOrderDetailBySeqid(seqId);
		return kqdsCostorderDetail;
	}
	@Override
	public List<JSONObject> findCostOrderDetailReturnBySeqid(String seqid) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsInDao.findCostOrderDetailReturnBySeqid(seqid);
		return list;
	}
	/**
	 * 发药
	 */
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void grantDrugs(String organization,String costno,String[] batchnoNum,String[] seqId,String[] costseqIdArr,HttpServletRequest request) throws Exception {
		if(costno!=null&&!costno.equals("")&&!costno.equals(null)){
			List<YkzzDrugsBatchnumSave> list = new ArrayList<YkzzDrugsBatchnumSave>();
			//获取费用单号下明细
			List<String> drugsOrderNoList = new ArrayList<String>();
			YZPerson person = SessionUtil.getLoginPerson(request);
			for (int i = 0; i < costseqIdArr.length; i++) {
				//seqId查询出对应详情
				//所有的药品的
				//查询费用明细表明细
				KqdsCostorderDetail kqdsCostorderDetail=findCostOrderDetailBySeqid(costseqIdArr[i]);
				//查找有无退款减去退费的数量
				//查询所有退款
				KqdsCostorderDetail kqdsCostorderDetail2 = ykzzDrugsInDao.findCostOrderDetailByParentid(costseqIdArr[i]);
				if(kqdsCostorderDetail2!=null){
					int nums=Integer.parseInt(kqdsCostorderDetail.getNum())+Integer.parseInt(kqdsCostorderDetail2.getNum());
					kqdsCostorderDetail.setNum(String.valueOf(nums));
				}else{
					String qfbh=kqdsCostorderDetail.getQfbh();
					if(qfbh!=null&&!qfbh.equals("")){
						KqdsCostorderDetail kqdsCostorderDetail1 = ykzzDrugsInDao.findCostOrderDetailSubtotalByQfbh(qfbh);
						if(kqdsCostorderDetail1!=null){
							int nums=Integer.parseInt(kqdsCostorderDetail.getNum())+Integer.parseInt(kqdsCostorderDetail1.getNum());
							kqdsCostorderDetail.setNum(String.valueOf(nums));
						}
					}
				}
				YkzzDrugsInDetail drugsInDetail = ykzzDrugsInDetailDao.findYkzzDrugsInDatailById(seqId[i]);
				YkzzDrugsBatchnumSave dp = new YkzzDrugsBatchnumSave();
				//费用明细表id
				dp.setCostOrderDetailId(costseqIdArr[i]);
				//id
				dp.setId(costseqIdArr[i]);
				//药品名称
				dp.setDrugsname(kqdsCostorderDetail.getItemname());
				//药品编号
				dp.setDrugsno(kqdsCostorderDetail.getItemno());
				//库存数量
				Integer batchno = drugsInDetail.getBatchnoNum();
				dp.setBatchnum(batchno+"");
				//发药数量
				dp.setNumber(kqdsCostorderDetail.getNum());
				//批号
				dp.setBatchno(drugsInDetail.getBatchnum());
				dp.setOrganization(organization);
				dp.setCreatename(person.getUserName());
				dp.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
				list.add(dp);
				Object ckNum = kqdsCostorderDetail.getNum();
				int cknum = Integer.parseInt(String.valueOf(ckNum));
				int num = batchno - cknum;
				drugsInDetail.setBatchnoNum(num);
				//发药的同时更新入库明细批号的数量，修改
				ykzzDrugsInDetailDao.updateDrugsInDetail(drugsInDetail);			
				drugsOrderNoList.add(kqdsCostorderDetail.getItemno());
				
			}
			//仓库查询药品明细
			List<YkzzDrugs> drugsList = ykzzDrugsService.selectDrugsByOrderNoStr(drugsOrderNoList);
			//将药品集合转成Map
			Map<String,YkzzDrugs> drugsMap = new HashMap<String,YkzzDrugs>();
			for(YkzzDrugs drug : drugsList) {
				drugsMap.put(drug.getOrder_no(), drug);
			}
			//处理每个商品的数据
			YkzzDrugs drugsTemp = null;
			for (int i = 0; i < costseqIdArr.length; i++) {
				KqdsCostorderDetail kqdsCostorderDetail=findCostOrderDetailBySeqid(costseqIdArr[i]);
				drugsTemp = drugsMap.get(kqdsCostorderDetail.getItemno());
				if((drugsTemp.getDrug_total_num() - Integer.parseInt(kqdsCostorderDetail.getNum())) < 0 ){
					throw new Exception(drugsTemp.getChemistry_name() + "库存不足");
				}
				//总价等于原价减去批号总价
				//库存总价
				//查询出仓库对应的所有的批号信息计算总价和单价
				BigDecimal zj=null;
				BigDecimal dj=new BigDecimal(0.000);
				String order_no=drugsTemp.getOrder_no();
				List<YkzzDrugsInDetail> lt = ykzzDrugsInDetailDao.findBatchnumByOrderno(order_no);
				if(lt.size()>0){
					for (YkzzDrugsInDetail ykzzDrugsInDetail1 : lt) {
						BigDecimal phzj=(new BigDecimal(ykzzDrugsInDetail1.getBatchnoNum())).multiply(ykzzDrugsInDetail1.getNuitPrice()).setScale(3,BigDecimal.ROUND_HALF_UP);
						if(zj==null){
							zj=phzj;
						}else{
							zj=zj.add(phzj);							
						}
					}
					dj=zj.divide(new BigDecimal(drugsTemp.getDrug_total_num()-Integer.parseInt(kqdsCostorderDetail.getNum())),BigDecimal.ROUND_HALF_UP);
				}else{
					zj=new BigDecimal(0);
				}
				drugsTemp.setDrgs_total_money(zj);
				//结存单价
				drugsTemp.setNuit_price(dj);
				drugsTemp.setDrug_total_num(drugsTemp.getDrug_total_num() - Integer.parseInt(kqdsCostorderDetail.getNum()));
				drugsMap.put(kqdsCostorderDetail.getItemno(),drugsTemp);
			}
			
			List<YkzzDrugs>	tempList = new ArrayList<YkzzDrugs>();
			for(Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet()) {
				tempList.add(entry.getValue());
			}
			//修改库存结存单价、数量和总价
			ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
			Integer status = updateCostOrderById(costno);
			if(status>0){
				batchnumDao.insertDrugsBatchnumSave(list);//发药的同时保存批号信息
			}else{
				throw new Exception("此操作非发药操作触发!");
			}
		}
		}
	/**
	 * 
	 */
	@Override
	public List<JSONObject> findCostOrderDetailById(String id) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsInDao.findCostOrderDetailById(id);
		return list;
	}
	/**
	 * 退药
	 */
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void returnDrugs(String batchnoNum,String seqId,String costseqIdArr,String outnum,YZPerson person) throws Exception {
		List<YkzzDrugsBatchnumSave> list = new ArrayList<YkzzDrugsBatchnumSave>();
		YkzzDrugsBatchnumSave dp = new YkzzDrugsBatchnumSave();
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put(costseqIdArr, seqId);
		//获取费用单号下明细
		//修改费用单信息
		KqdsCostorderDetail kcd = new KqdsCostorderDetail();
		kcd.setSeqId(costseqIdArr);
		kcd.setReturnDrugsNum(outnum);
		kcd.setReturnTime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		kcd.setReturnName(person.getUserName());
		insertCostOrderDetailReturnBySeqId( kcd);
		
		//查询
		List<JSONObject> costOrderDetail = findCostOrderDetailById(costseqIdArr);
		BigDecimal nuitPrice = null;
		if(null != costOrderDetail && costOrderDetail.size() > 0){
			List<String> drugsOrderNoList = new ArrayList<String>();
			//获取药品详情
			int cs=0;
			for (JSONObject js : costOrderDetail) {
				if(Integer.parseInt(outnum)==Integer.parseInt((String) js.get("num"))){
					cs=1;
				}else if(Integer.parseInt(outnum)<Integer.parseInt((String) js.get("num"))){
					cs=Integer.parseInt(outnum);
				}
			}
			for (int i = 0; i < cs; i++) {
				//通过入库明细id
				String [] b = seqId.split(",");
				for(int j=0;j<b.length;j++){
				    YkzzDrugsInDetail drugsInDetail = ykzzDrugsInDetailDao.findYkzzDrugsInDatailById(b[j]);
				    if(drugsInDetail.getBatchnum().equals(batchnoNum)){
				    	//药品单价
						nuitPrice = drugsInDetail.getNuitPrice();
						//费用明细表id
						dp.setCostOrderDetailId(costseqIdArr);
						//id
						dp.setId(costOrderDetail.get(i).getString("seqid"));
						//药品名称
						dp.setDrugsname(costOrderDetail.get(i).getString("itemname"));
						//药品编号
						dp.setDrugsno(costOrderDetail.get(i).getString("itemno"));
						
						//库存数量
						Integer batchno = drugsInDetail.getBatchnoNum();
						dp.setBatchnum(Integer.toString(batchno));
						//发药数量
						dp.setNumber(Integer.toString(Integer.parseInt(costOrderDetail.get(i).getString("num"))-Integer.parseInt(outnum)));
						//批号
						dp.setBatchno(drugsInDetail.getBatchnum());
						list.add(dp);
						int cknum = Integer.parseInt(outnum);
						int num = batchno + cknum;
						drugsInDetail.setBatchnoNum(num);
						//退药的同时更新入库明细批号的数量，修改
						ykzzDrugsInDetailDao.updateDrugsInDetail(drugsInDetail);
						drugsOrderNoList.add(costOrderDetail.get(i).getString("itemno"));
				    }
				}
			}
			//根据药品编号查询出库存表的明细
			List<YkzzDrugs> drugsList = ykzzDrugsService.selectDrugsByOrderNoStr(drugsOrderNoList);
			//将药品集合转成Map
			Map<String,YkzzDrugs> drugsMap = new HashMap<String,YkzzDrugs>();
			for(YkzzDrugs drug : drugsList) {
				drugsMap.put(drug.getOrder_no(), drug);
			}
			
			//处理每个商品的数据
			YkzzDrugs drugsTemp = null;
			//for(JSONObject obj : costOrderDetail) {
			for (int i = 0; i < cs; i++) {
				//库存信息
				drugsTemp = drugsMap.get(costOrderDetail.get(i).getString("itemno"));
				//商品数量
				drugsTemp.setDrug_total_num(drugsTemp.getDrug_total_num() + Integer.parseInt(outnum));
				//商品价格
				BigDecimal yj=null;
				if(drugsTemp.getNuit_price()==null){
					drugsTemp.setNuit_price(nuitPrice);
					yj = nuitPrice.multiply(new BigDecimal(drugsTemp.getDrug_total_num().toString())).setScale(3,BigDecimal.ROUND_HALF_UP);
				}else{
					yj = drugsTemp.getNuit_price().multiply(new BigDecimal(drugsTemp.getDrug_total_num().toString())).setScale(3,BigDecimal.ROUND_HALF_UP);
				}
				drugsTemp.setDrgs_total_money(yj);
				drugsMap.put(costOrderDetail.get(i).getString("itemno"),drugsTemp);
			}
			
			List<YkzzDrugs>	tempList = new ArrayList<YkzzDrugs>();
			for(Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet()) {
				tempList.add(entry.getValue());
			}
			
			ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
			//updateCostOrderById(costno);
			
			//修改患者领药HUDH_YKZZ_DRUGS_BATCHNUM_SAVE
			//batchnumDao.insertDrugsBatchnumSave(list);//发药的同时保存批号信息
		}
	}
	/**
	 * 更新费用单状态
	 * @param costno
	 * @throws Exception
	 */
	private Integer updateCostOrderById(String costno) throws Exception{
		return ykzzDrugsInDao.updateCostOrderById(costno);
	}
	/**
	 * 更新费用单状态
	 * @param kcd
	 * @throws Exception
	 */
	private void insertCostOrderDetailReturnBySeqId(KqdsCostorderDetail kcd) throws Exception{
		ykzzDrugsInDao.insertCostOrderDetailReturnBySeqId(kcd);
	}
	@Override
	public void updateCheckStatus(String id) throws Exception {
		// TODO Auto-generated method stub
		ykzzDrugsInDao.updateCheckStatus(id);
	}
	
	@Override
	public List<JSONObject> findDrugsInAdmin(HttpServletRequest request) throws Exception{
		 String organization = ChainUtil.getCurrentOrganization(request);
		List<String> list = new ArrayList<String>();
		list.add(StaticVar.DRUGS_IN_ADMIN);
		List<JSONObject> jsonO = sysParaDao.getParaValueListByName(list, request, organization);
		return jsonO;
	}

	@Override
	public List<YkzzDrugsInDetail> findBatchnumByOrderno(String order_no) throws Exception {
		// TODO Auto-generated method stub
		List<YkzzDrugsInDetail> list = ykzzDrugsInDetailDao.findBatchnumByOrderno(order_no);
		return list;
	}
	@Override
	public List<YkzzDrugsInDetail> findBatchnumByOrderno1(String order_no) throws Exception {
		// TODO Auto-generated method stub
		List<YkzzDrugsInDetail> list = ykzzDrugsInDetailDao.findBatchnumByOrderno1(order_no);
		return list;
	}
	@Override
	public YkzzDrugsInDetail findYkzzDrugsInDatailByInDetail(String inDetail) throws Exception {
		// TODO Auto-generated method stub
		return ykzzDrugsInDetailDao.findYkzzDrugsInDatailByInDetail(inDetail);
	}
	/**
	 * 根据药品编号查询批号详情
	 * 2019-08-12 lwg
	 */
	@Override
	public List<JSONObject> findYkzzDrugsInDetailByOrderno(String orderno) throws Exception {
		// TODO Auto-generated method stub
		return ykzzDrugsInDetailDao.findYkzzDrugsInDetailByOrderno(orderno);
	}
	@Override
	public int updateYkzzDrugsInDatailByParentId(YkzzDrugsInDetail ykzzDrugsInDetail) throws Exception {
		// TODO Auto-generated method stub
		return ykzzDrugsInDetailDao.updateYkzzDrugsInDatailByParentId(ykzzDrugsInDetail);
	}
	/**
	 * 根据parentid查找CostOrderDetail表中的明细
	 */
	@Override
	public KqdsCostorderDetail findCostOrderDetailByParentid(String seqid) throws Exception {
		
		KqdsCostorderDetail kqdsCostorderDetail = ykzzDrugsInDao.findCostOrderDetailByParentid(seqid);
		return kqdsCostorderDetail;
	}
	/**
	 * 根据qfbh查询CostOrderDetail表中的明细
	 */
	@Override
	public List<JSONObject> findCostOrderDetailByQfbh(String qfbh) throws Exception {
		List<JSONObject> kqdsCostorderDetail = ykzzDrugsInDao.findCostOrderDetailByQfbh(qfbh);
		return kqdsCostorderDetail;
	}
	/**
	 * 根据明细表的qfbh查找Subtotal<0情况
	 * @param qfbh
	 * @return
	 * @throws Exception
	 */
	@Override
	public KqdsCostorderDetail findCostOrderDetailSubtotalByQfbh(String qfbh) throws Exception {
		KqdsCostorderDetail kqdsCostorderDetail = ykzzDrugsInDao.findCostOrderDetailSubtotalByQfbh(qfbh);
		return kqdsCostorderDetail;
	}

}
