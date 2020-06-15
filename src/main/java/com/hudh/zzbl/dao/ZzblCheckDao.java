package com.hudh.zzbl.dao;

import com.hudh.zzbl.entity.ZzblCheck;
import com.kqds.dao.DaoSupport;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZzblCheckDao {
  @Autowired
  private DaoSupport dao;
  
  public void insertZzblCheck(ZzblCheck dp) throws Exception {
    this.dao.save("HUDH_ZZBL_CHECK.insertZzblCheck", dp);
  }
  
  public List<JSONObject> findZzblOprationById(String id) throws Exception {
    List<JSONObject> jsonObject = (List<JSONObject>)this.dao.findForList("HUDH_ZZBL_CHECK.findZzblOprationById", id);
    return jsonObject;
  }
  
  public ZzblCheck selectZzblCheckById(String id) throws Exception {
    ZzblCheck zzblCheck = (ZzblCheck)this.dao.findForObject("HUDH_ZZBL_CHECK.selectZzblCheckById", id);
    return zzblCheck;
  }
  
  public void updateZzblOprationById(ZzblCheck dp) throws Exception {
    this.dao.update("HUDH_ZZBL_CHECK.updateZzblOprationById", dp);
  }
  
  public void deleteZzblInforById(String id) throws Exception {
    this.dao.delete("HUDH_ZZBL_CHECK.deleteZzblInforById", id);
  }
  
  public List<JSONObject> findAllZzblInfor() throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_ZZBL_CHECK.findAllZzblInfor", null);
    return list;
  }
}
