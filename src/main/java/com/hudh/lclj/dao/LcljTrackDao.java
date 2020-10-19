package com.hudh.lclj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.OperatingRecord;
import com.hudh.lclj.entity.PreoperativeVerification;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;
@Service
public class LcljTrackDao {
	@Autowired
	private DaoSupport dao;
	
	@Autowired
	private KQDS_CostOrderLogic costOrderLogic;//引入开单依赖，解决路径成交判断是否展示
	
	/**
	 * 向临床路径表插入数据
	 * @return
	 * @throws Exception
	 */
	public int saveLcljOrderTrack(LcljOrderTrack lcljOrderTrack) throws Exception {
		int rsCount = (int) dao.save("HUDH_LCLJ_ORDERTRACK.insertLcljOrderTrack", lcljOrderTrack);
		return rsCount;
	}
	
	/**
	 * 根据编号查询对应编号下的手术信息
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLcljOrderTrackByOrderNumber(String orderNumber) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_ORDERTRACK.findLcljOrderTrsackByOrderNumber", orderNumber);
		return list;
	}
	
	/**
	 * 根据编号查询对应编号下的手术信息及患者信息
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLcljOrderTrackAndHzInfo(String orderNumber) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_ORDERTRACK.findLcljOrderTrackAndHzInfo", orderNumber);
		return list;
	}
	
	
	/**
	 * 根据id获取临床跟踪信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject findLcljOrderTrsackById(String id) throws Exception {
		JSONObject jsonOb = (JSONObject) dao.findForObject("HUDH_LCLJ_ORDERTRACK.findLcljOrderTrsackById", id);
		return jsonOb;
	}
	
	/**
	 * 根据id获取临床跟踪信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LcljOrderTrack findLcljOrderTrsackByseqId(String id) throws Exception {
		LcljOrderTrack lcljOrderTrack = (LcljOrderTrack) dao.findForObject("HUDH_LCLJ_ORDERTRACK.findLcljOrderTrsackByseqId", id);
		return lcljOrderTrack;
	}
	
	/**
	 * 更新操作项目的状态#################
	 * @throws Exception
	 */
	public void updateOperateStatus(String orderTrackId)  throws Exception{
		dao.update("HUDH_LCLJ_ORDERTRACK.updateOperateStatus", orderTrackId);
	}
	
