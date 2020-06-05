package com.hudh.ykzz.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.YZPerson;

import net.sf.json.JSONObject;
public interface IYkzzDrugsInService {
	
	/**
	 * 添加入库
	 * @param ykzzDrugs 入库信息
	 * @param drugsIndetails 入库明细
	 * @return
	 * @throws Exception
	 */
	void insertDrugsIn(YkzzDrugsIn ykzzDrugsIn,String drugsIndetails,HttpServletRequest request) throws Exception;
	
	/**
	 * 药品加入库存
	 * @param drugsInId
	 * @throws Exception
	 */
	void drugsAddInStock(String drugsInId) throws Exception;
	
	/**
	 * 查询全部入库信息或根据条件查找
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllDrugsIn(Map<String,String> dataMap)  throws Exception;
	
	/**
	 * 根据parentid查询入库明细
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findDetailByParendId(String parentid)  throws Exception;
	
	/**
	 * 删除入库信息
	 * @param id
	 * @throws Exception
	 */
	String deleteDrugsIn(String id)  throws Exception;
	
	/**
	 * 删除入库表，用于未审核入库数据删除，不涉及库存数据问题
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	void deleteDrugsInById(String id)  throws Exception;
	
	/**
	 * 删除入库明细表
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	void deleteDrugsInDetailByParendId(String id)  throws Exception;
	
	/**
	 * 获取所有费用单
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllCostOrder(Map<String,Object> dataMap)  throws Exception;
	
	/**
	 * 根据orderno获取药品明细
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findCostOrderDetailByCostno(String costno)  throws Exception;
	
	/**
	 * 发药
	 * @param costno
	 * @throws Exception
	 */
	void grantDrugs(String organization,String costno, String[] idr, String[] seqId, String[] costseqIdArr,HttpServletRequest request) throws Exception;
	/**
	 * 退药
	 * @param costno
	 * @param idr
	 * @param seqId
	 * @param costseqIdArr
	 * @throws Exception
	 */
	void returnDrugs(String idr, String seqId, String costseqIdArr,String outnum,YZPerson person) throws Exception;
	
	/**
	 * 更新审批状态
	 * @throws Exception
	 */
	void updateCheckStatus(String id) throws Exception;
	/**
	 * 获取药库管理员登录名
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findDrugsInAdmin(HttpServletRequest request) throws Exception;
	
	/**
	 * 根据药品编号查询批号
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	List<YkzzDrugsInDetail> findBatchnumByOrderno(String order_no) throws Exception;
	
	/**
	 * 根据id查询入库明细
	 * @param inDetail
	 * @return
	 * @throws Exception
	 */
	YkzzDrugsInDetail findYkzzDrugsInDatailByInDetail(String inDetail) throws Exception;

	List<JSONObject> findCostOrderDetailById(String id) throws Exception;

	List<YkzzDrugsInDetail> findBatchnumByOrderno1(String order_no) throws Exception;

	String findByParendId(String parentid) throws Exception;

	int updateYkzzDrugsInDatailByParentId(YkzzDrugsInDetail ykzzDrugsInDetail) throws Exception;

	List<JSONObject> findCostOrderDetailReturnBySeqid(String seqid) throws Exception;
	/**
	 * 根据明细表的seqid查找有没有退款情况
	 * @param seqid
	 * @return
	 * @throws Exception
	 */
	KqdsCostorderDetail findCostOrderDetailByParentid(String seqid) throws Exception;
	/**
	 * 根据明细表的seqid查询明细表详情
	 * @param seqid
	 * @return
	 * @throws Exception
	 */
	KqdsCostorderDetail findCostOrderDetailBySeqid(String seqId) throws Exception;
	/**
	 * 根据qfbh获取药品明细
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findCostOrderDetailByQfbh(String qfbh)  throws Exception;
	
	/**
	 * 根据明细表的qfbh查找Subtotal<0情况
	 * @param qfbh
	 * @return
	 * @throws Exception
	 */
	KqdsCostorderDetail findCostOrderDetailSubtotalByQfbh(String qfbh) throws Exception;
	/**
	 * 根据药品编号查询批号详情
	 * 2019-08-12 lwg
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findYkzzDrugsInDetailByOrderno(String orderno)throws Exception;
}
