package com.kqds.util.base.util;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class HttpRequestUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class); // 日志记录

	public static void main(String[] args) {
		StringBuffer queryBf = new StringBuffer();

		JSONObject jsonParam = new JSONObject();
		jsonParam.put("action", "QueryTalklog");
		jsonParam.put("rpccallkey", "VocLogRpcKey");
		jsonParam.put("startdate", "2017-11-21 00:30:10"); // 非空
		jsonParam.put("enddate", "2017-11-21 18:30:10");
		jsonParam.put("localcode", "");
		jsonParam.put("channel", "");
		jsonParam.put("localname", "");
		jsonParam.put("localdepart", "");
		jsonParam.put("remotecode", "");
		jsonParam.put("direction", "");
		jsonParam.put("maxtalklong", "");
		jsonParam.put("mintalklong", "0");
		jsonParam.put("tags", "");
		jsonParam.put("pagestart", "0");
		jsonParam.put("pagelimit", "50");

		queryBf.append("action=" + jsonParam.getString("action"));
		queryBf.append("&rpccallkey=" + jsonParam.getString("rpccallkey"));
		queryBf.append("&startdate=" + jsonParam.getString("startdate"));
		queryBf.append("&enddate=" + jsonParam.getString("enddate"));
		queryBf.append("&localcode=" + jsonParam.getString("localcode"));
		queryBf.append("&channel=" + jsonParam.getString("channel"));
		queryBf.append("&localname=" + jsonParam.getString("localname"));
		queryBf.append("&localdepart=" + jsonParam.getString("localdepart"));
		queryBf.append("&remotecode=" + jsonParam.getString("remotecode"));
		queryBf.append("&direction=" + jsonParam.getString("direction"));
		queryBf.append("&maxtalklong=" + jsonParam.getString("maxtalklong"));
		queryBf.append("&mintalklong=" + jsonParam.getString("mintalklong"));
		queryBf.append("&tags=" + jsonParam.getString("tags"));
		queryBf.append("&pagestart=" + jsonParam.getString("pagestart"));
		queryBf.append("&pagelimit=" + jsonParam.getString("pagelimit"));

		// httpPost("http://192.168.0.200/friextra/unite_call.php",
		// queryBf.toString(), false);

		// httpGet("http://58.213.92.115:8080/sms/api?phonenumber=15850664753&smscontent=测试短信11Get");
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 */
	public static JSONObject httpPostJson(String url, String paramStr, boolean noNeedResponse) {
		// post请求返回结果
		DefaultHttpClient httpClient = new DefaultHttpClient();
		JSONObject jsonResult = null;
		HttpPost method = new HttpPost(url);
		try {
			if (null != paramStr) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(paramStr, "utf-8");
				entity.setContentEncoding("UTF-8");
				// entity.setContentType("application/json");
				entity.setContentType("application/x-www-form-urlencoded");

				method.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200) {
				String str = "";
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());

					// System.out.println(str);
					if (noNeedResponse) {
						return null;
					}
					/** 把json字符串转换成json对象 **/
					jsonResult = JSONObject.fromObject(str);
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		}
		return jsonResult;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param param
	 *            请求参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpPost(String url, String param, boolean noNeedResponse) throws ClientProtocolException, IOException {

		url = URLDecoder.decode(url, "UTF-8");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = null;
		HttpPost method = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(300).build();// 设置请求和传输超时时间
		//method.setConfig(requestConfig);

		if (null != param) {
			StringEntity entity = new StringEntity(param, "utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/x-www-form-urlencoded");
			method.setEntity(entity);
		}

		HttpResponse resultRep = httpClient.execute(method);
		/** 请求发送成功，并得到响应 **/
		if (resultRep.getStatusLine().getStatusCode() == 200) {
			String str = "";
			/** 读取服务器返回过来的json字符串数据 **/
			str = EntityUtils.toString(resultRep.getEntity());
			if (noNeedResponse) {
				return null;
			}
			/** 把json字符串转换成json对象 **/
			// jsonResult = JSONObject.parseObject(str);
			result = str;
		}
		return result;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static JSONObject httpGet(String url) {
		// get请求返回结果
		JSONObject jsonResult = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = EntityUtils.toString(response.getEntity());
				/** 把json字符串转换成json对象 **/
				jsonResult = JSONObject.fromObject(strResult);
				url = URLDecoder.decode(url, "UTF-8");
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		}
		return jsonResult;
	}
}