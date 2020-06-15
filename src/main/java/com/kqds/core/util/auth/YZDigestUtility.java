package com.kqds.core.util.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class YZDigestUtility {
  private static final char[] HEX_ARRAY = new char[] { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'a', 'b', 'c', 'd', 'e', 'f' };
  
  private static MessageDigest md = null;
  
  static {
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
  }
  
  public static synchronized byte[] md5(byte[] inputData) {
    if (md == null)
      return null; 
    md.reset();
    md.update(inputData);
    return md.digest();
  }
  
  public static synchronized String md5Hex(byte[] srcBytes) {
    byte[] md5Array = md5(srcBytes);
    StringBuffer rtStr = new StringBuffer();
    for (int i = 0; i < md5Array.length; i++) {
      byte high = (byte)(md5Array[i] >> 4 & 0xF);
      byte low = (byte)(md5Array[i] & 0xF);
      rtStr.append(HEX_ARRAY[high]);
      rtStr.append(HEX_ARRAY[low]);
    } 
    return rtStr.toString();
  }
}
