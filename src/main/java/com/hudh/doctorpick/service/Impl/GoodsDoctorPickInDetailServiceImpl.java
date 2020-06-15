package com.hudh.doctorpick.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.doctorpick.dao.GoodsDoctorPickInDetailDao;
import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;
import com.hudh.doctorpick.service.IGoodsDoctorPickInDetailService;

import net.sf.json.JSONObject;

@Service
public class GoodsDoctorPickInDetailServiceImpl implements IGoodsDoctorPickInDetailService {
	
	@Autowired
	private GoodsDoctorPickInDetailDao detailDao;

	@Override
	public void batchSaveInDetail(List<GoodsDoctorPickInDetail> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<JSONObject> findDoctorPickInDetailByIncode(String incode, String visualstaff) throws Exception {
		List<JSONObject> list = detailDao.findDoctorPickInDetailByIncode(incode, visualstaff);
		return list;
	}

	@Override
	public void updateGoodsDoctorPickInDetail(String seq_id) throws Exception {
		// TODO Auto-generated method stub
		detailDao.updateGoodsDoctorPickInDetail(seq_id);
	}

	@Override
	public void deleteDoctorPickInDetailBySeqId(String seqId) throws Exception {
		// TODO Auto-generated method stub
		detailDao.deleteDoctorPickInDetailBySeqId(seqId);
	}

	@Override
	public GoodsDoctorPickInDetail findDoctorPickInDetailBySeqId(String seqId) throws Exception {
		// TODO Auto-generated method stub
		GoodsDoctorPickInDetail goodsDoctorPickInDetail = detailDao.findDoctorPickInDetailBySeqId(seqId);
		return goodsDoctorPickInDetail;
	}

	@Override
	public void deleteDoctorPickInDetailByIncode(String incode) throws Exception {
		// TODO Auto-generated method stub
		detailDao.deleteDoctorPickInDetailByIncode(incode);
	}

	@Override
	public JSONObject findDoctorPickInDetailById(String seqId) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = detailDao.findDoctorPickInDetailById(seqId);
		return json;
	}

}
