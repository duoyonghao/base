package com.hudh.sjtj.dao;

import com.kqds.dao.DaoSupport;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataAnalysisDao {
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> findBaseDataAskperson() throws Exception {
    this.dao.findForList("", null);
    return null;
  }
  
  public JSONObject findQuantityByAskpersonAndYear(Map<String, String> map) throws Exception {
    JSONObject list = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_REG) + ".findQuantityByAskpersonAndYear", map);
    return list;
  }
  
  public JSONObject findCJQuantityByAskpersonAndYear(Map<String, String> map) throws Exception {
    JSONObject list = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_REG) + ".findCJQuantityByAskpersonAndYear", map);
    return list;
  }
  
  public List<JSONObject> findPaymoneyByYear(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findPaymoneyByYear", map);
    return list;
  }
  
  public JSONObject findQuantityByAskpersonAndMonth(Map<String, String> map) throws Exception {
    JSONObject list = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_REG) + ".findQuantityByAskpersonAndMonth", map);
    return list;
  }
  
  public JSONObject findCJQuantityByAskpersonAndMonth(Map<String, String> map) throws Exception {
    JSONObject list = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_REG) + ".findCJQuantityByAskpersonAndMonth", map);
    return list;
  }
  
  public List<JSONObject> findPaymoneyByMonth(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findPaymoneyByMonth", map);
    return list;
  }
  
  public JSONObject findQuantityByAskpersonAndDay(Map<String, String> map) throws Exception {
    JSONObject list = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_REG) + ".findQuantityByAskpersonAndDay", map);
    return list;
  }
  
  public JSONObject findCJQuantityByAskpersonAndDay(Map<String, String> map) throws Exception {
    JSONObject list = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_REG) + ".findCJQuantityByAskpersonAndDay", map);
    return list;
  }
  
  public List<JSONObject> findPaymoneyByDay(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findPaymoneyByDay", map);
    return list;
  }
  
  public JSONObject findQuantityByAskpersonAndMonthInYear(Map<String, String> map) throws Exception {
    JSONObject list = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_REG) + ".findQuantityByAskpersonAndMonthInYear", map);
    return list;
  }
  
  public JSONObject findCJQuantityByAskpersonAndMonthInYear(Map<String, String> map) throws Exception {
    JSONObject list = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_REG) + ".findCJQuantityByAskpersonAndMonthInYear", map);
    return list;
  }
  
  public List<JSONObject> findAllQuantityByAskpersonAndYear(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_REG) + ".findAllQuantityByAskpersonAndYear", map);
    return list;
  }
  
  public List<JSONObject> findAllCJQuantityByAskpersonAndYear(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_REG) + ".findAllCJQuantityByAskpersonAndYear", map);
    return list;
  }
  
  public List<JSONObject> findAllPaymoneyByYear(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findAllPaymoneyByYear", map);
    return list;
  }
  
  public List<JSONObject> findAllQuantityByAskpersonAndMonth(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_REG) + ".findAllQuantityByAskpersonAndMonth", map);
    return list;
  }
  
  public List<JSONObject> findAllCJQuantityByAskpersonAndMonth(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_REG) + ".findAllCjQuantityByAskpersonAndMonth", map);
    return list;
  }
  
  public List<JSONObject> findAllPaymoneyByMonth(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findAllPaymoneyByMonth", map);
    return list;
  }
  
  public List<JSONObject> findAllQuantityByAskpersonAndDay(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_REG) + ".findAllQuantityByAskpersonAndDay", map);
    return list;
  }
  
  public List<JSONObject> findAllCJQuantityByAskpersonAndDay(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_REG) + ".findAllCjQuantityByAskpersonAndDay", map);
    return list;
  }
  
  public List<JSONObject> findAllPaymoneyByDay(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findAllPaymoneyByDay", map);
    return list;
  }
  
  public List<JSONObject> findTotalMoneyByMonth(Map<String, String> dataMap) throws Exception {
    List<JSONObject> json = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findTotalMoneyByMonth", dataMap);
    return json;
  }
  
  public List<JSONObject> findTotalRefundByMonth(Map<String, String> dataMap) throws Exception {
    List<JSONObject> json = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_REFUND) + ".findTotalRefundByMonth", dataMap);
    return json;
  }
  
  public List<JSONObject> findTotalPrepayByMonth(Map<String, String> dataMap) throws Exception {
    List<JSONObject> json = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findTotalPrepayByMonth", dataMap);
    return json;
  }
  
  public List<JSONObject> findTotalUsePrepayByMonth(Map<String, String> dataMap) throws Exception {
    List<JSONObject> json = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findTotalUsePrepayByMonth", dataMap);
    return json;
  }
  
  public List<JSONObject> findTotalRefundPrepayByMonth(Map<String, String> dataMap) throws Exception {
    List<JSONObject> json = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_MEMBER_RECORD) + ".findTotalRefundPrepayByMonth", dataMap);
    return json;
  }
  
  public List<JSONObject> findTotalDiscountByMonth(Map<String, String> dataMap) throws Exception {
    List<JSONObject> json = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_PAYCOST) + ".findTotalDiscountByMonth", dataMap);
    return json;
  }
  
  public List<JSONObject> findTotalDrugsByMonth(Map<String, String> dataMap) throws Exception {
    List<JSONObject> json = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findTotalDrugsByMonth", dataMap);
    return json;
  }
  
  public List<JSONObject> findTotalCheckItemsByMonth(Map<String, String> dataMap) throws Exception {
    List<JSONObject> json = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findTotalCheckItemsByMonth", dataMap);
    return json;
  }
  
  public List<JSONObject> findAllCmoneyByYear(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_MEMBER_RECORD) + ".findAllCmoneyByYear", map);
    return list;
  }
  
  public List<JSONObject> findAllCmoneyByMonth(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_MEMBER_RECORD) + ".findAllCmoneyByMonth", map);
    return list;
  }
  
  public List<JSONObject> findAllCmoneyByDay(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_MEMBER_RECORD) + ".findAllCmoneyByDay", map);
    return list;
  }
  
  public List<JSONObject> findCmoneyByYear(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_MEMBER_RECORD) + ".findCmoneyByYear", map);
    return list;
  }
  
  public List<JSONObject> findCmoneyByMonth(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_MEMBER_RECORD) + ".findCmoneyByMonth", map);
    return list;
  }
  
  public List<JSONObject> findCmoneyByDay(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_MEMBER_RECORD) + ".findCmoneyByDay", map);
    return list;
  }
  
  public List<JSONObject> findAllDrugsmoneyByDay(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findAllDrugsmoneyByDay", map);
    return list;
  }
  
  public List<JSONObject> findAllDrugsmoneyByMonth(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findAllDrugsmoneyByMonth", map);
    return list;
  }
  
  public List<JSONObject> findAllDrugsmoneyByYear(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findAllDrugsmoneyByYear", map);
    return list;
  }
  
  public List<JSONObject> findDrugsmoneyByDay(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findDrugsmoneyByDay", map);
    return list;
  }
  
  public List<JSONObject> findDrugsmoneyByMonth(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findDrugsmoneyByMonth", map);
    return list;
  }
  
  public List<JSONObject> findDrugsmoneyByYear(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findDrugsmoneyByYear", map);
    return list;
  }
}
