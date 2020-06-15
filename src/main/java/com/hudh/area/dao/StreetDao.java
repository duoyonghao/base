package com.hudh.area.dao;

import com.kqds.dao.DaoSupport;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreetDao
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> findStreetByAreaCode(String areaCode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("bs_street.findStreetByAreaCode", areaCode);
    return list;
  }
  
  public JSONObject findStreetByTownCode(String townCode)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject("bs_street.findStreetByTownCode", townCode);
    return json;
  }
}
