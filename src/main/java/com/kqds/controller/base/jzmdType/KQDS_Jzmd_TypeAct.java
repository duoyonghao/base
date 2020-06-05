package com.kqds.controller.base.jzmdType;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsJzmdType;
import com.kqds.service.base.jzmdType.KQDS_Jzmd_TypeLogic;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Jzmd_TypeAct")
public class KQDS_Jzmd_TypeAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Jzmd_TypeAct.class);
	@Autowired
	private KQDS_Jzmd_TypeLogic logic;

	@RequestMapping(value = "/toIndex.act")
	public ModelAndView toJzmdSubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/jzmdType/list_kqds_jzmd_type.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fid = request.getParameter("fid");
		String cid = request.getParameter("cid");
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("fid", fid);
		mv.addObject("cid", cid);
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/jzmdType/addTx_kqds_jzmd_type.jsp");
		return mv;
	}

	/**
	 * 查询就诊目的子分类
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getJzmdChildTypeSelect.act")
	public void getJzmdChildTypeSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String jzmd = request.getParameter("jzmd");
			String isAdd = request.getParameter("isAdd");
			// 门诊条件过滤
			List<KqdsJzmdType> list = logic.getJzmdChildTypeSelect(jzmd, isAdd, ChainUtil.getCurrentOrganization(request));// 门诊条件过滤
			JSONObject jobj = new JSONObject();
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}
}