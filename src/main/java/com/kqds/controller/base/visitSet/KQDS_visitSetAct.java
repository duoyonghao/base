package com.kqds.controller.base.visitSet;

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

import com.kqds.entity.base.KqdsVisitSet;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.visitSet.KQDS_VisitSetLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("KQDS_visitSetAct")
public class KQDS_visitSetAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_visitSetAct.class);
	@Autowired
	private KQDS_VisitSetLogic logic;

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/visitSet/index_ls.jsp");
		return mv;
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/visitSet/list_kqds_visitSet.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/visitSet/edit_kqds_visitSet.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail.act")
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/visitSet/detail_kqds_visitSet.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/visitSet/add_kqds_visitSet.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsVisitSet dp = new KqdsVisitSet();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_VISIT_SET, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.UPDATE, BcjlUtil.KQDS_VISIT_SET, dp, TableNameUtil.KQDS_VISIT_SET, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getOrganizationFromUrl(request)); // 【后台数据维护，以页面传入的门诊编号为主】
				logic.saveSingleUUID(TableNameUtil.KQDS_VISIT_SET, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT_SET, dp, TableNameUtil.KQDS_VISIT_SET, request);
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

			KqdsVisitSet en = (KqdsVisitSet) logic.loadObjSingleUUID(TableNameUtil.KQDS_VISIT_SET, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_VISIT_SET, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_VISIT_SET, en, TableNameUtil.KQDS_VISIT_SET, request);
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
			KqdsVisitSet en = (KqdsVisitSet) logic.loadObjSingleUUID(TableNameUtil.KQDS_VISIT_SET, seqId);

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
			String hffl = request.getParameter("hffl");
			String userpriv = request.getParameter("userpriv");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(hffl)) {
				map.put("hffl", hffl);
			}
			if (!YZUtility.isNullorEmpty(userpriv)) {
				map.put("userpriv", userpriv);
			}
			map.put("organization", ChainUtil.getOrganizationFromUrl(request));
			JSONObject data = logic.selectWithPage(TableNameUtil.KQDS_VISIT_SET, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}