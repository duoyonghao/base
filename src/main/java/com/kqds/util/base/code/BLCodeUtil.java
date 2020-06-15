package com.kqds.util.base.code;

import com.kqds.service.base.medicalRecord.KQDS_MedicalRecordLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class BLCodeUtil extends BaseLogic {
  @Autowired
  private KQDS_MedicalRecordLogic medicalRecordLogic;
  
  private static BLCodeUtil blCodeUtil;
  
  public void setUserDocLogic(KQDS_MedicalRecordLogic medicalRecordLogic) {
    this.medicalRecordLogic = medicalRecordLogic;
  }
  
  @PostConstruct
  public void init() {
    blCodeUtil = this;
    blCodeUtil.medicalRecordLogic = this.medicalRecordLogic;
  }
  
  public static String getBLCode(String organization) throws Exception {
    synchronized (BLCodeUtil.class) {
      String blNo = String.valueOf(organization) + "BL";
      int num = 0;
      String numstr = "";
      String maxblcode = blCodeUtil.medicalRecordLogic.getMaxBLCode(TableNameUtil.KQDS_MEDICALRECORD, organization);
      if (!YZUtility.isNullorEmpty(maxblcode)) {
        String codeNumStr = maxblcode.substring(maxblcode.length() - 7);
        num = Integer.parseInt(codeNumStr);
      } 
      num++;
      if (num < 10) {
        numstr = String.valueOf(blNo) + "000000" + num;
      } else if (num > 9 && num < 100) {
        numstr = String.valueOf(blNo) + "00000" + num;
      } else if (num > 99 && num < 1000) {
        numstr = String.valueOf(blNo) + "0000" + num;
      } else if (num > 999 && num < 10000) {
        numstr = String.valueOf(blNo) + "000" + num;
      } else if (num > 9999 && num < 100000) {
        numstr = String.valueOf(blNo) + "00" + num;
      } else if (num > 99999 && num < 100000) {
        numstr = String.valueOf(blNo) + "0" + num;
      } else if (num > 999999) {
        numstr = String.valueOf(blNo) + num;
      } 
      return numstr;
    } 
  }
}
