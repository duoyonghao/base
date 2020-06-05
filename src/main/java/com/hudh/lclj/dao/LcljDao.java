package com.hudh.lclj.dao;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.lclj.entity.LcljOrder;
import com.kqds.dao.DaoSupport;
@Service
public class LcljDao {
	@Autowired
	private DaoSupport dao;
	/**
	 * 向临床路径表插入数据
	 * @param lcljOrder
	 * @return
	 * @throws Exception
	 */
	public int saveLcljOrder(LcljOrder lcljOrder) throws Exception {
		int rsCount = (int) dao.save("HUDH_LCLJ_ORDER.insertLcljOrder", lcljOrder);
		return rsCount;
	}
	/**
	 * 根据病例号查询对应的临床路径信息
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	public JSONObject findLcljOrderByBlcode(String blCode) throws Exception {
		JSONObject lcljOrder = (JSONObject) dao.findForObject("HUDH_LCLJ_ORDER.findLcljOrderByBlcode", blCode);
		return lcljOrder;
	}
	/**
	 * 根据病例号和状态查询对应的临床路径信息
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLcljOrderByBlcodeAndStu(Map<String,String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_ORDER.findLcljOrderByBlcodeAndStu", map);
		return list;
	}
	/**
	 * 根据编号查询对应的临床路径信息
	 * @param ord erNumber
	 * @return
	 * @throws Exception
	 */
	public LcljOrder findLcljOrderByOrderNumber(String orderNumber) throws Exception {
		LcljOrder lcljOrder = (LcljOrder) dao.findForObject("HUDH_LCLJ_ORDER.findLcljOrderByOrderNumber", orderNumber);
		return lcljOrder;
	}
	/**
	 * 获取当前临床路径最大的编号
	 * @return
	 * @throws Exception
	 */
	public LcljOrder findNextOrderNumber() throws Exception {
		LcljOrder lcljOrder = (LcljOrder) dao.findForObject("HUDH_LCLJ_ORDER.findNextOrderNumber", null);
		return lcljOrder;
	}
	
	/**
	 * 根据条件查询临床路径表的条目数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int findHasOrder(Map<String,String> map)  throws Exception {
		int rsCount = (int) dao.findForObject("HUDH_LCLJ_ORDER.findHasOrder",map);
		return rsCount;
	}
	
	/**
	 * 根据条件查询临床路径表的条目数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void updateRemaintooth(Map map)  throws Exception {
		dao.update("HUDH_LCLJ_ORDER.updateRemaintooth",map);
	}
	
	/**
	 * 更新一次完整临床路径的状态
	 * @param id
	 * @throws Exception
	 */
	public void updateOrderStatus(String orderNumber) throws Exception {
		dao.update("HUDH_LCLJ_ORDER.updateOrderStatus", orderNumber);
	}
}
