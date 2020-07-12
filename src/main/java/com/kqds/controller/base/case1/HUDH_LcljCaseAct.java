package com.kqds.controller.base.case1;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.entity.base.HudhLcljBase;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.case1.HUDH_lcljCaseLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/HUDH_LcljCaseAct")
public class HUDH_LcljCaseAct {
	private static Logger logger = LoggerFactory.getLogger(HUDH_LcljCaseAct.class);
	@Autowired
	private HUDH_lcljCaseLogic logic;

	@RequestMapping(value = "/insert.act")
	public String insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			HudhLcljBase hudhLcljBase = new HudhLcljBase();
			BeanUtils.populate(hudhLcljBase, request.getParameterMap());
			hudhLcljBase.setCreateuser(person.getSeqId());
			hudhLcljBase.setId(YZUtility.getUUID());
			hudhLcljBase.setOrganization(ChainUtil.getCurrentOrganization(request));
			hudhLcljBase.setCreatetime(YZUtility.getCurDateTimeStr());
			hudhLcljBase.setStatus("0");
			logic.insertSelective(hudhLcljBase);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/select.act")
	public String select(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String LcljId = request.getParameter("id");
			List<JSONObject> list = logic.selectWithPage(LcljId);
			YZUtility.RETURN_LIST(list,response,logger);
			//YZUtility.RETURN_OBJ(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/update.act")
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HudhLcljBase hudhLcljBase = new HudhLcljBase();
			BeanUtils.populate(hudhLcljBase, request.getParameterMap());
			logic.update(hudhLcljBase);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.HUDH_LCLJ_BASE, hudhLcljBase, hudhLcljBase.getUsercode(), "HUDH_LCLJ_BASE", request);
				
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/selectById.act")
	public String selectById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			HudhLcljBase list = logic.selectById(id);
			YZUtility.RETURN_OBJ(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}
