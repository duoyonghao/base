package com.hudh.area.controller;

import com.hudh.area.service.IProviceService;
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
@RequestMapping({"HUDH_ProviceAct"})
public class HUDH_ProviceAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_ProviceAct.class);
  @Autowired
  private IProviceService proviceService;
  
  @RequestMapping({"/findAll.act"})
  public String findAll(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      List<JSONObject> list = this.proviceService.findAll();
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
}
