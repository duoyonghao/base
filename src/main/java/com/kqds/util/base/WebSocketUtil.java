package com.kqds.util.base;

import java.io.IOException;
import javax.websocket.Session;

public class WebSocketUtil {
  public static void sendMsg2Page(Session session, String msg) throws IOException {
    if (session != null)
      session.getBasicRemote().sendText(msg); 
  }
}
