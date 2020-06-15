package com.hudh.doctorpick.controller;

import com.hudh.doctorpick.service.IGoodsDoctorPickInDetailService;
import com.kqds.util.sys.SessionUtil;
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
@RequestMapping({"/GoodsDoctorPickInDetailAct"})
public class GoodsDoctorPickInDetailAct
{
  private static Logger logger = LoggerFactory.getLogger(GoodsDoctorPickInDetailAct.class);
  @Autowired
  private IGoodsDoctorPickInDetailService detailService;
  
  @RequestMapping({"/findDoctorPickInDetailByIncode.act"})
  public String findDoctorPickInDetailByIncode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String incode = request.getParameter("incode");
    
    String visualstaff = SessionUtil.getVisualstaff(request);
    try
    {
      List<JSONObject> list = this.detailService.findDoctorPickInDetailByIncode(incode, visualstaff);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateGoodsDoctorPickInDetail.act"})
  public String updateGoodsDoctorPickInDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seq_id = request.getParameter("seq_id");
    try
    {
      this.detailService.updateGoodsDoctorPickInDetail(seq_id);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteDoctorPickInDetailBySeqId.act"})
  public String deleteDoctorPickInDetailBySeqId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String indetailseqId = request.getParameter("indetailseqId");
    try
    {
      this.detailService.deleteDoctorPickInDetailBySeqId(indetailseqId);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/findDoctorPickInDetailById.act"})
  public String findDoctorPickInDetailById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      JSONObject json = this.detailService.findDoctorPickInDetailById(id);
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
}
