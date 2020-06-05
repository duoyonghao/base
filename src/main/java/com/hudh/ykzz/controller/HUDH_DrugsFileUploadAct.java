package com.hudh.ykzz.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.ykzz.service.impl.HUDH_DrugsFileUploadLogic;
import com.kqds.core.global.YZSysProps;
import com.kqds.util.core.file.YZFileUploadForm;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("HUDH_DrugsFileUploadAct")
public class HUDH_DrugsFileUploadAct {

	private Logger logger = LoggerFactory.getLogger(HUDH_DrugsFileUploadAct.class);

	@Autowired
	private HUDH_DrugsFileUploadLogic uploadLogic;

	/**
	 * 文件上传，具体调用页面包括： 导入： 1、加工项目导入 2、收费项目导入 3、商品导入 8、排班导入 上传： 4、影像资料上传 5、市场活动 6、异业记录
	 * 7 媒体资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadFile.act")
	public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try {
			YZFileUploadForm fileForm = new YZFileUploadForm();
			fileForm.parseUploadRequest(request);
			String module = request.getParameter("module");
			String imgtype = request.getParameter("imgtype");
//			String imgtype1 = fileForm.getParamMap().get("imgtype");
			// 根据所选门诊进行查询
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			String attrId = (fileForm.getParameter("attachmentId") == null) ? "" : fileForm.getParameter("attachmentId");
			String attrName = (fileForm.getParameter("attachmentName") == null) ? "" : fileForm.getParameter("attachmentName");
			result = uploadLogic.fileUploadAndImport(module, fileForm, request, imgtype, organization);

			Set<String> keys = result.keySet();
			for (String key : keys) {
				String value = (String) result.get(key);
				if (attrId != null && !"".equals(attrId)) {
					if (!(attrId.trim()).endsWith(",")) {
						attrId += ",";
					}
					if (!(attrName.trim()).endsWith("*")) {
						attrName += "*";
					}
				}
				attrId += key + ",";
				attrName += value + "*";
			}
			if (!result.containsKey("data")) { // 如果返回的json里没有data属性
				result.put("data", "上传成功");
			}
			result.put("attrId", attrId);
			result.put("attrName", attrName);

			if ("wechat_news".equals(module)) {
				/** 微信图文， 特殊处理 **/
				JSONObject sub = new JSONObject();
				sub.put("fileKey", "");
				sub.put("name", "");
				sub.put("delurl", "");
				sub.put("viewhref", "");
				sub.put("url", YZUtility.getImageUrlRelative(module, attrId, attrName));

