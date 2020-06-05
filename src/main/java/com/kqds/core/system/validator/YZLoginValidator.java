package com.kqds.core.system.validator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kqds.entity.sys.YZPerson;

public interface YZLoginValidator {
	public static Logger log = LoggerFactory.getLogger(YZLoginValidator.class);

	public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception;

	public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception;

	public String getValidatorType();

	public int getValidatorCode();

	public String getValidatorMsg();
}
