package com.hudh.lclj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.entity.LcljApprovedMemo;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class LcljVerificationSheetDao {
	
	@Autowired
	private DaoSupport dao;
	
	public void insert(LcljApprovedMemo bean) throws Exception {
		// TODO Auto-generated method stub
		dao.save(TableNameUtil.HUDH_LCLJ_CHECKRECORD+".insert", bean);
	}
	
	public void delCheckRecord(String id) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(TableNameUtil.HUDH_LCLJ_CHECKRECORD+".delCheckRecord", id);
	}
	
	public Integer getCheckRecordNum(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (Integer) dao.findForObject(TableNameUtil.HUDH_LCLJ_CHECKRECORD+".findLcljCheckrecordCount", map);
	}
	
	/**
	 * 查询未审核的记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getAwaitVerifieNum(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (Integer) dao.findForObject(TableNameUtil.HUDH_LCLJ_CHECKRECORD+".getAwaitVerifieNum", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLcljCheckrecordById(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".findLcljCheckrecordById", map);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLcljCheckrecord(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".findLcljCheckrecord", map);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> getAwaitCheckRecord(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".getAwaitCheckRecord", map);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<LcljApprovedMemo> findCheckRecordByOrderAndStatus(LcljApprovedMemo dp) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("LcljId", dp.getLcljId());
		List<LcljApprovedMemo> list = (List<LcljApprovedMemo>) dao.findForList(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".findCheckRecordByOrderAndStatus", dataMap);
		return list;
	}
	
	public void updateCheckRecordBySeqId(LcljApprovedMemo dp) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("seqId", dp.getSeqId());
		dao.update(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".updateCheckRecordBySeqId", dataMap);
	}
	
	public void Update(LcljApprovedMemo dp) throws Exception {
		dao.update(TableNameUtil.HUDH_LCLJ_CHECKRECORD+".Update", dp);
	}
}
