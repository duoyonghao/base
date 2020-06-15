package com.kqds.service.base.hzjd;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsUserdocument;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_InitUserdocumentLogic
{
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveUserDocument(List<KqdsUserdocument> list)
    throws Exception
  {
    this.dao.batchUpdate("KQDS_USERDOCUMENT.batchSaveUserDocument", list);
  }
}
