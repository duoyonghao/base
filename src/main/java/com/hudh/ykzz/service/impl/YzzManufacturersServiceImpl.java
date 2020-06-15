package com.hudh.ykzz.service.impl;

import com.hudh.ykzz.dao.YkzzManufacturersDao;
import com.hudh.ykzz.entity.YkzzManufacturers;
import com.hudh.ykzz.service.IYzzManufacturersService;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YzzManufacturersServiceImpl
  implements IYzzManufacturersService
{
  @Autowired
  private YkzzManufacturersDao YkzzManufacturersDao;
  
  public int insertManufacturers(YkzzManufacturers ykzzManufacturers)
    throws Exception
  {
    int i = this.YkzzManufacturersDao.insertManufacturers(ykzzManufacturers);
    return i;
  }
  
  public void deleteManufacturers(String id)
    throws Exception
  {
    this.YkzzManufacturersDao.deleteManufacturers(id);
  }
  
  public void updateManufacturers(YkzzManufacturers ykzzManufacturers)
    throws Exception
  {
    this.YkzzManufacturersDao.updateManufacturers(ykzzManufacturers);
  }
  
  public List<JSONObject> findAllManufacturers(String organization)
    throws Exception
  {
    List<JSONObject> list = this.YkzzManufacturersDao.findAllManufacturers(organization);
    return list;
  }
  
  public JSONObject findManufacturersById(String id)
    throws Exception
  {
    JSONObject json = this.YkzzManufacturersDao.findManufacturersById(id);
    return json;
  }
  
  public JSONObject findManufacturersByCode(String manufacturersCode)
    throws Exception
  {
    JSONObject json = this.YkzzManufacturersDao.findManufacturersByCode(manufacturersCode);
    return json;
  }
}
