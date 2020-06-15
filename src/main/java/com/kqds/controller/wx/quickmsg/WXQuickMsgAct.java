package com.kqds.controller.wx.quickmsg;

import com.kqds.controller.base.sms.KQDS_SmsAct;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXQuickmsg;
import com.kqds.service.wx.quickmsg.WXQuickMsgLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
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
@RequestMapping({"WXQuickMsgAct"})
public class WXQuickMsgAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_SmsAct.class);
  
  @Autowired
  private WXQuickMsgLogic logic;
  
  @RequestMapping({"/toIndex.act"})
  public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/quickmsg/index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUserList.act"})
  public ModelAndView toUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/wechat/sendMsg/userList.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddNew.act"})
  public ModelAndView toAddNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/wechat/quickmsg/add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/wechat/quickmsg/detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/wechat/quickmsg/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      WXQuickmsg dp = new WXQuickmsg();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.WX_QUICKMSG, dp);
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.WX_QUICKMSG, dp, TableNameUtil.WX_QUICKMSG, request);
      } else {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.WX_QUICKMSG, dp);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.WX_QUICKMSG, dp, TableNameUtil.WX_QUICKMSG, request);
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
      WXQuickmsg en = (WXQuickmsg)this.logic.loadObjSingleUUID(TableNameUtil.WX_QUICKMSG, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.WX_QUICKMSG, seqId);
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.WX_QUICKMSG, en, TableNameUtil.WX_QUICKMSG, request);
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
      WXQuickmsg en = (WXQuickmsg)this.logic.loadObjSingleUUID(TableNameUtil.WX_QUICKMSG, seqId);
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
  
  @RequestMapping({"/NoselectPage.act"})
  public String NoselectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = request.getParameter("organization");
      String msgtype = request.getParameter("msgtype");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(msgtype))
        map.put("msgtype", msgtype); 
      List<JSONObject> list = this.logic.noSelectWithPage(TableNameUtil.WX_QUICKMSG, map);
      YZUtility.RETURN_LIST(list, response, logger);
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
      String organization = request.getParameter("organization");
      String msgtype = request.getParameter("msgtype");
      String smsnexttype = request.getParameter("smsnexttype");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(msgtype))
        map.put("msgtype", msgtype); 
      if (!YZUtility.isNullorEmpty(smsnexttype))
        map.put("smsnexttype", smsnexttype); 
      JSONObject data = this.logic.selectWithPage(TableNameUtil.WX_QUICKMSG, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
