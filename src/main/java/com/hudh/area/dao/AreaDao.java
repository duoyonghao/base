package com.hudh.area.dao;

import com.kqds.dao.DaoSupport;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaDao
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> findAreaByCityCode(String cityCode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("bs_area.findAreaByCityCode", cityCode);
    return list;
  }
  
  public JSONObject findAreaByAreaCode(String areaCode)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject("bs_area.findAreaByAreaCode", areaCode);
    return json;
  }
}
