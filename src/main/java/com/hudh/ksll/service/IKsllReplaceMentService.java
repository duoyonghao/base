package com.hudh.ksll.service;

import com.hudh.ksll.entity.KsllReplaceMent;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IKsllReplaceMentService {
  void insertReplacement(KsllReplaceMent paramKsllReplaceMent, String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findAllReplacement(Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findAllReplacementNoDelete(Map<String, String> paramMap) throws Exception;
  
  void updateReplacementStatus(Map<String, String> paramMap, List<JSONObject> paramList, YZPerson paramYZPerson, String paramString) throws Exception;
  
  List<JSONObject> findDetailByParendId(Map<String, String> paramMap) throws Exception;
  
  void updaeCKStock(String paramString1, String paramString2, List<JSONObject> paramList, YZPerson paramYZPerson, String paramString3) throws Exception;
  
  void updateKsllGoodNums(String paramString1, String paramString2) throws Exception;
  
  int selectReturnNumByGoodscode(String paramString) throws Exception;
  
  List<JSONObject> findCkReplaceMentDetailByParendId(String paramString) throws Exception;
}
