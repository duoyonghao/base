package com.kqds.util.base;

import javax.websocket.Session;
import java.io.IOException;



public class WebSocketUtil {

	/**
	 * 发送消息到前台页面
	 * 
	 * @param session
	 * @param msg
	 * @throws IOException
	 */
	public static void sendMsg2Page(Session session, String msg) throws IOException {
		if (session != null) {
			session.getBasicRemote().sendText(msg);
		}

	}

}
