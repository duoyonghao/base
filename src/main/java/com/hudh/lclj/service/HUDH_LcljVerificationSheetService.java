package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljApprovedMemo;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public interface HUDH_LcljVerificationSheetService {
  void insert(LcljApprovedMemo paramLcljApprovedMemo) throws Exception;
  
  void Update(LcljApprovedMemo paramLcljApprovedMemo) throws Exception;
  
  List<JSONObject> getCheckRecord(Map<String, String> paramMap, YZPerson paramYZPerson) throws Exception;
  
  List<JSONObject> getAwaitCheckRecord(Map<String, String> paramMap, YZPerson paramYZPerson) throws Exception;
  
  Integer getCheckRecordNum(Map<String, String> paramMap) throws Exception;
  
  void delCheckRecord(String paramString) throws Exception;
  
  Integer getAwaitVerifieNum(Map<String, String> paramMap) throws Exception;
}
