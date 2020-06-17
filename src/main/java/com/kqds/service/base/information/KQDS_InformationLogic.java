package com.kqds.service.base.information;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service
public class KQDS_InformationLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings("rawtypes")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> filter) throws Exception {
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		map.put("params", filter);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_INFORMATION + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	public List<JSONObject> selectList(String table, String searchinput, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("searchinput", searchinput);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_INFORMATION + ".selectList", json);
		return list;
	}
}