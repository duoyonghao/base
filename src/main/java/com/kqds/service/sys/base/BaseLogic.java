package com.kqds.service.sys.base;

import com.kqds.dao.DaoSupport;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public Object loadObjSingleUUID(String tableName, String seqId)
    throws Exception
  {
    return this.dao.loadObjSingleUUID(tableName, seqId);
  }
  
  public Object deleteSingleUUID(String tableName, String seqId)
    throws Exception
  {
    int obj = this.dao.deleteSingleUUID(tableName, seqId);
    return Integer.valueOf(obj);
  }
  
  public void saveSingleUUID(String tableName, Object obj)
    throws Exception
  {
    this.dao.saveSingleUUID(tableName, obj);
  }
  
  public Object loadList(String tableName, Map<String, String> filter)
    throws Exception
  {
    return this.dao.loadList(tableName, filter);
  }
  
  public int selectCount(String tableName, Map map)
    throws Exception
  {
    int count = this.dao.selectCount(tableName, map);
    return count;
  }
  
  public void updateSingleUUID(String tableName, Object dp)
    throws Exception
  {
    this.dao.updateSingleUUID(tableName, dp);
  }
  
  public Object loadList4One(String tableName, Map<String, String> filter)
    throws Exception
  {
    return this.dao.loadList4One(tableName, filter);
  }
}
