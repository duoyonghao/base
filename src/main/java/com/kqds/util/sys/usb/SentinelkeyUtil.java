package com.kqds.util.sys.usb;

import Aladdin.Hasp;
import java.io.PrintStream;

public class SentinelkeyUtil
{
  public static final int DEMO_MEMBUFFER_SIZE = 128;
  public static final String vendorCode = "yG6SqZwJhzCtN2lPjEg7ujqe7jZN6UqGJN1DxSR7ZqHpOQeNdRMs3DFgzRNv72Xlahm3veHrOTp5zms+6TQP9zK6Gbu2fdAUICHA3bRaBrNcBQ3Q3FcEGrVG7MIbztA/S86v2BT4QwfLvNk8qOBB3MkQofj6GfuDSSrMFBeh1AjgSz0ehmZ35Egu5vypI4XRDhUca6fXia4VbUa45j9DzYxhePXMW6HKuBP97YzIdTfbC+oVfwTZu0kiA1z/aCj5trnWBg3ygGNY1OL+8A/IrEjSiTFYZBJfqL2rPsrbnGAdOoaXjaPt5uRQfVeBOb0WXPBbUjm1DTTW1pdbwcpo+NVBVXtKmDYAfH4Y7+lxrUhrgzWo5/ORao4WyfQcVRc6EJQLEdb41IHILH/6WSab6AMhgRY9IdIqijA3XO7No3osb2iF8YJf6+zQQyP/bDGXBEH9eek2d3NEc0NMS3ad2GIx+c6S1EqnoGPxErQZ4h5LYuOesVtqa16qW0OWq0zf23u8gj959obmUbwgiacIl+orc2uGdNAhLithUtmQ1FEIYUkBCLGWi7B9StGjp3KacneeS9wk+eAZpGLvaAB7smYco18Yk9MNy/Fhvr8Ek+RNmvdoB/FQtQbqxjkOeAH8hOl7TP3g9ICQiHfJaewyslMwcqUkhIZ6HnzSPnQG1bUdi4OMqDxaq3d49F/S89CzJCBPqVJqYnM9vzIVNK5kYdMVze6loGulmA2zmMZzlOqd2Vh6os4yUbiQtdjIj0c3npIZOMpb1+DMDViScu6lmWDTMCw41PBX6+XoosWDqLC0mKFtkzJlTeL3lV9DUAGYPOVfvBLlxeJ4Ydrsl7ZTxIaq91Z7sFZnbeRMxQQkmk/a+EY1n7ZqVEqjoNubNt/80zdEl/lQx9uTDYQp1qzdBgWA4MXq463jxmjR4R2JoAJmzhCTCQEtgvPqvm1TbU43OrspnMjduiVPO0yjWj1Vvg==";
  public static final String scope = new String("<haspscope>\n <license_manager hostname=\"localhost\" />\n</haspscope>\n");
  public static final String scope1 = new String("<haspscope />\n");
  public static final String view = new String("<haspformat root=\"my_custom_scope\">\n  <hasp>\n    <attribute name=\"id\" />\n    <attribute name=\"type\" />\n    <feature>\n      <attribute name=\"id\" />\n      <element name=\"concurrency\" />\n      <element name=\"license\" />\n      <session>\n        <element name=\"username\" />\n        <element name=\"hostname\" />\n        <element name=\"ip\" />\n        <element name=\"apiversion\" />\n      </session>\n    </feature>\n  </hasp>\n</haspformat>\n");
  
