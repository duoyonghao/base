package com.hudh.sjtj.service.impl;

import com.hudh.dept.dao.SysDeptPrivDao;
import com.hudh.sjtj.dao.DataAnalysisDao;
import com.hudh.sjtj.service.IDataAnalysisService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.memberRecord.KQDS_Member_RecordLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.base.refund.KQDS_RefundLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.DictUtil;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataAnalysisServiceImpl implements IDataAnalysisService {
  @Autowired
  private DataAnalysisDao analysisDao;
  
  @Autowired
  private SysDeptPrivDao sysDeptPrivDao;
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @Autowired
  private Kqds_PayCostLogic kpayLogic;
  
  @Autowired
  private KQDS_RefundLogic kRefundLogic;
  
  @Autowired
  private KQDS_CostOrder_DetailLogic kDetailLogic;
  
  @Autowired
  private KQDS_REGLogic krLogic;
  
  @Autowired
  private YZPersonLogic yzPersonLogic;
  
  @Autowired
  private YZDeptLogic yzDeptLogic;
  
  @Autowired
  private KQDS_UserDocumentLogic kLogic;
  
  @Autowired
  private KQDS_Member_RecordLogic kRecordLogic;
  
  public List<JSONObject> findBaseDataAskperson(HttpServletRequest request, Map<String, String> dataMap) throws Exception {
    return this.analysisDao.findBaseDataAskperson();
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public List<JSONObject> findCJStatisticsByYear(HttpServletRequest request, Map<String, String> map) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String name = "";
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < personSeqIds.size(); j++) {
        if (j == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "',");
        } 
      } 
      map.put("visualstaff", str.toString());
      name = "所有部门";
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < yzpersonSeqids.size(); j++) {
        if (j == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "',");
        } 
      } 
      map.put("visualstaff", str.toString());
      String deptname = this.yzDeptLogic.getDeptNamesBySeqIds(map.get("deptCategory"));
      name = deptname;
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("personage")) {
      map.put("visualstaff", "'" + person.getSeqId() + "'");
      name = person.getUserName();
    } 
    String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
    map.put("recesort", "'" + czseqId + "'");
    JSONObject czAllNums = this.analysisDao.findQuantityByAskpersonAndYear(map);
    JSONObject czCjNum = this.analysisDao.findCJQuantityByAskpersonAndYear(map);
    List<JSONObject> czPaymoney = this.analysisDao.findPaymoneyByYear(map);
    String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
    map.put("recesort", "'" + fzseqId + "'");
    JSONObject fzAllNums = this.analysisDao.findQuantityByAskpersonAndYear(map);
    JSONObject fzCjNum = this.analysisDao.findCJQuantityByAskpersonAndYear(map);
    List<JSONObject> fzPaymoney = this.analysisDao.findPaymoneyByYear(map);
    String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
    String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
    map.put("recesort", "'" + zxfseqId + "','" + fcseqId + "'");
    JSONObject zxfAllNums = this.analysisDao.findQuantityByAskpersonAndYear(map);
    map.put("recesort", "'" + zxfseqId + "'");
    JSONObject zxfCjNum = this.analysisDao.findCJQuantityByAskpersonAndYear(map);
    List<JSONObject> zxfPaymoney = this.analysisDao.findPaymoneyByYear(map);
    List<JSONObject> Cmoney = this.analysisDao.findCmoneyByYear(map);
    List<JSONObject> Drugsmoney = this.analysisDao.findDrugsmoneyByYear(map);
    String startyear = map.get("startyear");
    String endyear = map.get("endyear");
    int paymoneyMark1 = 0;
    int paymoneyMark2 = 0;
    int paymoneyMark3 = 0;
    int cmoneyMark4 = 0;
    int drugsmoneyMark5 = 0;
    List<JSONObject> list = new ArrayList<>();
    for (int i = Integer.parseInt(startyear); i <= Integer.parseInt(endyear); i++) {
      JSONObject json = new JSONObject();
      json.put("month", String.valueOf(i) + "年");
      json.put("name", name);
      if (czAllNums != null) {
        String nums = czAllNums.getString(String.valueOf(i) + "年");
        if (nums != null && !nums.equals(""))
          json.put("czperson", nums); 
      } else {
        json.put("czperson", "0");
      } 
      if (czCjNum != null) {
        String nums = czCjNum.getString(String.valueOf(i) + "年");
        if (nums != null && !nums.equals(""))
          json.put("czcjperson", nums); 
      } else {
        json.put("czcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("czperson")) > 0) {
        json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
      } else {
        json.put("czcjratio", "0");
      } 
      if (czPaymoney != null && czPaymoney.size() > paymoneyMark1) {
        JSONObject nums = czPaymoney.get(paymoneyMark1);
        if (nums.getString("年").equals((new StringBuilder(String.valueOf(i))).toString())) {
          String paymoney = nums.getString("paymoney");
          if (paymoney != null && !paymoney.equals(""))
            json.put("czpaymoney", paymoney); 
          String jcfmoney = nums.getString("jcfmoney");
          if (jcfmoney != null && !jcfmoney.equals(""))
            json.put("czjcfmoney", jcfmoney); 
          String yjjmoney = nums.getString("yjjmoney");
          if (yjjmoney != null && !yjjmoney.equals(""))
            json.put("czyjjmoney", yjjmoney); 
          paymoneyMark1++;
        } else {
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
      } else {
        json.put("czpaymoney", "0.00");
        json.put("czjcfmoney", "0.00");
        json.put("czyjjmoney", "0.00");
      } 
      if (fzAllNums != null) {
        String nums = fzAllNums.getString(String.valueOf(i) + "年");
        if (nums != null && !nums.equals(""))
          json.put("fzperson", nums); 
      } else {
        json.put("fzperson", "0");
      } 
      if (fzCjNum != null) {
        String nums = fzCjNum.getString(String.valueOf(i) + "年");
        if (nums != null && !nums.equals(""))
          json.put("fzcjperson", nums); 
      } else {
        json.put("fzcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("fzperson")) > 0) {
        json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
      } else {
        json.put("fzcjratio", "0");
      } 
      if (fzPaymoney != null && fzPaymoney.size() > paymoneyMark2) {
        JSONObject nums = fzPaymoney.get(paymoneyMark2);
        if (nums.getString("年").equals((new StringBuilder(String.valueOf(i))).toString())) {
          String paymoney = nums.getString("paymoney");
          if (paymoney != null && !paymoney.equals(""))
            json.put("fzpaymoney", paymoney); 
          String jcfmoney = nums.getString("jcfmoney");
          if (jcfmoney != null && !jcfmoney.equals(""))
            json.put("fzjcfmoney", jcfmoney); 
          String yjjmoney = nums.getString("yjjmoney");
          if (yjjmoney != null && !yjjmoney.equals(""))
            json.put("fzyjjmoney", paymoney); 
          paymoneyMark2++;
        } else {
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
      } else {
        json.put("fzpaymoney", "0.00");
        json.put("fzjcfmoney", "0.00");
        json.put("fzyjjmoney", "0.00");
      } 
      if (zxfAllNums != null) {
        String nums = zxfAllNums.getString(String.valueOf(i) + "年");
        if (nums != null && !nums.equals(""))
          json.put("zxfperson", nums); 
      } else {
        json.put("zxfperson", "0");
      } 
      if (zxfCjNum != null) {
        String nums = zxfCjNum.getString(String.valueOf(i) + "年");
        if (nums != null && !nums.equals(""))
          json.put("zxfcjperson", nums); 
      } else {
        json.put("zxfcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("zxfperson")) > 0) {
        json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
      } else {
        json.put("zxfcjratio", "0");
      } 
      if (zxfPaymoney != null && zxfPaymoney.size() > paymoneyMark3) {
        JSONObject nums = zxfPaymoney.get(paymoneyMark3);
        if (nums.getString("年").equals((new StringBuilder(String.valueOf(i))).toString())) {
          String paymoney = nums.getString("paymoney");
          if (paymoney != null && !paymoney.equals(""))
            json.put("zxfpaymoney", paymoney); 
          String jcfmoney = nums.getString("jcfmoney");
          if (jcfmoney != null && !jcfmoney.equals(""))
            json.put("zxfjcfmoney", jcfmoney); 
          String yjjmoney = nums.getString("yjjmoney");
          if (yjjmoney != null && !yjjmoney.equals(""))
            json.put("zxfyjjmoney", yjjmoney); 
          paymoneyMark3++;
        } else {
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
      } else {
        json.put("zxfpaymoney", "0.00");
        json.put("zxfjcfmoney", "0.00");
        json.put("zxfyjjmoney", "0.00");
      } 
      if (Cmoney != null && Cmoney.size() > cmoneyMark4) {
        JSONObject nums = Cmoney.get(cmoneyMark4);
        if (nums.getString("年").equals((new StringBuilder(String.valueOf(i))).toString()) && nums.getString("type").equals("充值")) {
          String cmoney = nums.getString("cmoney");
          if (cmoney != null && !cmoney.equals(""))
            json.put("cmoney", cmoney); 
          cmoneyMark4++;
        } else {
          json.put("cmoney", "0.00");
        } 
        if (cmoneyMark4 < Cmoney.size()) {
          JSONObject nums1 = Cmoney.get(cmoneyMark4);
          if (nums1.getString("年").equals((new StringBuilder(String.valueOf(i))).toString()) && nums1.getString("type").equals("退费")) {
            String cmoney = nums1.getString("cmoney");
            if (cmoney != null && !cmoney.equals(""))
              json.put("tcmoney", cmoney); 
            cmoneyMark4++;
          } else {
            json.put("tcmoney", "0.00");
          } 
        } else {
          json.put("tcmoney", "0.00");
        } 
      } else {
        json.put("cmoney", "0.00");
        json.put("tcmoney", "0.00");
      } 
      if (Drugsmoney != null && Drugsmoney.size() > drugsmoneyMark5) {
        JSONObject nums = Drugsmoney.get(drugsmoneyMark5);
        if (nums.getString("年").equals((new StringBuilder(String.valueOf(i))).toString())) {
          String drugsmoney = nums.getString("drugsmoney");
          if (drugsmoney != null && !drugsmoney.equals(""))
            json.put("drugsmoney", drugsmoney); 
          drugsmoneyMark5++;
        } else {
          json.put("drugsmoney", "0.00");
        } 
      } else {
        json.put("drugsmoney", "0.00");
      } 
      json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
      list.add(json);
    } 
    return list;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public List<JSONObject> findCJStatisticsByMonth(HttpServletRequest request, Map<String, String> map) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String name = "";
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < personSeqIds.size(); j++) {
        if (j == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "',");
        } 
      } 
      map.put("visualstaff", str.toString());
      name = "所有部门";
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < yzpersonSeqids.size(); j++) {
        if (j == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "',");
        } 
      } 
      map.put("visualstaff", str.toString());
      String deptname = this.yzDeptLogic.getDeptNamesBySeqIds(map.get("deptCategory"));
      name = deptname;
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("personage")) {
      map.put("visualstaff", "'" + person.getSeqId() + "'");
      name = person.getUserName();
    } 
    String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
    map.put("recesort", "'" + czseqId + "'");
    JSONObject czAllNums = this.analysisDao.findQuantityByAskpersonAndMonth(map);
    JSONObject czCjNum = this.analysisDao.findCJQuantityByAskpersonAndMonth(map);
    List<JSONObject> czPaymoney = this.analysisDao.findPaymoneyByMonth(map);
    String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
    map.put("recesort", "'" + fzseqId + "'");
    JSONObject fzAllNums = this.analysisDao.findQuantityByAskpersonAndMonth(map);
    JSONObject fzCjNum = this.analysisDao.findCJQuantityByAskpersonAndMonth(map);
    List<JSONObject> fzPaymoney = this.analysisDao.findPaymoneyByMonth(map);
    String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
    String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
    map.put("recesort", "'" + zxfseqId + "','" + fcseqId + "'");
    JSONObject zxfAllNums = this.analysisDao.findQuantityByAskpersonAndMonth(map);
    map.put("recesort", "'" + zxfseqId + "'");
    JSONObject zxfCjNum = this.analysisDao.findCJQuantityByAskpersonAndMonth(map);
    List<JSONObject> zxfPaymoney = this.analysisDao.findPaymoneyByMonth(map);
    List<JSONObject> Cmoney = this.analysisDao.findCmoneyByMonth(map);
    List<JSONObject> Drugsmoney = this.analysisDao.findDrugsmoneyByMonth(map);
    String startmonth = map.get("startmonth");
    String endmonth = map.get("endmonth");
    String year = map.get("year");
    List<JSONObject> list = new ArrayList<>();
    int hjczAllNums = 0;
    int hjczCjNum = 0;
    double hjczPaymoney = 0.0D;
    double hjczJcfmoney = 0.0D;
    double hjczYjjmoney = 0.0D;
    int hjfzAllNums = 0;
    int hjfzCjNum = 0;
    double hjfzPaymoney = 0.0D;
    double hjfzJcfmoney = 0.0D;
    double hjfzYjjmoney = 0.0D;
    int hjzxfAllNums = 0;
    int hjzxfCjNum = 0;
    double hjzxfPaymoney = 0.0D;
    double hjCmoney = 0.0D;
    double hjzxfJcfmoney = 0.0D;
    double hjzxfYjjmoney = 0.0D;
    double hjTcmoney = 0.0D;
    double hjDrugsmoney = 0.0D;
    int paymoneyMark1 = 0;
    int paymoneyMark2 = 0;
    int paymoneyMark3 = 0;
    int cmoneyMark4 = 0;
    int drugsmoneyMark5 = 0;
    for (int i = Integer.parseInt(startmonth); i <= Integer.parseInt(endmonth); i++) {
      JSONObject json = new JSONObject();
      json.put("month", String.valueOf(year) + "年" + i + "月");
      json.put("name", name);
      if (czAllNums != null) {
        String nums = czAllNums.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("czperson", nums); 
      } else {
        json.put("czperson", "0");
      } 
      if (czCjNum != null) {
        String nums = czCjNum.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("czcjperson", nums); 
      } else {
        json.put("czcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("czperson")) > 0) {
        json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
      } else {
        json.put("czcjratio", "0");
      } 
      if (czPaymoney != null && czPaymoney.size() > paymoneyMark1) {
        JSONObject nums = czPaymoney.get(paymoneyMark1);
        if (nums.getString("年").equals(year)) {
          if (nums.getString("月").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("czpaymoney", paymoney); 
            String jcfmoney = nums.getString("jcfmoney");
            if (jcfmoney != null && !jcfmoney.equals(""))
              json.put("czjcfmoney", jcfmoney); 
            String yjjmoney = nums.getString("yjjmoney");
            if (yjjmoney != null && !yjjmoney.equals(""))
              json.put("czyjjmoney", yjjmoney); 
            paymoneyMark1++;
          } else {
            json.put("czpaymoney", "0.00");
            json.put("czjcfmoney", "0.00");
            json.put("czyjjmoney", "0.00");
          } 
        } else {
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
      } else {
        json.put("czpaymoney", "0.00");
        json.put("czjcfmoney", "0.00");
        json.put("czyjjmoney", "0.00");
      } 
      if (fzAllNums != null) {
        String nums = fzAllNums.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("fzperson", nums); 
      } else {
        json.put("fzperson", "0");
      } 
      if (fzCjNum != null) {
        String nums = fzCjNum.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("fzcjperson", nums); 
      } else {
        json.put("fzcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("fzperson")) > 0) {
        json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
      } else {
        json.put("fzcjratio", "0");
      } 
      if (fzPaymoney != null && fzPaymoney.size() > paymoneyMark2) {
        JSONObject nums = fzPaymoney.get(paymoneyMark2);
        if (nums.getString("年").equals(year)) {
          if (nums.getString("月").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("fzpaymoney", paymoney); 
            String jcfmoney = nums.getString("jcfmoney");
            if (jcfmoney != null && !jcfmoney.equals(""))
              json.put("fzjcfmoney", jcfmoney); 
            String yjjmoney = nums.getString("yjjmoney");
            if (yjjmoney != null && !yjjmoney.equals(""))
              json.put("fzyjjmoney", yjjmoney); 
            paymoneyMark2++;
          } else {
            json.put("fzpaymoney", "0.00");
            json.put("fzjcfmoney", "0.00");
            json.put("fzyjjmoney", "0.00");
          } 
        } else {
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
      } else {
        json.put("fzpaymoney", "0.00");
        json.put("fzjcfmoney", "0.00");
        json.put("fzyjjmoney", "0.00");
      } 
      if (zxfAllNums != null) {
        String nums = zxfAllNums.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("zxfperson", nums); 
      } else {
        json.put("zxfperson", "0");
      } 
      if (zxfCjNum != null) {
        String nums = zxfCjNum.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("zxfcjperson", nums); 
      } else {
        json.put("zxfcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("zxfperson")) > 0) {
        json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
      } else {
        json.put("zxfcjratio", "0");
      } 
      if (zxfPaymoney != null && zxfPaymoney.size() > paymoneyMark3) {
        JSONObject nums = zxfPaymoney.get(paymoneyMark3);
        if (nums.getString("年").equals(year)) {
          if (nums.getString("月").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("zxfpaymoney", paymoney); 
            String jcfmoney = nums.getString("jcfmoney");
            if (jcfmoney != null && !jcfmoney.equals(""))
              json.put("zxfjcfmoney", jcfmoney); 
            String yjjmoney = nums.getString("yjjmoney");
            if (yjjmoney != null && !yjjmoney.equals(""))
              json.put("zxfyjjmoney", yjjmoney); 
            paymoneyMark3++;
          } else {
            json.put("zxfpaymoney", "0.00");
            json.put("zxfjcfmoney", "0.00");
            json.put("zxfyjjmoney", "0.00");
          } 
        } else {
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
      } else {
        json.put("zxfpaymoney", "0.00");
        json.put("zxfjcfmoney", "0.00");
        json.put("zxfyjjmoney", "0.00");
      } 
      if (Cmoney != null && Cmoney.size() > cmoneyMark4) {
        JSONObject nums = Cmoney.get(cmoneyMark4);
        if (nums.getString("年").equals(year)) {
          if (nums.getString("月").equals((new StringBuilder(String.valueOf(i))).toString()) && nums.getString("type").equals("充值")) {
            String cmoney = nums.getString("cmoney");
            if (cmoney != null && !cmoney.equals(""))
              json.put("cmoney", cmoney); 
            cmoneyMark4++;
          } else {
            json.put("cmoney", "0.00");
          } 
          if (cmoneyMark4 < Cmoney.size()) {
            JSONObject nums1 = Cmoney.get(cmoneyMark4);
            if (nums1.getString("月").equals((new StringBuilder(String.valueOf(i))).toString()) && nums1.getString("type").equals("退费")) {
              String cmoney = nums1.getString("cmoney");
              if (cmoney != null && !cmoney.equals(""))
                json.put("tcmoney", cmoney); 
              cmoneyMark4++;
            } else {
              json.put("tcmoney", "0.00");
            } 
          } else {
            json.put("tcmoney", "0.00");
          } 
        } else {
          json.put("cmoney", "0.00");
          json.put("tcmoney", "0.00");
        } 
      } else {
        json.put("cmoney", "0.00");
        json.put("tcmoney", "0.00");
      } 
      if (Drugsmoney != null && Drugsmoney.size() > drugsmoneyMark5) {
        JSONObject nums = Drugsmoney.get(drugsmoneyMark5);
        if (nums.getString("年").equals(year)) {
          if (nums.getString("月").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String drugsmoney = nums.getString("drugsmoney");
            if (drugsmoney != null && !drugsmoney.equals(""))
              json.put("drugsmoney", drugsmoney); 
            drugsmoneyMark5++;
          } else {
            json.put("drugsmoney", "0.00");
          } 
        } else {
          json.put("drugsmoney", "0.00");
        } 
      } else {
        json.put("drugsmoney", "0.00");
      } 
      json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
      hjczAllNums += Integer.parseInt(json.getString("czperson"));
      hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
      hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
      hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
      hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
      hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
      hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
      hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
      hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
      hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
      hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
      hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
      hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
      hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
      hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
      hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
      hjCmoney += Double.parseDouble(json.getString("cmoney"));
      hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
      list.add(json);
    } 
    JSONObject jsonobject = new JSONObject();
    jsonobject.put("month", String.valueOf(year) + "年");
    jsonobject.put("name", "合计");
    jsonobject.put("czperson", (new StringBuilder(String.valueOf(hjczAllNums))).toString());
    jsonobject.put("czcjperson", (new StringBuilder(String.valueOf(hjczCjNum))).toString());
    NumberFormat nf1 = NumberFormat.getInstance();
    nf1.setGroupingUsed(false);
    if (hjczPaymoney > 0.0D) {
      jsonobject.put("czpaymoney", nf1.format(hjczPaymoney));
    } else {
      jsonobject.put("czpaymoney", "0.00");
    } 
    if (hjczJcfmoney > 0.0D) {
      jsonobject.put("czjcfmoney", nf1.format(hjczJcfmoney));
    } else {
      jsonobject.put("czjcfmoney", "0.00");
    } 
    if (hjczYjjmoney > 0.0D) {
      jsonobject.put("czyjjmoney", nf1.format(hjczYjjmoney));
    } else {
      jsonobject.put("czyjjmoney", "0.00");
    } 
    jsonobject.put("fzperson", (new StringBuilder(String.valueOf(hjfzAllNums))).toString());
    jsonobject.put("fzcjperson", (new StringBuilder(String.valueOf(hjfzCjNum))).toString());
    if (hjfzPaymoney > 0.0D) {
      jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
    } else {
      jsonobject.put("fzpaymoney", "0.00");
    } 
    if (hjfzJcfmoney > 0.0D) {
      jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
    } else {
      jsonobject.put("fzjcfmoney", "0.00");
    } 
    if (hjfzYjjmoney > 0.0D) {
      jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
    } else {
      jsonobject.put("fzyjjmoney", "0.00");
    } 
    jsonobject.put("zxfperson", (new StringBuilder(String.valueOf(hjzxfAllNums))).toString());
    jsonobject.put("zxfcjperson", (new StringBuilder(String.valueOf(hjzxfCjNum))).toString());
    if (hjzxfPaymoney > 0.0D) {
      jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
    } else {
      jsonobject.put("zxfpaymoney", "0.00");
    } 
    if (hjzxfJcfmoney > 0.0D) {
      jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
    } else {
      jsonobject.put("zxfjcfmoney", "0.00");
    } 
    if (hjzxfYjjmoney > 0.0D) {
      jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
    } else {
      jsonobject.put("zxfyjjmoney", "0.00");
    } 
    if (hjCmoney > 0.0D) {
      jsonobject.put("cmoney", nf1.format(hjCmoney));
    } else {
      jsonobject.put("cmoney", "0.00");
    } 
    if (hjTcmoney < 0.0D) {
      jsonobject.put("tcmoney", nf1.format(hjTcmoney));
    } else {
      jsonobject.put("tcmoney", "0.00");
    } 
    if (hjDrugsmoney > 0.0D) {
      jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
    } else {
      jsonobject.put("drugsmoney", "0.00");
    } 
    if (hjczAllNums > 0) {
      jsonobject.put("czcjratio", (new StringBuilder(String.valueOf(hjczCjNum / hjczAllNums))).toString());
    } else {
      jsonobject.put("czcjratio", "0");
    } 
    if (hjfzAllNums > 0) {
      jsonobject.put("fzcjratio", (new StringBuilder(String.valueOf(hjfzCjNum / hjfzAllNums))).toString());
    } else {
      jsonobject.put("fzcjratio", "0");
    } 
    if (hjzxfAllNums > 0) {
      jsonobject.put("zxfcjratio", (new StringBuilder(String.valueOf(hjzxfCjNum / hjzxfAllNums))).toString());
    } else {
      jsonobject.put("zxfcjratio", "0");
    } 
    jsonobject.put("xmpaymoney", Double.parseDouble(jsonobject.getString("czpaymoney")) + Double.parseDouble(jsonobject.getString("fzpaymoney")) + Double.parseDouble(jsonobject.getString("zxfpaymoney")));
    list.add(jsonobject);
    return list;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public List<JSONObject> findCJStatisticsByDay(HttpServletRequest request, Map<String, String> map) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String name = "";
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personSeqIds.size(); i++) {
        if (i == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "',");
        } 
      } 
      map.put("visualstaff", str.toString());
      name = "所有部门";
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < yzpersonSeqids.size(); i++) {
        if (i == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "',");
        } 
      } 
      map.put("visualstaff", str.toString());
      String deptname = this.yzDeptLogic.getDeptNamesBySeqIds(map.get("deptCategory"));
      name = deptname;
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("personage")) {
      map.put("visualstaff", "'" + person.getSeqId() + "'");
      name = person.getUserName();
    } 
    String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
    map.put("recesort", "'" + czseqId + "'");
    JSONObject czAllNums = this.analysisDao.findQuantityByAskpersonAndDay(map);
    JSONObject czCjNum = this.analysisDao.findCJQuantityByAskpersonAndDay(map);
    List<JSONObject> czPaymoney = this.analysisDao.findPaymoneyByDay(map);
    String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
    map.put("recesort", "'" + fzseqId + "'");
    JSONObject fzAllNums = this.analysisDao.findQuantityByAskpersonAndDay(map);
    JSONObject fzCjNum = this.analysisDao.findCJQuantityByAskpersonAndDay(map);
    List<JSONObject> fzPaymoney = this.analysisDao.findPaymoneyByDay(map);
    String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
    String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
    map.put("recesort", "'" + zxfseqId + "','" + fcseqId + "'");
    JSONObject zxfAllNums = this.analysisDao.findQuantityByAskpersonAndDay(map);
    map.put("recesort", "'" + zxfseqId + "'");
    JSONObject zxfCjNum = this.analysisDao.findCJQuantityByAskpersonAndDay(map);
    List<JSONObject> zxfPaymoney = this.analysisDao.findPaymoneyByDay(map);
    List<JSONObject> Cmoney = this.analysisDao.findCmoneyByDay(map);
    List<JSONObject> Drugsmoney = this.analysisDao.findDrugsmoneyByDay(map);
    List<JSONObject> list = new ArrayList<>();
    int paymoneyMark1 = 0;
    int paymoneyMark2 = 0;
    int paymoneyMark3 = 0;
    int cmoneyMark4 = 0;
    int drugsmoneyMark5 = 0;
    if ((((String)map.get("startTime")).contains("年") && ((String)map.get("startTime")).length() == 7) || (((String)map.get("startTime")).contains("年") && ((String)map.get("startTime")).length() == 8)) {
      String endday = map.get("endday");
      for (int i = 1; i <= Integer.parseInt(endday); i++) {
        JSONObject json = new JSONObject();
        json.put("day", String.valueOf(i) + "日");
        if (czAllNums != null) {
          String nums = czAllNums.getString(String.valueOf(i) + "日");
          if (nums != null && !nums.equals(""))
            json.put("czperson", nums); 
        } else {
          json.put("czperson", "0");
        } 
        if (czCjNum != null) {
          String nums = czCjNum.getString(String.valueOf(i) + "日");
          if (nums != null && !nums.equals(""))
            json.put("czcjperson", nums); 
        } else {
          json.put("czcjperson", "0");
        } 
        if (czPaymoney != null && czPaymoney.size() > paymoneyMark1) {
          JSONObject nums = czPaymoney.get(paymoneyMark1);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("czpaymoney", paymoney); 
            String jcfmoney = nums.getString("jcfmoney");
            if (jcfmoney != null && !jcfmoney.equals(""))
              json.put("czjcfmoney", jcfmoney); 
            String yjjmoney = nums.getString("yjjmoney");
            if (yjjmoney != null && !yjjmoney.equals(""))
              json.put("czyjjmoney", yjjmoney); 
            paymoneyMark1++;
          } else {
            json.put("czpaymoney", "0.00");
            json.put("czjcfmoney", "0.00");
            json.put("czyjjmoney", "0.00");
          } 
        } else {
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
        if (fzAllNums != null) {
          String nums = fzAllNums.getString(String.valueOf(i) + "日");
          if (nums != null && !nums.equals(""))
            json.put("fzperson", nums); 
        } else {
          json.put("fzperson", "0");
        } 
        if (fzCjNum != null) {
          String nums = fzCjNum.getString(String.valueOf(i) + "日");
          if (nums != null && !nums.equals(""))
            json.put("fzcjperson", nums); 
        } else {
          json.put("fzcjperson", "0");
        } 
        if (fzPaymoney != null && fzPaymoney.size() > paymoneyMark2) {
          JSONObject nums = fzPaymoney.get(paymoneyMark2);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("fzpaymoney", paymoney); 
            String jcfmoney = nums.getString("jcfmoney");
            if (jcfmoney != null && !jcfmoney.equals(""))
              json.put("fzjcfmoney", jcfmoney); 
            String yjjmoney = nums.getString("yjjmoney");
            if (yjjmoney != null && !yjjmoney.equals(""))
              json.put("fzyjjmoney", yjjmoney); 
            paymoneyMark2++;
          } else {
            json.put("fzpaymoney", "0.00");
            json.put("fzjcfmoney", "0.00");
            json.put("fzyjjmoney", "0.00");
          } 
        } else {
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
        if (zxfAllNums != null) {
          String nums = zxfAllNums.getString(String.valueOf(i) + "日");
          if (nums != null && !nums.equals(""))
            json.put("zxfperson", nums); 
        } else {
          json.put("zxfperson", "0");
        } 
        if (zxfCjNum != null) {
          String nums = zxfCjNum.getString(String.valueOf(i) + "日");
          if (nums != null && !nums.equals(""))
            json.put("zxfcjperson", nums); 
        } else {
          json.put("zxfcjperson", "0");
        } 
        if (zxfPaymoney != null && zxfPaymoney.size() > paymoneyMark3) {
          JSONObject nums = zxfPaymoney.get(paymoneyMark3);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("zxfpaymoney", paymoney); 
            String jcfmoney = nums.getString("jcfmoney");
            if (jcfmoney != null && !jcfmoney.equals(""))
              json.put("zxfjcfmoney", jcfmoney); 
            String yjjmoney = nums.getString("yjjmoney");
            if (yjjmoney != null && !yjjmoney.equals(""))
              json.put("zxfyjjmoney", yjjmoney); 
            paymoneyMark3++;
          } else {
            json.put("zxfpaymoney", "0.00");
            json.put("zxfjcfmoney", "0.00");
            json.put("zxfyjjmoney", "0.00");
          } 
        } else {
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
        if (Cmoney != null && Cmoney.size() > cmoneyMark4) {
          JSONObject nums = Cmoney.get(cmoneyMark4);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString()) && nums.getString("type").equals("充值")) {
            String cmoney = nums.getString("cmoney");
            if (cmoney != null && !cmoney.equals(""))
              json.put("cmoney", cmoney); 
            cmoneyMark4++;
          } else {
            json.put("cmoney", "0.00");
          } 
          if (cmoneyMark4 < Cmoney.size()) {
            JSONObject nums1 = Cmoney.get(cmoneyMark4);
            if (nums1.getString("日").equals((new StringBuilder(String.valueOf(i))).toString()) && nums.getString("type").equals("退费")) {
              String cmoney = nums.getString("cmoney");
              if (cmoney != null && !cmoney.equals(""))
                json.put("tcmoney", cmoney); 
              cmoneyMark4++;
            } else {
              json.put("tcmoney", "0.00");
            } 
          } else {
            json.put("tcmoney", "0.00");
          } 
        } else {
          json.put("cmoney", "0.00");
          json.put("tcmoney", "0.00");
        } 
        if (Drugsmoney != null && Drugsmoney.size() > drugsmoneyMark5) {
          JSONObject nums = fzPaymoney.get(drugsmoneyMark5);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String drugsmoney = nums.getString("drugsmoney");
            if (drugsmoney != null && !drugsmoney.equals(""))
              json.put("drugsmoney", drugsmoney); 
            drugsmoneyMark5++;
          } else {
            json.put("drugsmoney", "0.00");
          } 
        } else {
          json.put("drugsmoney", "0.00");
        } 
        list.add(json);
      } 
    } else {
      int hjczAllNums = 0;
      int hjczCjNum = 0;
      double hjczPaymoney = 0.0D;
      double hjczJcfmoney = 0.0D;
      double hjczYjjmoney = 0.0D;
      int hjfzAllNums = 0;
      int hjfzCjNum = 0;
      double hjfzPaymoney = 0.0D;
      double hjfzJcfmoney = 0.0D;
      double hjfzYjjmoney = 0.0D;
      int hjzxfAllNums = 0;
      int hjzxfCjNum = 0;
      double hjzxfPaymoney = 0.0D;
      double hjCmoney = 0.0D;
      double hjzxfJcfmoney = 0.0D;
      double hjzxfYjjmoney = 0.0D;
      double hjTcmoney = 0.0D;
      double hjDrugsmoney = 0.0D;
      if (((String)map.get("startTime")).equals(map.get("endTime"))) {
        JSONObject json = new JSONObject();
        json.put("month", String.valueOf(((String)map.get("startTime")).substring(0, 4)) + "年" + ((String)map.get("startTime")).substring(5, 7) + "月" + ((String)map.get("startTime")).substring(8, 10) + "日");
        json.put("name", name);
        if (czAllNums != null) {
          String nums = czAllNums.getString(map.get("startTime"));
          if (nums != null && !nums.equals(""))
            json.put("czperson", nums); 
        } else {
          json.put("czperson", "0");
        } 
        if (czCjNum != null) {
          String nums = czCjNum.getString(map.get("startTime"));
          if (nums != null && !nums.equals(""))
            json.put("czcjperson", nums); 
        } else {
          json.put("czcjperson", "0");
        } 
        if (Integer.parseInt(json.getString("czperson")) > 0) {
          json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
        } else {
          json.put("czcjratio", "0");
        } 
        if (czPaymoney != null && czPaymoney.size() > paymoneyMark1) {
          JSONObject nums = czPaymoney.get(paymoneyMark1);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(Integer.parseInt(((String)map.get("startTime")).substring(8, 10))))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("czpaymoney", paymoney); 
            String jcfmoney = nums.getString("jcfmoney");
            if (jcfmoney != null && !jcfmoney.equals(""))
              json.put("czjcfmoney", jcfmoney); 
            String yjjmoney = nums.getString("yjjmoney");
            if (yjjmoney != null && !yjjmoney.equals(""))
              json.put("czyjjmoney", yjjmoney); 
            paymoneyMark1++;
          } else {
            json.put("czpaymoney", "0.00");
            json.put("czjcfmoney", "0.00");
            json.put("czyjjmoney", "0.00");
          } 
        } else {
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
        if (fzAllNums != null) {
          String nums = fzAllNums.getString(map.get("startTime"));
          if (nums != null && !nums.equals(""))
            json.put("fzperson", nums); 
        } else {
          json.put("fzperson", "0");
        } 
        if (fzCjNum != null) {
          String nums = fzCjNum.getString(map.get("startTime"));
          if (nums != null && !nums.equals(""))
            json.put("fzcjperson", nums); 
        } else {
          json.put("fzcjperson", "0");
        } 
        if (Integer.parseInt(json.getString("fzperson")) > 0) {
          json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
        } else {
          json.put("fzcjratio", "0");
        } 
        if (fzPaymoney != null && fzPaymoney.size() > paymoneyMark2) {
          JSONObject nums = fzPaymoney.get(paymoneyMark2);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(Integer.parseInt(((String)map.get("startTime")).substring(8, 10))))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("fzpaymoney", paymoney); 
            String jcfmoney = nums.getString("jcfmoney");
            if (jcfmoney != null && !jcfmoney.equals(""))
              json.put("fzjcfmoney", jcfmoney); 
            String yjjmoney = nums.getString("yjjmoney");
            if (yjjmoney != null && !yjjmoney.equals(""))
              json.put("fzyjjmoney", yjjmoney); 
            paymoneyMark2++;
          } else {
            json.put("fzpaymoney", "0.00");
            json.put("fzjcfmoney", "0.00");
            json.put("fzyjjmoney", "0.00");
          } 
        } else {
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
        if (zxfAllNums != null) {
          String nums = zxfAllNums.getString(map.get("startTime"));
          if (nums != null && !nums.equals(""))
            json.put("zxfperson", nums); 
        } else {
          json.put("zxfperson", "0");
        } 
        if (zxfCjNum != null) {
          String nums = zxfCjNum.getString(map.get("startTime"));
          if (nums != null && !nums.equals(""))
            json.put("zxfcjperson", nums); 
        } else {
          json.put("zxfcjperson", "0");
        } 
        if (Integer.parseInt(json.getString("zxfperson")) > 0) {
          json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
        } else {
          json.put("zxfcjratio", "0");
        } 
        if (zxfPaymoney != null && zxfPaymoney.size() > paymoneyMark3) {
          JSONObject nums = zxfPaymoney.get(paymoneyMark3);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(Integer.parseInt(((String)map.get("startTime")).substring(8, 10))))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("zxfpaymoney", paymoney); 
            String jcfmoney = nums.getString("jcfmoney");
            if (jcfmoney != null && !jcfmoney.equals(""))
              json.put("zxfjcfmoney", jcfmoney); 
            String yjjmoney = nums.getString("yjjmoney");
            if (yjjmoney != null && !yjjmoney.equals(""))
              json.put("zxfyjjmoney", yjjmoney); 
            paymoneyMark3++;
          } else {
            json.put("zxfpaymoney", "0.00");
            json.put("zxfjcfmoney", "0.00");
            json.put("zxfyjjmoney", "0.00");
          } 
        } else {
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
        if (Cmoney != null && Cmoney.size() > cmoneyMark4) {
          JSONObject nums = Cmoney.get(cmoneyMark4);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(Integer.parseInt(((String)map.get("startTime")).substring(8, 10))))).toString()) && nums.getString("type").equals("充值")) {
            String cmoney = nums.getString("cmoney");
            if (cmoney != null && !cmoney.equals(""))
              json.put("cmoney", cmoney); 
            cmoneyMark4++;
          } else {
            json.put("cmoney", "0.00");
          } 
          if (cmoneyMark4 < Cmoney.size()) {
            JSONObject nums1 = Cmoney.get(cmoneyMark4);
            if (nums1.getString("日").equals((new StringBuilder(String.valueOf(Integer.parseInt(((String)map.get("startTime")).substring(8, 10))))).toString()) && nums1.getString("type").equals("退费")) {
              String cmoney = nums1.getString("cmoney");
              if (cmoney != null && !cmoney.equals(""))
                json.put("tcmoney", cmoney); 
              cmoneyMark4++;
            } else {
              json.put("tcmoney", "0.00");
            } 
          } else {
            json.put("tcmoney", "0.00");
          } 
        } else {
          json.put("cmoney", "0.00");
          json.put("tcmoney", "0.00");
        } 
        if (Drugsmoney != null && Drugsmoney.size() > drugsmoneyMark5) {
          JSONObject nums = Drugsmoney.get(drugsmoneyMark5);
          if (nums.getString("日").equals((new StringBuilder(String.valueOf(Integer.parseInt(((String)map.get("startTime")).substring(8, 10))))).toString())) {
            String drugsmoney = nums.getString("drugsmoney");
            if (drugsmoney != null && !drugsmoney.equals(""))
              json.put("drugsmoney", drugsmoney); 
            drugsmoneyMark5++;
          } else {
            json.put("drugsmoney", "0.00");
          } 
        } else {
          json.put("drugsmoney", "0.00");
        } 
        json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
        hjczAllNums += Integer.parseInt(json.getString("czperson"));
        hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
        hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
        hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
        hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
        hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
        hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
        hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
        hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
        hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
        hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
        hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
        hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
        hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
        hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
        hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
        hjCmoney += Double.parseDouble(json.getString("cmoney"));
        hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
        list.add(json);
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("month", String.valueOf(((String)map.get("startTime")).substring(0, 4)) + "年" + ((String)map.get("startTime")).substring(5, 7) + "月" + ((String)map.get("startTime")).substring(8, 10) + "日");
        jsonobject.put("name", "合计");
        jsonobject.put("czperson", (new StringBuilder(String.valueOf(hjczAllNums))).toString());
        jsonobject.put("czcjperson", (new StringBuilder(String.valueOf(hjczCjNum))).toString());
        NumberFormat nf1 = NumberFormat.getInstance();
        nf1.setGroupingUsed(false);
        if (hjczPaymoney > 0.0D) {
          jsonobject.put("czpaymoney", nf1.format(hjczPaymoney));
        } else {
          jsonobject.put("czpaymoney", "0.00");
        } 
        if (hjczJcfmoney > 0.0D) {
          jsonobject.put("czjcfmoney", nf1.format(hjczJcfmoney));
        } else {
          jsonobject.put("czjcfmoney", "0.00");
        } 
        if (hjczYjjmoney > 0.0D) {
          jsonobject.put("czyjjmoney", nf1.format(hjczYjjmoney));
        } else {
          jsonobject.put("czyjjmoney", "0.00");
        } 
        jsonobject.put("fzperson", (new StringBuilder(String.valueOf(hjfzAllNums))).toString());
        jsonobject.put("fzcjperson", (new StringBuilder(String.valueOf(hjfzCjNum))).toString());
        if (hjfzPaymoney > 0.0D) {
          jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
        } else {
          jsonobject.put("fzpaymoney", "0.00");
        } 
        if (hjfzJcfmoney > 0.0D) {
          jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
        } else {
          jsonobject.put("fzjcfmoney", "0.00");
        } 
        if (hjfzYjjmoney > 0.0D) {
          jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
        } else {
          jsonobject.put("fzyjjmoney", "0.00");
        } 
        jsonobject.put("zxfperson", (new StringBuilder(String.valueOf(hjzxfAllNums))).toString());
        jsonobject.put("zxfcjperson", (new StringBuilder(String.valueOf(hjzxfCjNum))).toString());
        if (hjzxfPaymoney > 0.0D) {
          jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
        } else {
          jsonobject.put("zxfpaymoney", "0.00");
        } 
        if (hjzxfYjjmoney > 0.0D) {
          jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
        } else {
          jsonobject.put("zxfyjjmoney", "0.00");
        } 
        if (hjzxfJcfmoney > 0.0D) {
          jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
        } else {
          jsonobject.put("zxfjcfmoney", "0.00");
        } 
        if (hjCmoney > 0.0D) {
          jsonobject.put("cmoney", nf1.format(hjCmoney));
        } else {
          jsonobject.put("cmoney", "0.00");
        } 
        if (hjTcmoney < 0.0D) {
          jsonobject.put("tcmoney", nf1.format(hjTcmoney));
        } else {
          jsonobject.put("tcmoney", "0.00");
        } 
        if (hjDrugsmoney > 0.0D) {
          jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
        } else {
          jsonobject.put("drugsmoney", "0.00");
        } 
        if (hjczAllNums > 0) {
          jsonobject.put("czcjratio", (new StringBuilder(String.valueOf(hjczCjNum / hjczAllNums))).toString());
        } else {
          jsonobject.put("czcjratio", "0");
        } 
        if (hjfzAllNums > 0) {
          jsonobject.put("fzcjratio", (new StringBuilder(String.valueOf(hjfzCjNum / hjfzAllNums))).toString());
        } else {
          jsonobject.put("fzcjratio", "0");
        } 
        if (hjzxfAllNums > 0) {
          jsonobject.put("zxfcjratio", (new StringBuilder(String.valueOf(hjzxfCjNum / hjzxfAllNums))).toString());
        } else {
          jsonobject.put("zxfcjratio", "0");
        } 
        jsonobject.put("xmpaymoney", Double.parseDouble(jsonobject.getString("czpaymoney")) + Double.parseDouble(jsonobject.getString("fzpaymoney")) + Double.parseDouble(jsonobject.getString("zxfpaymoney")));
        list.add(jsonobject);
      } else {
        for (int i = Integer.parseInt(map.get("startday")); i <= Integer.parseInt((String)map.get("endday")); i++) {
          JSONObject json = new JSONObject();
          String r = "";
          if (i <= 9) {
            r = "0" + i;
          } else {
            r = (new StringBuilder(String.valueOf(i))).toString();
          } 
          json.put("month", String.valueOf(map.get("year")) + "年" + (String)map.get("month") + "月" + r + "日");
          json.put("name", name);
          if (czAllNums != null) {
            String nums = czAllNums.getString(String.valueOf(i) + "日");
            if (nums != null && !nums.equals(""))
              json.put("czperson", nums); 
          } else {
            json.put("czperson", "0");
          } 
          if (czCjNum != null) {
            String nums = czCjNum.getString(String.valueOf(i) + "日");
            if (nums != null && !nums.equals(""))
              json.put("czcjperson", nums); 
          } else {
            json.put("czcjperson", "0");
          } 
          if (Integer.parseInt(json.getString("czperson")) > 0) {
            json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
          } else {
            json.put("czcjratio", "0");
          } 
          if (czPaymoney != null && czPaymoney.size() > paymoneyMark1) {
            JSONObject nums = czPaymoney.get(paymoneyMark1);
            if (nums.getString("月").equals((new StringBuilder(String.valueOf(Integer.parseInt(map.get("month"))))).toString())) {
              if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString())) {
                String paymoney = nums.getString("paymoney");
                if (paymoney != null && !paymoney.equals(""))
                  json.put("czpaymoney", paymoney); 
                String jcfmoney = nums.getString("jcfmoney");
                if (jcfmoney != null && !jcfmoney.equals(""))
                  json.put("czjcfmoney", jcfmoney); 
                String yjjmoney = nums.getString("yjjmoney");
                if (yjjmoney != null && !yjjmoney.equals(""))
                  json.put("czyjjmoney", yjjmoney); 
                paymoneyMark1++;
              } else {
                json.put("czpaymoney", "0.00");
                json.put("czjcfmoney", "0.00");
                json.put("czyjjmoney", "0.00");
              } 
            } else {
              json.put("czpaymoney", "0.00");
              json.put("czjcfmoney", "0.00");
              json.put("czyjjmoney", "0.00");
            } 
          } else {
            json.put("czpaymoney", "0.00");
            json.put("czjcfmoney", "0.00");
            json.put("czyjjmoney", "0.00");
          } 
          if (fzAllNums != null) {
            String nums = fzAllNums.getString(String.valueOf(i) + "日");
            if (nums != null && !nums.equals(""))
              json.put("fzperson", nums); 
          } else {
            json.put("fzperson", "0");
          } 
          if (fzCjNum != null) {
            String nums = fzCjNum.getString(String.valueOf(i) + "日");
            if (nums != null && !nums.equals(""))
              json.put("fzcjperson", nums); 
          } else {
            json.put("fzcjperson", "0");
          } 
          if (Integer.parseInt(json.getString("fzperson")) > 0) {
            json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
          } else {
            json.put("fzcjratio", "0");
          } 
          if (fzPaymoney != null && fzPaymoney.size() > paymoneyMark2) {
            JSONObject nums = fzPaymoney.get(paymoneyMark2);
            if (nums.getString("月").equals((new StringBuilder(String.valueOf(Integer.parseInt(map.get("month"))))).toString())) {
              if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString())) {
                String paymoney = nums.getString("paymoney");
                if (paymoney != null && !paymoney.equals(""))
                  json.put("fzpaymoney", paymoney); 
                String jcfmoney = nums.getString("jcfmoney");
                if (jcfmoney != null && !jcfmoney.equals(""))
                  json.put("fzjcfmoney", jcfmoney); 
                String yjjmoney = nums.getString("yjjmoney");
                if (yjjmoney != null && !yjjmoney.equals(""))
                  json.put("fzyjjmoney", yjjmoney); 
                paymoneyMark2++;
              } else {
                json.put("fzpaymoney", "0.00");
                json.put("fzjcfmoney", "0.00");
                json.put("fzyjjmoney", "0.00");
              } 
            } else {
              json.put("fzpaymoney", "0.00");
              json.put("fzjcfmoney", "0.00");
              json.put("fzyjjmoney", "0.00");
            } 
          } else {
            json.put("fzpaymoney", "0.00");
            json.put("fzjcfmoney", "0.00");
            json.put("fzyjjmoney", "0.00");
          } 
          if (zxfAllNums != null) {
            String nums = zxfAllNums.getString(String.valueOf(i) + "日");
            if (nums != null && !nums.equals(""))
              json.put("zxfperson", nums); 
          } else {
            json.put("zxfperson", "0");
          } 
          if (zxfCjNum != null) {
            String nums = zxfCjNum.getString(String.valueOf(i) + "日");
            if (nums != null && !nums.equals(""))
              json.put("zxfcjperson", nums); 
          } else {
            json.put("zxfcjperson", "0");
          } 
          if (Integer.parseInt(json.getString("zxfperson")) > 0) {
            json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
          } else {
            json.put("zxfcjratio", "0");
          } 
          if (zxfPaymoney != null && zxfPaymoney.size() > paymoneyMark3) {
            JSONObject nums = zxfPaymoney.get(paymoneyMark3);
            if (nums.getString("月").equals((new StringBuilder(String.valueOf(Integer.parseInt(map.get("month"))))).toString())) {
              if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString())) {
                String paymoney = nums.getString("paymoney");
                if (paymoney != null && !paymoney.equals(""))
                  json.put("zxfpaymoney", paymoney); 
                String jcfmoney = nums.getString("jcfmoney");
                if (jcfmoney != null && !jcfmoney.equals(""))
                  json.put("zxfjcfmoney", jcfmoney); 
                String yjjmoney = nums.getString("yjjmoney");
                if (yjjmoney != null && !yjjmoney.equals(""))
                  json.put("zxfyjjmoney", yjjmoney); 
                paymoneyMark3++;
              } else {
                json.put("zxfpaymoney", "0.00");
                json.put("zxfjcfmoney", "0.00");
                json.put("zxfyjjmoney", "0.00");
              } 
            } else {
              json.put("zxfpaymoney", "0.00");
              json.put("zxfjcfmoney", "0.00");
              json.put("zxfyjjmoney", "0.00");
            } 
          } else {
            json.put("zxfpaymoney", "0.00");
            json.put("zxfjcfmoney", "0.00");
            json.put("zxfyjjmoney", "0.00");
          } 
          if (Cmoney != null && Cmoney.size() > cmoneyMark4) {
            JSONObject nums = Cmoney.get(cmoneyMark4);
            if (nums.getString("月").equals((new StringBuilder(String.valueOf(Integer.parseInt(map.get("month"))))).toString())) {
              if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString()) && nums.getString("type").equals("充值")) {
                String cmoney = nums.getString("cmoney");
                if (cmoney != null && !cmoney.equals(""))
                  json.put("cmoney", cmoney); 
                cmoneyMark4++;
              } else {
                json.put("cmoney", "0.00");
              } 
              if (cmoneyMark4 < Cmoney.size()) {
                JSONObject nums1 = Cmoney.get(cmoneyMark4);
                if (nums1.getString("日").equals((new StringBuilder(String.valueOf(i))).toString()) && nums1.getString("type").equals("退费")) {
                  String cmoney = nums1.getString("cmoney");
                  if (cmoney != null && !cmoney.equals(""))
                    json.put("tcmoney", cmoney); 
                  cmoneyMark4++;
                } else {
                  json.put("tcmoney", "0.00");
                } 
              } else {
                json.put("tcmoney", "0.00");
              } 
            } else {
              json.put("cmoney", "0.00");
              json.put("tcmoney", "0.00");
            } 
          } else {
            json.put("cmoney", "0.00");
            json.put("tcmoney", "0.00");
          } 
          if (Drugsmoney != null && Drugsmoney.size() > drugsmoneyMark5) {
            JSONObject nums = Drugsmoney.get(drugsmoneyMark5);
            if (nums.getString("月").equals((new StringBuilder(String.valueOf(Integer.parseInt(map.get("month"))))).toString())) {
              if (nums.getString("日").equals((new StringBuilder(String.valueOf(i))).toString())) {
                String drugsmoney = nums.getString("drugsmoney");
                if (drugsmoney != null && !drugsmoney.equals(""))
                  json.put("drugsmoney", drugsmoney); 
                drugsmoneyMark5++;
              } else {
                json.put("drugsmoney", "0.00");
              } 
            } else {
              json.put("drugsmoney", "0.00");
            } 
          } else {
            json.put("drugsmoney", "0.00");
          } 
          json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
          hjczAllNums += Integer.parseInt(json.getString("czperson"));
          hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
          hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
          hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
          hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
          hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
          hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
          hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
          hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
          hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
          hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
          hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
          hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
          hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
          hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
          hjCmoney += Double.parseDouble(json.getString("cmoney"));
          hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
          hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
          list.add(json);
        } 
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("month", String.valueOf(map.get("year")) + "年" + (String)map.get("month") + "月");
        jsonobject.put("name", "合计");
        jsonobject.put("czperson", (new StringBuilder(String.valueOf(hjczAllNums))).toString());
        jsonobject.put("czcjperson", (new StringBuilder(String.valueOf(hjczCjNum))).toString());
        NumberFormat nf1 = NumberFormat.getInstance();
        nf1.setGroupingUsed(false);
        if (hjczPaymoney > 0.0D) {
          jsonobject.put("czpaymoney", nf1.format(hjczPaymoney));
        } else {
          jsonobject.put("czpaymoney", "0.00");
        } 
        if (hjczJcfmoney > 0.0D) {
          jsonobject.put("czjcfmoney", nf1.format(hjczJcfmoney));
        } else {
          jsonobject.put("czjcfmoney", "0.00");
        } 
        if (hjczYjjmoney > 0.0D) {
          jsonobject.put("czyjjmoney", nf1.format(hjczYjjmoney));
        } else {
          jsonobject.put("czyjjmoney", "0.00");
        } 
        jsonobject.put("fzperson", (new StringBuilder(String.valueOf(hjfzAllNums))).toString());
        jsonobject.put("fzcjperson", (new StringBuilder(String.valueOf(hjfzCjNum))).toString());
        if (hjfzPaymoney > 0.0D) {
          jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
        } else {
          jsonobject.put("fzpaymoney", "0.00");
        } 
        if (hjfzJcfmoney > 0.0D) {
          jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
        } else {
          jsonobject.put("fzjcfmoney", "0.00");
        } 
        if (hjfzYjjmoney > 0.0D) {
          jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
        } else {
          jsonobject.put("fzyjjmoney", "0.00");
        } 
        jsonobject.put("zxfperson", (new StringBuilder(String.valueOf(hjzxfAllNums))).toString());
        jsonobject.put("zxfcjperson", (new StringBuilder(String.valueOf(hjzxfCjNum))).toString());
        if (hjzxfPaymoney > 0.0D) {
          jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
        } else {
          jsonobject.put("zxfpaymoney", "0.00");
        } 
        if (hjzxfJcfmoney > 0.0D) {
          jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
        } else {
          jsonobject.put("zxfjcfmoney", "0.00");
        } 
        if (hjzxfYjjmoney > 0.0D) {
          jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
        } else {
          jsonobject.put("zxfyjjmoney", "0.00");
        } 
        if (hjCmoney > 0.0D) {
          jsonobject.put("cmoney", nf1.format(hjCmoney));
        } else {
          jsonobject.put("cmoney", "0.00");
        } 
        if (hjTcmoney < 0.0D) {
          jsonobject.put("tcmoney", nf1.format(hjTcmoney));
        } else {
          jsonobject.put("tcmoney", "0.00");
        } 
        if (hjDrugsmoney > 0.0D) {
          jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
        } else {
          jsonobject.put("drugsmoney", "0.00");
        } 
        if (hjczAllNums > 0) {
          jsonobject.put("czcjratio", (new StringBuilder(String.valueOf(hjczCjNum / hjczAllNums))).toString());
        } else {
          jsonobject.put("czcjratio", "0");
        } 
        if (hjfzAllNums > 0) {
          jsonobject.put("fzcjratio", (new StringBuilder(String.valueOf(hjfzCjNum / hjfzAllNums))).toString());
        } else {
          jsonobject.put("fzcjratio", "0");
        } 
        if (hjzxfAllNums > 0) {
          jsonobject.put("zxfcjratio", (new StringBuilder(String.valueOf(hjzxfCjNum / hjzxfAllNums))).toString());
        } else {
          jsonobject.put("zxfcjratio", "0");
        } 
        jsonobject.put("xmpaymoney", Double.parseDouble(jsonobject.getString("czpaymoney")) + Double.parseDouble(jsonobject.getString("fzpaymoney")) + Double.parseDouble(jsonobject.getString("zxfpaymoney")));
        list.add(jsonobject);
      } 
    } 
    return list;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public List<JSONObject> findAllCJStatisticsByYear(HttpServletRequest request, Map<String, String> map) throws Exception {
    List<JSONObject> personlist = new ArrayList<>();
    String name = "";
    String askpersonId = "";
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all") && ((String)map.get("personId")).equals("all")) {
      personlist = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "' or ");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && ((String)map.get("personId")).equals("all")) {
      map.put("deptId", "'" + (String)map.get("deptCategory") + "'");
      personlist = this.sysDeptPrivDao.findPersonByDeptId(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "' or ");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!((String)map.get("personId")).equals("all")) {
      Map<String, String> map1 = new HashMap<>();
      map1.put("seqId", map.get("personId"));
      YZPerson yzPerson = this.sysDeptPrivDao.findPersonBySeqId(map1);
      name = yzPerson.getUserName();
      askpersonId = yzPerson.getSeqId();
      map.put("askperson", "r.askperson='" + (String)map.get("personId") + "'");
    } 
    String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
    map.put("recesort", "'" + czseqId + "'");
    List<JSONObject> czAllNums = this.analysisDao.findAllQuantityByAskpersonAndYear(map);
    List<JSONObject> czCjNum = this.analysisDao.findAllCJQuantityByAskpersonAndYear(map);
    List<JSONObject> czPaymoney = this.analysisDao.findAllPaymoneyByYear(map);
    String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
    map.put("recesort", "'" + fzseqId + "'");
    List<JSONObject> fzAllNums = this.analysisDao.findAllQuantityByAskpersonAndYear(map);
    List<JSONObject> fzCjNum = this.analysisDao.findAllCJQuantityByAskpersonAndYear(map);
    List<JSONObject> fzPaymoney = this.analysisDao.findAllPaymoneyByYear(map);
    List<JSONObject> Drugsmoney = this.analysisDao.findAllDrugsmoneyByYear(map);
    String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
    String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
    map.put("recesort", "'" + zxfseqId + "','" + fcseqId + "'");
    List<JSONObject> zxfAllNums = this.analysisDao.findAllQuantityByAskpersonAndYear(map);
    map.put("recesort", "'" + zxfseqId + "'");
    List<JSONObject> zxfCjNum = this.analysisDao.findAllCJQuantityByAskpersonAndYear(map);
    List<JSONObject> zxfPaymoney = this.analysisDao.findAllPaymoneyByYear(map);
    List<JSONObject> Cmoney = this.analysisDao.findAllCmoneyByYear(map);
    String startyear = map.get("startyear");
    String endyear = map.get("endyear");
    List<JSONObject> list = new ArrayList<>();
    int hjczAllNums = 0;
    int hjczCjNum = 0;
    double hjczPaymoney = 0.0D;
    double hjczJcfmoney = 0.0D;
    double hjczYjjmoney = 0.0D;
    int hjfzAllNums = 0;
    int hjfzCjNum = 0;
    double hjfzPaymoney = 0.0D;
    double hjfzJcfmoney = 0.0D;
    double hjfzYjjmoney = 0.0D;
    int hjzxfAllNums = 0;
    int hjzxfCjNum = 0;
    double hjzxfPaymoney = 0.0D;
    double hjzxfJcfmoney = 0.0D;
    double hjzxfYjjmoney = 0.0D;
    double hjDrugsmoney = 0.0D;
    double hjcmoney = 0.0D;
    double hjtcmoney = 0.0D;
    if (name.equals("")) {
      for (JSONObject jsonObject : personlist) {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        int f = 0;
        int g = 0;
        int h = 0;
        int j = 0;
        int k1 = 0;
        int k2 = 0;
        int l = 0;
        JSONObject json = new JSONObject();
        if (!startyear.equals(endyear)) {
          json.put("month", "-");
        } else {
          json.put("month", String.valueOf(startyear) + "年");
        } 
        json.put("name", jsonObject.getString("username"));
        if (czAllNums.size() > 0)
          for (JSONObject jsonObject2 : czAllNums) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              a = 1;
              json.put("czperson", jsonObject2.getString("nums"));
            } 
          }  
        if (a == 0)
          json.put("czperson", "0"); 
        if (czCjNum.size() > 0)
          for (JSONObject jsonObject2 : czCjNum) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              b = 1;
              json.put("czcjperson", jsonObject2.getString("nums"));
            } 
          }  
        if (b == 0)
          json.put("czcjperson", "0"); 
        if (Integer.parseInt(json.getString("czperson")) > 0) {
          json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
        } else {
          json.put("czcjratio", "0");
        } 
        if (czPaymoney.size() > 0)
          for (JSONObject jsonObject2 : czPaymoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              c = 1;
              json.put("czpaymoney", jsonObject2.getString("paymoney"));
              json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
              json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
            } 
          }  
        if (c == 0) {
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
        if (fzAllNums.size() > 0)
          for (JSONObject jsonObject2 : fzAllNums) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              d = 1;
              json.put("fzperson", jsonObject2.getString("nums"));
            } 
          }  
        if (d == 0)
          json.put("fzperson", "0"); 
        if (fzCjNum.size() > 0)
          for (JSONObject jsonObject2 : fzCjNum) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              e = 1;
              json.put("fzcjperson", jsonObject2.getString("nums"));
            } 
          }  
        if (e == 0)
          json.put("fzcjperson", "0"); 
        if (Integer.parseInt(json.getString("fzperson")) > 0) {
          json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
        } else {
          json.put("fzcjratio", "0");
        } 
        if (fzPaymoney.size() > 0)
          for (JSONObject jsonObject2 : fzPaymoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              f = 1;
              json.put("fzpaymoney", jsonObject2.getString("paymoney"));
              json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
              json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
            } 
          }  
        if (f == 0) {
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
        if (zxfAllNums.size() > 0)
          for (JSONObject jsonObject2 : zxfAllNums) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              g = 1;
              json.put("zxfperson", jsonObject2.getString("nums"));
            } 
          }  
        if (g == 0)
          json.put("zxfperson", "0"); 
        if (zxfCjNum.size() > 0)
          for (JSONObject jsonObject2 : zxfCjNum) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              h = 1;
              json.put("zxfcjperson", jsonObject2.getString("nums"));
            } 
          }  
        if (h == 0)
          json.put("zxfcjperson", "0"); 
        if (Integer.parseInt(json.getString("zxfperson")) > 0) {
          json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
        } else {
          json.put("zxfcjratio", "0");
        } 
        if (zxfPaymoney.size() > 0)
          for (JSONObject jsonObject2 : zxfPaymoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              j = 1;
              json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
              json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
              json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
            } 
          }  
        if (j == 0) {
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
        if (Cmoney.size() > 0)
          for (JSONObject jsonObject2 : Cmoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              if (jsonObject2.getString("type").equals("充值")) {
                k1 = 1;
                json.put("cmoney", jsonObject2.getString("cmoney"));
                continue;
              } 
              k2 = 1;
              json.put("tcmoney", jsonObject2.getString("cmoney"));
            } 
          }  
        if (k1 == 0)
          json.put("cmoney", "0.00"); 
        if (k2 == 0)
          json.put("tcmoney", "0.00"); 
        if (Drugsmoney.size() > 0)
          for (JSONObject jsonObject2 : Drugsmoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              l = 1;
              json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
            } 
          }  
        if (l == 0)
          json.put("drugsmoney", "0.00"); 
        json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
        hjczAllNums += Integer.parseInt(json.getString("czperson"));
        hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
        hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
        hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
        hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
        hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
        hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
        hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
        hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
        hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
        hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
        hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
        hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
        hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
        hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
        hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
        hjcmoney += Double.parseDouble(json.getString("cmoney"));
        hjtcmoney += Double.parseDouble(json.getString("tcmoney"));
        list.add(json);
      } 
    } else {
      JSONObject json = new JSONObject();
      if (startyear.equals(endyear)) {
        json.put("month", "-");
      } else {
        json.put("month", String.valueOf(startyear) + "年");
      } 
      json.put("name", name);
      if (czAllNums.size() > 0) {
        for (JSONObject jsonObject2 : czAllNums) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("czperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("czperson", "0");
        } 
      } else {
        json.put("czperson", "0");
      } 
      if (czCjNum.size() > 0) {
        for (JSONObject jsonObject2 : czCjNum) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("czcjperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("czcjperson", "0");
        } 
      } else {
        json.put("czcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("czperson")) > 0) {
        json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
      } else {
        json.put("czcjratio", "0");
      } 
      if (czPaymoney.size() > 0) {
        for (JSONObject jsonObject2 : czPaymoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("czpaymoney", jsonObject2.getString("paymoney"));
            json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
            json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
            continue;
          } 
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
      } else {
        json.put("czpaymoney", "0.00");
        json.put("czjcfmoney", "0.00");
        json.put("czyjjmoney", "0.00");
      } 
      if (fzAllNums.size() > 0) {
        for (JSONObject jsonObject2 : fzAllNums) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("fzperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("fzperson", "0");
        } 
      } else {
        json.put("fzperson", "0");
      } 
      if (fzCjNum.size() > 0) {
        for (JSONObject jsonObject2 : fzCjNum) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("fzcjperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("fzcjperson", "0");
        } 
      } else {
        json.put("fzcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("fzperson")) > 0) {
        json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
      } else {
        json.put("fzcjratio", "0");
      } 
      if (fzPaymoney.size() > 0) {
        for (JSONObject jsonObject2 : fzPaymoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("fzpaymoney", jsonObject2.getString("paymoney"));
            json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
            json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
            continue;
          } 
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
      } else {
        json.put("fzpaymoney", "0.00");
        json.put("fzjcfmoney", "0.00");
        json.put("fzyjjmoney", "0.00");
      } 
      if (zxfAllNums.size() > 0) {
        for (JSONObject jsonObject2 : zxfAllNums) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("zxfperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("zxfperson", "0");
        } 
      } else {
        json.put("zxfperson", "0");
      } 
      if (zxfCjNum.size() > 0) {
        for (JSONObject jsonObject2 : zxfCjNum) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("zxfcjperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("zxfcjperson", "0");
        } 
      } else {
        json.put("zxfcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("zxfperson")) > 0) {
        json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
      } else {
        json.put("zxfcjratio", "0");
      } 
      if (zxfPaymoney.size() > 0) {
        for (JSONObject jsonObject2 : zxfPaymoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
            json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
            json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
            continue;
          } 
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
      } else {
        json.put("zxfpaymoney", "0.00");
        json.put("zxfjcfmoney", "0.00");
        json.put("zxfyjjmoney", "0.00");
      } 
      if (Cmoney.size() > 0) {
        for (JSONObject jsonObject2 : Cmoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            if (jsonObject2.getString("type").equals("充值"))
              json.put("cmoney", jsonObject2.getString("cmoney")); 
            continue;
          } 
          json.put("cmoney", "0.00");
        } 
        for (JSONObject jsonObject2 : Cmoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            if (jsonObject2.getString("type").equals("退费"))
              json.put("tcmoney", jsonObject2.getString("cmoney")); 
            continue;
          } 
          json.put("tcmoney", "0.00");
        } 
      } else {
        json.put("cmoney", "0.00");
        json.put("tcmoney", "0.00");
      } 
      if (Drugsmoney.size() > 0) {
        for (JSONObject jsonObject2 : Drugsmoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
            continue;
          } 
          json.put("drugsmoney", "0.00");
        } 
      } else {
        json.put("drugsmoney", "0.00");
      } 
      json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
      hjczAllNums += Integer.parseInt(json.getString("czperson"));
      hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
      hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
      hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
      hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
      hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
      hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
      hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
      hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
      hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
      hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
      hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
      hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
      hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
      hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
      hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
      hjcmoney += Double.parseDouble(json.getString("cmoney"));
      hjtcmoney += Double.parseDouble(json.getString("tcmoney"));
      list.add(json);
    } 
    JSONObject jsonobject = new JSONObject();
    if (startyear.equals(endyear)) {
      jsonobject.put("month", "-");
    } else {
      jsonobject.put("month", String.valueOf(startyear) + "年");
    } 
    jsonobject.put("name", "合计");
    jsonobject.put("czperson", (new StringBuilder(String.valueOf(hjczAllNums))).toString());
    jsonobject.put("czcjperson", (new StringBuilder(String.valueOf(hjczCjNum))).toString());
    NumberFormat nf1 = NumberFormat.getInstance();
    nf1.setGroupingUsed(false);
    if (hjczPaymoney > 0.0D) {
      jsonobject.put("czpaymoney", nf1.format(hjczPaymoney));
    } else {
      jsonobject.put("czpaymoney", "0.00");
    } 
    if (hjczJcfmoney > 0.0D) {
      jsonobject.put("czjcfmoney", nf1.format(hjczJcfmoney));
    } else {
      jsonobject.put("czjcfmoney", "0.00");
    } 
    if (hjczYjjmoney > 0.0D) {
      jsonobject.put("czyjjmoney", nf1.format(hjczYjjmoney));
    } else {
      jsonobject.put("czyjjmoney", "0.00");
    } 
    jsonobject.put("fzperson", (new StringBuilder(String.valueOf(hjfzAllNums))).toString());
    jsonobject.put("fzcjperson", (new StringBuilder(String.valueOf(hjfzCjNum))).toString());
    if (hjfzPaymoney > 0.0D) {
      jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
    } else {
      jsonobject.put("fzpaymoney", "0.00");
    } 
    if (hjfzJcfmoney > 0.0D) {
      jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
    } else {
      jsonobject.put("fzjcfmoney", "0.00");
    } 
    if (hjfzYjjmoney > 0.0D) {
      jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
    } else {
      jsonobject.put("fzyjjmoney", "0.00");
    } 
    jsonobject.put("zxfperson", (new StringBuilder(String.valueOf(hjzxfAllNums))).toString());
    jsonobject.put("zxfcjperson", (new StringBuilder(String.valueOf(hjzxfCjNum))).toString());
    if (hjzxfPaymoney > 0.0D) {
      jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
    } else {
      jsonobject.put("zxfpaymoney", "0.00");
    } 
    if (hjzxfJcfmoney > 0.0D) {
      jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
    } else {
      jsonobject.put("zxfjcfmoney", "0.00");
    } 
    if (hjzxfYjjmoney > 0.0D) {
      jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
    } else {
      jsonobject.put("zxfyjjmoney", "0.00");
    } 
    if (hjcmoney > 0.0D) {
      jsonobject.put("cmoney", nf1.format(hjcmoney));
    } else {
      jsonobject.put("cmoney", "0.00");
    } 
    if (hjtcmoney < 0.0D) {
      jsonobject.put("tcmoney", nf1.format(hjtcmoney));
    } else {
      jsonobject.put("tcmoney", "0.00");
    } 
    if (hjDrugsmoney > 0.0D) {
      jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
    } else {
      jsonobject.put("drugsmoney", "0.00");
    } 
    if (hjczAllNums > 0) {
      jsonobject.put("czcjratio", (new StringBuilder(String.valueOf(hjczCjNum / hjczAllNums))).toString());
    } else {
      jsonobject.put("czcjratio", "0");
    } 
    if (hjfzAllNums > 0) {
      jsonobject.put("fzcjratio", (new StringBuilder(String.valueOf(hjfzCjNum / hjfzAllNums))).toString());
    } else {
      jsonobject.put("fzcjratio", "0");
    } 
    if (hjzxfAllNums > 0) {
      jsonobject.put("zxfcjratio", (new StringBuilder(String.valueOf(hjzxfCjNum / hjzxfAllNums))).toString());
    } else {
      jsonobject.put("zxfcjratio", "0");
    } 
    jsonobject.put("xmpaymoney", Double.parseDouble(jsonobject.getString("czpaymoney")) + Double.parseDouble(jsonobject.getString("fzpaymoney")) + Double.parseDouble(jsonobject.getString("zxfpaymoney")));
    list.add(jsonobject);
    return list;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public List<JSONObject> findAllCJStatisticsByMonth(HttpServletRequest request, Map<String, String> map) throws Exception {
    List<JSONObject> personlist = new ArrayList<>();
    String name = "";
    String askpersonId = "";
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all") && ((String)map.get("personId")).equals("all")) {
      personlist = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "' or ");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && ((String)map.get("personId")).equals("all")) {
      map.put("deptId", "'" + (String)map.get("deptCategory") + "'");
      personlist = this.sysDeptPrivDao.findPersonByDeptId(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "' or ");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!((String)map.get("personId")).equals("all")) {
      Map<String, String> map1 = new HashMap<>();
      map1.put("seqId", map.get("personId"));
      YZPerson yzPerson = this.sysDeptPrivDao.findPersonBySeqId(map1);
      name = yzPerson.getUserName();
      askpersonId = yzPerson.getSeqId();
      map.put("askperson", "r.askperson='" + (String)map.get("personId") + "'");
    } 
    String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
    map.put("recesort", "'" + czseqId + "'");
    List<JSONObject> czAllNums = this.analysisDao.findAllQuantityByAskpersonAndMonth(map);
    List<JSONObject> czCjNum = this.analysisDao.findAllCJQuantityByAskpersonAndMonth(map);
    List<JSONObject> czPaymoney = this.analysisDao.findAllPaymoneyByMonth(map);
    String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
    map.put("recesort", "'" + fzseqId + "'");
    List<JSONObject> fzAllNums = this.analysisDao.findAllQuantityByAskpersonAndMonth(map);
    List<JSONObject> fzCjNum = this.analysisDao.findAllCJQuantityByAskpersonAndMonth(map);
    List<JSONObject> fzPaymoney = this.analysisDao.findAllPaymoneyByMonth(map);
    String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
    String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
    map.put("recesort", "'" + zxfseqId + "','" + fcseqId + "'");
    List<JSONObject> zxfAllNums = this.analysisDao.findAllQuantityByAskpersonAndMonth(map);
    map.put("recesort", "'" + zxfseqId + "'");
    List<JSONObject> zxfCjNum = this.analysisDao.findAllCJQuantityByAskpersonAndMonth(map);
    List<JSONObject> zxfPaymoney = this.analysisDao.findAllPaymoneyByMonth(map);
    List<JSONObject> Cmoney = this.analysisDao.findAllCmoneyByMonth(map);
    List<JSONObject> Drugsmoney = this.analysisDao.findAllDrugsmoneyByMonth(map);
    List<JSONObject> list = new ArrayList<>();
    int hjczAllNums = 0;
    int hjczCjNum = 0;
    double hjczPaymoney = 0.0D;
    double hjczJcfmoney = 0.0D;
    double hjczYjjmoney = 0.0D;
    int hjfzAllNums = 0;
    int hjfzCjNum = 0;
    double hjfzPaymoney = 0.0D;
    double hjfzJcfmoney = 0.0D;
    double hjfzYjjmoney = 0.0D;
    int hjzxfAllNums = 0;
    int hjzxfCjNum = 0;
    double hjzxfPaymoney = 0.0D;
    double hjzxfJcfmoney = 0.0D;
    double hjzxfYjjmoney = 0.0D;
    double hjcmoney = 0.0D;
    double hjtcmoney = 0.0D;
    double hjDrugsmoney = 0.0D;
    if (name.equals("")) {
      for (JSONObject jsonObject : personlist) {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        int f = 0;
        int g = 0;
        int h = 0;
        int j = 0;
        int k1 = 0;
        int k2 = 0;
        int l = 0;
        JSONObject json = new JSONObject();
        if (!((String)map.get("startmonth")).equals(map.get("endmonth"))) {
          json.put("month", "-");
        } else {
          json.put("month", String.valueOf(map.get("year")) + "年" + (String)map.get("startmonth") + "月");
        } 
        json.put("name", jsonObject.getString("username"));
        if (czAllNums.size() > 0)
          for (JSONObject jsonObject2 : czAllNums) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              a = 1;
              json.put("czperson", jsonObject2.getString("nums"));
            } 
          }  
        if (a == 0)
          json.put("czperson", "0"); 
        if (czCjNum.size() > 0)
          for (JSONObject jsonObject2 : czCjNum) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              b = 1;
              json.put("czcjperson", jsonObject2.getString("nums"));
            } 
          }  
        if (b == 0)
          json.put("czcjperson", "0"); 
        if (Integer.parseInt(json.getString("czperson")) > 0) {
          json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
        } else {
          json.put("czcjratio", "0");
        } 
        if (czPaymoney.size() > 0)
          for (JSONObject jsonObject2 : czPaymoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              c = 1;
              json.put("czpaymoney", jsonObject2.getString("paymoney"));
              json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
              json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
            } 
          }  
        if (c == 0) {
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
        if (fzAllNums.size() > 0)
          for (JSONObject jsonObject2 : fzAllNums) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              d = 1;
              json.put("fzperson", jsonObject2.getString("nums"));
            } 
          }  
        if (d == 0)
          json.put("fzperson", "0"); 
        if (fzCjNum.size() > 0)
          for (JSONObject jsonObject2 : fzCjNum) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              e = 1;
              json.put("fzcjperson", jsonObject2.getString("nums"));
            } 
          }  
        if (e == 0)
          json.put("fzcjperson", "0"); 
        if (Integer.parseInt(json.getString("fzperson")) > 0) {
          json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
        } else {
          json.put("fzcjratio", "0");
        } 
        if (fzPaymoney.size() > 0)
          for (JSONObject jsonObject2 : fzPaymoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              f = 1;
              json.put("fzpaymoney", jsonObject2.getString("paymoney"));
              json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
              json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
            } 
          }  
        if (f == 0) {
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
        if (zxfAllNums.size() > 0)
          for (JSONObject jsonObject2 : zxfAllNums) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              g = 1;
              json.put("zxfperson", jsonObject2.getString("nums"));
            } 
          }  
        if (g == 0)
          json.put("zxfperson", "0"); 
        if (zxfCjNum.size() > 0)
          for (JSONObject jsonObject2 : zxfCjNum) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              h = 1;
              json.put("zxfcjperson", jsonObject2.getString("nums"));
            } 
          }  
        if (h == 0)
          json.put("zxfcjperson", "0"); 
        if (Integer.parseInt(json.getString("zxfperson")) > 0) {
          json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
        } else {
          json.put("zxfcjratio", "0");
        } 
        if (zxfPaymoney.size() > 0)
          for (JSONObject jsonObject2 : zxfPaymoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              j = 1;
              json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
              json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
              json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
            } 
          }  
        if (j == 0) {
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
        if (Cmoney.size() > 0)
          for (JSONObject jsonObject2 : Cmoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              if (jsonObject2.getString("type").equals("充值")) {
                k1 = 1;
                json.put("cmoney", jsonObject2.getString("cmoney"));
                continue;
              } 
              k2 = 1;
              json.put("tcmoney", jsonObject2.getString("cmoney"));
            } 
          }  
        if (k1 == 0)
          json.put("cmoney", "0.00"); 
        if (k2 == 0)
          json.put("tcmoney", "0.00"); 
        if (Drugsmoney.size() > 0)
          for (JSONObject jsonObject2 : Drugsmoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              l = 1;
              json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
            } 
          }  
        if (l == 0)
          json.put("drugsmoney", "0.00"); 
        json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
        hjczAllNums += Integer.parseInt(json.getString("czperson"));
        hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
        hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
        hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
        hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
        hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
        hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
        hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
        hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
        hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
        hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
        hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
        hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
        hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
        hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
        hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
        hjcmoney += Double.parseDouble(json.getString("cmoney"));
        hjtcmoney += Double.parseDouble(json.getString("tcmoney"));
        list.add(json);
      } 
    } else {
      JSONObject json = new JSONObject();
      if (!((String)map.get("startmonth")).equals(map.get("endmonth"))) {
        json.put("month", "-");
      } else {
        json.put("month", String.valueOf(map.get("year")) + "年" + (String)map.get("startmonth") + "月");
      } 
      json.put("name", name);
      if (czAllNums.size() > 0) {
        for (JSONObject jsonObject2 : czAllNums) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("czperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("czperson", "0");
        } 
      } else {
        json.put("czperson", "0");
      } 
      if (czCjNum.size() > 0) {
        for (JSONObject jsonObject2 : czCjNum) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("czcjperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("czcjperson", "0");
        } 
      } else {
        json.put("czcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("czperson")) > 0) {
        json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
      } else {
        json.put("czcjratio", "0");
      } 
      if (czPaymoney.size() > 0) {
        for (JSONObject jsonObject2 : czPaymoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("czpaymoney", jsonObject2.getString("paymoney"));
            json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
            json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
            continue;
          } 
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
      } else {
        json.put("czpaymoney", "0.00");
        json.put("czjcfmoney", "0.00");
        json.put("czyjjmoney", "0.00");
      } 
      if (fzAllNums.size() > 0) {
        for (JSONObject jsonObject2 : fzAllNums) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("fzperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("fzperson", "0");
        } 
      } else {
        json.put("fzperson", "0");
      } 
      if (fzCjNum.size() > 0) {
        for (JSONObject jsonObject2 : fzCjNum) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("fzcjperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("fzcjperson", "0");
        } 
      } else {
        json.put("fzcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("fzperson")) > 0) {
        json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
      } else {
        json.put("fzcjratio", "0");
      } 
      if (fzPaymoney.size() > 0) {
        for (JSONObject jsonObject2 : fzPaymoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("fzpaymoney", jsonObject2.getString("paymoney"));
            json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
            json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
            continue;
          } 
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
      } else {
        json.put("fzpaymoney", "0.00");
        json.put("fzjcfmoney", "0.00");
        json.put("fzyjjmoney", "0.00");
      } 
      if (zxfAllNums.size() > 0) {
        for (JSONObject jsonObject2 : zxfAllNums) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("zxfperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("zxfperson", "0");
        } 
      } else {
        json.put("zxfperson", "0");
      } 
      if (zxfCjNum.size() > 0) {
        for (JSONObject jsonObject2 : zxfCjNum) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("zxfcjperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("zxfcjperson", "0");
        } 
      } else {
        json.put("zxfcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("zxfperson")) > 0) {
        json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
      } else {
        json.put("zxfcjratio", "0");
      } 
      if (zxfPaymoney.size() > 0) {
        for (JSONObject jsonObject2 : zxfPaymoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
            json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
            json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
            continue;
          } 
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
      } else {
        json.put("zxfpaymoney", "0.00");
        json.put("zxfjcfmoney", "0.00");
        json.put("zxfyjjmoney", "0.00");
      } 
      if (Cmoney.size() > 0) {
        for (JSONObject jsonObject2 : Cmoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            if (jsonObject2.getString("type").equals("充值"))
              json.put("cmoney", jsonObject2.getString("cmoney")); 
            continue;
          } 
          json.put("cmoney", "0.00");
        } 
        for (JSONObject jsonObject2 : Cmoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            if (jsonObject2.getString("type").equals("退费"))
              json.put("tcmoney", jsonObject2.getString("cmoney")); 
            continue;
          } 
          json.put("tcmoney", "0.00");
        } 
      } else {
        json.put("cmoney", "0.00");
        json.put("tcmoney", "0.00");
      } 
      if (Drugsmoney.size() > 0) {
        for (JSONObject jsonObject2 : Drugsmoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
            continue;
          } 
          json.put("drugsmoney", "0.00");
        } 
      } else {
        json.put("drugsmoney", "0.00");
      } 
      json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
      hjczAllNums += Integer.parseInt(json.getString("czperson"));
      hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
      hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
      hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
      hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
      hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
      hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
      hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
      hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
      hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
      hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
      hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
      hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
      hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
      hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
      hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
      hjcmoney += Double.parseDouble(json.getString("cmoney"));
      hjtcmoney += Double.parseDouble(json.getString("tcmoney"));
      list.add(json);
    } 
    JSONObject jsonobject = new JSONObject();
    if (!((String)map.get("startmonth")).equals(map.get("endmonth"))) {
      jsonobject.put("month", "-");
    } else {
      jsonobject.put("month", String.valueOf(map.get("year")) + "年" + (String)map.get("startmonth") + "月");
    } 
    jsonobject.put("name", "合计");
    jsonobject.put("czperson", (new StringBuilder(String.valueOf(hjczAllNums))).toString());
    jsonobject.put("czcjperson", (new StringBuilder(String.valueOf(hjczCjNum))).toString());
    NumberFormat nf1 = NumberFormat.getInstance();
    nf1.setGroupingUsed(false);
    if (hjczPaymoney > 0.0D) {
      jsonobject.put("czpaymoney", nf1.format(hjczPaymoney));
    } else {
      jsonobject.put("czpaymoney", "0.00");
    } 
    if (hjczJcfmoney > 0.0D) {
      jsonobject.put("czjcfmoney", nf1.format(hjczJcfmoney));
    } else {
      jsonobject.put("czjcfmoney", "0.00");
    } 
    if (hjczYjjmoney > 0.0D) {
      jsonobject.put("czyjjmoney", nf1.format(hjczYjjmoney));
    } else {
      jsonobject.put("czyjjmoney", "0.00");
    } 
    jsonobject.put("fzperson", (new StringBuilder(String.valueOf(hjfzAllNums))).toString());
    jsonobject.put("fzcjperson", (new StringBuilder(String.valueOf(hjfzCjNum))).toString());
    if (hjfzPaymoney > 0.0D) {
      jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
    } else {
      jsonobject.put("fzpaymoney", "0.00");
    } 
    if (hjfzJcfmoney > 0.0D) {
      jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
    } else {
      jsonobject.put("fzjcfmoney", "0.00");
    } 
    if (hjfzYjjmoney > 0.0D) {
      jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
    } else {
      jsonobject.put("fzyjjmoney", "0.00");
    } 
    jsonobject.put("zxfperson", (new StringBuilder(String.valueOf(hjzxfAllNums))).toString());
    jsonobject.put("zxfcjperson", (new StringBuilder(String.valueOf(hjzxfCjNum))).toString());
    if (hjzxfPaymoney > 0.0D) {
      jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
    } else {
      jsonobject.put("zxfpaymoney", "0.00");
    } 
    if (hjzxfJcfmoney > 0.0D) {
      jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
    } else {
      jsonobject.put("zxfjcfmoney", "0.00");
    } 
    if (hjzxfYjjmoney > 0.0D) {
      jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
    } else {
      jsonobject.put("zxfyjjmoney", "0.00");
    } 
    if (hjcmoney > 0.0D) {
      jsonobject.put("cmoney", nf1.format(hjcmoney));
    } else {
      jsonobject.put("cmoney", "0.00");
    } 
    if (hjtcmoney < 0.0D) {
      jsonobject.put("tcmoney", nf1.format(hjtcmoney));
    } else {
      jsonobject.put("tcmoney", "0.00");
    } 
    if (hjDrugsmoney > 0.0D) {
      jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
    } else {
      jsonobject.put("drugsmoney", "0.00");
    } 
    if (hjczAllNums > 0) {
      jsonobject.put("czcjratio", (new StringBuilder(String.valueOf(hjczCjNum / hjczAllNums))).toString());
    } else {
      jsonobject.put("czcjratio", "0");
    } 
    if (hjfzAllNums > 0) {
      jsonobject.put("fzcjratio", (new StringBuilder(String.valueOf(hjfzCjNum / hjfzAllNums))).toString());
    } else {
      jsonobject.put("fzcjratio", "0");
    } 
    if (hjzxfAllNums > 0) {
      jsonobject.put("zxfcjratio", (new StringBuilder(String.valueOf(hjzxfCjNum / hjzxfAllNums))).toString());
    } else {
      jsonobject.put("zxfcjratio", "0");
    } 
    jsonobject.put("xmpaymoney", Double.parseDouble(jsonobject.getString("czpaymoney")) + Double.parseDouble(jsonobject.getString("fzpaymoney")) + Double.parseDouble(jsonobject.getString("zxfpaymoney")));
    list.add(jsonobject);
    return list;
  }
  
  public List<JSONObject> findAllCJStatisticsByDay(HttpServletRequest request, Map<String, String> map) throws Exception {
    List<JSONObject> personlist = new ArrayList<>();
    String name = "";
    String askpersonId = "";
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all") && ((String)map.get("personId")).equals("all")) {
      personlist = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "' or ");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && ((String)map.get("personId")).equals("all")) {
      map.put("deptId", "'" + (String)map.get("deptCategory") + "'");
      personlist = this.sysDeptPrivDao.findPersonByDeptId(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("r.askperson='" + ((JSONObject)personlist.get(i)).getString("seqid") + "' or ");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!((String)map.get("personId")).equals("all")) {
      Map<String, String> map1 = new HashMap<>();
      map1.put("seqId", map.get("personId"));
      YZPerson yzPerson = this.sysDeptPrivDao.findPersonBySeqId(map1);
      name = yzPerson.getUserName();
      askpersonId = yzPerson.getSeqId();
      map.put("askperson", "r.askperson='" + (String)map.get("personId") + "'");
    } 
    String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
    map.put("recesort", "'" + czseqId + "'");
    List<JSONObject> czAllNums = this.analysisDao.findAllQuantityByAskpersonAndDay(map);
    List<JSONObject> czCjNum = this.analysisDao.findAllCJQuantityByAskpersonAndDay(map);
    List<JSONObject> czPaymoney = this.analysisDao.findAllPaymoneyByDay(map);
    String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
    map.put("recesort", "'" + fzseqId + "'");
    List<JSONObject> fzAllNums = this.analysisDao.findAllQuantityByAskpersonAndDay(map);
    List<JSONObject> fzCjNum = this.analysisDao.findAllCJQuantityByAskpersonAndDay(map);
    List<JSONObject> fzPaymoney = this.analysisDao.findAllPaymoneyByDay(map);
    String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
    String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
    map.put("recesort", "'" + zxfseqId + "','" + fcseqId + "'");
    List<JSONObject> zxfAllNums = this.analysisDao.findAllQuantityByAskpersonAndDay(map);
    map.put("recesort", "'" + zxfseqId + "'");
    List<JSONObject> zxfCjNum = this.analysisDao.findAllCJQuantityByAskpersonAndDay(map);
    List<JSONObject> zxfPaymoney = this.analysisDao.findAllPaymoneyByDay(map);
    List<JSONObject> Cmoney = this.analysisDao.findAllCmoneyByDay(map);
    List<JSONObject> Drugsmoney = this.analysisDao.findAllDrugsmoneyByDay(map);
    List<JSONObject> list = new ArrayList<>();
    int hjczAllNums = 0;
    int hjczCjNum = 0;
    double hjczPaymoney = 0.0D;
    double hjczJcfmoney = 0.0D;
    double hjczYjjmoney = 0.0D;
    int hjfzAllNums = 0;
    int hjfzCjNum = 0;
    double hjfzPaymoney = 0.0D;
    double hjfzJcfmoney = 0.0D;
    double hjfzYjjmoney = 0.0D;
    int hjzxfAllNums = 0;
    int hjzxfCjNum = 0;
    double hjzxfPaymoney = 0.0D;
    double hjzxfJcfmoney = 0.0D;
    double hjzxfYjjmoney = 0.0D;
    double hjDrugsmoney = 0.0D;
    double hjcmoney = 0.0D;
    double hjtcmoney = 0.0D;
    if (name.equals("")) {
      for (JSONObject jsonObject : personlist) {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        int f = 0;
        int g = 0;
        int h = 0;
        int j = 0;
        int k1 = 0;
        int k2 = 0;
        int l = 0;
        JSONObject json = new JSONObject();
        if (((String)map.get("startday")).equals(map.get("endday"))) {
          json.put("month", String.valueOf(map.get("year")) + "年" + (String)map.get("month") + "月" + (String)map.get("startday") + "日");
        } else {
          json.put("month", "-");
        } 
        json.put("name", jsonObject.getString("username"));
        if (czAllNums.size() > 0)
          for (JSONObject jsonObject2 : czAllNums) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              a = 1;
              json.put("czperson", jsonObject2.getString("nums"));
            } 
          }  
        if (a == 0)
          json.put("czperson", "0"); 
        if (czCjNum.size() > 0)
          for (JSONObject jsonObject2 : czCjNum) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              b = 1;
              json.put("czcjperson", jsonObject2.getString("nums"));
            } 
          }  
        if (b == 0)
          json.put("czcjperson", "0"); 
        if (Integer.parseInt(json.getString("czperson")) > 0) {
          json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
        } else {
          json.put("czcjratio", "0");
        } 
        if (czPaymoney.size() > 0)
          for (JSONObject jsonObject2 : czPaymoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              c = 1;
              json.put("czpaymoney", jsonObject2.getString("paymoney"));
              json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
              json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
            } 
          }  
        if (c == 0) {
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
        if (fzAllNums.size() > 0)
          for (JSONObject jsonObject2 : fzAllNums) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              d = 1;
              json.put("fzperson", jsonObject2.getString("nums"));
            } 
          }  
        if (d == 0)
          json.put("fzperson", "0"); 
        if (fzCjNum.size() > 0)
          for (JSONObject jsonObject2 : fzCjNum) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              e = 1;
              json.put("fzcjperson", jsonObject2.getString("nums"));
            } 
          }  
        if (e == 0)
          json.put("fzcjperson", "0"); 
        if (Integer.parseInt(json.getString("fzperson")) > 0) {
          json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
        } else {
          json.put("fzcjratio", "0");
        } 
        if (fzPaymoney.size() > 0)
          for (JSONObject jsonObject2 : fzPaymoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              f = 1;
              json.put("fzpaymoney", jsonObject2.getString("paymoney"));
              json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
              json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
            } 
          }  
        if (f == 0) {
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
        if (zxfAllNums.size() > 0)
          for (JSONObject jsonObject2 : zxfAllNums) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              g = 1;
              json.put("zxfperson", jsonObject2.getString("nums"));
            } 
          }  
        if (g == 0)
          json.put("zxfperson", "0"); 
        if (zxfCjNum.size() > 0)
          for (JSONObject jsonObject2 : zxfCjNum) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))) {
              h = 1;
              json.put("zxfcjperson", jsonObject2.getString("nums"));
            } 
          }  
        if (h == 0)
          json.put("zxfcjperson", "0"); 
        if (Integer.parseInt(json.getString("zxfperson")) > 0) {
          json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
        } else {
          json.put("zxfcjratio", "0");
        } 
        if (zxfPaymoney.size() > 0)
          for (JSONObject jsonObject2 : zxfPaymoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              j = 1;
              json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
              json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
              json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
            } 
          }  
        if (j == 0) {
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
        if (Cmoney.size() > 0)
          for (JSONObject jsonObject2 : Cmoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              if (jsonObject2.getString("type").equals("充值")) {
                k1 = 1;
                json.put("cmoney", jsonObject2.getString("cmoney"));
                continue;
              } 
              k2 = 1;
              json.put("tcmoney", jsonObject2.getString("cmoney"));
            } 
          }  
        if (k1 == 0)
          json.put("cmoney", "0.00"); 
        if (k2 == 0)
          json.put("tcmoney", "0.00"); 
        if (Drugsmoney.size() > 0)
          for (JSONObject jsonObject2 : Drugsmoney) {
            if (jsonObject.getString("seqid").equals(jsonObject2.getString("askperson"))) {
              l = 1;
              json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
            } 
          }  
        if (l == 0)
          json.put("drugsmoney", "0.00"); 
        json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
        hjczAllNums += Integer.parseInt(json.getString("czperson"));
        hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
        hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
        hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
        hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
        hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
        hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
        hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
        hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
        hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
        hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
        hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
        hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
        hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
        hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
        hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
        hjcmoney += Double.parseDouble(json.getString("cmoney"));
        hjtcmoney += Double.parseDouble(json.getString("tcmoney"));
        list.add(json);
      } 
    } else {
      JSONObject json = new JSONObject();
      if (((String)map.get("startday")).equals(map.get("endday"))) {
        json.put("month", String.valueOf(map.get("year")) + "年" + (String)map.get("month") + "月" + (String)map.get("startday") + "日");
      } else {
        json.put("month", "-");
      } 
      json.put("name", name);
      if (czAllNums.size() > 0) {
        for (JSONObject jsonObject2 : czAllNums) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("czperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("czperson", "0");
        } 
      } else {
        json.put("czperson", "0");
      } 
      if (czCjNum.size() > 0) {
        for (JSONObject jsonObject2 : czCjNum) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("czcjperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("czcjperson", "0");
        } 
      } else {
        json.put("czcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("czperson")) > 0) {
        json.put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("czcjperson")) / Double.parseDouble(json.getString("czperson"))))).toString());
      } else {
        json.put("czcjratio", "0");
      } 
      if (czPaymoney.size() > 0) {
        for (JSONObject jsonObject2 : czPaymoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("czpaymoney", jsonObject2.getString("paymoney"));
            json.put("czjcfmoney", jsonObject2.getString("jcfmoney"));
            json.put("czyjjmoney", jsonObject2.getString("yjjmoney"));
            continue;
          } 
          json.put("czpaymoney", "0.00");
          json.put("czjcfmoney", "0.00");
          json.put("czyjjmoney", "0.00");
        } 
      } else {
        json.put("czpaymoney", "0.00");
        json.put("czjcfmoney", "0.00");
        json.put("czyjjmoney", "0.00");
      } 
      if (fzAllNums.size() > 0) {
        for (JSONObject jsonObject2 : fzAllNums) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("fzperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("fzperson", "0");
        } 
      } else {
        json.put("fzperson", "0");
      } 
      if (fzCjNum.size() > 0) {
        for (JSONObject jsonObject2 : fzCjNum) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("fzcjperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("fzcjperson", "0");
        } 
      } else {
        json.put("fzcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("fzperson")) > 0) {
        json.put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("fzcjperson")) / Double.parseDouble(json.getString("fzperson"))))).toString());
      } else {
        json.put("fzcjratio", "0");
      } 
      if (fzPaymoney.size() > 0) {
        for (JSONObject jsonObject2 : fzPaymoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("fzpaymoney", jsonObject2.getString("paymoney"));
            json.put("fzjcfmoney", jsonObject2.getString("jcfmoney"));
            json.put("fzyjjmoney", jsonObject2.getString("yjjmoney"));
            continue;
          } 
          json.put("fzpaymoney", "0.00");
          json.put("fzjcfmoney", "0.00");
          json.put("fzyjjmoney", "0.00");
        } 
      } else {
        json.put("fzpaymoney", "0.00");
        json.put("fzjcfmoney", "0.00");
        json.put("fzyjjmoney", "0.00");
      } 
      if (zxfAllNums.size() > 0) {
        for (JSONObject jsonObject2 : zxfAllNums) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("zxfperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("zxfperson", "0");
        } 
      } else {
        json.put("zxfperson", "0");
      } 
      if (zxfCjNum.size() > 0) {
        for (JSONObject jsonObject2 : zxfCjNum) {
          if (askpersonId.equals(jsonObject2.getString("seqid"))) {
            json.put("zxfcjperson", jsonObject2.getString("nums"));
            continue;
          } 
          json.put("zxfcjperson", "0");
        } 
      } else {
        json.put("zxfcjperson", "0");
      } 
      if (Integer.parseInt(json.getString("zxfperson")) > 0) {
        json.put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(json.getString("zxfcjperson")) / Double.parseDouble(json.getString("zxfperson"))))).toString());
      } else {
        json.put("zxfcjratio", "0");
      } 
      if (zxfPaymoney.size() > 0) {
        for (JSONObject jsonObject2 : zxfPaymoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("zxfpaymoney", jsonObject2.getString("paymoney"));
            json.put("zxfjcfmoney", jsonObject2.getString("jcfmoney"));
            json.put("zxfyjjmoney", jsonObject2.getString("yjjmoney"));
            continue;
          } 
          json.put("zxfpaymoney", "0.00");
          json.put("zxfjcfmoney", "0.00");
          json.put("zxfyjjmoney", "0.00");
        } 
      } else {
        json.put("zxfpaymoney", "0.00");
        json.put("zxfjcfmoney", "0.00");
        json.put("zxfyjjmoney", "0.00");
      } 
      if (Cmoney.size() > 0) {
        for (JSONObject jsonObject2 : Cmoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            if (jsonObject2.getString("type").equals("充值"))
              json.put("cmoney", jsonObject2.getString("cmoney")); 
            continue;
          } 
          json.put("cmoney", "0.00");
        } 
        for (JSONObject jsonObject2 : Cmoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            if (jsonObject2.getString("type").equals("退费"))
              json.put("tcmoney", jsonObject2.getString("cmoney")); 
            continue;
          } 
          json.put("tcmoney", "0.00");
        } 
      } else {
        json.put("cmoney", "0.00");
        json.put("tcmoney", "0.00");
      } 
      if (Drugsmoney.size() > 0) {
        for (JSONObject jsonObject2 : Drugsmoney) {
          if (askpersonId.equals(jsonObject2.getString("askperson"))) {
            json.put("drugsmoney", jsonObject2.getString("drugsmoney"));
            continue;
          } 
          json.put("drugsmoney", "0.00");
        } 
      } else {
        json.put("drugsmoney", "0.00");
      } 
      json.put("xmpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
      hjczAllNums += Integer.parseInt(json.getString("czperson"));
      hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
      hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
      hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
      hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
      hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
      hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
      hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
      hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
      hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
      hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
      hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
      hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
      hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
      hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
      hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
      hjcmoney += Double.parseDouble(json.getString("cmoney"));
      hjtcmoney += Double.parseDouble(json.getString("tcmoney"));
      list.add(json);
    } 
    JSONObject jsonobject = new JSONObject();
    if (((String)map.get("startday")).equals(map.get("endday"))) {
      jsonobject.put("month", String.valueOf(map.get("year")) + "年" + (String)map.get("month") + "月" + (String)map.get("startday") + "日");
    } else {
      jsonobject.put("month", "-");
    } 
    jsonobject.put("name", "合计");
    jsonobject.put("czperson", (new StringBuilder(String.valueOf(hjczAllNums))).toString());
    jsonobject.put("czcjperson", (new StringBuilder(String.valueOf(hjczCjNum))).toString());
    NumberFormat nf1 = NumberFormat.getInstance();
    nf1.setGroupingUsed(false);
    if (hjczPaymoney > 0.0D) {
      jsonobject.put("czpaymoney", nf1.format(hjczPaymoney));
    } else {
      jsonobject.put("czpaymoney", "0.00");
    } 
    if (hjczJcfmoney > 0.0D) {
      jsonobject.put("czjcfmoney", nf1.format(hjczJcfmoney));
    } else {
      jsonobject.put("czjcfmoney", "0.00");
    } 
    if (hjczYjjmoney > 0.0D) {
      jsonobject.put("czyjjmoney", nf1.format(hjczYjjmoney));
    } else {
      jsonobject.put("czyjjmoney", "0.00");
    } 
    jsonobject.put("fzperson", (new StringBuilder(String.valueOf(hjfzAllNums))).toString());
    jsonobject.put("fzcjperson", (new StringBuilder(String.valueOf(hjfzCjNum))).toString());
    if (hjfzPaymoney > 0.0D) {
      jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
    } else {
      jsonobject.put("fzpaymoney", "0.00");
    } 
    if (hjfzJcfmoney > 0.0D) {
      jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
    } else {
      jsonobject.put("fzjcfmoney", "0.00");
    } 
    if (hjfzYjjmoney > 0.0D) {
      jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
    } else {
      jsonobject.put("fzyjjmoney", "0.00");
    } 
    jsonobject.put("zxfperson", (new StringBuilder(String.valueOf(hjzxfAllNums))).toString());
    jsonobject.put("zxfcjperson", (new StringBuilder(String.valueOf(hjzxfCjNum))).toString());
    if (hjzxfPaymoney > 0.0D) {
      jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
    } else {
      jsonobject.put("zxfpaymoney", "0.00");
    } 
    if (hjzxfJcfmoney > 0.0D) {
      jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
    } else {
      jsonobject.put("zxfjcfmoney", "0.00");
    } 
    if (hjzxfYjjmoney > 0.0D) {
      jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
    } else {
      jsonobject.put("zxfyjjmoney", "0.00");
    } 
    if (hjcmoney > 0.0D) {
      jsonobject.put("cmoney", nf1.format(hjcmoney));
    } else {
      jsonobject.put("cmoney", "0.00");
    } 
    if (hjtcmoney < 0.0D) {
      jsonobject.put("tcmoney", nf1.format(hjtcmoney));
    } else {
      jsonobject.put("tcmoney", "0.00");
    } 
    if (hjDrugsmoney > 0.0D) {
      jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
    } else {
      jsonobject.put("drugsmoney", "0.00");
    } 
    if (hjczAllNums > 0) {
      jsonobject.put("czcjratio", (new StringBuilder(String.valueOf(hjczCjNum / hjczAllNums))).toString());
    } else {
      jsonobject.put("czcjratio", "0");
    } 
    if (hjfzAllNums > 0) {
      jsonobject.put("fzcjratio", (new StringBuilder(String.valueOf(hjfzCjNum / hjfzAllNums))).toString());
    } else {
      jsonobject.put("fzcjratio", "0");
    } 
    if (hjzxfAllNums > 0) {
      jsonobject.put("zxfcjratio", (new StringBuilder(String.valueOf(hjzxfCjNum / hjzxfAllNums))).toString());
    } else {
      jsonobject.put("zxfcjratio", "0");
    } 
    jsonobject.put("xmpaymoney", Double.parseDouble(jsonobject.getString("czpaymoney")) + Double.parseDouble(jsonobject.getString("fzpaymoney")) + Double.parseDouble(jsonobject.getString("zxfpaymoney")));
    list.add(jsonobject);
    return list;
  }
  
  public List<JSONObject> findTotalMoneyByMonth(Map<String, String> dataMap, HttpServletRequest request) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    if (!YZUtility.isNullorEmpty(dataMap.get("buttonName")) && ((String)dataMap.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(dataMap);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < personSeqIds.size(); j++) {
        if (j == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "',");
        } 
      } 
      dataMap.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(dataMap.get("buttonName")) && !((String)dataMap.get("deptCategory")).equals("all") && !((String)dataMap.get("deptCategory")).equals("personage")) {
      dataMap.put("dept_id", dataMap.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(dataMap);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < yzpersonSeqids.size(); j++) {
        if (j == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "',");
        } 
      } 
      dataMap.put("askperson", str.toString());
    } else if (YZUtility.isNullorEmpty(dataMap.get("buttonName")) && ((String)dataMap.get("deptCategory")).equals("personage")) {
      dataMap.put("askperson", "'" + person.getSeqId() + "'");
    } 
    List<JSONObject> list = new ArrayList<>();
    String startYear = dataMap.get("startTime");
    String endYear = dataMap.get("endTime");
    for (int i = Integer.parseInt(startYear); i <= Integer.parseInt(endYear); i++) {
      JSONObject json = new JSONObject();
      List<JSONObject> listTotalMoney = null;
      List<JSONObject> listTotalPrepay = null;
      List<JSONObject> listTotalRefund = null;
      List<JSONObject> listTotalDiscount = null;
      List<JSONObject> listTotalUsePrepay = null;
      List<JSONObject> listTotalRefundPrepay = null;
      List<JSONObject> listTotalDrugs = null;
      List<JSONObject> listTotalCheckItems = null;
      json.put("year", String.valueOf(i) + "年");
      json.put("name", person.getUserName());
      if ("zymoney".equals(dataMap.get("selectLabel"))) {
        dataMap.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(((String)dataMap.get("endTime")).substring(0, 4))))).toString());
        dataMap.put("startmonth", "1");
        dataMap.put("endmonth", "12");
        dataMap.put("years", "(year(detail.createtime)=" + i + ")");
        listTotalMoney = this.analysisDao.findTotalMoneyByMonth(dataMap);
        if (listTotalMoney != null && listTotalMoney.size() == 12) {
          json.put("selectType", "总业绩");
          double totalYearMoney = 0.0D;
          for (int j = 0; j < listTotalMoney.size(); j++) {
            JSONObject o = listTotalMoney.get(j);
            json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
            totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else if (listTotalMoney.size() < 12 && listTotalMoney.size() > 0) {
          json.put("selectType", "总业绩");
          double totalYearMoney = 0.0D;
          int a = 0;
          for (int j = 1; j < 13; j++) {
            if (a >= listTotalMoney.size()) {
              json.put("totalMoney" + j, "0.00");
            } else {
              int k = a;
              if (k < listTotalMoney.size())
                if (((JSONObject)listTotalMoney.get(k)).get("月").equals((new StringBuilder(String.valueOf(j))).toString())) {
                  JSONObject o = listTotalMoney.get(k);
                  json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
                  totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
                  a++;
                } else {
                  json.put("totalMoney" + j, "0.00");
                }  
            } 
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else {
          json.put("selectType", "总业绩");
          json.put("totalMoney1", "0.00");
          json.put("totalMoney2", "0.00");
          json.put("totalMoney3", "0.00");
          json.put("totalMoney4", "0.00");
          json.put("totalMoney5", "0.00");
          json.put("totalMoney6", "0.00");
          json.put("totalMoney7", "0.00");
          json.put("totalMoney8", "0.00");
          json.put("totalMoney9", "0.00");
          json.put("totalMoney10", "0.00");
          json.put("totalMoney11", "0.00");
          json.put("totalMoney12", "0.00");
          json.put("totalYearMoney", "0.00");
        } 
      } else if ("zmoney".equals(dataMap.get("selectLabel"))) {
        dataMap.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(((String)dataMap.get("endTime")).substring(0, 4))))).toString());
        dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + Integer.parseInt(dataMap.get("endTime")) + " then 1 else 0 end),0) as '" + Integer.parseInt(dataMap.get("endTime")) + "月'");
        dataMap.put("startmonth", "1");
        dataMap.put("endmonth", "12");
        dataMap.put("years", "(year(detail.createtime)=" + i + ")");
        listTotalPrepay = this.analysisDao.findTotalPrepayByMonth(dataMap);
        if (listTotalPrepay != null && listTotalPrepay.size() == 12) {
          json.put("selectType", "总预交金");
          double totalYearMoney = 0.0D;
          for (int j = 0; j < listTotalPrepay.size(); j++) {
            JSONObject o = listTotalPrepay.get(j);
            json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
            totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else if (listTotalPrepay.size() < 12 && listTotalPrepay.size() > 0) {
          json.put("selectType", "总预交金");
          double totalYearMoney = 0.0D;
          int a = 0;
          for (int j = 1; j < 13; j++) {
            if (a >= listTotalPrepay.size()) {
              json.put("totalMoney" + j, "0.00");
            } else {
              int k = a;
              if (k < listTotalPrepay.size())
                if (((JSONObject)listTotalPrepay.get(k)).get("月").equals((new StringBuilder(String.valueOf(j))).toString())) {
                  JSONObject o = listTotalPrepay.get(k);
                  json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
                  totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
                  a++;
                } else {
                  json.put("totalMoney" + j, "0.00");
                }  
            } 
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else {
          json.put("selectType", "总预交金");
          json.put("totalMoney1", "0.00");
          json.put("totalMoney2", "0.00");
          json.put("totalMoney3", "0.00");
          json.put("totalMoney4", "0.00");
          json.put("totalMoney5", "0.00");
          json.put("totalMoney6", "0.00");
          json.put("totalMoney7", "0.00");
          json.put("totalMoney8", "0.00");
          json.put("totalMoney9", "0.00");
          json.put("totalMoney10", "0.00");
          json.put("totalMoney11", "0.00");
          json.put("totalMoney12", "0.00");
          json.put("totalYearMoney", "0.00");
        } 
      } else if ("tkmoney".equals(dataMap.get("selectLabel"))) {
        dataMap.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(((String)dataMap.get("endTime")).substring(0, 4))))).toString());
        dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + Integer.parseInt(dataMap.get("endTime")) + " then 1 else 0 end),0) as '" + Integer.parseInt(dataMap.get("endTime")) + "月'");
        dataMap.put("startmonth", "1");
        dataMap.put("endmonth", "12");
        dataMap.put("years", "(year(createtime)=" + i + ")");
        listTotalRefund = this.analysisDao.findTotalRefundByMonth(dataMap);
        if (listTotalRefund != null && listTotalRefund.size() == 12) {
          json.put("selectType", "退费");
          double totalYearMoney = 0.0D;
          for (int j = 0; j < listTotalRefund.size(); j++) {
            JSONObject o = listTotalRefund.get(j);
            json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
            totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else if (listTotalRefund.size() < 12 && listTotalRefund.size() > 0) {
          json.put("selectType", "退费");
          double totalYearMoney = 0.0D;
          int a = 0;
          for (int j = 1; j < 13; j++) {
            if (a >= listTotalRefund.size()) {
              json.put("totalMoney" + j, "0.00");
            } else {
              int k = a;
              if (k < listTotalRefund.size())
                if (((JSONObject)listTotalRefund.get(k)).get("月").equals((new StringBuilder(String.valueOf(j))).toString())) {
                  JSONObject o = listTotalRefund.get(k);
                  json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
                  totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
                  a++;
                } else {
                  json.put("totalMoney" + j, "0.00");
                }  
            } 
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else {
          json.put("selectType", "退费");
          json.put("totalMoney1", "0.00");
          json.put("totalMoney2", "0.00");
          json.put("totalMoney3", "0.00");
          json.put("totalMoney4", "0.00");
          json.put("totalMoney5", "0.00");
          json.put("totalMoney6", "0.00");
          json.put("totalMoney7", "0.00");
          json.put("totalMoney8", "0.00");
          json.put("totalMoney9", "0.00");
          json.put("totalMoney10", "0.00");
          json.put("totalMoney11", "0.00");
          json.put("totalMoney12", "0.00");
          json.put("totalYearMoney", "0.00");
        } 
      } else if ("jmdzmoney".equals(dataMap.get("selectLabel"))) {
        dataMap.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(((String)dataMap.get("endTime")).substring(0, 4))))).toString());
        dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + Integer.parseInt(dataMap.get("endTime")) + " then 1 else 0 end),0) as '" + Integer.parseInt(dataMap.get("endTime")) + "月'");
        dataMap.put("startmonth", "1");
        dataMap.put("endmonth", "12");
        dataMap.put("years", "(year(createtime)=" + i + ")");
        listTotalDiscount = this.analysisDao.findTotalDiscountByMonth(dataMap);
        if (listTotalDiscount != null && listTotalDiscount.size() == 12) {
          json.put("selectType", "减免及折扣额");
          double totalYearMoney = 0.0D;
          for (int j = 0; j < listTotalDiscount.size(); j++) {
            JSONObject o = listTotalDiscount.get(j);
            json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
            totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else if (listTotalDiscount.size() < 12 && listTotalDiscount.size() > 0) {
          json.put("selectType", "减免及折扣额");
          double totalYearMoney = 0.0D;
          int a = 0;
          for (int j = 1; j < 13; j++) {
            if (a >= listTotalDiscount.size()) {
              json.put("totalMoney" + j, "0.00");
            } else {
              int k = a;
              if (k < listTotalDiscount.size())
                if (((JSONObject)listTotalDiscount.get(k)).get("月").equals((new StringBuilder(String.valueOf(j))).toString())) {
                  JSONObject o = listTotalDiscount.get(k);
                  json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
                  totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
                  a++;
                } else {
                  json.put("totalMoney" + j, "0.00");
                }  
            } 
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else {
          json.put("selectType", "减免及折扣额");
          json.put("totalMoney1", "0.00");
          json.put("totalMoney2", "0.00");
          json.put("totalMoney3", "0.00");
          json.put("totalMoney4", "0.00");
          json.put("totalMoney5", "0.00");
          json.put("totalMoney6", "0.00");
          json.put("totalMoney7", "0.00");
          json.put("totalMoney8", "0.00");
          json.put("totalMoney9", "0.00");
          json.put("totalMoney10", "0.00");
          json.put("totalMoney11", "0.00");
          json.put("totalMoney12", "0.00");
          json.put("totalYearMoney", "0.00");
        } 
      } else if ("symoney".equals(dataMap.get("selectLabel"))) {
        dataMap.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(((String)dataMap.get("endTime")).substring(0, 4))))).toString());
        dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + Integer.parseInt(dataMap.get("endTime")) + " then 1 else 0 end),0) as '" + Integer.parseInt(dataMap.get("endTime")) + "月'");
        dataMap.put("startmonth", "1");
        dataMap.put("endmonth", "12");
        dataMap.put("years", "(year(detail.createtime)=" + i + ")");
        listTotalUsePrepay = this.analysisDao.findTotalUsePrepayByMonth(dataMap);
        if (listTotalUsePrepay != null && listTotalUsePrepay.size() == 12) {
          json.put("selectType", "使用预交金");
          double totalYearMoney = 0.0D;
          for (int j = 0; j < listTotalUsePrepay.size(); j++) {
            JSONObject o = listTotalUsePrepay.get(j);
            json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
            totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else if (listTotalUsePrepay.size() < 12 && listTotalUsePrepay.size() > 0) {
          json.put("selectType", "使用预交金");
          double totalYearMoney = 0.0D;
          int a = 0;
          for (int j = 1; j < 13; j++) {
            if (a >= listTotalUsePrepay.size()) {
              json.put("totalMoney" + j, "0.00");
            } else {
              int k = a;
              if (k < listTotalUsePrepay.size())
                if (((JSONObject)listTotalUsePrepay.get(k)).get("月").equals((new StringBuilder(String.valueOf(j))).toString())) {
                  JSONObject o = listTotalUsePrepay.get(k);
                  json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
                  totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
                  a++;
                } else {
                  json.put("totalMoney" + j, "0.00");
                }  
            } 
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else {
          json.put("selectType", "使用预交金");
          json.put("totalMoney1", "0.00");
          json.put("totalMoney2", "0.00");
          json.put("totalMoney3", "0.00");
          json.put("totalMoney4", "0.00");
          json.put("totalMoney5", "0.00");
          json.put("totalMoney6", "0.00");
          json.put("totalMoney7", "0.00");
          json.put("totalMoney8", "0.00");
          json.put("totalMoney9", "0.00");
          json.put("totalMoney10", "0.00");
          json.put("totalMoney11", "0.00");
          json.put("totalMoney12", "0.00");
          json.put("totalYearMoney", "0.00");
        } 
      } else if ("tfmoney".equals(dataMap.get("selectLabel"))) {
        dataMap.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(((String)dataMap.get("endTime")).substring(0, 4))))).toString());
        dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + Integer.parseInt(dataMap.get("endTime")) + " then 1 else 0 end),0) as '" + Integer.parseInt(dataMap.get("endTime")) + "月'");
        dataMap.put("startmonth", "1");
        dataMap.put("endmonth", "12");
        dataMap.put("years", "(year(createtime)=" + i + ")");
        listTotalRefundPrepay = this.analysisDao.findTotalRefundPrepayByMonth(dataMap);
        if (listTotalRefundPrepay != null && listTotalRefundPrepay.size() == 12) {
          json.put("selectType", "退费预交金");
          double totalYearMoney = 0.0D;
          for (int j = 0; j < listTotalRefundPrepay.size(); j++) {
            JSONObject o = listTotalRefundPrepay.get(j);
            json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
            totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney < 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else if (listTotalRefundPrepay.size() < 12 && listTotalRefundPrepay.size() > 0) {
          json.put("selectType", "退费预交金");
          double totalYearMoney = 0.0D;
          int a = 0;
          for (int j = 1; j < 13; j++) {
            if (a >= listTotalRefundPrepay.size()) {
              json.put("totalMoney" + j, "0.00");
            } else {
              int k = a;
              if (k < listTotalRefundPrepay.size())
                if (((JSONObject)listTotalRefundPrepay.get(k)).get("月").equals((new StringBuilder(String.valueOf(j))).toString())) {
                  JSONObject o = listTotalRefundPrepay.get(k);
                  json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
                  totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
                  a++;
                } else {
                  json.put("totalMoney" + j, "0.00");
                }  
            } 
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney < 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else {
          json.put("selectType", "退费预交金");
          json.put("totalMoney1", "0.00");
          json.put("totalMoney2", "0.00");
          json.put("totalMoney3", "0.00");
          json.put("totalMoney4", "0.00");
          json.put("totalMoney5", "0.00");
          json.put("totalMoney6", "0.00");
          json.put("totalMoney7", "0.00");
          json.put("totalMoney8", "0.00");
          json.put("totalMoney9", "0.00");
          json.put("totalMoney10", "0.00");
          json.put("totalMoney11", "0.00");
          json.put("totalMoney12", "0.00");
          json.put("totalYearMoney", "0.00");
        } 
      } else if ("ypmoney".equals(dataMap.get("selectLabel"))) {
        dataMap.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(((String)dataMap.get("endTime")).substring(0, 4))))).toString());
        dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + Integer.parseInt(dataMap.get("endTime")) + " then 1 else 0 end),0) as '" + Integer.parseInt(dataMap.get("endTime")) + "月'");
        dataMap.put("startmonth", "1");
        dataMap.put("endmonth", "12");
        dataMap.put("years", "(year(detail.createtime)=" + i + ")");
        listTotalDrugs = this.analysisDao.findTotalDrugsByMonth(dataMap);
        if (listTotalDrugs != null && listTotalDrugs.size() == 12) {
          json.put("selectType", "药品");
          double totalYearMoney = 0.0D;
          for (int j = 0; j < listTotalDrugs.size(); j++) {
            JSONObject o = listTotalDrugs.get(j);
            json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
            totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else if (listTotalDrugs.size() < 12 && listTotalDrugs.size() > 0) {
          json.put("selectType", "药品");
          double totalYearMoney = 0.0D;
          int a = 0;
          for (int j = 1; j < 13; j++) {
            if (a >= listTotalDrugs.size()) {
              json.put("totalMoney" + j, "0.00");
            } else {
              int k = a;
              if (k < listTotalDrugs.size())
                if (((JSONObject)listTotalDrugs.get(k)).get("月").equals((new StringBuilder(String.valueOf(j))).toString())) {
                  JSONObject o = listTotalDrugs.get(k);
                  json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
                  totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
                  a++;
                } else {
                  json.put("totalMoney" + j, "0.00");
                }  
            } 
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else {
          json.put("selectType", "药品");
          json.put("totalMoney1", "0.00");
          json.put("totalMoney2", "0.00");
          json.put("totalMoney3", "0.00");
          json.put("totalMoney4", "0.00");
          json.put("totalMoney5", "0.00");
          json.put("totalMoney6", "0.00");
          json.put("totalMoney7", "0.00");
          json.put("totalMoney8", "0.00");
          json.put("totalMoney9", "0.00");
          json.put("totalMoney10", "0.00");
          json.put("totalMoney11", "0.00");
          json.put("totalMoney12", "0.00");
          json.put("totalYearMoney", "0.00");
        } 
      } else if ("jcxmmoney".equals(dataMap.get("selectLabel"))) {
        dataMap.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(((String)dataMap.get("endTime")).substring(0, 4))))).toString());
        dataMap.put("code", "ISNULL(sum(case when datepart(month,createtime)=" + Integer.parseInt(dataMap.get("endTime")) + " then 1 else 0 end),0) as '" + Integer.parseInt(dataMap.get("endTime")) + "月'");
        dataMap.put("startmonth", "1");
        dataMap.put("endmonth", "12");
        dataMap.put("years", "(year(detail.createtime)=" + i + ")");
        listTotalCheckItems = this.analysisDao.findTotalCheckItemsByMonth(dataMap);
        if (listTotalCheckItems != null && listTotalCheckItems.size() == 12) {
          json.put("selectType", "检查项目");
          double totalYearMoney = 0.0D;
          for (int j = 0; j < listTotalCheckItems.size(); j++) {
            JSONObject o = listTotalCheckItems.get(j);
            json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
            totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else if (listTotalCheckItems.size() < 12 && listTotalCheckItems.size() > 0) {
          json.put("selectType", "检查项目");
          double totalYearMoney = 0.0D;
          int a = 0;
          for (int j = 1; j < 13; j++) {
            if (a >= listTotalCheckItems.size()) {
              json.put("totalMoney" + j, "0.00");
            } else {
              int k = a;
              if (k < listTotalCheckItems.size())
                if (((JSONObject)listTotalCheckItems.get(k)).get("月").equals((new StringBuilder(String.valueOf(j))).toString())) {
                  JSONObject o = listTotalCheckItems.get(k);
                  json.put("totalMoney" + o.getString("月"), o.getString("totalmoney"));
                  totalYearMoney += Double.parseDouble(o.getString("totalmoney"));
                  a++;
                } else {
                  json.put("totalMoney" + j, "0.00");
                }  
            } 
          } 
          NumberFormat nf = NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          if (totalYearMoney > 0.0D) {
            json.put("totalYearMoney", nf.format(totalYearMoney));
          } else {
            json.put("totalYearMoney", "0.00");
          } 
        } else {
          json.put("selectType", "检查项目");
          json.put("totalMoney1", "0.00");
          json.put("totalMoney2", "0.00");
          json.put("totalMoney3", "0.00");
          json.put("totalMoney4", "0.00");
          json.put("totalMoney5", "0.00");
          json.put("totalMoney6", "0.00");
          json.put("totalMoney7", "0.00");
          json.put("totalMoney8", "0.00");
          json.put("totalMoney9", "0.00");
          json.put("totalMoney10", "0.00");
          json.put("totalMoney11", "0.00");
          json.put("totalMoney12", "0.00");
          json.put("totalYearMoney", "0.00");
        } 
      } 
      list.add(json);
    } 
    return list;
  }
  
  public List<JSONObject> findCjsCale(HttpServletRequest request, Map<String, String> map) throws Exception {
    String startmonth = map.get("startmonth");
    String endmonth = map.get("endmonth");
    String year = map.get("year");
    YZPerson person = SessionUtil.getLoginPerson(request);
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < personSeqIds.size(); j++) {
        if (j == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < yzpersonSeqids.size(); j++) {
        if (j == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("personage")) {
      map.put("askperson", "'" + person.getSeqId() + "'");
    } 
    List<JSONObject> jsonPerformance = this.kDetailLogic.findZperformance(map);
    List<JSONObject> jsonDiscount = this.kpayLogic.discount(map);
    List<JSONObject> jsonAccepting = this.kRecordLogic.findAcceptingGold(map);
    List<JSONObject> jsTk = this.kRefundLogic.tkQuery(map);
    List<JSONObject> list = new ArrayList<>();
    for (int i = Integer.parseInt(startmonth); i <= Integer.parseInt(endmonth); i++) {
      int a = 0;
      JSONObject obj = new JSONObject();
      for (JSONObject object : jsonPerformance) {
        if (object != null) {
          if (Integer.valueOf(object.getString("month")).intValue() == i && object.getString("performance") != null && !object.getString("performance").equals("")) {
            a = 1;
            obj.put("month", String.valueOf(object.getString("year")) + "年" + object.getString("month") + "月");
            obj.put("performance", object.getString("performance"));
          } 
          if (a == 0) {
            obj.put("month", String.valueOf(year) + "年" + i + "月");
            obj.put("performance", "0.00");
          } 
        } 
      } 
      for (JSONObject object1 : jsonDiscount) {
        if (object1 != null) {
          if (Integer.valueOf(object1.getString("month")).intValue() == i && object1.getString("discount") != null && !object1.getString("discount").equals("")) {
            a = 1;
            obj.put("month", String.valueOf(object1.getString("year")) + "年" + object1.getString("month") + "月");
            obj.put("discount", object1.getString("discount"));
          } 
          if (a == 0) {
            obj.put("month", String.valueOf(year) + "年" + i + "月");
            obj.put("discount", "0.00");
          } 
        } 
      } 
      for (JSONObject object3 : jsonAccepting) {
        if (object3 != null) {
          if (Integer.valueOf(object3.getString("monthes")).intValue() == i && object3.getString("money") != null && !object3.getString("money").equals("")) {
            a = 1;
            obj.put("month", String.valueOf(object3.getString("yeares")) + "年" + object3.getString("monthes") + "月");
            obj.put("money", object3.getString("money"));
          } 
          if (a == 0) {
            obj.put("month", String.valueOf(year) + "年" + i + "月");
            obj.put("money", "0.00");
          } 
        } 
      } 
      for (JSONObject object2 : jsTk) {
        if (object2 != null) {
          if (jsonDiscount.size() <= 0) {
            obj.put("month", String.valueOf(year) + "年" + i + "月");
            obj.put("discount", "0.00");
          } 
          if (jsonPerformance.size() <= 0) {
            obj.put("month", String.valueOf(year) + "年" + i + "月");
            obj.put("performance", "0.00");
          } 
          if (Integer.valueOf(object2.getString("month")).intValue() == i && object2.getString("tkmoney") != null && !object2.getString("tkmoney").equals("")) {
            a = 1;
            obj.put("month", String.valueOf(object2.getString("year")) + "年" + object2.getString("month") + "月");
            obj.put("tkMoney", object2.getString("tkmoney"));
          } 
          if (a == 0) {
            obj.put("month", String.valueOf(year) + "年" + i + "月");
            obj.put("tkMoney", "0.00");
          } 
          obj.put("yjMoney", "0.00");
        } 
      } 
      list.add(obj);
    } 
    return list;
  }
  
  public List<JSONObject> findDepartment(HttpServletRequest request, Map<String, String> map) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personSeqIds.size(); i++) {
        if (i == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < yzpersonSeqids.size(); i++) {
        if (i == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("personage")) {
      map.put("askperson", "'" + person.getSeqId() + "'");
    } 
    List<JSONObject> jsonDepartment = this.krLogic.findDepartment(map);
    JSONObject jsonZperformance = this.krLogic.findZperformance(map);
    List<JSONObject> list = new ArrayList<>();
    NumberFormat percent = NumberFormat.getPercentInstance();
    percent.setMinimumFractionDigits(3);
    for (JSONObject jsonObject : jsonDepartment) {
      JSONObject obj = new JSONObject();
      if (jsonObject.getString("performance") != null && !jsonObject.getString("performance").equals("")) {
        obj.put("name", jsonObject.getString("deptname"));
        obj.put("department", jsonObject.getString("performance"));
        obj.put("proportion", percent.format(Double.valueOf(jsonObject.getString("performance")).doubleValue() / Double.valueOf(jsonZperformance.getString("zperformance")).doubleValue()));
      } else {
        obj.put("name", jsonObject.getString("deptname"));
        obj.put("department", "0.00");
        obj.put("proportion", "0.00%");
      } 
      list.add(obj);
    } 
    return list;
  }
  
  public List<JSONObject> findImplant(HttpServletRequest request, Map<String, String> map) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personSeqIds.size(); i++) {
        if (i == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < yzpersonSeqids.size(); i++) {
        if (i == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("personage")) {
      map.put("askperson", "'" + person.getSeqId() + "'");
    } 
    List<JSONObject> ImplantNum = this.krLogic.findImplantNum(map);
    JSONObject ImplantNumall = this.krLogic.findImplantNumAll(map);
    int nums = 0;
    if (ImplantNumall.getString("nums") != null && !ImplantNumall.getString("nums").equals(""))
      nums = Integer.valueOf(ImplantNumall.getString("nums")).intValue(); 
    NumberFormat percent = NumberFormat.getPercentInstance();
    percent.setMinimumFractionDigits(3);
    List<JSONObject> list = new ArrayList<>();
    for (JSONObject jsonObject : ImplantNum) {
      JSONObject json = new JSONObject();
      if (jsonObject != null) {
        json.put("ImplantName", jsonObject.getString("implantname"));
        json.put("ImplantNum", jsonObject.getString("nums"));
        int num = Integer.valueOf(jsonObject.getString("nums")).intValue();
        if (nums > 0) {
          json.put("proportion", percent.format(num / nums));
        } else {
          json.put("proportion", "0.00");
        } 
      } 
      list.add(json);
    } 
    return list;
  }
  
  public List<JSONObject> finddoctor(HttpServletRequest request, Map<String, String> map) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personSeqIds.size(); i++) {
        if (i == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < yzpersonSeqids.size(); i++) {
        if (i == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("personage")) {
      map.put("askperson", "'" + person.getSeqId() + "'");
    } 
    List<JSONObject> jsonDepartment = this.krLogic.findDoctor(map);
    JSONObject jsonZperformance = this.krLogic.findZdoctor(map);
    NumberFormat percent = NumberFormat.getPercentInstance();
    percent.setMinimumFractionDigits(3);
    List<JSONObject> list = new ArrayList<>();
    for (JSONObject jsonObject : jsonDepartment) {
      JSONObject obj = new JSONObject();
      if (jsonObject.getString("paymoney") != null && !jsonObject.getString("paymoney").equals("")) {
        obj.put("name", jsonObject.getString("doctor"));
        obj.put("doctor", jsonObject.getString("paymoney"));
        obj.put("zdoctor", percent.format(Double.valueOf(jsonObject.getString("paymoney")).doubleValue() / Double.valueOf(jsonZperformance.getString("paymoney")).doubleValue()));
      } else {
        obj.put("name", jsonObject.getString("doctor"));
        obj.put("doctor", "0");
        obj.put("zdoctor", "0.00%");
      } 
      list.add(obj);
    } 
    return list;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public List<JSONObject> findCJQuantityByAskpersonAndMonthInYear(HttpServletRequest request, Map<String, String> map) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String name = "";
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < personSeqIds.size(); j++) {
        if (j == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "',");
        } 
      } 
      map.put("visualstaff", str.toString());
      name = "所有部门";
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < yzpersonSeqids.size(); j++) {
        if (j == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "',");
        } 
      } 
      map.put("visualstaff", str.toString());
      String deptname = this.yzDeptLogic.getDeptNamesBySeqIds(map.get("deptCategory"));
      name = deptname;
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("visualstaff", "'" + person.getSeqId() + "'");
      name = person.getUserName();
    } 
    String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
    map.put("recesort", czseqId);
    JSONObject czAllNums = this.analysisDao.findQuantityByAskpersonAndMonthInYear(map);
    JSONObject czCjNum = this.analysisDao.findCJQuantityByAskpersonAndMonthInYear(map);
    List<JSONObject> czPaymoney = this.analysisDao.findPaymoneyByMonth(map);
    String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
    map.put("recesort", fzseqId);
    JSONObject fzAllNums = this.analysisDao.findQuantityByAskpersonAndMonthInYear(map);
    JSONObject fzCjNum = this.analysisDao.findCJQuantityByAskpersonAndMonthInYear(map);
    List<JSONObject> fzPaymoney = this.analysisDao.findPaymoneyByMonth(map);
    String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
    String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
    map.put("recesort", "'" + zxfseqId + "','" + fcseqId + "'");
    JSONObject zxfAllNums = this.analysisDao.findQuantityByAskpersonAndMonthInYear(map);
    map.put("recesort", "'" + zxfseqId + "'");
    JSONObject zxfCjNum = this.analysisDao.findCJQuantityByAskpersonAndMonthInYear(map);
    List<JSONObject> zxfPaymoney = this.analysisDao.findPaymoneyByMonth(map);
    List<JSONObject> list = new ArrayList<>();
    int paymoneyMark1 = 0;
    int paymoneyMark2 = 0;
    int paymoneyMark3 = 0;
    for (int i = Integer.parseInt(map.get("startmonth")); i <= Integer.parseInt((String)map.get("endmonth")); i++) {
      JSONObject json = new JSONObject();
      json.put("day", String.valueOf(i) + "月");
      if (czAllNums != null) {
        String nums = czAllNums.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("czperson", nums); 
      } else {
        json.put("czperson", "0");
      } 
      if (czCjNum != null) {
        String nums = czCjNum.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("czcjperson", nums); 
      } else {
        json.put("czcjperson", "0");
      } 
      if (czPaymoney != null && czPaymoney.size() > paymoneyMark1) {
        JSONObject nums = czPaymoney.get(paymoneyMark1);
        if (nums.getString("年").equals(map.get("year"))) {
          if (nums.getString("月").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("czpaymoney", paymoney); 
            paymoneyMark1++;
          } else {
            json.put("czpaymoney", "0.00");
          } 
        } else {
          json.put("czpaymoney", "0.00");
        } 
      } else {
        json.put("czpaymoney", "0.00");
      } 
      if (fzAllNums != null) {
        String nums = fzAllNums.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("fzperson", nums); 
      } else {
        json.put("fzperson", "0");
      } 
      if (fzCjNum != null) {
        String nums = fzCjNum.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("fzcjperson", nums); 
      } else {
        json.put("fzcjperson", "0");
      } 
      if (fzPaymoney != null && fzPaymoney.size() > paymoneyMark2) {
        JSONObject nums = fzPaymoney.get(paymoneyMark2);
        if (nums.getString("年").equals(map.get("year"))) {
          if (nums.getString("月").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("fzpaymoney", paymoney); 
            paymoneyMark2++;
          } else {
            json.put("fzpaymoney", "0.00");
          } 
        } else {
          json.put("fzpaymoney", "0.00");
        } 
      } else {
        json.put("fzpaymoney", "0.00");
      } 
      if (zxfAllNums != null) {
        String nums = zxfAllNums.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("zxfperson", nums); 
      } else {
        json.put("zxfperson", "0");
      } 
      if (zxfCjNum != null) {
        String nums = zxfCjNum.getString(String.valueOf(i) + "月");
        if (nums != null && !nums.equals(""))
          json.put("zxfcjperson", nums); 
      } else {
        json.put("zxfcjperson", "0");
      } 
      if (zxfPaymoney != null && zxfPaymoney.size() > paymoneyMark3) {
        JSONObject nums = zxfPaymoney.get(paymoneyMark3);
        if (nums.getString("年").equals(map.get("year"))) {
          if (nums.getString("月").equals((new StringBuilder(String.valueOf(i))).toString())) {
            String paymoney = nums.getString("paymoney");
            if (paymoney != null && !paymoney.equals(""))
              json.put("zxfpaymoney", paymoney); 
            paymoneyMark3++;
          } else {
            json.put("zxfpaymoney", "0.00");
          } 
        } else {
          json.put("zxfpaymoney", "0.00");
        } 
      } else {
        json.put("zxfpaymoney", "0.00");
      } 
      json.put("zjpaymoney", Double.parseDouble(json.getString("czpaymoney")) + Double.parseDouble(json.getString("fzpaymoney")) + Double.parseDouble(json.getString("zxfpaymoney")));
      list.add(json);
    } 
    return list;
  }
  
  public List<JSONObject> Devchannel(HttpServletRequest request, Map<String, String> map) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personSeqIds.size(); i++) {
        if (i == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(i)).getString("seqid") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < yzpersonSeqids.size(); i++) {
        if (i == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(i)).getString("seq_id") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("personage")) {
      map.put("askperson", "'" + person.getSeqId() + "'");
    } 
    List<JSONObject> findDevchannel = this.kLogic.findDevchannel(map);
    JSONObject findDevchannelAll = this.kLogic.findDevchannelAll(map);
    List<JSONObject> list = new ArrayList<>();
    NumberFormat percent = NumberFormat.getPercentInstance();
    percent.setMinimumFractionDigits(3);
    for (JSONObject object : findDevchannel) {
      JSONObject obj = new JSONObject();
      if (object.get("paymoney") != null && !object.get("paymoney").equals("")) {
        obj.put("DevChannelName", object.getString("devchannelname"));
        obj.put("performance", object.getString("paymoney"));
        obj.put("proportion", percent.format(Double.valueOf(object.getString("paymoney")).doubleValue() / Double.valueOf(findDevchannelAll.getString("paymoney")).doubleValue()));
      } else {
        obj.put("DevChannelName", object.getString("devchannelname"));
        obj.put("performance", "0");
        obj.put("proportion", "0.00%");
      } 
      list.add(obj);
    } 
    return list;
  }
  
  public List<JSONObject> consumptionInterval(HttpServletRequest request, Map<String, String> map) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    if (!YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("all")) {
      List<JSONObject> personSeqIds = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < personSeqIds.size(); j++) {
        if (j == personSeqIds.size() - 1) {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personSeqIds.get(j)).getString("seqid") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (!YZUtility.isNullorEmpty(map.get("buttonName")) && !((String)map.get("deptCategory")).equals("all") && !((String)map.get("deptCategory")).equals("personage")) {
      map.put("dept_id", map.get("deptCategory"));
      List<JSONObject> yzpersonSeqids = this.yzPersonLogic.selectPersonPaiban(map);
      StringBuffer str = new StringBuffer();
      for (int j = 0; j < yzpersonSeqids.size(); j++) {
        if (j == yzpersonSeqids.size() - 1) {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "'");
        } else {
          str.append("'" + ((JSONObject)yzpersonSeqids.get(j)).getString("seq_id") + "',");
        } 
      } 
      map.put("askperson", str.toString());
    } else if (YZUtility.isNullorEmpty(map.get("buttonName")) && ((String)map.get("deptCategory")).equals("personage")) {
      map.put("askperson", "'" + person.getSeqId() + "'");
    } 
    List<JSONObject> list = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      if (i == 0) {
        map.put("start", "0");
        map.put("end", "5000");
        JSONObject json = this.kLogic.findConsumptionInterval(map);
        json.put("name", "0~5000");
        list.add(json);
      } 
      if (i == 1) {
        map.put("start", "5000");
        map.put("end", "20000");
        JSONObject json1 = this.kLogic.findConsumptionInterval(map);
        json1.put("name", "5000~20000");
        list.add(json1);
      } 
      if (i == 2) {
        map.put("start", "20000");
        map.put("end", "50000");
        JSONObject json2 = this.kLogic.findConsumptionInterval(map);
        json2.put("name", "20000~50000");
        list.add(json2);
      } 
      if (i == 3) {
        map.put("start", "50000");
        map.put("end", "100000");
        JSONObject json3 = this.kLogic.findConsumptionInterval(map);
        json3.put("name", "50000~100000");
        list.add(json3);
      } 
      if (i == 4) {
        map.put("start", "100000");
        map.put("end", "200000");
        JSONObject json4 = this.kLogic.findConsumptionInterval(map);
        json4.put("name", "100000~200000");
        list.add(json4);
      } 
      if (i == 5) {
        map.put("start", "200000");
        JSONObject json5 = this.kLogic.findConsumptionInterval(map);
        json5.put("name", "200000以上");
        list.add(json5);
      } 
    } 
    return list;
  }
}
