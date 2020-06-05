package com.kqds.service.ck;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DAO;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;


@Service
public class KQDS_Ck_Goods_DetailLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private YZPersonLogic personLogic;

	/**
	 * 根据商品编号，查询是否存在
	 * 
	 * @param conn
	 * @param dp
	 * @return
	 * @throws Exception
	 */
	public int countByGoodsCode(KqdsCkGoodsDetail dp) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".countByGoodsCode", dp);
		return count;
	}

	/**
	 * 商品列表(区分仓库：仓库首页面调用，当前库存页面,选择商品 调用)
	 * @param json 
	 * @param bp 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectList(Map<String, String> map, BootStrapPage bp, JSONObject json) throws Exception {
		//-------------------------------------------------------------------------------------------------------------------------------
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = new ArrayList<JSONObject>();
		if(map.get("type").equals("2")){
			map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoneys", 18, 3));
			/*map.put("selectGoodsprice", SQLUtil.convertDecimal("g.goodsprices", 18, 3));*/
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectList2", map);
			for (JSONObject jobj : list) {
				int num = 0;
				if (!YZUtility.isNullorEmpty(jobj.getString("nums"))) {
					num = jobj.getInt("nums");
				}
				if (num == 0) {
					jobj.put("goodsprice", "0.000");
				} else {
					BigDecimal kcmoney = new BigDecimal(jobj.getString("kcmoney"));
					BigDecimal goodsprice = KqdsBigDecimal.div(kcmoney, new BigDecimal(num));
					jobj.put("goodsprice", goodsprice);
				}
			}
		}else{
			map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoney", 18, 3));
			map.put("selectGoodsprice", SQLUtil.convertDecimal("g.goodsprice", 18, 3));
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectList", map);
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
		}
		//--------------------------------------------------------------------------------------------------------------------------------
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();		
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 商品列表(不区分仓库：仓库首页面调用，当前库存页面,选择商品 调用)
	 * @param json 
	 * @param bp 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectNoHouseList(Map<String, String> map, BootStrapPage bp, JSONObject json) throws Exception {
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = new ArrayList<JSONObject>();
		if(map.get("type").equals("2")){
			map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoneys", 18, 3));
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseList2", map);
		}else{
			map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoney", 18, 3));
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseList", map);
		}
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
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();		
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		return jobj1;
	}

	/**
	 * 商品列表(区分仓库：仓库首页面调用，当前库存页面,选择商品 调用)
	 * @param json 
	 * @param bp 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectListOrdertime(Map<String, String> map, BootStrapPage bp, JSONObject json) throws Exception {
		map.put("selectKcmoney", SQLUtil.convertDecimal("g.goodspriceexport", 18, 3));
		//-------------------------------------------------------------------------------------------------------------------------------
		List<JSONObject> list=null;
		JSONObject jobj1 = new JSONObject();	
		if(map.get("flag")!=null&&map.get("supplier")!=null){
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertimeS", map);
		}else if(map.get("flag")!=null&&map.get("supplier")==null){
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertime", map);
		}else {
			String search = bp.getSearch();
			String sort = bp.getSort();
			json.put("search", search);
			json.put("sort", sort);
			// 分页插件
			PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
			// 分页插件后的查询，会自动进行分页
			if(map.get("supplier")!=null){
				list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertimeS", map);
			}else{
				list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertime", map);
			}
			PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
			jobj1.put("total", pageInfo.getTotal());
		}
				
		//-------------------------------------------------------------------------------------------------------------------------
		
		for (JSONObject jobj : list) {
			String goodsdetailid = jobj.getString("seq_id");
			map.put("goodsdetailid", goodsdetailid);
			List <JSONObject> inobjs = (List <JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".getOneByDetailid", map);
			JSONObject outobj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_OUT + ".getOneByDetailid", map);

			int nums = 0;
			BigDecimal kcmoney = BigDecimal.ZERO;
			// 初始化数量 + 入库数量 -出库数量
			// 初始化金额 + 入库金额 - 出库金额
			if (!YZUtility.isNullorEmpty(jobj.getString("numsexport"))) {
				nums = jobj.getInt("numsexport");
				kcmoney = new BigDecimal(jobj.getString("kcmoney"));
			}

			
				for (JSONObject inobj : inobjs) {
					if (inobj != null && inobj.containsKey("nums")) {
						if (!YZUtility.isNullorEmpty(inobj.getString("nums"))) {
							nums = nums + inobj.getInt("nums");
							kcmoney = kcmoney.add(new BigDecimal(inobj.getString("rkmoney")));
						}
					}
				}
				
				if (outobj != null && outobj.containsKey("nums")) {
					if (!YZUtility.isNullorEmpty(outobj.getString("nums"))) {
						nums = nums - outobj.getInt("nums");
						kcmoney = kcmoney.subtract(new BigDecimal(outobj.getString("rkmoney")));
					}
				}
			
			jobj.put("nums", nums);
			jobj.put("kcmoney", kcmoney);
			if (nums == 0) {
				jobj.put("goodsprice", "0.000");
			} else {
				BigDecimal goodsprice = KqdsBigDecimal.div(kcmoney, new BigDecimal(nums));
				jobj.put("goodsprice", goodsprice);
			}
		}
		//List<JSONObject> list2=null;
		//if(map.get("supplier")!=null){
		//	list2 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertimeSeqidS", map);
		//}else{
		//	list2 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectListOrdertimeSeqid", map);
		//}
//		System.err.println(list2);
		//-----------------------------------------------------------------------------------------------------------
		
		jobj1.put("rows", list);
		//map1.put("nums", nums1+"");
		//map1.put("kcmoney", kcmoney1+"");
		//for (JSONObject jsonObject : list2) {
			//jsonObject.put("nums", Integer.parseInt((String)jsonObject.get("innums"))-Integer.parseInt((String)jsonObject.get("outnums")));
			//jsonObject.put("kcmoney", new BigDecimal(jsonObject.get("numsexport")+"").add(new BigDecimal(jsonObject.get("rkmoney")+"")).subtract(new BigDecimal(jsonObject.get("ckmoney")+"")));
		//}
		//jobj1.put("nums", list2);
		return jobj1;		
	}

	/**
	 * 商品列表(不区分仓库：仓库首页面调用，当前库存页面,选择商品 调用)
	 * @param json 
	 * @param bp 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectNoHouseListOrdertime(Map<String, String> map, BootStrapPage bp, JSONObject json) throws Exception {
		map.put("selectKcmoney", SQLUtil.convertDecimal("g.kcmoney", 18, 3));
		//-------------------------------------------------------------------------------------------------------------------------------
		
		List<JSONObject> list=null;
		JSONObject jobj1 = new JSONObject();
		if(map.get("flag")!=null&&map.get("supplier")!=null){
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertimeS", map);
		}else if(map.get("flag")!=null&&map.get("supplier")==null){
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertime", map);
		}else {
				// 分页插件
				PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
				// 分页插件后的查询，会自动进行分页
				String search = bp.getSearch();
				String sort = bp.getSort();
				json.put("search", search);
				json.put("sort", sort);
		//--------------------------------------------------------------------------------------------------------------------------------
		if(map.get("supplier")!=null){
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertimeS", map);
		}else{
			list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertime", map);
		}	
		
		//--------------------------------------------------------------------------------------------------------------------------------
				PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
				jobj1.put("total", pageInfo.getTotal());
		}
		//-------------------------------------------------------------------------------------------------------------------------
		for (JSONObject jobj : list) {
			String goodsdetailid = jobj.getString("seq_id");
			map.put("goodsdetailid", goodsdetailid);
			JSONObject inobj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN + ".getOneByDetailid", map);
			JSONObject outobj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_OUT + ".getOneByDetailid", map);

			int nums = 0;
			// 初始化数量 + 入库数量 -出库数量
			// 初始化金额 + 入库金额 - 出库金额
			BigDecimal kcmoney = BigDecimal.ZERO;
			if (!YZUtility.isNullorEmpty(jobj.getString("nums"))) {
				nums = jobj.getInt("nums");
				kcmoney = new BigDecimal(jobj.getString("kcmoney"));
			}

			if (inobj != null && inobj.containsKey("nums")) {
				if (!YZUtility.isNullorEmpty(inobj.getString("nums"))) {
					nums = nums + inobj.getInt("nums");
					kcmoney = kcmoney.add(new BigDecimal(inobj.getString("rkmoney")));
				}
			}
			if (outobj != null && outobj.containsKey("nums")) {
				if (!YZUtility.isNullorEmpty(outobj.getString("nums"))) {
					nums = nums - outobj.getInt("nums");
					kcmoney = kcmoney.subtract(new BigDecimal(outobj.getString("rkmoney")));
				}
			}

			jobj.put("nums", nums);
			jobj.put("kcmoney", kcmoney);
			if (nums == 0) {
				jobj.put("goodsprice", "0.000");
			} else {
				BigDecimal goodsprice = KqdsBigDecimal.div(kcmoney, new BigDecimal(nums));
				jobj.put("goodsprice", goodsprice);
			}
		}
		//-----------------------------------------------------------------------------------------------------------
			
			jobj1.put("rows", list);
			//List<JSONObject> list2=null;
			//if(map.get("supplier")!=null){
			//	list2 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertimeSeqidS", map);
			//}else{
			//	list2 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectNoHouseListOrdertimeSeqid", map);
			//}
			//for (JSONObject jsonObject : list2) {
			//	jsonObject.put("nums", Integer.parseInt((String)jsonObject.get("innums"))-Integer.parseInt((String)jsonObject.get("outnums")));
			//	jsonObject.put("kcmoney", new BigDecimal(jsonObject.get("numsexport")+"").add(new BigDecimal(jsonObject.get("rkmoney")+"")).subtract(new BigDecimal(jsonObject.get("ckmoney")+"")));
			//}
			//jobj1.put("nums", list2);
			return jobj1;
	}

	/**
	 * 根据分类编号查询商品（分类编号包括一级、二级分类）
	 * 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int selectSizeByTypeno(Map<String, String> map) throws Exception {
		int size = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectSizeByTypeno", map);
		return size;
	}

	/**
	 * 商品基本资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectGoodsDetailList(Map<String, String> map,BootStrapPage bp, JSONObject json) throws Exception {
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
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectGoodsDetailList", map);
		for (JSONObject jsonObject : list) {
			String maxgj = jsonObject.getString("maxgj");
			if ("1".equals(maxgj)) {
				jsonObject.put("maxgj", "开启");
			}
			String mingj = jsonObject.getString("mingj");
			if ("1".equals(mingj)) {
				jsonObject.put("mingj", "关闭");
			}
		}
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		return jobj1;
//		return list;
	}

	/**
	 * 库存报警数量（首页）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public int getCountIndex(String organization) throws Exception {
		int count = 0;
		Map<String, String> map = new HashMap<>();
		map.put("organization", organization);
		List<JSONObject> list = selectGoodsGjNoHousseList(map);
		if (list != null && list.size() > 0) {
			count = list.size();
		}
		return count;
	}

	/**
	 * 库存报警查询(不区分仓库)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectGoodsGjNoHousseList(Map<String, String> map) throws Exception {
		if (map.size() == 0) {
			throw new Exception("参数map不能为空");
		}

		if (!map.containsKey("organization")) {
			throw new Exception("库存报警查询时，门诊编号不能为空值");
		}

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectGoodsGjNoHousseList", map);
		return list;
	}

	/**
	 * 库存报警查询(区分仓库)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectGoodsGjList(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectGoodsGjList", map);
		return list;
	}

	/**
	 * 库存报警提醒
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void selectGoodsGjTx(List<JSONObject> list, String ckpriv, String organization) throws Exception {
		// 查询仓库人员
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("user_priv", ckpriv);
		List<YZPerson> perlist = personLogic.getPersonListByRole(ckpriv, organization);
		for (JSONObject jsonObject : list) {
			String firsttype = jsonObject.getString("firsttype");
			String goodstype = jsonObject.getString("goodstype");
			String goodsname = jsonObject.getString("goodsname");
			String orgaization = jsonObject.getString("organization");
			int nums = jsonObject.getInt("nums");
			int minnums = jsonObject.getInt("minnums");
			int mingj = jsonObject.getInt("mingj");
			int maxnums = jsonObject.getInt("maxnums");
			int maxgj = jsonObject.getInt("maxgj");
			// 下限报警
			if (1 == mingj && nums < minnums) {
				// ###添加消息提示
				String content = "库存下限报警:" + firsttype + "-" + goodstype + " " + goodsname + ",下限报警数：" + minnums + ",当前数量：" + nums;
				for (YZPerson per : perlist) {
					PushUtil.saveTx4CKDownAlarm(per, content, orgaization);
				}
			}
			// 上限报警
			if (1 == maxgj && nums > maxnums) {
				// ###添加消息提示
				String content = "库存上限报警:" + firsttype + "-" + goodstype + " " + goodsname + ",上限报警数：" + minnums + ",当前数量：" + nums;
				for (YZPerson per : perlist) {
					PushUtil.saveTx4CKTopAlarm(per, content, orgaization);
				}
			}
		}
	}

	public int getCountInByItemnos(String seqId) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectCountByGoodsuuid", YZUtility.ConvertStringIds4Query(seqId));
		return count;
	}

	public int getCountOutByItemnos(String seqId) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL + ".selectCountByGoodsuuid", YZUtility.ConvertStringIds4Query(seqId));
		return count;
	}
	
	/**
	 * 根据入库表查询对应商品入库时选的厂家-库房入库同一种商品每次入库厂家是一致的
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectGoodSupplierByInGoods(String goodsuuid) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("goodsuuid", goodsuuid);
		List<JSONObject> list= (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".selectGoodSupplierByInGoods", map);
		return list;
	}
	
	/**
	 * 根据商品编号查询商品库存明细2019-4-15
	 * @param goodscode
	 * @return
	 * @throws Exception
	 */
	public JSONObject findGoodsDetailByGoodscode(String goodscode) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".findGoodsDetailByGoodscode", goodscode);
		return json;
	}

	/**   
	  * @Title: updateGoods   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param string
	  * @param: @param kqdsckGood      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年4月3日 下午3:52:02
	  */  
	public void updateGoods(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		dao.update(TableNameUtil.KQDS_CK_GOODS + ".updateGoods", map);
	}
	
}