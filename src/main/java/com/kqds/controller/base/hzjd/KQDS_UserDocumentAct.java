package com.kqds.controller.base.hzjd;

import com.hudh.util.HUDHUtil;
import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.entity.base.KqdsChangeKefu;
import com.kqds.entity.base.KqdsChangeWd;
import com.kqds.entity.base.KqdsCostorderPriceList;
import com.kqds.entity.base.KqdsHzLabelAssociated;
import com.kqds.entity.base.KqdsJdrchange;
import com.kqds.entity.base.KqdsLabel;
import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderPriceList.KQDS_CostOrderPriceListLogic;
import com.kqds.service.base.hzLabel.KQDS_HzLabelAssociatedLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.code.UserCodeUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.sys.SysParaUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_UserDocumentAct"})
public class KQDS_UserDocumentAct
{
  private Logger logger = LoggerFactory.getLogger(KQDS_UserDocumentAct.class);
  @Autowired
  private KQDS_UserDocumentLogic logic;
  @Autowired
  private KQDS_CostOrderPriceListLogic priceListLogic;
  @Autowired
  private KQDS_HzLabelAssociatedLogic hzLabelAssociatedLogic;
  @Autowired
  private YZPersonLogic personLogic;
  @Autowired
  private YZDictLogic dictLogic;
  @Autowired
  private KQDS_hz_labelLogic labelLogic;
  
  @RequestMapping({"/toCloudsTagsAdd.act"})
  public ModelAndView toCloudsTagsAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/kfzx/user_manager_kfzx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUserManagerKfzx.act"})
  public ModelAndView toUserManagerKfzx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/kfzx/user_manager_kfzx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUserManagerYxzx.act"})
  public ModelAndView toUserManagerYxzx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/kfzx/user_manager_yxzx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUserManagerWdzx.act"})
  public ModelAndView toUserManagerWdzx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/kfzx/user_manager_wdzx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toXxbbCenter.act"})
  public ModelAndView toXxbbCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/jdzx/xxbb_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUserManagerJq.act"})
  public ModelAndView toUserManagerJq(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/kfzx/user_manager_jq.jsp");
    return mv;
  }
  
  @RequestMapping({"/toFirst_Center.act"})
  public ModelAndView toFirst_Center(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/first_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toWdAddWin.act"})
  public ModelAndView toWdAddWin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/reg/wd/wdAddWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toWdWin.act"})
  public ModelAndView toWdWin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/reg/wd/wdWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZzWinPage.act"})
  public ModelAndView toZzWinPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/reg/zz/zzWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZzAddWin.act"})
  public ModelAndView toZzAddWin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/reg/zz/zzAddWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZzEditDoctor.act"})
  public ModelAndView toZzEditDoctor(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/reg/zzDoctor/zz_edit_doctor.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZzDoctorWin.act"})
  public ModelAndView toZzDoctorWin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/reg/zzDoctor/zzDoctorWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toGrxx.act"})
  public ModelAndView toGrxx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/grxx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toRightGrxx.act"})
  public ModelAndView toRightGrxx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/right_grxx.jsp");
    return mv;
  }
  
  @RequestMapping({"/toOpenPatientTag.act"})
  public ModelAndView toOpenPatientTag(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    String frameSelfindex = request.getParameter("frameSelfindex");
    mv.addObject("frameSelfindex", frameSelfindex);
    mv.setViewName("/kqdsFront/index/patientTags2.jsp");
    return mv;
  }
  
  @RequestMapping({"/toOpenPatientTagAdd.act"})
  public ModelAndView toOpenPatientTagAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/patientTags.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUserCenter.act"})
  public ModelAndView toUserCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/index/userCenter.jsp");
    return mv;
  }
  
