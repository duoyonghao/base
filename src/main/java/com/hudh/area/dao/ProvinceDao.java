package com.hudh.area.dao;

import com.kqds.dao.DaoSupport;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceDao {
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> findAll() throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("bs_province.findAll", null);
    return list;
  }
  
  public JSONObject findProviceByProviceCode(String proviceCode) throws Exception {
    JSONObject jsonObject = (JSONObject)this.dao.findForObject("bs_province.findProviceByProviceCode", proviceCode);
    return jsonObject;
  }
}
