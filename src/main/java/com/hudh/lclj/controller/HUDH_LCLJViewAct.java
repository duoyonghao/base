package com.hudh.lclj.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.util.sys.YZUtility;

@Controller
@RequestMapping("ClinicPathControllerAct")
public class HUDH_LCLJViewAct {

	private static Logger logger = LoggerFactory.getLogger(HUDH_LCLJViewAct.class);
	private final static String  URL_CODEING = "UTF-8";
	
	@RequestMapping("/toEditLcljOpreation.act")
	public ModelAndView toEditLcljOpreation(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", id);
		mv.setViewName("/hudh/lclj/edit_oprationInfor.jsp");
		return mv;
	}
	
	@RequestMapping("/toLcljOpreation.act")
	public ModelAndView toLcljOpreation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String id = request.getParameter("id");
		String searchField = request.getParameter("searchField");
		String searchValue = request.getParameter("searchValue");
		String usercode = request.getParameter("usercode");
		String querydata = request.getParameter("querydata");
		if(YZUtility.isNotNullOrEmpty(searchField)&& YZUtility.isNotNullOrEmpty(searchValue)&& YZUtility.isNotNullOrEmpty(usercode)&&YZUtility.isNotNullOrEmpty(querydata)) {
	//		mv.addObject(querydata);
	//		mv.addObject(usercode);
	//		mv.addObject(searchValue);
	//		mv.addObject(searchField);
		}
		mv.addObject("id", id);
		mv.setViewName("/hudh/lclj/add_oprationInfor.jsp");
		return mv;
	}
	
	@RequestMapping("/toLcljOpreationZGstatus.act")
	public ModelAndView toLcljOpreationZGstatus(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String id = request.getParameter("id");
		String searchField = request.getParameter("searchField");
		String searchValue = request.getParameter("searchValue");
		String usercode = request.getParameter("usercode");
		String querydata = request.getParameter("querydata");
		if(YZUtility.isNotNullOrEmpty(searchField)&& YZUtility.isNotNullOrEmpty(searchValue)&& YZUtility.isNotNullOrEmpty(usercode)&&YZUtility.isNotNullOrEmpty(querydata)) {
			//		mv.addObject(querydata);
			//		mv.addObject(usercode);
			//		mv.addObject(searchValue);
			//		mv.addObject(searchField);
		}
		mv.addObject("id", id);
		mv.setViewName("/hudh/lclj/changeZGstatus.jsp");
		return mv;
	}
	
	@RequestMapping("/toLcljInformation.act")
	public ModelAndView toLcljInformation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/lclj_patient.jsp");
		return mv;
	}
	
	@RequestMapping("/toAddRemakeInfor.act")
	public ModelAndView toAddRemakeInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/add_remake.jsp");
		String orderTrackId = request.getParameter("orderTrackId");
		String flowLink = request.getParameter("flowLink");
		String orderNumber = request.getParameter("orderNumber");
		mv.addObject("orderTrackId", orderTrackId);
		mv.addObject("flowLink", flowLink);
		mv.addObject("operateName", orderNumber);
		return mv;
	}
	
	@RequestMapping("/toCkaRemakeInfor.act")
	public ModelAndView toCkaRemakeInfor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/ck_remake.jsp");
		String orderTrackId = request.getParameter("orderTrackId");
		String flowLink = request.getParameter("flowLink");
		String operateName = request.getParameter("operateName");
		if(YZUtility.isNotNullOrEmpty(orderTrackId)&& YZUtility.isNotNullOrEmpty(flowLink)&& YZUtility.isNotNullOrEmpty(operateName)) {
			mv.addObject("orderTrackId", orderTrackId);
			mv.addObject("flowLink", flowLink);
			mv.addObject("operateName", operateName);
		}
		return mv;
	}
	
	@RequestMapping("/toOperationSplit.act")
	public ModelAndView toOperationSplit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/create_gzDdXx.jsp");
		return mv;
	}
	
	//保存术前取模定咬合业务数据页面
	@RequestMapping("/toSaveOperativeTreatmentQmdyh.act")
	public ModelAndView toSaveOperativeTreatmentQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/operativeTreatmentQmdyh.jsp");
		return mv;
	}

	// 添加临床路径
	@RequestMapping(value = "/toClinicalPath.act")
	public ModelAndView toClinicalPath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/cp.jsp");
		return mv;
	}

	// 添加生成临床路径信息
	@RequestMapping(value = "/toCreateClinicalPath.act")
	public ModelAndView toCreateClinicalPath(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/cp.jsp");
		return mv;
	}

	@RequestMapping("/toCreateClinicPath.act")
	public ModelAndView toCreateClinicPath(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/lclj/cp_add.jsp");
		return mv;
	}

	@RequestMapping("/toOperation.act")
	public ModelAndView toOperation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String orderNumber = request.getParameter("orderNumber");
		String uncomNum = request.getParameter("uncomNum");
		mv.addObject("orderNumber", orderNumber);
		mv.addObject("uncomNum", uncomNum);
		mv.setViewName("/hudh/lclj/operation.jsp");
		return mv;
	}
	
	@RequestMapping("/toFlowLinkOperate.act")
	public ModelAndView toFlowLinkOperate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String orderTrackId = request.getParameter("orderTrackId");
		mv.addObject("orderTrackId", orderTrackId);
		mv.setViewName("/hudh/lclj/flowlink_operate.jsp");
		return mv;
	}
	
	@RequestMapping("/openRemakeInfo.act")
	public ModelAndView openRemakeInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView();
		String orderTrackId = request.getParameter("orderTrackId");
		String flowLink = URLDecoder.decode(request.getParameter("flowLink"),URL_CODEING);
		String oprationName = URLDecoder.decode(request.getParameter("oprationName"),URL_CODEING);
		if(YZUtility.isNotNullOrEmpty(orderTrackId)&& YZUtility.isNotNullOrEmpty(flowLink)&& YZUtility.isNotNullOrEmpty(oprationName)) {
			mv.addObject("orderTrackId", orderTrackId);
			mv.addObject("flowLink", flowLink);
			mv.addObject("oprationName", oprationName);
		}
		mv.setViewName("/hudh/lclj/remakeinfo.jsp");
		return mv;
	}
	
	/**
	 * 单多颗植骨（url配置）
	 */
	@RequestMapping("/toCounterCheck.act")
	public ModelAndView toCounterCheck(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/counterCheck.jsp");
		return mv;
	}
	
	@RequestMapping("/toFirstReview.act")
	public ModelAndView toFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/firstReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toOperativeTreatment.act")
	public ModelAndView toOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/operativeTreatment.jsp");
		return mv;
	}
	
	@RequestMapping("/toQmdyh.act")
	public ModelAndView toQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/qmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toReviewTime.act")
	public ModelAndView toReviewTime(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/reviewTime.jsp");
		return mv;
	}
	
	@RequestMapping("/toSecondDateCounterCheck.act")
	public ModelAndView toSecondDateCounterCheck(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/secondDate.jsp");
		return mv;
	}
	
	@RequestMapping("/toSecondDate.act")
	public ModelAndView toSecondDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/secondDate.jsp");
		return mv;
	}
	
	/**
	  * @Title: toSecondDates   
	  * @Description: TODO(种植局部义齿  基台+连桥 --戴牙调合)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	 */
	@RequestMapping("/toSecondDates.act")
	public ModelAndView toSecondDates(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMoreAbutmentAndBridge/secondDate.jsp");
		return mv;
	}
	
	@RequestMapping("/toSecondReview.act")
	public ModelAndView toSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/secondReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toTryIn.act")
	public ModelAndView toTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/tryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toWearTooth.act")
	public ModelAndView toWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/wearTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toPreoperativePreparation.act")
	public ModelAndView toPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMoreAbutment/preoperativePreparation.jsp");
		return mv;
	}
	
	/**
	  * @Title: toPreoperativePreparationes   
	  * @Description: TODO(种植局部单基台上下颌--术前准备)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	 */
	@RequestMapping("/toPreoperativePreparationes.act")
	public ModelAndView toPreoperativePreparationes(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMoreAbutment/preoperativePreparation.jsp");
		return mv;
	}
	
	@RequestMapping("/toLcljFlowEnd.act")
	public ModelAndView toLcljFlowEnd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/lcljFlowEnd.jsp");
		return mv;
	}
	
	/**
	 * 单多颗非植骨（url配置）
	 * @param request
	 * @param response
	 * @return
	 */ 
	@RequestMapping("/toSingleOrMultiBoneCounterCheck.act")
	public ModelAndView toSingleOrMultiBoneCounterCheck(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMultiBone/counterCheck.jsp");
		return mv;
	}
	
	@RequestMapping("/toSingleOrMultiBoneFirstReview.act")
	public ModelAndView toSingleOrMultiBoneFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMultiBone/firstReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toSingleOrMultiBoneOperativeTreatment.act")
	public ModelAndView toSingleOrMultiBoneOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/operativeTreatment.jsp");
		return mv;
	}
	/**
	  * @Title: toSingleOrMultiBoneOperativeTreatmentes   
	  * @Description: TODO(种植局部单基台--手术治疗)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	 */
	@RequestMapping("/toSingleOrMultiBoneOperativeTreatmentes.act")
	public ModelAndView toSingleOrMultiBoneOperativeTreatmentes(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMoreAbutment/operativeTreatment.jsp");
		return mv;
	}
	
	@RequestMapping("/toSingleOrMultiBoneQmdyh.act")
	public ModelAndView toSingleOrMultiBoneQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMultiBone/qmdyh.jsp");
		return mv;
	}
	
	
	@RequestMapping("/toSingleOrMultiBoneReviewTime.act")
	public ModelAndView toSingleOrMultiBoneReviewTime(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMultiBone/reviewTime.jsp");
		return mv;
	}
	
	/**
	  * @Title: toSingleOrMultiBoneReviewTimes   
	  * @Description: TODO(种植局部义齿--单基台    戴牙后复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	 */
	@RequestMapping("/toSingleOrMultiBoneReviewTimes.act")
	public ModelAndView toSingleOrMultiBoneReviewTimes(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMoreAbutment/reviewTime.jsp");
		return mv;
	}
	
	@RequestMapping("/toSingleOrMultiBoneSecondDate.act")
	public ModelAndView toSingleOrMultiBoneSecondDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMultiBone/secondDate.jsp");
		return mv;
	}
	
	@RequestMapping("/toSingleOrMultiBoneSecondReview.act")
	public ModelAndView toSingleOrMultiBoneSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMultiBone/secondReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toSingleOrMultiBoneThirdReview.act")
	public ModelAndView toSingleOrMultiBoneThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMultiBone/thirdReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toSingleOrMultiBoneTryIn.act")
	public ModelAndView toSingleOrMultiBoneTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMultiBone/tryIn.jsp");
		return mv;
	}
	
	/**
	  * @Title: toSingleOrMultiBoneTryIn   
	  * @Description: TODO(种植局部义齿  --基台+连桥   试戴)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	 */
	@RequestMapping("/toSingleOrMultiBoneTryInes.act")
	public ModelAndView toSingleOrMultiBoneTryInes(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMoreAbutmentAndBridge/tryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toSingleOrMultiBoneWearTooth.act")
	public ModelAndView toSingleOrMultiBoneWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMultiBone/wearTooth.jsp");
		return mv;
	}
	
	/**
	  * @Title: toSingleOrMultiBoneWearToothes   
	  * @Description: TODO(种植局部单基台--戴牙)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	 */
	@RequestMapping("/toSingleOrMultiBoneWearToothes.act")
	public ModelAndView toSingleOrMultiBoneWearToothes(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/SingleOrMoreAbutment/wearTooth.jsp");
		return mv;
	}
	/**
	 * Locator非植骨（url配置）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toLocatorNoBoneOperativeTreatmentQmdyh.act")
	public ModelAndView toLocatorNoBoneOperativeTreatmentQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/operativeTreatmentQmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneOperativeTreatment.act")
	public ModelAndView toLocatorNoBoneOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/operativeTreatment.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBonePostOperativeTreatmentQmdyh.act")
	public ModelAndView toLocatorNoBonePostOperativeTreatmentQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/postOperativeTreatmentQmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneTryInTransitionTooth.act")
	public ModelAndView toLocatorNoBoneTryInTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/tryInTransitionTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneFirstReview.act")
	public ModelAndView toLocatorNoBoneFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/firstReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneSecondReview.act")
	public ModelAndView toLocatorNoBoneSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/secondReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneSecondDate.act")
	public ModelAndView toLocatorNoBoneSecondDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/secondDate.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneCounterCheck.act")
	public ModelAndView toLocatorNoBoneCounterCheck(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/counterCheck.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneQmdyh.act")
	public ModelAndView toLocatorNoBoneQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/qmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneTryIn.act")
	public ModelAndView toLocatorNoBoneTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/tryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneSecondGetModule.act")
	public ModelAndView toLocatorNoBoneSecondGetModule(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/secondGetModule.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneAgainTryIn.act")
	public ModelAndView toLocatorNoBoneAgainTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/againTryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneWearTooth.act")
	public ModelAndView toLocatorNoBoneWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/wearTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorNoBoneReviewTime.act")
	public ModelAndView toLocatorNoBoneReviewTime(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorNoBone/reviewTime.jsp");
		return mv;
	}
	
	/**
	 * Locator植骨（url配置）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toLocatorBoneOperativeTreatmentQmdyh.act")
	public ModelAndView toLocatorBoneOperativeTreatmentQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/operativeTreatmentQmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneOperativeTreatment.act")
	public ModelAndView toLocatorBoneOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/operativeTreatment.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBonePostOperativeTreatmentQmdyh.act")
	public ModelAndView toLocatorBonePostOperativeTreatmentQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/postOperativeTreatmentQmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneTryInTransitionTooth.act")
	public ModelAndView toLocatorBoneTryInTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/tryInTransitionTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneFirstReview.act")
	public ModelAndView toLocatorBoneFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/firstReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneSecondReview.act")
	public ModelAndView toLocatorBoneSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/secondReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneThirdReview.act")
	public ModelAndView toLocatorBoneThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/thirdReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneSecondDate.act")
	public ModelAndView toLocatorBoneSecondDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/secondDate.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneCounterCheck.act")
	public ModelAndView toLocatorBoneCounterCheck(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/counterCheck.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneQmdyh.act")
	public ModelAndView toLocatorBoneQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/qmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneTryIn.act")
	public ModelAndView toLocatorBoneTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/tryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneSecondGetModule.act")
	public ModelAndView toLocatorBoneSecondGetModule(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/secondGetModule.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneAgainTryIn.act")
	public ModelAndView toLocatorBoneAgainTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/againTryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneWearTooth.act")
	public ModelAndView toLocatorBoneWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/wearTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toLocatorBoneReviewTime.act")
	public ModelAndView toLocatorBoneReviewTime(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/LocatorBone/reviewTime.jsp");
		return mv;
	}
	
	/**
	 * Allonx非植骨（url配置）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAllonxNoBoneOperativeTreatmentQmdyh.act")
	public ModelAndView toAllonxNoBoneOperativeTreatmentQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/operativeTreatmentQmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneOperativeTreatment.act")
	public ModelAndView toAllonxNoBoneOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/operativeTreatment.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBonePostOperativeTreatmentQmdyh.act")
	public ModelAndView toAllonxNoBonePostOperativeTreatmentQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/postOperativeTreatmentQmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneTryInTransitionTooth.act")
	public ModelAndView toAllonxNoBoneTryInTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/tryInTransitionTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneFirstReview.act")
	public ModelAndView toAllonxNoBoneFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/firstReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneSecondReview.act")
	public ModelAndView toAllonxNoBoneSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/secondReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneResinSealing.act")
	public ModelAndView toAllonxNoBoneResinSealing(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/secondDate.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneReexaminationReconcile.act")
	public ModelAndView toAllonxNoBoneCounterCheck(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/counterCheck.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneQmdyh.act")
	public ModelAndView toAllonxNoBoneQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/qmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneTryIn.act")
	public ModelAndView toAllonxNoBoneTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/tryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneSecondGetModule.act")
	public ModelAndView toAllonxNoBoneSecondGetModule(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/secondGetModule.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneAgainTryIn.act")
	public ModelAndView toAllonxNoBoneAgainTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/againTryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneWearTooth.act")
	public ModelAndView toAllonxNoBoneWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/wearTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxNoBoneReviewTime.act")
	public ModelAndView toAllonxNoBoneReviewTime(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxNoBone/reviewTime.jsp");
		return mv;
	}
	
	/**
	 * Allonx植骨（url配置）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAllonxBoneOperativeTreatmentQmdyh.act")
	public ModelAndView toAllonxBoneOperativeTreatmentQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/operativeTreatmentQmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneOperativeTreatment.act")
	public ModelAndView toAllonxBoneOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/operativeTreatment.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBonePostOperativeTreatmentQmdyh.act")
	public ModelAndView toAllonxBonePostOperativeTreatmentQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/postOperativeTreatmentQmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneTryInTransitionTooth.act")
	public ModelAndView toAllonxBoneTryInTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/tryInTransitionTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneFirstReview.act")
	public ModelAndView toAllonxBoneFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/firstReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneThirdReview.act")
	public ModelAndView toAllonxBoneThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/thirdReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneSecondReview.act")
	public ModelAndView toAllonxBoneSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/secondReview.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneResinSealing.act")
	public ModelAndView toAllonxBoneResinSealing(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/secondDate.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneReexaminationReconcile.act")
	public ModelAndView toAllonxBoneCounterCheck(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/counterCheck.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneQmdyh.act")
	public ModelAndView toAllonxBoneQmdyh(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/qmdyh.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneTryIn.act")
	public ModelAndView toAllonxBoneTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/tryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneSecondGetModule.act")
	public ModelAndView toAllonxBoneSecondGetModule(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/secondGetModule.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneAgainTryIn.act")
	public ModelAndView toAllonxBoneAgainTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/againTryIn.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneWearTooth.act")
	public ModelAndView toAllonxBoneWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/wearTooth.jsp");
		return mv;
	}
	
	@RequestMapping("/toAllonxBoneReviewTime.act")
	public ModelAndView toAllonxBoneReviewTime(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/AllonxBone/reviewTime.jsp");
		return mv;
	}
	
	/**
	 * 单颗及多颗：全口--》螺丝+植骨+连桥：（url配置）---->种植局部义齿修复
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toScrewBoneBridgeFirstReview.act")
	public ModelAndView toScrewBoneBridgeFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/reviewTime.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewBoneBridgeSecondReview.act")
	public ModelAndView toScrewBoneBridgeSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewBoneBridgeThirdReview.act")
	public ModelAndView toScrewBoneBridgeThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/thirdReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewBoneBridgeTwoDate.act")
	public ModelAndView toScrewBoneBridgeTwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/twoDate.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewBoneBridgeFetchModel.act")
	public ModelAndView toScrewBoneBridgeFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewBoneBridgeTryIn.act")
	public ModelAndView toScrewBoneBridgeTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/tryIn.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewBoneBridgeWearTooth.act")
	public ModelAndView toScrewBoneBridgeWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/wearTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewBoneBridgeWearToothTemper.act")
	public ModelAndView toScrewBoneBridgeWearToothTemper(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/wearToothTemper.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewBoneBridgeFourthReview.act")
	public ModelAndView toScrewBoneBridgeFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/fourthReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewBoneBridgeLcljFlowEnd.act")
	public ModelAndView toScrewBoneBridgeLcljFlowEnd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwBoneBridge/finish.jsp");
		return mView;
	}
	
	/**
	 * 单颗及多颗：全口--》螺丝+外提：（url配置）---->种植局部义齿修复
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= "/toScrewOutsidePreoperativePreparation.act")
	public ModelAndView toScrewOutsidePreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/preoperativePreparation.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toScrewOutsideOperativeTreatment.act")
	public ModelAndView toScrewOutsideOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toScrewOutsideFirstReview.act")
	public ModelAndView toScrewOutsideFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/firstReview.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toScrewOutsideSecondReview.act")
	public ModelAndView toScrewOutsideSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toScrewOutsideThirdReview.act")
	public ModelAndView toScrewOutsideThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/thirdReview.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toScrewOutsideTwoDate.act")
	public ModelAndView toScrewOutsideTwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/twoDate.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toScrewOutsideFetchModel.act")
	public ModelAndView toScrewOutsideFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toScrewOutsideWearTooth.act")
	public ModelAndView toScrewOutsideWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/wearTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toScrewOutsideFourthReview.act")
	public ModelAndView toScrewOutsideFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/fourthReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideLcljFlowEnd.act")
	public ModelAndView toScrewOutsideLcljFlowEnd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutside/finish.jsp");
		return mView;
	}
	
	/**
	 * 单颗及多颗：全口--》螺丝+外提+连桥：（url配置）---->种植局部义齿修复
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toScrewOutsideBridgePreoperativePreparation.act")
	public ModelAndView toScrewOutsideBridgePreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/preoperativePreparation.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeOperativeTreatment.act")
	public ModelAndView toScrewOutsideBridgeOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeFirstReview.act")
	public ModelAndView toScrewOutsideBridgeFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/firstReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeSecondReview.act")
	public ModelAndView toScrewOutsideBridgeSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeThirdReview.act")
	public ModelAndView toScrewOutsideBridgeThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/thirdReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeTwoDate.act")
	public ModelAndView toScrewOutsideBridgeTwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/twoDate.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeFetchModel.act")
	public ModelAndView toScrewOutsideBridgeFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeTryIn.act")
	public ModelAndView toScrewOutsideBridgeTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/tryIn.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeWearTooth.act")
	public ModelAndView toScrewOutsideBridgeWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/wearTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeWearToothTemper.act")
	public ModelAndView toScrewOutsideBridgeWearToothTemper(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/wearToothTemper.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeFourthReview.act")
	public ModelAndView toScrewOutsideBridgeFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/fourthReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toScrewOutsideBridgeLcljFlowEnd.act")
	public ModelAndView toScrewOutsideBridgeLcljFlowEnd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/screwOutsideBridge/finish.jsp");
		return mView;
	}
	
	/**
	 * 种植可摘义齿修复（杆卡、Locator）临床路径流程（Locator上颌--》放置螺丝：url配置）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= "/toLocatorTopScrewPreoperativePreparation.act")
	public ModelAndView toLocatorTopScrewPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/preoperativePreparation.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewOperativeTreatment.act")
	public ModelAndView toLocatorTopScrewOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewFirstReview.act")
	public ModelAndView toLocatorTopScrewFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/firstReview.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewReviewFetchModel.act")
	public ModelAndView toLocatorTopScrewReviewFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopScrew/reviewFetchModel.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewTryIn.act")
	public ModelAndView toLocatorTopScrewTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/tryIn.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewWearTransitionTooth.act")
	public ModelAndView toLocatorTopScrewWearTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/wearTransitionTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewTwoDate.act")
	public ModelAndView toLocatorTopScrewTwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/twoDate.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewSecondReview.act")
	public ModelAndView toLocatorTopScrewSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewFetchModel.act")
	public ModelAndView toLocatorTopScrewFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewTryInWaxPattern.act")
	public ModelAndView toLocatorTopScrewTryInWaxPattern(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/tryInWaxPattern.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewWearLastTooth.act")
	public ModelAndView toLocatorTopScrewWearLastTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/wearLastTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewAdjustMouth.act")
	public ModelAndView toLocatorTopScrewAdjustMouth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/adjustMouth.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewFixationToothBridge.act")
	public ModelAndView toLocatorTopScrewFixationToothBridge(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fixationToothBridge.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewThirdReview.act")
	public ModelAndView toLocatorTopScrewThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/thirdReview.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewFourthReview.act")
	public ModelAndView toLocatorTopScrewFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fourthReview.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewFifthReview.act")
	public ModelAndView toLocatorTopScrewFifthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fifthReview.jsp");
		return mView;
	}
	
	@RequestMapping(value= "/toLocatorTopScrewSixthReview.act")
	public ModelAndView toLocatorTopScrewSixthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/sixthReview.jsp");
		return mView;
	}
	
	/**
	 * Locator半口修复放置基台（上颌）url配置
	 * @param request
	 * @param response
	 * @return
	 */
	/**
	  * @Title: toLocatorTopAbutmentPreoperativePreparation   
	  * @Description: TODO(Locator放置基台 --上颌   术前准备)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:52:37
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentPreoperativePreparation.act")
	public ModelAndView toLocatorTopAbutmentPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/preoperativePreparation.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoPerativeTreatment   
	  * @Description: TODO(Locator放置基台 --上颌  手术治疗)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:56:55
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoPerativeTreatment.act")
	public ModelAndView toLocatorTopAbutmentoPerativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/operativeTreatment.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoTryIn   
	  * @Description: TODO(Locator放置基台 --上颌  试戴)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午6:04:09
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoTryIn.act")
	public ModelAndView toLocatorTopAbutmentoTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/tryIn.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoWearTransitionTooth   
	  * @Description: TODO(Locator放置基台 --上颌  戴过渡牙)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午6:08:17
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoWearTransitionTooth.act")
	public ModelAndView toLocatorTopAbutmentoWearTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/wearTransitionTooth.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoFirstReview   
	  * @Description: TODO(Locator放置基台 --上颌  第一次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午6:09:21
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoFirstReview.act")
	public ModelAndView toLocatorTopAbutmentoFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/firstReview.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoSecondReview   
	  * @Description: TODO(Locator放置基台 --上颌  第二次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午6:10:21
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoSecondReview.act")
	public ModelAndView toLocatorTopAbutmentoSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/secondReview.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoFetchModel   
	  * @Description: TODO(Locator放置基台 --上颌  取模)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午6:11:20
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoFetchModel.act")
	public ModelAndView toLocatorTopAbutmentoFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fetchModel.jsp");
		return mView;
	}
	/**
	  * @Title: toLocatorTopAbutmentoTryInWaxPattern   
	  * @Description: TODO(Locator放置基台---上颌   试戴蜡型)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:21:34
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoTryInWaxPattern.act")
	public ModelAndView toLocatorTopAbutmentoTryInWaxPattern(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/tryInWaxPattern.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoWearLastTooth   
	  * @Description: TODO(Locator放置基台---上颌  戴牙)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月27日 上午8:25:08
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoWearLastTooth.act")
	public ModelAndView toLocatorTopAbutmentoWearLastTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/wearLastTooth.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoAdjustMouth   
	  * @Description: TODO(Locator放置基台---上颌  戴牙调合)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月27日 上午8:25:52
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoAdjustMouth.act")
	public ModelAndView toLocatorTopAbutmentoAdjustMouth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/adjustMouth.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoFixationToothBridge   
	  * @Description: TODO(Locator放置基台---上颌  固定牙桥)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月27日 上午8:26:51
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoFixationToothBridge.act")
	public ModelAndView toLocatorTopAbutmentoFixationToothBridge(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fixationToothBridge.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoThirdReview   
	  * @Description: TODO(Locator放置基台---上颌  戴牙后第一次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月27日 上午8:27:40
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoThirdReview.act")
	public ModelAndView toLocatorTopAbutmentoThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/thirdReview.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoFourthReview   
	  * @Description: TODO(Locator放置基台---上颌  戴牙后第二次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月27日 上午8:30:06
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoFourthReview.act")
	public ModelAndView toLocatorTopAbutmentoFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fourthReview.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorTopAbutmentoFifthReview   
	  * @Description: TODO(Locator放置基台---上颌  戴牙后第二次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月27日 上午8:30:31
	 */
	@RequestMapping(value = "/toLocatorTopAbutmentoFifthReview.act")
	public ModelAndView toLocatorTopAbutmentoFifthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fifthReview.jsp");
		return mView;
	}
	
	/**
	 * Locator半口修复放置螺丝（下颌） url配置
	 * @param request
	 * @param response
	 * @return
	 */
	
	/**
	  * @Title: toLocatorBottomScrewPreoperativePreparation   
	  * @Description: TODO(Locator半口修复放置螺丝  下颌  术前准备)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月27日 上午8:31:12
	 */
	@RequestMapping("/toLocatorBottomScrewPreoperativePreparation.act")
	public ModelAndView toLocatorBottomScrewPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/preoperativePreparation.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomScrewOperativeTreatment   
	  * @Description: TODO( Locator半口修复放置螺丝  下颌  手术治疗)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:29:38
	 */
	@RequestMapping("/toLocatorBottomScrewOperativeTreatment.act")
	public ModelAndView toLocatorBottomScrewOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewFirstReview.act")
	public ModelAndView toLocatorBottomScrewFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/firstReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewReviewFetchModel.act")
	public ModelAndView toLocatorBottomScrewReviewFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/reviewFetchModel.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewTryIn.act")
	public ModelAndView toLocatorBottomScrewTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/tryIn.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewWearTransitionTooth.act")
	public ModelAndView toLocatorBottomScrewWearTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/wearTransitionTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewTwoDate.act")
	public ModelAndView toLocatorBottomScrewTwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/twoDate.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewSecondReview.act")
	public ModelAndView toLocatorBottomScrewSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewFetchModel.act")
	public ModelAndView toLocatorBottomScrewFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewTryInWaxPattern.act")
	public ModelAndView toLocatorBottomScrewTryInWaxPattern(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/tryInWaxPattern.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewWearLastTooth.act")
	public ModelAndView toLocatorBottomScrewWearLastTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/wearLastTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewAdjustMouth.act")
	public ModelAndView toLocatorBottomScrewAdjustMouth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/adjustMouth.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewFixationToothBridge.act")
	public ModelAndView toLocatorBottomScrewFixationToothBridge(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fixationToothBridge.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewThirdReview.act")
	public ModelAndView toLocatorBottomScrewThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/thirdReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewFourthReview.act")
	public ModelAndView toLocatorBottomScrewFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fourthReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toLocatorBottomScrewFifthReview.act")
	public ModelAndView toLocatorBottomScrewFifthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fifthReview.jsp");
		return mView;
	}
	
	/**
	 * Locator半口修复放置基台（下颌）url配置
	 */
	/**
	  * @Title: toLocatorBottomAbutmentPreoperativePreparation   
	  * @Description: TODO(Locator半口修复放置基台  --下颌   术前准备)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:27:19
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentPreoperativePreparation.act")
	public ModelAndView toLocatorBottomAbutmentPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorBottomAbutment/preoperativePreparation.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toLocatorBottomAbutmentOperativeTreatment.act")
	public ModelAndView toLocatorBottomAbutmentOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorBottomAbutment/operativeTreatment.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentTryIn   
	  * @Description: TODO(Locator放置基台   --下颌  试戴 )   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:32:16
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentTryIn.act")
	public ModelAndView toLocatorBottomAbutmentTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/tryIn.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentWearTransitionTooth   
	  * @Description: TODO(Locator放置基台  --下颌  戴过渡牙)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:34:42
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentWearTransitionTooth.act")
	public ModelAndView toLocatorBottomAbutmentWearTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorBottomAbutment/wearTransitionTooth.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentFirstReview   
	  * @Description: TODO(Locator放置基台 --下颌   第一次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:36:41
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentFirstReview.act")
	public ModelAndView toLocatorBottomAbutmentFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorBottomAbutment/firstReview.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentSecondReview   
	  * @Description: TODO(Locator放置基台 --下颌  第二次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:38:05
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentSecondReview.act")
	public ModelAndView toLocatorBottomAbutmentSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorBottomAbutment/secondReview.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentFetchModel   
	  * @Description: TODO(Locator放置基台 --下颌  取模)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:39:22
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentFetchModel.act")
	public ModelAndView toLocatorBottomAbutmentFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fetchModel.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentTryInWaxPattern   
	  * @Description: TODO(Locator放置基台 --下颌  试戴蜡型)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:40:42
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentTryInWaxPattern.act")
	public ModelAndView toLocatorBottomAbutmentTryInWaxPattern(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorBottomAbutment/tryInWaxPattern.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentWearLastTooth   
	  * @Description: TODO(Locator放置基台 --下颌  戴牙)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:42:01
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentWearLastTooth.act")
	public ModelAndView toLocatorBottomAbutmentWearLastTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorBottomAbutment/wearLastTooth.jsp");
		return mView;
	}
	/**
	  * @Title: toLocatorBottomAbutmentAdjustMouth   
	  * @Description: TODO(Locator放置基台 --下颌 戴牙调合)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:43:25
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentAdjustMouth.act")
	public ModelAndView toLocatorBottomAbutmentAdjustMouth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/adjustMouth.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentFixationToothBridge   
	  * @Description: TODO(Locator放置基台 --下颌   固定牙桥)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:44:45
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentFixationToothBridge.act")
	public ModelAndView toLocatorBottomAbutmentFixationToothBridge(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorBottomAbutment/fixationToothBridge.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentThirdReview   
	  * @Description: TODO(Locator放置基台 --下颌   戴牙后第一次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:46:22
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentThirdReview.act")
	public ModelAndView toLocatorBottomAbutmentThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/thirdReview.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentFourthReview   
	  * @Description: TODO(Locator放置基台 --下颌  戴牙后第二次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:48:03
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentFourthReview.act")
	public ModelAndView toLocatorBottomAbutmentFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorTopAbutment/fourthReview.jsp");
		return mView;
	}
	
	/**
	  * @Title: toLocatorBottomAbutmentFifthReview   
	  * @Description: TODO(Locator放置基台 --下颌  戴牙后第三次复查)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年5月26日 下午5:49:27
	 */
	@RequestMapping(value = "/toLocatorBottomAbutmentFifthReview.act")
	public ModelAndView toLocatorBottomAbutmentFifthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/locatorBottomAbutment/fifthReview.jsp");
		return mView;
	}
	
	/**
	 * All-on-x:种植固定义齿修复上颌--》复合基台+即刻负重：（url配置）
	 */
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadPreoperativePreparation.act")
	public ModelAndView toAllonxTopAbutmentBearloadPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/preoperativePreparation.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadOperativeTreatment.act")
	public ModelAndView toAllonxTopAbutmentBearloadOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
