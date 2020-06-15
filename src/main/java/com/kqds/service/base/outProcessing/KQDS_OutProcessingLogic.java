package com.kqds.service.base.outProcessing;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsOutprocessing;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.outProcessingSheetDetail.KQDS_OutProcessingSheet_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.ArrayList;
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
public class KQDS_OutProcessingLogic extends BaseLogic {
  private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessingLogic.class);
  
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private KQDS_OutProcessingSheet_DetailLogic detailLogic;
  
  public int delelteAll(String organization, HttpServletRequest request) throws Exception {
    List<JSONObject> listall = getItemListByTypeCodes(null, organization);
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
    int count = 0;
    for (String wjgxmbh : itemnoList) {
      int count1 = this.detailLogic.getCountByItemCodes(wjgxmbh);
      if (count1 > 0) {
        if (isSingleDel)
          throw new Exception("该加工项目存在加工单，无法删除！"); 
        logger.error("该加工项目存在加工单，无法删除！");
        continue;
      } 
      this.dao.delete(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".deletewjgxmbh", wjgxmbh);
      count++;
    } 
    return count;
  }
  
  public List<String> getItemnosBySeqIds(String seqIds) throws Exception {
    List<String> itemnoList = new ArrayList<>();
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".getItemnosBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
    for (JSONObject json : list) {
      String wjgxmbh = json.getString("wjgxmbh");
      if (YZUtility.isNullorEmpty(wjgxmbh))
        continue; 
      itemnoList.add(wjgxmbh);
    } 
    return itemnoList;
  }
  
  public List<JSONObject> getItemListByTypeCodes(String codes, String organization) throws Exception {
    Map<String, String> map = new HashMap<>();
    if (YZUtility.isNotNullOrEmpty(codes))
      map.put("wjgfl", YZUtility.ConvertStringIds4Query(codes)); 
    map.put("organization", organization);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".getItemListByTypeCodes", map);
    return list;
  }
  
  public int deleteByUnitCode(String unitCode, HttpServletRequest request) throws Exception {
    int count = ((Integer)this.dao.delete(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".deleteByUnitCode", unitCode)).intValue();
    return count;
  }
  
  public int deleteByTypeCode(String typecode, HttpServletRequest request) throws Exception {
    int count = ((Integer)this.dao.delete(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".deleteByUnitCode", typecode)).intValue();
    return count;
  }
  
  public List<KqdsOutprocessing> getjgItemListByType(String wjgfl, String search, String isAdd, String organization) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("wjgfl", wjgfl);
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    if (!YZUtility.isNullorEmpty(isAdd))
      map.put("isadd", isAdd); 
    if (!YZUtility.isNullorEmpty(search))
      map.put("search", search); 
    List<KqdsOutprocessing> list = (List<KqdsOutprocessing>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".getjgItemListByType", map);
    return list;
  }
  
  public int countjgItemListByType(String wjgfl, String search, String isAdd, String organization) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("wjgfl", wjgfl);
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    if (!YZUtility.isNullorEmpty(isAdd))
      map.put("isadd", isAdd); 
    if (!YZUtility.isNullorEmpty(search))
      map.put("search", search); 
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".countjgItemListByType", map)).intValue();
    return count;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectByitem(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".selectByitem", map);
    return list;
  }
  
  public int countByCode(String wjgxmbh) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_OUTPROCESSING) + ".countByCode", wjgxmbh)).intValue();
    return count;
  }
}
