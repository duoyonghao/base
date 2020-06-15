package com.kqds.controller.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/SignatureAct"})
public class SignatureAct {
  @RequestMapping({"/toSignature.act"})
  public ModelAndView toSignature(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String category = request.getParameter("category");
    String url = "/kqdsFront/signature/signature.jsp";
    ModelAndView model = new ModelAndView();
    model.addObject("category", category);
    model.setViewName(url);
    return model;
  }
}
