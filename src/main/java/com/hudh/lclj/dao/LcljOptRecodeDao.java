package com.hudh.lclj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.lclj.entity.LcljOptRecode;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;
@Service
public class LcljOptRecodeDao {
	@Autowired
	private DaoSupport dao;
	/**
	 * 向备注表添加信息
	 * @param lcljOrder
	 * @return
	 * @throws Exception
	 */
	public int saveLcljOptRecode(LcljOptRecode lcljOptRecode) throws Exception {
		int rsCount = (int) dao.save("HUDH_LCLJ_OPT_RECODE.insertLcljOptRecode", lcljOptRecode);
		return rsCount;
	}
	/**
	 * 查询所有的备注信息
	 * @param blCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLcljOptRecodeList(Map<String,String> dataMap) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_OPT_RECODE.findLcljOptRecodeList", dataMap);
		return list;
	}
	
	/**
	 * 跟新备注信息表状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRemarkStus(Map<String,String> dataMap) throws Exception {
		dao.update("HUDH_LCLJ_OPT_RECODE.updateRemarkStus", dataMap);
	}
}
