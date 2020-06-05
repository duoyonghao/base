package com.kqds.controller.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("ChainAct")
public class ChainAct {

	private static Logger logger = LoggerFactory.getLogger(ChainAct.class);

	@Autowired
	private YZDeptLogic deptLogic;

	/**
	 * 获取所有分店列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	@RequestMapping(value = "/getHosList.act")
	public String getHosList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String nocheck = request.getParameter("nocheck");
			YZPerson person = SessionUtil.getLoginPerson(request);
			List<YZDept> list = deptLogic.getHosList(null); // 获取所有门诊列表
			// 情况1：用户登录页面调用
			if (!YZUtility.isNullorEmpty(nocheck) && person == null) {
				JSONObject retrunObj = new JSONObject();
				retrunObj.put("list", list);
				YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
				return null;
			}

			if (person == null) {
				throw new Exception("用户未登录，请重新登录。");
			}

			// 情况2：
			if ((!YZUtility.isNullorEmpty(nocheck) || "admin".equals(person.getUserId())) && !ChainUtil.isOpenTry()) { // 未开启试用
				JSONObject retrunObj = new JSONObject();
				retrunObj.put("list", list);
				YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
				return null;
			}

			// 情况3：除此之外，判断当前用户是否具备查询非当前登录门诊的权限 判断依据，根据部门的manager字段进行判断
			String currentOrganizationCode = ChainUtil.getCurrentOrganization(request); // 当前所登录的门诊编号
			List<YZDept> returnList = new ArrayList<YZDept>(); // 定义返回的门诊列表list
			for (YZDept dept : list) {
				if (currentOrganizationCode.equals(dept.getDeptCode())) {
					returnList.add(dept);
					continue;
				}
				boolean flag = YZUtility.isStrInArrayEach(person.getSeqId(), dept.getManager());
				if (flag) {
					returnList.add(dept);
				}
			}
			JSONObject retrunObj = new JSONObject();
			retrunObj.put("list", returnList);
			retrunObj.put("current", currentOrganizationCode);
			YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
			throw ex;
		}

		return null;
	}

	/**
	 * 根据门诊编号获取门诊信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOrganizationInfoByOrgCode.act")
	public String getOrganizationInfoByOrgCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			String organization = request.getParameter("organization");

			if (YZUtility.isNullorEmpty(organization)) {
				throw new Exception("根据门诊编号获取门诊信息失败，门诊编号为空");
			}

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("DEPT_PARENT", "0");
			filters.put("DEPT_CODE", organization);

			List<YZDept> list = (ArrayList<YZDept>) deptLogic.loadList(TableNameUtil.SYS_DEPT, filters);

			if (list == null || list.size() == 0) {
				throw new Exception("根据门诊编号获取门诊信息失败，门诊不存在");
			}

			JSONObject retrunObj = new JSONObject();
			retrunObj.put("data", list.get(0));
			YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

}
