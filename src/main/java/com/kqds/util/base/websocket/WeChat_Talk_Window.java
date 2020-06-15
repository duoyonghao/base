package com.kqds.util.base.websocket;

import com.kqds.service.wx.core.WXUtil4ChatLogic;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ServerEndpoint("/wechat4talk/{openid}")
public class WeChat_Talk_Window
{
  private static Logger logger = LoggerFactory.getLogger(WeChat_Talk_Window.class);
  private String openid = null;
  private String userseqid = null;
  public static Map<String, Session> WeChat_Talk_OpenIdList = new ConcurrentHashMap();
  public static Map<String, String> WeChat_Talk_UserSeqIdList = new ConcurrentHashMap();
  @Autowired
  private WXUtil4ChatLogic wxUtil4ChatLogic;
  
  @OnOpen
  public void onOpen(@PathParam("openid") String openidParam, Session session)
    throws Exception
  {
    String[] paramArr = openidParam.split("\\*");
    

    this.openid = paramArr[0];
    this.userseqid = paramArr[1];
    
    WeChat_Talk_OpenIdList.put(this.openid, session);
    WeChat_Talk_UserSeqIdList.put(this.openid, this.userseqid);
    

    this.wxUtil4ChatLogic.updateChatUserListMsg(this.openid, "opentalk", null, null);
  }
  
  @OnClose
  public void onClose(CloseReason reason)
    throws SQLException, Exception
  {
    WeChat_Talk_OpenIdList.remove(this.openid);
    WeChat_Talk_UserSeqIdList.remove(this.openid);
    

    this.wxUtil4ChatLogic.updateChatUserListMsg(this.openid, "colsetalk", null, null);
  }
  
  @OnError
  public void onError(Session session, Throwable error)
  {
    logger.error("wechat4talk websocket 发生错误:" + error.getMessage());
  }
}
