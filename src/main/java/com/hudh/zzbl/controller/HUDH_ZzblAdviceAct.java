package com.hudh.zzbl.controller;


import com.hudh.zzbl.entity.ZzblAdvice;
import com.hudh.zzbl.service.IZzblAdviceService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;


	@Controller
	@RequestMapping({"/HUDH_ZzblAdviceAct"})
public class HUDH_ZzblAdviceAct {
		
	  private Logger logger = LoggerFactory.getLogger(HUDH_ZzblAdviceAct.class);
	  @Autowired
	  private IZzblAdviceService zzblAdviceService;
	  
	  /**
	    * @Title: saveCaseHistory   
	    * @Description: TODO(保存病历)   
	    * @param: @param request
	    * @param: @param response
	    * @param: @return
	    * @param: @throws Exception      
	    * @return: String
	   */
	  @RequestMapping("/saveCaseHistory.act")
	  public String saveCaseHistory(HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
		  ZzblAdvice zzblAdvice = new ZzblAdvice();
		  BeanUtils.populate(zzblAdvice, request.getParameterMap());
		  YZPerson person = SessionUtil.getLoginPerson(request);
		  String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		  zzblAdvice.setCreateuser(person.getSeqId());
		  zzblAdvice.setOrganization(organization);
		  zzblAdvice.setSEQ_ID(YZUtility.getUUID());
		  zzblAdvice.setCreatetime(YZUtility.getCurDateTimeStr());
		  zzblAdvice.setStatus("0");
	    try
	    {
			zzblAdviceService.saveCaseHistory(zzblAdvice);
	      YZUtility.DEAL_SUCCESS(null, "true", response, this.logger);
	    }
	    catch (Exception e)
	    {
	      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
	    }
	    return null;
	  }
	  
	  /**
	    * @Title: findCaseHistoryById   
	    * @Description: TODO(根据id查询病历)   
	    * @param: @param request
	    * @param: @param response
	    * @param: @return
	    * @param: @throws Exception      
	    * @return: JSONObject
	   */
	  @RequestMapping("/findCaseHistoryById.act")
	  public JSONObject findCaseHistoryById(HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
	    String id = request.getParameter("id");
	    try
	    {
	    	JSONObject caseHistory = zzblAdviceService.findCaseHistoryById(id);
	        YZUtility.DEAL_SUCCESS(caseHistory,null,response,this.logger);
	    }
	    catch (Exception e)
	    {
	      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
	    }
	    return null;
	  }
	  
	  /**
	    * @Title: updateCaseHistoryById   
	    * @Description: TODO(根据id修改病历)   
	    * @param: @param request
	    * @param: @param response
	    * @param: @return
	    * @param: @throws Exception      
	    * @return: String
	   */
	  @RequestMapping("/updateCaseHistoryById.act")
	  public String updateCaseHistoryById(HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
		  ZzblAdvice zzblAdvice = new ZzblAdvice();
		  BeanUtils.populate(zzblAdvice, request.getParameterMap());
		  YZPerson person = SessionUtil.getLoginPerson(request);
		  zzblAdvice.setUpdateuser(person.getSeqId());
		  zzblAdvice.setUpdatetime(YZUtility.getCurDateTimeStr());
	    try
	    {
			zzblAdviceService.updateCaseHistoryById(zzblAdvice);
	      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
	    }
	    catch (Exception e)
	    {
	      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
	    }
	    return null;
	  }
	  
	  /**
	    * @Title: deleteCaseHistory   
	    * @Description: TODO(根据Id删除病历)   
	    * @param: @param request
	    * @param: @param response
	    * @param: @return
	    * @param: @throws Exception      
	    * @return: String
	    */
	  @RequestMapping("/deleteCaseHistoryById.act")
	  public String deleteCaseHistoryById(HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
	    String id = request.getParameter("id");
	    try
	    {
			zzblAdviceService.deleteCaseHistory(id);
	      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
	    }
	    catch (Exception e)
	    {
	      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
	    }
	    return null;
	  }

}
