package com.kqds.util.sms;

import com.kqds.core.global.YZSysProps;
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

public class SmsClient {
  private String serviceURL = "";
  
  private String corporateid = "";
  
  private String userid = "";
  
  private String password = "";
  
  public SmsClient() throws UnsupportedEncodingException {
    this.serviceURL = YZSysProps.getString("SMS_SERVICEURL");
    this.corporateid = YZSysProps.getString("SMS_CORPORATEID");
    this.userid = YZSysProps.getString("SMS_USERID");
    this.password = YZSysProps.getString("SMS_PASSWORD");
  }
  
  public SmsClient(String serviceURL, String corporateid, String userid, String password) throws UnsupportedEncodingException {
    this.serviceURL = serviceURL;
    this.corporateid = corporateid;
    this.userid = userid;
    this.password = password;
  }
  
  public String GetBal() {
    String result = "";
    String soapAction = "http://tempuri.org/GetBal";
    String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>";
    xml = String.valueOf(xml) + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
    xml = String.valueOf(xml) + "<soap:Body>";
    xml = String.valueOf(xml) + "<GetBal xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<corporateid>" + this.corporateid + "</corporateid>";
    xml = String.valueOf(xml) + "<userid>" + this.userid + "</userid>";
    xml = String.valueOf(xml) + "<password>" + this.password + "</password>";
    xml = String.valueOf(xml) + "</GetBal>";
    xml = String.valueOf(xml) + "</soap:Body>";
    xml = String.valueOf(xml) + "</soap:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
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
      while ((inputLine = in.readLine()) != null) {
        Pattern pattern = Pattern.compile("<GetBalResult>(.*)</GetBalResult>");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
          result = matcher.group(1); 
      } 
      in.close();
      return new String(result.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public String SendSmsNew(String mobile, String content, String subcode, String schtime) {
    String result = "";
    String soapAction = "http://tempuri.org/SendSmsNew";
    String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>";
    xml = String.valueOf(xml) + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
    xml = String.valueOf(xml) + "<soap:Body>";
    xml = String.valueOf(xml) + "<SendSmsNew xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<corporateid>" + this.corporateid + "</corporateid>";
    xml = String.valueOf(xml) + "<userid>" + this.userid + "</userid>";
    xml = String.valueOf(xml) + "<password>" + this.password + "</password>";
    xml = String.valueOf(xml) + "<mobile>" + mobile + "</mobile>";
    xml = String.valueOf(xml) + "<content>" + content + "</content>";
    xml = String.valueOf(xml) + "<subcode>" + subcode + "</subcode>";
    xml = String.valueOf(xml) + "<schtime>" + schtime + "</schtime>";
    xml = String.valueOf(xml) + "</SendSmsNew>";
    xml = String.valueOf(xml) + "</soap:Body>";
    xml = String.valueOf(xml) + "</soap:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
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
      while ((inputLine = in.readLine()) != null) {
        Pattern pattern = Pattern.compile("<SendSmsNewResult>(.*)</SendSmsNewResult>");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
          result = matcher.group(1); 
      } 
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public String GetMo() {
    String result = "";
    String soapAction = "http://tempuri.org/GetMo";
    String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>";
    xml = String.valueOf(xml) + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
    xml = String.valueOf(xml) + "<soap:Body>";
    xml = String.valueOf(xml) + "<GetMo xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<corporateid>" + this.corporateid + "</corporateid>";
    xml = String.valueOf(xml) + "<userid>" + this.userid + "</userid>";
    xml = String.valueOf(xml) + "<password>" + this.password + "</password>";
    xml = String.valueOf(xml) + "</GetMo>";
    xml = String.valueOf(xml) + "</soap:Body>";
    xml = String.valueOf(xml) + "</soap:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
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
      while ((inputLine = in.readLine()) != null) {
        Pattern pattern = Pattern.compile("<GetMoResult>(.*)</GetMoResult>");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
          result = matcher.group(1); 
      } 
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
}
