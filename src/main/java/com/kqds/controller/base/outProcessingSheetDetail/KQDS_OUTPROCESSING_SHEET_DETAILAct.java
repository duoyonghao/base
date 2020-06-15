package com.kqds.controller.base.outProcessingSheetDetail;

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

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.outProcessingSheetDetail.KQDS_OutProcessingSheet_DetailLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("KQDS_OUTPROCESSING_SHEET_DETAILAct")
public class KQDS_OUTPROCESSING_SHEET_DETAILAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_OUTPROCESSING_SHEET_DETAILAct.class);
	@Autowired
	private KQDS_OutProcessingSheet_DetailLogic logic;

	/**
	 * 不分页查询 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListNoPage.act")
	public String getListNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String outprocessingsheetno = request.getParameter("outprocessingsheetno");
			Map<String, String> map = new HashMap<String, String>();
			map.put("outprocessingsheetno", outprocessingsheetno);
			List<JSONObject> list = logic.selectByitem(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 不分页查询 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();

			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			} else {
				// 使用可见人员配置
				String visualstaff = SessionUtil.getVisualstaff(request);
				map.put("doctorno", visualstaff);
			}
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			String num = request.getParameter("num");
			String doctor = request.getParameter("doctor");
			String queryInput = request.getParameter("queryInput");
			String status = request.getParameter("status");

			String fjtime1 = request.getParameter("fjtime1");
			String fjtime2 = request.getParameter("fjtime2");
			String hjtime1 = request.getParameter("hjtime1");
			String hjtime2 = request.getParameter("hjtime2");
			String dztime1 = request.getParameter("dztime1");
			String dztime2 = request.getParameter("dztime2");
			String fgtime1 = request.getParameter("fgtime1");
			String fgtime2 = request.getParameter("fgtime2");
			String createtime1 = request.getParameter("createtime1");
			String createtime2 = request.getParameter("createtime2");
			if (num == null) {
				num = "1";// 如果num为NULL 加载改患者所有的单子
			}
			if (!YZUtility.isNullorEmpty(fjtime1) && !fjtime1.equals("null") && !fjtime1.equals("undefined")) {
				map.put("fjtime1", fjtime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(fjtime2) && !fjtime2.equals("null") && !fjtime2.equals("undefined")) {
				map.put("fjtime2", fjtime2 + ConstUtil.TIME_END);
			}

			if (!YZUtility.isNullorEmpty(hjtime1) && !hjtime1.equals("null") && !hjtime1.equals("undefined")) {
				map.put("hjtime1", hjtime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(hjtime2) && !hjtime2.equals("null") && !hjtime2.equals("undefined")) {
				map.put("hjtime2", hjtime2 + ConstUtil.TIME_END);
			}

			if (!YZUtility.isNullorEmpty(dztime1) && !dztime1.equals("null") && !dztime1.equals("undefined")) {
				map.put("dztime1", dztime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(dztime2) && !dztime2.equals("null") && !dztime2.equals("undefined")) {
				map.put("dztime2", dztime2 + ConstUtil.TIME_END);
			}

			if (!YZUtility.isNullorEmpty(fgtime1) && !fgtime1.equals("null") && !fgtime1.equals("undefined")) {
				map.put("fgtime1", fgtime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(fgtime2) && !fgtime2.equals("null") && !fgtime2.equals("undefined")) {
				map.put("fgtime2", fgtime2 + ConstUtil.TIME_END);
			}
			// 创建时间
			if (!YZUtility.isNullorEmpty(createtime1) && !createtime1.equals("null") && !createtime1.equals("undefined")) {
				map.put("createtime1", createtime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(createtime2) && !createtime2.equals("null") && !createtime2.equals("undefined")) {
				map.put("createtime2", createtime2 + ConstUtil.TIME_END);
			}

			if (!YZUtility.isNullorEmpty(doctor) && !doctor.equals("null") && !doctor.equals("undefined")) {
				map.put("doctor", doctor);
			}
			if (!YZUtility.isNullorEmpty(queryInput) && !queryInput.equals("null") && !queryInput.equals("undefined")) {
				map.put("queryInput", queryInput);
			}
			if (!YZUtility.isNullorEmpty(status) && !status.equals("null") && !status.equals("undefined")) {
				map.put("status", status);
			}
			String sno = request.getParameter("sno");
			if (!YZUtility.isNullorEmpty(sno) && !sno.equals("null") && !sno.equals("undefined")) {
				map.put("sno", sno);
			}
			String unit = request.getParameter("unit");
			if (!YZUtility.isNullorEmpty(unit) && !unit.equals("null") && !unit.equals("undefined")) {
				map.put("processingfactory", unit);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				// 根据所选门诊进行查询
				map.put("organization", ChainUtil.getCurrentOrganization(request));
			} else {
				map.put("organization", organization);
			}
			List<JSONObject> list = logic.selectListByQuery(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, map, num);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("加工明细列表", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}