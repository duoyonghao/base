package com.kqds.service.sys.fkfs;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fkfsLogic")
public class YZFkfsLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, String organization)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FKFS + ".selectWithPage", organization);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectList(String table, Map<String, String> filter)
    throws Exception
  {
    Map<String, Map<String, String>> map = new HashMap();
    map.put("params", filter);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FKFS + ".selectList", map);
    return list;
  }
  
  public List<JSONObject> getMemberFkfsList(String table)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FKFS + ".getMemberFkfsList", null);
    return list;
  }
  
  public String selectSeqId4costfield(String costfield)
    throws Exception
  {
    String result = "";
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FKFS + ".selectSeqId4costfield", costfield);
    if ((list != null) && (list.size() > 0)) {
      result = ((JSONObject)list.get(0)).getString("seq_id");
    }
    return result;
  }
  
  public String selectSeqId4Memberfield(String memberfield)
    throws Exception
  {
    String result = "";
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FKFS + ".selectSeqId4Memberfield", memberfield);
    if ((list != null) && (list.size() > 0)) {
      result = ((JSONObject)list.get(0)).getString("seq_id");
    }
    return result;
  }
  
  public JSONObject getCostField()
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FKFS + ".getCostField", null);
    JSONObject json = new JSONObject();
    for (JSONObject job : list)
    {
      String value = YZUtility.parseString(job.getString("payname"));
      String name = YZUtility.parseString(job.getString("costfield"));
      json.put(name, value);
    }
    return json;
  }
  
  public JSONObject getMemberField()
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_FKFS + ".getMemberField", null);
    JSONObject json = new JSONObject();
    for (JSONObject job : list)
    {
      String value = YZUtility.parseString(job.getString("payname"));
      String name = YZUtility.parseString(job.getString("memberfield"));
      json.put(name, value);
    }
    return json;
  }
  
  public int updateFlagBySeqIds(String seqids, String useflag, HttpServletRequest request)
    throws Exception
  {
    List<String> idList = YZUtility.ConvertString2List(seqids);
    JSONObject json = new JSONObject();
    json.put("useflag", useflag);
    json.put("idList", idList);
    int count = ((Integer)this.dao.update(TableNameUtil.SYS_FKFS + ".updateFlagBySeqIds", json)).intValue();
    
    SysLogUtil.log(SysLogUtil.UPDATE_STATUS, SysLogUtil.SYS_DICT, seqids, TableNameUtil.SYS_DICT, request);
    return count;
  }
}
