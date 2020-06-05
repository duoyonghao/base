package com.kqds.controller.sys.fkfs;

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
import com.kqds.entity.sys.YZFkfs;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZFkfsAct")
public class YZFkfsAct {
	private static Logger logger = LoggerFactory.getLogger(YZFkfsAct.class);
	@Autowired
	private YZFkfsLogic logic;

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/fkfs/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toDxflEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/fkfs/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			YZFkfs dp = new YZFkfs();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(dp.getPayname())) {
				throw new Exception("名称不能为空");
			}

			if (!YZUtility.isNullorEmpty(seqId)) {
				YZFkfs tmp = (YZFkfs) logic.loadObjSingleUUID(TableNameUtil.SYS_FKFS, seqId);
				if (tmp == null) {
					throw new Exception("记录不存在");
				}
				tmp.setPayname(dp.getPayname());
				tmp.setCreatetime(YZUtility.getCurDateTimeStr());
				tmp.setCreateuser(person.getSeqId());
				logic.updateSingleUUID(TableNameUtil.SYS_FKFS, tmp);
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_FKFS, dp, TableNameUtil.SYS_FKFS, request);
				JSONObject sysParaCost = logic.getCostField();
				request.getSession().setAttribute(SessionUtil.LOGIN_SYS_COSTFIELD, sysParaCost);
				JSONObject sysParaMember = logic.getMemberField();
				request.getSession().setAttribute(SessionUtil.LOGIN_SYS_MEMBERFIELD, sysParaMember);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 启用/禁用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateFlagBySeqIds.act")
	public String updateFlagBySeqIds(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");
			String flag = request.getParameter("flag");
			if (!("0".equals(flag) || "1".equals(flag))) { // 如果flag 不等于0 或者 1
				throw new Exception("状态标识不正确");
			}

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			logic.updateFlagBySeqIds(seqId, flag, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 详情【后台调用】
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

			YZFkfs en = (YZFkfs) logic.loadObjSingleUUID(TableNameUtil.SYS_FKFS, seqId);
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
	 * 分页查询 【后台调用】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			// ### 门诊条件过滤
			JSONObject jobj = logic.selectWithPage(TableNameUtil.SYS_FKFS, bp, ChainUtil.getOrganizationFromUrlCanNull(request));
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 不分页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPage.act")
	public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 初始化分页实体类
			String ismoney = request.getParameter("ismoney");
			String forcost = request.getParameter("forcost");
			String useflag = request.getParameter("useflag");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();
			if (ismoney != null && !ismoney.equals("")) {
				map.put("ismoney", ismoney);
			}
			if (forcost != null && !forcost.equals("")) {
				map.put("forcost", forcost);
			}
			if (useflag != null && !useflag.equals("")) {
				map.put("useflag", useflag);
			}

			List<JSONObject> list = logic.selectList(TableNameUtil.SYS_FKFS, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询付款方式（未禁用的）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFkfsList.act")
	public String getFkfsList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			Map<String, String> map = new HashMap<String, String>();
			map.put("useflag", "0");
			List<JSONObject> en = logic.selectList(TableNameUtil.SYS_FKFS, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询会员卡充值方式（未禁用的）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMemberFkfsList.act")
	public String getMemberFkfsList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			List<JSONObject> en = logic.getMemberFkfsList(TableNameUtil.SYS_FKFS);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}