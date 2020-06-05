package com.kqds.util.sys;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.YZGuid;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.spring.BeanUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

/**
 * 此类存放 用户登录成功后，session赋值相关的信息
 * 
 * @author Administrator
 * 
 */
public class SessionUtil {

	private static Logger logger = LoggerFactory.getLogger(SessionUtil.class);

	private static YZParaLogic paraLogic = (YZParaLogic) BeanUtil.getBean("paraLogic");
	private static YZFkfsLogic fkfsLogic = (YZFkfsLogic) BeanUtil.getBean("fkfsLogic");

	public static String ONLINE_INFO = "ONLINE_INFO"; // 用户登录成功时，构建Online对象，放入session中

	// 缓存登录用户的session ConcurrentHashMap 线程安全 map实现类
	public static Map<String, HttpSession> Session_MAP = new ConcurrentHashMap<String, HttpSession>();

	public static String LOGIN_ORGANIZATION = "LOGIN_ORGANIZATION"; // 存放当前登录的门诊编号
	public static String LOGIN_ORGANIZATION_TELNO = "LOGIN_ORGANIZATION_TELNO"; // 当前登录门诊电话
	public static String LOGIN_ORGANIZATION_ADDRESS = "LOGIN_ORGANIZATION_ADDRESS"; // 当前登录门诊地址
	public static String LOGIN_ORGANIZATION_PRINTNAME = "LOGIN_ORGANIZATION_PRINTNAME"; // 当前门诊打印抬头
	public static String LOGIN_ORGANIZATION_NAME = "LOGIN_ORGANIZATION_NAME"; // 当前登录门诊名称

	public static String visualstaffYyrl = "visualstaffYyrl"; // 预约可见人员
	public static String visualstaff = "visualstaff"; // 可见人员

	public static String LOGIN_SYS_PARA = "LOGIN_SYS_PARA"; // 将系统配置参数，放到session中
	public static String LOGIN_SYS_COSTFIELD = "LOGIN_SYS_COSTFIELD"; // 将项目付款方式，放到session中
	public static String LOGIN_SYS_MEMBERFIELD = "LOGIN_SYS_MEMBERFIELD"; // 将会员卡充值付款方式，放到session中

	public static String SESSION_TOKEN = "SESSION_TOKEN";

	/**
	 * 获取当前登录用户的对象
	 * 
	 * @param request
	 * @return
	 */
	public static YZPerson getLoginPerson(HttpServletRequest request) {
		YZPerson person = (YZPerson) request.getSession().getAttribute(ConstUtil.LOGIN_USER);
		return person;
	}

	// 将系统配置放到session中
	public static void setSysPara4UserLogin(HttpServletRequest request) throws Exception {
		JSONObject sysPara = paraLogic.getSysPara(ChainUtil.getCurrentOrganization(request));
		request.getSession().setAttribute(SessionUtil.LOGIN_SYS_PARA, sysPara);
	}

	// 付款方式放到session中
	public static void getCostField(HttpServletRequest request) throws Exception {
		JSONObject sysPara = fkfsLogic.getCostField();
		request.getSession().setAttribute(SessionUtil.LOGIN_SYS_COSTFIELD, sysPara);
	}

	public static void getMemberField(HttpServletRequest request) throws Exception {
		JSONObject sysPara = fkfsLogic.getMemberField();
		request.getSession().setAttribute(SessionUtil.LOGIN_SYS_MEMBERFIELD, sysPara);
	}

	public static String getVisualstaff(HttpServletRequest request) throws Exception {
		return request.getSession().getAttribute("visualstaff").toString();
	}

	/**
	 * 在session中设置用户信息
	 * 
	 * @param person
	 * @param session
	 */
	public static void setUserInfo4UserLogin(YZPerson person, HttpSession session, String ip, HttpServletRequest request) throws Exception {
		String sessionToken = YZGuid.getRawGuid();
		session.setAttribute("LOGIN_USER", person);
		session.setAttribute(SESSION_TOKEN, sessionToken);
	}

	/**
	 * 用户登录成功后，在session中设置门诊相关信息
	 * 
	 * @param request
	 * @param session
	 * @throws Exception
	 */
	public static void setHosInfo4UserLogin(HttpServletRequest request, HttpSession session, YZPerson person) throws Exception {

		/**
		 * ####################### 设置门诊编号 #############################
		 */
		String organization = null;
		if (ChainUtil.isOpenChain()) { // 连锁功能开启
			organization = request.getParameter("organization");
		} else { // 连锁功能未开启
			organization = YZSysProps.getString(SysParaUtil.ORGANIZATION);
		}
		// 试用版本(查询登录人的门诊编号)
		if (ChainUtil.isOpenTry()) {
			organization = ChainUtil.getOrganizationByPersonSeqId(person.getSeqId(), request);
		}
		session.setAttribute(SessionUtil.LOGIN_ORGANIZATION, organization);
		/**
		 * ####################### 设置门诊打印信息 #############################
		 */
		YZDept dpt = ChainUtil.getOrganizationByOrgCode(organization, request);

		if (!YZUtility.isNullorEmpty(dpt.getTelNo())) { // 当前门诊的联系电话
			session.setAttribute(SessionUtil.LOGIN_ORGANIZATION_TELNO, dpt.getTelNo());
		} else {
			session.setAttribute(SessionUtil.LOGIN_ORGANIZATION_TELNO, "");
		}

		if (!YZUtility.isNullorEmpty(dpt.getDeptAddress())) { // 当前门诊地址
			session.setAttribute(SessionUtil.LOGIN_ORGANIZATION_ADDRESS, dpt.getDeptAddress());
		} else {
			session.setAttribute(SessionUtil.LOGIN_ORGANIZATION_ADDRESS, "");
		}

		if (!YZUtility.isNullorEmpty(dpt.getPrintName())) { // 当前门诊打印抬头
			session.setAttribute(SessionUtil.LOGIN_ORGANIZATION_PRINTNAME, dpt.getPrintName());
		} else {
			session.setAttribute(SessionUtil.LOGIN_ORGANIZATION_PRINTNAME, dpt.getDeptName());
		}

		// 当前门诊名称
		session.setAttribute(SessionUtil.LOGIN_ORGANIZATION_NAME, dpt.getDeptName());

		/**
		 * ####################### 设置门诊打印信息 END #############################
		 */
	}

	/**
	 * 使session失效
	 * 
	 * @param repeatlogin
	 * @param conn
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public static void invalidateSession(HttpSession session) {
		try {
			if (session != null) {
				YZPerson person = (YZPerson) session.getAttribute(ConstUtil.LOGIN_USER);
				SessionUtil.Session_MAP.remove(person.getUserId());
				// 销毁session
				session.invalidate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage()); // 出现这类错误，也不处理，如： invalidate: Session
											// already invalidated
		}

	}

	public static void removeUserFromOnlineMap(String userid) {
		Iterator<String> iterator = SessionUtil.Session_MAP.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (userid.equals(key)) {
				iterator.remove();
			}
		}
	}

	/**
	 * 验证Session的有效性
	 * 
	 * @param session
	 *            用户上下文会话
	 * 
	 * @param key
	 *            检查参数的键名
	 * @return true=在上下文会话中存在以key为名称的对象 false=在上下文会话中不存在以key为名称的对象
	 */
	public static boolean isValidSession(HttpSession session, String key) {
		if (session == null || key == null) {
			return false;
		}
		if (session.getAttribute(key) == null) {
			return false;
		}
		return true;
	}
}
