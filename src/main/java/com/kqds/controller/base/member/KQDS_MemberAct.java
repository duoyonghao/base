package com.kqds.controller.base.member;

import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsMemberRecord;
import com.kqds.entity.base.KqdsMemberRecordSh;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.member.KQDS_MemberLogic;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
import java.util.Date;
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
@RequestMapping({"KQDS_MemberAct"})
public class KQDS_MemberAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_MemberAct.class);
  @Autowired
  private YZPersonLogic personLogic;
  @Autowired
  private KQDS_MemberLogic logic;
  @Autowired
  private YZFkfsLogic fkfsLogic;
  
  @RequestMapping({"/toMemberTkSq.act"})
  public ModelAndView toMemberTkSq(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/member/member_tk_sq.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHyzxIndex.act"})
  public ModelAndView toCkIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/member/hyzx_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMethodModify.act"})
  public ModelAndView toMethodModify(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/methodModify.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMethodModifyList.act"})
  public ModelAndView toMethodModifyList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/methodModifyList.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberAdd.act"})
  public ModelAndView toMemberAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.setViewName("/kqdsFront/member/member_add.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberChongzhi.act"})
  public ModelAndView toMemberChongzhi(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/member_chongzhi.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberZhuanzeng.act"})
  public ModelAndView toMemberZhuanzeng(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/member/member_zhuanzeng.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberZengSong.act"})
  public ModelAndView toMemberZengSong(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    String memberno = request.getParameter("memberno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("username", username);
    mv.addObject("memberno", memberno);
    mv.setViewName("/kqdsFront/member/member_zengsong.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberRecord.act"})
  public ModelAndView toMemberRecord(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/member/member_record.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberOpencard.act"})
  public ModelAndView toMemberOpencard(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.setViewName("/kqdsFront/member/opencard.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberRecharge.act"})
  public ModelAndView toMemberRecharge(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.setViewName("/kqdsFront/member/recharge.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberTuifei.act"})
  public ModelAndView toMemberTuifei(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.setViewName("/kqdsFront/member/tuifei.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberPay.act"})
  public ModelAndView toMemberPay(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.setViewName("/kqdsFront/member/pay.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberQuery.act"})
  public ModelAndView toMemberQuery(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.setViewName("/kqdsFront/member/member_query.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZengsong_Give_Record.act"})
  public ModelAndView toZengsong_Give_Record(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    String memberno = request.getParameter("memberno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.addObject("memberno", memberno);
    mv.setViewName("/kqdsFront/member/zengsong_give_record.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZengsong_User_Record.act"})
  public ModelAndView toZengsong_User_Record(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    String memberno = request.getParameter("memberno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.addObject("memberno", memberno);
    mv.setViewName("/kqdsFront/member/zengsong_use_record.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZengsongQuery.act"})
  public ModelAndView toZengsongQuery(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.setViewName("/kqdsFront/member/zengsong_query.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZhuanzengQuery.act"})
  public ModelAndView toZhuanzengQuery(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String menuid = request.getParameter("menuid");
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuid", menuid);
    mv.setViewName("/kqdsFront/member/zhuanzeng_query.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberBangding.act"})
  public ModelAndView toMemberBangding(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/kqds_member_binding.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberEdit.act"})
  public ModelAndView toMemberEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/member_edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberEditpassIndex.act"})
  public ModelAndView toMemberEditpassIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/kqds_member_editpass_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberEditpass.act"})
  public ModelAndView toMemberEditpass(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/kqds_member_editpass.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberSetAskperson.act"})
  public ModelAndView toMemberSetAskperson(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/askperson_set.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberAskpersonList.act"})
  public ModelAndView toMemberAskpersonList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/askperson_list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toember_TkList.act"})
  public ModelAndView toCost_TkList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/member/member_tklist.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMemberTuifeiPage.act"})
  public ModelAndView toMemberTuifeiPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/member/member_tuifei.jsp");
    return mv;
  }
  
  @RequestMapping({"/toember_Yujiaojin.act"})
  public ModelAndView toember_Yujiaojin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/member/member_yujiaojin.jsp");
    return mv;
  }
  
  @RequestMapping({"/editPass.act"})
  public String editPass(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      KqdsMember dp = new KqdsMember();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("请选择会员卡");
      }
      dp.setIsbinding(Integer.valueOf(1));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
      BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEMBER_MODIFY_PWD, dp, TableNameUtil.KQDS_MEMBER, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkPassword.act"})
  public String checkPassword(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    boolean result = true;
    try
    {
      String password = request.getParameter("password");
      String seqId = request.getParameter("seqId");
      KqdsMember en = (KqdsMember)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
      if (en == null) {
        throw new Exception("数据不存在");
      }
      if (!password.equals(en.getPassword())) {
        result = false;
      }
      YZUtility.DEAL_SUCCESS_VALID(result, response);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/bindingAdd.act"})
  public String bindingAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      KqdsMember dp = new KqdsMember();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("请选择需要绑定IC卡的会员卡");
      }
      if (YZUtility.isNullorEmpty(dp.getIcno())) {
        throw new Exception("无绑定的IC卡");
      }
      Map<String, String> map = new HashMap();
      map.put("icno", dp.getIcno());
      List<KqdsMember> iclist = (List)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
      if ((iclist != null) && (iclist.size() > 0)) {
        throw new Exception("该IC卡已绑定过其他会员卡");
      }
      dp.setIsbinding(Integer.valueOf(1));
      this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
      BcjlUtil.LogBcjl(BcjlUtil.UPDATE, BcjlUtil.KQDS_MEMBER_BIND, dp, TableNameUtil.KQDS_MEMBER, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/YzUsercodeIsbinding.act"})
  public String YzUsercodeIsbinding(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      
      Map<String, String> map = new HashMap();
      map.put("usercode", usercode);
      map.put("isbinding", ConstUtil.MEMBER_ISBINDING_0);
      List<KqdsMember> en = (List)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
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
  
  @RequestMapping({"/insertOrUpdate.act"})
  public synchronized String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      JSONObject czfs = new JSONObject();
      
      KqdsMember dp = new KqdsMember();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String askperson = request.getParameter("askperson");
      String regsort = request.getParameter("regsort");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        String type = request.getParameter("type");
        if (!YZUtility.isNullorEmpty(type))
        {
          String moneys = request.getParameter("moneys");
          String types = request.getParameter("types");
          String content = request.getParameter("content");
          if (type.equals("chongzhi"))
          {
            KqdsMember member = (KqdsMember)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
            if (YZUtility.isNullorEmpty(member.getIcno())) {
              throw new Exception("该患者存在会员卡未绑定IC卡，请先绑定");
            }
            if (!YZUtility.isNullorEmpty(content)) {
              dp.setRemark(content);
            }
            czfs = this.logic.saveChongzhi(moneys, types, askperson, regsort, seqId, dp, person, czfs, "充值", "", request);
            

            BcjlUtil.LogBcjlWithUserCode(BcjlUtil.RECHARGE, BcjlUtil.KQDS_MEMBER, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER, request);
          }
          else if (type.equals("tuifei"))
          {
            String tfremark = request.getParameter("tfremark");
            czfs = this.logic.saveChongzhi(moneys, types, askperson, regsort, seqId, dp, person, czfs, "退费", tfremark, request);
            
            BcjlUtil.LogBcjlWithUserCode(BcjlUtil.REFUND, BcjlUtil.KQDS_MEMBER, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER, request);
          }
        }
        else
        {
          this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
          

          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEMBER, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER, request);
          

          Map<String, String> map = new HashMap();
          map.put("usercode", dp.getUsercode());
          List<KqdsUserdocument> user = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
          
          KqdsMemberRecord r = new KqdsMemberRecord();
          r.setSeqId(YZUtility.getUUID());
          r.setUsercode(dp.getUsercode());
          r.setUsername(((KqdsUserdocument)user.get(0)).getUsername());
          r.setCardno(dp.getMemberno());
          r.setCreatetime(YZUtility.getCurDateTimeStr());
          r.setCreateuser(person.getSeqId());
          r.setType(ConstUtil.MEMBER_RECORD_TYPE_MODIFY);
          r.setContent(ConstUtil.MEMBER_RECORD_TYPE_MODIFY_USERINFO);
          r.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
        }
      }
      else
      {
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        String password = dp.getPassword();
        if (YZUtility.isNullorEmpty(password)) {
          dp.setPassword(dp.getMemberno().substring(dp.getMemberno().length() - 6, dp.getMemberno().length()));
        }
        if (YZUtility.isNullorEmpty(dp.getIcno())) {
          throw new Exception("无绑定的IC卡");
        }
        Map<String, String> map = new HashMap();
        map.put("icno", dp.getIcno());
        List<KqdsMember> iclist = (List)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
        if ((iclist != null) && (iclist.size() > 0)) {
          throw new Exception("该IC卡已绑定过其他会员卡");
        }
        dp.setIsbinding(Integer.valueOf(1));
        String username = request.getParameter("usercodeDesc");
        dp.setUsername(username);
        String moneys = request.getParameter("moneys");
        String types = request.getParameter("types");
        czfs = this.logic.saveChongzhi(moneys, types, askperson, regsort, "", dp, person, czfs, "开卡", "", request);
        
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MEMBER, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER, request);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", czfs);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/tksqMember.act"})
  public String tksqMember(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      KqdsMember dp = new KqdsMember();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String askperson = request.getParameter("askperson");
      String regsort = request.getParameter("regsort");
      String moneys = request.getParameter("moneys");
      String types = request.getParameter("types");
      
      String tfremark = request.getParameter("tfremark");
      BigDecimal xjmoney = BigDecimal.ZERO;
      BigDecimal yhkmoney = BigDecimal.ZERO;
      BigDecimal qtmoney = BigDecimal.ZERO;
      BigDecimal zfbmoney = BigDecimal.ZERO;
      BigDecimal wxmoney = BigDecimal.ZERO;
      BigDecimal mmdmoney = BigDecimal.ZERO;
      BigDecimal bdfqmoney = BigDecimal.ZERO;
      BigDecimal totalmoney = BigDecimal.ZERO;
      
      String[] typesstrs = types.split(",");
      if (types.indexOf(",") != -1)
      {
        String[] moneyss = moneys.split(",");
        for (int i = 0; i < typesstrs.length; i++) {
          if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("xjmoney")))
          {
            xjmoney = new BigDecimal(moneyss[i]);
            totalmoney = KqdsBigDecimal.add(totalmoney, xjmoney);
          }
          else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("yhkmoney")))
          {
            yhkmoney = new BigDecimal(moneyss[i]);
            totalmoney = KqdsBigDecimal.add(totalmoney, yhkmoney);
          }
          else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("zfbmoney")))
          {
            zfbmoney = new BigDecimal(moneyss[i]);
            totalmoney = KqdsBigDecimal.add(totalmoney, zfbmoney);
          }
          else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("wxmoney")))
          {
            wxmoney = new BigDecimal(moneyss[i]);
            totalmoney = KqdsBigDecimal.add(totalmoney, wxmoney);
          }
          else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("mmdmoney")))
          {
            mmdmoney = new BigDecimal(moneyss[i]);
            totalmoney = KqdsBigDecimal.add(totalmoney, mmdmoney);
          }
          else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("bdfqmoney")))
          {
            bdfqmoney = new BigDecimal(moneyss[i]);
            totalmoney = KqdsBigDecimal.add(totalmoney, bdfqmoney);
          }
          else if (typesstrs[i].equals(this.fkfsLogic.selectSeqId4Memberfield("qtmoney")))
          {
            qtmoney = new BigDecimal(moneyss[i]);
            totalmoney = KqdsBigDecimal.add(totalmoney, qtmoney);
          }
        }
      }
      else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("xjmoney")))
      {
        xjmoney = new BigDecimal(moneys);
        totalmoney = KqdsBigDecimal.add(totalmoney, xjmoney);
      }
      else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("yhkmoney")))
      {
        yhkmoney = new BigDecimal(moneys);
        totalmoney = KqdsBigDecimal.add(totalmoney, yhkmoney);
      }
      else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("zfbmoney")))
      {
        zfbmoney = new BigDecimal(moneys);
        totalmoney = KqdsBigDecimal.add(totalmoney, zfbmoney);
      }
      else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("wxmoney")))
      {
        wxmoney = new BigDecimal(moneys);
        totalmoney = KqdsBigDecimal.add(totalmoney, wxmoney);
      }
      else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("mmdmoney")))
      {
        mmdmoney = new BigDecimal(moneys);
        totalmoney = KqdsBigDecimal.add(totalmoney, mmdmoney);
      }
      else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("bdfqmoney")))
      {
        bdfqmoney = new BigDecimal(moneys);
        totalmoney = KqdsBigDecimal.add(totalmoney, bdfqmoney);
      }
      else if (types.equals(this.fkfsLogic.selectSeqId4Memberfield("qtmoney")))
      {
        qtmoney = new BigDecimal(moneys);
        totalmoney = KqdsBigDecimal.add(totalmoney, qtmoney);
      }
      KqdsMember en = new KqdsMember();
      if (!YZUtility.isNullorEmpty(seqId)) {
        en = (KqdsMember)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
      }
      Map<String, String> map = new HashMap();
      map.put("usercode", en.getUsercode());
      List<KqdsUserdocument> user = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
      
      KqdsMemberRecordSh r = new KqdsMemberRecordSh();
      r.setSeqId(YZUtility.getUUID());
      r.setUsercode(((KqdsUserdocument)user.get(0)).getUsercode());
      r.setUsername(((KqdsUserdocument)user.get(0)).getUsername());
      r.setCardno(en.getMemberno());
      String ctime = YZUtility.getCurDateTimeStr();
      r.setCreatetime(ctime);
      r.setCreateuser(person.getSeqId());
      
      r.setSqstatus(Integer.valueOf(1));
      r.setTfremark(tfremark);
      
      r.setAskperson(askperson);
      r.setRegsort(regsort);
      r.setXjmoney(xjmoney);
      r.setYhkmoney(yhkmoney);
      r.setQtmoney(qtmoney);
      r.setZfbmoney(zfbmoney);
      r.setWxmoney(wxmoney);
      r.setMmdmoney(mmdmoney);
      r.setBdfqmoney(bdfqmoney);
      r.setCmoney(totalmoney);
      r.setCgivemoney(dp.getGivemoney());
      r.setCtotal(KqdsBigDecimal.add(totalmoney, dp.getGivemoney()));
      r.setYmoney(en.getMoney());
      r.setYgivemoney(en.getGivemoney());
      r.setYtotal(KqdsBigDecimal.add(en.getMoney(), en.getGivemoney()));
      r.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, r);
      

      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.APPLY, BcjlUtil.KQDS_MEMBER_RECORD_SH, r, r.getUsercode(), TableNameUtil.KQDS_MEMBER_RECORD_SH, request);
      

      List<JSONObject> personlist = this.personLogic.getAllShowZjlPerson(ChainUtil.getCurrentOrganization(request), request);
      for (int i = 0; i < personlist.size(); i++) {
        PushUtil.saveTx4MemberRefund((JSONObject)personlist.get(i), person, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/editMember.act"})
  public String editMember(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      String memberno = request.getParameter("memberno");
      String memberlevel = request.getParameter("memberlevel");
      String discount = request.getParameter("discount");
      String discountdate = request.getParameter("discountdate");
      String remark = request.getParameter("remark");
      KqdsMember en = (KqdsMember)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
      if (en == null) {
        throw new Exception("数据不存在");
      }
      List<JSONObject> list2 = this.logic.getMemberListByMember(memberno, seqId);
      if (list2.size() > 0) {
        throw new Exception("该卡号已被使用");
      }
      this.logic.setCardno(memberno, en.getMemberno());
      en.setMemberlevel(memberlevel);
      en.setRemark(remark);
      en.setDiscount(Integer.valueOf(Integer.parseInt(discount)));
      en.setDiscountdate(discountdate);
      en.setMemberno(memberno);
      en.setIcno(memberno);
      this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);
      
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
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      
      KqdsMember en = (KqdsMember)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
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
  
  @RequestMapping({"/getMemberCardListByUsercode.act"})
  public String getMemberCardListByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      
      Map<String, String> map = new HashMap();
      map.put("usercode", usercode);
      map.put("memberstatus", ConstUtil.MEMBER_STATUS_1);
      List<KqdsMember> en = (List)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
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
  
  @RequestMapping({"/selectDetailByMemberno.act"})
  public String selectDetailByMemberno(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String memberno = request.getParameter("memberno");
      
      Map<String, String> map = new HashMap();
      map.put("memberno", memberno);
      List<KqdsMember> en = (List)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
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
  
  @RequestMapping({"/getSymoneyByUsercode.act"})
  public String getSymoneyByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      String sftime = request.getParameter("sftime");
      JSONObject jobj = new JSONObject();
      JSONObject FKFS = this.logic.getSymoneyByUsercode(usercode, sftime);
      jobj.put("member", FKFS);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/guashi.act"})
  public String guashi(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      
      KqdsMember en = (KqdsMember)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
      
      en.setMemberstatus(ConstUtil.MEMBER_STATUS_2);
      this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);
      

      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.LOSS, BcjlUtil.KQDS_MEMBER, en, en.getUsercode(), TableNameUtil.KQDS_MEMBER, request);
      

      YZPerson person = SessionUtil.getLoginPerson(request);
      Map<String, String> map = new HashMap();
      map.put("usercode", en.getUsercode());
      List<KqdsUserdocument> user = (List)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
      
      KqdsMemberRecord r = new KqdsMemberRecord();
      r.setSeqId(YZUtility.getUUID());
      r.setUsercode(en.getUsercode());
      r.setUsername(((KqdsUserdocument)user.get(0)).getUsername());
      r.setCardno(en.getMemberno());
      r.setCreatetime(YZUtility.getCurDateTimeStr());
      r.setCreateuser(person.getSeqId());
      r.setType(ConstUtil.MEMBER_RECORD_TYPE_GS);
      r.setContent("");
      r.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
      
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
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
    String sortName = request.getParameter("sortName");
    String sortOrder = request.getParameter("sortOrder");
    try
    {
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap();
      String queryInput = request.getParameter("queryInput");
      if (!YZUtility.isNullorEmpty(queryInput)) {
        map.put("queryInput", queryInput);
      }
      String createtime = request.getParameter("createtime");
      if (!YZUtility.isNullorEmpty(createtime)) {
        map.put("createtime", createtime + ConstUtil.TIME_START);
      }
      String starttime = request.getParameter("starttime");
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      String endtime = request.getParameter("endtime");
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      String memberno = request.getParameter("memberno");
      if (!YZUtility.isNullorEmpty(memberno)) {
        map.put("memberno", memberno);
      }
      String usercode = request.getParameter("usercode");
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      String username = request.getParameter("username");
      if (!YZUtility.isNullorEmpty(username)) {
        map.put("username", username);
      }
      String type = request.getParameter("type");
      if (!YZUtility.isNullorEmpty(type)) {
        map.put("type", type);
      }
      String memberlevel = request.getParameter("memberlevel");
      if (!YZUtility.isNullorEmpty(memberlevel)) {
        map.put("memberlevel", memberlevel);
      }
      if (map.isEmpty())
      {
        map.put("starttime", YZUtility.getDateStr(new Date()) + ConstUtil.TIME_START);
        map.put("endtime", YZUtility.getDateStr(new Date()) + ConstUtil.TIME_END);
      }
      String pagenum = request.getParameter("pagenum");
      if (!YZUtility.isNullorEmpty(pagenum)) {
        map.put("pagenum", pagenum);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      

      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject json = new JSONObject();
      if ((flag != null) && (flag.equals("exportTable")))
      {
        json = this.logic.selectList(TableNameUtil.KQDS_MEMBER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), json, bp);
        if (json != null)
        {
          ExportBean bean = ExportTable.initExcel4RsWrite("会员中心", fieldArr, fieldnameArr, response, request);
          bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List)json.get("rows"), "member_center");
          ExportTable.writeExcel4DownLoad("会员中心", bean.getWorkbook(), response);
        }
        return null;
      }
      if (sortName != null)
      {
        map.put("sortName", sortName);
        map.put("sortOrder", sortOrder);
        json = this.logic.selectList(TableNameUtil.KQDS_MEMBER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), json, bp);
      }
      else
      {
        json = this.logic.selectList(TableNameUtil.KQDS_MEMBER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), json, bp);
      }
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkMemberno.act"})
  public String checkMemberno(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String memberno = request.getParameter("memberno");
      
      Map<String, String> map = new HashMap();
      map.put("memberno", memberno);
      List<KqdsMember> en = (List)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
      JSONObject jobj = new JSONObject();
      jobj.put("data", Integer.valueOf(en.size()));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/checkIsMemberByUsercode.act"})
  public String checkIsMemberByUsercode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String usercode = request.getParameter("usercode");
      int num = this.logic.checkIsMemberByUsercode(usercode);
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("retMsrg", "操作成功");
      if (num > 0) {
        jobj.put("data", Integer.valueOf(1));
      } else {
        jobj.put("data", Integer.valueOf(0));
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectMemberTongji.act"})
  public String selectMemberTongji(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      
      String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
      String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
      String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
      Map<String, String> map = new HashMap();
      String queryInput = request.getParameter("queryInput");
      if (!YZUtility.isNullorEmpty(queryInput)) {
        map.put("queryInput", queryInput);
      }
      String starttime = request.getParameter("starttime");
      if (!YZUtility.isNullorEmpty(starttime)) {
        map.put("starttime", starttime + ConstUtil.TIME_START);
      }
      String endtime = request.getParameter("endtime");
      if (!YZUtility.isNullorEmpty(endtime)) {
        map.put("endtime", endtime + ConstUtil.TIME_END);
      }
      String askperson = request.getParameter("askperson");
      if (!YZUtility.isNullorEmpty(askperson)) {
        map.put("askperson", askperson);
      }
      JSONObject result = this.logic.selectMemberTongji(TableNameUtil.KQDS_MEMBER, bp, map, flag, person);
      if ((flag != null) && (flag.equals("exportTable")))
      {
        ExportTable.exportBootStrapTable2Excel("会员卡查询列表", fieldArr, fieldnameArr, (List)result.get("list"), response, request);
        return null;
      }
      YZUtility.DEAL_SUCCESS(result, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/zhuanzeng.act"})
  public String zhuanzeng(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String usercode = request.getParameter("usercode");
      String username = request.getParameter("username");
      String usercode1 = request.getParameter("usercode1");
      String username1 = request.getParameter("username1");
      String memberno = request.getParameter("memberno");
      String memberno1 = request.getParameter("memberno1");
      String zzremark = request.getParameter("zzremark");
      String money1 = request.getParameter("money1");
      String money2 = request.getParameter("money2");
      BigDecimal money11 = new BigDecimal(money1);
      BigDecimal money22 = new BigDecimal(money2);
      


      BigDecimal zzmoney = BigDecimal.ZERO;
      BigDecimal zzgivemoney = BigDecimal.ZERO;
      Map<String, String> map = new HashMap();
      map.put("memberno", memberno);
      List<KqdsMember> en = (List)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
      KqdsMember member = (KqdsMember)en.get(0);
      zzmoney = KqdsBigDecimal.sub(member.getMoney(), money11);
      zzgivemoney = KqdsBigDecimal.sub(member.getGivemoney(), money22);
      
      KqdsMemberRecord r = new KqdsMemberRecord();
      String uuid = YZUtility.getUUID();
      r.setSeqId(uuid);
      r.setUsercode(usercode);
      r.setUsername(username);
      r.setSzrusercode(usercode1);
      r.setSzrusername(username1);
      r.setSzcardno(memberno1);
      r.setCardno(memberno);
      r.setCreatetime(YZUtility.getCurDateTimeStr());
      r.setCreateuser(person.getSeqId());
      r.setTfremark(zzremark);
      r.setYmoney(member.getMoney());
      r.setYgivemoney(member.getGivemoney());
      BigDecimal ytotal = KqdsBigDecimal.add(member.getMoney(), member.getGivemoney());
      r.setYtotal(ytotal);
      r.setType(ConstUtil.MEMBER_RECORD_TYPE_ZZ);
      if (KqdsBigDecimal.compareTo(money11, BigDecimal.ZERO) > 0) {
        r.setZzmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, money11));
      } else {
        r.setZzmoney(money11);
      }
      if (KqdsBigDecimal.compareTo(money22, BigDecimal.ZERO) > 0) {
        r.setZzgivemoney(KqdsBigDecimal.sub(BigDecimal.ZERO, money22));
      } else {
        r.setZzgivemoney(money22);
      }
      r.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
      
      member.setMoney(zzmoney);
      member.setGivemoney(zzgivemoney);
      this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, member);
      
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DONATION, BcjlUtil.KQDS_MEMBER, member, member.getUsercode(), TableNameUtil.KQDS_MEMBER, request);
      


      BigDecimal szmoney = BigDecimal.ZERO;
      BigDecimal szgivemoney = BigDecimal.ZERO;
      map.put("memberno", memberno1);
      List<KqdsMember> en1 = (List)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
      KqdsMember member1 = (KqdsMember)en1.get(0);
      szmoney = KqdsBigDecimal.add(member1.getMoney(), money11);
      szgivemoney = KqdsBigDecimal.add(member1.getGivemoney(), money22);
      
      KqdsMemberRecord r1 = new KqdsMemberRecord();
      String uuid1 = YZUtility.getUUID();
      r1.setSeqId(uuid1);
      r1.setUsercode(usercode);
      r1.setUsername(username);
      r1.setSzrusercode(usercode1);
      r1.setSzrusername(username1);
      r1.setCardno(memberno1);
      r1.setSzcardno(memberno);
      r1.setCreatetime(YZUtility.getCurDateTimeStr());
      r1.setCreateuser(person.getSeqId());
      r1.setTfremark(zzremark);
      r1.setType(ConstUtil.MEMBER_RECORD_TYPE_SZ);
      r1.setZzmoney(money11);
      r1.setZzgivemoney(money22);
      r1.setYmoney(member1.getMoney());
      r1.setYgivemoney(member1.getGivemoney());
      BigDecimal ytotal1 = KqdsBigDecimal.add(member1.getMoney(), member1.getGivemoney());
      r1.setYtotal(ytotal1);
      r1.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r1);
      
      member1.setMoney(szmoney);
      member1.setGivemoney(szgivemoney);
      this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, member1);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
}
