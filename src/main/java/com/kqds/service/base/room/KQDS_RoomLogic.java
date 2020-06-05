package com.kqds.service.base.room;

//合并测试
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsRoom;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
@Service
public class KQDS_RoomLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	// 查询手术室预约列表
	public int countRoom(Map<String, String> map, String seqId) throws Exception {
		if (!YZUtility.isNullorEmpty(seqId)) {
			map.put("seqId", seqId);
		}
		// 不存在 所要查询的 患者、医生、护士、手术室 直接返回
		if (!map.containsKey("roomid") && !map.containsKey("doctor") && !map.containsKey("nurse") && !map.containsKey("usercode")) {
			return 0;
		}
		int count = (int) dao.findForObject(TableNameUtil.KQDS_ROOM + ".countRoom", map);
		return count;
	}

	// 查询手术室预约列表
	@SuppressWarnings("unchecked")
	public List checkRoom(Map map) throws Exception {
		// 不存在 所要查询的 患者、医生、护士、手术室 直接返回
		if (!map.containsKey("roomid") && !map.containsKey("doctor") && !map.containsKey("nurse") && !map.containsKey("usercode")) {
			return new ArrayList<JSONObject>();
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_ROOM + ".checkRoom2", map);
		return list;
	}

	// 查询门诊预约列表
	@SuppressWarnings("unchecked")
	public KqdsHospitalOrder checkOrderUsercode(Map map) throws Exception {
		KqdsHospitalOrder order = new KqdsHospitalOrder();
		// 不存在 所要查询的 患者、医生、护士 直接返回
		if (!map.containsKey("usercode") && !map.containsKey("doctor") && !map.containsKey("nurse")) {
			return order;
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_ROOM + ".checkOrderUsercode", map);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			order.setUsercode(rs.getString("usercode"));
			order.setUsername(rs.getString("username"));
			order.setAskperson(rs.getString("askperson"));
			order.setStarttime(rs.getString("starttime"));
			order.setEndtime(rs.getString("endtime"));
		}
		return order;
	}

	// 查询门诊预约列表
	@SuppressWarnings("unchecked")
	public KqdsRoom checkSSSUsercode(Map map) throws Exception {
		KqdsRoom order = new KqdsRoom();
		// 不存在 所要查询的 患者、医生、护士 直接返回
		if (!map.containsKey("usercode") && !map.containsKey("doctor")) {
			return order;
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_ROOM + ".checkSSSUsercode", map);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			order.setUsercode(rs.getString("usercode"));
			order.setNurse(rs.getString("nurse"));
			order.setDoctor(rs.getString("doctor"));
			order.setStarttime(rs.getString("starttime"));
			order.setEndtime(rs.getString("endtime"));
		}
		return order;
	}

	// 初始化手术室预约页面查询
	@SuppressWarnings("unchecked")
	public List selectList(String table, Map<String, String> map, String organization) throws Exception {
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_ROOM + ".selectList", map);
		return list;
	}
	
	

	// 预约查询
	@SuppressWarnings("unchecked")
	public JSONObject selectNoPage(String table, Map<String, String> map, String visualstaff, String jrroom, String searchValue, BootStrapPage bp, JSONObject json) throws Exception {
		if (map.containsKey("username")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
		}
		map.put("jrroom", jrroom);
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", visualstaff);
		}
