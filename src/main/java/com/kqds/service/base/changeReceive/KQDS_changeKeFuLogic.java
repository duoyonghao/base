package com.kqds.service.base.changeReceive;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_changeKeFuLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectWithPage(String table, Map<String, String> map) throws Exception {
    map.put("starttime", String.valueOf(map.get("starttime")) + ConstUtil.TIME_START);
    map.put("endtime", String.valueOf(map.get("endtime")) + ConstUtil.TIME_END);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CHANGE_KEFU) + ".selectWithPage", map);
    return list;
  }
}
