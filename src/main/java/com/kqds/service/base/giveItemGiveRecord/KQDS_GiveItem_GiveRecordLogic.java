package com.kqds.service.base.giveItemGiveRecord;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_GiveItem_GiveRecordLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public int getCountByItemnos(String itemnos) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_GIVEITEM_GIVERECORD) + ".getCountByItemnos", YZUtility.ConvertStringIds4Query(itemnos))).intValue();
    return count;
  }
  
  public List<JSONObject> getGiveRecordByMemberno(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_GIVEITEM_GIVERECORD) + ".selectWithPage", map);
    return list;
  }
}
