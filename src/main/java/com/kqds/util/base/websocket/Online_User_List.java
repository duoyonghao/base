package com.kqds.util.base.websocket;

import com.kqds.util.base.WebSocketUtil;
import com.kqds.util.sys.YZUtility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/checkUserOnlineAndPushMsg/{userId}")
public class Online_User_List
{
  private static Logger logger = LoggerFactory.getLogger(Online_User_List.class);
  private String userId = null;
  public static Map<String, Session> Websockt_Session_MAP = new ConcurrentHashMap();
  
  @OnOpen
  public void onOpen(@PathParam("userId") String userId, Session session)
    throws Exception
  {
    this.userId = userId;
    Websockt_Session_MAP.put(userId, session);
    int count = Websockt_Session_MAP.size();
    if (count == 0) {
      count = 1;
    }
    try
    {
      JSONObject jobj = new JSONObject();
      jobj.put("retData", new ArrayList());
      jobj.put("retState", "0");
      jobj.put("retDataOnline", Integer.valueOf(count));
      String pushText = jobj.toString();
      WebSocketUtil.sendMsg2Page(session, pushText);
    }
    catch (IOException ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, null, logger);
    }
  }
  
  @OnClose
  public void onClose(CloseReason reason)
  {
    Websockt_Session_MAP.remove(this.userId);
  }
  
  @OnError
  public void onError(Session session, Throwable error)
  {
    logger.error("checkUserOnlineAndPushMsg websocket 发生错误:" + error.getMessage());
  }
}
