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
public class KQDS_Sms_ModelLogic extends BaseLogic {
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
	public List<JSONObject> noSelectWithPage(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_SMS_MODEL + ".getListsql", map);
		for (JSONObject job : list) {
			String isdsmodel = job.getString("isdsmodel");
			if (isdsmodel.equals("1")) {
				job.put("isdsmodelname", "是");
			} else {
				job.put("isdsmodelname", "否");
			}
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_SMS_MODEL + ".getListsql", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		for (JSONObject job : list) {
			String isdsmodel = job.getString("isdsmodel");
			if (isdsmodel.equals("1")) {
				job.put("isdsmodelname", "是");
			} else {
				job.put("isdsmodelname", "否");
			}
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 查询定时短信模板
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getDsmodel(String smsnexttype) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_SMS_MODEL + ".getDsmodel", smsnexttype);
		return list;
	}

}