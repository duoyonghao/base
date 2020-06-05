package com.kqds.core.system.validator;

import javax.servlet.http.HttpServletRequest;

import com.kqds.core.system.common.YZLoginErrorConst;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.log.SysLogUtil;

public class YZIsLeaveUserValidator implements YZLoginValidator {

	public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception {
		return !(1 == person.getIsLeave()); // 用户isLeave 不等于1时，验证通过
	}

	public String getValidatorType() {
		return YZLoginErrorConst.IS_LEAVE_CODE_ERROR;
	}

	public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception {
		SysLogUtil.log(SysLogUtil.IS_LEAVE, SysLogUtil.SYS_PERSON, "用户已离职", TableNameUtil.SYS_PERSON, request);
	}

	public int getValidatorCode() {
		return YZLoginErrorConst.IS_LEAVE_CODE;
	}

	public String getValidatorMsg() {
		return null;
	}

}
