package com.kqds.controller.base.outProcessing;

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

import com.kqds.service.base.outProcessing.KQDS_OutProcessingLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_OutProcessingAct")
public class KQDS_OutProcessingAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessingAct.class);
	@Autowired
	private KQDS_OutProcessingLogic logic;

	@RequestMapping(value = "/toJgIndex.act")
	public ModelAndView toJgIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/jgzx/jg_index.jsp");
		return mv;
	}

	/**
	 * 根据加工项目编号和 工厂ID 查询加工项目 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOneByBh.act")
	public String getOneByBh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String wjgxmbh = request.getParameter("wjgxmbh");
			String factoryId = request.getParameter("factoryId");
			Map<String, String> map = new HashMap<String, String>();
			map.put("wjgxmbh", wjgxmbh);
			map.put("factoryId", factoryId);
			List<JSONObject> en = logic.selectByitem(TableNameUtil.KQDS_OUTPROCESSING, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en.get(0));
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}