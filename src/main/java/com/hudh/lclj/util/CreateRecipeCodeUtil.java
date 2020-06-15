package com.hudh.lclj.util;

import com.kqds.entity.base.KqdsChufang;
import com.kqds.service.base.chufang.KQDS_ChuFangLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CreateRecipeCodeUtil
{
  @Autowired
  private static KQDS_ChuFangLogic kqds_ChuFangLogic;
  private static String orderNumber;
  private static final String orderNumber_Prefix = "HUDHCF";
  private static final int orderNumber_length = 6;
  private static final String beginNumber = "000001";
  private static volatile CreateRecipeCodeUtil instance;
  
  public static synchronized CreateRecipeCodeUtil getInstance()
  {
    if (instance == null)
    {
      instance = new CreateRecipeCodeUtil();
      WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
      kqds_ChuFangLogic = (KQDS_ChuFangLogic)wac.getBean(KQDS_ChuFangLogic.class);
    }
    return instance;
  }
  
  public static String getNextOrderNumber()
    throws Exception
  {
    if (orderNumber == null)
    {
      KqdsChufang kqdsChufang = new KqdsChufang();
      kqdsChufang = kqds_ChuFangLogic.findNextOrderNumber();
      if (kqdsChufang == null) {
        return "HUDHCF000001";
      }
      String templeNumber = kqdsChufang.getRecipecode();
      templeNumber = templeNumber.replace("HUDHCF", "");
      orderNumber = "HUDHCF" + String.format("%06d", new Object[] { Integer.valueOf(Integer.parseInt(templeNumber) + 1) });
      return orderNumber;
    }
    orderNumber = orderNumber.replace("HUDHCF", "");
    orderNumber = "HUDHCF" + String.format("%06d", new Object[] { Integer.valueOf(Integer.parseInt(orderNumber) + 1) });
    return orderNumber;
  }
}
