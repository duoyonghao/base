package com.hudh.ksll.dao;

import com.hudh.ksll.entity.KsllReplaceMent;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KsllReplaceMentDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertReplacement(KsllReplaceMent ksllReplaceMent)
    throws Exception
  {
    return ((Integer)this.dao.save("HUDH_KSLL_REPLACEMENT.insertReplacement", ksllReplaceMent)).intValue();
  }
  
  public List<JSONObject> findAllReplacement(Map<String, String> dataMap)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_KSLL_REPLACEMENT.findAllReplacement", dataMap);
  }
  
  public void updateReplacementStatus(Map<String, String> dataMap)
    throws Exception
  {
    this.dao.delete("HUDH_KSLL_REPLACEMENT.updateReplacementStatus", dataMap);
  }
  
  public void batchUpdateCKGoodsByPrimaryId(List<KqdsCkGoods> kqdsCkGoods)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_KSLL_REPLACEMENT.batchUpdateCKGoodsByPrimaryId", kqdsCkGoods);
  }
  
  public KsllReplaceMent findReplacementById(String id, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("id", id);
    map.put("organization", organization);
    return (KsllReplaceMent)this.dao.findForObject("HUDH_KSLL_REPLACEMENT.findReplacementById", map);
  }
}
