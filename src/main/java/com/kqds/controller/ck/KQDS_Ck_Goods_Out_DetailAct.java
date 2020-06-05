package com.kqds.controller.ck;

import java.math.BigDecimal;
import java.util.HashMap;
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

import com.kqds.service.ck.KQDS_Ck_Goods_In_DetailLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_Out_DetailLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Ck_Goods_Out_DetailAct")
public class KQDS_Ck_Goods_Out_DetailAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_Goods_In_DetailAct.class);
	@Autowired
	private KQDS_Ck_Goods_Out_DetailLogic logic;
	@Autowired
	private KQDS_Ck_Goods_In_DetailLogic inlogic;

	@RequestMapping(value = "/toCkOutGoodsDetailSearch.act")
	public ModelAndView toCkOutGoodsDetailSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/search/out_goodsdetail_search.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCkOutGoodsDetailEdit.act")
	public ModelAndView toCkOutGoodsDetailEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/goodsOut/edit_out_goods_detail.jsp");
		return mv;
	}

	/**
	 * 入库明细(入库明细页面调用，入库查询页面调用)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inSearchList.act")
	public String inSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 入库明细 页面参数
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String outhouse = request.getParameter("outhouse");
			String outtype = request.getParameter("outtype");
			String goodsname = request.getParameter("goodsname");
			String supplier = request.getParameter("supplier");
			String sqdeptid = request.getParameter("sqdeptid");
			String goodstype = request.getParameter("goodstype");
			String llr = request.getParameter("llr");
			String sqdoctor = request.getParameter("sqdoctor");
			String outcode = request.getParameter("outcode");
			String goodsuuid = request.getParameter("goodsuuid");
			String type = request.getParameter("type");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(outhouse)) {
				map.put("outhouse", outhouse);
			}
			if (!YZUtility.isNullorEmpty(outtype)) {
				map.put("outtype", outtype);
			}
			if (!YZUtility.isNullorEmpty(goodsname)) {
				map.put("goodsname", goodsname);
			}
			if (!YZUtility.isNullorEmpty(goodstype)) {
				map.put("goodstype", goodstype);
			}
			if (!YZUtility.isNullorEmpty(supplier)) {
				map.put("supplier", supplier);
			}
			if (!YZUtility.isNullorEmpty(sqdeptid)) {
				map.put("sqdeptid", sqdeptid);
			}
			if (!YZUtility.isNullorEmpty(llr)) {
				map.put("llr", llr);
			}
			if (!YZUtility.isNullorEmpty(sqdoctor)) {
				map.put("sqdoctor", sqdoctor);
			}
			if (!YZUtility.isNullorEmpty(outcode)) {
				map.put("outcode", outcode);
			}
			if (!YZUtility.isNullorEmpty(goodsuuid)) {
				map.put("goodsuuid", goodsuuid);
			}
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);
			}
				
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // 当前所在门诊
			List<JSONObject> list = logic.inSearchList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("出库明细", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 供应商查询（包括出库 入库 明细）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sipplierSearchList.act")
	public String sipplierSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 入库明细 页面参数
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String outhouse = request.getParameter("outhouse");
			String outtype = request.getParameter("outtype");
			String goodsname = request.getParameter("goodsname");
			String supplier = request.getParameter("supplier");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			// map filter
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(outhouse)) {
				map.put("outhouse", outhouse);
				map.put("inhouse", outhouse);
			}
			if (!YZUtility.isNullorEmpty(outtype)) {
				map.put("outtype", outtype);
			}
			if (!YZUtility.isNullorEmpty(goodsname)) {
				map.put("goodsname", goodsname);
			}
			if (!YZUtility.isNullorEmpty(supplier)) {
				map.put("supplier", supplier);
			}
			map.put("supplierNoNull", "1");
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // 当前所在门诊
			List<JSONObject> list = null;
			// 只查询供应商不为空
			if (!YZUtility.isNullorEmpty(outtype)) {
				if (outtype.equals("1")) {// 出库
					list = logic.inSearchList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, map);
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							JSONObject json = (JSONObject) obj;
							json.put("inorout", "出库");
							json.put("outnum", BigDecimal.ZERO.subtract(new BigDecimal(json.getString("outnum"))));
							json.put("ckmoney", BigDecimal.ZERO.subtract(new BigDecimal(json.getString("ckmoney"))));
						}
					}
				} else {// 入库
					list = inlogic.inSearchList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, map);
					// 字段统一
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							JSONObject json = (JSONObject) obj;
							json.put("outcode", json.get("incode"));
							json.put("outtype", json.get("intype"));
							json.put("cktime", json.get("rktime"));
							json.put("outnum", json.get("innum"));
							json.put("outprice", json.get("inprice"));
							json.put("ckmoney", json.get("rkmoney"));
							json.put("inorout", "入库");
						}
					}
				}
			} else {// 出库 + 入库
				List<JSONObject> listout = logic.inSearchList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, map);
				List<JSONObject> listin = inlogic.inSearchList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL, map);
				// 字段统一
				if (listout != null && listout.size() > 0) {
					for (Object obj : listout) {
						JSONObject json = (JSONObject) obj;
						json.put("inorout", "出库");
						json.put("outnum", BigDecimal.ZERO.subtract(new BigDecimal(json.getString("outnum"))));
						json.put("ckmoney", BigDecimal.ZERO.subtract(new BigDecimal(json.getString("ckmoney"))));
					}
				}
				if (listin != null && listin.size() > 0) {
					for (Object obj : listin) {
						JSONObject json = (JSONObject) obj;
						json.put("outcode", json.get("incode"));
						json.put("outtype", json.get("intype"));
						json.put("cktime", json.get("rktime"));
						json.put("outnum", json.get("innum"));
						json.put("outprice", json.get("inprice"));
						json.put("ckmoney", json.get("rkmoney"));
						json.put("inorout", "入库");
					}
				}

				listout.addAll(listin);
				list = listout;
			}

			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("供应商查询", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}