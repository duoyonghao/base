package com.hudh.ykzz.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ykzz.entity.YkzzManufacturers;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

@Service
public class YkzzManufacturersDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 增加生产商
	 * @param ykzzManufacturers
	 * @return
	 * @throws Exception
	 */
	public int insertManufacturers(YkzzManufacturers ykzzManufacturers) throws Exception{
		int i = (int) dao.save("HUDH_YKZZ_MANUFACTURERS.insert", ykzzManufacturers);
		return i;
	}
	
	/**
	 * 删除生产商
	 * @param ykzzManufacturers
	 * @throws Exception
	 */
	public void deleteManufacturers(String id) throws Exception {
		dao.delete("HUDH_YKZZ_MANUFACTURERS.deleteByPrimaryKey", id);
	}
	
	/**
	 * 更新生产商
	 * @param ykzzManufacturers
	 * @throws Exception
	 */
	public void updateManufacturers(YkzzManufacturers ykzzManufacturers) throws Exception {
		dao.update("HUDH_YKZZ_MANUFACTURERS.updateByPrimaryKeySelective", ykzzManufacturers);
	}
	
	/**
	 * 查询全部生产商
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllManufacturers(String organization) throws Exception{
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_YKZZ_MANUFACTURERS.findAllManufacturers", dataMap);
		return list;
	}
	
	/**
	 * 根据id查询生产商
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject findManufacturersById(String id) throws Exception{
		JSONObject json = (JSONObject) dao.findForObject("HUDH_YKZZ_MANUFACTURERS.selectByPrimaryKey", id);
		return json;
	}
	
	/**
	 * 根据编号获取生产商
	 * @param manufacturersCode
	 * @return
	 * @throws Exception
	 */
	public JSONObject findManufacturersByCode(String manufacturersCode) throws Exception{
		JSONObject json = (JSONObject) dao.findForObject("HUDH_YKZZ_MANUFACTURERS.findManufacturersByCode", manufacturersCode);
		return json;
	}
}
