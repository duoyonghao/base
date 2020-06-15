package com.kqds.controller.wx.templatemsg;

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

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXTemplatemsg;
import com.kqds.service.wx.core.WXUtil4TemplateMsgLogic;
import com.kqds.service.wx.templatemsg.WXTemplateMsgLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.export.ExportTable;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("WXTemplateMsgAct")
public class WXTemplateMsgAct {

	private static Logger logger = LoggerFactory.getLogger(WXTemplateMsgAct.class);
	@Autowired
	private WXTemplateMsgLogic logic;

	@Autowired
	private WXUtil4TemplateMsgLogic wxUtil4TemplateMsgLogic;

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/wechat/templatemsg/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			List<JSONObject> list = logic.selectList(map);
			JSONObject json = new JSONObject();
			json.put("list", list);
			YZUtility.DEAL_SUCCESS(json, "查询成功", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
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

			WXTemplatemsg en = (WXTemplatemsg) logic.loadObjSingleUUID(TableNameUtil.WX_TEMPLATEMSG, seqId);
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
	 * 管理页面调用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/selectPage4Manage.act")
	public String selectPage4Manage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());

			Map<String, String> map = new HashMap<String, String>();

			// 导出
			if (!YZUtility.isNullorEmpty(flag)) {
				map.put("flag", flag);
			}

			JSONObject data = logic.selectPage(bp, map);
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("消息模板列表", fieldArr, fieldnameArr, (List<JSONObject>) data.getJSONArray("rows"), response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 获取模板列表【当前公众号的】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get_all_private_template.act")
	public String get_all_private_template(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			/*************** 微信回复文本信息 ***********************/
			JSONObject json = wxUtil4TemplateMsgLogic.get_all_private_template(request);
			YZUtility.DEAL_SUCCESS(json, "同步消息模板列表成功！", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 获取模板列表【当前公众号的】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del_private_template.act")
	public String del_private_template(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String template_id = request.getParameter("templateId");

			if (YZUtility.isNullorEmpty(template_id)) {
				throw new Exception("template_id不能为空");
			}

			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("seqId不能为空");
			}

			/*************** 微信回复文本信息 ***********************/
			JSONObject json = WXUtil4TemplateMsgLogic.del_private_template(template_id, request);

			if (0 == json.getInt("errcode")) {
				WXTemplatemsg msg = (WXTemplatemsg) logic.loadObjSingleUUID(TableNameUtil.WX_TEMPLATEMSG, seqId);
				if (msg != null) {
					logic.deleteSingleUUID(TableNameUtil.WX_TEMPLATEMSG, seqId);
				}
			}

			YZUtility.DEAL_SUCCESS(json, "删除消息模板成功！", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

}
