package com.kqds.controller.sys.system;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.core.system.adapter.YZLoginAdapter;
import com.kqds.core.system.common.YZLoginErrorConst;
import com.kqds.core.system.validator.YZConfineNumberValidator;
import com.kqds.core.system.validator.YZExistUserValidator;
import com.kqds.core.system.validator.YZForbidLoginValidator;
import com.kqds.core.system.validator.YZIsLeaveUserValidator;
import com.kqds.core.system.validator.YZPasswordValidator;
import com.kqds.core.system.validator.YZRepeatLoginValidator;
import com.kqds.core.util.auth.YZPassEncrypt;
import com.kqds.entity.sys.QueryFirstLog;
import com.kqds.entity.sys.YZButton;
import com.kqds.entity.sys.YZMenuModel;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.button.YZButtonLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.system.YZSystemLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/YZSystemAct")
public class YZSystemAct {
	private Logger logger = LoggerFactory.getLogger(YZSystemAct.class);

	// 系统业务逻辑处理类
	@Autowired
	private YZSystemLogic sysLogic;
	@Autowired
	private YZButtonLogic buttonLogic;

	@Autowired
	private YZPersonLogic personLogic;
	
	@RequestMapping(value = "/toLogIndex.act")
	public ModelAndView toLogIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/log/index.jsp");
		return mv;
	}

	/**
	 * 系统登录方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doLoginIn.act")
	public String doLoginIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 传参repeatlogin = 1 ，作用是当用户在其他地方登录时，强制挤掉该用户，然后进行登录
			String repeatlogin = request.getParameter("repeatlogin");
			String userName = request.getParameter("userName");
			String organization = request.getParameter("organization");
			YZPerson person = null;
			QueryFirstLog queryLog = null;
			if (YZUtility.isNullorEmpty(userName)) {
				userName = "";
			} else {
				person = personLogic.queryPerson(userName, request);
				queryLog = personLogic.queryFirstLog(userName);
			}
				
			// 登录验证
			YZLoginAdapter loginAdapter = new YZLoginAdapter(request, person);

			// 验证用户是否存在
			if (!loginAdapter.validate(new YZExistUserValidator())) {
				YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, logger);
				return null;
			}
			// 验证门诊
			if (!person.getOrganization().contains(organization)) {
					JSONObject successRt = new JSONObject();
					successRt.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_MISTAKE);
					successRt.put("code", YZLoginErrorConst.LOGIN_MISTAKE_CODE);
					successRt.put("msg", YZLoginErrorConst.LOGIN_MISTAKE);
					YZUtility.RETURN_OBJ(successRt, response, logger);
					return null;
				}
			// 验证端口数
			if (!loginAdapter.validate(new YZConfineNumberValidator())) {
				YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, logger);
				return null;
			}
			// 验证用户是否离职
			if (!loginAdapter.validate(new YZIsLeaveUserValidator())) {
				YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, logger);
				return null;
			}

			// 验证用户是否禁止登录
			if (!loginAdapter.validate(new YZForbidLoginValidator())) {
				YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, logger);
				return null;
			}

			if (!loginAdapter.validate(new YZPasswordValidator())) {
				YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, logger);
				return null;
			}
			// repeatlogin值为空，则判断是否存在一个账号多处登录的情况，如果存在，则提示当前用户，该账号已在别处登录，不允许继续登录
			if (YZUtility.isNullorEmpty(repeatlogin)) {
				if (!loginAdapter.validate(new YZRepeatLoginValidator())) {
					YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, logger);
					return null;
				}
			} else { // repeatlogin值不为空，则系统默认执行：挤掉已经登录的用户
				HttpSession session = SessionUtil.Session_MAP.get(person.getUserId());
				SessionUtil.invalidateSession(session);
			}
			
			if(queryLog==null){
			QueryFirstLog queryFirstLog = new QueryFirstLog();
			queryFirstLog.setSeqId(YZUtility.getUUID());
			queryFirstLog.setUserId(person.getUserId());
			queryFirstLog.setUserName(person.getUserName());
			queryFirstLog.setOrganization(organization);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			queryFirstLog.setTime(df.format(new Date()));// new Date()为获取当前系统时间
				personLogic.saveQueryLog(queryFirstLog);
			}
			// 调用登录成功的处理
			sysLogic.loginSuccess(person, request, response);
			JSONObject successRt = new JSONObject();
			successRt.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			successRt.put("code", YZLoginErrorConst.LOGIN_SUCCESS_CODE);
			successRt.put("msg", YZLoginErrorConst.LOGIN_SUCCESS);
			YZUtility.RETURN_OBJ(successRt, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR("登录失败", false, ex, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: QueryFirstLog   
	  * @Description: TODO(查询确认是否首次登录)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	 */
	@RequestMapping("/QueryFirstLog.act")
	public String QueryFirstLog(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userName = request.getParameter("userName");
		try {
			YZPerson person = personLogic.queryPerson(userName, request);
			QueryFirstLog queryLog = personLogic.queryFirstLog(userName);
			if(queryLog != null && YZPassEncrypt.isValidPas("123456",person.getPassword())){
				YZUtility.DEAL_SUCCESS(null, "true", response, logger);
			}else{
				YZUtility.DEAL_SUCCESS(null, "false", response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 退出登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/doLogout.act")
	public String doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		SessionUtil.invalidateSession(session);
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		return null;
	}

	/**
	 * 跳转到口腔大师后端管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginAdmin.act")
	public String loginAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = (YZPerson) request.getSession().getAttribute("LOGIN_USER");
			// 根据用户主角色，获取用户所有的权限菜单
			List<String> funclist = sysLogic.listUserMenu(person);
			List<YZMenuModel> menulist = sysLogic.getMainMenu(person, funclist);
			// 递归，获取完整的菜单list
			menulist = sysLogic.lazyLoadJsonModel(funclist, menulist);
			request.setAttribute("menuList", menulist);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return "/admin/index/index.jsp";
	}

	/**
	 * 前台登录后跳转到系统首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginIndexFront.act")
	public String loginIndexFront(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = (YZPerson) request.getSession().getAttribute("LOGIN_USER");
			// 获取该用户的权限菜单list，该权限菜单满足树形菜单的数据格式要求（根据用户主角色，获取用户所有的权限菜单）
			List<String> funclist = sysLogic.listUserMenu(person);
			List<YZMenuModel> menulist = sysLogic.getMainMenu(person, funclist);
			// 递归，获取完整的菜单list
			menulist = sysLogic.lazyLoadJsonModel(funclist, menulist);
			request.setAttribute("menuList", menulist);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return "/kqdsFront/index.jsp";
	}

	/**
	 * 获取某以页面所有的按钮
	 * 
	 * @param person
	 * @param model
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getButtonList.act")
	public String getButtonList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		try {
			// 获取当前菜单下的按钮
			List<YZButton> btnlist = buttonLogic.getButtonList(menuId);
			// 当前用户的角色
			YZPriv userPriv = (YZPriv) request.getSession().getAttribute(ConstUtil.LOGIN_USER_PRIV);
			// 获取当前角色下的所有按钮
			String currUserButtons = userPriv.getFuncbutton();

			if (null == currUserButtons) {
				return null;
			}
			currUserButtons = currUserButtons.trim();
			if ("".equals(currUserButtons)) {
				return null;
			}
			if (!currUserButtons.endsWith(",")) {
				currUserButtons += ",";
			}
			if (!currUserButtons.startsWith(",")) {
				currUserButtons = "," + currUserButtons;
			}
			// 构造返回list
			List<YZButton> list = new ArrayList<YZButton>();
			for (YZButton btn : btnlist) { // 根据当前用户角色对应的按钮权限，进行过滤
				if (currUserButtons.matches(".*,\\s*" + btn.getSeqId() + "\\s*,.*")) {
					list.add(btn);
				}
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}
