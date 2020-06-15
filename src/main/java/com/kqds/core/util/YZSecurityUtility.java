package com.kqds.core.util;

import java.io.InputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class YZSecurityUtility
{
  public static Cipher getPassWordCipher(int mode)
    throws Exception
  {
    return getPassWordCipher(YZAuthKeys.getPassword(null), YZAuthKeys.getSalt(null), YZAuthKeys.getItCnt(null), mode);
  }
  
  public static Cipher getPassWordCipher(char[] passWord, byte[] salt, int itCnt, int mode)
    throws Exception
  {
    PBEKeySpec pbeKeySpec = null;
    PBEParameterSpec pbeParamSpec = null;
    SecretKeyFactory keyFac = null;
    
    pbeParamSpec = new PBEParameterSpec(salt, itCnt);
    pbeKeySpec = new PBEKeySpec(passWord);
    keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
    SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
    Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
    pbeCipher.init(mode, pbeKey, pbeParamSpec);
    
    return pbeCipher;
  }
  
  public static InputStream buildPassWordInputStream(Cipher cipher, InputStream is)
    throws Exception
  {
    CipherInputStream rtIs = new CipherInputStream(is, cipher);
    
    return rtIs;
  }
  
  public static InputStream buildPassWordInputStream(char[] passWord, byte[] salt, int itCnt, int mode, InputStream is)
    throws Exception
  {
    Cipher cipher = getPassWordCipher(passWord, salt, itCnt, mode);
    CipherInputStream rtIs = new CipherInputStream(is, cipher);
    
    return rtIs;
  }
}
