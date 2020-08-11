package com.kqds.controller.base.scbb;

import java.math.BigDecimal;
import java.rmi.ServerError;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.base.scbb.KQDS_ScbbLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({})
@Controller
@RequestMapping("KQDS_ScbbAct")
public class KQDS_ScbbAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_ScbbAct.class);
	@Autowired
	private KQDS_ScbbLogic logic;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private YZDeptLogic deptLogic;
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private YZPrivLogic privLogic;
	@RequestMapping(value = "/toBbAskIndex.act")
	public ModelAndView toBbAskIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/bbzx/bb_ask_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBbTyIndex.act")
	public ModelAndView toBbTyIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/bbzx/bb_ty_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBbLsIndex.act")
	public ModelAndView toBbLsIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/bbzx/bb_ls_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBbGzlIndex.act")
	public ModelAndView toBbGzlIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.addObject("isyx", 1);
		mv.setViewName("/kqdsFront/index/bbzx/bb_gzl_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBbGzlIndex2.act")
	public ModelAndView toBbGzlIndex2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.addObject("isyx", 2);
		mv.setViewName("/kqdsFront/index/bbzx/bb_gzl_index.jsp");
		
		return mv;
	}

	@RequestMapping(value = "/toBbGzlIndex3.act")
	public ModelAndView toBbGzlIndex3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.addObject("isyx", 3);
		mv.setViewName("/kqdsFront/index/bbzx/bb_gzl_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBbDoctorTj.act")
	public ModelAndView toBbDoctorTj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/bbzx/bb_doctorTj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBbZzcxIndex.act")
	public ModelAndView toBbZzcxIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/bbzx/bb_zzcx_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBbHzlyTj.act")
	public ModelAndView toBbHzlyTj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/bbzx/bb_xxlyTj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBbTubiaoIndex.act")
	public ModelAndView toBbTubiaoIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/bbzx/bb_tubiao_index.jsp");
		return mv;
	}
	
	/**
	  * @Title: toBb_AskCgl   
	  * @Description: TODO(现场咨询统计页面跳转)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年9月17日 上午9:16:22
	 */
	@RequestMapping(value = "/toBb_AskCgl.act")
	public ModelAndView toBb_AskCgl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_askCgl.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_AskSort.act")
	public ModelAndView toNetorder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_askSort.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_DoctorQkTj.act")
	public ModelAndView toBb_DoctorQkTj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_doctorQkTj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_DoctorYjTj.act")
	public ModelAndView toBb_DoctorYjTj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_doctorYjTj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_DoctorClcbTj.act")
	public ModelAndView toBb_DoctorClcbTj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_doctorClcbTj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_WdNetPer.act")
	public ModelAndView toBb_WdNetPer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String isyx = request.getParameter("isyx");
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("isyx", isyx);
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_wdNetPer.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_WdNetPerItem.act")
	public ModelAndView toBb_WdNetPerItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isyx = request.getParameter("isyx");
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("isyx", isyx);
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_wdNetPerItem.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_WdNetItem.act")
	public ModelAndView toBb_WdNetItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isyx = request.getParameter("isyx");
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("isyx", isyx);
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_wdNetItem.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Ls_Lszxxmtj.act")
	public ModelAndView toBb_Ls_Lszxxmtj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_ls_lszxxmtj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Ls_Lsxmtj.act")
	public ModelAndView toBb_Ls_Lsxmtj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_ls_lsxmtj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Ls_Lsxffltj.act")
	public ModelAndView toBb_Ls_Lsxffltj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_ls_lsxffltj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_RegCjlHigh.act")
	public ModelAndView toBb_RegCjlHigh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_regCjlHigh.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_YysmlHigh.act")
	public ModelAndView toBb_YysmlHigh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_yysmlHigh.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_RegHigh.act")
	public ModelAndView toBb_RegHigh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_regHigh.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_QzHigh.act")
	public ModelAndView toBb_QzHigh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_qzHigh.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_QyyjHigh.act")
	public ModelAndView toBb_QyyjHigh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_qyyjHigh.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_QylrHigh.act")
	public ModelAndView toBb_QylrHigh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_qylrHigh.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Rsktj_Search.act")
	public ModelAndView toBb_Rsktj_Search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/jdzx/rsktj_search.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Rsktj_Search_Askperson.act")
	public ModelAndView toBb_Rsktj_Search_Askperson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/jdzx/rsktj_search_askperson.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Ty_Yjjcx.act")
	public ModelAndView toBb_Ty_Yjjcx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_ty_yjjcx.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Ty_Hjzlqktj.act")
	public ModelAndView toBb_Ty_Hjzlqktj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_ty_hjzlqktj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_XxlyNumTj.act")
	public ModelAndView toBb_XxlyNumTj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_xxlyNumTj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Zhcx.act")
	public ModelAndView toBb_Zhcx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_zhcx.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Sfmx.act")
	public ModelAndView toBb_Sfmx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Qfcx.act")
	public ModelAndView toBb_Qfcx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/jdzx/qfcx_center.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Sfmx_all.act")
	public ModelAndView toBb_Sfmx_all(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx_all.jsp");
		return mv;
	}

	@RequestMapping(value = "/toRsktj_search_wdperson.act")
	public ModelAndView toRsktj_search_wdperson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/jdzx/rsktj_search_wdperson.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Sfmx_All_Wdperson_Trcc.act")
	public ModelAndView toBb_Sfmx_All_Wdperson_Trcc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		String usercodes = request.getParameter("usercodes");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.addObject("usercodes", usercodes);
		mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx_all_wdperson_trcc.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Sfmx_all_Wdperson.act")
	public ModelAndView toBb_Sfmx_all_Wdperson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx_all_wdperson.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Sfmx_all_Askperson.act")
	public ModelAndView toBb_Sfmx_all_Askperson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx_all_askperson.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Zxqk.act")
	public ModelAndView toBb_Zxqk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		String seqIds = request.getParameter("seqIds");
		String table = request.getParameter("table");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.addObject("seqIds", seqIds);
		mv.addObject("table", table);
		mv.setViewName("/kqdsFront/index/bbzx/bb_zxqk.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Xxcx.act")
	public ModelAndView toBb_Xxcx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		String usercodes = request.getParameter("usercodes");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.addObject("usercodes", usercodes);
		mv.setViewName("/kqdsFront/index/bbzx/bb_xxcx.jsp");
		return mv;
	}

	@RequestMapping(value = "/toBb_Trsc.act")
	public ModelAndView toBb_Trsc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqIds = request.getParameter("seqIds");
		String table = request.getParameter("table");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqIds", seqIds);
		mv.addObject("table", table);
		mv.setViewName("/kqdsFront/index/bbzx/bb_trsc.jsp");
		return mv;
	}
	
	/**
	 * syp
	  * @Title: toBb_Sjtj   
	  * @Description: TODO(现场咨询工作情况统计初始页)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: ModelAndView
	  * @dateTime:2019年9月21日 下午3:02:04
	 */
	@RequestMapping(value = "/toBb_Sjtj.act")
	public ModelAndView toBb_Sjtj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqIds = request.getParameter("seqIds");
		String table = request.getParameter("table");
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqIds", seqIds);
		mv.addObject("table", table);
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/zxtj/consult_index.jsp");
		return mv;
	}
	
	/**
	  * @Title: toBb_BaseData   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年9月24日 上午8:30:03
	 */
	@RequestMapping(value = "/toBb_BaseData.act")
	public ModelAndView toBb_BaseData(HttpServletRequest request, HttpServletResponse response) {
		String seqIds = request.getParameter("seqIds");
		String table = request.getParameter("table");
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqIds", seqIds);
		mv.addObject("table", table);
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/zxtj/baseData.jsp");
		return mv;
	}
	
	/**
	  * @Title: toBb_BaseDataDay   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年9月24日 上午8:30:11
	 */
	@RequestMapping(value = "/toBb_BaseDataDay.act")
	public ModelAndView toBb_BaseDataDay(HttpServletRequest request, HttpServletResponse response) {
		String seqIds = request.getParameter("seqIds");
		String table = request.getParameter("table");
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqIds", seqIds);
		mv.addObject("table", table);
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/zxtj/baseDataDay.jsp");
		return mv;
	}
	
	/**
	  * @Title: toBb_Cjdetaile   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年9月24日 上午8:30:19
	 */
	@RequestMapping(value = "/toBb_Cjdetaile.act")
	public ModelAndView toBb_Cjdetaile(HttpServletRequest request, HttpServletResponse response) {
		String seqIds = request.getParameter("seqIds");
		String table = request.getParameter("table");
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqIds", seqIds);
		mv.addObject("table", table);
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/zxtj/cjdetaile.jsp");
		return mv;
	}
	
	/**
	  * @Title: toBb_Cjdetaile   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年9月24日 上午8:30:19
	 */
	@RequestMapping(value = "/toBb_dayTotalData.act")
	public ModelAndView toBb_dayTotalData(HttpServletRequest request, HttpServletResponse response) {
		String timeMonth = request.getParameter("timeMonth");
		ModelAndView mv = new ModelAndView();
		mv.addObject("timeMonth", timeMonth);
		mv.setViewName("/kqdsFront/index/zxtj/dayTotalData.jsp");
		return mv;
	}
	
	/**
	  * @Title: toBb_Totaldetaile   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年9月24日 上午8:30:28
	 */
	@RequestMapping(value = "/toBb_Totaldetaile.act")
	public ModelAndView toBb_Totaldetaile(HttpServletRequest request, HttpServletResponse response) {
		String seqIds = request.getParameter("seqIds");
		String table = request.getParameter("table");
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqIds", seqIds);
		mv.addObject("table", table);
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/zxtj/totalDetaile.jsp");
		return mv;
	}
	
	/**
	  * @Title: toBb_EmployeeData   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2019年9月24日 上午8:30:37
	 */
	@RequestMapping(value = "/toBb_EmployeeData.act")
	public ModelAndView toBb_EmployeeData(HttpServletRequest request, HttpServletResponse response) {
		String seqIds = request.getParameter("seqIds");
		String table = request.getParameter("table");
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqIds", seqIds);
		mv.addObject("table", table);
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/zxtj/employeeData.jsp");
		return mv;
	}

	/**
	 * 科室权责业绩图表展示 【青岛特有】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountBBDept.act")
	public String selectCountBBDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			// 业绩科室
			List<YZDept> list = deptLogic.getDeptListByDeptType(ConstUtil.DEPT_TYPE_1, ChainUtil.getOrganizationFromUrl(request));
			List<JSONObject> listAll = new ArrayList<JSONObject>();
			for (YZDept dept : list) {
				List<JSONObject> listobj = new ArrayList<JSONObject>();
				listobj = logic.getCountTjDept(TableNameUtil.KQDS_COSTORDER_DETAIL, map, ChainUtil.getOrganizationFromUrl(request), dept.getSeqId(), dept.getDeptName());
				listAll.addAll(listobj);
			}
			String yysr = logic.getYysr(TableNameUtil.KQDS_COSTORDER_DETAIL, map, ChainUtil.getOrganizationFromUrl(request));
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			jobj.put("yysr", yysr);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 1.咨询/医生成功率分析表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/selectCountBB.act")
	public String selectCountBB(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String depttype = request.getParameter("depttype");// 0咨询 1医生 2网电		
			
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			} else {
				map.put("organization", organization);
			}
			BigDecimal all_advance = new BigDecimal(0);
			DecimalFormat df = new DecimalFormat("#.00");
			// （医生情况统计，查询总数 排除 挂号表医生为空的情况）
			map.put("depttype", depttype);
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> listPerson = personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);

			// 组装查询过滤条件，防止脏数据干扰统计准确性，比如cost_order_detail表中的咨询人员，在sys_person表中已经不存在
			StringBuffer personIds4QueryBf = new StringBuffer();
			for (JSONObject person : listPerson) {
				personIds4QueryBf.append(person.getString("seqId")).append(",");
			}
			String personIds4Query = YZUtility.ConvertStringIds4Query(personIds4QueryBf.toString());
			map.put("personIds4Query", personIds4Query);

			List<JSONObject> listAll = new ArrayList<JSONObject>();

			// 获取咨询项目
			List<YZDict> listDict = dictLogic.getListByParentCodeALL("GHFL", ChainUtil.getCurrentOrganization(request));

			// 查出所有挂号
			List<JSONObject> listRegAllTotal = logic.getListRegByGhfl(map, "", "");

			// 总咨询人数（所有咨询）（医生情况统计，查询总数 排除 挂号表医生为空的情况）
			int allnums = listRegAllTotal.size();
			// 总咨询成交人数（所有咨询）（医生情况统计，查询总数 排除 挂号表医生为空的情况）
			int allcjnums = 0;
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				if ("1".equals(cjstatus)) {
					allcjnums++;
				}
			}

			/** 营收数据列表 **/
			List<JSONObject> skjeList = null;
			List<JSONObject> skjeYjjList = null;
			List<JSONObject> yysrList = null;
			if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
				skjeList = logic.getYysrList(map);
				skjeYjjList = logic.getYysrYjjList(map);
			} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
				yysrList = logic.getYysrList(map, ChainUtil.getOrganizationFromUrl(request));
			}

			// 获取初诊编码
			String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
			// 获取复诊编码
			String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
			// 获取再消费编码
			String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
			// 获取复查编码
			String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
			// 获取其他编码
			String qtseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);

			/** 一级细分，细分到人 **/
			for (JSONObject personJson : listPerson) { // 然后根据用户id，进行分组，过滤，目的是减少查询数据库次数
				String personseqId = personJson.getString("seqId");
				List<JSONObject> listRegFilter = new ArrayList<JSONObject>();
				for (JSONObject regJson : listRegAllTotal) {
					if (depttype.equals(ConstUtil.DEPT_TYPE_0)) { // 0 咨询
						if (personseqId.equals(regJson.getString("askperson"))) {
							listRegFilter.add(regJson);
						}
					} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) { // 1 医生
						if (personseqId.equals(regJson.getString("doctor"))) {
							listRegFilter.add(regJson);
						}
					}
				}
				personJson.put("personReglist", listRegFilter);// ----------------------
			}

			/** 二级细分，细分到人和挂号分类 **/
			for (int i = 0; i < listPerson.size(); i++) {
				JSONObject objper = listPerson.get(i);
				BigDecimal per_advance = new BigDecimal(0);
				// 从用户JsonObject获取对应的挂号列表
				List<JSONObject> listRegAll = (List) objper.get("personReglist"); // ----------------------

				if (listRegAll == null || listRegAll.size() == 0) {
					continue;
				}

				List<JSONObject> jsonListDict = JSONArray.fromObject(listDict);
				// 遍历咨询项目
				for (JSONObject jsonDict : jsonListDict) {
					String regsort = jsonDict.getString("seqId");
					List<JSONObject> listRegFilter = new ArrayList<JSONObject>();
					for (JSONObject jsonReg : listRegAll) {
						if (jsonReg.getString("regsort").equals(regsort)) { // 根据挂号分类过滤
							listRegFilter.add(jsonReg);
						}
					}
					jsonDict.put("reglist", listRegFilter); // ----------------------
				}

				for (JSONObject dict : jsonListDict) {
					JSONObject obj = new JSONObject();
					// ------人员
					obj.put("username", objper.getString("userName"));
					// ------咨询项目/挂号分类
					obj.put("zxxm", dict.getString("dictName"));

					List<JSONObject> list = (List) dict.get("reglist"); // -------------人+挂号分类对应的挂号列表
					// ------咨询人次
					int zxnums = list.size();
					obj.put("zxnums", zxnums);
					// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
					Float zzlzb = (float) 0;
					if (allnums != 0) {
						zzlzb = (float) zxnums * 100 / allnums;
					}
					obj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");

					int cjnums = 0; // ------其中成交人次
					int wcjnums = 0;// ------其中未成交人次
					int czallnums = 0; // ------初诊总人次
					int czcjnums = 0; // ------初诊成交人次
					int fzallnums = 0; // ------复诊总人次
					int fzcjnums = 0;// ------复诊成交人次
					int zxfallnums = 0;// ------再消费总人次
					int zxfcjnums = 0;// ------再消费成交人次
					int fcallnums = 0;// ------复查总人次
					int fccjnums = 0;// ------复查成交人次
					int qtallnums = 0; // ------其他总人次
					int qtcjnums = 0; // ------其他成交人次
					for (JSONObject regJson : list) {
						String cjstatus = regJson.getString("cjstatus");
						String recesort = regJson.getString("recesort");
						if ("1".equals(cjstatus)) {
							cjnums++;
						}

						if ("0".equals(cjstatus)) {
							wcjnums++;
						}

						if (czseqId.equals(recesort)) {
							czallnums++;
						}
						if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
							czcjnums++;
						}

						if (fzseqId.equals(recesort)) {
							fzallnums++;
						}
						if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
							fzcjnums++;
						}

						if (zxfseqId.equals(recesort)) {
							zxfallnums++;
						}
						if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
							zxfcjnums++;
						}

						if (fcseqId.equals(recesort)) {
							fcallnums++;
						}
						if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
							fccjnums++;
						}
						if (qtseqId.equals(recesort)) {
							qtallnums++;
						}
						if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
							qtcjnums++;
						}
					}

					// ------其中成交人次
					obj.put("cjnums", cjnums);
					// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
					Float cgl = (float) 0;
					if (zxnums != 0) {
						cgl = (float) cjnums * 100 / zxnums;
					}
					obj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
					// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
					Float cglzb = (float) 0;
					if (allcjnums != 0) {
						cglzb = (float) cjnums * 100 / allcjnums;
					}
					obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");

					// ------其中未成交人次
					obj.put("wcjnums", wcjnums);

					// ------初诊总人次
					obj.put("czallnums", czallnums);
					// ------初诊成交人次
					obj.put("czcjnums", czcjnums);

					// ------复诊总人次
					obj.put("fzallnums", fzallnums);
					// ------复诊成交人次
					obj.put("fzcjnums", fzcjnums);

					// ------再消费总人次
					obj.put("zxfallnums", zxfallnums);
					// ------再消费成交人次
					obj.put("zxfcjnums", zxfcjnums);

					// ------复查总人次
					obj.put("fcallnums", fcallnums);
					// ------复查成交人次
					obj.put("fccjnums", fccjnums);
					// ------其他总人次
					obj.put("qtallnums", qtallnums);
					// ------其他成交人次
					obj.put("qtcjnums", qtcjnums);
					// ------收款金额
					String sk = "0.00";
					if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
						sk = getskje4AskPerson(skjeList, skjeYjjList, objper.getString("seqId"), dict.getString("seqId"));
						String ss = getskje4AskPersonSS(skjeList, skjeYjjList, objper.getString("seqId"), dict.getString("seqId"));
						obj.put("ssje", ss);
					} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
						// 营业收入
						sk = getskje4Doctor(yysrList, objper.getString("seqId"), dict.getString("seqId"));
					}
					obj.put("skje", sk);
					// ---如果 咨询人次为0，不展示
					if (zxnums == 0 && KqdsBigDecimal.compareTo(new BigDecimal(sk), BigDecimal.ZERO) == 0) {
						continue;
					}
					//JSONObject
					 BigDecimal singleAdvance=new BigDecimal(0);
					 //医生-项目-挂号（挂号信息可能为多个，对其进行遍历，并取遍历每项的挂号编号作为参数去费用表去查该医生对该挂号的费用添加信息）
					 for (JSONObject regnojson : list) {
						 Map perMap=   new HashMap();
						 perMap.put("starttime", starttime);
						 perMap.put("endtime", endtime);
						 perMap.put("createuser",objper.getString("seq_id"));
						 perMap.put("regno", regnojson.getString("seq_id"));	
						 BigDecimal regAdvance= logic.getPerAdvance(perMap);
						 singleAdvance=singleAdvance.add(regAdvance);
					}
					per_advance=per_advance.add(singleAdvance);
					obj.put("totalAdvance",singleAdvance.equals(BigDecimal.ZERO)?BigDecimal.ZERO:df.format(singleAdvance.setScale(2,BigDecimal.ROUND_HALF_UP)));
					listAll.add(obj);
				}
				// ------------------合计 行（条件去除挂号分类）
				JSONObject objhj = new JSONObject();

				int cjnums = 0; // ------其中成交人次
				int wcjnums = 0;// ------其中未成交人次
				int czallnums = 0; // ------初诊总人次
				int czcjnums = 0; // ------初诊成交人次
				int fzallnums = 0; // ------复诊总人次
				int fzcjnums = 0;// ------复诊成交人次
				int zxfallnums = 0;// ------再消费总人次
				int zxfcjnums = 0;// ------再消费成交人次
				int fcallnums = 0;// ------复查总人次
				int fccjnums = 0;// ------复查成交人次
				int qtallnums = 0; // ------其他总人次
				int qtcjnums = 0; // ------其他成交人次
				for (JSONObject regJson : listRegAll) {
					String cjstatus = regJson.getString("cjstatus");
					String recesort = regJson.getString("recesort");
					if ("1".equals(cjstatus)) {
						cjnums++;
					}
					if ("0".equals(cjstatus)) {
						wcjnums++;
					}
					if (czseqId.equals(recesort)) {
						czallnums++;
					}
					if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
						czcjnums++;
					}
					if (fzseqId.equals(recesort)) {
						fzallnums++;
					}
					if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
						fzcjnums++;
					}
					if (zxfseqId.equals(recesort)) {
						zxfallnums++;
					}
					if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
						zxfcjnums++;
					}
					if (fcseqId.equals(recesort)) {
						fcallnums++;
					}
					if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
						fccjnums++;
					}
					if (qtseqId.equals(recesort)) {
						qtallnums++;
					}
					if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
						qtcjnums++;
					}
				}
				// ------咨询人次
				int zxnums = listRegAll.size();
				objhj.put("zxnums", zxnums);
				// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
				Float zzlzb = (float) 0;
				if (allnums != 0) {
					zzlzb = (float) zxnums * 100 / allnums;
				}
				objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
				// ------其中成交人次
				objhj.put("cjnums", cjnums);
				// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
				Float cgl = (float) 0;
				if (zxnums != 0) {
					cgl = (float) cjnums * 100 / zxnums;
				}
				objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
				// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
				Float cglzb = (float) 0;
				if (allcjnums != 0) {
					cglzb = (float) cjnums * 100 / allcjnums;
				}
				objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
				// ------其中未成交人次
				objhj.put("wcjnums", wcjnums);

				// ------初诊总人次
				objhj.put("czallnums", czallnums);
				// ------初诊成交人次
				objhj.put("czcjnums", czcjnums);

				// ------复诊总人次
				objhj.put("fzallnums", fzallnums);
				// ------复诊成交人次
				objhj.put("fzcjnums", fzcjnums);

				// ------再消费总人次
				objhj.put("zxfallnums", zxfallnums);
				// ------再消费成交人次
				objhj.put("zxfcjnums", zxfcjnums);

				// ------复查总人次
				objhj.put("fcallnums", fcallnums);

				// ------复查成交人次
				objhj.put("fccjnums", fccjnums);
				// ------其他总人次
				objhj.put("qtallnums", qtallnums);
				// ------其他成交人次
				objhj.put("qtcjnums", qtcjnums);
				// ------收款金额
				String sk = "0.00";
				if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
					sk = getskje4AskPerson(skjeList, skjeYjjList, objper.getString("seqId"), null);
					String ss = getskje4AskPersonSS(skjeList, skjeYjjList, objper.getString("seqId"), null);
					objhj.put("ssje", ss);
				} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
					// 营业收入
					sk = getskje4Doctor(yysrList, objper.getString("seqId"), null);
				}
				objhj.put("skje", sk);
				// ---如果 咨询人次为0，不展示
				if (zxnums == 0 && KqdsBigDecimal.compareTo(new BigDecimal(sk), BigDecimal.ZERO) == 0) {
					continue;
				}
				all_advance=all_advance.add(per_advance);
				objhj.put("totalAdvance",per_advance.equals(BigDecimal.ZERO)?BigDecimal.ZERO:df.format(per_advance.setScale(2,BigDecimal.ROUND_HALF_UP)));
				listAll.add(objhj);
			}
			/** -------------------------------------------二级细分，细分到人和挂号分类 END **/
			// ------------------总计 行（条件去除挂号分类）
			int cjnums = 0; // ------其中成交人次
			int wcjnums = 0;// ------其中未成交人次
			int czallnums = 0; // ------初诊总人次
			int czcjnums = 0; // ------初诊成交人次
			int fzallnums = 0; // ------复诊总人次
			int fzcjnums = 0;// ------复诊成交人次
			int zxfallnums = 0;// ------再消费总人次
			int zxfcjnums = 0;// ------再消费成交人次
			int fcallnums = 0;// ------复查总人次
			int qtallnums = 0; // ------其他总人次
			int fccjnums = 0;// ------复查成交人次
			int qtcjnums = 0; // ------其他成交人次
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				String recesort = regJson.getString("recesort");
				if ("1".equals(cjstatus)) {
					cjnums++;
				}

				if ("0".equals(cjstatus)) {
					wcjnums++;
				}

				if (czseqId.equals(recesort)) {
					czallnums++;
				}
				if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
					czcjnums++;
				}

				if (fzseqId.equals(recesort)) {
					fzallnums++;
				}
				if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
					fzcjnums++;
				}

				if (zxfseqId.equals(recesort)) {
					zxfallnums++;
				}
				if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
					zxfcjnums++;
				}

				if (fcseqId.equals(recesort)) {
					fcallnums++;
				}
				if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
					fccjnums++;
				}
				if (qtseqId.equals(recesort)) {
					qtallnums++;
				}
				if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
					qtcjnums++;
				}
			}

			JSONObject objzj = new JSONObject();
			// ------咨询人次（医生情况统计，查询总数 排除 挂号表医生为空的情况）
			int zxnums = listRegAllTotal.size();
			objzj.put("zxnums", zxnums);
			// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
			Float zzlzb = (float) 0;
			if (allnums != 0) {
				zzlzb = (float) zxnums * 100 / allnums;
			}
			objzj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
			// ------其中成交人次
			objzj.put("cjnums", cjnums);
			// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
			Float cgl = (float) 0;
			if (zxnums != 0) {
				cgl = (float) cjnums * 100 / zxnums;
			}
			objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
			// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
			Float cglzb = (float) 0;
			if (allcjnums != 0) {
				cglzb = (float) cjnums * 100 / allcjnums;
			}
			objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
			// ------其中未成交人次
			objzj.put("wcjnums", wcjnums);

			// ------初诊总人次
			objzj.put("czallnums", czallnums);
			// ------初诊成交人次
			objzj.put("czcjnums", czcjnums);

			// ------复诊总人次
			objzj.put("fzallnums", fzallnums);
			// ------复诊成交人次
			objzj.put("fzcjnums", fzcjnums);

			// ------再消费总人次
			objzj.put("zxfallnums", zxfallnums);
			// ------再消费成交人次
			objzj.put("zxfcjnums", zxfcjnums);

			// ------复查总人次
			objzj.put("fcallnums", fcallnums);

			// ------复查成交人次
			objzj.put("fccjnums", fccjnums);
			// ------其他总人次
			objzj.put("qtallnums", qtallnums);
			// ------其他成交人次
			objzj.put("qtcjnums", qtcjnums);
			// ------收款金额
			String sk = "0.00";
			if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
				sk = getskje4AskPerson(skjeList, skjeYjjList, null, null);
				String ss = getskje4AskPersonSS(skjeList, skjeYjjList, null, null);
				objzj.put("ssje", ss);
			} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
				// 营业收入
				sk = getskje4Doctor(yysrList, null, null);
			}
			objzj.put("skje", sk);
			// ---如果 咨询人次为0，不展示
			if (zxnums > 0) {
				if(all_advance.equals(BigDecimal.ZERO)){
					objzj.put("totalAdvance",BigDecimal.ZERO);
				}else{
					objzj.put("totalAdvance", df.format(all_advance.setScale(2,BigDecimal.ROUND_HALF_UP)));
				}
								
				listAll.add(objzj);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

			// System.out.println(System.currentTimeMillis() - start);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	/**
	 * 1.咨询/医生成功率分析表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/selectCountDoctorBB.act")
	public String selectCountDoctorBB(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");

			//按钮标识
			String qxname=request.getParameter("qxname");
			//部门id
			String deptCategory=request.getParameter("deptCategory");
			//人员id
			String personId=request.getParameter("recesort");
			
			
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			
			if(!YZUtility.isNullorEmpty(qxname)){
				map.put("buttonName", "\'"+qxname+"\'");
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
			if(!YZUtility.isNullorEmpty(personId)){
				map.put("personId", personId);	
			}
			
			
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			} else {
				map.put("organization", organization);
			}
			List<JSONObject> listAll=logic.findDoctorSituationByTime(request,map);

			
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);


		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	  * @Title: consultingStatistics   
	  * @Description: TODO(咨询情况工作统计)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月17日 上午9:25:15
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/consultingStatistics.act")
	public String consultingStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String depttype = request.getParameter("depttype");// 0咨询 1医生 2网电
																// 3营销
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			} else {
				map.put("organization", organization);
			}
			BigDecimal all_advance = new BigDecimal(0);
			DecimalFormat df = new DecimalFormat("#.00");
			// （医生情况统计，查询总数 排除 挂号表医生为空的情况）
			map.put("depttype", depttype);
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> listPerson = personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);

			// 组装查询过滤条件，防止脏数据干扰统计准确性，比如cost_order_detail表中的咨询人员，在sys_person表中已经不存在
			StringBuffer personIds4QueryBf = new StringBuffer();
			for (JSONObject person : listPerson) {
				personIds4QueryBf.append(person.getString("seqId")).append(",");
			}
			String personIds4Query = YZUtility.ConvertStringIds4Query(personIds4QueryBf.toString());
			map.put("personIds4Query", personIds4Query);

			List<JSONObject> listAll = new ArrayList<JSONObject>();

			// 获取咨询项目
			List<YZDict> listDict = dictLogic.getListByParentCodeALL("GHFL", ChainUtil.getCurrentOrganization(request));

			// 查出所有挂号
			List<JSONObject> listRegAllTotal = logic.getListRegByGhfl(map, "", "");

			// 总咨询人数（所有咨询）（医生情况统计，查询总数 排除 挂号表医生为空的情况）
			int allnums = listRegAllTotal.size();
			// 总咨询成交人数（所有咨询）（医生情况统计，查询总数 排除 挂号表医生为空的情况）
			int allcjnums = 0;
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				if ("1".equals(cjstatus)) {
					allcjnums++;
				}
			}

			/** 营收数据列表 **/
			List<JSONObject> skjeList = null;
			List<JSONObject> skjeYjjList = null;
			List<JSONObject> yysrList = null;
			if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
				skjeList = logic.getYysrList(map);
				skjeYjjList = logic.getYysrYjjList(map);
			} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
				yysrList = logic.getYysrList(map, ChainUtil.getOrganizationFromUrl(request));
			}

			// 获取初诊编码
			String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
			// 获取复诊编码
			String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
			// 获取再消费编码
			String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
			// 获取复查编码
			String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
			// 获取其他编码
			String qtseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);

			/** 一级细分，细分到人 **/
			for (JSONObject personJson : listPerson) { // 然后根据用户id，进行分组，过滤，目的是减少查询数据库次数
				String personseqId = personJson.getString("seqId");
				List<JSONObject> listRegFilter = new ArrayList<JSONObject>();
				for (JSONObject regJson : listRegAllTotal) {
					if (depttype.equals(ConstUtil.DEPT_TYPE_0)) { // 0 咨询
						if (personseqId.equals(regJson.getString("askperson"))) {
							listRegFilter.add(regJson);
						}
					} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) { // 1 医生
						if (personseqId.equals(regJson.getString("doctor"))) {
							listRegFilter.add(regJson);
						}
					}
				}
				personJson.put("personReglist", listRegFilter);// ----------------------
			}

			/** 二级细分，细分到人和挂号分类 **/
			for (int i = 0; i < listPerson.size(); i++) {
				JSONObject objper = listPerson.get(i);
				BigDecimal per_advance = new BigDecimal(0);
				// 从用户JsonObject获取对应的挂号列表
				List<JSONObject> listRegAll = (List) objper.get("personReglist"); // ----------------------

				if (listRegAll == null || listRegAll.size() == 0) {
					continue;
				}

				List<JSONObject> jsonListDict = JSONArray.fromObject(listDict);
				// 遍历咨询项目
				for (JSONObject jsonDict : jsonListDict) {
					String regsort = jsonDict.getString("seqId");
					List<JSONObject> listRegFilter = new ArrayList<JSONObject>();
					for (JSONObject jsonReg : listRegAll) {
						if (jsonReg.getString("regsort").equals(regsort)) { // 根据挂号分类过滤
							listRegFilter.add(jsonReg);
						}
					}
					jsonDict.put("reglist", listRegFilter); // ----------------------
				}

				for (JSONObject dict : jsonListDict) {
					JSONObject obj = new JSONObject();
					// ------人员
					obj.put("username", objper.getString("userName"));
					// ------咨询项目/挂号分类
					obj.put("zxxm", dict.getString("dictName"));

					List<JSONObject> list = (List) dict.get("reglist"); // -------------人+挂号分类对应的挂号列表
					// ------咨询人次
					int zxnums = list.size();
					obj.put("zxnums", zxnums);
					// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
					Float zzlzb = (float) 0;
					if (allnums != 0) {
						zzlzb = (float) zxnums * 100 / allnums;
					}
					obj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");

					int cjnums = 0; // ------其中成交人次
					int wcjnums = 0;// ------其中未成交人次
					int czallnums = 0; // ------初诊总人次
					int czcjnums = 0; // ------初诊成交人次
					int fzallnums = 0; // ------复诊总人次
					int fzcjnums = 0;// ------复诊成交人次
					int zxfallnums = 0;// ------再消费总人次
					int zxfcjnums = 0;// ------再消费成交人次
					int fcallnums = 0;// ------复查总人次
					int fccjnums = 0;// ------复查成交人次
					int qtallnums = 0; // ------其他总人次
					int qtcjnums = 0; // ------其他成交人次
					for (JSONObject regJson : list) {
						String cjstatus = regJson.getString("cjstatus");
						String recesort = regJson.getString("recesort");
						if ("1".equals(cjstatus)) {
							cjnums++;
						}

						if ("0".equals(cjstatus)) {
							wcjnums++;
						}

						if (czseqId.equals(recesort)) {
							czallnums++;
						}
						if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
							czcjnums++;
						}

						if (fzseqId.equals(recesort)) {
							fzallnums++;
						}
						if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
							fzcjnums++;
						}

						if (zxfseqId.equals(recesort)) {
							zxfallnums++;
						}
						if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
							zxfcjnums++;
						}

						if (fcseqId.equals(recesort)) {
							fcallnums++;
						}
						if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
							fccjnums++;
						}
						if (qtseqId.equals(recesort)) {
							qtallnums++;
						}
						if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
							qtcjnums++;
						}
					}

					// ------其中成交人次
					obj.put("cjnums", cjnums);
					// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
					Float cgl = (float) 0;
					if (zxnums != 0) {
						cgl = (float) cjnums * 100 / zxnums;
					}
					obj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
					// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
					Float cglzb = (float) 0;
					if (allcjnums != 0) {
						cglzb = (float) cjnums * 100 / allcjnums;
					}
					obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");

					// ------其中未成交人次
					obj.put("wcjnums", wcjnums);

					// ------初诊总人次
					obj.put("czallnums", czallnums);
					// ------初诊成交人次
					obj.put("czcjnums", czcjnums);

					// ------复诊总人次
					obj.put("fzallnums", fzallnums);
					// ------复诊成交人次
					obj.put("fzcjnums", fzcjnums);

					// ------再消费总人次
					obj.put("zxfallnums", zxfallnums);
					// ------再消费成交人次
					obj.put("zxfcjnums", zxfcjnums);

					// ------复查总人次
					obj.put("fcallnums", fcallnums);
					// ------复查成交人次
					obj.put("fccjnums", fccjnums);
					// ------其他总人次
					obj.put("qtallnums", qtallnums);
					// ------其他成交人次
					obj.put("qtcjnums", qtcjnums);
					// ------收款金额
					String sk = "0.00";
					if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
						sk = getskje4AskPerson(skjeList, skjeYjjList, objper.getString("seqId"), dict.getString("seqId"));
						String ss = getskje4AskPersonSS(skjeList, skjeYjjList, objper.getString("seqId"), dict.getString("seqId"));
						obj.put("ssje", ss);
					} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
						// 营业收入
						sk = getskje4Doctor(yysrList, objper.getString("seqId"), dict.getString("seqId"));
					}
					obj.put("skje", sk);
					// ---如果 咨询人次为0，不展示
					if (zxnums == 0 && KqdsBigDecimal.compareTo(new BigDecimal(sk), BigDecimal.ZERO) == 0) {
						continue;
					}
					//JSONObject
					 BigDecimal singleAdvance=new BigDecimal(0);
					 //医生-项目-挂号（挂号信息可能为多个，对其进行遍历，并取遍历每项的挂号编号作为参数去费用表去查该医生对该挂号的费用添加信息）
					 for (JSONObject regnojson : list) {
						 Map perMap=   new HashMap();
						 perMap.put("starttime", starttime);
						 perMap.put("endtime", endtime);
						 perMap.put("createuser",objper.getString("seq_id"));
						 perMap.put("regno", regnojson.getString("seq_id"));	
						 BigDecimal regAdvance= logic.getPerAdvance(perMap);
						 singleAdvance=singleAdvance.add(regAdvance);
					}
					per_advance=per_advance.add(singleAdvance);
					obj.put("totalAdvance",singleAdvance.equals(BigDecimal.ZERO)?BigDecimal.ZERO:df.format(singleAdvance.setScale(2,BigDecimal.ROUND_HALF_UP)));
					listAll.add(obj);
				}
				// ------------------合计 行（条件去除挂号分类）
				JSONObject objhj = new JSONObject();

				int cjnums = 0; // ------其中成交人次
				int wcjnums = 0;// ------其中未成交人次
				int czallnums = 0; // ------初诊总人次
				int czcjnums = 0; // ------初诊成交人次
				int fzallnums = 0; // ------复诊总人次
				int fzcjnums = 0;// ------复诊成交人次
				int zxfallnums = 0;// ------再消费总人次
				int zxfcjnums = 0;// ------再消费成交人次
				int fcallnums = 0;// ------复查总人次
				int fccjnums = 0;// ------复查成交人次
				int qtallnums = 0; // ------其他总人次
				int qtcjnums = 0; // ------其他成交人次
				for (JSONObject regJson : listRegAll) {
					String cjstatus = regJson.getString("cjstatus");
					String recesort = regJson.getString("recesort");
					if ("1".equals(cjstatus)) {
						cjnums++;
					}
					if ("0".equals(cjstatus)) {
						wcjnums++;
					}
					if (czseqId.equals(recesort)) {
						czallnums++;
					}
					if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
						czcjnums++;
					}
					if (fzseqId.equals(recesort)) {
						fzallnums++;
					}
					if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
						fzcjnums++;
					}
					if (zxfseqId.equals(recesort)) {
						zxfallnums++;
					}
					if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
						zxfcjnums++;
					}
					if (fcseqId.equals(recesort)) {
						fcallnums++;
					}
					if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
						fccjnums++;
					}
					if (qtseqId.equals(recesort)) {
						qtallnums++;
					}
					if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
						qtcjnums++;
					}
				}
				// ------咨询人次
				int zxnums = listRegAll.size();
				objhj.put("zxnums", zxnums);
				// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
				Float zzlzb = (float) 0;
				if (allnums != 0) {
					zzlzb = (float) zxnums * 100 / allnums;
				}
				objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
				// ------其中成交人次
				objhj.put("cjnums", cjnums);
				// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
				Float cgl = (float) 0;
				if (zxnums != 0) {
					cgl = (float) cjnums * 100 / zxnums;
				}
				objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
				// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
				Float cglzb = (float) 0;
				if (allcjnums != 0) {
					cglzb = (float) cjnums * 100 / allcjnums;
				}
				objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
				// ------其中未成交人次
				objhj.put("wcjnums", wcjnums);

				// ------初诊总人次
				objhj.put("czallnums", czallnums);
				// ------初诊成交人次
				objhj.put("czcjnums", czcjnums);

				// ------复诊总人次
				objhj.put("fzallnums", fzallnums);
				// ------复诊成交人次
				objhj.put("fzcjnums", fzcjnums);

				// ------再消费总人次
				objhj.put("zxfallnums", zxfallnums);
				// ------再消费成交人次
				objhj.put("zxfcjnums", zxfcjnums);

				// ------复查总人次
				objhj.put("fcallnums", fcallnums);

				// ------复查成交人次
				objhj.put("fccjnums", fccjnums);
				// ------其他总人次
				objhj.put("qtallnums", qtallnums);
				// ------其他成交人次
				objhj.put("qtcjnums", qtcjnums);
				// ------收款金额
				String sk = "0.00";
				if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
					sk = getskje4AskPerson(skjeList, skjeYjjList, objper.getString("seqId"), null);
					String ss = getskje4AskPersonSS(skjeList, skjeYjjList, objper.getString("seqId"), null);
					objhj.put("ssje", ss);
				} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
					// 营业收入
					sk = getskje4Doctor(yysrList, objper.getString("seqId"), null);
				}
				objhj.put("skje", sk);
				// ---如果 咨询人次为0，不展示
				if (zxnums == 0 && KqdsBigDecimal.compareTo(new BigDecimal(sk), BigDecimal.ZERO) == 0) {
					continue;
				}
				all_advance=all_advance.add(per_advance);
				objhj.put("totalAdvance",per_advance.equals(BigDecimal.ZERO)?BigDecimal.ZERO:df.format(per_advance.setScale(2,BigDecimal.ROUND_HALF_UP)));
				listAll.add(objhj);
			}
			/** -------------------------------------------二级细分，细分到人和挂号分类 END **/
			// ------------------总计 行（条件去除挂号分类）
			int cjnums = 0; // ------其中成交人次
			int wcjnums = 0;// ------其中未成交人次
			int czallnums = 0; // ------初诊总人次
			int czcjnums = 0; // ------初诊成交人次
			int fzallnums = 0; // ------复诊总人次
			int fzcjnums = 0;// ------复诊成交人次
			int zxfallnums = 0;// ------再消费总人次
			int zxfcjnums = 0;// ------再消费成交人次
			int fcallnums = 0;// ------复查总人次
			int qtallnums = 0; // ------其他总人次
			int fccjnums = 0;// ------复查成交人次
			int qtcjnums = 0; // ------其他成交人次
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				String recesort = regJson.getString("recesort");
				if ("1".equals(cjstatus)) {
					cjnums++;
				}

				if ("0".equals(cjstatus)) {
					wcjnums++;
				}

				if (czseqId.equals(recesort)) {
					czallnums++;
				}
				if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
					czcjnums++;
				}

				if (fzseqId.equals(recesort)) {
					fzallnums++;
				}
				if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
					fzcjnums++;
				}

				if (zxfseqId.equals(recesort)) {
					zxfallnums++;
				}
				if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
					zxfcjnums++;
				}

				if (fcseqId.equals(recesort)) {
					fcallnums++;
				}
				if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
					fccjnums++;
				}
				if (qtseqId.equals(recesort)) {
					qtallnums++;
				}
				if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
					qtcjnums++;
				}
			}

			JSONObject objzj = new JSONObject();
			// ------咨询人次（医生情况统计，查询总数 排除 挂号表医生为空的情况）
			int zxnums = listRegAllTotal.size();
			objzj.put("zxnums", zxnums);
			// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
			Float zzlzb = (float) 0;
			if (allnums != 0) {
				zzlzb = (float) zxnums * 100 / allnums;
			}
			objzj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
			// ------其中成交人次
			objzj.put("cjnums", cjnums);
			// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
			Float cgl = (float) 0;
			if (zxnums != 0) {
				cgl = (float) cjnums * 100 / zxnums;
			}
			objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
			// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
			Float cglzb = (float) 0;
			if (allcjnums != 0) {
				cglzb = (float) cjnums * 100 / allcjnums;
			}
			objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
			// ------其中未成交人次
			objzj.put("wcjnums", wcjnums);

			// ------初诊总人次
			objzj.put("czallnums", czallnums);
			// ------初诊成交人次
			objzj.put("czcjnums", czcjnums);

			// ------复诊总人次
			objzj.put("fzallnums", fzallnums);
			// ------复诊成交人次
			objzj.put("fzcjnums", fzcjnums);

			// ------再消费总人次
			objzj.put("zxfallnums", zxfallnums);
			// ------再消费成交人次
			objzj.put("zxfcjnums", zxfcjnums);

			// ------复查总人次
			objzj.put("fcallnums", fcallnums);

			// ------复查成交人次
			objzj.put("fccjnums", fccjnums);
			// ------其他总人次
			objzj.put("qtallnums", qtallnums);
			// ------其他成交人次
			objzj.put("qtcjnums", qtcjnums);
			// ------收款金额
			String sk = "0.00";
			if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
				sk = getskje4AskPerson(skjeList, skjeYjjList, null, null);
				String ss = getskje4AskPersonSS(skjeList, skjeYjjList, null, null);
				objzj.put("ssje", ss);
			} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
				// 营业收入
				sk = getskje4Doctor(yysrList, null, null);
			}
			objzj.put("skje", sk);
			// ---如果 咨询人次为0，不展示
			if (zxnums > 0) {
				if(all_advance.equals(BigDecimal.ZERO)){
					objzj.put("totalAdvance",BigDecimal.ZERO);
				}else{
					objzj.put("totalAdvance", df.format(all_advance.setScale(2,BigDecimal.ROUND_HALF_UP)));
				}
								
				listAll.add(objzj);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

			// System.out.println(System.currentTimeMillis() - start);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	
	/**
	 * 1.咨询/医生成功率分析表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountYj.act")
	public String selectCountYj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String depttype = request.getParameter("depttype");// 0咨询 1医生 2网电
																// 3营销
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			} else {
				map.put("organization", organization);
			}
			// （医生情况统计，查询总数 排除 挂号表医生为空的情况）
			map.put("depttype", depttype);
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> listPerson = personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);

			// 组装查询过滤条件，防止脏数据干扰统计准确性，比如cost_order_detail表中的咨询人员，在sys_person表中已经不存在
			StringBuffer personIds4QueryBf = new StringBuffer();
			for (JSONObject person : listPerson) {
				personIds4QueryBf.append(person.getString("seqId")).append(",");
			}
			String personIds4Query = YZUtility.ConvertStringIds4Query(personIds4QueryBf.toString());
			map.put("personIds4Query", personIds4Query);
			/** 应收、实收数据列表 **/
			List<JSONObject> costdetailList = null;
			if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
				costdetailList = logic.getYysrDetailList(map);
			} else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
				costdetailList = logic.getYysrDetailList(map, ChainUtil.getOrganizationFromUrl(request));
			}
			List<JSONObject> listAll = new ArrayList<JSONObject>();
			// 总计
			BigDecimal zj_ysje = BigDecimal.ZERO;
			BigDecimal zj_ssje = BigDecimal.ZERO;
			for (int i = 0; i < listPerson.size(); i++) {
				// 合计
				BigDecimal hj_ysje = BigDecimal.ZERO;
				BigDecimal hj_ssje = BigDecimal.ZERO;
				JSONObject objper = listPerson.get(i);
				// 筛选出objper的消费信息
				List<JSONObject> listPersonDetail = new ArrayList<JSONObject>();
				for (JSONObject jsonDetail : costdetailList) {
					String perid = objper.getString("seqId");
					if (jsonDetail.getString("perid").equals(perid)) { // 根据医生/咨询过滤
						listPersonDetail.add(jsonDetail);
					}
				}
				if (listPersonDetail.size() == 0) {
					continue;
				}

				/********
				 * ############################################################# ####
				 * 根据一二级分类，进行处理 #################
				 *************/
				Set<String> currSortSet = new HashSet<String>(); // 获取不重复的一二级消费分类
				Map<String, String> sortNameMap = new HashMap<String, String>();
				for (JSONObject perDetail : listPersonDetail) {
					String basetype = perDetail.getString("basetype");
					String nexttype = perDetail.getString("nexttype");
					String base_next = basetype + "," + nexttype;
					currSortSet.add(base_next);
					// 存储分类名称
					sortNameMap.put(basetype, perDetail.getString("basename"));
					sortNameMap.put(nexttype, perDetail.getString("nextname"));
				}

				for (String base_next : currSortSet) {
					if (YZUtility.isNullorEmpty(base_next)) {
						continue;
					}
					String[] sortArray = base_next.split(",");
					if (sortArray.length != 2) {
						continue;
					}
					String basetype = sortArray[0]; // 一级分类
					String nexttype = sortArray[1]; // 二级分类

					if (YZUtility.isNullorEmpty(basetype) || YZUtility.isNullorEmpty(nexttype)) {
						continue;
					}

					JSONObject obj = new JSONObject();
					// ------人员
					obj.put("username", objper.getString("userName"));
					// ------消费一级分类
					obj.put("basename", sortNameMap.get(basetype));
					// ------消费二级分类
					obj.put("nextname", sortNameMap.get(nexttype));

					BigDecimal ysje = BigDecimal.ZERO;
					BigDecimal ssje = BigDecimal.ZERO;
					for (JSONObject perDetail : listPersonDetail) {
						String basetype2 = perDetail.getString("basetype");
						String nexttype2 = perDetail.getString("nexttype");

						if (basetype.equals(basetype2) && nexttype.equals(nexttype2)) {
							// -------应收金额（小计-免除）
							String subtotal = perDetail.getString("subtotal");
							String voidmoney = perDetail.getString("voidmoney");
							BigDecimal ys = new BigDecimal(subtotal).subtract(new BigDecimal(voidmoney));
							ysje = ysje.add(KqdsBigDecimal.round(ys, 2));
							// -------实收金额
							String paymoney = perDetail.getString("paymoney");
							String payother2 = perDetail.getString("payother2");
							String paydjq = perDetail.getString("paydjq");
							String payintegral = perDetail.getString("payintegral");
							BigDecimal ss = new BigDecimal(paymoney).subtract(new BigDecimal(payother2)).subtract(new BigDecimal(paydjq)).subtract(new BigDecimal(payintegral));
							ssje = ssje.add(KqdsBigDecimal.round(ss, 2));
						}
					}
					obj.put("ysje", ysje);
					obj.put("ssje", ssje);
					listAll.add(obj);
					hj_ysje = hj_ysje.add(ysje);
					hj_ssje = hj_ssje.add(ssje);
				}
				/********
				 * ############################################################# ####
				 * 根据一二级分类，进行处理 END #################
				 *************/
				// ------------------合计 行
				JSONObject objhj = new JSONObject();
				objhj.put("ysje", KqdsBigDecimal.round(hj_ysje, 2));
				objhj.put("ssje", KqdsBigDecimal.round(hj_ssje, 2));
				listAll.add(objhj);
				// System.out.println("-------合计----");
				zj_ysje = zj_ysje.add(hj_ysje);
				zj_ssje = zj_ssje.add(hj_ssje);

			}
			// ------------------总计 行
			if (costdetailList.size() > 0) {
				JSONObject objzj = new JSONObject();
				objzj.put("ysje", KqdsBigDecimal.round(zj_ysje, 2));
				objzj.put("ssje", KqdsBigDecimal.round(zj_ssje, 2));
				listAll.add(objzj);
				// System.out.println("-------总计----");
			}
			List<JSONObject> listAll1 = new ArrayList<JSONObject>();
			for (int i = 0; i < listAll.size(); i++) {
				if(listAll.get(i).size()>2){
					int a=0;
					int h=0;
					for (int j = 0; j < listAll1.size(); j++) {
						if(listAll.get(i).getString("basename").equals(listAll1.get(j).getString("basename"))){
							a=1;
							h=j;
						}
					}
					//无该字段值
					if(a!=1){
						JSONObject jso= new JSONObject();
						jso.put("basename",listAll.get(i).getString("basename"));
						jso.put("ssje",listAll.get(i).getString("ssje"));
						listAll1.add(jso);
					}else{
						listAll1.get(h).put("ssje", (new BigDecimal(listAll1.get(h).getString("ssje")).add(new BigDecimal(listAll.get(i).getString("ssje")))).toString());
					}
				}
			}
			List<JSONObject> pluslist = new ArrayList<JSONObject>();
			List<JSONObject> minuslist = new ArrayList<JSONObject>();
			for (JSONObject jsonObject : listAll1) {
				if(new BigDecimal(jsonObject.getString("ssje")).compareTo(new BigDecimal("0"))==1||new BigDecimal(jsonObject.getString("ssje")).compareTo(new BigDecimal("0"))==0){
					pluslist.add(jsonObject);
				}else{
					minuslist.add(jsonObject);
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			jobj.put("plus", pluslist);
			jobj.put("minus", minuslist);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

			// System.out.println(System.currentTimeMillis() - start);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	@RequestMapping(value = "/selectCountDoctorYj.act")
	public String selectCountDoctorYj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			
			//按钮标识
			String qxname=request.getParameter("qxname");
			//部门id
			String deptCategory=request.getParameter("deptCategory");
			//人员id
			String personId=request.getParameter("recesort");
			
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			
			if(!YZUtility.isNullorEmpty(qxname)){
				map.put("buttonName", qxname);	
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
			if(!YZUtility.isNullorEmpty(personId)){
				map.put("personId", personId);	
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			} else {
				map.put("organization", organization);
			}
			List<JSONObject> listAll=logic.findDoctorPerformanceByTime(request,map);
			List<JSONObject> pluslist = new ArrayList<JSONObject>();
			List<JSONObject> minuslist = new ArrayList<JSONObject>();
			for (JSONObject jsonObject : listAll) {
				if(jsonObject.size()>1){
					if(new BigDecimal(jsonObject.getString("ssje")).compareTo(new BigDecimal("0"))==1||new BigDecimal(jsonObject.getString("ssje")).compareTo(new BigDecimal("0"))==0){
						pluslist.add(jsonObject);
					}else{
						minuslist.add(jsonObject);
					}
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			jobj.put("plus", pluslist);
			jobj.put("minus", minuslist);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

			// System.out.println(System.currentTimeMillis() - start);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	/**
	 * 1.咨询/医生成功率分析表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountClcb.act")
	public String selectCountClcb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String depttype = request.getParameter("depttype");// 0咨询 1医生 2网电//
																// 3营销
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			} else {
				map.put("organization", organization);
			}
			// （医生情况统计，查询总数 排除 挂号表医生为空的情况）
			map.put("depttype", depttype);
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> listPerson = personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);

			// 组装查询过滤条件，防止脏数据干扰统计准确性，比如cost_order_detail表中的咨询人员，在sys_person表中已经不存在
			StringBuffer personIds4QueryBf = new StringBuffer();
			for (JSONObject person : listPerson) {
				personIds4QueryBf.append(person.getString("seqId")).append(",");
			}
			String personIds4Query = YZUtility.ConvertStringIds4Query(personIds4QueryBf.toString());
			map.put("personIds4Query", personIds4Query);
			/** 应收、实收数据列表 **/
			List<JSONObject> costdetailList = null;
			/** 加工金额数据列表 **/
			List<JSONObject> jgdetailList = null;
			/** 仓库金额数据列表 **/
			List<JSONObject> ckList = null;
			/*
			 * if (depttype.equals(ConstUtil.DEPT_TYPE_0)) { costdetailList =
			 * logic.getYysrDetailList(map); } else if
			 * (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
			 */
			// costdetailList = logic.getYysrDetailList(map,
			// ChainUtil.getOrganizationFromUrl(request));
			/* } */
			costdetailList = logic.getYysrDetailList(map, ChainUtil.getOrganizationFromUrl(request));
			jgdetailList = logic.getJgdetailList(map, ChainUtil.getOrganizationFromUrl(request));
			ckList = logic.getCkDetailList(map, ChainUtil.getOrganizationFromUrl(request));
			List<JSONObject> listAll = new ArrayList<JSONObject>();
			// 总计
			BigDecimal zj_ysje = BigDecimal.ZERO;
			BigDecimal zj_ssje = BigDecimal.ZERO;
			BigDecimal zj_jgje = BigDecimal.ZERO;
			BigDecimal zj_ckje = BigDecimal.ZERO;
			for (int i = 0; i < listPerson.size(); i++) {
				JSONObject objper = listPerson.get(i);
				// 筛选出objper的消费信息
				List<JSONObject> listPersonCostDetail = new ArrayList<JSONObject>();
				for (JSONObject jsonDetail : costdetailList) {
					String perid = objper.getString("seqId");
					if (jsonDetail.getString("perid").equals(perid)) { // 根据医生/咨询过滤
						listPersonCostDetail.add(jsonDetail);
					}
				}
				List<JSONObject> listPersonjgdetailList = new ArrayList<JSONObject>();
				for (JSONObject jsonDetail : jgdetailList) {
					String perid = objper.getString("seqId");
					if (jsonDetail.getString("createuser").equals(perid)) { // 根据医生/咨询过滤
						listPersonjgdetailList.add(jsonDetail);
					}
				}
				List<JSONObject> listPersonckdetailList = new ArrayList<JSONObject>();
				for (JSONObject jsonDetail : ckList) {
					String perid = objper.getString("seqId");
					if (jsonDetail.getString("sqdoctor").equals(perid)) { // 根据医生/咨询过滤
						listPersonckdetailList.add(jsonDetail);
					}
				}
				if (listPersonCostDetail.size() == 0 && listPersonjgdetailList.size() == 0 && listPersonckdetailList.size() == 0) {
					continue;
				}
				JSONObject obj = new JSONObject();
				// ------人员
				obj.put("username", objper.getString("userName"));

				BigDecimal ysje = BigDecimal.ZERO;
				BigDecimal ssje = BigDecimal.ZERO;
				BigDecimal jgje = BigDecimal.ZERO;
				BigDecimal ckje = BigDecimal.ZERO;
				for (JSONObject perDetail : listPersonCostDetail) {
					// -------应收金额（小计-免除）
					String subtotal = perDetail.getString("subtotal");
					String voidmoney = perDetail.getString("voidmoney");
					BigDecimal ys = new BigDecimal(subtotal).subtract(new BigDecimal(voidmoney));
					ysje = ysje.add(KqdsBigDecimal.round(ys, 2));
					// -------实收金额
					String paymoney = perDetail.getString("paymoney");
					String payother2 = perDetail.getString("payother2");
					String paydjq = perDetail.getString("paydjq");
					String payintegral = perDetail.getString("payintegral");
					BigDecimal ss = new BigDecimal(paymoney).subtract(new BigDecimal(payother2)).subtract(new BigDecimal(paydjq)).subtract(new BigDecimal(payintegral));
					ssje = ssje.add(KqdsBigDecimal.round(ss, 2));
				}
				for (JSONObject perDetail : listPersonjgdetailList) {
					// -------加工金额
					if (YZUtility.isNotNullOrEmpty(perDetail.getString("subtotal"))) {
						BigDecimal subtotal = new BigDecimal(perDetail.getString("subtotal"));
						jgje = jgje.add(KqdsBigDecimal.round(subtotal, 2));
					}
				}
				for (JSONObject perDetail : listPersonckdetailList) {
					// -------仓库金额
					if (YZUtility.isNotNullOrEmpty(perDetail.getString("subtotal"))) {
						BigDecimal subtotal = new BigDecimal(perDetail.getString("subtotal"));
						ckje = ckje.add(KqdsBigDecimal.round(subtotal, 2));
					}
				}
				obj.put("ysje", ysje);
				obj.put("ssje", ssje);
				obj.put("jgje", jgje);
				// 加工金额占比 = 加工金额/实收金额
				BigDecimal jgzb = BigDecimal.ZERO;
				if (ssje.compareTo(BigDecimal.ZERO) != 0) {
					jgzb = jgje.multiply(new BigDecimal(100)).divide(ssje, 2, BigDecimal.ROUND_HALF_UP);
				}
				obj.put("jgzb", jgzb + "%");
				obj.put("ckje", ckje);
				// 仓库金额占比 = 仓库金额/实收金额
				BigDecimal ckzb = BigDecimal.ZERO;
				if (ssje.compareTo(BigDecimal.ZERO) != 0) {
					ckzb = ckje.multiply(new BigDecimal(100)).divide(ssje, 2, BigDecimal.ROUND_HALF_UP);
				}
				obj.put("ckzb", ckzb + "%");
				listAll.add(obj);
				zj_ysje = zj_ysje.add(ysje);
				zj_ssje = zj_ssje.add(ssje);
				zj_jgje = zj_jgje.add(jgje);
				zj_ckje = zj_ckje.add(ckje);

			}
			// ------------------总计 行
			if (costdetailList.size() > 0 || jgdetailList.size() > 0 || ckList.size() > 0) {
				JSONObject objzj = new JSONObject();
				objzj.put("ysje", KqdsBigDecimal.round(zj_ysje, 2));
				objzj.put("ssje", KqdsBigDecimal.round(zj_ssje, 2));
				objzj.put("jgje", KqdsBigDecimal.round(zj_jgje, 2));
				// 加工金额占比 = 加工金额/实收金额
				BigDecimal jgzbAll = BigDecimal.ZERO;
				if (zj_ssje.compareTo(BigDecimal.ZERO) != 0) {
					jgzbAll = zj_jgje.multiply(new BigDecimal(100)).divide(zj_ssje, 2, BigDecimal.ROUND_HALF_UP);
				}
				objzj.put("jgzb", jgzbAll + "%");
				objzj.put("ckje", KqdsBigDecimal.round(zj_ckje, 2));
				// 仓库金额占比 = 仓库金额/实收金额
				BigDecimal ckzbAll = BigDecimal.ZERO;
				if (zj_ssje.compareTo(BigDecimal.ZERO) != 0) {
					ckzbAll = zj_ckje.multiply(new BigDecimal(100)).divide(zj_ssje, 2, BigDecimal.ROUND_HALF_UP);
				}
				objzj.put("ckzb", ckzbAll + "%");
				listAll.add(objzj);
				// System.out.println("-------总计----");
			}

			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

			// System.out.println(System.currentTimeMillis() - start);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 患者来源汇总（实收金额）
	 * 
	 * @param skjeList
	 * @param skjeYjjList
	 * @param devchannel
	 * @param nexttype
	 * @return
	 */
	private String getssjeForHzly(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String devchannel, String nexttype) {
		String ss = "0.00";
		BigDecimal ssje = BigDecimal.ZERO;
		for (JSONObject rs : skjeList) {
			String devchannelT = rs.getString("devchannel");
			String nexttypeT = rs.getString("nexttype");
			if (!YZUtility.isNullorEmpty(devchannel)) {
				if (!devchannel.equals(devchannelT)) {
					continue;
				}
			}

			if (!YZUtility.isNullorEmpty(nexttype)) {
				if (!nexttypeT.equals(nexttype)) {
					continue;
				}
			}

			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));

			paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
			// 累加
			ssje = ssje.add(paymoney);
		}
		ss = ssje.toString();
		return ss;
	}

	/**
	 * 患者来源汇总（收款金额）
	 * 
	 * @param skjeList
	 * @param skjeYjjList
	 * @param devchannel
	 * @param nexttype
	 * @return
	 */
	private String getskjeForHzly(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String devchannel, String nexttype) {
		String sk = "0.00";
		BigDecimal skje = BigDecimal.ZERO;
		for (JSONObject rs : skjeList) {
			String devchannelT = rs.getString("devchannel");
			String nexttypeT = rs.getString("nexttype");
			if (!YZUtility.isNullorEmpty(devchannel)) {
				if (!devchannel.equals(devchannelT)) {
					continue;
				}
			}

			if (!YZUtility.isNullorEmpty(nexttype)) {
				if (!nexttypeT.equals(nexttype)) {
					continue;
				}
			}

			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
			BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));

			paymoney = paymoney.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
			// 累加
			skje = skje.add(paymoney);
		}
		// skje = skje.setScale(2, BigDecimal.ROUND_HALF_UP);

		BigDecimal skjeYjj = BigDecimal.ZERO;
		for (JSONObject rs : skjeYjjList) {
			String devchannelT = rs.getString("devchannel");
			String nexttypeT = rs.getString("nexttype");
			if (!YZUtility.isNullorEmpty(devchannel)) {
				if (!devchannelT.equals(devchannel)) {
					continue;
				}
			}

			if (!YZUtility.isNullorEmpty(nexttype)) {
				if (!nexttypeT.equals(nexttype)) {
					continue;
				}
			}
			BigDecimal paymoney = BigDecimal.ZERO;
			if (!YZUtility.isNullorEmpty(rs.getString("cmoney"))) {
				paymoney = new BigDecimal(rs.getString("cmoney"));
			}
			// 累加
			skjeYjj = skjeYjj.add(paymoney);
		}
		// skjeYjj = KqdsBigDecimal.round(skjeYjj, 2);
		// 汇总
		sk = skje.add(skjeYjj).toString();
		return sk;
	}

	/**
	 * 网电工作量汇总（收款金额）
	 * 
	 * @param skjeList
	 * @param skjeYjjList
	 * @param perid
	 * @return
	 */
	private String getskjeForWD(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String perid) {
		String sk = "0.00";
		BigDecimal skje = BigDecimal.ZERO;
		for (JSONObject rs : skjeList) {
			String createuser = rs.getString("createuser");
			if (YZUtility.isNullorEmpty(createuser)) {
				continue;
			}
			if (!createuser.equals(perid)) {
				continue;
			}

			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
			BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));

			paymoney = paymoney.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
			// 累加
			skje = skje.add(paymoney);
		}

		BigDecimal skjeYjj = BigDecimal.ZERO;
		for (JSONObject rs : skjeYjjList) {
			String createuser = rs.getString("createuser");
			if (YZUtility.isNullorEmpty(createuser)) {
				continue;
			}
			if (!createuser.equals(perid)) {
				continue;
			}

			BigDecimal paymoney = BigDecimal.ZERO;
			if (!YZUtility.isNullorEmpty(rs.getString("cmoney"))) {
				paymoney = new BigDecimal(rs.getString("cmoney"));
			}
			// 累加
			skjeYjj = skjeYjj.add(paymoney);
		}
		// 汇总
		sk = skje.add(skjeYjj).toString();
		return sk;
	}

	/**
	 * 咨询汇总
	 * 
	 * @param skjeList
	 * @param skjeYjjList
	 * @param askPersonSeqid
	 * @param regsort
	 * @param regsort
	 * @return
	 */
	private String getskje4AskPerson(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String askPersonSeqid, String regsort) {
		String sk = "0.00";
		BigDecimal skje = BigDecimal.ZERO;
		for (JSONObject rs : skjeList) {
			String askpersonT = rs.getString("askperson");
			String regsortT = rs.getString("regsort");
			if (!YZUtility.isNullorEmpty(askPersonSeqid)) {
				if (!askPersonSeqid.equals(askpersonT)) {
					continue;
				}
			}

			if (!YZUtility.isNullorEmpty(regsort)) {
				if (!regsortT.equals(regsort)) {
					continue;
				}
			}

			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
			BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));

			paymoney = paymoney.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
			// 累加
			skje = skje.add(paymoney);
		}
		// skje = skje.setScale(2, BigDecimal.ROUND_HALF_UP);

		BigDecimal skjeYjj = BigDecimal.ZERO;
		for (JSONObject rs : skjeYjjList) {
			String askpersonT = rs.getString("askperson");
			String regsortT = rs.getString("regsort");
			if (!YZUtility.isNullorEmpty(askPersonSeqid)) {
				if (!askPersonSeqid.equals(askpersonT)) {
					continue;
				}
			}

			if (!YZUtility.isNullorEmpty(regsort)) {
				if (!regsortT.equals(regsort)) {
					continue;
				}
			}
			BigDecimal paymoney = BigDecimal.ZERO;
			if (!YZUtility.isNullorEmpty(rs.getString("cmoney"))) {
				paymoney = new BigDecimal(rs.getString("cmoney"));
			}
			// 累加
			skjeYjj = skjeYjj.add(paymoney);
		}
		// skjeYjj = KqdsBigDecimal.round(skjeYjj, 2);
		// 汇总
		sk = skje.add(skjeYjj).toString();
		return sk;
	}

	/**
	 * 咨询汇总
	 * 
	 * @param skjeList
	 * @param skjeYjjList
	 * @param askPersonSeqid
	 * @param regsort
	 * @param regsort
	 * @return
	 */
	private String getskje4AskPersonSS(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String askPersonSeqid, String regsort) {
		String sk = "0.00";
		BigDecimal skje = BigDecimal.ZERO;
		for (JSONObject rs : skjeList) {
			String askpersonT = rs.getString("askperson");
			String regsortT = rs.getString("regsort");
			if (!YZUtility.isNullorEmpty(askPersonSeqid)) {
				if (!askPersonSeqid.equals(askpersonT)) {
					continue;
				}
			}

			if (!YZUtility.isNullorEmpty(regsort)) {
				if (!regsortT.equals(regsort)) {
					continue;
				}
			}

			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));

			paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
			// 累加
			skje = skje.add(paymoney);
		}
		sk = skje.toString();
		return sk;
	}

	/**
	 * 医生汇总
	 * 
	 * @param yysrList
	 * @param doctorSeqId
	 * @param regsort
	 * @return
	 */
	private String getskje4Doctor(List<JSONObject> yysrList, String doctorSeqId, String regsort) {
		String sk = "0.00";
		// 营业收入
		BigDecimal yysr = BigDecimal.ZERO;
		for (JSONObject rs : yysrList) {
			String doctorT = rs.getString("doctor");
			String regsortT = rs.getString("regsort");
			if (!YZUtility.isNullorEmpty(doctorSeqId)) {
				if (!doctorSeqId.equals(doctorT)) {
					continue;
				}
			}

			if (!YZUtility.isNullorEmpty(regsort)) {
				if (!regsortT.equals(regsort)) {
					continue;
				}
			}

			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
			BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("skze")) ? "0" : rs.getString("skze"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
			BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
			skze = skze.add(qf).subtract(zs).subtract(djq).subtract(integral);// 营业收入（项目收费）
																				// =
																				// 实收金额
			// + 欠费金额
			// 累加
			yysr = yysr.add(skze);
		}
		// yysr = KqdsBigDecimal.round(yysr, 2);
		sk = yysr.toString();
		return sk;
	}

	/**
	 * 1.2.信息来源统计表 该方法废弃### 2018-2-4 yangsen
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountXxly.act")
	public String selectCountXxly(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String regsort = request.getParameter("regsort");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			} else {
				map.put("organization", organization);
			}
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 查出所有挂号
			List<JSONObject> listRegAllTotal = logic.getCountHzly(map, visualstaff, "");
			// ------总来源人次
			int alllynums = listRegAllTotal.size();
			// ------总来源成交人次
			int allcjlynums = 0;
			// ------总来源未成交人次
			int allwcjlynums = 0;
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				if ("1".equals(cjstatus)) {
					allcjlynums++;
				} else {
					allwcjlynums++;
				}
			}

			/** 营收数据列表 **/
			List<JSONObject> skjeList = null;
			List<JSONObject> skjeYjjList = null;
			skjeList = logic.getYysrList(map);
			skjeYjjList = logic.getYysrYjjList(map);

			List<YZDict> listDict = dictLogic.getListByParentCodeALL(DictUtil.HZLY, ChainUtil.getCurrentOrganization(request));
			List<JSONObject> listAll = new ArrayList<JSONObject>();
			// 遍历患者来源类型
			for (YZDict dict : listDict) {
				List<YZDict> listHzly = dictLogic.getListByParentCodeALL(dict.getDictCode(), organization);
				// 遍历患者来源
				for (int i = 0; i < listHzly.size(); i++) {
					YZDict objper = listHzly.get(i);
					JSONObject obj = new JSONObject();
					// ------患者来源类型
					obj.put("hzlyType", dict.getDictName());
					// ------患者来源
					obj.put("hzlychildname", objper.getDictName());

					int lynums = 0; // ------来源人次
					int cjnums = 0; // ------其中成交人次
					int wcjnums = 0;// ------其中未成交人次

					for (JSONObject regJson : listRegAllTotal) {
						String cjstatus = regJson.getString("cjstatus");
						String nexttype = regJson.getString("nexttype");
						if (objper.getSeqId().equals(nexttype)) {
							lynums++;
						}
						if ("1".equals(cjstatus) && objper.getSeqId().equals(nexttype)) {
							cjnums++;
						}

						if ("0".equals(cjstatus) && objper.getSeqId().equals(nexttype)) {
							wcjnums++;
						}
					}

					// ------来源人次
					obj.put("lynums", lynums);
					// ---如果 来源人次为0，不展示
					if (lynums == 0) {
						continue;
					}
					// ------来源率占比% = 来源人次/总咨询来源人次
					Float lylzb = (float) 0;
					if (alllynums != 0) {
						lylzb = (float) lynums * 100 / alllynums;
					}
					obj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
					// ------其中成交人次
					obj.put("cjnums", cjnums);
					// ------成功率占比% = 来源成交人/来源人次
					Float cglzb = (float) 0;
					if (allcjlynums != 0) {
						cglzb = (float) cjnums * 100 / allcjlynums;
					}
					obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
					// ------其中未成交人次
					obj.put("wcjnums", wcjnums);
					// ------未成功率占比% = 来源未成交人/来源人次
					Float wcglzb = (float) 0;
					if (allwcjlynums != 0) {
						wcglzb = (float) wcjnums * 100 / allwcjlynums;
					}
					obj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
					// ------实收金额
					String ss = "0.00";
					ss = getssjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), objper.getSeqId());
					obj.put("ssje", ss);
					// ------收款金额
					String sk = "0.00";
					sk = getskjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), objper.getSeqId());
					obj.put("skje", sk);
					listAll.add(obj);
				}
				// ------------------合计 行（条件去除挂号分类）
				JSONObject objhj = new JSONObject();
				int lynums = 0; // ------来源人次
				int cjnums = 0; // ------其中成交人次
				int wcjnums = 0;// ------其中未成交人次

				for (JSONObject regJson : listRegAllTotal) {
					String cjstatus = regJson.getString("cjstatus");
					String devchannel = regJson.getString("devchannel");
					if (dict.getSeqId().equals(devchannel)) {
						lynums++;
					}
					if ("1".equals(cjstatus) && dict.getSeqId().equals(devchannel)) {
						cjnums++;
					}

					if ("0".equals(cjstatus) && dict.getSeqId().equals(devchannel)) {
						wcjnums++;
					}
				}
				// ------来源人次
				objhj.put("lynums", lynums);
				// ---如果 来源人次为0，不展示
				if (lynums == 0) {
					continue;
				}
				// ------来源率占比% = 来源人次/总咨询来源人次
				Float lylzb = (float) 0;
				if (alllynums != 0) {
					lylzb = (float) lynums * 100 / alllynums;
				}
				objhj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
				// ------其中成交人次
				objhj.put("cjnums", cjnums);
				// ------成功率% = 来源成交人次/来源人次
				Float cgl = (float) 0;
				if (lynums != 0) {
					cgl = (float) cjnums * 100 / lynums;
				}
				objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
				// ------成功率占比% = 来源成交人/来源人次
				Float cglzb = (float) 0;
				if (allcjlynums != 0) {
					cglzb = (float) cjnums * 100 / allcjlynums;
				}
				objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
				// ------其中未成交人次
				objhj.put("wcjnums", wcjnums);
				// ------未成功率占比% = 来源未成交人/来源人次
				Float wcglzb = (float) 0;
				if (allwcjlynums != 0) {
					wcglzb = (float) wcjnums * 100 / allwcjlynums;
				}
				objhj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
				// ------实收金额
				String ss = "0.00";
				ss = getssjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), "");
				objhj.put("ssje", ss);
				// ------收款金额
				String sk = "0.00";
				sk = getskjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), "");
				objhj.put("skje", sk);
				listAll.add(objhj);
			}
			// ------------------总计 行（条件去除挂号分类）
			JSONObject objzj = new JSONObject();
			int lynums = alllynums; // ------来源人次
			int cjnums = allcjlynums; // ------其中成交人次
			int wcjnums = allwcjlynums;// ------其中未成交人次
			// ------来源人次
			objzj.put("lynums", lynums);
			// ------来源率占比% = 来源人次/总咨询来源人次
			Float lylzb = (float) 0;
			if (alllynums != 0) {
				lylzb = (float) lynums * 100 / alllynums;
			}
			objzj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
			// ------其中成交人次
			objzj.put("cjnums", cjnums);
			// ------成功率% = 来源成交人次/来源人次
			Float cgl = (float) 0;
			if (lynums != 0) {
				cgl = (float) cjnums * 100 / lynums;
			}
			objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
			// ------成功率占比% = 来源成交人/来源人次
			Float cglzb = (float) 0;
			if (allcjlynums != 0) {
				cglzb = (float) cjnums * 100 / allcjlynums;
			}
			objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
			// ------其中未成交人次
			objzj.put("wcjnums", wcjnums);
			// ------未成功率占比% = 来源未成交人/来源人次
			Float wcglzb = (float) 0;
			if (allwcjlynums != 0) {
				wcglzb = (float) wcjnums * 100 / allwcjlynums;
			}
			objzj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
			// ------实收金额
			String ss = "0.00";
			ss = getssjeForHzly(skjeList, skjeYjjList, "", "");
			objzj.put("ssje", ss);
			// ------收款金额
			String sk = "0.00";
			sk = getskjeForHzly(skjeList, skjeYjjList, "", "");
			objzj.put("skje", sk);
			// ---如果 来源人次为0，不展示
			if (lynums > 0) {
				listAll.add(objzj);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 报表中心，患者来源统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountXxlyNum.act")
	public String selectCountXxlyNum(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String regsort = request.getParameter("regsort");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			} else {
				map.put("organization", organization);
			}
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);

			map.put("personIds4Query", visualstaff);

			// 查出所有挂号
			List<JSONObject> listRegAllTotal = logic.getCountHzly(map, visualstaff, "");
			// ------总来源人次
			int alllynums = listRegAllTotal.size();
			// ------总来源成交人次
			int allcjlynums = 0;
			// ------总来源未成交人次
			int allwcjlynums = 0;
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				if ("1".equals(cjstatus)) {
					allcjlynums++;
				} else {
					allwcjlynums++;
				}
			}
			// 获取初诊编码
			String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
			// 获取复诊编码
			String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
			// 获取再消费编码
			String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
			// 获取复查编码
			String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
			// 获取其他编码
			String qtseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);

			/** 营收数据列表 **/
			List<JSONObject> skjeList = null;
			List<JSONObject> skjeYjjList = null;
			skjeList = logic.getYysrList(map);
			skjeYjjList = logic.getYysrYjjList(map);

			List<YZDict> listDict = dictLogic.getListByParentCodeALL(DictUtil.HZLY, ChainUtil.getCurrentOrganization(request));
			List<JSONObject> listAll = new ArrayList<JSONObject>();
			// 遍历患者来源类型
			for (YZDict dict : listDict) {
				List<YZDict> listHzly = dictLogic.getListByParentCodeALL(dict.getDictCode(), organization);

				// 遍历患者来源
				for (int i = 0; i < listHzly.size(); i++) {
					YZDict objper = listHzly.get(i);
					JSONObject obj = new JSONObject();
					// ------患者来源类型
					obj.put("hzlyType", dict.getDictName());
					// ------患者来源
					obj.put("hzlychildname", objper.getDictName());

					int lynums = 0; // ------来源人次
					int cjnums = 0; // ------其中成交人次
					int wcjnums = 0;// ------其中未成交人次
					int czallnums = 0; // ------初诊总人次
					int czcjnums = 0; // ------初诊成交人次
					int fzallnums = 0; // ------复诊总人次
					int fzcjnums = 0;// ------复诊成交人次
					int zxfallnums = 0;// ------再消费总人次
					int zxfcjnums = 0;// ------再消费成交人次
					int fcallnums = 0;// ------复查总人次
					int fccjnums = 0;// ------复查成交人次
					int qtallnums = 0; // ------其他总人次
					int qtcjnums = 0; // ------其他成交人次

					for (JSONObject regJson : listRegAllTotal) {
						String cjstatus = regJson.getString("cjstatus");
						String nexttype = regJson.getString("nexttype");
						String recesort = regJson.getString("recesort");
						if (objper.getSeqId().equals(nexttype)) {
							lynums++;
						}
						if ("1".equals(cjstatus) && objper.getSeqId().equals(nexttype)) {
							cjnums++;
						}

						if ("0".equals(cjstatus) && objper.getSeqId().equals(nexttype)) {
							wcjnums++;
						}

						if (czseqId.equals(recesort) && objper.getSeqId().equals(nexttype)) {
							czallnums++;
						}
						if (czseqId.equals(recesort) && "1".equals(cjstatus) && objper.getSeqId().equals(nexttype)) {
							czcjnums++;
						}

						if (fzseqId.equals(recesort) && objper.getSeqId().equals(nexttype)) {
							fzallnums++;
						}
						if (fzseqId.equals(recesort) && "1".equals(cjstatus) && objper.getSeqId().equals(nexttype)) {
							fzcjnums++;
						}

						if (zxfseqId.equals(recesort) && objper.getSeqId().equals(nexttype)) {
							zxfallnums++;
						}
						if (zxfseqId.equals(recesort) && "1".equals(cjstatus) && objper.getSeqId().equals(nexttype)) {
							zxfcjnums++;
						}

						if (fcseqId.equals(recesort) && objper.getSeqId().equals(nexttype)) {
							fcallnums++;
						}
						if (fcseqId.equals(recesort) && "1".equals(cjstatus) && objper.getSeqId().equals(nexttype)) {
							fccjnums++;
						}
						if (qtseqId.equals(recesort) && objper.getSeqId().equals(nexttype)) {
							qtallnums++;
						}
						if (qtseqId.equals(recesort) && "1".equals(cjstatus) && objper.getSeqId().equals(nexttype)) {
							qtcjnums++;
						}
					}
					obj.put("lynums", lynums);
					// ---如果 来源人次为0，不展示
					if (lynums == 0) {
						continue;
					}
					// ------来源率占比% = 来源人次/总咨询来源人次
					Float lylzb = (float) 0;
					if (alllynums != 0) {
						lylzb = (float) lynums * 100 / alllynums;
					}
					obj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
					// ------其中成交人次
					obj.put("cjnums", cjnums);
					// ------成功率占比% = 来源成交人/来源人次
					Float cglzb = (float) 0;
					if (allcjlynums != 0) {
						cglzb = (float) cjnums * 100 / allcjlynums;
					}
					obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
					// ------其中未成交人次
					obj.put("wcjnums", wcjnums);
					// ------未成功率占比% = 来源未成交人/来源人次
					Float wcglzb = (float) 0;
					if (allwcjlynums != 0) {
						wcglzb = (float) wcjnums * 100 / allwcjlynums;
					}
					obj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");

					// ------初诊总人次
					obj.put("czallnums", czallnums);
					// ------初诊成交人次
					obj.put("czcjnums", czcjnums);
					// ------复诊总人次
					obj.put("fzallnums", fzallnums);
					// ------复诊成交人次
					obj.put("fzcjnums", fzcjnums);
					// ------再消费总人次
					obj.put("zxfallnums", zxfallnums);
					// ------再消费成交人次
					obj.put("zxfcjnums", zxfcjnums);
					// ------复查总人次
					obj.put("fcallnums", fcallnums);
					// ------复查成交人次
					obj.put("fccjnums", fccjnums);
					// ------其他总人次
					obj.put("qtallnums", qtallnums);
					// ------其他成交人次
					obj.put("qtcjnums", qtcjnums);
					// ------实收金额
					String ss = "0.00";
					ss = getssjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), objper.getSeqId());
					obj.put("ssje", ss);
					// ------收款金额
					String sk = "0.00";
					sk = getskjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), objper.getSeqId());
					obj.put("skje", sk);
					listAll.add(obj);
				}
				// ------------------合计 行（条件去除挂号分类）
				JSONObject objhj = new JSONObject();
				int lynums = 0; // ------来源人次
				int cjnums = 0; // ------其中成交人次
				int wcjnums = 0;// ------其中未成交人次
				int czallnums = 0; // ------初诊总人次
				int czcjnums = 0; // ------初诊成交人次
				int fzallnums = 0; // ------复诊总人次
				int fzcjnums = 0;// ------复诊成交人次
				int zxfallnums = 0;// ------再消费总人次
				int zxfcjnums = 0;// ------再消费成交人次
				int fcallnums = 0;// ------复查总人次
				int fccjnums = 0;// ------复查成交人次
				int qtallnums = 0; // ------其他总人次
				int qtcjnums = 0; // ------其他成交人次

				for (JSONObject regJson : listRegAllTotal) {
					String cjstatus = regJson.getString("cjstatus");
					String devchannel = regJson.getString("devchannel");
					String recesort = regJson.getString("recesort");
					if (dict.getSeqId().equals(devchannel)) {
						lynums++;
					}
					if ("1".equals(cjstatus) && dict.getSeqId().equals(devchannel)) {
						cjnums++;
					}

					if ("0".equals(cjstatus) && dict.getSeqId().equals(devchannel)) {
						wcjnums++;
					}

					if (czseqId.equals(recesort) && dict.getSeqId().equals(devchannel)) {
						czallnums++;
					}
					if (czseqId.equals(recesort) && "1".equals(cjstatus) && dict.getSeqId().equals(devchannel)) {
						czcjnums++;
					}

					if (fzseqId.equals(recesort) && dict.getSeqId().equals(devchannel)) {
						fzallnums++;
					}
					if (fzseqId.equals(recesort) && "1".equals(cjstatus) && dict.getSeqId().equals(devchannel)) {
						fzcjnums++;
					}

					if (zxfseqId.equals(recesort) && dict.getSeqId().equals(devchannel)) {
						zxfallnums++;
					}
					if (zxfseqId.equals(recesort) && "1".equals(cjstatus) && dict.getSeqId().equals(devchannel)) {
						zxfcjnums++;
					}

					if (fcseqId.equals(recesort) && dict.getSeqId().equals(devchannel)) {
						fcallnums++;
					}
					if (fcseqId.equals(recesort) && "1".equals(cjstatus) && dict.getSeqId().equals(devchannel)) {
						fccjnums++;
					}
					if (qtseqId.equals(recesort) && dict.getSeqId().equals(devchannel)) {
						qtallnums++;
					}
					if (qtseqId.equals(recesort) && "1".equals(cjstatus) && dict.getSeqId().equals(devchannel)) {
						qtcjnums++;
					}
				}
				// ------来源人次
				objhj.put("lynums", lynums);
				// ---如果 来源人次为0，不展示
				if (lynums == 0) {
					continue;
				}
				// ------来源率占比% = 来源人次/总咨询来源人次
				Float lylzb = (float) 0;
				if (alllynums != 0) {
					lylzb = (float) lynums * 100 / alllynums;
				}
				objhj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
				// ------其中成交人次
				objhj.put("cjnums", cjnums);
				// ------成功率% = 来源成交人次/来源人次
				Float cgl = (float) 0;
				if (lynums != 0) {
					cgl = (float) cjnums * 100 / lynums;
				}
				objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
				// ------成功率占比% = 来源成交人/来源人次
				Float cglzb = (float) 0;
				if (allcjlynums != 0) {
					cglzb = (float) cjnums * 100 / allcjlynums;
				}
				objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
				// ------其中未成交人次
				objhj.put("wcjnums", wcjnums);
				// ------未成功率占比% = 来源未成交人/来源人次
				Float wcglzb = (float) 0;
				if (allwcjlynums != 0) {
					wcglzb = (float) wcjnums * 100 / allwcjlynums;
				}
				objhj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");

				// ------初诊总人次
				objhj.put("czallnums", czallnums);
				// ------初诊成交人次
				objhj.put("czcjnums", czcjnums);
				// ------复诊总人次
				objhj.put("fzallnums", fzallnums);
				// ------复诊成交人次
				objhj.put("fzcjnums", fzcjnums);
				// ------再消费总人次
				objhj.put("zxfallnums", zxfallnums);
				// ------再消费成交人次
				objhj.put("zxfcjnums", zxfcjnums);
				// ------复查总人次
				objhj.put("fcallnums", fcallnums);
				// ------复查成交人次
				objhj.put("fccjnums", fccjnums);
				// ------其他总人次
				objhj.put("qtallnums", qtallnums);
				// ------其他成交人次
				objhj.put("qtcjnums", qtcjnums);
				// ------实收金额
				String ss = "0.00";
				ss = getssjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), "");
				objhj.put("ssje", ss);
				// ------收款金额
				String sk = "0.00";
				sk = getskjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), "");
				objhj.put("skje", sk);
				listAll.add(objhj);
			}
			// ------------------总计 行（条件去除挂号分类）
			JSONObject objzj = new JSONObject();
			int lynums = alllynums; // ------来源人次
			int cjnums = allcjlynums; // ------其中成交人次
			int wcjnums = allwcjlynums;// ------其中未成交人次
			int czallnums = 0; // ------初诊总人次
			int czcjnums = 0; // ------初诊成交人次
			int fzallnums = 0; // ------复诊总人次
			int fzcjnums = 0;// ------复诊成交人次
			int zxfallnums = 0;// ------再消费总人次
			int zxfcjnums = 0;// ------再消费成交人次
			int fcallnums = 0;// ------复查总人次
			int fccjnums = 0;// ------复查成交人次
			int qtallnums = 0; // ------其他总人次
			int qtcjnums = 0; // ------其他成交人次

			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				String recesort = regJson.getString("recesort");
				if (czseqId.equals(recesort)) {
					czallnums++;
				}
				if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
					czcjnums++;
				}

				if (fzseqId.equals(recesort)) {
					fzallnums++;
				}
				if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
					fzcjnums++;
				}

				if (zxfseqId.equals(recesort)) {
					zxfallnums++;
				}
				if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
					zxfcjnums++;
				}

				if (fcseqId.equals(recesort)) {
					fcallnums++;
				}
				if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
					fccjnums++;
				}
				if (qtseqId.equals(recesort)) {
					qtallnums++;
				}
				if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
					qtcjnums++;
				}
			}
			// ------来源人次
			objzj.put("lynums", lynums);
			// ------来源率占比% = 来源人次/总咨询来源人次
			Float lylzb = (float) 0;
			if (alllynums != 0) {
				lylzb = (float) lynums * 100 / alllynums;
			}
			objzj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
			// ------其中成交人次
			objzj.put("cjnums", cjnums);
			// ------成功率% = 来源成交人次/来源人次
			Float cgl = (float) 0;
			if (lynums != 0) {
				cgl = (float) cjnums * 100 / lynums;
			}
			objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
			// ------成功率占比% = 来源成交人/来源人次
			Float cglzb = (float) 0;
			if (allcjlynums != 0) {
				cglzb = (float) cjnums * 100 / allcjlynums;
			}
			objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
			// ------其中未成交人次
			objzj.put("wcjnums", wcjnums);
			// ------未成功率占比% = 来源未成交人/来源人次
			Float wcglzb = (float) 0;
			if (allwcjlynums != 0) {
				wcglzb = (float) wcjnums * 100 / allwcjlynums;
			}
			objzj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");

			// ------初诊总人次
			objzj.put("czallnums", czallnums);
			// ------初诊成交人次
			objzj.put("czcjnums", czcjnums);
			// ------复诊总人次
			objzj.put("fzallnums", fzallnums);
			// ------复诊成交人次
			objzj.put("fzcjnums", fzcjnums);
			// ------再消费总人次
			objzj.put("zxfallnums", zxfallnums);
			// ------再消费成交人次
			objzj.put("zxfcjnums", zxfcjnums);
			// ------复查总人次
			objzj.put("fcallnums", fcallnums);
			// ------复查成交人次
			objzj.put("fccjnums", fccjnums);
			// ------其他总人次
			objzj.put("qtallnums", qtallnums);
			// ------其他成交人次
			objzj.put("qtcjnums", qtcjnums);
			// ------实收金额
			String ss = "0.00";
			ss = getssjeForHzly(skjeList, skjeYjjList, "", "");
			objzj.put("ssje", ss);
			// ------收款金额
			String sk = "0.00";
			sk = getskjeForHzly(skjeList, skjeYjjList, "", "");
			objzj.put("skje", sk);
			// ---如果 来源人次为0，不展示
			if (lynums > 0) {
				listAll.add(objzj);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 1.咨询项目分析表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountSort.act")
	public String selectCountSort(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
				map.put("organization", organization);
			} else {
				map.put("organization", organization);
			}
			// 可见人员过滤
			List<JSONObject> listAll = new ArrayList<JSONObject>();

			// 查出所有挂号
			List<JSONObject> listRegAllTotal = logic.getListRegByGhfl(map, "", "");
			// ------总咨询人数（所有咨询）
			int allnums = listRegAllTotal.size();
			// ------总咨询成交人数（所有咨询）
			int allcjnums = 0;
			// ------总咨询未成交人数（所有咨询）
			int allwcjlynums = 0;
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				if ("1".equals(cjstatus)) {
					allcjnums++;
				} else {
					allwcjlynums++;
				}
			}
			/** 营收数据列表 **/
			List<JSONObject> skjeList = null;
			List<JSONObject> skjeYjjList = null;
			skjeList = logic.getYysrList(map);
			skjeYjjList = logic.getYysrYjjList(map);

			// 获取初诊编码
			String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
			// 获取复诊编码
			String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
			// 获取再消费编码
			String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
			// 获取复查编码
			String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
			// 获取其他编码
			String qtseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);

			// 遍历咨询项目
			List<YZDict> listDict = dictLogic.getListByParentCodeALL("GHFL", ChainUtil.getCurrentOrganization(request));
			for (YZDict dict : listDict) {
				JSONObject obj = new JSONObject();
				// ------咨询项目/挂号分类
				obj.put("zxxm", dict.getDictName());

				int zxnums = 0; // ------咨询人次
				int cjnums = 0; // ------其中成交人次
				int wcjnums = 0;// ------其中未成交人次
				int czallnums = 0; // ------初诊总人次
				int czcjnums = 0; // ------初诊成交人次
				int fzallnums = 0; // ------复诊总人次
				int fzcjnums = 0;// ------复诊成交人次
				int zxfallnums = 0;// ------再消费总人次
				int zxfcjnums = 0;// ------再消费成交人次
				int fcallnums = 0;// ------复查总人次
				int fccjnums = 0;// ------复查成交人次
				int qtallnums = 0; // ------其他总人次
				int qtcjnums = 0; // ------其他成交人次
				for (JSONObject regJson : listRegAllTotal) {
					String cjstatus = regJson.getString("cjstatus");
					String recesort = regJson.getString("recesort");
					String regsort = regJson.getString("regsort");
					if (regsort.equals(dict.getSeqId())) {
						zxnums++;
					}
					if ("1".equals(cjstatus) && regsort.equals(dict.getSeqId())) {
						cjnums++;
					}

					if ("0".equals(cjstatus) && regsort.equals(dict.getSeqId())) {
						wcjnums++;
					}

					if (czseqId.equals(recesort) && regsort.equals(dict.getSeqId())) {
						czallnums++;
					}
					if (czseqId.equals(recesort) && "1".equals(cjstatus) && regsort.equals(dict.getSeqId())) {
						czcjnums++;
					}

					if (fzseqId.equals(recesort) && regsort.equals(dict.getSeqId())) {
						fzallnums++;
					}
					if (fzseqId.equals(recesort) && "1".equals(cjstatus) && regsort.equals(dict.getSeqId())) {
						fzcjnums++;
					}

					if (zxfseqId.equals(recesort) && regsort.equals(dict.getSeqId())) {
						zxfallnums++;
					}
					if (zxfseqId.equals(recesort) && "1".equals(cjstatus) && regsort.equals(dict.getSeqId())) {
						zxfcjnums++;
					}

					if (fcseqId.equals(recesort) && regsort.equals(dict.getSeqId())) {
						fcallnums++;
					}
					if (fcseqId.equals(recesort) && "1".equals(cjstatus) && regsort.equals(dict.getSeqId())) {
						fccjnums++;
					}
					if (qtseqId.equals(recesort) && regsort.equals(dict.getSeqId())) {
						qtallnums++;
					}
					if (qtseqId.equals(recesort) && "1".equals(cjstatus) && regsort.equals(dict.getSeqId())) {
						qtcjnums++;
					}
				}

				// ------咨询人次
				obj.put("zxnums", zxnums);
				// ---如果 咨询人次为0，不展示
				if (zxnums == 0) {
					continue;
				}
				// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
				Float zzlzb = (float) 0;
				if (allnums != 0) {
					zzlzb = (float) zxnums * 100 / allnums;
				}
				obj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
				// ------其中成交人次
				obj.put("cjnums", cjnums);
				// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
				Float cgl = (float) 0;
				if (zxnums != 0) {
					cgl = (float) cjnums * 100 / zxnums;
				}
				obj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
				// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
				Float cglzb = (float) 0;
				if (allcjnums != 0) {
					cglzb = (float) cjnums * 100 / allcjnums;
				}
				obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
				// ------其中未成交人次
				obj.put("wcjnums", wcjnums);
				// ------未成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
				Float wcglzb = (float) 0;
				if (allwcjlynums != 0) {
					wcglzb = (float) wcjnums * 100 / allwcjlynums;
				}
				obj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
				// ------初诊总人次
				obj.put("czallnums", czallnums);
				// ------初诊成交人次
				obj.put("czcjnums", czcjnums);
				// ------复诊总人次
				obj.put("fzallnums", fzallnums);
				obj.put("fzcjnums", fzcjnums);
				// ------再消费总人次
				obj.put("zxfallnums", zxfallnums);
				// ------再消费成交人次
				obj.put("zxfcjnums", zxfcjnums);
				// ------复查总人次
				obj.put("fcallnums", fcallnums);
				// ------复查成交人次
				obj.put("fccjnums", fccjnums);
				// ------其他总人次
				obj.put("qtallnums", qtallnums);
				// ------其他成交人次
				obj.put("qtcjnums", qtcjnums);
				// ------收款金额
				String sk = "0.00";
				sk = getskje4AskPerson(skjeList, skjeYjjList, null, dict.getSeqId());
				obj.put("skje", sk);
				String ss = getskje4AskPersonSS(skjeList, skjeYjjList, null, dict.getSeqId());
				obj.put("ssje", ss);
				listAll.add(obj);
			}
			// ------------------合计 行（条件去除挂号分类）
			JSONObject objhj = new JSONObject();
			int zxnums = allnums; // ------咨询人次
			int cjnums = allcjnums; // ------其中成交人次
			int wcjnums = allwcjlynums;// ------其中未成交人次
			int czallnums = 0; // ------初诊总人次
			int czcjnums = 0; // ------初诊成交人次
			int fzallnums = 0; // ------复诊总人次
			int fzcjnums = 0;// ------复诊成交人次
			int zxfallnums = 0;// ------再消费总人次
			int zxfcjnums = 0;// ------再消费成交人次
			int fcallnums = 0;// ------复查总人次
			int fccjnums = 0;// ------复查成交人次
			int qtallnums = 0; // ------其他总人次
			int qtcjnums = 0; // ------其他成交人次
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				String recesort = regJson.getString("recesort");

				if (czseqId.equals(recesort)) {
					czallnums++;
				}
				if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
					czcjnums++;
				}

				if (fzseqId.equals(recesort)) {
					fzallnums++;
				}
				if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
					fzcjnums++;
				}

				if (zxfseqId.equals(recesort)) {
					zxfallnums++;
				}
				if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
					zxfcjnums++;
				}

				if (fcseqId.equals(recesort)) {
					fcallnums++;
				}
				if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
					fccjnums++;
				}
				if (qtseqId.equals(recesort)) {
					qtallnums++;
				}
				if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
					qtcjnums++;
				}
			}
			// ------咨询人次
			objhj.put("zxnums", zxnums);
			// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
			Float zzlzb = (float) 0;
			if (allnums != 0) {
				zzlzb = (float) zxnums * 100 / allnums;
			}
			objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
			// ------其中成交人次
			objhj.put("cjnums", cjnums);
			// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
			Float cgl = (float) 0;
			if (zxnums != 0) {
				cgl = (float) cjnums * 100 / zxnums;
			}
			objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
			// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
			Float cglzb = (float) 0;
			if (allcjnums != 0) {
				cglzb = (float) cjnums * 100 / allcjnums;
			}
			objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
			// ------其中未成交人次
			objhj.put("wcjnums", wcjnums);
			// ------未成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
			Float wcglzb = (float) 0;
			if (allwcjlynums != 0) {
				wcglzb = (float) wcjnums * 100 / allwcjlynums;
			}
			objhj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
			// ------初诊总人次
			objhj.put("czallnums", czallnums);
			// ------初诊成交人次
			objhj.put("czcjnums", czcjnums);
			// ------复诊总人次
			objhj.put("fzallnums", fzallnums);
			// ------复诊成交人次
			objhj.put("fzcjnums", fzcjnums);
			// ------再消费总人次
			objhj.put("zxfallnums", zxfallnums);
			// ------再消费成交人次
			objhj.put("zxfcjnums", zxfcjnums);
			// ------复查总人次
			objhj.put("fcallnums", fcallnums);
			// ------复查成交人次
			objhj.put("fccjnums", fccjnums);
			// ------其他总人次
			objhj.put("qtallnums", qtallnums);
			// ------其他成交人次
			objhj.put("qtcjnums", qtcjnums);
			// ------收款金额
			String sk = "0.00";
			sk = getskje4AskPerson(skjeList, skjeYjjList, null, null);
			objhj.put("skje", sk);
			String ss = getskje4AskPersonSS(skjeList, skjeYjjList, null, null);
			objhj.put("ssje", ss);
			listAll.add(objhj);
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	@RequestMapping(value = "/getOrdertjDept.act")
	public String getOrdertjDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String isyx = request.getParameter("isyx"); // 1 是营销 2是网电 3客服
			String shouli=request.getParameter("shouli");
			YZPerson person = SessionUtil.getLoginPerson(request);
			String depttype = "";// 2 网电 3 营销 6客服，这里不能写成 String depttype = null;
			// 因为后面的sql会使用这个变量值
			String deptId = null;
			String deptname = null;
			if ("1".equals(isyx)) {// 营销查询
				depttype = ConstUtil.DEPT_TYPE_3;
			} else if ("2".equals(isyx)) {// 网电查询
				depttype = ConstUtil.DEPT_TYPE_2;
			} else if ("3".equals(isyx)) {// 客服查询
				depttype = ConstUtil.DEPT_TYPE_6;
			}
			List<JSONObject> list=new ArrayList<>();
			//读取登录人的登录信息
			YZPriv priv = privLogic.findGeneral(person.getUserPriv());
			if(priv.getAuthority() != null && !priv.getAuthority().equals("")){
				if(priv.getAuthority().equals("2")){
				}else if(priv.getAuthority().equals("3")){
					shouli = "zz373";
					YZDept department = deptLogic.findmarketing("2",depttype,shouli);
					deptId = department.getSeqId();
					deptname=department.getDeptName();
				}else if(priv.getAuthority().equals("4")){
					shouli = "zj634";
					YZDept department = deptLogic.findmarketing("2",depttype,shouli);
					deptId = department.getSeqId();
					deptname=department.getDeptName();
				}
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("deptid",deptId);
				jsonObject.put("deptname",deptname);
				list.add(jsonObject);
			}else{
				//获取业务部门
				List<JSONObject> dept = deptLogic.findMarket("2");
				//登录人是否所属业务部门之中
				//如果属于业务部门将业务部门ID赋予一个map中
				for (JSONObject json : dept) {
					if(!json.get("seqId").equals(person.getDeptId())){
						if(shouli!=null && !shouli.equals("")){
							YZDept department = deptLogic.findmarketing("2",depttype,shouli);
							deptId = department.getSeqId();
							deptname=department.getDeptName();
						}else{
								if(priv.getAuthority() != null && priv.getAuthority().equals("5")){
									shouli = "service";
									YZDept department = deptLogic.findmarketing("2",depttype,shouli);
									deptId = department.getSeqId();
									deptname=department.getDeptName();
								}else{
									shouli = "zz373";
									YZDept department = deptLogic.findmarketing("2",depttype,shouli);
									deptId = department.getSeqId();
									deptname=department.getDeptName();
								}
						}
					}else{
						deptId = person.getDeptId();
						deptname=person.getUserName();
					}
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("deptid",deptId);
					jsonObject.put("deptname",deptname);
					list.add(jsonObject);
				}
			}
			list = list.stream().distinct().collect(Collectors.toList());
			YZUtility.RETURN_LIST(list,response,logger);
		}catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}


	/**
	 * 2019-08-15 lwg 
	 * 网电中心/营销中心的工作量统计->咨询统计表Tab
	 * 
	 */
	@RequestMapping(value = "/getWdOrdertj.act")
	public String getWdOrdertj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//根据人员信息查询出部门id，根据部门id查询对应的数据信息
			YZPerson person = SessionUtil.getLoginPerson(request);
			String jdtime1=request.getParameter("jdtime1");
			String jdtime2=request.getParameter("jdtime2");
			String yytime1=request.getParameter("yytime1");
			String yytime2=request.getParameter("yytime2");
			String dztime1=request.getParameter("dztime1");
			String dztime2=request.getParameter("dztime2");
			String yewu =request.getParameter("yewu");
			String yewuname =request.getParameter("yewuname");
			String xiangmu=request.getParameter("xiangmu");
			String shouli=request.getParameter("shouli");
			String devchannel=request.getParameter("devchannel");
			String isyx = request.getParameter("isyx"); // 1 是营销 2是网电 3客服
			String nexttype = request.getParameter("nexttype");
			String gongju = request.getParameter("gongju");
			String deptid = request.getParameter("deptid");
			String personid = request.getParameter("personid");
			String personname = request.getParameter("personname");
			
			// 导出参数 
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();
			String depttype = "";// 2 网电 3 营销 6客服，这里不能写成 String depttype = null;
									// 因为后面的sql会使用这个变量值
			if (!YZUtility.isNullorEmpty(jdtime1)) {
				jdtime1 = jdtime1 + ConstUtil.TIME_START;
				map.put("jdtime1", jdtime1);
			}
			if (!YZUtility.isNullorEmpty(jdtime2)) {
				jdtime2 = jdtime2 + ConstUtil.TIME_END;
				map.put("jdtime2", jdtime2);
			}
			if (!YZUtility.isNullorEmpty(yytime1)) {
				yytime1 = yytime1 + ConstUtil.TIME_START;
				map.put("yytime1", yytime1);
			}
			if (!YZUtility.isNullorEmpty(yytime2)) {
				yytime2 = yytime2 + ConstUtil.TIME_END;
				map.put("yytime2", yytime2);
			}
			if (!YZUtility.isNullorEmpty(dztime1)) {
				dztime1 = dztime1 + ConstUtil.TIME_START;
				map.put("dztime1", dztime1);
			}
			if (!YZUtility.isNullorEmpty(dztime2)) {
				dztime2 = dztime2 + ConstUtil.TIME_END;
				map.put("dztime2", dztime2);
			}
			if (!YZUtility.isNullorEmpty(yewu)){
				map.put("yewu", yewu);
			}
			if (!YZUtility.isNullorEmpty(xiangmu)){
				map.put("askitem", xiangmu);
			}
			if (!YZUtility.isNullorEmpty(devchannel)){
				map.put("devchannel", devchannel);		
			}
			if(!YZUtility.isNullorEmpty(shouli)){
				map.put("shouli", shouli);
			}
			if(!YZUtility.isNullorEmpty(nexttype)){
				map.put("nexttype", nexttype);
			}
			if(!YZUtility.isNullorEmpty(gongju)){
				map.put("accepttool", gongju);
			}
			if ("1".equals(isyx)) {// 营销查询
				depttype = ConstUtil.DEPT_TYPE_3;
			} else if ("2".equals(isyx)) {// 网电查询
				depttype = ConstUtil.DEPT_TYPE_2;
			} else if ("3".equals(isyx)) {// 客服查询
				depttype = ConstUtil.DEPT_TYPE_6;
			}

