package com.kqds.service.base.sms;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Sms_ModelLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> noSelectWithPage(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_SMS_MODEL) + ".getListsql", map);
    for (JSONObject job : list) {
      String isdsmodel = job.getString("isdsmodel");
      if (isdsmodel.equals("1")) {
        job.put("isdsmodelname", "是");
        continue;
      } 
      job.put("isdsmodelname", "否");
    } 
    return list;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_SMS_MODEL) + ".getListsql", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    for (JSONObject job : list) {
      String isdsmodel = job.getString("isdsmodel");
      if (isdsmodel.equals("1")) {
        job.put("isdsmodelname", "是");
        continue;
      } 
      job.put("isdsmodelname", "否");
    } 
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> getDsmodel(String smsnexttype) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_SMS_MODEL) + ".getDsmodel", smsnexttype);
    return list;
  }
}
