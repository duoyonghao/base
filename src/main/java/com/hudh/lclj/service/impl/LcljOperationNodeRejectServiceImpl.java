package com.hudh.lclj.service.impl;

import com.hudh.lclj.dao.LcljOperateRejectRecordDao;
import com.hudh.lclj.entity.LcljOperateRejectRecord;
import com.hudh.lclj.service.ILcljOperationNodeRejectService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOperationNodeRejectServiceImpl implements ILcljOperationNodeRejectService {
  @Autowired
  private LcljOperateRejectRecordDao rejectDao;
  
  public void insertOperationNodeReject(LcljOperateRejectRecord dp, HttpServletRequest request) throws Exception {
    dp.setSEQ_ID(YZUtility.getUUID());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    String organization = ChainUtil.getCurrentOrganization(request);
    YZPerson person = SessionUtil.getLoginPerson(request);
    dp.setCreateuser(person.getSeqId());
    dp.setOrganization(organization);
    this.rejectDao.insertOperationNodeReject(dp);
  }
  
  public List<JSONObject> findOperationNodeRejectByOrderNumber(String orderNumber) throws Exception {
    return this.rejectDao.findOperationNodeRejectByOrderNumber(orderNumber);
  }
}
