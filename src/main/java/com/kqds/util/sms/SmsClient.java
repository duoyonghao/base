package com.kqds.util.sms;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kqds.core.global.YZSysProps;

public class SmsClient {

	private String serviceURL = ""; // webservice服务器定义
	private String corporateid = "";// 序列号
	private String userid = ""; // 账号
	private String password = ""; // 密码

	/*
	 * 构造函数
	 */
	public SmsClient() throws UnsupportedEncodingException {
		this.serviceURL = YZSysProps.getString("SMS_SERVICEURL");
		this.corporateid = YZSysProps.getString("SMS_CORPORATEID");
		this.userid = YZSysProps.getString("SMS_USERID");
		this.password = YZSysProps.getString("SMS_PASSWORD");
	}

	/*
	 * 构造函数
	 */
	public SmsClient(String serviceURL, String corporateid, String userid, String password) throws UnsupportedEncodingException {
		this.serviceURL = serviceURL;
		this.corporateid = corporateid;
		this.userid = userid;
		this.password = password;
	}

	/*
	 * 方法名称：GetBal 功 能：获取余额 参 数：无 返 回 值：余额（String）
	 */
	public String GetBal() {
		String result = "";
		String soapAction = "http://tempuri.org/GetBal";
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<GetBal xmlns=\"http://tempuri.org/\">";
		xml += "<corporateid>" + corporateid + "</corporateid>";
		xml += "<userid>" + userid + "</userid>";
		xml += "<password>" + password + "</password>";
		xml += "</GetBal>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		URL url;
		try {
			url = new URL(serviceURL);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			OutputStream out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<GetBalResult>(.*)</GetBalResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			in.close();
			return new String(result.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * 方法名称：SendSmsNew 功 能：发送短信
	 * 
	 * 返 回 值：唯一标识 rrid将返回系统生成的
	 */
	public String SendSmsNew(String mobile, String content, String subcode, String schtime) {
		String result = "";
		String soapAction = "http://tempuri.org/SendSmsNew";
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<SendSmsNew xmlns=\"http://tempuri.org/\">";
		xml += "<corporateid>" + corporateid + "</corporateid>";
		xml += "<userid>" + userid + "</userid>";
		xml += "<password>" + password + "</password>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "<subcode>" + subcode + "</subcode>";
		xml += "<schtime>" + schtime + "</schtime>";
		xml += "</SendSmsNew>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		URL url;
		try {
			url = new URL(serviceURL);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes("GBK"));
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			OutputStream out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<SendSmsNewResult>(.*)</SendSmsNewResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * 方法名称：GetMo 功 能：接收短信 参 数：无 返 回 值：接收到的信息
	 */
	public String GetMo() {
		String result = "";
		String soapAction = "http://tempuri.org/GetMo";
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<GetMo xmlns=\"http://tempuri.org/\">";
		xml += "<corporateid>" + corporateid + "</corporateid>";
		xml += "<userid>" + userid + "</userid>";
		xml += "<password>" + password + "</password>";
		xml += "</GetMo>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		URL url;
		try {
			url = new URL(serviceURL);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			OutputStream out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<GetMoResult>(.*)</GetMoResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
