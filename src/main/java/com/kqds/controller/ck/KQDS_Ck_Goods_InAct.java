package com.kqds.controller.ck;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Session;
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
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsIn;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_Goods_InLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_In_DetailLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_OutLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_Ck_Goods_InAct")
public class KQDS_Ck_Goods_InAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_Goods_InAct.class);
	@Autowired
	private KQDS_Ck_Goods_InLogic logic;
	@Autowired
	private KQDS_Ck_Goods_OutLogic outlogic;
	@Autowired
	private KQDS_Ck_Goods_In_DetailLogic detailLogic;
	
	@RequestMapping(value = "/toCkGoodsIn.act")
	public ModelAndView toCkDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/goodsIn/in_goods.jsp");
		return mv;
	}
	@RequestMapping(value = "/toCkGoodsIn2.act")
	public ModelAndView toCkGoodsIn2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/childhouse/inGoods/in_goods.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCkInGoodsSearch.act")
	public ModelAndView toCkInGoodsSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/search/in_goods_search.jsp");
		return mv;
	}
	
	/**
	 * 明细编辑
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCkInGoodsDetail.act")
	public ModelAndView toCkInGoodsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String goodsinid = request.getParameter("goodsinid");
		mv.addObject("goodsinid", goodsinid);
		mv.setViewName("/kqdsFront/ck/goodsIn/editInGoodsDetail.jsp");
		return mv;
	}

	/**
	 * 入库
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
			KqdsCkGoodsIn dp = new KqdsCkGoodsIn();
			BeanUtils.populate(dp, request.getParameterMap());
			String menzhen = ChainUtil.getCurrentOrganization(request);
			// 验证入库单号是否重复
			Map<String, String> mapin = new HashMap<String, String>();
			mapin.put("incode", dp.getIncode());
			List<KqdsCkGoodsIn> inList = (List<KqdsCkGoodsIn>) logic.loadList(TableNameUtil.KQDS_CK_GOODS_IN, mapin);
			if (inList.size() > 0) {
				JSONObject retrunObj = new JSONObject();
				retrunObj.put(YZActionKeys.JSON_RET_STATES, "100"); // ###
																	// 特殊处理
				retrunObj.put(YZActionKeys.JSON_RET_MSRGS, "入库单号已存在，系统已自动重新获取");
				PrintWriter pw = response.getWriter();
				pw.println(retrunObj.toString());
				pw.flush();

				return null;
			}
			dp.setSeqId(YZUtility.getUUID());
			dp.setOrganization(menzhen);
			// 现在入库不需要审核功能，暂把审核、入库、申请 都一致保存申请的人及时间；状态设为3（已入库）
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			dp.setShtime(dp.getCreatetime());
			dp.setAuditor(person.getSeqId());
			// dp.setRktime(dp.getCreatetime());
			dp.setRkr(person.getSeqId());
			dp.setInstatus(3);
			dp.setCheck_status(0);//用于商品入库审核
			dp.setStatus(0);
			logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, dp);

			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_IN, dp, TableNameUtil.KQDS_CK_GOODS_IN, request);

			//插入商品入库明细
			String params = request.getParameter("paramDetail");
			params = java.net.URLDecoder.decode(params, "UTF-8");
			JSONArray jArray = JSONArray.fromObject(params);
			List<KqdsCkGoodsInDetail> jList = (List<KqdsCkGoodsInDetail>) JSONArray.toCollection(jArray, KqdsCkGoodsInDetail.class);
			for (KqdsCkGoodsInDetail detail : jList) {
				detail.setSeqId(YZUtility.getUUID());
				detail.setIncode(dp.getIncode());
				detail.setOrganization(menzhen);
				detail.setCreateuser(person.getSeqId());
				detail.setCreatetime(dp.getCreatetime());
				detail.setGoodsInSeqId(dp.getSeqId());
				detail.setAuditStatus(0);//用于商品入库审核
				if(detail.getInnum()!=null){
					detail.setPhnum(detail.getInnum());
				}
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, detail);
				// 记录入库日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_IN_DETAIL, detail, TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, request);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", dp.getSeqId());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 修改入库单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editGoodsIn.act")
	public String editGoodsIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 取值
			String rkddata = request.getParameter("rkddata");
			String rkdetaildata = request.getParameter("rkdetaildata");
			String goodsdata = request.getParameter("goodsdata");
			// --------------------保存入库单
			JSONObject jsonobj = JSONObject.fromObject(rkddata);
			KqdsCkGoodsIn inobj = (KqdsCkGoodsIn) JSONObject.toBean(jsonobj, KqdsCkGoodsIn.class);
			// 根据Id查询入库单
			KqdsCkGoodsIn goodsin = (KqdsCkGoodsIn) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, inobj.getSeqId());
			if (goodsin == null) {
				throw new Exception("入库单不存在");
			}
			String oldhouse = goodsin.getInhouse();// 原仓库
			String nowhouse = inobj.getInhouse();// 现手术室
			goodsin.setIntype(inobj.getIntype());
			goodsin.setSupplier(inobj.getSupplier());
			goodsin.setInhouse(inobj.getInhouse());
			goodsin.setInremark(inobj.getInremark());
			goodsin.setZhaiyao(inobj.getZhaiyao());
			logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, goodsin);

			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_GOODS_IN, goodsin, TableNameUtil.KQDS_CK_GOODS_IN, request);

			// --------------------保存入库明细
			JSONObject jsonobj2 = JSONObject.fromObject(rkdetaildata);
			KqdsCkGoodsInDetail inobjdetail = (KqdsCkGoodsInDetail) JSONObject.toBean(jsonobj2, KqdsCkGoodsInDetail.class);
			// 根据Id查询入库单
			KqdsCkGoodsInDetail goodsinDetal = (KqdsCkGoodsInDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, inobjdetail.getSeqId());
			if (goodsinDetal == null) {
				throw new Exception("入库单明细不存在");
			}
			int oldinnum = goodsinDetal.getInnum();// 原入库数量
			BigDecimal oldrkmoney = goodsinDetal.getRkmoney();// 原入库金额
			goodsinDetal.setInnum(inobjdetail.getInnum());
			goodsinDetal.setInprice(inobjdetail.getInprice());
			goodsinDetal.setRkmoney(inobjdetail.getRkmoney());
			goodsinDetal.setYxdate(inobjdetail.getYxdate());
			goodsinDetal.setSqremark(inobjdetail.getSqremark());
			logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, goodsinDetal);

			// 记录入库日志
			BcjlUtil.LogBcjl(BcjlUtil.UPDATE, BcjlUtil.KQDS_CK_GOODS_IN_DETAIL, goodsinDetal, TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, request);

			// --------------------保存商品结存
			JSONObject jsonobj4 = JSONObject.fromObject(goodsdata);
			KqdsCkGoods goodsobj = (KqdsCkGoods) JSONObject.toBean(jsonobj4, KqdsCkGoods.class);
			// 根据Id查询入库单
			KqdsCkGoods goods = (KqdsCkGoods) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS, goodsobj.getSeqId());
			if (goods == null) {// 不存在说明之前new仓库没有该商品的库存
				// 新建
				goods = new KqdsCkGoods();
				// 保存该入库商品所属仓库
				goods.setSeqId(YZUtility.getUUID());
				goods.setGoodsdetailid(goodsinDetal.getGoodsuuid());
				goods.setSshouse(nowhouse);
				goods.setGoodsprice(goodsinDetal.getInprice());
				goods.setKcmoney(goodsinDetal.getRkmoney());
				goods.setNums(goodsinDetal.getInnum());
				goods.setOrganization(goodsinDetal.getOrganization());
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
			} else {
				goods.setGoodsprice(goodsobj.getGoodsprice());
				goods.setNums(goodsobj.getNums());
				goods.setKcmoney(goodsobj.getKcmoney());
				logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
			}
			// ***************************如果修改入库商品所属的仓库************************
			// 从原仓库的库存中减掉本次入库的数量及金额。
			if (!oldhouse.equals(nowhouse)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sshouse", oldhouse);
				map.put("goodsdetailid", goods.getGoodsdetailid());
				List<KqdsCkGoods> en = (List<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
				KqdsCkGoods oldgoods = en.get(0);
				oldgoods.setNums(oldgoods.getNums() - oldinnum);
				oldgoods.setKcmoney(oldgoods.getKcmoney().subtract(oldrkmoney));
				BigDecimal goodsprice = null;
				if (oldgoods.getNums() == 0) {// 出库后，库存为0
					goodsprice = BigDecimal.ZERO;
				} else {
					goodsprice = oldgoods.getKcmoney().divide(new BigDecimal(oldgoods.getNums()), 3, RoundingMode.HALF_EVEN).setScale(3, BigDecimal.ROUND_HALF_DOWN);
				}
				oldgoods.setGoodsprice(goodsprice);
				logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, oldgoods);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除入库单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAllGoodsIn.act")
	public String deleteAllGoodsIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String inseqId = request.getParameter("inseqId");
			KqdsCkGoodsIn goodsin = (KqdsCkGoodsIn) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, inseqId);
			if (goodsin == null) {
				throw new Exception("入库单不存在");
			}
			// 判断改入库单下是否还存在其他入库明细；不存在则删除该入库单
			Map<String, String> map = new HashMap<String, String>();
			map.put("incode", goodsin.getIncode());
			List<KqdsCkGoodsInDetail> detailList = (List<KqdsCkGoodsInDetail>) logic.loadList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, map);

			// --------------------删除入库明细
			if (detailList == null || detailList.size() == 0) {
				throw new Exception("入库单明细不存在");
			}
			for (KqdsCkGoodsInDetail detail : detailList) {
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("sshouse", goodsin.getInhouse());
				map2.put("goodsdetailid", detail.getGoodsuuid());
				List<KqdsCkGoods> goodsList = (List<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map2);
				if (goodsList == null || goodsList.size() == 0) {// 不存在说明之前new仓库没有该商品的库存
					throw new Exception("库存信息不存在");
				}
				KqdsCkGoods goods = goodsList.get(0);

				/*** 增加判断，库存数量必须大于删除的入库数量 **/
				/*
				 * int willKCNums = goods.getNums() - detail.getInnum(); // 入库删除后的库存数 if
				 * (willKCNums < 0) { throw new Exception("库存数量不足！"); }
				 */

				// 删除入库单
				logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, goodsin.getSeqId());
				logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, detail.getSeqId());
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_IN_DETAIL, detail, TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, request);

				goods.setNums(goods.getNums() - detail.getInnum());
				goods.setKcmoney(goods.getKcmoney().subtract(detail.getRkmoney()));
				BigDecimal goodsprice = BigDecimal.ZERO;
				if (goods.getNums() != 0) {
					goodsprice = goods.getKcmoney().divide(new BigDecimal(goods.getNums()), 3, RoundingMode.HALF_EVEN).setScale(3, BigDecimal.ROUND_HALF_DOWN);
				}
				goods.setGoodsprice(goodsprice);
				logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除入库单明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGoodsIn.act")
	public String deleteGoodsIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 取值
			String indetailseqId = request.getParameter("indetailseqId");
			String inseqId = request.getParameter("inseqId");
			String goodsseqId = request.getParameter("goodsseqId");
			// --------------------删除入库明细
			KqdsCkGoodsInDetail goodsinDetal = (KqdsCkGoodsInDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, indetailseqId);
			if (goodsinDetal == null) {
				throw new Exception("入库单明细不存在");
			}

			KqdsCkGoods goods = (KqdsCkGoods) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS, goodsseqId);
			if (goods == null) {// 不存在说明之前new仓库没有该商品的库存
				throw new Exception("库存信息不存在");
			}

			/*** 增加判断，库存数量必须大于删除的入库数量 **/
			/*
			 * int willKCNums = goods.getNums() - goodsinDetal.getInnum(); // 入库删除后的库存数 if
			 * (willKCNums < 0) { throw new Exception("库存数量不足！"); }
			 */

			logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, goodsinDetal.getSeqId());
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_IN_DETAIL, goodsinDetal, TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, request);

			// 判断改入库单下是否还存在其他入库明细；不存在则删除该入库单
			Map<String, String> map = new HashMap<String, String>();
			map.put("incode", goodsinDetal.getIncode());
			List<KqdsCkGoodsInDetail> en = (List<KqdsCkGoodsInDetail>) logic.loadList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, map);
			if (en == null || en.size() == 0) {// 不存在则删除该入库单
				// --------------------删除入库单
				KqdsCkGoodsIn goodsin = (KqdsCkGoodsIn) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, inseqId);
				if (goodsin == null) {
					throw new Exception("入库单不存在");
				}
				logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, inseqId);
			}
			// --------------------保存商品结存
			goods.setNums(goods.getNums() - goodsinDetal.getInnum());
			goods.setKcmoney(goods.getKcmoney().subtract(goodsinDetal.getRkmoney()));
			BigDecimal goodsprice = BigDecimal.ZERO;
			if (goods.getNums() != 0) {
				goodsprice = goods.getKcmoney().divide(new BigDecimal(goods.getNums()), 3, RoundingMode.HALF_EVEN).setScale(3, BigDecimal.ROUND_HALF_DOWN);
			}
			goods.setGoodsprice(goodsprice);
			logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据明细表id删除信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGoodsInDetailById.act")
	public String deleteGoodsInDetailById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("indetailseqId");
		try {
			detailLogic.deleteGoodsInDetailById(id);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);;
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

	/**
	 * 入库查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/inSearchList.act")
	public String inSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 入库查询 页面参数
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String inhouse = request.getParameter("inhouse");
			String intype = request.getParameter("intype");
			String supplier = request.getParameter("supplier");
			String incode = request.getParameter("incode");
			String check_status = request.getParameter("check_status");
			String stock_starttime = request.getParameter("stock_starttime");
			String stock_endtime = request.getParameter("stock_endtime");
			String type = request.getParameter("type");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(inhouse)) {
				map.put("inhouse", inhouse);
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
			if (!YZUtility.isNullorEmpty(check_status)) {
				map.put("check_status", check_status);
			}
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // 当前所在门诊
			List<JSONObject> list = logic.inSearchList(TableNameUtil.KQDS_CK_GOODS_IN, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/selectBySupplier.act")
	public String selectBySupplier(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String supplierid = request.getParameter("supplierid");
			String organization = ChainUtil.getCurrentOrganization(request);
			int in = logic.countBySupplier(supplierid, organization);
			int out = outlogic.countBySupplier(supplierid, organization);
			boolean flag = (in + out) > 0 ? false : true;
			JSONObject jobj = new JSONObject();
			jobj.put("data", flag);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/selectZczh.act")
	public String selectZczh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String goodsid = request.getParameter("goodsid");
			String organization = ChainUtil.getCurrentOrganization(request);
			String zczh = logic.selectZczh(goodsid, organization);
			String inprice = logic.selectInprice(goodsid, organization);
			String cd = logic.selectCd(goodsid, organization);
			JSONObject jobj = new JSONObject();
			jobj.put("zczh", zczh);
			jobj.put("inprice", inprice);
			jobj.put("cd", cd);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	/**
	 * 判断当前人员是否是仓库管理员（2019-6-3） syp
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkCurrentIsAdmin.act")
	public String checkCurrentIsAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			List<JSONObject> adminObj = logic.getParaValueListByName(request, organization);
			String result = "false";
			JSONObject jo = new JSONObject();
			for (JSONObject jsonObject : adminObj) {
				if(jsonObject != null){
					String drugsAdmin = jsonObject.getString("para_value");
					jo.put("adminName", drugsAdmin);
					if(drugsAdmin.contains(person.getUserId())) {
						result = "true";
					}else {
						result = "false";
					}
				}else {
					result = "false";
				}
			}
			/*for (int i = 0; i < adminObj.size(); i++) {//添加分院仓库审核人员
				if(null != adminObj && adminObj.size() >= 1) {
					String drugsAdmin = adminObj.get(i).getString("para_value");
					jo.put("adminName", drugsAdmin);
					if(drugsAdmin.contains(person.getUserId())) {
						result = "true";
					}else {
						result = "false";
					}
				}else {
					result = "false";
				}
			}*/
			jo.put("result", result);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: checkIsAdmin   
	  * @Description: TODO(判断当前人员是否是仓库管理员)   duoyh
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年12月9日 下午5:04:50
	 */
	@RequestMapping(value = "/checkIsAdmin.act")
	public String checkIsAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			JSONObject adminObj = logic.getParaValueByName(request, organization);
			String result = "false";
			JSONObject jo = new JSONObject();
			if(adminObj != null){
				String drugsAdmin = adminObj.getString("para_value");
				jo.put("adminName", drugsAdmin);
				if(drugsAdmin.contains(person.getUserId())) {
					result = "true";
				}else {
					result = "false";
				}
			}else{
				result = "false";
			}
			
			
			jo.put("result", result);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/goodsInSelectList.act")
	public String goodsInSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 入库查询 页面参数
			String type = request.getParameter("type");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String inhouse = request.getParameter("inhouse");
			String intype = request.getParameter("intype");
			String supplier = request.getParameter("supplier");
			String incode = request.getParameter("incode");
			String check_status = request.getParameter("check_status");
			String status = request.getParameter("status");
			
			String stock_starttime = request.getParameter("stock_starttime");
			String stock_endtime = request.getParameter("stock_endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(inhouse)) {
				map.put("inhouse", inhouse);
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
			if (!YZUtility.isNullorEmpty(check_status)) {
				map.put("check_status", check_status);
			}
			if (!YZUtility.isNullorEmpty(status)) {
				map.put("status", status);
			}
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // 当前所在门诊
			List<JSONObject> list = logic.goodsInSelectList(TableNameUtil.KQDS_CK_GOODS_IN, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	/**
	 * 删除入库时填写有问题的数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGoodsInById.act")
	public String deleteGoodsInById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seq_id = request.getParameter("seq_id");
		try {
			logic.deleteGoodsInById(seq_id);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据入库表id查询明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCkGoodsDetailByInid.act")
	public String selectCkGoodsDetailByInid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String goodsInSeqId = request.getParameter("goodsInSeqId");
		try {
			List<KqdsCkGoodsInDetail> list = detailLogic.findCkGoodsDetailByInid(goodsInSeqId);
		    JSONArray jsonArray = JSONArray.fromObject(list);
			YZUtility.RETURN_LIST(jsonArray, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 修改入库明细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editGoodsInDetail.act")
	public String editGoodsInDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 取值
		//String rkddata = request.getParameter("rkddata");
		String rkdetaildata = request.getParameter("rkdetaildata");
		YZPerson person = SessionUtil.getLoginPerson(request);
		//String goodsdata = request.getParameter("goodsdata");
		// --------------------保存入库单
		//JSONObject jsonobj = JSONObject.fromObject(rkddata);
		//KqdsCkGoodsIn inobj = (KqdsCkGoodsIn) JSONObject.toBean(jsonobj, KqdsCkGoodsIn.class);
		JSONObject jsonobjDetail = JSONObject.fromObject(rkdetaildata);
		KqdsCkGoodsInDetail dp = (KqdsCkGoodsInDetail) JSONObject.toBean(jsonobjDetail, KqdsCkGoodsInDetail.class);
		// 根据Id查询入库单
		//KqdsCkGoodsIn goodsin = (KqdsCkGoodsIn) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, inobj.getSeqId());
		try {
			KqdsCkGoodsInDetail list = detailLogic.selectCkGoodsDetailByInid(dp.getSeqId());
			list.setInnum(dp.getInnum());
			list.setInprice(dp.getInprice());
			list.setRkmoney(dp.getRkmoney());
			list.setPh(dp.getPh());
			list.setZczh(dp.getZczh());
			list.setYxdate(dp.getYxdate());
			detailLogic.updateGoodsInDetailByGoodsInSeqId(list);
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_GOODS_IN, list, person.getSeqId(),
					TableNameUtil.KQDS_CK_GOODS_IN, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
}