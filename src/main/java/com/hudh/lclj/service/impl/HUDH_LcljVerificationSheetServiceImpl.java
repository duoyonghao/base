package com.hudh.lclj.service.impl;

import com.hudh.lclj.dao.LcljVerificationSheetDao;
import com.hudh.lclj.entity.LcljApprovedMemo;
import com.hudh.lclj.service.HUDH_LcljVerificationSheetService;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HUDH_LcljVerificationSheetServiceImpl implements HUDH_LcljVerificationSheetService {
  @Autowired
  private LcljVerificationSheetDao lSheetDao;
  
  @Transactional
  public void insert(LcljApprovedMemo bean) throws Exception {
    this.lSheetDao.insert(bean);
  }
  
  @Transactional
  public void Update(LcljApprovedMemo bean) throws Exception {
    List<LcljApprovedMemo> list = this.lSheetDao.findCheckRecordByOrderAndStatus(bean);
    for (LcljApprovedMemo lcljApprovedMemo : list)
      this.lSheetDao.updateCheckRecordBySeqId(lcljApprovedMemo); 
    this.lSheetDao.Update(bean);
  }
  
  @Transactional
  public List<JSONObject> getCheckRecord(Map<String, String> map, YZPerson person) throws Exception {
    if (person.getDeptId().equals("adea811e-a70c-46b0-a950-2f5727bd2fe8"))
      return this.lSheetDao.findLcljCheckrecordById(map); 
    if (person.getDeptId().equals("45df337f-8687-4c0b-aa29-95e8b4a59d5d"))
      return this.lSheetDao.findLcljCheckrecordById(map); 
    if (person.getDeptId().equals("4c4d0f09-6b5d-471f-80fb-4b2b92aaa7ae"))
      return this.lSheetDao.findLcljCheckrecordById(map); 
    return this.lSheetDao.findLcljCheckrecord(map);
  }
  
  @Transactional
  public void delCheckRecord(String id) throws Exception {
    this.lSheetDao.delCheckRecord(id);
  }
  
  public Integer getCheckRecordNum(Map<String, String> map) throws Exception {
    return this.lSheetDao.getCheckRecordNum(map);
  }
  
  public Integer getAwaitVerifieNum(Map<String, String> map) throws Exception {
    return this.lSheetDao.getAwaitVerifieNum(map);
  }
  
  public List<JSONObject> getAwaitCheckRecord(Map<String, String> map, YZPerson person) throws Exception {
    return this.lSheetDao.getAwaitCheckRecord(map);
  }
}
