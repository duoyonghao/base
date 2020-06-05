package com.kqds.service.wx.order;

import java.sql.SQLException;
import java.util.HashMap;
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
public class WXOrderLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_ORDER + ".selectList", map);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_ORDER + ".selectPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	public JSONObject selectDetail(String table, String seqId) throws Exception {
		JSONObject jobj = (JSONObject) dao.findForObject(TableNameUtil.WX_ORDER + ".selectDetail", seqId);
		return jobj;
	}

	/**
	 * 检查当天预约次数
	 * 
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public int countToday(String openid, String orderdate, HttpServletRequest request) throws SQLException, Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);
		map.put("orderdate", orderdate.trim());
		int count = (int) dao.findForObject(TableNameUtil.WX_ORDER + ".countToday", map);
		return count;
	}

}
