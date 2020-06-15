package com.kqds.controller.base.outProcessingUnit;

import com.kqds.entity.base.KqdsOutprocessingUnit;
import com.kqds.service.base.outProcessingUnit.KQDS_outProcessing_UnitLogic;
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
@RequestMapping({"KQDS_OutProcessing_UnitAct"})
public class KQDS_OutProcessing_UnitAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessing_UnitAct.class);
  @Autowired
  private KQDS_outProcessing_UnitLogic logic;
  
  @RequestMapping({"/toIndexLs.act"})
  public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqds/outProcessingUnit/index_ls.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectDetailByCode.act"})
  public String selectDetailByCode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String code = request.getParameter("code");
      
      Map<String, String> map = new HashMap();
      map.put("code", code);
      
      map.put("organization", ChainUtil.getOrganizationFromUrl(request));
      List<KqdsOutprocessingUnit> en = (List)this.logic.loadList(TableNameUtil.KQDS_OUTPROCESSING_UNIT, map);
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("retMsrg", "操作成功");
      if (en.size() > 0) {
        jobj.put("data", en.get(0));
      } else {
        jobj.put("data", new KqdsOutprocessingUnit());
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getUnitList4Back.act"})
  public String getUnitList4Back(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String isAdd = request.getParameter("isAdd");
      
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      List<JSONObject> en = this.logic.getUnitList4Back(isAdd, organization);
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("retMsrg", "操作成功");
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getUnitList.act"})
  public String getUnitList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String isAdd = request.getParameter("isAdd");
      
      String organization = ChainUtil.getOrganizationFromUrl(request);
      List<JSONObject> en = this.logic.getUnitList(isAdd, organization);
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("retMsrg", "操作成功");
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
