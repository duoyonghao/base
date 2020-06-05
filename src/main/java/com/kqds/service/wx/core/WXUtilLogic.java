package com.kqds.service.wx.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import net.sf.json.JSONObject;

@Service
public class WXUtilLogic {
	@Autowired
	private WXReceiveTextLogic logic;
	@Autowired
	private WXUserDocLogic wxUserDocLogic;
	@Autowired
	private WXFansLogic fansLogic;

	/**
	 * 全局map，用于存放随机数和患者编号，用于生成临时二维码时，用户绑定成功后，找到对应的患者编号
	 */
	public Map<String, String> QR_CODE_MAP = new HashMap<String, String>();

	public final String GET_METHOD = "GET";

	public final String POST_METHOD = "POST";

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 菜单创建（POST） 限100（次/天）
	public String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 客服接口地址
	public String send_message_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	/**
	 * 用于存放随机数和患者编号，用于生成临时二维码时，用户绑定成功后，找到对应的患者编号
	 * 
	 * @param usercode
	 * @return
	 */
	public int getRandom4QrCode(String usercode) {

		String curr = String.valueOf(System.currentTimeMillis());

		String random = curr.substring(curr.length() - 9, curr.length()); // 取后9位

		int randomInt = Integer.parseInt(random);

		if (QR_CODE_MAP.containsKey(String.valueOf(randomInt))) {
			getRandom4QrCode(usercode);
		}

		QR_CODE_MAP.put(String.valueOf(randomInt), usercode);

		return randomInt;
	}

	public String getUserCodeFromQrCodeMap(String random) throws Exception {
		String userCode = null;
		if (!QR_CODE_MAP.containsKey(random)) {
			throw new Exception("根据随机数找不到对应的患者编号，随机数为：" + random);
		}

		userCode = QR_CODE_MAP.get(random);

		if (YZUtility.isNullorEmpty(userCode)) {
			throw new Exception("根据随机数找到的患者编号为空值，随机数为：" + random);
		}

		YZUtility.deleteKeyOfMap(QR_CODE_MAP, random);

		return userCode;
	}

