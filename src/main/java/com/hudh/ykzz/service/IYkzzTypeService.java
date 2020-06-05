package com.hudh.ykzz.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.ykzz.entity.YkzzType;

import net.sf.json.JSONObject;
public interface IYkzzTypeService {
	/**
	 * 新增分类
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	void insertYkzzType(YkzzType ykzzType,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据id查找分类
	 * @param id
	 * @return
	 * @throws Exception
	 */
	YkzzType findYkzzTypeById(String id) throws Exception;
	
	/**
	 * 根据id删除分类
	 * @param id
	 * @throws Exception
	 */
	void deleteYkzzTypeById(String id) throws Exception;
	
	/**
	 * 根据id更新分类信息
	 * @param ykzzType
	 * @throws Exception
	 */
	void updateYkzzTypeById(YkzzType ykzzType) throws Exception;
	
	/**
	 * 根据id查找子级分类
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findChildTypesByParentId(Map<String, String> map) throws Exception;
	
	/**
	 * 获取全部分类
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllTypes(String organization) throws Exception;
}
