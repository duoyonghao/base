package com.hudh.area.dao;

import com.kqds.dao.DaoSupport;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityDao
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> findCityByProviceCode(String proviceCode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("bs_city.findCityByProviceCode", proviceCode);
    return list;
  }
  
  public JSONObject findCityByCityCode(String cityCode)
    throws Exception
  {
    JSONObject jsonObject = (JSONObject)this.dao.findForObject("bs_city.findCityByCityCode", cityCode);
    return jsonObject;
  }
}
