package com.kqds.controller.sys.unit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.sys.YZOrganization;
import com.kqds.service.sys.unit.YZUnitLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZUnitAct")
public class YZUnitAct {
	private Logger logger = LoggerFactory.getLogger(YZUnitAct.class);
	@Autowired
	private YZUnitLogic unitLogic;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.act")
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZOrganization dp = new YZOrganization();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键不能为空");
			}

			if (YZUtility.isNullorEmpty(dp.getUnitName())) {
				throw new Exception("单位名称不能为空");
			}

			YZOrganization tmp = (YZOrganization) unitLogic.loadObjSingleUUID(TableNameUtil.SYS_ORGANIZATION, seqId);

			if (tmp == null) {
				throw new Exception("单位记录不存在");
			}
			unitLogic.updateSingleUUID(TableNameUtil.SYS_ORGANIZATION, dp);
			// 记录日志
			SysLogUtil.log(SysLogUtil.UPDATE, SysLogUtil.SYS_ORGANIZATION, dp, TableNameUtil.SYS_ORGANIZATION, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			JSONObject unit = unitLogic.getUnitDetail();

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, unit);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}
