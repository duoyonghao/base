package com.hudh.ykzz.controller;

import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.entity.YkzzManufacturers;
import com.hudh.ykzz.service.IYzzManufacturersService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
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

@Controller
@RequestMapping({"/YkzzManufacturersAct"})
public class YkzzManufacturersAct
{
  private Logger logger = LoggerFactory.getLogger(YkzzManufacturersAct.class);
  @Autowired
  private IYzzManufacturersService iYzzManufacturersService;
  
  @RequestMapping({"/insertManufacturers.act"})
  public String insertManufacturers(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String manuName = request.getParameter("manuName");
    String orderno = request.getParameter("orderno");
    String manuCode = request.getParameter("manuCode");
    YkzzManufacturers ykzzManufacturers = new YkzzManufacturers();
    ykzzManufacturers.setManufacturersName(manuName);
    ykzzManufacturers.setId(YZUtility.getUUID());
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    ykzzManufacturers.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    ykzzManufacturers.setCreator(person.getUserName());
    ykzzManufacturers.setOrganization(organization);
    if (YZUtility.isNotNullOrEmpty(orderno)) {
      ykzzManufacturers.setOrderno(orderno);
    }
    ykzzManufacturers.setManufacturersCode(manuCode);
    try
    {
      this.iYzzManufacturersService.insertManufacturers(ykzzManufacturers);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteManufacturersById.act"})
  public String deleteManufacturersById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      this.iYzzManufacturersService.deleteManufacturers(id);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findAllManufacturers.act"})
  public String findAllManufacturers(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = ChainUtil.getCurrentOrganization(request);
    try
    {
      List<JSONObject> list = this.iYzzManufacturersService.findAllManufacturers(organization);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateManufacturersById.act"})
  public String updateManufacturersById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String manuName = request.getParameter("manuName");
    String orderno = request.getParameter("orderno");
    YkzzManufacturers ykzzManufacturers = new YkzzManufacturers();
    ykzzManufacturers.setId(id);
    ykzzManufacturers.setOrderno(orderno);
    ykzzManufacturers.setManufacturersName(manuName);
    try
    {
      if ((YZUtility.isNotNullOrEmpty(id)) && (YZUtility.isNotNullOrEmpty(manuName)) && 
        (YZUtility.isNotNullOrEmpty(orderno)))
      {
        this.iYzzManufacturersService.updateManufacturers(ykzzManufacturers);
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findManufacturersById.act"})
  public String findManufacturersById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      JSONObject json = this.iYzzManufacturersService.findManufacturersById(id);
      YZUtility.DEAL_SUCCESS(json, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
}
