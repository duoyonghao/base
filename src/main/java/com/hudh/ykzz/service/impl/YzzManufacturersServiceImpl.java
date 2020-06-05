package com.hudh.ykzz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ykzz.dao.YkzzManufacturersDao;
import com.hudh.ykzz.entity.YkzzManufacturers;
import com.hudh.ykzz.service.IYzzManufacturersService;

import net.sf.json.JSONObject;

@Service
public class YzzManufacturersServiceImpl implements IYzzManufacturersService {
	
	@Autowired
	private YkzzManufacturersDao YkzzManufacturersDao;

	@Override
	public int insertManufacturers(YkzzManufacturers ykzzManufacturers) throws Exception {
		// TODO Auto-generated method stub
		int i = (int) YkzzManufacturersDao.insertManufacturers(ykzzManufacturers);
		return i;
	}

	@Override
	public void deleteManufacturers(String id) throws Exception {
		// TODO Auto-generated method stub
		YkzzManufacturersDao.deleteManufacturers(id);
	}

	@Override
	public void updateManufacturers(YkzzManufacturers ykzzManufacturers) throws Exception {
		// TODO Auto-generated method stub
		YkzzManufacturersDao.updateManufacturers(ykzzManufacturers);
	}

	@Override
	public List<JSONObject> findAllManufacturers(String organization) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = (List<JSONObject>) YkzzManufacturersDao.findAllManufacturers(organization);
		return list;
	}

	@Override
	public JSONObject findManufacturersById(String id) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = (JSONObject) YkzzManufacturersDao.findManufacturersById(id);
		return json;
	}

	@Override
	public JSONObject findManufacturersByCode(String manufacturersCode) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = (JSONObject) YkzzManufacturersDao.findManufacturersByCode(manufacturersCode);
		return json;
	}

}
