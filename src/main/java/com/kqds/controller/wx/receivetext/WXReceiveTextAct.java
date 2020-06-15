package com.kqds.controller.wx.receivetext;

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXBatchmsg;
import com.kqds.entity.wx.WXFans;
import com.kqds.entity.wx.WXReceivetext;
import com.kqds.entity.wx.WXTemplateitem;
import com.kqds.service.wx.core.WXUtil4ChatLogic;
import com.kqds.service.wx.core.WXUtil4TemplateMsgLogic;
import com.kqds.service.wx.core.WXUtilLogic;
import com.kqds.service.wx.fans.WXFansLogic;
import com.kqds.service.wx.news.WXNewsLogic;
import com.kqds.service.wx.receivetext.WXReceiveTextLogic;
import com.kqds.service.wx.templateitem.WXTemplateItemLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"WXReceiveTextAct"})
public class WXReceiveTextAct {
  private static Logger logger = LoggerFactory.getLogger(WXReceiveTextAct.class);
  
  @Autowired
  private WXReceiveTextLogic logic;
  
  @Autowired
  private WXFansLogic fansLogic;
  
  @Autowired
  private WXNewsLogic newsLogic;
  
  @Autowired
  private WXTemplateItemLogic itemLogic;
  
  @Autowired
  private WXUtilLogic wxUtilLogic;
  
  @Autowired
  private WXUtil4TemplateMsgLogic wxUtil4TemplateMsgLogic;
  
  @Autowired
  private WXUtil4ChatLogic wxUtil4ChatLogic;
  
