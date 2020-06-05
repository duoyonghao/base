package com.kqds.controller.sys.dict;

import java.util.ArrayList;
import java.util.List;

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
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.jzmdType.KQDS_Jzmd_TypeLogic;
import com.kqds.service.base.util.UtilLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZDictAct")
public class YZDictAct {

	private Logger logger = LoggerFactory.getLogger(YZDictAct.class);

	@Autowired
	private YZDictLogic dictLogic;

	@Autowired
	private UtilLogic utilLogic;

	@Autowired
	private KQDS_Jzmd_TypeLogic jzmdTypeLogic;

	@RequestMapping(value = "/toIndex.act")
	public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/dict/index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSelectMultiIndex.act")
	public String toSelectMultiIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/dict/multi_select/index.jsp";
	}

	@RequestMapping(value = "/toTop.act")
	public String toContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/dict/top.jsp";
	}

	@RequestMapping(value = "/toLeft.act")
	public String toLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/dict/left.jsp";
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.setViewName("/admin/dict/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlct.act")
	public String toBlct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/kqds/blct/index.jsp";
	}

	@RequestMapping(value = "/toDxfl.act")
	public String toDxfl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/kqds/dxfl/index.jsp";
	}

	@RequestMapping(value = "/toJzmd.act")
	public String toJzmd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/kqds/jzmd/index.jsp";
	}

	@RequestMapping(value = "/toBlfl.act")
	public String toBlfl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/kqds/blfl/index.jsp";
	}

	@RequestMapping(value = "/toHzly.act")
	public String toHzly(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/kqds/hzly/index.jsp";
	}

	@RequestMapping(value = "/toTreatItemType.act")
	public String toTreatItemType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/kqds/treatItemType/index.jsp";
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/admin/dict/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.setViewName("/admin/dict/newAdd.jsp");
		return mv;
	}

	// 病历分类
	@RequestMapping(value = "/toBlflSubIndex.act")
	public ModelAndView toBlflSubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/blfl/subindex.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlflList.act")
	public ModelAndView toBlflList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/blfl/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlflLeft.act")
	public ModelAndView toBlflLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/blfl/left.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlflEdit.act")
	public ModelAndView toBlflEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/blfl/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlflNewAdd.act")
	public ModelAndView toBlflNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/blfl/newAdd.jsp");
		return mv;
	}

	// 短信分类
	@RequestMapping(value = "/toDxflSubIndex.act")
	public ModelAndView toDxflSubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/dxfl/subindex.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDxflList.act")
	public ModelAndView toDxflList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/dxfl/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDxflLeft.act")
	public ModelAndView toDxflLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/dxfl/left.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDxflEdit.act")
	public ModelAndView toDxflEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/dxfl/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDxflNewAdd.act")
	public ModelAndView toDxflNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/dxfl/newAdd.jsp");
		return mv;
	}

	// 患者来源
	@RequestMapping(value = "/toHzlySubIndex.act")
	public ModelAndView toHzlySubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/hzly/subindex.jsp");
		return mv;
	}

	@RequestMapping(value = "/toHzlyList.act")
	public ModelAndView toHzlyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/hzly/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toHzlyLeft.act")
	public ModelAndView toHzlyLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/hzly/left.jsp");
		return mv;
	}

	@RequestMapping(value = "/toHzlyEdit.act")
	public ModelAndView toHzlyEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/hzly/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toHzlyNewAdd.act")
	public ModelAndView toHzlyNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/hzly/newAdd.jsp");
		return mv;
	}

	// 就诊目的
	@RequestMapping(value = "/toJzmdSubIndex.act")
	public ModelAndView toJzmdSubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/jzmd/subindex.jsp");
		return mv;
	}

	@RequestMapping(value = "/toJzmdList.act")
	public ModelAndView toJzmdList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/jzmd/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toJzmdLeft.act")
	public ModelAndView toJzmdLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/jzmd/left.jsp");
		return mv;
	}

	@RequestMapping(value = "/toJzmdEdit.act")
	public ModelAndView toJzmdEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/jzmd/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toJzmdNewAdd.act")
	public ModelAndView toJzmdNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/jzmd/newAdd.jsp");
		return mv;
	}

	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			YZDict dp = new YZDict();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(dp.getParentCode())) {
				throw new Exception("上级编号不能为空");
			}
			if (YZUtility.isNullorEmpty(dp.getDictName())) {
				throw new Exception("字典名称不能为空");
			}

			// 获取顶级字典
			// YZDict topDict = dictLogic.getTopDict(dp);
			/*
			 * if ("FKFS".equals(topDict.getDictCode())) { throw new
			 * Exception("付款方式不允许操作！"); }
			 */

			// if (YZUtility.isNullorEmpty(dp.getDictCode())) { /** 编号自动生成，不手工填写
			// **/
			// throw new Exception("字典编号不能为空");
			// }
			if (YZUtility.isNullorEmpty(dp.getOrganization())) {
				/** 所属门诊可以为空，空表示公用 **/
				// throw new Exception("所属门诊不能为空");
				dp.setOrganization("");
			}
			// int num = dictLogic.countByCode( dp); /**
			// 通过代码，确保手工填写的编号不重复 **/
			// if (num >= 1) {
			// throw new Exception("编号重复, 请重新填写");
			// }

			// if("XXKFL".equals(dp.getParentCode())){ // 信息库分类，不区分门诊
			// num = dictLogic.countByName( dp, dp.getOrganization());
			// }else{
			//
			// }

			// num = dictLogic.countByName( dp); /** 字典名称可以重复 **/
			// if (num >= 1) {
			// throw new Exception("名称重复, 请重新填写");
			// }

			if (!YZUtility.isNullorEmpty(seqId)) {
				YZDict tmp = (YZDict) dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, seqId);
				if (tmp == null) {
					throw new Exception("记录不存在");
				}

				// 这几个字段修改时，不更新
				dp.setParentCode(tmp.getParentCode());
				dp.setDictCode(tmp.getDictCode());
				dp.setUseflag(tmp.getUseflag());
				dp.setCreatetime(tmp.getCreatetime());
				dp.setCreateuser(tmp.getCreateuser());
				// 这几个字段修改时，不更新 end..
				dictLogic.updateSingleUUID(TableNameUtil.SYS_DICT, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_DICT, dp, TableNameUtil.SYS_DICT, request);
			} else {
				String uuid = dictLogic.getUniDictCodeByName(dp.getDictName()); // YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setDictCode(uuid);
				dp.setUseflag(0); // 默认启用
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				// dp.setOrganization(ChainUtil.getCurrentOrganization(request));
				dictLogic.saveSingleUUID(TableNameUtil.SYS_DICT, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_DICT, dp, TableNameUtil.SYS_DICT, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 获取字典树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictTree.act")
	public String getDictTree(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String idStr = request.getParameter("id");
		if (YZUtility.isNullorEmpty(idStr)) {
			idStr = "0";
		}

		try {

			List<JSONObject> list = dictLogic.getSubListByParentCode(idStr, request);
			for (JSONObject obj : list) {
				// boolean haveChild = dictLogic.IsHaveChild(
				// obj.getString("dictCode"));
				int count = dictLogic.countByParentCode(obj.getString("dictCode"));
				String dictName = obj.getString("dictName");
				if (count > 0) {
					// dictName +=
					// "<span style='color:red;' title='存在非公共数据，选择右侧门诊下拉框进行查看'>【"
					// + count + "】</span>";
					dictName += "【" + count + "】";
					obj.put("icon", request.getContextPath() + "/static/image/admin/dict/folder.gif");
				} else {
					obj.put("icon", request.getContextPath() + "/static/image/admin/dict/file.jpg");
				}
				obj.put("id", obj.getString("dictCode"));
				obj.put("pId", obj.getString("parentCode"));
				obj.put("name", dictName);
				obj.put("isParent", false);
				/** 2018-02-26 修改 **/

			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 收费项目分类管理专用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictTree4TreatItemType.act")
	public String getDictTree4TreatItemType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		if (YZUtility.isNullorEmpty(idStr)) {
			idStr = "COSTITEM_SORT";
		}

		try {

			List<JSONObject> list = dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
			for (JSONObject obj : list) {
				boolean haveChild = dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
				int count = dictLogic.countByParentCode(obj.getString("dictCode"), organization);
				String dictName = obj.getString("dictName");
				if (count > 0) {
					// dictName +=
					// "<span style='color:red;' title='存在非公共数据，选择右侧门诊下拉框进行查看'>【"
					// + count + "】</span>";
					dictName += "【" + count + "】";
				}
				obj.put("id", obj.getString("dictCode"));
				obj.put("pId", obj.getString("parentCode"));
				obj.put("name", dictName);
				obj.put("isParent", haveChild);
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 患者来源分类管理专用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictTree4HZLY.act")
	public String getDictTree4HZLY(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		if (YZUtility.isNullorEmpty(idStr)) {
			idStr = DictUtil.HZLY;
		}

		try {

			List<JSONObject> list = dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
			for (JSONObject obj : list) {
				boolean haveChild = dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
				int count = dictLogic.countByParentCode(obj.getString("dictCode"), organization);
				String dictName = obj.getString("dictName");
				if (count > 0) {
					// dictName +=
					// "<span style='color:red;' title='存在非公共数据，选择右侧门诊下拉框进行查看'>【"
					// + count + "】</span>";
					dictName += "【" + count + "】";
				}
				obj.put("id", obj.getString("dictCode"));
				obj.put("pId", obj.getString("parentCode"));
				obj.put("name", dictName);
				obj.put("isParent", haveChild);
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 病历词条分类管理专用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictTree4Blct.act")
	public String getDictTree4Blct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		if (YZUtility.isNullorEmpty(idStr)) {
			idStr = "blct124";
		}

		try {

			List<JSONObject> list = dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
			for (JSONObject obj : list) {
				boolean haveChild = dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
				int count = dictLogic.countByParentCode(obj.getString("dictCode"), organization);
				String dictName = obj.getString("dictName");
				if (count > 0) {
					// dictName +=
					// "<span style='color:red;' title='存在非公共数据，选择右侧门诊下拉框进行查看'>【"
					// + count + "】</span>";
					dictName += "【" + count + "】";
				}
				obj.put("id", obj.getString("dictCode"));
				obj.put("pId", obj.getString("parentCode"));
				obj.put("name", dictName);
				obj.put("isParent", haveChild);
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 病历分类管理专用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictTree4BLFL.act")
	public String getDictTree4BLFL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		if (YZUtility.isNullorEmpty(idStr)) {
			idStr = "BLFL";
		}

		try {

			List<JSONObject> list = dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
			for (JSONObject obj : list) {
				boolean haveChild = dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
				int count = dictLogic.countByParentCode(obj.getString("dictCode"), organization);
				String dictName = obj.getString("dictName");
				if (count > 0) {
					// dictName +=
					// "<span style='color:red;' title='存在非公共数据，选择右侧门诊下拉框进行查看'>【"
					// + count + "】</span>";
					dictName += "【" + count + "】";
				}
				obj.put("id", obj.getString("dictCode"));
				obj.put("pId", obj.getString("parentCode"));
				obj.put("name", dictName);
				obj.put("isParent", haveChild);
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 就诊目的管理专用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictTree4JZMD.act")
	public String getDictTree4JZMD(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		if (YZUtility.isNullorEmpty(idStr)) {
			idStr = "JZMD";
		}

		try {

			List<JSONObject> list = dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
			for (JSONObject obj : list) {
				boolean haveChild = dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
				int count = dictLogic.countByParentCode(obj.getString("dictCode"), organization);
				String dictName = obj.getString("dictName");
				if (count > 0) {
					// dictName +=
					// "<span style='color:red;' title='存在非公共数据，选择右侧门诊下拉框进行查看'>【"
					// + count + "】</span>";
					dictName += "【" + count + "】";
				}
				obj.put("id", obj.getString("dictCode"));
				obj.put("pId", obj.getString("parentCode"));
				obj.put("name", dictName);
				obj.put("isParent", haveChild);
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 短信分类专用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictTree4DXFL.act")
	public String getDictTree4DXFL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
		if (YZUtility.isNullorEmpty(idStr)) {
			idStr = "DXFL";
		}

		try {

			List<JSONObject> list = dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
			for (JSONObject obj : list) {
				boolean haveChild = dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
				int count = dictLogic.countByParentCode(obj.getString("dictCode"), organization);
				String dictName = obj.getString("dictName");
				if (count > 0) {
					// dictName +=
					// "<span style='color:red;' title='存在非公共数据，选择右侧门诊下拉框进行查看'>【"
					// + count + "】</span>";
					dictName += "【" + count + "】";
				}
				obj.put("id", obj.getString("dictCode"));
				obj.put("pId", obj.getString("parentCode"));
				obj.put("name", dictName);
				obj.put("isParent", haveChild);
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据数据字典一级分类，一次性加载所有节点
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictTree4All.act")
	public String getDictTree4All(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = ChainUtil.getCurrentOrganization(request);
		String dictIds = request.getParameter("dictIds"); // 传参已被选中的人员
		String parentCode = request.getParameter("parentCode");
		if (YZUtility.isNullorEmpty(parentCode)) {
			parentCode = "0";
		}

		List<JSONObject> treeList = new ArrayList<JSONObject>();

		try {

			treeList = dictLogic.getDictNodeList(parentCode, treeList, dictIds,organization);
			for (JSONObject job : treeList) {
				String id = job.getString("id");
				boolean check = YZUtility.isStrInArrayEach(id, dictIds);
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
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//
		try {

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				// organization = ChainUtil.getCurrentOrganization(request); //
				// 注释by yangsen 17-12-24
			}

			String parentCode = request.getParameter("parentCode");
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject jobj = dictLogic.selectPage(bp, parentCode, organization);
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
	@RequestMapping(value = "/updateFlagBySeqIds.act")
	public String updateFlagBySeqIds(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");
			String flag = request.getParameter("flag");
			if (!("0".equals(flag) || "1".equals(flag))) { // 如果flag 不等于0 或者 1
				throw new Exception("状态标识不正确");
			}

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			dictLogic.updateFlagBySeqIds(seqId, flag, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 单个删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBySeqId.act")
	public String deleteBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			YZDict dict = (YZDict) dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, seqId);
			if (dict == null) {
				throw new Exception("数据不存在");
			}

			// 获取顶级字典
			YZDict topDict = dictLogic.getTopDict(dict);

			// 删除回访分类时，判断是否存在回访设置
			if (DictUtil.HFFL.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_VISIT_SET, "hffl", dict.getSeqId());
				if (count > 0) {
					throw new Exception("回访分类存在回访设置，无法删除！");
				}

				count = utilLogic.selectCount(TableNameUtil.KQDS_VISIT, "hffl", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该回访分类下存在回访记录，无法删除！");
				}
			}

			// 信息库分类
			if (DictUtil.XXKFL.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_INFORMATION, "type", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该分类下存在信息记录，无法删除！");
				}
			}

			// 种植系统
			if (DictUtil.ZZXT.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_ROOM, "zzxt", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该分类下存在预约手术记录，无法删除！");
				}
			}

			// 手术室
			if (DictUtil.SHOUSS.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_ROOM, "roomid", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该手术室存在预约手术记录，无法删除！");
				}
			}

			// 预约分类
			if (DictUtil.YYFL.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_HOSPITAL_ORDER, "ordertype", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该预约分类存在预约记录，无法删除！");
				}
			}

			// 门诊预约项目分类
			if (DictUtil.YYXM.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_HOSPITAL_ORDER, "orderitemtype", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该预约项目分类存在预约记录，无法删除！");
				}
			}

			// 潜在开发项目
			if (DictUtil.QZKFXM.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_RECEIVEINFO, "dev_item", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该分类下存在咨询记录，无法删除！");
				}
			}

			// 未成交原因
			if (DictUtil.WCJYY.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_RECEIVEINFO, "failreason1", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该分类下存在咨询记录，无法删除！");
				}
			}

			// 处方单用法
			if (DictUtil.CHUFANG_YF.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_CHUFANG_DETAIL, "cfusage", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该分类下存在处方单，无法删除！");
				}
			}

			// 处方_用药途径
			if (DictUtil.CHUFANG_YYTJ.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_CHUFANG_DETAIL, "cfusemethod", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该分类下存在处方单，无法删除！");
				}
			}

			// 受理类型
			if (DictUtil.SLLX.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_NET_ORDER, "accepttype", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该受理类型下存在网电预约记录，无法删除！");
				}
			}

			// 受理工具
			if (DictUtil.SLGJ.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_NET_ORDER, "accepttool", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该受理工具下存在网电预约记录，无法删除！");
				}
			}

			// 咨询项目
			if (DictUtil.ZXXM.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_NET_ORDER, "askitem", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该咨询项目下存在网电预约记录，无法删除！");
				}
			}

			// 会员卡级别
			if (DictUtil.HYKFL.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_MEMBER, "memberlevel", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该级别下存在会员卡，无法删除！");
				}
			}

			// 挂号分类
			if (DictUtil.GHFS.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_REG, "regsort", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该挂号分类存在挂号记录，无法删除！");
				}
			}

			// 就诊分类
			if (DictUtil.JZFL.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_REG, "recesort", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该就诊分类存在挂号记录，无法删除！");
				}
			}

			// 职业
			if (DictUtil.JOB.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_USERDOCUMENT, "profession", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该分类下存在患者档案，无法删除！");
				}
			}

			// 微信图文分类
			if (DictUtil.WECHAT_NEWSSORT.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.WX_NEWS, "newstype", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该分类下存在图文信息，无法删除！");
				}
			}

			// 病历库分类
			if (DictUtil.BLKFL.equals(topDict.getDictCode())) {
				int count = utilLogic.selectCount(TableNameUtil.KQDS_BLK, "blkfl", dict.getSeqId());
				if (count > 0) {
					throw new Exception("该分类下存在病历模板，无法删除！");
				}
			}

			// 病历分类/病程
			if (DictUtil.BLFL.equals(topDict.getDictCode())) {
				if (DictUtil.BLFL.equals(dict.getParentCode())) { // 病历分类
					int count = utilLogic.selectCount(TableNameUtil.KQDS_MEDICALRECORD, "blfl", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在病历，无法删除！");
					}
				} else {
					// 病程
					int count = utilLogic.selectCount(TableNameUtil.KQDS_MEDICALRECORD, "bc", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该病程下存在病历，无法删除！");
					}
				}
			}

			// 短信分类
			if (DictUtil.DXFL.equals(topDict.getDictCode())) {
				if (DictUtil.DXFL.equals(dict.getParentCode())) { // 病历分类
					int count = utilLogic.selectCount(TableNameUtil.KQDS_SMS_MODEL, "smstype", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在短信模板，无法删除！");
					}
				} else {
					// 病程
					int count = utilLogic.selectCount(TableNameUtil.KQDS_SMS_MODEL, "smsnexttype", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在短信模板，无法删除！");
					}
				}
			}

			// 就诊目的
			if (DictUtil.JZMD.equals(topDict.getDictCode())) {
				if (DictUtil.JZMD.equals(dict.getParentCode())) { // 病历分类
					int count = utilLogic.selectCount(TableNameUtil.KQDS_JZMD_TYPE, "jzmd", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在就诊目的设置，无法删除！");
					}

					count = utilLogic.selectCount(TableNameUtil.KQDS_JZQK, "reggoal", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在就诊情况，无法删除！");
					}
				} else {
					// 病程
					int count = jzmdTypeLogic.selectCountLocal(dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在就诊目的设置，无法删除！");
					}

					count = utilLogic.selectCount(TableNameUtil.KQDS_JZQK, "jzmd", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在就诊情况，无法删除！");
					}
				}
			}

			// 患者来源/来源子分类
			if (DictUtil.HZLY.equals(topDict.getDictCode())) {
				if (DictUtil.HZLY.equals(dict.getParentCode())) { // 病历分类
					int count = utilLogic.selectCount(TableNameUtil.KQDS_USERDOCUMENT, "devchannel", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在患者档案，无法删除！");
					}
				} else {
					// 来源子分类
					int count = utilLogic.selectCount(TableNameUtil.KQDS_USERDOCUMENT, "nexttype", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在患者档案，无法删除！");
					}
				}
			}

			// 删除消费项目分类
			if (DictUtil.COSTITEM_SORT.equals(topDict.getDictCode())) {
				if (DictUtil.COSTITEM_SORT.equals(dict.getParentCode())) { // 删除一级分类
					int count = utilLogic.selectCount(TableNameUtil.KQDS_TREATITEM, "basetype", dict.getDictCode());
					if (count > 0) {
						throw new Exception("该分类下存在收费项目，无法删除！");
					}
				} else {
					// 删除二级分类
					int count = utilLogic.selectCount(TableNameUtil.KQDS_TREATITEM, "nexttype", dict.getSeqId());
					if (count > 0) {
						throw new Exception("该分类下存在收费项目，无法删除！");
					}
				}
			}

			// 付款方式
			/*
			 * if ("FKFS".equals(topDict.getDictCode())) { throw new
			 * Exception("付款方式不允许删除！"); }
			 */

			// 挂号方式
			if (DictUtil.GHFL.equals(topDict.getDictCode())) {
				throw new Exception("挂号方式不允许删除！");
			}

			// 影像分类
			if (DictUtil.YXFL.equals(topDict.getDictCode())) {
				throw new Exception("影像分类不允许删除！");
			}

			boolean isHaveChild = dictLogic.IsHaveChild(dict.getDictCode());
			if (isHaveChild) {
				throw new Exception("请先删除子节点");
			}

			dictLogic.deleteBySeqIds(seqId, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键不能为空");
			}
			YZDict person = (YZDict) dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, seqId);

			if (person == null) {
				throw new Exception("记录不存在");
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
	 * 根据父级编号获取子项目列表【如：患者来源】
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListByParentCode.act")
	public void getListByParentCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String organization=request.getParameter("organization");
			if(YZUtility.isNullorEmpty(organization)){
				organization=ChainUtil.getCurrentOrganization(request);
			}
			String parentCode = request.getParameter("parentCode");
			List<YZDict> list = null;
			list = dictLogic.getListByParentCode(parentCode, organization);
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}
	
	/**
	 * 初始化患者来源 （专属客服人员） 2019-11-30 syp
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListByParentCodeIscustomer.act")
	public void getListByParentCodeIscustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		String deptId = person.getDeptId();
		String iscustomer = null;
		if(deptId.equals("aeafb25a-ae9f-4d22-8230-3725cb942435")) {
			iscustomer = "1";
		} else if (deptId.equals("658b52d6-a42a-4b3d-9a19-bd0bc77210c5")) {
			iscustomer = "1";
		} else if (deptId.equals("abcea651-b674-42fd-a9e1-0dbd08f61525")) {
			iscustomer = "1";
		} else if (deptId.equals("41f199cd-e4be-4cfc-b553-555d13605976")) {
			iscustomer = "1";
		} else if (deptId.equals("e6066145-dd3b-4bf9-b2aa-d63b45375930")) {
			iscustomer = "1";
		} else if (deptId.equals("355168c8-de68-4a7b-aa41-0b33a4c31073")) {
			iscustomer = "1";
		}
		try {
			String parentCode = request.getParameter("parentCode");
			List<YZDict> list = null;
			list = dictLogic.getListByParentCodeIscustomer(parentCode, ChainUtil.getCurrentOrganization(request), iscustomer);
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据父级编号获取子项目列表【如：患者来源】 门诊编号从前台传入
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListByParentCodeOrg.act")
	public void getListByParentCodeOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String parentCode = request.getParameter("parentCode");
			List<YZDict> list = null;
			list = dictLogic.getListByParentCode(parentCode, ChainUtil.getOrganizationFromUrlCanNull(request));
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListByParentCodeALL.act")
	public void getListByParentCodeALL(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String parentCode = request.getParameter("parentCode");
			List<YZDict> list = null;
			list = dictLogic.getListByParentCodeALL(parentCode, ChainUtil.getOrganizationFromUrl(request));
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 获取子项目列表【如：来源子分类】 根据父级seqId
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSubListByParentSeqId.act")
	public void getSubListByParentSeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String parentSeqId = request.getParameter("parentSeqId");
			if (YZUtility.isNullorEmpty(parentSeqId)) {
				throw new Exception("父级主键不能为空");
			}

			YZDict parentDict = (YZDict) dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, parentSeqId);
			if (parentDict == null) {
				throw new Exception("父级分类不存在");
			}

			String isAdd = request.getParameter("isAdd");
			// 门诊条件过滤
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			List<YZDict> list = dictLogic.getSubListByParentCode(parentDict.getDictCode(), organization, isAdd);

			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);

		}
	}

	/**
	 * 不做门诊条件过滤
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSubListByParentSeqIdNoOrg.act")
	public void getSubListByParentSeqIdNoOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String parentSeqId = request.getParameter("parentSeqId");
			if (YZUtility.isNullorEmpty(parentSeqId)) {
				throw new Exception("父级主键不能为空");
			}

			YZDict parentDict = (YZDict) dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, parentSeqId);
			if (parentDict == null) {
				throw new Exception("父级分类不存在");
			}

			String isAdd = request.getParameter("isAdd");
			List<YZDict> list = dictLogic.getSubListByParentCodeNoOrg(parentDict.getDictCode(), isAdd);

			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);

		}
	}

	/**
	 * 获取子项目列表【如：来源子分类】
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSubListByParentCode.act")
	public void getSubListByParentCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String parentCode = request.getParameter("parentCode");
			String isAdd = request.getParameter("isAdd");
			// 门诊条件过滤
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			List<YZDict> list = dictLogic.getSubListByParentCode(parentCode, organization, isAdd);

			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);

		}
	}

	/**
	 * 根据编号获取详情【字典编号全局唯一】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailByDictCode.act")
	public String getDetailByDictCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String dictCode = request.getParameter("dictCode");

			if (YZUtility.isNullorEmpty(dictCode)) {
				throw new Exception("编号不能为空");
			}
			YZDict dict = dictLogic.getDetailByDictCode(dictCode);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, JSONObject.fromObject(dict).toString());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 获取字典表数据名称 【仅 grxx.jsp页面调用】
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictNamesBySeqIds.act")
	public void getDictNamesBySeqIds(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String ids = request.getParameter("ids");
			String data = "";
			if (!YZUtility.isNullorEmpty(ids)) {
				ids = YZUtility.ConvertStringIds4Query(ids);// 转换后供查询
				data = dictLogic.getDictNamesBySeqIds(ids);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 根据编码获取名称字符串
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictNamesByDictCodes.act")
	public void getDictNamesByDictCodes(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String ids = request.getParameter("dictCodes");
			String data = "";
			if (!YZUtility.isNullorEmpty(ids)) {
				ids = YZUtility.ConvertStringIds4Query(ids);// 转换后供查询
				data = dictLogic.getDictNamesByDictCodes(ids);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/****************************************
	 * 以下为合并过来的代码
	 ***************************************************/

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictNameById.act")
	public void getDictNameById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String dicIdStr = request.getParameter("dicId");
			String data = dictLogic.getDictNameBySeqId(dicIdStr);
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
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMaxOrderno.act")
	public void getMaxOrderno(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String parentCode = request.getParameter("parentCode");
			String data = dictLogic.getMaxOrderno(parentCode);
			JSONObject jobj = new JSONObject();
			jobj.put("data", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}

	/**
	 * 只获取一级分类列表，用于病历库后台管理
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLITree.act")
	public void getLITree(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String parentCode = request.getParameter("parentCode");
		List<JSONObject> listtree = new ArrayList<JSONObject>();
		try {

			// 门诊条件过滤
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			List<JSONObject> list = dictLogic.getSubListByParentCodeAndOrg(parentCode, organization);
			for (int i = 0; i < list.size(); i++) {
				JSONObject base = list.get(i);
				JSONObject obj = new JSONObject();
				obj.put("id", base.getString("seqId")); // 用 DICT_CODE ！！
				obj.put("pId", parentCode);
				obj.put("name", base.getString("dictName"));
				obj.put("nocheck", "true");
				obj.put("isParent", "false");
				listtree.add(obj);
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, listtree);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

	}
	@RequestMapping(value = "/getHFFLSeqid.act")
	public void getHFFLSeqid(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String parentCode = request.getParameter("parentCode");
			// 门诊条件过滤
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			List<JSONObject> list = dictLogic.getSubListByParentCodeAndOrg(parentCode, organization);
			YZUtility.RETURN_LIST(list, response, logger);
			//YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

	}
	/**
	  * @Title:    
	  * @Description: TODO(牙病分類)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2020年5月26日 下午2:28:57
	 */
	@RequestMapping(value = "/getDiseaseByCode.act")
	public void getDiseaseByCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String diagnose = request.getParameter("code");
			String bqfl = request.getParameter("id");
			// 门诊条件过滤
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			JSONObject json = new JSONObject();
			List<JSONObject> list = dictLogic.getSubListByParentCodeAndOrg(diagnose, organization);
			List<JSONObject> bqList = dictLogic.getSubListByParentCodeAndOrg(bqfl, organization);
			json.put("certificateData", list);
			json.put("conditionData", bqList);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

	}

}
