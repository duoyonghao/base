package com.kqds.controller.base.treatItemTc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsTreatitemTc;
import com.kqds.entity.base.KqdsTreatitemTcType;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItemTc.KQDS_TreatItem_TcLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_TreatItem_TcAct")
public class KQDS_TreatItem_TcAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_TreatItem_TcAct.class);
	@Autowired
	private KQDS_TreatItem_TcLogic logic;

	@RequestMapping(value = "/toIndexLs.act")
	public ModelAndView toWdyySearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqds/treatItemTc/index_ls.jsp");
		return mv;
	}

	/**
	 * 另存套餐 前端调用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertList.act")
	public String insertList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsTreatitemTcType dptype = new KqdsTreatitemTcType();
			KqdsTreatitemTcType dpname = new KqdsTreatitemTcType();
			String tctype = request.getParameter("tctype");
			String tcname = request.getParameter("tcname");
			// 判断套餐类型是否存在
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", tctype);
			map.put("parentid", "0");
			String menzhen = ChainUtil.getCurrentOrganization(request);
			/** 由于是前端调用的，所以这里取当前门诊 **/
			List<KqdsTreatitemTcType> list = (List<KqdsTreatitemTcType>) logic.loadList(TableNameUtil.KQDS_TREATITEM_TC_TYPE, map);
			if (list != null && list.size() > 0) {
				// 存在套餐类型 则不新建
				dptype = list.get(0);
				// 新建套餐名称
				dpname.setSeqId(YZUtility.getUUID());
				dpname.setName(tcname);
				dpname.setIsopen(0);
				dpname.setParentid(dptype.getSeqId());
				dpname.setOrganization(menzhen);
				dpname.setCreatetime(YZUtility.getCurDateTimeStr());
				dpname.setCreateuser(person.getSeqId());
				logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dpname);
			} else {
				// 新建套餐类型
				dptype.setSeqId(YZUtility.getUUID());
				dptype.setName(tctype);
				dptype.setIsopen(0);
				dptype.setParentid("0");
				dptype.setOrganization(menzhen);
				dptype.setCreatetime(YZUtility.getCurDateTimeStr());
				dptype.setCreateuser(person.getSeqId());
				logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dptype);

				// 新建套餐名称
				dpname.setSeqId(YZUtility.getUUID());
				dpname.setName(tcname);
				dpname.setIsopen(0);
				dpname.setParentid(dptype.getSeqId());
				dpname.setOrganization(menzhen);
				dpname.setCreatetime(dptype.getCreatetime());
				dpname.setCreateuser(person.getSeqId());
				logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dpname);
			}
			// 获取的对象集合 json格式
			String listdata = request.getParameter("params");
			JSONArray jArray = JSONArray.fromObject(listdata);
			Collection collection = JSONArray.toCollection(jArray, KqdsTreatitemTc.class);
			Iterator it = collection.iterator();
			// 保存套餐明细
			KqdsTreatitemTc detail = new KqdsTreatitemTc();
			while (it.hasNext()) {
				detail = (KqdsTreatitemTc) it.next();
				detail.setTcnameid(dpname.getSeqId());
				detail.setSeqId(YZUtility.getUUID());
				detail.setCreatetime(YZUtility.getCurDateTimeStr());
				detail.setCreateuser(person.getSeqId());
				detail.setOrganization(dptype.getOrganization());
				detail.setOrganization(menzhen); // ### 【前端调用，以当前所在门诊为主】
				logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, detail);
			}

			// 记录日志
			JSONObject logcontent = new JSONObject();
			logcontent.put("tctype", tctype);
			logcontent.put("tcname", tcname);
			BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_TREATITEM_TC_TYPE, logcontent, TableNameUtil.KQDS_USERDOCUMENT, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断该套餐类型下是否存在 该套餐
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/YzCode.act")
	public String YzCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String tctype = request.getParameter("tctype");
			String tcname = request.getParameter("tcname");
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			boolean flag = true;
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", tctype);
			map.put("parentid", "0");
			if (YZUtility.isNotNullOrEmpty(organization)) {
				map.put("organization", organization);
			}
			List<KqdsTreatitemTcType> list = (List<KqdsTreatitemTcType>) logic.loadList(TableNameUtil.KQDS_TREATITEM_TC_TYPE, map);
			if (list != null && list.size() > 0) {
				// 存在套餐类型 判断该套餐类型下是否存在 该套餐
				KqdsTreatitemTcType tctypeObj = list.get(0);
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("name", tcname);
				map2.put("parentid", tctypeObj.getSeqId());
				if (YZUtility.isNotNullOrEmpty(organization)) {
					map2.put("organization", organization);
				}
				int count = logic.selectCount(TableNameUtil.KQDS_TREATITEM_TC_TYPE, map2);
				if (count > 0) {
					flag = false;// 存在 该套餐名称 返回false
				}
			} else {
				// 不存在套餐类型 ，肯定不存在该套餐
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", flag);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除--根据套餐类型,name,createuser 【############### 前后台共用 ############## 】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteObjBytctype.act")
	public String deleteObjBytctype(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String tcnameid = request.getParameter("tcnameid");

			Map<String, String> map = new HashMap<String, String>();
			map.put("tcnameid", tcnameid);
			List<KqdsTreatitemTc> en = (List<KqdsTreatitemTc>) logic.loadList(TableNameUtil.KQDS_TREATITEM_TC, map);
			if (en != null && en.size() > 0) {
				for (KqdsTreatitemTc tc : en) {
					logic.deleteSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, tc.getSeqId());
				}
			}
			// 该套餐名称
			KqdsTreatitemTcType currTC = (KqdsTreatitemTcType) logic.loadObjSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, tcnameid);
			if (currTC == null) {
				throw new Exception("根据tcnameid查询不到套餐记录");
			}
			// 删除当前的套餐
			logic.deleteSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, currTC.getSeqId());
			// 查询该类型下是否还有其他套餐，如果没有，就删除
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("parentid", currTC.getParentid());
			int count = logic.selectCount(TableNameUtil.KQDS_TREATITEM_TC_TYPE, map2);
			if (count == 0) {
				logic.deleteSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, currTC.getParentid());
			}
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_TREATITEM_TC_TYPE, map, TableNameUtil.KQDS_TREATITEM_TC_TYPE, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据套餐类型和名称查询详情 【############### 前后台共用 ############## 】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailBytctypeAndtcname.act")
	public String selectDetailBytctypeAndtcname(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String type = request.getParameter("type");
			String tcnameid = request.getParameter("tcnameid");
			Map<String, String> map = new HashMap<String, String>();
			map.put("tcnameid", tcnameid);
			List<KqdsTreatitemTc> en = (List<KqdsTreatitemTc>) logic.loadList(TableNameUtil.KQDS_TREATITEM_TC, map);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put(YZActionKeys.JSON_RET_MSRGS, "操作成功");
			if (en == null || en.size() == 0) {
				throw new Exception("数据不存在");
			}
			if (type.equals("1")) {
				jobj.put("data", en);
			} else {
				jobj.put("data", en);
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 根据套餐分类、名称
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getList.act")
	public String getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String tcnameid = request.getParameter("tcnameid");

			Map<String, String> map = new HashMap<String, String>();
			map.put("tcnameid", tcnameid);
			List<KqdsTreatitemTc> en = (List<KqdsTreatitemTc>) logic.loadList(TableNameUtil.KQDS_TREATITEM_TC, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}