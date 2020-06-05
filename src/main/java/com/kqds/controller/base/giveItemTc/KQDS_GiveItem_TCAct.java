package com.kqds.controller.base.giveItemTc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.base.KqdsGiveitem;
import com.kqds.entity.base.KqdsGiveitemGiverecord;
import com.kqds.entity.base.KqdsGiveitemTc;
import com.kqds.service.base.giveItemTc.KQDS_GiveItem_TCLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_GiveItem_TCAct")
public class KQDS_GiveItem_TCAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_GiveItem_TCAct.class);
	@Autowired
	private KQDS_GiveItem_TCLogic logic;

	/**
	 * 根据套餐id查询套餐下所有项目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getItemsByTcno.act")
	public String getItemsByTcno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			List<KqdsGiveitemGiverecord> list = new ArrayList<KqdsGiveitemGiverecord>();

			KqdsGiveitemTc en = (KqdsGiveitemTc) logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, seqId);
			String itemnos = en.getItemno();
			String nums = en.getNum() + "";
			if (YZUtility.isNotNullOrEmpty(itemnos)) {
				// 去除最后一个逗号
				itemnos = itemnos.substring(0, itemnos.length() - 1);
				nums = nums.substring(0, nums.length() - 1);
				String[] itemnoss = itemnos.split(",");
				String[] numss = nums.split(",");
				for (int i = 0; i < itemnoss.length; i++) {
					KqdsGiveitem itemobj = (KqdsGiveitem) logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM, itemnoss[i]);

					if (itemobj.getUseflag() == 1) {
						continue; // 禁用的项目不展示
					}

					KqdsGiveitemGiverecord s = new KqdsGiveitemGiverecord();
					s.setMemberno(itemobj.getSeqId());
					s.setItemno(itemobj.getItemno());
					s.setItemname(itemobj.getItemname());
					s.setUnit(itemobj.getUnit());
					s.setUnitprice(itemobj.getUnitprice());
					s.setZsnum(numss[i]);
					list.add(s);
				}
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 异步加载治疗套餐
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectTreeTcAsync.act")
	public String getSelectTreeTcAsync(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		List<JSONObject> listtree = new ArrayList<JSONObject>();
		String organization = ChainUtil.getCurrentOrganization(request);
		try {
			if (YZUtility.isNullorEmpty(id)) {
				List<KqdsGiveitemTc> list = logic.getSelectTc(organization);
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						JSONObject obj = new JSONObject();
						// base类
						KqdsGiveitemTc base = list.get(i);
						obj.put("id", base.getSeqId());
						obj.put("pId", "0");
						obj.put("name", base.getName());
						obj.put("nocheck", "true");
						String num = base.getNum() + "";
						if (num.indexOf(",") != -1) {
							// 有子类 isParent true
							obj.put("isParent", "true");
						} else {
							obj.put("isParent", "false");
						}
						listtree.add(obj);
					}
				}

			} else {
				// 按照父id 返回子节点
				List<KqdsGiveitemGiverecord> list = new ArrayList<KqdsGiveitemGiverecord>();

				KqdsGiveitemTc en = (KqdsGiveitemTc) logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, id);
				String itemnos = en.getItemno();
				String nums = en.getNum() + "";
				if (YZUtility.isNotNullOrEmpty(itemnos)) {
					// 去除最后一个逗号
					itemnos = itemnos.substring(0, itemnos.length() - 1);
					nums = nums.substring(0, nums.length() - 1);
					String[] itemnoss = itemnos.split(",");
					String[] numss = nums.split(",");
					for (int i = 0; i < itemnoss.length; i++) {
						KqdsGiveitem itemobj = (KqdsGiveitem) logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM, itemnoss[i]);
						if (itemobj == null) {
							continue;
						}

						KqdsGiveitemGiverecord s = new KqdsGiveitemGiverecord();
						s.setMemberno(itemobj.getSeqId());
						s.setItemno(itemobj.getItemno());
						s.setItemname(itemobj.getItemname());
						s.setUnit(itemobj.getUnit());
						s.setZsnum(numss[i]);
						list.add(s);
					}
				}
				if (list != null && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						JSONObject obj = new JSONObject();
						KqdsGiveitemGiverecord next = list.get(j);
						obj.put("id", next.getItemno());
						obj.put("pId", 0);
						obj.put("name", next.getItemname());
						listtree.add(obj);
					}
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, listtree);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("seqId为空");
			}
			KqdsGiveitemTc tc = (KqdsGiveitemTc) logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, seqId);
			if (tc == null) {
				throw new Exception("赠送套餐记录不存在");
			}

			logic.deleteSingleUUID(TableNameUtil.KQDS_GIVEITEM_TC, seqId);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
}