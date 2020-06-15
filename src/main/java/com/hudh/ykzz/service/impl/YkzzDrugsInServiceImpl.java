package com.hudh.ykzz.service.impl;

import com.alibaba.fastjson.JSON;
import com.hudh.lclj.dao.SysParaDao;
import com.hudh.util.HUDHUtil;
import com.hudh.ykzz.dao.YkzzDrugsBatchnumSaveDao;
import com.hudh.ykzz.dao.YkzzDrugsInDao;
import com.hudh.ykzz.dao.YkzzDrugsInDetailDao;
import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsBatchnumSave;
import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.service.IYkzzDrugsInService;
import com.hudh.ykzz.service.IYkzzDrugsService;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.math.BigDecimal;
import java.net.URLDecoder;
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
public class YkzzDrugsInServiceImpl implements IYkzzDrugsInService {
  @Autowired
  private YkzzDrugsInDao ykzzDrugsInDao;
  
  @Autowired
  private YkzzDrugsInDetailDao ykzzDrugsInDetailDao;
  
  @Autowired
  private IYkzzDrugsService ykzzDrugsService;
  
  @Autowired
  private SysParaDao sysParaDao;
  
  @Autowired
  private YkzzDrugsBatchnumSaveDao batchnumDao;
  
  @Transactional(rollbackFor = {Exception.class})
  public void insertDrugsIn(YkzzDrugsIn ykzzDrugsIn, String drugsIndetails, HttpServletRequest request) throws Exception {
    String id = YZUtility.getUUID();
    ykzzDrugsIn.setId(id);
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    ykzzDrugsIn.setCreator(person.getSeqId());
    ykzzDrugsIn.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    ykzzDrugsIn.setStatus(0);
    ykzzDrugsIn.setCheckStatus(0);
    ykzzDrugsIn.setOrganization(organization);
    drugsIndetails = URLDecoder.decode(drugsIndetails, "UTF-8");
    List<YkzzDrugsInDetail> drugsInDetailList = HUDHUtil.parseJsonToObjectList(drugsIndetails, YkzzDrugsInDetail.class);
    for (YkzzDrugsInDetail ykzzDrugsInDetail : drugsInDetailList) {
      ykzzDrugsInDetail.setId(YZUtility.getUUID());
      ykzzDrugsInDetail.setParentid(id);
      ykzzDrugsInDetail.setStatus(0);
      ykzzDrugsInDetail.setCreatetime(ykzzDrugsIn.getCreatetime());
      ykzzDrugsInDetail.setBatchnoNum(Integer.valueOf(ykzzDrugsInDetail.getQuantity()));
      ykzzDrugsInDetail.setOrganization(organization);
    } 
    this.ykzzDrugsInDao.insertDrugsIn(ykzzDrugsIn);
    this.ykzzDrugsInDetailDao.batchSaveInDetail(drugsInDetailList);
  }
  
