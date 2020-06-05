package com.kqds.controller.base.visit;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.base.KqdsVisitSet;
import com.kqds.entity.base.VisitDataCount;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.visit.KQDS_VisitLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.UserPrivUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_VisitAct")
public class KQDS_VisitAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_VisitAct.class);
	@Autowired
	private KQDS_VisitLogic logic;
	@Autowired
	private KQDS_UserDocumentLogic userLogic;
	@RequestMapping(value = "/toVisitWin.act")
	public ModelAndView toVisitWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/visit/visitWin.jsp");
		return mv;
	}

	@RequestMapping(value = "/toVisitAdd.act")
	public ModelAndView toVisitAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String lytype = request.getParameter("lytype");
		String type = request.getParameter("type");
		String regno = request.getParameter("regno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("lytype", lytype);
		mv.addObject("type", type);
		mv.addObject("regno", regno);
		mv.setViewName("/kqdsFront/visit/visit_add.jsp");
		return mv;
	}
	

	@RequestMapping(value = "/toVisitPlanAdd.act")
	public ModelAndView toVisitPlanAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String lytype = request.getParameter("lytype");
		String type = request.getParameter("type");
		String regno = request.getParameter("regno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("lytype", lytype);
		mv.addObject("type", type);
		mv.addObject("regno", regno);
		mv.setViewName("/kqdsFront/visit/visit_plan_add.jsp");
		return mv;
	}
	@RequestMapping(value = "/toVisitPlansAdd.act")
	public ModelAndView toVisitPlansAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercodes = request.getParameter("usercodes");
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercodes", usercodes);
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/visit/visit_plans_add.jsp");
		return mv;
	}
	/**
	  * @Title: toVisitAddf   
	  * @Description: TODO(客户管理-回访管理)   lutian 
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年11月5日 
	 */
	@RequestMapping(value = "/toVisitPlanManage.act")
	public ModelAndView toVisitPlanManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/visit/visit_manage.jsp");
		return mv;
	}
	/**
	  * @Title: toVisitAddf   
	  * @Description: TODO(客户管理-添加回访)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年6月24日 上午10:50:59
	 */
	@RequestMapping(value = "/toVisitAddf.act")
	public ModelAndView toVisitAddf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String lytype = request.getParameter("lytype");
		String type = request.getParameter("type");
		String regno = request.getParameter("regno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("lytype", lytype);
		mv.addObject("type", type);
		mv.addObject("regno", regno);
		mv.setViewName("/kqdsFront/visit/visit_addf.jsp");
		return mv;
	}

	@RequestMapping(value = "/toVisitDetail.act")
	public ModelAndView toVisitDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/visit/visit_detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toVisitEdit.act")
	public ModelAndView toVisitEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/visit/visit_edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toVisitPost.act")
	public ModelAndView toVisitPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/visit/visit_post.jsp");
		return mv;
	}

	@RequestMapping(value = "/toVisitList.act")
	public ModelAndView toVisitList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/index/center/visit_list.jsp");
		return mv;
	}
	@RequestMapping(value = "/toVisitList3.act")
	public ModelAndView toVisitList3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String visittime = request.getParameter("visittime");
		String visitor = request.getParameter("visitor");
		ModelAndView mv = new ModelAndView();
		mv.addObject("visitor", visitor);
		mv.addObject("visittime", visittime);
		mv.setViewName("/kqdsFront/visit/visit_list2.jsp");
		return mv;
	}
	@RequestMapping(value = "/toVisitList2.act")
	public ModelAndView toVisitList2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String is_Wd_oper = request.getParameter("is_Wd_oper");
		ModelAndView mv = new ModelAndView();
		mv.addObject("is_Wd_oper", is_Wd_oper);
		mv.setViewName("/kqdsFront/visit/visit_list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toUserVisit.act")
	public ModelAndView toUserVisit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/visit/user_visit.jsp");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			KqdsVisit visit = (KqdsVisit) logic.loadObjSingleUUID(TableNameUtil.KQDS_VISIT, seqId);
			if (visit == null) {
				throw new Exception("回访记录不存在");
			}

			String visitTime = visit.getVisittime();
			if (YZUtility.isNullorEmpty(visitTime)) {
				throw new Exception("回访时间值为空");
			}

			if (visitTime.length() == 16) {
				visitTime = visitTime + ":00";
			}

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//修改回访时间格式
			Date vistDate = df.parse(visitTime);
			Date currDate = new Date();
			if (currDate.getTime() >= vistDate.getTime()) { // 回访时间已过
				throw new Exception("回访时间已过，不能删除");
			}

			logic.deleteSingleUUID(TableNameUtil.KQDS_VISIT, seqId);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_VISIT, visit, visit.getUsercode(), TableNameUtil.KQDS_VISIT, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 入库，修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			KqdsVisit dp = new KqdsVisit();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			String usercode = request.getParameter("usercode");//二次取值，避免后台拿不到值问题
			String username = request.getParameter("username");
			String sex = request.getParameter("sex");
			String telphone = request.getParameter("telphone");
			if (!YZUtility.isNullorEmpty(dp.getVisittime())) {
				if (dp.getVisittime().length() != 10) {
					throw new Exception("回访时间格式不正确，请确保长度为10位，即精确到天钟。");
				}

				if (dp.getVisittime().contains("：")) {
					throw new Exception("回访时间格式不正确，不允许包含中文分号。");
				}

//				String longFormat = dp.getVisittime() + ":00";
//				if (!YZUtility.valiDateTimeWithLongFormat(longFormat)) {
//					throw new Exception("回访时间不正确，请确保日期存在，如2月29日、4月31日。");
//				}
			}

			if (!YZUtility.isNullorEmpty(seqId)) {
				String myd = request.getParameter("myd");
				if (!YZUtility.isNullorEmpty(myd)) {
					dp.setStatus(ConstUtil.VISIT_STATUS_1);// 已回访
					dp.setFinishtime(YZUtility.getCurDateTimeStr());// 回访结果时间
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.SUBMIT, BcjlUtil.KQDS_VISIT, dp, dp.getUsercode(), TableNameUtil.KQDS_VISIT, request);
				} else {
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_VISIT, dp, dp.getUsercode(), TableNameUtil.KQDS_VISIT, request);
					// 添加消息提示
					PushUtil.saveTx4ModifyVisit(dp, request);
				}

				logic.updateSingleUUID(TableNameUtil.KQDS_VISIT, dp);
			} else {
				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // ###
				dp.setUsercode(usercode);//二次赋值，避免后台拿不到值问题
				dp.setUsername(username);
				dp.setSex(sex);
				dp.setTelphone(telphone);
				// 检查是否存在 回访设置
				// 查询回访人员的角色
				YZPerson visitor = (YZPerson) logic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, dp.getVisitor());
				Map<String, String> map = new HashMap<String, String>();
				map.put("hffl", dp.getHffl());
				map.put("userpriv", visitor.getUserPriv());
				map.put("organization", ChainUtil.getCurrentOrganization(request));
				List<KqdsVisitSet> visitSetList = (List<KqdsVisitSet>) logic.loadList(TableNameUtil.KQDS_VISIT_SET, map);
				if (visitSetList != null && visitSetList.size() > 0) {
					dp.setIsfirstday(ConstUtil.VISIT_IS_FIRST_1);
				}
				logic.saveSingleUUID(TableNameUtil.KQDS_VISIT, dp);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, dp, dp.getUsercode(), TableNameUtil.KQDS_VISIT, request);
				// 添加消息提示
				PushUtil.saveTx4NewVisit(dp, request);
				if (visitSetList != null && visitSetList.size() > 0) {
					addAutovisit(dp, visitSetList, person, request);
				}

			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/insertOrUpdate1.act")
	public String insertOrUpdate1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String usercode =request.getParameter("usercode");
			String username =request.getParameter("username");
			String sex =request.getParameter("sex");
			String telphone =request.getParameter("telphone");
			String visitor =request.getParameter("visitor");
