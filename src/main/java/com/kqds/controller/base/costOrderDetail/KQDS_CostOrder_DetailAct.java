package com.kqds.controller.base.costOrderDetail;

import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.memberRecord.KQDS_Member_RecordLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
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
@RequestMapping({"KQDS_CostOrder_DetailAct"})
public class KQDS_CostOrder_DetailAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_CostOrder_DetailAct.class);
  @Autowired
  private KQDS_CostOrder_DetailLogic logic;
  @Autowired
  private KQDS_Member_RecordLogic logicmember;
  @Autowired
  private YZPersonLogic personLogic;
  
  @RequestMapping({"/toQfqkWin.act"})
  public ModelAndView toQfqkWin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/coatOrder/qfqkWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCostDetail_Tuidan.act"})
  public ModelAndView toCostDetail_Tuidan(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/coatOrder/cost_detail_tuidan.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCostDetail.act"})
  public ModelAndView toCostDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/index/center/cost_detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCostDetail2.act"})
  public ModelAndView toCostDetail2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String costno = request.getParameter("costno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("costno", costno);
    mv.setViewName("/kqdsFront/coatOrder/cost_detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMxcxCenter.act"})
  public ModelAndView toMxcxCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/jdzx/mxcx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/update.act"})
  public String update(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsCostorderDetail dp = new KqdsCostorderDetail();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键不能为空");
      }
      KqdsCostorderDetail en = (KqdsCostorderDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, seqId);
      if (en == null) {
        throw new Exception("数据不存在");
      }
      if ((!YZUtility.isNullorEmpty(dp.getZltime())) && (YZUtility.isNullorEmpty(dp.getKaifa())))
      {
        en.setKaifa("已治疗");
        en.setZltime(dp.getZltime());
      }
      if ((YZUtility.isNullorEmpty(dp.getZltime())) && (!YZUtility.isNullorEmpty(dp.getKaifa())))
      {
        if (dp.getKaifa().equals("已治疗"))
        {
          en.setKaifa("已治疗");
          en.setZltime(YZUtility.getCurDateTimeStr());
        }
        if ((!dp.getKaifa().equals("已治疗")) && (!YZUtility.isNullorEmpty(en.getZltime())))
        {
          en.setKaifa("未治疗");
          en.setZltime("");
        }
      }
      en.setCzperson(person.getUserName());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, en);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_COSTORDER_DETAIL, en, en.getUsercode(), TableNameUtil.KQDS_COSTORDER_DETAIL, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getAll.act"})
  public String getAll(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      String starttime = request.getParameter("sfstarttime");
      String endtime = request.getParameter("sfendtime");
      if (YZUtility.isNullorEmpty(starttime)) {
        starttime = request.getParameter("starttime");
      }
      if (YZUtility.isNullorEmpty(endtime)) {
        endtime = request.getParameter("endtime");
      }
      String costno = request.getParameter("costno");
      String basetype = request.getParameter("basetype");
      String nexttype = request.getParameter("xfnexttype");
      String queryinput = request.getParameter("queryinput");
      String regdept = request.getParameter("regdept");
      String askperson = request.getParameter("askpersonSearch");
      String doctor = request.getParameter("doctor");
      String nurse = request.getParameter("nurse");
      String createuser = request.getParameter("createuser");
      String devchannel = request.getParameter("devchannel");
      String nexttype1 = request.getParameter("nexttype1");
      String recesort = request.getParameter("recesort");
      String regsort = request.getParameter("regsort");
      String remark = request.getParameter("remark");
      String isyjjitem = request.getParameter("isyjjitem");
      String zlstatus = request.getParameter("zlstatus");
      String zlstarttime = request.getParameter("zlstarttime");
      String zlendtime = request.getParameter("zlendtime");
      String itemname = request.getParameter("itemname");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(itemname)) {
        map.put("itemname", itemname);
      }
      String organization = ChainUtil.getOrganizationFromUrl(request);
      if (YZUtility.isNullorEmpty(organization)) {
        map.put("organization", ChainUtil.getCurrentOrganization(request));
      } else {
        map.put("organization", organization);
      }
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(zlstarttime))
      {
        zlstarttime = zlstarttime + ConstUtil.TIME_START;
        map.put("zlstarttime", zlstarttime);
      }
      if (!YZUtility.isNullorEmpty(zlendtime))
      {
        zlendtime = zlendtime + ConstUtil.TIME_END;
        map.put("zlendtime", zlendtime);
      }
      if (!YZUtility.isNullorEmpty(zlstatus)) {
        map.put("zlstatus", zlstatus);
      }
      if (!YZUtility.isNullorEmpty(askperson)) {
        map.put("askperson", askperson);
      }
      if (!YZUtility.isNullorEmpty(doctor)) {
        map.put("doctor", doctor);
      }
      if (!YZUtility.isNullorEmpty(nurse)) {
        map.put("nurse", nurse);
      }
      if (!YZUtility.isNullorEmpty(createuser)) {
        map.put("createuser", createuser);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype1)) {
        map.put("nexttype1", nexttype1);
      }
      if (!YZUtility.isNullorEmpty(recesort)) {
        map.put("recesort", recesort);
      }
      if (!YZUtility.isNullorEmpty(regsort)) {
        map.put("regsort", regsort);
      }
      if (!YZUtility.isNullorEmpty(costno)) {
        map.put("costno", costno);
      }
      if (!YZUtility.isNullorEmpty(regdept)) {
        map.put("regdept", regdept);
      }
      if (!YZUtility.isNullorEmpty(basetype)) {
        map.put("basetype", basetype);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      if (!YZUtility.isNullorEmpty(remark)) {
        map.put("remark", remark);
      }
      if (!YZUtility.isNullorEmpty(isyjjitem)) {
        map.put("isyjjitem", isyjjitem);
      }
      if (!YZUtility.isNullorEmpty(sortName))
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        JSONObject data = this.logic.selectNoPage(bp, TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request));
        if (data != null)
        {
          ExportBean bean = ExportTable.initExcel4RsWrite("费用明细", fieldArr, fieldnameArr, response, request);
          bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List)data.get("rows"), "costOrderDetail_selectNoPage");
          ExportTable.writeExcel4DownLoad("费用明细", bean.getWorkbook(), response);
        }
        return null;
      }
      JSONObject data = this.logic.selectNoPage(bp, TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request));
      







      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getAllTuidan.act"})
  public String getAllTuidan(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String costno = request.getParameter("costno");
      String cjStatus = request.getParameter("cjStatus");
      String qf = request.getParameter("qf");
      String basetype = request.getParameter("basetype");
      String nexttype = request.getParameter("nexttype");
      String queryinput = request.getParameter("queryinput");
      
      String askperson = request.getParameter("askperson");
      String doctor = request.getParameter("doctor");
      String nurse = request.getParameter("nurse");
      String createuser = request.getParameter("createuser");
      String devchannel = request.getParameter("devchannel");
      String nexttype1 = request.getParameter("nexttype1");
      String recesort = request.getParameter("recesort");
      String regsort = request.getParameter("regsort");
      String remark = request.getParameter("remark");
      String backuser = request.getParameter("backuser");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      

      String organization = ChainUtil.getOrganizationFromUrl(request);
      if (YZUtility.isNullorEmpty(organization)) {
        map.put("organization", ChainUtil.getCurrentOrganization(request));
      } else {
        map.put("organization", organization);
      }
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(askperson)) {
        map.put("askperson", askperson);
      }
      if (!YZUtility.isNullorEmpty(doctor)) {
        map.put("doctor", doctor);
      }
      if (!YZUtility.isNullorEmpty(nurse)) {
        map.put("nurse", nurse);
      }
      if (!YZUtility.isNullorEmpty(createuser)) {
        map.put("createuser", createuser);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype1)) {
        map.put("nexttype1", nexttype1);
      }
      if (!YZUtility.isNullorEmpty(recesort)) {
        map.put("recesort", recesort);
      }
      if (!YZUtility.isNullorEmpty(regsort)) {
        map.put("regsort", regsort);
      }
      if (!YZUtility.isNullorEmpty(costno)) {
        map.put("costno", costno);
      }
      if (!YZUtility.isNullorEmpty(cjStatus)) {
        map.put("cjStatus", cjStatus);
      }
      if (!YZUtility.isNullorEmpty(qf)) {
        map.put("qf", qf);
      }
      if (!YZUtility.isNullorEmpty(basetype)) {
        map.put("basetype", basetype);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      if (!YZUtility.isNullorEmpty(remark)) {
        map.put("remark", remark);
      }
      if (!YZUtility.isNullorEmpty(backuser)) {
        map.put("backuser", backuser);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      JSONObject data = this.logic.selectNoPage(bp, "KQDS_COSTORDER_DETAIL_TUIDAN", person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request));
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("费用明细", fieldArr, fieldnameArr, data.getJSONArray("rows"), response, request);
        return null;
      }
      YZUtility.RETURN_LIST(data.getJSONArray("rows"), response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/orderAndMember.act"})
  public String orderAndMember(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String basetype = request.getParameter("basetype");
      String nexttype = request.getParameter("nexttype");
      String itemname = request.getParameter("itemname");
      String queryinput = request.getParameter("queryinput");
      String persontype = request.getParameter("persontype");
      
      String regdept = request.getParameter("regdept");
      String doctor = request.getParameter("doctor");
      String askperson = request.getParameter("askperson");
      String createuser = request.getParameter("createuser");
      String kfr = request.getParameter("kfr");
      String devchannel = request.getParameter("devchannel");
      String nexttype1 = request.getParameter("nexttype1");
      String shouli = request.getParameter("shouli");
      String gongju = request.getParameter("gongju");
      String remark = request.getParameter("remark");
      String introducer = request.getParameter("introducer");
      String zlstatus = request.getParameter("zlstatus");
      String zlstarttime = request.getParameter("zlstarttime");
      String zlendtime = request.getParameter("zlendtime");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      

      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (!YZUtility.isNullorEmpty(organization)) {
        map.put("organization", organization);
      }
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(zlstarttime))
      {
        zlstarttime = zlstarttime + ConstUtil.TIME_START;
        map.put("zlstarttime", zlstarttime);
      }
      if (!YZUtility.isNullorEmpty(zlendtime))
      {
        zlendtime = zlendtime + ConstUtil.TIME_END;
        map.put("zlendtime", zlendtime);
      }
      if (!YZUtility.isNullorEmpty(zlstatus)) {
        map.put("zlstatus", zlstatus);
      }
      if (!YZUtility.isNullorEmpty(regdept)) {
        map.put("regdept", regdept);
      }
      if (!YZUtility.isNullorEmpty(doctor)) {
        map.put("doctor", doctor);
      }
      if (!YZUtility.isNullorEmpty(askperson)) {
        map.put("askperson", askperson);
      }
      if (!YZUtility.isNullorEmpty(createuser)) {
        map.put("createuser", createuser);
      }
      if (!YZUtility.isNullorEmpty(kfr)) {
        map.put("kfr", kfr);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype1)) {
        map.put("nexttype1", nexttype1);
      }
      if (!YZUtility.isNullorEmpty(shouli)) {
        map.put("shouli", shouli);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("gongju", gongju);
      }
      if (!YZUtility.isNullorEmpty(basetype)) {
        map.put("basetype", basetype);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      if (!YZUtility.isNullorEmpty(introducer)) {
        map.put("introducer", introducer);
      }
      if (!YZUtility.isNullorEmpty(itemname)) {
        map.put("itemname", itemname);
      }
      String visualstaffwd = "";
      if (!YZUtility.isNullorEmpty(persontype))
      {
        map.put("persontype", persontype);
        visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg(persontype);
      }
      if (!YZUtility.isNullorEmpty(remark)) {
        map.put("remark", remark);
      }
      String usercodes = request.getParameter("usercodes");
      if (!YZUtility.isNullorEmpty(usercodes)) {
        map.put("usercodes", usercodes);
      }
      if (!YZUtility.isNullorEmpty(sortName))
      {
        map.put("sortName", sortName);
        map.put("sortName1", sortName);
        map.put("sortOrder", sortOrder);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        JSONObject resut1 = this.logic.selectNoPageOrder(bp, TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, visualstaffwd);
        JSONObject resut2 = this.logicmember.selectListOrder(bp, TableNameUtil.KQDS_MEMBER_RECORD, map, visualstaff, visualstaffwd);
        if ((resut1 != null) && (resut2 != null))
        {
          ExportBean bean = ExportTable.initExcel4RsWrite("费用明细", fieldArr, fieldnameArr, response, request);
          bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List)resut1.get("rows"), "selectNoPageOrder");
          

          bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List)resut2.get("rows"), "selectListOrder");
          ExportTable.writeExcel4DownLoad("费用明细", bean.getWorkbook(), response);
        }
        return null;
      }
      JSONObject jobjall = new JSONObject();
      JSONObject dataorder = new JSONObject();
      JSONObject datamember = new JSONObject();
      




















      List<JSONObject> list = new ArrayList();
      
      dataorder = this.logic.selectNoPageOrder(bp, TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, visualstaffwd);
      datamember = this.logicmember.selectListOrder(bp, TableNameUtil.KQDS_MEMBER_RECORD, map, visualstaff, visualstaffwd);
      int orderTotal = dataorder.getInt("total");
      int memberTotal = datamember.getInt("total");
      List<JSONObject> listorder = dataorder.getJSONArray("rows");
      List<JSONObject> listmember = datamember.getJSONArray("rows");
      if ((memberTotal <= bp.getOffset()) && (orderTotal > bp.getOffset()))
      {
        list.addAll(listorder);
      }
      else if ((memberTotal > bp.getOffset()) && (orderTotal <= bp.getOffset()))
      {
        list.addAll(listmember);
      }
      else
      {
        list.addAll(listorder);
        list.addAll(listmember);
      }
      jobjall.put("rows", list);
      if (dataorder.getInt("total") > datamember.getInt("total")) {
        jobjall.put("total", Integer.valueOf(dataorder.getInt("total")));
      } else {
        jobjall.put("total", Integer.valueOf(datamember.getInt("total")));
      }
      if ((bp.getOffset() == 0) && (YZUtility.isNullorEmpty(flag)))
      {
        BigDecimal realmoney = BigDecimal.ZERO;
        BigDecimal payxj = BigDecimal.ZERO;
        BigDecimal paybank = BigDecimal.ZERO;
        BigDecimal payyb = BigDecimal.ZERO;
        BigDecimal paywx = BigDecimal.ZERO;
        BigDecimal payzfb = BigDecimal.ZERO;
        BigDecimal paymmd = BigDecimal.ZERO;
        BigDecimal paybdfq = BigDecimal.ZERO;
        BigDecimal payother1 = BigDecimal.ZERO;
        if (dataorder.getInt("total") > 0)
        {
          realmoney = realmoney.add(new BigDecimal(dataorder.getString("realmoney")));
          payxj = payxj.add(new BigDecimal(dataorder.getString("payxj")));
          paybank = paybank.add(new BigDecimal(dataorder.getString("paybank")));
          payyb = payyb.add(new BigDecimal(dataorder.getString("payyb")));
          paywx = paywx.add(new BigDecimal(dataorder.getString("paywx")));
          payzfb = payzfb.add(new BigDecimal(dataorder.getString("payzfb")));
          paymmd = paymmd.add(new BigDecimal(dataorder.getString("paymmd")));
          paybdfq = paybdfq.add(new BigDecimal(dataorder.getString("paybdfq")));
          payother1 = payother1.add(new BigDecimal(dataorder.getString("payother1")));
        }
        if (datamember.getInt("total") > 0)
        {
          realmoney = realmoney.add(new BigDecimal(datamember.getString("realmoney")));
          payxj = payxj.add(new BigDecimal(datamember.getString("payxj")));
          paybank = paybank.add(new BigDecimal(datamember.getString("paybank")));
          payyb = payyb.add(new BigDecimal(datamember.getString("payyb")));
          paywx = paywx.add(new BigDecimal(datamember.getString("paywx")));
          payzfb = payzfb.add(new BigDecimal(datamember.getString("payzfb")));
          paymmd = paymmd.add(new BigDecimal(datamember.getString("paymmd")));
          paybdfq = paybdfq.add(new BigDecimal(datamember.getString("paybdfq")));
          payother1 = payother1.add(new BigDecimal(datamember.getString("payother1")));
        }
        jobjall.put("realmoney", realmoney);
        jobjall.put("payxj", payxj);
        jobjall.put("paybank", paybank);
        jobjall.put("payyb", payyb);
        jobjall.put("paywx", paywx);
        jobjall.put("payzfb", payzfb);
        jobjall.put("paymmd", paymmd);
        jobjall.put("paybdfq", paybdfq);
        jobjall.put("payother1", payother1);
      }
      YZUtility.DEAL_SUCCESS(jobjall, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getRsktj.act"})
  public String getRsktj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String skr = request.getParameter("skr");
      String askperson = request.getParameter("askperson");
      String wdperson = request.getParameter("wdperson");
      String yxperson = request.getParameter("yxperson");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      if (skr != null) {
        map.put("skr", skr);
      }
      if (askperson != null) {
        map.put("askperson", askperson);
      }
      if (wdperson != null) {
        map.put("wdperson", wdperson);
      }
      if (yxperson != null) {
        map.put("yxperson", yxperson);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      List list = this.logic.selectRsktj(TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, organization, request);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("日收款查询", fieldArr, fieldnameArr, list, response, request);
        return null;
      }
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getRsktj4WD.act"})
  public String getRsktj4WD(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String skr = request.getParameter("skr");
      String askperson = request.getParameter("askperson");
      String wdperson = request.getParameter("wdperson");
      String yxperson = request.getParameter("yxperson");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      if (skr != null) {
        map.put("skr", skr);
      }
      if (askperson != null) {
        map.put("askperson", askperson);
      }
      if (wdperson != null) {
        map.put("wdperson", wdperson);
      }
      if (yxperson != null) {
        map.put("yxperson", yxperson);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      List list = this.logic.selectRsktj(TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, organization, request);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("日收款查询", fieldArr, fieldnameArr, list, response, request);
        return null;
      }
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      KqdsCostorderDetail en = (KqdsCostorderDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, seqId);
      if (ConstUtil.QF_STATUS_1 == en.getIsqfreal().intValue())
      {
        en.setCostno("");
        this.logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, en);
      }
      else
      {
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, seqId);
      }
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_COSTORDER_DETAIL, en, en.getUsercode(), TableNameUtil.KQDS_COSTORDER_DETAIL, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      
      KqdsCostorderDetail en = (KqdsCostorderDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, seqId);
      if (en == null) {
        throw new Exception("数据不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectQfDetail.act"})
  public String selectQfDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      
      Map<String, String> map = new HashMap();
      map.put("usercode", usercode);
      List<JSONObject> list = this.logic.selectWithPageLzjl2(TableNameUtil.KQDS_COSTORDER, map);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectNoPage.act"})
  public String selectNoPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String costno = request.getParameter("costno");
      String regno = request.getParameter("regno");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(costno)) {
        map.put("costno", costno);
      }
      if (!YZUtility.isNullorEmpty(regno)) {
        map.put("regno", regno);
      }
      List<JSONObject> list = this.logic.selectWithPage(TableNameUtil.KQDS_COSTORDER_DETAIL, map, "");
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectNoPage4Cost.act"})
  public String selectNoPage4Cost(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String costno = request.getParameter("costno");
      String regno = request.getParameter("regno");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(costno)) {
        map.put("costno", costno);
      }
      if (!YZUtility.isNullorEmpty(regno)) {
        map.put("regno", regno);
      }
      List<JSONObject> list = this.logic.selectWithPage(TableNameUtil.KQDS_COSTORDER_DETAIL, map, "");
      for (JSONObject detail : list)
      {
        String qfbh = detail.getString("qfbh");
        String createtime = detail.getString("createtime");
        int expireflag = this.logic.judgeIFExpire(createtime, qfbh);
        detail.put("expireflag", Integer.valueOf(expireflag));
      }
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/NoselectPage.act"})
  public String NoselectPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      String costno = request.getParameter("costno");
      String usercode = request.getParameter("usercode");
      


      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(costno)) {
        map.put("costno", costno);
      }
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      List<JSONObject> list = this.logic.selectWithPageLzjl(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountBB.act"})
  public String selectCountBB(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      List<KqdsCostorderDetail> list = new ArrayList();
      map.put("organization", ChainUtil.getOrganizationFromUrl(request));
      
      list = this.logic.getCountTj(TableNameUtil.KQDS_COSTORDER_DETAIL, map, ChainUtil.getOrganizationFromUrl(request));
      JSONObject jobj = new JSONObject();
      jobj.put("rows", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountBBQylr.act"})
  public String selectCountBBQylr(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      int year = Integer.parseInt(starttime.substring(0, 4));
      int month = Integer.parseInt(starttime.substring(starttime.length() - 2, starttime.length()));
      int days = YZUtility.getDaysByYearMonth(year, month);
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      List<Object> listxz = new ArrayList();
      List<Object> listAll = new ArrayList();
      
      listAll = this.logic.getCountQylrAll(TableNameUtil.KQDS_REG, year, month, days, ChainUtil.getOrganizationFromUrl(request));
      
      listxz = this.logic.getCountQylrNew(TableNameUtil.KQDS_REG, year, month, days, ChainUtil.getOrganizationFromUrl(request));
      
      BigDecimal all = this.logic.getCountQylrAll(TableNameUtil.KQDS_REG, year, month, ChainUtil.getOrganizationFromUrl(request));
      
      BigDecimal xz = this.logic.getCountQylrNew(TableNameUtil.KQDS_REG, year, month, ChainUtil.getOrganizationFromUrl(request));
      JSONObject jobj = new JSONObject();
      jobj.put("listAll", listAll);
      jobj.put("listxz", listxz);
      jobj.put("all", all);
      jobj.put("xz", xz);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getOrderListByUsercode.act"})
  public String getOrderListByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      String usercode = request.getParameter("usercode");
      String doctorno = request.getParameter("doctorno");
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      if (!YZUtility.isNullorEmpty(doctorno)) {
        map.put("doctor", doctorno);
      }
      map.put("status", ConstUtil.COST_ORDER_STATUS_2);
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> list = this.logic.selectWithPage(TableNameUtil.KQDS_COSTORDER_DETAIL, map, visualstaff);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkTf.act"})
  public String checkTf(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String itemno = request.getParameter("itemno");
      String usercode = request.getParameter("usercode");
      String qfbh = request.getParameter("qfbh");
      String detailId = request.getParameter("detailId");
      
      JSONObject objall = new JSONObject();
      
      JSONObject obj = this.logic.checkTf(usercode, itemno, qfbh, detailId);
      

      JSONObject objrefund = this.logic.checkTfRefund(usercode, itemno, qfbh, detailId);
      objall.put("symoney", new BigDecimal(obj.getString("paymoney")).subtract(new BigDecimal(objrefund.getString("tkmoney"))));
      PrintWriter pw = response.getWriter();
      pw.println(objall.toString());
      pw.flush();
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/searchOrderZs.act"})
  public String searchOrderZs(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String costno = request.getParameter("costno");
      BigDecimal obj = this.logic.searchOrderZs(TableNameUtil.KQDS_COSTORDER_DETAIL, costno);
      PrintWriter pw = response.getWriter();
      pw.println(KqdsBigDecimal.round(obj, 2));
      pw.flush();
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/printSfxm.act"})
  public String printSfxm(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String costno = request.getParameter("costno");
      JSONObject jobj = new JSONObject();
      JSONObject FKFS = this.logic.printSfxm(TableNameUtil.KQDS_COSTORDER_DETAIL, costno, request);
      jobj.put("FKFS", FKFS);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/searchZqfByusercode.act"})
  public String searchZqfByusercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      String sftime = request.getParameter("sftime");
      BigDecimal obj = this.logic.selectQfAll(usercode, sftime);
      PrintWriter pw = response.getWriter();
      pw.println(obj.toString());
      pw.flush();
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
