package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IYkzzDrugsInService {
  void insertDrugsIn(YkzzDrugsIn paramYkzzDrugsIn, String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void drugsAddInStock(String paramString) throws Exception;
  
  List<JSONObject> findAllDrugsIn(Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findDetailByParendId(String paramString) throws Exception;
  
  String deleteDrugsIn(String paramString) throws Exception;
  
  void deleteDrugsInById(String paramString) throws Exception;
  
  void deleteDrugsInDetailByParendId(String paramString) throws Exception;
  
  List<JSONObject> findAllCostOrder(Map<String, Object> paramMap) throws Exception;
  
  List<JSONObject> findCostOrderDetailByCostno(String paramString) throws Exception;
  
  void grantDrugs(String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void returnDrugs(String paramString1, String paramString2, String paramString3, String paramString4, YZPerson paramYZPerson) throws Exception;
  
  void updateCheckStatus(String paramString) throws Exception;
  
  List<JSONObject> findDrugsInAdmin(HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<YkzzDrugsInDetail> findBatchnumByOrderno(String paramString) throws Exception;
  
  YkzzDrugsInDetail findYkzzDrugsInDatailByInDetail(String paramString) throws Exception;
  
  List<JSONObject> findCostOrderDetailById(String paramString) throws Exception;
  
  List<YkzzDrugsInDetail> findBatchnumByOrderno1(String paramString) throws Exception;
  
  String findByParendId(String paramString) throws Exception;
  
  int updateYkzzDrugsInDatailByParentId(YkzzDrugsInDetail paramYkzzDrugsInDetail) throws Exception;
  
  List<JSONObject> findCostOrderDetailReturnBySeqid(String paramString) throws Exception;
  
  KqdsCostorderDetail findCostOrderDetailByParentid(String paramString) throws Exception;
  
  KqdsCostorderDetail findCostOrderDetailBySeqid(String paramString) throws Exception;
  
  List<JSONObject> findCostOrderDetailByQfbh(String paramString) throws Exception;
  
  KqdsCostorderDetail findCostOrderDetailSubtotalByQfbh(String paramString) throws Exception;
  
  List<JSONObject> findYkzzDrugsInDetailByOrderno(String paramString) throws Exception;
}
