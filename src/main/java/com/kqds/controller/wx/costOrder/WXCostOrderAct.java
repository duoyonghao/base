package com.kqds.controller.wx.costOrder;

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.wx.costOrder.WXCostOrderLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.HashMap;
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
@RequestMapping({"WXCostOrderAct"})
public class WXCostOrderAct {
  private static Logger logger = LoggerFactory.getLogger(WXCostOrderAct.class);
  
  @Autowired
  private WXCostOrderLogic wxCostOrderLogic;
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/wechat/order_manage/detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNetOrder4wechat.act"})
  public ModelAndView toNetOrder4wechat(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String wxOrderSeqId = request.getParameter("wxOrderSeqId");
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("wxOrderSeqId", wxOrderSeqId);
    mv.addObject("usercode", usercode);
    mv.setViewName("/wechat/user_manage/netorder4wechat.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzjd4wechat.act"})
  public ModelAndView toHzjd4wechat(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String wxOrderSeqId = request.getParameter("wxOrderSeqId");
    String openid = request.getParameter("openid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("wxOrderSeqId", wxOrderSeqId);
    mv.addObject("openid", openid);
    mv.setViewName("/wechat/user_manage/hzjd4wechat.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      String usercode = request.getParameter("usercode");
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      JSONObject data = this.wxCostOrderLogic.selectPage(TableNameUtil.KQDS_COSTORDER, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPageDetail.act"})
  public String selectPageDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      String costno = request.getParameter("costno");
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(costno))
        map.put("costno", costno); 
      JSONObject data = this.wxCostOrderLogic.selectPageDetail(TableNameUtil.KQDS_COSTORDER_DETAIL, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
