package com.kqds.dao;

import java.util.Map;

public abstract interface DAO
{
  public abstract Object save(String paramString, Object paramObject)
    throws Exception;
  
  public abstract Object update(String paramString, Object paramObject)
    throws Exception;
  
  public abstract Object delete(String paramString, Object paramObject)
    throws Exception;
  
  public abstract Object deleteAll(String paramString)
    throws Exception;
  
  public abstract Object findForObject(String paramString, Object paramObject)
    throws Exception;
  
  public abstract Object findForList(String paramString, Object paramObject)
    throws Exception;
  
  public abstract Object findForMap(String paramString1, Object paramObject, String paramString2, String paramString3)
    throws Exception;
  
  public abstract Object loadList(String paramString, Map<String, String> paramMap)
    throws Exception;
  
  public abstract Object loadObjSingleUUID(String paramString1, String paramString2)
    throws Exception;
  
  public abstract void saveSingleUUID(String paramString, Object paramObject)
    throws Exception;
  
  public abstract void updateSingleUUID(String paramString, Object paramObject)
    throws Exception;
  
  public abstract int deleteSingleUUID(String paramString1, String paramString2)
    throws Exception;
  
  public abstract int selectCount(String paramString, Map paramMap)
    throws Exception;
}
