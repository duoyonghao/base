package com.hudh.doctorpick.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class GoodsDoctorPickInDetailDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存明细数据
	 * @param list
	 * @throws Exception
	 */
	public void batchSaveInDetail(List<GoodsDoctorPickInDetail> list) throws Exception {
		dao.batchUpdate("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.batchSaveInDetail", list);
	}
	
	/**
	 * 根据单据编号查询明细
	 * @param incode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDoctorPickInDetailByIncode(String incode, String visualstaff) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNotNullOrEmpty(incode)) {
			map.put("incode", incode);
		}
		if (YZUtility.isNotNullOrEmpty(visualstaff)) {
			map.put("querytype", visualstaff);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.findDoctorPickInDetailByIncode", map);
		return list;
	}
	
	/**
	 * 根据单据编号批量删除领料明细
	 * @param incode
	 * @throws Exception
	 */
	public void deleteDoctorPickInDetailByIncode(String incode) throws Exception {
		dao.delete("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.", incode);
	}
	
	/**
	 * 根据明细表id查询信息
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	public GoodsDoctorPickInDetail findDoctorPickInDetailBySeqId(String seqId) throws Exception {
		GoodsDoctorPickInDetail goodsDoctorPickInDetail = (GoodsDoctorPickInDetail) dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.findDoctorPickInDetailBySeqId", seqId);
		return goodsDoctorPickInDetail;
	}
	
	/**
	 * 根据主键更新领料明细
	 * @param seq_id
	 * @throws Exception
	 */
	public void updateGoodsDoctorPickInDetail(String seq_id) throws Exception {
		dao.update("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.updateGoodsDoctorPickInDetail", seq_id);
	}
	
	/**
	 * 根据主键删除明细记录
	 * @param seqId
	 * @throws Exception
	 */
	public void deleteDoctorPickInDetailBySeqId(String seqId) throws Exception {
		dao.delete("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.deleteDoctorPickInDetailBySeqId", seqId);
	}
	
	/**
	 * 根据商品id查询领料明细
	 * @param goodsuuid
	 * @return
	 * @throws Exception
	 */
	public GoodsDoctorPickInDetail findDoctorPickInDetailByGoodsuuid(String goodsuuid) throws Exception {
		GoodsDoctorPickInDetail detail = (GoodsDoctorPickInDetail) dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.findDoctorPickInDetailByGoodsuuid", goodsuuid);
		return detail;
	}
	
	/**
	 * 根据商品id更新明细表商品数量
	 * @param goodsuuid
	 * @throws Exception
	 */
	public void updateDoctorPickInDetailByGoodsuuid(GoodsDoctorPickInDetail dp) throws Exception {
		dao.update("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.updateDoctorPickInDetailByGoodsuuid", dp);
	}
	
	/**
	 * 根据明细表主键查询信息
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	public JSONObject findDoctorPickInDetailById(String seqId) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.findDoctorPickInDetailById", seqId);
		return json;
	}
}
