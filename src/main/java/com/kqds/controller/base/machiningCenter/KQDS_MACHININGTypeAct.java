/**  
  *
  * @Title:  KQDS_MACHININGAct.java   
  * @Package com.kqds.controller.base.machiningCenter   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月13日 上午9:00:36   
  * @version V1.0  
  */ 
package com.kqds.controller.base.machiningCenter;

import java.util.ArrayList;
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

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.base.KqdsMachiningType;
import com.kqds.service.base.machiningCenter.KQDS_MACHININGTypeLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  KQDS_MACHININGAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月13日 上午9:00:36   
  *      
  */
@Controller
@RequestMapping("KQDS_MACHININGTypeAct")
public class KQDS_MACHININGTypeAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_MACHININGTypeAct.class);
	@Autowired
	private KQDS_MACHININGTypeLogic logic;

	@RequestMapping(value = "/toJgIndex.act")
	public ModelAndView toJgIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/jgzx/jg_index.jsp");
		return mv;
	}
	
	/**
	  * @Title: toJgAddGenre   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年12月6日 上午10:05:33
	 */
	@RequestMapping(value = "/toJgAddGenre.act")
	public ModelAndView toJgAddGenre(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/jiagong/jg_reparierenManage.jsp");
		return mv;
	}
	
	/**
	  * @Title: toSave   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年12月4日 下午12:46:19
	 */
	@RequestMapping(value = "/toAddProcess.act")
	public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/jiagong/add_process.jsp");
		return mv;
	}
	/**
	  * @Title: toCkHouse   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年12月5日 下午5:32:23
	 */
	@RequestMapping(value = "/toOneClassify.act")
	public ModelAndView toCkHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/jiagong/first_classify.jsp");
		return mv;
	}
	
	/**
	  * @Title: toAddFirstclassify   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年12月6日 上午10:05:23
	 */
	@RequestMapping(value = "/toAddFirstclassify.act")
	public ModelAndView toAddFirstclassify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String typename = request.getParameter("typename");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("typename", typename);
		mv.setViewName("/kqdsFront/jiagong/add_firstclassify.jsp");
		return mv;
	}
	
	/**
	  * @Title: RepairTypeManage   
	  * @Description: TODO(新增/修改修复类型)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年12月5日 下午4:52:04
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			KqdsMachiningType dp = new KqdsMachiningType();
			BeanUtils.populate(dp, request.getParameterMap());
			if(dp.getSeqId() != null && !dp.getSeqId().equals("")){
				logic.updateSingleUUID(TableNameUtil.KQDS_MACHINING_TYPE, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.MODIFY, dp, TableNameUtil.KQDS_OUTPROCESSING_TYPE, request);
			}else{
				dp.setSeqId(YZUtility.getUUID());
				String parentId = dp.getParentId();
				if(parentId == null){
					dp.setParentId("1");
				}
				if(dp.getIsCategory() == null){
					dp.setIsCategory("0");
				}
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setOrganization(organization);//添加门诊
				dp.setUseflag(1);
				logic.saveSingleUUID(TableNameUtil.KQDS_MACHINING_TYPE, dp);
				// 记录日志
				SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.NEW, dp, TableNameUtil.KQDS_MACHINING_TYPE, request);
			}
			YZUtility.DEAL_SUCCESS_VALID(true, response);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: getPrimaryCategories   
	  * @Description: TODO(获取一级类别)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月5日 下午5:05:28
	 */
	@RequestMapping("/getPrimaryCategories.act")
	public String getPrimaryCategories(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String parentId = request.getParameter("parentId");
		try {
			List<JSONObject> listAll = new ArrayList<JSONObject>();
			Map<String, String> map = new HashMap<String, String>();
			if(YZUtility.isNullorEmpty(parentId)){
				map.put("parentId", "1");
				listAll = logic.getListAll(map);
			}
			YZUtility.RETURN_LIST(listAll, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: getPrimaryCategories   
	  * @Description: TODO(获取一级类别)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年12月5日 下午5:05:28
	 */
	@RequestMapping("/initPrimary.act")
	public String initPrimary(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String parentId = request.getParameter("parentId");
		String lv = request.getParameter("lv"); // 树的级
		Map<String, String> map = new HashMap<String, String>();
		List<JSONObject> listTree = new ArrayList<JSONObject>();
		try {
			if(YZUtility.isNullorEmpty(parentId)){
				map.put("parentId", "1");
				map.put("isCategory", "1");
				List<JSONObject> listAll = logic.getCategory(map);
				if(listAll != null && listAll.size()>0){
					for (JSONObject jsonObject : listAll) {
						JSONObject json = new JSONObject();
						json.put("id", jsonObject.get("seq_id"));
						json.put("name", jsonObject.get("typename"));
						json.put("open", "false");
						List<JSONObject> getnextType = getnextType(jsonObject.getString("seq_id"));
						if(getnextType != null && getnextType.size() > 0){
							// 有子类 isParent true
							json.put("isParent", "true");
						}else{
							json.put("isParent", "false");
						}
						listTree.add(json);
					}
				}
			}else{
				if("1".equals(lv)){
					map.put("parentId", parentId);
					map.put("isCategory", "1");
					List<JSONObject> listAll = logic.getCategory(map);
					if(listAll != null && listAll.size()>0){
						for (JSONObject jsonObject : listAll) {
							JSONObject json = new JSONObject();
							json.put("id", jsonObject.get("seq_id"));
							json.put("name", jsonObject.get("typename"));
							json.put("pId", jsonObject.get("seq_id"));
							List<JSONObject> getnextType = getnextType(jsonObject.getString("seq_id"));
							if(getnextType != null && getnextType.size() > 0){
								// 有子类 isParent true
								json.put("isParent", "true");
							}else{
								json.put("isParent", "false");
							}
							listTree.add(json);
						}
					}
				}else{
					map.put("parentId", parentId);
					map.put("isCategory", "1");
					List<JSONObject> listAll = logic.getCategory(map);
					if(listAll != null && listAll.size()>0){
						for (JSONObject jsonObject : listAll) {
							JSONObject json = new JSONObject();
							json.put("id", jsonObject.get("seq_id"));
							json.put("pId", "0");
							json.put("name", jsonObject.get("typename"));
							json.put("nocheck", "true");
							List<JSONObject> getnextType = getnextType(jsonObject.getString("seq_id"));
							if(getnextType != null && getnextType.size() > 0){
								// 有子类 isParent true
								json.put("isParent", "true");
							}else{
								json.put("isParent", "false");
							}
							listTree.add(json);
						}
					}
				}
			}
			
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, listTree);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: getnextType   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param parentId
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年12月16日 下午2:19:27
	 */
	public List<JSONObject> getnextType(String parentId) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("parentId", parentId);
		map.put("isCategory", "1");
		return logic.getCategory(map);
	}
	/**
	  * @Title: getnextType   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param parentId
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年12月16日 下午2:19:27
	 */
	@RequestMapping("/findnextType.act")
	public String findnextType(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String parentId = request.getParameter("parentId");
		
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(YZUtility.isNotNullOrEmpty(parentId)){
				map.put("parentId", parentId);
			}
			List<JSONObject> list = logic.findnextType(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: findPrimary   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年12月16日 下午2:19:20
	 */
	@RequestMapping("/findPrimary.act")
	public String findPrimary(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String parentId = request.getParameter("parentId");
		try {
			Map<String, String> map = new HashMap<String, String>();
			List<JSONObject> list = new ArrayList<JSONObject>();
			if(YZUtility.isNullorEmpty(parentId)){
				map.put("parentId", "");
				map.put("isCategory", "0");
				list = logic.getListAll(map);
			}else{
				map.put("parentId", parentId);
				map.put("isCategory", "0");
				list = logic.getListAll(map);
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: findPrimary   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年12月16日 下午2:19:20
	 */
	@RequestMapping("/findPrimaryByParentId.act")
	public String findPrimaryByParentId(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String parentId = request.getParameter("parentId");
		try {
			Map<String, String> map = new HashMap<String, String>();
				if(YZUtility.isNullorEmpty(parentId)){
					map.put("parentId", "1");
				}else {
					map.put("parentId", parentId);
				}
				List<JSONObject> list = logic.getListAll(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: delPrimaryBySeqId   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年12月19日 上午10:55:15
	 */
	@RequestMapping("/delPrimaryBySeqId.act")
	public String delPrimaryBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String seqId = request.getParameter("seqId");
		try {
			logic.delPrimaryBySeqId(seqId);
			YZUtility.DEAL_SUCCESS_VALID(true, response);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(seqId, false, e, response, logger);
		}
		return null;
	}
}
