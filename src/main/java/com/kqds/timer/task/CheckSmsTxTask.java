package com.kqds.timer.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsSms;
import com.kqds.entity.base.KqdsSmsModel;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.service.base.hospitalOrder.KQDS_Hospital_OrderLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.netOrder.KQDS_Net_OrderLogic;
import com.kqds.service.base.sms.KQDS_Sms_ModelLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sms.SmsSender;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@Service
public class CheckSmsTxTask implements Job {
	@Autowired
	private KQDS_Net_OrderLogic netlogic;
	@Autowired
	private KQDS_Hospital_OrderLogic hoslogic;
	@Autowired
	private KQDS_Sms_ModelLogic smsmodellogic;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private KQDS_UserDocumentLogic userLogic;
	@Autowired
	private YZParaLogic paraLogic;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			doTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doTask() throws Exception {
		// Map<String, HttpSession> map = SessionUtil.Session_MAP;
		// 查询门诊
		// String organization = "";
		// for (String key : map.keySet()) {
		// organization = (String)
		// map.get(key).getAttribute(SessionUtil.LOGIN_ORGANIZATION);
		// }
		Map<String, String> map = new HashMap<String, String>();
		map.put("paraName",SysParaUtil.SMS_YYTX_BEFORE);
		map.put("organization","");
		String yyhouse = paraLogic.getParaValueByName(map);
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("paraName",SysParaUtil.SMS_BIRTH_BEFORE);
		map1.put("organization","");
		String srhouse = paraLogic.getParaValueByName(map1);

		String organization = YZSysProps.getProp(SysParaUtil.ORGANIZATION);
		// 获取当前小时
		Calendar cale = null;
		cale = Calendar.getInstance();
		int hour = cale.get(Calendar.HOUR_OF_DAY);
		if (Integer.parseInt(yyhouse) == hour) {
			// 查询短信模板
			KqdsSmsModel smsmodel = getsmscontent(organization, "DXFL_YY");
			// 预约提醒
			yytxWd(organization, smsmodel);
			yytxMz(organization, smsmodel);
		}
		if (Integer.parseInt(srhouse) == hour) {
			// 查询短信模板
			KqdsSmsModel smsmodel2 = getsmscontent(organization, "DXFL_SR");
			// 生日提醒
			srtx(organization, smsmodel2);
		}
	}

	public KqdsSmsModel getsmscontent(String organization, String parentcode) throws Exception {
		// 查询短信模板
		List<YZDict> dictlist = dictLogic.getListByParentCode(parentcode, organization);
		KqdsSmsModel smsmodel = new KqdsSmsModel();
		if (dictlist != null && dictlist.size() > 0) {
			List<JSONObject> smslist = smsmodellogic.getDsmodel(dictlist.get(0).getSeqId());
			if (smslist != null && smslist.size() > 0) {
				smsmodel = (KqdsSmsModel) JSONObject.toBean(smslist.get(0), KqdsSmsModel.class);
			}
		}
		return smsmodel;
	}

