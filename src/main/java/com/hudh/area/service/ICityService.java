package com.hudh.area.service;

import java.util.List;
import net.sf.json.JSONObject;

public abstract interface ICityService
{
  public abstract List<JSONObject> findCityByProviceCode(String paramString)
    throws Exception;
  
  public abstract JSONObject findCityByCityCode(String paramString)
    throws Exception;
}
