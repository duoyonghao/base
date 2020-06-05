package com.kqds.service.base.netOrder;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class KQDS_Net_OrderLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private YZDeptLogic deptLogic;

	public KqdsNetOrder getNetOrderByWXOrderSeqId(String wxOrderSeqId) throws Exception {
		KqdsNetOrder order = (KqdsNetOrder) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getNetOrderByWXOrderSeqId", wxOrderSeqId);
		return order;
	}

	/**
	 * 查询该患者 当天是否存在预约，同一个患者当天不允许预约两次 【同一个患者的编辑操作，不算做重复预约】
	 * 
	 * @param conn
	 * @param seqId
	 * @param usercode
	 * @param ordertime
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> checkNetOrderCount(String seqId, String usercode, String ordertime) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("ordertime", ordertime);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".checkNetOrderCount", map);
		return list;
	}

	/**
	 * 获取患者的第一条网电记录，用于网电建档后，编辑档案时展示使用
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectFirstByUsercode(String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectFirstByUsercode", usercode);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public JSONObject selectNoPageYyzx(String table, Map<String, String> map, String visualstaff, String organization,BootStrapPage<T> bp,JSONObject json) throws Exception {
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if (map.containsKey("username")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
		}
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", visualstaff);
		}
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "n.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "n.username");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.PhoneNumber1");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("ordertime")){
				map.put("sortName", "n.ordertime");
			}
			if(map.get("sortName").equals("guidetime")){
				map.put("sortName", "n.guidetime");
			}
			if(map.get("sortName").equals("zdoorstatus")){
				map.put("sortName", "u.doorstatus");
			}
			if(map.get("sortName").equals("doorstatus")){
				map.put("sortName", "n.doorstatus");
			}
			if(map.get("sortName").equals("cjstatus")){
				map.put("sortName", "n.cjstatus");
			}
			if(map.get("sortName").equals("jdr")){
				map.put("sortName", "per4.user_name");
			}
			if(map.get("sortName").equals("jdsj")){
				map.put("sortName", "u.createtime");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "n.usercode");
			}
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "n.remark");
			}
		}
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectNoPageYyzx", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();	
		for (JSONObject job : list) {
			String cjstatus = job.getString("cjstatus");
			if ("1".equals(cjstatus)) {
				cjstatus = "已成交";
			} else {
				cjstatus = "未成交";
			}
			job.put("cjstatus", cjstatus);

			String zdoorstatus = job.getString("zdoorstatus");
			if ("1".equals(zdoorstatus)) {
				zdoorstatus = "已上门";
			} else {
				zdoorstatus = "未上门";
			}
			job.put("zdoorstatus", zdoorstatus);

			String doorstatus = job.getString("doorstatus");
			if ("1".equals(doorstatus)) {
				doorstatus = "已上门";
			} else {
				doorstatus = "未上门";
			}
			job.put("doorstatus", doorstatus);

		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);	

		return jobj;
	}
	
	/**
	 * 添加新方法，用于正畸部门查看网电信息  2019-6-21  syp
	  * @Title: selectNoPageYyzxNet   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param table
	  * @param: @param map
	  * @param: @param visualstaff
	  * @param: @param organization
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年6月21日 下午3:38:05
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectNoPageYyzxNet(String table, Map<String, String> map, String visualstaff, String organization, YZPerson person,BootStrapPage bp) throws Exception {
		JSONObject json = new JSONObject();
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if (map.containsKey("username")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
		}
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", visualstaff);
		}
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "n.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "n.username");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.PhoneNumber1");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("ordertime")){
				map.put("sortName", "n.ordertime");
			}
			if(map.get("sortName").equals("guidetime")){
				map.put("sortName", "n.guidetime");
			}
			if(map.get("sortName").equals("zdoorstatus")){
				map.put("sortName", "u.doorstatus");
			}
			if(map.get("sortName").equals("doorstatus")){
				map.put("sortName", "n.doorstatus");
			}
			if(map.get("sortName").equals("cjstatus")){
				map.put("sortName", "n.cjstatus");
			}
			if(map.get("sortName").equals("askitem")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("jdr")){
				map.put("sortName", "per4.user_name");
			}
			if(map.get("sortName").equals("jdsj")){
				map.put("sortName", "u.createtime");
			}
			if(map.get("sortName").equals("isdelete")){
				map.put("sortName", "n.isdelete");
			}
			if(map.get("sortName").equals("askcontent")){
				map.put("sortName", "n.askcontent");
			}
			
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "n.remark");
			}
			
		}
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		List<JSONObject> list1 = null;
		if ("a361daeb-be3c-4dab-a63d-3d718177c81e".equals(person.getSeqId())) {
			list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectNoPageYyzxNetNum", map);
		} else {
			list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectNoPageYyzxNum", map);
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		//---------------------------------------------------------------
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = null;
		if ("a361daeb-be3c-4dab-a63d-3d718177c81e".equals(person.getSeqId())) {
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectNoPageYyzxNet", map);
		} else {
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectNoPageYyzx", map);
		}
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();	
		for (JSONObject job : list) {
			String cjstatus = job.getString("cjstatus");
			if ("1".equals(cjstatus)) {
				cjstatus = "已成交";
			} else {
				cjstatus = "未成交";
			}
			job.put("cjstatus", cjstatus);
			
			String zdoorstatus = job.getString("zdoorstatus");
			if ("1".equals(zdoorstatus)) {
				zdoorstatus = "已上门";
			} else {
				zdoorstatus = "未上门";
			}
			job.put("zdoorstatus", zdoorstatus);
			
			String doorstatus = job.getString("doorstatus");
			if ("1".equals(doorstatus)) {
				doorstatus = "已上门";
			} else {
				doorstatus = "未上门";
			}
			job.put("doorstatus", doorstatus);
			
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);	
		jobj.put("nums", list1);
		return jobj;
	}
	
	

	// 网电预约-今日预约 只看本部门的（网电部门）
	@SuppressWarnings("unchecked")
	public JSONObject selectNoPageYyzxWd(String table, Map<String, String> map, String datetype, String organization, String visualstaff,BootStrapPage bp) throws Exception {
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if (map.containsKey("username")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
		}
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", YZUtility.ConvertStringIds4Query(visualstaff));
		}
		String wdPersonIds = personLogic.getPerIdsByDeptTypeNoOrg(datetype);
		map.put("wdPersonIds", YZUtility.ConvertStringIds4Query(wdPersonIds));
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("username")){
				map.put("sortName", "n.username");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.PhoneNumber1");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("ordertime")){
				map.put("sortName", "n.ordertime");
			}
			if(map.get("sortName").equals("guidetime")){
				map.put("sortName", "n.guidetime");
			}
			if(map.get("sortName").equals("zdoorstatus")){
				map.put("sortName", "u.doorstatus");
			}
			if(map.get("sortName").equals("doorstatus")){
				map.put("sortName", "n.doorstatus");
			}
			if(map.get("sortName").equals("cjstatus")){
				map.put("sortName", "n.cjstatus");
			}
			if(map.get("sortName").equals("askitem")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("askcontent")){
				map.put("sortName", "n.askcontent");
			}
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "n.remark");
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
		List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectNoPageYyzxWdNum", map);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectNoPageYyzxWd", map);
		for (JSONObject job : list) {
			String cjstatus = job.getString("cjstatus");
			if ("1".equals(cjstatus)) {
				cjstatus = "已成交";
			} else {
				cjstatus = "未成交";
			}
			job.put("cjstatus", cjstatus);

			String zdoorstatus = job.getString("zdoorstatus");
			if ("1".equals(zdoorstatus)) {
				zdoorstatus = "已上门";
			} else {
				zdoorstatus = "未上门";
			}
			job.put("zdoorstatus", zdoorstatus);

			String doorstatus = job.getString("doorstatus");
			if ("1".equals(doorstatus)) {
				doorstatus = "已上门";
			} else {
				doorstatus = "未上门";
			}
			job.put("doorstatus", doorstatus);

		}

		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		jobj1.put("nums", list1);
		return jobj1;
	}

	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	@SuppressWarnings("unchecked")
	public List selectWithNopage(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectWithNopage", map);
		return list;
	}

	/**
	 * 接待中心-网点预约列表调用 【只查询当前所在门诊，Organization直接从登录用户的session中获取】
	 * 
	 * @param conn
	 * @param table
	 * @param person
	 * @param querytype
	 * @param visualstaff
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectNetOrderList(String table, YZPerson person,Map<String,String> map1,BootStrapPage bp) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNullorEmpty(map1.get("querytype")) || map1.get("querytype").equals("null") || map1.get("querytype").equals("undefined")) {
			map.put("querytype", map1.get("visualstaff"));
		}
		map.put("organization", map1.get("organization"));
		if (!YZUtility.isNullorEmpty(map1.get("searchValue"))) {
			map.put("searchValue", map1.get("searchValue"));
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map1.get("searchValue")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map1.get("searchValue")));
		}
		if(!YZUtility.isNullorEmpty(map1.get("sortName"))){
			if(map1.get("sortName").equals("zdoorstatus")){
				map.put("sortName", "u.doorstatus");
			}
			if(map1.get("sortName").equals("doorstatus")){
				map.put("sortName", "n.doorstatus");
			}
			if(map1.get("sortName").equals("cjstatus")){
				map.put("sortName", "n.cjstatus");
			}
			if(map1.get("sortName").equals("ordertime")){
				map.put("sortName", "n.ordertime");
			}
			if(map1.get("sortName").equals("usercode")){
				map.put("sortName", "n.usercode");
			}
			if(map1.get("sortName").equals("username")){
				map.put("sortName", "n.username");
			}
			if(map1.get("sortName").equals("iscreatelclj")){
				map.put("sortName", "u.iscreateLclj");
			}
			if(map1.get("sortName").equals("acceptdate")){
				map.put("sortName", "n.acceptdate");
			}
			if(map1.get("sortName").equals("acceptphone")){
				map.put("sortName", "n.acceptphone");
			}
			if(map1.get("sortName").equals("accepttypename")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map1.get("sortName").equals("accepttoolname")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(map1.get("sortName").equals("askitemname")){
				map.put("sortName", "kcit4.dict_name");
			}
			if(map1.get("sortName").equals("regdeptname")){
				map.put("sortName", "odept2.dept_name");
			}
			if(map1.get("sortName").equals("askpersonname")){
				map.put("sortName", "per2.user_name");
			}
			if(map1.get("sortName").equals("orderpersonname")){
				map.put("sortName", "per1.user_name");
			}
			if(map1.get("sortName").equals("daoyiname")){
				map.put("sortName", "per3.user_name");
			}
			if(map1.get("sortName").equals("isdelete")){
				map.put("sortName", "n.isdelete");
			}
			map.put("sortOrder", map1.get("sortOrder"));
			
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectNetOrderList", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		if(bp.getOffset()==0){
			JSONObject list1 = (JSONObject) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".selectNetOrderListNum", map);
			if(list1!=null){
				jobj1.put("cjstatus", list1.getString("cjstatus"));
				jobj1.put("doorstatus", list1.getString("doorstatus"));
			}else{
				jobj1.put("cjstatus", 0);
				jobj1.put("doorstatus", 0);
			}
		}
		return jobj1;
	}
	
/*	private String checkIsAcceaptType(YZPerson person) throws Exception{
		List<YZDept> deptList = deptLogic.getDeptListBySeqIds(person.getDeptId());
		if(null != deptList && deptList.size() >= 1) {
			YZDept dept = deptList.get(0);
			if(dept.getDeptType().equals("0")) { //如果是咨询的部门类型则需要加上AcceaptType条件
				if(dept.getSeqId().equals(HUDHStaticVar.zzAskPersonDeptId)) { //种植咨询
					return HUDHStaticVar.zzAskPersonDictId;
				}else if(dept.getSeqId().equals(HUDHStaticVar.zjAskPersonDeptId)){
					return HUDHStaticVar.zjAskPersonDictId;
				}
			}
		} 
		return null;
	}*/
	
	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectListNoPage(String table, String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectListNoPage", usercode);
		return list;
	}

	/**
	 * 首页查询网电预约总条数
	 * 
	 * @param conn
	 * @param table
	 * @param person
	 * @param querytype
	 * @param visualstaff
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public int getCountIndex(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			map.put("querytype", visualstaff);
		}
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(searchValue)) {
			map.put("searchValue", searchValue);
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
		}
		int count = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountIndex", map);
		return count;
	}

	/**
	 * 统计预约人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountYy(String table, String date) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("starttime", date + ConstUtil.HOUR_START);
		map.put("endtime", date + ConstUtil.HOUR_END);
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountYy1", map);
		return nums;
	}

	/**
	 * 预约上门人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountYysm(String table, String date) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("starttime", date + ConstUtil.HOUR_START);
		map.put("endtime", date + ConstUtil.HOUR_END);
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountYysm1", map);
		return nums;
	}

	/**
	 * 预约上门率
	 * 
	 * @param conn
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Double getSml(String date) throws Exception {
		Double sml = 0.0;
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", date + ConstUtil.HOUR_START);
		map.put("endDate", date + ConstUtil.HOUR_END);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getSml", map);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			double tmpSml = YZUtility.isNullorEmpty(rs.getString("sml")) ? 0.0 : rs.getDouble("sml");
			sml = new BigDecimal(tmpSml).setScale(1, BigDecimal.ROUND_HALF_DOWN).doubleValue();
		}
		return sml;
	}

	/**
	 * 录单人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountJd(Map<String, String> map) throws Exception {
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountJd", map);
		return nums;
	}

	/**
	 * 统计预约人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountYy(Map<String, String> map) throws Exception {
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountYy2", map);
		return nums;
	}

	/**
	 * 预约上门人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountYysm(Map<String, String> map) throws Exception {
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountYysm", map);
		return nums;
	}

	/**
	 * 收款（项目）
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getYysr(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getYysr", map);
		String yysr = "0";
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
			BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0.00" : rs.getString("yjj"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
			paymoney = paymoney.subtract(yjj).subtract(zs).subtract(djq).subtract(integral);
			yysr = KqdsBigDecimal.round(paymoney, 2).toString();
		}
		return yysr;
	}

	/**
	 * 收款（预交金）
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getYysrYjj(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getYysrYjj", map);
		String yysr = "0.00";
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("cmoney")) ? "0.00" : rs.getString("cmoney"));
			yysr = KqdsBigDecimal.round(paymoney, 2).toString();
		}
		return yysr;
	}

	/**
	 * 预约上门人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountCj(Map<String, String> map) throws Exception {
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountCj", map);
		return nums;
	}
	
	/**
	 * 取消预约改变状态
	 * @param seqId
	 * @param isDelete
	 * @throws Exception
	 */
	public void updateIsdeleteById(String seqId,int isDelete) throws Exception{
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("seqId", seqId);
		dataMap.put("isdelete", isDelete);
		dao.update(TableNameUtil.KQDS_NET_ORDER + ".updateIsdeleteById", dataMap);
	}
}