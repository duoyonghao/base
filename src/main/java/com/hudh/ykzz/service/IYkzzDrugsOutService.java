package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzDrugsOut;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IYkzzDrugsOutService {
  void insertDrugsOut(YkzzDrugsOut paramYkzzDrugsOut, String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findAllDrugsOut(Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findDetailByParendId(String paramString) throws Exception;
  
  String deleteDrugsOut(String paramString) throws Exception;
  
  void deleteDrugsOutById(String paramString) throws Exception;
  
  void deleteDrugsOutDetailByParendId(String paramString) throws Exception;
  
  String getDrugsInBatchnum(String paramString1, String paramString2) throws Exception;
  
  String findOutNumByOrderno(String paramString1, String paramString2) throws Exception;
  
  String findOutNumsByAll() throws Exception;
  
  String findBatchnumSaveOutNumsByOrdernoAndBatchnum(String paramString1, String paramString2) throws Exception;
  
  String findOutNumsByBatchnumSave() throws Exception;
}
