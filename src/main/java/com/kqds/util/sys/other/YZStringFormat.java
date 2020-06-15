package com.kqds.util.sys.other;

public final class YZStringFormat
{
  public static String unformat(String str)
  {
    String st = null;
    if (str == null) {
      return null;
    }
    StringBuffer words = new StringBuffer();
    
    String[] temps = str.split("_");
    for (String string : temps)
    {
      String temp = string.trim();
      String s = temp.substring(1, temp.length());
      String ss = temp.substring(0, 1).toUpperCase() + s.toLowerCase();
      words.append(ss);
    }
    st = words.toString().substring(0, 1).toLowerCase() + words.toString().substring(1, words.toString().length());
    return st;
  }
  
  public static String format(String str, boolean isTable)
  {
    StringBuffer words = new StringBuffer();
    for (int i = 0; i < str.length(); i++)
    {
      char ch = str.charAt(i);
      if (Character.isUpperCase(ch))
      {
        String s = str.substring(0, i);
        String ss = s.toUpperCase();
        if (!"".equals(ss)) {
          words.append(ss + "_");
        }
        str = str.substring(i);
        i = 0;
      }
    }
    words.append(str.toUpperCase());
    return words.toString();
  }
}
