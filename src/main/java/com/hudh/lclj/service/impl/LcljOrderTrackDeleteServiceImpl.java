package com.hudh.lclj.service.impl;

import com.hudh.lclj.dao.LcljOrderTrackDeleteDao;
import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import com.hudh.lclj.service.ILcljOrderTrackDeleteService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOrderTrackDeleteServiceImpl
  implements ILcljOrderTrackDeleteService
{
  @Autowired
  private LcljOrderTrackDeleteDao orderTrackDeleteDao;
  
  public void save(LcljOrderTrackDeleteRecord dp, HttpServletRequest request)
    throws Exception
  {
    String order_number = request.getParameter("order_number");
    String lcljId = request.getParameter("lcljId");
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    dp.setSeqId(YZUtility.getUUID());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    dp.setCreateuser(person.getSeqId());
    dp.setOrganization(organization);
    dp.setLcljId(lcljId);
    dp.setOrder_number(order_number);
    this.orderTrackDeleteDao.save(dp);
  }
}
