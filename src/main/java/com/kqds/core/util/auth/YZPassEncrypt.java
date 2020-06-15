package com.kqds.core.util.auth;

import com.kqds.core.global.YZSysProps;
import java.io.UnsupportedEncodingException;

public class YZPassEncrypt {
  private static final byte[] MD5_MAGIC = new byte[] { 36, 49, 36 };
  
  private static final int MD5_MAGIC_LEN = 3;
  
  private static final int PHP_MAX_SALT_LEN = 12;
  
  private static final int S11 = 7;
  
  private static final int S12 = 12;
  
  private static final int S13 = 17;
  
  private static final int S14 = 22;
  
  private static final int S21 = 5;
  
  private static final int S22 = 9;
  
  private static final int S23 = 14;
  
  private static final int S24 = 20;
  
  private static final int S31 = 4;
  
  private static final int S32 = 11;
  
  private static final int S33 = 16;
  
  private static final int S34 = 23;
  
  private static final int S41 = 6;
  
  private static final int S42 = 10;
  
  private static final int S43 = 15;
  
  private static final int S44 = 21;
  
  private static byte[] itoa64 = new byte[] { 
      46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 
      56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 
      73, 74, 75, 76, 77, 78, 
      79, 80, 81, 82, 
      83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 
      99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 
      109, 110, 111, 112, 113, 114, 115, 116, 117, 
      118, 
      119, 120, 121, 122 };
  
  private static byte[] PADDING = new byte[] { Byte.MIN_VALUE };
  
  public static byte[] str2Bytes(String srcStr) {
    int len = srcStr.length();
    byte[] rtArray = new byte[len];
    for (int i = 0; i < len; i++)
      rtArray[i] = (byte)(srcStr.charAt(i) & 0xFF); 
    return rtArray;
  }
  
  public static long int2Long(int src) {
    long rtValue = src & 0xFFFFFFFFL;
    return rtValue;
  }
  
  public static int add(int i1, int i2) {
    long rtValue = int2Long(i1) + int2Long(i2);
    rtValue &= 0xFFFFFFFFL;
    return (int)rtValue;
  }
  
  public static int byte2Int(byte src) {
    int rtValue = src & 0xFF;
    return rtValue;
  }
  
  private static int F(int x, int y, int z) {
    return x & y | (x ^ 0xFFFFFFFF) & z;
  }
  
  private static int G(int x, int y, int z) {
    return x & z | y & (z ^ 0xFFFFFFFF);
  }
  
  private static int H(int x, int y, int z) {
    return x ^ y ^ z;
  }
  
  private static int I(int x, int y, int z) {
    return y ^ (x | z ^ 0xFFFFFFFF);
  }
  
  private static int ROTATE_LEFT(int x, int n) {
    return x << n | x >>> 32 - n;
  }
  
  private static int FF(int a, int b, int c, int d, int x, int s, int ac) {
    int a0 = add(add(F(b, c, d), x), ac);
    a = add(a, a0);
    a = ROTATE_LEFT(a, s);
    a = add(a, b);
    return a;
  }
  
  private static int GG(int a, int b, int c, int d, int x, int s, int ac) {
    int a0 = add(add(G(b, c, d), x), ac);
    a = add(a, a0);
    a = ROTATE_LEFT(a, s);
    a = add(a, b);
    return a;
  }
  
  private static int HH(int a, int b, int c, int d, int x, int s, int ac) {
    int a0 = add(add(H(b, c, d), x), ac);
    a = add(a, a0);
    a = ROTATE_LEFT(a, s);
    a = add(a, b);
    return a;
  }
  
  private static int II(int a, int b, int c, int d, int x, int s, int ac) {
    int a0 = add(add(I(b, c, d), x), ac);
    a = add(a, a0);
    a = ROTATE_LEFT(a, s);
    a = add(a, b);
    return a;
  }
  
