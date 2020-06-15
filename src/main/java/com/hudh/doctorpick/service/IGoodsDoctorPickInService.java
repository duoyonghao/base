package com.hudh.doctorpick.service;

import com.hudh.doctorpick.entity.GoodsDoctorPickIn;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IGoodsDoctorPickInService
{
  public abstract void insertGoogsPick(GoodsDoctorPickIn paramGoodsDoctorPickIn, String paramString1, HttpServletRequest paramHttpServletRequest, String paramString2)
    throws Exception;
  
  public abstract List<JSONObject> findAllGoodsDoctorPick(Map<String, String> paramMap)
    throws Exception;
  
  public abstract GoodsDoctorPickIn findAllGoodsDoctorPickBySeqId(String paramString)
    throws Exception;
  
  public abstract void deleteGoodsDoctorPickByIncode(String paramString)
    throws Exception;
  
  public abstract void updateGoodsDoctorPickBySeqId(String paramString)
    throws Exception;
  
  public abstract JSONObject findGoodsDoctorPickByIncode(String paramString)
    throws Exception;
}
