package com.hudh.ksll.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ksll.entity.KsllCollor;
import com.hudh.ksll.entity.KsllCollorDetail;
import com.hudh.ksll.entity.KsllGoods;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkHouse;

import net.sf.json.JSONObject;
@Service
public class KsllCollorGoodsDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增科室物品数据
	 * @param KsllGoods
	 * @return
	 * @throws Exception
	 */
	public int insertKsllGoods(KsllGoods KsllGoods) throws Exception{
		return (int) dao.save("HUDH_KSLL_GOODS.insertKsllGoods", KsllGoods);
	}
	
	/**
	 * 获取科室全部的物品
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllKsllGoods(Map<String,String> dataMap) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_KSLL_GOODS.findAllKsllGoods", dataMap);
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllKsllColorGoodsByGoodscode(Map<String,String> dataMap) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_KSLL_GOODS.findAllKsllColorGoodsByGoodscode", dataMap);
	}
	/**
	 * 获取全部的仓库领用部门
	 * @param dataMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllCKDept(Map<String,String> dataMap) throws Exception{
		return (List<JSONObject>) dao.findForList("KQDS_CK_DEPT.selectList", dataMap);
	}
	
	/**
	 * 获取全部的仓库
	 * @param dataMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsCkHouse> findAllCKHouse(Map<String,Map<String,String>> dataMap) throws Exception{
		return (List<KqdsCkHouse>) dao.findForList("KQDS_CK_HOUSE.selectBeanListByMap", dataMap);
	}
	
	/**
	 * 批量新增科室库存商品
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public void batchSaveKsllGoodsDetail(List<KsllGoods> list) throws Exception{
		dao.batchUpdate("HUDH_KSLL_GOODS.batchSaveKsllGoodsDetail", list);
	}
	
	/**
	 * 批量更新科室库存商品
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateKsllGoodsByPrimaryId(List<KsllGoods> list) throws Exception{
		dao.batchUpdate("HUDH_KSLL_GOODS.batchUpdateKsllGoodsByPrimaryId", list);
	}
	
	/**
	 * 根据id查找商品信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject findKsllGoodsByPrimaryId(String id) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("HUDH_KSLL_GOODS.findKsllGoodsByPrimaryId", id);
		return json;
	}
	
	/**
	 * 根据id更新商品科室库存数量
	 * @param id
	 * @throws Exception
	 */
	public void updateKsllGoodsByPrimaryId(KsllGoods ksllGoods) throws Exception {
		dao.update("HUDH_KSLL_GOODS.updateKsllGoodsByPrimaryId", ksllGoods);
	}
	
	/**
	 * 根据商品编号查询商品信息2019-4-15
	 * @param goodsCode
	 * @return
	 * @throws Exception
	 */
	public KsllGoods findKsllGoodsByGoodsCode(String goodsCode) throws Exception {
		KsllGoods ksllGoods = (KsllGoods) dao.findForObject("HUDH_KSLL_GOODS.findKsllGoodsByGoodsCode", goodsCode);
		return ksllGoods;
	}
	/**
	 * 查询单商品科室库存
	 * <p>Title: findKsllGoodsByDeptpartAndGoodscode</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月26日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public KsllGoods findKsllGoodsByDeptpartAndGoodscode(Map<String,String> map) throws Exception {
		KsllGoods ksllGoods = (KsllGoods) dao.findForObject("HUDH_KSLL_GOODS.findKsllGoodsByDeptpartAndGoodscode", map);
		return ksllGoods;
	}
}
