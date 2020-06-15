package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljOperationNodeInforaBerrance;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOperationNodeInforAberranceDao {
  @Autowired
  private DaoSupport dao;
  
  public void saveOperationNodeInforAberrance(LcljOperationNodeInforaBerrance dp) throws Exception {
    this.dao.save("HUDH_LCLJ_OPERATION_NODE_INFOR_ABERRANCE.saveOperationNodeInforAberrance", dp);
  }
  
  public List<JSONObject> findOperationNodeInforAberranceByOrderNumberAndNodeId(Map<String, String> dataMap) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_LCLJ_OPERATION_NODE_INFOR_ABERRANCE.findOperationNodeInforAberranceByOrderNumberAndNodeId", dataMap);
    return list;
  }
  
  public void insertOperationNodeInforAberrance(LcljOperationNodeInforaBerrance dp) throws Exception {
    this.dao.save("HUDH_LCLJ_OPERATION_NODE_INFOR_ABERRANCE.insertOperationNodeInforAberrance", dp);
  }
}
