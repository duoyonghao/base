package com.hudh.dzbl.dao;

import com.hudh.dzbl.entity.DzblDetail;
import com.kqds.dao.DaoSupport;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DzblDetailDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertDzblDetail(DzblDetail dzblDetail)
    throws Exception
  {
    int rsCount = ((Integer)this.dao.save("HUDH_DZBL_DETAIL.insertDzblDetail", dzblDetail)).intValue();
    return rsCount;
  }
  
  public List<JSONObject> findDzblByBlcode(String blCode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_DZBL_DETAIL.findDzblByBlcode", blCode);
    return list;
  }
  
  public void updateDzblById(DzblDetail dzblDetail)
    throws Exception
  {
    this.dao.update("HUDH_DZBL_DETAIL.updateDzblById", dzblDetail);
  }
  
  public JSONObject getBaseUserInfoByUsercode(String blCode)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject("KQDS_USERDOCUMENT.getBaseUserInfoByUsercode", blCode);
  }
  
  public JSONObject findDzblById(String id)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject("HUDH_DZBL_DETAIL.findDzblById", id);
  }
  
  public List<JSONObject> findDzblDetailByUsercodes(String usercodes)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_DZBL_DETAIL.findDzblDetailByUsercodes", usercodes);
    return list;
  }
}
