package com.kqds.service.base.reg;

//合并测试
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsJzqk;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hospitalOrder.KQDS_Hospital_OrderLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.jzmdType.KQDS_JzqkLogic;
import com.kqds.service.base.room.KQDS_RoomLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class KQDS_REGLogic extends BaseLogic {
	@Autowired
	private KQDS_RoomLogic roomlogic;
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_UserDocumentLogic userlogic;
	@Autowired
	private KQDS_JzqkLogic jlogic;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private KQDS_Hospital_OrderLogic hospitalOrderLogic;
	
	/**
	 * 统计该患者初诊数
	 * 
	 * @param dbConn
	 * @param usercode
	 * @param czIdStrs
	 * @return
	 * @throws Exception
	 */
	public int countCzReg(String usercode, String czIdStrs) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("recesort", czIdStrs);
		int num = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".countCzReg", map);
		return num;
	}

	/**
	 * 根据患者编号，获取上一次挂号信息
	 * 
	 * @param conn
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	public List<JSONObject> getLastestRegInfo(String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getLastestRegInfo", usercode);
		return list;
	}

	/**
	 * 查询患者24小时内的初诊挂号记录
	 * 
	 * @param conn
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	public List<JSONObject> getChuZhenIn24Hours(String usercode, HttpServletRequest request) throws Exception {
		String czSeqId = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID); // 就诊分类-初诊
		Map<String, String> map = new HashMap<String, String>();
		map.put("recesort", czSeqId);
		map.put("usercode", usercode);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getChuZhenIn24Hours", usercode);
		return list;
	}

	/**
	 * ##################################挂号新增、挂号修改、挂号撤销操作涉及到的代码抽取方法汇总###########
	 * ########################
	 **/

	/**
	 * 修改挂号时，对咨询记录数据并做相应处理
	 * 
	 * @param dp
	 * @param regold
	 * @param dbConn
	 * @param userdoc
	 * @param person
	 * @throws Exception
	 */
	public void checkReceive4RegEdit(KqdsReg dp, HttpServletRequest request) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("regno", dp.getSeqId());
		List<KqdsReceiveinfo> list = (List<KqdsReceiveinfo>) dao.loadList(TableNameUtil.KQDS_RECEIVEINFO, map1);
		if (list != null && list.size() > 0) {
			KqdsReceiveinfo rece = list.get(0);
			rece.setAskperson(dp.getAskperson());
			rece.setRecesort(dp.getRegsort());
			dao.updateSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, rece); // 修改挂号记录
		}
	}

	/**
	 * 修改挂号时，对就诊情况数据并做相应处理，如果选择医生，更新患者档案表的医生数据
	 * 
	 * @param dp
	 * @param regold
	 * @param dbConn
	 * @param userdoc
	 * @param person
	 * @throws Exception
	 */
	public void checkJzqk4RegEdit(KqdsReg dp, KqdsReg regold, KqdsUserdocument userdoc, YZPerson person, HttpServletRequest request, String regsortname) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("regno", dp.getSeqId());
		map1.put("doctor", regold.getDoctor());
		// ### 如果之前的医生已接诊了 就不删了
		// 如果修改挂号时，没有选医生
		if (YZUtility.isNullorEmpty(dp.getDoctor())) {
			// ### 如果之前的医生已接诊了 就不删了
			dao.delete(TableNameUtil.KQDS_REG + ".deletebyregno", map1);
		} else { // ### 修改挂号时，选了医生
			// ### 更新患者档案表的医生数值
			userdoc.setDoctor(dp.getDoctor());
			dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, userdoc);

			if (YZUtility.isNullorEmpty(regold.getDoctor())) {
				// ### 修改之前，如果没选医生，则直接插入一条就诊情况记录
				checkUpdateInsertJZQK(dp, person, ChainUtil.getCurrentOrganization(request), request);
			} else { // ### 修改之前，选了医生
				if (dp.getDoctor().equals(regold.getDoctor())) {
					// ### do nothing ...
				} else {
					// ###
					// ### 如果之前的医生已接诊了 就不删了
					dao.delete(TableNameUtil.KQDS_REG + ".deletebyregno", map1);
					checkUpdateInsertJZQK(dp, person, ChainUtil.getCurrentOrganization(request), request);
				}
			}
			// 添加消息提示，选了医生就添加
			PushUtil.saveTx4ModifyReg(dp, regsortname, request);
		} // -------------------------------------------针对就诊情况的判断 end
	}

	/**
	 * 如果是门诊患者挂号，检测门诊预约数据，以最新的挂号数据为准进行更新
	 * 
	 * @param dp
	 * @param dbConn
	 * @param request
	 * @throws Exception
	 */
	public void checkHosOrder(KqdsReg dp, HttpServletRequest request) throws Exception {

		String orderno = request.getParameter("orderno");
		String nowdatestr = YZUtility.getDateStr(new Date());
		if (!YZUtility.isNullorEmpty(orderno)) {
			KqdsHospitalOrder hos = (KqdsHospitalOrder) dao.loadObjSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, orderno);
			hos.setOrderstatus(1);// 已上门
			hos.setRegno(dp.getSeqId());// 挂号编号
			hos.setRegdept(dp.getRegdept());// 安排科室
			hos.setOrdertime(dp.getCreatetime());
			dao.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, hos);
		} else {
			// 判断该患者今天是否有门诊预约 如果有 则修改状态为已上门 并且保存挂号编号等信息
			List<JSONObject> orderList = getTodayHosOrderByOrdertimeAndUsercode(nowdatestr, dp.getUsercode());
			for (JSONObject job : orderList) {
				KqdsHospitalOrder hos = (KqdsHospitalOrder) JSONObject.toBean(job, KqdsHospitalOrder.class);
				hos.setOrderstatus(1);// 已上门
				hos.setRegno(dp.getSeqId());// 挂号编号
				hos.setRegdept(dp.getRegdept());// 安排科室
				hos.setOrdertime(dp.getCreatetime());
				dao.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, hos);
			}
		}
	}

	/**
	 * 如果是网电患者挂号，检测网电预约数据
	 * 
	 * @param dp
	 * @param dbConn
	 * @param request
	 * @throws Exception
	 */
	public void checkNetOrder(KqdsReg dp, HttpServletRequest request) throws Exception {

		String netorderid = request.getParameter("netorderid");
		String nowdatestr = YZUtility.getDateStr(new Date());

		/***
		 * 注意事项： 1、同一个患者，每天只允许有一个网电预约记录 2、如果当前有网电预约，则直接更新； 如果没有，则判断是否存在提前到诊
		 * 如果还没有，则取最新的一个ordertime为空的网电预约数据进行更新
		 */
		KqdsNetOrder netOrder = null;
		// 查询当天是否存在网电预约
		List<KqdsNetOrder> orderList = getTodayNetOrderByOrdertimeAndUsercode(nowdatestr, dp.getUsercode());

		if (orderList == null || orderList.size() == 0) { // 当天没有网电预约
			if (!YZUtility.isNullorEmpty(netorderid)) { // 处理直接选择网电记录进行预约的情况
				KqdsNetOrder netTmp = (KqdsNetOrder) dao.loadObjSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorderid);
				if (netTmp == null) {
					throw new Exception("网电预约记录不存在，预约主键为：" + netorderid);
				}

				if (0 == netTmp.getDoorstatus()) { // 不是已上门的
					netOrder = netTmp;
				}
			}

			// 此时，表示选择的网电预约记录，不是提前到诊
			if (netOrder == null) {
				// 查找没有指定预约时间的网电预约记录(查最新的一条网电预约)
				orderList = this.getTodayNetOrderByOrdertimeAndUsercode("", dp.getUsercode());
				if ((orderList != null) && (orderList.size() > 0)) {
					netOrder = (KqdsNetOrder) orderList.get(0);
				}
			}
			if (netOrder != null) {
				netOrder.setDoorstatus(1);
				netOrder.setRegdept(dp.getRegdept());
				netOrder.setOrderperson(dp.getDoctor()); // 安排医生
				netOrder.setAskperson(dp.getAskperson());
				netOrder.setRegno(dp.getSeqId());
				netOrder.setDaoyi(dp.getCreateuser());
				netOrder.setGuidetime(dp.getCreatetime());
				dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, netOrder);
			}

		} else {
			for (KqdsNetOrder netorder2 : orderList) {
				netorder2.setDoorstatus(1);
				netorder2.setRegdept(dp.getRegdept());
				netorder2.setOrderperson(dp.getDoctor()); // 安排医生
				netorder2.setAskperson(dp.getAskperson());
				netorder2.setRegno(dp.getSeqId());
				netorder2.setDaoyi(dp.getCreateuser());
				netorder2.setGuidetime(dp.getCreatetime());
				dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder2);
			}
		}

	}

	/**
	 * 根据挂号编号，删除状态为未接诊的咨询记录
	 * 
	 * @param regno
	 * @param orm
	 * @param dbConn
	 * @throws Exception
	 */
	public void deleteReceiveInfoByRegNo(String regno, HttpServletRequest request) throws Exception {
		Map<String, String> mapzx = new HashMap<String, String>();
		mapzx.put("regno", regno);
		List<KqdsReceiveinfo> receiveList = (List<KqdsReceiveinfo>) dao.loadList(TableNameUtil.KQDS_RECEIVEINFO, mapzx);

		for (KqdsReceiveinfo receive : receiveList) {

			String receive_seqId = receive.getSeqId();
			if (!YZUtility.isNullorEmpty(receive_seqId)) {
				dao.delete(TableNameUtil.KQDS_REG + ".deletereceive", receive_seqId);
			}
			dao.deleteSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, receive_seqId);
		}
	}

	/**
	 * 判断患者是否是 就诊情况的 复诊患者 【不做门诊条件过滤】
	 * 
	 * @param dp
	 * @param dbConn
	 * @param person
	 * @param orm
	 * @throws Exception
	 */
	public void checkUpdateInsertJZQK(KqdsReg dp, YZPerson person, String organization, HttpServletRequest request) throws Exception {
		// ### 根据医生、istx状态和dzdata进行查询，查询该医生对应的到诊时间为空的提醒记录
		List<JSONObject> list = jlogic.jzFz(dp.getUsercode(), dp.getDoctor());
		for (JSONObject job : list) {
			KqdsJzqk jzqk = (KqdsJzqk) JSONObject.toBean(job, KqdsJzqk.class);
			// 一次挂号 一个患者 对应一个医生只有一个就诊情况
			// 更新就诊情况记录的到诊时间（存在未到诊的情况）
			jzqk.setDzdata(dp.getCreatetime());
			jzqk.setDzregno(dp.getSeqId());
			dao.updateSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
		}
		// 生成一条新的就诊情况，可能存在 复诊的患者 不需要开单 结账
		KqdsJzqk jzqk = new KqdsJzqk();
		jzqk.setSeqId(YZUtility.getUUID());
		jzqk.setRegno(dp.getSeqId());
		jzqk.setDoctor(dp.getDoctor());
		jzqk.setCreatetime(YZUtility.getCurDateTimeStr());
		jzqk.setCreateuser(person.getSeqId());
		jzqk.setOrganization(organization);// ### 【以当前所在门诊为主】
		dao.saveSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
	}

	/**
	 * 生成咨询记录，内部调用
	 * 
	 * @param dbConn
	 * @param dp
	 * @throws Exception
	 */
	public KqdsReceiveinfo saveReceive(KqdsReg dp, KqdsUserdocument userdoc, String organization, HttpServletRequest request) throws Exception {

		KqdsReceiveinfo receive = new KqdsReceiveinfo();
		try {
			receive.setSeqId(YZUtility.getUUID());
			receive.setUsercode(dp.getUsercode());
			receive.setAskperson(dp.getAskperson());
			receive.setRegno(dp.getSeqId());
			receive.setUsername(userdoc.getUsername());
			receive.setDeptno(dp.getRegdept());
			receive.setRecesort(dp.getRegsort() + "");
			receive.setCreatetime(YZUtility.getCurDateTimeStr());
			receive.setCreateuser(dp.getCreateuser());
			receive.setOrganization(organization); // ### 【以当前所在门诊为主】
			dao.saveSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, receive);
		} catch (Exception ex) {
			throw ex;
		}
		return receive;
	}

	/**
	 * ##################################挂号新增、挂号修改、挂号撤销操作涉及到的代码抽取方法汇总
	 * END################################
	 **/

	/**
	 * 接待中心-等待治疗列表 【只查询当前所在门诊，Organization直接从登录用户的session中获取】
	 * 
	 * @param conn
	 * @param table
	 * @param person
	 * @param status
	 * @param querytype
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	public JSONObject selectDzlmenu(String table, YZPerson person,String sortName,String sortOrder, int status, String querytype, String searchValue, String visualstaff, String organization,
			HttpServletRequest request,BootStrapPage bp) throws Exception {
		String sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID); // 获取收费角色
		Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			if (!YZUtility.isStrInArrayEach(person.getUserPriv(), sfpriv)) {
				map.put("querytype", visualstaff);
			}
		}
		
		String recesort = request.getParameter("recesort");
		if(YZUtility.isNotNullOrEmpty(recesort)) {
			map.put("recesort", recesort);
		}
		map.put("status", status + "");
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(searchValue)) {
			map.put("searchValue", searchValue);
			map.put("p1", YZAuthenticator.phonenumberLike("us.PhoneNumber1", map.get("searchValue")));
			map.put("p2", YZAuthenticator.phonenumberLike("us.PhoneNumber2", map.get("searchValue")));
		}
		if(!YZUtility.isNullorEmpty(sortName)){
			map.put("sortOrder",sortOrder);
			if(sortName.equals("createtime")){
				map.put("sortName", "reg.createtime");
			}
			if(sortName.equals("recesortname")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(sortName.equals("regsortname")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(sortName.equals("usercode")){
				map.put("sortName", "reg.usercode");
			}
			if(sortName.equals("username")){
				map.put("sortName", "reg.username");
			}
			if(sortName.equals("iscreatelclj")){
				map.put("sortName", "us.iscreateLclj");
			}
			if(sortName.equals("sex")){
				map.put("sortName", "us.sex");
			}
			if(sortName.equals("age")){
				map.put("sortName", "us.age");
			}
			if(sortName.equals("cjstatus")){
				map.put("sortName", "reg.cjstatus");
			}
			if(sortName.equals("askpersonname")){
				map.put("sortName", "per1.user_name");
			}
			if(sortName.equals("doctorname")){
				map.put("sortName", "per2.user_name");
			}
			if(sortName.equals("repairdoctorname")){
				map.put("sortName", "per5.user_name");
			}
			if(sortName.equals("remark")){
				map.put("sortName", "us.remark");
			}
			if(sortName.equals("devchannelname")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(sortName.equals("nexttypename")){
				map.put("sortName", "hzly.dict_name");
			}
			if(sortName.equals("ifcost")){
				map.put("sortName", "reg.ifcost");
			}
			if(sortName.equals("createusername")){
				map.put("sortName", "per3.user_name");
			}
			if(sortName.equals("receivenoname")){
				map.put("sortName", "per4.user_name");
			}
			if(sortName.equals("editreason")){
				map.put("sortName", "reg.editreason");
			}
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectDzlmenu", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();	
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);	
		if(bp.getOffset()==0){
			JSONObject jzfljobj = new JSONObject();
			List<YZDict> jzflDict = dictLogic.getListByParentCodeALL(DictUtil.JZFL, ChainUtil.getCurrentOrganization(request));
			List<JSONObject> jzflDictList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectDzlmenuRecesortNum", map);
			int nums=0;
			jzfljobj.put("总数",nums);
			for (YZDict dict : jzflDict) {
				jzfljobj.put(dict.getDictName(),0);
				for (JSONObject obj : jzflDictList) {
					if (obj.getString("recesort").equals(dict.getSeqId())) {
						nums=nums+Integer.parseInt(obj.getString("num"));
						jzfljobj.put(dict.getDictName(), obj.getString("num"));
					}
				}
			}
			jzfljobj.put("总数", nums);
			jobj.put("jzfl", jzfljobj);
		}
		return jobj;
	}

	// 就诊情况
	public JSONObject selectJzqk(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization,BootStrapPage bp) throws Exception {
		if (map.containsKey("usercodeorname")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("usercodeorname")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("usercodeorname")));
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		map.put("visualstaff", visualstaff);
		if(!YZUtility.isNullorEmpty(map.get("sortName"))){
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "reg.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "reg.username");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			if(map.get("sortName").equals("cjstatus")){
				map.put("sortName", "reg.cjstatus");
			}
			if(map.get("sortName").equals("recesort")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("regsort")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(map.get("sortName").equals("doctor")){
				map.put("sortName", "per5.user_name");
			}
			if(map.get("sortName").equals("reggoal")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(map.get("sortName").equals("jzmdname")){
				map.put("sortName", "kcit4.dict_name");
			}
			if(map.get("sortName").equals("ifmedrecord")){
				map.put("sortName", "reg.ifmedrecord");
			}
			if(map.get("sortName").equals("ifqf")){
				map.put("sortName", "reg.SEQ_ID");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "per4.user_name");
			}
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "reg.createtime");
			}
			if(map.get("sortName").equals("createuser")){
				map.put("sortName", "per6.user_name");
			}
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "j.remark");
			}
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectJzqk", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		return jobj1;
	}

	public List<JSONObject> selectByusercode(String table, String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectByusercode", usercode);
		return list;
	}

	// 修改挂号状态
	public void editStatus(String doctor, String seqId, int ststus, HttpServletRequest request) throws Exception {
		KqdsReg reg = (KqdsReg) dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
		if (reg != null) {
			// 就诊科室
			YZPerson doctorPer = (YZPerson) dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, doctor);
			if (doctorPer != null) {
				reg.setRegdept(doctorPer.getDeptId());
			}
			//防止开单医生与挂号医生不符，挂号医生被覆盖
			//reg.setDoctor(doctor);
			/** 如果连续开两个单，医生不一样，则以后面的为准？ **/
			if (ststus >= 2) { // 这里的值，永远都不会大于等于2
				reg.setIfcost(1);
			}
			reg.setStatus(ststus); // 这里的status值只会是1
			dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
		}
	}

	/**
	 * 修改挂号记录的状态信息，如就诊科室
	 * 
	 * @param conn
	 * @param doctor
	 *            费用单中的医生
	 * @param seqId
	 *            挂号记录主键
	 * @param ststus
	 *            0 未成交 1已成交
	 * @param request
	 * @throws Exception
	 */
	public void editCjstatus(String doctor, String seqId, int ststus, HttpServletRequest request) throws Exception {
		KqdsReg reg = (KqdsReg) dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
		if (reg != null) {
			//避免更改挂号医生
			/*if (YZUtility.isNullorEmpty(reg.getDoctor()) && YZUtility.isNotNullOrEmpty(doctor)) {
				//结账预交金的单子时，医生可能会不存在
				reg.setDoctor(doctor);
			}*/
			if (1 != reg.getCjstatus()) {// 如果之前状态值为已成交 ，则不更新;不是已成交则更新
				reg.setCjstatus(ststus);// 成交状态
			}
			reg.setStatus(2);// 已结账
			reg.setIfcost(1);// 已消费

			if (YZUtility.isNotNullOrEmpty(reg.getDoctor())) {
				/** 结账预交金的单子时，医生可能会不存在 **/
				// 就诊科室赋值
				YZPerson per = (YZPerson) dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, reg.getDoctor());
				if (per != null) {
					reg.setRegdept(per.getDeptId());
				}
			}

			/** 这儿不能写死其他科室，没法保证每个医院都有其他科室 **/
			// else {
			// YZDept dept = deptLogic.getDeptByTypeAndName(
			// ConstUtil.DEPT_TYPE_1, "其他科室");
			// if (dept != null) {
			// reg.setRegdept(dept.getSeqId());
			// }
			// }
			dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
		}
	}

	// 修改是否填写病历
	public void setIfmedRecord(String seqId, int ifmedrecord, HttpServletRequest request) throws Exception {

		KqdsReg reg = (KqdsReg) dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
		if (reg != null) {
			reg.setIfmedrecord(ifmedrecord);
			dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
		}
	}

	// 修改咨询
	public void editAskperson(String askperson, String seqId, HttpServletRequest request) throws Exception {
		KqdsReg reg = (KqdsReg) dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
		if (reg == null) {
			throw new Exception("挂号记录不存在，seqId为：" + seqId);
		}
		reg.setAskperson(askperson);
		dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
	}

	// 挂号时判断该病人当天是否有网电预约 有的话则修改上门状态为已上门
	public List<KqdsNetOrder> getTodayNetOrderByOrdertimeAndUsercode(String ordertime, String usercode) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("ordertime", ordertime);
		List<KqdsNetOrder> list = (List<KqdsNetOrder>) dao.findForList(TableNameUtil.KQDS_REG + ".getTodayNetOrderByOrdertimeAndUsercode", map);
		return list;
	}

	// 挂号时判断该病人当天是否有门诊预约 有的话则修改上门状态为已上门
	public List<JSONObject> getTodayHosOrderByOrdertimeAndUsercode(String ordertime, String usercode) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("ordertime", ordertime);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getTodayHosOrderByOrdertimeAndUsercode", map);
		return list;
	}

	public JSONObject selectJzcx(BootStrapPage bp, String table, YZPerson person, Map<String, String> map, List<YZDict> jzflDict, String organization, String visualstaff,
			HttpServletRequest request,String flag) throws Exception {
		String sfpriv="";
		if(person!=null){
			sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID); // 获取收费角色
		}
		if (map.containsKey("searchValue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("searchValue")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("searchValue")));
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if(person!=null){
			if (!YZUtility.isStrInArrayEach(person.getUserPriv(), sfpriv)) {
				map.put("visualstaff", visualstaff);
//				//查询可见人的数据
//				List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectRegnoByVisualstaff", map);
//				List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEDICALRECORD + ".selectRegnoByVisualstaff", map);
//				List<JSONObject> list2 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectUsercodeByVisualstaff", map);
//				if(list.size()>0){
//					StringBuilder str = new StringBuilder();
//					for (int i = 0; i < list.size(); i++) {
//						if(i==list.size()-1){
//							str.append("\'"+list.get(i).getString("regno")+"\'");
//						}else{
//							str.append("\'"+list.get(i).getString("regno")+"\',");
//						}
//					}
//					map.put("costorderRegno", str.toString());
//				}
//				if(list1.size()>0){
//					StringBuilder str = new StringBuilder();
//					for (int i = 0; i < list1.size(); i++) {
//						if(i==list1.size()-1){
//							str.append("\'"+list1.get(i).getString("regno")+"\'");
//						}else{
//							str.append("\'"+list1.get(i).getString("regno")+"\',");
//						}
//					}
//					map.put("medicalrecordRegno", str.toString());
//				}
//				if(list2.size()>0){
//					StringBuilder str = new StringBuilder();
//					for (int i = 0; i < list2.size(); i++) {
//						if(i==list2.size()-1){
//							str.append("\'"+list2.get(i).getString("usercode")+"\'");
//						}else{
//							
//							str.append("\'"+list2.get(i).getString("usercode")+"\',");
//						}
//					}
//					map.put("memberrecordUsercode", str.toString());
//				}
			}	
		}
		//根据可见人查询数据
		int firstIndex=0;
		if(bp!=null){
			firstIndex = bp.getOffset();
		}
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "r.createtime");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "r.usercode");
			}
			if(map.get("sortName").equals("blcode")){
				map.put("sortName", "u.blcode");
			}
			if(map.get("sortName").equals("failreason1")){
				map.put("sortName", "i.failreason1");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "r.username");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			if(map.get("sortName").equals("sex")){
				map.put("sortName", "u.sex");
			}
			if(map.get("sortName").equals("age")){
				map.put("sortName", "u.age");
			} 
			if(map.get("sortName").equals("provincename")){
				map.put("sortName", "u.province");
			}
			if(map.get("sortName").equals("cityname")){
				map.put("sortName", "u.city");
			}
			if(map.get("sortName").equals("townname")){
				map.put("sortName", "u.area");
			}
			if(map.get("sortName").equals("streetName")){
				map.put("sortName", "u.town");
			}
			if(map.get("sortName").equals("important")){
				map.put("sortName", "u.important");
			}
			if(map.get("sortName").equals("cjstatus")){
				map.put("sortName", "r.cjstatus");
			}
			if(map.get("sortName").equals("recesort")){
				map.put("sortName", "r.recesort");
			}
			if(map.get("sortName").equals("regsort")){
				map.put("sortName", "r.regsort");
			}
			if(map.get("sortName").equals("faskperson")){
				map.put("sortName", "u.askperson");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "r.askperson");
			}
			if(map.get("sortName").equals("regdept")){
				map.put("sortName", "r.regdept");
			}
			if(map.get("sortName").equals("doctor")){
				map.put("sortName", "r.doctor");
			}
			if(map.get("sortName").equals("devchannel")){
				map.put("sortName", "u.devchannel");
			}
			if(map.get("sortName").equals("nexttype")){
				map.put("sortName", "u.nexttype");
			}
			if(map.get("sortName").equals("slgj")){
				map.put("sortName", "u.accepttool");
			}
			if(map.get("sortName").equals("jf")){
				map.put("sortName", "(select sum(c.actualmoney) from KQDS_COSTORDER c where c.regno = r.SEQ_ID and c.status=2 )");
			}
			if(map.get("sortName").equals("devitemdesc")){
				map.put("sortName", "i.dev_item");
			}
			if(map.get("sortName").equals("ifmedrecord")){
				map.put("sortName", "r.ifmedrecord");
			}
			if(map.get("sortName").equals("detaildesc")){
				map.put("sortName", "i.detaildesc");
			}
			if(map.get("sortName").equals("jdr")){
				map.put("sortName", "u.createuser");
			}
			if(map.get("sortName").equals("developername")){
				map.put("sortName", "u.developer");
			}
			if(map.get("sortName").equals("jddy")){
				map.put("sortName", "u.guider");
			}
			if(map.get("sortName").equals("createuser")){
				map.put("sortName", "r.createuser");
			}
			if(map.get("sortName").equals("dy")){
				map.put("sortName", "r.receiveno");
			}
		}
		
		
		if(bp!=null){
			PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		}
		if (flag != null && flag.equals("exportTable")) {
			List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectJzcx", map);
			List<String> list1= new ArrayList<String>();
			for (JSONObject job : list) {
				String important = job.getString("important");
				if ("1".equals(important)) {
					important = "一级";
				} else if ("2".equals(important)) {
					important = "二级";
				} else if ("3".equals(important)) {
					important = "三级";
				} else if ("4".equals(important)) {
					important = "四级";
				} else if ("5".equals(important)) {
					important = "五级";
				}
				job.put("important", important);

				String cjstatusTmp = job.getString("cjstatus");
				if ("1".equals(cjstatusTmp)) {
					cjstatusTmp = "已成交";
				} else {
					cjstatusTmp = "未成交";
				}
				job.put("cjstatus", cjstatusTmp);

				String ifmedrecordTmp = job.getString("ifmedrecord");
				if ("1".equals(ifmedrecordTmp)) {
					ifmedrecordTmp = "已填写";
				} else {
					ifmedrecordTmp = "未填写";
				}
				job.put("ifmedrecord", ifmedrecordTmp);
				list1.add(job.getString("seq_id"));
			}
			PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
			JSONObject jobj = new JSONObject();
			jobj.put("total", pageInfo.getTotal());
			jobj.put("rows", list);
			return jobj;
		}else{
			List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectJzcx", map);
			//挂号id
			StringBuilder str = new StringBuilder(); 
			//患者编号
			StringBuilder str1 = new StringBuilder(); 
			for (int i = 0; i <list.size(); i++) {
				String important = list.get(i).getString("important");
				if ("1".equals(important)) {
					important = "一级";
				} else if ("2".equals(important)) {
					important = "二级";
				} else if ("3".equals(important)) {
					important = "三级";
				} else if ("4".equals(important)) {
					important = "四级";
				} else if ("5".equals(important)) {
					important = "五级";
				}
				list.get(i).put("important", important);
				String cjstatusTmp = list.get(i).getString("cjstatus");
				if ("1".equals(cjstatusTmp)) {
					cjstatusTmp = "已成交";
				} else {
					cjstatusTmp = "未成交";
				}
				list.get(i).put("cjstatus", cjstatusTmp);

				String ifmedrecordTmp = list.get(i).getString("ifmedrecord");
				if ("1".equals(ifmedrecordTmp)) {
					ifmedrecordTmp = "已填写";
				} else {
					ifmedrecordTmp = "未填写";
				}
				list.get(i).put("ifmedrecord", ifmedrecordTmp);
				if(i==list.size()-1){
					str1.append("\'"+list.get(i).getString("usercode")+"\'");
					str.append("\'"+list.get(i).getString("seq_id")+"\'");
				}else{
					str1.append("\'"+list.get(i).getString("usercode")+"\',");
					str.append("\'"+list.get(i).getString("seq_id")+"\',");
				}
				list.get(i).put("ssje", "0.00");
				list.get(i).put("streetName", "");
				list.get(i).put("provincename", "");
				list.get(i).put("cityname", "");
				list.get(i).put("townname", "");
				list.get(i).put("jdr", "");
				list.get(i).put("doctor", "");
				list.get(i).put("createuser", "");
				list.get(i).put("dy", "");
				list.get(i).put("recesort", "");
				list.get(i).put("regsort", "");
				list.get(i).put("failreason1", "");
				list.get(i).put("devitemdesc", "");
				list.get(i).put("jdr","");
				list.get(i).put("jddy", "");
				list.get(i).put("faskperson", "");
				list.get(i).put("developername", "");
				list.get(i).put("devchannel", "");
				list.get(i).put("nexttype", "");
				list.get(i).put("slgj", "");
			}
			if(str.length()>0){
				List<JSONObject> ssje = userlogic.getSsjeReg(str.toString());
				if(ssje.size()>0){
					for (JSONObject jsonObject : list) {
						for (JSONObject jsonObject1 : ssje) {
							if(jsonObject.getString("seq_id").equals(jsonObject1.getString("regno"))){
								jsonObject.put("ssje", jsonObject1.getString("ssje"));
							}
						}
					}
				}
				Map<String,String> map1 = new HashMap<String,String>();
				map1.put("seqid", str.toString());
				List<JSONObject> nameList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG+".selectNameBySeqid", map1);
				if(nameList.size()>0){
					for (JSONObject jsonObject : list) {
						for (JSONObject jsonObject1 : nameList) {
							if(jsonObject.getString("seq_id").equals(jsonObject1.getString("seqid"))){			
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("askperson"))){
									jsonObject.put("askperson", jsonObject1.getString("askperson"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("doctor"))){
									jsonObject.put("doctor", jsonObject1.getString("doctor"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("createuser"))){
									jsonObject.put("createuser", jsonObject1.getString("createuser"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("dy"))){
									jsonObject.put("dy", jsonObject1.getString("dy"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("recesort"))){
									jsonObject.put("recesort", jsonObject1.getString("recesort"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("regsort"))){
									jsonObject.put("regsort", jsonObject1.getString("regsort"));
								}
							}
						}
					}
				}
				List<JSONObject> dictNameList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_RECEIVEINFO+".selectNameByRegno", map1);
				if(dictNameList.size()>0){
					for (JSONObject jsonObject : list) {
						for (JSONObject jsonObject1 : dictNameList) {
							if(jsonObject.getString("seq_id").equals(jsonObject1.getString("regno"))){	
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("failreason1"))){
									jsonObject.put("failreason1", jsonObject1.getString("failreason1"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("devitemdesc"))){
									jsonObject.put("devitemdesc", jsonObject1.getString("devitemdesc"));
								}
							}
						}
					}
				}
//				List<JSONObject> slgjList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG+".selectSlgjBySeqid", map1);
//				if(slgjList.size()>0){
//					for (JSONObject jsonObject : list) {
//						jsonObject.put("slgj", "");
//						for (JSONObject jsonObject1 : slgjList) {
//							if(jsonObject.getString("seq_id").equals(jsonObject1.getString("seqid"))){	
//								if(!YZUtility.isNullorEmpty(jsonObject1.getString("slgj"))){
//									jsonObject.put("slgj", jsonObject1.getString("slgj"));
//								}
//							}
//						}
//					}
//				}
				
			}	
			if(str1.length()>0){
				Map<String,String> map1 = new HashMap<String,String>();
				map1.put("usercode", str1.toString());
				List<JSONObject> areaList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT+".findAreaByUsercode", map1);
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
				List<JSONObject> nameList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT+".findNameByUsercode", map1);
				if(nameList.size()>0){
					for (JSONObject jsonObject : list) {
						for (JSONObject jsonObject1 : nameList) {
							if(jsonObject.getString("usercode").equals(jsonObject1.getString("usercode"))){
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("jdr"))){
									jsonObject.put("jdr", jsonObject1.getString("jdr"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("jddy"))){
									jsonObject.put("jddy", jsonObject1.getString("jddy"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("faskperson"))){
									jsonObject.put("faskperson", jsonObject1.getString("faskperson"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("developername"))){
									jsonObject.put("developername", jsonObject1.getString("developername"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("devchannel"))){
									jsonObject.put("devchannel", jsonObject1.getString("devchannel"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("nexttype"))){
									jsonObject.put("nexttype", jsonObject1.getString("nexttype"));
								}
								if(!YZUtility.isNullorEmpty(jsonObject1.getString("slgj"))){
									jsonObject.put("slgj", jsonObject1.getString("slgj"));
								}
							}
						}
					}
				}
			}
			PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
			JSONObject jobj = new JSONObject();
			if (firstIndex == 0) {
				// 已成交
				Map<String, String> wheremap = new HashMap<>();
				wheremap.putAll(map);
				wheremap.put("del", "0");
				// 成交
				wheremap.put("cjstatus", "1");
				int cjtotal = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".selectJzcxtotal", wheremap);
				jobj.put("cjtotal", cjtotal);
				// 为成交
				jobj.put("wcjtotal", pageInfo.getTotal() - cjtotal);
				wheremap.remove("cjstatus");
				JSONObject jzflobj = new JSONObject();
				StringBuilder jzflStr = new StringBuilder();
				for (int j = 0; j < jzflDict.size(); j++) {
					if(j==jzflDict.size()-1){
						jzflStr.append("\'"+jzflDict.get(j).getSeqId()+"\'");
					}else{
						jzflStr.append("\'"+jzflDict.get(j).getSeqId()+"\',");
					}
					
				}
				wheremap.put("recesort", jzflStr.toString());
				List<JSONObject> jzfltotal = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectJzcxtotal1", wheremap);
				if(jzfltotal.size()>0){
					for (JSONObject jsonObject : jzfltotal) {
						jzflobj.put(jsonObject.getString("dictname"), jsonObject.getString("num"));
					}
					jobj.put("jzfl", jzflobj);	
				}
			}
			jobj.put("total", pageInfo.getTotal());
			jobj.put("rows", list);
			return jobj;
		}
		
		
	}

	public JSONObject selectToday(String table, YZPerson person, Map<String, String> map, String visualstaff, String visualstaffwd,BootStrapPage bp,String flag) throws Exception {
		map.put("starttime", map.get("starttime") + ConstUtil.TIME_START);
		map.put("endtime", map.get("endtime") + ConstUtil.TIME_END);
		if (map.containsKey("searchValue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("searchValue")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("searchValue")));
		}
		map.put("visualstaff", visualstaff);
		map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
		/**
		 * 2019.07.18 lwg 网店咨询 客户管理的点击问题
		 */
		if(map.get("sortName")!=null){
			String sortName=map.get("sortName");
			if(sortName.equals("createtime")){
				map.put("sortName", "r.createtime");
			}
			if(sortName.equals("usercode")){
				map.put("sortName", "r.usercode");
			}
			if(sortName.equals("username")){
				map.put("sortName", "r.username");
			}
			if(sortName.equals("age")){
				map.put("sortName", "u.age");
			}
			if(sortName.equals("important")){
				map.put("sortName", "u.important");
			}
			if(sortName.equals("jdr")){
				map.put("sortName", "per3.USER_NAME");
			}
			if(sortName.equals("jdsj")){
				map.put("sortName", "u.createtime");
			}
			if(sortName.equals("accepttype")){
				map.put("sortName", "kcit5.dict_name");
			}
			if(sortName.equals("accepttool")){
				map.put("sortName", "kcit6.dict_name");
			}
			if(sortName.equals("devchannel")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(sortName.equals("nexttype")){
				map.put("sortName", "hzly.dict_name");
			}
			if(sortName.equals("kefuname")){
				map.put("sortName", "per7.user_name");
			}
			if(sortName.equals("jddy")){
				map.put("sortName", "per4.USER_NAME");
			}
			if(sortName.equals("createuser")){
				map.put("sortName", "per5.USER_NAME");
			}
			if(sortName.equals("dy")){
				map.put("sortName", "per6.USER_NAME");
			}
			if(sortName.equals("ywhf")){
				map.put("sortName", "(select count(v.usercode) from KQDS_Visit v where v.usercode=u.UserCode)");
			}
			if(sortName.equals("doctor")){
				map.put("sortName", "per2.USER_NAME");
			}
			if(sortName.equals("askperson")){
				map.put("sortName", "per1.USER_NAME");
			}
			if(sortName.equals("regdept")){
				map.put("sortName", "dept.DEPT_NAME");
			}
			if(sortName.equals("regsort")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(sortName.equals("recesort")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(sortName.equals("cjstatus")){
				map.put("sortName", "r.cjstatus");
			}
			if(sortName.equals("sex")){
				map.put("sortName", "u.sex");
			}
			if(sortName.equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			
			
		}
		if (flag != null && flag.equals("exportTable")) {
			map.put("flag", "exportTable");
		}
		/**
		 * end
		 */
		List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectTodayNum", map);
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectToday", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();
		if(YZUtility.isNullorEmpty(map.get("flag"))){
			StringBuilder str=new StringBuilder();
			StringBuilder str1=new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				if(i==list.size()-1){
					str.append("\'"+list.get(i).getString("seq_id")+"\'");
					str1.append("\'"+list.get(i).getString("usercode")+"\'");
				}else{
					str.append("\'"+list.get(i).getString("seq_id")+"\',");
					str1.append("\'"+list.get(i).getString("usercode")+"\',");
				}
				list.get(i).put("jdr", "");
				list.get(i).put("jddy", "");
				list.get(i).put("devchannel", "");
				list.get(i).put("nexttype", "");
				list.get(i).put("ywhf", "无回访");
				list.get(i).put("askperson", "");
				list.get(i).put("doctor", "");
				list.get(i).put("createuser", "");
				list.get(i).put("dy","");
				list.get(i).put("recesort","");
				list.get(i).put("regdept", "");
				list.get(i).put("regsort", "");
				list.get(i).put("failreason1", "");
			}
			if(str1.length()>0){
				map.put("usercodes", str1.toString());
				List<JSONObject> namelist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectTodayNameByUsercode", map);
				for (JSONObject jsonObject : namelist) {
					for (JSONObject job : list) {
						if(jsonObject.getString("usercode").equals(job.getString("usercode"))){
							if(!YZUtility.isNullorEmpty(jsonObject.getString("jdr"))){
								job.put("jdr", jsonObject.getString("jdr"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("jddy"))){
								job.put("jddy", jsonObject.getString("jddy"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("devchannel"))){
								job.put("devchannel", jsonObject.getString("devchannel"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("nexttype"))){
								job.put("nexttype", jsonObject.getString("nexttype"));
							}
						}
					}
				}
				List<JSONObject> ywhflist = (List<JSONObject>) dao.findForList("KQDS_VISIT.selectYwhfNumByUsercode", map);
				for (JSONObject jsonObject : ywhflist) {
					if ("0".equals(jsonObject.getString("ywhf"))) {
						jsonObject.put("ywhf", "无回访");
					} else {
						jsonObject.put("ywhf", "有回访");
					}
					for (JSONObject job : list) {
						if(jsonObject.getString("usercode").equals(job.getString("usercode"))){
							if(!YZUtility.isNullorEmpty(jsonObject.getString("ywhf"))){
								job.put("ywhf", jsonObject.getString("ywhf"));
							}
						}
					}
				}
				
			}
			if(str.length()>0){
				map.put("seqid", str.toString());
				List<JSONObject> namelist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectTodayNameBySeqid", map);
				for (JSONObject jsonObject : namelist) {
					for (JSONObject job : list) {
						if(jsonObject.getString("seqid").equals(job.getString("seq_id"))){
							if(!YZUtility.isNullorEmpty(jsonObject.getString("askperson"))){
								job.put("askperson", jsonObject.getString("askperson"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("doctor"))){
								job.put("doctor", jsonObject.getString("doctor"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("createuser"))){
								job.put("createuser", jsonObject.getString("createuser"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("dy"))){
								job.put("dy", jsonObject.getString("dy"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("recesort"))){
								job.put("recesort", jsonObject.getString("recesort"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("regdept"))){
								job.put("regdept", jsonObject.getString("regdept"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("regsort"))){
								job.put("regsort", jsonObject.getString("regsort"));
							}
							if(!YZUtility.isNullorEmpty(jsonObject.getString("failreason1"))){
								job.put("failreason1", jsonObject.getString("failreason1"));
							}
						}
					}
				}
			}
		}else{
			for (JSONObject job : list) {
			String ywhf = job.getString("ywhf");
			if ("0".equals(ywhf)) {
				ywhf = "无回访";
			} else {
				ywhf = "有回访";
			}
			job.put("ywhf", ywhf);
			}
		}
		
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		jobj1.put("nums", list1);
		return jobj1;
	}
	
	public JSONObject selectDzQuery(String table, YZPerson person, Map<String, String> map, String visualstaff, String visualstaffwd,BootStrapPage bp) throws Exception {
		map.put("starttime", map.get("starttime") + ConstUtil.TIME_START);
		map.put("endtime", map.get("endtime") + ConstUtil.TIME_END);
		if (map.containsKey("searchValue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("searchValue")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("searchValue")));
		}
		map.put("visualstaff", visualstaff);
		map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "reg.createtime");
			}
			if(map.get("sortName").equals("recesortname")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("regsortname")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "reg.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "reg.username");
			}
			if(map.get("sortName").equals("sex")){
				map.put("sortName", "us.sex");
			}
			if(map.get("sortName").equals("age")){
				map.put("sortName", "us.age");
			}
			if(map.get("sortName").equals("cjstatus")){
				map.put("sortName", "reg.cjstatus");
			}
			if(map.get("sortName").equals("askpersonname")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("doctorname")){
				map.put("sortName", "per2.user_name");
			}
			if(map.get("sortName").equals("repairdoctorname")){
				map.put("sortName", "per5.user_name");
			}
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "us.remark");
			}
			if(map.get("sortName").equals("devchannelname")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(map.get("sortName").equals("nexttypename")){
				map.put("sortName", "hzly.dict_name");
			}
			if(map.get("sortName").equals("slgj")){
				map.put("sortName", "kcit4.dict_name");
			}
			if(map.get("sortName").equals("ifmedrecord")){
				map.put("sortName", "reg.ifmedrecord");
			}
			if(map.get("sortName").equals("ifcost")){
				map.put("sortName", "reg.ifcost");
			}
			if(map.get("sortName").equals("createusername")){
				map.put("sortName", "per3.user_name");
			}
			if(map.get("sortName").equals("receivenoname")){
				map.put("sortName", "per4.user_name");
			}
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectDzQuery", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectDzQueryNum", map);
		jobj.put("nums", list1);
		
		return jobj;
	}

	// 首页查询条数
	public int getCountIndex(String table, YZPerson person, int status, String querytype, String searchValue, String visualstaff, String organization, HttpServletRequest request)
			throws Exception {
		String sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID); // 获取收费角色
		Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			if (!YZUtility.isStrInArrayEach(person.getUserPriv(), sfpriv)) {
				map.put("querytype", visualstaff);
			}
		}

		// if (status != 5) {
		// if (status == 2) {
		// map.put("status2", "2");
		// } else {
		// map.put("status2novalue", status + "");
		// map.put("status2no", "");
		// }
		// } else {
		// map.put("status5", "");
		// }

		StringBuffer statusBf = new StringBuffer();
		if (status != 5) {
			if (status == 2) {
				statusBf.append(" and r.status>=" + status + " ");
			} else {
				statusBf.append(" and r.status=" + status + " ");
				statusBf.append(" and " + SQLUtil.dateDiffDay("r.createtime"));
			}
		} else {
			// 今日患者
			statusBf.append(" and " + SQLUtil.dateDiffDay("r.createtime"));
		}

		map.put("statusSql", statusBf.toString());

		if (!YZUtility.isNullorEmpty(searchValue)) {
			map.put("searchValue", searchValue);
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
		}
		map.put("organization", organization);

		int count = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".getCountIndex", map);
		return count;
	}

	// 统计就诊分类 上门人数
	public List getCountTj(String table, Map<String, String> map, String organization) throws Exception {
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getCountTj", map);
		for (JSONObject obj : list) {
			obj.put("recesortDesc", obj.getString("dict_name"));
		}
		return list;
	}

	// 统计全院来人情况 ---总来人
	public List getCountQylrAll(String table, int year, int month, int days, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("year", year + "");
		map.put("month", month + "");
		map.put("organization", organization);

		List<int[]> list = new ArrayList<int[]>();
		int[] num = new int[days + 1];// 解决折线图初始位置从1开始
		int[] allnum = new int[1];// 总人数
		for (int i = 0; i <= days; i++) {
			num[i] = 0;
			map.put("day", i + "");
			int tmpcount = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".getCountQylrAll", map);
			num[i] = tmpcount;
			allnum[0] += tmpcount;
		}
		list.add(num);
		list.add(allnum);
		return list;
	}

	// 统计全院来人情况 ---新诊
	public List getCountQylrNew(String table, int year, int month, int days, String organization, HttpServletRequest request) throws Exception {
		String recesort = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID);
		recesort = YZUtility.ConvertStringIds4Query(recesort);
		Map<String, String> map = new HashMap<String, String>();
		map.put("year", year + "");
		map.put("month", month + "");
		map.put("organization", organization);
		map.put("recesort", recesort);

		List<int[]> list = new ArrayList<int[]>();
		int[] num = new int[days + 1];// 解决折线图初始位置从1开始
		int[] allnum = new int[1];// 总人数
		for (int i = 0; i <= days; i++) {
			num[i] = 0;
			map.put("day", i + "");
			int tmpcount = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".getCountQylrNew", map);
			num[i] = tmpcount;
			allnum[0] += tmpcount;
		}
		list.add(num);
		list.add(allnum);
		return list;
	}

	// 成交率
	public Double getcjl(String date, String ghfl, String jzfl, String visualstaff, String organization) throws Exception {
		Double cjl = 0.0;
		Map<String, String> map = new HashMap<String, String>();
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime", date + ConstUtil.TIME_END);
		if (!YZUtility.isNullorEmpty(ghfl)) {
			map.put("regsort", ghfl);
		}
		if (!YZUtility.isNullorEmpty(jzfl)) {
			map.put("recesort", jzfl);
		}
		map.put("organization", organization);
		map.put("visualstaff", visualstaff);

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getcjl", map);
		if (list != null && list.size() > 0) {
			cjl = YZUtility.isNullorEmpty(list.get(0).getString("cjl")) ? 0.0 : list.get(0).getDouble("cjl");
		}

		return cjl;
	}

	// 统计全院来人情况 ---新诊
	public int getCountQylrNew(String table, String date, String ghfl, String jzfl, String visualstaff, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime", date + ConstUtil.TIME_END);
		if (!YZUtility.isNullorEmpty(ghfl)) {
			map.put("regsort", ghfl);
		}
		if (!YZUtility.isNullorEmpty(jzfl)) {
			map.put("recesort", jzfl);
		}
		map.put("organization", organization);
		map.put("visualstaff", visualstaff);
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".getCountQylrNew2", map);
		return nums;
	}

	// 成交人数
	public int getCountQylrAll(String table, String date, String ghfl, String jzfl, String visualstaff, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime", date + ConstUtil.TIME_END);
		if (!YZUtility.isNullorEmpty(ghfl)) {
			map.put("regsort", ghfl);
		}
		if (!YZUtility.isNullorEmpty(jzfl)) {
			map.put("recesort", jzfl);
		}
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".getCountQylrAll2", map);
		return nums;
	}

	// 获取患者当天最新的
	public JSONObject selectToDayNewDetail(String usercode, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(usercode)) {
			map.put("usercode", usercode);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		JSONObject obj = new JSONObject();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".selectToDayNewDetail", map);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			obj.put("askperson", rs.getString("askperson"));
			obj.put("regsort", rs.getString("regsort"));
			obj.put("askpersonname", rs.getString("askpersonname"));
			obj.put("sortname", rs.getString("sortname"));
		}

		return obj;
	}

	public KqdsReg getRegForQsyy(String usercode, String createtime) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("createtime", createtime);
		List<KqdsReg> list = (List<KqdsReg>) dao.findForList(TableNameUtil.KQDS_REG + ".getRegForQsyy", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<JSONObject> getNoUsercode(String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getNoUsercode", usercode);
		return list;
	}

	public void deleteByRegno(String outprocessingsheetno) throws Exception {
		dao.delete(TableNameUtil.KQDS_REG + ".deleteByRegno", outprocessingsheetno);
	}

	/**
	 * 查询患者24小时内的初诊挂号记录
	 * 
	 * @param conn
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void cancelReg(KqdsReg reg, HttpServletRequest request) throws Exception {
		// 修改
		dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
		// 删掉就诊情况，如果存在的话// ### 删掉之前未填写就诊情况的数据，填写了的就不删了，根据就诊目的是否有值进行判断
		jlogic.deleteByRegNo(reg.getSeqId(), request);

		// 删掉咨询记录，如果存在的话
		deleteReceiveInfoByRegNo(reg.getSeqId(), request);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void updateReg(KqdsReg reg, KqdsReg regold, KqdsUserdocument userdoc, YZPerson person, HttpServletRequest request) throws Exception {
		// 修改
		dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg); // 修改挂号记录
		// ### 记录日志
		String regsortname = dictLogic.getDictNameBySeqId(reg.getRegsort());
		// ### 修改挂号时，对就诊情况数据并做相应处理，如果选择医生，更新患者档案表的医生数据
		checkJzqk4RegEdit(reg, regold, userdoc, person, request, regsortname);

		// ### 修改挂号时，对咨询记录数据并做相应处理
		checkReceive4RegEdit(reg, request);
	}

	@Transactional(rollbackFor = { Exception.class })
	public List<JSONObject> updateRegInsert(KqdsReg dp, KqdsUserdocument userdoc, YZPerson person, HttpServletRequest request) throws Exception {
		dao.saveSingleUUID(TableNameUtil.KQDS_REG, dp);
		// ###添加消息提示
		PushUtil.saveTx4NewReg(dp, request);
		// 记录日志
		BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_REG, dp, dp.getUsercode(), TableNameUtil.KQDS_REG, request);

		// ### 检测并更新+插入就诊记录 ### 挂号时直接选择医生
		// 生成就诊情况记录，验证是否是就诊提醒，是则更新上一次就诊情况的到诊时间
		if (!YZUtility.isNullorEmpty(dp.getDoctor())) { // ##选医生的情况下才执行!!!
			checkUpdateInsertJZQK(dp, person, ChainUtil.getCurrentOrganization(request), request);

			// ## 更新患者档案表的医生数值
			userdoc.setDoctor(dp.getDoctor());
			dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, userdoc);

		}
		// ### 生成接诊信息 ### 只有选咨询的情况下，才生成咨询记录 !!!
				if (!YZUtility.isNullorEmpty(dp.getAskperson())) {
					saveReceive(dp, userdoc, ChainUtil.getCurrentOrganization(request), request);

					// ###添加消息提示
					// ResultSetTool.savebcjl(receive, person,
					// receive.getUsercode(), "流转", "咨询记录", receive.getRegno(),
					// dp.getAskperson(),ChainUtil.getCurrentOrganization(request));
				}

				// ### 只有之前的上门状态为未上门时，才执行
				if (0 == userdoc.getDoorstatus()) {
					userdoc.setDoorstatus(1); // 更新患者总上门状态 0未上门 1已上门
					updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, userdoc);
					//修改当天所有预约为已上门 
				}

				// 网电建档的患者，目前客户报备的，也属于网电的
				if (ConstUtil.USER_TYPE_1 == userdoc.getType()) {
					checkNetOrder(dp, request);
				}

				// 门诊建档的患者
				checkHosOrder(dp, request);
		
				//单次挂号挂多个预约信息下的挂号 先注释掉
		//查询所有的门诊预约
		//查询当天该患者的所有的预约信息，查询出
		Map<String,String> map= new HashMap<String,String>();
		String date = YZUtility.getDateStr(new Date());
		map.put("starttime", date + ConstUtil.TIME_START);
		map.put("endtime", date + ConstUtil.TIME_END);
		map.put("usercode", dp.getUsercode());
		//所有未上门的门诊预约信息
		List<JSONObject> list = hospitalOrderLogic.selectHospitalOrderByUsercodeAndTime(map);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
////		if(list!=null&&list.size()>1){
////			for (KqdsHospitalOrder kqdsHospitalOrder : list) {
////				if(!kqdsHospitalOrder.getAskperson().equals(dp.getDoctor())){
////					map.put("organization", ChainUtil.getCurrentOrganization(request));
////					map.put("searchValue", dp.getUsercode());
////					//时间 username=usercode hudh查询手术预约
////					List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_ROOM + ".selectNoPageByUsercode", map);
////					//organization searchValue=usercode查询门诊预约
////					List<JSONObject> list2 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectHospitalOrderListByUsercode", map);
////					//askperson, askpersonname
////					//searchValue=usercode  organization查询网电预约
////					List<JSONObject> list3 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectNetOrderListByUsercode", map);
////				}
////			}
////		}
////		
//		if(list!=null&&list.size()>0){
//			for (KqdsHospitalOrder kqdsHospitalOrder : list) {
//				if(!kqdsHospitalOrder.getAskperson().equals(dp.getDoctor())){
//					// ### 根据医生、istx状态和dzdata进行查询，查询该医生对应的到诊时间为空的提醒记录
//					List<JSONObject> list3 = jlogic.jzFz(dp.getUsercode(), kqdsHospitalOrder.getAskperson());
//					
//					if(list3!=null&&list3.size()>0){
//						for (JSONObject job : list3) {
//							KqdsJzqk jzqk = (KqdsJzqk) JSONObject.toBean(job, KqdsJzqk.class);
//							// 一次挂号 一个患者 对应一个医生只有一个就诊情况
//							// 更新就诊情况记录的到诊时间（存在未到诊的情况）
//							jzqk.setDzdata(dp.getCreatetime());
//							jzqk.setDzregno(dp.getSeqId());
//							dao.updateSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
//						}
//					}
//					// 生成一条新的就诊情况，可能存在 复诊的患者 不需要开单 结账
//					KqdsJzqk jzqk = new KqdsJzqk();
//					dp.setSeqId(YZUtility.getUUID());
//					jzqk.setSeqId(YZUtility.getUUID());
//					jzqk.setRegno(dp.getSeqId());
//					jzqk.setDoctor(kqdsHospitalOrder.getAskperson());
//					jzqk.setCreatetime(YZUtility.getCurDateTimeStr());
//					jzqk.setCreateuser(person.getSeqId());
//					jzqk.setOrganization(ChainUtil.getCurrentOrganization(request));// ### 【以当前所在门诊为主】
//					dao.saveSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
//					map.put("doctor", kqdsHospitalOrder.getAskperson());
////					List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getLastestRegInfoByDoctor", map);
////					if(list1!=null&&list1.size()>0){
////							//就诊科室
////							dp.setRegdept(kqdsHospitalOrder.getRegdept());
////							dp.setRecesort(kqdsHospitalOrder.getOrdertype());
////							String dd=deptLogic.getDeptNamesBySeqIds(kqdsHospitalOrder.getRegdept());
////							//根据科室查询出挂号分类
////							JSONObject json = new JSONObject();
////							json.put("organization", ChainUtil.getCurrentOrganization(request));
////							json.put("parentCode", "jzfl");
////							json.put("orderBy", SQLUtil.castAsInt("orderno"));
////							List<YZDict> list2 = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getListByParentCode", json);
////							for (YZDict yzDict : list2) {
////								if(dd.startsWith("洁牙")){
////									if(yzDict.getDictName().startsWith("其他")){
////										dp.setRegsort(yzDict.getSeqId());
////									}
////								}else if(dd.startsWith(yzDict.getDictName())){
////									dp.setRegsort(yzDict.getSeqId());
////								}else{
////									if(yzDict.getDictName().startsWith("其他")){
////										dp.setRegsort(yzDict.getSeqId());
////									}
////								}
////							}
////							saveReceive(dp, userdoc, ChainUtil.getCurrentOrganization(request), request);
////						
////					}else{
//						//就诊科室
//						dp.setRegdept(kqdsHospitalOrder.getRegdept());
//						dp.setRecesort(kqdsHospitalOrder.getOrdertype());
//						String dd=deptLogic.getDeptNamesBySeqIds(kqdsHospitalOrder.getRegdept());
//						//根据科室查询出挂号分类
//						JSONObject json = new JSONObject();
//						json.put("organization", ChainUtil.getCurrentOrganization(request));
//						json.put("parentCode", "ghfl");
//						json.put("orderBy", SQLUtil.castAsInt("orderno"));
//						List<YZDict> list2 = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getListByParentCode", json);
//						for (YZDict yzDict : list2) {
//							if(dd.startsWith("洁牙")||dd.startsWith("院办")){
//								if(yzDict.getDictName().startsWith("其他")){
//									dp.setRegsort(yzDict.getSeqId());
//									break;
//								}
//							}else if(dd.startsWith("修复")){
//								if(yzDict.getDictName().startsWith("修复")){
//									dp.setRegsort(yzDict.getSeqId());
//									break;
//								}
//							}else if(dd.startsWith(yzDict.getDictName())){
//								dp.setRegsort(yzDict.getSeqId());
//								break;
//							}
//						}
//						//挂号分类
//						saveReceive(dp, userdoc, ChainUtil.getCurrentOrganization(request), request);
////					}
//					dp.setDoctor(kqdsHospitalOrder.getAskperson());	
//					dao.saveSingleUUID(TableNameUtil.KQDS_REG, dp);
//					// ###添加消息提示
//					PushUtil.saveTx4NewReg(dp, request);
//					// 记录日志
//					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_REG, dp, dp.getUsercode(), TableNameUtil.KQDS_REG, request);
//					
//					kqdsHospitalOrder.setOrderstatus(1);// 已上门
//					kqdsHospitalOrder.setRegno(dp.getSeqId());// 挂号编号
//					kqdsHospitalOrder.setRegdept(dp.getRegdept());// 安排科室
//					kqdsHospitalOrder.setOrdertime(dp.getCreatetime());
//					//修改预约信息
//					dao.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, kqdsHospitalOrder);
//					// ## 更新患者档案表的医生数值
//					userdoc.setDoctor(dp.getDoctor());
//					dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, userdoc);
//					if (!YZUtility.isNullorEmpty(dp.getAskperson())) {
//						saveReceive(dp, userdoc, ChainUtil.getCurrentOrganization(request), request);
//					}
//				}
//			}
//		}
		
	}
	
	/**
	 * 根据患者编号查询挂号信息
	 * @param usercode 2019-4-21 shaoyp
	 * @return
	 * @throws Exception
	 */
	public JSONObject findKqdsRegByUserCode(String usercode) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_REG + ".findKqdsRegByUserCode", usercode);
		return json;
	}

	/**   
	  * @Title: findRegByregNo   
	  * @Description: TODO(查询挂号信息)   
	  * @param: @param kQDS_REG
	  * @param: @param regno      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年7月10日 上午9:46:35
	  */  
	public JSONObject findRegByregNo(String kQDS_REG, String regno) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_REG+".findRegByregNo", regno);
	}
	
	public String selectExistByUsercode(Map<String,String> map) throws Exception{
		String createtime=(String) dao.findForObject(TableNameUtil.KQDS_REG+".selectExistByUsercode", map);
		return createtime;
	}
	/**   
	  * @Title: findRegRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月24日 下午3:20:39
	  */  
	public JSONObject findRegRecord(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		List<JSONObject> findForList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG+".findRegRecord", map);
		json.put("regRecord", findForList.indexOf(0));
		return json;
	}

	/**   
	  * @Title: findRecord   
	  * @Description: TODO(查找今日挂号记录)   
	  * @param: @param usercode      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月27日 下午4:36:35
	  */  
	public JSONObject findRecord(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG+".findRecord", map);
		JSONObject json = new JSONObject();
		if(list.size()>0 && list != null){
			for (int i = 0; i < list.size(); i++) {
				json.put("json", list.get(0));
			}
		}else{
			json = null;
		}
		return json;
	}
	/**
	 * 查询患者的所有挂号信息
	 * 2019-09-01 lwg
	 * @param usercodes
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findKqdsRegByUsercodes(String usercodes) throws Exception{
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG+".findKqdsRegByUsercodes", usercodes);
		return list;
	}

	/**   
	  * @Title: findDepartment   
	  * @Description: TODO(部门)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年9月28日 下午4:29:34
	  */  
	public List<JSONObject> findDepartment(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG+".findDepartment", map);
	}

	/**   
	  * @Title: findZperformance   
	  * @Description: TODO(总)   
	  * @param: @param map
	  * @param: @return      
	  * @return: JSONObject
	 * @throws Exception 
	  * @dateTime:2019年9月28日 下午4:30:07
	  */  
	public JSONObject findZperformance(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_REG+".findZperformance", map);
	}

	/**   
	  * @Title: findDoctor   
	  * @Description: TODO(医生)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年9月29日 下午3:30:14
	  */  
	public List<JSONObject> findDoctor(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG+".findDoctor", map);
	}

	/**   
	  * @Title: findZdoctor   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年9月29日 下午3:47:28
	  */  
	public JSONObject findZdoctor(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_REG+".findZdoctor", map);
	}

	/**   
	  * @Title: findImplantNum   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年10月5日 下午3:55:43
	  */  
	public List<JSONObject> findImplantNum(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>)dao.findForList(TableNameUtil.KQDS_REG+".findImplantNum", map);
	}

	/**   
	  * @Title: findImplantNumAll   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年10月5日 下午3:55:50
	  */  
	public JSONObject findImplantNumAll(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_REG+".findImplantNumAll", map);
	}
	
	/**
	 * 折扣后也改变成交状态为已成交 syp 2019-10-07
	  * @Title: updateRegCjstatus   
	  * @Description: TODO(折扣后也改变成交状态为已成交)   
	  * @param: @param seqId
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年10月7日 下午6:16:15
	 */
	public void updateRegCjstatus(String seqId) throws Exception {
		dao.update(TableNameUtil.KQDS_REG + ".updateRegCjstatus", seqId);
	}
	
	
	public void updateRegJhStatus(Map<String,String> map) throws Exception {
		dao.update(TableNameUtil.KQDS_REG + ".updateRegJhStatus", map);
	}
}