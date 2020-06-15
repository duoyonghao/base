package com.hudh.zzbl.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hudh.zzbl.entity.RepairSchemeConfirm;

import net.sf.json.JSONObject;

public interface IRepairSchemeConfirmService {
	
	/**
	 * 保存修复信息
	 * @param dp
	 * @param request
	 * @throws Exception
	 */
	void saveRepairSchemeConfirmInfro(RepairSchemeConfirm dp, HttpServletRequest request) throws Exception;
	
	/**
	 * 根据id删除信息
	 * @param id
	 * @throws Exception
	 */
	void deleteRepairInforById(String id) throws Exception;
	
	/**
	 * 根据id更新修复信息
	 * @param id
	 * @throws Exception
	 */
	void updateRepairInforById(RepairSchemeConfirm dp, HttpServletRequest request) throws Exception;
	
	/**
	 * 根据id查询修复信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findRepairInforById(String id) throws Exception;
	
	/**
	 * 根据病历id查询详情
	  * @Title: selectRepairInforById   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param id
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2020年4月9日 上午10:32:17
	 */
	JSONObject selectRepairInforById(String id) throws Exception;
	
	/**
	 * 根据id查询修复信息，返回对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	RepairSchemeConfirm selectRepairSchemeConfirmById(String id) throws Exception;
	
	/**
	 * 查询所有的信息
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findReapirInforAll() throws Exception;

}
