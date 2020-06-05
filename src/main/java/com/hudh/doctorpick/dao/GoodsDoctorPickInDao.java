package com.hudh.doctorpick.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.doctorpick.entity.GoodsDoctorPickIn;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

@Service
public class GoodsDoctorPickInDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存
	 * @param goodsDoctorPickIn
	 * @throws Exception
	 */
	public void insertGoogsPick(GoodsDoctorPickIn goodsDoctorPickIn) throws Exception {
		dao.save("HUDH_GOOGS_DOCTOR_PICK_IN.insert", goodsDoctorPickIn);
	}
	
	/**
	 * 查询全部领料材料
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllGoodsDoctorPick(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_GOOGS_DOCTOR_PICK_IN.findAllGoodsDoctorPick", map);
		return list;
	}
	
	/**
	 * 根据领料表主键查询信息
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	public GoodsDoctorPickIn findAllGoodsDoctorPickBySeqId(String seqId) throws Exception {
		GoodsDoctorPickIn goodsDoctorPickIn = (GoodsDoctorPickIn) dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN.findAllGoodsDoctorPickBySeqId", seqId);
		return goodsDoctorPickIn;
	}
	
	/**
	 * 根据单据编号删除领料记录
	 * @param incode
	 * @throws Exception
	 */
	public void deleteGoodsDoctorPickByIncode(String incode) throws Exception {
		dao.delete("HUDH_GOOGS_DOCTOR_PICK_IN.deleteGoodsDoctorPickByIncode", incode);
	}
	
	public JSONObject findGoodsDoctorPickByIncode(String incode) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN.findGoodsDoctorPickByIncode", incode);
		return json;
	}
	
	/**
	 * 根据领料表主键修改领料记录
	 * @param seqId
	 * @throws Exception
	 */
	public void updateGoodsDoctorPickBySeqId(String seqId) throws Exception {
		dao.update("HUDH_GOOGS_DOCTOR_PICK_IN.updateGoodsDoctorPickBySeqId", seqId);
	}
	
}
