package com.hudh.zzbl.dao;

import com.hudh.zzbl.entity.RepairSchemeConfirm;
import com.kqds.dao.DaoSupport;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairSchemeConfirmDao
{
  @Autowired
  private DaoSupport dao;
  
  public void saveRepairSchemeConfirmInfro(RepairSchemeConfirm dp)
    throws Exception
  {
    this.dao.save("HUDH_ZZBL_REPAIR_DETAIL.saveRepairSchemeConfirmInfro", dp);
  }
  
  public void deleteRepairInforById(String id)
    throws Exception
  {
    this.dao.delete("HUDH_ZZBL_REPAIR_DETAIL.deleteRepairInforById", id);
  }
  
  public void updateRepairInforById(RepairSchemeConfirm dp)
    throws Exception
  {
    this.dao.update("HUDH_ZZBL_REPAIR_DETAIL.updateRepairInforById", dp);
  }
  
  public JSONObject findRepairInforById(String id)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_ZZBL_REPAIR_DETAIL.findRepairInforById", id);
    return json;
  }
  
  public JSONObject selectRepairInforById(String id)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_ZZBL_REPAIR_DETAIL.selectRepairInforById", id);
    return json;
  }
  
  public RepairSchemeConfirm selectRepairSchemeConfirmById(String id)
    throws Exception
  {
    RepairSchemeConfirm repairSchemeConfirm = (RepairSchemeConfirm)this.dao.findForObject("HUDH_ZZBL_REPAIR_DETAIL.selectRepairSchemeConfirmById", id);
    return repairSchemeConfirm;
  }
  
  public List<JSONObject> findReapirInforAll()
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_ZZBL_REPAIR_DETAIL.findReapirInforAll", null);
    return list;
  }
}
