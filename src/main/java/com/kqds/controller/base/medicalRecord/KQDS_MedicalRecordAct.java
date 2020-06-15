package com.kqds.controller.base.medicalRecord;

import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordCz;
import com.kqds.entity.base.KqdsMedicalrecordFz;
import com.kqds.entity.base.KqdsMedicalrecordRestoration;
import com.kqds.entity.base.KqdsMedicalrecordReview;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi2;
import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.medicalRecord.KQDS_MedicalRecordLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.base.code.BLCodeUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.bus.BLUtil;
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
@RequestMapping({"KQDS_MedicalRecordAct"})
public class KQDS_MedicalRecordAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_MedicalRecordAct.class);
  
  @Autowired
  private KQDS_MedicalRecordLogic logic;
  
  @Autowired
  private KQDS_REGLogic logicreg;
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @RequestMapping({"/toZZBLList.act"})
  public ModelAndView toZZBLList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/bingli/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZZBLKList.act"})
  public ModelAndView toZZBLKList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/bingli/blk/blk_list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBlSearch.act"})
  public ModelAndView toBlSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/medicalRecord/blSearch.jsp");
    return mv;
  }
  
  @RequestMapping({"/toYhgtIndex.act"})
  public ModelAndView toYhgtIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/yhgt/yhgt_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBlkManager.act"})
  public ModelAndView toBlkManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/medicalRecord/blkManager.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMedicalrecordCz.act"})
  public ModelAndView toMedicalrecordCz(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_cz.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMedicalrecordFz.act"})
  public ModelAndView toMedicalrecordFz(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_fz.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMedicalrecordDetail.act"})
  public ModelAndView toMedicalrecordDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/medicalRecord/detail_kqds_medicalrecord.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTooth_Select.act"})
  public ModelAndView toTooth_Select(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String inputTdName = request.getParameter("inputTdName");
    ModelAndView mv = new ModelAndView();
    mv.addObject("inputTdName", inputTdName);
    mv.setViewName("/kqdsFront/medicalRecord/tooth_select.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMedicalRecord_Edit.act"})
  public ModelAndView toMedicalRecord_Edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    String mtype = request.getParameter("mtype");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("mtype", mtype);
    mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMedicalRecord_Detail.act"})
  public ModelAndView toMedicalRecord_Detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/medicalRecord/detail_kqds_medicalrecord.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAttachList.act"})
  public ModelAndView toAttachList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/medicalRecord/attachList.jsp");
    return mv;
  }
  
  @RequestMapping({"/toLsblWin.act"})
  public ModelAndView toLsblWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String status = request.getParameter("status");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("status", status);
    mv.setViewName("/kqdsFront/medicalRecord/lsblWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMedicalrecord.act"})
  public ModelAndView toMedicalrecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/medicalRecord/medicalrecord.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBingliIndex.act"})
  public ModelAndView toBingliIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/bingli/index.jsp");
    return mv;
  }
  
  @RequestMapping({"/updateBl.act"})
  public String updateBl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      String mtype = request.getParameter("mtype");
      String subSeqId = request.getParameter("subSeqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("病历表主键参数值为空"); 
      if (YZUtility.isNullorEmpty(mtype))
        throw new Exception("病历分类参数值为空"); 
      if (YZUtility.isNullorEmpty(subSeqId))
        throw new Exception("病历内容表主键参数值为空"); 
      if (!"0".equals(mtype) && !"1".equals(mtype))
        throw new Exception("病历分类参数值错误，值为：" + mtype); 
      String blfl = request.getParameter("blfl");
      String bc = request.getParameter("bc");
      String doctor = request.getParameter("doctor");
      String nurse = request.getParameter("nurse");
      KqdsMedicalrecord dp = (KqdsMedicalrecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
      if (dp == null)
        throw new Exception("病历记录不存在"); 
      if (!YZUtility.isNullorEmpty(blfl))
        dp.setBlfl(blfl); 
      if (!YZUtility.isNullorEmpty(bc))
        dp.setBc(bc); 
      if (!YZUtility.isNullorEmpty(doctor))
        dp.setDoctor(doctor); 
      if (!YZUtility.isNullorEmpty(nurse))
        dp.setNurse(nurse); 
      this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
      if ("0".equals(mtype)) {
        KqdsMedicalrecordCz cz = (KqdsMedicalrecordCz)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, subSeqId);
        if (cz == null)
          throw new Exception("该病历对应的初诊记录不存在"); 
        KqdsMedicalrecordCz czPage = new KqdsMedicalrecordCz();
        BeanUtils.populate(czPage, request.getParameterMap());
        czPage.setSeqId(subSeqId);
        czPage.setMeid(seqId);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, czPage);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_CZ, czPage, czPage.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_CZ, request);
      } 
      if ("1".equals(mtype)) {
        KqdsMedicalrecordFz fz = (KqdsMedicalrecordFz)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, subSeqId);
        if (fz == null)
          throw new Exception("该病历对应的复诊记录不存在"); 
        KqdsMedicalrecordFz fzPage = new KqdsMedicalrecordFz();
        BeanUtils.populate(fzPage, request.getParameterMap());
        fzPage.setSeqId(subSeqId);
        fzPage.setMeid(seqId);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, fzPage);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_FZ, fzPage, fzPage.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_FZ, request);
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("id", dp.getSeqId());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String organization = ChainUtil.getCurrentOrganization(request);
      String mseqId = null;
      String contentUUID = null;
      KqdsMedicalrecord dp = new KqdsMedicalrecord();
      KqdsMedicalrecordCz cz = new KqdsMedicalrecordCz();
      KqdsMedicalrecordFz fz = new KqdsMedicalrecordFz();
      BeanUtils.populate(dp, request.getParameterMap());
      BeanUtils.populate(cz, request.getParameterMap());
      BeanUtils.populate(fz, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String subSeqId = request.getParameter("subSeqId");
      if (dp.getMtype() == null)
        throw new Exception("病历分类参数值为空"); 
      if (dp.getMtype().intValue() != 0 && 1 != dp.getMtype().intValue())
        throw new Exception("病历分类参数值错误，值为：" + dp.getMtype()); 
      KqdsMedicalrecord tmpMed = null;
      KqdsMedicalrecordCz tmpCz = null;
      KqdsMedicalrecordFz tmpFz = null;
      if (!YZUtility.isNullorEmpty(seqId)) {
        mseqId = seqId;
        contentUUID = subSeqId;
        if (YZUtility.isNullorEmpty(subSeqId))
          throw new Exception("病历内容表主键参数值为空"); 
        tmpMed = (KqdsMedicalrecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
        if (tmpMed == null)
          throw new Exception("系统数据异常，病历不存在，病历号为：" + seqId); 
        if (1 == tmpMed.getStatus().intValue())
          if (dp.getMtype().intValue() == 0) {
            tmpCz = (KqdsMedicalrecordCz)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, subSeqId);
            if (tmpCz == null)
              throw new Exception("系统数据异常，病历内容表记录不存在，病历号为：" + subSeqId); 
          } else {
            tmpFz = (KqdsMedicalrecordFz)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, subSeqId);
            if (tmpFz == null)
              throw new Exception("系统数据异常，病历内容表记录不存在，病历号为：" + subSeqId); 
          }  
      } else {
        mseqId = BLCodeUtil.getBLCode(organization);
        contentUUID = YZUtility.getUUID();
        Map<String, String> map = new HashMap<>();
        map.put("regno", dp.getRegno());
        List<KqdsReceiveinfo> en = (List<KqdsReceiveinfo>)this.logic.loadList(TableNameUtil.KQDS_RECEIVEINFO, map);
        if (en != null && en.size() > 0) {
          KqdsReceiveinfo receive = en.get(0);
          receive.setAskstatus(Integer.valueOf(1));
          this.logic.updateSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, receive);
        } 
      } 
      String tmpcreatetime = request.getParameter("tmpcreatetime");
      if (YZUtility.isNullorEmpty(tmpcreatetime)) {
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
      } else {
        dp.setCreatetime(tmpcreatetime);
      } 
      dp.setCreateuser(person.getSeqId());
      dp.setOrganization(ChainUtil.getCurrentOrganization(request));
      dp.setSeqId(mseqId);
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
      } else {
        this.logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
      } 
      if (dp.getMtype().intValue() == 0) {
        cz.setSeqId(contentUUID);
        cz.setMeid(dp.getSeqId());
        cz.setOrganization(ChainUtil.getCurrentOrganization(request));
        cz.setCreateuser(person.getSeqId());
        cz.setCreatetime(YZUtility.getCurDateTimeStr());
        if (!YZUtility.isNullorEmpty(seqId)) {
          this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, cz);
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_CZ, cz, cz.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_CZ, request);
        } else {
          this.logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, cz);
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MEDICALRECORD_CZ, cz, cz.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_CZ, request);
        } 
      } else {
        fz.setSeqId(contentUUID);
        fz.setMeid(dp.getSeqId());
        fz.setOrganization(ChainUtil.getCurrentOrganization(request));
        fz.setCreateuser(person.getSeqId());
        fz.setCreatetime(YZUtility.getCurDateTimeStr());
        if (!YZUtility.isNullorEmpty(seqId)) {
          this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, fz);
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_FZ, fz, fz.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_FZ, request);
        } else {
          this.logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, fz);
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MEDICALRECORD_FZ, fz, fz.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_FZ, request);
        } 
      } 
      if (dp.getStatus().intValue() == 2)
        this.logicreg.setIfmedRecord(dp.getRegno(), 1, request); 
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("id", dp.getSeqId());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      KqdsMedicalrecord en = (KqdsMedicalrecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
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
  
  @RequestMapping({"/selectDetailContent.act"})
  public String selectDetailContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String meid = request.getParameter("meid");
      String mtype = request.getParameter("mtype");
      Map<String, String> map = new HashMap<>();
      map.put("meid", meid);
      JSONObject jobj = new JSONObject();
      if (YZUtility.isNullorEmpty(mtype) || YZUtility.isNullorEmpty(meid))
        throw new Exception("参数传递错误，参数值不能为空"); 
      KqdsMedicalrecord en = (KqdsMedicalrecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, meid);
      jobj.put("usertype", en.getUsertype());
      jobj.put("doctor", en.getDoctor());
      jobj.put("assistant", en.getAssistant());
      jobj.put("nurse", en.getNurse());
      jobj.put("blfl", en.getBlfl());
      jobj.put("bc", en.getBc());
      if (mtype.equals(BLUtil.MTYPE_0)) {
        List<KqdsMedicalrecordCz> cz = (List<KqdsMedicalrecordCz>)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_CZ, map);
        if (cz.size() == 0)
          throw new Exception("初诊病历内容不存在"); 
        jobj.put("data", cz.get(0));
      } else if (mtype.equals(BLUtil.MTYPE_1)) {
        List<KqdsMedicalrecordFz> fz = (List<KqdsMedicalrecordFz>)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_FZ, map);
        if (fz.size() == 0)
          throw new Exception("复诊病历内容不存在"); 
        jobj.put("data", fz.get(0));
      } else if (mtype.equals(BLUtil.MTYPE_2)) {
        List<KqdsMedicalrecordZhongzhi> zhongzhi = (List<KqdsMedicalrecordZhongzhi>)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, map);
        if (zhongzhi.size() == 0)
          throw new Exception("种植1期病历病历内容不存在"); 
        jobj.put("data", zhongzhi.get(0));
      } else if (mtype.equals(BLUtil.MTYPE_3)) {
        List<KqdsMedicalrecordReview> chaixian = (List<KqdsMedicalrecordReview>)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, map);
        if (chaixian.size() == 0)
          throw new Exception("术后拆线病历病历内容不存在"); 
        jobj.put("data", chaixian.get(0));
      } else if (mtype.equals(BLUtil.MTYPE_4)) {
        List<KqdsMedicalrecordZhongzhi2> erqi = (List<KqdsMedicalrecordZhongzhi2>)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI2, map);
        if (erqi.size() == 0)
          throw new Exception("种植2期病历病历内容不存在"); 
        jobj.put("data", erqi.get(0));
      } else if (mtype.equals(BLUtil.MTYPE_5)) {
        List<KqdsMedicalrecordRestoration> xiufu = (List<KqdsMedicalrecordRestoration>)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_RESTORATION, map);
        if (xiufu.size() == 0)
          throw new Exception("种植修复病历病历内容不存在"); 
        jobj.put("data", xiufu.get(0));
      } 
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPageByUsercodeNopage.act"})
  public String selectPageByUsercodeNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String regdept = request.getParameter("regdept");
      String createuser = request.getParameter("createuser");
      String searchvalue = request.getParameter("searchvalue");
      String status = request.getParameter("status");
      String blfl = request.getParameter("blfl");
      String bc = request.getParameter("bc");
      String mtype = request.getParameter("mtype");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap<>();
      if (starttime != null && !starttime.equals(""))
        map.put("starttime", starttime); 
      if (endtime != null && !endtime.equals(""))
        map.put("endtime", endtime); 
      if (searchvalue != null && !searchvalue.equals(""))
        map.put("searchvalue", searchvalue); 
      if (usercode != null && !usercode.equals(""))
        map.put("usercode", usercode); 
      if (regdept != null && !regdept.equals(""))
        map.put("regdept", regdept); 
      if (createuser != null && !createuser.equals(""))
        map.put("createuser", createuser); 
      if (status != null && !status.equals(""))
        map.put("status", status); 
      if (blfl != null && !blfl.equals(""))
        map.put("blfl", blfl); 
      if (bc != null && !bc.equals(""))
        map.put("bc", bc); 
      if (mtype != null && !mtype.equals(""))
        map.put("mtype", mtype); 
      if (sortName != null && !sortName.equals("")) {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      } 
      String visualstaff = SessionUtil.getVisualstaff(request);
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject list = this.logic.selectWithPageByUsercodeNopage(TableNameUtil.KQDS_MEDICALRECORD, map, visualstaff, ChainUtil.getOrganizationFromUrl(request), bp);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("病历查询", fieldArr, fieldnameArr, (List)list.get("rows"), response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPageByUsercod.act"})
  public String selectPageByUsercod(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      String status = request.getParameter("status");
      String mtype = request.getParameter("mtype");
      Map<String, String> map = new HashMap<>();
      if (usercode != null && !usercode.equals(""))
        map.put("usercode", usercode); 
      if (status != null && !status.equals(""))
        map.put("status", status); 
      if (!YZUtility.isNullorEmpty(mtype))
        map.put("mtype", mtype); 
      List<JSONObject> list = this.logic.selectWithPageByUsercode(TableNameUtil.KQDS_MEDICALRECORD, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
