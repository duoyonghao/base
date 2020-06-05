package com.hudh.ykzz.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ykzz.entity.YkzzManu;
import com.hudh.ykzz.entity.YkzzType;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;
@Service
public class YkzzManuDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增供应商
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public int insertYkzzManu(YkzzManu ykzzManu) throws Exception{
		return (int) dao.save("HUDH_YKZZ_MANU.insertYkzzManu", ykzzManu);
	}
	
	/**
	 * 根据id查找供应商
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public YkzzManu findYkzzManuById(String id) throws Exception{
		YkzzManu ykzzManu = (YkzzManu) dao.findForObject("HUDH_YKZZ_MANU.findYkzzManuById", id);
		return ykzzManu;
	}
	
	/**
	 * 根据id删除供应商
	 * @param id
	 * @throws Exception
	 */
	public void deleteYkzzManuById(String id) throws Exception{
		dao.delete("HUDH_YKZZ_MANU.deleteManuTypeById", id);
	}
	
	/**
	 * 根据id更新供应商信息
	 * @param ykzzType
	 * @throws Exception
	 */
	public void updateYkzzManuById(YkzzManu ykzzManu) throws Exception{
		dao.update("HUDH_YKZZ_MANU.updateManuTypeById", ykzzManu);
	}
	
	/**
	 * 获取全部供应商
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllManu(String organization) throws Exception{
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_YKZZ_MANU.findAllManu", dataMap);
		return list;
	}
	
	/**
	 * 根据编号获取供应商
	 * @param manuCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject findManuByCode(String manuCode) throws Exception{
		JSONObject jo = (JSONObject) dao.findForList("HUDH_YKZZ_MANU.findManuByCode", null);
		return jo;
	}
}
