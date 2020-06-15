package com.hudh.ykzz.util;

import com.hudh.ykzz.dao.IYkzzDrugsDao;
import com.hudh.ykzz.entity.YkzzDrugs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CreateOrderNoUtil {
  @Autowired
  private static IYkzzDrugsDao iyYkzzDrugsDao;
  
  private static String orderNumber;
  
  private static final String orderNumber_Prefix = "DRUG";
  
  private static final int orderNumber_length = 6;
  
  private static final String beginNumber = "000001";
  
  private static volatile CreateOrderNoUtil instance;
  
  public static synchronized CreateOrderNoUtil getInstance() {
    if (instance == null) {
      instance = new CreateOrderNoUtil();
      WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
      iyYkzzDrugsDao = (IYkzzDrugsDao)wac.getBean(IYkzzDrugsDao.class);
    } 
    return instance;
  }
  
  public static String getNextOrderNumber() throws Exception {
    if (orderNumber == null) {
      YkzzDrugs ykzzDrugs = new YkzzDrugs();
      ykzzDrugs = iyYkzzDrugsDao.findNextOrderNumber();
      if (ykzzDrugs == null)
        return "DRUG000001"; 
      String templeNumber = ykzzDrugs.getOrder_no();
      templeNumber = templeNumber.replace("DRUG", "");
      orderNumber = "DRUG" + String.format("%06d", new Object[] { Integer.valueOf(Integer.parseInt(templeNumber) + 1) });
      return orderNumber;
    } 
    orderNumber = orderNumber.replace("DRUG", "");
    orderNumber = "DRUG" + String.format("%06d", new Object[] { Integer.valueOf(Integer.parseInt(orderNumber) + 1) });
    return orderNumber;
  }
}
