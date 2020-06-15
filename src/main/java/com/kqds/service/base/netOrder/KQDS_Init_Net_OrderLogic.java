package com.kqds.service.base.netOrder;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsNetOrder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Init_Net_OrderLogic
{
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveNetOrder(List<KqdsNetOrder> list)
    throws Exception
  {
    this.dao.batchUpdate("KQDS_NET_ORDER.batchSaveNetOrder", list);
  }
}
