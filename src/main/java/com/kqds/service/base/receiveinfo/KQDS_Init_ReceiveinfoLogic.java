package com.kqds.service.base.receiveinfo;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsReceiveinfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Init_ReceiveinfoLogic
{
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveReceiveinfo(List<KqdsReceiveinfo> receList)
    throws Exception
  {
    this.dao.batchUpdate("KQDS_RECEIVEINFO.batchSaveReceiveinfo", receList);
  }
}
