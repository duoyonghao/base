package com.kqds.service.base.makeInvoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_makeInvoiceLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 删除开票系统 患者相关数
	 * 
	 * @param usercode
	 * @param dbConn
	 * @param usercode
	 * @param request
	 * @throws Exception
	 */
	public void deleteUserDoc(String usercode, String table) throws Exception {
		JSONObject json = new JSONObject();
		json.put("tablename", table);
		json.put("usercode", usercode);
		dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".deleteuser", json);
	}
}