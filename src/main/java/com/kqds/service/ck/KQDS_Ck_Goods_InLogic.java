package com.kqds.service.ck;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.lclj.StaticVar;
import com.hudh.lclj.dao.SysParaDao;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsIn;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class KQDS_Ck_Goods_InLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	
	@Autowired
	private SysParaDao sysParaDao;
	
	@Autowired
	private KQDS_Ck_Goods_In_DetailLogic logic;

	/**
	 * 根据供应商统计数量
	 * 
	 * @param conn
	 * @param supplier
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public int countBySupplier(String supplier, String organization) throws Exception {
		JSONObject jobj = new JSONObject();
		jobj.put("supplier", supplier);
		jobj.put("organization", organization);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN + ".countBySupplier", jobj);
		return count;
	}

	/**
	 * 入库明细查询
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> inSearchList(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".inSearchList", map);
		for (JSONObject job : list) {
			String intype = job.getString("intype");
			if (ConstUtil.CK_IN_0.equals(intype)) {
				intype = "采购入库";
			} else if (ConstUtil.CK_IN_2.equals(intype)) {
				intype = "换货入库";
			} else if (ConstUtil.CK_IN_4.equals(intype)) {
				intype = "其他入库";
			}else if(ConstUtil.CK_IN_8.equals(intype)){
				intype = "调拨入库";
			}else if(ConstUtil.CK_IN_9.equals(intype)){
				intype = "二次入库";
			}
			job.put("intype", intype);
		}
		return list;
	}

	public String selectZczh(String goodsid, String organization) throws Exception {
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsid);
		jobj.put("organization", organization);
		String zczh = (String) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectZczh", jobj);
		return zczh;
	}

	public String selectInprice(String goodsid, String organization) throws Exception {
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsid);
		jobj.put("organization", organization);
		String inprice = (String) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectInprice", jobj);
		return inprice;
	}

	public String selectCd(String goodsid, String organization) throws Exception {
		JSONObject jobj = new JSONObject();
		jobj.put("goodsuuid", goodsid);
		jobj.put("organization", organization);
		String cd = (String) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectCd", jobj);
		return cd;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> inList(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".timeOrder", map);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> inListByPh(Map<String,String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".timeOrderByPh", map);
		return list;
	}
	/**
	 * 查询批号单价是否相同
	 * <p>Title: selectNumByPh</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月10日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectPriceByPh(String goodsuuid,String ph,int outNum,String type) throws Exception {
		Map<String,String> map=new HashMap<String,String>();
		map.put("goodsuuid", goodsuuid);
		map.put("ph", ph);
		map.put("type", type);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectPriceByPh", map);
		JSONObject json = new JSONObject();
		if(list.size()>0){
			int m=0;
			int j=0;
			for (int i = 0; i < list.size(); i++) {
				m+=list.get(i).getInt("phnum");
				if(m>=outNum){
					j=i;
					break;
				}
			}
			List<JSONObject> numlist=new ArrayList<JSONObject>();
			if(j>0){
				BigDecimal money= new BigDecimal("0");
				BigDecimal moneyAll= new BigDecimal("0");
				int out=outNum;
				for (int i = 0; i < list.size(); i++) {
					JSONObject obj=new JSONObject();
					if(list.get(i).getInt("phnum")>=outNum){
						money=new BigDecimal(list.get(i).getString("inprice")).multiply(new BigDecimal(outNum));
						moneyAll=moneyAll.add(money);
						obj.put("cknum", outNum);
						obj.put("inprice", list.get(i).getString("inprice"));
						obj.put("seqid", list.get(i).getString("seqid"));
						obj.put("goodsuuid", list.get(i).getString("goodsuuid"));
						obj.put("createtime", list.get(i).getString("createtime"));
						obj.put("incode", list.get(i).getString("incode"));
						obj.put("yxdate", list.get(i).getString("yxdate"));
						obj.put("ph", ph);
						numlist.add(obj);
						break;
					}else{
						money=new BigDecimal(list.get(i).getString("inprice")).multiply(new BigDecimal(list.get(i).getInt("phnum")));
						outNum=outNum-list.get(i).getInt("phnum");
						moneyAll=moneyAll.add(money);
						obj.put("cknum", list.get(i).getInt("phnum"));
						obj.put("inprice", list.get(i).getString("inprice"));
						obj.put("seqid", list.get(i).getString("seqid"));
						obj.put("goodsuuid", list.get(i).getString("goodsuuid"));
						obj.put("createtime", list.get(i).getString("createtime"));
						obj.put("incode", list.get(i).getString("incode"));
						obj.put("yxdate", list.get(i).getString("yxdate"));
						obj.put("ph", ph);
						numlist.add(obj);
					}
				}
				json.put("goodsprice", moneyAll.divide(new BigDecimal(out),3, RoundingMode.HALF_UP));
				json.put("ckmoney", moneyAll);
				json.put("numlist", numlist);
				return json;
			}else{
				if(list.get(0).getString("inprice") != null && !list.get(0).getString("inprice").equals("")){
					json.put("goodsprice", list.get(0).getString("inprice"));
					json.put("ckmoney", new BigDecimal(list.get(0).getString("inprice")).multiply(new BigDecimal(outNum)));
				}else{
					json.put("goodsprice", new BigDecimal("0.00"));
					json.put("ckmoney", new BigDecimal("0.00"));
				}
				list.get(0).put("cknum", outNum);
				numlist.add(list.get(0));
				json.put("numlist", numlist);
				return json;
			}
		}
		return null;
	}
	//查询商品入库数量
	public int selectNum(Map<String, String> map) throws Exception{
		int i = (int) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL +".selectNum", map);
		return i;
	}
	
	/**
	 * 判断当前用户是否是管理员
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getParaValueListByName(HttpServletRequest request, String organization) throws Exception {
		List<String> list = new ArrayList<>();
		if (organization.equals("HUDH")) {
			list.add(StaticVar.GOODS_IN_ADMIN);
		} else if (organization.equals("HUDX")) {
			list.add(StaticVar.GOODS_IN_ADMIN_XZM);//西直门仓库审核
		}
		List<JSONObject> json = sysParaDao.getParaValueListByName(list, request, organization);
		return json;
	}
	
	/**
	  * @Title: getParaValueByName   
	  * @Description: TODO(判断当前用户是否是仓库管理员)   duoyh
	  * @param: @param request
	  * @param: @param organization
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2019年12月9日 下午5:05:12
	 */
	public JSONObject getParaValueByName(HttpServletRequest request, String organization) throws Exception {
		String paraValue = null;
		if (organization.equals("HUDH")) {
			paraValue = StaticVar.GOODS_IN_ADMIN;
		} else if (organization.equals("HUDX")) {
			paraValue = StaticVar.GOODS_IN_ADMIN_XZM;//西直门仓库审核
		}
		return sysParaDao.getParaValueByName(paraValue, request, organization);
	}
	
	/**
	 * 入库明细查询（2019-6-4）
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> goodsInSelectList(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN + ".goodsInSelectList", map);
		for (JSONObject job : list) {
			String intype = job.getString("intype");
			if (ConstUtil.CK_IN_0.equals(intype)) {
				intype = "采购入库";
			} else if (ConstUtil.CK_IN_2.equals(intype)) {
				intype = "换货入库";
			} else if (ConstUtil.CK_IN_4.equals(intype)) {
				intype = "其他入库";
			}else if(ConstUtil.CK_IN_8.equals(intype)){
				intype = "调拨入库";
			}else if(ConstUtil.CK_IN_9.equals(intype)){
				intype = "二次入库";
			}
			job.put("intype", intype);
		}
		return list;
	}
	
	/**
	 * 修改状态
	 * @param goodsinid
	 * @throws Exception 
	 */
	public void updateCheckStatus(String goodsinid) throws Exception {
		//修改入库状态
		dao.update(TableNameUtil.KQDS_CK_GOODS_IN + ".updateCheckStatus", goodsinid);
	}
	/**
	 * 修改入库明细状态
	 * @param incode
	 * @throws Exception 
	 */
	public void updateAuditStatus(String incode) throws Exception {
		//修改入库明细状态
		Map<String,String> map = new HashMap<String,String>();
		map.put("incode", incode);
		dao.update(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".updateAuditStatus", map);
	}
	
	/**
	 * 在商品库存表中添加入库数量、单价、金额 二级仓库（dyh）
	 * @param goodsinid
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void goodsAddInStock(String goodsinid, String menzhen) throws Exception {
		KqdsCkGoodsIn dp = this.findCkGoodsInById(goodsinid);
		List<KqdsCkGoodsInDetail> jList = logic.findCkGoodsDetailByInid(goodsinid);
		// TODO Auto-generated method stub
		for (KqdsCkGoodsInDetail detail : jList) {
			// 商品表 更新商品库存 ；等待入库数量 等待出库数量 因没有审核功能 暂不维护
			// 根据商品主键及所属仓库 查询库存表是否存在该商品对应该仓库的记录；
			// 1.不存在 说明是第一次入库，新增
			// 2.存在则直接更新 单价 数量 金额
			Map<String, String> map = new HashMap<String, String>();
			map.put("goodsdetailid", detail.getGoodsuuid());
			List<KqdsCkGoods> list = null;
			if(detail.getType().equals("2")){
				JSONObject json = logic.findGoodsDetailById(map);
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("goodscode", json.getString("goodscode"));
				map2.put("organization", detail.getOrganization());
				JSONObject object = logic.findGoodsBycodeAndType(map2);
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("goodsdetailid", object.getString("seqId"));
				list = (List<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map3);
			}else{
				list = (List<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
			}
			if(detail.getType().equals("2")){
				if (list != null && list.size() > 0) {// 更新
					KqdsCkGoods goods = list.get(0);
					// 平均价= （本次总价+库存总价） /（本次数量+库存数量）
					BigDecimal zong = new BigDecimal("0.00");
					if(detail.getRkmoney() != null){
						 if(goods.getKcmoneys()!=null && !goods.getKcmoneys().equals("")){
							 zong = detail.getRkmoney().add(goods.getKcmoneys());
						 }else{
							 zong = detail.getRkmoney().add(new BigDecimal("0.00"));
						 }
					}
					int nums = 0;
					if(goods.getNum() !=null && !goods.getNum().equals("")){
						nums = detail.getInnum() + goods.getNum();
					}else{
						nums = detail.getInnum() + 0;
					}
					// 保留3位小数
					BigDecimal goodsprice = new BigDecimal("0.00");
					if (nums == 0) {// 出库后，库存为0
						goodsprice = BigDecimal.ZERO;
					} else {
						if(zong != null){
							goodsprice = zong.divide(new BigDecimal(nums), 3, RoundingMode.HALF_EVEN).setScale(3, BigDecimal.ROUND_HALF_DOWN);
						}
					}
					goods.setGoodsprices(goodsprice);
					goods.setKcmoneys(zong);
					if(goods.getNum() != null && !goods.getNum().equals("")){
						goods.setNum(goods.getNum() + detail.getInnum());
					}else{
						goods.setNum(0 + detail.getInnum());
					}
					logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
				
			} else {// 新增
				KqdsCkGoods goods = new KqdsCkGoods();
				// 保存该入库商品所属仓库
				goods.setSeqId(YZUtility.getUUID());
				goods.setGoodsdetailid(detail.getGoodsuuid());
				goods.setSshouse(dp.getInhouse());
				goods.setGoodsprices(detail.getInprice());
				goods.setKcmoneys(detail.getRkmoney());
				goods.setNum(detail.getInnum());
				goods.setOrganization(menzhen);
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
			   }
			}else{
				if (list != null && list.size() > 0) {// 更新
					KqdsCkGoods goods = list.get(0);
					// 平均价= （本次总价+库存总价） /（本次数量+库存数量）
					BigDecimal zong = detail.getRkmoney().add(goods.getKcmoney());
					int nums = detail.getInnum() + goods.getNums();
					// 保留3位小数
					BigDecimal goodsprice = null;
					if (nums == 0) {// 出库后，库存为0
						goodsprice = BigDecimal.ZERO;
					} else {
						goodsprice = zong.divide(new BigDecimal(nums), 3, RoundingMode.HALF_EVEN).setScale(3, BigDecimal.ROUND_HALF_DOWN);
					}
					goods.setGoodsprice(goodsprice);
					goods.setKcmoney(zong);
					goods.setNums(goods.getNums() + detail.getInnum());
					logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
				
			} else {// 新增
				KqdsCkGoods goods = new KqdsCkGoods();
				// 保存该入库商品所属仓库
				goods.setSeqId(YZUtility.getUUID());
				goods.setGoodsdetailid(detail.getGoodsuuid());
				goods.setSshouse(dp.getInhouse());
				goods.setGoodsprice(detail.getInprice());
				goods.setKcmoney(detail.getRkmoney());
				goods.setNums(detail.getInnum());
				goods.setOrganization(menzhen);
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
			   }
			}
		}
	}
	
	/**
	 * 在商品库存表中添加入库数量、单价、金额
	 * @param goodsinid
	 * @throws Exception 
	 */
