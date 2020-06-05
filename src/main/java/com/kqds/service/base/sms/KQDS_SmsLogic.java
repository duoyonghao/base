package com.kqds.service.base.sms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_SmsLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 不分页
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> noSelectWithPage(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_SMS + ".getListsql", map);
		for (JSONObject job : list) {
			String smsstate = job.getString("smsstate");
			if ("1".equals(smsstate)) {
				smsstate = "定时";
			} else {
				smsstate = "实时";
			}
			job.put("smsstatename", smsstate);

			String sendstate = job.getString("sendstate");
			if ("1".equals(sendstate)) {
				sendstate = "已发送";
			} else {
				sendstate = "未发送";
			}
			job.put("sendstatename", sendstate);
		}
		return list;
	}

	/**
	 * 分页
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_SMS + ".getListsql", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		for (JSONObject job : list) {
			String smsstate = job.getString("smsstate");
			if ("1".equals(smsstate)) {
				smsstate = "定时";
			} else {
				smsstate = "实时";
			}
			job.put("smsstatename", smsstate);

			String sendstate = job.getString("sendstate");
			if ("1".equals(sendstate)) {
				sendstate = "已发送";
			} else {
				sendstate = "未发送";
			}
			job.put("sendstatename", sendstate);
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
}