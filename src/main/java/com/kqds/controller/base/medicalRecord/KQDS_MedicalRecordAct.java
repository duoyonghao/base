package com.kqds.controller.base.medicalRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordCz;
import com.kqds.entity.base.KqdsMedicalrecordFz;
import com.kqds.entity.base.KqdsMedicalrecordRestoration;
import com.kqds.entity.base.KqdsMedicalrecordReview;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi2;
import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.medicalRecord.KQDS_MedicalRecordLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.base.code.BLCodeUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.bus.BLUtil;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_MedicalRecordAct")
public class KQDS_MedicalRecordAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_MedicalRecordAct.class);
	@Autowired
	private KQDS_MedicalRecordLogic logic;
	@Autowired
	private KQDS_REGLogic logicreg;
	@Autowired
	private YZDictLogic dictLogic;

	/** 漏掉的页面跳转代码 **/
	@RequestMapping(value = "/toZZBLList.act")
	public ModelAndView toZZBLList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String regno = request.getParameter("regno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("regno", regno);
		mv.setViewName("/kqdsFront/bingli/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZZBLKList.act")
	public ModelAndView toZZBLKList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String regno = request.getParameter("regno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("regno", regno);
		mv.setViewName("/kqdsFront/bingli/blk/blk_list.jsp");
		return mv;
	}

	/** 漏掉的页面跳转代码 end **/

	@RequestMapping(value = "/toBlSearch.act")
	public ModelAndView toBlSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/medicalRecord/blSearch.jsp");
		return mv;
	}

	@RequestMapping(value = "/toYhgtIndex.act")
	public ModelAndView toYhgtIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/yhgt/yhgt_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlkManager.act")
	public ModelAndView toBlkManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/medicalRecord/blkManager.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMedicalrecordCz.act")
	public ModelAndView toMedicalrecordCz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_cz.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMedicalrecordFz.act")
	public ModelAndView toMedicalrecordFz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_fz.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMedicalrecordDetail.act")
	public ModelAndView toMedicalrecordDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/medicalRecord/detail_kqds_medicalrecord.jsp");
		return mv;
	}

	@RequestMapping(value = "/toTooth_Select.act")
	public ModelAndView toTooth_Select(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String inputTdName = request.getParameter("inputTdName");
		ModelAndView mv = new ModelAndView();
		mv.addObject("inputTdName", inputTdName);
		mv.setViewName("/kqdsFront/medicalRecord/tooth_select.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMedicalRecord_Edit.act")
	public ModelAndView toMedicalRecord_Edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMedicalRecord_Detail.act")
	public ModelAndView toMedicalRecord_Detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/medicalRecord/detail_kqds_medicalrecord.jsp");
		return mv;
	}

	@RequestMapping(value = "/toAttachList.act")
	public ModelAndView toAttachList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/medicalRecord/attachList.jsp");
		return mv;
	}

	@RequestMapping(value = "/toLsblWin.act")
	public ModelAndView toLsblWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String status = request.getParameter("status");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("status", status);
		mv.setViewName("/kqdsFront/medicalRecord/lsblWin.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMedicalrecord.act")
	public ModelAndView toMedicalrecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/medicalRecord/medicalrecord.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBingliIndex.act")
	public ModelAndView toBingliIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String regno = request.getParameter("regno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("regno", regno);
		mv.setViewName("/kqdsFront/bingli/index.jsp");
		return mv;
	}

	/**
	 * 修改患者病历信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBl.act")
	public String updateBl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String mtype = request.getParameter("mtype");

			String subSeqId = request.getParameter("subSeqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("病历表主键参数值为空");
			}

			if (YZUtility.isNullorEmpty(mtype)) {
				throw new Exception("病历分类参数值为空");
			}

			if (YZUtility.isNullorEmpty(subSeqId)) {
				throw new Exception("病历内容表主键参数值为空");
			}

			if (!("0".equals(mtype) || "1".equals(mtype))) {
				throw new Exception("病历分类参数值错误，值为：" + mtype);
			}

			String blfl = request.getParameter("blfl");
			String bc = request.getParameter("bc");
			String doctor = request.getParameter("doctor");
			String nurse = request.getParameter("nurse");

			KqdsMedicalrecord dp = (KqdsMedicalrecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);

			if (dp == null) {
				throw new Exception("病历记录不存在");
			}

			if (!YZUtility.isNullorEmpty(blfl)) {
				dp.setBlfl(blfl);
			}

			if (!YZUtility.isNullorEmpty(bc)) {
				dp.setBc(bc);
			}

			if (!YZUtility.isNullorEmpty(doctor)) {
				dp.setDoctor(doctor);
			}

			if (!YZUtility.isNullorEmpty(nurse)) {
				dp.setNurse(nurse);
			}

			logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);

			if ("0".equals(mtype)) { // 初诊
				KqdsMedicalrecordCz cz = (KqdsMedicalrecordCz) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, subSeqId);
				if (cz == null) {
					throw new Exception("该病历对应的初诊记录不存在");
				}

				KqdsMedicalrecordCz czPage = new KqdsMedicalrecordCz();
				BeanUtils.populate(czPage, request.getParameterMap());
				// 重新赋下值
				czPage.setSeqId(subSeqId);
				czPage.setMeid(seqId);

				logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, czPage);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_CZ, czPage, czPage.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_CZ, request);
			}

			if ("1".equals(mtype)) { // 复诊

				KqdsMedicalrecordFz fz = (KqdsMedicalrecordFz) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, subSeqId);
				if (fz == null) {
					throw new Exception("该病历对应的复诊记录不存在");
				}

				KqdsMedicalrecordFz fzPage = new KqdsMedicalrecordFz();
				BeanUtils.populate(fzPage, request.getParameterMap());
				// 重新赋下值
				fzPage.setSeqId(subSeqId);
				fzPage.setMeid(seqId);

				logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, fzPage);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_FZ, fzPage, fzPage.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_FZ, request);
			}
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put("id", dp.getSeqId());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 入库，修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String organization = ChainUtil.getCurrentOrganization(request);
			// 病历号 ####
			String mseqId = null;
			// 初诊、复诊主键####
			String contentUUID = null;

			KqdsMedicalrecord dp = new KqdsMedicalrecord();
			KqdsMedicalrecordCz cz = new KqdsMedicalrecordCz();
			KqdsMedicalrecordFz fz = new KqdsMedicalrecordFz();
			BeanUtils.populate(dp, request.getParameterMap());
			BeanUtils.populate(cz, request.getParameterMap());
			BeanUtils.populate(fz, request.getParameterMap());

			String seqId = request.getParameter("seqId");
			String subSeqId = request.getParameter("subSeqId");

			if (dp.getMtype() == null) {
				throw new Exception("病历分类参数值为空");
			}
			if (!(0 == dp.getMtype() || 1 == dp.getMtype())) {
				throw new Exception("病历分类参数值错误，值为：" + dp.getMtype());
			}

			KqdsMedicalrecord tmpMed = null;
			KqdsMedicalrecordCz tmpCz = null;
			KqdsMedicalrecordFz tmpFz = null; // ###########用于更新病历

			if (!YZUtility.isNullorEmpty(seqId)) {
				mseqId = seqId; // 病历号
				contentUUID = subSeqId; // 病历号

				if (YZUtility.isNullorEmpty(subSeqId)) {
					throw new Exception("病历内容表主键参数值为空");
				}
				tmpMed = (KqdsMedicalrecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
				if (tmpMed == null) {
					throw new Exception("系统数据异常，病历不存在，病历号为：" + seqId);
				}
				if (1 == tmpMed.getStatus()) { // 如果状态为暂存
					if (dp.getMtype() == 0) { // 初诊病历
						tmpCz = (KqdsMedicalrecordCz) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, subSeqId);
						if (tmpCz == null) {
							throw new Exception("系统数据异常，病历内容表记录不存在，病历号为：" + subSeqId);
						}
					} else {
						tmpFz = (KqdsMedicalrecordFz) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, subSeqId);
						if (tmpFz == null) {
							throw new Exception("系统数据异常，病历内容表记录不存在，病历号为：" + subSeqId);
						}
					}
				}
			} else {
				mseqId = BLCodeUtil.getBLCode(organization); // 病历号
				contentUUID = YZUtility.getUUID(); // 病历号
				// 修改接诊状态为已接诊
				Map<String, String> map = new HashMap<>();
				map.put("regno", dp.getRegno());
				List<KqdsReceiveinfo> en = (List<KqdsReceiveinfo>) logic.loadList(TableNameUtil.KQDS_RECEIVEINFO, map);
				if (en != null && en.size() > 0) {
					KqdsReceiveinfo receive = en.get(0);
					receive.setAskstatus(1);
					logic.updateSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, receive);
				}
			}

			String tmpcreatetime = request.getParameter("tmpcreatetime"); // 病历填写时间
			if (YZUtility.isNullorEmpty(tmpcreatetime)) {
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
			} else {
				dp.setCreatetime(tmpcreatetime);
			}
			dp.setCreateuser(person.getSeqId());
			dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			dp.setSeqId(mseqId); // 病历号

			if (!YZUtility.isNullorEmpty(seqId)) { // 编辑病历、暂存病历
				logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
			} else {
				logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
			}

			if (dp.getMtype() == 0) {// 初诊病历
				cz.setSeqId(contentUUID);
				cz.setMeid(dp.getSeqId());
				cz.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
				// add by yangen 2017-9-7
				cz.setCreateuser(person.getSeqId());
				cz.setCreatetime(YZUtility.getCurDateTimeStr());
				if (!YZUtility.isNullorEmpty(seqId)) { // 编辑病历、暂存病历
					logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, cz);
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_CZ, cz, cz.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_CZ, request);
				} else {
					logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_CZ, cz);
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MEDICALRECORD_CZ, cz, cz.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_CZ, request);
				}

			} else {
				fz.setSeqId(contentUUID);
				fz.setMeid(dp.getSeqId());
				fz.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】

				fz.setCreateuser(person.getSeqId());
				fz.setCreatetime(YZUtility.getCurDateTimeStr());
				if (!YZUtility.isNullorEmpty(seqId)) { // 编辑病历、暂存病历
					logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, fz);
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_FZ, fz, fz.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_FZ, request);

				} else {
					logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_FZ, fz);
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MEDICALRECORD_FZ, fz, fz.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_FZ, request);

				}

			}
			if (dp.getStatus() == 2) {// 提交病历
				logicreg.setIfmedRecord(dp.getRegno(), 1, request);
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put("id", dp.getSeqId());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}
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

			KqdsMedicalrecord en = (KqdsMedicalrecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
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
	 * 病历内容详情返回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailContent.act")
	public String selectDetailContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String meid = request.getParameter("meid");
			String mtype = request.getParameter("mtype");

			Map<String, String> map = new HashMap<String, String>();
			map.put("meid", meid);
			JSONObject jobj = new JSONObject();

			if (YZUtility.isNullorEmpty(mtype) || YZUtility.isNullorEmpty(meid)) {
				throw new Exception("参数传递错误，参数值不能为空");
			}

			KqdsMedicalrecord en = (KqdsMedicalrecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, meid);

			jobj.put("usertype", en.getUsertype());
			jobj.put("doctor", en.getDoctor()); // ### 青岛特定需求
			jobj.put("assistant", en.getAssistant());
			jobj.put("nurse", en.getNurse());
			jobj.put("blfl", en.getBlfl());
			jobj.put("bc", en.getBc());
			if (mtype.equals(BLUtil.MTYPE_0)) {// 初诊
				List<KqdsMedicalrecordCz> cz = (List<KqdsMedicalrecordCz>) logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_CZ, map);
				if (cz.size() == 0) {
					throw new Exception("初诊病历内容不存在");
				}

				jobj.put("data", cz.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_1)) {
				List<KqdsMedicalrecordFz> fz = (List<KqdsMedicalrecordFz>) logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_FZ, map);
				if (fz.size() == 0) {
					throw new Exception("复诊病历内容不存在");
				}

				jobj.put("data", fz.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_2)) { // ###########################种植1期病历
				List<KqdsMedicalrecordZhongzhi> zhongzhi = (List<KqdsMedicalrecordZhongzhi>) logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, map);
				if (zhongzhi.size() == 0) {
					throw new Exception("种植1期病历病历内容不存在");
				}

				jobj.put("data", zhongzhi.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_3)) { // ###########################术后拆线
				List<KqdsMedicalrecordReview> chaixian = (List<KqdsMedicalrecordReview>) logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, map);
				if (chaixian.size() == 0) {
					throw new Exception("术后拆线病历病历内容不存在");
				}

				jobj.put("data", chaixian.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_4)) { // ###########################种植2期病历
				List<KqdsMedicalrecordZhongzhi2> erqi = (List<KqdsMedicalrecordZhongzhi2>) logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI2, map);
				if (erqi.size() == 0) {
					throw new Exception("种植2期病历病历内容不存在");
				}

				jobj.put("data", erqi.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_5)) { // ###########################种植2期病历
				List<KqdsMedicalrecordRestoration> xiufu = (List<KqdsMedicalrecordRestoration>) logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_RESTORATION, map);
				if (xiufu.size() == 0) {
					throw new Exception("种植修复病历病历内容不存在");
				}

				jobj.put("data", xiufu.get(0));
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 医疗中心-病历查询 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPageByUsercodeNopage.act")
	public String selectPageByUsercodeNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			String usercode = request.getParameter("usercode");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String regdept = request.getParameter("regdept");
			String createuser = request.getParameter("createuser");
			String searchvalue = request.getParameter("searchvalue");
			String status = request.getParameter("status");
			String blfl = request.getParameter("blfl");
			String bc = request.getParameter("bc");
			String mtype = request.getParameter("mtype");
			String sortName=request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();
			if (starttime != null && !starttime.equals("")) {
				map.put("starttime", starttime);
			}
			if (endtime != null && !endtime.equals("")) {
				map.put("endtime", endtime);
			}
			if (searchvalue != null && !searchvalue.equals("")) {
				map.put("searchvalue", searchvalue);
			}
			if (usercode != null && !usercode.equals("")) {
				map.put("usercode", usercode);
			}
			if (regdept != null && !regdept.equals("")) {
				map.put("regdept", regdept);
			}
			if (createuser != null && !createuser.equals("")) {
				map.put("createuser", createuser);
			}
			if (status != null && !status.equals("")) {
				map.put("status", status);
			}
			if (blfl != null && !blfl.equals("")) {
				map.put("blfl", blfl);
			}
			if (bc != null && !bc.equals("")) {
				map.put("bc", bc);
			}
			if (mtype != null && !mtype.equals("")) {
				map.put("mtype", mtype);
			}
			if(sortName != null && !sortName.equals("")){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
			}
			// 分页查询
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject list = logic.selectWithPageByUsercodeNopage(TableNameUtil.KQDS_MEDICALRECORD, map, visualstaff, ChainUtil.getOrganizationFromUrl(request),bp);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("病历查询", fieldArr, fieldnameArr, (List<JSONObject>) list.get("rows"), response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(list, null, response, logger);
			//YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 右侧病历列表 ---不做人员权限 【支持跨院】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPageByUsercod.act")
	public String selectPageByUsercod(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			String usercode = request.getParameter("usercode");
			String status = request.getParameter("status");
			String mtype = request.getParameter("mtype");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();
			if (usercode != null && !usercode.equals("")) {
				map.put("usercode", usercode);
			}
			if (status != null && !status.equals("")) {
				map.put("status", status);
			}
			if (!YZUtility.isNullorEmpty(mtype)) {
				map.put("mtype", mtype);
			}

			// 分页查询
			List<JSONObject> list = logic.selectWithPageByUsercode(TableNameUtil.KQDS_MEDICALRECORD, map);
			/*
			 * if (list != null && list.size() > 0) { list.get(list.size() -
			 * 1).setSize(list.size()); }
			 */
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}