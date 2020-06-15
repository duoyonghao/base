package com.hudh.ykzz.dao;

import com.hudh.ykzz.entity.YkzzDrugsInExamine;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YkzzDrugsInExamineDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertDrugsInExamine(YkzzDrugsInExamine ykzzDrugsInExamine)
    throws Exception
  {
    return ((Integer)this.dao.save("HUDH_YKZZ_DRUGS_IN_EXAMINE.insertDrugsInExamine", ykzzDrugsInExamine)).intValue();
  }
  
  public List<JSONObject> findDrugsInExamineByInId(String drugsInId)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN_EXAMINE.findDrugsInExamineByInId", drugsInId);
    return list;
  }
  
  public void deleteDrugsInExamineById(String id)
    throws Exception
  {
    this.dao.delete("HUDH_YKZZ_DRUGS_IN_EXAMINE.deleteDrugsInExamineById", id);
  }
  
  public void deleteDrugsInExamineByInId(String drugsInId)
    throws Exception
  {
    this.dao.delete("HUDH_YKZZ_DRUGS_IN_EXAMINE.deleteDrugsInExamineByInId", drugsInId);
  }
  
  public List<JSONObject> findDugsExamineDetail(Map<String, String> dataMap)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN_EXAMINE.findDugsExamineDetail", dataMap);
    return list;
  }
  
  public List<JSONObject> findDugsReturnDetail(Map<String, String> dataMap)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_YKZZ_DRUGS_IN_EXAMINE.findDugsReturnDetail", dataMap);
    return list;
  }
}
