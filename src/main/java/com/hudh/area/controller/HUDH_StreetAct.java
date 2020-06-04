/**  
  *
  * @Title:  HUDH_StreetAct.java   
  * @Package com.hudh.area.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月15日 上午11:26:04   
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

import com.hudh.area.service.IStreetService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  HUDH_StreetAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月15日 上午11:26:04   
  *      
  */
@Controller
@RequestMapping("HUDH_StreetAct")
public class HUDH_StreetAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_StreetAct.class);
	
	@Autowired
	private IStreetService streetService;
	
	@RequestMapping(value = "/findStreetByAreaCode.act")
	public String findStreetByAreaCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String areaCode = request.getParameter("areaCode");
//		if(areaCode.equals("")) {
//			areaCode = "110101";
//		}
		try {
			List<JSONObject> list = streetService.findStreetByAreaCode(areaCode);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

}
