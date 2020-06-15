package com.hudh.area.service;

import java.util.List;
import net.sf.json.JSONObject;

public abstract interface IProviceService
{
  public abstract List<JSONObject> findAll()
    throws Exception;
  
  public abstract JSONObject findProviceByProviceCode(String paramString)
    throws Exception;
}