//------------------------------------------------------------------------首页搜索框-------------------------------------------------------------------------------	
		if (!YZUtility.isNullorEmpty(searchValue)) {
			map.put("searchValue", searchValue);
		}
		if(map.get("sortName") != null){
			if(map.get("sortName").equals("roomname")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("roomstatusname")){
				map.put("sortName", "r.roomstatus");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "r.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "u.username");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.PhoneNumber1");
			}
			if(map.get("sortName").equals("doctorname")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("zzxtname")){
				map.put("sortName", "zzxtname");
			}
			if(map.get("sortName").equals("ks")){
				map.put("sortName", "r.ks");
			}
			if(map.get("sortName").equals("starttime")){
				map.put("sortName", "r.starttime");
			}
			if(map.get("sortName").equals("endtime")){
				map.put("sortName", "r.endtime");
			}
			if(map.get("sortName").equals("isdeletename")){
				map.put("sortName", "r.isdelete");
			}
			if(map.get("sortName").equals("createusername")){
				map.put("sortName", "per5.user_name");
			}
			if(map.get("sortName").equals("jdr")){
				map.put("sortName", "per4.user_name");
			}
			if(map.get("sortName").equals("jdsj")){
				map.put("sortName", "u.createtime");
			}
			if(map.get("sortName").equals("nursename")){
				map.put("sortName", "per2.user_name");
			}
			if(map.get("sortName").equals("zzxtname")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "r.remark");
			}
			if(map.get("sortName").equals("delpersonname")){
				map.put("sortName", "per3.user_name");
			}
			if(map.get("sortName").equals("roomname")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "r.usercode");
			}
			if(map.get("sortName").equals("ks")){
				map.put("sortName", "r.ks");
			}
		}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_ROOM + ".selectNoPage", map);
		
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();	
		
		for (JSONObject job : list) {
			String roomstatusname = job.getString("roomstatus");
			if ("0".equals(roomstatusname)) {
				roomstatusname = "手术前";
			} else if ("1".equals(roomstatusname)) {
				roomstatusname = "手术中";
			} else {
				roomstatusname = "手术后";
			}
			job.put("roomstatusname", roomstatusname);
			String isdeletename = job.getString("isdelete");
			if ("1".equals(isdeletename)) {
				isdeletename = "已取消";
			} else {
				isdeletename = "正常";
			}
			job.put("isdeletename", isdeletename);
		}
		
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);	
		
		return jobj;
	}
	// 预约查询
		@SuppressWarnings("unchecked")
		public List<JSONObject> selectNoPages(String table, Map<String, String> map,  String searchValue, String visualstaff,String jrroom) throws Exception {
			if(map!=null){
				if (map.containsKey("username")) {
					map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
					map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
				}
				map.put("jrroom", jrroom);
			}
			if (!YZUtility.isNullorEmpty(visualstaff)) {
				map.put("visualstaff", visualstaff);
			}
			if(!YZUtility.isNullorEmpty(searchValue)){
				map.put("searchValue", searchValue);
			}
			List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_ROOM + ".selectNoPage", map);
			for (JSONObject job : list) {
				String roomstatusname = job.getString("roomstatus");
				if ("0".equals(roomstatusname)) {
					roomstatusname = "手术前";
				} else if ("1".equals(roomstatusname)) {
					roomstatusname = "手术中";
				} else {
					roomstatusname = "手术后";
				}
				job.put("roomstatusname", roomstatusname);
				String isdeletename = job.getString("isdelete");
				if ("1".equals(isdeletename)) {
					isdeletename = "已取消";
				} else {
					isdeletename = "正常";
				}
				job.put("isdeletename", isdeletename);
			}
			return list;
		}
	// 首页查询今日手术总条数
	public int getCountIndex(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization) throws Exception {
		int count = 0;
		Map<String, String> map = new HashMap<String, String>();
		map.put("organization", organization);
		if (person.getUserPriv().equals("1")) {// 总经理看所有
			visualstaff = "";
		}
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
				map.put("querytype", visualstaff);
			}
		}
		if(!YZUtility.isNullorEmpty(searchValue)){
			map.put("searchValue", searchValue);
		}
		try {
			count = (int) dao.findForObject(TableNameUtil.KQDS_ROOM + ".getCountIndex", map);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
	
	//根据seqId查询手术室预约信息
	public KqdsRoom selectByPrimaryKey(String seqId) throws Exception {
		KqdsRoom kqdsRoom = (KqdsRoom) dao.findForObject(TableNameUtil.KQDS_ROOM + ".selectByPrimaryKey", seqId);
		return kqdsRoom;
	}
}