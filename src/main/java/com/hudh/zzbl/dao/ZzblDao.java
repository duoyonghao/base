package com.hudh.zzbl.dao;

import com.hudh.zzbl.entity.ZzblOperation;
import com.kqds.dao.DaoSupport;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZzblDao {
  @Autowired
  private DaoSupport dao;
  
  public void save(ZzblOperation dp) throws Exception {
    this.dao.save("HUDH_ZZBL_DETAIL.save", dp);
  }
  
  public List<ZzblOperation> findZzblOprationById(String id) throws Exception {
    List<ZzblOperation> json = (List<ZzblOperation>)this.dao.findForList("HUDH_ZZBL_DETAIL.findZzblOprationById", id);
    return json;
  }
  
  public JSONObject selectZzblOperationById(String id) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_ZZBL_DETAIL.selectZzblOperationById", id);
    return json;
  }
  
  public void updateZzblOprationById(ZzblOperation dp) throws Exception {
    this.dao.update("HUDH_ZZBL_DETAIL.updateZzblOprationById", dp);
  }
  
  public void updateZzblOperationByOrderNumber(ZzblOperation dp) throws Exception {
    this.dao.update("HUDH_ZZBL_DETAIL.updateZzblOperationByOrderNumber", dp);
  }
  
  public void deleteZzblInforById(String id) throws Exception {
    this.dao.delete("HUDH_ZZBL_DETAIL.deleteZzblInforById", id);
  }
  
  public List<JSONObject> findAllZzblInfor() throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_ZZBL_DETAIL.findAllZzblInfor", null);
    return list;
  }
}
