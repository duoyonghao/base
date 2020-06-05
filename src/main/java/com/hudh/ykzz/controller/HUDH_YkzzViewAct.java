package com.hudh.ykzz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 药库中心操作Controller
 * @author XKY
 *
 */
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/HUDH_YkzzViewAct")
public class HUDH_YkzzViewAct {
	
	@RequestMapping("/toMedicnes.act")
	public ModelAndView toMedicnes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/yk_index.jsp");
		return mv;
	}
	
	@RequestMapping("/toAddmedicines.act")
	public ModelAndView toAddmedicines(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String seqId = request.getParameter("seqId");
		mv.addObject("seqId", seqId);
		mv.setViewName("/hudh/ykzz/medicines/save_medicnes.jsp");
		return mv;
	}
	
	@RequestMapping("/toUploadExcel.act")
	public ModelAndView toUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/upload/upload_excel.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toTypeManger.act")
	public ModelAndView toTypeManger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/type/type_manager.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toSaveType.act")
	public ModelAndView toSaveType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String perId = request.getParameter("perId");
		String perName = request.getParameter("perName");
		mv.addObject("perId", perId);
		mv.addObject("perName", perName);
		mv.setViewName("/hudh/ykzz/type/save_type.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toUpdateType.act")
	public ModelAndView toUpdateType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String id = request.getParameter("id");
		String perId = request.getParameter("perId");
		String perName = request.getParameter("perName");
		mv.addObject("id", id);
		mv.addObject("perId", perId);
		mv.addObject("perName", perName);
		mv.setViewName("/hudh/ykzz/type/update_type.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toManuManger.act")
	public ModelAndView toManuManger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/manu/manu_manager.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toManufacManger.act")
	public ModelAndView toManufacManger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/manufac/manufac_manager.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toSaveManufac.act")
	public ModelAndView toSaveManufac(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/manufac/save_manufac.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toUpdateManufac.act")
	public ModelAndView toUpdateManufac(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String id = request.getParameter("id");
		mv.addObject("id", id);
		mv.setViewName("/hudh/ykzz/manufac/update_manufac.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toSaveManu.act")
	public ModelAndView toSaveManu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/manu/save_manu.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toUpdateManu.act")
	public ModelAndView toUpdateManu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String id = request.getParameter("id");
		mv.addObject("id", id);
		mv.setViewName("/hudh/ykzz/manu/update_manu.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toInDrugs.act")
	public ModelAndView toInDrugs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsIn/in_drugs.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toYkInDrugsSearch.act")
	public ModelAndView toYkInDrugsSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsIn/in_drugs_search.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toDrugsHouse.act")
	public ModelAndView toDrugsHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsIn/drugs_house.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toCostDrugs.act")
	public ModelAndView toCostDrugs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsCost/cost_drugs.jsp");
		return mv;
	}
	@RequestMapping(value = "/toOutDrugs.act")
	public ModelAndView toOutDrugs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsOut/out_drugs.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toYkOutDrugsSearch.act")
	public ModelAndView toYkOutDrugsSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsOut/out_drugs_search.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toYkCostRecode.act")
	public ModelAndView toYkCostRecode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsCost/cost_drugs_recode.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toYkCostReturn.act")
	public ModelAndView toYkCostReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsReturn/cost_drugs_return.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toDrugsExamine.act")
	public ModelAndView toDrugsExamine(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsIn/in_drugs_examine.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toAddExamineRemark.act")
	public ModelAndView toAddExamineRemark(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String drugsInId = request.getParameter("drugsInId");
		mv.addObject("drugsInId", drugsInId);
		mv.setViewName("/hudh/ykzz/drugsIn/add_examine_remark.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toDugsExamineDetail.act")
	public ModelAndView dugsExamineDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsIn/dugs_examine_detail.jsp");
		return mv;
	}
	@RequestMapping(value = "/toYkCostReturnDetail.act")
	public ModelAndView YkCostReturnDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/ykzz/drugsReturn/cost_drugs_return_detail.jsp");
		return mv;
	}
}
