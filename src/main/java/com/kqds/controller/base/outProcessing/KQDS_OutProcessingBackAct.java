package com.kqds.controller.base.outProcessing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.kqds.entity.base.KqdsOutprocessing;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.outProcessing.KQDS_OutProcessingLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("KQDS_OutProcessingBackAct")
public class KQDS_OutProcessingBackAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessingBackAct.class);
	@Autowired
	private KQDS_OutProcessingLogic logic;

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toJgIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/outProcessing/index_ls.jsp");
		return mv;
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/outProcessing/list_kqds_outprocessing.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/outProcessing/edit_kqds_outprocessing.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail.act")
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/outProcessing/detail_kqds_outprocessing.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/outProcessing/add_kqds_outprocessing.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsOutprocessing dp = new KqdsOutprocessing();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_OUTPROCESSING, dp, TableNameUtil.KQDS_OUTPROCESSING, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request)); // 【后台数据维护，以页面传入的门诊编号为主】
				logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING, dp, TableNameUtil.KQDS_OUTPROCESSING, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	// 根据项目编号查询-新增时调用，修改的不调用
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/yzBywjgxmbh.act")
	public String yzBywjgxmbh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String wjgxmbh = request.getParameter("wjgxmbh");
			Map<String, String> map = new HashMap<String, String>();
			map.put("wjgxmbh", wjgxmbh);
			boolean result = true;
			List<KqdsOutprocessing> en = (List<KqdsOutprocessing>) logic.loadList(TableNameUtil.KQDS_OUTPROCESSING, map);

			if (en != null && en.size() > 0) {
				result = false;
			}
			YZUtility.DEAL_SUCCESS_VALID(result, response);
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
			int count = logic.delelteBySeqIds(seqId, request);
			YZUtility.DEAL_SUCCESS(null, "删除" + count + "条记录", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除所有
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll.act")
	public String deleteAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int count = logic.delelteAll(ChainUtil.getOrganizationFromUrlCanNull(request), request);
			YZUtility.DEAL_SUCCESS(null, "清空" + count + "条记录", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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
			KqdsOutprocessing en = (KqdsOutprocessing) logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING, seqId);
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

	/*
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String mrjgc = request.getParameter("mrjgc");
			String wjgfl = request.getParameter("wjgfl");
			String wjgmc = request.getParameter("wjgmc");
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(mrjgc)) {
				map.put("mrjgc", mrjgc);
			}
			if (!YZUtility.isNullorEmpty(wjgfl)) {
				map.put("wjgfl", wjgfl);
			}
			if (!YZUtility.isNullorEmpty(wjgmc)) {
				map.put("wjgmc", wjgmc);
			}
			// 导出
			if (!YZUtility.isNullorEmpty(flag)) {
				map.put("flag", flag);
			}
			map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_OUTPROCESSING, bp, map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("加工项目", fieldArr, fieldnameArr, (List<JSONObject>) data.getJSONArray("rows"), response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/excelStandardTemplateOut.act")
	public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File f = new File(ConstUtil.ROOT_DIR + "\\model\\加工项目模板.xls");
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("加工项目模板" + ".xls").getBytes(), "iso-8859-1"));// 下载文件的名称
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
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
}