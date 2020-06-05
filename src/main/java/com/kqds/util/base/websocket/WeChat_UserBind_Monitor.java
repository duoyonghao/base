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
 * 监听有参二维码扫码的绑定结果
 * 
 * @author Administrator
 * 
 */
@ServerEndpoint("/userbind/{openid}")
public class WeChat_UserBind_Monitor {
	private static Logger logger = LoggerFactory.getLogger(WeChat_UserBind_Monitor.class);

	// 用户openid
	private String usercode = null;

	// 用户id和websocket的session绑定的路由表
	public static Map<String, Session> WeChat_UserBind_Monitor = new ConcurrentHashMap<String, Session>();

	@OnOpen
	public void onOpen(@PathParam("openid") String openidParam, Session session) throws Exception {
		// 获取当前登录用户的id
		this.usercode = openidParam;
		WeChat_UserBind_Monitor.put(this.usercode, session);
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(CloseReason reason) {
		WeChat_UserBind_Monitor.remove(this.usercode);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		// logger.debug("发生错误:" + error.getMessage());
		// error.printStackTrace();
		logger.error("userbind websocket 发生错误:" + error.getMessage());
	}

}