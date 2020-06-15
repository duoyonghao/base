package com.kqds.service.base.mediaRecord;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Kqds_MediaRecordLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectNoPage(String table, Map<String, String> map, String organization)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEDIA_RECORD + ".selectNoPage", map);
    return list;
  }
  
  public List<JSONObject> selectTrscColumn(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEDIA_RECORD + ".selectTrscColumn", map);
    return list;
  }
}
