package com.kqds.service.base.paiban;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class KQDS_Paiban_TypeLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectWithPage(String table, BootStrapPage bp, String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_PAIBAN_TYPE + ".selectWithPage", organization);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	// 不分页
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(String table, Map<String, String> map, String organization) throws Exception {
		if (YZUtility.isNotNullOrEmpty(organization)) {
			map.put("organization", organization);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_PAIBAN_TYPE + ".selectList", map);
		return list;
	}
}