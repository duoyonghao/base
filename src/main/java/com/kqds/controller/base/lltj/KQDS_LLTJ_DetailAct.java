package com.kqds.controller.base.lltj;

import com.kqds.entity.base.KqdsLltjDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.lltj.Kqds_LLTJ_DetailLogic;
import com.kqds.util.sys.ConstUtil;
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
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_LLTJ_DetailAct"})
public class KQDS_LLTJ_DetailAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_LLTJ_DetailAct.class);
  @Autowired
  private Kqds_LLTJ_DetailLogic logic;
  
  @RequestMapping({"/zlStatusUpdate.act"})
  public String zlStatusUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      KqdsLltjDetail good = (KqdsLltjDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_LLTJ_DETAIL, seqId);
      if (good == null) {
        throw new Exception("操作失败，记录不存在");
      }
      good.setIszl(Integer.valueOf(ConstUtil.ISZJL_1));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_LLTJ_DETAIL, good);
      

      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_LLTJ_DETAIL, good, good.getUsercode(), TableNameUtil.KQDS_LLTJ_DETAIL, request);
      
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
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
      KqdsLltjDetail dp = new KqdsLltjDetail();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_LLTJ_DETAIL, dp);
        

        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_LLTJ_DETAIL, dp, dp.getUsercode(), TableNameUtil.KQDS_LLTJ_DETAIL, request);
      }
      else
      {
        String listdata = request.getParameter("data");
        JSONArray jArray = JSONArray.fromObject(listdata);
        Collection collection = JSONArray.toCollection(jArray, KqdsLltjDetail.class);
        Iterator it = collection.iterator();
        while (it.hasNext())
        {
          dp = (KqdsLltjDetail)it.next();
          dp.setSeqId(YZUtility.getUUID());
          dp.setCreatetime(YZUtility.getCurDateTimeStr());
          dp.setCreateuser(person.getSeqId());
          dp.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_LLTJ_DETAIL, dp);
          

          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_LLTJ_DETAIL, dp, dp.getUsercode(), TableNameUtil.KQDS_LLTJ_DETAIL, request);
        }
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String treatdetailno = request.getParameter("treatdetailno");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String queryinput = request.getParameter("queryinput");
      
      Map<String, String> map = new HashMap();
      
      map.put("organization", ChainUtil.getCurrentOrganization(request));
      if (!YZUtility.isNullorEmpty(treatdetailno)) {
        map.put("lltjid", treatdetailno);
      }
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
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      List<KqdsLltjDetail> list = this.logic.selectList(TableNameUtil.KQDS_LLTJ_DETAIL, map, ChainUtil.getCurrentOrganization(request));
      
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteTreatDetail.act"})
  public String deleteTreatDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      KqdsLltjDetail en = (KqdsLltjDetail)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_LLTJ_DETAIL, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_LLTJ_DETAIL, seqId);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_LLTJ_DETAIL, en, en.getUsercode(), TableNameUtil.KQDS_LLTJ_DETAIL, request);
      
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
}
