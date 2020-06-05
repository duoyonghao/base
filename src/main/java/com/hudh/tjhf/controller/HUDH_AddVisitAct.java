/**  
  *
  * @Title:  HUDH_AddVisitAct.java   
  * @Package com.hudh.tjhf.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月2日 上午9:03:09   
  * @version V1.0  
  */ 
package com.hudh.tjhf.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSONArray;
import com.hudh.lclj.StaticVar;
import com.hudh.tjhf.entity.VisitPlanTemplate;
import com.hudh.tjhf.entity.VisitTemplate;
import com.hudh.tjhf.service.IAddVisitService;
import com.hudh.util.HUDHUtil;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.base.push.KQDS_Pushogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;



/**  
  * 2019-11-02 syp
  * @ClassName:  HUDH_AddVisitAct   
  * @Description:TODO(回访计划)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月2日 上午9:03:09   
  *      
  */
@Controller
@RequestMapping("/HUDH_AddVisitAct")
public class HUDH_AddVisitAct {
	
	public static Logger logger = LoggerFactory.getLogger(HUDH_AddVisitAct.class);
	
	@Autowired
	private IAddVisitService visitService;
	@Autowired
	private KQDS_Pushogic pushLogic;
	
	@Autowired
	private YZPrivLogic privLogic;
	
	@Autowired
	private YZDeptLogic yzDeptLogic;
	
