package com.kqds.controller.ck;

import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsIn;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_Goods_InLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_In_DetailLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_OutLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_Ck_Goods_InAct"})
public class KQDS_Ck_Goods_InAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_Goods_InAct.class);
  
  @Autowired
  private KQDS_Ck_Goods_InLogic logic;
  
  @Autowired
  private KQDS_Ck_Goods_OutLogic outlogic;
  
  @Autowired
  private KQDS_Ck_Goods_In_DetailLogic detailLogic;
  
  @RequestMapping({"/toCkGoodsIn.act"})
  public ModelAndView toCkDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/goodsIn/in_goods.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCkGoodsIn2.act"})
  public ModelAndView toCkGoodsIn2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/childhouse/inGoods/in_goods.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCkInGoodsSearch.act"})
  public ModelAndView toCkInGoodsSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/search/in_goods_search.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCkInGoodsDetail.act"})
  public ModelAndView toCkInGoodsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    String goodsinid = request.getParameter("goodsinid");
    mv.addObject("goodsinid", goodsinid);
    mv.setViewName("/kqdsFront/ck/goodsIn/editInGoodsDetail.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsCkGoodsIn dp = new KqdsCkGoodsIn();
      BeanUtils.populate(dp, request.getParameterMap());
      String menzhen = ChainUtil.getCurrentOrganization(request);
      Map<String, String> mapin = new HashMap<>();
      mapin.put("incode", dp.getIncode());
      List<KqdsCkGoodsIn> inList = (List<KqdsCkGoodsIn>)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS_IN, mapin);
      if (inList.size() > 0) {
        JSONObject retrunObj = new JSONObject();
        retrunObj.put("retState", "100");
        retrunObj.put("retMsrg", "入库单号已存在，系统已自动重新获取");
        PrintWriter pw = response.getWriter();
        pw.println(retrunObj.toString());
        pw.flush();
        return null;
      } 
      dp.setSeqId(YZUtility.getUUID());
      dp.setOrganization(menzhen);
      dp.setCreatetime(YZUtility.getCurDateTimeStr());
      dp.setCreateuser(person.getSeqId());
      dp.setShtime(dp.getCreatetime());
      dp.setAuditor(person.getSeqId());
      dp.setRkr(person.getSeqId());
      dp.setInstatus(Integer.valueOf(3));
      dp.setCheck_status(0);
      dp.setStatus(0);
      this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, dp);
      BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_IN, dp, TableNameUtil.KQDS_CK_GOODS_IN, request);
      String params = request.getParameter("paramDetail");
      params = URLDecoder.decode(params, "UTF-8");
      JSONArray jArray = JSONArray.fromObject(params);
      List<KqdsCkGoodsInDetail> jList = (List<KqdsCkGoodsInDetail>)JSONArray.toCollection(jArray, KqdsCkGoodsInDetail.class);
      for (KqdsCkGoodsInDetail detail : jList) {
        detail.setSeqId(YZUtility.getUUID());
        detail.setIncode(dp.getIncode());
        detail.setOrganization(menzhen);
        detail.setCreateuser(person.getSeqId());
        detail.setCreatetime(dp.getCreatetime());
        detail.setGoodsInSeqId(dp.getSeqId());
        detail.setAuditStatus(0);
        if (detail.getInnum() != null)
          detail.setPhnum(detail.getInnum().intValue()); 
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, detail);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_IN_DETAIL, detail, TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, request);
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("data", dp.getSeqId());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/editGoodsIn.act"})
  public String editGoodsIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String rkddata = request.getParameter("rkddata");
      String rkdetaildata = request.getParameter("rkdetaildata");
      String goodsdata = request.getParameter("goodsdata");
      JSONObject jsonobj = JSONObject.fromObject(rkddata);
      KqdsCkGoodsIn inobj = (KqdsCkGoodsIn)JSONObject.toBean(jsonobj, KqdsCkGoodsIn.class);
      KqdsCkGoodsIn goodsin = (KqdsCkGoodsIn)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, inobj.getSeqId());
      if (goodsin == null)
        throw new Exception("入库单不存在"); 
      String oldhouse = goodsin.getInhouse();
      String nowhouse = inobj.getInhouse();
      goodsin.setIntype(inobj.getIntype());
      goodsin.setSupplier(inobj.getSupplier());
      goodsin.setInhouse(inobj.getInhouse());
      goodsin.setInremark(inobj.getInremark());
      goodsin.setZhaiyao(inobj.getZhaiyao());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, goodsin);
      BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_GOODS_IN, goodsin, TableNameUtil.KQDS_CK_GOODS_IN, request);
      JSONObject jsonobj2 = JSONObject.fromObject(rkdetaildata);
      KqdsCkGoodsInDetail inobjdetail = (KqdsCkGoodsInDetail)JSONObject.toBean(jsonobj2, KqdsCkGoodsInDetail.class);
      KqdsCkGoodsInDetail goodsinDetal = (KqdsCkGoodsInDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, inobjdetail.getSeqId());
      if (goodsinDetal == null)
        throw new Exception("入库单明细不存在"); 
      int oldinnum = goodsinDetal.getInnum().intValue();
      BigDecimal oldrkmoney = goodsinDetal.getRkmoney();
      goodsinDetal.setInnum(inobjdetail.getInnum());
      goodsinDetal.setInprice(inobjdetail.getInprice());
      goodsinDetal.setRkmoney(inobjdetail.getRkmoney());
      goodsinDetal.setYxdate(inobjdetail.getYxdate());
      goodsinDetal.setSqremark(inobjdetail.getSqremark());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, goodsinDetal);
      BcjlUtil.LogBcjl(BcjlUtil.UPDATE, BcjlUtil.KQDS_CK_GOODS_IN_DETAIL, goodsinDetal, TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, request);
      JSONObject jsonobj4 = JSONObject.fromObject(goodsdata);
      KqdsCkGoods goodsobj = (KqdsCkGoods)JSONObject.toBean(jsonobj4, KqdsCkGoods.class);
      KqdsCkGoods goods = (KqdsCkGoods)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS, goodsobj.getSeqId());
      if (goods == null) {
        goods = new KqdsCkGoods();
        goods.setSeqId(YZUtility.getUUID());
        goods.setGoodsdetailid(goodsinDetal.getGoodsuuid());
        goods.setSshouse(nowhouse);
        goods.setGoodsprice(goodsinDetal.getInprice());
        goods.setKcmoney(goodsinDetal.getRkmoney());
        goods.setNums(goodsinDetal.getInnum());
        goods.setOrganization(goodsinDetal.getOrganization());
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
      } else {
        goods.setGoodsprice(goodsobj.getGoodsprice());
        goods.setNums(goodsobj.getNums());
        goods.setKcmoney(goodsobj.getKcmoney());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
      } 
      if (!oldhouse.equals(nowhouse)) {
        Map<String, String> map = new HashMap<>();
        map.put("sshouse", oldhouse);
        map.put("goodsdetailid", goods.getGoodsdetailid());
        List<KqdsCkGoods> en = (List<KqdsCkGoods>)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
        KqdsCkGoods oldgoods = en.get(0);
        oldgoods.setNums(Integer.valueOf(oldgoods.getNums().intValue() - oldinnum));
        oldgoods.setKcmoney(oldgoods.getKcmoney().subtract(oldrkmoney));
        BigDecimal goodsprice = null;
        if (oldgoods.getNums().intValue() == 0) {
          goodsprice = BigDecimal.ZERO;
        } else {
          goodsprice = oldgoods.getKcmoney().divide(new BigDecimal(oldgoods.getNums().intValue()), 3, RoundingMode.HALF_EVEN).setScale(3, 5);
        } 
        oldgoods.setGoodsprice(goodsprice);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, oldgoods);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteAllGoodsIn.act"})
  public String deleteAllGoodsIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String inseqId = request.getParameter("inseqId");
      KqdsCkGoodsIn goodsin = (KqdsCkGoodsIn)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, inseqId);
      if (goodsin == null)
        throw new Exception("入库单不存在"); 
      Map<String, String> map = new HashMap<>();
      map.put("incode", goodsin.getIncode());
      List<KqdsCkGoodsInDetail> detailList = (List<KqdsCkGoodsInDetail>)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, map);
      if (detailList == null || detailList.size() == 0)
        throw new Exception("入库单明细不存在"); 
      for (KqdsCkGoodsInDetail detail : detailList) {
        Map<String, String> map2 = new HashMap<>();
        map2.put("sshouse", goodsin.getInhouse());
        map2.put("goodsdetailid", detail.getGoodsuuid());
        List<KqdsCkGoods> goodsList = (List<KqdsCkGoods>)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS, map2);
        if (goodsList == null || goodsList.size() == 0)
          throw new Exception("库存信息不存在"); 
        KqdsCkGoods goods = goodsList.get(0);
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, goodsin.getSeqId());
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, detail.getSeqId());
        BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_IN_DETAIL, detail, TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, request);
        goods.setNums(Integer.valueOf(goods.getNums().intValue() - detail.getInnum().intValue()));
        goods.setKcmoney(goods.getKcmoney().subtract(detail.getRkmoney()));
        BigDecimal goodsprice = BigDecimal.ZERO;
        if (goods.getNums().intValue() != 0)
          goodsprice = goods.getKcmoney().divide(new BigDecimal(goods.getNums().intValue()), 3, RoundingMode.HALF_EVEN).setScale(3, 5); 
        goods.setGoodsprice(goodsprice);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteGoodsIn.act"})
  public String deleteGoodsIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String indetailseqId = request.getParameter("indetailseqId");
      String inseqId = request.getParameter("inseqId");
      String goodsseqId = request.getParameter("goodsseqId");
      KqdsCkGoodsInDetail goodsinDetal = (KqdsCkGoodsInDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, indetailseqId);
      if (goodsinDetal == null)
        throw new Exception("入库单明细不存在"); 
      KqdsCkGoods goods = (KqdsCkGoods)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS, goodsseqId);
      if (goods == null)
        throw new Exception("库存信息不存在"); 
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, goodsinDetal.getSeqId());
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_IN_DETAIL, goodsinDetal, TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, request);
      Map<String, String> map = new HashMap<>();
      map.put("incode", goodsinDetal.getIncode());
      List<KqdsCkGoodsInDetail> en = (List<KqdsCkGoodsInDetail>)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, map);
      if (en == null || en.size() == 0) {
        KqdsCkGoodsIn goodsin = (KqdsCkGoodsIn)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, inseqId);
        if (goodsin == null)
          throw new Exception("入库单不存在"); 
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, inseqId);
      } 
      goods.setNums(Integer.valueOf(goods.getNums().intValue() - goodsinDetal.getInnum().intValue()));
      goods.setKcmoney(goods.getKcmoney().subtract(goodsinDetal.getRkmoney()));
      BigDecimal goodsprice = BigDecimal.ZERO;
      if (goods.getNums().intValue() != 0)
        goodsprice = goods.getKcmoney().divide(new BigDecimal(goods.getNums().intValue()), 3, RoundingMode.HALF_EVEN).setScale(3, 5); 
      goods.setGoodsprice(goodsprice);
      this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteGoodsInDetailById.act"})
  public String deleteGoodsInDetailById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("indetailseqId");
    try {
      this.detailLogic.deleteGoodsInDetailById(id);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/inSearchList.act"})
  public String inSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String inhouse = request.getParameter("inhouse");
      String intype = request.getParameter("intype");
      String supplier = request.getParameter("supplier");
      String incode = request.getParameter("incode");
      String check_status = request.getParameter("check_status");
      String stock_starttime = request.getParameter("stock_starttime");
      String stock_endtime = request.getParameter("stock_endtime");
      String type = request.getParameter("type");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(starttime))
        map.put("starttime", String.valueOf(starttime) + ConstUtil.TIME_START); 
      if (!YZUtility.isNullorEmpty(endtime))
        map.put("endtime", String.valueOf(endtime) + ConstUtil.TIME_END); 
      if (!YZUtility.isNullorEmpty(inhouse))
        map.put("inhouse", inhouse); 
      if (!YZUtility.isNullorEmpty(intype))
        map.put("intype", intype); 
      if (!YZUtility.isNullorEmpty(supplier))
        map.put("supplier", supplier); 
      if (!YZUtility.isNullorEmpty(incode))
        map.put("incode", incode); 
      if (!YZUtility.isNullorEmpty(stock_starttime))
        map.put("stock_starttime", stock_starttime); 
      if (!YZUtility.isNullorEmpty(stock_endtime))
        map.put("stock_endtime", stock_endtime); 
      if (!YZUtility.isNullorEmpty(check_status))
        map.put("check_status", check_status); 
      if (!YZUtility.isNullorEmpty(type))
        map.put("type", type); 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = this.logic.inSearchList(TableNameUtil.KQDS_CK_GOODS_IN, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectBySupplier.act"})
  public String selectBySupplier(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String supplierid = request.getParameter("supplierid");
      String organization = ChainUtil.getCurrentOrganization(request);
      int in = this.logic.countBySupplier(supplierid, organization);
      int out = this.outlogic.countBySupplier(supplierid, organization);
      boolean flag = !(in + out > 0);
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectZczh.act"})
  public String selectZczh(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String goodsid = request.getParameter("goodsid");
      String organization = ChainUtil.getCurrentOrganization(request);
      String zczh = this.logic.selectZczh(goodsid, organization);
      String inprice = this.logic.selectInprice(goodsid, organization);
      String cd = this.logic.selectCd(goodsid, organization);
      JSONObject jobj = new JSONObject();
      jobj.put("zczh", zczh);
      jobj.put("inprice", inprice);
      jobj.put("cd", cd);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/checkCurrentIsAdmin.act"})
  public String checkCurrentIsAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    try {
      List<JSONObject> adminObj = this.logic.getParaValueListByName(request, organization);
      String result = "false";
      JSONObject jo = new JSONObject();
      for (JSONObject jsonObject : adminObj) {
        if (jsonObject != null) {
          String drugsAdmin = jsonObject.getString("para_value");
          jo.put("adminName", drugsAdmin);
          if (drugsAdmin.contains(person.getUserId())) {
            result = "true";
            continue;
          } 
          result = "false";
          continue;
        } 
        result = "false";
      } 
      jo.put("result", result);
      YZUtility.DEAL_SUCCESS(jo, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/checkIsAdmin.act"})
  public String checkIsAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    try {
      JSONObject adminObj = this.logic.getParaValueByName(request, organization);
      String result = "false";
      JSONObject jo = new JSONObject();
      if (adminObj != null) {
        String drugsAdmin = adminObj.getString("para_value");
        jo.put("adminName", drugsAdmin);
        if (drugsAdmin.contains(person.getUserId())) {
          result = "true";
        } else {
          result = "false";
        } 
      } else {
        result = "false";
      } 
      jo.put("result", result);
      YZUtility.DEAL_SUCCESS(jo, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/goodsInSelectList.act"})
  public String goodsInSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String type = request.getParameter("type");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String inhouse = request.getParameter("inhouse");
      String intype = request.getParameter("intype");
      String supplier = request.getParameter("supplier");
      String incode = request.getParameter("incode");
      String check_status = request.getParameter("check_status");
      String status = request.getParameter("status");
      String stock_starttime = request.getParameter("stock_starttime");
      String stock_endtime = request.getParameter("stock_endtime");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(starttime))
        map.put("starttime", String.valueOf(starttime) + ConstUtil.TIME_START); 
      if (!YZUtility.isNullorEmpty(endtime))
        map.put("endtime", String.valueOf(endtime) + ConstUtil.TIME_END); 
      if (!YZUtility.isNullorEmpty(inhouse))
        map.put("inhouse", inhouse); 
      if (!YZUtility.isNullorEmpty(intype))
        map.put("intype", intype); 
      if (!YZUtility.isNullorEmpty(supplier))
        map.put("supplier", supplier); 
      if (!YZUtility.isNullorEmpty(incode))
        map.put("incode", incode); 
      if (!YZUtility.isNullorEmpty(stock_starttime))
        map.put("stock_starttime", stock_starttime); 
      if (!YZUtility.isNullorEmpty(stock_endtime))
        map.put("stock_endtime", stock_endtime); 
      if (!YZUtility.isNullorEmpty(check_status))
        map.put("check_status", check_status); 
      if (!YZUtility.isNullorEmpty(status))
        map.put("status", status); 
      if (!YZUtility.isNullorEmpty(type))
        map.put("type", type); 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = this.logic.goodsInSelectList(TableNameUtil.KQDS_CK_GOODS_IN, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteGoodsInById.act"})
  public String deleteGoodsInById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seq_id = request.getParameter("seq_id");
    try {
      this.logic.deleteGoodsInById(seq_id);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectCkGoodsDetailByInid.act"})
  public String selectCkGoodsDetailByInid(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String goodsInSeqId = request.getParameter("goodsInSeqId");
    try {
      List<KqdsCkGoodsInDetail> list = this.detailLogic.findCkGoodsDetailByInid(goodsInSeqId);
      JSONArray jsonArray = JSONArray.fromObject(list);
      YZUtility.RETURN_LIST((List)jsonArray, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/editGoodsInDetail.act"})
  public String editGoodsInDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String rkdetaildata = request.getParameter("rkdetaildata");
    YZPerson person = SessionUtil.getLoginPerson(request);
    JSONObject jsonobjDetail = JSONObject.fromObject(rkdetaildata);
    KqdsCkGoodsInDetail dp = (KqdsCkGoodsInDetail)JSONObject.toBean(jsonobjDetail, KqdsCkGoodsInDetail.class);
    try {
      KqdsCkGoodsInDetail list = this.detailLogic.selectCkGoodsDetailByInid(dp.getSeqId());
      list.setInnum(dp.getInnum());
      list.setInprice(dp.getInprice());
      list.setRkmoney(dp.getRkmoney());
      list.setPh(dp.getPh());
      list.setZczh(dp.getZczh());
      list.setYxdate(dp.getYxdate());
      this.detailLogic.updateGoodsInDetailByGoodsInSeqId(list);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_GOODS_IN, list, person.getSeqId(), 
          TableNameUtil.KQDS_CK_GOODS_IN, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
}
