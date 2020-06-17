package com.kqds.controller.sys.para;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.member.KQDS_MemberLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZParaAct")
public class YZParaAct {
	private static Logger logger = LoggerFactory.getLogger(YZParaAct.class);
	@Autowired
	private YZParaLogic paraLogic;
	@Autowired
	private KQDS_MemberLogic mlogic;

	@RequestMapping(value = "/toIndex.act")
	public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/para/index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toIndexJf.act")
	public ModelAndView toIndexJf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/para/index_jf.jsp");
		return mv;
	}

	@RequestMapping(value = "/toJf.act")
	public ModelAndView toJf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/admin/para/list_JF.jsp");
		return mv;
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/admin/para/list.jsp");
		return mv;
	}

	/**
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
			YZPara dp = new YZPara();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(dp.getParaName())) {
				throw new Exception("参数名称不能为空");
			}

			int num = paraLogic.countByName(dp);
			if (num >= 1) {
				throw new Exception("参数名称已存在, 请重新填写");
			}

			if (!YZUtility.isNullorEmpty(seqId)) {

				YZPara tmp = (YZPara) paraLogic.loadObjSingleUUID(TableNameUtil.SYS_PARA, seqId);
				if (tmp == null) {
					throw new Exception("按钮不存在");
				}
				// 这几个字段修改时，不更新
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				// 这几个字段修改时，不更新 end..
				paraLogic.updateSingleUUID(TableNameUtil.SYS_PARA, dp);
				if ("HYK_BINDING".equals(tmp.getParaName())) {
					// 不需要绑定会员卡
					if (dp.getParaValue().equals("0")) {
						mlogic.editIcno(TableNameUtil.KQDS_MEMBER);
					} else {
						mlogic.emptyIcno(TableNameUtil.KQDS_MEMBER);
					}
				}
				// 记录日志
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_PARA, dp, TableNameUtil.SYS_PARA, request);

			} else {
				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				paraLogic.saveSingleUUID(TableNameUtil.SYS_PARA, dp);
				if ("HYK_BINDING".equals(dp.getParaName())) {
					// 不需要绑定会员卡
					if (dp.getParaValue().equals("0")) {
						// mlogic.editIcno(TableNameUtil.KQDS_MEMBER,
						// request);
					} else {
						// mlogic.emptyIcno(TableNameUtil.KQDS_MEMBER,
						// request);
					}
				}
				// 记录日志
				SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_PARA, dp, TableNameUtil.SYS_PARA, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String organization = request.getParameter("organization");
			//String organization = ChainUtil.getOrganizationFromUrl(request);
			if (YZUtility.isNullorEmpty(organization)) {
				throw new Exception("参数错误：organization不能为空！");
			}
			List<JSONObject> list = paraLogic.selectList(organization);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.act")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			paraLogic.deleteBySeqIds(seqId, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键不能为空");
			}
			YZPara button = (YZPara) paraLogic.loadObjSingleUUID(TableNameUtil.SYS_PARA, seqId);

			if (button == null) {
				throw new Exception("参数不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, JSONObject.fromObject(button).toString());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}
