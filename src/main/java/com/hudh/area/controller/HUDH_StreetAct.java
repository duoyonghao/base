package com.hudh.area.controller;

import com.hudh.area.service.IStreetService;
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
@RequestMapping({"HUDH_StreetAct"})
public class HUDH_StreetAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_StreetAct.class);
  
  @Autowired
  private IStreetService streetService;
  
  @RequestMapping({"/findStreetByAreaCode.act"})
  public String findStreetByAreaCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String areaCode = request.getParameter("areaCode");
    try {
      List<JSONObject> list = this.streetService.findStreetByAreaCode(areaCode);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
