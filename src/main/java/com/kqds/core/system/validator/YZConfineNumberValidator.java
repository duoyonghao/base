package com.kqds.core.system.validator;

import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.spring.BeanUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import javax.servlet.http.HttpServletRequest;

public class YZConfineNumberValidator implements YZLoginValidator {
  public boolean isValid(HttpServletRequest request, YZPerson person) throws Exception {
    YZParaLogic paraLogic = (YZParaLogic)BeanUtil.getBean("paraLogic");
    YZOnlineLogic onlineLogic = (YZOnlineLogic)BeanUtil.getBean("onlineLogic");
    String confineStr = paraLogic.getParaValueByName(SysParaUtil.YZ_CONFINE_NUMBER);
    if (YZUtility.isNullorEmpty(confineStr))
      throw new Exception("未配置端口数限制参数！"); 
    int confine = Integer.parseInt(confineStr);
    int count = onlineLogic.queryUserCount();
    boolean isLogin = onlineLogic.isLogin(person.getUserId());
    if (isLogin || confine > count)
      return true; 
    return false;
  }
  
  public String getValidatorType() {
    return "超过最大端口数";
  }
  
  public void addSysLog(HttpServletRequest request, YZPerson person) throws Exception {
    SysLogUtil.log("超过最大端口数", SysLogUtil.SYS_PERSON, "超过最大端口数", TableNameUtil.SYS_PERSON, request);
  }
  
  public int getValidatorCode() {
    return 25;
  }
  
  public String getValidatorMsg() {
    return "超过最大端口数";
  }
}
