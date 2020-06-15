package com.hudh.lclj.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.OperatingRecord;
import com.hudh.lclj.entity.PreoperativeVerification;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.util.sys.YZUtility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljTrackDao
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private KQDS_CostOrderLogic costOrderLogic;
  
  public int saveLcljOrderTrack(LcljOrderTrack lcljOrderTrack)
    throws Exception
  {
    int rsCount = ((Integer)this.dao.save("HUDH_LCLJ_ORDERTRACK.insertLcljOrderTrack", lcljOrderTrack)).intValue();
    return rsCount;
  }
  
  public List<JSONObject> findLcljOrderTrackByOrderNumber(String orderNumber)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_LCLJ_ORDERTRACK.findLcljOrderTrsackByOrderNumber", orderNumber);
    return list;
  }
  
  public List<JSONObject> findLcljOrderTrackAndHzInfo(String orderNumber)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_LCLJ_ORDERTRACK.findLcljOrderTrackAndHzInfo", orderNumber);
    return list;
  }
  
  public JSONObject findLcljOrderTrsackById(String id)
    throws Exception
  {
    JSONObject jsonOb = (JSONObject)this.dao.findForObject("HUDH_LCLJ_ORDERTRACK.findLcljOrderTrsackById", id);
    return jsonOb;
  }
  
  public LcljOrderTrack findLcljOrderTrsackByseqId(String id)
    throws Exception
  {
    LcljOrderTrack lcljOrderTrack = (LcljOrderTrack)this.dao.findForObject("HUDH_LCLJ_ORDERTRACK.findLcljOrderTrsackByseqId", id);
    return lcljOrderTrack;
  }
  
  public void updateOperateStatus(String orderTrackId)
    throws Exception
  {
    this.dao.update("HUDH_LCLJ_ORDERTRACK.updateOperateStatus", orderTrackId);
  }
  
  public void updateOperationFlowStatus(@Param("flag") String flag, String orderTrackId)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("flag", flag);
    map.put("orderNumber", orderTrackId);
    this.dao.update("HUDH_LCLJ_ORDERTRACK.updateOperationFlowStatus", map);
  }
  
  public List<JSONObject> findToohthNum(String orderNumber)
    throws Exception
  {
    List<JSONObject> jsono = (List)this.dao.findForList("HUDH_LCLJ_ORDERTRACK.findToohthNum", orderNumber);
    return jsono;
  }
  
  public void updateOrderTrack(Map<String, String> dataMap)
    throws Exception
  {
    this.dao.update("HUDH_LCLJ_ORDERTRACK.updateOrderTrack", dataMap);
  }
  
  public void updateOrderTrackNodes(LcljOrderTrack lcljOrderTrack)
    throws Exception
  {
    this.dao.update("HUDH_LCLJ_ORDERTRACK.updateOrderTrackNodes", lcljOrderTrack);
  }
  
  public LcljOrderTrack findNextOrderNumber()
    throws Exception
  {
    LcljOrderTrack lcljOrderTrack = (LcljOrderTrack)this.dao.findForObject("HUDH_LCLJ_ORDERTRACK.findNextOrderNumber", null);
    return lcljOrderTrack;
  }
  
  public JSONObject findOrderTrackInforByOrderNumber(Map<String, String> map, BootStrapPage bp, JSONObject json)
    throws Exception
  {
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "c.UserName");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "c.UserCode");
      }
      if (((String)map.get("sortName")).equals("phonenumber1")) {
        map.put("sortName", "c.PhoneNumber1");
      }
      if (((String)map.get("sortName")).equals("nodename")) {
        map.put("sortName", "e.nodeName");
      }
      if (((String)map.get("sortName")).equals("type")) {
        map.put("sortName", "a.type");
      }
      if (((String)map.get("sortName")).equals("flowname")) {
        map.put("sortName", "f.flowname");
      }
      if (((String)map.get("sortName")).equals("createtime")) {
        map.put("sortName", "a.createtime");
      }
      if (((String)map.get("sortName")).equals("order_number")) {
        map.put("sortName", "e.order_number");
      }
      if (((String)map.get("sortName")).equals("counsellor")) {
        map.put("sortName", "a.counsellor");
      }
      if (((String)map.get("sortName")).equals("plant_physician")) {
        map.put("sortName", "a.plant_physician");
      }
      if (((String)map.get("sortName")).equals("repair_physician")) {
        map.put("sortName", "a.repair_physician");
      }
      if (((String)map.get("sortName")).equals("flowstatus")) {
        map.put("sortName", "e.flowStatus");
      }
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    

    List<JSONObject> list = (List)this.dao.findForList("HUDH_LCLJ_ORDERTRACK.findOrderTrackInforByOrderNumber", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject findOrderTrackInforByOrderNumberCjstatus(Map<String, String> map, BootStrapPage bp, JSONObject json)
    throws Exception
  {
    String search = bp.getSearch();
    String sort = bp.getSort();
    json.put("search", search);
    json.put("sort", sort);
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "c.UserName");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "c.UserCode");
      }
      if (((String)map.get("sortName")).equals("phonenumber1")) {
        map.put("sortName", "c.PhoneNumber1");
      }
      if (((String)map.get("sortName")).equals("nodename")) {
        map.put("sortName", "e.nodeName");
      }
      if (((String)map.get("sortName")).equals("type")) {
        map.put("sortName", "a.type");
      }
      if (((String)map.get("sortName")).equals("flowname")) {
        map.put("sortName", "f.flowname");
      }
      if (((String)map.get("sortName")).equals("createtime")) {
        map.put("sortName", "a.createtime");
      }
      if (((String)map.get("sortName")).equals("order_number")) {
        map.put("sortName", "e.order_number");
      }
      if (((String)map.get("sortName")).equals("counsellor")) {
        map.put("sortName", "a.counsellor");
      }
      if (((String)map.get("sortName")).equals("plant_physician")) {
        map.put("sortName", "a.plant_physician");
      }
      if (((String)map.get("sortName")).equals("repair_physician")) {
        map.put("sortName", "a.repair_physician");
      }
      if (((String)map.get("sortName")).equals("flowstatus")) {
        map.put("sortName", "e.flowStatus");
      }
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    

    List<JSONObject> list = (List)this.dao.findForList("HUDH_LCLJ_ORDERTRACK.findOrderTrackInforByOrderNumber", map);
    for (JSONObject jsonO : list)
    {
      List<JSONObject> costList = this.costOrderLogic.findCostOrderByRegsortUsercode(jsonO.getString("usercode"));
      if ((costList != null) && (costList.size() > 0)) {
        jsonO.put("cjstatus", Integer.valueOf(1));
      } else {
        jsonO.put("cjstatus", Integer.valueOf(0));
      }
    }
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<KqdsReg> findRegListByBlcode(Map<String, String> map)
    throws Exception
  {
    List<KqdsReg> list = (List)this.dao.findForList("KQDS_REG.selectRegByusercode", map);
    return list;
  }
  
  public JSONObject findOrderTrackInforByConditionQuery(YZPerson person, Map<String, String> map, String organization)
    throws Exception
  {
    if (map.containsKey("usercodeorname"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("c.PhoneNumber1", (String)map.get("usercodeorname")));
      map.put("p2", YZAuthenticator.phonenumberLike("c.PhoneNumber2", (String)map.get("usercodeorname")));
    }
    if (YZUtility.isNotNullOrEmpty(organization)) {
      map.put("organization", organization);
    }
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_LCLJ_ORDERTRACK.findOrderTrackInforByConditionQuery", map);
    return json;
  }
  
  public void updateOrderTrackById(Map<String, String> dataMap)
    throws Exception
  {
    this.dao.update("HUDH_LCLJ_ORDERTRACK.updateOrderTrackById", dataMap);
  }
  
  public void deleteOrderTrackInforById(String id)
    throws Exception
  {
    this.dao.delete("HUDH_LCLJ_ORDERTRACK.deleteOrderTrackInforById", id);
  }
  
  public LcljOrderTrack findOrderTrackInforById(String id)
    throws Exception
  {
    LcljOrderTrack lcljOrderTrack = (LcljOrderTrack)this.dao.findForObject("HUDH_LCLJ_ORDERTRACK.findOrderTrackInforById", id);
    return lcljOrderTrack;
  }
  
  public void changeLcljOrderTrackBoneStatus(LcljOrderTrack lcljOrderTrack)
    throws Exception
  {
    this.dao.save("HUDH_LCLJ_ORDERTRACK.changeLcljOrderTrackBoneStatus", lcljOrderTrack);
  }
  
  public void updateLcljOrderTrackIsobsolete(String id)
    throws Exception
  {
    this.dao.update("HUDH_LCLJ_ORDERTRACK.updateLcljOrderTrackIsobsolete", id);
  }
  
  public void updateLcljOrderTrackById(LcljOrderTrack lcljOrderTrack)
    throws Exception
  {
    this.dao.update("HUDH_LCLJ_ORDERTRACK.updateLcljOrderTrackById", lcljOrderTrack);
  }
  
  public JSONObject savePreoperativeVerification(PreoperativeVerification pVerification)
    throws Exception
  {
    return (JSONObject)this.dao.save("HUDH_LCLJ_preoperativeVerification.savePreoperativeVerification", pVerification);
  }
  
  public JSONObject findPreoperativeVerification(String lcljId)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject("HUDH_LCLJ_preoperativeVerification.findPreoperativeVerification", lcljId);
  }
  
  public Integer editToothBit(LcljOrderTrack lTrack)
    throws Exception
  {
    return (Integer)this.dao.update("HUDH_LCLJ_ORDERTRACK.editToothBit", lTrack);
  }
  
  public void saveOperatingRecord(OperatingRecord oRecord)
    throws Exception
  {
    this.dao.save("HUDH_LCLJ_ORDERTRACK.saveOperatingRecord", oRecord);
  }
  
  public JSONObject findPatientInformation(String usercode)
    throws Exception
  {
    return (JSONObject)this.dao.findForObject("HUDH_LCLJ_ORDERTRACK.findPatientInformation", usercode);
  }
}
