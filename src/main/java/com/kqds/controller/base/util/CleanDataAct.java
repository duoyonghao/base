package com.kqds.controller.base.util;

import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.makeInvoice.KQDS_makeInvoiceLogic;
import com.kqds.service.base.outProcessingSheet.KQDS_OutProcessingSheetLogic;
import com.kqds.service.base.receiveinfo.KQDS_ReceiveInfoLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"CleanDataAct"})
public class CleanDataAct {
  private static Logger logger = LoggerFactory.getLogger(CleanDataAct.class);
  
  @Autowired
  private KQDS_UserDocumentLogic logic;
  
  @Autowired
  private KQDS_makeInvoiceLogic Mlogic;
  
  @Autowired
  private KQDS_OutProcessingSheetLogic outLogic;
  
  @Autowired
  private KQDS_ReceiveInfoLogic recLogic;
  
  @Autowired
  private KQDS_REGLogic regLogic;
  
  @RequestMapping({"/tmpDeleteUers.act"})
  public String tmpDeleteUers(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String usercodes = request.getParameter("usercodes");
      if (YZUtility.isNullorEmpty(usercodes))
        throw new Exception("没有选择任何患者，无法执行伪删除操作。"); 
      usercodes = YZUtility.ConvertStringIds4Query(usercodes);
      StringBuffer sql = new StringBuffer();
      sql.append(" update KQDS_UserDocument ");
      sql.append(" set isdelete = 1 where 1=1 ");
      sql.append(" and usercode in (" + usercodes + ") ");
      int count = this.logic.deleteuser(usercodes);
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_USERDOCUMENT, sql.toString(), TableNameUtil.KQDS_USERDOCUMENT, request);
      String logText = "系统用户" + person.getUserId() + "伪删除：" + sql.toString() + "，成功删除：" + count + "条记录。";
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_USERDOCUMENT, logText, TableNameUtil.KQDS_USERDOCUMENT, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteUserAllData.act"})
  public String deleteUserAllData(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String usercodes = request.getParameter("usercodes");
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      deleteUserAllDataByUserCode(usercodes, request);
      StringBuffer logText = new StringBuffer();
      logText.append("用户").append(person.getUserId()).append("对编号为：").append(usercodes).append("的患者进行了所有业务数据删除操作！");
      BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_USERDOCUMENT, logText, TableNameUtil.KQDS_USERDOCUMENT, request);
      JSONObject retrunObj = new JSONObject();
      YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
    } 
    return null;
  }
  
  private void deleteUserAllDataByUserCode(String usercodes, HttpServletRequest request) throws Exception {
    List<String> usercodeList = YZUtility.ConvertString2List(usercodes);
    StringBuffer cannotDeleteBf = new StringBuffer();
    for (String usercode : usercodeList) {
      int tmpCount = this.logic.checkuser(usercode);
      if (tmpCount > 0)
        cannotDeleteBf.append(usercode).append(","); 
    } 
    if (cannotDeleteBf.length() > 0) {
      String codeStr = cannotDeleteBf.toString();
      if (codeStr.endsWith(","))
        codeStr = codeStr.substring(0, codeStr.length() - 1); 
      throw new Exception("存在转赠/受赠操作的患者无法删除，患者编号为：" + codeStr);
    } 
    List<String> tableNameList = new ArrayList<>();
    tableNameList.add(TableNameUtil.KQDS_CHANGE_DOCTOR);
    tableNameList.add(TableNameUtil.KQDS_CHANGE_KEFU);
    tableNameList.add(TableNameUtil.KQDS_CHANGE_RECEIVE);
    tableNameList.add(TableNameUtil.KQDS_CHANGE_WD);
    tableNameList.add(TableNameUtil.KQDS_CHUFANG);
    tableNameList.add(TableNameUtil.KQDS_CHUFANG_DETAIL);
    tableNameList.add(TableNameUtil.KQDS_COSTORDER);
    tableNameList.add(TableNameUtil.KQDS_COSTORDER_DETAIL);
    tableNameList.add(TableNameUtil.KQDS_COSTORDER_TUIDAN);
    tableNameList.add(TableNameUtil.KQDS_COSTORDER_DETAIL_TUIDAN);
    tableNameList.add(TableNameUtil.KQDS_EXTENSION);
    tableNameList.add(TableNameUtil.KQDS_GIVEITEM_GIVERECORD);
    tableNameList.add(TableNameUtil.KQDS_GIVEITEM_USERECORD);
    tableNameList.add(TableNameUtil.KQDS_HOSPITAL_ORDER);
    tableNameList.add(TableNameUtil.KQDS_IMAGE_DATA);
    tableNameList.add(TableNameUtil.KQDS_LLTJ);
    tableNameList.add(TableNameUtil.KQDS_LLTJ_DETAIL);
    tableNameList.add(TableNameUtil.KQDS_MEDICALRECORD);
    tableNameList.add(TableNameUtil.KQDS_MEDICALRECORD_CZ);
    tableNameList.add(TableNameUtil.KQDS_MEDICALRECORD_FZ);
    tableNameList.add(TableNameUtil.KQDS_MEMBER);
    tableNameList.add(TableNameUtil.KQDS_MEMBER_RECORD);
    tableNameList.add(TableNameUtil.KQDS_MEMBER_RECORD_SH);
    tableNameList.add(TableNameUtil.KQDS_NET_ORDER);
    tableNameList.add(TableNameUtil.KQDS_NET_ORDER_RECORD);
    tableNameList.add(TableNameUtil.KQDS_OUTPROCESSING_SHEET);
    tableNameList.add(TableNameUtil.KQDS_PAYCOST);
    tableNameList.add(TableNameUtil.KQDS_RECEIVEINFO);
    tableNameList.add(TableNameUtil.KQDS_REFUND);
    tableNameList.add(TableNameUtil.KQDS_REFUND_DETAIL);
    tableNameList.add(TableNameUtil.KQDS_REG);
    tableNameList.add(TableNameUtil.KQDS_ROOM);
    tableNameList.add(TableNameUtil.KQDS_SMS);
    tableNameList.add(TableNameUtil.KQDS_TOOTH_DOC);
    tableNameList.add(TableNameUtil.KQDS_USERDOCUMENT);
    tableNameList.add(TableNameUtil.KQDS_VISIT);
    String userCodes4Del = YZUtility.ConvertStringIds4Query(usercodes);
    deleteNoUserCodeTableRecord(userCodes4Del, request);
    for (String tableName : tableNameList)
      this.Mlogic.deleteUserDoc(userCodes4Del, tableName); 
  }
  
  private void deleteNoUserCodeTableRecord(String userCodes4Del, HttpServletRequest request) throws Exception {
    List<JSONObject> tmpList1 = this.outLogic.getNoUsercode(userCodes4Del);
    for (JSONObject jsonObject : tmpList1) {
      String seqId = jsonObject.getString("seq_id");
      this.outLogic.deleteBySheetno(seqId);
    } 
    List<JSONObject> tmpList2 = this.recLogic.getNoUsercode(userCodes4Del);
    for (JSONObject jsonObject : tmpList2) {
      String seqId = jsonObject.getString("seq_id");
      this.recLogic.deleteByrecno(seqId);
    } 
    List<JSONObject> tmpList3 = this.regLogic.getNoUsercode(userCodes4Del);
    for (JSONObject jsonObject : tmpList3) {
      String seqId = jsonObject.getString("seq_id");
      this.regLogic.deleteByRegno(seqId);
    } 
  }
}
