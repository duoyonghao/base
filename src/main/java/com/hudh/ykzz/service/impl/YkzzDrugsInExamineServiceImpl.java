package com.hudh.ykzz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.dao.YkzzDrugsInDao;
import com.hudh.ykzz.dao.YkzzDrugsInExamineDao;
import com.hudh.ykzz.entity.YkzzDrugsInExamine;
import com.hudh.ykzz.service.IYkzzDrugsInExamineService;
import com.hudh.ykzz.service.IYkzzDrugsInService;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Service
public class YkzzDrugsInExamineServiceImpl implements IYkzzDrugsInExamineService {
	/**
	 * 入库审批dao
	 */
	@Autowired
	private YkzzDrugsInExamineDao ykzzDrugsInExamineDao;
	/**
	 * 入库
	 */
	@Autowired
	private YkzzDrugsInDao ykzzDrugsInDao;
	/**
	 * 入库接口
	 */
	@Autowired
	private IYkzzDrugsInService YkzzDrugsInService;
	
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int insertDrugsInExamine(YkzzDrugsInExamine ykzzDrugsInExamine,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ykzzDrugsInExamine.setId(YZUtility.getUUID());
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		ykzzDrugsInExamine.setCheckDate(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		ykzzDrugsInExamine.setCheckUserId(person.getSeqId());
		ykzzDrugsInExamine.setOrganization(organization);
		//当外包装是否破损异常 合格证 验收报告 全部通过时审批结果为1 ，其他为0
		if(ykzzDrugsInExamine.getPacking().equals("1") && ykzzDrugsInExamine.getReport().equals("1") && ykzzDrugsInExamine.getCertificate().equals("1")) {
			ykzzDrugsInExamine.setResult("1");
			//审批通过更新入库表对应数据审批状态
			YkzzDrugsInService.updateCheckStatus(ykzzDrugsInExamine.getDrugsInId());
			YkzzDrugsInService.drugsAddInStock(ykzzDrugsInExamine.getDrugsInId());
		}else {
			ykzzDrugsInExamine.setResult("0");
		}
		return ykzzDrugsInExamineDao.insertDrugsInExamine(ykzzDrugsInExamine);
	}

	@Override
	public List<JSONObject> findDrugsInExamineByInId(String drugsInId) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsInExamineDao.findDrugsInExamineByInId(drugsInId);
		return list;
	}

	@Override
	public void deleteDrugsInExamineById(String id) throws Exception {
		// TODO Auto-generated method stub
		ykzzDrugsInExamineDao.deleteDrugsInExamineById(id);
	}

	@Override
	public void deleteDrugsInExamineByInId(String drugsInId) throws Exception {
		// TODO Auto-generated method stub
		ykzzDrugsInExamineDao.deleteDrugsInExamineByInId(drugsInId);
	}

	@Override
	public List<JSONObject> findDugsExamineDetail(Map<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = new ArrayList<JSONObject>();
		List<JSONObject> list1 = new ArrayList<JSONObject>();
		list = ykzzDrugsInExamineDao.findDugsExamineDetail(dataMap);
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("batchno")!=null&&!list.get(i).get("batchno").equals("")){
				String qfbh=(String) list.get(i).get("qfbh");
				String seqid=(String) list.get(i).get("seq_id");
				//查询所有退款
				KqdsCostorderDetail kqdsCostorderDetail2 = ykzzDrugsInDao.findCostOrderDetailByParentid(seqid);
				if(kqdsCostorderDetail2!=null){
					int nums=Integer.parseInt((String)list.get(i).get("num"))+Integer.parseInt(kqdsCostorderDetail2.getNum());
					list.get(i).put("num", nums);
					list.get(i).put("subtotal", new BigDecimal((String)list.get(i).get("unitprice")).multiply(new BigDecimal(nums)));
					list.get(i).put("paymoney",new BigDecimal((String)list.get(i).get("unitprice")).multiply(new BigDecimal(nums)));
				}else{
					if(qfbh!=null&&!qfbh.equals("")){
						KqdsCostorderDetail kqdsCostorderDetail1 = ykzzDrugsInDao.findCostOrderDetailSubtotalByQfbh(qfbh);
						if(kqdsCostorderDetail1!=null){
							int nums=Integer.parseInt((String)list.get(i).get("num"))+Integer.parseInt(kqdsCostorderDetail1.getNum());
							list.get(i).put("num", nums);
							list.get(i).put("subtotal", new BigDecimal((String)list.get(i).get("unitprice")).multiply(new BigDecimal(nums)));
							list.get(i).put("paymoney",new BigDecimal((String)list.get(i).get("unitprice")).multiply(new BigDecimal(nums)));

						}
					}
				}
				list1.add(list.get(i));
			}
		}
		return list1;
	}
	@Override
	public List<JSONObject> findDugsReturnDetail(Map<String, String> dataMap) throws Exception {
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ykzzDrugsInExamineDao.findDugsReturnDetail(dataMap);
		return list;
	}
	

}
