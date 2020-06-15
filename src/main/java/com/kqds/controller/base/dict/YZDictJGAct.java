package com.kqds.controller.base.dict;

import com.kqds.entity.base.KqdsOutprocessing;
import com.kqds.entity.base.KqdsOutprocessingType;
import com.kqds.entity.base.KqdsOutprocessingUnit;
import com.kqds.service.base.outProcessing.KQDS_OutProcessingLogic;
import com.kqds.service.base.outProcessing.KQDS_OutProcessing_TypeLogic;
import com.kqds.service.base.outProcessingUnit.KQDS_outProcessing_UnitLogic;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"YZDictJGAct"})
public class YZDictJGAct {
  private Logger logger = LoggerFactory.getLogger(YZDictJGAct.class);
  
  @Autowired
  private KQDS_outProcessing_UnitLogic unitLogic;
  
  @Autowired
  private KQDS_OutProcessing_TypeLogic typeLogic;
  
  @Autowired
  private KQDS_OutProcessingLogic itemLogic;
  
  @RequestMapping({"/getJgTreeAsync.act"})
  public void getJgTreeAsync(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String lv = request.getParameter("lv");
    String search = request.getParameter("search");
    String mrjgc = request.getParameter("mrjgc");
    String isAdd = null;
    List<JSONObject> listtree = new ArrayList<>();
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    try {
      if (YZUtility.isNullorEmpty(id)) {
        List<KqdsOutprocessingUnit> list = this.unitLogic.getJgcDictList4Back(search, mrjgc, isAdd, organization);
        for (int i = 0; i < list.size(); i++) {
          KqdsOutprocessingUnit base = list.get(i);
          int count = this.typeLogic.countJgTypeList(base.getCode(), search, isAdd, base.getOrganization());
          JSONObject obj = new JSONObject();
          obj.put("id", base.getCode());
          obj.put("pId", "0");
          obj.put("name", base.getName());
          obj.put("nocheck", "true");
          obj.put("isParent", Boolean.valueOf((count > 0)));
          listtree.add(obj);
        } 
      } 
      if (!YZUtility.isNullorEmpty(id) && !"1".equals(lv)) {
        List<KqdsOutprocessingType> nextlist = this.typeLogic.getjgTypeList(id, search, isAdd, organization);
        for (int j = 0; j < nextlist.size(); j++) {
          KqdsOutprocessingType next = nextlist.get(j);
          JSONObject obj = new JSONObject();
          obj.put("id", next.getTypeno());
          obj.put("pId", id);
          obj.put("name", next.getTypename());
          obj.put("nocheck", "true");
          obj.put("isParent", Boolean.valueOf(false));
          listtree.add(obj);
        } 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getSelectJgTreeAsync.act"})
  public void getSelectJgTreeAsync(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String lv = request.getParameter("lv");
    String search = request.getParameter("search");
    String mrjgc = request.getParameter("mrjgc");
    String isAdd = request.getParameter("isAdd");
    List<JSONObject> listtree = new ArrayList<>();
    String organization = ChainUtil.getCurrentOrganization(request);
    try {
      if (YZUtility.isNullorEmpty(id)) {
        List<KqdsOutprocessingUnit> list = this.unitLogic.getJgcDictList(search, mrjgc, isAdd, organization);
        for (int i = 0; i < list.size(); i++) {
          KqdsOutprocessingUnit base = list.get(i);
          int count = this.typeLogic.countJgTypeList(base.getCode(), search, isAdd, base.getOrganization());
          JSONObject obj = new JSONObject();
          obj.put("id", base.getCode());
          obj.put("pId", "0");
          obj.put("name", base.getName());
          obj.put("nocheck", "true");
          obj.put("isParent", Boolean.valueOf((count > 0)));
          listtree.add(obj);
        } 
      } 
      if (!YZUtility.isNullorEmpty(id) && "1".equals(lv)) {
        List<KqdsOutprocessing> itemlist = this.itemLogic.getjgItemListByType(id, search, isAdd, null);
        for (int k = 0; k < itemlist.size(); k++) {
          KqdsOutprocessing item = itemlist.get(k);
          JSONObject obj = new JSONObject();
          obj.put("id", item.getWjgxmbh());
          obj.put("pId", id);
          obj.put("name", item.getWjgmc());
          listtree.add(obj);
        } 
      } 
      if (!YZUtility.isNullorEmpty(id) && !"1".equals(lv)) {
        List<KqdsOutprocessingType> nextlist = this.typeLogic.getjgTypeList(id, search, isAdd, null);
        for (int j = 0; j < nextlist.size(); j++) {
          KqdsOutprocessingType next = nextlist.get(j);
          int count = this.itemLogic.countjgItemListByType(next.getTypeno(), search, isAdd, next.getOrganization());
          JSONObject obj = new JSONObject();
          obj.put("id", next.getTypeno());
          obj.put("pId", id);
          obj.put("name", next.getTypename());
          obj.put("nocheck", "true");
          obj.put("isParent", Boolean.valueOf((count > 0)));
          listtree.add(obj);
        } 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
}