	/**
	 * 更新流程状态
	 * @throws Exception
	 */
	public void updateOperationFlowStatus( @Param("flag") String flag, String orderTrackId) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("flag", flag);
		map.put("orderNumber", orderTrackId);
		dao.update("HUDH_LCLJ_ORDERTRACK.updateOperationFlowStatus", map);
	}
	
	/**
	 * 根据订单号查询牙齿数量
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findToohthNum(String orderNumber) throws Exception{
		List<JSONObject> jsono = (List<JSONObject>) dao.findForList("HUDH_LCLJ_ORDERTRACK.findToohthNum", orderNumber);
		return jsono;
	}
	
	/**
	 * 更新手术数据信息
	 * @param dataMap
	 * @throws Exception
	 */
	public void updateOrderTrack(Map<String,String> dataMap) throws Exception{
		dao.update("HUDH_LCLJ_ORDERTRACK.updateOrderTrack", dataMap);
	}
	
	/**
	 * 更新临床路径节点信息
	 * @throws Exception
	 */
	public void updateOrderTrackNodes(LcljOrderTrack lcljOrderTrack) throws Exception {
		dao.update("HUDH_LCLJ_ORDERTRACK.updateOrderTrackNodes", lcljOrderTrack);
	}
	
	/**
	 * 获取当前临床路径最大的编号
	 * @return
	 * @throws Exception
	 */
	public LcljOrderTrack findNextOrderNumber() throws Exception {
		LcljOrderTrack lcljOrderTrack = (LcljOrderTrack) dao.findForObject("HUDH_LCLJ_ORDERTRACK.findNextOrderNumber", null);
		return lcljOrderTrack;
	}
	
	/**
	 * 根据订单编号查询患者的信息
	 * @return
	 * @throws Exception 
	 */
	public JSONObject findOrderTrackInforByOrderNumber(Map<String, String> map, BootStrapPage bp, JSONObject json) throws Exception {
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("username")){
				map.put("sortName", "c.UserName");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "c.UserCode");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "c.PhoneNumber1");
			}
			if(map.get("sortName").equals("nodename")){
				map.put("sortName", "e.nodeName");
			}
			if(map.get("sortName").equals("type")){
				map.put("sortName", "a.type");
			}
			if(map.get("sortName").equals("flowname")){
				map.put("sortName", "f.flowname");
			}
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "a.createtime");
			}
			if(map.get("sortName").equals("order_number")){
				map.put("sortName", "e.order_number");
			}
			if(map.get("sortName").equals("counsellor")){
				map.put("sortName", "a.counsellor");
			}
			if(map.get("sortName").equals("plant_physician")){
				map.put("sortName", "a.plant_physician");
			}
			if(map.get("sortName").equals("repair_physician")){
				map.put("sortName", "a.repair_physician");
			}
			if(map.get("sortName").equals("flowstatus")){
				map.put("sortName", "e.flowStatus");
			}
			
		}
		
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_ORDERTRACK.findOrderTrackInforByOrderNumber", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
	
	/**
	 * 根据订单编号查询患者的信息（展示已成交的患者临床路径 syp 2019-12-21）
	 * @return
	 * @throws Exception 
	 */
	public JSONObject findOrderTrackInforByOrderNumberCjstatus(Map<String, String> map, BootStrapPage bp, JSONObject json) throws Exception {
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("username")){
				map.put("sortName", "c.UserName");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "c.UserCode");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "c.PhoneNumber1");
			}
			if(map.get("sortName").equals("nodename")){
				map.put("sortName", "e.nodeName");
			}
			if(map.get("sortName").equals("type")){
				map.put("sortName", "a.type");
			}
			if(map.get("sortName").equals("flowname")){
				map.put("sortName", "f.flowname");
			}
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "a.createtime");
			}
			if(map.get("sortName").equals("order_number")){
				map.put("sortName", "e.order_number");
			}
			if(map.get("sortName").equals("counsellor")){
				map.put("sortName", "a.counsellor");
			}
			if(map.get("sortName").equals("plant_physician")){
				map.put("sortName", "a.plant_physician");
			}
			if(map.get("sortName").equals("repair_physician")){
				map.put("sortName", "a.repair_physician");
			}
			if(map.get("sortName").equals("flowstatus")){
				map.put("sortName", "e.flowStatus");
			}
			
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_ORDERTRACK.findOrderTrackInforByOrderNumber", map);
		for (JSONObject jsonO : list) {
			List<JSONObject> costList = costOrderLogic.findCostOrderByRegsortUsercode(jsonO.getString("usercode"));
			if(costList != null && costList.size() > 0) {
				jsonO.put("cjstatus", 1);
				continue;
			} else {
				jsonO.put("cjstatus", 0);
			}
//			System.out.println(costList);
		}
