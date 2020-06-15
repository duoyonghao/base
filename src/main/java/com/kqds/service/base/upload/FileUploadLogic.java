package com.kqds.service.base.upload;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZPassEncrypt;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.base.KqdsCkGoodsType;
import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.base.KqdsCkSupplier;
import com.kqds.entity.base.KqdsInformation;
import com.kqds.entity.base.KqdsOutprocessing;
import com.kqds.entity.base.KqdsOutprocessingType;
import com.kqds.entity.base.KqdsOutprocessingUnit;
import com.kqds.entity.base.KqdsPaiban;
import com.kqds.entity.base.KqdsPaibanType;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.outProcessing.KQDS_OutProcessingLogic;
import com.kqds.service.base.outProcessing.KQDS_OutProcessing_TypeLogic;
import com.kqds.service.base.outProcessingUnit.KQDS_outProcessing_UnitLogic;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.ck.KQDS_Ck_GoodstypeLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.util.base.code.UserCodeUtil;
import com.kqds.util.core.file.YZFileUploadForm;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.imports.ExcelTool;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

@Service
public class FileUploadLogic extends BaseLogic {
  private static Logger logger = LoggerFactory.getLogger(FileUploadLogic.class);
  
  @Autowired
  private YZDeptLogic deptLogic;
  
  @Autowired
  private YZPrivLogic userPrivLogic;
  
  @Autowired
  private YZPersonLogic plogic;
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @Autowired
  private KQDS_Ck_GoodstypeLogic goodstypeLogic;
  
  @Autowired
  private KQDS_UserDocumentLogic userlogic;
  
  @Autowired
  private KQDS_TreatItemLogic itemlogic;
  
  @Autowired
  private KQDS_outProcessing_UnitLogic jgclogic;
  
  @Autowired
  private KQDS_OutProcessing_TypeLogic typelogic;
  
  @Autowired
  private KQDS_OutProcessingLogic jgxmlogic;
  
  @Autowired
  private DaoSupport dao;
  
  public static String getRandNum(int min, int max) {
    int randNum = min + (int)(Math.random() * (max - min + 1));
    return (new StringBuilder(String.valueOf(randNum))).toString();
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public JSONObject fileUploadAndImport(String module, YZFileUploadForm fileForm, HttpServletRequest request, String imgtype, String organization) throws Exception {
    JSONObject result = new JSONObject();
    try {
      String hard = YZUtility.getHard4File();
      Iterator<String> iKeys = fileForm.iterateFileFields();
      while (iKeys.hasNext()) {
        String fieldName = iKeys.next();
        String fileName = fileForm.getFileName(fieldName);
        if (YZUtility.isNullorEmpty(fileName))
          continue; 
        FileItem itemFile = fileForm.getFileItem(fieldName);
        if (itemFile == null)
          continue; 
        String fileNameTmp = fileName;
        String rand = YZUtility.getRondom();
        fileName = String.valueOf(rand) + "_" + fileName;
        while (YZUtility.getExist(String.valueOf(YZSysProps.getAttachPath()) + File.separator + hard, fileName)) {
          rand = YZUtility.getRondom();
          fileName = String.valueOf(rand) + "_" + fileNameTmp;
        } 
        if ("information".equals(imgtype) || "goods".equals(imgtype) || "documentuser".equals(imgtype) || "costorderitem".equals(imgtype) || "wjgitem".equals(imgtype) || 
          "paiban".equals(imgtype) || "userdept".equals(imgtype) || "dictdata".equals(imgtype)) {
          FileItem fileitem = fileForm.getFileItem(fieldName);
          if ("goods".equals(imgtype) || "documentuser".equals(imgtype) || "paiban".equals(imgtype))
            organization = ChainUtil.getCurrentOrganization(request); 
          if (fileitem != null)
            if ("paiban".equals(imgtype)) {
              String msg = importPaiBanData(request, fileitem, imgtype, organization);
              result.put("data", msg);
            } else {
              String msg = exceltodSavedata(request, fileitem, imgtype, organization);
              result.put("data", msg);
            }  
        } else {
          result.put(String.valueOf(hard) + "_" + rand, fileNameTmp);
          if (YZUtility.isNullorEmpty(module))
            module = YZUtility.DEFAULT_UPLOAD_MODULE_NAME; 
        } 
        if ("wechat".equals(module) || "wechat_news".equals(module)) {
          int index = fileName.lastIndexOf(".");
          String imgSort = fileName.substring(index, fileName.length());
          String newFileName = String.valueOf(System.currentTimeMillis()) + imgSort;
          fileName = fileName.replace(fileNameTmp, newFileName);
          result.put(String.valueOf(hard) + "_" + rand, newFileName);
        } 
        fileForm.saveFile(fieldName, String.valueOf(request.getSession().getServletContext().getRealPath("/")) + "upload" + File.separator + module + File.separator + hard + File.separator + fileName);
      } 
    } catch (Exception e) {
      throw e;
    } 
    return result;
  }
  
  private String exceltodSavedata(HttpServletRequest request, FileItem fileitem, String mtype, String organization) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    List<List<String>> list = null;
    try {
      list = ExcelTool.read(fileitem.getInputStream(), true);
    } catch (Exception e) {
      throw new Exception("导入数据文件格式有误，请以下载模板的格式重新导入（.xls的excel格式）！");
    } 
    int b = 0;
    for (int i = 1; i < list.size(); i++) {
      int rowslength = ((List)list.get(0)).size();
      List<String> rows = list.get(i);
      String value = "";
      int a = 0;
      for (int j = 0; j < rowslength; j++) {
        String li = rows.get(j);
        if (YZUtility.isNullorEmpty(li)) {
          value = String.valueOf(value) + li + " |||";
          a++;
        } else {
          value = String.valueOf(value) + li + "|||";
        } 
      } 
      if (rowslength == a)
        break; 
      b++;
      String[] val = value.split("\\|\\|\\|");
      try {
        if (mtype.equals("goods")) {
          importCKGoods(val, person, request, organization);
        } else if (mtype.equals("documentuser")) {
          importUserDocument(val, person, request, organization);
        } else if (mtype.equals("costorderitem")) {
          importCostItems(val, person, request, organization);
        } else if (mtype.equals("wjgitem")) {
          importOutProcessingItem(val, person, request, organization);
        } else if (mtype.equals("information")) {
          importInformation(val, person, request, organization);
        } else if (mtype.equals("userdept")) {
          importDeptUser(val, person, request);
        } else if (mtype.equals("dictdata")) {
          importDictData(val, person, request, organization);
        } 
      } catch (Exception e) {
        throw new Exception("导入数据：第" + (i + 1) + "行数据格式有误：" + e.getMessage() + "，请重新导入！");
      } 
    } 
    return "成功导入数据" + b + "条！";
  }
  
