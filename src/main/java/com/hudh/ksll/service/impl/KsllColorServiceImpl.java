package com.hudh.ksll.service.impl;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudh.ksll.service.IKsllColorService;
import com.hudh.ksll.util.RunnableUtil;
import com.hudh.util.HUDHUtil;
import com.kqds.core.global.YZActionKeys;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.entity.base.KqdsCkGoodsOut;
import com.kqds.entity.base.KqdsCkGoodsOutDetail;
import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_GoodsLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_InLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_In_DetailLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_OutLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_Out_DetailLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.ksll.dao.KsllCollorDao;
import com.hudh.ksll.dao.KsllCollorDetailDao;
import com.hudh.ksll.dao.KsllCollorGoodsDao;
import com.hudh.ksll.entity.KsllCollor;
import com.hudh.ksll.entity.KsllCollorDetail;
import com.hudh.ksll.entity.KsllCollorDetailPh;
import com.hudh.ksll.entity.KsllGoods;
@SuppressWarnings({ "unchecked","unused"})
@Service
public class   KsllColorServiceImpl implements IKsllColorService {
	private static Logger logger = LoggerFactory.getLogger(KsllColorServiceImpl.class);
	private static int KSLL_STATUS_BHZ = 0; //备货中
	private static int KSLL_STATUS_BHWC = 1; //备货完成
	private static int KSLL_STATUS_YLZ = 2; //已领走
	private static int KSLL_STATUS_YSC = 3; //已删除
	
	private static int KSLLDETAIL_STATUS_YSC = 1; //库房已删除
	private static int KSLLDETAIL_STATUS_YXG = 2; //库房修改
	/**
	 * 科室商品dao
	 */
	@Autowired
	private KsllCollorGoodsDao ksllCollorGoodsDao;
	/**
	 * 科室领料dao
	 */
	@Autowired
	private KsllCollorDao ksllCollorDao;
	
	/**
	 * 科室领料明细dao
	 */
	@Autowired
	private KsllCollorDetailDao ksllCollorDetailDao;
	
	@Autowired
	private KQDS_Ck_Goods_OutLogic logic;
	
	@Autowired
	private KQDS_Ck_Goods_InLogic inLogic;
	
	@Autowired
	private KQDS_Ck_Goods_In_DetailLogic indetailLogic;
	
	@Autowired
	private KQDS_Ck_GoodsLogic glogic;
	
	@Autowired
	private DaoSupport dao;
	
	@Override
	public JSONObject findAllKsllColorGoods(Map<String,String> dataMap,BootStrapPage bp) throws Exception {
		// TODO Auto-generated method stub
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = ksllCollorGoodsDao.findAllKsllGoods(dataMap);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();	
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		return jobj1;
	}

