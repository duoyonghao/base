package com.kqds.controller.sys.person;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZPassEncrypt;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZOrganization;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.service.sys.organization.YZOrganizationLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZPersonAct")
public class YZPersonAct {
	private Logger logger = LoggerFactory.getLogger(YZPersonAct.class);
	@Autowired
	private YZOrganizationLogic organizationLogic;
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private YZDeptLogic deptLogic;
	@Autowired
	private YZOnlineLogic onlineLogic;

	@RequestMapping(value = "/toIndex.act")
	public String toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/person/index.jsp";
	}

	@RequestMapping(value = "/toParallelTree.act")
	public String toParallelTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/kqdsFront/online/parallelTree.jsp";
	}
	
	/**
	  * @Title: toEditPwd   
	  * @Description: TODO(跳修改密码页面)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年6月16日 下午5:54:04
	 */
	@RequestMapping(value = "/toEditPwd.act")
	public String toEditPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/person/setpwd.jsp";
	}

	@RequestMapping(value = "/toLogin.act")
	public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isapp = request.getParameter("isapp");
		ModelAndView mv = new ModelAndView();
		mv.addObject("isapp", isapp);
		mv.setViewName("login.jsp");
		return mv;
	}

	@RequestMapping(value = "/toHzSelectIndex.act")
	public String toHzSelectIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/person/hz_select/index.jsp";
	}

	@RequestMapping(value = "/toHzSelectLeft.act")
	public String toSelectLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/person/hz_select/left.jsp";
	}

	@RequestMapping(value = "/toHzSelectList.act")
	public ModelAndView toSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/person/hz_select/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toIdIndex.act")
	public ModelAndView toIdIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptIds = request.getParameter("deptIds");
		ModelAndView mv = new ModelAndView();
		mv.addObject("deptIds", deptIds);
		mv.setViewName("/admin/person/id_select/index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toIdTop.act")
	public String toIdTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/person/id_select/top.jsp";
	}

	@RequestMapping(value = "/toIdLeft.act")
	public ModelAndView toIdLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptIds = request.getParameter("deptIds");
		ModelAndView mv = new ModelAndView();
		mv.addObject("deptIds", deptIds);
		mv.setViewName("/admin/person/id_select/left.jsp");
		return mv;
	}

	@RequestMapping(value = "/toIdList.act")
	public ModelAndView toIdList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/person/id_select/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSingleIndex.act")
	public ModelAndView toSingleIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isSingle = request.getParameter("isSingle");
		String showleave = request.getParameter("showleave");
		String depttype = request.getParameter("depttype");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("isSingle", isSingle);
		mv.addObject("showleave", showleave);
		mv.addObject("depttype", depttype);
		mv.addObject("organization", organization);
		mv.setViewName("/admin/person/single_select/index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSingleTop.act")
	public String toSingleTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/person/single_select/top.jsp";
	}

	@RequestMapping(value = "/toSingleLeft.act")
	public ModelAndView toSingleLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isSingle = request.getParameter("isSingle");
		String showleave = request.getParameter("showleave");
		String depttype = request.getParameter("depttype");
		ModelAndView mv = new ModelAndView();
		mv.addObject("isSingle", isSingle);
		mv.addObject("showleave", showleave);
		mv.addObject("depttype", depttype);
		mv.setViewName("/admin/person/single_select/left.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMultiLeft.act")
	public ModelAndView toMultiLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isFilterByVisualStaff = request.getParameter("isFilterByVisualStaff");
		ModelAndView mv = new ModelAndView();
		mv.addObject("isFilterByVisualStaff", isFilterByVisualStaff);
		mv.setViewName("/admin/person/multi_select/left.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSingleList.act")
	public ModelAndView toSingleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptId = request.getParameter("deptId");
		String showleave = request.getParameter("showleave");
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("deptId", deptId);
		mv.addObject("showleave", showleave);
		mv.addObject("seqId", seqId);
		mv.setViewName("/admin/person/single_select/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTop.act")
	public String toTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/person/top.jsp";
	}

	@RequestMapping(value = "/toLeft.act")
	public String toLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/person/left.jsp";
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptId = request.getParameter("deptId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("deptId", deptId);
		mv.setViewName("/admin/person/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String personId = request.getParameter("personId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("personId", personId);
		mv.setViewName("/admin/person/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptId = request.getParameter("deptId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("deptId", deptId);
		mv.setViewName("/admin/person/newAdd.jsp");
		return mv;
	}

	/**
	 * 获取当前部门用户最大排序号
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMaxOrderno.act")
	public void getMaxOrderno(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String deptId = request.getParameter("deptId");
			String data = personLogic.getMaxOrderno(deptId);
			JSONObject jobj = new JSONObject();
			jobj.put("data", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
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
			String organization = ChainUtil.getCurrentOrganization(request);
			YZPerson dp = new YZPerson();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(dp.getUserId())) {
				throw new Exception("用户名不能为空");
			}

			if (YZUtility.isNullorEmpty(dp.getDeptId())) {
				throw new Exception("部门不能为空");
			}

			if (!YZUtility.isNullorEmpty(seqId)) {
				YZPerson tmpPerson = (YZPerson) personLogic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, seqId);
				if (tmpPerson == null) {
					throw new Exception("用户不存在");
				}

				int num = personLogic.countByUserNameCurrHos(dp.getUserName(), dp.getDeptId(), seqId);
				if (num >= 1) {
					throw new Exception("同一个门诊，姓名不允许重复");
				}

				// 这几个字段修改时，不更新
				dp.setPassword(tmpPerson.getPassword());
				dp.setCreatetime(tmpPerson.getCreatetime());
				dp.setCreateuser(tmpPerson.getCreateuser());
				dp.setUserId(tmpPerson.getUserId());
				// 这几个字段修改时，不更新 end..
				personLogic.updateSingleUUID(TableNameUtil.SYS_PERSON, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_PERSON, dp, TableNameUtil.SYS_PERSON, request);
			} else {
				int num = personLogic.countByUserId(dp.getUserId());
				if (num >= 1) {
					throw new Exception("用户名重复, 用户名不能重复");
				}
				num = personLogic.countByUserNameCurrHos(dp.getUserName(), dp.getDeptId(), null);
				if (num >= 1) {
					throw new Exception("同一个门诊，姓名不允许重复");
				}

				dp.setPassword(YZPassEncrypt.encryptPass(dp.getPassword())); // 密码加密
				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(organization);//添加门诊
				personLogic.saveSingleUUID(TableNameUtil.SYS_PERSON, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_PERSON, dp, TableNameUtil.SYS_PERSON, request);
			}

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
	@RequestMapping(value = "/getPersonTree.act")
	public String getPersonTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = null;
		String idStr = request.getParameter("id");

		try {

			List<JSONObject> treeList = new ArrayList<JSONObject>();
			if (idStr == null || "0".equals(idStr)) {
				List<YZOrganization> orgList = organizationLogic.getOrganizationList();
				for (YZOrganization org : orgList) {
					JSONObject node = new JSONObject();
					node.put("id", "orgId");
					node.put("pId", "orgParentId");
					node.put("name", org.getUnitName());
					node.put("isParent", true);
					node.put("iconSkin", "home");

					treeList.add(node);
				}
			} else {
				if ("orgId".equals(idStr)) {
					idStr = "0";
				}

				List<YZPerson> persons = personLogic.getUserListByDeptSeqId(idStr);
				for (YZPerson p : persons) {
					JSONObject node = new JSONObject();
					node.put("id", "person" + p.getSeqId());
					node.put("pId", idStr);
					node.put("name", p.getUserId()); // 这样做的目的是，同名被选中时，树形菜单反过来可以准确赋值
														// + "[" + p.getUserId()
														// + "]"
					node.put("isParent", false);
					node.put("iconSkin", "person");
					treeList.add(node);
				}
				// 试用版本(查询登录人的门诊编号)
				if (ChainUtil.isOpenTry()) {
					organization = ChainUtil.getCurrentOrganization(request);
				}
				List<YZDept> deptList = deptLogic.getSubDeptListBySeqId(idStr, organization);
				for (YZDept dept : deptList) {
					boolean haveChild = deptLogic.IsHaveChildPerson(dept.getSeqId());
					JSONObject node = new JSONObject();
					node.put("id", dept.getSeqId());
					node.put("pId", dept.getDeptParent());
					node.put("name", dept.getDeptName());
					node.put("isParent", haveChild);
					treeList.add(node);
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, treeList);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 人员树(可见人员)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonTreeByPriv.act")
	public String getPersonTreeByPriv(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String idStr = request.getParameter("id");

		try {

			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> treeList = new ArrayList<JSONObject>();
			if (idStr == null || "0".equals(idStr)) {
				List<YZDept> deptList = deptLogic.getSubDeptListBySeqId("0", ChainUtil.getCurrentOrganization(request));
				for (YZDept dept : deptList) {
					Map<String, String> map = new HashMap<>();
					map.put("deptparentid", dept.getSeqId());
					int count = personLogic.countByUserDocument(map, visualstaff);
					if (count > 0) {
						JSONObject node = new JSONObject();
						node.put("id", dept.getSeqId());
						node.put("pId", dept.getDeptParent());
						node.put("name", dept.getDeptName());
						node.put("isParent", true);
						treeList.add(node);
					}
				}
			} else {
				List<YZPerson> persons = personLogic.getUserListByDeptSeqId(idStr);
				for (YZPerson p : persons) {
					JSONObject node = new JSONObject();
					node.put("id", "person" + p.getSeqId());
					node.put("pId", idStr);
					node.put("name", p.getUserId()); // 这样做的目的是，同名被选中时，树形菜单反过来可以准确赋值
														// + "[" + p.getUserId()
														// + "]"
					node.put("isParent", false);
					node.put("iconSkin", "person");

					Map<String, String> map = new HashMap<>();
					map.put("perid", p.getSeqId());
					int count = personLogic.countByUserDocument(map, visualstaff);
					if (count > 0) {
						treeList.add(node);
					}
				}

				List<YZDept> deptList = deptLogic.getSubDeptListBySeqId(idStr, null);
				for (YZDept dept : deptList) {
					Map<String, String> map = new HashMap<>();
					map.put("deptid", dept.getSeqId());
					int count = personLogic.countByUserDocument(map, visualstaff);
					if (count > 0) {
						JSONObject node = new JSONObject();
						node.put("id", dept.getSeqId());
						node.put("pId", dept.getDeptParent());
						node.put("name", dept.getDeptName());
						node.put("isParent", true);
						treeList.add(node);
					}
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, treeList);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 多选人员树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonTree4MultiSelect.act")
	public String getPersonTree4MultiSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String idStr = request.getParameter("id");
		String personIds = request.getParameter("personIds"); // 传参已被选中的人员

		try {

			List<JSONObject> treeList = new ArrayList<JSONObject>();
			if (idStr == null || "0".equals(idStr)) {
				List<YZOrganization> orgList = organizationLogic.getOrganizationList();
				for (YZOrganization org : orgList) {
					JSONObject node = new JSONObject();
					node.put("id", "orgId");
					node.put("pId", "orgParentId");
					node.put("name", org.getUnitName());
					node.put("isParent", true);
					node.put("iconSkin", "home");
					node.put("nocheck", true);

					treeList.add(node);
				}
			} else {
				if ("orgId".equals(idStr)) {
					idStr = "0";
				}

				List<YZPerson> persons = personLogic.getUserListByDeptSeqId(idStr);
				for (YZPerson p : persons) {
					JSONObject node = new JSONObject();
					node.put("id", "person" + p.getSeqId());
					node.put("pId", idStr);
					node.put("name", p.getUserId()); // 这样做的目的是，同名被选中时，树形菜单反过来可以准确赋值
														// + "[" + p.getUserId()
														// + "]"
					node.put("isParent", false);
					node.put("iconSkin", "person");
					node.put("nocheck", false);
					boolean isCheck = YZUtility.isStrInArrayEach(p.getSeqId(), personIds);
					node.put("checked", isCheck);

					treeList.add(node);
				}

				List<YZDept> deptList = deptLogic.getSubDeptListBySeqId(idStr, null);
				for (YZDept dept : deptList) {
					boolean haveChild = deptLogic.IsHaveChildPerson(dept.getSeqId());
					JSONObject node = new JSONObject();
					node.put("id", dept.getSeqId());
					node.put("pId", dept.getDeptParent());
					node.put("name", dept.getDeptName());
					node.put("isParent", haveChild);
					node.put("nocheck", true);
					treeList.add(node);
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, treeList);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据部门ID进行分页查询
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
			String deptId = request.getParameter("deptId");
			String username = request.getParameter("username");
			
			String queryDeptIds = "";
			if (YZUtility.isNullorEmpty(deptId)) {
				queryDeptIds = deptLogic.getAllDeptIds();
			} else {
				YZDept dept = (YZDept) personLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, deptId);
				if (dept == null) {
					throw new Exception("部门不存在");
				}
				// 如果部门的parentId为0，则展示该门诊的所有患者
				if ("0".equals(dept.getDeptParent())) {
					queryDeptIds = deptLogic.getCurrHosDeptIds(dept);
				} else {
					queryDeptIds = deptId;
				}
			}
			
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());

			JSONObject jobj = personLogic.selectPage(bp, queryDeptIds, username);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("UNIC账户信息", fieldArr, fieldnameArr, jobj.getJSONArray("rows"),
						response, request);
				return null;
			}
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
	@RequestMapping(value = "/selectNoPage.act")
	public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//
		try {

			String deptId = request.getParameter("deptId");
			String seqId = request.getParameter("seqId");
			String showleave = request.getParameter("showleave");

			if (YZUtility.isNullorEmpty(showleave) || showleave.equals("undefined")) {
				showleave = "0";
			}
			// 初始化分页实体类
			List<JSONObject> list = personLogic.selectNoPage(deptId, seqId, showleave);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 根据主键，获取用户基本信息
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
			YZPerson person = (YZPerson) personLogic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, seqId);

			if (person == null) {
				throw new Exception("系统用户不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, JSONObject.fromObject(person).toString());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 可同时删除多个用户
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
			personLogic.deleteBySeqIds(seqId, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 清空密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/clearPassword.act")
	public String clearPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			personLogic.clearPassword(seqId, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
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
	@RequestMapping(value = "/setPassword.act")
	public String setPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			String oldpwd = request.getParameter("oldpwd");
			String newpwd1 = request.getParameter("newpwd1");
			String newpwd2 = request.getParameter("newpwd2");
			if (person == null) {
				String username = request.getParameter("username");
				person = personLogic.getPersonByUserId(username);
			}
			if (person == null) {
				throw new Exception("用户不存在");
			}
			if (!newpwd1.equals(newpwd2)) {
				throw new Exception("两次输入的新密码不一致");
			}

			if (!YZPassEncrypt.isValidPas(oldpwd, person.getPassword())) {
				throw new Exception("输入的原密码错误");
			}

			String pwdNew = YZPassEncrypt.encryptPass(newpwd1);
			person.setPassword(pwdNew);
			personLogic.updateSingleUUID(TableNameUtil.SYS_PERSON, person);
			// 记录日志
			SysLogUtil.log(SysLogUtil.EIDT_PASSWORD, SysLogUtil.SYS_PERSON, person, TableNameUtil.SYS_PERSON, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/*********************************
	 * ##### 以下为合并过来的代码 #####
	 **********************************************************/
	/**
	 * 根据部门获取部门人员列表【用于挂号页面的就诊科室人员选择】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserListByDeptId.act")
	public String getUserListByDeptId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sDeptId = request.getParameter("deptId");
		String showleave = request.getParameter("showleave");
		String organization = request.getParameter("organization");
		if(YZUtility.isNullorEmpty(organization)){
			organization=ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		}
		if (YZUtility.isNullorEmpty(showleave) || showleave.equals("undefined")) {
			showleave = "0";
		}
		try {
			List<YZPerson> list = personLogic.getPersonListByDeptIds(sDeptId, showleave, organization);
			List<JSONObject> newlist = new ArrayList<JSONObject>();
			// 返回相关的用户id,用户名称
			for (YZPerson p : list) {
				JSONObject map = new JSONObject();
				map.put("userId", p.getSeqId() + "");
				map.put("userName", p.getUserName());
				newlist.add(map);
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据 用户seqId 获取用户所在部门
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDeptByUserSeqId.act")
	public String getDeptByUserSeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String seqId = request.getParameter("seqId");
		YZPerson person = null;
		try {

			person = personLogic.getPersonBySeqId(seqId);

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, person.getDeptId());
			jobj.put("xy_dept", person.getXy_dept());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * ############################## 以下代码为空腔大师 前端调用 ############## !!!!!!
	 */
	@RequestMapping(value = "/getPersonListByRoleAndVisual.act")
	public void getPersonListByRoleAndVisual(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			YZPerson person = SessionUtil.getLoginPerson(request);
			String userPriv = request.getParameter("userPriv");
			String visualstaff = SessionUtil.getVisualstaff(request);
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			if (YZUtility.isNullorEmpty(userPriv)) {
				throw new Exception("角色不能为空");
			}
			List<JSONObject> list = personLogic.getPersonListByRoleAndVisual(userPriv, visualstaff, organization);

			List<JSONObject> listper = new ArrayList<JSONObject>();
			for (JSONObject per : list) {
				if (visualstaff.contains(per.getString("seq_id")) || person.getSeqId().equals(per.getString("seq_id"))) {
					listper.add(per);
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put("list", listper);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据角色选择人员，用于下拉框 挂号和日收款统计页面使用 【只查当前门诊的】
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonByRole.act")
	public void getPersonByRole(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String userPriv = request.getParameter("userPriv");
			List<YZPerson> list = personLogic.getPersonListByRole(userPriv, ChainUtil.getCurrentOrganization(request));
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据部门选择人员，用于下拉框 挂号页面使用 【只查当前门诊的】
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonListByDeptType.act")
	public void getPersonListByDeptType(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String depttype = request.getParameter("depttype");
			String organization= request.getParameter("organization");
			if(YZUtility.isNullorEmpty(organization)){
				organization = ChainUtil.getCurrentOrganization(request);
			}
			List<JSONObject> list = personLogic.getPersonListByDeptType(depttype,organization);
			List<JSONObject> list1=new ArrayList<JSONObject>();
			if(organization.equals("HUDX")){
				list1 = personLogic.getPersonListByDeptType1(depttype,organization);
			}
			if(list1.size()>0){
				list.addAll(list1);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据部门选择人员，用于下拉框(可见人员过滤) 挂号页面使用 【只查当前门诊的】
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonListByDeptTypeAndVisual.act")
	public void getPersonListByDeptTypeAndVisual(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String depttype = request.getParameter("depttype");
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> list = personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 验证人员是否是当前门诊的
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yzUser.act")
	public String yzUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String ids = request.getParameter("ids"); // 查询条件
		try {

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			boolean data = personLogic.yzUserBySeqIds(ids, organization);
			JSONObject jobj = new JSONObject();
			jobj.put("data", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/getPersonNameStrBySeqIds.act")
	public String getPersonNameStrBySeqIds(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String ids = request.getParameter("ids"); // 查询条件
		try {

			String data = personLogic.getNameStrBySeqIds(ids);
			JSONObject jobj = new JSONObject();
			jobj.put("data", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断是否超过限定人数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             data = 1 已达到最大人数， 0 未达到最大人数
	 */

	@RequestMapping(value = "/getMaxuser.act")
	public String getMaxuser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int maxUserAccount = Integer.parseInt(YZSysProps.getProp(SysParaUtil.MAX_USER_ACCOUNT));
			int count = onlineLogic.queryUserCount();
			String data = "";
			if (maxUserAccount <= count) {
				data = "1"; // 1 已达到最大人数， 0 未达到最大人数
			} else {
				data = "0";
			}
			JSONObject rtjson = new JSONObject();
			rtjson.put("data", data);
			YZUtility.DEAL_SUCCESS(rtjson, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据部门类型部门人员列表 deptType 0 咨询 1医生 2网电 3营销 4 护士，可多个，比如 0,1
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserListByDeptType.act")
	public String getUserListByDeptType(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String deptType = request.getParameter("deptType");

		if (YZUtility.isNullorEmpty(deptType)) {
			YZUtility.DEAL_ERROR("部门类型不能传空值", false, null, response, logger);
			return null;
		}
		deptType = YZUtility.ConvertStringIds4Query(deptType);

		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		if (YZUtility.isNullorEmpty(organization)) {
			organization = ChainUtil.getCurrentOrganization(request);
		}

		try {

			List<JSONObject> deptList = deptLogic.getDeptSeqIdByTypeAndOrg(organization, deptType);

			StringBuffer deptIdStrs = new StringBuffer();
			for (JSONObject dept : deptList) {
				String seq_id = dept.getString("seq_id");
				if (!YZUtility.isNullorEmpty(seq_id)) { // 值不为空
					deptIdStrs.append(seq_id).append(",");
				}
			}

			if (deptIdStrs.length() > 0) {
				deptIdStrs.deleteCharAt(deptIdStrs.length() - 1);
			}

			if (YZUtility.isNullorEmpty(deptIdStrs.toString())) {
				YZUtility.DEAL_ERROR("不存在与传入部门类型匹配的部门", false, null, response, logger);
				return null;
			}

			List<JSONObject> list = personLogic.getPersonIdListByDeptIds(deptIdStrs.toString());
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据用户ID获取用户所在门诊编号 用于同一个门诊，不允许编辑咨询人员
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getOrganizationByPersonSeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String seqId = request.getParameter("seqId");
		try {
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("seqId值为空");
			}

			YZPerson person = (YZPerson) personLogic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, seqId);

			if (person == null) {
				throw new Exception("系统用户不存在");
			}

			String deptId = person.getDeptId();

			YZDept dpt = deptLogic.getTopDept(deptId);

			if (dpt == null) {
				throw new Exception("系统用户所在门诊不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put("data", dpt.getDeptCode());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 下载模板
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/excelStandardTemplateOut.act")
	public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File f = new File(ConstUtil.ROOT_DIR + "\\model\\部门用户模板.xls");
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("部门用户模板" + ".xls").getBytes(), "iso-8859-1"));// 下载文件的名称
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(f));
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	/**
	 * 
	  * @Title: findDeptnameByPerson   
	  * @Description: TODO(根据登录人查询所在部门)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年10月9日 下午2:37:28
	 */
	@RequestMapping(value = "/findDeptnameByPerson.act")
	public String findDeptnameByPerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		String seqId = person.getDeptId();
		try {
			 JSONObject deptName = deptLogic.findDeptNameBySeqId(seqId);
			YZUtility.DEAL_SUCCESS(deptName, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: findVisualPersonnel   
	  * @Description: TODO(根据部门Id初始化人员)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年11月8日 下午5:02:06
	 */
	@RequestMapping("/findVisualPersonnel.act")
	public String findVisualPersonnel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String deptId = request.getParameter("deptId");
		try {
			List<JSONObject> findVisualPersonnel = personLogic.findVisualPersonnel("'"+deptId+"'");
			YZUtility.RETURN_LIST(findVisualPersonnel, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	 * 修改人员门诊
	 * <p>Title: updateOrganization</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月7日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateOrganization.act")
	public String updateOrganization(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String organization = request.getParameter("organization");
		String seqid= request.getParameter("seqid");
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(YZUtility.isNotNullOrEmpty(seqid)){
				map.put("seqid", seqid);
			}
			if(YZUtility.isNotNullOrEmpty(organization)){
				map.put("organization", organization);
			}
			int i = personLogic.updateOrganization(map);
			if(i>0){
				YZUtility.DEAL_SUCCESS(null, "修改成功", response, logger);
			}else{
				YZUtility.DEAL_ERROR("修改失败", false, null, response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	@RequestMapping("/findIsLeaveBySeqId.act")
	public String findIsLeaveBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String seqId = request.getParameter("seqId");
		try {
			String str = personLogic.findPersonIsleaveBySeqId(seqId);
			YZUtility.DEAL_SUCCESS(null, str, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
