package com.kqds.controller.wx.news;

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

import com.kqds.controller.base.printSet.KQDS_Print_SetBackAct;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXNewsitem;
import com.kqds.service.wx.news.WXNewsItemLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("WXNewsItemAct")
public class WXNewsItemAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Print_SetBackAct.class);
	@Autowired
	private WXNewsItemLogic logic;

	@RequestMapping(value = "/toAddNew.act")
	public ModelAndView toAddNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		String newsid = request.getParameter("newsid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.addObject("newsid", newsid);
		mv.setViewName("/wechat/newsItem/add.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail.act")
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/wechat/newsItem/detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toShowMessage.act")
	public ModelAndView toShowMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String newsid = request.getParameter("newsid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("newsid", newsid);
		mv.setViewName("/wechat/news/showmessage.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String newsid = request.getParameter("newsid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("newsid", newsid);
		mv.setViewName("/wechat/newsItem/update.jsp");
		return mv;
	}

	/**
	 * 新建、编辑
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
			WXNewsitem dp = new WXNewsitem();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.WX_NEWSITEM, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.WX_NEWSITEM, dp, TableNameUtil.WX_NEWSITEM, request);
			} else {
				String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
				if (YZUtility.isNullorEmpty(organization)) {
					organization = ChainUtil.getCurrentOrganization(request);
				}

				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(organization); // 【后台数据维护，以页面传入的门诊编号为主】
				dp.setCreatedate(dp.getCreatetime().substring(0, 10));
				logic.saveSingleUUID(TableNameUtil.WX_NEWSITEM, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.WX_NEWSITEM, dp, TableNameUtil.WX_NEWSITEM, request);
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

			WXNewsitem en = (WXNewsitem) logic.loadObjSingleUUID(TableNameUtil.WX_NEWSITEM, seqId);
			logic.deleteSingleUUID(TableNameUtil.WX_NEWSITEM, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.WX_NEWSITEM, en, TableNameUtil.WX_NEWSITEM, request);
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

			WXNewsitem en = (WXNewsitem) logic.loadObjSingleUUID(TableNameUtil.WX_NEWSITEM, seqId);
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
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());

			Map<String, String> map = new HashMap<String, String>();
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			// ### 门诊条件过滤
			map.put("organization", organization);

			JSONObject data = logic.selectWithPage(TableNameUtil.WX_NEWSITEM, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}
