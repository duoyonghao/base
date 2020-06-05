package com.kqds.service.app;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service("logLogic")
public class KQDS_LogLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	// 推送消息
	@SuppressWarnings("unchecked")
	public JSONObject getPushListPage(String table, Map<String, String> map) throws Exception {
		int pageSize = Integer.parseInt(map.get("pageSize"));
		int nowpage = Integer.parseInt(map.get("nowpage"));
		map.put("num1", pageSize + "");
		map.put("num2", nowpage * pageSize + "");

		JSONObject jobj = new JSONObject();
		// 总记录数
		if (map.containsKey("ispaging")) {
			if (map.get("ispaging").equals("1")) {
				if (nowpage == 0) {
					int total = (int) dao.findForObject(TableNameUtil.KQDS_PUSH + ".pushNoread", null);
					jobj.put("noreadtotal", total);
					// 已读
					int cztotal = (int) dao.findForObject(TableNameUtil.KQDS_PUSH + ".pushIsread", null);
					jobj.put("readtotal", cztotal);
				}
			}
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_PUSH + ".getPushListPage", map);
		jobj.put("data", list);
		return jobj;
	}

	// 系统消息
	@SuppressWarnings("unchecked")
	public JSONObject getSysListPage(String table, Map<String, String> map) throws Exception {
		int pageSize = Integer.parseInt(map.get("pageSize"));
		int nowpage = Integer.parseInt(map.get("nowpage"));
		map.put("num1", pageSize + "");
		map.put("num2", nowpage * pageSize + "");
		JSONObject jobj = new JSONObject();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_PUSH + ".getSysListPage", map);
		jobj.put("data", list);
		return jobj;
	}

	// 系统消息
	public void updatePcpushreaded(String noreadSeqId) throws Exception {
		dao.update(TableNameUtil.KQDS_PUSH + ".updatePcpushreaded", noreadSeqId);
	}
}