package com.hudh.zzbl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ZzblViewAct")
public class ZzblViewAct {
	
	/**
	 * 手术室查看咨询填写病历页面的展示
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toPlantKnowbookInfor.act")
	public ModelAndView toPlantKnowbookInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/plantKnowbook.jsp");
		return mv;
	}
	@RequestMapping("/topulloutKnowbookInfor.act")
	public ModelAndView topulloutKnowbookInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/pulloutKnowbook.jsp");
		return mv;
	}
	@RequestMapping("/tolocatorKnowbookInfor.act")
	public ModelAndView tolocatorKnowbookInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/locatorKnowbook.jsp");
		return mv;
	}
	
	@RequestMapping("/toRepairProjectInfor.act")
	public ModelAndView toRepairProjectInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/repairProject.jsp");
		return mv;
	}
	
	@RequestMapping("/toOperationExamineInfor.act")
	public ModelAndView toOperationExamineInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/operationExamine.jsp");
		return mv;
	}
//	新核查单
	@RequestMapping("/toPatientOperationExamineInfor.act")
	public ModelAndView toPatientOperationExamineInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/patientoperationExamine.jsp");
		return mv;
	}
	
	@RequestMapping("/toOperationRecordInfor.act")
	public ModelAndView toOperationRecordInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/operationRecord.jsp");
		return mv;
	}
	
	@RequestMapping("/toExamineDiagnoseInfor.act")
	public ModelAndView toExamineDiagnoseInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/examineDiagnose.jsp");
		return mv;
	}
	
	@RequestMapping("/toExamineDiagnoseInform.act")
	public ModelAndView toExamineDiagnoseInform(HttpServletRequest request, HttpServletResponse response) {
		String status = request.getParameter("status");
		ModelAndView mv = new ModelAndView();
		mv.addObject("status", status);
		mv.setViewName("/hudh/lclj/plantCaseInfor/inform.jsp");
		return mv;
	}

	@RequestMapping("/toExamineDentalImplant.act")
	public ModelAndView toExamineDentalImplant(HttpServletRequest request, HttpServletResponse response) {
		String status = request.getParameter("status");
		ModelAndView mv = new ModelAndView();
		mv.addObject("status", status);
		mv.setViewName("/hudh/lclj/plantCaseInfor/implantTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toExamineDiagnoseCase.act")
	public ModelAndView toExamineDiagnoseCase(HttpServletRequest request, HttpServletResponse response) {
		String status = request.getParameter("status");
		ModelAndView mv = new ModelAndView();
		mv.addObject("status", status);
		mv.setViewName("/hudh/lclj/plantCaseInfor/case.jsp");
		return mv;
	}
	
	@RequestMapping("/toDiagnosisProjectInfor.act")
	public ModelAndView toDiagnosisProjectInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/diagnosisProject.jsp");
		return mv;
	}
	
	@RequestMapping("/toAnamnesisInfor.act")
	public ModelAndView toAnamnesisInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/anamnesis.jsp");
		return mv;
	}
//	lutian 2020/05/29
	@RequestMapping("/toAnamnesisSecondInfor.act")
	public ModelAndView toAnamnesisSecondInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/anamnesisSecond.jsp");
		return mv;
	}
	@RequestMapping("/toPlantOperationRecordInfor.act")
	public ModelAndView toPlantOperationRecordInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/plantOperationRecord.jsp");
		return mv;
	}
	@RequestMapping("/toRepairAcographyInfor.act")
	public ModelAndView toRepairAcographyInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/repairAcography.jsp");
		return mv;
	}
	@RequestMapping("/toPostoperativePrecautionsInfor.act")
	public ModelAndView toPostoperativePrecautionsInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/postoperativePrecautions.jsp");
		return mv;
	}
	@RequestMapping("/toAnamnesisThirdInfor.act")
	public ModelAndView toAnamnesisThirdInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/anamnesisThird.jsp");
		return mv;
	}
	@RequestMapping(value = "/toAnamnesisToothMap.act")
	public ModelAndView toAnamnesisToothMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.setViewName("/hudh/lclj/plantCaseInfor/toothMap.jsp");
		return mv;
	}
	/*-----------------------*/
	@RequestMapping(value = "/toNewLocatorKnowBook.act")
	public ModelAndView toNewBl7(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.setViewName("/hudh/lclj/plantCaseInfor/locatorKnowbook0409.jsp");
		return mv;
	}
	/**
	  * @Title: toDentalExamination   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2020年5月26日 下午1:54:25
	 */
	@RequestMapping("/toDentalExamination.act")
	public ModelAndView toDentalExamination(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/plantCaseInfor/dentalExamination.jsp");
		return mv;
	}
	
