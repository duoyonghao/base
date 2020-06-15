package com.kqds.core.system.validator;

import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.log.SysLogUtil;
import javax.servlet.http.HttpServletRequest;

public class YZIsLeaveUserValidator
  implements YZLoginValidator
{
  public boolean isValid(HttpServletRequest request, YZPerson person)
    throws Exception
  {
    return 1 != person.getIsLeave().intValue();
  }
  
  public String getValidatorType()
  {
    return "该用户已离职";
  }
  
  public void addSysLog(HttpServletRequest request, YZPerson person)
    throws Exception
  {
    SysLogUtil.log("已经离职", SysLogUtil.SYS_PERSON, "用户已离职", TableNameUtil.SYS_PERSON, request);
  }
  
  public int getValidatorCode()
  {
    return 20;
  }
  
  public String getValidatorMsg()
  {
    return null;
  }
}
