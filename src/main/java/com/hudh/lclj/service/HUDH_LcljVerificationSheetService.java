package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljApprovedMemo;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public abstract interface HUDH_LcljVerificationSheetService
{
  public abstract void insert(LcljApprovedMemo paramLcljApprovedMemo)
    throws Exception;
  
  public abstract void Update(LcljApprovedMemo paramLcljApprovedMemo)
    throws Exception;
  
  public abstract List<JSONObject> getCheckRecord(Map<String, String> paramMap, YZPerson paramYZPerson)
    throws Exception;
  
  public abstract List<JSONObject> getAwaitCheckRecord(Map<String, String> paramMap, YZPerson paramYZPerson)
    throws Exception;
  
  public abstract Integer getCheckRecordNum(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void delCheckRecord(String paramString)
    throws Exception;
  
  public abstract Integer getAwaitVerifieNum(Map<String, String> paramMap)
    throws Exception;
}