	@Override
	public List<JSONObject> findAllCKDept(Map<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ksllCollorGoodsDao.findAllCKDept(dataMap);
		return list;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void saveKsllData(KsllCollor ksllCollor, String collorDetails, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		String id = YZUtility.getUUID();
		String organization = ChainUtil.getCurrentOrganization(request);
		ksllCollor.setId(id);
		ksllCollor.setType(1); //领用出库
		ksllCollor.setStatus(KSLL_STATUS_BHZ);
		ksllCollor.setOrganization(organization);
		
		//处理明细数据
		collorDetails = java.net.URLDecoder.decode(collorDetails, "UTF-8");
		List<KsllCollorDetail> ksllCollorDetailList = HUDHUtil.parseJsonToObjectList(collorDetails, KsllCollorDetail.class);
		JSONObject ckGood = null;
		//获取仓库全部商品用于获取商品对应的仓库信息
//		Map<String,JSONObject> ckGoodMap = findAllCKGood();
		
		for(KsllCollorDetail ksllCollorDetail : ksllCollorDetailList){
			ksllCollorDetail.setId(YZUtility.getUUID());
			ksllCollorDetail.setParentid(id);
			ksllCollorDetail.setStatus(KSLL_STATUS_BHZ);
			ksllCollorDetail.setOrganization(organization);
			ksllCollorDetail.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
			ksllCollorDetail.setCreattime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			/**
			 * 2019.07.10 lwg
			 * 根据goodscode查询仓库的sshouse
			 */
			//String sshouse=findCKGoodSshouse(ksllCollorDetail.getGoodscode(),organization);
			//ckGood = ckGoodMap.get(ksllCollorDetail.getGoodscode());
//			if(null != ckGood) {
//				ksllCollorDetail.setHouse(ckGood.getString("sshouse"));
//			}
			/*if(sshouse!=null&&!sshouse.equals("")){
				ksllCollorDetail.setHouse(sshouse);
			}*/
			/**
			 * end
			 */
		}
		//保存数据
		ksllCollorDao.insertCollor(ksllCollor);
		/**
		 * 2019.07.10 lwg
		 * 添加线程异步执行添加明细的方法
		 */
		RunnableUtil runnableUtil=new RunnableUtil();
		runnableUtil.setKsllCollorDetailList(ksllCollorDetailList);
		Thread thread = new Thread(runnableUtil); 
		thread.start();
		//ksllCollorDetailDao.batchSaveCollorDetail(ksllCollorDetailList);
	}
	/**
	 * 获取全部的仓库商品，返回map类型,key为商品编号
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String findCKGoodSshouse(String goodscode,String organization) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("goodscode", goodscode);
		map.put("organization", organization);
		String sshouse=ksllCollorDao.findCKGoodSshouse(map);
		return sshouse;
	}
	/**
	 * 获取全部的仓库商品，返回map类型,key为商品编号
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String,JSONObject> findAllCKGood() throws Exception{
		Map<String,JSONObject> ckGoodMap = new HashMap<String,JSONObject>();
		List<JSONObject> list = ksllCollorDao.findAllCKGoods();
		for(JSONObject obj : list) {
			String key = obj.getString("goodscode");
			if(YZUtility.isNotNullOrEmpty(key)) {
				ckGoodMap.put(key, obj);
			}
		}
		return ckGoodMap;
	}

	@Override
	public List<JSONObject> findAllKsllColor(Map<String,String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = ksllCollorDao.findAllCollor(dataMap);
		return list;
	}

	@Override
	public JSONObject findKsllColorAndDetails(String ksllCollorId) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jo = new JSONObject();
		//获取科室申领主表信息
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("id", ksllCollorId);
		List<JSONObject> ksllCollorData = ksllCollorDao.findAllCollor(dataMap);
		if(null != ksllCollorData && ksllCollorData.size() > 0) {
			jo.put("ksllCollorData", ksllCollorData.get(0));
		}else {
			throw new Exception("获取申领单失败");
		}
		
		//获取申领明细数据
		List<JSONObject> ksllCollorDetailData = ksllCollorDetailDao.findDetailByParendId(ksllCollorId);
		jo.put("ksllCollorDetailData", ksllCollorDetailData);
		return jo;
	}
	@Override
	public JSONObject findKsllColorAndDetail(String ksllCollorId) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jo = new JSONObject();
		//获取科室申领主表信息
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("id", ksllCollorId);
		List<JSONObject> ksllCollorData = ksllCollorDao.findAllCollor(dataMap);
		if(null != ksllCollorData && ksllCollorData.size() > 0) {
			jo.put("ksllCollorData", ksllCollorData.get(0));
		}else {
			throw new Exception("获取申领单失败");
		}
		
		//获取申领明细数据
		List<JSONObject> ksllCollorDetailData = ksllCollorDetailDao.findDetailByParendIds(ksllCollorId);
		jo.put("ksllCollorDetailData", ksllCollorDetailData);
		return jo;
	}

	@Override
	public List<KqdsCkHouse> findAllCKHouse(Map<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Map<String,String>> tempMap = new HashMap<String,Map<String,String>>();
		tempMap.put("params", dataMap);
		List<KqdsCkHouse> list = ksllCollorGoodsDao.findAllCKHouse(tempMap);
		if(null != list && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void ksllOutGoods(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jo = this.insertOrUpdate(request, response); //仓库出库
		List<KsllCollorDetail> ksllDetailList = checkToKsllColor(request);
		 //修改对应的状态
		changeKsllStatus(request); 
		//将科室领料物品加到科室库存
		addKsllToGoods(request,ksllDetailList); 
	}
	/**
	 * 更新科室领料数据状态
	 * @param request
	 */
	private void changeKsllStatus(HttpServletRequest request) throws Exception {
		String ksllCollorId = request.getParameter("ksllCollorId");
		if(YZUtility.isNullorEmpty(ksllCollorId)) {
			throw new Exception("获取科室领料单失败");
		}
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("id", ksllCollorId);
		dataMap.put("status", KSLL_STATUS_YLZ + "");
		ksllCollorDao.updateCollorStatus(dataMap);
	}
	/**
	 * 更新科室领料明细数据状态
	 * @param request
	 */
	private void changeKsllDetailStatus(HttpServletRequest request) throws Exception {
		String ksllCollorId = request.getParameter("ksllCollorId");
		if(YZUtility.isNullorEmpty(ksllCollorId)) {
			throw new Exception("获取科室领料单失败");
		}
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("id", ksllCollorId);
		dataMap.put("status", KSLL_STATUS_YLZ + "");
		ksllCollorDetailDao.updateCollorDetail(dataMap);
	}
	/**
	 * 将科室领料物品加到科室库存
	 * @param request
	 */
	private void addKsllToGoods(HttpServletRequest request,List<KsllCollorDetail> ksllDetailList) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		String ksllCollorId = request.getParameter("ksllCollorId");
		//获取对应科室下现有的库存物品
		Map<String,String> dataMap = new HashMap<String,String>();
		String deptpart = request.getParameter("sqdeptid");
		dataMap.put("deptpart", deptpart);
		dataMap.put("parentid", ksllCollorId);
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		jsonList = ksllCollorGoodsDao.findAllKsllGoods(dataMap);
		List<KsllGoods> goodsList =  new ArrayList<KsllGoods>(); 
		goodsList = HUDHUtil.parseJsonToObjectList(JSON.toJSONString(jsonList), KsllGoods.class);
		Map<String,KsllGoods> goodsMap = new HashMap<String,KsllGoods>();
		for(KsllGoods ksllGoods : goodsList) {
			goodsMap.put(ksllGoods.getGoodscode(), ksllGoods);
		}
		//获取明细进行数据计算
		List<JSONObject> detailJsonTemp = ksllCollorDetailDao.findDetailByParendId(ksllCollorId);
		Map<String,JSONObject> detailJsonMap = new HashMap<String,JSONObject>();
		for(JSONObject o : detailJsonTemp){
			detailJsonMap.put(o.getString("goodscode"), o);
		}
		List<JSONObject> detailJson = new ArrayList<JSONObject>();
		if(null != ksllDetailList) {
			for(KsllCollorDetail ksllDetail : ksllDetailList) {
				JSONObject jo = detailJsonMap.get(ksllDetail.getGoodscode());
				jo.put("cknums", ksllDetail.getNums());
				detailJson.add(jo);
			}
		}
		
		List<KsllGoods> saveKsllGoodsList = new ArrayList<KsllGoods>(); //需新增的药品数据
		List<KsllGoods> updateKsllGoodsList = new ArrayList<KsllGoods>(); //只需更新数量的数据
		KsllGoods ksllGood = null;
		for(JSONObject ksllCollorDetail : detailJson) {
			KsllGoods tempksllGood  = goodsMap.get(ksllCollorDetail.getString("goodsdetailid")); //入科科室已经有该物品信息
			if(tempksllGood ==null){
				tempksllGood  = goodsMap.get(ksllCollorDetail.getString("goodscode"));
			}
			ksllGood = new KsllGoods();
			if(null != tempksllGood) {
				ksllGood.setId(tempksllGood.getId());
				ksllGood.setNums(tempksllGood.getNums() + ksllCollorDetail.getInt("cknums"));
				updateKsllGoodsList.add(ksllGood);
			}else {
				ksllGood.setId(YZUtility.getUUID());
				ksllGood.setDeptpart(deptpart);
				ksllGood.setGoodscode(ksllCollorDetail.getString("goodscode"));
				ksllGood.setGoodsname(ksllCollorDetail.getString("goodsname"));
				ksllGood.setGoodsnorms(ksllCollorDetail.getString("goodsnorms"));
				ksllGood.setGoodsunit(ksllCollorDetail.getString("goodsunit"));
				ksllGood.setNums(ksllCollorDetail.getInt("cknums"));
				ksllGood.setInremark(ksllCollorDetail.getString("goodsremark"));
				ksllGood.setHouse(ksllCollorDetail.getString("house"));
				ksllGood.setOrganization(ChainUtil.getCurrentOrganization(request));
				ksllGood.setGoodsprice(ksllCollorDetail.getString("goodprice"));//添加单价（科室库存表）
				ksllGood.setCreatetime(YZUtility.getCurDateTimeStr());//记录创建时间
				ksllGood.setGoodsdetailid(ksllCollorDetail.getString("goodsdetailid"));
				ksllGood.setCreateuser(""+person.getSeqId());
				saveKsllGoodsList.add(ksllGood);
			}
		}
		
		//进行数据更新
		if(null != saveKsllGoodsList && saveKsllGoodsList.size() > 0) {
			ksllCollorGoodsDao.batchSaveKsllGoodsDetail(saveKsllGoodsList);
		}
		if(null != updateKsllGoodsList && updateKsllGoodsList.size() > 0) {
			ksllCollorGoodsDao.batchUpdateKsllGoodsByPrimaryId(updateKsllGoodsList);
		}
	}
	
