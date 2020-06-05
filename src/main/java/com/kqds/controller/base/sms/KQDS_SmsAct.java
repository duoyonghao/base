package com.kqds.controller.base.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsSms;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.sms.KQDS_SmsLogic;
import com.kqds.util.sms.SmsSender;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_SmsAct")
public class KQDS_SmsAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_SmsAct.class);
	@Autowired
	private KQDS_SmsLogic logic;

	@RequestMapping(value = "/toSendCenter.act")
	public ModelAndView toSendCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/sms/sms/send_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSmsCenter.act")
	public ModelAndView toSmsCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/sms/sms/sms_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSendSms.act")
	public ModelAndView toSendSms(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/sms/sms/send_sms.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSendSmsDetail.act")
	public ModelAndView toSendSmsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/sms/sms/detail_sms.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSendSmsEdit.act")
	public ModelAndView toSendSmsEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/sms/sms/edit_sms.jsp");
		return mv;
	}

	/**
	 * 入库，修改
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
			KqdsSms dp = new KqdsSms();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				// 判断实时发送 还是定时发送
				if (0 == dp.getSmsstate()) {
					String returnString = SmsSender.getSenderTx(dp.getSendphone(), dp.getSmscontent());
					dp.setSendstate(Integer.parseInt(returnString));
					dp.setSendtime(YZUtility.getCurDateTimeStr());
				}
				logic.updateSingleUUID(TableNameUtil.KQDS_SMS, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_SMS, dp, TableNameUtil.KQDS_SMS, request);
			} else {
				String[] usercodeArr = dp.getUsercode().split(",");
				String[] sendphoneArr = dp.getSendphone().split(",");
				for (int i = 0; i < usercodeArr.length; i++) {
					String uuid = YZUtility.getUUID();
					dp.setSeqId(uuid);
					dp.setUsercode(usercodeArr[i]);
					String phone = YZUtility.getPhone(sendphoneArr[i]);
					dp.setSendphone(sendphoneArr[i]);
					dp.setCreatetime(YZUtility.getCurDateTimeStr());
					dp.setCreateuser(person.getSeqId());
					dp.setOrganization(ChainUtil.getCurrentOrganization(request));
					// 判断实时发送 还是定时发送
					if (0 == dp.getSmsstate()) {
						String returnString = SmsSender.getSenderTx(phone, dp.getSmscontent());
						dp.setSendstate(Integer.parseInt(returnString));
						dp.setSendtime(YZUtility.getCurDateTimeStr());
					}
					logic.saveSingleUUID(TableNameUtil.KQDS_SMS, dp);
				}
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_SMS, dp, TableNameUtil.KQDS_SMS, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			KqdsSms en = (KqdsSms) logic.loadObjSingleUUID(TableNameUtil.KQDS_SMS, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_SMS, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_SMS, en, TableNameUtil.KQDS_SMS, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/*
	 * 详情返回
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			KqdsSms en = (KqdsSms) logic.loadObjSingleUUID(TableNameUtil.KQDS_SMS, seqId);
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
	 * 不分页查询 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/NoselectPage.act")
	public String NoselectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String organization = request.getParameter("organization");
			String smstype = request.getParameter("smstype");
			String smsnexttype = request.getParameter("smsnexttype");
			String smsstate = request.getParameter("smsstate");
			String sendstate = request.getParameter("sendstate");
			String sendphone = request.getParameter("sendphone");
			String username = request.getParameter("username");
			Map<String, String> map = new HashMap<String, String>();
			if (usercode != null && !usercode.equals("")) {
				map.put("usercode", usercode);
			}
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			if (!YZUtility.isNullorEmpty(smstype)) {
				map.put("smstype", smstype);
			}
			if (!YZUtility.isNullorEmpty(smsnexttype)) {
				map.put("smsnexttype", smsnexttype);
			}

			if (!YZUtility.isNullorEmpty(smsstate)) {
				map.put("smsstate", smsstate);
			}
			if (!YZUtility.isNullorEmpty(sendstate)) {
				map.put("sendstate", sendstate);
			}
			if (!YZUtility.isNullorEmpty(sendphone)) {
				map.put("sendphone", sendphone);
			}
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			List<JSONObject> list = (List<JSONObject>) logic.noSelectWithPage(TableNameUtil.KQDS_SMS, map);

			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String usercode = request.getParameter("usercode");
			String organization = request.getParameter("organization");
			String smstype = request.getParameter("smstype");
			String smsnexttype = request.getParameter("smsnexttype");
			String smsstate = request.getParameter("smsstate");
			String sendstate = request.getParameter("sendstate");
			String sendphone = request.getParameter("sendphone");
			String username = request.getParameter("username");
			Map<String, String> map = new HashMap<String, String>();
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			if (!YZUtility.isNullorEmpty(smstype)) {
				map.put("smstype", smstype);
			}
			if (!YZUtility.isNullorEmpty(smsnexttype)) {
				map.put("smsnexttype", smsnexttype);
			}
			if (usercode != null && !usercode.equals("")) {
				map.put("usercode", usercode);
			}

			if (!YZUtility.isNullorEmpty(smsstate)) {
				map.put("smsstate", smsstate);
			}
			if (!YZUtility.isNullorEmpty(sendstate)) {
				map.put("sendstate", sendstate);
			}
			if (!YZUtility.isNullorEmpty(sendphone)) {
				map.put("sendphone", sendphone);
			}
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_SMS, bp, map);
			String dxye = SmsSender.GetBal();
			data.put("dxye", dxye);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}