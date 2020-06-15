package com.kqds.controller.base.jh;

import com.kqds.entity.base.KqdsJh;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.jh.Kqds_Jh_Logic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/Kqds_JhAct"})
public class Kqds_JhAct {
  private static Logger logger = LoggerFactory.getLogger(Kqds_JhAct.class);
  
  @Autowired
  private Kqds_Jh_Logic logic;
  
  @Autowired
  private KQDS_REGLogic regLogic;
  
  @RequestMapping({"/insert.act"})
  public String insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      String askperson = request.getParameter("askperson");
      String patient_name = request.getParameter("username");
      String sex = request.getParameter("sex");
      String regSeqId = request.getParameter("regid");
      String age = request.getParameter("age");
      String items = request.getParameter("items");
      String surgeryStatus = request.getParameter("surgery");
      String floor = request.getParameter("floor");
      String seqid = request.getParameter("seqid");
      String joint = request.getParameter("joint");
      String uplefttoothbit = request.getParameter("uplefttoothbit");
      String uperrighttoothbit = request.getParameter("uperrighttoothbit");
      String leftlowertoothbit = request.getParameter("leftlowertoothbit");
      String lowrighttoothbit = request.getParameter("lowrighttoothbit");
      YZPerson person = SessionUtil.getLoginPerson(request);
      JSONArray jsonArr = JSONArray.fromObject(floor);
      for (int i = 0; i < jsonArr.size(); i++) {
        if (jsonArr.get(i).equals("A"))
          floor = "A"; 
      } 
      if (items.contains("小牙片"))
        floor = "B"; 
      if (surgeryStatus.equals("复查"))
        floor = "B"; 
      if (!YZUtility.isNullorEmpty(seqid)) {
        KqdsJh jh1 = new KqdsJh();
        jh1.setId(seqid);
        jh1.setDel(2);
        this.logic.updateDel(jh1);
      } 
      KqdsJh jh = new KqdsJh();
      jh.setId(YZUtility.getUUID());
      jh.setUsercode(usercode);
      jh.setCreateuser(person.getSeqId());
      jh.setCreatetime(YZUtility.getCurDateTimeStr());
      jh.setAskperson(askperson);
      jh.setPatientName(patient_name);
      jh.setStatus("1");
      jh.setSex(sex);
      jh.setRegid(regSeqId);
      jh.setDel(0);
      jh.setOrganization(ChainUtil.getCurrentOrganization(request));
      jh.setAge(Integer.parseInt(age));
      jh.setItems(items);
      jh.setSurgeryStatus(surgeryStatus);
      jh.setFloor(floor);
      if (!YZUtility.isNullorEmpty(joint))
        jh.setJoint(joint); 
      if (!YZUtility.isNullorEmpty(uplefttoothbit))
        jh.setUplefttoothbit(uplefttoothbit); 
      if (!YZUtility.isNullorEmpty(uperrighttoothbit))
        jh.setUperrighttoothbit(uperrighttoothbit); 
      if (!YZUtility.isNullorEmpty(leftlowertoothbit))
        jh.setLeftlowertoothbit(leftlowertoothbit); 
      if (!YZUtility.isNullorEmpty(lowrighttoothbit))
        jh.setLowrighttoothbit(lowrighttoothbit); 
      this.logic.insert(jh);
      if (regSeqId != null) {
        Map<String, String> map = new HashMap<>();
        map.put("seqid", regSeqId);
        map.put("jh", "2");
        this.regLogic.updateRegJhStatus(map);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectFy.act"})
  public String selectFy(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String status = request.getParameter("status");
      String floor = request.getParameter("floor");
      Map<String, String> map = new HashMap<>();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String time = df.format(new Date());
      if (!YZUtility.isNullorEmpty(time)) {
        map.put("starttime", String.valueOf(time) + ConstUtil.TIME_START);
        map.put("endtime", String.valueOf(time) + ConstUtil.TIME_END);
      } 
      if (!YZUtility.isNullorEmpty(status))
        map.put("status", status); 
      if (!YZUtility.isNullorEmpty(floor))
        map.put("floor", floor); 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject obj = this.logic.selectFy(map, bp);
      YZUtility.DEAL_SUCCESS(obj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/select.act"})
  public String select(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String status = request.getParameter("status");
      String floor = request.getParameter("floor");
      Map<String, String> map = new HashMap<>();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String time = df.format(new Date());
      if (!YZUtility.isNullorEmpty(time)) {
        map.put("starttime", String.valueOf(time) + ConstUtil.TIME_START);
        map.put("endtime", String.valueOf(time) + ConstUtil.TIME_END);
      } 
      if (!YZUtility.isNullorEmpty(status))
        map.put("status", status); 
      if (!YZUtility.isNullorEmpty(floor))
        map.put("floor", floor); 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = this.logic.select(map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectByRegId.act"})
  public String selectById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String regid = request.getParameter("regid");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(regid))
        map.put("regid", regid); 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = this.logic.selectByRegId(map);
      YZUtility.RETURN_OBJ(list.get(0), response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectdelFy.act"})
  public String selectdelFy(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String floor = request.getParameter("floor");
      Map<String, String> map = new HashMap<>();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String time = df.format(new Date());
      if (!YZUtility.isNullorEmpty(time)) {
        map.put("starttime", String.valueOf(time) + ConstUtil.TIME_START);
        map.put("endtime", String.valueOf(time) + ConstUtil.TIME_END);
      } 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      if (!YZUtility.isNullorEmpty(floor))
        map.put("floor", floor); 
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject obj = this.logic.selectdelFy(map, bp);
      YZUtility.DEAL_SUCCESS(obj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectdel.act"})
  public String selectdel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String floor = request.getParameter("floor");
      Map<String, String> map = new HashMap<>();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String time = df.format(new Date());
      if (!YZUtility.isNullorEmpty(time)) {
        map.put("starttime", String.valueOf(time) + ConstUtil.TIME_START);
        map.put("endtime", String.valueOf(time) + ConstUtil.TIME_END);
      } 
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      if (!YZUtility.isNullorEmpty(floor))
        map.put("floor", floor); 
      List<JSONObject> list = this.logic.selectdel(map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectTime.act"})
  public String selectTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = request.getParameter("organization");
      String startTime = request.getParameter("starttime");
      String endTime = request.getParameter("endtime");
      String floor = request.getParameter("floor");
      String items = request.getParameter("items");
      String surgery = request.getParameter("surgery");
      try {
        Map<String, String> map = new HashMap<>();
        if (!YZUtility.isNullorEmpty(organization))
          map.put("organization", organization); 
        if (!YZUtility.isNullorEmpty(floor) && 
          !floor.equals("all"))
          map.put("floor", floor); 
        if (!YZUtility.isNullorEmpty(items)) {
          String[] item = items.split(",");
          if (item.length == 1) {
            map.put("items", "[\"" + items + "\"]");
          } else if (item.length > 1) {
            String str = "";
            for (int i = 0; i < item.length; i++) {
              if (i == 0) {
                str = "[\"" + item[i] + "\",";
              } else if (i == item.length - 1) {
                str = String.valueOf(str) + "\"" + item[i] + "\"]";
              } else {
                str = String.valueOf(str) + "\"" + item[i] + "\",";
              } 
            } 
            map.put("items", str);
          } 
        } 
        if (!YZUtility.isNullorEmpty(surgery) && 
          !surgery.equals("all"))
          map.put("surgery", surgery); 
        if (!YZUtility.isNullorEmpty(startTime))
          map.put("starttime", String.valueOf(startTime) + ConstUtil.TIME_START); 
        if (!YZUtility.isNullorEmpty(endTime))
          map.put("endtime", String.valueOf(endTime) + ConstUtil.TIME_END); 
        BootStrapPage bp = new BootStrapPage();
        BeanUtils.populate(bp, request.getParameterMap());
        JSONObject list = this.logic.selectByTime(map, bp);
        YZUtility.RETURN_OBJ(list, response, logger);
      } catch (Exception e) {
        YZUtility.DEAL_ERROR(null, true, e, response, logger);
      } 
      return null;
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
      return null;
    } 
  }
  
  @RequestMapping({"/selectTimeByDel.act"})
  public String selectTimeByDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = request.getParameter("organization");
      String startTime = request.getParameter("starttime");
      String endTime = request.getParameter("endtime");
      String floor = request.getParameter("floor");
      String items = request.getParameter("items");
      String surgery = request.getParameter("surgery");
      try {
        Map<String, String> map = new HashMap<>();
        if (!YZUtility.isNullorEmpty(organization))
          map.put("organization", organization); 
        if (!YZUtility.isNullorEmpty(floor) && 
          !floor.equals("all"))
          map.put("floor", floor); 
        if (!YZUtility.isNullorEmpty(items)) {
          String[] item = items.split(",");
          if (item.length == 1) {
            map.put("items", "[\"" + items + "\"]");
          } else if (item.length > 1) {
            String str = "";
            for (int i = 0; i < item.length; i++) {
              if (i == 0) {
                str = "[\"" + item[i] + "\",";
              } else if (i == item.length - 1) {
                str = String.valueOf(str) + "\"" + item[i] + "\"]";
              } else {
                str = String.valueOf(str) + "\"" + item[i] + "\",";
              } 
            } 
            map.put("items", str);
          } 
        } 
        if (!YZUtility.isNullorEmpty(surgery) && 
          !surgery.equals("all"))
          map.put("surgery", surgery); 
        if (!YZUtility.isNullorEmpty(startTime))
          map.put("starttime", String.valueOf(startTime) + ConstUtil.TIME_START); 
        if (!YZUtility.isNullorEmpty(endTime))
          map.put("endtime", String.valueOf(endTime) + ConstUtil.TIME_END); 
        BootStrapPage bp = new BootStrapPage();
        BeanUtils.populate(bp, request.getParameterMap());
        JSONObject list = this.logic.selectByTimeByDel(map, bp);
        YZUtility.RETURN_OBJ(list, response, logger);
      } catch (Exception e) {
        YZUtility.DEAL_ERROR(null, true, e, response, logger);
      } 
      return null;
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
      return null;
    } 
  }
  
  @RequestMapping({"/update.act"})
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String id = request.getParameter("id");
      String regid = request.getParameter("regid");
      String status = request.getParameter("status");
      String floor = request.getParameter("floor");
      String organization = request.getParameter("organization");
      String number = request.getParameter("number");
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsJh jh = new KqdsJh();
      jh.setId(id);
      jh.setOperator(person.getSeqId());
      if (status.equals("0")) {
        jh.setStatus("1");
        status = "1";
        jh.setDel(0);
        jh.setFloor(floor);
        jh.setNumber(Integer.parseInt(number));
        jh.setOrganization(organization);
      } else {
        jh.setStatus(status);
      } 
      if (status.equals("2"))
        jh.setStarttime(YZUtility.getCurDateTimeStr()); 
      if (status.equals("3"))
        jh.setEndtime(YZUtility.getCurDateTimeStr()); 
      this.logic.update(jh);
      if (regid != null) {
        Map<String, String> map = new HashMap<>();
        map.put("seqid", regid);
        map.put("jh", (new StringBuilder(String.valueOf(Integer.parseInt(status) + 1))).toString());
        this.regLogic.updateRegJhStatus(map);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateFloor.act"})
  public String updateFloor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String ids = request.getParameter("ids");
      String numbers = request.getParameter("numbers");
      String floor = request.getParameter("floor");
      YZPerson person = SessionUtil.getLoginPerson(request);
      JSONArray jsonArray1 = JSONArray.fromObject(ids);
      JSONArray jsonArray2 = JSONArray.fromObject(numbers);
      List<KqdsJh> jhList = new ArrayList<>();
      for (int i = 0; i < jsonArray1.size(); i++) {
        KqdsJh jh = new KqdsJh();
        jh.setId((String)jsonArray1.get(i));
        jh.setOperator(person.getSeqId());
        if (floor.equals("A")) {
          jh.setFloor("B");
        } else {
          jh.setFloor("A");
        } 
        jh.setOrganization(ChainUtil.getCurrentOrganization(request));
        jhList.add(jh);
      } 
      if (jhList.size() > 0)
        this.logic.updateFloor(jhList); 
      int j = Integer.parseInt(Collections.<String>min((Collection<? extends String>)jsonArray2));
      Map<String, String> map = new HashMap<>();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String time = df.format(new Date());
      if (!YZUtility.isNullorEmpty(time)) {
        map.put("starttime", String.valueOf(time) + ConstUtil.TIME_START);
        map.put("endtime", String.valueOf(time) + ConstUtil.TIME_END);
      } 
      map.put("number", (new StringBuilder(String.valueOf(j))).toString());
      map.put("floor", floor);
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<KqdsJh> list1 = this.logic.selectByNumber(map);
      if (list1.size() > 0) {
        for (int k = 0; k < list1.size(); k++)
          ((KqdsJh)list1.get(k)).setNumber(k + j); 
        this.logic.updateNumber(list1);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/precedence.act"})
  public String precedence(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String id = request.getParameter("id");
      String floor = request.getParameter("floor");
      YZPerson person = SessionUtil.getLoginPerson(request);
      Map<String, String> map = new HashMap<>();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String time = df.format(new Date());
      if (!YZUtility.isNullorEmpty(time)) {
        map.put("starttime", String.valueOf(time) + ConstUtil.TIME_START);
        map.put("endtime", String.valueOf(time) + ConstUtil.TIME_END);
      } 
      map.put("floor", floor);
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<KqdsJh> list1 = this.logic.selectByNumber(map);
      List<KqdsJh> list = new ArrayList<>();
      List<KqdsJh> list2 = new ArrayList<>();
      KqdsJh jh = new KqdsJh();
      jh.setId(id);
      jh.setOperator(person.getSeqId());
      if (list1.size() > 0) {
        int j = ((KqdsJh)list1.get(0)).getNumber();
        jh.setNumber(j);
        list.add(jh);
        int i;
        for (i = 0; i < list1.size(); i++) {
          if (!((KqdsJh)list1.get(i)).getId().equals(id))
            list2.add(list1.get(i)); 
        } 
        for (i = 0; i < list2.size(); i++) {
          ((KqdsJh)list2.get(i)).setNumber(i + j + 1);
          ((KqdsJh)list2.get(i)).setOperator(person.getSeqId());
        } 
        list.addAll(list2);
        this.logic.updateNumber(list);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/delete.act"})
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String id = request.getParameter("id");
      String regSeqId = request.getParameter("regid");
      String remark = request.getParameter("remark");
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsJh jh = new KqdsJh();
      jh.setId(id);
      jh.setOperator(person.getSeqId());
      jh.setDel(1);
      jh.setRemark(remark);
      jh.setEndtime(YZUtility.getCurDateTimeStr());
      this.logic.updateDel(jh);
      if (regSeqId != null) {
        Map<String, String> map = new HashMap<>();
        map.put("seqid", regSeqId);
        map.put("jh", "5");
        this.regLogic.updateRegJhStatus(map);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/toJhShow.act"})
  public ModelAndView toJhShow(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String url = "/kqdsFront/jh/jh_show.jsp";
    return new ModelAndView(url);
  }
  
  @RequestMapping({"/toExhibition.act"})
  public ModelAndView toExhibition(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String url = "/kqdsFront/jh/exhibition.jsp";
    return new ModelAndView(url);
  }
  
  @RequestMapping({"/toOvertime.act"})
  public ModelAndView toOvertime(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String regSeqId = request.getParameter("regid");
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    String sex = request.getParameter("sex");
    String askpersonname = request.getParameter("askpersonname");
    String age = request.getParameter("age");
    String phone = request.getParameter("phone");
    String floor = request.getParameter("floor");
    String number = request.getParameter("number");
    String url = "/kqdsFront/jh/overtime.jsp";
    ModelAndView model = new ModelAndView();
    model.addObject("id", id);
    model.addObject("regid", regSeqId);
    model.addObject("usercode", usercode);
    model.addObject("username", username);
    model.addObject("sex", sex);
    model.addObject("askpersonname", askpersonname);
    model.addObject("age", age);
    model.addObject("phone", phone);
    model.addObject("floor", floor);
    model.addObject("number", number);
    model.setViewName(url);
    return model;
  }
  
  @RequestMapping({"/toAppointment.act"})
  public ModelAndView toAppointment(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String regid = request.getParameter("regid");
    String username = request.getParameter("username");
    String sex = request.getParameter("sex");
    String askperson = request.getParameter("askperson");
    String age = request.getParameter("age");
    String index = request.getParameter("index");
    String url = "/kqdsFront/jh/appointment.jsp";
    ModelAndView model = new ModelAndView();
    model.addObject("usercode", usercode);
    model.addObject("regid", regid);
    model.addObject("username", username);
    model.addObject("sex", sex);
    model.addObject("askperson", askperson);
    model.addObject("age", age);
    model.addObject("index", index);
    model.setViewName(url);
    return model;
  }
  
  @RequestMapping({"/toZxSickOvertime.act"})
  public ModelAndView toZxSickOvertime(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String regid = request.getParameter("regid");
    String askperson = request.getParameter("askperson");
    String url = "/kqdsFront/jh/zx_sick_overtime.jsp";
    ModelAndView model = new ModelAndView();
    model.addObject("regid", regid);
    model.addObject("askperson", askperson);
    model.setViewName(url);
    return model;
  }
  
  @RequestMapping({"/toRadiologyTime.act"})
  public ModelAndView toRadiologyTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String url = "/kqdsFront/jh/time_statistics.jsp";
    ModelAndView model = new ModelAndView();
    model.setViewName(url);
    return model;
  }
}
