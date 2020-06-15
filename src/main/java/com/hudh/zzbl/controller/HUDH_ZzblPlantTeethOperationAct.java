package com.hudh.zzbl.controller;

import com.hudh.zzbl.entity.ZzblPlantTeethOperation;
import com.hudh.zzbl.service.IZzblPlantTeethOperationService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/HUDH_ZzblPlantTeethOperationAct"})
public class HUDH_ZzblPlantTeethOperationAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_ZzblPlantTeethOperationAct.class);
  @Autowired
  private IZzblPlantTeethOperationService operationService;
  
  @RequestMapping({"/save.act"})
  public String save(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ZzblPlantTeethOperation dp = new ZzblPlantTeethOperation();
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    dp.setCreateuser(person.getUserId());
    dp.setOrganization(organization);
    try
    {
      this.operationService.insertZzblPlantTeethOperation(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findZzblOprationById.act"})
  public JSONObject findZzblOprationById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      JSONObject json = this.operationService.findZzblPlantTeethOperationById(id);
      YZUtility.DEAL_SUCCESS(json, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
}
