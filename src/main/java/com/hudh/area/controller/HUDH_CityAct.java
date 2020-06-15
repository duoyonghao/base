package com.hudh.area.controller;

import com.hudh.area.service.ICityService;
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
@RequestMapping({"HUDH_CityAct"})
public class HUDH_CityAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_CityAct.class);
  @Autowired
  private ICityService cityService;
  
  @RequestMapping({"/findCityByProviceCode.act"})
  public String findCityByProviceCode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String proviceCode = request.getParameter("proviceCode");
    try
    {
      List<JSONObject> list = this.cityService.findCityByProviceCode(proviceCode);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
}
