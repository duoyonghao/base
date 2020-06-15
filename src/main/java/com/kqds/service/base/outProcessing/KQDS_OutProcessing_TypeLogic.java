package com.kqds.service.base.outProcessing;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsOutprocessingType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.outProcessingSheetDetail.KQDS_OutProcessingSheet_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
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
public class KQDS_OutProcessing_TypeLogic
  extends BaseLogic
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessing_TypeLogic.class);
  @Autowired
  private DaoSupport dao;
  @Autowired
  private KQDS_OutProcessingLogic itemLogic;
  @Autowired
  private KQDS_OutProcessingSheet_DetailLogic detailLogic;
  
  public int delelteBySeqIds(String seqIds, HttpServletRequest request)
    throws Exception
  {
    boolean isSingleDel = true;
    List<String> codeList = getTypeCodeListBySeqIds(seqIds);
    if (codeList.size() > 1) {
      isSingleDel = false;
    }
    int count = 0;
    for (String code : codeList)
    {
      List<JSONObject> itemList = this.itemLogic.getItemListByTypeCodes(code, ChainUtil.getOrganizationFromUrlCanNull(request));
      StringBuffer itemCodesStr = new StringBuffer();
      for (JSONObject item : itemList)
      {
        String wjgxmbh = item.getString("wjgxmbh");
        itemCodesStr.append(wjgxmbh).append(",");
      }
      int count1 = this.detailLogic.getCountByItemCodes(itemCodesStr.toString());
      if (count1 > 0)
      {
        if (isSingleDel) {
          throw new Exception("该加工分类存在加工单，无法删除！");
        }
        logger.error("该加工分类存在加工单，无法删除！");
      }
      else
      {
        this.dao.delete(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".deletecode", code);
        
        this.itemLogic.deleteByTypeCode(code, request);
        
        count++;
      }
    }
    return count;
  }
  
  public List<String> getTypeCodeListBySeqIds(String seqIds)
    throws Exception
  {
    List<String> codelist = new ArrayList();
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".getTypeCodeListBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
    for (JSONObject json : list)
    {
      String code = json.getString("typeno");
      if (!YZUtility.isNullorEmpty(code)) {
        codelist.add(code);
      }
    }
    return codelist;
  }
  
  public int deleteByUnitCode(String unitCode, HttpServletRequest request)
    throws Exception
  {
    int count = ((Integer)this.dao.delete(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".deleteByUnitCode", unitCode)).intValue();
    return count;
  }
  
  public int countByCode(KqdsOutprocessingType dp)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".countByCode", dp)).intValue();
    return count;
  }
  
  public int countByOrgAndName(KqdsOutprocessingType dp)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".countByOrgAndName", dp)).intValue();
    return count;
  }
  
  public List<KqdsOutprocessingType> getjgTypeList(String class_no, String search, String isAdd, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("processingfactory", class_no);
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    if (!YZUtility.isNullorEmpty(isAdd)) {
      map.put("isadd", isAdd);
    }
    if (!YZUtility.isNullorEmpty(search)) {
      map.put("search", search);
    }
    List<KqdsOutprocessingType> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".getjgTypeList", map);
    return list;
  }
  
  public int countJgTypeList(String code, String search, String isAdd, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("processingfactory", code);
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    if (!YZUtility.isNullorEmpty(isAdd)) {
      map.put("isadd", isAdd);
    }
    if (!YZUtility.isNullorEmpty(search)) {
      map.put("search", search);
    }
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".countJgTypeList", map)).intValue();
    return count;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public String checkJgcType(String typename, String jgc, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("typename", typename);
    map.put("jgc", jgc);
    map.put("organization", organization);
    String class_no = null;
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".checkJgcType", map);
    if ((list != null) && (list.size() > 0)) {
      class_no = ((JSONObject)list.get(0)).getString("typeno");
    }
    return class_no;
  }
  
  public KqdsOutprocessingType getJgcTypeByName(String jgcCode, String name)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("typename", name);
    map.put("jgc", jgcCode);
    List<KqdsOutprocessingType> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".getJgcTypeByName", map);
    if ((list != null) && (list.size() > 0)) {
      return (KqdsOutprocessingType)list.get(0);
    }
    return null;
  }
  
  public String getUniCodeByName(String typename, String unitCode)
    throws Exception
  {
    String code = unitCode + "_" + ChineseCharToEn.getAllFirstLetter_RandNum(typename);
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".getUniCodeByName", code)).intValue();
    if (count == 0) {
      return code;
    }
    return getUniCodeByName(typename, unitCode);
  }
}
