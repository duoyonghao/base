package com.hudh.ykzz.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.kqds.dao.DaoSupport;
import net.sf.json.JSONObject;
@Service
public class YkzzDrugsInDetailDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增入库明细
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public void batchSaveInDetail(List<YkzzDrugsInDetail> list) throws Exception{
		dao.batchUpdate("HUDH_YKZZ_DRUGS_IN_DETAIL.batchSaveInDetail", list);
	}
	
	/**
	 * 根据parentid查询入库明细
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDetailByParendId(String parentId) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findDetailByParendId", parentId);
	}
	
	/**
	 * 删除 更新status字段
	 * @param id
	 * @throws Exception
	 */
	public void deleteDrugsIn(String id) throws Exception{
		dao.delete("HUDH_YKZZ_DRUGS_IN_DETAIL.deleteDrugsInDetailByParendId", id);
	}
	
	/**
	 * 根据药品编号查询入库明细表信息
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	public List<YkzzDrugsInDetail> findDrugsInDetailByOrderno(String order_no) throws Exception {
		List<YkzzDrugsInDetail> list = (List<YkzzDrugsInDetail>) dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findDrugsInDetailByOrderno", order_no);
		return list;
	}
	
	/**
	 * 根据药品编号查询批号
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	public List<YkzzDrugsInDetail> findBatchnumByOrderno(String order_no) throws Exception {
		List<YkzzDrugsInDetail> list = (List<YkzzDrugsInDetail>) dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findBatchnumByOrderno", order_no);
		return list;
	}
	/**
	 * 根据药品编号查询批号
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	public List<YkzzDrugsInDetail> findBatchnumByOrderno1(String order_no) throws Exception {
		@SuppressWarnings("unchecked")
		List<YkzzDrugsInDetail> list = (List<YkzzDrugsInDetail>) dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findBatchnumByOrderno1", order_no);
		return list;
	}
	/**
	 * 批量更新药品入库明细表
	 * @param list
	 * @throws Exception
	 */
	public void updateDrugsInDetail(YkzzDrugsInDetail ykInDetail) throws Exception {
		 dao.update("HUDH_YKZZ_DRUGS_IN_DETAIL.updateDrugsInDetail", ykInDetail);
	}
	
	/**
	 * 根据id查询药品入库明细
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public YkzzDrugsInDetail findYkzzDrugsInDatailById(String id) throws Exception {
		YkzzDrugsInDetail drugsInDetail = (YkzzDrugsInDetail) dao.findForObject("HUDH_YKZZ_DRUGS_IN_DETAIL.findYkzzDrugsInDatailById", id);
		return drugsInDetail;
	}
	
	/**
	 * 根据id查询药品入库明细
	 * @param inDetail
	 * @return
	 * @throws Exception
	 */
	public YkzzDrugsInDetail findYkzzDrugsInDatailByInDetail(String inDetail) throws Exception {
		YkzzDrugsInDetail drugsInDetail = (YkzzDrugsInDetail) dao.findForObject("HUDH_YKZZ_DRUGS_IN_DETAIL.findYkzzDrugsInDatailByInDetail", inDetail);
		return drugsInDetail;
	}
	
	public int updateYkzzDrugsInDatailByParentId(YkzzDrugsInDetail ykzzDrugsInDetail) throws Exception {
		int i = (int) dao.findForObject("HUDH_YKZZ_DRUGS_IN_DETAIL.updateBatchnumInDetail", ykzzDrugsInDetail);
		return i;
	}
	/**
	 * 2019-08-14 lwg 根据药品编号orderno查询药品批号信息
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findYkzzDrugsInDetailByOrderno(String orderno) throws Exception {
		List<JSONObject> list =(List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findYkzzDrugsInDetailByOrderno", orderno);
		return list;
	}
}
