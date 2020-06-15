package com.hudh.area.service;

import java.util.List;
import net.sf.json.JSONObject;

public interface IStreetService {
  List<JSONObject> findStreetByAreaCode(String paramString) throws Exception;
  
  JSONObject findStreetByTownCode(String paramString) throws Exception;
}
