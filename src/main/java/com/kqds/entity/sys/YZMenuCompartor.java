package com.kqds.entity.sys;

import java.util.Comparator;

public class YZMenuCompartor
  implements Comparator<YZMenuModel>
{
  public int compare(YZMenuModel m1, YZMenuModel m2)
  {
    return m1.getOrderno().compareTo(m2.getOrderno());
  }
}
