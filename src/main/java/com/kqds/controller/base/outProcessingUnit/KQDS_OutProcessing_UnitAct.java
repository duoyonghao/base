package com.kqds.controller.base.outProcessingUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsOutprocessingUnit;
import com.kqds.service.base.outProcessingUnit.KQDS_outProcessing_UnitLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_OutProcessing_UnitAct")
public class KQDS_OutProcessing_UnitAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessing_UnitAct.class);
	@Autowired
	private KQDS_outProcessing_UnitLogic logic;

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/outProcessingUnit/index_ls.jsp");
		return mv;
	}

	/**
	 * 根据code查询详情返回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailByCode.act")
	public String selectDetailByCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String code = request.getParameter("code");

			Map<String, String> map = new HashMap<String, String>();
			map.put("code", code);
			// ### 门诊条件过滤
			map.put("organization", ChainUtil.getOrganizationFromUrl(request));
			List<KqdsOutprocessingUnit> en = (List<KqdsOutprocessingUnit>) logic.loadList(TableNameUtil.KQDS_OUTPROCESSING_UNIT, map);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put(YZActionKeys.JSON_RET_MSRGS, "操作成功");
			if (en.size() > 0) {
				jobj.put("data", en.get(0));
			} else {
				jobj.put("data", new KqdsOutprocessingUnit());
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询加工厂列表，后台管理中心使用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUnitList4Back.act")
	public String getUnitList4Back(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String isAdd = request.getParameter("isAdd"); // 如果isAdd有值，说明是新增页面；
															// 目前后台的患者来源有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			List<JSONObject> en = logic.getUnitList4Back(isAdd, organization);
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

	/**
	 * 查询加工厂列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUnitList.act")
	public String getUnitList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String isAdd = request.getParameter("isAdd"); // 如果isAdd有值，说明是新增页面；
															// 目前后台的患者来源有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
			String organization = ChainUtil.getOrganizationFromUrl(request);
			List<JSONObject> en = logic.getUnitList(isAdd, organization);
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

}