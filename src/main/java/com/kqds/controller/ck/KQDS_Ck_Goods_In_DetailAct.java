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

import com.hudh.ksll.service.IKsllReplaceMentService;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_Goods_In_DetailLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_Out_DetailLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Ck_Goods_In_DetailAct")
public class KQDS_Ck_Goods_In_DetailAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_Goods_In_DetailAct.class);
	@Autowired
	private KQDS_Ck_Goods_In_DetailLogic logic;
	@Autowired
	private KQDS_Ck_Goods_Out_DetailLogic outLogic;
	/**
	 * 科室商品service
	 */
	@Autowired
	private IKsllReplaceMentService ksllReplaceMentService;
	@RequestMapping(value = "/toCkInGoodsDetailSearch.act")
	public ModelAndView toCkInGoodsDetailSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/search/in_goodsdetail_search.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCkInGoodsDetailEdit.act")
	public ModelAndView toCkInGoodsDetailEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/goodsIn/edit_in_goods_detail.jsp");
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
			Map<String, String> map = new HashMap<String, String>();
			// 入库明细 页面参数
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String inhouse = request.getParameter("inhouse");
			String intype = request.getParameter("intype");
			String goodsname = request.getParameter("goodsname");
			String goodstype = request.getParameter("goodstype");
			String supplier = request.getParameter("supplier");
			String incode = request.getParameter("incode");
			String stockstarttime = request.getParameter("stockstarttime");
			String stockendtime = request.getParameter("stockendtime");
			String menu=request.getParameter("menu");
			String ph = request.getParameter("ph");
			String zczh = request.getParameter("zczh");
			String type = request.getParameter("type");
			// 入库明细Id（查询某一条入库明细）
			String goodsinid = request.getParameter("goodsinid");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(inhouse)) {
				map.put("inhouse", inhouse);
			}
			if (!YZUtility.isNullorEmpty(intype)) {
				map.put("intype", intype);
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
			if (!YZUtility.isNullorEmpty(incode)) {
				map.put("incode", incode);
			}
			if (!YZUtility.isNullorEmpty(goodsinid)) {
				map.put("goodsinid", goodsinid);
			}
			if (!YZUtility.isNullorEmpty(ph)) {
				map.put("ph", ph);
			}
			if (!YZUtility.isNullorEmpty(zczh)) {
				map.put("zczh", zczh);
			}
			if (!YZUtility.isNullorEmpty(stockstarttime)) {
				map.put("stockstarttime", stockstarttime);
			}
			if (!YZUtility.isNullorEmpty(stockendtime)) {
				map.put("stockendtime", stockendtime);
			}
			if(!YZUtility.isNullorEmpty(menu)){
				map.put("menu", menu);
			}
			if(!YZUtility.isNullorEmpty(type)){
				map.put("type", type);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // 当前所在门诊
			List<JSONObject> list = logic.inSearchList(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("入库明细", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据收据编号查询入库明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inDetailSelectByIncode.act")
	public String inDetailSelectByIncode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			// 入库明细 页面参数
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String inhouse = request.getParameter("inhouse");
			String intype = request.getParameter("intype");
			String goodsname = request.getParameter("goodsname");
			String goodstype = request.getParameter("goodstype");
			String supplier = request.getParameter("supplier");
			String incode = request.getParameter("incode");
			String stockstarttime = request.getParameter("stockstarttime");
			String stockendtime = request.getParameter("stockendtime");
			
			String ph = request.getParameter("ph");
			String zczh = request.getParameter("zczh");
			// 入库明细Id（查询某一条入库明细）
			String goodsinid = request.getParameter("goodsinid");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(inhouse)) {
				map.put("inhouse", inhouse);
			}
			if (!YZUtility.isNullorEmpty(intype)) {
				map.put("intype", intype);
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
			if (!YZUtility.isNullorEmpty(incode)) {
				map.put("incode", incode);
			}
			if (!YZUtility.isNullorEmpty(goodsinid)) {
				map.put("goodsinid", goodsinid);
			}
			if (!YZUtility.isNullorEmpty(ph)) {
				map.put("ph", ph);
			}
			if (!YZUtility.isNullorEmpty(zczh)) {
				map.put("zczh", zczh);
			}
			if (!YZUtility.isNullorEmpty(stockstarttime)) {
				map.put("stockstarttime", stockstarttime);
			}
			if (!YZUtility.isNullorEmpty(stockendtime)) {
				map.put("stockendtime", stockendtime);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // 当前所在门诊
			List<JSONObject> list = logic.inDetailSelectByIncode(TableNameUtil.KQDS_CK_GOODS_IN_DETAIL, map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("入库明细", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据商品编号在入库明细表查询所有批号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectAllGoodPhByGoodCode.act")
	public String selectAllGoodPhByGoodCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String goodsuuid = request.getParameter("goodsuuid");
		String type = request.getParameter("type");
		try {
			List<JSONObject> list = logic.selectAllGoodPhByGoodCode(goodsuuid,type);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	 * 根据商品编号查询商品批号详情
	 * <p>Title: toCkInGoodsDetailByGoodsuuid</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月17日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toCkInGoodsDetailByGoodsuuid.act")
	public ModelAndView toCkInGoodsDetailByGoodsuuid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String goodsuuid=request.getParameter("goodsuuid");
		//查询物品明细
		List<JSONObject> list = logic.findCkInGoodsDetailByGoodsuuid(goodsuuid); 
		//查询当天该商品的出库数量
		//查找当天商品所有的出库数量
		//查询当天所有的出库数量
		int outnums=outLogic.findOutNumsByAll();
		int batchnums=0;
		List<JSONObject> list1 = outLogic.findPhOutNumByGoodsuuid(goodsuuid);
		if(list1.size()>0){
			for (JSONObject jsonObject : list1) {
				batchnums+=jsonObject.getInt("outnum");
			}
		}
		int thnums=ksllReplaceMentService.selectReturnNumByGoodscode(list.get(0).getString("goodscode"));
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.addObject("batchlist", list1);
		mv.addObject("batchnums", batchnums+"");
		mv.addObject("thnums", thnums+"");
		mv.addObject("outnums", outnums+"");
		mv.setViewName("kqdsFront/ck/goodsIn/in_goods_detail.jsp");
		return mv;
	}
	@RequestMapping(value="/toCkInGoodsDetail.act")
	public ModelAndView toCkInGoodsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String goodsuuid=request.getParameter("goodsuuid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("goodsuuid", goodsuuid);
		mv.setViewName("kqdsFront/ck/search/goods_ph.jsp");
		return mv;
	}
	@RequestMapping(value = "/selectAllGoodDetail.act")
	public String selectAllGoodDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String goodsuuid = request.getParameter("goodsuuid");
		try {
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject list = logic.findCkInGoodsDetail(goodsuuid,bp); 
			YZUtility.DEAL_SUCCESS(list,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	@RequestMapping(value = "/updateGoodsPh.act")
	public String updateGoodsPh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String goodscode = request.getParameter("goodscode");
		String ph = request.getParameter("ph");
		String incode = request.getParameter("incode");
		String phnum = request.getParameter("phnum");
		String goodsuuid = request.getParameter("goodsuuid");
		String yxdate = request.getParameter("yxdate");
		YZPerson person = SessionUtil.getLoginPerson(request);
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("goodscode", goodscode);
			map.put("phnum", phnum);
			map.put("goodsuuid", goodsuuid);
			map.put("ph", ph);
			map.put("incode", incode);
			map.put("updateuser", person.getSeqId());
			map.put("updatetime", YZUtility.getCurDateTimeStr());
			map.put("yxdate", yxdate);
			logic.updateGoodsPh(map);
			
			YZUtility.DEAL_SUCCESS(null, "修改成功", response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}