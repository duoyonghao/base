package com.kqds.service.base.scbb;

import com.hudh.dept.dao.SysDeptPrivDao;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_ScbbLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  @Autowired
  private SysDeptPrivDao sysDeptPrivDao;
  @Autowired
  private YZDictLogic dictLogic;
  
  public List<JSONObject> getCountTjDept(String table, Map<String, String> map, String organization, String costdept, String deptname)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(costdept)) {
      map.put("costdept", costdept);
    }
    map.put("organization", organization);
    List<JSONObject> jsonlist = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getCountTjDept", map);
    for (JSONObject rs : jsonlist) {
      if (!YZUtility.isNullorEmpty(rs.getString("ys")))
      {
        BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
        BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("ys")) ? "0" : rs.getString("ys"));
        BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
        BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
        BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
        BigDecimal realmoney = skze.add(qf).subtract(zs).subtract(djq).subtract(integral);
        rs.put("ys", realmoney);
        rs.put("xflx", rs.getString("xflx"));
        rs.put("deptname", deptname);
      }
    }
    return jsonlist;
  }
  
  public String getYysr(String table, Map<String, String> map, String organization)
    throws Exception
  {
    String yysr = "0";
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysr", map);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
      BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("skze")) ? "0" : rs.getString("skze"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
      BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
      BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
      skze = skze.add(qf).subtract(zs).subtract(djq).subtract(integral);
      
      yysr = KqdsBigDecimal.round(skze, 2).toString();
    }
    return yysr;
  }
  
  public List<JSONObject> getYysrList(Map<String, String> map, String organization)
    throws Exception
  {
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrList", map);
    return list;
  }
  
  public int getCountRegByGhfl(Map<String, String> map, String cjstatus, String recesort)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(cjstatus)) {
      map.put("cjstatus", cjstatus);
    }
    if (!YZUtility.isNullorEmpty(recesort)) {
      map.put("recesort", recesort);
    }
    if (map.containsKey("depttype")) {
      if (((String)map.get("depttype")).equals("1")) {
        map.put("isdoctor", recesort);
      }
    }
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_REG + ".getCountRegByGhfl", map)).intValue();
    return nums;
  }
  
  public List<JSONObject> getListRegByGhfl(Map<String, String> map, String cjstatus, String recesort)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(cjstatus)) {
      map.put("cjstatus", cjstatus);
    }
    if (!YZUtility.isNullorEmpty(recesort)) {
      map.put("recesort", recesort);
    }
    if (map.containsKey("depttype")) {
      if (((String)map.get("depttype")).equals("1")) {
        map.put("isdoctor", recesort);
      }
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getListRegByGhfl", map);
    return list;
  }
  
  public List<JSONObject> getCountHzly(Map<String, String> map, String visualstaff, String cjstatus)
    throws Exception
  {
    if (!YZUtility.isNullorEmpty(cjstatus)) {
      map.put("cjstatus", cjstatus);
    }
    map.put("visualstaff", visualstaff);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getCountHzly", map);
    return list;
  }
  
  public int getCountJd(Map<String, String> map)
    throws Exception
  {
    map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJd", map)).intValue();
    return nums;
  }
  
  public List<JSONObject> getCountJdList(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    if ((map.get("jdtime1") != null) && (map.get("jdtime2") == null))
    {
      String date = YZUtility.getDateStr(new Date());
      String jdtime2 = date + ConstUtil.TIME_END;
      map.put("jdtime2", jdtime2);
    }
    if (map.get("yewu") != null) {
      map.put("seqId", (String)map.get("yewu"));
    }
    if ((map.get("askitem") != null) && (map.get("yytime1") == null) && (map.get("yytime2") == null) && (map.get("jdtime1") != null) && (map.get("jdtime2") != null))
    {
      map.put("yytime1", (String)map.get("jdtime1"));
      map.put("yytime2", (String)map.get("jdtime2"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJdList", map);
    







    return list;
  }
  
  public int getCountJdItem(Map<String, String> map)
    throws Exception
  {
    map.put("visualstaffwd", YZUtility.ConvertStringIds4Query((String)map.get("visualstaffwd")));
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJdItem", map)).intValue();
    return nums;
  }
  
  public List<JSONObject> getCountJdItemList(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    if ((map.get("jdtime1") != null) && (map.get("jdtime2") == null))
    {
      String date = YZUtility.getDateStr(new Date());
      String jdtime2 = date + ConstUtil.TIME_END;
      map.put("jdtime2", jdtime2);
    }
    if (map.get("yewu") != null) {
      map.put("seqId", (String)map.get("yewu"));
    }
    if ((map.get("askitem") != null) && (map.get("yytime1") == null) && (map.get("yytime2") == null) && (map.get("jdtime1") != null) && (map.get("jdtime2") != null))
    {
      map.put("yytime1", (String)map.get("jdtime1"));
      map.put("yytime2", (String)map.get("jdtime2"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJdItemList", map);
    return list;
  }
  
  public List<JSONObject> getCountJdItemStatisticsList(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    if ((map.get("jdtime1") != null) && (map.get("jdtime2") == null))
    {
      String date = YZUtility.getDateStr(new Date());
      String jdtime2 = date + ConstUtil.TIME_END;
      map.put("jdtime2", jdtime2);
    }
    if (map.get("yewu") != null) {
      map.put("seqId", (String)map.get("yewu"));
    }
    if ((map.get("askitem") != null) && (map.get("yytime1") == null) && (map.get("yytime2") == null) && (map.get("jdtime1") != null) && (map.get("jdtime2") != null))
    {
      map.put("yytime1", (String)map.get("jdtime1"));
      map.put("yytime2", (String)map.get("jdtime2"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJdItemStatisticsList", map);
    return list;
  }
  
  public List<JSONObject> getCountYyItemList(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    if ((map.get("yytime1") != null) && (map.get("yytime2") == null))
    {
      String date = YZUtility.getDateStr(new Date());
      String yytime2 = date + ConstUtil.TIME_END;
      map.put("yytime2", yytime2);
    }
    if (map.get("yewu") != null) {
      map.put("seqId", (String)map.get("yewu"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountYyItemList", map);
    
    return list;
  }
  
  public List<JSONObject> getCountYyItemStatisticsList(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    if ((map.get("yytime1") != null) && (map.get("yytime2") == null))
    {
      String date = YZUtility.getDateStr(new Date());
      String yytime2 = date + ConstUtil.TIME_END;
      map.put("yytime2", yytime2);
    }
    if (map.get("yewu") != null) {
      map.put("seqId", (String)map.get("yewu"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountYyItemStatisticsList", map);
    
    return list;
  }
  
  public int getCountYy(Map<String, String> map)
    throws Exception
  {
    if (map.containsKey("visualstaffwd")) {
      map.put("visualstaffwd", YZUtility.ConvertStringIds4Query((String)map.get("visualstaffwd")));
    }
    map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountYy", map)).intValue();
    return nums;
  }
  
  public List<JSONObject> getCountYyList(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    if ((map.get("yytime1") != null) && (map.get("yytime2") == null))
    {
      String date = YZUtility.getDateStr(new Date());
      String yytime2 = date + ConstUtil.TIME_END;
      map.put("yytime2", yytime2);
    }
    if (map.get("yewu") != null) {
      map.put("seqId", (String)map.get("yewu"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountYyList", map);
    
    return list;
  }
  
  public List<JSONObject> getCountDoorstatus(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    if ((map.get("dztime1") != null) && (map.get("dztime2") == null))
    {
      String date = YZUtility.getDateStr(new Date());
      String dztime2 = date + ConstUtil.TIME_END;
      map.put("dztime2", dztime2);
    }
    if (map.get("yewu") != null) {
      map.put("seqId", (String)map.get("yewu"));
    }
    if (((map.get("dztime1") == null) && (map.get("dztime2") == null) && (map.get("jdtime1") != null) && (map.get("jdtime2") != null)) || 
      ((map.get("dztime1") == null) && (map.get("dztime2") == null) && (map.get("jdtime1") != null) && (map.get("jdtime2") == null)) || (
      (map.get("dztime1") == null) && (map.get("dztime2") == null) && (map.get("jdtime1") == null) && (map.get("jdtime2") != null)))
    {
      map.put("dztime1", (String)map.get("jdtime1"));
      map.put("dztime2", (String)map.get("jdtime2"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountDoorstatus", map);
    return list;
  }
  
  public List<JSONObject> getCountDoorstatusItemList(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    if ((map.get("dztime1") != null) && (map.get("dztime2") == null))
    {
      String date = YZUtility.getDateStr(new Date());
      String dztime2 = date + ConstUtil.TIME_END;
      map.put("dztime2", dztime2);
    }
    if (map.get("yewu") != null) {
      map.put("seqId", (String)map.get("yewu"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountDoorstatusItemList", map);
    return list;
  }
  
  public List<JSONObject> getCountDoorstatusItemStatisticsList(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    if ((map.get("dztime1") != null) && (map.get("dztime2") == null))
    {
      String date = YZUtility.getDateStr(new Date());
      String dztime2 = date + ConstUtil.TIME_END;
      map.put("dztime2", dztime2);
    }
    if (map.get("yewu") != null) {
      map.put("seqId", (String)map.get("yewu"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountDoorstatusItemStatisticsList", map);
    return list;
  }
  
  public int getCountYysm(Map<String, String> map)
    throws Exception
  {
    if (map.containsKey("visualstaffwd")) {
      map.put("visualstaffwd", YZUtility.ConvertStringIds4Query((String)map.get("visualstaffwd")));
    }
    map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountYysm2", map)).intValue();
    return nums;
  }
  
  public String getYysr(Map<String, String> map)
    throws Exception
  {
    map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    String yysr = "0";
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysr2", map);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
      BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
      BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
      
      paymoney = paymoney.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
      yysr = paymoney.setScale(2, 4).toString();
    }
    return yysr;
  }
  
  public List<JSONObject> getYysrList(Map<String, String> map)
    throws Exception
  {
    map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrList2", map);
    return list;
  }
  
  public List<JSONObject> getYysrListNoOrg(Map<String, String> map)
    throws Exception
  {
    if (map.containsKey("usercodes")) {
      map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    }
    map.remove("organization");
    List<JSONObject> list1 = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrList3", map);
    List<JSONObject> list2 = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrList4", map);
    Iterator localIterator2;
    for (Iterator localIterator1 = list2.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      JSONObject jsonObject = (JSONObject)localIterator1.next();
      localIterator2 = list1.iterator(); continue;JSONObject jsonObject1 = (JSONObject)localIterator2.next();
      if (jsonObject.getString("seq_id").equals(jsonObject1.getString("seq_id"))) {
        jsonObject.put("paymoney", new BigDecimal(jsonObject.getString("paymoney")).add(new BigDecimal(jsonObject1.getString("paymoney"))));
      }
    }
    return list2;
  }
  
  public List<JSONObject> getYysrYjjList(Map<String, String> map)
    throws Exception
  {
    map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".getYysrYjjList", map);
    return list;
  }
  
  public List<JSONObject> getYysrYjjListNoOrg(Map<String, String> map)
    throws Exception
  {
    map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    map.remove("organization");
    List<JSONObject> list1 = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".getYysrYjjList1", map);
    List<JSONObject> list2 = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".getYysrYjjList2", map);
    Iterator localIterator2;
    for (Iterator localIterator1 = list2.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      JSONObject jsonObject = (JSONObject)localIterator1.next();
      localIterator2 = list1.iterator(); continue;JSONObject jsonObject1 = (JSONObject)localIterator2.next();
      if (jsonObject.getString("seq_id").equals(jsonObject1.getString("seq_id"))) {
        jsonObject.put("cmoney", new BigDecimal(jsonObject.getString("cmoney")).add(new BigDecimal(jsonObject1.getString("cmoney"))));
      }
    }
    return list2;
  }
  
  public String getYysrYjj(Map<String, String> map)
    throws Exception
  {
    String yysr = "0";
    map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".getYysrYjj", map);
    if ((list != null) && (list.size() > 0))
    {
      JSONObject rs = (JSONObject)list.get(0);
      BigDecimal paymoney = BigDecimal.ZERO;
      if (!YZUtility.isNullorEmpty(rs.getString("cmoney"))) {
        paymoney = new BigDecimal(rs.getString("cmoney"));
      }
      yysr = KqdsBigDecimal.round(paymoney, 2).toString();
    }
    return yysr;
  }
  
  public List<JSONObject> getYysrDetailList(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrDetailList", map);
    return list;
  }
  
  public List<JSONObject> getYysrDetailList(Map<String, String> map, String organization)
    throws Exception
  {
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrDetailList2", map);
    return list;
  }
  
  public List<JSONObject> getJgdetailList(Map<String, String> map, String organization)
    throws Exception
  {
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL + ".getJgdetailList", map);
    return list;
  }
  
  public List<JSONObject> getCkDetailList(Map<String, String> map, String organization)
    throws Exception
  {
    map.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL + ".getCkDetailList", map);
    return list;
  }
  
  public int getCountCj(Map<String, String> map)
    throws Exception
  {
    map.put("visualstaffwd", YZUtility.ConvertStringIds4Query((String)map.get("visualstaffwd")));
    map.put("usercodes", YZUtility.ConvertStringIds4Query((String)map.get("usercodes")));
    int nums = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountCj2", map)).intValue();
    return nums;
  }
  
  public List<JSONObject> getCountCjList(Map<String, String> map)
    throws Exception
  {
    if (YZUtility.isNotNullOrEmpty((String)map.get("organization"))) {
      map.put("organization2", (String)map.get("organization"));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountCjList", map);
    return list;
  }
  
  public JSONObject getJSONObject4getWdOrderPerItemtj(List<JSONObject> ldnumList, List<JSONObject> yynumsList, List<JSONObject> doorstatusList, List<JSONObject> yycjnumsList, JSONObject objper, YZDict dict)
  {
    int ldnums = 0;
    int yynums = 0;
    int yysmnums = 0;
    if ((objper == null) && (dict == null))
    {
      if (ldnumList != null) {
        for (JSONObject jsonObject : ldnumList) {
          ldnums += jsonObject.getInt("ldnums");
        }
      }
      if (yynumsList != null) {
        for (JSONObject jsonObject : yynumsList) {
          yynums += jsonObject.getInt("yynums");
        }
      }
      for (JSONObject jsonObject : doorstatusList) {
        yysmnums += jsonObject.getInt("yysmnums");
      }
    }
    else
    {
      if (ldnumList != null) {
        for (int j = 0; j < ldnumList.size(); j++)
        {
          JSONObject jobj = (JSONObject)ldnumList.get(j);
          if (jobj.getString("seq_id").equals(objper.getString("seqId"))) {
            if (dict == null) {
              ldnums += jobj.getInt("ldnums");
            } else if (jobj.getString("askitem").equals(dict.getSeqId())) {
              ldnums += jobj.getInt("ldnums");
            }
          }
        }
      }
      if (yynumsList != null) {
        for (int j = 0; j < yynumsList.size(); j++)
        {
          JSONObject jobj = (JSONObject)yynumsList.get(j);
          if (jobj.getString("seq_id").equals(objper.getString("seqId"))) {
            if (dict == null) {
              yynums += jobj.getInt("yynums");
            } else if (jobj.getString("askitem").equals(dict.getSeqId())) {
              yynums += jobj.getInt("yynums");
            }
          }
        }
      }
      for (int j = 0; j < doorstatusList.size(); j++)
      {
        JSONObject jobj = (JSONObject)doorstatusList.get(j);
        if (jobj.getString("seq_id").equals(objper.getString("seqId"))) {
          if (dict == null) {
            yysmnums += jobj.getInt("yysmnums");
          } else if (jobj.getString("askitem").equals(dict.getSeqId())) {
            yysmnums += jobj.getInt("yysmnums");
          }
        }
      }
      if ((ldnums == 0) && (yynums == 0) && (yysmnums == 0)) {
        return null;
      }
    }
    Float dzl = Float.valueOf(0.0F);
    if (yynums != 0) {
      dzl = Float.valueOf(yysmnums * 100.0F / yynums);
    }
    JSONObject obj = new JSONObject();
    obj.put("ldnums", Integer.valueOf(ldnums));
    obj.put("yynums", Integer.valueOf(yynums));
    obj.put("yysmnums", Integer.valueOf(yysmnums));
    obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
    if (dict != null)
    {
      obj.put("username", objper.getString("userName"));
      obj.put("zxxm", dict.getDictName());
    }
    return obj;
  }
  
  public JSONObject getQcje(String starttime, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb.append(" sum(" + SQLUtil.convertDecimal("cmoney", 18, 2) + ") as cmoney,");
    sb.append(" sum(" + SQLUtil.convertDecimal("cgivemoney", 18, 2) + ") as cgivemoney,");
    sb.append(" sum(" + SQLUtil.convertDecimal("ctotal", 18, 2) + ") as ctotal,");
    sb.append(" sum(" + SQLUtil.convertDecimal("zzmoney", 18, 2) + ") as zzmoney,");
    sb.append(" sum(" + SQLUtil.convertDecimal("zzgivemoney", 18, 2) + ") as zzgivemoney");
    map.put("moneysql", sb.toString());
    map.put("starttime", starttime);
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    JSONObject json = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".getQcje", map);
    BigDecimal cmoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" : json.getString("cmoney"));
    BigDecimal zzmoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney")) ? "0.00" : json.getString("zzmoney"));
    BigDecimal cgivemoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00" : json.getString("cgivemoney"));
    BigDecimal zzgivemoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney")) ? "0.00" : json.getString("zzgivemoney"));
    BigDecimal ctotal1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" : json.getString("ctotal"));
    JSONObject jobj = new JSONObject();
    jobj.put("money", cmoney1.add(zzmoney1));
    jobj.put("givemoney", cgivemoney1.add(zzgivemoney1));
    jobj.put("total", ctotal1.add(zzmoney1).add(zzgivemoney1));
    return jobj;
  }
  
  public JSONObject getCaozuoje(String type, String starttime, String endtime, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb.append(" sum(" + SQLUtil.convertDecimal("cmoney", 18, 2) + ") as cmoney,");
    sb.append(" sum(" + SQLUtil.convertDecimal("cgivemoney", 18, 2) + ") as cgivemoney,");
    sb.append(" sum(" + SQLUtil.convertDecimal("ctotal", 18, 2) + ") as ctotal");
    map.put("moneysql", sb.toString());
    if (!YZUtility.isNullorEmpty(starttime)) {
      map.put("starttime", starttime);
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      map.put("endtime", endtime);
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    map.put("type", type);
    
    JSONObject json = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".getCaozuoje", map);
    BigDecimal cmoney2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" : json.getString("cmoney"));
    BigDecimal cgivemoney2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00" : json.getString("cgivemoney"));
    BigDecimal ctotal2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" : json.getString("ctotal"));
    JSONObject jobj = new JSONObject();
    jobj.put("money", cmoney2);
    jobj.put("givemoney", cgivemoney2);
    jobj.put("total", ctotal2);
    return jobj;
  }
  
  public JSONObject getQmje(String endtime, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb.append(" sum(" + SQLUtil.convertDecimal("cmoney", 18, 2) + ") as cmoney,");
    sb.append(" sum(" + SQLUtil.convertDecimal("cgivemoney", 18, 2) + ") as cgivemoney,");
    sb.append(" sum(" + SQLUtil.convertDecimal("ctotal", 18, 2) + ") as ctotal,");
    sb.append(" sum(" + SQLUtil.convertDecimal("zzmoney", 18, 2) + ") as zzmoney,");
    sb.append(" sum(" + SQLUtil.convertDecimal("zzgivemoney", 18, 2) + ") as zzgivemoney");
    map.put("moneysql", sb.toString());
    map.put("starttime", endtime);
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    JSONObject json = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".getQmje", map);
    BigDecimal cmoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" : json.getString("cmoney"));
    BigDecimal zzmoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney")) ? "0.00" : json.getString("zzmoney"));
    BigDecimal cgivemoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00" : json.getString("cgivemoney"));
    BigDecimal zzgivemoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney")) ? "0.00" : json.getString("zzgivemoney"));
    BigDecimal ctotal7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" : json.getString("ctotal"));
    JSONObject jobj = new JSONObject();
    jobj.put("money", cmoney7.add(zzmoney7));
    jobj.put("givemoney", cgivemoney7.add(zzgivemoney7));
    jobj.put("total", ctotal7.add(zzmoney7).add(zzgivemoney7));
    return jobj;
  }
  
  public JSONObject getHjzlqk(String starttime, String endtime, int iszl, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    if (!YZUtility.isNullorEmpty(starttime)) {
      map.put("starttime", starttime);
    }
    if (!YZUtility.isNullorEmpty(endtime)) {
      map.put("endtime", endtime);
    }
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    if (iszl == 1) {
      map.put("iszl", "1");
    }
    JSONObject obj = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".getHjzlqk", map);
    BigDecimal subtotal = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("subtotal")) ? "0.00" : obj.getString("subtotal"));
    BigDecimal voidmoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("voidmoney")) ? "0.00" : obj.getString("voidmoney"));
    JSONObject jobj = new JSONObject();
    jobj.put("ys", subtotal.subtract(voidmoney).toString());
    String payother2str = "0";
    if (!YZUtility.isNullorEmpty(obj.getString("payother2"))) {
      payother2str = obj.getString("payother2");
    }
    BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney"));
    BigDecimal payother2 = new BigDecimal(payother2str);
    BigDecimal paydjq = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq"));
    BigDecimal payintegral = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral"));
    jobj.put("paymoney", paymoney.subtract(payother2).subtract(paydjq).subtract(payintegral).toString());
    jobj.put("y2", YZUtility.isNullorEmpty(obj.getString("y2")) ? "0.00" : obj.getString("y2"));
    return jobj;
  }
  
  public List<JSONObject> getUsercodes(Map map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".getUserList", map);
    return list;
  }
  
  public BigDecimal getPerAdvance(Map perMap)
    throws Exception
  {
    BigDecimal perAdvance = (BigDecimal)this.dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".getPerAdvance", perMap);
    if (perAdvance == null) {
      perAdvance = BigDecimal.ZERO;
    }
    return perAdvance;
  }
  
  public String getSeqIdIdentifying(Map map)
    throws Exception
  {
    String list = (String)this.dao.findForObject(TableNameUtil.SYS_PERSON + ".getIdentifying", map);
    return list;
  }
  
  public String getDeptIdIdentifying(Map map)
    throws Exception
  {
    String list = (String)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".getDeptIdIdentifying", map);
    return list;
  }
  
  public List<JSONObject> getDeptIdByIdentifying(Map map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptIdByIdentifying", map);
    return list;
  }
  
  public List<JSONObject> findDoctorSituationByTime(HttpServletRequest request, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> personlist = new ArrayList();
    String name = "";
    String doctorId = "";
    if ((!YZUtility.isNullorEmpty((String)map.get("buttonName"))) && (((String)map.get("deptCategory")).equals("all")) && (((String)map.get("personId")).equals("all")))
    {
      personlist = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("'" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personlist.get(i)).getString("seqid") + "',");
        }
      }
      map.put("doctor", str.toString());
    }
    else if ((!YZUtility.isNullorEmpty((String)map.get("buttonName"))) && (!((String)map.get("deptCategory")).equals("all")) && (((String)map.get("personId")).equals("all")))
    {
      map.put("deptId", "'" + (String)map.get("deptCategory") + "'");
      personlist = this.sysDeptPrivDao.findPersonByDeptId(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("'" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personlist.get(i)).getString("seqid") + "',");
        }
      }
      map.put("doctor", str.toString());
    }
    else if (!((String)map.get("personId")).equals("all"))
    {
      if (((String)map.get("personId")).equals("personage"))
      {
        YZPerson person = SessionUtil.getLoginPerson(request);
        name = person.getUserName();
        doctorId = person.getSeqId();
        map.put("doctor", "'" + person.getSeqId() + "'");
      }
      else
      {
        Map<String, String> map1 = new HashMap();
        map1.put("seqId", (String)map.get("personId"));
        YZPerson yzPerson = this.sysDeptPrivDao.findPersonBySeqId(map1);
        name = yzPerson.getUserName();
        doctorId = (String)map.get("personId");
        map.put("doctor", "'" + (String)map.get("personId") + "'");
      }
    }
    List<JSONObject> doctorSituationNumslist = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectDoctorSituationNums", map);
    
    List<JSONObject> doctorSituationCJNumslist = (List)this.dao.findForList(TableNameUtil.KQDS_REG + ".selectDoctorSituationCJNums", map);
    
    List<JSONObject> doctorSituationCJMoneylist = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".selectDoctorSituationMoney", map);
    
    List<JSONObject> doctorSituationPrepayMoneylist = (List)this.dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".selectDoctorSituationPrepayMoney", map);
    

    String czseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
    
    String fzseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
    
    String zxfseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
    
    String fcseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
    
    String qtseqId = this.dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
    
    List<YZDict> listDict = this.dictLogic.getListByParentCodeALL("GHFL", ChainUtil.getCurrentOrganization(request));
    
    List<JSONObject> list = new ArrayList();
    

    int zxnums = 0;
    
    int cjnums = 0;
    
    int wcjnums = 0;
    
    int czallnums = 0;
    
    int czcjnums = 0;
    
    int fzallnums = 0;
    
    int fzcjnums = 0;
    
    int zxfallnums = 0;
    
    int zxfcjnums = 0;
    
    int fcallnums = 0;
    
    int fccjnums = 0;
    
    int qtallnums = 0;
    
    int qtcjnums = 0;
    
    String skje = "0.00";
    
    String totalAdvance = "0.00";
    
    String zzlzb = "100.00%";
    
    String cgl = "0.00%";
    
    String cglzb = "100.00%";
    if (doctorSituationNumslist.size() > 0) {
      for (JSONObject jsonObject1 : doctorSituationNumslist)
      {
        zxnums += Integer.parseInt(jsonObject1.getString("nums"));
        if (czseqId.equals(jsonObject1.getString("recesort"))) {
          czallnums += Integer.parseInt(jsonObject1.getString("nums"));
        } else if (fzseqId.equals(jsonObject1.getString("recesort"))) {
          fzallnums += Integer.parseInt(jsonObject1.getString("nums"));
        } else if (zxfseqId.equals(jsonObject1.getString("recesort"))) {
          zxfallnums += Integer.parseInt(jsonObject1.getString("nums"));
        } else if (fcseqId.equals(jsonObject1.getString("recesort"))) {
          fcallnums += Integer.parseInt(jsonObject1.getString("nums"));
        } else if (qtseqId.equals(jsonObject1.getString("recesort"))) {
          qtallnums += Integer.parseInt(jsonObject1.getString("nums"));
        }
      }
    }
    if (doctorSituationCJNumslist.size() > 0) {
      for (JSONObject jsonObject2 : doctorSituationCJNumslist)
      {
        cjnums += Integer.parseInt(jsonObject2.getString("nums"));
        if (czseqId.equals(jsonObject2.getString("recesort"))) {
          czcjnums += Integer.parseInt(jsonObject2.getString("nums"));
        } else if (fzseqId.equals(jsonObject2.getString("recesort"))) {
          fzcjnums += Integer.parseInt(jsonObject2.getString("nums"));
        } else if (zxfseqId.equals(jsonObject2.getString("recesort"))) {
          zxfcjnums += Integer.parseInt(jsonObject2.getString("nums"));
        } else if (fcseqId.equals(jsonObject2.getString("recesort"))) {
          fccjnums += Integer.parseInt(jsonObject2.getString("nums"));
        } else if (qtseqId.equals(jsonObject2.getString("recesort"))) {
          qtcjnums += Integer.parseInt(jsonObject2.getString("nums"));
        }
      }
    }
    if (doctorSituationCJMoneylist.size() > 0) {
      for (JSONObject jsonObject3 : doctorSituationCJMoneylist) {
        skje = new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje)).toString();
      }
    }
    if (doctorSituationPrepayMoneylist.size() > 0) {
      for (JSONObject jsonObject4 : doctorSituationPrepayMoneylist) {
        totalAdvance = new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance)).toString();
      }
    }
    wcjnums = zxnums - cjnums;
    if (zxnums > 0) {
      cgl = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(cjnums / zxnums * 100.0F)) }) + "%";
    }
    List<JSONObject> list4;
    int a;
    String cglzb2;
    if (personlist.size() > 0)
    {
      for (JSONObject jsonObject : personlist)
      {
        int zxnums1 = 0;
        
        int cjnums1 = 0;
        
        int wcjnums1 = 0;
        
        int czallnums1 = 0;
        
        int czcjnums1 = 0;
        
        int fzallnums1 = 0;
        
        int fzcjnums1 = 0;
        
        int zxfallnums1 = 0;
        
        int zxfcjnums1 = 0;
        
        int fcallnums1 = 0;
        
        int fccjnums1 = 0;
        
        int qtallnums1 = 0;
        
        int qtcjnums1 = 0;
        
        String skje1 = "0.00";
        
        String totalAdvance1 = "0.00";
        
        String zzlzb1 = "0.00%";
        
        String cgl1 = "0.00%";
        
        String cglzb1 = "0.00%";
        List<JSONObject> list1 = new ArrayList();
        List<JSONObject> list2 = new ArrayList();
        List<JSONObject> list3 = new ArrayList();
        list4 = new ArrayList();
        for (JSONObject jsonObject1 : doctorSituationNumslist) {
          if (jsonObject.getString("seqid").equals(jsonObject1.getString("seqid")))
          {
            zxnums1 += Integer.parseInt(jsonObject1.getString("nums"));
            list1.add(jsonObject1);
            if (czseqId.equals(jsonObject1.getString("recesort"))) {
              czallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
            } else if (fzseqId.equals(jsonObject1.getString("recesort"))) {
              fzallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
            } else if (zxfseqId.equals(jsonObject1.getString("recesort"))) {
              zxfallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
            } else if (fcseqId.equals(jsonObject1.getString("recesort"))) {
              fcallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
            } else if (qtseqId.equals(jsonObject1.getString("recesort"))) {
              qtallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
            }
          }
        }
        for (JSONObject jsonObject2 : doctorSituationCJNumslist) {
          if (jsonObject.getString("seqid").equals(jsonObject2.getString("seqid")))
          {
            cjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
            list2.add(jsonObject2);
            if (czseqId.equals(jsonObject2.getString("recesort"))) {
              czcjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
            } else if (fzseqId.equals(jsonObject2.getString("recesort"))) {
              fzcjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
            } else if (zxfseqId.equals(jsonObject2.getString("recesort"))) {
              zxfcjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
            } else if (fcseqId.equals(jsonObject2.getString("recesort"))) {
              fccjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
            } else if (qtseqId.equals(jsonObject2.getString("recesort"))) {
              qtcjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
            }
          }
        }
        wcjnums1 = zxnums1 - cjnums1;
        for (JSONObject jsonObject3 : doctorSituationCJMoneylist) {
          if (jsonObject.getString("seqid").equals(jsonObject3.getString("seqid")))
          {
            skje1 = new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje1)).toString();
            list3.add(jsonObject3);
          }
        }
        for (JSONObject jsonObject4 : doctorSituationPrepayMoneylist) {
          if (jsonObject.getString("seqid").equals(jsonObject4.getString("seqid")))
          {
            totalAdvance1 = new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance1)).toString();
            list4.add(jsonObject4);
          }
        }
        if (zxnums1 > 0) {
          cgl1 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(cjnums1 / zxnums1 * 100.0F)) }) + "%";
        }
        if (zxnums > 0) {
          zzlzb1 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(zxnums1 / zxnums * 100.0F)) }) + "%";
        }
        if (cjnums > 0) {
          cglzb1 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(cjnums1 / cjnums * 100.0F)) }) + "%";
        }
        a = 0;
        for (YZDict yZDict : listDict)
        {
          String username = jsonObject.getString("username");
          
          String zxxm = "";
          
          int zxnums2 = 0;
          
          int cjnums2 = 0;
          
          int wcjnums2 = 0;
          
          int czallnums2 = 0;
          
          int czcjnums2 = 0;
          
          int fzallnums2 = 0;
          
          int fzcjnums2 = 0;
          
          int zxfallnums2 = 0;
          
          int zxfcjnums2 = 0;
          
          int fcallnums2 = 0;
          
          int fccjnums2 = 0;
          
          int qtallnums2 = 0;
          
          int qtcjnums2 = 0;
          
          String skje2 = "0.00";
          
          String totalAdvance2 = "0.00";
          
          String zzlzb2 = "0.00%";
          
          String cgl2 = "0.00%";
          
          cglzb2 = "0.00%";
          if (list1.size() > 0) {
            for (JSONObject jsonObject1 : list1) {
              if (yZDict.getSeqId().equals(jsonObject1.getString("regsort")))
              {
                a = 1;
                zxxm = jsonObject1.getString("regsortname");
                zxnums2 += Integer.parseInt(jsonObject1.getString("nums"));
                if (czseqId.equals(jsonObject1.getString("recesort"))) {
                  czallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
                } else if (fzseqId.equals(jsonObject1.getString("recesort"))) {
                  fzallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
                } else if (zxfseqId.equals(jsonObject1.getString("recesort"))) {
                  zxfallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
                } else if (fcseqId.equals(jsonObject1.getString("recesort"))) {
                  fcallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
                } else if (qtseqId.equals(jsonObject1.getString("recesort"))) {
                  qtallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
                }
              }
            }
          }
          if (list2.size() > 0) {
            for (JSONObject jsonObject2 : list2) {
              if ((jsonObject2.getString("regsortname").equals(zxxm)) && (!zxxm.equals("")))
              {
                cjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
                if (czseqId.equals(jsonObject2.getString("recesort"))) {
                  czcjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
                } else if (fzseqId.equals(jsonObject2.getString("recesort"))) {
                  fzcjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
                } else if (zxfseqId.equals(jsonObject2.getString("recesort"))) {
                  zxfcjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
                } else if (fcseqId.equals(jsonObject2.getString("recesort"))) {
                  fccjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
                } else if (qtseqId.equals(jsonObject2.getString("recesort"))) {
                  qtcjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
                }
              }
            }
          }
          if (list3.size() > 0) {
            for (JSONObject jsonObject3 : list3) {
              if (yZDict.getSeqId().equals(jsonObject3.getString("regsort")))
              {
                a = 1;
                zxxm = jsonObject3.getString("regsortname");
                skje2 = new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje2)).toString();
              }
            }
          }
          if (list4.size() > 0) {
            for (JSONObject jsonObject4 : list4) {
              if (yZDict.getSeqId().equals(jsonObject4.getString("regsort")))
              {
                a = 1;
                zxxm = jsonObject4.getString("regsortname");
                totalAdvance2 = new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance2)).toString();
              }
            }
          }
          if (zxnums2 > 0) {
            cgl2 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(cjnums2 / zxnums2 * 100.0F)) }) + "%";
          }
          if (zxnums > 0) {
            zzlzb2 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(zxnums2 / zxnums * 100.0F)) }) + "%";
          }
          if (cjnums > 0) {
            cglzb2 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(cjnums2 / cjnums * 100.0F)) }) + "%";
          }
          wcjnums2 = zxnums2 - cjnums2;
          if (!zxxm.equals(""))
          {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("zxxm", zxxm);
            json.put("zxnums", zxnums2);
            json.put("zzlzb", zzlzb2);
            json.put("cjnums", cjnums2);
            json.put("cgl", cgl2);
            json.put("cglzb", cglzb2);
            json.put("wcjnums", wcjnums2);
            json.put("czallnums", czallnums2);
            json.put("czcjnums", czcjnums2);
            json.put("fzallnums", fzallnums2);
            json.put("fzcjnums", fzcjnums2);
            json.put("zxfallnums", zxfallnums2);
            json.put("zxfcjnums", zxfcjnums2);
            json.put("fcallnums", fcallnums2);
            json.put("fccjnums", fccjnums2);
            json.put("qtallnums", qtallnums2);
            json.put("qtcjnums", qtcjnums2);
            json.put("skje", skje2);
            json.put("totalAdvance", totalAdvance2);
            list.add(json);
          }
        }
        if (a > 0)
        {
          JSONObject json = new JSONObject();
          json.put("zxnums", zxnums1);
          json.put("zzlzb", zzlzb1);
          json.put("cjnums", cjnums1);
          json.put("cgl", cgl1);
          json.put("cglzb", cglzb1);
          json.put("wcjnums", wcjnums1);
          json.put("czallnums", czallnums1);
          json.put("czcjnums", czcjnums1);
          json.put("fzallnums", fzallnums1);
          json.put("fzcjnums", fzcjnums1);
          json.put("zxfallnums", zxfallnums1);
          json.put("zxfcjnums", zxfcjnums1);
          json.put("fcallnums", fcallnums1);
          json.put("fccjnums", fccjnums1);
          json.put("qtallnums", qtallnums1);
          json.put("qtcjnums", qtcjnums1);
          json.put("skje", skje1);
          json.put("totalAdvance", totalAdvance1);
          list.add(json);
        }
      }
    }
    else
    {
      int zxnums1 = 0;
      
      int cjnums1 = 0;
      
      int wcjnums1 = 0;
      
      int czallnums1 = 0;
      
      int czcjnums1 = 0;
      
      int fzallnums1 = 0;
      
      int fzcjnums1 = 0;
      
      int zxfallnums1 = 0;
      
      int zxfcjnums1 = 0;
      
      int fcallnums1 = 0;
      
      int fccjnums1 = 0;
      
      int qtallnums1 = 0;
      
      int qtcjnums1 = 0;
      
      String skje1 = "0.00";
      
      String totalAdvance1 = "0.00";
      
      String zzlzb1 = "0.00%";
      
      String cgl1 = "0.00%";
      
      String cglzb1 = "0.00%";
      List<JSONObject> list1 = new ArrayList();
      List<JSONObject> list2 = new ArrayList();
      List<JSONObject> list3 = new ArrayList();
      List<JSONObject> list4 = new ArrayList();
      for (JSONObject jsonObject1 : doctorSituationNumslist) {
        if (doctorId.equals(jsonObject1.getString("seqid")))
        {
          zxnums1 += Integer.parseInt(jsonObject1.getString("nums"));
          list1.add(jsonObject1);
          if (czseqId.equals(jsonObject1.getString("recesort"))) {
            czallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
          } else if (fzseqId.equals(jsonObject1.getString("recesort"))) {
            fzallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
          } else if (zxfseqId.equals(jsonObject1.getString("recesort"))) {
            zxfallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
          } else if (fcseqId.equals(jsonObject1.getString("recesort"))) {
            fcallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
          } else if (qtseqId.equals(jsonObject1.getString("recesort"))) {
            qtallnums1 += Integer.parseInt(jsonObject1.getString("nums"));
          }
        }
      }
      for (JSONObject jsonObject2 : doctorSituationCJNumslist) {
        if (doctorId.equals(jsonObject2.getString("seqid")))
        {
          cjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
          list2.add(jsonObject2);
          if (czseqId.equals(jsonObject2.getString("recesort"))) {
            czcjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
          } else if (fzseqId.equals(jsonObject2.getString("recesort"))) {
            fzcjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
          } else if (zxfseqId.equals(jsonObject2.getString("recesort"))) {
            zxfcjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
          } else if (fcseqId.equals(jsonObject2.getString("recesort"))) {
            fccjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
          } else if (qtseqId.equals(jsonObject2.getString("recesort"))) {
            qtcjnums1 += Integer.parseInt(jsonObject2.getString("nums"));
          }
        }
      }
      wcjnums1 = zxnums1 - cjnums1;
      for (JSONObject jsonObject3 : doctorSituationCJMoneylist) {
        if (doctorId.equals(jsonObject3.getString("seqid")))
        {
          skje1 = new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje1)).toString();
          list3.add(jsonObject3);
        }
      }
      for (JSONObject jsonObject4 : doctorSituationPrepayMoneylist) {
        if (doctorId.equals(jsonObject4.getString("seqid")))
        {
          totalAdvance1 = new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance1)).toString();
          list4.add(jsonObject4);
        }
      }
      if (zxnums1 > 0) {
        cgl1 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(cjnums1 / zxnums1 * 100.0F)) }) + "%";
      }
      if (zxnums > 0) {
        zzlzb1 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(zxnums1 / zxnums * 100.0F)) }) + "%";
      }
      if (cjnums > 0) {
        cglzb1 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(cjnums1 / cjnums * 100.0F)) }) + "%";
      }
      int a = 0;
      for (YZDict yZDict : listDict)
      {
        String username = name;
        
        String zxxm = "";
        
        int zxnums2 = 0;
        
        int cjnums2 = 0;
        
        int wcjnums2 = 0;
        
        int czallnums2 = 0;
        
        int czcjnums2 = 0;
        
        int fzallnums2 = 0;
        
        int fzcjnums2 = 0;
        
        int zxfallnums2 = 0;
        
        int zxfcjnums2 = 0;
        
        int fcallnums2 = 0;
        
        int fccjnums2 = 0;
        
        int qtallnums2 = 0;
        
        int qtcjnums2 = 0;
        
        String skje2 = "0.00";
        
        String totalAdvance2 = "0.00";
        
        String zzlzb2 = "0.00%";
        
        String cgl2 = "0.00%";
        
        String cglzb2 = "0.00%";
        if (list1.size() > 0) {
          for (JSONObject jsonObject1 : list1) {
            if (yZDict.getSeqId().equals(jsonObject1.getString("regsort")))
            {
              a = 1;
              zxxm = jsonObject1.getString("regsortname");
              zxnums2 += Integer.parseInt(jsonObject1.getString("nums"));
              if (czseqId.equals(jsonObject1.getString("recesort"))) {
                czallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
              } else if (fzseqId.equals(jsonObject1.getString("recesort"))) {
                fzallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
              } else if (zxfseqId.equals(jsonObject1.getString("recesort"))) {
                zxfallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
              } else if (fcseqId.equals(jsonObject1.getString("recesort"))) {
                fcallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
              } else if (qtseqId.equals(jsonObject1.getString("recesort"))) {
                qtallnums2 += Integer.parseInt(jsonObject1.getString("nums"));
              }
            }
          }
        }
        if (list2.size() > 0) {
          for (JSONObject jsonObject2 : list2) {
            if ((jsonObject2.getString("regsortname").equals(zxxm)) && (!zxxm.equals("")))
            {
              cjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
              if (czseqId.equals(jsonObject2.getString("recesort"))) {
                czcjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
              } else if (fzseqId.equals(jsonObject2.getString("recesort"))) {
                fzcjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
              } else if (zxfseqId.equals(jsonObject2.getString("recesort"))) {
                zxfcjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
              } else if (fcseqId.equals(jsonObject2.getString("recesort"))) {
                fccjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
              } else if (qtseqId.equals(jsonObject2.getString("recesort"))) {
                qtcjnums2 += Integer.parseInt(jsonObject2.getString("nums"));
              }
            }
          }
        }
        if (list3.size() > 0) {
          for (JSONObject jsonObject3 : list3) {
            if (yZDict.getSeqId().equals(jsonObject3.getString("regsort")))
            {
              a = 1;
              zxxm = jsonObject3.getString("regsortname");
              skje2 = new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje2)).toString();
            }
          }
        }
        if (list4.size() > 0) {
          for (JSONObject jsonObject4 : list4) {
            if (yZDict.getSeqId().equals(jsonObject4.getString("regsort")))
            {
              a = 1;
              zxxm = jsonObject4.getString("regsortname");
              totalAdvance2 = new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance2)).toString();
            }
          }
        }
        if (zxnums2 > 0) {
          cgl2 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(cjnums2 / zxnums2 * 100.0F)) }) + "%";
        }
        if (zxnums > 0) {
          zzlzb2 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(zxnums2 / zxnums * 100.0F)) }) + "%";
        }
        if (cjnums > 0) {
          cglzb2 = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(cjnums2 / cjnums * 100.0F)) }) + "%";
        }
        wcjnums2 = zxnums2 - cjnums2;
        if (!zxxm.equals(""))
        {
          JSONObject json = new JSONObject();
          json.put("username", username);
          json.put("zxxm", zxxm);
          json.put("zxnums", zxnums2);
          json.put("zzlzb", zzlzb2);
          json.put("cjnums", cjnums2);
          json.put("cgl", cgl2);
          json.put("cglzb", cglzb2);
          json.put("wcjnums", wcjnums2);
          json.put("czallnums", czallnums2);
          json.put("czcjnums", czcjnums2);
          json.put("fzallnums", fzallnums2);
          json.put("fzcjnums", fzcjnums2);
          json.put("zxfallnums", zxfallnums2);
          json.put("zxfcjnums", zxfcjnums2);
          json.put("fcallnums", fcallnums2);
          json.put("fccjnums", fccjnums2);
          json.put("qtallnums", qtallnums2);
          json.put("qtcjnums", qtcjnums2);
          json.put("skje", skje2);
          json.put("totalAdvance", totalAdvance2);
          list.add(json);
        }
      }
      if (a > 0)
      {
        JSONObject json = new JSONObject();
        json.put("zxnums", zxnums1);
        json.put("zzlzb", zzlzb1);
        json.put("cjnums", cjnums1);
        json.put("cgl", cgl1);
        json.put("cglzb", cglzb1);
        json.put("wcjnums", wcjnums1);
        json.put("czallnums", czallnums1);
        json.put("czcjnums", czcjnums1);
        json.put("fzallnums", fzallnums1);
        json.put("fzcjnums", fzcjnums1);
        json.put("zxfallnums", zxfallnums1);
        json.put("zxfcjnums", zxfcjnums1);
        json.put("fcallnums", fcallnums1);
        json.put("fccjnums", fccjnums1);
        json.put("qtallnums", qtallnums1);
        json.put("qtcjnums", qtcjnums1);
        json.put("skje", skje1);
        json.put("totalAdvance", totalAdvance1);
        list.add(json);
      }
    }
    if (list.size() == 0)
    {
      JSONObject json = new JSONObject();
      json.put("username", "");
      json.put("zxxm", "");
      json.put("zxnums", zxnums);
      json.put("zzlzb", zzlzb);
      json.put("cjnums", cjnums);
      json.put("cgl", cgl);
      json.put("cglzb", cglzb);
      json.put("wcjnums", wcjnums);
      json.put("czallnums", czallnums);
      json.put("czcjnums", czcjnums);
      json.put("fzallnums", fzallnums);
      json.put("fzcjnums", fzcjnums);
      json.put("zxfallnums", zxfallnums);
      json.put("zxfcjnums", zxfcjnums);
      json.put("fcallnums", fcallnums);
      json.put("fccjnums", fccjnums);
      json.put("qtallnums", qtallnums);
      json.put("qtcjnums", qtcjnums);
      json.put("skje", skje);
      json.put("totalAdvance", totalAdvance);
      list.add(json);
    }
    else
    {
      JSONObject json = new JSONObject();
      json.put("zxnums", zxnums);
      json.put("zzlzb", zzlzb);
      json.put("cjnums", cjnums);
      json.put("cgl", cgl);
      json.put("cglzb", cglzb);
      json.put("wcjnums", wcjnums);
      json.put("czallnums", czallnums);
      json.put("czcjnums", czcjnums);
      json.put("fzallnums", fzallnums);
      json.put("fzcjnums", fzcjnums);
      json.put("zxfallnums", zxfallnums);
      json.put("zxfcjnums", zxfcjnums);
      json.put("fcallnums", fcallnums);
      json.put("fccjnums", fccjnums);
      json.put("qtallnums", qtallnums);
      json.put("qtcjnums", qtcjnums);
      json.put("skje", skje);
      json.put("totalAdvance", totalAdvance);
      list.add(json);
    }
    return list;
  }
  
  public List<JSONObject> findDoctorPerformanceByTime(HttpServletRequest request, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> personlist = new ArrayList();
    String name = "";
    String doctorId = "";
    if ((!YZUtility.isNullorEmpty((String)map.get("buttonName"))) && (((String)map.get("deptCategory")).equals("all")) && (((String)map.get("personId")).equals("all")))
    {
      personlist = this.sysDeptPrivDao.findPersonSeqIdByButtonName(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("'" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personlist.get(i)).getString("seqid") + "',");
        }
      }
      map.put("doctor", str.toString());
    }
    else if ((!YZUtility.isNullorEmpty((String)map.get("buttonName"))) && (!((String)map.get("deptCategory")).equals("all")) && (((String)map.get("personId")).equals("all")))
    {
      map.put("deptId", "'" + (String)map.get("deptCategory") + "'");
      personlist = this.sysDeptPrivDao.findPersonByDeptId(map);
      StringBuffer str = new StringBuffer();
      for (int i = 0; i < personlist.size(); i++) {
        if (i == personlist.size() - 1) {
          str.append("'" + ((JSONObject)personlist.get(i)).getString("seqid") + "'");
        } else {
          str.append("'" + ((JSONObject)personlist.get(i)).getString("seqid") + "',");
        }
      }
      map.put("doctor", str.toString());
    }
    else if (!((String)map.get("personId")).equals("all"))
    {
      if (((String)map.get("personId")).equals("personage"))
      {
        YZPerson person = SessionUtil.getLoginPerson(request);
        name = person.getUserName();
        doctorId = person.getSeqId();
        map.put("doctor", "'" + person.getSeqId() + "'");
      }
      else
      {
        Map<String, String> map1 = new HashMap();
        map1.put("seqId", (String)map.get("personId"));
        YZPerson yzPerson = this.sysDeptPrivDao.findPersonBySeqId(map1);
        name = yzPerson.getUserName();
        doctorId = (String)map.get("personId");
        map.put("doctor", "'" + (String)map.get("personId") + "'");
      }
    }
    List<JSONObject> doctorSituationCJMoneylist = (List)this.dao.findForList(TableNameUtil.KQDS_COSTORDER + ".selectDoctorPerformanceMoney", map);
    

    List<JSONObject> list = new ArrayList();
    
    String ssje = "0.00";
    for (JSONObject jsonObject : doctorSituationCJMoneylist) {
      ssje = new BigDecimal(jsonObject.getString("ssje")).add(new BigDecimal(ssje)).toString();
    }
    int a;
    if (personlist.size() > 0)
    {
      for (JSONObject jsonObject : personlist) {
        if (doctorSituationCJMoneylist.size() > 0)
        {
          String ssje1 = "0.00";
          a = 0;
          for (JSONObject jsonObject1 : doctorSituationCJMoneylist) {
            if (jsonObject.getString("seqid").equals(jsonObject1.getString("seqid")))
            {
              ssje1 = new BigDecimal(jsonObject1.getString("ssje")).add(new BigDecimal(ssje1)).toString();
              list.add(jsonObject1);
              a = 1;
            }
          }
          if (a == 1)
          {
            JSONObject json = new JSONObject();
            json.put("ssje", ssje1);
            list.add(json);
          }
        }
      }
    }
    else if (doctorSituationCJMoneylist.size() > 0)
    {
      String ssje1 = "0.00";
      int a = 0;
      for (JSONObject jsonObject1 : doctorSituationCJMoneylist) {
        if (doctorId.equals(jsonObject1.getString("seqid")))
        {
          ssje1 = new BigDecimal(jsonObject1.getString("ssje")).add(new BigDecimal(ssje1)).toString();
          list.add(jsonObject1);
          a = 1;
        }
      }
      if (a == 1)
      {
        JSONObject json = new JSONObject();
        json.put("ssje", ssje1);
        list.add(json);
      }
    }
    if (list.size() == 0)
    {
      JSONObject json = new JSONObject();
      json.put("username", "");
      json.put("basename", "");
      json.put("nextname", "");
      json.put("ssje", ssje);
      list.add(json);
    }
    else
    {
      JSONObject json = new JSONObject();
      json.put("ssje", ssje);
      list.add(json);
    }
    return list;
  }
}
