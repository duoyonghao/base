package com.kqds.controller.base.activityRecord;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsActivityRecord;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.activityRecord.KQDS_Activity_RecordLogic;
import com.kqds.service.base.scbb.KQDS_ScbbLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Activity_RecordAct")
public class KQDS_Activity_RecordAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_Activity_RecordAct.class);
	@Autowired
	private KQDS_Activity_RecordLogic logic;
	@Autowired
	private KQDS_ScbbLogic scbblogic;

	@RequestMapping(value = "/toActive.act")
	public ModelAndView toActive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/yxzx/active.jsp");
		return mv;
	}

	/**
	 * 新增、编辑
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
			KqdsActivityRecord dp = new KqdsActivityRecord();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_ACTIVITY_RECORD, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_ACTIVITY_RECORD, dp, TableNameUtil.KQDS_ACTIVITY_RECORD, request);
			} else {
				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
				logic.saveSingleUUID(TableNameUtil.KQDS_ACTIVITY_RECORD, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_ACTIVITY_RECORD, dp, TableNameUtil.KQDS_ACTIVITY_RECORD, request);
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
			KqdsActivityRecord en = (KqdsActivityRecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_ACTIVITY_RECORD, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_ACTIVITY_RECORD, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_ACTIVITY_RECORD, en, TableNameUtil.KQDS_ACTIVITY_RECORD, request);
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

			KqdsActivityRecord en = (KqdsActivityRecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_ACTIVITY_RECORD, seqId);
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
	 * 不分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPage.act")
	public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String username = request.getParameter("username");
			String activitycontactsSearch = request.getParameter("activitycontactsSearch");

			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(activitycontactsSearch)) {
				map.put("activitycontacts", activitycontactsSearch);
			}
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("kehu", username);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			}
			List<JSONObject> list = logic.selectNoPage(TableNameUtil.KQDS_ACTIVITY_RECORD, map, organization);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("活动记录", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 下载模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downTemplet.act")
	public void downTemplet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		File f = new File(ConstUtil.ROOT_DIR + "\\model\\模板.xls");
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("模板" + ".xls").getBytes(), "iso-8859-1"));// 下载文件的名称
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(f));
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException ex) {
			YZUtility.DEAL_ERROR("模板下载异常", true, ex, null, logger);
		} finally {
			if (bis != null) {
				bis.close();
			}

			if (bos != null) {
				bos.close();
			}

			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 投入产出柱状图展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTrscColumn.act")
	public String selectTrscColumn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String seqIds = request.getParameter("seqIds");

			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> map2 = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
				map2.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
				map2.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(seqIds)) {
				map.put("seqIds", seqIds);
			}
			List<JSONObject> list = new ArrayList<JSONObject>();
			list = logic.selectTrscColumn(TableNameUtil.KQDS_ACTIVITY_RECORD, map);
			if (list != null && list.size() > 0) {
				for (JSONObject jobj : list) {
					map2.put("usercodes", jobj.getString("kehu"));
					String skje = scbblogic.getYysr(map2);
					String skjeYjj = scbblogic.getYysrYjj(map2);

					BigDecimal sk = new BigDecimal(skje).add(new BigDecimal(skjeYjj));
					jobj.put("skje", sk);
				}
			}

			JSONObject jobj = new JSONObject();
			jobj.put("rows", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 
	 * 咨询情况表格统计
	 * 
	 */
	@RequestMapping(value = "/getWdOrdertj.act")
	public String getWdOrdertj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {

			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String seqIds = request.getParameter("seqIds");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (YZUtility.isNullorEmpty(seqIds)) {
				throw new Exception("请选择活动记录");
			}
			String[] seqIdArr = seqIds.split(",");
			// 网电人员
			List<JSONObject> list = new ArrayList<JSONObject>();
			for (int i = 0; i < seqIdArr.length; i++) {
				KqdsActivityRecord actity = (KqdsActivityRecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_ACTIVITY_RECORD, seqIdArr[i]);
				JSONObject obj = new JSONObject();
				obj.put("xh", i + 1);
				obj.put("username", actity.getActivityname());
				obj.put("createtime", actity.getCreatetime());
				obj.put("outmoney", actity.getOutmoney());
				map.put("usercodes", actity.getKehu());
				// 录单量
				int ldnums = scbblogic.getCountJd(map);
				obj.put("ldnums", ldnums);
				// 预约人数
				int yynums = scbblogic.getCountYy(map);
				obj.put("yynums", yynums);
				// 预约率 = 预约人数/录单量
				Float yyl = (float) 0;
				if (ldnums != 0) {
					yyl = (float) yynums * 100 / ldnums;
				}
				obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
				// 到院人数
				int yysmnums = scbblogic.getCountYysm(map);
				obj.put("yysmnums", yysmnums);
				// 到诊率 = 到院人数/录单量
				Float dzl = (float) 0;
				if (ldnums != 0) {
					dzl = (float) yysmnums * 100 / ldnums;
				}
				obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
				// 收款金额 = 项目收款 + 预交金收款
				// 项目收款 = 实收 - 预交金
				// 预交金收款 =
				String skje = scbblogic.getYysr(map);
				String skjeYjj = scbblogic.getYysrYjj(map);
				String sk = YZUtility.FloatToFixed2(Float.parseFloat(skje) + Float.parseFloat(skjeYjj));
				obj.put("skje", sk);
				// 投入产出比
				BigDecimal trccb = BigDecimal.ZERO;
				if (KqdsBigDecimal.compareTo(actity.getOutmoney(), BigDecimal.ZERO) > 0) {
					trccb = new BigDecimal(sk).divide(actity.getOutmoney(), 2);
				}
				obj.put("trccb", "1:" + trccb);
				// 成交人数
				int cjnums = scbblogic.getCountCj(map);
				obj.put("cjnums", cjnums);
				// 成交率 = 成交人数/到院人数
				Float cjl = (float) 0;
				if (yysmnums != 0) {
					cjl = (float) cjnums * 100 / yysmnums;
				}
				obj.put("cjl", YZUtility.FloatToFixed2(cjl) + "%");
				// 人均消费 = 收款金额/到院人数
				Float rjxf = (float) 0;
				if (yysmnums != 0) {
					rjxf = (float) Float.parseFloat(sk) / yysmnums;
				}
				obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
				list.add(obj);
			}
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("网电预约统计", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}