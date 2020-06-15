package com.kqds.controller.base.paiban;

import com.kqds.service.base.paiban.KQDS_Paiban_TypeLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_Paiban_TypeAct"})
public class KQDS_Paiban_TypeAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Paiban_TypeAct.class);
  
  @Autowired
  private KQDS_Paiban_TypeLogic logic;
  
  @RequestMapping({"/toPaibanTypeList.act"})
  public ModelAndView toPaibanTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqdsFront/paiban/list_kqds_paiban_type.jsp");
    return mv;
  }
  
  @RequestMapping({"/toPaibanTypeEdit.act"})
  public ModelAndView toPaibanTypeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/paiban/edit_kqds_paiban_type.jsp");
    return mv;
  }
  
  @RequestMapping({"/toPaibanTypeDetail.act"})
  public ModelAndView toPaibanTypeDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/paiban/detail_kqds_paiban_type.jsp");
    return mv;
  }
  
  @RequestMapping({"/toPaibanTypeAdd.act"})
  public ModelAndView toPaibanTypeAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqdsFront/paiban/add_kqds_paiban_type.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectDetailBytpename.act"})
  public String selectDetailBytpename(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String typename = request.getParameter("typename");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(typename))
        map.put("typename", typename); 
      List<JSONObject> list = this.logic.selectList(TableNameUtil.KQDS_PAIBAN_TYPE, map, ChainUtil.getCurrentOrganization(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
