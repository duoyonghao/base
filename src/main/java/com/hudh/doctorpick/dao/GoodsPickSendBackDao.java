package com.hudh.doctorpick.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.doctorpick.entity.GoodsPickSendBack;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

@Service
public class GoodsPickSendBackDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 入库
	 * @param goodsPickSendBack
	 * @return
	 * @throws Exception
	 */
	public int insertGoodsPickSendBack(GoodsPickSendBack goodsPickSendBack) throws Exception {
		int i = (int) dao.save("HUDH_GOODS_PICK_SEND_BACK.insertGoodsPickSendBack", goodsPickSendBack);
		return i;
	}
	
	/**
	 * 查询全部商品退还信息
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findGoodsPickSendBackAll(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_GOODS_PICK_SEND_BACK.findGoodsPickSendBackAll", map);
		return list;
	}
	
	/**
	 * 根据主键查询商品退还信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject findGoodsPickSendBackById(String id) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("HUDH_GOODS_PICK_SEND_BACK.findGoodsPickSendBackById", id);
		return json;
	}

}
