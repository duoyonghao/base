package com.kqds.service.base.tooth;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class KQDS_Tooth_DocLogic extends BaseLogic {
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
	public List<JSONObject> selectWithPageByRegno(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TOOTH_DOC + ".selectWithPageByRegno", map);
		return list;
	}

	/**
	 * 查询会诊记录
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectHzjl(String table, Map<String, String> map, String visualstaff, String organization) throws Exception {
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		map.put("starttime", map.get("starttime") + ConstUtil.TIME_START);
		map.put("endtime", map.get("endtime") + ConstUtil.TIME_END);
		if (!map.containsKey("createuser")) {
			map.put("visualstaff", visualstaff);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TOOTH_DOC + ".selectHzjl", map);
		return list;
	}
}