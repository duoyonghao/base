package com.kqds.controller.base.receiveinfo;

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

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsReceiveinfoContent;
import com.kqds.service.base.receiveinfo.KQDS_ReceiveInfo_ContentLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_Receiveinfo_ContentAct")
public class KQDS_Receiveinfo_ContentAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_Receiveinfo_ContentAct.class);
	@Autowired
	private KQDS_ReceiveInfo_ContentLogic logic;

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
			String receiveinfoId = request.getParameter("receiveinfoId");

			Map<String, String> map = new HashMap<String, String>();
			map.put("receiveid", receiveinfoId);
			List<KqdsReceiveinfoContent> en = (List<KqdsReceiveinfoContent>) logic.loadList(TableNameUtil.KQDS_RECEIVEINFO_CONTENT, map);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put(YZActionKeys.JSON_RET_MSRGS, "操作成功");
			if (en.size() > 0) {
				jobj.put("data", en.get(0));
			} else {
				jobj.put("data", new KqdsReceiveinfoContent());
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}