package com.kqds.service.wx.wx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.wx.WXReceivetext;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.wx.core.WXUtil4ChatLogic;
import com.kqds.service.wx.core.WXUtil4TemplateMsgLogic;
import com.kqds.service.wx.core.WXUtilLogic;
import com.kqds.service.wx.fans.WXFansLogic;
import com.kqds.service.wx.receivetext.WXReceiveTextLogic;
import com.kqds.service.wx.user.WXUserDocLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.wx.LogUtil;
import com.kqds.util.wx.MessageUtil;
import com.kqds.util.wx.WXDownLoadUtil;

@Service
public class WXServiceLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private WXFansLogic fansLogic;
	@Autowired
	private WXUserDocLogic wxUserLogic;
	@Autowired
	private WXReceiveTextLogic textLogic;
	@Autowired
	private KQDS_UserDocumentLogic logic;

	@Autowired
	private WXUtilLogic wxUtilLogic;
	@Autowired
	private WXUtil4TemplateMsgLogic wxUtil4TemplateMsgLogic;
	@Autowired
	private WXUtil4ChatLogic wxUtil4ChatLogic;

	/**
	 * 微信处理
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void coreService(HttpServletRequest request) throws Exception {
		// xml请求解析
		Map<String, String> requestMap = MessageUtil.parseReuest4Map(request);

		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		// 消息类型
		String msgType = requestMap.get("MsgType");
		String msgId = requestMap.get("MsgId");
		// 消息内容
		String content = requestMap.get("Content");
		// 【微信触发类型】文本消息
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			LogUtil.info("------------微信客户端发送请求------------------【微信触发类型】文本消息---");
			WXReceivetext receiveText = new WXReceivetext();
			receiveText.setSeqId(YZUtility.getUUID());
			receiveText.setAccountid(toUserName);
			receiveText.setFromusername(fromUserName);
			receiveText.setContent(content);
			receiveText.setCreatetime(YZUtility.getCurDateTimeStr());
			receiveText.setMsgid(msgId);
			receiveText.setTousername(toUserName);
			receiveText.setMsgtype("text");
			KqdsUserdocument user = wxUserLogic.getBindUserDocByOpenId(fromUserName, request);
			if (user != null) {
				receiveText.setUsercode(user.getUsercode());
			}
			logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, receiveText);

			/** 更新最后接收到信息的时间 **/
			fansLogic.updateLastTimeByOpenid(fromUserName, request);

			/** 发送消息到客户端 **/
			wxUtil4ChatLogic.sendMsg2Client(fromUserName, receiveText);
			/** 更改未读消息数 **/
			wxUtil4ChatLogic.updateChatUserListMsg(fromUserName, null, null, request);
			/** 更新总的未读数量 **/
			wxUtil4ChatLogic.notReadCountUpate(request);
		} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			LogUtil.info("------------微信客户端发送请求------------------【微信触发类型】图片消息---");
			// String picurl = requestMap.get("PicUrl");
			String mediaid = requestMap.get("MediaId");

			String accessToken = WXUtilLogic.getAccessToken();
			String picUrl = WXDownLoadUtil.saveImageToDisk(mediaid, msgId, accessToken, request); // 使用下来下载的路径

			WXReceivetext receiveText = new WXReceivetext();
			receiveText.setSeqId(YZUtility.getUUID());
			receiveText.setAccountid(toUserName);
			receiveText.setFromusername(fromUserName);
			receiveText.setCreatetime(YZUtility.getCurDateTimeStr());
			receiveText.setMsgid(msgId);
			receiveText.setTousername(toUserName);
			receiveText.setMsgtype("image");
			/** 图片属性 **/
			receiveText.setPicurl(picUrl);
			receiveText.setMediaid(mediaid);
			KqdsUserdocument user = wxUserLogic.getBindUserDocByOpenId(fromUserName, request);
			if (user != null) {
				receiveText.setUsercode(user.getUsercode());
			}
			logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, receiveText);

			/** 更新最后接收到信息的时间 **/
			fansLogic.updateLastTimeByOpenid(fromUserName, request);

			/** 发送消息到客户端 **/
			wxUtil4ChatLogic.sendMsg2Client(fromUserName, receiveText);

			/** 更改未读消息数 **/
			wxUtil4ChatLogic.updateChatUserListMsg(fromUserName, null, null, request);

			/** 更新总的未读数量 **/
			wxUtil4ChatLogic.notReadCountUpate(request);
		} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = requestMap.get("Event");
			// 订阅
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				/**
				 * 返回数据： {Ticket=
				 * gQG18TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAySm9qZ0JCOU9iTV8xWlEwNGhwMTcAAgTwvMRZAwSEAwAA
				 * , CreateTime=1506065842, EventKey=qrscene_1000, Event=subscribe,
				 * ToUserName=gh_eda5ce5b0b32, FromUserName=oSbLwv8Za3kzalxRboPcY_ls8LZo,
				 * MsgType=event}
				 */
				// 如果是绑定订阅
				String eventkey = requestMap.get("EventKey");
				String openid = requestMap.get("FromUserName");
				if (!YZUtility.isNullorEmpty(eventkey) && eventkey.startsWith("qrscene_")) {
					fansLogic.bindWXUser(eventkey, openid, request);
				} else {
					fansLogic.normalCare(openid, request);
				}

				/** 获取基本信息并更新 **/
				wxUtilLogic.getUserBaseInfo(openid, request);
			}
			// 取消订阅
			else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				String openid = requestMap.get("FromUserName");
				if (!YZUtility.isNullorEmpty(openid)) {
					fansLogic.unBindWXUser(openid, request);

					/** 更改未读消息数 **/
					wxUtil4ChatLogic.updateChatUserListMsg(fromUserName, MessageUtil.EVENT_TYPE_UNSUBSCRIBE, null, request);
				}
			} // 扫描二维码--适用于已经关注公众号后，再次通过右侧个人信息页面的二维码进行扫描 <<-<<-<<-
			else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
				// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				String openid = requestMap.get("FromUserName");
				String eventkey = requestMap.get("EventKey");
				if (!YZUtility.isNullorEmpty(eventkey)) {
					fansLogic.bindWXUser(eventkey, openid, request);
					/** 获取基本信息并更新 **/
					wxUtilLogic.getUserBaseInfo(openid, request);
				}
			}
		} else {
			String receiveContent = "";
			String respContent = "";
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				receiveContent = "用户发送了一条地理位置消息！";
				respContent = "公众号暂不支持地理位置消息，请发送文字或图片！";
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				receiveContent = "用户发送了一条链接消息！";
				respContent = "公众号暂不支持链接消息，请发送文字或图片！";
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				receiveContent = "用户发送了一条音频消息！";
				respContent = "公众号暂不支持音频消息，请发送文字或图片！";
			} else {
				receiveContent = "用户进行了一次未标识操作行为，类型为：" + msgType + "！";
				respContent = "公众号暂不支持此类操作，请发送文字或图片！";
			}

			WXReceivetext receiveText = new WXReceivetext();
			receiveText.setSeqId(YZUtility.getUUID());
			receiveText.setAccountid(toUserName);
			receiveText.setFromusername(fromUserName);
			receiveText.setContent(receiveContent);
			receiveText.setCreatetime(YZUtility.getCurDateTimeStr());
			receiveText.setMsgid(msgId);
			receiveText.setTousername(toUserName);
			receiveText.setMsgtype("text");
			KqdsUserdocument user = wxUserLogic.getBindUserDocByOpenId(fromUserName, request);
			String usercode = null;
			if (user != null) {
				usercode = user.getUsercode();
				receiveText.setUsercode(usercode);
			}
			logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, receiveText);

			/** 更新最后接收到信息的时间 **/
			fansLogic.updateLastTimeByOpenid(fromUserName, request);

			/** 发送消息到客户端 **/
			wxUtil4ChatLogic.sendMsg2Client(fromUserName, receiveText);
			/** 更改未读消息数 **/
			wxUtil4ChatLogic.updateChatUserListMsg(fromUserName, null, null, request);
			/** 更新总的未读数量 **/
			wxUtil4ChatLogic.notReadCountUpate(request);

			// 自动回复消息给用户
			String sendMsgUrl = textLogic.getUrl4Send(respContent);
			wxUtilLogic.sendTextMsg(sendMsgUrl, usercode, fromUserName, respContent, request);

		}
	}

}
