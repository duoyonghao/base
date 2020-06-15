package com.hudh.ykzz.service.impl;

import com.hudh.ykzz.dao.IYkzzDrugsDao;
import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
import com.hudh.ykzz.service.IYkzzDrugsService;
import com.hudh.ykzz.service.IYkzzManuService;
import com.hudh.ykzz.service.IYkzzTypeService;
import com.hudh.ykzz.service.IYzzManufacturersService;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictCostLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.DictUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzDrugsServiceImpl
  extends BaseLogic
  implements IYkzzDrugsService
{
  private static final String orderNumber_Prefix = "DRUG";
  private static final int orderNumber_length = 6;
  private static final String beginNumber = "000000";
  @Autowired
  private IYkzzDrugsDao iYkzzDrugsDao;
  @Autowired
  private YZDictCostLogic dictCostLogic;
  @Autowired
  private IYkzzTypeService ykzzTypeService;
  @Autowired
  private IYkzzManuService ykzzManuService;
  @Autowired
  private YZDictLogic yZDictLogic;
  @Autowired
  private IYzzManufacturersService iYzzManufacturersService;
  @Autowired
  private KQDS_TreatItemLogic logic;
  
  @Transactional
  public int insertDrugsinfor(YkzzDrugs ykzzDrugs, HttpServletRequest request)
    throws Exception
  {
    String organization = ChainUtil.getCurrentOrganization(request);
    int drugs = this.iYkzzDrugsDao.insertDrugsInfor(ykzzDrugs);
    
    KqdsTreatitem dp = new KqdsTreatitem();
    dp.setDiscount(ykzzDrugs.getDiscount());
    dp.setCreateuser(ykzzDrugs.getCreator());
    dp.setSeqId(ykzzDrugs.getId());
    dp.setTreatitemno(ykzzDrugs.getOrder_no());
    dp.setTreatitemname(ykzzDrugs.getGood_name());
    BigDecimal retailPrice = ykzzDrugs.getDrug_retail_price();
    dp.setUnitprice(retailPrice.toString());
    dp.setUnit(ykzzDrugs.getCompany());
    dp.setXmjs(ykzzDrugs.getComments());
    dp.setUseflag(Integer.valueOf(0));
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    if (organization.equals("HUDX"))
    {
      String baseType = this.dictCostLogic.getBaseType("2", DictUtil.COSTITEM_SORT, ChainUtil.getCurrentOrganization(request));
      dp.setBasetype(baseType);
      String nextType = this.dictCostLogic.getBaseType("2", baseType, ChainUtil.getCurrentOrganization(request));
      dp.setNexttype(nextType);
    }
    else
    {
      dp.setBasetype("yp610");
      dp.setNexttype("yp70");
    }
    dp.setStatus("1");
    dp.setOrganization(ykzzDrugs.getOrganization());
    dp.setIstsxm(Integer.valueOf(0));
    dp.setIsyjjitem(Integer.valueOf(0));
    dp.setIssplit(Integer.valueOf(0));
    this.iYkzzDrugsDao.addTreatItemBack(dp);
    return drugs;
  }
  
  public JSONObject selectDrugsByPrimaryId(String id)
    throws Exception
  {
    JSONObject json = this.iYkzzDrugsDao.selectDrugsByPrimaryId(id);
    return json;
  }
  
  @Transactional
  public void deleteDrugsByPrimaryId(String id)
    throws Exception
  {
    this.iYkzzDrugsDao.deleteDrugsByPrimaryId(id);
    this.iYkzzDrugsDao.deleteTreamtDrugsByPrimaryId(id);
  }
  
  @Transactional
  public void updateDrugsByPrimaryId(YkzzDrugs ykzzDrugs)
    throws Exception
  {
    this.iYkzzDrugsDao.updateDrugsByPrimaryId(ykzzDrugs);
    KqdsTreatitem dp = new KqdsTreatitem();
    dp.setDiscount(ykzzDrugs.getDiscount());
    dp.setCreateuser(ykzzDrugs.getCreator());
    dp.setSeqId(ykzzDrugs.getId());
    dp.setTreatitemno(ykzzDrugs.getOrder_no());
    dp.setTreatitemname(ykzzDrugs.getGood_name());
    BigDecimal retailPrice = ykzzDrugs.getDrug_retail_price();
    dp.setUnitprice(retailPrice.toString());
    dp.setUnit(ykzzDrugs.getCompany());
    dp.setXmjs(ykzzDrugs.getComments());
    dp.setUseflag(Integer.valueOf(0));
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    this.iYkzzDrugsDao.updateDeugsTreatitemInfor(dp);
  }
  
  public List<JSONObject> selectAllDrugsInfor(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = this.iYkzzDrugsDao.selectAllDrugsInfor(map);
    return list;
  }
  
  public List<JSONObject> selectDrugsInforByConditionQuery(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> json = this.iYkzzDrugsDao.selectDrugsInforByConditionQuery(map);
    return json;
  }
  
  public List<YkzzDrugs> selectDrugsByIdStr(List<YkzzDrugsInDetail> list)
    throws Exception
  {
    List<YkzzDrugs> joList = new ArrayList();
    joList = this.iYkzzDrugsDao.selectDrugsByIdStr(list);
    return joList;
  }
  
  public List<YkzzDrugs> selectDrugsOutByIdStr(List<YkzzDrugsOutDetail> list)
    throws Exception
  {
    List<YkzzDrugs> joList = new ArrayList();
    joList = this.iYkzzDrugsDao.selectDrugsOutByIdStr(list);
    return joList;
  }
  
  public void batchUpdateDrugsByPrimaryId(List<YkzzDrugs> ykzzDrugs)
    throws Exception
  {
    this.iYkzzDrugsDao.batchUpdateDrugsByPrimaryId(ykzzDrugs);
  }
  
  public List<YkzzDrugs> selectDrugsByOrderNoStr(List<String> list)
    throws Exception
  {
    List<YkzzDrugs> joList = new ArrayList();
    joList = this.iYkzzDrugsDao.selectDrugsByOrderNoStr(list);
    return joList;
  }
  
  public void updateDeugsTreatitemInfor(YkzzDrugs ykzzDrugs)
  {
    KqdsTreatitem dp = new KqdsTreatitem();
    dp.setDiscount(ykzzDrugs.getDiscount());
    dp.setCreateuser(ykzzDrugs.getCreator());
    dp.setSeqId(YZUtility.getUUID());
    dp.setTreatitemno(ykzzDrugs.getOrder_no());
    dp.setTreatitemname(ykzzDrugs.getGood_name());
    BigDecimal retailPrice = ykzzDrugs.getDrug_retail_price();
    dp.setUnitprice(retailPrice.toString());
    dp.setUnit(ykzzDrugs.getCompany());
    dp.setXmjs(ykzzDrugs.getComments());
    dp.setUseflag(Integer.valueOf(0));
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
  }
  
  @Transactional
  public void saveBatchInsert(List<List<String>> list, HttpServletRequest request)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    JSONObject result = new JSONObject();
    String organization = ChainUtil.getCurrentOrganization(request);
    
    List<YZDict> listYZDict = this.yZDictLogic.getDrugsStoreByName("药品");
    for (int i = 0; i < listYZDict.size(); i++)
    {
      YZDict y = (YZDict)listYZDict.get(i);
      if (y == null) {
        throw new Exception("药品分类级别不存在，请联系管理员！");
      }
    }
    List<YkzzDrugs> listYkzzDrugs = new ArrayList();
    YkzzDrugs ykzzDrugs = null;
    for (int i = 1; i < list.size(); i++)
    {
      ykzzDrugs = new YkzzDrugs();
      String good_name = (String)((List)list.get(i)).get(0);
      if (YZUtility.isNullorEmpty(good_name)) {
        throw new Exception("药品名称不能为空");
      }
      String drug_retail_price = (String)((List)list.get(i)).get(1);
      if (YZUtility.isNullorEmpty(drug_retail_price)) {
        throw new Exception("药品零售价不能为空");
      }
      String discount = (String)((List)list.get(i)).get(2);
      if (discount.equals("")) {
        ykzzDrugs.setDiscount("100.0");
      } else {
        ykzzDrugs.setDiscount(discount);
      }
      String chemistry_name = (String)((List)list.get(i)).get(3);
      


      String pharm_spec = (String)((List)list.get(i)).get(4);
      if (YZUtility.isNullorEmpty(pharm_spec)) {
        throw new Exception("药品规格不能为空");
      }
      String company = (String)((List)list.get(i)).get(5);
      if (YZUtility.isNullorEmpty(company)) {
        throw new Exception("单位不能为空");
      }
      List<JSONObject> list1 = null;
      String drugs_typeone = (String)((List)list.get(i)).get(6);
      String drugs_typetwo = (String)((List)list.get(i)).get(7);
      JSONObject json = null;
      if (!YZUtility.isNullorEmpty(drugs_typeone))
      {
        Map<String, String> map = new HashMap();
        map.put("id", "root");
        map.put("organization", organization);
        list1 = this.ykzzTypeService.findChildTypesByParentId(map);
        for (int j = 0; j < list1.size(); j++)
        {
          json = (JSONObject)list1.get(j);
          Object l = json.get("typeName");
          if (drugs_typeone.equals(json.get("typeName"))) {
            ykzzDrugs.setDrugs_typeone(json.get("id").toString());
          }
        }
        if (ykzzDrugs.getDrugs_typeone() == null) {
          throw new Exception("没有一级分类，请创建一级分类！");
        }
      }
      else
      {
        throw new Exception("一级分类不能为空");
      }
      if (!YZUtility.isNullorEmpty(drugs_typetwo))
      {
        Map<String, String> map = new HashMap();
        map.put("id", json.get("id").toString());
        map.put("organization", organization);
        List<JSONObject> list2 = this.ykzzTypeService.findChildTypesByParentId(map);
        for (int k = 0; k < list2.size(); k++)
        {
          JSONObject jsonb = (JSONObject)list2.get(k);
          Object ll = jsonb.get("type_name");
          if (drugs_typetwo.equals(jsonb.get("type_name"))) {
            ykzzDrugs.setDrugs_typetwo(jsonb.get("id").toString());
          }
        }
        if (ykzzDrugs.getDrugs_typetwo() == null) {
          throw new Exception("没有二级分类，请创建二级分类！");
        }
      }
      else
      {
        throw new Exception("二级分类不能为空");
      }
      String packing_num = (String)((List)list.get(i)).get(8);
      if (YZUtility.isNullorEmpty(packing_num)) {
        throw new Exception("包装数量不能为空");
      }
      String company_min = (String)((List)list.get(i)).get(9);
      if (YZUtility.isNullorEmpty(company_min)) {
        throw new Exception("包装数量不能为空");
      }
      String content_coe = (String)((List)list.get(i)).get(10);
      if (YZUtility.isNullorEmpty(content_coe)) {
        throw new Exception("含量系数不能为空");
      }
      String content_unit = (String)((List)list.get(i)).get(11);
      if (YZUtility.isNullorEmpty(content_unit)) {
        throw new Exception("含量单位不能为空");
      }
      String sta_item = (String)((List)list.get(i)).get(12);
      




      String contry_code = (String)((List)list.get(i)).get(13);
      if (YZUtility.isNullorEmpty(contry_code)) {
        throw new Exception("国家编码不能为空");
      }
      dataMap.put("organization", organization);
      dataMap.put("contrycode", contry_code);
      List<YkzzDrugs> listYkzz = this.iYkzzDrugsDao.findDeugsByContryCode(dataMap);
      for (int j = 0; j < listYkzz.size(); j++) {
        if (listYkzz != null) {
          throw new Exception("药品国家编码已存在，不能重复添加！");
        }
      }
      String pharm_class = (String)((List)list.get(i)).get(14);
      


      String pharm_dos = (String)((List)list.get(i)).get(15);
      if (YZUtility.isNullorEmpty(pharm_dos)) {
        throw new Exception("药品剂型不能为空");
      }
      String ant_gra = (String)((List)list.get(i)).get(16);
      


      String psy_drugs = (String)((List)list.get(i)).get(17);
      


      String minnums = (String)((List)list.get(i)).get(18);
      


      String mingj = (String)((List)list.get(i)).get(19);
      


      String maxnums = (String)((List)list.get(i)).get(20);
      


      String maxgj = (String)((List)list.get(i)).get(21);
      


      String drug_identify = (String)((List)list.get(i)).get(22);
      


      String comments = (String)((List)list.get(i)).get(23);
      



      String manu_id = (String)((List)list.get(i)).get(24);
      if (!YZUtility.isNullorEmpty(manu_id))
      {
        List<JSONObject> listManu = this.ykzzManuService.findAllManu(organization);
        for (int j = 0; j < listManu.size(); j++)
        {
          JSONObject jsonM = (JSONObject)listManu.get(j);
          Object m = jsonM.get("manu_name");
          if (manu_id.equals(m)) {
            ykzzDrugs.setManu_id(jsonM.get("id").toString());
          }
        }
        if (ykzzDrugs.getManu_id() == null) {
          throw new Exception("供应商不存在！");
        }
      }
      else
      {
        throw new Exception("厂家不能为空");
      }
      String packing_unit = (String)((List)list.get(i)).get(25);
      if (YZUtility.isNullorEmpty(packing_unit)) {
        throw new Exception("包装单位不能为空");
      }
      String drugs_type = (String)((List)list.get(i)).get(26);
      
      String manufac_id = (String)((List)list.get(i)).get(27);
      if (!YZUtility.isNullorEmpty(manufac_id))
      {
        List<JSONObject> listManufac = this.iYzzManufacturersService.findAllManufacturers(organization);
        for (int j = 0; j < listManufac.size(); j++)
        {
          JSONObject jsonM = (JSONObject)listManufac.get(j);
          Object m = jsonM.get("manufacturers_name");
          if (manufac_id.equals(m)) {
            ykzzDrugs.setManufac_id(jsonM.get("id").toString());
          }
        }
        if (ykzzDrugs.getManufac_id() == null) {
          throw new Exception("生产商不存在！");
        }
      }
      else
      {
        throw new Exception("生产商不能为空");
      }
      ykzzDrugs.setCreatetime(YZUtility.getCurDateTimeStr());
      ykzzDrugs.setId(YZUtility.getUUID());
      YZPerson person = SessionUtil.getLoginPerson(request);
      ykzzDrugs.setCreator(person.getSeqId());
      
      ykzzDrugs.setOrganization(organization);
      ykzzDrugs.setGood_name(good_name);
      ykzzDrugs.setComments(comments);
      ykzzDrugs.setPacking_unit(packing_unit);
      ykzzDrugs.setMaxgj(maxgj);
      ykzzDrugs.setMingj(mingj);
      ykzzDrugs.setMinnums(minnums);
      ykzzDrugs.setMaxnums(maxnums);
      ykzzDrugs.setGood_name(good_name);
      ykzzDrugs.setCompany(company);
      
      ykzzDrugs.setPacking_num(packing_num);
      ykzzDrugs.setCompany_min(company_min);
      ykzzDrugs.setContent_coe(content_coe);
      ykzzDrugs.setContent_unit(content_unit);
      ykzzDrugs.setContry_code(contry_code);
      ykzzDrugs.setSta_item(sta_item);
      ykzzDrugs.setPharm_class(pharm_class);
      ykzzDrugs.setPharm_dos(pharm_dos);
      ykzzDrugs.setPharm_spec(pharm_spec);
      ykzzDrugs.setAnt_gra(ant_gra);
      ykzzDrugs.setPsy_drugs(psy_drugs);
      ykzzDrugs.setDrug_identify(drug_identify);
      ykzzDrugs.setChemistry_name(chemistry_name);
      ykzzDrugs.setDrug_total_num(Integer.valueOf(0));
      ykzzDrugs.setDrugs_type(drugs_type);
      
      String drgs_total_money = "0.000";
      BigDecimal bd1 = new BigDecimal(drgs_total_money);
      ykzzDrugs.setDrgs_total_money(bd1);
      ykzzDrugs.setNuit_price(bd1);
      
      BigDecimal bd = new BigDecimal(drug_retail_price);
      bd = bd.setScale(3, 4);
      ykzzDrugs.setDrug_retail_price(bd);
      
      String orderNo = getCreateOederNo(i);
      ykzzDrugs.setOrder_no(orderNo);
      listYkzzDrugs.add(ykzzDrugs);
    }
    this.iYkzzDrugsDao.importDrugsInfro(listYkzzDrugs);
    List<KqdsTreatitem> kqdsTreatList = dxzh(listYkzzDrugs);
    this.iYkzzDrugsDao.saveBatchInsertTreatItemBack(kqdsTreatList);
  }
  
  public List<YkzzDrugs> getAllDrugsInfor(Map<String, String> map)
    throws Exception
  {
    List<YkzzDrugs> list = this.iYkzzDrugsDao.getAllDrugsInfor(map);
    return list;
  }
  
  private List<KqdsTreatitem> dxzh(List<YkzzDrugs> listYkzzDrugs)
  {
    List<KqdsTreatitem> kqdsTreatList = new ArrayList();
    KqdsTreatitem kqdsTreatitem = null;
    for (YkzzDrugs ykzzDrugs : listYkzzDrugs)
    {
      kqdsTreatitem = new KqdsTreatitem();
      kqdsTreatitem.setDiscount(ykzzDrugs.getDiscount());
      kqdsTreatitem.setCreateuser(ykzzDrugs.getCreator());
      kqdsTreatitem.setSeqId(ykzzDrugs.getId());
      kqdsTreatitem.setTreatitemno(ykzzDrugs.getOrder_no());
      kqdsTreatitem.setTreatitemname(ykzzDrugs.getGood_name());
      BigDecimal retailPrice = ykzzDrugs.getDrug_retail_price();
      kqdsTreatitem.setUnitprice(retailPrice.toString());
      kqdsTreatitem.setUnit(ykzzDrugs.getCompany());
      kqdsTreatitem.setXmjs(ykzzDrugs.getComments());
      kqdsTreatitem.setUseflag(Integer.valueOf(0));
      kqdsTreatitem.setCreatetime(ykzzDrugs.getCreatetime());
      kqdsTreatitem.setBasetype("yp610");
      kqdsTreatitem.setNexttype("yp70");
      kqdsTreatitem.setStatus("1");
      kqdsTreatitem.setOrganization(ykzzDrugs.getOrganization());
      kqdsTreatitem.setIstsxm(Integer.valueOf(0));
      kqdsTreatitem.setIsyjjitem(Integer.valueOf(0));
      kqdsTreatitem.setIssplit(Integer.valueOf(0));
      kqdsTreatList.add(kqdsTreatitem);
    }
    return kqdsTreatList;
  }
  
  private String getCreateOederNo(int i)
    throws Exception
  {
    int num = this.iYkzzDrugsDao.findOrderNumberCount();
    if (num == 0) {
      return "DRUG" + String.format("%06d", new Object[] { Integer.valueOf(Integer.parseInt("000000") + i) });
    }
    return "DRUG" + String.format("%06d", new Object[] { Integer.valueOf(Integer.parseInt("000000") + num + i) });
  }
  
  public List<YkzzDrugs> findDeugsByContryCode(String contrycode, String organization)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("contrycode", contrycode);
    dataMap.put("organization", organization);
    List<YkzzDrugs> list = this.iYkzzDrugsDao.findDeugsByContryCode(dataMap);
    return list;
  }
  
  @Transactional
  public void forbiddenDrugs(String id)
    throws Exception
  {
    this.iYkzzDrugsDao.forbiddenDrugs(id);
    this.logic.changeDrugsUserflag(id);
  }
  
  @Transactional
  public void recoverDrugs(String id)
    throws Exception
  {
    this.iYkzzDrugsDao.recoverDrugs(id);
    this.logic.recoverDrugsUserflag(id);
  }
}
