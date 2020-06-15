package com.kqds.timer.task;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsSms;
import com.kqds.entity.base.KqdsSmsModel;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZDict;
import com.kqds.service.base.hospitalOrder.KQDS_Hospital_OrderLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.netOrder.KQDS_Net_OrderLogic;
import com.kqds.service.base.sms.KQDS_Sms_ModelLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sms.SmsSender;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.SysParaUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckSmsTxTask implements Job {
  @Autowired
  private KQDS_Net_OrderLogic netlogic;
  
  @Autowired
  private KQDS_Hospital_OrderLogic hoslogic;
  
  @Autowired
  private KQDS_Sms_ModelLogic smsmodellogic;
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  
  @Autowired
  private YZParaLogic paraLogic;
  
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    try {
      doTask();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void doTask() throws Exception {
    String yyhouse = this.paraLogic.getParaValueByName(SysParaUtil.SMS_YYTX_BEFORE);
    String srhouse = this.paraLogic.getParaValueByName(SysParaUtil.SMS_BIRTH_BEFORE);
    String organization = YZSysProps.getProp(SysParaUtil.ORGANIZATION);
    Calendar cale = null;
    cale = Calendar.getInstance();
    int hour = cale.get(11);
    if (Integer.parseInt(yyhouse) == hour) {
      KqdsSmsModel smsmodel = getsmscontent(organization, "DXFL_YY");
      yytxWd(organization, smsmodel);
      yytxMz(organization, smsmodel);
    } 
    if (Integer.parseInt(srhouse) == hour) {
      KqdsSmsModel smsmodel2 = getsmscontent(organization, "DXFL_SR");
      srtx(organization, smsmodel2);
    } 
  }
  
  public KqdsSmsModel getsmscontent(String organization, String parentcode) throws Exception {
    List<YZDict> dictlist = this.dictLogic.getListByParentCode(parentcode, organization);
    KqdsSmsModel smsmodel = new KqdsSmsModel();
    if (dictlist != null && dictlist.size() > 0) {
      List<JSONObject> smslist = this.smsmodellogic.getDsmodel(((YZDict)dictlist.get(0)).getSeqId());
      if (smslist != null && smslist.size() > 0)
        smsmodel = (KqdsSmsModel)JSONObject.toBean(smslist.get(0), KqdsSmsModel.class); 
    } 
    return smsmodel;
  }
  
  public void yytxWd(String organization, KqdsSmsModel smsmodel) throws Exception {
    Map<String, String> map = new HashMap<>();
    String date = YZUtility.getDateStr(YZUtility.getDayAfter(new Date(), 1));
    map.put("starttime", String.valueOf(date) + ConstUtil.HOUR_START);
    map.put("endtime", String.valueOf(date) + ConstUtil.HOUR_END);
    JSONObject json = this.netlogic.selectNoPageYyzx(TableNameUtil.KQDS_NET_ORDER, map, null, organization, null, null);
    List<JSONObject> list = (List<JSONObject>)json.get("rows");
    if (list != null && list.size() > 0)
      for (JSONObject jobj : list) {
        KqdsNetOrder netorder = (KqdsNetOrder)JSONObject.toBean(jobj, KqdsNetOrder.class);
        KqdsUserdocument user = this.userLogic.getSingleUserByUsercode(netorder.getUsercode());
        String smscontent = "";
        String sex = "女士";
        if (user.getSex().equals("男"))
          sex = "先生"; 
        smscontent = String.valueOf(user.getUsername()) + sex + "您好，您于" + netorder.getOrdertime() + "有预约，请及时就诊！";
        if (!YZUtility.isNullorEmpty(smsmodel.getSmscontent()))
          try {
            smscontent = smsmodel.getSmscontent().replaceAll("【患者姓名】", String.valueOf(user.getUsername()) + sex);
            smscontent = smscontent.replaceAll("【预约时间】", netorder.getOrdertime());
          } catch (Exception e) {
            smscontent = String.valueOf(user.getUsername()) + sex + "您好，您于" + netorder.getOrdertime() + "有预约，请及时就诊！";
          }  
        sendSms(organization, user, smsmodel, smscontent);
      }  
  }
  
  public void yytxMz(String organization, KqdsSmsModel smsmodel) throws Exception {
    Map<String, String> map = new HashMap<>();
    String date = YZUtility.getDateStr(YZUtility.getDayAfter(new Date(), 1));
    map.put("starttime", String.valueOf(date) + ConstUtil.HOUR_START);
    map.put("endtime", String.valueOf(date) + ConstUtil.HOUR_END);
    if (YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    JSONObject json = this.hoslogic.selectNoPage(TableNameUtil.KQDS_HOSPITAL_ORDER, map, null, null, null);
    List<JSONObject> list = (List<JSONObject>)json.get("rows");
    if (list != null && list.size() > 0)
      for (JSONObject jobj : list) {
        KqdsHospitalOrder netorder = (KqdsHospitalOrder)JSONObject.toBean(jobj, KqdsHospitalOrder.class);
        KqdsUserdocument user = this.userLogic.getSingleUserByUsercode(netorder.getUsercode());
        String smscontent = "";
        String sex = "女士";
        if (user.getSex().equals("男"))
          sex = "先生"; 
        smscontent = String.valueOf(user.getUsername()) + sex + "您好，您于" + netorder.getOrdertime() + "有预约，请及时就诊！";
        if (!YZUtility.isNullorEmpty(smsmodel.getSmscontent()))
          try {
            smscontent = smsmodel.getSmscontent().replaceAll("【患者姓名】", String.valueOf(user.getUsername()) + sex);
            smscontent = smscontent.replaceAll("【预约时间】", netorder.getOrdertime());
          } catch (Exception e) {
            smscontent = String.valueOf(user.getUsername()) + sex + "您好，您于" + netorder.getOrdertime() + "有预约，请及时就诊！";
          }  
        sendSms(organization, user, smsmodel, smscontent);
      }  
  }
  
  public void srtx(String organization, KqdsSmsModel smsmodel) throws Exception {
    Calendar cale = null;
    cale = Calendar.getInstance();
    int month = cale.get(2) + 1;
    int day = cale.get(5);
    List<JSONObject> list = this.userLogic.selectListByBirth(month, day, organization);
    if (list != null && list.size() > 0)
      for (JSONObject jobj : list) {
        KqdsUserdocument user = (KqdsUserdocument)JSONObject.toBean(jobj, KqdsUserdocument.class);
        String smscontent = "";
        String sex = "女士";
        if (user.getSex().equals("男"))
          sex = "先生"; 
        smscontent = String.valueOf(user.getUsername()) + sex + "，祝您生日快乐！";
        if (!YZUtility.isNullorEmpty(smsmodel.getSmscontent()))
          try {
            smscontent = smsmodel.getSmscontent().replaceAll("【患者姓名】", String.valueOf(user.getUsername()) + sex);
          } catch (Exception e) {
            smscontent = String.valueOf(user.getUsername()) + sex + "，祝您生日快乐！";
          }  
        sendSms(organization, user, smsmodel, smscontent);
      }  
  }
  
  public void sendSms(String organization, KqdsUserdocument user, KqdsSmsModel smsmodel, String smscontent) throws Exception {
    KqdsSms dp = new KqdsSms();
    String uuid = YZUtility.getUUID();
    dp.setSeqId(uuid);
    dp.setUsercode(user.getUsercode());
    dp.setSmscontent(smscontent);
    dp.setSmstype(smsmodel.getSmstype());
    dp.setSmsstate(Integer.valueOf(0));
    dp.setSendstate(Integer.valueOf(1));
    dp.setSendphone(user.getPhonenumber1());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    dp.setSmsnexttype(smsmodel.getSmsnexttype());
    dp.setOrganization(organization);
    String returnString = SmsSender.getSenderTx(dp.getSendphone(), dp.getSmscontent());
    dp.setSendstate(Integer.valueOf(Integer.parseInt(returnString)));
    dp.setSendtime(YZUtility.getCurDateTimeStr());
    this.smsmodellogic.saveSingleUUID(TableNameUtil.KQDS_SMS, dp);
  }
}