  @RequestMapping({"/toUserList.act"})
  public ModelAndView toUserList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String typechoose = request.getParameter("typechoose");
    ModelAndView mv = new ModelAndView();
    mv.addObject("typechoose", typechoose);
    mv.setViewName("/kqdsFront/hzjd/user_list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDahb.act"})
  public ModelAndView toDahb(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/dahb.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZzIndex.act"})
  public ModelAndView toZzIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/reg/zz/zz_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZzWin.act"})
  public ModelAndView toZzWin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/reg/zz/zzAddAllWin.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzjd_Xxbb.act"})
  public ModelAndView toHzjd_Xxbb(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/hzjd_xxbb.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzjd_Edit.act"})
  public ModelAndView toHzjd_Edit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String menuid = request.getParameter("menuid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("menuid", menuid);
    mv.setViewName("/kqdsFront/hzjd/hzjd_edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzjd_Net_Edit.act"})
  public ModelAndView toHzjd_Net_Edit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/hzjd_net_edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzjd.act"})
  public ModelAndView toHzjd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/hzjd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzjd_Net.act"})
  public ModelAndView toHzjd_Net(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/kqdsFront/hzjd/hzjd_net.jsp");
    return mv;
  }
  
  @RequestMapping({"/toXxcxCenter.act"})
  public ModelAndView toXxcxCenter(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuId = request.getParameter("menuId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/kqdsFront/index/jdzx/xxcx_center.jsp");
    return mv;
  }
  
  @RequestMapping({"/toWdIndex.act"})
  public ModelAndView toWdIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/reg/wd/wd_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toVideo_Yxzl.act"})
  public ModelAndView toVideo_Yxzl(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String type = request.getParameter("type");
    ModelAndView mv = new ModelAndView();
    mv.addObject("type", type);
    mv.setViewName("/kqdsFront/video/video_yxzl.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHuizheng_Info.act"})
  public ModelAndView toHuizheng_Info(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/huizhen/huizheng_info.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMedicalrecord.act"})
  public ModelAndView toMedicalrecord(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String blflag = request.getParameter("blflag");
    ModelAndView mv = new ModelAndView();
    mv.addObject("blflag", blflag);
    mv.setViewName("/kqdsFront/medicalRecord/medicalrecord.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJg_List.act"})
  public ModelAndView toJg_List(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/jiagong/jg_list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSms_Usercode.act"})
  public ModelAndView toSms_Usercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/sms/sms/sms_usercode.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCs.act"})
  public ModelAndView toCs(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/lzjl/cs.jsp");
    return mv;
  }
  
  @RequestMapping({"/toYyzxMz.act"})
  public ModelAndView toYyzxMz(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/yyzx/yyzxMz.jsp");
    return mv;
  }
  
  @RequestMapping({"/toReceive.act"})
  public ModelAndView toReceive(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/zxjl/receive.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZengsong_List.act"})
  public ModelAndView toZengsong_List(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/member/zengsong_list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toGrxxList4dj.act"})
  public ModelAndView toGrxxList4dj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/soundRecord/grxxlist4dj.jsp");
    return mv;
  }
  
  @RequestMapping({"/checkBlcode.act"})
  public String checkBlcode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    boolean result = true;
    String blcode = request.getParameter("blcode");
    String seqId = request.getParameter("seqId");
    try
    {
      int num = this.logic.checkBlcode(seqId, blcode, TableNameUtil.KQDS_USERDOCUMENT);
      if (num > 0) {
        result = false;
      }
      YZUtility.DEAL_SUCCESS_VALID(result, response);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getSingUserByPhoneNumber.act"})
  public String getSingUserByPhoneNumber(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String phonenumber = request.getParameter("phonenumber");
      if (YZUtility.isNullorEmpty(phonenumber)) {
        throw new Exception("phonenumber不能为空");
      }
      KqdsUserdocument doc = this.logic.getSingUserByPhoneNumber(phonenumber);
      if (doc == null) {
        throw new Exception("患者不存在，手机号码为：" + phonenumber);
      }
      YZUtility.DEAL_SUCCESS(JSONObject.fromObject(doc), null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/setKeFu.act"})
  public String setKeFu(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      

      String listdata = request.getParameter("data");
      JSONArray jArray = JSONArray.fromObject(listdata);
      for (Object obj : jArray)
      {
        JSONObject job = (JSONObject)obj;
        String seqId = job.getString("seqId");
        String kefu = job.getString("kefu");
        String kefuremark = job.getString("kefuremark");
        KqdsUserdocument user = (KqdsUserdocument)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, 
          seqId);
        if (user != null)
        {
          KqdsChangeKefu wd = new KqdsChangeKefu();
          wd.setSeqId(YZUtility.getUUID());
          wd.setCreatetime(YZUtility.getCurDateTimeStr());
          wd.setCreateuser(person.getSeqId());
          if (!YZUtility.isNullorEmpty(user.getKefu())) {
            wd.setOldper(user.getKefu());
          }
          wd.setToper(kefu);
          wd.setRemark(kefuremark);
          wd.setUsercode(user.getUsercode());
          wd.setUsername(user.getUsername());
          wd.setOrganization(ChainUtil.getCurrentOrganization(request));
          user.setKefu(kefu);
          this.logic.setKeFu(wd, user);
          
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_KEFU, wd, wd.getUsercode(), 
            TableNameUtil.KQDS_CHANGE_KEFU, request);
        }
      }
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/setInputtingPerson.act"})
  public String setInputtingPerson(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      

      String listdata = request.getParameter("data");
      JSONArray jArray = JSONArray.fromObject(listdata);
      for (Object obj : jArray)
      {
        JSONObject job = (JSONObject)obj;
        String seqId = job.getString("seqId");
        String createuser = job.getString("createuser");
        String jdRremark = job.getString("jdRremark");
        KqdsUserdocument user = (KqdsUserdocument)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, 
          seqId);
        if (user != null)
        {
          KqdsJdrchange wd = new KqdsJdrchange();
          wd.setSeqId(YZUtility.getUUID());
          wd.setCreatetime(YZUtility.getCurDateTimeStr());
          wd.setCreateuser(person.getSeqId());
          if (!YZUtility.isNullorEmpty(user.getCreateuser())) {
            wd.setOldper(user.getCreateuser());
          }
          wd.setToper(createuser);
          wd.setRemark(jdRremark);
          wd.setUsercode(user.getUsercode());
          wd.setUsername(user.getUsername());
          wd.setOrganization(ChainUtil.getCurrentOrganization(request));
          user.setCreateuser(createuser);
          this.logic.setInputtingPerson(wd, user);
          
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_JDR, wd, wd.getUsercode(), 
            TableNameUtil.KQDS_CHANGE_JDR, request);
        }
      }
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/insert.act"})
  public String insert(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String exploit = request.getParameter("exploit");
      String exploit1 = request.getParameter("exploit1");
      String exploitId = request.getParameter("exploitId");
      String exploitId1 = request.getParameter("exploitId1");
      String labelAllArr = request.getParameter("labelAllArr");
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      String nation = request.getParameter("nation");
      String certOrg = request.getParameter("certOrg");
      String effDate = request.getParameter("effDate");
      String expDate = request.getParameter("expDate");
      String headPic = request.getParameter("headPic");
      String photoDisplay = request.getParameter("photoDisplay");
      String introduce = request.getParameter("introduce");
      List<kqdsHzLabellabeAndPatient> labelAllList = null;
      if (labelAllArr != null)
      {
        labelAllArr = URLDecoder.decode(labelAllArr, "UTF-8");
        labelAllList = HUDHUtil.parseJsonToObjectList(labelAllArr, kqdsHzLabellabeAndPatient.class);
      }
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsUserdocument dp = new KqdsUserdocument();
      KqdsNetOrder netorder = new KqdsNetOrder();
      BeanUtils.populate(dp, request.getParameterMap());
      BeanUtils.populate(netorder, request.getParameterMap());
      String[] hobby = request.getParameterValues("hobby");
      if (hobby != null)
      {
        StringBuffer sbHobby = new StringBuffer();
        for (int i = 0; i < hobby.length / 2; i++) {
          sbHobby.append(hobby[i] + ";");
        }
        dp.setHobby(sbHobby.toString());
      }
      if ((YZUtility.isNullorEmpty(dp.getPhonenumber1())) && (YZUtility.isNullorEmpty(dp.getPhonenumber2()))) {
        throw new Exception("建档时，手机号码不能都为空值！");
      }
      if ((YZUtility.isNotNullOrEmpty(dp.getPhonenumber1())) && (YZUtility.isNotNullOrEmpty(dp.getPhonenumber2())) && 
        (dp.getPhonenumber1().equals(dp.getPhonenumber2()))) {
        throw new Exception("建档时，两个手机号码值不能一样！");
      }
      String seqId = request.getParameter("seqId");
      String name = "";
      String createtime = "";
      if (!YZUtility.isNullorEmpty(seqId))
      {
        if (YZUtility.isNotNullOrEmpty(dp.getBlcode()))
        {
          int blcount = this.logic.checkBlcode(seqId, dp.getBlcode(), TableNameUtil.KQDS_USERDOCUMENT);
          if (blcount > 0) {
            throw new Exception("病历号重复，请重新填写");
          }
        }
        KqdsUserdocument en = (KqdsUserdocument)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, 
          seqId);
        if ((!YZUtility.isNullorEmpty(dp.getNexttype())) && 
          (dp.getNexttype().equals("请选择"))) {
          dp.setNexttype("");
        }
        if (!en.getCreateuser().equals(dp.getCreateuser()))
        {
          YZPerson createPerson = (YZPerson)this.logic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, 
            dp.getCreateuser());
          YZDept dept = (YZDept)this.logic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, createPerson.getDeptId());
          if ((ConstUtil.DEPT_TYPE_2.equals(dept.getDeptType())) || 
            (ConstUtil.DEPT_TYPE_3.equals(dept.getDeptType()))) {
            dp.setType(Integer.valueOf(1));
          } else {
            dp.setType(Integer.valueOf(0));
          }
          String logText = "系统用户" + person.getUserId() + "进行了建档人修改操作，原建档人" + en.getCreateuser() + "，现建档人：" + 
            dp.getCreateuser() + "，患者编号：" + en.getUsercode() + "。";
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY_JDR, BcjlUtil.KQDS_USERDOCUMENT, logText, 
            dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
        }
        dp.setDoorstatus(en.getDoorstatus());
        String pym = ChineseCharToEn.getAllFirstLetter(dp.getUsername());
        dp.setPym(pym);
        

        dp.setNation(nation);
        dp.setCertOrg(certOrg);
        dp.setEffDate(effDate);
        dp.setExpDate(expDate);
        dp.setHeadPic(headPic);
        dp.setPhotoDisplay(photoDisplay);
        dp.setIntroduce(introduce);
        this.logic.updateUserDoc(dp, en, netorder, person, request);
        if (labelAllList != null)
        {
          this.logic.deleteLabel(dp.getUsercode());
          kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
          for (kqdsHzLabellabeAndPatient kPatien : labelAllList)
          {
            String id = YZUtility.getUUID();
            kPatient.setSeqId(id);
            kPatient.setUserSeqId(dp.getSeqId());
            kPatient.setUserId(dp.getUsercode());
            kPatient.setUserName(dp.getUsername());
            kPatient.setLabelOneId(kPatien.getLabelOneId());
            kPatient.setLabelOneName(kPatien.getLabelOneName());
            kPatient.setLabelTwoId(kPatien.getLabelTwoId());
            kPatient.setLabelTwoName(kPatien.getLabelTwoName());
            kPatient.setLabelThreeId(kPatien.getLabelThreeId());
            if (kPatien.getLabelThreeId() == null) {
              kPatien.setLabelThreeId("00165d45-650b-4394-9768-4482a0ca9b05");
            }
            kPatient.setOpinion(kPatien.getOpinion());
            kPatient.setLabelThreeName(kPatien.getLabelThreeName());
            kPatient.setCreateUser(person.getSeqId());
            kPatient.setStatus(1);
            kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
            kPatient.setOrganization(organization);
            this.logic.saveKpatient(kPatient);
          }
          if ((dp.getAge() != null) && (!dp.getAge().equals("")))
          {
            int age = dp.getAge().intValue();
            String threename = "";
            if (age <= 30) {
              threename = "30岁以下";
            } else if ((age >= 31) && (age <= 40)) {
              threename = "31-40岁";
            } else if ((age >= 41) && (age <= 50)) {
              threename = "41-50岁";
            } else if ((age >= 51) && (age <= 60)) {
              threename = "51-60岁";
            } else if ((age >= 61) && (age <= 70)) {
              threename = "61-70岁";
            } else if ((age >= 71) && (age <= 80)) {
              threename = "71-80岁";
            } else if (age >= 81) {
              threename = "80岁以上";
            }
            String threeId = this.labelLogic.findKqdsHzLabelSeqIdByLeveLabel(threename);
            String id = YZUtility.getUUID();
            kPatient.setSeqId(id);
            kPatient.setUserSeqId(dp.getSeqId());
            kPatient.setUserId(dp.getUsercode());
            kPatient.setUserName(dp.getUsername());
            kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
            kPatient.setLabelOneName("社会标签");
            kPatient.setLabelTwoId("9f1cd4c9-2aea-4309-9556-7812b225e23f");
            kPatient.setLabelTwoName("年龄");
            kPatient.setLabelThreeId(threeId);
            kPatient.setLabelThreeName(threename);
            kPatient.setCreateUser(person.getSeqId());
            kPatient.setStatus(1);
            kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
            kPatient.setOrganization(organization);
            this.logic.saveKpatient(kPatient);
          }
          if ((dp.getProfession() != null) && (!dp.getProfession().equals("")))
          {
            String dictName = this.dictLogic.findDictNameBySeqId(dp.getProfession());
            Object map1 = new HashMap();
            if ((dictName != null) && (!dictName.equals(""))) {
              ((Map)map1).put("leveLabel", dictName);
            }
            ((Map)map1).put("parentId", "62aa427d-b590-4077-8b7b-4195201ec758");
            String seqid = this.labelLogic.selectKqdsHzLabelByLeveLabel((Map)map1);
            if ((seqid == null) || (seqid.equals("")))
            {
              KqdsLabel kqdsHzLabel = new KqdsLabel();
              seqid = YZUtility.getUUID();
              kqdsHzLabel.setSeqId(seqid);
              kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
              kqdsHzLabel.setCreateUser(person.getSeqId());
              kqdsHzLabel.setLeveLabel(dictName);
              kqdsHzLabel.setParentId("62aa427d-b590-4077-8b7b-4195201ec758");
              kqdsHzLabel.setParentName("职业");
              kqdsHzLabel.setRemark("三级");
              this.labelLogic.insertKqdsHzLabel(kqdsHzLabel);
            }
            String id = YZUtility.getUUID();
            kPatient.setSeqId(id);
            kPatient.setUserSeqId(dp.getSeqId());
            kPatient.setUserId(dp.getUsercode());
            kPatient.setUserName(dp.getUsername());
            kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
            kPatient.setLabelOneName("社会标签");
            kPatient.setLabelTwoId("62aa427d-b590-4077-8b7b-4195201ec758");
            kPatient.setLabelTwoName("职业");
            kPatient.setLabelThreeId(seqid);
            kPatient.setLabelThreeName(dictName);
            kPatient.setCreateUser(person.getSeqId());
            kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
            kPatient.setStatus(1);
            kPatient.setOrganization(organization);
            this.logic.saveKpatient(kPatient);
          }
        }
        if ((exploit != null) && (!exploit.equals("undefined")) && (exploitId != null)) {
          savePriceList(exploitId, exploit, dp.getUsername(), dp.getUsercode(), "1", person, response);
        }
        if ((exploit1 != null) && (!exploit1.equals("undefined")) && (exploitId1 != null)) {
          savePriceList(exploitId1, exploit1, dp.getUsername(), dp.getUsercode(), "2", person, response);
        }
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_USERDOCUMENT, dp, dp.getUsercode(), 
          TableNameUtil.KQDS_USERDOCUMENT, request);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_USERDOCUMENT, labelAllList, 
          dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
      }
      else
      {
        String usercode = dp.getUsercode();
        String jdOrganization = ChainUtil.getCurrentOrganization(request);
        if (1 == dp.getType().intValue()) {
          jdOrganization = ChainUtil.getOrganizationFromUrl(request);
        }
        String realusercode = UserCodeUtil.getUserCode4Insert(jdOrganization, usercode);
        if (!usercode.equals(realusercode))
        {
          dp.setUsercode(realusercode);
          this.logger.error("----页面患者编号：" + usercode + ",新编号：" + realusercode);
        }
        List<KqdsUserdocument> list = this.logic.selectUserdocumentByPhonenumber(dp.getPhonenumber1());
        kqdsHzLabellabeAndPatient kPatient;
        String seqid;
        if (list.size() == 0)
        {
          String uuid = YZUtility.getUUID();
          dp.setSeqId(uuid);
          String pym = ChineseCharToEn.getAllFirstLetter(dp.getUsername());
          dp.setPym(pym);
          



          dp.setNation(nation);
          dp.setCertOrg(certOrg);
          dp.setEffDate(effDate);
          dp.setExpDate(expDate);
          dp.setHeadPic(headPic);
          dp.setPhotoDisplay(photoDisplay);
          dp.setIsdelete(Integer.valueOf(0));
          dp.setCreatetime(YZUtility.getCurDateTimeStr());
          dp.setCreateuser(person.getSeqId());
          
          name = dp.getUsername();
          createtime = dp.getCreatetime();
          












          this.logic.insertUserDoc(dp, netorder, person, request);
          if (labelAllList != null)
          {
            kPatient = new kqdsHzLabellabeAndPatient();
            for (kqdsHzLabellabeAndPatient kPatien : labelAllList)
            {
              String id = YZUtility.getUUID();
              kPatient.setSeqId(id);
              kPatient.setUserSeqId(dp.getSeqId());
              kPatient.setUserId(dp.getUsercode());
              kPatient.setUserName(dp.getUsername());
              kPatient.setLabelOneId(kPatien.getLabelOneId());
              kPatient.setLabelOneName(kPatien.getLabelOneName());
              kPatient.setLabelTwoId(kPatien.getLabelTwoId());
              kPatient.setLabelTwoName(kPatien.getLabelTwoName());
              kPatient.setLabelThreeId(kPatien.getLabelThreeId());
              kPatient.setLabelThreeName(kPatien.getLabelThreeName());
              if (kPatien.getLabelThreeId() == null) {
                kPatien.setLabelThreeId("00165d45-650b-4394-9768-4482a0ca9b05");
              }
              kPatient.setOpinion(kPatien.getOpinion());
              kPatient.setCreateUser(person.getSeqId());
              kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
              kPatient.setOrganization(organization);
              this.logic.saveKpatient(kPatient);
            }
            if ((dp.getAge() != null) && (!dp.getAge().equals("")))
            {
              int age = dp.getAge().intValue();
              String threename = "";
              if (age <= 30) {
                threename = "30岁以下";
              } else if ((age >= 31) && (age <= 40)) {
                threename = "31-40岁";
              } else if ((age >= 41) && (age <= 50)) {
                threename = "41-50岁";
              } else if ((age >= 51) && (age <= 60)) {
                threename = "51-60岁";
              } else if ((age >= 61) && (age <= 70)) {
                threename = "61-70岁";
              } else if ((age >= 71) && (age <= 80)) {
                threename = "71-80岁";
              } else if (age >= 81) {
                threename = "80岁以上";
              }
              String threeId = this.labelLogic.findKqdsHzLabelSeqIdByLeveLabel(threename);
              String id = YZUtility.getUUID();
              kPatient.setSeqId(id);
              kPatient.setUserSeqId(dp.getSeqId());
              kPatient.setUserId(dp.getUsercode());
              kPatient.setUserName(dp.getUsername());
              kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
              kPatient.setLabelOneName("社会标签");
              kPatient.setLabelTwoId("9f1cd4c9-2aea-4309-9556-7812b225e23f");
              kPatient.setLabelTwoName("年龄");
              kPatient.setLabelThreeId(threeId);
              kPatient.setLabelThreeName(threename);
              kPatient.setCreateUser(person.getSeqId());
              kPatient.setStatus(1);
              kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
              kPatient.setOrganization(organization);
              this.logic.saveKpatient(kPatient);
            }
            if ((dp.getProfession() != null) && (!dp.getProfession().equals("")))
            {
              String dictName = this.dictLogic.findDictNameBySeqId(dp.getProfession());
              Object map1 = new HashMap();
              if ((dictName != null) && (!dictName.equals(""))) {
                ((Map)map1).put("leveLabel", dictName);
              }
              ((Map)map1).put("parentId", "62aa427d-b590-4077-8b7b-4195201ec758");
              seqid = this.labelLogic.selectKqdsHzLabelByLeveLabel((Map)map1);
              if ((seqid == null) || (seqid.equals("")))
              {
                KqdsLabel kqdsHzLabel = new KqdsLabel();
                seqid = YZUtility.getUUID();
                kqdsHzLabel.setSeqId(seqid);
                kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
                kqdsHzLabel.setCreateUser(person.getSeqId());
                kqdsHzLabel.setLeveLabel(dictName);
                kqdsHzLabel.setParentId("62aa427d-b590-4077-8b7b-4195201ec758");
                kqdsHzLabel.setParentName("职业");
                kqdsHzLabel.setRemark("三级");
                this.labelLogic.insertKqdsHzLabel(kqdsHzLabel);
              }
              String id = YZUtility.getUUID();
              kPatient.setSeqId(id);
              kPatient.setUserSeqId(dp.getSeqId());
              kPatient.setUserId(dp.getUsercode());
              kPatient.setUserName(dp.getUsername());
              kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
              kPatient.setLabelOneName("社会标签");
              kPatient.setLabelTwoId("62aa427d-b590-4077-8b7b-4195201ec758");
              kPatient.setLabelTwoName("职业");
              kPatient.setLabelThreeId(seqid);
              kPatient.setLabelThreeName(dictName);
              kPatient.setCreateUser(person.getSeqId());
              kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
              kPatient.setStatus(1);
              kPatient.setOrganization(organization);
              this.logic.saveKpatient(kPatient);
            }
          }
          if ((exploit != null) && (!exploit.equals("undefined")) && (exploitId != null)) {
            savePriceList(exploitId, exploit, dp.getUsername(), dp.getUsercode(), "1", person, response);
          }
          if ((exploit1 != null) && (!exploit1.equals("undefined")) && (exploitId1 != null)) {
            savePriceList(exploitId1, exploit1, dp.getUsername(), dp.getUsercode(), "2", person, response);
          }
        }
        else if (list.size() > 0)
        {
          int a = 0;
          for (KqdsUserdocument kqdsUserdocument : list) {
            if (("家人".equals(kqdsUserdocument.getFamilyship())) && (dp.getFamilyship().equals("家人"))) {
              a = 1;
            } else if (("本人".equals(kqdsUserdocument.getFamilyship())) && (dp.getFamilyship().equals("家人"))) {
              a = 1;
            } else if (("本人".equals(kqdsUserdocument.getFamilyship())) && (dp.getFamilyship().equals("本人"))) {
              throw new Exception("患者本人重复建档手机号码不能重复！");
            }
          }
          if (a > 0)
          {
            String uuid = YZUtility.getUUID();
            dp.setSeqId(uuid);
            String pym = ChineseCharToEn.getAllFirstLetter(dp.getUsername());
            dp.setPym(pym);
            


            dp.setIsdelete(Integer.valueOf(0));
            dp.setCreatetime(YZUtility.getCurDateTimeStr());
            dp.setCreateuser(person.getSeqId());
            
            name = dp.getUsername();
            createtime = dp.getCreatetime();
            












            this.logic.insertUserDoc(dp, netorder, person, request);
            if (labelAllList != null)
            {
              kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
              for (kqdsHzLabellabeAndPatient kPatien : labelAllList)
              {
                String id = YZUtility.getUUID();
                kPatient.setSeqId(id);
                kPatient.setUserSeqId(dp.getSeqId());
                kPatient.setUserId(dp.getUsercode());
                kPatient.setUserName(dp.getUsername());
                kPatient.setLabelOneId(kPatien.getLabelOneId());
                kPatient.setLabelOneName(kPatien.getLabelOneName());
                kPatient.setLabelTwoId(kPatien.getLabelTwoId());
                kPatient.setLabelTwoName(kPatien.getLabelTwoName());
                kPatient.setLabelThreeId(kPatien.getLabelThreeId());
                kPatient.setLabelThreeName(kPatien.getLabelThreeName());
                kPatient.setCreateUser(person.getSeqId());
                kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                kPatient.setOrganization(organization);
                this.logic.saveKpatient(kPatient);
              }
              if ((dp.getAge() != null) && (!dp.getAge().equals("")))
              {
                int age = dp.getAge().intValue();
                String threename = "";
                if (age <= 30) {
                  threename = "30岁以下";
                } else if ((age >= 31) && (age <= 40)) {
                  threename = "31-40岁";
                } else if ((age >= 41) && (age <= 50)) {
                  threename = "41-50岁";
                } else if ((age >= 51) && (age <= 60)) {
                  threename = "51-60岁";
                } else if ((age >= 61) && (age <= 70)) {
                  threename = "61-70岁";
                } else if ((age >= 71) && (age <= 80)) {
                  threename = "71-80岁";
                } else if (age >= 81) {
                  threename = "80岁以上";
                }
                String threeId = this.labelLogic.findKqdsHzLabelSeqIdByLeveLabel(threename);
                String id = YZUtility.getUUID();
                kPatient.setSeqId(id);
                kPatient.setUserSeqId(dp.getSeqId());
                kPatient.setUserId(dp.getUsercode());
                kPatient.setUserName(dp.getUsername());
                kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
                kPatient.setLabelOneName("社会标签");
                kPatient.setLabelTwoId("9f1cd4c9-2aea-4309-9556-7812b225e23f");
                kPatient.setLabelTwoName("年龄");
                kPatient.setLabelThreeId(threeId);
                kPatient.setLabelThreeName(threename);
                kPatient.setCreateUser(person.getSeqId());
                kPatient.setStatus(1);
                kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                kPatient.setOrganization(organization);
                this.logic.saveKpatient(kPatient);
              }
              if ((dp.getProfession() != null) && (!dp.getProfession().equals("")))
              {
                String dictName = this.dictLogic.findDictNameBySeqId(dp.getProfession());
                Map<String, String> map1 = new HashMap();
                if ((dictName != null) && (!dictName.equals(""))) {
                  map1.put("leveLabel", dictName);
                }
                map1.put("parentId", "62aa427d-b590-4077-8b7b-4195201ec758");
                String seqid = this.labelLogic.selectKqdsHzLabelByLeveLabel(map1);
                if ((seqid == null) || (seqid.equals("")))
                {
                  KqdsLabel kqdsHzLabel = new KqdsLabel();
                  seqid = YZUtility.getUUID();
                  kqdsHzLabel.setSeqId(seqid);
                  kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
                  kqdsHzLabel.setCreateUser(person.getSeqId());
                  kqdsHzLabel.setLeveLabel(dictName);
                  kqdsHzLabel.setParentId("62aa427d-b590-4077-8b7b-4195201ec758");
                  kqdsHzLabel.setParentName("职业");
                  kqdsHzLabel.setRemark("三级");
                  this.labelLogic.insertKqdsHzLabel(kqdsHzLabel);
                }
                String id = YZUtility.getUUID();
                kPatient.setSeqId(id);
                kPatient.setUserSeqId(dp.getSeqId());
                kPatient.setUserId(dp.getUsercode());
                kPatient.setUserName(dp.getUsername());
                kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
                kPatient.setLabelOneName("社会标签");
                kPatient.setLabelTwoId("62aa427d-b590-4077-8b7b-4195201ec758");
                kPatient.setLabelTwoName("职业");
                kPatient.setLabelThreeId(seqid);
                kPatient.setLabelThreeName(dictName);
                kPatient.setCreateUser(person.getSeqId());
                kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                kPatient.setStatus(1);
                kPatient.setOrganization(organization);
                this.logic.saveKpatient(kPatient);
              }
            }
            if ((exploit != null) && (!exploit.equals("undefined")) && (exploitId != null)) {
              savePriceList(exploitId, exploit, dp.getUsername(), dp.getUsercode(), "1", person, 
                response);
            }
            if ((exploit1 != null) && (!exploit1.equals("undefined")) && (exploitId1 != null)) {
              savePriceList(exploitId1, exploit1, dp.getUsername(), dp.getUsercode(), "2", person, 
                response);
            }
          }
        }
      }
      JSONObject jobj = new JSONObject();
      
      jobj.put("name", name);
      jobj.put("createtime", createtime);
      jobj.put("usercode", dp.getUsercode());
      

      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    }
    return null;
  }
  
  public void savePriceList(String seqid, String priveListDetails, String userName, String usercode, String status, YZPerson person, HttpServletResponse response)
    throws Exception
  {
    try
    {
      KqdsHzLabelAssociated kqdsHzLabelAssociated = new KqdsHzLabelAssociated();
      KqdsHzLabelAssociated kqdsHzLabelAssociated1 = new KqdsHzLabelAssociated();
      
      Map<String, String> map = new HashMap();
      map.put("usercode", usercode);
      map.put("status", status);
      String hzLabelAssciatedSeqId = this.hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map);
      if (!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId))
      {
        kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
        kqdsHzLabelAssociated1.setUpdateTime(YZUtility.getCurDateTimeStr());
        kqdsHzLabelAssociated1.setModifier(person.getSeqId());
        int j = this.hzLabelAssociatedLogic.updateKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
        
        KqdsCostorderPriceList kqdsCostorderPriceList = new KqdsCostorderPriceList();
        kqdsCostorderPriceList.setModifier(person.getSeqId());
        kqdsCostorderPriceList.setUpdateTime(YZUtility.getCurDateTimeStr());
        kqdsCostorderPriceList.setParentId(hzLabelAssciatedSeqId);
        int i = this.priceListLogic.updatePriceList(kqdsCostorderPriceList);
      }
      hzLabelAssciatedSeqId = YZUtility.getUUID();
      kqdsHzLabelAssociated.setSeqId(hzLabelAssciatedSeqId);
      kqdsHzLabelAssociated.setLabeId(seqid);
      kqdsHzLabelAssociated.setUsercode(usercode);
      kqdsHzLabelAssociated.setUserName(userName);
      kqdsHzLabelAssociated.setCreateTime(YZUtility.getCurDateTimeStr());
      kqdsHzLabelAssociated.setCreateUser(person.getSeqId());
      kqdsHzLabelAssociated.setRemark("");
      kqdsHzLabelAssociated.setStatus(Integer.valueOf(status).intValue());
      kqdsHzLabelAssociated.setIsdelete(0);
      int j = this.hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated);
      if (j > 0)
      {
        priveListDetails = URLDecoder.decode(priveListDetails, "UTF-8");
        priveListDetails = StringEscapeUtils.unescapeJava(priveListDetails).substring(1, 
          StringEscapeUtils.unescapeJava(priveListDetails).length() - 1);
        List<KqdsCostorderPriceList> list = HUDHUtil.parseJsonToObjectList(priveListDetails, 
          KqdsCostorderPriceList.class);
        for (KqdsCostorderPriceList kqdsCostorderPriceList : list)
        {
          kqdsCostorderPriceList.setCreateuser(person.getSeqId());
          kqdsCostorderPriceList.setCreatetime(YZUtility.getCurDateTimeStr());
          kqdsCostorderPriceList.setSeqId(YZUtility.getUUID());
          kqdsCostorderPriceList.setParentId(hzLabelAssciatedSeqId);
          kqdsCostorderPriceList.setIsdelete(0);
          kqdsCostorderPriceList.setUsercode(usercode);
        }
        this.priceListLogic.insertPriceList(list);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    }
  }
  
  @RequestMapping({"/getUserCode.act"})
  public String getUserCode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      String resultString = UserCodeUtil.getUserCode(organization);
      if (YZUtility.isNullorEmpty(resultString)) {
        throw new Exception("获取患者编号失败，编号值为空");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", resultString);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/hzhb.act"})
  public String hzhb(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode1 = request.getParameter("usercode1");
      String usercode2 = request.getParameter("usercode2");
      if (usercode1.equals(usercode2)) {
        throw new Exception("同一患者不能合并");
      }
      Map map = new HashMap();
      map.put("usercode", usercode1);
      List<KqdsUserdocument> list1 = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, 
        map);
      if ((list1 == null) || (list1.isEmpty())) {
        throw new Exception("患者不存在");
      }
      map.put("usercode", usercode2);
      List<KqdsUserdocument> list2 = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, 
        map);
      if ((list2 == null) || (list2.isEmpty())) {
        throw new Exception("患者不存在");
      }
      KqdsUserdocument user1 = (KqdsUserdocument)list1.get(0);
      KqdsUserdocument user2 = (KqdsUserdocument)list2.get(0);
      

      this.logic.hzhb(user1, user2, request);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MERGE, BcjlUtil.KQDS_USERDOCUMENT, user2, user2.getUsercode(), 
        TableNameUtil.KQDS_USERDOCUMENT, request);
      
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getOneByUsercode.act"})
  public String getOneByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      
      Map map = new HashMap();
      map.put("usercode", usercode);
      
      List<KqdsUserdocument> en = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
      if ((en != null) && (en.size() > 1)) {
        throw new Exception("数据异常，一个编号对应多个患者");
      }
      Map<String, String> map2 = new HashMap();
      map2.put("usercode", usercode);
      List<KqdsMember> mlist = (List)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map2);
      BigDecimal money = BigDecimal.ZERO;
      BigDecimal givemoney = BigDecimal.ZERO;
      for (KqdsMember mobj : mlist)
      {
        BigDecimal tmpMoney = mobj.getMoney();
        money = tmpMoney.add(money);
        BigDecimal tmpGiveMoney = mobj.getGivemoney();
        givemoney = tmpGiveMoney.add(givemoney);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      
      jobj.put("money", money);
      jobj.put("givemoney", givemoney);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getOneByUsercodes.act"})
  public String getOneByUsercodes(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercodes = request.getParameter("usercodes");
      
      Map map = new HashMap();
      List<KqdsUserdocument> list = new ArrayList();
      if (!YZUtility.isNullorEmpty(usercodes))
      {
        String[] usercodeArr = usercodes.split(",");
        for (String usercode : usercodeArr)
        {
          map.put("usercode", usercode);
          List<KqdsUserdocument> en = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, 
            map);
          if ((en != null) && (en.size() > 0)) {
            list.add((KqdsUserdocument)en.get(en.size() - 1));
          }
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getUsercodeByPhoneAndName.act"})
  public String getUsercodeByPhoneAndName(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Map map = new HashMap();
      
      String username = request.getParameter("username");
      String phonenumber1 = request.getParameter("phonenumber1");
      String phonenumber2 = request.getParameter("phonenumber2");
      
      map.put("username", username);
      if (!YZUtility.isNotNullOrEmpty(phonenumber1)) {
        map.put("phonenumber1", phonenumber1);
      }
      if (!YZUtility.isNotNullOrEmpty(phonenumber2)) {
        map.put("phonenumber2", phonenumber2);
      }
      List<KqdsUserdocument> en = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
      
      JSONObject jobj = new JSONObject();
      if (en.size() > 0) {
        jobj.put("data", ((KqdsUserdocument)en.get(en.size() - 1)).getUsercode());
      } else {
        jobj.put("data", "");
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectWithNopageGh.act"})
  public String selectWithNopageGh(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String searchField = request.getParameter("searchField");
      String searchValue = request.getParameter("searchValue");
      String usercode = request.getParameter("usercode");
      
      String querydata = request.getParameter("querydata");
      
      List<JSONObject> list = new ArrayList();
      Map<String, String> map = new HashMap();
      

      map.put("isdelete", "0");
      if (!YZUtility.isNullorEmpty(querydata)) {
        map.put("querydata", querydata);
      }
      boolean search = (searchField != null) && (searchValue != null) && (!"".equals(searchValue)) && 
        (!"".equals(searchField));
      if (search)
      {
        map.put(searchField, searchValue);
        list = this.logic.selectWithNopageGh(TableNameUtil.KQDS_USERDOCUMENT, map);
      }
      else if (YZUtility.isNotNullOrEmpty(usercode))
      {
        map.put("usercode", usercode);
        list = this.logic.selectWithNopageGh(TableNameUtil.KQDS_USERDOCUMENT, map);
      }
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectWithNopageGhLike.act"})
  public String selectWithNopageGhLike(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String searchField = request.getParameter("searchField");
      String searchValue = request.getParameter("searchValue");
      String usercode = request.getParameter("usercode");
      String organization = request.getParameter("organization");
      String querydata = request.getParameter("querydata");
      
      JSONObject list = new JSONObject();
      Map<String, String> map = new HashMap();
      
      map.put("organization", organization);
      map.put("isdelete", "0");
      if (!YZUtility.isNullorEmpty(querydata)) {
        map.put("querydata", querydata);
      }
      boolean search = (searchField != null) && (searchValue != null) && (!"".equals(searchValue)) && 
        (!"".equals(searchField));
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      if (search)
      {
        map.put("searchValue", searchValue);
        list = this.logic.selectWithNopageGhLike(TableNameUtil.KQDS_USERDOCUMENT, map, bp);
      }
      else if (YZUtility.isNotNullOrEmpty(usercode))
      {
        map.put("usercode", usercode);
        list = this.logic.selectWithNopageGhLike(TableNameUtil.KQDS_USERDOCUMENT, map, bp);
      }
      YZUtility.DEAL_SUCCESS(list, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectWithNopageGhPermission.act"})
  public String selectWithNopageGhPermission(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String searchField = request.getParameter("searchField");
      String searchValue = request.getParameter("searchValue");
      String usercode = request.getParameter("usercode");
      String regsort = request.getParameter("regsort");
      String organization = request.getParameter("organization");
      String querydata = request.getParameter("querydata");
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      List<JSONObject> list = new ArrayList();
      Map<String, String> map = new HashMap();
      if (YZUtility.isNotNullOrEmpty(visualstaff)) {
        map.put("querytype", visualstaff);
      }
      if (YZUtility.isNotNullOrEmpty(regsort)) {
        map.put("regsort", regsort);
      }
      map.put("organization", organization);
      map.put("isdelete", "0");
      if (!YZUtility.isNullorEmpty(querydata)) {
        map.put("querydata", querydata);
      }
      boolean search = (searchField != null) && (searchValue != null) && (!"".equals(searchValue)) && 
        (!"".equals(searchField));
      if (search)
      {
        map.put(searchField, searchValue);
        list = this.logic.selectWithNopageGhPermission(TableNameUtil.KQDS_USERDOCUMENT, map);
      }
      else if (YZUtility.isNotNullOrEmpty(usercode))
      {
        map.put("usercode", usercode);
        list = this.logic.selectWithNopageGhPermission(TableNameUtil.KQDS_USERDOCUMENT, map);
      }
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectByUserCodes.act"})
  public String selectByUserCodes(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercodes = request.getParameter("usercodes");
      String usercodeStr = YZUtility.ConvertStringIds4Query(usercodes);
      
      List<JSONObject> list = this.logic.selectByUserCodes(TableNameUtil.KQDS_USERDOCUMENT, usercodeStr);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectWithNopage.act"})
  public String selectWithNopage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String type = request.getParameter("type");
      String searchField = request.getParameter("searchField");
      String searchValue = request.getParameter("searchValue");
      String username = request.getParameter("username");
      String sjhm = request.getParameter("sjhm");
      String typechoose = request.getParameter("typechoose");
      String organization = request.getParameter("organization");
      
      String IS_OPEN_CHAIN_SELECT = YZSysProps.getProp(SysParaUtil.IS_OPEN_CHAIN_SELECT);
      if ("1".equals(IS_OPEN_CHAIN_SELECT)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(organization)) {
        map.put("organization", organization);
      }
      if (!YZUtility.isNullorEmpty(type)) {
        map.put("type", type);
      }
      map.put("isdelete", "0");
      if (!YZUtility.isNullorEmpty(username)) {
        map.put("username", username);
      }
      if (!YZUtility.isNullorEmpty(sjhm)) {
        map.put("PhoneNumber1", sjhm);
      }
      boolean search = (searchField != null) && (searchValue != null) && (!"".equals(searchValue)) && 
        (!"".equals(searchField));
      if (search) {
        map.put(searchField, searchValue);
      }
      String operFlag = request.getParameter("operFlag");
      

      String visualstaff = "";
      if ("yyzx".equals(operFlag)) {
        visualstaff = request.getSession().getAttribute("visualstaffYyrl").toString();
      } else {
        visualstaff = SessionUtil.getVisualstaff(request);
      }
      List<JSONObject> list = this.logic.selectWithNopage(TableNameUtil.KQDS_USERDOCUMENT, map, typechoose, 
        visualstaff, person);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectWithNopage2.act"})
  public String selectWithNopage2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String sortName = request.getParameter("sortName");
    String sortOrder = request.getParameter("sortOrder");
    try
    {
      String type = request.getParameter("type");
      String starttime = request.getParameter("cjstarttime");
      String endtime = request.getParameter("cjendtime");
      if (YZUtility.isNullorEmpty(starttime)) {
        starttime = request.getParameter("starttime");
      }
      if (YZUtility.isNullorEmpty(endtime)) {
        endtime = request.getParameter("endtime");
      }
      String dystarttime = request.getParameter("dystarttime");
      String dyendtime = request.getParameter("dyendtime");
      String username = request.getParameter("username");
      String askperson = request.getParameter("askperson");
      String doctor = request.getParameter("doctor");
      String devchannel = request.getParameter("devchannel");
      String nexttype = request.getParameter("nexttype");
      String province = request.getParameter("province");
      String city = request.getParameter("city");
      String ywhf = request.getParameter("ywhf");
      String jdr = request.getParameter("jdr");
      String kfr = request.getParameter("kfr");
      
      String usercodes = request.getParameter("usercodes");
      
      String bindWX = request.getParameter("bindWX");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : 
        request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(type)) {
        map.put("type", type);
      }
      map.put("isdelete", "0");
      if (!YZUtility.isNullorEmpty(starttime))
      {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      else if ((dystarttime.equals("")) && (dyendtime.equals("")) && (username.equals("")) && (doctor.equals("")) && 
        (jdr.equals("")) && (devchannel.equals("")) && (nexttype.equals("")) && (ywhf.equals("")) && 
        (askperson.equals("")))
      {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        map.put("starttime", df.format(new Date()) + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(endtime))
      {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      else if ((dystarttime.equals("")) && (dyendtime.equals("")) && (username.equals("")) && (doctor.equals("")) && 
        (jdr.equals("")) && (devchannel.equals("")) && (nexttype.equals("")) && (ywhf.equals("")) && 
        (askperson.equals("")))
      {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        map.put("endtime", df.format(new Date()) + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(dystarttime)) {
        map.put("dystarttime", dystarttime + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(dyendtime)) {
        map.put("dyendtime", dyendtime + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(username)) {
        map.put("username", username);
      }
      if (!YZUtility.isNullorEmpty(askperson)) {
        map.put("askperson", askperson);
      }
      if (!YZUtility.isNullorEmpty(doctor)) {
        map.put("doctor", doctor);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(province)) {
        map.put("province", province);
      }
      if (!YZUtility.isNullorEmpty(city)) {
        map.put("city", city);
      }
      if (!YZUtility.isNullorEmpty(ywhf)) {
        map.put("ywhf", ywhf);
      }
      if (!YZUtility.isNullorEmpty(bindWX)) {
        map.put("bindWX", bindWX);
      }
      if (!YZUtility.isNullorEmpty(jdr)) {
        map.put("createuser", jdr);
      }
      if (!YZUtility.isNullorEmpty(kfr)) {
        map.put("developer", kfr);
      }
      if (!YZUtility.isNullorEmpty(usercodes)) {
        map.put("usercodes", usercodes);
      }
      JSONObject json = new JSONObject();
      


      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      
      String visualstaff = SessionUtil.getVisualstaff(request);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        JSONObject resut1 = this.logic.selectWithNopage2(bp, TableNameUtil.KQDS_USERDOCUMENT, visualstaff, map, 
          ChainUtil.getOrganizationFromUrl(request), json, flag);
        if (resut1 != null)
        {
          ExportBean bean = ExportTable.initExcel4RsWrite("信息查询", fieldArr, fieldnameArr, response, request);
          bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List)resut1.get("rows"), 
            "userdoc_selectNoPage");
          ExportTable.writeExcel4DownLoad("信息查询", bean.getWorkbook(), response);
        }
        return null;
      }
      if (sortName != null)
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
        json = this.logic.selectWithNopage2(bp, TableNameUtil.KQDS_USERDOCUMENT, visualstaff, map, 
          ChainUtil.getOrganizationFromUrl(request), json, flag);
      }
      else
      {
        json = this.logic.selectWithNopage2(bp, TableNameUtil.KQDS_USERDOCUMENT, visualstaff, map, 
          ChainUtil.getOrganizationFromUrl(request), json, flag);
      }
      YZUtility.DEAL_SUCCESS(json, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkIdCardNo.act"})
  public String checkIdCardNo(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Map map = new HashMap();
      
      String idcardno = request.getParameter("idcardno");
      String seqId = request.getParameter("seqId");
      List<KqdsUserdocument> en = new ArrayList();
      map.put("idcardno", idcardno);
      boolean result = true;
      if (YZUtility.isNotNullOrEmpty(seqId))
      {
        map.put("seqId", seqId);
        int num = this.logic.checkIdcardnoBySeqIdAndIdcardno(map, TableNameUtil.KQDS_USERDOCUMENT);
        if (num > 0) {
          result = false;
        }
      }
      else if (!YZUtility.isNullorEmpty(idcardno))
      {
        en = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
        result = true;
        if ((en != null) && (en.size() > 0)) {
          result = false;
        }
      }
      YZUtility.DEAL_SUCCESS_VALID(result, response);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkphonenumber.act"})
  public String checkphonenumber(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    boolean result = true;
    try
    {
      String phonenumber1 = request.getParameter("phonenumber1");
      String phonenumber2 = request.getParameter("phonenumber2");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(phonenumber1)) {
        map.put("phonenumber1", phonenumber1);
      }
      if (!YZUtility.isNullorEmpty(phonenumber2)) {
        map.put("phonenumber2", phonenumber2);
      }
      String seqId = request.getParameter("seqId");
      int num = this.logic.checkphonenumber(seqId, map, TableNameUtil.KQDS_USERDOCUMENT);
      if (num > 0) {
        result = false;
      }
      YZUtility.DEAL_SUCCESS_VALID(result, response);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/editSub.act"})
  public String editSub(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      KqdsUserdocument dp = new KqdsUserdocument();
      BeanUtils.populate(dp, request.getParameterMap());
      Map map = new HashMap();
      map.put("seq_id", dp.getSeqId());
      List<KqdsUserdocument> en = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
      if ((dp.getMedicalhistory() != null) && (!dp.getMedicalhistory().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setMedicalhistory(dp.getMedicalhistory());
      }
      if ((dp.getDrugllergy() != null) && (!dp.getDrugllergy().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setDrugllergy(dp.getDrugllergy());
      }
      if ((dp.getPhonenumber1() != null) && (!dp.getPhonenumber1().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setPhonenumber1(dp.getPhonenumber1());
      }
      if ((dp.getPhonenumber2() != null) && (!dp.getPhonenumber2().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setPhonenumber2(dp.getPhonenumber2());
      }
      ((KqdsUserdocument)en.get(0)).setSex(dp.getSex());
      if ((dp.getBirthday() != null) && (!dp.getBirthday().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setBirthday(dp.getBirthday());
      }
      if ((dp.getAddress() != null) && (!dp.getAddress().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setAddress(dp.getAddress());
      }
      if ((dp.getIntroducer() != null) && (!dp.getIntroducer().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setIntroducer(dp.getIntroducer());
      }
      if ((dp.getDevchannel() != null) && (!dp.getDevchannel().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setDevchannel(dp.getDevchannel());
      }
      if ((dp.getNexttype() != null) && (!dp.getNexttype().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setNexttype(dp.getNexttype());
      }
      if ((dp.getPlatenumber() != null) && (!dp.getPlatenumber().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setPlatenumber(dp.getPlatenumber());
      }
      if ((dp.getFirstword() != null) && (!dp.getFirstword().equals(""))) {
        ((KqdsUserdocument)en.get(0)).setFirstword(dp.getFirstword());
      }
      this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, en.get(0));
      

      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_USERDOCUMENT, en.get(0), 
        ((KqdsUserdocument)en.get(0)).getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/editGlr.act"})
  public String editGlr(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      

      String listdata = request.getParameter("data");
      JSONArray jArray = JSONArray.fromObject(listdata);
      Collection collection = JSONArray.toCollection(jArray, KqdsUserdocument.class);
      Iterator it = collection.iterator();
      while (it.hasNext())
      {
        KqdsUserdocument extension = (KqdsUserdocument)it.next();
        KqdsUserdocument en = (KqdsUserdocument)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, 
          extension.getSeqId());
        en.setGlr(extension.getGlr());
        en.setGlrremark(extension.getGlrremark());
        en.setXgtime(YZUtility.getCurDateTimeStr());
        en.setXgr(person.getSeqId());
        
        KqdsChangeWd wd = new KqdsChangeWd();
        wd.setSeqId(YZUtility.getUUID());
        wd.setCreatetime(YZUtility.getCurDateTimeStr());
        wd.setCreateuser(person.getSeqId());
        wd.setOldper(en.getCreateuser());
        wd.setToper(extension.getGlr());
        wd.setRemark(extension.getGlrremark());
        wd.setUsercode(en.getUsercode());
        wd.setUsername(en.getUsername());
        wd.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.setWd(wd, en);
        
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_WD, wd, wd.getUsercode(), 
          TableNameUtil.KQDS_CHANGE_WD, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/downTemplet.act"})
  public void downTemplet(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    File f = new File(ConstUtil.ROOT_DIR + "\\model\\批量报备模板.xls");
    
    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    try
    {
      response.setHeader("Content-Disposition", 
        "attachment;filename=" + new String("批量报备模板.xls".getBytes(), "iso-8859-1"));
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    ServletOutputStream out = response.getOutputStream();
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try
    {
      bis = new BufferedInputStream(new FileInputStream(f));
      bos = new BufferedOutputStream(out);
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
      {
        int bytesRead;
        bos.write(buff, 0, bytesRead);
      }
    }
    catch (IOException e)
    {
      throw e;
    }
    finally
    {
      if (bis != null) {
        bis.close();
      }
      if (bos != null) {
        bos.close();
      }
    }
  }
  
  @RequestMapping({"/getSsje.act"})
  public String getSsje(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercodeString = request.getParameter("usercode");
      if (YZUtility.isNullorEmpty(usercodeString)) {
        usercodeString = this.logic.getSfuser();
      }
      if (YZUtility.isNullorEmpty(usercodeString)) {
        throw new Exception("无收费的患者");
      }
      String[] usercodeArr = usercodeString.split(",");
      Map map = new HashMap();
      for (String usercode : usercodeArr)
      {
        String ssje = this.logic.getSsje(usercode);
        map.put("usercode", usercode);
        List<KqdsUserdocument> list1 = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, 
          map);
        if ((list1 == null) || (list1.isEmpty())) {
          throw new Exception("患者不存在");
        }
        KqdsUserdocument entity = (KqdsUserdocument)list1.get(0);
        entity.setTotalpay(new BigDecimal(ssje));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, entity);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getBaseUserInfoByUsercode.act"})
  public String getBaseUserInfoByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      JSONObject rtjson = this.logic.getBaseUserInfoByUsercode(usercode);
      YZUtility.DEAL_SUCCESS(rtjson, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/jingQueChaXun4Net.act"})
  public String jingQueChaXun4Net(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String queryinput = request.getParameter("queryinput");
      String queryInputName = request.getParameter("queryInputName");
      if ((!YZUtility.isNullorEmpty(queryinput)) && (queryinput.length() < 8)) {
        throw new Exception("请输入精确查询条件，手机号码长度不能小于8");
      }
      if ((!YZUtility.isNullorEmpty(queryInputName)) && (queryInputName.length() < 2)) {
        throw new Exception("请输入精确查询条件，姓名长度不能小于2");
      }
      List<JSONObject> list = this.logic.jingQueChaXun4Net(queryinput, queryInputName);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/userManger4Wdzx.act"})
  public String userManger4Wdzx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      String jdtime1 = request.getParameter("jdtime1");
      String jdtime2 = request.getParameter("jdtime2");
      String yewu = request.getParameter("yewu");
      String shouli = request.getParameter("shouli");
      String gongju = request.getParameter("gongju");
      String yytime1 = request.getParameter("yytime1");
      String yytime2 = request.getParameter("yytime2");
      String xiangmu = request.getParameter("xiangmu");
      String level = request.getParameter("level");
      String important = request.getParameter("important");
      String queryinput = request.getParameter("queryinput");
      String doorstatus = request.getParameter("doorstatus");
      String cjstatus = request.getParameter("cjstatus");
      String devchannel = request.getParameter("devchannel");
      String nexttype = request.getParameter("nexttype");
      String organization = request.getParameter("organization");
      

      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : 
        request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(jdtime1)) {
        map.put("jdtime1", jdtime1 + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(jdtime2)) {
        map.put("jdtime2", jdtime2 + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(yewu)) {
        map.put("yewu", yewu);
      }
      if (!YZUtility.isNullorEmpty(shouli)) {
        map.put("shouli", shouli);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("gongju", gongju);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(yytime1))
      {
        if (yytime1.length() == 19) {
          yytime1 = yytime1.substring(0, yytime1.length() - 3);
        }
        if (yytime1.length() == 10) {
          yytime1 = yytime1 + ConstUtil.HOUR_START;
        }
        map.put("yytime1", yytime1);
      }
      if (!YZUtility.isNullorEmpty(yytime2))
      {
        if (yytime2.length() == 19) {
          yytime2 = yytime2.substring(0, yytime2.length() - 3);
        }
        if (yytime2.length() == 10) {
          yytime2 = yytime2 + ConstUtil.HOUR_END;
        }
        map.put("yytime2", yytime2);
      }
      if (!YZUtility.isNullorEmpty(xiangmu)) {
        map.put("xiangmu", xiangmu);
      }
      if (!YZUtility.isNullorEmpty(level)) {
        map.put("level", level);
      }
      if (!YZUtility.isNullorEmpty(important)) {
        map.put("important", important);
      }
      if (!YZUtility.isNullorEmpty(doorstatus)) {
        map.put("doorstatus", doorstatus);
      }
      if (!YZUtility.isNullorEmpty(cjstatus)) {
        map.put("cjstatus", cjstatus);
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      if (!YZUtility.isNullorEmpty(sortName)) {
        map.put("sortName", sortName);
      }
      if (!YZUtility.isNullorEmpty(sortOrder)) {
        map.put("sortOrder", sortOrder);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      String otherpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_WANGDIAN_SEQID);
      visualstaff = this.personLogic.filterVisualPersons(ConstUtil.DEPT_TYPE_2, visualstaff, otherpriv);
      if ("''".equals(visualstaff)) {
        visualstaff = "'-1'";
      }
      SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
      String today = formatter2.format(new Date());
      




      JSONObject list = new JSONObject();
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      if ((sortName == null) && (sortOrder != null) && (jdtime1.equals("")) && (jdtime2.equals("")) && (yewu.equals("")) && 
        (shouli.equals("")) && (gongju.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && 
        (xiangmu.equals("")) && (level == null) && (important.equals("")) && (queryinput.equals("")) && 
        (doorstatus.equals("")) && (cjstatus.equals("")) && (devchannel.equals("")) && (nexttype.equals("")))
      {
        Map<String, String> map2 = new HashMap();
        
        map2.put("jdtime1", today + ConstUtil.TIME_START);
        map2.put("jdtime2", today + ConstUtil.TIME_END);
        map2.put("mryytime1", today + ConstUtil.TIME_START);
        list = this.logic.userManger4Wdzx(TableNameUtil.KQDS_NET_ORDER, person, map2, visualstaff, organization, bp);
      }
      else if ((sortName != null) && (sortOrder != null) && (jdtime1.equals("")) && (jdtime2.equals("")) && 
        (yewu.equals("")) && (shouli.equals("")) && (gongju.equals("")) && (yytime1.equals("")) && 
        (yytime2.equals("")) && (xiangmu.equals("")) && (level == null) && (important.equals("")) && 
        (queryinput.equals("")) && (doorstatus.equals("")) && (cjstatus.equals("")) && (devchannel.equals("")) && 
        (nexttype.equals("")))
      {
        Map<String, String> map2 = new HashMap();
        
        map2.put("jdtime1", today + ConstUtil.TIME_START);
        map2.put("jdtime2", today + ConstUtil.TIME_END);
        map2.put("mryytime1", today + ConstUtil.TIME_START);
        map2.put("sortName", sortName);
        map2.put("sortOrder", sortOrder);
        list = this.logic.userManger4Wdzx(TableNameUtil.KQDS_NET_ORDER, person, map2, visualstaff, organization, bp);
      }
      else
      {
        list = this.logic.userManger4Wdzx(TableNameUtil.KQDS_NET_ORDER, person, map, visualstaff, organization, bp);
      }
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("网电中心-客户列表", fieldArr, fieldnameArr, list.getJSONArray("rows"), 
          response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(list, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/userManger4Kfzx.act"})
  public String userManger4Kfzx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      JSONObject list = new JSONObject();
      YZPerson person = SessionUtil.getLoginPerson(request);
      String jdtime1 = request.getParameter("jdtime1");
      String jdtime2 = request.getParameter("jdtime2");
      String yewu = request.getParameter("yewu");
      String devchannel = request.getParameter("devchannel");
      String nexttype = request.getParameter("nexttype");
      String shouli = request.getParameter("shouli");
      String gongju = request.getParameter("gongju");
      String yytime1 = request.getParameter("yytime1");
      String yytime2 = request.getParameter("yytime2");
      String xiangmu = request.getParameter("xiangmu");
      String level = request.getParameter("level");
      String important = request.getParameter("important");
      String queryinput = request.getParameter("queryinput");
      String doorstatus = request.getParameter("doorstatus");
      String cjstatus = request.getParameter("cjstatus");
      String ywhf = request.getParameter("ywhf");
      String customer = request.getParameter("customer");
      String consumer = request.getParameter("consumer");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      


      String askperson = request.getParameter("askperson");
      String needOne = request.getParameter("needOne");
      String societyOne = request.getParameter("societyOne");
      String expenseOne = request.getParameter("expenseOne");
      String needTwo = request.getParameter("needTwo");
      String societyTwo = request.getParameter("societyTwo");
      String expenseTwo = request.getParameter("expenseTwo");
      String needThree = request.getParameter("needThree");
      String societyThree = request.getParameter("societyThree");
      String expenseThree = request.getParameter("expenseThree");
      String jdtimelabel1 = request.getParameter("jdtimelabel1");
      String jdtimelabel2 = request.getParameter("jdtimelabel2");
      String starsLevelTwo = request.getParameter("starsLevelTwo");
      String unsatisfiedTwo = request.getParameter("unsatisfiedTwo");
      String starsLevelThree = request.getParameter("starsLevelThree");
      


      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : 
        request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap();
      JSONObject json = new JSONObject();
      String labelID = "";
      if (!YZUtility.isNullorEmpty(needOne)) {
        labelID = labelID + needOne + ",";
      }
      if (!YZUtility.isNullorEmpty(societyOne)) {
        labelID = labelID + societyOne + ",";
      }
      if (!YZUtility.isNullorEmpty(expenseOne)) {
        labelID = labelID + expenseOne + ",";
      }
      if (!YZUtility.isNullorEmpty(needTwo)) {
        labelID = labelID + needTwo + ",";
      }
      if (!YZUtility.isNullorEmpty(societyTwo)) {
        labelID = labelID + societyTwo + ",";
      }
      if (!YZUtility.isNullorEmpty(expenseTwo)) {
        labelID = labelID + expenseTwo + ",";
      }
      if (!YZUtility.isNullorEmpty(needThree)) {
        labelID = labelID + needThree + ",";
      }
      if (!YZUtility.isNullorEmpty(societyThree)) {
        labelID = labelID + societyThree + ",";
      }
      if (!YZUtility.isNullorEmpty(expenseThree)) {
        labelID = labelID + expenseThree + ",";
      }
      if (!YZUtility.isNullorEmpty(askperson)) {
        labelID = labelID + askperson + ",";
      }
      if (!YZUtility.isNullorEmpty(starsLevelThree)) {
        labelID = labelID + starsLevelThree + ",";
      }
      if (!YZUtility.isNullorEmpty(starsLevelTwo)) {
        labelID = labelID + starsLevelTwo + ",";
      }
      if (!YZUtility.isNullorEmpty(unsatisfiedTwo)) {
        labelID = labelID + unsatisfiedTwo + ",";
      }
      if (!YZUtility.isNullorEmpty(jdtimelabel1)) {
        map.put("jdtime1", jdtimelabel1 + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(jdtimelabel2)) {
        map.put("jdtime2", jdtimelabel2 + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(consumer)) {
        map.put("consumer", consumer);
      }
      if (!YZUtility.isNullorEmpty(customer)) {
        map.put("customer", customer);
      }
      if (!YZUtility.isNullorEmpty(jdtime1)) {
        map.put("jdtime1", jdtime1 + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(jdtime2)) {
        map.put("jdtime2", jdtime2 + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(yewu)) {
        map.put("yewu", yewu);
      }
      if (!YZUtility.isNullorEmpty(shouli)) {
        map.put("shouli", shouli);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("gongju", gongju);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(yytime1))
      {
        if (yytime1.length() == 10) {
          yytime1 = yytime1 + ConstUtil.TIME_START;
        }
        map.put("yytime1", yytime1);
      }
      if (!YZUtility.isNullorEmpty(yytime2))
      {
        if (yytime2.length() == 10) {
          yytime2 = yytime2 + ConstUtil.TIME_END;
        }
        map.put("yytime2", yytime2);
      }
      if (!YZUtility.isNullorEmpty(xiangmu)) {
        map.put("xiangmu", xiangmu);
      }
      if (!YZUtility.isNullorEmpty(level)) {
        map.put("level", level);
      }
      if (!YZUtility.isNullorEmpty(important)) {
        map.put("important", important);
      }
      if (!YZUtility.isNullorEmpty(doorstatus)) {
        map.put("doorstatus", doorstatus);
      }
      if (!YZUtility.isNullorEmpty(cjstatus)) {
        map.put("cjstatus", cjstatus);
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      if (!YZUtility.isNullorEmpty(sortName))
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      }
      double time1 = 0.0D;
      double time2 = 0.0D;
      if ((!YZUtility.isNullorEmpty(jdtimelabel1)) && (!YZUtility.isNullorEmpty(jdtimelabel2)))
      {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse((String)map.get("jdtime1"));
        
        time1 = date1.getTime();
        Date date2 = format.parse((String)map.get("jdtime2"));
        
        time2 = date2.getTime();
      }
      if ((!YZUtility.isNullorEmpty(jdtimelabel1)) && (YZUtility.isNullorEmpty(jdtimelabel2)))
      {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse((String)map.get("jdtime1"));
        
        time1 = date1.getTime();
        time2 = System.currentTimeMillis();
      }
      if ((YZUtility.isNullorEmpty(jdtimelabel1)) && (!YZUtility.isNullorEmpty(jdtimelabel2)))
      {
        time1 = System.currentTimeMillis();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = format.parse((String)map.get("jdtime2"));
        
        time2 = date2.getTime();
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
      String today = formatter2.format(new Date());
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      if (map.isEmpty())
      {
        Map<String, String> map2 = new HashMap();
        
        map2.put("jdtime1", today + ConstUtil.TIME_START);
        map2.put("jdtime2", today + ConstUtil.TIME_END);
        map2.put("mryytime1", today + ConstUtil.TIME_START);
        list = this.logic.userManger4Kfzx(TableNameUtil.KQDS_NET_ORDER, person, map2, ywhf, visualstaff, 
          organization, bp, json, Double.valueOf(time1), Double.valueOf(time2), labelID);
      }
      else if ((sortName != null) && (sortOrder != null) && (jdtime1.equals("")) && (jdtime2.equals("")) && 
        (yewu.equals("")) && (shouli.equals("")) && (gongju.equals("")) && (yytime1.equals("")) && 
        (yytime2.equals("")) && (xiangmu.equals("")) && (level == null) && (important.equals("")) && 
        (queryinput.equals("")) && (doorstatus.equals("")) && (cjstatus.equals("")) && (devchannel.equals("")) && 
        (nexttype.equals("")) && (customer.equals("")) && (consumer == null))
      {
        Map<String, String> map2 = new HashMap();
        
        map2.put("jdtime1", today + ConstUtil.TIME_START);
        map2.put("jdtime2", today + ConstUtil.TIME_END);
        map2.put("mryytime1", today + ConstUtil.TIME_START);
        map2.put("sortName", sortName);
        map2.put("sortOrder", sortOrder);
        list = this.logic.userManger4Kfzx(TableNameUtil.KQDS_NET_ORDER, person, map2, ywhf, visualstaff, 
          organization, bp, json, Double.valueOf(time1), Double.valueOf(time2), labelID);
      }
      else
      {
        list = this.logic.userManger4Kfzx(TableNameUtil.KQDS_NET_ORDER, person, map, ywhf, visualstaff, organization, 
          bp, json, Double.valueOf(time1), Double.valueOf(time2), labelID);
      }
      if ((flag != null) && (flag.equals("exportTable")))
      {
        List<JSONObject> da = list.getJSONArray("rows");
        if (da.size() > 0) {
          for (JSONObject job : da)
          {
            if ("1".equals(job.getString("cjstatus"))) {
              job.put("cjstatus", "已成交");
            } else {
              job.put("cjstatus", "未成交");
            }
            if ("1".equals(job.getString("zdoorstatus"))) {
              job.put("zdoorstatus", "已上门");
            } else {
              job.put("zdoorstatus", "未上门");
            }
            if ("1".equals(job.getString("doorstatus"))) {
              job.put("doorstatus", "已上门");
            } else {
              job.put("doorstatus", "未上门");
            }
            if ("0".equals(job.getString("ywhf"))) {
              job.put("ywhf", "无回访");
            } else {
              job.put("ywhf", "有回访");
            }
          }
        }
        ExportTable.exportBootStrapTable2Excel("客服中心-客户列表", fieldArr, fieldnameArr, da, response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(list, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/userManger4Yxzx.act"})
  public String userManger4Yxzx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String jdtime1 = request.getParameter("jdtime1");
      String jdtime2 = request.getParameter("jdtime2");
      String yewu = request.getParameter("yewu");
      String devchannel = request.getParameter("devchannel");
      String nexttype = request.getParameter("nexttype");
      String shouli = request.getParameter("shouli");
      String gongju = request.getParameter("gongju");
      String yytime1 = request.getParameter("yytime1");
      String yytime2 = request.getParameter("yytime2");
      String xiangmu = request.getParameter("xiangmu");
      String level = request.getParameter("level");
      String important = request.getParameter("important");
      String queryinput = request.getParameter("queryinput");
      String doorstatus = request.getParameter("doorstatus");
      String cjstatus = request.getParameter("cjstatus");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : 
        request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(jdtime1)) {
        map.put("jdtime1", jdtime1 + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(jdtime2)) {
        map.put("jdtime2", jdtime2 + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(yewu)) {
        map.put("yewu", yewu);
      }
      if (!YZUtility.isNullorEmpty(devchannel)) {
        map.put("devchannel", devchannel);
      }
      if (!YZUtility.isNullorEmpty(nexttype)) {
        map.put("nexttype", nexttype);
      }
      if (!YZUtility.isNullorEmpty(shouli)) {
        map.put("shouli", shouli);
      }
      if (!YZUtility.isNullorEmpty(gongju)) {
        map.put("gongju", gongju);
      }
      if (!YZUtility.isNullorEmpty(yytime1))
      {
        if (yytime1.length() == 19) {
          yytime1 = yytime1.substring(0, yytime1.length() - 3);
        }
        if (yytime1.length() == 10) {
          yytime1 = yytime1 + ConstUtil.HOUR_START;
        }
        map.put("yytime1", yytime1);
      }
      if (!YZUtility.isNullorEmpty(yytime2))
      {
        if (yytime2.length() == 19) {
          yytime2 = yytime2.substring(0, yytime2.length() - 3);
        }
        if (yytime2.length() == 10) {
          yytime2 = yytime2 + ConstUtil.HOUR_END;
        }
        map.put("yytime2", yytime2);
      }
      if (!YZUtility.isNullorEmpty(xiangmu)) {
        map.put("xiangmu", xiangmu);
      }
      if (!YZUtility.isNullorEmpty(level)) {
        map.put("level", level);
      }
      if (!YZUtility.isNullorEmpty(important)) {
        map.put("important", important);
      }
      if (!YZUtility.isNullorEmpty(doorstatus)) {
        map.put("doorstatus", doorstatus);
      }
      if (!YZUtility.isNullorEmpty(cjstatus)) {
        map.put("cjstatus", cjstatus);
      }
      if (!YZUtility.isNullorEmpty(queryinput)) {
        map.put("queryinput", queryinput);
      }
      if (!YZUtility.isNullorEmpty(sortName))
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      
      String otherpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHICHANG_SEQID);
      visualstaff = this.personLogic.filterVisualPersons(ConstUtil.DEPT_TYPE_3, visualstaff, otherpriv);
      if ("''".equals(visualstaff)) {
        visualstaff = "'-1'";
      }
      JSONObject list = new JSONObject();
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
      String today = formatter2.format(new Date());
      if (map.isEmpty())
      {
        Map<String, String> map2 = new HashMap();
        
        map2.put("jdtime1", today + ConstUtil.TIME_START);
        map2.put("jdtime2", today + ConstUtil.TIME_END);
        map2.put("mryytime1", today + ConstUtil.TIME_START);
        list = this.logic.userManger4Yxzx(TableNameUtil.KQDS_NET_ORDER, person, map2, visualstaff, organization, bp);
      }
      else if ((sortName != null) && (sortOrder != null) && (jdtime1.equals("")) && (jdtime2.equals("")) && 
        (yewu.equals("")) && (devchannel.equals("")) && (nexttype.equals("")) && (shouli.equals("")) && 
        (gongju.equals("")) && (yytime1.equals("")) && (yytime2.equals("")) && (xiangmu.equals("")) && 
        (level == null) && (important.equals("")) && (queryinput.equals("")) && (doorstatus.equals("")) && 
        (cjstatus.equals("")))
      {
        Map<String, String> map2 = new HashMap();
        
        map2.put("jdtime1", today + ConstUtil.TIME_START);
        map2.put("jdtime2", today + ConstUtil.TIME_END);
        map2.put("mryytime1", today + ConstUtil.TIME_START);
        map2.put("sortName", sortName);
        map2.put("sortOrder", sortOrder);
        list = this.logic.userManger4Yxzx(TableNameUtil.KQDS_NET_ORDER, person, map2, visualstaff, organization, bp);
      }
      else
      {
        list = this.logic.userManger4Yxzx(TableNameUtil.KQDS_NET_ORDER, person, map, visualstaff, organization, bp);
      }
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("营销中心-客户列表", fieldArr, fieldnameArr, list.getJSONArray("rows"), 
          response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(list, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
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
      String createuser = request.getParameter("createuser");
      String deptparentId = request.getParameter("deptparentId");
      String deptId = request.getParameter("deptId");
      String username = request.getParameter("username");
      String sjhm = request.getParameter("sjhm");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String visualstaff = SessionUtil.getVisualstaff(request);
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(createuser)) {
        map.put("createuser", createuser);
      }
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      if (!YZUtility.isNullorEmpty(deptparentId)) {
        map.put("deptparentId", deptparentId);
      }
      if (!YZUtility.isNullorEmpty(deptId)) {
        map.put("deptId", deptId);
      }
      if (!YZUtility.isNullorEmpty(username)) {
        map.put("username", username);
      }
      if (!YZUtility.isNullorEmpty(sjhm)) {
        map.put("sjhm", sjhm);
      }
      if (!YZUtility.isNullorEmpty(visualstaff)) {
        map.put("visualstaff", visualstaff);
      }
      JSONObject data = this.logic.selectWithPage(bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getUsercodeByPhonenumber.act"})
  public String getUsercodeByPhonenumber(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String phonenumber = request.getParameter("phonenumber");
      if (YZUtility.isNullorEmpty(phonenumber)) {
        throw new Exception("手机号码不能为空");
      }
      JSONObject jobj = this.logic.getusercodeBYsjhm(YZAuthenticator.encryKqdsPhonenumber(phonenumber));
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/toWdzx_xgjdr.act"})
  public ModelAndView toWdzxGjd(HttpServletRequest request, HttpServletResponse response)
  {
    String[] selectedrows = request.getParameterValues("selectedrows");
    ModelAndView mv = new ModelAndView();
    mv.addObject(selectedrows);
    mv.setViewName("/kqdsFront/index/kfzx/wdzx_jdr_set.jsp");
    return mv;
  }
  
  @RequestMapping({"/updateLabel.act"})
  public String updateLabel(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String userCode = request.getParameter("userCode");
    String userName = request.getParameter("userName");
    String labelAllArr = request.getParameter("labelAllArr");
    String exploit = request.getParameter("exploit");
    String exploit1 = request.getParameter("exploit1");
    String exploitId = request.getParameter("exploitId");
    String exploitId1 = request.getParameter("exploitId1");
    String organization = ChainUtil.getCurrentOrganization(request);
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      List<kqdsHzLabellabeAndPatient> labelAllList = null;
      if (labelAllArr != null)
      {
        labelAllArr = URLDecoder.decode(labelAllArr, "UTF-8");
        labelAllList = HUDHUtil.parseJsonToObjectList(labelAllArr, kqdsHzLabellabeAndPatient.class);
      }
      if (labelAllList != null)
      {
        String labelID = "";
        String labelName1 = "";
        String labelName2 = "";
        String labelName3 = "";
        

        this.logic.deleteLabel(userCode);
        
        kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
        for (kqdsHzLabellabeAndPatient kPatien : labelAllList)
        {
          String id = YZUtility.getUUID();
          kPatient.setSeqId(id);
          kPatient.setUserId(userCode);
          kPatient.setUserName(userName);
          kPatient.setLabelOneId(kPatien.getLabelOneId());
          kPatient.setLabelOneName(kPatien.getLabelOneName());
          kPatient.setLabelTwoId(kPatien.getLabelTwoId());
          kPatient.setLabelTwoName(kPatien.getLabelTwoName());
          kPatient.setOpinion(kPatien.getOpinion());
          if (kPatien.getLabelThreeId() == null) {
            kPatien.setLabelThreeId("00165d45-650b-4394-9768-4482a0ca9b05");
          }
          kPatient.setLabelThreeId(kPatien.getLabelThreeId());
          kPatient.setOpinion(kPatien.getOpinion());
          kPatient.setLabelThreeName(kPatien.getLabelThreeName());
          kPatient.setCreateUser(person.getSeqId());
          kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
          kPatient.setOrganization(organization);
          this.logic.saveKpatient(kPatient);
          if (!labelID.contains(kPatien.getLabelOneId())) {
            labelID = labelID + kPatien.getLabelOneId() + ",";
          }
          if (!labelID.contains(kPatien.getLabelTwoId())) {
            labelID = labelID + kPatien.getLabelTwoId() + ",";
          }
          if (!labelID.contains(kPatien.getLabelThreeId())) {
            labelID = labelID + kPatien.getLabelThreeId() + ",";
          }
          if (labelName1.equals("")) {
            labelName1 = 
              kPatien.getLabelOneName() + ":" + kPatien.getLabelTwoName() + ":" + kPatien.getLabelThreeName();
          } else if (labelName1.contains(kPatien.getLabelOneName()))
          {
            if (labelName1.contains(kPatien.getLabelTwoName())) {
              labelName1 = labelName1 + "," + kPatien.getLabelThreeName();
            } else {
              labelName1 = labelName1 + ";" + kPatien.getLabelTwoName() + ":" + kPatien.getLabelThreeName();
            }
          }
          else if (labelName2.equals("")) {
            labelName2 = 
              kPatien.getLabelOneName() + ":" + kPatien.getLabelTwoName() + ":" + kPatien.getLabelThreeName();
          } else if (labelName2.contains(kPatien.getLabelOneName()))
          {
            if (labelName2.contains(kPatien.getLabelTwoName())) {
              labelName2 = labelName2 + "," + kPatien.getLabelThreeName();
            } else {
              labelName2 = 
                labelName2 + ";" + kPatien.getLabelTwoName() + ":" + kPatien.getLabelThreeName();
            }
          }
          else if (labelName3.equals("")) {
            labelName3 = 
              kPatien.getLabelOneName() + ":" + kPatien.getLabelTwoName() + ":" + kPatien.getLabelThreeName();
          } else if (labelName3.contains(kPatien.getLabelOneName())) {
            if (labelName3.contains(kPatien.getLabelTwoName())) {
              labelName3 = labelName3 + "," + kPatien.getLabelThreeName();
            } else {
              labelName3 = 
                labelName3 + ";" + kPatien.getLabelTwoName() + ":" + kPatien.getLabelThreeName();
            }
          }
        }
        if (!labelID.equals(""))
        {
          CacheUtil.openCache();
          String labelName = "";
          if (!labelName1.equals("")) {
            labelName = labelName + labelName1 + "。";
          }
          if (!labelName2.equals("")) {
            labelName = labelName + labelName2 + "。";
          }
          if (!labelName3.equals("")) {
            labelName = labelName + labelName3 + "。";
          }
          Object key = new HashMap();
          ((Map)key).put(userCode + "," + labelID, userCode);
          Map<String, String> key1 = new HashMap();
          key1.put(userCode, labelName);
          Set<String> c = new HashSet();
          if (organization.equals("HUDH"))
          {
            c = CacheUtil.keys("labelQuery:*" + userCode + "*");
            if (c.size() > 0)
            {
              for (String string : c)
              {
                Double time = CacheUtil.getZSetScore("label:key", string.substring(11, string.length()));
                CacheUtil.delMapKey("label:value", string.substring(11, string.length()));
                CacheUtil.delMapKey("label:name", userCode);
                CacheUtil.removeZSet("label:key", string.substring(11, string.length()));
                CacheUtil.del(new String[] {string });
                CacheUtil.setMap("label:value", (Map)key);
                CacheUtil.setMap("label:name", key1);
                CacheUtil.addZSet("label:key", userCode + "," + labelID, time.doubleValue());
                CacheUtil.set("labelQuery:" + userCode + "," + labelID, userCode);
              }
            }
            else
            {
              CacheUtil.setMap("label:value", (Map)key);
              CacheUtil.setMap("label:name", key1);
              CacheUtil.addZSet("label:key", userCode + "," + labelID, 
                Double.parseDouble(System.currentTimeMillis()));
              CacheUtil.set("labelQuery:" + userCode + "," + labelID, userCode);
            }
          }
          else
          {
            c = CacheUtil.keys(organization + ":labelQuery:*" + userCode + "*");
            if (c.size() > 0)
            {
              for (String string : c)
              {
                Double time = CacheUtil.getZSetScore(organization + ":label:key", string.substring(16, string.length()));
                CacheUtil.delMapKey(organization + ":label:value", string.substring(16, string.length()));
                CacheUtil.delMapKey(organization + ":label:name", userCode);
                CacheUtil.removeZSet(organization + ":label:key", string.substring(16, string.length()));
                CacheUtil.del(new String[] {string });
                CacheUtil.setMap(organization + ":label:value", (Map)key);
                CacheUtil.setMap(organization + ":label:name", key1);
                CacheUtil.addZSet(organization + ":label:key", userCode + "," + labelID, time.doubleValue());
                CacheUtil.set(organization + ":labelQuery:" + userCode + "," + labelID, userCode);
              }
            }
            else
            {
              CacheUtil.setMap(organization + ":label:value", (Map)key);
              CacheUtil.setMap(organization + ":label:name", key1);
              CacheUtil.addZSet(organization + ":label:key", userCode + "," + labelID, 
                Double.parseDouble(System.currentTimeMillis()));
              CacheUtil.set(organization + ":labelQuery:" + userCode + "," + labelID, userCode);
            }
          }
          CacheUtil.close();
        }
        if ((exploit != null) && (!exploit.equals("undefined")) && (exploitId != null)) {
          savePriceList(exploitId, exploit, userName, userCode, "1", person, response);
        }
        if ((exploit1 != null) && (!exploit1.equals("undefined")) && (exploitId1 != null)) {
          savePriceList(exploitId1, exploit1, userName, userCode, "2", person, response);
        }
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_USERDOCUMENT, labelAllArr, userCode, 
          TableNameUtil.KQDS_USERDOCUMENT, request);
        YZUtility.DEAL_SUCCESS(null, "保存成功!", response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR("保存失败!", false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateBlcode.act"})
  public String updateBlcode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      String blcode = request.getParameter("blcode");
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      if (!YZUtility.isNullorEmpty(blcode)) {
        map.put("blcode", blcode);
      }
      int num = this.logic.checkBlcode(null, blcode, TableNameUtil.KQDS_USERDOCUMENT);
      if (num > 0)
      {
        YZUtility.DEAL_SUCCESS(null, "病历号已存在", response, this.logger);
      }
      else
      {
        int j = this.logic.updateBlcode(map);
        if (j > 0) {
          YZUtility.DEAL_SUCCESS(null, "修改成功", response, this.logger);
        } else {
          YZUtility.DEAL_ERROR("修改失败", false, null, response, this.logger);
        }
      }
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findByUsercode.act"})
  public List<JSONObject> findByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      List<JSONObject> list = this.logic.findByUsercode(usercode);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
}
