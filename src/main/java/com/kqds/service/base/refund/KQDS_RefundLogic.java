package com.kqds.service.base.refund;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_RefundLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private KQDS_CostOrder_DetailLogic detaillogic;
  
  public List<JSONObject> selectWithNopage(String table, Map<String, String> map, YZPerson person, String visualstaff, String organization)
    throws Exception
  {
    map.put("organization", organization);
    map.put("visualstaff", visualstaff);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REFUND + ".selectWithNopage", map);
    for (JSONObject jobj : list)
    {
      BigDecimal zsmoney = this.detaillogic.searchOrderZs(TableNameUtil.KQDS_COSTORDER_DETAIL, jobj.getString("costno"));
      jobj.put("zsmoney", zsmoney);
      jobj.put("ssmoney", KqdsBigDecimal.sub(new BigDecimal(jobj.getString("actualmoney")), zsmoney));
    }
    return list;
  }
  
  public List<JSONObject> selectByCostno(String kQDS_REFUND, String costno)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_REFUND + ".selectByCostno", costno);
  }
  
  public List<JSONObject> tkQuery(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_REFUND_DETAIL + ".tkQuery", map);
  }
}
