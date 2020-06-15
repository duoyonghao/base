package com.kqds.controller.base.changeReceive;

import com.kqds.service.base.changeReceive.KQDS_changeKeFuLogic;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.export.ExportTable;
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
@RequestMapping({"KQDS_ChangeKeFuAct"})
public class KQDS_ChangeKeFuAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_ChangeKeFuAct.class);
  @Autowired
  private KQDS_changeKeFuLogic logic;
  
  @RequestMapping({"/setKefu.act"})
  public ModelAndView setKefu(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.addObject("organization", organization);
    mv.setViewName("/kqdsFront/index/kfzx/xc_kefu_set.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSetKefu.act"})
  public ModelAndView toSetKefu(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/kfzx/kefu_set.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHisKefu.act"})
  public ModelAndView toHisKefu(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/kfzx/kefu_his.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKefuIndex.act"})
  public ModelAndView toKefuIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/kfzx/kefu_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectNoPage.act"})
  public String selectNoPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String queryinput = request.getParameter("queryinput");
      String toper = request.getParameter("toper");
      String oldper = request.getParameter("oldper");
      String createuser = request.getParameter("createuser");
      

      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      if (!YZUtility.isNullorEmpty(toper)) {
        map.put("toper", toper);
      }
      if (!YZUtility.isNullorEmpty(oldper)) {
        map.put("oldper", oldper);
      }
      if (!YZUtility.isNullorEmpty(createuser)) {
        map.put("createuser", createuser);
      }
      List<JSONObject> list = this.logic.selectWithPage("KQDS_CHANGE_KEFU", map);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("指定客服列表", fieldArr, fieldnameArr, list, response, request);
        return null;
      }
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