	/**
	 * 科室领料商品出口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private JSONObject insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String menzhen = ChainUtil.getCurrentOrganization(request);

			KqdsCkGoodsOut dp = new KqdsCkGoodsOut();
			BeanUtils.populate(dp, request.getParameterMap());
			/**
			 * 添加科室领料出库禁止多人出库同一单据校验  syp 2019-8-31 start
			 */
			String ksllCollorId = request.getParameter("ksllCollorId");
			JSONObject json = logic.findGoodsOutByOutcode(ksllCollorId);
			if (json != null) {
				throw new Exception("改领料单已经办理出库，不能重复出库！");
			}
			/**
			 * end
			 */

			// 验证出库单号是否重复
			Map<String, String> mapin = new HashMap<String, String>();
			mapin.put("outcode", dp.getOutcode());
			List<KqdsCkGoodsOut> outList = (List<KqdsCkGoodsOut>) logic.loadList(TableNameUtil.KQDS_CK_GOODS_OUT, mapin);
			if (outList.size() > 0) {
				throw new Exception("出库单号已存在，系统已自动重新获取！");
			}

			dp.setSeqId(YZUtility.getUUID());
			dp.setOrganization(menzhen);
			// 现在出库不需要审核功能，暂把审核、出库、申请 都一致保存申请的人及时间；状态设为3（已出库）
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			dp.setShtime(dp.getCreatetime());
			dp.setAuditor(person.getSeqId());
			// dp.setCktime(dp.getCreatetime());
			dp.setCkr(person.getSeqId());
			dp.setOutstatus(3);
			dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT, dp);

			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_OUT, dp, TableNameUtil.KQDS_CK_GOODS_OUT, request);

			// 插入商品出库明细
			String params = request.getParameter("paramDetail");
			params = java.net.URLDecoder.decode(params, "UTF-8");
			JSONArray jArray = JSONArray.fromObject(params);
			List<KqdsCkGoodsOutDetail> jList = (List<KqdsCkGoodsOutDetail>) JSONArray.toCollection(jArray, KqdsCkGoodsOutDetail.class);
			//出库明细数据
			String params1 = request.getParameter("paramDetail1");
			params1 = java.net.URLDecoder.decode(params1, "UTF-8");
			JSONArray jArray1 = JSONArray.fromObject(params1);
			List<JSONObject> numList = (List<JSONObject>) JSONArray.toCollection(jArray1, JSONObject.class);
			List<KqdsCkGoodsOutDetail> kList=new ArrayList<KqdsCkGoodsOutDetail>();
			List<KqdsCkGoodsOutDetail> cList=new ArrayList<KqdsCkGoodsOutDetail>();
			List<KqdsCkGoodsInDetail> iList=new ArrayList<KqdsCkGoodsInDetail>();
			for (KqdsCkGoodsOutDetail detail : jList) {
				if(detail.getPh().equals("请选择")||"".equals(detail.getPh())){
					kList.add(detail);
				}else{
					cList.add(detail);
				}
			}
			if(kList.size()>0){
			for (KqdsCkGoodsOutDetail detail : kList) {
				//根据商品id和数量获取入库明细
				String goodsuuid=detail.getGoodsuuid();
				//要出库的数量
				int outNum=detail.getOutnum();
				int out=outNum;
				//时间查询商品所有入库信息
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("goodsuuid", goodsuuid);
				map1.put("type", detail.getType());
				List<JSONObject> list1 = inLogic.inList(map1);
				//已出库数量
				//int outnum = glogic.getOutNumByGoodsuuid(goodsuuid);
				//入库的数量
				int innum = inLogic.selectNum(map1);
				//未出库数量查询仓库余量
				int unnum = 0;
				if(detail.getType().equals("2")){
					unnum = glogic.ckNums2(goodsuuid);
				}else{
					unnum = glogic.ckNums1(goodsuuid);
				}
				int outnum=innum-unnum;
				BigDecimal money=new BigDecimal(0);
				if(outNum==unnum){
					// 添加剩余所有的商品
					// 首个入库的剩余数量
					int num=0;
					int n=0;
					for (int i = 0; i < list1.size(); i++) {
						//单件商品的数量
						int in1=list1.get(i).getInt("innum");
						if(in1+num<outnum){
							num+=in1;
						}else if(in1+num==outnum){
							n=i;
							break;
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
						money = addOutDetail(person, menzhen, dp, detail, list1, n+1, outNum);
						for (int i = n+2; i < list1.size(); i++) {
							if(list1.get(i).getInt("innum")<=(outNum-i1)){
								//存入剩余所有
								money = addOut(person, menzhen, dp, detail,list1.get(i).getInt("innum") , list1, money, i);
								i1=i1+list1.get(i).getInt("innum");
							}
						}
					}else if(i1>0){
						money = addOutDetail(person, menzhen, dp, detail, list1, n, i1);
						for (int i = n+1; i < list1.size(); i++) {
							if(list1.get(i).getInt("innum")<=(outNum-i1)){
								//存入剩余所有
								money = addOut(person, menzhen, dp, detail,list1.get(i).getInt("innum") , list1, money, i);
								i1=i1+list1.get(i).getInt("innum");
							}
						}
					}
					
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
					if(list1.get(n).getInt("innum")<outNum&&dan==0){
						//存入第一个商品全部，其他进行循环添加
						if(list1.get(n).getInt("innum")==0){
							break;
						}
						money = addOutDetail(person, menzhen, dp, detail, list1, n, list1.get(n).getInt("innum"));
						outNum=outNum-list1.get(n).getInt("innum");
						for (int i = n+1; i < list1.size(); i++) {
							if(list1.get(i).getInt("innum")>=outNum){
								//存入outNum数量
								money = addOut(person, menzhen, dp, detail, outNum, list1, money, i);
								break;
							}else{
								//存入全部
								money = addOut(person, menzhen, dp, detail, list1.get(i).getInt("innum"), list1, money, i);
								outNum=outNum-list1.get(i).getInt("innum");
							}
						}
					}else if(list1.get(n).getInt("innum")>=outNum&&dan==0){
						if(list1.get(n).getInt("innum")==0){
							break;
						}
						money = addOutDetail(person, menzhen, dp, detail, list1, n, outNum);
					}else if(list1.get(n).getInt("innum")-dan>=outNum&&dan>0){
						if(list1.get(n).getInt("innum")==0){
							break;
						}
						money = addOutDetail(person, menzhen, dp, detail, list1, n, outNum);
					}else if(list1.get(n).getInt("innum")-dan<outNum&&dan>0 ){
						if(list1.get(n).getInt("innum")==0){
							break;
						}
						//存入第一个商品-dan的数量，其他进行循环添加
						money = addOutDetail(person, menzhen, dp, detail, list1, n, list1.get(n).getInt("innum")-dan);
						outNum=outNum-(list1.get(n).getInt("innum")-dan);
						for (int i = n+1; i < list1.size(); i++) {
							if(list1.get(i).getInt("innum")>=outNum){
								money = addOut(person, menzhen, dp, detail, outNum, list1, money, i);
								break;
							}else{
								//存入全部
								money = addOut(person, menzhen, dp, detail, list1.get(i).getInt("innum"), list1, money, i);
								outNum=outNum-list1.get(i).getInt("innum");
							}
						}
					}
				}
				//商品出库后剩余数量
				int syNum=innum-out-outnum;
				if(syNum<0){
					syNum=0;
				}
				// 商品表 更新商品库存 ；等待出库数量 等待出库数量 因没有审核功能 暂不维护
				// 根据商品主键 查询商品
				Map<String, String> map = new HashMap<String, String>();
				//map.put("sshouse", dp.getOuthouse());
				map.put("goodsdetailid", detail.getGoodsuuid());
				List<KqdsCkGoods> list = (List<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
				if (list == null) {
					throw new Exception("不存在商品");
				}
				KqdsCkGoods goods = list.get(0);
				if(dp.getType().equals("2")){
					if (goods.getNum() < detail.getOutnum()) {
						throw new Exception(detail.getGoodscode()+"库存不足");
					}
					// 更新 商品表 单价 及 金额
					// 金额 = 原金额 - 出库金额
					// 单价 = 商品表剩余金额/剩余库存
					BigDecimal kcmoney = new BigDecimal(0);
					if(goods.getKcmoneys()!=null){
						 kcmoney = goods.getKcmoneys().subtract(money);
					}
					//BigDecimal kcmoney = goods.getKcmoney().subtract(detail.getCkmoney());
					BigDecimal goodsprice = null;
					if (goods.getNum() == 0) {// 出库后，库存为0
						goodsprice = BigDecimal.ZERO;
						goods.setKcmoneys(BigDecimal.ZERO);
					} else {
						if(kcmoney != null){
							goodsprice = kcmoney.divide(new BigDecimal(goods.getNum()), 3, RoundingMode.HALF_EVEN).setScale(3, BigDecimal.ROUND_HALF_DOWN);
						}
					}
					goods.setNum(syNum);
					goods.setKcmoneys(kcmoney);
					goods.setGoodsprices(goodsprice);
				}else{
					if (goods.getNums() < detail.getOutnum()) {
						throw new Exception(detail.getGoodscode()+"库存不足");
					}
					// 更新 商品表 单价 及 金额
					// 金额 = 原金额 - 出库金额
					// 单价 = 商品表剩余金额/剩余库存
					BigDecimal kcmoney = goods.getKcmoney().subtract(money);
					//BigDecimal kcmoney = goods.getKcmoney().subtract(detail.getCkmoney());
					BigDecimal goodsprice = null;
					if (goods.getNums() == 0) {// 出库后，库存为0
						goodsprice = BigDecimal.ZERO;
						goods.setKcmoney(BigDecimal.ZERO);
					} else {
						goodsprice = kcmoney.divide(new BigDecimal(goods.getNums()), 3, RoundingMode.HALF_EVEN).setScale(3, BigDecimal.ROUND_HALF_DOWN);
					}
					goods.setNums(syNum);
					goods.setKcmoney(kcmoney);
					goods.setGoodsprice(goodsprice);
				}
				logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
				
				JSONObject jobj = new JSONObject();
				jobj.put("data", dp.getSeqId());
			}
		}
			if(cList.size()>0){
				List<KqdsCkGoodsOutDetail> bList=new ArrayList<KqdsCkGoodsOutDetail>();
				for (JSONObject jsonObject : numList) {
					KqdsCkGoodsInDetail in =new KqdsCkGoodsInDetail();
					in.setSeqId(jsonObject.getString("seqid"));
					in.setPhnum(jsonObject.getInt("cknum"));
					iList.add(in);
					KqdsCkGoodsOutDetail dd= new KqdsCkGoodsOutDetail();
					dd.setSeqId(YZUtility.getUUID());
					for (KqdsCkGoodsOutDetail detail : cList) {
						if(jsonObject.getString("goodsuuid").equals(detail.getGoodsuuid())&&jsonObject.getString("ph").equals(detail.getPh())){
							if(!detail.getSqremark().equals("")){
								dd.setSqremark(detail.getSqremark());
							}
							if(detail.getPh() != null){
								dd.setPh(detail.getPh());
							}
							if(detail.getPhnum() != null){
								dd.setPhnum(detail.getPhnum());
							}
							
							dd.setGoodsuuid(detail.getGoodsuuid());
							dd.setOutnum(jsonObject.getInt("cknum"));
							if(jsonObject.getString("inprice") != null && !jsonObject.getString("inprice").equals("")){
								dd.setOutprice(new BigDecimal(jsonObject.getString("inprice")));
								dd.setCkmoney((new BigDecimal(jsonObject.getString("inprice"))).multiply(new BigDecimal(jsonObject.getInt("cknum"))));
							}else{
								dd.setOutprice(new BigDecimal("0.00"));
								dd.setCkmoney(new BigDecimal("0.00"));
							}
							
							dd.setOutcode(dp.getOutcode());
							dd.setOrganization(menzhen);
							dd.setCreateuser(person.getSeqId());
							dd.setCreatetime(dp.getCreatetime());
							//入库编号和入库时间
							dd.setAddTime(jsonObject.getString("createtime"));
							dd.setAddNumber(jsonObject.getString("incode"));
							dd.setType(dp.getType());
							if(jsonObject.containsKey("yxdate") && jsonObject.getString("yxdate") != null && !jsonObject.getString("yxdate").equals("")){
								dd.setYxdate(jsonObject.getString("yxdate"));
							}
							bList.add(dd);
							break;
						}
					}
				}
				//修改商品信息
				List<KqdsCkGoods> gList=new ArrayList<KqdsCkGoods>();
				if(dp.getType().equals("2")){
					if(cList.size()==1){
						KqdsCkGoods goods=new KqdsCkGoods();
						goods.setGoodsdetailid(cList.get(0).getGoodsuuid());
						goods.setKcmoneys(cList.get(0).getCkmoney());
						goods.setNum(cList.get(0).getOutnum());
						goods.setNumsexport(cList.get(0).getPhnum());
						int nums=goods.getNum();
						int phnum=cList.get(0).getPhnum();
						if(nums==phnum){
							goods.setGoodsprices(new BigDecimal(0));
						}else{
							goods.setGoodsprices(new BigDecimal(1));
						}
						gList.add(goods);
					}else{
						for (int i = 0; i < cList.size(); i++) {
							BigDecimal kcmoney= cList.get(i).getCkmoney();
							int nums=cList.get(i).getOutnum();
							int numsexport = 0;
							if(cList.get(i).getPhnum() != null){
								numsexport = cList.get(i).getPhnum();
							}
							for (int j = 0; j < cList.size(); j++) {
								if(i!=j&&cList.get(i).getGoodsuuid().equals(cList.get(j).getGoodsuuid())){
									kcmoney=kcmoney.add(cList.get(j).getCkmoney());
									nums=nums+cList.get(j).getOutnum();
									numsexport=numsexport+cList.get(j).getPhnum();
								}
							}
							KqdsCkGoods goods=new KqdsCkGoods();
							goods.setGoodsdetailid(cList.get(i).getGoodsuuid());
							goods.setKcmoneys(kcmoney);
							goods.setNum(nums);
							goods.setNumsexport(numsexport);
							if(nums==numsexport){
								goods.setGoodsprices(new BigDecimal(0));
							}else{
								goods.setGoodsprices(new BigDecimal(1));
							}
							gList.add(goods);
						}
					}
				}else{
					if(cList.size()==1){
						KqdsCkGoods goods=new KqdsCkGoods();
						goods.setGoodsdetailid(cList.get(0).getGoodsuuid());
						goods.setKcmoney(cList.get(0).getCkmoney());
						goods.setNums(cList.get(0).getOutnum());
						goods.setNumsexport(cList.get(0).getPhnum());
						int nums=goods.getNums();
						int phnum=cList.get(0).getPhnum();
						if(nums==phnum){
							goods.setGoodsprice(new BigDecimal(0));
						}else{
							goods.setGoodsprice(new BigDecimal(1));
						}
						gList.add(goods);
					}else{
						for (int i = 0; i < cList.size(); i++) {
							BigDecimal kcmoney= cList.get(i).getCkmoney();
							int nums=cList.get(i).getOutnum();
							int numsexport = 0;
							if(cList.get(i).getPhnum() != null){
								numsexport = cList.get(i).getPhnum();
							}
							for (int j = 0; j < cList.size(); j++) {
								if(i!=j&&cList.get(i).getGoodsuuid().equals(cList.get(j).getGoodsuuid())){
									kcmoney=kcmoney.add(cList.get(j).getCkmoney());
									nums=nums+cList.get(j).getOutnum();
									numsexport=numsexport+cList.get(j).getPhnum();
								}
							}
							KqdsCkGoods goods=new KqdsCkGoods();
							goods.setGoodsdetailid(cList.get(i).getGoodsuuid());
							goods.setKcmoney(kcmoney);
							goods.setNums(nums);
							goods.setNumsexport(numsexport);
							if(nums==numsexport){
								goods.setGoodsprice(new BigDecimal(0));
							}else{
								goods.setGoodsprice(new BigDecimal(1));
							}
							gList.add(goods);
						}
					}
				}
				if(bList.size()>0){
					//添加出库明细
					dao.batchInsert(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL+".insertSelective", bList);
					//添加领料明细的批号明细
					List<JSONObject> detailJson = ksllCollorDetailDao.findDetailByParendId(ksllCollorId);
					List<KsllCollorDetailPh> ksllCollorDetailPhList= new ArrayList<KsllCollorDetailPh>();
					for (JSONObject jsonObject : detailJson) {
						for (KqdsCkGoodsOutDetail kqdsCkGoodsOutDetail: bList) {
							if(jsonObject.getString("goodsdetailid").equals(kqdsCkGoodsOutDetail.getGoodsuuid())){
								KsllCollorDetailPh ksllCollorDetailPh=new KsllCollorDetailPh();
								ksllCollorDetailPh.setParentid(jsonObject.getString("id"));
								ksllCollorDetailPh.setPh(kqdsCkGoodsOutDetail.getPh());
								ksllCollorDetailPh.setCreatetime(YZUtility.getCurDateTimeStr());
								ksllCollorDetailPh.setPhnum(kqdsCkGoodsOutDetail.getOutnum());
								ksllCollorDetailPh.setSeqId(YZUtility.getUUID());
								ksllCollorDetailPh.setAddnumber(kqdsCkGoodsOutDetail.getAddNumber());
								ksllCollorDetailPh.setPrice(kqdsCkGoodsOutDetail.getOutprice());
								ksllCollorDetailPh.setAddtime(kqdsCkGoodsOutDetail.getAddTime());
								ksllCollorDetailPh.setNums(kqdsCkGoodsOutDetail.getOutnum());
								ksllCollorDetailPhList.add(ksllCollorDetailPh);
							}
						}
					}
					dao.batchUpdate("HUDH_KSLL_COLLOR_DETAIL_PH.insertSelective", ksllCollorDetailPhList);
				}
				if(iList.size()>0){
					//修改入库明细表数据
					dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL+".updateGoodsInDetailByNumList", iList);	
					
				}
				if(dp.getType().equals("2")){
					if(gList.size()==1){
						dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS+".updateGoodsByNumLists", gList);
					}else if(gList.size()>1){
						List<KqdsCkGoods> hList=new ArrayList<KqdsCkGoods>();
					    for (KqdsCkGoods kqdsCkGoods : gList) {
					      boolean b = hList.stream().anyMatch(k -> k.getGoodsdetailid().equals(kqdsCkGoods.getGoodsdetailid()));
					      if (!b) {
					    	  hList.add(kqdsCkGoods);
					      }
					    }
						//批量修改仓库表数据
						dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS+".updateGoodsByNumLists", hList);
					}
				}else{
					if(gList.size()==1){
						dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS+".updateGoodsByNumList", gList);
					}else if(gList.size()>1){
						List<KqdsCkGoods> hList=new ArrayList<KqdsCkGoods>();
					    for (KqdsCkGoods kqdsCkGoods : gList) {
					      boolean b = hList.stream().anyMatch(k -> k.getGoodsdetailid().equals(kqdsCkGoods.getGoodsdetailid()));
					      if (!b) {
					    	  hList.add(kqdsCkGoods);
					      }
					    }
						//批量修改仓库表数据
						dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS+".updateGoodsByNumList", hList);
					}
				}
			}
		return null;
	}
	/**
	 * 未出库的第一批之后的商品
	 * @param person
	 * @param menzhen
	 * @param dp
	 * @param detail
	 * @param outNum
	 * @param list1
	 * @param money
	 * @param i
	 * @return
	 * @throws Exception
	 */
	private BigDecimal addOut(YZPerson person, String menzhen, KqdsCkGoodsOut dp, KqdsCkGoodsOutDetail detail,
			int outNum, List<JSONObject> list1, BigDecimal money, int i) throws Exception {
		detail.setSeqId(YZUtility.getUUID());
		detail.setOutnum(outNum);
		String inprice1 = (String) list1.get(i).get("inprice");
		detail.setOutprice(new BigDecimal(inprice1));
		detail.setCkmoney((new BigDecimal(inprice1)).multiply(new BigDecimal(outNum)));
		money=money.add(detail.getCkmoney());
		detail.setOutcode(dp.getOutcode());
		detail.setOrganization(menzhen);
		detail.setCreateuser(person.getSeqId());
		detail.setCreatetime(dp.getCreatetime());
		
		//入库编号和入库时间
		detail.setAddTime(list1.get(i).getString("createtime"));
		detail.setAddNumber(list1.get(i).getString("incode"));
		
		logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, detail);
		return money;
	}
	/**
	 * 未出库的第一批商品
	 * @param person
	 * @param menzhen
	 * @param dp
	 * @param detail
	 * @param list1
	 * @param n
	 * @param i12
	 * @return
	 * @throws Exception
	 */
	private BigDecimal addOutDetail(YZPerson person, String menzhen, KqdsCkGoodsOut dp, KqdsCkGoodsOutDetail detail,
			List<JSONObject> list1, int n, int i12) throws Exception {
		BigDecimal money;
		String detailid = YZUtility.getUUID();
		detail.setSeqId(detailid);
		detail.setOutnum(i12);
		String inprice = (String) list1.get(n).get("inprice");
		detail.setOutprice(new BigDecimal(inprice) );
		detail.setCkmoney((new BigDecimal(inprice)).multiply(new BigDecimal(i12)));
		money=detail.getCkmoney();
		detail.setOutcode(dp.getOutcode());
		detail.setOrganization(menzhen);
		detail.setCreateuser(person.getSeqId());
		detail.setCreatetime(dp.getCreatetime());
		//入库编号和入库时间
		detail.setAddTime(list1.get(n).getString("createtime"));
		detail.setAddNumber(list1.get(n).getString("incode"));
		detail.setPh("");
		logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, detail);
		return money;
	}
	/**
	 * 将当前的库房提交的数据和科室申请数据进行对比，将库房修改的内容更新到科室申请的数据中
	 * @param request
	 * @throws UnsupportedEncodingException 
	 */
	private List<KsllCollorDetail> checkToKsllColor(HttpServletRequest request) throws Exception{
		//获取库房出库提交上来的明细数据
		String params = request.getParameter("paramDetail");
		params = java.net.URLDecoder.decode(params, "UTF-8");
		JSONArray jArray = JSONArray.fromObject(params);
		List<KqdsCkGoodsOutDetail> jList = (List<KqdsCkGoodsOutDetail>) JSONArray.toCollection(jArray, KqdsCkGoodsOutDetail.class);
		//获取科室申领数据
		String ksllCollorId = request.getParameter("ksllCollorId");
		List<JSONObject> detailJson = ksllCollorDetailDao.findDetailByParendId(ksllCollorId);
		//将库房提交数据转成map，这里需要保证科室申领时相同编号的商品不能重复
		Map<String,KqdsCkGoodsOutDetail> jMap = new HashMap<String,KqdsCkGoodsOutDetail>();
		if(jList.size()==1){
			for(KqdsCkGoodsOutDetail kqdsCkGoodsOutDetail :jList ) {
				jMap.put(kqdsCkGoodsOutDetail.getGoodscode(), kqdsCkGoodsOutDetail);
			}
		}else{
			for (int i = 0; i < jList.size(); i++) {
				int nums=jList.get(i).getOutnum();
				for (int j = 0; j < jList.size(); j++) {
					if(i!=j&&jList.get(i).getGoodsuuid().equals(jList.get(j).getGoodsuuid())){
						nums=nums+jList.get(j).getOutnum();
					}
				}
				if(jMap.get(jList.get(i).getGoodscode())==null){
					KqdsCkGoodsOutDetail goods=new KqdsCkGoodsOutDetail();
					goods.setGoodscode(jList.get(i).getGoodscode());
					goods.setOutnum(nums);
					jMap.put(goods.getGoodscode(), goods);
				}
			}
		}
		
		List<KsllCollorDetail> changeKsllList = new ArrayList<KsllCollorDetail>();
		List<KsllCollorDetail> realKsllList = new ArrayList<KsllCollorDetail>();
		KsllCollorDetail ksllCollorDetail = null;
		for(JSONObject jo : detailJson) {
			ksllCollorDetail = new KsllCollorDetail();
			KqdsCkGoodsOutDetail outDetail = jMap.get(jo.getString("goodscode"));
			if(null != outDetail) { //如果存在说明没有删除只改了数据或没有变动
				if(Integer.valueOf(outDetail.getOutnum()) == Integer.valueOf(jo.getString("cknums"))) { //如果数量相同则不用修改
					ksllCollorDetail.setId(jo.getString("id"));
					ksllCollorDetail.setNums(outDetail.getOutnum());
					ksllCollorDetail.setGoodscode(jo.getString("goodscode"));
					realKsllList.add(ksllCollorDetail);
				}else {
					ksllCollorDetail.setId(jo.getString("id"));
					ksllCollorDetail.setNums(outDetail.getOutnum());
					ksllCollorDetail.setGoodscode(jo.getString("goodscode"));
					ksllCollorDetail.setStatus(KSLLDETAIL_STATUS_YXG);
					ksllCollorDetail.setCkchange("仓管将数量由"+jo.getString("cknums")+"修改为"+outDetail.getOutnum());
					ksllCollorDetail.setCkchangeuser(YZUtility.getCurrLoginPersonSeqId(request));
					changeKsllList.add(ksllCollorDetail);
					realKsllList.add(ksllCollorDetail);
				}
			}else {
				ksllCollorDetail.setId(jo.getString("id"));
				ksllCollorDetail.setStatus(KSLLDETAIL_STATUS_YSC);
				ksllCollorDetail.setCkchange("仓管已删除");
				ksllCollorDetail.setCkchangeuser(YZUtility.getCurrLoginPersonSeqId(request));
				changeKsllList.add(ksllCollorDetail);
			}
		}
		ksllCollorDetailDao.batchUpdateKsllDetailByPrimaryId(changeKsllList);
		return realKsllList;
	}
	
	@Override
	public List<JSONObject> findKsllColorDetailByparentid(String parentId) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = new ArrayList<>();
		list = ksllCollorDetailDao.findDetailByParendId(parentId);
		return list;
	}

	@Override
	public void updateNumsById(Map<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		ksllCollorDetailDao.updateNumsById(dataMap);
	}
	
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void deleteKsllNotCK(String id) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> dataMap = null;
		//更新科室领料主表
		dataMap = new HashMap<String,String>();
		dataMap.put("id", id);
		dataMap.put("status", KSLL_STATUS_YSC + "");
		ksllCollorDao.updateCollorStatus(dataMap);
		
		//更新明细表数据
		dataMap = new HashMap<String,String>();
		dataMap.put("id", id);
		dataMap.put("status", KSLL_STATUS_YSC + "");
		ksllCollorDetailDao.updateCollorDetail(dataMap);
	}

	@Override
	public List<JSONObject> findAllKsllColorGoods(Map<String, String> dataMap) throws Exception {
		List<JSONObject> list = ksllCollorGoodsDao.findAllKsllGoods(dataMap);
		return list;
	}
	
	@Override
	public Map<String,JSONObject> findAllKsllColorGoodsByGoodscode(Map<String, String> dataMap) throws Exception {
		List<JSONObject> list = ksllCollorGoodsDao.findAllKsllColorGoodsByGoodscode(dataMap);
		Map<String,JSONObject> ksllGoodMap = new HashMap<String,JSONObject>();
		if(list.size()>0){
			for(JSONObject obj : list) {
				ksllGoodMap.put(obj.getString("goodscode"), obj);
			}
		}
		return ksllGoodMap;
	}
	
	@Override
	public Map<String,JSONObject> findAllCkGoodsByGoodscode(String goodscode,String organization) throws Exception {
		Map<String,JSONObject> ckGoodMap = new HashMap<String,JSONObject>();
		List<JSONObject> list = glogic.findAllCkGoodsByGoodscode(goodscode, organization);
		for(JSONObject obj : list) {
			String key = obj.getString("goodscode");
			if(YZUtility.isNotNullOrEmpty(key)) {
				ckGoodMap.put(key, obj);
			}
		}
		return ckGoodMap;
	}

	@Override
	public List<JSONObject> selectAllGoodPhByGoodCode(Map<String, String> dataMap) throws Exception {
		List<JSONObject> list = ksllCollorDetailDao.findDetailPhByGoodscode(dataMap);
		//根据批号和商品id查询单价
		List<JSONObject> list1=new ArrayList<JSONObject>();
		KsllGoods ksllGoods = ksllCollorGoodsDao.findKsllGoodsByDeptpartAndGoodscode(dataMap);
		if(list.size()>0){
			int phnum=0;
			for (JSONObject jsonObject : list) {
				phnum+=jsonObject.getInt("phnum");
			}
			//批号数量小于库存数量时添加请选择的选项
			if(phnum<ksllGoods.getNums()){
				JSONObject json= new JSONObject();
				json.put("ph", "请选择");
				json.put("phnum", ksllGoods.getNums()-phnum);
				list1.add(json);
				list1.addAll(list);
			}else{
				list1.addAll(list);
			}
		}else{
			JSONObject json= new JSONObject();
			json.put("ph", "请选择");
			json.put("phnum", ksllGoods.getNums());
			list1.add(json);
		}
		return list1;
	}

}
