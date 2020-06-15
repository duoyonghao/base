package com.kqds.controller.base.jzmdType;

import com.kqds.entity.base.KqdsJzmdType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.jzmdType.KQDS_Jzmd_TypeLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.HashMap;
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
@RequestMapping({"KQDS_Jzmd_TypeBackAct"})
public class KQDS_Jzmd_TypeBackAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Jzmd_TypeBackAct.class);
  
  @Autowired
  private KQDS_Jzmd_TypeLogic logic;
  
  @RequestMapping({"/toIndexLs.act"})
  public ModelAndView toIndexLs(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqds/jzmdType/index_ls.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsJzmdType dp = new KqdsJzmdType();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, dp);
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_JZMD_TYPE, dp, TableNameUtil.KQDS_JZMD_TYPE, request);
      } else {
        String uuid = UUID.randomUUID().toString();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser((new StringBuilder(String.valueOf(person.getSeqId()))).toString());
        this.logic.saveSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, dp);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_JZMD_TYPE, dp, TableNameUtil.KQDS_JZMD_TYPE, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteObj.act"})
  public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      KqdsJzmdType en = (KqdsJzmdType)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, seqId);
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_JZMD_TYPE, en, TableNameUtil.KQDS_JZMD_TYPE, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      KqdsJzmdType en = (KqdsJzmdType)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_JZMD_TYPE, seqId);
      if (en == null)
        throw new Exception("数据不存在"); 
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String jzmd = request.getParameter("jzmd");
      String jzmdchildname = request.getParameter("jzmdchildname");
      String organization = ChainUtil.getOrganizationFromUrl(request);
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(jzmd))
        map.put("jzmd", jzmd); 
      if (!YZUtility.isNullorEmpty(jzmdchildname))
        map.put("jzmdchildname", jzmdchildname); 
      map.put("organization", organization);
      JSONObject data = this.logic.selectWithPage(bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
