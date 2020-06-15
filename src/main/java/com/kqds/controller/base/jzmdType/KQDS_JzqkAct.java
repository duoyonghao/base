package com.kqds.controller.base.jzmdType;

import com.kqds.controller.base.hospitalOrder.KQDS_Hospital_OrderAct;
import com.kqds.entity.base.KqdsJzmdType;
import com.kqds.entity.base.KqdsJzqk;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.jzmdType.KQDS_Jzmd_TypeLogic;
import com.kqds.service.base.jzmdType.KQDS_JzqkLogic;
import com.kqds.service.base.visit.KQDS_VisitLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
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
@RequestMapping({"KQDS_JzqkAct"})
public class KQDS_JzqkAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_Hospital_OrderAct.class);
  @Autowired
  private YZDictLogic dictLogic;
  @Autowired
  private KQDS_JzqkLogic logic;
  @Autowired
  private KQDS_UserDocumentLogic userDocLogic;
  @Autowired
  private KQDS_VisitLogic logicv;
  @Autowired
  private KQDS_Jzmd_TypeLogic logictype;
  
  @RequestMapping({"/toJzqk.act"})
  public ModelAndView toJzqk(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/reg/jzqk.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTx.act"})
  public ModelAndView toTx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/reg/jzqk_tx.jsp");
    return mv;
  }
  
  @RequestMapping({"/editState.act"})
  public String editState(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsJzqk dp = new KqdsJzqk();
      BeanUtils.populate(dp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      map.put("seq_id", dp.getSeqId());
      
      KqdsJzqk jzqkDB = (KqdsJzqk)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_JZQK, dp.getSeqId());
      if (jzqkDB == null) {
        throw new Exception("就诊情况记录不存在");
      }
      if ((dp.getReggoal() != null) && (!dp.getReggoal().equals(""))) {
        jzqkDB.setReggoal(dp.getReggoal());
      }
      if ((dp.getJzmd() != null) && (!dp.getJzmd().equals(""))) {
        jzqkDB.setJzmd(dp.getJzmd());
      }
      if ((dp.getRemark() != null) && (!dp.getRemark().equals(""))) {
        jzqkDB.setRemark(dp.getRemark());
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      if (YZUtility.isNotNullOrEmpty(jzqkDB.getFzdata())) {
        this.logicv.deleteBySeqId(jzqkDB.getSeqId(), request);
      }
      StringBuffer sqlbf = new StringBuffer();
      sqlbf.append(" select txjzmd,yxday from " + TableNameUtil.KQDS_JZMD_TYPE);
      sqlbf.append(" where jzmd = '" + dp.getReggoal() + "' ");
      sqlbf.append(" and jzmdchildname = '" + dp.getJzmd() + "' ");
      sqlbf.append(" and organization = '" + organization + "' ");
      sqlbf.append(" and yxday > 0 ");
      
      List<KqdsJzmdType> jzmdlist = this.logictype.selectTxList(dp.getReggoal(), dp.getJzmd(), organization);
      if (jzmdlist.size() > 0)
      {
        KqdsJzmdType jzmd = (KqdsJzmdType)jzmdlist.get(0);
        
        YZDict jzmdtx = (YZDict)this.logic.loadObjSingleUUID(TableNameUtil.SYS_DICT, jzmd.getTxjzmd());
        if (jzmdtx == null) {
          throw new Exception("字典没有就诊提醒");
        }
        KqdsReg reg = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, jzqkDB.getRegno());
        if (reg == null) {
          throw new Exception("就诊情况对应的挂号记录不存在");
        }
        Date date = YZUtility.getDayAfter(jzqkDB.getCreatetime(), jzmd.getYxday().intValue());
        jzqkDB.setFzdata(YZUtility.getDateStr(date));
        jzqkDB.setIstx(Integer.valueOf(1));
        
        KqdsUserdocument uentity = this.userDocLogic.getSingleUserByUsercode(reg.getUsercode());
        if (uentity == null) {
          throw new Exception("患者不存在");
        }
        PushUtil.saveTx4Jzqk(jzqkDB, reg, request);
        
        KqdsVisit visit = new KqdsVisit();
        visit.setSeqId(jzqkDB.getSeqId() + "_" + System.currentTimeMillis());
        visit.setCreatetime(YZUtility.getCurDateTimeStr());
        visit.setCreateuser(person.getSeqId());
        visit.setOrganization(organization);
        visit.setUsercode(reg.getUsercode());
        visit.setUsername(reg.getUsername());
        visit.setSex(uentity.getSex());
        visit.setTelphone(uentity.getPhonenumber1());
        visit.setVisittime(YZUtility.getDateStr(date) + ConstUtil.HOUR_START);
        String hffl = this.dictLogic.getDictIdByNameAndParentCode("HFFL", "病程提醒");
        if (YZUtility.isNullorEmpty(hffl)) {
          throw new Exception("回访分类不存在");
        }
        visit.setHffl(hffl);
        visit.setVisitor(person.getSeqId());
        visit.setHfyd("提醒患者：" + jzmdtx.getDictName());
        this.logic.saveSingleUUID(TableNameUtil.KQDS_VISIT, visit);
        
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, visit, visit.getUsercode(), TableNameUtil.KQDS_VISIT, request);
        
        PushUtil.saveTx4NewVisit(visit, request);
      }
      this.logic.updateSingleUUID(TableNameUtil.KQDS_JZQK, jzqkDB);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectJzTx.act"})
  public String selectJzTx(HttpServletRequest request, HttpServletResponse response)
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
      
      String isfz = request.getParameter("isfz");
      String fzsj = request.getParameter("fzsj");
      String fzsj2 = request.getParameter("fzsj2");
      
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      map.put("del", "0");
      if (!YZUtility.isNullorEmpty(ghsj)) {
        map.put("ghsj", ghsj + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(ghsj2)) {
        map.put("ghsj2", ghsj2 + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(regdept)) {
        map.put("regdept", regdept);
      }
      if (!YZUtility.isNullorEmpty(askperson)) {
        map.put("askperson", askperson);
      }
      if (!YZUtility.isNullorEmpty(doctor)) {
        map.put("doctor", doctor);
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
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> list = this.logic.selectJzqk(TableNameUtil.KQDS_REG, person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request));
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
