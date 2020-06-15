package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import com.kqds.dao.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOrderTrackDeleteDao {
  @Autowired
  private DaoSupport dao;
  
  public void save(LcljOrderTrackDeleteRecord dp) throws Exception {
    this.dao.save("HUDH_LCLJ_ORDERTRACK_DELETE_RECORD.save", dp);
  }
}
