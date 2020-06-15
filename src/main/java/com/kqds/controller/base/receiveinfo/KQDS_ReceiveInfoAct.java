package com.kqds.controller.base.receiveinfo;

import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.receiveinfo.KQDS_ReceiveInfoLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
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
@RequestMapping({"KQDS_ReceiveInfoAct"})
public class KQDS_ReceiveInfoAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_ReceiveInfoAct.class);
  
  @Autowired
  private KQDS_ReceiveInfoLogic logic;
  
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @Autowired
  private KQDS_REGLogic reglogic;
  
  @RequestMapping({"/toReceiveWin.act"})
  public ModelAndView toReceiveWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/zxjl/receiveWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toReceive.act"})
  public ModelAndView toReceive(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/index/center/receive.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      KqdsReceiveinfo dp = new KqdsReceiveinfo();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String isauto = request.getParameter("isauto");
      String devItem = request.getParameter("devItem");
      if (!YZUtility.isNullorEmpty(seqId)) {
        KqdsReceiveinfo en = (KqdsReceiveinfo)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, seqId);
        en.setDetaildesc(dp.getDetaildesc());
        en.setFailreason1(dp.getFailreason1());
        if (!YZUtility.isNullorEmpty(dp.getFailreason1()) || !YZUtility.isNullorEmpty(dp.getDetaildesc())) {
          en.setAskstatus(Integer.valueOf(1));
        } else {
          en.setAskstatus(Integer.valueOf(0));
        } 
        en.setDevItem(devItem);
        if (!en.getDetaildesc().equals("") || !en.getFailreason1().equals(""))
          if (YZUtility.isNullorEmpty(isauto))
            BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_RECEIVEINFO, en, en.getUsercode(), TableNameUtil.KQDS_RECEIVEINFO, request);  
        this.logic.updateSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, en);
        KqdsReg reg = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, en.getRegno());
        if (en.getAskstatus().intValue() == 1 && !YZUtility.isNullorEmpty(reg.getAskperson()))
          this.userLogic.setUserDocAskPerson(request, en.getUsercode(), reg.getAskperson()); 
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/NoselectPage.act"})
  public String NoselectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      String usercode = request.getParameter("usercode");
      String regno = request.getParameter("regno");
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap<>();
      if (usercode != null && !usercode.equals(""))
        map.put("usercode", usercode); 
      if (regno != null && !regno.equals(""))
        map.put("regno", regno); 
      List<JSONObject> list = this.logic.noSelectWithPage(TableNameUtil.KQDS_RECEIVEINFO, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
