package com.hudh.zzbl.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.zzbl.entity.Notification;
import com.hudh.zzbl.service.INotificationService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;


@Controller
@RequestMapping("/HUDH_NotificationAct")
public class HUDH_NotificationAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_NotificationAct.class);
	
	@Autowired
	private INotificationService iNotificationService;
	
	/**
	 * 保存告知通知单信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveNotification.act")
	public String saveNotification(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode=request.getParameter("usercode");
		String username=request.getParameter("username");
		String lcljId=request.getParameter("LcljId");
		String lcljNum=request.getParameter("LcljNum");
		String content=request.getParameter("content");
		String doctortime=request.getParameter("doctortime");
		String patienttime=request.getParameter("patienttime");
		String whether=request.getParameter("whether");
		String doctorsignature=request.getParameter("doctorsignature");
		String patientsignature=request.getParameter("patientsignature");
		Notification notification = new Notification();
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		notification.setCreateuser(person.getUserId());
		notification.setOrganization(organization);
		notification.setSEQ_ID(YZUtility.getUUID());
		notification.setCreatetime(YZUtility.getCurDateTimeStr());
		notification.setUsercode(usercode);
		notification.setUsername(username);
		notification.setLcljId(lcljId);
		notification.setLcljNum(lcljNum);
		notification.setContent(content);
		notification.setDoctortime(doctortime);
		notification.setPatienttime(patienttime);
		notification.setWhether(whether);
		notification.setDoctorsignature(doctorsignature);
		notification.setPatientsignature(patientsignature);
		try {
			iNotificationService.saveNotification(notification);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	@RequestMapping(value = "/findNotificationByLcljId.act")
	public String findNotificationByLcljId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String lcljId=request.getParameter("LcljId");
		String lcljNum=request.getParameter("LcljNum");
		Map<String,String> map=new HashMap<String,String>();
		try {
			map.put("lcljId", lcljId);
			map.put("lcljNum", lcljNum);
			Notification notification=iNotificationService.findNotificationByLcljId(map);
			YZUtility.RETURN_OBJ(notification, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/updateNotification.act")
	public String updateNotification(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String lcljId=request.getParameter("LcljId");
		String lcljNum=request.getParameter("LcljNum");
		String content=request.getParameter("content");
		String doctortime=request.getParameter("doctortime");
		String patienttime=request.getParameter("patienttime");
		String whether=request.getParameter("whether");
		String doctorsignature=request.getParameter("doctorsignature");
		String patientsignature=request.getParameter("patientsignature");
		Notification notification = new Notification();
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		notification.setCreateuser(person.getUserId());
		notification.setOrganization(organization);
		notification.setSEQ_ID(YZUtility.getUUID());
		notification.setCreatetime(YZUtility.getCurDateTimeStr());
		notification.setLcljId(lcljId);
		notification.setLcljNum(lcljNum);
		notification.setContent(content);
		notification.setDoctortime(doctortime);
		notification.setPatienttime(patienttime);
		notification.setWhether(whether);
		notification.setDoctorsignature(doctorsignature);
		notification.setPatientsignature(patientsignature);
		try {
			int i=iNotificationService.updateNotification(notification);
			if(i>0){
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
