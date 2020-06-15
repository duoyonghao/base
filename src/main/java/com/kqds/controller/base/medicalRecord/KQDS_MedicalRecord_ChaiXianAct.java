package com.kqds.controller.base.medicalRecord;

import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordReview;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.mediaRecord.Kqds_MediaRecordLogic;
import com.kqds.util.base.code.BLCodeUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_MedicalRecord_ChaiXianAct"})
public class KQDS_MedicalRecord_ChaiXianAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_MedicalRecord_ChaiXianAct.class);
  
  @Autowired
  private Kqds_MediaRecordLogic logic;
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsMedicalrecord dp = new KqdsMedicalrecord();
      KqdsMedicalrecordReview chaixian = new KqdsMedicalrecordReview();
      BeanUtils.populate(dp, request.getParameterMap());
      BeanUtils.populate(chaixian, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String subSeqId = request.getParameter("subSeqId");
      if (!YZUtility.isNullorEmpty(seqId)) {
        if (YZUtility.isNullorEmpty(subSeqId))
          throw new Exception("病历内容表主键不能为空"); 
        KqdsMedicalrecord m = (KqdsMedicalrecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
        if (m == null)
          throw new Exception("病历不存在"); 
        if (2 == m.getStatus().intValue()) {
          String yuanzhang = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_YUANZHANG_SEQID);
          if (!"admin".equals(person.getUserId()) && YZUtility.isStrInArrayEach(person.getUserPriv(), yuanzhang) && 
            YZUtility.isStrInArrayEach(person.getUserPrivOther(), yuanzhang))
            throw new Exception("不允许修改已提交的病历"); 
        } 
        dp.setMtype(m.getMtype());
        dp.setUsercode(m.getUsercode());
        dp.setRegno(m.getRegno());
        KqdsMedicalrecordReview subM = (KqdsMedicalrecordReview)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, subSeqId);
        if (subM == null)
          throw new Exception("病历内容不存在"); 
        dp.setRegno(m.getRegno());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
        chaixian.setSeqId(subSeqId);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, chaixian);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_REVIEW, chaixian, chaixian.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_REVIEW, 
            request);
      } else {
        String organization = ChainUtil.getCurrentOrganization(request);
        String mseqId = BLCodeUtil.getBLCode(organization);
        dp.setMtype(Integer.valueOf(3));
        dp.setSeqId(mseqId);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
        chaixian.setSeqId(YZUtility.getUUID());
        chaixian.setMeid(dp.getSeqId());
        chaixian.setOrganization(ChainUtil.getCurrentOrganization(request));
        chaixian.setCreatetime(YZUtility.getCurDateTimeStr());
        chaixian.setCreateuser(person.getSeqId());
        this.logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, chaixian);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MEDICALRECORD_REVIEW, chaixian, chaixian.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_REVIEW, request);
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
