package com.hudh.doctorpick.service;

import java.util.List;

import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;

import net.sf.json.JSONObject;

public interface IGoodsDoctorPickInDetailService {
	
	void batchSaveInDetail(List<GoodsDoctorPickInDetail> list);
	
	List<JSONObject> findDoctorPickInDetailByIncode(String incode, String visualstaff) throws Exception;
	
	GoodsDoctorPickInDetail findDoctorPickInDetailBySeqId(String seqId) throws Exception;
	
	void updateGoodsDoctorPickInDetail(String seq_id) throws Exception;
	
	void deleteDoctorPickInDetailBySeqId(String seqId) throws Exception;
	
	/**
	 * 批量删除领料明细
	 * @param incode
	 * @throws Exception
	 */
	void deleteDoctorPickInDetailByIncode(String incode) throws Exception;
	
	JSONObject findDoctorPickInDetailById(String seqId) throws Exception;
}
