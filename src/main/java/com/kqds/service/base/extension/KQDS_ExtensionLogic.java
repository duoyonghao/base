package com.kqds.service.base.extension;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsExtensionType;
import com.kqds.entity.base.VisitDataCount;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_ExtensionLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectList2(String table, Map<String, String> map, String visualstaff)
    throws Exception
  {
    map.put("visualstaff", visualstaff);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_EXTENSION + ".selectList2", map);
    for (JSONObject job : list)
    {
      String memberno = job.getString("memberno");
      if (!"0".equals(memberno)) {
        memberno = "是";
      } else {
        memberno = "否";
      }
      job.put("memberno", memberno);
      
      String status = job.getString("status");
      if (("".equals(status)) || ("0".equals(status))) {
        status = "未完成";
      } else {
        status = "已完成";
      }
      job.put("status", status);
    }
    return list;
  }
  
  public List<VisitDataCount> selectCountByQuery(String table, Map<String, String> map, String visualstaff)
    throws Exception
  {
    List<VisitDataCount> list = new ArrayList();
    map.put("visualstaff", visualstaff);
    List<JSONObject> jsonList = (List)this.dao.findForList(TableNameUtil.KQDS_EXTENSION + ".selectCountByQuery", map);
    for (JSONObject rs : jsonList)
    {
      VisitDataCount d = new VisitDataCount();
      d.setJhseqId(rs.getString("extension"));
      d.setJhname(rs.getString("jhname"));
      d.setCount(String.valueOf(rs.getInt("flcount")));
      list.add(d);
    }
    return list;
  }
  
  public int selectZongCount(String table, Map<String, String> map, String visualstaff)
    throws Exception
  {
    map.put("visualstaff", visualstaff);
    int sum = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_EXTENSION + ".selectZongCount", map)).intValue();
    return sum;
  }
  
  public List getExtensoionList(String organization, Map<String, String> map, String visualstaff)
    throws Exception
  {
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    List<KqdsExtensionType> list = new ArrayList();
    List<JSONObject> jsonlist = (List)this.dao.findForList(TableNameUtil.KQDS_EXTENSION + ".getExtensoionList", map);
    for (JSONObject rs : jsonlist)
    {
      KqdsExtensionType d = new KqdsExtensionType();
      d.setSeqId(rs.getString("seq_id"));
      d.setJhname(rs.getString("jhname"));
      list.add(d);
    }
    return list;
  }
  
  public int checkIsFinish(String seqId)
    throws Exception
  {
    int status = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_EXTENSION + ".checkIsFinish", seqId)).intValue();
    return status;
  }
  
  public List<JSONObject> selectDetail(String seqId)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_EXTENSION + ".selectDetail", seqId);
    for (JSONObject job : list)
    {
      String memberno = job.getString("memberno");
      if (!"0".equals(memberno)) {
        memberno = "是";
      } else {
        memberno = "否";
      }
      job.put("memberno", memberno);
      String status = job.getString("status");
      if (("".equals(status)) || ("0".equals(status))) {
        status = "未完成";
      } else {
        status = "已完成";
      }
      job.put("status", status);
    }
    return list;
  }
  
  public List<JSONObject> getTodayList(String currentOrganization, String visualstaff)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("visualstaff", visualstaff);
    map.put("organization", currentOrganization);
    map.put("visittime", SQLUtil.getCurrDate());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_EXTENSION + ".getTodayList", map);
    return list;
  }
  
  public int getCountIndex(String visualstaff, String organization)
    throws Exception
  {
    int count = 0;
    List<JSONObject> list = getTodayList(organization, visualstaff);
    if ((list != null) && (list.size() > 0)) {
      count = list.size();
    }
    return count;
  }
  
  public void JhTimeOut()
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("visittime", SQLUtil.getCurrDate());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_EXTENSION + ".JhTimeOut", map);
    if ((list != null) && (list.size() > 0)) {
      for (JSONObject json : list)
      {
        map.put("visitor", json.getString("visitor"));
        map.put("usercode", json.getString("usercode"));
        this.dao.delete(TableNameUtil.KQDS_EXTENSION + ".deletepush", map);
        
        PushUtil.saveTx4ExtensionTimeOut(json);
      }
    }
  }
}
