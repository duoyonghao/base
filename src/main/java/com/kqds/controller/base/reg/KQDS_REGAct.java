package com.kqds.controller.base.reg;

import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.jzmdType.KQDS_JzqkLogic;
import com.kqds.service.base.receiveinfo.KQDS_ReceiveInfoLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@RequestMapping({"KQDS_REGAct"})
public class KQDS_REGAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_REGAct.class);
  @Autowired
  private KQDS_REGLogic logic;
  @Autowired
  private KQDS_UserDocumentLogic userlogic;
  @Autowired
  private KQDS_CostOrderLogic costLogic;
  @Autowired
  private YZDictLogic dictLogic;
  @Autowired
  private KQDS_JzqkLogic jzqkLogic;
  @Autowired
  private KQDS_ReceiveInfoLogic receiveLogic;
  @Autowired
  private YZPersonLogic personLogic;
  
  @RequestMapping({"/toJrhzCenter.act"})
  public ModelAndView toJrhzCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/jdzx/jrhz_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJrhzCenter1.act"})
  public ModelAndView toJrhzCenter1(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.addObject("isyx", Integer.valueOf(1));
    mv.setViewName("/kqdsFront/index/jdzx/jrhz_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJrhzCenter2.act"})
  public ModelAndView toJrhzCenter2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.addObject("isyx", Integer.valueOf(2));
    mv.setViewName("/kqdsFront/index/jdzx/jrhz_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJdzxCenter.act"})
  public ModelAndView toJdzxCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/jdzx/jdzx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZxzxCenter.act"})
  public ModelAndView toZxzxCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/zxzx/zxzx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toYlzxCenter.act"})
  public ModelAndView toYlzxCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/ylzx/ylzx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEditRegRecesort.act"})
  public ModelAndView toEditRegRecesort(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String regSeqId = request.getParameter("regSeqId");
    String recesortvalue = request.getParameter("recesortvalue");
    ModelAndView mv = new ModelAndView();
    mv.addObject("regSeqId", regSeqId);
    mv.addObject("recesortvalue", recesortvalue);
    mv.setViewName("/kqdsFront/reg/chufuzhenModify.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEditRegReason.act"})
  public ModelAndView toEditRegReason(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/reg/editReason.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddReg.act"})
  public ModelAndView toAddReg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String orderno = request.getParameter("orderno");
    String netorderid = request.getParameter("netorderid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("orderno", orderno);
    mv.addObject("netorderid", netorderid);
    mv.setViewName("/kqdsFront/reg/reg_add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEditReg.act"})
  public ModelAndView toEditReg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String username = request.getParameter("username");
    ModelAndView mv = new ModelAndView();
    mv.addObject("username", username);
    mv.setViewName("/kqdsFront/reg/reg_edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toChufuzhenModify.act"})
  public ModelAndView toChufuzhenModify(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String regSeqId = request.getParameter("regSeqId");
    String recesortvalue = request.getParameter("recesortvalue");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("regSeqId", regSeqId);
    mv.addObject("recesortvalue", recesortvalue);
    mv.addObject("organization", organization);
    mv.setViewName("/kqdsFront/reg/chufuzhenModify.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUpdaeRegModify.act"})
  public ModelAndView toUpdaeRegModify(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String regSeqId = request.getParameter("regSeqId");
    String regsortvalue = request.getParameter("regsortvalue");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("regSeqId", regSeqId);
    mv.addObject("regsortvalue", regsortvalue);
    mv.addObject("organization", organization);
    mv.setViewName("/kqdsFront/reg/updateRegModify.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEditReason.act"})
  public ModelAndView toEditReason(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/reg/editReason.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJzcxCenter.act"})
  public ModelAndView toJzcxCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/jdzx/jzcx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKpxxcxCenter.act"})
  public ModelAndView toKpxxcxCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/jdzx/kpxxcx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDzQuery.act"})
  public ModelAndView toDzQuery(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/jdzx/dz_query.jsp");
    return mv;
  }
  
  @RequestMapping({"/chufuzhenModify.act"})
  public String chufuzhenModify(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      String recesort = request.getParameter("recesort");
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("seqId不能为空");
      }
      if (YZUtility.isNullorEmpty(recesort)) {
        throw new Exception("recesort不能为空");
      }
      KqdsReg reg = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
      if (reg == null) {
        throw new Exception("挂号记录不存在");
      }
      reg.setRecesort(recesort);
      this.logic.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateRegModify.act"})
  public String updateRegModify(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      String regsort = request.getParameter("regsort");
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("seqId不能为空");
      }
      if (YZUtility.isNullorEmpty(regsort)) {
        throw new Exception("recesort不能为空");
      }
      KqdsReg reg = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
      if (reg == null) {
        throw new Exception("挂号记录不存在");
      }
      reg.setRegsort(regsort);
      this.logic.updateSingleUUID(TableNameUtil.KQDS_REG, reg);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_CHANGE_KEFU, reg, regsort, TableNameUtil.KQDS_CHANGE_KEFU, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getLastestCostOrderInfo.act"})
  public String getLastestCostOrderInfo(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(usercode)) {
        throw new Exception("患者编号不能为空");
      }
      List<JSONObject> list = this.costLogic.getLastestCostOrderInfo(usercode);
      JSONObject jobj = new JSONObject();
      if ((list != null) && (list.size() > 0)) {
        jobj.put("data", list.get(0));
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getChuZhenIn24Hours.act"})
  public String getChuZhenIn24Hours(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(usercode)) {
        throw new Exception("患者编号不能为空");
      }
      List<JSONObject> list = this.logic.getChuZhenIn24Hours(usercode, request);
      
      JSONObject jobj = new JSONObject();
      if ((list != null) && (list.size() > 0)) {
        jobj.put("data", list.get(0));
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getLastestRegInfo.act"})
  public String getLastestRegInfo(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(usercode)) {
        throw new Exception("患者编号不能为空");
      }
      List<JSONObject> list = this.logic.getLastestRegInfo(usercode);
      JSONObject jobj = new JSONObject();
      if ((list != null) && (list.size() > 0)) {
        jobj.put("data", list.get(0));
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/isFirstReg.act"})
  public String isFirstReg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      String seqId = request.getParameter("seqId");
      boolean flag = false;
      Map<String, String> map = new HashMap();
      map.put("usercode", usercode);
      int regNum = this.logic.selectCount(TableNameUtil.KQDS_REG, map);
      if (YZUtility.isNullorEmpty(seqId))
      {
        if (regNum == 0) {
          flag = true;
        }
      }
      else
      {
        KqdsReg reg = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
        if (reg == null) {
          throw new Exception("挂号记录不存在！");
        }
        String syspara_recesort = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID);
        if (YZUtility.isStrInArrayEach(reg.getRecesort(), syspara_recesort)) {
          flag = true;
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/cancelReg.act"})
  public String cancelReg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String seqId = request.getParameter("seqId");
      String editreason = request.getParameter("editreason");
      KqdsReg reg = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
      if (reg == null) {
        throw new Exception("挂号记录不存在！");
      }
      reg.setBeforeeditreason(JSONObject.fromObject(reg).toString());
      reg.setEditreason(editreason);
      reg.setDel(Integer.valueOf(1));
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.CANCEL, BcjlUtil.KQDS_REG, reg, reg.getUsercode(), TableNameUtil.KQDS_REG, request);
      this.logic.cancelReg(reg, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkIfCanDelReg.act"})
  public String checkIfCanDelReg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      String regno = request.getParameter("regno");
      
      JSONObject jobj = new JSONObject();
      

      int num = this.jzqkLogic.countJzqkByRegNo(regno);
      if (num > 0)
      {
        jobj.put("data", Integer.valueOf(num));
        YZUtility.DEAL_SUCCESS(jobj, "该挂号单已经填写就诊情况，不能撤销！", response, logger);
        return null;
      }
      num = this.receiveLogic.countReceiveByRegNo(regno);
      if (num > 0)
      {
        jobj.put("data", Integer.valueOf(num));
        YZUtility.DEAL_SUCCESS(jobj, "该挂号单已接诊，不能撤销！", response, logger);
        return null;
      }
      Map<String, String> map = new HashMap();
      map.put("usercode", usercode);
      map.put("regno", regno);
      int count = this.logic.selectCount(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
      if (count > 0)
      {
        jobj.put("data", Integer.valueOf(count));
        YZUtility.DEAL_SUCCESS(jobj, "该患者挂号已开单，不能撤销！", response, logger);
        return null;
      }
      jobj.put("data", Integer.valueOf(0));
      YZUtility.DEAL_SUCCESS(jobj, "该挂号单可以撤销！", response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsReg dp = new KqdsReg();
      dp.setJh(1);
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      List<JSONObject> list = new ArrayList();
      if ((YZUtility.isNullorEmpty(dp.getAskperson())) && (YZUtility.isNullorEmpty(dp.getDoctor()))) {
        throw new Exception("挂号时咨询和医生至少选一个！");
      }
      Map<String, String> map1 = new HashMap();
      map1.put("usercode", dp.getUsercode());
      List<KqdsUserdocument> userlist = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map1);
      if (userlist.size() == 0) {
        throw new Exception("患者信息不存在，患者编号为：" + dp.getUsercode());
      }
      KqdsUserdocument userdoc = (KqdsUserdocument)userlist.get(0);
      
      dp.setUsername(userdoc.getUsername());
      if (!YZUtility.isNullorEmpty(seqId))
      {
        KqdsReg regold = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
        if (regold == null) {
          throw new Exception("该挂号记录不存在，无法修改！");
        }
        if (1 == regold.getDel().intValue()) {
          throw new Exception("该挂号记录已撤销，无法修改！");
        }
        dp.setBeforeeditreason(JSONObject.fromObject(regold).toString());
        this.logic.updateReg(dp, regold, userdoc, person, request);
        Map<String, String> netmap = new HashMap();
        netmap.put("regno", dp.getSeqId());
        if (ConstUtil.USER_TYPE_1 == userdoc.getType().intValue())
        {
          List<KqdsNetOrder> netlist = (List)this.logic.loadList(TableNameUtil.KQDS_NET_ORDER, netmap);
          for (KqdsNetOrder netorder : netlist)
          {
            netorder.setRegdept(dp.getRegdept());
            netorder.setOrderperson(dp.getDoctor());
            netorder.setAskperson(dp.getAskperson());
            netorder.setDaoyi(dp.getCreateuser());
            
            this.logic.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
          }
        }
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_REG, dp, dp.getUsercode(), TableNameUtil.KQDS_REG, request);
      }
      else
      {
        String czIdStrs = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID);
        if (YZUtility.isStrInArrayEach(dp.getRecesort(), czIdStrs))
        {
          int count = this.logic.countCzReg(dp.getUsercode(), czIdStrs);
          if (count > 0)
          {
            JSONObject retrunObj = new JSONObject();
            retrunObj.put("retState", Integer.valueOf(ConstUtil.REG_CHECK_100));
            
            retrunObj.put("retMsrg", "该患者已挂过初诊，请重新选择就诊分类。");
            PrintWriter pw = response.getWriter();
            pw.println(retrunObj.toString());
            pw.flush();
            return null;
          }
        }
        dp.setSeqId(YZUtility.getUUID());
        dp.setStatus(Integer.valueOf(ConstUtil.REG_STATUS_0));
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        list = this.logic.updateRegInsert(dp, userdoc, person, request);
      }
      if (list != null)
      {
        JSONObject json = new JSONObject();
        json.put("list", list);
        YZUtility.DEAL_SUCCESS(json, null, response, logger);
      }
      else
      {
        YZUtility.DEAL_SUCCESS(null, null, response, logger);
      }
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      KqdsReg en = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
      if (en == null) {
        throw new Exception("数据不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectToDayNewDetail.act"})
  public String selectToDayNewDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      JSONObject jobj = this.logic.selectToDayNewDetail(usercode, organization);
      jobj.has("askperson");
      

      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDzlNopage.act"})
  public String selectDzlNopage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String status = request.getParameter("status");
      String querytype = request.getParameter("querytype");
      String searchValue = request.getParameter("searchValue");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      YZPerson person = SessionUtil.getLoginPerson(request);
      int statusInt = 0;
      if ((status != null) && (!"".equals(status))) {
        statusInt = Integer.parseInt(status);
      }
      String organization = ChainUtil.getCurrentOrganization(request);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      JSONObject list = this.logic.selectDzlmenu(TableNameUtil.KQDS_REG, person, sortName, sortOrder, statusInt, querytype, searchValue, visualstaff, organization, request, bp);
      










      list.put("offset", Integer.valueOf(bp.getOffset()));
      
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectJzqkNopage.act"})
  public String selectJzqkNopage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String ghsj = request.getParameter("ghsj");
      String ghsj2 = request.getParameter("ghsj2");
      String regdept = request.getParameter("regdept");
      String askperson = request.getParameter("askperson");
      String doctor = request.getParameter("doctor");
      String usercodeorname = request.getParameter("usercodeorname");
      String reggoal = request.getParameter("reggoal");
      String jzmd = request.getParameter("jzmd");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      
      String isfz = request.getParameter("isfz");
      String fzsj = request.getParameter("fzsj");
      String fzsj2 = request.getParameter("fzsj2");
      Map<String, String> map = new HashMap();
      map.put("del", ConstUtil.REG_DELETE_0);
      if (!YZUtility.isNullorEmpty(ghsj)) {
        map.put("ghsj", ghsj);
      }
      if (!YZUtility.isNullorEmpty(ghsj2)) {
        map.put("ghsj2", ghsj2);
      }
      if (!YZUtility.isNullorEmpty(regdept)) {
        map.put("regdept", regdept);
      }
      if (!YZUtility.isNullorEmpty(doctor)) {
        map.put("doctor", doctor);
      }
      if (!YZUtility.isNullorEmpty(askperson)) {
        map.put("askperson", askperson);
      }
      if (!YZUtility.isNullorEmpty(usercodeorname)) {
        map.put("usercodeorname", usercodeorname);
      }
      if (!YZUtility.isNullorEmpty(reggoal)) {
        map.put("reggoal", reggoal);
      }
      if (!YZUtility.isNullorEmpty(jzmd)) {
        map.put("jzmd", jzmd);
      }
      if (!YZUtility.isNullorEmpty(isfz)) {
        map.put("isfz", isfz);
      }
      if (!YZUtility.isNullorEmpty(fzsj)) {
        map.put("fzsj", fzsj);
      }
      if (!YZUtility.isNullorEmpty(fzsj2)) {
        map.put("fzsj2", fzsj2);
      }
      if (!YZUtility.isNullorEmpty(sortName))
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      }
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject list = this.logic.selectJzqk(TableNameUtil.KQDS_REG, person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request), bp);
      
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectZhcxNopage.act"})
  public String selectZhcxNopage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      String regdept = request.getParameter("regdept");
      String doctorSearch = request.getParameter("doctorSearch");
      String askpersonSearch = request.getParameter("askpersonSearch");
      
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String searchValue = request.getParameter("searchValue");
      String ifmedrecord = request.getParameter("ifmedrecord");
      
      String importantSearch = request.getParameter("importantSearch");
      String devchannelSearch = request.getParameter("devchannelSearch");
      String nexttype1 = request.getParameter("nexttype1");
      
      String ageSearch = request.getParameter("ageSearch");
      String firstaskperson = request.getParameter("firstAskperson");
      String regsort = request.getParameter("regsort");
      String cjstatus = request.getParameter("cjstatus");
      
      String gongju = request.getParameter("gongju");
      
      String organization = ChainUtil.getOrganizationFromUrl(request);
      String recesort = request.getParameter("recesort");
      
      String type = request.getParameter("type");
      
      String devItem = request.getParameter("devItem");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      

      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(regdept)) {
        map.put("regdept", regdept);
      }
      if (!YZUtility.isNullorEmpty(doctorSearch)) {
        map.put("doctor", doctorSearch);
      }
      if (!YZUtility.isNullorEmpty(askpersonSearch)) {
        map.put("askperson", askpersonSearch);
      }
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(searchValue)) {
        map.put("searchValue", searchValue);
      }
      if (!YZUtility.isNullorEmpty(ifmedrecord)) {
        map.put("ifmedrecord", ifmedrecord);
      }
      if (!YZUtility.isNullorEmpty(importantSearch)) {
        map.put("important", importantSearch);
      }
      if (!YZUtility.isNullorEmpty(devchannelSearch)) {
        map.put("devchannel", devchannelSearch);
      }
      if (!YZUtility.isNullorEmpty(nexttype1)) {
        map.put("nexttype", nexttype1);
      }
      if (!YZUtility.isNullorEmpty(ageSearch)) {
        map.put("ageSearch", ageSearch);
      }
      if (!YZUtility.isNullorEmpty(regsort)) {
        map.put("regsort", regsort);
      }
      if (!YZUtility.isNullorEmpty(cjstatus)) {
        map.put("cjstatus", cjstatus);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("gongju", gongju);
      }
      if (!YZUtility.isNullorEmpty(recesort)) {
        map.put("recesort", recesort);
      }
      if (!YZUtility.isNullorEmpty(type)) {
        map.put("type", type);
      }
      if (!YZUtility.isNullorEmpty(devItem)) {
        map.put("devItem", devItem);
      }
      if (!YZUtility.isNullorEmpty(firstaskperson)) {
        map.put("firstaskperson", firstaskperson);
      }
      if (!YZUtility.isNullorEmpty(sortName))
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      }
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<YZDict> jzflDict = this.dictLogic.getListByParentCodeALL(DictUtil.JZFL, ChainUtil.getCurrentOrganization(request));
      JSONObject data = this.logic.selectJzcx(bp, TableNameUtil.KQDS_REG, person, map, jzflDict, organization, visualstaff, request, flag);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("接诊查询", fieldArr, fieldnameArr, data.getJSONArray("rows"), response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectToday.act"})
  public String selectToday(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String doctorSearch = request.getParameter("doctorSearch");
      String askpersonSearch = request.getParameter("askpersonSearch");
      String searchValue = request.getParameter("searchValue");
      String regsort = request.getParameter("regsort");
      String cjstatus = request.getParameter("cjstatus");
      String recesort = request.getParameter("recesort");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String createuserSearch = request.getParameter("createuserSearch");
      String shouli = request.getParameter("shouli");
      String gongju = request.getParameter("gongju");
      String devchannel = request.getParameter("devchannel");
      String nexttype = request.getParameter("nexttype");
      String ywhf = request.getParameter("ywhf");
      String customer = request.getParameter("customer");
      String depttype = request.getParameter("depttype");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        map.put("starttime", starttime);
      }
      else
      {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        map.put("starttime", time);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        map.put("endtime", endtime);
      }
      else
      {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        map.put("endtime", time);
      }
      if (!YZUtility.isNullorEmpty(createuserSearch)) {
        map.put("createuserSearch", createuserSearch);
      }
      if (!YZUtility.isNullorEmpty(doctorSearch)) {
        map.put("doctorSearch", doctorSearch);
      }
      if (!YZUtility.isNullorEmpty(askpersonSearch)) {
        map.put("askpersonSearch", askpersonSearch);
      }
      if (!YZUtility.isNullorEmpty(searchValue)) {
        map.put("searchValue", searchValue);
      }
      if (!YZUtility.isNullorEmpty(regsort)) {
        map.put("regsort", regsort);
      }
      if (!YZUtility.isNullorEmpty(cjstatus)) {
        map.put("cjstatus", cjstatus);
      }
      if (!YZUtility.isNullorEmpty(recesort)) {
        map.put("recesort", recesort);
      }
      if (!YZUtility.isNullorEmpty(shouli)) {
        map.put("shouli", shouli);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("gongju", gongju);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(ywhf)) {
        map.put("ywhf", ywhf);
      }
      if (!YZUtility.isNullorEmpty(customer)) {
        map.put("customer", customer);
      }
      if (!YZUtility.isNullorEmpty(sortName)) {
        map.put("sortName", sortName);
      }
      if (!YZUtility.isNullorEmpty(sortOrder)) {
        map.put("sortOrder", sortOrder);
      }
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(organization)) {
        map.put("organization", ChainUtil.getCurrentOrganization(request));
      } else {
        map.put("organization", organization);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg(depttype);
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject list = this.logic.selectToday("KQDS_REG", person, map, visualstaff, visualstaffwd, bp, flag);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("今日患者", fieldArr, fieldnameArr, list.getJSONArray("rows"), response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDzQuery.act"})
  public String selectDzQuery(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String doctorSearch = request.getParameter("doctorSearch");
      String askpersonSearch = request.getParameter("askperson");
      String searchValue = request.getParameter("searchValue");
      String regsort = request.getParameter("regsort");
      String cjstatus = request.getParameter("cjstatus");
      String recesort = request.getParameter("recesort");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String createuserSearch = request.getParameter("createuserSearch");
      String shouli = request.getParameter("shouli");
      String gongju = request.getParameter("gongju");
      String devchannel = request.getParameter("devchannel");
      String nexttype = request.getParameter("nexttype");
      String depttype = request.getParameter("depttype");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      String organization = request.getParameter("organization");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(createuserSearch)) {
        map.put("createuserSearch", createuserSearch);
      }
      if (!YZUtility.isNullorEmpty(doctorSearch)) {
        map.put("doctorSearch", doctorSearch);
      }
      if (!YZUtility.isNullorEmpty(askpersonSearch)) {
        map.put("askpersonSearch", askpersonSearch);
      }
      if (!YZUtility.isNullorEmpty(searchValue)) {
        map.put("searchValue", searchValue);
      }
      if (!YZUtility.isNullorEmpty(regsort)) {
        map.put("regsort", regsort);
      }
      if (!YZUtility.isNullorEmpty(cjstatus)) {
        map.put("cjstatus", cjstatus);
      }
      if (!YZUtility.isNullorEmpty(recesort)) {
        map.put("recesort", recesort);
      }
      if (!YZUtility.isNullorEmpty(shouli)) {
        map.put("shouli", shouli);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("gongju", gongju);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(sortName))
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      }
      if (YZUtility.isNullorEmpty(organization)) {
        map.put("organization", ChainUtil.getCurrentOrganization(request));
      } else {
        map.put("organization", organization);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg(depttype);
      JSONObject list = this.logic.selectDzQuery("KQDS_REG", person, map, visualstaff, visualstaffwd, bp);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("到诊患者", fieldArr, fieldnameArr, list.getJSONArray("rows"), response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectWithNopage.act"})
  public String selectWithNopage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String searchField = request.getParameter("searchField");
      String searchValue = request.getParameter("searchValue");
      
      List<JSONObject> listreg = new ArrayList();
      List<JSONObject> list = new ArrayList();
      if ((searchField != null) && (searchValue != null) && (!"".equals(searchValue)) && (!"".equals(searchField)))
      {
        Map<String, String> map = new HashMap();
        map.put("isdelete", "0");
        map.put(searchField, searchValue);
        
        String visualstaff = SessionUtil.getVisualstaff(request);
        list = this.userlogic.selectWithNopage(TableNameUtil.KQDS_USERDOCUMENT, map, "0", visualstaff, person);
      }
      if ((list != null) && (list.size() > 0))
      {
        StringBuffer usercodes = new StringBuffer();
        for (JSONObject user : list)
        {
          usercodes.append("'");
          usercodes.append(user.getString("usercode"));
          usercodes.append("'");
          usercodes.append(",");
        }
        String usercodeS = usercodes.toString().substring(0, usercodes.toString().length() - 1);
        listreg = this.logic.selectByusercode(TableNameUtil.KQDS_REG, usercodeS);
      }
      YZUtility.RETURN_LIST(listreg, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectWithNopageReg.act"})
  public String selectWithNopageReg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String searchField = request.getParameter("searchField");
      String searchValue = request.getParameter("searchValue");
      
      List<JSONObject> listreg = new ArrayList();
      List<JSONObject> list = new ArrayList();
      if ((searchField != null) && (searchValue != null) && (!"".equals(searchValue)) && (!"".equals(searchField)))
      {
        Map<String, String> map = new HashMap();
        map.put("isdelete", "0");
        map.put("searchValue", searchValue);
        
        String visualstaff = SessionUtil.getVisualstaff(request);
        list = this.userlogic.selectWithNopageReg(TableNameUtil.KQDS_USERDOCUMENT, map, "0", visualstaff, person);
      }
      if ((list != null) && (list.size() > 0))
      {
        StringBuffer usercodes = new StringBuffer();
        for (JSONObject user : list)
        {
          usercodes.append("'");
          usercodes.append(user.getString("usercode"));
          usercodes.append("'");
          usercodes.append(",");
        }
        String usercodeS = usercodes.toString().substring(0, usercodes.toString().length() - 1);
        listreg = this.logic.selectByusercode(TableNameUtil.KQDS_REG, usercodeS);
      }
      YZUtility.RETURN_LIST(listreg, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectOneByregno.act"})
  public String selectOneByregno(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      
      KqdsReg en = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, seqId);
      List<KqdsReg> list = new ArrayList();
      list.add(en);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountBB.act"})
  public String selectCountBB(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      List<KqdsReg> list = this.logic.getCountTj(TableNameUtil.KQDS_REG, map, organization);
      JSONObject jobj = new JSONObject();
      jobj.put("rows", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountBBQylr.act"})
  public String selectCountBBQylr(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      int year = Integer.parseInt(starttime.substring(0, 4));
      int month = Integer.parseInt(starttime.substring(starttime.length() - 2, starttime.length()));
      int days = YZUtility.getDaysByYearMonth(year, month);
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      List<Object> listxz = new ArrayList();
      List<Object> listAll = new ArrayList();
      
      listAll = this.logic.getCountQylrAll(TableNameUtil.KQDS_REG, year, month, days, ChainUtil.getOrganizationFromUrl(request));
      
      listxz = this.logic.getCountQylrNew(TableNameUtil.KQDS_REG, year, month, days, ChainUtil.getOrganizationFromUrl(request), request);
      
      int[] all = (int[])listAll.get(1);
      
      int[] xz = (int[])listxz.get(1);
      JSONObject jobj = new JSONObject();
      jobj.put("listAll", listAll);
      jobj.put("listxz", listxz);
      jobj.put("all", Integer.valueOf(all[0]));
      jobj.put("xz", Integer.valueOf(xz[0]));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountCjl.act"})
  public String selectCountCjl(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String ghfl = request.getParameter("ghfl");
      String jzfl = request.getParameter("jzfl");
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      
      String[] datearray = YZUtility.getBetweenDatesArray(starttime, endtime);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      Double[] cjlarray = new Double[datearray.length];
      for (int i = 0; i < datearray.length; i++)
      {
        Double cjl = this.logic.getcjl(datearray[i], ghfl, jzfl, visualstaff, ChainUtil.getOrganizationFromUrl(request));
        cjlarray[i] = cjl;
      }
      JSONObject jobj = new JSONObject();
      jobj.put("datearray", datearray);
      jobj.put("cjlarray", cjlarray);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountCjlTable.act"})
  public String selectCountCjlTable(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String ghfl = request.getParameter("ghfl");
      
      String jzfl = request.getParameter("jzfl");
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      List<Object> list = new ArrayList();
      
      String[] datearray = YZUtility.getBetweenDatesArray(starttime, endtime);
      
      int[] czarray = new int[datearray.length];
      
      int[] czcjarray = new int[datearray.length];
      
      String[] cjlarray = new String[datearray.length];
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      int allnum = 0;int allcjnum = 0;
      String allcjv = "0";
      for (int i = 0; i < datearray.length; i++)
      {
        int cznum = this.logic.getCountQylrNew(TableNameUtil.KQDS_REG, datearray[i], ghfl, jzfl, visualstaff, ChainUtil.getOrganizationFromUrl(request));
        int czcjnum = this.logic.getCountQylrAll(TableNameUtil.KQDS_REG, datearray[i], ghfl, jzfl, visualstaff, ChainUtil.getOrganizationFromUrl(request));
        if ((czcjnum == 0) || (cznum == 0))
        {
          cjlarray[i] = "0";
        }
        else
        {
          float num = czcjnum / cznum * 100.0F;
          DecimalFormat df = new DecimalFormat("0.00");
          cjlarray[i] = df.format(num);
        }
        czarray[i] = cznum;
        czcjarray[i] = czcjnum;
        allnum += cznum;
        allcjnum += czcjnum;
      }
      list.add(datearray);
      list.add(czarray);
      list.add(czcjarray);
      list.add(cjlarray);
      list.add(Integer.valueOf(allnum));
      list.add(Integer.valueOf(allcjnum));
      if ((allcjnum == 0) || (allnum == 0))
      {
        allcjv = "0";
      }
      else
      {
        float num = allcjnum / allnum * 100.0F;
        DecimalFormat df = new DecimalFormat("0.00");
        allcjv = df.format(num);
      }
      list.add(allcjv);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectExistByUsercode.act"})
  public String selectExistByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String status = "未挂号";
      String usercode = request.getParameter("usercode");
      if ((usercode != null) && (usercode != ""))
      {
        Map<String, String> map = new HashMap();
        map.put("usercode", usercode);
        String createtime = this.logic.selectExistByUsercode(map);
        if ((createtime != null) && (!createtime.equals("")))
        {
          String date = YZUtility.getDateStr(new Date());
          
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          calendar.add(5, -1);
          Date dBefore = calendar.getTime();
          String date1 = YZUtility.getDateStr(dBefore);
          if ((createtime.substring(0, 10).equals(date)) || (createtime.substring(0, 10).equals(date1)))
          {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateStart = format.parse(createtime);
            int endDay = (int)(new Date().getTime() / 1000L);
            int startDay = (int)(dateStart.getTime() / 1000L);
            if (endDay - startDay <= 129600) {
              status = "已挂号";
            } else {
              status = "未挂号";
            }
          }
          else
          {
            status = "未挂号";
          }
        }
        else
        {
          status = "未挂号";
        }
      }
      else
      {
        status = "未挂号";
      }
      JSONObject json = new JSONObject();
      json.put("status", status);
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
}
