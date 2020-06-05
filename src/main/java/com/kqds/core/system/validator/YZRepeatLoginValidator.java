package com.kqds.core.system.validator;

import javax.servlet.http.HttpServletRequest;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.system.common.YZLoginErrorConst;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.util.sys.spring.BeanUtil;

public class YZRepeatLoginValidator implements YZLoginValidator {

	public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception {
		// TODO Auto-generated method stub

	}

	public int getValidatorCode() {
		// TODO Auto-generated method stub
		return YZLoginErrorConst.REPEAT_LOGIN_ERROR_CODE;
	}

	public String getValidatorMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getValidatorType() {
		// TODO Auto-generated method stub
		return YZLoginErrorConst.REPEAT_LOGIN_ERROR;
	}

	public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception {
		YZOnlineLogic onlineLogic = (YZOnlineLogic) BeanUtil.getBean("onlineLogic");

		// # ONE_USER_MUL_LOGIN 是否允许多人用同一帐号同时登录 --//1 允许; 0 禁止;
		String mulLogin = YZSysProps.getString("ONE_USER_MUL_LOGIN");

		if (mulLogin == null) { // 如果没配置，则表示允许多人用同一帐号同时登录
			mulLogin = "1";
		}

		if ("0".equals(mulLogin)) {
			// logic.isLogin 返回值为true 表示该用户已在别处登录
			return !onlineLogic.isLogin(person.getUserId());
		}
		return true;
	}

}
