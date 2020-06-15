package com.hudh.ykzz.controller;

import com.alibaba.fastjson.JSON;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.service.IYkzzDrugsInService;
import com.hudh.ykzz.service.IYkzzDrugsOutService;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.refund.KQDS_RefundLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/HUDH_YkzzDrugsInAct"})
public class HUDH_YkzzDrugsInAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_YkzzDrugsInAct.class);
  @Autowired
  private IYkzzDrugsInService ykzzDrugsInService;
  @Autowired
  private IYkzzDrugsOutService ykzzDrugsOutService;
  @Autowired
  private KQDS_RefundLogic logic;
  
  @RequestMapping({"/insertDrugsIn.act"})
  public String insertDrugsIn(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String drugsIndetails = request.getParameter("paramDetail");
    String rktime = request.getParameter("rktime");
    String intype = request.getParameter("intype");
    String supplier = request.getParameter("supplier");
    String stocktime = request.getParameter("stocktime");
    String incode = request.getParameter("incode");
    String inremark = request.getParameter("inremark");
    String zhaiyao = request.getParameter("zhaiyao");
    
    YkzzDrugsIn ykzzDrugsIn = new YkzzDrugsIn();
    ykzzDrugsIn.setRktime(rktime);
    ykzzDrugsIn.setIntype(intype);
    ykzzDrugsIn.setSupplier(supplier);
    ykzzDrugsIn.setStocktime(stocktime);
    ykzzDrugsIn.setIncode(incode);
    ykzzDrugsIn.setInremark(inremark);
    ykzzDrugsIn.setZhaiyao(zhaiyao);
    try
    {
      this.ykzzDrugsInService.insertDrugsIn(ykzzDrugsIn, drugsIndetails, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteDrugsIn.act"})
  public String deleteDrugsIn(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      String backMg = this.ykzzDrugsInService.deleteDrugsIn(id);
      YZUtility.DEAL_SUCCESS(null, backMg, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteDrugsInById.act"})
  public String deleteDrugsInById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      this.ykzzDrugsInService.deleteDrugsInById(id);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findAllDrugsIn.act"})
  public void findAllDrugsIn(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String intype = request.getParameter("intype");
    String supplier = request.getParameter("supplier");
    String incode = request.getParameter("incode");
    String id = request.getParameter("id");
    String checkStatus = request.getParameter("check_status");
    String accurateIncode = request.getParameter("accurateIncode");
    
    String stock_starttime = request.getParameter("stock_starttime");
    String stock_endtime = request.getParameter("stock_endtime");
    String organization = ChainUtil.getCurrentOrganization(request);
    Map<String, String> map = new HashMap();
    if (!YZUtility.isNullorEmpty(id)) {
      map.put("id", id);
    }
    if (!YZUtility.isNullorEmpty(starttime)) {
      map.put("starttime", starttime);
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      map.put("endtime", endtime);
    }
    if (!YZUtility.isNullorEmpty(intype)) {
      map.put("intype", intype);
    }
    if (!YZUtility.isNullorEmpty(supplier)) {
      map.put("supplier", supplier);
    }
    if (!YZUtility.isNullorEmpty(incode)) {
      map.put("incode", incode);
    }
    if (!YZUtility.isNullorEmpty(stock_starttime)) {
      map.put("stock_starttime", stock_starttime);
    }
    if (!YZUtility.isNullorEmpty(stock_endtime)) {
      map.put("stock_endtime", stock_endtime);
    }
    if (!YZUtility.isNullorEmpty(accurateIncode)) {
      map.put("accurateIncode", accurateIncode);
    }
    if (!YZUtility.isNullorEmpty(checkStatus)) {
      map.put("checkStatus", checkStatus);
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    try
    {
      List<JSONObject> list = this.ykzzDrugsInService.findAllDrugsIn(map);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/findDetailByParendId.act"})
  public void findDetailByParendId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String parentid = request.getParameter("parentid");
    try
    {
      if (YZUtility.isNotNullOrEmpty(parentid))
      {
        List<JSONObject> list = this.ykzzDrugsInService.findDetailByParendId(parentid);
        YZUtility.RETURN_LIST(list, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/findAllCostOrder.act"})
  public void findAllCostOrder(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String hzname = request.getParameter("hzname");
    String status = request.getParameter("status");
    String issend = request.getParameter("issend");
    String organization = ChainUtil.getCurrentOrganization(request);
    
    Map<String, Object> map = new HashMap();
    if (!YZUtility.isNullorEmpty(starttime)) {
      map.put("starttime", starttime + ConstUtil.TIME_START);
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      map.put("endtime", endtime + ConstUtil.TIME_END);
    }
    if (!YZUtility.isNullorEmpty(hzname)) {
      map.put("hzname", hzname);
    }
    if (!YZUtility.isNullorEmpty(status)) {
      map.put("status", status);
    }
    if (!YZUtility.isNullorEmpty(issend)) {
      map.put("issend", issend);
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    try
    {
      List<JSONObject> list = this.ykzzDrugsInService.findAllCostOrder(map);
      List<JSONObject> show = new ArrayList();
      for (int i = 0; i < list.size(); i++)
      {
        BigDecimal money = new BigDecimal((String)((JSONObject)list.get(i)).get("actualmoney"));
        BigDecimal money4 = new BigDecimal((String)((JSONObject)list.get(i)).get("voidmoney"));
        BigDecimal money1 = new BigDecimal("0");
        BigDecimal money2 = new BigDecimal("0");
        BigDecimal money3 = new BigDecimal("0");
        if ((money.compareTo(money1) > 0) || (money4.compareTo(money1) > 0))
        {
          int variate = 0;
          
          int frequency = 0;
          String costno = ((JSONObject)list.get(i)).getString("costno");
          

          List<JSONObject> list2 = this.ykzzDrugsInService.findCostOrderDetailByCostno(costno);
          for (int j = 0; j < list2.size(); j++) {
            if (((JSONObject)list2.get(j)).get("status").equals("0"))
            {
              BigDecimal unitprice = new BigDecimal((String)((JSONObject)list2.get(j)).get("unitprice"));
              BigDecimal num = new BigDecimal((String)((JSONObject)list2.get(j)).get("num"));
              String qfbh = (String)((JSONObject)list2.get(j)).get("qfbh");
              if ((qfbh != null) && (!qfbh.equals("")))
              {
                List<JSONObject> list3 = this.ykzzDrugsInService.findCostOrderDetailByQfbh(qfbh);
                
                BigDecimal paymoney1 = new BigDecimal((String)((JSONObject)list2.get(j)).get("paymoney"));
                for (JSONObject kqdsCostorderDetail : list3) {
                  if (unitprice.multiply(num).compareTo(paymoney1.add(new BigDecimal((String)kqdsCostorderDetail.get("paymoney")))) == 0) {
                    money3 = money3.add(new BigDecimal((String)kqdsCostorderDetail.get("paymoney")));
                  }
                }
              }
            }
          }
          money = money.add(money3);
          if ((money3.compareTo(money1) == 0) && (((JSONObject)list.get(i)).get("actualmoney").equals(((JSONObject)list.get(i)).get("shouldmoney"))))
          {
            BigDecimal voidmoney = new BigDecimal("0");
            for (int j = 0; j < list2.size(); j++) {
              if (((JSONObject)list2.get(j)).get("status").equals("0"))
              {
                if ((((JSONObject)list2.get(j)).getString("voidmoney") != null) && (!((JSONObject)list2.get(j)).getString("voidmoney").equals(""))) {
                  voidmoney = voidmoney.add(new BigDecimal(((JSONObject)list2.get(j)).getString("voidmoney")));
                }
                String parentid = (String)((JSONObject)list2.get(j)).get("parentid");
                if ((parentid == null) || (parentid.equals("")))
                {
                  String seqId = (String)((JSONObject)list2.get(j)).get("seq_id");
                  KqdsCostorderDetail kqdsCostorderDetail = this.ykzzDrugsInService.findCostOrderDetailByParentid(seqId);
                  if (kqdsCostorderDetail != null)
                  {
                    BigDecimal paymoney = kqdsCostorderDetail.getPaymoney();
                    money2 = money2.add(paymoney);
                    variate = 1;
                  }
                }
                if (variate == 1)
                {
                  frequency++;
                  variate = 0;
                }
              }
            }
            if ((frequency <= list2.size()) && (money.add(money2).compareTo(money1) > 0))
            {
              ((JSONObject)list.get(i)).put("actualmoney", money.add(money2));
              ((JSONObject)list.get(i)).put("shouldmoney", money.add(money2));
              show.add((JSONObject)list.get(i));
            }
            else if ((frequency <= list2.size()) && (money.add(money2).compareTo(money1) == 0) && (voidmoney.compareTo(money1) > 0))
            {
              ((JSONObject)list.get(i)).put("actualmoney", voidmoney);
              ((JSONObject)list.get(i)).put("shouldmoney", voidmoney);
              show.add((JSONObject)list.get(i));
            }
          }
          else if ((money3.compareTo(money1) > 0) && (!((JSONObject)list.get(i)).get("actualmoney").equals(((JSONObject)list.get(i)).get("shouldmoney"))))
          {
            for (int j = 0; j < list2.size(); j++)
            {
              String qfbh = (String)((JSONObject)list2.get(j)).get("qfbh");
              String parentid = (String)((JSONObject)list2.get(j)).get("parentid");
              if ((parentid == null) || (parentid.equals("")))
              {
                KqdsCostorderDetail kqdsCostorderDetail1 = this.ykzzDrugsInService.findCostOrderDetailSubtotalByQfbh(qfbh);
                if (kqdsCostorderDetail1 != null)
                {
                  BigDecimal paymoney = kqdsCostorderDetail1.getPaymoney();
                  money2 = money2.add(paymoney);
                  variate = 1;
                }
              }
              if (variate == 1)
              {
                frequency++;
                variate = 0;
              }
            }
            if ((frequency <= list2.size()) && (money.add(money2).compareTo(money1) > 0))
            {
              ((JSONObject)list.get(i)).put("actualmoney", money.add(money2));
              ((JSONObject)list.get(i)).put("shouldmoney", money.add(money2));
              show.add((JSONObject)list.get(i));
            }
          }
        }
      }
      YZUtility.RETURN_LIST(show, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/findAllCostOrderReturn.act"})
  public void findAllCostOrderReturn(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = ChainUtil.getCurrentOrganization(request);
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String hzname = request.getParameter("hzname");
    String status = request.getParameter("status");
    String issend = request.getParameter("issend");
    
    Map<String, Object> map = new HashMap();
    if (!YZUtility.isNullorEmpty(starttime)) {
      map.put("starttime", starttime + ConstUtil.TIME_START);
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      map.put("endtime", endtime + ConstUtil.TIME_END);
    }
    if (!YZUtility.isNullorEmpty(hzname)) {
      map.put("hzname", hzname);
    }
    if (!YZUtility.isNullorEmpty(status)) {
      map.put("status", status);
    }
    if (!YZUtility.isNullorEmpty(issend)) {
      map.put("issend", issend);
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    try
    {
      List<JSONObject> list = this.ykzzDrugsInService.findAllCostOrder(map);
      List<JSONObject> list2 = new ArrayList();
      for (int i = 0; i < list.size(); i++)
      {
        BigDecimal money = new BigDecimal((String)((JSONObject)list.get(i)).get("actualmoney"));
        BigDecimal money1 = new BigDecimal("0");
        BigDecimal money2 = new BigDecimal("0");
        BigDecimal money3 = new BigDecimal("0");
        BigDecimal unitprice1 = new BigDecimal("0");
        if (money.compareTo(money1) > 0)
        {
          int variate = 0;
          
          int frequency = 0;
          
          int tynums = 0;
          String costno = ((JSONObject)list.get(i)).getString("costno");
          

          List<JSONObject> list1 = this.ykzzDrugsInService.findCostOrderDetailByCostno(costno);
          for (int j = 0; j < list1.size(); j++) {
            if (((JSONObject)list1.get(j)).get("status").equals("0"))
            {
              BigDecimal unitprice = new BigDecimal((String)((JSONObject)list1.get(j)).get("unitprice"));
              BigDecimal num = new BigDecimal((String)((JSONObject)list1.get(j)).get("num"));
              String qfbh = (String)((JSONObject)list1.get(j)).get("qfbh");
              if ((qfbh != null) && (!qfbh.equals("")))
              {
                List<JSONObject> list3 = this.ykzzDrugsInService.findCostOrderDetailByQfbh(qfbh);
                
                BigDecimal paymoney1 = new BigDecimal((String)((JSONObject)list1.get(j)).get("paymoney"));
                for (JSONObject kqdsCostorderDetail : list3) {
                  if (unitprice.multiply(num).compareTo(paymoney1.add(new BigDecimal((String)kqdsCostorderDetail.get("paymoney")))) == 0) {
                    money3 = money3.add(new BigDecimal((String)kqdsCostorderDetail.get("paymoney")));
                  }
                }
              }
            }
          }
          money = money.add(money3);
          if ((money3.compareTo(money1) == 0) && (((JSONObject)list.get(i)).get("actualmoney").equals(((JSONObject)list.get(i)).get("shouldmoney"))))
          {
            String seqId;
            KqdsCostorderDetail kqdsCostorderDetail;
            for (int j = 0; j < list1.size(); j++) {
              if (((JSONObject)list1.get(j)).get("status").equals("0"))
              {
                String parentid = (String)((JSONObject)list1.get(j)).get("parentid");
                if ((parentid == null) || (parentid.equals("")))
                {
                  seqId = (String)((JSONObject)list1.get(j)).get("seq_id");
                  kqdsCostorderDetail = this.ykzzDrugsInService.findCostOrderDetailByParentid(seqId);
                  if (kqdsCostorderDetail != null)
                  {
                    BigDecimal paymoney = kqdsCostorderDetail.getPaymoney();
                    tynums += Integer.parseInt(kqdsCostorderDetail.getNum());
                    money2 = money2.add(paymoney);
                    variate = 1;
                  }
                }
                if (variate == 1)
                {
                  frequency++;
                  variate = 0;
                }
              }
            }
            if ((frequency <= list1.size()) && (money.add(money2).compareTo(money1) > 0))
            {
              int nums = 0;
              for (JSONObject jsonObject : list1) {
                nums += Integer.parseInt((String)jsonObject.get("num"));
              }
              int a = 0;
              for (JSONObject jsonObject2 : list1)
              {
                List<JSONObject> ykzzDrugsReturnList = this.ykzzDrugsInService.findCostOrderDetailReturnBySeqid(jsonObject2.getString("seq_id"));
                
                int re = 0;
                for (JSONObject jsonObject : ykzzDrugsReturnList) {
                  if ((jsonObject != null) && (jsonObject.size() > 0))
                  {
                    String seqid = (String)jsonObject.get("seqid");
                    KqdsCostorderDetail costorderDetail = this.ykzzDrugsInService.findCostOrderDetailBySeqid(seqid);
                    BigDecimal unitprice = costorderDetail.getUnitprice();
                    int drugsnum = Integer.parseInt((String)jsonObject.get("returndrugsnum"));
                    unitprice1 = unitprice1.add(unitprice.multiply(new BigDecimal(drugsnum)));
                    re += drugsnum;
                  }
                }
                a += re;
              }
              if (nums + tynums > a)
              {
                ((JSONObject)list.get(i)).put("actualmoney", money.add(money2).subtract(unitprice1));
                ((JSONObject)list.get(i)).put("shouldmoney", money.add(money2).subtract(unitprice1));
                list2.add((JSONObject)list.get(i));
              }
            }
          }
          else if ((money3.compareTo(money1) > 0) && (!((JSONObject)list.get(i)).get("actualmoney").equals(((JSONObject)list.get(i)).get("shouldmoney"))))
          {
            String parentid;
            KqdsCostorderDetail kqdsCostorderDetail1;
            for (int j = 0; j < list1.size(); j++)
            {
              String qfbh = (String)((JSONObject)list1.get(j)).get("qfbh");
              parentid = (String)((JSONObject)list1.get(j)).get("parentid");
              if ((parentid == null) || (parentid.equals("")))
              {
                kqdsCostorderDetail1 = this.ykzzDrugsInService.findCostOrderDetailSubtotalByQfbh(qfbh);
                if (kqdsCostorderDetail1 != null)
                {
                  BigDecimal paymoney = kqdsCostorderDetail1.getPaymoney();
                  money2 = money2.add(paymoney);
                  tynums += Integer.parseInt(kqdsCostorderDetail1.getNum());
                  variate = 1;
                }
              }
              if (variate == 1)
              {
                frequency++;
                variate = 0;
              }
            }
            if ((frequency <= list1.size()) && (money.add(money2).compareTo(money1) > 0))
            {
              int nums = 0;
              for (JSONObject jsonObject : list1) {
                nums += Integer.parseInt((String)jsonObject.get("num"));
              }
              int a = 0;
              for (JSONObject jsonObject2 : list1)
              {
                List<JSONObject> ykzzDrugsReturnList = this.ykzzDrugsInService.findCostOrderDetailReturnBySeqid(jsonObject2.getString("seq_id"));
                
                int re = 0;
                for (JSONObject jsonObject : ykzzDrugsReturnList) {
                  if ((jsonObject != null) && (jsonObject.size() > 0))
                  {
                    String seqid = (String)jsonObject.get("seqid");
                    KqdsCostorderDetail costorderDetail = this.ykzzDrugsInService.findCostOrderDetailBySeqid(seqid);
                    BigDecimal unitprice = costorderDetail.getUnitprice();
                    int drugsnum = Integer.parseInt((String)jsonObject.get("returndrugsnum"));
                    unitprice1 = unitprice1.add(unitprice.multiply(new BigDecimal(drugsnum)));
                    re += drugsnum;
                  }
                }
                a += re;
              }
              if (nums + tynums > a)
              {
                ((JSONObject)list.get(i)).put("actualmoney", money.add(money2).subtract(unitprice1));
                ((JSONObject)list.get(i)).put("shouldmoney", money.add(money2).subtract(unitprice1));
                list2.add((JSONObject)list.get(i));
              }
            }
          }
        }
      }
      YZUtility.RETURN_LIST(list2, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/findCostOrderDetailByCostno.act"})
  public void findCostOrderDetailByCostno(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String costno = request.getParameter("costno");
    try
    {
      if ((YZUtility.isNotNullOrEmpty(costno)) && (!costno.equals("undefined")))
      {
        List<JSONObject> list = this.ykzzDrugsInService.findCostOrderDetailByCostno(costno);
        List<JSONObject> show = new ArrayList();
        String znums = null;
        for (int i = 0; i < list.size(); i++)
        {
          String nums = (String)((JSONObject)list.get(i)).get("num");
          znums = nums;
          BigDecimal money2 = new BigDecimal("0");
          int tknum = 0;
          
          KqdsCostorderDetail kqdsCostorderDetail = this.ykzzDrugsInService.findCostOrderDetailByParentid((String)((JSONObject)list.get(i)).get("seq_id"));
          if (kqdsCostorderDetail != null)
          {
            BigDecimal paymoney = kqdsCostorderDetail.getPaymoney();
            String num = kqdsCostorderDetail.getNum();
            tknum += Integer.parseInt(num);
            money2 = money2.add(paymoney);
          }
          else if (kqdsCostorderDetail == null)
          {
            String qfbh = (String)((JSONObject)list.get(i)).get("qfbh");
            if ((qfbh != null) && (!qfbh.equals("")))
            {
              KqdsCostorderDetail kqdsCostorderDetail1 = this.ykzzDrugsInService.findCostOrderDetailSubtotalByQfbh(qfbh);
              if (kqdsCostorderDetail1 != null)
              {
                BigDecimal paymoney = kqdsCostorderDetail1.getPaymoney();
                String num = kqdsCostorderDetail1.getNum();
                tknum += Integer.parseInt(num);
                money2 = money2.add(paymoney);
              }
            }
          }
          if (Integer.parseInt(nums) + tknum > 0)
          {
            ((JSONObject)list.get(i)).put("num", Integer.valueOf(Integer.parseInt(nums) + tknum));
            ((JSONObject)list.get(i)).put("subtotal", new BigDecimal(((JSONObject)list.get(i)).getString("unitprice")).multiply(new BigDecimal(znums)).add(money2));
            List<JSONObject> ykzzDrugsReturnList = this.ykzzDrugsInService.findCostOrderDetailReturnBySeqid((String)((JSONObject)list.get(i)).get("seq_id"));
            
            int ret = 0;
            String returndrugs = null;
            for (JSONObject jsonObject : ykzzDrugsReturnList) {
              if ((jsonObject != null) && (jsonObject.size() > 0))
              {
                returndrugs = (String)jsonObject.get("returndrugs");
                int drugsnum = Integer.parseInt((String)jsonObject.get("returndrugsnum"));
                ret += drugsnum;
              }
            }
            Object orderNo = ((JSONObject)list.get(i)).get("itemno");
            String order_no = orderNo.toString();
            
            List<YkzzDrugsInDetail> yk = this.ykzzDrugsInService.findBatchnumByOrderno(order_no);
            String batchno = "";
            String id = "";
            for (int j = 0; j < yk.size(); j++)
            {
              String batchnum = ((YkzzDrugsInDetail)yk.get(j)).getBatchnum();
              batchno = batchnum + "," + batchno;
              String firstId = ((YkzzDrugsInDetail)yk.get(j)).getId();
              id = firstId + "," + id;
            }
            if (ret != 0)
            {
              ((JSONObject)list.get(i)).put("returnMoney", JSON.toJSON(new BigDecimal(ret).multiply(new BigDecimal((String)((JSONObject)list.get(i)).get("unitprice")).setScale(3, 4))));
              ((JSONObject)list.get(i)).put("returndrugsnum", JSON.toJSON(Integer.valueOf(ret)));
              ((JSONObject)list.get(i)).put("returndrugs", JSON.toJSON(returndrugs));
            }
            ((JSONObject)list.get(i)).put("batchnum", JSON.toJSON(batchno));
            ((JSONObject)list.get(i)).put("id", JSON.toJSON(id));
            ((JSONObject)list.get(i)).put("costno", costno);
            show.add((JSONObject)list.get(i));
          }
        }
        YZUtility.RETURN_LIST(show, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/findCostOrderDetailByCostno1.act"})
  public void findCostOrderDetailByCostno1(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String costno = request.getParameter("costno");
    try
    {
      if (YZUtility.isNotNullOrEmpty(costno))
      {
        List<JSONObject> list = this.ykzzDrugsInService.findCostOrderDetailByCostno(costno);
        List<JSONObject> list1 = new ArrayList();
        for (int i = 0; i < list.size(); i++)
        {
          String nums = (String)((JSONObject)list.get(i)).get("num");
          BigDecimal money2 = new BigDecimal("0");
          BigDecimal unitprice1 = new BigDecimal("0");
          int tknum = 0;
          
          KqdsCostorderDetail kqdsCostorderDetail = this.ykzzDrugsInService.findCostOrderDetailByParentid((String)((JSONObject)list.get(i)).get("seq_id"));
          if (kqdsCostorderDetail != null)
          {
            BigDecimal paymoney = kqdsCostorderDetail.getPaymoney();
            String num = kqdsCostorderDetail.getNum();
            tknum += Integer.parseInt(num);
            money2 = money2.add(paymoney);
          }
          else if (kqdsCostorderDetail == null)
          {
            String qfbh = (String)((JSONObject)list.get(i)).get("qfbh");
            if ((qfbh != null) && (!qfbh.equals("")))
            {
              KqdsCostorderDetail kqdsCostorderDetail1 = this.ykzzDrugsInService.findCostOrderDetailSubtotalByQfbh(qfbh);
              if (kqdsCostorderDetail1 != null)
              {
                BigDecimal paymoney = kqdsCostorderDetail1.getPaymoney();
                String num = kqdsCostorderDetail1.getNum();
                tknum += Integer.parseInt(num);
                money2 = money2.add(paymoney);
              }
            }
          }
          if (Integer.parseInt(nums) + tknum > 0)
          {
            ((JSONObject)list.get(i)).put("num", Integer.valueOf(Integer.parseInt(nums) + tknum));
            ((JSONObject)list.get(i)).put("subtotal", new BigDecimal(((JSONObject)list.get(i)).getString("unitprice")).multiply(new BigDecimal(((JSONObject)list.get(i)).getString("num"))).add(money2));
            String bat = (String)((JSONObject)list.get(i)).get("batchno");
            List<JSONObject> ykzzDrugsReturnList = this.ykzzDrugsInService.findCostOrderDetailReturnBySeqid((String)((JSONObject)list.get(i)).get("seq_id"));
            
            int ret = 0;
            for (JSONObject jsonObject : ykzzDrugsReturnList) {
              if ((jsonObject != null) && (jsonObject.size() > 0))
              {
                String seqid = (String)jsonObject.get("seqid");
                KqdsCostorderDetail costorderDetail = this.ykzzDrugsInService.findCostOrderDetailBySeqid(seqid);
                BigDecimal unitprice = costorderDetail.getUnitprice();
                int drugsnum = Integer.parseInt((String)jsonObject.get("returndrugsnum"));
                unitprice1 = unitprice1.add(unitprice.multiply(new BigDecimal(drugsnum)));
                ret += drugsnum;
              }
            }
            int a = ((Integer)((JSONObject)list.get(i)).get("num")).intValue();
            if (a - ret > 0)
            {
              Object orderNo = ((JSONObject)list.get(i)).get("itemno");
              String order_no = orderNo.toString();
              
              List<YkzzDrugsInDetail> yk = this.ykzzDrugsInService.findBatchnumByOrderno1(order_no);
              String id = "";
              for (int j = 0; j < yk.size(); j++)
              {
                String batchnum = ((YkzzDrugsInDetail)yk.get(j)).getBatchnum();
                if ((batchnum != null) && (!batchnum.equals("")) && 
                  (batchnum.equals(bat))) {
                  id = ((YkzzDrugsInDetail)yk.get(j)).getId();
                }
              }
              ((JSONObject)list.get(i)).put("subtotal", ((BigDecimal)((JSONObject)list.get(i)).get("subtotal")).subtract(unitprice1).toString());
              ((JSONObject)list.get(i)).put("num", Integer.valueOf(a - ret));
              ((JSONObject)list.get(i)).put("id", JSON.toJSON(id));
              ((JSONObject)list.get(i)).put("costno", costno);
              list1.add((JSONObject)list.get(i));
            }
          }
        }
        YZUtility.RETURN_LIST(list1, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/grantDrugs.act"})
  public void grantDrugs(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = ChainUtil.getCurrentOrganization(request);
    String costno = request.getParameter("costno");
    
    String[] batchnoNum = request.getParameterValues("selectBatchnumAll");
    
    String[] seqId = request.getParameterValues("selectIdAll");
    
    String[] costseqIdArr = request.getParameterValues("costseqIdArr");
    try
    {
      if ((YZUtility.isNullorEmpty(costno)) || (costno.equals("")) || (seqId == null) || (seqId[0].equals("")) || (costseqIdArr == null) || (costseqIdArr[0].equals("")) || (batchnoNum == null) || (batchnoNum[0].equals(""))) {
        throw new Exception("请选择药品批号！");
      }
      this.ykzzDrugsInService.grantDrugs(organization, costno, batchnoNum, seqId, costseqIdArr, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/returnDrugs.act"})
  public void returnDrugs(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String batchnoNum = request.getParameter("selectBatchnumAll");
    String seqId = request.getParameter("selectIdAll");
    String costseqIdArr = request.getParameter("costseqIdArr");
    String outnum = request.getParameter("outnum");
    
    YZPerson person = SessionUtil.getLoginPerson(request);
    try
    {
      this.ykzzDrugsInService.returnDrugs(batchnoNum, seqId, costseqIdArr, outnum, person);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/checkCurrentIsAdmin.act"})
  public void checkCurrentIsAdmin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    try
    {
      List<JSONObject> adminObj = this.ykzzDrugsInService.findDrugsInAdmin(request);
      String result = "false";
      JSONObject jo = new JSONObject();
      if ((adminObj != null) && (adminObj.size() >= 1))
      {
        String drugsAdmin = ((JSONObject)adminObj.get(0)).getString("para_value");
        jo.put("adminName", drugsAdmin);
        if (drugsAdmin.contains(person.getUserId())) {
          result = "true";
        } else {
          result = "false";
        }
      }
      else
      {
        result = "false";
      }
      jo.put("result", result);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/findBatchnumByOrderno.act"})
  public String findBatchnumByOrderno(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String order_no = request.getParameter("order_no");
    try
    {
      List<YkzzDrugsInDetail> list = this.ykzzDrugsInService.findBatchnumByOrderno(order_no);
      JSONObject json = new JSONObject();
      json.put("batchnum", list);
      YZUtility.DEAL_SUCCESS(json, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findYkzzDrugsInDatailByInDetail.act"})
  public String findYkzzDrugsInDetailByInDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String inDetail = request.getParameter("id");
    try
    {
      YkzzDrugsInDetail ykzzIndetail = this.ykzzDrugsInService.findYkzzDrugsInDatailByInDetail(inDetail);
      JSONObject json = new JSONObject();
      json.put("data", ykzzIndetail);
      YZUtility.DEAL_SUCCESS(json, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/toYkzzDrugsInDetailByOrderno.act"})
  public ModelAndView toYkzzDrugsInDetailByOrderno(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String orderno = request.getParameter("orderno");
    List<JSONObject> list = this.ykzzDrugsInService.findYkzzDrugsInDetailByOrderno(orderno);
    String batchnum = "";
    int batchnums = 0;
    List<JSONObject> list1 = new ArrayList();
    for (JSONObject jsonObject : list) {
      if ((jsonObject.getString("batchnum") != null) && (!jsonObject.getString("batchnum").equals("")))
      {
        String outnum = "0";
        String batchnumSaveOutNum = "0";
        if (!batchnum.equals(jsonObject.getString("batchnum")))
        {
          outnum = this.ykzzDrugsOutService.findOutNumByOrderno(orderno, jsonObject.getString("batchnum"));
          batchnumSaveOutNum = this.ykzzDrugsOutService.findBatchnumSaveOutNumsByOrdernoAndBatchnum(orderno, jsonObject.getString("batchnum"));
          JSONObject json = new JSONObject();
          json.put("barchno", jsonObject.getString("batchnum"));
          json.put("batchoutnum", Integer.valueOf(Integer.parseInt(outnum) + Integer.parseInt(batchnumSaveOutNum)));
          list1.add(json);
          jsonObject.put("outnum", Integer.valueOf(jsonObject.getInt("quantity") - jsonObject.getInt("batchnonum")));
        }
        else
        {
          jsonObject.put("outnum", Integer.valueOf(0));
        }
        batchnum = jsonObject.getString("batchnum");
        batchnums += Integer.parseInt(outnum) + Integer.parseInt(batchnumSaveOutNum);
      }
    }
    String outnums = this.ykzzDrugsOutService.findOutNumsByAll();
    String batchnumSaveOutNums = this.ykzzDrugsOutService.findOutNumsByBatchnumSave();
    ModelAndView mv = new ModelAndView();
    mv.addObject("list", list);
    mv.addObject("batchlist", list1);
    mv.addObject("batchnums", batchnums);
    mv.addObject("outnums", Integer.parseInt(outnums) + Integer.parseInt(batchnumSaveOutNums));
    mv.setViewName("/hudh/ykzz/drugsIn/drugs_detail.jsp");
    return mv;
  }
}
