package com.kqds.controller.base.chufang;

import com.hudh.lclj.util.CreateRecipeCodeUtil;
import com.kqds.entity.base.KqdsChufang;
import com.kqds.entity.base.KqdsChufangDetail;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.chufang.KQDS_ChuFangLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_ChuFangAct"})
public class KQDS_ChuFangAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_ChuFangAct.class);
  @Autowired
  private KQDS_ChuFangLogic logic;
  @Autowired
  private YZPersonLogic personLogic;
  
  @RequestMapping({"/toSearch_ChuFang.act"})
  public ModelAndView toSearch_ChuFang(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/chufang/search_chufang.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail_ChuFang.act"})
  public ModelAndView toDetail_ChuFang(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/chufang/detail_ypchufang.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail_HuaYan.act"})
  public ModelAndView toDetail_HuaYan(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/chufang/detail_chufang.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit_ChuFang.act"})
  public ModelAndView toEdit_ChuFang(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/chufang/edit_chufang.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAdd_ChuFang.act"})
  public ModelAndView toAdd_ChuFang(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String costno = request.getParameter("costno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("costno", costno);
    mv.setViewName("/kqdsFront/chufang/add_chufang.jsp");
    return mv;
  }
  
  @RequestMapping({"/searchChuFang.act"})
  public String searchChuFang(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String regdept = request.getParameter("regdept");
      String createuser = request.getParameter("createuser");
      String searchvalue = request.getParameter("searchvalue");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      Map<String, String> map = new HashMap();
      if ((starttime != null) && (!starttime.equals(""))) {
        map.put("starttime", starttime);
      }
      if ((endtime != null) && (!endtime.equals(""))) {
        map.put("endtime", endtime);
      }
      if ((searchvalue != null) && (!searchvalue.equals(""))) {
        map.put("searchvalue", searchvalue);
      }
      if ((usercode != null) && (!usercode.equals(""))) {
        map.put("usercode", usercode);
      }
      if ((regdept != null) && (!regdept.equals(""))) {
        map.put("regdept", regdept);
      }
      if ((createuser != null) && (!createuser.equals(""))) {
        map.put("createuser", createuser);
      }
      if ((sortName != null) && (!sortName.equals("")))
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      }
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      String visualstaff = SessionUtil.getVisualstaff(request);
      JSONObject list = this.logic.searchChuFang(TableNameUtil.KQDS_CHUFANG_DETAIL, map, visualstaff, ChainUtil.getOrganizationFromUrl(request), bp);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("处方查询", fieldArr, fieldnameArr, list.getJSONArray("rows"), response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/countCF.act"})
  public String countCF(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String costno = request.getParameter("costno");
      int count = this.logic.countCfByCostno(costno);
      JSONObject returnObj = new JSONObject();
      returnObj.put("count", Integer.valueOf(count));
      YZUtility.DEAL_SUCCESS(returnObj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/jinYongChuFang.act"})
  public String jinYongChuFang(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      
      KqdsChufang cf = (KqdsChufang)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CHUFANG, seqId);
      cf.setStatus(Integer.valueOf(1));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_CHUFANG, cf);
      

      BcjlUtil.LogBcjl(BcjlUtil.DISABLE, BcjlUtil.KQDS_CHUFANG, cf, TableNameUtil.KQDS_CHUFANG, request);
      
      JSONObject returnObj = new JSONObject();
      YZUtility.DEAL_SUCCESS(returnObj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getListByCostNo.act"})
  public String getListByCostNo(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String costno = request.getParameter("costno");
      Map<String, String> filters = new HashMap();
      filters.put("costno", costno);
      List<JSONObject> list = (List)this.logic.loadList(TableNameUtil.KQDS_CHUFANG, filters);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getChuFangObjBySeqId.act"})
  public String getChuFangObjBySeqId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      KqdsChufang cf = (KqdsChufang)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CHUFANG, seqId);
      
      Map<String, String> filters = new HashMap();
      filters.put("chufangno", seqId);
      List<JSONObject> cfdetailList = (List)this.logic.loadList(TableNameUtil.KQDS_CHUFANG_DETAIL, filters);
      

      String doctorName = "";
      YZPerson doctorPerson = this.personLogic.getPersonBySeqId(cf.getDoctor());
      if (doctorPerson != null) {
        doctorName = doctorPerson.getUserName();
      }
      JSONObject returnObj = new JSONObject();
      returnObj.put("cf", cf);
      returnObj.put("cfdetailList", cfdetailList);
      returnObj.put("doctorName", doctorName);
      YZUtility.DEAL_SUCCESS(returnObj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/findChuFangInfor"})
  public String findChuFangInfor(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      KqdsChufang cf = (KqdsChufang)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CHUFANG, seqId);
      Map<String, String> map = new HashMap();
      map.put("chufangno", seqId);
      List<JSONObject> list = this.logic.findChuFangInfor(map);
      
      JSONObject returnObj = new JSONObject();
      returnObj.put("cf", cf);
      returnObj.put("list", list);
      YZUtility.DEAL_SUCCESS(returnObj, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsChufang dp = new KqdsChufang();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String costno = request.getParameter("costno");
      
      String uuid = "";
      
      KqdsCostorder cost = (KqdsCostorder)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
      if (!YZUtility.isNullorEmpty(seqId))
      {
        uuid = seqId;
        
        KqdsChufang cf = (KqdsChufang)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CHUFANG, seqId);
        cf.setRemark(dp.getRemark());
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CHUFANG, cf);
        

        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_CHUFANG, dp, dp.getUsercode(), TableNameUtil.KQDS_CHUFANG, request);
      }
      else
      {
        uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCostno(cost.getSeqId());
        dp.setUsercode(cost.getUsercode());
        dp.setRegno(cost.getRegno());
        dp.setDoctor(cost.getDoctor());
        dp.setNurse(cost.getNurse());
        dp.setTechperson(cost.getTechperson());
        
        CreateRecipeCodeUtil.getInstance();String orderNumber = CreateRecipeCodeUtil.getNextOrderNumber();
        dp.setRecipecode(orderNumber);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CHUFANG, dp);
        

        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHUFANG, dp, dp.getUsercode(), TableNameUtil.KQDS_CHUFANG, request);
      }
      String listdata = request.getParameter("listDetail");
      JSONArray jArray = JSONArray.fromObject(listdata);
      Collection collection = JSONArray.toCollection(jArray, KqdsChufangDetail.class);
      Iterator it = collection.iterator();
      

      KqdsChufangDetail detail = new KqdsChufangDetail();
      while (it.hasNext())
      {
        detail = (KqdsChufangDetail)it.next();
        if (!YZUtility.isNullorEmpty(detail.getSeqId()))
        {
          KqdsChufangDetail detail2 = (KqdsChufangDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CHUFANG_DETAIL, detail.getSeqId());
          detail2.setCfusage(detail.getCfusage());
          detail2.setCfuselevel(detail.getCfuselevel());
          detail2.setCfusemethod(detail.getCfusemethod());
          this.logic.updateSingleUUID(TableNameUtil.KQDS_CHUFANG_DETAIL, detail);
        }
        else
        {
          detail.setSeqId(YZUtility.getUUID());
          
          detail.setUsercode(cost.getUsercode());
          detail.setCostno(cost.getSeqId());
          detail.setRegno(cost.getRegno());
          detail.setChufangno(uuid);
          detail.setCreatetime(YZUtility.getCurDateTimeStr());
          detail.setCreateuser(person.getSeqId());
          detail.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_CHUFANG_DETAIL, detail);
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", uuid);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getByRegno.act"})
  public String getByRegno(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String regno = request.getParameter("regno");
      Map<String, String> map = new HashMap();
      map.put("regno", regno);
      List<KqdsCostorder> en = (List)this.logic.loadList(TableNameUtil.KQDS_COSTORDER, map);
      JSONObject jobj = new JSONObject();
      jobj.put("data", en.get(0));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getRecipeCodeById.act"})
  public String getRecipeCodeById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String costno = request.getParameter("costno");
      JSONObject json = this.logic.getRecipeCodeById(costno);
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
