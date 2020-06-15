package com.kqds.core.system.validator;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.util.sys.spring.BeanUtil;
import javax.servlet.http.HttpServletRequest;

public class YZRepeatLoginValidator implements YZLoginValidator {
  public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception {}
  
  public int getValidatorCode() {
    return 9;
  }
  
  public String getValidatorMsg() {
    return null;
  }
  
  public String getValidatorType() {
    return "用户已经登录,无法重复登录";
  }
  
  public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception {
    YZOnlineLogic onlineLogic = (YZOnlineLogic)BeanUtil.getBean("onlineLogic");
    String mulLogin = YZSysProps.getString("ONE_USER_MUL_LOGIN");
    if (mulLogin == null)
      mulLogin = "1"; 
    if ("0".equals(mulLogin))
      return !onlineLogic.isLogin(person.getUserId()); 
    return true;
  }
}
