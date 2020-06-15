package com.kqds.controller.base.costOrder;

import com.hudh.util.HUDHUtil;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsCostorderPriceList;
import com.kqds.entity.base.KqdsHzLabelAssociated;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.costOrderPriceList.KQDS_CostOrderPriceListLogic;
import com.kqds.service.base.hzLabel.KQDS_HzLabelAssociatedLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.memberRecord.KQDS_Member_RecordLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.SysParaUtil;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_CostOrderAct"})
public class KQDS_CostOrderAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_CostOrderAct.class);
  
  @Autowired
  private KQDS_CostOrderLogic logic;
  
  @Autowired
  private KQDS_CostOrder_DetailLogic logicd;
  
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  
  @Autowired
  private KQDS_CostOrderPriceListLogic priceListLogic;
  
  @Autowired
  private KQDS_HzLabelAssociatedLogic hzLabelAssociatedLogic;
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private KQDS_Member_RecordLogic recordLogic;
  
  @RequestMapping({"/toDetail_AddCost.act"})
  public ModelAndView toDetail_AddCost(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String zhekou = request.getParameter("zhekou");
    String costno = request.getParameter("costno");
    String isback = request.getParameter("isback");
    String regno = request.getParameter("regno");
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("zhekou", zhekou);
    mv.addObject("costno", costno);
    mv.addObject("isback", isback);
    mv.addObject("regno", regno);
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/coatOrder/add_cost.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail_AddCostTest.act"})
  public ModelAndView toDetail_AddCostTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String regno = request.getParameter("regno");
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("regno", regno);
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/coatOrder/add_cost4test.jsp");
    return mv;
  }
  
  @RequestMapping({"/toFycxCenter.act"})
  public ModelAndView toFycxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/jdzx/fycx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBbConsumerTrends.act"})
  public ModelAndView toBbConsumerTrends(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/bbzx/bb_consumer_trends.jsp");
    return mv;
  }
  
  @RequestMapping({"/consumerTrends.act"})
  public String consumerTrends(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = request.getParameter("organization");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String czstarttime = request.getParameter("czstarttime");
      String czendtime = request.getParameter("czendtime");
      Map<String, String> map = new HashMap<>();
      map.put("organization", organization);
      map.put("starttime", starttime);
      map.put("endtime", endtime);
      map.put("czstarttime", czstarttime);
      map.put("czendtime", czendtime);
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject object = this.logic.findCostOrderByUserdocument(map, bp);
      YZUtility.RETURN_OBJ(object, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/yzTuiDan.act"})
  public String yzTuiDan(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String costno = request.getParameter("costno");
      String usercode = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(costno))
        throw new Exception("请选择费用单！"); 
      boolean flag = this.logic.ifCanTuiDan(costno);
      boolean flagJF = this.logic.ifCanTuiDanJF(costno, usercode, request);
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      jobj.put("dataJF", Boolean.valueOf(flagJF));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/yzTuiDanYjj.act"})
  public String yzTuiDanYjj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String costno = request.getParameter("costno");
      if (YZUtility.isNullorEmpty(costno))
        throw new Exception("请选择费用单！"); 
      boolean flag = this.logic.ifCanTuiDanYJJ(costno);
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/yzTuiDanSf.act"})
  public String yzTuiDanSf(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      boolean flag = true;
      String costno = request.getParameter("costno");
      if (YZUtility.isNullorEmpty(costno))
        throw new Exception("请选择费用单！"); 
      KqdsCostorder cost = (KqdsCostorder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
      if (cost == null)
        throw new Exception("费用单不存在！"); 
      if (cost.getIsback().intValue() != 1)
        throw new Exception("请选择退单的费用单结账！"); 
      if (!cost.getStatus().equals(ConstUtil.COST_ORDER_STATUS_1))
        throw new Exception("请选择等待结账的费用单！"); 
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/tuiDan.act"})
  public String tuiDan(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String costno = request.getParameter("costno");
      String backremark = request.getParameter("backremark");
      if (YZUtility.isNullorEmpty(costno))
        throw new Exception("请选择费用单！"); 
      if (YZUtility.isNullorEmpty(backremark))
        throw new Exception("请选择退单原因！"); 
      YZPerson person = SessionUtil.getLoginPerson(request);
      String backid = YZUtility.getUUID();
      Map<String, String> map = new HashMap<>();
      map.put("costno", costno);
      int isyjjitem = 0;
      List<KqdsCostorderDetail> detallist = (List<KqdsCostorderDetail>)this.logicd.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
      if (detallist == null || detallist.size() == 0)
        throw new Exception("数据异常：消费项目不存在"); 
      KqdsCostorder cost = (KqdsCostorder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
      Map<String, String> map2 = new HashMap<>();
      map2.put("usercode", cost.getUsercode());
      List<KqdsUserdocument> userlist = (List<KqdsUserdocument>)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
      if (userlist == null)
        throw new Exception("患者不存在！"); 
      KqdsUserdocument user = userlist.get(0);
      for (KqdsCostorderDetail detail : detallist) {
        if (1 == detail.getIsyjjitem().intValue()) {
          isyjjitem = 1;
          break;
        } 
      } 
      if (1 == isyjjitem) {
        this.logic.ifCanTuiDanYJJ(costno);
      } else {
        this.logic.ifCanTuiDan(costno);
        setIntegralMoney(costno, user, person.getSeqId(), request);
      } 
      BigDecimal yjj = BigDecimal.ZERO;
      BigDecimal zs = BigDecimal.ZERO;
      this.logic.tuiDan(detallist, backid, yjj, zs, cost, backremark, person, costno, isyjjitem, map, request);
      JSONObject jobj = new JSONObject();
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  private void setIntegralMoney(String costno, KqdsUserdocument u, String perId, HttpServletRequest request) throws Exception {
    BigDecimal ssmoney = this.userLogic.getSsjeOne(costno);
    BigDecimal integralback = this.userLogic.getJFjeOne(costno);
    BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, "COST_INTEGRAL"));
    u.setTotalpay(u.getTotalpay().subtract(ssmoney));
    this.logic.setIntegralMoney(integralback, costIntegral, ssmoney, u, perId, request);
  }
  
  @RequestMapping({"/getAll4IndexGzl.act"})
  public String getAll4IndexGzl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String isSelectCurrentOrg = request.getParameter("isSelectCurrentOrg");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String organization = null;
      if (!YZUtility.isNullorEmpty(isSelectCurrentOrg) && "1".equals(isSelectCurrentOrg)) {
        organization = ChainUtil.getCurrentOrganization(request);
      } else {
        organization = request.getParameter("organization");
      } 
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      map.put("organization", organization);
      String visualstaff = SessionUtil.getVisualstaff(request);
      map.put("visualstaff", visualstaff);
      List<JSONObject> list = this.logic.selectNoPage4IndexGzl(map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getAllyjz.act"})
  public String getAllyjz(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String querytype = request.getParameter("querytype");
      String regno = request.getParameter("regno");
      String usercode = request.getParameter("usercode");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String cjStatus = request.getParameter("cjStatus");
      String askperson = request.getParameter("askperson");
      String regdept = request.getParameter("regdept");
      String doctor = request.getParameter("doctor");
      String nurse = request.getParameter("nurse");
      String createuser = request.getParameter("createuser");
      String devchannel = request.getParameter("devchannel");
      String nexttype = request.getParameter("nexttype");
      String recesort = request.getParameter("recesort");
      String regsort = request.getParameter("regsort");
      String remark = request.getParameter("remark");
      String isyjjitem = request.getParameter("isyjjitem");
      String isSelectCurrentOrg = request.getParameter("isSelectCurrentOrg");
      String searchValue = request.getParameter("searchValue");
      String organization = null;
      if (!YZUtility.isNullorEmpty(isSelectCurrentOrg) && "1".equals(isSelectCurrentOrg)) {
        organization = ChainUtil.getCurrentOrganization(request);
      } else {
        organization = request.getParameter("organization");
      } 
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(askperson))
        map.put("askperson", askperson); 
      if (!YZUtility.isNullorEmpty(regdept))
        map.put("regdept", regdept); 
      if (!YZUtility.isNullorEmpty(doctor))
        map.put("doctor", doctor); 
      if (!YZUtility.isNullorEmpty(nurse))
        map.put("nurse", nurse); 
      if (!YZUtility.isNullorEmpty(createuser))
        map.put("createuser", createuser); 
      if (!YZUtility.isNullorEmpty(devchannel))
        map.put("devchannel", devchannel); 
      if (!YZUtility.isNullorEmpty(nexttype))
        map.put("nexttype", nexttype); 
      if (!YZUtility.isNullorEmpty(recesort))
        map.put("recesort", recesort); 
      if (!YZUtility.isNullorEmpty(regsort))
        map.put("regsort", regsort); 
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      if (!YZUtility.isNullorEmpty(cjStatus))
        map.put("cjStatus", cjStatus); 
      if (!YZUtility.isNullorEmpty(regno))
        map.put("regno", regno); 
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      String zhcx_seqId = request.getParameter("zhcx_seqId");
      if (!YZUtility.isNullorEmpty(zhcx_seqId))
        map.put("seq_id", zhcx_seqId); 
      String queryinput = request.getParameter("queryinput");
      if (!YZUtility.isNullorEmpty(queryinput))
        map.put("queryinput", queryinput); 
      if (!YZUtility.isNullorEmpty(remark))
        map.put("remark", remark); 
      if (!YZUtility.isNullorEmpty(searchValue))
        map.put("searchValue", searchValue); 
      if (!YZUtility.isNullorEmpty(isyjjitem))
        map.put("isyjjitem", isyjjitem); 
      String ispaging = request.getParameter("ispaging");
      if (!YZUtility.isNullorEmpty(ispaging) && YZUtility.isNullorEmpty(flag)) {
        map.put("ispaging", ispaging);
      } else {
        map.put("ispaging", ConstUtil.PAGE_FLAG_0);
      } 
      String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaff).toString();
      if (flag != null && flag.equals("exportTable")) {
        JSONObject jSONObject = this.logic.selectNoPage(bp, person, map, visualstaff, querytype);
        if (jSONObject != null) {
          ExportBean bean = ExportTable.initExcel4RsWrite("费用查询", fieldArr, fieldnameArr, response, request);
          bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List)jSONObject.get("rows"), "costOrder_selectNoPage");
          ExportTable.writeExcel4DownLoad("费用查询", bean.getWorkbook(), response);
        } 
        return null;
      } 
      JSONObject data = this.logic.selectNoPageYjz(bp, person, map, visualstaff, querytype);
      if (!YZUtility.isNullorEmpty(ispaging)) {
        YZUtility.DEAL_SUCCESS(data, null, response, logger);
      } else {
        YZUtility.RETURN_LIST((List)data.getJSONArray("rows"), response, logger);
      } 
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getAll.act"})
  public String getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String querytype = request.getParameter("querytype");
      String regno = request.getParameter("regno");
      String usercode = request.getParameter("usercode");
      String starttime = request.getParameter("sfstarttime");
      String endtime = request.getParameter("sfendtime");
      if (YZUtility.isNullorEmpty(starttime))
        starttime = request.getParameter("starttime"); 
      if (YZUtility.isNullorEmpty(endtime))
        endtime = request.getParameter("endtime"); 
      String cjStatus = request.getParameter("cjStatus");
      String askperson = request.getParameter("askpersonSearch");
      String regdept = request.getParameter("regdept");
      String doctor = request.getParameter("doctor");
      String nurse = request.getParameter("nurse");
      String createuser = request.getParameter("createuser");
      String devchannel = request.getParameter("devchannel");
      String nexttype = request.getParameter("nexttype");
      String recesort = request.getParameter("recesort");
      String regsort = request.getParameter("regsort");
      String remark = request.getParameter("remark");
      String isyjjitem = request.getParameter("isyjjitem");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      String isSelectCurrentOrg = request.getParameter("isSelectCurrentOrg");
      String searchValue = request.getParameter("searchValue");
      String organization = null;
      if (!YZUtility.isNullorEmpty(isSelectCurrentOrg) && "1".equals(isSelectCurrentOrg)) {
        organization = ChainUtil.getCurrentOrganization(request);
      } else {
        organization = request.getParameter("organization");
      } 
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(askperson))
        map.put("askperson", askperson); 
      if (!YZUtility.isNullorEmpty(regdept))
        map.put("regdept", regdept); 
      if (!YZUtility.isNullorEmpty(doctor))
        map.put("doctor", doctor); 
      if (!YZUtility.isNullorEmpty(nurse))
        map.put("nurse", nurse); 
      if (!YZUtility.isNullorEmpty(createuser))
        map.put("createuser", createuser); 
      if (!YZUtility.isNullorEmpty(devchannel))
        map.put("devchannel", devchannel); 
      if (!YZUtility.isNullorEmpty(nexttype))
        map.put("nexttype", nexttype); 
      if (!YZUtility.isNullorEmpty(recesort))
        map.put("recesort", recesort); 
      if (!YZUtility.isNullorEmpty(regsort))
        map.put("regsort", regsort); 
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      if (!YZUtility.isNullorEmpty(cjStatus))
        map.put("cjStatus", cjStatus); 
      if (!YZUtility.isNullorEmpty(regno))
        map.put("regno", regno); 
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      String zhcx_seqId = request.getParameter("zhcx_seqId");
      if (!YZUtility.isNullorEmpty(zhcx_seqId))
        map.put("seq_id", zhcx_seqId); 
      String queryinput = request.getParameter("queryinput");
      if (!YZUtility.isNullorEmpty(queryinput))
        map.put("queryinput", queryinput); 
      if (!YZUtility.isNullorEmpty(remark))
        map.put("remark", remark); 
      if (!YZUtility.isNullorEmpty(searchValue))
        map.put("searchValue", searchValue); 
      if (!YZUtility.isNullorEmpty(isyjjitem))
        map.put("isyjjitem", isyjjitem); 
      if (!YZUtility.isNullorEmpty(sortName)) {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      } 
      String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaff).toString();
      List<JSONObject> list = new ArrayList<>();
      if (flag != null && flag.equals("exportTable")) {
        JSONObject jSONObject1 = this.logic.selectNoPage(bp, person, map, visualstaff, querytype);
        JSONObject jSONObject2 = this.recordLogic.selectListRefund(bp, person, map, visualstaff, querytype);
        JSONArray jSONArray3 = jSONObject1.getJSONArray("rows");
        JSONArray jSONArray4 = jSONObject2.getJSONArray("rows");
        list.addAll((Collection<? extends JSONObject>)jSONArray3);
        list.addAll((Collection<? extends JSONObject>)jSONArray4);
        if (list.size() > 0) {
          ExportBean bean = ExportTable.initExcel4RsWrite("费用查询", fieldArr, fieldnameArr, response, request);
          bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, list, "costOrder_selectNoPage");
          ExportTable.writeExcel4DownLoad("费用查询", bean.getWorkbook(), response);
        } 
        return null;
      } 
      JSONObject data = this.logic.selectNoPage(bp, person, map, visualstaff, querytype);
      JSONObject recordData = this.recordLogic.selectListRefund(bp, person, map, visualstaff, querytype);
      JSONObject jobjall = new JSONObject();
      int orderTotal = data.getInt("total");
      int memberTotal = recordData.getInt("total");
      JSONArray jSONArray1 = data.getJSONArray("rows");
      JSONArray jSONArray2 = recordData.getJSONArray("rows");
      if (memberTotal <= bp.getOffset() && orderTotal > bp.getOffset()) {
        list.addAll((Collection<? extends JSONObject>)jSONArray1);
      } else if (memberTotal > bp.getOffset() && orderTotal <= bp.getOffset()) {
        list.addAll((Collection<? extends JSONObject>)jSONArray2);
      } else {
        list.addAll((Collection<? extends JSONObject>)jSONArray1);
        list.addAll((Collection<? extends JSONObject>)jSONArray2);
      } 
      jobjall.put("rows", list);
      if (orderTotal > memberTotal) {
        jobjall.put("total", Integer.valueOf(orderTotal));
      } else {
        jobjall.put("total", Integer.valueOf(memberTotal));
      } 
      if (bp.getOffset() == 0 && YZUtility.isNullorEmpty(flag)) {
        BigDecimal totalcost = BigDecimal.ZERO;
        BigDecimal voidmoney = BigDecimal.ZERO;
        BigDecimal shouldmoney = BigDecimal.ZERO;
        BigDecimal actualmoney = BigDecimal.ZERO;
        BigDecimal y2 = BigDecimal.ZERO;
        BigDecimal yjjshiyong = BigDecimal.ZERO;
        BigDecimal yjjchongzhi = BigDecimal.ZERO;
        BigDecimal cmoney = BigDecimal.ZERO;
        if (orderTotal > 0) {
          totalcost = totalcost.add(new BigDecimal(data.getString("totalcost")));
          voidmoney = voidmoney.add(new BigDecimal(data.getString("voidmoney")));
          shouldmoney = shouldmoney.add(new BigDecimal(data.getString("shouldmoney")));
          actualmoney = actualmoney.add(new BigDecimal(data.getString("actualmoney")));
          y2 = y2.add(new BigDecimal(data.getString("y2")));
          yjjshiyong = yjjshiyong.add(new BigDecimal(data.getString("yjjshiyong")));
          yjjchongzhi = yjjchongzhi.add(new BigDecimal(data.getString("yjjchongzhi")));
        } 
        if (memberTotal > 0) {
          cmoney = cmoney.add(new BigDecimal(recordData.getString("cmoney")));
          totalcost = totalcost.add(new BigDecimal(recordData.getString("cmoney")));
          shouldmoney = shouldmoney.add(new BigDecimal(recordData.getString("cmoney")));
          actualmoney = actualmoney.add(new BigDecimal(recordData.getString("cmoney")));
        } 
        jobjall.put("totalcost", totalcost);
        jobjall.put("voidmoney", voidmoney);
        jobjall.put("shouldmoney", shouldmoney);
        jobjall.put("actualmoney", actualmoney);
        jobjall.put("y2", y2);
        jobjall.put("yjjshiyong", yjjshiyong);
        jobjall.put("yjjchongzhi", yjjchongzhi);
        jobjall.put("cmoney", cmoney);
      } 
      YZUtility.DEAL_SUCCESS(jobjall, null, response, logger);
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
      BeanUtils.populate(bp, request.getParameterMap());
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String askperson = request.getParameter("askperson");
      String createuser = request.getParameter("createuser");
      String devchannel = request.getParameter("devchannel");
      String nexttype = request.getParameter("nexttype");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(askperson))
        map.put("askperson", askperson); 
      if (!YZUtility.isNullorEmpty(createuser))
        map.put("createuser", createuser); 
      if (!YZUtility.isNullorEmpty(devchannel))
        map.put("devchannel", devchannel); 
      if (!YZUtility.isNullorEmpty(nexttype))
        map.put("nexttype", nexttype); 
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_END;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      String zhcx_seqId = request.getParameter("zhcx_seqId");
      if (!YZUtility.isNullorEmpty(zhcx_seqId))
        map.put("seq_id", zhcx_seqId); 
      String queryinput = request.getParameter("queryinput");
      if (!YZUtility.isNullorEmpty(queryinput))
        map.put("queryinput", queryinput); 
      if (!YZUtility.isNullorEmpty(sortName)) {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      } 
      String visualstaff = SessionUtil.getVisualstaff(request);
      map.put("visualstaff", visualstaff);
      map.put("organization", ChainUtil.getOrganizationFromUrl(request));
      JSONObject data = this.logic.qfSearch(bp, person, map, request);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("欠费查询", fieldArr, fieldnameArr, (List)data.getJSONArray("rows"), response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String organization = ChainUtil.getCurrentOrganization(request);
      List<YZPerson> listPserson = this.personLogic.getPersonListByDeptIds("015d89d6-7b32-47ec-9557-233407c7fc71,72d1324f-22a2-41a7-9739-8b64c50e7b97,3b47a915-977b-4799-acf6-540d525722f4,b4f9dc9e-d2e0-44e1-ba37-eecaddcbf93d,4b88b74c-9373-4b5f-9d53-3c115de7a7e4,d915e83c-862e-40eb-a4a0-792e34db701e", "0", organization);
      KqdsCostorder dp = new KqdsCostorder();
      BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String repairdoctor = request.getParameter("repair");
      String askperson = request.getParameter("askperson");
      String deleteitem = request.getParameter("deleteitem");
      String uuid = this.logic.insertOrUpdate(dp, person, askperson, deleteitem, seqId, request, listPserson, repairdoctor);
      JSONObject jobj = new JSONObject();
      jobj.put("data", uuid);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getQk.act"})
  public String getQk(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    Map<String, String> map = new HashMap<>();
    map.put("usercode", usercode);
    map.put("isqfreal", (new StringBuilder(String.valueOf(ConstUtil.QF_STATUS_1))).toString());
    List<KqdsCostorderDetail> en = (List<KqdsCostorderDetail>)this.logic.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
    YZUtility.RETURN_LIST(en, response, logger);
    return null;
  }
  
  @RequestMapping({"/selectNopay.act"})
  public String selectNopay(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      Map<String, String> map = new HashMap<>();
      map.put("usercode", usercode);
      map.put("status", ConstUtil.COST_ORDER_STATUS_1);
      List<KqdsCostorder> en = (List<KqdsCostorder>)this.logic.loadList(TableNameUtil.KQDS_COSTORDER, map);
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      this.logic.deleteObj(seqId, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      KqdsCostorder en = (KqdsCostorder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, seqId);
      if (en == null)
        throw new Exception("数据不存在"); 
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectByregno.act"})
  public String selectByregno(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String regno = request.getParameter("regno");
      Map<String, String> map = new HashMap<>();
      map.put("regno", regno);
      List<KqdsCostorder> en = (List<KqdsCostorder>)this.logic.loadList(TableNameUtil.KQDS_COSTORDER, map);
      List<KqdsCostorder> en2 = new ArrayList<>();
      for (KqdsCostorder order : en) {
        if (ConstUtil.COST_ORDER_STATUS_1.equals(order.getStatus()) || ConstUtil.COST_ORDER_STATUS_2.equals(order.getStatus()))
          en2.add(order); 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("data", en2);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getListByStatus.act"})
  public String getListByStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String status = request.getParameter("status");
      String querytype = request.getParameter("querytype");
      String searchValue = request.getParameter("searchValue");
      Map<String, String> map = new HashMap<>();
      map.put("status", status);
      if (!YZUtility.isNullorEmpty(searchValue))
        map.put("searchValue", searchValue); 
      String visualstaff = SessionUtil.getVisualstaff(request);
      String organization = ChainUtil.getCurrentOrganization(request);
      map.put("organization", organization);
      List<JSONObject> list = this.logic.getListByStatus(map, person, querytype, visualstaff, request);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getByRegno.act"})
  public String getByRegno(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String regno = request.getParameter("regno");
      Map<String, String> map = new HashMap<>();
      map.put("regno", regno);
      List<KqdsCostorder> en = (List<KqdsCostorder>)this.logic.loadList(TableNameUtil.KQDS_COSTORDER, map);
      JSONObject jobj = new JSONObject();
      jobj.put("data", en.get(0));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getByRegnoNopage.act"})
  public String getByRegnoNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String regno = request.getParameter("regno");
      String usercode = request.getParameter("usercode");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String cjStatus = request.getParameter("cjStatus");
      String status = request.getParameter("status");
      String qf = request.getParameter("qf");
      String searchValue = request.getParameter("searchValue");
      String istk = request.getParameter("istk");
      String access = request.getParameter("access");
      String isyjjitem = request.getParameter("isyjjitem");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      if (!YZUtility.isNullorEmpty(cjStatus))
        map.put("cjStatus", cjStatus); 
      if (!YZUtility.isNullorEmpty(status))
        map.put("status", status); 
      if (!YZUtility.isNullorEmpty(qf))
        if (qf.equals("1")) {
          map.put("noqf", qf);
        } else {
          map.put("isqf", qf);
        }  
      if (!YZUtility.isNullorEmpty(regno))
        map.put("regno", regno); 
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      String zhcx_seqId = request.getParameter("zhcx_seqId");
      if (!YZUtility.isNullorEmpty(zhcx_seqId))
        map.put("seq_id", zhcx_seqId); 
      String queryinput = request.getParameter("queryinput");
      if (!YZUtility.isNullorEmpty(queryinput))
        map.put("queryinput", queryinput); 
      if (!YZUtility.isNullorEmpty(searchValue))
        map.put("searchValue", searchValue); 
      if (!YZUtility.isNullorEmpty(istk))
        if (istk.equals(Integer.valueOf(ConstUtil.COST_IS_TK_0))) {
          map.put("istk", "and " + SQLUtil.convertDecimal("cost.shouldmoney", 18, 2) + " >=0");
        } else if (istk.equals(Integer.valueOf(ConstUtil.COST_IS_TK_1))) {
          map.put("istk", " and " + SQLUtil.convertDecimal("cost.shouldmoney", 18, 2) + " <0");
        }  
      if (!YZUtility.isNullorEmpty(access) && !access.equals("1"))
        map.put("access", access); 
      if (!YZUtility.isNullorEmpty(isyjjitem))
        map.put("isyjjitem", isyjjitem); 
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> list = this.logic.selectWithPageNopage(TableNameUtil.KQDS_COSTORDER, map, visualstaff);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/editPaymoney.act"})
  public String editPaymoney(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String costno = request.getParameter("costno");
      String detailid = request.getParameter("detailid");
      String oldPaymoney = YZUtility.isNullorEmpty(request.getParameter("oldpaymoney")) ? "0" : request.getParameter("oldpaymoney");
      String newPaymoney = YZUtility.isNullorEmpty(request.getParameter("paymoney")) ? "0" : request.getParameter("paymoney");
      String newArrearmoney = YZUtility.isNullorEmpty(request.getParameter("arrearmoney")) ? "0" : request.getParameter("arrearmoney");
      String remark = request.getParameter("remark");
      KqdsCostorder cost = (KqdsCostorder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
      BigDecimal newActualMoney = KqdsBigDecimal.sub(cost.getActualmoney(), new BigDecimal(oldPaymoney));
      newActualMoney = KqdsBigDecimal.add(newActualMoney, new BigDecimal(newPaymoney));
      cost.setActualmoney(newActualMoney);
      BigDecimal arrearmoneyAll = KqdsBigDecimal.sub(cost.getShouldmoney(), newActualMoney);
      cost.setArrearmoney(arrearmoneyAll);
      KqdsCostorderDetail detail = (KqdsCostorderDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detailid);
      detail.setRemark(remark);
      detail.setArrearmoney(new BigDecimal(newArrearmoney));
      detail.setPaymoney(new BigDecimal(newPaymoney));
      BigDecimal olddetailY2Str = detail.getY2();
      BigDecimal itemQF = this.logic.dealCostItem(detail, request);
      BigDecimal oldorderY2 = KqdsBigDecimal.sub(cost.getY2(), olddetailY2Str);
      cost.setY2(KqdsBigDecimal.add(oldorderY2, itemQF));
      this.logic.editPaymoney(cost, detail);
      BcjlUtil.LogBcjlWithUserCodeAndRegno(BcjlUtil.MODIFY_MONEY, BcjlUtil.KQDS_COSTORDER_DETAIL, detail, detail.getUsercode(), detail.getRegno(), true, 
          TableNameUtil.KQDS_COSTORDER_DETAIL, request);
      BcjlUtil.LogBcjlWithUserCodeAndRegno(BcjlUtil.MODIFY_MONEY, BcjlUtil.KQDS_COSTORDER, cost, cost.getUsercode(), cost.getRegno(), true, TableNameUtil.KQDS_COSTORDER, 
          request);
      JSONObject jobj = new JSONObject();
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/savePriceList.act"})
  public String savePriceList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String parentid = request.getParameter("parentid");
      String seqid = request.getParameter("seqid");
      String priveListDetails = request.getParameter("paramDetail");
      String userName = request.getParameter("userName");
      String usercode = request.getParameter("usercode");
      String status = request.getParameter("status");
      String remark = request.getParameter("remark");
      KqdsHzLabelAssociated kqdsHzLabelAssociated = new KqdsHzLabelAssociated();
      KqdsHzLabelAssociated kqdsHzLabelAssociated1 = new KqdsHzLabelAssociated();
      Map<String, String> map = new HashMap<>();
      map.put("usercode", usercode);
      map.put("status", status);
      String hzLabelAssciatedSeqId = this.hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map);
      if (!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)) {
        kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
        kqdsHzLabelAssociated1.setUpdateTime(YZUtility.getCurDateTimeStr());
        kqdsHzLabelAssociated1.setModifier(person.getSeqId());
        int i = this.hzLabelAssociatedLogic.updateKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
        KqdsCostorderPriceList kqdsCostorderPriceList = new KqdsCostorderPriceList();
        kqdsCostorderPriceList.setModifier(person.getSeqId());
        kqdsCostorderPriceList.setUpdateTime(YZUtility.getCurDateTimeStr());
        kqdsCostorderPriceList.setParentId(hzLabelAssciatedSeqId);
        int k = this.priceListLogic.updatePriceList(kqdsCostorderPriceList);
      } 
      hzLabelAssciatedSeqId = YZUtility.getUUID();
      kqdsHzLabelAssociated.setSeqId(hzLabelAssciatedSeqId);
      kqdsHzLabelAssociated.setLabeId(seqid);
      kqdsHzLabelAssociated.setUsercode(usercode);
      kqdsHzLabelAssociated.setUserName(userName);
      kqdsHzLabelAssociated.setCreateTime(YZUtility.getCurDateTimeStr());
      kqdsHzLabelAssociated.setCreateUser(person.getSeqId());
      kqdsHzLabelAssociated.setRemark(remark);
      kqdsHzLabelAssociated.setStatus(Integer.valueOf(status).intValue());
      kqdsHzLabelAssociated.setIsdelete(0);
      int j = this.hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated);
      if (j > 0) {
        priveListDetails = URLDecoder.decode(priveListDetails, "UTF-8");
        List<KqdsCostorderPriceList> list = HUDHUtil.parseJsonToObjectList(priveListDetails, KqdsCostorderPriceList.class);
        for (KqdsCostorderPriceList kqdsCostorderPriceList : list) {
          kqdsCostorderPriceList.setCreateuser(person.getSeqId());
          kqdsCostorderPriceList.setCreatetime(YZUtility.getCurDateTimeStr());
          kqdsCostorderPriceList.setSeqId(YZUtility.getUUID());
          kqdsCostorderPriceList.setParentId(hzLabelAssciatedSeqId);
          kqdsCostorderPriceList.setIsdelete(0);
          kqdsCostorderPriceList.setUsercode(usercode);
        } 
        this.priceListLogic.insertPriceList(list);
        JSONObject jobj = new JSONObject();
        YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPriceListByLabeId.act"})
  public ModelAndView selectPriceListByLabeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String parentId = request.getParameter("parentId");
    String parentName = request.getParameter("parentName");
    String labeId = request.getParameter("labeId");
    String status = request.getParameter("status");
    String frameSelfindex = request.getParameter("frameSelfindex");
    String labelLayerIndex = request.getParameter("labelLayerIndex");
    ModelAndView mv = new ModelAndView();
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(usercode))
      map.put("usercode", usercode); 
    if (!YZUtility.isNullorEmpty(labeId))
      map.put("labeId", labeId); 
    if (!YZUtility.isNullorEmpty(status))
      map.put("status", status); 
    if (!YZUtility.isNullorEmpty(usercode) && !YZUtility.isNullorEmpty(labeId)) {
      List<JSONObject> list = this.hzLabelAssociatedLogic.selectPriceListByLabeId(map);
      JSONArray jsonArray = JSONArray.fromObject(list);
      if (list != null && list.size() > 0)
        mv.addObject("list", jsonArray); 
    } else {
      mv.addObject("list", null);
    } 
    mv.addObject("parentId", parentId);
    mv.addObject("parentName", parentName);
    mv.addObject("status", status);
    mv.addObject("frameSelfindex", frameSelfindex);
    mv.addObject("labelLayerIndex", labelLayerIndex);
    mv.setViewName("/kqdsFront/coatOrder/add_cost_price.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectPriceListByLabeIdTwo.act"})
  public ModelAndView selectPriceListByLabeIdTwo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String parentId = request.getParameter("parentId");
    String parentName = request.getParameter("parentName");
    String labeId = request.getParameter("labeId");
    String status = request.getParameter("status");
    String labelLayerIndex = request.getParameter("labelLayerIndex");
    ModelAndView mv = new ModelAndView();
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(usercode))
      map.put("usercode", usercode); 
    if (!YZUtility.isNullorEmpty(labeId))
      map.put("labeId", labeId); 
    if (!YZUtility.isNullorEmpty(status))
      map.put("status", status); 
    if (!YZUtility.isNullorEmpty(usercode) && !YZUtility.isNullorEmpty(labeId)) {
      List<JSONObject> list = this.hzLabelAssociatedLogic.selectPriceListByLabeId(map);
      JSONArray jsonArray = JSONArray.fromObject(list);
      if (list != null && list.size() > 0)
        mv.addObject("list", jsonArray); 
    } else {
      mv.addObject("list", null);
    } 
    mv.addObject("parentId", parentId);
    mv.addObject("parentName", parentName);
    mv.addObject("status", status);
    mv.addObject("labelLayerIndex", labelLayerIndex);
    mv.setViewName("/kqdsFront/coatOrder/add_cost_price2.jsp");
    return mv;
  }
}
