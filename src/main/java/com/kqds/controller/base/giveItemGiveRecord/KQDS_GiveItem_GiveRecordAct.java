package com.kqds.controller.base.giveItemGiveRecord;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.entity.base.KqdsGiveitemGiverecord;
import com.kqds.entity.base.KqdsGiveitemUserecord;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.giveItemGiveRecord.KQDS_GiveItem_GiveRecordLogic;
import com.kqds.service.base.jzmdType.KQDS_JzqkLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_GiveItem_GiveRecordAct")
public class KQDS_GiveItem_GiveRecordAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_GiveItem_GiveRecordAct.class);
	@Autowired
	private KQDS_GiveItem_GiveRecordLogic logic;
	@Autowired
	private KQDS_JzqkLogic jzqkLogic;

	/**
	 * 根据会员卡号 查询赠送项目记录 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGiveRecordByMemberno.act")
	public String getGiveRecordByMemberno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String memberno = request.getParameter("memberno");
			String queryInput = request.getParameter("queryInput");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String usercode = request.getParameter("usercode");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(memberno)) {
				map.put("memberno", memberno);
			}
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("queryInput", queryInput);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			List<JSONObject> list = logic.getGiveRecordByMemberno(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("赠送记录", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			KqdsGiveitemGiverecord en = (KqdsGiveitemGiverecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_GIVEITEM_GIVERECORD, en, TableNameUtil.KQDS_GIVEITEM_GIVERECORD, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 确定赠送
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/confirmGiveItems.act")
	public String confirmGiveItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String usercode = request.getParameter("usercode");
			String username = request.getParameter("username");
			String memberno = request.getParameter("memberno");
			String params = request.getParameter("params");
			params = java.net.URLDecoder.decode(params, "UTF-8");
			if (YZUtility.isNullorEmpty(params)) {
				throw new Exception("params为空或者null");
			}
			JSONArray jArray = JSONArray.fromObject(params);
			Collection collection = JSONArray.toCollection(jArray, KqdsGiveitemGiverecord.class);
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				// 保存赠送记录
				KqdsGiveitemGiverecord r = (KqdsGiveitemGiverecord) it.next();
				String rid = YZUtility.getUUID();
				r.setSeqId(rid);
				r.setCreatetime(YZUtility.getCurDateTimeStr());
				r.setCreateuser(person.getSeqId());
				r.setUsercode(usercode);
				r.setUsername(username);
				r.setMemberno(memberno);
				r.setUsenum("0");
				r.setNownum(r.getZsnum());
				r.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
																				// 【前端调用，以当前所在门诊为主】
				logic.saveSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, r);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_GIVEITEM_GIVERECORD, r, r.getUsercode(), TableNameUtil.KQDS_GIVEITEM_GIVERECORD, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 咨询中心->确认使用-> 保存使用次数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/useGiveItems.act")
	public String useGiveItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			String regno = request.getParameter("regno");
			String doctor = request.getParameter("doctor");
			String nurse = request.getParameter("nurse");
			String params = request.getParameter("params");
			params = java.net.URLDecoder.decode(params, "UTF-8");
			if (YZUtility.isNullorEmpty(params)) {
				throw new Exception("params为空或者null");
			}
			// 保存就诊情况
			jzqkLogic.saveJzqk(doctor, regno, person, request);
			JSONArray jArray = JSONArray.fromObject(params);
			Collection collection = JSONArray.toCollection(jArray, KqdsGiveitemUserecord.class);
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				KqdsGiveitemUserecord r = (KqdsGiveitemUserecord) it.next();
				String id = r.getSeqId() + "";
				int num = Integer.parseInt(r.getSynum());// 本次使用次数
				if (num > 0) {
					KqdsGiveitemGiverecord en = (KqdsGiveitemGiverecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, id);
					int synum = Integer.parseInt(en.getUsenum());// 已使用次数
					int nownum = Integer.parseInt(en.getNownum());// 剩余次数
					en.setUsenum((synum + num) + "");// 本次使用后的已使用次数
					en.setNownum((nownum - num) + "");// 剩余数量
					logic.updateSingleUUID(TableNameUtil.KQDS_GIVEITEM_GIVERECORD, en);
					// 保存使用记录
					String uuid = YZUtility.getUUID();
					r.setSeqId(uuid);
					r.setCreatetime(YZUtility.getCurDateTimeStr());
					r.setCreateuser(person.getSeqId());
					r.setUsercode(en.getUsercode());
					r.setUsername(en.getUsername());
					r.setItemno(en.getItemno());
					r.setItemname(en.getItemname());
					r.setMemberno(en.getMemberno());
					r.setUnit(en.getUnit());
					r.setUnitprice(en.getUnitprice());
					r.setZsnum(en.getZsnum());
					r.setSynum(num + "");
					r.setNownum(en.getNownum() + "");
					r.setDoctor(doctor);
					r.setNurse(nurse);
					r.setStatus(ConstUtil.GIVE_ITEM_USE_STATUS_0);// 未操作
					r.setCztime("");// 操作时间
					r.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
																					// 【前端调用，以当前所在门诊为主】
					logic.saveSingleUUID(TableNameUtil.KQDS_GIVEITEM_USERECORD, r);

					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_GIVEITEM_USERECORD, r, r.getUsercode(), TableNameUtil.KQDS_GIVEITEM_USERECORD, request);

				}
				// 修改本次挂号的医生
				KqdsReg regTmp = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, regno);
				if (regTmp != null) {
					regTmp.setDoctor(doctor);
					logic.updateSingleUUID(TableNameUtil.KQDS_REG, regTmp);
				}
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}
}