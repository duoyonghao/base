package com.kqds.service.base.giveItemGiveRecord;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class KQDS_GiveItem_GiveRecordLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 根据消费项目编号统计
	 * 
	 * @param conn
	 * @param itemnos
	 * @return
	 * @throws Exception
	 */
	public int getCountByItemnos(String itemnos) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_GIVEITEM_GIVERECORD + ".getCountByItemnos", YZUtility.ConvertStringIds4Query(itemnos));
		return count;
	}

	/**
	 * 根据会员卡号 查询赠送项目记录 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getGiveRecordByMemberno(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_GIVEITEM_GIVERECORD + ".selectWithPage", map);
		return list;
	}

}