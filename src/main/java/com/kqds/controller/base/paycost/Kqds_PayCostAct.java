package com.kqds.controller.base.paycost;

import com.hudh.dzbl.dao.DzblDetailDao;
import com.kqds.entity.base.KqdsPaycost;
import com.kqds.service.base.chufang.KQDS_ChuFangLogic;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.CacheUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"Kqds_PayCostAct"})
public class Kqds_PayCostAct
{
  private static Logger logger = LoggerFactory.getLogger(Kqds_PayCostAct.class);
  @Autowired
  private Kqds_PayCostLogic logic;
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  @Autowired
  private KQDS_REGLogic regLogic;
  @Autowired
  private KQDS_CostOrderLogic costOrderLogic;
  @Autowired
  private KQDS_CostOrder_DetailLogic costOrderDetailLogic;
  @Autowired
  private KQDS_ChuFangLogic chuFangLogic;
  @Autowired
  private DzblDetailDao dzblDetailDao;
  
  @RequestMapping({"/toXfjlWin.act"})
  public ModelAndView toXfjlWin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/coatOrder/xfjlWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCostListing.act"})
  public ModelAndView toCostListing(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String regno = request.getParameter("regno");
    String costno = request.getParameter("costno");
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    ModelAndView mv = new ModelAndView();
    mv.addObject("regno", regno);
    mv.addObject("costno", costno);
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.setViewName("/kqdsFront/coatOrder/cost_listing.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCostListingPrint.act"})
  public ModelAndView toCostListingPrint(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String costno = request.getParameter("costno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("costno", costno);
    mv.setViewName("/kqdsFront/coatOrder/cost_listing_print.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCost_EditPaymoney.act"})
  public ModelAndView toCost_EditPaymoney(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/coatOrder/cost_editPaymoney.jsp");
    return mv;
  }
  
  @RequestMapping({"/tocw_invoice.act"})
  public ModelAndView tocw_invoice(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/jdzx/cw_invoice.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      KqdsPaycost dp = new KqdsPaycost();
      BeanUtils.populate(dp, request.getParameterMap());
      this.logic.payMoney(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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
      KqdsPaycost en = (KqdsPaycost)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_PAYCOST, seqId);
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
  
  @RequestMapping({"/selectDetailByCostNo.act"})
  public String selectDetailByCostNo(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String costno = request.getParameter("costno");
      
      Map<String, String> map = new HashMap();
      map.put("costno", costno);
      List<KqdsPaycost> newen = (List)this.logic.loadList(TableNameUtil.KQDS_PAYCOST, map);
      JSONObject jobj = new JSONObject();
      jobj.put("data", newen.get(0));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDetailByUsercode.act"})
  public String selectDetailByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      
      Map<String, String> map = new HashMap();
      map.put("usercode", usercode);
      List<KqdsPaycost> newen = (List)this.logic.loadList(TableNameUtil.KQDS_PAYCOST, map);
      JSONObject jobj = new JSONObject();
      jobj.put("data", newen);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDoctor.act"})
  public String selectDoctor(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      String doctor = this.userLogic.selectDoctorByusercode(TableNameUtil.KQDS_PAYCOST, usercode);
      JSONObject jobj = new JSONObject();
      jobj.put("data", doctor);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/transferringData.act"})
  public String transferringData(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercodes");
    
    String[] stringArr = usercode.substring(1, usercode.length() - 1).split(",");
    
    List<String> list = new ArrayList();
    for (String string : stringArr) {
      list.add(string.replaceAll("\"", "'"));
    }
    try
    {
      List<JSONObject> list1 = this.userLogic.findKqdsUserdocumentByUsercodes(StringUtils.strip(list.toString(), "[]"));
      
      Object list2 = this.regLogic.findKqdsRegByUsercodes(StringUtils.strip(list.toString(), "[]"));
      
      Object list3 = this.costOrderLogic.findCostOrderByUsercodes(StringUtils.strip(list.toString(), "[]"));
      
      Object list4 = this.costOrderDetailLogic.findCostOrderDetailByUsercodes(StringUtils.strip(list.toString(), "[]"));
      
      List<JSONObject> list5 = this.costOrderLogic.findCostOrderTuidanByUsercodes(StringUtils.strip(list.toString(), "[]"));
      
      List<JSONObject> list6 = this.costOrderDetailLogic.findCostOrderDetailTuidanByUsercodes(StringUtils.strip(list.toString(), "[]"));
      
      List<JSONObject> list7 = this.chuFangLogic.findChuFangByUsercodes(StringUtils.strip(list.toString(), "[]"));
      
      List<JSONObject> list8 = this.chuFangLogic.findChuFangDetailByUsercodes(StringUtils.strip(list.toString(), "[]"));
      
      List<JSONObject> list9 = this.logic.findPayCostByUsercodes(StringUtils.strip(list.toString(), "[]"));
      
      List<JSONObject> list10 = this.dzblDetailDao.findDzblDetailByUsercodes(StringUtils.strip(list.toString(), "[]"));
      

      CacheUtil.openCache();
      if ((list1 != null) && (list1.size() > 0))
      {
        CacheUtil.addZSet("KqdsUserdocument:key", ((JSONObject)list1.get(0)).getString("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key1 = new HashMap();
        key1.put(((JSONObject)list1.get(0)).getString("usercode"), list1);
        CacheUtil.setMap("KqdsUserdocument:value", key1);
      }
      if ((list2 != null) && (((List)list2).size() > 0))
      {
        CacheUtil.addZSet("KqdsReg:key", ((JSONObject)((List)list2).get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key2 = new HashMap();
        key2.put(((JSONObject)((List)list2).get(0)).getString("usercode"), list2);
        CacheUtil.setMap("KqdsReg:value", key2);
      }
      if ((list3 != null) && (((List)list3).size() > 0))
      {
        CacheUtil.addZSet("KqdsCostOrder:key", ((JSONObject)((List)list3).get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key3 = new HashMap();
        key3.put(((JSONObject)((List)list3).get(0)).getString("usercode"), list3);
        CacheUtil.setMap("KqdsCostOrder:value", key3);
      }
      if ((list4 != null) && (((List)list4).size() > 0))
      {
        CacheUtil.addZSet("KqdsCostOrderDetail:key", ((JSONObject)((List)list4).get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key4 = new HashMap();
        key4.put(((JSONObject)((List)list4).get(0)).getString("usercode"), list4);
        CacheUtil.setMap("KqdsCostOrderDetail:value", key4);
      }
      if ((list5 != null) && (list5.size() > 0))
      {
        CacheUtil.addZSet("KqdsCostOrderTuidan:key", ((JSONObject)list5.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key5 = new HashMap();
        key5.put(((JSONObject)list5.get(0)).getString("usercode"), list5);
        CacheUtil.setMap("KqdsCostOrderTuidan:value", key5);
      }
      if ((list6 != null) && (list6.size() > 0))
      {
        CacheUtil.addZSet("KqdsCostOrderDetailTuidan:key", ((JSONObject)list6.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key6 = new HashMap();
        key6.put(((JSONObject)list6.get(0)).getString("usercode"), list6);
        CacheUtil.setMap("KqdsCostOrderDetailTuidan:value", key6);
      }
      if ((list7 != null) && (list7.size() > 0))
      {
        CacheUtil.addZSet("KqdsChuFang:key", ((JSONObject)list7.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key7 = new HashMap();
        key7.put(((JSONObject)list7.get(0)).getString("usercode"), list7);
        CacheUtil.setMap("KqdsChuFang:value", key7);
      }
      if ((list8 != null) && (list8.size() > 0))
      {
        CacheUtil.addZSet("KqdsChuFangDetail:key", ((JSONObject)list8.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key8 = new HashMap();
        key8.put(((JSONObject)list8.get(0)).getString("usercode"), list8);
        CacheUtil.setMap("KqdsChuFangDetail:value", key8);
      }
      if ((list9 != null) && (list9.size() > 0))
      {
        CacheUtil.addZSet("KqdsPayCost:key", ((JSONObject)list9.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key9 = new HashMap();
        key9.put(((JSONObject)list9.get(0)).getString("usercode"), list9);
        CacheUtil.setMap("KqdsPayCost:value", key9);
      }
      if ((list10 != null) && (list10.size() > 0))
      {
        CacheUtil.addZSet("HudhDzblDetail:key", ((JSONObject)list10.get(0)).get("blcode"), new Date().getTime());
        Map<String, List<JSONObject>> key10 = new HashMap();
        key10.put(((JSONObject)list10.get(0)).getString("blcode"), list10);
        CacheUtil.setMap("HudhDzblDetail:value", key10);
      }
      CacheUtil.close();
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
}
