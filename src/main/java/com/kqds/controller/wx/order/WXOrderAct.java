package com.kqds.controller.wx.order;

import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXOrder;
import com.kqds.service.base.netOrder.KQDS_Net_OrderLogic;
import com.kqds.service.wx.core.WXUtilLogic;
import com.kqds.service.wx.order.WXOrderLogic;
import com.kqds.service.wx.receivetext.WXReceiveTextLogic;
import com.kqds.service.wx.user.WXUserDocLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.wx.WXConst;
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
@RequestMapping({"WXOrderAct"})
public class WXOrderAct {
  private static Logger logger = LoggerFactory.getLogger(WXOrderAct.class);
  
  @Autowired
  private WXOrderLogic logic;
  
  @Autowired
  private WXUserDocLogic wxUserLogic;
  
  @Autowired
  private KQDS_Net_OrderLogic netOrderLogic;
  
  @Autowired
  private WXReceiveTextLogic textLogic;
  
  @Autowired
  private WXUtilLogic wxUtilLogic;
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      WXOrder dp = new WXOrder();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String openid = request.getParameter("openid");
      String phonenumber = request.getParameter("phonenumber");
      String orderdate = request.getParameter("orderdate");
      String ordertime = request.getParameter("ordertime");
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(openid))
        throw new Exception("openid不能为空"); 
      if (YZUtility.isNullorEmpty(orderdate))
        throw new Exception("orderdate不能为空"); 
      if (YZUtility.isNullorEmpty(ordertime))
        throw new Exception("ordertime不能为空"); 
      if (YZUtility.isNullorEmpty(organization))
        throw new Exception("organization不能为空"); 
      if (!YZUtility.isNullorEmpty(phonenumber)) {
        int i = this.wxUserLogic.phoneIsExist(phonenumber, openid);
        if (i > 0)
          throw new Exception("该手机号码已被他人使用，请确认输入信息正确!"); 
      } 
      int count = this.logic.countToday(openid, orderdate, request);
      if (count > 0)
        throw new Exception("一天只能预约一次!"); 
      if (YZUtility.isNullorEmpty(seqId)) {
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        this.logic.saveSingleUUID(TableNameUtil.WX_ORDER, dp);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.WX_ORDER, dp, TableNameUtil.WX_ORDER, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/cancel.act"})
  public String cancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      String reason = request.getParameter("reason");
      WXOrder order = (WXOrder)this.logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, seqId);
      if (order == null)
        throw new Exception("预约记录不存在！"); 
      order.setCancelreason(reason);
      order.setCanceltime(YZUtility.getCurDateTimeStr());
      order.setOrderstatus(Integer.valueOf(WXConst.ORDER_STATUS_3));
      this.logic.updateSingleUUID(TableNameUtil.WX_ORDER, order);
      KqdsNetOrder netOrder = this.netOrderLogic.getNetOrderByWXOrderSeqId(seqId);
      if (netOrder != null) {
        netOrder.setIsdelete(Integer.valueOf(1));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, seqId);
      } 
      BcjlUtil.LogBcjl(BcjlUtil.CANCEL, BcjlUtil.WX_ORDER, order, TableNameUtil.WX_ORDER, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/auditor.act"})
  public String auditor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String seqId = request.getParameter("seqId");
      String auditorremark = request.getParameter("auditorremark");
      String orderstatus = request.getParameter("orderstatus");
      if (YZUtility.isNullorEmpty(auditorremark))
        throw new Exception("auditorremark不能为空"); 
      if (YZUtility.isNullorEmpty(orderstatus))
        throw new Exception("orderstatus不能为空"); 
      WXOrder order = (WXOrder)this.logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, seqId);
      if (order == null)
        throw new Exception("预约记录不存在！"); 
      Integer.parseInt(orderstatus);
      order.setAuditor(person.getSeqId());
      order.setAuditorremark(auditorremark);
      order.setOrderstatus(Integer.valueOf(Integer.parseInt(orderstatus)));
      order.setAuditortime(YZUtility.getCurDateTimeStr());
      this.logic.updateSingleUUID(TableNameUtil.WX_ORDER, order);
      BcjlUtil.LogBcjl(BcjlUtil.AUDITOR, BcjlUtil.WX_ORDER, order, TableNameUtil.WX_ORDER, request);
      String sendContent = "对不起，预约失败：" + auditorremark;
      String sendMsgUrl = this.textLogic.getUrl4Send(sendContent);
      this.wxUtilLogic.sendTextMsg(sendMsgUrl, null, order.getOpenid(), sendContent, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
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
      WXOrder order = (WXOrder)this.logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.WX_ORDER, seqId);
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.WX_ORDER, order, TableNameUtil.WX_ORDER, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      String openid = request.getParameter("openid");
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(openid))
        map.put("openid", openid); 
      JSONObject data = this.logic.selectPage(TableNameUtil.WX_ORDER, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String orderdate = request.getParameter("orderdate");
      String confirmdateStart = request.getParameter("confirmdateStart");
      String confirmdateEnd = request.getParameter("confirmdateEnd");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(orderdate))
        map.put("orderdate", orderdate); 
      if (!YZUtility.isNullorEmpty(confirmdateStart))
        map.put("confirmdateStart", String.valueOf(confirmdateStart) + ConstUtil.HOUR_START); 
      if (!YZUtility.isNullorEmpty(confirmdateEnd))
        map.put("confirmdateEnd", String.valueOf(confirmdateEnd) + ConstUtil.HOUR_END); 
      List<JSONObject> list = this.logic.selectList(TableNameUtil.WX_ORDER, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("seqId不能为空"); 
      JSONObject en = this.logic.selectDetail(TableNameUtil.WX_ORDER, seqId);
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
}
