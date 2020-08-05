package com.kqds.controller.ck;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsIn;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.entity.base.KqdsCkGoodsOut;
import com.kqds.entity.base.KqdsCkGoodsOutDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_GoodsLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_InLogic;
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
@RequestMapping("KQDS_Ck_Goods_OutAct")
public class KQDS_Ck_Goods_OutAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_Goods_OutAct.class);
	@Autowired
	private KQDS_Ck_Goods_OutLogic logic;
	@Autowired
	private KQDS_Ck_Goods_InLogic inLogic;
	@Autowired
	private KQDS_Ck_GoodsLogic glogic;
	
	@RequestMapping(value = "/toCkGoodsOut.act")
	public ModelAndView toCkDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/goodsOut/out_goods.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toCkGoodsOut2.act")
	public ModelAndView toCkGoodsOut2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/childhouse/outGoods/out_goods2.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCkOutGoodsSearch.act")
	public ModelAndView toCkOutGoodsSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/search/out_goods_search.jsp");
		return mv;
	}
	@RequestMapping(value="/findGoodsprice.act")
	public String findGoodsprice(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
		String outnums=request.getParameter("outnums");
		String goodsuuid=request.getParameter("goodsuuid");
		String ph=request.getParameter("ph");
		String type = request.getParameter("type");
		//根据编号和数量获取单价
		//先查询库存数量
		//要出库的数量
		int outNum=Integer.parseInt(outnums);
		int out=outNum;
		if(!YZUtility.isNullorEmpty(ph)&&!ph.equals("请选择")){
			//查询批号单价
			JSONObject json = inLogic.selectPriceByPh(goodsuuid, ph,outNum,type);
			YZUtility.RETURN_OBJ(json,response, logger);
		}else{
			//时间查询商品所有入库信息
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("goodsuuid", goodsuuid);
			map1.put("type", type);
			List<JSONObject> list1 = inLogic.inList(map1);
			//入库的数量
			int innum = inLogic.selectNum(map1);
			//未出库数量查询仓库余量
			int unnum = 0;
			if(type.equals("2")){
				unnum = glogic.ckNums2(goodsuuid);
			}else{
				unnum = glogic.ckNums1(goodsuuid);
			}
			//已出库数量
			int outnum=innum-unnum;
			BigDecimal money=new BigDecimal(0.000);
			//出库数量等于库存数量
			Map<String ,String > map = new HashMap<String,String>();
			if(outNum==unnum){
				int num=0;
				int n=0;
				for (int i = 0; i < list1.size(); i++) {
					//单件商品的数量
					int in1=list1.get(i).getInt("innum");
					if(in1+num<outnum){
						num+=in1;
					}else{
						n=i;
						break;
					}
				}
				//
				int dan=outnum-num;
				//存入第一个出库表 同一个编号
				//数量 
				int i1=list1.get(n).getInt("innum")-dan;
				if(i1==0){
					String inprice = (String) list1.get(n+1).get("inprice");
					money=new BigDecimal(inprice).multiply(new BigDecimal(list1.get(n+1).getInt("innum")));
					for (int i = n+2; i < list1.size(); i++) {
						if(list1.get(i).getInt("innum")<=(outNum-i1)){
							//存入剩余所有
							String inprice1 = (String) list1.get(i).get("inprice");
							money=money.add(new BigDecimal(inprice1).multiply(new BigDecimal(list1.get(i).getInt("innum"))));
							i1=i1+list1.get(i).getInt("innum");
						}
					}
				}else if(i1>0){
					String inprice = (String) list1.get(n).get("inprice");
					money=new BigDecimal(inprice).multiply(new BigDecimal(i1));
					for (int i = n+1; i < list1.size(); i++) {
						if(list1.get(i).getInt("innum")<=(outNum-i1)){
							//存入剩余所有
							String inprice1 = (String) list1.get(i).get("inprice");
							money=money.add(new BigDecimal(inprice1).multiply(new BigDecimal(list1.get(i).getInt("innum"))));
							i1=i1+list1.get(i).getInt("innum");
						}
					}
				}
				map.put("goodsprice", (new BigDecimal(money+"").divide(new BigDecimal(out+""),3,BigDecimal.ROUND_HALF_UP)).toString());
				map.put("ckmoney", money+"");
				YZUtility.RETURN_OBJ(map,response, logger);
			}else if(outNum<unnum){
				//添加相关商品
				int num=0;
				int n=0;
				for (int i = 0; i < list1.size(); i++) {
					//单件商品的数量
					int in1=list1.get(i).getInt("innum");
					if(in1+num<=outnum){
						num+=in1;
					}else{
						n=i;
						break;
					}
				}
				//商品多出来的数量
				int dan = 0;
				if(num==0){
					dan=0;
				}else if(num>0){
					dan=outnum - num;
				}
				//存入第一个出库表 同一个SEQ_ID
				if(list1.get(n).getInt("innum")<outNum&&dan==0){
					//存入第一个商品全部，其他进行循环添加
					money=new BigDecimal((String) list1.get(n).get("inprice")).multiply(new BigDecimal(list1.get(n).getInt("innum")));
					outNum=outNum-list1.get(n).getInt("innum");
					for (int i = n+1; i < list1.size(); i++) {
						if(list1.get(i).getInt("innum")>=outNum){
							//存入outNum数量
							money=money.add(new BigDecimal((String) list1.get(i).get("inprice")).multiply(new BigDecimal(outNum)));
							break;
						}else{
							//存入全部
							money=money.add(new BigDecimal((String) list1.get(i).get("inprice")).multiply(new BigDecimal(list1.get(i).getInt("innum"))));
							outNum=outNum-list1.get(i).getInt("innum");
						}
					}
				}else if(list1.get(n).getInt("innum")>=outNum&&dan==0&&list1.get(n).get("inprice")!=null&&!list1.get(n).get("inprice").equals("")){
					money=new BigDecimal((String) list1.get(n).get("inprice")).multiply(new BigDecimal(outNum));
				}else if(list1.get(n).getInt("innum")-dan>=outNum&&dan>0&&list1.get(n).get("inprice")!=null&&!list1.get(n).get("inprice").equals("")){
					money=new BigDecimal((String) list1.get(n).get("inprice")).multiply(new BigDecimal(outNum));
				}else if(list1.get(n).getInt("innum")-dan<outNum&&dan>0&&list1.get(n).get("inprice")!=null&&!list1.get(n).get("inprice").equals("")){
					//存入第一个商品-dan的数量，其他进行循环添加
					money=new BigDecimal((String) list1.get(n).get("inprice")).multiply(new BigDecimal(list1.get(n).getInt("innum")-dan));
					outNum=outNum-(list1.get(n).getInt("innum")-dan);
					for (int i = n+1; i < list1.size(); i++) {
						if(list1.get(i).getInt("innum")>=outNum){
							money=money.add(new BigDecimal((String) list1.get(i).get("inprice")).multiply(new BigDecimal(outNum)));
							break;
						}else{
							//存入全部
							money=money.add(new BigDecimal((String) list1.get(i).get("inprice")).multiply(new BigDecimal(list1.get(i).getInt("innum"))));
							outNum=outNum-list1.get(i).getInt("innum");
						}
					}
				}
				map.put("goodsprice", (new BigDecimal(money+"").divide(new BigDecimal(out+""),3,BigDecimal.ROUND_HALF_UP)).toString());
				map.put("ckmoney", money+"");
				YZUtility.RETURN_OBJ(map,response, logger);
			}
		}
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 出库，修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public synchronized String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String menzhen = ChainUtil.getCurrentOrganization(request);

			KqdsCkGoodsOut dp = new KqdsCkGoodsOut();
			BeanUtils.populate(dp, request.getParameterMap());
			String ll=logic.insertOut(request, response, person, menzhen, dp);
			if(ll==null){
				return null;
			}
			// 插入商品出库明细
			String params = request.getParameter("paramDetail");
			params = java.net.URLDecoder.decode(params, "UTF-8");
			JSONArray jArray = JSONArray.fromObject(params);
			List<KqdsCkGoodsOutDetail> jList = (List<KqdsCkGoodsOutDetail>) JSONArray.toCollection(jArray, KqdsCkGoodsOutDetail.class);
			String params1 = request.getParameter("paramDetail1");
			params1 = java.net.URLDecoder.decode(params1, "UTF-8");
			JSONArray jArray1 = JSONArray.fromObject(params1);
			List<JSONObject> numList = (List<JSONObject>) JSONArray.toCollection(jArray1, JSONObject.class);
			//如是调拨出库，插入二层入库信息
			if(dp.getOuttype().equals(new Integer(10))){
				if(numList.size()>0){
						KqdsCkGoodsIn in = new KqdsCkGoodsIn();
						in.setSeqId(YZUtility.getUUID());
						in.setIncode(request.getParameter("incode"));
						in.setOrganization(menzhen);
						in.setCreateuser(dp.getCreateuser());
						in.setShtime(dp.getCreatetime());
						in.setRktime(dp.getCreatetime());
						in.setCreatetime(YZUtility.getCurDateTimeStr());
						in.setAuditor(person.getSeqId());
						in.setRkr(person.getSeqId());
						in.setInstatus(3);
						in.setCheck_status(0);
						in.setStatus(0);
						in.setIntype(4);
						in.setType("2");
						List<KqdsCkGoodsInDetail> list = new ArrayList<KqdsCkGoodsInDetail>();
					for (JSONObject json : numList) {
						KqdsCkGoodsInDetail detail = new KqdsCkGoodsInDetail();
						detail.setSeqId(YZUtility.getUUID());
						if(request.getParameter("incode") != null && !request.getParameter("incode").equals("")){
							detail.setIncode(request.getParameter("incode"));
						}
						detail.setOrganization(menzhen);
						detail.setCreateuser(person.getSeqId());
						detail.setCreatetime(YZUtility.getCurDateTimeStr());
						detail.setGoodsInSeqId(in.getSeqId());
						detail.setAuditStatus(0);
						detail.setInnum(Integer.parseInt(json.getString("cknum")));
						detail.setInprice(new BigDecimal(json.getString("inprice")));
						detail.setPh(json.getString("ph"));
						detail.setPhnum(Integer.parseInt(json.getString("cknum")));
						detail.setRkmoney(new BigDecimal(json.getString("cknum")).multiply(new BigDecimal(json.getString("inprice"))));
						detail.setYxdate(json.getString("yxdate"));
						detail.setGoodsuuid(json.getString("goodsuuid"));
						detail.setType("2");
						list.add(detail);
					}
					insert(in,list);
				}
			}
			logic.updateDetailByOutnum(person, menzhen, dp, jList, numList);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_OUT, dp, TableNameUtil.KQDS_CK_GOODS_OUT, request);
				
			} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}



	/**
	 * 修改出库单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editGoodsOut.act")
	public String editGoodsOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 取值
			String ckddata = request.getParameter("ckddata");
			String ckdetaildata = request.getParameter("ckdetaildata");
			// --------------------保存出库单
			JSONObject jsonobj = JSONObject.fromObject(ckddata);
			KqdsCkGoodsOut inobj = (KqdsCkGoodsOut) JSONObject.toBean(jsonobj, KqdsCkGoodsOut.class);
			// 根据Id查询入库单
			KqdsCkGoodsOut goodsin = (KqdsCkGoodsOut) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, inobj.getSeqId());
			if (goodsin == null) {
				throw new Exception("出库单不存在");
			}
			String oldhouse = goodsin.getOuthouse();// 原仓库
			String nowhouse = inobj.getOuthouse();// 现手术室

			goodsin.setOuttype(inobj.getOuttype());
			goodsin.setSupplier(inobj.getSupplier());
			goodsin.setOuthouse(inobj.getOuthouse());
			goodsin.setOutremark(inobj.getOutremark());
			goodsin.setZhaiyao(inobj.getZhaiyao());
			goodsin.setLlr(inobj.getLlr());
			goodsin.setSqdoctor(inobj.getSqdoctor());
			logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, goodsin);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_GOODS_OUT, goodsin, TableNameUtil.KQDS_CK_GOODS_OUT, request);
			// --------------------保存出库明细
			JSONObject jsonobj2 = JSONObject.fromObject(ckdetaildata);
			KqdsCkGoodsOutDetail inobjdetail = (KqdsCkGoodsOutDetail) JSONObject.toBean(jsonobj2, KqdsCkGoodsOutDetail.class);
			// 根据Id查询入库单
			KqdsCkGoodsOutDetail goodsinDetal = (KqdsCkGoodsOutDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, inobjdetail.getSeqId());
			if (goodsinDetal == null) {
				throw new Exception("出库单明细不存在");
			}
			int oldoutnum = goodsinDetal.getOutnum();// 原出库数量
			BigDecimal oldckmoney = goodsinDetal.getCkmoney();// 原入库金额
			goodsinDetal.setOutnum(inobjdetail.getOutnum());
			goodsinDetal.setOutprice(inobjdetail.getOutprice());
			goodsinDetal.setCkmoney(inobjdetail.getCkmoney());
			goodsinDetal.setSqremark(inobjdetail.getSqremark());
			logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, goodsinDetal);
			// --------------------保存商品结存
			String goodsdata = request.getParameter("goodsdata");
			JSONObject jsonobj4 = JSONObject.fromObject(goodsdata);
			KqdsCkGoods goodsobj = (KqdsCkGoods) JSONObject.toBean(jsonobj4, KqdsCkGoods.class);
			// 根据Id查询入库单
			KqdsCkGoods goods = (KqdsCkGoods) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS, goodsobj.getSeqId());
			if (goods == null) {// 不存在说明new仓库没有该商品的库存
				throw new Exception("本仓库没有该商品的仓库");
			} else {
				goods.setGoodsprice(goodsobj.getGoodsprice());
				goods.setNums(goodsobj.getNums());
				goods.setKcmoney(goodsobj.getKcmoney());
				logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
			}
			// ***************************如果修改入库商品所属的仓库************************
			// 从原仓库的库存中加上本次入库的数量及金额。
			if (!oldhouse.equals(nowhouse)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sshouse", oldhouse);
				map.put("goodsdetailid", goods.getGoodsdetailid());
				List<KqdsCkGoods> en = (List<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
				KqdsCkGoods oldgoods = en.get(0);
				oldgoods.setNums(oldgoods.getNums() + oldoutnum);
				oldgoods.setKcmoney(oldgoods.getKcmoney().add(oldckmoney));
				BigDecimal goodsprice = null;
				if (oldgoods.getNums() == 0) {// 出库后，库存为0
					goodsprice = BigDecimal.ZERO;
				} else {
					goodsprice = oldgoods.getKcmoney().divide(new BigDecimal(oldgoods.getNums()), 2, RoundingMode.HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
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
	@RequestMapping(value = "/deleteAllGoodsOut.act")
	public String deleteAllGoodsOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String outseqId = request.getParameter("outseqId");
			KqdsCkGoodsOut goodsin = (KqdsCkGoodsOut) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, outseqId);
			if (goodsin == null) {
				throw new Exception("出库单不存在");
			}
			// 判断改入库单下是否还存在其他入库明细；不存在则删除该入库单
			Map<String, String> map = new HashMap<String, String>();
			map.put("outcode", goodsin.getOutcode());
			List<KqdsCkGoodsOutDetail> detailList = (List<KqdsCkGoodsOutDetail>) logic.loadList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, map);

			// --------------------删除入库明细
			if (detailList == null || detailList.size() == 0) {
				throw new Exception("出库单明细不存在");
			}
			for (KqdsCkGoodsOutDetail detail : detailList) {
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("sshouse", goodsin.getOuthouse());
				map2.put("goodsdetailid", detail.getGoodsuuid());
				List<KqdsCkGoods> goodsList = (List<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map2);
				if (goodsList == null || goodsList.size() == 0) {// 不存在说明之前new仓库没有该商品的库存
					throw new Exception("库存信息不存在");
				}
				KqdsCkGoods goods = goodsList.get(0);

				// 删除入库单
				logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, goodsin.getSeqId());
				logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, detail.getSeqId());
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_OUT_DETAIL, detail, TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, request);

				goods.setNums(goods.getNums() + detail.getOutnum());
				goods.setKcmoney(goods.getKcmoney().add(detail.getCkmoney()));
				BigDecimal goodsprice = BigDecimal.ZERO;
				if (goods.getNums() != 0) {
					goodsprice = goods.getKcmoney().divide(new BigDecimal(goods.getNums()), 2, RoundingMode.HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
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
	 * 删除出库单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGoodsOut.act")
	public String deleteGoodsOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// --------------------删除入库明细
			String outdetailseqId = request.getParameter("outdetailseqId");
			KqdsCkGoodsOutDetail goodsinDetal = (KqdsCkGoodsOutDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, outdetailseqId);
			if (goodsinDetal == null) {
				throw new Exception("入库单明细不存在");
			}
			logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, outdetailseqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_OUT_DETAIL, goodsinDetal, TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, request);
			// 判断改入库单下是否还存在其他入库明细；不存在则删除该入库单
			Map<String, String> map = new HashMap<String, String>();
			map.put("outcode", goodsinDetal.getOutcode());
			List<KqdsCkGoodsOutDetail> en = (List<KqdsCkGoodsOutDetail>) logic.loadList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, map);
			if (en == null || en.size() == 0) {// 不存在则删除该入库单
				// --------------------删除入库单
				String inseqId = request.getParameter("outseqId");
				KqdsCkGoodsOut goodsin = (KqdsCkGoodsOut) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, inseqId);
				if (goodsin == null) {
					throw new Exception("入库单不存在");
				}
				logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, inseqId);
			}
			// --------------------保存商品结存
			String goodsseqId = request.getParameter("goodsseqId");
			KqdsCkGoods goods = (KqdsCkGoods) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS, goodsseqId);
			if (goods == null) {// 不存在说明之前new仓库没有该商品的库存
				throw new Exception("库存信息不存在");
			} else {
				goods.setNums(goods.getNums() + goodsinDetal.getOutnum());
				goods.setKcmoney(goods.getKcmoney().add(goodsinDetal.getCkmoney()));
				BigDecimal goodsprice = BigDecimal.ZERO;
				if (goods.getNums() != 0) {
					goodsprice = goods.getKcmoney().divide(new BigDecimal(goods.getNums()), 2, RoundingMode.HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
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
	 * 出库查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inSearchList.act")
	public String inSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			// 出库查询 页面参数
			String type = request.getParameter("type");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String outhouse = request.getParameter("outhouse");
			String outtype = request.getParameter("outtype");
			String supplier = request.getParameter("supplier");
			String sqdeptid = request.getParameter("sqdeptid");
			String llr = request.getParameter("llr");
			String sqdoctor = request.getParameter("sqdoctor");
			String outcode = request.getParameter("outcode");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(outhouse)) {
				map.put("outhouse", outhouse);
			}
			if (!YZUtility.isNullorEmpty(outtype)) {
				map.put("outtype", outtype);
			}
			if (!YZUtility.isNullorEmpty(supplier)) {
				map.put("supplier", supplier);
			}
			if (!YZUtility.isNullorEmpty(sqdeptid)) {
				map.put("sqdeptid", sqdeptid);
			}
			if (!YZUtility.isNullorEmpty(llr)) {
				map.put("llr", llr);
			}
			if (!YZUtility.isNullorEmpty(sqdoctor)) {
				map.put("sqdoctor", sqdoctor);
			}
			if (!YZUtility.isNullorEmpty(outcode)) {
				map.put("outcode", outcode);
			}
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // 当前所在门诊
			List<JSONObject> list = logic.inSearchList(TableNameUtil.KQDS_CK_GOODS_OUT, map);

			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据部门查询出库
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectBydeptid.act")
	public String selectBydeptid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String deptid = request.getParameter("deptid");

			Map<String, String> map = new HashMap<>();
			map.put("sqdeptid", deptid);
			List<KqdsCkGoodsOut> en = (ArrayList<KqdsCkGoodsOut>) logic.loadList(TableNameUtil.KQDS_CK_GOODS_OUT, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: insert   
	  * @Description: TODO(调拨入库)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2020年4月18日 下午3:41:54
	 */
	public String insert(KqdsCkGoodsIn dp, List<KqdsCkGoodsInDetail> detail) throws Exception{
			try {
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN, dp);
				for (KqdsCkGoodsInDetail kdetail : detail) {
					logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, kdetail);
				}
			} catch (Exception e) {
				// TODO: handle exception
				throw new Exception(e);
			}
			
		return null;
	}
}