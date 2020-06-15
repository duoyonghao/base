package com.kqds.service.app;

import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_WorkLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> getWdList(String table, int days, String visualstaff, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    Map<String, String> map = new HashMap();
    sb.append(" and " + SQLUtil.dateDiffDay("n.ordertime", days));
    sb.append(" and " + SQLUtil.length("n.ordertime") + " >= 16 ");
    map.put("ordertime", sb.toString());
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getWdList", map);
    return list;
  }
  
  public List<JSONObject> getWdListByUsercode(String table, String usercode, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getWdListByUsercode", map);
    return list;
  }
  
  public List<JSONObject> getMzListByUsercode(String table, String usercode, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzListByUsercode", map);
    return list;
  }
  
  public List<JSONObject> getMzList(String table, int days, String visualstaff, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    Map<String, String> map = new HashMap();
    sb.append(" and " + SQLUtil.dateDiffDay("n.starttime", days));
    map.put("starttime", sb.toString());
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzList", map);
    return list;
  }
  
  public JSONObject getMzListPage(String table, Map<String, String> map, String visualstaff, String organization)
    throws Exception
  {
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    if (map.containsKey("search"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("search")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("search")));
    }
    int pageSize = Integer.parseInt((String)map.get("pageSize"));
    int nowpage = Integer.parseInt((String)map.get("nowpage"));
    map.put("num1", pageSize);
    map.put("num2", nowpage * pageSize);
    
    JSONObject jobj = new JSONObject();
    if ((map.containsKey("ispaging")) && 
      (((String)map.get("ispaging")).equals("1")) && 
      (nowpage == 0))
    {
      Map<String, String> wheremap = new HashMap();
      int total = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzListPageTotal", map)).intValue();
      jobj.put("total", Integer.valueOf(total));
      
      wheremap.put("n.orderstatus", "1");
      int cztotal = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzListPageTotal2", map)).intValue();
      jobj.put("smtotal", Integer.valueOf(cztotal));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getMzListPage", map);
    jobj.put("data", list);
    return jobj;
  }
  
  public int getRegCount(String table, String visualstaff, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    Map<String, String> map = new HashMap();
    sb.append(" and " + SQLUtil.dateDiffDay("r.createtime"));
    map.put("createtime", sb.toString());
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getRegCount", map)).intValue();
    return count;
  }
  
  public JSONObject getRegList(String table, Map<String, String> map, String visualstaff, String organization)
    throws Exception
  {
    if ((!map.containsKey("starttime")) && (!map.containsKey("search"))) {
      map.put("createtime", "");
    }
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    if (map.containsKey("search"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("search")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("search")));
    }
    int pageSize = Integer.parseInt((String)map.get("pageSize"));
    int nowpage = Integer.parseInt((String)map.get("nowpage"));
    map.put("num1", pageSize);
    map.put("num2", nowpage * pageSize);
    
    JSONObject jobj = new JSONObject();
    if ((map.containsKey("ispaging")) && 
      (((String)map.get("ispaging")).equals("1"))) {
      if (nowpage == 0)
      {
        int total = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getRegListTotal", map)).intValue();
        jobj.put("total", Integer.valueOf(total));
        
        int cztotal = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getRegListTotal2", map)).intValue();
        jobj.put("cztotal", Integer.valueOf(cztotal));
      }
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getRegList", map);
    jobj.put("data", list);
    return jobj;
  }
  
  public JSONObject selectUserInfo(String table, String usercode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectUserInfo", usercode);
    return (JSONObject)list.get(0);
  }
  
  public List<JSONObject> getHfList(String table, String visualstaff, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    Map<String, String> map = new HashMap();
    sb.append(" and " + SQLUtil.dateDiffDay("visittime"));
    map.put("visittime", sb.toString());
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getHfList", map);
    return list;
  }
  
  public JSONObject getHfListPage(String table, Map<String, String> map, String visualstaff, String organization)
    throws Exception
  {
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    int pageSize = Integer.parseInt((String)map.get("pageSize"));
    int nowpage = Integer.parseInt((String)map.get("nowpage"));
    map.put("num1", pageSize);
    map.put("num2", nowpage * pageSize);
    JSONObject jobj = new JSONObject();
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_VISIT + ".getHfListPage", map);
    jobj.put("data", list);
    return jobj;
  }
  
  public List<JSONObject> getHfListByUsercode(String table, String usercode, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("organization", organization);
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getHfListByUsercode", map);
    return list;
  }
  
  public List<JSONObject> getCostListByUsercode(String table, String usercode, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("organization", organization);
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getCostListByUsercode", map);
    return list;
  }
}