  public JSONObject fileUploadLogicForbase64(String module, String imgStr, HttpServletRequest request) throws Exception {
    JSONObject result = new JSONObject();
    try {
      String hard = YZUtility.getHard4File();
      String fileName = String.valueOf(YZUtility.getCurDateTimeStr(YZUtility.DATE_FORMAT_NOSPLIT_yyyyMMddHHmmss)) + ".png";
      String rand = YZUtility.getRondom();
      result.put("attrId", String.valueOf(hard) + "_" + rand + ",");
      result.put("attrName", String.valueOf(fileName) + "*");
      fileName = String.valueOf(rand) + "_" + fileName;
      while (YZUtility.getExist(String.valueOf(YZSysProps.getAttachPath()) + File.separator + hard, fileName)) {
        rand = YZUtility.getRondom();
        fileName = String.valueOf(rand) + "_" + fileName;
      } 
      if (YZUtility.isNullorEmpty(module))
        module = YZUtility.DEFAULT_UPLOAD_MODULE_NAME; 
      generateImage(imgStr, String.valueOf(request.getSession().getServletContext().getRealPath("/")) + "upload" + File.separator + module + File.separator + hard + File.separator, 
          fileName);
    } catch (Exception e) {
      throw e;
    } 
    return result;
  }
  
