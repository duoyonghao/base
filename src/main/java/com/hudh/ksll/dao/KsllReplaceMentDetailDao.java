package com.hudh.ksll.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.ksll.entity.KsllReplaceMentDetail;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkReplacementDetail;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;
@Service
public class KsllReplaceMentDetailDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增科室领料明细
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public void batchSaveReplacementDetail(List<KsllReplaceMentDetail> list) throws Exception{
		dao.batchUpdate("HUDH_KSLL_REPLACEMENT_DETAIL.batchSaveReplacementDetail", list);
	}
	
	/**
	 * 根据parentid查询科室领料明细
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDetailByParendId(Map<String, String> map) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_KSLL_REPLACEMENT_DETAIL.findDetailByParendId", map);
	}
	/**
	 * 根据编号、部门和批号查询领料情况
	 * <p>Title: findCollorDetailPhByParendId</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月23日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCollorDetailPhByParendId(Map<String,String> map) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_KSLL_COLLOR_DETAIL.findCollorDetailPhByParendId", map);
	}
	
	public int selectReturnNumByGoodscode(String goodscode)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String date = YZUtility.getDateStr(new Date());
		map.put("starttime", date);
		map.put("endtime",date);
		map.put("goodscode", goodscode);
		return (int) dao.findForObject("HUDH_KSLL_REPLACEMENT_DETAIL.selectReturnNumByGoodscode", map);
	}
	/**
	 * 批量新增仓库退货明细
	 * <p>Title: batchSaveCkReplacementDetail</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月20日 
	 * @param list
	 * @throws Exception
	 */
	public void batchSaveCkReplacementDetail(List<KqdsCkReplacementDetail> list) throws Exception{
		dao.batchUpdate("KQDS_CK_REPLACEMENT_DETAIL.insertSelective", list);
	}
	/**
	 * 查询退货明细
	 * <p>Title: findCkReplaceMentDetailByParendId</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月20日 
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCkReplaceMentDetailByParendId(String parentId) throws Exception{
		return (List<JSONObject>) dao.findForList("KQDS_CK_REPLACEMENT_DETAIL.selectByParentid", parentId);
	}
}
