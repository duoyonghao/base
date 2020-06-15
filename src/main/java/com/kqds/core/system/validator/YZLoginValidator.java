package com.kqds.core.system.validator;

import com.kqds.entity.sys.YZPerson;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface YZLoginValidator {
  public static final Logger log = LoggerFactory.getLogger(YZLoginValidator.class);
  
  boolean isValid(HttpServletRequest paramHttpServletRequest, YZPerson paramYZPerson) throws Exception;
  
  void addSysLog(HttpServletRequest paramHttpServletRequest, YZPerson paramYZPerson) throws Exception;
  
  String getValidatorType();
  
  int getValidatorCode();
  
  String getValidatorMsg();
}
