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

@ServerEndpoint("/checkNotReadWeChatMsg/{userId}")
public class WeChat_NotRead_Monitor {
  private static Logger logger = LoggerFactory.getLogger(WeChat_NotRead_Monitor.class);
  
  private String userId = null;
  
  public static Map<String, Session> Online_User_List4Wechat_NotRead = new ConcurrentHashMap<>();
  
  @OnOpen
  public void onOpen(@PathParam("userId") String userId, Session session) throws Exception {
    this.userId = userId;
    Online_User_List4Wechat_NotRead.put(userId, session);
  }
  
  @OnClose
  public void onClose(CloseReason reason) {
    Online_User_List4Wechat_NotRead.remove(this.userId);
  }
  
  @OnError
  public void onError(Session session, Throwable error) {
    logger.error("checkNotReadWeChatMsg websocket 发生错误:" + error.getMessage());
  }
}
