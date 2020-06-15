package com.kqds.service.base.treatItem;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.giveItem.KQDS_Give_ItemLogic;
import com.kqds.service.base.treatItemTc.KQDS_TreatItem_TcLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_TreatItemLogic extends BaseLogic {
  private static Logger logger = LoggerFactory.getLogger(KQDS_TreatItemLogic.class);
  
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private KQDS_CostOrder_DetailLogic costOrderDetailLogic;
  
  @Autowired
  private KQDS_Give_ItemLogic giveItemLogic;
  
  @Autowired
  private KQDS_TreatItem_TcLogic tcLogic;
  
  public String getAutoCode4Add(String organization) throws Exception {
    JSONObject topJson = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getAutoCode4Add", organization);
    if (topJson == null)
      return null; 
    String treatitemno = topJson.getString("treatitemno");
    if (YZUtility.isNullorEmpty(treatitemno))
      return null; 
    String lastStr = treatitemno.substring(treatitemno.length() - 1, treatitemno.length());
    try {
      int lastInt = Integer.parseInt(lastStr);
      String preStr = treatitemno.substring(0, treatitemno.length() - 1);
      return String.valueOf(preStr) + (lastInt + 1);
    } catch (Exception e) {
      logger.error("字符转数据失败：" + e.getMessage());
      return null;
    } 
  }
  
  public int delelteAll(String organization, HttpServletRequest request) throws Exception {
    List<JSONObject> listall = getAllsfxmSelectOrg(organization);
    StringBuffer seqIdBf = new StringBuffer();
    for (JSONObject json : listall) {
      String seq_id = json.getString("seq_id");
      if (YZUtility.isNullorEmpty(seq_id))
        continue; 
      seqIdBf.append(seq_id).append(",");
    } 
    return delelteBySeqIds(seqIdBf.toString(), request);
  }
  
  public int delelteBySeqIds(String seqIds, HttpServletRequest request) throws Exception {
    boolean isSingleDel = true;
    List<String> itemnoList = getItemnosBySeqIds(seqIds);
    if (itemnoList.size() > 1)
      isSingleDel = false; 
    StringBuffer delItemnoBf = new StringBuffer();
    for (String itemno : itemnoList) {
      int count1 = this.costOrderDetailLogic.getCountByItemnos(itemno);
      if (count1 > 0) {
        if (isSingleDel)
          throw new Exception("该收费项目存在消费记录，无法删除，项目编号为：" + itemno); 
        logger.error("该收费项目存在消费记录，无法删除，项目编号为：" + itemno);
      } 
      int count2 = this.giveItemLogic.getCountByItemnos(itemno);
      if (count2 > 0) {
        if (isSingleDel)
          throw new Exception("该收费项目作为赠送项目，无法删除，项目编号为：" + itemno); 
        logger.error("该收费项目作为赠送项目，无法删除，项目编号为：" + itemno);
      } 
      int count3 = this.tcLogic.getCountByItemnos(itemno);
      if (count3 > 0) {
        if (isSingleDel)
          throw new Exception("该收费项目存在收费套餐中，无法删除，项目编号为：" + itemno); 
        logger.error("该收费项目存在收费套餐中，无法删除，项目编号为：" + itemno);
      } 
      if (count1 == 0 && count2 == 0 && count3 == 0)
        delItemnoBf.append(itemno).append(","); 
    } 
    int count = deleteByItemno(delItemnoBf.toString());
    BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_TREATITEM, delItemnoBf.toString(), TableNameUtil.KQDS_TREATITEM, request);
    return count;
  }
  
  public int deleteByItemno(String itemnos) throws Exception {
    return ((Integer)this.dao.delete(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".deleteByItemno", YZUtility.ConvertStringIds4Query(itemnos))).intValue();
  }
  
  public List<String> getItemnosBySeqIds(String seqIds) throws Exception {
    List<String> itemnoList = new ArrayList<>();
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getItemnosBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
    for (JSONObject json : list) {
      String treatitemno = json.getString("treatitemno");
      if (YZUtility.isNullorEmpty(treatitemno))
        continue; 
      itemnoList.add(treatitemno);
    } 
    return itemnoList;
  }
  
  public List<JSONObject> getAllsfxmSelect(String organization) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getAllsfxmSelect", organization);
    return list;
  }
  
  public List<JSONObject> getAllsfxmSelectOrg(String organization) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getAllsfxmSelectOrg", organization);
    return list;
  }
  
  public List<JSONObject> getSfxmSelectParam(String basetype, String nexttype, String organization) throws Exception {
    Map<String, String> map1 = new HashMap<>();
    map1.put("basetype", basetype);
    map1.put("nexttype", nexttype);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getSfxmSelectParam", map1);
    return list;
  }
  
  public List<KqdsTreatitem> getTreatItemListByNextType(String nexttype, String search, String noyjj) throws Exception {
    Map<String, String> map1 = new HashMap<>();
    if (!YZUtility.isNullorEmpty(search))
      map1.put("treatitemname", search); 
    if (!YZUtility.isNullorEmpty(noyjj))
      map1.put("isyjjitem", noyjj); 
    map1.put("nexttype", nexttype);
    List<KqdsTreatitem> list = (List<KqdsTreatitem>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getTreatItemListByNextType", map1);
    for (KqdsTreatitem kqds_TreatItem : list)
      kqds_TreatItem.setTreatitemname(String.valueOf(kqds_TreatItem.getTreatitemname()) + "【￥" + kqds_TreatItem.getUnitprice() + "】"); 
    return list;
  }
  
  public KqdsTreatitem getTreatItem(String basetype, String nexttype, String itemname) throws Exception {
    Map<String, String> map1 = new HashMap<>();
    if (!YZUtility.isNullorEmpty(itemname))
      map1.put("itemname", itemname); 
    map1.put("basetype", basetype);
    map1.put("nexttype", nexttype);
    List<KqdsTreatitem> list = (List<KqdsTreatitem>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getTreatItem", map1);
    if (list != null && list.size() > 0)
      return list.get(0); 
    return null;
  }
  
  public int getCountByNextType(String nexttype, String search) throws Exception {
    Map<String, String> map1 = new HashMap<>();
    if (!YZUtility.isNullorEmpty(search))
      map1.put("treatitemname", search); 
    map1.put("nexttype", nexttype);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getCountByNextType", map1)).intValue();
    return count;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    for (JSONObject obj : list) {
      String istsxm = obj.getString("istsxm");
      if (istsxm != null && istsxm.equals("1")) {
        obj.put("istsxm", "是");
      } else {
        obj.put("istsxm", "否");
      } 
      String issplit = obj.getString("issplit");
      if (issplit != null && issplit.equals("1")) {
        obj.put("issplit", "是");
      } else {
        obj.put("issplit", "否");
      } 
      String useflag = obj.getString("useflag");
      if (useflag != null && useflag.equals("1")) {
        obj.put("useflag", "是");
      } else {
        obj.put("useflag", "否");
      } 
      String isyjjitem = obj.getString("isyjjitem");
      if (!YZUtility.isNullorEmpty(isyjjitem)) {
        if (isyjjitem.equals(ConstUtil.COST_ITEM_1)) {
          obj.put("isyjjitem", "预交金");
          continue;
        } 
        if (isyjjitem.equals(ConstUtil.COST_ITEM_2)) {
          obj.put("isyjjitem", "挂号");
          continue;
        } 
        if (isyjjitem.equals(ConstUtil.COST_ITEM_3)) {
          obj.put("isyjjitem", "治疗费");
          continue;
        } 
        if (isyjjitem.equals(ConstUtil.COST_ITEM_4)) {
          obj.put("isyjjitem", "生成回访");
          continue;
        } 
        obj.put("isyjjitem", "");
      } 
    } 
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> getItemList(String querydata, String organization) throws Exception {
    Map<String, String> map1 = new HashMap<>();
    if (!YZUtility.isNullorEmpty(querydata))
      map1.put("querydata", querydata); 
    map1.put("organization", organization);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getItemList", map1);
    return list;
  }
  
  public String checkNextType(String typename, String baseno) throws Exception {
    String seq_id = "";
    Map<String, String> map1 = new HashMap<>();
    if (!YZUtility.isNullorEmpty(typename))
      map1.put("typename", typename); 
    if (!YZUtility.isNullorEmpty(baseno))
      map1.put("baseno", baseno); 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".checkNextType", map1);
    if (list != null && list.size() > 0)
      seq_id = ((JSONObject)list.get(0)).getString("seq_id"); 
    return seq_id;
  }
  
  public int countByTreatItemno(String itemno) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".countByTreatItemno", itemno)).intValue();
    return count;
  }
  
  public KqdsTreatitem getByTreatItemno(String itemno) throws Exception {
    KqdsTreatitem item = (KqdsTreatitem)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getByTreatItemno", itemno);
    return item;
  }
  
  public List<KqdsTreatitem> getTreatItemInfroList(Collection collection) throws Exception {
    List<KqdsTreatitem> list = (List<KqdsTreatitem>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM) + ".getTreatItemInfroList", collection);
    return list;
  }
  
  public void changeDrugsUserflag(String id) throws Exception {
    this.dao.update("KQDS_TREATITEM.changeDrugsUserflag", id);
  }
  
  public void recoverDrugsUserflag(String id) throws Exception {
    this.dao.update("KQDS_TREATITEM.recoverDrugsUserflag", id);
  }
}
