package com.kqds.service.base.printSet;

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
public class KQDS_Print_SetLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_PRINT_SET + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

}