  public void drugsAddInStock(String drugsInId) throws Exception {
    if (YZUtility.isNullorEmpty(drugsInId))
      throw new Exception("入库单不存在"); 
    List<JSONObject> drugsIndetails = this.ykzzDrugsInDetailDao.findDetailByParendId(drugsInId);
    List<YkzzDrugsInDetail> drugsInDetailList = HUDHUtil.parseJsonToObjectList(JSON.toJSONString(drugsIndetails), YkzzDrugsInDetail.class);
    List<YkzzDrugs> drugsList = this.ykzzDrugsService.selectDrugsByIdStr(drugsInDetailList);
    Map<String, YkzzDrugs> drugsMap = new HashMap<>();
    for (YkzzDrugs ykzzDrugs : drugsList)
      drugsMap.put(ykzzDrugs.getId(), ykzzDrugs); 
    YkzzDrugs drugsTemp = null;
    for (YkzzDrugsInDetail ykzzDrugsInDetail : drugsInDetailList) {
      drugsTemp = drugsMap.get(ykzzDrugsInDetail.getDrugsId());
      drugsTemp.setDrug_total_num(Integer.valueOf(drugsTemp.getDrug_total_num().intValue() + ykzzDrugsInDetail.getQuantity()));
      drugsTemp.setDrgs_total_money(drugsTemp.getDrgs_total_money().add(ykzzDrugsInDetail.getAmount()));
      drugsTemp.setNuit_price(drugsTemp.getDrgs_total_money().divide(new BigDecimal(drugsTemp.getDrug_total_num().toString()), 3, 0));
      drugsMap.put(ykzzDrugsInDetail.getDrugsId(), drugsTemp);
    } 
    List<YkzzDrugs> tempList = new ArrayList<>();
    for (Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet())
      tempList.add(entry.getValue()); 
    this.ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public String deleteDrugsIn(String id) throws Exception {
    String backMsg = "";
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("id", id);
    List<JSONObject> drugsInObj = this.ykzzDrugsInDao.findAllDrugsIn(dataMap);
    if (drugsInObj == null || drugsInObj.size() <= 0) {
      backMsg = "入库单不存在";
      return backMsg;
    } 
    List<JSONObject> drugsInDetailObj = this.ykzzDrugsInDetailDao.findDetailByParendId(((JSONObject)drugsInObj.get(0)).getString("id"));
    List<YkzzDrugsInDetail> drugsInDetailList = HUDHUtil.parseJsonToObjectList(JSON.toJSONString(drugsInDetailObj), YkzzDrugsInDetail.class);
    List<YkzzDrugs> drugsList = this.ykzzDrugsService.selectDrugsByIdStr(drugsInDetailList);
    Map<String, YkzzDrugs> drugsMap = new HashMap<>();
    for (YkzzDrugs drug : drugsList)
      drugsMap.put(drug.getId(), drug); 
    YkzzDrugs drugsTemp = null;
    for (YkzzDrugsInDetail ykzzDrugsInDetail : drugsInDetailList) {
      drugsTemp = drugsMap.get(ykzzDrugsInDetail.getDrugsId());
      int drugTotalNum = drugsTemp.getDrug_total_num().intValue();
      BigDecimal drugTotalMoney = drugsTemp.getDrgs_total_money();
      drugTotalNum -= ykzzDrugsInDetail.getQuantity();
      if (drugTotalNum < 0) {
        backMsg = String.valueOf(drugsTemp.getChemistry_name()) + "库存数量不足";
        return backMsg;
      } 
      drugsTemp.setDrug_total_num(Integer.valueOf(drugTotalNum));
      if (drugTotalMoney.compareTo(ykzzDrugsInDetail.getAmount()) < 0) {
        backMsg = String.valueOf(drugsTemp.getChemistry_name()) + "库存金额不足";
        return backMsg;
      } 
      drugsTemp.setDrgs_total_money(drugTotalMoney.subtract(ykzzDrugsInDetail.getAmount()));
      drugsTemp.setNuit_price(drugsTemp.getDrgs_total_money().divide(
            new BigDecimal(drugsTemp.getDrug_total_num().toString()), 3, 0));
      drugsMap.put(ykzzDrugsInDetail.getDrugsId(), drugsTemp);
    } 
    List<YkzzDrugs> tempList = new ArrayList<>();
    for (Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet())
      tempList.add(entry.getValue()); 
    deleteDrugsInById(id);
    deleteDrugsInDetailByParendId(id);
    this.ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
    backMsg = "操作成功";
    return backMsg;
  }
  
  public List<JSONObject> findAllDrugsIn(Map<String, String> dataMap) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    list = this.ykzzDrugsInDao.findAllDrugsIn(dataMap);
    return list;
  }
  
