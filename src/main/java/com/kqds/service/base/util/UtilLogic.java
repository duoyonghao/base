package com.kqds.service.base.util;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("utilLogic")
public class UtilLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public int updateUseFlag(String tableName, String useFlag, String seqId)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("tableName", tableName);
    json.put("useFlag", useFlag);
    json.put("seqId", seqId);
    
    int flag = ((Integer)this.dao.update("UtilMapper.updateUseFlag", json)).intValue();
    return flag;
  }
  
  public int selectCount(String tableName, String fieldName, String fieldValule)
    throws Exception
  {
    Map<String, String> json = new HashMap();
    json.put("tableName", tableName);
    json.put("fieldName", fieldName);
    json.put("fieldValue", fieldValule);
    
    int flag = ((Integer)this.dao.update("UtilMapper.selectCount", json)).intValue();
    return flag;
  }
}
