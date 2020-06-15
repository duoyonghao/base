package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzType;
import com.kqds.dao.DaoSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzTypeDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertYkzzType(YkzzType ykzzType)
    throws Exception
  {
    return ((Integer)this.dao.save("HUDH_YKZZ_TYPE.insertYkzzType", ykzzType)).intValue();
  }
  
  public YkzzType findYkzzTypeById(String id)
    throws Exception
  {
    YkzzType ykzzType = (YkzzType)this.dao.findForObject("HUDH_YKZZ_TYPE.findYkzzTypeById", id);
    return ykzzType;
  }
  
  public void deleteYkzzTypeById(String id)
    throws Exception
  {
    this.dao.delete("HUDH_YKZZ_TYPE.deleteYkzzTypeById", id);
  }
  
  public void updateYkzzTypeById(YkzzType ykzzType)
    throws Exception
  {
    this.dao.update("HUDH_YKZZ_TYPE.updateYkzzTypeById", ykzzType);
  }
  
  public List<JSONObject> findChildTypesByParentId(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_YKZZ_TYPE.findChildTypesByParentId", map);
    return list;
  }
  
  public List<JSONObject> findAllTypes(@Param("organization") String organization)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList("HUDH_YKZZ_TYPE.findAllTypes", dataMap);
    return list;
  }
}
