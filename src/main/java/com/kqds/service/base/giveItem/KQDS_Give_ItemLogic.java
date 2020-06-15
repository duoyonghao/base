package com.kqds.service.base.giveItem;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.VisitDataCount;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.giveItemGiveRecord.KQDS_GiveItem_GiveRecordLogic;
import com.kqds.service.base.giveItemTc.KQDS_GiveItem_TCLogic;
import com.kqds.service.base.giveItemUseRecord.KQDS_GiveItem_UseRecordLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Give_ItemLogic extends BaseLogic {
  private static Logger logger = LoggerFactory.getLogger(KQDS_Give_ItemLogic.class);
  
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private KQDS_GiveItem_TCLogic tcLogic;
  
  @Autowired
  private KQDS_GiveItem_GiveRecordLogic giveRecordLogic;
  
  @Autowired
  private KQDS_GiveItem_UseRecordLogic useRecordLogic;
  
  public List<JSONObject> getAllGiveItem(String organization) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_GIVEITEM) + ".getAllGiveItem", organization);
    return list;
  }
  
  public int delelteAll(String organization, HttpServletRequest request) throws Exception {
    List<JSONObject> listall = getAllGiveItem(organization);
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
    List<JSONObject> itemList = getItemObjBySeqIds(seqIds);
    if (itemList.size() > 1)
      isSingleDel = false; 
    StringBuffer delItemnoBf = new StringBuffer();
    for (JSONObject itemObj : itemList) {
      String itemno = itemObj.getString("itemno");
      String itemSeqId = itemObj.getString("seq_id");
      int count3 = this.tcLogic.getCountByItemnos(itemSeqId);
      if (count3 > 0) {
        if (isSingleDel)
          throw new Exception("该赠送项目存在赠送套餐中，无法删除，项目编号为：" + itemno); 
        logger.error("该赠送项目存在赠送套餐中，无法删除，项目编号为：" + itemno);
      } 
      int count1 = this.giveRecordLogic.getCountByItemnos(itemno);
      if (count1 > 0) {
        if (isSingleDel)
          throw new Exception("该赠送项目存在赠送记录，无法删除，项目编号为：" + itemno); 
        logger.error("该赠送项目存在赠送记录，无法删除，项目编号为：" + itemno);
      } 
      int count2 = this.useRecordLogic.getCountByItemnos(itemno);
      if (count2 > 0) {
        if (isSingleDel)
          throw new Exception("该赠送项目存在使用记录，无法删除，项目编号为：" + itemno); 
        logger.error("该赠送项目存在使用记录，无法删除，项目编号为：" + itemno);
      } 
      if (count1 == 0 && count2 == 0 && count3 == 0)
        delItemnoBf.append(itemno).append(","); 
    } 
    int count = deleteByItemno(delItemnoBf.toString());
    BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_GIVEITEM, delItemnoBf.toString(), TableNameUtil.KQDS_GIVEITEM, request);
    return count;
  }
  
  public int deleteByItemno(String itemnos) throws Exception {
    int count = ((Integer)this.dao.delete(String.valueOf(TableNameUtil.KQDS_GIVEITEM) + ".deleteByItemno", YZUtility.ConvertStringIds4Query(itemnos))).intValue();
    return count;
  }
  
  public List<JSONObject> getItemObjBySeqIds(String seqIds) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_GIVEITEM) + ".getItemObjBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
    return list;
  }
  
  public int getCountByItemnos(String itemnos) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_GIVEITEM) + ".getCountByItemnos", YZUtility.ConvertStringIds4Query(itemnos))).intValue();
    return count;
  }
  
  public List getGiveItemList(String organization) throws Exception {
    List<VisitDataCount> list = new ArrayList<>();
    List<JSONObject> listJson = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_GIVEITEM) + ".getGiveItemList", organization);
    for (JSONObject rs : listJson) {
      VisitDataCount d = new VisitDataCount();
      d.setHffl(rs.getString("seq_id"));
      d.setName(rs.getString("itemname"));
      list.add(d);
    } 
    return list;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_GIVEITEM) + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
}
