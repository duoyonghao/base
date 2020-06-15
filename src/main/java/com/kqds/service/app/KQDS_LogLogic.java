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
public class KQDS_LogLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public JSONObject getPushListPage(String table, Map<String, String> map) throws Exception {
    int pageSize = Integer.parseInt(map.get("pageSize"));
    int nowpage = Integer.parseInt(map.get("nowpage"));
    map.put("num1", (new StringBuilder(String.valueOf(pageSize))).toString());
    map.put("num2", (new StringBuilder(String.valueOf(nowpage * pageSize))).toString());
    JSONObject jobj = new JSONObject();
    if (map.containsKey("ispaging") && (
      (String)map.get("ispaging")).equals("1") && 
      nowpage == 0) {
      int total = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_PUSH) + ".pushNoread", null)).intValue();
      jobj.put("noreadtotal", Integer.valueOf(total));
      int cztotal = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_PUSH) + ".pushIsread", null)).intValue();
      jobj.put("readtotal", Integer.valueOf(cztotal));
    } 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_PUSH) + ".getPushListPage", map);
    jobj.put("data", list);
    return jobj;
  }
  
  public JSONObject getSysListPage(String table, Map<String, String> map) throws Exception {
    int pageSize = Integer.parseInt(map.get("pageSize"));
    int nowpage = Integer.parseInt(map.get("nowpage"));
    map.put("num1", (new StringBuilder(String.valueOf(pageSize))).toString());
    map.put("num2", (new StringBuilder(String.valueOf(nowpage * pageSize))).toString());
    JSONObject jobj = new JSONObject();
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_PUSH) + ".getSysListPage", map);
    jobj.put("data", list);
    return jobj;
  }
  
  public void updatePcpushreaded(String noreadSeqId) throws Exception {
    this.dao.update(String.valueOf(TableNameUtil.KQDS_PUSH) + ".updatePcpushreaded", noreadSeqId);
  }
}
