package com.kqds.core.system.validator;

import javax.servlet.http.HttpServletRequest;

import com.kqds.core.system.common.YZLoginErrorConst;
import com.kqds.core.util.auth.YZPassEncrypt;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.log.SysLogUtil;

public class YZPasswordValidator implements YZLoginValidator {
	String isSecureCard = "0";
	String returnValue = "0";

	public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception {
		String pwd = request.getParameter("pwd");

		if (person.getPassword() == null) {
			person.setPassword("");
		}

		if (person != null && YZPassEncrypt.isValidPas(pwd, person.getPassword().trim())) {
			return true;
		} else {
			return false;
		}
	}

	public int getValidatorCode() {
		return YZLoginErrorConst.LOGIN_PASSWORD_ERROR_CODE;
	}

	public String getValidatorType() {
		return YZLoginErrorConst.LOGIN_PASSWORD_ERROR;
	}

	public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception {
		// 系统日志-登录密码错误
		SysLogUtil.log(SysLogUtil.LOGIN_PASSWORD_ERROR, SysLogUtil.SYS_PERSON, "登录密码错误", TableNameUtil.SYS_PERSON, request);
	}

	public String getValidatorMsg() {
		// TODO Auto-generated method stub
		return YZLoginErrorConst.LOGIN_PASSWORD_ERROR;
	}

}
