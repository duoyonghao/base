package com.hudh.doctorpick.service;

import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;
import java.util.List;
import net.sf.json.JSONObject;

public abstract interface IGoodsDoctorPickInDetailService
{
  public abstract void batchSaveInDetail(List<GoodsDoctorPickInDetail> paramList);
  
  public abstract List<JSONObject> findDoctorPickInDetailByIncode(String paramString1, String paramString2)
    throws Exception;
  
  public abstract GoodsDoctorPickInDetail findDoctorPickInDetailBySeqId(String paramString)
    throws Exception;
  
  public abstract void updateGoodsDoctorPickInDetail(String paramString)
    throws Exception;
  
  public abstract void deleteDoctorPickInDetailBySeqId(String paramString)
    throws Exception;
  
  public abstract void deleteDoctorPickInDetailByIncode(String paramString)
    throws Exception;
  
  public abstract JSONObject findDoctorPickInDetailById(String paramString)
    throws Exception;
}
