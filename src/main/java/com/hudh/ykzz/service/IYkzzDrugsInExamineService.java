package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzDrugsInExamine;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IYkzzDrugsInExamineService {
  int insertDrugsInExamine(YkzzDrugsInExamine paramYkzzDrugsInExamine, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findDrugsInExamineByInId(String paramString) throws Exception;
  
  void deleteDrugsInExamineById(String paramString) throws Exception;
  
  void deleteDrugsInExamineByInId(String paramString) throws Exception;
  
  List<JSONObject> findDugsExamineDetail(Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findDugsReturnDetail(Map<String, String> paramMap) throws Exception;
}
