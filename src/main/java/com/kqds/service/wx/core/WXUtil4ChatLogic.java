package com.kqds.service.wx.core;

import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.wx.WXReceivetext;
import com.kqds.service.wx.receivetext.WXReceiveTextLogic;
import com.kqds.util.base.WebSocketUtil;
import com.kqds.util.base.websocket.WeChat_NotRead_Monitor;
import com.kqds.util.base.websocket.WeChat_Talk_UserList;
import com.kqds.util.base.websocket.WeChat_Talk_Window;
import com.kqds.util.wx.face.QQFaceUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXUtil4ChatLogic
{
  @Autowired
  public WXReceiveTextLogic textLogic;
  
  public void notReadCountUpate(HttpServletRequest request)
    throws Exception
  {
    for (Entry<String, Session> entry : WeChat_NotRead_Monitor.Online_User_List4Wechat_NotRead.entrySet())
    {
      Session session = (Session)entry.getValue();
      
      int count = this.textLogic.getNotReadMsgCountByOpenid(null, request);
      
      JSONObject jobj = new JSONObject();
      jobj.put("wdcount", Integer.valueOf(count));
      String pushText = jobj.toString();
      WebSocketUtil.sendMsg2Page(session, pushText);
    }
  }
  
  public int getTalkStatusByOpenid(String openid)
  {
    Session sess = (Session)WeChat_Talk_Window.WeChat_Talk_OpenIdList.get(openid);
    if (sess != null) {
      return 1;
    }
    return 0;
  }
  
  public void sendMsg2Client(String openid, WXReceivetext msg)
    throws IOException
  {
    Session session = (Session)WeChat_Talk_Window.WeChat_Talk_OpenIdList.get(openid);
    
    JSONObject json = JSONObject.fromObject(msg);
    String pushText = json.toString();
    

    pushText = QQFaceUtil.dealQQFace(pushText);
    
    WebSocketUtil.sendMsg2Page(session, pushText);
  }
  
  public void updateChatUserListMsg(String openid, String eventType, KqdsUserdocument user, HttpServletRequest request)
    throws SQLException, Exception
  {
    for (Entry<String, Session> entry : WeChat_Talk_UserList.WeChat_KeFuPerson_List.entrySet())
    {
      Session session = (Session)entry.getValue();
      
      JSONObject jobj = new JSONObject();
      
      jobj.put("openid", openid);
      if (request != null)
      {
        int wdcount = this.textLogic.getNotReadMsgCountByOpenid(openid, request);
        jobj.put("wdcount", Integer.valueOf(wdcount));
      }
      if ("unsubscribe".equals(eventType)) {
        jobj.put("eventtype", eventType);
      }
      if ("unsubscribe".equals(eventType)) {
        jobj.put("eventtype", eventType);
      }
      if ("opentalk".equals(eventType)) {
        jobj.put("eventtype", eventType);
      }
      if ("colsetalk".equals(eventType)) {
        jobj.put("eventtype", eventType);
      }
      if ("createdoc".equals(eventType))
      {
        jobj.put("usercode", user.getUsercode());
        jobj.put("username", user.getUsername());
        jobj.put("openid", user.getOpenid());
        jobj.put("eventtype", eventType);
      }
      String pushText = jobj.toString();
      
      WebSocketUtil.sendMsg2Page(session, pushText);
    }
  }
}
