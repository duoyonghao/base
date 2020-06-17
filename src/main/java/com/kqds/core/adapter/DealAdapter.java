package com.kqds.core.adapter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsReg;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.sys.dict.YZDictLogic;

/**
 * 结账时，判定是否成交的适配器
 * 
 * @author Administrator
 * 
 */
public interface DealAdapter {

	public boolean isDeal(KqdsReg reg, KqdsCostorder cost, List<KqdsCostorderDetail> list, HttpServletRequest request, Kqds_PayCostLogic logic, YZDictLogic dictLogic,
			KQDS_UserDocumentLogic userLogic) throws Exception;
}
