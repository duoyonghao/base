package com.hudh.zzbl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/ZzblViewAct"})
public class ZzblViewAct {
  @RequestMapping({"/toPlantKnowbookInfor.act"})
  public ModelAndView toPlantKnowbookInfor(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/plantCaseInfor/plantKnowbook.jsp");
    return mv;
  }
  
  @RequestMapping({"/topulloutKnowbookInfor.act"})
  public ModelAndView topulloutKnowbookInfor(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/plantCaseInfor/pulloutKnowbook.jsp");
    return mv;
  }
  
  @RequestMapping({"/tolocatorKnowbookInfor.act"})
  public ModelAndView tolocatorKnowbookInfor(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/plantCaseInfor/locatorKnowbook.jsp");
    return mv;
  }
  
  @RequestMapping({"/toRepairProjectInfor.act"})
  public ModelAndView toRepairProjectInfor(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/plantCaseInfor/repairProject.jsp");
    return mv;
  }
  
  @RequestMapping({"/toOperationExamineInfor.act"})
  public ModelAndView toOperationExamineInfor(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/plantCaseInfor/operationExamine.jsp");
    return mv;
  }
  
  @RequestMapping({"/toOperationRecordInfor.act"})
  public ModelAndView toOperationRecordInfor(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/plantCaseInfor/operationRecord.jsp");
    return mv;
  }
  
  @RequestMapping({"/toExamineDiagnoseInfor.act"})
  public ModelAndView toExamineDiagnoseInfor(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/plantCaseInfor/examineDiagnose.jsp");
    return mv;
  }
  
  @RequestMapping({"/toExamineDiagnoseInform.act"})
  public ModelAndView toExamineDiagnoseInform(HttpServletRequest request, HttpServletResponse response) {
    String status = request.getParameter("status");
    ModelAndView mv = new ModelAndView();
    mv.addObject("status", status);
    mv.setViewName("/hudh/lclj/plantCaseInfor/inform.jsp");
    return mv;
  }
  
  @RequestMapping({"/toExamineDiagnoseCase.act"})
  public ModelAndView toExamineDiagnoseCase(HttpServletRequest request, HttpServletResponse response) {
    String status = request.getParameter("status");
    ModelAndView mv = new ModelAndView();
    mv.addObject("status", status);
    mv.setViewName("/hudh/lclj/plantCaseInfor/case.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDiagnosisProjectInfor.act"})
  public ModelAndView toDiagnosisProjectInfor(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/plantCaseInfor/diagnosisProject.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAnamnesisInfor.act"})
  public ModelAndView toAnamnesisInfor(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/plantCaseInfor/anamnesis.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZzllPlantKnowbook.act"})
  public ModelAndView toZzllOperation(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mView = new ModelAndView();
    String order_number = request.getParameter("order_number");
    String id = request.getParameter("id");
    mView.addObject("order_number", order_number);
    mView.addObject("id", id);
    mView.setViewName("/hudh/lclj/plantCase/plantKnowbook.jsp");
    return mView;
  }
  
  @RequestMapping({"/topulloutKnowbook.act"})
  public ModelAndView topulloutKnowbook(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mView = new ModelAndView();
    String order_number = request.getParameter("order_number");
    String id = request.getParameter("id");
    mView.addObject("order_number", order_number);
    mView.addObject("id", id);
    mView.setViewName("/hudh/lclj/plantCase/pulloutKnowbook.jsp");
    return mView;
  }
  
  @RequestMapping({"/tolocatorKnowbook.act"})
  public ModelAndView tolocatorKnowbook(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mView = new ModelAndView();
    String order_number = request.getParameter("order_number");
    String id = request.getParameter("id");
    mView.addObject("order_number", order_number);
    mView.addObject("id", id);
    mView.setViewName("/hudh/lclj/plantCase/locatorKnowbook.jsp");
    return mView;
  }
  
  @RequestMapping({"/toZzllAnammesis.act"})
  public ModelAndView toZzllAnammesis(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mView = new ModelAndView();
    String order_number = request.getParameter("order_number");
    String id = request.getParameter("id");
    mView.addObject("order_number", order_number);
    mView.addObject("id", id);
    mView.setViewName("/hudh/lclj/plantCase/anamnesis.jsp");
    return mView;
  }
  
  @RequestMapping({"/toZzllDiagnosisProject.act"})
  public ModelAndView toZzllDiagnosisProject(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mView = new ModelAndView();
    String order_number = request.getParameter("order_number");
    String id = request.getParameter("id");
    mView.addObject("order_number", order_number);
    mView.addObject("id", id);
    mView.setViewName("/hudh/lclj/plantCase/diagnosisProject.jsp");
    return mView;
  }
  
  @RequestMapping({"/toZzllExamineDiagnose.act"})
  public ModelAndView toZzllExamineDiagnose(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mView = new ModelAndView();
    String order_number = request.getParameter("order_number");
    String id = request.getParameter("id");
    mView.addObject("order_number", order_number);
    mView.addObject("id", id);
    mView.setViewName("/hudh/lclj/plantCase/examineDiagnose.jsp");
    return mView;
  }
  
  @RequestMapping({"/toZzllRepairProject.act"})
  public ModelAndView toZzllRepairProject(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mView = new ModelAndView();
    String order_number = request.getParameter("order_number");
    String id = request.getParameter("id");
    mView.addObject("order_number", order_number);
    mView.addObject("id", id);
    mView.setViewName("/hudh/lclj/plantCase/repairProject.jsp");
    return mView;
  }
  
  @RequestMapping({"/toZzllAttentionTopo.act"})
  public ModelAndView toZzllAttentionTopo(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mView = new ModelAndView();
    mView.setViewName("/hudh/lclj/plantCase/attentionTopo.jsp");
    return mView;
  }
  
  @RequestMapping({"/toZzllAttentionWholeHalf.act"})
  public ModelAndView toZzllAttentionWholeHalf(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mView = new ModelAndView();
    mView.setViewName("/hudh/lclj/plantCase/attentionWholehalf.jsp");
    return mView;
  }
}
