package com.kqds.core.adapter.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

/**
 * 根据挂号分类、消费项目分类，判定是否为成交
 * 
 * @author Administrator
 * 
 */
public class ItemDealAdapter implements DealAdapter {
	@Override
	public boolean isDeal(KqdsReg reg, KqdsCostorder cost, List<KqdsCostorderDetail> list, HttpServletRequest request, Kqds_PayCostLogic logic, YZDictLogic dictLogic,
			KQDS_UserDocumentLogic userLogic) throws Exception {

		boolean dealStatus = false;

		// 验证三大项的成交状态
		boolean flagitem = checkCj(reg, list, request, logic, dictLogic);
		// 消费的项目是否全为特殊项目
		boolean tsxmflag = false; // false 表示全为特殊项目
//		for (KqdsCostorderDetail detail : list) {
//			if (detail.getIstsxm() == null || detail.getIstsxm() == 0) {// 如果全是特殊项目,还是未成交，这行代码的意思是：只要有一个不是特殊项目，flag=true
//				tsxmflag = true;
//			}
//		}
		/**
		 * start （收银修改费用单不做任何修改时保存再结账、成交状态判断  syp 2019-11-19）
		 */
		for (KqdsCostorderDetail detail : list) {
			if (detail.getIstsxm() == null || detail.getIstsxm() == 0) {// 如果全是特殊项目,还是未成交，这行代码的意思是：只要有一个不是特殊项目，flag=true
				tsxmflag = true;
			} else if (detail.getIstsxm() == null || detail.getIstsxm() == 1) {//添加else情况判断
				tsxmflag = true;
			}
		}
		/**
		 * end
		 */
		if (tsxmflag && flagitem) {
			dealStatus = true;
		}

		return dealStatus;
	}

	/**
	 * 验证三大项的成交状态
	 * 
	 * @param dbConn
	 * @param regno
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private boolean checkCj(KqdsReg reg, List<KqdsCostorderDetail> list, HttpServletRequest request, Kqds_PayCostLogic logic, YZDictLogic dictLogic) throws Exception {

		boolean flagitem = false;
		// 成交时 判断挂号分类是否是三大类 如果是三大类 并且开单项目中存在三大类的项目 才算成交
		String regsort = reg.getRegsort();
		// 获取挂号分类的三大类（正畸种植修复）的编号 ### 以逗号分隔
		String REG_SUCCESS_ITEM_SORT = SysParaUtil.getSysValueByName(request, SysParaUtil.REG_SUCCESS_ITEM_SORT);
		// 获取收费项目的三大类（正畸种植修复）的编号 ### 以逗号分隔
		String COST_SUCCESS_ITEM_SORT = SysParaUtil.getSysValueByName(request, SysParaUtil.COST_SUCCESS_ITEM_SORT);
		/** sys_para表存的是主键，需要转成dict_code，以便和消费项目的一级分类basetype比较 **/
		String COST_SUCCESS_ITEM_SORT_dictCode = dictLogic.getDictCodesBySeqIds(COST_SUCCESS_ITEM_SORT);

		// 3.系统内成交率的计算:咨询种植、正畸、修复项目之一，在种植、正畸、修复或刮治成交的算成交；
		String COST_SUCCESS_ITEM = SysParaUtil.getSysValueByName(request, SysParaUtil.COST_SUCCESS_ITEM);
		/** 由于sys_para表的存值改为dict_code，所以这里进行下转换，转成主键 yangsen 12.21 **/
		COST_SUCCESS_ITEM = dictLogic.getDictSeqIdsByDictCodes(COST_SUCCESS_ITEM);

		if (YZUtility.isStrInArrayEach(regsort, REG_SUCCESS_ITEM_SORT)) {
			/** 挂号时，挂号分类存主键值seqId，所以这里不需要调整 **/
			// 判断收费项目中是否存在三大类的项目
			for (KqdsCostorderDetail detail : list) {
				KqdsTreatitem item = logic.getItem(TableNameUtil.KQDS_TREATITEM, detail.getItemno());
				if (YZUtility.isStrInArrayEach(item.getBasetype(), COST_SUCCESS_ITEM_SORT_dictCode)) {
					flagitem = true;
					break;
				}
				// 不属于三大成交项目的收费项目，也算成交
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