//			String bigPlanName =request.getParameter("bigPlanName");
//			String bigPlanExplan =request.getParameter("bigPlanExplan");
			String smallList =request.getParameter("smallList");
			JSONArray listArray=JSONArray.fromObject(smallList);
			List<JSONObject> list2 = JSONArray.toList(listArray, new JSONObject(), new JsonConfig());
			List<KqdsVisit> visitList=new ArrayList<KqdsVisit>();
			for (JSONObject jsonObject : list2) {
				KqdsVisit dp = new KqdsVisit();
				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request));
				if(!YZUtility.isNullorEmpty(sex)){
					dp.setSex(sex);;
				}
				if(!YZUtility.isNullorEmpty(usercode)){
					dp.setUsercode(usercode);;
				}
				if(!YZUtility.isNullorEmpty(username)){
					dp.setUsername(username);;
				}
				if(!YZUtility.isNullorEmpty(telphone)){
					dp.setTelphone(telphone);
				}
				if(!YZUtility.isNullorEmpty(visitor)){
					dp.setVisitor(visitor);
				}
//				if(!YZUtility.isNullorEmpty(bigPlanName)){
//					dp.setVisittype("");
//				}
//				if(!YZUtility.isNullorEmpty(bigPlanExplan)){
//					dp.setVisittype("");
//				}
	            //大计划id
