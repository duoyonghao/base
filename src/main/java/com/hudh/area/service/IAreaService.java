package com.hudh.area.service;

import java.util.List;
import net.sf.json.JSONObject;

public abstract interface IAreaService
{
  public abstract List<JSONObject> findAreaByCityCode(String paramString)
    throws Exception;
  
  public abstract JSONObject findAreaByAreaCode(String paramString)
    throws Exception;
}
