package com.hudh.zzbl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.zzbl.entity.ZzblOperation;
import com.hudh.zzbl.service.IZzblService;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

/**
 * 病历操作类
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/HUDH_ZzblAct")
public class HUDH_ZzblAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_ZzblAct.class);
	
	@Autowired
	private IZzblService zzblService;
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.act")
	public String save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ZzblOperation dp = new ZzblOperation();
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		dp.setCreateuser(person.getUserId());
		dp.setOrganization(organization);
		try {
			zzblService.save(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findZzblOprationById.act")
	public JSONObject findZzblOprationById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		try {
			List<ZzblOperation> json = zzblService.findZzblOprationById(id);
			YZUtility.RETURN_LIST(json, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询所有的病历信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAllZzblInfor.act")
	public String findAllZzblInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> list = zzblService.findAllZzblInfor();
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id更新病历信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateZzblOprationById.act")
	public String updateZzblOprationById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id  = request.getParameter("id");
		JSONObject jsonObject = zzblService.selectZzblOperationById(id);
		String seqId = jsonObject.getString("seq_id");
		ZzblOperation dp = (ZzblOperation) JSONObject.toBean(jsonObject, ZzblOperation.class);
		dp.setSEQ_ID(seqId);
		try {
			zzblService.updateZzblOprationById(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

	/**
	  * @Title: selectedScheme   
	  * @Description: TODO(选中方案)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2020年4月23日 上午9:32:41
	 */
	@RequestMapping("/selectedAnamnesisInfor.act")
	public String selectedAnamnesisInfor(HttpServletRequest request, HttpServletResponse response) throws Exception{
			String id = request.getParameter("id");
			String status = request.getParameter("status");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("status", status);
			dao.update("HUDH_DZBL_AdviceNote.selectedAnamnesisInfor", map);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: selectedScheme   
	  * @Description: TODO(选中方案)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2020年4月23日 上午9:32:41
	 */
	@RequestMapping("/selectedExamineDiagnoseInfor.act")
	public String selectedExamineDiagnoseInfor(HttpServletRequest request, HttpServletResponse response) throws Exception{
			String id = request.getParameter("id");
			String status = request.getParameter("status");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("status", status);
			dao.update("HUDH_ZZBL_CHECK.selectedExamineDiagnoseInfor", map);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: selectedScheme   
	  * @Description: TODO(选中方案)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2020年4月23日 上午9:32:41
	 */
	@RequestMapping("/selectedScheme.act")
	public String selectedScheme(HttpServletRequest request, HttpServletResponse response) throws Exception{
			String id = request.getParameter("id");
			String status = request.getParameter("status");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("status", status);
			dao.update("HUDH_ZZBL_DETAIL.selectedScheme", map);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: selectedScheme   
	  * @Description: TODO(选中方案)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2020年4月23日 上午9:32:41
	 */
	@RequestMapping("/selectedRepairProjectInfor.act")
	public String selectedRepairProjectInfor(HttpServletRequest request, HttpServletResponse response) throws Exception{
			String id = request.getParameter("id");
			String status = request.getParameter("status");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("status", status);
			dao.update("HUDH_ZZBL_REPAIR_DETAIL.selectedRepairProjectInfor", map);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}