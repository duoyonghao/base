package com.kqds.controller.base.refund;

//合并测试
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsHzLabelAssociated;
import com.kqds.entity.base.KqdsIntegralRecord;
import com.kqds.entity.base.KqdsLabel;
import com.kqds.entity.base.KqdsPaycost;
import com.kqds.entity.base.KqdsRefund;
import com.kqds.entity.base.KqdsRefundDetail;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzLabel.KQDS_HzLabelAssociatedLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.base.refund.KQDS_RefundLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_RefundAct")
public class KQDS_RefundAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_RefundAct.class);
	@Autowired
	private KQDS_RefundLogic logic;
	@Autowired
	private KQDS_CostOrderLogic cologic;
	@Autowired
	private KQDS_CostOrder_DetailLogic costorderlogic;
	@Autowired
	private KQDS_UserDocumentLogic userLogic;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private KQDS_UserDocumentLogic userDocumentlogic;
	@Autowired
	private KQDS_hz_labelLogic hzLabelLogic;
	@Autowired
	private KQDS_HzLabelAssociatedLogic hzLabelAssociatedLogic;
	@Autowired
	private KQDS_REGLogic regLogic;

	@RequestMapping(value = "/toCost_TkIndex.act")
	public ModelAndView toCost_TkIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/coatOrder/cost_tk_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCost_TkList.act")
	public ModelAndView toCost_TkList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/coatOrder/cost_tklist.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTkSq.act")
	public ModelAndView toTkSq(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/coatOrder/cost_tk_sq.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTkSqEdit.act")
	public ModelAndView toTkSqEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String refundId = request.getParameter("refundId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("refundId", refundId);
		mv.setViewName("/kqdsFront/coatOrder/cost_tk_sq_edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTkSqDetail.act")
	public ModelAndView toTkSqDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String refundId = request.getParameter("refundId");
		String status = request.getParameter("status");
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("refundId", refundId);
		mv.addObject("status", status);
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.setViewName("/kqdsFront/coatOrder/cost_tk_sq_detail.jsp");
		return mv;
	}

	/**
	 * 申请退款
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
			KqdsRefund dp = new KqdsRefund();
			BeanUtils.populate(dp, request.getParameterMap());
			String costno = request.getParameter("costno");
			// 创建退款单
			KqdsCostorder tkorder = (KqdsCostorder) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
			String uuid = YZUtility.getUUID();
			dp.setSeqId(uuid);
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			dp.setUsercode(tkorder.getUsercode());
			dp.setCostno(tkorder.getCostno());
			dp.setTotalcost(tkorder.getTotalcost());
			dp.setVoidmoney(tkorder.getVoidmoney());
			dp.setShouldmoney(tkorder.getShouldmoney());
			dp.setArrearmoney(tkorder.getArrearmoney());
			// dp.setTotalarrmoney(tkorder.getTotalarrmoney());
			dp.setActualmoney(tkorder.getActualmoney());
			dp.setDiscountmoney(tkorder.getDiscountmoney());
			dp.setDoctor(tkorder.getDoctor());
			dp.setNurse(tkorder.getNurse());
			dp.setTechperson(tkorder.getTechperson());
			dp.setStatus(1);
			dp.setRemark(tkorder.getRemark());
			dp.setUsername(tkorder.getUsername());
			dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // ###
																			// 【前端调用，以当前所在门诊为主】
			logic.saveSingleUUID(TableNameUtil.KQDS_REFUND, dp);

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_REFUND, dp, dp.getUsercode(), TableNameUtil.KQDS_REFUND, request);

			// 给总经理添加提示信息 收费申请
			List<JSONObject> personlist = new ArrayList<JSONObject>();
			personlist = personLogic.getAllShowZjlPerson(ChainUtil.getCurrentOrganization(request), request);
			for (int i = 0; i < personlist.size(); i++) {
				PushUtil.saveTx4NewTuiFei(personlist.get(i), dp, request);
			}
			// 保存的对象集合 json格式
			String listdata = request.getParameter("listDetail");
			JSONArray jArray = JSONArray.fromObject(listdata);
			Collection collection = JSONArray.toCollection(jArray, KqdsRefundDetail.class);
			Iterator it = collection.iterator();
			// 保存收费项目
			KqdsRefundDetail detail = new KqdsRefundDetail();
			while (it.hasNext()) {
				detail = (KqdsRefundDetail) it.next();
				/* 把需要退费的收费项目的相关数据 填充到 退费清单表 */
				KqdsCostorderDetail orderDetail = (KqdsCostorderDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail.getOrderdetailno());
				detail.setSeqId(YZUtility.getUUID());
				detail.setRefundid(dp.getSeqId());
				detail.setCreatetime(orderDetail.getCreatetime());
				detail.setCreateuser(orderDetail.getCreatetime());
				detail.setUsercode(orderDetail.getUsercode());
				detail.setItemno(orderDetail.getItemno());
				detail.setItemname(orderDetail.getItemname());
				detail.setUnit(orderDetail.getUnit());
				detail.setUnitprice(orderDetail.getUnitprice());
				detail.setNum(Integer.parseInt(orderDetail.getNum()));
				detail.setDiscount(orderDetail.getDiscount());
				detail.setSubtotal(orderDetail.getSubtotal());
				detail.setArrearmoney(orderDetail.getArrearmoney());
				detail.setPaymoney(orderDetail.getPaymoney());
				detail.setVoidmoney(orderDetail.getVoidmoney());
				detail.setAskperson(orderDetail.getAskperson());
				detail.setDoctor(orderDetail.getDoctor());
				detail.setCostno(orderDetail.getCostno());
				detail.setQfbh(orderDetail.getQfbh());
				detail.setOrganization(ChainUtil.getCurrentOrganization(request)); // ###// 【以当前所在门诊为准】
				
				logic.saveSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, detail);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("refundid", dp.getSeqId());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTk.act")
	public String updateTk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String refundId = request.getParameter("refundId");
			String tkze = request.getParameter("tkze");
			// 修改退款单
			KqdsRefund tkorder = (KqdsRefund) logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND, refundId);
			if (ConstUtil.REFUND_STATUS_4 == tkorder.getStatus()) {
				throw new Exception("退款单已退费，操作无效！");
			}
			tkorder.setTkze(new BigDecimal(tkze));
			tkorder.setStatus(1);
			logic.updateSingleUUID(TableNameUtil.KQDS_REFUND, tkorder);

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_REFUND, tkorder, tkorder.getUsercode(), TableNameUtil.KQDS_REFUND, request);

			// 给总经理添加提示信息 收费申请
			List<JSONObject> personlist = new ArrayList<JSONObject>();
			personlist = personLogic.getAllShowZjlPerson(ChainUtil.getCurrentOrganization(request), request);
			for (int i = 0; i < personlist.size(); i++) {
				PushUtil.saveTx4NewTuiFei(personlist.get(i), tkorder, request);
			}
			// 保存的对象集合 json格式
			String listdata = request.getParameter("listDetail");
			JSONArray jArray = JSONArray.fromObject(listdata);
			Collection collection = JSONArray.toCollection(jArray, KqdsRefundDetail.class);
			Iterator it = collection.iterator();
			// 保存收费项目
			KqdsRefundDetail detail = new KqdsRefundDetail();
			while (it.hasNext()) {
				detail = (KqdsRefundDetail) it.next();
				/* 把需要退费的收费项目的相关数据 填充到 退费清单表 */
				KqdsRefundDetail orderDetail = (KqdsRefundDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, detail.getSeqId());
				orderDetail.setTknum(detail.getTknum());
				orderDetail.setTkmoney(detail.getTkmoney());
				orderDetail.setRemark(detail.getRemark());
				logic.updateSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, orderDetail);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("refundid", tkorder.getSeqId());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 验证费用单是否可以退单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yzTuiKuanJF.act")
	public String yzTuiKuanJF(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			boolean flagJF = true;
			BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, SysParaUtil.COST_INTEGRAL));
			// 参数值大于0 积分功能正常
			if (KqdsBigDecimal.compareTo(costIntegral, BigDecimal.ZERO) > 0) {

				String ssmoney = request.getParameter("ssmoney");
				String usercode = request.getParameter("usercode");
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("usercode", usercode);
				List<KqdsUserdocument> userlist = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
				if (userlist == null || userlist.size() == 0) {
					throw new Exception("患者不存在！");
				}
				BigDecimal ssmoneybig = BigDecimal.ZERO;
				if (!YZUtility.isNullorEmpty(ssmoney)) {
					ssmoneybig = new BigDecimal(ssmoney);
				}
				KqdsUserdocument user = userlist.get(0);
				BigDecimal integral = ssmoneybig.divide(costIntegral, 0, RoundingMode.DOWN);
				if (KqdsBigDecimal.compareTo(user.getIntegral(), integral) < 0) {
					flagJF = false;
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put("dataJF", flagJF); // true 可以退单 false 提示该费用单产生的积分已被使用
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 申请单操作 审核 确认
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/editState.act")
	public String editState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String usercode=request.getParameter("usercode");
			String username=request.getParameter("username");
			String access="1";
			KqdsRefund dp = new KqdsRefund();
			BeanUtils.populate(dp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			map.put("seq_id", dp.getSeqId());
			List<KqdsRefund> en = (List<KqdsRefund>) logic.loadList(TableNameUtil.KQDS_REFUND, map);
			if (en == null) {
				throw new Exception("退款单不存在，操作无效！");
			}
			// 申请状态
			if (ConstUtil.REFUND_STATUS_4 == dp.getStatus()) {// 确认退款
				if (ConstUtil.REFUND_STATUS_4 == en.get(0).getStatus()) {
					throw new Exception("退款单已退费，操作无效！");
				}
				String listdata = request.getParameter("listDetail");
				// 处理退款明细
				updateCostDetailType(listdata, request);
				// 添加费用单
				String newcostno = addOrder(en.get(0).getCostno(), en.get(0).getTkze(), dp.getSeqId(), person, request);
				String refundid = en.get(0).getSeqId();
				// 添加退款费用明细
				addOrderDetail(newcostno, refundid, en.get(0).getTkze(), person, request);
				// 添加结账记录
				addPayOrder(newcostno, en.get(0).getCostno(), dp.getSeqId(), en.get(0).getTkze(), person, request);
				en.get(0).setTktime(YZUtility.getCurDateTimeStr());// 退款确认时间
				en.get(0).setTkuser(person.getSeqId());// 退款确认人
				// 给申请人添加提示信息 已退款
				PushUtil.saveTx4TuiFeiConfirm(en.get(0), person, request);
				// 设置积分 实收金额
				setIntegralMoney(newcostno, en.get(0).getUsercode(), refundid, person.getSeqId(), request);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.CONFIRM_REFUND, BcjlUtil.KQDS_REFUND, en.get(0), en.get(0).getUsercode(), TableNameUtil.KQDS_REFUND, request);
				
				//2019-08-21 lwg 生成标签
				Map<String, String> map1 = new HashMap<String, String>();
				if (!YZUtility.isNullorEmpty(usercode)) {
					map1.put("usercode", usercode);
				}
				if (!YZUtility.isNullorEmpty(access) && !access.equals("1")) {// 不需要可见人员过滤，查询全部费用
					// access=1
					map1.put("access", access);
				}
				String visualstaff = SessionUtil.getVisualstaff(request);
				//查询实际消费项目
				List<JSONObject> list = cologic.selectWithPageNopageForLabel(TableNameUtil.KQDS_COSTORDER, map1, visualstaff);
				String itemname="";
				int nums=0;
				BigDecimal ys=new BigDecimal("0");
				List<JSONObject> itemlist1=new ArrayList<JSONObject>();
				List<JSONObject> itemlist2=new ArrayList<JSONObject>();
				for (JSONObject jsonObject : list) {
					if(jsonObject.getInt("status")== 2 && jsonObject.getInt("isyjjitem") == 0){//预交金单不算
		    			ys =ys.add(new BigDecimal(jsonObject.getString("shouldmoney")));
		    		}
					String costno=jsonObject.getString("costno");
					map1.put("costno", costno);
					List<JSONObject> list1 = costorderlogic.selectWithPageLzjlForLabel(TableNameUtil.KQDS_COSTORDER_DETAIL, map1);
					for (JSONObject jsonObject2 : list1) {
						if(jsonObject2.getString("itemname").endsWith("种植体")&&jsonObject2.getString("unit").equals("颗")){
							//种植体项目添加到itemlist
							if(jsonObject2.getInt("num")>0){
								itemlist1.add(jsonObject2);
							}else if(jsonObject2.getInt("num")<0){
								itemlist2.add(jsonObject2);
							}
						}
					}
				}
				List<JSONObject> itemlist3=new ArrayList<JSONObject>();
				List<JSONObject> itemlist4=new ArrayList<JSONObject>();
				//遍历itemlist查询种植体项目
				if(itemlist1.size()>0){
					StringBuffer str= new StringBuffer();
					if(itemlist1.size()==1){
						itemlist3.addAll(itemlist1);
					}else{
						for (JSONObject jsonObject2 : itemlist1) {
							if(!str.toString().contains(jsonObject2.getString("itemname"))){
								int m=0;
								for (int i = 0; i < itemlist1.size(); i++) {
									if(!jsonObject2.getString("seqid").equals(itemlist1.get(i).getString("seqid"))&&jsonObject2.getString("itemname").equals(itemlist1.get(i).getString("itemname"))){
										jsonObject2.put("num", (jsonObject2.getInt("num")+itemlist1.get(i).getInt("num"))+"");
										m=1;
									}
								}
								if(m==1){
									itemlist3.add(jsonObject2);
									str.append(jsonObject2.getString("itemname")+",");
									
								}
							}
						}
					}
					if(str.length()>0){
						for (JSONObject jsonObject2 : itemlist1) {
							if(!str.toString().contains(jsonObject2.getString("itemname"))){
								itemlist3.add(jsonObject2);
							}
						}
					}else if(str.length()==0&&itemlist1.size()>1){
						itemlist3.addAll(itemlist1);
					}
					
				}
				if(itemlist2.size()>0){
					StringBuffer str= new StringBuffer();
					if(itemlist2.size()==1){
						itemlist4.addAll(itemlist2);
					}else{
						for (JSONObject jsonObject2 : itemlist2) {
							if(!str.toString().contains(jsonObject2.getString("itemname"))){
								int m=0;
								for (int i = 0; i < itemlist2.size(); i++) {
									if(!jsonObject2.getString("seqid").equals(itemlist2.get(i).getString("seqid"))&&jsonObject2.getString("itemname").equals(itemlist2.get(i).getString("itemname"))){
										jsonObject2.put("num", (jsonObject2.getInt("num")+itemlist2.get(i).getInt("num"))+"");
										m=1;
									}
								}
								if(m==1){
									itemlist4.add(jsonObject2);
									str.append(jsonObject2.getString("itemname")+",");	
								}
							}
						}
					}
					if(str.length()>0){
						for (JSONObject jsonObject2 : itemlist2) {
							if(!str.toString().contains(jsonObject2.getString("itemname"))){
								itemlist4.add(jsonObject2);
							}
						}
					}else if(str.length()==0&&itemlist2.size()>1){
						itemlist4.addAll(itemlist2);
					}
				}
				if(itemlist3.size()>0&&itemlist4.size()>0){
					StringBuffer str= new StringBuffer();
					for (JSONObject jsonObject3 : itemlist3) {
						for (JSONObject jsonObject4 : itemlist4) {
							if(jsonObject3.getString("itemname").equals(jsonObject4.getString("itemname"))){
								nums+=jsonObject3.getInt("num")+jsonObject4.getInt("num");
								if(jsonObject3.getInt("num")+jsonObject4.getInt("num")>0){
									str.append(jsonObject3.getString("itemname")+",");
									if(itemname.equals("")){
										itemname=jsonObject3.getString("itemname");
									}else if(!itemname.equals(jsonObject3.getString("itemname"))){
										itemname=itemname+"+"+jsonObject3.getString("itemname");
									}
								}else if(jsonObject3.getInt("num")+jsonObject4.getInt("num")==0){
									jsonObject3.put("num", "0");
								}
							}
						}
					}
					if(str.length()>0){
						for (JSONObject jsonObject3 : itemlist3) {
							if(!str.toString().contains(jsonObject3.getString("itemname"))&&!jsonObject3.getString("num").equals("0")){
								nums+=jsonObject3.getInt("num");
								if(itemname.equals("")){
									itemname=jsonObject3.getString("itemname");
								}else if(!itemname.equals(jsonObject3.getString("itemname"))){
									itemname=itemname+"+"+jsonObject3.getString("itemname");
								}
							}
						}
					}else if(str.length()==0&&itemlist3.size()>1){
						for (JSONObject jsonObject3 : itemlist3) {
							if(jsonObject3.getInt("num")>0){
								nums+=jsonObject3.getInt("num");
								if(itemname.equals("")){
									itemname=jsonObject3.getString("itemname");
								}else if(!itemname.equals(jsonObject3.getString("itemname"))){
									itemname=itemname+"+"+jsonObject3.getString("itemname");
								}
							}
						}
					}
				}else if(itemlist3.size()>0&&itemlist4.size()==0){
					for (JSONObject jsonObject3 : itemlist3) {
						nums+=jsonObject3.getInt("num");
						if(itemname.equals("")){
							itemname=jsonObject3.getString("itemname");
						}else if(!itemname.equals(jsonObject3.getString("itemname"))){
							itemname=itemname+"+"+jsonObject3.getString("itemname");
						}
					}
				}
				if(ys.compareTo(new BigDecimal("0"))==1||ys.compareTo(new BigDecimal("0"))==0){
					//查询消费金额
					map1.put("status", "4");
					int status=4;
					//判断患者是否已经关联标签
					String hzLabelAssciatedSeqId=hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map1);
					//患者已经关联标签
					String labelname="";
					//查询父类标签id
					String parentName="消费区间";
					KqdsLabel kqdsLabel=hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
					if(!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)){
						//查询患者标签是否和实际相符
						labelname=hzLabelAssociatedLogic.selectKqdsHzLabelBySeqId(hzLabelAssciatedSeqId);
						String start="";
						String end="";
						if(!YZUtility.isNullorEmpty(labelname)){
							//相符
							if(labelname.endsWith("以下")&&labelname.length()==6){
								start=labelname.substring(0,4);
							}else if(labelname.length()==11){
								start=labelname.substring(0,4);
								end=labelname.substring(5,10);
							}else if(labelname.length()==12){
								start=labelname.substring(0,5);
								end=labelname.substring(6,11);
							}else if(labelname.length()==6){
								start=labelname.substring(0,2);
								end=labelname.substring(3,5);
							}else{
								end="200000.9";
							}
						}
						if(!start.equals("")&&!end.equals("")){
							if(ys.compareTo(new BigDecimal(start))==-1||ys.compareTo(new BigDecimal(end))==1){
								//修改关联表,生成新标签名,保存新的关联表数据
								String ys1=ys.toString();
								labelname=cloudOfTags(ys1,null,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
							}
						}else if(!start.equals("")&&end.equals("")){
							if(ys.compareTo(new BigDecimal(start))==1){
								//修改关联表,生成新标签名,保存新的关联表数据
								String ys1=ys.toString();
								labelname=cloudOfTags(ys1,null,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
							}
						}else if(start.equals("")&&!end.equals("")){
								//修改关联表,生成新标签名,保存新的关联表数据
								String ys1=ys.toString();
								labelname=cloudOfTags(ys1,null,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
						}else{
							String ys1=ys.toString();
							labelname=cloudOfTags(ys1,null,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
						}
					}else{
						//直接生成存储标签
						//修改关联表,生成新标签名,保存新的关联表数据
						String ys1=ys.toString();
						labelname=cloudOfTags(ys1,null,null,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(),request);
					}
				}
				if(!YZUtility.isNullorEmpty(itemname)&&nums>0){
					//查询消费项目
					map1.put("status", "3");
					int status=3;
					String xfxmLabelname="";
					//判断患者是否已经关联标签
					String hzLabelAssciatedSeqId=hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map1);
					//患者已经关联标签
					String parentName="消费项目(种植体品牌)";
					KqdsLabel kqdsLabel=hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
					if(!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)){
						//查询患者标签是否和实际相符
						xfxmLabelname=hzLabelAssociatedLogic.selectKqdsHzLabelBySeqId(hzLabelAssciatedSeqId);
						if(xfxmLabelname!=null&&!xfxmLabelname.equals(itemname)){
							xfxmLabelname=cloudOfTags(null,itemname,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
						}
					}else{
						//直接生成存储标签
						//修改关联表,生成新标签名,保存新的关联表数据
						xfxmLabelname=cloudOfTags(null,itemname,null,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
					}
					
				}else{
					String parentName="消费项目(种植体品牌)";
					KqdsLabel kqdsLabel=hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
					map.put("userCode", usercode);
					map.put("labelTwoId", kqdsLabel.getSeqId());
					userDocumentlogic.deleteLabelByUsercode(map);
					KqdsHzLabelAssociated kqdsHzLabelAssociated=new KqdsHzLabelAssociated();
					kqdsHzLabelAssociated.setModifier(person.getSeqId());
					kqdsHzLabelAssociated.setUpdateTime(YZUtility.getCurDateTimeStr());
					kqdsHzLabelAssociated.setUsercode(usercode);
					kqdsHzLabelAssociated.setStatus(3);
					int j=hzLabelAssociatedLogic.updateKqdsHzLabelAssociatedByStatus(kqdsHzLabelAssociated);
			}
			}
			if (ConstUtil.REFUND_STATUS_2 == dp.getStatus() || ConstUtil.REFUND_STATUS_3 == dp.getStatus()) {// 审核
																												// [同意退款、拒绝退款]
				if (ConstUtil.REFUND_STATUS_2 == en.get(0).getStatus() || ConstUtil.REFUND_STATUS_3 == en.get(0).getStatus()) {
					throw new Exception("退款单已审核，操作无效！");
				}
				// 同意申请，验证金额
				if (2 == dp.getStatus()) {
					Map map2 = new HashMap<>();
					map2.put("refundid", dp.getSeqId());
					// 查询该退款单下的退款明细
					List<KqdsRefundDetail> list = (List<KqdsRefundDetail>) logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map2);
					for (KqdsRefundDetail detail : list) {
						// 已收费
						JSONObject obj = costorderlogic.checkTf(detail.getUsercode(), detail.getItemno(), detail.getQfbh(), detail.getOrderdetailno());
						BigDecimal ysf = new BigDecimal(obj.getString("paymoney"));
						// 已退款申请
						JSONObject objrefund = costorderlogic.checkTfRefund(detail.getUsercode(), detail.getItemno(), detail.getQfbh(), detail.getOrderdetailno());
						BigDecimal agreeTk = new BigDecimal(objrefund.getString("tkmoney"));
						BigDecimal aowTk = detail.getTkmoney();
						BigDecimal tked = KqdsBigDecimal.add(agreeTk, aowTk);
						if (KqdsBigDecimal.compareTo(ysf, tked) < 0) {
							throw new Exception("该收费项目的退款金额大于缴费金额！");
						}
					}

					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.AGREE, BcjlUtil.KQDS_REFUND, dp, dp.getUsercode(), TableNameUtil.KQDS_REFUND, request);
				}
				if (3 == dp.getStatus()) {
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DISAGREE, BcjlUtil.KQDS_REFUND, dp, dp.getUsercode(), TableNameUtil.KQDS_REFUND, request);

				}
				en.get(0).setShtime(YZUtility.getCurDateTimeStr());// 审核时间
				en.get(0).setShuser(person.getSeqId());// 审核人
				en.get(0).setRemark(dp.getRemark());
				// 给收费人添加提示信息 收费申请
				List<JSONObject> personlist = personLogic.getAllShowfeiPerson(ChainUtil.getCurrentOrganization(request), request);
				for (int i = 0; i < personlist.size(); i++) {
					PushUtil.saveTx4TuiFeiSH(personlist.get(i), person, request);
				}
			}
			en.get(0).setStatus(dp.getStatus());
			logic.updateSingleUUID(TableNameUtil.KQDS_REFUND, en.get(0));
			
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	private String cloudOfTags(String ys,String itemname,String hzLabelAssciatedSeqId,YZPerson person,String usercode,String username,int status,String parentId,String parentName,String labelOneId,String labelOneName,HttpServletRequest request) throws Exception {
		String labelname="";
		try {
		if(!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)){
			KqdsHzLabelAssociated kqdsHzLabelAssociated=new KqdsHzLabelAssociated();
			kqdsHzLabelAssociated.setSeqId(hzLabelAssciatedSeqId);
			kqdsHzLabelAssociated.setModifier(person.getSeqId());
			kqdsHzLabelAssociated.setUpdateTime(YZUtility.getCurDateTimeStr());
			int i = hzLabelAssociatedLogic.updateKqdsHzLabelAssociated(kqdsHzLabelAssociated);
		}
		if(!YZUtility.isNullorEmpty(ys)){
			double d = Double.valueOf(ys);
			if(d>=0&&d<=5000.9){
				labelname="5000以下";
			}else if(d>=5001&&d<=20000.9){
				labelname="5001-20000元";
			}else if(d>=20001&&d<=40000.9){
				labelname="20001-40000元";
			}else if(d>=40001&&d<=60000.9){
				labelname="40001-60000元";
			}else if(d>=60001&&d<=80000.9){
				labelname="60001-80000元";
			}else if(d>=80001&&d<=100000.9){
				labelname="80001-100000元";
			}else if(d>=100001&&d<=150000.9){
				labelname="10-15万";
			}else if(d>=150001&&d<=200000.9){
				labelname="15-20万";
			}else if(d>=200001){
				labelname="20万以上";
			}
		}else if(!YZUtility.isNullorEmpty(itemname)){
			labelname=itemname;
		}
		if(YZUtility.isNullorEmpty(itemname)&&YZUtility.isNullorEmpty(ys)){
			Map<String,String> map =new  HashMap<String,String>();
			map.put("userCode", usercode);
			map.put("labelTwoId", "bd7c762b-9465-457c-b9b1-b085fc2a27a5");
			userDocumentlogic.deleteLabelByUsercode(map);
		}
		//查询标签名有无，有则使用，无则新增
		if(labelname!=null&&!labelname.equals("")){
			Map<String,String> map1= new HashMap<String,String>();
			if(labelname!=null &&!labelname.equals("")){
				map1.put("leveLabel", labelname);
			}
			if(!parentId.equals("")&&parentId!=null){
				map1.put("parentId", parentId);
			}
			String labelId = hzLabelLogic.selectKqdsHzLabelByLeveLabel(map1);
			if(YZUtility.isNullorEmpty(labelId)){
				//新增标签和关联表数据
				KqdsLabel kqdsHzLabel = new KqdsLabel();
				String seqid=YZUtility.getUUID();
				labelId=seqid;
				kqdsHzLabel.setSeqId(seqid);
				kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
				kqdsHzLabel.setCreateUser(person.getSeqId());
				kqdsHzLabel.setLeveLabel(labelname);
				kqdsHzLabel.setParentId(parentId);
				kqdsHzLabel.setParentName(parentName);
				kqdsHzLabel.setStatus("0");
				kqdsHzLabel.setRemark("三级");
				int j = hzLabelLogic.insertKqdsHzLabel(kqdsHzLabel);
				KqdsHzLabelAssociated kqdsHzLabelAssociated1=new KqdsHzLabelAssociated();
				hzLabelAssciatedSeqId=YZUtility.getUUID();
				kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
				kqdsHzLabelAssociated1.setLabeId(seqid);
				kqdsHzLabelAssociated1.setUsercode(usercode);//患者id
				kqdsHzLabelAssociated1.setUserName(username);//患者姓名
				kqdsHzLabelAssociated1.setCreateTime(YZUtility.getCurDateTimeStr());
				kqdsHzLabelAssociated1.setCreateUser(person.getSeqId());
				kqdsHzLabelAssociated1.setRemark("");
				kqdsHzLabelAssociated1.setStatus(status);
				kqdsHzLabelAssociated1.setIsdelete(0);
				int t=hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
			}else{
				KqdsHzLabelAssociated kqdsHzLabelAssociated1=new KqdsHzLabelAssociated();
				hzLabelAssciatedSeqId=YZUtility.getUUID();
				kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
				kqdsHzLabelAssociated1.setLabeId(labelId);
				kqdsHzLabelAssociated1.setUsercode(usercode);//患者id
				kqdsHzLabelAssociated1.setUserName(username);//患者姓名
				kqdsHzLabelAssociated1.setCreateTime(YZUtility.getCurDateTimeStr());
				kqdsHzLabelAssociated1.setCreateUser(person.getSeqId());
				kqdsHzLabelAssociated1.setRemark("");
				kqdsHzLabelAssociated1.setStatus(status);
				kqdsHzLabelAssociated1.setIsdelete(0);
				int t=hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
				
			}
			Map<String,String> map =new  HashMap<String,String>();
			map.put("userCode", usercode);
			map.put("labelTwoId", parentId);
			String threeid=userDocumentlogic.selectLabelByUsercode(map);
			userDocumentlogic.deleteLabelByUsercode(map);
			kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
				String id = YZUtility.getUUID();
				kPatient.setSeqId(id);
				kPatient.setUserSeqId("");
				kPatient.setUserId(usercode);
				kPatient.setUserName(username);
				kPatient.setLabelOneId(labelOneId);
				kPatient.setLabelOneName(labelOneName);
				kPatient.setLabelTwoId(parentId);
				kPatient.setLabelTwoName(parentName);
				kPatient.setLabelThreeId(labelId);
				kPatient.setLabelThreeName(labelname);
				kPatient.setCreateUser(person.getSeqId());
				kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
				kPatient.setOrganization(ChainUtil.getCurrentOrganization(request));
				userDocumentlogic.saveKpatient(kPatient);
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_Hz_LabellabeAndPatient, kPatient, usercode, TableNameUtil.KQDS_Hz_LabellabeAndPatient, request);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return labelname;
	}
	/**
	 * 积分减少
	 * 
	 * @param usercode
	 * @param dbConn
	 * @param Actualmoney
	 * @param doctor
	 * @param money22
	 * @throws Exception
	 */
	private void setIntegralMoney(String costno, String usercode, String refundid, String perId, HttpServletRequest request) throws Exception {
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("usercode", usercode);
		List<KqdsUserdocument> userlist = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
		if (userlist == null) {
			throw new Exception("患者不存在！");
		}
		KqdsUserdocument u = userlist.get(0);
		BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, SysParaUtil.COST_INTEGRAL));

		Map<String, String> map = new HashMap<String, String>();
		map.put("refundid", refundid);

		BigDecimal ssmoney = userLogic.getSsjeOne(costno);

		// 实收金额
		u.setTotalpay(u.getTotalpay().add(ssmoney));
		// 参数值大于0 积分功能正常
		if (KqdsBigDecimal.compareTo(costIntegral, BigDecimal.ZERO) > 0) {
			// 设置积分
			BigDecimal integral = ssmoney.divide(costIntegral, 0, RoundingMode.DOWN);
			u.setIntegral(u.getIntegral().add(integral));
			logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
			// 保存积分记录
			String jfjs = dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "退费减少");
			String tkjs = dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "退费返回");

			if (YZUtility.isNullorEmpty(jfjs) || YZUtility.isNullorEmpty(jfjs)) {
				throw new Exception("积分类型不存在！");
			}
			KqdsIntegralRecord record = new KqdsIntegralRecord();
			record.setSeqId(UUID.randomUUID().toString());
			record.setCreatetime(YZUtility.getCurDateTimeStr());
			record.setOrganization(ChainUtil.getCurrentOrganization(request));
			record.setUsercode(u.getUsercode());
			record.setJfmoney(integral);
			record.setJftype(jfjs);
			record.setSyjfmoney(u.getIntegral());
			record.setCreateuser(perId);
			logic.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);

			// 查询该退款单下的退款明细
			List<KqdsRefundDetail> list = (List<KqdsRefundDetail>) logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map);
			// 退费积分金额
			BigDecimal jfje = BigDecimal.ZERO;
			for (KqdsRefundDetail detail : list) {
				jfje = jfje.add(detail.getPayintegral());
			}
			// 退单回退
			record = new KqdsIntegralRecord();
			record.setSeqId(UUID.randomUUID().toString());
			record.setCreatetime(YZUtility.getCurDateTimeStr());
			record.setOrganization(ChainUtil.getCurrentOrganization(request));
			record.setUsercode(u.getUsercode());
			record.setJfmoney(jfje);
			record.setJftype(tkjs);
			record.setSyjfmoney(u.getIntegral().add(jfje));
			record.setCreateuser(perId);
			logic.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);
			u.setIntegral(u.getIntegral().add(jfje));
			logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
		} else {
			logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
		}
	}

	@RequestMapping(value = "/NoselectPage.act")
	public void updateCostDetailType(String listdata, HttpServletRequest request) throws Exception {
		// 保存的对象集合 json格式
		JSONArray jArray = JSONArray.fromObject(listdata);
		Collection collection = JSONArray.toCollection(jArray, KqdsRefundDetail.class);
		Iterator it = collection.iterator();
		// 保存收费项目
		KqdsRefundDetail detail = new KqdsRefundDetail();
		while (it.hasNext()) {
			detail = (KqdsRefundDetail) it.next();
			/* 把需要退费的收费项目的相关数据 填充到 退费清单表 */
			KqdsRefundDetail refundDetail = (KqdsRefundDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, detail.getSeqId());
			refundDetail.setPayyjj(BigDecimal.ZERO);
			refundDetail.setPayxj(detail.getPayxj());
			refundDetail.setPaybank(detail.getPaybank());
			refundDetail.setPayyb(detail.getPayyb());
			refundDetail.setPaywx(detail.getPaywx());
			refundDetail.setPayzfb(detail.getPayzfb());
			refundDetail.setPaymmd(detail.getPaymmd());
			refundDetail.setPaybdfq(detail.getPaybdfq());
			refundDetail.setPaydjq(detail.getPaydjq());
			refundDetail.setPayintegral(detail.getPayintegral());
			refundDetail.setPayother1(detail.getPayother1());
			refundDetail.setPayother2(detail.getPayother2());
			logic.updateSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, refundDetail);
		}
	}

	/*
	 * 详情返回
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			KqdsRefund en = (KqdsRefund) logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND, seqId);
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
	 * 查询该项目 实际欠费情况【内部调用】
	 * 
	 * @param dbConn
	 * @param qfbh
	 * @return
	 * @throws Exception
	 */
	private BigDecimal realQfDetail(String qfbh) throws Exception {
		BigDecimal qf = BigDecimal.ZERO;
		if (!YZUtility.isNullorEmpty(qfbh)) {
			// 根据欠费编号查询 该项目实际欠费情况
			String qfStr = costorderlogic.selectRealQf(TableNameUtil.KQDS_COSTORDER_DETAIL, qfbh);
			qf = qf.add(new BigDecimal(qfStr));
		}
		return qf;

	}

	/**
	 * 退款单列表 【内部调用】
	 * 
	 * @param dbConn
	 * @param costno
	 * @param tkje
	 * @param refundid
	 * @param person
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String addOrder(String costno, BigDecimal tkje, String refundid, YZPerson person, HttpServletRequest request) throws Exception {
		// 欠款单

		KqdsCostorder tkorder = (KqdsCostorder) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
		tkorder.setSeqId(refundid);
		tkorder.setCostno(refundid);
		tkorder.setCreatetime(YZUtility.getCurDateTimeStr());
		tkorder.setSftime(YZUtility.getCurDateTimeStr());
		tkorder.setSfuser(person.getSeqId());
		tkorder.setCreateuser(person.getSeqId());
		BigDecimal qf = BigDecimal.ZERO;
		Map<String, String> map = new HashMap<String, String>();
		map.put("refundid", refundid);
		// 查询该退款单下的退款明细
		List<KqdsRefundDetail> list = (List<KqdsRefundDetail>) logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map);
		for (KqdsRefundDetail detail : list) {
			KqdsCostorderDetail orderdetail = (KqdsCostorderDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail.getOrderdetailno());
			if (KqdsBigDecimal.compareTo(orderdetail.getY2(), BigDecimal.ZERO) != 0) { // 如果是欠费单，查询总欠费
				String qfStr = costorderlogic.selectRealQf(TableNameUtil.KQDS_COSTORDER_DETAIL, orderdetail.getQfbh());
				qf = qf.add(new BigDecimal(qfStr));
			}
		}
		// 退款总额
		BigDecimal tkmoney = tkje;
		// 缴费金额
		tkorder.setActualmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
		// 应收金额
		BigDecimal ysje = KqdsBigDecimal.add(tkmoney, qf);
		tkorder.setShouldmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, ysje));
		// 免除金额
		tkorder.setVoidmoney(qf);
		/** 退费之后，清楚当前费用单的欠费标识 **/
		// 欠费金额
		tkorder.setY2(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
		/** 把欠费金额免除，和上面的 tkorder.setVoidmoney(qf)抵消，目的是退款后，不弹欠费项目 **/
		// 欠费金额
		tkorder.setArrearmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
		// 欠费金额
		tkorder.setTotalarrmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
		// 合计
		tkorder.setTotalcost(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
		tkorder.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
																			// 【前端调用，以当前所在门诊为主】
		logic.saveSingleUUID(TableNameUtil.KQDS_COSTORDER, tkorder);
		if (YZUtility.isNotNullOrEmpty(tkorder.getRegno())) {
			KqdsReg reg = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, tkorder.getRegno());
			if (reg == null) {
				throw new Exception("数据不存在");
			}
			// 就诊科室赋值
			YZPerson per = (YZPerson) logic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, reg.getDoctor());
			if (per != null) {
				reg.setRegdept(per.getDeptId());
			}
			logic.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
		}
		return refundid;
	}

	/**
	 * 添加退款明细 【内部调用】
	 * 
	 * @param dbConn
	 * @param newcostno
	 * @param refundid
	 * @param tkje
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	private void addOrderDetail(String newcostno, String refundid, BigDecimal tkje, YZPerson person, HttpServletRequest request) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("refundid", refundid);
		// 查询该退款单下的退款明细
		List<KqdsRefundDetail> list = (List<KqdsRefundDetail>) logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map);
		for (KqdsRefundDetail detail : list) {
			// 查询该退款明细的收费项目明细
			KqdsCostorderDetail orderdetail = (KqdsCostorderDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail.getOrderdetailno());
			// 查询该项目实际欠费情况
			BigDecimal qf = realQfDetail(orderdetail.getQfbh());
			if (0 == detail.getTknum()) {
				orderdetail.setNum((0 - detail.getNum()) + "");
			} else {
				orderdetail.setNum((0 - detail.getTknum()) + "");
			}
			orderdetail.setSeqId(detail.getSeqId());
			orderdetail.setCostno(newcostno);
			orderdetail.setCreatetime(YZUtility.getCurDateTimeStr());
			orderdetail.setCreateuser(person.getSeqId());
			// 退款总额
			BigDecimal tkmoney = detail.getTkmoney();
			// 实收金额
			orderdetail.setPaymoney(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
			orderdetail.setPayyjj(BigDecimal.ZERO);
			orderdetail.setPayxj(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayxj()));
			orderdetail.setPaybank(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaybank()));
			orderdetail.setPayyb(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayyb()));
			orderdetail.setPaywx(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaywx()));
			orderdetail.setPayzfb(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayzfb()));
			orderdetail.setPaymmd(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaymmd()));
			orderdetail.setPaybdfq(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaybdfq()));
			orderdetail.setPaydjq(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaydjq()));
			orderdetail.setPayintegral(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayintegral()));
			orderdetail.setPayother1(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayother1()));
			orderdetail.setPayother2(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPayother2()));
			orderdetail.setStatus(ConstUtil.COST_DETAIL_STATUS_0);
			orderdetail.setIsqfreal(ConstUtil.QF_STATUS_0);
			// 应收金额
			orderdetail.setArrearmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
			// 免除金额
			orderdetail.setVoidmoney(qf);
			// 欠费金额
			orderdetail.setY2(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
			// 合计
			orderdetail.setSubtotal(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
			orderdetail.setIstk(1);
			orderdetail.setCreatetime(YZUtility.getCurDateTimeStr());
			orderdetail.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
			orderdetail.setParentid(detail.getOrderdetailno());																		// 【前端调用，以当前所在门诊为主】
			logic.saveSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, orderdetail);
			// 取消该欠费项目的欠费状态
			editQfStatus(orderdetail.getQfbh(), request);
		}
	}

	/**
	 * 取消该欠费项目的欠费状态 【内部调用】
	 * 
	 * @param qfbh
	 * @param dbConn
	 * @throws Exception
	 */
	private void editQfStatus(String qfbh, HttpServletRequest request) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("qfbh", qfbh);
		// map.put("status", 1);
		map.put("isqfreal", "1");
		List<KqdsCostorderDetail> detaillist = (List<KqdsCostorderDetail>) logic.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
		for (KqdsCostorderDetail detailnew : detaillist) {
			detailnew.setStatus(ConstUtil.COST_DETAIL_STATUS_0);
			detailnew.setIsqfreal(ConstUtil.QF_STATUS_0);
			logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detailnew);
		}
	}

	/**
	 * 添加退款收费 【内部调用】
	 * 
	 * @param dbConn
	 * @param newcostno
	 * @param oldcostno
	 * @param refundid
	 * @param tkje
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	private void addPayOrder(String newcostno, String oldcostno, String refundid, BigDecimal tkje, YZPerson person, HttpServletRequest request) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("costno", oldcostno);
		// 查询该costno下的收费信息
		List<KqdsPaycost> listpay = (List<KqdsPaycost>) logic.loadList(TableNameUtil.KQDS_PAYCOST, map);
		for (KqdsPaycost detail : listpay) {
			// 查询该项目实际欠费情况
			BigDecimal qf = BigDecimal.ZERO;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("refundid", refundid);
			// 查询该退款单下的退款明细
			List<KqdsRefundDetail> list = (List<KqdsRefundDetail>) logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map2);
			for (KqdsRefundDetail rdetail : list) {
				KqdsCostorderDetail orderdetail = (KqdsCostorderDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, rdetail.getOrderdetailno());
				if( !orderdetail.getQfbh().equals("") ) {//添加判断，避免查大量数据影响性能 2019-10-22 syp
					String qfStr = costorderlogic.selectRealQf(TableNameUtil.KQDS_COSTORDER_DETAIL, orderdetail.getQfbh());
					qf = qf.add(new BigDecimal(qfStr));
				}
			}
			String uuid = YZUtility.getUUID();
			detail.setSeqId(uuid);
			detail.setCostno(newcostno);
			detail.setCreatetime(YZUtility.getCurDateTimeStr());
			detail.setCreateuser(person.getSeqId());
			// 实收金额
			// 退款总额
			BigDecimal tkmoney = tkje;
			// 缴费金额
			detail.setActualmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
			// 应收金额
			BigDecimal ysje = KqdsBigDecimal.add(tkmoney, qf);
			detail.setShouldmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, ysje));
			// 免除金额
			detail.setVoidmoney(qf);
			// 欠费金额
			detail.setArrearmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, qf));
			// 合计
			detail.setTotalcost(KqdsBigDecimal.sub(BigDecimal.ZERO, tkmoney));
			detail.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
																				// 【前端调用，以当前所在门诊为主】
			logic.saveSingleUUID(TableNameUtil.KQDS_PAYCOST, detail);
		}
	}

	@RequestMapping(value = "/selectWithNopage.act")
	public String selectWithNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			// 模糊查询
			String searchValue = request.getParameter("searchValue");
			// 退款状态
			String tkstatus = request.getParameter("tkstatus");
			// 申请退款时间
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(searchValue)) {
				map.put("searchValue", searchValue);
			}
			if (!YZUtility.isNullorEmpty(tkstatus)) {
				map.put("status", tkstatus);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}

			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> list = logic.selectWithNopage(TableNameUtil.KQDS_REFUND, map, person, visualstaff, organization);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				for (JSONObject json : list) {
					String tkze = json.getString("tkze");
					json.put("tkze", "-" + tkze); // 和页面保持一致
				}
				ExportTable.exportBootStrapTable2Excel("项目退款", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
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

			KqdsRefund en = (KqdsRefund) logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND, seqId);
			if (ConstUtil.REFUND_STATUS_4 == en.getStatus()) {// 一结账不能删除
				throw new Exception("该退款单已退款，不能删除！");
			} else {
				logic.deleteSingleUUID(TableNameUtil.KQDS_REFUND, seqId);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_REFUND, en, en.getUsercode(), TableNameUtil.KQDS_REFUND, request);
				// 删除费用单中项目
				Map<String, String> map = new HashMap<String, String>();
				map.put("refundid", seqId);
				List<KqdsRefundDetail> list = (List<KqdsRefundDetail>) logic.loadList(TableNameUtil.KQDS_REFUND_DETAIL, map);
				for (KqdsRefundDetail detail : list) {
					logic.deleteSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, detail.getSeqId());
				}
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
}