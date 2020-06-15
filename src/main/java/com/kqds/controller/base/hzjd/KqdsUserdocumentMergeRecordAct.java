package com.kqds.controller.base.hzjd;

import com.kqds.entity.base.KqdsUserdocumentMergeRecord;
import com.kqds.service.base.hzjd.UserdocumentMergeRecordLogic;
import com.kqds.util.sys.YZUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KqdsUserdocumentMergeRecordAct"})
public class KqdsUserdocumentMergeRecordAct {
  private Logger logger = LoggerFactory.getLogger(KqdsUserdocumentMergeRecordAct.class);
  
  @Autowired
  private UserdocumentMergeRecordLogic mergeLogic;
  
  @RequestMapping({"/toHzjd_NetPlantDept.act"})
  public ModelAndView toHzjd_NetPlantDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/hzjd_net_plant.jsp");
    return mv;
  }
  
  @RequestMapping({"/saveMergeRecord.act"})
  public String saveMergeRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
    KqdsUserdocumentMergeRecord dp = new KqdsUserdocumentMergeRecord();
    try {
      this.mergeLogic.saveMergeRecord(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
