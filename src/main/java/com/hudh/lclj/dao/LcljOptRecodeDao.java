package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljOptRecode;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljOptRecodeDao
{
  @Autowired
  private DaoSupport dao;
  
  public int saveLcljOptRecode(LcljOptRecode lcljOptRecode)
    throws Exception
  {
    int rsCount = ((Integer)this.dao.save("HUDH_LCLJ_OPT_RECODE.insertLcljOptRecode", lcljOptRecode)).intValue();
    return rsCount;
  }
  
  public List<JSONObject> findLcljOptRecodeList(Map<String, String> dataMap)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_LCLJ_OPT_RECODE.findLcljOptRecodeList", dataMap);
    return list;
  }
  
  public void updateRemarkStus(Map<String, String> dataMap)
    throws Exception
  {
    this.dao.update("HUDH_LCLJ_OPT_RECODE.updateRemarkStus", dataMap);
  }
}
