package com.hudh.lclj.service.impl;

import com.hudh.lclj.dao.LcljOperationNodeRemarkDao;
import com.hudh.lclj.entity.LcljOperationNodeRemark;
import com.hudh.lclj.service.ILcljOperationNodeRemarkService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOperationNodeRemarkServiceImpl
  implements ILcljOperationNodeRemarkService
{
  @Autowired
  private LcljOperationNodeRemarkDao remarkDao;
  
  public void saveNodeRemark(LcljOperationNodeRemark dp, HttpServletRequest request)
    throws Exception
  {
    String lcljId = request.getParameter("");
    String order_number = request.getParameter("order_number");
    String nodeName = request.getParameter("nodeName");
    String nodeId = request.getParameter("nodeId");
    String remark = request.getParameter("remark");
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    dp.setOrganization(organization);
    dp.setCreateuser(person.getSeqId());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    dp.setSeqId(YZUtility.getUUID());
    dp.setLcljId(lcljId);
    dp.setNodeName(nodeName);
    dp.setNodeId(nodeId);
    dp.setOrder_number(order_number);
    dp.setRemark(remark);
    
    this.remarkDao.saveNodeRemark(dp);
  }
  
  public List<JSONObject> findNodeRemarkByNodeId(Map<String, String> dataMap)
    throws Exception
  {
    List<JSONObject> list = this.remarkDao.findNodeRemarkByNodeId(dataMap);
    return list;
  }
}
