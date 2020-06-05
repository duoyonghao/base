package com.kqds.service.wx.news;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class WXNewsItemLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_NEWSITEM + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getList(String newsid) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_NEWSITEM + ".getList", newsid);
		return list;
	}

	public int getCountByNewsId(String newsid) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.WX_NEWSITEM + ".getCountByNewsId", newsid);
		return count;
	}

	public int deleteByParentId(String newsid, HttpServletRequest request) throws Exception {
		int count = (int) dao.delete(TableNameUtil.WX_NEWSITEM + ".deleteByParentId", newsid);
		return count;
	}

}
