package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IYkzzDrugsService {
  int insertDrugsinfor(YkzzDrugs paramYkzzDrugs, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<YkzzDrugs> findDeugsByContryCode(String paramString1, String paramString2) throws Exception;
  
  JSONObject selectDrugsByPrimaryId(String paramString) throws Exception;
  
  List<JSONObject> selectAllDrugsInfor(Map<String, String> paramMap) throws Exception;
  
  void deleteDrugsByPrimaryId(String paramString) throws Exception;
  
  void updateDrugsByPrimaryId(YkzzDrugs paramYkzzDrugs) throws Exception;
  
  List<JSONObject> selectDrugsInforByConditionQuery(Map<String, String> paramMap) throws Exception;
  
  List<YkzzDrugs> selectDrugsByOrderNoStr(List<String> paramList) throws Exception;
  
  List<YkzzDrugs> selectDrugsByIdStr(List<YkzzDrugsInDetail> paramList) throws Exception;
  
  List<YkzzDrugs> selectDrugsOutByIdStr(List<YkzzDrugsOutDetail> paramList) throws Exception;
  
  void batchUpdateDrugsByPrimaryId(List<YkzzDrugs> paramList) throws Exception;
  
  void saveBatchInsert(List<List<String>> paramList, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<YkzzDrugs> getAllDrugsInfor(Map<String, String> paramMap) throws Exception;
  
  void forbiddenDrugs(String paramString) throws Exception;
  
  void recoverDrugs(String paramString) throws Exception;
}