//				if(!YZUtility.isNullorEmpty(jsonObject.getString("managarId"))){
//					
//				}
				//小计划id
//				if(!YZUtility.isNullorEmpty(jsonObject.getString("SEQ_ID"))){
//					
//				}
				if(!YZUtility.isNullorEmpty(jsonObject.getString("plandays"))){
					if(!YZUtility.isNullorEmpty(jsonObject.getString("nowDate"))){
						//跟进天数
						int d=Integer.parseInt(jsonObject.getString("plandays"));
						String da = jsonObject.getString("nowDate");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date dateA = sdf.parse(da);
						dateA.setDate(dateA.getDate()+d);
						dp.setVisittime(sdf.format(dateA));
					}
				}
				//回访类型名称
				if(!YZUtility.isNullorEmpty(jsonObject.getString("visittype"))){
					dp.setVisittype(jsonObject.getString("visittype"));
				}
				//是否设为强制跟进
//				if(!YZUtility.isNullorEmpty(jsonObject.getString("ismandatory"))){
//					
//				}
				//受理类型
//				if(!YZUtility.isNullorEmpty(jsonObject.getString("accepttype"))){
//					dp.setAccepttype(jsonObject.getString("accepttype"));
//				}
				//受理类型id
				if(!YZUtility.isNullorEmpty(jsonObject.getString("accepttypeId"))){
					dp.setAccepttype(jsonObject.getString("accepttypeId"));
				}
				//回访类型id
				if(!YZUtility.isNullorEmpty(jsonObject.getString("visittypeId"))){
					dp.setHffl(jsonObject.getString("visittypeId"));
				}
				//回访要点
				if(!YZUtility.isNullorEmpty(jsonObject.getString("remark"))){
					dp.setHfyd(jsonObject.getString("remark"));
				}
				visitList.add(dp);
			}
//			if (!YZUtility.isNullorEmpty(dp.getVisittime())) {
//				if (dp.getVisittime().length() != 10) {
//					throw new Exception("回访时间格式不正确，请确保长度为10位，即精确到天钟。");
//				}
//
//				if (dp.getVisittime().contains("：")) {
//					throw new Exception("回访时间格式不正确，不允许包含中文分号。");
//				}

//				String longFormat = dp.getVisittime() + ":00";
//				if (!YZUtility.valiDateTimeWithLongFormat(longFormat)) {
//					throw new Exception("回访时间不正确，请确保日期存在，如2月29日、4月31日。");
//				}
//			}
//
			logic.saveList(visitList);
