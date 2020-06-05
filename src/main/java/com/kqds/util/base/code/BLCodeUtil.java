package com.kqds.util.base.code;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.service.base.medicalRecord.KQDS_MedicalRecordLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

/**
 * 该类只负责生成病历编号
 * 
 * @author Administrator
 * 
 */
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

	/**
	 * 获取病历号【线程同步】
	 * 
	 * @param conn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public static String getBLCode(String organization) throws Exception {
		synchronized (BLCodeUtil.class) {
			String blNo = organization + "BL"; // 病历号
			int num = 0;
			String numstr = "";
			String maxblcode = blCodeUtil.medicalRecordLogic.getMaxBLCode(TableNameUtil.KQDS_MEDICALRECORD, organization);

			if (YZUtility.isNullorEmpty(maxblcode)) { // 空表时
				// 不需要做任何处理
			} else {
				String codeNumStr = maxblcode.substring(maxblcode.length() - 7);
				num = Integer.parseInt(codeNumStr);
			}
			num++;// 记录加1
			if (num < 10) {
				numstr = blNo + "000000" + num;
			} else if (num > 9 && num < 100) {
				numstr = blNo + "00000" + num;
			} else if (num > 99 && num < 1000) {
				numstr = blNo + "0000" + num;
			} else if (num > 999 && num < 10000) {
				numstr = blNo + "000" + num;
			} else if (num > 9999 && num < 100000) {
				numstr = blNo + "00" + num;
			} else if (num > 99999 && num < 100000) {
				numstr = blNo + "0" + num;
			} else if (num > 999999) {
				numstr = blNo + num;
			}
			return numstr;
		}

	}

}
