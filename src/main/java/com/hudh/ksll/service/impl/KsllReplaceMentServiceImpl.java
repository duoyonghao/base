package com.hudh.ksll.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hudh.ksll.dao.KsllCollorDao;
import com.hudh.ksll.dao.KsllCollorDetailDao;
import com.hudh.ksll.dao.KsllCollorGoodsDao;
import com.hudh.ksll.dao.KsllReplaceMentDao;
import com.hudh.ksll.dao.KsllReplaceMentDetailDao;
import com.hudh.ksll.entity.KsllCollor;
import com.hudh.ksll.entity.KsllCollorDetail;
import com.hudh.ksll.entity.KsllCollorDetailPh;
import com.hudh.ksll.entity.KsllGoods;
import com.hudh.ksll.entity.KsllReplaceMent;
import com.hudh.ksll.entity.KsllReplaceMentDetail;
import com.hudh.ksll.service.IKsllColorService;
import com.hudh.ksll.service.IKsllReplaceMentService;
import com.hudh.util.HUDHUtil;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.base.KqdsCkReplacementDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_GoodsLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_DetailLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_In_DetailLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.other.CacheUtil;

import net.sf.json.JSONObject;
@SuppressWarnings({ "unchecked","unused"})
@Service
public class KsllReplaceMentServiceImpl implements IKsllReplaceMentService {
	private static final List<JSONObject> JSONObject = null;
	private static Logger logger = LoggerFactory.getLogger(KsllReplaceMentServiceImpl.class);
	private static int KSLL_REPLACEMENT_STATUS_DSH = 0; //待审核
	private static int KSLL_REPLACEMENT_STATUS_TG = 1; //通过
	private static int KSLL_REPLACEMENT_STATUS_YSC_YSC = 2; //已删除
	private static int KSLL_REPLACEMENT_STATUS_YSC_WTG = 3; //未通过
	/**
	 * 科室退还明细dao
	 */
	@Autowired
	private KsllReplaceMentDetailDao ksllReplaceMentDetailDao;
	
	/**
	 * 科室领料Service
	 */
	@Autowired
	private IKsllColorService ksllColorService;
	
	/**
	 * 科室退还dao
	 */
	@Autowired
	private KsllReplaceMentDao ksllReplaceMentDao;
	
	/**
	 *科室商品dao
	 */
	@Autowired
	private KsllCollorGoodsDao ksllCollorGoodsDao;
	
	@Autowired
	private DaoSupport dao;
	/**
	 * 仓库商品表loglic
	 */
	@Autowired
	private KQDS_Ck_GoodsLogic logic;
	
	@Autowired
	private KQDS_Ck_Goods_DetailLogic detailLoglic;
	
	@Autowired
	private KsllCollorGoodsDao ksllGoodsDao;
	
	@Autowired
	private KQDS_Ck_Goods_In_DetailLogic inLogic;
	
