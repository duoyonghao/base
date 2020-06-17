package com.kqds.controller.base.medicalRecord;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordReview;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.mediaRecord.Kqds_MediaRecordLogic;
import com.kqds.util.base.code.BLCodeUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_MedicalRecord_ChaiXianAct")
public class KQDS_MedicalRecord_ChaiXianAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_MedicalRecord_ChaiXianAct.class);
	@Autowired
	private Kqds_MediaRecordLogic logic;

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
			KqdsMedicalrecord dp = new KqdsMedicalrecord();
			KqdsMedicalrecordReview chaixian = new KqdsMedicalrecordReview();
			BeanUtils.populate(dp, request.getParameterMap());
			BeanUtils.populate(chaixian, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			String subSeqId = request.getParameter("subSeqId"); // 子表主键

			if (!YZUtility.isNullorEmpty(seqId)) {
				if (YZUtility.isNullorEmpty(subSeqId)) {
					throw new Exception("病历内容表主键不能为空");
				}

				KqdsMedicalrecord m = (KqdsMedicalrecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
				if (m == null) {
					throw new Exception("病历不存在");
				}

				if (2 == m.getStatus()) {
					// 只有院长才能编辑病历
					String yuanzhang = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_YUANZHANG_SEQID);
					if (!"admin".equals(person.getUserId()) && YZUtility.isStrInArrayEach(person.getUserPriv(), yuanzhang)
							&& YZUtility.isStrInArrayEach(person.getUserPrivOther(), yuanzhang)) {
						throw new Exception("不允许修改已提交的病历");
					}
				}

				dp.setMtype(m.getMtype()); // 病历类别不做更新
				dp.setUsercode(m.getUsercode());
				dp.setRegno(m.getRegno());

				KqdsMedicalrecordReview subM = (KqdsMedicalrecordReview) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, subSeqId);
				if (subM == null) {
					throw new Exception("病历内容不存在");
				}

				dp.setRegno(m.getRegno()); // 挂号主键
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request));
				logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);
				// 更新子表
				chaixian.setSeqId(subSeqId);
				logic.updateSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, chaixian);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEDICALRECORD_REVIEW, chaixian, chaixian.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_REVIEW,
						request);

			} else {

				String organization = ChainUtil.getCurrentOrganization(request);

				String mseqId = BLCodeUtil.getBLCode(organization);
				dp.setMtype(3);// 种植一期
				dp.setSeqId(mseqId);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
				logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, dp);

				chaixian.setSeqId(YZUtility.getUUID());
				chaixian.setMeid(dp.getSeqId());
				chaixian.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】

				chaixian.setCreatetime(YZUtility.getCurDateTimeStr());
				chaixian.setCreateuser(person.getSeqId());
				logic.saveSingleUUID(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, chaixian);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MEDICALRECORD_REVIEW, chaixian, chaixian.getUsercode(), TableNameUtil.KQDS_MEDICALRECORD_REVIEW, request);

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