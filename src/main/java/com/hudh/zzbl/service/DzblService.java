package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.AdviceNote;
import com.hudh.zzbl.entity.FamiliarBook;
import com.hudh.zzbl.entity.LocatorFamiliar;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public interface DzblService {
  void saveCaseHistory(AdviceNote paramAdviceNote) throws Exception;
  
  List<JSONObject> findCaseHistoryById(String paramString) throws Exception;
  
  void updateCaseHistoryById(AdviceNote paramAdviceNote) throws Exception;
  
  void deleteCaseHistory(String paramString) throws Exception;
  
  void saveFamiliarBook(FamiliarBook paramFamiliarBook) throws Exception;
  
  void updateFamiliarBook(FamiliarBook paramFamiliarBook) throws Exception;
  
  JSONObject findFamiliarBook(String paramString) throws Exception;
  
  void deleteFamiliarBook(String paramString) throws Exception;
  
  List<JSONObject> findFamiliarBookList();
  
  JSONObject findLocatorFamiliar(Map<String, Object> paramMap) throws Exception;
  
  Integer saveLocatorFamiliar(LocatorFamiliar paramLocatorFamiliar) throws Exception;
  
  void updateLocatorFamiliar(LocatorFamiliar paramLocatorFamiliar) throws Exception;
  
  List<JSONObject> findLocatorFamiliares(String paramString) throws Exception;
}
