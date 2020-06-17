package com.kqds.service.base.receiveinfo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_ReceiveInfoLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 判断该挂号对应的就诊情况是否已经填写
	 * 
	 * @param dbConn
	 * @param regseqId
	 * @return
	 * @throws Exception
	 */
	public int countReceiveByRegNo(String regseqId) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_RECEIVEINFO + ".countReceiveByRegNo", regseqId);
		return count;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> noSelectWithPage(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_RECEIVEINFO + ".noSelectWithPage", map);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getNoUsercode(String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_RECEIVEINFO + ".getNoUsercode", usercode);
		return list;
	}

	public void deleteByrecno(String outprocessingsheetno) throws Exception {
		dao.delete(TableNameUtil.KQDS_RECEIVEINFO + ".deleteByrecno", outprocessingsheetno);
	}
}