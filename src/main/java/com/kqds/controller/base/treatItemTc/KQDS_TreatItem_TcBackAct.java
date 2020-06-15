package com.kqds.controller.base.treatItemTc;

import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsTreatitemTc;
import com.kqds.entity.base.KqdsTreatitemTcType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.base.treatItemTc.KQDS_TreatItem_TcLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
@RequestMapping({"KQDS_TreatItem_TcBackAct"})
public class KQDS_TreatItem_TcBackAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_TreatItem_TcBackAct.class);
  
  @Autowired
  private KQDS_TreatItem_TcLogic logic;
  
  @Autowired
  private KQDS_TreatItemLogic treatlogic;
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItemTc/list_kqds_treatitem_tc.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String tcnameid = request.getParameter("tcnameid");
    String organization = request.getParameter("organization");
    String tctype = request.getParameter("tctype");
    String tcname = request.getParameter("tcname");
    ModelAndView mv = new ModelAndView();
    mv.addObject("tcnameid", tcnameid);
    mv.addObject("organization", organization);
    mv.addObject("tctype", tctype);
    mv.addObject("tcname", tcname);
    mv.setViewName("/kqds/treatItemTc/edit_kqds_treatitem_tc.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String tcnameid = request.getParameter("tcnameid");
    String organization = request.getParameter("organization");
    String tctype = request.getParameter("tctype");
    String tcname = request.getParameter("tcname");
    ModelAndView mv = new ModelAndView();
    mv.addObject("tcnameid", tcnameid);
    mv.addObject("organization", organization);
    mv.addObject("tctype", tctype);
    mv.addObject("tcname", tcname);
    mv.setViewName("/kqds/treatItemTc/detail_kqds_treatitem_tc.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItemTc/add_kqds_treatitem_tc.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTypeIndex.act"})
  public ModelAndView toTypeIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItemType/subindex.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTypeList.act"})
  public ModelAndView toTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItemType/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTypeEdit.act"})
  public ModelAndView toTypeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/treatItemType/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTypeNewAdd.act"})
  public ModelAndView toTypeNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItemType/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTypeLeft.act"})
  public ModelAndView toTypeLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/treatItemType/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertList4Back.act"})
  public String insertList4Back(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String tctype = request.getParameter("tctype");
      String tcname = request.getParameter("tcname");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      Map<String, String> map = new HashMap<>();
      map.put("name", tctype);
      map.put("parentid", "0");
      map.put("organization", organization);
      this.logic.newAddTc(tctype, tcname, map, request);
      JSONObject json = new JSONObject();
      map.put("tctype", tctype);
      map.put("tcname", tcname);
      BcjlUtil.LogBcjl(BcjlUtil.SAVE_TC, BcjlUtil.KQDS_TREATITEM_TC_TYPE, json, TableNameUtil.KQDS_TREATITEM_TC_TYPE, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/insertList.act"})
  public String insertList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String tctype = request.getParameter("tctype");
      String tcname = request.getParameter("tcname");
      String organization = ChainUtil.getCurrentOrganization(request);
      Map<String, String> map = new HashMap<>();
      map.put("name", tctype);
      map.put("parentid", "0");
      map.put("organization", organization);
      this.logic.newAddTc(tctype, tcname, map, request);
      JSONObject json = new JSONObject();
      map.put("tctype", tctype);
      map.put("tcname", tcname);
      BcjlUtil.LogBcjl(BcjlUtil.SAVE_TC, BcjlUtil.KQDS_TREATITEM_TC_TYPE, json, TableNameUtil.KQDS_TREATITEM_TC_TYPE, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/editList.act"})
  public String editList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String tcnameid = request.getParameter("tcnameid");
      String tcname = request.getParameter("tcname");
      if (YZUtility.isNullorEmpty(tcnameid))
        throw new Exception("tcnameid参数值为空"); 
      KqdsTreatitemTcType tctypeNameObj = (KqdsTreatitemTcType)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, tcnameid);
      if (tctypeNameObj == null)
        throw new Exception("根据tcnameid查询不到对应套餐记录"); 
      if (!tctypeNameObj.getName().equals(tcname)) {
        int count = this.logic.checkTc(tctypeNameObj.getParentid(), tcname, tctypeNameObj.getSeqId());
        if (count > 0)
          throw new Exception("该套餐类型下已存在该套餐名称！"); 
        tctypeNameObj.setName(tcname);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, tctypeNameObj);
      } 
      Map<String, String> map = new HashMap<>();
      map.put("tcnameid", tcnameid);
      List<KqdsTreatitemTc> en = (List<KqdsTreatitemTc>)this.logic.loadList(TableNameUtil.KQDS_TREATITEM_TC, map);
      if (en != null && en.size() > 0)
        for (KqdsTreatitemTc tc : en)
          this.logic.deleteSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, tc.getSeqId());  
      String listdata = request.getParameter("params");
      JSONArray jArray = JSONArray.fromObject(listdata);
      Collection collection = JSONArray.toCollection(jArray, KqdsTreatitemTc.class);
      Iterator<KqdsTreatitemTc> it = collection.iterator();
      KqdsTreatitemTc detail = new KqdsTreatitemTc();
      while (it.hasNext()) {
        detail = it.next();
        detail.setTcnameid(tcnameid);
        detail.setSeqId(YZUtility.getUUID());
        detail.setArrearmoney("0");
        detail.setVoidmoney("0");
        detail.setPaymoney(detail.getSubtotal());
        detail.setCreatetime(YZUtility.getCurDateTimeStr());
        detail.setCreateuser(person.getSeqId());
        detail.setOrganization(tctypeNameObj.getOrganization());
        KqdsTreatitem treatitem = this.treatlogic.getByTreatItemno(detail.getItemno());
        if (treatitem == null)
          throw new Exception("收费编号对应的收费项目不存在"); 
        if (1 == treatitem.getIsyjjitem().intValue())
          throw new Exception("预交金不能作为收费套餐项目"); 
        this.logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, detail);
      } 
      JSONObject json = new JSONObject();
      json.put("tcnameid", tcnameid);
      BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_TREATITEM_TC_TYPE, json, TableNameUtil.KQDS_TREATITEM_TC, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/openOrcloseTc.act"})
  public String openOrcloseTc(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String isopen = request.getParameter("isopen");
      String tcnameid = request.getParameter("tcnameid");
      KqdsTreatitemTcType en = (KqdsTreatitemTcType)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, tcnameid);
      en.setIsopen(Integer.valueOf(Integer.parseInt(isopen)));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, en);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String tctype = request.getParameter("tctype");
      String tcname = request.getParameter("tcname");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(tctype))
        map.put("tctype", tctype); 
      if (!YZUtility.isNullorEmpty(tcname))
        map.put("tcname", tcname); 
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      map.put("organization", organization);
      JSONObject data = this.logic.selectWithPage(TableNameUtil.KQDS_TREATITEM_TC, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPageByTctypeAndTcname.act"})
  public String selectPageByTctypeAndTcname(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String tcnameid = request.getParameter("tcnameid");
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject jobj = this.logic.selectWithPageBytctypeAndname(TableNameUtil.KQDS_TREATITEM_TC, bp, tcnameid);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectNoPageByTctypeAndTcname.act"})
  public String selectNoPageByTctypeAndTcname(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String tcnameid = request.getParameter("tcnameid");
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      int total = this.logic.selectTcxmCount(TableNameUtil.KQDS_TREATITEM_TC, tcnameid);
      List<JSONObject> list = this.logic.selectNoPageBytctypeAndname(TableNameUtil.KQDS_TREATITEM_TC, bp, tcnameid);
      JSONObject jobj = new JSONObject();
      jobj.put("total", Integer.valueOf(total));
      jobj.put("rows", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
