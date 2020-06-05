package com.kqds.service.base.hospitalOrder;

//合并测试
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class KQDS_Hospital_OrderLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 获取患者当天最晚的预约记录
	 * 
	 * @param conn
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getTopHosOrderCurrDay(String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getTopHosOrderCurrDay", usercode);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	// 预约查询
	@SuppressWarnings("unchecked")
	public JSONObject selectNoPage(String table, Map<String, String> map, String visualstaff,BootStrapPage bp,JSONObject json) throws Exception {
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", visualstaff);
		}
		if (!YZUtility.isNullorEmpty(map.get("username"))) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
		}
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "h.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "h.username");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.PhoneNumber1");
			}
			if(map.get("sortName").equals("firstaskperson")){
				map.put("sortName", "per5.user_name");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("orderitemtype")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("zdoorstatus")){
				map.put("sortName", "u.doorstatus");
			}
			if(map.get("sortName").equals("orderstatus")){
				map.put("sortName", "h.orderstatus");
			}
			if(map.get("sortName").equals("cjstatus")){
				map.put("sortName", "h.cjstatus");
			}
			if(map.get("sortName").equals("starttime")){
				map.put("sortName", "h.starttime");
			}
			if(map.get("sortName").equals("endtime")){
				map.put("sortName", "h.endtime");
			}
			
			if(map.get("sortName").equals("ordertime")){
				map.put("sortName", "h.ordertime");
			}
			if(map.get("sortName").equals("isdelete")){
				map.put("sortName", "h.isdelete");
			}
			if(map.get("sortName").equals("cjr")){
				map.put("sortName", "per3.user_name");
			}
			if(map.get("sortName").equals("jdr")){
				map.put("sortName", "per4.user_name");
			}
			if(map.get("sortName").equals("jdsj")){
				map.put("sortName", "u.createtime");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "u.usercode");
			}
		}
		//String search = bp.getSearch();
		//String sort = bp.getSort();
		//json.put("search", search);
		//json.put("sort", sort);
		List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectNoPageNum", map);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectNoPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();	
		for (JSONObject job : list) {
			String cjstatus = job.getString("cjstatus");
			if (ConstUtil.CJ_STATUS_1.equals(cjstatus)) {
				cjstatus = ConstUtil.CJ_STATUS_1_DESC;
			} else {
				cjstatus = ConstUtil.CJ_STATUS_0_DESC;
			}
			job.put("cjstatus", cjstatus);

			String zdoorstatus = job.getString("zdoorstatus");
			if (ConstUtil.DOOR_STATUS_1.equals(zdoorstatus)) {
				zdoorstatus = ConstUtil.DOOR_STATUS_1_DESC;
			} else {
				zdoorstatus = ConstUtil.DOOR_STATUS_0_DESC;
			}
			job.put("zdoorstatus", zdoorstatus);

			String orderstatus = job.getString("orderstatus");
			if (ConstUtil.DOOR_STATUS_1.equals(orderstatus)) {
				orderstatus = ConstUtil.DOOR_STATUS_1_DESC;
			} else {
				orderstatus = ConstUtil.DOOR_STATUS_0_DESC;
			}
			job.put("orderstatus", orderstatus);

			String isdelete = job.getString("isdelete");
			if (ConstUtil.YY_ORDER_STATUS_1.equals(isdelete)) {
				isdelete = ConstUtil.YY_ORDER_STATUS_1_DESC;
			} else {
				isdelete = ConstUtil.YY_ORDER_STATUS_0_DESC;
			}
			job.put("isdelete", isdelete);

		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		jobj.put("nums", list1);
		return jobj;
	}

	// 预约查询
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectYyxxByUsercode(String table, String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectYyxxByUsercode", usercode);
		for (JSONObject job : list) {
			String cjstatus = job.getString("cjstatus");
			if (ConstUtil.CJ_STATUS_1.equals(cjstatus)) {
				cjstatus = ConstUtil.CJ_STATUS_1_DESC;
			} else {
				cjstatus = ConstUtil.CJ_STATUS_0_DESC;
			}
			job.put("cjstatus", cjstatus);

			String zdoorstatus = job.getString("zdoorstatus");
			if (ConstUtil.DOOR_STATUS_1.equals(zdoorstatus)) {
				zdoorstatus = ConstUtil.DOOR_STATUS_1_DESC;
			} else {
				zdoorstatus = ConstUtil.DOOR_STATUS_0_DESC;
			}
			job.put("zdoorstatus", zdoorstatus);

			String orderstatus = job.getString("orderstatus");
			if (ConstUtil.DOOR_STATUS_1.equals(orderstatus)) {
				orderstatus = ConstUtil.DOOR_STATUS_1_DESC;
			} else {
				orderstatus = ConstUtil.DOOR_STATUS_0_DESC;
			}
			job.put("orderstatus", orderstatus);

			String isdelete = job.getString("isdelete");
			if (ConstUtil.YY_ORDER_STATUS_1.equals(isdelete)) {
				isdelete = ConstUtil.YY_ORDER_STATUS_1_DESC;
			} else {
				isdelete = ConstUtil.YY_ORDER_STATUS_0_DESC;
			}
			job.put("isdelete", isdelete);

		}
		return list;
	}

	/**
	 * 查询门诊预约列表，系统首页 和 接待中心-门诊预约列表调用 【只查询当前所在门诊，Organization直接从登录用户的session中获取】
	 * 
	 * @param conn
	 * @param table
	 * @param person
	 * @param querytype
	 * @param visualstaff
	 * @param organization
	 * @param json 
	 * @param bp 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectHospitalOrderList(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization,Map<String, String> map,BootStrapPage bp) throws Exception {
		//Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			map.put("visualstaff", visualstaff);
		}
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(searchValue)) {
			map.put("searchValue", searchValue);
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
		}
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("orderstatus")){
				map.put("sortName", "h.orderstatus");
			}
			if(map.get("sortName").equals("starttime")){
				map.put("sortName", "h.starttime");
			}
			if(map.get("sortName").equals("endtime")){
				map.put("sortName", "h.endtime");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "h.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "h.username");
			}
			if(map.get("sortName").equals("ordertypename")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(map.get("sortName").equals("askpersonname")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("orderitemtypename")){
				map.put("sortName", "kcit1.dict_name");
			}
		}
		//String search = bp.getSearch();
		//String sort = bp.getSort();
		//json.put("search", search);
		//json.put("sort", sort);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectHospitalOrderList", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		if(bp.getOffset()==0){
			JSONObject list1 = (JSONObject) dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectHospitalOrderListNum", map);
			if(list1!=null){
				jobj.put("cjs", list1.get("cjs"));
				jobj.put("sms", list1.get("sms"));
			}
		}
		jobj.put("offset", bp.getOffset());
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);	
		return jobj;
	}

	/**
	 * 首页查询预约总条数
	 * 
	 * @param conn
	 * @param person
	 * @param querytype
	 * @param searchValue
	 * @param visualstaff
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public int getCountIndex(String querytype, String searchValue, String visualstaff, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			map.put("visualstaff", visualstaff);
		}
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(searchValue)) {
			map.put("searchValue", searchValue);
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
		}
		int count = (int) dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getCountIndex", map);
		return count;
	}

	/**
	 * 查询预约列表 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param doctors
	 * @param startime1
	 * @param endTime1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> yycx(String doctors, String startime1, String endTime1) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("doctors", doctors);
		map.put("startime1", startime1);
		map.put("endTime1", endTime1);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".yycx", map);
		return list;
	}

	/**
	 * 查询预约列表
	 * 
	 * @param conn
	 * @param usercode
	 * @param startime1
	 * @param endTime1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> yycxUser(String usercode, String startime1, String endTime1) throws Exception {
		/**
		 * 判断某个患者当日的预约时间段是否出现重叠 特殊情况1：比如患者预约了10-11点的，那么可以预约11-12点的
		 * 特殊情况2：基于情况下，要去掉完全重叠的这个情况
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("startime1", startime1);
		map.put("endTime1", endTime1);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".yycxUser", map);
		return list;
	}

	/**
	 * 日历 ---不查看取消预约
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @param visualstaff
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(String table, Map<String, String> map, String visualstaff, String organization) throws Exception {
		map.put("visualstaff", visualstaff);
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectList", map);
		return list;
	}

	/**
	 * 判断医生是否下班 或者休息
	 * 
	 * @param conn
	 * @param map
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public int checkYy(Map<String, String> map) throws Exception {
		if (map.containsKey("starttime")) {
			if (map.get("starttime").toString().length() == 10) {
				map.put("starttimeShort", map.get("starttime") + ConstUtil.TIME_START);
			} else {
				map.put("starttimeLong", map.get("starttime").substring(0, 10));
			}
		}
		int count = (int) dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".checkYy", map);
		return count;
	}

	/**
	 * 判断医生是否下班 或者休息 或者其他
	 */
	@SuppressWarnings("unchecked")
	public JSONObject orderstatusString(Map<String, String> map) throws Exception {
		map.put("starttime", map.get("starttime").substring(0, 10));
		JSONObject jobj = new JSONObject();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".orderstatusString", map);
		if (list != null && list.size() > 0) {
			jobj = list.get(0);
		}
		return jobj;
	}

	/**
	 * 查询该患者 当天是否存在预约 不做门诊条件过滤
	 * 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> checkHz(Map<String, String> map) throws Exception {
		map.put("starttime", map.get("starttime").substring(0, 10));
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".checkHz", map);
		return list;
	}

	/**
	 * 预约日历，预约人员列表
	 * 
	 * @param conn
	 * @param map
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectPerson(Map<String, String> map, String organization, String visualstaffYyrl) throws Exception {
		map.put("visualstaffYyrl", visualstaffYyrl);
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectPerson", map);
		return list;
	}

	/**
	 * 预约上门率
	 * 
	 * @param conn
	 * @param date
	 * @param ghfl
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Double getSml(String date, String yytype, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime", date + ConstUtil.TIME_END);
		if (!YZUtility.isNullorEmpty(yytype)) {
			map.put("yytype", yytype);
		}
		map.put("organization", organization);

		Double sml = 0.0;
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getSml", map);
		if (list != null && list.size() > 0) {
			double tmpSml = YZUtility.isNullorEmpty(list.get(0).getString("sml")) ? 0.0 : list.get(0).getDouble("sml");
			sml = new BigDecimal(tmpSml).setScale(1, BigDecimal.ROUND_HALF_DOWN).doubleValue();
		}
		return sml;
	}

	/**
	 * 统计预约人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @param ghfl
	 * @return
	 * @throws Exception
	 */
	public int getCountYy(String table, String date, String yytype, String type, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime", date + ConstUtil.TIME_END);
		if (!YZUtility.isNullorEmpty(yytype)) {
			map.put("yytype", yytype);
		}
		map.put("organization", organization);
		if ("del".equals(type)) {
			map.put("isdelete", "1");
		}
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getCountYy", map);
		return nums;
	}

	/**
	 * 预约上门人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @param ghfl
	 * @return
	 * @throws Exception
	 */
	public int getCountYysm(String table, String date, String yytype, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime", date + ConstUtil.TIME_END);
		if (!YZUtility.isNullorEmpty(yytype)) {
			map.put("yytype", yytype);
		}
		map.put("organization", organization);
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getCountYysm", map);
		return nums;
	}

	/**
	 * 判断所选咨询、人员在特定时间段内是否有预约
	 * 
	 * @param conn
	 * @param doctors
	 * @param startime1
	 * @param endTime1
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	public int coutYYPerson(String doctors, String startime1, String endTime1, String seqId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("doctors", doctors);
		map.put("starttime", startime1);
		map.put("endtime", endTime1);
		if (!YZUtility.isNullorEmpty(seqId)) {
			map.put("seqId", seqId);
		}
		int count = (int) dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".coutYYPerson", map);
		return count;
	}
	
	/**
	 * 根据临床路径编号和节点id查询预约信息 2019-7-31 syp
	  * @Title: findHospitalOrderByOrdernumAndnodeId   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param order_number
	  * @param: @param nodeId
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年7月31日 下午3:16:54
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findHospitalOrderByOrdernumAndnodeId(String order_number, String nodeId) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("order_number", order_number);
		dataMap.put("nodeId", nodeId);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".findHospitalOrderByOrdernumAndnodeId", dataMap);
		return list;
	}
	
	
	public List<JSONObject> selectHospitalOrderByUsercodeAndTime(Map<String,String> map) throws Exception{
		List<JSONObject> list=(List<JSONObject>)dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectHospitalOrderByUsercodeAndTime", map);
		return list;
	}
}