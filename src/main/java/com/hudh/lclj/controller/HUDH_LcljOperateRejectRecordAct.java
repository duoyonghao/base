package com.hudh.lclj.controller;

import com.hudh.lclj.service.ILcljOperationNodeRejectService;
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
@RequestMapping({"/HUDH_LcljOperateRejectRecordAct"})
public class HUDH_LcljOperateRejectRecordAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_LcljOperateRejectRecordAct.class);
  @Autowired
  private ILcljOperationNodeRejectService rejectService;
  
  @RequestMapping({"/findOperationNodeRejectByOrderNumber.act"})
  public String findOperationNodeRejectByOrderNumber(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String orderNumber = request.getParameter("orderNumber");
    try
    {
      List<JSONObject> list = this.rejectService.findOperationNodeRejectByOrderNumber(orderNumber);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
}
