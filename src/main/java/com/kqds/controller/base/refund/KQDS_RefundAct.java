package com.kqds.controller.base.refund;

import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsHzLabelAssociated;
import com.kqds.entity.base.KqdsIntegralRecord;
import com.kqds.entity.base.KqdsLabel;
import com.kqds.entity.base.KqdsPaycost;
import com.kqds.entity.base.KqdsRefund;
import com.kqds.entity.base.KqdsRefundDetail;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzLabel.KQDS_HzLabelAssociatedLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.base.refund.KQDS_RefundLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
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
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_RefundAct"})
public class KQDS_RefundAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_RefundAct.class);
  
  @Autowired
  private KQDS_RefundLogic logic;
  
  @Autowired
  private KQDS_CostOrderLogic cologic;
  
  @Autowired
  private KQDS_CostOrder_DetailLogic costorderlogic;
  
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private KQDS_UserDocumentLogic userDocumentlogic;
  
  @Autowired
  private KQDS_hz_labelLogic hzLabelLogic;
  
  @Autowired
  private KQDS_HzLabelAssociatedLogic hzLabelAssociatedLogic;
  
  @Autowired
  private KQDS_REGLogic regLogic;
  
  @RequestMapping({"/toCost_TkIndex.act"})
  public ModelAndView toCost_TkIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/coatOrder/cost_tk_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCost_TkList.act"})
  public ModelAndView toCost_TkList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/coatOrder/cost_tklist.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTkSq.act"})
  public ModelAndView toTkSq(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/coatOrder/cost_tk_sq.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTkSqEdit.act"})
  public ModelAndView toTkSqEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String refundId = request.getParameter("refundId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("refundId", refundId);
    mv.setViewName("/kqdsFront/coatOrder/cost_tk_sq_edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTkSqDetail.act"})
  public ModelAndView toTkSqDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String refundId = request.getParameter("refundId");
    String status = request.getParameter("status");
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    ModelAndView mv = new ModelAndView();
    mv.addObject("refundId", refundId);
    mv.addObject("status", status);
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.setViewName("/kqdsFront/coatOrder/cost_tk_sq_detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsRefund dp = new KqdsRefund();
      BeanUtils.populate(dp, request.getParameterMap());
      String costno = request.getParameter("costno");
      KqdsCostorder tkorder = (KqdsCostorder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
      String uuid = YZUtility.getUUID();
      dp.setSeqId(uuid);
      dp.setCreatetime(YZUtility.getCurDateTimeStr());
      dp.setCreateuser(person.getSeqId());
      dp.setUsercode(tkorder.getUsercode());
      dp.setCostno(tkorder.getCostno());
      dp.setTotalcost(tkorder.getTotalcost());
      dp.setVoidmoney(tkorder.getVoidmoney());
      dp.setShouldmoney(tkorder.getShouldmoney());
      dp.setArrearmoney(tkorder.getArrearmoney());
      dp.setActualmoney(tkorder.getActualmoney());
      dp.setDiscountmoney(tkorder.getDiscountmoney());
      dp.setDoctor(tkorder.getDoctor());
      dp.setNurse(tkorder.getNurse());
      dp.setTechperson(tkorder.getTechperson());
      dp.setStatus(Integer.valueOf(1));
      dp.setRemark(tkorder.getRemark());
      dp.setUsername(tkorder.getUsername());
      dp.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.logic.saveSingleUUID(TableNameUtil.KQDS_REFUND, dp);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_REFUND, dp, dp.getUsercode(), TableNameUtil.KQDS_REFUND, request);
      List<JSONObject> personlist = new ArrayList<>();
      personlist = this.personLogic.getAllShowZjlPerson(ChainUtil.getCurrentOrganization(request), request);
      for (int i = 0; i < personlist.size(); i++)
        PushUtil.saveTx4NewTuiFei(personlist.get(i), dp, request); 
      String listdata = request.getParameter("listDetail");
      JSONArray jArray = JSONArray.fromObject(listdata);
      Collection collection = JSONArray.toCollection(jArray, KqdsRefundDetail.class);
      Iterator<KqdsRefundDetail> it = collection.iterator();
      KqdsRefundDetail detail = new KqdsRefundDetail();
      while (it.hasNext()) {
        detail = it.next();
        KqdsCostorderDetail orderDetail = (KqdsCostorderDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail.getOrderdetailno());
        detail.setSeqId(YZUtility.getUUID());
        detail.setRefundid(dp.getSeqId());
        detail.setCreatetime(orderDetail.getCreatetime());
        detail.setCreateuser(orderDetail.getCreatetime());
        detail.setUsercode(orderDetail.getUsercode());
        detail.setItemno(orderDetail.getItemno());
        detail.setItemname(orderDetail.getItemname());
        detail.setUnit(orderDetail.getUnit());
        detail.setUnitprice(orderDetail.getUnitprice());
        detail.setNum(Integer.valueOf(Integer.parseInt(orderDetail.getNum())));
        detail.setDiscount(orderDetail.getDiscount());
        detail.setSubtotal(orderDetail.getSubtotal());
        detail.setArrearmoney(orderDetail.getArrearmoney());
        detail.setPaymoney(orderDetail.getPaymoney());
        detail.setVoidmoney(orderDetail.getVoidmoney());
        detail.setAskperson(orderDetail.getAskperson());
        detail.setDoctor(orderDetail.getDoctor());
        detail.setCostno(orderDetail.getCostno());
        detail.setQfbh(orderDetail.getQfbh());
        detail.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, detail);
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("refundid", dp.getSeqId());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateTk.act"})
  public String updateTk(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String refundId = request.getParameter("refundId");
      String tkze = request.getParameter("tkze");
      KqdsRefund tkorder = (KqdsRefund)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND, refundId);
      if (ConstUtil.REFUND_STATUS_4 == tkorder.getStatus().intValue())
        throw new Exception("退款单已退费，操作无效！"); 
      tkorder.setTkze(new BigDecimal(tkze));
      tkorder.setStatus(Integer.valueOf(1));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_REFUND, tkorder);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_REFUND, tkorder, tkorder.getUsercode(), TableNameUtil.KQDS_REFUND, request);
      List<JSONObject> personlist = new ArrayList<>();
      personlist = this.personLogic.getAllShowZjlPerson(ChainUtil.getCurrentOrganization(request), request);
      for (int i = 0; i < personlist.size(); i++)
        PushUtil.saveTx4NewTuiFei(personlist.get(i), tkorder, request); 
      String listdata = request.getParameter("listDetail");
      JSONArray jArray = JSONArray.fromObject(listdata);
      Collection collection = JSONArray.toCollection(jArray, KqdsRefundDetail.class);
      Iterator<KqdsRefundDetail> it = collection.iterator();
      KqdsRefundDetail detail = new KqdsRefundDetail();
      while (it.hasNext()) {
        detail = it.next();
        KqdsRefundDetail orderDetail = (KqdsRefundDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, detail.getSeqId());
        orderDetail.setTknum(detail.getTknum());
        orderDetail.setTkmoney(detail.getTkmoney());
        orderDetail.setRemark(detail.getRemark());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, orderDetail);
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("refundid", tkorder.getSeqId());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/yzTuiKuanJF.act"})
  public String yzTuiKuanJF(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      boolean flagJF = true;
      BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, "COST_INTEGRAL"));
      if (KqdsBigDecimal.compareTo(costIntegral, BigDecimal.ZERO) > 0) {
        String ssmoney = request.getParameter("ssmoney");
        String usercode = request.getParameter("usercode");
        Map<String, String> map2 = new HashMap<>();
        map2.put("usercode", usercode);
        List<KqdsUserdocument> userlist = (List<KqdsUserdocument>)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
        if (userlist == null || userlist.size() == 0)
          throw new Exception("患者不存在！"); 
        BigDecimal ssmoneybig = BigDecimal.ZERO;
        if (!YZUtility.isNullorEmpty(ssmoney))
          ssmoneybig = new BigDecimal(ssmoney); 
        KqdsUserdocument user = userlist.get(0);
        BigDecimal integral = ssmoneybig.divide(costIntegral, 0, RoundingMode.DOWN);
        if (KqdsBigDecimal.compareTo(user.getIntegral(), integral) < 0)
          flagJF = false; 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("dataJF", Boolean.valueOf(flagJF));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  @RequestMapping({"/editState.act"})
  public String editState(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String usercode = request.getParameter("usercode");
      String username = request.getParameter("username");
      String access = "1";
      KqdsRefund dp = new KqdsRefund();
      BeanUtils.populate(dp, request.getParameterMap());
      Map<String, String> map = new HashMap<>();
      map.put("seq_id", dp.getSeqId());
      List<KqdsRefund> en = (List<KqdsRefund>)this.logic.loadList(TableNameUtil.KQDS_REFUND, map);
      if (en == null)
        throw new Exception("退款单不存在，操作无效！"); 
      if (ConstUtil.REFUND_STATUS_4 == dp.getStatus().intValue()) {
        if (ConstUtil.REFUND_STATUS_4 == ((KqdsRefund)en.get(0)).getStatus().intValue())
          throw new Exception("退款单已退费，操作无效！"); 
        String listdata = request.getParameter("listDetail");
        updateCostDetailType(listdata, request);
        String newcostno = addOrder(((KqdsRefund)en.get(0)).getCostno(), ((KqdsRefund)en.get(0)).getTkze(), dp.getSeqId(), person, request);
        String refundid = ((KqdsRefund)en.get(0)).getSeqId();
        addOrderDetail(newcostno, refundid, ((KqdsRefund)en.get(0)).getTkze(), person, request);
        addPayOrder(newcostno, ((KqdsRefund)en.get(0)).getCostno(), dp.getSeqId(), ((KqdsRefund)en.get(0)).getTkze(), person, request);
        ((KqdsRefund)en.get(0)).setTktime(YZUtility.getCurDateTimeStr());
        ((KqdsRefund)en.get(0)).setTkuser(person.getSeqId());
        PushUtil.saveTx4TuiFeiConfirm(en.get(0), person, request);
        setIntegralMoney(newcostno, ((KqdsRefund)en.get(0)).getUsercode(), refundid, person.getSeqId(), request);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.CONFIRM_REFUND, BcjlUtil.KQDS_REFUND, en.get(0), ((KqdsRefund)en.get(0)).getUsercode(), TableNameUtil.KQDS_REFUND, request);
        Map<String, String> map1 = new HashMap<>();
        if (!YZUtility.isNullorEmpty(usercode))
          map1.put("usercode", usercode); 
        if (!YZUtility.isNullorEmpty(access) && !access.equals("1"))
          map1.put("access", access); 
        String visualstaff = SessionUtil.getVisualstaff(request);
        List<JSONObject> list = this.cologic.selectWithPageNopageForLabel(TableNameUtil.KQDS_COSTORDER, map1, visualstaff);
        String itemname = "";
        int nums = 0;
        BigDecimal ys = new BigDecimal("0");
        List<JSONObject> itemlist1 = new ArrayList<>();
        List<JSONObject> itemlist2 = new ArrayList<>();
        for (JSONObject jsonObject : list) {
          if (jsonObject.getInt("status") == 2 && jsonObject.getInt("isyjjitem") == 0)
            ys = ys.add(new BigDecimal(jsonObject.getString("shouldmoney"))); 
          String costno = jsonObject.getString("costno");
          map1.put("costno", costno);
          List<JSONObject> list1 = this.costorderlogic.selectWithPageLzjlForLabel(TableNameUtil.KQDS_COSTORDER_DETAIL, map1);
          for (JSONObject jsonObject2 : list1) {
            if (jsonObject2.getString("itemname").endsWith("种植体") && jsonObject2.getString("unit").equals("颗")) {
              if (jsonObject2.getInt("num") > 0) {
                itemlist1.add(jsonObject2);
                continue;
              } 
              if (jsonObject2.getInt("num") < 0)
                itemlist2.add(jsonObject2); 
            } 
          } 
        } 
        List<JSONObject> itemlist3 = new ArrayList<>();
        List<JSONObject> itemlist4 = new ArrayList<>();
        if (itemlist1.size() > 0) {
          StringBuffer str = new StringBuffer();
          if (itemlist1.size() == 1) {
            itemlist3.addAll(itemlist1);
          } else {
            for (JSONObject jsonObject2 : itemlist1) {
              if (!str.toString().contains(jsonObject2.getString("itemname"))) {
                int m = 0;
                for (int i = 0; i < itemlist1.size(); i++) {
                  if (!jsonObject2.getString("seqid").equals(((JSONObject)itemlist1.get(i)).getString("seqid")) && jsonObject2.getString("itemname").equals(((JSONObject)itemlist1.get(i)).getString("itemname"))) {
                    jsonObject2.put("num", (new StringBuilder(String.valueOf(jsonObject2.getInt("num") + ((JSONObject)itemlist1.get(i)).getInt("num")))).toString());
                    m = 1;
                  } 
                } 
                if (m == 1) {
                  itemlist3.add(jsonObject2);
                  str.append(String.valueOf(jsonObject2.getString("itemname")) + ",");
                } 
              } 
            } 
          } 
          if (str.length() > 0) {
            for (JSONObject jsonObject2 : itemlist1) {
              if (!str.toString().contains(jsonObject2.getString("itemname")))
                itemlist3.add(jsonObject2); 
            } 
          } else if (str.length() == 0 && itemlist1.size() > 1) {
            itemlist3.addAll(itemlist1);
          } 
        } 
        if (itemlist2.size() > 0) {
          StringBuffer str = new StringBuffer();
          if (itemlist2.size() == 1) {
            itemlist4.addAll(itemlist2);
          } else {
            for (JSONObject jsonObject2 : itemlist2) {
              if (!str.toString().contains(jsonObject2.getString("itemname"))) {
                int m = 0;
                for (int i = 0; i < itemlist2.size(); i++) {
                  if (!jsonObject2.getString("seqid").equals(((JSONObject)itemlist2.get(i)).getString("seqid")) && jsonObject2.getString("itemname").equals(((JSONObject)itemlist2.get(i)).getString("itemname"))) {
                    jsonObject2.put("num", (new StringBuilder(String.valueOf(jsonObject2.getInt("num") + ((JSONObject)itemlist2.get(i)).getInt("num")))).toString());
                    m = 1;
                  } 
                } 
                if (m == 1) {
                  itemlist4.add(jsonObject2);
                  str.append(String.valueOf(jsonObject2.getString("itemname")) + ",");
                } 
              } 
            } 
          } 
          if (str.length() > 0) {
            for (JSONObject jsonObject2 : itemlist2) {
              if (!str.toString().contains(jsonObject2.getString("itemname")))
                itemlist4.add(jsonObject2); 
            } 
          } else if (str.length() == 0 && itemlist2.size() > 1) {
            itemlist4.addAll(itemlist2);
          } 
        } 
        if (itemlist3.size() > 0 && itemlist4.size() > 0) {
          StringBuffer str = new StringBuffer();
          for (JSONObject jsonObject3 : itemlist3) {
            for (JSONObject jsonObject4 : itemlist4) {
              if (jsonObject3.getString("itemname").equals(jsonObject4.getString("itemname"))) {
                nums += jsonObject3.getInt("num") + jsonObject4.getInt("num");
                if (jsonObject3.getInt("num") + jsonObject4.getInt("num") > 0) {
                  str.append(String.valueOf(jsonObject3.getString("itemname")) + ",");
                  if (itemname.equals("")) {
                    itemname = jsonObject3.getString("itemname");
                    continue;
                  } 
                  if (!itemname.equals(jsonObject3.getString("itemname")))
                    itemname = String.valueOf(itemname) + "+" + jsonObject3.getString("itemname"); 
                  continue;
                } 
                if (jsonObject3.getInt("num") + jsonObject4.getInt("num") == 0)
                  jsonObject3.put("num", "0"); 
              } 
            } 
          } 
          if (str.length() > 0) {
            for (JSONObject jsonObject3 : itemlist3) {
              if (!str.toString().contains(jsonObject3.getString("itemname")) && !jsonObject3.getString("num").equals("0")) {
                nums += jsonObject3.getInt("num");
                if (itemname.equals("")) {
                  itemname = jsonObject3.getString("itemname");
                  continue;
                } 
                if (!itemname.equals(jsonObject3.getString("itemname")))
                  itemname = String.valueOf(itemname) + "+" + jsonObject3.getString("itemname"); 
              } 
            } 
          } else if (str.length() == 0 && itemlist3.size() > 1) {
            for (JSONObject jsonObject3 : itemlist3) {
              if (jsonObject3.getInt("num") > 0) {
                nums += jsonObject3.getInt("num");
                if (itemname.equals("")) {
                  itemname = jsonObject3.getString("itemname");
                  continue;
                } 
                if (!itemname.equals(jsonObject3.getString("itemname")))
                  itemname = String.valueOf(itemname) + "+" + jsonObject3.getString("itemname"); 
              } 
            } 
          } 
        } else if (itemlist3.size() > 0 && itemlist4.size() == 0) {
          for (JSONObject jsonObject3 : itemlist3) {
            nums += jsonObject3.getInt("num");
            if (itemname.equals("")) {
              itemname = jsonObject3.getString("itemname");
              continue;
            } 
            if (!itemname.equals(jsonObject3.getString("itemname")))
              itemname = String.valueOf(itemname) + "+" + jsonObject3.getString("itemname"); 
          } 
        } 
        if (ys.compareTo(new BigDecimal("0")) == 1 || ys.compareTo(new BigDecimal("0")) == 0) {
          map1.put("status", "4");
          int status = 4;
          String hzLabelAssciatedSeqId = this.hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map1);
          String labelname = "";
          String parentName = "消费区间";
          KqdsLabel kqdsLabel = this.hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
          if (!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)) {
            labelname = this.hzLabelAssociatedLogic.selectKqdsHzLabelBySeqId(hzLabelAssciatedSeqId);
            String start = "";
            String end = "";
            if (!YZUtility.isNullorEmpty(labelname))
              if (labelname.endsWith("以下") && labelname.length() == 6) {
                start = labelname.substring(0, 4);
              } else if (labelname.length() == 11) {
                start = labelname.substring(0, 4);
                end = labelname.substring(5, 10);
              } else if (labelname.length() == 12) {
                start = labelname.substring(0, 5);
                end = labelname.substring(6, 11);
              } else if (labelname.length() == 6) {
                start = labelname.substring(0, 2);
                end = labelname.substring(3, 5);
              } else {
                end = "200000.9";
              }  
            if (!start.equals("") && !end.equals("")) {
              if (ys.compareTo(new BigDecimal(start)) == -1 || ys.compareTo(new BigDecimal(end)) == 1) {
                String ys1 = ys.toString();
                labelname = cloudOfTags(ys1, null, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
              } 
            } else if (!start.equals("") && end.equals("")) {
              if (ys.compareTo(new BigDecimal(start)) == 1) {
                String ys1 = ys.toString();
                labelname = cloudOfTags(ys1, null, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
              } 
            } else if (start.equals("") && !end.equals("")) {
              String ys1 = ys.toString();
              labelname = cloudOfTags(ys1, null, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
            } else {
              String ys1 = ys.toString();
              labelname = cloudOfTags(ys1, null, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
            } 
          } else {
            String ys1 = ys.toString();
            labelname = cloudOfTags(ys1, null, null, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
          } 
        } 
        if (!YZUtility.isNullorEmpty(itemname) && nums > 0) {
          map1.put("status", "3");
          int status = 3;
          String xfxmLabelname = "";
          String hzLabelAssciatedSeqId = this.hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map1);
          String parentName = "消费项目(种植体品牌)";
          KqdsLabel kqdsLabel = this.hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
          if (!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)) {
            xfxmLabelname = this.hzLabelAssociatedLogic.selectKqdsHzLabelBySeqId(hzLabelAssciatedSeqId);
            if (xfxmLabelname != null && !xfxmLabelname.equals(itemname))
              xfxmLabelname = cloudOfTags(null, itemname, hzLabelAssciatedSeqId, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request); 
          } else {
            xfxmLabelname = cloudOfTags(null, itemname, null, person, usercode, username, status, kqdsLabel.getSeqId(), parentName, kqdsLabel.getParentId(), kqdsLabel.getParentName(), request);
          } 
        } else {
          String parentName = "消费项目(种植体品牌)";
          KqdsLabel kqdsLabel = this.hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
          map.put("userCode", usercode);
          map.put("labelTwoId", kqdsLabel.getSeqId());
          this.userDocumentlogic.deleteLabelByUsercode(map);
          KqdsHzLabelAssociated kqdsHzLabelAssociated = new KqdsHzLabelAssociated();
          kqdsHzLabelAssociated.setModifier(person.getSeqId());
          kqdsHzLabelAssociated.setUpdateTime(YZUtility.getCurDateTimeStr());
          kqdsHzLabelAssociated.setUsercode(usercode);
          kqdsHzLabelAssociated.setStatus(3);
          int i = this.hzLabelAssociatedLogic.updateKqdsHzLabelAssociatedByStatus(kqdsHzLabelAssociated);
        } 
      } 
      if (ConstUtil.REFUND_STATUS_2 == dp.getStatus().intValue() || ConstUtil.REFUND_STATUS_3 == dp.getStatus().intValue()) {
        if (ConstUtil.REFUND_STATUS_2 == ((KqdsRefund)en.get(0)).getStatus().intValue() || ConstUtil.REFUND_STATUS_3 == ((KqdsRefund)en.get(0)).getStatus().intValue())
          throw new Exception("退款单已审核，操作无效！"); 
        if (2 == dp.getStatus().intValue()) {
          Map<Object, Object> map2 = new HashMap<>();
          map2.put("refundid", dp.getSeqId());
          List<KqdsRefundDetail> list = (List<KqdsRefundDetail>)this.logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map2);
          for (KqdsRefundDetail detail : list) {
            JSONObject obj = this.costorderlogic.checkTf(detail.getUsercode(), detail.getItemno(), detail.getQfbh(), detail.getOrderdetailno());
            BigDecimal ysf = new BigDecimal(obj.getString("paymoney"));
            JSONObject objrefund = this.costorderlogic.checkTfRefund(detail.getUsercode(), detail.getItemno(), detail.getQfbh(), detail.getOrderdetailno());
            BigDecimal agreeTk = new BigDecimal(objrefund.getString("tkmoney"));
            BigDecimal aowTk = detail.getTkmoney();
            BigDecimal tked = KqdsBigDecimal.add(agreeTk, aowTk);
            if (KqdsBigDecimal.compareTo(ysf, tked) < 0)
              throw new Exception("该收费项目的退款金额大于缴费金额！"); 
          } 
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.AGREE, BcjlUtil.KQDS_REFUND, dp, dp.getUsercode(), TableNameUtil.KQDS_REFUND, request);
        } 
        if (3 == dp.getStatus().intValue())
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DISAGREE, BcjlUtil.KQDS_REFUND, dp, dp.getUsercode(), TableNameUtil.KQDS_REFUND, request); 
        ((KqdsRefund)en.get(0)).setShtime(YZUtility.getCurDateTimeStr());
        ((KqdsRefund)en.get(0)).setShuser(person.getSeqId());
        ((KqdsRefund)en.get(0)).setRemark(dp.getRemark());
        List<JSONObject> personlist = this.personLogic.getAllShowfeiPerson(ChainUtil.getCurrentOrganization(request), request);
        for (int i = 0; i < personlist.size(); i++)
          PushUtil.saveTx4TuiFeiSH(personlist.get(i), person, request); 
      } 
      ((KqdsRefund)en.get(0)).setStatus(dp.getStatus());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_REFUND, en.get(0));
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  private String cloudOfTags(String ys, String itemname, String hzLabelAssciatedSeqId, YZPerson person, String usercode, String username, int status, String parentId, String parentName, String labelOneId, String labelOneName, HttpServletRequest request) throws Exception {
    String labelname = "";
    try {
      if (!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)) {
        KqdsHzLabelAssociated kqdsHzLabelAssociated = new KqdsHzLabelAssociated();
        kqdsHzLabelAssociated.setSeqId(hzLabelAssciatedSeqId);
        kqdsHzLabelAssociated.setModifier(person.getSeqId());
        kqdsHzLabelAssociated.setUpdateTime(YZUtility.getCurDateTimeStr());
        int i = this.hzLabelAssociatedLogic.updateKqdsHzLabelAssociated(kqdsHzLabelAssociated);
      } 
      if (!YZUtility.isNullorEmpty(ys)) {
        double d = Double.valueOf(ys).doubleValue();
        if (d >= 0.0D && d <= 5000.9D) {
          labelname = "5000以下";
        } else if (d >= 5001.0D && d <= 20000.9D) {
          labelname = "5001-20000元";
        } else if (d >= 20001.0D && d <= 40000.9D) {
          labelname = "20001-40000元";
        } else if (d >= 40001.0D && d <= 60000.9D) {
          labelname = "40001-60000元";
        } else if (d >= 60001.0D && d <= 80000.9D) {
          labelname = "60001-80000元";
        } else if (d >= 80001.0D && d <= 100000.9D) {
          labelname = "80001-100000元";
        } else if (d >= 100001.0D && d <= 150000.9D) {
          labelname = "10-15万";
        } else if (d >= 150001.0D && d <= 200000.9D) {
          labelname = "15-20万";
        } else if (d >= 200001.0D) {
          labelname = "20万以上";
        } 
      } else if (!YZUtility.isNullorEmpty(itemname)) {
        labelname = itemname;
      } 
      if (YZUtility.isNullorEmpty(itemname) && YZUtility.isNullorEmpty(ys)) {
        Map<String, String> map = new HashMap<>();
        map.put("userCode", usercode);
        map.put("labelTwoId", "bd7c762b-9465-457c-b9b1-b085fc2a27a5");
        this.userDocumentlogic.deleteLabelByUsercode(map);
      } 
      if (labelname != null && !labelname.equals("")) {
        Map<String, String> map1 = new HashMap<>();
        if (labelname != null && !labelname.equals(""))
          map1.put("leveLabel", labelname); 
        if (!parentId.equals("") && parentId != null)
          map1.put("parentId", parentId); 
        String labelId = this.hzLabelLogic.selectKqdsHzLabelByLeveLabel(map1);
        if (YZUtility.isNullorEmpty(labelId)) {
          KqdsLabel kqdsHzLabel = new KqdsLabel();
          String seqid = YZUtility.getUUID();
          labelId = seqid;
          kqdsHzLabel.setSeqId(seqid);
          kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
          kqdsHzLabel.setCreateUser(person.getSeqId());
          kqdsHzLabel.setLeveLabel(labelname);
          kqdsHzLabel.setParentId(parentId);
          kqdsHzLabel.setParentName(parentName);
          kqdsHzLabel.setStatus("0");
          kqdsHzLabel.setRemark("三级");
          int j = this.hzLabelLogic.insertKqdsHzLabel(kqdsHzLabel);
          KqdsHzLabelAssociated kqdsHzLabelAssociated1 = new KqdsHzLabelAssociated();
          hzLabelAssciatedSeqId = YZUtility.getUUID();
          kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
          kqdsHzLabelAssociated1.setLabeId(seqid);
          kqdsHzLabelAssociated1.setUsercode(usercode);
          kqdsHzLabelAssociated1.setUserName(username);
          kqdsHzLabelAssociated1.setCreateTime(YZUtility.getCurDateTimeStr());
          kqdsHzLabelAssociated1.setCreateUser(person.getSeqId());
          kqdsHzLabelAssociated1.setRemark("");
          kqdsHzLabelAssociated1.setStatus(status);
          kqdsHzLabelAssociated1.setIsdelete(0);
          int i = this.hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
        } else {
          KqdsHzLabelAssociated kqdsHzLabelAssociated1 = new KqdsHzLabelAssociated();
          hzLabelAssciatedSeqId = YZUtility.getUUID();
          kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
          kqdsHzLabelAssociated1.setLabeId(labelId);
          kqdsHzLabelAssociated1.setUsercode(usercode);
          kqdsHzLabelAssociated1.setUserName(username);
          kqdsHzLabelAssociated1.setCreateTime(YZUtility.getCurDateTimeStr());
          kqdsHzLabelAssociated1.setCreateUser(person.getSeqId());
          kqdsHzLabelAssociated1.setRemark("");
          kqdsHzLabelAssociated1.setStatus(status);
          kqdsHzLabelAssociated1.setIsdelete(0);
          int i = this.hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
        } 
        Map<String, String> map = new HashMap<>();
        map.put("userCode", usercode);
        map.put("labelTwoId", parentId);
        String threeid = this.userDocumentlogic.selectLabelByUsercode(map);
        this.userDocumentlogic.deleteLabelByUsercode(map);
        kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
        String id = YZUtility.getUUID();
        kPatient.setSeqId(id);
        kPatient.setUserSeqId("");
        kPatient.setUserId(usercode);
        kPatient.setUserName(username);
        kPatient.setLabelOneId(labelOneId);
        kPatient.setLabelOneName(labelOneName);
        kPatient.setLabelTwoId(parentId);
        kPatient.setLabelTwoName(parentName);
        kPatient.setLabelThreeId(labelId);
        kPatient.setLabelThreeName(labelname);
        kPatient.setCreateUser(person.getSeqId());
        kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
        kPatient.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.userDocumentlogic.saveKpatient(kPatient);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_Hz_LabellabeAndPatient, kPatient, usercode, TableNameUtil.KQDS_Hz_LabellabeAndPatient, request);
      } 
    } catch (Exception exception) {}
    return labelname;
  }
  
  private void setIntegralMoney(String costno, String usercode, String refundid, String perId, HttpServletRequest request) throws Exception {
    Map<String, String> map2 = new HashMap<>();
    map2.put("usercode", usercode);
    List<KqdsUserdocument> userlist = (List<KqdsUserdocument>)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
    if (userlist == null)
      throw new Exception("患者不存在！"); 
    KqdsUserdocument u = userlist.get(0);
    BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, "COST_INTEGRAL"));
    Map<String, String> map = new HashMap<>();
    map.put("refundid", refundid);
    BigDecimal ssmoney = this.userLogic.getSsjeOne(costno);
    u.setTotalpay(u.getTotalpay().add(ssmoney));
    if (KqdsBigDecimal.compareTo(costIntegral, BigDecimal.ZERO) > 0) {
      BigDecimal integral = ssmoney.divide(costIntegral, 0, RoundingMode.DOWN);
      u.setIntegral(u.getIntegral().add(integral));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
      String jfjs = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "退费减少");
      String tkjs = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "退费返回");
      if (YZUtility.isNullorEmpty(jfjs) || YZUtility.isNullorEmpty(jfjs))
        throw new Exception("积分类型不存在！"); 
      KqdsIntegralRecord record = new KqdsIntegralRecord();
      record.setSeqId(UUID.randomUUID().toString());
      record.setCreatetime(YZUtility.getCurDateTimeStr());
      record.setOrganization(ChainUtil.getCurrentOrganization(request));
      record.setUsercode(u.getUsercode());
      record.setJfmoney(integral);
      record.setJftype(jfjs);
      record.setSyjfmoney(u.getIntegral());
      record.setCreateuser(perId);
      this.logic.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);
      List<KqdsRefundDetail> list = (List<KqdsRefundDetail>)this.logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map);
      BigDecimal jfje = BigDecimal.ZERO;
      for (KqdsRefundDetail detail : list)
        jfje = jfje.add(detail.getPayintegral()); 
      record = new KqdsIntegralRecord();
      record.setSeqId(UUID.randomUUID().toString());
      record.setCreatetime(YZUtility.getCurDateTimeStr());
      record.setOrganization(ChainUtil.getCurrentOrganization(request));
      record.setUsercode(u.getUsercode());
      record.setJfmoney(jfje);
      record.setJftype(tkjs);
      record.setSyjfmoney(u.getIntegral().add(jfje));
      record.setCreateuser(perId);
      this.logic.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);
      u.setIntegral(u.getIntegral().add(jfje));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
    } else {
      this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
    } 
  }
  
  @RequestMapping({"/NoselectPage.act"})
  public void updateCostDetailType(String listdata, HttpServletRequest request) throws Exception {
    JSONArray jArray = JSONArray.fromObject(listdata);
    Collection collection = JSONArray.toCollection(jArray, KqdsRefundDetail.class);
    Iterator<KqdsRefundDetail> it = collection.iterator();
    KqdsRefundDetail detail = new KqdsRefundDetail();
    while (it.hasNext()) {
      detail = it.next();
      KqdsRefundDetail refundDetail = (KqdsRefundDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, detail.getSeqId());
      refundDetail.setPayyjj(BigDecimal.ZERO);
      refundDetail.setPayxj(detail.getPayxj());
      refundDetail.setPaybank(detail.getPaybank());
      refundDetail.setPayyb(detail.getPayyb());
      refundDetail.setPaywx(detail.getPaywx());
      refundDetail.setPayzfb(detail.getPayzfb());
      refundDetail.setPaymmd(detail.getPaymmd());
      refundDetail.setPaybdfq(detail.getPaybdfq());
      refundDetail.setPaydjq(detail.getPaydjq());
      refundDetail.setPayintegral(detail.getPayintegral());
      refundDetail.setPayother1(detail.getPayother1());
      refundDetail.setPayother2(detail.getPayother2());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, refundDetail);
    } 
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      KqdsRefund en = (KqdsRefund)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND, seqId);
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
  
  private BigDecimal realQfDetail(String qfbh) throws Exception {
    BigDecimal qf = BigDecimal.ZERO;
    if (!YZUtility.isNullorEmpty(qfbh)) {
      String qfStr = this.costorderlogic.selectRealQf(TableNameUtil.KQDS_COSTORDER_DETAIL, qfbh);
      qf = qf.add(new BigDecimal(qfStr));
    } 
    return qf;
  }
  
  private String addOrder(String costno, BigDecimal tkje, String refundid, YZPerson person, HttpServletRequest request) throws Exception {
    KqdsCostorder tkorder = (KqdsCostorder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
    tkorder.setSeqId(refundid);
    tkorder.setCostno(refundid);
    tkorder.setCreatetime(YZUtility.getCurDateTimeStr());
    tkorder.setSftime(YZUtility.getCurDateTimeStr());
    tkorder.setSfuser(person.getSeqId());
    tkorder.setCreateuser(person.getSeqId());
    BigDecimal qf = BigDecimal.ZERO;
    Map<String, String> map = new HashMap<>();
    map.put("refundid", refundid);
    List<KqdsRefundDetail> list = (List<KqdsRefundDetail>)this.logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map);
    for (KqdsRefundDetail detail : list) {
      KqdsCostorderDetail orderdetail = (KqdsCostorderDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail.getOrderdetailno());
      if (KqdsBigDecimal.compareTo(orderdetail.getY2(), BigDecimal.ZERO) != 0) {
        String qfStr = this.costorderlogic.selectRealQf(TableNameUtil.KQDS_COSTORDER_DETAIL, orderdetail.getQfbh());
        qf = qf.add(new BigDecimal(qfStr));
      } 
    } 
    BigDecimal tkmoney = tkje;
    tkorder.setActualmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
    BigDecimal ysje = KqdsBigDecimal.add(tkmoney, qf);
    tkorder.setShouldmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, ysje));
    tkorder.setVoidmoney(qf);
    tkorder.setY2(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
    tkorder.setArrearmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
    tkorder.setTotalarrmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
    tkorder.setTotalcost(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
    tkorder.setOrganization(ChainUtil.getCurrentOrganization(request));
    this.logic.saveSingleUUID(TableNameUtil.KQDS_COSTORDER, tkorder);
    if (YZUtility.isNotNullOrEmpty(tkorder.getRegno())) {
      KqdsReg reg = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, tkorder.getRegno());
      if (reg == null)
        throw new Exception("数据不存在"); 
      YZPerson per = (YZPerson)this.logic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, reg.getDoctor());
      if (per != null)
        reg.setRegdept(per.getDeptId()); 
      this.logic.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
    } 
    return refundid;
  }
  
  private void addOrderDetail(String newcostno, String refundid, BigDecimal tkje, YZPerson person, HttpServletRequest request) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("refundid", refundid);
    List<KqdsRefundDetail> list = (List<KqdsRefundDetail>)this.logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map);
    for (KqdsRefundDetail detail : list) {
      KqdsCostorderDetail orderdetail = (KqdsCostorderDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail.getOrderdetailno());
      BigDecimal qf = realQfDetail(orderdetail.getQfbh());
      if (detail.getTknum().intValue() == 0) {
        orderdetail.setNum((new StringBuilder(String.valueOf(0 - detail.getNum().intValue()))).toString());
      } else {
        orderdetail.setNum((new StringBuilder(String.valueOf(0 - detail.getTknum().intValue()))).toString());
      } 
      orderdetail.setSeqId(detail.getSeqId());
      orderdetail.setCostno(newcostno);
      orderdetail.setCreatetime(YZUtility.getCurDateTimeStr());
      orderdetail.setCreateuser(person.getSeqId());
      BigDecimal tkmoney = detail.getTkmoney();
      orderdetail.setPaymoney(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
      orderdetail.setPayyjj(BigDecimal.ZERO);
      orderdetail.setPayxj(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayxj()));
      orderdetail.setPaybank(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaybank()));
      orderdetail.setPayyb(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayyb()));
      orderdetail.setPaywx(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaywx()));
      orderdetail.setPayzfb(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayzfb()));
      orderdetail.setPaymmd(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaymmd()));
      orderdetail.setPaybdfq(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaybdfq()));
      orderdetail.setPaydjq(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaydjq()));
      orderdetail.setPayintegral(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayintegral()));
      orderdetail.setPayother1(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayother1()));
      orderdetail.setPayother2(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayother2()));
      orderdetail.setStatus(ConstUtil.COST_DETAIL_STATUS_0);
      orderdetail.setIsqfreal(Integer.valueOf(ConstUtil.QF_STATUS_0));
      orderdetail.setArrearmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
      orderdetail.setVoidmoney(qf);
      orderdetail.setY2(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
      orderdetail.setSubtotal(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
      orderdetail.setIstk(Integer.valueOf(1));
      orderdetail.setCreatetime(YZUtility.getCurDateTimeStr());
      orderdetail.setOrganization(ChainUtil.getCurrentOrganization(request));
      orderdetail.setParentid(detail.getOrderdetailno());
      this.logic.saveSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, orderdetail);
      editQfStatus(orderdetail.getQfbh(), request);
    } 
  }
  
  private void editQfStatus(String qfbh, HttpServletRequest request) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("qfbh", qfbh);
    map.put("isqfreal", "1");
    List<KqdsCostorderDetail> detaillist = (List<KqdsCostorderDetail>)this.logic.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
    for (KqdsCostorderDetail detailnew : detaillist) {
      detailnew.setStatus(ConstUtil.COST_DETAIL_STATUS_0);
      detailnew.setIsqfreal(Integer.valueOf(ConstUtil.QF_STATUS_0));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detailnew);
    } 
  }
  
  private void addPayOrder(String newcostno, String oldcostno, String refundid, BigDecimal tkje, YZPerson person, HttpServletRequest request) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("costno", oldcostno);
    List<KqdsPaycost> listpay = (List<KqdsPaycost>)this.logic.loadList(TableNameUtil.KQDS_PAYCOST, map);
    for (KqdsPaycost detail : listpay) {
      BigDecimal qf = BigDecimal.ZERO;
      Map<String, String> map2 = new HashMap<>();
      map2.put("refundid", refundid);
      List<KqdsRefundDetail> list = (List<KqdsRefundDetail>)this.logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map2);
      for (KqdsRefundDetail rdetail : list) {
        KqdsCostorderDetail orderdetail = (KqdsCostorderDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, rdetail.getOrderdetailno());
        if (!orderdetail.getQfbh().equals("")) {
          String qfStr = this.costorderlogic.selectRealQf(TableNameUtil.KQDS_COSTORDER_DETAIL, orderdetail.getQfbh());
          qf = qf.add(new BigDecimal(qfStr));
        } 
      } 
      String uuid = YZUtility.getUUID();
      detail.setSeqId(uuid);
      detail.setCostno(newcostno);
      detail.setCreatetime(YZUtility.getCurDateTimeStr());
      detail.setCreateuser(person.getSeqId());
      BigDecimal tkmoney = tkje;
      detail.setActualmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
      BigDecimal ysje = KqdsBigDecimal.add(tkmoney, qf);
      detail.setShouldmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, ysje));
      detail.setVoidmoney(qf);
      detail.setArrearmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
      detail.setTotalcost(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
      detail.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.logic.saveSingleUUID(TableNameUtil.KQDS_PAYCOST, detail);
    } 
  }
  
  @RequestMapping({"/selectWithNopage.act"})
  public String selectWithNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      String searchValue = request.getParameter("searchValue");
      String tkstatus = request.getParameter("tkstatus");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(searchValue))
        map.put("searchValue", searchValue); 
      if (!YZUtility.isNullorEmpty(tkstatus))
        map.put("status", tkstatus); 
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = String.valueOf(starttime) + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      } 
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = String.valueOf(endtime) + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      } 
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
        organization = ChainUtil.getCurrentOrganization(request); 
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> list = this.logic.selectWithNopage(TableNameUtil.KQDS_REFUND, map, person, visualstaff, organization);
      if (flag != null && flag.equals("exportTable")) {
        for (JSONObject json : list) {
          String tkze = json.getString("tkze");
          json.put("tkze", "-" + tkze);
        } 
        ExportTable.exportBootStrapTable2Excel("项目退款", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, logger);
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
      KqdsRefund en = (KqdsRefund)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND, seqId);
      if (ConstUtil.REFUND_STATUS_4 == en.getStatus().intValue())
        throw new Exception("该退款单已退款，不能删除！"); 
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_REFUND, seqId);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_REFUND, en, en.getUsercode(), TableNameUtil.KQDS_REFUND, request);
      Map<String, String> map = new HashMap<>();
      map.put("refundid", seqId);
      List<KqdsRefundDetail> list = (List<KqdsRefundDetail>)this.logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map);
      for (KqdsRefundDetail detail : list)
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, detail.getSeqId()); 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
}
