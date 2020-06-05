package com.hudh.doctorpick.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.doctorpick.entity.GoodsDoctorPickIn;

import net.sf.json.JSONObject;

public interface IGoodsDoctorPickInService {
	
	void insertGoogsPick(GoodsDoctorPickIn dp, String paramsGoodsDetail, HttpServletRequest request, String costno1) throws Exception;
	
	List<JSONObject> findAllGoodsDoctorPick(Map<String, String> map) throws Exception;
	
	GoodsDoctorPickIn findAllGoodsDoctorPickBySeqId(String seqId) throws Exception;
	
	void deleteGoodsDoctorPickByIncode(String incode) throws Exception;
	
	void updateGoodsDoctorPickBySeqId(String seqId) throws Exception;
	
	JSONObject findGoodsDoctorPickByIncode(String incode) throws Exception;
}
