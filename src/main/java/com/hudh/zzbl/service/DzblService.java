package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.AdviceNote;
import com.hudh.zzbl.entity.FamiliarBook;
import com.hudh.zzbl.entity.LocatorFamiliar;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public abstract interface DzblService
{
  public abstract void saveCaseHistory(AdviceNote paramAdviceNote)
    throws Exception;
  
  public abstract JSONObject findCaseHistoryById(String paramString)
    throws Exception;
  
  public abstract void updateCaseHistoryById(AdviceNote paramAdviceNote)
    throws Exception;
  
  public abstract void deleteCaseHistory(String paramString)
    throws Exception;
  
  public abstract void saveFamiliarBook(FamiliarBook paramFamiliarBook)
    throws Exception;
  
  public abstract void updateFamiliarBook(FamiliarBook paramFamiliarBook)
    throws Exception;
  
  public abstract JSONObject findFamiliarBook(String paramString)
    throws Exception;
  
  public abstract void deleteFamiliarBook(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findFamiliarBookList();
  
  public abstract JSONObject findLocatorFamiliar(Map<String, Object> paramMap)
    throws Exception;
  
  public abstract Integer saveLocatorFamiliar(LocatorFamiliar paramLocatorFamiliar)
    throws Exception;
  
  public abstract void updateLocatorFamiliar(LocatorFamiliar paramLocatorFamiliar)
    throws Exception;
  
  public abstract List<JSONObject> findLocatorFamiliares(String paramString)
    throws Exception;
}
