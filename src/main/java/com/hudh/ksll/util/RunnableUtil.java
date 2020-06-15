package com.hudh.ksll.util;

import com.hudh.ksll.entity.KsllCollorDetail;
import java.util.List;

public class RunnableUtil implements Runnable {
  private List<KsllCollorDetail> ksllCollorDetailList;
  
  public void setKsllCollorDetailList(List<KsllCollorDetail> ksllCollorDetailList) {
    this.ksllCollorDetailList = ksllCollorDetailList;
  }
  
  public void run() {
    try {
      KsllCollerDetailDaoUtil.save(this.ksllCollorDetailList);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
