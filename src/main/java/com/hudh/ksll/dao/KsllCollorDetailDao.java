package com.hudh.ksll.dao;

import com.hudh.ksll.entity.KsllCollorDetail;
import com.hudh.ksll.entity.KsllCollorDetailPh;
import com.kqds.dao.DaoSupport;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KsllCollorDetailDao
{
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveCollorDetail(List<KsllCollorDetail> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_KSLL_COLLOR_DETAIL.batchSaveCollorDetail", list);
  }
  
  public List<JSONObject> findDetailByParendId(String parentId)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_KSLL_COLLOR_DETAIL.findDetailByParendId", parentId);
  }
  
  public List<JSONObject> findDetailByParendIds(String parentId)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_KSLL_COLLOR_DETAIL.findDetailByParendIds", parentId);
  }
  
  public void updateCollorDetail(Map<String, String> dataMap)
    throws Exception
  {
    this.dao.update("HUDH_KSLL_COLLOR_DETAIL.updateCollorDetailByParendId", dataMap);
  }
  
  public void updateNumsById(Map<String, String> dataMap)
    throws Exception
  {
    this.dao.update("HUDH_KSLL_COLLOR_DETAIL.updateNumsById", dataMap);
  }
  
  public void batchUpdateKsllDetailByPrimaryId(List<KsllCollorDetail> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_KSLL_COLLOR_DETAIL.batchUpdateKsllDetailByPrimaryId", list);
  }
  
  public List<JSONObject> findDetailPhByGoodscode(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_KSLL_COLLOR_DETAIL.findDetailPhByGoodscode", map);
    return list;
  }
  
  public List<KsllCollorDetailPh> findDetailPhPriceByGoodscode(Map<String, String> map)
    throws Exception
  {
    List<KsllCollorDetailPh> list = (List)this.dao.findForList("HUDH_KSLL_COLLOR_DETAIL_PH.findDetailPhPriceByGoodscode", map);
    return list;
  }
  
  public void updateDetailPhnumBySeqid(List<KsllCollorDetailPh> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_KSLL_COLLOR_DETAIL_PH.updateDetailPhnumBySeqid", list);
  }
  
  public BigDecimal findCollorDetailPriceByGoodscode(Map<String, String> map)
    throws Exception
  {
    String price = (String)this.dao.findForObject("HUDH_KSLL_COLLOR_DETAIL.findCollorDetailPriceByGoodscode", map);
    if (price == null) {
      price = "0";
    }
    return new BigDecimal(price);
  }
  
  public List<KsllCollorDetailPh> findDetailPhBySeqid(String seqId)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("seqid", seqId);
    List<KsllCollorDetailPh> list = (List)this.dao.findForList("HUDH_KSLL_COLLOR_DETAIL_PH.findDetailPhBySeqid", map);
    return list;
  }
  
  public void updateDetailAddPhnumBySeqid(List<KsllCollorDetailPh> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_KSLL_COLLOR_DETAIL_PH.updateDetailAddPhnumBySeqid", list);
  }
}
