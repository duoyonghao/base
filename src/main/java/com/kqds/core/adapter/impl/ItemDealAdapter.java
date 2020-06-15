package com.kqds.core.adapter.impl;

import com.kqds.core.adapter.DealAdapter;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.SysParaUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ItemDealAdapter implements DealAdapter {
  public boolean isDeal(KqdsReg reg, KqdsCostorder cost, List<KqdsCostorderDetail> list, HttpServletRequest request, Kqds_PayCostLogic logic, YZDictLogic dictLogic, KQDS_UserDocumentLogic userLogic) throws Exception {
    boolean dealStatus = false;
    boolean flagitem = checkCj(reg, list, request, logic, dictLogic);
    boolean tsxmflag = false;
    for (KqdsCostorderDetail detail : list) {
      if (detail.getIstsxm() == null || detail.getIstsxm().intValue() == 0) {
        tsxmflag = true;
        continue;
      } 
      if (detail.getIstsxm() == null || detail.getIstsxm().intValue() == 1)
        tsxmflag = true; 
    } 
    if (tsxmflag && flagitem)
      dealStatus = true; 
    return dealStatus;
  }
  
  private boolean checkCj(KqdsReg reg, List<KqdsCostorderDetail> list, HttpServletRequest request, Kqds_PayCostLogic logic, YZDictLogic dictLogic) throws Exception {
    boolean flagitem = false;
    String regsort = reg.getRegsort();
    String REG_SUCCESS_ITEM_SORT = SysParaUtil.getSysValueByName(request, SysParaUtil.REG_SUCCESS_ITEM_SORT);
    String COST_SUCCESS_ITEM_SORT = SysParaUtil.getSysValueByName(request, SysParaUtil.COST_SUCCESS_ITEM_SORT);
    String COST_SUCCESS_ITEM_SORT_dictCode = dictLogic.getDictCodesBySeqIds(COST_SUCCESS_ITEM_SORT);
    String COST_SUCCESS_ITEM = SysParaUtil.getSysValueByName(request, SysParaUtil.COST_SUCCESS_ITEM);
    COST_SUCCESS_ITEM = dictLogic.getDictSeqIdsByDictCodes(COST_SUCCESS_ITEM);
    if (YZUtility.isStrInArrayEach(regsort, REG_SUCCESS_ITEM_SORT)) {
      for (KqdsCostorderDetail detail : list) {
        KqdsTreatitem item = logic.getItem(TableNameUtil.KQDS_TREATITEM, detail.getItemno());
        if (YZUtility.isStrInArrayEach(item.getBasetype(), COST_SUCCESS_ITEM_SORT_dictCode)) {
          flagitem = true;
          break;
        } 
        if (YZUtility.isStrInArrayEach(item.getNexttype(), COST_SUCCESS_ITEM)) {
          flagitem = true;
          break;
        } 
      } 
    } else {
      flagitem = true;
    } 
    return flagitem;
  }
}
