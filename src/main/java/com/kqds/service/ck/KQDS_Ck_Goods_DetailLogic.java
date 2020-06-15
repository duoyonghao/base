package com.kqds.service.ck;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Ck_Goods_DetailLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private YZPersonLogic personLogic;
  
  public int countByGoodsCode(KqdsCkGoodsDetail dp)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".countByGoodsCode", dp)).intValue();
    return count;
  }
  
  public JSONObject selectList(Map<String, String> map, BootStrapPage bp, JSONObject json)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    
    List<JSONObject> list = new ArrayList();
    if (((String)map.get("type")).equals("2"))
    {
      map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoneys", 18, 3));
      
      list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectList2", map);
      for (JSONObject jobj : list)
      {
        int num = 0;
        if (!YZUtility.isNullorEmpty(jobj.getString("nums"))) {
          num = jobj.getInt("nums");
        }
        if (num == 0)
        {
          jobj.put("goodsprice", "0.000");
        }
        else
        {
          BigDecimal kcmoney = new BigDecimal(jobj.getString("kcmoney"));
          BigDecimal goodsprice = KqdsBigDecimal.div(kcmoney, new BigDecimal(num));
          jobj.put("goodsprice", goodsprice);
        }
      }
    }
    else
    {
      map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoney", 18, 3));
      map.put("selectGoodsprice", SQLUtil.convertDecimal("g.goodsprice", 18, 3));
      list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectList", map);
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
    }
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectNoHouseList(Map<String, String> map, BootStrapPage bp, JSONObject json)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    
    List<JSONObject> list = new ArrayList();
    if (((String)map.get("type")).equals("2"))
    {
      map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoneys", 18, 3));
      list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseList2", map);
    }
    else
    {
      map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoney", 18, 3));
      list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseList", map);
    }
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
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", list);
    return jobj1;
  }
  
  public JSONObject selectListOrdertime(Map<String, String> map, BootStrapPage bp, JSONObject json)
    throws Exception
  {
    map.put("selectKcmoney", SQLUtil.convertDecimal("g.goodspriceexport", 18, 3));
    
    List<JSONObject> list = null;
    JSONObject jobj1 = new JSONObject();
    String sort;
    if ((map.get("flag") != null) && (map.get("supplier") != null))
    {
      list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertimeS", map);
    }
    else if ((map.get("flag") != null) && (map.get("supplier") == null))
    {
      list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertime", map);
    }
    else
    {
      String search = bp.getSearch();
      sort = bp.getSort();
      json.put("search", search);
      json.put("sort", sort);
      
      PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
      if (map.get("supplier") != null) {
        list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertimeS", map);
      } else {
        list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertime", map);
      }
      PageInfo<JSONObject> pageInfo = new PageInfo(list);
      jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    }
    for (JSONObject jobj : list)
    {
      String goodsdetailid = jobj.getString("seq_id");
      map.put("goodsdetailid", goodsdetailid);
      List<JSONObject> inobjs = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".getOneByDetailid", map);
      JSONObject outobj = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_OUT + ".getOneByDetailid", map);
      
      int nums = 0;
      BigDecimal kcmoney = BigDecimal.ZERO;
      if (!YZUtility.isNullorEmpty(jobj.getString("numsexport")))
      {
        nums = jobj.getInt("numsexport");
        kcmoney = new BigDecimal(jobj.getString("kcmoney"));
      }
      for (JSONObject inobj : inobjs) {
        if ((inobj != null) && (inobj.containsKey("nums")) && 
          (!YZUtility.isNullorEmpty(inobj.getString("nums"))))
        {
          nums += inobj.getInt("nums");
          kcmoney = kcmoney.add(new BigDecimal(inobj.getString("rkmoney")));
        }
      }
      if ((outobj != null) && (outobj.containsKey("nums")) && 
        (!YZUtility.isNullorEmpty(outobj.getString("nums"))))
      {
        nums -= outobj.getInt("nums");
        kcmoney = kcmoney.subtract(new BigDecimal(outobj.getString("rkmoney")));
      }
      jobj.put("nums", Integer.valueOf(nums));
      jobj.put("kcmoney", kcmoney);
      if (nums == 0)
      {
        jobj.put("goodsprice", "0.000");
      }
      else
      {
        BigDecimal goodsprice = KqdsBigDecimal.div(kcmoney, new BigDecimal(nums));
        jobj.put("goodsprice", goodsprice);
      }
    }
    jobj1.put("rows", list);
    






    return jobj1;
  }
  
  public JSONObject selectNoHouseListOrdertime(Map<String, String> map, BootStrapPage bp, JSONObject json)
    throws Exception
  {
    map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoney", 18, 3));
    

    List<JSONObject> list = null;
    JSONObject jobj1 = new JSONObject();
    String sort;
    if ((map.get("flag") != null) && (map.get("supplier") != null))
    {
      list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertimeS", map);
    }
    else if ((map.get("flag") != null) && (map.get("supplier") == null))
    {
      list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertime", map);
    }
    else
    {
      PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
      
      String search = bp.getSearch();
      sort = bp.getSort();
      json.put("search", search);
      json.put("sort", sort);
      if (map.get("supplier") != null) {
        list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertimeS", map);
      } else {
        list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertime", map);
      }
      PageInfo<JSONObject> pageInfo = new PageInfo(list);
      jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    }
    for (JSONObject jobj : list)
    {
      String goodsdetailid = jobj.getString("seq_id");
      map.put("goodsdetailid", goodsdetailid);
      JSONObject inobj = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN + ".getOneByDetailid", map);
      JSONObject outobj = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_OUT + ".getOneByDetailid", map);
      
      int nums = 0;
      

      BigDecimal kcmoney = BigDecimal.ZERO;
      if (!YZUtility.isNullorEmpty(jobj.getString("nums")))
      {
        nums = jobj.getInt("nums");
        kcmoney = new BigDecimal(jobj.getString("kcmoney"));
      }
      if ((inobj != null) && (inobj.containsKey("nums")) && 
        (!YZUtility.isNullorEmpty(inobj.getString("nums"))))
      {
        nums += inobj.getInt("nums");
        kcmoney = kcmoney.add(new BigDecimal(inobj.getString("rkmoney")));
      }
      if ((outobj != null) && (outobj.containsKey("nums")) && 
        (!YZUtility.isNullorEmpty(outobj.getString("nums"))))
      {
        nums -= outobj.getInt("nums");
        kcmoney = kcmoney.subtract(new BigDecimal(outobj.getString("rkmoney")));
      }
      jobj.put("nums", Integer.valueOf(nums));
      jobj.put("kcmoney", kcmoney);
      if (nums == 0)
      {
        jobj.put("goodsprice", "0.000");
      }
      else
      {
        BigDecimal goodsprice = KqdsBigDecimal.div(kcmoney, new BigDecimal(nums));
        jobj.put("goodsprice", goodsprice);
      }
    }
    jobj1.put("rows", list);
    










    return jobj1;
  }
  
  public int selectSizeByTypeno(Map<String, String> map)
    throws Exception
  {
    int size = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectSizeByTypeno", map)).intValue();
    return size;
  }
  
  public JSONObject selectGoodsDetailList(Map<String, String> map, BootStrapPage bp, JSONObject json)
    throws Exception
  {
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    

    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectGoodsDetailList", map);
    for (JSONObject jsonObject : list)
    {
      String maxgj = jsonObject.getString("maxgj");
      if ("1".equals(maxgj)) {
        jsonObject.put("maxgj", "开启");
      }
      String mingj = jsonObject.getString("mingj");
      if ("1".equals(mingj)) {
        jsonObject.put("mingj", "关闭");
      }
    }
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", list);
    return jobj1;
  }
  
  public int getCountIndex(String organization)
    throws Exception
  {
    int count = 0;
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    List<JSONObject> list = selectGoodsGjNoHousseList(map);
    if ((list != null) && (list.size() > 0)) {
      count = list.size();
    }
    return count;
  }
  
  public List<JSONObject> selectGoodsGjNoHousseList(Map<String, String> map)
    throws Exception
  {
    if (map.size() == 0) {
      throw new Exception("参数map不能为空");
    }
    if (!map.containsKey("organization")) {
      throw new Exception("库存报警查询时，门诊编号不能为空值");
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectGoodsGjNoHousseList", map);
    return list;
  }
  
  public List<JSONObject> selectGoodsGjList(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectGoodsGjList", map);
    return list;
  }
  
  public void selectGoodsGjTx(List<JSONObject> list, String ckpriv, String organization)
    throws Exception
  {
    Map<String, String> map2 = new HashMap();
    map2.put("user_priv", ckpriv);
    List<YZPerson> perlist = this.personLogic.getPersonListByRole(ckpriv, organization);
    label384:
    for (Iterator localIterator1 = list.iterator(); localIterator1.hasNext(); ???.hasNext())
    {
      JSONObject jsonObject = (JSONObject)localIterator1.next();
      String firsttype = jsonObject.getString("firsttype");
      String goodstype = jsonObject.getString("goodstype");
      String goodsname = jsonObject.getString("goodsname");
      String orgaization = jsonObject.getString("organization");
      int nums = jsonObject.getInt("nums");
      int minnums = jsonObject.getInt("minnums");
      int mingj = jsonObject.getInt("mingj");
      int maxnums = jsonObject.getInt("maxnums");
      int maxgj = jsonObject.getInt("maxgj");
      if ((1 == mingj) && (nums < minnums))
      {
        String content = "库存下限报警:" + firsttype + "-" + goodstype + " " + goodsname + ",下限报警数：" + minnums + ",当前数量：" + nums;
        for (YZPerson per : perlist) {
          PushUtil.saveTx4CKDownAlarm(per, content, orgaization);
        }
      }
      if ((1 != maxgj) || (nums <= maxnums)) {
        break label384;
      }
      String content = "库存上限报警:" + firsttype + "-" + goodstype + " " + goodsname + ",上限报警数：" + minnums + ",当前数量：" + nums;
      ??? = perlist.iterator(); continue;YZPerson per = (YZPerson)???.next();
      PushUtil.saveTx4CKTopAlarm(per, content, orgaization);
    }
  }
  
  public int getCountInByItemnos(String seqId)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectCountByGoodsuuid", YZUtility.ConvertStringIds4Query(seqId))).intValue();
    return count;
  }
  
  public int getCountOutByItemnos(String seqId)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL + ".selectCountByGoodsuuid", YZUtility.ConvertStringIds4Query(seqId))).intValue();
    return count;
  }
  
  public List<JSONObject> selectGoodSupplierByInGoods(String goodsuuid)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("goodsuuid", goodsuuid);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectGoodSupplierByInGoods", map);
    return list;
  }
  
  public JSONObject findGoodsDetailByGoodscode(String goodscode)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".findGoodsDetailByGoodscode", goodscode);
    return json;
  }
  
  public void updateGoods(Map<String, String> map)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_CK_GOODS + ".updateGoods", map);
  }
}
