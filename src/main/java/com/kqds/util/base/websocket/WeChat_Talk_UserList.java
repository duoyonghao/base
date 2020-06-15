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

/**
 * 微信客服，左侧用户列表的websocket left.html
 */
@ServerEndpoint("/wechat_userlist/{userseqid}")
public class WeChat_Talk_UserList {
	private static Logger logger = LoggerFactory.getLogger(WeChat_Talk_UserList.class);
	private String userseqid = null; // 正在使用微信客服的系统用户seqId

	// 用户主键
	public static Map<String, Session> WeChat_KeFuPerson_List = new ConcurrentHashMap<String, Session>();

	@OnOpen
	public void onOpen(@PathParam("userseqid") String userseqid, Session session) throws Exception {
		// 获取当前登录用户的id
		this.userseqid = userseqid;
		WeChat_KeFuPerson_List.put(this.userseqid, session);
	}

	@OnClose
	public void onClose(CloseReason reason) {
		WeChat_KeFuPerson_List.remove(this.userseqid);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		// error.printStackTrace();
		logger.error("wechat_userlist websocket 发生错误:" + error.getMessage());
	}

}