package com.hudh.doctorpick.controller;

import com.hudh.doctorpick.entity.GoodsDoctorPickIn;
import com.hudh.doctorpick.entity.GoodsDoctorPickInDetail;
import com.hudh.doctorpick.service.IGoodsDoctorPickInDetailService;
import com.hudh.doctorpick.service.IGoodsDoctorPickInService;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import java.net.URLDecoder;
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

@Controller
@RequestMapping({"/HUDH_Goods_DoctorPickInAct"})
public class HUDH_Goods_DoctorPickInAct {
  private static Logger logger = LoggerFactory.getLogger(HUDH_Goods_DoctorPickInAct.class);
  
  @Autowired
  private IGoodsDoctorPickInService goodsDoctorPickInService;
  
  @Autowired
  private IGoodsDoctorPickInDetailService detailService;
  
  @RequestMapping({"/insertGoogsPick.act"})
  public String insertGoogsPick(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String rktime = request.getParameter("rktime");
    String supplier = request.getParameter("supplier");
    String stocktime = request.getParameter("stocktime");
    String incode = request.getParameter("incode");
    String inremark = request.getParameter("inremark");
    String zhaiyao = request.getParameter("zhaiyao");
    String costno1 = request.getParameter("costno1");
    GoodsDoctorPickIn dp = new GoodsDoctorPickIn();
    dp.setSummary(zhaiyao);
    dp.setSupplier(supplier);
    dp.setIncode(incode);
    dp.setRktime(rktime);
    dp.setStocktime(stocktime);
    dp.setInremark(inremark);
    String paramDetail = request.getParameter("paramDetail");
    paramDetail = URLDecoder.decode(paramDetail, "UTF-8");
    try {
      this.goodsDoctorPickInService.insertGoogsPick(dp, paramDetail, request, costno1);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findAllGoodsDoctorPick.act"})
  public String findAllGoodsDoctorPick(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> map = new HashMap<>();
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String supplier = request.getParameter("supplier");
    String incode = request.getParameter("incode");
    String stock_starttime = request.getParameter("stock_starttime");
    String stock_endtime = request.getParameter("stock_endtime");
    String visualstaff = SessionUtil.getVisualstaff(request);
    if (YZUtility.isNotNullOrEmpty(visualstaff))
      map.put("querytype", visualstaff); 
    if (!YZUtility.isNullorEmpty(starttime))
      map.put("starttime", starttime); 
    if (!YZUtility.isNullorEmpty(endtime))
      map.put("endtime", endtime); 
    if (!YZUtility.isNullorEmpty(supplier))
      map.put("supplier", supplier); 
    if (!YZUtility.isNullorEmpty(incode))
      map.put("incode", incode); 
    if (!YZUtility.isNullorEmpty(stock_starttime))
      map.put("stock_starttime", stock_starttime); 
    if (!YZUtility.isNullorEmpty(stock_endtime))
      map.put("stock_endtime", stock_endtime); 
    try {
      List<JSONObject> list = this.goodsDoctorPickInService.findAllGoodsDoctorPick(map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteGoodsDoctorPickByIncode.act"})
  public String deleteGoodsDoctorPickByIncode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String inseqId = request.getParameter("inseqId");
    try {
      this.goodsDoctorPickInService.deleteGoodsDoctorPickByIncode(inseqId);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findGoodsDoctorPickByIncode.act"})
  public String findGoodsDoctorPickByIncode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String incode = request.getParameter("incode");
    try {
      JSONObject json = this.goodsDoctorPickInService.findGoodsDoctorPickByIncode(incode);
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateGoodsDoctorPickBySeqId.act"})
  public String updateGoodsDoctorPickBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    String rkddata = request.getParameter("rkddata");
    String rkdetaildata = request.getParameter("rkdetaildata");
    JSONObject jsonobj = JSONObject.fromObject(rkddata);
    GoodsDoctorPickIn inobj = (GoodsDoctorPickIn)JSONObject.toBean(jsonobj, GoodsDoctorPickIn.class);
    GoodsDoctorPickIn goodsDoctorPickIn = this.goodsDoctorPickInService.findAllGoodsDoctorPickBySeqId(inobj.getSeqId());
    if (goodsDoctorPickIn == null)
      throw new Exception("领料单不存在"); 
    JSONObject jsonobj2 = JSONObject.fromObject(rkdetaildata);
    GoodsDoctorPickInDetail inobjdetail = (GoodsDoctorPickInDetail)JSONObject.toBean(jsonobj2, GoodsDoctorPickInDetail.class);
    GoodsDoctorPickInDetail goodDetal = this.detailService.findDoctorPickInDetailBySeqId(inobjdetail.getSeqId());
    if (goodDetal == null)
      throw new Exception("领料单明细不存在"); 
    try {
      this.goodsDoctorPickInService.updateGoodsDoctorPickBySeqId(seqId);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findAllGoodsDoctorPickBySeqId.act"})
  public String findAllGoodsDoctorPickBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    try {
      GoodsDoctorPickIn goodsDoctorPickIn = this.goodsDoctorPickInService.findAllGoodsDoctorPickBySeqId(seqId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return null;
  }
}
