package com.kqds.service.sys.online;

import com.kqds.entity.sys.YZOnline;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service("onlineLogic")
public class YZOnlineLogic {
  public YZOnline getOnlineByUserSeqId(String userTmpId) {
    for (Map.Entry<String, HttpSession> entry : (Iterable<Map.Entry<String, HttpSession>>)SessionUtil.Session_MAP.entrySet()) {
      String userId = entry.getKey();
      HttpSession session = entry.getValue();
      if (session == null)
        continue; 
      if (userTmpId.equals(userId)) {
        YZOnline online = (YZOnline)session.getAttribute(SessionUtil.ONLINE_INFO);
        return online;
      } 
    } 
    return null;
  }
  
  public void updateOnlineInfoByUserSeqId(YZOnline online) {
    String userIdTmp = online.getUserId();
    for (Map.Entry<String, HttpSession> entry : (Iterable<Map.Entry<String, HttpSession>>)SessionUtil.Session_MAP.entrySet()) {
      String userId = entry.getKey();
      HttpSession session = entry.getValue();
      if (session == null)
        continue; 
      if (userIdTmp.equals(userId))
        session.setAttribute(SessionUtil.ONLINE_INFO, online); 
    } 
  }
  
  public List<YZOnline> getList() throws SQLException {
    List<YZOnline> list = new ArrayList<>();
    for (Map.Entry<String, HttpSession> entry : (Iterable<Map.Entry<String, HttpSession>>)SessionUtil.Session_MAP.entrySet()) {
      HttpSession session = entry.getValue();
      if (session == null)
        continue; 
      try {
        YZOnline online = (YZOnline)session.getAttribute(SessionUtil.ONLINE_INFO);
        if (online == null)
          continue; 
        list.add(online);
      } catch (Exception exception) {}
    } 
    return list;
  }
  
  public boolean isLogin(String userTmpId) throws Exception {
    int count = 0;
    for (Map.Entry<String, HttpSession> entry : (Iterable<Map.Entry<String, HttpSession>>)SessionUtil.Session_MAP.entrySet()) {
      String userId = entry.getKey();
      if (userTmpId.equals(userId))
        count++; 
    } 
    return (count > 0);
  }
  
  public int queryUserCount() throws Exception {
    int count = SessionUtil.Session_MAP.size();
    return count;
  }
  
  public void addOnline(YZPerson person, String sessionToken, HttpSession session, HttpServletRequest request) throws Exception {
    YZOnline online = new YZOnline();
    online.setSeqId(YZUtility.getUUID());
    online.setSessionToken(sessionToken);
    online.setLoginTime(new Date());
    online.setUserId(person.getSeqId());
    online.setUserState("0");
    online.setOrganization(ChainUtil.getCurrentOrganization(session));
    online.setUpdateTime(System.currentTimeMillis());
    session.setAttribute(SessionUtil.ONLINE_INFO, online);
  }
}
