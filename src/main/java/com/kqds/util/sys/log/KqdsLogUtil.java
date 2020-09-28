package com.kqds.util.sys.log;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.kqds.entity.base.KqdsLog;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.util.UtilLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.spring.BeanUtil;

import net.sf.json.JSONObject;

/**
 * 此类为详细日志记录类
 * 
 * @author Administrator
 * 
 */
public class KqdsLogUtil {

	private static UtilLogic utilLogic = (UtilLogic) BeanUtil.getBean("utilLogic");

	public static int LOG_TYPE_1 = 1; // 请求日志
	public static int LOG_TYPE_2 = 2; // 新增日志【单个】
	public static int LOG_TYPE_3 = 3; // 修改日志【单个】
	public static int LOG_TYPE_4 = 4; // 删除日志【单个】
	public static int LOG_TYPE_5 = 5; // 更新日志【SQL】
	public static int LOG_TYPE_6 = 6; // 删除日志【SQL】
	public static int LOG_TYPE_7 = 7; // 查询日志
	public static int LOG_TYPE_8 = 8; // 异常日志

	/**
	 * 判断对象是否包含usercode属性
	 * 
	 * @param obj
	 * @param log
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void setUserCode(Object obj, KqdsLog log) throws Exception {
		Class cls = obj.getClass();// ### 所有的修改，都不修改门诊编号
		if (ChainUtil.isHasAttributeInClass("usercode", cls)) { // 判断该类中是否有organization属性
			Method getMethod = cls.getMethod("getUsercode", new Class[] {}); // 判断传入的organization是否有值
			String usercode = (String) getMethod.invoke(obj, new Object[] {});
			if (!YZUtility.isNullorEmpty(usercode)) { // 如果没有值
				log.setUserCode(usercode);
			}
		}
	}

	public static void insertRequestLog(HttpServletRequest request) throws Exception {
		String qUri = ((HttpServletRequest) request).getRequestURI();
		if (!qUri.endsWith(".act")) {
			return;
		}
		KqdsLog log = new KqdsLog();
		log.setLogType(LOG_TYPE_1);
		log.setRequestUrl(qUri);

		// 入库
		insetIntoDB(log, request);

	}

	public static void insertSaveLog(Object json, String tableName, HttpServletRequest request) throws Exception {
		KqdsLog log = new KqdsLog();
		log.setLogType(LOG_TYPE_2);

		// 设置usercode
		setUserCode(json, log);

		log.setTableName(tableName);
		log.setInsertObject(JSONObject.fromObject(json).toString());

		// 入库
		insetIntoDB(log, request);

	}

	public static void insertUpdateLog(Object beforeObj, Object afterObj, String tableName, HttpServletRequest request) throws Exception {
		KqdsLog log = new KqdsLog();
		log.setLogType(LOG_TYPE_3);

		// 设置usercode
		setUserCode(afterObj, log);

		log.setTableName(tableName);
		log.setBeforeObject(JSONObject.fromObject(beforeObj).toString());
		log.setAfterObject(JSONObject.fromObject(afterObj).toString());

		// 入库
		insetIntoDB(log, request);

	}

	public static void insertDeleteLog(Object json, String tableName, HttpServletRequest request) throws Exception {
		KqdsLog log = new KqdsLog();
		log.setLogType(LOG_TYPE_4);
		// 设置usercode
		setUserCode(json, log);
		log.setTableName(tableName);
		log.setDeleteObject(JSONObject.fromObject(json).toString());
		// 入库
		insetIntoDB(log, request);
	}

	public static void insertUpdateSQL(String updateSql, HttpServletRequest request) throws Exception {
		KqdsLog log = new KqdsLog();
		log.setLogType(LOG_TYPE_5);
		log.setUpdateSql(updateSql);
		// 入库
		insetIntoDB(log, request);

	}

	public static void insertDeleteSQL(String deleteSql, HttpServletRequest request) throws Exception {
		KqdsLog log = new KqdsLog();
		log.setLogType(LOG_TYPE_6);
		log.setDeleteSql(deleteSql);
		// 入库
		insetIntoDB(log, request);
	}

	/**
	 * 【内部调用】
	 * 
	 * @throws Exception
	 */
	private static void insetIntoDB(KqdsLog log, HttpServletRequest request) throws Exception {
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
