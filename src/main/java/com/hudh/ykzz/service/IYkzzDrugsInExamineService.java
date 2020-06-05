package com.hudh.ykzz.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.hudh.ykzz.entity.YkzzDrugsInExamine;
import net.sf.json.JSONObject;
public interface IYkzzDrugsInExamineService {
	/**
	 * 新增入库审批数据
	 * @param ykzzDrugsInExamine
	 * @return
	 * @throws Exception
	 */
	public int insertDrugsInExamine(YkzzDrugsInExamine ykzzDrugsInExamine,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据入库表id查询对应的审批数据
	 * @param drugsInId
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findDrugsInExamineByInId(String drugsInId) throws Exception;
	
	/**
	 * 根据id删除数据
	 * @param id
	 * @throws Exception
	 */
	public void deleteDrugsInExamineById(String id) throws Exception;
	
	/**
	 * 根据入库表id删除信息
	 * @param drugsInId
	 * @throws Exception
	 */
	public void deleteDrugsInExamineByInId(String drugsInId) throws Exception;
	
	/**
	 * 查询发要明细数据
	 * @param ykzzType
	 * @throws Exception
	 */
	public List<JSONObject> findDugsExamineDetail(Map<String,String> dataMap) throws Exception;
	/**
	 * 查询退药明细数据
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findDugsReturnDetail(Map<String, String> dataMap) throws Exception;
}
