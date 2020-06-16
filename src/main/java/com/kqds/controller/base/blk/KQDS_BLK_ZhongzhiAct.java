package com.kqds.controller.base.blk;

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

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsBlk;
import com.kqds.entity.base.KqdsBlkZhongzhi;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.blk.KQDS_BLKLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.bus.BLUtil;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_BLK_ZhongzhiAct")
public class KQDS_BLK_ZhongzhiAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_BLK_ZhongzhiAct.class);
	@Autowired
	private KQDS_BLKLogic logic;

	@RequestMapping(value = "/toZhongZhiYiQi_Huifu.act")
	public ModelAndView toZhongZhiYiQi_Huifu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		String type = request.getParameter("type");
		String editFlag = request.getParameter("editFlag");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.addObject("type", type);
		mv.addObject("editFlag", editFlag);
		mv.setViewName("/kqdsFront/bingli/zhongzhi1/zhongzhiyiqi_huifu.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhiYiQi_Detail.act")
	public ModelAndView toZhongZhiYiQi_Detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		String type = request.getParameter("type");
		String detailFlag = request.getParameter("detailFlag");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.addObject("type", type);
		mv.addObject("detailFlag", detailFlag);
		mv.setViewName("/kqdsFront/bingli/zhongzhi1/zhongzhiyiqi_detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhiYiQi_Add.act")
	public ModelAndView toZhongZhiYiQi_Add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String regno = request.getParameter("regno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("regno", regno);
		mv.setViewName("/kqdsFront/bingli/zhongzhi1/zhongzhiyiqi_add.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsBlk dp = new KqdsBlk();
			KqdsBlkZhongzhi zhongzhi = new KqdsBlkZhongzhi();
			BeanUtils.populate(dp, request.getParameterMap());
			BeanUtils.populate(zhongzhi, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			String subSeqId = request.getParameter("subSeqId"); // 子表主键

			if (!YZUtility.isNullorEmpty(seqId)) {
				if (YZUtility.isNullorEmpty(subSeqId)) {
					throw new Exception("病历内容表主键不能为空");
				}

				KqdsBlk m = (KqdsBlk) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK, seqId);
				if (m == null) {
					throw new Exception("病历不存在");
				}
				dp.setMtype(m.getMtype()); // 病历类别，不做更新

				KqdsBlkZhongzhi subM = (KqdsBlkZhongzhi) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK_ZHONGZHI, subSeqId);
				if (subM == null) {
					throw new Exception("病历内容不存在");
				}

				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request));
				logic.updateSingleUUID(TableNameUtil.KQDS_BLK, dp);
				// 更新子表
				zhongzhi.setSeqId(subSeqId);
				logic.updateSingleUUID(TableNameUtil.KQDS_BLK_ZHONGZHI, zhongzhi);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_BLK_ZHONGZHI, zhongzhi, TableNameUtil.KQDS_BLK_ZHONGZHI, request);
			} else {
				String type = request.getParameter("type");
				String meid = request.getParameter("meid");
				String blname = request.getParameter("blname");

				if (YZUtility.isNullorEmpty(meid)) {
					/** 直接创建病历库 **/
					dp.setMtype(BLUtil.MTYPE_2);// 种植一期
					dp.setSeqId(YZUtility.getUUID());
					dp.setCreatetime(YZUtility.getCurDateTimeStr());
					dp.setCreateuser(person.getSeqId());
					dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
					logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);

					zhongzhi.setSeqId(YZUtility.getUUID());
					zhongzhi.setMeid(dp.getSeqId());
					zhongzhi.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
					zhongzhi.setCreatetime(YZUtility.getCurDateTimeStr());
					zhongzhi.setCreateuser(person.getSeqId());
					logic.saveSingleUUID(TableNameUtil.KQDS_BLK_ZHONGZHI, zhongzhi);
				} else {
					/** 添加病历到 病历库 **/
					KqdsMedicalrecord m = (KqdsMedicalrecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, meid);
					if (m == null) {
						throw new Exception("病历不存在");
					}
					Map<String, String> map = new HashMap<String, String>();
					map.put("meid", meid);
					List<KqdsMedicalrecordZhongzhi> list = (List<KqdsMedicalrecordZhongzhi>) logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, map);
					if (list == null || list.isEmpty()) {
						throw new Exception("病历内容不存在");
					}
					KqdsMedicalrecordZhongzhi zhongzhiM = list.get(0);
					dp.setSeqId(YZUtility.getUUID());
					dp.setMtype(m.getMtype() + "");// 种植一期
					dp.setBlname(blname);
					dp.setType(type);
					dp.setCreatetime(YZUtility.getCurDateTimeStr());
					dp.setCreateuser(person.getSeqId());
					dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
					logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);

					// 病历内容 赋值 到 病历库
					zhongzhi = (KqdsBlkZhongzhi) YZUtility.Obj1ToObj2(zhongzhiM, zhongzhi);
					zhongzhi.setSeqId(YZUtility.getUUID());
					zhongzhi.setMeid(dp.getSeqId());
					zhongzhi.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】

					zhongzhi.setCreatetime(YZUtility.getCurDateTimeStr());
					zhongzhi.setCreateuser(person.getSeqId());
					logic.saveSingleUUID(TableNameUtil.KQDS_BLK_ZHONGZHI, zhongzhi);
				}
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_BLK_ZHONGZHI, zhongzhi, TableNameUtil.KQDS_BLK_ZHONGZHI, request);
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
}