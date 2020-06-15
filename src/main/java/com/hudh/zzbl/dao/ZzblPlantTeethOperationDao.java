package com.hudh.zzbl.dao;

import com.hudh.zzbl.entity.ZzblPlantTeethOperation;
import com.kqds.dao.DaoSupport;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZzblPlantTeethOperationDao {
  @Autowired
  private DaoSupport dao;
  
  public void insertZzblPlantTeethOperation(ZzblPlantTeethOperation dp) throws Exception {
    this.dao.save("HUDH_ZZBL_PLANT_TEETH_OPERATION.insertZzblPlantTeethOperation", dp);
  }
  
  public JSONObject findZzblPlantTeethOperationById(String id) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_ZZBL_PLANT_TEETH_OPERATION.findZzblPlantTeethOperationById", id);
    return json;
  }
}
