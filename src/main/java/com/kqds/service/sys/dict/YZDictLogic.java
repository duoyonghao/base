package com.kqds.service.sys.dict;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.sys.SysParaUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZDictLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public String getDictCodesBySeqIds(String seq_ids) throws Exception {
    String dictCodes = "";
    List<String> idList = YZUtility.ConvertString2List(seq_ids);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getDictCodesBySeqIds", idList);
    for (JSONObject rs : list)
      dictCodes = String.valueOf(dictCodes) + rs.getString("dict_code") + ","; 
    if (dictCodes.endsWith(","))
      dictCodes = dictCodes.substring(0, dictCodes.length() - 1); 
    return dictCodes;
  }
  
  public String getDictSeqIdsByDictCodes(String dictCodes) throws Exception {
    String dictSeqIds = "";
    List<String> codeList = YZUtility.ConvertString2List(dictCodes);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getDictSeqIdsByDictCodes", codeList);
    for (JSONObject rs : list)
      dictSeqIds = String.valueOf(dictSeqIds) + rs.getString("seq_id") + ","; 
    if (dictSeqIds.endsWith(","))
      dictSeqIds = dictSeqIds.substring(0, dictSeqIds.length() - 1); 
    return dictSeqIds;
  }
  
  public String getDictNamesByDictCodes(String ids) throws Exception {
    String dictNames = "";
    List<String> codeList = YZUtility.ConvertString2List(ids);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getDictNamesByDictCodes", codeList);
    for (JSONObject rs : list)
      dictNames = String.valueOf(dictNames) + rs.getString("dict_name") + ","; 
    if (dictNames.endsWith(","))
      dictNames = dictNames.substring(0, dictNames.length() - 1); 
    return dictNames;
  }
  
  public String getDictNamesBySeqIds(String ids) throws Exception {
    List<String> idList = YZUtility.ConvertString2List(ids);
    String dictNames = "";
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getDictNamesBySeqIds", idList);
    for (JSONObject rs : list)
      dictNames = String.valueOf(dictNames) + rs.getString("dict_name") + ","; 
    if (dictNames.endsWith(","))
      dictNames = dictNames.substring(0, dictNames.length() - 1); 
    return dictNames;
  }
  
  public int countByName(YZDict dp) throws Exception {
    JSONObject json = new JSONObject();
    json.put("dictName", dp.getDictName());
    json.put("seqId", dp.getSeqId());
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".countByName", json)).intValue();
    return count;
  }
  
  public int countByName(YZDict dp, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("parentCode", dp.getParentCode());
    json.put("seqId", dp.getSeqId());
    json.put("organization", organization);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".countByName", json)).intValue();
    return count;
  }
  
  public int countByCode(YZDict dp) throws Exception {
    JSONObject json = new JSONObject();
    json.put("dictCode", dp.getDictCode());
    json.put("seqId", dp.getSeqId());
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".countByName", json)).intValue();
    return count;
  }
  
  public YZDict getByNameAndParntCodeOrg(String dictName, String parentCode, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    json.put("dictName", dictName);
    json.put("organization", organization);
    YZDict dict = (YZDict)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".getByNameAndParntCodeOrg", json);
    return dict;
  }
  
  public YZDict getUniDictByCode(String dictCode) throws Exception {
    YZDict dict = (YZDict)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".getUniDictByCode", dictCode);
    return dict;
  }
  
  public String getUniDictCodeByName(String dictname) throws Exception {
    String dictCode = ChineseCharToEn.getAllFirstLetter_RandNum(dictname);
    JSONObject json = new JSONObject();
    json.put("dictCode", dictCode);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".countByName", json)).intValue();
    if (count == 0)
      return dictCode; 
    return getUniDictCodeByName(dictname);
  }
  
  public List<JSONObject> getSubListByParentCodeAndOrg(String parentCode, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    json.put("organization", organization);
    List<JSONObject> deptlist = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getSubListByParentCodeAndOrg", json);
    return deptlist;
  }
  
  public List<JSONObject> getSubListByParentCode(String parentCode, HttpServletRequest request) throws Exception {
    String noShowDict = SysParaUtil.getSysValueByName(request, SysParaUtil.NOT_SHOW_KIND_DICTS);
    List<String> idList = YZUtility.ConvertString2List(noShowDict);
    JSONObject json = new JSONObject();
    json.put("idList", idList);
    json.put("parentCode", parentCode);
    List<JSONObject> deptlist = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getSubListByParentCodeJson", json);
    return deptlist;
  }
  
  public boolean IsHaveChild(String parentCode) throws Exception {
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".countByName", json)).intValue();
    return (count > 0);
  }
  
  public boolean IsHaveChild(String parentCode, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    json.put("organization", organization);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".countByName", json)).intValue();
    return (count > 0);
  }
  
  public boolean IsHaveChild4DictTree(String parentCode) throws Exception {
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".IsHaveChild4DictTree", json)).intValue();
    return (count > 0);
  }
  
  public int countByParentCode(String parentCode) throws Exception {
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".countByName", json)).intValue();
    return count;
  }
  
  public int countByParentCode(String parentCode, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    json.put("organization", organization);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".countByName", json)).intValue();
    return count;
  }
  
  public JSONObject selectPage(BootStrapPage bp, String parentCode, String organization) throws Exception {
    String search = bp.getSearch();
    String sort = bp.getSort();
    if (YZUtility.isNullorEmpty(organization))
      organization = ""; 
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    json.put("organization", organization);
    json.put("search", search);
    json.put("sort", sort);
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".selectPage", json);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public int updateFlagBySeqIds(String seqids, String useflag, HttpServletRequest request) throws Exception {
    List<String> idList = YZUtility.ConvertString2List(seqids);
    JSONObject json = new JSONObject();
    json.put("useflag", useflag);
    json.put("idList", idList);
    int count = ((Integer)this.dao.update(String.valueOf(TableNameUtil.SYS_DICT) + ".updateFlagBySeqIds", json)).intValue();
    SysLogUtil.log(SysLogUtil.UPDATE_STATUS, SysLogUtil.SYS_DICT, seqids, TableNameUtil.SYS_DICT, request);
    return count;
  }
  
  public int deleteBySeqIds(String seqids, HttpServletRequest request) throws Exception {
    List<String> idList = YZUtility.ConvertString2List(seqids);
    int count = ((Integer)this.dao.delete(String.valueOf(TableNameUtil.SYS_DICT) + ".deleteBySeqIds", idList)).intValue();
    SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_DICT, seqids, TableNameUtil.SYS_DICT, request);
    return count;
  }
  
  public void initData(HttpServletRequest request) throws Exception {
    this.dao.delete(String.valueOf(TableNameUtil.SYS_DICT) + ".initData", null);
  }
  
  public List<YZDict> getListByParentCode(String parentCode, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("parentCode", parentCode);
    json.put("orderBy", SQLUtil.castAsInt("orderno"));
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getListByParentCode", json);
    return list;
  }
  
  public List<YZDict> getListByParentCodeIscustomer(String parentCode, String organization, String iscustomer) throws Exception {
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("parentCode", parentCode);
    json.put("iscustomer", iscustomer);
    json.put("orderBy", SQLUtil.castAsInt("orderno"));
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getListByParentCodeIscustomer", json);
    return list;
  }
  
  public List<YZDict> getListByParentCodeALL(String parentCode, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("parentCode", parentCode);
    json.put("orderBy", SQLUtil.castAsInt("orderno"));
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getListByParentCodeALL", json);
    return list;
  }
  
  public List<YZDict> getSubListByParentCode(String parentCode, String organization, String isAdd) throws Exception {
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("parentCode", parentCode);
    json.put("isAdd", isAdd);
    json.put("orderBy", SQLUtil.castAsInt("orderno"));
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getSubListByParentCode", json);
    return list;
  }
  
  public List<YZDict> getSubListByParentCodeNoOrg(String parentCode, String isAdd) throws Exception {
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    json.put("isAdd", isAdd);
    json.put("orderBy", SQLUtil.castAsInt("orderno"));
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getSubListByParentCode", json);
    return list;
  }
  
  public YZDict getDetailByDictCode(String dictCode) throws Exception {
    StringBuffer sql = new StringBuffer(" select * from " + TableNameUtil.SYS_DICT + " ");
    sql.append(" where 1=1 and dict_code = '" + dictCode + "' ");
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getDetailByDictCode", dictCode);
    if (list.size() == 0)
      throw new Exception("记录不存在"); 
    if (list.size() > 1)
      throw new Exception("数据异常，一个编号不存在多个记录"); 
    return list.get(0);
  }
  
  public YZDict getDetailByNameAndParentCode(String dictName, String parentCode) throws Exception {
    JSONObject json = new JSONObject();
    json.put("dictName", dictName);
    json.put("parentCode", parentCode);
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getDetailByNameAndParentCode", json);
    if (list.size() == 0)
      throw new Exception("数据字典不存在，字典名称为：" + dictName); 
    if (list.size() > 1)
      throw new Exception("字典数据异常，一个编号不存在多个记录，字典名称为：" + dictName); 
    return list.get(0);
  }
  
  public YZDict getDetailByNameAndParentCodeCanNull(String dictName, String parentCode) throws Exception {
    JSONObject json = new JSONObject();
    json.put("dictName", dictName);
    json.put("parentCode", parentCode);
    YZDict dict = new YZDict();
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getDetailByNameAndParentCodeCanNull", json);
    if (list != null && list.size() > 0)
      dict = list.get(0); 
    return dict;
  }
  
  public String getDictIdByNameAndParentCode(String parentCode, String dictName) throws Exception {
    String result = "";
    JSONObject json = new JSONObject();
    json.put("dictName", dictName);
    json.put("parentCode", parentCode);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getDictIdByNameAndParentCode", json);
    if (list != null && list.size() > 0)
      result = ((JSONObject)list.get(0)).getString("seq_id"); 
    return result;
  }
  
  public String getMaxOrderno(String parentCode) throws Exception {
    String result = "";
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    json.put("orderBy", SQLUtil.castAsInt("orderno"));
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getMaxOrderno", json);
    if (list != null && list.size() > 0)
      result = ((JSONObject)list.get(0)).getString("orderno"); 
    return result;
  }
  
  public String getDictNameBySeqId(String seqId) throws Exception {
    String dictName = "";
    YZDict dict = (YZDict)this.dao.loadObjSingleUUID(TableNameUtil.SYS_DICT, seqId);
    if (dict != null)
      dictName = dict.getDictName(); 
    return dictName;
  }
  
  public List<JSONObject> getDictNodeList(String parentCode, List<JSONObject> treeList, String dictIds, String organization) throws Exception {
    List<YZDict> dictList = getListByParentCode(parentCode, organization);
    for (YZDict dict : dictList) {
      boolean haveChild = IsHaveChild(dict.getDictCode());
      JSONObject node = new JSONObject();
      node.put("id", dict.getDictCode());
      node.put("pId", dict.getParentCode());
      node.put("name", dict.getDictName());
      node.put("isParent", Boolean.valueOf(haveChild));
      node.put("nocheck", Boolean.valueOf(false));
      treeList.add(node);
      if (haveChild)
        getDictNodeList(dict.getDictCode(), treeList, dictIds, organization); 
    } 
    return treeList;
  }
  
  public YZDict getTopDict(YZDict dict) throws Exception {
    if ("0".equals(dict.getParentCode()))
      return dict; 
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getTopDict", dict.getParentCode());
    if (list.size() == 0)
      throw new Exception("根据编号查询不到字典记录，编号为：" + dict.getParentCode()); 
    if (list.size() > 1)
      throw new Exception("数据异常：一个编号对应多条记录，编号为：" + dict.getParentCode()); 
    YZDict parent = list.get(0);
    return getTopDict(parent);
  }
  
  public List<YZDict> getDrugsStoreByName(String storeName) throws Exception {
    List<YZDict> list = (List<YZDict>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_DICT) + ".getDrugsStoreByName", storeName);
    return list;
  }
  
  public JSONObject findDictByDictCode(String dictCode) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".findDictByDictCode", dictCode);
    return json;
  }
  
  public String findDictNameBySeqId(String seqId) throws Exception {
    String json = (String)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_DICT) + ".findDictNameBySeqId", seqId);
    return json;
  }
}
