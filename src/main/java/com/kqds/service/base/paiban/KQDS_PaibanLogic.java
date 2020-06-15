package com.kqds.service.base.paiban;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_PaibanLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectList(String table, Map<String, String> map, String organization)
    throws Exception
  {
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_PAIBAN + ".selectList", map);
    return list;
  }
  
  public int checkPaiban(Map<String, String> map, String organization)
    throws Exception
  {
    map.put("starttime", ((String)map.get("starttime")).substring(0, 10));
    map.put("organization", organization);
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_PAIBAN + ".checkPaiban", map)).intValue();
    return count;
  }
}
