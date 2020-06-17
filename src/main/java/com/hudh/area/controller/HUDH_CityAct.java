/**  
  *
  * @Title:  HUDH_CityAct.java   
  * @Package com.hudh.area.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 下午7:29:42   
  * @version V1.0  
  */ 
package com.hudh.area.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.area.service.ICityService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  HUDH_CityAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 下午7:29:42   
  *      
  */
@Controller
@RequestMapping("HUDH_CityAct")
public class HUDH_CityAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_CityAct.class);
	
	@Autowired
	private ICityService cityService;
	
	@RequestMapping(value = "/findCityByProviceCode.act")
	public String findCityByProviceCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
 		String proviceCode = request.getParameter("proviceCode");
//		if (proviceCode.equals("")) {
//			proviceCode = "110100";
//		} 
		try {
			List<JSONObject> list = cityService.findCityByProviceCode(proviceCode);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

}
