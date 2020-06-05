package com.kqds.util.base.websocket;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.util.base.WebSocketUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**
 * 在线人员，用于检测人员在线情况和推送消息
 * 
 * @author Administrator
 * 
 */
@ServerEndpoint("/checkUserOnlineAndPushMsg/{userId}")
public class Online_User_List {
	private static Logger logger = LoggerFactory.getLogger(Online_User_List.class);
	private String userId = null;
	public static Map<String, Session> Websockt_Session_MAP = new ConcurrentHashMap<String, Session>();

	@OnOpen
	public void onOpen(@PathParam("userId") String userId, Session session) throws Exception {

		// 获取当前登录用户的id
		this.userId = userId;
		Websockt_Session_MAP.put(userId, session);
		int count = Websockt_Session_MAP.size();
		// 防止刷新websockt 尚未建立，用户为0
		if (count == 0) {
			count = 1;
		}
		try {
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, new ArrayList<JSONObject>());
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put("retDataOnline", count);
			String pushText = jobj.toString();
			WebSocketUtil.sendMsg2Page(session, pushText);
			/** 推送在线人数 **/
		} catch (IOException ex) {
			YZUtility.DEAL_ERROR(null, false, ex, null, logger);
		}
	}

	@OnClose
	public void onClose(CloseReason reason) {
		Websockt_Session_MAP.remove(this.userId);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		// logger.debug("发生错误:" + error.getMessage());
		// error.printStackTrace();
		logger.error("checkUserOnlineAndPushMsg websocket 发生错误:" + error.getMessage());
	}

}