  public static boolean login()
  {
    boolean retVal = false;
    
    Hasp hasp = new Hasp(1L);
    
    hasp.login("yG6SqZwJhzCtN2lPjEg7ujqe7jZN6UqGJN1DxSR7ZqHpOQeNdRMs3DFgzRNv72Xlahm3veHrOTp5zms+6TQP9zK6Gbu2fdAUICHA3bRaBrNcBQ3Q3FcEGrVG7MIbztA/S86v2BT4QwfLvNk8qOBB3MkQofj6GfuDSSrMFBeh1AjgSz0ehmZ35Egu5vypI4XRDhUca6fXia4VbUa45j9DzYxhePXMW6HKuBP97YzIdTfbC+oVfwTZu0kiA1z/aCj5trnWBg3ygGNY1OL+8A/IrEjSiTFYZBJfqL2rPsrbnGAdOoaXjaPt5uRQfVeBOb0WXPBbUjm1DTTW1pdbwcpo+NVBVXtKmDYAfH4Y7+lxrUhrgzWo5/ORao4WyfQcVRc6EJQLEdb41IHILH/6WSab6AMhgRY9IdIqijA3XO7No3osb2iF8YJf6+zQQyP/bDGXBEH9eek2d3NEc0NMS3ad2GIx+c6S1EqnoGPxErQZ4h5LYuOesVtqa16qW0OWq0zf23u8gj959obmUbwgiacIl+orc2uGdNAhLithUtmQ1FEIYUkBCLGWi7B9StGjp3KacneeS9wk+eAZpGLvaAB7smYco18Yk9MNy/Fhvr8Ek+RNmvdoB/FQtQbqxjkOeAH8hOl7TP3g9ICQiHfJaewyslMwcqUkhIZ6HnzSPnQG1bUdi4OMqDxaq3d49F/S89CzJCBPqVJqYnM9vzIVNK5kYdMVze6loGulmA2zmMZzlOqd2Vh6os4yUbiQtdjIj0c3npIZOMpb1+DMDViScu6lmWDTMCw41PBX6+XoosWDqLC0mKFtkzJlTeL3lV9DUAGYPOVfvBLlxeJ4Ydrsl7ZTxIaq91Z7sFZnbeRMxQQkmk/a+EY1n7ZqVEqjoNubNt/80zdEl/lQx9uTDYQp1qzdBgWA4MXq463jxmjR4R2JoAJmzhCTCQEtgvPqvm1TbU43OrspnMjduiVPO0yjWj1Vvg==");
    int status = hasp.getLastError();
    switch (status)
    {
    case 0: 
      System.out.println("根据请求检测加密狗是否存在。。。。。。ok");
      retVal = true;
      break;
    case 31: 
      System.out.println("no Sentinel DEMOMA key found");
      break;
    case 7: 
      System.out.println("Sentinel key not found");
      break;
    case 11: 
      System.out.println("outdated driver version or no driver installed");
      break;
    case 14: 
      System.out.println("Sentinel key not found");
      break;
    case 22: 
      System.out.println("invalid vendor code");
      break;
    default: 
      System.out.println("login to default feature failed");
    }
    return retVal;
  }
  
  public static String decrypt()
  {
    String retVal = "";
    
    Hasp hasp = new Hasp(1L);
    int status = 0;
    hasp.login("yG6SqZwJhzCtN2lPjEg7ujqe7jZN6UqGJN1DxSR7ZqHpOQeNdRMs3DFgzRNv72Xlahm3veHrOTp5zms+6TQP9zK6Gbu2fdAUICHA3bRaBrNcBQ3Q3FcEGrVG7MIbztA/S86v2BT4QwfLvNk8qOBB3MkQofj6GfuDSSrMFBeh1AjgSz0ehmZ35Egu5vypI4XRDhUca6fXia4VbUa45j9DzYxhePXMW6HKuBP97YzIdTfbC+oVfwTZu0kiA1z/aCj5trnWBg3ygGNY1OL+8A/IrEjSiTFYZBJfqL2rPsrbnGAdOoaXjaPt5uRQfVeBOb0WXPBbUjm1DTTW1pdbwcpo+NVBVXtKmDYAfH4Y7+lxrUhrgzWo5/ORao4WyfQcVRc6EJQLEdb41IHILH/6WSab6AMhgRY9IdIqijA3XO7No3osb2iF8YJf6+zQQyP/bDGXBEH9eek2d3NEc0NMS3ad2GIx+c6S1EqnoGPxErQZ4h5LYuOesVtqa16qW0OWq0zf23u8gj959obmUbwgiacIl+orc2uGdNAhLithUtmQ1FEIYUkBCLGWi7B9StGjp3KacneeS9wk+eAZpGLvaAB7smYco18Yk9MNy/Fhvr8Ek+RNmvdoB/FQtQbqxjkOeAH8hOl7TP3g9ICQiHfJaewyslMwcqUkhIZ6HnzSPnQG1bUdi4OMqDxaq3d49F/S89CzJCBPqVJqYnM9vzIVNK5kYdMVze6loGulmA2zmMZzlOqd2Vh6os4yUbiQtdjIj0c3npIZOMpb1+DMDViScu6lmWDTMCw41PBX6+XoosWDqLC0mKFtkzJlTeL3lV9DUAGYPOVfvBLlxeJ4Ydrsl7ZTxIaq91Z7sFZnbeRMxQQkmk/a+EY1n7ZqVEqjoNubNt/80zdEl/lQx9uTDYQp1qzdBgWA4MXq463jxmjR4R2JoAJmzhCTCQEtgvPqvm1TbU43OrspnMjduiVPO0yjWj1Vvg==");
    status = hasp.getLastError();
    switch (status)
    {
    case 0: 
      System.out.println("OK");
      break;
    case 31: 
      System.out.println("no Sentinel DEMOMA key found");
      break;
    case 7: 
      System.out.println("Sentinel key not found");
      break;
    case 11: 
      System.out.println("outdated driver version or no driver installed");
      break;
    case 14: 
      System.out.println("Sentinel key not found");
      break;
    case 22: 
      System.out.println("invalid vendor code");
      break;
    default: 
      System.out.println("login to default feature failed");
    }
    byte[] membuffer = new byte[48];
    hasp.read(65524L, 0, membuffer);
    status = hasp.getLastError();
    switch (status)
    {
    case 0: 
      System.out.println("OK");
      retVal = new String(membuffer, 0, membuffer.length).trim();
      break;
    case 9: 
      System.out.println("handle not active\n");
      break;
    case 10: 
      System.out.println("invalid file id");
      break;
    case 1: 
      System.out.println("beyond memory range of attached Sentinel key");
      break;
    case 7: 
      System.out.println("Sentinel key not found");
      break;
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 8: 
    default: 
      System.out.println("read memory failed");
    }
    hasp.decrypt(membuffer);
    status = hasp.getLastError();
    switch (status)
    {
    case 0: 
      System.out.println("decryption ok:");
      
      retVal = new String(membuffer, 0, membuffer.length).trim();
      break;
    case 9: 
      System.out.println("handle not active");
      break;
    case 8: 
      System.out.println("data length too short");
      break;
    case 23: 
      System.out.println("attached key does not support AES encryption");
      break;
    case 31: 
      System.out.println("key not found");
      break;
    default: 
      System.out.println("decryption failed");
    }
    return retVal;
  }
  
