package com.kqds.controller.sys.priv;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.service.sys.system.YZSystemLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.sys.UserPrivUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZPrivAct")
public class YZPrivAct {
	private static Logger logger = LoggerFactory.getLogger(YZPrivAct.class);
	@Autowired
	private YZPrivLogic privLogic;
	@Autowired
	private YZSystemLogic systemLogic;

	@RequestMapping(value = "/toIndex.act")
	public String toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/priv/index.jsp";
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/admin/priv/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/admin/priv/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSetPriv.act")
	public ModelAndView toSetPriv(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String priv_seqId = request.getParameter("priv_seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("priv_seqId", priv_seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/admin/priv/setpriv_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCopy.act")
	public ModelAndView toCopy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/admin/priv/copy.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/admin/priv/newAdd.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSetPrivList.act")
	public ModelAndView toSetPrivList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String priv_seqId = request.getParameter("priv_seqId");
		String menuId = request.getParameter("menuId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("priv_seqId", priv_seqId);
		mv.addObject("menuId", menuId);
		mv.addObject("organization", organization);
		mv.setViewName("/admin/priv/setpriv_list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSetPrivLeft.act")
	public ModelAndView toSetPrivLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String priv_seqId = request.getParameter("priv_seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("priv_seqId", priv_seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/admin/priv/setpriv_left.jsp");
		return mv;
	}

	/**
	 * 拷贝角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/copyPriv.act")
	public String copyPriv(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			String seqId = request.getParameter("seqId");
			String privName = request.getParameter("privName");
			String organization = request.getParameter("organization");

			if (YZUtility.isNullorEmpty(organization)) {
				organization = ""; // null 要赋空值，否则 sql查询，查不到null的记录
			}

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			if (YZUtility.isNullorEmpty(privName)) {
				throw new Exception("角色名称不允许为空");
			}

			YZPriv forQuery = new YZPriv();
			forQuery.setPrivName(privName);
			int num = privLogic.countByPrivName(forQuery);
			if (num >= 1) {
				throw new Exception("角色名称不允许重复");
			}

			YZPriv tmpPriv = (YZPriv) privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
			if (tmpPriv == null) {
				throw new Exception("角色不存在");
			}

			// 拷贝并新建角色
			tmpPriv.setSeqId(YZUtility.getUUID());
			tmpPriv.setPrivName(privName);
			tmpPriv.setCreatetime(YZUtility.getCurDateTimeStr());
			tmpPriv.setCreateuser(person.getSeqId());
			tmpPriv.setOrganization(organization);
			privLogic.saveSingleUUID(TableNameUtil.SYS_PRIV, tmpPriv);

			// 记录日志
			SysLogUtil.log(SysLogUtil.COPY_ROLE, SysLogUtil.SYS_PRIV, tmpPriv, TableNameUtil.SYS_PRIV, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			YZPriv dp = new YZPriv();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(dp.getPrivName())) {
				throw new Exception("角色名称不能为空");
			}

			if (!YZUtility.isNullorEmpty(seqId)) {
				YZPriv tmpPriv = (YZPriv) privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
				if (tmpPriv == null) {
					throw new Exception("角色不存在");
				}

				int num = privLogic.countByPrivName(dp);
				if (num >= 1) {
					throw new Exception("角色名称不允许重复");
				}

				// 这几个字段修改时，不更新
				dp.setFuncIdStr(tmpPriv.getFuncIdStr());
				dp.setFuncbutton(tmpPriv.getFuncbutton());
				dp.setCreatetime(tmpPriv.getCreatetime());
				dp.setCreateuser(tmpPriv.getCreateuser());
				// 这几个字段修改时，不更新 end..
				privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_PRIV, dp, TableNameUtil.SYS_PRIV, request);
			} else {
				int num = privLogic.countByPrivName(dp);
				if (num >= 1) {
					throw new Exception("角色名称不允许重复");
				}

				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				privLogic.saveSingleUUID(TableNameUtil.SYS_PRIV, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_PRIV, dp, TableNameUtil.SYS_PRIV, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 角色下拉框
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSlectUserPriv.act")
	public String getSlectUserPriv(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}

			List<JSONObject> list = privLogic.getSlectUserPriv(organization);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 指定门诊+公共角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSlectUserPrivWithCommon.act")
	public String getSlectUserPrivWithCommon(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			List<JSONObject> list = privLogic.getSlectUserPrivWithCommon(organization);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 角色下拉框 不加门诊编号过滤条件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSlectUserPrivNoOrg.act")
	public String getSlectUserPrivNoOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			List<JSONObject> list = privLogic.getSlectUserPriv(null);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject pagejson = privLogic.selectPage(bp, ChainUtil.getOrganizationFromUrlCanNull(request));
			YZUtility.DEAL_SUCCESS(pagejson, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 根据主键，获取角色基本信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键不能为空");
			}
			// YZPriv person = (YZPriv)dao.loadObjSingleUUID(
			// YZPriv.class, seqId, TableNameUtil.SYS_PRIV);
			JSONObject userPriv = privLogic.selectDetail(seqId, request);
			if (userPriv == null) {
				throw new Exception("角色不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, userPriv);
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

			boolean ischild = privLogic.IsHaveChild(seqId);
			if (ischild) {
				throw new Exception("该记录已被用户主角色引用，不允许删除");
			}
			ischild = privLogic.IsHaveChildOther(seqId);
			if (ischild) {
				throw new Exception("该记录已被用户辅助角色引用，不允许删除");
			}

			privLogic.deleteBySeqIds(seqId, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 给角色设定菜单权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menuSetSave.act")
	public String menuSetSave(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");
			String funcIdStr = request.getParameter("funcIdStr");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			YZPriv tmpPriv = (YZPriv) privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
			if (tmpPriv == null) {
				throw new Exception("角色不存在");
			}

			tmpPriv.setFuncIdStr(funcIdStr);
			privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, tmpPriv);
			// 记录日志
			SysLogUtil.log(SysLogUtil.SET_FUNC, SysLogUtil.SYS_PRIV, tmpPriv, TableNameUtil.SYS_PRIV, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 按钮权限设置【一次只能设置一个】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setButtonPriv.act")
	public String setButtonPriv(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 取查询条件
			String privid = request.getParameter("privid");
			String buttonIds = request.getParameter("buttonid");
			String checked = request.getParameter("checked");

			YZPriv userpriv = (YZPriv) privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
			if (userpriv == null) {
				throw new Exception("角色不存在");
			}

			String qxButtonStr = userpriv.getFuncbutton(); // 按钮权限
			if (qxButtonStr == null) {
				qxButtonStr = "";
			}
			// 以下程序按照这种格式进行编写 1,11,17,
			if (checked.equals("0")) {// 取消权限
				String[] buttionArray = buttonIds.split(",");
				for (String buttonid : buttionArray) {
					if (YZUtility.isNullorEmpty(buttonid)) {
						continue;
					}
					if (qxButtonStr.startsWith(buttonid + ",")) {
						qxButtonStr = qxButtonStr.substring(buttonid.length() + 1, qxButtonStr.length());
					} else {
						if (qxButtonStr.matches(".*,\\s*" + buttonid + "\\s*,.*")) { // 如果权限中包含此按钮
							qxButtonStr = qxButtonStr.replace("," + buttonid + ",", ",");
						}
					}
				}
			} else {// 设定权限
				String[] buttionArray = buttonIds.split(",");
				for (String buttonid : buttionArray) {
					if (YZUtility.isNullorEmpty(buttonid)) {
						continue;
					}
					if (!qxButtonStr.startsWith(buttonid + ",") && !qxButtonStr.matches(".*,\\s*" + buttonid + "\\s*,.*")) {
						if (qxButtonStr.endsWith(",")) { // 容错处理
							qxButtonStr += (buttonid + ",");
						} else {
							qxButtonStr += ("," + buttonid + ",");
						}
					}
				}
			}
			userpriv.setFuncbutton(qxButtonStr); // 重新赋值
			privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
			// 记录日志
			SysLogUtil.log(SysLogUtil.SET_BUTTON, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 设置该角色对应的用户相关权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setPersonPriv.act")
	public String setPersonPriv(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			// 取查询条件
			String privid = request.getParameter("privid");

			String maxDiscount = request.getParameter(UserPrivUtil.qxFlag0_maxDiscount);
			String canLookPhone = request.getParameter(UserPrivUtil.qxFlag1_canLookPhone);
			String canKd = request.getParameter(UserPrivUtil.qxFlag2_canKd);
			String canHj = request.getParameter(UserPrivUtil.qxFlag3_canHj);
			String canPaiban = request.getParameter(UserPrivUtil.qxFlag4_canPaiban);
			String canRoom = request.getParameter(UserPrivUtil.qxFlag5_canRoom);
			String canOrderOther = request.getParameter(UserPrivUtil.qxFlag6_canOrderOther);
			String canEditKf = request.getParameter(UserPrivUtil.qxFlag7_canEditKf);
			String canEditWd = request.getParameter(UserPrivUtil.qxFlag8_canEditWd);
			String canEditCost = request.getParameter(UserPrivUtil.qxFlag9_canEditCost);
			String canEditOrder = request.getParameter(UserPrivUtil.qxFlag10_canEditOrder);
			String iszj = request.getParameter(UserPrivUtil.qxFlag11_iszj);
			String canEditJdr = request.getParameter(UserPrivUtil.qxFlag12_canEditJdr);
			String canEditAskperson = request.getParameter(UserPrivUtil.qxFlag13_canEditAskperson);
			String canExportPhoneNumber = request.getParameter(UserPrivUtil.qxFlag14_canExportPhoneNumber);
			String canEditPhone = request.getParameter(UserPrivUtil.qxFlag15_canEditPhone);
			String canEditHzly = request.getParameter(UserPrivUtil.qxFlag16_canEditHzly);
			String canZheKouOnly = request.getParameter(UserPrivUtil.qxFlag17_canZheKouOnly);
			String canDelCk = request.getParameter(UserPrivUtil.qxFlag18_canDelCk);
			String maxVoidmoney = request.getParameter(UserPrivUtil.qxFlag19_maxVoidmoney);
			String canModRoom = request.getParameter(UserPrivUtil.qxFlag20_canModRoom);

			// 获取角色对象
			YZPriv userPriv = privLogic.getDetailBySeqId(privid);
			if (userPriv == null) {
				throw new Exception("角色不存在");
			}

			String[] privArray = privLogic.preCheckInit(userPriv, request);

			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag0_maxDiscount, maxDiscount);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag1_canLookPhone, canLookPhone);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag2_canKd, canKd);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag3_canHj, canHj);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag4_canPaiban, canPaiban);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag5_canRoom, canRoom);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag6_canOrderOther, canOrderOther);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag7_canEditKf, canEditKf);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag8_canEditWd, canEditWd);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag9_canEditCost, canEditCost);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag10_canEditOrder, canEditOrder);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag11_iszj, iszj);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag12_canEditJdr, canEditJdr);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag13_canEditAskperson, canEditAskperson);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag14_canExportPhoneNumber, canExportPhoneNumber);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag15_canEditPhone, canEditPhone);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag16_canEditHzly, canEditHzly);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag17_canZheKouOnly, canZheKouOnly);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag18_canDelCk, canDelCk);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag19_maxVoidmoney, maxVoidmoney);
			privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag20_canModRoom, canModRoom);
			StringBuffer newIdStr = new StringBuffer();
			for (String id : privArray) {
				if (YZUtility.isNullorEmpty(id)) {
					id = ConstUtil.EMPTY_SPACE; // 空格
				}
				newIdStr.append(id).append(",");
			}
			userPriv.setPrivIdStr(newIdStr.toString());

			// 更新
			privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userPriv);
			// 记录日志
			SysLogUtil.log(SysLogUtil.SET_PERSON_PRIV, SysLogUtil.SYS_PRIV, userPriv, TableNameUtil.SYS_PRIV, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 可见人员/部门设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setVisal.act")
	public String setVisal(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 取查询条件
			String privid = request.getParameter("privid");
			String visualPerson = request.getParameter("visualPerson");
			String visualDept = request.getParameter("visualDept");

			YZPriv userpriv = (YZPriv) privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
			if (userpriv == null) {
				throw new Exception("角色不存在");
			}

			userpriv.setVisualPerson(visualPerson);
			userpriv.setVisualDept(visualDept);

			privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
			// 记录日志
			SysLogUtil.log(SysLogUtil.SET_VISUAL, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 预约可见人员/部门设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setOrderVisal.act")
	public String setOrderVisal(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 取查询条件
			String privid = request.getParameter("privid");
			String orderVisualPerson = request.getParameter("orderVisualPerson");
			String orderVisualDept = request.getParameter("orderVisualDept");

			YZPriv userpriv = (YZPriv) privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
			if (userpriv == null) {
				throw new Exception("角色不存在");
			}

			userpriv.setOrderVisualPerson(orderVisualPerson);
			userpriv.setOrderVisualDept(orderVisualDept);
			privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
			// 记录日志
			SysLogUtil.log(SysLogUtil.SET_VISUAL_ORDER, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	
	/**
	 * 回访可见设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setVisitDept.act")
	public String setVisitDept(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 取查询条件
			String privid = request.getParameter("privid");
			String visitDept = request.getParameter("visitDept");

			YZPriv userpriv = (YZPriv) privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
			if (userpriv == null) {
				throw new Exception("角色不存在");
			}

			userpriv.setVisitDept(visitDept);
			privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
			// 记录日志
			SysLogUtil.log(SysLogUtil.SET_VISUAL_ORDER, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	/**
	 * 仓库可见
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setOneHouse.act")
	public String setOneHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {

			// 取查询条件
			String privid = request.getParameter("privid");
			String firstFloor = request.getParameter("firstfloor");
			String upstair = request.getParameter("upstair");

			YZPriv userpriv = (YZPriv) privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
			if (userpriv == null) {
				throw new Exception("角色不存在");
			}

			userpriv.setFirstFloor(firstFloor);
			userpriv.setUpstair(upstair);
			privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
			// 记录日志
			SysLogUtil.log(SysLogUtil.SET_VISUAL_ORDER, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 更新在线人员数据，实时更新权限信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOnlineData.act")
	public String updateOnlineData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 取查询条件
			String privid = request.getParameter("privid");

			YZPriv userpriv = (YZPriv) privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
			if (userpriv == null) {
				throw new Exception("角色不存在");
			}

			String visualstaffYyrl = systemLogic.getVisualstaffYyrl(userpriv, null); // 这里的Person对象参数传Null值，为了公用
			String visualstaff = systemLogic.getVisualstaff(userpriv, null);

			for (Map.Entry<String, HttpSession> entry : SessionUtil.Session_MAP.entrySet()) {
				HttpSession session = entry.getValue();
				if (session == null) {
					continue;
				}
				YZPerson person = (YZPerson) session.getAttribute(ConstUtil.LOGIN_USER);
				if (person == null) {
					continue;
				}
				if (!person.getUserPriv().equals(privid)) {
					continue;
				}

				String currvisualstaffYyrl = null;
				if (YZUtility.isNullorEmpty(visualstaffYyrl)) { // 如果该角色没有设置预约可见人员
					currvisualstaffYyrl = "'" + person.getSeqId() + "'";
				} else {
					currvisualstaffYyrl = visualstaffYyrl + ",'" + person.getSeqId() + "'";
				}

				String currvisualstaff = null;
				if (YZUtility.isNullorEmpty(visualstaff)) { // 如果该角色没有设置可见人员
					currvisualstaff = "'" + person.getSeqId() + "'";
				} else {
					currvisualstaff = visualstaff + ",'" + person.getSeqId() + "'";
				}

				// 当前登录人的用户角色
				session.setAttribute(ConstUtil.LOGIN_USER_PRIV, userpriv);

				// 预约可见人员
				session.setAttribute(SessionUtil.visualstaffYyrl, currvisualstaffYyrl);

				// 可见人员
				session.setAttribute(SessionUtil.visualstaff, currvisualstaff);

			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

}
