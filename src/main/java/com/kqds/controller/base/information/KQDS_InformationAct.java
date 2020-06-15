package com.kqds.controller.base.information;

import com.kqds.service.base.information.KQDS_InformationLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.List;
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
@RequestMapping({"KQDS_InformationAct"})
public class KQDS_InformationAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_InformationAct.class);
  @Autowired
  private KQDS_InformationLogic logic;
  
  @RequestMapping({"/toIndexLs.act"})
  public ModelAndView toWdyySearch(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqds/information/index_ls.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/information/detail_kqds_information.jsp");
    return mv;
  }
  
  @RequestMapping({"/getInfoList.act"})
  public String getInfoList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String searchinput = request.getParameter("searchinput");
      List<JSONObject> en = this.logic.selectList(TableNameUtil.KQDS_INFORMATION, searchinput, ChainUtil.getCurrentOrganization(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
