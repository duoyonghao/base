package com.kqds.core.system.validator;

import javax.servlet.http.HttpServletRequest;

import com.kqds.core.system.common.YZLoginErrorConst;
import com.kqds.entity.sys.YZPerson;

public class YZForbidLoginValidator implements YZLoginValidator {

	public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception {
		return forbidLogin(person);
	}

	/**
	 * 判断是否禁止登录
	 * 
	 * @param person
	 * @return
	 */
	private boolean forbidLogin(YZPerson person) {
		String notLogin = person.getNotLogin();
		if (notLogin == null) {
			notLogin = "0";
		}
		return !"1".equals(notLogin.trim());
	}

	public int getValidatorCode() {
		return YZLoginErrorConst.LOGIN_FORBID_LOGIN_CODE;
	}

	public String getValidatorType() {
		return YZLoginErrorConst.LOGIN_FORBID_LOGIN;
	}

	public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception {

	}

	public String getValidatorMsg() {
		return null;
	}
}