//			//读取登录人的登录信息
//			YZPriv priv = privLogic.findGeneral(person.getUserPriv());
//			if(priv.getAuthority() != null && !priv.getAuthority().equals("")){
//				String dept = null;
//				if(priv.getAuthority().equals("2")){
//					listPerson = personLogic.findPersonalDetails(person.getUserId());
//				}else if(priv.getAuthority().equals("3")){
//					shouli = "zz373";
//					YZDept department = deptLogic.findmarketing("2",depttype,shouli);
//					dept = department.getSeqId();
//					listPerson = personLogic.findVisualPersonnel(dept);
//				}else if(priv.getAuthority().equals("4")){
//					shouli = "zj634";
//					YZDept department = deptLogic.findmarketing("2",depttype,shouli);
//					dept = department.getSeqId();
//					listPerson = personLogic.findVisualPersonnel(dept);
//				}
//			}else{
//				//获取业务部门
//				List<JSONObject> dept = deptLogic.findMarket("2");
//
//				 String deptId = null;
//				//登录人是否所属业务部门之中
//				//如果属于业务部门将业务部门ID赋予一个map中
//				for (JSONObject json : dept) {
//					if(!json.get("seqId").equals(person.getDeptId())){
//						if(shouli!=null && !shouli.equals("")){
//						YZDept department = deptLogic.findmarketing("2",depttype,shouli);
//						deptId = department.getSeqId();
//						}else{
//								if(priv.getAuthority() != null && priv.getAuthority().equals("5")){
//									shouli = "service";
//									YZDept department = deptLogic.findmarketing("2",depttype,shouli);
//									deptId = department.getSeqId();
//								}else{
//									shouli = "zz373";
//									YZDept department = deptLogic.findmarketing("2",depttype,shouli);
//									deptId = department.getSeqId();
//								}
//						}
//					}else{
//						deptId = person.getDeptId();
//					}
//				}
//
//				// 可见人员过滤
//				//String visualstaff = SessionUtil.getVisualstaff(request);
//				// 网电人员
//				//List<JSONObject> listPerson = personLogic.getPersonListByDeptTypeAndVisual(deptId, visualstaff, ChainUtil.getCurrentOrganization(request));
//				 listPerson = personLogic.findVisualPersonnel(deptId);
//			}
			//定义可见人员集合
			List<JSONObject> listPerson=new ArrayList<>();
			if(!YZUtility.isNullorEmpty(personid)){
				map.put("visualstaff", "\'"+personid+"\'");
				JSONObject objper = new JSONObject();
				objper.put("seqId",personid);
				objper.put("userName",personname);
				listPerson.add(objper);
			}else if(map.get("yewu")!=null){
				map.put("visualstaff", "\'"+map.get("yewu")+"\'");
				JSONObject objper = new JSONObject();
				objper.put("seqId",map.get("yewu"));
				objper.put("userName",yewuname);
				listPerson.add(objper);
			} else{
				listPerson = personLogic.findVisualPersonnel(deptid);
				String personIds = personLogic.getPerIdsByPersonList(listPerson);
				map.put("visualstaff", personIds);
			}
			// 门诊编号，从页面下拉框传值过来
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			/** 门诊编号可以为空 **/
			map.put("organization", organization);
			List<JSONObject> list = new ArrayList<JSONObject>();
			if(jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&dztime1.equals("")&&dztime2.equals("")){
				String date = YZUtility.getDateStr(new Date());
				jdtime1 = date + ConstUtil.TIME_START;
				jdtime2 = date + ConstUtil.TIME_END;
				map.put("jdtime1", jdtime1);
				map.put("jdtime2", jdtime2);
				// 录单量 
				List<JSONObject> jdList = logic.getCountJdList(map);
				map.put("jdtime1", null);
				map.put("jdtime2", null);
				map.put("yytime1", jdtime1);
				map.put("yytime2", jdtime2);
				// 预约人数
				List<JSONObject> yynumsList = logic.getCountYyList(map);
				map.put("yytime1", null);
				map.put("yytime2", null);
				map.put("jdtime1", jdtime1);
				map.put("jdtime2", jdtime2);
				map.put("dztime1", jdtime1);
				map.put("dztime2", jdtime2);
				// 到诊量
				List<JSONObject> doorstatusList = logic.getCountDoorstatus(map);
				// 成交人数
				//List<JSONObject> yycjnumsList = logic.getCountCjList(map);
				List<JSONObject> skjeList = logic.getYysrListNoOrg(map); // 消费收入
				List<JSONObject> skjeYjjList = logic.getYysrYjjListNoOrg(map); // 预交金充值收入
				if(map.get("yewu")!=null){
					JSONObject obj = new JSONObject();
					for (int i = 0; i < listPerson.size(); i++) {
						JSONObject objper = listPerson.get(i);
						if(objper.getString("seqId").equals(yewu)){
							obj.put("username", objper.getString("userName"));
						}
					}
					obj.put("xh", 1);
					int ldnums = jdList.get(0).getInt("num");
					int yynums = yynumsList.get(0).getInt("num");
					int yysmnums = doorstatusList.get(0).getInt("num");
					Float yyl = (float) 0; // 预约率 = 预约人数/录单量
					if (ldnums != 0) {
						yyl = (float) yynums * 100 / ldnums;
					}
					
					Float dzl = (float) 0; // 到诊率 = 到院人数/录单量
					if (ldnums != 0) {
						dzl = (float) yysmnums * 100 / ldnums;
					}
					
					obj.put("ldnums", ldnums); // 录单量
					obj.put("yynums", yynums); // 预约人数
					obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
					obj.put("yysmnums", yysmnums); // 到院人数
					obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
					// 收款金额 = 项目收款 + 预交金收款
					// 项目收款 = 实收 - 预交金
					// 预交金收款 =
					String sk = ""+new BigDecimal(skjeList.get(0).get("paymoney")+"").add(new BigDecimal(skjeYjjList.get(0).get("cmoney")+""));
					obj.put("skje", new BigDecimal(skjeList.get(0).get("paymoney")+"").add(new BigDecimal(skjeYjjList.get(0).get("cmoney")+"")));
					Float rjxf = (float) 0; // 人均消费 = 收款金额/到院人数
					if (yysmnums != 0) {
						rjxf = (float) Float.parseFloat(sk) / yysmnums;
					}
					obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
					obj.put("xmje", skjeList.get(0).get("paymoney"));
					obj.put("ysje", skjeYjjList.get(0).get("cmoney"));
					list.add(obj);
				}else{
					for (int i = 0; i < listPerson.size(); i++) {
						JSONObject objper = listPerson.get(i);
						JSONObject obj = new JSONObject();
						obj.put("xh", i + 1);
						obj.put("username", objper.getString("userName"));
						
						int ldnums = 0;
						int yynums = 0;
						int yysmnums = 0;
						String sk = ""+new BigDecimal(skjeList.get(i).get("paymoney")+"").add(new BigDecimal(skjeYjjList.get(i).get("cmoney")+""));
						for (int j = 0; j <listPerson.size(); j++) {
							if(jdList.get(j).get("seqId").equals(objper.getString("seqId"))){
								ldnums=jdList.get(j).getInt("num");
							}
						}
						for (int j = 0; j <listPerson.size(); j++) {
							if(yynumsList.get(j).get("seqId").equals(objper.getString("seqId"))){
								yynums=yynumsList.get(j).getInt("num");
							}
						}
						for (int j = 0; j <listPerson.size(); j++) {
							if(doorstatusList.get(j).get("seqId").equals(objper.getString("seqId"))){
								yysmnums=doorstatusList.get(j).getInt("num");
							}
						}
						for (int j = 0; j <listPerson.size(); j++) {
							if(skjeList.get(j).get("seqId").equals(objper.getString("seqId"))){
								sk=""+new BigDecimal(skjeList.get(j).get("paymoney")+"");
								obj.put("xmje", skjeList.get(j).get("paymoney"));
							}
						}
						for (int j = 0; j <listPerson.size(); j++) {
							if(skjeYjjList.get(j).get("seqId").equals(objper.getString("seqId"))){
								sk=""+new BigDecimal(sk).add(new BigDecimal(skjeYjjList.get(j).get("cmoney")+""));
								obj.put("ysje", skjeYjjList.get(j).get("cmoney"));
							}
						}
						Float yyl = (float) 0; // 预约率 = 预约人数/录单量
						if (ldnums != 0) {
							yyl = (float) yynums * 100 / ldnums;
						}
						
						Float dzl = (float) 0; // 到诊率 = 到院人数/录单量
						if (ldnums != 0) {
							dzl = (float) yysmnums * 100 / ldnums;
						}
						
						obj.put("ldnums", ldnums); // 录单量
						obj.put("yynums", yynums); // 预约人数
						obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
						obj.put("yysmnums", yysmnums); // 到院人数
						obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
						// 收款金额 = 项目收款 + 预交金收款
						// 项目收款 = 实收 - 预交金
						// 预交金收款 =
						obj.put("skje", sk);
						Float rjxf = (float) 0; // 人均消费 = 收款金额/到院人数
						if (yysmnums != 0) {
							rjxf = (float) Float.parseFloat(sk) / yysmnums;
						}
						obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
						
						list.add(obj);
					}
				}
			}else{
				int ldnums =0;
				int yynums =0;
				List<JSONObject> jdList=null;
				List<JSONObject> yynumsList =null;
				if(jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&!dztime1.equals("")&&!dztime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&!dztime1.equals("")&&dztime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&dztime1.equals("")&&!dztime2.equals("")){
					ldnums=0;
					yynums =0;
				}else if(jdtime1.equals("")&&jdtime2.equals("")&&!yytime1.equals("")&&!yytime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&!yytime1.equals("")&&yytime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&!yytime2.equals("")){
					ldnums=0;
					yynumsList = logic.getCountYyList(map);
					yynums = yynumsList.get(0).getInt("num");
				}else{
					// 录单量 
					jdList = logic.getCountJdList(map);
					// 预约人数
					yynumsList = logic.getCountYyList(map);
					ldnums = jdList.get(0).getInt("num");
					yynums = yynumsList.get(0).getInt("num");
					
				}
				// 到诊量
				List<JSONObject> doorstatusList = logic.getCountDoorstatus(map);
				// 成交人数
				//List<JSONObject> yycjnumsList = logic.getCountCjList(map);
				List<JSONObject> skjeList = logic.getYysrListNoOrg(map); // 消费收入
				List<JSONObject> skjeYjjList = logic.getYysrYjjListNoOrg(map); // 预交金充值收入
				if(map.get("yewu")!=null){
					JSONObject obj = new JSONObject();
					for (int i = 0; i < listPerson.size(); i++) {
						JSONObject objper = listPerson.get(i);
						if(objper.getString("seqId").equals(yewu)){
							obj.put("username", objper.getString("userName"));
						}
					}
					obj.put("xh", 1);
					
					int yysmnums = doorstatusList.get(0).getInt("num");
//			for (int j = 0; j < yynumsList.size(); j++) {
//				JSONObject jobj = yynumsList.get(j);
//				if (jobj.getString("createuser").equals(perid)) { // 预约人数累加
//					yynums++;
//				}
//			}
//
//			// 上门人数
//			for (int j = 0; j < doorstatusList.size(); j++) {
//				JSONObject jobj = doorstatusList.get(j);
//				if (jobj.getString("createuser").equals(perid)) { // 到院人数累加
//					yysmnums++;
//				}
//			}
					
//			int cjnums = 0;
//			for (int j = 0; j < yycjnumsList.size(); j++) {
//				JSONObject jobj = yycjnumsList.get(j);
//				if (jobj.getString("createuser").equals(perid)) { // 成交人数累加
//					cjnums++;
//				}
//			}
					
					Float yyl = (float) 0; // 预约率 = 预约人数/录单量
					if (ldnums != 0) {
						yyl = (float) yynums * 100 / ldnums;
					}
					Float dzl = (float) 0; // 到诊率 = 到院人数/录单量
					if (ldnums != 0) {
						dzl = (float) yysmnums * 100 / ldnums;
					}
					
					obj.put("ldnums", ldnums); // 录单量
					obj.put("yynums", yynums); // 预约人数
					obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
					obj.put("yysmnums", yysmnums); // 到院人数
					obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
					// 收款金额 = 项目收款 + 预交金收款
					// 项目收款 = 实收 - 预交金
					// 预交金收款 =
					String sk = ""+new BigDecimal(skjeList.get(0).get("paymoney")+"").add(new BigDecimal(skjeYjjList.get(0).get("cmoney")+""));
					obj.put("skje", new BigDecimal(skjeList.get(0).get("paymoney")+"").add(new BigDecimal(skjeYjjList.get(0).get("cmoney")+"")));
					
//			Float cjl = (float) 0; // 成交率 = 成交人数/到院人数
//			if (yysmnums != 0) {
//				cjl = (float) cjnums * 100 / yysmnums;
//			}
					
					Float rjxf = (float) 0; // 人均消费 = 收款金额/到院人数
					if (yysmnums != 0) {
						rjxf = (float) Float.parseFloat(sk) / yysmnums;
					}
					
					//obj.put("cjnums", cjnums); // 成交人数
					//obj.put("cjl", YZUtility.FloatToFixed2(cjl) + "%");
					obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
					obj.put("xmje", skjeList.get(0).get("paymoney"));
					obj.put("ysje", skjeYjjList.get(0).get("cmoney"));
					list.add(obj);
				}else{
					for (int i = 0; i < listPerson.size(); i++) {
						JSONObject objper = listPerson.get(i);
						JSONObject obj = new JSONObject();
						obj.put("xh", i + 1);
						obj.put("username", objper.getString("userName"));
						
						int yysmnums = 0;
						String sk = ""+new BigDecimal(skjeList.get(i).get("paymoney")+"").add(new BigDecimal(skjeYjjList.get(i).get("cmoney")+""));
						for (int j = 0; j <listPerson.size(); j++) {
							if(jdList==null){
								ldnums=0;
							}else if(jdList.get(j).get("seqId").equals(objper.getString("seqId"))){
								ldnums=jdList.get(j).getInt("num");
							}
						}
						for (int j = 0; j <listPerson.size(); j++) {
							if(yynumsList==null){
								yynums=0;
							}else if(yynumsList.get(j).get("seqId").equals(objper.getString("seqId"))){
								yynums=yynumsList.get(j).getInt("num");
							}
						}
						for (int j = 0; j <listPerson.size(); j++) {
							
							if(doorstatusList.get(j).get("seqId").equals(objper.getString("seqId"))){
								yysmnums=doorstatusList.get(j).getInt("num");
							}
						}
						for (int j = 0; j <listPerson.size(); j++) {
							if(skjeList.get(j).get("seqId").equals(objper.getString("seqId"))){
								sk=""+new BigDecimal(skjeList.get(j).get("paymoney")+"");
								obj.put("xmje", skjeList.get(j).get("paymoney"));
							}
						}
						for (int j = 0; j <listPerson.size(); j++) {
							if(skjeYjjList.get(j).get("seqId").equals(objper.getString("seqId"))){
								sk=""+new BigDecimal(sk).add(new BigDecimal(skjeYjjList.get(j).get("cmoney")+""));
								obj.put("ysje", skjeYjjList.get(j).get("cmoney"));
							}
						}
//				for (int j = 0; j < yynumsList.size(); j++) {
//					JSONObject jobj = yynumsList.get(j);
//					if (jobj.getString("createuser").equals(perid)) { // 预约人数累加
//						yynums++;
//					}
//				}
//
//				// 上门人数
//				for (int j = 0; j < doorstatusList.size(); j++) {
//					JSONObject jobj = doorstatusList.get(j);
//					if (jobj.getString("createuser").equals(perid)) { // 到院人数累加
//						yysmnums++;
//					}
//				}
						
//				int cjnums = 0;
//				for (int j = 0; j < yycjnumsList.size(); j++) {
//					JSONObject jobj = yycjnumsList.get(j);
//					if (jobj.getString("createuser").equals(perid)) { // 成交人数累加
//						cjnums++;
//					}
//				}
						
						Float yyl = (float) 0; // 预约率 = 预约人数/录单量
						if (ldnums != 0) {
							yyl = (float) yynums * 100 / ldnums;
						}
						
						Float dzl = (float) 0; // 到诊率 = 到院人数/录单量
						if (ldnums != 0) {
							dzl = (float) yysmnums * 100 / ldnums;
						}
						
						obj.put("ldnums", ldnums); // 录单量
						obj.put("yynums", yynums); // 预约人数
						obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
						obj.put("yysmnums", yysmnums); // 到院人数
						obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
						// 收款金额 = 项目收款 + 预交金收款
						// 项目收款 = 实收 - 预交金
						// 预交金收款 =
						obj.put("skje", sk);
						
//				Float cjl = (float) 0; // 成交率 = 成交人数/到院人数
//				if (yysmnums != 0) {
//					cjl = (float) cjnums * 100 / yysmnums;
//				}
						
						Float rjxf = (float) 0; // 人均消费 = 收款金额/到院人数
						if (yysmnums != 0) {
							rjxf = (float) Float.parseFloat(sk) / yysmnums;
						}
						
						//obj.put("cjnums", cjnums); // 成交人数
						//obj.put("cjl", YZUtility.FloatToFixed2(cjl) + "%");
						obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
						
						list.add(obj);
					}
			}
			}
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("网电预约统计", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	/**
	 * duoyh (查询建档人员)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/findCreateUser.act")
	public String findCreateUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String shouli = request.getParameter("shouli");
		String isyx = request.getParameter("isyx");
		try {
			List<JSONObject> listPerson = null;
			YZPerson person = SessionUtil.getLoginPerson(request);
			YZPriv priv = privLogic.findGeneral(person.getUserPriv());
			String deptId = null;
			if(priv.getAuthority()!=null){
				if(priv.getAuthority().equals("5")){
					listPerson = personLogic.findPersonalDetails(person.getDeptId());
				}
			}else{
			//登录人是否所属业务部门之中
			//如果属于业务部门将业务部门ID赋予一个map中
					if(shouli!=null && !shouli.equals("")){
					YZDept department = deptLogic.findmarketing("2",isyx,shouli);
					deptId = department.getSeqId();
					}else{
						shouli = "zz373";
						YZDept department = deptLogic.findmarketing("2",isyx,shouli);
						deptId = department.getSeqId();
					}
			}
			 listPerson = personLogic.findVisualPersonnel(deptId);
			YZUtility.RETURN_LIST(listPerson, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

	/**
	 * 2019-08-15 lwg
	 * 咨询项目情况统计表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWdOrderPerItemtj.act")
	public String getWdOrderPerItemtj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//根据人员信息查询出部门id，根据部门id查询对应的数据信息
			YZPerson person = SessionUtil.getLoginPerson(request);
			String jdtime1=request.getParameter("jdtime1");
			String jdtime2=request.getParameter("jdtime2");
			String yytime1=request.getParameter("yytime1");
			String yytime2=request.getParameter("yytime2");
			String dztime1=request.getParameter("dztime1");
			String dztime2=request.getParameter("dztime2");
			String yewu =request.getParameter("yewu");
			String xiangmu=request.getParameter("xiangmu");
			String shouli=request.getParameter("shouli");
			String devchannel=request.getParameter("devchannel");
			String isyx = request.getParameter("isyx"); // 1 是营销 2是网电 3客服
			String nexttype = request.getParameter("nexttype");
			String gongju = request.getParameter("gongju");
			// 导出参数 
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			String depttype = "";// 2 网电 3 营销 6客服，这里不能写成 String depttype =
									// null，因为后面的sql会使用这个变量值
			if ("1".equals(isyx)) {// 营销查询
				depttype = ConstUtil.DEPT_TYPE_3;
			} else if ("2".equals(isyx)) {// 网电查询
				depttype = ConstUtil.DEPT_TYPE_2;
			} else if ("3".equals(isyx)) {// 客服查询
				depttype = ConstUtil.DEPT_TYPE_6;
			}

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(jdtime1)) {
				jdtime1 = jdtime1 + ConstUtil.TIME_START;
				map.put("jdtime1", jdtime1);
			}
			if (!YZUtility.isNullorEmpty(jdtime2)) {
				jdtime2 = jdtime2 + ConstUtil.TIME_END;
				map.put("jdtime2", jdtime2);
			}
			if (!YZUtility.isNullorEmpty(yytime1)) {
				yytime1 = yytime1 + ConstUtil.TIME_START;
				map.put("yytime1", yytime1);
			}
			if (!YZUtility.isNullorEmpty(yytime2)) {
				yytime2 = yytime2 + ConstUtil.TIME_END;
				map.put("yytime2", yytime2);
			}
			if (!YZUtility.isNullorEmpty(dztime1)) {
				dztime1 = dztime1 + ConstUtil.TIME_START;
				map.put("dztime1", dztime1);
			}
			if (!YZUtility.isNullorEmpty(dztime2)) {
				dztime2 = dztime2 + ConstUtil.TIME_END;
				map.put("dztime2", dztime2);
			}
			if (!YZUtility.isNullorEmpty(yewu)){
				map.put("yewu", yewu);
			}
			if (!YZUtility.isNullorEmpty(xiangmu)){
				map.put("askitem", xiangmu);
			}
			if (!YZUtility.isNullorEmpty(devchannel)){
				map.put("devchannel", devchannel);		
			}
			if(!YZUtility.isNullorEmpty(shouli)){
				map.put("shouli", shouli);
			}
			if(!YZUtility.isNullorEmpty(nexttype)){
				map.put("nexttype", nexttype);
			}
			if(!YZUtility.isNullorEmpty(gongju)){
				map.put("accepttool", gongju);
			}
			//定义可见人员集合
			List<JSONObject> listPerson = null;	
			//读取登录人的登录信息
			YZPriv priv = privLogic.findGeneral(person.getUserPriv());
			if(priv.getAuthority()!=null){
				if(priv.getAuthority().equals("2")){
					listPerson = personLogic.findPersonalDetails(person.getUserId());
				}
			}else{
			//获取业务部门
			List<JSONObject> dept = deptLogic.findMarket("2");
			
			 String deptId = null;
			//登录人是否所属业务部门之中
			//如果属于业务部门将业务部门ID赋予一个map中
			for (JSONObject json : dept) {
				if(!json.get("seqId").equals(person.getDeptId())){
					if(shouli!=null && !shouli.equals("")){
					YZDept department = deptLogic.findmarketing("2",depttype,shouli);
					deptId = department.getSeqId();
					}else{
						shouli = "zz373";
						YZDept department = deptLogic.findmarketing("2",depttype,shouli);
						deptId = department.getSeqId();
					}
				}else{
					deptId = person.getDeptId();
				}
			}
			
				// 可见人员过滤
				listPerson = personLogic.findVisualPersonnel(deptId);
			}
			if(map.get("yewu")!=null){
				map.put("visualstaff", "\'"+map.get("yewu")+"\'");
			}else{
				String personIds = personLogic.getPerIdsByPersonList(listPerson);
				map.put("visualstaff", personIds);
			}
			
			// 门诊编号，从页面下拉框传值过来
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			/** 门诊编号可以为空 **/
			map.put("organization", organization);
			List<YZDict> zxxmDictList = dictLogic.getListByParentCodeALL(DictUtil.ZXXM, ChainUtil.getCurrentOrganization(request));
			List<JSONObject> listAll = new ArrayList<JSONObject>();
			List<JSONObject> ldnumList=null;
			List<JSONObject> yynumsList =null;
			List<JSONObject> doorstatusList=null;
			if(jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&dztime1.equals("")&&dztime2.equals("")){
				String date = YZUtility.getDateStr(new Date());
				jdtime1 = date + ConstUtil.TIME_START;
				jdtime2 = date + ConstUtil.TIME_END;
				map.put("jdtime1", jdtime1);
				map.put("jdtime2", jdtime2);
				// 录单量
				ldnumList = logic.getCountJdItemList(map);
				map.put("jdtime1", null);
				map.put("jdtime2", null);
				map.put("yytime1", jdtime1);
				map.put("yytime2", jdtime2);
				// 预约人数
				yynumsList = logic.getCountYyItemList(map);
				map.put("yytime1", null);
				map.put("yytime2", null);
				map.put("jdtime1", jdtime1);
				map.put("jdtime2", jdtime2);
				map.put("dztime1", jdtime1);
				map.put("dztime2", jdtime2);
				// 统计当前到院人数
				doorstatusList = logic.getCountDoorstatusItemList(map);
				if(map.get("yewu")!=null){
					// 咨询项目分类列表
					for (int i = 0; i < listPerson.size(); i++) {
						JSONObject objper = listPerson.get(i);
						if(objper.getString("seqId").equals(yewu)){
							// 遍历咨询项目
							for (YZDict dict : zxxmDictList) {
								JSONObject obj1 = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, dict);
								if (obj1 == null) {
									continue;
								}
								listAll.add(obj1);
							}
							// ------------------小计，条件去除挂号分类
							JSONObject objhj = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, null);
							if (objhj == null) {
								continue;
							}
							listAll.add(objhj);
						}
						
					}
				}else{
					for (int i = 0; i < listPerson.size(); i++) {
						JSONObject objper = listPerson.get(i);
						// 遍历咨询项目
						for (YZDict dict : zxxmDictList) {
							JSONObject obj = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, dict);
							if (obj == null) {
								continue;
							}
							listAll.add(obj);
						}
						// ------------------小计，条件去除挂号分类
						JSONObject objhj = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, null);
						if (objhj == null) {
							continue;
						}
						listAll.add(objhj);
					}
				}
			}else{
				
				if(jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&!dztime1.equals("")&&!dztime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&!dztime1.equals("")&&dztime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&dztime1.equals("")&&!dztime2.equals("")){
					ldnumList=null;
					yynumsList =null;
				}else if(jdtime1.equals("")&&jdtime2.equals("")&&!yytime1.equals("")&&!yytime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&!yytime1.equals("")&&yytime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&!yytime2.equals("")){
					ldnumList=null;
					yynumsList = logic.getCountYyItemList(map);
				}else{
					// 录单量 
					ldnumList = logic.getCountJdItemList(map);
					// 预约人数
					yynumsList = logic.getCountYyItemList(map);
					
				}
				// 到诊量
				doorstatusList = logic.getCountDoorstatusItemList(map);
				
				if(map.get("yewu")!=null){
					for (int i = 0; i < listPerson.size(); i++) {
						JSONObject objper = listPerson.get(i);
						if(objper.getString("seqId").equals(yewu)){
							// 遍历咨询项目
							for (YZDict dict : zxxmDictList) {
								JSONObject obj1 = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, dict);
								if (obj1 == null) {
									continue;
								}
								listAll.add(obj1);
							}
							// ------------------小计，条件去除挂号分类
							JSONObject objhj = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, null);
							if (objhj == null) {
								continue;
							}
							listAll.add(objhj);
						}
					}
				}else{
					for (int i = 0; i < listPerson.size(); i++) {
						JSONObject objper = listPerson.get(i);
						// 遍历咨询项目
						for (YZDict dict : zxxmDictList) {
							JSONObject obj = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, dict);
							if (obj == null) {
								continue;
							}
							listAll.add(obj);
						}
						// ------------------小计，条件去除挂号分类
						JSONObject objhj = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, null);
						if (objhj == null) {
							continue;
						}
						listAll.add(objhj);
					}
				}
			}
			
			
			
			
			// 录单量
			//List<JSONObject> ldnumList = logic.getCountJdItemList(map);
			// 预约人数
			//List<JSONObject> yynumsList = logic.getCountYyItemList(map);
			// 统计当前到院人数
			//List<JSONObject> doorstatusList = logic.getCountDoorstatusItemList(map);
			// 成交人数
			//List<JSONObject> yycjnumsList = logic.getCountCjList(map);
			// 咨询项目分类列表