//				// 检查是否存在 回访设置
//				// 查询回访人员的角色
//				YZPerson visitor = (YZPerson) logic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, dp.getVisitor());
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("hffl", dp.getHffl());
//				map.put("userpriv", visitor.getUserPriv());
//				map.put("organization", ChainUtil.getCurrentOrganization(request));
//				List<KqdsVisitSet> visitSetList = (List<KqdsVisitSet>) logic.loadList(TableNameUtil.KQDS_VISIT_SET, map);
//				if (visitSetList != null && visitSetList.size() > 0) {
//					dp.setIsfirstday(ConstUtil.VISIT_IS_FIRST_1);
//				}
//				logic.saveSingleUUID(TableNameUtil.KQDS_VISIT, dp);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, usercode+username+sex+telphone+visitor+smallList, usercode, TableNameUtil.KQDS_VISIT, request);
//				// 添加消息提示
//				PushUtil.saveTx4NewVisit(dp, request);
//				if (visitSetList != null && visitSetList.size() > 0) {
//					addAutovisit(dp, visitSetList, person, request);
//				}
//
//			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/insertOrUpdate2.act")
	public String insertOrUpdate2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String usercode =request.getParameter("usercode");
			String visitor =request.getParameter("visitor");
			String smallList =request.getParameter("smallList");
			JSONArray listArray=JSONArray.fromObject(smallList);
			List<JSONObject> list2 = JSONArray.toList(listArray, new JSONObject(), new JsonConfig());
			List<KqdsVisit> visitList=new ArrayList<KqdsVisit>();
			String[] usercodes = usercode.split(",");
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < usercodes.length; i++) {
				if(i==0){
					str.append("\'"+usercodes[i]+"\'");
				}else{
					str.append(",\'"+usercodes[i]+"\'");
				}
			}
			//查找手机号，姓名，性别
			List<JSONObject> l = userLogic.findKqdsUserdocumentByUsercodes(str.toString());
			for (JSONObject json : l) {
				for (JSONObject jsonObject : list2) {
					KqdsVisit dp = new KqdsVisit();
					dp.setSeqId(YZUtility.getUUID());
					dp.setCreatetime(YZUtility.getCurDateTimeStr());
					dp.setCreateuser(person.getSeqId());
					dp.setOrganization(ChainUtil.getCurrentOrganization(request));
					if(!YZUtility.isNullorEmpty(json.getString("usercode"))){
						dp.setUsercode(json.getString("usercode"));
					}
					if(!YZUtility.isNullorEmpty(visitor)){
						dp.setVisitor(visitor);
					}
					if(!YZUtility.isNullorEmpty(json.getString("sex"))){
						dp.setSex(json.getString("sex"));;
					}
					if(!YZUtility.isNullorEmpty(json.getString("username"))){
						dp.setUsername(json.getString("username"));;
					}
					if(!YZUtility.isNullorEmpty(json.getString("phonenumber1"))){
						dp.setTelphone(json.getString("phonenumber1"));
					}
					if(!YZUtility.isNullorEmpty(jsonObject.getString("plandays"))){
						if(!YZUtility.isNullorEmpty(jsonObject.getString("nowDate"))){
							//跟进天数
							int d=Integer.parseInt(jsonObject.getString("plandays"));
							String da = jsonObject.getString("nowDate");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date dateA = sdf.parse(da);
							dateA.setDate(dateA.getDate()+d);
							dp.setVisittime(sdf.format(dateA));
						}
					}
					//回访类型名称
					if(!YZUtility.isNullorEmpty(jsonObject.getString("visittype"))){
						dp.setVisittype(jsonObject.getString("visittype"));
					}
					//受理类型id
					if(!YZUtility.isNullorEmpty(jsonObject.getString("accepttypeId"))){
						dp.setAccepttype(jsonObject.getString("accepttypeId"));
					}
					//回访类型id
					if(!YZUtility.isNullorEmpty(jsonObject.getString("visittypeId"))){
						dp.setHffl(jsonObject.getString("visittypeId"));
					}
					//回访要点
					if(!YZUtility.isNullorEmpty(jsonObject.getString("remark"))){
						dp.setHfyd(jsonObject.getString("remark"));
					}
					visitList.add(dp);
				}
			}
			logic.saveList(visitList);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, usercode+visitor+smallList, usercode, TableNameUtil.KQDS_VISIT, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 生成回访
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAutovisit.act")
	public void addAutovisit(KqdsVisit dp, List<KqdsVisitSet> visitSetList, YZPerson person, HttpServletRequest request) throws Exception {
		String firstVisittime = dp.getVisittime() + ":00";
		for (KqdsVisitSet vset : visitSetList) {
			KqdsVisit visit = dp;
			visit.setSeqId(YZUtility.getUUID());
			visit.setRemark(vset.getRemark());// 设置回访备注
			visit.setPurpose(vset.getPurpose());// 设置回访目的
			visit.setIsauto(ConstUtil.VISIT_IS_AUTO_1);// 设置自动生成标识
			visit.setHfyd(vset.getPurpose());
			// 设置回访时间
			Date visittime = YZUtility.getDayAfter(firstVisittime, vset.getJgday());
			String visittimeStr = YZUtility.getDateTimeStr(visittime);
			visit.setVisittime(visittimeStr.substring(0, visittimeStr.length() - 3));
			logic.saveSingleUUID(TableNameUtil.KQDS_VISIT, visit);

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, visit, visit.getUsercode(), TableNameUtil.KQDS_VISIT, request);
			// 添加消息提示
			PushUtil.saveTx4NewVisit(visit, request);
		}
	}

	/**
	 * 详情返回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			KqdsVisit en = (KqdsVisit) logic.loadObjSingleUUID(TableNameUtil.KQDS_VISIT, seqId);

			if (en == null) {
				throw new Exception("数据不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 不分页查询 【不支持跨院】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectListNotByPersonId.act")
	public String selectListNotByPersonId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			//患者编号
			String usercode = request.getParameter("usercode");
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			//满意度
			String myd = request.getParameter("myd");
			if (!YZUtility.isNullorEmpty(myd)) {
				map.put("myd", myd);
			}
			//回访时间
			String visittime = request.getParameter("visittime");
			if (!YZUtility.isNullorEmpty(visittime)) {
				map.put("visittime", visittime);
			}
			//患者名称
			String username = request.getParameter("username");
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			//回访人员
			String visitor = request.getParameter("visitor");
			if (!YZUtility.isNullorEmpty(visitor)) {
				map.put("visitor", visitor);
			}
			//回访分类
			String hffl = request.getParameter("hffl");
			if (!YZUtility.isNullorEmpty(hffl)) {
				map.put("hffl", hffl);
			}
			//回访状态
			String status = request.getParameter("status");
			if (!YZUtility.isNullorEmpty(status)) {
				map.put("status", status);
			}
			// 门诊条件过滤
			//map.put("organization", ChainUtil.getCurrentOrganization(request));

			List<JSONObject> list = logic.selectList(TableNameUtil.KQDS_VISIT, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	@RequestMapping(value = "/selectListByPersonIdAndTime.act")
	public String selectListByPersonIdAndTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			//患者编号
			String usercode = request.getParameter("usercode");
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			//满意度
			String myd = request.getParameter("myd");
			if (!YZUtility.isNullorEmpty(myd)) {
				map.put("myd", myd);
			}
			//回访时间
			String visittime = request.getParameter("visittime");
			if (!YZUtility.isNullorEmpty(visittime)) {
				map.put("visittime", visittime);
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				map.put("visittime",  df.format(new Date()));
			}
			//患者名称
			String username = request.getParameter("username");
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			//回访人员
			String visitor = request.getParameter("visitor");
			if (!YZUtility.isNullorEmpty(visitor)) {
				map.put("visitor", visitor);
			}
			//回访分类
			String hffl = request.getParameter("hffl");
			if (!YZUtility.isNullorEmpty(hffl)) {
				map.put("hffl", hffl);
			}
			//回访状态
			String status = request.getParameter("status");
			if (!YZUtility.isNullorEmpty(status)) {
				map.put("status", status);
			}
			// 门诊条件过滤
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
			}
			JSONObject list = logic.selectList(bp,TableNameUtil.KQDS_VISIT, map);
			//YZUtility.RETURN_LIST(list, response, logger);
			YZUtility.DEAL_SUCCESS(list, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	/**
	 * first_center.jsp 今日回访
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectListNotByPersonIdToday.act")
	public String selectListNotByPersonIdToday(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			YZPerson person = SessionUtil.getLoginPerson(request);

			String visualstaff = SessionUtil.getVisualstaff(request);
			if (person.getUserPriv().equals(UserPrivUtil.ADMIN_SEQ_ID)) {// 总经理看所有
				visualstaff = "";
			}
			// 初始化分页实体类
			@SuppressWarnings("rawtypes")
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String,String>();
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			JSONObject json = logic.selectListMyToday(TableNameUtil.KQDS_VISIT, visualstaff, ChainUtil.getCurrentOrganization(request),bp,map);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询各类条数 只在网电中心-回访中心的右侧被调用 查询每个回访分类对应的 回访数量
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCountByQuery.act")
	public String getCountByQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String vtime1 = request.getParameter("vtime1");
			String vtime2 = request.getParameter("vtime2");
			String name = request.getParameter("username");
			String ishuifang = request.getParameter("ishuifang");
			String hffl = request.getParameter("hffl");
			String renwu = request.getParameter("renwu");
			String visitor = request.getParameter("visitor");
			String visitdept = request.getParameter("visitdept");
			/** 回访人员所在部门 **/
			if (!YZUtility.isNullorEmpty(visitdept) && !visitdept.equals("null") && !visitdept.equals("undefined")) {
				map.put("visitdept", visitdept);
			}
			String myd = request.getParameter("myd");
			String phonenumber1 = request.getParameter("phonenumber1");
			if (!YZUtility.isNullorEmpty(vtime1) && !vtime1.equals("null") && !vtime1.equals("undefined")) {
				// 如果是精确到天的，则加上 时分
				if (vtime1.length() == 10) {
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
					Date date =sdf.parse(vtime1);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
			        calendar.add(Calendar.DATE, -1);    //得到前一天
			        String yestedayDate= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
					vtime1 = yestedayDate + ConstUtil.HOUR_START;
				}
				map.put("vtime1", vtime1);
			}
			if (!YZUtility.isNullorEmpty(vtime2) && !vtime2.equals("null") && !vtime2.equals("undefined")) {
				if (vtime2.length() == 10) {
					vtime2 = vtime2 + ConstUtil.HOUR_END;
				}
				map.put("vtime2", vtime2);
			}

			if (YZUtility.isNullorEmpty(vtime1) && YZUtility.isNullorEmpty(vtime2)) {
				String curDate = YZUtility.getDateStr(new Date());
				Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
		        calendar.add(Calendar.DATE, -1);    //得到前一天
		        String yestedayDate= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				map.put("vtime1", yestedayDate + ConstUtil.HOUR_START);
				map.put("vtime2", curDate + ConstUtil.HOUR_END);
			}

			if (!YZUtility.isNullorEmpty(name) && !name.equals("null") && !name.equals("undefined")) {
				map.put("name", name);
			}
			if (!YZUtility.isNullorEmpty(ishuifang) && !ishuifang.equals("null") && !ishuifang.equals("undefined")) {
				map.put("ishuifang", ishuifang);
			}
			if (!YZUtility.isNullorEmpty(hffl) && !hffl.equals("null") && !hffl.equals("undefined")) {
				map.put("hffl", hffl);
			}
			if (!YZUtility.isNullorEmpty(renwu) && !renwu.equals("null") && !renwu.equals("undefined")) {
				map.put("renwu", renwu);
			}
			if (!YZUtility.isNullorEmpty(visitor) && !visitor.equals("null") && !visitor.equals("undefined")) {
				map.put("visitor", visitor);
			}
			if (!YZUtility.isNullorEmpty(myd) && !myd.equals("null") && !myd.equals("undefined")) {
				map.put("myd", myd);
			}
			if (!YZUtility.isNullorEmpty(phonenumber1) && !myd.equals("null") && !myd.equals("undefined")) {
				map.put("phonenumber1", phonenumber1);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			// 可见人员id集合 (包含自己)
			String visualstaff = SessionUtil.getVisualstaff(request);

			JSONObject jobj = new JSONObject();
			// 总条数
			int total = logic.getCountByQuery(TableNameUtil.KQDS_VISIT, map, visualstaff, organization);
			jobj.put("total", total);
			// 分类条数
			List<VisitDataCount> groupCountList = logic.selectCountByQuery(TableNameUtil.KQDS_VISIT, map, visualstaff, organization);

			// ### 增加全部分类
			VisitDataCount all = new VisitDataCount();
			int allcount = 0;
			// 增加全部
			for (VisitDataCount dataCount : groupCountList) {
				allcount += Integer.parseInt(dataCount.getCount());
			}

			all.setCount(allcount + "");
			all.setHffl("allHFFL");
			all.setName("全部");
			groupCountList.add(all); // ### 增加全部分类 end

			// 已回访条数
			map.put("status", "1");
			int yi = logic.getCountByQuery(TableNameUtil.KQDS_VISIT, map, visualstaff, organization);
			jobj.put("yihuifang", yi);

			// 存放 每个分类对应的数量
			jobj.put("flcounts", groupCountList);

			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 不分页查询 只在客服中心-回访中心页面被调用一次
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList4returnVisit.act")
	public String selectList4returnVisit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			
			String vtime1 = request.getParameter("vtime1");
			String vtime2 = request.getParameter("vtime2");
			String name = request.getParameter("username");
			String ishuifang = request.getParameter("ishuifang");
			String hffl = request.getParameter("hffl");
			String visitor = request.getParameter("visitor");
			String myd = request.getParameter("myd");
			String createtime = request.getParameter("createtime");
			String phonenumber1 = request.getParameter("phonenumber1");
			String visitdept = request.getParameter("visitdept");
			String sortName=request.getParameter("sortName");
			String sortOrder=request.getParameter("sortOrder");
			/** 回访人员所在部门 **/
			if (!YZUtility.isNullorEmpty(visitdept) && !visitdept.equals("null") && !visitdept.equals("undefined")) {
				map.put("visitdept", visitdept);
			}
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			
			JSONObject json = new JSONObject();
			
			if (!YZUtility.isNullorEmpty(createtime) && !createtime.equals("null") && !createtime.equals("undefined")) {
				map.put("createtime", createtime);
			}
			
			if (!YZUtility.isNullorEmpty(vtime1) && !vtime1.equals("null") && !vtime1.equals("undefined")) {
				// 如果是精确到天的，则加上 时分
				if (vtime1.length() == 10) {
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
					Date date =sdf.parse(vtime1);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
			        calendar.add(Calendar.DATE, -1);    //得到前一天
			        String yestedayDate= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
					vtime1 = yestedayDate + ConstUtil.HOUR_START;
				}
				map.put("vtime1", vtime1);
			}
			if (!YZUtility.isNullorEmpty(vtime2) && !vtime2.equals("null") && !vtime2.equals("undefined")) {
				if (vtime2.length() == 10) {
					vtime2 = vtime2 + ConstUtil.HOUR_END;
				}
				map.put("vtime2", vtime2);
			}

			if (YZUtility.isNullorEmpty(vtime1) && YZUtility.isNullorEmpty(vtime2)) {
				String curDate = YZUtility.getDateStr(new Date());
				Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
		        calendar.add(Calendar.DATE, -1);    //得到前一天
		        String yestedayDate= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				map.put("vtime1", yestedayDate + ConstUtil.HOUR_START);
				map.put("vtime2", curDate + ConstUtil.HOUR_END);
			}

			if (!YZUtility.isNullorEmpty(name) && !name.equals("null") && !name.equals("undefined")) {
				map.put("name", name);
			}
			if (!YZUtility.isNullorEmpty(ishuifang) && !ishuifang.equals("null") && !ishuifang.equals("undefined")) {
				map.put("ishuifang", ishuifang);
			}
			if (!YZUtility.isNullorEmpty(hffl) && !hffl.equals("null") && !hffl.equals("undefined")) {
				map.put("hffl", hffl);
			}
			if (!YZUtility.isNullorEmpty(visitor) && !visitor.equals("null") && !visitor.equals("undefined")) {
				map.put("visitor", visitor);
			}
			if (!YZUtility.isNullorEmpty(myd) && !myd.equals("null") && !myd.equals("undefined")) {
				map.put("myd", myd);
			}
			if (!YZUtility.isNullorEmpty(phonenumber1) && !phonenumber1.equals("null") && !phonenumber1.equals("undefined")) {
				map.put("phonenumber1", phonenumber1);
			}
			// 门诊过滤条件
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			//String organization = ChainUtil.getCurrentOrganization(request);//分院模式
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
			}
			if(!YZUtility.isNullorEmpty(sortOrder)){
				map.put("sortOrder", sortOrder);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 初始化分页实体类
			@SuppressWarnings("rawtypes")
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject list = logic.selectList4returnVisit(TableNameUtil.KQDS_VISIT,bp, map, visualstaff, organization,json,flag);
			List<JSONObject> list0 = list.getJSONArray("rows");
			
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("回访中心", fieldArr, fieldnameArr, list0, response, request);
				return null;
			}
			
			YZUtility.DEAL_SUCCESS(list, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据患者编号查询患者详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCostorderDetailByUsercode.act")
	public String selectCostorderDetailByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			Map<String, String> map = new HashMap<String, String>();
			map.put("UserCode", usercode);

			List<KqdsUserdocument> en = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);

			JSONObject jobj = new JSONObject();
			if (en != null && en.size() > 0) {
				jobj.put("data", en.get(0));
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}