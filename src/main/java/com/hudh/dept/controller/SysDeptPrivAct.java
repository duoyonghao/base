package com.hudh.dept.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.dept.service.ISysDeptPrivService;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;



@Controller
@RequestMapping("SysDeptPrivAct")
public class SysDeptPrivAct {
private static Logger logger = LoggerFactory.getLogger(SysDeptPrivAct.class);

	@Autowired
	private ISysDeptPrivService sysDeptPrivService;
	@Autowired
	private YZDeptLogic yzDeptLogic;
	
	@RequestMapping("/findDeptNameByButtonName.act")
	public String findDeptNameByButtonName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String buttonname=request.getParameter("qxname");
		String organization=request.getParameter("organization");
		try {
			if(!buttonname.equals("")&&buttonname!=null){
				Map<String,String> map = new HashMap<String,String>();
				String[] buttonnamelist = buttonname.split(",");
				if(buttonnamelist.length==1){
					map.put("buttonname", "\'"+buttonnamelist[0]+"\'");
				}else{
					StringBuilder str = new StringBuilder();
					for (int i = 0; i < buttonnamelist.length; i++) {
						if(i==buttonnamelist.length-1){
							str.append("\'"+buttonnamelist[i]+"\'");
						}else{
							str.append("\'"+buttonnamelist[i]+"\',");
						}
					}
					map.put("buttonname", str.toString());
				}
				if(!YZUtility.isNullorEmpty(organization)){
					map.put("organization", organization);
				}else{
					map.put("organization",ChainUtil.getCurrentOrganization(request));
				}
				List<JSONObject> list=sysDeptPrivService.findDeptNameByButtonName(map);
				
				YZUtility.RETURN_LIST(list, response, logger);
			}else{
				YZUtility.RETURN_LIST(null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping("/insertSysDeptPriv.act")
	public String insertSysDeptPriv(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String buttonname=request.getParameter("qxname");
		//部门id
		String deptNoCompilations=request.getParameter("deptNoCompilations");
		//部门类别
		String deptType=request.getParameter("deptType");
		//String deptName=request.getParameter("deptName");
		String organization=request.getParameter("organization");
		if(YZUtility.isNullorEmpty(organization)){
			organization = ChainUtil.getCurrentOrganization(request);//获取当前门诊
		}
		YZPerson person = SessionUtil.getLoginPerson(request);
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(buttonname)){
				map.put("buttonname", buttonname);
			}
			if(!YZUtility.isNullorEmpty(deptNoCompilations)){
				StringBuffer strs= new StringBuffer();
				JSONArray array= new JSONArray(deptNoCompilations);
				for (int i = 0; i < array.length(); i++) {
					if(i==array.length()-1){
						strs.append(array.getString(i));
					}else{
						strs.append(array.getString(i)+",");
					}
				}
				map.put("deptNoCompilations", strs.toString());
				List<YZDept> deptlist=yzDeptLogic.getDeptListBySeqIds(strs.toString());
				StringBuffer strname= new StringBuffer();
				for (int i = 0; i < deptlist.size(); i++) {
					if(i==deptlist.size()-1){
						strname.append(deptlist.get(i).getDeptName());
					}else{
						strname.append(deptlist.get(i).getDeptName()+",");
					}
				}
				map.put("deptName", strname.toString());
			}
			if(!YZUtility.isNullorEmpty(deptType)){
				map.put("deptType", deptType);
			}
			map.put("organization", organization);
			map.put("createuser", person.getSeqId());
			sysDeptPrivService.insertSysDeptPriv(map);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping("/findDeptByDeptPrivId.act")
	public String findDeptByDeptPrivId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptprivid=request.getParameter("deptprivid");
		try {
			if(!deptprivid.equals("")&&deptprivid!=null){
				List<JSONObject> list=sysDeptPrivService.findDeptPrivByDeptPrivId(deptprivid);
				
				YZUtility.RETURN_LIST(list, response, logger);
			}else{
				YZUtility.RETURN_LIST(null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping("/updateSysDeptPriv.act")
	public String updateSysDeptPriv(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptprivid=request.getParameter("deptprivid");
		//部门id
		String deptNoCompilations=request.getParameter("deptNoCompilations");
		//部门类别
		String deptType=request.getParameter("deptType");
		YZPerson person = SessionUtil.getLoginPerson(request);
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(deptprivid)){
				map.put("deptprivid", deptprivid);
			}
			if(!YZUtility.isNullorEmpty(deptNoCompilations)){
				StringBuffer strs= new StringBuffer();
				JSONArray array= new JSONArray(deptNoCompilations);
				for (int i = 0; i < array.length(); i++) {
					if(i==array.length()-1){
						strs.append(array.getString(i));
					}else{
						strs.append(array.getString(i)+",");
					}
				}
				map.put("deptNoCompilations", strs.toString());
				List<YZDept> deptlist=yzDeptLogic.getDeptListBySeqIds(strs.toString());
				StringBuffer strname= new StringBuffer();
				for (int i = 0; i < deptlist.size(); i++) {
					if(i==deptlist.size()-1){
						strname.append(deptlist.get(i).getDeptName());
					}else{
						strname.append(deptlist.get(i).getDeptName()+",");
					}
				}
				map.put("deptName", strname.toString());
			}
			if(!YZUtility.isNullorEmpty(deptType)){
				map.put("deptType", deptType);
			}
			map.put("createuser", person.getSeqId());
			int i=sysDeptPrivService.updateSysDeptPriv(map);
			if(i>0){
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}else{
				throw new RuntimeException("修改失败");
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	 * 根据部门id查询部门内人员
	 * <p>Title: findPersonByDeptId</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findPersonByDeptId.act")
	public String findPersonByDeptId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//部门id
		String deptid=request.getParameter("deptid");
		String qxname=request.getParameter("qxname");
		String organization=request.getParameter("organization");
		try {
			//all 查询所有人员
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(deptid)){
				map.put("deptid", deptid);
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				map.put("buttonname", qxname);
			}
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);
			}
			List<JSONObject> list = sysDeptPrivService.findPersonByDeptId(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
