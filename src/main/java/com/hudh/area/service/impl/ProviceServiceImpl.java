package com.hudh.area.service.impl;

import com.hudh.area.dao.ProvinceDao;
import com.hudh.area.service.IProviceService;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviceServiceImpl implements IProviceService {
  @Autowired
  private ProvinceDao provinceDao;
  
  public List<JSONObject> findAll() throws Exception {
    List<JSONObject> list = this.provinceDao.findAll();
    return list;
  }
  
  public JSONObject findProviceByProviceCode(String proviceCode) throws Exception {
    JSONObject json = this.provinceDao.findProviceByProviceCode(proviceCode);
    return json;
  }
}
