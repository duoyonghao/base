package com.kqds.controller.base.outProcessing;

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
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsOutprocessingType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.outProcessing.KQDS_OutProcessing_TypeLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_OutProcessing_TypeBackAct")
public class KQDS_OutProcessing_TypeBackAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessing_TypeBackAct.class);
	@Autowired
	private KQDS_OutProcessing_TypeLogic logic;

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/outProcessingType/index_ls.jsp");
		return mv;
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/outProcessingType/list_kqds_outprocessing_type.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/outProcessingType/edit_kqds_outprocessing_type.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail.act")
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/outProcessingType/detail_kqds_outprocessing_type.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/outProcessingType/add_kqds_outprocessing_type.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsOutprocessingType dp = new KqdsOutprocessingType();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			int num = logic.countByCode(dp);
			if (num >= 1) {
				throw new Exception("加工分类编号重复, 请重新填写！");
			}
			int num2 = logic.countByOrgAndName(dp);
			if (num2 >= 1) {
				throw new Exception("同一个加工厂分类名称不允许重复！");
			}
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_TYPE, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_OUTPROCESSING_TYPE, dp, TableNameUtil.KQDS_OUTPROCESSING_TYPE, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request)); // 【后台数据维护，以页面传入的门诊编号为主】
				logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_TYPE, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING_TYPE, dp, TableNameUtil.KQDS_OUTPROCESSING_TYPE, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			int count = logic.delelteBySeqIds(seqId, request);
			YZUtility.DEAL_SUCCESS(null, "共删除" + count + "条记录", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/*
	 * 详情返回
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			KqdsOutprocessingType en = (KqdsOutprocessingType) logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_TYPE, seqId);
			if (en == null) {
				throw new Exception("数据不存在");
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/*
	 * 分页查询
	 */
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String mrjgc = request.getParameter("mrjgc");
			String wjgfl = request.getParameter("wjgfl");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(mrjgc)) {
				map.put("mrjgc", mrjgc);
			}
			if (!YZUtility.isNullorEmpty(wjgfl)) {
				map.put("wjgfl", wjgfl);
			}
			map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_OUTPROCESSING_TYPE, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询加工厂列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTypeList.act")
	public String getTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String jgc = request.getParameter("jgc");

			Map<String, String> map = new HashMap<String, String>();
			map.put("processingfactory", jgc);
			map.put("useflag", "0");
			List<KqdsOutprocessingType> en = (List<KqdsOutprocessingType>) logic.loadList(TableNameUtil.KQDS_OUTPROCESSING_TYPE, map);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put(YZActionKeys.JSON_RET_MSRGS, "操作成功");
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	// 验证分类编号
	@RequestMapping(value = "/yzTypeNo.act")
	public String yzTypeNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String typeno = request.getParameter("typeno");
			String seqId = request.getParameter("seqId");

			Map<String, String> map = new HashMap<String, String>();
			map.put("typeno", typeno);
			boolean result = true;
			List<KqdsOutprocessingType> en = (List<KqdsOutprocessingType>) logic.loadList(TableNameUtil.KQDS_OUTPROCESSING_TYPE, map);
			if (YZUtility.isNullorEmpty(seqId)) {// 添加
				if (en != null && en.size() > 0) {
					result = false;
				}
			} else {// 修改
				if (en != null && en.size() > 0) {
					for (KqdsOutprocessingType type : en) {
						if (!type.getSeqId().equals(seqId)) {
							result = false;
						}
					}
				}
			}
			YZUtility.DEAL_SUCCESS_VALID(result, response);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}