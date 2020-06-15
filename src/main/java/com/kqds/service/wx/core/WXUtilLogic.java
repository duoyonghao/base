package com.kqds.service.wx.core;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXFans;
import com.kqds.entity.wx.WXReceivetext;
import com.kqds.service.wx.fans.WXFansLogic;
import com.kqds.service.wx.receivetext.WXReceiveTextLogic;
import com.kqds.service.wx.user.WXUserDocLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.wx.MyX509TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXUtilLogic {
  @Autowired
  private WXReceiveTextLogic logic;
  
  @Autowired
  private WXUserDocLogic wxUserDocLogic;
  
  @Autowired
  private WXFansLogic fansLogic;
  
  public Map<String, String> QR_CODE_MAP = new HashMap<>();
  
  public final String GET_METHOD = "GET";
  
  public final String POST_METHOD = "POST";
  
  public final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
  
  public String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
  
  public String send_message_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
  
  public int getRandom4QrCode(String usercode) {
    String curr = String.valueOf(System.currentTimeMillis());
    String random = curr.substring(curr.length() - 9, curr.length());
    int randomInt = Integer.parseInt(random);
    if (this.QR_CODE_MAP.containsKey(String.valueOf(randomInt)))
      getRandom4QrCode(usercode); 
    this.QR_CODE_MAP.put(String.valueOf(randomInt), usercode);
    return randomInt;
  }
  
  public String getUserCodeFromQrCodeMap(String random) throws Exception {
    String userCode = null;
    if (!this.QR_CODE_MAP.containsKey(random))
      throw new Exception("根据随机数找不到对应的患者编号，随机数为：" + random); 
    userCode = this.QR_CODE_MAP.get(random);
    if (YZUtility.isNullorEmpty(userCode))
      throw new Exception("根据随机数找到的患者编号为空值，随机数为：" + random); 
    YZUtility.deleteKeyOfMap(this.QR_CODE_MAP, random);
    return userCode;
  }
  
  public static String getAccessToken() throws Exception {
    String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
    String sendMsgUrl = YZSysProps.getString("GET_TOKEN_URL");
    if (YZUtility.isNullorEmpty(accountid))
      throw new Exception("accountid不能为空"); 
    sendMsgUrl = String.valueOf(sendMsgUrl) + "&wxaccountid=" + accountid;
    JSONObject jsonObject = httpRequest(sendMsgUrl, "POST", null);
    if (jsonObject == null)
      throw new Exception("获取accessToken！URL地址不正确。"); 
    return jsonObject.getString("accessToken");
  }
  
  public JSONObject sendNewsMsg(JSONObject newsMsg, String usercode, String openid, String newsid, HttpServletRequest request) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
    String sendMsgUrl = YZSysProps.getString("SEND_NEWSMSG_URL");
    if (YZUtility.isNullorEmpty(accountid))
      throw new Exception("accountid不能为空"); 
    if (YZUtility.isNullorEmpty(newsid))
      throw new Exception("newsid不能为空"); 
    newsMsg.put("openid", openid);
    JSONObject jsonObject = httpRequestJSON(sendMsgUrl, newsMsg);
    if (jsonObject == null)
      throw new Exception("回复文本信息失败！URL地址不正确。"); 
    if (jsonObject.getInt("errcode") != 0)
      throw new Exception("回复文本信息失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg")); 
    WXReceivetext text = new WXReceivetext();
    text.setAccountid(accountid);
    text.setContent(newsMsg.toString());
    text.setUsercode(usercode);
    text.setFromusername(accountid);
    text.setTousername(openid);
    text.setCreatetime(YZUtility.getCurDateTimeStr());
    text.setMsgid(String.valueOf(System.currentTimeMillis()));
    text.setMsgtype("news");
    text.setSeqId(YZUtility.getUUID());
    text.setCreateuser(person.getSeqId());
    this.logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, text);
    return JSONObject.fromObject(text);
  }
  
  private String getImageUrl(String module, String attrId, String attrName, HttpServletRequest request) {
    attrId = attrId.replace(",", "");
    attrName = attrName.replace("*", "");
    String[] attrIdArray = attrId.split("_");
    String imageUrl = YZSysProps.getString("WEBSITE_URL");
    imageUrl = String.valueOf(imageUrl) + request.getContextPath() + "/upload/" + module + "/";
    imageUrl = String.valueOf(imageUrl) + attrIdArray[0] + "/" + attrIdArray[1] + "_" + attrName;
    return imageUrl;
  }
  
  public JSONObject getUserBaseInfo(String openid, HttpServletRequest request) throws Exception {
    String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
    String reqUrl = YZSysProps.getString("GET_BASEINFO_URL");
    reqUrl = String.valueOf(reqUrl) + "&wxaccountid=" + accountid + "&openid=" + openid;
    JSONObject jsonObject = httpRequest(reqUrl, "POST", null);
    if (jsonObject == null)
      throw new Exception("获取粉丝基本消息！URL地址不正确。"); 
    if (jsonObject.containsKey("errcode"))
      throw new Exception("获取粉丝基本消息失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg")); 
    WXFans fans = this.fansLogic.getFanByOpenid(openid, request);
    if (fans != null) {
      String sexname = "";
      String sex = jsonObject.getString("sex");
      if ("0".equals(sex))
        sexname = "未知"; 
      if ("1".equals(sex))
        sexname = "男"; 
      if ("2".equals(sex))
        sexname = "女"; 
      fans.setSex(sexname);
      fans.setNickname(jsonObject.getString("nickname"));
      fans.setRemark(jsonObject.getString("remark"));
      fans.setCity(jsonObject.getString("city"));
      fans.setCountry(jsonObject.getString("country"));
      fans.setSubscribeTime(jsonObject.getString("subscribe_time"));
      fans.setSubscribe(jsonObject.getString("subscribe"));
      fans.setProvince(jsonObject.getString("province"));
      fans.setHeadimgurl(jsonObject.getString("headimgurl"));
      this.logic.updateSingleUUID(TableNameUtil.WX_FANS, fans);
    } 
    return jsonObject;
  }
  
  public JSONObject getMediaIdAndSendMsg(String openid, String attrId, String attrName, HttpServletRequest request) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
    String sendMsgUrl = YZSysProps.getString("GET_MEDIAID_URL");
    String imageUrl = getImageUrl("wechat", attrId, attrName, request);
    if (YZUtility.isNullorEmpty(accountid))
      throw new Exception("accountid不能为空"); 
    String msgtype = "image";
    String imageUrlCode = URLEncoder.encode(imageUrl, "utf-8");
    sendMsgUrl = String.valueOf(sendMsgUrl) + "&touser=" + openid + "&imageUrl=" + imageUrlCode + "&wxaccountid=" + accountid + "&msgtype=" + msgtype;
    JSONObject jsonObject = httpRequest(sendMsgUrl, "POST", null);
    if (jsonObject == null)
      throw new Exception("发送图片消息！URL地址不正确。"); 
    if (jsonObject.getInt("errcode") != 0)
      throw new Exception("发送图片消息失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg")); 
    KqdsUserdocument user = this.wxUserDocLogic.getBindUserDocByOpenId(openid, request);
    WXReceivetext text = new WXReceivetext();
    text.setAccountid(accountid);
    if (user != null)
      text.setUsercode(user.getUsercode()); 
    text.setFromusername(accountid);
    text.setTousername(openid);
    text.setCreatetime(YZUtility.getCurDateTimeStr());
    text.setMsgid(String.valueOf(System.currentTimeMillis()));
    text.setMsgtype(msgtype);
    text.setSeqId(YZUtility.getUUID());
    text.setCreateuser(person.getSeqId());
    text.setMediaid(jsonObject.getString("media_id"));
    text.setPicurl(imageUrl);
    this.logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, text);
    JSONObject json = JSONObject.fromObject(text);
    return json;
  }
  
  public void sendTextMsg(String sendMsgUrl, String usercode, String openid, String content, HttpServletRequest request) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
    sendMsgUrl = String.valueOf(sendMsgUrl) + "&touser=" + openid;
    JSONObject jsonObject = httpRequest(sendMsgUrl, "POST", null);
    if (jsonObject == null)
      throw new Exception("回复文本信息失败！URL地址不正确。"); 
    if (jsonObject.getInt("errcode") != 0)
      throw new Exception("回复文本信息失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg")); 
    WXReceivetext text = new WXReceivetext();
    text.setAccountid(accountid);
    text.setContent(content);
    text.setUsercode(usercode);
    text.setFromusername(accountid);
    text.setTousername(openid);
    text.setCreatetime(YZUtility.getCurDateTimeStr());
    text.setMsgid(String.valueOf(System.currentTimeMillis()));
    text.setMsgtype("text");
    text.setSeqId(YZUtility.getUUID());
    text.setCreateuser(person.getSeqId());
    this.logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, text);
  }
  
  public static JSONObject httpRequestSSL(String requestUrl, String requestMethod, String outputStr) throws Exception {
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    TrustManager[] tm = { (TrustManager)new MyX509TrustManager() };
    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
    sslContext.init(null, tm, new SecureRandom());
    SSLSocketFactory ssf = sslContext.getSocketFactory();
    URL url = new URL(requestUrl);
    HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();
    httpUrlConn.setSSLSocketFactory(ssf);
    httpUrlConn.setDoOutput(true);
    httpUrlConn.setDoInput(true);
    httpUrlConn.setUseCaches(false);
    httpUrlConn.setRequestMethod(requestMethod);
    if ("GET".equalsIgnoreCase(requestMethod))
      httpUrlConn.connect(); 
    if (outputStr != null) {
      OutputStream outputStream = httpUrlConn.getOutputStream();
      outputStream.write(outputStr.getBytes("UTF-8"));
      outputStream.close();
    } 
    InputStream inputStream = httpUrlConn.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String str = null;
    while ((str = bufferedReader.readLine()) != null)
      buffer.append(str); 
    bufferedReader.close();
    inputStreamReader.close();
    inputStream.close();
    inputStream = null;
    httpUrlConn.disconnect();
    jsonObject = JSONObject.fromObject(buffer.toString());
    return jsonObject;
  }
  
  public static JSONObject httpRequestJSON(String requestUrl, JSONObject jsonParam) throws Exception {
    String outputStr = null;
    if (jsonParam != null)
      outputStr = jsonParam.toString(); 
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    URL url = new URL(requestUrl);
    HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
    httpUrlConn.setDoOutput(true);
    httpUrlConn.setDoInput(true);
    httpUrlConn.setUseCaches(false);
    httpUrlConn.setRequestMethod("POST");
    httpUrlConn.setRequestProperty("Content-Type", "application/json");
    if (outputStr != null) {
      OutputStream outputStream = httpUrlConn.getOutputStream();
      outputStream.write(outputStr.getBytes("UTF-8"));
      outputStream.close();
    } 
    InputStream inputStream = httpUrlConn.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String str = null;
    while ((str = bufferedReader.readLine()) != null)
      buffer.append(str); 
    bufferedReader.close();
    inputStreamReader.close();
    inputStream.close();
    inputStream = null;
    httpUrlConn.disconnect();
    jsonObject = JSONObject.fromObject(buffer.toString());
    return jsonObject;
  }
  
  public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    URL url = new URL(requestUrl);
    HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
    httpUrlConn.setDoOutput(true);
    httpUrlConn.setDoInput(true);
    httpUrlConn.setUseCaches(false);
    httpUrlConn.setRequestMethod(requestMethod);
    if ("GET".equalsIgnoreCase(requestMethod))
      httpUrlConn.connect(); 
    if (outputStr != null) {
      OutputStream outputStream = httpUrlConn.getOutputStream();
      outputStream.write(outputStr.getBytes("UTF-8"));
      outputStream.close();
    } 
    InputStream inputStream = httpUrlConn.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String str = null;
    while ((str = bufferedReader.readLine()) != null)
      buffer.append(str); 
    bufferedReader.close();
    inputStreamReader.close();
    inputStream.close();
    inputStream = null;
    httpUrlConn.disconnect();
    jsonObject = JSONObject.fromObject(buffer.toString());
    return jsonObject;
  }
  
  public static boolean isQqFace(String content) {
    boolean result = false;
    String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
    Pattern p = Pattern.compile(qqfaceRegex);
    Matcher m = p.matcher(content);
    if (m.matches())
      result = true; 
    return result;
  }
}
