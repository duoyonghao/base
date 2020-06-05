package com.kqds.controller.sys.blct;

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

import com.kqds.entity.base.KqdsBlct;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.blct.YZBlctLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZBlctAct")
public class YZBlctAct {

	private static Logger logger = LoggerFactory.getLogger(YZBlctAct.class);

	@Autowired
	private YZBlctLogic logic;

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/medicalRecord/blct/index_ls.jsp");
		return mv;
	}

	@RequestMapping(value = "/toListBlctQt.act")
	public ModelAndView toListBlctQt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/medicalRecord/blct/list_kqds_blct_qt.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSubIndex.act")
	public ModelAndView toSubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/blct/subindex.jsp");
		return mv;
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/blct/list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toLeft.act")
	public ModelAndView toLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/blct/left.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/blct/edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("parentCode", parentCode);
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/blct/newAdd.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlctList.act")
	public ModelAndView toBlctList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/medicalRecord/blct/list_kqds_blct.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlctEdit.act")
	public ModelAndView toBlctEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/medicalRecord/blct/edit_blct.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlctDetail.act")
	public ModelAndView toBlctDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/medicalRecord/blct/detail_blct.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBlctAdd.act")
	public ModelAndView toBlctAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		String cttype = request.getParameter("cttype");
		String ctnexttype = request.getParameter("ctnexttype");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.addObject("cttype", cttype);
		mv.addObject("ctnexttype", ctnexttype);
		mv.setViewName("/kqdsFront/medicalRecord/blct/add_blct.jsp");
		return mv;
	}

	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			KqdsBlct dp = new KqdsBlct();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(dp.getCtname())) {
				throw new Exception("名称不能为空");
			}

			if (!YZUtility.isNullorEmpty(seqId)) {
				KqdsBlct tmp = (KqdsBlct) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLCT, seqId);
				if (tmp == null) {
					throw new Exception("记录不存在");
				}
				tmp.setCtname(dp.getCtname());
				tmp.setOrderno(dp.getOrderno());
				tmp.setCttype(dp.getCttype());
				tmp.setCtnexttype(dp.getCtnexttype());
				tmp.setCreatetime(YZUtility.getCurDateTimeStr());
				tmp.setCreateuser(person.getSeqId());
				logic.updateSingleUUID(TableNameUtil.KQDS_BLCT, tmp);
				SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.KQDS_BLCT, dp, TableNameUtil.KQDS_BLCT, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				logic.saveSingleUUID(TableNameUtil.KQDS_BLCT, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_BLCT, dp, TableNameUtil.KQDS_BLCT, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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

			KqdsBlct en = (KqdsBlct) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLCT, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_BLCT, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_BLCT, en, TableNameUtil.KQDS_BLCT, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
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

			KqdsBlct en = (KqdsBlct) logic.loadObjSingleUUID(TableNameUtil.KQDS_BLCT, seqId);
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
			// 初始化分页实体类
			String ctnexttype = request.getParameter("ctnexttype");
			String cttype = request.getParameter("cttype");
			String ctname = request.getParameter("ctname");
			String useflag = request.getParameter("useflag");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();
			if (YZUtility.isNotNullOrEmpty(ctnexttype)) {
				map.put("ctnexttype", ctnexttype);
			}
			if (YZUtility.isNotNullOrEmpty(cttype)) {
				map.put("cttype", cttype);
			}
			if (YZUtility.isNotNullOrEmpty(ctname)) {
				map.put("ctname", ctname);
			}
			if (YZUtility.isNotNullOrEmpty(useflag)) {
				map.put("useflag", useflag);
			}
			// ### 门诊条件过滤
			JSONObject jobj = logic.selectWithPage(TableNameUtil.KQDS_BLCT, bp, map, ChainUtil.getOrganizationFromUrlCanNull(request));
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
			String ctnexttype = request.getParameter("ctnexttype");
			String cttype = request.getParameter("cttype");
			String ctname = request.getParameter("ctname");
			String useflag = request.getParameter("useflag");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();
			if (YZUtility.isNotNullOrEmpty(ctnexttype)) {
				map.put("ctnexttype", ctnexttype);
			}
			if (YZUtility.isNotNullOrEmpty(cttype)) {
				map.put("cttype", cttype);
			}
			if (YZUtility.isNotNullOrEmpty(ctname)) {
				map.put("ctname", ctname);
			}
			if (YZUtility.isNotNullOrEmpty(useflag)) {
				map.put("useflag", useflag);
			}

			List<JSONObject> list = logic.selectList(TableNameUtil.KQDS_BLCT, map);
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
	@RequestMapping(value = "/getBlctList.act")
	public String getBlctList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			Map<String, String> map = new HashMap<String, String>();
			map.put("useflag", "0");
			List<JSONObject> en = logic.selectList(TableNameUtil.KQDS_BLCT, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}