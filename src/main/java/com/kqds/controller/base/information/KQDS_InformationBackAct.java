package com.kqds.controller.base.information;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
import com.kqds.entity.base.KqdsInformation;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.information.KQDS_InformationLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("KQDS_InformationBackAct")
public class KQDS_InformationBackAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_InformationBackAct.class);
	@Autowired
	private KQDS_InformationLogic logic = new KQDS_InformationLogic();

	@RequestMapping(value = "/toIndex.act")
	public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/information/list_kqds_information.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/information/edit_kqds_information.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail.act")
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/information/detail_kqds_information.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/information/add_kqds_information.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			KqdsInformation dp = new KqdsInformation();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_INFORMATION, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_INFORMATION, dp, TableNameUtil.KQDS_INFORMATION, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request)); // 【后台数据维护，以页面传入的门诊编号为主】
				logic.saveSingleUUID(TableNameUtil.KQDS_INFORMATION, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_INFORMATION, dp, TableNameUtil.KQDS_INFORMATION, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
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
			KqdsInformation en = (KqdsInformation) logic.loadObjSingleUUID(TableNameUtil.KQDS_INFORMATION, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_INFORMATION, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_INFORMATION, en, TableNameUtil.KQDS_INFORMATION, request);
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
			KqdsInformation en = (KqdsInformation) logic.loadObjSingleUUID(TableNameUtil.KQDS_INFORMATION, seqId);
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
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String type = request.getParameter("type");
			String title = request.getParameter("title");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);
			}
			if (!YZUtility.isNullorEmpty(title)) {
				map.put("title", title);
			}
			map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_INFORMATION, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
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
	 * @throws IOException
	 */
	@RequestMapping(value = "/excelStandardTemplateOut.act")
	public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File f = new File(ConstUtil.ROOT_DIR + "\\model\\信息库导入模板.xls");
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("信息库导入模板" + ".xls").getBytes(), "iso-8859-1"));// 下载文件的名称
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