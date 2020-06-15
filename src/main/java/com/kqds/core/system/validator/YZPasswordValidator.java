package com.kqds.core.system.validator;

import com.kqds.core.util.auth.YZPassEncrypt;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.log.SysLogUtil;
import javax.servlet.http.HttpServletRequest;

public class YZPasswordValidator
  implements YZLoginValidator
{
  String isSecureCard = "0";
  String returnValue = "0";
  
  public boolean isValid(HttpServletRequest request, YZPerson person)
    throws Exception
  {
    String pwd = request.getParameter("pwd");
    if (person.getPassword() == null) {
      person.setPassword("");
    }
    if ((person != null) && (YZPassEncrypt.isValidPas(pwd, person.getPassword().trim()))) {
      return true;
    }
    return false;
  }
  
  public int getValidatorCode()
  {
    return 5;
  }
  
  public String getValidatorType()
  {
    return "密码错误";
  }
  
  public void addSysLog(HttpServletRequest request, YZPerson person)
    throws Exception
  {
    SysLogUtil.log("登录密码错误", SysLogUtil.SYS_PERSON, "登录密码错误", TableNameUtil.SYS_PERSON, request);
  }
  
  public String getValidatorMsg()
  {
    return "密码错误";
  }
}
