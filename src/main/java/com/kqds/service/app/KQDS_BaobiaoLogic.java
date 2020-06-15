package com.kqds.service.app;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_BaobiaoLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject getSkMoneyItem(Map<String, String> map, String visualstaff, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    sb.append("sum(" + SQLUtil.convertDecimal("detail.subtotal", 18, 2) + ") as subtotal, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.voidmoney", 18, 2) + ") as voidmoney, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.y2", 18, 2) + ") as y2, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.payyjj", 18, 2) + ") as yjj, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.paydjq", 18, 2) + ") as djq, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.payintegral", 18, 2) + ") as integral, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.payother2", 18, 2) + ") as zs, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.paymoney", 18, 2) + ")as paymoney ");
    map.put("moneysql", sb.toString());
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    map.put("visualstaff", visualstaff);
    
    JSONObject jobj = new JSONObject();
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".getSkMoneyItem", map);
    BigDecimal ys = BigDecimal.ZERO;
    BigDecimal qf = BigDecimal.ZERO;
    BigDecimal jf = BigDecimal.ZERO;
    BigDecimal skze = BigDecimal.ZERO;
    BigDecimal ssze = BigDecimal.ZERO;
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      BigDecimal subtotal = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("subtotal")) ? "0" : rs.getString("subtotal"));
      BigDecimal voidmoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("voidmoney")) ? "0" : rs.getString("voidmoney"));
      qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0" : rs.getString("y2"));
      jf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
      ys = subtotal.subtract(voidmoney);
      
      BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
      BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
      
      skze = jf.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
      ssze = jf.subtract(zs).subtract(djq).subtract(integral);
    }
    jobj.put("ys", KqdsBigDecimal.round(ys, 2));
    jobj.put("qf", KqdsBigDecimal.round(qf, 2));
    jobj.put("jf", KqdsBigDecimal.round(jf, 2));
    jobj.put("skze", KqdsBigDecimal.round(skze, 2));
    jobj.put("ssze", KqdsBigDecimal.round(ssze, 2));
    return jobj;
  }
  
  public BigDecimal getSkMoneyYjj(Map<String, String> map, String visualstaff, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    sb.append("sum(" + SQLUtil.convertDecimal("rec.cmoney", 18, 2) + ") as cmoney ");
    map.put("moneysql", sb.toString());
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    map.put("visualstaff", visualstaff);
    
    List<JSONObject> list = (List)this.dao.findForList("KQDS_Member_Record.getSkMoneyYjj", map);
    BigDecimal skze = BigDecimal.ZERO;
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("cmoney")) ? "0.00" : rs.getString("cmoney"));
    }
    return KqdsBigDecimal.round(skze, 2);
  }
}
