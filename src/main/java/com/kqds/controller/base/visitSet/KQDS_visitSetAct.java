package com.kqds.controller.base.visitSet;

import com.kqds.entity.base.KqdsVisitSet;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.visitSet.KQDS_VisitSetLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_visitSetAct"})
public class KQDS_visitSetAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_visitSetAct.class);
  @Autowired
  private KQDS_VisitSetLogic logic;
  
  @RequestMapping({"/toIndexLs.act"})
  public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqds/visitSet/index_ls.jsp");
    return mv;
  }
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/visitSet/list_kqds_visitSet.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/visitSet/edit_kqds_visitSet.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/visitSet/detail_kqds_visitSet.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/visitSet/add_kqds_visitSet.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsVisitSet dp = new KqdsVisitSet();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_VISIT_SET, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.UPDATE, BcjlUtil.KQDS_VISIT_SET, dp, TableNameUtil.KQDS_VISIT_SET, request);
      }
      else
      {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getOrganizationFromUrl(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_VISIT_SET, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_VISIT_SET, dp, TableNameUtil.KQDS_VISIT_SET, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
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
      KqdsVisitSet en = (KqdsVisitSet)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_VISIT_SET, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_VISIT_SET, seqId);
      
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_VISIT_SET, en, TableNameUtil.KQDS_VISIT_SET, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      KqdsVisitSet en = (KqdsVisitSet)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_VISIT_SET, seqId);
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
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      String hffl = request.getParameter("hffl");
      String userpriv = request.getParameter("userpriv");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(hffl)) {
        map.put("hffl", hffl);
      }
      if (!YZUtility.isNullorEmpty(userpriv)) {
        map.put("userpriv", userpriv);
      }
      map.put("organization", ChainUtil.getOrganizationFromUrl(request));
      JSONObject data = this.logic.selectWithPage(TableNameUtil.KQDS_VISIT_SET, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
