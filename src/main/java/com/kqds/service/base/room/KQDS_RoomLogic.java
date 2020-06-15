package com.kqds.service.base.room;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsRoom;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_RoomLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public int countRoom(Map<String, String> map, String seqId)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(seqId)) {
      map.put("seqId", seqId);
    }
    if ((!map.containsKey("roomid")) && (!map.containsKey("doctor")) && (!map.containsKey("nurse")) && (!map.containsKey("usercode"))) {
      return 0;
    }
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_ROOM + ".countRoom", map)).intValue();
    return count;
  }
  
  public List checkRoom(Map map)
    throws Exception
  {
    if ((!map.containsKey("roomid")) && (!map.containsKey("doctor")) && (!map.containsKey("nurse")) && (!map.containsKey("usercode"))) {
      return new ArrayList();
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_ROOM + ".checkRoom2", map);
    return list;
  }
  
  public KqdsHospitalOrder checkOrderUsercode(Map map)
    throws Exception
  {
    KqdsHospitalOrder order = new KqdsHospitalOrder();
    if ((!map.containsKey("usercode")) && (!map.containsKey("doctor")) && (!map.containsKey("nurse"))) {
      return order;
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_ROOM + ".checkOrderUsercode", map);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      order.setUsercode(rs.getString("usercode"));
      order.setUsername(rs.getString("username"));
      order.setAskperson(rs.getString("askperson"));
      order.setStarttime(rs.getString("starttime"));
      order.setEndtime(rs.getString("endtime"));
    }
    return order;
  }
  
  public KqdsRoom checkSSSUsercode(Map map)
    throws Exception
  {
    KqdsRoom order = new KqdsRoom();
    if ((!map.containsKey("usercode")) && (!map.containsKey("doctor"))) {
      return order;
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_ROOM + ".checkSSSUsercode", map);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      order.setUsercode(rs.getString("usercode"));
      order.setNurse(rs.getString("nurse"));
      order.setDoctor(rs.getString("doctor"));
      order.setStarttime(rs.getString("starttime"));
      order.setEndtime(rs.getString("endtime"));
    }
    return order;
  }
  
  public List selectList(String table, Map<String, String> map, String organization)
    throws Exception
  {
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_ROOM + ".selectList", map);
    return list;
  }
  
  public JSONObject selectNoPage(String table, Map<String, String> map, String visualstaff, String jrroom, String searchValue, BootStrapPage bp, JSONObject json)
    throws Exception
  {
    if (map.containsKey("username"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("username")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("username")));
    }
    map.put("jrroom", jrroom);
    if (!YZUtility.isNullorEmpty(visualstaff)) {
      map.put("visualstaff", visualstaff);
    }
    if (!YZUtility.isNullorEmpty(searchValue)) {
      map.put("searchValue", searchValue);
    }
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("roomname")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (((String)map.get("sortName")).equals("roomstatusname")) {
        map.put("sortName", "r.roomstatus");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "r.usercode");
      }
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "u.username");
      }
      if (((String)map.get("sortName")).equals("phonenumber1")) {
        map.put("sortName", "u.PhoneNumber1");
      }
      if (((String)map.get("sortName")).equals("doctorname")) {
        map.put("sortName", "per1.user_name");
      }
      if (((String)map.get("sortName")).equals("zzxtname")) {
        map.put("sortName", "zzxtname");
      }
      if (((String)map.get("sortName")).equals("ks")) {
        map.put("sortName", "r.ks");
      }
      if (((String)map.get("sortName")).equals("starttime")) {
        map.put("sortName", "r.starttime");
      }
      if (((String)map.get("sortName")).equals("endtime")) {
        map.put("sortName", "r.endtime");
      }
      if (((String)map.get("sortName")).equals("isdeletename")) {
        map.put("sortName", "r.isdelete");
      }
      if (((String)map.get("sortName")).equals("createusername")) {
        map.put("sortName", "per5.user_name");
      }
      if (((String)map.get("sortName")).equals("jdr")) {
        map.put("sortName", "per4.user_name");
      }
      if (((String)map.get("sortName")).equals("jdsj")) {
        map.put("sortName", "u.createtime");
      }
      if (((String)map.get("sortName")).equals("nursename")) {
        map.put("sortName", "per2.user_name");
      }
      if (((String)map.get("sortName")).equals("zzxtname")) {
        map.put("sortName", "kcit2.dict_name");
      }
      if (((String)map.get("sortName")).equals("remark")) {
        map.put("sortName", "r.remark");
      }
      if (((String)map.get("sortName")).equals("delpersonname")) {
        map.put("sortName", "per3.user_name");
      }
      if (((String)map.get("sortName")).equals("roomname")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "r.usercode");
      }
      if (((String)map.get("sortName")).equals("ks")) {
        map.put("sortName", "r.ks");
      }
    }
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_ROOM + ".selectNoPage", map);
    
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    for (JSONObject job : list)
    {
      String roomstatusname = job.getString("roomstatus");
      if ("0".equals(roomstatusname)) {
        roomstatusname = "手术前";
      } else if ("1".equals(roomstatusname)) {
        roomstatusname = "手术中";
      } else {
        roomstatusname = "手术后";
      }
      job.put("roomstatusname", roomstatusname);
      String isdeletename = job.getString("isdelete");
      if ("1".equals(isdeletename)) {
        isdeletename = "已取消";
      } else {
        isdeletename = "正常";
      }
      job.put("isdeletename", isdeletename);
    }
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    
    return jobj;
  }
  
  public List<JSONObject> selectNoPages(String table, Map<String, String> map, String searchValue, String visualstaff, String jrroom)
    throws Exception
  {
    if (map != null)
    {
      if (map.containsKey("username"))
      {
        map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("username")));
        map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("username")));
      }
      map.put("jrroom", jrroom);
    }
    if (!YZUtility.isNullorEmpty(visualstaff)) {
      map.put("visualstaff", visualstaff);
    }
    if (!YZUtility.isNullorEmpty(searchValue)) {
      map.put("searchValue", searchValue);
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_ROOM + ".selectNoPage", map);
    for (JSONObject job : list)
    {
      String roomstatusname = job.getString("roomstatus");
      if ("0".equals(roomstatusname)) {
        roomstatusname = "手术前";
      } else if ("1".equals(roomstatusname)) {
        roomstatusname = "手术中";
      } else {
        roomstatusname = "手术后";
      }
      job.put("roomstatusname", roomstatusname);
      String isdeletename = job.getString("isdelete");
      if ("1".equals(isdeletename)) {
        isdeletename = "已取消";
      } else {
        isdeletename = "正常";
      }
      job.put("isdeletename", isdeletename);
    }
    return list;
  }
  
  public int getCountIndex(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization)
    throws Exception
  {
    int count = 0;
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    if (person.getUserPriv().equals("1")) {
      visualstaff = "";
    }
    if ((!YZUtility.isNullorEmpty(visualstaff)) && (
      (YZUtility.isNullorEmpty(querytype)) || (querytype.equals("null")) || (querytype.equals("undefined")))) {
      map.put("querytype", visualstaff);
    }
    if (!YZUtility.isNullorEmpty(searchValue)) {
      map.put("searchValue", searchValue);
    }
    try
    {
      count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_ROOM + ".getCountIndex", map)).intValue();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return count;
  }
  
  public KqdsRoom selectByPrimaryKey(String seqId)
    throws Exception
  {
    KqdsRoom kqdsRoom = (KqdsRoom)this.dao.findForObject(TableNameUtil.KQDS_ROOM + ".selectByPrimaryKey", seqId);
    return kqdsRoom;
  }
}
