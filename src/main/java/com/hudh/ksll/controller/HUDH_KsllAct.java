package com.hudh.ksll.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.hudh.ksll.entity.KsllCollor;
import com.hudh.ksll.entity.KsllReplaceMent;
import com.hudh.ksll.service.IKsllColorService;
import com.hudh.ksll.service.IKsllReplaceMentService;
import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 临床路径操作Controller
 * @author XKY
 *
 */
@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("/HUDH_KSllAct")
public class HUDH_KsllAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_KsllAct.class);
	/**
	 * 科室商品service
	 */
	@Autowired
	private IKsllColorService ksllColorService;
	
	/**
	 * 科室商品service
	 */
	@Autowired
	private IKsllReplaceMentService ksllReplaceMentService;
	/**
	 * 获取科室下全部商品信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAllKsllColorGoods.act")
	public String findAllKsllColorGoods(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		String deptpart = request.getParameter("deptpart");
		String house = request.getParameter("house");
		String goodscode = request.getParameter("goodscode");
		String goodsname = request.getParameter("goodsname");
		String queryInput = request.getParameter("queryInput");
		String initialize=request.getParameter("initialize");
		dataMap.put("organization", ChainUtil.getCurrentOrganization(request));
		if(YZUtility.isNotNullOrEmpty(deptpart)) {
			dataMap.put("deptpart", deptpart);
		}
		if(YZUtility.isNotNullOrEmpty(house)) {
			dataMap.put("house", house);
		}
		if(YZUtility.isNotNullOrEmpty(goodscode)) {
			dataMap.put("goodscode", goodscode);
		}
		if(YZUtility.isNotNullOrEmpty(goodsname)) {
			dataMap.put("goodsname", goodsname);
		}
		if(YZUtility.isNotNullOrEmpty(queryInput)) {
			dataMap.put("queryInput", queryInput);
		}
		try {
			JSONObject list= new JSONObject();
			if(initialize.equals("0")){
				list.put("rows", "");
				list.put("total", 0);
			}else{
				// 初始化分页实体类
				@SuppressWarnings("rawtypes")
				BootStrapPage bp = new BootStrapPage();
				// 封装参数到实体类
				BeanUtils.populate(bp, request.getParameterMap());
				list = ksllColorService.findAllKsllColorGoods(dataMap,bp);
			}
			//YZUtility.RETURN_LIST(list, response, logger);
			YZUtility.RETURN_OBJ(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 获取全部科室数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCkDept.act")
	public String findCkDept(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		String organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
		dataMap.put("organization", organization);
		try {
			List<JSONObject> list = ksllColorService.findAllCKDept(dataMap);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 获取科室树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCkDeptTreeData.act")
	public String findCkDeptTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		String organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
		dataMap.put("organization", organization);
		try {
			List<JSONObject> list = ksllColorService.findAllCKDept(dataMap);
			JSONArray jsonArray = new JSONArray();
			if(null != list && list.size() > 0) {
				JSONObject tempObj =  new JSONObject();
				for(JSONObject obj : list) {
					tempObj = new JSONObject();
					tempObj.put("id", obj.get("seq_id"));
					tempObj.put("pId", "0");
					tempObj.put("name", obj.get("deptname"));
					jsonArray.add(tempObj);
				}
			}
			YZUtility.RETURN_LIST(jsonArray, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 保存科室领料数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveKsllData.act")
	public String saveKsllData(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String paramDetail = request.getParameter("paramDetail");
		String createtime = request.getParameter("createtime");
		String deptpart = request.getParameter("deptpart");
		String creatorId = request.getParameter("creatorId");
		String inremark = request.getParameter("inremark");
		//String sshouse = request.getParameter("sshouse");
		String floor = request.getParameter("floor");
		
		KsllCollor ksllCollor = new KsllCollor();
		ksllCollor.setCreatetime(createtime);
		ksllCollor.setCreator(creatorId);
		ksllCollor.setDeptpart(deptpart);
		ksllCollor.setRemark(inremark);
		//ksllCollor.setSshouse(sshouse);
		ksllCollor.setFloor(floor);
		try {
			ksllColorService.saveKsllData(ksllCollor, paramDetail, request);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 获取全部科室领料信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAllKsllColor.act")
	public String findAllKsllColor(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		String organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
		dataMap.put("organization", organization);
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String creatorname = request.getParameter("hzname");
		String deptpart = request.getParameter("deparent");
		String status = request.getParameter("status");
		String floor = request.getParameter("floor");
		if(YZUtility.isNotNullOrEmpty(starttime)) {
			dataMap.put("starttime", starttime);
		}
		if(YZUtility.isNotNullOrEmpty(endtime)) {
			dataMap.put("endtime", endtime);
		}
		if(YZUtility.isNotNullOrEmpty(creatorname)) {
			dataMap.put("creatorname", creatorname);
		}
		if(YZUtility.isNotNullOrEmpty(deptpart)) {
			dataMap.put("deptpart", deptpart);
		}
		if(YZUtility.isNotNullOrEmpty(status)) {
			dataMap.put("status", status);
		}
		if(YZUtility.isNotNullOrEmpty(floor)) {
			dataMap.put("floor", floor);
		}
		try {
			List<JSONObject> list = ksllColorService.findAllKsllColor(dataMap);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 获取某条科室领料信息及对应的明细初始化出库页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findKsllColorAndDetails.act")
	public String findKsllColorAndDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String ksllCollorId = request.getParameter("ksllCollorId");
		try {
			JSONObject jo = ksllColorService.findKsllColorAndDetails(ksllCollorId);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	 * 获取某条科室领料信息及对应的明细初始化出库页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findKsllColorAndDetail.act")
	public String findKsllColorAndDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String ksllCollorId = request.getParameter("ksllCollorId");
		try {
			JSONObject jo = ksllColorService.findKsllColorAndDetail(ksllCollorId);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 获取全部仓库
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCkHouse.act")
	public String findCkHouse(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		String organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
//		if(!organization.equals("HUDH")){
//			organization="HUDH";
//		}
		dataMap.put("organization", organization);
		try {
			List<KqdsCkHouse> list = ksllColorService.findAllCKHouse(dataMap);
			JSONObject jo = new JSONObject();
			jo.put("houseList", JSON.toJSON(list));
			YZUtility.DEAL_SUCCESS(jo, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 商品出库
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ksllOutGoods.act")
	public synchronized String ksllOutGoods(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try {
			ksllColorService.ksllOutGoods(request, response);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据科室领料id获取明细数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findKsllColorDetailByparentid.act")
	public String findKsllColorDetailByparentid(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String parentId = request.getParameter("parentId");
		try {
			List<JSONObject> list =new ArrayList<JSONObject>();
			if(parentId != null && !parentId.equals("") && !parentId.equals("undefined")){
				list = ksllColorService.findKsllColorDetailByparentid(parentId);
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 更新明细表数量字段 仓库发货之前修改领用数量
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateNumsById.act")
	public String updateNumsById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		String id = request.getParameter("id");
		String nums = request.getParameter("newNums");
		try {
			if(YZUtility.isNotNullOrEmpty(id) && YZUtility.isNotNullOrEmpty(nums)) {
				dataMap.put("id", id);
				dataMap.put("nums", nums);
				ksllColorService.updateNumsById(dataMap);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 未出库申领单删除
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteKsllNotCK.act")
	public String deleteKsllNotCK(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			ksllColorService.deleteKsllNotCK(id);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 保存科室退还数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveReplaceMentData.act")
	public String saveReplaceMentData(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String paramDetail = request.getParameter("paramDetail");
		String createtime = request.getParameter("createtime");
		String deptpart = request.getParameter("deptpart");
		String creatorId = request.getParameter("creatorId");
		String inremark = request.getParameter("inremark");
		String sshouse = request.getParameter("sshouse");
		String type = request.getParameter("type");
		String floor = request.getParameter("floor");
		
		KsllReplaceMent ksllReplaceMent = new KsllReplaceMent();
		ksllReplaceMent.setCreatetime(createtime);
		ksllReplaceMent.setCreator(creatorId);
		ksllReplaceMent.setDeptpart(deptpart);
		ksllReplaceMent.setRemark(inremark);
		ksllReplaceMent.setSshouse(sshouse);
		ksllReplaceMent.setType(Integer.valueOf(type));
		ksllReplaceMent.setFloor(floor);
		try {
			ksllReplaceMentService.insertReplacement(ksllReplaceMent, paramDetail, request);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 获取科室领料退还的全部数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findReplaceMentListNoDelete.act")
	public String findReplaceMentListNoDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		String organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
		dataMap.put("organization", organization);
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String creatorname = request.getParameter("hzname");
		String deptpart = request.getParameter("deparent");
		String status = request.getParameter("status");
		String floor = request.getParameter("type");
		if(YZUtility.isNotNullOrEmpty(starttime)) {
			dataMap.put("starttime", starttime);
		}
		if(YZUtility.isNotNullOrEmpty(endtime)) {
			dataMap.put("endtime", endtime);
		}
		if(YZUtility.isNotNullOrEmpty(creatorname)) {
			dataMap.put("creatorname", creatorname);
		}
		if(YZUtility.isNotNullOrEmpty(deptpart)) {
			dataMap.put("deptpart", deptpart);
		}
		if(YZUtility.isNotNullOrEmpty(status)) {
			dataMap.put("status", status);
		}
		if(YZUtility.isNotNullOrEmpty(floor)) {
			dataMap.put("floor", floor);
		}
		try {
			List<JSONObject> list = ksllReplaceMentService.findAllReplacementNoDelete(dataMap);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 获取科室领料退还的全部数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findReplaceMentList.act")
	public String findReplaceMentList(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		String organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
		dataMap.put("organization", organization);
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String creatorname = request.getParameter("hzname");
		String deptpart = request.getParameter("deparent");
		String status = request.getParameter("status");
		String type = request.getParameter("type");
		String floor = request.getParameter("floor");
		if(YZUtility.isNotNullOrEmpty(starttime)) {
			dataMap.put("starttime", starttime);
		}
		if(YZUtility.isNotNullOrEmpty(endtime)) {
			dataMap.put("endtime", endtime);
		}
		if(YZUtility.isNotNullOrEmpty(creatorname)) {
			dataMap.put("creatorname", creatorname);
		}
		if(YZUtility.isNotNullOrEmpty(deptpart)) {
			dataMap.put("deptpart", deptpart);
		}
		if(YZUtility.isNotNullOrEmpty(status)) {
			dataMap.put("status", status);
		}
		if(YZUtility.isNotNullOrEmpty(type)) {
			dataMap.put("type", type);
		}
		if(YZUtility.isNotNullOrEmpty(floor)){
			dataMap.put("floor", floor);
		}
		try {
			List<JSONObject> list = ksllReplaceMentService.findAllReplacement(dataMap);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 获取科室退还明细数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findReplaceMentDetailByParentId.act")
	public String findReplaceMentDetailByParentId(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String parentId = request.getParameter("parentId");
		String status=request.getParameter("status");
		String type=request.getParameter("type");
		String organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("parentId", parentId);
			map.put("organization", organization);
			List<JSONObject> list =new ArrayList<JSONObject>();
			if(type!=null&&status.equals("1")&&type.equals("1")){
				list=ksllReplaceMentService.findCkReplaceMentDetailByParendId(parentId);
			}else{
				list= ksllReplaceMentService.findDetailByParendId(map);
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 库房退回审批 更新退还数据状态 如果状态为1表示同意退回库房，需要同步更新库存。目前是直接更改的库存数量和库存金额，未生成入库单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateReplacementStatus.act")
	public String updateReplacementStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String status = request.getParameter("status");
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");//获取id
		String type = request.getParameter("type");
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("status", status);
		dataMap.put("id", id);
		dataMap.put("parentId", parentId);
		dataMap.put("organization", organization);
		String params = request.getParameter("paramDetail");
		List<JSONObject> jList = new ArrayList<JSONObject>();
		if(params != null && !params.equals("")){
			params = java.net.URLDecoder.decode(params, "UTF-8");
			JSONArray jArray = JSONArray.fromObject(params);
			jList = (List<JSONObject>) JSONArray.toCollection(jArray, JSONObject.class);
		}
		
		try {
			ksllReplaceMentService.updateReplacementStatus(dataMap,jList,person,type);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			if(e.toString().contains("编号")){
				String[] ex = e.toString().split(":");
				YZUtility.DEAL_ERROR(ex[1], false, e, response, logger);
			}else{
				YZUtility.DEAL_ERROR(null, false, e, response, logger);
			}
		}
		return null;
	}
	@RequestMapping(value = "/selectAllGoodPhByGoodCode.act")
	public String selectAllGoodPhByGoodCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String goodscode = request.getParameter("goodscode");
		String deptpart = request.getParameter("deptpart");
		Map<String,String> map = new HashMap<String,String>();
		map.put("goodscode", goodscode);
		map.put("deptpart", deptpart);
		try {
			List<JSONObject> list = ksllColorService.selectAllGoodPhByGoodCode(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
