package com.kqds.controller.base.printSet;

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

import com.kqds.entity.base.KqdsPrintSet;
import com.kqds.service.base.printSet.KQDS_Print_SetLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Print_SetAct")
public class KQDS_Print_SetAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Print_SetAct.class);
	@Autowired
	private KQDS_Print_SetLogic logic;

	@RequestMapping(value = "/toHyczPrintPage.act")
	public ModelAndView toHyczPrintPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String printpage = request.getParameter("printpage");
		String printType = request.getParameter("printType");
		String cztime = request.getParameter("cztime");
		String memberno = request.getParameter("memberno");
		String username = request.getParameter("username");
		String szrusercode = request.getParameter("szrusercode");
		String szrusername = request.getParameter("szrusername");
		String money = request.getParameter("money");
		String givemoney = request.getParameter("givemoney");
		String totalmoney = request.getParameter("totalmoney");
		String tksh = request.getParameter("tksh");
		String usercode = request.getParameter("usercode");
		String ymoney = request.getParameter("ymoney");
		String recordId = request.getParameter("recordId");
		String ygivemoney = request.getParameter("ygivemoney");
		String ytotalmoney = request.getParameter("ytotalmoney");
		String cost_organization = request.getParameter("cost_organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("printType", printType);
		mv.addObject("cztime", cztime);
		mv.addObject("memberno", memberno);
		mv.addObject("username", username);
		mv.addObject("szrusercode", szrusercode);
		mv.addObject("szrusername", szrusername);
		mv.addObject("money", money);
		mv.addObject("givemoney", givemoney);
		mv.addObject("totalmoney", totalmoney);
		mv.addObject("tksh", tksh);
		mv.addObject("usercode", usercode);
		mv.addObject("ymoney", ymoney);
		mv.addObject("recordId", recordId);
		mv.addObject("ygivemoney", ygivemoney);
		mv.addObject("ytotalmoney", ytotalmoney);
		mv.addObject("cost_organization", cost_organization);
		mv.setViewName("/kqdsFront/print/" + printpage);
		return mv;
	}

	@RequestMapping(value = "/toJgdPrintPage.act")
	public ModelAndView toJgdPrintPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String printpage = request.getParameter("printpage");
		String printType = request.getParameter("printType");
		String outprocessingsheetno = request.getParameter("outprocessingsheetno");
		String doctorno = request.getParameter("doctorno");
		String fajiantime = request.getParameter("fajiantime");
		String processingfactory = request.getParameter("processingfactory");
		String type = request.getParameter("type");
		String yaoqiu = request.getParameter("yaoqiu");
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("printType", printType);
		mv.addObject("outprocessingsheetno", outprocessingsheetno);
		mv.addObject("doctorno", doctorno);
		mv.addObject("fajiantime", fajiantime);
		mv.addObject("processingfactory", processingfactory);
		mv.addObject("type", type);
		mv.addObject("yaoqiu", yaoqiu);
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/print/" + printpage);
		return mv;
	}

	@RequestMapping(value = "/toInGoodsPrintPage.act")
	public ModelAndView toInGoodsPrintPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String printpage = request.getParameter("printpage");
		String printType = request.getParameter("printType");
		String incode = request.getParameter("incode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("printType", printType);
		mv.addObject("incode", incode);
		mv.setViewName("/kqdsFront/print/" + printpage);
		return mv;
	}

	@RequestMapping(value = "/toOutGoodsPrintPage.act")
	public ModelAndView toOutGoodsPrintPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String printpage = request.getParameter("printpage");
		String printType = request.getParameter("printType");
		String outcode = request.getParameter("outcode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("printType", printType);
		mv.addObject("outcode", outcode);
		mv.setViewName("/kqdsFront/print/" + printpage);
		return mv;
	}

	@RequestMapping(value = "/toFyqrdPrintPage.act")
	public ModelAndView toFyqrdPrintPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String printpage = request.getParameter("printpage");
		String printType = request.getParameter("printType");
		String titles = request.getParameter("titles");
		String actualmoney = request.getParameter("actualmoney");
		String arrearmoney = request.getParameter("arrearmoney");
		String ymoney = request.getParameter("ymoney");
		String costno = request.getParameter("costno");
		String costno2 = request.getParameter("costno2");
		String username = request.getParameter("username");
		String sftime = request.getParameter("sftime");
		String doctor = request.getParameter("doctor");
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("printType", printType);
		mv.addObject("titles", titles);
		mv.addObject("actualmoney", actualmoney);
		mv.addObject("arrearmoney", arrearmoney);
		mv.addObject("ymoney", ymoney);
		mv.addObject("costno", costno);
		mv.addObject("costno2", costno2);
		mv.addObject("username", username);
		mv.addObject("sftime", sftime);
		mv.addObject("doctor", doctor);
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/print/" + printpage);
		return mv;
	}

	@RequestMapping(value = "/toTkPrintPage.act")
	public ModelAndView toTkPrintPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String printpage = request.getParameter("printpage");
		String printType = request.getParameter("printType");
		String usercode = request.getParameter("usercode");
		String seqIds = request.getParameter("seqIds");
		String cost_organization = request.getParameter("cost_organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("printType", printType);
		mv.addObject("usercode", usercode);
		mv.addObject("seqIds", seqIds);
		mv.addObject("cost_organization", cost_organization);
		mv.setViewName("/kqdsFront/print/" + printpage);
		return mv;
	}

	@RequestMapping(value = "/toZengSongPrintPage.act")
	public ModelAndView toZengSongPrintPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String printpage = request.getParameter("printpage");
		String printType = request.getParameter("printType");
		String usercode = request.getParameter("usercode");
		String seqIds = request.getParameter("seqIds");
		ModelAndView mv = new ModelAndView();
		mv.addObject("printType", printType);
		mv.addObject("usercode", usercode);
		mv.addObject("seqIds", seqIds);
		mv.setViewName("/kqdsFront/print/" + printpage);
		return mv;
	}

	@RequestMapping(value = "/toList.act")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/printSet/list_kqds_print_set.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/printSet/edit_kqds_print_set.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail.act")
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqds/printSet/detail_kqds_print_set.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNewAdd.act")
	public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("organization", organization);
		mv.setViewName("/kqds/printSet/add_kqds_print_set.jsp");
		return mv;
	}

	/**
	 * 根据url查询printtype
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getPrintTypeByUrl.act")
	public String getPrintTypeByUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String printname = request.getParameter("printname");
			// 当前门诊的优先级高于公用设置
			Map map = new HashMap();
			map.put("printname", printname);
			map.put("organization", ChainUtil.getCurrentOrganization(request)); // 先查当前门诊的
			List<KqdsPrintSet> p = (List<KqdsPrintSet>) logic.loadList(TableNameUtil.KQDS_PRINT_SET, map);

			if (p == null || p.size() == 0) {
				map.put("organization", ""); // 再查公用的
				p = (List<KqdsPrintSet>) logic.loadList(TableNameUtil.KQDS_PRINT_SET, map);
				if (p == null || p.size() == 0) {
					throw new Exception("未查询到'" + printname + "'对应的数据");
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", p.get(0));
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}