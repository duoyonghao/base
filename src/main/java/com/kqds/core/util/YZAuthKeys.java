package com.kqds.core.util;

import java.util.Map;

public class YZAuthKeys
{
  public static char[] getPassword(Map params)
  {
    return "BLMDfSiSEUSeRnwxL89HnBbCUgBsYBjDbvHJGA==".toCharArray();
  }
  
  public static byte[] getSalt(Map params)
  {
    byte[] salt = { 1, 2, 3, 4, 5, 6, 7, 8 };
    return salt;
  }
  
  public static int getItCnt(Map params)
  {
    return 3;
  }
}
