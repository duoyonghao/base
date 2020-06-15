package com.hudh.area.service;

import java.util.List;
import net.sf.json.JSONObject;

public interface ICityService {
  List<JSONObject> findCityByProviceCode(String paramString) throws Exception;
  
  JSONObject findCityByCityCode(String paramString) throws Exception;
}
