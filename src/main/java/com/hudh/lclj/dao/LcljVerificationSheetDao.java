package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljApprovedMemo;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljVerificationSheetDao
{
  @Autowired
  private DaoSupport dao;
  
  public void insert(LcljApprovedMemo bean)
    throws Exception
  {
    this.dao.save(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".insert", bean);
  }
  
  public void delCheckRecord(String id)
    throws Exception
  {
    this.dao.delete(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".delCheckRecord", id);
  }
  
  public Integer getCheckRecordNum(Map<String, String> map)
    throws Exception
  {
    return (Integer)this.dao.findForObject(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".findLcljCheckrecordCount", map);
  }
  
  public Integer getAwaitVerifieNum(Map<String, String> map)
    throws Exception
  {
    return (Integer)this.dao.findForObject(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".getAwaitVerifieNum", map);
  }
  
  public List<JSONObject> findLcljCheckrecordById(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".findLcljCheckrecordById", map);
    return list;
  }
  
  public List<JSONObject> findLcljCheckrecord(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".findLcljCheckrecord", map);
    return list;
  }
  
  public List<JSONObject> getAwaitCheckRecord(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".getAwaitCheckRecord", map);
    return list;
  }
  
  public List<LcljApprovedMemo> findCheckRecordByOrderAndStatus(LcljApprovedMemo dp)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("LcljId", dp.getLcljId());
    List<LcljApprovedMemo> list = (List)this.dao.findForList(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".findCheckRecordByOrderAndStatus", dataMap);
    return list;
  }
  
  public void updateCheckRecordBySeqId(LcljApprovedMemo dp)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("seqId", dp.getSeqId());
    this.dao.update(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".updateCheckRecordBySeqId", dataMap);
  }
  
  public void Update(LcljApprovedMemo dp)
    throws Exception
  {
    this.dao.update(TableNameUtil.HUDH_LCLJ_CHECKRECORD + ".Update", dp);
  }
}
