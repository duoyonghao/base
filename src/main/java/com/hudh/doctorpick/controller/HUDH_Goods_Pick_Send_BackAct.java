package com.hudh.doctorpick.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.doctorpick.entity.GoodsPickSendBack;
import com.hudh.doctorpick.service.IGoodsPickSendBackService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

/**
 * 商品退还controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/HUDH_Goods_Pick_Send_BackAct")
public class HUDH_Goods_Pick_Send_BackAct {
	
	private static Logger logger = LoggerFactory.getLogger(HUDH_Goods_Pick_Send_BackAct.class);
	
	@Autowired
	private IGoodsPickSendBackService backService;
	
	/**
	 * 添加商品退还科室信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/insertGoodsPickSendBack.act")
	public String insertGoodsPickSendBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String goodsname = request.getParameter("goodsname");
		String goodscode = request.getParameter("goodscode");
		String goodsunit = request.getParameter("goodsunit");
		String id = request.getParameter("id");
		String nums = request.getParameter("nums");
		String goodsnorms = request.getParameter("goodsnorms");
		String goodsuuid1 = request.getParameter("goodsuuid1");
		String goods_unitprice = request.getParameter("goods_unitprice");
		String sendBackAmount = request.getParameter("sendBackAmount");
		String storage_id = request.getParameter("storage_id");
		String supplier_id = request.getParameter("supplier_id");
		String keshi_id = request.getParameter("keshi_id");
		String remark = request.getParameter("remark");
		String batchnum=request.getParameter("batchnum");
		GoodsPickSendBack dp = new GoodsPickSendBack();
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		dp.setOrganization(organization);
		dp.setCreateuser(person.getSeqId());
		dp.setGoodsname(goodsname);
		dp.setGoodscode(goodscode);
		dp.setDeptpartname(keshi_id);
		dp.setHousename(storage_id);
		dp.setGoodsunit(goodsunit);
		dp.setNums(nums);
		dp.setGoodsnorms(goodsnorms);
		dp.setGoodsuuid(goodsuuid1);
		dp.setDetailId(id);
		dp.setBatchnum(batchnum);
//		BigDecimal bd = new BigDecimal(goods_unitprice);
//		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
//		BigDecimal bd1 = new BigDecimal(sendBackAmount);
//		bd1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);
//		dp.setAmount(bd1);
//		dp.setNuitPrice(bd);
		dp.setRemark(remark);
		dp.setSupplier(supplier_id);
		try {
			backService.insertGoodsPickSendBack(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/findGoodsPickSendBackAll.act")
	public String findGoodsPickSendBackAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String supplier = request.getParameter("supplier");
		String goodscode = request.getParameter("goodscode");
		String stock_starttime = request.getParameter("stock_starttime");
		String stock_endtime = request.getParameter("stock_endtime");
		
		// 可见人员过滤
		String visualstaff = SessionUtil.getVisualstaff(request);
		if (YZUtility.isNotNullOrEmpty(visualstaff)) {
			map.put("querytype", visualstaff);
		}
		
		if (!YZUtility.isNullorEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		if (!YZUtility.isNullorEmpty(supplier)) {
			map.put("supplier", supplier);
		}
		if (!YZUtility.isNullorEmpty(goodscode)) {
			map.put("goodscode", goodscode);
		}
		if (!YZUtility.isNullorEmpty(stock_starttime)) {
			map.put("stock_starttime", stock_starttime);
		}
		if (!YZUtility.isNullorEmpty(stock_endtime)) {
			map.put("stock_endtime", stock_endtime);
		}
		try {
			List<JSONObject> list = backService.findGoodsPickSendBackAll(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据主键查询商品退还信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "findGoodsPickSendBackById.act")
	public String findGoodsPickSendBackById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		try {
			JSONObject json = backService.findGoodsPickSendBackById(id);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

}
