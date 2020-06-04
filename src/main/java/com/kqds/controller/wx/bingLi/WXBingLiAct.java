package com.kqds.controller.wx.bingLi;

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

import com.kqds.entity.base.KqdsMedicalrecordCz;
import com.kqds.entity.base.KqdsMedicalrecordFz;
import com.kqds.entity.base.KqdsMedicalrecordRestoration;
import com.kqds.entity.base.KqdsMedicalrecordReview;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi2;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.wx.bingLi.WXBingLiLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.bus.BLUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("WXBingLiAct")
public class WXBingLiAct {

	private static Logger logger = LoggerFactory.getLogger(WXBingLiAct.class);
	@Autowired
	private WXBingLiLogic logic = new WXBingLiLogic();

	/**
	 * 分页查询-种植
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPage4ZhongZhi.act")
	public String selectPage4ZhongZhi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			JSONObject data = logic.selectPage4ZhongZhi(TableNameUtil.KQDS_MEDICALRECORD, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			JSONObject data = logic.selectPage(TableNameUtil.KQDS_MEDICALRECORD, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String meid = request.getParameter("meid");
			String mtype = request.getParameter("mtype");

			Map<String, String> map = new HashMap<String, String>();
			map.put("meid", meid);
			JSONObject jobj = new JSONObject();

			if (YZUtility.isNullorEmpty(mtype) || YZUtility.isNullorEmpty(meid)) {
				throw new Exception("参数传递错误，参数值不能为空");
			}
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
}
