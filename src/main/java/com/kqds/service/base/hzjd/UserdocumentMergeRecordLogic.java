package com.kqds.service.base.hzjd;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsUserdocumentMergeRecord;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserdocumentMergeRecordLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public void saveMergeRecord(KqdsUserdocumentMergeRecord dp, HttpServletRequest request) throws Exception {
    String organization = ChainUtil.getCurrentOrganization(request);
    YZPerson person = SessionUtil.getLoginPerson(request);
    dp.setCrateuser(person.getSeqId());
    dp.setSeqId(YZUtility.getUUID());
    dp.setCratetime(YZUtility.getCurDateTimeStr());
    dp.setOrganization(organization);
    this.dao.save("KQDS_UserDocument_merge_record.saveMergeRecord", dp);
  }
}
