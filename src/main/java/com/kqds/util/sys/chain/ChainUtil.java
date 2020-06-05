package com.kqds.util.sys.chain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.spring.BeanUtil;
import com.kqds.util.sys.sys.SysParaUtil;

public class ChainUtil {

	private static Logger log = LoggerFactory.getLogger(ChainUtil.class);
	private static YZDeptLogic deptLogic = (YZDeptLogic) BeanUtil.getBean("deptLogic");

	/**
	 * 获取门诊列表
	 * 
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	public static List<YZDept> getHosList(String organization) throws Exception {
		List<YZDept> list = deptLogic.getHosList(organization);
		return list;
	}

	/**
	 * 判断系统是否开启连锁功能 false 未开启 true 开启
	 * 
	 * @return
	 */
	public static boolean isOpenChain() {
		String is_open_chain_func = YZSysProps.getProp(SysParaUtil.IS_OPEN_CHAIN_FUNC);
		if (YZUtility.isNullorEmpty(is_open_chain_func)) {
			return false;
		}

		if (ConstUtil.IS_OPEN_CHAIN_FUNC_1.equals(is_open_chain_func)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否开启试用
	 * 
	 * @param conn
	 * @param request
	 * @return
	 */
	public static boolean isOpenTry() {
		String is_open_chain_select = YZSysProps.getProp(SysParaUtil.IS_OPEN_CHAIN_SELECT);
		if (YZUtility.isNullorEmpty(is_open_chain_select)) {
			return false;
		}

		if (ConstUtil.IS_OPEN_TRY_FUNC_1.equals(is_open_chain_select)) { // 1
																			// 开启试用
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断某个类中，是否存在指定属性
	 * 
	 * @param cls
	 * @param field
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isHasAttributeInClass(String fieldname, Class cls) {
		/**
		 * 循环遍历所有的元素，检测有没有这个名字
		 */
		Field[] fields = cls.getDeclaredFields();

		boolean flag = false;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(fieldname)) {
				flag = true;
				break;
			}
		}
		return flag;

	}

	public static void main(String[] args) {
		String sql = "select * from a where 1=1 aa";

		String[] sqlArray = sql.split("1=1");

		if (sqlArray.length == 2) {

			// for (String string : sqlArray) {
			// System.out.println(string);
			// }

		}
	}

	/**
	 * 给查询sql增加连锁过滤条件
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String addSqlHosCodeFilter4Delete(String sql, Class cls, HttpServletRequest request) throws Exception {
		return addSqlHosCodeFilter(sql, cls, request);
	}

	/**
	 * 给查询sql增加连锁过滤条件
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String addSqlHosCodeFilter4Query(String sql, Class cls, HttpServletRequest request) throws Exception {
		return addSqlHosCodeFilter(sql, cls, request);
	}

	/**
	 * 给查询sql增加连锁过滤条件
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String addSqlHosCodeFilter4Update(String sql, Class cls, HttpServletRequest request) throws Exception {
		return addSqlHosCodeFilter(sql, cls, request);

	}

	/**
	 * 给查询sql增加连锁过滤条件
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static String addSqlHosCodeFilter(String sql, Class cls, HttpServletRequest request) throws Exception {

		String organization = request.getParameter("organization");

		if (YZUtility.isNullorEmpty(organization)) {
			return sql;
		}

		if (!isHasAttributeInClass("organization", cls)) {
			return sql;
		}

		if (!sql.contains("1=1")) {
			return sql;
		}

		StringBuffer sqlNew = new StringBuffer(""); // where 1=1
		String[] sqlArray = sql.split("1=1");

		if (sqlArray.length == 1) {
			sqlNew.append(sqlArray[0]);
			sqlNew.append(" 1=1 ");
			sqlNew.append(" and organization = '" + organization + "' ");

		}

		else if (sqlArray.length == 2) {
			sqlNew.append(sqlArray[0]);
			sqlNew.append(" 1=1 ");
			sqlNew.append(" and organization = '" + organization + "' ");
			sqlNew.append(sqlArray[1]);

		} else {
			// SQL中只允许存在一个 1=1
			throw new Exception("SQL语句错误，只允许出现一次 1=1");
		}

		log.error("### SQL IN ### " + sql);
		log.error("### SQL OUT ### " + sqlNew.toString());

		return sqlNew.toString();

	}

	/**
	 * 给url添加门诊编号参数
	 * 
	 * @param url
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String addUrlChainFilter(String url, HttpServletRequest request) throws Exception {
		String organization = request.getParameter("organization");

		if (YZUtility.isNullorEmpty(organization)) {
			return url;
		}

		return url + "?organization=" + organization;
	}

	/**
	 * 获取url方式传值的organization
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getOrganizationFromUrl(HttpServletRequest request) throws Exception {
		String organization = request.getParameter("organization");

		if (YZUtility.isNullorEmpty(organization)) {
			// System.out.println("###连锁模式下，organization参数值为空，请求Url为：" +
			// request.getRequestURL());
			log.error("###连锁模式下，organization参数值为空，请求Url为：" + request.getRequestURL());
			throw new Exception("###连锁模式下，organization参数值为空，请求Url为：" + request.getRequestURL());
		}

		return organization;
	}

	/**
	 * 获取url方式传值的organization，允许为空
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getOrganizationFromUrlCanNull(HttpServletRequest request) throws Exception {
		String organization = request.getParameter("organization");

		if (YZUtility.isNullorEmpty(organization)) {
			return "";
		}

		return organization;
	}

	/**
	 * 获取当前所在的门诊
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentOrganization(HttpServletRequest request) throws Exception {

		String organization = (String) request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION);

		if (YZUtility.isNullorEmpty(organization)) {
			// System.out.println("###连锁模式下，session中organization参数值为空。");
			log.error("###连锁模式下，session中organization参数值为空。");
			/** 这里不抛异常，因为微信约约情况下，没有此值 ys 17-9-27 **/
			// throw new Exception("###连锁模式下，session中organization参数值为空。");
		}

		return organization;
	}

