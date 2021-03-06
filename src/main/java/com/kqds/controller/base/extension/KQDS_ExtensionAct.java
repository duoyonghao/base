package com.kqds.controller.base.extension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

import com.kqds.entity.base.KqdsExtension;
import com.kqds.entity.base.KqdsExtensionType;
import com.kqds.entity.base.VisitDataCount;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.extension.KQDS_ExtensionLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_ExtensionAct")
public class KQDS_ExtensionAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_ExtensionAct.class);
	@Autowired
	private KQDS_ExtensionLogic logic;

	@RequestMapping(value = "/toExtensionAdd.act")
	public ModelAndView toExtensionAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/tgjh/extension_add.jsp");
		return mv;
	}

	@RequestMapping(value = "/toExtensionDetail.act")
	public ModelAndView toExtensionDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/tgjh/extension_detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toExtensionEdit.act")
	public ModelAndView toExtensionEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/tgjh/extension_edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toExtensionFinish.act")
	public ModelAndView toExtensionFinish(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/tgjh/extension_finish.jsp");
		return mv;
	}

	@RequestMapping(value = "/toExtensionVisit.act")
	public ModelAndView toExtensionVisit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/tgjh/extension_visit.jsp");
		return mv;
	}

	/**
	 * 编辑推广计划
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editTgjh.act")
	public String editTgjh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String remark = request.getParameter("remark");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空");
			}

			KqdsExtension ext = (KqdsExtension) logic.loadObjSingleUUID(TableNameUtil.KQDS_EXTENSION, seqId);

			if (ext == null) {
				throw new Exception("推广计划不存在");
			}
			ext.setRemark(remark);
			logic.updateSingleUUID(TableNameUtil.KQDS_EXTENSION, ext);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_EXTENSION, ext, TableNameUtil.KQDS_EXTENSION, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
			throw ex;
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
			KqdsExtension dp = new KqdsExtension();
			KqdsExtensionType dptype = new KqdsExtensionType();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}

			if (!YZUtility.isNullorEmpty(seqId)) {
				// 查询该计划下是否还有未完成的记录 有则不修改计划status(无用字段复用，当作大状态来用)
				// 没有记录则修改status为1（已完成）
				int num = logic.checkIsFinish(seqId);
				if (num == 0 || num == 1) {
					// 如果有一条或者少于一条记录 则代表当前操作的记录是本计划内最后一个患者记录 修改本计划所有患者为已完成
					KqdsExtension jh = (KqdsExtension) logic.loadObjSingleUUID(TableNameUtil.KQDS_EXTENSION, seqId);
					KqdsExtensionType tgjh = (KqdsExtensionType) logic.loadObjSingleUUID(TableNameUtil.KQDS_EXTENSION_TYPE, jh.getExtension());
					tgjh.setStatus(1);
					logic.updateSingleUUID(TableNameUtil.KQDS_EXTENSION_TYPE, tgjh);
					// 记录日志
					BcjlUtil.LogBcjl(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_EXTENSION_TYPE, tgjh, TableNameUtil.KQDS_EXTENSION_TYPE, request);
				}
				if (dp.getStatus().equals(ConstUtil.VISIT_STATUS_1)) {
					dp.setStatus(ConstUtil.VISIT_STATUS_1);
					dp.setPostperson(person.getSeqId());
					dp.setFinishtime(YZUtility.getCurDateTimeStr());
					// 记录日志
					BcjlUtil.LogBcjl(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_EXTENSION, dp, TableNameUtil.KQDS_EXTENSION, request);
				}
				logic.updateSingleUUID(TableNameUtil.KQDS_EXTENSION, dp);
			} else {
				// 保存计划名称
				String jhname = request.getParameter("jhname");
				String jhseqId = request.getParameter("jhseqId");
				// jhname 不为空说明 新建计划名称
				if (!YZUtility.isNullorEmpty(jhname)) {
					dptype.setSeqId(YZUtility.getUUID());
					dptype.setStatus(0);
					dptype.setJhname(jhname);
					dptype.setCreatetime(YZUtility.getCurDateTimeStr());
					dptype.setCreateuser(person.getSeqId());
					dptype.setOrganization(organization); // 【前端页面调用，以所在门诊为准】
					logic.saveSingleUUID(TableNameUtil.KQDS_EXTENSION_TYPE, dptype);
				} else if (!YZUtility.isNullorEmpty(jhseqId)) {
					dptype = (KqdsExtensionType) logic.loadObjSingleUUID(TableNameUtil.KQDS_EXTENSION_TYPE, jhseqId);
				}
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_EXTENSION_TYPE, dptype, TableNameUtil.KQDS_EXTENSION_TYPE, request);

				// 保存的对象集合 json格式
				String listdata = request.getParameter("data");
				String visitorStr = request.getParameter("visitor");
				int rowsnum = Integer.parseInt(request.getParameter("rowsnum"));
				String[] visitorArr = visitorStr.split(",");
				int visitorLen = visitorArr.length;
				int size = 0;
				if (rowsnum % visitorLen != 0) {// 两个整数相除,如果有余数则取整数再加一
					size = rowsnum / visitorLen + 1;
				} else {
					size = rowsnum / visitorLen;
				}
				String visittime = request.getParameter("visittime");
				for (int k = 0; k < visitorLen; k++) {// 随机分给执行人
					PushUtil.saveTx4NewExtension(visitorArr[k], visittime, request);
				}

				JSONArray jArray = JSONArray.fromObject(listdata);
				Collection collection = JSONArray.toCollection(jArray, KqdsExtension.class);
				Iterator it = collection.iterator();
				int i = 0;
				while (it.hasNext()) {
					KqdsExtension extension = (KqdsExtension) it.next();
					String uuid = YZUtility.getUUID();
					extension.setSeqId(uuid);
					for (int k = 0; k < visitorLen; k++) {// 随机分给执行人
						if (i < size * (k + 1)) {
							extension.setVisitor(visitorArr[k]);
							break;
						}
					}
					extension.setExtension(dptype.getSeqId());

					extension.setCreatetime(YZUtility.getCurDateTimeStr());
					extension.setCreateuser(person.getSeqId());
					extension.setOrganization(organization);
					logic.saveSingleUUID(TableNameUtil.KQDS_EXTENSION, extension);
					i++;
				}
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
			throw ex;
		}
		return null;
	}

	/**
	 * 不分页查询(客服中心-推广计划)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();

			String status = request.getParameter("status");
			String vtime1 = request.getParameter("vtime1");
			String vtime2 = request.getParameter("vtime2");
			String extension = request.getParameter("extension");
			String visitor = request.getParameter("visitor");
			String planstatus = request.getParameter("planstatus");
			String tuiguang = request.getParameter("tuiguang");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			if (!YZUtility.isNullorEmpty(status) && !status.equals("null") && !status.equals("undefined")) {
				map.put("status", status);
			}
			if (!YZUtility.isNullorEmpty(vtime1) && !vtime1.equals("null") && !vtime1.equals("undefined")) {
				map.put("vtime1", vtime1);
			}
			if (!YZUtility.isNullorEmpty(vtime2) && !vtime2.equals("null") && !vtime2.equals("undefined")) {
				map.put("vtime2", vtime2);
			}
			if (!YZUtility.isNullorEmpty(extension) && !extension.equals("null") && !extension.equals("undefined")) {
				map.put("extension", extension);
			}
			if (!YZUtility.isNullorEmpty(visitor) && !visitor.equals("null") && !visitor.equals("undefined")) {
				map.put("visitor", visitor);
			}
			if (!YZUtility.isNullorEmpty(planstatus) && !planstatus.equals("null") && !planstatus.equals("undefined")) {
				map.put("planstatus", planstatus);
			}
			if (!YZUtility.isNullorEmpty(tuiguang) && !tuiguang.equals("null") && !tuiguang.equals("undefined")) {
				map.put("tuiguang", tuiguang);
			}

			// 门诊过滤条件
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			map.put("organization", organization);

			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> list = logic.selectList2(TableNameUtil.KQDS_EXTENSION, map, visualstaff);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("推广计划", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询各类条数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCountByQuery.act")
	public String getCountByQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String status = request.getParameter("status");
			String vtime1 = request.getParameter("vtime1");
			String vtime2 = request.getParameter("vtime2");
			String extension = request.getParameter("extension");
			String visitor = request.getParameter("visitor");
			String planstatus = request.getParameter("planstatus");
			String tuiguang = request.getParameter("tuiguang");
			if (!YZUtility.isNullorEmpty(tuiguang) && !tuiguang.equals("null") && !tuiguang.equals("undefined")) {
				map.put("tuiguang", tuiguang);
			}
			if (!YZUtility.isNullorEmpty(status) && !status.equals("null") && !status.equals("undefined")) {
				map.put("status", status);
			}
			if (!YZUtility.isNullorEmpty(vtime1) && !vtime1.equals("null") && !vtime1.equals("undefined")) {
				map.put("vtime1", vtime1);
			}
			if (!YZUtility.isNullorEmpty(vtime2) && !vtime2.equals("null") && !vtime2.equals("undefined")) {
				map.put("vtime2", vtime2);
			}
			if (!YZUtility.isNullorEmpty(extension) && !extension.equals("null") && !extension.equals("undefined")) {
				map.put("extension", extension);
			}
			if (!YZUtility.isNullorEmpty(visitor) && !visitor.equals("null") && !visitor.equals("undefined")) {
				map.put("visitor", visitor);
			}
			if (!YZUtility.isNullorEmpty(planstatus) && !planstatus.equals("null") && !planstatus.equals("undefined")) {
				map.put("planstatus", planstatus);
			}

			// 门诊过滤条件
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			map.put("organization", organization);

			String visualstaff = SessionUtil.getVisualstaff(request);
			JSONObject jobj = new JSONObject();
			// 总条数
			int total = logic.selectZongCount(TableNameUtil.KQDS_EXTENSION, map, visualstaff);
			jobj.put("total", total);
			// 分类条数
			List<VisitDataCount> numbers = logic.selectCountByQuery(TableNameUtil.KQDS_EXTENSION, map, visualstaff);
			// 已回访条数
			map.put("status", ConstUtil.VISIT_STATUS_1);// 1已回访
			int yi = logic.selectZongCount(TableNameUtil.KQDS_EXTENSION, map, visualstaff);
			map.put("qm", "1");
			int qmyi = logic.selectZongCount(TableNameUtil.KQDS_EXTENSION, map, visualstaff);
			jobj.put("yihuifang", yi);
			jobj.put("nohuifang", (total - yi));
			BigDecimal b1 = new BigDecimal(yi * 100);
			BigDecimal b2 = new BigDecimal(qmyi * 100);
			BigDecimal ball = new BigDecimal(total);
			if (KqdsBigDecimal.compareTo(ball, BigDecimal.ZERO) == 1) {
				jobj.put("wcl", b1.divide(ball, 1, RoundingMode.HALF_UP));
				jobj.put("qmwcl", b2.divide(ball, 1, RoundingMode.HALF_UP));
			}

			if (numbers.size() > 0) {
				jobj.put("flcounts", numbers);
			} else {
				jobj.put("flcounts", 0);
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询推广表中所有的推广计划
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExtensoionTypeList.act")
	public String getExtensoionTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String planstatus = request.getParameter("planstatus");
			String vtime1 = request.getParameter("vtime1");
			String vtime2 = request.getParameter("vtime2");
			String extension = request.getParameter("extension");
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			if (!YZUtility.isNullorEmpty(planstatus) && !planstatus.equals("null") && !planstatus.equals("undefined")) {
				map.put("planstatus", planstatus);
			}
			if (!YZUtility.isNullorEmpty(vtime1) && !vtime1.equals("null") && !vtime1.equals("undefined")) {
				map.put("vtime1", vtime1);
			}
			if (!YZUtility.isNullorEmpty(vtime2) && !vtime2.equals("null") && !vtime2.equals("undefined")) {
				map.put("vtime2", vtime2);
			}
			if (!YZUtility.isNullorEmpty(extension) && !extension.equals("null") && !extension.equals("undefined")) {
				map.put("extension", extension);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<KqdsExtensionType> en = logic.getExtensoionList(organization, map, visualstaff); // 门诊条件过滤
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
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
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("seqId主键为空");
			}
			List<JSONObject> list = logic.selectDetail(seqId);
			JSONObject jobj = new JSONObject();
			jobj.put("data", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 首页 推广计划
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTodayList.act")
	public String getTodayList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> en = logic.getTodayList(ChainUtil.getCurrentOrganization(request), visualstaff); // 门诊条件过滤
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
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
			KqdsExtension en = (KqdsExtension) logic.loadObjSingleUUID(TableNameUtil.KQDS_EXTENSION, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_EXTENSION, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_EXTENSION, en, TableNameUtil.KQDS_EXTENSION, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}
}