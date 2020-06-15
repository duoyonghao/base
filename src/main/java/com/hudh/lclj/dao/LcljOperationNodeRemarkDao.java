package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljOperationNodeRemark;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOperationNodeRemarkDao {
  @Autowired
  private DaoSupport dao;
  
  public void saveNodeRemark(LcljOperationNodeRemark dp) throws Exception {
    this.dao.save("HUDH_LCLJ_OPERATION_NODE_REMARK.saveNodeRemark", dp);
  }
  
  public List<JSONObject> findNodeRemarkByNodeId(Map<String, String> dataMap) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_LCLJ_OPERATION_NODE_REMARK.findNodeRemarkByNodeId", dataMap);
    return list;
  }
}
