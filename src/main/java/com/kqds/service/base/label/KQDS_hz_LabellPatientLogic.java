/**  
  *
  * @Title:  KQDS_hz_LabellPatientLogic.java   
  * @Package com.kqds.service.base.label   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月5日 上午8:44:07   
  * @version V1.0  
  */ 
package com.kqds.service.base.label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  KQDS_hz_LabellPatientLogic   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年9月5日 上午8:44:07   
  *      
  */
@Service
public class KQDS_hz_LabellPatientLogic {
	
	@Autowired
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	public List<kqdsHzLabellabeAndPatient> findLabellPatientByUserId(JSONObject json) throws Exception {
//		JSONObject jsonO = new JSONObject();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String userCode = json.getString("userCode");
		dataMap.put("userCode", userCode);
		boolean needTwoBoo = json.containsKey("needTwo");
		if(needTwoBoo){
			String needTwoStr = json.getString("needTwo");
			String[] needTwoArr = needTwoStr.split(",");
			dataMap.put("needTwoArr", needTwoArr);
		}
		boolean societyTwoBoo = json.containsKey("societyTwo");
		if(societyTwoBoo) {
			String societyTwoStr = json.getString("societyTwo");
			String[] societyTwoArr = societyTwoStr.split(",");
			dataMap.put("societyTwoArr", societyTwoArr);
		}
		boolean expenseTwoBoo = json.containsKey("expenseTwo");
		if(expenseTwoBoo) {
			String expenseTwoStr = json.getString("expenseTwo");
			String[] expenseTwoArr = expenseTwoStr.split(",");
			dataMap.put("expenseTwoArr", expenseTwoArr);
		}
		boolean needThreeBoo = json.containsKey("needThree");
		if(needThreeBoo) {
			String needThreeStr = json.getString("needThree"); 
			String[] needThreeArr = needThreeStr.split(",");
			dataMap.put("needThreeArr", needThreeArr);
		}
		boolean societyThreeBoo = json.containsKey("societyThree");
		if(societyThreeBoo) {
			String societyThreeStr = json.getString("societyThree");
			String[] societyThreeArr = societyThreeStr.split(",");
			dataMap.put("societyThreeArr", societyThreeArr);
		}
		boolean expenseThreeBoo = json.containsKey("expenseThree");
		if(expenseThreeBoo) {
			String expenseThreeStr = json.getString("expenseThree");
			String[] expenseThreeArr = expenseThreeStr.split(",");
			dataMap.put("expenseThreeArr", expenseThreeArr);
		}
		
//		JSONObject jsonObject = new JSONObject();
//		JSONObject jsonNeedTwo = new JSONObject();
//		JSONObject jsonSocietyTwo = new JSONObject();
//		JSONObject jsonExpenseTwo = new JSONObject();
//		JSONObject jsonNeedThree = new JSONObject();
//		JSONObject jsonSocietyThree = new JSONObject();
//		JSONObject jsonExpenseThree = new JSONObject();
//		jsonObject.put("userCode", userCode);
//		boolean needTwoBool = json.containsKey("needTwo");
//		if (needTwoBool) {
//			String needTwo = json.getString("needTwo");
//			if (!YZUtility.isNullorEmpty(needTwo)) {
//				List<String> needTwolist = YZUtility.ConvertString2List(needTwo);
//				jsonNeedTwo.put("needTwolist", needTwolist);
//				jsonNeedTwo.put("userCode", userCode);
//				jsonObject.put("needTwolist", needTwolist);
//			}
//		}
//		boolean societyTwoBool = json.containsKey("societyTwo");
//		if(societyTwoBool) {
//			String societyTwo = json.getString("societyTwo");
//			if (!YZUtility.isNullorEmpty(societyTwo)) {
//				List<String> societyTwoList = YZUtility.ConvertString2List(societyTwo);
//				jsonSocietyTwo.put("societyTwoList", societyTwoList);
//				jsonSocietyTwo.put("userCode", userCode);
//				jsonObject.put("societyTwoList", societyTwoList);
//			}
//		}
//		boolean expenseTwoBool = json.containsKey("expenseTwo");
//		if(expenseTwoBool){
//			String expenseTwo = json.getString("expenseTwo");
//			if (!YZUtility.isNullorEmpty(expenseTwo)) {
//				List<String> expenseTwoList = YZUtility.ConvertString2List(expenseTwo);
//				jsonExpenseTwo.put("expenseTwoList", expenseTwoList);
//				jsonExpenseTwo.put("userCode", userCode);
//				jsonObject.put("expenseTwoList", expenseTwoList);
//			}
//		}
//		boolean needThreeBool = json.containsKey("needThree");
//		if (needThreeBool) {
//			String needThree = json.getString("needThree");      
//			if (!YZUtility.isNullorEmpty(needThree)) {
//				List<String> needThreeList = YZUtility.ConvertString2List(needThree);
//				jsonNeedThree.put("needThreeList", needThreeList);
//				jsonNeedThree.put("userCode", userCode);
//				jsonObject.put("needThreeList", needThreeList);
//			}
//		}
//		boolean societyThreeBool = json.containsKey("societyThree");
//		if(societyThreeBool) {
//			String societyThree = json.getString("societyThree");
//			if (!YZUtility.isNullorEmpty(societyThree)) {
//				List<String> societyThreeList = YZUtility.ConvertString2List(societyThree);
//				jsonSocietyThree.put("societyThreeList", societyThreeList);
//				jsonSocietyThree.put("userCode", userCode);
//				jsonObject.put("societyThreeList", societyThreeList);
//			}
//		}
//		boolean expenseThreeBool = json.containsKey("expenseThree");
//		if(expenseThreeBool){
//			String expenseThree = json.getString("expenseThree");
//			if (!YZUtility.isNullorEmpty(expenseThree)) {
//				List<String> expenseThreeList = YZUtility.ConvertString2List(expenseThree);
//				jsonExpenseThree.put("expenseThreeList", expenseThreeList);
//				jsonExpenseThree.put("userCode", userCode);
//				jsonObject.put("expenseThreeList", expenseThreeList);
//			}
//			
//		}
//		boolean needTwolistBool = jsonNeedTwo.containsKey("needTwolist");
//		boolean societyTwoListBool = jsonSocietyTwo.containsKey("societyTwoList");
//		boolean expenseTwoListBool = jsonExpenseTwo.containsKey("expenseTwoList");
//		boolean needThreeListBool = jsonNeedThree.containsKey("needThreeList");
//		boolean societyThreeListBool = jsonSocietyThree.containsKey("societyThreeList");
//		boolean expenseThreeListBool = jsonExpenseThree.containsKey("expenseThreeList");
//		if(needTwolistBool) {
//			List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>) dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelContentByNeedTwolist", jsonNeedTwo);
//			return list;
//		} else if (societyTwoListBool) {
//			List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>) dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelContentBySocietyTwoList", jsonSocietyTwo);
//			return list;
//		} else if (expenseTwoListBool) {
//			List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>) dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelContentByExpenseTwoList", jsonExpenseTwo);
//			return list;
//		} else if (needThreeListBool) {
//			List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>) dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelContentByNeedThreeList", jsonNeedThree);
//			return list;
//		} else if (societyThreeListBool) {
//			List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>) dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelContentBySocietyThreeList", jsonSocietyThree);
//			return list;
//		} else if (expenseThreeListBool) {
//			List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>) dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelContentByeExpenseThreeList", jsonExpenseThree);
//			return list;
//		} else {
//			List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>) dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabellPatientByUserId", json);
//			return list;
//		}
//		if(jsonObject.containsKey("needTwolist") && jsonObject.containsKey("societyTwoList") && jsonObject.containsKey("expenseTwoList") && jsonObject.containsKey("needThreeList") && jsonObject.containsKey("societyThreeList") && jsonObject.containsKey("expenseThreeList")){
			List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>) dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabellPatientByUserId", dataMap);
			return list;
//		}
//		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<kqdsHzLabellabeAndPatient> findLabelContentByParentId(String parentId, Map<String, String> dataMap) throws Exception {
		dataMap.put("parentId", parentId);
		List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>) dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelContentByParentId", dataMap);
		return list;
	}
	//查询所有数据遍历添加到redis
	public List<JSONObject> findLabelByCreatetime(Map<String,String> map) throws Exception{
		
		List<JSONObject> list = (List<JSONObject>)dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelByCreatetime", map);
		return list;
	}
	//查询所有数据遍历添加到redis
	public List<JSONObject> findLabelByCreatetime1(Map<String,String> map) throws Exception{
			
		List<JSONObject> list = (List<JSONObject>)dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelByCreatetime1", map);
		return list;
	}
}
