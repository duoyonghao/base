package com.kqds.service.base.chufang;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsChufang;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_ChuFangLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public int countCfByCostno(String costno) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_CHUFANG) + ".countCfByCostno", costno)).intValue();
    return count;
  }
  
  public JSONObject searchChuFang(String table, Map<String, String> map, String visualstaff, String organization, BootStrapPage bp) throws Exception {
    map.put("starttime", String.valueOf(map.get("starttime")) + ConstUtil.TIME_START);
    map.put("endtime", String.valueOf(map.get("endtime")) + ConstUtil.TIME_END);
    if (map.containsKey("searchvalue")) {
      map.put("p1", YZAuthenticator.phonenumberLike("userd.PhoneNumber1", map.get("searchvalue")));
      map.put("p2", YZAuthenticator.phonenumberLike("userd.PhoneNumber2", map.get("searchvalue")));
    } 
    if (!map.containsKey("m.createuser"))
      map.put("visualstaff", visualstaff); 
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    if (!YZUtility.isNullorEmpty(map.get("sortName"))) {
      if (((String)map.get("sortName")).equals("usercode"))
        map.put("sortName", "m.usercode"); 
      if (((String)map.get("sortName")).equals("username"))
        map.put("sortName", "userd.username"); 
      if (((String)map.get("sortName")).equals("itemname"))
        map.put("sortName", "m.itemname"); 
      if (((String)map.get("sortName")).equals("cfusagename"))
        map.put("sortName", "kcit1.dict_name"); 
      if (((String)map.get("sortName")).equals("cfuselevel"))
        map.put("sortName", "m.cfuselevel"); 
      if (((String)map.get("sortName")).equals("cfusemethodname"))
        map.put("sortName", "kcit2.dict_name"); 
      if (((String)map.get("sortName")).equals("phonenumber1"))
        map.put("sortName", "userd.phonenumber1"); 
      if (((String)map.get("sortName")).equals("docdept"))
        map.put("sortName", "d.dept_name"); 
      if (((String)map.get("sortName")).equals("createuser"))
        map.put("sortName", "p.user_name"); 
      if (((String)map.get("sortName")).equals("createtime"))
        map.put("sortName", "m.createtime"); 
    } 
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CHUFANG_DETAIL) + ".searchChuFang", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj1 = new JSONObject();
    jobj1.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj1.put("rows", list);
    return jobj1;
  }
  
  public KqdsChufang findNextOrderNumber() throws Exception {
    KqdsChufang kqdsChufang = (KqdsChufang)this.dao.findForObject("KQDS_CHUFANG.findNextOrderNumber", null);
    return kqdsChufang;
  }
  
  public JSONObject getRecipeCodeById(String costno) throws Exception {
    JSONObject json = (JSONObject)this.dao.findForObject("KQDS_CHUFANG.getRecipeCodeById", costno);
    return json;
  }
  
  public List<JSONObject> findChuFangInfor(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CHUFANG_DETAIL) + ".findChuFangInfor", map);
    return list;
  }
  
  public List<JSONObject> findChuFangByUsercodes(String usercodes) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CHUFANG) + ".findChuFangByUsercodes", usercodes);
    return list;
  }
  
  public List<JSONObject> findChuFangDetailByUsercodes(String usercodes) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CHUFANG_DETAIL) + ".findChuFangDetailByUsercodes", usercodes);
    return list;
  }
}
