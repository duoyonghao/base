package com.kqds.service.base.changeReceive;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_changeWdLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

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
	public List<JSONObject> selectWithPage(String table, Map<String, String> map) throws Exception {
		if (map.containsKey("starttime")) {
			map.put("starttime", map.get("starttime") + ConstUtil.TIME_START);
		}
		if (map.containsKey("endtime")) {
			map.put("endtime", map.get("endtime") + ConstUtil.TIME_END);
		}

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CHANGE_WD + ".selectWithPage", map);
		return list;
	}
}