/**  
  *
  * @Title:  HUDH_AreaAct.java   
  * @Package com.hudh.area.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月15日 上午11:04:11   
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

import com.hudh.area.service.IAreaService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  HUDH_AreaAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月15日 上午11:04:11   
  *      
  */
@Controller
@RequestMapping("HUDH_AreaAct")
public class HUDH_AreaAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_AreaAct.class);
	
	@Autowired
	private IAreaService areaService;
	
	@RequestMapping(value = "/findAreaByCityCode.act")
	public String findAreaByCityCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cityCode = request.getParameter("cityCode");
//		if (cityCode.equals("")) {
//			cityCode = "110100";
//		}
		try {
			List<JSONObject> list = areaService.findAreaByCityCode(cityCode);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
