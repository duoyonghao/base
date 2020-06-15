package com.kqds.controller.app;

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.app.KQDS_WorkLogic;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_WorkAct"})
public class KQDS_WorkAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_WorkAct.class);
  
  @Autowired
  private KQDS_WorkLogic logic;
  
  @Autowired
  private KQDS_CostOrderLogic costlogic;
  
  @RequestMapping({"/selectCountWork.act"})
  public String selectCountWork(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> wdata = this.logic.getWdList(TableNameUtil.KQDS_NET_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      List<JSONObject> mdata = this.logic.getMzList(TableNameUtil.KQDS_HOSPITAL_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      int regdata = this.logic.getRegCount(TableNameUtil.KQDS_REG, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      List<JSONObject> vdata = this.logic.getHfList(TableNameUtil.KQDS_VISIT, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject mz = new JSONObject();
      int mztotal = 0, mzsmcount = 0;
      if (mdata != null && mdata.size() > 0) {
        mztotal = mdata.size();
        for (JSONObject mzobj : mdata) {
          if (mzobj.getInt("orderstatus") == 1)
            mzsmcount++; 
        } 
      } 
      mz.put("total", Integer.valueOf(mztotal));
      mz.put("smcount", Integer.valueOf(mzsmcount));
      JSONObject wd = new JSONObject();
      int wdtotal = 0, wdsmcount = 0;
      if (wdata != null && wdata.size() > 0) {
        wdtotal = wdata.size();
        for (JSONObject wdobj : wdata) {
          if (wdobj.getInt("doorstatus") == 1)
            wdsmcount++; 
        } 
      } 
      wd.put("total", Integer.valueOf(wdtotal));
      wd.put("smcount", Integer.valueOf(wdsmcount));
      JSONObject hf = new JSONObject();
      int vtotal = 0, vwcount = 0;
      if (vdata != null && vdata.size() > 0) {
        vtotal = vdata.size();
        for (JSONObject vobj : vdata) {
          if (vobj.getInt("status") == 0)
            vwcount++; 
        } 
      } 
      hf.put("total", Integer.valueOf(vtotal));
      hf.put("vwcount", Integer.valueOf(vwcount));
      JSONObject reg = new JSONObject();
      reg.put("regtotal", Integer.valueOf(regdata));
      JSONObject jobj = new JSONObject();
      jobj.put("mzdata", mz);
      jobj.put("wddata", wd);
      jobj.put("vdata", hf);
      jobj.put("regdata", reg);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectMzList.act"})
  public String selectMzList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String askperson = request.getParameter("askperson");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String search = request.getParameter("search");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(askperson))
        map.put("askperson", askperson); 
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      if (!YZUtility.isNullorEmpty(search))
        map.put("search", search); 
      String ispaging = request.getParameter("ispaging");
      if (!YZUtility.isNullorEmpty(ispaging)) {
        String pageSize = request.getParameter("pageSize");
        String nowpage = request.getParameter("nowpage");
        map.put("ispaging", ispaging);
        map.put("pageSize", pageSize);
        map.put("nowpage", nowpage);
      } else {
        map.put("ispaging", ConstUtil.PAGE_FLAG_0);
      } 
      String visualstaff = SessionUtil.getVisualstaff(request);
      JSONObject mdata = this.logic.getMzListPage(TableNameUtil.KQDS_HOSPITAL_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      YZUtility.DEAL_SUCCESS(mdata, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectMzListToday.act"})
  public String selectMzListToday(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> mdata = this.logic.getMzList(TableNameUtil.KQDS_HOSPITAL_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", mdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectWdListToday.act"})
  public String selectWdListToday(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> wdata = this.logic.getWdList(TableNameUtil.KQDS_NET_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", wdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectRegList.act"})
  public String selectRegList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String visualstaff = SessionUtil.getVisualstaff(request);
      String regsort = request.getParameter("regsort");
      String recesort = request.getParameter("recesort");
      String search = request.getParameter("search");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(regsort))
        map.put("regsort", regsort); 
      if (!YZUtility.isNullorEmpty(recesort))
        map.put("recesort", recesort); 
      if (!YZUtility.isNullorEmpty(search))
        map.put("search", search); 
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      String czSeqId = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID);
      map.put("czSeqId", czSeqId);
      String ispaging = request.getParameter("ispaging");
      if (!YZUtility.isNullorEmpty(ispaging)) {
        String pageSize = request.getParameter("pageSize");
        String nowpage = request.getParameter("nowpage");
        map.put("ispaging", ispaging);
        map.put("pageSize", pageSize);
        map.put("nowpage", nowpage);
      } else {
        map.put("ispaging", ConstUtil.PAGE_FLAG_0);
      } 
      JSONObject regdata = this.logic.getRegList(TableNameUtil.KQDS_REG, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      YZUtility.DEAL_SUCCESS(regdata, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectUserInfo.act"})
  public String selectUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      JSONObject regdata = this.logic.selectUserInfo(TableNameUtil.KQDS_USERDOCUMENT, usercode);
      JSONObject jobj = new JSONObject();
      jobj.put("data", regdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectHfToDayList.act"})
  public String selectHfToDayList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> vdata = this.logic.getHfList(TableNameUtil.KQDS_VISIT, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", vdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectHfList.act"})
  public String selectHfList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      String ispaging = request.getParameter("ispaging");
      if (!YZUtility.isNullorEmpty(ispaging)) {
        String pageSize = request.getParameter("pageSize");
        String nowpage = request.getParameter("nowpage");
        map.put("ispaging", ispaging);
        map.put("pageSize", pageSize);
        map.put("nowpage", nowpage);
      } else {
        map.put("ispaging", ConstUtil.PAGE_FLAG_0);
      } 
      String visualstaff = SessionUtil.getVisualstaff(request);
      JSONObject vdata = this.logic.getHfListPage(TableNameUtil.KQDS_VISIT, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      YZUtility.DEAL_SUCCESS(vdata, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectCountWorkTomorrow.act"})
  public String selectCountWorkTomorrow(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> wdata = this.logic.getWdList(TableNameUtil.KQDS_NET_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      List<JSONObject> mdata = this.logic.getMzList(TableNameUtil.KQDS_HOSPITAL_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      int mztotal = 0;
      if (mdata != null && mdata.size() > 0)
        mztotal = mdata.size(); 
      int wdtotal = 0;
      if (wdata != null && wdata.size() > 0)
        wdtotal = wdata.size(); 
      JSONObject jobj = new JSONObject();
      jobj.put("mzdata", Integer.valueOf(mztotal));
      jobj.put("wddata", Integer.valueOf(wdtotal));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectMzListTomorrow.act"})
  public String selectMzListTomorrow(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> mdata = this.logic.getMzList(TableNameUtil.KQDS_HOSPITAL_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", mdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectWdListTomorrow.act"})
  public String selectWdListTomorrow(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> wdata = this.logic.getWdList(TableNameUtil.KQDS_NET_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", wdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/qfSearch.act"})
  public String qfSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      BootStrapPage bp = new BootStrapPage();
      Map<String, String> map = new HashMap<>();
      map.put("endtime", YZUtility.getCurDateTimeStr());
      String visualstaff = SessionUtil.getVisualstaff(request);
      map.put("visualstaff", visualstaff);
      map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject data = this.costlogic.qfSearch(bp, person, map, request);
      data.put("nowdate", YZUtility.getCurDateTimeStr());
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectWdYyForUser.act"})
  public String selectWdYyForUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      List<JSONObject> wdata = this.logic.getWdListByUsercode(TableNameUtil.KQDS_NET_ORDER, usercode, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", wdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectMzYyForUser.act"})
  public String selectMzYyForUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      List<JSONObject> mdata = this.logic.getMzListByUsercode(TableNameUtil.KQDS_HOSPITAL_ORDER, usercode, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", mdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectHfForUser.act"})
  public String selectHfForUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      List<JSONObject> mdata = this.logic.getHfListByUsercode(TableNameUtil.KQDS_VISIT, usercode, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", mdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectCostForUser.act"})
  public String selectCostForUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      List<JSONObject> mdata = this.logic.getCostListByUsercode(TableNameUtil.KQDS_COSTORDER, usercode, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("data", mdata);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
