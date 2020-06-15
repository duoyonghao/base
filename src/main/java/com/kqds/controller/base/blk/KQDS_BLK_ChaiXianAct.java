package com.kqds.controller.base.blk;

import com.kqds.entity.base.KqdsBlk;
import com.kqds.entity.base.KqdsBlkReview;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordReview;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.blk.KQDS_BLKLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.bus.BLUtil;
import com.kqds.util.sys.chain.ChainUtil;
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
@RequestMapping({"KQDS_BLK_ChaiXianAct"})
public class KQDS_BLK_ChaiXianAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_BLK_ChaiXianAct.class);
  
  @Autowired
  private KQDS_BLKLogic logic;
  
  @RequestMapping({"/toZhongZhi_Suture_Removal_Huifu.act"})
  public ModelAndView toZhongZhi_Suture_Removal_Huifu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    String mtype = request.getParameter("mtype");
    String type = request.getParameter("type");
    String editFlag = request.getParameter("editFlag");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("mtype", mtype);
    mv.addObject("type", type);
    mv.addObject("editFlag", editFlag);
    mv.setViewName("/kqdsFront/bingli/fucha/zhongzhi_suture_removal_huifu.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZhongZhi_Suture_Removal_Detail.act"})
  public ModelAndView toZhongZhi_Suture_Removal_Detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    String mtype = request.getParameter("mtype");
    String type = request.getParameter("type");
    String detailFlag = request.getParameter("detailFlag");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("mtype", mtype);
    mv.addObject("type", type);
    mv.addObject("detailFlag", detailFlag);
    mv.setViewName("/kqdsFront/bingli/fucha/zhongzhi_suture_removal_detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZhongZhi_Suture_Removal_Add.act"})
  public ModelAndView toZhongZhi_Suture_Removal_Add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/bingli/fucha/zhongzhi_suture_removal_add.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsBlk dp = new KqdsBlk();
      KqdsBlkReview chaixian = new KqdsBlkReview();
      BeanUtils.populate(dp, request.getParameterMap());
      BeanUtils.populate(chaixian, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String subSeqId = request.getParameter("subSeqId");
      if (!YZUtility.isNullorEmpty(seqId)) {
        if (YZUtility.isNullorEmpty(subSeqId))
          throw new Exception("病历内容表主键不能为空"); 
        KqdsBlk m = (KqdsBlk)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK, seqId);
        if (m == null)
          throw new Exception("病历不存在"); 
        dp.setMtype(m.getMtype());
        KqdsBlkReview subM = (KqdsBlkReview)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK_REVIEW, subSeqId);
        if (subM == null)
          throw new Exception("病历内容不存在"); 
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_BLK, dp);
        chaixian.setSeqId(subSeqId);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_BLK_REVIEW, chaixian);
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_BLK_REVIEW, chaixian, TableNameUtil.KQDS_BLK_REVIEW, request);
      } else {
        String type = request.getParameter("type");
        String meid = request.getParameter("meid");
        String blname = request.getParameter("blname");
        if (YZUtility.isNullorEmpty(meid)) {
          dp.setMtype(BLUtil.MTYPE_3);
          dp.setSeqId(YZUtility.getUUID());
          dp.setCreatetime(YZUtility.getCurDateTimeStr());
          dp.setCreateuser((new StringBuilder(String.valueOf(person.getSeqId()))).toString());
          dp.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);
          chaixian.setSeqId(YZUtility.getUUID());
          chaixian.setMeid(dp.getSeqId());
          chaixian.setOrganization(ChainUtil.getCurrentOrganization(request));
          chaixian.setCreatetime(YZUtility.getCurDateTimeStr());
          chaixian.setCreateuser((new StringBuilder(String.valueOf(person.getSeqId()))).toString());
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK_REVIEW, chaixian);
        } else {
          KqdsMedicalrecord m = (KqdsMedicalrecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, meid);
          if (m == null)
            throw new Exception("病历不存在"); 
          Map<String, String> map = new HashMap<>();
          map.put("meid", meid);
          List<KqdsMedicalrecordReview> list = (List<KqdsMedicalrecordReview>)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, map);
          if (list == null || list.isEmpty())
            throw new Exception("病历内容不存在"); 
          KqdsMedicalrecordReview zhongzhiM = list.get(0);
          String mseqId = YZUtility.getUUID();
          dp.setMtype((String)m.getMtype());
          dp.setBlname(blname);
          dp.setSeqId(mseqId);
          dp.setType(type);
          dp.setCreatetime(YZUtility.getCurDateTimeStr());
          dp.setCreateuser(person.getSeqId());
          dp.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);
          chaixian = (KqdsBlkReview)YZUtility.Obj1ToObj2(zhongzhiM, chaixian);
          chaixian.setSeqId(YZUtility.getUUID());
          chaixian.setMeid(dp.getSeqId());
          chaixian.setOrganization(ChainUtil.getCurrentOrganization(request));
          chaixian.setCreatetime(YZUtility.getCurDateTimeStr());
          chaixian.setCreateuser(person.getSeqId());
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK_REVIEW, chaixian);
        } 
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_BLK_REVIEW, chaixian, TableNameUtil.KQDS_BLK_REVIEW, request);
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
}
