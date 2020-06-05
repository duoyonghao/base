package com.hudh.ksll.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.hudh.ksll.entity.KsllReplaceMent;
import com.kqds.entity.sys.YZPerson;

import net.sf.json.JSONObject;
public interface IKsllReplaceMentService {
	/**
	 * 新增科室领料退还信息
	 * @param ksllReplaceMent
	 * @param KsllReplaceMentDetail
	 * @param request
	 * @return
	 * @throws Exception
	 */
	void insertReplacement(KsllReplaceMent ksllReplaceMent,String KsllReplaceMentDetail,HttpServletRequest request) throws Exception;
	
	/**
	 * 获取全部科室领料退还数据或根据条件检索
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllReplacement(Map<String,String> dataMap) throws Exception;
	
	/**
	 * 获取全部科室领料退还数据或根据条件检索-出去删除的仓库不需要看到科室删除的数据
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllReplacementNoDelete(Map<String, String> dataMap) throws Exception;
	
	/**
	 * 更新status字段
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	void updateReplacementStatus(Map<String,String> dataMap,List<JSONObject> jList,YZPerson person,String type) throws Exception;
	
	/**
	 * 获取退还物品明细
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findDetailByParendId(Map<String, String> map) throws Exception;
	
	/**
	 * 更新仓库库存数量和总金额
	 * @param parentId
	 * @throws Exception
	 */
	void updaeCKStock (String parentId,String updaeCKStock,List<JSONObject> jList,YZPerson person,String type) throws Exception;
	
	/**
	 * 更新科室库存表数量
	 * @param parentId
	 * @throws Exception
	 */
	void updateKsllGoodNums (String parentId, String organization) throws Exception;
	
	public int selectReturnNumByGoodscode(String goodscode) throws Exception;

	List<JSONObject> findCkReplaceMentDetailByParendId(String parentId) throws Exception;
}
