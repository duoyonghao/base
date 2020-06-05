package com.kqds.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_WorkLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	// 今日网电预约总条数
	@SuppressWarnings("unchecked")
	public List<JSONObject> getWdList(String table, int days, String visualstaff, String organization) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		sb.append(" and " + SQLUtil.dateDiffDay("n.ordertime", days)); // 该查询条件不受
		sb.append(" and " + SQLUtil.length("n.ordertime") + " >= 16 "); // 预约时间
		map.put("ordertime", sb.toString());
		map.put("visualstaff", visualstaff);
		map.put("organization", organization);

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getWdList", map);
		return list;
	}

	// 患者网电预约记录(这里不需要可见人员，因为能看到改患者，说明已经经过可见人员过滤过了)
	@SuppressWarnings("unchecked")
	public List<JSONObject> getWdListByUsercode(String table, String usercode, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getWdListByUsercode", map);
		return list;
	}

	// 患者门诊预约记录(这里不需要可见人员，因为能看到改患者，说明已经经过可见人员过滤过了)
	@SuppressWarnings("unchecked")
	public List<JSONObject> getMzListByUsercode(String table, String usercode, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzListByUsercode", map);
		return list;
	}

	// 今日门诊预约总条数
	@SuppressWarnings("unchecked")
	public List<JSONObject> getMzList(String table, int days, String visualstaff, String organization) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		sb.append(" and " + SQLUtil.dateDiffDay("n.starttime", days));
		map.put("starttime", sb.toString());
		map.put("visualstaff", visualstaff);
		map.put("organization", organization);

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzList", map);
		return list;
	}

	// 门诊预约查询
	@SuppressWarnings("unchecked")
	public JSONObject getMzListPage(String table, Map<String, String> map, String visualstaff, String organization) throws Exception {
		map.put("visualstaff", visualstaff);
		map.put("organization", organization);
		if (map.containsKey("search")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("search")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("search")));
		}
		int pageSize = Integer.parseInt(map.get("pageSize"));
		int nowpage = Integer.parseInt(map.get("nowpage"));
		map.put("num1", pageSize + "");
		map.put("num2", nowpage * pageSize + "");

		JSONObject jobj = new JSONObject();
		// 总记录数
		if (map.containsKey("ispaging")) {
			if (map.get("ispaging").equals("1")) {
				if (nowpage == 0) {
					Map<String, String> wheremap = new HashMap<>();
					int total = (int) dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzListPageTotal", map);
					jobj.put("total", total);
					// 已上门
					wheremap.put("n.orderstatus", "1");
					int cztotal = (int) dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzListPageTotal2", map);
					jobj.put("smtotal", cztotal);
				}
			}
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzListPage", map);
		jobj.put("data", list);
		return jobj;
	}

	// 今日患者
	public int getRegCount(String table, String visualstaff, String organization) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		sb.append(" and " + SQLUtil.dateDiffDay("r.createtime"));
		map.put("createtime", sb.toString());
		map.put("visualstaff", visualstaff);
		map.put("organization", organization);

		int count = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".getRegCount", map);
		return count;
	}

	// 今日患者
	@SuppressWarnings("unchecked")
	public JSONObject getRegList(String table, Map<String, String> map, String visualstaff, String organization) throws Exception {
		if (!map.containsKey("starttime") && !map.containsKey("search")) {
			map.put("createtime", "");
		}
		map.put("visualstaff", visualstaff);
		map.put("organization", organization);
		if (map.containsKey("search")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("search")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("search")));
		}
		int pageSize = Integer.parseInt(map.get("pageSize"));
		int nowpage = Integer.parseInt(map.get("nowpage"));
		map.put("num1", pageSize + "");
		map.put("num2", nowpage * pageSize + "");

		JSONObject jobj = new JSONObject();
		if (map.containsKey("ispaging")) {
			if (map.get("ispaging").equals("1")) {
				// 第一页 查询 总数 初诊数量
				if (nowpage == 0) {
					int total = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".getRegListTotal", map);
					jobj.put("total", total);
					// 初诊
					int cztotal = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".getRegListTotal2", map);
					jobj.put("cztotal", cztotal);
				}
			}
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getRegList", map);
		jobj.put("data", list);
		return jobj;
	}

	// 患者信息
	@SuppressWarnings("unchecked")
	public JSONObject selectUserInfo(String table, String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectUserInfo", usercode);
		return list.get(0);
	}

	// 今日回访
	@SuppressWarnings("unchecked")
	public List<JSONObject> getHfList(String table, String visualstaff, String organization) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		sb.append(" and " + SQLUtil.dateDiffDay("visittime"));
		map.put("visittime", sb.toString());
		map.put("visualstaff", visualstaff);
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getHfList", map);
		return list;
	}

	// 今日回访
	@SuppressWarnings("unchecked")
	public JSONObject getHfListPage(String table, Map<String, String> map, String visualstaff, String organization) throws Exception {
		map.put("visualstaff", visualstaff);
		map.put("organization", organization);
		int pageSize = Integer.parseInt(map.get("pageSize"));
		int nowpage = Integer.parseInt(map.get("nowpage"));
		map.put("num1", pageSize + "");
		map.put("num2", nowpage * pageSize + "");
		JSONObject jobj = new JSONObject();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT + ".getHfListPage", map);
		jobj.put("data", list);
		return jobj;
	}

	// 患者回访
	@SuppressWarnings("unchecked")
	public List<JSONObject> getHfListByUsercode(String table, String usercode, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("organization", organization);

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getHfListByUsercode", map);
		return list;
	}

	// 患者费用详情
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCostListByUsercode(String table, String usercode, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("organization", organization);

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getCostListByUsercode", map);
		return list;
	}
}