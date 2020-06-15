package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzManufacturers;
import java.util.List;
import net.sf.json.JSONObject;

public interface IYzzManufacturersService {
  int insertManufacturers(YkzzManufacturers paramYkzzManufacturers) throws Exception;
  
  void deleteManufacturers(String paramString) throws Exception;
  
  void updateManufacturers(YkzzManufacturers paramYkzzManufacturers) throws Exception;
  
  List<JSONObject> findAllManufacturers(String paramString) throws Exception;
  
  JSONObject findManufacturersById(String paramString) throws Exception;
  
  JSONObject findManufacturersByCode(String paramString) throws Exception;
}
