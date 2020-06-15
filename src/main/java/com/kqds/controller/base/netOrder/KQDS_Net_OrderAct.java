package com.kqds.controller.base.netOrder;

import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsNetOrderRecord;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.extension.KQDS_ExtensionLogic;
import com.kqds.service.base.hospitalOrder.KQDS_Hospital_OrderLogic;
import com.kqds.service.base.netOrder.KQDS_Net_OrderLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.base.room.KQDS_RoomLogic;
import com.kqds.service.base.visit.KQDS_VisitLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_DetailLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping({"KQDS_Net_OrderAct"})
public class KQDS_Net_OrderAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Net_OrderAct.class);
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private KQDS_Net_OrderLogic logic;
  
  @Autowired
  private KQDS_Hospital_OrderLogic hlogic;
  
  @Autowired
  private KQDS_REGLogic rlogic;
  
  @Autowired
  private KQDS_CostOrderLogic clogic;
  
  @Autowired
  private Kqds_PayCostLogic plogic;
  
  @Autowired
  private KQDS_VisitLogic vlogic;
  
  @Autowired
  private KQDS_RoomLogic roomlogic;
  
  @Autowired
  private KQDS_ExtensionLogic tglogic;
  
  @Autowired
  private KQDS_Ck_Goods_DetailLogic cklogic;
  
  @RequestMapping({"/toWdyySearch.act"})
  public ModelAndView toWdyySearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.addObject("isyx", Integer.valueOf(1));
    mv.setViewName("/kqdsFront/index/kfzx/wdyySearch.jsp");
    return mv;
  }
  
  @RequestMapping({"/toWdyySearch2.act"})
  public ModelAndView toWdyySearch2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.addObject("isyx", Integer.valueOf(2));
    mv.setViewName("/kqdsFront/index/kfzx/wdyySearch.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNetorderInsert.act"})
  public ModelAndView toNetorderInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/yyzx/netorder_insert.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNetorderSearchWd.act"})
  public ModelAndView toNetorderSearchWd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.setViewName("/kqdsFront/yyzx/netOrderSearchWd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNetorderSearchMz.act"})
  public ModelAndView toNetorderSearchMz(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.setViewName("/kqdsFront/yyzx/netOrderSearchMz.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNetorderSearch.act"})
  public ModelAndView toNetorderSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.setViewName("/kqdsFront/yyzx/netOrderSearch.jsp");
    return mv;
  }
  
  @RequestMapping({"/toYyzxSearchMz.act"})
  public ModelAndView toYyzxSearchMz(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.setViewName("/kqdsFront/yyzx/yyzxSearchMz.jsp");
    return mv;
  }
  
  @RequestMapping({"/toYyzxSearchWd.act"})
  public ModelAndView toYyzxSearchWd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.setViewName("/kqdsFront/yyzx/yyzxSearchWd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNetorderInsertOrupdate.act"})
  public ModelAndView toNetorderInsertOrupdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String source = request.getParameter("source");
    ModelAndView mv = new ModelAndView();
    mv.addObject("source", source);
    mv.setViewName("/kqdsFront/yyzx/netorder_insertOrUpdate.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNetorder2.act"})
  public ModelAndView toNetorder2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/yyzx/netorder.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNetorderList.act"})
  public ModelAndView toNetorderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/yyzx/netorderlist.jsp");
    return mv;
  }
  
  @RequestMapping({"/toYyzx2.act"})
  public ModelAndView toYyzx2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    String order_number = request.getParameter("order_number");
    String nodeId = request.getParameter("nodeId");
    String yyxm = request.getParameter("yyxm");
    String doctors = request.getParameter("doctors");
    String regdept = request.getParameter("regdept");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.addObject("yyxm", yyxm);
    mv.addObject("doctors", doctors);
    mv.addObject("regdept", regdept);
    mv.addObject("organization", organization);
    mv.addObject("order_number", order_number);
    mv.addObject("nodeId", nodeId);
    mv.setViewName("/kqdsFront/yyzx/yyzx2.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNetorder.act"})
  public ModelAndView toNetorder(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/index/center/netorder.jsp");
    return mv;
  }
  
  @RequestMapping({"/toYyzxSearch.act"})
  public ModelAndView toYyzxSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/yyzx/yyzxSearch.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectFirstByUsercode.act"})
  public String selectFirstByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      JSONObject wdinfo = this.logic.selectFirstByUsercode(usercode);
      JSONObject jobj = new JSONObject();
      jobj.put("wdinfo", wdinfo);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/checkNetOrderCount.act"})
  public String checkNetOrderCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      String ordertime = request.getParameter("ordertime");
      String seqId = request.getParameter("seqId");
      int count = 0;
      if (!YZUtility.isNullorEmpty(usercode) && !YZUtility.isNullorEmpty(ordertime)) {
        ordertime = ordertime.substring(0, 10);
        List<JSONObject> list = this.logic.checkNetOrderCount(seqId, usercode, ordertime);
        if (list.size() >= 1)
          throw new Exception("同一个患者当天只能网电预约一次，患者编号：" + usercode + "，预约日期：" + ordertime + "。"); 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("orderCount", Integer.valueOf(count));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsNetOrder dp = new KqdsNetOrder();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
        organization = ChainUtil.getCurrentOrganization(request); 
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, dp);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_NET_ORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_NET_ORDER, request);
      } else {
        String uuid = YZUtility.getUUID();
        seqId = uuid;
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getOrganizationFromUrl(request));
        dp.setDoorstatus(Integer.valueOf(0));
        dp.setCjstatus(Integer.valueOf(0));
        Map<String, String> map1 = new HashMap<>();
        map1.put("usercode", dp.getUsercode());
        List<KqdsUserdocument> user = (List<KqdsUserdocument>)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map1);
        dp.setAcceptdate(((KqdsUserdocument)user.get(0)).getCreatetime());
        dp.setOrganization(organization);
        this.logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, dp);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_NET_ORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_NET_ORDER, request);
      } 
      if (!YZUtility.isNullorEmpty(dp.getZxremark())) {
        KqdsNetOrderRecord r = new KqdsNetOrderRecord();
        r.setSeqId(YZUtility.getUUID());
        r.setCreatetime(YZUtility.getCurDateTimeStr());
        r.setCreateuser(person.getSeqId());
        r.setContent(dp.getZxremark());
        r.setUsercode(dp.getUsercode());
        r.setUsername(dp.getUsername());
        r.setNerorderseqid(seqId);
        r.setOrganization(organization);
        this.logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER_RECORD, r);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      KqdsNetOrder en = (KqdsNetOrder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_NET_ORDER, seqId);
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
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String usercode = request.getParameter("usercode");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      String username = request.getParameter("username");
      if (!YZUtility.isNullorEmpty(username))
        map.put("username", username); 
      String organization = request.getParameter("organization");
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      String regdept = request.getParameter("regdept");
      if (!YZUtility.isNullorEmpty(regdept))
        map.put("regdept", regdept); 
      String askperson = request.getParameter("askperson");
      if (!YZUtility.isNullorEmpty(askperson))
        map.put("askperson", askperson); 
      String orderstatus = request.getParameter("doorstatus");
      if (!YZUtility.isNullorEmpty(orderstatus))
        map.put("doorstatus", orderstatus); 
      String starttime = request.getParameter("starttime");
      if (!YZUtility.isNullorEmpty(starttime))
        map.put("starttime", starttime); 
      String endtime = request.getParameter("endtime");
      if (!YZUtility.isNullorEmpty(endtime))
        map.put("endtime", endtime); 
      String createtime = request.getParameter("createtime");
      if (!YZUtility.isNullorEmpty(createtime))
        map.put("createtime", createtime); 
      JSONObject jobj = this.logic.selectWithPage(TableNameUtil.KQDS_NET_ORDER, bp, map);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectNoPage.act"})
  public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      String organization = request.getParameter("organization");
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      String regdept = request.getParameter("regdept");
      if (!YZUtility.isNullorEmpty(regdept))
        map.put("regdept", regdept); 
      String askperson = request.getParameter("askperson");
      if (!YZUtility.isNullorEmpty(askperson))
        map.put("askperson", askperson); 
      String orderstatus = request.getParameter("doorstatus");
      if (!YZUtility.isNullorEmpty(orderstatus))
        map.put("doorstatus", orderstatus); 
      String starttime = request.getParameter("starttime");
      if (!YZUtility.isNullorEmpty(starttime))
        map.put("starttime", starttime); 
      String endtime = request.getParameter("endtime");
      if (!YZUtility.isNullorEmpty(endtime))
        map.put("endtime", endtime); 
      String createtime = request.getParameter("createtime");
      if (!YZUtility.isNullorEmpty(createtime))
        map.put("createtime", createtime); 
      List<JSONObject> list = this.logic.selectWithNopage(TableNameUtil.KQDS_NET_ORDER, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectNetOrderList.act"})
  public String selectNetOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String querytype = request.getParameter("querytype");
      String searchValue = request.getParameter("searchValue");
      String visualstaff = SessionUtil.getVisualstaff(request);
      String organization = ChainUtil.getCurrentOrganization(request);
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(querytype))
        map.put("querytype", querytype); 
      if (!YZUtility.isNullorEmpty(searchValue))
        map.put("searchValue", searchValue); 
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(visualstaff))
        map.put("visualstaff", visualstaff); 
      if (!YZUtility.isNullorEmpty(sortName)) {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      } 
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject list = this.logic.selectNetOrderList(TableNameUtil.KQDS_NET_ORDER, person, map, bp);
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectListNoPage.act"})
  public String selectListNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      List<JSONObject> list = this.logic.selectListNoPage(TableNameUtil.KQDS_NET_ORDER, usercode);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectNoPageYYZX.act"})
  public String selectNoPageYYZX(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String sortName = request.getParameter("sortName");
    String sortOrder = request.getParameter("sortOrder");
    try {
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      String usercode = request.getParameter("usercode");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      String username = request.getParameter("username");
      if (!YZUtility.isNullorEmpty(username))
        map.put("username", username); 
      String regdept = request.getParameter("regdept");
      if (!YZUtility.isNullorEmpty(regdept))
        map.put("regdept", regdept); 
      String askperson = request.getParameter("askperson");
      if (!YZUtility.isNullorEmpty(askperson))
        map.put("askperson", askperson); 
      String orderstatus = request.getParameter("doorstatus");
      if (!YZUtility.isNullorEmpty(orderstatus))
        map.put("doorstatus", orderstatus); 
      String starttime = request.getParameter("starttime");
      if (!YZUtility.isNullorEmpty(starttime)) {
        if (starttime.length() == 19)
          starttime = starttime.substring(0, starttime.length() - 3); 
        if (starttime.length() == 10)
          starttime = String.valueOf(starttime) + ConstUtil.HOUR_START; 
        map.put("starttime", starttime);
      } 
      String endtime = request.getParameter("endtime");
      if (!YZUtility.isNullorEmpty(endtime)) {
        if (endtime.length() == 19)
          endtime = endtime.substring(0, endtime.length() - 3); 
        if (endtime.length() == 10)
          endtime = String.valueOf(endtime) + ConstUtil.HOUR_END; 
        map.put("endtime", endtime);
      } 
      String createtime = request.getParameter("createtime");
      if (!YZUtility.isNullorEmpty(createtime))
        map.put("createtime", createtime); 
      String xiangmu = request.getParameter("xiangmu");
      if (!YZUtility.isNullorEmpty(xiangmu))
        map.put("xiangmu", xiangmu); 
      String createuser = request.getParameter("createuser");
      if (!YZUtility.isNullorEmpty(createuser))
        map.put("createuser", createuser); 
      String isdelete = request.getParameter("isdelete");
      if (!YZUtility.isNullorEmpty(isdelete))
        map.put("isdelete", isdelete); 
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject json = new JSONObject();
      String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaffYyrl).toString();
      if (sortName != null) {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
        json = this.logic.selectNoPageYyzx(TableNameUtil.KQDS_NET_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), bp, json);
      } else {
        json = this.logic.selectNoPageYyzx(TableNameUtil.KQDS_NET_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), bp, json);
      } 
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("预约信息", fieldArr, fieldnameArr, (List)json.get("rows"), response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectNoPageYyzxNet.act"})
  public String selectNoPageYyzxNet(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      String usercode = request.getParameter("usercode");
      YZPerson person = SessionUtil.getLoginPerson(request);
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      String username = request.getParameter("username");
      if (!YZUtility.isNullorEmpty(username))
        map.put("username", username); 
      String regdept = request.getParameter("regdept");
      if (!YZUtility.isNullorEmpty(regdept))
        map.put("regdept", regdept); 
      String askperson = request.getParameter("askperson");
      if (!YZUtility.isNullorEmpty(askperson))
        map.put("askperson", askperson); 
      String doorstatus = request.getParameter("doorstatus");
      if (!YZUtility.isNullorEmpty(doorstatus))
        map.put("doorstatus", doorstatus); 
      String starttime = request.getParameter("starttime");
      if (!YZUtility.isNullorEmpty(starttime)) {
        if (starttime.length() == 19)
          starttime = starttime.substring(0, starttime.length() - 3); 
        if (starttime.length() == 10)
          starttime = String.valueOf(starttime) + ConstUtil.HOUR_START; 
        map.put("starttime", starttime);
      } 
      String endtime = request.getParameter("endtime");
      if (!YZUtility.isNullorEmpty(endtime)) {
        if (endtime.length() == 19)
          endtime = endtime.substring(0, endtime.length() - 3); 
        if (endtime.length() == 10)
          endtime = String.valueOf(endtime) + ConstUtil.HOUR_END; 
        map.put("endtime", endtime);
      } 
      String createtime = request.getParameter("createtime");
      if (!YZUtility.isNullorEmpty(createtime))
        map.put("createtime", createtime); 
      String xiangmu = request.getParameter("xiangmu");
      if (!YZUtility.isNullorEmpty(xiangmu))
        map.put("xiangmu", xiangmu); 
      String createuser = request.getParameter("createuser");
      if (!YZUtility.isNullorEmpty(createuser))
        map.put("createuser", createuser); 
      String isdelete = request.getParameter("isdelete");
      if (!YZUtility.isNullorEmpty(isdelete))
        map.put("isdelete", isdelete); 
      String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaffYyrl).toString();
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject list = new JSONObject();
      if (sortName != null) {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
        list = this.logic.selectNoPageYyzxNet(TableNameUtil.KQDS_NET_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), person, bp);
      } else {
        list = this.logic.selectNoPageYyzxNet(TableNameUtil.KQDS_NET_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), person, bp);
      } 
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("预约信息", fieldArr, fieldnameArr, (List)list.get("rows"), response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectNoPageYYZXWd.act"})
  public String selectNoPageYYZXWd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      String usercode = request.getParameter("usercode");
      String datetype = request.getParameter("datetype");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      String username = request.getParameter("username");
      if (!YZUtility.isNullorEmpty(username))
        map.put("username", username); 
      String organization = request.getParameter("organization");
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      String regdept = request.getParameter("regdept");
      if (!YZUtility.isNullorEmpty(regdept))
        map.put("regdept", regdept); 
      String askperson = request.getParameter("askperson");
      if (!YZUtility.isNullorEmpty(askperson))
        map.put("askperson", askperson); 
      String orderstatus = request.getParameter("doorstatus");
      if (!YZUtility.isNullorEmpty(orderstatus))
        map.put("doorstatus", orderstatus); 
      String starttime = request.getParameter("starttime");
      if (!YZUtility.isNullorEmpty(starttime)) {
        if (starttime.length() == 19)
          starttime = starttime.substring(0, starttime.length() - 3); 
        if (starttime.length() == 10)
          starttime = String.valueOf(starttime) + ConstUtil.HOUR_START; 
        map.put("starttime", starttime);
      } 
      String endtime = request.getParameter("endtime");
      if (!YZUtility.isNullorEmpty(endtime)) {
        if (endtime.length() == 19)
          endtime = endtime.substring(0, endtime.length() - 3); 
        if (endtime.length() == 10)
          endtime = String.valueOf(endtime) + ConstUtil.HOUR_END; 
        map.put("endtime", endtime);
      } 
      String createtime = request.getParameter("createtime");
      if (!YZUtility.isNullorEmpty(createtime))
        map.put("createtime", createtime); 
      String xiangmu = request.getParameter("xiangmu");
      if (!YZUtility.isNullorEmpty(xiangmu))
        map.put("xiangmu", xiangmu); 
      String createuser = request.getParameter("createuser");
      if (!YZUtility.isNullorEmpty(createuser))
        map.put("createuser", createuser); 
      if (!YZUtility.isNullorEmpty(sortName))
        map.put("sortName", sortName); 
      if (!YZUtility.isNullorEmpty(sortOrder))
        map.put("sortOrder", sortOrder); 
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String visualstaff = SessionUtil.getVisualstaff(request);
      JSONObject list = this.logic.selectNoPageYyzxWd(TableNameUtil.KQDS_NET_ORDER, map, datetype, ChainUtil.getOrganizationFromUrlCanNull(request), visualstaff, bp);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("预约信息", fieldArr, fieldnameArr, (List)list.getJSONArray("rows"), response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getCountIndex.act"})
  public String getCountIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String querytype = request.getParameter("querytype");
      String searchValue = request.getParameter("searchValue");
      String visualstaff = SessionUtil.getVisualstaff(request);
      String organization = ChainUtil.getCurrentOrganization(request);
      Map<String, String> map = new HashMap<>();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String time = df.format(new Date());
      map.put("starttime", String.valueOf(time) + ConstUtil.TIME_START);
      map.put("endtime", String.valueOf(time) + ConstUtil.TIME_END);
      map.put("organization", person.getOrganization());
      int vdataCount = this.vlogic.getCountIndex(TableNameUtil.KQDS_VISIT, person, querytype, searchValue, visualstaff, organization);
      int mdataCount = this.hlogic.getCountIndex(querytype, searchValue, visualstaff, organization);
      int ssdataCount = this.roomlogic.getCountIndex(TableNameUtil.KQDS_ROOM, person, querytype, searchValue, visualstaff, organization);
      int tgjhCount = this.tglogic.getCountIndex(visualstaff, organization);
      int ckbjCount = this.cklogic.getCountIndex(organization);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date d = new Date();
      String starttime = String.valueOf(sdf.format(d)) + ConstUtil.TIME_START;
      String endtime = String.valueOf(sdf.format(d)) + ConstUtil.TIME_END;
      Map<String, String> map1 = new HashMap<>();
      map1.put("starttime", starttime);
      map1.put("endtime", endtime);
      int glist = this.clogic.selectNoPage4IndexGzlNum(map1);
      JSONObject jobj = new JSONObject();
      jobj.put("mdataCount", Integer.valueOf(mdataCount));
      jobj.put("ssdataCount", Integer.valueOf(ssdataCount));
      jobj.put("vdataCount", Integer.valueOf(vdataCount));
      jobj.put("gdataCount", Integer.valueOf(glist));
      jobj.put("roomdataCount", Integer.valueOf(ssdataCount));
      jobj.put("tgjhCount", Integer.valueOf(tgjhCount));
      jobj.put("ckbjCount", Integer.valueOf(ckbjCount));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getCountIndex1.act"})
  public String getCountIndex1(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String querytype = request.getParameter("querytype");
      String searchValue = request.getParameter("searchValue");
      String visualstaff = SessionUtil.getVisualstaff(request);
      String organization = ChainUtil.getCurrentOrganization(request);
      Map<String, String> map = new HashMap<>();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String time = df.format(new Date());
      map.put("starttime", String.valueOf(time) + ConstUtil.TIME_START);
      map.put("endtime", String.valueOf(time) + ConstUtil.TIME_END);
      map.put("organization", person.getOrganization());
      int wdataCount = this.logic.getCountIndex(TableNameUtil.KQDS_NET_ORDER, person, querytype, searchValue, visualstaff, organization);
      int ssdataCount = this.roomlogic.getCountIndex(TableNameUtil.KQDS_ROOM, person, querytype, searchValue, visualstaff, organization);
      int mdataCount = this.hlogic.getCountIndex(querytype, searchValue, visualstaff, organization);
      int tdataCountd = this.rlogic.getCountIndex(TableNameUtil.KQDS_REG, person, 0, querytype, searchValue, visualstaff, organization, request);
      int tdataCountj = this.rlogic.getCountIndex(TableNameUtil.KQDS_REG, person, 5, querytype, searchValue, visualstaff, organization, request);
      int ddataCount = this.clogic.getCountIndex(person, querytype, searchValue, visualstaff, organization, request);
      int ydataCount = this.plogic.getCountIndex(TableNameUtil.KQDS_COSTORDER, person, querytype, searchValue, visualstaff, organization);
      JSONObject jobj = new JSONObject();
      jobj.put("wdataCount", Integer.valueOf(wdataCount));
      jobj.put("mdataCount", Integer.valueOf(mdataCount));
      jobj.put("ssdataCount", Integer.valueOf(ssdataCount));
      jobj.put("tdataCountd", Integer.valueOf(tdataCountd));
      jobj.put("tdataCountj", Integer.valueOf(tdataCountj));
      jobj.put("ddataCount", Integer.valueOf(ddataCount));
      jobj.put("ydataCount", Integer.valueOf(ydataCount));
      jobj.put("roomdataCount", Integer.valueOf(ssdataCount));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectCountYySml.act"})
  public String selectCountYySml(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String[] datearray = YZUtility.getBetweenDatesArray(starttime, endtime);
      Double[] smlarray = new Double[datearray.length];
      for (int i = 0; i < datearray.length; i++) {
        Double sml = this.logic.getSml(datearray[i]);
        smlarray[i] = sml;
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("datearray", datearray);
      jobj.put("smlarray", smlarray);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectCountYySmlTable.act"})
  public String selectCountYySmlTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      List<Object> list = new ArrayList();
      String[] datearray = YZUtility.getBetweenDatesArray(starttime, endtime);
      int[] yyarray = new int[datearray.length];
      int[] yysmarray = new int[datearray.length];
      String[] smlarray = new String[datearray.length];
      int yysum = 0;
      int yysmsum = 0;
      for (int i = 0; i < datearray.length; i++) {
        int yynum = this.logic.getCountYy(TableNameUtil.KQDS_NET_ORDER, datearray[i]);
        yysum += yynum;
        int yysmnum = this.logic.getCountYysm(TableNameUtil.KQDS_NET_ORDER, datearray[i]);
        yysmsum += yysmnum;
        if (yysmnum == 0) {
          smlarray[i] = "0";
        } else {
          float num = yysmnum / yynum * 100.0F;
          DecimalFormat df = new DecimalFormat("0.00");
          smlarray[i] = df.format(num);
        } 
        yyarray[i] = yynum;
        yysmarray[i] = yysmnum;
      } 
      list.add(datearray);
      list.add(yyarray);
      list.add(yysmarray);
      list.add(smlarray);
      list.add(Integer.valueOf(yysum));
      list.add(Integer.valueOf(yysmsum));
      if (yysmsum == 0) {
        list.add("0");
      } else {
        float num = yysmsum / yysum * 100.0F;
        DecimalFormat df = new DecimalFormat("0.00");
        list.add(df.format(num));
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getWdOrdertj.act"})
  public String getWdOrdertj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String wdperson = request.getParameter("wdperson");
      String yxperson = request.getParameter("yxperson");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap<>();
      String depttype = "";
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      if (wdperson != null) {
        map.put("wdperson", wdperson);
        depttype = ConstUtil.DEPT_TYPE_2;
      } 
      if (yxperson != null) {
        map.put("wdperson", yxperson);
        depttype = ConstUtil.DEPT_TYPE_3;
      } 
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
        organization = ChainUtil.getCurrentOrganization(request); 
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> listPerson = this.personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);
      List<JSONObject> list = new ArrayList<>();
      for (int i = 0; i < listPerson.size(); i++) {
        JSONObject objper = listPerson.get(i);
        JSONObject obj = new JSONObject();
        obj.put("xh", Integer.valueOf(i + 1));
        obj.put("username", objper.get("userName"));
        map.put("wdperson", (String)objper.get("seqId"));
        int ldnums = this.logic.getCountJd(map);
        obj.put("ldnums", Integer.valueOf(ldnums));
        int yynums = this.logic.getCountYy(map);
        obj.put("yynums", Integer.valueOf(yynums));
        Float yyl = Float.valueOf(0.0F);
        if (ldnums != 0)
          yyl = Float.valueOf(yynums * 100.0F / ldnums); 
        obj.put("yyl", String.valueOf(YZUtility.FloatToFixed2(yyl)) + "%");
        int yysmnums = this.logic.getCountYysm(map);
        obj.put("yysmnums", Integer.valueOf(yysmnums));
        Float dzl = Float.valueOf(0.0F);
        if (ldnums != 0)
          dzl = Float.valueOf(yysmnums * 100.0F / ldnums); 
        obj.put("dzl", String.valueOf(YZUtility.FloatToFixed2(dzl)) + "%");
        String skje = this.logic.getYysr(map);
        String skjeYjj = this.logic.getYysrYjj(map);
        String sk = YZUtility.FloatToFixed2(Float.valueOf(Float.parseFloat(skje) + Float.parseFloat(skjeYjj)));
        obj.put("skje", sk);
        int cjnums = this.logic.getCountCj(map);
        obj.put("cjnums", Integer.valueOf(cjnums));
        Float cjl = Float.valueOf(0.0F);
        if (yysmnums != 0)
          cjl = Float.valueOf(cjnums * 100.0F / yysmnums); 
        obj.put("cjl", String.valueOf(YZUtility.FloatToFixed2(cjl)) + "%");
        Float rjxf = Float.valueOf(0.0F);
        if (yysmnums != 0)
          rjxf = Float.valueOf(Float.parseFloat(sk) / yysmnums); 
        obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
        list.add(obj);
      } 
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("网电预约统计", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateIsDeleteById.act"})
  public void updateIsDeleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    try {
      this.logic.updateIsdeleteById(seqId, 1);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
  }
}
