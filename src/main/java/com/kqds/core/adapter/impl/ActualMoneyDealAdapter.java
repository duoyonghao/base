package com.kqds.core.adapter.impl;

import com.kqds.core.adapter.DealAdapter;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsReg;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ActualMoneyDealAdapter
  implements DealAdapter
{
  public boolean isDeal(KqdsReg reg, KqdsCostorder cost, List<KqdsCostorderDetail> list, HttpServletRequest request, Kqds_PayCostLogic logic, YZDictLogic dictLogic, KQDS_UserDocumentLogic userDocLogic)
    throws Exception
  {
    boolean dealStatus = false;
    

    boolean tsxmflag = false;
    for (KqdsCostorderDetail detail : list) {
      if ((detail.getIstsxm() == null) || (detail.getIstsxm().intValue() == 0)) {
        tsxmflag = true;
      }
    }
    if (tsxmflag)
    {
      BigDecimal ssje = userDocLogic.getSsjeOne(cost.getCostno());
      if (KqdsBigDecimal.compareTo(ssje, BigDecimal.ZERO) == 1) {
        dealStatus = true;
      }
    }
    return dealStatus;
  }
}
