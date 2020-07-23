package com.kqds.controller.base.paycost;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hudh.dzbl.dao.DzblDetailDao;
import com.hudh.dzbl.dao.DzblTemplateDao;
import com.kqds.entity.base.KqdsPaycost;
import com.kqds.service.base.chufang.KQDS_ChuFangLogic;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.CacheUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("Kqds_PayCostAct")
public class Kqds_PayCostAct {

	private static Logger logger = LoggerFactory.getLogger(Kqds_PayCostAct.class);
	@Autowired
	private Kqds_PayCostLogic logic;
	@Autowired
	private KQDS_UserDocumentLogic userLogic;
	@Autowired
	private KQDS_REGLogic regLogic;
	@Autowired
	private KQDS_CostOrderLogic costOrderLogic;
	@Autowired
	private KQDS_CostOrder_DetailLogic costOrderDetailLogic;
	@Autowired
	private KQDS_ChuFangLogic chuFangLogic;
	@Autowired
	private DzblDetailDao dzblDetailDao;
	@RequestMapping(value = "/toXfjlWin.act")
	public ModelAndView toXfjlWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/coatOrder/xfjlWin.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCostListing.act")
	public ModelAndView toCostListing(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String regno = request.getParameter("regno");
		String costno = request.getParameter("costno");
		String usercode = request.getParameter("usercode");
		String username=request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("regno", regno);
		mv.addObject("costno", costno);
		mv.addObject("usercode", usercode);
		mv.addObject("username",username);
		mv.setViewName("/kqdsFront/coatOrder/cost_listing.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCostListingPrint.act")
	public ModelAndView toCostListingPrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String costno = request.getParameter("costno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("costno", costno);
		mv.setViewName("/kqdsFront/coatOrder/cost_listing_print.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCost_EditPaymoney.act")
	public ModelAndView toCost_EditPaymoney(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/coatOrder/cost_editPaymoney.jsp");
		return mv;
	}
	/**
	  * @Title: tocw_invoice.jsp   
	  * @Description: TODO(跳转开发票页)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年9月3日 上午11:20:50
	 */
	@RequestMapping(value = "/tocw_invoice.act")
	public ModelAndView tocw_invoice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/jdzx/cw_invoice.jsp");
		return mv;
	}

	/**
	 * 结账
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			KqdsPaycost dp = new KqdsPaycost();
			BeanUtils.populate(dp, request.getParameterMap());
			logic.payMoney(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
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
			KqdsPaycost en = (KqdsPaycost) logic.loadObjSingleUUID(TableNameUtil.KQDS_PAYCOST, seqId);
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
	 * util.js调用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailByCostNo.act")
	public String selectDetailByCostNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String costno = request.getParameter("costno");

			Map<String, String> map = new HashMap<String, String>();
			map.put("costno", costno);
			List<KqdsPaycost> newen = (List<KqdsPaycost>) logic.loadList(TableNameUtil.KQDS_PAYCOST, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", newen.get(0));
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 根据usercode 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailByUsercode.act")
	public String selectDetailByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");

			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", usercode);
			List<KqdsPaycost> newen = (List<KqdsPaycost>) logic.loadList(TableNameUtil.KQDS_PAYCOST, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", newen);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 结账时如果全是还款项目 把上一次的医生带入 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDoctor.act")
	public String selectDoctor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String doctor = userLogic.selectDoctorByusercode(TableNameUtil.KQDS_PAYCOST, usercode);
			JSONObject jobj = new JSONObject();
			jobj.put("data", doctor);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	/**
	 * 添加缓存数据
	 * 2019-09-01 lwg
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/transferringData.act")
	public String transferringData(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String usercode= request.getParameter("usercodes");
		//String [] stringArr= usercode.substring(1,usercode.length()-1).split("\""); 
		String [] stringArr= usercode.substring(1,usercode.length()-1).split(","); 
		//查询所有数据
		List<String> list= new ArrayList<String>();
		for (String string : stringArr) {
			list.add(string.replaceAll("\"","\'"));
		}
		try {
			//查询患者的所有建档信息
			List<JSONObject> list1 = userLogic.findKqdsUserdocumentByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查询患者的所有挂号信息
			List<JSONObject> list2=regLogic.findKqdsRegByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查询患者的所有缴费信息
			List<JSONObject> list3=costOrderLogic.findCostOrderByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查找患者的所有缴费明细信息
			List<JSONObject> list4=costOrderDetailLogic.findCostOrderDetailByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查找患者的所有退单信息
			List<JSONObject> list5=costOrderLogic.findCostOrderTuidanByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查找患者的所有退单明细
			List<JSONObject> list6=costOrderDetailLogic.findCostOrderDetailTuidanByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查找患者的所有处方
			List<JSONObject> list7=chuFangLogic.findChuFangByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查找患者的所有处方明细
			List<JSONObject> list8=chuFangLogic.findChuFangDetailByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查找患者的所有费用信息
			List<JSONObject> list9=logic.findPayCostByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查找患者的所有病历信息
			List<JSONObject> list10=dzblDetailDao.findDzblDetailByUsercodes(StringUtils.strip(list.toString(),"[]"));
			//查找患者医生的所有就诊情况
			
			CacheUtil.openCache();
			//CacheUtil.queryVisitArticleStatistics(0);
			if(list1!=null&&list1.size()>0){
				
				CacheUtil.addZSet("KqdsUserdocument:key", list1.get(0).getString("usercode"),new Date().getTime());
				Map<String,List<JSONObject>> key1=new HashMap<String,List<JSONObject>>();
				key1.put(list1.get(0).getString("usercode"), list1);
				CacheUtil.setMap("KqdsUserdocument:value", key1);
			}
			
			if(list2!=null&&list2.size()>0){
				CacheUtil.addZSet("KqdsReg:key", list2.get(0).get("usercode"),new Date().getTime());
				Map<String,List<JSONObject>> key2=new HashMap<String,List<JSONObject>>();
				key2.put(list2.get(0).getString("usercode"), list2);
				CacheUtil.setMap("KqdsReg:value", key2);
				
			}
			
			if(list3!=null&&list3.size()>0){
				CacheUtil.addZSet("KqdsCostOrder:key", list3.get(0).get("usercode"),new Date().getTime());
				Map<String,List<JSONObject>> key3=new HashMap<String,List<JSONObject>>();
				key3.put(list3.get(0).getString("usercode"), list3);
				CacheUtil.setMap("KqdsCostOrder:value", key3);
			}
			
			if(list4!=null&&list4.size()>0){
				CacheUtil.addZSet("KqdsCostOrderDetail:key", list4.get(0).get("usercode"),new Date().getTime());
				Map<String,List<JSONObject>> key4=new HashMap<String,List<JSONObject>>();
				key4.put(list4.get(0).getString("usercode"), list4);
				CacheUtil.setMap("KqdsCostOrderDetail:value", key4);
			}
			
			if(list5!=null&&list5.size()>0){
				CacheUtil.addZSet("KqdsCostOrderTuidan:key", list5.get(0).get("usercode"),new Date().getTime());
				Map<String,List<JSONObject>> key5=new HashMap<String,List<JSONObject>>();
				key5.put(list5.get(0).getString("usercode"), list5);
				CacheUtil.setMap("KqdsCostOrderTuidan:value", key5);
			}
			
			if(list6!=null&&list6.size()>0){
				CacheUtil.addZSet("KqdsCostOrderDetailTuidan:key", list6.get(0).get("usercode"),new Date().getTime());
				Map<String,List<JSONObject>> key6=new HashMap<String,List<JSONObject>>();
				key6.put(list6.get(0).getString("usercode"), list6);
				CacheUtil.setMap("KqdsCostOrderDetailTuidan:value", key6);
				
			}
			if(list7!=null&&list7.size()>0){
				CacheUtil.addZSet("KqdsChuFang:key", list7.get(0).get("usercode"),new Date().getTime());
				Map<String,List<JSONObject>> key7=new HashMap<String,List<JSONObject>>();
				key7.put(list7.get(0).getString("usercode"), list7);
				CacheUtil.setMap("KqdsChuFang:value", key7);
				
			}
			
			if(list8!=null&&list8.size()>0){
				CacheUtil.addZSet("KqdsChuFangDetail:key", list8.get(0).get("usercode"),new Date().getTime());
				Map<String,List<JSONObject>> key8=new HashMap<String,List<JSONObject>>();
				key8.put(list8.get(0).getString("usercode"), list8);
				CacheUtil.setMap("KqdsChuFangDetail:value", key8);
				
			}
			
			if(list9!=null&&list9.size()>0){
				CacheUtil.addZSet("KqdsPayCost:key", list9.get(0).get("usercode"),new Date().getTime());
				Map<String,List<JSONObject>> key9=new HashMap<String,List<JSONObject>>();
				key9.put(list9.get(0).getString("usercode"), list9);
				CacheUtil.setMap("KqdsPayCost:value", key9);
			}
			
			if(list10!=null&&list10.size()>0){
				CacheUtil.addZSet("HudhDzblDetail:key", list10.get(0).get("blcode"),new Date().getTime());
				Map<String,List<JSONObject>> key10=new HashMap<String,List<JSONObject>>();
				key10.put(list10.get(0).getString("blcode"), list10);
				CacheUtil.setMap("HudhDzblDetail:value", key10);
			}
			
			CacheUtil.close();
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

    /**
     * 根据挂号id查询是否有结账记录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/findCountByRegno.act")
	public String findCountByRegno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String regno = request.getParameter("regno");
			int i=logic.findCountByRegno(regno);
			if(i>0){
				YZUtility.DEAL_SUCCESS_VALID(false,response);
			}
			YZUtility.DEAL_SUCCESS_VALID(true,response);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}