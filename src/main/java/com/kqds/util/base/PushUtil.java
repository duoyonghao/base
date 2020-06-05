package com.kqds.util.base;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsJzqk;
import com.kqds.entity.base.KqdsMemberRecordSh;
import com.kqds.entity.base.KqdsOutprocessingSheet;
import com.kqds.entity.base.KqdsPaycost;
import com.kqds.entity.base.KqdsPush;
import com.kqds.entity.base.KqdsRefund;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsRoom;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.push.KQDS_Pushogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

/**
 * 此类主要涉及推送信息相关
 * 
 * @author Administrator
 * 
 */

@Component
@Controller
public class PushUtil {
	@Autowired
	private KQDS_Pushogic logic;
	@Autowired
	private YZDictLogic dictLogic;

	private static PushUtil pushUtil;

	public void setPushLogic(KQDS_Pushogic logic) {
		this.logic = logic;
	}

	public void setYZDictLogic(YZDictLogic dictLogic) {
		this.dictLogic = dictLogic;
	}

	@PostConstruct
	public void init() {
		pushUtil = this;
		pushUtil.logic = this.logic;
		pushUtil.dictLogic = this.dictLogic;
	}

	public static String EMPTY_PUSH_URL = ""; // 推送时间
	public static String EMPTY_PUSH_TIME = ""; // 推送时间
	public static String IS_NOW_PUSH_0 = "0";
	public static String IS_NOW_PUSH_1 = "1";

	// 存储提醒
	private static void saveTx(String type, String receiver, String content, String url, String isnow, String pushtime, String organization) throws Exception {

		KqdsPush p = new KqdsPush();
		String pid = YZUtility.getUUID();
		p.setSeqId(pid);
		p.setCreatetime(YZUtility.getCurDateTimeStr());
		p.setContent(content);
		p.setNotifytype(type);
		p.setReciveuser(receiver);
		p.setRemindurl(url);
		p.setIsnowpush(isnow);// 0及时推送 1定时推送
		p.setTargetpushtime(pushtime);// 推送时间 isnowpush为0时赋值 默认当天的0点
		p.setOrganization(organization); // ### 【当前所在门诊】
		pushUtil.logic.saveSingleUUID(TableNameUtil.KQDS_PUSH, p);
	}

