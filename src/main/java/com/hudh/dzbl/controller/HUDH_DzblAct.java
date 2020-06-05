package com.hudh.dzbl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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

import com.hudh.dzbl.entity.DzblDetail;
import com.hudh.dzbl.entity.DzblTemplate;
import com.hudh.dzbl.service.IDzblTemplateService;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/HUDH_DzblAct")
public class HUDH_DzblAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_DzblAct.class);
	/**
	 * 模板操作接口
	 */
	@Autowired
	private IDzblTemplateService dzblTemplateService;
	/**
	 * 新增模板
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/saveBlTemple.act")
	public void saveBlTemple(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String title = request.getParameter("title"); //标题
		String blfl = request.getParameter("blfl"); //病历分类
		String bc = request.getParameter("bc"); //病程
		String sstype = request.getParameter("sstype"); //手术类型
		String templeDetail = request.getParameter("templeDetail"); //模板详情
		String organization = ChainUtil.getCurrentOrganization(request);
		DzblTemplate dzblTemplate = new DzblTemplate();
		dzblTemplate.setTitle(title);
		dzblTemplate.setBlfl(blfl);
		dzblTemplate.setBc(bc);
		dzblTemplate.setOrganization(organization);
		dzblTemplate.setDetail(templeDetail);
		dzblTemplate.setSstype(sstype);
		try {
			//保存模板数据
			dzblTemplateService.insertDzblTemplate(dzblTemplate);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 根据id获取病例模板
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/findDzblTemplateById.act")
	public void findDzblTemplateById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String blId = request.getParameter("blId"); //病历分类
		try {
			//获取模板详情
			JSONObject jo =  dzblTemplateService.findDzblTemplateById(blId);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 根据病程分类和病程查找对应的模板
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/findTemplateByBlflAndBc.act")
	public void findTemplateByBlflAndBc(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String blfl = request.getParameter("blfl"); //病历分类
		String bc = request.getParameter("bc"); //病程
		String sstype = request.getParameter("sstype"); //手术分类
		String organization = ChainUtil.getCurrentOrganization(request); //门诊
		try {
			//保存模板数据
			JSONObject jo =  dzblTemplateService.findTemplateByBlflAndBc(blfl, bc,sstype,organization);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 根据病例分类回去已存的病程列表
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/findBcByBlfl.act")
	public void findBcByBlfl(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String blfl = request.getParameter("blfl"); //病历分类
		String sstype = request.getParameter("sstype"); //病历分类
		try {
			//保存模板数据
			List<JSONObject> list = dzblTemplateService.findBcByBlfl(blfl,sstype);
			JSONObject jo = new JSONObject();
			jo.put("list", list);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 新增或暂存病例
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/insertDzblDetail.act")
	public String insertDzblDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String blfl = request.getParameter("blfl"); //病历分类
		String bc = request.getParameter("bc"); //病程
		String templeDetail = request.getParameter("templeDetail"); //模板详情
		String type  = request.getParameter("type"); //0：暂存 1:生效  
		String blcode  = request.getParameter("blcode"); //病历号
		String sstype  = request.getParameter("sstype"); //病历号	
		String organization = ChainUtil.getCurrentOrganization(request);
		DzblDetail dzblDetail = new DzblDetail();
		String id = request.getParameter("id"); 
		dzblDetail.setBlfl(blfl);
		dzblDetail.setBc(bc);
		dzblDetail.setSstype(sstype);
		dzblDetail.setDetail(templeDetail);
		dzblDetail.setOrganization(organization);
		if(type.equals("1")) {
			dzblDetail.setStatus("1"); //新增生效状态
		}else {
			dzblDetail.setStatus("0"); //新增暂存状态
		}
		
		try {
			if(YZUtility.isNotNullOrEmpty(id)) { //更新
				dzblDetail.setId(id);
				dzblTemplateService.updateDzblById(dzblDetail,request);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_KEFU, dzblDetail,id, TableNameUtil.KQDS_CHANGE_KEFU, request);
			}else { //保存
				dzblDetail.setBlcode(blcode);
				//获取标题
				JSONObject jo =  dzblTemplateService.findTemplateByBlflAndBc(blfl, bc,sstype,organization);
				if(null != jo) {
					dzblDetail.setTitle((String)jo.get("title"));
					//保存模板数据
					dzblTemplateService.insertDzblDetail(dzblDetail,request);
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_KEFU, dzblDetail, (String)jo.get("title"), TableNameUtil.KQDS_CHANGE_KEFU, request);
				}
			}
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据病历号查询患者病历列表
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/findDzblByBlcode.act")
	public void findDzblByBlcode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String blCode = request.getParameter("blCode"); //病历分类
		try {
			List<JSONObject> list  = null;
			if(YZUtility.isNotNullOrEmpty(blCode)) {
				list = dzblTemplateService.findDzblByBlcode(blCode);
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 获取打印预览页展示的信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/findPrintInfo.act")
	public void findPrintInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String blId = request.getParameter("blId"); //病例Id
		String blCode = request.getParameter("blCode"); //病历分类
		try {
			JSONObject userDoc = null;
			JSONObject dzblInfo = null;
			if(YZUtility.isNotNullOrEmpty(blCode) && YZUtility.isNotNullOrEmpty(blId)) {
				userDoc = dzblTemplateService.findUserDocByBlCode(blCode); //患者详情
				dzblInfo = dzblTemplateService.findDzblById(blId); //病历详情
				//获取详情处理
				String detail = dzblInfo.getString("detail");
				if(YZUtility.isNotNullOrEmpty(detail)) {
					detail = detail.replace("&nbsp;", "").replace("<u>", "").replace("</u>", "");
					dzblInfo.put("detail", detail);
				}
			}
			JSONObject jo = new JSONObject();
			jo.put("userDoc", userDoc);
			jo.put("dzblInfo", dzblInfo);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	
	/**
	 * 电子病历生成pdf返回前台
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/dzblPrint.act")
	public String dzblPrint(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String blId = request.getParameter("blId");
		String blCode = request.getParameter("blCode");
		String ssTime = request.getParameter("ssTime");
		response.setContentType("application/pdf");
		try {
			String filePath = dzblTemplateService.dzblPrint(request,blId,blCode,ssTime);

			File f = new File(filePath);

			FileInputStream in = new FileInputStream(f);
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024 * 8];
			while ((in.read(b)) != -1) {
				out.write(b);
			}
			out.flush();
			in.close();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	
	/**
	 * 判断当前登录人是否有权限修改电子病历
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/selectHasDzblOptAuth.act")
	public void selectHasDzblOptAuth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		try {
			JSONObject jo  = new JSONObject();
			String isOk = dzblTemplateService.selectParaValueByName(request);
			jo.put("isOk", isOk);
			YZUtility.RETURN_OBJ(jo, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	  * @Title: findDzbl   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2020年4月10日 下午3:40:12
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findDzbl.act")
	public String findDzbl(HttpServletRequest request, HttpServletResponse response) throws Exception{
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String searchValue = request.getParameter("searchValue");
		try{
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			// 可见人员过滤
			String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaffYyrl).toString();
			map.put("querytype", visualstaff);
			if(starttime != null && !starttime.equals("")){
				map.put("starttime", starttime);
					}
			if(starttime != null && !starttime.equals("")){
				map.put("endtime", endtime);			
						}
			if(starttime != null && !starttime.equals("")){
				map.put("searchValue", searchValue);
					}
			JSONObject findDzbl = dzblTemplateService.findDzbl(map, bp);
			YZUtility.DEAL_SUCCESS(findDzbl, null, response, logger);
		}catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
