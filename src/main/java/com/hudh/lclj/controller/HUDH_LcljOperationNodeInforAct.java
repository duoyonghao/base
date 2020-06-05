package com.hudh.lclj.controller;

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

import com.hudh.lclj.entity.OperationNodeInfor;
import com.hudh.lclj.service.ILcljOperationNodeInforService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
/**
 * 手术流程信息操作类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/HUDH_LcljOperationNodeInforAct")
public class HUDH_LcljOperationNodeInforAct {
	
	private static Logger logger = LoggerFactory.getLogger(HUDH_LcljOperationNodeInforAct.class);
	
	@Autowired
	private ILcljOperationNodeInforService inforService;
	
	/**
	 * 保存手术流程对应节点信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/insertOperationNodeInfor.act")
	public String insertOperationNodeInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		String remark = request.getParameter("remark");
		String operation_time = request.getParameter("operation_time");
		String ismodule = request.getParameter("ismodule");
		String preoperative_verification = request.getParameter("preoperative_verification");
		String confirmOcclusalRelationship = request.getParameter("confirmOcclusalRelationship");
		String makeTransitionDenture = request.getParameter("makeTransitionDenture");
		String tryInTransitionDenture = request.getParameter("tryInTransitionDenture");
		String orderNumber = request.getParameter("orderNumber");
		OperationNodeInfor dp = new OperationNodeInfor();
		dp.setCreateuser(person.getUserId());
		dp.setOrganization(organization);
		dp.setRemark(remark);
		dp.setOperation_time(operation_time);
		dp.setIsmodule(ismodule);
		dp.setPreoperative_verification(preoperative_verification);
		dp.setConfirmOcclusalRelationship(confirmOcclusalRelationship);
		dp.setMakeTransitionDenture(makeTransitionDenture);
		dp.setTryInTransitionDenture(tryInTransitionDenture);
		dp.setNodeId("PerSurMouset");
		dp.setOrder_number(orderNumber);
		String dataId = null;//定义dataId用于判断具体节点业务数据是正常提交还是退回再提交
		try {
			inforService.insertOperationNodeInfor(dp, request, dataId);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id查询手术流程对应节点信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/selectOperationNodeInforById.act")
	public String selectOperationNodeInforById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		try {
			JSONObject json = inforService.selectOperationNodeInforById(id);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询所有的手术节点信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/selectOperationNodeInforAll.act")
	public String selectOperationNodeInforAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> list = inforService.selectOperationNodeInforAll();
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id更新手术流程信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/updateOperationNodeInforById.act")
	public String updateOperationNodeInforById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//String id = request.getParameter("id");
		OperationNodeInfor dp = new OperationNodeInfor();
		
		try {
			inforService.updateOperationNodeInforById(dp);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id删除手术流程信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/deleteOperationNodeInforById.act")
	public String deleteOperationNodeInforById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		try {
			inforService.deleteOperationNodeInforById(id);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据临床路径编号和节点id查询相应节点业务数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/selectOperationNodeInforByOrdernumberAndNodeId.act")
	public String selectOperationNodeInforByOrdernumberAndNodeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNumber = request.getParameter("orderNumber");
		String nodeId = request.getParameter("nodeId");
		Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNotNullOrEmpty(orderNumber)) {
			map.put("orderNumber", orderNumber);
		}
		if (YZUtility.isNotNullOrEmpty(nodeId)) {
			map.put("nodeId", nodeId);
		}
		try {
			JSONObject jsonObject = inforService.selectOperationNodeInforByOrdernumberAndNodeId(map);
			YZUtility.DEAL_SUCCESS(jsonObject, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/saveOperationNodeInforAberrance.act")
	public String saveOperationNodeInforAberrance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		String remark = request.getParameter("remark");
		String operation_time = request.getParameter("operation_time");
		String ismodule = request.getParameter("ismodule");
		String preoperative_verification = request.getParameter("preoperative_verification");
		String confirmOcclusalRelationship = request.getParameter("confirmOcclusalRelationship");
		String makeTransitionDenture = request.getParameter("makeTransitionDenture");
		String tryInTransitionDenture = request.getParameter("tryInTransitionDenture");
		String orderNumber = request.getParameter("orderNumber");
		OperationNodeInfor dp = new OperationNodeInfor();
		dp.setCreateuser(person.getUserId());
		dp.setOrganization(organization);
		dp.setRemark(remark);
		dp.setOperation_time(operation_time);
		dp.setIsmodule(ismodule);
		dp.setPreoperative_verification(preoperative_verification);
		dp.setConfirmOcclusalRelationship(confirmOcclusalRelationship);
		dp.setMakeTransitionDenture(makeTransitionDenture);
		dp.setTryInTransitionDenture(tryInTransitionDenture);
		dp.setOrder_number(orderNumber);
		return null;
	}
	
	/**
	 * 2019-7-26 syp 更改下次来院时间 
	  * @Title: updateOrderTimeHospital   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年7月26日 上午10:42:12
	 */
	@RequestMapping(value = "/updateOrderTimeHospital.act")
	public String updateOrderTimeHospital(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String order_number = request.getParameter("order_number");
		String nodeId = request.getParameter("nodeId");
		String orderTime = request.getParameter("orderTime");
		try {
			inforService.updateOrderTimeHospital(order_number, nodeId, orderTime);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 *  预约日历里面取消手术预约临床路径同步取消   2019-7-27  syp
	  * @Title: cancelTimeHospital   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年7月27日 上午11:14:25
	 */
	public String cancelTimeHospital(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String order_number = request.getParameter("order_number");
		String nodeId = request.getParameter("nodeId");
		try {
			inforService.cancelTimeHospital(order_number, nodeId);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
}
