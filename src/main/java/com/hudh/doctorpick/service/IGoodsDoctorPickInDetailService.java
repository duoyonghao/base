package com.hudh.doctorpick.service;

import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;
import java.util.List;
import net.sf.json.JSONObject;

public interface IGoodsDoctorPickInDetailService {
  void batchSaveInDetail(List<GoodsDoctorPickInDetail> paramList);
  
  List<JSONObject> findDoctorPickInDetailByIncode(String paramString1, String paramString2) throws Exception;
  
  GoodsDoctorPickInDetail findDoctorPickInDetailBySeqId(String paramString) throws Exception;
  
  void updateGoodsDoctorPickInDetail(String paramString) throws Exception;
  
  void deleteDoctorPickInDetailBySeqId(String paramString) throws Exception;
  
  void deleteDoctorPickInDetailByIncode(String paramString) throws Exception;
  
  JSONObject findDoctorPickInDetailById(String paramString) throws Exception;
}
