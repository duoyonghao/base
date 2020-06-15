package com.kqds.controller.base.scbb;

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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_ScbbAct"})
public class KQDS_ScbbAct
{
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
  
  @RequestMapping({"/toBbAskIndex.act"})
  public ModelAndView toBbAskIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/bbzx/bb_ask_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBbTyIndex.act"})
  public ModelAndView toBbTyIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/bbzx/bb_ty_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBbLsIndex.act"})
  public ModelAndView toBbLsIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/bbzx/bb_ls_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBbGzlIndex.act"})
  public ModelAndView toBbGzlIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.addObject("isyx", Integer.valueOf(1));
    mv.setViewName("/kqdsFront/index/bbzx/bb_gzl_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBbGzlIndex2.act"})
  public ModelAndView toBbGzlIndex2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.addObject("isyx", Integer.valueOf(2));
    mv.setViewName("/kqdsFront/index/bbzx/bb_gzl_index.jsp");
    
    return mv;
  }
  
  @RequestMapping({"/toBbGzlIndex3.act"})
  public ModelAndView toBbGzlIndex3(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.addObject("isyx", Integer.valueOf(3));
    mv.setViewName("/kqdsFront/index/bbzx/bb_gzl_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBbDoctorTj.act"})
  public ModelAndView toBbDoctorTj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/bbzx/bb_doctorTj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBbZzcxIndex.act"})
  public ModelAndView toBbZzcxIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/bbzx/bb_zzcx_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBbHzlyTj.act"})
  public ModelAndView toBbHzlyTj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/bbzx/bb_xxlyTj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBbTubiaoIndex.act"})
  public ModelAndView toBbTubiaoIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/bbzx/bb_tubiao_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_AskCgl.act"})
  public ModelAndView toBb_AskCgl(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_askCgl.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_AskSort.act"})
  public ModelAndView toNetorder(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_askSort.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_DoctorQkTj.act"})
  public ModelAndView toBb_DoctorQkTj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_doctorQkTj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_DoctorYjTj.act"})
  public ModelAndView toBb_DoctorYjTj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_doctorYjTj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_DoctorClcbTj.act"})
  public ModelAndView toBb_DoctorClcbTj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_doctorClcbTj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_WdNetPer.act"})
  public ModelAndView toBb_WdNetPer(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String isyx = request.getParameter("isyx");
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("isyx", isyx);
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_wdNetPer.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_WdNetPerItem.act"})
  public ModelAndView toBb_WdNetPerItem(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String isyx = request.getParameter("isyx");
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("isyx", isyx);
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_wdNetPerItem.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_WdNetItem.act"})
  public ModelAndView toBb_WdNetItem(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String isyx = request.getParameter("isyx");
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("isyx", isyx);
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_wdNetItem.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Ls_Lszxxmtj.act"})
  public ModelAndView toBb_Ls_Lszxxmtj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_ls_lszxxmtj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Ls_Lsxmtj.act"})
  public ModelAndView toBb_Ls_Lsxmtj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_ls_lsxmtj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Ls_Lsxffltj.act"})
  public ModelAndView toBb_Ls_Lsxffltj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_ls_lsxffltj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_RegCjlHigh.act"})
  public ModelAndView toBb_RegCjlHigh(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_regCjlHigh.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_YysmlHigh.act"})
  public ModelAndView toBb_YysmlHigh(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_yysmlHigh.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_RegHigh.act"})
  public ModelAndView toBb_RegHigh(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_regHigh.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_QzHigh.act"})
  public ModelAndView toBb_QzHigh(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_qzHigh.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_QyyjHigh.act"})
  public ModelAndView toBb_QyyjHigh(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_qyyjHigh.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_QylrHigh.act"})
  public ModelAndView toBb_QylrHigh(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_qylrHigh.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Rsktj_Search.act"})
  public ModelAndView toBb_Rsktj_Search(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/jdzx/rsktj_search.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Rsktj_Search_Askperson.act"})
  public ModelAndView toBb_Rsktj_Search_Askperson(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/jdzx/rsktj_search_askperson.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Ty_Yjjcx.act"})
  public ModelAndView toBb_Ty_Yjjcx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_ty_yjjcx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Ty_Hjzlqktj.act"})
  public ModelAndView toBb_Ty_Hjzlqktj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_ty_hjzlqktj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_XxlyNumTj.act"})
  public ModelAndView toBb_XxlyNumTj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_xxlyNumTj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Zhcx.act"})
  public ModelAndView toBb_Zhcx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_zhcx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Sfmx.act"})
  public ModelAndView toBb_Sfmx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Qfcx.act"})
  public ModelAndView toBb_Qfcx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/jdzx/qfcx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Sfmx_all.act"})
  public ModelAndView toBb_Sfmx_all(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx_all.jsp");
    return mv;
  }
  
  @RequestMapping({"/toRsktj_search_wdperson.act"})
  public ModelAndView toRsktj_search_wdperson(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/jdzx/rsktj_search_wdperson.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Sfmx_All_Wdperson_Trcc.act"})
  public ModelAndView toBb_Sfmx_All_Wdperson_Trcc(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    String usercodes = request.getParameter("usercodes");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.addObject("usercodes", usercodes);
    mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx_all_wdperson_trcc.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Sfmx_all_Wdperson.act"})
  public ModelAndView toBb_Sfmx_all_Wdperson(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx_all_wdperson.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Sfmx_all_Askperson.act"})
  public ModelAndView toBb_Sfmx_all_Askperson(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/bbzx/bb_sfmx_all_askperson.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Zxqk.act"})
  public ModelAndView toBb_Zxqk(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
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
  
  @RequestMapping({"/toBb_Xxcx.act"})
  public ModelAndView toBb_Xxcx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    String usercodes = request.getParameter("usercodes");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.addObject("usercodes", usercodes);
    mv.setViewName("/kqdsFront/index/bbzx/bb_xxcx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Trsc.act"})
  public ModelAndView toBb_Trsc(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqIds = request.getParameter("seqIds");
    String table = request.getParameter("table");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqIds", seqIds);
    mv.addObject("table", table);
    mv.setViewName("/kqdsFront/index/bbzx/bb_trsc.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Sjtj.act"})
  public ModelAndView toBb_Sjtj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
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
  
  @RequestMapping({"/toBb_BaseData.act"})
  public ModelAndView toBb_BaseData(HttpServletRequest request, HttpServletResponse response)
  {
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
  
  @RequestMapping({"/toBb_BaseDataDay.act"})
  public ModelAndView toBb_BaseDataDay(HttpServletRequest request, HttpServletResponse response)
  {
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
  
  @RequestMapping({"/toBb_Cjdetaile.act"})
  public ModelAndView toBb_Cjdetaile(HttpServletRequest request, HttpServletResponse response)
  {
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
  
  @RequestMapping({"/toBb_dayTotalData.act"})
  public ModelAndView toBb_dayTotalData(HttpServletRequest request, HttpServletResponse response)
  {
    String timeMonth = request.getParameter("timeMonth");
    ModelAndView mv = new ModelAndView();
    mv.addObject("timeMonth", timeMonth);
    mv.setViewName("/kqdsFront/index/zxtj/dayTotalData.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBb_Totaldetaile.act"})
  public ModelAndView toBb_Totaldetaile(HttpServletRequest request, HttpServletResponse response)
  {
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
  
  @RequestMapping({"/toBb_EmployeeData.act"})
  public ModelAndView toBb_EmployeeData(HttpServletRequest request, HttpServletResponse response)
  {
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
  
  @RequestMapping({"/selectCountBBDept.act"})
  public String selectCountBBDept(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      List<YZDept> list = this.deptLogic.getDeptListByDeptType(ConstUtil.DEPT_TYPE_1, ChainUtil.getOrganizationFromUrl(request));
      List<JSONObject> listAll = new ArrayList();
      for (YZDept dept : list)
      {
        List<JSONObject> listobj = new ArrayList();
        listobj = this.logic.getCountTjDept(TableNameUtil.KQDS_COSTORDER_DETAIL, map, ChainUtil.getOrganizationFromUrl(request), dept.getSeqId(), dept.getDeptName());
        listAll.addAll(listobj);
      }
      String yysr = this.logic.getYysr(TableNameUtil.KQDS_COSTORDER_DETAIL, map, ChainUtil.getOrganizationFromUrl(request));
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      jobj.put("yysr", yysr);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountBB.act"})
  public String selectCountBB(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String depttype = request.getParameter("depttype");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
      {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      }
      else
      {
        map.put("organization", organization);
      }
      BigDecimal all_advance = new BigDecimal(0);
      DecimalFormat df = new DecimalFormat("#.00");
      
      map.put("depttype", depttype);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> listPerson = this.personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);
      

      StringBuffer personIds4QueryBf = new StringBuffer();
      for (JSONObject person : listPerson) {
        personIds4QueryBf.append(person.getString("seqId")).append(",");
      }
      String personIds4Query = YZUtility.ConvertStringIds4Query(personIds4QueryBf.toString());
      map.put("personIds4Query", personIds4Query);
      
      Object listAll = new ArrayList();
      

      List<YZDict> listDict = this.dictLogic.getListByParentCodeALL("GHFL", ChainUtil.getCurrentOrganization(request));
      

      List<JSONObject> listRegAllTotal = this.logic.getListRegByGhfl(map, "", "");
      

      int allnums = listRegAllTotal.size();
      
      int allcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
        String cjstatus = regJson.getString("cjstatus");
        if ("1".equals(cjstatus)) {
          allcjnums++;
        }
      }
      List<JSONObject> skjeList = null;
      Object skjeYjjList = null;
      List<JSONObject> yysrList = null;
      if (depttype.equals(ConstUtil.DEPT_TYPE_0))
      {
        skjeList = this.logic.getYysrList(map);
        skjeYjjList = this.logic.getYysrYjjList(map);
      }
      else if (depttype.equals(ConstUtil.DEPT_TYPE_1))
      {
        yysrList = this.logic.getYysrList(map, ChainUtil.getOrganizationFromUrl(request));
      }
      String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
      
      String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
      
      String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
      
      String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
      
      String qtseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
      for (JSONObject personJson : listPerson)
      {
        String personseqId = personJson.getString("seqId");
        List<JSONObject> listRegFilter = new ArrayList();
        for (JSONObject regJson : listRegAllTotal) {
          if (depttype.equals(ConstUtil.DEPT_TYPE_0))
          {
            if (personseqId.equals(regJson.getString("askperson"))) {
              listRegFilter.add(regJson);
            }
          }
          else if ((depttype.equals(ConstUtil.DEPT_TYPE_1)) && 
            (personseqId.equals(regJson.getString("doctor")))) {
            listRegFilter.add(regJson);
          }
        }
        personJson.put("personReglist", listRegFilter);
      }
      int zxfcjnums;
      for (int i = 0; i < listPerson.size(); i++)
      {
        JSONObject objper = (JSONObject)listPerson.get(i);
        BigDecimal per_advance = new BigDecimal(0);
        
        List<JSONObject> listRegAll = (List)objper.get("personReglist");
        if ((listRegAll != null) && (listRegAll.size() != 0))
        {
          List<JSONObject> jsonListDict = JSONArray.fromObject(listDict);
          for (JSONObject jsonDict : jsonListDict)
          {
            String regsort = jsonDict.getString("seqId");
            List<JSONObject> listRegFilter = new ArrayList();
            for (JSONObject jsonReg : listRegAll) {
              if (jsonReg.getString("regsort").equals(regsort)) {
                listRegFilter.add(jsonReg);
              }
            }
            jsonDict.put("reglist", listRegFilter);
          }
          int fcallnums;
          for (JSONObject dict : jsonListDict)
          {
            JSONObject obj = new JSONObject();
            
            obj.put("username", objper.getString("userName"));
            
            obj.put("zxxm", dict.getString("dictName"));
            
            List<JSONObject> list = (List)dict.get("reglist");
            
            int zxnums = list.size();
            obj.put("zxnums", Integer.valueOf(zxnums));
            
            Float zzlzb = Float.valueOf(0.0F);
            if (allnums != 0) {
              zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
            }
            obj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
            
            int cjnums = 0;
            int wcjnums = 0;
            int czallnums = 0;
            int czcjnums = 0;
            int fzallnums = 0;
            int fzcjnums = 0;
            int zxfallnums = 0;
            int zxfcjnums = 0;
            fcallnums = 0;
            int fccjnums = 0;
            int qtallnums = 0;
            int qtcjnums = 0;
            for (JSONObject regJson : list)
            {
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
              if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                czcjnums++;
              }
              if (fzseqId.equals(recesort)) {
                fzallnums++;
              }
              if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                fzcjnums++;
              }
              if (zxfseqId.equals(recesort)) {
                zxfallnums++;
              }
              if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                zxfcjnums++;
              }
              if (fcseqId.equals(recesort)) {
                fcallnums++;
              }
              if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                fccjnums++;
              }
              if (qtseqId.equals(recesort)) {
                qtallnums++;
              }
              if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                qtcjnums++;
              }
            }
            obj.put("cjnums", Integer.valueOf(cjnums));
            
            Float cgl = Float.valueOf(0.0F);
            if (zxnums != 0) {
              cgl = Float.valueOf(cjnums * 100.0F / zxnums);
            }
            obj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
            
            Float cglzb = Float.valueOf(0.0F);
            if (allcjnums != 0) {
              cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
            }
            obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
            

            obj.put("wcjnums", Integer.valueOf(wcjnums));
            

            obj.put("czallnums", Integer.valueOf(czallnums));
            
            obj.put("czcjnums", Integer.valueOf(czcjnums));
            

            obj.put("fzallnums", Integer.valueOf(fzallnums));
            
            obj.put("fzcjnums", Integer.valueOf(fzcjnums));
            

            obj.put("zxfallnums", Integer.valueOf(zxfallnums));
            
            obj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
            

            obj.put("fcallnums", Integer.valueOf(fcallnums));
            
            obj.put("fccjnums", Integer.valueOf(fccjnums));
            
            obj.put("qtallnums", Integer.valueOf(qtallnums));
            
            obj.put("qtcjnums", Integer.valueOf(qtcjnums));
            
            String sk = "0.00";
            if (depttype.equals(ConstUtil.DEPT_TYPE_0))
            {
              sk = getskje4AskPerson(skjeList, (List)skjeYjjList, objper.getString("seqId"), dict.getString("seqId"));
              String ss = getskje4AskPersonSS(skjeList, (List)skjeYjjList, objper.getString("seqId"), dict.getString("seqId"));
              obj.put("ssje", ss);
            }
            else if (depttype.equals(ConstUtil.DEPT_TYPE_1))
            {
              sk = getskje4Doctor(yysrList, objper.getString("seqId"), dict.getString("seqId"));
            }
            obj.put("skje", sk);
            if ((zxnums != 0) || (KqdsBigDecimal.compareTo(new BigDecimal(sk), BigDecimal.ZERO) != 0))
            {
              BigDecimal singleAdvance = new BigDecimal(0);
              for (JSONObject regnojson : list)
              {
                Map perMap = new HashMap();
                perMap.put("starttime", starttime);
                perMap.put("endtime", endtime);
                perMap.put("createuser", objper.getString("seq_id"));
                perMap.put("regno", regnojson.getString("seq_id"));
                BigDecimal regAdvance = this.logic.getPerAdvance(perMap);
                singleAdvance = singleAdvance.add(regAdvance);
              }
              per_advance = per_advance.add(singleAdvance);
              obj.put("totalAdvance", singleAdvance.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : df.format(singleAdvance.setScale(2, 4)));
              ((List)listAll).add(obj);
            }
          }
          JSONObject objhj = new JSONObject();
          
          int cjnums = 0;
          int wcjnums = 0;
          int czallnums = 0;
          int czcjnums = 0;
          int fzallnums = 0;
          int fzcjnums = 0;
          int zxfallnums = 0;
          zxfcjnums = 0;
          int fcallnums = 0;
          int fccjnums = 0;
          int qtallnums = 0;
          int qtcjnums = 0;
          for (JSONObject regJson : listRegAll)
          {
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
            if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              czcjnums++;
            }
            if (fzseqId.equals(recesort)) {
              fzallnums++;
            }
            if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              fzcjnums++;
            }
            if (zxfseqId.equals(recesort)) {
              zxfallnums++;
            }
            if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              zxfcjnums++;
            }
            if (fcseqId.equals(recesort)) {
              fcallnums++;
            }
            if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              fccjnums++;
            }
            if (qtseqId.equals(recesort)) {
              qtallnums++;
            }
            if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              qtcjnums++;
            }
          }
          int zxnums = listRegAll.size();
          objhj.put("zxnums", Integer.valueOf(zxnums));
          
          Float zzlzb = Float.valueOf(0.0F);
          if (allnums != 0) {
            zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
          }
          objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
          
          objhj.put("cjnums", Integer.valueOf(cjnums));
          
          Float cgl = Float.valueOf(0.0F);
          if (zxnums != 0) {
            cgl = Float.valueOf(cjnums * 100.0F / zxnums);
          }
          objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
          
          Float cglzb = Float.valueOf(0.0F);
          if (allcjnums != 0) {
            cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
          }
          objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
          
          objhj.put("wcjnums", Integer.valueOf(wcjnums));
          

          objhj.put("czallnums", Integer.valueOf(czallnums));
          
          objhj.put("czcjnums", Integer.valueOf(czcjnums));
          

          objhj.put("fzallnums", Integer.valueOf(fzallnums));
          
          objhj.put("fzcjnums", Integer.valueOf(fzcjnums));
          

          objhj.put("zxfallnums", Integer.valueOf(zxfallnums));
          
          objhj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
          

          objhj.put("fcallnums", Integer.valueOf(fcallnums));
          

          objhj.put("fccjnums", Integer.valueOf(fccjnums));
          
          objhj.put("qtallnums", Integer.valueOf(qtallnums));
          
          objhj.put("qtcjnums", Integer.valueOf(qtcjnums));
          
          String sk = "0.00";
          if (depttype.equals(ConstUtil.DEPT_TYPE_0))
          {
            sk = getskje4AskPerson(skjeList, (List)skjeYjjList, objper.getString("seqId"), null);
            String ss = getskje4AskPersonSS(skjeList, (List)skjeYjjList, objper.getString("seqId"), null);
            objhj.put("ssje", ss);
          }
          else if (depttype.equals(ConstUtil.DEPT_TYPE_1))
          {
            sk = getskje4Doctor(yysrList, objper.getString("seqId"), null);
          }
          objhj.put("skje", sk);
          if ((zxnums != 0) || (KqdsBigDecimal.compareTo(new BigDecimal(sk), BigDecimal.ZERO) != 0))
          {
            all_advance = all_advance.add(per_advance);
            objhj.put("totalAdvance", per_advance.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : df.format(per_advance.setScale(2, 4)));
            ((List)listAll).add(objhj);
          }
        }
      }
      int cjnums = 0;
      int wcjnums = 0;
      int czallnums = 0;
      int czcjnums = 0;
      int fzallnums = 0;
      int fzcjnums = 0;
      int zxfallnums = 0;
      int zxfcjnums = 0;
      int fcallnums = 0;
      int qtallnums = 0;
      int fccjnums = 0;
      int qtcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
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
        if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          czcjnums++;
        }
        if (fzseqId.equals(recesort)) {
          fzallnums++;
        }
        if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fzcjnums++;
        }
        if (zxfseqId.equals(recesort)) {
          zxfallnums++;
        }
        if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          zxfcjnums++;
        }
        if (fcseqId.equals(recesort)) {
          fcallnums++;
        }
        if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fccjnums++;
        }
        if (qtseqId.equals(recesort)) {
          qtallnums++;
        }
        if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          qtcjnums++;
        }
      }
      JSONObject objzj = new JSONObject();
      
      int zxnums = listRegAllTotal.size();
      objzj.put("zxnums", Integer.valueOf(zxnums));
      
      Float zzlzb = Float.valueOf(0.0F);
      if (allnums != 0) {
        zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
      }
      objzj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
      
      objzj.put("cjnums", Integer.valueOf(cjnums));
      
      Float cgl = Float.valueOf(0.0F);
      if (zxnums != 0) {
        cgl = Float.valueOf(cjnums * 100.0F / zxnums);
      }
      objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
      
      Float cglzb = Float.valueOf(0.0F);
      if (allcjnums != 0) {
        cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
      }
      objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
      
      objzj.put("wcjnums", Integer.valueOf(wcjnums));
      

      objzj.put("czallnums", Integer.valueOf(czallnums));
      
      objzj.put("czcjnums", Integer.valueOf(czcjnums));
      

      objzj.put("fzallnums", Integer.valueOf(fzallnums));
      
      objzj.put("fzcjnums", Integer.valueOf(fzcjnums));
      

      objzj.put("zxfallnums", Integer.valueOf(zxfallnums));
      
      objzj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
      

      objzj.put("fcallnums", Integer.valueOf(fcallnums));
      

      objzj.put("fccjnums", Integer.valueOf(fccjnums));
      
      objzj.put("qtallnums", Integer.valueOf(qtallnums));
      
      objzj.put("qtcjnums", Integer.valueOf(qtcjnums));
      
      String sk = "0.00";
      if (depttype.equals(ConstUtil.DEPT_TYPE_0))
      {
        sk = getskje4AskPerson(skjeList, (List)skjeYjjList, null, null);
        String ss = getskje4AskPersonSS(skjeList, (List)skjeYjjList, null, null);
        objzj.put("ssje", ss);
      }
      else if (depttype.equals(ConstUtil.DEPT_TYPE_1))
      {
        sk = getskje4Doctor(yysrList, null, null);
      }
      objzj.put("skje", sk);
      if (zxnums > 0)
      {
        if (all_advance.equals(BigDecimal.ZERO)) {
          objzj.put("totalAdvance", BigDecimal.ZERO);
        } else {
          objzj.put("totalAdvance", df.format(all_advance.setScale(2, 4)));
        }
        ((List)listAll).add(objzj);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountDoctorBB.act"})
  public String selectCountDoctorBB(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      

      String qxname = request.getParameter("qxname");
      
      String deptCategory = request.getParameter("deptCategory");
      
      String personId = request.getParameter("recesort");
      

      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(qxname)) {
        map.put("buttonName", qxname);
      }
      if (!YZUtility.isNullorEmpty(deptCategory)) {
        map.put("deptCategory", deptCategory);
      }
      if (!YZUtility.isNullorEmpty(personId)) {
        map.put("personId", personId);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
      {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      }
      else
      {
        map.put("organization", organization);
      }
      List<JSONObject> listAll = this.logic.findDoctorSituationByTime(request, map);
      

      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/consultingStatistics.act"})
  public String consultingStatistics(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String depttype = request.getParameter("depttype");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
      {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      }
      else
      {
        map.put("organization", organization);
      }
      BigDecimal all_advance = new BigDecimal(0);
      DecimalFormat df = new DecimalFormat("#.00");
      
      map.put("depttype", depttype);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> listPerson = this.personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);
      

      StringBuffer personIds4QueryBf = new StringBuffer();
      for (JSONObject person : listPerson) {
        personIds4QueryBf.append(person.getString("seqId")).append(",");
      }
      String personIds4Query = YZUtility.ConvertStringIds4Query(personIds4QueryBf.toString());
      map.put("personIds4Query", personIds4Query);
      
      Object listAll = new ArrayList();
      

      List<YZDict> listDict = this.dictLogic.getListByParentCodeALL("GHFL", ChainUtil.getCurrentOrganization(request));
      

      List<JSONObject> listRegAllTotal = this.logic.getListRegByGhfl(map, "", "");
      

      int allnums = listRegAllTotal.size();
      
      int allcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
        String cjstatus = regJson.getString("cjstatus");
        if ("1".equals(cjstatus)) {
          allcjnums++;
        }
      }
      List<JSONObject> skjeList = null;
      Object skjeYjjList = null;
      List<JSONObject> yysrList = null;
      if (depttype.equals(ConstUtil.DEPT_TYPE_0))
      {
        skjeList = this.logic.getYysrList(map);
        skjeYjjList = this.logic.getYysrYjjList(map);
      }
      else if (depttype.equals(ConstUtil.DEPT_TYPE_1))
      {
        yysrList = this.logic.getYysrList(map, ChainUtil.getOrganizationFromUrl(request));
      }
      String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
      
      String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
      
      String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
      
      String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
      
      String qtseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
      for (JSONObject personJson : listPerson)
      {
        String personseqId = personJson.getString("seqId");
        List<JSONObject> listRegFilter = new ArrayList();
        for (JSONObject regJson : listRegAllTotal) {
          if (depttype.equals(ConstUtil.DEPT_TYPE_0))
          {
            if (personseqId.equals(regJson.getString("askperson"))) {
              listRegFilter.add(regJson);
            }
          }
          else if ((depttype.equals(ConstUtil.DEPT_TYPE_1)) && 
            (personseqId.equals(regJson.getString("doctor")))) {
            listRegFilter.add(regJson);
          }
        }
        personJson.put("personReglist", listRegFilter);
      }
      int zxfcjnums;
      for (int i = 0; i < listPerson.size(); i++)
      {
        JSONObject objper = (JSONObject)listPerson.get(i);
        BigDecimal per_advance = new BigDecimal(0);
        
        List<JSONObject> listRegAll = (List)objper.get("personReglist");
        if ((listRegAll != null) && (listRegAll.size() != 0))
        {
          List<JSONObject> jsonListDict = JSONArray.fromObject(listDict);
          for (JSONObject jsonDict : jsonListDict)
          {
            String regsort = jsonDict.getString("seqId");
            List<JSONObject> listRegFilter = new ArrayList();
            for (JSONObject jsonReg : listRegAll) {
              if (jsonReg.getString("regsort").equals(regsort)) {
                listRegFilter.add(jsonReg);
              }
            }
            jsonDict.put("reglist", listRegFilter);
          }
          int fcallnums;
          for (JSONObject dict : jsonListDict)
          {
            JSONObject obj = new JSONObject();
            
            obj.put("username", objper.getString("userName"));
            
            obj.put("zxxm", dict.getString("dictName"));
            
            List<JSONObject> list = (List)dict.get("reglist");
            
            int zxnums = list.size();
            obj.put("zxnums", Integer.valueOf(zxnums));
            
            Float zzlzb = Float.valueOf(0.0F);
            if (allnums != 0) {
              zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
            }
            obj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
            
            int cjnums = 0;
            int wcjnums = 0;
            int czallnums = 0;
            int czcjnums = 0;
            int fzallnums = 0;
            int fzcjnums = 0;
            int zxfallnums = 0;
            int zxfcjnums = 0;
            fcallnums = 0;
            int fccjnums = 0;
            int qtallnums = 0;
            int qtcjnums = 0;
            for (JSONObject regJson : list)
            {
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
              if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                czcjnums++;
              }
              if (fzseqId.equals(recesort)) {
                fzallnums++;
              }
              if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                fzcjnums++;
              }
              if (zxfseqId.equals(recesort)) {
                zxfallnums++;
              }
              if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                zxfcjnums++;
              }
              if (fcseqId.equals(recesort)) {
                fcallnums++;
              }
              if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                fccjnums++;
              }
              if (qtseqId.equals(recesort)) {
                qtallnums++;
              }
              if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                qtcjnums++;
              }
            }
            obj.put("cjnums", Integer.valueOf(cjnums));
            
            Float cgl = Float.valueOf(0.0F);
            if (zxnums != 0) {
              cgl = Float.valueOf(cjnums * 100.0F / zxnums);
            }
            obj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
            
            Float cglzb = Float.valueOf(0.0F);
            if (allcjnums != 0) {
              cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
            }
            obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
            

            obj.put("wcjnums", Integer.valueOf(wcjnums));
            

            obj.put("czallnums", Integer.valueOf(czallnums));
            
            obj.put("czcjnums", Integer.valueOf(czcjnums));
            

            obj.put("fzallnums", Integer.valueOf(fzallnums));
            
            obj.put("fzcjnums", Integer.valueOf(fzcjnums));
            

            obj.put("zxfallnums", Integer.valueOf(zxfallnums));
            
            obj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
            

            obj.put("fcallnums", Integer.valueOf(fcallnums));
            
            obj.put("fccjnums", Integer.valueOf(fccjnums));
            
            obj.put("qtallnums", Integer.valueOf(qtallnums));
            
            obj.put("qtcjnums", Integer.valueOf(qtcjnums));
            
            String sk = "0.00";
            if (depttype.equals(ConstUtil.DEPT_TYPE_0))
            {
              sk = getskje4AskPerson(skjeList, (List)skjeYjjList, objper.getString("seqId"), dict.getString("seqId"));
              String ss = getskje4AskPersonSS(skjeList, (List)skjeYjjList, objper.getString("seqId"), dict.getString("seqId"));
              obj.put("ssje", ss);
            }
            else if (depttype.equals(ConstUtil.DEPT_TYPE_1))
            {
              sk = getskje4Doctor(yysrList, objper.getString("seqId"), dict.getString("seqId"));
            }
            obj.put("skje", sk);
            if ((zxnums != 0) || (KqdsBigDecimal.compareTo(new BigDecimal(sk), BigDecimal.ZERO) != 0))
            {
              BigDecimal singleAdvance = new BigDecimal(0);
              for (JSONObject regnojson : list)
              {
                Map perMap = new HashMap();
                perMap.put("starttime", starttime);
                perMap.put("endtime", endtime);
                perMap.put("createuser", objper.getString("seq_id"));
                perMap.put("regno", regnojson.getString("seq_id"));
                BigDecimal regAdvance = this.logic.getPerAdvance(perMap);
                singleAdvance = singleAdvance.add(regAdvance);
              }
              per_advance = per_advance.add(singleAdvance);
              obj.put("totalAdvance", singleAdvance.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : df.format(singleAdvance.setScale(2, 4)));
              ((List)listAll).add(obj);
            }
          }
          JSONObject objhj = new JSONObject();
          
          int cjnums = 0;
          int wcjnums = 0;
          int czallnums = 0;
          int czcjnums = 0;
          int fzallnums = 0;
          int fzcjnums = 0;
          int zxfallnums = 0;
          zxfcjnums = 0;
          int fcallnums = 0;
          int fccjnums = 0;
          int qtallnums = 0;
          int qtcjnums = 0;
          for (JSONObject regJson : listRegAll)
          {
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
            if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              czcjnums++;
            }
            if (fzseqId.equals(recesort)) {
              fzallnums++;
            }
            if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              fzcjnums++;
            }
            if (zxfseqId.equals(recesort)) {
              zxfallnums++;
            }
            if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              zxfcjnums++;
            }
            if (fcseqId.equals(recesort)) {
              fcallnums++;
            }
            if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              fccjnums++;
            }
            if (qtseqId.equals(recesort)) {
              qtallnums++;
            }
            if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              qtcjnums++;
            }
          }
          int zxnums = listRegAll.size();
          objhj.put("zxnums", Integer.valueOf(zxnums));
          
          Float zzlzb = Float.valueOf(0.0F);
          if (allnums != 0) {
            zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
          }
          objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
          
          objhj.put("cjnums", Integer.valueOf(cjnums));
          
          Float cgl = Float.valueOf(0.0F);
          if (zxnums != 0) {
            cgl = Float.valueOf(cjnums * 100.0F / zxnums);
          }
          objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
          
          Float cglzb = Float.valueOf(0.0F);
          if (allcjnums != 0) {
            cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
          }
          objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
          
          objhj.put("wcjnums", Integer.valueOf(wcjnums));
          

          objhj.put("czallnums", Integer.valueOf(czallnums));
          
          objhj.put("czcjnums", Integer.valueOf(czcjnums));
          

          objhj.put("fzallnums", Integer.valueOf(fzallnums));
          
          objhj.put("fzcjnums", Integer.valueOf(fzcjnums));
          

          objhj.put("zxfallnums", Integer.valueOf(zxfallnums));
          
          objhj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
          

          objhj.put("fcallnums", Integer.valueOf(fcallnums));
          

          objhj.put("fccjnums", Integer.valueOf(fccjnums));
          
          objhj.put("qtallnums", Integer.valueOf(qtallnums));
          
          objhj.put("qtcjnums", Integer.valueOf(qtcjnums));
          
          String sk = "0.00";
          if (depttype.equals(ConstUtil.DEPT_TYPE_0))
          {
            sk = getskje4AskPerson(skjeList, (List)skjeYjjList, objper.getString("seqId"), null);
            String ss = getskje4AskPersonSS(skjeList, (List)skjeYjjList, objper.getString("seqId"), null);
            objhj.put("ssje", ss);
          }
          else if (depttype.equals(ConstUtil.DEPT_TYPE_1))
          {
            sk = getskje4Doctor(yysrList, objper.getString("seqId"), null);
          }
          objhj.put("skje", sk);
          if ((zxnums != 0) || (KqdsBigDecimal.compareTo(new BigDecimal(sk), BigDecimal.ZERO) != 0))
          {
            all_advance = all_advance.add(per_advance);
            objhj.put("totalAdvance", per_advance.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : df.format(per_advance.setScale(2, 4)));
            ((List)listAll).add(objhj);
          }
        }
      }
      int cjnums = 0;
      int wcjnums = 0;
      int czallnums = 0;
      int czcjnums = 0;
      int fzallnums = 0;
      int fzcjnums = 0;
      int zxfallnums = 0;
      int zxfcjnums = 0;
      int fcallnums = 0;
      int qtallnums = 0;
      int fccjnums = 0;
      int qtcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
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
        if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          czcjnums++;
        }
        if (fzseqId.equals(recesort)) {
          fzallnums++;
        }
        if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fzcjnums++;
        }
        if (zxfseqId.equals(recesort)) {
          zxfallnums++;
        }
        if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          zxfcjnums++;
        }
        if (fcseqId.equals(recesort)) {
          fcallnums++;
        }
        if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fccjnums++;
        }
        if (qtseqId.equals(recesort)) {
          qtallnums++;
        }
        if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          qtcjnums++;
        }
      }
      JSONObject objzj = new JSONObject();
      
      int zxnums = listRegAllTotal.size();
      objzj.put("zxnums", Integer.valueOf(zxnums));
      
      Float zzlzb = Float.valueOf(0.0F);
      if (allnums != 0) {
        zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
      }
      objzj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
      
      objzj.put("cjnums", Integer.valueOf(cjnums));
      
      Float cgl = Float.valueOf(0.0F);
      if (zxnums != 0) {
        cgl = Float.valueOf(cjnums * 100.0F / zxnums);
      }
      objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
      
      Float cglzb = Float.valueOf(0.0F);
      if (allcjnums != 0) {
        cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
      }
      objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
      
      objzj.put("wcjnums", Integer.valueOf(wcjnums));
      

      objzj.put("czallnums", Integer.valueOf(czallnums));
      
      objzj.put("czcjnums", Integer.valueOf(czcjnums));
      

      objzj.put("fzallnums", Integer.valueOf(fzallnums));
      
      objzj.put("fzcjnums", Integer.valueOf(fzcjnums));
      

      objzj.put("zxfallnums", Integer.valueOf(zxfallnums));
      
      objzj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
      

      objzj.put("fcallnums", Integer.valueOf(fcallnums));
      

      objzj.put("fccjnums", Integer.valueOf(fccjnums));
      
      objzj.put("qtallnums", Integer.valueOf(qtallnums));
      
      objzj.put("qtcjnums", Integer.valueOf(qtcjnums));
      
      String sk = "0.00";
      if (depttype.equals(ConstUtil.DEPT_TYPE_0))
      {
        sk = getskje4AskPerson(skjeList, (List)skjeYjjList, null, null);
        String ss = getskje4AskPersonSS(skjeList, (List)skjeYjjList, null, null);
        objzj.put("ssje", ss);
      }
      else if (depttype.equals(ConstUtil.DEPT_TYPE_1))
      {
        sk = getskje4Doctor(yysrList, null, null);
      }
      objzj.put("skje", sk);
      if (zxnums > 0)
      {
        if (all_advance.equals(BigDecimal.ZERO)) {
          objzj.put("totalAdvance", BigDecimal.ZERO);
        } else {
          objzj.put("totalAdvance", df.format(all_advance.setScale(2, 4)));
        }
        ((List)listAll).add(objzj);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountYj.act"})
  public String selectCountYj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String depttype = request.getParameter("depttype");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
      {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      }
      else
      {
        map.put("organization", organization);
      }
      map.put("depttype", depttype);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> listPerson = this.personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);
      

      StringBuffer personIds4QueryBf = new StringBuffer();
      for (JSONObject person : listPerson) {
        personIds4QueryBf.append(person.getString("seqId")).append(",");
      }
      String personIds4Query = YZUtility.ConvertStringIds4Query(personIds4QueryBf.toString());
      map.put("personIds4Query", personIds4Query);
      
      Object costdetailList = null;
      if (depttype.equals(ConstUtil.DEPT_TYPE_0)) {
        costdetailList = this.logic.getYysrDetailList(map);
      } else if (depttype.equals(ConstUtil.DEPT_TYPE_1)) {
        costdetailList = this.logic.getYysrDetailList(map, ChainUtil.getOrganizationFromUrl(request));
      }
      List<JSONObject> listAll = new ArrayList();
      
      BigDecimal zj_ysje = BigDecimal.ZERO;
      BigDecimal zj_ssje = BigDecimal.ZERO;
      for (int i = 0; i < listPerson.size(); i++)
      {
        BigDecimal hj_ysje = BigDecimal.ZERO;
        BigDecimal hj_ssje = BigDecimal.ZERO;
        JSONObject objper = (JSONObject)listPerson.get(i);
        
        List<JSONObject> listPersonDetail = new ArrayList();
        for (JSONObject jsonDetail : (List)costdetailList)
        {
          String perid = objper.getString("seqId");
          if (jsonDetail.getString("perid").equals(perid)) {
            listPersonDetail.add(jsonDetail);
          }
        }
        if (listPersonDetail.size() != 0)
        {
          Set<String> currSortSet = new HashSet();
          Object sortNameMap = new HashMap();
          for (JSONObject perDetail : listPersonDetail)
          {
            String basetype = perDetail.getString("basetype");
            String nexttype = perDetail.getString("nexttype");
            String base_next = basetype + "," + nexttype;
            currSortSet.add(base_next);
            
            ((Map)sortNameMap).put(basetype, perDetail.getString("basename"));
            ((Map)sortNameMap).put(nexttype, perDetail.getString("nextname"));
          }
          for (String base_next : currSortSet) {
            if (!YZUtility.isNullorEmpty(base_next))
            {
              String[] sortArray = base_next.split(",");
              if (sortArray.length == 2)
              {
                String basetype = sortArray[0];
                String nexttype = sortArray[1];
                if ((!YZUtility.isNullorEmpty(basetype)) && (!YZUtility.isNullorEmpty(nexttype)))
                {
                  JSONObject obj = new JSONObject();
                  
                  obj.put("username", objper.getString("userName"));
                  
                  obj.put("basename", ((Map)sortNameMap).get(basetype));
                  
                  obj.put("nextname", ((Map)sortNameMap).get(nexttype));
                  
                  BigDecimal ysje = BigDecimal.ZERO;
                  BigDecimal ssje = BigDecimal.ZERO;
                  for (JSONObject perDetail : listPersonDetail)
                  {
                    String basetype2 = perDetail.getString("basetype");
                    String nexttype2 = perDetail.getString("nexttype");
                    if ((basetype.equals(basetype2)) && (nexttype.equals(nexttype2)))
                    {
                      String subtotal = perDetail.getString("subtotal");
                      String voidmoney = perDetail.getString("voidmoney");
                      BigDecimal ys = new BigDecimal(subtotal).subtract(new BigDecimal(voidmoney));
                      ysje = ysje.add(KqdsBigDecimal.round(ys, 2));
                      
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
              }
            }
          }
          JSONObject objhj = new JSONObject();
          objhj.put("ysje", KqdsBigDecimal.round(hj_ysje, 2));
          objhj.put("ssje", KqdsBigDecimal.round(hj_ssje, 2));
          listAll.add(objhj);
          
          zj_ysje = zj_ysje.add(hj_ysje);
          zj_ssje = zj_ssje.add(hj_ssje);
        }
      }
      if (((List)costdetailList).size() > 0)
      {
        JSONObject objzj = new JSONObject();
        objzj.put("ysje", KqdsBigDecimal.round(zj_ysje, 2));
        objzj.put("ssje", KqdsBigDecimal.round(zj_ssje, 2));
        listAll.add(objzj);
      }
      List<JSONObject> listAll1 = new ArrayList();
      JSONObject jso;
      for (int i = 0; i < listAll.size(); i++) {
        if (((JSONObject)listAll.get(i)).size() > 2)
        {
          int a = 0;
          int h = 0;
          for (int j = 0; j < listAll1.size(); j++) {
            if (((JSONObject)listAll.get(i)).getString("basename").equals(((JSONObject)listAll1.get(j)).getString("basename")))
            {
              a = 1;
              h = j;
            }
          }
          if (a != 1)
          {
            jso = new JSONObject();
            jso.put("basename", ((JSONObject)listAll.get(i)).getString("basename"));
            jso.put("ssje", ((JSONObject)listAll.get(i)).getString("ssje"));
            listAll1.add(jso);
          }
          else
          {
            ((JSONObject)listAll1.get(h)).put("ssje", new BigDecimal(((JSONObject)listAll1.get(h)).getString("ssje")).add(new BigDecimal(((JSONObject)listAll.get(i)).getString("ssje"))).toString());
          }
        }
      }
      List<JSONObject> pluslist = new ArrayList();
      List<JSONObject> minuslist = new ArrayList();
      for (JSONObject jsonObject : listAll1) {
        if ((new BigDecimal(jsonObject.getString("ssje")).compareTo(new BigDecimal("0")) == 1) || (new BigDecimal(jsonObject.getString("ssje")).compareTo(new BigDecimal("0")) == 0)) {
          pluslist.add(jsonObject);
        } else {
          minuslist.add(jsonObject);
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      jobj.put("plus", pluslist);
      jobj.put("minus", minuslist);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountDoctorYj.act"})
  public String selectCountDoctorYj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      

      String qxname = request.getParameter("qxname");
      
      String deptCategory = request.getParameter("deptCategory");
      
      String personId = request.getParameter("recesort");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(qxname)) {
        map.put("buttonName", qxname);
      }
      if (!YZUtility.isNullorEmpty(deptCategory)) {
        map.put("deptCategory", deptCategory);
      }
      if (!YZUtility.isNullorEmpty(personId)) {
        map.put("personId", personId);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
      {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      }
      else
      {
        map.put("organization", organization);
      }
      List<JSONObject> listAll = this.logic.findDoctorPerformanceByTime(request, map);
      List<JSONObject> pluslist = new ArrayList();
      List<JSONObject> minuslist = new ArrayList();
      for (JSONObject jsonObject : listAll) {
        if (jsonObject.size() > 1) {
          if ((new BigDecimal(jsonObject.getString("ssje")).compareTo(new BigDecimal("0")) == 1) || (new BigDecimal(jsonObject.getString("ssje")).compareTo(new BigDecimal("0")) == 0)) {
            pluslist.add(jsonObject);
          } else {
            minuslist.add(jsonObject);
          }
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      jobj.put("plus", pluslist);
      jobj.put("minus", minuslist);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountClcb.act"})
  public String selectCountClcb(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String depttype = request.getParameter("depttype");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
      {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      }
      else
      {
        map.put("organization", organization);
      }
      map.put("depttype", depttype);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> listPerson = this.personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);
      

      StringBuffer personIds4QueryBf = new StringBuffer();
      for (JSONObject person : listPerson) {
        personIds4QueryBf.append(person.getString("seqId")).append(",");
      }
      String personIds4Query = YZUtility.ConvertStringIds4Query(personIds4QueryBf.toString());
      map.put("personIds4Query", personIds4Query);
      
      Object costdetailList = null;
      
      List<JSONObject> jgdetailList = null;
      
      List<JSONObject> ckList = null;
      







      costdetailList = this.logic.getYysrDetailList(map, ChainUtil.getOrganizationFromUrl(request));
      jgdetailList = this.logic.getJgdetailList(map, ChainUtil.getOrganizationFromUrl(request));
      ckList = this.logic.getCkDetailList(map, ChainUtil.getOrganizationFromUrl(request));
      List<JSONObject> listAll = new ArrayList();
      
      BigDecimal zj_ysje = BigDecimal.ZERO;
      BigDecimal zj_ssje = BigDecimal.ZERO;
      BigDecimal zj_jgje = BigDecimal.ZERO;
      BigDecimal zj_ckje = BigDecimal.ZERO;
      for (int i = 0; i < listPerson.size(); i++)
      {
        JSONObject objper = (JSONObject)listPerson.get(i);
        
        List<JSONObject> listPersonCostDetail = new ArrayList();
        String perid;
        for (JSONObject jsonDetail : (List)costdetailList)
        {
          perid = objper.getString("seqId");
          if (jsonDetail.getString("perid").equals(perid)) {
            listPersonCostDetail.add(jsonDetail);
          }
        }
        List<JSONObject> listPersonjgdetailList = new ArrayList();
        String perid;
        for (JSONObject jsonDetail : jgdetailList)
        {
          perid = objper.getString("seqId");
          if (jsonDetail.getString("createuser").equals(perid)) {
            listPersonjgdetailList.add(jsonDetail);
          }
        }
        Object listPersonckdetailList = new ArrayList();
        for (JSONObject jsonDetail : ckList)
        {
          String perid = objper.getString("seqId");
          if (jsonDetail.getString("sqdoctor").equals(perid)) {
            ((List)listPersonckdetailList).add(jsonDetail);
          }
        }
        if ((listPersonCostDetail.size() != 0) || (listPersonjgdetailList.size() != 0) || (((List)listPersonckdetailList).size() != 0))
        {
          JSONObject obj = new JSONObject();
          
          obj.put("username", objper.getString("userName"));
          
          BigDecimal ysje = BigDecimal.ZERO;
          BigDecimal ssje = BigDecimal.ZERO;
          BigDecimal jgje = BigDecimal.ZERO;
          BigDecimal ckje = BigDecimal.ZERO;
          for (JSONObject perDetail : listPersonCostDetail)
          {
            String subtotal = perDetail.getString("subtotal");
            String voidmoney = perDetail.getString("voidmoney");
            BigDecimal ys = new BigDecimal(subtotal).subtract(new BigDecimal(voidmoney));
            ysje = ysje.add(KqdsBigDecimal.round(ys, 2));
            
            String paymoney = perDetail.getString("paymoney");
            String payother2 = perDetail.getString("payother2");
            String paydjq = perDetail.getString("paydjq");
            String payintegral = perDetail.getString("payintegral");
            BigDecimal ss = new BigDecimal(paymoney).subtract(new BigDecimal(payother2)).subtract(new BigDecimal(paydjq)).subtract(new BigDecimal(payintegral));
            ssje = ssje.add(KqdsBigDecimal.round(ss, 2));
          }
          for (JSONObject perDetail : listPersonjgdetailList) {
            if (YZUtility.isNotNullOrEmpty(perDetail.getString("subtotal")))
            {
              BigDecimal subtotal = new BigDecimal(perDetail.getString("subtotal"));
              jgje = jgje.add(KqdsBigDecimal.round(subtotal, 2));
            }
          }
          for (JSONObject perDetail : (List)listPersonckdetailList) {
            if (YZUtility.isNotNullOrEmpty(perDetail.getString("subtotal")))
            {
              BigDecimal subtotal = new BigDecimal(perDetail.getString("subtotal"));
              ckje = ckje.add(KqdsBigDecimal.round(subtotal, 2));
            }
          }
          obj.put("ysje", ysje);
          obj.put("ssje", ssje);
          obj.put("jgje", jgje);
          
          BigDecimal jgzb = BigDecimal.ZERO;
          if (ssje.compareTo(BigDecimal.ZERO) != 0) {
            jgzb = jgje.multiply(new BigDecimal(100)).divide(ssje, 2, 4);
          }
          obj.put("jgzb", jgzb + "%");
          obj.put("ckje", ckje);
          
          BigDecimal ckzb = BigDecimal.ZERO;
          if (ssje.compareTo(BigDecimal.ZERO) != 0) {
            ckzb = ckje.multiply(new BigDecimal(100)).divide(ssje, 2, 4);
          }
          obj.put("ckzb", ckzb + "%");
          listAll.add(obj);
          zj_ysje = zj_ysje.add(ysje);
          zj_ssje = zj_ssje.add(ssje);
          zj_jgje = zj_jgje.add(jgje);
          zj_ckje = zj_ckje.add(ckje);
        }
      }
      if ((((List)costdetailList).size() > 0) || (jgdetailList.size() > 0) || (ckList.size() > 0))
      {
        JSONObject objzj = new JSONObject();
        objzj.put("ysje", KqdsBigDecimal.round(zj_ysje, 2));
        objzj.put("ssje", KqdsBigDecimal.round(zj_ssje, 2));
        objzj.put("jgje", KqdsBigDecimal.round(zj_jgje, 2));
        
        BigDecimal jgzbAll = BigDecimal.ZERO;
        if (zj_ssje.compareTo(BigDecimal.ZERO) != 0) {
          jgzbAll = zj_jgje.multiply(new BigDecimal(100)).divide(zj_ssje, 2, 4);
        }
        objzj.put("jgzb", jgzbAll + "%");
        objzj.put("ckje", KqdsBigDecimal.round(zj_ckje, 2));
        
        BigDecimal ckzbAll = BigDecimal.ZERO;
        if (zj_ssje.compareTo(BigDecimal.ZERO) != 0) {
          ckzbAll = zj_ckje.multiply(new BigDecimal(100)).divide(zj_ssje, 2, 4);
        }
        objzj.put("ckzb", ckzbAll + "%");
        listAll.add(objzj);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  private String getssjeForHzly(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String devchannel, String nexttype)
  {
    String ss = "0.00";
    BigDecimal ssje = BigDecimal.ZERO;
    for (JSONObject rs : skjeList)
    {
      String devchannelT = rs.getString("devchannel");
      String nexttypeT = rs.getString("nexttype");
      if ((YZUtility.isNullorEmpty(devchannel)) || 
        (devchannel.equals(devchannelT))) {
        if ((YZUtility.isNullorEmpty(nexttype)) || 
          (nexttypeT.equals(nexttype)))
        {
          BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
          BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
          BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
          BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
          
          paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
          
          ssje = ssje.add(paymoney);
        }
      }
    }
    ss = ssje.toString();
    return ss;
  }
  
  private String getskjeForHzly(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String devchannel, String nexttype)
  {
    String sk = "0.00";
    BigDecimal skje = BigDecimal.ZERO;
    String devchannelT;
    for (JSONObject rs : skjeList)
    {
      devchannelT = rs.getString("devchannel");
      String nexttypeT = rs.getString("nexttype");
      if ((YZUtility.isNullorEmpty(devchannel)) || 
        (devchannel.equals(devchannelT))) {
        if ((YZUtility.isNullorEmpty(nexttype)) || 
          (nexttypeT.equals(nexttype)))
        {
          BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
          BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
          BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
          BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
          BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
          
          paymoney = paymoney.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
          
          skje = skje.add(paymoney);
        }
      }
    }
    BigDecimal skjeYjj = BigDecimal.ZERO;
    for (JSONObject rs : skjeYjjList)
    {
      String devchannelT = rs.getString("devchannel");
      String nexttypeT = rs.getString("nexttype");
      if ((YZUtility.isNullorEmpty(devchannel)) || 
        (devchannelT.equals(devchannel))) {
        if ((YZUtility.isNullorEmpty(nexttype)) || 
          (nexttypeT.equals(nexttype)))
        {
          BigDecimal paymoney = BigDecimal.ZERO;
          if (!YZUtility.isNullorEmpty(rs.getString("cmoney"))) {
            paymoney = new BigDecimal(rs.getString("cmoney"));
          }
          skjeYjj = skjeYjj.add(paymoney);
        }
      }
    }
    sk = skje.add(skjeYjj).toString();
    return sk;
  }
  
  private String getskjeForWD(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String perid)
  {
    String sk = "0.00";
    BigDecimal skje = BigDecimal.ZERO;
    String createuser;
    for (JSONObject rs : skjeList)
    {
      createuser = rs.getString("createuser");
      if (!YZUtility.isNullorEmpty(createuser)) {
        if (createuser.equals(perid))
        {
          BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
          BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
          BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
          BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
          BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
          
          paymoney = paymoney.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
          
          skje = skje.add(paymoney);
        }
      }
    }
    BigDecimal skjeYjj = BigDecimal.ZERO;
    for (JSONObject rs : skjeYjjList)
    {
      String createuser = rs.getString("createuser");
      if (!YZUtility.isNullorEmpty(createuser)) {
        if (createuser.equals(perid))
        {
          BigDecimal paymoney = BigDecimal.ZERO;
          if (!YZUtility.isNullorEmpty(rs.getString("cmoney"))) {
            paymoney = new BigDecimal(rs.getString("cmoney"));
          }
          skjeYjj = skjeYjj.add(paymoney);
        }
      }
    }
    sk = skje.add(skjeYjj).toString();
    return sk;
  }
  
  private String getskje4AskPerson(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String askPersonSeqid, String regsort)
  {
    String sk = "0.00";
    BigDecimal skje = BigDecimal.ZERO;
    String askpersonT;
    for (JSONObject rs : skjeList)
    {
      askpersonT = rs.getString("askperson");
      String regsortT = rs.getString("regsort");
      if ((YZUtility.isNullorEmpty(askPersonSeqid)) || 
        (askPersonSeqid.equals(askpersonT))) {
        if ((YZUtility.isNullorEmpty(regsort)) || 
          (regsortT.equals(regsort)))
        {
          BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
          BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
          BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
          BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
          BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
          
          paymoney = paymoney.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
          
          skje = skje.add(paymoney);
        }
      }
    }
    BigDecimal skjeYjj = BigDecimal.ZERO;
    for (JSONObject rs : skjeYjjList)
    {
      String askpersonT = rs.getString("askperson");
      String regsortT = rs.getString("regsort");
      if ((YZUtility.isNullorEmpty(askPersonSeqid)) || 
        (askPersonSeqid.equals(askpersonT))) {
        if ((YZUtility.isNullorEmpty(regsort)) || 
          (regsortT.equals(regsort)))
        {
          BigDecimal paymoney = BigDecimal.ZERO;
          if (!YZUtility.isNullorEmpty(rs.getString("cmoney"))) {
            paymoney = new BigDecimal(rs.getString("cmoney"));
          }
          skjeYjj = skjeYjj.add(paymoney);
        }
      }
    }
    sk = skje.add(skjeYjj).toString();
    return sk;
  }
  
  private String getskje4AskPersonSS(List<JSONObject> skjeList, List<JSONObject> skjeYjjList, String askPersonSeqid, String regsort)
  {
    String sk = "0.00";
    BigDecimal skje = BigDecimal.ZERO;
    for (JSONObject rs : skjeList)
    {
      String askpersonT = rs.getString("askperson");
      String regsortT = rs.getString("regsort");
      if ((YZUtility.isNullorEmpty(askPersonSeqid)) || 
        (askPersonSeqid.equals(askpersonT))) {
        if ((YZUtility.isNullorEmpty(regsort)) || 
          (regsortT.equals(regsort)))
        {
          BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
          BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
          BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
          BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
          
          paymoney = paymoney.subtract(zs).subtract(djq).subtract(integral);
          
          skje = skje.add(paymoney);
        }
      }
    }
    sk = skje.toString();
    return sk;
  }
  
  private String getskje4Doctor(List<JSONObject> yysrList, String doctorSeqId, String regsort)
  {
    String sk = "0.00";
    
    BigDecimal yysr = BigDecimal.ZERO;
    for (JSONObject rs : yysrList)
    {
      String doctorT = rs.getString("doctor");
      String regsortT = rs.getString("regsort");
      if ((YZUtility.isNullorEmpty(doctorSeqId)) || 
        (doctorSeqId.equals(doctorT))) {
        if ((YZUtility.isNullorEmpty(regsort)) || 
          (regsortT.equals(regsort)))
        {
          BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
          BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("skze")) ? "0" : rs.getString("skze"));
          BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
          BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
          BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
          skze = skze.add(qf).subtract(zs).subtract(djq).subtract(integral);
          



          yysr = yysr.add(skze);
        }
      }
    }
    sk = yysr.toString();
    return sk;
  }
  
  @RequestMapping({"/selectCountXxly.act"})
  public String selectCountXxly(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String regsort = request.getParameter("regsort");
      
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(regsort)) {
        map.put("regsort", regsort);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
      {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      }
      else
      {
        map.put("organization", organization);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      List<JSONObject> listRegAllTotal = this.logic.getCountHzly(map, visualstaff, "");
      
      int alllynums = listRegAllTotal.size();
      
      int allcjlynums = 0;
      
      int allwcjlynums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
        String cjstatus = regJson.getString("cjstatus");
        if ("1".equals(cjstatus)) {
          allcjlynums++;
        } else {
          allwcjlynums++;
        }
      }
      List<JSONObject> skjeList = null;
      Object skjeYjjList = null;
      skjeList = this.logic.getYysrList(map);
      skjeYjjList = this.logic.getYysrYjjList(map);
      
      List<YZDict> listDict = this.dictLogic.getListByParentCodeALL(DictUtil.HZLY, ChainUtil.getCurrentOrganization(request));
      List<JSONObject> listAll = new ArrayList();
      for (YZDict dict : listDict)
      {
        List<YZDict> listHzly = this.dictLogic.getListByParentCodeALL(dict.getDictCode(), organization);
        int wcjnums;
        for (int i = 0; i < listHzly.size(); i++)
        {
          YZDict objper = (YZDict)listHzly.get(i);
          JSONObject obj = new JSONObject();
          
          obj.put("hzlyType", dict.getDictName());
          
          obj.put("hzlychildname", objper.getDictName());
          
          int lynums = 0;
          int cjnums = 0;
          wcjnums = 0;
          for (JSONObject regJson : listRegAllTotal)
          {
            String cjstatus = regJson.getString("cjstatus");
            String nexttype = regJson.getString("nexttype");
            if (objper.getSeqId().equals(nexttype)) {
              lynums++;
            }
            if (("1".equals(cjstatus)) && (objper.getSeqId().equals(nexttype))) {
              cjnums++;
            }
            if (("0".equals(cjstatus)) && (objper.getSeqId().equals(nexttype))) {
              wcjnums++;
            }
          }
          obj.put("lynums", Integer.valueOf(lynums));
          if (lynums != 0)
          {
            Float lylzb = Float.valueOf(0.0F);
            if (alllynums != 0) {
              lylzb = Float.valueOf(lynums * 100.0F / alllynums);
            }
            obj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
            
            obj.put("cjnums", Integer.valueOf(cjnums));
            
            Float cglzb = Float.valueOf(0.0F);
            if (allcjlynums != 0) {
              cglzb = Float.valueOf(cjnums * 100.0F / allcjlynums);
            }
            obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
            
            obj.put("wcjnums", Integer.valueOf(wcjnums));
            
            Float wcglzb = Float.valueOf(0.0F);
            if (allwcjlynums != 0) {
              wcglzb = Float.valueOf(wcjnums * 100.0F / allwcjlynums);
            }
            obj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
            
            String ss = "0.00";
            ss = getssjeForHzly(skjeList, (List)skjeYjjList, dict.getSeqId(), objper.getSeqId());
            obj.put("ssje", ss);
            
            String sk = "0.00";
            sk = getskjeForHzly(skjeList, (List)skjeYjjList, dict.getSeqId(), objper.getSeqId());
            obj.put("skje", sk);
            listAll.add(obj);
          }
        }
        JSONObject objhj = new JSONObject();
        int lynums = 0;
        int cjnums = 0;
        int wcjnums = 0;
        for (JSONObject regJson : listRegAllTotal)
        {
          String cjstatus = regJson.getString("cjstatus");
          String devchannel = regJson.getString("devchannel");
          if (dict.getSeqId().equals(devchannel)) {
            lynums++;
          }
          if (("1".equals(cjstatus)) && (dict.getSeqId().equals(devchannel))) {
            cjnums++;
          }
          if (("0".equals(cjstatus)) && (dict.getSeqId().equals(devchannel))) {
            wcjnums++;
          }
        }
        objhj.put("lynums", Integer.valueOf(lynums));
        if (lynums != 0)
        {
          Float lylzb = Float.valueOf(0.0F);
          if (alllynums != 0) {
            lylzb = Float.valueOf(lynums * 100.0F / alllynums);
          }
          objhj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
          
          objhj.put("cjnums", Integer.valueOf(cjnums));
          
          Float cgl = Float.valueOf(0.0F);
          if (lynums != 0) {
            cgl = Float.valueOf(cjnums * 100.0F / lynums);
          }
          objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
          
          Float cglzb = Float.valueOf(0.0F);
          if (allcjlynums != 0) {
            cglzb = Float.valueOf(cjnums * 100.0F / allcjlynums);
          }
          objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
          
          objhj.put("wcjnums", Integer.valueOf(wcjnums));
          
          Float wcglzb = Float.valueOf(0.0F);
          if (allwcjlynums != 0) {
            wcglzb = Float.valueOf(wcjnums * 100.0F / allwcjlynums);
          }
          objhj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
          
          String ss = "0.00";
          ss = getssjeForHzly(skjeList, (List)skjeYjjList, dict.getSeqId(), "");
          objhj.put("ssje", ss);
          
          String sk = "0.00";
          sk = getskjeForHzly(skjeList, (List)skjeYjjList, dict.getSeqId(), "");
          objhj.put("skje", sk);
          listAll.add(objhj);
        }
      }
      JSONObject objzj = new JSONObject();
      int lynums = alllynums;
      int cjnums = allcjlynums;
      int wcjnums = allwcjlynums;
      
      objzj.put("lynums", Integer.valueOf(lynums));
      
      Float lylzb = Float.valueOf(0.0F);
      if (alllynums != 0) {
        lylzb = Float.valueOf(lynums * 100.0F / alllynums);
      }
      objzj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
      
      objzj.put("cjnums", Integer.valueOf(cjnums));
      
      Float cgl = Float.valueOf(0.0F);
      if (lynums != 0) {
        cgl = Float.valueOf(cjnums * 100.0F / lynums);
      }
      objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
      
      Float cglzb = Float.valueOf(0.0F);
      if (allcjlynums != 0) {
        cglzb = Float.valueOf(cjnums * 100.0F / allcjlynums);
      }
      objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
      
      objzj.put("wcjnums", Integer.valueOf(wcjnums));
      
      Float wcglzb = Float.valueOf(0.0F);
      if (allwcjlynums != 0) {
        wcglzb = Float.valueOf(wcjnums * 100.0F / allwcjlynums);
      }
      objzj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
      
      String ss = "0.00";
      ss = getssjeForHzly(skjeList, (List)skjeYjjList, "", "");
      objzj.put("ssje", ss);
      
      String sk = "0.00";
      sk = getskjeForHzly(skjeList, (List)skjeYjjList, "", "");
      objzj.put("skje", sk);
      if (lynums > 0) {
        listAll.add(objzj);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountXxlyNum.act"})
  public String selectCountXxlyNum(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String regsort = request.getParameter("regsort");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      if (!YZUtility.isNullorEmpty(regsort)) {
        map.put("regsort", regsort);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
      {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      }
      else
      {
        map.put("organization", organization);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      map.put("personIds4Query", visualstaff);
      

      List<JSONObject> listRegAllTotal = this.logic.getCountHzly(map, visualstaff, "");
      
      int alllynums = listRegAllTotal.size();
      
      int allcjlynums = 0;
      
      int allwcjlynums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
        String cjstatus = regJson.getString("cjstatus");
        if ("1".equals(cjstatus)) {
          allcjlynums++;
        } else {
          allwcjlynums++;
        }
      }
      String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
      
      String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
      
      String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
      
      String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
      
      String qtseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
      

      List<JSONObject> skjeList = null;
      List<JSONObject> skjeYjjList = null;
      skjeList = this.logic.getYysrList(map);
      skjeYjjList = this.logic.getYysrYjjList(map);
      
      List<YZDict> listDict = this.dictLogic.getListByParentCodeALL(DictUtil.HZLY, ChainUtil.getCurrentOrganization(request));
      List<JSONObject> listAll = new ArrayList();
      int qtallnums;
      for (YZDict dict : listDict)
      {
        List<YZDict> listHzly = this.dictLogic.getListByParentCodeALL(dict.getDictCode(), organization);
        int qtcjnums;
        for (int i = 0; i < listHzly.size(); i++)
        {
          YZDict objper = (YZDict)listHzly.get(i);
          JSONObject obj = new JSONObject();
          
          obj.put("hzlyType", dict.getDictName());
          
          obj.put("hzlychildname", objper.getDictName());
          
          int lynums = 0;
          int cjnums = 0;
          int wcjnums = 0;
          int czallnums = 0;
          int czcjnums = 0;
          int fzallnums = 0;
          int fzcjnums = 0;
          int zxfallnums = 0;
          int zxfcjnums = 0;
          int fcallnums = 0;
          int fccjnums = 0;
          int qtallnums = 0;
          qtcjnums = 0;
          for (JSONObject regJson : listRegAllTotal)
          {
            String cjstatus = regJson.getString("cjstatus");
            String nexttype = regJson.getString("nexttype");
            String recesort = regJson.getString("recesort");
            if (objper.getSeqId().equals(nexttype)) {
              lynums++;
            }
            if (("1".equals(cjstatus)) && (objper.getSeqId().equals(nexttype))) {
              cjnums++;
            }
            if (("0".equals(cjstatus)) && (objper.getSeqId().equals(nexttype))) {
              wcjnums++;
            }
            if ((czseqId.equals(recesort)) && (objper.getSeqId().equals(nexttype))) {
              czallnums++;
            }
            if ((czseqId.equals(recesort)) && ("1".equals(cjstatus)) && (objper.getSeqId().equals(nexttype))) {
              czcjnums++;
            }
            if ((fzseqId.equals(recesort)) && (objper.getSeqId().equals(nexttype))) {
              fzallnums++;
            }
            if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus)) && (objper.getSeqId().equals(nexttype))) {
              fzcjnums++;
            }
            if ((zxfseqId.equals(recesort)) && (objper.getSeqId().equals(nexttype))) {
              zxfallnums++;
            }
            if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus)) && (objper.getSeqId().equals(nexttype))) {
              zxfcjnums++;
            }
            if ((fcseqId.equals(recesort)) && (objper.getSeqId().equals(nexttype))) {
              fcallnums++;
            }
            if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus)) && (objper.getSeqId().equals(nexttype))) {
              fccjnums++;
            }
            if ((qtseqId.equals(recesort)) && (objper.getSeqId().equals(nexttype))) {
              qtallnums++;
            }
            if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus)) && (objper.getSeqId().equals(nexttype))) {
              qtcjnums++;
            }
          }
          obj.put("lynums", Integer.valueOf(lynums));
          if (lynums != 0)
          {
            Float lylzb = Float.valueOf(0.0F);
            if (alllynums != 0) {
              lylzb = Float.valueOf(lynums * 100.0F / alllynums);
            }
            obj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
            
            obj.put("cjnums", Integer.valueOf(cjnums));
            
            Float cglzb = Float.valueOf(0.0F);
            if (allcjlynums != 0) {
              cglzb = Float.valueOf(cjnums * 100.0F / allcjlynums);
            }
            obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
            
            obj.put("wcjnums", Integer.valueOf(wcjnums));
            
            Float wcglzb = Float.valueOf(0.0F);
            if (allwcjlynums != 0) {
              wcglzb = Float.valueOf(wcjnums * 100.0F / allwcjlynums);
            }
            obj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
            

            obj.put("czallnums", Integer.valueOf(czallnums));
            
            obj.put("czcjnums", Integer.valueOf(czcjnums));
            
            obj.put("fzallnums", Integer.valueOf(fzallnums));
            
            obj.put("fzcjnums", Integer.valueOf(fzcjnums));
            
            obj.put("zxfallnums", Integer.valueOf(zxfallnums));
            
            obj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
            
            obj.put("fcallnums", Integer.valueOf(fcallnums));
            
            obj.put("fccjnums", Integer.valueOf(fccjnums));
            
            obj.put("qtallnums", Integer.valueOf(qtallnums));
            
            obj.put("qtcjnums", Integer.valueOf(qtcjnums));
            
            String ss = "0.00";
            ss = getssjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), objper.getSeqId());
            obj.put("ssje", ss);
            
            String sk = "0.00";
            sk = getskjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), objper.getSeqId());
            obj.put("skje", sk);
            listAll.add(obj);
          }
        }
        JSONObject objhj = new JSONObject();
        int lynums = 0;
        int cjnums = 0;
        int wcjnums = 0;
        int czallnums = 0;
        int czcjnums = 0;
        int fzallnums = 0;
        int fzcjnums = 0;
        int zxfallnums = 0;
        int zxfcjnums = 0;
        int fcallnums = 0;
        int fccjnums = 0;
        qtallnums = 0;
        int qtcjnums = 0;
        for (JSONObject regJson : listRegAllTotal)
        {
          String cjstatus = regJson.getString("cjstatus");
          String devchannel = regJson.getString("devchannel");
          String recesort = regJson.getString("recesort");
          if (dict.getSeqId().equals(devchannel)) {
            lynums++;
          }
          if (("1".equals(cjstatus)) && (dict.getSeqId().equals(devchannel))) {
            cjnums++;
          }
          if (("0".equals(cjstatus)) && (dict.getSeqId().equals(devchannel))) {
            wcjnums++;
          }
          if ((czseqId.equals(recesort)) && (dict.getSeqId().equals(devchannel))) {
            czallnums++;
          }
          if ((czseqId.equals(recesort)) && ("1".equals(cjstatus)) && (dict.getSeqId().equals(devchannel))) {
            czcjnums++;
          }
          if ((fzseqId.equals(recesort)) && (dict.getSeqId().equals(devchannel))) {
            fzallnums++;
          }
          if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus)) && (dict.getSeqId().equals(devchannel))) {
            fzcjnums++;
          }
          if ((zxfseqId.equals(recesort)) && (dict.getSeqId().equals(devchannel))) {
            zxfallnums++;
          }
          if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus)) && (dict.getSeqId().equals(devchannel))) {
            zxfcjnums++;
          }
          if ((fcseqId.equals(recesort)) && (dict.getSeqId().equals(devchannel))) {
            fcallnums++;
          }
          if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus)) && (dict.getSeqId().equals(devchannel))) {
            fccjnums++;
          }
          if ((qtseqId.equals(recesort)) && (dict.getSeqId().equals(devchannel))) {
            qtallnums++;
          }
          if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus)) && (dict.getSeqId().equals(devchannel))) {
            qtcjnums++;
          }
        }
        objhj.put("lynums", Integer.valueOf(lynums));
        if (lynums != 0)
        {
          Float lylzb = Float.valueOf(0.0F);
          if (alllynums != 0) {
            lylzb = Float.valueOf(lynums * 100.0F / alllynums);
          }
          objhj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
          
          objhj.put("cjnums", Integer.valueOf(cjnums));
          
          Float cgl = Float.valueOf(0.0F);
          if (lynums != 0) {
            cgl = Float.valueOf(cjnums * 100.0F / lynums);
          }
          objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
          
          Float cglzb = Float.valueOf(0.0F);
          if (allcjlynums != 0) {
            cglzb = Float.valueOf(cjnums * 100.0F / allcjlynums);
          }
          objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
          
          objhj.put("wcjnums", Integer.valueOf(wcjnums));
          
          Float wcglzb = Float.valueOf(0.0F);
          if (allwcjlynums != 0) {
            wcglzb = Float.valueOf(wcjnums * 100.0F / allwcjlynums);
          }
          objhj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
          

          objhj.put("czallnums", Integer.valueOf(czallnums));
          
          objhj.put("czcjnums", Integer.valueOf(czcjnums));
          
          objhj.put("fzallnums", Integer.valueOf(fzallnums));
          
          objhj.put("fzcjnums", Integer.valueOf(fzcjnums));
          
          objhj.put("zxfallnums", Integer.valueOf(zxfallnums));
          
          objhj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
          
          objhj.put("fcallnums", Integer.valueOf(fcallnums));
          
          objhj.put("fccjnums", Integer.valueOf(fccjnums));
          
          objhj.put("qtallnums", Integer.valueOf(qtallnums));
          
          objhj.put("qtcjnums", Integer.valueOf(qtcjnums));
          
          String ss = "0.00";
          ss = getssjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), "");
          objhj.put("ssje", ss);
          
          String sk = "0.00";
          sk = getskjeForHzly(skjeList, skjeYjjList, dict.getSeqId(), "");
          objhj.put("skje", sk);
          listAll.add(objhj);
        }
      }
      JSONObject objzj = new JSONObject();
      int lynums = alllynums;
      int cjnums = allcjlynums;
      int wcjnums = allwcjlynums;
      int czallnums = 0;
      int czcjnums = 0;
      int fzallnums = 0;
      int fzcjnums = 0;
      int zxfallnums = 0;
      int zxfcjnums = 0;
      int fcallnums = 0;
      int fccjnums = 0;
      int qtallnums = 0;
      int qtcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
        String cjstatus = regJson.getString("cjstatus");
        String recesort = regJson.getString("recesort");
        if (czseqId.equals(recesort)) {
          czallnums++;
        }
        if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          czcjnums++;
        }
        if (fzseqId.equals(recesort)) {
          fzallnums++;
        }
        if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fzcjnums++;
        }
        if (zxfseqId.equals(recesort)) {
          zxfallnums++;
        }
        if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          zxfcjnums++;
        }
        if (fcseqId.equals(recesort)) {
          fcallnums++;
        }
        if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fccjnums++;
        }
        if (qtseqId.equals(recesort)) {
          qtallnums++;
        }
        if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          qtcjnums++;
        }
      }
      objzj.put("lynums", Integer.valueOf(lynums));
      
      Float lylzb = Float.valueOf(0.0F);
      if (alllynums != 0) {
        lylzb = Float.valueOf(lynums * 100.0F / alllynums);
      }
      objzj.put("lylzb", YZUtility.FloatToFixed2(lylzb) + "%");
      
      objzj.put("cjnums", Integer.valueOf(cjnums));
      
      Float cgl = Float.valueOf(0.0F);
      if (lynums != 0) {
        cgl = Float.valueOf(cjnums * 100.0F / lynums);
      }
      objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
      
      Float cglzb = Float.valueOf(0.0F);
      if (allcjlynums != 0) {
        cglzb = Float.valueOf(cjnums * 100.0F / allcjlynums);
      }
      objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
      
      objzj.put("wcjnums", Integer.valueOf(wcjnums));
      
      Float wcglzb = Float.valueOf(0.0F);
      if (allwcjlynums != 0) {
        wcglzb = Float.valueOf(wcjnums * 100.0F / allwcjlynums);
      }
      objzj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
      

      objzj.put("czallnums", Integer.valueOf(czallnums));
      
      objzj.put("czcjnums", Integer.valueOf(czcjnums));
      
      objzj.put("fzallnums", Integer.valueOf(fzallnums));
      
      objzj.put("fzcjnums", Integer.valueOf(fzcjnums));
      
      objzj.put("zxfallnums", Integer.valueOf(zxfallnums));
      
      objzj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
      
      objzj.put("fcallnums", Integer.valueOf(fcallnums));
      
      objzj.put("fccjnums", Integer.valueOf(fccjnums));
      
      objzj.put("qtallnums", Integer.valueOf(qtallnums));
      
      objzj.put("qtcjnums", Integer.valueOf(qtcjnums));
      
      String ss = "0.00";
      ss = getssjeForHzly(skjeList, skjeYjjList, "", "");
      objzj.put("ssje", ss);
      
      String sk = "0.00";
      sk = getskjeForHzly(skjeList, skjeYjjList, "", "");
      objzj.put("skje", sk);
      if (lynums > 0) {
        listAll.add(objzj);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCountSort.act"})
  public String selectCountSort(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
      {
        organization = ChainUtil.getCurrentOrganization(request);
        map.put("organization", organization);
      }
      else
      {
        map.put("organization", organization);
      }
      List<JSONObject> listAll = new ArrayList();
      

      List<JSONObject> listRegAllTotal = this.logic.getListRegByGhfl(map, "", "");
      
      int allnums = listRegAllTotal.size();
      
      int allcjnums = 0;
      
      int allwcjlynums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
        String cjstatus = regJson.getString("cjstatus");
        if ("1".equals(cjstatus)) {
          allcjnums++;
        } else {
          allwcjlynums++;
        }
      }
      List<JSONObject> skjeList = null;
      Object skjeYjjList = null;
      skjeList = this.logic.getYysrList(map);
      skjeYjjList = this.logic.getYysrYjjList(map);
      

      String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
      
      String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
      
      String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
      
      String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
      
      String qtseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
      

      List<YZDict> listDict = this.dictLogic.getListByParentCodeALL("GHFL", ChainUtil.getCurrentOrganization(request));
      int qtcjnums;
      for (YZDict dict : listDict)
      {
        JSONObject obj = new JSONObject();
        
        obj.put("zxxm", dict.getDictName());
        
        int zxnums = 0;
        int cjnums = 0;
        int wcjnums = 0;
        int czallnums = 0;
        int czcjnums = 0;
        int fzallnums = 0;
        int fzcjnums = 0;
        int zxfallnums = 0;
        int zxfcjnums = 0;
        int fcallnums = 0;
        int fccjnums = 0;
        int qtallnums = 0;
        qtcjnums = 0;
        for (JSONObject regJson : listRegAllTotal)
        {
          String cjstatus = regJson.getString("cjstatus");
          String recesort = regJson.getString("recesort");
          String regsort = regJson.getString("regsort");
          if (regsort.equals(dict.getSeqId())) {
            zxnums++;
          }
          if (("1".equals(cjstatus)) && (regsort.equals(dict.getSeqId()))) {
            cjnums++;
          }
          if (("0".equals(cjstatus)) && (regsort.equals(dict.getSeqId()))) {
            wcjnums++;
          }
          if ((czseqId.equals(recesort)) && (regsort.equals(dict.getSeqId()))) {
            czallnums++;
          }
          if ((czseqId.equals(recesort)) && ("1".equals(cjstatus)) && (regsort.equals(dict.getSeqId()))) {
            czcjnums++;
          }
          if ((fzseqId.equals(recesort)) && (regsort.equals(dict.getSeqId()))) {
            fzallnums++;
          }
          if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus)) && (regsort.equals(dict.getSeqId()))) {
            fzcjnums++;
          }
          if ((zxfseqId.equals(recesort)) && (regsort.equals(dict.getSeqId()))) {
            zxfallnums++;
          }
          if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus)) && (regsort.equals(dict.getSeqId()))) {
            zxfcjnums++;
          }
          if ((fcseqId.equals(recesort)) && (regsort.equals(dict.getSeqId()))) {
            fcallnums++;
          }
          if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus)) && (regsort.equals(dict.getSeqId()))) {
            fccjnums++;
          }
          if ((qtseqId.equals(recesort)) && (regsort.equals(dict.getSeqId()))) {
            qtallnums++;
          }
          if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus)) && (regsort.equals(dict.getSeqId()))) {
            qtcjnums++;
          }
        }
        obj.put("zxnums", Integer.valueOf(zxnums));
        if (zxnums != 0)
        {
          Float zzlzb = Float.valueOf(0.0F);
          if (allnums != 0) {
            zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
          }
          obj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
          
          obj.put("cjnums", Integer.valueOf(cjnums));
          
          Float cgl = Float.valueOf(0.0F);
          if (zxnums != 0) {
            cgl = Float.valueOf(cjnums * 100.0F / zxnums);
          }
          obj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
          
          Float cglzb = Float.valueOf(0.0F);
          if (allcjnums != 0) {
            cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
          }
          obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
          
          obj.put("wcjnums", Integer.valueOf(wcjnums));
          
          Float wcglzb = Float.valueOf(0.0F);
          if (allwcjlynums != 0) {
            wcglzb = Float.valueOf(wcjnums * 100.0F / allwcjlynums);
          }
          obj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
          
          obj.put("czallnums", Integer.valueOf(czallnums));
          
          obj.put("czcjnums", Integer.valueOf(czcjnums));
          
          obj.put("fzallnums", Integer.valueOf(fzallnums));
          obj.put("fzcjnums", Integer.valueOf(fzcjnums));
          
          obj.put("zxfallnums", Integer.valueOf(zxfallnums));
          
          obj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
          
          obj.put("fcallnums", Integer.valueOf(fcallnums));
          
          obj.put("fccjnums", Integer.valueOf(fccjnums));
          
          obj.put("qtallnums", Integer.valueOf(qtallnums));
          
          obj.put("qtcjnums", Integer.valueOf(qtcjnums));
          
          String sk = "0.00";
          sk = getskje4AskPerson(skjeList, (List)skjeYjjList, null, dict.getSeqId());
          obj.put("skje", sk);
          String ss = getskje4AskPersonSS(skjeList, (List)skjeYjjList, null, dict.getSeqId());
          obj.put("ssje", ss);
          listAll.add(obj);
        }
      }
      JSONObject objhj = new JSONObject();
      int zxnums = allnums;
      int cjnums = allcjnums;
      int wcjnums = allwcjlynums;
      int czallnums = 0;
      int czcjnums = 0;
      int fzallnums = 0;
      int fzcjnums = 0;
      int zxfallnums = 0;
      int zxfcjnums = 0;
      int fcallnums = 0;
      int fccjnums = 0;
      int qtallnums = 0;
      int qtcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
        String cjstatus = regJson.getString("cjstatus");
        String recesort = regJson.getString("recesort");
        if (czseqId.equals(recesort)) {
          czallnums++;
        }
        if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          czcjnums++;
        }
        if (fzseqId.equals(recesort)) {
          fzallnums++;
        }
        if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fzcjnums++;
        }
        if (zxfseqId.equals(recesort)) {
          zxfallnums++;
        }
        if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          zxfcjnums++;
        }
        if (fcseqId.equals(recesort)) {
          fcallnums++;
        }
        if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fccjnums++;
        }
        if (qtseqId.equals(recesort)) {
          qtallnums++;
        }
        if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          qtcjnums++;
        }
      }
      objhj.put("zxnums", Integer.valueOf(zxnums));
      
      Float zzlzb = Float.valueOf(0.0F);
      if (allnums != 0) {
        zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
      }
      objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
      
      objhj.put("cjnums", Integer.valueOf(cjnums));
      
      Float cgl = Float.valueOf(0.0F);
      if (zxnums != 0) {
        cgl = Float.valueOf(cjnums * 100.0F / zxnums);
      }
      objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
      
      Float cglzb = Float.valueOf(0.0F);
      if (allcjnums != 0) {
        cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
      }
      objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
      
      objhj.put("wcjnums", Integer.valueOf(wcjnums));
      
      Float wcglzb = Float.valueOf(0.0F);
      if (allwcjlynums != 0) {
        wcglzb = Float.valueOf(wcjnums * 100.0F / allwcjlynums);
      }
      objhj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
      
      objhj.put("czallnums", Integer.valueOf(czallnums));
      
      objhj.put("czcjnums", Integer.valueOf(czcjnums));
      
      objhj.put("fzallnums", Integer.valueOf(fzallnums));
      
      objhj.put("fzcjnums", Integer.valueOf(fzcjnums));
      
      objhj.put("zxfallnums", Integer.valueOf(zxfallnums));
      
      objhj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
      
      objhj.put("fcallnums", Integer.valueOf(fcallnums));
      
      objhj.put("fccjnums", Integer.valueOf(fccjnums));
      
      objhj.put("qtallnums", Integer.valueOf(qtallnums));
      
      objhj.put("qtcjnums", Integer.valueOf(qtcjnums));
      
      String sk = "0.00";
      sk = getskje4AskPerson(skjeList, (List)skjeYjjList, null, null);
      objhj.put("skje", sk);
      String ss = getskje4AskPersonSS(skjeList, (List)skjeYjjList, null, null);
      objhj.put("ssje", ss);
      listAll.add(objhj);
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getWdOrdertj.act"})
  public String getWdOrdertj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String jdtime1 = request.getParameter("jdtime1");
      String jdtime2 = request.getParameter("jdtime2");
      String yytime1 = request.getParameter("yytime1");
      String yytime2 = request.getParameter("yytime2");
      String dztime1 = request.getParameter("dztime1");
      String dztime2 = request.getParameter("dztime2");
      String yewu = request.getParameter("yewu");
      String xiangmu = request.getParameter("xiangmu");
      String shouli = request.getParameter("shouli");
      String devchannel = request.getParameter("devchannel");
      String isyx = request.getParameter("isyx");
      String nexttype = request.getParameter("nexttype");
      String gongju = request.getParameter("gongju");
      

      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      String depttype = "";
      if (!YZUtility.isNullorEmpty(jdtime1))
      {
        jdtime1 = jdtime1 + ConstUtil.TIME_START;
        map.put("jdtime1", jdtime1);
      }
      if (!YZUtility.isNullorEmpty(jdtime2))
      {
        jdtime2 = jdtime2 + ConstUtil.TIME_END;
        map.put("jdtime2", jdtime2);
      }
      if (!YZUtility.isNullorEmpty(yytime1))
      {
        yytime1 = yytime1 + ConstUtil.TIME_START;
        map.put("yytime1", yytime1);
      }
      if (!YZUtility.isNullorEmpty(yytime2))
      {
        yytime2 = yytime2 + ConstUtil.TIME_END;
        map.put("yytime2", yytime2);
      }
      if (!YZUtility.isNullorEmpty(dztime1))
      {
        dztime1 = dztime1 + ConstUtil.TIME_START;
        map.put("dztime1", dztime1);
      }
      if (!YZUtility.isNullorEmpty(dztime2))
      {
        dztime2 = dztime2 + ConstUtil.TIME_END;
        map.put("dztime2", dztime2);
      }
      if (!YZUtility.isNullorEmpty(yewu)) {
        map.put("yewu", yewu);
      }
      if (!YZUtility.isNullorEmpty(xiangmu)) {
        map.put("askitem", xiangmu);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(shouli)) {
        map.put("shouli", shouli);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("accepttool", gongju);
      }
      if ("1".equals(isyx)) {
        depttype = ConstUtil.DEPT_TYPE_3;
      } else if ("2".equals(isyx)) {
        depttype = ConstUtil.DEPT_TYPE_2;
      } else if ("3".equals(isyx)) {
        depttype = ConstUtil.DEPT_TYPE_6;
      }
      List<JSONObject> listPerson = null;
      
      YZPriv priv = this.privLogic.findGeneral(person.getUserPriv());
      if ((priv.getAuthority() != null) && (!priv.getAuthority().equals("")))
      {
        String dept = null;
        if (priv.getAuthority().equals("2"))
        {
          listPerson = this.personLogic.findPersonalDetails(person.getUserId());
        }
        else if (priv.getAuthority().equals("3"))
        {
          shouli = "zz373";
          YZDept department = this.deptLogic.findmarketing("2", depttype, shouli);
          dept = department.getSeqId();
          listPerson = this.personLogic.findVisualPersonnel(dept);
        }
        else if (priv.getAuthority().equals("4"))
        {
          shouli = "zj634";
          YZDept department = this.deptLogic.findmarketing("2", depttype, shouli);
          dept = department.getSeqId();
          listPerson = this.personLogic.findVisualPersonnel(dept);
        }
      }
      else
      {
        List<JSONObject> dept = this.deptLogic.findMarket("2");
        
        String deptId = null;
        for (JSONObject json : dept) {
          if (!json.get("seqId").equals(person.getDeptId()))
          {
            if ((shouli != null) && (!shouli.equals("")))
            {
              YZDept department = this.deptLogic.findmarketing("2", depttype, shouli);
              deptId = department.getSeqId();
            }
            else if ((priv.getAuthority() != null) && (priv.getAuthority().equals("5")))
            {
              shouli = "service";
              YZDept department = this.deptLogic.findmarketing("2", depttype, shouli);
              deptId = department.getSeqId();
            }
            else
            {
              shouli = "zz373";
              YZDept department = this.deptLogic.findmarketing("2", depttype, shouli);
              deptId = department.getSeqId();
            }
          }
          else {
            deptId = person.getDeptId();
          }
        }
        listPerson = this.personLogic.findVisualPersonnel(deptId);
      }
      if (map.get("yewu") != null)
      {
        map.put("visualstaff", "'" + (String)map.get("yewu") + "'");
      }
      else
      {
        String personIds = this.personLogic.getPerIdsByPersonList(listPerson);
        map.put("visualstaff", personIds);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      
      map.put("organization", organization);
      List<JSONObject> list = new ArrayList();
      if ((jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (dztime1.equals("")) && (dztime2.equals("")))
      {
        String date = YZUtility.getDateStr(new Date());
        jdtime1 = date + ConstUtil.TIME_START;
        jdtime2 = date + ConstUtil.TIME_END;
        map.put("jdtime1", jdtime1);
        map.put("jdtime2", jdtime2);
        
        Object jdList = this.logic.getCountJdList(map);
        map.put("jdtime1", null);
        map.put("jdtime2", null);
        map.put("yytime1", jdtime1);
        map.put("yytime2", jdtime2);
        
        List<JSONObject> yynumsList = this.logic.getCountYyList(map);
        map.put("yytime1", null);
        map.put("yytime2", null);
        map.put("jdtime1", jdtime1);
        map.put("jdtime2", jdtime2);
        map.put("dztime1", jdtime1);
        map.put("dztime2", jdtime2);
        
        List<JSONObject> doorstatusList = this.logic.getCountDoorstatus(map);
        

        List<JSONObject> skjeList = this.logic.getYysrListNoOrg(map);
        List<JSONObject> skjeYjjList = this.logic.getYysrYjjListNoOrg(map);
        if (map.get("yewu") != null)
        {
          JSONObject obj = new JSONObject();
          for (int i = 0; i < listPerson.size(); i++)
          {
            JSONObject objper = (JSONObject)listPerson.get(i);
            if (objper.getString("seqId").equals(yewu)) {
              obj.put("username", objper.getString("userName"));
            }
          }
          obj.put("xh", Integer.valueOf(1));
          int ldnums = ((JSONObject)((List)jdList).get(0)).getInt("num");
          int yynums = ((JSONObject)yynumsList.get(0)).getInt("num");
          int yysmnums = ((JSONObject)doorstatusList.get(0)).getInt("num");
          Float yyl = Float.valueOf(0.0F);
          if (ldnums != 0) {
            yyl = Float.valueOf(yynums * 100.0F / ldnums);
          }
          Float dzl = Float.valueOf(0.0F);
          if (ldnums != 0) {
            dzl = Float.valueOf(yysmnums * 100.0F / ldnums);
          }
          obj.put("ldnums", Integer.valueOf(ldnums));
          obj.put("yynums", Integer.valueOf(yynums));
          obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
          obj.put("yysmnums", Integer.valueOf(yysmnums));
          obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
          


          String sk = new BigDecimal(new StringBuilder().append(((JSONObject)skjeList.get(0)).get("paymoney")).toString()).add(new BigDecimal(new StringBuilder().append(((JSONObject)skjeYjjList.get(0)).get("cmoney")).toString()));
          obj.put("skje", new BigDecimal(((JSONObject)skjeList.get(0)).get("paymoney")).add(new BigDecimal(((JSONObject)skjeYjjList.get(0)).get("cmoney"))));
          Float rjxf = Float.valueOf(0.0F);
          if (yysmnums != 0) {
            rjxf = Float.valueOf(Float.parseFloat(sk) / yysmnums);
          }
          obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
          obj.put("xmje", ((JSONObject)skjeList.get(0)).get("paymoney"));
          obj.put("ysje", ((JSONObject)skjeYjjList.get(0)).get("cmoney"));
          list.add(obj);
        }
        else
        {
          for (int i = 0; i < listPerson.size(); i++)
          {
            JSONObject objper = (JSONObject)listPerson.get(i);
            JSONObject obj = new JSONObject();
            obj.put("xh", Integer.valueOf(i + 1));
            obj.put("username", objper.getString("userName"));
            
            int ldnums = 0;
            int yynums = 0;
            int yysmnums = 0;
            String sk = new BigDecimal(new StringBuilder().append(((JSONObject)skjeList.get(i)).get("paymoney")).toString()).add(new BigDecimal(new StringBuilder().append(((JSONObject)skjeYjjList.get(i)).get("cmoney")).toString()));
            for (int j = 0; j < listPerson.size(); j++) {
              if (((JSONObject)((List)jdList).get(j)).get("seqId").equals(objper.getString("seqId"))) {
                ldnums = ((JSONObject)((List)jdList).get(j)).getInt("num");
              }
            }
            for (int j = 0; j < listPerson.size(); j++) {
              if (((JSONObject)yynumsList.get(j)).get("seqId").equals(objper.getString("seqId"))) {
                yynums = ((JSONObject)yynumsList.get(j)).getInt("num");
              }
            }
            for (int j = 0; j < listPerson.size(); j++) {
              if (((JSONObject)doorstatusList.get(j)).get("seqId").equals(objper.getString("seqId"))) {
                yysmnums = ((JSONObject)doorstatusList.get(j)).getInt("num");
              }
            }
            for (int j = 0; j < listPerson.size(); j++) {
              if (((JSONObject)skjeList.get(j)).get("seqId").equals(objper.getString("seqId")))
              {
                sk = new BigDecimal(new StringBuilder().append(((JSONObject)skjeList.get(j)).get("paymoney")).toString());
                obj.put("xmje", ((JSONObject)skjeList.get(j)).get("paymoney"));
              }
            }
            for (int j = 0; j < listPerson.size(); j++) {
              if (((JSONObject)skjeYjjList.get(j)).get("seqId").equals(objper.getString("seqId")))
              {
                sk = new BigDecimal(sk).add(new BigDecimal(new StringBuilder().append(((JSONObject)skjeYjjList.get(j)).get("cmoney")).toString()));
                obj.put("ysje", ((JSONObject)skjeYjjList.get(j)).get("cmoney"));
              }
            }
            Float yyl = Float.valueOf(0.0F);
            if (ldnums != 0) {
              yyl = Float.valueOf(yynums * 100.0F / ldnums);
            }
            Float dzl = Float.valueOf(0.0F);
            if (ldnums != 0) {
              dzl = Float.valueOf(yysmnums * 100.0F / ldnums);
            }
            obj.put("ldnums", Integer.valueOf(ldnums));
            obj.put("yynums", Integer.valueOf(yynums));
            obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
            obj.put("yysmnums", Integer.valueOf(yysmnums));
            obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
            


            obj.put("skje", sk);
            Float rjxf = Float.valueOf(0.0F);
            if (yysmnums != 0) {
              rjxf = Float.valueOf(Float.parseFloat(sk) / yysmnums);
            }
            obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
            
            list.add(obj);
          }
        }
      }
      else
      {
        int ldnums = 0;
        int yynums = 0;
        List<JSONObject> jdList = null;
        List<JSONObject> yynumsList = null;
        if (((jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (!dztime1.equals("")) && (!dztime2.equals(""))) || 
          ((jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (!dztime1.equals("")) && (dztime2.equals(""))) || (
          (jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (dztime1.equals("")) && (!dztime2.equals(""))))
        {
          ldnums = 0;
          yynums = 0;
        }
        else if (((jdtime1.equals("")) && (jdtime2.equals("")) && (!yytime1.equals("")) && (!yytime2.equals(""))) || 
          ((jdtime1.equals("")) && (jdtime2.equals("")) && (!yytime1.equals("")) && (yytime2.equals(""))) || (
          (jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (!yytime2.equals(""))))
        {
          ldnums = 0;
          yynumsList = this.logic.getCountYyList(map);
          yynums = ((JSONObject)yynumsList.get(0)).getInt("num");
        }
        else
        {
          jdList = this.logic.getCountJdList(map);
          
          yynumsList = this.logic.getCountYyList(map);
          ldnums = ((JSONObject)jdList.get(0)).getInt("num");
          yynums = ((JSONObject)yynumsList.get(0)).getInt("num");
        }
        List<JSONObject> doorstatusList = this.logic.getCountDoorstatus(map);
        

        List<JSONObject> skjeList = this.logic.getYysrListNoOrg(map);
        List<JSONObject> skjeYjjList = this.logic.getYysrYjjListNoOrg(map);
        if (map.get("yewu") != null)
        {
          JSONObject obj = new JSONObject();
          for (int i = 0; i < listPerson.size(); i++)
          {
            JSONObject objper = (JSONObject)listPerson.get(i);
            if (objper.getString("seqId").equals(yewu)) {
              obj.put("username", objper.getString("userName"));
            }
          }
          obj.put("xh", Integer.valueOf(1));
          
          int yysmnums = ((JSONObject)doorstatusList.get(0)).getInt("num");
          






















          Float yyl = Float.valueOf(0.0F);
          if (ldnums != 0) {
            yyl = Float.valueOf(yynums * 100.0F / ldnums);
          }
          Float dzl = Float.valueOf(0.0F);
          if (ldnums != 0) {
            dzl = Float.valueOf(yysmnums * 100.0F / ldnums);
          }
          obj.put("ldnums", Integer.valueOf(ldnums));
          obj.put("yynums", Integer.valueOf(yynums));
          obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
          obj.put("yysmnums", Integer.valueOf(yysmnums));
          obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
          


          String sk = new BigDecimal(new StringBuilder().append(((JSONObject)skjeList.get(0)).get("paymoney")).toString()).add(new BigDecimal(new StringBuilder().append(((JSONObject)skjeYjjList.get(0)).get("cmoney")).toString()));
          obj.put("skje", new BigDecimal(((JSONObject)skjeList.get(0)).get("paymoney")).add(new BigDecimal(((JSONObject)skjeYjjList.get(0)).get("cmoney"))));
          





          Float rjxf = Float.valueOf(0.0F);
          if (yysmnums != 0) {
            rjxf = Float.valueOf(Float.parseFloat(sk) / yysmnums);
          }
          obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
          obj.put("xmje", ((JSONObject)skjeList.get(0)).get("paymoney"));
          obj.put("ysje", ((JSONObject)skjeYjjList.get(0)).get("cmoney"));
          list.add(obj);
        }
        else
        {
          for (int i = 0; i < listPerson.size(); i++)
          {
            JSONObject objper = (JSONObject)listPerson.get(i);
            JSONObject obj = new JSONObject();
            obj.put("xh", Integer.valueOf(i + 1));
            obj.put("username", objper.getString("userName"));
            
            int yysmnums = 0;
            String sk = new BigDecimal(new StringBuilder().append(((JSONObject)skjeList.get(i)).get("paymoney")).toString()).add(new BigDecimal(new StringBuilder().append(((JSONObject)skjeYjjList.get(i)).get("cmoney")).toString()));
            for (int j = 0; j < listPerson.size(); j++) {
              if (jdList == null) {
                ldnums = 0;
              } else if (((JSONObject)jdList.get(j)).get("seqId").equals(objper.getString("seqId"))) {
                ldnums = ((JSONObject)jdList.get(j)).getInt("num");
              }
            }
            for (int j = 0; j < listPerson.size(); j++) {
              if (yynumsList == null) {
                yynums = 0;
              } else if (((JSONObject)yynumsList.get(j)).get("seqId").equals(objper.getString("seqId"))) {
                yynums = ((JSONObject)yynumsList.get(j)).getInt("num");
              }
            }
            for (int j = 0; j < listPerson.size(); j++) {
              if (((JSONObject)doorstatusList.get(j)).get("seqId").equals(objper.getString("seqId"))) {
                yysmnums = ((JSONObject)doorstatusList.get(j)).getInt("num");
              }
            }
            for (int j = 0; j < listPerson.size(); j++) {
              if (((JSONObject)skjeList.get(j)).get("seqId").equals(objper.getString("seqId")))
              {
                sk = new BigDecimal(new StringBuilder().append(((JSONObject)skjeList.get(j)).get("paymoney")).toString());
                obj.put("xmje", ((JSONObject)skjeList.get(j)).get("paymoney"));
              }
            }
            for (int j = 0; j < listPerson.size(); j++) {
              if (((JSONObject)skjeYjjList.get(j)).get("seqId").equals(objper.getString("seqId")))
              {
                sk = new BigDecimal(sk).add(new BigDecimal(new StringBuilder().append(((JSONObject)skjeYjjList.get(j)).get("cmoney")).toString()));
                obj.put("ysje", ((JSONObject)skjeYjjList.get(j)).get("cmoney"));
              }
            }
            Float yyl = Float.valueOf(0.0F);
            if (ldnums != 0) {
              yyl = Float.valueOf(yynums * 100.0F / ldnums);
            }
            Float dzl = Float.valueOf(0.0F);
            if (ldnums != 0) {
              dzl = Float.valueOf(yysmnums * 100.0F / ldnums);
            }
            obj.put("ldnums", Integer.valueOf(ldnums));
            obj.put("yynums", Integer.valueOf(yynums));
            obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
            obj.put("yysmnums", Integer.valueOf(yysmnums));
            obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
            


            obj.put("skje", sk);
            





            Float rjxf = Float.valueOf(0.0F);
            if (yysmnums != 0) {
              rjxf = Float.valueOf(Float.parseFloat(sk) / yysmnums);
            }
            obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
            
            list.add(obj);
          }
        }
      }
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("网电预约统计", fieldArr, fieldnameArr, list, response, request);
        return null;
      }
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/findCreateUser.act"})
  public String findCreateUser(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String shouli = request.getParameter("shouli");
    String isyx = request.getParameter("isyx");
    try
    {
      List<JSONObject> listPerson = null;
      YZPerson person = SessionUtil.getLoginPerson(request);
      YZPriv priv = this.privLogic.findGeneral(person.getUserPriv());
      String deptId = null;
      if (priv.getAuthority() != null)
      {
        if (priv.getAuthority().equals("5")) {
          listPerson = this.personLogic.findPersonalDetails(person.getDeptId());
        }
      }
      else if ((shouli != null) && (!shouli.equals("")))
      {
        YZDept department = this.deptLogic.findmarketing("2", isyx, shouli);
        deptId = department.getSeqId();
      }
      else
      {
        shouli = "zz373";
        YZDept department = this.deptLogic.findmarketing("2", isyx, shouli);
        deptId = department.getSeqId();
      }
      listPerson = this.personLogic.findVisualPersonnel(deptId);
      YZUtility.RETURN_LIST(listPerson, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getWdOrderPerItemtj.act"})
  public String getWdOrderPerItemtj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String jdtime1 = request.getParameter("jdtime1");
      String jdtime2 = request.getParameter("jdtime2");
      String yytime1 = request.getParameter("yytime1");
      String yytime2 = request.getParameter("yytime2");
      String dztime1 = request.getParameter("dztime1");
      String dztime2 = request.getParameter("dztime2");
      String yewu = request.getParameter("yewu");
      String xiangmu = request.getParameter("xiangmu");
      String shouli = request.getParameter("shouli");
      String devchannel = request.getParameter("devchannel");
      String isyx = request.getParameter("isyx");
      String nexttype = request.getParameter("nexttype");
      String gongju = request.getParameter("gongju");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      String depttype = "";
      if ("1".equals(isyx)) {
        depttype = ConstUtil.DEPT_TYPE_3;
      } else if ("2".equals(isyx)) {
        depttype = ConstUtil.DEPT_TYPE_2;
      } else if ("3".equals(isyx)) {
        depttype = ConstUtil.DEPT_TYPE_6;
      }
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(jdtime1))
      {
        jdtime1 = jdtime1 + ConstUtil.TIME_START;
        map.put("jdtime1", jdtime1);
      }
      if (!YZUtility.isNullorEmpty(jdtime2))
      {
        jdtime2 = jdtime2 + ConstUtil.TIME_END;
        map.put("jdtime2", jdtime2);
      }
      if (!YZUtility.isNullorEmpty(yytime1))
      {
        yytime1 = yytime1 + ConstUtil.TIME_START;
        map.put("yytime1", yytime1);
      }
      if (!YZUtility.isNullorEmpty(yytime2))
      {
        yytime2 = yytime2 + ConstUtil.TIME_END;
        map.put("yytime2", yytime2);
      }
      if (!YZUtility.isNullorEmpty(dztime1))
      {
        dztime1 = dztime1 + ConstUtil.TIME_START;
        map.put("dztime1", dztime1);
      }
      if (!YZUtility.isNullorEmpty(dztime2))
      {
        dztime2 = dztime2 + ConstUtil.TIME_END;
        map.put("dztime2", dztime2);
      }
      if (!YZUtility.isNullorEmpty(yewu)) {
        map.put("yewu", yewu);
      }
      if (!YZUtility.isNullorEmpty(xiangmu)) {
        map.put("askitem", xiangmu);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(shouli)) {
        map.put("shouli", shouli);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("accepttool", gongju);
      }
      List<JSONObject> listPerson = null;
      
      YZPriv priv = this.privLogic.findGeneral(person.getUserPriv());
      if (priv.getAuthority() != null)
      {
        if (priv.getAuthority().equals("2")) {
          listPerson = this.personLogic.findPersonalDetails(person.getUserId());
        }
      }
      else
      {
        List<JSONObject> dept = this.deptLogic.findMarket("2");
        
        String deptId = null;
        for (JSONObject json : dept) {
          if (!json.get("seqId").equals(person.getDeptId()))
          {
            if ((shouli != null) && (!shouli.equals("")))
            {
              YZDept department = this.deptLogic.findmarketing("2", depttype, shouli);
              deptId = department.getSeqId();
            }
            else
            {
              shouli = "zz373";
              YZDept department = this.deptLogic.findmarketing("2", depttype, shouli);
              deptId = department.getSeqId();
            }
          }
          else {
            deptId = person.getDeptId();
          }
        }
        listPerson = this.personLogic.findVisualPersonnel(deptId);
      }
      if (map.get("yewu") != null)
      {
        map.put("visualstaff", "'" + (String)map.get("yewu") + "'");
      }
      else
      {
        String personIds = this.personLogic.getPerIdsByPersonList(listPerson);
        map.put("visualstaff", personIds);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      
      map.put("organization", organization);
      List<YZDict> zxxmDictList = this.dictLogic.getListByParentCodeALL(DictUtil.ZXXM, ChainUtil.getCurrentOrganization(request));
      List<JSONObject> listAll = new ArrayList();
      Object ldnumList = null;
      List<JSONObject> yynumsList = null;
      List<JSONObject> doorstatusList = null;
      JSONObject objhj;
      if ((jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (dztime1.equals("")) && (dztime2.equals("")))
      {
        String date = YZUtility.getDateStr(new Date());
        jdtime1 = date + ConstUtil.TIME_START;
        jdtime2 = date + ConstUtil.TIME_END;
        map.put("jdtime1", jdtime1);
        map.put("jdtime2", jdtime2);
        
        ldnumList = this.logic.getCountJdItemList(map);
        map.put("jdtime1", null);
        map.put("jdtime2", null);
        map.put("yytime1", jdtime1);
        map.put("yytime2", jdtime2);
        
        yynumsList = this.logic.getCountYyItemList(map);
        map.put("yytime1", null);
        map.put("yytime2", null);
        map.put("jdtime1", jdtime1);
        map.put("jdtime2", jdtime2);
        map.put("dztime1", jdtime1);
        map.put("dztime2", jdtime2);
        
        doorstatusList = this.logic.getCountDoorstatusItemList(map);
        if (map.get("yewu") != null) {
          for (int i = 0; i < listPerson.size(); i++)
          {
            JSONObject objper = (JSONObject)listPerson.get(i);
            if (objper.getString("seqId").equals(yewu))
            {
              for (YZDict dict : zxxmDictList)
              {
                JSONObject obj1 = this.logic.getJSONObject4getWdOrderPerItemtj((List)ldnumList, yynumsList, doorstatusList, null, objper, dict);
                if (obj1 != null) {
                  listAll.add(obj1);
                }
              }
              JSONObject objhj = this.logic.getJSONObject4getWdOrderPerItemtj((List)ldnumList, yynumsList, doorstatusList, null, objper, null);
              if (objhj != null) {
                listAll.add(objhj);
              }
            }
          }
        } else {
          for (int i = 0; i < listPerson.size(); i++)
          {
            JSONObject objper = (JSONObject)listPerson.get(i);
            for (YZDict dict : zxxmDictList)
            {
              JSONObject obj = this.logic.getJSONObject4getWdOrderPerItemtj((List)ldnumList, yynumsList, doorstatusList, null, objper, dict);
              if (obj != null) {
                listAll.add(obj);
              }
            }
            objhj = this.logic.getJSONObject4getWdOrderPerItemtj((List)ldnumList, yynumsList, doorstatusList, null, objper, null);
            if (objhj != null) {
              listAll.add(objhj);
            }
          }
        }
      }
      else
      {
        if (((jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (!dztime1.equals("")) && (!dztime2.equals(""))) || 
          ((jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (!dztime1.equals("")) && (dztime2.equals(""))) || (
          (jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (dztime1.equals("")) && (!dztime2.equals(""))))
        {
          ldnumList = null;
          yynumsList = null;
        }
        else if (((jdtime1.equals("")) && (jdtime2.equals("")) && (!yytime1.equals("")) && (!yytime2.equals(""))) || 
          ((jdtime1.equals("")) && (jdtime2.equals("")) && (!yytime1.equals("")) && (yytime2.equals(""))) || (
          (jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (!yytime2.equals(""))))
        {
          ldnumList = null;
          yynumsList = this.logic.getCountYyItemList(map);
        }
        else
        {
          ldnumList = this.logic.getCountJdItemList(map);
          
          yynumsList = this.logic.getCountYyItemList(map);
        }
        doorstatusList = this.logic.getCountDoorstatusItemList(map);
        if (map.get("yewu") != null) {
          for (int i = 0; i < listPerson.size(); i++)
          {
            JSONObject objper = (JSONObject)listPerson.get(i);
            if (objper.getString("seqId").equals(yewu))
            {
              for (YZDict dict : zxxmDictList)
              {
                JSONObject obj1 = this.logic.getJSONObject4getWdOrderPerItemtj((List)ldnumList, yynumsList, doorstatusList, null, objper, dict);
                if (obj1 != null) {
                  listAll.add(obj1);
                }
              }
              JSONObject objhj = this.logic.getJSONObject4getWdOrderPerItemtj((List)ldnumList, yynumsList, doorstatusList, null, objper, null);
              if (objhj != null) {
                listAll.add(objhj);
              }
            }
          }
        } else {
          for (int i = 0; i < listPerson.size(); i++)
          {
            JSONObject objper = (JSONObject)listPerson.get(i);
            for (YZDict dict : zxxmDictList)
            {
              JSONObject obj = this.logic.getJSONObject4getWdOrderPerItemtj((List)ldnumList, yynumsList, doorstatusList, null, objper, dict);
              if (obj != null) {
                listAll.add(obj);
              }
            }
            JSONObject objhj = this.logic.getJSONObject4getWdOrderPerItemtj((List)ldnumList, yynumsList, doorstatusList, null, objper, null);
            if (objhj != null) {
              listAll.add(objhj);
            }
          }
        }
      }
      JSONObject objhj = this.logic.getJSONObject4getWdOrderPerItemtj((List)ldnumList, yynumsList, doorstatusList, null, null, null);
      listAll.add(objhj);
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("咨询项目情况统计表", fieldArr, fieldnameArr, listAll, response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getWdOrderItemtj.act"})
  public String getWdOrderItemtj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String jdtime1 = request.getParameter("jdtime1");
      String jdtime2 = request.getParameter("jdtime2");
      String yytime1 = request.getParameter("yytime1");
      String yytime2 = request.getParameter("yytime2");
      String dztime1 = request.getParameter("dztime1");
      String dztime2 = request.getParameter("dztime2");
      String yewu = request.getParameter("yewu");
      String xiangmu = request.getParameter("xiangmu");
      String shouli = request.getParameter("shouli");
      String devchannel = request.getParameter("devchannel");
      String isyx = request.getParameter("isyx");
      String nexttype = request.getParameter("nexttype");
      String gongju = request.getParameter("gongju");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      String depttype = "";
      if ("1".equals(isyx)) {
        depttype = ConstUtil.DEPT_TYPE_3;
      } else if ("2".equals(isyx)) {
        depttype = ConstUtil.DEPT_TYPE_2;
      } else if ("3".equals(isyx)) {
        depttype = ConstUtil.DEPT_TYPE_6;
      }
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(jdtime1))
      {
        jdtime1 = jdtime1 + ConstUtil.TIME_START;
        map.put("jdtime1", jdtime1);
      }
      if (!YZUtility.isNullorEmpty(jdtime2))
      {
        jdtime2 = jdtime2 + ConstUtil.TIME_END;
        map.put("jdtime2", jdtime2);
      }
      if (!YZUtility.isNullorEmpty(yytime1))
      {
        yytime1 = yytime1 + ConstUtil.TIME_START;
        map.put("yytime1", yytime1);
      }
      if (!YZUtility.isNullorEmpty(yytime2))
      {
        yytime2 = yytime2 + ConstUtil.TIME_END;
        map.put("yytime2", yytime2);
      }
      if (!YZUtility.isNullorEmpty(dztime1))
      {
        dztime1 = dztime1 + ConstUtil.TIME_START;
        map.put("dztime1", dztime1);
      }
      if (!YZUtility.isNullorEmpty(dztime2))
      {
        dztime2 = dztime2 + ConstUtil.TIME_END;
        map.put("dztime2", dztime2);
      }
      if (!YZUtility.isNullorEmpty(yewu)) {
        map.put("yewu", yewu);
      }
      if (!YZUtility.isNullorEmpty(xiangmu)) {
        map.put("askitem", xiangmu);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(shouli)) {
        map.put("shouli", shouli);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("accepttool", gongju);
      }
      List<JSONObject> listPerson = null;
      
      YZPriv priv = this.privLogic.findGeneral(person.getUserPriv());
      if (priv.getAuthority() != null)
      {
        if (priv.getAuthority().equals("2")) {
          listPerson = this.personLogic.findPersonalDetails(person.getUserId());
        }
      }
      else
      {
        List<JSONObject> dept = this.deptLogic.findMarket("2");
        
        String deptId = null;
        for (JSONObject json : dept) {
          if (!json.get("seqId").equals(person.getDeptId()))
          {
            if ((shouli != null) && (!shouli.equals("")))
            {
              YZDept department = this.deptLogic.findmarketing("2", depttype, shouli);
              deptId = department.getSeqId();
            }
            else
            {
              shouli = "zz373";
              YZDept department = this.deptLogic.findmarketing("2", depttype, shouli);
              deptId = department.getSeqId();
            }
          }
          else {
            deptId = person.getDeptId();
          }
        }
        listPerson = this.personLogic.findVisualPersonnel(deptId);
      }
      if (map.get("yewu") != null)
      {
        map.put("visualstaff", "'" + (String)map.get("yewu") + "'");
      }
      else
      {
        String personIds = this.personLogic.getPerIdsByPersonList(listPerson);
        map.put("visualstaff", personIds);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      
      map.put("organization", organization);
      
      List<JSONObject> ldnumList = null;
      List<JSONObject> yynumsList = null;
      Object doorstatusList = null;
      if ((jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (dztime1.equals("")) && (dztime2.equals("")))
      {
        String date = YZUtility.getDateStr(new Date());
        jdtime1 = date + ConstUtil.TIME_START;
        jdtime2 = date + ConstUtil.TIME_END;
        map.put("jdtime1", jdtime1);
        map.put("jdtime2", jdtime2);
        
        ldnumList = this.logic.getCountJdItemStatisticsList(map);
        map.put("jdtime1", null);
        map.put("jdtime2", null);
        map.put("yytime1", jdtime1);
        map.put("yytime2", jdtime2);
        
        yynumsList = this.logic.getCountYyItemStatisticsList(map);
        map.put("yytime1", null);
        map.put("yytime2", null);
        map.put("jdtime1", jdtime1);
        map.put("jdtime2", jdtime2);
        map.put("dztime1", jdtime1);
        map.put("dztime2", jdtime2);
        
        doorstatusList = this.logic.getCountDoorstatusItemStatisticsList(map);
      }
      else
      {
        if (((jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (!dztime1.equals("")) && (!dztime2.equals(""))) || 
          ((jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (!dztime1.equals("")) && (dztime2.equals(""))) || (
          (jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (dztime1.equals("")) && (!dztime2.equals(""))))
        {
          ldnumList = null;
          yynumsList = null;
        }
        else if (((jdtime1.equals("")) && (jdtime2.equals("")) && (!yytime1.equals("")) && (!yytime2.equals(""))) || 
          ((jdtime1.equals("")) && (jdtime2.equals("")) && (!yytime1.equals("")) && (yytime2.equals(""))) || (
          (jdtime1.equals("")) && (jdtime2.equals("")) && (yytime1.equals("")) && (!yytime2.equals(""))))
        {
          ldnumList = null;
          yynumsList = this.logic.getCountYyItemStatisticsList(map);
        }
        else
        {
          ldnumList = this.logic.getCountJdItemStatisticsList(map);
          
          yynumsList = this.logic.getCountYyItemStatisticsList(map);
        }
        doorstatusList = this.logic.getCountDoorstatusItemStatisticsList(map);
      }
      int ldallnums = 0;
      int yyallnums = 0;
      
      int yysmallnums = 0;
      if (ldnumList != null) {
        for (JSONObject jsonObject : ldnumList) {
          ldallnums += jsonObject.getInt("ldnums");
        }
      }
      if (yynumsList != null) {
        for (JSONObject jsonObject : yynumsList) {
          yyallnums += jsonObject.getInt("yynums");
        }
      }
      for (JSONObject jsonObject : (List)doorstatusList) {
        yysmallnums += jsonObject.getInt("yysmnums");
      }
      List<JSONObject> listAll = new ArrayList();
      
      Object listDict = this.dictLogic.getListByParentCodeALL(DictUtil.ZXXM, ChainUtil.getCurrentOrganization(request));
      for (YZDict dict : (List)listDict)
      {
        int ldnums = 0;
        int yynums = 0;
        int yysmnums = 0;
        if (ldnumList != null) {
          for (int j = 0; j < ldnumList.size(); j++)
          {
            JSONObject jobj = (JSONObject)ldnumList.get(j);
            if (jobj.getString("askitem").equals(dict.getSeqId())) {
              ldnums += jobj.getInt("ldnums");
            }
          }
        }
        if (yynumsList != null) {
          for (int j = 0; j < yynumsList.size(); j++)
          {
            JSONObject jobj = (JSONObject)yynumsList.get(j);
            if (jobj.getString("askitem").equals(dict.getSeqId())) {
              yynums += jobj.getInt("yynums");
            }
          }
        }
        for (int j = 0; j < ((List)doorstatusList).size(); j++)
        {
          JSONObject jobj = (JSONObject)((List)doorstatusList).get(j);
          if (jobj.getString("askitem").equals(dict.getSeqId())) {
            yysmnums += jobj.getInt("yysmnums");
          }
        }
        Float zzzb = Float.valueOf(0.0F);
        if (ldallnums != 0) {
          zzzb = Float.valueOf(ldnums * 100.0F / ldallnums);
        }
        Float dzl = Float.valueOf(0.0F);
        if (yynums != 0) {
          dzl = Float.valueOf(yysmnums * 100.0F / yynums);
        }
        JSONObject obj = new JSONObject();
        obj.put("zxxm", dict.getDictName());
        obj.put("ldnums", Integer.valueOf(ldnums));
        obj.put("zzzb", YZUtility.FloatToFixed2(zzzb) + "%");
        obj.put("yynums", Integer.valueOf(yynums));
        obj.put("yysmnums", Integer.valueOf(yysmnums));
        obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
        


        listAll.add(obj);
      }
      JSONObject objhj = new JSONObject();
      int ldnums = ldallnums;
      int yynums = yyallnums;
      int yysmnums = yysmallnums;
      

      Float dzl = Float.valueOf(0.0F);
      if (yynums != 0) {
        dzl = Float.valueOf(yysmnums * 100.0F / yynums);
      }
      objhj.put("ldnums", Integer.valueOf(ldnums));
      objhj.put("zzzb", "100%");
      objhj.put("yynums", Integer.valueOf(yynums));
      objhj.put("yysmnums", Integer.valueOf(yysmnums));
      objhj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
      



      listAll.add(objhj);
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCount_yjjcx.act"})
  public String selectCount_yjjcx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = starttime + ConstUtil.TIME_START;
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = endtime + ConstUtil.TIME_END;
      }
      List<JSONObject> listAll = new ArrayList();
      
      JSONObject qcjobj = this.logic.getQcje(starttime, organization);
      qcjobj.put("name", "期初余额");
      listAll.add(qcjobj);
      
      JSONObject kkjobj = this.logic.getCaozuoje("开卡", starttime, endtime, organization);
      kkjobj.put("name", "本期开卡");
      listAll.add(kkjobj);
      
      JSONObject czobj = this.logic.getCaozuoje("充值", starttime, endtime, organization);
      czobj.put("name", "本期充值");
      listAll.add(czobj);
      
      JSONObject jfjobj = this.logic.getCaozuoje("缴费", starttime, endtime, organization);
      jfjobj.put("name", "本期缴费");
      listAll.add(jfjobj);
      
      JSONObject tfjobj = this.logic.getCaozuoje("退费", starttime, endtime, organization);
      tfjobj.put("name", "本期退费");
      listAll.add(tfjobj);
      
      JSONObject qmjobj = this.logic.getQmje(endtime, organization);
      qmjobj.put("name", "期末余额");
      listAll.add(qmjobj);
      JSONObject jobj2 = new JSONObject();
      jobj2.put("data", listAll);
      YZUtility.DEAL_SUCCESS(jobj2, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCount_hjzlqk.act"})
  public String selectCount_hjzlqk(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (!YZUtility.isNullorEmpty(starttime)) {
        starttime = starttime + ConstUtil.TIME_START;
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        endtime = endtime + ConstUtil.TIME_END;
      }
      List<JSONObject> listAll = new ArrayList();
      
      JSONObject alljobj = this.logic.getHjzlqk(starttime, endtime, 0, organization);
      
      JSONObject yzljobj = this.logic.getHjzlqk(starttime, endtime, 1, organization);
      
      JSONObject wzlobj = new JSONObject();
      wzlobj.put("name", "已划价未治疗");
      wzlobj.put("ys", new BigDecimal(alljobj.getString("ys")).subtract(new BigDecimal(yzljobj.getString("ys"))).toString());
      wzlobj.put("paymoney", new BigDecimal(alljobj.getString("paymoney")).subtract(new BigDecimal(yzljobj.getString("paymoney"))).toString());
      wzlobj.put("y2", new BigDecimal(alljobj.getString("y2")).subtract(new BigDecimal(yzljobj.getString("y2"))).toString());
      listAll.add(wzlobj);
      
      yzljobj.put("name", "已划价已治疗");
      listAll.add(yzljobj);
      
      alljobj.put("name", "小计");
      listAll.add(alljobj);
      
      JSONObject jobj2 = new JSONObject();
      jobj2.put("data", listAll);
      YZUtility.DEAL_SUCCESS(jobj2, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCount_lszxxmtj.act"})
  public String selectCount_lszxxmtj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      List<YZDept> deptList = this.deptLogic.getSubOrgDeptListBySeqId("0", "", "");
      List<JSONObject> listAll = new ArrayList();
      List<JSONObject> listRegAllTotal = this.logic.getListRegByGhfl(map, "", "");
      
      int allnums = listRegAllTotal.size();
      
      int allcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
        String cjstatus = regJson.getString("cjstatus");
        if ("1".equals(cjstatus)) {
          allcjnums++;
        }
      }
      List<JSONObject> allskjeList = null;
      Object allskjeYjjList = null;
      allskjeList = this.logic.getYysrList(map);
      allskjeYjjList = this.logic.getYysrYjjList(map);
      

      String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
      
      String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
      
      String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
      
      String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
      
      String qtseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
      int hjqtallnums;
      for (YZDept organization : deptList)
      {
        List<JSONObject> listRegFilter = new ArrayList();
        int hjcjnums = 0;
        int hjwcjnums = 0;
        int hjczallnums = 0;
        int hjczcjnums = 0;
        int hjfzallnums = 0;
        int hjfzcjnums = 0;
        int hjzxfallnums = 0;
        int hjzxfcjnums = 0;
        int hjfcallnums = 0;
        int hjfccjnums = 0;
        hjqtallnums = 0;
        int hjqtcjnums = 0;
        String hjsk = "0.00";
        String hjss = "0.00";
        
        map.put("organization", organization.getDeptCode());
        List<JSONObject> skjeList = null;
        List<JSONObject> skjeYjjList = null;
        skjeList = this.logic.getYysrList(map);
        skjeYjjList = this.logic.getYysrYjjList(map);
        String cjstatus;
        for (JSONObject regJson : listRegAllTotal) {
          if (organization.getDeptCode().equals(regJson.getString("organization")))
          {
            listRegFilter.add(regJson);
            cjstatus = regJson.getString("cjstatus");
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
            if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjczcjnums++;
            }
            if (fzseqId.equals(recesort)) {
              hjfzallnums++;
            }
            if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjfzcjnums++;
            }
            if (zxfseqId.equals(recesort)) {
              hjzxfallnums++;
            }
            if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjzxfcjnums++;
            }
            if (fcseqId.equals(recesort)) {
              hjfcallnums++;
            }
            if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjfccjnums++;
            }
            if (qtseqId.equals(recesort)) {
              hjqtallnums++;
            }
            if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjqtcjnums++;
            }
            hjsk = getskje4AskPerson(skjeList, skjeYjjList, "", "");
            hjss = getskje4AskPersonSS(skjeList, skjeYjjList, "", "");
          }
        }
        if ((listRegFilter != null) && (listRegFilter.size() != 0))
        {
          List<YZDict> listDict = this.dictLogic.getListByParentCodeALL("GHFL", organization.getDeptCode());
          for (YZDict dict : listDict)
          {
            List<JSONObject> listRegDictFilter = new ArrayList();
            int cjnums = 0;
            int wcjnums = 0;
            int czallnums = 0;
            int czcjnums = 0;
            int fzallnums = 0;
            int fzcjnums = 0;
            int zxfallnums = 0;
            int zxfcjnums = 0;
            int fcallnums = 0;
            int fccjnums = 0;
            int qtallnums = 0;
            int qtcjnums = 0;
            String sk = "0.00";
            String ss = "0.00";
            for (JSONObject jsonReg : listRegFilter) {
              if (jsonReg.getString("regsort").equals(dict.getSeqId()))
              {
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
                if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                  czcjnums++;
                }
                if (fzseqId.equals(recesort)) {
                  fzallnums++;
                }
                if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                  fzcjnums++;
                }
                if (zxfseqId.equals(recesort)) {
                  zxfallnums++;
                }
                if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                  zxfcjnums++;
                }
                if (fcseqId.equals(recesort)) {
                  fcallnums++;
                }
                if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                  fccjnums++;
                }
                if (qtseqId.equals(recesort)) {
                  qtallnums++;
                }
                if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
                  qtcjnums++;
                }
                sk = getskje4AskPerson(skjeList, skjeYjjList, "", dict.getSeqId());
                ss = getskje4AskPersonSS(skjeList, skjeYjjList, "", dict.getSeqId());
              }
            }
            if ((listRegDictFilter != null) && (listRegDictFilter.size() != 0))
            {
              JSONObject obj = new JSONObject();
              
              obj.put("deptname", organization.getDeptName());
              
              obj.put("zxxm", dict.getDictName());
              
              int zxnums = listRegDictFilter.size();
              obj.put("zxnums", Integer.valueOf(zxnums));
              
              Float zzlzb = Float.valueOf(0.0F);
              if (allnums != 0) {
                zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
              }
              obj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
              

              obj.put("cjnums", Integer.valueOf(cjnums));
              
              Float cgl = Float.valueOf(0.0F);
              if (zxnums != 0) {
                cgl = Float.valueOf(cjnums * 100.0F / zxnums);
              }
              obj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
              
              Float cglzb = Float.valueOf(0.0F);
              if (allcjnums != 0) {
                cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
              }
              obj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
              
              obj.put("wcjnums", Integer.valueOf(wcjnums));
              
              obj.put("czallnums", Integer.valueOf(czallnums));
              
              obj.put("czcjnums", Integer.valueOf(czcjnums));
              
              obj.put("fzallnums", Integer.valueOf(fzallnums));
              
              obj.put("fzcjnums", Integer.valueOf(fzcjnums));
              
              obj.put("zxfallnums", Integer.valueOf(zxfallnums));
              
              obj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
              
              obj.put("fcallnums", Integer.valueOf(fcallnums));
              
              obj.put("fccjnums", Integer.valueOf(fccjnums));
              
              obj.put("qtallnums", Integer.valueOf(qtallnums));
              
              obj.put("qtcjnums", Integer.valueOf(qtcjnums));
              
              obj.put("ssje", ss);
              obj.put("skje", sk);
              listAll.add(obj);
            }
          }
          JSONObject objhj = new JSONObject();
          
          int zxnums = listRegFilter.size();
          objhj.put("zxnums", Integer.valueOf(zxnums));
          
          Float zzlzb = Float.valueOf(0.0F);
          if (allnums != 0) {
            zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
          }
          objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
          
          objhj.put("cjnums", Integer.valueOf(hjcjnums));
          
          Float cgl = Float.valueOf(0.0F);
          if (zxnums != 0) {
            cgl = Float.valueOf(hjcjnums * 100.0F / zxnums);
          }
          objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
          
          Float cglzb = Float.valueOf(0.0F);
          if (allcjnums != 0) {
            cglzb = Float.valueOf(hjcjnums * 100.0F / allcjnums);
          }
          objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
          
          objhj.put("wcjnums", Integer.valueOf(hjwcjnums));
          
          objhj.put("czallnums", Integer.valueOf(hjczallnums));
          
          objhj.put("czcjnums", Integer.valueOf(hjczcjnums));
          
          objhj.put("fzallnums", Integer.valueOf(hjfzallnums));
          
          objhj.put("fzcjnums", Integer.valueOf(hjfzcjnums));
          
          objhj.put("zxfallnums", Integer.valueOf(hjzxfallnums));
          
          objhj.put("zxfcjnums", Integer.valueOf(hjzxfcjnums));
          
          objhj.put("fcallnums", Integer.valueOf(hjfcallnums));
          
          objhj.put("fccjnums", Integer.valueOf(hjfccjnums));
          
          objhj.put("qtallnums", Integer.valueOf(hjqtallnums));
          
          objhj.put("qtcjnums", Integer.valueOf(hjqtcjnums));
          
          objhj.put("ssje", hjss);
          objhj.put("skje", hjsk);
          listAll.add(objhj);
        }
      }
      int cjnums = 0;
      int wcjnums = 0;
      int czallnums = 0;
      int czcjnums = 0;
      int fzallnums = 0;
      int fzcjnums = 0;
      int zxfallnums = 0;
      int zxfcjnums = 0;
      int fcallnums = 0;
      int qtallnums = 0;
      int fccjnums = 0;
      int qtcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
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
        if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          czcjnums++;
        }
        if (fzseqId.equals(recesort)) {
          fzallnums++;
        }
        if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fzcjnums++;
        }
        if (zxfseqId.equals(recesort)) {
          zxfallnums++;
        }
        if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          zxfcjnums++;
        }
        if (fcseqId.equals(recesort)) {
          fcallnums++;
        }
        if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fccjnums++;
        }
        if (qtseqId.equals(recesort)) {
          qtallnums++;
        }
        if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          qtcjnums++;
        }
      }
      JSONObject objzj = new JSONObject();
      
      int zxnums = allnums;
      objzj.put("zxnums", Integer.valueOf(zxnums));
      
      Float zzlzb = Float.valueOf(0.0F);
      if (allnums != 0) {
        zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
      }
      objzj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
      
      objzj.put("cjnums", Integer.valueOf(allcjnums));
      
      Float cgl = Float.valueOf(0.0F);
      if (zxnums != 0) {
        cgl = Float.valueOf(cjnums * 100.0F / zxnums);
      }
      objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
      
      Float cglzb = Float.valueOf(0.0F);
      if (allcjnums != 0) {
        cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
      }
      objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
      
      objzj.put("wcjnums", Integer.valueOf(wcjnums));
      

      objzj.put("czallnums", Integer.valueOf(czallnums));
      
      objzj.put("czcjnums", Integer.valueOf(czcjnums));
      

      objzj.put("fzallnums", Integer.valueOf(fzallnums));
      
      objzj.put("fzcjnums", Integer.valueOf(fzcjnums));
      

      objzj.put("zxfallnums", Integer.valueOf(zxfallnums));
      
      objzj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
      

      objzj.put("fcallnums", Integer.valueOf(fcallnums));
      

      objzj.put("fccjnums", Integer.valueOf(fccjnums));
      
      objzj.put("qtallnums", Integer.valueOf(qtallnums));
      
      objzj.put("qtcjnums", Integer.valueOf(qtcjnums));
      
      String sk = "0.00";
      sk = getskje4AskPerson(allskjeList, (List)allskjeYjjList, null, null);
      String ss = getskje4AskPersonSS(allskjeList, (List)allskjeYjjList, null, null);
      objzj.put("ssje", ss);
      objzj.put("skje", sk);
      if (zxnums > 0) {
        listAll.add(objzj);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCount_lsxmtj.act"})
  public String selectCount_lsxmtj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      List<YZDept> deptList = this.deptLogic.getSubOrgDeptListBySeqId("0", "", "");
      List<JSONObject> listAll = new ArrayList();
      List<JSONObject> listRegAllTotal = this.logic.getListRegByGhfl(map, "", "");
      
      int allnums = listRegAllTotal.size();
      
      int allcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
        String cjstatus = regJson.getString("cjstatus");
        if ("1".equals(cjstatus)) {
          allcjnums++;
        }
      }
      List<JSONObject> allskjeList = null;
      Object allskjeYjjList = null;
      allskjeList = this.logic.getYysrList(map);
      allskjeYjjList = this.logic.getYysrYjjList(map);
      

      String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
      
      String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
      
      String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
      
      String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
      
      String qtseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
      int hjqtallnums;
      for (YZDept organization : deptList)
      {
        List<JSONObject> listRegFilter = new ArrayList();
        int hjcjnums = 0;
        int hjwcjnums = 0;
        int hjczallnums = 0;
        int hjczcjnums = 0;
        int hjfzallnums = 0;
        int hjfzcjnums = 0;
        int hjzxfallnums = 0;
        int hjzxfcjnums = 0;
        int hjfcallnums = 0;
        int hjfccjnums = 0;
        hjqtallnums = 0;
        int hjqtcjnums = 0;
        String hjsk = "0.00";
        String hjss = "0.00";
        
        map.put("organization", organization.getDeptCode());
        List<JSONObject> skjeList = null;
        List<JSONObject> skjeYjjList = null;
        skjeList = this.logic.getYysrList(map);
        skjeYjjList = this.logic.getYysrYjjList(map);
        for (JSONObject regJson : listRegAllTotal) {
          if (organization.getDeptCode().equals(regJson.getString("organization")))
          {
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
            if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjczcjnums++;
            }
            if (fzseqId.equals(recesort)) {
              hjfzallnums++;
            }
            if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjfzcjnums++;
            }
            if (zxfseqId.equals(recesort)) {
              hjzxfallnums++;
            }
            if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjzxfcjnums++;
            }
            if (fcseqId.equals(recesort)) {
              hjfcallnums++;
            }
            if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjfccjnums++;
            }
            if (qtseqId.equals(recesort)) {
              hjqtallnums++;
            }
            if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
              hjqtcjnums++;
            }
            hjsk = getskje4AskPerson(skjeList, skjeYjjList, "", "");
            hjss = getskje4AskPersonSS(skjeList, skjeYjjList, "", "");
          }
        }
        if ((listRegFilter != null) && (listRegFilter.size() != 0))
        {
          JSONObject objhj = new JSONObject();
          objhj.put("deptname", organization.getDeptName());
          
          int zxnums = listRegFilter.size();
          objhj.put("zxnums", Integer.valueOf(zxnums));
          
          Float zzlzb = Float.valueOf(0.0F);
          if (allnums != 0) {
            zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
          }
          objhj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
          
          objhj.put("cjnums", Integer.valueOf(hjcjnums));
          
          Float cgl = Float.valueOf(0.0F);
          if (zxnums != 0) {
            cgl = Float.valueOf(hjcjnums * 100.0F / zxnums);
          }
          objhj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
          
          Float cglzb = Float.valueOf(0.0F);
          if (allcjnums != 0) {
            cglzb = Float.valueOf(hjcjnums * 100.0F / allcjnums);
          }
          objhj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
          
          objhj.put("wcjnums", Integer.valueOf(hjwcjnums));
          
          Float wcglzb = Float.valueOf(0.0F);
          if (allcjnums != 0) {
            wcglzb = Float.valueOf(hjwcjnums * 100.0F / allcjnums);
          }
          objhj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
          
          objhj.put("czallnums", Integer.valueOf(hjczallnums));
          
          objhj.put("czcjnums", Integer.valueOf(hjczcjnums));
          
          objhj.put("fzallnums", Integer.valueOf(hjfzallnums));
          
          objhj.put("fzcjnums", Integer.valueOf(hjfzcjnums));
          
          objhj.put("zxfallnums", Integer.valueOf(hjzxfallnums));
          
          objhj.put("zxfcjnums", Integer.valueOf(hjzxfcjnums));
          
          objhj.put("fcallnums", Integer.valueOf(hjfcallnums));
          
          objhj.put("fccjnums", Integer.valueOf(hjfccjnums));
          
          objhj.put("qtallnums", Integer.valueOf(hjqtallnums));
          
          objhj.put("qtcjnums", Integer.valueOf(hjqtcjnums));
          
          objhj.put("ssje", hjss);
          objhj.put("skje", hjsk);
          listAll.add(objhj);
        }
      }
      int cjnums = 0;
      int wcjnums = 0;
      int czallnums = 0;
      int czcjnums = 0;
      int fzallnums = 0;
      int fzcjnums = 0;
      int zxfallnums = 0;
      int zxfcjnums = 0;
      int fcallnums = 0;
      int qtallnums = 0;
      int fccjnums = 0;
      int qtcjnums = 0;
      for (JSONObject regJson : listRegAllTotal)
      {
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
        if ((czseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          czcjnums++;
        }
        if (fzseqId.equals(recesort)) {
          fzallnums++;
        }
        if ((fzseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fzcjnums++;
        }
        if (zxfseqId.equals(recesort)) {
          zxfallnums++;
        }
        if ((zxfseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          zxfcjnums++;
        }
        if (fcseqId.equals(recesort)) {
          fcallnums++;
        }
        if ((fcseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          fccjnums++;
        }
        if (qtseqId.equals(recesort)) {
          qtallnums++;
        }
        if ((qtseqId.equals(recesort)) && ("1".equals(cjstatus))) {
          qtcjnums++;
        }
      }
      JSONObject objzj = new JSONObject();
      
      int zxnums = allnums;
      objzj.put("zxnums", Integer.valueOf(zxnums));
      
      Float zzlzb = Float.valueOf(0.0F);
      if (allnums != 0) {
        zzlzb = Float.valueOf(zxnums * 100.0F / allnums);
      }
      objzj.put("zzlzb", YZUtility.FloatToFixed2(zzlzb) + "%");
      
      objzj.put("cjnums", Integer.valueOf(allcjnums));
      
      Float cgl = Float.valueOf(0.0F);
      if (zxnums != 0) {
        cgl = Float.valueOf(cjnums * 100.0F / zxnums);
      }
      objzj.put("cgl", YZUtility.FloatToFixed2(cgl) + "%");
      
      Float cglzb = Float.valueOf(0.0F);
      if (allcjnums != 0) {
        cglzb = Float.valueOf(cjnums * 100.0F / allcjnums);
      }
      objzj.put("cglzb", YZUtility.FloatToFixed2(cglzb) + "%");
      
      objzj.put("wcjnums", Integer.valueOf(wcjnums));
      Float wcglzb = Float.valueOf(0.0F);
      if (allcjnums != 0) {
        wcglzb = Float.valueOf(wcjnums * 100.0F / allcjnums);
      }
      objzj.put("wcglzb", YZUtility.FloatToFixed2(wcglzb) + "%");
      
      objzj.put("czallnums", Integer.valueOf(czallnums));
      
      objzj.put("czcjnums", Integer.valueOf(czcjnums));
      

      objzj.put("fzallnums", Integer.valueOf(fzallnums));
      
      objzj.put("fzcjnums", Integer.valueOf(fzcjnums));
      

      objzj.put("zxfallnums", Integer.valueOf(zxfallnums));
      
      objzj.put("zxfcjnums", Integer.valueOf(zxfcjnums));
      

      objzj.put("fcallnums", Integer.valueOf(fcallnums));
      

      objzj.put("fccjnums", Integer.valueOf(fccjnums));
      
      objzj.put("qtallnums", Integer.valueOf(qtallnums));
      
      objzj.put("qtcjnums", Integer.valueOf(qtcjnums));
      
      String sk = "0.00";
      sk = getskje4AskPerson(allskjeList, (List)allskjeYjjList, null, null);
      String ss = getskje4AskPersonSS(allskjeList, (List)allskjeYjjList, null, null);
      objzj.put("ssje", ss);
      objzj.put("skje", sk);
      if (zxnums > 0) {
        listAll.add(objzj);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectCount_lsxffltj.act"})
  public String selectCount_lsxffltj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(starttime))
      {
        starttime = starttime + ConstUtil.TIME_START;
        map.put("starttime", starttime);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        endtime = endtime + ConstUtil.TIME_END;
        map.put("endtime", endtime);
      }
      List<YZDept> deptList = this.deptLogic.getSubOrgDeptListBySeqId("0", "", "");
      
      List<JSONObject> listAll = new ArrayList();
      
      BigDecimal zj_ysje = BigDecimal.ZERO;
      BigDecimal zj_ssje = BigDecimal.ZERO;
      for (int i = 0; i < deptList.size(); i++)
      {
        BigDecimal hj_ysje = BigDecimal.ZERO;
        BigDecimal hj_ssje = BigDecimal.ZERO;
        YZDept objper = (YZDept)deptList.get(i);
        map.put("organization", objper.getDeptCode());
        
        List<JSONObject> zjcostdetailList = this.logic.getYysrDetailList(map);
        




        Set<String> currSortSet = new HashSet();
        Map<String, String> sortNameMap = new HashMap();
        for (JSONObject perDetail : zjcostdetailList)
        {
          String basetype = perDetail.getString("basetype");
          String nexttype = perDetail.getString("nexttype");
          String base_next = basetype + "," + nexttype;
          currSortSet.add(base_next);
          
          sortNameMap.put(basetype, perDetail.getString("basename"));
          sortNameMap.put(nexttype, perDetail.getString("nextname"));
        }
        for (String base_next : currSortSet) {
          if (!YZUtility.isNullorEmpty(base_next))
          {
            String[] sortArray = base_next.split(",");
            if (sortArray.length == 2)
            {
              String basetype = sortArray[0];
              String nexttype = sortArray[1];
              if ((!YZUtility.isNullorEmpty(basetype)) && (!YZUtility.isNullorEmpty(nexttype)))
              {
                JSONObject obj = new JSONObject();
                
                obj.put("deptname", objper.getDeptName());
                
                obj.put("basename", sortNameMap.get(basetype));
                
                obj.put("nextname", sortNameMap.get(nexttype));
                
                BigDecimal ysje = BigDecimal.ZERO;
                BigDecimal ssje = BigDecimal.ZERO;
                for (JSONObject perDetail : zjcostdetailList)
                {
                  String basetype2 = perDetail.getString("basetype");
                  String nexttype2 = perDetail.getString("nexttype");
                  if ((basetype.equals(basetype2)) && (nexttype.equals(nexttype2)))
                  {
                    String subtotal = perDetail.getString("subtotal");
                    String voidmoney = perDetail.getString("voidmoney");
                    BigDecimal ys = new BigDecimal(subtotal).subtract(new BigDecimal(voidmoney));
                    ysje = ysje.add(KqdsBigDecimal.round(ys, 2));
                    
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
            }
          }
        }
        JSONObject objhj = new JSONObject();
        objhj.put("ysje", KqdsBigDecimal.round(hj_ysje, 2));
        objhj.put("ssje", KqdsBigDecimal.round(hj_ssje, 2));
        listAll.add(objhj);
        
        zj_ysje = zj_ysje.add(hj_ysje);
        zj_ssje = zj_ssje.add(hj_ssje);
      }
      if (deptList.size() > 0)
      {
        JSONObject objzj = new JSONObject();
        objzj.put("ysje", KqdsBigDecimal.round(zj_ysje, 2));
        objzj.put("ssje", KqdsBigDecimal.round(zj_ssje, 2));
        listAll.add(objzj);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("rows", listAll);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
