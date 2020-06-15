package com.hudh.dept.service;

import java.util.List;
import java.util.Map;


import net.sf.json.JSONObject;

public interface ISysDeptPrivService {
	/**
	 * 根据按钮查询部门名称
	 * <p>Title: findDeptNameByButtonName</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月29日 
	 * @param buttonName
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findDeptNameByButtonName(Map<String,String> map) throws Exception;
	/**
	 * 新增部门关联表数据
	 * <p>Title: insertSysDeptPriv</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月29日 
	 * @param map
	 * @throws Exception
	 */
	void insertSysDeptPriv(Map<String,String> map)throws Exception;
	/**
	 * 删除表数据
	 * <p>Title: deleteSysDeptPriv</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月29日 
	 * @param deptprivid
	 * @throws Exception
	 */
	void deleteSysDeptPriv(String deptprivid)throws Exception;
	/**
	 * 根据id查询详情
	 * <p>Title: findDeptPrivByDeptPrivId</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param deptprivid
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findDeptPrivByDeptPrivId(String deptprivid) throws Exception;
	/**
	 * 根据id修改数据
	 * <p>Title: updateSysDeptPriv</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param map
	 * @throws Exception
	 */
	int updateSysDeptPriv(Map<String, String> map) throws Exception;
	/**
	 * 根据部门id查询人员数据
	 * <p>Title: findPersonByDeptId</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findPersonByDeptId(Map<String, String> map) throws Exception;
}
