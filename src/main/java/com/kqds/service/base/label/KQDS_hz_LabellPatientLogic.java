package com.kqds.service.base.label;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_hz_LabellPatientLogic {
  @Autowired
  private DaoSupport dao;
  
  public List<kqdsHzLabellabeAndPatient> findLabellPatientByUserId(JSONObject json) throws Exception {
    Map<String, Object> dataMap = new HashMap<>();
    String userCode = json.getString("userCode");
    dataMap.put("userCode", userCode);
    boolean needTwoBoo = json.containsKey("needTwo");
    if (needTwoBoo) {
      String needTwoStr = json.getString("needTwo");
      String[] needTwoArr = needTwoStr.split(",");
      dataMap.put("needTwoArr", needTwoArr);
    } 
    boolean societyTwoBoo = json.containsKey("societyTwo");
    if (societyTwoBoo) {
      String societyTwoStr = json.getString("societyTwo");
      String[] societyTwoArr = societyTwoStr.split(",");
      dataMap.put("societyTwoArr", societyTwoArr);
    } 
    boolean expenseTwoBoo = json.containsKey("expenseTwo");
    if (expenseTwoBoo) {
      String expenseTwoStr = json.getString("expenseTwo");
      String[] expenseTwoArr = expenseTwoStr.split(",");
      dataMap.put("expenseTwoArr", expenseTwoArr);
    } 
    boolean needThreeBoo = json.containsKey("needThree");
    if (needThreeBoo) {
      String needThreeStr = json.getString("needThree");
      String[] needThreeArr = needThreeStr.split(",");
      dataMap.put("needThreeArr", needThreeArr);
    } 
    boolean societyThreeBoo = json.containsKey("societyThree");
    if (societyThreeBoo) {
      String societyThreeStr = json.getString("societyThree");
      String[] societyThreeArr = societyThreeStr.split(",");
      dataMap.put("societyThreeArr", societyThreeArr);
    } 
    boolean expenseThreeBoo = json.containsKey("expenseThree");
    if (expenseThreeBoo) {
      String expenseThreeStr = json.getString("expenseThree");
      String[] expenseThreeArr = expenseThreeStr.split(",");
      dataMap.put("expenseThreeArr", expenseThreeArr);
    } 
    List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>)this.dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabellPatientByUserId", dataMap);
    return list;
  }
  
  public List<kqdsHzLabellabeAndPatient> findLabelContentByParentId(String parentId, Map<String, String> dataMap) throws Exception {
    dataMap.put("parentId", parentId);
    List<kqdsHzLabellabeAndPatient> list = (List<kqdsHzLabellabeAndPatient>)this.dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelContentByParentId", dataMap);
    return list;
  }
  
  public List<JSONObject> findLabelByCreatetime(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelByCreatetime", map);
    return list;
  }
  
  public List<JSONObject> findLabelByCreatetime1(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("KQDS_Hz_LabellabeAndPatient.findLabelByCreatetime1", map);
    return list;
  }
}
