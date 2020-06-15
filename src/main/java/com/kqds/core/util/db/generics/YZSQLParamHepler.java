package com.kqds.core.util.db.generics;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;

public class YZSQLParamHepler {
  public static String clobToString(Clob cl) throws Exception {
    String res = "";
    Reader is = null;
    if (cl == null)
      return ""; 
    try {
      is = cl.getCharacterStream();
      BufferedReader br = new BufferedReader(is);
      String s = br.readLine();
      StringBuffer sb = new StringBuffer();
      while (s != null) {
        sb.append(s);
        s = br.readLine();
        if (s != null)
          sb.append("\r\n"); 
      } 
      res = sb.toString();
      return res;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      is.close();
    } 
  }
}
