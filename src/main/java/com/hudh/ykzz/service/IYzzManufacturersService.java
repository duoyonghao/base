package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzManufacturers;
import java.util.List;
import net.sf.json.JSONObject;

public abstract interface IYzzManufacturersService
{
  public abstract int insertManufacturers(YkzzManufacturers paramYkzzManufacturers)
    throws Exception;
  
  public abstract void deleteManufacturers(String paramString)
    throws Exception;
  
  public abstract void updateManufacturers(YkzzManufacturers paramYkzzManufacturers)
    throws Exception;
  
  public abstract List<JSONObject> findAllManufacturers(String paramString)
    throws Exception;
  
  public abstract JSONObject findManufacturersById(String paramString)
    throws Exception;
  
  public abstract JSONObject findManufacturersByCode(String paramString)
    throws Exception;
}
