package com.hudh.ykzz.controller;

import com.alibaba.fastjson.JSON;
import com.hudh.ykzz.entity.YkzzManu;
import com.hudh.ykzz.service.IYkzzManuService;
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
@RequestMapping({"/HUDH_YkzzManuAct"})
public class HUDH_YkzzManuAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_YkzzManuAct.class);
  
  @Autowired
  private IYkzzManuService ykzzManuService;
  
  @RequestMapping({"/insertYkzzManu.act"})
  public String insertYkzzManu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String manuName = request.getParameter("manuName");
    String orderno = request.getParameter("orderno");
    String manuCode = request.getParameter("manuCode");
    YkzzManu ykzzManu = new YkzzManu();
    if (YZUtility.isNotNullOrEmpty(orderno))
      ykzzManu.setOrderno(Integer.valueOf(orderno).intValue()); 
    ykzzManu.setManuCode(manuCode);
    ykzzManu.setManuName(manuName);
    try {
      this.ykzzManuService.insertYkzzManu(ykzzManu, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findYkzzManuById.act"})
  public String findYkzzManuById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      if (YZUtility.isNotNullOrEmpty(id)) {
        YkzzManu ykzzManu = this.ykzzManuService.findYkzzManuById(id);
        JSONObject jo = new JSONObject();
        jo.put("ykzzManu", JSON.toJSONString(ykzzManu));
        YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteYkzzManuById.act"})
  public String deleteYkzzManuById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      if (YZUtility.isNotNullOrEmpty(id)) {
        this.ykzzManuService.deleteYkzzManuById(id);
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateYkzzManuById.act"})
  public String updateYkzzManuById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String manuName = request.getParameter("manuName");
    String orderno = request.getParameter("orderno");
    YkzzManu ykzzManu = new YkzzManu();
    ykzzManu.setId(id);
    if (YZUtility.isNotNullOrEmpty(orderno))
      ykzzManu.setOrderno(Integer.valueOf(orderno).intValue()); 
    ykzzManu.setManuName(manuName);
    try {
      if (YZUtility.isNotNullOrEmpty(id) && YZUtility.isNotNullOrEmpty(manuName) && 
        YZUtility.isNotNullOrEmpty(orderno)) {
        this.ykzzManuService.updateYkzzManuById(ykzzManu);
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findAllManu.act"})
  public void findAllManu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = ChainUtil.getCurrentOrganization(request);
    try {
      List<JSONObject> list = this.ykzzManuService.findAllManu(organization);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
}
