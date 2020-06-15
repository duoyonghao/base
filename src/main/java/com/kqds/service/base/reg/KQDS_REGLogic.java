package com.kqds.service.base.reg;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsJzqk;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hospitalOrder.KQDS_Hospital_OrderLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.jzmdType.KQDS_JzqkLogic;
import com.kqds.service.base.room.KQDS_RoomLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_REGLogic
  extends BaseLogic
{
  @Autowired
  private KQDS_RoomLogic roomlogic;
  @Autowired
  private DaoSupport dao;
  @Autowired
  private KQDS_UserDocumentLogic userlogic;
  @Autowired
  private KQDS_JzqkLogic jlogic;
  @Autowired
  private YZDictLogic dictLogic;
  @Autowired
  private KQDS_Hospital_OrderLogic hospitalOrderLogic;
  
  public int countCzReg(String usercode, String czIdStrs)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("recesort", czIdStrs);
    int num = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".countCzReg", map)).intValue();
    return num;
  }
  
  public List<JSONObject> getLastestRegInfo(String usercode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getLastestRegInfo", usercode);
    return list;
  }
  
  public List<JSONObject> getChuZhenIn24Hours(String usercode, HttpServletRequest request)
    throws Exception
  {
    String czSeqId = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID);
    Map<String, String> map = new HashMap();
    map.put("recesort", czSeqId);
    map.put("usercode", usercode);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getChuZhenIn24Hours", usercode);
    return list;
  }
  
  public void checkReceive4RegEdit(KqdsReg dp, HttpServletRequest request)
    throws Exception
  {
    Map<String, String> map1 = new HashMap();
    map1.put("regno", dp.getSeqId());
    List<KqdsReceiveinfo> list = (List)this.dao.loadList(TableNameUtil.KQDS_RECEIVEINFO, map1);
    if ((list != null) && (list.size() > 0))
    {
      KqdsReceiveinfo rece = (KqdsReceiveinfo)list.get(0);
      rece.setAskperson(dp.getAskperson());
      rece.setRecesort(dp.getRegsort());
      this.dao.updateSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, rece);
    }
  }
  
  public void checkJzqk4RegEdit(KqdsReg dp, KqdsReg regold, KqdsUserdocument userdoc, YZPerson person, HttpServletRequest request, String regsortname)
    throws Exception
  {
    Map<String, String> map1 = new HashMap();
    map1.put("regno", dp.getSeqId());
    map1.put("doctor", regold.getDoctor());
    if (YZUtility.isNullorEmpty(dp.getDoctor()))
    {
      this.dao.delete(TableNameUtil.KQDS_REG + ".deletebyregno", map1);
    }
    else
    {
      userdoc.setDoctor(dp.getDoctor());
      this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, userdoc);
      if (YZUtility.isNullorEmpty(regold.getDoctor()))
      {
        checkUpdateInsertJZQK(dp, person, ChainUtil.getCurrentOrganization(request), request);
      }
      else if (!dp.getDoctor().equals(regold.getDoctor()))
      {
        this.dao.delete(TableNameUtil.KQDS_REG + ".deletebyregno", map1);
        checkUpdateInsertJZQK(dp, person, ChainUtil.getCurrentOrganization(request), request);
      }
      PushUtil.saveTx4ModifyReg(dp, regsortname, request);
    }
  }
  
  public void checkHosOrder(KqdsReg dp, HttpServletRequest request)
    throws Exception
  {
    String orderno = request.getParameter("orderno");
    String nowdatestr = YZUtility.getDateStr(new Date());
    if (!YZUtility.isNullorEmpty(orderno))
    {
      KqdsHospitalOrder hos = (KqdsHospitalOrder)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, orderno);
      hos.setOrderstatus(Integer.valueOf(1));
      hos.setRegno(dp.getSeqId());
      hos.setRegdept(dp.getRegdept());
      hos.setOrdertime(dp.getCreatetime());
      this.dao.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, hos);
    }
    else
    {
      List<JSONObject> orderList = getTodayHosOrderByOrdertimeAndUsercode(nowdatestr, dp.getUsercode());
      for (JSONObject job : orderList)
      {
        KqdsHospitalOrder hos = (KqdsHospitalOrder)JSONObject.toBean(job, KqdsHospitalOrder.class);
        hos.setOrderstatus(Integer.valueOf(1));
        hos.setRegno(dp.getSeqId());
        hos.setRegdept(dp.getRegdept());
        hos.setOrdertime(dp.getCreatetime());
        this.dao.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, hos);
      }
    }
  }
  
  public void checkNetOrder(KqdsReg dp, HttpServletRequest request)
    throws Exception
  {
    String netorderid = request.getParameter("netorderid");
    String nowdatestr = YZUtility.getDateStr(new Date());
    




    KqdsNetOrder netOrder = null;
    
    List<KqdsNetOrder> orderList = getTodayNetOrderByOrdertimeAndUsercode(nowdatestr, dp.getUsercode());
    if ((orderList == null) || (orderList.size() == 0))
    {
      if (!YZUtility.isNullorEmpty(netorderid))
      {
        KqdsNetOrder netTmp = (KqdsNetOrder)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorderid);
        if (netTmp == null) {
          throw new Exception("网电预约记录不存在，预约主键为：" + netorderid);
        }
        if (netTmp.getDoorstatus().intValue() == 0) {
          netOrder = netTmp;
        }
      }
      if (netOrder == null)
      {
        orderList = getTodayNetOrderByOrdertimeAndUsercode("", dp.getUsercode());
        if ((orderList != null) && (orderList.size() > 0)) {
          netOrder = (KqdsNetOrder)orderList.get(0);
        }
      }
      if (netOrder != null)
      {
        netOrder.setDoorstatus(Integer.valueOf(1));
        netOrder.setRegdept(dp.getRegdept());
        netOrder.setOrderperson(dp.getDoctor());
        netOrder.setAskperson(dp.getAskperson());
        netOrder.setRegno(dp.getSeqId());
        netOrder.setDaoyi(dp.getCreateuser());
        netOrder.setGuidetime(dp.getCreatetime());
        this.dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, netOrder);
      }
    }
    else
    {
      for (KqdsNetOrder netorder2 : orderList)
      {
        netorder2.setDoorstatus(Integer.valueOf(1));
        netorder2.setRegdept(dp.getRegdept());
        netorder2.setOrderperson(dp.getDoctor());
        netorder2.setAskperson(dp.getAskperson());
        netorder2.setRegno(dp.getSeqId());
        netorder2.setDaoyi(dp.getCreateuser());
        netorder2.setGuidetime(dp.getCreatetime());
        this.dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder2);
      }
    }
  }
  
  public void deleteReceiveInfoByRegNo(String regno, HttpServletRequest request)
    throws Exception
  {
    Map<String, String> mapzx = new HashMap();
    mapzx.put("regno", regno);
    List<KqdsReceiveinfo> receiveList = (List)this.dao.loadList(TableNameUtil.KQDS_RECEIVEINFO, mapzx);
    for (KqdsReceiveinfo receive : receiveList)
    {
      String receive_seqId = receive.getSeqId();
      if (!YZUtility.isNullorEmpty(receive_seqId)) {
        this.dao.delete(TableNameUtil.KQDS_REG + ".deletereceive", receive_seqId);
      }
      this.dao.deleteSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, receive_seqId);
    }
  }
  
  public void checkUpdateInsertJZQK(KqdsReg dp, YZPerson person, String organization, HttpServletRequest request)
    throws Exception
  {
    List<JSONObject> list = this.jlogic.jzFz(dp.getUsercode(), dp.getDoctor());
    for (JSONObject job : list)
    {
      KqdsJzqk jzqk = (KqdsJzqk)JSONObject.toBean(job, KqdsJzqk.class);
      

      jzqk.setDzdata(dp.getCreatetime());
      jzqk.setDzregno(dp.getSeqId());
      this.dao.updateSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
    }
    KqdsJzqk jzqk = new KqdsJzqk();
    jzqk.setSeqId(YZUtility.getUUID());
    jzqk.setRegno(dp.getSeqId());
    jzqk.setDoctor(dp.getDoctor());
    jzqk.setCreatetime(YZUtility.getCurDateTimeStr());
    jzqk.setCreateuser(person.getSeqId());
    jzqk.setOrganization(organization);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
  }
  
  public KqdsReceiveinfo saveReceive(KqdsReg dp, KqdsUserdocument userdoc, String organization, HttpServletRequest request)
    throws Exception
  {
    KqdsReceiveinfo receive = new KqdsReceiveinfo();
    try
    {
      receive.setSeqId(YZUtility.getUUID());
      receive.setUsercode(dp.getUsercode());
      receive.setAskperson(dp.getAskperson());
      receive.setRegno(dp.getSeqId());
      receive.setUsername(userdoc.getUsername());
      receive.setDeptno(dp.getRegdept());
      receive.setRecesort(dp.getRegsort());
      receive.setCreatetime(YZUtility.getCurDateTimeStr());
      receive.setCreateuser(dp.getCreateuser());
      receive.setOrganization(organization);
      this.dao.saveSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, receive);
    }
    catch (Exception ex)
    {
      throw ex;
    }
    return receive;
  }
  
  public JSONObject selectDzlmenu(String table, YZPerson person, String sortName, String sortOrder, int status, String querytype, String searchValue, String visualstaff, String organization, HttpServletRequest request, BootStrapPage bp)
    throws Exception
  {
    String sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID);
    Map<String, String> map = new HashMap();
    if (((YZUtility.isNullorEmpty(querytype)) || (querytype.equals("null")) || (querytype.equals("undefined"))) && 
      (!YZUtility.isStrInArrayEach(person.getUserPriv(), sfpriv))) {
      map.put("querytype", visualstaff);
    }
    String recesort = request.getParameter("recesort");
    if (YZUtility.isNotNullOrEmpty(recesort)) {
      map.put("recesort", recesort);
    }
    map.put("status", status);
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(searchValue))
    {
      map.put("searchValue", searchValue);
      map.put("p1", YZAuthenticator.phonenumberLike("us.PhoneNumber1", (String)map.get("searchValue")));
      map.put("p2", YZAuthenticator.phonenumberLike("us.PhoneNumber2", (String)map.get("searchValue")));
    }
    if (!YZUtility.isNullorEmpty(sortName))
    {
      map.put("sortOrder", sortOrder);
      if (sortName.equals("createtime")) {
        map.put("sortName", "reg.createtime");
      }
      if (sortName.equals("recesortname")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (sortName.equals("regsortname")) {
        map.put("sortName", "kcit2.dict_name");
      }
      if (sortName.equals("usercode")) {
        map.put("sortName", "reg.usercode");
      }
      if (sortName.equals("username")) {
        map.put("sortName", "reg.username");
      }
      if (sortName.equals("iscreatelclj")) {
        map.put("sortName", "us.iscreateLclj");
      }
      if (sortName.equals("sex")) {
        map.put("sortName", "us.sex");
      }
      if (sortName.equals("age")) {
        map.put("sortName", "us.age");
      }
      if (sortName.equals("cjstatus")) {
        map.put("sortName", "reg.cjstatus");
      }
      if (sortName.equals("askpersonname")) {
        map.put("sortName", "per1.user_name");
      }
      if (sortName.equals("doctorname")) {
        map.put("sortName", "per2.user_name");
      }
      if (sortName.equals("repairdoctorname")) {
        map.put("sortName", "per5.user_name");
      }
      if (sortName.equals("remark")) {
        map.put("sortName", "us.remark");
      }
      if (sortName.equals("devchannelname")) {
        map.put("sortName", "kcit3.dict_name");
      }
      if (sortName.equals("nexttypename")) {
        map.put("sortName", "hzly.dict_name");
      }
      if (sortName.equals("ifcost")) {
        map.put("sortName", "reg.ifcost");
      }
      if (sortName.equals("createusername")) {
        map.put("sortName", "per3.user_name");
      }
      if (sortName.equals("receivenoname")) {
        map.put("sortName", "per4.user_name");
      }
      if (sortName.equals("editreason")) {
        map.put("sortName", "reg.editreason");
      }
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectDzlmenu", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    if (bp.getOffset() == 0)
    {
      JSONObject jzfljobj = new JSONObject();
      List<YZDict> jzflDict = this.dictLogic.getListByParentCodeALL(DictUtil.JZFL, ChainUtil.getCurrentOrganization(request));
      List<JSONObject> jzflDictList = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectDzlmenuRecesortNum", map);
      int nums = 0;
      jzfljobj.put("总数", Integer.valueOf(nums));
      Iterator localIterator2;
      for (Iterator localIterator1 = jzflDict.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
      {
        YZDict dict = (YZDict)localIterator1.next();
        jzfljobj.put(dict.getDictName(), Integer.valueOf(0));
        localIterator2 = jzflDictList.iterator(); continue;JSONObject obj = (JSONObject)localIterator2.next();
        if (obj.getString("recesort").equals(dict.getSeqId()))
        {
          nums += Integer.parseInt(obj.getString("num"));
          jzfljobj.put(dict.getDictName(), obj.getString("num"));
        }
      }
      jzfljobj.put("总数", Integer.valueOf(nums));
      jobj.put("jzfl", jzfljobj);
    }
    return jobj;
  }
  
  public JSONObject selectJzqk(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization, BootStrapPage bp)
    throws Exception
  {
    if (map.containsKey("usercodeorname"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("usercodeorname")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("usercodeorname")));
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    map.put("visualstaff", visualstaff);
    if (!YZUtility.isNullorEmpty((String)map.get("sortName")))
    {
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "reg.usercode");
      }
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "reg.username");
      }
      if (((String)map.get("sortName")).equals("phonenumber1")) {
        map.put("sortName", "u.phonenumber1");
      }
      if (((String)map.get("sortName")).equals("cjstatus")) {
        map.put("sortName", "reg.cjstatus");
      }
      if (((String)map.get("sortName")).equals("recesort")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (((String)map.get("sortName")).equals("regsort")) {
        map.put("sortName", "kcit2.dict_name");
      }
      if (((String)map.get("sortName")).equals("doctor")) {
        map.put("sortName", "per5.user_name");
      }
      if (((String)map.get("sortName")).equals("reggoal")) {
        map.put("sortName", "kcit3.dict_name");
      }
      if (((String)map.get("sortName")).equals("jzmdname")) {
        map.put("sortName", "kcit4.dict_name");
      }
      if (((String)map.get("sortName")).equals("ifmedrecord")) {
        map.put("sortName", "reg.ifmedrecord");
      }
      if (((String)map.get("sortName")).equals("ifqf")) {
        map.put("sortName", "reg.SEQ_ID");
      }
      if (((String)map.get("sortName")).equals("askperson")) {
        map.put("sortName", "per4.user_name");
      }
      if (((String)map.get("sortName")).equals("createtime")) {
        map.put("sortName", "reg.createtime");
      }
      if (((String)map.get("sortName")).equals("createuser")) {
        map.put("sortName", "per6.user_name");
      }
      if (((String)map.get("sortName")).equals("remark")) {
        map.put("sortName", "j.remark");
      }
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectJzqk", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", list);
    return jobj1;
  }
  
  public List<JSONObject> selectByusercode(String table, String usercode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectByusercode", usercode);
    return list;
  }
  
  public void editStatus(String doctor, String seqId, int ststus, HttpServletRequest request)
    throws Exception
  {
    KqdsReg reg = (KqdsReg)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
    if (reg != null)
    {
      YZPerson doctorPer = (YZPerson)this.dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, doctor);
      if (doctorPer != null) {
        reg.setRegdept(doctorPer.getDeptId());
      }
      if (ststus >= 2) {
        reg.setIfcost(Integer.valueOf(1));
      }
      reg.setStatus(Integer.valueOf(ststus));
      this.dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
    }
  }
  
  public void editCjstatus(String doctor, String seqId, int ststus, HttpServletRequest request)
    throws Exception
  {
    KqdsReg reg = (KqdsReg)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
    if (reg != null)
    {
      if (1 != reg.getCjstatus().intValue()) {
        reg.setCjstatus(Integer.valueOf(ststus));
      }
      reg.setStatus(Integer.valueOf(2));
      reg.setIfcost(Integer.valueOf(1));
      if (YZUtility.isNotNullOrEmpty(reg.getDoctor()))
      {
        YZPerson per = (YZPerson)this.dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, reg.getDoctor());
        if (per != null) {
          reg.setRegdept(per.getDeptId());
        }
      }
      this.dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
    }
  }
  
  public void setIfmedRecord(String seqId, int ifmedrecord, HttpServletRequest request)
    throws Exception
  {
    KqdsReg reg = (KqdsReg)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
    if (reg != null)
    {
      reg.setIfmedrecord(Integer.valueOf(ifmedrecord));
      this.dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
    }
  }
  
  public void editAskperson(String askperson, String seqId, HttpServletRequest request)
    throws Exception
  {
    KqdsReg reg = (KqdsReg)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
    if (reg == null) {
      throw new Exception("挂号记录不存在，seqId为：" + seqId);
    }
    reg.setAskperson(askperson);
    this.dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
  }
  
  public List<KqdsNetOrder> getTodayNetOrderByOrdertimeAndUsercode(String ordertime, String usercode)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("ordertime", ordertime);
    List<KqdsNetOrder> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getTodayNetOrderByOrdertimeAndUsercode", map);
    return list;
  }
  
  public List<JSONObject> getTodayHosOrderByOrdertimeAndUsercode(String ordertime, String usercode)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("ordertime", ordertime);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getTodayHosOrderByOrdertimeAndUsercode", map);
    return list;
  }
  
  public JSONObject selectJzcx(BootStrapPage bp, String table, YZPerson person, Map<String, String> map, List<YZDict> jzflDict, String organization, String visualstaff, HttpServletRequest request, String flag)
    throws Exception
  {
    String sfpriv = "";
    if (person != null) {
      sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID);
    }
    if (map.containsKey("searchValue"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("searchValue")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("searchValue")));
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    if ((person != null) && 
      (!YZUtility.isStrInArrayEach(person.getUserPriv(), sfpriv))) {
      map.put("visualstaff", visualstaff);
    }
    int firstIndex = 0;
    if (bp != null) {
      firstIndex = bp.getOffset();
    }
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("createtime")) {
        map.put("sortName", "r.createtime");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "r.usercode");
      }
      if (((String)map.get("sortName")).equals("blcode")) {
        map.put("sortName", "u.blcode");
      }
      if (((String)map.get("sortName")).equals("failreason1")) {
        map.put("sortName", "i.failreason1");
      }
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "r.username");
      }
      if (((String)map.get("sortName")).equals("phonenumber1")) {
        map.put("sortName", "u.phonenumber1");
      }
      if (((String)map.get("sortName")).equals("sex")) {
        map.put("sortName", "u.sex");
      }
      if (((String)map.get("sortName")).equals("age")) {
        map.put("sortName", "u.age");
      }
      if (((String)map.get("sortName")).equals("provincename")) {
        map.put("sortName", "u.province");
      }
      if (((String)map.get("sortName")).equals("cityname")) {
        map.put("sortName", "u.city");
      }
      if (((String)map.get("sortName")).equals("townname")) {
        map.put("sortName", "u.area");
      }
      if (((String)map.get("sortName")).equals("streetName")) {
        map.put("sortName", "u.town");
      }
      if (((String)map.get("sortName")).equals("important")) {
        map.put("sortName", "u.important");
      }
      if (((String)map.get("sortName")).equals("cjstatus")) {
        map.put("sortName", "r.cjstatus");
      }
      if (((String)map.get("sortName")).equals("recesort")) {
        map.put("sortName", "r.recesort");
      }
      if (((String)map.get("sortName")).equals("regsort")) {
        map.put("sortName", "r.regsort");
      }
      if (((String)map.get("sortName")).equals("faskperson")) {
        map.put("sortName", "u.askperson");
      }
      if (((String)map.get("sortName")).equals("askperson")) {
        map.put("sortName", "r.askperson");
      }
      if (((String)map.get("sortName")).equals("regdept")) {
        map.put("sortName", "r.regdept");
      }
      if (((String)map.get("sortName")).equals("doctor")) {
        map.put("sortName", "r.doctor");
      }
      if (((String)map.get("sortName")).equals("devchannel")) {
        map.put("sortName", "u.devchannel");
      }
      if (((String)map.get("sortName")).equals("nexttype")) {
        map.put("sortName", "u.nexttype");
      }
      if (((String)map.get("sortName")).equals("slgj")) {
        map.put("sortName", "u.accepttool");
      }
      if (((String)map.get("sortName")).equals("jf")) {
        map.put("sortName", "(select sum(c.actualmoney) from KQDS_COSTORDER c where c.regno = r.SEQ_ID and c.status=2 )");
      }
      if (((String)map.get("sortName")).equals("devitemdesc")) {
        map.put("sortName", "i.dev_item");
      }
      if (((String)map.get("sortName")).equals("ifmedrecord")) {
        map.put("sortName", "r.ifmedrecord");
      }
      if (((String)map.get("sortName")).equals("detaildesc")) {
        map.put("sortName", "i.detaildesc");
      }
      if (((String)map.get("sortName")).equals("jdr")) {
        map.put("sortName", "u.createuser");
      }
      if (((String)map.get("sortName")).equals("developername")) {
        map.put("sortName", "u.developer");
      }
      if (((String)map.get("sortName")).equals("jddy")) {
        map.put("sortName", "u.guider");
      }
      if (((String)map.get("sortName")).equals("createuser")) {
        map.put("sortName", "r.createuser");
      }
      if (((String)map.get("sortName")).equals("dy")) {
        map.put("sortName", "r.receiveno");
      }
    }
    if (bp != null) {
      PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    }
    if ((flag != null) && (flag.equals("exportTable")))
    {
      List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectJzcx", map);
      List<String> list1 = new ArrayList();
      for (JSONObject job : list)
      {
        String important = job.getString("important");
        if ("1".equals(important)) {
          important = "一级";
        } else if ("2".equals(important)) {
          important = "二级";
        } else if ("3".equals(important)) {
          important = "三级";
        } else if ("4".equals(important)) {
          important = "四级";
        } else if ("5".equals(important)) {
          important = "五级";
        }
        job.put("important", important);
        
        String cjstatusTmp = job.getString("cjstatus");
        if ("1".equals(cjstatusTmp)) {
          cjstatusTmp = "已成交";
        } else {
          cjstatusTmp = "未成交";
        }
        job.put("cjstatus", cjstatusTmp);
        
        String ifmedrecordTmp = job.getString("ifmedrecord");
        if ("1".equals(ifmedrecordTmp)) {
          ifmedrecordTmp = "已填写";
        } else {
          ifmedrecordTmp = "未填写";
        }
        job.put("ifmedrecord", ifmedrecordTmp);
        list1.add(job.getString("seq_id"));
      }
      PageInfo<JSONObject> pageInfo = new PageInfo(list);
      JSONObject jobj = new JSONObject();
      jobj.put("total", Long.valueOf(pageInfo.getTotal()));
      jobj.put("rows", list);
      return jobj;
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectJzcx", map);
    
    StringBuilder str = new StringBuilder();
    
    StringBuilder str1 = new StringBuilder();
    String cjstatusTmp;
    for (int i = 0; i < list.size(); i++)
    {
      String important = ((JSONObject)list.get(i)).getString("important");
      if ("1".equals(important)) {
        important = "一级";
      } else if ("2".equals(important)) {
        important = "二级";
      } else if ("3".equals(important)) {
        important = "三级";
      } else if ("4".equals(important)) {
        important = "四级";
      } else if ("5".equals(important)) {
        important = "五级";
      }
      ((JSONObject)list.get(i)).put("important", important);
      cjstatusTmp = ((JSONObject)list.get(i)).getString("cjstatus");
      if ("1".equals(cjstatusTmp)) {
        cjstatusTmp = "已成交";
      } else {
        cjstatusTmp = "未成交";
      }
      ((JSONObject)list.get(i)).put("cjstatus", cjstatusTmp);
      
      String ifmedrecordTmp = ((JSONObject)list.get(i)).getString("ifmedrecord");
      if ("1".equals(ifmedrecordTmp)) {
        ifmedrecordTmp = "已填写";
      } else {
        ifmedrecordTmp = "未填写";
      }
      ((JSONObject)list.get(i)).put("ifmedrecord", ifmedrecordTmp);
      if (i == list.size() - 1)
      {
        str1.append("'" + ((JSONObject)list.get(i)).getString("usercode") + "'");
        str.append("'" + ((JSONObject)list.get(i)).getString("seq_id") + "'");
      }
      else
      {
        str1.append("'" + ((JSONObject)list.get(i)).getString("usercode") + "',");
        str.append("'" + ((JSONObject)list.get(i)).getString("seq_id") + "',");
      }
      ((JSONObject)list.get(i)).put("ssje", "0.00");
      ((JSONObject)list.get(i)).put("streetName", "");
      ((JSONObject)list.get(i)).put("provincename", "");
      ((JSONObject)list.get(i)).put("cityname", "");
      ((JSONObject)list.get(i)).put("townname", "");
      ((JSONObject)list.get(i)).put("jdr", "");
      ((JSONObject)list.get(i)).put("doctor", "");
      ((JSONObject)list.get(i)).put("createuser", "");
      ((JSONObject)list.get(i)).put("dy", "");
      ((JSONObject)list.get(i)).put("recesort", "");
      ((JSONObject)list.get(i)).put("regsort", "");
      ((JSONObject)list.get(i)).put("failreason1", "");
      ((JSONObject)list.get(i)).put("devitemdesc", "");
      ((JSONObject)list.get(i)).put("jdr", "");
      ((JSONObject)list.get(i)).put("jddy", "");
      ((JSONObject)list.get(i)).put("faskperson", "");
      ((JSONObject)list.get(i)).put("developername", "");
      ((JSONObject)list.get(i)).put("devchannel", "");
      ((JSONObject)list.get(i)).put("nexttype", "");
      ((JSONObject)list.get(i)).put("slgj", "");
    }
    JSONObject jsonObject1;
    List<JSONObject> dictNameList;
    Object jsonObject1;
    if (str.length() > 0)
    {
      Object ssje = this.userlogic.getSsjeReg(str.toString());
      Iterator localIterator2;
      if (((List)ssje).size() > 0) {
        for (cjstatusTmp = list.iterator(); cjstatusTmp.hasNext(); localIterator2.hasNext())
        {
          JSONObject jsonObject = (JSONObject)cjstatusTmp.next();
          localIterator2 = ((List)ssje).iterator(); continue;JSONObject jsonObject1 = (JSONObject)localIterator2.next();
          if (jsonObject.getString("seq_id").equals(jsonObject1.getString("regno"))) {
            jsonObject.put("ssje", jsonObject1.getString("ssje"));
          }
        }
      }
      Map<String, String> map1 = new HashMap();
      map1.put("seqid", str.toString());
      List<JSONObject> nameList = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectNameBySeqid", map1);
      if (nameList.size() > 0)
      {
        Iterator localIterator3;
        for (localIterator2 = list.iterator(); localIterator2.hasNext(); localIterator3.hasNext())
        {
          JSONObject jsonObject = (JSONObject)localIterator2.next();
          localIterator3 = nameList.iterator(); continue;jsonObject1 = (JSONObject)localIterator3.next();
          if (jsonObject.getString("seq_id").equals(jsonObject1.getString("seqid")))
          {
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("askperson"))) {
              jsonObject.put("askperson", jsonObject1.getString("askperson"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("doctor"))) {
              jsonObject.put("doctor", jsonObject1.getString("doctor"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("createuser"))) {
              jsonObject.put("createuser", jsonObject1.getString("createuser"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("dy"))) {
              jsonObject.put("dy", jsonObject1.getString("dy"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("recesort"))) {
              jsonObject.put("recesort", jsonObject1.getString("recesort"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("regsort"))) {
              jsonObject.put("regsort", jsonObject1.getString("regsort"));
            }
          }
        }
      }
      dictNameList = (List)this.dao.findForList(TableNameUtil.KQDS_RECEIVEINFO + ".selectNameByRegno", map1);
      if (dictNameList.size() > 0)
      {
        Iterator localIterator4;
        for (jsonObject1 = list.iterator(); jsonObject1.hasNext(); localIterator4.hasNext())
        {
          JSONObject jsonObject = (JSONObject)jsonObject1.next();
          localIterator4 = dictNameList.iterator(); continue;jsonObject1 = (JSONObject)localIterator4.next();
          if (jsonObject.getString("seq_id").equals(((JSONObject)jsonObject1).getString("regno")))
          {
            if (!YZUtility.isNullorEmpty(((JSONObject)jsonObject1).getString("failreason1"))) {
              jsonObject.put("failreason1", ((JSONObject)jsonObject1).getString("failreason1"));
            }
            if (!YZUtility.isNullorEmpty(((JSONObject)jsonObject1).getString("devitemdesc"))) {
              jsonObject.put("devitemdesc", ((JSONObject)jsonObject1).getString("devitemdesc"));
            }
          }
        }
      }
    }
    if (str1.length() > 0)
    {
      Object map1 = new HashMap();
      ((Map)map1).put("usercode", str1.toString());
      List<JSONObject> areaList = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".findAreaByUsercode", map1);
      Object jsonObject1;
      if (areaList.size() > 0) {
        for (dictNameList = list.iterator(); dictNameList.hasNext(); jsonObject1.hasNext())
        {
          JSONObject jsonObject = (JSONObject)dictNameList.next();
          jsonObject1 = areaList.iterator(); continue;jsonObject1 = (JSONObject)jsonObject1.next();
          if (jsonObject.getString("usercode").equals(((JSONObject)jsonObject1).getString("usercode")))
          {
            if (!YZUtility.isNullorEmpty(((JSONObject)jsonObject1).getString("province_name"))) {
              jsonObject.put("provincename", ((JSONObject)jsonObject1).getString("province_name"));
            } else {
              jsonObject.put("provincename", ((JSONObject)jsonObject1).getString("provincename"));
            }
            if (!YZUtility.isNullorEmpty(((JSONObject)jsonObject1).getString("city_name"))) {
              jsonObject.put("cityname", ((JSONObject)jsonObject1).getString("city_name"));
            } else {
              jsonObject.put("cityname", ((JSONObject)jsonObject1).getString("cityname"));
            }
            if (!YZUtility.isNullorEmpty(((JSONObject)jsonObject1).getString("area_name"))) {
              jsonObject.put("townname", ((JSONObject)jsonObject1).getString("area_name"));
            } else {
              jsonObject.put("townname", ((JSONObject)jsonObject1).getString("townname"));
            }
            if (!YZUtility.isNullorEmpty(((JSONObject)jsonObject1).getString("street_name"))) {
              jsonObject.put("streetName", ((JSONObject)jsonObject1).getString("street_name"));
            }
          }
        }
      }
      List<JSONObject> nameList = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".findNameByUsercode", map1);
      if (nameList.size() > 0) {
        for (jsonObject1 = list.iterator(); ((Iterator)jsonObject1).hasNext(); ((Iterator)jsonObject1).hasNext())
        {
          JSONObject jsonObject = (JSONObject)((Iterator)jsonObject1).next();
          jsonObject1 = nameList.iterator(); continue;JSONObject jsonObject1 = (JSONObject)((Iterator)jsonObject1).next();
          if (jsonObject.getString("usercode").equals(jsonObject1.getString("usercode")))
          {
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("jdr"))) {
              jsonObject.put("jdr", jsonObject1.getString("jdr"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("jddy"))) {
              jsonObject.put("jddy", jsonObject1.getString("jddy"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("faskperson"))) {
              jsonObject.put("faskperson", jsonObject1.getString("faskperson"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("developername"))) {
              jsonObject.put("developername", jsonObject1.getString("developername"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("devchannel"))) {
              jsonObject.put("devchannel", jsonObject1.getString("devchannel"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("nexttype"))) {
              jsonObject.put("nexttype", jsonObject1.getString("nexttype"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject1.getString("slgj"))) {
              jsonObject.put("slgj", jsonObject1.getString("slgj"));
            }
          }
        }
      }
    }
    Object pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    if (firstIndex == 0)
    {
      Map<String, String> wheremap = new HashMap();
      wheremap.putAll(map);
      wheremap.put("del", "0");
      
      wheremap.put("cjstatus", "1");
      int cjtotal = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".selectJzcxtotal", wheremap)).intValue();
      jobj.put("cjtotal", Integer.valueOf(cjtotal));
      
      jobj.put("wcjtotal", Long.valueOf(((PageInfo)pageInfo).getTotal() - cjtotal));
      wheremap.remove("cjstatus");
      JSONObject jzflobj = new JSONObject();
      StringBuilder jzflStr = new StringBuilder();
      for (int j = 0; j < jzflDict.size(); j++) {
        if (j == jzflDict.size() - 1) {
          jzflStr.append("'" + ((YZDict)jzflDict.get(j)).getSeqId() + "'");
        } else {
          jzflStr.append("'" + ((YZDict)jzflDict.get(j)).getSeqId() + "',");
        }
      }
      wheremap.put("recesort", jzflStr.toString());
      Object jzfltotal = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectJzcxtotal1", wheremap);
      if (((List)jzfltotal).size() > 0)
      {
        for (JSONObject jsonObject : (List)jzfltotal) {
          jzflobj.put(jsonObject.getString("dictname"), jsonObject.getString("num"));
        }
        jobj.put("jzfl", jzflobj);
      }
    }
    jobj.put("total", Long.valueOf(((PageInfo)pageInfo).getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectToday(String table, YZPerson person, Map<String, String> map, String visualstaff, String visualstaffwd, BootStrapPage bp, String flag)
    throws Exception
  {
    map.put("starttime", (String)map.get("starttime") + ConstUtil.TIME_START);
    map.put("endtime", (String)map.get("endtime") + ConstUtil.TIME_END);
    if (map.containsKey("searchValue"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("searchValue")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("searchValue")));
    }
    map.put("visualstaff", visualstaff);
    map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
    if (map.get("sortName") != null)
    {
      String sortName = (String)map.get("sortName");
      if (sortName.equals("createtime")) {
        map.put("sortName", "r.createtime");
      }
      if (sortName.equals("usercode")) {
        map.put("sortName", "r.usercode");
      }
      if (sortName.equals("username")) {
        map.put("sortName", "r.username");
      }
      if (sortName.equals("age")) {
        map.put("sortName", "u.age");
      }
      if (sortName.equals("important")) {
        map.put("sortName", "u.important");
      }
      if (sortName.equals("jdr")) {
        map.put("sortName", "per3.USER_NAME");
      }
      if (sortName.equals("jdsj")) {
        map.put("sortName", "u.createtime");
      }
      if (sortName.equals("accepttype")) {
        map.put("sortName", "kcit5.dict_name");
      }
      if (sortName.equals("accepttool")) {
        map.put("sortName", "kcit6.dict_name");
      }
      if (sortName.equals("devchannel")) {
        map.put("sortName", "kcit3.dict_name");
      }
      if (sortName.equals("nexttype")) {
        map.put("sortName", "hzly.dict_name");
      }
      if (sortName.equals("kefuname")) {
        map.put("sortName", "per7.user_name");
      }
      if (sortName.equals("jddy")) {
        map.put("sortName", "per4.USER_NAME");
      }
      if (sortName.equals("createuser")) {
        map.put("sortName", "per5.USER_NAME");
      }
      if (sortName.equals("dy")) {
        map.put("sortName", "per6.USER_NAME");
      }
      if (sortName.equals("ywhf")) {
        map.put("sortName", "(select count(v.usercode) from KQDS_Visit v where v.usercode=u.UserCode)");
      }
      if (sortName.equals("doctor")) {
        map.put("sortName", "per2.USER_NAME");
      }
      if (sortName.equals("askperson")) {
        map.put("sortName", "per1.USER_NAME");
      }
      if (sortName.equals("regdept")) {
        map.put("sortName", "dept.DEPT_NAME");
      }
      if (sortName.equals("regsort")) {
        map.put("sortName", "kcit2.dict_name");
      }
      if (sortName.equals("recesort")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (sortName.equals("cjstatus")) {
        map.put("sortName", "r.cjstatus");
      }
      if (sortName.equals("sex")) {
        map.put("sortName", "u.sex");
      }
      if (sortName.equals("phonenumber1")) {
        map.put("sortName", "u.phonenumber1");
      }
    }
    if ((flag != null) && (flag.equals("exportTable"))) {
      map.put("flag", "exportTable");
    }
    List<JSONObject> list1 = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectTodayNum", map);
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectToday", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj1 = new JSONObject();
    StringBuilder str1;
    if (YZUtility.isNullorEmpty((String)map.get("flag")))
    {
      StringBuilder str = new StringBuilder();
      str1 = new StringBuilder();
      for (int i = 0; i < list.size(); i++)
      {
        if (i == list.size() - 1)
        {
          str.append("'" + ((JSONObject)list.get(i)).getString("seq_id") + "'");
          str1.append("'" + ((JSONObject)list.get(i)).getString("usercode") + "'");
        }
        else
        {
          str.append("'" + ((JSONObject)list.get(i)).getString("seq_id") + "',");
          str1.append("'" + ((JSONObject)list.get(i)).getString("usercode") + "',");
        }
        ((JSONObject)list.get(i)).put("jdr", "");
        ((JSONObject)list.get(i)).put("jddy", "");
        ((JSONObject)list.get(i)).put("devchannel", "");
        ((JSONObject)list.get(i)).put("nexttype", "");
        ((JSONObject)list.get(i)).put("ywhf", "无回访");
        ((JSONObject)list.get(i)).put("askperson", "");
        ((JSONObject)list.get(i)).put("doctor", "");
        ((JSONObject)list.get(i)).put("createuser", "");
        ((JSONObject)list.get(i)).put("dy", "");
        ((JSONObject)list.get(i)).put("recesort", "");
        ((JSONObject)list.get(i)).put("regdept", "");
        ((JSONObject)list.get(i)).put("regsort", "");
        ((JSONObject)list.get(i)).put("failreason1", "");
      }
      Object jsonObject;
      Object job;
      if (str1.length() > 0)
      {
        map.put("usercodes", str1.toString());
        List<JSONObject> namelist = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectTodayNameByUsercode", map);
        Iterator localIterator2;
        for (Iterator localIterator1 = namelist.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
        {
          JSONObject jsonObject = (JSONObject)localIterator1.next();
          localIterator2 = list.iterator(); continue;job = (JSONObject)localIterator2.next();
          if (jsonObject.getString("usercode").equals(job.getString("usercode")))
          {
            if (!YZUtility.isNullorEmpty(jsonObject.getString("jdr"))) {
              job.put("jdr", jsonObject.getString("jdr"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("jddy"))) {
              job.put("jddy", jsonObject.getString("jddy"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("devchannel"))) {
              job.put("devchannel", jsonObject.getString("devchannel"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("nexttype"))) {
              job.put("nexttype", jsonObject.getString("nexttype"));
            }
          }
        }
        List<JSONObject> ywhflist = (List)this.dao.findForList("KQDS_VISIT.selectYwhfNumByUsercode", map);
        Iterator localIterator3;
        for (JSONObject job = ywhflist.iterator(); job.hasNext(); localIterator3.hasNext())
        {
          jsonObject = (JSONObject)job.next();
          if ("0".equals(((JSONObject)jsonObject).getString("ywhf"))) {
            ((JSONObject)jsonObject).put("ywhf", "无回访");
          } else {
            ((JSONObject)jsonObject).put("ywhf", "有回访");
          }
          localIterator3 = list.iterator(); continue;job = (JSONObject)localIterator3.next();
          if ((((JSONObject)jsonObject).getString("usercode").equals(((JSONObject)job).getString("usercode"))) && 
            (!YZUtility.isNullorEmpty(((JSONObject)jsonObject).getString("ywhf")))) {
            ((JSONObject)job).put("ywhf", ((JSONObject)jsonObject).getString("ywhf"));
          }
        }
      }
      if (str.length() > 0)
      {
        map.put("seqid", str.toString());
        List<JSONObject> namelist = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectTodayNameBySeqid", map);
        for (jsonObject = namelist.iterator(); ((Iterator)jsonObject).hasNext(); ((Iterator)job).hasNext())
        {
          JSONObject jsonObject = (JSONObject)((Iterator)jsonObject).next();
          job = list.iterator(); continue;JSONObject job = (JSONObject)((Iterator)job).next();
          if (jsonObject.getString("seqid").equals(job.getString("seq_id")))
          {
            if (!YZUtility.isNullorEmpty(jsonObject.getString("askperson"))) {
              job.put("askperson", jsonObject.getString("askperson"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("doctor"))) {
              job.put("doctor", jsonObject.getString("doctor"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("createuser"))) {
              job.put("createuser", jsonObject.getString("createuser"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("dy"))) {
              job.put("dy", jsonObject.getString("dy"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("recesort"))) {
              job.put("recesort", jsonObject.getString("recesort"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("regdept"))) {
              job.put("regdept", jsonObject.getString("regdept"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("regsort"))) {
              job.put("regsort", jsonObject.getString("regsort"));
            }
            if (!YZUtility.isNullorEmpty(jsonObject.getString("failreason1"))) {
              job.put("failreason1", jsonObject.getString("failreason1"));
            }
          }
        }
      }
    }
    else
    {
      for (JSONObject job : list)
      {
        String ywhf = job.getString("ywhf");
        if ("0".equals(ywhf)) {
          ywhf = "无回访";
        } else {
          ywhf = "有回访";
        }
        job.put("ywhf", ywhf);
      }
    }
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", list);
    jobj1.put("nums", list1);
    return jobj1;
  }
  
  public JSONObject selectDzQuery(String table, YZPerson person, Map<String, String> map, String visualstaff, String visualstaffwd, BootStrapPage bp)
    throws Exception
  {
    map.put("starttime", (String)map.get("starttime") + ConstUtil.TIME_START);
    map.put("endtime", (String)map.get("endtime") + ConstUtil.TIME_END);
    if (map.containsKey("searchValue"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("searchValue")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("searchValue")));
    }
    map.put("visualstaff", visualstaff);
    map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("createtime")) {
        map.put("sortName", "reg.createtime");
      }
      if (((String)map.get("sortName")).equals("recesortname")) {
        map.put("sortName", "kcit1.dict_name");
      }
      if (((String)map.get("sortName")).equals("regsortname")) {
        map.put("sortName", "kcit2.dict_name");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "reg.usercode");
      }
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "reg.username");
      }
      if (((String)map.get("sortName")).equals("sex")) {
        map.put("sortName", "us.sex");
      }
      if (((String)map.get("sortName")).equals("age")) {
        map.put("sortName", "us.age");
      }
      if (((String)map.get("sortName")).equals("cjstatus")) {
        map.put("sortName", "reg.cjstatus");
      }
      if (((String)map.get("sortName")).equals("askpersonname")) {
        map.put("sortName", "per1.user_name");
      }
      if (((String)map.get("sortName")).equals("doctorname")) {
        map.put("sortName", "per2.user_name");
      }
      if (((String)map.get("sortName")).equals("repairdoctorname")) {
        map.put("sortName", "per5.user_name");
      }
      if (((String)map.get("sortName")).equals("remark")) {
        map.put("sortName", "us.remark");
      }
      if (((String)map.get("sortName")).equals("devchannelname")) {
        map.put("sortName", "kcit3.dict_name");
      }
      if (((String)map.get("sortName")).equals("nexttypename")) {
        map.put("sortName", "hzly.dict_name");
      }
      if (((String)map.get("sortName")).equals("slgj")) {
        map.put("sortName", "kcit4.dict_name");
      }
      if (((String)map.get("sortName")).equals("ifmedrecord")) {
        map.put("sortName", "reg.ifmedrecord");
      }
      if (((String)map.get("sortName")).equals("ifcost")) {
        map.put("sortName", "reg.ifcost");
      }
      if (((String)map.get("sortName")).equals("createusername")) {
        map.put("sortName", "per3.user_name");
      }
      if (((String)map.get("sortName")).equals("receivenoname")) {
        map.put("sortName", "per4.user_name");
      }
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectDzQuery", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    List<JSONObject> list1 = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectDzQueryNum", map);
    jobj.put("nums", list1);
    
    return jobj;
  }
  
  public int getCountIndex(String table, YZPerson person, int status, String querytype, String searchValue, String visualstaff, String organization, HttpServletRequest request)
    throws Exception
  {
    String sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID);
    Map<String, String> map = new HashMap();
    if (((YZUtility.isNullorEmpty(querytype)) || (querytype.equals("null")) || (querytype.equals("undefined"))) && 
      (!YZUtility.isStrInArrayEach(person.getUserPriv(), sfpriv))) {
      map.put("querytype", visualstaff);
    }
    StringBuffer statusBf = new StringBuffer();
    if (status != 5)
    {
      if (status == 2)
      {
        statusBf.append(" and r.status>=" + status + " ");
      }
      else
      {
        statusBf.append(" and r.status=" + status + " ");
        statusBf.append(" and " + SQLUtil.dateDiffDay("r.createtime"));
      }
    }
    else {
      statusBf.append(" and " + SQLUtil.dateDiffDay("r.createtime"));
    }
    map.put("statusSql", statusBf.toString());
    if (!YZUtility.isNullorEmpty(searchValue))
    {
      map.put("searchValue", searchValue);
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
    }
    map.put("organization", organization);
    
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getCountIndex", map)).intValue();
    return count;
  }
  
  public List getCountTj(String table, Map<String, String> map, String organization)
    throws Exception
  {
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getCountTj", map);
    for (JSONObject obj : list) {
      obj.put("recesortDesc", obj.getString("dict_name"));
    }
    return list;
  }
  
  public List getCountQylrAll(String table, int year, int month, int days, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("year", year);
    map.put("month", month);
    map.put("organization", organization);
    
    List<int[]> list = new ArrayList();
    int[] num = new int[days + 1];
    int[] allnum = new int[1];
    for (int i = 0; i <= days; i++)
    {
      num[i] = 0;
      map.put("day", i);
      int tmpcount = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getCountQylrAll", map)).intValue();
      num[i] = tmpcount;
      allnum[0] += tmpcount;
    }
    list.add(num);
    list.add(allnum);
    return list;
  }
  
  public List getCountQylrNew(String table, int year, int month, int days, String organization, HttpServletRequest request)
    throws Exception
  {
    String recesort = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID);
    recesort = YZUtility.ConvertStringIds4Query(recesort);
    Map<String, String> map = new HashMap();
    map.put("year", year);
    map.put("month", month);
    map.put("organization", organization);
    map.put("recesort", recesort);
    
    List<int[]> list = new ArrayList();
    int[] num = new int[days + 1];
    int[] allnum = new int[1];
    for (int i = 0; i <= days; i++)
    {
      num[i] = 0;
      map.put("day", i);
      int tmpcount = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getCountQylrNew", map)).intValue();
      num[i] = tmpcount;
      allnum[0] += tmpcount;
    }
    list.add(num);
    list.add(allnum);
    return list;
  }
  
  public Double getcjl(String date, String ghfl, String jzfl, String visualstaff, String organization)
    throws Exception
  {
    Double cjl = Double.valueOf(0.0D);
    Map<String, String> map = new HashMap();
    map.put("starttime", date + ConstUtil.TIME_START);
    map.put("endtime", date + ConstUtil.TIME_END);
    if (!YZUtility.isNullorEmpty(ghfl)) {
      map.put("regsort", ghfl);
    }
    if (!YZUtility.isNullorEmpty(jzfl)) {
      map.put("recesort", jzfl);
    }
    map.put("organization", organization);
    map.put("visualstaff", visualstaff);
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getcjl", map);
    if ((list != null) && (list.size() > 0)) {
      cjl = Double.valueOf(YZUtility.isNullorEmpty(((JSONObject)list.get(0)).getString("cjl")) ? 0.0D : ((JSONObject)list.get(0)).getDouble("cjl"));
    }
    return cjl;
  }
  
  public int getCountQylrNew(String table, String date, String ghfl, String jzfl, String visualstaff, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("starttime", date + ConstUtil.TIME_START);
    map.put("endtime", date + ConstUtil.TIME_END);
    if (!YZUtility.isNullorEmpty(ghfl)) {
      map.put("regsort", ghfl);
    }
    if (!YZUtility.isNullorEmpty(jzfl)) {
      map.put("recesort", jzfl);
    }
    map.put("organization", organization);
    map.put("visualstaff", visualstaff);
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getCountQylrNew2", map)).intValue();
    return nums;
  }
  
  public int getCountQylrAll(String table, String date, String ghfl, String jzfl, String visualstaff, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("starttime", date + ConstUtil.TIME_START);
    map.put("endtime", date + ConstUtil.TIME_END);
    if (!YZUtility.isNullorEmpty(ghfl)) {
      map.put("regsort", ghfl);
    }
    if (!YZUtility.isNullorEmpty(jzfl)) {
      map.put("recesort", jzfl);
    }
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getCountQylrAll2", map)).intValue();
    return nums;
  }
  
  public JSONObject selectToDayNewDetail(String usercode, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    if (!YZUtility.isNullorEmpty(usercode)) {
      map.put("usercode", usercode);
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    JSONObject obj = new JSONObject();
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectToDayNewDetail", map);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      obj.put("askperson", rs.getString("askperson"));
      obj.put("regsort", rs.getString("regsort"));
      obj.put("askpersonname", rs.getString("askpersonname"));
      obj.put("sortname", rs.getString("sortname"));
    }
    return obj;
  }
  
  public KqdsReg getRegForQsyy(String usercode, String createtime)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("createtime", createtime);
    List<KqdsReg> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getRegForQsyy", map);
    if ((list != null) && (list.size() > 0)) {
      return (KqdsReg)list.get(0);
    }
    return null;
  }
  
  public List<JSONObject> getNoUsercode(String usercode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getNoUsercode", usercode);
    return list;
  }
  
  public void deleteByRegno(String outprocessingsheetno)
    throws Exception
  {
    this.dao.delete(TableNameUtil.KQDS_REG + ".deleteByRegno", outprocessingsheetno);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void cancelReg(KqdsReg reg, HttpServletRequest request)
    throws Exception
  {
    this.dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
    
    this.jlogic.deleteByRegNo(reg.getSeqId(), request);
    

    deleteReceiveInfoByRegNo(reg.getSeqId(), request);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void updateReg(KqdsReg reg, KqdsReg regold, KqdsUserdocument userdoc, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    this.dao.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
    
    String regsortname = this.dictLogic.getDictNameBySeqId(reg.getRegsort());
    
    checkJzqk4RegEdit(reg, regold, userdoc, person, request, regsortname);
    

    checkReceive4RegEdit(reg, request);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public List<JSONObject> updateRegInsert(KqdsReg dp, KqdsUserdocument userdoc, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    this.dao.saveSingleUUID(TableNameUtil.KQDS_REG, dp);
    
    PushUtil.saveTx4NewReg(dp, request);
    
    BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_REG, dp, dp.getUsercode(), TableNameUtil.KQDS_REG, request);
    if (!YZUtility.isNullorEmpty(dp.getDoctor()))
    {
      checkUpdateInsertJZQK(dp, person, ChainUtil.getCurrentOrganization(request), request);
      

      userdoc.setDoctor(dp.getDoctor());
      this.dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, userdoc);
    }
    if (!YZUtility.isNullorEmpty(dp.getAskperson())) {
      saveReceive(dp, userdoc, ChainUtil.getCurrentOrganization(request), request);
    }
    if (userdoc.getDoorstatus().intValue() == 0)
    {
      userdoc.setDoorstatus(Integer.valueOf(1));
      updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, userdoc);
    }
    if (ConstUtil.USER_TYPE_1 == userdoc.getType().intValue()) {
      checkNetOrder(dp, request);
    }
    checkHosOrder(dp, request);
    



    Map<String, String> map = new HashMap();
    String date = YZUtility.getDateStr(new Date());
    map.put("starttime", date + ConstUtil.TIME_START);
    map.put("endtime", date + ConstUtil.TIME_END);
    map.put("usercode", dp.getUsercode());
    
    List<JSONObject> list = this.hospitalOrderLogic.selectHospitalOrderByUsercodeAndTime(map);
    if ((list != null) && (list.size() > 0)) {
      return list;
    }
    return null;
  }
  
  public JSONObject findKqdsRegByUserCode(String usercode)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_REG + ".findKqdsRegByUserCode", usercode);
    return json;
  }
  
  public JSONObject findRegByregNo(String kQDS_REG, String regno)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_REG + ".findRegByregNo", regno);
  }
  
  public String selectExistByUsercode(Map<String, String> map)
    throws Exception
  {
    String createtime = (String)this.dao.findForObject(TableNameUtil.KQDS_REG + ".selectExistByUsercode", map);
    return createtime;
  }
  
  public JSONObject findRegRecord(Map<String, String> map)
    throws Exception
  {
    JSONObject json = new JSONObject();
    List<JSONObject> findForList = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".findRegRecord", map);
    json.put("regRecord", Integer.valueOf(findForList.indexOf(Integer.valueOf(0))));
    return json;
  }
  
  public JSONObject findRecord(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".findRecord", map);
    JSONObject json = new JSONObject();
    if ((list.size() > 0) && (list != null)) {
      for (int i = 0; i < list.size(); i++) {
        json.put("json", list.get(0));
      }
    } else {
      json = null;
    }
    return json;
  }
  
  public List<JSONObject> findKqdsRegByUsercodes(String usercodes)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".findKqdsRegByUsercodes", usercodes);
    return list;
  }
  
  public List<JSONObject> findDepartment(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".findDepartment", map);
  }
  
  public JSONObject findZperformance(Map<String, String> map)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_REG + ".findZperformance", map);
  }
  
  public List<JSONObject> findDoctor(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".findDoctor", map);
  }
  
  public JSONObject findZdoctor(Map<String, String> map)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_REG + ".findZdoctor", map);
  }
  
  public List<JSONObject> findImplantNum(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".findImplantNum", map);
  }
  
  public JSONObject findImplantNumAll(Map<String, String> map)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_REG + ".findImplantNumAll", map);
  }
  
  public void updateRegCjstatus(String seqId)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_REG + ".updateRegCjstatus", seqId);
  }
  
  public void updateRegJhStatus(Map<String, String> map)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_REG + ".updateRegJhStatus", map);
  }
}
