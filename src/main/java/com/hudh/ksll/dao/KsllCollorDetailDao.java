package com.hudh.ksll.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ksll.entity.KsllCollorDetail;
import com.hudh.ksll.entity.KsllCollorDetailPh;
import com.kqds.dao.DaoSupport;
import net.sf.json.JSONObject;
@Service
public class KsllCollorDetailDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增科室领料明细
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public void batchSaveCollorDetail(List<KsllCollorDetail> list) throws Exception{
		dao.batchUpdate("HUDH_KSLL_COLLOR_DETAIL.batchSaveCollorDetail", list);
	}
	
	/**
	 * 根据parentid查询科室领料明细
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDetailByParendId(String parentId) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_KSLL_COLLOR_DETAIL.findDetailByParendId", parentId);
	}
	/**
	 * 根据parentid查询科室领料明细
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDetailByParendIds(String parentId) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_KSLL_COLLOR_DETAIL.findDetailByParendIds", parentId);
	}
	
	/**
	 * 删除 更新status字段
	 * @param id
	 * @throws Exception
	 */
	public void updateCollorDetail(Map<String,String> dataMap) throws Exception{
		dao.update("HUDH_KSLL_COLLOR_DETAIL.updateCollorDetailByParendId", dataMap);
	}
	
	/**
	 * 更新明细表数量字段
	 * @param id
	 * @throws Exception
	 */
	public void updateNumsById(Map<String,String> dataMap) throws Exception{
		dao.update("HUDH_KSLL_COLLOR_DETAIL.updateNumsById", dataMap);
	}
	
	/**
	 * 批量更新领料明细
	 * @param id
	 * @throws Exception
	 */
	public void batchUpdateKsllDetailByPrimaryId(List<KsllCollorDetail> list) throws Exception{
		dao.batchUpdate("HUDH_KSLL_COLLOR_DETAIL.batchUpdateKsllDetailByPrimaryId", list);
	}
	/**
	 * 根据编号查询明细
	 * <p>Title: findDetailPhByGoodscode</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月29日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDetailPhByGoodscode(Map<String,String> map) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_KSLL_COLLOR_DETAIL.findDetailPhByGoodscode", map);
		return list;
	}
	/**
	 * 查询批号单价
	 * <p>Title: findDetailPhPriceByGoodscode</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月29日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KsllCollorDetailPh> findDetailPhPriceByGoodscode(Map<String,String> map) throws Exception{
		List<KsllCollorDetailPh> list = (List<KsllCollorDetailPh>) dao.findForList("HUDH_KSLL_COLLOR_DETAIL_PH.findDetailPhPriceByGoodscode", map);
		return list;
	}
	/**
	 * 减少批号数量
	 * <p>Title: updateDetailPhnumBySeqid</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月29日 
	 * @param list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDetailPhnumBySeqid(List<KsllCollorDetailPh> list) throws Exception{
		dao.batchUpdate("HUDH_KSLL_COLLOR_DETAIL_PH.updateDetailPhnumBySeqid", list);
	}
	/**
	 * 查询单价
	 * <p>Title: findCollorDetailPriceByGoodscode</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月29日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BigDecimal findCollorDetailPriceByGoodscode(Map<String,String> map) throws Exception{
		String price= (String) dao.findForObject("HUDH_KSLL_COLLOR_DETAIL.findCollorDetailPriceByGoodscode", map);
		if(price==null){
			price="0";
		}
		return new BigDecimal(price);
	}
	/**
	 * 根据id查询明细
	 * <p>Title: findDetailPhBySeqid</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月29日 
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KsllCollorDetailPh> findDetailPhBySeqid(String seqId) throws Exception{
		Map<String,String> map= new HashMap<String,String>();
		map.put("seqid", seqId);
		List<KsllCollorDetailPh> list = (List<KsllCollorDetailPh>) dao.findForList("HUDH_KSLL_COLLOR_DETAIL_PH.findDetailPhBySeqid", map);
		return list;
	}
	/**
	 * 增加批号数量
	 * <p>Title: updateDetailAddPhnumBySeqid</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月29日 
	 * @param list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDetailAddPhnumBySeqid(List<KsllCollorDetailPh> list) throws Exception{
		dao.batchUpdate("HUDH_KSLL_COLLOR_DETAIL_PH.updateDetailAddPhnumBySeqid", list);
	}
}
