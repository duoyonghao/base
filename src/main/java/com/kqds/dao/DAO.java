package com.kqds.dao;

import java.util.Map;

/**
 * 修改时间：2015、12、11
 */
public interface DAO {

	/**
	 * 保存对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object save(String str, Object obj) throws Exception;

	/**
	 * 修改对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object update(String str, Object obj) throws Exception;

	/**
	 * 删除对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object delete(String str, Object obj) throws Exception;

	/**
	 * 清空表
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object deleteAll(String str) throws Exception;

	/**
	 * 查找对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String str, Object obj) throws Exception;

	/**
	 * 查找对象
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String str, Object obj) throws Exception;

	/**
	 * 查找对象封装成Map
	 * 
	 * @param s
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForMap(String sql, Object obj, String key, String value) throws Exception;

	/**
	 * 实现之前的 logic.loadList方法
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object loadList(String tableName, Map<String, String> filter) throws Exception;

	/**
	 * 实现之前的 logic.loadObjSingleUUID方法
	 */
	public Object loadObjSingleUUID(String tableName, String seqId) throws Exception;

	/**
	 * 实现之前的 logic.saveSingleUUID方法
	 * 
	 * @param dp
	 * @param tableName
	 * @param request
	 * @throws Exception
	 */
	public void saveSingleUUID(String tableName, Object dp) throws Exception;

	/**
	 * 实现之前的 logic.updateSingleUUID方法
	 * 
	 * @param dp
	 * @param tableName
	 * @param request
	 * @throws Exception
	 */
	public void updateSingleUUID(String tableName, Object dp) throws Exception;

	/**
	 * 实现之前的 logic.deleteSingleUUID方法
	 * 
	 * @param dp
	 * @param tableName
	 * @param request
	 * @throws Exception
	 */
	public int deleteSingleUUID(String tableName, String seqId) throws Exception;

	/**
	 * 目前只支持 等于 条件的查询
	 * 
	 * @param tableName
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int selectCount(String tableName, Map map) throws Exception;

}
