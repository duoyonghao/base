package com.kqds.controller.base.tooth;

import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsToothDoc;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.tooth.KQDS_Tooth_DocLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
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
@RequestMapping({"KQDS_Tooth_DocAct"})
public class KQDS_Tooth_DocAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Tooth_DocAct.class);
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @Autowired
  private KQDS_Tooth_DocLogic logic;
  
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  
  @RequestMapping({"/toYmjlWin.act"})
  public ModelAndView toYmjlWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/jiagong/ymjlWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Hzjl.act"})
  public ModelAndView toBb_Hzjl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/huizhen/bb_hzjl.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsToothDoc dp = new KqdsToothDoc();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String schf = request.getParameter("schf");
      String uuid = YZUtility.getUUID();
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_TOOTH_DOC, dp);
        if (dp.getRegno() != null)
          dp.getRegno(); 
        if (!YZUtility.isNullorEmpty(dp.getDiseasesort()) && !YZUtility.isNullorEmpty(schf) && schf.equals("1")) {
          KqdsToothDoc en = (KqdsToothDoc)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_TOOTH_DOC, seqId);
          Map<String, String> map = new HashMap<>();
          map.put("usercode", en.getUsercode());
          List<KqdsUserdocument> userList = (List<KqdsUserdocument>)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
          if (userList == null)
            throw new Exception("患者不存在"); 
          KqdsUserdocument uentity = userList.get(0);
          KqdsVisit visit = new KqdsVisit();
          visit.setSeqId(YZUtility.getUUID());
          visit.setCreatetime(YZUtility.getCurDateTimeStr());
          visit.setCreateuser(person.getSeqId());
          visit.setOrganization(ChainUtil.getCurrentOrganization(request));
          visit.setUsercode(en.getUsercode());
          visit.setUsername(uentity.getUsername());
          visit.setSex(uentity.getSex());
          visit.setTelphone(uentity.getPhonenumber1());
          visit.setVisittime(dp.getDiseasesort().substring(0, 16));
          String hffl = this.dictLogic.getDictIdByNameAndParentCode("HFFL", "会诊");
          if (YZUtility.isNullorEmpty(hffl))
            throw new Exception("回访分类不存在"); 
          visit.setHffl(hffl);
          visit.setVisitor(person.getSeqId());
          visit.setHfyd(en.getDetaildesc());
          this.logic.saveSingleUUID(TableNameUtil.KQDS_VISIT, visit);
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, en, en.getUsercode(), TableNameUtil.KQDS_VISIT, request);
          PushUtil.saveTx4NewVisit(visit, request);
        } 
      } else {
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_TOOTH_DOC, dp);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_TOOTH_DOC, dp, dp.getUsercode(), TableNameUtil.KQDS_TOOTH_DOC, request);
        KqdsReg reg = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, dp.getRegno());
        if (!YZUtility.isNullorEmpty(reg.getAskperson()))
          this.userLogic.setUserDocAskPerson(request, dp.getUsercode(), reg.getAskperson()); 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("data", uuid);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/tianJiaHuiZhen.act"})
  public String tianJiaHuiZhen(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsToothDoc dp = new KqdsToothDoc();
      BeanUtils.populate(dp, request.getParameterMap());
      String regno = request.getParameter("regno");
      String usercode = request.getParameter("usercode");
      String usertype = request.getParameter("usertype");
      String toothtype = request.getParameter("toothtype");
      String yw1 = request.getParameter("yw1");
      String yw2 = request.getParameter("yw2");
      String yw3 = request.getParameter("yw3");
      String yw4 = request.getParameter("yw4");
      String uuid = YZUtility.getUUID();
      dp.setSeqId(uuid);
      dp.setRegno(regno);
      dp.setUsercode(usercode);
      dp.setUsertype(usertype);
      dp.setToothtype(toothtype);
      dp.setYw1(yw1);
      dp.setYw2(yw2);
      dp.setYw3(yw3);
      dp.setYw4(yw4);
      dp.setCreatetime(YZUtility.getCurDateTimeStr());
      dp.setCreateuser(person.getSeqId());
      dp.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.logic.saveSingleUUID(TableNameUtil.KQDS_TOOTH_DOC, dp);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_TOOTH_DOC, dp, dp.getUsercode(), TableNameUtil.KQDS_TOOTH_DOC, request);
      Map<String, String> map = new HashMap<>();
      map.put("regno", dp.getRegno());
      List<KqdsReceiveinfo> en = (List<KqdsReceiveinfo>)this.logic.loadList(TableNameUtil.KQDS_RECEIVEINFO, map);
      if (en != null && en.size() > 0) {
        KqdsReceiveinfo receive = en.get(0);
        receive.setAskstatus(Integer.valueOf(ConstUtil.RECEIVE_STATUS_1));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, receive);
        KqdsReg reg = (KqdsReg)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, dp.getRegno());
        if (!YZUtility.isNullorEmpty(reg.getAskperson()))
          this.userLogic.setUserDocAskPerson(request, dp.getUsercode(), reg.getAskperson()); 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("data", uuid);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      JSONObject jobj = new JSONObject();
      String ids = request.getParameter("ids");
      if (!YZUtility.isNullorEmpty(ids)) {
        String[] idArr = ids.split(",");
        byte b;
        int i;
        String[] arrayOfString1;
        for (i = (arrayOfString1 = idArr).length, b = 0; b < i; ) {
          String tmpid = arrayOfString1[b];
          if (!YZUtility.isNullorEmpty(tmpid)) {
            KqdsToothDoc en = (KqdsToothDoc)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_TOOTH_DOC, tmpid);
            this.logic.deleteSingleUUID(TableNameUtil.KQDS_TOOTH_DOC, tmpid);
            BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_TOOTH_DOC, en, en.getUsercode(), TableNameUtil.KQDS_TOOTH_DOC, request);
            jobj.put("retState", "0");
            jobj.put("retMsrg", "操作成功");
          } 
          b++;
        } 
      } else {
        String seqId = request.getParameter("seqId");
        if (YZUtility.isNullorEmpty(seqId))
          throw new Exception("主键为空或者null"); 
        KqdsToothDoc en = (KqdsToothDoc)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_TOOTH_DOC, seqId);
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_TOOTH_DOC, seqId);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_TOOTH_DOC, en, en.getUsercode(), TableNameUtil.KQDS_TOOTH_DOC, request);
        jobj.put("retState", "0");
        jobj.put("retMsrg", "操作成功");
      } 
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPageByRegno.act"})
  public String selectPageByRegno(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      String regno = request.getParameter("regno");
      if (!YZUtility.isNullorEmpty(regno))
        map.put("regno", regno); 
      String usercode = request.getParameter("usercode");
      if (!YZUtility.isNullorEmpty(usercode))
        map.put("usercode", usercode); 
      List<JSONObject> list = this.logic.selectWithPageByRegno(TableNameUtil.KQDS_TOOTH_DOC, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectHzjl.act"})
  public String selectHzjl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, String> map = new HashMap<>();
      String starttime = request.getParameter("starttime");
      if (!YZUtility.isNullorEmpty(starttime))
        map.put("starttime", starttime); 
      String endtime = request.getParameter("endtime");
      if (!YZUtility.isNullorEmpty(endtime))
        map.put("endtime", endtime); 
      String toothseat = request.getParameter("toothseat");
      if (!YZUtility.isNullorEmpty(toothseat))
        map.put("toothseat", toothseat); 
      String txstarttime = request.getParameter("txstarttime");
      if (!YZUtility.isNullorEmpty(txstarttime))
        map.put("txstarttime", txstarttime); 
      String txendtime = request.getParameter("txendtime");
      if (!YZUtility.isNullorEmpty(txendtime))
        map.put("txendtime", txendtime); 
      String createuser = request.getParameter("createuser");
      if (!YZUtility.isNullorEmpty(createuser))
        map.put("createuser", createuser); 
      String toothtype = request.getParameter("toothtype");
      if (!YZUtility.isNullorEmpty(toothtype))
        map.put("toothtype", toothtype); 
      String queryinput = request.getParameter("queryinput");
      if (!YZUtility.isNullorEmpty(queryinput))
        map.put("queryinput", queryinput); 
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
        organization = ChainUtil.getCurrentOrganization(request); 
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> list = this.logic.selectHzjl(TableNameUtil.KQDS_TOOTH_DOC, map, visualstaff, organization);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("会诊记录", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
