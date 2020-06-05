package com.hudh.ykzz.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.hudh.ykzz.entity.YkzzManu;
import net.sf.json.JSONObject;
public interface IYkzzManuService {
	/**
	 * 新增供应商
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	void insertYkzzManu(YkzzManu ykzzManu,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据id查找供应商
	 * @param id
	 * @return
	 * @throws Exception
	 */
	YkzzManu findYkzzManuById(String id) throws Exception;
	
	/**
	 * 根据id删除供应商
	 * @param id
	 * @throws Exception
	 */
	void deleteYkzzManuById(String id) throws Exception;
	
	/**
	 * 根据id更新供应商信息
	 * @param ykzzType
	 * @throws Exception
	 */
	void updateYkzzManuById(YkzzManu ykzzManu) throws Exception;
	
	/**
	 * 获取全部供应商
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllManu(String organization) throws Exception;
	
	/**
	 * 根据编号获取供应商
	 * @param id
	 * @return
	 * @throws Exception
	 */
	JSONObject findManuByCode(String manuCode) throws Exception;
}
