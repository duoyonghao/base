package com.kqds.service.wx.receivetext;

import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.core.global.YZSysProps;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXReceivetext;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.wx.face.QQFaceUtil;

import net.sf.json.JSONObject;

@Service
public class WXReceiveTextLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	public String getUrl4Send(String content) throws Exception {
		String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");

		String sendMsgUrl = YZSysProps.getString("SEND_MSG_URL");

		if (YZUtility.isNullorEmpty(accountid)) {
			throw new Exception("accountid不能为空");
		}

		String msgtype = "text";
		// 处理换行
		content = content.replaceAll("<div><br></div>", "\n");
		content = content.replaceAll("</div><div>", "\n");
		content = content.replaceAll("<div>", "\n");
		content = content.replaceAll("</div>", "\n");
		// qq表情处理
		content = qqFaceDeal(content);

		String contentCode = URLEncoder.encode(content, "utf-8");
		sendMsgUrl += "&content=" + contentCode + "&wxaccountid=" + accountid + "&msgtype=" + msgtype;
		return sendMsgUrl;
	}

	/**
	 * QQ表情处理
	 * 
	 * @param content
	 * @return
	 */
	private static String qqFaceDeal(String text) throws Exception {
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

			String titleBQ = "[" + title + "]";
			text = text.replaceAll(imgBf1.toString(), titleBQ);
			text = text.replaceAll(imgBf2.toString(), titleBQ);

			result = matcherForTag.find();
		}

		// String text = "<wechat>" + content + "</wechat>";
		// InputStream is = new ByteArrayInputStream(text.getBytes("UTF-8"));
		// DocumentBuilderFactory domFactory =
		// DocumentBuilderFactory.newInstance();
		// domFactory.setNamespaceAware(true); // never forget this!
		// DocumentBuilder builder = domFactory.newDocumentBuilder();
		// Document doc = builder.parse(is);
		//
		// XPathFactory factory = XPathFactory.newInstance();
		// XPath xpath = factory.newXPath();
		// XPathExpression expr = xpath.compile("//img[contains(@class,'qqface
		// qqface')]");
		//
		// Object result = expr.evaluate(doc, XPathConstants.NODESET);
		// NodeList nodes = (NodeList) result;
		// for (int i = 0; i < nodes.getLength(); i++) {
		// Node node = nodes.item(i);
		// NamedNodeMap map = node.getAttributes();
		// String title = map.getNamedItem("title").getNodeValue();
		// String classN = map.getNamedItem("class").getNodeValue();
		// String src = map.getNamedItem("src").getNodeValue();
		//
		// StringBuffer imgBf1 = new StringBuffer("<img class=\"" + classN + "\"
		// title=\"" + title + "\" src=\"" + src + "\"></img>");
		// StringBuffer imgBf2 = new StringBuffer("<img class=\"" + classN + "\"
		// title=\"" + title + "\" src=\"" + src + "\">");
		// String titleBQ = "/" + title;
		// text = text.replaceAll(imgBf1.toString(), titleBQ);
		// text = text.replaceAll(imgBf2.toString(), titleBQ);
		// }
		//
		// text = text.replaceAll("<wechat>", "");
		// text = text.replaceAll("</wechat>", "");
		// System.out.println(text);

		return text;
	}

	/**
	 * 聊天页面，向上翻页
	 * 
	 * @param conn
	 * @param seqId
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(String seqId, String openid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);

		if (!YZUtility.isNullorEmpty(seqId)) {
			WXReceivetext text = (WXReceivetext) dao.loadObjSingleUUID(TableNameUtil.WX_RECEIVETEXT, seqId);
			if (text != null) {
				map.put("createtime", text.getCreatetime());
			}
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".selectList", map);
		for (JSONObject jsonObject : list) {
			String content = jsonObject.getString("content");
			/** 处理微信表情 **/
			content = QQFaceUtil.dealQQFace(content);
			jsonObject.put("content", content);
		}
		// Collections.reverse(list); //反转一个List
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 聊天相关
	 * 
	 * @param conn
	 * @param openid
	 * @return
	 * @throws SQLException
	 */
	public JSONObject getMsg4Talk(String openid) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.WX_RECEIVETEXT + ".getMsg4Talk", openid);
		return json;
	}

	/**
	 * 根据openid 获取未读的消息数
	 * 
	 * @param openid
	 * @param request
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getNotReadMsgCountByOpenid(String openid, HttpServletRequest request) throws SQLException, Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(openid)) {
			map.put("openid", openid);
		} else {
			String accountid = YZSysProps.getString("WEIXIN_ACCOUNTID");
			map.put("accountid", accountid);
		}

		int count = (int) dao.findForObject(TableNameUtil.WX_RECEIVETEXT + ".getNotReadMsgCountByOpenid", map);
		return count;
	}

}
