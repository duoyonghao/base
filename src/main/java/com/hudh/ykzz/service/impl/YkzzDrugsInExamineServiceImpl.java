package com.hudh.ykzz.service.impl;

import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.dao.YkzzDrugsInDao;
import com.hudh.ykzz.dao.YkzzDrugsInExamineDao;
import com.hudh.ykzz.entity.YkzzDrugsInExamine;
import com.hudh.ykzz.service.IYkzzDrugsInExamineService;
import com.hudh.ykzz.service.IYkzzDrugsInService;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class YkzzDrugsInExamineServiceImpl implements IYkzzDrugsInExamineService {
  @Autowired
  private YkzzDrugsInExamineDao ykzzDrugsInExamineDao;
  
  @Autowired
  private YkzzDrugsInDao ykzzDrugsInDao;
  
  @Autowired
  private IYkzzDrugsInService YkzzDrugsInService;
  
  @Transactional(rollbackFor = {Exception.class})
  public int insertDrugsInExamine(YkzzDrugsInExamine ykzzDrugsInExamine, HttpServletRequest request) throws Exception {
    ykzzDrugsInExamine.setId(YZUtility.getUUID());
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    ykzzDrugsInExamine.setCheckDate(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    ykzzDrugsInExamine.setCheckUserId(person.getSeqId());
    ykzzDrugsInExamine.setOrganization(organization);
    if (ykzzDrugsInExamine.getPacking().equals("1") && ykzzDrugsInExamine.getReport().equals("1") && ykzzDrugsInExamine.getCertificate().equals("1")) {
      ykzzDrugsInExamine.setResult("1");
      this.YkzzDrugsInService.updateCheckStatus(ykzzDrugsInExamine.getDrugsInId());
      this.YkzzDrugsInService.drugsAddInStock(ykzzDrugsInExamine.getDrugsInId());
    } else {
      ykzzDrugsInExamine.setResult("0");
    } 
    return this.ykzzDrugsInExamineDao.insertDrugsInExamine(ykzzDrugsInExamine);
  }
  
  public List<JSONObject> findDrugsInExamineByInId(String drugsInId) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    list = this.ykzzDrugsInExamineDao.findDrugsInExamineByInId(drugsInId);
    return list;
  }
  
  public void deleteDrugsInExamineById(String id) throws Exception {
    this.ykzzDrugsInExamineDao.deleteDrugsInExamineById(id);
  }
  
  public void deleteDrugsInExamineByInId(String drugsInId) throws Exception {
    this.ykzzDrugsInExamineDao.deleteDrugsInExamineByInId(drugsInId);
  }
  
  public List<JSONObject> findDugsExamineDetail(Map<String, String> dataMap) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    List<JSONObject> list1 = new ArrayList<>();
    list = this.ykzzDrugsInExamineDao.findDugsExamineDetail(dataMap);
    for (int i = 0; i < list.size(); i++) {
      if (((JSONObject)list.get(i)).get("batchno") != null && !((JSONObject)list.get(i)).get("batchno").equals("")) {
        String qfbh = (String)((JSONObject)list.get(i)).get("qfbh");
        String seqid = (String)((JSONObject)list.get(i)).get("seq_id");
        KqdsCostorderDetail kqdsCostorderDetail2 = this.ykzzDrugsInDao.findCostOrderDetailByParentid(seqid);
        if (kqdsCostorderDetail2 != null) {
          int nums = Integer.parseInt((String)((JSONObject)list.get(i)).get("num")) + Integer.parseInt(kqdsCostorderDetail2.getNum());
          ((JSONObject)list.get(i)).put("num", Integer.valueOf(nums));
          ((JSONObject)list.get(i)).put("subtotal", (new BigDecimal((String)((JSONObject)list.get(i)).get("unitprice"))).multiply(new BigDecimal(nums)));
          ((JSONObject)list.get(i)).put("paymoney", (new BigDecimal((String)((JSONObject)list.get(i)).get("unitprice"))).multiply(new BigDecimal(nums)));
        } else if (qfbh != null && !qfbh.equals("")) {
          KqdsCostorderDetail kqdsCostorderDetail1 = this.ykzzDrugsInDao.findCostOrderDetailSubtotalByQfbh(qfbh);
          if (kqdsCostorderDetail1 != null) {
            int nums = Integer.parseInt((String)((JSONObject)list.get(i)).get("num")) + Integer.parseInt(kqdsCostorderDetail1.getNum());
            ((JSONObject)list.get(i)).put("num", Integer.valueOf(nums));
            ((JSONObject)list.get(i)).put("subtotal", (new BigDecimal((String)((JSONObject)list.get(i)).get("unitprice"))).multiply(new BigDecimal(nums)));
            ((JSONObject)list.get(i)).put("paymoney", (new BigDecimal((String)((JSONObject)list.get(i)).get("unitprice"))).multiply(new BigDecimal(nums)));
          } 
        } 
        list1.add(list.get(i));
      } 
    } 
    return list1;
  }
  
  public List<JSONObject> findDugsReturnDetail(Map<String, String> dataMap) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    list = this.ykzzDrugsInExamineDao.findDugsReturnDetail(dataMap);
    return list;
  }
}
