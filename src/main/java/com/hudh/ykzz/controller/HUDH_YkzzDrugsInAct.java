package com.hudh.ykzz.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.service.IYkzzDrugsInService;
import com.hudh.ykzz.service.IYkzzDrugsOutService;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.refund.KQDS_RefundLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/HUDH_YkzzDrugsInAct")
public class HUDH_YkzzDrugsInAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_YkzzDrugsInAct.class);
	/**
	 * 药品入库接口
	 */
	@Autowired
	private IYkzzDrugsInService ykzzDrugsInService;
	@Autowired
	private IYkzzDrugsOutService ykzzDrugsOutService;
	@Autowired
	private KQDS_RefundLogic logic;
	/**
	 * 药品入库
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertDrugsIn.act")
	public String insertDrugsIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	    String drugsIndetails = request.getParameter("paramDetail");
		String rktime = request.getParameter("rktime");
		String intype = request.getParameter("intype");
		String supplier = request.getParameter("supplier");
		String stocktime = request.getParameter("stocktime");
		String incode = request.getParameter("incode");
		String inremark = request.getParameter("inremark");
		String zhaiyao = request.getParameter("zhaiyao");
		
		YkzzDrugsIn ykzzDrugsIn = new YkzzDrugsIn();
		ykzzDrugsIn.setRktime(rktime);
		ykzzDrugsIn.setIntype(intype);
		ykzzDrugsIn.setSupplier(supplier);
		ykzzDrugsIn.setStocktime(stocktime);
		ykzzDrugsIn.setIncode(incode);
		ykzzDrugsIn.setInremark(inremark);
		ykzzDrugsIn.setZhaiyao(zhaiyao);
		
		try {
			ykzzDrugsInService.insertDrugsIn(ykzzDrugsIn, drugsIndetails, request);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id直接删除入库数据用于审核未通过的入库单不关联库存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteDrugsIn.act")
	public String deleteDrugsIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			String backMg = ykzzDrugsInService.deleteDrugsIn(id);
			YZUtility.DEAL_SUCCESS(null,backMg, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping("/deleteDrugsInById.act")
	public String deleteDrugsInById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			ykzzDrugsInService.deleteDrugsInById(id);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询全部入库信息或根据条件查找
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAllDrugsIn.act")
	public void findAllDrugsIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		// 入库查询 页面参数
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String intype = request.getParameter("intype");
		String supplier = request.getParameter("supplier");
		String incode = request.getParameter("incode");
		String id = request.getParameter("id");
		String checkStatus = request.getParameter("check_status");
		String accurateIncode = request.getParameter("accurateIncode"); //精确查号单号
		
		String stock_starttime = request.getParameter("stock_starttime");
		String stock_endtime = request.getParameter("stock_endtime");
		String organization = ChainUtil.getCurrentOrganization(request);
		Map<String, String> map = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(id)) {
			map.put("id", id);
		}
		if (!YZUtility.isNullorEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		if (!YZUtility.isNullorEmpty(intype)) {
			map.put("intype", intype);
		}
		if (!YZUtility.isNullorEmpty(supplier)) {
			map.put("supplier", supplier);
		}
		if (!YZUtility.isNullorEmpty(incode)) {
			map.put("incode", incode);
		}
		if (!YZUtility.isNullorEmpty(stock_starttime)) {
			map.put("stock_starttime", stock_starttime);
		}
		if (!YZUtility.isNullorEmpty(stock_endtime)) {
			map.put("stock_endtime", stock_endtime);
		}
		if (!YZUtility.isNullorEmpty(accurateIncode)) {
			map.put("accurateIncode", accurateIncode);
		}
		if (!YZUtility.isNullorEmpty(checkStatus)) {
			map.put("checkStatus", checkStatus);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		try {
			List<JSONObject> list = ykzzDrugsInService.findAllDrugsIn(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 根据parentid查询入库明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDetailByParendId.act")
	public void findDetailByParendId(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String parentid = request.getParameter("parentid");
		try {
			if(YZUtility.isNotNullOrEmpty(parentid)) {
				List<JSONObject> list = ykzzDrugsInService.findDetailByParendId(parentid);
				YZUtility.RETURN_LIST(list, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}

	/**
	 * 发药获取所有费用单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAllCostOrder.act")
	public void findAllCostOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		// 入库查询 页面参数
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String hzname = request.getParameter("hzname");
		String status = request.getParameter("status");
		String issend = request.getParameter("issend");
		String organization = ChainUtil.getCurrentOrganization(request);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (!YZUtility.isNullorEmpty(starttime)) {
			map.put("starttime", starttime + ConstUtil.TIME_START);
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			map.put("endtime", endtime + ConstUtil.TIME_END);
		}
		if (!YZUtility.isNullorEmpty(hzname)) {
			map.put("hzname", hzname);
		}
		if (!YZUtility.isNullorEmpty(status)) {
			map.put("status", status);
		}
		if (!YZUtility.isNullorEmpty(issend)) {
			map.put("issend", issend);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		try {
			List<JSONObject> list = ykzzDrugsInService.findAllCostOrder(map);
			List<JSONObject> show =new ArrayList<JSONObject>();
			for (int i = 0; i < list.size(); i++) {
				BigDecimal money=new BigDecimal((String) list.get(i).get("actualmoney"));
				BigDecimal money4=new BigDecimal((String) list.get(i).get("voidmoney"));
				BigDecimal money1=new BigDecimal("0");
				BigDecimal money2=new BigDecimal("0");
				BigDecimal money3=new BigDecimal("0");
				if(money.compareTo(money1)>0||money4.compareTo(money1)>0){
					int variate=0;
					//退款次数
					int frequency=0;
					String costno=list.get(i).getString("costno");
					//查询明细
					//先判断明细的欠费情况
					List<JSONObject> list2 = ykzzDrugsInService.findCostOrderDetailByCostno(costno);
					for (int j = 0; j < list2.size(); j++) {
						if(list2.get(j).get("status").equals("0")){	
							BigDecimal unitprice=new BigDecimal((String)list2.get(j).get("unitprice"));
							BigDecimal num=new BigDecimal((String)list2.get(j).get("num"));
							String qfbh=(String) list2.get(j).get("qfbh");
							if(qfbh!=null&&!qfbh.equals("")){
								//查询状态为1的明细
								List<JSONObject> list3 = ykzzDrugsInService.findCostOrderDetailByQfbh(qfbh);
								//明细的缴费价格
								BigDecimal paymoney1=new BigDecimal((String)list2.get(j).get("paymoney"));
								for (JSONObject kqdsCostorderDetail : list3) {
									if(unitprice.multiply(num).compareTo(paymoney1.add(new BigDecimal((String)kqdsCostorderDetail.get("paymoney"))))==0){
										money3=money3.add(new BigDecimal((String)kqdsCostorderDetail.get("paymoney")));
									}
								}
							}
						}
					}
					money=money.add(money3);
					//无欠款的状态
					if(money3.compareTo(money1)==0&&list.get(i).get("actualmoney").equals(list.get(i).get("shouldmoney"))){
						BigDecimal voidmoney=new BigDecimal("0");
						for (int j = 0; j < list2.size(); j++) {
							if(list2.get(j).get("status").equals("0")){
								if(list2.get(j).getString("voidmoney")!=null&&!list2.get(j).getString("voidmoney").equals("")){
									voidmoney =voidmoney.add(new BigDecimal( list2.get(j).getString("voidmoney")));
								}
								String parentid=(String) list2.get(j).get("parentid");
								if(parentid==null||parentid.equals("")){
									String seqId=(String) list2.get(j).get("seq_id");
									KqdsCostorderDetail kqdsCostorderDetail = ykzzDrugsInService.findCostOrderDetailByParentid(seqId);
									//判断是否全部退款
									if(kqdsCostorderDetail!=null){
										//if(Integer.parseInt(kqdsCostorderDetail.getNum())+Integer.parseInt((String) list2.get(j).get("num"))==0){
										BigDecimal paymoney = kqdsCostorderDetail.getPaymoney();
										money2=money2.add(paymoney);
										variate=1;
										//}
									}
								}
								if(variate==1){
									frequency+=1;
									variate=0;
								}
							}
						}
						if(frequency<=list2.size()&&money.add(money2).compareTo(money1)>0){
							list.get(i).put("actualmoney", money.add(money2));
							list.get(i).put("shouldmoney", money.add(money2));
							show.add(list.get(i));
						}else if(frequency<=list2.size()&&money.add(money2).compareTo(money1)==0&&voidmoney.compareTo(money1)>0){
							list.get(i).put("actualmoney", voidmoney);
							list.get(i).put("shouldmoney", voidmoney);
							show.add(list.get(i));
						}
					//有欠款状态
					}else if(money3.compareTo(money1)>0&&!list.get(i).get("actualmoney").equals(list.get(i).get("shouldmoney"))){
						for (int j = 0; j < list2.size(); j++) {
							//根据seq_id查找是否有退款
							String qfbh=(String) list2.get(j).get("qfbh");
							String parentid=(String) list2.get(j).get("parentid");
							if(parentid==null||parentid.equals("")){
								//根据qfbh查询subtotal<0的数据
								KqdsCostorderDetail kqdsCostorderDetail1 = ykzzDrugsInService.findCostOrderDetailSubtotalByQfbh(qfbh);
								if(kqdsCostorderDetail1!=null){
										BigDecimal paymoney = kqdsCostorderDetail1.getPaymoney();
										money2=money2.add(paymoney);
										variate=1;
								}
							}
							if(variate==1){
								frequency+=1;
								variate=0;
							}
						}
						if(frequency<=list2.size()&&money.add(money2).compareTo(money1)>0){
							list.get(i).put("actualmoney", money.add(money2));
							list.get(i).put("shouldmoney", money.add(money2));
							show.add(list.get(i));
						}
					}
				}
			}
			YZUtility.RETURN_LIST(show, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	/**
	 * 退药获取所有费用单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAllCostOrderReturn.act")
	public void findAllCostOrderReturn(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		// 入库查询 页面参数
		String organization = ChainUtil.getCurrentOrganization(request);
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String hzname = request.getParameter("hzname");
		String status = request.getParameter("status");
		String issend = request.getParameter("issend");
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (!YZUtility.isNullorEmpty(starttime)) {
			map.put("starttime", starttime + ConstUtil.TIME_START);
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			map.put("endtime", endtime + ConstUtil.TIME_END);
		}
		if (!YZUtility.isNullorEmpty(hzname)) {
			map.put("hzname", hzname);
		}
		if (!YZUtility.isNullorEmpty(status)) {
			map.put("status", status);
		}
		if (!YZUtility.isNullorEmpty(issend)) {
			map.put("issend", issend);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		try {			
			List<JSONObject> list = ykzzDrugsInService.findAllCostOrder(map);
			List<JSONObject> list2 = new ArrayList<JSONObject>();
			for (int i = 0; i < list.size(); i++) {
				BigDecimal money=new BigDecimal((String) list.get(i).get("actualmoney"));
				BigDecimal money1=new BigDecimal("0");
				BigDecimal money2=new BigDecimal("0");
				BigDecimal money3=new BigDecimal("0");
				BigDecimal unitprice1=new BigDecimal("0");
				if(money.compareTo(money1)>0){
					int variate=0;
					//退款次数
					int frequency=0;
					//退药数量
					int tynums=0;
					String costno=list.get(i).getString("costno");
					
					//查询明细
					List<JSONObject> list1 = ykzzDrugsInService.findCostOrderDetailByCostno(costno);
					for (int j = 0; j < list1.size(); j++) {
						if(list1.get(j).get("status").equals("0")){	
							BigDecimal unitprice=new BigDecimal((String)list1.get(j).get("unitprice"));
							BigDecimal num=new BigDecimal((String)list1.get(j).get("num"));
							String qfbh=(String) list1.get(j).get("qfbh");
							if(qfbh!=null&&!qfbh.equals("")){
								//查询状态为1的明细
								List<JSONObject> list3 = ykzzDrugsInService.findCostOrderDetailByQfbh(qfbh);
								//明细的缴费价格
								BigDecimal paymoney1=new BigDecimal((String)list1.get(j).get("paymoney"));
								for (JSONObject kqdsCostorderDetail : list3) {
									if(unitprice.multiply(num).compareTo(paymoney1.add(new BigDecimal((String)kqdsCostorderDetail.get("paymoney"))))==0){
										money3=money3.add(new BigDecimal((String)kqdsCostorderDetail.get("paymoney")));
									}
								}
							}
						}
					}
					money=money.add(money3);
					//无欠款的状态
					if(money3.compareTo(money1)==0&&list.get(i).get("actualmoney").equals(list.get(i).get("shouldmoney"))){
						for (int j = 0; j < list1.size(); j++) {
							if(list1.get(j).get("status").equals("0")){
								String parentid=(String) list1.get(j).get("parentid");
								if(parentid==null||parentid.equals("")){
									String seqId=(String) list1.get(j).get("seq_id");
									KqdsCostorderDetail kqdsCostorderDetail = ykzzDrugsInService.findCostOrderDetailByParentid(seqId);
									//判断是否全部退款
									if(kqdsCostorderDetail!=null){
										BigDecimal paymoney = kqdsCostorderDetail.getPaymoney();
										tynums+=Integer.parseInt(kqdsCostorderDetail.getNum());
										money2=money2.add(paymoney);
										variate=1;
									}
								}
								if(variate==1){
									frequency+=1;
									variate=0;
								}
							}
						}
						if(frequency<=list1.size()&&money.add(money2).compareTo(money1)>0){
							
							int nums=0;
							for (JSONObject jsonObject : list1) {
								nums+=Integer.parseInt((String)jsonObject.get("num"));
							}
							int a=0;
							for (JSONObject jsonObject2 : list1) {
								//查询
								List<JSONObject> ykzzDrugsReturnList=ykzzDrugsInService.findCostOrderDetailReturnBySeqid(jsonObject2.getString("seq_id"));
								//已退数量
								int re=0;
								for (JSONObject jsonObject : ykzzDrugsReturnList) {
									if(jsonObject!=null&&jsonObject.size()>0){
										String seqid=(String) jsonObject.get("seqid");
										KqdsCostorderDetail costorderDetail = ykzzDrugsInService.findCostOrderDetailBySeqid(seqid);
										BigDecimal unitprice = costorderDetail.getUnitprice();
										int drugsnum = Integer.parseInt((String) jsonObject.get("returndrugsnum"));
										unitprice1=unitprice1.add(unitprice.multiply(new BigDecimal(drugsnum)));
										re+=drugsnum;
									}
								}
								a+=re;
							}
							if(nums+tynums>a){
								//BigDecimal bb=;
								//System.err.println((String)list.get(i).get("unitprice"));
								//.subtract(unitprice.multiply(new BigDecimal(a))
								list.get(i).put("actualmoney", money.add(money2).subtract(unitprice1));
								list.get(i).put("shouldmoney", money.add(money2).subtract(unitprice1));
								list2.add(list.get(i));
							}
							
						}
						//有欠款状态
					}else if(money3.compareTo(money1)>0&&!list.get(i).get("actualmoney").equals(list.get(i).get("shouldmoney"))){
						for (int j = 0; j < list1.size(); j++) {
							//根据seq_id查找是否有退款
							String qfbh=(String) list1.get(j).get("qfbh");
							String parentid=(String) list1.get(j).get("parentid");
							if(parentid==null||parentid.equals("")){
								//根据qfbh查询subtotal<0的数据
								KqdsCostorderDetail kqdsCostorderDetail1 = ykzzDrugsInService.findCostOrderDetailSubtotalByQfbh(qfbh);
								if(kqdsCostorderDetail1!=null){
										BigDecimal paymoney = kqdsCostorderDetail1.getPaymoney();
										money2=money2.add(paymoney);
										tynums+=Integer.parseInt(kqdsCostorderDetail1.getNum());
										variate=1;
								}
							}
							if(variate==1){
								frequency+=1;
								variate=0;
							}
						}
						if(frequency<=list1.size()&&money.add(money2).compareTo(money1)>0){
							
							int nums=0;
							for (JSONObject jsonObject : list1) {
								nums+=Integer.parseInt((String)jsonObject.get("num"));
							}
							int a=0;
							for (JSONObject jsonObject2 : list1) {
								//查询
								List<JSONObject> ykzzDrugsReturnList=ykzzDrugsInService.findCostOrderDetailReturnBySeqid(jsonObject2.getString("seq_id"));
								//已退数量
								int re=0;
								for (JSONObject jsonObject : ykzzDrugsReturnList) {
									if(jsonObject!=null&&jsonObject.size()>0){
										String seqid=(String) jsonObject.get("seqid");
										KqdsCostorderDetail costorderDetail = ykzzDrugsInService.findCostOrderDetailBySeqid(seqid);
										BigDecimal unitprice = costorderDetail.getUnitprice();
										int drugsnum = Integer.parseInt((String) jsonObject.get("returndrugsnum"));
										unitprice1=unitprice1.add(unitprice.multiply(new BigDecimal(drugsnum)));
										re+=drugsnum;
									}
								}
								a+=re;
							}
							if(nums+tynums>a){
								list.get(i).put("actualmoney", money.add(money2).subtract(unitprice1));
								list.get(i).put("shouldmoney", money.add(money2).subtract(unitprice1));
								list2.add(list.get(i));
							}
						}
					}
				}	
			}
			YZUtility.RETURN_LIST(list2, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}

	/**
	 * 发药根据costno获取药品明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCostOrderDetailByCostno.act")
	public void findCostOrderDetailByCostno(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String costno = request.getParameter("costno");
		try {
			if(YZUtility.isNotNullOrEmpty(costno)&&!costno.equals("undefined")) {
				//查询明细
				List<JSONObject> list = ykzzDrugsInService.findCostOrderDetailByCostno(costno);
				List<JSONObject> show =new ArrayList<JSONObject>();
				String znums=null;
				for (int i = 0; i < list.size(); i++) {
					//取药数量
					String nums=(String) list.get(i).get("num");
					znums=nums;
					BigDecimal money2=new BigDecimal("0");
					int tknum=0;
					//根据seq_id查找是否有退款
					KqdsCostorderDetail kqdsCostorderDetail = ykzzDrugsInService.findCostOrderDetailByParentid((String) list.get(i).get("seq_id"));
					//判断是否全部退款
					if(kqdsCostorderDetail!=null){
						//if(Integer.parseInt(kqdsCostorderDetail.getNum())+Integer.parseInt((String) list.get(i).get("num"))==0){
							BigDecimal paymoney = kqdsCostorderDetail.getPaymoney();
							String num = kqdsCostorderDetail.getNum();
							tknum=tknum+Integer.parseInt(num);
							money2=money2.add(paymoney);
						//}
					}else if(kqdsCostorderDetail==null){
						String qfbh=(String) list.get(i).get("qfbh");
						//查询所有退款
						if(qfbh!=null&&!qfbh.equals("")){
							KqdsCostorderDetail kqdsCostorderDetail1 = ykzzDrugsInService.findCostOrderDetailSubtotalByQfbh(qfbh);
							if(kqdsCostorderDetail1!=null){
								BigDecimal paymoney = kqdsCostorderDetail1.getPaymoney();
								String num = kqdsCostorderDetail1.getNum();
								tknum=tknum+Integer.parseInt(num);
								money2=money2.add(paymoney);
							}
						}
					}
					if(Integer.parseInt(nums)+tknum>0){
						list.get(i).put("num", Integer.parseInt(nums)+tknum);
						list.get(i).put("subtotal",new BigDecimal(list.get(i).getString("unitprice")).multiply(new BigDecimal(znums)).add(money2));
						List<JSONObject> ykzzDrugsReturnList=ykzzDrugsInService.findCostOrderDetailReturnBySeqid((String) list.get(i).get("seq_id"));
						//已退数量
						int ret=0;
						String returndrugs=null;
						for (JSONObject jsonObject : ykzzDrugsReturnList) {
							if(jsonObject!=null&&jsonObject.size()>0){
								returndrugs=(String) jsonObject.get("returndrugs");
								int drugsnum = Integer.parseInt((String) jsonObject.get("returndrugsnum"));
								ret+=drugsnum;
							}
						}
						Object orderNo = list.get(i).get("itemno");
						String order_no = orderNo.toString();
						//查询入库明细表的集合
						List<YkzzDrugsInDetail> yk = ykzzDrugsInService.findBatchnumByOrderno(order_no);
						String batchno = "";
						String id = "";
						for (int j = 0; j < yk.size(); j++) {
							//商品批号
							String batchnum = yk.get(j).getBatchnum();
							batchno = batchnum + "," + batchno;
							String firstId = yk.get(j).getId();
							id = firstId + "," + id;
						}
						if(ret!=0){
							list.get(i).put("returnMoney",JSON.toJSON(new BigDecimal(ret).multiply(new BigDecimal((String)list.get(i).get("unitprice")).setScale(3,BigDecimal.ROUND_HALF_UP))));
							list.get(i).put("returndrugsnum", JSON.toJSON(ret));
							list.get(i).put("returndrugs", JSON.toJSON(returndrugs));
						}
						list.get(i).put("batchnum", JSON.toJSON(batchno));
						list.get(i).put("id", JSON.toJSON(id));
						list.get(i).put("costno", costno);
						show.add(list.get(i));
					}
				}
				//System.err.println(show);
				YZUtility.RETURN_LIST(show, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	/**
	 * 退药根据costno获取药品明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCostOrderDetailByCostno1.act")
	public void findCostOrderDetailByCostno1(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String costno = request.getParameter("costno");
		try {
			if(YZUtility.isNotNullOrEmpty(costno)) {
				//查询出费用明细表的详情
				
				List<JSONObject> list = ykzzDrugsInService.findCostOrderDetailByCostno(costno);
				List<JSONObject> list1=new ArrayList<JSONObject>();
				
				for (int i = 0; i < list.size(); i++) {
					//取药数量
					String nums=(String) list.get(i).get("num");
					BigDecimal money2=new BigDecimal("0");
					BigDecimal unitprice1=new BigDecimal("0");
					int tknum=0;
					//根据seq_id查找是否有退款
					KqdsCostorderDetail kqdsCostorderDetail = ykzzDrugsInService.findCostOrderDetailByParentid((String) list.get(i).get("seq_id"));
					//判断是否全部退款
					if(kqdsCostorderDetail!=null){
						//if(Integer.parseInt(kqdsCostorderDetail.getNum())+Integer.parseInt((String) list.get(i).get("num"))==0){
							BigDecimal paymoney = kqdsCostorderDetail.getPaymoney();
							String num = kqdsCostorderDetail.getNum();
							tknum=tknum+Integer.parseInt(num);
							money2=money2.add(paymoney);
						//}
					}else if(kqdsCostorderDetail==null){
						String qfbh=(String) list.get(i).get("qfbh");
						//查询所有退款
						if(qfbh!=null&&!qfbh.equals("")){
							KqdsCostorderDetail kqdsCostorderDetail1 = ykzzDrugsInService.findCostOrderDetailSubtotalByQfbh(qfbh);
							if(kqdsCostorderDetail1!=null){
								BigDecimal paymoney = kqdsCostorderDetail1.getPaymoney();
								String num = kqdsCostorderDetail1.getNum();
								tknum=tknum+Integer.parseInt(num);
								money2=money2.add(paymoney);
							}
						}
					}
					if(Integer.parseInt(nums)+tknum>0){
						list.get(i).put("num", Integer.parseInt(nums)+tknum);
						list.get(i).put("subtotal",new BigDecimal(list.get(i).getString("unitprice")).multiply(new BigDecimal(list.get(i).getString("num"))).add(money2));
						String bat=(String) list.get(i).get("batchno");
						List<JSONObject> ykzzDrugsReturnList=ykzzDrugsInService.findCostOrderDetailReturnBySeqid((String) list.get(i).get("seq_id"));
						//已退数量
						int ret=0;
						for (JSONObject jsonObject : ykzzDrugsReturnList) {
							if(jsonObject!=null&&jsonObject.size()>0){
								String seqid=(String) jsonObject.get("seqid");
								KqdsCostorderDetail costorderDetail = ykzzDrugsInService.findCostOrderDetailBySeqid(seqid);
								BigDecimal unitprice = costorderDetail.getUnitprice();
								int drugsnum = Integer.parseInt((String) jsonObject.get("returndrugsnum"));
								unitprice1=unitprice1.add(unitprice.multiply(new BigDecimal(drugsnum)));
								ret+=drugsnum;
							}
						}
						//退药数量
						int a=(int) list.get(i).get("num");
						if((a-ret)>0){
							//药品编号
							Object orderNo = list.get(i).get("itemno");
							String order_no = orderNo.toString();
							//根据药品编号查询出药品入库明细
							List<YkzzDrugsInDetail> yk = ykzzDrugsInService.findBatchnumByOrderno1(order_no);
							String id = "";
							for (int j = 0; j < yk.size(); j++) {
								//批号
								String batchnum = yk.get(j).getBatchnum();
								if(batchnum!=null&&!batchnum.equals("")){
									if(batchnum.equals(bat)){
										id = yk.get(j).getId();
									}
								}
							}
							list.get(i).put("subtotal", ((BigDecimal) list.get(i).get("subtotal")).subtract(unitprice1).toString());
							list.get(i).put("num", a-ret);
							list.get(i).put("id", JSON.toJSON(id));
							list.get(i).put("costno", costno);
							list1.add(list.get(i));
						}
						
					}
				}
				YZUtility.RETURN_LIST(list1, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	/**
	 * 发药
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/grantDrugs.act")
	public void grantDrugs(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String organization=ChainUtil.getCurrentOrganization(request);
		String costno = request.getParameter("costno");
		//批号
		String[] batchnoNum = request.getParameterValues("selectBatchnumAll");
		//批号的id
		String[] seqId = request.getParameterValues("selectIdAll");
		//费用明细表id
		String[] costseqIdArr = request.getParameterValues("costseqIdArr");
		try {
			
			if(YZUtility.isNullorEmpty(costno)||costno.equals("")||seqId==null||seqId[0].equals("")||costseqIdArr==null||costseqIdArr[0].equals("")||batchnoNum==null||batchnoNum[0].equals("")) {
				throw new Exception("请选择药品批号！");
			}
			ykzzDrugsInService.grantDrugs(organization,costno,batchnoNum,seqId,costseqIdArr,request);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, logger);
		}
	}
	/**
	 * 退药
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/returnDrugs.act")
	public void returnDrugs(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String batchnoNum = request.getParameter("selectBatchnumAll");
		String seqId = request.getParameter("selectIdAll");
		String costseqIdArr = request.getParameter("costseqIdArr");
		String outnum = request.getParameter("outnum");
		//根据人员信息查询出部门id，根据部门id查询对应的数据信息
		YZPerson person = SessionUtil.getLoginPerson(request);
		try {
			ykzzDrugsInService.returnDrugs(batchnoNum,seqId,costseqIdArr,outnum,person);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, logger);
		}
	}
	/**
	 * 判断当前人员是否是药库管理员
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/checkCurrentIsAdmin.act")
	public void checkCurrentIsAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		YZPerson person = SessionUtil.getLoginPerson(request);
		try {
			List<JSONObject> adminObj = ykzzDrugsInService.findDrugsInAdmin(request);
			String result = "false";
			JSONObject jo = new JSONObject();
			if(null != adminObj && adminObj.size() >= 1) {
				String drugsAdmin = adminObj.get(0).getString("para_value");
				jo.put("adminName", drugsAdmin);
				if(drugsAdmin.contains(person.getUserId())) {
					result = "true";
				}else {
					result = "false";
				}
			}else {
				result = "false";
			}
			jo.put("result", result);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, logger);
		}
	}
	
	/**
	 * 根据药品编号查询批号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/findBatchnumByOrderno.act")
	public String findBatchnumByOrderno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String order_no = request.getParameter("order_no");
		try {
			List<YkzzDrugsInDetail> list = ykzzDrugsInService.findBatchnumByOrderno(order_no);
			JSONObject json = new JSONObject();
			json.put("batchnum", list);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id查询药品入库明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/findYkzzDrugsInDatailByInDetail.act")
	public String findYkzzDrugsInDetailByInDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String inDetail = request.getParameter("id");
		try {
			YkzzDrugsInDetail ykzzIndetail = ykzzDrugsInService.findYkzzDrugsInDatailByInDetail(inDetail);
			JSONObject json = new JSONObject();
			json.put("data", ykzzIndetail);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	 * 2019-08-14 lwg 根据药品编号orderno展示批号详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toYkzzDrugsInDetailByOrderno.act")
	public ModelAndView toYkzzDrugsInDetailByOrderno(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String orderno=request.getParameter("orderno");
		List<JSONObject> list = ykzzDrugsInService.findYkzzDrugsInDetailByOrderno(orderno); 
		String batchnum="";
		int batchnums=0;
		List<JSONObject> list1=new ArrayList<JSONObject>();
		for (JSONObject jsonObject : list) {
			if(jsonObject.getString("batchnum")!=null&&!jsonObject.getString("batchnum").equals("")){
				String outnum="0";
				String batchnumSaveOutNum="0";
				if(!batchnum.equals(jsonObject.getString("batchnum"))){
					outnum=ykzzDrugsOutService.findOutNumByOrderno(orderno, jsonObject.getString("batchnum"));
					batchnumSaveOutNum=ykzzDrugsOutService.findBatchnumSaveOutNumsByOrdernoAndBatchnum(orderno, jsonObject.getString("batchnum"));
					JSONObject json = new JSONObject();
					json.put("barchno", jsonObject.getString("batchnum"));
					json.put("batchoutnum", Integer.parseInt(outnum)+Integer.parseInt(batchnumSaveOutNum));
					list1.add(json);
					jsonObject.put("outnum",jsonObject.getInt("quantity")-jsonObject.getInt("batchnonum"));
				}else{
					jsonObject.put("outnum",0);
				}
				batchnum=jsonObject.getString("batchnum");
				batchnums+=Integer.parseInt(outnum)+Integer.parseInt(batchnumSaveOutNum);
			}
		}
		//查找当天所有的出库数量
		String outnums=ykzzDrugsOutService.findOutNumsByAll();
		String batchnumSaveOutNums=ykzzDrugsOutService.findOutNumsByBatchnumSave();
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.addObject("batchlist", list1);
		mv.addObject("batchnums", batchnums+"");
		mv.addObject("outnums", Integer.parseInt(outnums)+Integer.parseInt(batchnumSaveOutNums)+"");
		mv.setViewName("/hudh/ykzz/drugsIn/drugs_detail.jsp");
		return mv;
	}
}
