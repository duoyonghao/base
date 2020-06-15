package com.hudh.ksll.dao;

import com.hudh.ksll.entity.KsllCollor;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KsllCollorDao {
  @Autowired
  private DaoSupport dao;
  
  public int insertCollor(KsllCollor ksllCollor) throws Exception {
    return ((Integer)this.dao.save("HUDH_KSLL_COLLOR.insertCollor", ksllCollor)).intValue();
  }
  
  public List<JSONObject> findAllCollor(Map<String, String> dataMap) throws Exception {
    return (List<JSONObject>)this.dao.findForList("HUDH_KSLL_COLLOR.findAllCollor", dataMap);
  }
  
  public void updateCollorStatus(Map<String, String> dataMap) throws Exception {
    this.dao.delete("HUDH_KSLL_COLLOR.updateCollorStatus", dataMap);
  }
  
  public List<JSONObject> findAllCKGoods() throws Exception {
    return (List<JSONObject>)this.dao.findForList("HUDH_KSLL_COLLOR.findAllCKGoods", null);
  }
  
  public String findCKGoodSshouse(Map<String, String> map) throws Exception {
    String sshouse = (String)this.dao.findForObject("HUDH_KSLL_COLLOR.findCKGoodSshouse", map);
    return sshouse;
  }
}