  public static String readFormKey()
  {
    String retVal = "";
    
    Hasp hasp = new Hasp(1L);
    int status = 0;
    hasp.login("yG6SqZwJhzCtN2lPjEg7ujqe7jZN6UqGJN1DxSR7ZqHpOQeNdRMs3DFgzRNv72Xlahm3veHrOTp5zms+6TQP9zK6Gbu2fdAUICHA3bRaBrNcBQ3Q3FcEGrVG7MIbztA/S86v2BT4QwfLvNk8qOBB3MkQofj6GfuDSSrMFBeh1AjgSz0ehmZ35Egu5vypI4XRDhUca6fXia4VbUa45j9DzYxhePXMW6HKuBP97YzIdTfbC+oVfwTZu0kiA1z/aCj5trnWBg3ygGNY1OL+8A/IrEjSiTFYZBJfqL2rPsrbnGAdOoaXjaPt5uRQfVeBOb0WXPBbUjm1DTTW1pdbwcpo+NVBVXtKmDYAfH4Y7+lxrUhrgzWo5/ORao4WyfQcVRc6EJQLEdb41IHILH/6WSab6AMhgRY9IdIqijA3XO7No3osb2iF8YJf6+zQQyP/bDGXBEH9eek2d3NEc0NMS3ad2GIx+c6S1EqnoGPxErQZ4h5LYuOesVtqa16qW0OWq0zf23u8gj959obmUbwgiacIl+orc2uGdNAhLithUtmQ1FEIYUkBCLGWi7B9StGjp3KacneeS9wk+eAZpGLvaAB7smYco18Yk9MNy/Fhvr8Ek+RNmvdoB/FQtQbqxjkOeAH8hOl7TP3g9ICQiHfJaewyslMwcqUkhIZ6HnzSPnQG1bUdi4OMqDxaq3d49F/S89CzJCBPqVJqYnM9vzIVNK5kYdMVze6loGulmA2zmMZzlOqd2Vh6os4yUbiQtdjIj0c3npIZOMpb1+DMDViScu6lmWDTMCw41PBX6+XoosWDqLC0mKFtkzJlTeL3lV9DUAGYPOVfvBLlxeJ4Ydrsl7ZTxIaq91Z7sFZnbeRMxQQkmk/a+EY1n7ZqVEqjoNubNt/80zdEl/lQx9uTDYQp1qzdBgWA4MXq463jxmjR4R2JoAJmzhCTCQEtgvPqvm1TbU43OrspnMjduiVPO0yjWj1Vvg==");
    status = hasp.getLastError();
    switch (status)
    {
    case 0: 
      System.out.println("OK");
      break;
    case 31: 
      System.out.println("no Sentinel DEMOMA key found");
      break;
    case 7: 
      System.out.println("Sentinel key not found");
      break;
    case 11: 
      System.out.println("outdated driver version or no driver installed");
      break;
    case 14: 
      System.out.println("Sentinel key not found");
      break;
    case 22: 
      System.out.println("invalid vendor code");
      break;
    default: 
      System.out.println("login to default feature failed");
    }
    byte[] membuffer = new byte[48];
    hasp.read(65524L, 0, membuffer);
    status = hasp.getLastError();
    switch (status)
    {
    case 0: 
      System.out.println("OK");
      retVal = new String(membuffer, 0, membuffer.length).trim();
      break;
    case 9: 
      System.out.println("handle not active\n");
      break;
    case 10: 
      System.out.println("invalid file id");
      break;
    case 1: 
      System.out.println("beyond memory range of attached Sentinel key");
      break;
    case 7: 
      System.out.println("Sentinel key not found");
      break;
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 8: 
    default: 
      System.out.println("read memory failed");
    }
    return retVal;
  }
}
