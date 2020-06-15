package com.kqds.core.system.validator;

import com.kqds.entity.sys.YZPerson;
import javax.servlet.http.HttpServletRequest;

public class YZForbidLoginValidator implements YZLoginValidator {
  public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception {
    return forbidLogin(person);
  }
  
  private boolean forbidLogin(YZPerson person) {
    String notLogin = person.getNotLogin();
    if (notLogin == null)
      notLogin = "0"; 
    return !"1".equals(notLogin.trim());
  }
  
  public int getValidatorCode() {
    return 1;
  }
  
  public String getValidatorType() {
    return "用户禁止登录";
  }
  
  public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception {}
  
  public String getValidatorMsg() {
    return null;
  }
}
