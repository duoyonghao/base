package com.kqds.service.base.hospitalOrder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Hospital_OrderLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject getTopHosOrderCurrDay(String usercode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getTopHosOrderCurrDay", usercode);
    if ((list != null) && (list.size() > 0)) {
      return (JSONObject)list.get(0);
    }
    return null;
  }
  
  public JSONObject selectNoPage(String table, Map<String, String> map, String visualstaff, BootStrapPage bp, JSONObject json)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(visualstaff)) {
      map.put("visualstaff", visualstaff);
    }
    if (!YZUtility.isNullorEmpty((String)map.get("username")))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("username")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("username")));
    }
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "h.usercode");
      }
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "h.username");
      }
      if (((String)map.get("sortName")).equals("phonenumber1")) {
        map.put("sortName", "u.PhoneNumber1");
      }
      if (((String)map.get("sortName")).equals("firstaskperson")) {
        map.put("sortName", "per5.user_name");
      }
      if (((String)map.get("sortName")).equals("askperson")) {
        map.put("sortName", "per1.user_name");
      }
      if (((String)map.get("sortName")).equals("orderitemtype")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (((String)map.get("sortName")).equals("zdoorstatus")) {
        map.put("sortName", "u.doorstatus");
      }
      if (((String)map.get("sortName")).equals("orderstatus")) {
        map.put("sortName", "h.orderstatus");
      }
      if (((String)map.get("sortName")).equals("cjstatus")) {
        map.put("sortName", "h.cjstatus");
      }
      if (((String)map.get("sortName")).equals("starttime")) {
        map.put("sortName", "h.starttime");
      }
      if (((String)map.get("sortName")).equals("endtime")) {
        map.put("sortName", "h.endtime");
      }
      if (((String)map.get("sortName")).equals("ordertime")) {
        map.put("sortName", "h.ordertime");
      }
      if (((String)map.get("sortName")).equals("isdelete")) {
        map.put("sortName", "h.isdelete");
      }
      if (((String)map.get("sortName")).equals("cjr")) {
        map.put("sortName", "per3.user_name");
      }
      if (((String)map.get("sortName")).equals("jdr")) {
        map.put("sortName", "per4.user_name");
      }
      if (((String)map.get("sortName")).equals("jdsj")) {
        map.put("sortName", "u.createtime");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "u.usercode");
      }
    }
    List<JSONObject> list1 = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectNoPageNum", map);
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectNoPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    for (JSONObject job : list)
    {
      String cjstatus = job.getString("cjstatus");
      if (ConstUtil.CJ_STATUS_1.equals(cjstatus)) {
        cjstatus = ConstUtil.CJ_STATUS_1_DESC;
      } else {
        cjstatus = ConstUtil.CJ_STATUS_0_DESC;
      }
      job.put("cjstatus", cjstatus);
      
      String zdoorstatus = job.getString("zdoorstatus");
      if (ConstUtil.DOOR_STATUS_1.equals(zdoorstatus)) {
        zdoorstatus = ConstUtil.DOOR_STATUS_1_DESC;
      } else {
        zdoorstatus = ConstUtil.DOOR_STATUS_0_DESC;
      }
      job.put("zdoorstatus", zdoorstatus);
      
      String orderstatus = job.getString("orderstatus");
      if (ConstUtil.DOOR_STATUS_1.equals(orderstatus)) {
        orderstatus = ConstUtil.DOOR_STATUS_1_DESC;
      } else {
        orderstatus = ConstUtil.DOOR_STATUS_0_DESC;
      }
      job.put("orderstatus", orderstatus);
      
      String isdelete = job.getString("isdelete");
      if (ConstUtil.YY_ORDER_STATUS_1.equals(isdelete)) {
        isdelete = ConstUtil.YY_ORDER_STATUS_1_DESC;
      } else {
        isdelete = ConstUtil.YY_ORDER_STATUS_0_DESC;
      }
      job.put("isdelete", isdelete);
    }
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    jobj.put("nums", list1);
    return jobj;
  }
  
  public List<JSONObject> selectYyxxByUsercode(String table, String usercode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectYyxxByUsercode", usercode);
    for (JSONObject job : list)
    {
      String cjstatus = job.getString("cjstatus");
      if (ConstUtil.CJ_STATUS_1.equals(cjstatus)) {
        cjstatus = ConstUtil.CJ_STATUS_1_DESC;
      } else {
        cjstatus = ConstUtil.CJ_STATUS_0_DESC;
      }
      job.put("cjstatus", cjstatus);
      
      String zdoorstatus = job.getString("zdoorstatus");
      if (ConstUtil.DOOR_STATUS_1.equals(zdoorstatus)) {
        zdoorstatus = ConstUtil.DOOR_STATUS_1_DESC;
      } else {
        zdoorstatus = ConstUtil.DOOR_STATUS_0_DESC;
      }
      job.put("zdoorstatus", zdoorstatus);
      
      String orderstatus = job.getString("orderstatus");
      if (ConstUtil.DOOR_STATUS_1.equals(orderstatus)) {
        orderstatus = ConstUtil.DOOR_STATUS_1_DESC;
      } else {
        orderstatus = ConstUtil.DOOR_STATUS_0_DESC;
      }
      job.put("orderstatus", orderstatus);
      
      String isdelete = job.getString("isdelete");
      if (ConstUtil.YY_ORDER_STATUS_1.equals(isdelete)) {
        isdelete = ConstUtil.YY_ORDER_STATUS_1_DESC;
      } else {
        isdelete = ConstUtil.YY_ORDER_STATUS_0_DESC;
      }
      job.put("isdelete", isdelete);
    }
    return list;
  }
  
  public JSONObject selectHospitalOrderList(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization, Map<String, String> map, BootStrapPage bp)
    throws Exception
  {
    if ((YZUtility.isNullorEmpty(querytype)) || (querytype.equals("null")) || (querytype.equals("undefined"))) {
      map.put("visualstaff", visualstaff);
    }
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(searchValue))
    {
      map.put("searchValue", searchValue);
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
    }
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("orderstatus")) {
        map.put("sortName", "h.orderstatus");
      }
      if (((String)map.get("sortName")).equals("starttime")) {
        map.put("sortName", "h.starttime");
      }
      if (((String)map.get("sortName")).equals("endtime")) {
        map.put("sortName", "h.endtime");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "h.usercode");
      }
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "h.username");
      }
      if (((String)map.get("sortName")).equals("ordertypename")) {
        map.put("sortName", "kcit2.dict_name");
      }
      if (((String)map.get("sortName")).equals("askpersonname")) {
        map.put("sortName", "per1.user_name");
      }
      if (((String)map.get("sortName")).equals("orderitemtypename")) {
        map.put("sortName", "kcit1.dict_name");
      }
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectHospitalOrderList", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    if (bp.getOffset() == 0)
    {
      JSONObject list1 = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectHospitalOrderListNum", map);
      if (list1 != null)
      {
        jobj.put("cjs", list1.get("cjs"));
        jobj.put("sms", list1.get("sms"));
      }
    }
    jobj.put("offset", Integer.valueOf(bp.getOffset()));
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public int getCountIndex(String querytype, String searchValue, String visualstaff, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    if ((YZUtility.isNullorEmpty(querytype)) || (querytype.equals("null")) || (querytype.equals("undefined"))) {
      map.put("visualstaff", visualstaff);
    }
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(searchValue))
    {
      map.put("searchValue", searchValue);
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
    }
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getCountIndex", map)).intValue();
    return count;
  }
  
  public List<JSONObject> yycx(String doctors, String startime1, String endTime1)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("doctors", doctors);
    map.put("startime1", startime1);
    map.put("endTime1", endTime1);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".yycx", map);
    return list;
  }
  
  public List<JSONObject> yycxUser(String usercode, String startime1, String endTime1)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("startime1", startime1);
    map.put("endTime1", endTime1);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".yycxUser", map);
    return list;
  }
  
  public List<JSONObject> selectList(String table, Map<String, String> map, String visualstaff, String organization)
    throws Exception
  {
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectList", map);
    return list;
  }
  
  public int checkYy(Map<String, String> map)
    throws Exception
  {
    if (map.containsKey("starttime")) {
      if (((String)map.get("starttime")).toString().length() == 10) {
        map.put("starttimeShort", (String)map.get("starttime") + ConstUtil.TIME_START);
      } else {
        map.put("starttimeLong", ((String)map.get("starttime")).substring(0, 10));
      }
    }
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".checkYy", map)).intValue();
    return count;
  }
  
  public JSONObject orderstatusString(Map<String, String> map)
    throws Exception
  {
    map.put("starttime", ((String)map.get("starttime")).substring(0, 10));
    JSONObject jobj = new JSONObject();
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".orderstatusString", map);
    if ((list != null) && (list.size() > 0)) {
      jobj = (JSONObject)list.get(0);
    }
    return jobj;
  }
  
  public List<JSONObject> checkHz(Map<String, String> map)
    throws Exception
  {
    map.put("starttime", ((String)map.get("starttime")).substring(0, 10));
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".checkHz", map);
    return list;
  }
  
  public List<JSONObject> selectPerson(Map<String, String> map, String organization, String visualstaffYyrl)
    throws Exception
  {
    map.put("visualstaffYyrl", visualstaffYyrl);
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectPerson", map);
    return list;
  }
  
  public Double getSml(String date, String yytype, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("starttime", date + ConstUtil.TIME_START);
    map.put("endtime", date + ConstUtil.TIME_END);
    if (!YZUtility.isNullorEmpty(yytype)) {
      map.put("yytype", yytype);
    }
    map.put("organization", organization);
    
    Double sml = Double.valueOf(0.0D);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getSml", map);
    if ((list != null) && (list.size() > 0))
    {
      double tmpSml = YZUtility.isNullorEmpty(((JSONObject)list.get(0)).getString("sml")) ? 0.0D : ((JSONObject)list.get(0)).getDouble("sml");
      sml = Double.valueOf(new BigDecimal(tmpSml).setScale(1, 5).doubleValue());
    }
    return sml;
  }
  
  public int getCountYy(String table, String date, String yytype, String type, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("starttime", date + ConstUtil.TIME_START);
    map.put("endtime", date + ConstUtil.TIME_END);
    if (!YZUtility.isNullorEmpty(yytype)) {
      map.put("yytype", yytype);
    }
    map.put("organization", organization);
    if ("del".equals(type)) {
      map.put("isdelete", "1");
    }
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getCountYy", map)).intValue();
    return nums;
  }
  
  public int getCountYysm(String table, String date, String yytype, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("starttime", date + ConstUtil.TIME_START);
    map.put("endtime", date + ConstUtil.TIME_END);
    if (!YZUtility.isNullorEmpty(yytype)) {
      map.put("yytype", yytype);
    }
    map.put("organization", organization);
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getCountYysm", map)).intValue();
    return nums;
  }
  
  public int coutYYPerson(String doctors, String startime1, String endTime1, String seqId)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("doctors", doctors);
    map.put("starttime", startime1);
    map.put("endtime", endTime1);
    if (!YZUtility.isNullorEmpty(seqId)) {
      map.put("seqId", seqId);
    }
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".coutYYPerson", map)).intValue();
    return count;
  }
  
  public List<JSONObject> findHospitalOrderByOrdernumAndnodeId(String order_number, String nodeId)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("order_number", order_number);
    dataMap.put("nodeId", nodeId);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".findHospitalOrderByOrdernumAndnodeId", dataMap);
    return list;
  }
  
  public List<JSONObject> selectHospitalOrderByUsercodeAndTime(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectHospitalOrderByUsercodeAndTime", map);
    return list;
  }
}
