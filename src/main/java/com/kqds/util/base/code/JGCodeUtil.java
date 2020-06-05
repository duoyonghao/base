package com.kqds.util.base.code;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.entity.base.KqdsOutprocessingSheet;
import com.kqds.service.base.outProcessingSheet.KQDS_OutProcessingSheetLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.chain.ChainUtil;

/**
 * 加工编号生成器
 * 
 * @author Administrator
 * 
 */
@Component
@Controller
public class JGCodeUtil {
	@Autowired
	private KQDS_OutProcessingSheetLogic logic;
	private static JGCodeUtil jgCodeUtil;

	public void setUserDocLogic(KQDS_OutProcessingSheetLogic logic) {
		this.logic = logic;
	}

	@PostConstruct
	public void init() {
		jgCodeUtil = this;
		jgCodeUtil.logic = this.logic;
	}

	/**
	 * 获取加工单号
	 * 
	 * @param cishu
	 * @return
	 * @throws Exception
	 */
	public static synchronized String getSheetNo(int cishu, HttpServletRequest request) throws Exception {

		String menzhen = ChainUtil.getCurrentOrganization(request);// 以当前所在门诊为主
		// 生成加工单号
		String uuid = menzhen + "JG";
		// 字符源，可以根据需要删减
		String generateSource = "0123456789";
		String rtnStr = "";
		for (int i = 0; i < cishu; i++) {
			// 循环随机获得当次字符，并移走选出的字符
			String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
			rtnStr += nowStr;
			generateSource = generateSource.replaceAll(nowStr, "");
		}
		uuid += rtnStr;
		// 先验证单号是否已存在 存在则重新获取单号 直到不存在
		KqdsOutprocessingSheet sheet = (KqdsOutprocessingSheet) jgCodeUtil.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, uuid);
		if (sheet == null) {
			return uuid;
		} else {
			return getSheetNo(cishu, request);
		}
	}

}
