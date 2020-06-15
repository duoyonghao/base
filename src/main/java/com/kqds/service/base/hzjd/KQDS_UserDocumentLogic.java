package com.kqds.service.base.hzjd;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.area.service.IAreaService;
import com.hudh.area.service.ICityService;
import com.hudh.area.service.IProviceService;
import com.hudh.area.service.IStreetService;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsChangeKefu;
import com.kqds.entity.base.KqdsChangeWd;
import com.kqds.entity.base.KqdsJdrchange;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.KqdsUserdocumentMergeRecord;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.label.KQDS_hz_LabellPatientLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.base.member.KQDS_MemberLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_UserDocumentLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private KQDS_MemberLogic memberLogic;
  @Autowired
  private UserdocumentMergeRecordLogic mergeLogic;
  @Autowired
  private IProviceService iProviceService;
  @Autowired
  private ICityService iCityService;
  @Autowired
  private IAreaService iAreaService;
  @Autowired
  private IStreetService isService;
  @Autowired
  private YZDictLogic yzDictlogic;
  @Autowired
  private KQDS_CostOrderLogic costOrderLogic;
  @Autowired
  private KQDS_hz_LabellPatientLogic labellPatientLogic;
  @Autowired
  private KQDS_hz_labelLogic labelLogic;
  @Autowired
  private KQDS_REGLogic logic;
  @Autowired
  private YZDictLogic dictLogic;
  
  public String getBlcode(String blcode_start)
    throws Exception
  {
    int num = 0;
    int start = Integer.parseInt(blcode_start.trim());
    
    int maxblcode = getMaxBlcode(TableNameUtil.KQDS_USERDOCUMENT);
    if (maxblcode == 0) {
      num = start;
    } else if (maxblcode < start) {
      num = start;
    } else {
      num = maxblcode;
    }
    num++;
    return String.valueOf(num);
  }
  
  public int getMaxBlcode(String table)
    throws Exception
  {
    int maxblcode = 0;
    String blcode = "";
    JSONObject json = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getmaxblcode", blcode);
    if ((json != null) && (YZUtility.isNotNullOrEmpty(json.getString("maxblcode")))) {
      maxblcode = json.getInt("maxblcode");
    }
    return maxblcode;
  }
  
  public KqdsUserdocument getSingUserByPhoneNumber(String phonenumber)
    throws Exception
  {
    phonenumber = YZAuthenticator.encryKqdsPhonenumber(phonenumber);
    KqdsUserdocument user = (KqdsUserdocument)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getSingUserByPhoneNumber", phonenumber);
    return user;
  }
  
  public JSONObject getSingUserNameAndCodeByPhoneNumber(String phonenumber)
    throws Exception
  {
    phonenumber = YZAuthenticator.encryKqdsPhonenumber(phonenumber);
    JSONObject json = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getSingUserNameAndCodeByPhoneNumber", phonenumber);
    return json;
  }
  
  public List<JSONObject> selectNoPymList(Connection conn)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectNoPymList", null);
    return list;
  }
  
  public void setUserDocAskPerson(HttpServletRequest request, String usercode, String askperson)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    List<KqdsUserdocument> userlist = (List)this.dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
    if ((userlist == null) || (userlist.size() == 0)) {
      throw new Exception("此患者不存在，患者编号为：" + usercode);
    }
    if (userlist.size() > 1) {
      throw new Exception("数据异常，同一个编号对应多个患者，患者编号为：" + usercode);
    }
    KqdsUserdocument user = (KqdsUserdocument)userlist.get(0);
    if (user.getIsdelete().intValue() != 0) {
      throw new Exception("该患者档案数据状态异常，患者编号为：" + usercode);
    }
    if (YZUtility.isNullorEmpty(askperson)) {
      throw new Exception("咨询人员seqId值为空");
    }
    YZPerson ask = (YZPerson)this.dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, askperson);
    if (ask == null) {
      throw new Exception("咨询人员不存在，seqId值为：" + askperson);
    }
    if (YZUtility.isNullorEmpty(user.getAskperson()))
    {
      StringBuffer loggerMsg = new StringBuffer();
      loggerMsg.append("### 设置患者档案表的咨询人员");
      loggerMsg.append("### 操作人：").append(person.getUserId());
      loggerMsg.append("### 操作时间：").append(YZUtility.getCurDateTimeStr());
      loggerMsg.append("### 设定咨询人：").append(ask.getUserId());
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.SET_ASKPERSON, BcjlUtil.KQDS_USERDOCUMENT, loggerMsg.toString(), user.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
      
      user.setAskperson(askperson);
      this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
    }
  }
  
  public KqdsUserdocument user1ToUser2(KqdsUserdocument user1, KqdsUserdocument user2)
    throws Exception
  {
    Field[] fields = user1.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; i++)
    {
      Field field = fields[i];
      String fieldName = field.getName();
      String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
      String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
      if ((!fieldName.equals("seqId")) && (!fieldName.equals("usercode")) && (!fieldName.equals("username")))
      {
        Class tCls = user1.getClass();
        Method getMethod = tCls.getMethod(getMethodName, new Class[0]);
        Object user1value = getMethod.invoke(user1, new Object[0]);
        
        Class tCls2 = user2.getClass();
        Method getMethod2 = tCls2.getMethod(getMethodName, new Class[0]);
        Object user2value = getMethod2.invoke(user2, new Object[0]);
        
        Method setMethod2 = tCls2.getMethod(setMethodName, new Class[] { field.getType() });
        if ((fieldName.equals("totalpay")) || (fieldName.equals("integral")))
        {
          BigDecimal totalpay1 = new BigDecimal(user1value.toString());
          BigDecimal totalpay2 = new BigDecimal(user2value.toString());
          setMethod2.invoke(user2, new Object[] { KqdsBigDecimal.add(totalpay1, totalpay2) });
        }
        else if ((user1value != null) && (user2value == null))
        {
          setMethod2.invoke(user2, new Object[] { user1value });
        }
      }
    }
    if (YZUtility.isNullorEmpty(user2.getPhonenumber2())) {
      user2.setPhonenumber2(user1.getPhonenumber1());
    }
    if (1 == user1.getDoorstatus().intValue()) {
      user2.setDoorstatus(user1.getDoorstatus());
    }
    if ((user1.getIscreateLclj() != null) && (!user1.getIscreateLclj().equals("")) && (user1.getIscreateLclj().equals("1")))
    {
      mergeMessage(user1.getUsercode(), user2.getUsercode());
      user2.setIscreateLclj("1");
    }
    return user2;
  }
  
  public KqdsUserdocument getSingleUserByUsercode(String usercode)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("isdelete", "0");
    List<KqdsUserdocument> list = (List)this.dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
    if ((list == null) || (list.size() == 0)) {
      throw new Exception("数据异常，患者不存在");
    }
    if ((list != null) && (list.size() > 1)) {
      throw new Exception("数据异常，一个编号对应多个患者");
    }
    return (KqdsUserdocument)list.get(0);
  }
  
  public JSONObject getBaseUserInfoByUsercode(String userCode)
    throws Exception
  {
    int count = countByUserCode(userCode);
    if (count > 1) {
      throw new Exception("数据异常，一个编号对应多个患者");
    }
    List<JSONObject> mlist = this.memberLogic.getMemberListByUserCode(userCode);
    BigDecimal money = BigDecimal.ZERO;
    BigDecimal givemoney = BigDecimal.ZERO;
    int discount = 100;
    for (JSONObject mobj : mlist)
    {
      String tmpMoney = mobj.getString("money");
      if (!YZUtility.isNullorEmpty(tmpMoney)) {
        money = money.add(new BigDecimal(tmpMoney));
      }
      String tmpGiveMoney = mobj.getString("givemoney");
      if (!YZUtility.isNullorEmpty(tmpGiveMoney)) {
        givemoney = givemoney.add(new BigDecimal(tmpGiveMoney));
      }
      String discountStr = mobj.getString("discount");
      String discountdate = mobj.getString("discountdate");
      boolean flag = false;
      if (YZUtility.isNullorEmpty(discountdate))
      {
        flag = true;
      }
      else
      {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (new Date().getTime() <= sdf.parse(discountdate + " 23:59:59").getTime()) {
          flag = true;
        }
      }
      if ((Integer.parseInt(discountStr) < discount) && (flag)) {
        discount = Integer.parseInt(discountStr);
      }
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getBaseUserInfoByUsercode", userCode);
    if (list.size() == 0) {
      throw new Exception("患者不存在，编号为：" + userCode);
    }
    JSONObject userinfo = (JSONObject)list.get(0);
    


    int introducerNum = findIntroducerNum(userCode);
    userinfo.put("introducerNum", Integer.valueOf(introducerNum));
    if (userinfo.get("hobby") != null)
    {
      Object ah = userinfo.get("hobby");
      String hobby = ah.toString();
      String[] hobbyArray = hobby.split(";");
      String hobbyName = "";
      for (int i = 0; i < hobbyArray.length; i++)
      {
        JSONObject json = this.yzDictlogic.findDictByDictCode(hobbyArray[i]);
        if (json == null) {
          break;
        }
        hobbyName = hobbyName + json.get("dictName").toString() + ";";
      }
      userinfo.replace("hobby", hobbyName);
    }
    String activity = userinfo.getString("activity");
    if (activity != null)
    {
      JSONObject json = this.yzDictlogic.findDictByDictCode(activity);
      if (json != null) {
        userinfo.replace("activity", json.getString("dictName"));
      }
    }
    Object province = userinfo.get("province");
    String provinceCode = province.toString();
    Object city = userinfo.get("city");
    String cityCode = city.toString();
    Object area = userinfo.get("area");
    String areaCode = area.toString();
    Object town = userinfo.get("town");
    String townCode = town.toString();
    if (provinceCode.length() == 6)
    {
      JSONObject provice = this.iProviceService.findProviceByProviceCode(provinceCode);
      Object provinceName = provice.get("provinceName");
      userinfo.put("provinceName", provinceName);
    }
    if (cityCode.length() == 6)
    {
      JSONObject provice = this.iCityService.findCityByCityCode(cityCode);
      Object cityName = provice.get("cityName");
      userinfo.put("cityName", cityName);
    }
    if (areaCode.length() == 6)
    {
      JSONObject provice = this.iAreaService.findAreaByAreaCode(areaCode);
      Object areaName = provice.get("areaName");
      userinfo.put("areaName", areaName);
    }
    if (townCode.length() == 9)
    {
      JSONObject provice = this.isService.findStreetByTownCode(townCode);
      Object townName = provice.get("streetName");
      userinfo.put("townName", townName);
    }
    userinfo.put("money", KqdsBigDecimal.round(money, 2).toString());
    userinfo.put("givemoney", KqdsBigDecimal.round(givemoney, 2).toString());
    userinfo.put("discount", Integer.valueOf(discount));
    List<kqdsHzLabellabeAndPatient> findForList = (List)this.dao.findForList(TableNameUtil.KQDS_Hz_LabellabeAndPatient + ".findLabelList", userCode);
    userinfo.put("labelList", findForList);
    return userinfo;
  }
  
  public int countByUserCode(String userCode)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".countByUserCode", userCode)).intValue();
    return count;
  }
  
  public String selectDoctorByusercode(String table, String usercode)
    throws Exception
  {
    String doctor = "";
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectDoctorByusercode", usercode);
    if ((list != null) && (list.size() > 0)) {
      doctor = ((JSONObject)list.get(0)).getString("doctor");
    }
    return doctor;
  }
  
  public String selectusercodeByusername(String table, String username)
    throws Exception
  {
    String usercode = "";
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectusercodeByusername", username);
    if ((list != null) && (list.size() > 0)) {
      usercode = ((JSONObject)list.get(0)).getString("usercode");
    }
    return usercode;
  }
  
  public List<JSONObject> getMaxUserCode(String table, String organization)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getMaxUserCode", organization);
    return list;
  }
  
  public List<JSONObject> selectWithNopage(String table, Map<String, String> map, String typechoose, String visualstaff, YZPerson person)
    throws Exception
  {
    if (map.containsKey("PhoneNumber1"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("PhoneNumber1", (String)map.get("PhoneNumber1")));
      map.put("p2", YZAuthenticator.phonenumberLike("PhoneNumber2", (String)map.get("PhoneNumber1")));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopage", map);
    for (JSONObject jsonObject : list)
    {
      String usercode = jsonObject.getString("usercode");
      JSONObject json = this.costOrderLogic.findCostOrderByUsercode(usercode);
      if (json != null)
      {
        jsonObject.put("totalcost", json.getString("totalcost"));
        jsonObject.put("doctorname", json.getString("doctorname"));
        jsonObject.put("askpersonname", json.getString("askpersonname"));
        jsonObject.put("repairdoctorname", json.getString("repairdoctorname"));
      }
    }
    return list;
  }
  
  public List<JSONObject> selectWithNopageReg(String table, Map<String, String> map, String typechoose, String visualstaff, YZPerson person)
    throws Exception
  {
    if (map.containsKey("PhoneNumber1"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("PhoneNumber1", (String)map.get("PhoneNumber1")));
      map.put("p2", YZAuthenticator.phonenumberLike("PhoneNumber2", (String)map.get("PhoneNumber1")));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopageReg", map);
    return list;
  }
  
  public List<JSONObject> selectWithNopageGh(String table, Map<String, String> map)
    throws Exception
  {
    if (map.containsKey("PhoneNumber1"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("PhoneNumber1")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("PhoneNumber1")));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopageGh", map);
    return list;
  }
  
  public JSONObject selectWithNopageGhLike(String table, Map<String, String> map, BootStrapPage bp)
    throws Exception
  {
    if (map.containsKey("PhoneNumber1"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("PhoneNumber1")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("PhoneNumber1")));
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopageGhLike", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectWithNopageGhPermission(String table, Map<String, String> map)
    throws Exception
  {
    if (map.containsKey("PhoneNumber1"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("PhoneNumber1")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("PhoneNumber1")));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopageGhPermission", map);
    return list;
  }
  
  public List<JSONObject> selectByUserCodes(String table, String usercodes)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectByUserCodes", usercodes);
    return list;
  }
  
  public JSONObject selectWithNopage2(BootStrapPage bp, String table, String visualstaff, Map<String, String> map, String organization, JSONObject json, String flag)
    throws Exception
  {
    map.put("organization", organization);
    if (map.containsKey("username"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("username")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("username")));
    }
    if (map.containsKey("usercodes")) {
      map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    }
    if (!YZUtility.isNullorEmpty(visualstaff)) {
      map.put("visualstaff", visualstaff);
    }
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("createtime")) {
        map.put("sortName", "u.createtime");
      }
      if (((String)map.get("sortName")).equals("lasttime")) {
        map.put("sortName", "(select top 1 CreateTime from KQDS_REG reg where reg.usercode=u.UserCode ORDER BY CreateTime desc)");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "u.usercode");
      }
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "u.username");
      }
      if (((String)map.get("sortName")).equals("sex")) {
        map.put("sortName", "u.sex");
      }
      if (((String)map.get("sortName")).equals("age")) {
        map.put("sortName", "u.age");
      }
      if (((String)map.get("sortName")).equals("askperson")) {
        map.put("sortName", "per2.user_name");
      }
      if (((String)map.get("sortName")).equals("doctor")) {
        map.put("sortName", "per1.user_name");
      }
      if (((String)map.get("sortName")).equals("phonenumber1")) {
        map.put("sortName", "u.phonenumber1");
      }
      if (((String)map.get("sortName")).equals("devchannelname")) {
        map.put("sortName", "okdi1.dict_name");
      }
      if (((String)map.get("sortName")).equals("nexttypename")) {
        map.put("sortName", "hztype.dict_name");
      }
      if (((String)map.get("sortName")).equals("createuser")) {
        map.put("sortName", "per4.user_name");
      }
      if (((String)map.get("sortName")).equals("ywhf")) {
        map.put("sortName", "(select count(v.usercode) from KQDS_Visit v where v.usercode=u.UserCode)");
      }
      if (((String)map.get("sortName")).equals("provincename")) {
        map.put("sortName", "a1.area_name");
      }
      if (((String)map.get("sortName")).equals("cityname")) {
        map.put("sortName", "a2.area_name");
      }
      if (((String)map.get("sortName")).equals("townname")) {
        map.put("sortName", "a3.area_name");
      }
      if (((String)map.get("sortName")).equals("phonenumber2")) {
        map.put("sortName", "u.phonenumber2");
      }
    }
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    String proviceName;
    String towname;
    if ((flag != null) && (flag.equals("exportTable")))
    {
      List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopage3", map);
      for (JSONObject job : list)
      {
        String ywhf = job.getString("ywhf");
        if ("0".equals(ywhf)) {
          ywhf = "无回访";
        } else {
          ywhf = "有回访";
        }
        job.put("ywhf", ywhf);
        String doorstatus = job.getString("doorstatus");
        if ("1".equals(doorstatus)) {
          doorstatus = "已上门";
        } else {
          doorstatus = "未上门";
        }
        job.put("doorstatusname", doorstatus);
        proviceName = job.getString("provincename");
        if (proviceName.equals("")) {
          job.replace("provincename", job.getString("province_name"));
        }
        String citname = job.getString("cityname");
        if (citname.equals("")) {
          job.replace("cityname", job.getString("city_name"));
        }
        towname = job.getString("townname");
        if (towname.equals("")) {
          job.replace("townname", job.getString("area_name"));
        }
      }
      PageInfo<JSONObject> pageInfo = new PageInfo(list);
      JSONObject jobj = new JSONObject();
      jobj.put("total", Long.valueOf(pageInfo.getTotal()));
      jobj.put("rows", list);
      return jobj;
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithNopage2", map);
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < list.size(); i++)
    {
      String ywhf = ((JSONObject)list.get(i)).getString("ywhf");
      if ("0".equals(ywhf)) {
        ywhf = "无回访";
      } else {
        ywhf = "有回访";
      }
      ((JSONObject)list.get(i)).put("ywhf", ywhf);
      String doorstatus = ((JSONObject)list.get(i)).getString("doorstatus");
      if ("1".equals(doorstatus)) {
        doorstatus = "已上门";
      } else {
        doorstatus = "未上门";
      }
      ((JSONObject)list.get(i)).put("doorstatusname", doorstatus);
      if (i == list.size() - 1) {
        str.append("'" + ((JSONObject)list.get(i)).getString("usercode") + "'");
      } else {
        str.append("'" + ((JSONObject)list.get(i)).getString("usercode") + "',");
      }
      ((JSONObject)list.get(i)).put("streetName", "");
      ((JSONObject)list.get(i)).put("provincename", "");
      ((JSONObject)list.get(i)).put("cityname", "");
      ((JSONObject)list.get(i)).put("townname", "");
      ((JSONObject)list.get(i)).put("askperson", "");
      ((JSONObject)list.get(i)).put("doctor", "");
      ((JSONObject)list.get(i)).put("devchannelname", "");
      ((JSONObject)list.get(i)).put("nexttypename", "");
      ((JSONObject)list.get(i)).put("createuser", "");
    }
    if (str.length() > 0)
    {
      Object map1 = new HashMap();
      ((Map)map1).put("usercode", str.toString());
      List<JSONObject> areaList = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".findAreaByUsercode", map1);
      JSONObject jsonObject1;
      if (areaList.size() > 0) {
        for (proviceName = list.iterator(); proviceName.hasNext(); towname.hasNext())
        {
          JSONObject jsonObject = (JSONObject)proviceName.next();
          towname = areaList.iterator(); continue;jsonObject1 = (JSONObject)towname.next();
          if (jsonObject.getString("usercode").equals(jsonObject1.getString("usercode")))
          {
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("province_name"))) {
              jsonObject.put("provincename", jsonObject1.getString("province_name"));
            } else {
              jsonObject.put("provincename", jsonObject1.getString("provincename"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("city_name"))) {
              jsonObject.put("cityname", jsonObject1.getString("city_name"));
            } else {
              jsonObject.put("cityname", jsonObject1.getString("cityname"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("area_name"))) {
              jsonObject.put("townname", jsonObject1.getString("area_name"));
            } else {
              jsonObject.put("townname", jsonObject1.getString("townname"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("street_name"))) {
              jsonObject.put("streetName", jsonObject1.getString("street_name"));
            }
          }
        }
      }
      List<JSONObject> nameList = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".findXxcxNameByUsercode", map1);
      if (nameList.size() > 0)
      {
        Iterator localIterator2;
        for (jsonObject1 = list.iterator(); jsonObject1.hasNext(); localIterator2.hasNext())
        {
          JSONObject jsonObject = (JSONObject)jsonObject1.next();
          localIterator2 = nameList.iterator(); continue;JSONObject jsonObject1 = (JSONObject)localIterator2.next();
          if (jsonObject.getString("usercode").equals(jsonObject1.getString("usercode")))
          {
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("createuser"))) {
              jsonObject.put("createuser", jsonObject1.getString("createuser"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("askperson"))) {
              jsonObject.put("askperson", jsonObject1.getString("askperson"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("doctor"))) {
              jsonObject.put("doctor", jsonObject1.getString("doctor"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("devchannelname"))) {
              jsonObject.put("devchannelname", jsonObject1.getString("devchannelname"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("nexttypename"))) {
              jsonObject.put("nexttypename", jsonObject1.getString("nexttypename"));
            }
          }
        }
      }
    }
    Object pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(((PageInfo)pageInfo).getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public int checkIdcardnoBySeqIdAndIdcardno(Map<String, String> map, String table)
    throws Exception
  {
    int num = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".checkIdcardnoBySeqIdAndIdcardno", map)).intValue();
    return num;
  }
  
  public int checkphonenumber(String seqId, Map<String, String> map, String table)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(seqId)) {
      map.put("seqId", seqId);
    }
    if (map.containsKey("phonenumber1")) {
      map.put("phonenumber1", YZAuthenticator.encryKqdsPhonenumber((String)map.get("phonenumber1")));
    }
    if (map.containsKey("phonenumber2")) {
      map.put("phonenumber2", YZAuthenticator.encryKqdsPhonenumber((String)map.get("phonenumber2")));
    }
    int num = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".checkphonenumber", map)).intValue();
    return num;
  }
  
  public List<KqdsUserdocument> selectUserdocumentByPhonenumber(String phoneNumber)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("phoneNumber", phoneNumber);
    
    List<KqdsUserdocument> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectUserdocumentByPhonenumber", dataMap);
    return list;
  }
  
  public int checkBlcode(String seqId, String blcode, String table)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    if (!YZUtility.isNullorEmpty(seqId)) {
      map.put("seqId", seqId);
    }
    if (!YZUtility.isNullorEmpty(blcode)) {
      map.put("blcode", blcode);
    }
    int num = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".checkBlcode", map)).intValue();
    return num;
  }
  
  public int updateUserName(String usercode, String oldname, String newname, String table, int type, HttpServletRequest request)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("usercode", usercode);
    json.put("username", newname);
    json.put("tablename", table);
    int num = 0;
    if (table.equals(TableNameUtil.KQDS_MEMBER)) {
      num = ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateFlagBySeqIds1", json)).intValue();
    } else if (table.equals("KQDS_MEMBER_RECORD_SZR")) {
      num = ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateFlagBySeqIds2", json)).intValue();
    } else if (type == 1) {
      num = ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateFlagBySeqIds3", json)).intValue();
    } else {
      num = ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateFlagBySeqIds4", json)).intValue();
    }
    return num;
  }
  
  public int updateUser(String usercode1, String usercode2, String username2, String table, int type, HttpServletRequest request)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("usercode2", usercode2);
    json.put("usercode1", usercode1);
    json.put("username2", username2);
    json.put("tablename", table);
    int num = 0;
    if ((table.equals(TableNameUtil.KQDS_COSTORDER_DETAIL)) || (table.equals(TableNameUtil.KQDS_MEDICALRECORD)) || (table.equals(TableNameUtil.KQDS_OUTPROCESSING_SHEET)) || 
      (table.equals(TableNameUtil.KQDS_REFUND_DETAIL)) || (table.equals(TableNameUtil.KQDS_TOOTH_DOC)) || (table.equals(TableNameUtil.KQDS_BCJL))) {
      num = ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateUser1", json)).intValue();
    } else if (type == 1) {
      num = ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateUser2", json)).intValue();
    }
    return num;
  }
  
  public String getSfuser()
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSfuser", null);
    String bqs = "";
    for (JSONObject rs : list) {
      bqs = bqs + rs.getString("usercode") + ",";
    }
    if (bqs.endsWith(",")) {
      bqs = bqs.substring(0, bqs.length() - 1);
    }
    return bqs;
  }
  
  public JSONObject getusercodeBYsjhm(String sjhm)
    throws Exception
  {
    JSONObject jobj = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getusercodeBYsjhm", sjhm);
    return jobj;
  }
  
  public String getSsje(String usercode)
    throws Exception
  {
    String ssje = "0";
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSsje", usercode);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
      BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
      
      paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
      ssje = KqdsBigDecimal.round(paymoney, 2).toString();
    }
    return ssje;
  }
  
  public List<JSONObject> getSsjeReg(String regno)
    throws Exception
  {
    String ssje = "0";
    Map<String, String> map = new HashMap();
    map.put("regno", regno);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSsjeReg", map);
    if ((list != null) && (list.size() > 0)) {
      for (JSONObject rs : list)
      {
        BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
        BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
        BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
        BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
        
        paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
        ssje = KqdsBigDecimal.round(paymoney, 2).toString();
        rs.put("ssje", ssje);
      }
    }
    return list;
  }
  
  public BigDecimal getSsjeOne(String costno)
    throws Exception
  {
    BigDecimal ssmoney = BigDecimal.ZERO;
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSsjeOne", costno);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
      BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
      
      paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
      ssmoney = KqdsBigDecimal.round(paymoney, 2);
    }
    return ssmoney;
  }
  
  public BigDecimal getSsjeOneAddIntegral(String costno)
    throws Exception
  {
    BigDecimal ssmoney = BigDecimal.ZERO;
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getSsjeOne", costno);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney"));
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
      
      paymoney = paymoney.subtract(zs).subtract(djq);
      ssmoney = KqdsBigDecimal.round(paymoney, 2);
    }
    return ssmoney;
  }
  
  public BigDecimal getJFjeOne(String costno)
    throws Exception
  {
    BigDecimal integral = BigDecimal.ZERO;
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getJFjeOne", costno);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
      
      integral = KqdsBigDecimal.round(integral, 2);
    }
    return integral;
  }
  
  public JSONObject userManger4Wdzx(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization, BootStrapPage bp)
    throws Exception
  {
    if ((map.containsKey("yytime1")) || (map.containsKey("yytime2")) || (map.containsKey("xiangmu"))) {
      map.put("netsql", "left join KQDS_NET_ORDER n ");
    } else {
      map.put("netsql", 
        "left join (select a.* from KQDS_NET_ORDER a,(select usercode,max(createtime) createtime  from KQDS_NET_ORDER group by usercode) b where a.usercode=b.usercode and a.createtime=b.createtime ) n");
    }
    if (map.containsKey("queryinput"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("queryinput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("queryinput")));
    }
    if (map.containsKey("yewu"))
    {
      String jdr = ((String)map.get("yewu")).toString();
      if (YZUtility.isStrInArrayEach("'" + jdr + "'", visualstaff)) {
        map.put("yewu2", (String)map.get("yewu"));
      } else {
        map.put("yewu3", (String)map.get("yewu"));
      }
    }
    else if (!map.containsKey("yewu"))
    {
      map.put("yewu4", visualstaff);
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    if (map.get("sortName") != null)
    {
      String sortName = (String)map.get("sortName");
      if (sortName.equals("zdoorstatus")) {
        map.put("sortName", "u.doorstatus");
      }
      if (sortName.equals("doorstatus")) {
        map.put("sortName", "n.doorstatus");
      }
      if (sortName.equals("cjstatus")) {
        map.put("sortName", "n.cjstatus");
      }
      if (sortName.equals("username")) {
        map.put("sortName", "u.username");
      }
      if (sortName.equals("phonenumber1")) {
        map.put("sortName", "u.phonenumber1");
      }
      if (sortName.equals("createuser")) {
        map.put("sortName", "per1.user_name");
      }
      if (sortName.equals("askitem")) {
        map.put("sortName", "kcit4.dict_name");
      }
      if (sortName.equals("createtime")) {
        map.put("sortName", "u.createtime");
      }
      if (sortName.equals("ordertime")) {
        map.put("sortName", "n.ordertime");
      }
      if (sortName.equals("guidetime")) {
        map.put("sortName", "n.guidetime");
      }
      if (sortName.equals("devchannel")) {
        map.put("sortName", "kcit3.dict_name");
      }
      if (sortName.equals("nexttype")) {
        map.put("sortName", "hzly.dict_name");
      }
      if (sortName.equals("provincename")) {
        map.put("sortName", "a1.area_name");
      }
      if (sortName.equals("cityname")) {
        map.put("sortName", "a2.area_name");
      }
      if (sortName.equals("townname")) {
        map.put("sortName", "a3.area_name");
      }
      if (sortName.equals("age")) {
        map.put("sortName", "u.age");
      }
      if (sortName.equals("important")) {
        map.put("sortName", "u.important");
      }
      if (sortName.equals("xueli")) {
        map.put("sortName", "u.xueli");
      }
      if (sortName.equals("askperson")) {
        map.put("sortName", "per2.user_name");
      }
      if (sortName.equals("accepttype")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (sortName.equals("accepttool")) {
        map.put("sortName", "kcit2.dict_name");
      }
      if (sortName.equals("askcontent")) {
        map.put("sortName", "n.askcontent");
      }
      if (sortName.equals("remark")) {
        map.put("sortName", "u.remark");
      }
      if (sortName.equals("glr")) {
        map.put("sortName", "per3.user_name");
      }
      if (sortName.equals("glrremark")) {
        map.put("sortName", "u.glrremark");
      }
      if (sortName.equals("usercode")) {
        map.put("sortName", "u.usercode");
      }
    }
    List<JSONObject> returnlist1 = null;
    if ("a361daeb-be3c-4dab-a63d-3d718177c81e".equals(person.getSeqId())) {
      returnlist1 = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4WdzxNetNum", map);
    } else {
      returnlist1 = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4WdzxNum", map);
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    
    List<JSONObject> returnlist = null;
    if ("a361daeb-be3c-4dab-a63d-3d718177c81e".equals(person.getSeqId())) {
      returnlist = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4WdzxNet", map);
    } else {
      returnlist = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4Wdzx", map);
    }
    for (JSONObject job : returnlist)
    {
      String proviceName = job.getString("provincename");
      if (proviceName.equals("")) {
        job.replace("provincename", job.getString("province_name"));
      }
      String citname = job.getString("cityname");
      if (citname.equals("")) {
        job.replace("cityname", job.getString("city_name"));
      }
      String towname = job.getString("townname");
      if (towname.equals("")) {
        job.replace("townname", job.getString("area_name"));
      }
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
    PageInfo<JSONObject> pageInfo = new PageInfo(returnlist);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", returnlist);
    jobj1.put("nums", returnlist1);
    return jobj1;
  }
  
  public JSONObject userManger4Kfzx(String table, YZPerson person, Map<String, String> map, String ywhf, String visualstaff, String organization, BootStrapPage bp, JSONObject json, Double time1, Double time2, String labelID)
    throws Exception
  {
    if ((map.containsKey("yytime1")) || (map.containsKey("yytime2"))) {
      map.put("netsql", "left join KQDS_REG r ");
    } else {
      map.put("netsql", 
        "left join (select a.* from KQDS_REG a,(select usercode,max(createtime) createtime  from KQDS_REG group by usercode) b where a.usercode=b.usercode and a.createtime=b.createtime ) r");
    }
    if (map.containsKey("queryinput"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("queryinput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("queryinput")));
    }
    if (map.containsKey("yewu"))
    {
      String jdr = ((String)map.get("yewu")).toString();
      if (YZUtility.isStrInArrayEach("'" + jdr + "'", visualstaff)) {
        map.put("yewu2", (String)map.get("yewu"));
      } else {
        map.put("yewu3", (String)map.get("yewu"));
      }
    }
    else if (!map.containsKey("yewu"))
    {
      map.put("yewu4", visualstaff);
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    if (!YZUtility.isNullorEmpty(ywhf)) {
      if (ywhf.equals("0")) {
        map.put("nohf", ywhf);
      } else {
        map.put("ishf", ywhf);
      }
    }
    if (map.get("sortName") != null)
    {
      String sortName = (String)map.get("sortName");
      if (sortName.equals("zdoorstatus")) {
        map.put("sortName", "u.doorstatus");
      }
      if (sortName.equals("doorstatus")) {
        map.put("sortName", "n.doorstatus");
      }
      if (sortName.equals("cjstatus")) {
        map.put("sortName", "n.cjstatus");
      }
      if (sortName.equals("username")) {
        map.put("sortName", "u.username");
      }
      if (sortName.equals("phonenumber1")) {
        map.put("sortName", "u.phonenumber1");
      }
      if (sortName.equals("createuser")) {
        map.put("sortName", "per1.user_name");
      }
      if (sortName.equals("askitem")) {
        map.put("sortName", "kcit4.dict_name");
      }
      if (sortName.equals("createtime")) {
        map.put("sortName", "u.createtime");
      }
      if (sortName.equals("ordertime")) {
        map.put("sortName", "n.ordertime");
      }
      if (sortName.equals("guidetime")) {
        map.put("sortName", "n.guidetime");
      }
      if (sortName.equals("devchannel")) {
        map.put("sortName", "kcit3.dict_name");
      }
      if (sortName.equals("nexttype")) {
        map.put("sortName", "hzly.dict_name");
      }
      if (sortName.equals("provincename")) {
        map.put("sortName", "a1.area_name");
      }
      if (sortName.equals("cityname")) {
        map.put("sortName", "a2.area_name");
      }
      if (sortName.equals("townname")) {
        map.put("sortName", "a3.area_name");
      }
      if (sortName.equals("age")) {
        map.put("sortName", "u.age");
      }
      if (sortName.equals("important")) {
        map.put("sortName", "u.important");
      }
      if (sortName.equals("xueli")) {
        map.put("sortName", "u.xueli");
      }
      if (sortName.equals("askperson")) {
        map.put("sortName", "per2.user_name");
      }
      if (sortName.equals("accepttype")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (sortName.equals("accepttool")) {
        map.put("sortName", "kcit2.dict_name");
      }
      if (sortName.equals("askcontent")) {
        map.put("sortName", "n.askcontent");
      }
      if (sortName.equals("remark")) {
        map.put("sortName", "u.remark");
      }
      if (sortName.equals("glr")) {
        map.put("sortName", "per3.user_name");
      }
      if (sortName.equals("glrremark")) {
        map.put("sortName", "u.glrremark");
      }
      if (sortName.equals("usercode")) {
        map.put("sortName", "u.usercode");
      }
      if (sortName.equals("ywhf")) {
        map.put("sortName", "(select count(v.usercode) from KQDS_Visit v where v.usercode=u.UserCode)");
      }
    }
    List<String> usercodeAll = new ArrayList();
    List<JSONObject> returnlist = new ArrayList();
    String mm;
    Set<String> c;
    if (((time1.doubleValue() > 0.0D) && (time2.doubleValue() > 0.0D) && (!labelID.equals(""))) || ((time1.doubleValue() > 0.0D) && (time2.doubleValue() > 0.0D) && (labelID.equals(""))))
    {
      CacheUtil.openCache();
      if (organization.equals("HUDH"))
      {
        Set<String> c = CacheUtil.getZSetByScore("label:key", time1.doubleValue(), time2.doubleValue());
        List<String> labelAll = new ArrayList();
        for (String string : c) {
          if (!labelID.equals(""))
          {
            int i = 0;
            String[] id = labelID.split(",");
            for (String str : id) {
              if (string.contains(str)) {
                i++;
              }
            }
            if (i == id.length) {
              labelAll.add(string);
            }
          }
          else
          {
            labelAll.add(string);
          }
        }
        for (String string : labelAll)
        {
          String mm = (String)CacheUtil.getMapKey("label:value", string);
          if ((mm != null) && (!mm.equals(""))) {
            usercodeAll.add(mm);
          }
        }
      }
      else
      {
        Set<String> c = CacheUtil.getZSetByScore(organization + ":label:key", time1.doubleValue(), time2.doubleValue());
        List<String> labelAll = new ArrayList();
        for (String string : c) {
          if (!labelID.equals(""))
          {
            int i = 0;
            String[] id = labelID.split(",");
            for (String str : id) {
              if (string.contains(str)) {
                i++;
              }
            }
            if (i == id.length) {
              labelAll.add(string);
            }
          }
          else
          {
            labelAll.add(string);
          }
        }
        for (String string : labelAll)
        {
          mm = (String)CacheUtil.getMapKey(organization + ":label:value", string);
          if ((mm != null) && (!mm.equals(""))) {
            usercodeAll.add(mm);
          }
        }
      }
      CacheUtil.close();
    }
    else if (!labelID.equals(""))
    {
      CacheUtil.openCache();
      
      List<String> labelAll = new ArrayList();
      String[] id = labelID.split(",");
      if (organization.equals("HUDH"))
      {
        Set<String> c = CacheUtil.keys("labelQuery:*" + id[0] + "*");
        if (c.size() > 0) {
          for (String string : c)
          {
            int i = 0;
            for (String str : id) {
              if (string.contains(str)) {
                i++;
              }
            }
            if (i == id.length) {
              labelAll.add(string);
            }
          }
        }
        for (String string : labelAll)
        {
          String mm = (String)CacheUtil.getMapKey("label:value", string.substring(11, string.length()));
          if ((mm != null) && (!mm.equals(""))) {
            usercodeAll.add(mm);
          }
        }
      }
      else
      {
        c = CacheUtil.keys(organization + ":labelQuery:*" + id[0] + "*");
        if (c.size() > 0) {
          for (String string : c)
          {
            int i = 0;
            for (String str : id) {
              if (string.contains(str)) {
                i++;
              }
            }
            if (i == id.length) {
              labelAll.add(string);
            }
          }
        }
        for (String string : labelAll)
        {
          String mm = (String)CacheUtil.getMapKey(organization + ":label:value", string.substring(16, string.length()));
          if ((mm != null) && (!mm.equals(""))) {
            usercodeAll.add(mm);
          }
        }
      }
      CacheUtil.close();
    }
    if ((usercodeAll.size() > 0) && (usercodeAll != null))
    {
      StringBuffer usercodeAlls = new StringBuffer();
      for (String string : usercodeAll) {
        if (usercodeAlls.length() == 0) {
          usercodeAlls.append("'" + string + "'");
        } else {
          usercodeAlls.append(",'" + string + "'");
        }
      }
      if (usercodeAlls != null) {
        map.put("usercodealls", usercodeAlls.toString());
      }
      if ((time1.doubleValue() == 0.0D) && (time2.doubleValue() == 0.0D))
      {
        map.put("jdtime1", "");
        map.put("jdtime2", "");
      }
      List<JSONObject> returnlist1 = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4KfzxNumByUsercodes", map);
      
      PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
      returnlist = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4KfzxByUsercodes", map);
      PageInfo<JSONObject> pageInfo = new PageInfo(returnlist);
      
      JSONObject jobj1 = new JSONObject();
      








































      jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
      jobj1.put("rows", returnlist);
      jobj1.put("nums", returnlist1);
      return jobj1;
    }
    if (((usercodeAll.size() == 0) && (labelID != null) && (!labelID.equals("")) && (returnlist.size() == 0)) || ((usercodeAll.size() > 0) && (labelID != null) && (!labelID.equals("")) && (returnlist.size() == 0)))
    {
      JSONObject jobj1 = new JSONObject();
      List<JSONObject> returnlist1 = new ArrayList();
      JSONObject jobj = new JSONObject();
      jobj.put("doorstatus", Integer.valueOf(0));
      jobj.put("cjstatus", Integer.valueOf(0));
      returnlist1.add(jobj);
      jobj1.put("total", Integer.valueOf(0));
      jobj1.put("rows", returnlist);
      jobj1.put("nums", returnlist1);
      return jobj1;
    }
    List<JSONObject> returnlist1 = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4KfzxNum", map);
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    returnlist = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4Kfzx", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(returnlist);
    JSONObject jobj1 = new JSONObject();
    















































































    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", returnlist);
    jobj1.put("nums", returnlist1);
    return jobj1;
  }
  
  public JSONObject userManger4Yxzx(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization, BootStrapPage bp)
    throws Exception
  {
    if ((map.containsKey("yytime1")) || (map.containsKey("yytime2")) || (map.containsKey("xiangmu"))) {
      map.put("netsql", "left join KQDS_NET_ORDER n ");
    } else {
      map.put("netsql", 
        "left join (select a.* from KQDS_NET_ORDER a,(select usercode,max(createtime) createtime  from KQDS_NET_ORDER group by usercode) b where a.usercode=b.usercode and a.createtime=b.createtime ) n");
    }
    if (map.containsKey("queryinput"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("queryinput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("queryinput")));
    }
    if (map.containsKey("yewu"))
    {
      String jdr = ((String)map.get("yewu")).toString();
      if (YZUtility.isStrInArrayEach("'" + jdr + "'", visualstaff)) {
        map.put("yewu2", (String)map.get("yewu"));
      } else {
        map.put("yewu3", (String)map.get("yewu"));
      }
    }
    else if (!map.containsKey("yewu"))
    {
      map.put("yewu4", visualstaff);
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    if (map.get("sortName") != null)
    {
      String sortName = (String)map.get("sortName");
      if (sortName.equals("zdoorstatus")) {
        map.put("sortName", "u.doorstatus");
      }
      if (sortName.equals("doorstatus")) {
        map.put("sortName", "n.doorstatus");
      }
      if (sortName.equals("cjstatus")) {
        map.put("sortName", "n.cjstatus");
      }
      if (sortName.equals("usercode")) {
        map.put("sortName", "u.usercode");
      }
      if (sortName.equals("username")) {
        map.put("sortName", "u.username");
      }
      if (sortName.equals("phonenumber1")) {
        map.put("sortName", "u.phonenumber1");
      }
      if (sortName.equals("createuser")) {
        map.put("sortName", "per1.user_name");
      }
      if (sortName.equals("askitem")) {
        map.put("sortName", "kcit4.dict_name");
      }
      if (sortName.equals("createtime")) {
        map.put("sortName", "u.createtime");
      }
      if (sortName.equals("ordertime")) {
        map.put("sortName", "n.ordertime");
      }
      if (sortName.equals("guidetime")) {
        map.put("sortName", "n.guidetime");
      }
      if (sortName.equals("devchannel")) {
        map.put("sortName", "kcit3.dict_name");
      }
      if (sortName.equals("nexttype")) {
        map.put("sortName", "hzly.dict_name");
      }
      if (sortName.equals("provincename")) {
        map.put("sortName", "a1.area_name");
      }
      if (sortName.equals("cityname")) {
        map.put("sortName", "a2.area_name");
      }
      if (sortName.equals("townname")) {
        map.put("sortName", "a3.area_name");
      }
      if (sortName.equals("age")) {
        map.put("sortName", "u.age");
      }
      if (sortName.equals("important")) {
        map.put("sortName", "u.important");
      }
      if (sortName.equals("xueli")) {
        map.put("sortName", "u.xueli");
      }
      if (sortName.equals("askperson")) {
        map.put("sortName", "per2.user_name");
      }
      if (sortName.equals("accepttype")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (sortName.equals("accepttool")) {
        map.put("sortName", "kcit2.dict_name");
      }
      if (sortName.equals("askcontent")) {
        map.put("sortName", "n.askcontent");
      }
      if (sortName.equals("remark")) {
        map.put("sortName", "u.remark");
      }
      if (sortName.equals("glr")) {
        map.put("sortName", "per3.user_name");
      }
      if (sortName.equals("glrremark")) {
        map.put("sortName", "u.glrremark");
      }
      if (sortName.equals("usercode")) {
        map.put("sortName", "u.usercode");
      }
    }
    List<JSONObject> returnlist1 = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4YxzxNum", map);
    
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> returnlist = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".userManger4Yxzx", map);
    for (JSONObject job : returnlist)
    {
      String proviceName = job.getString("provincename");
      if (proviceName.equals("")) {
        job.replace("provincename", job.getString("province_name"));
      }
      String citname = job.getString("cityname");
      if (citname.equals("")) {
        job.replace("cityname", job.getString("city_name"));
      }
      String towname = job.getString("townname");
      if (towname.equals("")) {
        job.replace("townname", job.getString("area_name"));
      }
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
    PageInfo<JSONObject> pageInfo = new PageInfo(returnlist);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", returnlist);
    jobj1.put("nums", returnlist1);
    return jobj1;
  }
  
  public List<JSONObject> jingQueChaXun4Net(String queryinput, String queryInputName)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    if (!YZUtility.isNullorEmpty(queryinput))
    {
      map.put("queryinput", queryinput);
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", queryinput));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", queryinput));
    }
    if (!YZUtility.isNullorEmpty(queryInputName)) {
      map.put("queryInputName", queryInputName);
    }
    List<JSONObject> returnlist = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".jingQueChaXun4Net", map);
    for (JSONObject job : returnlist)
    {
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
    return returnlist;
  }
  
  public JSONObject selectWithPage(BootStrapPage bp, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectListByBirth(int month, int day, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("month", month);
    map.put("day", day);
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".selectListByBirth", map);
    return list;
  }
  
  public JSONObject getUserInfoByUserCode(String usercode)
    throws Exception
  {
    JSONObject result = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getUserInfoByUserCode", usercode);
    return result;
  }
  
  public int deleteuser(String userCodes)
    throws Exception
  {
    int count = ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateusers", userCodes)).intValue();
    return count;
  }
  
  public int checkuser(String userCode)
    throws Exception
  {
    int count = ((Integer)this.dao.update(TableNameUtil.KQDS_MEMBER_RECORD + ".checkuser", userCode)).intValue();
    return count;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void setKeFu(KqdsChangeKefu wd, KqdsUserdocument user)
    throws Exception
  {
    this.dao.saveSingleUUID(TableNameUtil.KQDS_CHANGE_KEFU, wd);
    this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void setWd(KqdsChangeWd wd, KqdsUserdocument user)
    throws Exception
  {
    this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_CHANGE_WD, wd);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void updateUserDoc(KqdsUserdocument dp, KqdsUserdocument en, KqdsNetOrder netorder, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, dp);
    if (!dp.getUsername().equals(en.getUsername()))
    {
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_COSTORDER, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_REFUND, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_EXTENSION, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_GIVEITEM_GIVERECORD, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_GIVEITEM_USERECORD, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_HOSPITAL_ORDER, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_IMAGE_DATA, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_MEMBER_RECORD, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), "KQDS_MEMBER_RECORD_SZR", 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_MEMBER, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_NET_ORDER, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_NET_ORDER_RECORD, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_PAYCOST, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_RECEIVEINFO, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_CHANGE_DOCTOR, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_CHANGE_RECEIVE, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_CHANGE_WD, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_REG, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_VISIT, 1, request);
      updateUserName(dp.getUsercode(), en.getUsername(), dp.getUsername(), TableNameUtil.KQDS_PARTICIPANT, 2, request);
    }
    if (ConstUtil.USER_TYPE_1 == dp.getType().intValue())
    {
      String wdseqId = request.getParameter("wdseqId");
      if (!YZUtility.isNullorEmpty(wdseqId))
      {
        String accepttype = request.getParameter("accepttype");
        String accepttool = request.getParameter("accepttool");
        String askitem = request.getParameter("askitem");
        String askcontent = request.getParameter("askcontent");
        String ordertime = request.getParameter("ordertime");
        KqdsNetOrder wd = (KqdsNetOrder)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_NET_ORDER, wdseqId);
        wd.setAccepttype(accepttype);
        wd.setAccepttool(accepttool);
        wd.setAskitem(askitem);
        wd.setAskcontent(askcontent);
        wd.setOrdertime(ordertime);
        this.dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, wd);
        
        Map map = new HashMap();
        map.put("usercode", wd.getUsercode());
        List<KqdsNetOrder> orderList = (List)this.dao.loadList(TableNameUtil.KQDS_NET_ORDER, map);
        for (KqdsNetOrder order : orderList)
        {
          order.setAccepttype(accepttype);
          order.setAccepttool(accepttool);
          this.dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, order);
        }
      }
      else
      {
        Map map = new HashMap();
        map.put("usercode", dp.getUsercode());
        List<KqdsNetOrder> orderList = (List)this.dao.loadList(TableNameUtil.KQDS_NET_ORDER, map);
        if ((orderList == null) || (orderList.isEmpty()))
        {
          netorder.setSeqId(YZUtility.getUUID());
          netorder.setCreatetime(YZUtility.getCurDateTimeStr());
          netorder.setCreateuser(person.getSeqId());
          netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request));
          netorder.setDoorstatus(Integer.valueOf(0));
          netorder.setCjstatus(Integer.valueOf(0));
          netorder.setUsercode(dp.getUsercode());
          netorder.setUsername(dp.getUsername());
          netorder.setAcceptdate(dp.getCreatetime());
          netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request));
          this.dao.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
        }
      }
    }
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void insertUserDoc(KqdsUserdocument dp, KqdsNetOrder netorder, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    if (ConstUtil.USER_TYPE_1 == dp.getType().intValue())
    {
      netorder.setSeqId(YZUtility.getUUID());
      netorder.setCreatetime(YZUtility.getCurDateTimeStr());
      netorder.setCreateuser(person.getSeqId());
      netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request));
      netorder.setDoorstatus(Integer.valueOf(0));
      netorder.setCjstatus(Integer.valueOf(0));
      netorder.setUsercode(dp.getUsercode());
      netorder.setUsername(dp.getUsername());
      netorder.setAcceptdate(dp.getCreatetime());
      netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request));
      this.dao.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
    }
    else
    {
      dp.setOrganization(ChainUtil.getCurrentOrganization(request));
    }
    this.dao.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, dp);
    
    BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_USERDOCUMENT, dp, dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
    

    this.memberLogic.addMember4CreateUserDocument(dp, person, request);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void hzhb(KqdsUserdocument user1, KqdsUserdocument user2, HttpServletRequest request)
    throws Exception
  {
    user2 = user1ToUser2(user1, user2);
    user1.setIsdelete(Integer.valueOf(1));
    this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user2);
    this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user1);
    


    KqdsUserdocumentMergeRecord dp = new KqdsUserdocumentMergeRecord();
    dp.setUsercode_one(user1.getUsercode());
    dp.setUsercode_two(user2.getUsercode());
    dp.setUsername_one(user1.getUsername());
    dp.setUsername_two(user2.getUsername());
    dp.setUserdocument_one_crateuser(user1.getCreateuser());
    dp.setUserdocument_two_crateuser(user2.getCreateuser());
    this.mergeLogic.saveMergeRecord(dp, request);
    


    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_BCJL, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_LLTJ_DETAIL, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_COSTORDER, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_COSTORDER_DETAIL, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_EXTENSION, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_GIVEITEM_GIVERECORD, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_GIVEITEM_USERECORD, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_HOSPITAL_ORDER, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_IMAGE_DATA, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_MEDICALRECORD, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_MEMBER_RECORD, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_MEMBER, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_NET_ORDER, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_NET_ORDER_RECORD, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_PAYCOST, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_RECEIVEINFO, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_CHANGE_DOCTOR, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_CHANGE_RECEIVE, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_CHANGE_WD, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_REFUND, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_REFUND_DETAIL, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_REG, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_VISIT, 1, request);
    updateUser(user1.getUsercode(), user2.getUsercode(), user2.getUsername(), TableNameUtil.KQDS_TOOTH_DOC, 1, request);
  }
  
  public void isCreateLclj(String seqId, @Param("iscreateLclj") String iscreateLclj)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("seqId", seqId);
    map.put("iscreateLclj", iscreateLclj);
    this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".isCreateLclj", map);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void setInputtingPerson(KqdsJdrchange wd, KqdsUserdocument user)
    throws Exception
  {
    this.dao.saveSingleUUID(TableNameUtil.KQDS_CHANGE_JDR, wd);
    this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
  }
  
  public int findIntroducerNum(String usercode)
    throws Exception
  {
    int introducerNum = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".findIntroducerNum", usercode)).intValue();
    return introducerNum;
  }
  
  public void saveKpatient(kqdsHzLabellabeAndPatient kPatient)
    throws Exception
  {
    this.dao.save(TableNameUtil.KQDS_Hz_LabellabeAndPatient + ".saveKpatient", kPatient);
  }
  
  public void deleteLabel(String userCode)
    throws Exception
  {
    this.dao.delete(TableNameUtil.KQDS_Hz_LabellabeAndPatient + ".deleteLabel", userCode);
  }
  
  public void deleteLabelByUsercode(Map<String, String> map)
    throws Exception
  {
    this.dao.delete(TableNameUtil.KQDS_Hz_LabellabeAndPatient + ".deleteLabelByUsercode", map);
  }
  
  public String selectLabelByUsercode(Map<String, String> map)
    throws Exception
  {
    String threeid = (String)this.dao.findForObject(TableNameUtil.KQDS_Hz_LabellabeAndPatient + ".selectLabelByUsercode", map);
    return threeid;
  }
  
  public void mergeMessage(String blcode, String usercode)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode1", blcode);
    map.put("usercode2", usercode);
    this.dao.update(TableNameUtil.HUDH_LCLJ_ORDERTRACK + ".mergeMessage", map);
  }
  
  public List<JSONObject> findKqdsUserdocumentByUsercodes(String usercodes)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".findKqdsUserdocumentByUsercodes", usercodes);
    
    return list;
  }
  
  public List<JSONObject> findByUsercode(String usercode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".findByUsercode", usercode);
    return list;
  }
  
  public static List removeDuplicate(List list)
  {
    HashSet h = new HashSet(list);
    list.clear();
    list.addAll(h);
    return list;
  }
  
  public List findDevchannel(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".findDevchannel", map);
  }
  
  public JSONObject findDevchannelAll(Map<String, String> map)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".findDevchannelAll", map);
  }
  
  public JSONObject findConsumptionInterval(Map<String, String> map)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".findConsumptionInterval", map);
  }
  
  public JSONObject findLabel(String userCode)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("userCode", userCode);
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_Hz_LabellabeAndPatient + ".findLabel", map);
  }
  
  public String findOrganizationBySeqid(String seqid)
    throws Exception
  {
    return (String)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".findOrganizationBySeqid", seqid);
  }
  
  public int updateBlcode(Map<String, String> map)
    throws Exception
  {
    return ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateBlcode", map)).intValue();
  }
}
