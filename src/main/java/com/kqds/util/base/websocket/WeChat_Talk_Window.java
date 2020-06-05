package com.kqds.util.base.websocket;

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

import com.kqds.service.wx.core.WXUtil4ChatLogic;
import com.kqds.util.wx.MessageUtil;

/**
 * 聊天窗口
 * 
 * @author Administrator
 * 
 */
@ServerEndpoint("/wechat4talk/{openid}")
public class WeChat_Talk_Window {
	private static Logger logger = LoggerFactory.getLogger(WeChat_Talk_Window.class);
	private String openid = null;
	private String userseqid = null;

	public static Map<String, Session> WeChat_Talk_OpenIdList = new ConcurrentHashMap<String, Session>();
	public static Map<String, String> WeChat_Talk_UserSeqIdList = new ConcurrentHashMap<String, String>();

	@Autowired
	private WXUtil4ChatLogic wxUtil4ChatLogic;

	@OnOpen
	public void onOpen(@PathParam("openid") String openidParam, Session session) throws Exception {
		String[] paramArr = openidParam.split("\\*");

		// 获取当前登录用户的id
		this.openid = paramArr[0];
		this.userseqid = paramArr[1];

		WeChat_Talk_OpenIdList.put(this.openid, session);
		WeChat_Talk_UserSeqIdList.put(this.openid, userseqid);

		/** 更改未读消息数 **/
		wxUtil4ChatLogic.updateChatUserListMsg(this.openid, MessageUtil.EVENT_TYPE_OPEN_TALK, null, null);

	}

	@OnClose
	public void onClose(CloseReason reason) throws SQLException, Exception {
		WeChat_Talk_OpenIdList.remove(this.openid);
		WeChat_Talk_UserSeqIdList.remove(this.openid);

		/** 更改未读消息数 **/
		wxUtil4ChatLogic.updateChatUserListMsg(this.openid, MessageUtil.EVENT_TYPE_CLOSE_TALK, null, null);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		// error.printStackTrace();
		logger.error("wechat4talk websocket 发生错误:" + error.getMessage());
	}

}