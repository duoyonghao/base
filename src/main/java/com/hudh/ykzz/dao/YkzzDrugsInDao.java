package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCostorderDetail;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzDrugsInDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertDrugsIn(YkzzDrugsIn ykzzDrugsIn)
    throws Exception
  {
    return ((Integer)this.dao.save("HUDH_YKZZ_DRUGS_IN.insertDrugsIn", ykzzDrugsIn)).intValue();
  }
  
  public List<JSONObject> findAllDrugsIn(Map<String, String> dataMap)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN.findAllDrugsIn", dataMap);
  }
  
  public String findByParendId(String parentid)
    throws Exception
  {
    return (String)this.dao.findForObject("HUDH_YKZZ_DRUGS_IN.findByParendId", parentid);
  }
  
  public void deleteDrugsIn(String id)
    throws Exception
  {
    this.dao.delete("HUDH_YKZZ_DRUGS_IN.deleteDrugsIn", id);
  }
  
  public List<JSONObject> findAllCostOrder(Map<String, Object> dataMap)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN.findAllCostOrder", dataMap);
  }
  
  public List<KqdsCostorderDetail> findCostOrderDetailNoSend()
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailNoSend", null);
  }
  
  public List<JSONObject> findCostOrderDetailByCostno(String costno)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailByCostno", costno);
  }
  
  public KqdsCostorderDetail findCostOrderDetailBySeqid(String seqId)
    throws Exception
  {
    return (KqdsCostorderDetail)this.dao.findForObject("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailBySeqid", seqId);
  }
  
  public List<JSONObject> findCostOrderDetailReturnBySeqid(String seqid)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailReturnBySeqid", seqid);
  }
  
  public List<JSONObject> findCostOrderDetailById(String seqId)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailBySeqId", seqId);
  }
  
  public Integer updateCostOrderById(String costno)
    throws Exception
  {
    return (Integer)this.dao.update("HUDH_YKZZ_DRUGS_IN.updateCostOrderById", costno);
  }
  
  public void insertCostOrderDetailReturnBySeqId(KqdsCostorderDetail kcd)
    throws Exception
  {
    this.dao.update("HUDH_YKZZ_DRUGS_IN.insertCostOrderDetailReturnBySeqId", kcd);
  }
  
  public void updateCheckStatus(String id)
    throws Exception
  {
    this.dao.update("HUDH_YKZZ_DRUGS_IN.updateCheckStatus", id);
  }
  
  public KqdsCostorderDetail findCostOrderDetailByParentid(String seqid)
    throws Exception
  {
    KqdsCostorderDetail i = (KqdsCostorderDetail)this.dao.findForObject("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailByParentid", seqid);
    return i;
  }
  
  public List<JSONObject> findCostOrderDetailByQfbh(String qfbh)
    throws Exception
  {
    List<JSONObject> i = (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailByQfbh", qfbh);
    return i;
  }
  
  public KqdsCostorderDetail findCostOrderDetailSubtotalByQfbh(String qfbh)
    throws Exception
  {
    KqdsCostorderDetail i = (KqdsCostorderDetail)this.dao.findForObject("HUDH_YKZZ_DRUGS_IN.findCostOrderDetailSubtotalByQfbh", qfbh);
    return i;
  }
}
