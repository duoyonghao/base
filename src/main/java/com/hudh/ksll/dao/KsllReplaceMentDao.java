package com.hudh.ksll.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ksll.entity.KsllCollor;
import com.hudh.ksll.entity.KsllReplaceMent;
import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;

import net.sf.json.JSONObject;
@Service
public class KsllReplaceMentDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增科室领料退还信息
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public int insertReplacement(KsllReplaceMent ksllReplaceMent) throws Exception{
		return (int) dao.save("HUDH_KSLL_REPLACEMENT.insertReplacement", ksllReplaceMent);
	}
	
	/**
	 * 获取全部科室领料退还数据或根据条件检索
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllReplacement(Map<String,String> dataMap) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_KSLL_REPLACEMENT.findAllReplacement", dataMap);
	}
	
	/**
	 * 更新status字段
	 * @param id
	 * @throws Exception
	 */
	public void updateReplacementStatus(Map<String,String> dataMap) throws Exception{
		dao.delete("HUDH_KSLL_REPLACEMENT.updateReplacementStatus", dataMap);
	}
	
	/**
	 * 批量库存药品
	 * @param id
	 * @throws Exception
	 */
	public void batchUpdateCKGoodsByPrimaryId(List<KqdsCkGoods> kqdsCkGoods) throws Exception{
		dao.batchUpdate("HUDH_KSLL_REPLACEMENT.batchUpdateCKGoodsByPrimaryId", kqdsCkGoods);
	}
	
	/**
	 * 根据id获取退还详情
	 * @param id
	 * @throws Exception
	 */
	public KsllReplaceMent findReplacementById(String id,String organization) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("organization", organization);
		return (KsllReplaceMent) dao.findForObject("HUDH_KSLL_REPLACEMENT.findReplacementById", map);
	}
	
}
