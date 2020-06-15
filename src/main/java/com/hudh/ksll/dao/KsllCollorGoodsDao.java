package com.hudh.ksll.dao;

import com.hudh.ksll.entity.KsllGoods;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkHouse;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KsllCollorGoodsDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertKsllGoods(KsllGoods KsllGoods)
    throws Exception
  {
    return ((Integer)this.dao.save("HUDH_KSLL_GOODS.insertKsllGoods", KsllGoods)).intValue();
  }
  
  public List<JSONObject> findAllKsllGoods(Map<String, String> dataMap)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_KSLL_GOODS.findAllKsllGoods", dataMap);
  }
  
  public List<JSONObject> findAllKsllColorGoodsByGoodscode(Map<String, String> dataMap)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_KSLL_GOODS.findAllKsllColorGoodsByGoodscode", dataMap);
  }
  
  public List<JSONObject> findAllCKDept(Map<String, String> dataMap)
    throws Exception
  {
    return (List)this.dao.findForList("KQDS_CK_DEPT.selectList", dataMap);
  }
  
  public List<KqdsCkHouse> findAllCKHouse(Map<String, Map<String, String>> dataMap)
    throws Exception
  {
    return (List)this.dao.findForList("KQDS_CK_HOUSE.selectBeanListByMap", dataMap);
  }
  
  public void batchSaveKsllGoodsDetail(List<KsllGoods> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_KSLL_GOODS.batchSaveKsllGoodsDetail", list);
  }
  
  public void batchUpdateKsllGoodsByPrimaryId(List<KsllGoods> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_KSLL_GOODS.batchUpdateKsllGoodsByPrimaryId", list);
  }
  
  public JSONObject findKsllGoodsByPrimaryId(String id)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_KSLL_GOODS.findKsllGoodsByPrimaryId", id);
    return json;
  }
  
  public void updateKsllGoodsByPrimaryId(KsllGoods ksllGoods)
    throws Exception
  {
    this.dao.update("HUDH_KSLL_GOODS.updateKsllGoodsByPrimaryId", ksllGoods);
  }
  
  public KsllGoods findKsllGoodsByGoodsCode(String goodsCode)
    throws Exception
  {
    KsllGoods ksllGoods = (KsllGoods)this.dao.findForObject("HUDH_KSLL_GOODS.findKsllGoodsByGoodsCode", goodsCode);
    return ksllGoods;
  }
  
  public KsllGoods findKsllGoodsByDeptpartAndGoodscode(Map<String, String> map)
    throws Exception
  {
    KsllGoods ksllGoods = (KsllGoods)this.dao.findForObject("HUDH_KSLL_GOODS.findKsllGoodsByDeptpartAndGoodscode", map);
    return ksllGoods;
  }
}