//		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/RperativeTreatment.jsp");
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadWearTransitionTooth.act")
	public ModelAndView toAllonxTopAbutmentBearloadWearTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/wearTransitionTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadFirstReview.act")
	public ModelAndView toAllonxTopAbutmentBearloadFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/operationAfterFirstReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadSecondReview.act")
	public ModelAndView toAllonxTopAbutmentBearloadSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/operationAfterSecondReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadThirdReview.act")
	public ModelAndView toAllonxTopAbutmentBearloadThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/operationAfterThirdReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadFetchModel.act")
	public ModelAndView toAllonxTopAbutmentBearloadFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadReviewFaceBiteUpper.act")
	public ModelAndView toAllonxTopAbutmentBearloadReviewFaceBiteUpper(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/reviewFaceBiteUpper.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadTryInWaxPattern.act")
	public ModelAndView toAllonxTopAbutmentBearloadTryInWaxPattern(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/tryInWaxPattern.jsp");
		return mView;
	}
	
	//美学临时牙
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadAestheticsTemporaryTooth.act")
	public ModelAndView toAllonxTopAbutmentBearloadAestheticsTemporaryTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/aestheticsTemporaryTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadToothReturnFactory.act")
	public ModelAndView toAllonxTopAbutmentBearloadToothReturnFactory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/toothReturnFactory.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadWearLastTooth.act")
	public ModelAndView toAllonxTopAbutmentBearloadWearLastTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/wearLastTooth.jsp");
		return mView;
	}
	
	//戴牙调和
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadAdjustMouth.act")
	public ModelAndView toAllonxTopAbutmentBearloadAdjustMouth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/adjustMouth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadOperationAfterFirstReview.act")
	public ModelAndView toAllonxTopAbutmentBearloadOperationAfterFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/firstReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadOperationAfterSecondReview.act")
	public ModelAndView toAllonxTopAbutmentBearloadOperationAfterSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxTopAbutmentBearloadOperationAfterThirdReview.act")
	public ModelAndView toAllonxTopAbutmentBearloadOperationAfterThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopAbutmentBearload/thirdReview.jsp");
		return mView;
	}
	
	/**
	 * All-on-x 下颌--》复合基台+即刻负重：url配置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAllonxBablPreoperativePreparation.act")
	public ModelAndView toAllonxBablPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/preoperativePreparation.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablOperativeTreatment.act")
	public ModelAndView toAllonxBablOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablWearTransitionTooth.act")
	public ModelAndView toAllonxBablWearTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/wearTransitionTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablFirstReview.act")
	public ModelAndView toAllonxBablFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/operationAfterFirstReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablSecondReview.act")
	public ModelAndView toAllonxBablSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/operationAfterSecondReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablThirdReview.act")
	public ModelAndView toAllonxBablThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/operationAfterThirdReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablFetchModel.act")
	public ModelAndView toAllonxBablFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablReviewFaceBiteUpper.act")
	public ModelAndView toAllonxBablReviewFaceBiteUpper(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/reviewFaceBiteUpper.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablTryInWaxPattern.act")
	public ModelAndView toAllonxBablTryInWaxPattern(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/tryInWaxPattern.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablAestheticsTemporaryTooth.act")
	public ModelAndView toAllonxBablAestheticsTemporaryTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/aestheticsTemporaryTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablToothReturnFactory.act")
	public ModelAndView toAllonxBablToothReturnFactory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/toothReturnFactory.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablWearLastTooth.act")
	public ModelAndView toAllonxBablWearLastTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/wearLastTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablAdjustMouth.act")
	public ModelAndView toAllonxBablAdjustMouth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/adjustMouth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablPperationAfterFirstReview.act")
	public ModelAndView toAllonxBablPperationAfterFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/firstReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablOperationAfterSecondReview.act")
	public ModelAndView toAllonxBablOperationAfterSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBablOperationAfterThirdReview.act")
	public ModelAndView toAllonxBablOperationAfterThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBottomAbutmentBearload/thirdReview.jsp");
		return mView;
	}
	
	/**
	 * 重要位点 大量植骨、骨劈开+同期种植+放置螺丝 url配置  allonxBonePlantScrew
	 * @return
	 */
	@RequestMapping(value = "/toAllonxBonePlantScrewPreoperativePreparation.act")
	public ModelAndView toAllonxBonePlantScrewPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/preoperativePreparation.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewOperativeTreatment.act")
	public ModelAndView toAllonxBonePlantScrewOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewFirstReview.act")
	public ModelAndView toAllonxBonePlantScrewFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/operationAfterFirstReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewSecondReview.act")
	public ModelAndView toAllonxBonePlantScrewSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/operationAfterSecondReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewThirdReview.act")
	public ModelAndView toAllonxBonePlantScrewThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/operationAfterThirdReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewTwoDate.act")
	public ModelAndView toAllonxBonePlantScrewTwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/twoDate.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewFourthReview.act")
	public ModelAndView toAllonxBonePlantScrewFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/fourthReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewFetchModel.act")
	public ModelAndView toAllonxBonePlantScrewFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewReviewFaceBiteUpper.act")
	public ModelAndView toAllonxBonePlantScrewReviewFaceBiteUpper(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/reviewFaceBiteUpper.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewTryInWaxPattern.act")
	public ModelAndView toAllonxBonePlantScrewTryInWaxPattern(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/tryInWaxPattern.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewaAstheticsTemporaryTooth.act")
	public ModelAndView toAllonxBonePlantScrewaAstheticsTemporaryTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/aestheticsTemporaryTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewaToothReturnFactory.act")
	public ModelAndView toAllonxBonePlantScrewaToothReturnFactory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/toothReturnFactory.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewaWearLastTooth.act")
	public ModelAndView toAllonxBonePlantScrewaWearLastTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/wearLastTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewaAdjustMouth.act")
	public ModelAndView toAllonxBonePlantScrewaAdjustMouth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/adjustMouth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewaOperationAfterFirstReview.act")
	public ModelAndView toAllonxBonePlantScrewaOperationAfterFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/firstReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewaOperationAfterSecondReview.act")
	public ModelAndView toAllonxBonePlantScrewaOperationAfterSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toAllonxBonePlantScrewaOperationAfterThirdReview.act")
	public ModelAndView toAllonxBonePlantScrewaOperationAfterThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBonePlantScrew/thirdReview.jsp");
		return mView;
	}
	
	/**
	 * 前牙区 All-on-X即刻负重+窦外提升植骨+延期种植（url配置）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAllonxBlbPlantPreoperativePreparation.act")
	public ModelAndView toAllonxBlbPlantPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/preoperativePreparation.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantOperativeTreatment.act")
	public ModelAndView toAllonxBlbPlantOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantFirstReview.act")
	public ModelAndView toAllonxBlbPlantFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/operationAfterFirstReview.jsp");/*firstReview*/
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantWearTooth.act")
	public ModelAndView toAllonxBlbPlantWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/wearTooth.jsp");
		return mView;
	}
	
	//添加
	@RequestMapping("/toAllonxBlbPlantSecondReviewGraftingbone.act")
	public ModelAndView toAllonxBlbPlantSecondReviewGraftingbone(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/operationAfterSecondReview.jsp");/*secondReviewGraftingbone*/
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantOperativeTreatmentPlant.act")
	public ModelAndView toAllonxBlbPlantOperativeTreatmentPlant(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/operativeTreatmentPlant.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantSecondReviewGraftingboneWire.act")
	public ModelAndView toAllonxBlbPlantSecondReviewGraftingboneWire(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/secondReviewGraftingboneWire.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantSecondReviewGraftingboneWireB.act")
	public ModelAndView toAllonxBlbPlantSecondReviewGraftingboneWireB(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/secondReviewGraftingboneWireB.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantThirdReview.act")
	public ModelAndView toAllonxBlbPlantThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/operationAfterThirdReview.jsp");/*thirdReview*/
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantTwoDate.act")
	public ModelAndView toAllonxBlbPlantTwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/twoDate.jsp");
		return mView;
	}
	
	//二期后3-4周取模（excel中--->二期后植骨）
	@RequestMapping("/toAllonxBlbPlantFetchModel.act")
	public ModelAndView toAllonxBlbPlantFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantTeviewFaceBiteUpper.act")
	public ModelAndView toAllonxBlbPlantTeviewFaceBiteUpper(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/reviewFaceBiteUpper.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlanTryInWaxPattern.act")
	public ModelAndView toAllonxBlbPlanTryInWaxPattern(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/tryInWaxPattern.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantAestheticsTemporaryTooth.act")
	public ModelAndView toAllonxBlbPlantAestheticsTemporaryTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/aestheticsTemporaryTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantToothReturnFactory.act")
	public ModelAndView toAllonxBlbPlantToothReturnFactory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/toothReturnFactory.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantWearLastTooth.act")
	public ModelAndView toAllonxBlbPlantWearLastTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/wearLastTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantAdjustMouth.act")
	public ModelAndView toAllonxBlbPlantAdjustMouth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/adjustMouth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantOperationAfterFirstReview.act")
	public ModelAndView toAllonxBlbPlantOperationAfterFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/firstReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxBlbPlantOperationAfterSecondReview.act")
	public ModelAndView toAllonxBlbPlantOperationAfterSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/secondReviewGraftingbone.jsp");
		return mView;
	}
	@RequestMapping("/toAllonxBlbPlantOperationAfterThirdReview.act")
	public ModelAndView toAllonxBlbPlantOperationAfterThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxBearloadBonePlant/thirdReview.jsp");
		return mView;
	}
	
	/**
	 * 覆盖螺丝+延期负重(上颌）（url配置） allonxTopScrewBearloadTransitionTooth
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAllonxTsblToothPreoperativePreparation.act")
	public ModelAndView toAllonxTsblToothPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/preoperativePreparation.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothOperativeTreatment.act")
	public ModelAndView toAllonxTsblToothOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothFirstReview.act")
	public ModelAndView toAllonxTsblToothFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/firstReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothReviewFetchModel.act")
	public ModelAndView toAllonxTsblToothReviewFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/reviewFetchModel.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothTryIn.act")
	public ModelAndView toAllonxTsblToothTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/tryIn.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothWearTransitionTooth.act")
	public ModelAndView toAllonxTsblToothWearTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/wearTransitionTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothTwoDate.act")
	public ModelAndView toAllonxTsblToothTwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/twoDate.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothSecondReview.act")
	public ModelAndView toAllonxTsblToothSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothFetchModel.act")
	public ModelAndView toAllonxTsblToothFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothReviewFaceBiteUpper.act")
	public ModelAndView toAllonxTsblToothReviewFaceBiteUpper(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/reviewFaceBiteUpper.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothTryInWaxPattern.act")
	public ModelAndView toAllonxTsblToothTryInWaxPattern(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/tryInWaxPattern.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothAestheticsTemporaryTooth.act")
	public ModelAndView toAllonxTsblToothAestheticsTemporaryTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/aestheticsTemporaryTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothToothReturnFactory.act")
	public ModelAndView toAllonxTsblToothToothReturnFactory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/toothReturnFactory.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothToothWearLastTooth.act")
	public ModelAndView toAllonxTsblToothToothWearLastTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/wearLastTooth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothToothAdjustMouth.act")
	public ModelAndView toAllonxTsblToothToothAdjustMouth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/adjustMouth.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothToothOperationAfterFirstReview.act")
	public ModelAndView toAllonxTsblToothToothOperationAfterFirstReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/operationAfterFirstReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothToothOperationAfterSecondReview.act")
	public ModelAndView toAllonxTsblToothToothOperationAfterSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/operationAfterSecondReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toAllonxTsblToothToothOperationAfterThirdReview.act")
	public ModelAndView toAllonxTsblToothToothOperationAfterThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/allonxTopScrewBearloadTransitionTooth/operationAfterThirdReview.jsp");
		return mView;
	}
	
	/**
	 * 种植杆卡修复-无试戴（url配置）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toCueCardAPreoperativePreparation.act")
	public ModelAndView toCueCardAPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardAFetchModel.act")
	public ModelAndView toCueCardAFetchModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/fetchModel.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardAOperativeTreatmentWeartooth.act")
	public ModelAndView toCueCardAOperativeTreatmentWeartooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/operativeTreatmentWeartooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardAWearTransitionTooth.act")
	public ModelAndView toCueCardAWearTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/wearTransitionTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardASecondReview.act")
	public ModelAndView toCueCardASecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardATwoDate.act")
	public ModelAndView toCueCardATwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/twoDate.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardAFetchModelExtendWire.act")
	public ModelAndView toCueCardAFetchModelExtendWire(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/fetchModelExtendWire.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardATryInPole.act")
	public ModelAndView toCueCardATryInPole(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/tryInPole.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardATryInFalsetooth.act")
	public ModelAndView toCueCardATryInFalsetooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/tryInFalsetooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardAWearTooth.act")
	public ModelAndView toCueCardAWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/wearTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardAThirdReview.act")
	public ModelAndView toCueCardAThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/thirdReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardAFourthReview.act")
	public ModelAndView toCueCardAFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/fourthReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardAFifthReview.act")
	public ModelAndView toCueCardAFifthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloA/fifthReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toLcljFlowCueCardEnd.act")
	public ModelAndView toLcljFlowCueCardEnd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/lcljFlowEnd.jsp");
		return mv;
	}
	
	/**
	 * 种植杆卡修复-有试戴（url配置）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toCueCardBPreoperativePreparation.act")
	public ModelAndView toCueCardBPreoperativePreparation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBOperativeTreatment.act")
	public ModelAndView toCueCardBOperativeTreatment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/operativeTreatment.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBTryIn.act")
	public ModelAndView toCueCardBTryIn(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/tryIn.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBWearTransitionTooth.act")
	public ModelAndView toCueCardBWearTransitionTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/wearTransitionTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBSecondReview.act")
	public ModelAndView toCueCardBSecondReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/secondReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBTwoDate.act")
	public ModelAndView toCueCardBTwoDate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/twoDate.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBFetchModelExtendWire.act")
	public ModelAndView toCueCardBFetchModelExtendWire(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/fetchModelExtendWire.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBTryInPole.act")
	public ModelAndView toCueCardBTryInPole(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/tryInPole.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBTryInFalsetooth.act")
	public ModelAndView toCueCardBTryInFalsetooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/tryInFalsetooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBWearTooth.act")
	public ModelAndView toCueCardBWearTooth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/wearTooth.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBThirdReview.act")
	public ModelAndView toCueCardBThirdReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/thirdReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBFourthReview.act")
	public ModelAndView toCueCardBFourthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/fourthReview.jsp");
		return mView;
	}
	
	@RequestMapping(value = "/toCueCardBFifthReview.act")
	public ModelAndView toCueCardBFifthReview(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/hudh/operationNodeProcess/CueCardPreoperativeModuloB/fifthReview.jsp");
		return mView;
	}
	
	@RequestMapping("/toLcljFlowCueCardBEnd.act")
	public ModelAndView toLcljFlowCueCardBEnd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/operationNodeProcess/lcljFlowEnd.jsp");
		return mv;
	}
	
	@RequestMapping("/toLcljPageFetchInfo.act")
	public ModelAndView toLcljPageFetchInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String menuId = request.getParameter("menuId");
		String userId = request.getParameter("userId");
		String username = request.getParameter("username");
		mv.addObject("userId",userId);
		mv.addObject("username",username);
		mv.addObject("menuid",menuId);
		mv.setViewName("/hudh/lclj/lclj_patient.jsp");
		return mv;
	}
}
