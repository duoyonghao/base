package com.hudh.doctorpick.service;

import com.hudh.doctorpick.entity.GoodsDoctorPickIn;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IGoodsDoctorPickInService {
  void insertGoogsPick(GoodsDoctorPickIn paramGoodsDoctorPickIn, String paramString1, HttpServletRequest paramHttpServletRequest, String paramString2) throws Exception;
  
  List<JSONObject> findAllGoodsDoctorPick(Map<String, String> paramMap) throws Exception;
  
  GoodsDoctorPickIn findAllGoodsDoctorPickBySeqId(String paramString) throws Exception;
  
  void deleteGoodsDoctorPickByIncode(String paramString) throws Exception;
  
  void updateGoodsDoctorPickBySeqId(String paramString) throws Exception;
  
  JSONObject findGoodsDoctorPickByIncode(String paramString) throws Exception;
}
