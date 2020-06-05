package com.kqds.service.base.costOrderPriceList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCostorderPriceList;
import com.kqds.service.sys.base.BaseLogic;
@Service
public class KQDS_CostOrderPriceListLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	/**
	 * 新增价目表明细
	 * @param list
	 * @throws Exception
	 */
	public void insertPriceList(List<KqdsCostorderPriceList> list) throws Exception{
		dao.batchUpdate("KQDS_COSTORDER_PRICE_LIST.insertPriceList", list);
	}
	/**
	 * 修改相关项目内容
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int updatePriceList(KqdsCostorderPriceList kqdsCostorderPriceList) throws Exception{
		int a=(int) dao.update("KQDS_COSTORDER_PRICE_LIST.updatePriceList", kqdsCostorderPriceList);
		return a;
	}
}
