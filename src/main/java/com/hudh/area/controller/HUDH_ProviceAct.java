/**  
  *
  * @Title:  HUDH_ProviceAct.java   
  * @Package com.hudh.area.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 下午6:57:52   
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

import com.hudh.area.service.IProviceService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  HUDH_ProviceAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 下午6:57:52   
  *      
  */
@Controller
@RequestMapping("HUDH_ProviceAct")
public class HUDH_ProviceAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_ProviceAct.class);
	
	@Autowired
	private IProviceService proviceService;
	
	@RequestMapping(value = "/findAll.act")
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> list = proviceService.findAll();
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

}
