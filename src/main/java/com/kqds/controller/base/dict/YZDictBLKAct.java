package com.kqds.controller.base.dict;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsBlk;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.blk.KQDS_BLKLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZDictBLKAct")
public class YZDictBLKAct {

	private Logger logger = LoggerFactory.getLogger(YZDictBLKAct.class);
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private KQDS_BLKLogic blkLogic;

	/****************************************
	 * 病历相关
	 ***************************************************/
	@RequestMapping(value = "/getSelectBlkTree.act")
	public void getSelectBlkTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String type = request.getParameter("type");
			String search = request.getParameter("search");
			List<YZDict> list = dictLogic.getListByParentCode("BLKFL", null);
			StringBuffer sb = new StringBuffer();

			String organization = null;
			// 试用版本(查询登录人的门诊编号)
			String IS_OPEN_CHAIN_SELECT = YZSysProps.getProp(SysParaUtil.IS_OPEN_CHAIN_SELECT);
			if (YZUtility.isNullorEmpty(IS_OPEN_CHAIN_SELECT) || !"1".equals(IS_OPEN_CHAIN_SELECT)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			if (list != null && list.size() > 0) {
				sb.append("[");
				for (int i = 0; i < list.size(); i++) {
					// base类
					YZDict base = list.get(i);
					sb.append("{ id:\"" + base.getSeqId() + "-" + i + "\", pId:\"0\", name:\"" + base.getDictName() + "\", nocheck:true},");
					List<KqdsBlk> itemlist = blkLogic.getTreatItemBlk(base.getSeqId(), type, search, person, organization);
					if (itemlist != null && itemlist.size() > 0) {
						for (int k = 0; k < itemlist.size(); k++) {
							KqdsBlk item = itemlist.get(k);
							String blname = "";
							if (item.getMtype().equals("0")) {
								blname = "【初诊】" + item.getBlname();
							} else if (item.getMtype().equals("1")) {
								blname = "<span style=\'color:red;margin-right:0px;\'>【复诊】</span>" + item.getBlname();
							}
							sb.append("{ id:\"" + item.getSeqId() + "\", pId:\"" + base.getSeqId() + "-" + i + "\", name:\"" + blname + "\"},");
						}
					}
				}
				if (sb.length() > 1) {
					sb.deleteCharAt(sb.length() - 1);
				}
				sb.append("]");
			}
			JSONObject jobj = new JSONObject();
			jobj.put("tree", sb.toString());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
	}
	/****************************************
	 * 病历相关END
	 ***************************************************/

}
