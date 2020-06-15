package com.kqds.service.ck;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_Ck_GoodsLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject selectNoHouseListSfc(String table, Map<String, String> map, BootStrapPage bp, JSONObject json, String organization)
    throws Exception
  {
    map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoney", 18, 3));
    


    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    


    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListSfc", map);
    for (JSONObject jobj : list)
    {
      int nums = 0;
      if (!YZUtility.isNullorEmpty(jobj.getString("nums"))) {
        nums = jobj.getInt("nums");
      }
      if (nums == 0)
      {
        jobj.put("goodsprice", "0.000");
      }
      else
      {
        BigDecimal kcmoney = new BigDecimal(jobj.getString("kcmoney"));
        BigDecimal goodsprice = KqdsBigDecimal.div(kcmoney, new BigDecimal(nums));
        jobj.put("goodsprice", goodsprice);
      }
    }
    list = searchValue(list, map, organization, Boolean.valueOf(true));
    
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", list);
    return jobj1;
  }
  
  public JSONObject selectListSfc(String table, Map<String, String> map, BootStrapPage bp, JSONObject json, String organization)
    throws Exception
  {
    map.put("selectGoodsprice", SQLUtil.convertDecimal("g.goodsprice", 18, 3));
    


    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    


    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListSfc", map);
    list = searchValue(list, map, organization, Boolean.valueOf(false));
    
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", list);
    return jobj1;
  }
  
  public List<JSONObject> searchValue(List<JSONObject> list, Map<String, String> map, String organization, Boolean nohouse)
    throws Exception
  {
    for (JSONObject obj : list)
    {
      String sshouse = "";
      if (!nohouse.booleanValue()) {
        sshouse = obj.getString("sshouse");
      }
      int daorunums = 0;
      if (!YZUtility.isNullorEmpty(obj.getString("numsexport"))) {
        daorunums = obj.getInt("numsexport");
      }
      int qcrknums = rkNumsWithOneTime(sshouse, obj.getString("seqId"), (String)map.get("starttime"), organization);
      int qccknums = ckNumsWithOneTime(sshouse, obj.getString("seqId"), (String)map.get("starttime"), organization);
      int qcnums = daorunums + qcrknums - qccknums;
      obj.put("qcnums", Integer.valueOf(qcnums));
      
      BigDecimal goodspriceexport = BigDecimal.ZERO;
      if (!YZUtility.isNullorEmpty(obj.getString("goodspriceexport"))) {
        goodspriceexport = new BigDecimal(obj.getString("goodspriceexport"));
      }
      BigDecimal qcrkmoney = rkMoneyWithOneTime(sshouse, obj.getString("seqId"), (String)map.get("starttime"), organization);
      BigDecimal qcckmoney = ckMoneyWithOneTime(sshouse, obj.getString("seqId"), (String)map.get("starttime"), organization);
      BigDecimal qcmoney = goodspriceexport.add(qcrkmoney).subtract(qcckmoney).setScale(2, 5);
      obj.put("qcmoney", qcmoney.toString());
      
      BigDecimal qcsprice = null;
      if (qcnums == 0) {
        qcsprice = new BigDecimal(0.0D);
      } else {
        qcsprice = qcmoney.divide(new BigDecimal(qcnums), 2, RoundingMode.HALF_EVEN).setScale(2, 5);
      }
      obj.put("qcsprice", qcsprice.toString());
      

      int rknums = rkNums(sshouse, obj.getString("seqId"), (String)map.get("starttime"), (String)map.get("endtime"), organization);
      obj.put("rknums", Integer.valueOf(rknums));
      
      BigDecimal rkmoney = rkMoney(sshouse, obj.getString("seqId"), (String)map.get("starttime"), (String)map.get("endtime"), organization);
      obj.put("rkmoney", rkmoney.setScale(2, 5).toString());
      
      BigDecimal rksprice = null;
      if (rknums == 0) {
        rksprice = new BigDecimal(0.0D);
      } else {
        rksprice = rkmoney.divide(new BigDecimal(rknums), 2, RoundingMode.HALF_EVEN).setScale(2, 5);
      }
      obj.put("rksprice", rksprice.toString());
      

      int cknums = ckNums(sshouse, obj.getString("seqId"), (String)map.get("starttime"), (String)map.get("endtime"), organization);
      obj.put("cknums", Integer.valueOf(cknums));
      
      BigDecimal ckmoney = ckMoney(sshouse, obj.getString("seqId"), (String)map.get("starttime"), (String)map.get("endtime"), organization);
      obj.put("ckmoney", ckmoney.setScale(2, 5).toString());
      
      BigDecimal cksprice = null;
      if (cknums == 0) {
        cksprice = new BigDecimal(0.0D);
      } else {
        cksprice = ckmoney.divide(new BigDecimal(cknums), 2, RoundingMode.HALF_EVEN).setScale(2, 5);
      }
      obj.put("cksprice", cksprice.toString());
      

      int qmrknums = rkNumsWithOneTime(sshouse, obj.getString("seqId"), (String)map.get("endtime"), organization);
      int qmcknums = ckNumsWithOneTime(sshouse, obj.getString("seqId"), (String)map.get("endtime"), organization);
      int qmnums = daorunums + qmrknums - qmcknums;
      obj.put("qmnums", Integer.valueOf(qmnums));
      
      BigDecimal qmrkmoney = rkMoneyWithOneTime(sshouse, obj.getString("seqId"), (String)map.get("endtime"), organization);
      BigDecimal qmckmoney = ckMoneyWithOneTime(sshouse, obj.getString("seqId"), (String)map.get("endtime"), organization);
      BigDecimal qmmoney = goodspriceexport.add(qmrkmoney).subtract(qmckmoney).setScale(2, 5);
      obj.put("qmmoney", qmmoney.toString());
      
      BigDecimal qmsprice = null;
      if (qmnums == 0) {
        qmsprice = new BigDecimal(0.0D);
      } else {
        qmsprice = qmmoney.divide(new BigDecimal(qmnums), 2, RoundingMode.HALF_EVEN).setScale(2, 5);
      }
      obj.put("qmsprice", qmsprice.toString());
    }
    return list;
  }
  
  public int rkNumsWithOneTime(String sshouse, String goodsuuid, String starttime, String organization)
    throws Exception
  {
    int rknums = 0;
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsuuid);
    jobj.put("starttime", starttime);
    jobj.put("sshouse", sshouse);
    jobj.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".rkNumsWithOneTime", jobj);
    if ((list != null) && (list.size() > 0)) {
      rknums = YZUtility.isNullorEmpty(((JSONObject)list.get(0)).getString("rknums")) ? 0 : ((JSONObject)list.get(0)).getInt("rknums");
    }
    return rknums;
  }
  
  public BigDecimal rkMoneyWithOneTime(String sshouse, String goodsuuid, String starttime, String organization)
    throws Exception
  {
    BigDecimal rkmoney = null;
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsuuid);
    jobj.put("starttime", starttime);
    jobj.put("sshouse", sshouse);
    jobj.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".rkMoneyWithOneTime", jobj);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject json = (JSONObject)list.get(0);
      String d = YZUtility.isNullorEmpty(json.getString("rkmoney")) ? "0.00" : json.getString("rkmoney");
      rkmoney = new BigDecimal(d);
    }
    return rkmoney;
  }
  
  public int rkNums(String sshouse, String goodsuuid, String starttime, String endtime, String organization)
    throws Exception
  {
    int rknums = 0;
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsuuid);
    jobj.put("starttime", starttime);
    jobj.put("endtime", endtime);
    jobj.put("sshouse", sshouse);
    jobj.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".rknums", jobj);
    if ((list != null) && (list.size() > 0)) {
      rknums = YZUtility.isNullorEmpty(((JSONObject)list.get(0)).getString("rknums")) ? 0 : ((JSONObject)list.get(0)).getInt("rknums");
    }
    return rknums;
  }
  
  public BigDecimal rkMoney(String sshouse, String goodsuuid, String starttime, String endtime, String organization)
    throws Exception
  {
    BigDecimal rkmoney = null;
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsuuid);
    jobj.put("starttime", starttime);
    jobj.put("endtime", endtime);
    jobj.put("sshouse", sshouse);
    jobj.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".rkmoney", jobj);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject json = (JSONObject)list.get(0);
      String d = YZUtility.isNullorEmpty(json.getString("rkmoney")) ? "0.00" : json.getString("rkmoney");
      rkmoney = new BigDecimal(d);
    }
    return rkmoney;
  }
  
  public int ckNumsWithOneTime(String sshouse, String goodsuuid, String starttime, String organization)
    throws Exception
  {
    int rknums = 0;
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsuuid);
    jobj.put("starttime", starttime);
    jobj.put("sshouse", sshouse);
    jobj.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT + ".ckNumsWithOneTime", jobj);
    if ((list != null) && (list.size() > 0)) {
      rknums = YZUtility.isNullorEmpty(((JSONObject)list.get(0)).getString("rknums")) ? 0 : ((JSONObject)list.get(0)).getInt("rknums");
    }
    return rknums;
  }
  
  public BigDecimal ckMoneyWithOneTime(String sshouse, String goodsuuid, String starttime, String organization)
    throws Exception
  {
    BigDecimal ckmoney = null;
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsuuid);
    jobj.put("starttime", starttime);
    jobj.put("sshouse", sshouse);
    jobj.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT + ".ckMoneyWithOneTime", jobj);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject json = (JSONObject)list.get(0);
      String d = YZUtility.isNullorEmpty(json.getString("ckmoney")) ? "0.00" : json.getString("ckmoney");
      ckmoney = new BigDecimal(d);
    }
    return ckmoney;
  }
  
  public int ckNums(String sshouse, String goodsuuid, String starttime, String endtime, String organization)
    throws Exception
  {
    int rknums = 0;
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsuuid);
    jobj.put("starttime", starttime);
    jobj.put("endtime", endtime);
    jobj.put("sshouse", sshouse);
    jobj.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT + ".cknums", jobj);
    if ((list != null) && (list.size() > 0)) {
      rknums = YZUtility.isNullorEmpty(((JSONObject)list.get(0)).getString("rknums")) ? 0 : ((JSONObject)list.get(0)).getInt("rknums");
    }
    return rknums;
  }
  
  public BigDecimal ckMoney(String sshouse, String goodsuuid, String starttime, String endtime, String organization)
    throws Exception
  {
    BigDecimal ckmoney = null;
    JSONObject jobj = new JSONObject();
    jobj.put("goodsuuid", goodsuuid);
    jobj.put("starttime", starttime);
    jobj.put("endtime", endtime);
    jobj.put("sshouse", sshouse);
    jobj.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT + ".ckmoney", jobj);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject json = (JSONObject)list.get(0);
      String d = YZUtility.isNullorEmpty(json.getString("ckmoney")) ? "0.00" : json.getString("ckmoney");
      ckmoney = new BigDecimal(d);
    }
    return ckmoney;
  }
  
  public void updateCkGoodsByGoodsno(KqdsCkGoods dp)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_CK_GOODS + ".updateCkGoodsByGoodsno", dp);
  }
  
  public KqdsCkGoods findCkGoodsByDetailid(String detailid)
    throws Exception
  {
    KqdsCkGoods kqdsCkGoods = (KqdsCkGoods)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".findCkGoodsByDetailid", detailid);
    return kqdsCkGoods;
  }
  
  public int ckNums1(String goodsuuid)
    throws Exception
  {
    int i = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".cknums", goodsuuid)).intValue();
    return i;
  }
  
  public int ckNums2(String goodsuuid)
    throws Exception
  {
    int i = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".cknum", goodsuuid)).intValue();
    return i;
  }
  
  public void updatetCkByKsllReplaceMent(List<KqdsCkGoods> list)
    throws Exception
  {
    this.dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS + ".updatetCkByKsllReplaceMent", list);
  }
  
  public void updatetCkByKsllReplaceMents(List<KqdsCkGoods> list)
    throws Exception
  {
    this.dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS + ".updatetCkByKsllReplaceMents", list);
  }
  
  public List<JSONObject> findAllCkGoodsByGoodscode(String goodscode, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("goodscode", goodscode);
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS + ".findAllCkGoodsByGoodscode", map);
    return list;
  }
  
  public JSONObject getNumber(Map<String, String> map)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".getNumber", map);
  }
  
  public String getTypeName(Map<String, String> map)
    throws Exception
  {
    JSONObject jsonObject = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_TYPE + ".findHouse", map);
    String case1 = ChineseCharToEn.getAllFirstLetter(jsonObject.getString("house").substring(0, 2)).toUpperCase();
    return case1;
  }
  
  @Transactional
  public void unallowable(Map<String, String> map)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_CK_GOODS + ".unallowable", map);
  }
  
  @Transactional
  public JSONObject findGoodsById(String seqId)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".findGoodsById", seqId);
  }
}
