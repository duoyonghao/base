package com.kqds.core.adapter;

import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsReg;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface DealAdapter {
  boolean isDeal(KqdsReg paramKqdsReg, KqdsCostorder paramKqdsCostorder, List<KqdsCostorderDetail> paramList, HttpServletRequest paramHttpServletRequest, Kqds_PayCostLogic paramKqds_PayCostLogic, YZDictLogic paramYZDictLogic, KQDS_UserDocumentLogic paramKQDS_UserDocumentLogic) throws Exception;
}
