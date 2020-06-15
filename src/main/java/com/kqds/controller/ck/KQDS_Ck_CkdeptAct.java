package com.kqds.controller.ck;

import com.kqds.entity.base.KqdsCkDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_CkdeptLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
@RequestMapping({"KQDS_Ck_CkdeptAct"})
public class KQDS_Ck_CkdeptAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_CkdeptAct.class);
  @Autowired
  private KQDS_Ck_CkdeptLogic logic;
  
  @RequestMapping({"/toSave.act"})
  public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/ck/ckdept/save_ckdept.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCkDept.act"})
  public ModelAndView toCkDept(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/ck/ckdept/ck_ckdept.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsCkDept dp = new KqdsCkDept();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_CK_DEPT, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_DEPT, dp, TableNameUtil.KQDS_CK_DEPT, request);
      }
      else
      {
        String uuid = UUID.randomUUID().toString();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_CK_DEPT, dp);
        
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_DEPT, dp, TableNameUtil.KQDS_CK_DEPT, request);
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
      KqdsCkDept en = (KqdsCkDept)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_DEPT, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_CK_DEPT, seqId);
      
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_DEPT, en, TableNameUtil.KQDS_CK_DEPT, request);
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
      KqdsCkDept en = (KqdsCkDept)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_DEPT, seqId);
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
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Map<String, String> map = new HashMap();
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      List<JSONObject> list = this.logic.selectList(map);
      
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
