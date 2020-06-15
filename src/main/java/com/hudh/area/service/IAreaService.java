package com.hudh.area.service;

import java.util.List;
import net.sf.json.JSONObject;

public interface IAreaService {
  List<JSONObject> findAreaByCityCode(String paramString) throws Exception;
  
  JSONObject findAreaByAreaCode(String paramString) throws Exception;
}
