package com.hudh.area.service;

import java.util.List;
import net.sf.json.JSONObject;

public abstract interface IStreetService
{
  public abstract List<JSONObject> findStreetByAreaCode(String paramString)
    throws Exception;
  
  public abstract JSONObject findStreetByTownCode(String paramString)
    throws Exception;
}
