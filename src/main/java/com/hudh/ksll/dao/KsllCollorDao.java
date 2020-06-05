package com.hudh.ksll.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ksll.entity.KsllCollor;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;
@Service
public class KsllCollorDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增科室领料信息
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public int insertCollor(KsllCollor ksllCollor) throws Exception{
		return (int) dao.save("HUDH_KSLL_COLLOR.insertCollor", ksllCollor);
	}
	
	/**
	 * 获取全部科室领料数据或根据条件检索
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllCollor(Map<String,String> dataMap) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_KSLL_COLLOR.findAllCollor", dataMap);
	}
	
	/**
	 * 更新status字段
	 * @param id
	 * @throws Exception
	 */
	public void updateCollorStatus(Map<String,String> dataMap) throws Exception{
		dao.delete("HUDH_KSLL_COLLOR.updateCollorStatus", dataMap);
	}
	
	/**
	 * 获取全部仓库商品包括所在仓库
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllCKGoods() throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_KSLL_COLLOR.findAllCKGoods", null);
	}
	
	/**
	 * 2019.07.10 lwg
	 * 根据goodscode查询仓库sshouse
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String findCKGoodSshouse(Map<String,String> map) throws Exception{
		 String sshouse = (String) dao.findForObject("HUDH_KSLL_COLLOR.findCKGoodSshouse", map);
		 return sshouse;
	}

}
