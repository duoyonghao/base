package com.kqds.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class YZGuid {
  public static final int BEFORE_MD5 = 1;
  
  public static final int AFTER_M5 = 2;
  
  public static final int FORMAT_STRING = 3;
  
  private static Random myRand = null;
  
  private static SecureRandom mySecureRand = null;
  
  private static String s_id = null;
  
  private String seedingString = "";
  
  private String rawGUID = "";
  
  static {
    mySecureRand = new SecureRandom();
    long secureInitializer = mySecureRand.nextLong();
    myRand = new Random(secureInitializer);
    try {
      s_id = InetAddress.getLocalHost().toString();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } 
  }
  
  private void getRandomGUID(boolean secure) throws NoSuchAlgorithmException {
    MessageDigest md5 = null;
    StringBuffer sbValueBeforeMD5 = new StringBuffer();
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException ex) {
      throw ex;
    } 
    try {
      long time = System.currentTimeMillis();
      long rand = 0L;
      if (secure) {
        rand = mySecureRand.nextLong();
      } else {
        rand = myRand.nextLong();
      } 
      sbValueBeforeMD5.append(s_id);
      sbValueBeforeMD5.append(":");
      sbValueBeforeMD5.append(Long.toString(time));
      sbValueBeforeMD5.append(":");
      sbValueBeforeMD5.append(Long.toString(rand));
      this.seedingString = sbValueBeforeMD5.toString();
      md5.update(this.seedingString.getBytes());
      byte[] array = md5.digest();
      StringBuffer sb = new StringBuffer();
      for (int j = 0; j < array.length; j++) {
        int b = array[j] & 0xFF;
        if (b < 16)
          sb.append('0'); 
        sb.append(Integer.toHexString(b));
      } 
      this.rawGUID = sb.toString();
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public static String getGuid(int nFormatType, boolean secure) throws NoSuchAlgorithmException {
    YZGuid builder = new YZGuid();
    builder.getRandomGUID(secure);
    String sGuid = "";
    if (1 == nFormatType) {
      sGuid = builder.getSeedingString();
    } else if (2 == nFormatType) {
      sGuid = builder.getRawGUID();
    } else {
      sGuid = builder.toString();
    } 
    return sGuid;
  }
  
  public static String getGuid(int nFormatType) throws NoSuchAlgorithmException {
    return getGuid(nFormatType, false);
  }
  
  public static String getGuid() throws NoSuchAlgorithmException {
    return getGuid(3, false);
  }
  
  public static String getRawGuid() throws NoSuchAlgorithmException {
    return getGuid(2, false);
  }
  
  public String toString() {
    String raw = this.rawGUID.toUpperCase();
    StringBuffer sb = new StringBuffer();
    sb.append(raw.substring(0, 8));
    sb.append("-");
    sb.append(raw.substring(8, 12));
    sb.append("-");
    sb.append(raw.substring(12, 16));
    sb.append("-");
    sb.append(raw.substring(16, 20));
    sb.append("-");
    sb.append(raw.substring(20));
    return sb.toString();
  }
  
  public String getSeedingString() {
    return this.seedingString;
  }
  
  public String getRawGUID() {
    return this.rawGUID;
  }
}
