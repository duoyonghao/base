package com.kqds.util.base.websocket;

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

@ServerEndpoint("/userbind/{openid}")
public class WeChat_UserBind_Monitor
{
  private static Logger logger = LoggerFactory.getLogger(WeChat_UserBind_Monitor.class);
  private String usercode = null;
  public static Map<String, Session> WeChat_UserBind_Monitor = new ConcurrentHashMap();
  
  @OnOpen
  public void onOpen(@PathParam("openid") String openidParam, Session session)
    throws Exception
  {
    this.usercode = openidParam;
    WeChat_UserBind_Monitor.put(this.usercode, session);
  }
  
  @OnClose
  public void onClose(CloseReason reason)
  {
    WeChat_UserBind_Monitor.remove(this.usercode);
  }
  
  @OnError
  public void onError(Session session, Throwable error)
  {
    logger.error("userbind websocket 发生错误:" + error.getMessage());
  }
}
