package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzDrugsOut;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzDrugsOutDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertDrugsOut(YkzzDrugsOut ykzzDrugsOut)
    throws Exception
  {
    return ((Integer)this.dao.save("HUDH_YKZZ_DRUGS_OUT.insertDrugsOut", ykzzDrugsOut)).intValue();
  }
  
  public List<JSONObject> findAllDrugsOut(Map<String, String> dataMap)
    throws Exception
  {
    return (List)this.dao.findForList("HUDH_YKZZ_DRUGS_OUT.findAllDrugsOut", dataMap);
  }
  
  public void deleteDrugsOut(String id)
    throws Exception
  {
    this.dao.delete("HUDH_YKZZ_DRUGS_OUT.deleteDrugsOut", id);
  }
}
