package com.hudh.doctorpick.dao;

import com.hudh.doctorpick.entity.GoodsDoctorPickIn;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsDoctorPickInDao {
  @Autowired
  private DaoSupport dao;
  
  public void insertGoogsPick(GoodsDoctorPickIn goodsDoctorPickIn) throws Exception {
    this.dao.save("HUDH_GOOGS_DOCTOR_PICK_IN.insert", goodsDoctorPickIn);
  }
  
  public List<JSONObject> findAllGoodsDoctorPick(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_GOOGS_DOCTOR_PICK_IN.findAllGoodsDoctorPick", map);
    return list;
  }
  
  public GoodsDoctorPickIn findAllGoodsDoctorPickBySeqId(String seqId) throws Exception {
    GoodsDoctorPickIn goodsDoctorPickIn = (GoodsDoctorPickIn)this.dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN.findAllGoodsDoctorPickBySeqId", seqId);
    return goodsDoctorPickIn;
  }
  
  public void deleteGoodsDoctorPickByIncode(String incode) throws Exception {
    this.dao.delete("HUDH_GOOGS_DOCTOR_PICK_IN.deleteGoodsDoctorPickByIncode", incode);
  }
  
  public JSONObject findGoodsDoctorPickByIncode(String incode) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_GOOGS_DOCTOR_PICK_IN.findGoodsDoctorPickByIncode", incode);
    return json;
  }
  
  public void updateGoodsDoctorPickBySeqId(String seqId) throws Exception {
    this.dao.update("HUDH_GOOGS_DOCTOR_PICK_IN.updateGoodsDoctorPickBySeqId", seqId);
  }
}
