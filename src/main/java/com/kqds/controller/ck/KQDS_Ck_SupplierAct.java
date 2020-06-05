package com.kqds.controller.ck;

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

import com.kqds.entity.base.KqdsCkSupplier;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_SupplierLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Ck_SupplierAct")
public class KQDS_Ck_SupplierAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_SupplierAct.class);
	@Autowired
	private KQDS_Ck_SupplierLogic logic;

	@RequestMapping(value = "/toSupplier.act")
	public ModelAndView toSupplier(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		// mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/ck/supplier/ck_supplier.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCkSupplierSearch.act")
	public ModelAndView toCkSupplierSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/search/supplier_search.jsp");
		return mv;
	}

	@RequestMapping(value = "/toSave.act")
	public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/ck/supplier/save_supplier.jsp");
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
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsCkSupplier dp = new KqdsCkSupplier();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_CK_SUPPLIER, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_SUPPLIER, dp, TableNameUtil.KQDS_CK_SUPPLIER, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_SUPPLIER, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_SUPPLIER, dp, TableNameUtil.KQDS_CK_SUPPLIER, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 验证编号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/YzCode.act")
	public String YzCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String suppliercode = request.getParameter("suppliercode");
			KqdsCkSupplier dp4query = new KqdsCkSupplier();
			dp4query.setSeqId(seqId);
			dp4query.setSuppliercode(suppliercode);
			dp4query.setOrganization(ChainUtil.getCurrentOrganization(request));
			int count = logic.countBySupplierCode(dp4query);
			boolean flag = count > 0 ? false : true; // false 表示存在
			JSONObject jobj = new JSONObject();
			jobj.put("data", flag);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			KqdsCkSupplier en = (KqdsCkSupplier) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_SUPPLIER, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_CK_SUPPLIER, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_SUPPLIER, en, TableNameUtil.KQDS_CK_SUPPLIER, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 详情返回
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

			KqdsCkSupplier en = (KqdsCkSupplier) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_SUPPLIER, seqId);

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
	 * 获取列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			List<JSONObject> list = logic.selectList(map);

			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}