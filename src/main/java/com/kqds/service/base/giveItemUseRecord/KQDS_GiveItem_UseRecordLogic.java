package com.kqds.service.base.giveItemUseRecord;

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
public class KQDS_GiveItem_UseRecordLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public int getCountByItemnos(String itemnos) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_GIVEITEM_USERECORD) + ".getCountByItemnos", YZUtility.ConvertStringIds4Query(itemnos))).intValue();
    return count;
  }
  
  public List<JSONObject> getUserRecordByMemberno(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_GIVEITEM_USERECORD) + ".selectWithPage", map);
    for (JSONObject job : list) {
      String status = job.getString("status");
      if ("1".equals(status)) {
        status = "已操作";
      } else {
        status = "未操作";
      } 
      job.put("status", status);
    } 
    return list;
  }
}
