package com.kqds.service.app;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("logLogic")
public class KQDS_LogLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject getPushListPage(String table, Map<String, String> map)
    throws Exception
  {
    int pageSize = Integer.parseInt((String)map.get("pageSize"));
    int nowpage = Integer.parseInt((String)map.get("nowpage"));
    map.put("num1", pageSize);
    map.put("num2", nowpage * pageSize);
    
    JSONObject jobj = new JSONObject();
    if ((map.containsKey("ispaging")) && 
      (((String)map.get("ispaging")).equals("1")) && 
      (nowpage == 0))
    {
      int total = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_PUSH + ".pushNoread", null)).intValue();
      jobj.put("noreadtotal", Integer.valueOf(total));
      
      int cztotal = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_PUSH + ".pushIsread", null)).intValue();
      jobj.put("readtotal", Integer.valueOf(cztotal));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_PUSH + ".getPushListPage", map);
    jobj.put("data", list);
    return jobj;
  }
  
  public JSONObject getSysListPage(String table, Map<String, String> map)
    throws Exception
  {
    int pageSize = Integer.parseInt((String)map.get("pageSize"));
    int nowpage = Integer.parseInt((String)map.get("nowpage"));
    map.put("num1", pageSize);
    map.put("num2", nowpage * pageSize);
    JSONObject jobj = new JSONObject();
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_PUSH + ".getSysListPage", map);
    jobj.put("data", list);
    return jobj;
  }
  
  public void updatePcpushreaded(String noreadSeqId)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_PUSH + ".updatePcpushreaded", noreadSeqId);
  }
}
