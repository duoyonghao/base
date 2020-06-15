package com.hudh.area.service.impl;

import com.hudh.area.dao.CityDao;
import com.hudh.area.service.ICityService;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements ICityService {
  @Autowired
  private CityDao cityDao;
  
  public List<JSONObject> findCityByProviceCode(String proviceCode) throws Exception {
    List<JSONObject> list = this.cityDao.findCityByProviceCode(proviceCode);
    return list;
  }
  
  public JSONObject findCityByCityCode(String cityCode) throws Exception {
    JSONObject json = this.cityDao.findCityByCityCode(cityCode);
    return json;
  }
}