	/**
	  * @Title: toProject   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2020年5月26日 下午1:54:31
	 */
	@RequestMapping(value = "/toProject.act")
	public ModelAndView toProject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String projectName = request.getParameter("projectName");
		String toothNum = request.getParameter("toothNum");		
		String toothMore = request.getParameter("toothMore");	
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.addObject("id", id);	
		mv.addObject("projectName", projectName);	
		mv.addObject("toothNum", toothNum);	
		mv.addObject("toothMore", toothMore);	
		mv.setViewName("/hudh/lclj/plantCaseInfor/projectItems.jsp");
		return mv;
	}
	/**
	 * 咨询填写的页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toZzllPlantKnowbook.act")
	public ModelAndView toZzllOperation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		String order_number = request.getParameter("order_number");
		String id = request.getParameter("id");
		mView.addObject("order_number", order_number);
		mView.addObject("id", id);
		mView.setViewName("/hudh/lclj/plantCase/plantKnowbook.jsp");
		return mView;
	}
	@RequestMapping("/topulloutKnowbook.act")
	public ModelAndView topulloutKnowbook(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		String order_number = request.getParameter("order_number");
		String id = request.getParameter("id");
		mView.addObject("order_number", order_number);
		mView.addObject("id", id);
		mView.setViewName("/hudh/lclj/plantCase/pulloutKnowbook.jsp");
		return mView;
	}
	@RequestMapping("/tolocatorKnowbook.act")
	public ModelAndView tolocatorKnowbook(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		String order_number = request.getParameter("order_number");
		String id = request.getParameter("id");
		mView.addObject("order_number", order_number);
		mView.addObject("id", id);
		mView.setViewName("/hudh/lclj/plantCase/locatorKnowbook.jsp");
		return mView;
	}
	
	@RequestMapping("/toZzllAnammesis.act")
	public ModelAndView toZzllAnammesis(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		String order_number = request.getParameter("order_number");
		String id = request.getParameter("id");
		mView.addObject("order_number", order_number);
		mView.addObject("id", id);
		mView.setViewName("/hudh/lclj/plantCase/anamnesis.jsp");
		return mView;
	}
	/*2019/12/30 lutian 主诉及既往病史新页面*/
	/*@RequestMapping("/toZzllAnammesis.act")
	public ModelAndView toZzllAnammesis(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		String order_number = request.getParameter("order_number");
		String id = request.getParameter("id");
		mView.addObject("order_number", order_number);
		mView.addObject("id", id);
		mView.setViewName("/hudh/lclj/plantCase/anamnesisNew.jsp");
		return mView;
	}*/
	
	@RequestMapping("/toZzllDiagnosisProject.act")
	public ModelAndView toZzllDiagnosisProject(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		String order_number = request.getParameter("order_number");
		String id = request.getParameter("id");
		mView.addObject("order_number", order_number);
		mView.addObject("id", id);
		mView.setViewName("/hudh/lclj/plantCase/diagnosisProject.jsp");
		return mView;
	}
	
	@RequestMapping("/toZzllExamineDiagnose.act")
	public ModelAndView toZzllExamineDiagnose(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		String order_number = request.getParameter("order_number");
		String id = request.getParameter("id");
		mView.addObject("order_number", order_number);
		mView.addObject("id", id);
		mView.setViewName("/hudh/lclj/plantCase/examineDiagnose.jsp");
		return mView;
	}
	
	@RequestMapping("/toZzllRepairProject.act")
	public ModelAndView toZzllRepairProject(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		String order_number = request.getParameter("order_number");
		String id = request.getParameter("id");
		mView.addObject("order_number", order_number);
		mView.addObject("id", id);
		mView.setViewName("/hudh/lclj/plantCase/repairProject.jsp");
		return mView;
	}
	
	@RequestMapping("/toZzllAttentionTopo.act")
	public ModelAndView toZzllAttentionTopo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/lclj/plantCase/attentionTopo.jsp");
		return mView;
	}
	
	@RequestMapping("/toZzllAttentionWholeHalf.act")
	public ModelAndView toZzllAttentionWholeHalf(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/lclj/plantCase/attentionWholehalf.jsp");
		return mView;
	}
	
}
