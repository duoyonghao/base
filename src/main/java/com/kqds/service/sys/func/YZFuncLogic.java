package com.kqds.service.sys.func;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZFunc;
import com.kqds.entity.sys.YZMenuModel;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZFuncLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> getListByParentId(String parentId)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FUNC + ".getListByParentId", parentId);
    return list;
  }
  
  public YZMenuModel queryFuncMoel(String id)
    throws Exception
  {
    List<YZMenuModel> list = (List)this.dao.findForList(TableNameUtil.SYS_FUNC + ".queryFuncMoel", id);
    if ((list != null) && (list.size() > 0)) {
      return (YZMenuModel)list.get(0);
    }
    return null;
  }
  
  public int deleteBySeqIds(String seqids, HttpServletRequest request)
    throws Exception
  {
    List<String> idList = YZUtility.ConvertString2List(seqids);
    int count = ((Integer)this.dao.delete(TableNameUtil.SYS_FUNC + ".deleteBySeqIds", idList)).intValue();
    
    SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_FUNC, seqids, TableNameUtil.SYS_FUNC, request);
    return count;
  }
  
  public boolean IsHaveChild(String id)
    throws Exception
  {
    int count1 = ((Integer)this.dao.findForObject(TableNameUtil.SYS_FUNC + ".IsHaveChild", id)).intValue();
    return count1 > 0;
  }
  
  public int countByMenuId(YZFunc dp, String menuparent, String menuCode)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("menuId", menuparent + menuCode);
    json.put("seqId", dp.getSeqId());
    int count = ((Integer)this.dao.findForObject(TableNameUtil.SYS_FUNC + ".countByMenuId", json)).intValue();
    return count;
  }
  
  public int countByMenuName(YZFunc dp, String menuparent, String funcName)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("menuparent", menuparent);
    json.put("funcName", funcName);
    json.put("seqId", dp.getSeqId());
    int count = ((Integer)this.dao.findForObject(TableNameUtil.SYS_FUNC + ".countByMenuName", json)).intValue();
    return count;
  }
  
  public JSONObject getFuncById(String menuId)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FUNC + ".getFuncById", menuId);
    if ((list != null) && (list.size() > 0)) {
      return (JSONObject)list.get(0);
    }
    return null;
  }
  
  public List<JSONObject> getAllFuncList()
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FUNC + ".getAllFuncList", null);
    return list;
  }
  
  public List<String> listAllMenu4Tree()
    throws Exception
  {
    HashSet<String> addSet = new HashSet();
    
    List<JSONObject> mlist = (List)this.dao.findForList(TableNameUtil.SYS_FUNC + ".listAllMenu4Tree", null);
    for (JSONObject func : mlist)
    {
      String menuid = func.getString("menu_id");
      menuid = menuid.trim();
      
      addSet.add(menuid);
      if (menuid.length() > 4) {
        addSet.add(menuid.substring(0, 4));
      } else if (menuid.length() > 2) {
        addSet.add(menuid.substring(0, 2));
      }
    }
    List<String> list = new ArrayList(addSet);
    
    Collections.sort(list, new Comparator()
    {
      public int compare(String arg0, String arg1)
      {
        if (YZUtility.isNullorEmpty(arg0)) {
          arg0 = "";
        }
        if (YZUtility.isNullorEmpty(arg1)) {
          arg1 = "";
        }
        return arg0.compareTo(arg1);
      }
    });
    return list;
  }
}