  private static void Encode(byte[] output, int[] input, int len) {
    for (int i = 0, j = 0; j < len; i++, j += 4) {
      output[j] = (byte)(input[i] & 0xFF);
      output[j + 1] = (byte)(input[i] >>> 8 & 0xFF);
      output[j + 2] = (byte)(input[i] >>> 16 & 0xFF);
      output[j + 3] = (byte)(input[i] >>> 24 & 0xFF);
    } 
  }
  
  private static void Decode(int[] output, byte[] input, int len) {
    for (int i = 0, j = 0; j < len; i++, j += 4)
      output[i] = byte2Int(input[j]) | byte2Int(input[j + 1]) << 8 | byte2Int(input[j + 2]) << 16 | byte2Int(input[j + 3]) << 24; 
  }
  
  private static void PHP_MD5Init(YZMD5Context context) {
    context.count[1] = 0;
    context.count[0] = 0;
    context.state[0] = 1732584193;
    context.state[1] = -271733879;
    context.state[2] = -1732584194;
    context.state[3] = 271733878;
  }
  
  private static void memcpy(byte[] dest, byte[] src, int offsetDest, int offesetSrc, int length) {
    for (int i = 0; i < length; i++)
      dest[offsetDest + i] = src[offesetSrc + i]; 
  }
  
  private static void PHP_MD5Update(YZMD5Context context, byte[] input, int inputLen) {
    int i, index = context.count[0] >>> 3 & 0x3F;
    if (int2Long(context.count[0] = add(context.count[0], inputLen << 3)) < int2Long(inputLen << 3))
      context.count[1] = add(context.count[1], 1); 
    context.count[1] = add(context.count[1], inputLen >>> 29);
    int partLen = 64 - index;
    if (inputLen >= partLen) {
      memcpy(context.buffer, input, index, 0, partLen);
      MD5Transform(context.state, context.buffer);
      for (i = partLen; i + 63 < inputLen; i += 64) {
        byte[] buff = new byte[64];
        memset(buff, (byte)0);
        int len = input.length - i;
        memcpy(buff, input, 0, i, (len < 64) ? len : 64);
        MD5Transform(context.state, buff);
      } 
      index = 0;
    } else {
      i = 0;
    } 
    memcpy(context.buffer, input, index, i, inputLen - i);
  }
  
  private static void memset(byte[] src, byte value) {
    for (int i = 0; i < src.length; i++)
      src[i] = value; 
  }
  
  private static void memset(int[] src, int value) {
    for (int i = 0; i < src.length; i++)
      src[i] = value; 
  }
  
