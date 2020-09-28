package com.kqds.controller.base.room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsRoom;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.base.room.KQDS_RoomLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked", "unused" })
@Controller
@RequestMapping("KQDS_RoomAct")
public class KQDS_RoomAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_RoomAct.class);
	@Autowired
	private KQDS_RoomLogic logic;
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private YZPrivLogic privLogic;

	@RequestMapping(value = "/toRoom.act")
	public ModelAndView toRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String roomid = request.getParameter("roomid");
		String zzxt = request.getParameter("zzxt");
		String nurse = request.getParameter("nurse");
		String doctor = request.getParameter("doctor");
		String nurseDesc = request.getParameter("nurseDesc");
		String doctorDesc = request.getParameter("doctorDesc");
		String organization = request.getParameter("organization");

		ModelAndView mv = new ModelAndView();
		mv.addObject("roomid", roomid);
		mv.addObject("zzxt", zzxt);
		mv.addObject("nurse", nurse);
		mv.addObject("doctor", doctor);
		mv.addObject("nurseDesc", nurseDesc);
		mv.addObject("doctorDesc", doctorDesc);
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/yyzx/room/room.jsp");
		return mv;
	}

	@RequestMapping(value = "/toRoomSearch.act")
	public ModelAndView toRoomSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/yyzx/room/roomSearch.jsp");
		return mv;
	}

	/**
	 * 新增、编辑
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsRoom dp = new KqdsRoom();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dp.setStarttime(formatter.format(formatter.parse(dp.getStarttime())));
			dp.setEndtime(formatter.format(formatter.parse(dp.getEndtime())));

			// 测试过程中出现 存的usercode和Username匹配的情况，这里增加校验
			// ##############################################
			if (YZUtility.isNullorEmpty(dp.getUsercode())) {
				throw new Exception("usercode值不能为空");
			}
			if (!YZUtility.isNullorEmpty(seqId)) {
				/**
				 * 判断当前人员是否有权限修改 ADD 2019-3-8
				 */
				//获取当前手术室预约创建者
				KqdsRoom kqdsRoom = logic.selectByPrimaryKey(seqId);
				String createUser = kqdsRoom.getCreateuser();
				String currentUser = SessionUtil.getLoginPerson(request).getSeqId(); //当前登录人员id
				String currentUserPriv = SessionUtil.getLoginPerson(request).getUserPriv(); //当前登录人员主角色
				boolean isOneSelf = createUser.equals(currentUser);
				YZPriv priv = privLogic.getDetailBySeqId(currentUserPriv);
				//判断当前人员是否有 允许修改他人手术室预约的权限
				boolean isCanModRoomPriv = false;
				try {
					String canModRoomPriv = priv.getPrivIdStr().split(",")[20];
					if(canModRoomPriv.equals("1")) {
						isCanModRoomPriv = true;
					}
				} catch (Exception e) {
				}
				if(!isOneSelf && !isCanModRoomPriv) {
					throw new Exception("noPriv");
				}
				/**
				 * end
				 */
				dp.setRemark(YZUtility.filterLineBreak(dp.getRemark()));
				logic.updateSingleUUID(TableNameUtil.KQDS_ROOM, dp);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_ROOM, dp, dp.getUsercode(), TableNameUtil.KQDS_ROOM, request);
				// 添加消息提示
				PushUtil.saveTx4ModifyRoomOrder(dp, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
				dp.setRemark(YZUtility.filterLineBreak(dp.getRemark()));
				logic.saveSingleUUID(TableNameUtil.KQDS_ROOM, dp);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_ROOM, dp, dp.getUsercode(), TableNameUtil.KQDS_ROOM, request);
				// 添加消息提示
				PushUtil.saveTx4NewRoomOrder(dp, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断手术室预约情况
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkRoom.act")
	public String checkRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String endtime = formatter.format(formatter.parse(request.getParameter("endtime")));
			String doctor = request.getParameter("doctor");
			String usercode = request.getParameter("usercode");
			String nurse = request.getParameter("nurse");
			String seqId = request.getParameter("seqId");
			String roomid = request.getParameter("roomid");
			Map<String, String> map = new HashMap<String, String>();
			String flagmsg = "";
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
				flagmsg = "医生";
			}
			if (!YZUtility.isNullorEmpty(nurse)) {
				map.put("nurse", nurse);
				flagmsg = "护士";
			}
			if (!YZUtility.isNullorEmpty(roomid)) {
				map.put("roomid", roomid);
				flagmsg = "手术室";
			}
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
				flagmsg = "患者";
			}

			int count = logic.countRoom(map, seqId);
			JSONObject jobj = new JSONObject();
			jobj.put("data", count);
			YZUtility.DEAL_SUCCESS(jobj, "该时间段" + flagmsg + "存在预约", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断患者门诊预约情况 （同一医生和患者，存在门诊预约也可以手术预约）
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkOrderUsercode.act")
	public String checkOrderUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String endtime = formatter.format(formatter.parse(request.getParameter("endtime")));
			String usercode = request.getParameter("usercode");
			String doctor = request.getParameter("doctor");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			JSONObject jobj = new JSONObject();
			int flag = 0;
			KqdsHospitalOrder order = logic.checkOrderUsercode(map);
			String msg = "";
			if (order != null) {
				if (!YZUtility.isNullorEmpty(order.getAskperson())) {
					String doctorordername = personLogic.getNameStrBySeqIds(order.getAskperson());
					if (YZUtility.isNotNullOrEmpty(order.getAskperson())) {//!order.getAskperson().equals(doctor)
						msg = "该时间段患者存在门诊预约：<br>医生：" + doctorordername + "&nbsp;&nbsp;&nbsp;时间：" + order.getStarttime().substring(11) + "-" + order.getEndtime().substring(11);
						flag = 1;
					}
				}
			}
			jobj.put("data", flag);
			YZUtility.DEAL_SUCCESS(jobj, msg, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断患者门诊预约情况 （同一医生和患者，存在门诊预约也可以手术预约）
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkOrderDoctor.act")
	public String checkOrderDoctor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String endtime = formatter.format(formatter.parse(request.getParameter("endtime")));
			String usercode = request.getParameter("usercode");
			String nurse = request.getParameter("nurse");
			String doctor = request.getParameter("doctor");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
			}
			if (!YZUtility.isNullorEmpty(nurse)) {
				map.put("nurse", nurse);
			}
			JSONObject jobj = new JSONObject();
			int flag = 0;
			KqdsHospitalOrder order = logic.checkOrderUsercode(map);
			String msg = "";
			if (order != null) {
				if (!YZUtility.isNullorEmpty(order.getUsercode())) {
					if (YZUtility.isNotNullOrEmpty(order.getUsercode())) {//!order.getUsercode().equals(usercode)
						msg = "该时间段医生存在门诊预约：<br>患者：" + order.getUsername() + "&nbsp;&nbsp;&nbsp;时间：" + order.getStarttime().substring(11) + "-" + order.getEndtime().substring(11);
						flag = 1;
					}
				}
			}
			jobj.put("data", flag);
			YZUtility.DEAL_SUCCESS(jobj, msg, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 详情返回
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			KqdsRoom en = (KqdsRoom) logic.loadObjSingleUUID(TableNameUtil.KQDS_ROOM, seqId);

			if (en == null) {
				throw new Exception("数据不存在");
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String delreason = request.getParameter("delreason");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			if (YZUtility.isNullorEmpty(delreason)) {
				throw new Exception("取消预约原因为空或者null");
			}

			KqdsRoom en = (KqdsRoom) logic.loadObjSingleUUID(TableNameUtil.KQDS_ROOM, seqId);
			en.setIsdelete(1);
			en.setDelreason(delreason);
			YZPerson person = SessionUtil.getLoginPerson(request);
			en.setDelperson(person.getSeqId());
			logic.updateSingleUUID(TableNameUtil.KQDS_ROOM, en);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.CANCEL, BcjlUtil.KQDS_ROOM, en, en.getUsercode(), TableNameUtil.KQDS_ROOM, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 日历查询
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showXML.act")
	public String showXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 前台需要读取某范围的数据时，通过数据库数据查询，将该段时间的日历保存到一个xml文件中。
			Map<String, String> map = new HashMap<String, String>();
			String roomid = request.getParameter("roomid");// 手术室
			String zzxt = request.getParameter("zzxt");// 种植系统
			String doctor = request.getParameter("doctor");// 医生
			String nurse = request.getParameter("nurse");// 医生
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			starttime = sdf.format(new Date(Long.valueOf(starttime)));
			endtime = sdf.format(new Date(Long.valueOf(endtime)));
			if (!YZUtility.isNullorEmpty(starttime) && !starttime.equals("null")) {
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime) && !endtime.equals("null")) {
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(roomid) && !roomid.equals("null")) {
				map.put("roomid", roomid);
			}
			if (!YZUtility.isNullorEmpty(zzxt) && !roomid.equals("null")) {
				map.put("zzxt", zzxt);
			}
			if (!YZUtility.isNullorEmpty(doctor) && !doctor.equals("null")) {
				map.put("doctor", doctor);
			}
			if (!YZUtility.isNullorEmpty(nurse) && !doctor.equals("null")) {
				map.put("nurse", nurse);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			List<Object> list = (List<Object>) logic.selectList(TableNameUtil.KQDS_ROOM, map, organization);
			String data = new String();
			data = "[";
			for (int i = 0; i < list.size(); i++) {
				JSONObject hd = (JSONObject) list.get(i);
				String text = "<span style='color:black'>患者：" + hd.getString("username")+ "&nbsp;&nbsp;&nbsp;医生：" + hd.getString("doctorname")+ "&nbsp;&nbsp;&nbsp;种植系统："+ hd.getString("zzxtname")+ "<br/>颗数：" + hd.getString("ks") + "</span>";
				data += "{" + "id:\"" + hd.getString("seqId") + "\"" + ",start_date: \"" + hd.getString("starttime") + "\"" + ",end_date: \"" + hd.getString("endtime") + "\""
						+ ",text: \"" + text + "\"" + ",createusername: \"" + hd.getString("createusername") + "\"" + ",createuser: \"" + hd.getString("createuser") + "\""
						+ ",usercode: \"" + hd.getString("usercode") + "\"" + ",username: \"" + hd.getString("username") + "\"" + ",roomid: \"" + hd.getString("roomid") + "\""
						+ ",phasedoctor: \"" + hd.getString("phasedoctor") + "\"" + ",phasedoctorname: \"" + hd.getString("phasedoctorname") + "\""
						+ ",roomname: \"" + hd.getString("roomname") + "\"" + ",nurse: \"" + hd.getString("nurse") + "\"" + ",nursename: \"" + hd.getString("nursename") + "\""
						+ ",doctor: \"" + hd.getString("doctor") + "\"" + ",doctorname: \"" + hd.getString("doctorname") + "\"" + ",remark: \"" + hd.getString("remark") + "\""
						+ ",delreason: \"" + hd.getString("delreason") + "\"" + ",isdelete: \"" + hd.getString("isdelete") + "\"" + ",delperson: \"" + hd.getString("delperson")
						+ "\"" + ",zzxt: \"" + hd.getString("zzxt") + "\"" + ",zzxtname: \"" + hd.getString("zzxtname") + "\"" + ",tooth: \"" + hd.getString("tooth") +"\"" + ",ks: \"" + hd.getString("ks") + "\""
						+ ",roomstatus: \"" + hd.getString("roomstatus") + "\"" + ",askperson: \"" + hd.getString("askperson") + "\"" +",askpersonname: \"" + hd.getString("askpersonname") + "\""+
						",age: \"" + hd.getString("age") + "\"" +",sex: \"" + hd.getString("sex") + "\""+",phonenumber1: \"" + hd.getString("phonenumber1") + "\"" +",phonenumber2: \"" + hd.getString("phonenumber2") + "\""+"},";
			}
			if (data.contains(",")) {
				data = data.substring(0, data.length() - 1);
			}
			data += "]";
			data = data.replaceAll("\r\n", "<br/>");
			JSONObject jobj = new JSONObject();
			jobj.put("result", "ok");
			jobj.put("path", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断患者门诊预约情况 （同一医生和患者，存在门诊预约也可以手术预约）
	 */
	@RequestMapping(value = "/checkSSSUsercode.act")
	public String checkSSSUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String endtime = formatter.format(formatter.parse(request.getParameter("endtime")));
			String usercode = request.getParameter("usercode");
			String doctor = request.getParameter("doctor");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			JSONObject jobj = new JSONObject();
			int flag = 0;
			KqdsRoom order = logic.checkSSSUsercode(map);
			String msg = "";
			if (order != null) {
				if (!YZUtility.isNullorEmpty(order.getDoctor()) || !YZUtility.isNullorEmpty(order.getNurse())) {
					//if (!order.getDoctor().equals(doctor) && !order.getNurse().equals(doctor)) {
					String doctorordername = personLogic.getNameStrBySeqIds(order.getDoctor());
					String nurseordername = "";//添加判断，如果预约没有选择护士  2019-8-2  syp
					if ((!order.getNurse().equals(""))) {
						nurseordername = personLogic.getNameStrBySeqIds(order.getNurse());
					}
					msg = "该时间段患者存在手术预约：<br>医生：" + doctorordername + "&nbsp;&nbsp;&nbsp;护士：" + nurseordername + "&nbsp;&nbsp;&nbsp;时间：" + order.getStarttime().substring(11)
							+ "-" + order.getEndtime().substring(11);
					flag = 1;
					//}
				}
			}
			jobj.put("data", flag);
			YZUtility.DEAL_SUCCESS(jobj, msg, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断患者门诊预约情况 （同一医生和患者，存在门诊预约也可以手术预约）
	 */
	@RequestMapping(value = "/checkSSSDoctor.act")
	public String checkSSSDoctor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String endtime = formatter.format(formatter.parse(request.getParameter("endtime")));
			String usercode = request.getParameter("usercode");
			String doctor = request.getParameter("doctor");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
			}
			JSONObject jobj = new JSONObject();
			int flag = 0;
			KqdsRoom order = logic.checkSSSUsercode(map);
			String msg = "";
			if (order != null) {
				if (!YZUtility.isNullorEmpty(order.getUsercode())) {
					//if (!order.getUsercode().equals(usercode)) {
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("usercode", order.getUsercode());
					List<KqdsUserdocument> en = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
					if (en == null || en.size() == 0) {
						throw new Exception("患者不存在，usercode值为：" + order.getUsercode());
					}
					msg = "该时间段医生存在手术预约：<br>患者：" + en.get(0).getUsername() + "&nbsp;&nbsp;&nbsp;时间：" + order.getStarttime().substring(11) + "-"
							+ order.getEndtime().substring(11);
					flag = 1;
					//}
				}
			}
			jobj.put("data", flag);
			YZUtility.DEAL_SUCCESS(jobj, msg, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 手术查询 【只查当前门诊】
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPage.act")
	public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sortName = request.getParameter("sortName");
		String sortOrder = request.getParameter("sortOrder");
		try {
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
//--------------------------------------------------------首頁搜索條件--------------------------------------------------------------------------
			String querytype = request.getParameter("querytype");
			String searchValue = request.getParameter("searchValue");
			YZPerson person = SessionUtil.getLoginPerson(request);
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();
			String zzxt = request.getParameter("zzxt");
			if (!YZUtility.isNullorEmpty(zzxt)) {
				map.put("zzxt", zzxt);
			}
			String roomstatus = request.getParameter("roomstatus");
			if (!YZUtility.isNullorEmpty(roomstatus)) {
				map.put("roomstatus", roomstatus);
			}
			String doctor = request.getParameter("doctor");
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
			}
			String nurse = request.getParameter("nurse");
			if (!YZUtility.isNullorEmpty(nurse)) {
				map.put("nurse", nurse);
			}
			String kefu = request.getParameter("kefu");
			if (!YZUtility.isNullorEmpty(kefu)) {
				map.put("kefu", kefu);
			}
			String starttime = request.getParameter("starttime");
			if(starttime==null || starttime.equals("")){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				starttime = df.format(new Date());
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			String endtime = request.getParameter("endtime");
			if(endtime==null || endtime.equals("")){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				endtime = df.format(new Date());
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			String isdelete = request.getParameter("isdelete");
			if (!YZUtility.isNullorEmpty(isdelete)) {
				map.put("isdelete", isdelete);
			}
			String username = request.getParameter("username");
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			// 首页面调用改方法时 传值1 ：添加可见人员过滤，不展示取消的预约
			String jrroom = request.getParameter("jrroom");
			if (YZUtility.isNullorEmpty(jrroom)) {
				jrroom = "0";
			}

			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject json = new JSONObject();
			// 查当前门诊的
			String visualstaff = SessionUtil.getVisualstaff(request);
			String organization = request.getParameter("organization");
			if (!YZUtility.isNullorEmpty(organization)) {
				//map.put("organization", ChainUtil.getCurrentOrganization(request));
				map.put("organization", organization);
			}else{
				map.put("organization", ChainUtil.getCurrentOrganization(request));
			}
			if(sortName != null){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
				json = logic.selectNoPage(TableNameUtil.KQDS_ROOM, map, visualstaff, jrroom,searchValue,bp,json);
			}else{
				json = logic.selectNoPage(TableNameUtil.KQDS_ROOM, map, visualstaff, jrroom,searchValue,bp,json);
			}
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("手术信息", fieldArr, fieldnameArr, (List<JSONObject>)json.get("rows"), response, request);
				return null;
			}

			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}