  private boolean generateImage(String imgStr, String filePath, String fileName) {
    try {
      if (imgStr == null)
        return false; 
      BASE64Decoder decoder = new BASE64Decoder();
      byte[] b = decoder.decodeBuffer(imgStr);
      File file = new File(filePath);
      if (!file.exists())
        file.mkdirs(); 
      OutputStream out = new FileOutputStream(String.valueOf(filePath) + fileName);
      out.write(b);
      out.flush();
      out.close();
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public String importPaiBanData(HttpServletRequest request, FileItem fileitem, String mtype, String organization) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    List<List<String>> list = null;
    try {
      list = ExcelTool.read(fileitem.getInputStream(), true);
    } catch (Exception e) {
      throw new Exception("导入数据文件格式有误，请以下载模板的格式重新导入（.xls的excel格式）！");
    } 
    if (list == null || list.size() == 0)
      throw new Exception("导入的文件无数据，请重新导入！"); 
    List<String> dateRowList = list.get(0);
    int cloNum = 0;
    for (String date : dateRowList) {
      cloNum++;
      if (cloNum == 1)
        continue; 
      if (YZUtility.isNullorEmpty(date))
        throw new Exception("第" + cloNum + "列日期格式不对，必须为yyyy-mm-dd，请重新导入！"); 
      if (!YZUtility.isDateYYYYmmdd(date))
        throw new Exception("第" + cloNum + "列日期格式不对，必须为yyyy-mm-dd，请重新导入！"); 
    } 
    List<String> userIdList = new ArrayList<>();
    for (int i = 1; i < list.size(); i++) {
      List<String> rowDataList = list.get(i);
      if (YZUtility.isNullorEmpty(rowDataList.get(0)))
        break; 
      String userId = rowDataList.get(0);
      if (YZUtility.isNullorEmpty(userId))
        throw new Exception("第" + (i + 1) + "行用户账号不能为空，请重新导入！"); 
      userIdList.add(userId);
    } 
    Map<String, String> userId2SeqIdMap = new HashMap<>();
    Map<String, String> userId2DeptIdMap = new HashMap<>();
    for (int j = 0; j < userIdList.size(); j++) {
      String userId = userIdList.get(j);
      Map<String, String> filtermap = new HashMap<>();
      filtermap.put("user_id", userId);
      List<YZPerson> perlist = (List<YZPerson>)this.dao.loadList(TableNameUtil.SYS_PERSON, filtermap);
      if (perlist == null || perlist.size() == 0)
        throw new Exception("第" + (j + 2) + "行用户账号在系统中不存在，请重新导入！"); 
      userId2SeqIdMap.put(userId, ((YZPerson)perlist.get(0)).getSeqId());
      userId2DeptIdMap.put(userId, ((YZPerson)perlist.get(0)).getDeptId());
    } 
    List<KqdsPaibanType> plist = (List<KqdsPaibanType>)this.dao.loadList(TableNameUtil.KQDS_PAIBAN_TYPE, null);
    Map<String, KqdsPaibanType> pbTypeMap = new HashMap<>();
    for (KqdsPaibanType kqds_Paiban_Type : plist)
      pbTypeMap.put(kqds_Paiban_Type.getTypename(), kqds_Paiban_Type); 
    for (int k = 1; k < list.size(); k++) {
      List<String> rowDataList = list.get(k);
      if (YZUtility.isNullorEmpty(rowDataList.get(0)))
        break; 
      String userId = rowDataList.get(0);
      for (int m = 1; m < dateRowList.size(); m++) {
        String zbValue = rowDataList.get(m);
        if (YZUtility.isNullorEmpty(zbValue)) {
          String errmsg = "第" + (k + 1) + "行，第" + (m + 1) + " 列单元格内容不能为空";
          throw new Exception(errmsg);
        } 
        if (!pbTypeMap.containsKey(zbValue)) {
          String errmsg = "第" + (k + 1) + "行，第" + (m + 1) + " 列排班类型在系统中不存在";
          throw new Exception(errmsg);
        } 
        KqdsPaibanType pbTypeObj = pbTypeMap.get(zbValue);
        Map<String, String> pbfilter = new HashMap<>();
        pbfilter.put("personid", (new StringBuilder(String.valueOf(userId2SeqIdMap.get(userId)))).toString());
        pbfilter.put("startdate", dateRowList.get(m));
        pbfilter.put("organization", organization);
        String SeqId = checkPaiban4DaoRu(pbfilter, organization);
        if (SeqId != null) {
          KqdsPaiban entity = (KqdsPaiban)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_PAIBAN, SeqId);
          entity.setPersonid((new StringBuilder(String.valueOf(userId2SeqIdMap.get(userId)))).toString());
          entity.setDeptid((new StringBuilder(String.valueOf(userId2DeptIdMap.get(userId)))).toString());
          if (!YZUtility.isNullorEmpty(pbTypeObj.getStartdate())) {
            entity.setStartdate(String.valueOf(dateRowList.get(m)) + " " + pbTypeObj.getStartdate() + ":00");
            entity.setEnddate(String.valueOf(dateRowList.get(m)) + " " + pbTypeObj.getEnddate() + ":00");
          } else {
            entity.setStartdate(String.valueOf(dateRowList.get(m)) + ConstUtil.TIME_START);
            entity.setEnddate(String.valueOf(dateRowList.get(m)) + ConstUtil.TIME_START);
          } 
          entity.setOrderstatus(zbValue);
          entity.setCreatetime(YZUtility.getCurDateTimeStr());
          entity.setCreateuser(person.getSeqId());
          this.dao.updateSingleUUID(TableNameUtil.KQDS_PAIBAN, entity);
          BcjlUtil.LogBcjl(BcjlUtil.UPDATE, BcjlUtil.KQDS_PAIBAN, entity, TableNameUtil.KQDS_PAIBAN, request);
        } else {
          KqdsPaiban entity = new KqdsPaiban();
          entity.setSeqId(YZUtility.getUUID());
          entity.setPersonid((new StringBuilder(String.valueOf(userId2SeqIdMap.get(userId)))).toString());
          entity.setDeptid((new StringBuilder(String.valueOf(userId2DeptIdMap.get(userId)))).toString());
          if (!YZUtility.isNullorEmpty(pbTypeObj.getStartdate())) {
            entity.setStartdate(String.valueOf(dateRowList.get(m)) + " " + pbTypeObj.getStartdate() + ":00");
            entity.setEnddate(String.valueOf(dateRowList.get(m)) + " " + pbTypeObj.getEnddate() + ":00");
          } else {
            entity.setStartdate(String.valueOf(dateRowList.get(m)) + ConstUtil.TIME_START);
            entity.setEnddate(String.valueOf(dateRowList.get(m)) + ConstUtil.TIME_START);
          } 
          entity.setOrderstatus(zbValue);
          entity.setCreatetime(YZUtility.getCurDateTimeStr());
          entity.setCreateuser(person.getSeqId());
          entity.setOrganization(organization);
          this.dao.saveSingleUUID(TableNameUtil.KQDS_PAIBAN, entity);
          BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_PAIBAN, entity, TableNameUtil.KQDS_PAIBAN, request);
        } 
      } 
    } 
    return "成功导入数据" + (list.size() - 1) + "条！";
  }
  
  private String checkPaiban4DaoRu(Map<String, String> map, String organization) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.loadList(TableNameUtil.KQDS_PAIBAN, map);
    if (list != null && list.size() > 0)
      return ((JSONObject)list.get(0)).getString("seq_id"); 
    return null;
  }
  
  public void importDeptUser(String[] val, YZPerson person, HttpServletRequest request) throws Exception {
    String userId = null;
    String userName = null;
    String userPriv = null;
    String userPrivId = null;
    String userPrivOther = null;
    StringBuffer userPrivOtherId = new StringBuffer();
    String userDept = null;
    String userDeptId = null;
    String pwd = null;
    String sortno = null;
    if (val.length != 9)
      throw new Exception("请确认该Excel文件的列数和标准模板一致，标准模板列数为9"); 
    if (YZUtility.isNullorEmpty(val[0]))
      return; 
    if (YZUtility.isNullorEmpty(val[1]))
      throw new Exception("真实姓名不能为空"); 
    userName = val[1].trim();
    if (YZUtility.isNullorEmpty(val[2]))
      throw new Exception("主角色不能为空"); 
    if (YZUtility.isNullorEmpty(val[4]))
      throw new Exception("部门不能为空"); 
    userDept = val[4].trim();
    if (YZUtility.isNullorEmpty(val[5]))
      throw new Exception("密码不能为空"); 
    pwd = val[5].trim();
    if (YZUtility.isNullorEmpty(val[6]))
      val[6] = "0"; 
    sortno = val[6].trim();
    try {
      Integer.parseInt(sortno);
    } catch (Exception e) {
      logger.error("用户排序号【" + sortno + "】只能为数字类型：" + e.getMessage());
      throw new Exception("用户排序号【" + sortno + "】只能为数字类型：" + e.getMessage());
    } 
    if (YZUtility.isNullorEmpty(val[7]))
      throw new Exception("门诊编号不能为空"); 
    String organization = val[7].trim();
    String depttypeStr = val[8].trim();
    String depttype = "";
    if (depttypeStr.contains(",")) {
      String[] depttypeArr = depttypeStr.split(",");
      byte b;
      int i;
      String[] arrayOfString1;
      for (i = (arrayOfString1 = depttypeArr).length, b = 0; b < i; ) {
        String depttypes = arrayOfString1[b];
        if (depttypes.equals("咨询")) {
          depttype = String.valueOf(depttype) + "0,";
        } else if (depttypes.equals("医生")) {
          depttype = String.valueOf(depttype) + "1,";
        } else if (depttypes.equals("网电")) {
          depttype = String.valueOf(depttype) + "2,";
        } else if (depttypes.equals("营销")) {
          depttype = String.valueOf(depttype) + "3,";
        } else if (depttypes.equals("护士")) {
          depttype = String.valueOf(depttype) + "4,";
        } else if (depttypes.equals("业绩科室")) {
          depttype = String.valueOf(depttype) + "5,";
        } 
        b++;
      } 
      if (depttype.endsWith(","))
        depttype = depttype.substring(0, depttype.length() - 1); 
    } else if (depttypeStr.equals("咨询")) {
      depttype = ConstUtil.DEPT_TYPE_0;
    } else if (depttypeStr.equals("医生")) {
      depttype = ConstUtil.DEPT_TYPE_1;
    } else if (depttypeStr.equals("网电")) {
      depttype = ConstUtil.DEPT_TYPE_2;
    } else if (depttypeStr.equals("营销")) {
      depttype = ConstUtil.DEPT_TYPE_3;
    } else if (depttypeStr.equals("护士")) {
      depttype = ConstUtil.DEPT_TYPE_4;
    } else if (depttypeStr.equals("业绩科室")) {
      depttype = ConstUtil.DEPT_TYPE_5;
    } 
    userId = val[0].trim();
    Map<String, String> countMap = new HashMap<>();
    countMap.put("user_id", userId);
    int count1 = this.dao.selectCount(TableNameUtil.SYS_PERSON, countMap);
    if (count1 > 0)
      throw new Exception("用户名已存在：" + val[0]); 
    userPriv = val[2].trim();
    JSONObject jsonMainPriv = this.userPrivLogic.getOneByPrivNameNoOrg(userPriv, organization);
    if (jsonMainPriv == null)
      throw new Exception("角色不存在：" + userPriv); 
    userPrivId = jsonMainPriv.getString("seqId");
    if (!YZUtility.isNullorEmpty(val[3])) {
      userPrivOther = val[3].trim();
      userPrivOther = userPrivOther.replace("，", ",");
      String[] uOtherArr = userPrivOther.split(",");
      byte b;
      int i;
      String[] arrayOfString1;
      for (i = (arrayOfString1 = uOtherArr).length, b = 0; b < i; ) {
        String othpriv = arrayOfString1[b];
        othpriv = othpriv.trim();
        if (!YZUtility.isNullorEmpty(othpriv)) {
          JSONObject jsonP = this.userPrivLogic.getOneByPrivNameNoOrg(othpriv, organization);
          if (jsonP == null)
            throw new Exception("辅助角色不存在：" + othpriv); 
          userPrivOtherId.append(jsonP.getString("seqId")).append(",");
        } 
        b++;
      } 
    } 
    JSONObject jsonDept = this.deptLogic.getOneByNameAndCode(userDept, organization);
    if (jsonDept == null) {
      YZDept topDept = this.deptLogic.getTopDeptByCode(organization);
      if (topDept == null)
        throw new Exception("所属门诊不存在，门诊编号：" + organization); 
      String maxDeptNo = this.deptLogic.getMaxDeptNO(organization);
      YZDept newDept = new YZDept();
      newDept.setSeqId(YZUtility.getUUID());
      newDept.setCreatetime(YZUtility.getCurDateTimeStr());
      newDept.setCreateuser(SessionUtil.getLoginPerson(request).getSeqId());
      newDept.setDeptCode(organization);
      newDept.setDeptParent(topDept.getSeqId());
      newDept.setDeptName(userDept);
      newDept.setDeptType(depttype);
      try {
        int currNO = Integer.parseInt(maxDeptNo) + 1;
        newDept.setDeptNo(String.valueOf(currNO));
      } catch (Exception e) {
        logger.error("新建部门，排序号【" + maxDeptNo + "】转换异常：" + e.getMessage());
        newDept.setDeptNo("0");
      } 
      this.dao.saveSingleUUID(TableNameUtil.SYS_DEPT, newDept);
      userDeptId = newDept.getSeqId();
    } else {
      userDeptId = jsonDept.getString("seqId");
    } 
    String auPwd = YZPassEncrypt.encryptPass(pwd);
    YZPerson newp = new YZPerson();
    newp.setSeqId(YZUtility.getUUID());
    newp.setCreatetime(YZUtility.getCurDateTimeStr());
    newp.setCreateuser(SessionUtil.getLoginPerson(request).getSeqId());
    newp.setDeptId(userDeptId);
    newp.setIsLeave(Integer.valueOf(0));
    newp.setNotLogin("0");
    newp.setPassword(auPwd);
    newp.setUserId(userId);
    newp.setUserName(userName);
    newp.setUserNo(Integer.valueOf(Integer.parseInt(sortno)));
    newp.setUserPriv(userPrivId);
    newp.setUserPrivOther(userPrivOtherId.toString());
    this.dao.saveSingleUUID(TableNameUtil.SYS_PERSON, newp);
  }
  
  public void importCKGoods(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
    if (YZUtility.isNullorEmpty(val[0]))
      throw new Exception("一级类别不能为空"); 
    if (YZUtility.isNullorEmpty(val[1]))
      throw new Exception("二级类别不能为空"); 
    if (YZUtility.isNullorEmpty(val[2]))
      throw new Exception("商品编号不能为空"); 
    if (YZUtility.isNullorEmpty(val[3]))
      throw new Exception("商品名称不能为空"); 
    if (YZUtility.isNullorEmpty(val[9]))
      throw new Exception("仓库不能为空"); 
    int maxnums = 0;
    if (!YZUtility.isNullorEmpty(val[14]))
      try {
        maxnums = (int)Float.parseFloat(val[14].trim());
      } catch (Exception e) {
        throw new Exception("上限告警值必须为数字类型，不能为：" + val[14]);
      }  
    int maxgj = 0;
    if ("是".equals(val[15]))
      maxgj = 1; 
    int minnums = 0;
    if (!YZUtility.isNullorEmpty(val[16]))
      try {
        minnums = (int)Float.parseFloat(val[16].trim());
      } catch (Exception e) {
        throw new Exception("下限告警值必须为数字类型，不能为：" + val[16]);
      }  
    int mingj = 0;
    if ("是".equals(val[17]))
      mingj = 1; 
    Map<String, String> map2 = new HashMap<>();
    map2.put("goodscode", val[2]);
    map2.put("organization", organization);
    List<KqdsCkGoodsDetail> listDetail = (List<KqdsCkGoodsDetail>)this.dao.loadList(TableNameUtil.KQDS_CK_GOODS_DETAIL, map2);
    if (listDetail != null && listDetail.size() > 0)
      throw new Exception("该商品编号已存在，无法重复导入，编号为：" + val[2] + "！"); 
    Map<String, String> map1 = new HashMap<>();
    map1.put("goodstype", val[0]);
    map1.put("perid", "0");
    map1.put("ORGANIZATION", organization);
    List<KqdsCkGoodsType> lelve1List = (List<KqdsCkGoodsType>)this.dao.loadList(TableNameUtil.KQDS_CK_GOODS_TYPE, map1);
    KqdsCkGoodsType goodstype = null;
    KqdsCkGoodsType nextgoodstype = null;
    if (lelve1List == null || lelve1List.size() == 0) {
      goodstype = saveGoodsType(val[0], "0", person, request, organization);
    } else {
      goodstype = lelve1List.get(0);
    } 
    map1.put("goodstype", val[1]);
    map1.put("perid", goodstype.getSeqId());
    List<KqdsCkGoodsType> lelve2List = (List<KqdsCkGoodsType>)this.dao.loadList(TableNameUtil.KQDS_CK_GOODS_TYPE, map1);
    if (lelve2List == null || lelve2List.size() == 0) {
      nextgoodstype = saveGoodsType(val[1], goodstype.getSeqId(), person, request, organization);
    } else {
      nextgoodstype = lelve2List.get(0);
    } 
    KqdsCkHouse house = null;
    Map<String, String> map3 = new HashMap<>();
    map3.put("housename", val[9]);
    map3.put("organization", organization);
    List<KqdsCkHouse> houselist = (List<KqdsCkHouse>)this.dao.loadList(TableNameUtil.KQDS_CK_HOUSE, map3);
    if (houselist == null || houselist.size() == 0) {
      house = saveHouse(val[9], person, request, organization);
    } else {
      house = houselist.get(0);
    } 
    if (!YZUtility.isNullorEmpty(val[10])) {
      if (YZUtility.isNullorEmpty(val[11]))
        throw new Exception("该供应商对应的编号不能为空，供应商为：" + val[10] + "！"); 
      Map<String, String> map4 = new HashMap<>();
      map4.put("suppliercode", val[11]);
      map4.put("organization", organization);
      List<KqdsCkSupplier> supplierlist = (List<KqdsCkSupplier>)this.dao.loadList(TableNameUtil.KQDS_CK_SUPPLIER, map4);
      if (supplierlist == null || supplierlist.size() == 0) {
        saveSupplier(val[10], val[11], person, request, organization);
      } else if (!((KqdsCkSupplier)supplierlist.get(0)).getSuppliername().equals(val[10])) {
        throw new Exception("该供应商对应的编号在系统中已存在，供应商为：" + val[10] + "！");
      } 
    } 
    String kuwei = "", remark = "";
    if (!YZUtility.isNullorEmpty(val[12]))
      kuwei = val[12]; 
    if (!YZUtility.isNullorEmpty(val[13]))
      remark = val[13]; 
    KqdsCkGoodsDetail detail = new KqdsCkGoodsDetail();
    detail.setSeqId(YZUtility.getUUID());
    detail.setGoodstype(nextgoodstype.getSeqId());
    detail.setOrganization(organization);
    detail.setGoodscode(val[2]);
    detail.setGoodsname(val[3]);
    detail.setGoodsnorms((val[4] == null) ? "" : val[4]);
    detail.setGoodsunit((val[5] == null) ? "" : val[5]);
    detail.setKuwei(kuwei);
    detail.setRemark(remark);
    detail.setCreatetime(YZUtility.getCurDateTimeStr());
    detail.setCreateuser(person.getSeqId());
    detail.setSortno(Integer.valueOf(1));
    detail.setMinnums(Integer.valueOf(minnums));
    detail.setMaxnums(Integer.valueOf(maxnums));
    detail.setMingj(Integer.valueOf(mingj));
    detail.setMaxgj(Integer.valueOf(maxgj));
    detail.setOrganization(organization);
    String pym = ChineseCharToEn.getAllFirstLetter(detail.getGoodsname());
    detail.setPym(pym);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, detail);
    KqdsCkGoods kqdsckGood = new KqdsCkGoods();
    kqdsckGood.setSeqId(YZUtility.getUUID());
    kqdsckGood.setGoodsdetailid(detail.getSeqId());
    kqdsckGood.setSshouse(house.getSeqId());
    kqdsckGood.setOrganization(ChainUtil.getCurrentOrganization(request));
    this.dao.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, kqdsckGood);
    BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_DETAIL, detail, TableNameUtil.KQDS_CK_GOODS_DETAIL, request);
  }
  
  private KqdsCkGoodsType saveGoodsType(String goodstypename, String perid, YZPerson person, HttpServletRequest request, String organization) throws Exception {
    KqdsCkGoodsType goodstype = new KqdsCkGoodsType();
    goodstype.setSeqId(YZUtility.getUUID());
    String typeno = this.goodstypeLogic.getUniTypenoByName(goodstypename, perid);
    goodstype.setTypeno(typeno);
    goodstype.setGoodstype(goodstypename);
    goodstype.setSortno(Integer.valueOf(1));
    goodstype.setPerid(perid);
    goodstype.setCreatetime(YZUtility.getCurDateTimeStr());
    goodstype.setCreateuser(person.getSeqId());
    goodstype.setOrganization(organization);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_TYPE, goodstype);
    BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_TYPE, goodstype, TableNameUtil.KQDS_CK_GOODS_TYPE, request);
    return goodstype;
  }
  
  private KqdsCkHouse saveHouse(String housename, YZPerson person, HttpServletRequest request, String organization) throws Exception {
    KqdsCkHouse house = new KqdsCkHouse();
    house.setSeqId(YZUtility.getUUID());
    house.setHousename(housename);
    house.setSortno(Integer.valueOf(1));
    house.setCreatetime(YZUtility.getCurDateTimeStr());
    house.setCreateuser(person.getSeqId());
    house.setOrganization(organization);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_CK_HOUSE, house);
    BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_HOUSE, house, TableNameUtil.KQDS_CK_HOUSE, request);
    return house;
  }
  
  private KqdsCkSupplier saveSupplier(String suppliername, String suppliercode, YZPerson person, HttpServletRequest request, String organization) throws Exception {
    KqdsCkSupplier supplier = new KqdsCkSupplier();
    supplier.setSeqId(YZUtility.getUUID());
    supplier.setSuppliername(suppliername);
    supplier.setSuppliercode(suppliercode);
    supplier.setSortno(Integer.valueOf(1));
    supplier.setCreatetime(YZUtility.getCurDateTimeStr());
    supplier.setCreateuser(person.getSeqId());
    supplier.setOrganization(organization);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_CK_SUPPLIER, supplier);
    BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_SUPPLIER, supplier, TableNameUtil.KQDS_CK_SUPPLIER, request);
    return supplier;
  }
  
  public void importUserDocument(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
    if (YZUtility.isNullorEmpty(val[0]))
      throw new Exception("姓名不能为空"); 
    if (YZUtility.isNullorEmpty(val[1]))
      throw new Exception("性别不能为空"); 
    if (YZUtility.isNullorEmpty(val[2]))
      throw new Exception("电话不能为空"); 
    if (YZUtility.isNullorEmpty(val[3]))
      throw new Exception("患者来源不能为空"); 
    if (YZUtility.isNullorEmpty(val[4]))
      throw new Exception("患者来源子分类不能为空"); 
    String phonenumber = val[2];
    Map<String, String> mapsj = new HashMap<>();
    mapsj.put("phonenumber1", phonenumber);
    int num = this.userlogic.checkphonenumber(null, mapsj, TableNameUtil.KQDS_USERDOCUMENT);
    if (num > 0)
      throw new Exception("电话号码已存在，号码为：" + phonenumber); 
    YZDict dict = null;
    try {
      dict = this.dictLogic.getDetailByNameAndParentCode(val[3], DictUtil.HZLY);
    } catch (Exception e) {
      throw new Exception("患者来源数据异常，错误信息为：" + e.getMessage());
    } 
    YZDict subDict = null;
    try {
      subDict = this.dictLogic.getDetailByNameAndParentCode(val[4], dict.getDictCode());
    } catch (Exception e) {
      throw new Exception("患者来源子分类数据异常，错误信息为：" + e.getMessage());
    } 
    KqdsUserdocument entity = new KqdsUserdocument();
    YZPerson per = this.plogic.getPersonByUserId(val[6]);
    if (per == null)
      throw new Exception("开发人不存在"); 
    if (YZUtility.isNullorEmpty(organization))
      throw new Exception("门诊编号获取不到，无法生成患者编号信息"); 
    String uuid = YZUtility.getUUID();
    entity.setSeqId(uuid);
    entity.setIsdelete(Integer.valueOf(0));
    entity.setOrganization(organization);
    entity.setCreatetime(YZUtility.getCurDateTimeStr());
    entity.setCreateuser(person.getSeqId());
    entity.setUsername((val[0] == null) ? "" : val[0]);
    entity.setSex((val[1] == null) ? "" : val[1]);
    entity.setPhonenumber1((val[2] == null) ? "" : val[2]);
    entity.setRemark((val[5] == null) ? "" : val[5]);
    entity.setDeveloper((new StringBuilder(String.valueOf(per.getSeqId()))).toString());
    entity.setDevchannel(dict.getSeqId());
    entity.setNexttype(subDict.getSeqId());
    String uniqUserCode = UserCodeUtil.getUserCode(organization);
    entity.setUsercode(uniqUserCode);
    entity.setType(Integer.valueOf(2));
    entity.setBirthday("");
    entity.setProfession("");
    entity.setIdcardno("");
    entity.setGuider("");
    entity.setIntroducer("");
    entity.setAddress("");
    entity.setRemark("");
    entity.setExperience("");
    entity.setHabit("");
    entity.setXueli("");
    entity.setQq("");
    entity.setImportant("");
    entity.setPhonenumber2("");
    entity.setOrganization(organization);
    String pym = ChineseCharToEn.getAllFirstLetter(entity.getUsername());
    entity.setPym(pym);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, entity);
    BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_USERDOCUMENT, entity, entity.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
  }
  
  public void importCostItems(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
    if (val.length != 12)
      throw new Exception("请确认该Excel文件的列数和标准模板一致，标准模板列数为12"); 
    if (YZUtility.isNullorEmpty(val[0]))
      throw new Exception("项目编号不能为空"); 
    int count = this.itemlogic.countByTreatItemno(val[0]);
    if (count > 0)
      throw new Exception("项目编号在系统中已存在，不允许重复导入，项目编号为：" + val[0]); 
    if (YZUtility.isNullorEmpty(val[1]))
      throw new Exception("项目名称不能为空"); 
    if (YZUtility.isNullorEmpty(val[3]))
      throw new Exception("单价不能为空"); 
    val[3] = val[3].trim();
    BigDecimal unitPriceTmp = BigDecimal.ZERO;
    try {
      unitPriceTmp = new BigDecimal(val[3]);
    } catch (Exception e) {
      throw new Exception("单价必须为数字类型，不能为：" + val[3]);
    } 
    if (KqdsBigDecimal.compareTo(unitPriceTmp, BigDecimal.ZERO) == -1)
      throw new Exception("单价必须大于等于0"); 
    BigDecimal zhekou = BigDecimal.ZERO;
    try {
      zhekou = new BigDecimal(val[4]);
    } catch (Exception e) {
      throw new Exception("折扣必须为数字类型，不能为：" + val[4]);
    } 
    if (KqdsBigDecimal.compareTo(zhekou, BigDecimal.ZERO) == -1)
      throw new Exception("折扣不能小于0，不能为：" + val[4]); 
    if (KqdsBigDecimal.compareTo(zhekou, new BigDecimal(100)) == 1)
      throw new Exception("折扣不能大于100，不能为：" + val[4]); 
    if (YZUtility.isNullorEmpty(val[8]))
      throw new Exception("消费项目一级分类不能为空"); 
    if (YZUtility.isNullorEmpty(val[9]))
      throw new Exception("消费项目二级分类不能为空"); 
    String istsxm = val[5];
    if (istsxm != null && istsxm.equals("是")) {
      istsxm = "1";
    } else {
      istsxm = "0";
    } 
    String llcf = val[6];
    if (llcf != null && llcf.equals("是")) {
      llcf = "1";
    } else {
      llcf = "0";
    } 
    String useflag = val[7];
    if (useflag != null && useflag.equals("是")) {
      useflag = "1";
    } else {
      useflag = "0";
    } 
    String basetype = val[8];
    String classNo = null;
    YZDict baseDict = this.dictLogic.getByNameAndParntCodeOrg(basetype, DictUtil.COSTITEM_SORT, organization);
    if (baseDict == null) {
      YZDict tcode = new YZDict();
      classNo = this.dictLogic.getUniDictCodeByName(basetype);
      tcode.setSeqId(YZUtility.getUUID());
      tcode.setDictCode(classNo);
      tcode.setDictName(basetype);
      tcode.setCreatetime(YZUtility.getCurDateTimeStr());
      tcode.setCreateuser(person.getSeqId());
      tcode.setUseflag(Integer.valueOf(0));
      tcode.setOrderno(Integer.valueOf(0));
      tcode.setParentCode(DictUtil.COSTITEM_SORT);
      tcode.setOrganization(organization);
      this.dao.saveSingleUUID(TableNameUtil.SYS_DICT, tcode);
      BcjlUtil.LogBcjl(BcjlUtil.NEW, SysLogUtil.SYS_DICT, tcode, TableNameUtil.SYS_DICT, request);
    } else {
      classNo = baseDict.getDictCode();
    } 
    String nexttype = val[9];
    String nexttypeid = null;
    YZDict nextDict = this.dictLogic.getByNameAndParntCodeOrg(nexttype, classNo, organization);
    if (nextDict == null) {
      YZDict ci = new YZDict();
      String subClassNo = this.dictLogic.getUniDictCodeByName(nexttype);
      ci.setSeqId(YZUtility.getUUID());
      ci.setDictCode(subClassNo);
      ci.setDictName(nexttype);
      ci.setCreatetime(YZUtility.getCurDateTimeStr());
      ci.setCreateuser(person.getSeqId());
      ci.setUseflag(Integer.valueOf(0));
      ci.setOrderno(Integer.valueOf(0));
      ci.setParentCode(classNo);
      ci.setOrganization(organization);
      this.dao.saveSingleUUID(TableNameUtil.SYS_DICT, ci);
      nexttypeid = ci.getSeqId();
    } else {
      nexttypeid = nextDict.getSeqId();
    } 
    KqdsTreatitem entity = new KqdsTreatitem();
    String uuid = YZUtility.getUUID();
    entity.setSeqId(uuid);
    entity.setCreatetime(YZUtility.getCurDateTimeStr());
    entity.setCreateuser(person.getSeqId());
    entity.setTreatitemno(val[0]);
    entity.setTreatitemname(val[1]);
    entity.setUnit(val[2]);
    entity.setUnitprice(val[3]);
    entity.setDiscount(val[4]);
    entity.setIstsxm(Integer.valueOf(Integer.parseInt(istsxm)));
    entity.setIssplit(Integer.valueOf(Integer.parseInt(llcf)));
    entity.setUseflag(Integer.valueOf(Integer.parseInt(useflag)));
    entity.setBasetype(classNo);
    entity.setNexttype((new StringBuilder(String.valueOf(nexttypeid))).toString());
    entity.setXmjs((val[10] == null) ? "" : val[10]);
    entity.setYhxx((val[11] == null) ? "" : val[11]);
    entity.setOrganization(organization);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_TREATITEM, entity);
  }
  
  private void importOutProcessingItem(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
    if (YZUtility.isNullorEmpty(val[0]))
      throw new Exception("项目编号不能为空"); 
    int count = this.jgxmlogic.countByCode(val[0]);
    if (count > 0)
      throw new Exception("项目编号在系统中已存在，不允许重复导入，项目编号为：" + val[0]); 
    if (YZUtility.isNullorEmpty(val[1]))
      throw new Exception("项目名称不能为空"); 
    if (YZUtility.isNotNullOrEmpty(val[3]))
      try {
      
      } catch (Exception e) {
        throw new Exception("单价必须为数字类型，不能为：" + val[3]);
      }  
    if (YZUtility.isNullorEmpty(val[7]))
      throw new Exception("加工厂不能为空"); 
    if (YZUtility.isNullorEmpty(val[8]))
      throw new Exception("项目分类不能为空"); 
    String basetype = null;
    String nexttype = null;
    String dysfxm = null;
    if (!YZUtility.isNullorEmpty(val[6])) {
      if (YZUtility.isNullorEmpty(val[4]))
        throw new Exception("导入收费项目时，收费分类不能为空，收费项目为：" + val[6]); 
      YZDict dict = this.dictLogic.getDetailByNameAndParentCode(val[4], DictUtil.COSTITEM_SORT);
      if (dict == null)
        throw new Exception("收费项目对应的收费分类不存在，收费项目为：" + val[6]); 
      basetype = dict.getSeqId();
      if (YZUtility.isNullorEmpty(val[5]))
        throw new Exception("导入收费项目时，收费分类子类不能为空，收费项目为：" + val[6]); 
      YZDict dict2 = this.dictLogic.getDetailByNameAndParentCode(val[5], dict.getDictCode());
      if (dict2 == null)
        throw new Exception("收费项目对应的收费分类子类不存在，收费项目为：" + val[6]); 
      nexttype = dict2.getSeqId();
      KqdsTreatitem treatitem = this.itemlogic.getTreatItem(basetype, nexttype, val[6]);
      if (treatitem == null)
        throw new Exception("导入的收费项目不存在，收费项目为：" + val[6]); 
      dysfxm = treatitem.getSeqId();
    } 
    String mrjgc = val[7];
    String code = null;
    KqdsOutprocessingUnit jgcDB = this.jgclogic.getJgcByName(mrjgc, organization);
    if (jgcDB == null) {
      code = this.jgclogic.getUniCodeByName(mrjgc);
      KqdsOutprocessingUnit jgc = new KqdsOutprocessingUnit();
      jgc.setSeqId(YZUtility.getUUID());
      jgc.setCreatetime(YZUtility.getCurDateTimeStr());
      jgc.setCreateuser(person.getSeqId());
      jgc.setCode(code);
      jgc.setName(mrjgc);
      jgc.setOrganization(organization);
      this.dao.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_UNIT, jgc);
      BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING_UNIT, jgc, TableNameUtil.KQDS_OUTPROCESSING_UNIT, request);
    } else {
      code = jgcDB.getCode();
    } 
    String xmfl = val[8];
    String typeno = this.typelogic.checkJgcType(xmfl, code, organization);
    if (YZUtility.isNullorEmpty(typeno)) {
      KqdsOutprocessingType jgctype = new KqdsOutprocessingType();
      jgctype.setSeqId(YZUtility.getUUID());
      jgctype.setProcessingfactory(code);
      jgctype.setTypename(xmfl);
      typeno = this.typelogic.getUniCodeByName(xmfl, code);
      jgctype.setTypeno(typeno);
      jgctype.setUseflag(Integer.valueOf(0));
      jgctype.setCreatetime(YZUtility.getCurDateTimeStr());
      jgctype.setCreateuser(person.getSeqId());
      jgctype.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request));
      this.dao.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_TYPE, jgctype);
      BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING_TYPE, jgctype, TableNameUtil.KQDS_OUTPROCESSING_TYPE, request);
    } 
    KqdsOutprocessing entity = new KqdsOutprocessing();
    String uuid = YZUtility.getUUID();
    entity.setSeqId(uuid);
    entity.setCreatetime(YZUtility.getCurDateTimeStr());
    entity.setCreateuser(person.getSeqId());
    entity.setWjgxmbh(val[0]);
    entity.setWjgmc(val[1]);
    entity.setDw(val[2]);
    entity.setDj(val[3]);
    entity.setBasetype(basetype);
    entity.setNexttype(nexttype);
    entity.setDysfxm(dysfxm);
    entity.setMrjgc(code);
    entity.setWjgfl(typeno);
    entity.setRemark(val[9]);
    entity.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request));
    this.dao.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING, entity);
  }
  
  private void importInformation(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
    String xxtype = val[0];
    String xxtitle = val[1];
    String xxcontent = "";
    if (YZUtility.isNullorEmpty(val[0]))
      throw new Exception("信息分类不能为空"); 
    if (YZUtility.isNullorEmpty(val[1]))
      throw new Exception("信息标题不能为空"); 
    if (YZUtility.isNullorEmpty(val[2]))
      throw new Exception("信息内容不能为空"); 
    YZDict dict = this.dictLogic.getDetailByNameAndParentCode(xxtype, DictUtil.XXKFL);
    if (dict == null) {
      dict = new YZDict();
      dict.setSeqId(YZUtility.getUUID());
      dict.setDictCode("XXKFL_" + YZUtility.getUUID());
      dict.setDictName(val[0]);
      dict.setCreatetime(YZUtility.getCurDateTimeStr());
      dict.setCreateuser(person.getSeqId());
      dict.setUseflag(Integer.valueOf(0));
      dict.setOrderno(Integer.valueOf(0));
      dict.setParentCode(DictUtil.XXKFL);
      dict.setOrganization(organization);
      this.dao.saveSingleUUID(TableNameUtil.SYS_DICT, dict);
    } 
    xxtype = dict.getSeqId();
    String start_p = "<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑, Tahoma, Arial, sans-serif; white-space: normal; background-color: rgb(255, 255, 255);\">";
    String end_p = "</p>";
    String[] xxcontentArr = val[2].split("\\s+");
    for (int i = 0; i < xxcontentArr.length; i++) {
      String content = xxcontentArr[i];
      if (!content.equals(""))
        xxcontent = String.valueOf(xxcontent) + start_p + content + end_p; 
    } 
    KqdsInformation infomation = new KqdsInformation();
    String uuid = YZUtility.getUUID();
    infomation.setSeqId(uuid);
    infomation.setCreatetime(YZUtility.getCurDateTimeStr());
    infomation.setCreateuser(person.getSeqId());
    infomation.setOrganization(organization);
    infomation.setType(xxtype);
    infomation.setTitle(xxtitle);
    infomation.setContent(xxcontent);
    this.dao.saveSingleUUID(TableNameUtil.KQDS_INFORMATION, infomation);
  }
  
  public void importDictData(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
    int useflag2 = 0;
    int useflag3 = 0;
    if (val.length != 5)
      throw new Exception("请确认该Excel文件的列数和标准模板一致，标准模板列数为5"); 
    if (YZUtility.isNullorEmpty(val[0]))
      throw new Exception("一级分类不能为空"); 
    if (YZUtility.isNullorEmpty(val[1]))
      throw new Exception("二级分类不能为空"); 
    if (!YZUtility.isNullorEmpty(val[2]) && val[2].equals("是"))
      useflag2 = 1; 
    if (!YZUtility.isNullorEmpty(val[4]) && val[4].equals("是"))
      useflag3 = 1; 
    YZDict level1Dict = this.dictLogic.getByNameAndParntCodeOrg(val[0], "0", organization);
    if (level1Dict == null) {
      level1Dict = new YZDict();
      String pym = ChineseCharToEn.getAllFirstLetter_RandNum(val[0]);
      level1Dict.setSeqId(YZUtility.getUUID());
      level1Dict.setDictCode(pym);
      level1Dict.setDictName(val[0]);
      level1Dict.setCreatetime(YZUtility.getCurDateTimeStr());
      level1Dict.setCreateuser(person.getSeqId());
      level1Dict.setUseflag(Integer.valueOf(0));
      level1Dict.setOrderno(Integer.valueOf(0));
      level1Dict.setParentCode("0");
      level1Dict.setOrganization(organization);
      this.dao.saveSingleUUID(TableNameUtil.SYS_DICT, level1Dict);
    } 
    YZDict level2Dict = this.dictLogic.getByNameAndParntCodeOrg(val[1], level1Dict.getDictCode(), organization);
    if (level2Dict == null) {
      level2Dict = new YZDict();
      String pym = ChineseCharToEn.getAllFirstLetter_RandNum(val[1]);
      level2Dict.setSeqId(YZUtility.getUUID());
      level2Dict.setDictCode(String.valueOf(level1Dict.getDictCode()) + "_" + pym);
      level2Dict.setDictName(val[1]);
      level2Dict.setCreatetime(YZUtility.getCurDateTimeStr());
      level2Dict.setCreateuser(person.getSeqId());
      level2Dict.setUseflag(Integer.valueOf(useflag2));
      level2Dict.setOrderno(Integer.valueOf(0));
      level2Dict.setParentCode(level1Dict.getDictCode());
      level2Dict.setOrganization(organization);
      this.dao.saveSingleUUID(TableNameUtil.SYS_DICT, level2Dict);
    } 
    if (!YZUtility.isNullorEmpty(val[3])) {
      YZDict level3Dict = this.dictLogic.getByNameAndParntCodeOrg(val[3], level2Dict.getDictCode(), organization);
      if (level3Dict == null) {
        level3Dict = new YZDict();
        String pym = ChineseCharToEn.getAllFirstLetter_RandNum(val[3]);
        level3Dict.setSeqId(YZUtility.getUUID());
        level3Dict.setDictCode(String.valueOf(level2Dict.getDictCode()) + "_" + pym);
        level3Dict.setDictName(val[3]);
        level3Dict.setCreatetime(YZUtility.getCurDateTimeStr());
        level3Dict.setCreateuser(person.getSeqId());
        level3Dict.setUseflag(Integer.valueOf(useflag3));
        level3Dict.setOrderno(Integer.valueOf(0));
        level3Dict.setParentCode(level2Dict.getDictCode());
        level3Dict.setOrganization(organization);
        this.dao.saveSingleUUID(TableNameUtil.SYS_DICT, level3Dict);
      } 
    } 
  }
}