//		list = list.stream().filter(o -> !o.getString("cjstatus").equals(1)).collect(Collectors.toList());
//		list = list.stream().filter(o -> !o.getString("cjstatus").equals("0")).collect(Collectors.toList());
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
	
	/**
	 * 获取患者下面的挂号信息
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findRegListByBlcode(Map<String,String> map)  throws Exception {
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>) dao.findForList("KQDS_REG.selectRegByusercode", map);
		return list;
	}
	
	/**
	 * 根据条件精确查询临床路径患者信息
	 * @param person
	 * @param map
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public JSONObject findOrderTrackInforByConditionQuery(YZPerson person, Map<String, String> map, String organization) throws Exception{
		if (map.containsKey("usercodeorname")) {
			map.put("p1", YZAuthenticator.phonenumberLike("c.PhoneNumber1", map.get("usercodeorname")));
			map.put("p2", YZAuthenticator.phonenumberLike("c.PhoneNumber2", map.get("usercodeorname")));
		}
		if (YZUtility.isNotNullOrEmpty(organization)) {
			map.put("organization", organization);
		}
		JSONObject json = (JSONObject) dao.findForObject("HUDH_LCLJ_ORDERTRACK.findOrderTrackInforByConditionQuery", map);
		return json;
	}
	
	/**
	 * 根据id更新手术数据信息
	 * @param dataMap
	 * @throws Exception
	 */
	public void updateOrderTrackById(Map<String,String> dataMap) throws Exception{
		dao.update("HUDH_LCLJ_ORDERTRACK.updateOrderTrackById", dataMap);
	}
	
	/**
	 * 根据id删除手术信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteOrderTrackInforById(String id) throws Exception {
		dao.delete("HUDH_LCLJ_ORDERTRACK.deleteOrderTrackInforById", id);
	}
	
	/**
	 * 根据id查询手术跟踪信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LcljOrderTrack findOrderTrackInforById(String id) throws Exception {
		LcljOrderTrack lcljOrderTrack = (LcljOrderTrack) dao.findForObject("HUDH_LCLJ_ORDERTRACK.findOrderTrackInforById", id);
		return lcljOrderTrack;
	}
	
	/**
	 * 改变临床路径流程植骨状态
	 * @param lcljOrderTrack
	 * @throws Exception
	 */
	public void changeLcljOrderTrackBoneStatus(LcljOrderTrack lcljOrderTrack) throws Exception {
		dao.save("HUDH_LCLJ_ORDERTRACK.changeLcljOrderTrackBoneStatus", lcljOrderTrack);
	}
	
	/**
	 * 修改临床路径记录是否作废的状态
	 * @param id
	 * @throws Exception
	 */
	public void updateLcljOrderTrackIsobsolete(String id) throws Exception {
		dao.update("HUDH_LCLJ_ORDERTRACK.updateLcljOrderTrackIsobsolete", id);
	}
	
	/**
	 * 根据id更新临床路径信息
	 * @throws Exception
	 */
	public void updateLcljOrderTrackById(LcljOrderTrack lcljOrderTrack) throws Exception {
		dao.update("HUDH_LCLJ_ORDERTRACK.updateLcljOrderTrackById", lcljOrderTrack);
	}

	/**   
	  * @Title: savePreoperativeVerification   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param pVerification      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年5月28日 上午10:09:59
	  */  
	public int savePreoperativeVerification(PreoperativeVerification pVerification) throws Exception {
		// TODO Auto-generated method stub
		return (int)dao.save("HUDH_LCLJ_preoperativeVerification.savePreoperativeVerification", pVerification);
	}

	/**   
	  * @Title: findPreoperativeVerification   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lcljId
	  * @param: @return      
	  * @return: JSONObject
	 * @throws Exception 
	  * @dateTime:2019年5月28日 上午11:04:02
	  */  
	public JSONObject findPreoperativeVerification(String lcljId) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject("HUDH_LCLJ_preoperativeVerification.findPreoperativeVerification", lcljId);
	}

	/**   
	  * @Title: editToothBit   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lTrack
	  * @param: @return      
	  * @return: int
	 * @throws Exception 
	  * @dateTime:2019年7月25日 下午3:16:23
	  */  
	public Integer editToothBit(LcljOrderTrack lTrack) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.update("HUDH_LCLJ_ORDERTRACK.editToothBit", lTrack);
	}

	/**   
	  * @Title: saveOperatingRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param oRecord      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年7月26日 下午7:20:59
	  */  
	public void saveOperatingRecord(OperatingRecord oRecord) throws Exception {
		// TODO Auto-generated method stub
		dao.save("HUDH_LCLJ_ORDERTRACK.saveOperatingRecord", oRecord);
	}
	
	public JSONObject findPatientInformation(String usercode) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject("HUDH_LCLJ_ORDERTRACK.findPatientInformation", usercode);
	}

	/**
	 * 修改表单状态字段为1
	 * @param map
	 * @throws Exception
	 */
	public void updateFormStatus(Map<String,String> map) throws Exception {
		//form 表示字段名称，status 表示字段保存的值 ， id 表示临床路径id
		dao.update("HUDH_LCLJ_ORDERTRACK.updateFormStatus",map);
	}

	/**
	 * 修改同意书字段填值
	 * @param map
	 * @throws Exception
	 */
	public void updateFormBookStatus(Map<String,String> map) throws Exception {
		//status 表示字段保存的值 ， id 表示临床路径id
		dao.update("HUDH_LCLJ_ORDERTRACK.updateFormBookStatus",map);
	}
}
