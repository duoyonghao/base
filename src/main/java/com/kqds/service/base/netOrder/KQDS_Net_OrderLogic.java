package com.kqds.service.base.netOrder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Net_OrderLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private YZDeptLogic deptLogic;
  
  public KqdsNetOrder getNetOrderByWXOrderSeqId(String wxOrderSeqId) throws Exception {
    KqdsNetOrder order = (KqdsNetOrder)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getNetOrderByWXOrderSeqId", wxOrderSeqId);
    return order;
  }
  
  public List<JSONObject> checkNetOrderCount(String seqId, String usercode, String ordertime) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("usercode", usercode);
    map.put("ordertime", ordertime);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".checkNetOrderCount", map);
    return list;
  }
  
  public JSONObject selectFirstByUsercode(String usercode) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectFirstByUsercode", usercode);
    if (list != null && list.size() > 0)
      return list.get(0); 
    return null;
  }
  
  public JSONObject selectNoPageYyzx(String table, Map<String, String> map, String visualstaff, String organization, BootStrapPage<T> bp, JSONObject json) throws Exception {
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    if (map.containsKey("username")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
    } 
    if (!YZUtility.isNullorEmpty(visualstaff))
      map.put("visualstaff", visualstaff); 
    if (map.get("sortName") != null) {
      if (((String)map.get("sortName")).equals("usercode"))
        map.put("sortName", "n.usercode"); 
      if (((String)map.get("sortName")).equals("username"))
        map.put("sortName", "n.username"); 
      if (((String)map.get("sortName")).equals("phonenumber1"))
        map.put("sortName", "u.PhoneNumber1"); 
      if (((String)map.get("sortName")).equals("askperson"))
        map.put("sortName", "per1.user_name"); 
      if (((String)map.get("sortName")).equals("ordertime"))
        map.put("sortName", "n.ordertime"); 
      if (((String)map.get("sortName")).equals("guidetime"))
        map.put("sortName", "n.guidetime"); 
      if (((String)map.get("sortName")).equals("zdoorstatus"))
        map.put("sortName", "u.doorstatus"); 
      if (((String)map.get("sortName")).equals("doorstatus"))
        map.put("sortName", "n.doorstatus"); 
      if (((String)map.get("sortName")).equals("cjstatus"))
        map.put("sortName", "n.cjstatus"); 
      if (((String)map.get("sortName")).equals("jdr"))
        map.put("sortName", "per4.user_name"); 
      if (((String)map.get("sortName")).equals("jdsj"))
        map.put("sortName", "u.createtime"); 
      if (((String)map.get("sortName")).equals("usercode"))
        map.put("sortName", "n.usercode"); 
      if (((String)map.get("sortName")).equals("remark"))
        map.put("sortName", "n.remark"); 
    } 
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectNoPageYyzx", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    for (JSONObject job : list) {
      String cjstatus = job.getString("cjstatus");
      if ("1".equals(cjstatus)) {
        cjstatus = "已成交";
      } else {
        cjstatus = "未成交";
      } 
      job.put("cjstatus", cjstatus);
      String zdoorstatus = job.getString("zdoorstatus");
      if ("1".equals(zdoorstatus)) {
        zdoorstatus = "已上门";
      } else {
        zdoorstatus = "未上门";
      } 
      job.put("zdoorstatus", zdoorstatus);
      String doorstatus = job.getString("doorstatus");
      if ("1".equals(doorstatus)) {
        doorstatus = "已上门";
      } else {
        doorstatus = "未上门";
      } 
      job.put("doorstatus", doorstatus);
    } 
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectNoPageYyzxNet(String table, Map<String, String> map, String visualstaff, String organization, YZPerson person, BootStrapPage bp) throws Exception {
    JSONObject json = new JSONObject();
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    if (map.containsKey("username")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
    } 
    if (!YZUtility.isNullorEmpty(visualstaff))
      map.put("visualstaff", visualstaff); 
    if (map.get("sortName") != null) {
      if (((String)map.get("sortName")).equals("usercode"))
        map.put("sortName", "n.usercode"); 
      if (((String)map.get("sortName")).equals("username"))
        map.put("sortName", "n.username"); 
      if (((String)map.get("sortName")).equals("phonenumber1"))
        map.put("sortName", "u.PhoneNumber1"); 
      if (((String)map.get("sortName")).equals("askperson"))
        map.put("sortName", "per1.user_name"); 
      if (((String)map.get("sortName")).equals("ordertime"))
        map.put("sortName", "n.ordertime"); 
      if (((String)map.get("sortName")).equals("guidetime"))
        map.put("sortName", "n.guidetime"); 
      if (((String)map.get("sortName")).equals("zdoorstatus"))
        map.put("sortName", "u.doorstatus"); 
      if (((String)map.get("sortName")).equals("doorstatus"))
        map.put("sortName", "n.doorstatus"); 
      if (((String)map.get("sortName")).equals("cjstatus"))
        map.put("sortName", "n.cjstatus"); 
      if (((String)map.get("sortName")).equals("askitem"))
        map.put("sortName", "kcit1.dict_name"); 
      if (((String)map.get("sortName")).equals("jdr"))
        map.put("sortName", "per4.user_name"); 
      if (((String)map.get("sortName")).equals("jdsj"))
        map.put("sortName", "u.createtime"); 
      if (((String)map.get("sortName")).equals("isdelete"))
        map.put("sortName", "n.isdelete"); 
      if (((String)map.get("sortName")).equals("askcontent"))
        map.put("sortName", "n.askcontent"); 
      if (((String)map.get("sortName")).equals("remark"))
        map.put("sortName", "n.remark"); 
    } 
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    List<JSONObject> list1 = null;
    if ("a361daeb-be3c-4dab-a63d-3d718177c81e".equals(person.getSeqId())) {
      list1 = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectNoPageYyzxNetNum", map);
    } else {
      list1 = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectNoPageYyzxNum", map);
    } 
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = null;
    if ("a361daeb-be3c-4dab-a63d-3d718177c81e".equals(person.getSeqId())) {
      list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectNoPageYyzxNet", map);
    } else {
      list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectNoPageYyzx", map);
    } 
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    for (JSONObject job : list) {
      String cjstatus = job.getString("cjstatus");
      if ("1".equals(cjstatus)) {
        cjstatus = "已成交";
      } else {
        cjstatus = "未成交";
      } 
      job.put("cjstatus", cjstatus);
      String zdoorstatus = job.getString("zdoorstatus");
      if ("1".equals(zdoorstatus)) {
        zdoorstatus = "已上门";
      } else {
        zdoorstatus = "未上门";
      } 
      job.put("zdoorstatus", zdoorstatus);
      String doorstatus = job.getString("doorstatus");
      if ("1".equals(doorstatus)) {
        doorstatus = "已上门";
      } else {
        doorstatus = "未上门";
      } 
      job.put("doorstatus", doorstatus);
    } 
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    jobj.put("nums", list1);
    return jobj;
  }
  
  public JSONObject selectNoPageYyzxWd(String table, Map<String, String> map, String datetype, String organization, String visualstaff, BootStrapPage bp) throws Exception {
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    if (map.containsKey("username")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("username")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("username")));
    } 
    if (!YZUtility.isNullorEmpty(visualstaff))
      map.put("visualstaff", YZUtility.ConvertStringIds4Query(visualstaff)); 
    String wdPersonIds = this.personLogic.getPerIdsByDeptTypeNoOrg(datetype);
    map.put("wdPersonIds", YZUtility.ConvertStringIds4Query(wdPersonIds));
    if (map.get("sortName") != null) {
      if (((String)map.get("sortName")).equals("username"))
        map.put("sortName", "n.username"); 
      if (((String)map.get("sortName")).equals("phonenumber1"))
        map.put("sortName", "u.PhoneNumber1"); 
      if (((String)map.get("sortName")).equals("askperson"))
        map.put("sortName", "per1.user_name"); 
      if (((String)map.get("sortName")).equals("ordertime"))
        map.put("sortName", "n.ordertime"); 
      if (((String)map.get("sortName")).equals("guidetime"))
        map.put("sortName", "n.guidetime"); 
      if (((String)map.get("sortName")).equals("zdoorstatus"))
        map.put("sortName", "u.doorstatus"); 
      if (((String)map.get("sortName")).equals("doorstatus"))
        map.put("sortName", "n.doorstatus"); 
      if (((String)map.get("sortName")).equals("cjstatus"))
        map.put("sortName", "n.cjstatus"); 
      if (((String)map.get("sortName")).equals("askitem"))
        map.put("sortName", "kcit1.dict_name"); 
      if (((String)map.get("sortName")).equals("askcontent"))
        map.put("sortName", "n.askcontent"); 
      if (((String)map.get("sortName")).equals("remark"))
        map.put("sortName", "n.remark"); 
      if (((String)map.get("sortName")).equals("jdr"))
        map.put("sortName", "per4.user_name"); 
      if (((String)map.get("sortName")).equals("jdsj"))
        map.put("sortName", "u.createtime"); 
      if (((String)map.get("sortName")).equals("usercode"))
        map.put("sortName", "u.usercode"); 
    } 
    List<JSONObject> list1 = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectNoPageYyzxWdNum", map);
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectNoPageYyzxWd", map);
    for (JSONObject job : list) {
      String cjstatus = job.getString("cjstatus");
      if ("1".equals(cjstatus)) {
        cjstatus = "已成交";
      } else {
        cjstatus = "未成交";
      } 
      job.put("cjstatus", cjstatus);
      String zdoorstatus = job.getString("zdoorstatus");
      if ("1".equals(zdoorstatus)) {
        zdoorstatus = "已上门";
      } else {
        zdoorstatus = "未上门";
      } 
      job.put("zdoorstatus", zdoorstatus);
      String doorstatus = job.getString("doorstatus");
      if ("1".equals(doorstatus)) {
        doorstatus = "已上门";
      } else {
        doorstatus = "未上门";
      } 
      job.put("doorstatus", doorstatus);
    } 
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", list);
    jobj1.put("nums", list1);
    return jobj1;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List selectWithNopage(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectWithNopage", map);
    return list;
  }
  
  public JSONObject selectNetOrderList(String table, YZPerson person, Map<String, String> map1, BootStrapPage bp) throws Exception {
    Map<String, String> map = new HashMap<>();
    if (YZUtility.isNullorEmpty(map1.get("querytype")) || ((String)map1.get("querytype")).equals("null") || ((String)map1.get("querytype")).equals("undefined"))
      map.put("querytype", map1.get("visualstaff")); 
    map.put("organization", map1.get("organization"));
    if (!YZUtility.isNullorEmpty(map1.get("searchValue"))) {
      map.put("searchValue", map1.get("searchValue"));
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map1.get("searchValue")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map1.get("searchValue")));
    } 
    if (!YZUtility.isNullorEmpty(map1.get("sortName"))) {
      if (((String)map1.get("sortName")).equals("zdoorstatus"))
        map.put("sortName", "u.doorstatus"); 
      if (((String)map1.get("sortName")).equals("doorstatus"))
        map.put("sortName", "n.doorstatus"); 
      if (((String)map1.get("sortName")).equals("cjstatus"))
        map.put("sortName", "n.cjstatus"); 
      if (((String)map1.get("sortName")).equals("ordertime"))
        map.put("sortName", "n.ordertime"); 
      if (((String)map1.get("sortName")).equals("usercode"))
        map.put("sortName", "n.usercode"); 
      if (((String)map1.get("sortName")).equals("username"))
        map.put("sortName", "n.username"); 
      if (((String)map1.get("sortName")).equals("iscreatelclj"))
        map.put("sortName", "u.iscreateLclj"); 
      if (((String)map1.get("sortName")).equals("acceptdate"))
        map.put("sortName", "n.acceptdate"); 
      if (((String)map1.get("sortName")).equals("acceptphone"))
        map.put("sortName", "n.acceptphone"); 
      if (((String)map1.get("sortName")).equals("accepttypename"))
        map.put("sortName", "kcit1.dict_name"); 
      if (((String)map1.get("sortName")).equals("accepttoolname"))
        map.put("sortName", "kcit2.dict_name"); 
      if (((String)map1.get("sortName")).equals("askitemname"))
        map.put("sortName", "kcit4.dict_name"); 
      if (((String)map1.get("sortName")).equals("regdeptname"))
        map.put("sortName", "odept2.dept_name"); 
      if (((String)map1.get("sortName")).equals("askpersonname"))
        map.put("sortName", "per2.user_name"); 
      if (((String)map1.get("sortName")).equals("orderpersonname"))
        map.put("sortName", "per1.user_name"); 
      if (((String)map1.get("sortName")).equals("daoyiname"))
        map.put("sortName", "per3.user_name"); 
      if (((String)map1.get("sortName")).equals("isdelete"))
        map.put("sortName", "n.isdelete"); 
      map.put("sortOrder", map1.get("sortOrder"));
    } 
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectNetOrderList", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", list);
    if (bp.getOffset() == 0) {
      JSONObject list1 = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectNetOrderListNum", map);
      if (list1 != null) {
        jobj1.put("cjstatus", list1.getString("cjstatus"));
        jobj1.put("doorstatus", list1.getString("doorstatus"));
      } else {
        jobj1.put("cjstatus", Integer.valueOf(0));
        jobj1.put("doorstatus", Integer.valueOf(0));
      } 
    } 
    return jobj1;
  }
  
  public List<JSONObject> selectListNoPage(String table, String usercode) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".selectListNoPage", usercode);
    return list;
  }
  
  public int getCountIndex(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization) throws Exception {
    Map<String, String> map = new HashMap<>();
    if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined"))
      map.put("querytype", visualstaff); 
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(searchValue)) {
      map.put("searchValue", searchValue);
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
    } 
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getCountIndex", map)).intValue();
    return count;
  }
  
  public int getCountYy(String table, String date) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("starttime", String.valueOf(date) + ConstUtil.HOUR_START);
    map.put("endtime", String.valueOf(date) + ConstUtil.HOUR_END);
    int nums = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getCountYy1", map)).intValue();
    return nums;
  }
  
  public int getCountYysm(String table, String date) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("starttime", String.valueOf(date) + ConstUtil.HOUR_START);
    map.put("endtime", String.valueOf(date) + ConstUtil.HOUR_END);
    int nums = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getCountYysm1", map)).intValue();
    return nums;
  }
  
  public Double getSml(String date) throws Exception {
    Double sml = Double.valueOf(0.0D);
    Map<String, String> map = new HashMap<>();
    map.put("startDate", String.valueOf(date) + ConstUtil.HOUR_START);
    map.put("endDate", String.valueOf(date) + ConstUtil.HOUR_END);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getSml", map);
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      double tmpSml = YZUtility.isNullorEmpty(rs.getString("sml")) ? 0.0D : rs.getDouble("sml");
      sml = Double.valueOf((new BigDecimal(tmpSml)).setScale(1, 5).doubleValue());
    } 
    return sml;
  }
  
  public int getCountJd(Map<String, String> map) throws Exception {
    int nums = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getCountJd", map)).intValue();
    return nums;
  }
  
  public int getCountYy(Map<String, String> map) throws Exception {
    int nums = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getCountYy2", map)).intValue();
    return nums;
  }
  
  public int getCountYysm(Map<String, String> map) throws Exception {
    int nums = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getCountYysm", map)).intValue();
    return nums;
  }
  
  public String getYysr(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getYysr", map);
    String yysr = "0";
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
      BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0.00" : rs.getString("yjj"));
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
      BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
      paymoney = paymoney.subtract(yjj).subtract(zs).subtract(djq).subtract(integral);
      yysr = KqdsBigDecimal.round(paymoney, 2).toString();
    } 
    return yysr;
  }
  
  public String getYysrYjj(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getYysrYjj", map);
    String yysr = "0.00";
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("cmoney")) ? "0.00" : rs.getString("cmoney"));
      yysr = KqdsBigDecimal.round(paymoney, 2).toString();
    } 
    return yysr;
  }
  
  public int getCountCj(Map<String, String> map) throws Exception {
    int nums = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".getCountCj", map)).intValue();
    return nums;
  }
  
  public void updateIsdeleteById(String seqId, int isDelete) throws Exception {
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("seqId", seqId);
    dataMap.put("isdelete", Integer.valueOf(isDelete));
    this.dao.update(String.valueOf(TableNameUtil.KQDS_NET_ORDER) + ".updateIsdeleteById", dataMap);
  }
}
