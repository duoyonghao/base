package com.kqds.controller.base.refundDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.entity.base.KqdsRefundDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.refundDetail.KQDS_Refund_detailLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Refund_DetailAct")
public class KQDS_Refund_DetailAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Refund_DetailAct.class);
	@Autowired
	private KQDS_Refund_detailLogic logic;

	/**
	 * 详情返回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			KqdsRefundDetail en = (KqdsRefundDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_REFUND_DETAIL, seqId);

			if (en == null) {
				throw new Exception("数据不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectWithNopage.act")
	public String selectWithNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String refundid = request.getParameter("refundid");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(refundid)) {
				map.put("refundid", refundid);
			}
			List<JSONObject> list = logic.selectWithNopage(TableNameUtil.KQDS_REFUND_DETAIL, map, person);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectWithNopage4.act")
	public String selectWithNopage4(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String refundid = request.getParameter("refundid");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(refundid)) {
				map.put("refundid", refundid);
			}
			List<JSONObject> list = logic.selectWithNopage4(TableNameUtil.KQDS_REFUND_DETAIL, map, person);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}