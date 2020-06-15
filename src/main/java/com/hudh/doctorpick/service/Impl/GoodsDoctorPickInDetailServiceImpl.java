package com.hudh.doctorpick.service.Impl;

import com.hudh.doctorpick.dao.GoodsDoctorPickInDetailDao;
import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;
import com.hudh.doctorpick.service.IGoodsDoctorPickInDetailService;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsDoctorPickInDetailServiceImpl
  implements IGoodsDoctorPickInDetailService
{
  @Autowired
  private GoodsDoctorPickInDetailDao detailDao;
  
  public void batchSaveInDetail(List<GoodsDoctorPickInDetail> list) {}
  
  public List<JSONObject> findDoctorPickInDetailByIncode(String incode, String visualstaff)
    throws Exception
  {
    List<JSONObject> list = this.detailDao.findDoctorPickInDetailByIncode(incode, visualstaff);
    return list;
  }
  
  public void updateGoodsDoctorPickInDetail(String seq_id)
    throws Exception
  {
    this.detailDao.updateGoodsDoctorPickInDetail(seq_id);
  }
  
  public void deleteDoctorPickInDetailBySeqId(String seqId)
    throws Exception
  {
    this.detailDao.deleteDoctorPickInDetailBySeqId(seqId);
  }
  
  public GoodsDoctorPickInDetail findDoctorPickInDetailBySeqId(String seqId)
    throws Exception
  {
    GoodsDoctorPickInDetail goodsDoctorPickInDetail = this.detailDao.findDoctorPickInDetailBySeqId(seqId);
    return goodsDoctorPickInDetail;
  }
  
  public void deleteDoctorPickInDetailByIncode(String incode)
    throws Exception
  {
    this.detailDao.deleteDoctorPickInDetailByIncode(incode);
  }
  
  public JSONObject findDoctorPickInDetailById(String seqId)
    throws Exception
  {
    JSONObject json = this.detailDao.findDoctorPickInDetailById(seqId);
    return json;
  }
}