	public void yytxWd(String organization, KqdsSmsModel smsmodel) throws Exception {
		// 网电预约
		Map<String, String> map = new HashMap<String, String>();
		// 查询明天的预约
		String date = YZUtility.getDateStr(YZUtility.getDayAfter(new Date(), 1));
		map.put("starttime", date + ConstUtil.HOUR_START);
		map.put("endtime", date + ConstUtil.HOUR_END);
		JSONObject json = netlogic.selectNoPageYyzx(TableNameUtil.KQDS_NET_ORDER, map, null, organization,null,null);
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>)json.get("rows");
		// 发送短信
		if (list != null && list.size() > 0) {
			for (JSONObject jobj : list) {
				KqdsNetOrder netorder = (KqdsNetOrder) JSONObject.toBean(jobj, KqdsNetOrder.class);
				KqdsUserdocument user = userLogic.getSingleUserByUsercode(netorder.getUsercode());
				String smscontent = "";
				// 获取短信内容
				// 【患者姓名】您好，您于【预约时间】有预约，请及时就诊！
				String sex = "女士";
				if (user.getSex().equals("男")) {
					sex = "先生";
				}
				smscontent = user.getUsername() + sex + "您好，您于" + netorder.getOrdertime() + "有预约，请及时就诊！";
				if (!YZUtility.isNullorEmpty(smsmodel.getSmscontent())) {// 没有短信模板,设置默认短信格式
					try {
						smscontent = smsmodel.getSmscontent().replaceAll("【患者姓名】", user.getUsername() + sex);
						smscontent = smscontent.replaceAll("【预约时间】", netorder.getOrdertime());
					} catch (Exception e) {
						smscontent = user.getUsername() + sex + "您好，您于" + netorder.getOrdertime() + "有预约，请及时就诊！";
					}

				}
				sendSms(organization, user, smsmodel, smscontent);
			}
		}
	}

	public void yytxMz(String organization, KqdsSmsModel smsmodel) throws Exception {
		// 网电预约
		Map<String, String> map = new HashMap<String, String>();
		// 查询明天的预约
		String date = YZUtility.getDateStr(YZUtility.getDayAfter(new Date(), 1));
		map.put("starttime", date + ConstUtil.HOUR_START);
		map.put("endtime", date + ConstUtil.HOUR_END);
		if (YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		JSONObject json = hoslogic.selectNoPage(TableNameUtil.KQDS_HOSPITAL_ORDER, map, null, null, null);
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>)json.get("rows");
		// 发送短信
		if (list != null && list.size() > 0) {
			for (JSONObject jobj : list) {
				KqdsHospitalOrder netorder = (KqdsHospitalOrder) JSONObject.toBean(jobj, KqdsHospitalOrder.class);
				KqdsUserdocument user = userLogic.getSingleUserByUsercode(netorder.getUsercode());
				String smscontent = "";
				// 获取短信内容
				// 【患者姓名】您好，您于【预约时间】有预约，请及时就诊！
				String sex = "女士";
				if (user.getSex().equals("男")) {
					sex = "先生";
				}
				smscontent = user.getUsername() + sex + "您好，您于" + netorder.getOrdertime() + "有预约，请及时就诊！";
				if (!YZUtility.isNullorEmpty(smsmodel.getSmscontent())) {// 没有短信模板,设置默认短信格式
					try {
						smscontent = smsmodel.getSmscontent().replaceAll("【患者姓名】", user.getUsername() + sex);
						smscontent = smscontent.replaceAll("【预约时间】", netorder.getOrdertime());
					} catch (Exception e) {
						smscontent = user.getUsername() + sex + "您好，您于" + netorder.getOrdertime() + "有预约，请及时就诊！";
					}

				}
				sendSms(organization, user, smsmodel, smscontent);
			}
		}
	}

	public void srtx(String organization, KqdsSmsModel smsmodel) throws Exception {
		// 获取当前年份、月份、日期
		Calendar cale = null;
		cale = Calendar.getInstance();
		int month = cale.get(Calendar.MONTH) + 1;
		int day = cale.get(Calendar.DATE);
		List<JSONObject> list = userLogic.selectListByBirth(month, day, organization);
		// 发送短信
		if (list != null && list.size() > 0) {
			for (JSONObject jobj : list) {
				KqdsUserdocument user = (KqdsUserdocument) JSONObject.toBean(jobj, KqdsUserdocument.class);
				String smscontent = "";
				// 获取短信内容
				// 【患者姓名】您好，您于【预约时间】有预约，请及时就诊！
				String sex = "女士";
				if (user.getSex().equals("男")) {
					sex = "先生";
				}
				smscontent = user.getUsername() + sex + "，祝您生日快乐！";
				if (!YZUtility.isNullorEmpty(smsmodel.getSmscontent())) {// 没有短信模板,设置默认短信格式
					try {
						smscontent = smsmodel.getSmscontent().replaceAll("【患者姓名】", user.getUsername() + sex);
					} catch (Exception e) {
						smscontent = user.getUsername() + sex + "，祝您生日快乐！";
					}

				}
				sendSms(organization, user, smsmodel, smscontent);
			}
		}
	}

	public void sendSms(String organization, KqdsUserdocument user, KqdsSmsModel smsmodel, String smscontent) throws Exception {
		KqdsSms dp = new KqdsSms();
		String uuid = YZUtility.getUUID();
		dp.setSeqId(uuid);
		dp.setUsercode(user.getUsercode());
		dp.setSmscontent(smscontent);
		dp.setSmstype(smsmodel.getSmstype());
		dp.setSmsstate(0);
		dp.setSendstate(1);
		dp.setSendphone(user.getPhonenumber1());
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		dp.setSmsnexttype(smsmodel.getSmsnexttype());
		dp.setOrganization(organization);
		String returnString = SmsSender.getSenderTx(dp.getSendphone(), dp.getSmscontent());
		dp.setSendstate(Integer.parseInt(returnString));
		dp.setSendtime(YZUtility.getCurDateTimeStr());
		smsmodellogic.saveSingleUUID(TableNameUtil.KQDS_SMS, dp);
	}

}