  private static void MD5Transform(int[] state, byte[] block) {
    int a = state[0], b = state[1], c = state[2], d = state[3];
    int[] x = new int[16];
    Decode(x, block, 64);
    a = FF(a, b, c, d, x[0], 7, -680876936);
    d = FF(d, a, b, c, x[1], 12, -389564586);
    c = FF(c, d, a, b, x[2], 17, 606105819);
    b = FF(b, c, d, a, x[3], 22, -1044525330);
    a = FF(a, b, c, d, x[4], 7, -176418897);
    d = FF(d, a, b, c, x[5], 12, 1200080426);
    c = FF(c, d, a, b, x[6], 17, -1473231341);
    b = FF(b, c, d, a, x[7], 22, -45705983);
    a = FF(a, b, c, d, x[8], 7, 1770035416);
    d = FF(d, a, b, c, x[9], 12, -1958414417);
    c = FF(c, d, a, b, x[10], 17, -42063);
    b = FF(b, c, d, a, x[11], 22, -1990404162);
    a = FF(a, b, c, d, x[12], 7, 1804603682);
    d = FF(d, a, b, c, x[13], 12, -40341101);
    c = FF(c, d, a, b, x[14], 17, -1502002290);
    b = FF(b, c, d, a, x[15], 22, 1236535329);
    a = GG(a, b, c, d, x[1], 5, -165796510);
    d = GG(d, a, b, c, x[6], 9, -1069501632);
    c = GG(c, d, a, b, x[11], 14, 643717713);
    b = GG(b, c, d, a, x[0], 20, -373897302);
    a = GG(a, b, c, d, x[5], 5, -701558691);
    d = GG(d, a, b, c, x[10], 9, 38016083);
    c = GG(c, d, a, b, x[15], 14, -660478335);
    b = GG(b, c, d, a, x[4], 20, -405537848);
    a = GG(a, b, c, d, x[9], 5, 568446438);
    d = GG(d, a, b, c, x[14], 9, -1019803690);
    c = GG(c, d, a, b, x[3], 14, -187363961);
    b = GG(b, c, d, a, x[8], 20, 1163531501);
    a = GG(a, b, c, d, x[13], 5, -1444681467);
    d = GG(d, a, b, c, x[2], 9, -51403784);
    c = GG(c, d, a, b, x[7], 14, 1735328473);
    b = GG(b, c, d, a, x[12], 20, -1926607734);
    a = HH(a, b, c, d, x[5], 4, -378558);
    d = HH(d, a, b, c, x[8], 11, -2022574463);
    c = HH(c, d, a, b, x[11], 16, 1839030562);
    b = HH(b, c, d, a, x[14], 23, -35309556);
    a = HH(a, b, c, d, x[1], 4, -1530992060);
    d = HH(d, a, b, c, x[4], 11, 1272893353);
    c = HH(c, d, a, b, x[7], 16, -155497632);
    b = HH(b, c, d, a, x[10], 23, -1094730640);
    a = HH(a, b, c, d, x[13], 4, 681279174);
    d = HH(d, a, b, c, x[0], 11, -358537222);
    c = HH(c, d, a, b, x[3], 16, -722521979);
    b = HH(b, c, d, a, x[6], 23, 76029189);
    a = HH(a, b, c, d, x[9], 4, -640364487);
    d = HH(d, a, b, c, x[12], 11, -421815835);
    c = HH(c, d, a, b, x[15], 16, 530742520);
    b = HH(b, c, d, a, x[2], 23, -995338651);
    a = II(a, b, c, d, x[0], 6, -198630844);
    d = II(d, a, b, c, x[7], 10, 1126891415);
    c = II(c, d, a, b, x[14], 15, -1416354905);
    b = II(b, c, d, a, x[5], 21, -57434055);
    a = II(a, b, c, d, x[12], 6, 1700485571);
    d = II(d, a, b, c, x[3], 10, -1894986606);
    c = II(c, d, a, b, x[10], 15, -1051523);
    b = II(b, c, d, a, x[1], 21, -2054922799);
    a = II(a, b, c, d, x[8], 6, 1873313359);
    d = II(d, a, b, c, x[15], 10, -30611744);
    c = II(c, d, a, b, x[6], 15, -1560198380);
    b = II(b, c, d, a, x[13], 21, 1309151649);
    a = II(a, b, c, d, x[4], 6, -145523070);
    d = II(d, a, b, c, x[11], 10, -1120210379);
    c = II(c, d, a, b, x[2], 15, 718787259);
    b = II(b, c, d, a, x[9], 21, -343485551);
    state[0] = add(state[0], a);
    state[1] = add(state[1], b);
    state[2] = add(state[2], c);
    state[3] = add(state[3], d);
    memset(x, 0);
  }
  
  private static void PHP_MD5Final(byte[] digest, YZMD5Context context) {
    byte[] bits = new byte[8];
    int index = 0;
    int padLen = 0;
    Encode(bits, context.count, 8);
    index = context.count[0] >>> 3 & 0x3F;
    padLen = (index < 56) ? (56 - index) : (120 - index);
    PHP_MD5Update(context, PADDING, padLen);
    PHP_MD5Update(context, bits, 8);
    Encode(digest, context.state, 16);
    context.set0();
  }
  
