package com.kqds.controller.base.visit;

import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.base.KqdsVisitSet;
import com.kqds.entity.base.VisitDataCount;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.visit.KQDS_VisitLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.UserPrivUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_VisitAct"})
public class KQDS_VisitAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_VisitAct.class);
  @Autowired
  private KQDS_VisitLogic logic;
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  
  @RequestMapping({"/toVisitWin.act"})
  public ModelAndView toVisitWin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/visit/visitWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitAdd.act"})
  public ModelAndView toVisitAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String lytype = request.getParameter("lytype");
    String type = request.getParameter("type");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("lytype", lytype);
    mv.addObject("type", type);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/visit/visit_add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitPlanAdd.act"})
  public ModelAndView toVisitPlanAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String lytype = request.getParameter("lytype");
    String type = request.getParameter("type");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("lytype", lytype);
    mv.addObject("type", type);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/visit/visit_plan_add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitPlansAdd.act"})
  public ModelAndView toVisitPlansAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercodes = request.getParameter("usercodes");
    String type = request.getParameter("type");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercodes", usercodes);
    mv.addObject("type", type);
    mv.setViewName("/kqdsFront/visit/visit_plans_add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitPlanManage.act"})
  public ModelAndView toVisitPlanManage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/visit/visit_manage.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitAddf.act"})
  public ModelAndView toVisitAddf(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String lytype = request.getParameter("lytype");
    String type = request.getParameter("type");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("lytype", lytype);
    mv.addObject("type", type);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/visit/visit_addf.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitDetail.act"})
  public ModelAndView toVisitDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/visit/visit_detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitEdit.act"})
  public ModelAndView toVisitEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String type = request.getParameter("type");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("type", type);
    mv.setViewName("/kqdsFront/visit/visit_edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitPost.act"})
  public ModelAndView toVisitPost(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String type = request.getParameter("type");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("type", type);
    mv.setViewName("/kqdsFront/visit/visit_post.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitList.act"})
  public ModelAndView toVisitList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/index/center/visit_list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitList3.act"})
  public ModelAndView toVisitList3(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String visittime = request.getParameter("visittime");
    String visitor = request.getParameter("visitor");
    ModelAndView mv = new ModelAndView();
    mv.addObject("visitor", visitor);
    mv.addObject("visittime", visittime);
    mv.setViewName("/kqdsFront/visit/visit_list2.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVisitList2.act"})
  public ModelAndView toVisitList2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String is_Wd_oper = request.getParameter("is_Wd_oper");
    ModelAndView mv = new ModelAndView();
    mv.addObject("is_Wd_oper", is_Wd_oper);
    mv.setViewName("/kqdsFront/visit/visit_list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUserVisit.act"})
  public ModelAndView toUserVisit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/visit/user_visit.jsp");
    return mv;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      KqdsVisit visit = (KqdsVisit)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_VISIT, seqId);
      if (visit == null) {
        throw new Exception("回访记录不存在");
      }
      String visitTime = visit.getVisittime();
      if (YZUtility.isNullorEmpty(visitTime)) {
        throw new Exception("回访时间值为空");
      }
      if (visitTime.length() == 16) {
        visitTime = visitTime + ":00";
      }
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date vistDate = df.parse(visitTime);
      Date currDate = new Date();
      if (currDate.getTime() >= vistDate.getTime()) {
        throw new Exception("回访时间已过，不能删除");
      }
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_VISIT, seqId);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_VISIT, visit, visit.getUsercode(), TableNameUtil.KQDS_VISIT, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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
      
      KqdsVisit dp = new KqdsVisit();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String usercode = request.getParameter("usercode");
      String username = request.getParameter("username");
      String sex = request.getParameter("sex");
      String telphone = request.getParameter("telphone");
      if (!YZUtility.isNullorEmpty(dp.getVisittime()))
      {
        if (dp.getVisittime().length() != 10) {
          throw new Exception("回访时间格式不正确，请确保长度为10位，即精确到天钟。");
        }
        if (dp.getVisittime().contains("：")) {
          throw new Exception("回访时间格式不正确，不允许包含中文分号。");
        }
      }
      if (!YZUtility.isNullorEmpty(seqId))
      {
        String myd = request.getParameter("myd");
        if (!YZUtility.isNullorEmpty(myd))
        {
          dp.setStatus(ConstUtil.VISIT_STATUS_1);
          dp.setFinishtime(YZUtility.getCurDateTimeStr());
          
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.SUBMIT, BcjlUtil.KQDS_VISIT, dp, dp.getUsercode(), TableNameUtil.KQDS_VISIT, request);
        }
        else
        {
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_VISIT, dp, dp.getUsercode(), TableNameUtil.KQDS_VISIT, request);
          
          PushUtil.saveTx4ModifyVisit(dp, request);
        }
        this.logic.updateSingleUUID(TableNameUtil.KQDS_VISIT, dp);
      }
      else
      {
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        dp.setUsercode(usercode);
        dp.setUsername(username);
        dp.setSex(sex);
        dp.setTelphone(telphone);
        

        YZPerson visitor = (YZPerson)this.logic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, dp.getVisitor());
        Map<String, String> map = new HashMap();
        map.put("hffl", dp.getHffl());
        map.put("userpriv", visitor.getUserPriv());
        map.put("organization", ChainUtil.getCurrentOrganization(request));
        List<KqdsVisitSet> visitSetList = (List)this.logic.loadList(TableNameUtil.KQDS_VISIT_SET, map);
        if ((visitSetList != null) && (visitSetList.size() > 0)) {
          dp.setIsfirstday(Integer.valueOf(ConstUtil.VISIT_IS_FIRST_1));
        }
        this.logic.saveSingleUUID(TableNameUtil.KQDS_VISIT, dp);
        
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, dp, dp.getUsercode(), TableNameUtil.KQDS_VISIT, request);
        
        PushUtil.saveTx4NewVisit(dp, request);
        if ((visitSetList != null) && (visitSetList.size() > 0)) {
          addAutovisit(dp, visitSetList, person, request);
        }
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate1.act"})
  public String insertOrUpdate1(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String usercode = request.getParameter("usercode");
      String username = request.getParameter("username");
      String sex = request.getParameter("sex");
      String telphone = request.getParameter("telphone");
      String visitor = request.getParameter("visitor");
      

      String smallList = request.getParameter("smallList");
      JSONArray listArray = JSONArray.fromObject(smallList);
      List<JSONObject> list2 = JSONArray.toList(listArray, new JSONObject(), new JsonConfig());
      List<KqdsVisit> visitList = new ArrayList();
      for (JSONObject jsonObject : list2)
      {
        KqdsVisit dp = new KqdsVisit();
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        if (!YZUtility.isNullorEmpty(sex)) {
          dp.setSex(sex);
        }
        if (!YZUtility.isNullorEmpty(usercode)) {
          dp.setUsercode(usercode);
        }
        if (!YZUtility.isNullorEmpty(username)) {
          dp.setUsername(username);
        }
        if (!YZUtility.isNullorEmpty(telphone)) {
          dp.setTelphone(telphone);
        }
        if (!YZUtility.isNullorEmpty(visitor)) {
          dp.setVisitor(visitor);
        }
        if ((!YZUtility.isNullorEmpty(jsonObject.getString("plandays"))) && 
          (!YZUtility.isNullorEmpty(jsonObject.getString("nowDate"))))
        {
          int d = Integer.parseInt(jsonObject.getString("plandays"));
          String da = jsonObject.getString("nowDate");
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date dateA = sdf.parse(da);
          dateA.setDate(dateA.getDate() + d);
          dp.setVisittime(sdf.format(dateA));
        }
        if (!YZUtility.isNullorEmpty(jsonObject.getString("visittype"))) {
          dp.setVisittype(jsonObject.getString("visittype"));
        }
        if (!YZUtility.isNullorEmpty(jsonObject.getString("accepttypeId"))) {
          dp.setAccepttype(jsonObject.getString("accepttypeId"));
        }
        if (!YZUtility.isNullorEmpty(jsonObject.getString("visittypeId"))) {
          dp.setHffl(jsonObject.getString("visittypeId"));
        }
        if (!YZUtility.isNullorEmpty(jsonObject.getString("remark"))) {
          dp.setHfyd(jsonObject.getString("remark"));
        }
        visitList.add(dp);
      }
      this.logic.saveList(visitList);
      












      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, usercode + username + sex + telphone + visitor + smallList, usercode, TableNameUtil.KQDS_VISIT, request);
      






      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate2.act"})
  public String insertOrUpdate2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String usercode = request.getParameter("usercode");
      String visitor = request.getParameter("visitor");
      String smallList = request.getParameter("smallList");
      JSONArray listArray = JSONArray.fromObject(smallList);
      List<JSONObject> list2 = JSONArray.toList(listArray, new JSONObject(), new JsonConfig());
      List<KqdsVisit> visitList = new ArrayList();
      String[] usercodes = usercode.split(",");
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < usercodes.length; i++) {
        if (i == 0) {
          str.append("'" + usercodes[i] + "'");
        } else {
          str.append(",'" + usercodes[i] + "'");
        }
      }
      List<JSONObject> l = this.userLogic.findKqdsUserdocumentByUsercodes(str.toString());
      Iterator localIterator2;
      for (Iterator localIterator1 = l.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
      {
        JSONObject json = (JSONObject)localIterator1.next();
        localIterator2 = list2.iterator(); continue;JSONObject jsonObject = (JSONObject)localIterator2.next();
        KqdsVisit dp = new KqdsVisit();
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        if (!YZUtility.isNullorEmpty(json.getString("usercode"))) {
          dp.setUsercode(json.getString("usercode"));
        }
        if (!YZUtility.isNullorEmpty(visitor)) {
          dp.setVisitor(visitor);
        }
        if (!YZUtility.isNullorEmpty(json.getString("sex"))) {
          dp.setSex(json.getString("sex"));
        }
        if (!YZUtility.isNullorEmpty(json.getString("username"))) {
          dp.setUsername(json.getString("username"));
        }
        if (!YZUtility.isNullorEmpty(json.getString("phonenumber1"))) {
          dp.setTelphone(json.getString("phonenumber1"));
        }
        if ((!YZUtility.isNullorEmpty(jsonObject.getString("plandays"))) && 
          (!YZUtility.isNullorEmpty(jsonObject.getString("nowDate"))))
        {
          int d = Integer.parseInt(jsonObject.getString("plandays"));
          String da = jsonObject.getString("nowDate");
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date dateA = sdf.parse(da);
          dateA.setDate(dateA.getDate() + d);
          dp.setVisittime(sdf.format(dateA));
        }
        if (!YZUtility.isNullorEmpty(jsonObject.getString("visittype"))) {
          dp.setVisittype(jsonObject.getString("visittype"));
        }
        if (!YZUtility.isNullorEmpty(jsonObject.getString("accepttypeId"))) {
          dp.setAccepttype(jsonObject.getString("accepttypeId"));
        }
        if (!YZUtility.isNullorEmpty(jsonObject.getString("visittypeId"))) {
          dp.setHffl(jsonObject.getString("visittypeId"));
        }
        if (!YZUtility.isNullorEmpty(jsonObject.getString("remark"))) {
          dp.setHfyd(jsonObject.getString("remark"));
        }
        visitList.add(dp);
      }
      this.logic.saveList(visitList);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, usercode + visitor + smallList, usercode, TableNameUtil.KQDS_VISIT, request);
      
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/addAutovisit.act"})
  public void addAutovisit(KqdsVisit dp, List<KqdsVisitSet> visitSetList, YZPerson person, HttpServletRequest request)
    throws Exception
  {
    String firstVisittime = dp.getVisittime() + ":00";
    for (KqdsVisitSet vset : visitSetList)
    {
      KqdsVisit visit = dp;
      visit.setSeqId(YZUtility.getUUID());
      visit.setRemark(vset.getRemark());
      visit.setPurpose(vset.getPurpose());
      visit.setIsauto(Integer.valueOf(ConstUtil.VISIT_IS_AUTO_1));
      visit.setHfyd(vset.getPurpose());
      
      Date visittime = YZUtility.getDayAfter(firstVisittime, vset.getJgday().intValue());
      String visittimeStr = YZUtility.getDateTimeStr(visittime);
      visit.setVisittime(visittimeStr.substring(0, visittimeStr.length() - 3));
      this.logic.saveSingleUUID(TableNameUtil.KQDS_VISIT, visit);
      

      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT, visit, visit.getUsercode(), TableNameUtil.KQDS_VISIT, request);
      
      PushUtil.saveTx4NewVisit(visit, request);
    }
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      
      KqdsVisit en = (KqdsVisit)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_VISIT, seqId);
      if (en == null) {
        throw new Exception("数据不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectListNotByPersonId.act"})
  public String selectListNotByPersonId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Map<String, String> map = new HashMap();
      
      String usercode = request.getParameter("usercode");
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      String myd = request.getParameter("myd");
      if (!YZUtility.isNullorEmpty(myd)) {
        map.put("myd", myd);
      }
      String visittime = request.getParameter("visittime");
      if (!YZUtility.isNullorEmpty(visittime)) {
        map.put("visittime", visittime);
      }
      String username = request.getParameter("username");
      if (!YZUtility.isNullorEmpty(username)) {
        map.put("username", username);
      }
      String visitor = request.getParameter("visitor");
      if (!YZUtility.isNullorEmpty(visitor)) {
        map.put("visitor", visitor);
      }
      String hffl = request.getParameter("hffl");
      if (!YZUtility.isNullorEmpty(hffl)) {
        map.put("hffl", hffl);
      }
      String status = request.getParameter("status");
      if (!YZUtility.isNullorEmpty(status)) {
        map.put("status", status);
      }
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      
      List<JSONObject> list = this.logic.selectList(TableNameUtil.KQDS_VISIT, map);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectListByPersonIdAndTime.act"})
  public String selectListByPersonIdAndTime(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      
      String usercode = request.getParameter("usercode");
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      String myd = request.getParameter("myd");
      if (!YZUtility.isNullorEmpty(myd)) {
        map.put("myd", myd);
      }
      String visittime = request.getParameter("visittime");
      if (!YZUtility.isNullorEmpty(visittime))
      {
        map.put("visittime", visittime);
      }
      else
      {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        map.put("visittime", df.format(new Date()));
      }
      String username = request.getParameter("username");
      if (!YZUtility.isNullorEmpty(username)) {
        map.put("username", username);
      }
      String visitor = request.getParameter("visitor");
      if (!YZUtility.isNullorEmpty(visitor)) {
        map.put("visitor", visitor);
      }
      String hffl = request.getParameter("hffl");
      if (!YZUtility.isNullorEmpty(hffl)) {
        map.put("hffl", hffl);
      }
      String status = request.getParameter("status");
      if (!YZUtility.isNullorEmpty(status)) {
        map.put("status", status);
      }
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      if (!YZUtility.isNullorEmpty(sortName))
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      }
      JSONObject list = this.logic.selectList(bp, TableNameUtil.KQDS_VISIT, map);
      
      YZUtility.DEAL_SUCCESS(list, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectListNotByPersonIdToday.act"})
  public String selectListNotByPersonIdToday(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      if (person.getUserPriv().equals(UserPrivUtil.ADMIN_SEQ_ID)) {
        visualstaff = "";
      }
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      map.put("sortName", sortName);
      map.put("sortOrder", sortOrder);
      JSONObject json = this.logic.selectListMyToday(TableNameUtil.KQDS_VISIT, visualstaff, ChainUtil.getCurrentOrganization(request), bp, map);
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getCountByQuery.act"})
  public String getCountByQuery(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Map<String, String> map = new HashMap();
      String vtime1 = request.getParameter("vtime1");
      String vtime2 = request.getParameter("vtime2");
      String name = request.getParameter("username");
      String ishuifang = request.getParameter("ishuifang");
      String hffl = request.getParameter("hffl");
      String renwu = request.getParameter("renwu");
      String visitor = request.getParameter("visitor");
      String visitdept = request.getParameter("visitdept");
      if ((!YZUtility.isNullorEmpty(visitdept)) && (!visitdept.equals("null")) && (!visitdept.equals("undefined"))) {
        map.put("visitdept", visitdept);
      }
      String myd = request.getParameter("myd");
      String phonenumber1 = request.getParameter("phonenumber1");
      if ((!YZUtility.isNullorEmpty(vtime1)) && (!vtime1.equals("null")) && (!vtime1.equals("undefined")))
      {
        if (vtime1.length() == 10)
        {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date date = sdf.parse(vtime1);
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(5, -1);
          String yestedayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
          vtime1 = yestedayDate + ConstUtil.HOUR_START;
        }
        map.put("vtime1", vtime1);
      }
      if ((!YZUtility.isNullorEmpty(vtime2)) && (!vtime2.equals("null")) && (!vtime2.equals("undefined")))
      {
        if (vtime2.length() == 10) {
          vtime2 = vtime2 + ConstUtil.HOUR_END;
        }
        map.put("vtime2", vtime2);
      }
      if ((YZUtility.isNullorEmpty(vtime1)) && (YZUtility.isNullorEmpty(vtime2)))
      {
        String curDate = YZUtility.getDateStr(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -1);
        String yestedayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        map.put("vtime1", yestedayDate + ConstUtil.HOUR_START);
        map.put("vtime2", curDate + ConstUtil.HOUR_END);
      }
      if ((!YZUtility.isNullorEmpty(name)) && (!name.equals("null")) && (!name.equals("undefined"))) {
        map.put("name", name);
      }
      if ((!YZUtility.isNullorEmpty(ishuifang)) && (!ishuifang.equals("null")) && (!ishuifang.equals("undefined"))) {
        map.put("ishuifang", ishuifang);
      }
      if ((!YZUtility.isNullorEmpty(hffl)) && (!hffl.equals("null")) && (!hffl.equals("undefined"))) {
        map.put("hffl", hffl);
      }
      if ((!YZUtility.isNullorEmpty(renwu)) && (!renwu.equals("null")) && (!renwu.equals("undefined"))) {
        map.put("renwu", renwu);
      }
      if ((!YZUtility.isNullorEmpty(visitor)) && (!visitor.equals("null")) && (!visitor.equals("undefined"))) {
        map.put("visitor", visitor);
      }
      if ((!YZUtility.isNullorEmpty(myd)) && (!myd.equals("null")) && (!myd.equals("undefined"))) {
        map.put("myd", myd);
      }
      if ((!YZUtility.isNullorEmpty(phonenumber1)) && (!myd.equals("null")) && (!myd.equals("undefined"))) {
        map.put("phonenumber1", phonenumber1);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      JSONObject jobj = new JSONObject();
      
      int total = this.logic.getCountByQuery(TableNameUtil.KQDS_VISIT, map, visualstaff, organization);
      jobj.put("total", Integer.valueOf(total));
      
      List<VisitDataCount> groupCountList = this.logic.selectCountByQuery(TableNameUtil.KQDS_VISIT, map, visualstaff, organization);
      

      VisitDataCount all = new VisitDataCount();
      int allcount = 0;
      for (VisitDataCount dataCount : groupCountList) {
        allcount += Integer.parseInt(dataCount.getCount());
      }
      all.setCount(allcount);
      all.setHffl("allHFFL");
      all.setName("全部");
      groupCountList.add(all);
      

      map.put("status", "1");
      int yi = this.logic.getCountByQuery(TableNameUtil.KQDS_VISIT, map, visualstaff, organization);
      jobj.put("yihuifang", Integer.valueOf(yi));
      

      jobj.put("flcounts", groupCountList);
      
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectList4returnVisit.act"})
  public String selectList4returnVisit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Map<String, String> map = new HashMap();
      
      String vtime1 = request.getParameter("vtime1");
      String vtime2 = request.getParameter("vtime2");
      String name = request.getParameter("username");
      String ishuifang = request.getParameter("ishuifang");
      String hffl = request.getParameter("hffl");
      String visitor = request.getParameter("visitor");
      String myd = request.getParameter("myd");
      String createtime = request.getParameter("createtime");
      String phonenumber1 = request.getParameter("phonenumber1");
      String visitdept = request.getParameter("visitdept");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      if ((!YZUtility.isNullorEmpty(visitdept)) && (!visitdept.equals("null")) && (!visitdept.equals("undefined"))) {
        map.put("visitdept", visitdept);
      }
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      
      JSONObject json = new JSONObject();
      if ((!YZUtility.isNullorEmpty(createtime)) && (!createtime.equals("null")) && (!createtime.equals("undefined"))) {
        map.put("createtime", createtime);
      }
      if ((!YZUtility.isNullorEmpty(vtime1)) && (!vtime1.equals("null")) && (!vtime1.equals("undefined")))
      {
        if (vtime1.length() == 10)
        {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date date = sdf.parse(vtime1);
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(5, -1);
          String yestedayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
          vtime1 = yestedayDate + ConstUtil.HOUR_START;
        }
        map.put("vtime1", vtime1);
      }
      if ((!YZUtility.isNullorEmpty(vtime2)) && (!vtime2.equals("null")) && (!vtime2.equals("undefined")))
      {
        if (vtime2.length() == 10) {
          vtime2 = vtime2 + ConstUtil.HOUR_END;
        }
        map.put("vtime2", vtime2);
      }
      if ((YZUtility.isNullorEmpty(vtime1)) && (YZUtility.isNullorEmpty(vtime2)))
      {
        String curDate = YZUtility.getDateStr(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -1);
        String yestedayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        map.put("vtime1", yestedayDate + ConstUtil.HOUR_START);
        map.put("vtime2", curDate + ConstUtil.HOUR_END);
      }
      if ((!YZUtility.isNullorEmpty(name)) && (!name.equals("null")) && (!name.equals("undefined"))) {
        map.put("name", name);
      }
      if ((!YZUtility.isNullorEmpty(ishuifang)) && (!ishuifang.equals("null")) && (!ishuifang.equals("undefined"))) {
        map.put("ishuifang", ishuifang);
      }
      if ((!YZUtility.isNullorEmpty(hffl)) && (!hffl.equals("null")) && (!hffl.equals("undefined"))) {
        map.put("hffl", hffl);
      }
      if ((!YZUtility.isNullorEmpty(visitor)) && (!visitor.equals("null")) && (!visitor.equals("undefined"))) {
        map.put("visitor", visitor);
      }
      if ((!YZUtility.isNullorEmpty(myd)) && (!myd.equals("null")) && (!myd.equals("undefined"))) {
        map.put("myd", myd);
      }
      if ((!YZUtility.isNullorEmpty(phonenumber1)) && (!phonenumber1.equals("null")) && (!phonenumber1.equals("undefined"))) {
        map.put("phonenumber1", phonenumber1);
      }
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      if (!YZUtility.isNullorEmpty(sortName)) {
        map.put("sortName", sortName);
      }
      if (!YZUtility.isNullorEmpty(sortOrder)) {
        map.put("sortOrder", sortOrder);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      

      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject list = this.logic.selectList4returnVisit(TableNameUtil.KQDS_VISIT, bp, map, visualstaff, organization, json, flag);
      List<JSONObject> list0 = list.getJSONArray("rows");
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("回访中心", fieldArr, fieldnameArr, list0, response, request);
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
  
  @RequestMapping({"/selectCostorderDetailByUsercode.act"})
  public String selectCostorderDetailByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      Map<String, String> map = new HashMap();
      map.put("UserCode", usercode);
      
      List<KqdsUserdocument> en = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
      
      JSONObject jobj = new JSONObject();
      if ((en != null) && (en.size() > 0)) {
        jobj.put("data", en.get(0));
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
