package com.hudh.lclj.dao;

import com.hudh.lclj.entity.OperationNodeInfor;
import com.kqds.dao.DaoSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOperationNodeInforDao {
  @Autowired
  private DaoSupport dao;
  
  public void insertOperationNodeInfor(OperationNodeInfor dp) throws Exception {
    this.dao.save("HUDH_LCLJ_OPERATION_NODE_INFOR.insertOperationNodeInfor", dp);
  }
  
  public JSONObject selectOperationNodeInforById(String id) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_LCLJ_OPERATION_NODE_INFOR.selectOperationNodeInforById", id);
    return json;
  }
  
  public List<JSONObject> selectOperationNodeInforAll() throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_LCLJ_OPERATION_NODE_INFOR.selectOperationNodeInforAll", null);
    return list;
  }
  
  public void updateOperationNodeInforById(OperationNodeInfor dp) throws Exception {
    this.dao.update("HUDH_LCLJ_OPERATION_NODE_INFOR.updateOperationNodeInforById", dp);
  }
  
  public void deleteOperationNodeInforById(String id) throws Exception {
    this.dao.delete("HUDH_LCLJ_OPERATION_NODE_INFOR.deleteOperationNodeInforById", id);
  }
  
  public JSONObject selectOperationNodeInforByOrdernumberAndNodeId(Map<String, String> map) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_LCLJ_OPERATION_NODE_INFOR.selectOperationNodeInforByOrdernumberAndNodeId", map);
    return json;
  }
  
  public void updateOperationNodeInforByOrderNumberAndNodeId(OperationNodeInfor dp) throws Exception {
    this.dao.update("HUDH_LCLJ_OPERATION_NODE_INFOR.updateOperationNodeInforByOrderNumberAndNodeId", dp);
  }
  
  public void updateOrderTimeHospital(String order_number, String nodeId, String orderTime) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("order_number", order_number);
    dataMap.put("nodeId", nodeId);
    dataMap.put("orderTime", orderTime);
    this.dao.update("HUDH_LCLJ_OPERATION_NODE_INFOR.updateOrderTimeHospital", dataMap);
  }
  
  public void cancelTimeHospital(String order_number, String nodeId) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("order_number", order_number);
    dataMap.put("nodeId", nodeId);
    this.dao.update("HUDH_LCLJ_OPERATION_NODE_INFOR.cancelTimeHospital", dataMap);
  }
  
  public JSONObject findOperationNodeInforByCancelTimeHospital(String next_hospital_time, String visit_time, String order_number) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("next_hospital_time", next_hospital_time);
    dataMap.put("visit_time", visit_time);
    dataMap.put("order_number", order_number);
    JSONObject jsonObject = (JSONObject)this.dao.findForObject("HUDH_LCLJ_OPERATION_NODE_INFOR.findOperationNodeInforByCancelTimeHospital", dataMap);
    return jsonObject;
  }
}
