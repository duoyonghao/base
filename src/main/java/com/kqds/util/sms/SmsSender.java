package com.kqds.util.sms;

import com.kqds.util.sys.YZUtility;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class SmsSender
{
  public static String getRandNum(int min, int max)
  {
    int randNum = min + (int)(Math.random() * (max - min + 1));
    return randNum;
  }
  
  public static String getSender(String sjhm)
    throws Exception
  {
    String phone = YZUtility.getPhone(sjhm);
    if (!YZUtility.validatemobile(phone)) {
      throw new Exception("无效手机号码：" + sjhm);
    }
    String randNum = getRandNum(1, 999999);
    String content = "【UNIC管理系统】您的验证码是：" + randNum + "。";
    try
    {
      SmsClient client = new SmsClient();
      
      String sendresult = client.SendSmsNew(phone, content, "", "");
      if ((!sendresult.startsWith("-")) && (sendresult.length() > 0)) {
        System.out.println("发送成功返回值为:" + sendresult);
      } else {
        System.out.println("发送失败 返回值:" + sendresult);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return randNum;
  }
  
  public static String getSenderTx(String sjhm, String content)
    throws Exception
  {
    String phone = YZUtility.getPhone(sjhm);
    if (!YZUtility.validatemobile(phone)) {
      throw new Exception("无效手机号码：" + sjhm);
    }
    String returnString = "0";
    try
    {
      SmsClient client = new SmsClient();
      
      returnString = client.SendSmsNew(phone, "【UNIC管理系统】" + content, "", "");
      System.out.println("发送成功返回值为:" + returnString);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if ((!returnString.startsWith("-")) && (returnString.length() > 0)) {
      returnString = "1";
    }
    return returnString;
  }
  
  public static String GetBal()
    throws Exception
  {
    String returnString = "0";
    try
    {
      SmsClient client = new SmsClient();
      
      returnString = client.GetBal();
      System.out.println("短信余额为:" + returnString);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return returnString;
  }
  
  public static void main(String[] args)
    throws UnsupportedEncodingException
  {
    String corporateid = "201084";
    String userid = "201084";
    String password = "D8R2z0p4";
    SmsClient client = new SmsClient("http://yd10086.cn:8070/Service.asmx", corporateid, userid, password);
    
    String balance = client.GetBal();
    System.out.println("当前余额为:" + balance);
    
    String sendresult = client.SendSmsNew("15850664753", "【UNIC管理系统】【患者姓名】，你好！你的预约时间为【预约时间】，请及时就诊，谢谢！", "", "");
    if ((!sendresult.startsWith("-")) && (sendresult.length() > 0)) {
      System.out.println("发送成功返回值为:" + sendresult);
    } else {
      System.out.println("发送失败 返回值:" + sendresult);
    }
  }
}
