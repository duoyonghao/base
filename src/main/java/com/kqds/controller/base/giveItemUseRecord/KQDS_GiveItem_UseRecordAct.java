package com.kqds.controller.base.giveItemUseRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsGiveitemUserecord;
import com.kqds.service.base.giveItemUseRecord.KQDS_GiveItem_UseRecordLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_GiveItem_UseRecordAct")
public class KQDS_GiveItem_UseRecordAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_GiveItem_UseRecordAct.class);
	@Autowired
	private KQDS_GiveItem_UseRecordLogic logic;

	@RequestMapping(value = "/toZengSongList.act")
	public ModelAndView toGrxx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/index/center/zengsong_list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMember_Zengsong_UserItem.act")
	public ModelAndView toMember_Zengsong_UserItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String regno = request.getParameter("regno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("regno", regno);
		mv.setViewName("/kqdsFront/member/member_zengsong_userItem.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMember_Zengsong4zxzx.act")
	public ModelAndView toMember_Zengsong4zxzx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/member/member_zengsong4zxzx.jsp");
		return mv;
	}

	/**
	 * 医疗中心-已操作【在咨询 确认使用后，医生进行此操作】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			KqdsGiveitemUserecord dp = new KqdsGiveitemUserecord();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键不能为空");
			}

			// 操作人
			dp.setStatus(ConstUtil.GIVE_ITEM_USE_STATUS_1);
			// 操作时间
			dp.setCztime(YZUtility.getCurDateTimeStr());
			logic.updateSingleUUID(TableNameUtil.KQDS_GIVEITEM_USERECORD, dp);

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_GIVEITEM_USERECORD, dp, dp.getUsercode(), TableNameUtil.KQDS_GIVEITEM_USERECORD, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/*
	 * 详情返回
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			KqdsGiveitemUserecord en = (KqdsGiveitemUserecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_GIVEITEM_USERECORD, seqId);

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
	 * 根据会员卡号 查询使用赠送项目的记录 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserRecordByMemberno.act")
	public String getUserRecordByMemberno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String memberno = request.getParameter("memberno");
			String queryInput = request.getParameter("queryInput");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String doctor = request.getParameter("doctor");
			String nurse = request.getParameter("nurse");
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
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
			}
			if (!YZUtility.isNullorEmpty(nurse)) {
				map.put("nurse", nurse);
			}
			List<JSONObject> list = logic.getUserRecordByMemberno(TableNameUtil.KQDS_GIVEITEM_USERECORD, map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("使用赠送记录", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 根据医生和患者编号 查询使用赠送项目的记录 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUseRecordByUsercodeAndDoctor.act")
	public String getUseRecordByUsercodeAndDoctor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String doctor = request.getParameter("doctor");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
			}
			List<JSONObject> list = logic.getUserRecordByMemberno(TableNameUtil.KQDS_GIVEITEM_USERECORD, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}