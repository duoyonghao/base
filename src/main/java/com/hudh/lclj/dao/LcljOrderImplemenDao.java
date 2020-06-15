package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljOrderImplemen;
import com.kqds.dao.DaoSupport;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOrderImplemenDao
{
  @Autowired
  private DaoSupport dao;
  
  public int saveLcljOrderImplemen(LcljOrderImplemen lcljOrderImplemen)
    throws Exception
  {
    int rsCount = ((Integer)this.dao.save("HUDH_LCLJ_ORDERIMPLEMEN.insertLcljOrderImplemen", lcljOrderImplemen)).intValue();
    return rsCount;
  }
  
  public LcljOrderImplemen findLcljOrderImplemenByTrackId(String orderTrackId)
    throws Exception
  {
    LcljOrderImplemen lcljOrderImplemen = (LcljOrderImplemen)this.dao.findForObject("HUDH_LCLJ_ORDERIMPLEMEN.findLcljOrderImplemenByTrackId", orderTrackId);
    return lcljOrderImplemen;
  }
  
  public LcljOrderImplemen updateOperateNoteInfo(LcljOrderImplemen lcljOrderImplemen)
    throws Exception
  {
    LcljOrderImplemen lo = (LcljOrderImplemen)this.dao.findForObject("HUDH_LCLJ_ORDERIMPLEMEN.updateOperateNoteInfo", lcljOrderImplemen);
    return lo;
  }
  
  public LcljOrderImplemen changeOperateStatus(LcljOrderImplemen lcljOrderImplemen)
    throws Exception
  {
    LcljOrderImplemen lo = (LcljOrderImplemen)this.dao.findForObject("HUDH_LCLJ_ORDERIMPLEMEN.updateOperateNoteInfo", lcljOrderImplemen);
    return lo;
  }
  
  public JSONObject selectRemakeInfor()
  {
    return null;
  }
}
