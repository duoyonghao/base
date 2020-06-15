package com.kqds.service.app;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_IndexLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject getSkMoneyItem(String visualstaff, String organization, String whereSql)
    throws Exception
  {
    JSONObject jobj = new JSONObject();
    Map<String, String> map = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb.append("sum(" + SQLUtil.convertDecimal("detail.payyjj", 18, 2) + ") as yjj, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.payother2", 18, 2) + ") as zs, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.paydjq", 18, 2) + ") as djq, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.payintegral", 18, 2) + ") as integral, ");
    sb.append("sum(" + SQLUtil.convertDecimal("detail.paymoney", 18, 2) + ")as skze ");
    map.put("moneysql", sb.toString());
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    map.put("visualstaff", visualstaff);
    if (whereSql.equals("year")) {
      map.put("sftime", SQLUtil.dateDiffYear("cost.sftime"));
    } else if (whereSql.equals("month")) {
      map.put("sftime", SQLUtil.dateDiffMonth("cost.sftime"));
    } else if (whereSql.equals("day")) {
      map.put("sftime", SQLUtil.dateDiffDay("cost.sftime"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".getSkMoneyItem2", map);
    BigDecimal skze = BigDecimal.ZERO;
    BigDecimal ssze = BigDecimal.ZERO;
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("skze")) ? "0" : rs.getString("skze"));
      BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
      BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
      
      skze = paymoney.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
      ssze = paymoney.subtract(zs).subtract(djq).subtract(integral);
    }
    jobj.put("skje", KqdsBigDecimal.round(skze, 2));
    jobj.put("ssje", KqdsBigDecimal.round(ssze, 2));
    return jobj;
  }
  
  public BigDecimal getSkMoneyYjj(String visualstaff, String organization, String whereSql)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb.append("sum(" + SQLUtil.convertDecimal("rec.cmoney", 18, 2) + ")as cmoney ");
    map.put("moneysql", sb.toString());
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    map.put("visualstaff", visualstaff);
    if (whereSql.equals("year")) {
      map.put("sftime", SQLUtil.dateDiffYear("rec.createtime"));
    } else if (whereSql.equals("month")) {
      map.put("sftime", SQLUtil.dateDiffMonth("rec.createtime"));
    } else if (whereSql.equals("day")) {
      map.put("sftime", SQLUtil.dateDiffDay("rec.createtime"));
    }
    List<JSONObject> list = (List)this.dao.findForList("KQDS_Member_Record.getSkMoneyYjj2", map);
    BigDecimal skze = BigDecimal.ZERO;
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("cmoney")) ? "0.00" : rs.getString("cmoney"));
    }
    return KqdsBigDecimal.round(skze, 2);
  }
  
  public int getCountWd(String table, int days, String visualstaff, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    Map<String, String> map = new HashMap();
    sb.append(" and " + SQLUtil.dateDiffDay("n.ordertime", days));
    sb.append(" and " + SQLUtil.length("n.ordertime") + " >= 16 ");
    map.put("ordertime", sb.toString());
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountWd", map)).intValue();
    return count;
  }
  
  public int getCountMz(String table, int days, String visualstaff, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    Map<String, String> map = new HashMap();
    sb.append(" and " + SQLUtil.dateDiffDay("n.starttime", days));
    map.put("starttime", sb.toString());
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_HOSPITAL_ORDER + ".getCountMz", map)).intValue();
    return count;
  }
  
  public int getCountReg(String table, String visualstaff, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    Map<String, String> map = new HashMap();
    sb.append(" and " + SQLUtil.dateDiffDay("r.createtime"));
    map.put("createtime", sb.toString());
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getCountReg2", map)).intValue();
    return count;
  }
  
  public int getCountV(String table, String visualstaff, String organization)
    throws Exception
  {
    int count = 0;
    StringBuffer sb = new StringBuffer();
    Map<String, String> map = new HashMap();
    sb.append(" and " + SQLUtil.dateDiffDay("visittime"));
    map.put("visittime", sb.toString());
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    
    count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_VISIT + ".getCountV", map)).intValue();
    return count;
  }
  
  public BigDecimal selectRealQfMoney(String visualstaff, String organization)
    throws Exception
  {
    StringBuffer subsql = new StringBuffer();
    Map<String, String> map = new HashMap();
    subsql.append(" sum(" + SQLUtil.convertDecimal("d.y2", 18, 2) + ")as y2 ");
    map.put("y2", subsql.toString());
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectRealQfMoney", map);
    BigDecimal qfmoney = BigDecimal.ZERO;
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      qfmoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
    }
    return KqdsBigDecimal.round(qfmoney, 2);
  }
}
