/**  
  *
  * @Title:  HUDH_LcljOrderTrackDeleteAct.java   
  * @Package com.hudh.lclj.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月3日 上午11:08:25   
  * @version V1.0  
  */ 
package com.hudh.lclj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import com.hudh.lclj.service.ILcljOrderTrackDeleteService;
import com.kqds.util.sys.YZUtility;

/**  
  * 
  * @ClassName:  HUDH_LcljOrderTrackDeleteAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年9月3日 上午11:08:25   
  *      
  */
@Controller
@RequestMapping("/HUDH_LcljOrderTrackDeleteAct")
public class HUDH_LcljOrderTrackDeleteAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_LcljOrderTrackDeleteAct.class);
	
	@Autowired
	private ILcljOrderTrackDeleteService trackDeleteService;
	
	@RequestMapping(value = "/save.act")
	public String save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LcljOrderTrackDeleteRecord dp = new LcljOrderTrackDeleteRecord();
		try {
			trackDeleteService.save(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

}
