package com.kqds.controller.base.jzmdType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsJzmdType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.jzmdType.KQDS_Jzmd_TypeLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("KQDS_Jzmd_TypeBackAct")
public class KQDS_Jzmd_TypeBackAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Jzmd_TypeBackAct.class);
	@Autowired
	private KQDS_Jzmd_TypeLogic logic;

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/jzmdType/index_ls.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsJzmdType dp = new KqdsJzmdType();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_JZMD_TYPE, dp, TableNameUtil.KQDS_JZMD_TYPE, request);
			} else {
				String uuid = UUID.randomUUID().toString();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId() + "");
				logic.saveSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_JZMD_TYPE, dp, TableNameUtil.KQDS_JZMD_TYPE, request);
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
			KqdsJzmdType en = (KqdsJzmdType) logic.loadObjSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_JZMD_TYPE, en, TableNameUtil.KQDS_JZMD_TYPE, request);
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
			KqdsJzmdType en = (KqdsJzmdType) logic.loadObjSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, seqId);
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
			String jzmd = request.getParameter("jzmd");
			String jzmdchildname = request.getParameter("jzmdchildname");
			String organization = ChainUtil.getOrganizationFromUrl(request);
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(jzmd)) {
				map.put("jzmd", jzmd);
			}
			if (!YZUtility.isNullorEmpty(jzmdchildname)) {
				map.put("jzmdchildname", jzmdchildname);
			}
			map.put("organization", organization);
			JSONObject data = logic.selectWithPage(bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}