package com.kqds.controller.base.outProcessingUnit;

import com.kqds.entity.base.KqdsOutprocessingUnit;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.outProcessingUnit.KQDS_outProcessing_UnitLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_OutProcessing_UnitBackAct"})
public class KQDS_OutProcessing_UnitBackAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessing_UnitBackAct.class);
  
  @Autowired
  private KQDS_outProcessing_UnitLogic logic;
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/outProcessingUnit/list_kqds_outprocessing_unit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/outProcessingUnit/edit_kqds_outprocessing_unit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/outProcessingUnit/detail_kqds_outprocessing_unit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/outProcessingUnit/add_kqds_outprocessing_unit.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsOutprocessingUnit dp = new KqdsOutprocessingUnit();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      int num = this.logic.countByCode(dp);
      if (num >= 1)
        throw new Exception("加工厂编码重复, 请重新填写"); 
      int num2 = this.logic.countByOrgAndName(dp);
      if (num2 >= 1)
        throw new Exception("加工厂名称重复, 请重新填写"); 
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_UNIT, dp);
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_OUTPROCESSING_UNIT, dp, TableNameUtil.KQDS_OUTPROCESSING_UNIT, request);
      } else {
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_UNIT, dp);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING_UNIT, dp, TableNameUtil.KQDS_OUTPROCESSING_UNIT, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/checkCode.act"})
  public String checkCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    boolean result = true;
    try {
      String code = request.getParameter("code");
      String seqId = request.getParameter("seqId");
      int num = this.logic.checkCode(seqId, code, TableNameUtil.KQDS_OUTPROCESSING_UNIT);
      if (num > 0)
        result = false; 
      YZUtility.DEAL_SUCCESS_VALID(result, response);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      this.logic.delelteBySeqIds(seqId, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      KqdsOutprocessingUnit en = (KqdsOutprocessingUnit)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_UNIT, seqId);
      if (en == null)
        throw new Exception("数据不存在"); 
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String code = request.getParameter("code");
      String name = request.getParameter("name");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(code))
        map.put("code", code); 
      if (!YZUtility.isNullorEmpty(name))
        map.put("name", name); 
      map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject data = this.logic.selectWithPage(TableNameUtil.KQDS_OUTPROCESSING_UNIT, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
