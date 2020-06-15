package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzManufacturers;
import com.kqds.dao.DaoSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzManufacturersDao {
  @Autowired
  private DaoSupport dao;
  
  public int insertManufacturers(YkzzManufacturers ykzzManufacturers) throws Exception {
    int i = ((Integer)this.dao.save("HUDH_YKZZ_MANUFACTURERS.insert", ykzzManufacturers)).intValue();
    return i;
  }
  
  public void deleteManufacturers(String id) throws Exception {
    this.dao.delete("HUDH_YKZZ_MANUFACTURERS.deleteByPrimaryKey", id);
  }
  
  public void updateManufacturers(YkzzManufacturers ykzzManufacturers) throws Exception {
    this.dao.update("HUDH_YKZZ_MANUFACTURERS.updateByPrimaryKeySelective", ykzzManufacturers);
  }
  
  public List<JSONObject> findAllManufacturers(String organization) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("organization", organization);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_YKZZ_MANUFACTURERS.findAllManufacturers", dataMap);
    return list;
  }
  
  public JSONObject findManufacturersById(String id) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_YKZZ_MANUFACTURERS.selectByPrimaryKey", id);
    return json;
  }
  
  public JSONObject findManufacturersByCode(String manufacturersCode) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_YKZZ_MANUFACTURERS.findManufacturersByCode", manufacturersCode);
    return json;
  }
}