	@Autowired
	private KsllCollorDetailDao ksllCollorDetailDao;
	
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void insertReplacement(KsllReplaceMent ksllReplaceMent, String ksllReplaceDetailsMents, HttpServletRequest request)
			throws Exception {
		String id = YZUtility.getUUID();
		String organization = ChainUtil.getCurrentOrganization(request);
		
		ksllReplaceMent.setId(id);
		ksllReplaceMent.setStatus(KSLL_REPLACEMENT_STATUS_DSH);
		ksllReplaceMent.setOrganization(organization);
		//处理明细数据
		ksllReplaceDetailsMents = java.net.URLDecoder.decode(ksllReplaceDetailsMents, "UTF-8");
		List<KsllReplaceMentDetail> ksllCollorDetailList = HUDHUtil.parseJsonToObjectList(ksllReplaceDetailsMents, KsllReplaceMentDetail.class);
		JSONObject ckGood = null;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < ksllCollorDetailList.size(); i++) {
			if(i==ksllCollorDetailList.size()-1){
				str.append("\'"+ksllCollorDetailList.get(i).getGoodscode()+"\'");
			}else{
				str.append("\'"+ksllCollorDetailList.get(i).getGoodscode()+"\',");
			}
		}
		//获取仓库全部商品用于获取商品对应的仓库信息
		Map<String,JSONObject> ckGoodMap = ksllColorService.findAllCkGoodsByGoodscode(str.toString(),organization);
		for(KsllReplaceMentDetail ksllReplaceMentDetail : ksllCollorDetailList){
			if(ksllReplaceMentDetail.getPh().equals("请选择")){
				ksllReplaceMentDetail.setPh("");
			}
			ksllReplaceMentDetail.setId(YZUtility.getUUID());
			ksllReplaceMentDetail.setParentid(id);
			ksllReplaceMentDetail.setOrganization(organization);
			ksllReplaceMentDetail.setCreatetime(YZUtility.getCurDateTimeStr());
			ckGood = ckGoodMap.get(ksllReplaceMentDetail.getGoodscode());
			if(null != ckGood) {
				ksllReplaceMentDetail.setHouse(ckGood.getString("sshouse"));
			}
		}
		if(ksllReplaceMent.getType() == 2){ //内部消耗 直接扣除科室库存
			YZPerson person = SessionUtil.getLoginPerson(request);
			ksllReplaceMent.setStatus(KSLL_REPLACEMENT_STATUS_TG);
			List<JSONObject> ksllCollorDetailList2 = HUDHUtil.parseJsonToObjectList(ksllReplaceDetailsMents, JSONObject.class);
			//获取科室现有库存
			Map<String,String> dataMap = new HashMap<String, String>();
			dataMap.put("deptpart", ksllReplaceMent.getDeptpart());
			dataMap.put("house", ksllReplaceMent.getSshouse());
			dataMap.put("goodscode", str.toString());
			Map<String,JSONObject> ksllGoodMap =  ksllColorService.findAllKsllColorGoodsByGoodscode(dataMap);
			//为了得到当前商品的库存
			List<KsllGoods> dataList = new ArrayList<KsllGoods>();
			KsllGoods ksllGoods = null;
			for(JSONObject obj : ksllCollorDetailList2) {
				JSONObject ksllKcObj = ksllGoodMap.get(obj.getString("goodscode"));
				if(null != ksllKcObj) {
					ksllGoods = new KsllGoods();
					ksllGoods.setId(ksllKcObj.getString("id"));
					int ksKcnums = Integer.valueOf(ksllKcObj.getString("nums")); //现有库存数量
					int thNums = Integer.valueOf(obj.getString("nums")); //退还数量
					int newNums = ksKcnums - thNums;
					if(newNums < 0) {
						throw new Exception("编号"+obj.getString("goodscode")+"商品科室库存不足");
					}
					ksllGoods.setNums(newNums);
					dataList.add(ksllGoods);
				}else {
					throw new Exception("科室库存中不存在编号为"+obj.getString("goodscode")+"的物品");
				}
			}
			if(dataList.size() > 0) {
				ksllCollorGoodsDao.batchUpdateKsllGoodsByPrimaryId(dataList);
				for (KsllReplaceMentDetail detail : ksllCollorDetailList) {
					Map<String,String> map = new HashMap<String,String>();
					String deptpart=request.getParameter("deptpart");
					map.put("goodscode", detail.getGoodscode());
					map.put("batchnum", detail.getPh());
					map.put("deptpart", deptpart);
					List<KsllCollorDetailPh> phlist = ksllCollorDetailDao.findDetailPhPriceByGoodscode(map);
					List<KsllCollorDetailPh> updatephlist=new ArrayList<KsllCollorDetailPh>();
					//查询部门商品单价 减去批号数量
					int i=detail.getNums();
					if(phlist.size()>0){
						if(phlist.size()==1){
							detail.setGoodsprice(phlist.get(0).getPrice());
							KsllCollorDetailPh ksll1 = new KsllCollorDetailPh();
							ksll1.setSeqId(phlist.get(0).getSeqId());
							ksll1.setNums(i);
							updatephlist.add(ksll1);
						}else{
							BigDecimal money = new BigDecimal(0);
							for (KsllCollorDetailPh ksllCollorDetailPh : phlist) {
								if(ksllCollorDetailPh.getPhnum()>=i){
									//批号数量比出库数量多,确定单价
									money=money.add(ksllCollorDetailPh.getPrice().multiply(new BigDecimal(i)));
									KsllCollorDetailPh ksll1 = new KsllCollorDetailPh();
									ksll1.setSeqId(ksllCollorDetailPh.getSeqId());
									ksll1.setNums(ksllCollorDetailPh.getPhnum());
									updatephlist.add(ksll1);
									break;
								}else{
									money=money.add(ksllCollorDetailPh.getPrice().multiply(new BigDecimal(ksllCollorDetailPh.getPhnum())));
									KsllCollorDetailPh ksll1 = new KsllCollorDetailPh();
									ksll1.setSeqId(ksllCollorDetailPh.getSeqId());
									ksll1.setNums(ksllCollorDetailPh.getPhnum());
									i=i-ksllCollorDetailPh.getPhnum();
									updatephlist.add(ksll1);
								}
							}
							detail.setGoodsprice(money.divide(new BigDecimal(i), 3));
						}
					}else{
						//查询无批号商品单价
						BigDecimal p = ksllCollorDetailDao.findCollorDetailPriceByGoodscode(map);
						detail.setGoodsprice(p);
					}
					ksllCollorDetailDao.updateDetailPhnumBySeqid(updatephlist);
				}
			}
		}
		
