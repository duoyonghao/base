package com.kqds.controller.base.lltj;

import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsLltj;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.lltj.Kqds_LLTJLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_LLTJAct"})
public class KQDS_LLTJAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_LLTJAct.class);
  @Autowired
  private Kqds_LLTJLogic logic;
  
  @RequestMapping({"/toLingliao.act"})
  public ModelAndView toLingliao(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String treatdetailno = request.getParameter("treatdetailno");
    String username = request.getParameter("username");
    String usercode = request.getParameter("usercode");
    String itemno = request.getParameter("itemno");
    String unit = request.getParameter("unit");
    ModelAndView mv = new ModelAndView();
    mv.addObject("treatdetailno", treatdetailno);
    mv.addObject("username", username);
    mv.addObject("usercode", usercode);
    mv.addObject("itemno", itemno);
    mv.addObject("unit", unit);
    mv.setViewName("/kqdsFront/lltj/lingliao.jsp");
    return mv;
  }
  
  @RequestMapping({"/toLltj.act"})
  public ModelAndView toLltj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/lltj/lltj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toLltjQuery.act"})
  public ModelAndView toLltjQuery(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/lltj/lltj_query.jsp");
    return mv;
  }
  
  @RequestMapping({"/chaiFen.act"})
  public String chaiFen(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String queryinput = request.getParameter("queryinput");
      
      Map<String, String> map = new HashMap();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date d = new Date();
      if ((YZUtility.isNullorEmpty(starttime)) && (YZUtility.isNullorEmpty(endtime)))
      {
        starttime = sdf.format(d) + ConstUtil.TIME_START;
        endtime = sdf.format(d) + ConstUtil.TIME_END;
        map.put("starttime", starttime);
        map.put("endtime", endtime);
      }
      else
      {
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
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      map.put("issplit", ConstUtil.IS_SPLIT_0);
      

      List<JSONObject> list1 = this.logic.getCostDetailList4LltjChaifen(TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, ChainUtil.getCurrentOrganization(request));
      for (int j = 0; j < list1.size(); j++)
      {
        JSONObject jo = (JSONObject)list1.get(j);
        
        KqdsCostorderDetail costdetail = (KqdsCostorderDetail)JSONObject.toBean(jo, KqdsCostorderDetail.class);
        
        int num = Integer.parseInt(costdetail.getNum());
        if (num > 0) {
          for (int i = 0; i < num; i++)
          {
            KqdsLltj treatdetail = (KqdsLltj)JSONObject.toBean(jo, KqdsLltj.class);
            String uuid = YZUtility.getUUID();
            treatdetail.setSeqId(uuid);
            treatdetail.setCostdetailno(costdetail.getSeqId());
            treatdetail.setNum("1");
            treatdetail.setOrganization(ChainUtil.getCurrentOrganization(request));
            this.logic.saveSingleUUID(TableNameUtil.KQDS_LLTJ, treatdetail);
          }
        }
        costdetail.setIssplit(Integer.valueOf(1));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, costdetail);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getAllTreatDetailList.act"})
  public String getAllTreatDetailList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String costno = request.getParameter("costno");
      String cjStatus = request.getParameter("cjStatus");
      String qf = request.getParameter("qf");
      String basetype = request.getParameter("basetype");
      String nexttype = request.getParameter("nexttype");
      String queryinput = request.getParameter("queryinput");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      
      String askperson = request.getParameter("askperson");
      String doctor = request.getParameter("doctor");
      String createuser = request.getParameter("createuser");
      String devchannel = request.getParameter("devchannel");
      String nexttype1 = request.getParameter("nexttype1");
      String recesort = request.getParameter("recesort");
      String regsort = request.getParameter("regsort");
      Map<String, String> map = new HashMap();
      Date d = new Date();
      if ((YZUtility.isNullorEmpty(starttime)) && (YZUtility.isNullorEmpty(endtime)))
      {
        starttime = sdf.format(d) + ConstUtil.TIME_START;
        endtime = sdf.format(d) + ConstUtil.TIME_END;
        map.put("starttime", starttime);
        map.put("endtime", endtime);
      }
      else
      {
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
      }
      if (!YZUtility.isNullorEmpty(askperson)) {
        map.put("askperson", askperson);
      }
      if (!YZUtility.isNullorEmpty(doctor)) {
        map.put("doctor", doctor);
      }
      if (!YZUtility.isNullorEmpty(createuser)) {
        map.put("createuser", createuser);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype1)) {
        map.put("nexttype1", nexttype1);
      }
      if (!YZUtility.isNullorEmpty(recesort)) {
        map.put("recesort", recesort);
      }
      if (!YZUtility.isNullorEmpty(regsort)) {
        map.put("regsort", regsort);
      }
      if (!YZUtility.isNullorEmpty(costno)) {
        map.put("costno", costno);
      }
      if (!YZUtility.isNullorEmpty(cjStatus)) {
        map.put("cjStatus", cjStatus);
      }
      if (!YZUtility.isNullorEmpty(qf)) {
        map.put("qf", qf);
      }
      if (!YZUtility.isNullorEmpty(basetype)) {
        map.put("basetype", basetype);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      

      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      List<JSONObject> list = this.logic.selectTreatDetailListNopage(person, map, visualstaff, organization);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateTreatStatus.act"})
  public String updateTreatStatus(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      KqdsLltj treatdetail = (KqdsLltj)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_LLTJ, seqId);
      if (treatdetail == null) {
        throw new Exception("操作失败，记录不存在");
      }
      treatdetail.setIszl(Integer.valueOf(1));
      treatdetail.setZlperson(person.getSeqId());
      treatdetail.setZltime(YZUtility.getCurDateTimeStr());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_LLTJ, treatdetail);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
}
