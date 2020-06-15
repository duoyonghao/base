package com.kqds.controller.base.paiban;

import com.kqds.entity.base.KqdsPaiban;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.paiban.KQDS_PaibanLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_PaibanAct"})
public class KQDS_PaibanAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_PaibanAct.class);
  
  @Autowired
  private KQDS_PaibanLogic logic;
  
  @Autowired
  private YZDeptLogic deptLogic;
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @RequestMapping({"/toPaiBan.act"})
  public ModelAndView toPaiBan(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String personid = request.getParameter("personid");
    String regdept = request.getParameter("regdept");
    String stepnum = request.getParameter("stepnum");
    ModelAndView mv = new ModelAndView();
    mv.addObject("personid", personid);
    mv.addObject("regdept", regdept);
    mv.addObject("stepnum", stepnum);
    mv.setViewName("/kqdsFront/yyzx/paiban.jsp");
    return mv;
  }
  
  @RequestMapping({"/toPaiBanExport.act"})
  public ModelAndView toPaiBanExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String personid = request.getParameter("personid");
    String regdept = request.getParameter("regdept");
    ModelAndView mv = new ModelAndView();
    mv.addObject("personid", personid);
    mv.addObject("regdept", regdept);
    mv.setViewName("/kqdsFront/yyzx/paiban_export.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsPaiban dp = new KqdsPaiban();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      dp.setStartdate(formatter.format(formatter.parse(dp.getStartdate())));
      dp.setEnddate(formatter.format(formatter.parse(dp.getEnddate())));
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_PAIBAN, dp);
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_PAIBAN, dp, TableNameUtil.KQDS_PAIBAN, request);
      } else {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setDeptid(this.deptLogic.getDeptSeqIdByUserSeqId(dp.getPersonid()));
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_PAIBAN, dp);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_PAIBAN, dp, TableNameUtil.KQDS_PAIBAN, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      KqdsPaiban en = (KqdsPaiban)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_PAIBAN, seqId);
      if (en != null) {
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_PAIBAN, seqId);
        BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_PAIBAN, en, TableNameUtil.KQDS_PAIBAN, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/showXML.act"})
  public String showXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      String orderstatus = request.getParameter("orderstatus");
      String personid = request.getParameter("personid");
      String regdept = request.getParameter("regdept");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      starttime = YZUtility.parseDate2(starttime);
      endtime = YZUtility.parseDate2(endtime);
      if (!YZUtility.isNullorEmpty(starttime) && !starttime.equals("null"))
        map.put("starttime", starttime); 
      if (!YZUtility.isNullorEmpty(endtime) && !endtime.equals("null")) {
        if (endtime.length() == 19)
          endtime = String.valueOf(endtime.substring(0, 10)) + ConstUtil.TIME_END; 
        map.put("endtime", endtime);
      } 
      if (!YZUtility.isNullorEmpty(orderstatus) && !orderstatus.equals("null"))
        map.put("orderstatus", orderstatus); 
      if (!YZUtility.isNullorEmpty(personid) && !personid.equals("null"))
        map.put("personid", personid); 
      if (!YZUtility.isNullorEmpty(regdept) && !regdept.equals("null"))
        map.put("deptid", regdept); 
      List<JSONObject> list = this.logic.selectList(TableNameUtil.KQDS_PAIBAN, map, ChainUtil.getCurrentOrganization(request));
      for (int i = 0; i < list.size(); i++) {
        JSONObject hd = list.get(i);
        StringBuffer bf = new StringBuffer();
        bf.append("<br>人员：");
        bf.append(hd.getString("personidname"));
        bf.append("<br>排班类型：");
        bf.append(hd.getString("orderstatus").equals("休息") ? "<span style='color:red'>休息</span>" : hd.getString("orderstatus"));
        bf.append("<br>创建人：");
        bf.append(hd.getString("createusername"));
        hd.put("text", bf.toString());
        hd.put("id", hd.getString("seq_id"));
        hd.put("start_date", hd.getString("startdate"));
        hd.put("end_date", hd.getString("enddate"));
        hd.put("pername", hd.getString("personidname"));
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("result", "ok");
      jobj.put("path", list.toString());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/excelStandardTemplateOut.act"})
  public String excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String deptId = request.getParameter("deptId");
      String seqId = request.getParameter("seqId");
      String startdate = request.getParameter("startdate");
      String enddate = request.getParameter("enddate");
      List<JSONObject> perlist = this.personLogic.getPersonList(request, deptId, seqId);
      ExportTable.exportExcel4PaiBan("排班模板", perlist, startdate, enddate, response);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getPersoPaiban.act"})
  public String getPersoPaiban(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String deptId = request.getParameter("deptId");
      String seqId = request.getParameter("seqId");
      List<JSONObject> list = this.personLogic.getPersonList(request, deptId, seqId);
      JSONObject jobj = new JSONObject();
      jobj.put("result", "ok");
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/checkPaiban.act"})
  public String checkPaiban(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
      String personid = request.getParameter("personid");
      Map<String, String> map = new HashMap<>();
      map.put("starttime", starttime);
      map.put("personid", personid);
      JSONObject jobj = new JSONObject();
      int count = this.logic.checkPaiban(map, ChainUtil.getCurrentOrganization(request));
      jobj.put("data", Integer.valueOf(count));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
