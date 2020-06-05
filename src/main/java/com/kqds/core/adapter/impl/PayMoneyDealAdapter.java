package com.kqds.core.adapter.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kqds.core.adapter.DealAdapter;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsReg;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.other.KqdsBigDecimal;

/**
 * 根据挂号分类、消费项目分类，判定是否为成交
 * 
 * @author Administrator
 * 
 */
public class PayMoneyDealAdapter implements DealAdapter {

	@Override
	public boolean isDeal(KqdsReg reg, KqdsCostorder cost, List<KqdsCostorderDetail> list, HttpServletRequest request, Kqds_PayCostLogic logic, YZDictLogic dictLogic,
			KQDS_UserDocumentLogic userDocLogic) throws Exception {

		boolean dealStatus = false;

		// 消费的项目是否全为特殊项目
		boolean tsxmflag = false; // false 表示全为特殊项目
		for (KqdsCostorderDetail detail : list) {
			if (detail.getIstsxm() == null || detail.getIstsxm() == 0) {// 如果全是特殊项目,还是未成交，这行代码的意思是：只要有一个不是特殊项目，flag=true
				tsxmflag = true;
			}
		}

		if (tsxmflag) {
			// 非特殊项目，缴费大于0，算成交
			if (KqdsBigDecimal.compareTo(cost.getActualmoney(), BigDecimal.ZERO) == 1) {
				dealStatus = true;
			}
		}

		return dealStatus;
	}

}
