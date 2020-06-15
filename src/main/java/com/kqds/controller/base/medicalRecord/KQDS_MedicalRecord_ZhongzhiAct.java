package com.kqds.controller.base.medicalRecord;

import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi;
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
@RequestMapping({"KQDS_MedicalRecord_ZhongzhiAct"})
public class KQDS_MedicalRecord_ZhongzhiAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_MedicalRecord_ZhongzhiAct.class);
  @Autowired
  private Kqds_MediaRecordLogic logic;
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsMedicalrecord dp = new KqdsMedicalrecord();
      KqdsMedicalrecordZhongzhi zhongzhi = new KqdsMedicalrecordZhongzhi();
      BeanUtils.populate(dp, request.getParameterMap());
      BeanUtils.populate(zhongzhi, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String subSeqId = request.getParameter("subSeqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        if (YZUtility.isNullorEmpty(subSeqId)) {
          throw new Exception("病历内容表主键不能为空");
        }
        KqdsMedicalrecord m = (KqdsMedicalrecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
        if (m == null) {
          throw new Exception("病历不存在");
        }
        dp.setMtype(m.getMtype());
        dp.setUsercode(m.getUsercode());
        dp.setRegno(m.getRegno());
        if (2 == m.getStatus().intValue())
        {
          String yuanzhang = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_YUANZHANG_SEQID);
          if ((!"admin".equals(person.getUserId())) && (YZUtility.isStrInArrayEach(person.getUserPriv(), yuanzhang)) && 
            (YZUtility.isStrInArrayEach(person.getUserPrivOther(), yuanzhang))) {
            throw new Exception("不允许修改已提交的病历");
          }
        }
        dp.setMtype(m.getMtype());
        
        KqdsMedicalrecordZhongzhi subM = (KqdsMedicalrecordZhongzhi)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, subSeqId);
        if (subM == null) {
          throw new Exception("病历内容不存在");
        }
        dp.setRegno(m.getRegno());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
        
        zhongzhi.setSeqId(subSeqId);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, zhongzhi);
        

        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_ZHONGZHI, zhongzhi, zhongzhi.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, 
          request);
      }
      else
      {
        String organization = ChainUtil.getCurrentOrganization(request);
        
        String mseqId = BLCodeUtil.getBLCode(organization);
        dp.setMtype(Integer.valueOf(2));
        dp.setSeqId(mseqId);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
        
        zhongzhi.setSeqId(YZUtility.getUUID());
        zhongzhi.setMeid(dp.getSeqId());
        zhongzhi.setOrganization(ChainUtil.getCurrentOrganization(request));
        
        zhongzhi.setCreatetime(YZUtility.getCurDateTimeStr());
        zhongzhi.setCreateuser(person.getSeqId());
        this.logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, zhongzhi);
        
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MEDICALRECORD_ZHONGZHI, zhongzhi, zhongzhi.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, 
          request);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("id", dp.getSeqId());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
}
