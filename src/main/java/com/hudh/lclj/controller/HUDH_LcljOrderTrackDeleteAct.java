package com.hudh.lclj.controller;

import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import com.hudh.lclj.service.ILcljOrderTrackDeleteService;
import com.kqds.util.sys.YZUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/HUDH_LcljOrderTrackDeleteAct"})
public class HUDH_LcljOrderTrackDeleteAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_LcljOrderTrackDeleteAct.class);
  
  @Autowired
  private ILcljOrderTrackDeleteService trackDeleteService;
  
  @RequestMapping({"/save.act"})
  public String save(HttpServletRequest request, HttpServletResponse response) throws Exception {
    LcljOrderTrackDeleteRecord dp = new LcljOrderTrackDeleteRecord();
    try {
      this.trackDeleteService.save(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
