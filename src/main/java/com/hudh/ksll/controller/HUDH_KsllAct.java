package com.hudh.ksll.controller;

import com.alibaba.fastjson.JSON;
import com.hudh.ksll.entity.KsllCollor;
import com.hudh.ksll.entity.KsllReplaceMent;
import com.hudh.ksll.service.IKsllColorService;
import com.hudh.ksll.service.IKsllReplaceMentService;
import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.net.URLDecoder;
import java.util.ArrayList;
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

@Controller
@RequestMapping({"/HUDH_KSllAct"})
public class HUDH_KsllAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_KsllAct.class);
  
  @Autowired
  private IKsllColorService ksllColorService;
  
  @Autowired
  private IKsllReplaceMentService ksllReplaceMentService;
  
  @RequestMapping({"/findAllKsllColorGoods.act"})
  public String findAllKsllColorGoods(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    String deptpart = request.getParameter("deptpart");
    String house = request.getParameter("house");
    String goodscode = request.getParameter("goodscode");
    String goodsname = request.getParameter("goodsname");
    String queryInput = request.getParameter("queryInput");
    String initialize = request.getParameter("initialize");
    dataMap.put("organization", ChainUtil.getCurrentOrganization(request));
    if (YZUtility.isNotNullOrEmpty(deptpart))
      dataMap.put("deptpart", deptpart); 
    if (YZUtility.isNotNullOrEmpty(house))
      dataMap.put("house", house); 
    if (YZUtility.isNotNullOrEmpty(goodscode))
      dataMap.put("goodscode", goodscode); 
    if (YZUtility.isNotNullOrEmpty(goodsname))
      dataMap.put("goodsname", goodsname); 
    if (YZUtility.isNotNullOrEmpty(queryInput))
      dataMap.put("queryInput", queryInput); 
    try {
      JSONObject list = new JSONObject();
      if (initialize.equals("0")) {
        list.put("rows", "");
        list.put("total", Integer.valueOf(0));
      } else {
        BootStrapPage bp = new BootStrapPage();
        BeanUtils.populate(bp, request.getParameterMap());
        list = this.ksllColorService.findAllKsllColorGoods(dataMap, bp);
      } 
      YZUtility.RETURN_OBJ(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findCkDept.act"})
  public String findCkDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    String organization = ChainUtil.getCurrentOrganization(request);
    dataMap.put("organization", organization);
    try {
      List<JSONObject> list = this.ksllColorService.findAllCKDept(dataMap);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findCkDeptTreeData.act"})
  public String findCkDeptTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    String organization = ChainUtil.getCurrentOrganization(request);
    dataMap.put("organization", organization);
    try {
      List<JSONObject> list = this.ksllColorService.findAllCKDept(dataMap);
      JSONArray jsonArray = new JSONArray();
      if (list != null && list.size() > 0) {
        JSONObject tempObj = new JSONObject();
        for (JSONObject obj : list) {
          tempObj = new JSONObject();
          tempObj.put("id", obj.get("seq_id"));
          tempObj.put("pId", "0");
          tempObj.put("name", obj.get("deptname"));
          jsonArray.add(tempObj);
        } 
      } 
      YZUtility.RETURN_LIST((List)jsonArray, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/saveKsllData.act"})
  public String saveKsllData(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String paramDetail = request.getParameter("paramDetail");
    String createtime = request.getParameter("createtime");
    String deptpart = request.getParameter("deptpart");
    String creatorId = request.getParameter("creatorId");
    String inremark = request.getParameter("inremark");
    String floor = request.getParameter("floor");
    KsllCollor ksllCollor = new KsllCollor();
    ksllCollor.setCreatetime(createtime);
    ksllCollor.setCreator(creatorId);
    ksllCollor.setDeptpart(deptpart);
    ksllCollor.setRemark(inremark);
    ksllCollor.setFloor(floor);
    try {
      this.ksllColorService.saveKsllData(ksllCollor, paramDetail, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findAllKsllColor.act"})
  public String findAllKsllColor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    String organization = ChainUtil.getCurrentOrganization(request);
    dataMap.put("organization", organization);
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String creatorname = request.getParameter("hzname");
    String deptpart = request.getParameter("deparent");
    String status = request.getParameter("status");
    String floor = request.getParameter("floor");
    if (YZUtility.isNotNullOrEmpty(starttime))
      dataMap.put("starttime", starttime); 
    if (YZUtility.isNotNullOrEmpty(endtime))
      dataMap.put("endtime", endtime); 
    if (YZUtility.isNotNullOrEmpty(creatorname))
      dataMap.put("creatorname", creatorname); 
    if (YZUtility.isNotNullOrEmpty(deptpart))
      dataMap.put("deptpart", deptpart); 
    if (YZUtility.isNotNullOrEmpty(status))
      dataMap.put("status", status); 
    if (YZUtility.isNotNullOrEmpty(floor))
      dataMap.put("floor", floor); 
    try {
      List<JSONObject> list = this.ksllColorService.findAllKsllColor(dataMap);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findKsllColorAndDetails.act"})
  public String findKsllColorAndDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String ksllCollorId = request.getParameter("ksllCollorId");
    try {
      JSONObject jo = this.ksllColorService.findKsllColorAndDetails(ksllCollorId);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findKsllColorAndDetail.act"})
  public String findKsllColorAndDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String ksllCollorId = request.getParameter("ksllCollorId");
    try {
      JSONObject jo = this.ksllColorService.findKsllColorAndDetail(ksllCollorId);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findCkHouse.act"})
  public String findCkHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    String organization = ChainUtil.getCurrentOrganization(request);
    dataMap.put("organization", organization);
    try {
      List<KqdsCkHouse> list = this.ksllColorService.findAllCKHouse(dataMap);
      JSONObject jo = new JSONObject();
      jo.put("houseList", JSON.toJSON(list));
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/ksllOutGoods.act"})
  public String ksllOutGoods(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      this.ksllColorService.ksllOutGoods(request, response);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findKsllColorDetailByparentid.act"})
  public String findKsllColorDetailByparentid(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentId = request.getParameter("parentId");
    try {
      List<JSONObject> list = new ArrayList<>();
      if (parentId != null && !parentId.equals("") && !parentId.equals("undefined"))
        list = this.ksllColorService.findKsllColorDetailByparentid(parentId); 
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateNumsById.act"})
  public String updateNumsById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    String id = request.getParameter("id");
    String nums = request.getParameter("newNums");
    try {
      if (YZUtility.isNotNullOrEmpty(id) && YZUtility.isNotNullOrEmpty(nums)) {
        dataMap.put("id", id);
        dataMap.put("nums", nums);
        this.ksllColorService.updateNumsById(dataMap);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteKsllNotCK.act"})
  public String deleteKsllNotCK(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      this.ksllColorService.deleteKsllNotCK(id);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/saveReplaceMentData.act"})
  public String saveReplaceMentData(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String paramDetail = request.getParameter("paramDetail");
    String createtime = request.getParameter("createtime");
    String deptpart = request.getParameter("deptpart");
    String creatorId = request.getParameter("creatorId");
    String inremark = request.getParameter("inremark");
    String sshouse = request.getParameter("sshouse");
    String type = request.getParameter("type");
    String floor = request.getParameter("floor");
    KsllReplaceMent ksllReplaceMent = new KsllReplaceMent();
    ksllReplaceMent.setCreatetime(createtime);
    ksllReplaceMent.setCreator(creatorId);
    ksllReplaceMent.setDeptpart(deptpart);
    ksllReplaceMent.setRemark(inremark);
    ksllReplaceMent.setSshouse(sshouse);
    ksllReplaceMent.setType(Integer.valueOf(type));
    ksllReplaceMent.setFloor(floor);
    try {
      this.ksllReplaceMentService.insertReplacement(ksllReplaceMent, paramDetail, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findReplaceMentListNoDelete.act"})
  public String findReplaceMentListNoDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    String organization = ChainUtil.getCurrentOrganization(request);
    dataMap.put("organization", organization);
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String creatorname = request.getParameter("hzname");
    String deptpart = request.getParameter("deparent");
    String status = request.getParameter("status");
    String floor = request.getParameter("type");
    if (YZUtility.isNotNullOrEmpty(starttime))
      dataMap.put("starttime", starttime); 
    if (YZUtility.isNotNullOrEmpty(endtime))
      dataMap.put("endtime", endtime); 
    if (YZUtility.isNotNullOrEmpty(creatorname))
      dataMap.put("creatorname", creatorname); 
    if (YZUtility.isNotNullOrEmpty(deptpart))
      dataMap.put("deptpart", deptpart); 
    if (YZUtility.isNotNullOrEmpty(status))
      dataMap.put("status", status); 
    if (YZUtility.isNotNullOrEmpty(floor))
      dataMap.put("floor", floor); 
    try {
      List<JSONObject> list = this.ksllReplaceMentService.findAllReplacementNoDelete(dataMap);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findReplaceMentList.act"})
  public String findReplaceMentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    String organization = ChainUtil.getCurrentOrganization(request);
    dataMap.put("organization", organization);
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String creatorname = request.getParameter("hzname");
    String deptpart = request.getParameter("deparent");
    String status = request.getParameter("status");
    String type = request.getParameter("type");
    String floor = request.getParameter("floor");
    if (YZUtility.isNotNullOrEmpty(starttime))
      dataMap.put("starttime", starttime); 
    if (YZUtility.isNotNullOrEmpty(endtime))
      dataMap.put("endtime", endtime); 
    if (YZUtility.isNotNullOrEmpty(creatorname))
      dataMap.put("creatorname", creatorname); 
    if (YZUtility.isNotNullOrEmpty(deptpart))
      dataMap.put("deptpart", deptpart); 
    if (YZUtility.isNotNullOrEmpty(status))
      dataMap.put("status", status); 
    if (YZUtility.isNotNullOrEmpty(type))
      dataMap.put("type", type); 
    if (YZUtility.isNotNullOrEmpty(floor))
      dataMap.put("floor", floor); 
    try {
      List<JSONObject> list = this.ksllReplaceMentService.findAllReplacement(dataMap);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findReplaceMentDetailByParentId.act"})
  public String findReplaceMentDetailByParentId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentId = request.getParameter("parentId");
    String status = request.getParameter("status");
    String type = request.getParameter("type");
    String organization = ChainUtil.getCurrentOrganization(request);
    try {
      Map<String, String> map = new HashMap<>();
      map.put("parentId", parentId);
      map.put("organization", organization);
      List<JSONObject> list = new ArrayList<>();
      if (type != null && status.equals("1") && type.equals("1")) {
        list = this.ksllReplaceMentService.findCkReplaceMentDetailByParendId(parentId);
      } else {
        list = this.ksllReplaceMentService.findDetailByParendId(map);
      } 
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateReplacementStatus.act"})
  public String updateReplacementStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String status = request.getParameter("status");
    String id = request.getParameter("id");
    String parentId = request.getParameter("parentId");
    String type = request.getParameter("type");
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("status", status);
    dataMap.put("id", id);
    dataMap.put("parentId", parentId);
    dataMap.put("organization", organization);
    String params = request.getParameter("paramDetail");
    List<JSONObject> jList = new ArrayList<>();
    if (params != null && !params.equals("")) {
      params = URLDecoder.decode(params, "UTF-8");
      JSONArray jArray = JSONArray.fromObject(params);
      jList = (List<JSONObject>)JSONArray.toCollection(jArray, JSONObject.class);
    } 
    try {
      this.ksllReplaceMentService.updateReplacementStatus(dataMap, jList, person, type);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      if (e.toString().contains("编号")) {
        String[] ex = e.toString().split(":");
        YZUtility.DEAL_ERROR(ex[1], false, e, response, this.logger);
      } else {
        YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
      } 
    } 
    return null;
  }
  
  @RequestMapping({"/selectAllGoodPhByGoodCode.act"})
  public String selectAllGoodPhByGoodCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String goodscode = request.getParameter("goodscode");
    String deptpart = request.getParameter("deptpart");
    Map<String, String> map = new HashMap<>();
    map.put("goodscode", goodscode);
    map.put("deptpart", deptpart);
    try {
      List<JSONObject> list = this.ksllColorService.selectAllGoodPhByGoodCode(map);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
