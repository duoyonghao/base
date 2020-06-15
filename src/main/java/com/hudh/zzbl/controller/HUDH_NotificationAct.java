package com.hudh.zzbl.controller;

import com.hudh.zzbl.entity.Notification;
import com.hudh.zzbl.service.INotificationService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/HUDH_NotificationAct"})
public class HUDH_NotificationAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_NotificationAct.class);
  
  @Autowired
  private INotificationService iNotificationService;
  
  @RequestMapping({"/saveNotification.act"})
  public String saveNotification(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    String lcljId = request.getParameter("LcljId");
    String lcljNum = request.getParameter("LcljNum");
    String content = request.getParameter("content");
    String doctortime = request.getParameter("doctortime");
    String patienttime = request.getParameter("patienttime");
    String whether = request.getParameter("whether");
    Notification notification = new Notification();
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
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
    try {
      this.iNotificationService.saveNotification(notification);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findNotificationByLcljId.act"})
  public String findNotificationByLcljId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String lcljId = request.getParameter("LcljId");
    String lcljNum = request.getParameter("LcljNum");
    Map<String, String> map = new HashMap<>();
    try {
      map.put("lcljId", lcljId);
      map.put("lcljNum", lcljNum);
      Notification notification = this.iNotificationService.findNotificationByLcljId(map);
      YZUtility.RETURN_OBJ(notification, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