  private static void to64(byte[] s, int offset, int v, int n) {
    int i = 0;
    while (--n >= 0) {
      s[offset + i++] = itoa64[v & 0x3F];
      v >>>= 6;
    } 
  }
  
  private static int strncmp(byte[] str1, byte[] str2, int from1, int from2, int len) {
    for (int i = 0; i < len; i++) {
      if (str1[from1 + i] > str2[from2 + i])
        return 1; 
      if (str1[from1 + i] < str2[from2 + i])
        return -1; 
    } 
    return 0;
  }
  
  private static byte[] cloneArray(byte[] src, int offset, int len) {
    byte[] rtArray = new byte[len];
    for (int i = 0; i < len; i++)
      rtArray[i] = src[offset + i]; 
    return rtArray;
  }
  
  private static int strlcpy(byte[] dst, byte[] src, int siz, int dstOffset, int srcOffset) {
    int d = 0;
    int s = 0;
    int n = siz;
    if (n != 0 && --n != 0)
      for (dst[dstOffset + d++] = src[srcOffset + s++]; src[srcOffset + s++] != 0;) {
        if (--n == 0)
          break; 
      }  
    if (n == 0) {
      if (siz != 0)
        dst[dstOffset + d] = 0; 
      while (s < src.length && src[s] != 0)
        s++; 
    } 
    return s - 1;
  }
  
  private static byte[] md5_crypt(byte[] pw, byte[] salt) {
    byte[] passwd = new byte[120];
    int p = 0;
    int sp = 0;
    int ep = 0;
    byte[] finalArray = new byte[16];
    YZMD5Context ctx = new YZMD5Context();
    YZMD5Context ctx1 = new YZMD5Context();
    int pwl = pw.length;
    sp = 0;
    if (strncmp(salt, MD5_MAGIC, sp, 0, 3) == 0)
      sp += 3; 
    for (ep = sp; salt[ep] != 0 && salt[ep] != 36 && ep < sp + 8; ep++);
    int sl = ep - sp;
    PHP_MD5Init(ctx);
    PHP_MD5Update(ctx, pw, pwl);
    PHP_MD5Update(ctx, MD5_MAGIC, 3);
    PHP_MD5Update(ctx, cloneArray(salt, sp, sl), sl);
    PHP_MD5Init(ctx1);
    PHP_MD5Update(ctx1, pw, pwl);
    PHP_MD5Update(ctx1, cloneArray(salt, sp, sl), sl);
    PHP_MD5Update(ctx1, pw, pwl);
    PHP_MD5Final(finalArray, ctx1);
    for (int pl = pwl; pl > 0; pl -= 16)
      PHP_MD5Update(ctx, finalArray, (pl > 16) ? 16 : pl); 
    memset(finalArray, (byte)0);
    int i;
    for (i = pwl; i != 0; i >>>= 1) {
      if ((i & 0x1) != 0) {
        PHP_MD5Update(ctx, finalArray, 1);
      } else {
        PHP_MD5Update(ctx, pw, 1);
      } 
    } 
    memcpy(passwd, MD5_MAGIC, 0, 0, 3);
    strlcpy(passwd, salt, sl + 1, 3, sp);
    passwd[3 + sl] = 36;
    PHP_MD5Final(finalArray, ctx);
    for (i = 0; i < 1000; i++) {
      PHP_MD5Init(ctx1);
      if ((i & 0x1) != 0) {
        PHP_MD5Update(ctx1, pw, pwl);
      } else {
        PHP_MD5Update(ctx1, finalArray, 16);
      } 
      if (i % 3 != 0)
        PHP_MD5Update(ctx1, cloneArray(salt, sp, sl), sl); 
      if (i % 7 != 0)
        PHP_MD5Update(ctx1, pw, pwl); 
      if ((i & 0x1) != 0) {
        PHP_MD5Update(ctx1, finalArray, 16);
      } else {
        PHP_MD5Update(ctx1, pw, pwl);
      } 
      PHP_MD5Final(finalArray, ctx1);
    } 
    p = sl + 3 + 1;
    int l = byte2Int(finalArray[0]) << 16 | byte2Int(finalArray[6]) << 8 | byte2Int(finalArray[12]);
    to64(passwd, p, l, 4);
    p += 4;
    l = byte2Int(finalArray[1]) << 16 | byte2Int(finalArray[7]) << 8 | byte2Int(finalArray[13]);
    to64(passwd, p, l, 4);
    p += 4;
    l = byte2Int(finalArray[2]) << 16 | byte2Int(finalArray[8]) << 8 | byte2Int(finalArray[14]);
    to64(passwd, p, l, 4);
    p += 4;
    l = byte2Int(finalArray[3]) << 16 | byte2Int(finalArray[9]) << 8 | byte2Int(finalArray[15]);
    to64(passwd, p, l, 4);
    p += 4;
    l = byte2Int(finalArray[4]) << 16 | byte2Int(finalArray[10]) << 8 | byte2Int(finalArray[5]);
    to64(passwd, p, l, 4);
    p += 4;
    l = byte2Int(finalArray[11]);
    to64(passwd, p, l, 2);
    p += 2;
    passwd[p] = 0;
    byte[] rtArray = new byte[p];
    memcpy(rtArray, passwd, 0, 0, p);
    return rtArray;
  }
  
