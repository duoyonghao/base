package com.kqds.service.base.reg;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsReg;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_InitRegLogic
{
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveReg(List<KqdsReg> regList)
    throws Exception
  {
    this.dao.batchUpdate("KQDS_REG.batchSaveReg", regList);
  }
}
