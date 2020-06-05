package com.kqds.service.wx.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXReceivetext;
import com.kqds.entity.wx.WXTemplateitem;
import com.kqds.entity.wx.WXTemplatemsg;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.wx.templateitem.WXTemplateItemLogic;
import com.kqds.service.wx.templatemsg.WXTemplateMsgLogic;
import com.kqds.service.wx.user.WXUserDocLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WXUtil4TemplateMsgLogic {

	public static final String GET_METHOD = "GET";

	public static final String POST_METHOD = "POST";

	public static final String COLOR = "#173177";
	@Autowired
	private WXUserDocLogic wxUserLogic;
	@Autowired
	private WXTemplateMsgLogic tempMsgLogic;
	@Autowired
	private WXTemplateItemLogic itemlogic;
	@Autowired
	private YZDictLogic dictLogic;

	public JSONObject orderSuccessNotify(String usercode, String openid, KqdsNetOrder netorder, HttpServletRequest request) throws Exception {
		JSONObject json = null;
		String askItemName = dictLogic.getDictNameBySeqId(netorder.getAskitem());

		String crete_doc_templateid = YZSysProps.getString("ORDER_SUCCESS_TEMPLATEID");
		if (!YZUtility.isNullorEmpty(crete_doc_templateid)) {
			WXTemplateitem dp = new WXTemplateitem();
			dp.setFirst("您已预约成功!");
			dp.setKeyword1(askItemName);
			dp.setKeyword2(netorder.getOrdertime());
			dp.setRemark("如有任何疑问，请及时与我们联系，谢谢！");

			WXTemplatemsg temp = tempMsgLogic.getWXTemplateMsgById(crete_doc_templateid, request);
			if (temp != null) {
				dp.setTemplateSeqid(temp.getSeqId());
				dp.setTemplateId(crete_doc_templateid);

				JSONObject postParam = itemlogic.getTemplateItem4Send(dp, request);
				json = template_send(postParam, openid, request);
			}
		}
		return json;
	}

	public JSONObject createDocSuccessNotify(KqdsUserdocument user, KqdsNetOrder netorder, HttpServletRequest request) throws Exception {
		JSONObject json = null;
		String crete_doc_templateid = YZSysProps.getString("CRETE_DOC_TEMPLATEID");
		if (!YZUtility.isNullorEmpty(crete_doc_templateid)) {
			WXTemplateitem dp = new WXTemplateitem();
			dp.setFirst("您已建档成功!");
			dp.setKeyword1(user.getUsername());
			dp.setKeyword2(user.getUsercode());
			dp.setRemark("如有任何疑问，请及时与我们联系，谢谢！");

			WXTemplatemsg temp = tempMsgLogic.getWXTemplateMsgById(crete_doc_templateid, request);
			if (temp != null) {
				dp.setTemplateSeqid(temp.getSeqId());
				dp.setTemplateId(crete_doc_templateid);

				JSONObject postParam = itemlogic.getTemplateItem4Send(dp, request);
				json = template_send(postParam, user.getOpenid(), request);
			}
		}
		return json;
	}

	/**
	 * 获取模板列表【当前公众号的】
	 * 
	 * @param usercode
	 * @param openid
	 * @param content
	 * @param request
	 * @throws Exception
	 */
	public JSONObject get_all_private_template(HttpServletRequest request) throws Exception {
		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
		String get_all_private_template_url = YZSysProps.getString("GET_ALL_PRIVATE_TEMPLATE_URL");

		get_all_private_template_url += "&wxaccountid=" + accountid;

		JSONObject jsonObject = WXUtilLogic.httpRequest(get_all_private_template_url, GET_METHOD, null);
		if (jsonObject == null) {
			throw new Exception("获取消息模板列表失败！URL地址不正确。");
		}
		/*************** 微信回复文本信息 ***********************/
		String template_list = jsonObject.getString("template_list");
		JSONArray templateArray = JSONArray.fromObject(template_list);

		for (Object object : templateArray) {
			JSONObject template = JSONObject.fromObject(object);

			String template_id = template.getString("template_id");
			WXTemplatemsg dbObj = (WXTemplatemsg) tempMsgLogic.getWXTemplateMsgById(template_id, request);

			if (dbObj == null) {
				WXTemplatemsg msg = new WXTemplatemsg();
				msg.setContent(template.getString("content"));
				msg.setCreatetime(YZUtility.getCurDateTimeStr());
				msg.setCreateuser(YZUtility.getCurrLoginPersonSeqId(request));
				msg.setDeputyIndustry(template.getString("deputy_industry"));
				msg.setExample(template.getString("example"));
				msg.setPrimaryIndustry(template.getString("primary_industry"));
				msg.setSeqId(YZUtility.getUUID());
				msg.setStatus(0);
				msg.setTemplateId(template.getString("template_id"));
				msg.setTitle(template.getString("title"));
				tempMsgLogic.saveSingleUUID(TableNameUtil.WX_TEMPLATEMSG, msg);
			} else {
				dbObj.setContent(template.getString("content"));
				dbObj.setCreatetime(YZUtility.getCurDateTimeStr());
				dbObj.setCreateuser(YZUtility.getCurrLoginPersonSeqId(request));
				dbObj.setDeputyIndustry(template.getString("deputy_industry"));
				dbObj.setExample(template.getString("example"));
				dbObj.setPrimaryIndustry(template.getString("primary_industry"));
				// dbObj.setSeqId(YZUtility.getUUID());
				// msg.setStatus(0);
				dbObj.setTemplateId(template.getString("template_id"));
				dbObj.setTitle(template.getString("title"));
				tempMsgLogic.updateSingleUUID(TableNameUtil.WX_TEMPLATEMSG, dbObj);
			}
		}

		/*
		 * { "template_list": [ { "content": "{{first.DATA}} 姓名：{{keyword1.DATA}}
		 * 时间：{{keyword2.DATA}} 计划内容：{{keyword3.DATA}}
		 * {{remark.DATA}}", "title": "诊疗计划提醒", "template_id":
		 * "bYkLc33SMAWVZpS0bhBtRsYPQh79Vg06WoUV90z5Lcg", "primary_industry": "医疗护理",
		 * "example": "您好，您的诊疗计划如下 姓名：张三 时间：2014年8月29日上午 计划内容：1、口服乙胺丁醇。2、口服异福酰胺胶囊。
		 * 请务必按计划操作，如有疑问请直接回复问题。我们会尽快给您答复", "deputy_industry": "医药医疗" } ] }
		 */

		return JSONObject.fromObject(jsonObject);
	}

	/**
	 * 获取模板列表【当前公众号的】
	 * 
	 * @param usercode
	 * @param openid
	 * @param content
	 * @param request
	 * @throws Exception
	 */
	public static JSONObject del_private_template(String template_id, HttpServletRequest request) throws Exception {
		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
		String del_private_template = YZSysProps.getString("DEL_PRIVATE_TEMPLATE_URL");

		del_private_template += "&wxaccountid=" + accountid;
		del_private_template += "&template_id=" + template_id;

		JSONObject jsonObject = WXUtilLogic.httpRequest(del_private_template, GET_METHOD, null);
		if (jsonObject == null) {
			throw new Exception("删除消息模板列表失败！URL地址不正确。");
		}
		if (0 != jsonObject.getInt("errcode")) {
			throw new Exception("删除消息模板列表失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg"));
		}
		/*************** 微信回复文本信息 ***********************/

		// System.out.println("test");

		return JSONObject.fromObject(jsonObject);
	}

	/**
	 * 发送模板消息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject template_send(JSONObject postParam, String openid, HttpServletRequest request) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);

		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
		String template_send_url = YZSysProps.getString("TEMPLATE_SEND_URL");

		JSONObject wxPostParam = JSONObject.fromObject(postParam.toString());
		/** 创建新的对象 **/
		wxPostParam.put("touser", openid);
		wxPostParam.remove("local_content");
		wxPostParam.remove("local_title");
		/** 移除多余参数 **/

		template_send_url += "&wxaccountid=" + accountid;

		JSONObject jsonObject = WXUtilLogic.httpRequestJSON(template_send_url, wxPostParam);
		if (jsonObject == null) {
			throw new Exception("发送模板消息失败！URL地址不正确。");
		}
		if (0 != jsonObject.getInt("errcode")) {
			throw new Exception("发送模板消息失败！错误码为：" + jsonObject.getInt("errcode") + "错误信息为：" + jsonObject.getString("errmsg"));
		}
		/*************** 微信回复信息 ***********************/

		// String str =
		// "{{first.DATA}}</br>诊前提示：{{keyword1.DATA}}</br>提示时间：{{keyword2.DATA}}</br>{{remark.DATA}}";

		String usercode = null;
		KqdsUserdocument userinfo = wxUserLogic.getBindUserDocByOpenId(openid, request);
		if (userinfo != null) {
			usercode = userinfo.getUsercode();
		}

		String content = postParam.getString("local_content");
		String title = postParam.getString("local_title");
		content = title + "：<br>" + content;

		WXReceivetext text = new WXReceivetext();
		text.setAccountid(accountid);
		text.setContent(content);
		text.setUsercode(usercode);
		text.setFromusername(accountid);
		text.setTousername(openid);
		text.setCreatetime(YZUtility.getCurDateTimeStr());
		text.setMsgid(String.valueOf(System.currentTimeMillis()));
		text.setMsgtype("template");
		text.setSeqId(YZUtility.getUUID());
		text.setCreateuser(person.getSeqId());

		tempMsgLogic.saveSingleUUID(TableNameUtil.WX_RECEIVETEXT, text);

		return JSONObject.fromObject(text);
	}

	public static void main(String[] args) {
		String str = "{{first.DATA}}</br>诊前提示：{{keyword1.DATA}}</br>提示时间：{{keyword2.DATA}}</br>{{remark.DATA}}";
		String regEx = "\\{\\{.*?\\.DATA\\}\\}";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String ss = matcher.group();
			ss = ss.replace(".DATA}}", "");
			ss = ss.replace("{{", "");
			// System.out.println("##" + ss);
		}
	}

}
