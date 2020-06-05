package com.hudh.dzbl.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.dzbl.entity.DzblDetail;
import com.hudh.dzbl.entity.DzblTemplate;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPara;

import net.sf.json.JSONObject;

public interface IDzblTemplateService {
	
	/**
	 *  向电子病历模板表插入数据
	 * @param dzblTemplate
	 * @return
	 */
	void insertDzblTemplate(DzblTemplate dzblTemplate) throws Exception ;
	
	/**
	 * 根据病程分类和病程查找对应的模板
	 * @param blfl
	 * @param bc
	 */
	JSONObject findTemplateByBlflAndBc(String blfl,String bc,String sstype, String organization) throws Exception ;
	
	/**
	 * 根据病例分类回去已存的病程s列表
	 * @param blfl
	 */
	List<JSONObject> findBcByBlfl(String blfl,String sstype) throws Exception ;
	
	/**
	 * 保存病例数据
	 * @param dzblDetail
	 * @throws Exception
	 */
	public void insertDzblDetail(DzblDetail dzblDetail,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据病历号查询患者病历列表
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findDzblByBlcode(String blCode)  throws Exception;
	
	/**
	 * 更新病历
	 * @param dzblTemplate
	 * @throws Exception
	 */
	void updateDzblById(DzblDetail dzblDetail,HttpServletRequest request) throws Exception;
	
	/**
	 * 电子病历打印
	 * @throws Exception
	 */
	String dzblPrint(HttpServletRequest request,String blId,String blCode ,String ssTime) throws Exception;
	
	/**
	 * 根据病历号查询患者
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	JSONObject findUserDocByBlCode(String blCode) throws Exception;
	
	/**
	 * 根id查询病历详情
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	JSONObject findDzblById(String id) throws Exception;
	
	/**
	 * 根id查询病历模板
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	JSONObject findDzblTemplateById(String id) throws Exception;
	
	/**
	 * 根据id更新模板表
	 * @param id
	 * @throws Exception
	 */
	void updateDzblTempleById(DzblTemplate dzblTemplate)  throws Exception;
	
	/**
	 * 获取能够修改电子病历的角色并判断当前操作人有无权限
	 * @return
	 * @throws Exception
	 */
	String selectParaValueByName(HttpServletRequest request)  throws Exception;

	/**   
	  * @Title: findDzbl   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: List<JSONObject>
	  * @dateTime:2020年1月13日 上午10:39:07
	  */  
	@SuppressWarnings("rawtypes")
	JSONObject findDzbl(Map<String, String> map, BootStrapPage bp) throws Exception;
}