	/**
	 * 从session中直接获取
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentOrganization(HttpSession session) throws Exception {
		String organization = (String) session.getAttribute(SessionUtil.LOGIN_ORGANIZATION);
		if (YZUtility.isNullorEmpty(organization)) {
			log.error("###session中organization参数值为空。");
			throw new Exception("###session中organization参数值为空。");
		}

		return organization;
	}

	/**
	 * 获取当前所在的门诊的名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentOrganizationName(HttpServletRequest request) throws Exception {

		String organization = (String) request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION);

		if (YZUtility.isNullorEmpty(organization)) {
			// System.out.println("###连锁模式下，session中organization参数值为空。");
			log.error("###连锁模式下，session中organization参数值为空。");
			throw new Exception("###连锁模式下，session中organization参数值为空。");
		}

		String organizationName = getOrganizationNameByOrgCode(organization, request);
		return organizationName;

	}

	/**
	 * 获取所在门诊名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getOrganizationNameFromUrl(HttpServletRequest request) throws Exception {
		String organization = request.getParameter("organization");
		if (YZUtility.isNullorEmpty(organization)) {
			// System.out.println("###连锁模式下，organization参数值为空，请求Url为：" +
			// request.getRequestURL());
			// log.error("###连锁模式下，organization参数值为空，请求Url为：" +
			// request.getRequestURL());
			// throw new Exception("###连锁模式下，organization参数值为空，请求Url为：" +
			// request.getRequestURL());
			// organization = (String)
			// request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION);
			return "公用";
		}

		String organizationName = getOrganizationNameByOrgCode(organization, request);
		return organizationName;

	}

	/**
	 * 根据编号获取门诊名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String getOrganizationNameByOrgCode(String organization, HttpServletRequest request) throws Exception {
		String organizationName = "";

		Map<String, String> filters = new HashMap<String, String>();
		filters.put("DEPT_PARENT", "0");
		filters.put("DEPT_CODE", organization);

		List<YZDept> list = (ArrayList<YZDept>) deptLogic.loadList(TableNameUtil.SYS_DEPT, filters);

		if (list == null || list.size() == 0) {
			throw new Exception("###连锁模式下，门诊名称获取失败，门诊编号为：" + organization);
		}

		if (list.size() > 1) {
			throw new Exception("###连锁模式下，一级门诊编号出现多个，门诊编号为：" + organization);
		}

		organizationName = list.get(0).getDeptName();

		return organizationName;

	}

	/**
	 * 根据编号获取门诊对象
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static YZDept getOrganizationByOrgCode(String organization, HttpServletRequest request) throws Exception {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("DEPT_PARENT", "0");
		filter.put("DEPT_CODE", organization);

		List<YZDept> list = (ArrayList<YZDept>) deptLogic.loadList(TableNameUtil.SYS_DEPT, filter);

		if (list == null || list.size() == 0) {
			throw new Exception("###连锁模式下，门诊名称获取失败，门诊编号为：" + organization);
		}

		if (list.size() > 1) {
			throw new Exception("###连锁模式下，一级门诊编号出现多个，门诊编号为：" + organization);
		}

		return list.get(0);

	}

	/**
	 * 根据当前登录用户，获取其所在的门诊名称
	 * 
	 * @param person
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getOrganizationNameByPerson(YZPerson person, HttpServletRequest request) throws Exception {

		String deptId = person.getDeptId();

		YZDept dpt = deptLogic.getTopDept(deptId);

		if (!YZUtility.isNullorEmpty(dpt.getDeptByname())) {
			return dpt.getDeptByname();
		}

		return dpt.getDeptName();

	}

	/**
	 * 根据用户ID获取用户所在门诊编号 用于同一个门诊，不允许编辑咨询人员
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static String getOrganizationByPersonSeqId(String userSeqId, HttpServletRequest request) throws Exception {
		String organization = null;
		try {
			YZPerson person = (YZPerson) deptLogic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, userSeqId);

			if (person == null) {
				throw new Exception("系统用户不存在");
			}

			String deptId = person.getDeptId();

			YZDept dpt = deptLogic.getTopDept(deptId);

			if (dpt == null) {
				throw new Exception("系统用户所在门诊不存在");
			}

			organization = dpt.getDeptCode();
		} catch (Exception ex) {
			throw ex;
		}
		return organization;
	}

	/**
	 * 根据当前登录用户，获取其所在的部门名称
	 * 
	 * @param person
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getDeptNameByPerson(YZPerson person, HttpServletRequest request) throws Exception {
		String deptId = person.getDeptId();

		YZDept dpt = (YZDept) deptLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, deptId);

		if (dpt != null) {
			return dpt.getDeptName();
		}

		return "";
	}

	/**
	 * #############################打印名称相关
	 */
	// 当前门诊名称
	public static String getOrgName(HttpServletRequest request) {
		String returnStr = request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION_NAME) + "";
		return returnStr;
	}

	// 用于打印的门诊抬头
	public static String getOrgPrintName(HttpServletRequest request) {
		String returnStr = request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION_PRINTNAME) + "";
		return returnStr;
	}

	// 门诊地址
	public static String getOrgAddress(HttpServletRequest request) {
		String returnStr = request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION_ADDRESS) + "";
		return returnStr;
	}

	// 门诊电话
	public static String getOrgTelNo(HttpServletRequest request) {
		String returnStr = request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION_TELNO) + "";
		return returnStr;
	}
	/**
	 * #############################打印名称相关 END ...
	 */

}
