package com.kqds.util.sys.log;

import com.kqds.entity.base.KqdsLog;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.util.UtilLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.spring.BeanUtil;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;

public class KqdsLogUtil
{
  private static UtilLogic utilLogic = (UtilLogic)BeanUtil.getBean("utilLogic");
  public static int LOG_TYPE_1 = 1;
  public static int LOG_TYPE_2 = 2;
  public static int LOG_TYPE_3 = 3;
  public static int LOG_TYPE_4 = 4;
  public static int LOG_TYPE_5 = 5;
  public static int LOG_TYPE_6 = 6;
  public static int LOG_TYPE_7 = 7;
  public static int LOG_TYPE_8 = 8;
  
  private static void setUserCode(Object obj, KqdsLog log)
    throws Exception
  {
    Class cls = obj.getClass();
    if (ChainUtil.isHasAttributeInClass("usercode", cls))
    {
      Method getMethod = cls.getMethod("getUsercode", new Class[0]);
      String usercode = (String)getMethod.invoke(obj, new Object[0]);
      if (!YZUtility.isNullorEmpty(usercode)) {
        log.setUserCode(usercode);
      }
    }
  }
  
  public static void insertRequestLog(HttpServletRequest request)
    throws Exception
  {
    String qUri = request.getRequestURI();
    if (!qUri.endsWith(".act")) {
      return;
    }
    KqdsLog log = new KqdsLog();
    log.setLogType(Integer.valueOf(LOG_TYPE_1));
    log.setRequestUrl(qUri);
    

    insetIntoDB(log, request);
  }
  
  public static void insertSaveLog(Object json, String tableName, HttpServletRequest request)
    throws Exception
  {
    KqdsLog log = new KqdsLog();
    log.setLogType(Integer.valueOf(LOG_TYPE_2));
    

    setUserCode(json, log);
    
    log.setTableName(tableName);
    log.setInsertObject(JSONObject.fromObject(json).toString());
    

    insetIntoDB(log, request);
  }
  
  public static void insertUpdateLog(Object beforeObj, Object afterObj, String tableName, HttpServletRequest request)
    throws Exception
  {
    KqdsLog log = new KqdsLog();
    log.setLogType(Integer.valueOf(LOG_TYPE_3));
    

    setUserCode(afterObj, log);
    
    log.setTableName(tableName);
    log.setBeforeObject(JSONObject.fromObject(beforeObj).toString());
    log.setAfterObject(JSONObject.fromObject(afterObj).toString());
    

    insetIntoDB(log, request);
  }
  
  public static void insertDeleteLog(Object json, String tableName, HttpServletRequest request)
    throws Exception
  {
    KqdsLog log = new KqdsLog();
    log.setLogType(Integer.valueOf(LOG_TYPE_4));
    
    setUserCode(json, log);
    log.setTableName(tableName);
    log.setDeleteObject(JSONObject.fromObject(json).toString());
    
    insetIntoDB(log, request);
  }
  
  public static void insertUpdateSQL(String updateSql, HttpServletRequest request)
    throws Exception
  {
    KqdsLog log = new KqdsLog();
    log.setLogType(Integer.valueOf(LOG_TYPE_5));
    log.setUpdateSql(updateSql);
    
    insetIntoDB(log, request);
  }
  
  public static void insertDeleteSQL(String deleteSql, HttpServletRequest request)
    throws Exception
  {
    KqdsLog log = new KqdsLog();
    log.setLogType(Integer.valueOf(LOG_TYPE_6));
    log.setDeleteSql(deleteSql);
    
    insetIntoDB(log, request);
  }
  
  private static void insetIntoDB(KqdsLog log, HttpServletRequest request)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    
    String sessiontokenStr = null;
    Object sessiontoken = request.getSession().getAttribute(SessionUtil.SESSION_TOKEN);
    if (sessiontoken != null) {
      sessiontokenStr = String.valueOf(sessiontoken);
    }
    String createUser = person == null ? "" : person.getSeqId();
    Object requesttoken = request.getAttribute(ConstUtil.REQUEST_LOG_UUID);
    String requesttokenStr = "";
    if (requesttoken != null) {
      requesttokenStr = String.valueOf(requesttoken);
    }
    log.setSeqId(YZUtility.getUUID());
    log.setRequesttoken(requesttokenStr);
    log.setCreateuser(createUser);
    log.setCreatetime(YZUtility.getCurDateTimeStr());
    log.setSessiontoken(sessiontokenStr);
    
    utilLogic.saveSingleUUID(TableNameUtil.KQDS_LOG, log);
  }
}
