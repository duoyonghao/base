package com.hudh.ykzz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCostorderDetail;

import net.sf.json.JSONObject;
@Service
public class YkzzDrugsInDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增入库信息
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public int insertDrugsIn(YkzzDrugsIn ykzzDrugsIn) throws Exception{
		return (int) dao.save("HUDH_YKZZ_DRUGS_IN.insertDrugsIn", ykzzDrugsIn);
	}
	
	/**
	 * 获取全部入库数据或根据条件检索
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllDrugsIn(Map<String,String> dataMap) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN.findAllDrugsIn", dataMap);
	}
	/**
	 * 获得入库表的创建时间
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String findByParendId(String parentid) throws Exception{
		return  (String) dao.findForObject("HUDH_YKZZ_DRUGS_IN.findByParendId", parentid);
	}
	/**
	 * 删除 更新status字段
	 * @param id
	 * @throws Exception
	 */
	public void deleteDrugsIn(String id) throws Exception{
		dao.delete("HUDH_YKZZ_DRUGS_IN.deleteDrugsIn", id);
	}
	
	/**
	 * 获取所有费用单
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllCostOrder(Map<String,Object> dataMap) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN.findAllCostOrder", dataMap);
	}
	/**
	 * 获取已结账未发药的药单明细
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsCostorderDetail> findCostOrderDetailNoSend() throws Exception{
		return (List<KqdsCostorderDetail>) dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailNoSend", null);
	}
	/**
	 * 发药根据orderno获取药品明细
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCostOrderDetailByCostno(String costno) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailByCostno", costno);
	}
	/**
	 * 发药根据seqId获取药品明细
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public KqdsCostorderDetail findCostOrderDetailBySeqid(String seqId) throws Exception{
		return  (KqdsCostorderDetail) dao.findForObject("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailBySeqid", seqId);
	}
	/**
	 * 根据orderno获取药品的详情
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCostOrderDetailReturnBySeqid(String seqid) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailReturnBySeqid", seqid);
	}
	/**
	 * 退药根据seq_id获取药品明细
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCostOrderDetailById(String seqId) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailBySeqId", seqId);
	}
	/**
	 * 更新费用单状态
	 * @param id
	 * @throws Exception
	 */
	public Integer updateCostOrderById(String costno) throws Exception{
		return (Integer) dao.update("HUDH_YKZZ_DRUGS_IN.updateCostOrderById", costno);
	}
	/**
	 * 退药更改费用单状态
	 * @param kcd
	 * @throws Exception
	 */
	public void insertCostOrderDetailReturnBySeqId(KqdsCostorderDetail kcd) throws Exception{
		dao.update("HUDH_YKZZ_DRUGS_IN.insertCostOrderDetailReturnBySeqId", kcd);
	}
	/**
	 * 更新审批状态
	 * @param id
	 * @throws Exception
	 */
	public void updateCheckStatus(String id) throws Exception{
		dao.update("HUDH_YKZZ_DRUGS_IN.updateCheckStatus", id);
	}
	/**
	 * 根据seqid查找CostorderDetail表的详情
	 * @param seqid
	 * @return
	 * @throws Exception
	 */
	public KqdsCostorderDetail findCostOrderDetailByParentid(String seqid) throws Exception {
		KqdsCostorderDetail i = (KqdsCostorderDetail) dao.findForObject("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailByParentid", seqid);
		return i;
	}
	/**
	 * 根据qfbh查询CostorderDetail表状态为1的详情
	 * @param qfbh
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findCostOrderDetailByQfbh(String qfbh) throws Exception {
		@SuppressWarnings("unchecked")
		List<JSONObject> i = (List<JSONObject>) dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailByQfbh", qfbh);
		return i;
	}
	/**
	 * 根据明细表的qfbh查找Subtotal<0情况
	 * @param qfbh
	 * @return
	 * @throws Exception
	 */
	public KqdsCostorderDetail findCostOrderDetailSubtotalByQfbh(String qfbh) throws Exception {
		KqdsCostorderDetail i = (KqdsCostorderDetail) dao.findForObject("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailSubtotalByQfbh", qfbh);
		return i;
	}
}
