package com.kqds.service.base.outProcessingUnit;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsOutprocessingUnit;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.outProcessing.KQDS_OutProcessingLogic;
import com.kqds.service.base.outProcessing.KQDS_OutProcessing_TypeLogic;
import com.kqds.service.base.outProcessingSheetDetail.KQDS_OutProcessingSheet_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.ChineseCharToEn;
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
public class KQDS_outProcessing_UnitLogic
  extends BaseLogic
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_outProcessing_UnitLogic.class);
  @Autowired
  private DaoSupport dao;
  @Autowired
  private KQDS_OutProcessingSheet_DetailLogic detailLogic;
  @Autowired
  private KQDS_OutProcessing_TypeLogic typeLogic;
  @Autowired
  private KQDS_OutProcessingLogic itemLogic;
  
  public int delelteBySeqIds(String seqIds, HttpServletRequest request)
    throws Exception
  {
    boolean isSingleDel = true;
    List<String> codeList = getUnitCodeListBySeqIds(seqIds);
    if (codeList.size() > 1) {
      isSingleDel = false;
    }
    int count = 0;
    for (String code : codeList)
    {
      int count1 = this.detailLogic.getCount(code);
      if (count1 > 0)
      {
        if (isSingleDel) {
          throw new Exception("该加工厂存在加工单，无法删除！");
        }
        logger.error("该收费项目存在消费记录，无法删除！");
      }
      else
      {
        this.dao.delete(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".deletecode", code);
        
        this.typeLogic.deleteByUnitCode(code, request);
        

        this.itemLogic.deleteByUnitCode(code, request);
        
        count++;
      }
    }
    return count;
  }
  
  public List<String> getUnitCodeListBySeqIds(String seqIds)
    throws Exception
  {
    List<String> codelist = new ArrayList();
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getUnitCodeListBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
    for (JSONObject json : list)
    {
      String code = json.getString("code");
      if (!YZUtility.isNullorEmpty(code)) {
        codelist.add(code);
      }
    }
    return codelist;
  }
  
  public int countByCode(KqdsOutprocessingUnit dp)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".countByCode", dp)).intValue();
    return count;
  }
  
  public int checkCode(String seqId, String code, String table)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("seqId", seqId);
    map.put("code", code);
    int num = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".checkCode", map)).intValue();
    return num;
  }
  
  public int countByOrgAndName(KqdsOutprocessingUnit dp)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".countByOrgAndName", dp)).intValue();
    return count;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> getUnitList4Back(String isAdd, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(isAdd)) {
      map.put("isadd", isAdd);
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getUnitList4Back", map);
    return list;
  }
  
  public List<JSONObject> getUnitList(String isAdd, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(isAdd)) {
      map.put("isadd", isAdd);
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getUnitList", map);
    return list;
  }
  
  public KqdsOutprocessingUnit getJgcByName(String name, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(name)) {
      map.put("name", name);
    }
    List<KqdsOutprocessingUnit> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getJgcByName", map);
    if ((list != null) && (list.size() > 0)) {
      return (KqdsOutprocessingUnit)list.get(0);
    }
    return null;
  }
  
  public KqdsOutprocessingUnit getJgcByCode(String code, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    map.put("code", code);
    List<KqdsOutprocessingUnit> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getJgcByCode", map);
    if ((list != null) && (list.size() > 0)) {
      return (KqdsOutprocessingUnit)list.get(0);
    }
    return null;
  }
  
  public String getUniCodeByName(String name)
    throws Exception
  {
    String code = ChineseCharToEn.getAllFirstLetter_RandNum(name);
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getUniCodeByName", code)).intValue();
    if (count == 0) {
      return code;
    }
    return getUniCodeByName(name);
  }
  
  public List<KqdsOutprocessingUnit> getJgcDictList4Back(String search, String mrjgc, String isAdd, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(mrjgc)) {
      map.put("mrjgc", mrjgc);
    }
    if (!YZUtility.isNullorEmpty(isAdd)) {
      map.put("isadd", isAdd);
    }
    if (!YZUtility.isNullorEmpty(search)) {
      map.put("search", search);
    }
    List<KqdsOutprocessingUnit> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getJgcDictList4Back", map);
    return list;
  }
  
  public List<KqdsOutprocessingUnit> getJgcDictList(String search, String mrjgc, String isAdd, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("organization", organization);
    if (!YZUtility.isNullorEmpty(mrjgc)) {
      map.put("mrjgc", mrjgc);
    }
    if (!YZUtility.isNullorEmpty(isAdd)) {
      map.put("isadd", isAdd);
    }
    if (!YZUtility.isNullorEmpty(search)) {
      map.put("search", search);
    }
    List<KqdsOutprocessingUnit> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getJgcDictList", map);
    return list;
  }
}
