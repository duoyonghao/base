package com.kqds.controller.ck;

import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_Goods_DetailLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.sys.SysParaUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
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
@RequestMapping({"KQDS_Ck_Goods_DetailAct"})
public class KQDS_Ck_Goods_DetailAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_Goods_DetailAct.class);
  
  @Autowired
  private KQDS_Ck_Goods_DetailLogic logic;
  
  @RequestMapping({"/toGoodsgJ.act"})
  public ModelAndView toGoodsgJ(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/search/ck_gj_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toGoodsDetailSearch.act"})
  public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/search/goods_detail_search.jsp");
    return mv;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String sshouse = request.getParameter("sshouse");
      KqdsCkGoodsDetail dp = new KqdsCkGoodsDetail();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId)) {
        String pym = ChineseCharToEn.getAllFirstLetter(dp.getGoodsname());
        dp.setPym(pym);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, dp);
        Map<String, String> map = new HashMap<>();
        map.put("seqId", dp.getSeqId());
        map.put("house", sshouse);
        this.logic.updateGoods(map);
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_GOODS_DETAIL, dp, TableNameUtil.KQDS_CK_GOODS_DETAIL, request);
      } else {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        String pym = ChineseCharToEn.getAllFirstLetter(dp.getGoodsname());
        dp.setPym(pym);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, dp);
        KqdsCkGoods kqdsckGood = new KqdsCkGoods();
        kqdsckGood.setSeqId(YZUtility.getUUID());
        kqdsckGood.setGoodsdetailid(dp.getSeqId());
        kqdsckGood.setSshouse(sshouse);
        kqdsckGood.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, kqdsckGood);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_DETAIL, dp, TableNameUtil.KQDS_CK_GOODS_DETAIL, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/YzCode.act"})
  public String YzCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      String goodscode = request.getParameter("goodscode");
      KqdsCkGoodsDetail dp4check = new KqdsCkGoodsDetail();
      dp4check.setGoodscode(goodscode);
      dp4check.setSeqId(seqId);
      dp4check.setOrganization(ChainUtil.getCurrentOrganization(request));
      int count = this.logic.countByGoodsCode(dp4check);
      boolean flag = !(count > 0);
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/delleteGoodsYz.act"})
  public String delleteGoodsYz(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String goodstype = request.getParameter("goodstype");
      Map<String, String> map = new HashMap<>();
      map.put("goodstype", goodstype);
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      int size = this.logic.selectSizeByTypeno(map);
      boolean flag = !(size > 0);
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
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
      int incount = this.logic.getCountInByItemnos(seqId);
      if (incount > 0)
        throw new Exception("该商品存在入库记录，不能删除！"); 
      int outcount = this.logic.getCountOutByItemnos(seqId);
      if (outcount > 0)
        throw new Exception("该商品存在出库记录，不能删除！"); 
      Map<String, String> map = new HashMap<>();
      map.put("goodsdetailid", seqId);
      List<KqdsCkGoods> list = (ArrayList)this.logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
      for (KqdsCkGoods goods : list) {
        if (goods.getNums().intValue() > 0)
          throw new Exception("该商品存在库存，不能删除！"); 
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods.getSeqId());
      } 
      KqdsCkGoodsDetail en = (KqdsCkGoodsDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, seqId);
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_DETAIL, en, TableNameUtil.KQDS_CK_GOODS_DETAIL, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      KqdsCkGoodsDetail en = (KqdsCkGoodsDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, seqId);
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
  
  @RequestMapping({"/selectDetailAll.act"})
  public String selectDetailAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqIds = request.getParameter("seqIds");
      if (YZUtility.isNullorEmpty(seqIds))
        throw new Exception("主键参数值为空"); 
      String goodsname = "";
      String[] arr = seqIds.split(",");
      byte b;
      int i;
      String[] arrayOfString1;
      for (i = (arrayOfString1 = arr).length, b = 0; b < i; ) {
        String seqId = arrayOfString1[b];
        if (!YZUtility.isNullorEmpty(seqId)) {
          KqdsCkGoodsDetail en = (KqdsCkGoodsDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, seqId);
          goodsname = String.valueOf(goodsname) + en.getGoodsname() + ",";
        } 
        b++;
      } 
      if (goodsname.endsWith(","))
        goodsname = goodsname.substring(0, goodsname.length() - 1); 
      JSONObject jobj = new JSONObject();
      jobj.put("data", goodsname);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      String type = request.getParameter("type");
      String queryInput = request.getParameter("queryInput");
      String sshouse = request.getParameter("sshouse");
      String qstime = request.getParameter("qstime");
      String jztime = request.getParameter("jztime");
      String goodstype = request.getParameter("goodstype");
      String goodsname = request.getParameter("goodsname");
      String goodsuuid = request.getParameter("goodsuuid");
      String basetype = request.getParameter("basetype");
      String nexttype = request.getParameter("nexttype");
      String supplier = request.getParameter("supplier");
      String current = request.getParameter("current");
      JSONObject json = new JSONObject();
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      if (!YZUtility.isNullorEmpty(queryInput))
        map.put("queryInput", queryInput); 
      if (!YZUtility.isNullorEmpty(sshouse))
        map.put("sshouse", sshouse); 
      if (!YZUtility.isNullorEmpty(goodstype))
        map.put("goodstype", goodstype); 
      if (!YZUtility.isNullorEmpty(goodsname))
        map.put("goodsname", goodsname); 
      if (!YZUtility.isNullorEmpty(goodsuuid))
        map.put("goodsuuid", goodsuuid); 
      if (!YZUtility.isNullorEmpty(basetype))
        map.put("basetype", basetype); 
      if (!YZUtility.isNullorEmpty(nexttype) && nexttype != "")
        map.put("nexttype", nexttype); 
      if (!YZUtility.isNullorEmpty(qstime))
        map.put("qstime", qstime); 
      if (!YZUtility.isNullorEmpty(jztime))
        map.put("jztime", jztime); 
      if (!YZUtility.isNullorEmpty(supplier))
        map.put("supplier", supplier); 
      if (!YZUtility.isNullorEmpty(flag))
        map.put("flag", flag); 
      if (!YZUtility.isNullorEmpty(type))
        map.put("type", type); 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      JSONObject list = new JSONObject();
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      if (!YZUtility.isNullorEmpty(jztime) || !YZUtility.isNullorEmpty(qstime)) {
        if (!YZUtility.isNullorEmpty(sshouse)) {
          list = this.logic.selectListOrdertime(map, bp, json);
        } else {
          list = this.logic.selectNoHouseListOrdertime(map, bp, json);
        } 
      } else if (!YZUtility.isNullorEmpty(sshouse)) {
        list = this.logic.selectList(map, bp, json);
      } else {
        list = this.logic.selectNoHouseList(map, bp, json);
      } 
      JSONArray<JSONObject> jSONArray = list.getJSONArray("rows");
      if (supplier == null || supplier.equals(""))
        if (current != null && !current.equals("") && current.equals("1")) {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < jSONArray.size(); i++) {
            if (i == jSONArray.size() - 1) {
              str.append("'" + ((JSONObject)jSONArray.get(i)).getString("seq_id") + "'");
            } else {
              str.append("'" + ((JSONObject)jSONArray.get(i)).getString("seq_id") + "',");
            } 
          } 
          String stri = str.toString();
          List<JSONObject> list0 = null;
          if (!stri.equals(""))
            list0 = this.logic.selectGoodSupplierByInGoods(str.toString()); 
          Map<String, JSONObject> goodSupplierMap = new HashMap<>();
          if (list0 != null && list0.size() > 0)
            for (JSONObject obj : list0) {
              if (obj.getString("supplier") != null && !obj.getString("supplier").equals(""))
                goodSupplierMap.put(obj.getString("goodsuuid"), obj); 
            }  
          for (JSONObject good : jSONArray) {
            JSONObject obj = goodSupplierMap.get(good.get("seq_id"));
            String supplierId = "";
            String suppliername = "";
            if (obj != null) {
              supplierId = (String)obj.get("supplier");
              suppliername = (String)obj.get("suppliername");
            } 
            if (supplierId != null && !supplierId.equals("") && suppliername != null && !suppliername.equals("")) {
              good.put("supplier", supplierId);
              good.put("suppliername", suppliername);
            } 
          } 
          if (YZUtility.isNotNullOrEmpty(supplier)) {
            Iterator<JSONObject> it = jSONArray.iterator();
            while (it.hasNext()) {
              JSONObject o = it.next();
              if (o.get("supplier") != null && !o.get("supplier").equals(supplier))
                it.remove(); 
            } 
          } 
        }  
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("入库明细", fieldArr, fieldnameArr, (List)jSONArray, response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectGoodsDetailList.act"})
  public String selectGoodsDetailList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      String queryInput = request.getParameter("queryInput");
      String goodstype = request.getParameter("goodstype");
      String goodsname = request.getParameter("goodsname");
      String goodsuuid = request.getParameter("goodsuuid");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      if (!YZUtility.isNullorEmpty(queryInput))
        map.put("queryInput", queryInput); 
      if (!YZUtility.isNullorEmpty(goodstype))
        map.put("goodstype", goodstype); 
      if (!YZUtility.isNullorEmpty(goodsname))
        map.put("goodsname", goodsname); 
      if (!YZUtility.isNullorEmpty(goodsuuid))
        map.put("goodsuuid", goodsuuid); 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      JSONObject jsonOb = new JSONObject();
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      jsonOb = this.logic.selectGoodsDetailList(map, bp, jsonOb);
      JSONArray jSONArray = jsonOb.getJSONArray("rows");
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("商品资料", fieldArr, fieldnameArr, (List)jSONArray, response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(jsonOb, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectGoodsGjList.act"})
  public String selectGoodsGjList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      String queryInput = request.getParameter("queryInput");
      String sshouse = request.getParameter("sshouse");
      String goodstype = request.getParameter("goodstype");
      String goodsname = request.getParameter("goodsname");
      String goodsuuid = request.getParameter("goodsuuid");
      String gjtype = request.getParameter("gjtype");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      if (!YZUtility.isNullorEmpty(queryInput))
        map.put("queryInput", queryInput); 
      if (!YZUtility.isNullorEmpty(sshouse))
        map.put("sshouse", sshouse); 
      if (!YZUtility.isNullorEmpty(goodstype))
        map.put("goodstype", goodstype); 
      if (!YZUtility.isNullorEmpty(goodsname))
        map.put("goodsname", goodsname); 
      if (!YZUtility.isNullorEmpty(goodsuuid))
        map.put("goodsuuid", goodsuuid); 
      if (!YZUtility.isNullorEmpty(gjtype))
        map.put("gjtype", gjtype); 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = new ArrayList<>();
      if (!YZUtility.isNullorEmpty(sshouse)) {
        list = this.logic.selectGoodsGjList(map);
      } else {
        list = this.logic.selectGoodsGjNoHousseList(map);
      } 
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("库存告警", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/saveGoodsGjTx.act"})
  public String saveGoodsGjTx(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = ChainUtil.getCurrentOrganization(request);
      Map<String, String> map = new HashMap<>();
      map.put("organization", organization);
      List<JSONObject> list = this.logic.selectGoodsGjNoHousseList(map);
      String ck = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_CK_SEQID);
      this.logic.selectGoodsGjTx(list, ck, organization);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/excelStandardTemplateOut.act"})
  public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
    File f = new File(String.valueOf(ConstUtil.ROOT_DIR) + "\\model\\商品导入模板.xls");
    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    try {
      response.setHeader("Content-Disposition", "attachment;filename=" + new String("商品导入模板.xls".getBytes(), "iso-8859-1"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    ServletOutputStream out = response.getOutputStream();
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      bis = new BufferedInputStream(new FileInputStream(f));
      bos = new BufferedOutputStream((OutputStream)out);
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
        bos.write(buff, 0, bytesRead); 
    } catch (IOException e) {
      throw e;
    } finally {
      if (bis != null)
        bis.close(); 
      if (bos != null)
        bos.close(); 
    } 
  }
}