//			List<YZDict> zxxmDictList = dictLogic.getListByParentCodeALL(DictUtil.ZXXM, ChainUtil.getCurrentOrganization(request));
//
//			List<JSONObject> listAll = new ArrayList<JSONObject>();
//			for (int i = 0; i < listPerson.size(); i++) {
//				JSONObject objper = listPerson.get(i);
//				// 遍历咨询项目
//				for (YZDict dict : zxxmDictList) {
//					JSONObject obj = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, dict);
//					if (obj == null) {
//						continue;
//					}
//					listAll.add(obj);
//				}
//				// ------------------小计，条件去除挂号分类
//				JSONObject objhj = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, objper, null);
//				if (objhj == null) {
//					continue;
//				}
//				listAll.add(objhj);
//			}
			// ------------------合计 行（条件去除挂号分类）
			JSONObject objhj = logic.getJSONObject4getWdOrderPerItemtj(ldnumList, yynumsList, doorstatusList, null, null, null);
			listAll.add(objhj);
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("咨询项目情况统计表", fieldArr, fieldnameArr,listAll, response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 网电/营销中心->工作量统计->咨询项目统计表
	 * 2019-08-15 lwg
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWdOrderItemtj.act")
	public String getWdOrderItemtj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//根据人员信息查询出部门id，根据部门id查询对应的数据信息
			YZPerson person = SessionUtil.getLoginPerson(request);
			String jdtime1=request.getParameter("jdtime1");
			String jdtime2=request.getParameter("jdtime2");
			String yytime1=request.getParameter("yytime1");
			String yytime2=request.getParameter("yytime2");
			String dztime1=request.getParameter("dztime1");
			String dztime2=request.getParameter("dztime2");
			String yewu =request.getParameter("yewu");
			String xiangmu=request.getParameter("xiangmu");
			String shouli=request.getParameter("shouli");
			String devchannel=request.getParameter("devchannel");
			String isyx = request.getParameter("isyx"); // 1 是营销 2是网电 3客服
			String nexttype = request.getParameter("nexttype");
			String gongju = request.getParameter("gongju");
			// 导出参数 
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			String depttype = "";// 2 网电 3 营销 6客服，这里不能写成 String depttype =
									// null，因为后面的sql会使用这个变量值
			if ("1".equals(isyx)) {// 营销查询
				depttype = ConstUtil.DEPT_TYPE_3;
			} else if ("2".equals(isyx)) {// 网电查询
				depttype = ConstUtil.DEPT_TYPE_2;
			} else if ("3".equals(isyx)) {// 客服查询
				depttype = ConstUtil.DEPT_TYPE_6;
			}

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(jdtime1)) {
				jdtime1 = jdtime1 + ConstUtil.TIME_START;
				map.put("jdtime1", jdtime1);
			}
			if (!YZUtility.isNullorEmpty(jdtime2)) {
				jdtime2 = jdtime2 + ConstUtil.TIME_END;
				map.put("jdtime2", jdtime2);
			}
			if (!YZUtility.isNullorEmpty(yytime1)) {
				yytime1 = yytime1 + ConstUtil.TIME_START;
				map.put("yytime1", yytime1);
			}
			if (!YZUtility.isNullorEmpty(yytime2)) {
				yytime2 = yytime2 + ConstUtil.TIME_END;
				map.put("yytime2", yytime2);
			}
			if (!YZUtility.isNullorEmpty(dztime1)) {
				dztime1 = dztime1 + ConstUtil.TIME_START;
				map.put("dztime1", dztime1);
			}
			if (!YZUtility.isNullorEmpty(dztime2)) {
				dztime2 = dztime2 + ConstUtil.TIME_END;
				map.put("dztime2", dztime2);
			}
			if (!YZUtility.isNullorEmpty(yewu)){
				map.put("yewu", yewu);
			}
			if (!YZUtility.isNullorEmpty(xiangmu)){
				map.put("askitem", xiangmu);
			}
			if (!YZUtility.isNullorEmpty(devchannel)){
				map.put("devchannel", devchannel);		
			}
			if(!YZUtility.isNullorEmpty(shouli)){
				map.put("shouli", shouli);
			}
			if(!YZUtility.isNullorEmpty(nexttype)){
				map.put("nexttype", nexttype);
			}
			if(!YZUtility.isNullorEmpty(gongju)){
				map.put("accepttool", gongju);
			}
			//定义可见人员集合
			List<JSONObject> listPerson = null;	
			//读取登录人的登录信息
			YZPriv priv = privLogic.findGeneral(person.getUserPriv());
			if(priv.getAuthority()!=null){
				if(priv.getAuthority().equals("2")){
					listPerson = personLogic.findPersonalDetails(person.getUserId());
				}
			}else{
			//获取业务部门
			List<JSONObject> dept = deptLogic.findMarket("2");
			
			 String deptId = null;
			//登录人是否所属业务部门之中
			//如果属于业务部门将业务部门ID赋予一个map中
			for (JSONObject json : dept) {
				if(!json.get("seqId").equals(person.getDeptId())){
					if(shouli!=null && !shouli.equals("")){
					YZDept department = deptLogic.findmarketing("2",depttype,shouli);
					deptId = department.getSeqId();
					}else{
						shouli = "zz373";
						YZDept department = deptLogic.findmarketing("2",depttype,shouli);
						deptId = department.getSeqId();
					}
				}else{
					deptId = person.getDeptId();
				}
			}
				// 可见人员过滤
				listPerson = personLogic.findVisualPersonnel(deptId);
			}
			if(map.get("yewu")!=null){
				map.put("visualstaff", "\'"+map.get("yewu")+"\'");
			}else{
				String personIds = personLogic.getPerIdsByPersonList(listPerson);
				map.put("visualstaff", personIds);
			}
			
			// 门诊编号，从页面下拉框传值过来
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			/** 门诊编号可以为空 **/
			map.put("organization", organization);
			
			List<JSONObject> ldnumList=null;
			List<JSONObject> yynumsList =null;
			List<JSONObject> doorstatusList=null;
			if(jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&dztime1.equals("")&&dztime2.equals("")){
				String date = YZUtility.getDateStr(new Date());
				jdtime1 = date + ConstUtil.TIME_START;
				jdtime2 = date + ConstUtil.TIME_END;
				map.put("jdtime1", jdtime1);
				map.put("jdtime2", jdtime2);
				// 录单量
				ldnumList = logic.getCountJdItemStatisticsList(map);
				map.put("jdtime1", null);
				map.put("jdtime2", null);
				map.put("yytime1", jdtime1);
				map.put("yytime2", jdtime2);
				// 预约人数
				yynumsList = logic.getCountYyItemStatisticsList(map);
				map.put("yytime1", null);
				map.put("yytime2", null);
				map.put("jdtime1", jdtime1);
				map.put("jdtime2", jdtime2);
				map.put("dztime1", jdtime1);
				map.put("dztime2", jdtime2);
				// 统计当前到院人数
				doorstatusList = logic.getCountDoorstatusItemStatisticsList(map);
			}else{
				
				if(jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&!dztime1.equals("")&&!dztime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&!dztime1.equals("")&&dztime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&yytime2.equals("")&&dztime1.equals("")&&!dztime2.equals("")){
					ldnumList=null;
					yynumsList =null;
				}else if(jdtime1.equals("")&&jdtime2.equals("")&&!yytime1.equals("")&&!yytime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&!yytime1.equals("")&&yytime2.equals("")
				||jdtime1.equals("")&&jdtime2.equals("")&&yytime1.equals("")&&!yytime2.equals("")){
					ldnumList=null;
					yynumsList = logic.getCountYyItemStatisticsList(map);
				}else{
					// 录单量 
					ldnumList = logic.getCountJdItemStatisticsList(map);
					// 预约人数
					yynumsList = logic.getCountYyItemStatisticsList(map);
					
				}
				// 到诊量
				doorstatusList = logic.getCountDoorstatusItemStatisticsList(map);
			}
			int ldallnums = 0;// 总咨询人次（总录单量）
			int yyallnums = 0; // 总预约人数
			//int cjallnums = yycjnumsList.size(); // 总成交人数
			int yysmallnums = 0; // 总到诊人数
			// 成交人数
			//List<JSONObject> yycjnumsList = logic.getCountCjList(map);
			if(ldnumList!=null){
				for (JSONObject jsonObject : ldnumList) {
					ldallnums+=jsonObject.getInt("ldnums");
				}
			}
			if(yynumsList!=null){
				for (JSONObject jsonObject : yynumsList) {
					yyallnums+=jsonObject.getInt("yynums");
				}
			}
			for (JSONObject jsonObject : doorstatusList) {
				yysmallnums+=jsonObject.getInt("yysmnums");
			}
			// 该List用于结果返回，页面展示
			List<JSONObject> listAll = new ArrayList<>();
			// 遍历咨询项目
			List<YZDict> listDict = dictLogic.getListByParentCodeALL(DictUtil.ZXXM, ChainUtil.getCurrentOrganization(request));
			for (YZDict dict : listDict) {
				int ldnums = 0;
				int yynums = 0;
				int yysmnums = 0;
				//int cjnums = 0;
				if(ldnumList!=null){
					for (int j = 0; j < ldnumList.size(); j++) {
						JSONObject jobj = ldnumList.get(j);
						if (jobj.getString("askitem").equals(dict.getSeqId())) {
							ldnums+=jobj.getInt("ldnums");
						}
					}
				}
				// 预约人数
				if(yynumsList!=null){
					
					for (int j = 0; j < yynumsList.size(); j++) {
						JSONObject jobj = yynumsList.get(j);
						if (jobj.getString("askitem").equals(dict.getSeqId())) {
							yynums+=jobj.getInt("yynums");
						}
					}
				}

				// 上门人数
				for (int j = 0; j < doorstatusList.size(); j++) {
					JSONObject jobj = doorstatusList.get(j);
					if (jobj.getString("askitem").equals(dict.getSeqId())) {
						yysmnums+=jobj.getInt("yysmnums");
					}
				}

				// 如果 咨询人次、预约人数、到院人数为0，不展示
//				if (ldnums == 0 && yynums == 0 && yysmnums == 0) {
//					continue;
//				}

				// 成交人数
//				for (int j = 0; j < yycjnumsList.size(); j++) {
//					JSONObject jobj = yycjnumsList.get(j);
//					if (jobj.getString("askitem").equals(dict.getSeqId())) {
//						cjnums++;
//					}
//				}

				Float zzzb = (float) 0; // 咨询人次占比 = 咨询人次（咨询项目）/ 总咨询人次
				if (ldallnums != 0) {
					zzzb = (float) ldnums * 100 / ldallnums;
				}

				Float dzl = (float) 0;// 到诊率（预约成功率） = 到院人数/预约人数
				if (yynums != 0) {
					dzl = (float) yysmnums * 100 / yynums;
				}

//				Float cjl = (float) 0; // 到诊成交率 = 成交人数/到院人数
//				if (yysmnums != 0) {
//					cjl = (float) cjnums * 100 / yysmnums;
//				}

//				Float cjlzb = (float) 0; // 成交率占比 = 成交人数/总到院人数
//				if (cjallnums != 0) {
//					cjlzb = (float) cjnums * 100 / cjallnums;
//				}

				JSONObject obj = new JSONObject();
				obj.put("zxxm", dict.getDictName());// ------咨询项目
				obj.put("ldnums", ldnums);// 录单量
				obj.put("zzzb", YZUtility.FloatToFixed2(zzzb) + "%");
				obj.put("yynums", yynums); // 预约人数
				obj.put("yysmnums", yysmnums); // 到院人数
				obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
//				obj.put("cjnums", cjnums); // 成交人数
//				obj.put("cjl", YZUtility.FloatToFixed2(cjl) + "%");
//				obj.put("cjlzb", YZUtility.FloatToFixed2(cjlzb) + "%");
				listAll.add(obj);
			}
			JSONObject objhj = new JSONObject();
			int ldnums = ldallnums;// 总咨询人次（总录单量）
			int yynums = yyallnums;
			int yysmnums = yysmallnums;// 总到诊人数
			//int cjnums = cjallnums;

			Float dzl = (float) 0; // 到诊率（预约成功率） = 到院人数/预约人数
			if (yynums != 0) {
				dzl = (float) yysmnums * 100 / yynums;
			}

