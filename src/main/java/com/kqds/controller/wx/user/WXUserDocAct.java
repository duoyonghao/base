package com.kqds.controller.wx.user;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXOrder;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.netOrder.KQDS_Net_OrderLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.wx.core.WXUtil4ChatLogic;
import com.kqds.service.wx.core.WXUtil4TemplateMsgLogic;
import com.kqds.service.wx.core.WXUtilLogic;
import com.kqds.service.wx.fans.WXFansLogic;
import com.kqds.service.wx.user.WXUserDocLogic;
import com.kqds.util.base.code.UserCodeUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import com.kqds.util.wx.WXConst;
import java.math.BigDecimal;
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
@RequestMapping({"WXUserDocAct"})
public class WXUserDocAct
{
  private static Logger logger = LoggerFactory.getLogger(WXUserDocAct.class);
  @Autowired
  private WXUserDocLogic logic;
  @Autowired
  private KQDS_UserDocumentLogic userDocLogic;
  @Autowired
  private YZDictLogic dictLogic;
  @Autowired
  private KQDS_Net_OrderLogic netOrderlogic;
  @Autowired
  private WXFansLogic fansLogic;
  @Autowired
  private WXUtilLogic wxUtilLogic;
  @Autowired
  private WXUtil4TemplateMsgLogic wxUtil4TemplateMsgLogic;
  @Autowired
  private WXUtil4ChatLogic wxUtil4ChatLogic;
  
  @RequestMapping({"/toHzjd4kefu.act"})
  public ModelAndView toActive(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String openid = request.getParameter("openid");
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/user_manage/hzjd4kefu.jsp");
    mv.addObject("openid", openid);
    return mv;
  }
  
