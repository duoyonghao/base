package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljOrder;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljDao {
  @Autowired
  private DaoSupport dao;
  
  public int saveLcljOrder(LcljOrder lcljOrder) throws Exception {
    int rsCount = ((Integer)this.dao.save("HUDH_LCLJ_ORDER.insertLcljOrder", lcljOrder)).intValue();
    return rsCount;
  }
  
  public JSONObject findLcljOrderByBlcode(String blCode) throws Exception {
    JSONObject lcljOrder = (JSONObject)this.dao.findForObject("HUDH_LCLJ_ORDER.findLcljOrderByBlcode", blCode);
    return lcljOrder;
  }
  
  public List<JSONObject> findLcljOrderByBlcodeAndStu(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_LCLJ_ORDER.findLcljOrderByBlcodeAndStu", map);
    return list;
  }
  
  public LcljOrder findLcljOrderByOrderNumber(String orderNumber) throws Exception {
    LcljOrder lcljOrder = (LcljOrder)this.dao.findForObject("HUDH_LCLJ_ORDER.findLcljOrderByOrderNumber", orderNumber);
    return lcljOrder;
  }
  
  public LcljOrder findNextOrderNumber() throws Exception {
    LcljOrder lcljOrder = (LcljOrder)this.dao.findForObject("HUDH_LCLJ_ORDER.findNextOrderNumber", null);
    return lcljOrder;
  }
  
  public int findHasOrder(Map<String, String> map) throws Exception {
    int rsCount = ((Integer)this.dao.findForObject("HUDH_LCLJ_ORDER.findHasOrder", map)).intValue();
    return rsCount;
  }
  
  public void updateRemaintooth(Map map) throws Exception {
    this.dao.update("HUDH_LCLJ_ORDER.updateRemaintooth", map);
  }
  
  public void updateOrderStatus(String orderNumber) throws Exception {
    this.dao.update("HUDH_LCLJ_ORDER.updateOrderStatus", orderNumber);
  }
}