  @RequestMapping({"/selectWdMsgCount.act"})
  public String selectWdMsgCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      int wdcount = this.logic.getNotReadMsgCountByOpenid(null, request);
      JSONObject retrunObj = new JSONObject();
      retrunObj.put("wdcount", Integer.valueOf(wdcount));
      YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  public static void main(String[] args) throws Exception {
    String text = "<img class=\"qqface qqface45\" title=\"左哼哼1\" src=\"static/image/kqdsFront/wechat/chat/spacer.gif\"></img>你好<img class=\"qqface qqface45\" title=\"左哼哼2\" src=\"static/image/kqdsFront/wechat/chat/spacer.gif\"></img>";
    String regxpForTag = "<\\s*img\\s+([^>]*)\\s*";
    String regxp4Class = "class=\\s*\"([^\"]+)\"";
    String regxp4Title = "title=\\s*\"([^\"]+)\"";
    String regxp4Src = "src=\\s*\"([^\"]+)\"";
    Pattern patternForTag = Pattern.compile(regxpForTag, 2);
    Pattern pattern4Class = Pattern.compile(regxp4Class, 2);
    Pattern pattern4Title = Pattern.compile(regxp4Title, 2);
    Pattern pattern4Src = Pattern.compile(regxp4Src, 2);
    Matcher matcherForTag = patternForTag.matcher(text);
    boolean result = matcherForTag.find();
    while (result) {
      String imgStr = matcherForTag.group(1);
      Matcher matcher4Class = pattern4Class.matcher(imgStr);
      Matcher matcher4Title = pattern4Title.matcher(imgStr);
      Matcher matcher4Src = pattern4Src.matcher(imgStr);
      String title = null;
      String classN = null;
      String src = null;
      if (matcher4Class.find())
        classN = matcher4Class.group(1); 
      if (matcher4Title.find())
        title = matcher4Title.group(1); 
      if (matcher4Src.find())
        src = matcher4Src.group(1); 
      StringBuffer imgBf1 = new StringBuffer("<img class=\"" + classN + "\" title=\"" + title + "\" src=\"" + src + "\"></img>");
      StringBuffer imgBf2 = new StringBuffer("<img class=\"" + classN + "\" title=\"" + title + "\" src=\"" + src + "\">");
      String titleBQ = "/" + title;
      text = text.replaceAll(imgBf1.toString(), titleBQ);
      text = text.replaceAll(imgBf2.toString(), titleBQ);
      result = matcherForTag.find();
    } 
  }
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      String openid = request.getParameter("openid");
      List<JSONObject> list = this.logic.selectList(seqId, openid);
      JSONObject retrunObj = new JSONObject();
      retrunObj.put("list", list);
      YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap<>();
      JSONObject data = this.logic.selectWithPage(TableNameUtil.WX_RECEIVETEXT, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/sendNews.act"})
  public String sendNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String openid = request.getParameter("openid");
      String newsid = request.getParameter("newsid");
      String usercode = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(openid))
        throw new Exception("openid不能为空"); 
      if (YZUtility.isNullorEmpty(newsid))
        throw new Exception("newsid不能为空"); 
      JSONObject newsMsg = this.newsLogic.getNewsObject4Send(newsid, request);
      JSONObject json = this.wxUtilLogic.sendNewsMsg(newsMsg, usercode, openid, newsid, request);
      YZUtility.DEAL_SUCCESS(json, "消息发送成功！", response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/sendMsg.act"})
  public String sendMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String openid = request.getParameter("openid");
      String content = request.getParameter("content");
      String usercode = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(openid))
        throw new Exception("openid不能为空"); 
      if (YZUtility.isNullorEmpty(content))
        throw new Exception("content不能为空"); 
      WXFans fans = this.fansLogic.getFanByOpenid(openid, request);
      if (fans.getCarestatus().intValue() != 0)
        throw new Exception("该患者已取消微信绑定"); 
      String sendMsgUrl = this.logic.getUrl4Send(content);
      this.wxUtilLogic.sendTextMsg(sendMsgUrl, usercode, openid, content, request);
      YZUtility.DEAL_SUCCESS(null, "消息发送成功！", response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/sendMsgBath.act"})
  public String sendMsgBath(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String openIds = request.getParameter("openIds");
      String userCodes = request.getParameter("userCodes");
      String sendContent = request.getParameter("sendContent");
      if (YZUtility.isNullorEmpty(openIds))
        throw new Exception("openIds不能为空"); 
      if (YZUtility.isNullorEmpty(userCodes))
        throw new Exception("userCodes不能为空"); 
      if (YZUtility.isNullorEmpty(sendContent))
        throw new Exception("sendContent不能为空"); 
      String sendMsgUrl = this.logic.getUrl4Send(sendContent);
      StringBuffer errOpenidBf = new StringBuffer();
      StringBuffer errUsercodeBf = new StringBuffer();
      StringBuffer okOpenidBf = new StringBuffer();
      StringBuffer okUsercodeBf = new StringBuffer();
      WXBatchmsg batchmsg = new WXBatchmsg();
      batchmsg.setCreatetime(YZUtility.getCurDateTimeStr());
      batchmsg.setCreateuser(person.getSeqId());
      batchmsg.setMsgtype("text");
      batchmsg.setMsgcontent(sendContent);
      batchmsg.setOpenids(openIds);
      batchmsg.setOpenids(userCodes);
      String[] openIdArray = openIds.split(",");
      String[] userCodeArray = userCodes.split(",");
      int failcount = 0;
      int successcount = 0;
      String errMsg = "";
      for (int i = 0; i < openIdArray.length; i++) {
        if (!YZUtility.isNullorEmpty(openIdArray[i]))
          try {
            this.wxUtilLogic.sendTextMsg(sendMsgUrl, userCodeArray[i], openIdArray[i], sendContent, request);
            successcount++;
            okOpenidBf.append(openIdArray[i]).append(",");
            okUsercodeBf.append(userCodeArray[i]).append(",");
          } catch (Exception e) {
            failcount++;
            errOpenidBf.append(openIdArray[i]).append(",");
            errUsercodeBf.append(userCodeArray[i]).append(",");
            errMsg = e.getMessage();
            e.printStackTrace();
          }  
      } 
      String rtmsg = "";
      if (failcount > 0) {
        rtmsg = String.valueOf(rtmsg) + "成功发送：" + successcount + "条，<br>";
        rtmsg = String.valueOf(rtmsg) + "失败发送：" + failcount + "条，<br>";
        rtmsg = String.valueOf(rtmsg) + "失败原因：" + errMsg;
      } else {
        rtmsg = "发送成功！";
      } 
      batchmsg.setOkcount(Integer.valueOf(successcount));
      batchmsg.setErrcount(Integer.valueOf(failcount));
      batchmsg.setOkopenids(okOpenidBf.toString());
      batchmsg.setOkusercodes(okUsercodeBf.toString());
      batchmsg.setErropenids(errOpenidBf.toString());
      batchmsg.setErrusercodes(errUsercodeBf.toString());
      batchmsg.setSeqId(YZUtility.getUUID());
      this.logic.saveSingleUUID(TableNameUtil.WX_BATCHMSG, batchmsg);
      YZUtility.DEAL_SUCCESS(null, rtmsg, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/sendNewsBath.act"})
  public String sendNewsBath(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String openIds = request.getParameter("openIds");
      String userCodes = request.getParameter("userCodes");
      if (YZUtility.isNullorEmpty(openIds))
        throw new Exception("openIds不能为空"); 
      if (YZUtility.isNullorEmpty(userCodes))
        throw new Exception("userCodes不能为空"); 
      String newsid = request.getParameter("newsid");
      if (YZUtility.isNullorEmpty(newsid))
        throw new Exception("newsid不能为空"); 
      JSONObject newsMsg = this.newsLogic.getNewsObject4Send(newsid, request);
      StringBuffer errOpenidBf = new StringBuffer();
      StringBuffer errUsercodeBf = new StringBuffer();
      StringBuffer okOpenidBf = new StringBuffer();
      StringBuffer okUsercodeBf = new StringBuffer();
      WXBatchmsg batchmsg = new WXBatchmsg();
      batchmsg.setCreatetime(YZUtility.getCurDateTimeStr());
      batchmsg.setCreateuser(person.getSeqId());
      batchmsg.setMsgtype("news");
      batchmsg.setMsgcontent(newsMsg.toString());
      batchmsg.setOpenids(openIds);
      batchmsg.setOpenids(userCodes);
      String[] openIdArray = openIds.split(",");
      String[] userCodeArray = userCodes.split(",");
      int failcount = 0;
      int successcount = 0;
      String errMsg = "";
      for (int i = 0; i < openIdArray.length; i++) {
        if (!YZUtility.isNullorEmpty(openIdArray[i]))
          try {
            this.wxUtilLogic.sendNewsMsg(newsMsg, userCodeArray[i], openIdArray[i], newsid, request);
            successcount++;
            okOpenidBf.append(openIdArray[i]).append(",");
            okUsercodeBf.append(userCodeArray[i]).append(",");
          } catch (Exception e) {
            failcount++;
            errOpenidBf.append(openIdArray[i]).append(",");
            errUsercodeBf.append(userCodeArray[i]).append(",");
            errMsg = e.getMessage();
            e.printStackTrace();
          }  
      } 
      String rtmsg = "";
      if (failcount > 0) {
        rtmsg = String.valueOf(rtmsg) + "成功发送：" + successcount + "条，<br>";
        rtmsg = String.valueOf(rtmsg) + "失败发送：" + failcount + "条，<br>";
        rtmsg = String.valueOf(rtmsg) + "失败原因：" + errMsg;
      } else {
        rtmsg = "发送成功！";
      } 
      batchmsg.setOkcount(Integer.valueOf(successcount));
      batchmsg.setErrcount(Integer.valueOf(failcount));
      batchmsg.setOkopenids(okOpenidBf.toString());
      batchmsg.setOkusercodes(okUsercodeBf.toString());
      batchmsg.setErropenids(errOpenidBf.toString());
      batchmsg.setErrusercodes(errUsercodeBf.toString());
      batchmsg.setSeqId(YZUtility.getUUID());
      this.logic.saveSingleUUID(TableNameUtil.WX_BATCHMSG, batchmsg);
      YZUtility.DEAL_SUCCESS(null, rtmsg, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/sendTemplateBath.act"})
  public String sendTemplateBath(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      WXTemplateitem dp = new WXTemplateitem();
      BeanUtils.populate(dp, request.getParameterMap());
      String openIds = request.getParameter("openIds");
      if (YZUtility.isNullorEmpty(openIds))
        throw new Exception("openIds不能为空"); 
      String templateId = request.getParameter("templateId");
      if (YZUtility.isNullorEmpty(templateId))
        throw new Exception("templateId不能为空"); 
      if (YZUtility.isNullorEmpty(dp.getFirst()))
        throw new Exception("first不能为空"); 
      JSONObject postParam = this.itemLogic.getTemplateItem4Send(dp, request);
      StringBuffer errOpenidBf = new StringBuffer();
      StringBuffer okOpenidBf = new StringBuffer();
      WXBatchmsg batchmsg = new WXBatchmsg();
      batchmsg.setCreatetime(YZUtility.getCurDateTimeStr());
      batchmsg.setCreateuser(person.getSeqId());
      batchmsg.setMsgtype("news");
      batchmsg.setMsgcontent(JSONObject.fromObject(dp).toString());
      batchmsg.setOpenids(openIds);
      String[] openIdArray = openIds.split(",");
      int failcount = 0;
      int successcount = 0;
      String errMsg = "";
      for (int i = 0; i < openIdArray.length; i++) {
        if (!YZUtility.isNullorEmpty(openIdArray[i]))
          try {
            this.wxUtil4TemplateMsgLogic.template_send(postParam, openIdArray[i], request);
            successcount++;
            okOpenidBf.append(openIdArray[i]).append(",");
          } catch (Exception e) {
            failcount++;
            errOpenidBf.append(openIdArray[i]).append(",");
            errMsg = e.getMessage();
            e.printStackTrace();
          }  
      } 
      String rtmsg = "";
      if (failcount > 0) {
        rtmsg = String.valueOf(rtmsg) + "成功发送：" + successcount + "条，<br>";
        rtmsg = String.valueOf(rtmsg) + "失败发送：" + failcount + "条，<br>";
        rtmsg = String.valueOf(rtmsg) + "失败原因：" + errMsg;
      } else {
        rtmsg = "发送成功！";
      } 
      batchmsg.setOkcount(Integer.valueOf(successcount));
      batchmsg.setErrcount(Integer.valueOf(failcount));
      batchmsg.setOkopenids(okOpenidBf.toString());
      batchmsg.setErropenids(errOpenidBf.toString());
      batchmsg.setSeqId(YZUtility.getUUID());
      this.logic.saveSingleUUID(TableNameUtil.WX_BATCHMSG, batchmsg);
      YZUtility.DEAL_SUCCESS(null, rtmsg, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getMediaIdAndSendMsg.act"})
  public String getMediaIdAndSendMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String openid = request.getParameter("openid");
      String attrId = request.getParameter("attrId");
      String attrName = request.getParameter("attrName");
      if (YZUtility.isNullorEmpty(openid))
        throw new Exception("openid不能为空"); 
      if (YZUtility.isNullorEmpty(attrId))
        throw new Exception("attrId不能为空"); 
      if (YZUtility.isNullorEmpty(attrName))
        throw new Exception("attrName不能为空"); 
      JSONObject msgObj = this.wxUtilLogic.getMediaIdAndSendMsg(openid, attrId, attrName, request);
      YZUtility.DEAL_SUCCESS(msgObj, "消息发送成功！", response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateStatus.act"})
  public String updateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String openid = request.getParameter("openid");
      String seqId = request.getParameter("seqId");
      WXReceivetext order = (WXReceivetext)this.logic.loadObjSingleUUID(TableNameUtil.WX_RECEIVETEXT, seqId);
      if (order == null)
        throw new Exception("记录不存在！"); 
      order.setIspush(Integer.valueOf(1));
      this.logic.updateSingleUUID(TableNameUtil.WX_RECEIVETEXT, order);
      this.wxUtil4ChatLogic.updateChatUserListMsg(openid, null, null, request);
      this.wxUtil4ChatLogic.notReadCountUpate(request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
