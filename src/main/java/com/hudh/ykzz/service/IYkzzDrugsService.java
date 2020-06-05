package com.hudh.ykzz.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.ykzz.entity.YkzzDrugs;
import net.sf.json.JSONObject;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
public interface IYkzzDrugsService {
	
	/**
	 * 添加药品
	 * @param ykzzDrugs
	 * @return
	 * @throws Exception
	 */
	int insertDrugsinfor(YkzzDrugs ykzzDrugs, HttpServletRequest request) throws Exception;
	
	/**
	 * 根据国家编码查询药品
	 * @param contrycode
	 * @return
	 * @throws Exception
	 */
	List<YkzzDrugs> findDeugsByContryCode(String contrycode, String organization) throws Exception;
	
	/**
	 * 查询药品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	JSONObject selectDrugsByPrimaryId(String id) throws Exception;
	
	/**
	 * 查找全部药品
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> selectAllDrugsInfor(Map<String, String> map) throws Exception;
	
	/**
	 * 删除药品
	 * @param id
	 * @throws Exception
	 */
	void deleteDrugsByPrimaryId(String id) throws Exception;
	
	/**
	 * 更新药品
	 * @param id
	 * @throws Exception
	 */
	void updateDrugsByPrimaryId(YkzzDrugs ykzzDrugs) throws Exception;
	
	/**
	 * 精确查询药品信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> selectDrugsInforByConditionQuery(Map<String, String> map) throws Exception;
	
	/**
	 * 根据编号集合获取药品
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	List<YkzzDrugs> selectDrugsByOrderNoStr(List<String> list)  throws Exception;
	
	/**
	 * 根据id集合获取药品
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	List<YkzzDrugs> selectDrugsByIdStr(List<YkzzDrugsInDetail> list)  throws Exception;
	
	/**
	 * 根据id集合获取药品
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	List<YkzzDrugs> selectDrugsOutByIdStr(List<YkzzDrugsOutDetail> list)  throws Exception;
	
	/**
	 * 批量更新药品
	 * @param id
	 * @throws Exception
	 */
	void batchUpdateDrugsByPrimaryId(List<YkzzDrugs> ykzzDrugs) throws Exception;
	
	/**
	 * 根据模板批量导入药品信息
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	void saveBatchInsert(List<List<String>> list, HttpServletRequest request) throws Exception;
	
	/**
	 * 获取所有药品信息
	 * @return
	 * @throws Exception
	 */
	List<YkzzDrugs> getAllDrugsInfor(Map<String, String> map) throws Exception;
	
	/**
	 * 禁用药品
	 * @param id
	 * @throws Exception
	 */
	void forbiddenDrugs(String id) throws Exception;
	
	/**
	 * 恢复禁用
	 * @param id
	 * @throws Exception
	 */
	void recoverDrugs(String id) throws Exception;
}
