/**  
  *
  * @Title:  HUDH_LcljVerificationSheet.java   
  * @Package com.hudh.lclj.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月23日 下午6:43:48   
  * @version V1.0  
  */ 
package com.hudh.lclj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hudh.lclj.entity.LcljApprovedMemo;
import com.hudh.lclj.service.HUDH_LcljVerificationSheetService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  HUDH_LcljVerificationSheet   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月23日 下午6:43:48   
  *      
  */
@Controller
@RequestMapping("HUDH_LcljCheckRecordAct")
public class HUDH_LcljVerificationAct {
	
	/**
	 * 操作日志记录
	 */
	private static Logger logger = LoggerFactory.getLogger(HUDH_LcljVerificationAct.class);
	
	@Autowired
	private HUDH_LcljVerificationSheetService logic;
	
	@RequestMapping(value = "/toRemindAssessor.act")
	public ModelAndView toRemindAssessor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hudh/lclj/assessors_remind.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toAwaitRemindAssessor.act")
	public ModelAndView toAwaitRemindAssessor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hudh/lclj/await_assessors_remind.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toAssessor.act")
	public ModelAndView toAssessor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hudh/lclj/assessors.jsp");
		return mv;
	}
	
	/**
	  * @Title: insertOrUpdate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月23日 下午7:01:33
	 */
	@RequestMapping("/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String lcljid = request.getParameter("lcljid");
		try {
			LcljApprovedMemo bean = new LcljApprovedMemo();
			BeanUtils.populate(bean, request.getParameterMap());
			bean.setLcljId(lcljid);
			if(!YZUtility.isNullorEmpty(bean.getSeqId())){
				logic.Update(bean);
			}else{
				bean.setSeqId(YZUtility.getUUID());
			logic.insert(bean);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: getCheckRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月23日 下午7:17:41
	 */
	@RequestMapping("/getCheckRecord.act")
	public String getCheckRecord(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String LcljId = request.getParameter("LcljId");
		String createuser = request.getParameter("createuser");
		String status = request.getParameter("status");
		YZPerson person = SessionUtil.getLoginPerson(request);
		// 可见人员过滤
		String visualstaff = SessionUtil.getVisualstaff(request);
		try {
			Map<String, String> map = new HashMap<String, String>();
			if (YZUtility.isNotNullOrEmpty(visualstaff)) {
				map.put("querytype", visualstaff);
			}
			if(!YZUtility.isNullorEmpty(LcljId)){
				map.put("LcljId", LcljId);
			}else if(!YZUtility.isNullorEmpty(createuser)){
				map.put("createuser", createuser);
			}else{
				throw new Exception("请选择要操作的记录");
			}
			if(!YZUtility.isNullorEmpty(status)){
				map.put("status", status);
			}
			List<JSONObject> list = logic.getCheckRecord(map, person);
			for (JSONObject jsonO : list) {
				String nodename = jsonO.getString("nodename");
				String nodelimit = jsonO.getString("nodelimit");
				jsonO.put("name", nodename + "(" + nodelimit + ")");
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询未审核的记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAwaitCheckRecord.act")
	public String getAwaitCheckRecord(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String LcljId = request.getParameter("LcljId");
		String createuser = request.getParameter("createuser");
		String status = request.getParameter("status");
		YZPerson person = SessionUtil.getLoginPerson(request);
		// 可见人员过滤
		String visualstaff = SessionUtil.getVisualstaff(request);
		try {
			Map<String, String> map = new HashMap<String, String>();
			if (YZUtility.isNotNullOrEmpty(visualstaff)) {
				map.put("querytype", visualstaff);
			}
			if(!YZUtility.isNullorEmpty(LcljId)){
				map.put("LcljId", LcljId);
			}else if(!YZUtility.isNullorEmpty(createuser)){
				map.put("createuser", createuser);
			}else{
				throw new Exception("请选择要操作的记录");
			}
//			if(!YZUtility.isNullorEmpty(status)){
				map.put("status", "0");
//			}
			List<JSONObject> list = logic.getAwaitCheckRecord(map, person);
			for (JSONObject jsonO : list) {
				String nodename = jsonO.getString("nodename");
				String nodelimit = jsonO.getString("nodelimit");
				jsonO.put("name", nodename + "(" + nodelimit + ")");
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: getCheckRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月23日 下午7:17:41
	 */
	@RequestMapping("/getCheckRecordNum.act")
	public String getCheckRecordNum(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String createuser = request.getParameter("createuser");
		String status = request.getParameter("status");
		try {
			Map<String, String> map = new HashMap<String, String>();
			if(!YZUtility.isNullorEmpty(createuser)){
				map.put("createuser", createuser);
			}
			if(!YZUtility.isNullorEmpty(status)){
				map.put("status", status);
			}
			int count = logic.getCheckRecordNum(map);
			JSONObject json = new JSONObject();
			json.put("count", count);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/getAwaitVerifieNum.act") 
	public String getAwaitVerifieNum(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String createuser = request.getParameter("createuser");
		String status = request.getParameter("status");
		try {
			Map<String, String> map = new HashMap<String, String>();
			if(!YZUtility.isNullorEmpty(createuser)){
				map.put("createuser", null);
			}
			if(!YZUtility.isNullorEmpty(status)){
				map.put("status", "0");
			}
			int count = logic.getAwaitVerifieNum(map);
			JSONObject json = new JSONObject();
			json.put("count", count);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: delCheckRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年12月24日 下午2:42:49
	 */
	@RequestMapping("/delCheckRecord.act")
	public String delCheckRecord(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			if(YZUtility.isNullorEmpty(id)){
				throw new Exception("请选择需要删除的记录");
			}
			logic.delCheckRecord(id);
			YZUtility.DEAL_SUCCESS(null, id, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(id, false, e, response, logger);
		}
		return null;
	}
}
