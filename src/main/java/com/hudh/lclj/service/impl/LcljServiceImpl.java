package com.hudh.lclj.service.impl;

import com.alibaba.fastjson.JSON;
import com.hudh.lclj.dao.LcljDao;
import com.hudh.lclj.dao.LcljOrderImplemenDao;
import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.lclj.entity.LcljOperate;
import com.hudh.lclj.entity.LcljOperateDetail;
import com.hudh.lclj.entity.LcljOrder;
import com.hudh.lclj.entity.LcljOrderImplemen;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.PreoperativeVerification;
import com.hudh.lclj.service.ILcljService;
import com.hudh.lclj.util.OrderNumberUtil;
import com.hudh.lclj.util.ParseOperateXml;
import com.hudh.util.HUDHUtil;
import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.YZUtility;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LcljServiceImpl
  implements ILcljService
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private LcljDao lcljDao;
  @Autowired
  private LcljTrackDao lcljTrackDao;
  @Autowired
  private LcljOrderImplemenDao lcljOrderImplemenDao;
  
  public int saveLcljOrder(LcljOrder lcljOrder)
    throws Exception
  {
    lcljOrder.setId(YZUtility.getUUID());
    lcljOrder.setStatus("未完成");
    lcljOrder.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    lcljOrder.setRemainTooth(lcljOrder.getTotalTooth());
    OrderNumberUtil.getInstance();String orderNumber = OrderNumberUtil.getNextOrderNumber();
    lcljOrder.setOrderNumber(orderNumber);
    return this.lcljDao.saveLcljOrder(lcljOrder);
  }
  
  public JSONObject findLcljOrderByBlcode(String blCode)
    throws Exception
  {
    return this.lcljDao.findLcljOrderByBlcode(blCode);
  }
  
  public List<JSONObject> findLcljOrderByBlcodeAndStu(String blCode, String status)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("blCode", blCode);
    map.put("status", status);
    return this.lcljDao.findLcljOrderByBlcodeAndStu(map);
  }
  
  public LcljOrder findLcljOrderByOrderNumber(String orderNumber)
    throws Exception
  {
    return this.lcljDao.findLcljOrderByOrderNumber(orderNumber);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void saveLcljOrderTrackInfo(LcljOrderTrack lcljOrderTrack)
    throws Exception
  {
    String id = YZUtility.getUUID();
    
    lcljOrderTrack.setId(id);
    lcljOrderTrack.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    lcljOrderTrack.setFlowLink("手术");
    lcljOrderTrack.setSsStatus("未完成");
    lcljOrderTrack.setSsStu("未完成");
    lcljOrderTrack.setShgcStu("未完成");
    lcljOrderTrack.setDyStu("未完成");
    
    LcljOrderImplemen lcljOrderImplemen = new LcljOrderImplemen();
    lcljOrderImplemen.setId(YZUtility.getUUID());
    lcljOrderImplemen.setParentid(id);
    
    createOperateInfo(lcljOrderTrack.getType(), lcljOrderTrack.getBone(), lcljOrderImplemen);
    

    this.lcljTrackDao.saveLcljOrderTrack(lcljOrderTrack);
    this.lcljOrderImplemenDao.saveLcljOrderImplemen(lcljOrderImplemen);
    

    Map map = new HashMap();
    map.put("ssTooth", Integer.valueOf(Integer.parseInt(lcljOrderTrack.getTooth())));
    map.put("orderNumber", lcljOrderTrack.getOrderNumber());
    this.lcljDao.updateRemaintooth(map);
  }
  
  private void createOperateInfo(String type, String bone, LcljOrderImplemen lcljOrderImplemen)
  {
    Map<String, String> operateMap = new HashMap();
    if ((type.equals("1")) && (bone.equals("否"))) {
      operateMap = ParseOperateXml.getOperateMap("SingleOrMultiNoBone");
    } else if ((type.equals("1")) && (bone.equals("是"))) {
      operateMap = ParseOperateXml.getOperateMap("SingleOrMultiBone");
    } else if ((type.equals("2")) && (bone.equals("否"))) {
      operateMap = ParseOperateXml.getOperateMap("LocatorNoBone");
    } else if ((type.equals("2")) && (bone.equals("是"))) {
      operateMap = ParseOperateXml.getOperateMap("LocatorBone");
    } else if ((type.equals("3")) && (bone.equals("否"))) {
      operateMap = ParseOperateXml.getOperateMap("AllonxNoBone");
    } else if ((type.equals("3")) && (bone.equals("是"))) {
      operateMap = ParseOperateXml.getOperateMap("AllonxBone");
    }
    if (operateMap != null)
    {
      String ss = null;
      String shgc = null;
      String dy = null;
      
      LcljOperate lcljOperate = null;
      List<LcljOperate> lcljOperateList = null;
      for (Entry<String, String> entry : operateMap.entrySet())
      {
        lcljOperateList = new ArrayList();
        String key = (String)entry.getKey();
        String value = (String)entry.getValue();
        String[] optArray = value.split(";");
        int length = optArray.length;
        for (int i = 0; i < length; i++)
        {
          lcljOperate = new LcljOperate();
          lcljOperate.setName(optArray[i]);
          lcljOperate.setIsComplate("未完成");
          lcljOperateList.add(lcljOperate);
        }
        if (key.equals("operation")) {
          ss = JSON.toJSONString(lcljOperateList);
        } else if (key.equals("recovery")) {
          shgc = JSON.toJSONString(lcljOperateList);
        } else if (key.equals("wearTeeth")) {
          dy = JSON.toJSONString(lcljOperateList);
        }
      }
      lcljOrderImplemen.setSs(ss);
      lcljOrderImplemen.setShgc(shgc);
      lcljOrderImplemen.setDy(dy);
    }
  }
  
  public int findLcljOrderTrackByOrderNumber(String orderNumber)
    throws Exception
  {
    List<JSONObject> list = this.lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
    int ssTime = 0;
    if (list != null) {
      ssTime = list.size();
    }
    return ssTime;
  }
  
  public List<JSONObject> findLcljOrderTrackListByOrderNumber(String orderNumber)
    throws Exception
  {
    List<JSONObject> list = this.lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
    if (list != null) {
      return list;
    }
    return null;
  }
  
  public void updateOperateNoteInfo(String orderTrackId, String flowLink, String operateName, String remake)
    throws Exception
  {
    LcljOrderImplemen lcljOrderImplemen = this.lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
    
    String jsonStr = null;
    if ("手术".equals(flowLink))
    {
      jsonStr = lcljOrderImplemen.getSs();
      jsonStr = setRemakeToJsonStr(jsonStr, operateName, remake);
      lcljOrderImplemen.setSs(jsonStr);
    }
    else if ("术后观察".equals(flowLink))
    {
      jsonStr = lcljOrderImplemen.getShgc();
      jsonStr = setRemakeToJsonStr(jsonStr, operateName, remake);
      lcljOrderImplemen.setShgc(jsonStr);
    }
    else if ("戴牙".equals(flowLink))
    {
      jsonStr = lcljOrderImplemen.getDy();
      jsonStr = setRemakeToJsonStr(jsonStr, operateName, remake);
      lcljOrderImplemen.setDy(jsonStr);
    }
    this.lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
  }
  
  private String setRemakeToJsonStr(String jsonStr, String operateName, String remake)
  {
    Map childClazzMap = new HashMap();
    childClazzMap.put("detail", LcljOperateDetail.class);
    List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
    for (LcljOperate lop : list)
    {
      List<LcljOperateDetail> tempList = null;
      if (lop.getName().equals(operateName))
      {
        tempList = lop.getDetail();
        if (tempList == null) {
          tempList = new ArrayList();
        }
        LcljOperateDetail ld = new LcljOperateDetail();
        ld.setTime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        ld.setRemake(remake);
        tempList.add(ld);
        lop.setDetail(tempList);
        break;
      }
    }
    return JSON.toJSONString(list);
  }
  
  public int findHasOrderByBlcodeAndStu(String blCode)
    throws Exception
  {
    Map<String, String> paraMap = new HashMap();
    paraMap.put("blcode", blCode);
    paraMap.put("status", "未完成");
    return this.lcljDao.findHasOrder(paraMap);
  }
  
  public JSONObject findLcljOrderTrsackById(String orderTrackId)
    throws Exception
  {
    JSONObject jsonOb = this.lcljTrackDao.findLcljOrderTrsackById(orderTrackId);
    if (jsonOb != null) {
      return jsonOb;
    }
    return null;
  }
  
  public void updateOperateStatus(String ordernumber, String operateName)
    throws Exception
  {
    List<JSONObject> list = this.lcljTrackDao.findLcljOrderTrackByOrderNumber(ordernumber);
    if ((list != null) && (list.size() > 0))
    {
      String jsonStr = JSON.toJSONString(list);
      List<LcljOrderTrack> list1 = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOrderTrack.class);
      LcljOrderTrack lcljob = (LcljOrderTrack)list1.get(0);
      if (lcljob.getDyStu().equals("已完成"))
      {
        lcljob.setSsStatus("已完成");
        lcljob.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
      }
    }
  }
  
  public void changeOperateStatus(String operateName, String flowLink, String orderTrackId)
    throws Exception
  {
    LcljOrderImplemen lcljOrderImplemen = this.lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
    Map childClazzMap = new HashMap();
    childClazzMap.put("detail", LcljOperateDetail.class);
    
    String jsonStr = null;
    if ("手术".equals(flowLink))
    {
      jsonStr = lcljOrderImplemen.getSs();
      List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
      for (int i = 0; i < list.size(); i++)
      {
        LcljOperate l = (LcljOperate)list.get(i);
        String name = l.getName();
        if (operateName.equals(name))
        {
          l.setIsComplate("已完成");
          break;
        }
      }
      String jsonStri = JSON.toJSONString(list);
      lcljOrderImplemen.setSs(jsonStri);
    }
    else if ("术后观察".equals(flowLink))
    {
      jsonStr = lcljOrderImplemen.getShgc();
      List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
      for (int i = 0; i < list.size(); i++)
      {
        LcljOperate l = (LcljOperate)list.get(i);
        String name = l.getName();
        if (operateName.equals(name))
        {
          l.setIsComplate("已完成");
          break;
        }
      }
      String jsonStri = JSON.toJSONString(list);
      lcljOrderImplemen.setShgc(jsonStri);
    }
    else if ("戴牙".equals(flowLink))
    {
      jsonStr = lcljOrderImplemen.getDy();
      List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
      for (int i = 0; i < list.size(); i++)
      {
        LcljOperate l = (LcljOperate)list.get(i);
        String name = l.getName();
        if (operateName.equals(name))
        {
          l.setIsComplate("已完成");
          break;
        }
      }
      String jsonStri = JSON.toJSONString(list);
      lcljOrderImplemen.setDy(jsonStri);
    }
    this.lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
  }
  
  public String findOperateByTrackIdAndLink(String orderTrackId, String flowLink, String oprationName)
    throws Exception
  {
    LcljOrderImplemen lcljOrderImplemen = this.lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
    String operates = null;
    if (lcljOrderImplemen != null) {
      if ("手术".equals(flowLink)) {
        operates = lcljOrderImplemen.getSs();
      } else if ("术后观察".equals(flowLink)) {
        operates = lcljOrderImplemen.getShgc();
      } else if ("戴牙".equals(flowLink)) {
        operates = lcljOrderImplemen.getDy();
      }
    }
    return operates;
  }
  
  public List<LcljOperateDetail> findLcljOrderImplemenRemakeByTrackId(String orderTrackId, String flowLink, String oprationName)
    throws Exception
  {
    LcljOrderImplemen lcljOrderImplemen = this.lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
    Map childClazzMap = new HashMap();
    childClazzMap.put("detail", LcljOperateDetail.class);
    JSONObject jsonObj = new JSONObject();
    String operates = null;
    if (lcljOrderImplemen != null) {
      if ("手术".equals(flowLink))
      {
        operates = lcljOrderImplemen.getSs();
        List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(operates, LcljOperate.class, childClazzMap);
        if ((list != null) && (list.size() > 0)) {
          for (int i = 0; i < list.size(); i++)
          {
            String name = ((LcljOperate)list.get(i)).getName();
            if (oprationName.equals(name))
            {
              List<LcljOperateDetail> remak = ((LcljOperate)list.get(i)).getDetail();
              return remak;
            }
          }
        }
      }
      else if ("术后观察".equals(flowLink))
      {
        operates = lcljOrderImplemen.getShgc();
        List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(operates, LcljOperate.class, childClazzMap);
        for (int i = 0; i < list.size(); i++)
        {
          String name = ((LcljOperate)list.get(i)).getName();
          if (oprationName.equals(name))
          {
            List<LcljOperateDetail> remak = ((LcljOperate)list.get(i)).getDetail();
            return remak;
          }
        }
      }
      else if ("戴牙".equals(flowLink))
      {
        operates = lcljOrderImplemen.getDy();
        List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(operates, LcljOperate.class, childClazzMap);
        for (int i = 0; i < list.size(); i++)
        {
          String name = ((LcljOperate)list.get(i)).getName();
          if (oprationName.equals(name))
          {
            List<LcljOperateDetail> remak = ((LcljOperate)list.get(i)).getDetail();
            return remak;
          }
        }
      }
    }
    return null;
  }
  
  public void updateOrderStatus(String orderNumber)
    throws Exception
  {
    int total_tooth = 0;
    int tooth = 0;
    List<JSONObject> jsono = this.lcljTrackDao.findToohthNum(orderNumber);
    for (int i = 0; i < jsono.size(); i++)
    {
      Object j = ((JSONObject)jsono.get(i)).get("total_tooth");
      Object t = ((JSONObject)jsono.get(i)).get("tooth");
      String j1 = j.toString();
      int total_tooth1 = Integer.valueOf(j1).intValue();
      total_tooth = total_tooth1;
      String t1 = t.toString();
      int tooth1 = Integer.valueOf(t1).intValue();
      tooth += tooth1;
      System.out.println(total_tooth);
      System.out.println(tooth1);
    }
    if (total_tooth == tooth) {
      this.lcljDao.updateOrderStatus(orderNumber);
    }
  }
  
  public void updateOperationFlowStatus(String operateName, String orderTrackId, String flowLink)
    throws Exception
  {
    LcljOrderTrack lclj = new LcljOrderTrack();
    LcljOrderImplemen lcljOrderImplemen = this.lcljOrderImplemenDao.findLcljOrderImplemenByTrackId(orderTrackId);
    Map childClazzMap = new HashMap();
    childClazzMap.put("detail", LcljOperateDetail.class);
    
    String jsonStr = null;
    boolean flagStu = true;
    String flag = null;
    if ("手术".equals(flowLink))
    {
      flag = "ss";
      jsonStr = lcljOrderImplemen.getSs();
      List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
      for (LcljOperate lcljOperate : list) {
        if (lcljOperate.getName().equals(operateName)) {
          lcljOperate.setIsComplate("已完成");
        } else if (lcljOperate.getIsComplate().equals("未完成")) {
          flagStu = false;
        }
      }
      String jsonStri = JSON.toJSONString(list);
      lcljOrderImplemen.setSs(jsonStri);
      
      this.lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
    }
    else if ("术后观察".equals(flowLink))
    {
      flag = "shgc";
      jsonStr = lcljOrderImplemen.getShgc();
      List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
      for (LcljOperate lcljOperate : list) {
        if (lcljOperate.getName().equals(operateName)) {
          lcljOperate.setIsComplate("已完成");
        } else if (lcljOperate.getIsComplate().equals("未完成")) {
          flagStu = false;
        }
      }
      String jsonStri = JSON.toJSONString(list);
      lcljOrderImplemen.setShgc(jsonStri);
      
      this.lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
    }
    else if ("戴牙".equals(flowLink))
    {
      flag = "dy";
      jsonStr = lcljOrderImplemen.getDy();
      List<LcljOperate> list = HUDHUtil.parseJsonToObjectList(jsonStr, LcljOperate.class, childClazzMap);
      for (LcljOperate lcljOperate : list) {
        if (lcljOperate.getName().equals(operateName)) {
          lcljOperate.setIsComplate("已完成");
        } else if (lcljOperate.getIsComplate().equals("未完成")) {
          flagStu = false;
        }
      }
      String jsonStri = JSON.toJSONString(list);
      lcljOrderImplemen.setDy(jsonStri);
      
      this.lcljOrderImplemenDao.updateOperateNoteInfo(lcljOrderImplemen);
    }
    if (flagStu) {
      this.lcljTrackDao.updateOperationFlowStatus(flag, orderTrackId);
    }
    if ((flag.equals("dy")) && (flagStu)) {
      this.lcljTrackDao.updateOperateStatus(orderTrackId);
    }
  }
  
  public JSONObject savePreoperativeVerification(PreoperativeVerification pVerification)
    throws Exception
  {
    return this.lcljTrackDao.savePreoperativeVerification(pVerification);
  }
  
  public JSONObject findPreoperativeVerification(String lcljId)
    throws Exception
  {
    return this.lcljTrackDao.findPreoperativeVerification(lcljId);
  }
}
