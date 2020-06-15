package com.kqds.controller.ck;

import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsIn;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.entity.base.KqdsCkGoodsOut;
import com.kqds.entity.base.KqdsCkGoodsOutDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_GoodsLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_InLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_OutLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.util.ArrayList;
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
@RequestMapping({"KQDS_Ck_Goods_OutAct"})
public class KQDS_Ck_Goods_OutAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_Goods_OutAct.class);
  
  @Autowired
  private KQDS_Ck_Goods_OutLogic logic;
  
  @Autowired
  private KQDS_Ck_Goods_InLogic inLogic;
  
  @Autowired
  private KQDS_Ck_GoodsLogic glogic;
  
  @RequestMapping({"/toCkGoodsOut.act"})
  public ModelAndView toCkDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/goodsOut/out_goods.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCkGoodsOut2.act"})
  public ModelAndView toCkGoodsOut2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/childhouse/outGoods/out_goods2.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCkOutGoodsSearch.act"})
  public ModelAndView toCkOutGoodsSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/search/out_goods_search.jsp");
    return mv;
  }
  
  @RequestMapping({"/findGoodsprice.act"})
  public String findGoodsprice(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String outnums = request.getParameter("outnums");
      String goodsuuid = request.getParameter("goodsuuid");
      String ph = request.getParameter("ph");
      String type = request.getParameter("type");
      int outNum = Integer.parseInt(outnums);
      int out = outNum;
      if (!YZUtility.isNullorEmpty(ph) && !ph.equals("请选择")) {
        JSONObject json = this.inLogic.selectPriceByPh(goodsuuid, ph, outNum, type);
        YZUtility.RETURN_OBJ(json, response, logger);
      } else {
        Map<String, String> map1 = new HashMap<>();
        map1.put("goodsuuid", goodsuuid);
        map1.put("type", type);
        List<JSONObject> list1 = this.inLogic.inList(map1);
        int innum = this.inLogic.selectNum(map1);
        int unnum = 0;
        if (type.equals("2")) {
          unnum = this.glogic.ckNums2(goodsuuid);
        } else {
          unnum = this.glogic.ckNums1(goodsuuid);
        } 
        int outnum = innum - unnum;
        BigDecimal money = new BigDecimal(0.0D);
        Map<String, String> map = new HashMap<>();
        if (outNum == unnum) {
          int num = 0;
          int n = 0;
          for (int i = 0; i < list1.size(); i++) {
            int in1 = ((JSONObject)list1.get(i)).getInt("innum");
            if (in1 + num < outnum) {
              num += in1;
            } else {
              n = i;
              break;
            } 
          } 
          int dan = outnum - num;
          int i1 = ((JSONObject)list1.get(n)).getInt("innum") - dan;
          if (i1 == 0) {
            String inprice = (String)((JSONObject)list1.get(n + 1)).get("inprice");
            money = (new BigDecimal(inprice)).multiply(new BigDecimal(((JSONObject)list1.get(n + 1)).getInt("innum")));
            for (int j = n + 2; j < list1.size(); j++) {
              if (((JSONObject)list1.get(j)).getInt("innum") <= outNum - i1) {
                String inprice1 = (String)((JSONObject)list1.get(j)).get("inprice");
                money = money.add((new BigDecimal(inprice1)).multiply(new BigDecimal(((JSONObject)list1.get(j)).getInt("innum"))));
                i1 += ((JSONObject)list1.get(j)).getInt("innum");
              } 
            } 
          } else if (i1 > 0) {
            String inprice = (String)((JSONObject)list1.get(n)).get("inprice");
            money = (new BigDecimal(inprice)).multiply(new BigDecimal(i1));
            for (int j = n + 1; j < list1.size(); j++) {
              if (((JSONObject)list1.get(j)).getInt("innum") <= outNum - i1) {
                String inprice1 = (String)((JSONObject)list1.get(j)).get("inprice");
                money = money.add((new BigDecimal(inprice1)).multiply(new BigDecimal(((JSONObject)list1.get(j)).getInt("innum"))));
                i1 += ((JSONObject)list1.get(j)).getInt("innum");
              } 
            } 
          } 
          map.put("goodsprice", (new BigDecimal((String)money)).divide(new BigDecimal((new StringBuilder(String.valueOf(out))).toString()), 3, 4).toString());
          map.put("ckmoney", money);
          YZUtility.RETURN_OBJ(map, response, logger);
        } else if (outNum < unnum) {
          int num = 0;
          int n = 0;
          for (int i = 0; i < list1.size(); i++) {
            int in1 = ((JSONObject)list1.get(i)).getInt("innum");
            if (in1 + num <= outnum) {
              num += in1;
            } else {
              n = i;
              break;
            } 
          } 
          int dan = 0;
          if (num == 0) {
            dan = 0;
          } else if (num > 0) {
            dan = outnum - num;
          } 
          if (((JSONObject)list1.get(n)).getInt("innum") < outNum && dan == 0) {
            money = (new BigDecimal((String)((JSONObject)list1.get(n)).get("inprice"))).multiply(new BigDecimal(((JSONObject)list1.get(n)).getInt("innum")));
            outNum -= ((JSONObject)list1.get(n)).getInt("innum");
            for (int j = n + 1; j < list1.size(); j++) {
              if (((JSONObject)list1.get(j)).getInt("innum") >= outNum) {
                money = money.add((new BigDecimal((String)((JSONObject)list1.get(j)).get("inprice"))).multiply(new BigDecimal(outNum)));
              } else {
                money = money.add((new BigDecimal((String)((JSONObject)list1.get(j)).get("inprice"))).multiply(new BigDecimal(((JSONObject)list1.get(j)).getInt("innum"))));
                outNum -= ((JSONObject)list1.get(j)).getInt("innum");
              } 
            } 
          } else if (((JSONObject)list1.get(n)).getInt("innum") >= outNum && dan == 0 && ((JSONObject)list1.get(n)).get("inprice") != null && !((JSONObject)list1.get(n)).get("inprice").equals("")) {
            money = (new BigDecimal((String)((JSONObject)list1.get(n)).get("inprice"))).multiply(new BigDecimal(outNum));
          } else if (((JSONObject)list1.get(n)).getInt("innum") - dan >= outNum && dan > 0 && ((JSONObject)list1.get(n)).get("inprice") != null && !((JSONObject)list1.get(n)).get("inprice").equals("")) {
            money = (new BigDecimal((String)((JSONObject)list1.get(n)).get("inprice"))).multiply(new BigDecimal(outNum));
          } else if (((JSONObject)list1.get(n)).getInt("innum") - dan < outNum && dan > 0 && ((JSONObject)list1.get(n)).get("inprice") != null && !((JSONObject)list1.get(n)).get("inprice").equals("")) {
            money = (new BigDecimal((String)((JSONObject)list1.get(n)).get("inprice"))).multiply(new BigDecimal(((JSONObject)list1.get(n)).getInt("innum") - dan));
            outNum -= ((JSONObject)list1.get(n)).getInt("innum") - dan;
            for (int j = n + 1; j < list1.size(); j++) {
              if (((JSONObject)list1.get(j)).getInt("innum") >= outNum) {
                money = money.add((new BigDecimal((String)((JSONObject)list1.get(j)).get("inprice"))).multiply(new BigDecimal(outNum)));
              } else {
                money = money.add((new BigDecimal((String)((JSONObject)list1.get(j)).get("inprice"))).multiply(new BigDecimal(((JSONObject)list1.get(j)).getInt("innum"))));
                outNum -= ((JSONObject)list1.get(j)).getInt("innum");
              } 
            } 
          } 
          map.put("goodsprice", (new BigDecimal((String)money)).divide(new BigDecimal((new StringBuilder(String.valueOf(out))).toString()), 3, 4).toString());
          map.put("ckmoney", money);
          YZUtility.RETURN_OBJ(map, response, logger);
        } 
      } 
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String menzhen = ChainUtil.getCurrentOrganization(request);
      KqdsCkGoodsOut dp = new KqdsCkGoodsOut();
      BeanUtils.populate(dp, request.getParameterMap());
      String ll = this.logic.insertOut(request, response, person, menzhen, dp);
      if (ll == null)
        return null; 
      String params = request.getParameter("paramDetail");
      params = URLDecoder.decode(params, "UTF-8");
      JSONArray jArray = JSONArray.fromObject(params);
      List<KqdsCkGoodsOutDetail> jList = (List<KqdsCkGoodsOutDetail>)JSONArray.toCollection(jArray, KqdsCkGoodsOutDetail.class);
      String params1 = request.getParameter("paramDetail1");
      params1 = URLDecoder.decode(params1, "UTF-8");
      JSONArray jArray1 = JSONArray.fromObject(params1);
      List<JSONObject> numList = (List<JSONObject>)JSONArray.toCollection(jArray1, JSONObject.class);
      if (dp.getOuttype().equals(new Integer(10)) && 
        numList.size() > 0) {
        KqdsCkGoodsIn in = new KqdsCkGoodsIn();
        in.setSeqId(YZUtility.getUUID());
        in.setIncode(request.getParameter("incode"));
        in.setOrganization(menzhen);
        in.setCreateuser(dp.getCreateuser());
        in.setShtime(dp.getCreatetime());
        in.setRktime(dp.getCreatetime());
        in.setCreatetime(YZUtility.getCurDateTimeStr());
        in.setAuditor(person.getSeqId());
        in.setRkr(person.getSeqId());
        in.setInstatus(Integer.valueOf(3));
        in.setCheck_status(0);
        in.setStatus(0);
        in.setIntype(Integer.valueOf(4));
        in.setType("2");
        List<KqdsCkGoodsInDetail> list = new ArrayList<>();
        for (JSONObject json : numList) {
          KqdsCkGoodsInDetail detail = new KqdsCkGoodsInDetail();
          detail.setSeqId(YZUtility.getUUID());
          if (request.getParameter("incode") != null && !request.getParameter("incode").equals(""))
            detail.setIncode(request.getParameter("incode")); 
          detail.setOrganization(menzhen);
          detail.setCreateuser(person.getSeqId());
          detail.setCreatetime(YZUtility.getCurDateTimeStr());
          detail.setGoodsInSeqId(in.getSeqId());
          detail.setAuditStatus(0);
          detail.setInnum(Integer.valueOf(Integer.parseInt(json.getString("cknum"))));
          detail.setInprice(new BigDecimal(json.getString("inprice")));
          detail.setPh(json.getString("ph"));
          detail.setPhnum(Integer.parseInt(json.getString("cknum")));
          detail.setRkmoney((new BigDecimal(json.getString("cknum"))).multiply(new BigDecimal(json.getString("inprice"))));
          detail.setYxdate(json.getString("yxdate"));
          detail.setGoodsuuid(json.getString("goodsuuid"));
          detail.setType("2");
          list.add(detail);
        } 
        insert(in, list);
      } 
      this.logic.updateDetailByOutnum(person, menzhen, dp, jList, numList);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
      BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_OUT, dp, TableNameUtil.KQDS_CK_GOODS_OUT, request);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/editGoodsOut.act"})
  public String editGoodsOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String ckddata = request.getParameter("ckddata");
      String ckdetaildata = request.getParameter("ckdetaildata");
      JSONObject jsonobj = JSONObject.fromObject(ckddata);
      KqdsCkGoodsOut inobj = (KqdsCkGoodsOut)JSONObject.toBean(jsonobj, KqdsCkGoodsOut.class);
      KqdsCkGoodsOut goodsin = (KqdsCkGoodsOut)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, inobj.getSeqId());
      if (goodsin == null)
        throw new Exception("出库单不存在"); 
      String oldhouse = goodsin.getOuthouse();
      String nowhouse = inobj.getOuthouse();
      goodsin.setOuttype(inobj.getOuttype());
      goodsin.setSupplier(inobj.getSupplier());
      goodsin.setOuthouse(inobj.getOuthouse());
      goodsin.setOutremark(inobj.getOutremark());
      goodsin.setZhaiyao(inobj.getZhaiyao());
      goodsin.setLlr(inobj.getLlr());
      goodsin.setSqdoctor(inobj.getSqdoctor());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, goodsin);
      BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_GOODS_OUT, goodsin, TableNameUtil.KQDS_CK_GOODS_OUT, request);
      JSONObject jsonobj2 = JSONObject.fromObject(ckdetaildata);
      KqdsCkGoodsOutDetail inobjdetail = (KqdsCkGoodsOutDetail)JSONObject.toBean(jsonobj2, KqdsCkGoodsOutDetail.class);
      KqdsCkGoodsOutDetail goodsinDetal = (KqdsCkGoodsOutDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, inobjdetail.getSeqId());
      if (goodsinDetal == null)
        throw new Exception("出库单明细不存在"); 
      int oldoutnum = goodsinDetal.getOutnum().intValue();
      BigDecimal oldckmoney = goodsinDetal.getCkmoney();
      goodsinDetal.setOutnum(inobjdetail.getOutnum());
      goodsinDetal.setOutprice(inobjdetail.getOutprice());
      goodsinDetal.setCkmoney(inobjdetail.getCkmoney());
      goodsinDetal.setSqremark(inobjdetail.getSqremark());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, goodsinDetal);
      String goodsdata = request.getParameter("goodsdata");
      JSONObject jsonobj4 = JSONObject.fromObject(goodsdata);
      KqdsCkGoods goodsobj = (KqdsCkGoods)JSONObject.toBean(jsonobj4, KqdsCkGoods.class);
      KqdsCkGoods goods = (KqdsCkGoods)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS, goodsobj.getSeqId());
      if (goods == null)
        throw new Exception("本仓库没有该商品的仓库"); 
      goods.setGoodsprice(goodsobj.getGoodsprice());
      goods.setNums(goodsobj.getNums());
      goods.setKcmoney(goodsobj.getKcmoney());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
      if (!oldhouse.equals(nowhouse)) {
        Map<String, String> map = new HashMap<>();
        map.put("sshouse", oldhouse);
        map.put("goodsdetailid", goods.getGoodsdetailid());
        List<KqdsCkGoods> en = (List<KqdsCkGoods>)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
        KqdsCkGoods oldgoods = en.get(0);
        oldgoods.setNums(Integer.valueOf(oldgoods.getNums().intValue() + oldoutnum));
        oldgoods.setKcmoney(oldgoods.getKcmoney().add(oldckmoney));
        BigDecimal goodsprice = null;
        if (oldgoods.getNums().intValue() == 0) {
          goodsprice = BigDecimal.ZERO;
        } else {
          goodsprice = oldgoods.getKcmoney().divide(new BigDecimal(oldgoods.getNums().intValue()), 2, RoundingMode.HALF_EVEN).setScale(2, 5);
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
  
  @RequestMapping({"/deleteAllGoodsOut.act"})
  public String deleteAllGoodsOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String outseqId = request.getParameter("outseqId");
      KqdsCkGoodsOut goodsin = (KqdsCkGoodsOut)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, outseqId);
      if (goodsin == null)
        throw new Exception("出库单不存在"); 
      Map<String, String> map = new HashMap<>();
      map.put("outcode", goodsin.getOutcode());
      List<KqdsCkGoodsOutDetail> detailList = (List<KqdsCkGoodsOutDetail>)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, map);
      if (detailList == null || detailList.size() == 0)
        throw new Exception("出库单明细不存在"); 
      for (KqdsCkGoodsOutDetail detail : detailList) {
        Map<String, String> map2 = new HashMap<>();
        map2.put("sshouse", goodsin.getOuthouse());
        map2.put("goodsdetailid", detail.getGoodsuuid());
        List<KqdsCkGoods> goodsList = (List<KqdsCkGoods>)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS, map2);
        if (goodsList == null || goodsList.size() == 0)
          throw new Exception("库存信息不存在"); 
        KqdsCkGoods goods = goodsList.get(0);
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, goodsin.getSeqId());
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, detail.getSeqId());
        BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_OUT_DETAIL, detail, TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, request);
        goods.setNums(Integer.valueOf(goods.getNums().intValue() + detail.getOutnum().intValue()));
        goods.setKcmoney(goods.getKcmoney().add(detail.getCkmoney()));
        BigDecimal goodsprice = BigDecimal.ZERO;
        if (goods.getNums().intValue() != 0)
          goodsprice = goods.getKcmoney().divide(new BigDecimal(goods.getNums().intValue()), 2, RoundingMode.HALF_EVEN).setScale(2, 5); 
        goods.setGoodsprice(goodsprice);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteGoodsOut.act"})
  public String deleteGoodsOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String outdetailseqId = request.getParameter("outdetailseqId");
      KqdsCkGoodsOutDetail goodsinDetal = (KqdsCkGoodsOutDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, outdetailseqId);
      if (goodsinDetal == null)
        throw new Exception("入库单明细不存在"); 
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, outdetailseqId);
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_OUT_DETAIL, goodsinDetal, TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, request);
      Map<String, String> map = new HashMap<>();
      map.put("outcode", goodsinDetal.getOutcode());
      List<KqdsCkGoodsOutDetail> en = (List<KqdsCkGoodsOutDetail>)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, map);
      if (en == null || en.size() == 0) {
        String inseqId = request.getParameter("outseqId");
        KqdsCkGoodsOut goodsin = (KqdsCkGoodsOut)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, inseqId);
        if (goodsin == null)
          throw new Exception("入库单不存在"); 
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, inseqId);
      } 
      String goodsseqId = request.getParameter("goodsseqId");
      KqdsCkGoods goods = (KqdsCkGoods)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS, goodsseqId);
      if (goods == null)
        throw new Exception("库存信息不存在"); 
      goods.setNums(Integer.valueOf(goods.getNums().intValue() + goodsinDetal.getOutnum().intValue()));
      goods.setKcmoney(goods.getKcmoney().add(goodsinDetal.getCkmoney()));
      BigDecimal goodsprice = BigDecimal.ZERO;
      if (goods.getNums().intValue() != 0)
        goodsprice = goods.getKcmoney().divide(new BigDecimal(goods.getNums().intValue()), 2, RoundingMode.HALF_EVEN).setScale(2, 5); 
      goods.setGoodsprice(goodsprice);
      this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/inSearchList.act"})
  public String inSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      String type = request.getParameter("type");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String outhouse = request.getParameter("outhouse");
      String outtype = request.getParameter("outtype");
      String supplier = request.getParameter("supplier");
      String sqdeptid = request.getParameter("sqdeptid");
      String llr = request.getParameter("llr");
      String sqdoctor = request.getParameter("sqdoctor");
      String outcode = request.getParameter("outcode");
      if (!YZUtility.isNullorEmpty(starttime))
        map.put("starttime", String.valueOf(starttime) + ConstUtil.TIME_START); 
      if (!YZUtility.isNullorEmpty(endtime))
        map.put("endtime", String.valueOf(endtime) + ConstUtil.TIME_END); 
      if (!YZUtility.isNullorEmpty(outhouse))
        map.put("outhouse", outhouse); 
      if (!YZUtility.isNullorEmpty(outtype))
        map.put("outtype", outtype); 
      if (!YZUtility.isNullorEmpty(supplier))
        map.put("supplier", supplier); 
      if (!YZUtility.isNullorEmpty(sqdeptid))
        map.put("sqdeptid", sqdeptid); 
      if (!YZUtility.isNullorEmpty(llr))
        map.put("llr", llr); 
      if (!YZUtility.isNullorEmpty(sqdoctor))
        map.put("sqdoctor", sqdoctor); 
      if (!YZUtility.isNullorEmpty(outcode))
        map.put("outcode", outcode); 
      if (!YZUtility.isNullorEmpty(type))
        map.put("type", type); 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = this.logic.inSearchList(TableNameUtil.KQDS_CK_GOODS_OUT, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectBydeptid.act"})
  public String selectBydeptid(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String deptid = request.getParameter("deptid");
      Map<String, String> map = new HashMap<>();
      map.put("sqdeptid", deptid);
      List<KqdsCkGoodsOut> en = (ArrayList)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS_OUT, map);
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  public String insert(KqdsCkGoodsIn dp, List<KqdsCkGoodsInDetail> detail) throws Exception {
    try {
      this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, dp);
      for (KqdsCkGoodsInDetail kdetail : detail)
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, kdetail); 
    } catch (Exception e) {
      throw new Exception(e);
    } 
    return null;
  }
}