				result.put("attributes", sub);
				result.put("obj", null);
				result.put("jsonStr", "");
				result.put("success", true);
				result.put("msg", "文件添加成功");
			}
			YZUtility.DEAL_SUCCESS(result, null, response, logger);
		} catch (SizeLimitExceededException ex) {
			YZUtility.DEAL_ERROR("文件上传失败：文件需要小于" + YZSysProps.getInt(YZSysProps.MAX_UPLOAD_FILE_SIZE) + "M", true, ex, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR("文件上传失败：" + ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * base64 文件上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String picUploadBASE64(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try {
			// 默认传入的参数带类型等参数：data:image/png;base64,
			String imgStr = request.getParameter("image");
			if (null != imgStr) {
				imgStr = imgStr.substring(imgStr.indexOf(",") + 1);
			}
			String module = request.getParameter("module");
			result = uploadLogic.fileUploadLogicForbase64(module, imgStr, request);
			YZUtility.DEAL_SUCCESS(result, null, response, logger);
		} catch (SizeLimitExceededException ex) {
			YZUtility.DEAL_ERROR("文件上传失败：文件需要小于" + YZSysProps.getInt(YZSysProps.MAX_UPLOAD_FILE_SIZE) + "M", true, ex, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR("文件上传失败：" + ex.getMessage(), true, ex, response, logger);
		}
		return null;

	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void downLoad(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String module = request.getParameter("module");
		String attrId = request.getParameter("attachmentId");
		String attrName = request.getParameter("attachmentName");
		if (YZUtility.isNullorEmpty(attrId)) {
			throw new Exception("文件不存在！");
		}
		int ym = attrId.indexOf("_");
		String hard = attrId.substring(0, ym);
		String fileName = attrId.substring(ym + 1, attrId.length() - 1) + "_" + attrName.substring(0, attrName.length() - 1);
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + module + File.separator + hard + File.separator + fileName;
		File f = new File(filePath);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((attrName.substring(0, attrName.length() - 1)).getBytes(), "iso-8859-1"));// 下载文件的名称
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(f));
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	/**
	 * 文件下载，根据网址
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downLoadByURL.act")
	public void downLoadByURL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = request.getParameter("url");

		url = encodeURLChinese(url);

		if (YZUtility.isNullorEmpty(url)) {
			throw new Exception("文件下载网址不能为空！");
		}

		int offset = url.lastIndexOf("_");
		String fileName = url.substring(offset + 1, url.length());

		URL urlObj = new URL(url);
		InputStream in = urlObj.openStream();

		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName.substring(0, fileName.length())).getBytes(), "iso-8859-1"));// 下载文件的名称
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(in);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			if (in != null)
				in.close();
		}
	}

	/** 下面几个方法从网上拷贝 ***/
	private String encodeURLChinese(String url) {
		try {
			if (!needEncoding(url)) {
				// 不需要编码
				return url;
			} else {
				// 需要编码

				String allowChars = ".!*'();:@&=+_\\-$,/?#\\[\\]{}|\\^~`<>%\"";
				// UTF-8 大写
				return encode(url, "UTF-8", allowChars, false);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断一个url是否需要编码，按需要增减过滤的字符
	 * 
	 * @param url
	 * @return
	 */
	private boolean needEncoding(String url) {
		// 不需要编码的正则表达式
		if (url.matches("^[0-9a-zA-Z.:/?=&%~`#()-+]+$")) {
			return false;
		}

		return true;
	}

	/**
	 * 对字符串中的特定字符进行编码
	 * 
	 * @param s
	 *            待编码的字符串
	 * @param enc
	 *            编码类型
	 * @param allowed
	 *            不需要编码的字符
	 * @param lowerCase
	 *            true:小写 false：大写
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 */
	private final String encode(String s, String enc, String allowed, boolean lowerCase) throws UnsupportedEncodingException {

		byte[] bytes = s.getBytes(enc);
		int count = bytes.length;

		/*
		 * From RFC 2396:
		 * 
		 * mark = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")" reserved = ";" |
		 * "/" | ":" | "?" | "@" | "&" | "=" | "+" | "$" | ","
		 */
		// final String allowed = "=,+;.'-@&/$_()!~*:"; // '?' is omitted
		char[] buf = new char[3 * count];
		int j = 0;

		for (int i = 0; i < count; i++) {
			if ((bytes[i] >= 0x61 && bytes[i] <= 0x7A) || // a..z
					(bytes[i] >= 0x41 && bytes[i] <= 0x5A) || // A..Z
					(bytes[i] >= 0x30 && bytes[i] <= 0x39) || // 0..9
					(allowed.indexOf(bytes[i]) >= 0)) {
				buf[j++] = (char) bytes[i];
			} else {
				buf[j++] = '%';
				if (lowerCase) {
					buf[j++] = Character.forDigit(0xF & (bytes[i] >>> 4), 16);
					buf[j++] = Character.forDigit(0xF & bytes[i], 16);
				} else {
					buf[j++] = lowerCaseToUpperCase(Character.forDigit(0xF & (bytes[i] >>> 4), 16));
					buf[j++] = lowerCaseToUpperCase(Character.forDigit(0xF & bytes[i], 16));
				}

			}
		}
		return new String(buf, 0, j);
	}

	private char lowerCaseToUpperCase(char ch) {
		if (ch >= 97 && ch <= 122) { // 如果是小写字母就转化成大写字母
			ch = (char) (ch - 32);
		}
		return ch;
	}
}
