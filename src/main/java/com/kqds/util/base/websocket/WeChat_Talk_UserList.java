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

@ServerEndpoint("/wechat_userlist/{userseqid}")
public class WeChat_Talk_UserList {
  private static Logger logger = LoggerFactory.getLogger(WeChat_Talk_UserList.class);
  
  private String userseqid = null;
  
  public static Map<String, Session> WeChat_KeFuPerson_List = new ConcurrentHashMap<>();
  
  @OnOpen
  public void onOpen(@PathParam("userseqid") String userseqid, Session session) throws Exception {
    this.userseqid = userseqid;
    WeChat_KeFuPerson_List.put(this.userseqid, session);
  }
  
  @OnClose
  public void onClose(CloseReason reason) {
    WeChat_KeFuPerson_List.remove(this.userseqid);
  }
  
  @OnError
  public void onError(Session session, Throwable error) {
    logger.error("wechat_userlist websocket 发生错误:" + error.getMessage());
  }
}