  public List<JSONObject> findDetailByParendId(String parentid) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    list = this.ykzzDrugsInDetailDao.findDetailByParendId(parentid);
    return list;
  }
  
  public String findByParendId(String parentid) throws Exception {
    String list = this.ykzzDrugsInDao.findByParendId(parentid);
    return list;
  }
  
  public void deleteDrugsInById(String id) throws Exception {
    this.ykzzDrugsInDao.deleteDrugsIn(id);
  }
  
  public void deleteDrugsInDetailByParendId(String id) throws Exception {
    this.ykzzDrugsInDetailDao.deleteDrugsIn(id);
  }
  
  public List<JSONObject> findAllCostOrder(Map<String, Object> dataMap) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    list = this.ykzzDrugsInDao.findAllCostOrder(dataMap);
    return list;
  }
  
  public List<JSONObject> findCostOrderDetailByCostno(String costno) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    list = this.ykzzDrugsInDao.findCostOrderDetailByCostno(costno);
    return list;
  }
  
  public KqdsCostorderDetail findCostOrderDetailBySeqid(String seqId) throws Exception {
    KqdsCostorderDetail kqdsCostorderDetail = this.ykzzDrugsInDao.findCostOrderDetailBySeqid(seqId);
    return kqdsCostorderDetail;
  }
  
  public List<JSONObject> findCostOrderDetailReturnBySeqid(String seqid) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    list = this.ykzzDrugsInDao.findCostOrderDetailReturnBySeqid(seqid);
    return list;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public void grantDrugs(String organization, String costno, String[] batchnoNum, String[] seqId, String[] costseqIdArr, HttpServletRequest request) throws Exception {
    if (costno != null && !costno.equals("") && !costno.equals(null)) {
      List<YkzzDrugsBatchnumSave> list = new ArrayList<>();
      List<String> drugsOrderNoList = new ArrayList<>();
      YZPerson person = SessionUtil.getLoginPerson(request);
      for (int i = 0; i < costseqIdArr.length; i++) {
        KqdsCostorderDetail kqdsCostorderDetail = findCostOrderDetailBySeqid(costseqIdArr[i]);
        KqdsCostorderDetail kqdsCostorderDetail2 = this.ykzzDrugsInDao.findCostOrderDetailByParentid(costseqIdArr[i]);
        if (kqdsCostorderDetail2 != null) {
          int nums = Integer.parseInt(kqdsCostorderDetail.getNum()) + Integer.parseInt(kqdsCostorderDetail2.getNum());
          kqdsCostorderDetail.setNum(String.valueOf(nums));
        } else {
          String qfbh = kqdsCostorderDetail.getQfbh();
          if (qfbh != null && !qfbh.equals("")) {
            KqdsCostorderDetail kqdsCostorderDetail1 = this.ykzzDrugsInDao.findCostOrderDetailSubtotalByQfbh(qfbh);
            if (kqdsCostorderDetail1 != null) {
              int nums = Integer.parseInt(kqdsCostorderDetail.getNum()) + Integer.parseInt(kqdsCostorderDetail1.getNum());
              kqdsCostorderDetail.setNum(String.valueOf(nums));
            } 
          } 
        } 
        YkzzDrugsInDetail drugsInDetail = this.ykzzDrugsInDetailDao.findYkzzDrugsInDatailById(seqId[i]);
        YkzzDrugsBatchnumSave dp = new YkzzDrugsBatchnumSave();
        dp.setCostOrderDetailId(costseqIdArr[i]);
        dp.setId(costseqIdArr[i]);
        dp.setDrugsname(kqdsCostorderDetail.getItemname());
        dp.setDrugsno(kqdsCostorderDetail.getItemno());
        Integer batchno = drugsInDetail.getBatchnoNum();
        dp.setBatchnum((String)batchno);
        dp.setNumber(kqdsCostorderDetail.getNum());
        dp.setBatchno(drugsInDetail.getBatchnum());
        dp.setOrganization(organization);
        dp.setCreatename(person.getUserName());
        dp.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        list.add(dp);
        Object ckNum = kqdsCostorderDetail.getNum();
        int cknum = Integer.parseInt(String.valueOf(ckNum));
        int num = batchno.intValue() - cknum;
        drugsInDetail.setBatchnoNum(Integer.valueOf(num));
        this.ykzzDrugsInDetailDao.updateDrugsInDetail(drugsInDetail);
        drugsOrderNoList.add(kqdsCostorderDetail.getItemno());
      } 
      List<YkzzDrugs> drugsList = this.ykzzDrugsService.selectDrugsByOrderNoStr(drugsOrderNoList);
      Map<String, YkzzDrugs> drugsMap = new HashMap<>();
      for (YkzzDrugs drug : drugsList)
        drugsMap.put(drug.getOrder_no(), drug); 
      YkzzDrugs drugsTemp = null;
      for (int j = 0; j < costseqIdArr.length; j++) {
        KqdsCostorderDetail kqdsCostorderDetail = findCostOrderDetailBySeqid(costseqIdArr[j]);
        drugsTemp = drugsMap.get(kqdsCostorderDetail.getItemno());
        if (drugsTemp.getDrug_total_num().intValue() - Integer.parseInt(kqdsCostorderDetail.getNum()) < 0)
          throw new Exception(String.valueOf(drugsTemp.getChemistry_name()) + "库存不足"); 
        BigDecimal zj = null;
        BigDecimal dj = new BigDecimal(0.0D);
        String order_no = drugsTemp.getOrder_no();
        List<YkzzDrugsInDetail> lt = this.ykzzDrugsInDetailDao.findBatchnumByOrderno(order_no);
        if (lt.size() > 0) {
          for (YkzzDrugsInDetail ykzzDrugsInDetail1 : lt) {
            BigDecimal phzj = (new BigDecimal(ykzzDrugsInDetail1.getBatchnoNum().intValue())).multiply(ykzzDrugsInDetail1.getNuitPrice()).setScale(3, 4);
            if (zj == null) {
              zj = phzj;
              continue;
            } 
            zj = zj.add(phzj);
          } 
          dj = zj.divide(new BigDecimal(drugsTemp.getDrug_total_num().intValue() - Integer.parseInt(kqdsCostorderDetail.getNum())), 4);
        } else {
          zj = new BigDecimal(0);
        } 
        drugsTemp.setDrgs_total_money(zj);
        drugsTemp.setNuit_price(dj);
        drugsTemp.setDrug_total_num(Integer.valueOf(drugsTemp.getDrug_total_num().intValue() - Integer.parseInt(kqdsCostorderDetail.getNum())));
        drugsMap.put(kqdsCostorderDetail.getItemno(), drugsTemp);
      } 
      List<YkzzDrugs> tempList = new ArrayList<>();
      for (Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet())
        tempList.add(entry.getValue()); 
      this.ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
      Integer status = updateCostOrderById(costno);
      if (status.intValue() > 0) {
        this.batchnumDao.insertDrugsBatchnumSave(list);
      } else {
        throw new Exception("此操作非发药操作触发!");
      } 
    } 
  }
  
  public List<JSONObject> findCostOrderDetailById(String id) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    list = this.ykzzDrugsInDao.findCostOrderDetailById(id);
    return list;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public void returnDrugs(String batchnoNum, String seqId, String costseqIdArr, String outnum, YZPerson person) throws Exception {
    List<YkzzDrugsBatchnumSave> list = new ArrayList<>();
    YkzzDrugsBatchnumSave dp = new YkzzDrugsBatchnumSave();
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put(costseqIdArr, seqId);
    KqdsCostorderDetail kcd = new KqdsCostorderDetail();
    kcd.setSeqId(costseqIdArr);
    kcd.setReturnDrugsNum(outnum);
    kcd.setReturnTime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    kcd.setReturnName(person.getUserName());
    insertCostOrderDetailReturnBySeqId(kcd);
    List<JSONObject> costOrderDetail = findCostOrderDetailById(costseqIdArr);
    BigDecimal nuitPrice = null;
    if (costOrderDetail != null && costOrderDetail.size() > 0) {
      List<String> drugsOrderNoList = new ArrayList<>();
      int cs = 0;
      for (JSONObject js : costOrderDetail) {
        if (Integer.parseInt(outnum) == Integer.parseInt((String)js.get("num"))) {
          cs = 1;
          continue;
        } 
        if (Integer.parseInt(outnum) < Integer.parseInt((String)js.get("num")))
          cs = Integer.parseInt(outnum); 
      } 
      for (int i = 0; i < cs; i++) {
        String[] b = seqId.split(",");
        for (int k = 0; k < b.length; k++) {
          YkzzDrugsInDetail drugsInDetail = this.ykzzDrugsInDetailDao.findYkzzDrugsInDatailById(b[k]);
          if (drugsInDetail.getBatchnum().equals(batchnoNum)) {
            nuitPrice = drugsInDetail.getNuitPrice();
            dp.setCostOrderDetailId(costseqIdArr);
            dp.setId(((JSONObject)costOrderDetail.get(i)).getString("seqid"));
            dp.setDrugsname(((JSONObject)costOrderDetail.get(i)).getString("itemname"));
            dp.setDrugsno(((JSONObject)costOrderDetail.get(i)).getString("itemno"));
            Integer batchno = drugsInDetail.getBatchnoNum();
            dp.setBatchnum(Integer.toString(batchno.intValue()));
            dp.setNumber(Integer.toString(Integer.parseInt(((JSONObject)costOrderDetail.get(i)).getString("num")) - Integer.parseInt(outnum)));
            dp.setBatchno(drugsInDetail.getBatchnum());
            list.add(dp);
            int cknum = Integer.parseInt(outnum);
            int num = batchno.intValue() + cknum;
            drugsInDetail.setBatchnoNum(Integer.valueOf(num));
            this.ykzzDrugsInDetailDao.updateDrugsInDetail(drugsInDetail);
            drugsOrderNoList.add(((JSONObject)costOrderDetail.get(i)).getString("itemno"));
          } 
        } 
      } 
      List<YkzzDrugs> drugsList = this.ykzzDrugsService.selectDrugsByOrderNoStr(drugsOrderNoList);
      Map<String, YkzzDrugs> drugsMap = new HashMap<>();
      for (YkzzDrugs drug : drugsList)
        drugsMap.put(drug.getOrder_no(), drug); 
      YkzzDrugs drugsTemp = null;
      for (int j = 0; j < cs; j++) {
        drugsTemp = drugsMap.get(((JSONObject)costOrderDetail.get(j)).getString("itemno"));
        drugsTemp.setDrug_total_num(Integer.valueOf(drugsTemp.getDrug_total_num().intValue() + Integer.parseInt(outnum)));
        BigDecimal yj = null;
        if (drugsTemp.getNuit_price() == null) {
          drugsTemp.setNuit_price(nuitPrice);
          yj = nuitPrice.multiply(new BigDecimal(drugsTemp.getDrug_total_num().toString())).setScale(3, 4);
        } else {
          yj = drugsTemp.getNuit_price().multiply(new BigDecimal(drugsTemp.getDrug_total_num().toString())).setScale(3, 4);
        } 
        drugsTemp.setDrgs_total_money(yj);
        drugsMap.put(((JSONObject)costOrderDetail.get(j)).getString("itemno"), drugsTemp);
      } 
      List<YkzzDrugs> tempList = new ArrayList<>();
      for (Map.Entry<String, YkzzDrugs> entry : drugsMap.entrySet())
        tempList.add(entry.getValue()); 
      this.ykzzDrugsService.batchUpdateDrugsByPrimaryId(tempList);
    } 
  }
  
  private Integer updateCostOrderById(String costno) throws Exception {
    return this.ykzzDrugsInDao.updateCostOrderById(costno);
  }
  
  private void insertCostOrderDetailReturnBySeqId(KqdsCostorderDetail kcd) throws Exception {
    this.ykzzDrugsInDao.insertCostOrderDetailReturnBySeqId(kcd);
  }
  
  public void updateCheckStatus(String id) throws Exception {
    this.ykzzDrugsInDao.updateCheckStatus(id);
  }
  
  public List<JSONObject> findDrugsInAdmin(HttpServletRequest request) throws Exception {
    String organization = ChainUtil.getCurrentOrganization(request);
    List<String> list = new ArrayList<>();
    list.add("DRUGS_IN_ADMIN");
    List<JSONObject> jsonO = this.sysParaDao.getParaValueListByName(list, request, organization);
    return jsonO;
  }
  
  public List<YkzzDrugsInDetail> findBatchnumByOrderno(String order_no) throws Exception {
    List<YkzzDrugsInDetail> list = this.ykzzDrugsInDetailDao.findBatchnumByOrderno(order_no);
    return list;
  }
  
  public List<YkzzDrugsInDetail> findBatchnumByOrderno1(String order_no) throws Exception {
    List<YkzzDrugsInDetail> list = this.ykzzDrugsInDetailDao.findBatchnumByOrderno1(order_no);
    return list;
  }
  
  public YkzzDrugsInDetail findYkzzDrugsInDatailByInDetail(String inDetail) throws Exception {
    return this.ykzzDrugsInDetailDao.findYkzzDrugsInDatailByInDetail(inDetail);
  }
  
  public List<JSONObject> findYkzzDrugsInDetailByOrderno(String orderno) throws Exception {
    return this.ykzzDrugsInDetailDao.findYkzzDrugsInDetailByOrderno(orderno);
  }
  
  public int updateYkzzDrugsInDatailByParentId(YkzzDrugsInDetail ykzzDrugsInDetail) throws Exception {
    return this.ykzzDrugsInDetailDao.updateYkzzDrugsInDatailByParentId(ykzzDrugsInDetail);
  }
  
  public KqdsCostorderDetail findCostOrderDetailByParentid(String seqid) throws Exception {
    KqdsCostorderDetail kqdsCostorderDetail = this.ykzzDrugsInDao.findCostOrderDetailByParentid(seqid);
    return kqdsCostorderDetail;
  }
  
  public List<JSONObject> findCostOrderDetailByQfbh(String qfbh) throws Exception {
    List<JSONObject> kqdsCostorderDetail = this.ykzzDrugsInDao.findCostOrderDetailByQfbh(qfbh);
    return kqdsCostorderDetail;
  }
  
  public KqdsCostorderDetail findCostOrderDetailSubtotalByQfbh(String qfbh) throws Exception {
    KqdsCostorderDetail kqdsCostorderDetail = this.ykzzDrugsInDao.findCostOrderDetailSubtotalByQfbh(qfbh);
    return kqdsCostorderDetail;
  }
}
