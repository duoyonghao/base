package com.hudh.zzbl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.entity.ZzblPlantTeethOperation;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

@Service
public class ZzblPlantTeethOperationDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存
	 * @param dp
	 * @throws Exception
	 */
	public void insertZzblPlantTeethOperation(ZzblPlantTeethOperation dp) throws Exception {
		dao.save("HUDH_ZZBL_PLANT_TEETH_OPERATION.insertZzblPlantTeethOperation", dp);
	}
	
	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject findZzblPlantTeethOperationById(String id) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("HUDH_ZZBL_PLANT_TEETH_OPERATION.findZzblPlantTeethOperationById", id);
		return json;
	}
}
