package com.kqds.core.system.validator;

import javax.servlet.http.HttpServletRequest;

import com.kqds.core.system.common.YZLoginErrorConst;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.spring.BeanUtil;
import com.kqds.util.sys.sys.SysParaUtil;

public class YZConfineNumberValidator implements YZLoginValidator {
	public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception {
		YZParaLogic paraLogic = (YZParaLogic) BeanUtil.getBean("paraLogic");
		YZOnlineLogic onlineLogic = (YZOnlineLogic) BeanUtil.getBean("onlineLogic");

		// 允许最大端口数
		String confineStr = paraLogic.getParaValueByName(SysParaUtil.YZ_CONFINE_NUMBER);
		if (YZUtility.isNullorEmpty(confineStr)) {
			throw new Exception("未配置端口数限制参数！");
		}

		int confine = Integer.parseInt(confineStr);
		// 已登录端口数
		int count = onlineLogic.queryUserCount();
		// 判断要登录的用户是否已经登录
		boolean isLogin = onlineLogic.isLogin(person.getUserId());
		if (isLogin || (confine > count)) {
			return true;
		}
		return false; // true 验证通过 false 验证不通过
	}

	public String getValidatorType() {
		return YZLoginErrorConst.MORE_THAN_CONFINE_ERROR;
	}

	public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception {
		SysLogUtil.log(SysLogUtil.MORE_THAN_CONFINE, SysLogUtil.SYS_PERSON, "超过最大端口数", TableNameUtil.SYS_PERSON, request);
	}

	public int getValidatorCode() {
		return YZLoginErrorConst.MORE_THAN_CONFINE_CODE;
	}

	public String getValidatorMsg() {
		return YZLoginErrorConst.MORE_THAN_CONFINE_ERROR;
	}

}