	@Autowired
	private YZParaLogic yzLogic;
	
	
	/**
	  * @Title: addVisitTemplate   
	  * @Description: TODO(按权限区分模板)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年11月2日 下午4:12:56
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping("/addVisitTemplate.act")
	public String addVisitTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String attribute = request.getParameter("visttemplate");
		List<VisitTemplate> list = new ArrayList<VisitTemplate>();
		if(attribute!=null){			
			attribute = java.net.URLDecoder.decode(attribute, "UTF-8");
			list = HUDHUtil.parseJsonToObjectList(attribute, VisitTemplate.class);
		}
		YZPerson person = SessionUtil.getLoginPerson(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = sdf.format(date);
		try {
			List<VisitTemplate> Templatelist = new ArrayList<VisitTemplate>();
			for (VisitTemplate visitTemplate : list) {
				if(visitTemplate.getSeqId()==null||visitTemplate.getSeqId().equals("")){
					visitTemplate.setSeqId(YZUtility.getUUID());
				}
				if(visitTemplate.getOrganization()==null||visitTemplate.getOrganization().equals("")){
					visitTemplate.setOrganization(ChainUtil.getCurrentOrganization(request));
				}
				if(visitTemplate.getStatus()==null||visitTemplate.getStatus().equals("")){
					visitTemplate.setStatus("0");
				}
				if(visitTemplate.getCreartuser()==null||visitTemplate.getCreartuser().equals("")){
					visitTemplate.setCreartuser(person.getUserName());
				}
				if(visitTemplate.getCrearttTime()==null||visitTemplate.getCrearttTime().equals("")){
					visitTemplate.setCrearttime(time);
				}
				Templatelist.add(visitTemplate);
			}
			visitService.saveVisitTemalate(Templatelist);
			YZUtility.DEAL_SUCCESS(null, "", response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: findTemplate   
	  * @Description: TODO(查询模板)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年11月2日 下午4:31:51
	 */
	@RequestMapping("/findTemplate.act")
	public String findTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		YZPerson loginPerson = SessionUtil.getLoginPerson(request);
		YZPriv yzPriv = privLogic.getDetailBySeqId(loginPerson.getUserPriv());
		String organization=ChainUtil.getCurrentOrganization(request);
		try {
			String s = new String();
			String[] split = yzPriv.getVisitDept().split(",");
			int a = split.length;
			for (String string : split) {
				if(a>1){
					s+="'"+string+"'"+",";
				}else{
					s+="'"+string+"'";
				}
				a--;
			}
			if(yzPriv != null){
				map.put("hfdept", s);
			}
			if(organization!=null){
				map.put("organization", organization);
			}
			List<VisitTemplate> list = visitService.findTemplate(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: findTemplate   
	  * @Description: TODO(查询模板)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年11月2日 下午4:31:51
	 */
	@RequestMapping("/findvisitTemplate.act")
	public String findvisitTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		YZPerson loginPerson = SessionUtil.getLoginPerson(request);
		YZPriv yzPriv = privLogic.getDetailBySeqId(loginPerson.getUserPriv());
		String organization=ChainUtil.getCurrentOrganization(request);
		try {
			String s = new String();
			String[] split = yzPriv.getVisitDept().split(",");
			int a = split.length;
			for (String string : split) {
				if(a>1){
					s+="'"+string+"'"+",";
				}else{
					s+="'"+string+"'";
				}
				a--;
			}
			if(yzPriv != null){
				map.put("hfdept", s);
			}
			if(organization!=null){
				map.put("organization",organization);
			}
			List<JSONObject> list = visitService.findvisitTemplate(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: addVisitPlanTemplate   
	  * @Description: TODO(保存小计划模板)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年11月2日 下午4:12:41
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addVisitPlanTemplate.act")
	public String addVisitPlanTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String attribute = request.getParameter("vistplantemplate");
		List<VisitPlanTemplate> list = new ArrayList<VisitPlanTemplate>();
		if(attribute!=null){			
			attribute = java.net.URLDecoder.decode(attribute, "UTF-8");
			list = HUDHUtil.parseJsonToObjectList(attribute, VisitPlanTemplate.class);
		}
		YZPerson person = SessionUtil.getLoginPerson(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = sdf.format(date);
		try {
			List<VisitPlanTemplate> Templatelist = new ArrayList<VisitPlanTemplate>();
			for (VisitPlanTemplate visitTemplate : list) {
				if(visitTemplate.getSEQ_ID()!=null&&!visitTemplate.getSEQ_ID().equals("")){
					visitTemplate.setCreatetuser(person.getUserName());
					visitTemplate.setCreatetime(time);
				}else{
					visitTemplate.setSEQ_ID(YZUtility.getUUID());
					visitTemplate.setOrganization(ChainUtil.getCurrentOrganization(request));
					visitTemplate.setStatus("0");
					visitTemplate.setCreatetuser(person.getUserName());
					visitTemplate.setCreatetime(time);
				}
				Templatelist.add(visitTemplate);
			}
			visitService.saveVisitPlanTemalate(list);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: findTemplate   
	  * @Description: TODO(查询模板)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年11月2日 下午4:31:51
	 */
	@RequestMapping("/findvisitPlanTemplate.act")
	public String findvisitPlanTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String managarId = request.getParameter("managarId");
		try {
			List<VisitTemplate> list = visitService.findvisitPlanTemplate(managarId);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping("/findvisitByTime.act")
	public String findvisitByTime(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			//根据人员查询当前时间下的回访
			YZPerson person = SessionUtil.getLoginPerson(request);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String time = df.format(new Date());
			//根据时间和人员id查询回访
			Map<String,String> map = new HashMap<String,String>();
			map.put("seqid", person.getSeqId());
			map.put("time", time);
			map.put("reciveuser", person.getSeqId());
			map.put("notifyType", "回访提醒");
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			map.put("starttime", time+ ConstUtil.TIME_START);
			map.put("endtime", time+ ConstUtil.TIME_END);
			//查询判断是否已经推送当天数据
			String seqid=pushLogic.selectPushSeqid(map);
			if(YZUtility.isNullorEmpty(seqid)){
				int list=visitService.findvisitByTimeNum(map);
				if(list>0){
					KqdsVisit visit = new KqdsVisit();
					visit.setVisitor(person.getSeqId());
					visit.setUsername(person.getUserName());
					visit.setVisittime(time);
					PushUtil.selectTx4NewVisit(visit, request);
				}
			}
			YZUtility.RETURN_OBJ(null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	 * 修改模板状态 禁用-启用
	 * <p>Title: updateTemplateStatus</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月8日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateTemplateStatus.act")
	public String updateTemplateStatus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String bpId= request.getParameter("bpId");
			String status=request.getParameter("status");
			VisitTemplate visit= new VisitTemplate();
			visit.setSeqId(bpId);
			if(status.equals("0")){
				visit.setStatus("1");
			}else{
				visit.setStatus("0");
			}
			int i = visitService.updateManagarStatus(visit);
			if(i>0){
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	@RequestMapping("/deleteManagarPlan.act")
	public String deleteManagarPlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			
			String spDelArr=request.getParameter("spDelArr");
			JSONArray picArray = JSONArray.parseArray(spDelArr);
			int j=0;
			if(picArray!=null){
				StringBuffer str= new StringBuffer();
				for (int i = 0; i < picArray.size(); i++) {
					if(i==picArray.size()-1){
						str.append("\'"+picArray.get(i)+"\'");
					}else{
						str.append("\'"+picArray.get(i)+"\',");
					}
				}
				j=visitService.deleteManagarPlan(str.toString());
				
			}
			if(j>0){
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	@RequestMapping("/deleteManagar.act")
	public String deleteManagar(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			
			String bpDelArr=request.getParameter("bpDelArr");
			JSONArray picArray = JSONArray.parseArray(bpDelArr);
			int j=0;
			if(picArray!=null){
				StringBuffer str= new StringBuffer();
				for (int i = 0; i < picArray.size(); i++) {
					if(i==picArray.size()-1){
						str.append("\'"+picArray.get(i)+"\'");
					}else{
						str.append("\'"+picArray.get(i)+"\',");
					}
				}
				j=visitService.deleteManagar(str.toString());
			}
			if(j>0){
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: jurisdiction   
	  * @Description: TODO(根据名字查配置)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年11月9日 下午3:30:49
	 */
	@RequestMapping("/jurisdiction.act")
	public String jurisdiction(HttpServletRequest request,HttpServletResponse response) throws Exception{
			YZPerson loginPerson = SessionUtil.getLoginPerson(request);
			YZPriv yzPriv = privLogic.getDetailBySeqId(loginPerson.getUserPriv());
			try {
				String s = new String();
				String[] split = yzPriv.getVisitDept().split(",");
				int a = split.length;
				for (String string : split) {
					if(a>1){
						s+="'"+string+"'"+",";
					}else{
						s+="'"+string+"'";
					}
					a--;
				}
			List<JSONObject> selectBeanListByMap = yzDeptLogic.selectBeanListByMap(s);
			YZUtility.RETURN_LIST(selectBeanListByMap, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: jurisdiction   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年11月28日 下午2:13:17
	 */
	@RequestMapping("/editability.act")
	public String editability(HttpServletRequest request,HttpServletResponse response) throws Exception{
			try {
					YZPerson person = SessionUtil.getLoginPerson(request);
					List<String> list = new ArrayList<String>();
					String paraValueByName = yzLogic.getParaValueByName(SysParaUtil.SYS_POSITION);
					if(paraValueByName != null && !paraValueByName.equals("")){
						/*String[] split = paraValueByName.split(",");
						for (String string : split) {
							list.add(string);
						}*/
						if(paraValueByName.contains(person.getUserPriv())){
							YZUtility.DEAL_SUCCESS_VALID(true, response);
						}else{
							YZUtility.DEAL_SUCCESS_VALID(false, response);
						}
					}
					YZUtility.RETURN_LIST(list, response, logger);
				} catch (Exception e) {
					// TODO: handle exception
					YZUtility.DEAL_ERROR(null, false, e, response, logger);
				}
				return null;
	}
}
