package com.hudh.zzbl.dao;

import com.hudh.zzbl.entity.AdviceNote;
import com.hudh.zzbl.entity.FamiliarBook;
import com.hudh.zzbl.entity.LocatorFamiliar;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DzblDao
{
  @Autowired
  private DaoSupport dao;
  
  public void saveCaseHistory(AdviceNote adviceNote)
    throws Exception
  {
    this.dao.save("HUDH_DZBL_AdviceNote.saveCaseHistory", adviceNote);
  }
  
  public JSONObject findCaseHistoryById(String id)
    throws Exception
  {
    JSONObject caseHistory = (JSONObject)this.dao.findForObject("HUDH_DZBL_AdviceNote.findCaseHistoryById", id);
    return caseHistory;
  }
  
  public void updateCaseHistoryById(AdviceNote adviceNote)
    throws Exception
  {
    this.dao.update("HUDH_DZBL_AdviceNote.updateCaseHistoryById", adviceNote);
  }
  
  public void deleteCaseHistoryById(String id)
    throws Exception
  {
    this.dao.deleteSingleUUID("HUDH_DZBL_AdviceNote.deleteCaseHistoryById", id);
  }
  
  public void saveFamiliarBook(FamiliarBook familiarBook)
    throws Exception
  {
    this.dao.save("HUDH_DZBL_FamiliarBook.saveFamiliarBook", familiarBook);
  }
  
  public void updateFamiliarBook(FamiliarBook familiarBook)
    throws Exception
  {
    this.dao.update("HUDH_DZBL_FamiliarBook.updateFamiliarBook", familiarBook);
  }
  
  public JSONObject findFamiliarBook(String id)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject("HUDH_DZBL_FamiliarBook.findFamiliarBookById", id);
  }
  
  public void deleteFamiliarBook(String id)
    throws Exception
  {
    this.dao.deleteSingleUUID("HUDH_DZBL_FamiliarBook.deleteFamiliarBook", id);
  }
  
  public JSONObject findFamiliarBookList(String lcljId)
    throws Exception
  {
    return null;
  }
  
  public JSONObject findLocatorFamiliar(Map<String, Object> map)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject("HUDH_LCLJ_LOCATORFAMILIARBOOK.findLocatorFamiliar", map);
  }
  
  public List<JSONObject> findLocatorFamiliares(String lcljId)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_LCLJ_LOCATORFAMILIARBOOK.findLocatorFamiliares", lcljId);
  }
  
  public Object saveLocatorFamiliar(LocatorFamiliar locatorFamiliar)
    throws Exception
  {
    return this.dao.save("HUDH_LCLJ_LOCATORFAMILIARBOOK.saveLocatorFamiliar", locatorFamiliar);
  }
  
  public Object updateLocatorFamiliar(LocatorFamiliar locatorFamiliar)
    throws Exception
  {
    return this.dao.update("HUDH_LCLJ_LOCATORFAMILIARBOOK.updateLocatorFamiliar", locatorFamiliar);
  }
}
