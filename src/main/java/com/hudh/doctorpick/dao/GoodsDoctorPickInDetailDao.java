package com.hudh.doctorpick.dao;

import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.YZUtility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsDoctorPickInDetailDao
{
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveInDetail(List<GoodsDoctorPickInDetail> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.batchSaveInDetail", list);
  }
  
  public List<JSONObject> findDoctorPickInDetailByIncode(String incode, String visualstaff)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    if (YZUtility.isNotNullOrEmpty(incode)) {
      map.put("incode", incode);
    }
    if (YZUtility.isNotNullOrEmpty(visualstaff)) {
      map.put("querytype", visualstaff);
    }
    List<JSONObject> list = (List)this.dao.findForList("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.findDoctorPickInDetailByIncode", map);
    return list;
  }
  
  public void deleteDoctorPickInDetailByIncode(String incode)
    throws Exception
  {
    this.dao.delete("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.", incode);
  }
  
  public GoodsDoctorPickInDetail findDoctorPickInDetailBySeqId(String seqId)
    throws Exception
  {
    GoodsDoctorPickInDetail goodsDoctorPickInDetail = (GoodsDoctorPickInDetail)this.dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.findDoctorPickInDetailBySeqId", seqId);
    return goodsDoctorPickInDetail;
  }
  
  public void updateGoodsDoctorPickInDetail(String seq_id)
    throws Exception
  {
    this.dao.update("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.updateGoodsDoctorPickInDetail", seq_id);
  }
  
  public void deleteDoctorPickInDetailBySeqId(String seqId)
    throws Exception
  {
    this.dao.delete("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.deleteDoctorPickInDetailBySeqId", seqId);
  }
  
  public GoodsDoctorPickInDetail findDoctorPickInDetailByGoodsuuid(String goodsuuid)
    throws Exception
  {
    GoodsDoctorPickInDetail detail = (GoodsDoctorPickInDetail)this.dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.findDoctorPickInDetailByGoodsuuid", goodsuuid);
    return detail;
  }
  
  public void updateDoctorPickInDetailByGoodsuuid(GoodsDoctorPickInDetail dp)
    throws Exception
  {
    this.dao.update("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.updateDoctorPickInDetailByGoodsuuid", dp);
  }
  
  public JSONObject findDoctorPickInDetailById(String seqId)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN_DETAIL.findDoctorPickInDetailById", seqId);
    return json;
  }
}
