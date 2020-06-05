package com.kqds.controller.wx.order;

import java.util.HashMap;
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
import com.kqds.service.wx.order.KqdsOrderLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KqdsOrderAct")
public class KqdsOrderAct {

	private static Logger logger = LoggerFactory.getLogger(KqdsOrderAct.class);
	@Autowired
	private KqdsOrderLogic kqdsOrderLogic;

	@RequestMapping(value = "/toList4Front.act")
	public ModelAndView toList4Front(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/wechat/order_manage/list4front.jsp");
		return mv;
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/wechat/order_manage/list.jsp");
		return mv;
	}

	/**
	 * 分页查询(网电)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPageWd.act")
	public String selectPageWd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			JSONObject data = kqdsOrderLogic.selectPageWd(TableNameUtil.KQDS_NET_ORDER, bp, map, "1");
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查看详情(网电)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectDetailWd.act")
	public String selectDetailWd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			BootStrapPage bp = new BootStrapPage();
			String seqId = request.getParameter("seqId");
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(seqId)) {
				map.put("seqId", seqId);
			}
			JSONObject data = kqdsOrderLogic.selectPageWd(TableNameUtil.KQDS_NET_ORDER, bp, map, "0");
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询(门诊)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPageMz.act")
	public String selectPageMz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			JSONObject data = kqdsOrderLogic.selectPageMz(TableNameUtil.KQDS_HOSPITAL_ORDER, bp, map, "1");
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查看详情(门诊)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectDetailMz.act")
	public String selectDetailMz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			BootStrapPage bp = new BootStrapPage();
			String seqId = request.getParameter("seqId");
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(seqId)) {
				map.put("seqId", seqId);
			}
			JSONObject data = kqdsOrderLogic.selectPageMz(TableNameUtil.KQDS_HOSPITAL_ORDER, bp, map, "0");
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}
