package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljOperateRejectRecord;
import com.kqds.dao.DaoSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOperateRejectRecordDao {
  @Autowired
  private DaoSupport dao;
  
  public void insertOperationNodeReject(LcljOperateRejectRecord dp) throws Exception {
    this.dao.save("HUDH_LCLJ_OPERATION_NODE_REJECT.insertOperationNodeReject", dp);
  }
  
  public List<JSONObject> findOperationNodeRejectByOrderNumber(String orderNumber) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("orderNumber", orderNumber);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_LCLJ_OPERATION_NODE_REJECT.findOperationNodeRejectByOrderNumber", dataMap);
    return list;
  }
}
