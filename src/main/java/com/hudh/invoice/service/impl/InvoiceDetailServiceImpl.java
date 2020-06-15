package com.hudh.invoice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.dzbl.dao.DzblDetailDao;
import com.hudh.invoice.dao.InvoiceDetailDao;
import com.hudh.invoice.entity.InvoiceDetail;
import com.hudh.invoice.service.InvoiceDetailService;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.chufang.KQDS_ChuFangLogic;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.jzmdType.KQDS_JzqkLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.other.CacheUtil;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceDetailServiceImpl
  implements InvoiceDetailService
{
  @Autowired
  private KQDS_JzqkLogic jzqkLogic;
  @Autowired
  private Kqds_PayCostLogic payCostLogic;
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  @Autowired
  private KQDS_REGLogic regLogic;
  @Autowired
  private KQDS_CostOrderLogic costOrderLogic;
  @Autowired
  private KQDS_CostOrder_DetailLogic costOrderDetailLogic;
  @Autowired
  private KQDS_ChuFangLogic chuFangLogic;
  @Autowired
  private DzblDetailDao dzblDetailDao;
  @Autowired
  private InvoiceDetailDao invoiceDetailDao;
  @Autowired
  private DaoSupport dao;
  
  public int batchSaveInvoiceDetail(List<InvoiceDetail> list, String usercode, YZPerson person, String organization)
    throws Exception
  {
    for (InvoiceDetail invoiceDetail : list)
    {
      invoiceDetail.setCreatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
      invoiceDetail.setSeqId(YZUtility.getUUID());
      invoiceDetail.setCreateuser(person.getCreateuser());
      invoiceDetail.setStatus(0);
      invoiceDetail.setUsercode(usercode);
      invoiceDetail.setDishonour(0);
      invoiceDetail.setOrganization(organization);
    }
    this.invoiceDetailDao.batchSaveInvoiceDetail(list);
    int i = ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateUserdocumentInvoiceByUsercode", usercode)).intValue();
    String usercodes = "'" + usercode + "'";
    
    List<JSONObject> list1 = this.userLogic.findKqdsUserdocumentByUsercodes(usercodes);
    
    List<JSONObject> list2 = this.regLogic.findKqdsRegByUsercodes(usercodes);
    
    List<JSONObject> list3 = this.costOrderLogic.findCostOrderByUsercodes(usercodes);
    
    List<JSONObject> list4 = this.costOrderDetailLogic.findCostOrderDetailByUsercodes(usercodes);
    
    List<JSONObject> list5 = this.costOrderLogic.findCostOrderTuidanByUsercodes(usercodes);
    
    List<JSONObject> list6 = this.costOrderDetailLogic.findCostOrderDetailTuidanByUsercodes(usercodes);
    
    List<JSONObject> list7 = this.chuFangLogic.findChuFangByUsercodes(usercodes);
    
    List<JSONObject> list8 = this.chuFangLogic.findChuFangDetailByUsercodes(usercodes);
    
    List<JSONObject> list9 = this.payCostLogic.findPayCostByUsercodes(usercodes);
    
    List<JSONObject> list10 = this.dzblDetailDao.findDzblDetailByUsercodes(usercodes);
    
    List<JSONObject> list11 = this.invoiceDetailDao.selectInvoiceDetailByUsercode(usercode);
    
    List<JSONObject> list12 = this.jzqkLogic.selectJzqkByUsercodes(usercodes);
    CacheUtil.openCache();
    if ((list1 != null) && (list1.size() > 0))
    {
      CacheUtil.addZSet("KqdsUserdocument:key", ((JSONObject)list1.get(0)).getString("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key1 = new HashMap();
      key1.put(((JSONObject)list1.get(0)).getString("usercode"), list1);
      CacheUtil.setMap("KqdsUserdocument:value", key1);
    }
    if ((list2 != null) && (list2.size() > 0))
    {
      CacheUtil.addZSet("KqdsReg:key", ((JSONObject)list2.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key2 = new HashMap();
      key2.put(((JSONObject)list2.get(0)).getString("usercode"), list2);
      CacheUtil.setMap("KqdsReg:value", key2);
    }
    if ((list3 != null) && (list3.size() > 0))
    {
      CacheUtil.addZSet("KqdsCostOrder:key", ((JSONObject)list3.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key3 = new HashMap();
      key3.put(((JSONObject)list3.get(0)).getString("usercode"), list3);
      CacheUtil.setMap("KqdsCostOrder:value", key3);
    }
    if ((list4 != null) && (list4.size() > 0))
    {
      CacheUtil.addZSet("KqdsCostOrderDetail:key", ((JSONObject)list4.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key4 = new HashMap();
      key4.put(((JSONObject)list4.get(0)).getString("usercode"), list4);
      CacheUtil.setMap("KqdsCostOrderDetail:value", key4);
    }
    if ((list5 != null) && (list5.size() > 0))
    {
      CacheUtil.addZSet("KqdsCostOrderTuidan:key", ((JSONObject)list5.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key5 = new HashMap();
      key5.put(((JSONObject)list5.get(0)).getString("usercode"), list5);
      CacheUtil.setMap("KqdsCostOrderTuidan:value", key5);
    }
    if ((list6 != null) && (list6.size() > 0))
    {
      CacheUtil.addZSet("KqdsCostOrderDetailTuidan:key", ((JSONObject)list6.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key6 = new HashMap();
      key6.put(((JSONObject)list6.get(0)).getString("usercode"), list6);
      CacheUtil.setMap("KqdsCostOrderDetailTuidan:value", key6);
    }
    if ((list7 != null) && (list7.size() > 0))
    {
      CacheUtil.addZSet("KqdsChuFang:key", ((JSONObject)list7.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key7 = new HashMap();
      key7.put(((JSONObject)list7.get(0)).getString("usercode"), list7);
      CacheUtil.setMap("KqdsChuFang:value", key7);
    }
    if ((list8 != null) && (list8.size() > 0))
    {
      CacheUtil.addZSet("KqdsChuFangDetail:key", ((JSONObject)list8.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key8 = new HashMap();
      key8.put(((JSONObject)list8.get(0)).getString("usercode"), list8);
      CacheUtil.setMap("KqdsChuFangDetail:value", key8);
    }
    if ((list9 != null) && (list9.size() > 0))
    {
      CacheUtil.addZSet("KqdsPayCost:key", ((JSONObject)list9.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key9 = new HashMap();
      key9.put(((JSONObject)list9.get(0)).getString("usercode"), list9);
      CacheUtil.setMap("KqdsPayCost:value", key9);
    }
    if ((list10 != null) && (list10.size() > 0))
    {
      CacheUtil.addZSet("HudhDzblDetail:key", ((JSONObject)list10.get(0)).get("blcode"), new Date().getTime());
      Map<String, List<JSONObject>> key10 = new HashMap();
      key10.put(((JSONObject)list10.get(0)).getString("blcode"), list10);
      CacheUtil.setMap("HudhDzblDetail:value", key10);
    }
    if ((list11 != null) && (list11.size() > 0))
    {
      CacheUtil.addZSet("HudhInvoiceDetail:key", ((JSONObject)list11.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key11 = new HashMap();
      key11.put(((JSONObject)list11.get(0)).getString("usercode"), list11);
      CacheUtil.setMap("HudhInvoiceDetail:value", key11);
    }
    if ((list12 != null) && (list12.size() > 0))
    {
      CacheUtil.addZSet("KqdsJzqk:key", ((JSONObject)list12.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key12 = new HashMap();
      key12.put(((JSONObject)list12.get(0)).getString("usercode"), list12);
      CacheUtil.setMap("KqdsJzqk:value", key12);
    }
    CacheUtil.close();
    return i;
  }
  
  public void batchupdateInvoiceDetail(List<InvoiceDetail> list, String usercode, YZPerson person, String organization)
    throws Exception
  {
    List<InvoiceDetail> list1 = new ArrayList();
    for (InvoiceDetail invoiceDetail : list) {
      if ((invoiceDetail.getSeqId() != null) && (!invoiceDetail.getSeqId().equals("")))
      {
        invoiceDetail.setUpdatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
        invoiceDetail.setUpdateuser(person.getCreateuser());
      }
      else
      {
        invoiceDetail.setCreatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
        invoiceDetail.setSeqId(YZUtility.getUUID());
        invoiceDetail.setCreateuser(person.getCreateuser());
        invoiceDetail.setStatus(0);
        invoiceDetail.setUsercode(usercode);
        invoiceDetail.setDishonour(0);
        invoiceDetail.setOrganization(organization);
        list1.add(invoiceDetail);
      }
    }
    this.invoiceDetailDao.batchupdateInvoiceDetail(list);
    if ((list1.size() > 0) && (list1 != null)) {
      this.invoiceDetailDao.batchSaveInvoiceDetail(list1);
    }
    List<JSONObject> list11 = this.invoiceDetailDao.selectInvoiceDetailByUsercode(usercode);
    CacheUtil.openCache();
    if ((list11 != null) && (list11.size() > 0))
    {
      CacheUtil.addZSet("HudhInvoiceDetail:key", ((JSONObject)list11.get(0)).get("usercode"), new Date().getTime());
      Object key11 = new HashMap();
      ((Map)key11).put(((JSONObject)list11.get(0)).getString("usercode"), list11);
      CacheUtil.setMap("HudhInvoiceDetail:value", (Map)key11);
    }
    CacheUtil.close();
  }
  
  public void batchupdateInvoiceDetailStatus(List<InvoiceDetail> list, HttpServletRequest request)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    for (InvoiceDetail invoiceDetail : list)
    {
      invoiceDetail.setUpdatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
      invoiceDetail.setUpdateuser(person.getCreateuser());
      invoiceDetail.setStatus(1);
    }
    this.invoiceDetailDao.batchupdateInvoiceDetail(list);
  }
  
  public List<JSONObject> selectInvoiceDetailByUsercode(String usercode)
    throws Exception
  {
    List<JSONObject> list = this.invoiceDetailDao.selectInvoiceDetailByUsercode(usercode);
    return list;
  }
  
  public int updateDishonourInvoiceDetail(InvoiceDetail invoiceDetail, String usercode, YZPerson person)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("usercode", usercode);
    map.put("dishonour", "0");
    List<JSONObject> InvoiceDetail = this.invoiceDetailDao.findInvoiceDetailByuserCodeAndstatus(map);
    
    invoiceDetail.setDishonour(1);
    int i = this.invoiceDetailDao.updateDishonourInvoiceDetail(invoiceDetail);
    List<JSONObject> list11 = this.invoiceDetailDao.selectInvoiceDetailByUsercode(usercode);
    List<JSONObject> costOrder = this.costOrderLogic.findCostByUsercode(usercode);
    BigDecimal bd = new BigDecimal(0.0D);
    for (int y = 0; y < costOrder.size(); y++) {
      bd = bd.add(new BigDecimal(((JSONObject)costOrder.get(y)).getString("shouldmoney")));
    }
    CacheUtil.openCache();
    if ((InvoiceDetail.size() == 1) && 
      (bd.compareTo(invoiceDetail.getInvoiceValue()) > -1))
    {
      CacheUtil.addZSet("Detail:key", ((JSONObject)list11.get(0)).get("usercode"), new Date().getTime());
      InvoiceDetail detail = new InvoiceDetail();
      detail.setUsercode(((JSONObject)list11.get(0)).getString("usercode"));
      detail.setStatus(0);
      JSONObject json = JSONObject.fromObject(detail);
      Map<String, JSONObject> key11 = new HashMap();
      key11.put(usercode, json);
      CacheUtil.setMap("Detail:value", key11);
    }
    if ((list11 != null) && (list11.size() > 0))
    {
      CacheUtil.addZSet("HudhInvoiceDetail:key", ((JSONObject)list11.get(0)).get("usercode"), new Date().getTime());
      Map<String, List<JSONObject>> key11 = new HashMap();
      key11.put(((JSONObject)list11.get(0)).getString("usercode"), list11);
      CacheUtil.setMap("HudhInvoiceDetail:value", key11);
    }
    CacheUtil.close();
    return i;
  }
  
  public JSONObject selectInvoiceDetailValueByUsercode(String usercode)
    throws Exception
  {
    JSONObject json = this.invoiceDetailDao.selectInvoiceDetailValueByUsercode(usercode);
    return json;
  }
  
  public JSONObject selectInvoiceDetailValueByUsercodeAndDishonour(String usercode)
    throws Exception
  {
    JSONObject json = this.invoiceDetailDao.selectInvoiceDetailValueByUsercodeAndDishonour(usercode);
    return json;
  }
  
  public void updateDishonourInvoiceDetailAll(String usercode, YZPerson person)
    throws Exception
  {
    InvoiceDetail invoiceDetail = new InvoiceDetail();
    invoiceDetail.setUsercode(usercode);
    invoiceDetail.setUpdateuser(person.getSeqId());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String updatetime = simpleDateFormat.format(new Date());
    invoiceDetail.setUpdatetime(updatetime);
    this.invoiceDetailDao.updateDishonourInvoiceDetailAll(invoiceDetail);
    CacheUtil.openCache();
    
    CacheUtil.addZSet("DetailAll:key", usercode, new Date().getTime());
    InvoiceDetail detail = new InvoiceDetail();
    detail.setUsercode(usercode);
    detail.setStatus(0);
    JSONObject json = JSONObject.fromObject(detail);
    Map<String, JSONObject> key11 = new HashMap();
    key11.put(usercode, json);
    CacheUtil.setMap("DetailAll:value", key11);
    CacheUtil.close();
  }
  
  @Transactional
  public void saveBatchInsert(List<List<String>> list, HttpServletRequest request)
    throws Exception
  {
    JSONObject result = new JSONObject();
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    
    long time = new Date().getTime();
    List<InvoiceDetail> listInvoiceDetail = new ArrayList();
    InvoiceDetail invoiceDetail1 = null;
    String dutyParayraph;
    for (int i = 1; i < list.size(); i++)
    {
      invoiceDetail1 = new InvoiceDetail();
      invoiceDetail1.setSeqId(YZUtility.getUUID());
      invoiceDetail1.setCreatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
      invoiceDetail1.setCreateuser(person.getCreateuser());
      invoiceDetail1.setStatus(0);
      invoiceDetail1.setDishonour(0);
      invoiceDetail1.setOrganization(organization);
      dutyParayraph = (String)((List)list.get(i)).get(2);
      if (YZUtility.isNullorEmpty(dutyParayraph)) {
        throw new Exception("发票号码不能为空");
      }
      Double dou_obj = new Double(Double.parseDouble(dutyParayraph));
      NumberFormat nf = NumberFormat.getInstance();
      nf.setGroupingUsed(false);
      dutyParayraph = nf.format(dou_obj);
      invoiceDetail1.setDutyParayraph(dutyParayraph);
      
      String usercode = (String)((List)list.get(i)).get(3);
      if (YZUtility.isNullorEmpty(usercode)) {
        throw new Exception("患者编号不能为空");
      }
      invoiceDetail1.setUsercode(usercode);
      
      String drawer = (String)((List)list.get(i)).get(4);
      invoiceDetail1.setDrawer(drawer);
      
      String taxpayerNumber = (String)((List)list.get(i)).get(5);
      if (taxpayerNumber.equals("0.0"))
      {
        taxpayerNumber = "";
      }
      else if (taxpayerNumber.contains("."))
      {
        Double dou = new Double(Double.parseDouble(taxpayerNumber));
        NumberFormat nf1 = NumberFormat.getInstance();
        nf1.setGroupingUsed(false);
        taxpayerNumber = nf1.format(dou);
      }
      invoiceDetail1.setTaxpayerNumber(taxpayerNumber);
      
      String invoiceTime = (String)((List)list.get(i)).get(6);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(invoiceTime));
      invoiceTime = sdf.format(date);
      if (YZUtility.isNullorEmpty(invoiceTime)) {
        throw new Exception("开票日期不能为空");
      }
      invoiceDetail1.setInvoiceTime(invoiceTime);
      
      String invoiceValue = (String)((List)list.get(i)).get(8);
      if (YZUtility.isNullorEmpty(invoiceValue)) {
        throw new Exception("合计金额不能为空");
      }
      invoiceDetail1.setInvoiceValue(new BigDecimal(invoiceValue));
      
      String invoiceDetail = (String)((List)list.get(i)).get(9);
      invoiceDetail1.setInvoiceDetail(invoiceDetail);
      listInvoiceDetail.add(invoiceDetail1);
    }
    this.invoiceDetailDao.batchSaveInvoiceDetail(listInvoiceDetail);
    for (InvoiceDetail invoiceDetail : listInvoiceDetail)
    {
      int i = ((Integer)this.dao.update(TableNameUtil.KQDS_USERDOCUMENT + ".updateUserdocumentInvoiceByUsercode", invoiceDetail.getUsercode())).intValue();
      String usercodes = "'" + invoiceDetail.getUsercode() + "'";
      
      List<JSONObject> list1 = this.userLogic.findKqdsUserdocumentByUsercodes(usercodes);
      
      List<JSONObject> list2 = this.regLogic.findKqdsRegByUsercodes(usercodes);
      
      List<JSONObject> list3 = this.costOrderLogic.findCostOrderByUsercodes(usercodes);
      
      List<JSONObject> list4 = this.costOrderDetailLogic.findCostOrderDetailByUsercodes(usercodes);
      
      List<JSONObject> list5 = this.costOrderLogic.findCostOrderTuidanByUsercodes(usercodes);
      
      List<JSONObject> list6 = this.costOrderDetailLogic.findCostOrderDetailTuidanByUsercodes(usercodes);
      
      List<JSONObject> list7 = this.chuFangLogic.findChuFangByUsercodes(usercodes);
      
      List<JSONObject> list8 = this.chuFangLogic.findChuFangDetailByUsercodes(usercodes);
      
      List<JSONObject> list9 = this.payCostLogic.findPayCostByUsercodes(usercodes);
      
      List<JSONObject> list10 = this.dzblDetailDao.findDzblDetailByUsercodes(usercodes);
      
      List<JSONObject> list11 = this.invoiceDetailDao.selectInvoiceDetailByUsercode(invoiceDetail.getUsercode());
      
      List<JSONObject> list12 = this.jzqkLogic.selectJzqkByUsercodes(usercodes);
      CacheUtil.openCache();
      if ((list1 != null) && (list1.size() > 0))
      {
        CacheUtil.addZSet("KqdsUserdocument:key", ((JSONObject)list1.get(0)).getString("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key1 = new HashMap();
        key1.put(((JSONObject)list1.get(0)).getString("usercode"), list1);
        CacheUtil.setMap("KqdsUserdocument:value", key1);
      }
      if ((list2 != null) && (list2.size() > 0))
      {
        CacheUtil.addZSet("KqdsReg:key", ((JSONObject)list2.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key2 = new HashMap();
        key2.put(((JSONObject)list2.get(0)).getString("usercode"), list2);
        CacheUtil.setMap("KqdsReg:value", key2);
      }
      if ((list3 != null) && (list3.size() > 0))
      {
        CacheUtil.addZSet("KqdsCostOrder:key", ((JSONObject)list3.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key3 = new HashMap();
        key3.put(((JSONObject)list3.get(0)).getString("usercode"), list3);
        CacheUtil.setMap("KqdsCostOrder:value", key3);
      }
      if ((list4 != null) && (list4.size() > 0))
      {
        CacheUtil.addZSet("KqdsCostOrderDetail:key", ((JSONObject)list4.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key4 = new HashMap();
        key4.put(((JSONObject)list4.get(0)).getString("usercode"), list4);
        CacheUtil.setMap("KqdsCostOrderDetail:value", key4);
      }
      if ((list5 != null) && (list5.size() > 0))
      {
        CacheUtil.addZSet("KqdsCostOrderTuidan:key", ((JSONObject)list5.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key5 = new HashMap();
        key5.put(((JSONObject)list5.get(0)).getString("usercode"), list5);
        CacheUtil.setMap("KqdsCostOrderTuidan:value", key5);
      }
      if ((list6 != null) && (list6.size() > 0))
      {
        CacheUtil.addZSet("KqdsCostOrderDetailTuidan:key", ((JSONObject)list6.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key6 = new HashMap();
        key6.put(((JSONObject)list6.get(0)).getString("usercode"), list6);
        CacheUtil.setMap("KqdsCostOrderDetailTuidan:value", key6);
      }
      if ((list7 != null) && (list7.size() > 0))
      {
        CacheUtil.addZSet("KqdsChuFang:key", ((JSONObject)list7.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key7 = new HashMap();
        key7.put(((JSONObject)list7.get(0)).getString("usercode"), list7);
        CacheUtil.setMap("KqdsChuFang:value", key7);
      }
      if ((list8 != null) && (list8.size() > 0))
      {
        CacheUtil.addZSet("KqdsChuFangDetail:key", ((JSONObject)list8.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key8 = new HashMap();
        key8.put(((JSONObject)list8.get(0)).getString("usercode"), list8);
        CacheUtil.setMap("KqdsChuFangDetail:value", key8);
      }
      if ((list9 != null) && (list9.size() > 0))
      {
        CacheUtil.addZSet("KqdsPayCost:key", ((JSONObject)list9.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key9 = new HashMap();
        key9.put(((JSONObject)list9.get(0)).getString("usercode"), list9);
        CacheUtil.setMap("KqdsPayCost:value", key9);
      }
      if ((list10 != null) && (list10.size() > 0))
      {
        CacheUtil.addZSet("HudhDzblDetail:key", ((JSONObject)list10.get(0)).get("blcode"), new Date().getTime());
        Map<String, List<JSONObject>> key10 = new HashMap();
        key10.put(((JSONObject)list10.get(0)).getString("blcode"), list10);
        CacheUtil.setMap("HudhDzblDetail:value", key10);
      }
      if ((list11 != null) && (list11.size() > 0))
      {
        CacheUtil.addZSet("HudhInvoiceDetail:key", ((JSONObject)list11.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key11 = new HashMap();
        key11.put(((JSONObject)list11.get(0)).getString("usercode"), list11);
        CacheUtil.setMap("HudhInvoiceDetail:value", key11);
      }
      if ((list12 != null) && (list12.size() > 0))
      {
        CacheUtil.addZSet("KqdsJzqk:key", ((JSONObject)list12.get(0)).get("usercode"), new Date().getTime());
        Map<String, List<JSONObject>> key12 = new HashMap();
        key12.put(((JSONObject)list12.get(0)).getString("usercode"), list12);
        CacheUtil.setMap("KqdsJzqk:value", key12);
      }
      CacheUtil.close();
    }
  }
  
  public JSONObject selectInvoiceDetailByTime(BootStrapPage bp, Map<String, String> map)
    throws Exception
  {
    if (map.get("sortName") != null)
    {
      if (((String)map.get("sortName")).equals("duty_parayraph")) {
        map.put("sortName", "invoice.duty_parayraph");
      }
      if (((String)map.get("sortName")).equals("invoice_time")) {
        map.put("sortName", "invoice.invoice_time");
      }
      if (((String)map.get("sortName")).equals("invoice_value")) {
        map.put("sortName", "invoice.invoice_value");
      }
      if (((String)map.get("sortName")).equals("createtime")) {
        map.put("sortName", "invoice.createtime");
      }
      if (((String)map.get("sortName")).equals("drawer")) {
        map.put("sortName", "invoice.drawer");
      }
      if (((String)map.get("sortName")).equals("taxpayer_number")) {
        map.put("sortName", "invoice.taxpayer_number");
      }
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "invoice.usercode");
      }
      if (((String)map.get("sortName")).equals("invoice_detail")) {
        map.put("sortName", "invoice.invoice_detail");
      }
      if (((String)map.get("sortName")).equals("updatetime")) {
        map.put("sortName", "invoice.updatetime");
      }
      if (((String)map.get("sortName")).equals("dishonour")) {
        map.put("sortName", "invoice.dishonour");
      }
      if (((String)map.get("sortName")).equals("createuser")) {
        map.put("sortName", "invoice.createuser");
      }
      if (((String)map.get("sortName")).equals("updateuser")) {
        map.put("sortName", "invoice.updateuser");
      }
    }
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> json = this.invoiceDetailDao.selectInvoiceDetailByTime(map);
    PageInfo<JSONObject> pageInfo = new PageInfo(json);
    JSONObject jobj = new JSONObject();
    List<JSONObject> invocievalue = this.invoiceDetailDao.selectInvoiceValueByTime(map);
    String allinvoicevalue = "0.00";
    String invoicevalue = "0.00";
    String dishonourvalue = "0.00";
    if (invocievalue.size() > 0) {
      for (JSONObject jsonObject : invocievalue) {
        if (jsonObject.getString("dishonour").equals("0")) {
          invoicevalue = jsonObject.getString("invoicevalue");
        } else if (jsonObject.getString("dishonour").equals("1")) {
          dishonourvalue = jsonObject.getString("invoicevalue");
        }
      }
    }
    allinvoicevalue = new BigDecimal(invoicevalue).add(new BigDecimal(dishonourvalue)).toString();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", json);
    jobj.put("allinvoicevalue", allinvoicevalue);
    jobj.put("invoicevalue", invoicevalue);
    jobj.put("dishonourvalue", dishonourvalue);
    return jobj;
  }
}
