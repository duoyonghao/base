package com.kqds.controller.base.participant;

import com.kqds.entity.base.KqdsParticipant;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.participant.KQDS_ParticipantLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping({"KQDS_ParticipantAct"})
public class KQDS_ParticipantAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_ParticipantAct.class);
  
  @Autowired
  private KQDS_ParticipantLogic logic;
  
  @RequestMapping({"/toAdd.act"})
  public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/qygx/add_relation.jsp");
    return mv;
  }
  
  @RequestMapping({"/toQygxList.act"})
  public ModelAndView toQygxList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/qygx/qygxlist.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsParticipant dp = new KqdsParticipant();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_PARTICIPANT, dp);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_PARTICIPANT, dp, dp.getUsercode(), TableNameUtil.KQDS_PARTICIPANT, request);
      } else {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_PARTICIPANT, dp);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_PARTICIPANT, dp, dp.getUsercode(), TableNameUtil.KQDS_PARTICIPANT, request);
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
      KqdsParticipant en = (KqdsParticipant)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_PARTICIPANT, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_PARTICIPANT, seqId);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_PARTICIPANT, en, en.getUsercode(), TableNameUtil.KQDS_PARTICIPANT, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectNoPage.act"})
  public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      Map<Object, Object> map = new HashMap<>();
      map.put("usercode", usercode);
      List<JSONObject> list = this.logic.selectNoPage(TableNameUtil.KQDS_PARTICIPANT, map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
}
