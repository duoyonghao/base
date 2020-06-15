package com.kqds.core.util.auth;

import com.kqds.core.util.YZSecurityUtility;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class YZAuthenticator
{
  private static boolean IS_OPEN_ENCRY = false;
  
  public static void encryKqdsUserInfo4Add(Object obj, String tablename)
    throws Exception
  {
    if (!IS_OPEN_ENCRY) {}
  }
  
  public static void encryKqdsUserInfo4Edit(Object obj, String tablename)
    throws Exception
  {
    if (!IS_OPEN_ENCRY) {}
  }
  
  public static Object decryKqdsUserInfo(Object obj, String tablename)
    throws Exception
  {
    if (!IS_OPEN_ENCRY) {
      return obj;
    }
    return obj;
  }
  
  public static List<Object> decryKqdsUserInfo(List<Object> list, String tablename)
    throws Exception
  {
    if (!IS_OPEN_ENCRY) {
      return list;
    }
    if (TableNameUtil.KQDS_USERDOCUMENT.equals(tablename.toUpperCase()))
    {
      List<Object> newList = new ArrayList();
      for (Object object : list)
      {
        Object newObject = decryKqdsUserInfo(object, tablename);
        newList.add(newObject);
      }
      return newList;
    }
    if (TableNameUtil.KQDS_VISIT.equals(tablename.toUpperCase()))
    {
      List<Object> newList = new ArrayList();
      for (Object object : list)
      {
        Object newObject = decryKqdsUserInfo(object, tablename);
        newList.add(newObject);
      }
      return newList;
    }
    if (TableNameUtil.KQDS_SMS.equals(tablename.toUpperCase()))
    {
      List<Object> newList = new ArrayList();
      for (Object object : list)
      {
        Object newObject = decryKqdsUserInfo(object, tablename);
        newList.add(newObject);
      }
      return newList;
    }
    return list;
  }
  
  public static String decryKqdsPhonenumber(String colName, String colValue)
    throws Exception
  {
    if (!IS_OPEN_ENCRY) {
      return colValue;
    }
    String newValue = null;
    if ((("phonenumber1".equals(colName)) || ("phonenumber2".equals(colName)) || ("telphone".equals(colName)) || ("sendphone".equals(colName))) && 
      (YZUtility.isNotNullOrEmpty(colValue)))
    {
      newValue = ciphDecryptStr(colValue);
      return newValue;
    }
    return colValue;
  }
  
  public static Object decryKqdsPhonenumber(String colName, Object colValue)
    throws Exception
  {
    if (!IS_OPEN_ENCRY) {
      return colValue;
    }
    if (("phonenumber1".equals(colName)) || ("phonenumber2".equals(colName)) || ("telphone".equals(colName)) || ("sendphone".equals(colName)))
    {
      if (colValue == null) {
        return colValue;
      }
      String colValueStr = (String)colValue;
      if (YZUtility.isNotNullOrEmpty(colValueStr)) {
        colValueStr = ciphDecryptStr(colValueStr);
      }
      return colValueStr;
    }
    return colValue;
  }
  
  public static String encryKqdsPhonenumber(String phonenumber)
    throws Exception
  {
    if (!IS_OPEN_ENCRY) {
      return phonenumber;
    }
    if (YZUtility.isNullorEmpty(phonenumber)) {
      return phonenumber;
    }
    return ciphEncryptStr(phonenumber);
  }
  
  public static String phonenumberLike(String colname, String phonenumber)
    throws Exception
  {
    phonenumber = encryKqdsPhonenumber(phonenumber);
    if (IS_OPEN_ENCRY) {
      return " " + colname + " = '" + phonenumber + "' ";
    }
    return " " + colname + " like '%" + phonenumber + "%' ";
  }
  
  public static String ciphEncryptStr(String srcPass)
    throws Exception
  {
    Cipher cipher = YZSecurityUtility.getPassWordCipher(1);
    byte[] passBytes = srcPass.getBytes("UTF-8");
    passBytes = cipher.doFinal(passBytes);
    return new BASE64Encoder().encode(passBytes);
  }
  
  public static String ciphDecryptStr(String encryptPass)
    throws Exception
  {
    Cipher cipher = YZSecurityUtility.getPassWordCipher(2);
    byte[] passBytes = new BASE64Decoder().decodeBuffer(encryptPass);
    return new String(cipher.doFinal(passBytes), "UTF-8");
  }
}
