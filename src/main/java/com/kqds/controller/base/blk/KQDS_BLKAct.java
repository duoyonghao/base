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
import com.kqds.entity.base.KqdsBlkCz;
import com.kqds.entity.base.KqdsBlkFz;
import com.kqds.entity.base.KqdsBlkRestoration;
import com.kqds.entity.base.KqdsBlkReview;
import com.kqds.entity.base.KqdsBlkZhongzhi;
import com.kqds.entity.base.KqdsBlkZhongzhi2;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordCz;
import com.kqds.entity.base.KqdsMedicalrecordFz;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.blk.KQDS_BLKLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.bus.BLUtil;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.UserPrivUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_BLKAct")
public class KQDS_BLKAct {

	/**
	 * ##############################################################
	 * 经过评估，暂时不将病历库的Action类进行前后台划分！
	 */

	private static Logger logger = LoggerFactory.getLogger(KQDS_BLKAct.class);

	@Autowired
	private KQDS_BLKLogic logic;

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/medicalRecord/blk/index_ls.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlkTree.act")
	public ModelAndView toBlkTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/medicalRecord/list_kqds_blk_tree.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlkList.act")
	public ModelAndView toBlkList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/medicalRecord/blk/list_kqds_blk.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlkCopy.act")
	public ModelAndView toBlkCopy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/medicalRecord/blk/copy.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlkDetail.act")
	public ModelAndView toBlkDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/medicalRecord/blk/detail_kqds_blk.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMedicalrecord4blk.act")
	public ModelAndView toMedicalrecord4blk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/medicalRecord/medicalrecord4blk.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMedicalrecord_Edit4blk.act")
	public ModelAndView toMedicalrecord_Edit4blk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_edit4blk.jsp");
		return mv;
	}

	// 修改
	@RequestMapping(value = "/toZhongZhiYiQi_Edit.act")
	public ModelAndView toZhongZhiYiQi_Edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/bingli/blk/zhongzhi1/zhongzhiyiqi_edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhi_Suture_Removal_Edit.act")
	public ModelAndView toZhongZhi_Suture_Removal_Edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/bingli/blk/fucha/zhongzhi_suture_removal_edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhiErQi_Edit.act")
	public ModelAndView toZhongZhiErQi_Edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/bingli/blk/zhongzhi2/zhongzhierqi_edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhi_XiuFu_Edit.act")
	public ModelAndView toZhongZhi_XiuFu_Edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/bingli/blk/xiufu/zhongzhi_xiufu_edit.jsp");
		return mv;
	}

	// 详情
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
		mv.setViewName("/kqdsFront/bingli/blk/zhongzhi1/zhongzhiyiqi_detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhi_Suture_Removal_Detail.act")
	public ModelAndView toZhongZhi_Suture_Removal_Detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		String type = request.getParameter("type");
		String detailFlag = request.getParameter("detailFlag");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.addObject("type", type);
		mv.addObject("detailFlag", detailFlag);
		mv.setViewName("/kqdsFront/bingli/blk/fucha/zhongzhi_suture_removal_detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhiErQi_Detail.act")
	public ModelAndView toZhongZhiErQi_Detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		String type = request.getParameter("type");
		String detailFlag = request.getParameter("detailFlag");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.addObject("type", type);
		mv.addObject("detailFlag", detailFlag);
		mv.setViewName("/kqdsFront/bingli/blk/zhongzhi2/zhongzhierqi_detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhi_XiuFu_Detail.act")
	public ModelAndView toZhongZhi_XiuFu_Detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String mtype = request.getParameter("mtype");
		String type = request.getParameter("type");
		String detailFlag = request.getParameter("detailFlag");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("mtype", mtype);
		mv.addObject("type", type);
		mv.addObject("detailFlag", detailFlag);
		mv.setViewName("/kqdsFront/bingli/blk/xiufu/zhongzhi_xiufu_detail.jsp");
		return mv;
	}

	// 添加
	@RequestMapping(value = "/toZhongZhiYiQi_Add.act")
	public ModelAndView toZhongZhiYiQi_Add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/bingli/blk/zhongzhi1/zhongzhiyiqi_add.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhi_Suture_Removal_Add.act")
	public ModelAndView toZhongZhi_Suture_Removal_Add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/bingli/blk/fucha/zhongzhi_suture_removal_add.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhiErQi_Add.act")
	public ModelAndView toZhongZhiErQi_Add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/bingli/blk/zhongzhi2/zhongzhierqi_add.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhongZhi_XiuFu_Add.act")
	public ModelAndView toZhongZhi_XiuFu_Add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.setViewName("/kqdsFront/bingli/blk/xiufu/zhongzhi_xiufu_add.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEmtp.act")
	public ModelAndView toEmtp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/bingli/inc/emtp.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlkSelect.act")
	public ModelAndView toBlkSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/bingli/blk/blk_select.jsp");
		return mv;
	}

	/**
	 * 拷贝病历模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/copyBLK.act")
	public String copyBLK(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			String seqId = request.getParameter("seqId");
			String blname = request.getParameter("blname");
			String blkfl = request.getParameter("blkfl");
			String type = request.getParameter("type");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			if (YZUtility.isNullorEmpty(blname)) {
				throw new Exception("病历库名称不允许为空");
			}

			if (YZUtility.isNullorEmpty(blkfl)) {
				throw new Exception("病历库分类不允许为空");
			}

			if (YZUtility.isNullorEmpty(type)) {
				throw new Exception("是否自用不允许为空");
			}

			KqdsBlk blk = (KqdsBlk) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK, seqId);
			if (blk == null) {
				throw new Exception("病历库不存在");
			}

			// 重新赋值
			blk.setSeqId(YZUtility.getUUID());
			blk.setBlname(blname);
			blk.setType(type);
			blk.setBlkfl(blkfl);
			blk.setCreatetime(YZUtility.getCurDateTimeStr());
			blk.setCreateuser(person.getSeqId());
			logic.saveSingleUUID(TableNameUtil.KQDS_BLK, blk);

			if ("0".equals(blk.getMtype())) {
				KqdsBlkCz recordCZ = (KqdsBlkCz) logic.getBlkCzByblkid(seqId);
				if (recordCZ == null) {
					throw new Exception("该病历库对应的初诊记录不存在，病历库主键为：" + seqId);
				}
				// 重新赋值
				recordCZ.setSeqId(YZUtility.getUUID());
				recordCZ.setBlkid(blk.getSeqId());
				recordCZ.setCreatetime(YZUtility.getCurDateTimeStr());
				recordCZ.setCreateuser(person.getSeqId());
				logic.saveSingleUUID(TableNameUtil.KQDS_BLK_CZ, recordCZ);
			}

			if ("1".equals(blk.getMtype())) {
				KqdsBlkFz recordFZ = (KqdsBlkFz) logic.getBlkFzByblkid(seqId);
				if (recordFZ == null) {
					throw new Exception("该病历库对应的复诊记录不存在，病历库主键为：" + seqId);
				}
				recordFZ.setSeqId(YZUtility.getUUID());
				recordFZ.setBlkid(blk.getSeqId());
				recordFZ.setCreatetime(YZUtility.getCurDateTimeStr());
				recordFZ.setCreateuser(person.getSeqId());
				logic.saveSingleUUID(TableNameUtil.KQDS_BLK_FZ, recordFZ);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 病历详情页面，增加另存病历库的功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBlk.act")
	public String addBlk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String seqId = request.getParameter("seqId"); // 病历主键
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空");
			}

			String blkfl = request.getParameter("blkfl"); // 病历库分类
			if (YZUtility.isNullorEmpty(blkfl)) {
				throw new Exception("病历库分类为空");
			}

			String type = request.getParameter("type"); // 自用1、公用0
			if (YZUtility.isNullorEmpty(type)) {
				throw new Exception("自用公用标识为空");
			}

			String blname = request.getParameter("blname"); // 自用1、公用0
			if (YZUtility.isNullorEmpty(blname)) {
				throw new Exception("病历库名称为空");
			}

			KqdsMedicalrecord record = (KqdsMedicalrecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
			if (record == null) {
				throw new Exception("病历不存在，病历主键为：" + seqId);
			}

			String blkSeqId = YZUtility.getUUID(); // 病历库主键
			KqdsBlk blk = new KqdsBlk();
			blk.setSeqId(blkSeqId);
			blk.setCreateuser(person.getSeqId());
			blk.setCreatetime(YZUtility.getCurDateTimeStr());
			blk.setBlname(blname);
			blk.setBlkfl(blkfl);
			blk.setType(type);
			blk.setMtype(String.valueOf(record.getMtype()));
			blk.setOrganization(ChainUtil.getCurrentOrganization(request));
			logic.saveSingleUUID(TableNameUtil.KQDS_BLK, blk); // 入库

			int mtype = record.getMtype(); // 0初诊 1复诊
			if (0 == mtype) {
				KqdsMedicalrecordCz recordCZ = (KqdsMedicalrecordCz) logic.getMcz(seqId);
				if (recordCZ == null) {
					throw new Exception("该病历对应的初诊记录不存在，病历主键为：" + seqId);
				}

				KqdsBlkCz cz = new KqdsBlkCz();
				cz.setSeqId(YZUtility.getUUID());
				cz.setBlkid(blk.getSeqId());
				cz.setCzzs(recordCZ.getCzzs());
				cz.setCzxbs(recordCZ.getCzxbs());
				cz.setCzjws(recordCZ.getCzjws());
				cz.setCztgjc(recordCZ.getCztgjc());
				cz.setCzfzjc(recordCZ.getCzfzjc());
				cz.setCzzd(recordCZ.getCzzd());
				cz.setCzzlfa(recordCZ.getCzzlfa());
				cz.setCzcl(recordCZ.getCzcl());
				cz.setCzremark(recordCZ.getCzremark());
				cz.setCzgms(recordCZ.getCzgms());
				cz.setCzjzs(recordCZ.getCzjzs());
				cz.setCzjyjg(recordCZ.getCzjyjg());
				cz.setCzyz(recordCZ.getCzyz());
				cz.setOrganization(blk.getOrganization());
				cz.setCreateuser(person.getSeqId());
				cz.setCreatetime(YZUtility.getCurDateTimeStr());
				logic.saveSingleUUID(TableNameUtil.KQDS_BLK_CZ, cz);
			}

			if (1 == mtype) {
				KqdsMedicalrecordFz recordFZ = (KqdsMedicalrecordFz) logic.getMfz(seqId);
				if (recordFZ == null) {
					throw new Exception("该病历对应的复诊记录不存在，病历主键为：" + seqId);
				}

				KqdsBlkFz fz = new KqdsBlkFz();
				fz.setSeqId(YZUtility.getUUID());
				fz.setBlkid(blk.getSeqId());
				fz.setFzzs(recordFZ.getFzzs());
				fz.setFzjc(recordFZ.getFzjc());
				fz.setFzcl(recordFZ.getFzcl());
				fz.setFzremark(recordFZ.getFzremark());
				fz.setFzjyjg(recordFZ.getFzjyjg());
				fz.setFzyz(recordFZ.getFzyz());
				fz.setFzzlfa(recordFZ.getFzzlfa());
				fz.setFzzd(recordFZ.getFzzd());
				fz.setOrganization(blk.getOrganization());
				fz.setCreateuser(person.getSeqId());
				fz.setCreatetime(YZUtility.getCurDateTimeStr());
				logic.saveSingleUUID(TableNameUtil.KQDS_BLK_FZ, fz);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}

		return null;
	}

	/**
	 * 入库，修改 【前台调用】
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

			KqdsBlk dp = new KqdsBlk();
			BeanUtils.populate(dp, request.getParameterMap());

			dp.setSeqId(YZUtility.getUUID());
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);

			/**
			 * ############################# 下面没有区分 新增 和修改 ，后续要进行优化 ！！！ yangsen 17-6-17
			 */

			if (dp.getMtype().equals("0")) { // 初诊病历库
				KqdsBlkCz cz = new KqdsBlkCz();
				BeanUtils.populate(cz, request.getParameterMap());

				cz.setSeqId(YZUtility.getUUID());
				cz.setBlkid(dp.getSeqId());
				cz.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】

				cz.setCreateuser(person.getSeqId());
				cz.setCreatetime(YZUtility.getCurDateTimeStr());
				logic.saveSingleUUID(TableNameUtil.KQDS_BLK_CZ, cz);

				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_BLK_CZ, cz, TableNameUtil.KQDS_BLK_CZ, request);

			} else { // 复诊病历库
				KqdsBlkFz fz = new KqdsBlkFz();
				BeanUtils.populate(fz, request.getParameterMap());
				fz.setSeqId(YZUtility.getUUID());
				fz.setBlkid(dp.getSeqId());
				fz.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request)); // 门诊条件过滤【前台调用】

				fz.setCreateuser(person.getSeqId());
				fz.setCreatetime(YZUtility.getCurDateTimeStr());
				logic.saveSingleUUID(TableNameUtil.KQDS_BLK_FZ, fz);

				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_BLK_FZ, fz, TableNameUtil.KQDS_BLK_FZ, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 入库，修改 【后台调用】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate4Back.act")
	public String insertOrUpdate4Back(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsBlk dp = new KqdsBlk();
			BeanUtils.populate(dp, request.getParameterMap());

			dp.setSeqId(YZUtility.getUUID());
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			dp.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request)); // 【前端页面调用，以所在门诊为准】
			logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);

			/**
			 * ############################# 下面没有区分 新增 和修改 ，后续要进行优化 ！！！ yangsen 17-6-17
			 */

			if (dp.getMtype().equals("0")) { // 初诊病历库
				KqdsBlkCz cz = new KqdsBlkCz();
				BeanUtils.populate(cz, request.getParameterMap());

				cz.setSeqId(YZUtility.getUUID());
				cz.setBlkid(dp.getSeqId());
				cz.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request)); // 【前端页面调用，以所在门诊为准】

				cz.setCreateuser(person.getSeqId());
				cz.setCreatetime(YZUtility.getCurDateTimeStr());
				logic.saveSingleUUID(TableNameUtil.KQDS_BLK_CZ, cz);

				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_BLK_CZ, cz, TableNameUtil.KQDS_BLK_CZ, request);

			} else { // 复诊病历库
				KqdsBlkFz fz = new KqdsBlkFz();
				BeanUtils.populate(fz, request.getParameterMap());
				fz.setSeqId(YZUtility.getUUID());
				fz.setBlkid(dp.getSeqId());
				fz.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request)); // 门诊条件过滤【前台调用】

				fz.setCreateuser(person.getSeqId());
				fz.setCreatetime(YZUtility.getCurDateTimeStr());
				logic.saveSingleUUID(TableNameUtil.KQDS_BLK_FZ, fz);

				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_BLK_FZ, fz, TableNameUtil.KQDS_BLK_FZ, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			KqdsBlk en = (KqdsBlk) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK, seqId);

			String tableName = null;
			String seqIdName = null;
			String jlname = null;

			if (BLUtil.MTYPE_0.equals(en.getMtype())) {
				tableName = TableNameUtil.KQDS_BLK_CZ;
				seqIdName = "blkid";
				jlname = BcjlUtil.KQDS_BLK_CZ;
			}

			if (BLUtil.MTYPE_1.equals(en.getMtype())) {
				tableName = TableNameUtil.KQDS_BLK_FZ;
				seqIdName = "blkid";
				jlname = BcjlUtil.KQDS_BLK_FZ;

			}

			if (BLUtil.MTYPE_2.equals(en.getMtype())) {
				tableName = TableNameUtil.KQDS_BLK_ZHONGZHI;
				seqIdName = "meid";
				jlname = BcjlUtil.KQDS_BLK_ZHONGZHI;
			}

			if (BLUtil.MTYPE_3.equals(en.getMtype())) {
				tableName = TableNameUtil.KQDS_BLK_REVIEW;
				seqIdName = "meid";
				jlname = BcjlUtil.KQDS_BLK_REVIEW;
			}

			if (BLUtil.MTYPE_4.equals(en.getMtype())) {
				tableName = TableNameUtil.KQDS_BLK_ZHONGZHI2;
				seqIdName = "meid";
				jlname = BcjlUtil.KQDS_BLK_ZHONGZHI2;
			}

			if (BLUtil.MTYPE_5.equals(en.getMtype())) {
				tableName = TableNameUtil.KQDS_BLK_RESTORATION;
				jlname = BcjlUtil.KQDS_BLK_RESTORATION;
				seqIdName = "meid";
			}

			// 删除子表
			String sql = "delete from " + tableName + " where " + seqIdName + " = '" + seqId + "' ";
			logic.deleteMS(tableName, seqIdName, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_BLK, seqId);

			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, jlname, sql, tableName, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
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

			KqdsBlk en = (KqdsBlk) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK, seqId);

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

			if (YZUtility.isNullorEmpty(mtype) || YZUtility.isNullorEmpty(meid)) {
				throw new Exception("参数传递错误，参数值不能为空");
			}

			KqdsBlk en = (KqdsBlk) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK, meid);

			Map<String, String> map = new HashMap<String, String>();
			JSONObject jobj = new JSONObject();
			jobj.put("blname", en.getBlname());
			if (mtype.equals(BLUtil.MTYPE_0)) {// 初诊
				map.put("blkid", meid);
				List<KqdsBlkCz> cz = (List<KqdsBlkCz>) logic.loadList(TableNameUtil.KQDS_BLK_CZ, map);
				if (cz.size() == 0) {
					throw new Exception("初诊病历内容不存在");
				}
				jobj.put("data", cz.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_1)) {
				map.put("blkid", meid);
				List<KqdsBlkFz> fz = (List<KqdsBlkFz>) logic.loadList(TableNameUtil.KQDS_BLK_FZ, map);

				if (fz.size() == 0) {
					throw new Exception("复诊病历内容不存在");
				}
				jobj.put("data", fz.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_2)) { // ###########################种植1期病历
				map.put("meid", meid);
				List<KqdsBlkZhongzhi> zhongzhi = (List<KqdsBlkZhongzhi>) logic.loadList(TableNameUtil.KQDS_BLK_ZHONGZHI, map);
				if (zhongzhi.size() == 0) {
					throw new Exception("种植1期病历病历内容不存在");
				}

				jobj.put("data", zhongzhi.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_3)) { // ###########################术后拆线
				map.put("meid", meid);
				List<KqdsBlkReview> chaixian = (List<KqdsBlkReview>) logic.loadList(TableNameUtil.KQDS_BLK_REVIEW, map);
				if (chaixian.size() == 0) {
					throw new Exception("术后拆线病历病历内容不存在");
				}

				jobj.put("data", chaixian.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_4)) { // ###########################种植2期病历
				map.put("meid", meid);
				List<KqdsBlkZhongzhi2> erqi = (List<KqdsBlkZhongzhi2>) logic.loadList(TableNameUtil.KQDS_BLK_ZHONGZHI2, map);
				if (erqi.size() == 0) {
					throw new Exception("种植2期病历病历内容不存在");
				}

				jobj.put("data", erqi.get(0));
			} else if (mtype.equals(BLUtil.MTYPE_5)) { // ###########################种植2期病历
				map.put("meid", meid);
				List<KqdsBlkRestoration> xiufu = (List<KqdsBlkRestoration>) logic.loadList(TableNameUtil.KQDS_BLK_RESTORATION, map);
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
	 * 分页查询 【### 后台调用 ### 】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String blkfl = request.getParameter("blkfl");
			String blname = request.getParameter("blname");
			String mtype = request.getParameter("mtype");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(blkfl)) {
				map.put("blkfl", blkfl);
			}
			if (!YZUtility.isNullorEmpty(blname)) {
				map.put("blname", blname);
			}
			if (!YZUtility.isNullorEmpty(mtype)) {
				map.put("mtype", mtype);
			}
			map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_BLK, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 医疗中心-初复诊病历库管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/blkManager.act")
	public String blkManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String blname = request.getParameter("blname") == null ? "" : request.getParameter("blname");
			String blkfl = request.getParameter("blkfl") == null ? "" : request.getParameter("blkfl");
			String mtype = request.getParameter("mtype") == null ? "" : request.getParameter("mtype");
			String type = request.getParameter("type") == null ? "" : request.getParameter("type");

			Map<String, String> map = new HashMap<String, String>();
			if (YZUtility.isNotNullOrEmpty(blname)) {
				map.put("blname", blname);
			}
			if (YZUtility.isNotNullOrEmpty(blkfl)) {
				map.put("blkfl", blkfl);
			}
			if (YZUtility.isNotNullOrEmpty(mtype)) {
				map.put("mtype", mtype);
			}
			if (YZUtility.isNotNullOrEmpty(type)) {
				map.put("type", type);
			}

			map.put("createuser", person.getSeqId());
			map.put("organization", ChainUtil.getCurrentOrganization(request));

			List<JSONObject> list = logic.blkManager(TableNameUtil.KQDS_BLK, map, ChainUtil.getCurrentOrganization(request));
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询 【### 前台调用 ### 】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPage4Front.act")
	public String selectPage4Front(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			String search = request.getParameter("search") == null ? "" : request.getParameter("search");
			Map<String, String> map = new HashMap<String, String>();
			map.put("blname", search);
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // ###
																				// 门诊过滤条件
			// 种植病历公共、自用病历库共用此方法
			String mtype = request.getParameter("mtype"); // 0 初诊 1复诊 2种植1期
															// 3术后拆线 4种植2期 5修复
			if (!YZUtility.isNullorEmpty(mtype)) {
				map.put("mtype", mtype);
			}
			String type = request.getParameter("type"); // 0 标准病历库 1自用病历库
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);
				if (UserPrivUtil.ADMIN_SEQ_ID.equals(type) && !"admin".equals(person.getUserId())) { // 自用的，除了管理员，其他人
					// 只能查看自己新建的
					map.put("createuser", person.getSeqId());
				}
			}
			List<JSONObject> list = logic.selectWithPage4Front(TableNameUtil.KQDS_BLK, map, ChainUtil.getCurrentOrganization(request));
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 不分页查询 【### 前台调用 ### 】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPage4Front.act")
	public String selectNoPage4Front(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String search = request.getParameter("search") == null ? "" : request.getParameter("search");
			Map<String, String> map = new HashMap<String, String>();
			map.put("blname", search);
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // ###
																				// 门诊过滤条件

			// 种植病历公共、自用病历库共用此方法
			String mtype = request.getParameter("mtype"); // 0 初诊 1复诊 2种植1期
															// 3术后拆线 4种植2期 5修复

			if (!YZUtility.isNullorEmpty(mtype)) {
				map.put("mtype", mtype);
			}

			String type = request.getParameter("type"); // 0 标准病历库 1自用病历库
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);

				if (UserPrivUtil.ADMIN_SEQ_ID.equals(type) && !"admin".equals(person.getUserId())) { // 自用的，除了管理员，其他人
					// 只能查看自己新建的
					map.put("createuser", person.getSeqId());
				}
			}

			JSONObject jobj = logic.selectWithNoPage4Front(TableNameUtil.KQDS_BLK, bp, map, ChainUtil.getCurrentOrganization(request));
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
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

			KqdsBlk dp = (KqdsBlk) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK, seqId);

			if (dp == null) {
				throw new Exception("病历记录不存在");
			}

			logic.updateSingleUUID(TableNameUtil.KQDS_BLK, dp);

			if ("0".equals(mtype)) { // 初诊
				KqdsBlkCz cz = (KqdsBlkCz) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK_CZ, subSeqId);
				if (cz == null) {
					throw new Exception("该病历对应的初诊记录不存在");
				}

				KqdsBlkCz czPage = new KqdsBlkCz();
				BeanUtils.populate(czPage, request.getParameterMap());
				// 重新赋下值
				czPage.setSeqId(subSeqId);
				czPage.setBlkid(seqId);

				logic.updateSingleUUID(TableNameUtil.KQDS_BLK_CZ, czPage);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_BLK_CZ, czPage, null, TableNameUtil.KQDS_BLK_CZ, request);
			}

			if ("1".equals(mtype)) { // 复诊

				KqdsBlkFz fz = (KqdsBlkFz) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK_FZ, subSeqId);
				if (fz == null) {
					throw new Exception("该病历对应的复诊记录不存在");
				}

				KqdsBlkFz fzPage = new KqdsBlkFz();
				BeanUtils.populate(fzPage, request.getParameterMap());
				// 重新赋下值
				fzPage.setSeqId(subSeqId);
				fzPage.setBlkid(seqId);

				logic.updateSingleUUID(TableNameUtil.KQDS_BLK_FZ, fzPage);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_BLK_FZ, fzPage, null, TableNameUtil.KQDS_BLK_FZ, request);
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