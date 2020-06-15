package com.kqds.core.system.validator;

import com.kqds.entity.sys.YZPerson;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract interface YZLoginValidator
{
  public static final Logger log = LoggerFactory.getLogger(YZLoginValidator.class);
  
  public abstract boolean isValid(HttpServletRequest paramHttpServletRequest, YZPerson paramYZPerson)
    throws Exception;
  
  public abstract void addSysLog(HttpServletRequest paramHttpServletRequest, YZPerson paramYZPerson)
    throws Exception;
  
  public abstract String getValidatorType();
  
  public abstract int getValidatorCode();
  
  public abstract String getValidatorMsg();
}
