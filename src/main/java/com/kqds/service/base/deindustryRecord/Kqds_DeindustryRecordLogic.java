package com.kqds.service.base.deindustryRecord;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Kqds_DeindustryRecordLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private YZPersonLogic personLogic;
  
  public List<JSONObject> selectNoPage(String table, Map<String, String> map, String organization)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_DEINDUSTRYRECORD + ".selectNoPage", map);
    for (JSONObject job : list)
    {
      String activitycontacts = job.getString("activitycontacts");
      String data = this.personLogic.getNameStrBySeqIds(activitycontacts);
      job.put("activitycontactsname", data);
    }
    return list;
  }
  
  public List<JSONObject> selectTrscColumn(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_DEINDUSTRYRECORD + ".selectTrscColumn", map);
    return list;
  }
}
