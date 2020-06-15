package com.hudh.tjhf.dao;

import com.hudh.tjhf.entity.VisitPlanTemplate;
import com.hudh.tjhf.entity.VisitTemplate;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddVisitDao {
  @Autowired
  private DaoSupport dao;
  
  public void saveVisitTemalate(List<VisitTemplate> VisitTemplatelist) throws Exception {
    this.dao.batchUpdate(String.valueOf(TableNameUtil.KQDS_VISIT_PLAN) + ".saveplanTemplate", VisitTemplatelist);
  }
  
  public void saveVisitPlanTemalate(List<VisitPlanTemplate> list) throws Exception {
    this.dao.batchUpdate(String.valueOf(TableNameUtil.KQDS_VISIT_PLAN_TEMPLATE) + ".saveplanTemplate", list);
  }
  
  public List<VisitTemplate> findTemalate(Map<String, String> map) throws Exception {
    return (List<VisitTemplate>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT_PLAN) + ".findTemalate", map);
  }
  
  public List<VisitTemplate> findvisitPlanTemplate(String managarId) throws Exception {
    return (List<VisitTemplate>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT_PLAN_TEMPLATE) + ".findVisitPlanTemalate", managarId);
  }
  
  public List<JSONObject> findvisitByTime(Map<String, String> map) throws Exception {
    return (List<JSONObject>)this.dao.findForList("KQDS_VISIT.findvisitByTime", map);
  }
  
  public int findvisitByTimeNum(Map<String, String> map) throws Exception {
    return ((Integer)this.dao.findForObject("KQDS_VISIT.findvisitByTimeNum", map)).intValue();
  }
  
  public int deleteManagarPlan(String seqids) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("seqids", seqids);
    return ((Integer)this.dao.delete(String.valueOf(TableNameUtil.KQDS_VISIT_PLAN_TEMPLATE) + ".deleteManagarPlan", map)).intValue();
  }
  
  public int deleteManagar(String seqids) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("seqids", seqids);
    return ((Integer)this.dao.delete(String.valueOf(TableNameUtil.KQDS_VISIT_PLAN) + ".deleteManagar", map)).intValue();
  }
  
  public int deleteManagarPlanByManagarId(String seqids) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("seqids", seqids);
    return ((Integer)this.dao.delete(String.valueOf(TableNameUtil.KQDS_VISIT_PLAN_TEMPLATE) + ".deleteManagarPlanByManagarId", map)).intValue();
  }
  
  public int updateManagarStatus(VisitTemplate visit) throws Exception {
    return ((Integer)this.dao.update(String.valueOf(TableNameUtil.KQDS_VISIT_PLAN) + ".updateTemplateStatus", visit)).intValue();
  }
  
  public List<JSONObject> findVisitTemalate(Map<String, String> map) throws Exception {
    return (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT_PLAN) + ".findVisitTemalate", map);
  }
}
