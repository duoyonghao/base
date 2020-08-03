package com.kqds.controller.base.receiveinfo;

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

import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.receiveinfo.KQDS_ReceiveInfoLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("KQDS_ReceiveInfoAct")
public class KQDS_ReceiveInfoAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_ReceiveInfoAct.class);
	@Autowired
	private KQDS_ReceiveInfoLogic logic;
	@Autowired
	private KQDS_UserDocumentLogic userLogic;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private KQDS_REGLogic reglogic;
	@RequestMapping(value = "/toReceiveWin.act")
	public ModelAndView toReceiveWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/zxjl/receiveWin.jsp");
		return mv;
	}

	@RequestMapping(value = "/toReceive.act")
	public ModelAndView toReceive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/index/center/receive.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			KqdsReceiveinfo dp = new KqdsReceiveinfo();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			String isauto = request.getParameter("isauto");// 失去焦点时保存的
			String devItem = request.getParameter("devItem"); // 潜在开发项目
			if (!YZUtility.isNullorEmpty(seqId)) {
				KqdsReceiveinfo en = (KqdsReceiveinfo) logic.loadObjSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, seqId);
				en.setDetaildesc(dp.getDetaildesc());
				en.setFailreason1(dp.getFailreason1());
				en.setDoctors(dp.getDoctors());
				en.setMainSuit(dp.getMainSuit());
				en.setScheme(dp.getScheme());
				en.setPrice(dp.getPrice());
				en.setOrderProject(dp.getOrderProject());
				en.setOrderPlan(dp.getOrderPlan());
				en.setFollow(dp.getFollow());
				en.setFailreasonMark(dp.getFailreasonMark());
				en.setOthermark(dp.getOthermark());
				if (!YZUtility.isNullorEmpty(dp.getFailreason1()) || !YZUtility.isNullorEmpty(dp.getDetaildesc())) {
					en.setAskstatus(1);
				} else {
					en.setAskstatus(0);
				}
				en.setDevItem(devItem);
				if (!en.getDetaildesc().equals("") || !en.getFailreason1().equals("")) {
					// 点击保存按钮才记录
					if (YZUtility.isNullorEmpty(isauto)) {
						// 记录日志
						BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.KQDS_RECEIVEINFO, en, en.getUsercode(), TableNameUtil.KQDS_RECEIVEINFO, request);
					}
				}
				logic.updateSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, en);

				// 已接诊时，判断是否需要设定 患者档案的咨询人员字段
				KqdsReg reg = (KqdsReg) logic.loadObjSingleUUID(TableNameUtil.KQDS_REG, en.getRegno());
				if (en.getAskstatus() == 1 && !YZUtility.isNullorEmpty(reg.getAskperson())) {
					userLogic.setUserDocAskPerson(request, en.getUsercode(), reg.getAskperson());
				}

				/** 此表停用 **/
				// KQDS_ReceiveInfo_Content content = new
				// KQDS_ReceiveInfo_Content();
				// content.setSeqId(YZUtility.getUUID());
				// content.setZs(zs);
				// content.setCheck(check);
				// content.setJy(jy);
				// content.setMember(member);
				// content.setFy(fy);
				// content.setReceiveid(seqId);
				// content.setCreatetime(YZUtility.getCurDateTimeStr());
				// content.setCreateuser(person.getSeqId());
				// content.setOrganization(ChainUtil.getCurrentOrganization(request));
				// logic.saveSingleUUID(content,
				// TableNameUtil.KQDS_RECEIVEINFO_CONTENT, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 不分页查询 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/NoselectPage.act")
	public String NoselectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			String regno = request.getParameter("regno");

			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			// map.put("AskPerson", person.getSeqId()+"");
			if (usercode != null && !usercode.equals("")) {
				map.put("usercode", usercode);
			}
			if (regno != null && !regno.equals("")) {
				map.put("regno", regno);
			}

			List<JSONObject> list = (List<JSONObject>) logic.noSelectWithPage(TableNameUtil.KQDS_RECEIVEINFO, map);

			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}