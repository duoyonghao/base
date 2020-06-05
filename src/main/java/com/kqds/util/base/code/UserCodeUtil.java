package com.kqds.util.base.code;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**
 * 该类只负责生成用户编号
 * 
 * @author Administrator
 * 
 */
@Component
@Controller
public class UserCodeUtil {
	@Autowired
	private KQDS_UserDocumentLogic userDocLogic;
	private static UserCodeUtil userCodeUtil;

	public void setUserDocLogic(KQDS_UserDocumentLogic userDocLogic) {
		this.userDocLogic = userDocLogic;
	}

	@PostConstruct
	public void init() {
		userCodeUtil = this;
		userCodeUtil.userDocLogic = this.userDocLogic;
	}

	/**
	 * 获取用户编号
	 * 
	 * @param conn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public static String getUserCode4Insert(String organization, String userCode) throws Exception {
		int count = userCodeUtil.userDocLogic.countByUserCode(userCode);
		if (count == 0) {
			return userCode;
		} else {
			userCode = getUserCode(organization);
			return getUserCode4Insert(organization, userCode);
		}
	}

	/**
	 * 获取患者编号【线程同步】
	 * 
	 * @param conn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public static String getUserCode(String organization) throws Exception {
		int num = 0;
		String numstr = "";
		String maxusercode = getMaxUserCode(TableNameUtil.KQDS_USERDOCUMENT, organization);
		if (YZUtility.isNullorEmpty(maxusercode)) { // 空表时
			// 不需要做任何处理
		} else {
			String codeNumStr = maxusercode.substring(maxusercode.length() - ConstUtil.USER_CODE_NUM_LEN);
			num = Integer.parseInt(codeNumStr);
		}
		num++;// 记录加1
		if (num < 10) {
			numstr = organization + "00000" + num;
		} else if (num > 9 && num < 100) {
			numstr = organization + "0000" + num;
		} else if (num > 99 && num < 1000) {
			numstr = organization + "000" + num;
		} else if (num > 999 && num < 10000) {
			numstr = organization + "00" + num;
		} else if (num > 9999 && num < 100000) {
			numstr = organization + "0" + num;
		} else if (num > 99999) {
			numstr = organization + num;
		}
		return numstr;

	}

	/**
	 * 查询当前最大的病历号
	 * 
	 * @param conn
	 * @param table
	 * @return
	 * @throws Exception
	 */
	private static String getMaxUserCode(String table, String organization) throws Exception {
		String maxusercode = null;
		List<JSONObject> list = userCodeUtil.userDocLogic.getMaxUserCode(table, organization);
		if (list != null && list.size() > 0) {
			maxusercode = list.get(0).getString("maxusercode");
		}
		return maxusercode;
	}

}
