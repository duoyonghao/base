package com.kqds.controller.app;

import com.kqds.entity.sys.YZPerson;
import com.kqds.service.app.KQDS_LogLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_LogAct"})
public class KQDS_LogAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_LogAct.class);
  @Autowired
  private KQDS_LogLogic logic;
  
  @RequestMapping({"/selectPushListPage.act"})
  public String selectPushListPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String isread = request.getParameter("isread");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(isread)) {
        map.put("pcpushreaded", isread);
      }
      if (!YZUtility.isNullorEmpty(person.getSeqId())) {
        map.put("reciveuser", person.getSeqId());
      }
      String ispaging = request.getParameter("ispaging");
      if (!YZUtility.isNullorEmpty(ispaging))
      {
        String pageSize = request.getParameter("pageSize");
        
        String nowpage = request.getParameter("nowpage");
        map.put("ispaging", ispaging);
        map.put("pageSize", pageSize);
        map.put("nowpage", nowpage);
      }
      else
      {
        map.put("ispaging", ConstUtil.PAGE_FLAG_0);
      }
      JSONObject mdata = this.logic.getPushListPage(TableNameUtil.KQDS_PUSH, map);
      YZUtility.DEAL_SUCCESS(mdata, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/setReaded.act"})
  public String setReaded(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String noreadSeqId = request.getParameter("noreadSeqId");
      if (YZUtility.isNotNullOrEmpty(noreadSeqId)) {
        noreadSeqId = YZUtility.ConvertStringIds4Query(noreadSeqId);
      }
      this.logic.updatePcpushreaded(noreadSeqId);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectSysListPage.act"})
  public String selectSysListPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(person.getSeqId())) {
        map.put("createuser", person.getSeqId());
      }
      String ispaging = request.getParameter("ispaging");
      if (!YZUtility.isNullorEmpty(ispaging))
      {
        String pageSize = request.getParameter("pageSize");
        
        String nowpage = request.getParameter("nowpage");
        map.put("ispaging", ispaging);
        map.put("pageSize", pageSize);
        map.put("nowpage", nowpage);
      }
      else
      {
        map.put("ispaging", ConstUtil.PAGE_FLAG_0);
      }
      JSONObject mdata = this.logic.getSysListPage(TableNameUtil.KQDS_BCJL, map);
      YZUtility.DEAL_SUCCESS(mdata, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
