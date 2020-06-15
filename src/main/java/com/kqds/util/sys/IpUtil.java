package com.kqds.util.sys;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class IpUtil
{
  public static String getIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("x-forwarded-for");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    if (ip.equals("0:0:0:0:0:0:0:1")) {
      ip = "本地";
    }
    return ip;
  }
  
  public static List<String> getLocalIPList()
  {
    List<String> ipList = new ArrayList();
    try
    {
      Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
      Enumeration<InetAddress> inetAddresses;
      for (; networkInterfaces.hasMoreElements(); inetAddresses.hasMoreElements())
      {
        NetworkInterface networkInterface = (NetworkInterface)networkInterfaces.nextElement();
        inetAddresses = networkInterface.getInetAddresses();
        continue;
        InetAddress inetAddress = (InetAddress)inetAddresses.nextElement();
        if ((inetAddress != null) && ((inetAddress instanceof Inet4Address)))
        {
          String ip = inetAddress.getHostAddress();
          ipList.add(ip);
        }
      }
    }
    catch (SocketException e)
    {
      e.printStackTrace();
    }
    return ipList;
  }
}
