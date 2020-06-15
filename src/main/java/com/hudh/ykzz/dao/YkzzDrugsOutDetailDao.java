package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzDrugsOutDetailDao
{
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveOutDetail(List<YkzzDrugsOutDetail> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_YKZZ_DRUGS_OUT_DETAIL.batchSaveOutDetail", list);
  }
  
  public List<JSONObject> findDetailByParendId(String parentId)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_YKZZ_DRUGS_OUT_DETAIL.findDetailByParendId", parentId);
  }
  
  public void deleteDrugsOut(String id)
    throws Exception
  {
    this.dao.delete("HUDH_YKZZ_DRUGS_OUT_DETAIL.deleteDrugsOutDetailByParendId", id);
  }
  
  public List<YkzzDrugsOutDetail> findDrugsOutDetailByOrderno(String order_no)
    throws Exception
  {
    List<YkzzDrugsOutDetail> list = (List)this.dao.findForList("HUDH_YKZZ_DRUGS_OUT_DETAIL.findDrugsOutDetailByOrderno", order_no);
    return list;
  }
  
  public String findDrugsOutDetailByOrdernoAndBatchnum(Map<String, String> map)
    throws Exception
  {
    String list = (String)this.dao.findForObject("HUDH_YKZZ_DRUGS_OUT_DETAIL.findDrugsOutDetailByOrdernoAndBatchnum", map);
    return list;
  }
  
  public String findOutNumsByAll(Map<String, String> map)
    throws Exception
  {
    String list = (String)this.dao.findForObject("HUDH_YKZZ_DRUGS_OUT_DETAIL.findOutNumsByAll", map);
    return list;
  }
  
  public String findBatchnumSaveOutNumsByOrdernoAndBatchnum(Map<String, String> map)
    throws Exception
  {
    String list = (String)this.dao.findForObject("HUDH_YKZZ_DRUGS_BATCHNUM_SAVE.findBatchnumSaveOutNumsByOrdernoAndBatchnum", map);
    return list;
  }
  
  public String findOutNumsByBatchnumSave(Map<String, String> map)
    throws Exception
  {
    String list = (String)this.dao.findForObject("HUDH_YKZZ_DRUGS_BATCHNUM_SAVE.findOutNumsByBatchnumSave", map);
    return list;
  }
}
