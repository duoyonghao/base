package com.hudh.ykzz.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.hudh.ykzz.entity.YkzzDrugsOut;

import net.sf.json.JSONObject;
public interface IYkzzDrugsOutService {
	
	/**
	 * 添加出库
	 * @param ykzzDrugs 出库信息
	 * @param drugsOutdetails 出库明细
	 * @return
	 * @throws Exception
	 */
	void insertDrugsOut(YkzzDrugsOut ykzzDrugsOut,String drugsOutdetails,HttpServletRequest request) throws Exception;
	
	/**
	 * 查询全部出库信息或根据条件查找
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllDrugsOut(Map<String,String> dataMap)  throws Exception;
	
	/**
	 * 根据parentid查询出库明细
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findDetailByParendId(String parentid)  throws Exception;
	
	/**
	 * 删除出库信息
	 * @param id
	 * @throws Exception
	 */
	String deleteDrugsOut(String id)  throws Exception;
	
	/**
	 * 删除出库表
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	void deleteDrugsOutById(String id)  throws Exception;
	
	/**
	 * 删除出库明细表
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	void deleteDrugsOutDetailByParendId(String id)  throws Exception;
	
	/**
	 * 获取药品入库的批号
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	String getDrugsInBatchnum(String order_no, String outnum) throws Exception;
	
	/**
	 * 2019-08-23 lwg
	 * 查询批号药品的当天出库数量
	 */
	String findOutNumByOrderno(String orderno, String batchnum) throws Exception;
	/**
	 * 2019-08-23 lwg
	 * 查询所有药品的当天出库数量
	 */
	String findOutNumsByAll() throws Exception;
	/**
	 * 2019-08-23 lwg
	 * 查询批号药品的当天领药数量
	 */
	String findBatchnumSaveOutNumsByOrdernoAndBatchnum(String orderno, String batchnum) throws Exception;
	/**
	 * 2019-08-23 lwg
	 * 查询所有药品的当天领药数量
	 */
	String findOutNumsByBatchnumSave() throws Exception;

}
