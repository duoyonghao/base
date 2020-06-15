package com.hudh.area.controller;

import com.hudh.area.service.IAreaService;
import com.kqds.util.sys.YZUtility;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"HUDH_AreaAct"})
public class HUDH_AreaAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_AreaAct.class);
  @Autowired
  private IAreaService areaService;
  
  @RequestMapping({"/findAreaByCityCode.act"})
  public String findAreaByCityCode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String cityCode = request.getParameter("cityCode");
    try
    {
      List<JSONObject> list = this.areaService.findAreaByCityCode(cityCode);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
}