	/**
	 * 加工单回件 2
	 */
	public static void saveTx4OutProcessingSheetBack(KqdsOutprocessingSheet en, HttpServletRequest request) throws Exception {
		saveTx("加工", en.getCreateuser(), "您提交的加工单已回件，单号:" + en.getSeqId() + ",请注意查收", "/KQDS_OutProcessingAct/toJgIndex.act?sno=" + en.getSeqId() + "&type=pushlist", IS_NOW_PUSH_0,
				EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 提交加工单、发件 1
	 */
	public static void saveTx4OutProcessingSheetSubmit(KqdsOutprocessingSheet dp, HttpServletRequest request) throws Exception {
		saveTx("返工", dp.getDoctorno(), "您提交的加工单已发件，单号:" + dp.getSeqId() + ",请知悉。", "/KQDS_OutProcessingAct/toJgIndex.act?sno=" + dp.getSeqId() + "&type=pushlist", IS_NOW_PUSH_0,
				EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 返工 4
	 */
	public static void saveTx4OutProcessingSheetFG(KqdsOutprocessingSheet dp1, HttpServletRequest request) throws Exception {
		saveTx("返工", dp1.getDoctorno(), "返工操作完成，已创建一个新的加工单，单号:" + dp1.getSeqId() + ",请查看", "/KQDS_OutProcessingAct/toJgIndex.act?sno=" + dp1.getSeqId() + "&type=pushlist",
				IS_NOW_PUSH_0, EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 修改加工单
	 */
	public static void saveTx4OutProcessingSheetNew(KqdsOutprocessingSheet en, JSONObject person, HttpServletRequest request) throws Exception {
		saveTx("加工", person.getString("seqId"), "您的加工单已创建,单号:" + en.getSeqId() + "，请注意查看", "/KQDS_OutProcessingAct/toJgIndex.act?sno=" + en.getSeqId() + "&type=pushlist",
				IS_NOW_PUSH_0, EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 修改加工单
	 */
	public static void saveTx4OutProcessingSheetEdit(KqdsOutprocessingSheet en, HttpServletRequest request) throws Exception {
		saveTx("加工", en.getCreateuser(), "您的加工单已修改,单号:" + en.getSeqId() + "，请注意查看", "/KQDS_OutProcessingAct/toJgIndex.act?sno=" + en.getSeqId() + "&type=pushlist", IS_NOW_PUSH_0,
				EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 开单提醒
	 */
	public static void saveTx4NewCostOrder(String receiver, HttpServletRequest request) throws Exception {
		saveTx("开单", receiver, "有一条新的开单记录,请注意收费", EMPTY_PUSH_URL, IS_NOW_PUSH_0, EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 新建推广计划
	 * 
	 * @param receiver
	 */
	public static void saveTx4NewExtension(String receiver, String visittime, HttpServletRequest request) throws Exception {
		saveTx("推广", receiver, "您有一条新的推广计划,截止时间:" + visittime + "，请查看", EMPTY_PUSH_URL, IS_NOW_PUSH_0, EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 推广计划超时提醒 注意：推广超时提醒的remindurl存usercode
	 * 
	 * @param receiver
	 */
	public static void saveTx4ExtensionTimeOut(JSONObject extension) throws Exception {
		saveTx("推广超时", extension.getString("visitor"), "您有一条推广计划已超时，患者姓名：" + extension.getString("username") + ",截止时间：" + extension.getString("visittime") + "，请尽快完成",
				extension.getString("usercode"), IS_NOW_PUSH_0, EMPTY_PUSH_TIME, extension.getString("organization"));
	}

	/**
	 * 修改门诊预约
	 * 
	 * @param receiver
	 */
	public static void saveTx4ModifyHosOrder(KqdsHospitalOrder dp, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_Net_OrderAct/toYyzx2.act?doctors=" + dp.getAskperson() + "&regdept=" + dp.getRegdept();
		saveTx("门诊", dp.getAskperson(), "您有一条门诊预约已修改,预约时间:" + dp.getStarttime().substring(5) + " 到 " + dp.getEndtime().substring(5) + "，请查看", pushurl, IS_NOW_PUSH_1,
				dp.getStarttime().substring(0, 10) + ConstUtil.TIME_START, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 新建门诊预约
	 * 
	 * @param receiver
	 */
	public static void saveTx4NewHosOrder(KqdsHospitalOrder dp, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_Net_OrderAct/toYyzx2.act?doctors=" + dp.getAskperson() + "&regdept=" + dp.getRegdept();
		saveTx("门诊", dp.getAskperson(), "您有一条新的门诊预约,预约时间:" + dp.getStarttime().substring(5) + " 到 " + dp.getEndtime().substring(5) + "，请查看", pushurl, IS_NOW_PUSH_1,
				dp.getStarttime().substring(0, 10) + ConstUtil.TIME_START, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 修改手术室预约
	 * 
	 * @param receiver
	 */
	public static void saveTx4ModifyRoomOrder(KqdsRoom dp, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_Net_OrderAct/toYyzx2.act?doctors=" + dp.getDoctor();
		saveTx("手术室", dp.getDoctor(), "您有一条手术室预约已修改,预约时间:" + dp.getStarttime().substring(5) + " 到 " + dp.getEndtime().substring(5) + "，请查看", pushurl, IS_NOW_PUSH_1,
				dp.getStarttime().substring(0, 10) + ConstUtil.TIME_START, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 新建手术室预约
	 * 
	 * @param receiver
	 */
	public static void saveTx4NewRoomOrder(KqdsRoom dp, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_RoomAct/toRoom.act?doctors=" + dp.getDoctor();
		saveTx("手术室", dp.getDoctor(), "您有一条新的手术室预约,预约时间:" + dp.getStarttime().substring(5) + " 到 " + dp.getEndtime().substring(5) + "，请查看", pushurl, IS_NOW_PUSH_1,
				dp.getStarttime().substring(0, 10) + ConstUtil.TIME_START, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 就诊提醒
	 * 
	 * @param receiver
	 */
	public static void saveTx4Jzqk(KqdsJzqk dp, KqdsReg reg, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_JzqkAct/toTx.act";
		saveTx("就诊提醒", dp.getDoctor(), "就诊提醒,患者姓名:" + reg.getUsername() + ",已到复诊时间，请注意查看", pushurl, IS_NOW_PUSH_1, dp.getFzdata() + ConstUtil.TIME_START,
				ChainUtil.getCurrentOrganization(request));
	}
	/**
	 * 提醒回访
	 * 
	 * @param receiver
	 */
	public static void selectTx4NewVisit(KqdsVisit dp, HttpServletRequest request) throws Exception {
		// 删除回访数据，一个患者只保留一条回访数据
		//deleteVistPushInfo(dp.getUsercode(), dp.getVisitor());
		// 新建回访
		String pushurl2 = "/KQDS_VisitAct/toVisitList3.act?visittime=" + dp.getVisittime()+"&visitor="+dp.getVisitor();
		saveTx("回访提醒", dp.getVisitor(), "您今天的回访,回访时间:" + dp.getVisittime() + "，请查看", pushurl2, IS_NOW_PUSH_1, dp.getVisittime(), ChainUtil.getCurrentOrganization(request));
	}
	/**
	 * 新建回访
	 * 
	 * @param receiver
	 */
	public static void saveTx4NewVisit(KqdsVisit dp, HttpServletRequest request) throws Exception {
		// 删除回访数据，一个患者只保留一条回访数据
		deleteVistPushInfo(dp.getUsercode(), dp.getVisitor());
		// 新建回访
		String pushurl2 = "/KQDS_VisitAct/toVisitList2.act?usercode=" + dp.getUsercode();
		saveTx("回访", dp.getVisitor(), "您有一条新的回访,回访时间:" + dp.getVisittime() + "，请查看", pushurl2, IS_NOW_PUSH_1, dp.getVisittime() + ":00", ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 修改回访
	 * 
	 * @param receiver
	 */
	public static void saveTx4ModifyVisit(KqdsVisit dp, HttpServletRequest request) throws Exception {
		// 删除回访数据，一个患者只保留一条回访数据
		deleteVistPushInfo(dp.getUsercode(), dp.getVisitor());
		// 修改回访
		String pushurl = "/KQDS_VisitAct/toVisitList2.act?usercode=" + dp.getUsercode();
		saveTx("回访", dp.getVisitor(), "您有一条回访已被修改,回访时间:" + dp.getVisittime() + "，请查看", pushurl, IS_NOW_PUSH_1, dp.getVisittime() + ":00",
				ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 回访超时
	 * 
	 * @param receiver
	 */
	public static void saveTx4VisitTimeOut(JSONObject visit) throws Exception {
		String pushurl = "/KQDS_VisitAct/toVisitList2.act?usercode=" + visit.getString("usercode");
		saveTx("回访超时", visit.getString("visitor"), "您有一条回访已超时，患者姓名：" + visit.getString("username") + ",回访时间：" + visit.getString("visittime") + "，请尽快完成", pushurl, IS_NOW_PUSH_0,
				EMPTY_PUSH_TIME, visit.getString("organization"));
	}

	/**
	 * 删除回访数据，一个患者只保留一条回访数据
	 * 
	 * @param usercode
	 * @param reciveuser
	 * @throws Exception
	 */
	private static void deleteVistPushInfo(String usercode, String reciveuser) throws Exception {
		// 删除重复的提醒记录
		pushUtil.logic.deleteVistPushInfo(usercode, reciveuser);
	}

	/**
	 * 新建预收退款申请
	 * 
	 * @param zjl总经理
	 * @param sqr
	 *            申请人
	 */
	public static void saveTx4MemberRefund(JSONObject zjl, YZPerson sqr, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_MemberAct/toember_TkList.act";
		saveTx("预收退款", zjl.getString("seq_id") + "", "您有一条新的预收退款申请需要审批,申请人:" + sqr.getUserName() + "，请注意查看", pushurl, IS_NOW_PUSH_0, EMPTY_PUSH_TIME,
				ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 审核预收退款申请
	 * 
	 * @param dp
	 * @param zjl
	 *            总经理
	 */
	public static void saveTx4MemberSH(JSONObject caiwu, YZPerson zjl, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_MemberAct/toember_TkList.act";
		saveTx("退费", caiwu.getString("seq_id") + "", "您有一条新的预收退款申请已审批,审批人:" + zjl.getUserName() + "，请注意查看", pushurl, IS_NOW_PUSH_0, EMPTY_PUSH_TIME,
				ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 预收退费确认
	 */
	public static void saveTx4MemberConfirm(KqdsMemberRecordSh dp, YZPerson zjl, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_MemberAct/toember_TkList.act";
		saveTx("退费", dp.getCreateuser() + "", "您有一条新的预收退款申请已确认退款,确认人:" + zjl.getUserName() + "，请注意查看", pushurl, IS_NOW_PUSH_0, EMPTY_PUSH_TIME,
				ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 缴费
	 */
	public static void saveTx4PayCost(KqdsPaycost dp, HttpServletRequest request) throws Exception {
		String pushurl = "";
		saveTx("缴费", dp.getDoctor(), "您有一个患者已缴费,请注意接待", pushurl, IS_NOW_PUSH_0, EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 退费确认
	 */
	public static void saveTx4TuiFeiConfirm(KqdsRefund dp, YZPerson zjl, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_RefundAct/toCost_TkList.act";
		saveTx("退费", dp.getCreateuser() + "", "您有一条新的退款申请已确认退款,确认人:" + zjl.getUserName() + "，请注意查看", pushurl, IS_NOW_PUSH_0, EMPTY_PUSH_TIME,
				ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 退费审核
	 * 
	 * @param sfr
	 *            收费人员
	 * @param zjl
	 *            审核人
	 */
	public static void saveTx4TuiFeiSH(JSONObject sfr, YZPerson zjl, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_RefundAct/toCost_TkList.act";
		saveTx("退费", sfr.getString("seq_id") + "", "您有一条新的退款申请已审批,审批人:" + zjl.getUserName() + "，请注意查看", pushurl, IS_NOW_PUSH_0, EMPTY_PUSH_TIME,
				ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 新建退款申请
	 * 
	 * @param sfr
	 *            收费人员
	 * @param zjl
	 *            审核人
	 */
	public static void saveTx4NewTuiFei(JSONObject zjl, KqdsRefund dp, HttpServletRequest request) throws Exception {
		String pushurl = "/KQDS_RefundAct/toCost_TkList.act";
		saveTx("退费", zjl.getString("seq_id") + "", "您有一条新的退款申请需要审批,申请人:" + dp.getUsername() + "，请注意查看", pushurl, IS_NOW_PUSH_0, EMPTY_PUSH_TIME,
				ChainUtil.getCurrentOrganization(request));
	}

	/**
	 * 新建挂号
	 */
	public static void saveTx4NewReg(KqdsReg dp, HttpServletRequest request) throws Exception {
		if (!YZUtility.isNullorEmpty(dp.getAskperson())) {
			saveTx("挂号", dp.getAskperson(), "您有新的挂号,患者姓名:" + dp.getUsername() + ",分类:" + pushUtil.dictLogic.getDictNameBySeqId(dp.getRegsort()) + "，请准备接待", EMPTY_PUSH_URL,
					IS_NOW_PUSH_0, EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
		}
		if (!YZUtility.isNullorEmpty(dp.getDoctor())) {
			saveTx("挂号", dp.getDoctor(), "您有新的挂号,患者姓名:" + dp.getUsername() + ",分类:" + pushUtil.dictLogic.getDictNameBySeqId(dp.getRegsort()) + "，请准备接待", EMPTY_PUSH_URL,
					IS_NOW_PUSH_0, EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
		}
		if (!YZUtility.isNullorEmpty(dp.getReceiveno())) {
			saveTx("挂号", dp.getReceiveno(), "您有新的挂号,患者姓名:" + dp.getUsername() + ",分类:" + pushUtil.dictLogic.getDictNameBySeqId(dp.getRegsort()) + "，请准备接待", EMPTY_PUSH_URL,
					IS_NOW_PUSH_0, EMPTY_PUSH_TIME, ChainUtil.getCurrentOrganization(request));
		}
	}

	/**
	 * 挂号变更
	 */
	public static void saveTx4ModifyReg(KqdsReg dp, String regsortname, HttpServletRequest request) throws Exception {
		if (!YZUtility.isNullorEmpty(dp.getAskperson())) {
			saveTx("挂号", dp.getAskperson(), "您有新的挂号变更,患者姓名:" + dp.getUsername() + ",分类:" + regsortname + "，请准备接待", EMPTY_PUSH_URL, IS_NOW_PUSH_0, EMPTY_PUSH_TIME,
					ChainUtil.getCurrentOrganization(request));
		}
		if (!YZUtility.isNullorEmpty(dp.getDoctor())) {
			saveTx("挂号", dp.getDoctor(), "您有新的挂号变更,患者姓名:" + dp.getUsername() + ",分类:" + regsortname + "，请准备接待", EMPTY_PUSH_URL, IS_NOW_PUSH_0, EMPTY_PUSH_TIME,
					ChainUtil.getCurrentOrganization(request));
		}
		if (!YZUtility.isNullorEmpty(dp.getReceiveno())) {
			saveTx("挂号", dp.getReceiveno(), "您有新的挂号变更,患者姓名:" + dp.getUsername() + ",分类:" + regsortname + "，请准备接待", EMPTY_PUSH_URL, IS_NOW_PUSH_0, EMPTY_PUSH_TIME,
					ChainUtil.getCurrentOrganization(request));
		}
	}

	/**
	 * 库存上限报警
	 */
	public static void saveTx4CKTopAlarm(YZPerson dp, String alarmContent, String organization) throws Exception {
		saveTx("库存报警", dp.getSeqId(), alarmContent, EMPTY_PUSH_URL, IS_NOW_PUSH_0, EMPTY_PUSH_TIME, organization);
	}

	/**
	 * 库存下线报警
	 */
	public static void saveTx4CKDownAlarm(YZPerson dp, String alarmContent, String organization) throws Exception {
		saveTx("库存报警", dp.getSeqId(), alarmContent, EMPTY_PUSH_URL, IS_NOW_PUSH_0, EMPTY_PUSH_TIME, organization);
	}

}
