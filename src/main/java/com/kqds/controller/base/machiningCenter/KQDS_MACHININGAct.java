package com.kqds.controller.base.machiningCenter;

import com.kqds.entity.base.KqdsMachining;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.machiningCenter.KQDS_MACHININGLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_MACHININGAct"})
public class KQDS_MACHININGAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_MACHININGAct.class);
  @Autowired
  private KQDS_MACHININGLogic logic;
  
  @RequestMapping({"insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      KqdsMachining dp = new KqdsMachining();
      BeanUtils.populate(dp, request.getParameterMap());
      if (!YZUtility.isNullorEmpty(dp.getSeqId()))
      {
        this.logic.update(dp);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_MACHINING, dp, dp.getCreateuser(), 
          "KQDS_MACHINING", request);
      }
      else
      {
        dp.setSeqId(YZUtility.getUUID());
        dp.setSystemNumber(YZUtility.getSystemID());
        this.logic.insert(dp);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MACHINING, dp, dp.getCreateuser(), 
          "KQDS_MACHINING", request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getProcessingRecords.act"})
  public String getProcessingRecords(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String processUnit = request.getParameter("processUnit");
    String searching = request.getParameter("searching");
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String fjstarttime = request.getParameter("fjstarttime");
    String fjendtime = request.getParameter("fjendtime");
    String hjstarttime = request.getParameter("hjstarttime");
    String hjendtime = request.getParameter("hjendtime");
    String dzstarttime = request.getParameter("dzstarttime");
    String dzendtime = request.getParameter("dzendtime");
    String status = request.getParameter("status");
    Map<String, String> map = new HashMap();
    try
    {
      YZUtility.isNotNullOrEmpty(status);
      

      YZUtility.isNotNullOrEmpty(searching);
      if (YZUtility.isNotNullOrEmpty(processUnit)) {
        map.put("processUnit", processUnit);
      }
      if (YZUtility.isNotNullOrEmpty(starttime)) {
        map.put("starttime", starttime);
      }
      if (YZUtility.isNotNullOrEmpty(endtime)) {
        map.put("endtime", endtime);
      }
      if (YZUtility.isNotNullOrEmpty(searching)) {
        map.put("searching", searching);
      }
      List<JSONObject> list = this.logic.getRecords(map);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getWorksheet.act"})
  public String getWorksheetByusercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String searching = request.getParameter("searching");
    try
    {
      Map<String, String> map = new HashMap();
      map.put("searching", searching);
      List<JSONObject> records = this.logic.getRecords(map);
      YZUtility.RETURN_LIST(records, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getParticularsBySeqId.act"})
  public String getParticularsBySeqId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      Map<String, String> map = new HashMap();
      map.put("seqId", id);
      JSONObject json = this.logic.getParticularsBySeqId(map);
      YZUtility.DEAL_SUCCESS(json, id, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getGenre.act"})
  public String getGenre(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      List<JSONObject> list = this.logic.getGenre(id);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectMachineByUsercode.act"})
  public String selectMachineByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      List<JSONObject> list = this.logic.selectMachineByUsercode(usercode);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateStatus.act"})
  public String updateStatus(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String status = request.getParameter("status");
    String data = request.getParameter("onclickrow");
    try
    {
      KqdsMachining dp = new KqdsMachining();
      if ((data != null) && (!data.equals("")))
      {
        JSONObject fromObject = JSONObject.fromObject(data);
        dp.setSeqId(fromObject.getString("seqId"));
        dp.setSystemNumber(fromObject.getString("systemnumber"));
        dp.setUserCode(fromObject.getString("usercode"));
        dp.setUserName(fromObject.getString("username"));
        dp.setOrganization(fromObject.getString("organization"));
        dp.setStatus(status);
      }
      this.logic.updateStatus(dp, request);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_MACHINING, dp, person.getSeqId(), 
        "KQDS_MACHINING", request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/delRecord.act"})
  public String delRecord(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String id = request.getParameter("id");
      Map<String, String> map = new HashMap();
      map.put("id", id);
      this.logic.delRecord(map);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getFlow.act"})
  public String getFlow(HttpServletRequest request, HttpServletResponse response)
  {
    String worksheetId = request.getParameter("worksheetId");
    try
    {
      Map<String, String> map = new HashMap();
      map.put("worksheetId", worksheetId);
      List<JSONObject> list = this.logic.getFlow(map);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception localException) {}
    return null;
  }
}
