package com.kqds.core.system.validator;

import javax.servlet.http.HttpServletRequest;

import com.kqds.core.system.common.YZLoginErrorConst;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.log.SysLogUtil;

public class YZExistUserValidator implements YZLoginValidator {

	public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception {
		return person != null; // true 验证通过 false 验证不通过
	}

	public String getValidatorType() {
		return YZLoginErrorConst.LOGIN_NOTEXIST_USER;
	}

	public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception {
		// 系统日志-用户名错误
		SysLogUtil.log(SysLogUtil.LOGIN_USERID_ERROR, SysLogUtil.SYS_PERSON, "用户不存在", TableNameUtil.SYS_PERSON, request);
	}

	public int getValidatorCode() {
		return YZLoginErrorConst.LOGIN_NOTEXIST_USER_CODE;
	}

	public String getValidatorMsg() {
		return YZLoginErrorConst.LOGIN_NOTEXIST_USER;
	}

}
