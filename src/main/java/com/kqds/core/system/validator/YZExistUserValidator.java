package com.kqds.core.system.validator;

import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.log.SysLogUtil;
import javax.servlet.http.HttpServletRequest;

public class YZExistUserValidator
  implements YZLoginValidator
{
  public boolean isValid(HttpServletRequest request, YZPerson person)
    throws Exception
  {
    return person != null;
  }
  
  public String getValidatorType()
  {
    return "不存在用户";
  }
  
  public void addSysLog(HttpServletRequest request, YZPerson person)
    throws Exception
  {
    SysLogUtil.log("错误用户名", SysLogUtil.SYS_PERSON, "用户不存在", TableNameUtil.SYS_PERSON, request);
  }
  
  public int getValidatorCode()
  {
    return 15;
  }
  
  public String getValidatorMsg()
  {
    return "不存在用户";
  }
}
