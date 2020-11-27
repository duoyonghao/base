package com.kqds.controller.base.changeReceive;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_enteringInformationAct"})
public class KQDS_enteringInformationAct {
    @RequestMapping({"/setCertificate.act"})
    public ModelAndView setCertificate(HttpServletRequest request, HttpServletResponse response)
    {
        String menuid = request.getParameter("menuid");
        ModelAndView mv = new ModelAndView();
        mv.addObject("menuid", menuid);
        mv.setViewName("/kqdsFront/index/kfzx/certificateRegister.jsp");
        return mv;
    }
}