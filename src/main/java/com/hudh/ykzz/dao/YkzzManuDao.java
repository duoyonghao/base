package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzManu;
import com.kqds.dao.DaoSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzManuDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertYkzzManu(YkzzManu ykzzManu)
    throws Exception
  {
    return ((Integer)this.dao.save("HUDH_YKZZ_MANU.insertYkzzManu", ykzzManu)).intValue();
  }
  
  public YkzzManu findYkzzManuById(String id)
    throws Exception
  {
    YkzzManu ykzzManu = (YkzzManu)this.dao.findForObject("HUDH_YKZZ_MANU.findYkzzManuById", id);
    return ykzzManu;
  }
  
  public void deleteYkzzManuById(String id)
    throws Exception
  {
    this.dao.delete("HUDH_YKZZ_MANU.deleteManuTypeById", id);
  }
  
  public void updateYkzzManuById(YkzzManu ykzzManu)
    throws Exception
  {
    this.dao.update("HUDH_YKZZ_MANU.updateManuTypeById", ykzzManu);
  }
  
  public List<JSONObject> findAllManu(String organization)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList("HUDH_YKZZ_MANU.findAllManu", dataMap);
    return list;
  }
  
  public JSONObject findManuByCode(String manuCode)
    throws Exception
  {
    JSONObject jo = (JSONObject)this.dao.findForList("HUDH_YKZZ_MANU.findManuByCode", null);
    return jo;
  }
}
