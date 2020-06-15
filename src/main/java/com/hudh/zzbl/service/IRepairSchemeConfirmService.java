package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.RepairSchemeConfirm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IRepairSchemeConfirmService
{
  public abstract void saveRepairSchemeConfirmInfro(RepairSchemeConfirm paramRepairSchemeConfirm, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void deleteRepairInforById(String paramString)
    throws Exception;
  
  public abstract void updateRepairInforById(RepairSchemeConfirm paramRepairSchemeConfirm, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract JSONObject findRepairInforById(String paramString)
    throws Exception;
  
  public abstract JSONObject selectRepairInforById(String paramString)
    throws Exception;
  
  public abstract RepairSchemeConfirm selectRepairSchemeConfirmById(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findReapirInforAll()
    throws Exception;
}
