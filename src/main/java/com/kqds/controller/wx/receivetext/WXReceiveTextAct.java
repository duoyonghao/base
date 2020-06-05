package com.kqds.controller.wx.receivetext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

import net.sf.json.JSONObject;

@Controller
@RequestMapping("WXReceiveTextAct")
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

	/**
	 * 查询未读消息数量
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectWdMsgCount.act")
	public String selectWdMsgCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int wdcount = logic.getNotReadMsgCountByOpenid(null, request);
			JSONObject retrunObj = new JSONObject();
			retrunObj.put("wdcount", wdcount);
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
		Pattern patternForTag = Pattern.compile(regxpForTag, Pattern.CASE_INSENSITIVE);
		Pattern pattern4Class = Pattern.compile(regxp4Class, Pattern.CASE_INSENSITIVE);
		Pattern pattern4Title = Pattern.compile(regxp4Title, Pattern.CASE_INSENSITIVE);
		Pattern pattern4Src = Pattern.compile(regxp4Src, Pattern.CASE_INSENSITIVE);
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

			if (matcher4Class.find()) {
				classN = matcher4Class.group(1);
			}
			if (matcher4Title.find()) {
				title = matcher4Title.group(1);
			}
			if (matcher4Src.find()) {
				src = matcher4Src.group(1);
			}

			StringBuffer imgBf1 = new StringBuffer("<img class=\"" + classN + "\" title=\"" + title + "\" src=\"" + src + "\"></img>");
			StringBuffer imgBf2 = new StringBuffer("<img class=\"" + classN + "\" title=\"" + title + "\" src=\"" + src + "\">");

			String titleBQ = "/" + title;
			text = text.replaceAll(imgBf1.toString(), titleBQ);
			text = text.replaceAll(imgBf2.toString(), titleBQ);

			result = matcherForTag.find();
		}

		// String img = "";
		// Pattern p_image;
		// Matcher m_image;
		// List<String> pics = new ArrayList<String>();
		// String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		// p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		// m_image = p_image.matcher(text);
		// while (m_image.find()) {
		// img = img + "," + m_image.group();
		// Matcher m =
		// Pattern.compile("title\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
		// while (m.find()) {
		// pics.add(m.group(1));
		//
		// System.out.println(m.group(1));
		// }
		// }

		// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// dbf.setValidating(false);
		// DocumentBuilder db = dbf.newDocumentBuilder();
		// text = new String(text.getBytes(), "utf-8");
		// InputStream is = new ByteArrayInputStream(text.getBytes());
		// document = db.parse(is);
		//
		// XPathFactory factory = XPathFactory.newInstance();
		// xpath = factory.newXPath();
		//
		// Node node = (Node) xpath.evaluate("/A", document,
		// XPathConstants.NODE);

	}

	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String openid = request.getParameter("openid");
			List<JSONObject> list = logic.selectList(seqId, openid);
			JSONObject retrunObj = new JSONObject();
			retrunObj.put("list", list);
			YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			JSONObject data = logic.selectWithPage(TableNameUtil.WX_RECEIVETEXT, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 单条发送
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendNews.act")
	public String sendNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String openid = request.getParameter("openid");
			String newsid = request.getParameter("newsid");
			String usercode = request.getParameter("usercode");

			if (YZUtility.isNullorEmpty(openid)) {
				throw new Exception("openid不能为空");
			}
			if (YZUtility.isNullorEmpty(newsid)) {
				throw new Exception("newsid不能为空");
			}

			JSONObject newsMsg = newsLogic.getNewsObject4Send(newsid, request);

			/*************** 微信回复文本信息 ***********************/
			JSONObject json = wxUtilLogic.sendNewsMsg(newsMsg, usercode, openid, newsid, request);
			YZUtility.DEAL_SUCCESS(json, "消息发送成功！", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 单条发送
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendMsg.act")
	public String sendMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String openid = request.getParameter("openid");
			String content = request.getParameter("content");
			String usercode = request.getParameter("usercode");

			if (YZUtility.isNullorEmpty(openid)) {
				throw new Exception("openid不能为空");
			}
			if (YZUtility.isNullorEmpty(content)) {
				throw new Exception("content不能为空");
			}

			WXFans fans = fansLogic.getFanByOpenid(openid, request);

			if (0 != fans.getCarestatus()) {
				throw new Exception("该患者已取消微信绑定");
			}

			String sendMsgUrl = logic.getUrl4Send(content);

			/*************** 微信回复文本信息 ***********************/
			wxUtilLogic.sendTextMsg(sendMsgUrl, usercode, openid, content, request);

			YZUtility.DEAL_SUCCESS(null, "消息发送成功！", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 批量发送文字消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendMsgBath.act")
	public String sendMsgBath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String openIds = request.getParameter("openIds");
			String userCodes = request.getParameter("userCodes");
			String sendContent = request.getParameter("sendContent");

			if (YZUtility.isNullorEmpty(openIds)) {
				throw new Exception("openIds不能为空");
			}
			if (YZUtility.isNullorEmpty(userCodes)) {
				throw new Exception("userCodes不能为空");
			}
			if (YZUtility.isNullorEmpty(sendContent)) {
				throw new Exception("sendContent不能为空");
			}

			// sendContent += "【群发消息】";
			String sendMsgUrl = logic.getUrl4Send(sendContent);
			/****************** 业务处理 ******************************/
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
				if (YZUtility.isNullorEmpty(openIdArray[i])) {
					continue;
				}
				/*************** 微信回复文本信息 ***********************/
				try {
					wxUtilLogic.sendTextMsg(sendMsgUrl, userCodeArray[i], openIdArray[i], sendContent, request);
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
				rtmsg += "成功发送：" + successcount + "条，<br>";
				rtmsg += "失败发送：" + failcount + "条，<br>";
				rtmsg += "失败原因：" + errMsg;
			} else {
				rtmsg = "发送成功！";
			}
			batchmsg.setOkcount(successcount);
			batchmsg.setErrcount(failcount);
			batchmsg.setOkopenids(okOpenidBf.toString());
			batchmsg.setOkusercodes(okUsercodeBf.toString());
			batchmsg.setErropenids(errOpenidBf.toString());
			batchmsg.setErrusercodes(errUsercodeBf.toString());
			batchmsg.setSeqId(YZUtility.getUUID());
			// 记录入库
			logic.saveSingleUUID(TableNameUtil.WX_BATCHMSG, batchmsg);
			YZUtility.DEAL_SUCCESS(null, rtmsg, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 批量发送图文
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendNewsBath.act")
	public String sendNewsBath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String openIds = request.getParameter("openIds");
			String userCodes = request.getParameter("userCodes");

			if (YZUtility.isNullorEmpty(openIds)) {
				throw new Exception("openIds不能为空");
			}
			if (YZUtility.isNullorEmpty(userCodes)) {
				throw new Exception("userCodes不能为空");
			}

			String newsid = request.getParameter("newsid");

			if (YZUtility.isNullorEmpty(newsid)) {
				throw new Exception("newsid不能为空");
			}

			JSONObject newsMsg = newsLogic.getNewsObject4Send(newsid, request);

			/*************** 业务处理 ***********************/
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
				if (YZUtility.isNullorEmpty(openIdArray[i])) {
					continue;
				}
				/*************** 微信回复文本信息 ***********************/
				try {
					wxUtilLogic.sendNewsMsg(newsMsg, userCodeArray[i], openIdArray[i], newsid, request);
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
				rtmsg += "成功发送：" + successcount + "条，<br>";
				rtmsg += "失败发送：" + failcount + "条，<br>";
				rtmsg += "失败原因：" + errMsg;
			} else {
				rtmsg = "发送成功！";
			}

			batchmsg.setOkcount(successcount);
			batchmsg.setErrcount(failcount);
			batchmsg.setOkopenids(okOpenidBf.toString());
			batchmsg.setOkusercodes(okUsercodeBf.toString());
			batchmsg.setErropenids(errOpenidBf.toString());
			batchmsg.setErrusercodes(errUsercodeBf.toString());
			batchmsg.setSeqId(YZUtility.getUUID());
			// 记录入库
			logic.saveSingleUUID(TableNameUtil.WX_BATCHMSG, batchmsg);
			YZUtility.DEAL_SUCCESS(null, rtmsg, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 批量发送图文
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendTemplateBath.act")
	public String sendTemplateBath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			WXTemplateitem dp = new WXTemplateitem();
			BeanUtils.populate(dp, request.getParameterMap());

			String openIds = request.getParameter("openIds");

			if (YZUtility.isNullorEmpty(openIds)) {
				throw new Exception("openIds不能为空");
			}

			String templateId = request.getParameter("templateId");

			if (YZUtility.isNullorEmpty(templateId)) {
				throw new Exception("templateId不能为空");
			}

			if (YZUtility.isNullorEmpty(dp.getFirst())) {
				throw new Exception("first不能为空");
			}

			JSONObject postParam = itemLogic.getTemplateItem4Send(dp, request);
			/*************** 业务处理 ***********************/
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
				if (YZUtility.isNullorEmpty(openIdArray[i])) {
					continue;
				}
				/*************** 微信回复文本信息 ***********************/
				try {
					wxUtil4TemplateMsgLogic.template_send(postParam, openIdArray[i], request);
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
				rtmsg += "成功发送：" + successcount + "条，<br>";
				rtmsg += "失败发送：" + failcount + "条，<br>";
				rtmsg += "失败原因：" + errMsg;
			} else {
				rtmsg = "发送成功！";
			}
			batchmsg.setOkcount(successcount);
			batchmsg.setErrcount(failcount);
			batchmsg.setOkopenids(okOpenidBf.toString());
			batchmsg.setErropenids(errOpenidBf.toString());
			batchmsg.setSeqId(YZUtility.getUUID());
			// 记录入库
			logic.saveSingleUUID(TableNameUtil.WX_BATCHMSG, batchmsg);

			YZUtility.DEAL_SUCCESS(null, rtmsg, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/getMediaIdAndSendMsg.act")
	public String getMediaIdAndSendMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String openid = request.getParameter("openid");
			String attrId = request.getParameter("attrId");
			String attrName = request.getParameter("attrName");

			if (YZUtility.isNullorEmpty(openid)) {
				throw new Exception("openid不能为空");
			}
			if (YZUtility.isNullorEmpty(attrId)) {
				throw new Exception("attrId不能为空");
			}
			if (YZUtility.isNullorEmpty(attrName)) {
				throw new Exception("attrName不能为空");
			}

			/*************** 微信回复文本信息 ***********************/
			JSONObject msgObj = wxUtilLogic.getMediaIdAndSendMsg(openid, attrId, attrName, request);
			YZUtility.DEAL_SUCCESS(msgObj, "消息发送成功！", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 取消预约
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatus.act")
	public String updateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String openid = request.getParameter("openid");
			String seqId = request.getParameter("seqId");
			WXReceivetext order = (WXReceivetext) logic.loadObjSingleUUID(TableNameUtil.WX_RECEIVETEXT, seqId);
			if (order == null) {
				throw new Exception("记录不存在！");
			}
			order.setIspush(1); // 0未推送 1已推送
			logic.updateSingleUUID(TableNameUtil.WX_RECEIVETEXT, order);
			/** 更改未读消息数 **/
			wxUtil4ChatLogic.updateChatUserListMsg(openid, null, null, request);
			/** 更新总的未读数量 **/
			wxUtil4ChatLogic.notReadCountUpate(request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/*
	 * public String replayText(HttpServletRequest request, HttpServletResponse
	 * response) throws Exception {
	 * response.setContentType("text/json;charset=UTF-8"); try { YZRequestDbConn
	 * requestDbConn = (YZRequestDbConn)
	 * request.getAttribute(YZBeanKeys.REQUEST_DB_CONN_MGR); dbConn =
	 * logic.getConnFromReq(request); String seqId = request.getParameter("seqId");
	 * String fromusername = request.getParameter("fromusername"); String rescontent
	 * = request.getParameter("rescontent");
	 * 
	 * if (YZUtility.isNullorEmpty(seqId)) { throw new Exception("seqId不能为空"); } if
	 * (YZUtility.isNullorEmpty(fromusername)) { throw new
	 * Exception("fromusername不能为空"); } if (YZUtility.isNullorEmpty(rescontent)) {
	 * throw new Exception("rescontent不能为空"); } WXReceiveText text = (WXReceiveText)
	 * logic.loadObjSingleUUID(WXReceiveText.class, seqId,
	 * TableNameUtil.WX_RECEIVETEXT); if (text == null) { throw new
	 * Exception("消息记录不存在"); }
	 *//*************** 微信回复文本信息 ***********************/
	/*
	 * String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID"); WXAccount
	 * account = accountLogic.getWXAccountByAccountId(accountid); if (account ==
	 * null) { throw new Exception("公众账号在数据库中不存在，accountId：" + accountid); }
	 * 
	 * JSONObject textcontent = new JSONObject(); textcontent.put("content",
	 * rescontent); JSONObject textObj = new JSONObject(); textObj.put("touser",
	 * fromusername); textObj.put("msgtype", "text"); textObj.put("text",
	 * textcontent);
	 * 
	 * String accessToken = wxLogic.getAccessToken(account.getSeqId(), request);
	 * String url = WeixinUtil.send_message_url.replace("ACCESS_TOKEN",
	 * accessToken); JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST",
	 * textObj.toString()); if (jsonObject == null) { throw new
	 * Exception("回复文本信息失败！URL地址不正确。"); } if (0 != jsonObject.getInt("errcode")) {
	 * throw new Exception("回复文本信息失败！错误码为：" + jsonObject.getInt("errcode") +
	 * "错误信息为：" + jsonObject.getString("errmsg")); }
	 *//*************** 微信回复文本信息 ***********************/
	/*
	 * 
	 * text.setRescontent(rescontent); text.setResponse("1"); // 0 未回复 1 已回复
	 * logic.updateSingleUUID(text, TableNameUtil.WX_RECEIVETEXT, request);
	 * BcjlUtil.LogBcjl(BcjlUtil.MODIFY, WXLogUtil.WEIXIN_RECEIVETEXT, text,
	 * TableNameUtil.WX_RECEIVETEXT, request);
	 * 
	 * YZUtility.DEAL_SUCCESS(null, "回复成功！", response, logger); } catch (Exception
	 * ex) { YZUtility.DEAL_ERROR(null, true, ex, response, logger); } return null;
	 * }
	 */

}
