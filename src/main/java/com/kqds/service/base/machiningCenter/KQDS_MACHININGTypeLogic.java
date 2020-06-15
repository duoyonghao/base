package com.kqds.service.base.machiningCenter;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_MACHININGTypeLogic
  extends BaseLogic
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_MACHININGTypeLogic.class);
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> getListAll(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList("KQDS_MACHINING_TYPE.getListAll", map);
  }
  
  public List<JSONObject> getCategory(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList("KQDS_MACHINING_TYPE.getCategory", map);
  }
  
  public void delPrimaryBySeqId(String seqId)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("seqId", seqId);
    this.dao.delete("KQDS_MACHINING_TYPE.delByPrimaryBySeqId", dataMap);
  }
  
  public List<JSONObject> findnextType(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList("KQDS_MACHINING_TYPE.findnextType", map);
  }
}
