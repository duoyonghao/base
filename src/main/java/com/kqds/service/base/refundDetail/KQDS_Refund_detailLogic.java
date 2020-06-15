package com.kqds.service.base.refundDetail;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Refund_detailLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectWithNopage(String table, Map<String, String> map, YZPerson person) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_REFUND_DETAIL) + ".selectWithNopage", map);
    return list;
  }
  
  public List<JSONObject> selectWithNopage4(String table, Map<String, String> map, YZPerson person) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_REFUND_DETAIL) + ".selectWithNopage4", map);
    return list;
  }
}
