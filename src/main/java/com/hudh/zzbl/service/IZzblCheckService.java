package com.hudh.zzbl.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hudh.zzbl.entity.ZzblCheck;
import net.sf.json.JSONObject;

public interface IZzblCheckService {
	
	/**
	 * 保存数据
	 * @param dp
	 * @throws Exception
	 */
	void insertZzblCheck(ZzblCheck dp, HttpServletRequest request) throws Exception;
	
	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findZzblOprationById(String id) throws Exception;
	
	/**
	 * 根据id查询信息，返回对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ZzblCheck selectZzblCheckById(String id) throws Exception;
	
	/**
	 * 根据id更新
	 * @param id
	 * @throws Exception
	 */
	void updateZzblOprationById(ZzblCheck dp, HttpServletRequest request) throws Exception;
	
	/**
	 * 根据id删除信息
	 * @param id
	 * @throws Exception
	 */
	void deleteZzblInforById(String id) throws Exception;
	
	/**
	 * 查询全部信息
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllZzblInfor() throws Exception;

}
