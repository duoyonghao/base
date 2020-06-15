package com.kqds.controller.base.memberRecord;

import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsMemberRecord;
import com.kqds.entity.base.KqdsMemberRecordSh;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.memberRecord.KQDS_Member_Record_ShLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
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

@Controller
@RequestMapping({"KQDS_Member_Record_ShAct"})
public class KQDS_Member_Record_ShAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Member_Record_ShAct.class);
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private KQDS_Member_Record_ShLogic logic;
  
  @RequestMapping({"/selectListByType.act"})
  public String selectListByType(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      String cardno = request.getParameter("cardno");
      String queryInput = request.getParameter("cardqueryInputno");
      String queryinput = request.getParameter("queryinput");
      String starttime = request.getParameter("starttime");
      String endtime = request.getParameter("endtime");
      String sqstatus = request.getParameter("sqstatus");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(cardno))
        map.put("cardno", cardno); 
      if (!YZUtility.isNullorEmpty(queryInput))
        map.put("memberno", queryInput); 
      if (!YZUtility.isNullorEmpty(queryinput))
        map.put("queryinput", queryinput); 
      if (!YZUtility.isNullorEmpty(starttime))
        map.put("starttime", String.valueOf(starttime) + ConstUtil.TIME_START); 
      if (!YZUtility.isNullorEmpty(endtime))
        map.put("endtime", String.valueOf(endtime) + ConstUtil.TIME_END); 
      if (!YZUtility.isNullorEmpty(sqstatus))
        map.put("sqstatus", sqstatus); 
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
        organization = ChainUtil.getCurrentOrganization(request); 
      map.put("organization", organization);
      List<JSONObject> list = this.logic.selectListByType(TableNameUtil.KQDS_MEMBER_RECORD_SH, map);
      if (flag != null && flag.equals("exportTable")) {
        for (JSONObject json : list) {
          int value = json.getInt("sqstatus");
          String html = "";
          if (ConstUtil.REFUND_STATUS_1 == value) {
            html = "申请中";
          } else if (ConstUtil.REFUND_STATUS_2 == value) {
            html = "已同意";
          } else if (ConstUtil.REFUND_STATUS_3 == value) {
            html = "未同意";
          } else if (ConstUtil.REFUND_STATUS_4 == value) {
            html = "已退款";
          } 
          json.put("sqstatus", html);
        } 
        ExportTable.exportBootStrapTable2Excel("预收退款", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/editState.act"})
  public String editState(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsMemberRecordSh dp = new KqdsMemberRecordSh();
      BeanUtils.populate(dp, request.getParameterMap());
      Map<String, String> map = new HashMap<>();
      map.put("seq_id", dp.getSeqId());
      KqdsMemberRecordSh en = (KqdsMemberRecordSh)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, dp.getSeqId());
      en.setSqstatus(dp.getSqstatus());
      en.setSqremark(dp.getSqremark());
      this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, en);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.AUDIT, BcjlUtil.KQDS_MEMBER_RECORD_SH, en, en.getUsercode(), TableNameUtil.KQDS_MEMBER_RECORD_SH, request);
      List<JSONObject> personlist = this.personLogic.getAllShowfeiPerson(ChainUtil.getCurrentOrganization(request), request);
      for (int i = 0; i < personlist.size(); i++)
        PushUtil.saveTx4MemberSH(personlist.get(i), person, request); 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/confirmRefund.act"})
  public synchronized String confirmRefund(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      JSONObject czfs = new JSONObject();
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键不能为空"); 
      KqdsMemberRecordSh dp = (KqdsMemberRecordSh)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, seqId);
      if (ConstUtil.MEMBER_SH_STATUS_2 != dp.getSqstatus().intValue()) {
        if (ConstUtil.MEMBER_SH_STATUS_4 == dp.getSqstatus().intValue())
          throw new Exception("该退费单已退过费,请选择同意申请的退费单"); 
        throw new Exception("请选择同意申请的退费单");
      } 
      String organization = ChainUtil.getCurrentOrganization(request);
      confirmRefundDeal(dp, person, organization, request);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.CONFIRM_REFUND, BcjlUtil.KQDS_MEMBER_RECORD_SH, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER_RECORD_SH, request);
      PushUtil.saveTx4MemberConfirm(dp, person, request);
      JSONObject jobj = new JSONObject();
      jobj.put("retData", czfs);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/Yztuikuan.act"})
  public String Yztuikuan(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      boolean flag = true;
      String memberno = request.getParameter("memberno");
      String tkgivemoneyStr = request.getParameter("tkgivemoney");
      String tkmoneyStr = request.getParameter("tkmoney");
      BigDecimal tkgivemoney = BigDecimal.ZERO;
      BigDecimal tkmoney = BigDecimal.ZERO;
      if (!YZUtility.isNullorEmpty(tkgivemoneyStr))
        tkgivemoney = new BigDecimal(tkgivemoneyStr); 
      if (!YZUtility.isNullorEmpty(tkmoneyStr))
        tkmoney = new BigDecimal(tkmoneyStr); 
      Map<String, String> map = new HashMap<>();
      map.put("memberno", memberno);
      List<KqdsMember> listMember = (List<KqdsMember>)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
      if (listMember == null)
        throw new Exception("不存在会员卡"); 
      KqdsMember en = listMember.get(0);
      Map<Object, Object> map2 = new HashMap<>();
      map2.put("cardno", memberno);
      map2.put("sqstatus", Integer.valueOf(ConstUtil.MEMBER_SH_STATUS_2));
      List<KqdsMemberRecordSh> listSq = (List<KqdsMemberRecordSh>)this.logic.loadList(TableNameUtil.KQDS_MEMBER_RECORD_SH, map2);
      if (listSq != null && listSq.size() > 0) {
        KqdsMemberRecordSh sq = listSq.get(0);
        tkgivemoney = KqdsBigDecimal.add(tkgivemoney, sq.getCgivemoney());
        tkmoney = KqdsBigDecimal.add(tkmoney, sq.getCmoney());
      } 
      BigDecimal yegivemoney = KqdsBigDecimal.add(tkgivemoney, en.getGivemoney());
      BigDecimal yemoney = KqdsBigDecimal.add(tkmoney, en.getMoney());
      if (KqdsBigDecimal.compareTo(yegivemoney, BigDecimal.ZERO) < 0)
        throw new Exception("赠送金额不足"); 
      if (KqdsBigDecimal.compareTo(yemoney, BigDecimal.ZERO) < 0)
        throw new Exception("充值金额不足"); 
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(flag));
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  private void confirmRefundDeal(KqdsMemberRecordSh dp, YZPerson person, String organization, HttpServletRequest request) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("memberno", dp.getCardno());
    List<KqdsMember> listMember = (List<KqdsMember>)this.logic.loadList(TableNameUtil.KQDS_MEMBER, map);
    if (listMember != null && listMember.size() < 1)
      throw new Exception("不存在会员卡"); 
    KqdsMember en = listMember.get(0);
    BigDecimal money = KqdsBigDecimal.add(en.getMoney(), dp.getCmoney());
    BigDecimal givemoney = KqdsBigDecimal.add(en.getGivemoney(), dp.getCgivemoney());
    if (KqdsBigDecimal.compareTo(givemoney, BigDecimal.ZERO) < 0 || KqdsBigDecimal.compareTo(money, BigDecimal.ZERO) < 0)
      throw new Exception("会员卡余额不足"); 
    en.setMoney(money);
    en.setGivemoney(givemoney);
    this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);
    KqdsMemberRecord r = new KqdsMemberRecord();
    String uuid = YZUtility.getUUID();
    r.setSeqId(uuid);
    r.setUsercode(dp.getUsercode());
    r.setUsername(dp.getUsername());
    r.setCardno(en.getMemberno());
    String ctime = YZUtility.getCurDateTimeStr();
    r.setCreatetime(ctime);
    r.setCreateuser(person.getSeqId());
    r.setType(ConstUtil.MEMBER_RECORD_TYPE_TF);
    r.setTfremark(dp.getTfremark());
    r.setAskperson(dp.getAskperson());
    r.setRegsort(dp.getRegsort());
    r.setXjmoney(dp.getXjmoney());
    r.setYhkmoney(dp.getYhkmoney());
    r.setQtmoney(dp.getQtmoney());
    r.setZfbmoney(dp.getZfbmoney());
    r.setWxmoney(dp.getWxmoney());
    r.setMmdmoney(dp.getMmdmoney());
    r.setBdfqmoney(dp.getBdfqmoney());
    r.setCmoney(dp.getCmoney());
    r.setCgivemoney(dp.getCgivemoney());
    r.setCtotal(dp.getCtotal());
    r.setYmoney(en.getMoney());
    r.setYgivemoney(en.getGivemoney());
    r.setYtotal(KqdsBigDecimal.add(en.getMoney(), en.getGivemoney()));
    r.setOrganization(organization);
    this.logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
    BcjlUtil.LogBcjl(r.getSeqId(), BcjlUtil.KQDS_MEMBER_RECORD, r, TableNameUtil.KQDS_MEMBER_RECORD, request);
    dp.setSqstatus(Integer.valueOf(4));
    dp.setYmoney(r.getYmoney());
    dp.setYgivemoney(r.getYgivemoney());
    dp.setYtotal(r.getYtotal());
    dp.setType(r.getCreatetime());
    this.logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, dp);
  }
}
