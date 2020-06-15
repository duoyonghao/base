package com.kqds.controller.base.treatItemTc;

import com.kqds.entity.base.KqdsTreatitemTc;
import com.kqds.entity.base.KqdsTreatitemTcType;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItemTc.KQDS_TreatItem_TcLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_TreatItem_TcAct"})
public class KQDS_TreatItem_TcAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_TreatItem_TcAct.class);
  @Autowired
  private KQDS_TreatItem_TcLogic logic;
  
  @RequestMapping({"/toIndexLs.act"})
  public ModelAndView toWdyySearch(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqds/treatItemTc/index_ls.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertList.act"})
  public String insertList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsTreatitemTcType dptype = new KqdsTreatitemTcType();
      KqdsTreatitemTcType dpname = new KqdsTreatitemTcType();
      String tctype = request.getParameter("tctype");
      String tcname = request.getParameter("tcname");
      
      Map<String, String> map = new HashMap();
      map.put("name", tctype);
      map.put("parentid", "0");
      String menzhen = ChainUtil.getCurrentOrganization(request);
      
      List<KqdsTreatitemTcType> list = (List)this.logic.loadList(TableNameUtil.KQDS_TREATITEM_TC_TYPE, map);
      if ((list != null) && (list.size() > 0))
      {
        dptype = (KqdsTreatitemTcType)list.get(0);
        
        dpname.setSeqId(YZUtility.getUUID());
        dpname.setName(tcname);
        dpname.setIsopen(Integer.valueOf(0));
        dpname.setParentid(dptype.getSeqId());
        dpname.setOrganization(menzhen);
        dpname.setCreatetime(YZUtility.getCurDateTimeStr());
        dpname.setCreateuser(person.getSeqId());
        this.logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dpname);
      }
      else
      {
        dptype.setSeqId(YZUtility.getUUID());
        dptype.setName(tctype);
        dptype.setIsopen(Integer.valueOf(0));
        dptype.setParentid("0");
        dptype.setOrganization(menzhen);
        dptype.setCreatetime(YZUtility.getCurDateTimeStr());
        dptype.setCreateuser(person.getSeqId());
        this.logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dptype);
        

        dpname.setSeqId(YZUtility.getUUID());
        dpname.setName(tcname);
        dpname.setIsopen(Integer.valueOf(0));
        dpname.setParentid(dptype.getSeqId());
        dpname.setOrganization(menzhen);
        dpname.setCreatetime(dptype.getCreatetime());
        dpname.setCreateuser(person.getSeqId());
        this.logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dpname);
      }
      String listdata = request.getParameter("params");
      JSONArray jArray = JSONArray.fromObject(listdata);
      Collection collection = JSONArray.toCollection(jArray, KqdsTreatitemTc.class);
      Iterator it = collection.iterator();
      
      KqdsTreatitemTc detail = new KqdsTreatitemTc();
      while (it.hasNext())
      {
        detail = (KqdsTreatitemTc)it.next();
        detail.setTcnameid(dpname.getSeqId());
        detail.setSeqId(YZUtility.getUUID());
        detail.setCreatetime(YZUtility.getCurDateTimeStr());
        detail.setCreateuser(person.getSeqId());
        detail.setOrganization(dptype.getOrganization());
        detail.setOrganization(menzhen);
        this.logic.saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, detail);
      }
      JSONObject logcontent = new JSONObject();
      logcontent.put("tctype", tctype);
      logcontent.put("tcname", tcname);
      BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_TREATITEM_TC_TYPE, logcontent, TableNameUtil.KQDS_USERDOCUMENT, request);
      
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/YzCode.act"})
  public String YzCode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String tctype = request.getParameter("tctype");
      String tcname = request.getParameter("tcname");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      boolean flag = true;
      Map<String, String> map = new HashMap();
      map.put("name", tctype);
      map.put("parentid", "0");
      if (YZUtility.isNotNullOrEmpty(organization)) {
        map.put("organization", organization);
      }
      List<KqdsTreatitemTcType> list = (List)this.logic.loadList(TableNameUtil.KQDS_TREATITEM_TC_TYPE, map);
      if ((list != null) && (list.size() > 0))
      {
        KqdsTreatitemTcType tctypeObj = (KqdsTreatitemTcType)list.get(0);
        Map<String, String> map2 = new HashMap();
        map2.put("name", tcname);
        map2.put("parentid", tctypeObj.getSeqId());
        if (YZUtility.isNotNullOrEmpty(organization)) {
          map2.put("organization", organization);
        }
        int count = this.logic.selectCount(TableNameUtil.KQDS_TREATITEM_TC_TYPE, map2);
        if (count > 0) {
          flag = false;
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteObjBytctype.act"})
  public String deleteObjBytctype(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String tcnameid = request.getParameter("tcnameid");
      
      Map<String, String> map = new HashMap();
      map.put("tcnameid", tcnameid);
      List<KqdsTreatitemTc> en = (List)this.logic.loadList(TableNameUtil.KQDS_TREATITEM_TC, map);
      if ((en != null) && (en.size() > 0)) {
        for (KqdsTreatitemTc tc : en) {
          this.logic.deleteSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, tc.getSeqId());
        }
      }
      KqdsTreatitemTcType currTC = (KqdsTreatitemTcType)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, tcnameid);
      if (currTC == null) {
        throw new Exception("根据tcnameid查询不到套餐记录");
      }
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, currTC.getSeqId());
      
      Object map2 = new HashMap();
      ((Map)map2).put("parentid", currTC.getParentid());
      int count = this.logic.selectCount(TableNameUtil.KQDS_TREATITEM_TC_TYPE, (Map)map2);
      if (count == 0) {
        this.logic.deleteSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, currTC.getParentid());
      }
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_TREATITEM_TC_TYPE, map, TableNameUtil.KQDS_TREATITEM_TC_TYPE, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDetailBytctypeAndtcname.act"})
  public String selectDetailBytctypeAndtcname(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String type = request.getParameter("type");
      String tcnameid = request.getParameter("tcnameid");
      Map<String, String> map = new HashMap();
      map.put("tcnameid", tcnameid);
      List<KqdsTreatitemTc> en = (List)this.logic.loadList(TableNameUtil.KQDS_TREATITEM_TC, map);
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("retMsrg", "操作成功");
      if ((en == null) || (en.size() == 0)) {
        throw new Exception("数据不存在");
      }
      if (type.equals("1")) {
        jobj.put("data", en);
      } else {
        jobj.put("data", en);
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getList.act"})
  public String getList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String tcnameid = request.getParameter("tcnameid");
      
      Map<String, String> map = new HashMap();
      map.put("tcnameid", tcnameid);
      List<KqdsTreatitemTc> en = (List)this.logic.loadList(TableNameUtil.KQDS_TREATITEM_TC, map);
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
}
