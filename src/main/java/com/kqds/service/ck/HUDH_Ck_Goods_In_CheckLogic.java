package com.kqds.service.ck;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsInCheck;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class HUDH_Ck_Goods_In_CheckLogic extends BaseLogic {
	
	@Autowired
	private DaoSupport dao;
	
	@Autowired
	private KQDS_Ck_Goods_InLogic logic;
	
	/**
	 * 保存审核数据
	 * @param dp
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void saveGoodsInCheck(KqdsCkGoodsInCheck dp,String incode) throws Exception {
		//当外包装是否破损异常 合格证 验收报告 全部通过时审批结果为1 ，其他为0
		if(dp.getPacking().equals("1") && dp.getReport().equals("1") && dp.getCertificate().equals("1")) {
			dp.setResult("1");
			//审批通过更新入库表对应数据审批状态
			logic.updateCheckStatus(dp.getGoodsinid());
			logic.updateAuditStatus(incode);
			logic.goodsAddInStock(dp.getGoodsinid(), dp.getOrganization());
		}else {
			dp.setResult("0");
		}
		dao.save(TableNameUtil.KQDS_CK_GOODS_IN_CHECK + ".saveGoodsInCheck", dp);
	}
	
	/**
	 * 根据入库表id查询对应的审批数据
	 * @param goodsInId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findGoodsInExamineByInId(String goodsInId) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_IN_CHECK + ".findGoodsInExamineByInId", goodsInId);
		return list;
	}
}
