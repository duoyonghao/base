/**  
  *
  * @Title:  KQDS_LabelAct.java   
  * @Package com.kqds.controller.base.label   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 上午11:03:41   
  * @version V1.0  
  */ 
package com.kqds.controller.base.label;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.sql.visitor.functions.If;
import com.hudh.util.HUDHUtil;
import com.kqds.entity.base.KqdsCostorderPriceList;
import com.kqds.entity.base.KqdsLabel;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.label.KQDS_hz_LabellPatientLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.other.CacheUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  KQDS_LabelAct   
  * @Description:TODO(标签库操作)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 上午11:03:41   
  *      
  */
@Controller
@RequestMapping("/KQDS_LabelAct")
public class KQDS_LabelAct {
	/**
	 * 注意记得加操作日志
	 */
	private static Logger logger = LoggerFactory.getLogger(KQDS_LabelAct.class);
	@Autowired
	private KQDS_hz_labelLogic logic;
	@Autowired
	private KQDS_hz_LabellPatientLogic patientLogic;

	/**
	  * @Title: toUserManagerJq   
	  * @Description: TODO(标签管理)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年8月20日 下午1:47:48
	 */
	@RequestMapping(value = "/toLabelManagement.act")
	public ModelAndView toLabelManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/label/labelManagement.jsp");
		return mv;
	}
	/**
	  * @Title: toUserManagerJq   
	  * @Description: TODO(标签新增)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年8月20日 下午1:47:48
	 */
	@RequestMapping(value = "/toLabelAdd.act")
	public ModelAndView toLabelAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/label/labelAdd.jsp");
		return mv;
	}
	/**
	  * @Title: toUserManagerJq   
	  * @Description: TODO(标签更新)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年8月20日 下午1:47:48
	 */
	@RequestMapping(value = "/toLabelUpdate.act")
	public ModelAndView toLabelUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/label/labelUpdate.jsp");
		return mv;
	}
	
	/**
	  * @Title: saveLabel   
	  * @Description: TODO(保存患者印象标签)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年8月14日 上午11:06:52
	 */
	@RequestMapping("/saveLabel.act")
	public String saveLabel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String leveLabel = request.getParameter("leveLabel");
			String parentId = request.getParameter("parentId");
			String parentName = request.getParameter("parentName");
			String createTime = request.getParameter("createTime");
			String createUser = request.getParameter("createUser");
			String status=request.getParameter("status");
			String remark = request.getParameter("remark");
			
			KqdsLabel label = new KqdsLabel();
			
			label.setSeqId(YZUtility.getUUID());
			label.setLeveLabel(leveLabel);
			if(!parentId.equals("") && parentId!=null){
				label.setParentId(parentId);
			}else{
				label.setParentId("1");
			}
			if(parentName!=null && !parentName.equals("")){				
				label.setParentName(parentName);
			}else{
				label.setParentName("");
			}
			if(status!=null &&!status.equals("")){
				label.setStatus("1");
			}else{
				label.setStatus("0");
			}
			label.setCreateTime(createTime);	
			label.setCreateUser(createUser);
			label.setRemark(remark);		
			Integer saveLabel = logic.saveLabel(label);
			if(saveLabel>0 && !saveLabel.equals(null)){	
				logger.info("添加成功！");
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;		
	}
	/**
	  * @Title: fingLabel   
	  * @Description: TODO(查询筛选条件)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年8月26日 下午6:48:10
	 */
	@RequestMapping("/findLabelCondition.act")
	public String fingLabelCondition(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String parentId = request.getParameter("code");
		try {
			JSONObject json = new JSONObject();
			List<KqdsLabel> labelList = null;
			String[] labelIds = parentId.split(",");
			List<KqdsLabel> jsonList = new ArrayList<>();
			for (int i = 0; i < labelIds.length; i++) {
				if(labelIds[i] != null && !labelIds[i].equals("")){
					labelList = logic.findLabelCondition(labelIds[i]);
					for (KqdsLabel kqdsLabel : labelList) {
						jsonList.add(kqdsLabel);
					}
				}
			}
			json.put("jsonList", jsonList);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: fingLabel   
	  * @Description: TODO(查询患者印象标签)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年8月14日 下午5:18:31
	 */
	@RequestMapping("/findLabel.act")
	public String findLabelByParentId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String parentId = request.getParameter("parentId");
		
		try {
			List<KqdsLabel> labelList = null;
			
			if(parentId.equals(null) && parentId.equals("")){
				labelList = logic.findLabel("1");
			}else{
				labelList = logic.findLabel(parentId);
			}
			YZUtility.RETURN_LIST(labelList, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: deLabel   
	  * @Description: TODO(删除标签)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年8月15日 上午11:04:31
	 */
	@RequestMapping("/deLabel.act")
	public String deLabel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] seqId = request.getParameterValues("seqId");
		
		try {
			Integer deLabel = logic.deLabel(seqId);
			if(deLabel>0 && !deLabel.equals(null)){
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}else{
				YZUtility.DEAL_SUCCESS(null, "操作失败!", response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR("操作失败！", false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: updateLabel   
	  * @Description: TODO(修改标签)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年8月15日 上午11:14:31
	 */
	@RequestMapping("/updateLabel.act")
	public String updateLabel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String seqId = request.getParameter("seqId");
		String leveLabel = request.getParameter("leveLabel");
		String updateTime = request.getParameter("createTime");
		String remark = request.getParameter("remark");
		
		KqdsLabel kLabel = new KqdsLabel();
		
		if(!seqId.equals("") && !seqId.equals(null)){
			kLabel.setSeqId(seqId);
		}
		if(!leveLabel.equals("") && !leveLabel.equals(null)){
				kLabel.setLeveLabel(leveLabel);	
				}
		if(!updateTime.equals("") && !updateTime.equals(null)){
				kLabel.setUpdateTime(updateTime);
		}
		if(!remark.equals("") && !remark.equals(null)){
			kLabel.setRemark(remark);
	}
		
		try {
			Integer label = logic.updateLabel(kLabel);
			if(label>0){
				YZUtility.DEAL_SUCCESS(null, "操作成功!", response, logger);	
			}else{
			YZUtility.DEAL_SUCCESS(null, "操作失败!", response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: fingLabel   
	  * @Description: TODO(查询患者印象标签)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年8月14日 下午5:18:31
	 */
	@SuppressWarnings("null")
	@RequestMapping("/findLabelAll.act")
	public String fingLabelAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String labelname = request.getParameter("labelname");
		String firstLabelSelect = request.getParameter("firstLabelSelect");
		String secondLabelSelect = request.getParameter("secondLabelSelect");
		try {
				List<JSONObject> labelList = null;				
				
				// 导出参数
				String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
				String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
				String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
				Map<String,String> map = new HashMap<String, String>();
				
				String parentId = null;
				if(firstLabelSelect!=null && !firstLabelSelect.equals("") && secondLabelSelect!=null && !secondLabelSelect.equals("")){
					parentId = secondLabelSelect;
				}else{
					parentId = firstLabelSelect;
				}
				
				if(parentId == null || parentId.equals("") ){
					parentId = "1";
				}
				
				if(parentId!=null && !parentId.equals("")){
					map.put("parentId", parentId);
				}
				if(labelname!=null && !labelname.equals("")){					
					map.put("leveLabel", labelname);
				}
				/*-------导出excel---------*/
				if (flag != null && flag.equals("exportTable")) {
					labelList = logic.findLabelAll();
					if (labelList != null) {
						ExportBean bean = ExportTable.initExcel4RsWrite("标签管理", fieldArr, fieldnameArr, response, request);
						bean = ExportTable.exportBootStrapTable2ExcelByResult(bean,labelList, "labelManagememt");
						ExportTable.writeExcel4DownLoad("标签管理", bean.getWorkbook(), response);
					}
					return null;
				}
				if(map.isEmpty()){
					labelList = logic.findLabelAll();
				}else{
					labelList = logic.fingLabelByCondition(map);
				}
			
			JSONObject json = new JSONObject();
			json.put("total", labelList.size());
			json.put("rows", labelList);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 价目表查找标签
	 * 2019-08-23 lwg
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value="/findHzLabel.act")
	public String findHzLabel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			YZPerson person = SessionUtil.getLoginPerson(request);
			String priveListDetails = request.getParameter("paramDetail");
			String nodeLists=request.getParameter("nodeList");
			String status=request.getParameter("status");
			String button=request.getParameter("button");
			String parentId=request.getParameter("parentId");
			String parentName=request.getParameter("parentName");
			List<KqdsCostorderPriceList> list1=null;
			String priveListDetail="";
			if(status!=null&&status.equals("2")){
				//套餐标签
				priveListDetail = java.net.URLDecoder.decode(priveListDetails, "UTF-8");
				list1 = HUDHUtil.parseJsonToObjectList(priveListDetail,KqdsCostorderPriceList.class);
			}
			//添加费用明细数据
			nodeLists = java.net.URLDecoder.decode(nodeLists, "UTF-8");
			List<KqdsCostorderPriceList> list = HUDHUtil.parseJsonToObjectList(nodeLists,KqdsCostorderPriceList.class);
			String node="";
			if(!YZUtility.isNullorEmpty(button)){
				node="全口";
			}else{
				for (KqdsCostorderPriceList kqdsCostorderPriceList : list) {
					if(status!=null&&status.equals("2")){
						if(kqdsCostorderPriceList.getNodename().substring(0,2).equals("UN")){
							node="UNIC数字化种植";
							break;
						}
						if(kqdsCostorderPriceList.getNodename().substring(0,2).equals("半口")){
							if(node.equals("")){
								node="半口";
							}else if(node.startsWith("颗数")&&!node.equals("")){
								node="半口"+"+"+node;
							}
						}
						if(kqdsCostorderPriceList.getNodename().substring(0,2).equals("局部")){
							int i=0;
							for (KqdsCostorderPriceList kqdsCostorderPriceList1 : list1) {
								if(kqdsCostorderPriceList1.getNodename().substring(0,2).equals("局部")){
									if(kqdsCostorderPriceList1.getUnit().equals("颗")){
										i+=Integer.parseInt(kqdsCostorderPriceList1.getNum());
									}
								}
							}
							if(node.equals("")){
								node="颗数("+i+"颗)";
							}else if(node.startsWith("半口")&&!node.equals("")){
								node="半口"+"+"+"颗数("+i+"颗)";
							}
						}
					}else{
						int a = node.indexOf(kqdsCostorderPriceList.getNodename().substring(0,2));
						if(a==-1){
							if(node.equals("")){
								node=kqdsCostorderPriceList.getNodename().substring(0,2);
							}else{
								node=node+"+"+kqdsCostorderPriceList.getNodename().substring(0,2);
							}
						}
					}
				}
			}
			Map<String,String> map1= new HashMap<String,String>();
			if(!parentId.equals("")&&parentId!=null){
				map1.put("parentId", parentId);
			}
			if(!node.equals("")&&node!=null){
				map1.put("leveLabel", node);
			}
			//查询标签数据
			String seqid = logic.selectKqdsHzLabelByLeveLabel(map1);
			if(seqid==null||seqid.equals("")){
				//新增标签
				KqdsLabel kqdsHzLabel = new KqdsLabel();
				seqid=YZUtility.getUUID();
				kqdsHzLabel.setSeqId(seqid);
				kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
				kqdsHzLabel.setCreateUser(person.getSeqId());
				kqdsHzLabel.setLeveLabel(node);
				kqdsHzLabel.setParentId("");
				kqdsHzLabel.setParentName(parentName);
				kqdsHzLabel.setRemark("三级");
				logic.insertKqdsHzLabel(kqdsHzLabel);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			//费用明细数据
			priveListDetail = java.net.URLDecoder.decode(priveListDetails, "UTF-8");
			list1 = HUDHUtil.parseJsonToObjectList(priveListDetail,KqdsCostorderPriceList.class);
			JSONArray jsonArray = JSONArray.fromObject(list1);
			//JSONObject json_test = JSONObject.fromObject(list1); 
			map.put("priveListDetails", jsonArray);
			//标签id
			map.put("seqid", seqid);
			//标签名称
			map.put("leveLabel", node);
			//状态码
			map.put("status", status);
			JSONObject json = JSONObject.fromObject( map );
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		}catch(Exception ex){
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 2019-08-23 lwg
	 * 根据患者编号查询
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value="/findHzLabelByUsercode.act")
	public JSONObject findHzLabelByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String usercode=request.getParameter("usercode");
		Map<String, String> map = new HashMap<String,String>();
		map.put("usercode", usercode);
		map.put("status", "3");
		//查询消费项目
		KqdsLabel kqdsHzLabel1=logic.selectKqdsHzLabelByUsercode(map);
		map.put("status", "4");
		//查询消费金额区间
		KqdsLabel kqdsHzLabel2=logic.selectKqdsHzLabelByUsercode(map);
		map.put("status", "1");
		//查询可开发空间
		KqdsLabel kqdsHzLabel3=logic.selectKqdsHzLabelByUsercode(map);
		map.put("status", "2");
		//查询可开发项目
		KqdsLabel kqdsHzLabel4=logic.selectKqdsHzLabelByUsercode(map);
		JSONObject json = new JSONObject();
		if(kqdsHzLabel1!=null){
			String xfxmname=kqdsHzLabel1.getLeveLabel();
			json.put("xfxmname", xfxmname);
			json.put("xfxmstatus", 3);
		}
		if(kqdsHzLabel2!=null){
			String xfjename=kqdsHzLabel2.getLeveLabel();
			json.put("xfjename", xfjename);
			json.put("xfjestatus", 4);
		}
		if(kqdsHzLabel3!=null){
			String kfxmname=kqdsHzLabel3.getLeveLabel();
			String kfxmid=kqdsHzLabel3.getSeqId();
			json.put("kfxmname", kfxmname);
			json.put("kfxmid", kfxmid);
			json.put("kfxmstatus", 1);
		}
		if(kqdsHzLabel4!=null){
			String kfkjname=kqdsHzLabel4.getLeveLabel();
			String kfkjid=kqdsHzLabel4.getSeqId();
			json.put("kfkjname", kfkjname);
			json.put("kfkjid", kfkjid);
			json.put("kfkjstatus", 2);
		}
		return json;
	}
	
	/**
	 * 初始化查询二级标签菜单
	  * @Title: findParentId   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年8月28日 下午6:06:05
	 */
	@RequestMapping(value = "/findParentId.act")
	public String findParentId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentId = request.getParameter("parentId");
		List<KqdsLabel> list = logic.findLabelCondition(parentId);
//		String[] parentId = request.getParameterValues("");
		JSONObject jsonObject = new JSONObject();
		try {
			JSONObject listJson = new JSONObject();
			for (int i = 0; i< list.size();i++) {
				listJson = logic.findParentId(list.get(i).getSeqId());
				jsonObject.put("list"+i+"", listJson);
			}
			//System.out.println("查到的数据="+jsonObject);
			YZUtility.DEAL_SUCCESS(jsonObject, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	//查询所有数据遍历添加到redis
	@RequestMapping(value = "/setLabel.act")
	public String setLabel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String time = request.getParameter("time");
		Map<String,String> map = new HashMap<String,String>();
		try {
			if(time!=null&&!time.equals("")){	
				map.put("starttime", time + ConstUtil.TIME_START);
				map.put("endtime", time + ConstUtil.TIME_END);
				List<JSONObject> list = patientLogic.findLabelByCreatetime(map);
				if(list!=null&&list.size()>0){
					String usercodes="";
					String usercode="";
					for (JSONObject json : list) {
						if(!usercodes.contains(json.getString("userid"))){
							String labelID="";
							String labelName1="";
							String labelName2="";
							String labelName3="";
							long time1=0;
							if(usercodes.equals("")){
								usercodes=json.getString("userid");							
							}else{
								usercodes=usercodes+","+json.getString("userid");
							}
							usercode=json.getString("userid");	
							for (JSONObject json1 : list) {
								//患者编号相同 添加标签id到字段
								if(usercode.equals(json1.getString("userid"))){
									String createtime=json1.getString("time");
									SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									Date date = format.parse(createtime);  
									//日期转时间戳（毫秒）
									time1=date.getTime();
									if(labelID.equals("")){
										if(!YZUtility.isNullorEmpty(json1.getString("askperson"))){
											labelID=usercode+","+json1.getString("labeloneid")+","+json1.getString("labeltwoid")
											+","+json1.getString("labelthreeid")+","+json1.getString("askperson")+",";
										}else{
											labelID=usercode+","+json1.getString("labeloneid")+","+json1.getString("labeltwoid")
											+","+json1.getString("labelthreeid")+",";
										}
									}else{
										if(labelID.contains(json1.getString("labeloneid"))){
											if(labelID.contains(json1.getString("labeltwoid"))){
												if(!labelID.contains(json1.getString("labelthreeid"))){
													labelID=labelID+json1.getString("labelthreeid")+",";
												}
											}else{
												if(!labelID.contains(json1.getString("labelthreeid"))){
													labelID=labelID+json1.getString("labeltwoid")+","+json1.getString("labelthreeid")+",";
												}
											}
										}else{
												labelID=labelID+json1.getString("labeloneid")+","+json1.getString("labeltwoid")+","+json1.getString("labelthreeid")+",";
										}
									}
									
									if(labelName1.equals("")){
										labelName1=json1.getString("labelonename")+":"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
									}else{
										if(labelName1.contains(json1.getString("labelonename"))){
											if(labelName1.contains(json1.getString("labeltwoname"))){
												labelName1+=","+json1.getString("labelthreename");
											}else{
												labelName1+=";"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
											}
										}else{
											if(labelName2.equals("")){
												labelName2=json1.getString("labelonename")+":"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
											}else{
												if(labelName2.contains(json1.getString("labelonename"))){
													if(labelName2.contains(json1.getString("labeltwoname"))){
														labelName2+=","+json1.getString("labelthreename");
													}else{
														labelName2+=";"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
													}
												}else{
													if(labelName3.equals("")){
														labelName3=json1.getString("labelonename")+":"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
													}else{
														if(labelName3.contains(json1.getString("labelonename"))){
															if(labelName3.contains(json1.getString("labeltwoname"))){
																labelName3+=","+json1.getString("labelthreename");
															}else{
																labelName3+=";"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
															}
														}
													}
												}
											}
										}
									}
								}
							}
							String labelName="";
							if(!labelName1.equals("")){
								labelName+=labelName1+"。";
							}
							if(!labelName2.equals("")){
								labelName+=labelName2+"。";
							}
							if(!labelName3.equals("")){
								labelName+=labelName3+"。";
							}
							//添加标签到缓存
							CacheUtil.openCache();
							
							Map<String,String> key=new HashMap<String,String>();
							key.put(labelID, usercode);
							Map<String,String> key1=new HashMap<String,String>();
							key1.put(usercode,labelName);
							String organization=ChainUtil.getCurrentOrganization(request);
							if(organization.equals("HUDH")){
								CacheUtil.setMap("label:value", key);
								CacheUtil.setMap("label:name", key1);
								CacheUtil.addZSet("label:key",labelID,Double.parseDouble(time1+""));
								CacheUtil.set("labelQuery:"+labelID, usercode);
							}else{
								CacheUtil.setMap(organization+":label:value", key);
								CacheUtil.setMap(organization+":label:name", key1);
								CacheUtil.addZSet(organization+":label:key",labelID,Double.parseDouble(time1+""));
								CacheUtil.set(organization+":labelQuery:"+labelID, usercode);
							}
							
							CacheUtil.close();
						}
					}
				}
			}
			YZUtility.DEAL_SUCCESS(null, "成功", response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		
		return null;
	}
	@RequestMapping(value = "/toAddLabel.act")
	public ModelAndView toAddLabel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/kfzx/add_label.jsp");
		return mv;
	} 
}
