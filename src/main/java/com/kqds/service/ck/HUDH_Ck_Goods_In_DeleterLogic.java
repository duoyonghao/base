package com.kqds.service.ck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsDeleter;

@Service
public class HUDH_Ck_Goods_In_DeleterLogic {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存信息
	 * @param dp
	 * @throws Exception
	 */
	public void saveDeletePersonInfor(KqdsCkGoodsDeleter dp) throws Exception {
		dao.save("KQDS_CK_GOODS_IN_DELETERE.saveDeletePersonInfor", dp);
	}
	
}
