package com.hudh.tjhf.service.impl;

import com.hudh.tjhf.dao.AddVisitDao;
import com.hudh.tjhf.entity.VisitPlanTemplate;
import com.hudh.tjhf.entity.VisitTemplate;
import com.hudh.tjhf.service.IAddVisitService;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddVisitServcieImpl
  implements IAddVisitService
{
  @Autowired
  private AddVisitDao visitDao;
  @Autowired
  private DaoSupport dao;
  
  public void saveVisitTemalate(List<VisitTemplate> attribute)
    throws Exception
  {
    this.visitDao.saveVisitTemalate(attribute);
  }
  
  public void saveVisitPlanTemalate(List<VisitPlanTemplate> list)
    throws Exception
  {
    this.visitDao.saveVisitPlanTemalate(list);
  }
  
  public List<VisitTemplate> findTemplate(Map<String, String> map)
    throws Exception
  {
    return this.visitDao.findTemalate(map);
  }
  
  public List<VisitTemplate> findvisitPlanTemplate(String managarId)
    throws Exception
  {
    return this.visitDao.findvisitPlanTemplate(managarId);
  }
  
  public List<JSONObject> findvisitByTime(Map<String, String> map)
    throws Exception
  {
    return this.visitDao.findvisitByTime(map);
  }
  
  public int findvisitByTimeNum(Map<String, String> map)
    throws Exception
  {
    return this.visitDao.findvisitByTimeNum(map);
  }
  
  public int deleteManagarPlan(String managarId)
    throws Exception
  {
    int i = this.visitDao.deleteManagarPlan(managarId);
    return i;
  }
  
  public int deleteManagar(String managarId)
    throws Exception
  {
    int i = this.visitDao.deleteManagar(managarId);
    int y = this.visitDao.deleteManagarPlanByManagarId(managarId);
    return i;
  }
  
  public int updateManagarStatus(VisitTemplate visit)
    throws Exception
  {
    int i = this.visitDao.updateManagarStatus(visit);
    return i;
  }
  
  public List<JSONObject> findoperator(String sysPosition)
    throws Exception
  {
    return (List)this.dao.findForObject(TableNameUtil.SYS_PARA + ".findoperator", sysPosition);
  }
  
  public List<JSONObject> findvisitTemplate(Map<String, String> map)
    throws Exception
  {
    return this.visitDao.findVisitTemalate(map);
  }
}
