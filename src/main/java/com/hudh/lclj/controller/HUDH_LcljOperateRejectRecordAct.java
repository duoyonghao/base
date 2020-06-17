/**  
  *
  * @Title:  HUDH_LcljOperateRejectRecordAct.java   
  * @Package com.hudh.lclj.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年6月27日 下午3:27:39   
  * @version V1.0  
  */ 
package com.hudh.lclj.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.lclj.service.ILcljOperationNodeRejectService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  HUDH_LcljOperateRejectRecordAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年6月27日 下午3:27:39   
  *      
  */
@Controller
@RequestMapping("/HUDH_LcljOperateRejectRecordAct")
public class HUDH_LcljOperateRejectRecordAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_LcljOperateRejectRecordAct.class);
	
	@Autowired
	private ILcljOperationNodeRejectService rejectService;
	
	/**
	 * 根据临床路径编号查询退回操作信息
	  * @Title: findOperationNodeRejectByOrderNumber   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年6月27日 下午3:33:13
	 */
	@RequestMapping(value = "/findOperationNodeRejectByOrderNumber.act")
	public String findOperationNodeRejectByOrderNumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNumber = request.getParameter("orderNumber");
		try {
			List<JSONObject> list = rejectService.findOperationNodeRejectByOrderNumber(orderNumber);
			YZUtility.RETURN_LIST(list, response, logger);;
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
