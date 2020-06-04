package com.hudh.ksll.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hudh.ksll.entity.KsllCollor;
import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.sys.BootStrapPage;

import net.sf.json.JSONObject;
public interface IKsllColorService {
	
	/**
	 * 查找所有的仓库领料部门
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllCKDept(Map<String,String> dataMap) throws Exception;
	
	/**
	 * 保存科室的领料数据
	 * @param KsllCollor
	 * @param collorDetails
	 * @param request
	 * @return
	 * @throws Exception
	 */
	void saveKsllData(KsllCollor ksllCollor,String collorDetails,HttpServletRequest request) throws Exception;
	
	/**
	 * 查找所有的的领料信息
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllKsllColor(Map<String,String> dataMap) throws Exception;
	
	/**
	 * 获取某条科室领料信息及对应的明细初始化出库页面
	 * @return
	 * @throws Exception
	 */
	JSONObject findKsllColorAndDetails(String ksllCollorId) throws Exception;
	/**
	 * 获取某条科室领料信息及对应的明细初始化出库页面
	 * @return
	 * @throws Exception
	 */
	JSONObject findKsllColorAndDetail(String ksllCollorId) throws Exception;
	
	/**
	 * 获取全部仓库
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	List<KqdsCkHouse> findAllCKHouse(Map<String,String> dataMap) throws Exception;
	
	/**
	 * 商品出库
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	void ksllOutGoods(HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	/**
	 * 根据parentid获取明细数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findKsllColorDetailByparentid(String parentId) throws Exception;
	
	/**
	 * 更新明细表数量字段
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	void updateNumsById(Map<String,String> dataMap) throws Exception;
	
	/**
	 * 科室申领未出库的信息删除，直接软删除科室领料表即可
	 * @param id 科室领料主表id
	 */
	void deleteKsllNotCK(String id) throws Exception;
	
	/**
	 * 获取全部的仓库商品，返回map类型,key为商品编号
	 * @return
	 * @throws Exception 
	 */
	public Map<String,JSONObject> findAllCKGood() throws Exception;
	/**
	 * 2019.07.10 lwg
	 * 根据goodscode查询仓库sshouse
	 */

	String findCKGoodSshouse(String goodscode, String organization) throws Exception;
	/**
	 * 查询所有的科室下的物品
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllKsllColorGoods(Map<String,String> dataMap) throws Exception;
	/**
	 * 分页查询科室物品
	 * @param dataMap
	 * @param bp
	 * @return
	 * @throws Exception
	 */
	JSONObject findAllKsllColorGoods(Map<String, String> dataMap, BootStrapPage bp) throws Exception;
	/**
	 * 根据商品编号查询商品数据
	 * <p>Title: findAllCkGoodsByGoodscode</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月19日 
	 * @param goodscode
	 * @return
	 * @throws Exception
	 */
	Map<String,JSONObject> findAllCkGoodsByGoodscode(String goodscode,String organization) throws Exception;
	
	/**
	  * @Title: selectAllGoodPhByGoodCode   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2020年5月1日 上午10:32:15
	 */
	List<JSONObject> selectAllGoodPhByGoodCode(Map<String, String> dataMap)throws Exception;
	/**
	  * @Title: findAllKsllColorGoodsByGoodscode   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: Map<String,JSONObject>
	  * @dateTime:2020年5月1日 上午10:32:22
	 */
	Map<String,JSONObject> findAllKsllColorGoodsByGoodscode(Map<String, String> dataMap) throws Exception;
}
