package com.kqds.service.ck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_Ck_Goods_In_DetailLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	// 入库明细
	@SuppressWarnings("unchecked")
	public List<JSONObject> inSearchList(String table, Map<String, String> map) throws Exception {
		map.put("selectKcmoney", SQLUtil.convertDecimal("gind.rkmoney", 18, 3));
		map.put("selectGoodsprice", SQLUtil.convertDecimal("gind.inprice", 18, 3));
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".inSearchList", map);
		for (JSONObject job : list) {
			String intype = job.getString("intype");
			
			if (ConstUtil.CK_IN_0.equals(intype)) {
				intype = "采购入库";
			} else if (ConstUtil.CK_IN_2.equals(intype)) {
				intype = "换货入库";
			} else if (ConstUtil.CK_IN_4.equals(intype)) {
				intype = "其他入库";
			}

			job.put("intype", intype);
		}

		return list;
	}
	/**
	 * 根据商品id查询入库明细
	 * @param goodsuuid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectAll( String goodsuuid) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectAll", goodsuuid);
		return list;
	}
	
	/**
	 * 根据入库明细表主键查询明细表信息
	 * @param goodsinid
	 * @return
	 * @throws Exception
	 */
	public KqdsCkGoodsInDetail selectCkGoodsDetailByInid(String goodsinid) throws Exception {
		// TODO Auto-generated method stub
		KqdsCkGoodsInDetail list = (KqdsCkGoodsInDetail) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectCkGoodsDetailByInid", goodsinid);
		return list;
	}
	
	/**
	 * 根据入库表id查询入库明细表信息
	 * @param goodsinid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsCkGoodsInDetail> findCkGoodsDetailByInid(String goodsinid) throws Exception {
		List<KqdsCkGoodsInDetail> list = (List<KqdsCkGoodsInDetail>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".findCkGoodsDetailByInid", goodsinid);
		return list;
	}
	
	/**
	 * 根据单据编号查询明细表信息
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> inDetailSelectByIncode(String table, Map<String, String> map) throws Exception {
		map.put("selectKcmoney", SQLUtil.convertDecimal("gind.rkmoney", 18, 3));
		map.put("selectGoodsprice", SQLUtil.convertDecimal("gind.inprice", 18, 3));
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".inDetailSelectByIncode", map);
		for (JSONObject job : list) {
			String intype = job.getString("intype");

			if (ConstUtil.CK_IN_0.equals(intype)) {
				intype = "采购入库";
			} else if (ConstUtil.CK_IN_2.equals(intype)) {
				intype = "换货入库";
			} else if (ConstUtil.CK_IN_4.equals(intype)) {
				intype = "其他入库";
			}

			job.put("intype", intype);
		}

		return list;
	}
	
	/**
	 * 根据商品入库表主键更新明细表信息
	 * @param goodsInSeqId
	 * @throws Exception
	 */
	public void updateGoodsInDetailByGoodsInSeqId(KqdsCkGoodsInDetail kqdsCkGoodsInDetail) throws Exception {
		dao.update(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".updateGoodsInDetailByGoodsInSeqId", kqdsCkGoodsInDetail);
	}
	
	/**
	 * 根据商品入库明细表主键删除信息
	 * @param detailId
	 * @throws Exception
	 */
	public void deleteGoodsInDetailById(String detailId) throws Exception {
		dao.delete(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".deleteGoodsInDetailById", detailId);
	}
	
	/**
	 * 根据商品编号在入库明细表查询所有批号
	 * @param goodcode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectAllGoodPhByGoodCode(String goodsuuid,String type) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodsuuid", goodsuuid);
		map.put("type", type);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".selectAllGoodPhByGoodCode", map);
		//根据批号和商品id查询单价
		List<JSONObject> list1=new ArrayList<JSONObject>();
		//KqdsCkGoods kqdsCkGoods = new KqdsCkGoods();
		/*if(type.equals("2")){
			kqdsCkGoods=(KqdsCkGoods) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".findCkGoodsByDetailid2", goodsuuid);
		}else{*/
		KqdsCkGoods kqdsCkGoods=(KqdsCkGoods) dao.findForObject(TableNameUtil.KQDS_CK_GOODS + ".findCkGoodsByDetailid", goodsuuid);
		/*}*/
		if(list.size()>0){
			int phnum=0;
			for (JSONObject jsonObject : list) {
				phnum+=jsonObject.getInt("phnum");
			}
			if(type.equals("2")){
				//批号数量小于库存数量时添加请选择的选项
				if(phnum<kqdsCkGoods.getNum()){
					JSONObject json= new JSONObject();
					json.put("ph", "请选择");
					json.put("phnum", kqdsCkGoods.getNums()-phnum);
					json.put("goodsuuid", goodsuuid);
					list1.add(json);
					list1.addAll(list);
				}else{
					list1.addAll(list);
				}
			}else{
				//批号数量小于库存数量时添加请选择的选项
				if(phnum<kqdsCkGoods.getNums()){
					JSONObject json= new JSONObject();
					json.put("ph", "请选择");
					json.put("phnum", kqdsCkGoods.getNums()-phnum);
					json.put("goodsuuid", goodsuuid);
					list1.add(json);
					list1.addAll(list);
				}else{
					list1.addAll(list);
				}
			}
		}else{
			JSONObject json= new JSONObject();
			json.put("ph", "请选择");
			json.put("phnum", kqdsCkGoods.getNums());
			json.put("goodsuuid", goodsuuid);
			list1.add(json);
		}
		return list1;
	}
	/**
	 * 
	 * <p>Title: findCkInGoodsDetailByGoodsuuid</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月17日 
	 * @param goodsuuid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCkInGoodsDetailByGoodsuuid(String goodsuuid) throws Exception{
		//查询出商品的所有批号信息
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".findCkInGoodsDetailByGoodsuuid", goodsuuid);
		//查询商品的明细信息
		KqdsCkGoodsDetail kqdsCkGoodsDetail = (KqdsCkGoodsDetail) dao.findForObject(TableNameUtil.KQDS_CK_GOODS_DETAIL + ".findCkGoodsDetailBySeqid", goodsuuid);
		
		if(list.size()>0){
			for (JSONObject jsonObject : list) {
				jsonObject.put("goodscode", kqdsCkGoodsDetail.getGoodscode());
				jsonObject.put("goodsname", kqdsCkGoodsDetail.getGoodsname());
				jsonObject.put("goodsnorms", kqdsCkGoodsDetail.getGoodsnorms());
				jsonObject.put("goodsunit", kqdsCkGoodsDetail.getGoodsunit());
			}
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public JSONObject findCkInGoodsDetail(String goodsuuid, BootStrapPage bp) throws Exception{
		//查询出商品的所有批号信息
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".findCkInGoodsDetail", goodsuuid);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
	public void updateCkGoodsInDetailByKsllReplaceMent(List<KqdsCkGoodsInDetail> kqdsCkGoodsInDetailList) throws Exception{
		dao.batchUpdate(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL + ".updateCkGoodsInDetailByKsllReplaceMent", kqdsCkGoodsInDetailList);
	}
	
	public void updateGoodsPh(Map<String,String> map)throws Exception{
		dao.update(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL+".updateGoodsPh", map);
	}
	/**   
	  * @Title: findGoodsDetailById   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: JSONObject
	 * @throws Exception 
	  * @dateTime:2020年4月20日 下午4:02:08
	  */  
	public JSONObject findGoodsDetailById(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_CK_GOODS+".findGoodsDetailById", map);
	}
	/**   
	  * @Title: findGoodsBycodeAndType   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map2      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2020年4月20日 下午4:08:09
	  */  
	public JSONObject findGoodsBycodeAndType(Map<String, String> map2) throws Exception {
		// TODO Auto-generated method stub
		return (JSONObject) dao.findForObject(TableNameUtil.KQDS_CK_GOODS+".findGoodsBycodeAndType", map2);
	}
}