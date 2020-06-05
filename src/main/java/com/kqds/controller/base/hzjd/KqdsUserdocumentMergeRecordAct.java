/**  
  *
  * @Title:  KqdsUserdocumentMergeRecordAct.java   
  * @Package com.kqds.controller.base.hzjd   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年7月29日 下午3:13:06   
  * @version V1.0  
  */ 
package com.kqds.controller.base.hzjd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsUserdocumentMergeRecord;
import com.kqds.service.base.hzjd.UserdocumentMergeRecordLogic;
import com.kqds.util.sys.YZUtility;

/**  
  * 
  * @ClassName:  KqdsUserdocumentMergeRecordAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年7月29日 下午3:13:06   
  *      
  */
@Controller
@RequestMapping("KqdsUserdocumentMergeRecordAct")
public class KqdsUserdocumentMergeRecordAct {
	
	private Logger logger = LoggerFactory.getLogger(KqdsUserdocumentMergeRecordAct.class);
	
	@Autowired
	private UserdocumentMergeRecordLogic mergeLogic;
	
	@RequestMapping(value = "/toHzjd_NetPlantDept.act")
	public ModelAndView toHzjd_NetPlantDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/hzjd/hzjd_net_plant.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/saveMergeRecord.act")
	public String saveMergeRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		KqdsUserdocumentMergeRecord dp = new KqdsUserdocumentMergeRecord();
		try {
			mergeLogic.saveMergeRecord(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

}
