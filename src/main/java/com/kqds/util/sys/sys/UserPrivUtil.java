package com.kqds.util.sys.sys;

import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.spring.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserPrivUtil
{
  public static YZPrivLogic logic = (YZPrivLogic)BeanUtil.getBean("userPrivLogic");
  public static List<String> userQxNameList = new ArrayList();
  public static String ADMIN_SEQ_ID = "1";
  public static String qxFlag0_maxDiscount = "maxDiscount";
  public static String qxFlag1_canLookPhone = "canLookPhone";
  public static String qxFlag2_canKd = "canKd";
  public static String qxFlag3_canHj = "canHj";
  public static String qxFlag4_canPaiban = "canPaiban";
  public static String qxFlag5_canRoom = "canRoom";
  public static String qxFlag6_canOrderOther = "canOrderOther";
  public static String qxFlag7_canEditKf = "canEditKf";
  public static String qxFlag8_canEditWd = "canEditWd";
  public static String qxFlag9_canEditCost = "canEditCost";
  public static String qxFlag10_canEditOrder = "canEditOrder";
  public static String qxFlag11_iszj = "iszj";
  public static String qxFlag12_canEditJdr = "canEditJdr";
  public static String qxFlag13_canEditAskperson = "canEditAskperson";
  public static String qxFlag14_canExportPhoneNumber = "canExportPhoneNumber";
  public static String qxFlag15_canEditPhone = "canEditPhone";
  public static String qxFlag16_canEditHzly = "canEditHzly";
  public static String qxFlag17_canZheKouOnly = "canZheKouOnly";
  public static String qxFlag18_canDelCk = "canDelCk";
  public static String qxFlag19_maxVoidmoney = "maxVoidmoney";
  public static String qxFlag20_canModRoom = "canModRoom";
  
  static
  {
    userQxNameList.add(qxFlag0_maxDiscount);
    userQxNameList.add(qxFlag1_canLookPhone);
    userQxNameList.add(qxFlag2_canKd);
    userQxNameList.add(qxFlag3_canHj);
    userQxNameList.add(qxFlag4_canPaiban);
    userQxNameList.add(qxFlag5_canRoom);
    userQxNameList.add(qxFlag6_canOrderOther);
    userQxNameList.add(qxFlag7_canEditKf);
    userQxNameList.add(qxFlag8_canEditWd);
    userQxNameList.add(qxFlag9_canEditCost);
    userQxNameList.add(qxFlag10_canEditOrder);
    userQxNameList.add(qxFlag11_iszj);
    
    userQxNameList.add(qxFlag12_canEditJdr);
    userQxNameList.add(qxFlag13_canEditAskperson);
    userQxNameList.add(qxFlag14_canExportPhoneNumber);
    userQxNameList.add(qxFlag15_canEditPhone);
    userQxNameList.add(qxFlag16_canEditHzly);
    userQxNameList.add(qxFlag17_canZheKouOnly);
    userQxNameList.add(qxFlag18_canDelCk);
    userQxNameList.add(qxFlag19_maxVoidmoney);
    userQxNameList.add(qxFlag20_canModRoom);
  }
  
  public static String getPrivValueByKey(String privKey, HttpServletRequest request)
    throws Exception
  {
    int index = 0;
    for (String key : userQxNameList)
    {
      if (key.equals(privKey)) {
        break;
      }
      index++;
    }
    YZPriv userPriv = (YZPriv)request.getSession().getAttribute(ConstUtil.LOGIN_USER_PRIV);
    String[] privArray = logic.preCheckInit(userPriv, request);
    String privValue = privArray[index];
    privValue = privValue.trim();
    if ((qxFlag19_maxVoidmoney.equals(privKey)) && (YZUtility.isNullorEmpty(privValue))) {
      privValue = "0";
    }
    return privValue;
  }
  
  public static void main(String[] args) {}
}
