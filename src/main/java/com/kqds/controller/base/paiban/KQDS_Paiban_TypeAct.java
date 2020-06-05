package com.kqds.controller.base.paiban;

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

import com.kqds.service.base.paiban.KQDS_Paiban_TypeLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Paiban_TypeAct")
public class KQDS_Paiban_TypeAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Paiban_TypeAct.class);
	@Autowired
	private KQDS_Paiban_TypeLogic logic;

	@RequestMapping(value = "/toPaibanTypeList.act")
	public ModelAndView toPaibanTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/paiban/list_kqds_paiban_type.jsp");
		return mv;
	}

	@RequestMapping(value = "/toPaibanTypeEdit.act")
	public ModelAndView toPaibanTypeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/paiban/edit_kqds_paiban_type.jsp");
		return mv;
	}

	@RequestMapping(value = "/toPaibanTypeDetail.act")
	public ModelAndView toPaibanTypeDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/paiban/detail_kqds_paiban_type.jsp");
		return mv;
	}

	@RequestMapping(value = "/toPaibanTypeAdd.act")
	public ModelAndView toPaibanTypeAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/paiban/add_kqds_paiban_type.jsp");
		return mv;
	}

	/**
	 * 根据排班名称查询【###############前台调用#################】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailBytpename.act")
	public String selectDetailBytpename(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String typename = request.getParameter("typename");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(typename)) {
				map.put("typename", typename);
			}
			List<JSONObject> list = logic.selectList(TableNameUtil.KQDS_PAIBAN_TYPE, map, ChainUtil.getCurrentOrganization(request));
			JSONObject jobj = new JSONObject();
			jobj.put("data", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}