/*	@SuppressWarnings("unchecked")
	public void goodsAddInStock(String goodsinid, String menzhen) throws Exception {
		KqdsCkGoodsIn dp = this.findCkGoodsInById(goodsinid);
		List<KqdsCkGoodsInDetail> jList = logic.findCkGoodsDetailByInid(goodsinid);
		// TODO Auto-generated method stub
		for (KqdsCkGoodsInDetail detail : jList) {
			// 商品表 更新商品库存 ；等待入库数量 等待出库数量 因没有审核功能 暂不维护
			// 根据商品主键及所属仓库 查询库存表是否存在该商品对应该仓库的记录；
			// 1.不存在 说明是第一次入库，新增
			// 2.存在则直接更新 单价 数量 金额
			Map<String, String> map = new HashMap<String, String>();
			//map.put("sshouse", dp.getInhouse());
			map.put("goodsdetailid", detail.getGoodsuuid());
			List<KqdsCkGoods> list = (List<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
			
			if (list != null && list.size() > 0) {// 更新
				KqdsCkGoods goods = list.get(0);
					// 平均价= （本次总价+库存总价） /（本次数量+库存数量）
					BigDecimal zong = detail.getRkmoney().add(goods.getKcmoney());
					int nums = detail.getInnum() + goods.getNums();
					// 保留3位小数
					BigDecimal goodsprice = null;
					if (nums == 0) {// 出库后，库存为0
						goodsprice = BigDecimal.ZERO;
					} else {
						goodsprice = zong.divide(new BigDecimal(nums), 3, RoundingMode.HALF_EVEN).setScale(3, BigDecimal.ROUND_HALF_DOWN);
					}
					goods.setGoodsprice(goodsprice);
					goods.setKcmoney(zong);
					goods.setNums(goods.getNums() + detail.getInnum());
					logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
				
			} else {// 新增
				KqdsCkGoods goods = new KqdsCkGoods();
				// 保存该入库商品所属仓库
				goods.setSeqId(YZUtility.getUUID());
				goods.setGoodsdetailid(detail.getGoodsuuid());
				goods.setSshouse(dp.getInhouse());
				goods.setGoodsprice(detail.getInprice());
				goods.setKcmoney(detail.getRkmoney());
				goods.setNums(detail.getInnum());
				goods.setOrganization(menzhen);
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods);
			}
		}
	}*/
	
	/**
	 * 根据id查找入库表信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public KqdsCkGoodsIn findCkGoodsInById(String id) throws Exception {
		KqdsCkGoodsIn kaCkGoodsIn = (KqdsCkGoodsIn) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN + ".findCkGoodsInById", id);
		return kaCkGoodsIn;
	}
	
	/**
	 * 根据入库表主键删除信息
	 * @param seq_id
	 * @throws Exception
	 */
	public void deleteGoodsInById(String seq_id) throws Exception {
		dao.update(TableNameUtil.KQDS_CK_GOODS_IN + ".deleteGoodsInById", seq_id);
	}
}