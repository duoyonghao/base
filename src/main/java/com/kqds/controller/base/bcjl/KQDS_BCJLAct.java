package com.kqds.controller.base.bcjl;

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
import com.kqds.service.base.bcjl.KQDS_BCJLLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("KQDS_BCJLAct")
public class KQDS_BCJLAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_BCJLAct.class);
	@Autowired
	private KQDS_BCJLLogic logic;

	@RequestMapping(value = "/toLzjlWin.act")
	public ModelAndView toLzjlWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/lzjl/lzjlWin.jsp");
		return mv;
	}

	/**
	 * 不分页查询-流转记录 【查询所有，不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPageLz.act")
	public String selectPageLz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			String regno = request.getParameter("regno");
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			map.put("ifshow", "0");
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			if (!YZUtility.isNullorEmpty(regno)) {
				map.put("regno", regno);
			}
			List<JSONObject> list = logic.selectWithPageLzjl(TableNameUtil.KQDS_BCJL, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}