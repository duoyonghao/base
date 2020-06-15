package com.kqds.util.sys.other;

import java.math.BigDecimal;

public class KqdsBigDecimal
{
  public static BigDecimal add(BigDecimal v1, BigDecimal v2)
  {
    return v1.add(v2);
  }
  
  public static BigDecimal sub(BigDecimal v1, BigDecimal v2)
  {
    return v1.subtract(v2);
  }
  
  public static BigDecimal mul(BigDecimal v1, BigDecimal v2)
  {
    return v1.multiply(v2);
  }
  
  public static BigDecimal div(BigDecimal v1, BigDecimal v2)
  {
    return v1.divide(v2, 3, 4);
  }
  
  public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale)
  {
    if (scale < 0) {
      throw new IllegalArgumentException("保留位数必须是正整数或0");
    }
    return v1.divide(v2, scale, 4);
  }
  
  public static BigDecimal round(BigDecimal v, int scale)
  {
    if (scale < 0) {
      throw new IllegalArgumentException("保留位数必须是正整数或0");
    }
    BigDecimal one = new BigDecimal("1");
    return v.divide(one, scale, 4);
  }
  
  public static int compareTo(BigDecimal v1, BigDecimal v2)
  {
    return v1.compareTo(v2);
  }
}
