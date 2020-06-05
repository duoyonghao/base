package com.kqds.service.base.hzLabel;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsHzLabelAssociated;
import com.kqds.service.sys.base.BaseLogic;

import net.sf.json.JSONObject;
@Service
public class KQDS_HzLabelAssociatedLogic extends BaseLogic{
	@Autowired
	private DaoSupport dao;
	/**
	 * 新增标签关联表
	 * @param kqdsHzLabelAssociated
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertKqdsHzLabelAssociated(KqdsHzLabelAssociated  kqdsHzLabelAssociated) throws Exception{
		int i = (int) dao.save("KQDS_HZ_labelAssociated.insertKqdsHzLabelAssociated", kqdsHzLabelAssociated);
		return i;
	}
	/**
	 * 根据患者信息和标签查询价目表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> selectPriceListByLabeId(Map<String,String> map) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList("KQDS_HZ_labelAssociated.selectPriceListByLabeId", map);
		return list;
	}
	/**
	 * 根据患者id 查询是否存在关联表数据 seq_id
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectKqdsHzLabelAssociatedByUserId(Map<String,String> map) throws Exception{
		String seqid= (String) dao.findForObject("KQDS_HZ_labelAssociated.selectKqdsHzLabelAssociatedByUserId", map);
		return seqid;
	}
	/**
	 * 修改标签
	 * @param kqdsHzLabelAssociated
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateKqdsHzLabelAssociated(KqdsHzLabelAssociated kqdsHzLabelAssociated) throws Exception{
		int a= (int) dao.update("KQDS_HZ_labelAssociated.updateKqdsHzLabelAssociated", kqdsHzLabelAssociated);
		return a;
	}
	
	public String selectKqdsHzLabelBySeqId(String hzLabelAssciatedSeqId) throws Exception{
		String seqid= (String) dao.findForObject("KQDS_HZ_labelAssociated.selectKqdsHzLabelBySeqId", hzLabelAssciatedSeqId);
		return seqid;
	}
	@Transactional(rollbackFor = Exception.class)
	public int updateKqdsHzLabelAssociatedByStatus(KqdsHzLabelAssociated kqdsHzLabelAssociated) throws Exception{
		int a= (int) dao.update("KQDS_HZ_labelAssociated.updateKqdsHzLabelAssociatedByStatus", kqdsHzLabelAssociated);
		return a;
	}
}
