package com.hudh.zzbl.service;

import javax.servlet.http.HttpServletRequest;

import com.hudh.zzbl.entity.ZzblPlantTeethOperation;

import net.sf.json.JSONObject;

public interface IZzblPlantTeethOperationService {
	
	/**
	 * 保存数据
	 * @param dp
	 * @throws Exception
	 */
	void insertZzblPlantTeethOperation(ZzblPlantTeethOperation dp, HttpServletRequest request) throws Exception;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	JSONObject findZzblPlantTeethOperationById(String id) throws Exception;

}
