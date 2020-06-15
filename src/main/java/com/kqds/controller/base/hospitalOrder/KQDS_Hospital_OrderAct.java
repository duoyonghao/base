package com.kqds.controller.base.hospitalOrder;

import com.hudh.lclj.service.ILcljOperationNodeInforService;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hospitalOrder.KQDS_Hospital_OrderLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_Hospital_OrderAct"})
public class KQDS_Hospital_OrderAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_Hospital_OrderAct.class);
  @Autowired
  private KQDS_Hospital_OrderLogic logic;
  @Autowired
  private YZDictLogic dictLogic;
  @Autowired
  private YZPersonLogic personLogic;
  @Autowired
  private YZDeptLogic deptLogic;
  @Autowired
  private KQDS_REGLogic regLogic;
  @Autowired
  private ILcljOperationNodeInforService inforService;
  
  @RequestMapping({"/getCurrDatetHosOrderInfo.act"})
  public String getCurrDatetHosOrderInfo(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(usercode)) {
        throw new Exception("患者编号不能为空");
      }
      JSONObject hosorder = this.logic.getTopHosOrderCurrDay(usercode);
      JSONObject jobj = new JSONObject();
      jobj.put("data", hosorder);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String order_number = request.getParameter("order_number");
      String nodeId = request.getParameter("nodeId");
      String orderTime = request.getParameter("starttime");
      


      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsHospitalOrder dp = new KqdsHospitalOrder();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      dp.setStarttime(formatter.format(formatter.parse(dp.getStarttime())));
      dp.setEndtime(formatter.format(formatter.parse(dp.getEndtime())));
      
      String organization = dp.getOrganization();
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      if (YZUtility.isNullorEmpty(dp.getUsercode())) {
        throw new Exception("usercode值不能为空");
      }
      Map<String, String> map = new HashMap();
      map.put("usercode", dp.getUsercode());
      
      List<KqdsUserdocument> en = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
      if ((en == null) || (en.size() == 0)) {
        throw new Exception("患者不存在，usercode值为：" + dp.getUsercode());
      }
      YZPerson yydoctor = (YZPerson)this.logic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, dp.getAskperson());
      if (yydoctor == null) {
        throw new Exception("预约的医生在系统中不存在，医生seqId：" + dp.getAskperson());
      }
      dp.setRegdept(yydoctor.getDeptId());
      if (YZUtility.isNullorEmpty(dp.getUsername())) {
        dp.setUsername(((KqdsUserdocument)en.get(0)).getUsername());
      } else if (!dp.getUsername().equals(((KqdsUserdocument)en.get(0)).getUsername())) {
        throw new Exception("患者编号和患者名称不匹配，请联系系统管理员！<br>usercode：" + dp.getUsercode() + " username：" + ((KqdsUserdocument)en.get(0)).getUsername());
      }
      if (!YZUtility.isNullorEmpty(seqId))
      {
        String startTime = dp.getStarttime();
        this.inforService.updateOrderTimeHospital(order_number, nodeId, startTime.substring(0, 16));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, dp);
        
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_HOSPITAL_ORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_HOSPITAL_ORDER, request);
        
        PushUtil.saveTx4ModifyHosOrder(dp, request);
      }
      else
      {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrderno(uuid);
        dp.setOrderstatus(Integer.valueOf(0));
        
        dp.setOrganization(organization);
        
        dp.setRegdept(this.deptLogic.getDeptSeqIdByUserSeqIdAndOrganization(dp.getAskperson(), dp.getOrganization()));
        dp.setCjstatus(Integer.valueOf(0));
        List<JSONObject> list = new ArrayList();
        if ((!YZUtility.isNullorEmpty(order_number)) && (!YZUtility.isNullorEmpty(nodeId))) {
          list = this.logic.findHospitalOrderByOrdernumAndnodeId(order_number, nodeId);
        }
        if (list.size() == 0)
        {
          dp.setOrder_number(order_number);
          dp.setNodeId(nodeId);
        }
        else
        {
          dp.setOrder_number("");
          dp.setNodeId("");
        }
        Map<String, String> map2 = new HashMap();
        String startTime = dp.getStarttime().substring(0, 10);
        map2.put("startTime", startTime + ConstUtil.TIME_START);
        map2.put("endTime", startTime + ConstUtil.TIME_END);
        map2.put("usercode", dp.getUsercode());
        map2.put("doctor", dp.getAskperson());
        




        this.logic.saveSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, dp);
        

        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_HOSPITAL_ORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_HOSPITAL_ORDER, request);
        

        PushUtil.saveTx4NewHosOrder(dp, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkYy.act"})
  public String checkYy(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
      String endtime = formatter.format(formatter.parse(request.getParameter("endtime")));
      String askperson = request.getParameter("askperson");
      JSONObject jobj = new JSONObject();
      int count = this.logic.coutYYPerson(askperson, starttime, endtime, seqId);
      if (count > 0) {
        throw new Exception("该医生在选择的时间段中已存在预约");
      }
      jobj.put("retState", "0");
      
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkYyUser.act"})
  public String checkYyUser(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      String usercode = request.getParameter("usercode");
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
      String endtime = formatter.format(formatter.parse(request.getParameter("endtime")));
      String askperson = request.getParameter("askperson");
      JSONObject jobj = new JSONObject();
      List<JSONObject> list = this.logic.yycxUser(usercode, starttime, endtime);
      if ((list == null) || (list.size() == 0))
      {
        YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
        return null;
      }
      List<JSONObject> checklist = new ArrayList();
      for (JSONObject order : list) {
        if (!order.getString("seq_id").equals(seqId)) {
          checklist.add(order);
        }
      }
      if (checklist.size() > 0)
      {
        String data = "该病人在选择的时间段中已存在预约<br>";
        for (JSONObject order : checklist)
        {
          String tmpStart = order.getString("starttime");
          String tmpEnd = order.getString("endtime");
          if (tmpStart.length() == 19) {
            tmpStart = tmpStart.substring(11, tmpStart.length() - 3);
          }
          if (tmpEnd.length() == 19) {
            tmpEnd = tmpEnd.substring(11, tmpEnd.length() - 3);
          }
          data = data + "预约时间：" + tmpStart + "~" + tmpEnd + "&nbsp;&nbsp;医生：" + this.personLogic.getNameStrBySeqIds(order.getString("askperson")) + "<br>";
        }
        throw new Exception(data);
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkYy2.act"})
  public String checkYy2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
      String askperson = request.getParameter("askperson");
      Map<String, String> map = new HashMap();
      map.put("starttime", starttime);
      map.put("askperson", askperson);
      
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      map.put("organization", organization);
      
      int count = this.logic.checkYy(map);
      
      JSONObject jobj = new JSONObject();
      jobj.put("data", Integer.valueOf(count));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkHz.act"})
  public String checkHz(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      

      String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
      String usercode = request.getParameter("usercode");
      Map<String, String> map = new HashMap();
      map.put("starttime", starttime);
      map.put("usercode", usercode);
      JSONObject jobj = new JSONObject();
      
      List<JSONObject> en = this.logic.checkHz(map);
      String data = "";
      if (en != null)
      {
        jobj.put("nums", Integer.valueOf(en.size()));
        for (JSONObject order : en)
        {
          String tmpStart = order.getString("starttime");
          String tmpEnd = order.getString("endtime");
          if (tmpStart.length() == 19) {
            tmpStart = tmpStart.substring(11, tmpStart.length() - 3);
          }
          if (tmpEnd.length() == 19) {
            tmpEnd = tmpEnd.substring(11, tmpEnd.length() - 3);
          }
          data = data + "预约时间：" + tmpStart + "~" + tmpEnd + "&nbsp;&nbsp;医生：" + this.personLogic.getNameStrBySeqIds(order.getString("askperson")) + "<br>";
        }
      }
      jobj.put("data", data);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
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
      
      KqdsHospitalOrder en = (KqdsHospitalOrder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, seqId);
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
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String seqId = request.getParameter("seqId");
      String delreason = request.getParameter("delreason");
      


      String order_number = request.getParameter("order_number");
      String nodeId = request.getParameter("nodeId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      if (YZUtility.isNullorEmpty(delreason)) {
        throw new Exception("取消预约原因为空或者null");
      }
      KqdsHospitalOrder en = (KqdsHospitalOrder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, seqId);
      en.setIsdelete(Integer.valueOf(ConstUtil.IS_DELETE_1));
      en.setDelreason(delreason);
      en.setDelperson(person.getSeqId());
      this.inforService.cancelTimeHospital(order_number, nodeId);
      this.logic.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, en);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.CANCEL, BcjlUtil.KQDS_HOSPITAL_ORDER, en, en.getUsercode(), TableNameUtil.KQDS_HOSPITAL_ORDER, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectHospitalOrderList.act"})
  public String selectHospitalOrderList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String sortName = request.getParameter("sortName");
    String sortOrder = request.getParameter("sortOrder");
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String querytype = request.getParameter("querytype");
      String searchValue = request.getParameter("searchValue");
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      String organization = ChainUtil.getCurrentOrganization(request);
      
      Map<String, String> map = new HashMap();
      

      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      
      JSONObject json = new JSONObject();
      if (sortName != null)
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
        json = this.logic.selectHospitalOrderList(TableNameUtil.KQDS_HOSPITAL_ORDER, person, querytype, searchValue, visualstaff, organization, map, bp);
      }
      else
      {
        json = this.logic.selectHospitalOrderList(TableNameUtil.KQDS_HOSPITAL_ORDER, person, querytype, searchValue, visualstaff, organization, map, bp);
      }
      YZUtility.RETURN_OBJ(json, response, logger);
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
    String sortName = request.getParameter("sortName");
    String sortOrder = request.getParameter("sortOrder");
    try
    {
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      String createtime = request.getParameter("createtime");
      if (!YZUtility.isNullorEmpty(createtime)) {
        map.put("createtime", createtime);
      }
      String regdept = request.getParameter("regdept");
      if (!YZUtility.isNullorEmpty(regdept)) {
        map.put("regdept", regdept);
      }
      String username = request.getParameter("username");
      if (!YZUtility.isNullorEmpty(username)) {
        map.put("username", username);
      }
      String askperson = request.getParameter("askperson");
      if (!YZUtility.isNullorEmpty(askperson)) {
        map.put("askperson", askperson);
      }
      String firstaskperson = request.getParameter("firstaskperson");
      if (!YZUtility.isNullorEmpty(firstaskperson)) {
        map.put("firstaskperson", firstaskperson);
      }
      String orderstatus = request.getParameter("orderstatus");
      if (!YZUtility.isNullorEmpty(orderstatus)) {
        map.put("orderstatus", orderstatus);
      }
      String starttime = request.getParameter("starttime");
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      String endtime = request.getParameter("endtime");
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      String yyxm = request.getParameter("yyxm");
      if (!YZUtility.isNullorEmpty(yyxm)) {
        map.put("yyxm", yyxm);
      }
      String isdelete = request.getParameter("isdelete");
      if (!YZUtility.isNullorEmpty(isdelete)) {
        map.put("isdelete", isdelete);
      }
      String createuser = request.getParameter("createuser");
      if (!YZUtility.isNullorEmpty(createuser)) {
        map.put("createuser", createuser);
      }
      String cjr = request.getParameter("cjr");
      if (!YZUtility.isNullorEmpty(cjr)) {
        map.put("cjr", cjr);
      }
      map.put("organization", organization);
      
      String visualstaffYyrl = request.getSession().getAttribute("visualstaffYyrl").toString();
      JSONObject json = null;
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      if (sortName != null)
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
        json = this.logic.selectNoPage(TableNameUtil.KQDS_HOSPITAL_ORDER, map, visualstaffYyrl, bp, json);
      }
      else
      {
        json = this.logic.selectNoPage(TableNameUtil.KQDS_HOSPITAL_ORDER, map, visualstaffYyrl, bp, json);
      }
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("预约信息", fieldArr, fieldnameArr, (List)json.get("rows"), response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectYyxxByUsercode.act"})
  public String selectYyxxByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(usercode)) {
        throw new Exception("患者编号不能为空");
      }
      List<JSONObject> list = this.logic.selectYyxxByUsercode(TableNameUtil.KQDS_HOSPITAL_ORDER, usercode);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/showXML.act"})
  public String showXML(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      

      Map<String, String> map = new HashMap();
      String yyxm = request.getParameter("yyxm");
      String doctors = request.getParameter("doctors");
      String regdept = request.getParameter("regdept");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      starttime = sdf.format(new Date(Long.valueOf(starttime).longValue()));
      endtime = sdf.format(new Date(Long.valueOf(endtime).longValue()));
      if ((!YZUtility.isNullorEmpty(starttime)) && (!starttime.equals("null"))) {
        map.put("starttime", starttime);
      }
      if ((!YZUtility.isNullorEmpty(endtime)) && (!endtime.equals("null"))) {
        map.put("endtime", endtime);
      }
      if ((!YZUtility.isNullorEmpty(yyxm)) && (!yyxm.equals("null"))) {
        map.put("orderitemtype", yyxm);
      }
      if ((!YZUtility.isNullorEmpty(doctors)) && (!doctors.equals("null"))) {
        map.put("askperson", doctors);
      }
      if ((!YZUtility.isNullorEmpty(regdept)) && (!regdept.equals("null"))) {
        map.put("regdept", regdept);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      String visualstaffYyrl = request.getSession().getAttribute("visualstaffYyrl").toString();
      List<JSONObject> list = this.logic.selectList(TableNameUtil.KQDS_HOSPITAL_ORDER, map, visualstaffYyrl, organization);
      for (int i = 0; i < list.size(); i++)
      {
        JSONObject hd = (JSONObject)list.get(i);
        String orderitemtype = hd.getString("orderitemtypename");
        String createusername = hd.getString("createusername");
        String askname = hd.getString("askpersonname");
        String text = "<span style='color:black'>患者：" + hd.getString("username") + "<br>医生：" + askname + "<br>项目分类：" + orderitemtype + "</span>";
        
        hd.put("text", text);
        hd.put("createusername", createusername);
        hd.put("id", hd.getString("seq_id"));
        hd.put("start_date", hd.getString("starttime"));
        hd.put("end_date", hd.getString("endtime"));
      }
      JSONObject jobj = new JSONObject();
      jobj.put("result", "ok");
      jobj.put("path", list.toString());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getPersonBydeptId.act"})
  public String getPersonBydeptId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      

      Map<String, String> map = new HashMap();
      
      String deptId = request.getParameter("deptId");
      String seqId = request.getParameter("seqId");
      String yydate = request.getParameter("yydate");
      if (!YZUtility.isNullorEmpty(yydate))
      {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        yydate = sdf.format(new Date(Long.valueOf(yydate).longValue()));
      }
      if (!YZUtility.isNullorEmpty(deptId)) {
        map.put("dept_id", deptId);
      }
      if ((YZUtility.isNullorEmpty(deptId)) && ((YZUtility.isNullorEmpty(seqId)) || (seqId.equals("null"))))
      {
        map.put("sdate", person.getSeqId());
        if (!YZUtility.isNullorEmpty(yydate)) {
          map.put("yydate", yydate);
        }
      }
      if ((!YZUtility.isNullorEmpty(seqId)) && (!seqId.equals("null"))) {
        map.put("seq_id", seqId);
      }
      String visualstaffYyrl = request.getSession().getAttribute("visualstaffYyrl").toString();
      
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      List<String> perSeqIdList = new ArrayList();
      List<JSONObject> list = this.logic.selectPerson(map, organization, visualstaffYyrl);
      String IS_OPEN_PAIBAN_ORDER_FUNC = SysParaUtil.getSysValueByName(request, SysParaUtil.IS_OPEN_PAIBAN_ORDER_FUNC);
      if (IS_OPEN_PAIBAN_ORDER_FUNC.equals("1"))
      {
        Map<String, String> map2 = new HashMap();
        if (!YZUtility.isNullorEmpty(yydate))
        {
          map2.put("starttime", yydate);
        }
        else
        {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          yydate = sdf.format(new Date());
          map2.put("starttime", yydate);
        }
        for (JSONObject perjson : list)
        {
          map2.put("askperson", perjson.getString("seqId"));
          map2.put("organization", organization);
          
          int count = this.logic.checkYy(map2);
          
          JSONObject jobjpb = this.logic.orderstatusString(map2);
          if (jobjpb.isEmpty())
          {
            jobjpb.put("personid", perjson.getString("seqId"));
            jobjpb.put("orderstatus", "");
          }
          String color = "blue";
          if (count == 0)
          {
            perSeqIdList.add(perjson.getString("seqId"));
            if (jobjpb.getString("personid").equals(perjson.getString("seqId"))) {
              color = "red";
            }
          }
          String orderstatus = jobjpb.getString("orderstatus");
          if (!YZUtility.isNullorEmpty(orderstatus)) {
            perjson.put("userName", perjson.getString("userName") + "<span style='color:" + color + ";font-size: 10px;'>【" + orderstatus + "】</span>");
          } else {
            perjson.put("userName", perjson.getString("userName"));
          }
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("result", "ok");
      jobj.put("list", list);
      jobj.put("perSeqIdList", perSeqIdList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountYySml.act"})
  public String selectCountYySml(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String ordertype = request.getParameter("ordertype");
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      
      String[] datearray = YZUtility.getDateArray(starttime, endtime);
      Double[] smlarray = new Double[datearray.length];
      for (int i = 0; i < datearray.length; i++)
      {
        Double sml = this.logic.getSml(datearray[i], ordertype, ChainUtil.getCurrentOrganization(request));
        smlarray[i] = sml;
      }
      JSONObject jobj = new JSONObject();
      jobj.put("datearray", datearray);
      jobj.put("smlarray", smlarray);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountYySmlTable.act"})
  public String selectCountYySmlTable(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String ordertype = request.getParameter("ordertype");
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      List<Object> list = new ArrayList();
      
      String[] datearray = YZUtility.getDateArray(starttime, endtime);
      
      int[] yyarray = new int[datearray.length];
      
      int[] yyarrayqx = new int[datearray.length];
      
      int[] yyarrayreal = new int[datearray.length];
      
      int[] yysmarray = new int[datearray.length];
      
      String[] smlarray = new String[datearray.length];
      int yysum = 0;
      int yysumqx = 0;
      int yysumreal = 0;
      int yysmsum = 0;
      String yysmlsum = "";
      for (int i = 0; i < datearray.length; i++)
      {
        int yynum = this.logic.getCountYy(TableNameUtil.KQDS_HOSPITAL_ORDER, datearray[i], ordertype, "all", ChainUtil.getCurrentOrganization(request));
        yysum += yynum;
        
        int yynumqx = this.logic.getCountYy(TableNameUtil.KQDS_HOSPITAL_ORDER, datearray[i], ordertype, "del", ChainUtil.getCurrentOrganization(request));
        yysumqx += yynumqx;
        
        int yynumreal = yynum - yynumqx;
        yysumreal += yynumreal;
        
        int yysmnum = this.logic.getCountYysm(TableNameUtil.KQDS_HOSPITAL_ORDER, datearray[i], ordertype, ChainUtil.getCurrentOrganization(request));
        yysmsum += yysmnum;
        if (yysmnum == 0)
        {
          smlarray[i] = "0";
        }
        else
        {
          float num = yysmnum / yynumreal * 100.0F;
          DecimalFormat df = new DecimalFormat("0.00");
          smlarray[i] = df.format(num);
        }
        yyarray[i] = yynum;
        yyarrayqx[i] = yynumqx;
        yyarrayreal[i] = yynumreal;
        yysmarray[i] = yysmnum;
      }
      list.add(datearray);
      list.add(yyarray);
      list.add(yyarrayqx);
      list.add(yyarrayreal);
      list.add(yysmarray);
      list.add(smlarray);
      
      list.add(Integer.valueOf(yysum));
      list.add(Integer.valueOf(yysumqx));
      list.add(Integer.valueOf(yysumreal));
      list.add(Integer.valueOf(yysmsum));
      if (yysmsum == 0)
      {
        list.add("0");
      }
      else
      {
        float num = yysmsum / yysumreal * 100.0F;
        DecimalFormat df = new DecimalFormat("0.00");
        list.add(df.format(num));
      }
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
