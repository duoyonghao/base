package com.kqds.service.base.visit;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.base.VisitDataCount;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.UserPrivUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_VisitLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectList(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT) + ".selectList", map);
    return list;
  }
  
  public JSONObject selectList(BootStrapPage bp, String table, Map<String, String> map) throws Exception {
    if (map.get("sortName") != null) {
      if (((String)map.get("sortName")).equals("status"))
        map.put("sortName", "t.status"); 
      if (((String)map.get("sortName")).equals("visittime"))
        map.put("sortName", "t.visittime"); 
      if (((String)map.get("sortName")).equals("visitorname"))
        map.put("sortName", "per1.user_name"); 
      if (((String)map.get("sortName")).equals("usercode"))
        map.put("sortName", "t.usercode"); 
      if (((String)map.get("sortName")).equals("telphone"))
        map.put("sortName", "t.telphone"); 
      if (((String)map.get("sortName")).equals("sex"))
        map.put("sortName", "t.sex"); 
      if (((String)map.get("sortName")).equals("username"))
        map.put("sortName", "t.username"); 
      if (((String)map.get("sortName")).equals("hfflname"))
        map.put("sortName", "kcit1.dict_name"); 
      if (((String)map.get("sortName")).equals("hfyd"))
        map.put("sortName", "t.hfyd"); 
      if (((String)map.get("sortName")).equals("postpersonname"))
        map.put("sortName", "per2.user_name"); 
      if (((String)map.get("sortName")).equals("hfresult"))
        map.put("sortName", "t.hfresult"); 
      if (((String)map.get("sortName")).equals("finishtime"))
        map.put("sortName", "t.finishtime"); 
      if (((String)map.get("sortName")).equals("mydname"))
        map.put("sortName", "kcit2.dict_name"); 
    } 
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT) + ".selectList1", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectListMyToday(String table, String visualstaff, String organization, BootStrapPage bp, Map<String, String> map) throws Exception {
    JSONObject json = new JSONObject();
    if (!YZUtility.isNullorEmpty(visualstaff))
      map.put("visualstaff", visualstaff); 
    if (map.get("sortName") != null) {
      if (((String)map.get("sortName")).equals("status"))
        map.put("sortName", "t.status"); 
      if (((String)map.get("sortName")).equals("visittime"))
        map.put("sortName", "t.visittime"); 
      if (((String)map.get("sortName")).equals("visitorname"))
        map.put("sortName", "per1.user_name"); 
      if (((String)map.get("sortName")).equals("usercode"))
        map.put("sortName", "t.usercode"); 
      if (((String)map.get("sortName")).equals("hfflname"))
        map.put("sortName", "kcit1.dict_name"); 
      if (((String)map.get("sortName")).equals("hfyd"))
        map.put("sortName", "t.hfyd"); 
      if (((String)map.get("sortName")).equals("postpersonname"))
        map.put("sortName", "per2.user_name"); 
      if (((String)map.get("sortName")).equals("hfresult"))
        map.put("sortName", "t.hfresult"); 
      if (((String)map.get("sortName")).equals("mydname"))
        map.put("sortName", "kcit2.dict_name"); 
    } 
    map.put("organization", organization);
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT) + ".selectListMyToday", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectList4returnVisit(String table, BootStrapPage bp, Map<String, String> map, String visualstaff, String organization, JSONObject json, String flag) throws Exception {
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(visualstaff))
      map.put("visualstaff", visualstaff); 
    if (map.containsKey("phonenumber1")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("phonenumber1")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("phonenumber1")));
    } 
    if (!YZUtility.isNullorEmpty(flag))
      map.put("flag", flag); 
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    if (map.get("sortName") != null) {
      String sortName = map.get("sortName");
      if (sortName.equals("username"))
        map.put("sortName", "u.username"); 
      if (sortName.equals("sex"))
        map.put("sortName", "u.sex"); 
      if (sortName.equals("phonenumber1"))
        map.put("sortName", "u.phonenumber1"); 
      if (sortName.equals("phonenumber2"))
        map.put("sortName", "u.phonenumber2"); 
      if (sortName.equals("memberno"))
        map.put("sortName", "(select count(1) from KQDS_Member where usercode =v.usercode ) "); 
      if (sortName.equals("status"))
        map.put("sortName", "v.status"); 
      if (sortName.equals("visittime"))
        map.put("sortName", "v.visittime"); 
      if (sortName.equals("myd"))
        map.put("sortName", "okdi1.dict_name"); 
      if (sortName.equals("visitdeptname"))
        map.put("sortName", "dept2.DEPT_NAME"); 
      if (sortName.equals("visitor1"))
        map.put("sortName", "ps.user_name"); 
      if (sortName.equals("hffl1"))
        map.put("sortName", "v.hffl"); 
      if (sortName.equals("hfyd"))
        map.put("sortName", "v.hfyd"); 
      if (sortName.equals("hfresult"))
        map.put("sortName", "v.hfresult"); 
      if (sortName.equals("finishtime"))
        map.put("sortName", "v.finishtime"); 
      if (sortName.equals("usercode"))
        map.put("sortName", "v.usercode"); 
      if (sortName.equals("jdr"))
        map.put("sortName", "ps2.user_name"); 
    } 
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT) + ".selectList4returnVisit", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    if (YZUtility.isNullorEmpty(flag)) {
      StringBuilder str = new StringBuilder();
      StringBuilder usercodes = new StringBuilder();
      for (int i = 0; i < list.size(); i++) {
        String doorstatus = ((JSONObject)list.get(i)).getString("doorstatus");
        if (ConstUtil.DOOR_STATUS_1.equals(doorstatus)) {
          doorstatus = ConstUtil.DOOR_STATUS_1_DESC;
        } else {
          doorstatus = ConstUtil.DOOR_STATUS_0_DESC;
        } 
        ((JSONObject)list.get(i)).put("doorstatus", doorstatus);
        String status = ((JSONObject)list.get(i)).getString("status");
        if ("0".equals(status)) {
          status = "未回访";
        } else {
          status = "已回访";
        } 
        ((JSONObject)list.get(i)).put("status", status);
        if (i == list.size() - 1) {
          str.append("'" + ((JSONObject)list.get(i)).getString("seq_id") + "'");
          usercodes.append("'" + ((JSONObject)list.get(i)).getString("usercode") + "'");
        } else {
          str.append("'" + ((JSONObject)list.get(i)).getString("seq_id") + "',");
          usercodes.append("'" + ((JSONObject)list.get(i)).getString("usercode") + "',");
        } 
        ((JSONObject)list.get(i)).put("myd", "");
        ((JSONObject)list.get(i)).put("hffl1", "");
        ((JSONObject)list.get(i)).put("visitdeptname", "");
        ((JSONObject)list.get(i)).put("visitor1", "");
      } 
      if (usercodes.length() > 0) {
        map.put("usercode", usercodes.toString());
        List<JSONObject> membernoList = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT) + ".selectMembernoByUsercode", map);
        if (membernoList.size() > 0)
          for (JSONObject jsonObject : list) {
            for (JSONObject jsonObject1 : membernoList) {
              if (jsonObject.getString("usercode").equals(jsonObject1.getString("usercode"))) {
                if (!jsonObject1.getString("num").equals("0")) {
                  jsonObject.put("memberno", "是");
                  continue;
                } 
                jsonObject.put("memberno", "否");
              } 
            } 
          }  
      } 
      if (str.length() > 0) {
        map.put("seqid", str.toString());
        List<JSONObject> nameList = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT) + ".selectNameBySeqid", map);
        if (nameList.size() > 0)
          for (JSONObject jsonObject : list) {
            for (JSONObject jsonObject1 : nameList) {
              if (jsonObject.getString("seq_id").equals(jsonObject1.getString("vseqid"))) {
                if (jsonObject1.getString("myd") != null)
                  jsonObject.put("myd", jsonObject1.getString("myd")); 
                if (jsonObject1.getString("hffl1") != null)
                  jsonObject.put("hffl1", jsonObject1.getString("hffl1")); 
                if (jsonObject1.getString("visitdeptname") != null)
                  jsonObject.put("visitdeptname", jsonObject1.getString("visitdeptname")); 
                if (jsonObject1.getString("visitor1") != null)
                  jsonObject.put("visitor1", jsonObject1.getString("visitor1")); 
              } 
            } 
          }  
      } 
    } else {
      for (int i = 0; i < list.size(); i++) {
        String memberno = ((JSONObject)list.get(i)).getString("memberno");
        if (!"0".equals(memberno)) {
          memberno = "是";
        } else {
          memberno = "否";
        } 
        ((JSONObject)list.get(i)).put("memberno", memberno);
        String doorstatus = ((JSONObject)list.get(i)).getString("doorstatus");
        if (ConstUtil.DOOR_STATUS_1.equals(doorstatus)) {
          doorstatus = ConstUtil.DOOR_STATUS_1_DESC;
        } else {
          doorstatus = ConstUtil.DOOR_STATUS_0_DESC;
        } 
        ((JSONObject)list.get(i)).put("doorstatus", doorstatus);
        String status = ((JSONObject)list.get(i)).getString("status");
        if ("0".equals(status)) {
          status = "未回访";
        } else {
          status = "已回访";
        } 
        ((JSONObject)list.get(i)).put("status", status);
      } 
    } 
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<VisitDataCount> selectCountByQuery(String table, Map<String, String> map, String visualstaff, String organization) throws Exception {
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(visualstaff))
      map.put("visualstaff", visualstaff); 
    if (map.containsKey("phonenumber1")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("phonenumber1")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("phonenumber1")));
    } 
    List<JSONObject> jsonList = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT) + ".selectCountByQuery", map);
    List<VisitDataCount> list = new ArrayList<>();
    for (JSONObject json : jsonList) {
      VisitDataCount d = new VisitDataCount();
      d.setName(json.getString("dict_name"));
      d.setCount(json.getString("flcount"));
      d.setHffl(json.getString("hffl"));
      list.add(d);
    } 
    return list;
  }
  
  public int getCountByQuery(String table, Map<String, String> map, String visualstaff, String organization) throws Exception {
    int sum = 0;
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(visualstaff))
      map.put("visualstaff", visualstaff); 
    if (map.containsKey("phonenumber1")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("phonenumber1")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("phonenumber1")));
    } 
    sum = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_VISIT) + ".getCountByQuery", map)).intValue();
    return sum;
  }
  
  public int getCountIndex(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization) throws Exception {
    Map<String, String> map = new HashMap<>();
    int count = 0;
    String currDate = YZUtility.getDateStr(new Date());
    if (person.getUserPriv().equals(UserPrivUtil.ADMIN_SEQ_ID))
      visualstaff = ""; 
    if (!YZUtility.isNullorEmpty(visualstaff) && (
      YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")))
      map.put("visualstaff", visualstaff); 
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    map.put("visittime", currDate);
    if (!YZUtility.isNullorEmpty(searchValue)) {
      map.put("searchValue", searchValue);
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
    } 
    count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_VISIT) + ".getCountIndex", map)).intValue();
    return count;
  }
  
  public void visitTimeOut() throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_VISIT) + ".visitTimeOut", SQLUtil.getCurrDate());
    if (list != null && list.size() > 0) {
      Map<String, String> map = new HashMap<>();
      for (JSONObject visit : list) {
        map.put("visitor", visit.getString("visitor"));
        map.put("usercode", visit.getString("usercode"));
        this.dao.delete(String.valueOf(TableNameUtil.KQDS_VISIT) + ".deletepush", map);
        PushUtil.saveTx4VisitTimeOut(visit);
      } 
    } 
  }
  
  public int deleteBySeqId(String seqId, HttpServletRequest request) throws Exception {
    return ((Integer)this.dao.delete(String.valueOf(TableNameUtil.KQDS_VISIT) + ".deletevisit", seqId)).intValue();
  }
  
  public void saveList(List<KqdsVisit> visitList) throws Exception {
    this.dao.batchUpdate(String.valueOf(TableNameUtil.KQDS_VISIT) + ".saveList", visitList);
  }
}
