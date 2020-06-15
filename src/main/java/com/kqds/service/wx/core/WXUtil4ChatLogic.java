package com.kqds.service.wx.core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.wx.WXReceivetext;
import com.kqds.service.wx.receivetext.WXReceiveTextLogic;
import com.kqds.util.base.WebSocketUtil;
import com.kqds.util.base.websocket.WeChat_NotRead_Monitor;
import com.kqds.util.base.websocket.WeChat_Talk_UserList;
import com.kqds.util.base.websocket.WeChat_Talk_Window;
import com.kqds.util.wx.MessageUtil;
import com.kqds.util.wx.face.QQFaceUtil;

import net.sf.json.JSONObject;

@Service
public class WXUtil4ChatLogic {
	@Autowired
	public WXReceiveTextLogic textLogic;

	public void notReadCountUpate(HttpServletRequest request) throws Exception {
		for (Map.Entry<String, Session> entry : WeChat_NotRead_Monitor.Online_User_List4Wechat_NotRead.entrySet()) {
			// String userId = entry.getKey();
			Session session = entry.getValue();

			int count = textLogic.getNotReadMsgCountByOpenid(null, request);

			JSONObject jobj = new JSONObject();
			jobj.put("wdcount", count);
			String pushText = jobj.toString();
			WebSocketUtil.sendMsg2Page(session, pushText);

		}
	}

	/**
	 * 判断当前openid是否在会话
	 * 
	 * @param openid
	 * @return
	 */
	public int getTalkStatusByOpenid(String openid) {
		Session sess = WeChat_Talk_Window.WeChat_Talk_OpenIdList.get(openid);
		if (sess != null) {
			return 1; // 正在会话
		} else {
			return 0;
		}
	}

	/**
	 * 发送消息到客户端
	 * 
	 * @param openid
	 * @param msg
	 * @throws IOException
	 */
	public void sendMsg2Client(String openid, WXReceivetext msg) throws IOException {
		Session session = WeChat_Talk_Window.WeChat_Talk_OpenIdList.get(openid);

		JSONObject json = JSONObject.fromObject(msg);
		String pushText = json.toString();

		/** 处理微信表情 **/
		pushText = QQFaceUtil.dealQQFace(pushText);

		WebSocketUtil.sendMsg2Page(session, pushText);
	}

	/** 更改未读消息数 **/
	public void updateChatUserListMsg(String openid, String eventType, KqdsUserdocument user, HttpServletRequest request) throws SQLException, Exception {
		for (Map.Entry<String, Session> entry : WeChat_Talk_UserList.WeChat_KeFuPerson_List.entrySet()) {
			// String userId = entry.getKey();
			Session session = entry.getValue();

			JSONObject jobj = new JSONObject();

			jobj.put("openid", openid);

			if (request != null) {
				int wdcount = textLogic.getNotReadMsgCountByOpenid(openid, request);
				jobj.put("wdcount", wdcount);
			}

			if (MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
				jobj.put("eventtype", eventType);
			}

			if (MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
				jobj.put("eventtype", eventType);
			}
			if (MessageUtil.EVENT_TYPE_OPEN_TALK.equals(eventType)) { // 打开会话
				jobj.put("eventtype", eventType);
			}

			if (MessageUtil.EVENT_TYPE_CLOSE_TALK.equals(eventType)) { // 关闭会话
				jobj.put("eventtype", eventType);
			}

			if (MessageUtil.EVENT_TYPE_CREATE_DOC.equals(eventType)) { // 建档
				jobj.put("usercode", user.getUsercode());
				jobj.put("username", user.getUsername());
				jobj.put("openid", user.getOpenid());
				jobj.put("eventtype", eventType);
			}

			String pushText = jobj.toString();

			WebSocketUtil.sendMsg2Page(session, pushText);

		}

	}

}