//			Float cjl = (float) 0; // 到诊成交率 = 成交人数/到院人数
//			if (yysmnums != 0) {
//				cjl = (float) cjnums * 100 / yysmnums;
//			}

//			Float cjlzb = (float) 0; // 成交率占比 = 成交人数/总到院人数
//			if (cjallnums != 0) {
//				cjlzb = (float) cjnums * 100 / cjallnums;
//			}

			// 录单量
			objhj.put("ldnums", ldnums);
			objhj.put("zzzb", "100%"); // 咨询人次占比，肯定是100%
			objhj.put("yynums", yynums); // 预约人数
			objhj.put("yysmnums", yysmnums); // 到院人数
			objhj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
//			objhj.put("cjnums", cjnums); // 成交人数
//			objhj.put("cjl", YZUtility.FloatToFixed2(cjl) + "%");
//			objhj.put("cjlzb", YZUtility.FloatToFixed2(cjlzb) + "%");
			// 如果 咨询人次为0，不展示
			listAll.add(objhj);
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 预交金查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCount_yjjcx.act")
	public String selectCount_yjjcx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
			}
			List<JSONObject> listAll = new ArrayList<>();
			// 期初金额
			JSONObject qcjobj = logic.getQcje(starttime, organization);
			qcjobj.put("name", "期初余额");
			listAll.add(qcjobj);
			// 开卡金额
			JSONObject kkjobj = logic.getCaozuoje("开卡", starttime, endtime, organization);
			kkjobj.put("name", "本期开卡");
			listAll.add(kkjobj);
			// 充值金额
			JSONObject czobj = logic.getCaozuoje("充值", starttime, endtime, organization);
			czobj.put("name", "本期充值");
			listAll.add(czobj);
			// 缴费金额
			JSONObject jfjobj = logic.getCaozuoje("缴费", starttime, endtime, organization);
			jfjobj.put("name", "本期缴费");
			listAll.add(jfjobj);
			// 退费金额
			JSONObject tfjobj = logic.getCaozuoje("退费", starttime, endtime, organization);
			tfjobj.put("name", "本期退费");
			listAll.add(tfjobj);
			// 期末金额
			JSONObject qmjobj = logic.getQmje(endtime, organization);
			qmjobj.put("name", "期末余额");
			listAll.add(qmjobj);
			JSONObject jobj2 = new JSONObject();
			jobj2.put("data", listAll);
			YZUtility.DEAL_SUCCESS(jobj2, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 预交金查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCount_hjzlqk.act")
	public String selectCount_hjzlqk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
			}
			List<JSONObject> listAll = new ArrayList<>();

			JSONObject alljobj = logic.getHjzlqk(starttime, endtime, 0, organization);

			JSONObject yzljobj = logic.getHjzlqk(starttime, endtime, 1, organization);
			// 未治疗金额
			JSONObject wzlobj = new JSONObject();
			wzlobj.put("name", "已划价未治疗");
			wzlobj.put("ys", new BigDecimal(alljobj.getString("ys")).subtract(new BigDecimal(yzljobj.getString("ys"))).toString());
			wzlobj.put("paymoney", new BigDecimal(alljobj.getString("paymoney")).subtract(new BigDecimal(yzljobj.getString("paymoney"))).toString());
			wzlobj.put("y2", new BigDecimal(alljobj.getString("y2")).subtract(new BigDecimal(yzljobj.getString("y2"))).toString());
			listAll.add(wzlobj);
			// 已治疗金额
			yzljobj.put("name", "已划价已治疗");
			listAll.add(yzljobj);
			// 小计金额
			alljobj.put("name", "小计");
			listAll.add(alljobj);

			JSONObject jobj2 = new JSONObject();
			jobj2.put("data", listAll);
			YZUtility.DEAL_SUCCESS(jobj2, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 1.连锁咨询项目统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCount_lszxxmtj.act")
	public String selectCount_lszxxmtj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			// 查询门诊列表
			List<YZDept> deptList = deptLogic.getSubOrgDeptListBySeqId("0", "", "");
			List<JSONObject> listAll = new ArrayList<JSONObject>();
			List<JSONObject> listRegAllTotal = logic.getListRegByGhfl(map, "", "");
			// 总咨询人数
			int allnums = listRegAllTotal.size();
			// 总咨询成交人数
			int allcjnums = 0;
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				if ("1".equals(cjstatus)) {
					allcjnums++;
				}
			}
			/** 营收数据列表 **/
			List<JSONObject> allskjeList = null;
			List<JSONObject> allskjeYjjList = null;
			allskjeList = logic.getYysrList(map);
			allskjeYjjList = logic.getYysrYjjList(map);

			// 获取初诊编码
			String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
			// 获取复诊编码
			String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
			// 获取再消费编码
			String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
			// 获取复查编码
			String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
			// 获取其他编码
			String qtseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
			for (YZDept organization : deptList) {
				// 获取挂号
				List<JSONObject> listRegFilter = new ArrayList<JSONObject>();
				int hjcjnums = 0; // ------其中成交人次
				int hjwcjnums = 0;// ------其中未成交人次
				int hjczallnums = 0; // ------初诊总人次
				int hjczcjnums = 0; // ------初诊成交人次
				int hjfzallnums = 0; // ------复诊总人次
				int hjfzcjnums = 0;// ------复诊成交人次
				int hjzxfallnums = 0;// ------再消费总人次
				int hjzxfcjnums = 0;// ------再消费成交人次
				int hjfcallnums = 0;// ------复查总人次
				int hjfccjnums = 0;// ------复查成交人次
				int hjqtallnums = 0; // ------其他总人次
				int hjqtcjnums = 0; // ------其他成交人次
				String hjsk = "0.00";
				String hjss = "0.00";
				/** 营收数据列表 **/
				map.put("organization", organization.getDeptCode());
				List<JSONObject> skjeList = null;
				List<JSONObject> skjeYjjList = null;
				skjeList = logic.getYysrList(map);
				skjeYjjList = logic.getYysrYjjList(map);
				for (JSONObject regJson : listRegAllTotal) {
					if (organization.getDeptCode().equals(regJson.getString("organization"))) {
						listRegFilter.add(regJson);
						String cjstatus = regJson.getString("cjstatus");
						String recesort = regJson.getString("recesort");
						if ("1".equals(cjstatus)) {
							hjcjnums++;
						}
						if ("0".equals(cjstatus)) {
							hjwcjnums++;
						}
						if (czseqId.equals(recesort)) {
							hjczallnums++;
						}
						if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjczcjnums++;
						}
						if (fzseqId.equals(recesort)) {
							hjfzallnums++;
						}
						if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjfzcjnums++;
						}
						if (zxfseqId.equals(recesort)) {
							hjzxfallnums++;
						}
						if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjzxfcjnums++;
						}
						if (fcseqId.equals(recesort)) {
							hjfcallnums++;
						}
						if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjfccjnums++;
						}
						if (qtseqId.equals(recesort)) {
							hjqtallnums++;
						}
						if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjqtcjnums++;
						}
						hjsk = getskje4AskPerson(skjeList, skjeYjjList, "", "");
						hjss = getskje4AskPersonSS(skjeList, skjeYjjList, "", "");
					}
				}
				if (listRegFilter == null || listRegFilter.size() == 0) {
					continue;
				}

				// 获取咨询项目
				List<YZDict> listDict = dictLogic.getListByParentCodeALL("GHFL", organization.getDeptCode());
				// 遍历咨询项目
				for (YZDict dict : listDict) {
					List<JSONObject> listRegDictFilter = new ArrayList<JSONObject>();
					int cjnums = 0; // ------其中成交人次
					int wcjnums = 0;// ------其中未成交人次
					int czallnums = 0; // ------初诊总人次
					int czcjnums = 0; // ------初诊成交人次
					int fzallnums = 0; // ------复诊总人次
					int fzcjnums = 0;// ------复诊成交人次
					int zxfallnums = 0;// ------再消费总人次
					int zxfcjnums = 0;// ------再消费成交人次
					int fcallnums = 0;// ------复查总人次
					int fccjnums = 0;// ------复查成交人次
					int qtallnums = 0; // ------其他总人次
					int qtcjnums = 0; // ------其他成交人次
					String sk = "0.00";
					String ss = "0.00";
					for (JSONObject jsonReg : listRegFilter) {
						if (jsonReg.getString("regsort").equals(dict.getSeqId())) { // 根据挂号分类过滤
							listRegDictFilter.add(jsonReg);
							String cjstatus = jsonReg.getString("cjstatus");
							String recesort = jsonReg.getString("recesort");
							if ("1".equals(cjstatus)) {
								cjnums++;
							}

							if ("0".equals(cjstatus)) {
								wcjnums++;
							}

							if (czseqId.equals(recesort)) {
								czallnums++;
							}
							if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
								czcjnums++;
							}

							if (fzseqId.equals(recesort)) {
								fzallnums++;
							}
							if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
								fzcjnums++;
							}

							if (zxfseqId.equals(recesort)) {
								zxfallnums++;
							}
							if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
								zxfcjnums++;
							}

							if (fcseqId.equals(recesort)) {
								fcallnums++;
							}
							if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
								fccjnums++;
							}
							if (qtseqId.equals(recesort)) {
								qtallnums++;
							}
							if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
								qtcjnums++;
							}
							sk = getskje4AskPerson(skjeList, skjeYjjList, "", dict.getSeqId());
							ss = getskje4AskPersonSS(skjeList, skjeYjjList, "", dict.getSeqId());
						}
					}
					if (listRegDictFilter == null || listRegDictFilter.size() == 0) {
						continue;
					}
					JSONObject obj = new JSONObject();
					// ------门诊
					obj.put("deptname", organization.getDeptName());
					// ------咨询项目/挂号分类
					obj.put("zxxm", dict.getDictName());
					// ------咨询人次
					int zxnums = listRegDictFilter.size();
					obj.put("zxnums", zxnums);
					// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
					Float zzlzb = (float) 0;
					if (allnums != 0) {
						zzlzb = (float) zxnums * 100 / allnums;
					}
					obj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");

					// ------其中成交人次
					obj.put("cjnums", cjnums);
					// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
					Float cgl = (float) 0;
					if (zxnums != 0) {
						cgl = (float) cjnums * 100 / zxnums;
					}
					obj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
					// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
					Float cglzb = (float) 0;
					if (allcjnums != 0) {
						cglzb = (float) cjnums * 100 / allcjnums;
					}
					obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
					// ------其中未成交人次
					obj.put("wcjnums", wcjnums);
					// ------初诊总人次
					obj.put("czallnums", czallnums);
					// ------初诊成交人次
					obj.put("czcjnums", czcjnums);
					// ------复诊总人次
					obj.put("fzallnums", fzallnums);
					// ------复诊成交人次
					obj.put("fzcjnums", fzcjnums);
					// ------再消费总人次
					obj.put("zxfallnums", zxfallnums);
					// ------再消费成交人次
					obj.put("zxfcjnums", zxfcjnums);
					// ------复查总人次
					obj.put("fcallnums", fcallnums);
					// ------复查成交人次
					obj.put("fccjnums", fccjnums);
					// ------其他总人次
					obj.put("qtallnums", qtallnums);
					// ------其他成交人次
					obj.put("qtcjnums", qtcjnums);
					// ------收款金额
					obj.put("ssje", ss);
					obj.put("skje", sk);
					listAll.add(obj);
				}
				// ------------------合计 行（条件去除挂号分类）
				JSONObject objhj = new JSONObject();
				// ------咨询人次
				int zxnums = listRegFilter.size();
				objhj.put("zxnums", zxnums);
				// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
				Float zzlzb = (float) 0;
				if (allnums != 0) {
					zzlzb = (float) zxnums * 100 / allnums;
				}
				objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
				// ------其中成交人次
				objhj.put("cjnums", hjcjnums);
				// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
				Float cgl = (float) 0;
				if (zxnums != 0) {
					cgl = (float) hjcjnums * 100 / zxnums;
				}
				objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
				// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
				Float cglzb = (float) 0;
				if (allcjnums != 0) {
					cglzb = (float) hjcjnums * 100 / allcjnums;
				}
				objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
				// ------其中未成交人次
				objhj.put("wcjnums", hjwcjnums);
				// ------初诊总人次
				objhj.put("czallnums", hjczallnums);
				// ------初诊成交人次
				objhj.put("czcjnums", hjczcjnums);
				// ------复诊总人次
				objhj.put("fzallnums", hjfzallnums);
				// ------复诊成交人次
				objhj.put("fzcjnums", hjfzcjnums);
				// ------再消费总人次
				objhj.put("zxfallnums", hjzxfallnums);
				// ------再消费成交人次
				objhj.put("zxfcjnums", hjzxfcjnums);
				// ------复查总人次
				objhj.put("fcallnums", hjfcallnums);
				// ------复查成交人次
				objhj.put("fccjnums", hjfccjnums);
				// ------其他总人次
				objhj.put("qtallnums", hjqtallnums);
				// ------其他成交人次
				objhj.put("qtcjnums", hjqtcjnums);
				// ------收款金额
				objhj.put("ssje", hjss);
				objhj.put("skje", hjsk);
				listAll.add(objhj);

			}
			/** -------------------------------------------二级细分，细分到人和挂号分类 END **/
			// ------------------总计 行（条件去除挂号分类）
			int cjnums = 0; // ------其中成交人次
			int wcjnums = 0;// ------其中未成交人次
			int czallnums = 0; // ------初诊总人次
			int czcjnums = 0; // ------初诊成交人次
			int fzallnums = 0; // ------复诊总人次
			int fzcjnums = 0;// ------复诊成交人次
			int zxfallnums = 0;// ------再消费总人次
			int zxfcjnums = 0;// ------再消费成交人次
			int fcallnums = 0;// ------复查总人次
			int qtallnums = 0; // ------其他总人次
			int fccjnums = 0;// ------复查成交人次
			int qtcjnums = 0; // ------其他成交人次
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				String recesort = regJson.getString("recesort");
				if ("1".equals(cjstatus)) {
					cjnums++;
				}

				if ("0".equals(cjstatus)) {
					wcjnums++;
				}

				if (czseqId.equals(recesort)) {
					czallnums++;
				}
				if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
					czcjnums++;
				}

				if (fzseqId.equals(recesort)) {
					fzallnums++;
				}
				if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
					fzcjnums++;
				}

				if (zxfseqId.equals(recesort)) {
					zxfallnums++;
				}
				if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
					zxfcjnums++;
				}

				if (fcseqId.equals(recesort)) {
					fcallnums++;
				}
				if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
					fccjnums++;
				}
				if (qtseqId.equals(recesort)) {
					qtallnums++;
				}
				if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
					qtcjnums++;
				}
			}

			JSONObject objzj = new JSONObject();
			// ------咨询人次（医生情况统计，查询总数 排除 挂号表医生为空的情况）
			int zxnums = allnums;
			objzj.put("zxnums", zxnums);
			// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
			Float zzlzb = (float) 0;
			if (allnums != 0) {
				zzlzb = (float) zxnums * 100 / allnums;
			}
			objzj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
			// ------其中成交人次
			objzj.put("cjnums", allcjnums);
			// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
			Float cgl = (float) 0;
			if (zxnums != 0) {
				cgl = (float) cjnums * 100 / zxnums;
			}
			objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
			// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
			Float cglzb = (float) 0;
			if (allcjnums != 0) {
				cglzb = (float) cjnums * 100 / allcjnums;
			}
			objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
			// ------其中未成交人次
			objzj.put("wcjnums", wcjnums);

			// ------初诊总人次
			objzj.put("czallnums", czallnums);
			// ------初诊成交人次
			objzj.put("czcjnums", czcjnums);

			// ------复诊总人次
			objzj.put("fzallnums", fzallnums);
			// ------复诊成交人次
			objzj.put("fzcjnums", fzcjnums);

			// ------再消费总人次
			objzj.put("zxfallnums", zxfallnums);
			// ------再消费成交人次
			objzj.put("zxfcjnums", zxfcjnums);

			// ------复查总人次
			objzj.put("fcallnums", fcallnums);

			// ------复查成交人次
			objzj.put("fccjnums", fccjnums);
			// ------其他总人次
			objzj.put("qtallnums", qtallnums);
			// ------其他成交人次
			objzj.put("qtcjnums", qtcjnums);
			// ------收款金额
			String sk = "0.00";
			sk = getskje4AskPerson(allskjeList, allskjeYjjList, null, null);
			String ss = getskje4AskPersonSS(allskjeList, allskjeYjjList, null, null);
			objzj.put("ssje", ss);
			objzj.put("skje", sk);
			// ---如果 咨询人次为0，不展示
			if (zxnums > 0) {
				listAll.add(objzj);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

			// System.out.println(System.currentTimeMillis() - start);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 1.连锁咨询项目统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCount_lsxmtj.act")
	public String selectCount_lsxmtj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			// 查询门诊列表
			List<YZDept> deptList = deptLogic.getSubOrgDeptListBySeqId("0", "", "");
			List<JSONObject> listAll = new ArrayList<JSONObject>();
			List<JSONObject> listRegAllTotal = logic.getListRegByGhfl(map, "", "");
			// 总咨询人数
			int allnums = listRegAllTotal.size();
			// 总咨询成交人数
			int allcjnums = 0;
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				if ("1".equals(cjstatus)) {
					allcjnums++;
				}
			}
			/** 营收数据列表 **/
			List<JSONObject> allskjeList = null;
			List<JSONObject> allskjeYjjList = null;
			allskjeList = logic.getYysrList(map);
			allskjeYjjList = logic.getYysrYjjList(map);

			// 获取初诊编码
			String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
			// 获取复诊编码
			String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
			// 获取再消费编码
			String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
			// 获取复查编码
			String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
			// 获取其他编码
			String qtseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
			for (YZDept organization : deptList) {
				// 获取挂号
				List<JSONObject> listRegFilter = new ArrayList<JSONObject>();
				int hjcjnums = 0; // ------其中成交人次
				int hjwcjnums = 0;// ------其中未成交人次
				int hjczallnums = 0; // ------初诊总人次
				int hjczcjnums = 0; // ------初诊成交人次
				int hjfzallnums = 0; // ------复诊总人次
				int hjfzcjnums = 0;// ------复诊成交人次
				int hjzxfallnums = 0;// ------再消费总人次
				int hjzxfcjnums = 0;// ------再消费成交人次
				int hjfcallnums = 0;// ------复查总人次
				int hjfccjnums = 0;// ------复查成交人次
				int hjqtallnums = 0; // ------其他总人次
				int hjqtcjnums = 0; // ------其他成交人次
				String hjsk = "0.00";
				String hjss = "0.00";
				/** 营收数据列表 **/
				map.put("organization", organization.getDeptCode());
				List<JSONObject> skjeList = null;
				List<JSONObject> skjeYjjList = null;
				skjeList = logic.getYysrList(map);
				skjeYjjList = logic.getYysrYjjList(map);
				for (JSONObject regJson : listRegAllTotal) {
					if (organization.getDeptCode().equals(regJson.getString("organization"))) {
						listRegFilter.add(regJson);
						String cjstatus = regJson.getString("cjstatus");
						String recesort = regJson.getString("recesort");
						if ("1".equals(cjstatus)) {
							hjcjnums++;
						}
						if ("0".equals(cjstatus)) {
							hjwcjnums++;
						}
						if (czseqId.equals(recesort)) {
							hjczallnums++;
						}
						if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjczcjnums++;
						}
						if (fzseqId.equals(recesort)) {
							hjfzallnums++;
						}
						if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjfzcjnums++;
						}
						if (zxfseqId.equals(recesort)) {
							hjzxfallnums++;
						}
						if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjzxfcjnums++;
						}
						if (fcseqId.equals(recesort)) {
							hjfcallnums++;
						}
						if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjfccjnums++;
						}
						if (qtseqId.equals(recesort)) {
							hjqtallnums++;
						}
						if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
							hjqtcjnums++;
						}
						hjsk = getskje4AskPerson(skjeList, skjeYjjList, "", "");
						hjss = getskje4AskPersonSS(skjeList, skjeYjjList, "", "");
					}
				}
				if (listRegFilter == null || listRegFilter.size() == 0) {
					continue;
				}
				// ------------------合计 行（条件去除挂号分类）
				JSONObject objhj = new JSONObject();
				objhj.put("deptname", organization.getDeptName());
				// ------咨询人次
				int zxnums = listRegFilter.size();
				objhj.put("zxnums", zxnums);
				// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
				Float zzlzb = (float) 0;
				if (allnums != 0) {
					zzlzb = (float) zxnums * 100 / allnums;
				}
				objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
				// ------其中成交人次
				objhj.put("cjnums", hjcjnums);
				// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
				Float cgl = (float) 0;
				if (zxnums != 0) {
					cgl = (float) hjcjnums * 100 / zxnums;
				}
				objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
				// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
				Float cglzb = (float) 0;
				if (allcjnums != 0) {
					cglzb = (float) hjcjnums * 100 / allcjnums;
				}
				objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
				// ------其中未成交人次
				objhj.put("wcjnums", hjwcjnums);
				// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
				Float wcglzb = (float) 0;
				if (allcjnums != 0) {
					wcglzb = (float) hjwcjnums * 100 / allcjnums;
				}
				objhj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
				// ------初诊总人次
				objhj.put("czallnums", hjczallnums);
				// ------初诊成交人次
				objhj.put("czcjnums", hjczcjnums);
				// ------复诊总人次
				objhj.put("fzallnums", hjfzallnums);
				// ------复诊成交人次
				objhj.put("fzcjnums", hjfzcjnums);
				// ------再消费总人次
				objhj.put("zxfallnums", hjzxfallnums);
				// ------再消费成交人次
				objhj.put("zxfcjnums", hjzxfcjnums);
				// ------复查总人次
				objhj.put("fcallnums", hjfcallnums);
				// ------复查成交人次
				objhj.put("fccjnums", hjfccjnums);
				// ------其他总人次
				objhj.put("qtallnums", hjqtallnums);
				// ------其他成交人次
				objhj.put("qtcjnums", hjqtcjnums);
				// ------收款金额
				objhj.put("ssje", hjss);
				objhj.put("skje", hjsk);
				listAll.add(objhj);

			}
			/** -------------------------------------------二级细分，细分到人和挂号分类 END **/
			// ------------------总计 行（条件去除挂号分类）
			int cjnums = 0; // ------其中成交人次
			int wcjnums = 0;// ------其中未成交人次
			int czallnums = 0; // ------初诊总人次
			int czcjnums = 0; // ------初诊成交人次
			int fzallnums = 0; // ------复诊总人次
			int fzcjnums = 0;// ------复诊成交人次
			int zxfallnums = 0;// ------再消费总人次
			int zxfcjnums = 0;// ------再消费成交人次
			int fcallnums = 0;// ------复查总人次
			int qtallnums = 0; // ------其他总人次
			int fccjnums = 0;// ------复查成交人次
			int qtcjnums = 0; // ------其他成交人次
			for (JSONObject regJson : listRegAllTotal) {
				String cjstatus = regJson.getString("cjstatus");
				String recesort = regJson.getString("recesort");
				if ("1".equals(cjstatus)) {
					cjnums++;
				}

				if ("0".equals(cjstatus)) {
					wcjnums++;
				}

				if (czseqId.equals(recesort)) {
					czallnums++;
				}
				if (czseqId.equals(recesort) && "1".equals(cjstatus)) {
					czcjnums++;
				}

				if (fzseqId.equals(recesort)) {
					fzallnums++;
				}
				if (fzseqId.equals(recesort) && "1".equals(cjstatus)) {
					fzcjnums++;
				}

				if (zxfseqId.equals(recesort)) {
					zxfallnums++;
				}
				if (zxfseqId.equals(recesort) && "1".equals(cjstatus)) {
					zxfcjnums++;
				}

				if (fcseqId.equals(recesort)) {
					fcallnums++;
				}
				if (fcseqId.equals(recesort) && "1".equals(cjstatus)) {
					fccjnums++;
				}
				if (qtseqId.equals(recesort)) {
					qtallnums++;
				}
				if (qtseqId.equals(recesort) && "1".equals(cjstatus)) {
					qtcjnums++;
				}
			}

			JSONObject objzj = new JSONObject();
			// ------咨询人次（医生情况统计，查询总数 排除 挂号表医生为空的情况）
			int zxnums = allnums;
			objzj.put("zxnums", zxnums);
			// ------咨询率占比% = 咨询人次（该咨询）/总咨询人数（所有咨询）
			Float zzlzb = (float) 0;
			if (allnums != 0) {
				zzlzb = (float) zxnums * 100 / allnums;
			}
			objzj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
			// ------其中成交人次
			objzj.put("cjnums", allcjnums);
			// ------成功率% = 咨询成交人次（该咨询）/咨询人次（该咨询）
			Float cgl = (float) 0;
			if (zxnums != 0) {
				cgl = (float) cjnums * 100 / zxnums;
			}
			objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
			// ------成功率占比% = 咨询成交人次（该咨询）/咨询人次（所有咨询）
			Float cglzb = (float) 0;
			if (allcjnums != 0) {
				cglzb = (float) cjnums * 100 / allcjnums;
			}
			objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
			// ------其中未成交人次
			objzj.put("wcjnums", wcjnums);
			Float wcglzb = (float) 0;
			if (allcjnums != 0) {
				wcglzb = (float) wcjnums * 100 / allcjnums;
			}
			objzj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
			// ------初诊总人次
			objzj.put("czallnums", czallnums);
			// ------初诊成交人次
			objzj.put("czcjnums", czcjnums);

			// ------复诊总人次
			objzj.put("fzallnums", fzallnums);
			// ------复诊成交人次
			objzj.put("fzcjnums", fzcjnums);

			// ------再消费总人次
			objzj.put("zxfallnums", zxfallnums);
			// ------再消费成交人次
			objzj.put("zxfcjnums", zxfcjnums);

			// ------复查总人次
			objzj.put("fcallnums", fcallnums);

			// ------复查成交人次
			objzj.put("fccjnums", fccjnums);
			// ------其他总人次
			objzj.put("qtallnums", qtallnums);
			// ------其他成交人次
			objzj.put("qtcjnums", qtcjnums);
			// ------收款金额
			String sk = "0.00";
			sk = getskje4AskPerson(allskjeList, allskjeYjjList, null, null);
			String ss = getskje4AskPersonSS(allskjeList, allskjeYjjList, null, null);
			objzj.put("ssje", ss);
			objzj.put("skje", sk);
			// ---如果 咨询人次为0，不展示
			if (zxnums > 0) {
				listAll.add(objzj);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

			// System.out.println(System.currentTimeMillis() - start);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 1.咨询/医生成功率分析表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCount_lsxffltj.act")
	public String selectCount_lsxffltj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			// 查询门诊列表
			List<YZDept> deptList = deptLogic.getSubOrgDeptListBySeqId("0", "", "");

			List<JSONObject> listAll = new ArrayList<JSONObject>();
			// 总计
			BigDecimal zj_ysje = BigDecimal.ZERO;
			BigDecimal zj_ssje = BigDecimal.ZERO;
			for (int i = 0; i < deptList.size(); i++) {
				// 合计
				BigDecimal hj_ysje = BigDecimal.ZERO;
				BigDecimal hj_ssje = BigDecimal.ZERO;
				YZDept objper = deptList.get(i);
				map.put("organization", objper.getDeptCode());
				/** 应收、实收数据列表 **/
				List<JSONObject> zjcostdetailList = logic.getYysrDetailList(map);

				/********
				 * ############################################################# ####
				 * 根据一二级分类，进行处理 #################
				 *************/
				Set<String> currSortSet = new HashSet<String>(); // 获取不重复的一二级消费分类
				Map<String, String> sortNameMap = new HashMap<String, String>();
				for (JSONObject perDetail : zjcostdetailList) {
					String basetype = perDetail.getString("basetype");
					String nexttype = perDetail.getString("nexttype");
					String base_next = basetype + "," + nexttype;
					currSortSet.add(base_next);
					// 存储分类名称
					sortNameMap.put(basetype, perDetail.getString("basename"));
					sortNameMap.put(nexttype, perDetail.getString("nextname"));
				}

				for (String base_next : currSortSet) {
					if (YZUtility.isNullorEmpty(base_next)) {
						continue;
					}
					String[] sortArray = base_next.split(",");
					if (sortArray.length != 2) {
						continue;
					}
					String basetype = sortArray[0]; // 一级分类
					String nexttype = sortArray[1]; // 二级分类

					if (YZUtility.isNullorEmpty(basetype) || YZUtility.isNullorEmpty(nexttype)) {
						continue;
					}

					JSONObject obj = new JSONObject();
					// ------人员
					obj.put("deptname", objper.getDeptName());
					// ------消费一级分类
					obj.put("basename", sortNameMap.get(basetype));
					// ------消费二级分类
					obj.put("nextname", sortNameMap.get(nexttype));

					BigDecimal ysje = BigDecimal.ZERO;
					BigDecimal ssje = BigDecimal.ZERO;
					for (JSONObject perDetail : zjcostdetailList) {
						String basetype2 = perDetail.getString("basetype");
						String nexttype2 = perDetail.getString("nexttype");

						if (basetype.equals(basetype2) && nexttype.equals(nexttype2)) {
							// -------应收金额（小计-免除）
							String subtotal = perDetail.getString("subtotal");
							String voidmoney = perDetail.getString("voidmoney");
							BigDecimal ys = new BigDecimal(subtotal).subtract(new BigDecimal(voidmoney));
							ysje = ysje.add(KqdsBigDecimal.round(ys, 2));
							// -------实收金额
							String paymoney = perDetail.getString("paymoney");
							String payother2 = perDetail.getString("payother2");
							String paydjq = perDetail.getString("paydjq");
							String payintegral = perDetail.getString("payintegral");
							BigDecimal ss = new BigDecimal(paymoney).subtract(new BigDecimal(payother2)).subtract(new BigDecimal(paydjq)).subtract(new BigDecimal(payintegral));
							ssje = ssje.add(KqdsBigDecimal.round(ss, 2));
						}
					}
					obj.put("ysje", ysje);
					obj.put("ssje", ssje);
					listAll.add(obj);
					hj_ysje = hj_ysje.add(ysje);
					hj_ssje = hj_ssje.add(ssje);
				}
				/********
				 * ############################################################# ####
				 * 根据一二级分类，进行处理 END #################
				 *************/
				// ------------------合计 行
				JSONObject objhj = new JSONObject();
				objhj.put("ysje", KqdsBigDecimal.round(hj_ysje, 2));
				objhj.put("ssje", KqdsBigDecimal.round(hj_ssje, 2));
				listAll.add(objhj);
				// System.out.println("-------合计----");
				zj_ysje = zj_ysje.add(hj_ysje);
				zj_ssje = zj_ssje.add(hj_ssje);

			}
			// ------------------总计 行
			if (deptList.size() > 0) {
				JSONObject objzj = new JSONObject();
				objzj.put("ysje", KqdsBigDecimal.round(zj_ysje, 2));
				objzj.put("ssje", KqdsBigDecimal.round(zj_ssje, 2));
				listAll.add(objzj);
				// System.out.println("-------总计----");
			}

			JSONObject jobj = new JSONObject();
			jobj.put("rows", listAll);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

			// System.out.println(System.currentTimeMillis() - start);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}