  private static byte[] crypt(byte[] pw, byte[] salt) {
    if (salt.length > 3 && strncmp(salt, MD5_MAGIC, 0, 0, 3) == 0)
      return md5_crypt(pw, salt); 
    return null;
  }
  
  private static void php_to64(byte[] s, int v, int n, int sOffset) {
    while (--n >= 0) {
      s[sOffset++] = itoa64[v & 0x3F];
      v >>>= 6;
    } 
  }
  
  private static byte[] crypt(byte[] pw) {
    byte[] salt = new byte[12];
    salt[0] = 36;
    salt[1] = 49;
    salt[2] = 36;
    int rand = 0;
    rand = (int)(Math.random() * 1.0E9D);
    php_to64(salt, rand, 4, 3);
    rand = (int)(Math.random() * 1.0E9D);
    php_to64(salt, rand, 4, 7);
    salt[11] = 36;
    return crypt(pw, salt);
  }
  
  public static String encryptPass(String pass) {
    if (YZSysProps.getString("encryptPassMethod").equalsIgnoreCase("md5"))
      try {
        return YZDigestUtility.md5Hex(pass.getBytes("UTF-8"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return "";
      }  
    byte[] passArray = str2Bytes(pass);
    byte[] cryptPass = crypt(passArray);
    int passLen = cryptPass.length;
    char[] charPass = new char[passLen];
    for (int i = 0; i < passLen; i++)
      charPass[i] = (char)cryptPass[i]; 
    return new String(charPass);
  }
  
  public static boolean isValidPas(String realPass, String encryptPass) {
    if (YZSysProps.getString("encryptPassMethod").equalsIgnoreCase("md5"))
      try {
        String str = YZDigestUtility.md5Hex(realPass.getBytes("UTF-8")).toUpperCase();
        return str.equalsIgnoreCase(encryptPass);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return false;
      }  
    int tmpInt = encryptPass.lastIndexOf("$");
    if (tmpInt < 0)
      return false; 
    byte[] pw = str2Bytes(realPass);
    byte[] salt = str2Bytes(encryptPass.substring(0, tmpInt + 1));
    byte[] srcPassArray = str2Bytes(encryptPass);
    byte[] tmpPass = crypt(pw, salt);
    int len = tmpPass.length;
    if (len != srcPassArray.length)
      return false; 
    for (int i = 0; i < len; i++) {
      if (tmpPass[i] != srcPassArray[i])
        return false; 
    } 
    return true;
  }
}
