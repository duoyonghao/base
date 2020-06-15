package com.kqds.controller.sys.online;

import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZOnline;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"YZOnlineAct"})
public class YZOnlineAct {
  private Logger logger = LoggerFactory.getLogger(YZOnlineAct.class);
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private YZDeptLogic deptLogic;
  
  @Autowired
  private YZOnlineLogic onlineLogic;
  
  @RequestMapping({"/changeStatus.act"})
  public String changeStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String userid = request.getParameter("userid");
      String userstatus = request.getParameter("userstatus");
      if (YZUtility.isNullorEmpty(userid))
        throw new Exception("userid参数为空！"); 
      YZOnline online = this.onlineLogic.getOnlineByUserSeqId(userid);
      if (online != null)
        online.setUserState(userstatus); 
      this.onlineLogic.updateOnlineInfoByUserSeqId(online);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getUserStatus.act"})
  public String getUserStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String userid = request.getParameter("userid");
      if (YZUtility.isNullorEmpty(userid))
        throw new Exception("userid参数为空！"); 
      YZOnline lineuser = this.onlineLogic.getOnlineByUserSeqId(userid);
      if (lineuser != null)
        YZUtility.DEAL_SUCCESS(JSONObject.fromObject(lineuser), null, response, this.logger); 
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/cancelLogOut.act"})
  public String cancelLogOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
    YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    return null;
  }
  
  @RequestMapping({"/checkOnline.act"})
  public String checkOnline(HttpServletRequest request, HttpServletResponse response) throws Exception {
    YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    return null;
  }
  
  @RequestMapping({"/getOnlineTree.act"})
  public String getOnlineTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      StringBuffer useridBf = new StringBuffer();
      String currOrg = ChainUtil.getCurrentOrganization(request);
      List<YZOnline> list = this.onlineLogic.getList();
      for (YZOnline online : list) {
        String userSeqId = online.getUserId();
        if (YZUtility.isNullorEmpty(userSeqId))
          continue; 
        useridBf.append(userSeqId).append(",");
      } 
      List<JSONObject> personList = this.personLogic.getPersonListBySeqIds4Online(useridBf.toString(), currOrg);
      for (JSONObject person : personList) {
        String seqId = person.getString("seqId");
        for (YZOnline online : list) {
          if (seqId.equals(online.getUserId())) {
            String loginTime = YZUtility.getDateTimeStr(online.getLoginTime());
            person.put("user_state", online.getUserState());
            person.put("userState", online.getUserState());
            person.put("login_time", loginTime);
            person.put("loginTime", loginTime);
            break;
          } 
        } 
      } 
      String deptName = "";
      String roleName = "";
      for (int x = 0; x < personList.size(); x++) {
        JSONObject personinfo = personList.get(x);
        String loginTime = personinfo.getString("loginTime");
        String seqId = personinfo.getString("seqId");
        String deptId = personinfo.getString("deptId");
        String sex = personinfo.getString("sex");
        String userState = personinfo.getString("user_state");
        String userStateImg = "";
        if (sex.equals("1")) {
          if (userState.equals(ConstUtil.USER_STATUS_1)) {
            userStateImg = "<img src='" + request.getContextPath() + "/static/image/image/dtree/1-1.gif' align='absmiddle'>";
          } else if (userState.equals(ConstUtil.USER_STATUS_2)) {
            userStateImg = "<img src='" + request.getContextPath() + "/static/image/image/dtree/U12.gif' align='absmiddle'>";
          } else if (userState.equals(ConstUtil.USER_STATUS_3)) {
            userStateImg = "<img src='" + request.getContextPath() + "/static/image/image/dtree/U13.gif' align='absmiddle'>";
          } else {
            userStateImg = "<img src='" + request.getContextPath() + "/static/image/image/dtree/1-1.gif' align='absmiddle'>";
          } 
        } else if (userState.equals(ConstUtil.USER_STATUS_1)) {
          userStateImg = "<img src='" + request.getContextPath() + "/static/image/image/dtree/0-1.gif' align='absmiddle'>";
        } else if (userState.equals(ConstUtil.USER_STATUS_2)) {
          userStateImg = "<img src='" + request.getContextPath() + "/static/image/image/dtree/U02.gif' align='absmiddle'>";
        } else if (userState.equals(ConstUtil.USER_STATUS_3)) {
          userStateImg = "<img src='" + request.getContextPath() + "/static/image/image/dtree/U03.gif' align='absmiddle'>";
        } else {
          userStateImg = "<img src='" + request.getContextPath() + "/static/image/image/dtree/0-1.gif' align='absmiddle'>";
        } 
        deptName = YZUtility.encodeSpecial(personinfo.getString("deptName"));
        roleName = YZUtility.encodeSpecial(personinfo.getString("privName"));
        if ("0".equals(userState))
          userState = "空闲"; 
        if ("1".equals(userState))
          userState = "忙碌"; 
        if ("2".equals(userState))
          userState = "休息"; 
        if ("3".equals(userState))
          userState = "离开"; 
        personinfo.put("userStatus", userState);
        personinfo.put("name", personinfo.getString("userName"));
        personinfo.put("imgPath", userStateImg);
        personinfo.put("deptName", deptName);
        personinfo.put("userId", seqId);
        personinfo.put("lastVistTime", loginTime);
        personinfo.put("title", "部门:" + deptName + "\\n角色:" + roleName);
        personinfo.put("deptId", deptId);
      } 
      Set<String> deptUniqList = new HashSet<>();
      for (JSONObject person : personList)
        deptUniqList.add(person.getString("deptId")); 
      StringBuffer deptIdBf = new StringBuffer();
      for (String deptId : deptUniqList)
        deptIdBf.append(deptId).append(","); 
      String deptIdString = deptIdBf.toString();
      if (!deptIdString.startsWith(","))
        deptIdString = "," + deptIdString; 
      StringBuffer htmlBf = new StringBuffer();
      List<YZDept> deptList = this.deptLogic.getAllDeptByOrganization(null);
      for (YZDept yzDept : deptList) {
        if (!deptIdString.matches(".*," + yzDept.getSeqId() + ",+.*"))
          continue; 
        htmlBf.append("<li><p>" + yzDept.getDeptName() + "(" + yzDept.getDeptCode() + ")</p>" + "<ul class='personStateUl'>");
        for (JSONObject person : personList) {
          if (yzDept.getSeqId().equals(person.getString("deptId"))) {
            String lastVistTime = person.getString("lastVistTime");
            if (lastVistTime != null && lastVistTime.length() > 19)
              lastVistTime = lastVistTime.substring(0, 19); 
            htmlBf.append("<li><span style='width:83px;'>" + person.getString("userStatus") + "</span>" + "<span style='width:100px;'>" + person.getString("name") + 
                "</span>" + "<span style=''>" + lastVistTime + "</span>" + "</li>");
          } 
        } 
        htmlBf.append("</ul></li>");
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", htmlBf.toString());
      jobj.put("deptStr", deptIdBf.toString());
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
}
