/**  
  *
  * @Title:  HUDH_LcljOperationNodeRemarkAct.java   
  * @Package com.hudh.lclj.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月31日 上午11:09:14   
  * @version V1.0  
  */ 
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

import com.hudh.lclj.entity.LcljOperationNodeRemark;
import com.hudh.lclj.service.ILcljOperationNodeRemarkService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  HUDH_LcljOperationNodeRemarkAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月31日 上午11:09:14   
  *      
  */
@Controller
@RequestMapping("/HUDH_LcljOperationNodeRemarkAct")
public class HUDH_LcljOperationNodeRemarkAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_LcljOperationNodeRemarkAct.class);
	
	@Autowired
	private ILcljOperationNodeRemarkService remarkService;
	
	/**
	 * 保存备注信息
	  * @Title: saveNodeRemark   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年8月31日 下午2:46:41
	 */
	@RequestMapping(value = "/saveNodeRemark.act")
	public String saveNodeRemark(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LcljOperationNodeRemark dp = new LcljOperationNodeRemark();
		try {
			remarkService.saveNodeRemark(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询节点备注信息
	  * @Title: findNodeRemarkByNodeId   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年8月31日 下午2:46:45
	 */
	@RequestMapping(value = "/findNodeRemarkByNodeId.act")
	public String findNodeRemarkByNodeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String order_number = request.getParameter("order_number");
		String nodeId = request.getParameter("nodeId");
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("order_number", order_number);
		dataMap.put("nodeId", nodeId);
		try {
			List<JSONObject> list = remarkService.findNodeRemarkByNodeId(dataMap);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

}
