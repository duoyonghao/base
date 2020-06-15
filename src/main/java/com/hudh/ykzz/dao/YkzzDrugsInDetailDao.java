package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.kqds.dao.DaoSupport;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzDrugsInDetailDao
{
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveInDetail(List<YkzzDrugsInDetail> list)
    throws Exception
  {
    this.dao.batchUpdate("HUDH_YKZZ_DRUGS_IN_DETAIL.batchSaveInDetail", list);
  }
  
  public List<JSONObject> findDetailByParendId(String parentId)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findDetailByParendId", parentId);
  }
  
  public void deleteDrugsIn(String id)
    throws Exception
  {
    this.dao.delete("HUDH_YKZZ_DRUGS_IN_DETAIL.deleteDrugsInDetailByParendId", id);
  }
  
  public List<YkzzDrugsInDetail> findDrugsInDetailByOrderno(String order_no)
    throws Exception
  {
    List<YkzzDrugsInDetail> list = (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findDrugsInDetailByOrderno", order_no);
    return list;
  }
  
  public List<YkzzDrugsInDetail> findBatchnumByOrderno(String order_no)
    throws Exception
  {
    List<YkzzDrugsInDetail> list = (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findBatchnumByOrderno", order_no);
    return list;
  }
  
  public List<YkzzDrugsInDetail> findBatchnumByOrderno1(String order_no)
    throws Exception
  {
    List<YkzzDrugsInDetail> list = (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findBatchnumByOrderno1", order_no);
    return list;
  }
  
  public void updateDrugsInDetail(YkzzDrugsInDetail ykInDetail)
    throws Exception
  {
    this.dao.update("HUDH_YKZZ_DRUGS_IN_DETAIL.updateDrugsInDetail", ykInDetail);
  }
  
  public YkzzDrugsInDetail findYkzzDrugsInDatailById(String id)
    throws Exception
  {
    YkzzDrugsInDetail drugsInDetail = (YkzzDrugsInDetail)this.dao.findForObject("HUDH_YKZZ_DRUGS_IN_DETAIL.findYkzzDrugsInDatailById", id);
    return drugsInDetail;
  }
  
  public YkzzDrugsInDetail findYkzzDrugsInDatailByInDetail(String inDetail)
    throws Exception
  {
    YkzzDrugsInDetail drugsInDetail = (YkzzDrugsInDetail)this.dao.findForObject("HUDH_YKZZ_DRUGS_IN_DETAIL.findYkzzDrugsInDatailByInDetail", inDetail);
    return drugsInDetail;
  }
  
  public int updateYkzzDrugsInDatailByParentId(YkzzDrugsInDetail ykzzDrugsInDetail)
    throws Exception
  {
    int i = ((Integer)this.dao.findForObject("HUDH_YKZZ_DRUGS_IN_DETAIL.updateBatchnumInDetail", ykzzDrugsInDetail)).intValue();
    return i;
  }
  
  public List<JSONObject> findYkzzDrugsInDetailByOrderno(String orderno)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN_DETAIL.findYkzzDrugsInDetailByOrderno", orderno);
    return list;
  }
}
