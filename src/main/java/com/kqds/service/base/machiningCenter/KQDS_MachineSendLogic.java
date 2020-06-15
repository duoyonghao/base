package com.kqds.service.base.machiningCenter;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMachineSend;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_MachineSendLogic {
  @Autowired
  private DaoSupport dao;
  
  public void saveMachineSend(KqdsMachineSend dp) throws Exception {
    this.dao.save("KQDS_MACHINING_SEND.saveMachineSend", dp);
  }
  
  public void updateMachineSendById(KqdsMachineSend dp) throws Exception {
    this.dao.update("KQDS_MACHINING_SEND.updateMachineSendById", dp);
  }
  
  public List<JSONObject> selectMachineSend(Map<String, String> dataMap) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("KQDS_MACHINING_SEND.selectMachineSend", dataMap);
    return list;
  }
}
