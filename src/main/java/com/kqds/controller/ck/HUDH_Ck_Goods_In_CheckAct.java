package com.kqds.controller.ck;

import com.kqds.entity.base.KqdsCkGoodsInCheck;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.HUDH_Ck_Goods_In_CheckLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.List;
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
@RequestMapping({"HUDH_Ck_Goods_In_CheckAct"})
public class HUDH_Ck_Goods_In_CheckAct {
  private static Logger logger = LoggerFactory.getLogger(HUDH_Ck_Goods_In_CheckAct.class);
  
  @Autowired
  private HUDH_Ck_Goods_In_CheckLogic logic;
  
  @RequestMapping({"/toGoodsInCheck.act"})
  public ModelAndView toGoodsInCheck(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/goodsIn/in_goods_examine.jsp");
    return mv;
  }
  
  @RequestMapping({"toGoodsCheck.act"})
  public ModelAndView toGoodsCheck(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/childhouse/inGoods/in_goods_examine.jsp");
    return mv;
  }
  
  @RequestMapping({"/toGoodsInCollate.act"})
  public ModelAndView toGoodsInCollate(HttpServletRequest request, HttpServletResponse response) {
    String goodsinid = request.getParameter("goodsinid");
    String incode = request.getParameter("incode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("goodsinid", goodsinid);
    mv.addObject("incode", incode);
    mv.setViewName("/kqdsFront/ck/goodsIn/add_examine_remark.jsp");
    return mv;
  }
  
  @RequestMapping({"/saveGoodsInCheck.act"})
  public String saveGoodsInCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String goodsinid = request.getParameter("goodsinid");
    String incode = request.getParameter("incode");
    String remark = request.getParameter("remark");
    String packing = request.getParameter("packing");
    String certificate = request.getParameter("certificate");
    String report = request.getParameter("report");
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    KqdsCkGoodsInCheck dp = new KqdsCkGoodsInCheck();
    dp.setCheckUserId(person.getSeqId());
    dp.setOrganization(organization);
    dp.setCheckDate(YZUtility.getCurDateTimeStr());
    dp.setGoodsinid(goodsinid);
    dp.setSEQ_ID(YZUtility.getUUID());
    dp.setRemark(remark);
    dp.setCertificate(certificate);
    dp.setPacking(packing);
    dp.setReport(report);
    try {
      this.logic.saveGoodsInCheck(dp, incode);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findGoodsInExamineByInId.act"})
  public String findGoodsInExamineByInId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String goodsInId = request.getParameter("goodsinid");
    try {
      List<JSONObject> json = this.logic.findGoodsInExamineByInId(goodsInId);
      YZUtility.RETURN_LIST(json, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
}
