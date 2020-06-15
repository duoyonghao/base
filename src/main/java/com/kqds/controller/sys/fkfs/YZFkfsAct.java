package com.kqds.controller.sys.fkfs;

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZFkfs;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping({"YZFkfsAct"})
public class YZFkfsAct {
  private static Logger logger = LoggerFactory.getLogger(YZFkfsAct.class);
  
  @Autowired
  private YZFkfsLogic logic;
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqds/fkfs/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toDxflEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/fkfs/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      YZFkfs dp = new YZFkfs();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(dp.getPayname()))
        throw new Exception("名称不能为空"); 
      if (!YZUtility.isNullorEmpty(seqId)) {
        YZFkfs tmp = (YZFkfs)this.logic.loadObjSingleUUID(TableNameUtil.SYS_FKFS, seqId);
        if (tmp == null)
          throw new Exception("记录不存在"); 
        tmp.setPayname(dp.getPayname());
        tmp.setCreatetime(YZUtility.getCurDateTimeStr());
        tmp.setCreateuser(person.getSeqId());
        this.logic.updateSingleUUID(TableNameUtil.SYS_FKFS, tmp);
        SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_FKFS, dp, TableNameUtil.SYS_FKFS, request);
        JSONObject sysParaCost = this.logic.getCostField();
        request.getSession().setAttribute(SessionUtil.LOGIN_SYS_COSTFIELD, sysParaCost);
        JSONObject sysParaMember = this.logic.getMemberField();
        request.getSession().setAttribute(SessionUtil.LOGIN_SYS_MEMBERFIELD, sysParaMember);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateFlagBySeqIds.act"})
  public String updateFlagBySeqIds(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      String flag = request.getParameter("flag");
      if (!"0".equals(flag) && !"1".equals(flag))
        throw new Exception("状态标识不正确"); 
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      this.logic.updateFlagBySeqIds(seqId, flag, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      YZFkfs en = (YZFkfs)this.logic.loadObjSingleUUID(TableNameUtil.SYS_FKFS, seqId);
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
      JSONObject jobj = this.logic.selectWithPage(TableNameUtil.SYS_FKFS, bp, ChainUtil.getOrganizationFromUrlCanNull(request));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectNoPage.act"})
  public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String ismoney = request.getParameter("ismoney");
      String forcost = request.getParameter("forcost");
      String useflag = request.getParameter("useflag");
      Map<String, String> map = new HashMap<>();
      if (ismoney != null && !ismoney.equals(""))
        map.put("ismoney", ismoney); 
      if (forcost != null && !forcost.equals(""))
        map.put("forcost", forcost); 
      if (useflag != null && !useflag.equals(""))
        map.put("useflag", useflag); 
      List<JSONObject> list = this.logic.selectList(TableNameUtil.SYS_FKFS, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getFkfsList.act"})
  public String getFkfsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      map.put("useflag", "0");
      List<JSONObject> en = this.logic.selectList(TableNameUtil.SYS_FKFS, map);
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getMemberFkfsList.act"})
  public String getMemberFkfsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      List<JSONObject> en = this.logic.getMemberFkfsList(TableNameUtil.SYS_FKFS);
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
