package com.hudh.area.service.impl;

import com.hudh.area.dao.StreetDao;
import com.hudh.area.service.IStreetService;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreetServiceImpl
  implements IStreetService
{
  @Autowired
  private StreetDao streetDao;
  
  public List<JSONObject> findStreetByAreaCode(String areaCode)
    throws Exception
  {
    List<JSONObject> list = this.streetDao.findStreetByAreaCode(areaCode);
    return list;
  }
  
  public JSONObject findStreetByTownCode(String townCode)
    throws Exception
  {
    JSONObject json = this.streetDao.findStreetByTownCode(townCode);
    return json;
  }
}