		//保存数据
		ksllReplaceMentDao.insertReplacement(ksllReplaceMent);
		ksllReplaceMentDetailDao.batchSaveReplacementDetail(ksllCollorDetailList);
	}

	@Override
	public List<JSONObject> findAllReplacement(Map<String, String> dataMap) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ksllReplaceMentDao.findAllReplacement(dataMap);
		return list;
	}

	@Override
	public List<JSONObject> findAllReplacementNoDelete(Map<String, String> dataMap) throws Exception {
		List<JSONObject> tempList = this.findAllReplacement(dataMap);
		List<JSONObject> list = new ArrayList<JSONObject>();
		if(null != tempList && tempList.size() > 0) {
			for(JSONObject obj : tempList) {
				if(obj.getInt("status") != 2) {
					list.add(obj);
				}
			}
		}
		return list;
	}
	
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void updateReplacementStatus(Map<String, String> dataMap,List<JSONObject> jList,YZPerson person,String type) throws Exception {
		/**
		 * 仓库点击同意退还商品，仓库库存表更新商品单价，结存金额2019-4-15
		 */
		KqdsCkGoods dp = new KqdsCkGoods();
		List<JSONObject> list = ksllReplaceMentDetailDao.findDetailByParendId(dataMap);
		for (int i = 0; i < list.size(); i++) {
			String goodscode = list.get(i).getString("goodscode");
			String goodsprice = list.get(i).getString("goodsprice");//修改键
		}
		
		ksllReplaceMentDao.updateReplacementStatus(dataMap);
		if(dataMap.get("status").equals(KSLL_REPLACEMENT_STATUS_TG + "")) {
			this.updateKsllGoodNums(dataMap.get("id"),dataMap.get("organization"));
			this.updaeCKStock(dataMap.get("id"),dataMap.get("organization"),jList,person,type);
		}
}

	@Override
	public List<JSONObject> findDetailByParendId(Map<String, String> map1) throws Exception {
		//查询退货明细
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ksllReplaceMentDetailDao.findDetailByParendId(map1);
		//根据退货的id查询部门id并查询退货商品的领料数据
		if(list.size()>0){
			String deptpart=list.get(0).getString("deptpart");
			StringBuilder str = new StringBuilder();
			StringBuilder str1 = new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				if(i==list.size()-1){
					str.append("\'"+list.get(i).getString("goodscode")+"\'");
					str1.append("\'"+list.get(i).getString("ph")+"\'");
				}else{
					str.append("\'"+list.get(i).getString("goodscode")+"\',");
					str1.append("\'"+list.get(i).getString("ph")+"\',");
				}
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("deptpart", deptpart);
			map.put("goodscode", str.toString());
			map.put("ph", str1.toString());
			List<JSONObject> list1 = ksllReplaceMentDetailDao.findCollorDetailPhByParendId(map);
			for (JSONObject json : list) {
				List<JSONObject> outList= new ArrayList<JSONObject>();
				for (JSONObject jsonObject : list1) {
					if(json.getString("goodscode").equals(jsonObject.getString("goodscode"))&&jsonObject.getString("ph").equals(json.getString("ph"))){
						outList.add(jsonObject);
					}
				}
				if(outList.size()>0){
					json.put("outList", outList);
				}
			}
		}
		return list;
	}
	@Override
	public List<JSONObject> findCkReplaceMentDetailByParendId(String parentId) throws Exception {
		List<JSONObject> list = ksllReplaceMentDetailDao.findCkReplaceMentDetailByParendId(parentId);
		return list;
	}
	/**
	 * 更改仓库信息
	 */
	@Override
	public void updaeCKStock(String parentId,String organization,List<JSONObject> jList,YZPerson person,String type) throws Exception {
		//获取退还明细数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("parentId", parentId);
		map.put("organization", organization);
		List<JSONObject> replacementGoodsDetail = this.findDetailByParendId(map);
		if(null == replacementGoodsDetail ) {
			throw new Exception("获取退货明细失败");
		}
		List<JSONObject> phList=new ArrayList<JSONObject>();
		List<JSONObject> wList=new ArrayList<JSONObject>();
		if(jList.size()>0){
			for (JSONObject jsonObject : jList) {
				if(jsonObject.getString("parameter").equals("")){
					wList.add(jsonObject);
				}else{
					phList.add(jsonObject);
				}
			}
		}
		//无批号的商品
		if(wList.size()>0){
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < wList.size(); i++) {
				if(i==wList.size()-1){
					str.append("\'"+wList.get(i).getString("goodscode")+"\'");
				}else{
					str.append("\'"+wList.get(i).getString("goodscode")+"\',");
				}
			}
			if(str.length()>0){
				//查询商品
				Map<String,JSONObject> ckGoodsMap = ksllColorService.findAllCkGoodsByGoodscode(str.toString(),map.get("organization"));
				List<KqdsCkGoods> dataList = new ArrayList<KqdsCkGoods>();
				List<KsllReplaceMent> replaceMentList = new ArrayList<KsllReplaceMent>(); //记录退还时单价
				KqdsCkGoods kqdsCkGoods = null;
				//遍历退还明细
				for(JSONObject relpaceGood : replacementGoodsDetail) {
					//获取仓库库存的指定商品的相关信息
					JSONObject ckGood = ckGoodsMap.get(relpaceGood.getString("goodscode"));
					if(null != ckGood) {
						kqdsCkGoods = new KqdsCkGoods();
						//获得商品的主键
						String goodid = ckGood.getString("goodid");
						if(YZUtility.isNotNullOrEmpty(goodid)) {
							//添加主键
							kqdsCkGoods.setSeqId(goodid);
							//添加数量
							kqdsCkGoods.setNums(ckGood.getInt("nums") + relpaceGood.getInt("cknums"));
							
							//库存单价
							BigDecimal goodsprice = new BigDecimal(ckGood.getString("goodsprice"));
							//退还数量
							BigDecimal replaceNums = new BigDecimal(relpaceGood.getInt("cknums"));
							//退还总金额
							BigDecimal replaceMoney = goodsprice.multiply(replaceNums).setScale(3,BigDecimal.ROUND_UP); 
							
							if(goodsprice.toString().equals("0.000")){
								//从最新的入库信息开始修改仓库信息，循环修改
								//查询商品入库信息
								List<JSONObject> list1 = inLogic.selectAll(ckGood.getString("goodsuuid"));
								//退还商品的数量
								String num = replaceNums.toString();
								int bd1=Integer.parseInt(num);
								int innum=list1.get(0).getInt("innum");
								if(innum>=bd1){
									//修改仓库的数量单价和金额
									//商品单价
									String inprice = list1.get(0).getString("inprice");
									BigDecimal bd = new BigDecimal(inprice);
									//商品金额
									BigDecimal totalMoney = bd.multiply(new BigDecimal(bd1));
									replaceMoney=totalMoney;
								}else{
									//第一批的商品信息
									String inprice = list1.get(0).getString("inprice");
									//商品单价
									BigDecimal bd = new BigDecimal(inprice);
									//商品金额
									BigDecimal totalMoney = bd.multiply(new BigDecimal(innum));
									int surplus=bd1-innum;
									for (int j = 1; j < list1.size(); j++) {
										innum=list1.get(j).getInt("innum");
										if(innum>=surplus){
											//商品单价
											String inprice1 = list1.get(j).getString("inprice");
											BigDecimal bd2 = new BigDecimal(inprice1);
											//商品金额
											BigDecimal totalMoney1 = bd2.multiply(new BigDecimal(surplus));
											totalMoney=totalMoney1.add(totalMoney);
											break;
										}else{
											//商品单价
											String inprice1 = list1.get(j).getString("inprice");
											BigDecimal bd2 = new BigDecimal(inprice1);
											//商品金额
											BigDecimal totalMoney1 = bd2.multiply(new BigDecimal(surplus-innum));
											totalMoney=totalMoney1.add(totalMoney);
											surplus-=innum;
										}
									}
									replaceMoney=totalMoney;
								}
							} else {
								List<JSONObject> list1 = inLogic.selectAll(ckGood.getString("goodsuuid"));
								//退还商品的数量
								String num = replaceNums.toString();
								int bd1=Integer.parseInt(num);
								//仓库的剩余数量
								int cknums = ckGood.getInt("nums");
								int i1=0;
								int n=0;
								for (int j = 0; j < list1.size(); j++) {
									i1=cknums-list1.get(j).getInt("innum");
									if(i1>0){
										cknums=i1;
									}else{
										//找到最后出库的批次
										n=j;
										break;
									}
								}
								if(i1<0){
									i1=0-i1;
								}
								//仓库的总金额
								BigDecimal ckmoney=new BigDecimal(ckGood.getString("kcmoney"));
								if(i1>=bd1){
									//修改仓库的数量单价和金额
									//商品单价
									String inprice = list1.get(n).getString("inprice");
									BigDecimal bd = new BigDecimal(inprice);
									//商品金额
									BigDecimal totalMoney = bd.multiply(new BigDecimal(bd1));
									//totalMoney=totalMoney.add(ckmoney);
									replaceMoney=totalMoney;
								}else{
									//第一批的商品信息
									String inprice = list1.get(n).getString("inprice");
									BigDecimal bd = new BigDecimal(inprice);
									int innum=list1.get(n).getInt("innum");
									//商品金额
									BigDecimal totalMoney = bd.multiply(new BigDecimal(innum));
									int surplus=bd1-innum;
									for (int j = n+1; j < list1.size(); j++) {
										innum=list1.get(j).getInt("innum");
										if(innum>=surplus){
											//商品单价
											String inprice1 = list1.get(j).getString("inprice");
											BigDecimal bd2 = new BigDecimal(inprice1);
											//商品金额
											BigDecimal totalMoney1 = bd2.multiply(new BigDecimal(surplus));
											totalMoney=totalMoney1.add(totalMoney);
											break;
										}else{
											//商品单价
											String inprice1 = list1.get(j).getString("inprice");
											BigDecimal bd2 = new BigDecimal(inprice1);
											//商品金额
											BigDecimal totalMoney1 = bd2.multiply(new BigDecimal(surplus-innum));
											totalMoney=totalMoney1.add(totalMoney);
											surplus-=innum;
										}
									}
									replaceMoney=totalMoney;
								}
							}	
							//仓库加退还金额
							kqdsCkGoods.setKcmoney(new BigDecimal(ckGood.getString("kcmoney")).add(replaceMoney));
							kqdsCkGoods.setGoodsprice(kqdsCkGoods.getKcmoney().divide(new BigDecimal(kqdsCkGoods.getNums()),3, BigDecimal.ROUND_HALF_UP));
							dataList.add(kqdsCkGoods);
						}
					}
				}
				ksllReplaceMentDao.batchUpdateCKGoodsByPrimaryId(dataList);
			}
		}
		//修改包含批号的数据
		if(phList.size()>0){
			List<KqdsCkGoods> kqdsCkGoodsList=new ArrayList<KqdsCkGoods>();
			List<KqdsCkGoodsInDetail> kqdsCkGoodsInDetailList=new ArrayList<KqdsCkGoodsInDetail>();
			List<KqdsCkReplacementDetail> kqdsCkReplacementDetailList=new ArrayList<KqdsCkReplacementDetail>();
			List<KsllCollorDetailPh> ksllCollorDetailPhList=new ArrayList<KsllCollorDetailPh>();
			for (JSONObject jsonObject : phList) {
				
				if(!jsonObject.getString("parameter").equals("")){
					String[] m = jsonObject.getString("parameter").split("/");
					KqdsCkReplacementDetail kqdsCkReplacementDetail=new KqdsCkReplacementDetail();
					kqdsCkReplacementDetail.setSeqId(YZUtility.getUUID());
					kqdsCkReplacementDetail.setParentid(parentId);
					kqdsCkReplacementDetail.setGoodscode(jsonObject.getString("goodscode"));
					kqdsCkReplacementDetail.setGoodsname(jsonObject.getString("goodsname"));
					kqdsCkReplacementDetail.setGoodsnorms(jsonObject.getString("goodsnorms"));
					kqdsCkReplacementDetail.setCreatetime(YZUtility.getCurDateTimeStr());
					kqdsCkReplacementDetail.setCreateuser(person.getSeqId());
					kqdsCkReplacementDetail.setGoodsremark(jsonObject.getString("goodsremark"));
					kqdsCkReplacementDetail.setGoodsunit(jsonObject.getString("goodsunit"));
					kqdsCkReplacementDetail.setNums(jsonObject.getInt("cknums"));
					kqdsCkReplacementDetail.setReturnnums(jsonObject.getInt("nums"));
					kqdsCkReplacementDetail.setResidue_num(jsonObject.getInt("phnum"));
					kqdsCkReplacementDetail.setPhnum(jsonObject.getInt("llnum"));
					kqdsCkReplacementDetail.setAddnumber(m[0]+"");
					kqdsCkReplacementDetailList.add(kqdsCkReplacementDetail);
					KqdsCkGoods goods=new KqdsCkGoods();
					if(type.equals("2") && !type.equals("")){
						goods.setGoodsdetailid(m[2]);
						goods.setGoodsprices(new BigDecimal(m[3]));
						goods.setNum(jsonObject.getInt("nums"));
						goods.setKcmoneys(new BigDecimal(m[3]).multiply(new BigDecimal(jsonObject.getInt("nums"))));
						goods.setOrganization(organization);
					}else{
						goods.setGoodsdetailid(m[2]);
						goods.setGoodsprice(new BigDecimal(m[3]));
						goods.setNums(jsonObject.getInt("nums"));
						goods.setKcmoney(new BigDecimal(m[3]).multiply(new BigDecimal(jsonObject.getInt("nums"))));
						goods.setOrganization(organization);
					}
					kqdsCkGoodsList.add(goods);
					KqdsCkGoodsInDetail goodsInDetail=new KqdsCkGoodsInDetail();
					goodsInDetail.setGoodsuuid(m[2]);
					goodsInDetail.setPhnum(jsonObject.getInt("nums"));
					goodsInDetail.setPh(m[1]);
					goodsInDetail.setIncode(m[0]);
					goodsInDetail.setOrganization(organization);
					kqdsCkGoodsInDetailList.add(goodsInDetail);
					
					kqdsCkReplacementDetail.setPh(m[1]);
					kqdsCkReplacementDetail.setPrice(new BigDecimal(m[3]));
					Map<String,String> map1=new HashMap<String,String>();
					map1.put("deptpart", replacementGoodsDetail.get(0).getString("deptpart"));
					map1.put("goodscode", m[2]);
					map1.put("ph", m[1]);
					map1.put("price", m[3]);
					map1.put("addnumber", m[0]);
					map1.put("time", "desc");
					List<KsllCollorDetailPh> ll = ksllCollorDetailDao.findDetailPhPriceByGoodscode(map1);
					int cknum=jsonObject.getInt("nums");
					for (KsllCollorDetailPh ksllCollorDetailPh : ll) {
						if(ksllCollorDetailPh.getPh().equals(m[1])){
							if(ksllCollorDetailPh.getPhnum()>=cknum){
								ksllCollorDetailPh.setPhnum(cknum);
								ksllCollorDetailPhList.add(ksllCollorDetailPh);
								break;
							}else{
								cknum=jsonObject.getInt("nums")-ksllCollorDetailPh.getPhnum();
								ksllCollorDetailPhList.add(ksllCollorDetailPh);
							}
						}
					}
				}else{
					KqdsCkReplacementDetail kqdsCkReplacementDetail=new KqdsCkReplacementDetail();
					kqdsCkReplacementDetail.setSeqId(YZUtility.getUUID());
					kqdsCkReplacementDetail.setParentid(parentId);
					kqdsCkReplacementDetail.setGoodscode(jsonObject.getString("goodscode"));
					kqdsCkReplacementDetail.setGoodsname(jsonObject.getString("goodsname"));
					kqdsCkReplacementDetail.setGoodsnorms(jsonObject.getString("goodsnorms"));
					kqdsCkReplacementDetail.setCreatetime(YZUtility.getCurDateTimeStr());
					kqdsCkReplacementDetail.setCreateuser(person.getSeqId());
					kqdsCkReplacementDetail.setGoodsremark(jsonObject.getString("goodsremark"));
					kqdsCkReplacementDetail.setGoodsunit(jsonObject.getString("goodsunit"));
					kqdsCkReplacementDetail.setNums(jsonObject.getInt("cknums"));
					kqdsCkReplacementDetail.setReturnnums(jsonObject.getInt("nums"));
					kqdsCkReplacementDetail.setResidue_num(jsonObject.getInt("phnum"));
					kqdsCkReplacementDetail.setPhnum(jsonObject.getInt("llnum"));
					kqdsCkReplacementDetailList.add(kqdsCkReplacementDetail);
				}
				
			}
			if(type.equals("2") && !type.equals("")){
				//修改仓库数据
				logic.updatetCkByKsllReplaceMents(kqdsCkGoodsList);
			}else{
				logic.updatetCkByKsllReplaceMent(kqdsCkGoodsList);
			}
			//修改入库明细批号数据
			inLogic.updateCkGoodsInDetailByKsllReplaceMent(kqdsCkGoodsInDetailList);
			//保存数据
			ksllReplaceMentDetailDao.batchSaveCkReplacementDetail(kqdsCkReplacementDetailList);
			//修改批号
			ksllCollorDetailDao.updateDetailPhnumBySeqid(ksllCollorDetailPhList);
		}
	}
	
	@Override
	public void updateKsllGoodNums(String parentId,String organization) throws Exception{
		KsllReplaceMent ksllReplaceMent = ksllReplaceMentDao.findReplacementById(parentId,organization);
		//获取退还明细数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("parentId", parentId);
		map.put("organization", organization);
		List<JSONObject> replacementGoodsDetail = this.findDetailByParendId(map);
		//获取科室现有库存
		Map<String,String> dataMap = new HashMap<String, String>();
		dataMap.put("deptpart", ksllReplaceMent.getDeptpart());
		dataMap.put("house", ksllReplaceMent.getSshouse());
		List<JSONObject> ksllGoodList =  ksllColorService.findAllKsllColorGoods(dataMap);
		Map<String,JSONObject> ksllGoodMap = new HashMap<String,JSONObject>();
		for(JSONObject obj : ksllGoodList) {
			ksllGoodMap.put(obj.getString("goodscode"), obj);
		}
		
		List<KsllGoods> dataList = new ArrayList<KsllGoods>();
		KsllGoods ksllGoods = null;
		List<JSONObject> hList=new ArrayList<JSONObject>();
		for (int i = 0; i < replacementGoodsDetail.size(); i++) {
			int cknums=replacementGoodsDetail.get(i).getInt("cknums");
			for (int j = 0; j < replacementGoodsDetail.size(); j++) {
				if(i!=j&&replacementGoodsDetail.get(i).getString("goodscode").equals(replacementGoodsDetail.get(j).getString("goodscode"))){
					cknums=cknums+replacementGoodsDetail.get(j).getInt("cknums");
				}
			}
			JSONObject json= new JSONObject();
			json.put("goodscode", replacementGoodsDetail.get(i).getString("goodscode"));
			json.put("cknums", cknums);
			hList.add(json);
		}
		List<JSONObject> gList=new ArrayList<JSONObject>();
	    for (JSONObject obj : hList) {
	      boolean b = gList.stream().anyMatch(k -> k.getString("goodscode").equals(obj.getString("goodscode")));
	      if (!b) {
	    	  gList.add(obj);
	      }
	    }
		for(JSONObject obj : gList) {
			JSONObject ksllKcObj = ksllGoodMap.get(obj.getString("goodscode"));
			if(null != ksllKcObj) {
				ksllGoods = new KsllGoods();
				ksllGoods.setId(ksllKcObj.getString("id"));
				int ksKcnums = Integer.valueOf(ksllKcObj.getString("nums")); //现有库存数量
				int thNums = Integer.valueOf(obj.getString("cknums")); //退还数量
				int newNums = ksKcnums - thNums;
				if(newNums < 0) {
					throw new Exception("编号"+obj.getString("goodscode")+"商品科室库存不足，无法退还");
				}
				ksllGoods.setNums(newNums);
				dataList.add(ksllGoods);
			}else {
				throw new Exception("科室库存中不存在编号为"+obj.getString("goodscode")+"的物品");
			}
		}
		if(dataList.size() > 0) {
			ksllCollorGoodsDao.batchUpdateKsllGoodsByPrimaryId(dataList);
		}
	}
	/**
	 * 根据编号查询退货数量
	 * <p>Title: selectReturnNumByGoodscode</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月19日 
	 * @param goodscode
	 * @return
	 * @throws Exception
	 */
	public int selectReturnNumByGoodscode(String goodscode) throws Exception{
		return ksllReplaceMentDetailDao.selectReturnNumByGoodscode(goodscode);
	}
}
