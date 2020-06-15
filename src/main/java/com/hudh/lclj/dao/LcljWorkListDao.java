package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljWorklist;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljWorkListDao {
  @Autowired
  private DaoSupport dao;
  
  public int insertWorkList(LcljWorklist lcljWorklist) throws Exception {
    return ((Integer)this.dao.save("HUDH_LCLJ_WORKLIST.insertWorkList", lcljWorklist)).intValue();
  }
  
  public List<LcljWorklist> findWorkByOrderNumber(LcljWorklist lcljWorklist) throws Exception {
    List<LcljWorklist> list = (List<LcljWorklist>)this.dao.findForList("HUDH_LCLJ_WORKLIST.findWorkByOrderNumber", lcljWorklist);
    return list;
  }
  
  public void updateWorkByOrderNumber(LcljWorklist lcljWorklist) throws Exception {
    this.dao.update("HUDH_LCLJ_WORKLIST.updateWorkByOrderNumber", lcljWorklist);
  }
  
  public List<LcljWorklist> findHadWorkList(String orderNumber) throws Exception {
    List<LcljWorklist> list = (List<LcljWorklist>)this.dao.findForList("HUDH_LCLJ_WORKLIST.findHadWorkList", orderNumber);
    return list;
  }
  
  public List<LcljWorklist> findHadWorkByOrderNumberAndNodeId(Map<String, String> dataMap) throws Exception {
    List<LcljWorklist> list = (List<LcljWorklist>)this.dao.findForList("HUDH_LCLJ_WORKLIST.findHadWorkByOrderNumberAndNodeId", dataMap);
    return list;
  }
  
  public List<LcljWorklist> findAllWorkList() throws Exception {
    List<LcljWorklist> list = (List<LcljWorklist>)this.dao.findForList("HUDH_LCLJ_WORKLIST.findAllWorkList", null);
    return list;
  }
  
  public void updateOverdueStatus(Map<String, String> dataMap) throws Exception {
    this.dao.update("HUDH_LCLJ_WORKLIST.updateOverdueStatus", dataMap);
  }
  
  public LcljWorklist selectHadWorkData(Map<String, String> dataMap) throws Exception {
    LcljWorklist lcljWorklist = (LcljWorklist)this.dao.findForObject("HUDH_LCLJ_WORKLIST.selectHadWorkData", dataMap);
    return lcljWorklist;
  }
}
