package com.kqds.service.ck;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;

@Service
public class KQDS_Ck_GoodsLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 收发存查询(不区分仓库)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectNoHouseListSfc(String table, Map<String, String> map,BootStrapPage bp, JSONObject json, String organization) throws Exception {
		map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoney", 18, 3));
		/**
		 * 添加分页
		 */
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		@SuppressWarnings("unchecked")
		//-------------------------------------------------------------------------------------------------------------------------------
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListSfc", map);
		//-------------------------------------------------------------------------------------------------------------------------------
		for (JSONObject jobj : list) {
			int nums = 0;
			if (!YZUtility.isNullorEmpty(jobj.getString("nums"))) {
				nums = jobj.getInt("nums");
			}
			if (nums == 0) {
				jobj.put("goodsprice", "0.000");
			} else {
				BigDecimal kcmoney = new BigDecimal(jobj.getString("kcmoney"));
				BigDecimal goodsprice = KqdsBigDecimal.div(kcmoney, new BigDecimal(nums));
				jobj.put("goodsprice", goodsprice);
			}
		}
		list = searchValue(list, map, organization, true);
		//-------------------------------------------------------------------------------------------------------------------------------
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		return jobj1;
//		return list;
	}

	/**
	 * 收发存查询(区分仓库)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectListSfc(String table, Map<String, String> map,BootStrapPage bp, JSONObject json, String organization) throws Exception {
		map.put("selectGoodsprice", SQLUtil.convertDecimal("g.goodsprice", 18, 3));
		/**
		 * 添加分页
		 */
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		@SuppressWarnings("unchecked")
		//-------------------------------------------------------------------------------------------------------------------------------
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListSfc", map);
		list = searchValue(list, map, organization, false);
		//-------------------------------------------------------------------------------------------------------------------------------
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		return jobj1;
//		return list;
	}

	/* 收发存查询 */
	public List<JSONObject> searchValue(List<JSONObject> list, Map<String, String> map, String organization, Boolean nohouse) throws Exception {
		for (JSONObject obj : list) {
			String sshouse = "";
			if (!nohouse) {
				sshouse = obj.getString("sshouse");
			}
			// 导入数据 现在默认用户第一次使用的时候可以导入，之后导入商品功能关闭
			/*---------------------------期初------------------------ */
			/* 期初数量 = 导入数量 +期初入库数量-期初出库数量 */
			int daorunums = 0;
			if (!YZUtility.isNullorEmpty(obj.getString("numsexport"))) {
				daorunums = obj.getInt("numsexport");// 导入数量
			}
			int qcrknums = rkNumsWithOneTime(sshouse, obj.getString("seqId"), map.get("starttime"), organization);
			int qccknums = ckNumsWithOneTime(sshouse, obj.getString("seqId"), map.get("starttime"), organization);
			int qcnums = daorunums + qcrknums - qccknums;
			obj.put("qcnums", qcnums);
			/* 期初金额 = 导入金额 +期初入库金额-期初出库数量 */
			BigDecimal goodspriceexport = BigDecimal.ZERO;
			if (!YZUtility.isNullorEmpty(obj.getString("goodspriceexport"))) {
				goodspriceexport = new BigDecimal(obj.getString("goodspriceexport"));// 导入金额
			}
			BigDecimal qcrkmoney = rkMoneyWithOneTime(sshouse, obj.getString("seqId"), map.get("starttime"), organization);
			BigDecimal qcckmoney = ckMoneyWithOneTime(sshouse, obj.getString("seqId"), map.get("starttime"), organization);
			BigDecimal qcmoney = goodspriceexport.add(qcrkmoney).subtract(qcckmoney).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			obj.put("qcmoney", qcmoney.toString());
			/* 期初单价 = 期初金额/期初数量 */
			BigDecimal qcsprice = null;
			if (qcnums == 0) {
				qcsprice = new BigDecimal(0.00);
			} else {
				qcsprice = qcmoney.divide(new BigDecimal(qcnums), 2, RoundingMode.HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			}
			obj.put("qcsprice", qcsprice.toString());
			/*---------------------------期内------------------------ */
			/* 期内入库数量 */
			int rknums = rkNums(sshouse, obj.getString("seqId"), map.get("starttime"), map.get("endtime"), organization);
			obj.put("rknums", rknums);
			/* 期内入库金额 */
			BigDecimal rkmoney = rkMoney(sshouse, obj.getString("seqId"), map.get("starttime"), map.get("endtime"), organization);
			obj.put("rkmoney", rkmoney.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			/* 期内入库单价 = 期内入库金额/期内入库数量 */
			BigDecimal rksprice = null;
			if (rknums == 0) {
				rksprice = new BigDecimal(0.00);
			} else {
				rksprice = rkmoney.divide(new BigDecimal(rknums), 2, RoundingMode.HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			}
			obj.put("rksprice", rksprice.toString());

			/* 期内出库数量 */
			int cknums = ckNums(sshouse, obj.getString("seqId"), map.get("starttime"), map.get("endtime"), organization);
			obj.put("cknums", cknums);
			/* 期内入库金额 */
			BigDecimal ckmoney = ckMoney(sshouse, obj.getString("seqId"), map.get("starttime"), map.get("endtime"), organization);
			obj.put("ckmoney", ckmoney.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			/* 期内入库单价 = 期内入库金额/期内入库数量 */
			BigDecimal cksprice = null;
			if (cknums == 0) {
				cksprice = new BigDecimal(0.00);
			} else {
				cksprice = ckmoney.divide(new BigDecimal(cknums), 2, RoundingMode.HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			}
			obj.put("cksprice", cksprice.toString());
			/*---------------------------期末------------------------ */
			/* 期末数量 = 导入数量 +期末入库数量-期初末库数量 */
			int qmrknums = rkNumsWithOneTime(sshouse, obj.getString("seqId"), map.get("endtime"), organization);
			int qmcknums = ckNumsWithOneTime(sshouse, obj.getString("seqId"), map.get("endtime"), organization);
			int qmnums = daorunums + qmrknums - qmcknums;
			obj.put("qmnums", qmnums);
			/* 期末金额 = 导入金额 +期末入库金额-期末出库数量 */
			BigDecimal qmrkmoney = rkMoneyWithOneTime(sshouse, obj.getString("seqId"), map.get("endtime"), organization);
			BigDecimal qmckmoney = ckMoneyWithOneTime(sshouse, obj.getString("seqId"), map.get("endtime"), organization);
			BigDecimal qmmoney = goodspriceexport.add(qmrkmoney).subtract(qmckmoney).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			obj.put("qmmoney", qmmoney.toString());
			/* 期末单价 = 期末金额/期末数量 */
			BigDecimal qmsprice = null;
			if (qmnums == 0) {
				qmsprice = new BigDecimal(0.00);
			} else {
				qmsprice = qmmoney.divide(new BigDecimal(qmnums), 2, RoundingMode.HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			}
			obj.put("qmsprice", qmsprice.toString());
		}

		return list;
	}

	/* 期初入库数量 */
	@SuppressWarnings("unchecked")
	public int rkNumsWithOneTime(String sshouse, String goodsuuid, String starttime, String organization) throws Exception {
		int rknums = 0;
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsuuid);
		jobj.put("starttime", starttime);
		jobj.put("sshouse", sshouse);
		jobj.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".rkNumsWithOneTime", jobj);
		if (list != null && list.size() > 0) {
			rknums = YZUtility.isNullorEmpty(list.get(0).getString("rknums")) ? 0 : list.get(0).getInt("rknums");
		}
		return rknums;
	}

	/* 期初入库金额 */
	@SuppressWarnings("unchecked")
	public BigDecimal rkMoneyWithOneTime(String sshouse, String goodsuuid, String starttime, String organization) throws Exception {
		BigDecimal rkmoney = null;
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsuuid);
		jobj.put("starttime", starttime);
		jobj.put("sshouse", sshouse);
		jobj.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".rkMoneyWithOneTime", jobj);
		if (list != null && list.size() > 0) {
			JSONObject json = list.get(0);
			String d = YZUtility.isNullorEmpty(json.getString("rkmoney")) ? "0.00" : json.getString("rkmoney");
			rkmoney = new BigDecimal(d);
		}
		return rkmoney;
	}

	/* 期内入库数量 */
	@SuppressWarnings("unchecked")
	public int rkNums(String sshouse, String goodsuuid, String starttime, String endtime, String organization) throws Exception {
		int rknums = 0;
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsuuid);
		jobj.put("starttime", starttime);
		jobj.put("endtime", endtime);
		jobj.put("sshouse", sshouse);
		jobj.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".rknums", jobj);
		if (list != null && list.size() > 0) {
			rknums = YZUtility.isNullorEmpty(list.get(0).getString("rknums")) ? 0 : list.get(0).getInt("rknums");
		}
		return rknums;
	}

	/* 期内入库金额 */
	@SuppressWarnings("unchecked")
	public BigDecimal rkMoney(String sshouse, String goodsuuid, String starttime, String endtime, String organization) throws Exception {
		BigDecimal rkmoney = null;
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsuuid);
		jobj.put("starttime", starttime);
		jobj.put("endtime", endtime);
		jobj.put("sshouse", sshouse);
		jobj.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".rkmoney", jobj);
		if (list != null && list.size() > 0) {
			JSONObject json = list.get(0);
			String d = YZUtility.isNullorEmpty(json.getString("rkmoney")) ? "0.00" : json.getString("rkmoney");
			rkmoney = new BigDecimal(d);
		}
		return rkmoney;
	}

	/* 期初出库数量 */
	@SuppressWarnings("unchecked")
	public int ckNumsWithOneTime(String sshouse, String goodsuuid, String starttime, String organization) throws Exception {
		int rknums = 0;
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsuuid);
		jobj.put("starttime", starttime);
		jobj.put("sshouse", sshouse);
		jobj.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT + ".ckNumsWithOneTime", jobj);
		if (list != null && list.size() > 0) {
			rknums = YZUtility.isNullorEmpty(list.get(0).getString("rknums")) ? 0 : list.get(0).getInt("rknums");
		}
		return rknums;
	}

	/* 期初出库金额 */
	@SuppressWarnings("unchecked")
	public BigDecimal ckMoneyWithOneTime(String sshouse, String goodsuuid, String starttime, String organization) throws Exception {
		BigDecimal ckmoney = null;
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsuuid);
		jobj.put("starttime", starttime);
		jobj.put("sshouse", sshouse);
		jobj.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT + ".ckMoneyWithOneTime", jobj);
		if (list != null && list.size() > 0) {
			JSONObject json = list.get(0);
			String d = YZUtility.isNullorEmpty(json.getString("ckmoney")) ? "0.00" : json.getString("ckmoney");
			ckmoney = new BigDecimal(d);
		}
		return ckmoney;
	}

	/* 期内出库数量 */
	@SuppressWarnings("unchecked")
	public int ckNums(String sshouse, String goodsuuid, String starttime, String endtime, String organization) throws Exception {
		int rknums = 0;
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsuuid);
		jobj.put("starttime", starttime);
		jobj.put("endtime", endtime);
		jobj.put("sshouse", sshouse);
		jobj.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT + ".cknums", jobj);
		if (list != null && list.size() > 0) {
			rknums = YZUtility.isNullorEmpty(list.get(0).getString("rknums")) ? 0 : list.get(0).getInt("rknums");
		}
		return rknums;
	}

	/* 期内出库金额 */
	@SuppressWarnings("unchecked")
	public BigDecimal ckMoney(String sshouse, String goodsuuid, String starttime, String endtime, String organization) throws Exception {
		BigDecimal ckmoney = null;
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsuuid);
		jobj.put("starttime", starttime);
		jobj.put("endtime", endtime);
		jobj.put("sshouse", sshouse);
		jobj.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT + ".ckmoney", jobj);
		if (list != null && list.size() > 0) {
			JSONObject json = list.get(0);
			String d = YZUtility.isNullorEmpty(json.getString("ckmoney")) ? "0.00" : json.getString("ckmoney");
			ckmoney = new BigDecimal(d);
		}
		return ckmoney;
	}
	
	/**
	 * 更新仓库商品单价  结存金额 2019-4-15
	 * @param goodsno
	 * @throws Exception
	 */
	public void updateCkGoodsByGoodsno(KqdsCkGoods dp) throws Exception {
		dao.update(TableNameUtil.KQDS_CK_GOODS + ".updateCkGoodsByGoodsno", dp);
	}
	
	/**
	 * 根据明细表id查询商品库存表信息2019-4-15
	 * @param detailid
	 * @return
	 * @throws Exception
	 */
	public KqdsCkGoods findCkGoodsByDetailid(String detailid) throws Exception {
		KqdsCkGoods kqdsCkGoods = (KqdsCkGoods) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".findCkGoodsByDetailid", detailid);
		return kqdsCkGoods;
	}
	
	/**
	 * 仓库余量
	 * @return
	 * @throws Exception
	 */
	public int ckNums1(String goodsuuid) throws Exception {
		int i = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".cknums", goodsuuid);
		return i;
	}
	
	/**
	 * 仓库余量
	 * @return
	 * @throws Exception
	 */
	public int ckNums2(String goodsuuid) throws Exception {
		int i = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".cknum", goodsuuid);
		return i;
	}
	
	public void updatetCkByKsllReplaceMent(List<KqdsCkGoods> list) throws Exception {
		dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS + ".updatetCkByKsllReplaceMent", list);
	}
	public void updatetCkByKsllReplaceMents(List<KqdsCkGoods> list) throws Exception {
		dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS + ".updatetCkByKsllReplaceMents", list);
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllCkGoodsByGoodscode(String goodscode, String organization) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("goodscode", goodscode);
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS + ".findAllCkGoodsByGoodscode", map);
		return list;
	}

	/**   
	  * @Title: getNumber   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年3月23日 下午4:32:01
	  */  
	public JSONObject getNumber(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL+".getNumber", map);
	}

	/**   
	  * @Title: getName   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年4月7日 上午10:05:11
	  */  
	public String getTypeName(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jsonObject = (JSONObject)dao.findForObject(TableNameUtil.KQDS_CK_GOODS_TYPE+".findHouse", map);
		String case1 = ChineseCharToEn.getAllFirstLetter(jsonObject.getString("house").substring(0, 2)).toUpperCase();
		return case1;
	}

	/**   
	  * @Title: unallowable   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年4月30日 上午9:12:06
	  */  
	@Transactional
	public void unallowable(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		dao.update(TableNameUtil.KQDS_CK_GOODS+".unallowable", map);
	}

	/**   
	  * @Title: findGoodsById   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param seqId
	  * @param: @return      
	  * @return: JSONObject
	 * @throws Exception 
	  * @dateTime:2020年4月30日 上午10:15:56
	  */ 
	@Transactional
	public JSONObject findGoodsById(String seqId) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".findGoodsById", seqId);
	}
}