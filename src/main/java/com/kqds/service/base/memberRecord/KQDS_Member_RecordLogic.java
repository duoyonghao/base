package com.kqds.service.base.memberRecord;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.SysParaUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Member_RecordLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectList(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectList", map);
    for (JSONObject job : list) {
      job.putAll(selectAllQm(job.getString("cardno"), job.getString("createtime"), "qmmoney", "qmgivemoney", "qmtotal"));
    }
    return list;
  }
  
  public List<JSONObject> selectListForCzjl(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectListForCzjl", map);
    for (JSONObject job : list) {
      job.putAll(selectAllQm(job.getString("cardno"), job.getString("createtime"), "qmmoney", "qmgivemoney", "qmtotal"));
    }
    return list;
  }
  
  public JSONObject selectListOrder(BootStrapPage bp, String table, Map<String, String> map, String visualstaff, String visualstaffwd)
    throws Exception
  {
    if (map.containsKey("queryinput"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("queryinput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("queryinput")));
    }
    if (map.containsKey("usercodes")) {
      map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    }
    map.put("visualstaff", visualstaff);
    map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
    int firstIndex = bp.getOffset();
    JSONObject jobj = new JSONObject();
    if (map.containsKey("itemname"))
    {
      jobj.put("total", Integer.valueOf(0));
      jobj.put("rows", new ArrayList());
      return jobj;
    }
    if (map.get("sortName1") != null)
    {
      int a = 0;
      if (((String)map.get("sortName1")).equals("cgivemoney"))
      {
        map.put("sortName1", "rec.cgivemoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("ctotal"))
      {
        map.put("sortName1", "rec.ctotal");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("remark"))
      {
        map.put("sortName1", "rec.content");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("developer"))
      {
        map.put("sortName1", "p4.user_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("sftime"))
      {
        map.put("sortName1", "rec.createtime");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("usercode"))
      {
        map.put("sortName1", "rec.usercode");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("blcode"))
      {
        map.put("sortName1", "u.blcode");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("username"))
      {
        map.put("sortName1", "rec.username");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("phonenumber1"))
      {
        map.put("sortName1", "u.phonenumber1");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("nextname"))
      {
        map.put("sortName1", "rec.type");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("realmoney"))
      {
        map.put("sortName1", "rec.cmoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("cmoney"))
      {
        map.put("sortName1", "rec.cmoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("payxj"))
      {
        map.put("sortName1", "rec.xjmoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("paybank"))
      {
        map.put("sortName1", "rec.yhkmoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("paywx"))
      {
        map.put("sortName1", "rec.wxmoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("payzfb"))
      {
        map.put("sortName1", "rec.zfbmoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("paymmd"))
      {
        map.put("sortName1", "rec.mmdmoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("paybdfq"))
      {
        map.put("sortName1", "rec.bdfqmoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("payother1"))
      {
        map.put("sortName1", "rec.qtmoney");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("recesort"))
      {
        map.put("sortName1", "kcit1.dict_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("regsort"))
      {
        map.put("sortName1", "kcit2.dict_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("faskperson"))
      {
        map.put("sortName1", "p2.user_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("askperson"))
      {
        map.put("sortName1", "p8.user_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("regdept"))
      {
        map.put("sortName1", "dept2.DEPT_NAME");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("doctor"))
      {
        map.put("sortName1", "p9.USER_NAME");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("devchannel"))
      {
        map.put("sortName1", "kcit3.dict_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("nexttype"))
      {
        map.put("sortName1", "hzly.dict_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("accepttype"))
      {
        map.put("sortName1", "kcit5.dict_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("accepttool"))
      {
        map.put("sortName1", "kcit6.dict_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("introducer"))
      {
        map.put("sortName1", "u2.username");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("jduser"))
      {
        map.put("sortName1", "p3.user_name");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("jddy"))
      {
        map.put("sortName1", "per7.USER_NAME");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("jdtime"))
      {
        map.put("sortName1", "u.createtime");
        a = 1;
      }
      if (((String)map.get("sortName1")).equals("sfuser"))
      {
        map.put("sortName1", "p5.user_name");
        a = 1;
      }
      if (a == 0) {
        map.put("sortName1", "");
      }
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectListOrder", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    for (JSONObject obj : list)
    {
      obj.put("classname", "预交金");
      obj.put("nextname", obj.getString("type"));
      obj.put("sftime", obj.getString("createtime"));
      obj.put("sfuser", obj.getString("sfr"));
      obj.put("regsort", obj.getString("regsort"));
      obj.put("recesort", obj.getString("recesort"));
      obj.put("kduser", "");
      obj.put("kdtime", "");
      obj.put("cjstatus", "");
      obj.put("nurse", "");
      obj.put("techperson", "");
      obj.put("remark", "");
      
      obj.put("developer", obj.getString("kfr"));
      obj.put("introducer", obj.getString("jsr"));
      obj.put("type", "");
      obj.put("remark", obj.getString("content"));
      
      obj.put("seqIdMember", obj.getString("seq_id"));
      obj.put("costno", "");
      obj.put("itemname", "");
      obj.put("itemno", "");
      obj.put("unit", "");
      obj.put("unitprice", "");
      obj.put("detailremark", "");
      obj.put("num", "");
      obj.put("discount", "");
      obj.put("realmoney", YZUtility.isNullorEmpty(obj.getString("cmoney")) ? "0.00" : new BigDecimal(obj.getString("cmoney")));
      obj.put("cmoney", YZUtility.isNullorEmpty(obj.getString("cmoney")) ? "0.00" : new BigDecimal(obj.getString("cmoney")));
      obj.put("cgivemoney", YZUtility.isNullorEmpty(obj.getString("cgivemoney")) ? "0.00" : new BigDecimal(obj.getString("cgivemoney")));
      obj.put("ctotal", YZUtility.isNullorEmpty(obj.getString("ctotal")) ? "0.00" : new BigDecimal(obj.getString("ctotal")));
      obj.put("subtotal", "");
      obj.put("arrearmoney", "");
      obj.put("ys", "");
      obj.put("paymoney", "");
      obj.put("voidmoney", "");
      obj.put("doctor", obj.getString("doctor"));
      obj.put("regdept", obj.getString("regdept"));
      obj.put("status", "");
      obj.put("regno", "");
      obj.put("paydjq", "");
      obj.put("payintegral", "");
      obj.put("payxj", YZUtility.isNullorEmpty(obj.getString("xjmoney")) ? "0.00" : new BigDecimal(obj.getString("xjmoney")));
      obj.put("payyjj", "");
      obj.put("paybank", YZUtility.isNullorEmpty(obj.getString("yhkmoney")) ? "0.00" : new BigDecimal(obj.getString("yhkmoney")));
      obj.put("payyb", "");
      obj.put("paywx", YZUtility.isNullorEmpty(obj.getString("wxmoney")) ? "0.00" : new BigDecimal(obj.getString("wxmoney")));
      obj.put("payzfb", YZUtility.isNullorEmpty(obj.getString("zfbmoney")) ? "0.00" : new BigDecimal(obj.getString("zfbmoney")));
      obj.put("paymmd", YZUtility.isNullorEmpty(obj.getString("mmdmoney")) ? "0.00" : new BigDecimal(obj.getString("mmdmoney")));
      obj.put("paybdfq", YZUtility.isNullorEmpty(obj.getString("bdfqmoney")) ? "0.00" : new BigDecimal(obj.getString("bdfqmoney")));
      obj.put("payother1", YZUtility.isNullorEmpty(obj.getString("qtmoney")) ? "0.00" : new BigDecimal(obj.getString("qtmoney")));
      obj.put("payother2", "");
      obj.put("payother3", "");
      obj.put("istsxm", "");
      obj.put("y2", "");
      obj.put("istk", "");
      obj.put("issplit", "");
    }
    if (firstIndex == 0) {
      if (pageInfo.getTotal() > 0L)
      {
        Map<String, String> wheremap = new HashMap();
        wheremap.putAll(map);
        wheremap.put("rec.cmoney", "cmoney");
        wheremap.put("rec.xjmoney", "xjmoney");
        wheremap.put("rec.yhkmoney", "yhkmoney");
        wheremap.put("rec.wxmoney", "wxmoney");
        wheremap.put("rec.zfbmoney", "zfbmoney");
        wheremap.put("rec.mmdmoney", "mmdmoney");
        wheremap.put("rec.bdfqmoney", "bdfqmoney");
        wheremap.put("rec.qtmoney", "qtmoney");
        
        JSONObject obj = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".getTotalNumFields", wheremap);
        
        jobj.put("realmoney", obj.getString("cmoney").toString());
        jobj.put("payxj", obj.getString("xjmoney").toString());
        jobj.put("paybank", obj.getString("yhkmoney").toString());
        jobj.put("payyb", "0.00");
        jobj.put("paywx", obj.getString("wxmoney").toString());
        jobj.put("payzfb", obj.getString("zfbmoney").toString());
        jobj.put("paymmd", obj.getString("mmdmoney").toString());
        jobj.put("paybdfq", obj.getString("bdfqmoney").toString());
        jobj.put("payother1", obj.getString("qtmoney").toString());
      }
    }
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  private JSONObject selectAllQm(String memberno, String endtime, String key1, String key2, String key3)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("memberno", memberno);
    if (!YZUtility.isNullorEmpty(endtime)) {
      map.put("endtime", endtime);
    }
    JSONObject json = new JSONObject();
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectAllQm", map);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject tmp = (JSONObject)list.get(0);
      BigDecimal cmoney = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("cmoney")) ? "0.00" : tmp.getString("cmoney"));
      BigDecimal zzmoney = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("zzmoney")) ? "0.00" : tmp.getString("zzmoney"));
      BigDecimal cgivemoney = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("cgivemoney")) ? "0.00" : tmp.getString("cgivemoney"));
      BigDecimal zzgivemoney = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("zzgivemoney")) ? "0.00" : tmp.getString("zzgivemoney"));
      BigDecimal ctotal = new BigDecimal(YZUtility.isNullorEmpty(tmp.getString("ctotal")) ? "0.00" : tmp.getString("ctotal"));
      
      json.put(key1, cmoney.add(zzmoney));
      json.put(key2, cgivemoney.add(zzgivemoney));
      json.put(key3, ctotal.add(zzmoney).add(zzgivemoney));
    }
    return json;
  }
  
  public List<JSONObject> selectListByType(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectListByType", map);
    for (JSONObject job : list)
    {
      float zzmoney = 0.0F;
      if (!YZUtility.isNullorEmpty(job.getString("zzmoney"))) {
        zzmoney = Float.parseFloat(job.getString("zzmoney"));
      }
      float zzgivemoney = 0.0F;
      if (!YZUtility.isNullorEmpty(job.getString("zzgivemoney"))) {
        zzgivemoney = Float.parseFloat(job.getString("zzgivemoney"));
      }
      job.put("zztotal", Float.valueOf(zzmoney + zzgivemoney));
    }
    return list;
  }
  
  public JSONObject printSfxm(String table, String seqId, HttpServletRequest request)
    throws Exception
  {
    JSONObject obj = new JSONObject();
    Map<String, String> map = new HashMap();
    map.put("seqId", seqId);
    map.put("tablename", table);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".printSfxm", map);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      if ((!YZUtility.isNullorEmpty(rs.getString("xj"))) && 
        (rs.getDouble("xj") != 0.0D)) {
        obj.put(SysParaUtil.getFkfsNameByMemberField(request, "xjmoney"), rs.getString("xj"));
      }
      if ((!YZUtility.isNullorEmpty(rs.getString("bank"))) && 
        (rs.getDouble("bank") != 0.0D)) {
        obj.put(SysParaUtil.getFkfsNameByMemberField(request, "yhkmoney"), rs.getString("bank"));
      }
      if ((!YZUtility.isNullorEmpty(rs.getString("wx"))) && 
        (rs.getDouble("wx") != 0.0D)) {
        obj.put(SysParaUtil.getFkfsNameByMemberField(request, "wxmoney"), rs.getString("wx"));
      }
      if ((!YZUtility.isNullorEmpty(rs.getString("zfb"))) && 
        (rs.getDouble("zfb") != 0.0D)) {
        obj.put(SysParaUtil.getFkfsNameByMemberField(request, "zfbmoney"), rs.getString("zfb"));
      }
      if ((!YZUtility.isNullorEmpty(rs.getString("mmd"))) && 
        (rs.getDouble("mmd") != 0.0D)) {
        obj.put(SysParaUtil.getFkfsNameByMemberField(request, "mmdmoney"), rs.getString("mmd"));
      }
      if ((!YZUtility.isNullorEmpty(rs.getString("bdfq"))) && 
        (rs.getDouble("bdfq") != 0.0D)) {
        obj.put(SysParaUtil.getFkfsNameByMemberField(request, "bdfqmoney"), rs.getString("bdfq"));
      }
      if ((!YZUtility.isNullorEmpty(rs.getString("other1"))) && 
        (rs.getDouble("other1") != 0.0D)) {
        obj.put(SysParaUtil.getFkfsNameByMemberField(request, "qtmoney"), rs.getString("other1"));
      }
    }
    return obj;
  }
  
  public int yjjTuidan(String cardno, String datetime)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("cardno", cardno);
    map.put("datetime", datetime);
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".yjjTuidan", map)).intValue();
    return count;
  }
  
  public List<JSONObject> findAcceptingGold(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".findAcceptingGold", map);
  }
}
