package com.kqds.controller.sys.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.sys.YZMenu;
import com.kqds.entity.sys.YZMenuModel;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.func.YZFuncLogic;
import com.kqds.service.sys.menu.YZMenuLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.service.sys.system.YZSystemLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZMenuAct")
public class YZMenuAct {
	private Logger logger = LoggerFactory.getLogger(YZMenuAct.class);
	@Autowired
	private YZFuncLogic funcLogic;
	@Autowired
	private YZSystemLogic sysLogic;
	@Autowired
	private YZPrivLogic privLogic;
	@Autowired
	private YZMenuLogic menuLogic;

	@RequestMapping(value = "/toWelcome.act")
	public String toWelcome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/index/welcome.jsp";
	}

	@RequestMapping(value = "/toIndex.act")
	public String toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/menu/index.jsp";
	}

	@RequestMapping(value = "/toTop.act")
	public String toContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/menu/top.jsp";
	}

	@RequestMapping(value = "/toLeft.act")
	public String toLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/menu/left.jsp";
	}

	@RequestMapping(value = "/toNewFunc.act")
	public ModelAndView toNewFunc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentMenuId = request.getParameter("parentMenuId");
		String parentMenuName = request.getParameter("parentMenuName");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentMenuId", parentMenuId);
		mv.addObject("parentMenuName", parentMenuName);
		mv.setViewName("/admin/menu/newFunc.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewMenu.act")
	public ModelAndView toNewMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/menu/newMenu.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEditMenu.act")
	public ModelAndView toEditMenu(@RequestParam("menuId") String menuId) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/admin/menu/edit.jsp");
		return mv;
	}

	/**
	 * 保存主菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdateMenu.act")
	public String insertOrUpdateMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			YZMenu dp = new YZMenu();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(dp.getMenuId())) {
				throw new Exception("主菜单分类代码不能为空");
			}
			if (YZUtility.isNullorEmpty(dp.getMenuName())) {
				throw new Exception("菜单名称不能为空");
			}

			int num = menuLogic.countByMenuName(dp);
			if (num >= 1) {
				throw new Exception("菜单名称已存在, 请重新填写");
			}

			num = menuLogic.countByMenuId(dp);
			if (num >= 1) {
				throw new Exception("主菜单分类代码已存在, 请重新填写");
			}

			if (!YZUtility.isNullorEmpty(seqId)) {
				YZMenu tmp = (YZMenu) funcLogic.loadObjSingleUUID(TableNameUtil.SYS_MENU, seqId);
				if (tmp == null) {
					throw new Exception("主菜单不存在");
				}

				// 这几个字段修改时，不更新
				dp.setImage(tmp.getImage());
				dp.setCreatetime(tmp.getCreatetime());
				dp.setCreateuser(tmp.getCreateuser());
				// 这几个字段修改时，不更新 end..
				menuLogic.updateSingleUUID(TableNameUtil.SYS_MENU, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_MENU, dp, TableNameUtil.SYS_MENU, request);
			} else {

				dp.setSeqId(YZUtility.getUUID());
				dp.setImage("sys.gif");
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				menuLogic.saveSingleUUID(TableNameUtil.SYS_MENU, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_MENU, dp, TableNameUtil.SYS_MENU, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询出系统中所有菜单，以树形目录方式展示【用于菜单管理】 并且，根据当前用户的权限，将权限范围内的节点标记为选中状态
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllMenu4TreeManage.act")
	public void getAllMenu4TreeManage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			YZPerson person = (YZPerson) request.getSession().getAttribute("LOGIN_USER");

			// 根据用户主角色，获取用户所有的权限菜单
			List<String> funclist = funcLogic.listAllMenu4Tree();
			List<JSONObject> menulist = menuLogic.getMainMenu4Manage(person, funclist);
			// 获取zTree 菜单列表
			menulist = menuLogic.lazyLoadJsonTree4Manage(funclist, menulist);
			// 转换成ztree格式
			for (JSONObject menu : menulist) {
				menu.put("id", menu.getString("menuId"));
				menu.put("pId", menu.getString("parentid"));
				menu.put("name", menu.getString("menuName"));
			}

			JSONObject jobj = new JSONObject();
			jobj.put("menuList", menulist);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 查询出系统中所有菜单，以树形目录方式展示【用于菜单权限设置】 并且，根据当前用户的权限，将权限范围内的节点标记为选中状态
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getAllMenu4TreeData.act")
	public void getAllMenu4TreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			YZPerson person = (YZPerson) request.getSession().getAttribute("LOGIN_USER");
			// 页面传值，角色主键
			String privSeqId = request.getParameter("privSeqId");
			if (YZUtility.isNullorEmpty(privSeqId)) {
				throw new Exception("页面传值，角色主键为空");
			}

			YZPriv up = privLogic.getDetailBySeqId(privSeqId);
			if (up == null) {
				throw new Exception("角色不存在");
			}

			// 获取用户的菜单权限
			String personFuncStr = up.getFuncIdStr();
			if (personFuncStr == null) {
				personFuncStr = "";
			}

			// 根据用户主角色，获取用户所有的权限菜单
			List<String> funclist = funcLogic.listAllMenu4Tree();
			List<YZMenuModel> menulist = sysLogic.getMainMenu(person, funclist);
			// 获取zTree 菜单列表
			menulist = sysLogic.lazyLoadJsonModel4zTree(funclist, menulist);
			// 转换成ztree格式
			List<Map<String, String>> rtnlist = new ArrayList<Map<String, String>>();
			for (YZMenuModel yhMenuModel : menulist) {
				boolean ischeck = YZUtility.isStrInArrayEach(yhMenuModel.getMenuid(), personFuncStr);

				Map node = new HashMap();
				node.put("id", yhMenuModel.getMenuid());
				node.put("pId", yhMenuModel.getParentid());
				node.put("name", yhMenuModel.getMenuname());
				node.put("checked", ischeck);
				rtnlist.add(node);
			}

			JSONObject jobj = new JSONObject();
			jobj.put("menuList", rtnlist);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据menuid查询详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailByMenuId.act")
	public String selectDetailByMenuId(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String menuId = request.getParameter("menuId");

			if (YZUtility.isNullorEmpty(menuId)) {
				throw new Exception("主菜单分类代码不能为空");
			}
			JSONObject menu = menuLogic.getMenuById(menuId);

			if (menu == null) {
				throw new Exception("菜单不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, menu.toString());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.act")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			YZMenu tmp = (YZMenu) funcLogic.loadObjSingleUUID(TableNameUtil.SYS_MENU, seqId);
			if (tmp == null) {
				throw new Exception("主菜单不存在");
			}

			boolean ischild = funcLogic.IsHaveChild(tmp.getMenuId());
			if (ischild) {
				throw new Exception("当前菜单存在子菜单，不允许删除");
			}
			menuLogic.deleteBySeqIds(seqId, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 菜单树形下拉框
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectMenuTree.act")
	public String getSelectMenuTree(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String deptTreeListStr = menuLogic.getSelectMenuTree("0");
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, deptTreeListStr);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}
