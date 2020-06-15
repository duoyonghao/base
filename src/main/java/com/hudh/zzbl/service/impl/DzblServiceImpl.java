package com.hudh.zzbl.service.impl;

import com.hudh.zzbl.dao.DzblDao;
import com.hudh.zzbl.entity.AdviceNote;
import com.hudh.zzbl.entity.FamiliarBook;
import com.hudh.zzbl.entity.LocatorFamiliar;
import com.hudh.zzbl.service.DzblService;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DzblServiceImpl implements DzblService {
  @Autowired
  private DzblDao dzblDao;
  
  public void saveCaseHistory(AdviceNote adviceNote) throws Exception {
    this.dzblDao.saveCaseHistory(adviceNote);
  }
  
  public List<JSONObject> findCaseHistoryById(String id) throws Exception {
    return this.dzblDao.findCaseHistoryById(id);
  }
  
  public void updateCaseHistoryById(AdviceNote adviceNote) throws Exception {
    this.dzblDao.updateCaseHistoryById(adviceNote);
  }
  
  public void deleteCaseHistory(String id) throws Exception {
    this.dzblDao.deleteCaseHistoryById(id);
  }
  
  public void saveFamiliarBook(FamiliarBook familiarBook) throws Exception {
    this.dzblDao.saveFamiliarBook(familiarBook);
  }
  
  public void updateFamiliarBook(FamiliarBook familiarBook) throws Exception {
    this.dzblDao.updateFamiliarBook(familiarBook);
  }
  
  public JSONObject findFamiliarBook(String id) throws Exception {
    return this.dzblDao.findFamiliarBook(id);
  }
  
  public void deleteFamiliarBook(String id) throws Exception {
    this.dzblDao.deleteFamiliarBook(id);
  }
  
  public JSONObject findLocatorFamiliar(Map<String, Object> map) throws Exception {
    return this.dzblDao.findLocatorFamiliar(map);
  }
  
  public List<JSONObject> findLocatorFamiliares(String lcljId) throws Exception {
    return this.dzblDao.findLocatorFamiliares(lcljId);
  }
  
  public List<JSONObject> findFamiliarBookList() {
    return null;
  }
  
  public Integer saveLocatorFamiliar(LocatorFamiliar locatorFamiliar) throws Exception {
    return (Integer)this.dzblDao.saveLocatorFamiliar(locatorFamiliar);
  }
  
  public void updateLocatorFamiliar(LocatorFamiliar locatorFamiliar) throws Exception {
    this.dzblDao.updateLocatorFamiliar(locatorFamiliar);
  }
}
