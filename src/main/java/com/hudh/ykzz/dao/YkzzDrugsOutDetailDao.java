package com.hudh.ykzz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
import com.kqds.dao.DaoSupport;
import net.sf.json.JSONObject;
@Service
public class YkzzDrugsOutDetailDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增出库明细
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public void batchSaveOutDetail(List<YkzzDrugsOutDetail> list) throws Exception{
		dao.batchUpdate("HUDH_YKZZ_DRUGS_OUT_DETAIL.batchSaveOutDetail", list);
	}
	
	/**
	 * 根据parentid查询出库明细
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDetailByParendId(String parentId) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_OUT_DETAIL.findDetailByParendId", parentId);
	}
	
	/**
	 * 删除 更新status字段
	 * @param id
	 * @throws Exception
	 */
	public void deleteDrugsOut(String id) throws Exception{
		dao.delete("HUDH_YKZZ_DRUGS_OUT_DETAIL.deleteDrugsOutDetailByParendId", id);
	}
	
	/**
	 * 根据药品编号查询出库明细表信息
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	public List<YkzzDrugsOutDetail> findDrugsOutDetailByOrderno(String order_no) throws Exception {
		List<YkzzDrugsOutDetail> list = (List<YkzzDrugsOutDetail>) dao.findForList("HUDH_YKZZ_DRUGS_OUT_DETAIL.findDrugsOutDetailByOrderno", order_no);
		return list;
	}
	/**
	 * 2019-08-23 lwg 
	 * 根据批号和药品编号查询出库数量
	 */
	public String findDrugsOutDetailByOrdernoAndBatchnum(Map<String,String> map) throws Exception {
		
		String list = (String) dao.findForObject("HUDH_YKZZ_DRUGS_OUT_DETAIL.findDrugsOutDetailByOrdernoAndBatchnum", map);
		return list;
	}
	/**
	 * 2019-08-23 lwg 
	 * 查询药品当天的所有出库数量
	 */
	public String findOutNumsByAll(Map<String,String> map) throws Exception {
		
		String list = (String) dao.findForObject("HUDH_YKZZ_DRUGS_OUT_DETAIL.findOutNumsByAll", map);
		return list;
	}
	/**
	 * 2019-08-23 lwg 
	 * 根据批号和药品编号查询领药数量
	 */
	public String findBatchnumSaveOutNumsByOrdernoAndBatchnum(Map<String,String> map) throws Exception {
		
		String list = (String) dao.findForObject("HUDH_YKZZ_DRUGS_BATCHNUM_SAVE.findBatchnumSaveOutNumsByOrdernoAndBatchnum", map);
		return list;
	}
	/**
	 * 2019-08-23 lwg 
	 * 查询药品当天的所有领药数量
	 */
	public String findOutNumsByBatchnumSave(Map<String,String> map) throws Exception {
		
		String list = (String) dao.findForObject("HUDH_YKZZ_DRUGS_BATCHNUM_SAVE.findOutNumsByBatchnumSave", map);
		return list;
	}
}
