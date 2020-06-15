package com.kqds.dao;

import java.util.Map;

public interface DAO {
  Object save(String paramString, Object paramObject) throws Exception;
  
  Object update(String paramString, Object paramObject) throws Exception;
  
  Object delete(String paramString, Object paramObject) throws Exception;
  
  Object deleteAll(String paramString) throws Exception;
  
  Object findForObject(String paramString, Object paramObject) throws Exception;
  
  Object findForList(String paramString, Object paramObject) throws Exception;
  
  Object findForMap(String paramString1, Object paramObject, String paramString2, String paramString3) throws Exception;
  
  Object loadList(String paramString, Map<String, String> paramMap) throws Exception;
  
  Object loadObjSingleUUID(String paramString1, String paramString2) throws Exception;
  
  void saveSingleUUID(String paramString, Object paramObject) throws Exception;
  
  void updateSingleUUID(String paramString, Object paramObject) throws Exception;
  
  int deleteSingleUUID(String paramString1, String paramString2) throws Exception;
  
  int selectCount(String paramString, Map paramMap) throws Exception;
}
