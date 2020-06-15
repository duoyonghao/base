package com.kqds.service.sys.dict;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDict;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.sys.DictUtil;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZDictCostLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> getLeve1SortList4Manager(String organization)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DICT + ".getLeve1SortList4Manager", organization);
    return list;
  }
  
  public List<JSONObject> getLeve2SortList4Manager(String parentCode, String search, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("parentCode", parentCode);
    json.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DICT + ".getLeve2SortList4Manager", json);
    return list;
  }
  
  public List<YZDict> getLeve1SortListOrg(String search, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("search", search);
    json.put("organization", organization);
    List<YZDict> list = (List)this.dao.findForList(TableNameUtil.SYS_DICT + ".getLeve1SortListOrg", json);
    return list;
  }
  
  public List<YZDict> getLeve2SortListOrg(String parentCode, String search, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("search", search);
    json.put("parentCode", parentCode);
    json.put("organization", organization);
    List<YZDict> list = (List)this.dao.findForList(TableNameUtil.SYS_DICT + ".getLeve2SortListOrg", json);
    return list;
  }
  
  public List<YZDict> getLeve1SortList(String search, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("parentCode", DictUtil.COSTITEM_SORT);
    json.put("search", search);
    json.put("organization", organization);
    List<YZDict> list = (List)this.dao.findForList(TableNameUtil.SYS_DICT + ".getLeve1SortList", json);
    return list;
  }
  
  public List<YZDict> getLeve2SortList(String parentCode, String search, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("search", search);
    json.put("parentCode", parentCode);
    json.put("organization", organization);
    List<YZDict> list = (List)this.dao.findForList(TableNameUtil.SYS_DICT + ".getLeve2SortList", json);
    return list;
  }
  
  public String getBaseType(String iscustomer, String parentCode, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("iscustomer", iscustomer);
    json.put("parentCode", parentCode);
    json.put("organization", organization);
    return (String)this.dao.findForObject(TableNameUtil.SYS_DICT + ".getBaseType", json);
  }
  
  public int checkBaseType(String dictName)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.SYS_DICT + ".checkBaseType", dictName)).intValue();
    return count;
  }
  
  public String checkBaseTypeGetSeqId(String dictName)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DICT + ".checkBaseTypeGetSeqId", dictName);
    if (list.size() > 0) {
      return ((JSONObject)list.get(0)).getString("seq_id");
    }
    return "";
  }
  
  public String checkNextType(String dictName, String parentCode)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("dictName", dictName);
    json.put("parentCode", parentCode);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DICT + ".checkNextType", json);
    if (list.size() > 0) {
      return ((JSONObject)list.get(0)).getString("dictCode");
    }
    return null;
  }
}
