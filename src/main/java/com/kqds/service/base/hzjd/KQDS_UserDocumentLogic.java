package com.kqds.service.base.hzjd;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.area.service.IAreaService;
import com.hudh.area.service.ICityService;
import com.hudh.area.service.IProviceService;
import com.hudh.area.service.IStreetService;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import com.kqds.entity.base.KqdsChangeKefu;
import com.kqds.entity.base.KqdsChangeWd;
import com.kqds.entity.base.KqdsJdrchange;
import com.kqds.entity.base.KqdsLabel;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.KqdsUserdocumentMergeRecord;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.label.KQDS_hz_LabellPatientLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.base.member.KQDS_MemberLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
@Service
public class KQDS_UserDocumentLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_MemberLogic memberLogic;
	
	@Autowired
	private UserdocumentMergeRecordLogic mergeLogic;//添加合并档案logic
	
	@Autowired
	private IProviceService iProviceService;//添加省份查询service
	
	@Autowired
	private ICityService iCityService;
	
	@Autowired
	private IAreaService iAreaService;
	
	@Autowired
	private IStreetService isService;
	
	@Autowired
	private YZDictLogic yzDictlogic;//添加字典表dao  2019-8-20 syp
	
	@Autowired
	private KQDS_CostOrderLogic costOrderLogic;//添加费用表dao  2019-8-20 syp
	
	@Autowired
	private KQDS_hz_LabellPatientLogic labellPatientLogic;//标签业务数据dao
	
	@Autowired
	private KQDS_hz_labelLogic labelLogic;//患者标签配置dao
	
	@Autowired
	private KQDS_REGLogic logic;
	
	@Autowired
	private YZDictLogic dictLogic;
	
	/**
	 * 获取病历号
	 * 
	 * @param conn
	 * @return
	 * @throws 	
	 */
	public String getBlcode(String blcode_start) throws Exception {
		int num = 0;
		int start = Integer.parseInt(blcode_start.trim());// 系统跟新前 定一下开始病历号
															// 防止和之前的病历号重复
		int maxblcode = getMaxBlcode(TableNameUtil.KQDS_USERDOCUMENT);
		if (maxblcode == 0) {
			num = start;
		} else {
			if (maxblcode < start) {
				num = start;
			} else {
				num = maxblcode;
			}
		}
		num++;// 记录加1
		return String.valueOf(num);
	}

	public int getMaxBlcode(String table) throws Exception {
		int maxblcode = 0;
		String blcode = "";
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getmaxblcode", blcode);
		if (json != null && YZUtility.isNotNullOrEmpty(json.getString("maxblcode"))) {
			maxblcode = json.getInt("maxblcode");
		}
		return maxblcode;
	}

	/**
	 * 根据手机号码获取用户信息
	 * 
	 * @param dbConn
	 * @param seqId
	 * @param map
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public KqdsUserdocument getSingUserByPhoneNumber(String phonenumber) throws Exception {
		phonenumber = YZAuthenticator.encryKqdsPhonenumber(phonenumber);
		KqdsUserdocument user = (KqdsUserdocument) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getSingUserByPhoneNumber", phonenumber);
		return user;

	}

	/**
	 * 根据手机号码获取姓名和患者编号
	 * 
	 * @param dbConn
	 * @param phonenumber
	 * @return
	 * @throws Exception
	 */
	public JSONObject getSingUserNameAndCodeByPhoneNumber(String phonenumber) throws Exception {
		phonenumber = YZAuthenticator.encryKqdsPhonenumber(phonenumber);
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getSingUserNameAndCodeByPhoneNumber", phonenumber);
		return json;

	}

	/**
	 * 患者列表(无拼音码)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectNoPymList(Connection conn) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectNoPymList", null);
		return list;
	}

	/*************************** 从yzutility类中整理过来 ****************************/

	/**
	 * 设置患者档案表的咨询人员
	 * 
	 * @param uercode
	 * @param askperson
	 * @param dbConn
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void setUserDocAskPerson(HttpServletRequest request, String usercode, String askperson) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);

		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		List<KqdsUserdocument> userlist = (List<KqdsUserdocument>) dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);

		if (userlist == null || userlist.size() == 0) {
			throw new Exception("此患者不存在，患者编号为：" + usercode);
		}

		if (userlist.size() > 1) {
			throw new Exception("数据异常，同一个编号对应多个患者，患者编号为：" + usercode);
		}
		KqdsUserdocument user = userlist.get(0);

		if (0 != user.getIsdelete()) { // 0 未删除 1已删除
			throw new Exception("该患者档案数据状态异常，患者编号为：" + usercode);
		}

		if (YZUtility.isNullorEmpty(askperson)) {
			throw new Exception("咨询人员seqId值为空");
		}

		YZPerson ask = (YZPerson) dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, askperson);
		if (ask == null) {
			throw new Exception("咨询人员不存在，seqId值为：" + askperson);
		}

		if (YZUtility.isNullorEmpty(user.getAskperson())) {

			StringBuffer loggerMsg = new StringBuffer();
			loggerMsg.append("### 设置患者档案表的咨询人员");
			loggerMsg.append("### 操作人：").append(person.getUserId());
			loggerMsg.append("### 操作时间：").append(YZUtility.getCurDateTimeStr());
			loggerMsg.append("### 设定咨询人：").append(ask.getUserId());

			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.SET_ASKPERSON, BcjlUtil.KQDS_USERDOCUMENT, loggerMsg.toString(), user.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);

			user.setAskperson(askperson);
			dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
		}
	}

	/**
	 * 合并患者档案表 【内部调用】
	 * 
	 * @param user1
	 * @param user2
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public KqdsUserdocument user1ToUser2(KqdsUserdocument user1, KqdsUserdocument user2) throws Exception {
		Field[] fields = user1.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			// 过滤不需要覆盖的字段
			if (fieldName.equals("seqId") || fieldName.equals("usercode") || fieldName.equals("username")) {
				continue;
			}
			// user1的值
			Class tCls = user1.getClass();
			Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
			Object user1value = getMethod.invoke(user1, new Object[] {});
			// user2的值
			Class tCls2 = user2.getClass();
			Method getMethod2 = tCls2.getMethod(getMethodName, new Class[] {});
			Object user2value = getMethod2.invoke(user2, new Object[] {});
			// user1赋值到user2
			Method setMethod2 = tCls2.getMethod(setMethodName, new Class[] { field.getType() });
			if (fieldName.equals("totalpay") || fieldName.equals("integral")) {// 实收金额
																				// 、积分相加
				BigDecimal totalpay1 = new BigDecimal(user1value.toString());
				BigDecimal totalpay2 = new BigDecimal(user2value.toString());
				setMethod2.invoke(user2, KqdsBigDecimal.add(totalpay1, totalpay2));
			} else {
				if (user1value != null && user2value == null) {
					setMethod2.invoke(user2, user1value);
				}
			}
		}
		// 特殊字段（如患者1合并到患者2，如患者1、患者2各有1个电话号码1，合并后，若是患者2的号码2为空则取患者1的电话号码1）
		if (YZUtility.isNullorEmpty(user2.getPhonenumber2())) {
			user2.setPhonenumber2(user1.getPhonenumber1());
		}
		// 特殊字段（如患者1合并到患者2，如患者1、患者2存在已上门 则合并后也为已上门）
		if (1 == user1.getDoorstatus()) {
			user2.setDoorstatus(user1.getDoorstatus());
		}
		if(user1.getIscreateLclj() != null && !user1.getIscreateLclj().equals("") && user1.getIscreateLclj().equals("1")){
			mergeMessage(user1.getUsercode(),user2.getUsercode());
			user2.setIscreateLclj("1");
		}
		return user2;
	}

	/**
	 * 根据患者编号获取患者信息
	 * 
	 * @param usercode
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public KqdsUserdocument getSingleUserByUsercode(String usercode) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode); // usercode是全局唯一的
		map.put("isdelete", "0");
		List<KqdsUserdocument> list = (List<KqdsUserdocument>) dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);

		if (list == null || list.size() == 0) {
			throw new Exception("数据异常，患者不存在");
		}

		if (list != null && list.size() > 1) { // 确保只能查出来一条记录
			throw new Exception("数据异常，一个编号对应多个患者");
		}

		return list.get(0);

	}

	/**
	 * 根据患者编号获取基本信息
	 * 
	 * @param dbConn
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getBaseUserInfoByUsercode(String userCode) throws Exception {
		int count = countByUserCode(userCode);
		if (count > 1) { // 确保只能查出来一条记录
			throw new Exception("数据异常，一个编号对应多个患者");
		}

		List<JSONObject> mlist = memberLogic.getMemberListByUserCode(userCode);
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal givemoney = BigDecimal.ZERO;
		int discount = 100;
		for (JSONObject mobj : mlist) {
			String tmpMoney = mobj.getString("money");
			if (!YZUtility.isNullorEmpty(tmpMoney)) {
				money = money.add(new BigDecimal(tmpMoney));
			}
			String tmpGiveMoney = mobj.getString("givemoney");
			if (!YZUtility.isNullorEmpty(tmpGiveMoney)) {
				givemoney = givemoney.add(new BigDecimal(tmpGiveMoney));
			}
			// 获取最大折扣（在折扣截止日期之前）
			String discountStr = mobj.getString("discount");
			String discountdate = mobj.getString("discountdate");
			boolean flag = false;
			if (YZUtility.isNullorEmpty(discountdate)) {
				flag = true;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (new Date().getTime() <= sdf.parse(discountdate + " 23:59:59").getTime()) {
					flag = true;
				}
			}

			if (Integer.parseInt(discountStr) < discount && flag) {
				discount = Integer.parseInt(discountStr);
			}
		}

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getBaseUserInfoByUsercode", userCode);
		if (list.size() == 0) {
			throw new Exception("患者不存在，编号为：" + userCode);
		}
		JSONObject userinfo = list.get(0);
		/**
		 * 判断省市区四级联动更改后赋值情况 start syp 
		 */
		int introducerNum = this.findIntroducerNum(userCode);
		userinfo.put("introducerNum", introducerNum);
		if(userinfo.get("hobby")!=null){
			//解决爱好显示
			Object ah = userinfo.get("hobby");
			String hobby = ah.toString();
			String[] hobbyArray = hobby.split(";");
			String hobbyName = "";
			for (int i = 0; i < hobbyArray.length; i++) {
				JSONObject json = yzDictlogic.findDictByDictCode(hobbyArray[i]);
				if (json == null) {
					break;
				}
				hobbyName = hobbyName + json.get("dictName").toString() + ";";
			}
			userinfo.replace("hobby", hobbyName);//替换成爱好的名称
		}
		String activity = userinfo.getString("activity");
		if (activity != null) {
			JSONObject json = yzDictlogic.findDictByDictCode(activity);
			if (json != null) {
				userinfo.replace("activity", json.getString("dictName"));//替换成爱好的名称
			}
		}
		Object province = userinfo.get("province");
		String provinceCode = province.toString();
		Object city = userinfo.get("city");
		String cityCode = city.toString();
		Object area = userinfo.get("area");
		String areaCode = area.toString();
		Object town = userinfo.get("town");
		String townCode = town.toString();
		if (provinceCode.length() == 6) {
			JSONObject provice = iProviceService.findProviceByProviceCode(provinceCode);
			Object provinceName = provice.get("provinceName");
			userinfo.put("provinceName", provinceName);
		}
		if (cityCode.length() == 6) {
			JSONObject provice = iCityService.findCityByCityCode(cityCode);
			Object cityName = provice.get("cityName");
			userinfo.put("cityName", cityName);
		}
		if (areaCode.length() == 6) {
			JSONObject provice = iAreaService.findAreaByAreaCode(areaCode);
			Object areaName = provice.get("areaName");
			userinfo.put("areaName", areaName);
		}
		if (townCode.length() == 9) {
			JSONObject provice = isService.findStreetByTownCode(townCode);
			Object townName = provice.get("streetName");
			userinfo.put("townName", townName);
		}
		/**
		 * end
		 */
		userinfo.put("money", KqdsBigDecimal.round(money, 2).toString()); // 确保只能查出来一条记录
		userinfo.put("givemoney", KqdsBigDecimal.round(givemoney, 2).toString()); // 确保只能查出来一条记录
		userinfo.put("discount", discount);
		List<kqdsHzLabellabeAndPatient> findForList = (List<kqdsHzLabellabeAndPatient>) dao.findForList(TableNameUtil.KQDS_Hz_LabellabeAndPatient+".findLabelList", userCode);
		userinfo.put("labelList", findForList);
		return userinfo;
	}

	public int countByUserCode(String userCode) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".countByUserCode", userCode);
		return count;

	}

	@SuppressWarnings("unchecked")
	public String selectDoctorByusercode(String table, String usercode) throws Exception {
		String doctor = "";
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectDoctorByusercode", usercode);
		if (list != null && list.size() > 0) {
			doctor = list.get(0).getString("doctor");
		}
		return doctor;
	}

	@SuppressWarnings("unchecked")
	public String selectusercodeByusername(String table, String username) throws Exception {
		String usercode = "";
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectusercodeByusername", username);
		if (list != null && list.size() > 0) {
			usercode = list.get(0).getString("usercode");
		}
		return usercode;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getMaxUserCode(String table, String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getMaxUserCode", organization);
		return list;
	}

	/**
	 * 不分页查询 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectWithNopage(String table, Map<String, String> map, String typechoose, String visualstaff, YZPerson person) throws Exception {
		if (map.containsKey("PhoneNumber1")) {
			map.put("p1", YZAuthenticator.phonenumberLike("PhoneNumber1", map.get("PhoneNumber1")));
			map.put("p2", YZAuthenticator.phonenumberLike("PhoneNumber2", map.get("PhoneNumber1")));
		}
		// 为了避免该患者 指定给医生时，医生在预约界面查询不到该患者
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopage", map);
		/**
		 * 返回患者缴费信息，便于前台建档筛选介绍人  2019-8-23 syp start
		 */
		for (JSONObject jsonObject : list) {
			String usercode = jsonObject.getString("usercode");
			JSONObject json = costOrderLogic.findCostOrderByUsercode(usercode);
			if (json != null) {
				jsonObject.put("totalcost", json.getString("totalcost"));
				jsonObject.put("doctorname", json.getString("doctorname"));
				jsonObject.put("askpersonname", json.getString("askpersonname"));
				jsonObject.put("repairdoctorname", json.getString("repairdoctorname"));
			}
		} 
		/**
		 * end
		 */
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectWithNopageReg(String table, Map<String, String> map, String typechoose, String visualstaff, YZPerson person) throws Exception {
		if (map.containsKey("PhoneNumber1")) {
			map.put("p1", YZAuthenticator.phonenumberLike("PhoneNumber1", map.get("PhoneNumber1")));
			map.put("p2", YZAuthenticator.phonenumberLike("PhoneNumber2", map.get("PhoneNumber1")));
		}
		// 为了避免该患者 指定给医生时，医生在预约界面查询不到该患者
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopageReg", map);
		return list;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectWithNopageGh(String table, Map<String, String> map) throws Exception {
		if (map.containsKey("PhoneNumber1")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("PhoneNumber1")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("PhoneNumber1")));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopageGh", map);
		return list;
	}
	
	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectWithNopageGhLike(String table, Map<String, String> map,BootStrapPage bp) throws Exception {
		if (map.containsKey("PhoneNumber1")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("PhoneNumber1")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("PhoneNumber1")));
		}
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopageGhLike", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);;
		return jobj;
	}
	
	/**
	 * 咨询创建临床路径按权限查找患者  2019-7-30  syp
	  * @Title: selectWithNopageGhPermission   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param table
	  * @param: @param map
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年7月30日 上午10:21:47
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectWithNopageGhPermission(String table, Map<String, String> map) throws Exception {
		if (map.containsKey("PhoneNumber1")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("PhoneNumber1")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("PhoneNumber1")));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopageGhPermission", map);
		return list;
	}

	//
	/**
	 * 查询关联人 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param usercodes
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectByUserCodes(String table, String usercodes) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectByUserCodes", usercodes);
		return list;
	}

	/**
	 * 首页 按钮 信息查询 【不支持跨院】
	 * 
	 * @param conn
	 * @param table
	 * @param visualstaff
	 * @param map
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectWithNopage2(BootStrapPage bp, String table, String visualstaff, Map<String, String> map, String organization,JSONObject json,String flag) throws Exception {
		map.put("organization", organization);
		if (map.containsKey("username")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
		}
		if (map.containsKey("usercodes")) {
			map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		}
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", visualstaff);
		}
		if(map.get("sortName") !=null){
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "u.createtime");
			}
			if(map.get("sortName").equals("lasttime")){
				map.put("sortName", "(select top 1 CreateTime from KQDS_REG reg where reg.usercode=u.UserCode ORDER BY CreateTime desc)");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "u.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "u.username");
			}
			if(map.get("sortName").equals("sex")){
				map.put("sortName", "u.sex");
			}
			if(map.get("sortName").equals("age")){
				map.put("sortName", "u.age");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "per2.user_name");
			}
			if(map.get("sortName").equals("doctor")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			if(map.get("sortName").equals("devchannelname")){
				map.put("sortName", "okdi1.dict_name");
			}
			if(map.get("sortName").equals("nexttypename")){
				map.put("sortName", "hztype.dict_name");
			}
			if(map.get("sortName").equals("createuser")){
				map.put("sortName", "per4.user_name");
			}
			if(map.get("sortName").equals("ywhf")){
				map.put("sortName", "(select count(v.usercode) from KQDS_Visit v where v.usercode=u.UserCode)");
			}
			if(map.get("sortName").equals("provincename")){
				map.put("sortName", "a1.area_name");
			}
			if(map.get("sortName").equals("cityname")){
				map.put("sortName", "a2.area_name");
			}
			if(map.get("sortName").equals("townname")){
				map.put("sortName", "a3.area_name");
			}
			if(map.get("sortName").equals("phonenumber2")){
				map.put("sortName", "u.phonenumber2");
			}
		}
		
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		if (flag != null && flag.equals("exportTable")) {
			List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopage3", map);
			for (JSONObject job : list) {
				String ywhf = job.getString("ywhf");
				if ("0".equals(ywhf)) {
					ywhf = "无回访";
				} else {
					ywhf = "有回访";
				}
				job.put("ywhf", ywhf);
				String doorstatus = job.getString("doorstatus");
				if ("1".equals(doorstatus)) {
					doorstatus = "已上门";
				} else {
					doorstatus = "未上门";
				}
				job.put("doorstatusname", doorstatus);
				String proviceName = job.getString("provincename");
				if(proviceName.equals("")) {
					job.replace("provincename", job.getString("province_name"));
				}
				String citname = job.getString("cityname");
				if(citname.equals("")) {
					job.replace("cityname", job.getString("city_name"));
				}
				String towname = job.getString("townname");
				if(towname.equals("")) {
					job.replace("townname", job.getString("area_name"));
				}
			}
			PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
			JSONObject jobj = new JSONObject();
			jobj.put("total", pageInfo.getTotal());
			jobj.put("rows", list);
			return jobj;
		}else{
			List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopage2", map);
			StringBuilder str= new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				String ywhf = list.get(i).getString("ywhf");
				if ("0".equals(ywhf)) {
					ywhf = "无回访";
				} else {
					ywhf = "有回访";
				}
				list.get(i).put("ywhf", ywhf);
				String doorstatus = list.get(i).getString("doorstatus");
				if ("1".equals(doorstatus)) {
					doorstatus = "已上门";
				} else {
					doorstatus = "未上门";
				}
				list.get(i).put("doorstatusname", doorstatus);
				if(i==list.size()-1){
					str.append("\'"+list.get(i).getString("usercode")+"\'");
				}else{
					str.append("\'"+list.get(i).getString("usercode")+"\',");
				}
				list.get(i).put("streetName", "");
				list.get(i).put("provincename", "");
				list.get(i).put("cityname", "");
				list.get(i).put("townname", "");
				list.get(i).put("askperson", "");
				list.get(i).put("doctor", "");
				list.get(i).put("devchannelname", "");
				list.get(i).put("nexttypename", "");
				list.get(i).put("createuser", "");
			}
			if(str.length()>0){
				Map<String,String> map1= new HashMap<String,String>();
				map1.put("usercode", str.toString());
				List<JSONObject> areaList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".findAreaByUsercode", map1);
				if(areaList.size()>0){
					for (JSONObject jsonObject : list) {
						for (JSONObject jsonObject1 : areaList) {
							if(jsonObject.getString("usercode").equals(jsonObject1.getString("usercode"))){
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("province_name"))) {
									jsonObject.put("provincename", jsonObject1.getString("province_name"));
								}else{
									jsonObject.put("provincename", jsonObject1.getString("provincename"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("city_name"))) {
									jsonObject.put("cityname", jsonObject1.getString("city_name"));
								}else{
									jsonObject.put("cityname", jsonObject1.getString("cityname"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("area_name"))) {
									jsonObject.put("townname", jsonObject1.getString("area_name"));
								}else{
									jsonObject.put("townname", jsonObject1.getString("townname"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("street_name"))){
									jsonObject.put("streetName", jsonObject1.getString("street_name"));
								}
							}
						}
					}
				}
				List<JSONObject> nameList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".findXxcxNameByUsercode", map1);
				if(nameList.size()>0){
					for (JSONObject jsonObject : list) {
						for (JSONObject jsonObject1 : nameList) {
							if(jsonObject.getString("usercode").equals(jsonObject1.getString("usercode"))){
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("createuser"))) {
									jsonObject.put("createuser", jsonObject1.getString("createuser"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("askperson"))) {
									jsonObject.put("askperson", jsonObject1.getString("askperson"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("doctor"))) {
									jsonObject.put("doctor", jsonObject1.getString("doctor"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("devchannelname"))){
									jsonObject.put("devchannelname", jsonObject1.getString("devchannelname"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("nexttypename"))){
									jsonObject.put("nexttypename", jsonObject1.getString("nexttypename"));
								}
							}
						}
					}
				}
			}
			PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
			JSONObject jobj = new JSONObject();
			jobj.put("total", pageInfo.getTotal());
			jobj.put("rows", list);
			return jobj;
		}
	}

	/**
	 * 身份证号验证 【不做门诊条件过滤】
	 * 
	 * @param dbConn
	 * @param map
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public int checkIdcardnoBySeqIdAndIdcardno(Map<String, String> map, String table) throws Exception {
		int num = (int) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".checkIdcardnoBySeqIdAndIdcardno", map);
		return num;
	}

	/**
	 * 手机号码验证 【不做门诊条件过滤】
	 * 
	 * @param dbConn
	 * @param seqId
	 * @param phonenumber1
	 * @param phonenumber2
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public int checkphonenumber(String seqId, Map<String, String> map, String table) throws Exception {
		if (!YZUtility.isNullorEmpty(seqId)) {
			map.put("seqId", seqId);
		}
		if (map.containsKey("phonenumber1")) {
			map.put("phonenumber1", YZAuthenticator.encryKqdsPhonenumber(map.get("phonenumber1")));
		}
		if (map.containsKey("phonenumber2")) {
			map.put("phonenumber2", YZAuthenticator.encryKqdsPhonenumber(map.get("phonenumber2")));
		}
		int num = (int) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".checkphonenumber", map);
		return num;
	}
	
	/**
	 * 根据手机号查询患者档案表数据 2019-7-24 syp
	 * @throws Exception 
	 */
	public List<KqdsUserdocument> selectUserdocumentByPhonenumber(String phoneNumber) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("phoneNumber", phoneNumber);
		@SuppressWarnings("unchecked")
		List<KqdsUserdocument> list = (List<KqdsUserdocument>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectUserdocumentByPhonenumber", dataMap);
		return list;
	}

	/**
	 * 病例号验证
	 */
	public int checkBlcode(String seqId, String blcode, String table) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(seqId)) {
			map.put("seqId", seqId);
		}
		if (!YZUtility.isNullorEmpty(blcode)) {
			map.put("blcode", blcode);
		}
		int num = (int) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".checkBlcode", map);
		return num;
	}

	/**
	 * 通用 根据usercode 修改姓名 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param usercode
	 * @param oldname
	 * @param newname
	 * @param table
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int updateUserName(String usercode, String oldname, String newname, String table, int type, HttpServletRequest request) throws Exception {
		JSONObject json = new JSONObject();
		json.put("usercode", usercode);
		json.put("username", newname);
		json.put("tablename", table);
		int num = 0;
		if (table.equals(TableNameUtil.KQDS_MEMBER)) {
			num = (int) dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateFlagBySeqIds1", json);
		} else if (table.equals("KQDS_MEMBER_RECORD_SZR")) {
			num = (int) dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateFlagBySeqIds2", json);
		} else {
			if (type == 1) {
				num = (int) dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateFlagBySeqIds3", json);
			} else {
				num = (int) dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateFlagBySeqIds4", json);
			}
		}
		return num;
	}

	/**
	 * 通用 根据usercode 修改姓名usercode、username 患者合并 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param usercode1
	 * @param usercode2
	 * @param username2
	 * @param table
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int updateUser(String usercode1, String usercode2, String username2, String table, int type, HttpServletRequest request) throws Exception {
		JSONObject json = new JSONObject();
		json.put("usercode2", usercode2);
		json.put("usercode1", usercode1);
		json.put("username2", username2);
		json.put("tablename", table);
		int num = 0;
		if (table.equals(TableNameUtil.KQDS_COSTORDER_DETAIL) || table.equals(TableNameUtil.KQDS_MEDICALRECORD) || table.equals(TableNameUtil.KQDS_OUTPROCESSING_SHEET)
				|| table.equals(TableNameUtil.KQDS_REFUND_DETAIL) || table.equals(TableNameUtil.KQDS_TOOTH_DOC) || table.equals(TableNameUtil.KQDS_BCJL)) {
			num = (int) dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateUser1", json);
		} else {
			if (type == 1) {
				num = (int) dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateUser2", json);
			}
		}
		return num;
	}

	/**
	 * 获取有收费记录的患者编号
	 * 
	 * @param conn
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getSfuser() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSfuser", null);
		String bqs = "";
		for (JSONObject rs : list) {
			bqs += rs.getString("usercode") + ",";
		}

		if (bqs.endsWith(",")) {
			bqs = bqs.substring(0, bqs.length() - 1);
		}
		return bqs;
	}

	/**
	 * 获取患者编号根据手机号码
	 * 
	 * @param conn
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public JSONObject getusercodeBYsjhm(String sjhm) throws Exception {
		JSONObject jobj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getusercodeBYsjhm", sjhm);
		return jobj;
	}

	/**
	 * 实收金额
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getSsje(String usercode) throws Exception {
		String ssje = "0";
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSsje", usercode);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));

			paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
			ssje = KqdsBigDecimal.round(paymoney, 2).toString();
		}
		return ssje;
	}

	/**
	 * 实收金额(具体某一挂号)
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getSsjeReg(String regno) throws Exception {
		String ssje = "0";
		Map<String,String> map = new HashMap<String,String>();
		map.put("regno", regno);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSsjeReg", map);
		if (list != null && list.size() > 0) {
			for (JSONObject rs : list) {
				
			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));

			paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
			ssje = KqdsBigDecimal.round(paymoney, 2).toString();
			rs.put("ssje", ssje);
			}
		}
		return list;
	}

	/**
	 * 实收金额(具体某一费用单)
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public BigDecimal getSsjeOne(String costno) throws Exception {
		BigDecimal ssmoney = BigDecimal.ZERO;
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSsjeOne", costno);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));

			paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
			ssmoney = KqdsBigDecimal.round(paymoney, 2);
		}
		return ssmoney;
	}

	/**
	 * 实收金额(具体某一费用单)
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public BigDecimal getSsjeOneAddIntegral(String costno) throws Exception {
		BigDecimal ssmoney = BigDecimal.ZERO;
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSsjeOne", costno);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));

			paymoney = paymoney.subtract(zs).subtract(djq);
			ssmoney = KqdsBigDecimal.round(paymoney, 2);
		}
		return ssmoney;
	}

	/**
	 * 积分付款金额(具体某一费用单)
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public BigDecimal getJFjeOne(String costno) throws Exception {
		BigDecimal integral = BigDecimal.ZERO;
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getJFjeOne", costno);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));

			integral = KqdsBigDecimal.round(integral, 2);
		}
		return integral;
	}

	/****************************************
	 * 网电中心、客服中心、营销中心
	 **************************************/

	@SuppressWarnings("unchecked")
	public JSONObject userManger4Wdzx(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization,BootStrapPage bp) throws Exception {
		// 判断 如果 选择预约时间 查询所有患者预约的时间 即多条记录
		// 如果不选预约时间 默认只展示一条 即展示该患者最新预约的时间
		if (map.containsKey("yytime1") || map.containsKey("yytime2") || map.containsKey("xiangmu")) {
			map.put("netsql", "left join KQDS_NET_ORDER n ");
		} else {
			// 初始进入页面 当天建档 优先展示当天有预约的
			map.put("netsql",
					"left join (select a.* from KQDS_NET_ORDER a,(select usercode,max(createtime) createtime  from KQDS_NET_ORDER group by usercode) b where a.usercode=b.usercode and a.createtime=b.createtime ) n");
		}
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}
		if (map.containsKey("yewu")) {
			// 如果有查询该建档人所创建的患者信息权限
			String jdr = map.get("yewu").toString();
			if (YZUtility.isStrInArrayEach("\'" + jdr + "\'", visualstaff)) {
				map.put("yewu2", map.get("yewu"));
			} else {
				// 没权限时 给一个查不出东西的sql
				map.put("yewu3", map.get("yewu"));
			}
		} else if (!map.containsKey("yewu")) {
			map.put("yewu4", visualstaff);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		/**
		 * 2019.07.18 lwg 网店咨询 客户管理的点击问题
		 */
		if(map.get("sortName")!=null){
			String sortName=map.get("sortName");
			if(sortName.equals("zdoorstatus")){
				map.put("sortName", "u.doorstatus");
			}
			if(sortName.equals("doorstatus")){
				map.put("sortName", "n.doorstatus");
			}
			if(sortName.equals("cjstatus")){
				map.put("sortName", "n.cjstatus");
			}
			if(sortName.equals("username")){
				map.put("sortName", "u.username");
			}
			if(sortName.equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			if(sortName.equals("createuser")){
				map.put("sortName", "per1.user_name");
			}
			if(sortName.equals("askitem")){
				map.put("sortName", "kcit4.dict_name");
			}
			if(sortName.equals("createtime")){
				map.put("sortName", "u.createtime");
			}
			if(sortName.equals("ordertime")){
				map.put("sortName", "n.ordertime");
			}
			if(sortName.equals("guidetime")){
				map.put("sortName", "n.guidetime");
			}
			if(sortName.equals("devchannel")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(sortName.equals("nexttype")){
				map.put("sortName", "hzly.dict_name");
			}
			if(sortName.equals("provincename")){
				map.put("sortName", "a1.area_name");
			}
			if(sortName.equals("cityname")){
				map.put("sortName", "a2.area_name");
			}
			if(sortName.equals("townname")){
				map.put("sortName", "a3.area_name");
			}
			if(sortName.equals("age")){
				map.put("sortName", "u.age");
			}
			if(sortName.equals("important")){
				map.put("sortName", "u.important");
			}
			if(sortName.equals("xueli")){
				map.put("sortName", "u.xueli");
			}
			if(sortName.equals("askperson")){
				map.put("sortName", "per2.user_name");
			}
			if(sortName.equals("accepttype")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(sortName.equals("accepttool")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(sortName.equals("askcontent")){
				map.put("sortName", "n.askcontent");
			}
			if(sortName.equals("remark")){
				map.put("sortName", "u.remark");
			}
			if(sortName.equals("glr")){
				map.put("sortName", "per3.user_name");
			}
			if(sortName.equals("glrremark")){
				map.put("sortName", "u.glrremark");
			}
			if(sortName.equals("usercode")){
				map.put("sortName", "u.usercode");
			}
		}
		/**
		 * end
		 */
		/**
		 * 判断正畸部门人员查询可看的数据 syp 2019-6-20
		 * 添加后端分页功能 lwg 2019.07.13
		 */
		List<JSONObject> returnlist1=null;
		if ("a361daeb-be3c-4dab-a63d-3d718177c81e".equals(person.getSeqId()) ) {
			returnlist1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4WdzxNetNum", map);
			
		} else {
			returnlist1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4WdzxNum", map);
		}
		
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		
		List<JSONObject> returnlist=null;
		if ("a361daeb-be3c-4dab-a63d-3d718177c81e".equals(person.getSeqId()) ) {
			returnlist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4WdzxNet", map);
			
		} else {
			returnlist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4Wdzx", map);
		}

		for (JSONObject job : returnlist) {
			/**
			 * bootstrapTable 中四级赋值
			 */
			String proviceName = job.getString("provincename");
			if(proviceName.equals("")) {
				job.replace("provincename", job.getString("province_name"));
			}
			String citname = job.getString("cityname");
			if(citname.equals("")) {
				job.replace("cityname", job.getString("city_name"));
			}
			String towname = job.getString("townname");
			if(towname.equals("")) {
				job.replace("townname", job.getString("area_name"));
			}
			/**
			 * end
			 */
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
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(returnlist);
		JSONObject jobj1 = new JSONObject();
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", returnlist);
		jobj1.put("nums", returnlist1);
		return jobj1;
		/**
		 * end
		 */
/*//		List<JSONObject> returnlist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4Wdzx", map);
		for (JSONObject job : returnlist) {
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

		return returnlist;*/
	}

	@SuppressWarnings("unchecked")
	public JSONObject userManger4Kfzx(String table, YZPerson person, Map<String, String> map, String ywhf, String visualstaff, String organization,BootStrapPage bp, JSONObject json,Double time1,Double time2,String labelID) throws Exception {
		// 判断 如果 选择预约时间 查询所有患者预约的时间 即多条记录
		// 如果不选预约时间 默认只展示一条 即展示该患者最新预约的时间
		if (map.containsKey("yytime1") || map.containsKey("yytime2")) {
			map.put("netsql", "left join KQDS_REG r ");
		} else {
			// 初始进入页面 当天建档 优先展示当天有预约的
			map.put("netsql",
					"left join (select a.* from KQDS_REG a,(select usercode,max(createtime) createtime  from KQDS_REG group by usercode) b where a.usercode=b.usercode and a.createtime=b.createtime ) r");
		}
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}
		if (map.containsKey("yewu")) {
			// 如果有查询该建档人所创建的患者信息权限
			String jdr = map.get("yewu").toString();
			if (YZUtility.isStrInArrayEach("\'" + jdr + "\'", visualstaff)) {
				map.put("yewu2", map.get("yewu"));
			} else {
				// 没权限时 给一个查不出东西的sql
				map.put("yewu3", map.get("yewu"));
			}
		} else if (!map.containsKey("yewu")) {
			map.put("yewu4", visualstaff);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if (!YZUtility.isNullorEmpty(ywhf)) {
			if (ywhf.equals("0")) {// 无回访
				map.put("nohf", ywhf);
			} else {// 有回访
				map.put("ishf", ywhf);
			}
		}
		/**
		 * 2019.07.18 lwg 网店咨询 客户管理的点击问题
		 */
		if(map.get("sortName")!=null){
			String sortName=map.get("sortName");
			if(sortName.equals("zdoorstatus")){
				map.put("sortName", "u.doorstatus");
			}
			if(sortName.equals("doorstatus")){
				map.put("sortName", "n.doorstatus");
			}
			if(sortName.equals("cjstatus")){
				map.put("sortName", "n.cjstatus");
			}
			if(sortName.equals("username")){
				map.put("sortName", "u.username");
			}
			if(sortName.equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			if(sortName.equals("createuser")){
				map.put("sortName", "per1.user_name");
			}
			if(sortName.equals("askitem")){
				map.put("sortName", "kcit4.dict_name");
			}
			if(sortName.equals("createtime")){
				map.put("sortName", "u.createtime");
			}
			if(sortName.equals("ordertime")){
				map.put("sortName", "n.ordertime");
			}
			if(sortName.equals("guidetime")){
				map.put("sortName", "n.guidetime");
			}
			if(sortName.equals("devchannel")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(sortName.equals("nexttype")){
				map.put("sortName", "hzly.dict_name");
			}
			if(sortName.equals("provincename")){
				map.put("sortName", "a1.area_name");
			}
			if(sortName.equals("cityname")){
				map.put("sortName", "a2.area_name");
			}
			if(sortName.equals("townname")){
				map.put("sortName", "a3.area_name");
			}
			if(sortName.equals("age")){
				map.put("sortName", "u.age");
			}
			if(sortName.equals("important")){
				map.put("sortName", "u.important");
			}
			if(sortName.equals("xueli")){
				map.put("sortName", "u.xueli");
			}
			if(sortName.equals("askperson")){
				map.put("sortName", "per2.user_name");
			}
			if(sortName.equals("accepttype")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(sortName.equals("accepttool")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(sortName.equals("askcontent")){
				map.put("sortName", "n.askcontent");
			}
			if(sortName.equals("remark")){
				map.put("sortName", "u.remark");
			}
			if(sortName.equals("glr")){
				map.put("sortName", "per3.user_name");
			}
			if(sortName.equals("glrremark")){
				map.put("sortName", "u.glrremark");
			}
			if(sortName.equals("usercode")){
				map.put("sortName", "u.usercode");
			}
			if(sortName.equals("ywhf")){
				map.put("sortName", "(select count(v.usercode) from KQDS_Visit v where v.usercode=u.UserCode)");
			}
			
		}
		/**
		 * end
		 */
		List<String> usercodeAll= new ArrayList<String>();
		List<JSONObject> returnlist =new ArrayList<JSONObject>();
		if(time1>0&&time2>0&&!labelID.equals("")||time1>0&&time2>0&&labelID.equals("")){
//			Double dou_obj = new Double(Double.parseDouble(time1));
//	        NumberFormat nf = NumberFormat.getInstance();
//	        nf.setGroupingUsed(false);
//	        time1 = nf.format(dou_obj);
			CacheUtil.openCache();
			//CacheUtil.queryVisitArticleStatistics(1);
			if(organization.equals("HUDH")){
				Set<String> c = CacheUtil.getZSetByScore("label:key", time1, time2);
				List<String> labelAll= new ArrayList<String>();
				for (String string : c) {
					if(!labelID.equals("")){
						int i=0;
						String[] id=labelID.split(",");
						for (String str: id) {
							if(string.contains(str)){
								i+=1;
							}
						}
						if(i==id.length){
							labelAll.add(string);
						}
					}else{
						labelAll.add(string);
					}
				}
				for (String string : labelAll) {
					String mm = (String) CacheUtil.getMapKey("label:value", string);
					if(mm!=null&&!mm.equals("")){
						usercodeAll.add(mm);
					}
				}
			}else{
				Set<String> c = CacheUtil.getZSetByScore(organization+":label:key", time1, time2);
				List<String> labelAll= new ArrayList<String>();
				for (String string : c) {
					if(!labelID.equals("")){
						int i=0;
						String[] id=labelID.split(",");
						for (String str: id) {
							if(string.contains(str)){
								i+=1;
							}
						}
						if(i==id.length){
							labelAll.add(string);
						}
					}else{
						labelAll.add(string);
					}
				}
				for (String string : labelAll) {
					String mm = (String) CacheUtil.getMapKey(organization+":label:value", string);
					if(mm!=null&&!mm.equals("")){
						usercodeAll.add(mm);
					}
				}
			}
			
			CacheUtil.close();
		}else if(!labelID.equals("")){
			CacheUtil.openCache();
			//CacheUtil.queryVisitArticleStatistics(1);
			List<String> labelAll= new ArrayList<String>();
			String[] id=labelID.split(",");
			if(organization.equals("HUDH")){
				Set<String> c = CacheUtil.keys("labelQuery:*"+id[0]+"*");
				if(c.size()>0){
					for (String string : c) {
						int i=0;
						for (String str: id) {
							if(string.contains(str)){
								i+=1;
							}
						}
						if(i==id.length){
							labelAll.add(string);
						}
					}
				}
				for (String string : labelAll) {
					String mm = (String) CacheUtil.getMapKey("label:value", string.substring(11,string.length()));
					if(mm!=null&&!mm.equals("")){
						usercodeAll.add(mm);
					}
				}
			}else{
				Set<String> c = CacheUtil.keys(organization+":labelQuery:*"+id[0]+"*");
				if(c.size()>0){
					for (String string : c) {
						int i=0;
						for (String str: id) {
							if(string.contains(str)){
								i+=1;
							}
						}
						if(i==id.length){
							labelAll.add(string);
						}
					}
				}
				for (String string : labelAll) {
					String mm = (String) CacheUtil.getMapKey(organization+":label:value", string.substring(16,string.length()));
					if(mm!=null&&!mm.equals("")){
						usercodeAll.add(mm);
					}
				}
			}
			
			CacheUtil.close();
		}
		if(usercodeAll.size()>0&&usercodeAll!=null){
			StringBuffer usercodeAlls=new StringBuffer();
			for (String string : usercodeAll) {
				if(usercodeAlls.length()==0){
					usercodeAlls.append("\'"+string+"\'");
				}else{
					usercodeAlls.append(",\'"+string+"\'");
				}
			}
			if(usercodeAlls!=null){
				map.put("usercodealls", usercodeAlls.toString());
			}
			if(time1==0&&time2==0){
				map.put("jdtime1", "");
				map.put("jdtime2", "");
			}
			List<JSONObject> returnlist1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4KfzxNumByUsercodes", map);
			// 分页插件
			PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
			returnlist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4KfzxByUsercodes", map);
			PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(returnlist);
			
			JSONObject jobj1 = new JSONObject();
//				for (JSONObject job : returnlist) {
//					/**
//					 * end
//					 */
//					String cjstatus = job.getString("cjstatus");
//					if ("1".equals(cjstatus)) {
//						cjstatus = "已成交";
//					} else {
//						cjstatus = "未成交";
//					}
//					job.put("cjstatus", cjstatus);
//
//					String zdoorstatus = job.getString("zdoorstatus");
//					if ("1".equals(zdoorstatus)) {
//						zdoorstatus = "已上门";
//					} else {
//						zdoorstatus = "未上门";
//					}
//					job.put("zdoorstatus", zdoorstatus);
//
//					String doorstatus = job.getString("doorstatus");
//					if ("1".equals(doorstatus)) {
//						doorstatus = "已上门";
//					} else {
//						doorstatus = "未上门";
//					}
//					job.put("doorstatus", doorstatus);
//					ywhf = job.getString("ywhf");
//					if ("0".equals(ywhf)) {
//						ywhf = "无回访";
//					} else {
//						ywhf = "有回访";
//					}
//					job.put("ywhf", ywhf);
//					//TODO 查询标签名称方便生成报表功能添加标签名
////					CacheUtil.openCache();
////					CacheUtil.queryVisitArticleStatistics(1);
////					String mm = (String) CacheUtil.getMapKey("label:name",job.getString("usercode"));
////					job.put("labelname", mm);
////					CacheUtil.close();
//			}
			jobj1.put("total", pageInfo.getTotal());
			jobj1.put("rows", returnlist);
			jobj1.put("nums", returnlist1);
			return jobj1;
		}else if(usercodeAll.size()==0&&labelID!=null&&!labelID.equals("")&&returnlist.size()==0||usercodeAll.size()>0&&labelID!=null&&!labelID.equals("")&&returnlist.size()==0){
			JSONObject jobj1 = new JSONObject();
			List<JSONObject> returnlist1=new ArrayList<JSONObject>();
			JSONObject jobj = new JSONObject();
			jobj.put("doorstatus", 0);
			jobj.put("cjstatus", 0);
			returnlist1.add(jobj);
			jobj1.put("total", 0);
			jobj1.put("rows", returnlist);
			jobj1.put("nums", returnlist1);
			return jobj1;
		}else{
			List<JSONObject> returnlist1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4KfzxNum", map);
			// 分页插件
			PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
			returnlist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4Kfzx", map);
			PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(returnlist);
			JSONObject jobj1 = new JSONObject();
//			for (JSONObject job : returnlist) {
////				/**
////				 * 添加根据条件查询患者标签
////				 * 2019-9-5  syp
////				 */
////				String userCode = job.getString("usercode");
////				json.put("userCode", userCode);
////				List<kqdsHzLabellabeAndPatient> list = labellPatientLogic.findLabellPatientByUserId(json);
////				if (list != null && list.size() > 0) {
////					List<String> labelOneNames = list.stream().map(kqdsHzLabellabeAndPatient :: getLabelOneName).collect(Collectors.toList());
////					List dataList = this.removeDuplicate(labelOneNames);
////					Object result = dataList.stream().collect(Collectors.joining(","));
////					String oneLabel = result.toString();
////					List<String> labelTwoNames = list.stream().map(kqdsHzLabellabeAndPatient :: getLabelTwoName).collect(Collectors.toList());
////					Object resultTwo = labelTwoNames.stream().collect(Collectors.joining(","));
////					String twoLabel = resultTwo.toString();
////					List<String> labelThreeNames = list.stream().map(kqdsHzLabellabeAndPatient :: getLabelThreeName).collect(Collectors.toList());
////					Object resultThree = labelThreeNames.stream().collect(Collectors.joining(","));
////					String threeLabel = resultThree.toString();
////					job.put("oneLabel", oneLabel);
////					job.put("twoLabel", twoLabel);
////					job.put("threeLabel", threeLabel);
////				}
////				List<kqdsHzLabellabeAndPatient> unique = list.stream().distinct().collect(Collectors.toList());
////				List<kqdsHzLabellabeAndPatient> labelList = list.stream().collect(
////				      Collectors.collectingAndThen(
////				             Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(kqdsHzLabellabeAndPatient :: getLabelOneId))), ArrayList::new)
////				      );//按照指定对象属性去重
////				System.err.println(labelList);
////				
////				for (kqdsHzLabellabeAndPatient dp : labelList) {
////					List<kqdsHzLabellabeAndPatient> listLabel = labellPatientLogic.findLabelContentByParentId(dp.getLabelOneId(), dataMap);
////					List<String> orders = listLabel.stream().map(kqdsHzLabellabeAndPatient :: getLabelTwoName).collect(Collectors.toList());
////					System.out.println(orders);
////					for (kqdsHzLabellabeAndPatient kqdsHzLabellabeAndPatient : listLabel) {
////						kqdsHzLabellabeAndPatient.getLabelOneName();
////						String labelTeoId = kqdsHzLabellabeAndPatient.getLabelTwoId();
////						List<kqdsHzLabellabeAndPatient> listTwo = labellPatientLogic.findLabelContentByParentId(labelTeoId);
////					} 
////				}
//				/**
//				 * end
//				 */
//				String cjstatus = job.getString("cjstatus");
//				if ("1".equals(cjstatus)) {
//					cjstatus = "已成交";
//				} else {
//					cjstatus = "未成交";
//				}
//				job.put("cjstatus", cjstatus);
//
//				String zdoorstatus = job.getString("zdoorstatus");
//				if ("1".equals(zdoorstatus)) {
//					zdoorstatus = "已上门";
//				} else {
//					zdoorstatus = "未上门";
//				}
//				job.put("zdoorstatus", zdoorstatus);
//
//				String doorstatus = job.getString("doorstatus");
//				if ("1".equals(doorstatus)) {
//					doorstatus = "已上门";
//				} else {
//					doorstatus = "未上门";
//				}
//				job.put("doorstatus", doorstatus);
//				ywhf = job.getString("ywhf");
//				if ("0".equals(ywhf)) {
//					ywhf = "无回访";
//				} else {
//					ywhf = "有回访";
//				}
//				job.put("ywhf", ywhf);
//				//TODO 查询标签名称方便生成报表功能添加标签名
////				CacheUtil.openCache();
////				CacheUtil.queryVisitArticleStatistics(1);
////				String mm = (String) CacheUtil.getMapKey("label:name",job.getString("usercode"));
////				job.put("labelname", mm);
////				CacheUtil.close();
//			}
			jobj1.put("total", pageInfo.getTotal());
			jobj1.put("rows", returnlist);
			jobj1.put("nums", returnlist1);
			return jobj1;
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject userManger4Yxzx(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization,BootStrapPage bp) throws Exception {
		// 判断 如果 选择预约时间 查询所有患者预约的时间 即多条记录
		// 如果不选预约时间 默认只展示一条 即展示该患者最新预约的时间
		if (map.containsKey("yytime1") || map.containsKey("yytime2") || map.containsKey("xiangmu")) {
			map.put("netsql", "left join KQDS_NET_ORDER n ");
		} else {
			// 初始进入页面 当天建档 优先展示当天有预约的
			map.put("netsql",
					"left join (select a.* from KQDS_NET_ORDER a,(select usercode,max(createtime) createtime  from KQDS_NET_ORDER group by usercode) b where a.usercode=b.usercode and a.createtime=b.createtime ) n");
		}
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}
		if (map.containsKey("yewu")) {
			// 如果有查询该建档人所创建的患者信息权限
			String jdr = map.get("yewu").toString();
			if (YZUtility.isStrInArrayEach("\'" + jdr + "\'", visualstaff)) {
				map.put("yewu2", map.get("yewu"));
			} else {
				// 没权限时 给一个查不出东西的sql
				map.put("yewu3", map.get("yewu"));
			}
		} else if (!map.containsKey("yewu")) {
			map.put("yewu4", visualstaff);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if(map.get("sortName")!=null){
			String sortName=map.get("sortName");
			if(sortName.equals("zdoorstatus")){
				map.put("sortName", "u.doorstatus");
			}
			if(sortName.equals("doorstatus")){
				map.put("sortName", "n.doorstatus");
			}
			if(sortName.equals("cjstatus")){
				map.put("sortName", "n.cjstatus");
			}
			if(sortName.equals("usercode")){
				map.put("sortName", "u.usercode");
			}
			if(sortName.equals("username")){
				map.put("sortName", "u.username");
			}
			if(sortName.equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			if(sortName.equals("createuser")){
				map.put("sortName", "per1.user_name");
			}
			if(sortName.equals("askitem")){
				map.put("sortName", "kcit4.dict_name");
			}
			if(sortName.equals("createtime")){
				map.put("sortName", "u.createtime");
			}
			if(sortName.equals("ordertime")){
				map.put("sortName", "n.ordertime");
			}
			if(sortName.equals("guidetime")){
				map.put("sortName", "n.guidetime");
			}
			if(sortName.equals("devchannel")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(sortName.equals("nexttype")){
				map.put("sortName", "hzly.dict_name");
			}
			if(sortName.equals("provincename")){
				map.put("sortName", "a1.area_name");
			}
			if(sortName.equals("cityname")){
				map.put("sortName", "a2.area_name");
			}
			if(sortName.equals("townname")){
				map.put("sortName", "a3.area_name");
			}
			if(sortName.equals("age")){
				map.put("sortName", "u.age");
			}
			if(sortName.equals("important")){
				map.put("sortName", "u.important");
			}
			if(sortName.equals("xueli")){
				map.put("sortName", "u.xueli");
			}
			if(sortName.equals("askperson")){
				map.put("sortName", "per2.user_name");
			}
			if(sortName.equals("accepttype")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(sortName.equals("accepttool")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(sortName.equals("askcontent")){
				map.put("sortName", "n.askcontent");
			}
			if(sortName.equals("remark")){
				map.put("sortName", "u.remark");
			}
			if(sortName.equals("glr")){
				map.put("sortName", "per3.user_name");
			}
			if(sortName.equals("glrremark")){
				map.put("sortName", "u.glrremark");
			}
			if(sortName.equals("usercode")){
				map.put("sortName", "u.usercode");
			}
		}
		List<JSONObject> returnlist1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4YxzxNum", map);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> returnlist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4Yxzx", map);
		for (JSONObject job : returnlist) {
			/**
			 * bootstrapTable 中四级赋值
			 */
			String proviceName = job.getString("provincename");
			if(proviceName.equals("")) {
				job.replace("provincename", job.getString("province_name"));
			}
			String citname = job.getString("cityname");
			if(citname.equals("")) {
				job.replace("cityname", job.getString("city_name"));
			}
			String towname = job.getString("townname");
			if(towname.equals("")) {
				job.replace("townname", job.getString("area_name"));
			}
			/**
			 * end
			 */
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
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(returnlist);
		JSONObject jobj1 = new JSONObject();
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", returnlist);
		jobj1.put("nums", returnlist1);
		return jobj1;
	}

	/****************************************
	 * 网电中心、客服中心、营销中心 END
	 **************************************/

	/**********************************************************
	 * 从kqds_net_order整合过来的方法
	 *********************************************************************/

	/**
	 * 网电-客户中心-精确查询
	 * 
	 * @param conn
	 * @param table
	 * @param queryinput
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> jingQueChaXun4Net(String queryinput, String queryInputName) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(queryinput)) {
			map.put("queryinput", queryinput);
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", queryinput));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", queryinput));
		}
		if (!YZUtility.isNullorEmpty(queryInputName)) {
			map.put("queryInputName", queryInputName);
		}
		List<JSONObject> returnlist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".jingQueChaXun4Net", map);
		for (JSONObject job : returnlist) {
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
		return returnlist;
	}

	/**
	 * 分页查询
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/*******************************************
	 * 微信相关
	 ************************************************/

	/**
	 * 根据生日查询患者
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectListByBirth(int month, int day, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("month", month + "");
		map.put("day", day + "");
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectListByBirth", map);
		return list;
	}

	/**
	 * 根据用户编号获取用户信息，用于大量循环时使用
	 * 
	 * @param conn
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public JSONObject getUserInfoByUserCode(String usercode) throws Exception {
		JSONObject result = (JSONObject) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getUserInfoByUserCode", usercode);
		return result;
	}

	public int deleteuser(String userCodes) throws Exception {
		int count = (int) dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateusers", userCodes);
		return count;

	}

	public int checkuser(String userCode) throws Exception {
		int count = (int) dao.update(TableNameUtil.KQDS_MEMBER_RECORD + ".checkuser", userCode);
		return count;

	}

	@Transactional(rollbackFor = { Exception.class })
	public void setKeFu(KqdsChangeKefu wd, KqdsUserdocument user) throws Exception {
		dao.saveSingleUUID(TableNameUtil.KQDS_CHANGE_KEFU, wd);
		dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void setWd(KqdsChangeWd wd, KqdsUserdocument user) throws Exception {
		dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
		dao.saveSingleUUID(TableNameUtil.KQDS_CHANGE_WD, wd);
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public void updateUserDoc(KqdsUserdocument dp, KqdsUserdocument en, KqdsNetOrder netorder, YZPerson person, HttpServletRequest request) throws Exception {
		dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, dp);
		// 判断是否修改了患者姓名 如果修改了患者姓名则更新所有保存了该患者姓名的表
		if (!dp.getUsername().equals(en.getUsername())) {
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_COSTORDER, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_REFUND, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_EXTENSION, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_GIVEITEM_GIVERECORD, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_GIVEITEM_USERECORD, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_HOSPITAL_ORDER, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_IMAGE_DATA, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_MEMBER_RECORD, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), "KQDS_MEMBER_RECORD_SZR", 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_MEMBER, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_NET_ORDER, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_NET_ORDER_RECORD, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_PAYCOST, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_RECEIVEINFO, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_CHANGE_DOCTOR, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_CHANGE_RECEIVE, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_CHANGE_WD, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_REG, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_VISIT, 1, request);
			updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_PARTICIPANT, 2, request);
		}
		if (ConstUtil.USER_TYPE_1 == dp.getType()) {// 网电信息修改
			String wdseqId = request.getParameter("wdseqId");
			if (!YZUtility.isNullorEmpty(wdseqId)) {
				String accepttype = request.getParameter("accepttype");
				String accepttool = request.getParameter("accepttool");
				String askitem = request.getParameter("askitem");
				String askcontent = request.getParameter("askcontent");
				String ordertime = request.getParameter("ordertime");
				KqdsNetOrder wd = (KqdsNetOrder) dao.loadObjSingleUUID(TableNameUtil.KQDS_NET_ORDER, wdseqId);
				wd.setAccepttype(accepttype);
				wd.setAccepttool(accepttool);
				wd.setAskitem(askitem);
				wd.setAskcontent(askcontent);
				wd.setOrdertime(ordertime);
				dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, wd);
				// 修改该患者所有的受理类型 受理工具
				Map map = new HashMap<String, String>();
				map.put("usercode", wd.getUsercode());
				List<KqdsNetOrder> orderList = (List<KqdsNetOrder>) dao.loadList(TableNameUtil.KQDS_NET_ORDER, map);
				for (KqdsNetOrder order : orderList) {
					order.setAccepttype(accepttype);
					order.setAccepttool(accepttool);
					dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, order);
				}
			} else {
				// 新建网电信息
				Map map = new HashMap<String, String>();
				map.put("usercode", dp.getUsercode());
				List<KqdsNetOrder> orderList = (List<KqdsNetOrder>) dao.loadList(TableNameUtil.KQDS_NET_ORDER, map);
				if (orderList == null || orderList.isEmpty()) {
					netorder.setSeqId(YZUtility.getUUID());
					netorder.setCreatetime(YZUtility.getCurDateTimeStr());
					netorder.setCreateuser(person.getSeqId());
					netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request)); // 【网电建档，以页面传入为准】
					netorder.setDoorstatus(0); // 上门状态 0 未上门 1已上门
					netorder.setCjstatus(0); // 成交状态 0 未成交 1已成交
					netorder.setUsercode(dp.getUsercode());
					netorder.setUsername(dp.getUsername());
					netorder.setAcceptdate(dp.getCreatetime()); // 受理日期
					netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request)); // 【网电建档，以页面传入为准】
					dao.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
				}
			}
		}
	}
	@Transactional(rollbackFor = { Exception.class })
	public void insertUserDoc(KqdsUserdocument dp, KqdsNetOrder netorder, YZPerson person, HttpServletRequest request) throws Exception {
		if (ConstUtil.USER_TYPE_1 == dp.getType()) {
			// 新建网电信息
			netorder.setSeqId(YZUtility.getUUID());
			netorder.setCreatetime(YZUtility.getCurDateTimeStr());
			netorder.setCreateuser(person.getSeqId());
			netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request)); // 【网电建档，以页面传入为准】
			netorder.setDoorstatus(0); // 上门状态 0 未上门 1已上门
			netorder.setCjstatus(0); // 成交状态 0 未成交 1已成交
			netorder.setUsercode(dp.getUsercode());
			netorder.setUsername(dp.getUsername());
			netorder.setAcceptdate(dp.getCreatetime()); // 受理日期
			netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request)); // 【网电建档，以页面传入为准】
			dao.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
		} else {
			dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【门诊建档，以所在门诊为准】
		}

		// ### 入库
		dao.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, dp);
		// 记录日志
		BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_USERDOCUMENT, dp, dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);

		// 新建档案默认创建一张预交金卡
		memberLogic.addMember4CreateUserDocument(dp, person, request);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void hzhb(KqdsUserdocument user1, KqdsUserdocument user2, HttpServletRequest request) throws Exception {
		user2 = user1ToUser2(user1, user2);
		user1.setIsdelete(1);
		dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user2);
		dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user1);
		/**
		 * start 2019-7-29 syp
		 */
		KqdsUserdocumentMergeRecord dp = new KqdsUserdocumentMergeRecord();
		dp.setUsercode_one(user1.getUsercode());
		dp.setUsercode_two(user2.getUsercode());
		dp.setUsername_one(user1.getUsername());
		dp.setUsername_two(user2.getUsername());
		dp.setUserdocument_one_crateuser(user1.getCreateuser());
		dp.setUserdocument_two_crateuser(user2.getCreateuser());
		mergeLogic.saveMergeRecord(dp, request);
		/**
		 * end 2019-7-29 syp
		 */
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_BCJL, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_LLTJ_DETAIL, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_COSTORDER, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_COSTORDER_DETAIL, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_EXTENSION, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_GIVEITEM_GIVERECORD, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_GIVEITEM_USERECORD, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_HOSPITAL_ORDER, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_IMAGE_DATA, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_MEDICALRECORD, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_MEMBER_RECORD, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_MEMBER, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_NET_ORDER, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_NET_ORDER_RECORD, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_PAYCOST, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_RECEIVEINFO, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_CHANGE_DOCTOR, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_CHANGE_RECEIVE, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_CHANGE_WD, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_REFUND, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_REFUND_DETAIL, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_REG, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_VISIT, 1, request);
		updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_TOOTH_DOC, 1, request);
	}
	
	/**
	 * 添加判断该患者是否创建临床路径 2019-5-12 shaoyp
	 * @param usercode
	 * @throws Exception
	 */
	public void isCreateLclj(String seqId, @Param("iscreateLclj") String iscreateLclj) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("seqId", seqId);
		map.put("iscreateLclj", iscreateLclj);
		dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".isCreateLclj", map);
	}

	/**   
	  * @Title: setInputtingPerson   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param wd
	  * @param: @param user      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月19日 下午2:44:50
	  */ 
	@Transactional(rollbackFor = { Exception.class })
	public void setInputtingPerson(KqdsJdrchange wd, KqdsUserdocument user) throws Exception {
		// TODO Auto-generated method stub
		dao.saveSingleUUID(TableNameUtil.KQDS_CHANGE_JDR, wd);
		dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
	}
	
	/**
	 * 获取当前患者介绍他人的数量  2019-8-21   syp
	  * @Title: findIntroducerNum   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param usercode
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: int
	  * @dateTime:2019年8月21日 下午3:58:40
	 */
	public int findIntroducerNum(String usercode) throws Exception {
		int introducerNum = (int) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".findIntroducerNum", usercode);
		return introducerNum;
	}

	/**   
	  * @Title: saveKpatient   
	  * @Description: TODO(保存标签)   
	  * @param: @param kPatient      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月23日 下午3:53:58
	  */  
	public void saveKpatient(kqdsHzLabellabeAndPatient kPatient) throws Exception {
		// TODO Auto-generated method stub
		dao.save(TableNameUtil.KQDS_Hz_LabellabeAndPatient+".saveKpatient", kPatient);
	}

	/**   
	  * @Title: deleteLabel   
	  * @Description: TODO(修改患者的标签)   
	  * @param: @param usercode      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月23日 下午5:27:16
	  */  
	public void deleteLabel(String userCode) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(TableNameUtil.KQDS_Hz_LabellabeAndPatient+".deleteLabel", userCode);
	}
	/**   
	  * @Title: deleteLabel   
	  * @Description: TODO(删除患者的标签) lwg   
	  * @param: @param usercode      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月24日 
	  */  
	public void deleteLabelByUsercode(Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(TableNameUtil.KQDS_Hz_LabellabeAndPatient+".deleteLabelByUsercode", map);
	}
	
	public String selectLabelByUsercode(Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		String threeid=(String) dao.findForObject(TableNameUtil.KQDS_Hz_LabellabeAndPatient+".selectLabelByUsercode", map);
		return threeid;
	}
	/**
	  * @Title: mergeMessage   
	  * @Description: TODO(档案合并临床信息)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: Strings
	 * @throws Exception 
	  * @dateTime:2019年8月29日 下午2:10:29
	 */
	public void mergeMessage(String blcode,String usercode) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode1", blcode);
		map.put("usercode2", usercode);
		dao.update(TableNameUtil.HUDH_LCLJ_ORDERTRACK+".mergeMessage", map);
	}
	/**
	 * 根据患者编号查询所有档案信息
	 * 2019-09-01 lwg
	 * @param usercodes
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findKqdsUserdocumentByUsercodes(String usercodes) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT+".findKqdsUserdocumentByUsercodes", usercodes);
		
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> findByUsercode(String usercode) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT+".findByUsercode", usercode);
		return list;
	}
	/**
	 * list集合去重
	  * @Title: removeDuplicate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param list
	  * @param: @return      
	  * @return: List
	  * @dateTime:2019年9月10日 上午10:30:44
	 */
	@SuppressWarnings("unchecked")
	public static List removeDuplicate(List list) {   
	    HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	}

	/**   
	  * @Title: findDevchannel   
	  * @Description: TODO(按渠道查询)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年10月5日 上午10:20:14
	  */  
	public List findDevchannel(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT+".findDevchannel", map);
	}

	/**   
	  * @Title: findDevchannelAll   
	  * @Description: TODO(总)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List
	 * @throws Exception
	  * @dateTime:2019年10月5日 上午10:28:56
	  */  
	public JSONObject findDevchannelAll(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT+".findDevchannelAll", map);
	}

	/**   
	  * @Title: findConsumptionInterval   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年10月6日 上午10:20:26
	  */  
	@SuppressWarnings("unchecked")
	public JSONObject findConsumptionInterval(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT+".findConsumptionInterval", map);
	}

	/**   
	  * @Title: findLabel   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param userCode
	  * @param: @return      
	  * @return: boolean
	 * @throws Exception 
	  * @dateTime:2019年10月12日 下午2:47:51
	  */  
	@SuppressWarnings("unchecked")
	public JSONObject findLabel(String userCode) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap();
		map.put("userCode", userCode);
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_Hz_LabellabeAndPatient+".findLabel", map);
	}   
	/**
	 * 查询门诊标识
	 * <p>Title: findOrganizationBySeqid</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月21日 
	 * @param seqid
	 * @return
	 * @throws Exception
	 */
	public String findOrganizationBySeqid(String seqid) throws Exception {

		return (String) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT+".findOrganizationBySeqid", seqid);
	}
	/**
	 * 根据患者编号修改病历号
	 * <p>Title: updateBlcode</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月3日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateBlcode(Map<String,String> map) throws Exception{
		return (int) dao.update(TableNameUtil.KQDS_USERDOCUMENT+".updateBlcode", map);
	}
}