  @RequestMapping({"/bindUserByWXOrder.act"})
  public String bindUserByWXOrder(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String openid = request.getParameter("openid");
      String phonenumber = request.getParameter("phonenumber");
      if (YZUtility.isNullorEmpty(openid)) {
        throw new Exception("openid不能为空");
      }
      if (YZUtility.isNullorEmpty(phonenumber)) {
        throw new Exception("phonenumber不能为空");
      }
      KqdsUserdocument doc = this.userDocLogic.getSingUserByPhoneNumber(phonenumber);
      if (doc == null) {
        throw new Exception("患者不存在，手机号码为：" + phonenumber);
      }
      this.fansLogic.bindWXUser(openid, doc, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/confirmBind.act"})
  public String confirmBind(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String openid = request.getParameter("openid");
      String usercode = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(openid)) {
        throw new Exception("openid不能为空");
      }
      if (YZUtility.isNullorEmpty(usercode)) {
        throw new Exception("usercode不能为空");
      }
      KqdsUserdocument doc = this.userDocLogic.getSingleUserByUsercode(usercode);
      if (doc == null) {
        throw new Exception("患者不存在，编号为：" + usercode);
      }
      this.fansLogic.bindWXUser(openid, doc, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getUserDocByOpenId.act"})
  public String getUserDocByOpenId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String openid = request.getParameter("openid");
      KqdsUserdocument json = this.logic.getBindUserDocByOpenId(openid, request);
      JSONObject result = new JSONObject();
      result.put("retData", json);
      YZUtility.DEAL_SUCCESS(result, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getUserDocByNameAndPhonenumber.act"})
  public String getUserDocByNameAndPhonenumber(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String username = request.getParameter("username");
      String phonenumber = request.getParameter("phonenumber");
      JSONObject json = this.logic.getUserDocByNameAndPhonenumber(username, phonenumber, request);
      JSONObject result = new JSONObject();
      result.put("retData", json);
      YZUtility.DEAL_SUCCESS(result, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/bindWxOpenId.act"})
  public String bindWxOpenId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("userSeqId");
      String openid = request.getParameter("openid");
      int count = this.logic.bindWxOpenId(seqId, openid, request);
      JSONObject result = new JSONObject();
      result.put("retData", Integer.valueOf(count));
      YZUtility.DEAL_SUCCESS(result, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/createQRCodeByUserCode.act"})
  public String createQRCodeByUserCode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      String accountid = YZSysProps.getProp("WEIXIN_ACCOUNTID");
      String qrcode_create_url = YZSysProps.getString("QRCODE_CREATE_URL");
      if (YZUtility.isNullorEmpty(usercode)) {
        throw new Exception("usercode不能为空");
      }
      if (YZUtility.isNullorEmpty(accountid)) {
        throw new Exception("accountid不能为空");
      }
      if (YZUtility.isNullorEmpty(qrcode_create_url)) {
        throw new Exception("qrcode_create_url不能为空");
      }
      int scene_id = this.wxUtilLogic.getRandom4QrCode(usercode);
      
      qrcode_create_url = qrcode_create_url + "&scene_id=" + scene_id + "&wxaccountid=" + accountid;
      
      this.wxUtilLogic.getClass();JSONObject rtData = WXUtilLogic.httpRequest(qrcode_create_url, "GET", null);
      if (rtData.containsKey("errcode"))
      {
        String msg = "错误编号：" + rtData.getString("errcode") + "，错误信息：" + rtData.getString("errmsg");
        throw new Exception(msg);
      }
      YZUtility.DEAL_SUCCESS(rtData, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insert4kefu.act"})
  public String insert4kefu(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      KqdsUserdocument dp = new KqdsUserdocument();
      KqdsNetOrder netorder = new KqdsNetOrder();
      BeanUtils.populate(dp, request.getParameterMap());
      BeanUtils.populate(netorder, request.getParameterMap());
      
      String openid = request.getParameter("openid");
      if (YZUtility.isNullorEmpty(openid)) {
        throw new Exception("openid不能为空");
      }
      KqdsUserdocument json = this.logic.getBindUserDocByOpenId(openid, request);
      if (json != null) {
        throw new Exception("该微信已绑定患者，无法再次建档，已绑定患者姓名：" + json.getUsername() + ",编号：" + json.getUsercode());
      }
      String usercode = dp.getUsercode();
      String jdOrganization = ChainUtil.getCurrentOrganization(request);
      if (ConstUtil.USER_TYPE_1 == dp.getType().intValue()) {
        jdOrganization = ChainUtil.getOrganizationFromUrl(request);
      }
      String realusercode = UserCodeUtil.getUserCode4Insert(jdOrganization, usercode);
      if (!usercode.equals(realusercode))
      {
        dp.setUsercode(realusercode);
        logger.error("----页面患者编号：" + usercode + ",新编号：" + realusercode);
      }
      if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber1()))
      {
        Map<String, String> map = new HashMap();
        map.put("phonenumber1", dp.getPhonenumber1());
        int count = this.userDocLogic.checkphonenumber(null, map, TableNameUtil.KQDS_USERDOCUMENT);
        if (count > 0) {
          throw new Exception("手机号码1已存在：" + dp.getPhonenumber1());
        }
      }
      if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber2()))
      {
        Map<String, String> map = new HashMap();
        map.put("phonenumber2", dp.getPhonenumber2());
        int count = this.userDocLogic.checkphonenumber(null, map, TableNameUtil.KQDS_USERDOCUMENT);
        if (count > 0) {
          throw new Exception("手机号码2已存在：" + dp.getPhonenumber2());
        }
      }
      String uuid = YZUtility.getUUID();
      dp.setSeqId(uuid);
      dp.setIsdelete(Integer.valueOf(0));
      dp.setCreatetime(YZUtility.getCurDateTimeStr());
      dp.setCreateuser(person.getSeqId());
      if (1 == dp.getType().intValue())
      {
        netorder.setSeqId(YZUtility.getUUID());
        netorder.setCreatetime(YZUtility.getCurDateTimeStr());
        netorder.setCreateuser(person.getSeqId());
        netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request));
        
        netorder.setDoorstatus(Integer.valueOf(0));
        netorder.setCjstatus(Integer.valueOf(0));
        netorder.setUsercode(dp.getUsercode());
        netorder.setUsername(dp.getUsername());
        netorder.setAcceptdate(dp.getCreatetime());
        netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
      }
      dp.setBindstatus("0");
      this.logic.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, dp);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_USERDOCUMENT, dp, dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
      

      addMember4CreateUserDocument(dp, person, request);
      

      this.logic.setFansUserCode(dp.getOpenid(), dp.getUsercode(), request);
      
      this.wxUtil4TemplateMsgLogic.createDocSuccessNotify(dp, netorder, request);
      

      this.wxUtil4ChatLogic.updateChatUserListMsg(dp.getOpenid(), "createdoc", dp, null);
      if (!YZUtility.isNullorEmpty(netorder.getOrdertime())) {
        this.wxUtil4TemplateMsgLogic.orderSuccessNotify(dp.getUsercode(), dp.getOpenid(), netorder, request);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("username", dp.getUsername());
      jobj.put("usercode", dp.getUsercode());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insert.act"})
  public String insert(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      KqdsUserdocument dp = new KqdsUserdocument();
      KqdsNetOrder netorder = new KqdsNetOrder();
      BeanUtils.populate(dp, request.getParameterMap());
      BeanUtils.populate(netorder, request.getParameterMap());
      
      String wxOrderSeqId = request.getParameter("wxOrderSeqId");
      

      String usercode = dp.getUsercode();
      String jdOrganization = ChainUtil.getCurrentOrganization(request);
      if (ConstUtil.USER_TYPE_1 == dp.getType().intValue()) {
        jdOrganization = ChainUtil.getOrganizationFromUrl(request);
      }
      String realusercode = UserCodeUtil.getUserCode4Insert(jdOrganization, usercode);
      if (!usercode.equals(realusercode))
      {
        dp.setUsercode(realusercode);
        logger.error("----页面患者编号：" + usercode + ",新编号：" + realusercode);
      }
      if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber1()))
      {
        Map<String, String> map = new HashMap();
        map.put("phonenumber1", dp.getPhonenumber1());
        int count = this.userDocLogic.checkphonenumber(null, map, TableNameUtil.KQDS_USERDOCUMENT);
        if (count > 0) {
          throw new Exception("手机号码1已存在：" + dp.getPhonenumber1());
        }
      }
      if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber2()))
      {
        Map<String, String> map = new HashMap();
        map.put("phonenumber2", dp.getPhonenumber2());
        int count = this.userDocLogic.checkphonenumber(null, map, TableNameUtil.KQDS_USERDOCUMENT);
        if (count > 0) {
          throw new Exception("手机号码2已存在：" + dp.getPhonenumber2());
        }
      }
      String uuid = YZUtility.getUUID();
      dp.setSeqId(uuid);
      dp.setIsdelete(Integer.valueOf(0));
      dp.setCreatetime(YZUtility.getCurDateTimeStr());
      dp.setCreateuser(person.getSeqId());
      if (1 == dp.getType().intValue())
      {
        netorder.setSeqId(YZUtility.getUUID());
        netorder.setCreatetime(YZUtility.getCurDateTimeStr());
        netorder.setCreateuser(person.getSeqId());
        netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request));
        netorder.setAskno(wxOrderSeqId);
        netorder.setDoorstatus(Integer.valueOf(0));
        netorder.setCjstatus(Integer.valueOf(0));
        netorder.setUsercode(dp.getUsercode());
        netorder.setUsername(dp.getUsername());
        netorder.setAcceptdate(dp.getCreatetime());
        netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
      }
      if (!YZUtility.isNullorEmpty(wxOrderSeqId))
      {
        WXOrder wxorder = (WXOrder)this.logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, wxOrderSeqId);
        if (wxorder == null) {
          throw new Exception("数据不存在");
        }
        dp.setOpenid(wxorder.getOpenid());
        dp.setBindstatus("0");
        
        wxorder.setAuditor(person.getSeqId());
        wxorder.setOrderstatus(Integer.valueOf(WXConst.ORDER_STATUS_1));
        wxorder.setConfirmtime(netorder.getOrdertime());
        this.logic.updateSingleUUID(TableNameUtil.WX_ORDER, wxorder);
      }
      dp.setBindstatus("0");
      this.logic.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, dp);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_USERDOCUMENT, dp, dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
      

      addMember4CreateUserDocument(dp, person, request);
      

      this.logic.setFansUserCode(dp.getOpenid(), dp.getUsercode(), request);
      
      this.wxUtil4TemplateMsgLogic.createDocSuccessNotify(dp, netorder, request);
      if (!YZUtility.isNullorEmpty(netorder.getOrdertime())) {
        this.wxUtil4TemplateMsgLogic.orderSuccessNotify(dp.getUsercode(), dp.getOpenid(), netorder, request);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("username", dp.getUsername());
      jobj.put("usercode", dp.getUsercode());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertNetOrder.act"})
  public String insertNetOrder(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      KqdsNetOrder netorder = new KqdsNetOrder();
      BeanUtils.populate(netorder, request.getParameterMap());
      if (YZUtility.isNullorEmpty(netorder.getUsercode())) {
        throw new Exception("usercode不能为空");
      }
      if (YZUtility.isNullorEmpty(netorder.getOrdertime())) {
        throw new Exception("ordertime不能为空");
      }
      KqdsUserdocument dp = this.userDocLogic.getSingleUserByUsercode(netorder.getUsercode());
      if (dp == null) {
        throw new Exception("患者不存在，usercode：" + netorder.getUsercode());
      }
      String wxOrderSeqId = request.getParameter("wxOrderSeqId");
      if (YZUtility.isNullorEmpty(wxOrderSeqId)) {
        throw new Exception("wxOrderSeqId不能为空");
      }
      WXOrder wxorder = (WXOrder)this.logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, wxOrderSeqId);
      if (wxorder == null) {
        throw new Exception("数据不存在");
      }
      String ordertime4check = netorder.getOrdertime().substring(0, 10);
      
      List<JSONObject> list = this.netOrderlogic.checkNetOrderCount(null, dp.getUsercode(), ordertime4check);
      if (list.size() >= 1) {
        throw new Exception("同一个患者当天只能网电预约一次，患者编号：" + dp.getUsercode() + "，预约日期：" + netorder.getOrdertime() + "。");
      }
      netorder.setSeqId(YZUtility.getUUID());
      netorder.setCreatetime(YZUtility.getCurDateTimeStr());
      netorder.setCreateuser(person.getSeqId());
      netorder.setOrganization(wxorder.getOrganization());
      netorder.setAskno(wxOrderSeqId);
      netorder.setDoorstatus(Integer.valueOf(0));
      netorder.setCjstatus(Integer.valueOf(0));
      netorder.setUsercode(netorder.getUsercode());
      netorder.setUsername(dp.getUsername());
      netorder.setAcceptdate(YZUtility.getCurDateTimeStr());
      this.logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
      
      wxorder.setAuditor(person.getSeqId());
      wxorder.setOrderstatus(Integer.valueOf(WXConst.ORDER_STATUS_1));
      wxorder.setConfirmtime(netorder.getOrdertime());
      this.logic.updateSingleUUID(TableNameUtil.WX_ORDER, wxorder);
      
      this.wxUtil4TemplateMsgLogic.orderSuccessNotify(dp.getUsercode(), dp.getOpenid(), netorder, request);
      
      JSONObject jobj = new JSONObject();
      jobj.put("username", dp.getUsername());
      jobj.put("usercode", dp.getUsercode());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  private void addMember4CreateUserDocument(KqdsUserdocument user, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    KqdsMember dp = new KqdsMember();
    
    dp.setSeqId(YZUtility.getUUID());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    dp.setCreateuser(person.getSeqId());
    
    dp.setMemberno(user.getUsercode());
    
    dp.setPassword(dp.getMemberno().substring(dp.getMemberno().length() - 6, dp.getMemberno().length()));
    
    String level = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.HYKFL, "预交金卡");
    dp.setMemberlevel(level);
    dp.setMemberstatus("1");
    dp.setUsername(user.getUsername());
    dp.setUsercode(user.getUsercode());
    dp.setMoney(BigDecimal.ZERO);
    dp.setGivemoney(BigDecimal.ZERO);
    dp.setDiscount(Integer.valueOf(100));
    dp.setRemark("预交金卡");
    dp.setOrganization(ChainUtil.getCurrentOrganization(request));
    String HYK_BINDING = SysParaUtil.getSysValueByName(request, SysParaUtil.HYK_BINDING);
    if (HYK_BINDING.equals("0"))
    {
      dp.setIcno(dp.getMemberno());
      dp.setIsbinding(Integer.valueOf(1));
    }
    this.logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
  }
}
