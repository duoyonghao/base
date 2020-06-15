package com.kqds.service.base.machiningCenter;

import com.alibaba.fastjson.JSONArray;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMachining;
import com.kqds.entity.base.KqdsMachiningRecord;
import com.kqds.entity.base.KqdsMachiningRepairType;
import com.kqds.entity.base.KqdsMachiningRepairWay;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_MACHININGLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  @Transactional
  public boolean insert(KqdsMachining dp)
    throws Exception
  {
    boolean tooth = false;
    Integer seqId = (Integer)this.dao.save("KQDS_MACHINING.insert", dp);
    if (!YZUtility.isNullorEmpty(String.valueOf(seqId)))
    {
      String substring = dp.getToothMapArr();
      List<KqdsMachiningRepairWay> asList = JSONArray.parseArray(substring, KqdsMachiningRepairWay.class);
      tooth = insertTooth(asList, dp);
    }
    return tooth;
  }
  
  @Transactional
  public boolean insertTooth(List<KqdsMachiningRepairWay> list, KqdsMachining dp)
    throws Exception
  {
    for (int i = 0; i < list.size(); i++)
    {
      KqdsMachiningRepairWay bean = (KqdsMachiningRepairWay)list.get(i);
      if (bean != null)
      {
        bean.setSeqId(YZUtility.getUUID());
        bean.setWorksheetId(dp.getSeqId());
        bean.setCreateuser(dp.getCreateuser());
        bean.setCreatetime(dp.getCreatetime());
        bean.setOrganization(dp.getOrganization());
      }
      Integer id = (Integer)this.dao.save("KQDS_MACHININGREPAIRWAY.insert", bean);
      if (!YZUtility.isNullorEmpty(String.valueOf(id)))
      {
        String substring = bean.getRepairProjectArr();
        List<KqdsMachiningRepairType> asList = JSONArray.parseArray(substring, KqdsMachiningRepairType.class);
        batchrepair(asList, bean);
      }
    }
    return true;
  }
  
  public void batchrepair(List<KqdsMachiningRepairType> asList, KqdsMachiningRepairWay bean)
    throws Exception
  {
    for (int i = 0; i < asList.size(); i++)
    {
      KqdsMachiningRepairType kqdsMachiningRepair = (KqdsMachiningRepairType)asList.get(i);
      if (kqdsMachiningRepair != null)
      {
        kqdsMachiningRepair.setSeqId(YZUtility.getUUID());
        kqdsMachiningRepair.setToothId(bean.getSeqId());
        kqdsMachiningRepair.setCreateuser(bean.getCreateuser());
        kqdsMachiningRepair.setCreatetime(bean.getCreatetime());
        kqdsMachiningRepair.setOrganization(bean.getOrganization());
        this.dao.save("KQDS_MACHINING_REPAIRTYPE.insert", kqdsMachiningRepair);
      }
    }
  }
  
  public List<net.sf.json.JSONObject> getRecords(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList("KQDS_MACHINING.getRecordList", map);
  }
  
  @Transactional
  public net.sf.json.JSONObject getParticularsBySeqId(Map<String, String> map)
    throws Exception
  {
    net.sf.json.JSONObject json = (net.sf.json.JSONObject)this.dao.findForObject("KQDS_MACHINING.selectByPrimaryKey", map);
    net.sf.json.JSONObject object = getToothByPid(json.getString("seqId"), json);
    return object;
  }
  
  @Transactional
  public net.sf.json.JSONObject getToothByPid(String id, net.sf.json.JSONObject json)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    List<net.sf.json.JSONObject> KqdsMachining = new ArrayList();
    map.put("seqId", id);
    List<net.sf.json.JSONObject> findForList = (List)this.dao.findForList("KQDS_MACHININGREPAIRWAY.selectByParentKey", map);
    for (net.sf.json.JSONObject jsonObject : findForList)
    {
      Map<String, String> map2 = new HashMap();
      map2.put("seqId", jsonObject.getString("seqId"));
      List<net.sf.json.JSONObject> list = (List)this.dao.findForList("KQDS_MACHINING_REPAIRTYPE.selectByParentKey", map2);
      jsonObject.put("repairProjectArr", list);
      KqdsMachining.add(jsonObject);
    }
    json.put("toothMapArr", KqdsMachining);
    return json;
  }
  
  public List<net.sf.json.JSONObject> getGenre(String id)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("worksheetId", id);
    List<net.sf.json.JSONObject> typeList = new ArrayList();
    List<net.sf.json.JSONObject> list = (List)this.dao.findForList("KQDS_MACHININGREPAIRWAYselectByParentKey", map);
    for (net.sf.json.JSONObject json : list)
    {
      Map<String, String> map2 = new HashMap();
      map2.put("lcljId", json.getString("seqId"));
      typeList = (List)this.dao.findForList("KQDS_MACHINING_REPAIRTYPEselectByParentKey", map2);
    }
    return typeList;
  }
  
  @Transactional
  public void updateStatus(KqdsMachining dp, HttpServletRequest request)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    this.dao.update("KQDS_MACHINING.updateByPrimaryKeySelective", dp);
    if (Integer.valueOf(dp.getStatus()).intValue() == 5)
    {
      Map<String, String> map = new HashMap();
      map.put("seqId", dp.getSeqId());
      net.sf.json.JSONObject object = getParticularsBySeqId(map);
      KqdsMachining kMachining = new KqdsMachining();
      kMachining.setSeqId(YZUtility.getUUID());
      kMachining.setSystemNumber(YZUtility.getSystemID());
      kMachining.setOrderNumber(object.getString("ordernumber"));
      kMachining.setProcessUnit(object.getString("processunit"));
      kMachining.setDoctor(object.getString("doctor"));
      kMachining.setUserCode(object.getString("usercode"));
      kMachining.setUserName(object.getString("username"));
      kMachining.setSex(object.getString("sex"));
      kMachining.setAge(object.getString("age"));
      kMachining.setPhoneNumber(object.getString("phonenumber"));
      kMachining.setDeliverytime(object.getString("deliverytime"));
      kMachining.setChargeModelPerson(object.getString("chargemodelperson"));
      kMachining.setChargeModeltime(object.getString("chargemodeltime"));
      kMachining.setMaxillaryModel(object.getString("maxillarymodel"));
      kMachining.setOcclusioRecord(object.getString("occlusiorecord"));
      kMachining.setReplaceBody(object.getString("replacebody"));
      kMachining.setGingivalFormer(object.getString("gingivalformer"));
      kMachining.setMandibleModel(object.getString("mandiblemodel"));
      kMachining.setOldDentureModel(object.getString("olddenturemodel"));
      kMachining.setDrillBase(object.getString("drillbase"));
      kMachining.setLocator(object.getString("locator"));
      kMachining.setMaxillarySalver(object.getString("maxillarysalver"));
      kMachining.setWaxPattern(object.getString("waxpattern"));
      kMachining.setScrew(object.getString("screw"));
      kMachining.setJawFrame(object.getString("jawframe"));
      kMachining.setMandibleSalver(object.getString("mandiblesalver"));
      kMachining.setShiftLever(object.getString("shiftlever"));
      kMachining.setHumanGingival(object.getString("humangingival"));
      kMachining.setOmanJawBase(object.getString("omanjawbase"));
      kMachining.setOther(object.getString("other"));
      kMachining.setInnerCrown(object.getString("innercrown"));
      kMachining.setBiteJaw(object.getString("bitejaw"));
      kMachining.setSyntopy(object.getString("syntopy"));
      kMachining.setBridgeDesign(object.getString("bridgedesign"));
      kMachining.setNeckFlangeDesign(object.getString("neckflangedesign"));
      kMachining.setPlantFixed(object.getString("plantfixed"));
      kMachining.setStatus(object.getString("status"));
      kMachining.setOrganization(object.getString("organization"));
      kMachining.setCreateuser(person.getSeqId());
      kMachining.setCreatetime(object.getString("createtime"));
      this.dao.save("KQDS_MACHINING.insertdata", kMachining);
      String jsonArray = object.getString("toothMapArr");
      List<net.sf.json.JSONObject> list = com.alibaba.fastjson.JSONObject.parseArray(jsonArray, net.sf.json.JSONObject.class);
      Iterator localIterator2;
      for (Iterator localIterator1 = list.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
      {
        net.sf.json.JSONObject json = (net.sf.json.JSONObject)localIterator1.next();
        String string = json.getString("repairProjectArr");
        List<KqdsMachiningRepairType> repair = com.alibaba.fastjson.JSONObject.parseArray(string, KqdsMachiningRepairType.class);
        KqdsMachiningRepairWay kqdsMachiningRepairWay = new KqdsMachiningRepairWay();
        kqdsMachiningRepairWay.setSeqId(YZUtility.getUUID());
        kqdsMachiningRepairWay.setWorksheetId(kMachining.getSeqId());
        kqdsMachiningRepairWay.setUpleftTooth(json.getString("uplefttooth"));
        kqdsMachiningRepairWay.setUpperRightTooth(json.getString("upperrighttooth"));
        kqdsMachiningRepairWay.setLeftLowerTooth("leftlowertooth");
        kqdsMachiningRepairWay.setOrganization(json.getString("organization"));
        kqdsMachiningRepairWay.setLowRightTooth(json.getString("lowrighttooth"));
        kqdsMachiningRepairWay.setCreateuser(person.getSeqId());
        kqdsMachiningRepairWay.setCreatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        this.dao.save("KQDS_MACHININGREPAIRWAY.insertSelective", kqdsMachiningRepairWay);
        localIterator2 = repair.iterator(); continue;KqdsMachiningRepairType kqdsMachiningRepairType = (KqdsMachiningRepairType)localIterator2.next();
        kqdsMachiningRepairType.setSeqId(YZUtility.getUUID());
        kqdsMachiningRepairType.setToothId(kqdsMachiningRepairWay.getSeqId());
        this.dao.save("KQDS_MACHINING_REPAIRTYPE.insertSelective", kqdsMachiningRepairType);
      }
    }
    KqdsMachiningRecord kRecord = new KqdsMachiningRecord();
    kRecord.setSeqId(YZUtility.getUUID());
    kRecord.setWorksheetId(dp.getSeqId());
    kRecord.setSystemNumber(dp.getSystemNumber());
    kRecord.setUserCode(dp.getUserCode());
    kRecord.setUserName(dp.getUserName());
    kRecord.setStatus(dp.getStatus());
    kRecord.setCreateuser(person.getUserName());
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    kRecord.setCreatetime(df.format(new Date()));
    insertRecord(kRecord);
  }
  
  @Transactional
  public Integer insertRecord(KqdsMachiningRecord kRecord)
    throws Exception
  {
    this.dao.save("KQDS_MACHININGRECORD.insert", kRecord);
    return Integer.valueOf(1);
  }
  
  @Transactional
  public void update(KqdsMachining dp)
    throws Exception
  {
    Integer status = (Integer)this.dao.update("KQDS_MACHINING.updateByPrimaryKeySelective", dp);
    System.out.println(dp.toString());
    if (YZUtility.isNotNullOrEmpty(String.valueOf(status)))
    {
      String substring = dp.getToothMapArr();
      System.out.println(substring);
      List<KqdsMachiningRepairWay> asList = JSONArray.parseArray(substring, KqdsMachiningRepairWay.class);
      for (int i = 0; i < asList.size(); i++)
      {
        KqdsMachiningRepairWay bean = (KqdsMachiningRepairWay)asList.get(i);
        if (bean != null)
        {
          Map<String, String> map = new HashMap();
          map.put("toothId", bean.getSeqId());
          Integer id = (Integer)this.dao.delete("KQDS_MACHINING_REPAIRTYPE.deleteByPrimaryKey", map);
          if (!YZUtility.isNullorEmpty(String.valueOf(id)))
          {
            String string = bean.getRepairProjectArr();
            System.out.println(string);
            List<KqdsMachiningRepairType> list = JSONArray.parseArray(string, KqdsMachiningRepairType.class);
            batchrepair(list, bean);
          }
        }
      }
      Map<String, String> map1 = new HashMap();
      map1.put("worksheetId", dp.getSeqId());
      this.dao.delete("KQDS_MACHININGREPAIRWAY.deleteByPrimaryKey", map1);
      
      insertTooth(asList, dp);
    }
  }
  
  public List<net.sf.json.JSONObject> selectMachineByUsercode(String usercode)
    throws Exception
  {
    List<net.sf.json.JSONObject> list = (List)this.dao.findForList("KQDS_MACHINING.selectMachineByUsercode", usercode);
    return list;
  }
  
  public void updateMachineBySeqId(String seqId, String tryInTime)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("seqId", seqId);
    dataMap.put("tryInTime", tryInTime);
    dataMap.put("status", "");
    this.dao.update("KQDS_MACHINING.updateMachineBySeqId", dataMap);
  }
  
  public void updateMachineOutBySeqId(String seqId, String takeAwayTime)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("seqId", seqId);
    dataMap.put("takeAwayTime", takeAwayTime);
    dataMap.put("status", "");
    this.dao.update("KQDS_MACHINING.updateMachineOutBySeqId", dataMap);
  }
  
  public void delRecord(Map<String, String> map)
    throws Exception
  {
    this.dao.delete("KQDS_MACHINING.delRecord", map);
  }
  
  public List<net.sf.json.JSONObject> getFlow(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList("KQDS_MACHININGRECORD.selectByPrimaryKey", map);
  }
}
