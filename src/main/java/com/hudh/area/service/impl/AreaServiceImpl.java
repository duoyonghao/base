package com.hudh.area.service.impl;

import com.hudh.area.dao.AreaDao;
import com.hudh.area.service.IAreaService;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl implements IAreaService {
  @Autowired
  private AreaDao areaDao;
  
  public List<JSONObject> findAreaByCityCode(String cityCode) throws Exception {
    List<JSONObject> list = this.areaDao.findAreaByCityCode(cityCode);
    return list;
  }
  
  public JSONObject findAreaByAreaCode(String areaCode) throws Exception {
    JSONObject json = this.areaDao.findAreaByAreaCode(areaCode);
    return json;
  }
}
