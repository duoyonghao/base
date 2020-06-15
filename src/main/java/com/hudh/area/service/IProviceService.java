package com.hudh.area.service;

import java.util.List;
import net.sf.json.JSONObject;

public interface IProviceService {
  List<JSONObject> findAll() throws Exception;
  
  JSONObject findProviceByProviceCode(String paramString) throws Exception;
}
