package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.RepairSchemeConfirm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IRepairSchemeConfirmService {
  void saveRepairSchemeConfirmInfro(RepairSchemeConfirm paramRepairSchemeConfirm, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void deleteRepairInforById(String paramString) throws Exception;
  
  void updateRepairInforById(RepairSchemeConfirm paramRepairSchemeConfirm, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findRepairInforById(String paramString) throws Exception;
  
  JSONObject selectRepairInforById(String paramString) throws Exception;
  
  RepairSchemeConfirm selectRepairSchemeConfirmById(String paramString) throws Exception;
  
  List<JSONObject> findReapirInforAll() throws Exception;
}
