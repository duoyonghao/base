package com.hudh.dzbl.dao;

import com.hudh.dzbl.entity.DzblTemplate;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DzblTemplateDao {
  @Autowired
  private DaoSupport dao;
  
  public int insertDzblTemplate(DzblTemplate dzblTemplate) throws Exception {
    int rsCount = ((Integer)this.dao.save("HUDH_DZBL_TEMPLATE.insertDzblTemplate", dzblTemplate)).intValue();
    return rsCount;
  }
  
  public List<JSONObject> findDzblTemplate(Map<String, String> dataMap) throws Exception {
    List<JSONObject> dzblTemplate = (List<JSONObject>)this.dao.findForList("HUDH_DZBL_TEMPLATE.findDzblTemplate", dataMap);
    return dzblTemplate;
  }
  
  public List<JSONObject> findBcByBlfl(Map<String, String> dataMap) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_DZBL_TEMPLATE.findBcByBlfl", dataMap);
    return list;
  }
  
  public JSONObject findDzblTemplateById(String id) throws Exception {
    JSONObject jo = (JSONObject)this.dao.findForObject("HUDH_DZBL_TEMPLATE.findDzblTemplateById", id);
    return jo;
  }
  
  public void updateDzblTempleById(DzblTemplate dzblTemplate) throws Exception {
    this.dao.update("HUDH_DZBL_TEMPLATE.updateDzblTempleById", dzblTemplate);
  }
}
