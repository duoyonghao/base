package com.kqds.service.base.bcjl;

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
@Service("bcjlLogic")
public class KQDS_BCJLLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	@SuppressWarnings("rawtypes")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BCJL + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 不分页查询-流转记录 【查询所有，不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> selectWithPageLzjl(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BCJL + ".selectWithPageLzjl", map);
		return list;
	}

	public List<JSONObject> getListByJlName(String jlNmae) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BCJL + ".getListByJlName", jlNmae);
		return list;
	}

}