	/**
	 * 获取getAccessToken
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getAccessToken() throws Exception {
		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
		String sendMsgUrl = YZSysProps.getString("GET_TOKEN_URL");

		if (YZUtility.isNullorEmpty(accountid)) {
			throw new Exception("accountid不能为空");
		}
		sendMsgUrl += "&wxaccountid=" + accountid;
		JSONObject jsonObject = WXUtilLogic.httpRequest(sendMsgUrl, "POST", null);
		if (jsonObject == null) {
			throw new Exception("获取accessToken！URL地址不正确。");
		}

		return jsonObject.getString("accessToken");
	}

	/**
	 * 发送文本消息
	 * 
	 * @param usercode
	 * @param openid
	 * @param content
	 * @param request
	 * @throws Exception
	 */
	public JSONObject sendNewsMsg(JSONObject newsMsg, String usercode, String openid, String newsid, HttpServletRequest request) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);

		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
		String sendMsgUrl = YZSysProps.getString("SEND_NEWSMSG_URL");

		if (YZUtility.isNullorEmpty(accountid)) {
			throw new Exception("accountid不能为空");
		}

		if (YZUtility.isNullorEmpty(newsid)) {
			throw new Exception("newsid不能为空");
		}

		newsMsg.put("openid", openid);

		JSONObject jsonObject = WXUtilLogic.httpRequestJSON(sendMsgUrl, newsMsg);
		if (jsonObject == null) {
			throw new Exception("回复文本信息失败！URL地址不正确。");
		}
		if (0 != jsonObject.getInt("errcode")) {
			throw new Exception("回复文本信息失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg"));
		}
		/*************** 微信回复文本信息 ***********************/

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

		logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, text);

		return JSONObject.fromObject(text);
	}

	/**
	 * 获取图片网络地址
	 * 
	 * @param attrId
	 * @param attrName
	 * @return
	 */
	private String getImageUrl(String module, String attrId, String attrName, HttpServletRequest request) {
		attrId = attrId.replace(",", "");
		attrName = attrName.replace("*", "");
		String[] attrIdArray = attrId.split("_");
		String imageUrl = YZSysProps.getString("WEBSITE_URL");
		imageUrl += request.getContextPath() + "/upload/" + module + "/";
		imageUrl += attrIdArray[0] + "/" + attrIdArray[1] + "_" + attrName;
		return imageUrl;
	}

	public JSONObject getUserBaseInfo(String openid, HttpServletRequest request) throws Exception {
		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
		String reqUrl = YZSysProps.getString("GET_BASEINFO_URL");
		reqUrl += "&wxaccountid=" + accountid + "&openid=" + openid;
		JSONObject jsonObject = WXUtilLogic.httpRequest(reqUrl, "POST", null);
		if (jsonObject == null) {
			throw new Exception("获取粉丝基本消息！URL地址不正确。");
		}
		if (jsonObject.containsKey("errcode")) {
			throw new Exception("获取粉丝基本消息失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg"));
		}

		// {
		// "sex": 1,
		// "nickname": "彩虹",
		// "remark": "",
		// "city": "南京",
		// "country": "中国",
		// "subscribe_time": 1508464375,
		// "tagid_list": [ ], /** 暂时不存 **/
		// "subscribe": 1,
		// "province": "江苏",
		// "openid": "oSbLwv8Za3kzalxRboPcY_ls8LZo",
		// "language": "zh_CN", /** 暂时不存 **/
		// "groupid": 0, /** 暂时不存 **/
		// "headimgurl":
		// "http://wx.qlogo.cn/mmopen/QVnu8t7AEDn2DI2yrImia75Yxkaylib7PMEpcmMObVtV4KySLhCETn7ib5UsUunBNjxPTzK2ff6g7BK2y7VibnF5GmaqApfI1eKib/0"
		// }

		WXFans fans = fansLogic.getFanByOpenid(openid, request);
		if (fans != null) {
			// 更新操作
			String sexname = "";
			String sex = jsonObject.getString("sex");
			if ("0".equals(sex)) {
				sexname = "未知";
			}
			if ("1".equals(sex)) {
				sexname = "男";
			}
			if ("2".equals(sex)) {
				sexname = "女";
			}

			fans.setSex(sexname);
			fans.setNickname(jsonObject.getString("nickname"));
			fans.setRemark(jsonObject.getString("remark"));
			fans.setCity(jsonObject.getString("city"));
			fans.setCountry(jsonObject.getString("country"));
			fans.setSubscribeTime(jsonObject.getString("subscribe_time"));
			fans.setSubscribe(jsonObject.getString("subscribe"));
			fans.setProvince(jsonObject.getString("province"));
			fans.setHeadimgurl(jsonObject.getString("headimgurl"));

			logic.updateSingleUUID(TableNameUtil.WX_FANS, fans);
		}

		return jsonObject;
	}

	/**
	 * 获取mediaid
	 * 
	 * @param attrId
	 *            1710_82d86abee2027475583feb7aa61df298,
	 * @param attrName
	 *            1.jpg*
	 * @param request
	 * @throws Exception
	 */
	public JSONObject getMediaIdAndSendMsg(String openid, String attrId, String attrName, HttpServletRequest request) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);

		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
		String sendMsgUrl = YZSysProps.getString("GET_MEDIAID_URL");
		String imageUrl = getImageUrl("wechat", attrId, attrName, request);

		if (YZUtility.isNullorEmpty(accountid)) {
			throw new Exception("accountid不能为空");
		}

		String msgtype = "image";
		String imageUrlCode = URLEncoder.encode(imageUrl, "utf-8");
		sendMsgUrl += "&touser=" + openid + "&imageUrl=" + imageUrlCode + "&wxaccountid=" + accountid + "&msgtype=" + msgtype;
		JSONObject jsonObject = WXUtilLogic.httpRequest(sendMsgUrl, "POST", null);
		if (jsonObject == null) {
			throw new Exception("发送图片消息！URL地址不正确。");
		}
		if (0 != jsonObject.getInt("errcode")) {
			throw new Exception("发送图片消息失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg"));
		}
		/*************** 微信回复文本信息 ***********************/
		KqdsUserdocument user = wxUserDocLogic.getBindUserDocByOpenId(openid, request);

		WXReceivetext text = new WXReceivetext();
		text.setAccountid(accountid);
		if (user != null) {
			text.setUsercode(user.getUsercode());
		}
		text.setFromusername(accountid);
		text.setTousername(openid);
		text.setCreatetime(YZUtility.getCurDateTimeStr());
		text.setMsgid(String.valueOf(System.currentTimeMillis()));
		text.setMsgtype(msgtype);
		text.setSeqId(YZUtility.getUUID());
		text.setCreateuser(person.getSeqId());
		text.setMediaid(jsonObject.getString("media_id"));
		text.setPicurl(imageUrl);

		logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, text);

		JSONObject json = JSONObject.fromObject(text);
		return json;
	}

	/**
	 * 发送文本消息
	 * 
	 * @param usercode
	 * @param openid
	 * @param content
	 * @param request
	 * @throws Exception
	 */
	public void sendTextMsg(String sendMsgUrl, String usercode, String openid, String content, HttpServletRequest request) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
		sendMsgUrl += "&touser=" + openid;

		JSONObject jsonObject = WXUtilLogic.httpRequest(sendMsgUrl, "POST", null);
		if (jsonObject == null) {
			throw new Exception("回复文本信息失败！URL地址不正确。");
		}
		if (0 != jsonObject.getInt("errcode")) {
			throw new Exception("回复文本信息失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg"));
		}
		/*************** 微信回复文本信息 ***********************/

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
		logic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, text);
	}

	/**
	 * 发送图片
	 * 
	 * @param usercode
	 * @param openid
	 * @param mediaid
	 * @param picurl
	 * @param request
	 * @throws Exception
	 */
	// public static void sendImageMsg(String usercode, String openid, String
	// mediaid, String picurl, HttpServletRequest request) throws Exception {
	// String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
	// String sendMsgUrl = YZSysProps.getString("SEND_MSG_URL");
	//
	// if (YZUtility.isNullorEmpty(accountid)) {
	// throw new Exception("accountid不能为空");
	// }
	//
	// String msgtype = "image";
	//
	// sendMsgUrl += "&touser=" + openid + "&media_id=" + mediaid +
	// "&wxaccountid=" + accountid + "&msgtype=" + msgtype;
	//
	// JSONObject jsonObject = WXUtil.httpRequest(sendMsgUrl, "POST", null);
	// if (jsonObject == null) {
	// throw new Exception("回复文本信息失败！URL地址不正确。");
	// }
	// if (0 != jsonObject.getInt("errcode")) {
	// throw new Exception("回复文本信息失败！错误码为：" + jsonObject.getInt("errcode") +
	// "错误信息为：" + jsonObject.getString("errmsg"));
	// }
	// /*************** 微信回复文本信息 ***********************/
	//
	// WXReceiveText text = new WXReceiveText();
	// text.setAccountid(accountid);
	// text.setUsercode(usercode);
	// text.setFromusername(accountid);
	// text.setTousername(openid);
	// text.setCreatetime(YZUtility.getCurDateTimeStr());
	// text.setMsgid(String.valueOf(System.currentTimeMillis()));
	// text.setMsgtype("text");
	// text.setSeqId(YZUtility.getUUID());
	// text.setMediaid(mediaid);
	// text.setPicurl(picurl);
	//
	// logic.saveSingleUUID(text, TableNameUtil.WX_RECEIVETEXT, request);
	// }

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 */
	public static JSONObject httpRequestSSL(String requestUrl, String requestMethod, String outputStr) throws Exception {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		URL url = new URL(requestUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
		httpUrlConn.setSSLSocketFactory(ssf);

		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod(requestMethod);

		if ("GET".equalsIgnoreCase(requestMethod))
			httpUrlConn.connect();

		// 当有数据需要提交时
		if (null != outputStr) {
			OutputStream outputStream = httpUrlConn.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
		}

		// 将返回的输入流转换成字符串
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		inputStream = null;
		httpUrlConn.disconnect();
		jsonObject = JSONObject.fromObject(buffer.toString());
		// jsonObject = JSONObject.fromObject(buffer.toString());
		return jsonObject;
	}

	public static JSONObject httpRequestJSON(String requestUrl, JSONObject jsonParam) throws Exception {
		String outputStr = null;
		if (jsonParam != null) {
			outputStr = jsonParam.toString();
		}

		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();

		URL url = new URL(requestUrl);
		HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod("POST");
		httpUrlConn.setRequestProperty("Content-Type", "application/json");

		// 当有数据需要提交时
		if (null != outputStr) {
			OutputStream outputStream = httpUrlConn.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
		}

		// 将返回的输入流转换成字符串
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		inputStream = null;
		httpUrlConn.disconnect();
		jsonObject = JSONObject.fromObject(buffer.toString());
		// jsonObject = JSONObject.fromObject(buffer.toString());
		return jsonObject;
	}

	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();

		URL url = new URL(requestUrl);
		HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod(requestMethod);

		if ("GET".equalsIgnoreCase(requestMethod))
			httpUrlConn.connect();

		// 当有数据需要提交时
		if (null != outputStr) {
			OutputStream outputStream = httpUrlConn.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
		}

		// 将返回的输入流转换成字符串
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		inputStream = null;
		httpUrlConn.disconnect();
		jsonObject = JSONObject.fromObject(buffer.toString());
		// jsonObject = JSONObject.fromObject(buffer.toString());
		return jsonObject;
	}

	/**
	 * 判断是否是QQ表情
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isQqFace(String content) {
		boolean result = false;

		// 判断QQ表情的正则表达式
		String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
		Pattern p = Pattern.compile(qqfaceRegex);
		Matcher m = p.matcher(content);
		if (m.matches()) {
			result = true;
		}
		return result;
	}

}
