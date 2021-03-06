package com.kqds.service.wx.templatemsg;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXTemplatemsg;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class WXTemplateMsgLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectPage(BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_TEMPLATEMSG + ".selectPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		if (map.containsKey("flag")) {
			/** 导出的时候转换 **/
			for (JSONObject fans : list) {
				String status = fans.getString("status");
				if ("0".equals(status)) {
					fans.put("statusname", "正常");
				}
				if ("1".equals(status)) {
					fans.put("statusname", "禁用");
				}
			}
		}

		jobj.put("rows", list);
		return jobj;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_TEMPLATEMSG + ".selectPage", map);
		return list;
	}

	public WXTemplatemsg getWXTemplateMsgById(String templateid, HttpServletRequest request) throws Exception {
		WXTemplatemsg temp = (WXTemplatemsg) dao.findForObject(TableNameUtil.WX_TEMPLATEMSG + ".getWXTemplateMsgById", templateid);
		return temp;
	}

}
