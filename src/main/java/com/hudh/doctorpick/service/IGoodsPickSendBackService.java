package com.hudh.doctorpick.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.doctorpick.entity.GoodsPickSendBack;

import net.sf.json.JSONObject;

public interface IGoodsPickSendBackService {
	
	int insertGoodsPickSendBack(GoodsPickSendBack goodsPickSendBack, HttpServletRequest request) throws Exception;
	
	List<JSONObject> findGoodsPickSendBackAll(Map<String, String> map) throws Exception;
	
	JSONObject findGoodsPickSendBackById(String id) throws Exception;
	
}
