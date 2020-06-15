package com.kqds.service.base.costOrderPriceList;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCostorderPriceList;
import com.kqds.service.sys.base.BaseLogic;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_CostOrderPriceListLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public void insertPriceList(List<KqdsCostorderPriceList> list)
    throws Exception
  {
    this.dao.batchUpdate("KQDS_COSTORDER_PRICE_LIST.insertPriceList", list);
  }
  
  public int updatePriceList(KqdsCostorderPriceList kqdsCostorderPriceList)
    throws Exception
  {
    int a = ((Integer)this.dao.update("KQDS_COSTORDER_PRICE_LIST.updatePriceList", kqdsCostorderPriceList)).intValue();
    return a;
  }
}
