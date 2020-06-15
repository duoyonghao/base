package com.hudh.ksll.dao;

import com.hudh.ksll.entity.KsllReplaceMentDetail;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkReplacementDetail;
import com.kqds.util.sys.YZUtility;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KsllReplaceMentDetailDao
{
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveReplacementDetail(List<KsllReplaceMentDetail> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_KSLL_REPLACEMENT_DETAIL.batchSaveReplacementDetail", list);
  }
  
  public List<JSONObject> findDetailByParendId(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_KSLL_REPLACEMENT_DETAIL.findDetailByParendId", map);
  }
  
  public List<JSONObject> findCollorDetailPhByParendId(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_KSLL_COLLOR_DETAIL.findCollorDetailPhByParendId", map);
  }
  
  public int selectReturnNumByGoodscode(String goodscode)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    String date = YZUtility.getDateStr(new Date());
    map.put("starttime", date);
    map.put("endtime", date);
    map.put("goodscode", goodscode);
    return ((Integer)this.dao.findForObject("HUDH_KSLL_REPLACEMENT_DETAIL.selectReturnNumByGoodscode", map)).intValue();
  }
  
  public void batchSaveCkReplacementDetail(List<KqdsCkReplacementDetail> list)
    throws Exception
  {
    this.dao.batchUpdate("KQDS_CK_REPLACEMENT_DETAIL.insertSelective", list);
  }
  
  public List<JSONObject> findCkReplaceMentDetailByParendId(String parentId)
    throws Exception
  {
    return (List)this.dao.findForList("KQDS_CK_REPLACEMENT_DETAIL.selectByParentid", parentId);
  }
}
