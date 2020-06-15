package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsTreatitem;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IYkzzDrugsDao {
  @Autowired
  private DaoSupport dao;
  
  public int insertDrugsInfor(YkzzDrugs ykzzDrugs) throws Exception {
    int drugsNum = ((Integer)this.dao.save("HUDH_YKZZ_DRUGS.insertDrugsInfor", ykzzDrugs)).intValue();
    return drugsNum;
  }
  
  public int addTreatItemBack(KqdsTreatitem dp) throws Exception {
    this.dao.save("HUDH_YKZZ_DRUGS.addTreatItemBack", dp);
    return 0;
  }
  
  public void saveBatchInsertTreatItemBack(List<KqdsTreatitem> list) throws Exception {
    this.dao.batchUpdate("HUDH_YKZZ_DRUGS.saveBatchInsertTreatItemBack", list);
  }
  
  public void updateDeugsTreatitemInfor(KqdsTreatitem dp) throws Exception {
    this.dao.update("HUDH_YKZZ_DRUGS.updateDeugsTreatitemInfor", dp);
  }
  
  public JSONObject selectDrugsByPrimaryId(String id) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject("HUDH_YKZZ_DRUGS.selectDrugsByPrimaryId", id);
    return json;
  }
  
  public List<JSONObject> selectAllDrugsInfor(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("HUDH_YKZZ_DRUGS.selectAllDrugsInfor", map);
    return list;
  }
  
  public YkzzDrugs findNextOrderNumber() throws Exception {
    YkzzDrugs ykzzDrugs = (YkzzDrugs)this.dao.findForObject("HUDH_YKZZ_DRUGS.findNextOrderNumber", null);
    return ykzzDrugs;
  }
  
  public int findOrderNumberCount() throws Exception {
    int num = ((Integer)this.dao.findForObject("HUDH_YKZZ_DRUGS.findOrderNumberCount", null)).intValue();
    return num;
  }
  
  public void deleteDrugsByPrimaryId(String id) throws Exception {
    this.dao.delete("HUDH_YKZZ_DRUGS.deleteDrugsByPrimaryId", id);
  }
  
  public void deleteTreamtDrugsByPrimaryId(String id) throws Exception {
    this.dao.delete("HUDH_YKZZ_DRUGS.deleteTreamtDrugsByPrimaryId", id);
  }
  
  public void updateDrugsByPrimaryId(@Param("ykzzDrugs") YkzzDrugs ykzzDrugs) throws Exception {
    this.dao.update("HUDH_YKZZ_DRUGS.updateDrugsByPrimaryId", ykzzDrugs);
  }
  
  public void batchUpdateDrugsByPrimaryId(List<YkzzDrugs> ykzzDrugs) throws Exception {
    this.dao.batchUpdate("HUDH_YKZZ_DRUGS.batchUpdateDrugsByPrimaryId", ykzzDrugs);
  }
  
  public List<JSONObject> selectDrugsInforByConditionQuery(Map<String, String> map) throws Exception {
    List<JSONObject> json = (List<JSONObject>)this.dao.findForList("HUDH_YKZZ_DRUGS.selectDrugsInforByConditionQuery", map);
    return json;
  }
  
  public List<YkzzDrugs> selectDrugsByIdStr(List<YkzzDrugsInDetail> list) throws Exception {
    List<YkzzDrugs> json = (List<YkzzDrugs>)this.dao.findForList("HUDH_YKZZ_DRUGS.selectDrugsByIdStr", list);
    return json;
  }
  
  public List<YkzzDrugs> selectDrugsOutByIdStr(List<YkzzDrugsOutDetail> list) throws Exception {
    List<YkzzDrugs> json = (List<YkzzDrugs>)this.dao.findForList("HUDH_YKZZ_DRUGS.selectDrugsByIdStr", list);
    return json;
  }
  
  public List<YkzzDrugs> selectDrugsByOrderNoStr(List<String> list) throws Exception {
    List<YkzzDrugs> json = (List<YkzzDrugs>)this.dao.findForList("HUDH_YKZZ_DRUGS.selectDrugsByOrderNoStr", list);
    return json;
  }
  
  public void importDrugsInfro(List<YkzzDrugs> list) throws Exception {
    this.dao.batchUpdate("HUDH_YKZZ_DRUGS.importDrugsInfro", list);
  }
  
  public List<YkzzDrugs> getAllDrugsInfor(Map<String, String> map) throws Exception {
    List<YkzzDrugs> list = (List<YkzzDrugs>)this.dao.findForList("HUDH_YKZZ_DRUGS.getAllDrugsInfor", map);
    return list;
  }
  
  public void insertDrugsInfro(YkzzDrugs ykzzDrugs) throws Exception {
    this.dao.save("HUDH_YKZZ_DRUGS.insertDrugsInfro", ykzzDrugs);
  }
  
  public List<YkzzDrugs> findDeugsByContryCode(Map<String, String> dataMap) throws Exception {
    List<YkzzDrugs> ykzzDrugs = (List<YkzzDrugs>)this.dao.findForList("HUDH_YKZZ_DRUGS.findDeugsByContryCode", dataMap);
    return ykzzDrugs;
  }
  
  public void forbiddenDrugs(String id) throws Exception {
    this.dao.update("HUDH_YKZZ_DRUGS.forbiddenDrugs", id);
  }
  
  public void recoverDrugs(String id) throws Exception {
    this.dao.update("HUDH_YKZZ_DRUGS.recoverDrugs", id);
  }
}
