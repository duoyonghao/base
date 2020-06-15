package com.kqds.service.base.tooth;

import com.kqds.dao.DaoSupport;
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
public class KQDS_Tooth_DocLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectWithPageByRegno(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_TOOTH_DOC + ".selectWithPageByRegno", map);
    return list;
  }
  
  public List<JSONObject> selectHzjl(String table, Map<String, String> map, String visualstaff, String organization)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    map.put("starttime", (String)map.get("starttime") + ConstUtil.TIME_START);
    map.put("endtime", (String)map.get("endtime") + ConstUtil.TIME_END);
    if (!map.containsKey("createuser")) {
      map.put("visualstaff", visualstaff);
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_TOOTH_DOC + ".selectHzjl", map);
    return list;
  }
}
