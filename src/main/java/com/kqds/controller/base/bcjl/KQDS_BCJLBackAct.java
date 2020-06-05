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

import com.kqds.entity.base.KqdsBcjl;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.bcjl.KQDS_BCJLLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("KQDS_BCJLBackAct")
public class KQDS_BCJLBackAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_BCJLBackAct.class);

	@Autowired
	private KQDS_BCJLLogic logic;

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/bcjl/index_ls.jsp");
		return mv;
	}

	@RequestMapping(value = "/toListBcjl.act")
	public ModelAndView toListBcjl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/admin/log/list_kqds_bcjl.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail.act")
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/admin/log/detail_kqds_bcjl.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBackList.act")
	public ModelAndView toBackList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/bcjl/list_kqds_bcjl.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBackDetail.act")
	public ModelAndView toBackDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/bcjl/detail_kqds_bcjl.jsp");
		return mv;
	}

	/**
	 * 病程记录详情
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
			KqdsBcjl en = (KqdsBcjl) logic.loadObjSingleUUID(TableNameUtil.KQDS_BCJL, seqId);
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
	 * 分页查询 业务日志
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
			String username = request.getParameter("username");
			String usercode = request.getParameter("usercode");
			String createuser = request.getParameter("createuser");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}
			map.put("logtype", "0"); // 查询业务日志
			map.put("organization", ChainUtil.getOrganizationFromUrl(request));
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_BCJL, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询 业务日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPageLogin.act")
	public String selectPageLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			map.put("logtype", "1"); // 查询系统日志
			map.put("jlname", "登录日志");
			map.put("organization", ChainUtil.getOrganizationFromUrl(request));
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_BCJL, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询 业务日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPage4SysLog.act")
	public String selectPage4SysLog(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String username = request.getParameter("username");
			String usercode = request.getParameter("usercode");
			String createuser = request.getParameter("createuser");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}

			map.put("logtype", "1"); // 查询系统日志
			map.put("organization", ChainUtil.getOrganizationFromUrl(request));
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_BCJL, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询会员卡金额修改记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String askpersonList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("会员卡操作记录主键不能为空");
			}
			// 根据特定规则的jlname查询对应的咨询修改日志记录
			String specialFlag = BcjlUtil.UPDATE_ASKPERSON + seqId;
			List<JSONObject> list = logic.getListByJlName(specialFlag);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}