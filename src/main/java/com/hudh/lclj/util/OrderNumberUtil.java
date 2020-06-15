package com.hudh.lclj.util;

import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.lclj.entity.LcljOrderTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class OrderNumberUtil {
  @Autowired
  private static LcljTrackDao lcljTrackDao;
  
  private static String orderNumber;
  
  private static final String orderNumber_Prefix = "ss";
  
  private static final int orderNumber_length = 6;
  
  private static final String beginNumber = "000001";
  
  private static volatile OrderNumberUtil instance;
  
  public static synchronized OrderNumberUtil getInstance() {
    if (instance == null) {
      System.out.println("获取编号对象...");
      instance = new OrderNumberUtil();
      WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
      lcljTrackDao = (LcljTrackDao)wac.getBean(LcljTrackDao.class);
    } 
    return instance;
  }
  
  public static String getNextOrderNumber() throws Exception {
    synchronized (instance) {
      if (orderNumber == null) {
        LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
        lcljOrderTrack = lcljTrackDao.findNextOrderNumber();
        if (lcljOrderTrack == null)
          return "ss000001"; 
        String templeNumber = lcljOrderTrack.getOrderNumber();
        templeNumber = templeNumber.replace("ss", "");
        orderNumber = "ss" + String.format("%06d", new Object[] { Integer.valueOf(Integer.parseInt(templeNumber) + 1) });
        return orderNumber;
      } 
      orderNumber = orderNumber.replace("ss", "");
      orderNumber = "ss" + String.format("%06d", new Object[] { Integer.valueOf(Integer.parseInt(orderNumber) + 1) });
      return orderNumber;
    } 
  }
}
