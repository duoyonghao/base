package com.kqds.controller.base.sms;

import com.kqds.entity.base.KqdsSmsModel;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.sms.KQDS_Sms_ModelLogic;
import com.kqds.service.sys.dict.YZDictLogic;
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
@RequestMapping({"KQDS_Sms_ModelAct"})
public class KQDS_Sms_ModelAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_SmsAct.class);
  
  @Autowired
  private KQDS_Sms_ModelLogic logic;
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @RequestMapping({"/toSmsModelTree.act"})
  public ModelAndView toSmsModelTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/sms/model/list_model_tree.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSmsModel.act"})
  public ModelAndView toWdyySearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/sms/model/sms_model.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddModel.act"})
  public ModelAndView toAddModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqdsFront/sms/model/add_model.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEditModel.act"})
  public ModelAndView toEditModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_cz.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDetailModel.act"})
  public ModelAndView toDetailModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/medicalRecord/medicalrecord_cz.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsSmsModel dp = new KqdsSmsModel();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (!YZUtility.isNullorEmpty(seqId)) {
        this.logic.updateSingleUUID(TableNameUtil.KQDS_SMS_MODEL, dp);
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_SMS_MODEL, dp, TableNameUtil.KQDS_SMS_MODEL, request);
      } else {
        String uuid = YZUtility.getUUID();
        dp.setSeqId(uuid);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.saveSingleUUID(TableNameUtil.KQDS_SMS_MODEL, dp);
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_SMS_MODEL, dp, TableNameUtil.KQDS_SMS_MODEL, request);
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
      KqdsSmsModel en = (KqdsSmsModel)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_SMS_MODEL, seqId);
      this.logic.deleteSingleUUID(TableNameUtil.KQDS_SMS_MODEL, seqId);
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_SMS_MODEL, en, TableNameUtil.KQDS_SMS_MODEL, request);
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
      KqdsSmsModel en = (KqdsSmsModel)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_SMS_MODEL, seqId);
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
  
  @RequestMapping({"/NoselectPage.act"})
  public String NoselectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = request.getParameter("organization");
      String smstype = request.getParameter("smstype");
      String smsnexttype = request.getParameter("smsnexttype");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(smstype))
        map.put("smstype", smstype); 
      if (!YZUtility.isNullorEmpty(smsnexttype))
        map.put("smsnexttype", smsnexttype); 
      List<JSONObject> list = this.logic.noSelectWithPage(TableNameUtil.KQDS_SMS_MODEL, map);
      YZUtility.RETURN_LIST(list, response, logger);
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
      String organization = request.getParameter("organization");
      String smstype = request.getParameter("smstype");
      String smsnexttype = request.getParameter("smsnexttype");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(smstype))
        map.put("smstype", smstype); 
      if (!YZUtility.isNullorEmpty(smsnexttype))
        map.put("smsnexttype", smsnexttype); 
      JSONObject data = this.logic.selectWithPage(TableNameUtil.KQDS_SMS_MODEL, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getSelectModelTree.act"})
  public void getSelectModelTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = ChainUtil.getCurrentOrganization(request);
    try {
      StringBuffer sb = new StringBuffer();
      List<YZDict> list = this.dictLogic.getListByParentCode("DXFL", organization);
      if (list != null && list.size() > 0) {
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
          YZDict base = list.get(i);
          sb.append("{ id:\"" + base.getSeqId() + "\", pId:\"0\", name:\"" + base.getDictName() + "\", nocheck:true},");
          List<YZDict> listnext = this.dictLogic.getListByParentCode(base.getDictCode(), organization);
          if (listnext != null && listnext.size() > 0)
            for (int k = 0; k < listnext.size(); k++) {
              YZDict nexttype = listnext.get(k);
              sb.append("{ id:\"" + nexttype.getSeqId() + "\", pId:\"" + base.getSeqId() + "\", name:\"" + nexttype.getDictName() + "\"},");
              Map<String, String> map2 = new HashMap<>();
              map2.put("smstype", base.getSeqId());
              map2.put("smsnexttype", nexttype.getSeqId());
              List<KqdsSmsModel> smslist = (List<KqdsSmsModel>)this.logic.loadList(TableNameUtil.KQDS_SMS_MODEL, map2);
              if (smslist != null && smslist.size() > 0)
                for (int ik = 0; ik < smslist.size(); ik++) {
                  KqdsSmsModel sms = smslist.get(ik);
                  sb.append("{ id:\"" + sms.getSeqId() + "\", pId:\"" + nexttype.getSeqId() + "\", name:\"" + sms.getSmsname() + "\"},");
                }  
            }  
        } 
        if (sb.length() > 1)
          sb.deleteCharAt(sb.length() - 1); 
        sb.append("]");
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("tree", sb.toString());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
  }
}
