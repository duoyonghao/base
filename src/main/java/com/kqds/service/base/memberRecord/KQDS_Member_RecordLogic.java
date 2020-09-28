package com.kqds.service.base.memberRecord;

import java.math.BigDecimal;
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
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_Member_RecordLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

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
	public List<JSONObject> selectList(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectList", map);
		for (JSONObject job : list) {
			// 期初充值余额，期初赠送余额，期初余额小计
			job.putAll(selectAllQm(job.getString("cardno"), job.getString("createtime"), "qmmoney", "qmgivemoney", "qmtotal"));
		}
		return list;
	}

	/**
	 * 【不做门诊条件过滤】只查 受赠情况
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectListForCzjl(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectListForCzjl", map);
		for (JSONObject job : list) {
			// 期初充值余额，期初赠送余额，期初余额小计
			job.putAll(selectAllQm(job.getString("cardno"), job.getString("createtime"), "qmmoney", "qmgivemoney", "qmtotal"));
		}
		return list;
	}

	/**
	 * 预交金退费
	 * <p>Title: selectListRefund</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年5月25日 
	 * @param bp
	 * @param person
	 * @param map
	 * @param visualstaff
	 * @param querytype
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectListRefund(BootStrapPage bp, YZPerson person, Map<String, String> map, String visualstaff, String querytype) throws Exception {
		int firstIndex = bp.getOffset();
		if (map.containsKey("searchValue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("searchValue")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("searchValue")));
		}
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}

		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			if (!visualstaff.equals("")) {
				map.put("visualstaff", visualstaff);
			}
		}

		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("organizationname")){
				map.put("sortName", "dept.DEPT_NAME");
			}
			if(map.get("sortName").equals("sftime")){
				map.put("sortName", "kmr.createtime");
			}
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "kmr.tfremark");
			}
			if(map.get("sortName").equals("sfuser")){
				map.put("sortName", "per4.USER_NAME");
			}
			if(map.get("sortName").equals("jdtime")){
				map.put("sortName", "u.CreateTime");
			}
			if(map.get("sortName").equals("jddy")){
				map.put("sortName", "per7.USER_NAME");
			}
			if(map.get("sortName").equals("jduser")){
				map.put("sortName", "per5.USER_NAME");
			}
			if(map.get("sortName").equals("developer")){
				map.put("sortName", "per6.USER_NAME");
			}
			if(map.get("sortName").equals("introducer")){
				map.put("sortName", "u2.username");
			}
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "kmr.createtime");
			}
			if(map.get("sortName").equals("createuser")){
				map.put("sortName", "per4.USER_NAME");
			}
			if(map.get("sortName").equals("nexttype")){
				map.put("sortName", "hzly.dict_name");
			}
			if(map.get("sortName").equals("devchannel")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "p.USER_NAME");
			}
			if(map.get("sortName").equals("faskperson")){
				map.put("sortName", "per12.USER_NAME");
			}
			if(map.get("sortName").equals("regsort")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(map.get("sortName").equals("actualmoney")){
				map.put("sortName", "kmr.cmoney");
			}
			if(map.get("sortName").equals("shouldmoney")){
				map.put("sortName", "kmr.cmoney");
			}
			if(map.get("sortName").equals("totalcost")){
				map.put("sortName", "kmr.cmoney");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "kmr.username");
			}
			if(map.get("sortName").equals("blcode")){
				map.put("sortName", "u.blcode");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "kmr.usercode");
			}
		}
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectListRefund", map);
		for (JSONObject obj : list) {
			obj.put("cjstatus", "已成交");
		}
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		// 第一次加载数量，分页查询时不加载
		if (firstIndex == 0) {
			if (list.size() > 0) {
				// 统计 预交金退款数额
				JSONObject obj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".selectListRefundNum", map);
				jobj.put("cmoney", obj.getString("cmoney"));
			}
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
	
	
	
	/**
	 * 消费明细预收
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectListOrder(BootStrapPage bp, String table, Map<String, String> map, String visualstaff, String visualstaffwd) throws Exception {
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}
		if (map.containsKey("usercodes")) {
			map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		}
		map.put("visualstaff", visualstaff);
		map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
		int firstIndex = bp.getOffset();
		JSONObject jobj = new JSONObject();
		if (map.containsKey("itemname")) {
			jobj.put("total", 0);
			jobj.put("rows", new ArrayList<JSONObject>());
			return jobj;
		}
//		if (map.containsKey("ispaging")) {
//			if (map.get("ispaging").equals("1")) {
//				// 分页插件
//			}
//		}
		if(map.get("sortName1")!=null){
			int a=0;
			// 患者档案表
			if(map.get("sortName1").equals("cgivemoney")){
				map.put("sortName1", "rec.cgivemoney");
				a=1;
			}
			if(map.get("sortName1").equals("ctotal")){
				map.put("sortName1", "rec.ctotal");
				a=1;
			}
			if(map.get("sortName1").equals("remark")){
				map.put("sortName1", "rec.content");
				a=1;
			}
			if(map.get("sortName1").equals("developer")){
				map.put("sortName1", "p4.user_name");
				a=1;
			}
			if(map.get("sortName1").equals("sftime")){
				map.put("sortName1", "rec.createtime");
				a=1;
			}
			if(map.get("sortName1").equals("usercode")){
				map.put("sortName1", "rec.usercode");
				a=1;
			}
			if(map.get("sortName1").equals("blcode")){
				map.put("sortName1", "u.blcode");
				a=1;
			}
			if(map.get("sortName1").equals("username")){
				map.put("sortName1", "rec.username");
				a=1;
			}
			if(map.get("sortName1").equals("phonenumber1")){
				map.put("sortName1", "u.phonenumber1");
				a=1;
			}
			if(map.get("sortName1").equals("nextname")){
				map.put("sortName1", "rec.type");
				a=1;
			}
			if(map.get("sortName1").equals("realmoney")){
				map.put("sortName1", "rec.cmoney");
				a=1;
			}
			if(map.get("sortName1").equals("cmoney")){
				map.put("sortName1", "rec.cmoney");
				a=1;
			}
			if(map.get("sortName1").equals("payxj")){
				map.put("sortName1", "rec.xjmoney");
				a=1;
			}
			if(map.get("sortName1").equals("paybank")){
				map.put("sortName1", "rec.yhkmoney");
				a=1;
			}
			if(map.get("sortName1").equals("paywx")){
				map.put("sortName1", "rec.wxmoney");
				a=1;
			}
			if(map.get("sortName1").equals("payzfb")){
				map.put("sortName1", "rec.zfbmoney");
				a=1;
			}
			if(map.get("sortName1").equals("paymmd")){
				map.put("sortName1", "rec.mmdmoney");
				a=1;
			}
			if(map.get("sortName1").equals("paybdfq")){
				map.put("sortName1", "rec.bdfqmoney");
				a=1;
			}
			if(map.get("sortName1").equals("payother1")){
				map.put("sortName1", "rec.qtmoney");
				a=1;
			}
			if(map.get("sortName1").equals("recesort")){
				map.put("sortName1", "kcit1.dict_name");
				a=1;
			}
			if(map.get("sortName1").equals("regsort")){
				map.put("sortName1", "kcit2.dict_name");
				a=1;
			}
			if(map.get("sortName1").equals("faskperson")){
				map.put("sortName1", "p2.user_name");
				a=1;
			}
			if(map.get("sortName1").equals("askperson")){
				map.put("sortName1", "p8.user_name");
				a=1;
			}
			if(map.get("sortName1").equals("regdept")){
				map.put("sortName1", "dept2.DEPT_NAME");
				a=1;
			}
			if(map.get("sortName1").equals("doctor")){
				map.put("sortName1", "p9.USER_NAME");
				a=1;
			}
			if(map.get("sortName1").equals("devchannel")){
				map.put("sortName1", "kcit3.dict_name");
				a=1;
			}
			if(map.get("sortName1").equals("nexttype")){
				map.put("sortName1", "hzly.dict_name");
				a=1;
			}
			if(map.get("sortName1").equals("accepttype")){
				map.put("sortName1", "kcit5.dict_name");
				a=1;
			}
			if(map.get("sortName1").equals("accepttool")){
				map.put("sortName1", "kcit6.dict_name");
				a=1;
			}
			if(map.get("sortName1").equals("introducer")){
				map.put("sortName1", "u2.username");
				a=1;
			}
			if(map.get("sortName1").equals("jduser")){
				map.put("sortName1", "p3.user_name");
				a=1;
			}
            if(map.get("sortName1").equals("jddy")){
				map.put("sortName1", "per7.USER_NAME");
				a=1;
			}
			if(map.get("sortName1").equals("jdtime")){
				map.put("sortName1", "u.createtime");
				a=1;
			}
			if(map.get("sortName1").equals("sfuser")){
				map.put("sortName1", "p5.user_name");
				a=1;
			}
			if(a==0){
				map.put("sortName1", "");
			}
		}
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectListOrder", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		for (JSONObject obj : list) {
			// 治疗项目一、二级分类
			obj.put("classname", "预交金");
			obj.put("nextname", obj.getString("type"));
			obj.put("sftime", obj.getString("createtime"));
			obj.put("sfuser", obj.getString("sfr"));
			obj.put("regsort", obj.getString("regsort"));
			obj.put("recesort", obj.getString("recesort"));
			obj.put("kduser", "");
			obj.put("kdtime", "");
			obj.put("cjstatus", "");
			obj.put("nurse", "");
			obj.put("techperson", "");
			obj.put("remark", "");
			// 患者档案表
			obj.put("developer", obj.getString("kfr"));
			obj.put("introducer", obj.getString("jsr"));
			obj.put("type", "");
			obj.put("remark", obj.getString("content"));
			// 消费明细表
			obj.put("seqIdMember", obj.getString("seq_id"));
			obj.put("costno", "");
			obj.put("itemname", "");
			obj.put("itemno", "");
			obj.put("unit", "");
			obj.put("unitprice", "");
			obj.put("detailremark", "");
			obj.put("num", "");
			obj.put("discount", "");
			obj.put("realmoney", YZUtility.isNullorEmpty(obj.getString("cmoney")) ? "0.00" : new BigDecimal(obj.getString("cmoney")));
			obj.put("cmoney", YZUtility.isNullorEmpty(obj.getString("cmoney")) ? "0.00" : new BigDecimal(obj.getString("cmoney")));
			obj.put("cgivemoney", YZUtility.isNullorEmpty(obj.getString("cgivemoney")) ? "0.00" : new BigDecimal(obj.getString("cgivemoney")));
			obj.put("ctotal", YZUtility.isNullorEmpty(obj.getString("ctotal")) ? "0.00" : new BigDecimal(obj.getString("ctotal")));
			obj.put("subtotal", "");
			obj.put("arrearmoney", "");
			obj.put("ys", "");
			obj.put("paymoney", "");
			obj.put("voidmoney", "");
			if(!YZUtility.isNullorEmpty(obj.getString("doctor"))){
				obj.put("doctor", obj.getString("doctor"));
			}else{
				obj.put("doctor", obj.getString("doctorname"));
			}
			obj.put("regdept", obj.getString("regdept"));
			obj.put("status", "");
			obj.put("regno", "");
			obj.put("paydjq", "");
			obj.put("payintegral", "");
			obj.put("payxj", YZUtility.isNullorEmpty(obj.getString("xjmoney")) ? "0.00" : new BigDecimal(obj.getString("xjmoney")));
			obj.put("payyjj", "");
			obj.put("paybank", YZUtility.isNullorEmpty(obj.getString("yhkmoney")) ? "0.00" : new BigDecimal(obj.getString("yhkmoney")));
			obj.put("payyb", "");
			obj.put("paywx", YZUtility.isNullorEmpty(obj.getString("wxmoney")) ? "0.00" : new BigDecimal(obj.getString("wxmoney")));
			obj.put("payzfb", YZUtility.isNullorEmpty(obj.getString("zfbmoney")) ? "0.00" : new BigDecimal(obj.getString("zfbmoney")));
			obj.put("paymmd", YZUtility.isNullorEmpty(obj.getString("mmdmoney")) ? "0.00" : new BigDecimal(obj.getString("mmdmoney")));
			obj.put("paybdfq", YZUtility.isNullorEmpty(obj.getString("bdfqmoney")) ? "0.00" : new BigDecimal(obj.getString("bdfqmoney")));
			obj.put("payother1", YZUtility.isNullorEmpty(obj.getString("qtmoney")) ? "0.00" : new BigDecimal(obj.getString("qtmoney")));
			obj.put("payother2", "");
			obj.put("payother3", "");
			obj.put("istsxm", "");
			obj.put("y2", "");
			obj.put("istk", "");
			obj.put("issplit", "");
		}
		if (firstIndex == 0) {
//			int count = (int) dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".selectCountListOrder", map);
//			jobj.put("total", count);
			if (pageInfo.getTotal() > 0) {
				// 统计 费用表中 小计 应收 免除 欠款 缴费 等
				Map<String, String> wheremap = new HashMap<>();
				wheremap.putAll(map);
				wheremap.put("rec.cmoney", "cmoney");
				wheremap.put("rec.xjmoney", "xjmoney");
				wheremap.put("rec.yhkmoney", "yhkmoney");
				wheremap.put("rec.wxmoney", "wxmoney");
				wheremap.put("rec.zfbmoney", "zfbmoney");
				wheremap.put("rec.mmdmoney", "mmdmoney");
				wheremap.put("rec.bdfqmoney", "bdfqmoney");
				wheremap.put("rec.qtmoney", "qtmoney");
				//List<JSONObject> listmoney = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".getTotalNumFields", wheremap);
				JSONObject obj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".getTotalNumFields", wheremap);
				//for (JSONObject obj : listmoney) {
					jobj.put("realmoney", obj.getString("cmoney").toString());
					jobj.put("payxj", obj.getString("xjmoney").toString());
					jobj.put("paybank", obj.getString("yhkmoney").toString());
					jobj.put("payyb", "0.00");
					jobj.put("paywx", obj.getString("wxmoney").toString());
					jobj.put("payzfb", obj.getString("zfbmoney").toString());
					jobj.put("paymmd", obj.getString("mmdmoney").toString());
					jobj.put("paybdfq", obj.getString("bdfqmoney").toString());
					jobj.put("payother1", obj.getString("qtmoney").toString());
				//}
			}
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	// 查询期末余额
	@SuppressWarnings("unchecked")
	private JSONObject selectAllQm(String memberno, String endtime, String key1, String key2, String key3) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberno", memberno);
		if (!YZUtility.isNullorEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		JSONObject json = new JSONObject();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectAllQm", map);
		if (list != null && list.size() > 0) {
			JSONObject tmp = list.get(0);
			BigDecimal cmoney = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("cmoney")) ? "0.00" : tmp.getString("cmoney"));
			BigDecimal zzmoney = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("zzmoney")) ? "0.00" : tmp.getString("zzmoney"));
			BigDecimal cgivemoney = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("cgivemoney")) ? "0.00" : tmp.getString("cgivemoney"));
			BigDecimal zzgivemoney = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("zzgivemoney")) ? "0.00" : tmp.getString("zzgivemoney"));
			BigDecimal ctotal = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("ctotal")) ? "0.00" : tmp.getString("ctotal"));

			json.put(key1, cmoney.add(zzmoney));
			json.put(key2, cgivemoney.add(zzgivemoney));
			json.put(key3, ctotal.add(zzmoney).add(zzgivemoney));
		}
		return json;
	}

	/**
	 * 根据选择操作类型查询 【暂且不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectListByType(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectListByType", map);
		for (JSONObject job : list) {
			float zzmoney = 0;
			if (!YZUtility.isNullorEmpty(job.getString("zzmoney"))) {
				zzmoney = Float.parseFloat(job.getString("zzmoney"));
			}
			float zzgivemoney = 0;
			if (!YZUtility.isNullorEmpty(job.getString("zzgivemoney"))) {
				zzgivemoney = Float.parseFloat(job.getString("zzgivemoney"));
			}
			job.put("zztotal", zzmoney + zzgivemoney);
		}
		return list;
	}

	/**
	 * 打印页面 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param costno
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject printSfxm(String table, String seqId, HttpServletRequest request) throws Exception {
		JSONObject obj = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		map.put("seqId", seqId);
		map.put("tablename", table);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".printSfxm", map);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			if (!YZUtility.isNullorEmpty(rs.getString("xj"))) {
				if (rs.getDouble("xj") != 0) {
					obj.put(SysParaUtil.getFkfsNameByMemberField(request, "xjmoney"), rs.getString("xj"));
				}
			}
			if (!YZUtility.isNullorEmpty(rs.getString("bank"))) {
				if (rs.getDouble("bank") != 0) {
					obj.put(SysParaUtil.getFkfsNameByMemberField(request, "yhkmoney"), rs.getString("bank"));
				}
			}
			if (!YZUtility.isNullorEmpty(rs.getString("wx"))) {
				if (rs.getDouble("wx") != 0) {
					obj.put(SysParaUtil.getFkfsNameByMemberField(request, "wxmoney"), rs.getString("wx"));
				}
			}
			if (!YZUtility.isNullorEmpty(rs.getString("zfb"))) {
				if (rs.getDouble("zfb") != 0) {
					obj.put(SysParaUtil.getFkfsNameByMemberField(request, "zfbmoney"), rs.getString("zfb"));
				}
			}
			if (!YZUtility.isNullorEmpty(rs.getString("mmd"))) {
				if (rs.getDouble("mmd") != 0) {
					obj.put(SysParaUtil.getFkfsNameByMemberField(request, "mmdmoney"), rs.getString("mmd"));
				}
			}
			if (!YZUtility.isNullorEmpty(rs.getString("bdfq"))) {
				if (rs.getDouble("bdfq") != 0) {
					obj.put(SysParaUtil.getFkfsNameByMemberField(request, "bdfqmoney"), rs.getString("bdfq"));
				}
			}
			if (!YZUtility.isNullorEmpty(rs.getString("other1"))) {
				if (rs.getDouble("other1") != 0) {
					obj.put(SysParaUtil.getFkfsNameByMemberField(request, "qtmoney"), rs.getString("other1"));
				}
			}
		}
		return obj;
	}

	/**
	 * 预交金退单 验证（查询预交金单结账时间节点之后，是否存在会员卡的 缴费和转赠和退费记录）
	 * 
	 * @param conn
	 * @param table
	 * @param costno
	 * @return
	 * @throws Exception
	 */
	public int yjjTuidan(String cardno, String datetime) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cardno", cardno);
		map.put("datetime", datetime);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".yjjTuidan", map);
		return count;
	}

	/**   
	  * @Title: findAcceptingGold   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年10月23日 下午2:41:39
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAcceptingGold(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD+".findAcceptingGold", map);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject selectNoPageBySfmx(BootStrapPage bp, String table, YZPerson person, Map<String, String> map, String visualstaff, String organization) throws Exception {
		int firstIndex = bp.getOffset();
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}

		map.put("visualstaff", visualstaff);
		map.put("organization", organization);
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "rec.remark");
			}
			if(map.get("sortName").equals("sfuser")){
				map.put("sortName", "per1.USER_NAME");
			}
			if(map.get("sortName").equals("jdtime")){
				map.put("sortName", "u.CreateTime");
			}
			if(map.get("sortName").equals("jddy")){
				map.put("sortName", "per7.USER_NAME");
			}
			if(map.get("sortName").equals("jduser")){
				map.put("sortName", "per3.USER_NAME");
			}
			if(map.get("sortName").equals("developer")){
				map.put("sortName", "per6.USER_NAME");
			}
			if(map.get("sortName").equals("introducer")){
				map.put("sortName", "u2.username");
			}
			if(map.get("sortName").equals("kdtime")){
				map.put("sortName", "rec.createtime");
			}
			if(map.get("sortName").equals("kduser")){
				map.put("sortName", "per1.USER_NAME");
			}
			if(map.get("sortName").equals("nexttype")){
				map.put("sortName", "hzly.dict_name");
			}
			if(map.get("sortName").equals("devchannel")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "per4.USER_NAME");
			}
			if(map.get("sortName").equals("faskperson")){
				map.put("sortName", "per10.USER_NAME");
			}
			if(map.get("sortName").equals("regsort")){
				map.put("sortName", "kcit2.dict_name");
			}
			
			if(map.get("sortName").equals("paymoney")){
				map.put("sortName", "rec.cmoney");
			}
			if(map.get("sortName").equals("organization")){
				map.put("sortName", "dept.DEPT_NAME");
			}
			if(map.get("sortName").equals("sftime")){
				map.put("sortName", "rec.createtime");
			}
			
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "rec.usercode");
			}
			if(map.get("sortName").equals("blcode")){
				map.put("sortName", "u.blcode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "rec.username");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.PhoneNumber1");
			}
			if(map.get("sortName").equals("subtotal")){
				map.put("sortName", "rec.cmoney");
			}
		}
		
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(table+ ".selectNoPageBySfmx", map);
		for (JSONObject obj : list) {
			obj.put("dict_name", "");
			obj.put("dict_name_2", "");
			obj.put("cjstatus", "已成交");
			obj.put("istsxm", "");
			obj.put("y2", "0");
			obj.put("voidmoney", "0");
			obj.put("qfbh", "");
			obj.put("arrearmoney", "0");
			obj.put("istk", "0");
			obj.put("payother2", "0");
			obj.put("paydjq", "0");
			obj.put("payintegral", "0");
		}
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		// 第一次加载数量，分页查询时不加载
		if (firstIndex == 0) {
			if (list.size() > 0) {
				JSONObject obj = (JSONObject) dao.findForObject(table+ ".selectNoPageBySfmxNum", map);
				jobj.put("cmoney", obj.getString("cmoney"));
			}
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
}