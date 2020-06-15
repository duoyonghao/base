package com.kqds.service.base.lltj;

import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Kqds_LLTJLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private KQDS_CostOrder_DetailLogic detailLogic;
  
  public List selectTreatDetailListNopage(YZPerson person, Map<String, String> map, String visualstaff, String organization) throws Exception {
    if (map.containsKey("queryinput")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
    } 
    map.put("visualstaff", visualstaff);
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_LLTJ) + ".selectTreatDetailListNopage", map);
    for (JSONObject json : list) {
      Map<String, String> clsNameMap = this.detailLogic.getClassName(json.getString("itemno"), json.getString("itemname"), organization);
      json.put("classname", clsNameMap.get("basetypeName"));
      json.put("nextname", clsNameMap.get("nexttypeName"));
      json.put("cjstatus", json.getString("cjstatus").equals("0") ? "未成交" : "已成交");
      json.put("type", json.getString("type").equals("0") ? "否" : "是");
    } 
    return list;
  }
  
  public List getCostDetailList4LltjChaifen(String table, YZPerson person, Map<String, String> map, String organization) throws Exception {
    if (map.containsKey("queryinput")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
    } 
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_LLTJ) + ".getCostDetailList4LltjChaifen", map);
    return list;
  }
}
