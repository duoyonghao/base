package com.kqds.controller.sys.dept;

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
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZOrganization;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.organization.YZOrganizationLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/YZDeptAct")
public class YZDeptAct {
	private Logger logger = LoggerFactory.getLogger(YZDeptAct.class);
	@Autowired
	private YZOrganizationLogic organizationLogic;
	@Autowired
	private YZDeptLogic deptLogic;
	@Autowired
	private YZParaLogic paraLogic;

	@RequestMapping(value = "/toIndex.act")
	public String toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/dept/index.jsp";
	}

	@RequestMapping(value = "/toUnitIndex.act")
	public String toUnitIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/unit/index.jsp";
	}

	@RequestMapping(value = "/toSelectMultiIndex.act")
	public String toSelectMultiIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/dept/multi_select/index.jsp";
	}

	@RequestMapping(value = "/toSelectTop.act")
	public String toSelectTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/dept/single_select/top.jsp";
	}

	@RequestMapping(value = "/toSelectLeft.act")
	public String toSelectLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/dept/single_select/left.jsp";
	}

	@RequestMapping(value = "/toSelectList.act")
	public ModelAndView toSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptId = request.getParameter("deptId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("deptId", deptId);
		mv.setViewName("/admin/dept/single_select/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSuccess.act")
	public String toSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/inc/success.jsp";
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptParent = request.getParameter("deptParent");
		String deptCode = request.getParameter("deptCode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("deptParent", deptParent);
		mv.addObject("deptCode", deptCode);
		mv.setViewName("/admin/dept/newAdd.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTop.act")
	public String toTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/dept/top.jsp";
	}

	@RequestMapping(value = "/toLeft.act")
	public String toLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/dept/left.jsp";
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptId = request.getParameter("deptId");
		String deptCode = request.getParameter("deptCode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("deptId", deptId);
		mv.addObject("deptCode", deptCode);
		mv.setViewName("/admin/dept/edit.jsp");
		return mv;
	}

	/**
	 * 新增、编辑部门
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

			YZDept dp = new YZDept();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(dp.getDeptName())) {
				throw new Exception("部门名称不能为空");
			}
			if (YZUtility.isNullorEmpty(dp.getDeptCode())) {
				throw new Exception("部门编号不能为空");
			}
			if (!YZUtility.isNullorEmpty(seqId)) {
				YZDept tmpDept = (YZDept) deptLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, seqId);
				if (tmpDept == null) {
					throw new Exception("部门不存在");
				}
				int num = deptLogic.countByDeptNameCurrHos(dp);
				if (num >= 1) {
					throw new Exception("同一个门诊，部门名称不允许重复");
				}
				// 这几个字段修改时，不更新
				dp.setDeptParent(tmpDept.getDeptParent());
				dp.setCreatetime(tmpDept.getCreatetime());
				dp.setCreateuser(tmpDept.getCreateuser());
				// 这几个字段修改时，不更新 end..
				deptLogic.updateSingleUUID(TableNameUtil.SYS_DEPT, dp);

				if ("0".equals(dp.getDeptParent()) && (dp.getOrgflag() == 1)) {
					/** 导入的时候，初始化系统参数表 **/
					paraLogic.initParaByOrganization(dp.getDeptCode());
				}

				// 记录日志
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_DEPT, dp, TableNameUtil.SYS_DEPT, request);
			} else {
				if ("0".equals(dp.getDeptParent())) {
					int num = deptLogic.countByDeptCode(dp);
					if (num >= 1) {
						throw new Exception("一级部门，部门编号不允许重复");
					}
					if ((1 == dp.getOrgflag())) {
						/** 导入的时候，初始化系统参数表 **/
						paraLogic.initParaByOrganization(dp.getDeptCode());
					}
				}
				int num = deptLogic.countByDeptNameCurrHos(dp);
				if (num >= 1) {
					throw new Exception("同一个门诊，部门名称不允许重复");
				}
				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				deptLogic.saveSingleUUID(TableNameUtil.SYS_DEPT, dp);

				// 记录日志
				SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_DEPT, dp, TableNameUtil.SYS_DEPT, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据主键，获取基本信息
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
			YZDept person = (YZDept) deptLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, seqId);

			if (person == null) {
				throw new Exception("部门不存在");
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
	 * 删除部门
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
			boolean ischild = deptLogic.IsHaveChild(seqId);
			if (ischild) {
				throw new Exception("当前部门存在子部门，不允许删除");
			}
			ischild = deptLogic.IsHaveChildPerson(seqId);
			if (ischild) {
				throw new Exception("当前部门存在用户，不允许删除");
			}
			deptLogic.deleteBySeqIds(seqId, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 一次性查出所有部门树【一次加载完】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDeptTree4All.act")
	public String getDeptTree4All(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptIds = request.getParameter("deptIds"); // 传参已被选中的人员

		List<JSONObject> treeList = new ArrayList<JSONObject>();

		try {

			List<YZOrganization> orgList = organizationLogic.getOrganizationList();
			for (YZOrganization org : orgList) {
				JSONObject node = new JSONObject();
				node.put("id", "orgId");
				node.put("pId", "orgParentId");
				node.put("name", org.getUnitName());
				node.put("isParent", true);
				node.put("iconSkin", "home");
				node.put("nocheck", false);

				treeList.add(node);
			}

			treeList = deptLogic.getDeptNodeList("0", treeList, deptIds);
			for (JSONObject job : treeList) {
				String id = job.getString("id");
				boolean check = YZUtility.isStrInArrayEach(id, deptIds);
				job.put("checked", check);
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
	 * 获取部门树【动态加载】 该方法和人员树的区别是，没显示人员数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDeptTree.act")
	public String getDeptTree(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String idStr = request.getParameter("id");
		String isSingle = request.getParameter("isSingle");
		String organization = null;

		// 判断是否只显示当前门诊
		if (!YZUtility.isNullorEmpty(isSingle) && "single".equals(isSingle)) {
			organization = ChainUtil.getCurrentOrganization(request);
		}
		// 试用版本(查询登录人的门诊编号)
		if (ChainUtil.isOpenTry()) {
			organization = ChainUtil.getCurrentOrganization(request);
		}

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

				List<YZDept> deptList = deptLogic.getSubDeptListBySeqId(idStr, organization);
				for (YZDept dept : deptList) {
					boolean haveChild = deptLogic.IsHaveChild(dept.getSeqId());
					JSONObject node = new JSONObject();
					node.put("id", dept.getSeqId());
					node.put("pId", dept.getDeptParent());
					node.put("name", dept.getDeptName());
					node.put("isParent", haveChild);
					node.put("code", dept.getDeptCode()); // 部门编号
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
	 * 只查询 门诊类型的部门列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDeptTreeOnlyOrg.act")
	public String getDeptTreeOnlyOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String idStr = request.getParameter("id");
		String isSingle = request.getParameter("isSingle");
		String depttype = request.getParameter("depttype"); // 部门类型
		String organization = null;

		// 判断是否只显示当前门诊
		if (!YZUtility.isNullorEmpty(isSingle) && "single".equals(isSingle)) {
			organization = ChainUtil.getCurrentOrganization(request);
		}
		// 试用版本(查询登录人的门诊编号)
		if (ChainUtil.isOpenTry()) {
			organization = ChainUtil.getCurrentOrganization(request);
		}
		organization = ChainUtil.getCurrentOrganization(request);//添加门诊，人员树按门诊查询展示 2019-10-21 syp
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
				// 只查询部门类型为 门诊的
				List<YZDept> deptList = deptLogic.getSubOrgDeptListBySeqId(idStr, organization, depttype);
				for (YZDept dept : deptList) {
					boolean haveChild = deptLogic.IsHaveChild(dept.getSeqId());
					JSONObject node = new JSONObject();
					node.put("id", dept.getSeqId());
					node.put("pId", dept.getDeptParent());
					node.put("name", dept.getDeptName());
					node.put("isParent", haveChild);
					node.put("code", dept.getDeptCode()); // 部门编号
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
	 * 获取部门下拉树【下拉框】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectDeptTree.act")
	public String getSelectDeptTree(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String deptTreeListStr = deptLogic.getSelectDeptTree("0", null);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, deptTreeListStr);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	/**
	 * 获取部门下拉树【下拉框】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectOrganization.act")
	public String getSelectOrganization(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<YZDept> deptTreeListStr = deptLogic.getSelectOrganization();
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, deptTreeListStr);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据部门主键数组，获取部门树列表【适用于 挂号，人员高级选择】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDeptTreeByDeptIds.act")
	public String getDeptTreeByDeptIds(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String idStr = request.getParameter("deptIds");
		String id = request.getParameter("id");

		try {

			List<JSONObject> treeList = new ArrayList<JSONObject>();
			if (YZUtility.isNullorEmpty(id)) {
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
				List<YZDept> deptList = deptLogic.getDeptListBySeqIds(idStr);
				for (YZDept dept : deptList) {
					JSONObject node = new JSONObject();
					node.put("id", dept.getSeqId());
					node.put("pId", "orgId");
					node.put("name", dept.getDeptName());
					node.put("isParent", false);
					node.put("code", dept.getDeptCode()); // 部门编号
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

	/***********************
	 * ###### 合并过来的代码 start #####
	 ***********************************/

	/**
	 * 【前端调用】 util.js调用
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDeptNameBySeqId.act")
	public void getDeptNameBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");

			YZDept dept = (YZDept) deptLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, seqId);
			if (dept == null) {
				throw new Exception(" 部门不存在");
			}

			String data = dept.getDeptName();
			JSONObject jobj = new JSONObject();
			jobj.put("data", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 这个方法需要注意的是 dept_parent = 0 目前的目录结构是 单位下一级 ，放一个 一级菜单， 比如好大夫口腔 【前端调用】 util.js调用
	 * 
	 * @param conn
	 * @param deptcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTopDeptName.act")
	public void getTopDeptName(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String deptcode = request.getParameter("deptcode");
			String data = deptLogic.getTopDeptName(deptcode);
			JSONObject jobj = new JSONObject();
			jobj.put("data", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 查询所有部门 【前端调用】 util.js调用
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllDeptByOrganization.act")
	public void getAllDeptByOrganization(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}

			List<YZDept> list = deptLogic.getAllDeptByOrganization(organization);
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据部门类型初始化部门列表，可多个
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDeptListByDeptType.act")
	public String getDeptListByDeptType(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String typeIds = request.getParameter("typeIds");
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		if (YZUtility.isNullorEmpty(organization)) {
			organization = ChainUtil.getCurrentOrganization(request);
		}

		try {

			List<YZDept> list = deptLogic.getDeptListByDeptType(typeIds, organization);
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据部门ID 获取部门名称,支持多个 【前端调用】 util.js调用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDeptNamesBySeqIds.act")
	public String getDeptNamesBySeqIds(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String seqIds = request.getParameter("seqIds"); // 查询条件
		try {

			String data = deptLogic.getDeptNamesBySeqIds(seqIds);
			JSONObject rtjson = new JSONObject();
			rtjson.put("data", data);
			YZUtility.DEAL_SUCCESS(rtjson, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/***********************
	 * ###### 合并过来的代码 end #####
	 ***********************************/
	/**
	 * 查询所有部门和根据部门类别查询部门
	 * <p>Title: findDeptByDeptType</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月29日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findDeptByDeptType.act")
	public String findDeptByDeptType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		if(YZUtility.isNullorEmpty(organization)){
			organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
		}
		String deptType = request.getParameter("deptType"); // 查询条件
		try {
			List<JSONObject> list=new ArrayList<JSONObject>();
			Map<String,String> map =new  HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(deptType)){
				map.put("deptType", deptType);
				if(deptType.equals("all")){
					list =deptLogic.findAllDeptByDeptType(organization);
				}else{
					list = deptLogic.findDeptByDeptType(map);
				}
				
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: findDeptList   
	  * @Description: TODO(查询回访部门)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年11月8日 上午11:18:41
	 */
	@RequestMapping("/findDeptList.act")
	public String findDeptList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String organization=ChainUtil.getCurrentOrganization(request);
			Map<String,String> map= new HashMap<String,String>();
			if(organization!=null){
				map.put("organization",organization);
			}
			List<YZDept> findDeptList = deptLogic.findDeptList(map);
			YZUtility.RETURN_LIST(findDeptList,response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: getListByParentCodeALL   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年11月9日 下午6:59:16
	 */
	@RequestMapping(value = "/getListALL.act")
	public void getListALL(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String organization = request.getParameter("organization");
		try {
			Map <String, String>map = new HashMap<String,String>();
			map.put("organization", organization);
			List<YZDict> list = null;
			list = deptLogic.getListALL(map);
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}
}
