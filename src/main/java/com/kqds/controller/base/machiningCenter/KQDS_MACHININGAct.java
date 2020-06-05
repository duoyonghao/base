/**  
  *
  * @Title:  KQDS_MACHININGAct.java   
  * @Package com.kqds.controller.base.machiningCenter   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月18日 下午3:27:57   
  * @version V1.0  
  */ 
package com.kqds.controller.base.machiningCenter;

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

import com.kqds.entity.base.KqdsMachining;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.machiningCenter.KQDS_MACHININGLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  KQDS_MACHININGAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月18日 下午3:27:57   
  *      
  */
@Controller
@RequestMapping("KQDS_MACHININGAct")
public class KQDS_MACHININGAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_MACHININGAct.class);
	
	@Autowired
	private KQDS_MACHININGLogic logic;
	
	/**
	  * @Title: insertOrUpdate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月18日 下午3:41:11
	 */
	@RequestMapping("insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			KqdsMachining dp = new KqdsMachining();
			BeanUtils.populate(dp, request.getParameterMap());
			if(!YZUtility.isNullorEmpty(dp.getSeqId())){
				logic.update(dp);
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_MACHINING, dp,dp.getCreateuser(),
						TableNameUtil.KQDS_MACHINING, request);
			}else{
				dp.setSeqId(YZUtility.getUUID());
				dp.setSystemNumber(YZUtility.getSystemID());
				logic.insert(dp);
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MACHINING, dp,dp.getCreateuser(),
						TableNameUtil.KQDS_MACHINING, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: getProcessingRecords   
	  * @Description: TODO(加工中心获取需加工记录)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月20日 下午5:26:58
	 */
	@RequestMapping("/getProcessingRecords.act")
	public String getProcessingRecords(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String processUnit = request.getParameter("processUnit");
		String searching = request.getParameter("searching");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String fjstarttime = request.getParameter("fjstarttime");
		String fjendtime = request.getParameter("fjendtime");
		String hjstarttime = request.getParameter("hjstarttime");
		String hjendtime = request.getParameter("hjendtime");
		String dzstarttime = request.getParameter("dzstarttime");
		String dzendtime = request.getParameter("dzendtime");
		String status = request.getParameter("status");
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(YZUtility.isNotNullOrEmpty(status)){
				
			}
			if(YZUtility.isNotNullOrEmpty(searching)){
				
			}
			if(YZUtility.isNotNullOrEmpty(processUnit)){
				map.put("processUnit", processUnit);
			}
			if(YZUtility.isNotNullOrEmpty(starttime)){
				map.put("starttime", starttime);
			}
			if(YZUtility.isNotNullOrEmpty(endtime)){
				map.put("endtime", endtime);
			}
			if(YZUtility.isNotNullOrEmpty(searching)){
				map.put("searching", searching);
			}
			List<JSONObject> list = logic.getRecords(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: getWorksheet   
	  * @Description: TODO(医生获取申请的加工单)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月20日 下午5:28:39
	 */
	@RequestMapping("/getWorksheet.act")
	public String getWorksheetByusercode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String searching = request.getParameter("searching");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("searching", searching);
			List<JSONObject> records = logic.getRecords(map);
			YZUtility.RETURN_LIST(records, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: getParticulars   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月21日 下午2:20:42
	 */
	@RequestMapping("/getParticularsBySeqId.act")
	public String getParticularsBySeqId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("seqId", id);
			JSONObject json = logic.getParticularsBySeqId(map);
			YZUtility.DEAL_SUCCESS(json, id, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: getGenre   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月29日 上午10:21:34
	 */
	@RequestMapping("/getGenre.act")
	public String getGenre(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try{
			List<JSONObject> list = logic.getGenre(id);
			YZUtility.RETURN_LIST(list, response, logger);
		}catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据患者编号查询当前患者名下所有的加工单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectMachineByUsercode.act")
	public String selectMachineByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			List<JSONObject> list = logic.selectMachineByUsercode(usercode);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: updateStatus   
	  * @Description: TODO(加工单流转)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2020年1月1日 下午3:48:48
	 */
	@RequestMapping("/updateStatus.act")
	public String updateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
		YZPerson person = SessionUtil.getLoginPerson(request);
		String status = request.getParameter("status");
		String data = request.getParameter("onclickrow");
		try {
			KqdsMachining dp = new KqdsMachining();
			if(data != null && !data.equals("")){
				JSONObject fromObject = JSONObject.fromObject(data);
				dp.setSeqId(fromObject.getString("seqId"));
				dp.setSystemNumber(fromObject.getString("systemnumber"));
				dp.setUserCode(fromObject.getString("usercode"));
				dp.setUserName(fromObject.getString("username"));
				dp.setOrganization(fromObject.getString("organization"));
				dp.setStatus(status);
			}
			logic.updateStatus(dp,request);
			//操作
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_MACHINING, dp,person.getSeqId(),
					TableNameUtil.KQDS_MACHINING, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: delRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2020年1月12日 上午9:57:40
	 */
	@RequestMapping("/delRecord.act")
	public String delRecord(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			String id = request.getParameter("id");
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			logic.delRecord(map);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: getFlow   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: String
	  * @dateTime:2020年1月16日 下午3:07:10
	 */
	@RequestMapping("/getFlow.act")
	public String getFlow(HttpServletRequest request, HttpServletResponse response){
		String worksheetId = request.getParameter("worksheetId");
		try {
			Map<String, String> map = new HashMap<String, String>(); 
			map.put("worksheetId", worksheetId);
			List<JSONObject> list = logic.getFlow(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
