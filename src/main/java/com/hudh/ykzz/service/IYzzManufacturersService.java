package com.hudh.ykzz.service;

import java.util.List;

import com.hudh.ykzz.entity.YkzzManufacturers;

import net.sf.json.JSONObject;

public interface IYzzManufacturersService {

	int insertManufacturers(YkzzManufacturers ykzzManufacturers) throws Exception;

	void deleteManufacturers(String id) throws Exception;

	void updateManufacturers(YkzzManufacturers ykzzManufacturers) throws Exception;

	List<JSONObject> findAllManufacturers(String organization) throws Exception;

	JSONObject findManufacturersById(String id) throws Exception;

	JSONObject findManufacturersByCode(String manufacturersCode) throws Exception;
}
