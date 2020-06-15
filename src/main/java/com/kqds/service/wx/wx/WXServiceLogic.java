package com.kqds.service.wx.wx;

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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  
  public void coreService(HttpServletRequest request) throws Exception {
    Map<String, String> requestMap = MessageUtil.parseReuest4Map(request);
    String fromUserName = requestMap.get("FromUserName");
    String toUserName = requestMap.get("ToUserName");
    String msgType = requestMap.get("MsgType");
    String msgId = requestMap.get("MsgId");
    String content = requestMap.get("Content");
    if (msgType.equals("text")) {
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
      KqdsUserdocument user = this.wxUserLogic.getBindUserDocByOpenId(fromUserName, request);
      if (user != null)
        receiveText.setUsercode(user.getUsercode()); 
      this.logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, receiveText);
      this.fansLogic.updateLastTimeByOpenid(fromUserName, request);
      this.wxUtil4ChatLogic.sendMsg2Client(fromUserName, receiveText);
      this.wxUtil4ChatLogic.updateChatUserListMsg(fromUserName, null, null, request);
      this.wxUtil4ChatLogic.notReadCountUpate(request);
    } else if (msgType.equals("image")) {
      LogUtil.info("------------微信客户端发送请求------------------【微信触发类型】图片消息---");
      String mediaid = requestMap.get("MediaId");
      String accessToken = WXUtilLogic.getAccessToken();
      String picUrl = WXDownLoadUtil.saveImageToDisk(mediaid, msgId, accessToken, request);
      WXReceivetext receiveText = new WXReceivetext();
      receiveText.setSeqId(YZUtility.getUUID());
      receiveText.setAccountid(toUserName);
      receiveText.setFromusername(fromUserName);
      receiveText.setCreatetime(YZUtility.getCurDateTimeStr());
      receiveText.setMsgid(msgId);
      receiveText.setTousername(toUserName);
      receiveText.setMsgtype("image");
      receiveText.setPicurl(picUrl);
      receiveText.setMediaid(mediaid);
      KqdsUserdocument user = this.wxUserLogic.getBindUserDocByOpenId(fromUserName, request);
      if (user != null)
        receiveText.setUsercode(user.getUsercode()); 
      this.logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, receiveText);
      this.fansLogic.updateLastTimeByOpenid(fromUserName, request);
      this.wxUtil4ChatLogic.sendMsg2Client(fromUserName, receiveText);
      this.wxUtil4ChatLogic.updateChatUserListMsg(fromUserName, null, null, request);
      this.wxUtil4ChatLogic.notReadCountUpate(request);
    } else if (msgType.equals("event")) {
      String eventType = requestMap.get("Event");
      if (eventType.equals("subscribe")) {
        String eventkey = requestMap.get("EventKey");
        String openid = requestMap.get("FromUserName");
        if (!YZUtility.isNullorEmpty(eventkey) && eventkey.startsWith("qrscene_")) {
          this.fansLogic.bindWXUser(eventkey, openid, request);
        } else {
          this.fansLogic.normalCare(openid, request);
        } 
        this.wxUtilLogic.getUserBaseInfo(openid, request);
      } else if (eventType.equals("unsubscribe")) {
        String openid = requestMap.get("FromUserName");
        if (!YZUtility.isNullorEmpty(openid)) {
          this.fansLogic.unBindWXUser(openid, request);
          this.wxUtil4ChatLogic.updateChatUserListMsg(fromUserName, "unsubscribe", null, request);
        } 
      } else if (eventType.equals("SCAN")) {
        String openid = requestMap.get("FromUserName");
        String eventkey = requestMap.get("EventKey");
        if (!YZUtility.isNullorEmpty(eventkey)) {
          this.fansLogic.bindWXUser(eventkey, openid, request);
          this.wxUtilLogic.getUserBaseInfo(openid, request);
        } 
      } 
    } else {
      String receiveContent = "";
      String respContent = "";
      if (msgType.equals("location")) {
        receiveContent = "用户发送了一条地理位置消息！";
        respContent = "公众号暂不支持地理位置消息，请发送文字或图片！";
      } else if (msgType.equals("link")) {
        receiveContent = "用户发送了一条链接消息！";
        respContent = "公众号暂不支持链接消息，请发送文字或图片！";
      } else if (msgType.equals("voice")) {
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
      KqdsUserdocument user = this.wxUserLogic.getBindUserDocByOpenId(fromUserName, request);
      String usercode = null;
      if (user != null) {
        usercode = user.getUsercode();
        receiveText.setUsercode(usercode);
      } 
      this.logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, receiveText);
      this.fansLogic.updateLastTimeByOpenid(fromUserName, request);
      this.wxUtil4ChatLogic.sendMsg2Client(fromUserName, receiveText);
      this.wxUtil4ChatLogic.updateChatUserListMsg(fromUserName, null, null, request);
      this.wxUtil4ChatLogic.notReadCountUpate(request);
      String sendMsgUrl = this.textLogic.getUrl4Send(respContent);
      this.wxUtilLogic.sendTextMsg(sendMsgUrl, usercode, fromUserName, respContent, request);
    } 
  }
}
