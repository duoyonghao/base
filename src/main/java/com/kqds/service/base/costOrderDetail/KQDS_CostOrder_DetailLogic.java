package com.kqds.service.base.costOrderDetail;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.SysParaUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_CostOrder_DetailLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @Autowired
  private YZPersonLogic personLogic;
  
  public int getCountByItemnos(String itemnos) throws Exception {
    itemnos = YZUtility.ConvertStringIds4Query(itemnos);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".getCountByItemnos", itemnos)).intValue();
    return count;
  }
  
  public int judgeIFExpire(String createtime, String qfbh) throws Exception {
    if (YZUtility.isNullorEmpty(qfbh))
      return 0; 
    JSONObject lastestQfObj = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".getQfItemnos", qfbh);
    if (lastestQfObj == null)
      return 1; 
    String sftime = lastestQfObj.getString("sftime");
    if (YZUtility.isNullorEmpty(sftime))
      return 0; 
    int flag = YZUtility.compare_date(createtime, sftime);
    if (flag == 0)
      return 1; 
    return 0;
  }
  
  public Map<String, String> getClassName(String itemNO, String itemName, String organization) throws Exception {
    Map<String, String> cls = new HashMap<>();
    cls.put("basetypeName", "");
    cls.put("nexttypeName", "");
    Map<String, String> filters = new HashMap<>();
    filters.put("treatitemno", itemNO);
    filters.put("treatitemname", itemName);
    filters.put("organization", organization);
    List<KqdsTreatitem> itemList = (List<KqdsTreatitem>)this.dao.loadList(TableNameUtil.KQDS_TREATITEM, filters);
    if (itemList == null || itemList.size() == 0)
      return cls; 
    KqdsTreatitem tItem = itemList.get(0);
    String basetype = tItem.getBasetype();
    String nexttype = tItem.getNexttype();
    String basetypeName = this.dictLogic.getDictNameBySeqId(basetype);
    String nexttypeName = this.dictLogic.getDictNameBySeqId(nexttype);
    cls.put("basetypeName", basetypeName);
    cls.put("nexttypeName", nexttypeName);
    return cls;
  }
  
  public JSONObject selectNoPage(BootStrapPage bp, String table, YZPerson person, Map<String, String> map, String visualstaff, String organization) throws Exception {
    int firstIndex = bp.getOffset();
    if (map.containsKey("queryinput")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
    } 
    map.put("visualstaff", visualstaff);
    map.put("organization", organization);
    if (map.get("sortName") != null) {
      if (((String)map.get("sortName")).equals("detailremark"))
        map.put("sortName", "d.remark"); 
      if (((String)map.get("sortName")).equals("remark"))
        map.put("sortName", "c.remark"); 
      if (((String)map.get("sortName")).equals("sfuser"))
        map.put("sortName", "per2.USER_NAME"); 
      if (((String)map.get("sortName")).equals("jdtime"))
        map.put("sortName", "u.CreateTime"); 
      if (((String)map.get("sortName")).equals("jddy"))
        map.put("sortName", "per7.USER_NAME"); 
      if (((String)map.get("sortName")).equals("jduser"))
        map.put("sortName", "per3.USER_NAME"); 
      if (((String)map.get("sortName")).equals("developer"))
        map.put("sortName", "per6.USER_NAME"); 
      if (((String)map.get("sortName")).equals("introducer"))
        map.put("sortName", "u2.username"); 
      if (((String)map.get("sortName")).equals("kdtime"))
        map.put("sortName", "c.createtime"); 
      if (((String)map.get("sortName")).equals("kduser"))
        map.put("sortName", "per1.USER_NAME"); 
      if (((String)map.get("sortName")).equals("nexttype"))
        map.put("sortName", "hzly.dict_name"); 
      if (((String)map.get("sortName")).equals("devchannel"))
        map.put("sortName", "kcit3.dict_name"); 
      if (((String)map.get("sortName")).equals("techperson"))
        map.put("sortName", "per9.USER_NAME"); 
      if (((String)map.get("sortName")).equals("nurse"))
        map.put("sortName", "per8.USER_NAME"); 
      if (((String)map.get("sortName")).equals("doctor"))
        map.put("sortName", "per5.USER_NAME"); 
      if (((String)map.get("sortName")).equals("regdept"))
        map.put("sortName", "dept1.DEPT_NAME"); 
      if (((String)map.get("sortName")).equals("askperson"))
        map.put("sortName", "per4.USER_NAME"); 
      if (((String)map.get("sortName")).equals("faskperson"))
        map.put("sortName", "per10.USER_NAME"); 
      if (((String)map.get("sortName")).equals("cjstatus"))
        map.put("sortName", "c.cjstatus"); 
      if (((String)map.get("sortName")).equals("regsort"))
        map.put("sortName", "kcit2.dict_name"); 
      if (((String)map.get("sortName")).equals("recesort"))
        map.put("sortName", "kcit1.dict_name"); 
      if (((String)map.get("sortName")).equals("payyjj"))
        map.put("sortName", "d.payyjj"); 
      if (((String)map.get("sortName")).equals("paybank"))
        map.put("sortName", "d.paybank"); 
      if (((String)map.get("sortName")).equals("payyb"))
        map.put("sortName", "d.payyb"); 
      if (((String)map.get("sortName")).equals("paywx"))
        map.put("sortName", "d.paywx"); 
      if (((String)map.get("sortName")).equals("payzfb"))
        map.put("sortName", "d.payzfb"); 
      if (((String)map.get("sortName")).equals("paymmd"))
        map.put("sortName", "d.paymmd"); 
      if (((String)map.get("sortName")).equals("paybdfq"))
        map.put("sortName", "d.paybdfq"); 
      if (((String)map.get("sortName")).equals("payother1"))
        map.put("sortName", "d.payother1"); 
      if (((String)map.get("sortName")).equals("istsxm"))
        map.put("sortName", "d.istsxm"); 
      if (((String)map.get("sortName")).equals("y2"))
        map.put("sortName", "d.y2"); 
      if (((String)map.get("sortName")).equals("payother2"))
        map.put("sortName", "d.payother2"); 
      if (((String)map.get("sortName")).equals("paydjq"))
        map.put("sortName", "d.paydjq"); 
      if (((String)map.get("sortName")).equals("payintegral"))
        map.put("sortName", "d.payintegral"); 
      if (((String)map.get("sortName")).equals("paymoney"))
        map.put("sortName", "d.paymoney"); 
      if (((String)map.get("sortName")).equals("payxj"))
        map.put("sortName", "d.payxj"); 
      if (((String)map.get("sortName")).equals("organization"))
        map.put("sortName", "dept.DEPT_NAME"); 
      if (((String)map.get("sortName")).equals("sftime"))
        map.put("sortName", "c.sftime"); 
      if (((String)map.get("sortName")).equals("kaifa"))
        map.put("sortName", "d.kaifa"); 
      if (((String)map.get("sortName")).equals("zltime"))
        map.put("sortName", "d.zltime"); 
      if (((String)map.get("sortName")).equals("usercode"))
        map.put("sortName", "d.usercode"); 
      if (((String)map.get("sortName")).equals("blcode"))
        map.put("sortName", "u.blcode"); 
      if (((String)map.get("sortName")).equals("username"))
        map.put("sortName", "c.username"); 
      if (((String)map.get("sortName")).equals("phonenumber1"))
        map.put("sortName", "u.PhoneNumber1"); 
      if (((String)map.get("sortName")).equals("classname"))
        map.put("sortName", "tcode.dict_name"); 
      if (((String)map.get("sortName")).equals("nextname"))
        map.put("sortName", "cit.dict_name"); 
      if (((String)map.get("sortName")).equals("itemname"))
        map.put("sortName", "d.itemname"); 
      if (((String)map.get("sortName")).equals("unit"))
        map.put("sortName", "d.unit"); 
      if (((String)map.get("sortName")).equals("num"))
        map.put("sortName", "d.num"); 
      if (((String)map.get("sortName")).equals("discount"))
        map.put("sortName", "d.discount"); 
      if (((String)map.get("sortName")).equals("subtotal"))
        map.put("sortName", "d.subtotal"); 
      if (((String)map.get("sortName")).equals("voidmoney"))
        map.put("sortName", "d.voidmoney"); 
    } 
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(table) + ".selectNoPage", map);
    for (JSONObject obj : list) {
      obj.put("classname", obj.getString("dict_name"));
      obj.put("nextname", obj.getString("dict_name_2"));
      obj.put("cjstatus", obj.getString("cjstatus").equals("0") ? "未成交" : "已成交");
      obj.put("type", obj.getString("type").equals("0") ? "否" : "是");
      obj.put("istsxm", obj.getString("istsxm").equals("0") ? "否" : "是");
      boolean flag = true;
      if (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("y2")), new BigDecimal(0)) <= 0 && !YZUtility.isNullorEmpty(obj.getString("qfbh")) && 
        KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("subtotal")), BigDecimal.ZERO) == 0 && 
        KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("voidmoney")), BigDecimal.ZERO) == 0)
        flag = false; 
      String ys = "0.00";
      String paymoney = YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney");
      String arrearmoney = YZUtility.isNullorEmpty(obj.getString("arrearmoney")) ? "0.00" : obj.getString("arrearmoney");
      if (flag || obj.getString("istk").equals("1"))
        ys = (new BigDecimal(paymoney)).add(new BigDecimal(arrearmoney)).toString(); 
      obj.put("ys", ys);
      String payother2 = YZUtility.isNullorEmpty(obj.getString("payother2")) ? "0.00" : obj.getString("payother2");
      String paydjq = YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq");
      String payintegral = YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral");
      obj.put("paymoney", (new BigDecimal(paymoney)).subtract(new BigDecimal(payother2)).subtract(new BigDecimal(paydjq)).subtract(new BigDecimal(payintegral)).toString());
    } 
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    if (firstIndex == 0 && 
      list.size() > 0) {
      Map<String, String> wheremap = new HashMap<>();
      wheremap.putAll(map);
      wheremap.put("d.subtotal", "subtotal");
      wheremap.put("d.voidmoney", "voidmoney");
      wheremap.put("d.arrearmoney", "arrearmoney");
      wheremap.put("d.paymoney", "paymoney");
      wheremap.put("d.payother2", "payother2");
      wheremap.put("d.paydjq", "paydjq");
      wheremap.put("d.payintegral", "payintegral");
      wheremap.put("d.y2", "y2");
      JSONObject obj = (JSONObject)this.dao.findForObject(String.valueOf(table) + ".getTotalNumFields", wheremap);
      BigDecimal subtotal = new BigDecimal(obj.getString("subtotal"));
      BigDecimal voidmoney = new BigDecimal(obj.getString("voidmoney"));
      jobj.put("subtotal", obj.getString("subtotal"));
      jobj.put("voidmoney", obj.getString("voidmoney"));
      jobj.put("ys", subtotal.subtract(voidmoney).toString());
      String payother2str = "0";
      if (!YZUtility.isNullorEmpty(obj.getString("payother2")))
        payother2str = obj.getString("payother2"); 
      BigDecimal paymoney = new BigDecimal(obj.getString("paymoney"));
      BigDecimal payother2 = new BigDecimal(payother2str);
      BigDecimal paydjq = new BigDecimal(obj.getString("paydjq"));
      BigDecimal payintegral = new BigDecimal(obj.getString("payintegral"));
      BigDecimal payyjj = new BigDecimal(obj.getString("payyjj"));
      jobj.put("paymoney", paymoney.subtract(payother2).subtract(paydjq).subtract(payintegral).subtract(payyjj).toString());
      jobj.put("payother2", obj.getString("payother2"));
      jobj.put("y2", obj.getString("y2"));
      jobj.put("paydjq", obj.getString("paydjq"));
      jobj.put("payintegral", obj.getString("payintegral"));
    } 
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectNoPageOrder(BootStrapPage bp, String table, YZPerson person, Map<String, String> map, String visualstaff, String visualstaffwd) throws Exception {
    if (map.containsKey("queryinput")) {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
    } 
    map.put("visualstaff", visualstaff);
    map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
    if (map.containsKey("usercodes"))
      map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes"))); 
    int firstIndex = bp.getOffset();
    if (map.get("sortName") != null) {
      int a = 0;
      if (((String)map.get("sortName")).equals("sftime")) {
        map.put("sortName", "c.sftime");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("kaifa")) {
        map.put("sortName", "d.kaifa");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("zltime")) {
        map.put("sortName", "d.zltime");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("usercode")) {
        map.put("sortName", "d.usercode");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("blcode")) {
        map.put("sortName", "u.blcode");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("username")) {
        map.put("sortName", "c.username");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("phonenumber1")) {
        map.put("sortName", "u.PhoneNumber1");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("classname")) {
        map.put("sortName", "tcode.dict_name");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("nextname")) {
        map.put("sortName", "cit.dict_name");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("itemname")) {
        map.put("sortName", "d.itemname");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("realmoney")) {
        map.put("sortName", "d.paymoney-d.payyjj");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("unit")) {
        map.put("sortName", "d.unit");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("num")) {
        map.put("sortName", "d.num");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("discount")) {
        map.put("sortName", "d.discount");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("subtotal")) {
        map.put("sortName", "d.subtotal");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("voidmoney")) {
        map.put("sortName", "d.voidmoney");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("ys")) {
        map.put("sortName", "d.arrearmoney+d.paymoney");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("y2")) {
        map.put("sortName", "d.y2");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("payother2")) {
        map.put("sortName", "d.payother2");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("paydjq")) {
        map.put("sortName", "d.paydjq");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("payintegral")) {
        map.put("sortName", "d.payintegral");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("paymoney")) {
        map.put("sortName", "d.paymoney");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("payxj")) {
        map.put("sortName", "d.payxj");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("payyjj")) {
        map.put("sortName", "d.payyjj");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("paybank")) {
        map.put("sortName", "d.paybank");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("payyb")) {
        map.put("sortName", "d.payyb");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("paywx")) {
        map.put("sortName", "d.paywx");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("payzfb")) {
        map.put("sortName", "d.payzfb");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("paymmd")) {
        map.put("sortName", "d.paymmd");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("paybdfq")) {
        map.put("sortName", "d.paybdfq");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("payother1")) {
        map.put("sortName", "d.payother1");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("istsxm")) {
        map.put("sortName", "d.istsxm");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("recesort")) {
        map.put("sortName", "kcit1.dict_name");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("regsort")) {
        map.put("sortName", "kcit2.dict_name");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("cjstatus")) {
        map.put("sortName", "c.cjstatus");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("faskperson")) {
        map.put("sortName", "per10.USER_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("askperson")) {
        map.put("sortName", "per4.USER_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("regdept")) {
        map.put("sortName", "dept1.DEPT_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("doctor")) {
        map.put("sortName", "per5.USER_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("nurse")) {
        map.put("sortName", "per8.USER_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("techperson")) {
        map.put("sortName", "per9.USER_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("devchannel")) {
        map.put("sortName", "kcit3.dict_name");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("nexttype")) {
        map.put("sortName", "hzly.dict_name");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("accepttype")) {
        map.put("sortName", "kcit5.dict_name");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("accepttool")) {
        map.put("sortName", "kcit6.dict_name");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("kduser")) {
        map.put("sortName", "per1.USER_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("kdtime")) {
        map.put("sortName", "c.createtime");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("introducer")) {
        map.put("sortName", "u2.username");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("jduser")) {
        map.put("sortName", "per3.USER_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("jddy")) {
        map.put("sortName", "per7.USER_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("jdtime")) {
        map.put("sortName", "u.CreateTime");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("sfuser")) {
        map.put("sortName", "per2.USER_NAME");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("remark")) {
        map.put("sortName", "c.remark");
        a = 1;
      } 
      if (((String)map.get("sortName")).equals("detailremark")) {
        map.put("sortName", "d.remark");
        a = 1;
      } 
      if (a == 0)
        map.put("sortName", ""); 
    } 
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectNoPageOrder", map);
    for (JSONObject obj : list) {
      obj.put("classname", obj.getString("dict_name"));
      obj.put("nextname", obj.getString("dict_name_2"));
      obj.put("cjstatus", obj.getString("cjstatus").equals("0") ? "未成交" : "已成交");
      obj.put("type", obj.getString("type").equals("0") ? "否" : "是");
      obj.put("istsxm", obj.getString("istsxm").equals("0") ? "否" : "是");
      boolean flag = true;
      if (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("y2")), new BigDecimal(0)) <= 0 && !YZUtility.isNullorEmpty(obj.getString("qfbh")) && 
        KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("subtotal")), BigDecimal.ZERO) == 0 && 
        KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("voidmoney")), BigDecimal.ZERO) == 0)
        flag = false; 
      BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney"));
      BigDecimal arrearmoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("arrearmoney")) ? "0.00" : obj.getString("arrearmoney"));
      BigDecimal ys = BigDecimal.ZERO;
      if (flag || obj.getString("istk").equals("1"))
        ys = paymoney.add(arrearmoney); 
      obj.put("ys", ys.toString());
      BigDecimal payother2 = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payother2")) ? "0.00" : obj.getString("payother2"));
      BigDecimal paydjq = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq"));
      BigDecimal payintegral = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral"));
      obj.put("paymoney", paymoney.subtract(payother2).subtract(paydjq).subtract(payintegral));
      String payyjj = YZUtility.isNullorEmpty(obj.getString("payyjj")) ? "0.00" : obj.getString("payyjj");
      obj.put("realmoney", (new BigDecimal(obj.getString("paymoney"))).subtract(new BigDecimal(payyjj)).setScale(2, 4).toString());
      obj.put("cmoney", "0.00");
      obj.put("cgivemoney", "0.00");
      obj.put("ctotal", "0.00");
    } 
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    if (firstIndex == 0)
      if (pageInfo.getTotal() > 0L) {
        Map<String, String> wheremap = new HashMap<>();
        wheremap.putAll(map);
        wheremap.put("d.paymoney", "paymoney");
        wheremap.put("d.payxj", "payxj");
        wheremap.put("d.paybank", "paybank");
        wheremap.put("d.payyb", "payyb");
        wheremap.put("d.paywx", "paywx");
        wheremap.put("d.payzfb", "payzfb");
        wheremap.put("d.paymmd", "paymmd");
        wheremap.put("d.paybdfq", "paybdfq");
        wheremap.put("d.payother1", "payother1");
        wheremap.put("d.payyjj", "payyjj");
        wheremap.put("d.payother2", "payother2");
        wheremap.put("d.paydjq", "paydjq");
        wheremap.put("d.payintegral", "payintegral");
        JSONObject obj = (JSONObject)this.dao.findForObject(String.valueOf(table) + ".getTotalNumFields2", wheremap);
        BigDecimal paymoney = new BigDecimal(obj.getString("paymoney"));
        BigDecimal payyjj = new BigDecimal(obj.getString("payyjj"));
        BigDecimal paydjq = new BigDecimal(obj.getString("paydjq"));
        BigDecimal payintegral = new BigDecimal(obj.getString("payintegral"));
        BigDecimal payother2 = new BigDecimal(obj.getString("payother2"));
        jobj.put("realmoney", paymoney.subtract(payyjj).subtract(payother2).subtract(paydjq).subtract(payintegral).toString());
        jobj.put("payxj", obj.getString("payxj").toString());
        jobj.put("paybank", obj.getString("paybank").toString());
        jobj.put("payyb", obj.getString("payyb").toString());
        jobj.put("paywx", obj.getString("paywx").toString());
        jobj.put("payzfb", obj.getString("payzfb").toString());
        jobj.put("paymmd", obj.getString("paymmd").toString());
        jobj.put("paybdfq", obj.getString("paybdfq").toString());
        jobj.put("payother1", obj.getString("payother1").toString());
      }  
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List selectRsktj(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization, HttpServletRequest request) throws Exception {
    List<Object> list = new ArrayList();
    JSONObject sfxm = null;
    JSONObject yjj = null;
    JSONObject tk = null;
    JSONObject yjjtk = null;
    if (map.containsKey("askperson")) {
      sfxm = selectRsktjSfxm(table, person, map, visualstaff, "askperson", organization);
      tk = selectRsktjTk(table, person, map, visualstaff, "askperson", organization);
      yjj = selectRsktjYjj(table, person, map, visualstaff, "askperson", organization);
      yjjtk = selectRsktjYjjTk(table, person, map, visualstaff, "askperson", organization);
    } else if (map.containsKey("wdperson")) {
      sfxm = selectRsktjSfxm(table, person, map, visualstaff, "wdperson", organization);
      tk = selectRsktjTk(table, person, map, visualstaff, "wdperson", organization);
      yjj = selectRsktjYjj(table, person, map, visualstaff, "wdperson", organization);
      yjjtk = selectRsktjYjjTk(table, person, map, visualstaff, "wdperson", organization);
    } else if (map.containsKey("yxperson")) {
      sfxm = selectRsktjSfxm(table, person, map, visualstaff, "yxperson", organization);
      tk = selectRsktjTk(table, person, map, visualstaff, "yxperson", organization);
      yjj = selectRsktjYjj(table, person, map, visualstaff, "yxperson", organization);
      yjjtk = selectRsktjYjjTk(table, person, map, visualstaff, "yxperson", organization);
    } else {
      sfxm = selectRsktjSfxm(table, person, map, visualstaff, "skr", organization);
      tk = selectRsktjTk(table, person, map, visualstaff, "skr", organization);
      yjj = selectRsktjYjj(table, person, map, visualstaff, "skr", organization);
      yjjtk = selectRsktjYjjTk(table, person, map, visualstaff, "skr", organization);
    } 
    list = getRsktjList(sfxm, yjj, tk, yjjtk, request);
    return list;
  }
  
  private List<Object> getRsktjList(JSONObject sfxm, JSONObject yjj, JSONObject tk, JSONObject yjjtk, HttpServletRequest request) throws Exception {
    List<Object> list = new ArrayList();
    for (int i = 1; i < 22; i++) {
      JSONObject obj = new JSONObject();
      if (i == 1) {
        obj.put("xh", "1");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payxj"));
        obj.put("sfje", sfxm.get("xj").toString());
        obj.put("tkje", tk.get("xj").toString());
        BigDecimal ssje = (new BigDecimal(sfxm.get("xj").toString())).add(new BigDecimal(tk.get("xj").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 2) {
        obj.put("xh", "2");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paybank"));
        obj.put("sfje", sfxm.get("bank").toString());
        obj.put("tkje", tk.get("bank").toString());
        BigDecimal ssje = (new BigDecimal(sfxm.get("bank").toString())).add(new BigDecimal(tk.get("bank").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 3) {
        obj.put("xh", "3");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payyb"));
        obj.put("sfje", sfxm.get("yb").toString());
        obj.put("tkje", tk.get("yb").toString());
        BigDecimal ssje = (new BigDecimal(sfxm.get("yb").toString())).add(new BigDecimal(tk.get("yb").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 4) {
        obj.put("xh", "4");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paywx"));
        obj.put("sfje", sfxm.get("wx").toString());
        obj.put("tkje", tk.get("wx").toString());
        BigDecimal ssje = (new BigDecimal(sfxm.get("wx").toString())).add(new BigDecimal(tk.get("wx").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 5) {
        obj.put("xh", "5");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payzfb"));
        obj.put("sfje", sfxm.get("zfb").toString());
        obj.put("tkje", tk.get("zfb").toString());
        BigDecimal ssje = (new BigDecimal(sfxm.get("zfb").toString())).add(new BigDecimal(tk.get("zfb").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 6) {
        obj.put("xh", "6");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paymmd"));
        obj.put("sfje", sfxm.get("mmd").toString());
        obj.put("tkje", tk.get("mmd").toString());
        BigDecimal ssje = (new BigDecimal(sfxm.get("mmd").toString())).add(new BigDecimal(tk.get("mmd").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 7) {
        obj.put("xh", "7");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paybdfq"));
        obj.put("sfje", sfxm.get("bdfq").toString());
        obj.put("tkje", tk.get("bdfq").toString());
        BigDecimal ssje = (new BigDecimal(sfxm.get("bdfq").toString())).add(new BigDecimal(tk.get("bdfq").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 8) {
        obj.put("xh", "8");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payother1"));
        obj.put("sfje", sfxm.get("other1").toString());
        obj.put("tkje", tk.get("other1").toString());
        BigDecimal ssje = (new BigDecimal(sfxm.get("other1").toString())).add(new BigDecimal(tk.get("other1").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 9) {
        obj.put("xh", "9");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paydjq"));
        obj.put("sfje", "0.00");
        obj.put("tkje", "0.00");
        obj.put("ssje", "0.00");
        BigDecimal jfxj = (new BigDecimal(sfxm.get("djq").toString())).add(new BigDecimal(tk.get("djq").toString()));
        obj.put("zsje", jfxj.toString());
        obj.put("jfxj", jfxj.toString());
      } else if (i == 10) {
        obj.put("xh", "10");
        obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payintegral"));
        obj.put("sfje", "0.00");
        obj.put("tkje", "0.00");
        obj.put("ssje", "0.00");
        BigDecimal jfxj = (new BigDecimal(sfxm.get("integral").toString())).add(new BigDecimal(tk.get("integral").toString()));
        obj.put("zsje", jfxj.toString());
        obj.put("jfxj", jfxj.toString());
      } else if (i == 11) {
        obj.put("xh", "11");
        obj.put("xm", String.valueOf(ConstUtil.FKFS_YJJ2) + "充值");
        obj.put("sfje", yjj.get("czze").toString());
        obj.put("tkje", yjjtk.get("czze").toString());
        BigDecimal ssje = (new BigDecimal(yjj.get("czze").toString())).add(new BigDecimal(yjjtk.get("czze").toString()));
        obj.put("ssje", ssje.toString());
        BigDecimal zsje = (new BigDecimal(yjj.get("zs").toString())).add(new BigDecimal(yjjtk.get("zs").toString()));
        obj.put("zsje", zsje.toString());
        BigDecimal jfxj = KqdsBigDecimal.add(ssje, zsje);
        obj.put("jfxj", jfxj.toString());
      } else if (i == 12) {
        obj.put("xh", "");
        obj.put("xm", "└-├" + SysParaUtil.getFkfsNameByMemberField(request, "xjmoney"));
        obj.put("sfje", yjj.get("xjcz").toString());
        obj.put("tkje", yjjtk.get("xjcz").toString());
        BigDecimal ssje = (new BigDecimal(yjj.get("xjcz").toString())).add(new BigDecimal(yjjtk.get("xjcz").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 13) {
        obj.put("xh", "");
        obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "yhkmoney"));
        obj.put("sfje", yjj.get("bankcz").toString());
        obj.put("tkje", yjjtk.get("bankcz").toString());
        BigDecimal ssje = (new BigDecimal(yjj.get("bankcz").toString())).add(new BigDecimal(yjjtk.get("bankcz").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 14) {
        obj.put("xh", "");
        obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "wxmoney"));
        obj.put("sfje", yjj.get("wxcz").toString());
        obj.put("tkje", yjjtk.get("wxcz").toString());
        BigDecimal ssje = (new BigDecimal(yjj.get("wxcz").toString())).add(new BigDecimal(yjjtk.get("wxcz").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 15) {
        obj.put("xh", "");
        obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "zfbmoney"));
        obj.put("sfje", yjj.get("zfbcz").toString());
        obj.put("tkje", yjjtk.get("zfbcz").toString());
        BigDecimal ssje = (new BigDecimal(yjj.get("zfbcz").toString())).add(new BigDecimal(yjjtk.get("zfbcz").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 16) {
        obj.put("xh", "");
        obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "mmdmoney"));
        obj.put("sfje", yjj.get("mmdcz").toString());
        obj.put("tkje", yjjtk.get("mmdcz").toString());
        BigDecimal ssje = (new BigDecimal(yjj.get("mmdcz").toString())).add(new BigDecimal(yjjtk.get("mmdcz").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 17) {
        obj.put("xh", "");
        obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "bdfqmoney"));
        obj.put("sfje", yjj.get("bdfqcz").toString());
        obj.put("tkje", yjjtk.get("bdfqcz").toString());
        BigDecimal ssje = (new BigDecimal(yjj.get("bdfqcz").toString())).add(new BigDecimal(yjjtk.get("bdfqcz").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 18) {
        obj.put("xh", "");
        obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "qtmoney"));
        obj.put("sfje", yjj.get("qtcz").toString());
        obj.put("tkje", yjjtk.get("qtcz").toString());
        BigDecimal ssje = (new BigDecimal(yjj.get("qtcz").toString())).add(new BigDecimal(yjjtk.get("qtcz").toString()));
        obj.put("ssje", ssje.toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", ssje.toString());
      } else if (i == 19) {
        obj.put("xh", "12");
        obj.put("xm", "当日收款金额");
        BigDecimal sfss = new BigDecimal(sfxm.get("skze").toString().equals("") ? "0.00" : sfxm.get("skze").toString());
        BigDecimal czss = new BigDecimal(yjj.get("czze").toString().equals("") ? "0.00" : yjj.get("czze").toString());
        obj.put("sfje", KqdsBigDecimal.round(sfss.add(czss), 2).toString());
        BigDecimal tkczss = new BigDecimal(yjjtk.get("czze").toString().equals("") ? "0.00" : yjjtk.get("czze").toString());
        BigDecimal tkss = new BigDecimal(tk.get("skze").toString().equals("") ? "0.00" : tk.get("skze").toString());
        obj.put("tkje", KqdsBigDecimal.round(tkss.add(tkczss), 2).toString());
        BigDecimal ssje = sfss.add(czss).add(tkss).add(tkczss);
        obj.put("ssje", KqdsBigDecimal.round(ssje, 2).toString());
        BigDecimal yjjzs = new BigDecimal(yjj.get("zs").toString().equals("") ? "0.00" : yjj.get("zs").toString());
        BigDecimal yjjtkzs = new BigDecimal(yjjtk.get("zs").toString().equals("") ? "0.00" : yjjtk.get("zs").toString());
        BigDecimal tkdjq = new BigDecimal(tk.get("djq").toString().equals("") ? "0.00" : tk.get("djq").toString());
        BigDecimal tkintegral = new BigDecimal(tk.get("integral").toString().equals("") ? "0.00" : tk.get("integral").toString());
        BigDecimal sfdjq = new BigDecimal(sfxm.get("djq").toString().equals("") ? "0.00" : sfxm.get("djq").toString());
        BigDecimal sfintegral = new BigDecimal(sfxm.get("integral").toString().equals("") ? "0.00" : sfxm.get("integral").toString());
        BigDecimal zsje = yjjzs.add(yjjtkzs).add(tkdjq).add(sfdjq).add(tkintegral).add(sfintegral);
        obj.put("zsje", KqdsBigDecimal.round(zsje, 2).toString());
        obj.put("jfxj", KqdsBigDecimal.round(ssje.add(zsje), 2).toString());
      } else if (i == 20) {
        obj.put("xh", "13");
        obj.put("xm", "预交金使用金额");
        BigDecimal hkje = (new BigDecimal(sfxm.get("yjj").toString())).subtract(new BigDecimal(tk.get("yjj").toString()));
        obj.put("sfje", KqdsBigDecimal.round(hkje, 2).toString());
        obj.put("tkje", "0.00");
        obj.put("ssje", KqdsBigDecimal.round(hkje, 2).toString());
        BigDecimal sfxmzs = new BigDecimal(sfxm.get("zs").toString().equals("") ? "0.00" : sfxm.get("zs").toString());
        BigDecimal tkzs = new BigDecimal(tk.get("zs").toString().equals("") ? "0.00" : tk.get("zs").toString());
        BigDecimal zsje = sfxmzs.add(tkzs);
        obj.put("zsje", KqdsBigDecimal.round(zsje, 2).toString());
        obj.put("jfxj", KqdsBigDecimal.round(hkje.add(zsje), 2).toString());
      } else if (i == 21) {
        obj.put("xh", "14");
        obj.put("xm", "营业收入（实收+欠费）");
        obj.put("sfje", KqdsBigDecimal.round(new BigDecimal(sfxm.getString("realmoney")), 2).toString());
        obj.put("tkje", KqdsBigDecimal.round(new BigDecimal(tk.getString("realmoney")), 2).toString());
        BigDecimal yesr = (new BigDecimal(sfxm.get("realmoney").toString())).add(new BigDecimal(tk.get("realmoney").toString()));
        obj.put("ssje", KqdsBigDecimal.round(yesr, 2).toString());
        obj.put("zsje", "0.00");
        obj.put("jfxj", KqdsBigDecimal.round(yesr, 2).toString());
      } 
      list.add(obj);
    } 
    return list;
  }
  
  private JSONObject selectRsktjSfxm(String table, YZPerson person, Map<String, String> map, String visualstaff, String userpriv, String organization) throws Exception {
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    map.put("userpriv", userpriv);
    map.put("visualstaff", visualstaff);
    JSONObject obj = new JSONObject();
    if (userpriv.equals("wdperson")) {
      if (map.containsKey("wdperson")) {
        String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg("2");
        map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
      } 
    } else if (userpriv.equals("yxperson") && 
      map.containsKey("yxperson")) {
      String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg("3");
      map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
    } 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectRsktjSfxm", map);
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      obj.put("xj", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("xj")) ? "0.00" : rs.getString("xj")), 2));
      obj.put("bank", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bank")) ? "0.00" : rs.getString("bank")), 2));
      obj.put("yb", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yb")) ? "0.00" : rs.getString("yb")), 2));
      obj.put("wx", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("wx")) ? "0.00" : rs.getString("wx")), 2));
      obj.put("zfb", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zfb")) ? "0.00" : rs.getString("zfb")), 2));
      obj.put("mmd", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("mmd")) ? "0.00" : rs.getString("mmd")), 2));
      obj.put("bdfq", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bdfq")) ? "0.00" : rs.getString("bdfq")), 2));
      obj.put("djq", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq")), 2));
      obj.put("integral", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral")), 2));
      obj.put("other1", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("other1")) ? "0.00" : rs.getString("other1")), 2));
      BigDecimal payyjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0.00" : rs.getString("yjj"));
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
      BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("skze")) ? "0.00" : rs.getString("skze"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
      BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
      skze = skze.subtract(zs).subtract(payyjj).subtract(djq).subtract(integral);
      obj.put("zs", zs.toString());
      obj.put("skze", skze.toString());
      BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
      obj.put("realmoney", skze.add(qf).add(payyjj));
      obj.put("yjj", payyjj.toString());
    } else {
      obj.put("xj", "0.00");
      obj.put("bank", "0.00");
      obj.put("yb", "0.00");
      obj.put("other1", "0.00");
      obj.put("skze", "0.00");
      obj.put("zs", "0.00");
      obj.put("wx", "0.00");
      obj.put("zfb", "0.00");
      obj.put("mmd", "0.00");
      obj.put("bdfq", "0.00");
      obj.put("djq", "0.00");
      obj.put("integral", "0.00");
      obj.put("realmoney", "0.00");
      obj.put("yjj", "0.00");
    } 
    return obj;
  }
  
  private JSONObject selectRsktjTk(String table, YZPerson person, Map<String, String> map, String visualstaff, String userpriv, String organization) throws Exception {
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    map.put("userpriv", userpriv);
    map.put("visualstaff", visualstaff);
    JSONObject obj = new JSONObject();
    if (userpriv.equals("wdperson")) {
      if (map.containsKey("wdperson")) {
        String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg("2");
        map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
      } 
    } else if (userpriv.equals("yxperson") && 
      map.containsKey("yxperson")) {
      String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg("3");
      map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
    } 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectRsktjTk", map);
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      obj.put("xj", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("xj")) ? "0.00" : rs.getString("xj")), 2));
      obj.put("bank", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bank")) ? "0.00" : rs.getString("bank")), 2));
      obj.put("yb", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yb")) ? "0.00" : rs.getString("yb")), 2));
      obj.put("wx", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("wx")) ? "0.00" : rs.getString("wx")), 2));
      obj.put("zfb", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zfb")) ? "0.00" : rs.getString("zfb")), 2));
      obj.put("mmd", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("mmd")) ? "0.00" : rs.getString("mmd")), 2));
      obj.put("bdfq", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bdfq")) ? "0.00" : rs.getString("bdfq")), 2));
      obj.put("djq", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq")), 2));
      obj.put("integral", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral")), 2));
      obj.put("other1", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("other1")) ? "0.00" : rs.getString("other1")), 2));
      BigDecimal payyjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0.00" : rs.getString("yjj"));
      BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
      BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("skze")) ? "0.00" : rs.getString("skze"));
      BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
      BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
      skze = skze.subtract(zs).subtract(payyjj).subtract(djq).subtract(integral);
      obj.put("skze", skze.toString());
      obj.put("zs", zs.toString());
      BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
      obj.put("realmoney", skze.add(qf).add(payyjj).toString());
      obj.put("yjj", payyjj.toString());
    } else {
      obj.put("xj", "0.00");
      obj.put("zs", "0.00");
      obj.put("bank", "0.00");
      obj.put("yb", "0.00");
      obj.put("other1", "0.00");
      obj.put("skze", "0.00");
      obj.put("wx", "0.00");
      obj.put("zfb", "0.00");
      obj.put("mmd", "0.00");
      obj.put("bdfq", "0.00");
      obj.put("djq", "0.00");
      obj.put("integral", "0.00");
      obj.put("realmoney", "0.00");
      obj.put("yjj", "0.00");
    } 
    return obj;
  }
  
  private JSONObject selectRsktjYjj(String table, YZPerson person, Map<String, String> map, String visualstaff, String userpriv, String organization) throws Exception {
    JSONObject obj = new JSONObject();
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    map.put("userpriv", userpriv);
    map.put("visualstaff", visualstaff);
    if (userpriv.equals("wdperson")) {
      if (map.containsKey("wdperson")) {
        String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg("2");
        map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
      } 
    } else if (userpriv.equals("yxperson") && 
      map.containsKey("yxperson")) {
      String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg("3");
      map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
    } 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectRsktjYjj", map);
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      obj.put("bankcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bankcz")) ? "0.00" : rs.getString("bankcz")), 2));
      obj.put("xjcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("xjcz")) ? "0.00" : rs.getString("xjcz")), 2));
      obj.put("qtcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("qtcz")) ? "0.00" : rs.getString("qtcz")), 2));
      obj.put("zs", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs")), 2));
      obj.put("zfbcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zfbcz")) ? "0.00" : rs.getString("zfbcz")), 2));
      obj.put("mmdcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("mmdcz")) ? "0.00" : rs.getString("mmdcz")), 2));
      obj.put("bdfqcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bdfqcz")) ? "0.00" : rs.getString("bdfqcz")), 2));
      obj.put("wxcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("wxcz")) ? "0.00" : rs.getString("wxcz")), 2));
      obj.put("czze", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("czze")) ? "0.00" : rs.getString("czze")), 2));
    } else {
      obj.put("bankcz", "0.00");
      obj.put("xjcz", "0.00");
      obj.put("qtcz", "0.00");
      obj.put("zs", "0.00");
      obj.put("zfbcz", "0.00");
      obj.put("wxcz", "0.00");
      obj.put("mmdcz", "0.00");
      obj.put("bdfqcz", "0.00");
      obj.put("czze", "0.00");
    } 
    return obj;
  }
  
  private JSONObject selectRsktjYjjTk(String table, YZPerson person, Map<String, String> map, String visualstaff, String userpriv, String organization) throws Exception {
    JSONObject obj = new JSONObject();
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    map.put("userpriv", userpriv);
    map.put("visualstaff", visualstaff);
    if (userpriv.equals("wdperson")) {
      if (map.containsKey("wdperson")) {
        String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg("2");
        map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
      } 
    } else if (userpriv.equals("yxperson") && 
      map.containsKey("yxperson")) {
      String visualstaffwd = this.personLogic.getPerIdsByDeptTypeNoOrg("3");
      map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
    } 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectRsktjYjjTk", map);
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      obj.put("bankcz", YZUtility.isNullorEmpty(rs.getString("bankcz")) ? "0.00" : rs.getString("bankcz"));
      obj.put("xjcz", YZUtility.isNullorEmpty(rs.getString("xjcz")) ? "0.00" : rs.getString("xjcz"));
      obj.put("qtcz", YZUtility.isNullorEmpty(rs.getString("qtcz")) ? "0.00" : rs.getString("qtcz"));
      obj.put("zs", YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
      obj.put("zfbcz", YZUtility.isNullorEmpty(rs.getString("zfbcz")) ? "0.00" : rs.getString("zfbcz"));
      obj.put("mmdcz", YZUtility.isNullorEmpty(rs.getString("mmdcz")) ? "0.00" : rs.getString("mmdcz"));
      obj.put("bdfqcz", YZUtility.isNullorEmpty(rs.getString("bdfqcz")) ? "0.00" : rs.getString("bdfqcz"));
      obj.put("wxcz", YZUtility.isNullorEmpty(rs.getString("wxcz")) ? "0.00" : rs.getString("wxcz"));
      obj.put("czze", YZUtility.isNullorEmpty(rs.getString("czze")) ? "0.00" : rs.getString("czze"));
    } else {
      obj.put("bankcz", "0.00");
      obj.put("xjcz", "0.00");
      obj.put("qtcz", "0.00");
      obj.put("zs", "0.00");
      obj.put("zfbcz", "0.00");
      obj.put("wxcz", "0.00");
      obj.put("mmdcz", "0.00");
      obj.put("bdfqcz", "0.00");
      obj.put("czze", "0.00");
    } 
    return obj;
  }
  
  public List<JSONObject> selectWithPage(String table, Map<String, String> map, String visualstaff) throws Exception {
    map.put("visualstaff", visualstaff);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectWithPage", map);
    return list;
  }
  
  public List<JSONObject> selectWithPageLzjl(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectWithPageLzjl", map);
    return list;
  }
  
  public List<JSONObject> selectWithPageLzjlForLabel(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectWithPageLzjlForLabel", map);
    return list;
  }
  
  public List<JSONObject> selectWithPageLzjl2(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectWithPageLzjl2", map);
    return list;
  }
  
  public String selectRealQf(String table, String qfbh) throws Exception {
    BigDecimal qf = BigDecimal.ZERO;
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectrealqf", qfbh);
    for (JSONObject rs : list) {
      if (rs.getString("y2") != null && !rs.getString("y2").equals(""))
        qf = qf.add(new BigDecimal(Double.parseDouble(rs.getString("y2")))); 
    } 
    return qf.toString();
  }
  
  public void deleteDetail(String usercode, HttpServletRequest request) throws Exception {
    this.dao.delete(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".deleteDetail", usercode);
  }
  
  public List getCountTj(String table, Map<String, String> map, String organization) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".getCountTj", map);
    for (JSONObject obj : list) {
      String ys = YZUtility.isNullorEmpty(obj.getString("ys")) ? "0.00" : obj.getString("ys");
      String xflx = obj.getString("xflx");
      obj.put("ys", KqdsBigDecimal.round(new BigDecimal(ys), 2));
      obj.put("xflx", xflx);
    } 
    return list;
  }
  
  public List getCountQylrAll(String table, int year, int month, int days, String organization) throws Exception {
    List<BigDecimal[]> list = (List)new ArrayList<>();
    BigDecimal[] num = new BigDecimal[days + 1];
    Map<String, String> map = new HashMap<>();
    map.put("year", (new StringBuilder(String.valueOf(year))).toString());
    map.put("month", (new StringBuilder(String.valueOf(month))).toString());
    map.put("organization", organization);
    List<JSONObject> jsonList = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".getCountQylrAll", map);
    for (int i = 0; i <= days; i++)
      num[i] = BigDecimal.ZERO; 
    for (JSONObject rs : jsonList) {
      for (int j = 0; j <= days; j++) {
        if (rs.getInt("d") == j)
          num[j] = KqdsBigDecimal.round(new BigDecimal(rs.getString("ys")), 2); 
      } 
    } 
    list.add(num);
    return list;
  }
  
  public List getCountQylrNew(String table, int year, int month, int days, String organization) throws Exception {
    List<BigDecimal[]> list = (List)new ArrayList<>();
    BigDecimal[] num = new BigDecimal[days + 1];
    Map<String, String> map = new HashMap<>();
    map.put("year", (new StringBuilder(String.valueOf(year))).toString());
    map.put("month", (new StringBuilder(String.valueOf(month))).toString());
    map.put("organization", organization);
    List<JSONObject> jsonList = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".getCountQylrNew", map);
    for (int i = 0; i <= days; i++)
      num[i] = BigDecimal.ZERO; 
    for (JSONObject rs : jsonList) {
      for (int j = 0; j <= days; j++) {
        if (rs.getInt("d") == j)
          num[j] = KqdsBigDecimal.round(new BigDecimal(rs.getString("ss")), 2); 
      } 
    } 
    list.add(num);
    return list;
  }
  
  public BigDecimal getCountQylrAll(String table, int year, int month, String organization) throws Exception {
    BigDecimal nums = BigDecimal.ZERO;
    Map<String, String> map = new HashMap<>();
    map.put("year", (new StringBuilder(String.valueOf(year))).toString());
    map.put("month", (new StringBuilder(String.valueOf(month))).toString());
    map.put("organization", organization);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".getCountQylrAll2", map);
    if (list != null && list.size() > 0 && 
      YZUtility.isNotNullOrEmpty(((JSONObject)list.get(0)).getString("ys")))
      nums = new BigDecimal(((JSONObject)list.get(0)).getString("ys")); 
    return KqdsBigDecimal.round(nums, 2);
  }
  
  public BigDecimal getCountQylrNew(String table, int year, int month, String organization) throws Exception {
    BigDecimal nums = BigDecimal.ZERO;
    Map<String, String> map = new HashMap<>();
    map.put("year", (new StringBuilder(String.valueOf(year))).toString());
    map.put("month", (new StringBuilder(String.valueOf(month))).toString());
    map.put("organization", organization);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".getCountQylrNew2", map);
    if (list != null && list.size() > 0 && 
      YZUtility.isNotNullOrEmpty(((JSONObject)list.get(0)).getString("ss")))
      nums = new BigDecimal(((JSONObject)list.get(0)).getString("ss")); 
    return KqdsBigDecimal.round(nums, 2);
  }
  
  public JSONObject checkTf(String usercode, String itemno, String qfbh, String detailId) throws Exception {
    JSONObject obj = new JSONObject();
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(usercode))
      map.put("usercode", usercode); 
    if (!YZUtility.isNullorEmpty(itemno))
      map.put("itemno", itemno); 
    if (!YZUtility.isNullorEmpty(qfbh)) {
      map.put("qfbh", qfbh);
    } else {
      map.put("noqfbh", "");
      if (!YZUtility.isNullorEmpty(detailId))
        map.put("detailId", detailId); 
    } 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".checkTf", map);
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      String paymoney = YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney");
      obj.put("paymoney", paymoney);
    } 
    return obj;
  }
  
  public JSONObject checkTfRefund(String usercode, String itemno, String qfbh, String orderdetailno) throws Exception {
    JSONObject obj = new JSONObject();
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(usercode))
      map.put("usercode", usercode); 
    if (!YZUtility.isNullorEmpty(itemno))
      map.put("itemno", itemno); 
    if (!YZUtility.isNullorEmpty(qfbh)) {
      map.put("qfbh", qfbh);
    } else {
      map.put("noqfbh", "");
      if (!YZUtility.isNullorEmpty(orderdetailno))
        map.put("orderdetailno", orderdetailno); 
    } 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".checkTfRefund", map);
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      String tkmoney = YZUtility.isNullorEmpty(rs.getString("tkmoney")) ? "0.00" : rs.getString("tkmoney");
      obj.put("tkmoney", tkmoney);
    } 
    return obj;
  }
  
  public BigDecimal searchOrderZs(String table, String costno) throws Exception {
    BigDecimal zs = BigDecimal.ZERO;
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(costno))
      map.put("costno", costno); 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".searchOrderZs", map);
    if (list != null && list.size() > 0) {
      JSONObject rs = list.get(0);
      zs = YZUtility.isNullorEmpty(rs.getString("zs")) ? BigDecimal.ZERO : new BigDecimal(rs.getString("zs"));
    } 
    return zs;
  }
  
  public JSONObject printSfxm(String table, String costno, HttpServletRequest request) throws Exception {
    JSONObject obj = new JSONObject();
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(costno))
      map.put("costno", costno); 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".printSfxm", map);
    for (JSONObject rs : list) {
      if (!YZUtility.isNullorEmpty(rs.getString("xj")) && 
        rs.getDouble("xj") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "payxj"), rs.getString("xj")); 
      if (!YZUtility.isNullorEmpty(rs.getString("bank")) && 
        rs.getDouble("bank") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "paybank"), rs.getString("bank")); 
      if (!YZUtility.isNullorEmpty(rs.getString("yb")) && 
        rs.getDouble("yb") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "payyb"), rs.getString("yb")); 
      if (!YZUtility.isNullorEmpty(rs.getString("wx")) && 
        rs.getDouble("wx") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "paywx"), rs.getString("wx")); 
      if (!YZUtility.isNullorEmpty(rs.getString("zfb")) && 
        rs.getDouble("zfb") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "payzfb"), rs.getString("zfb")); 
      if (!YZUtility.isNullorEmpty(rs.getString("mmd")) && 
        rs.getDouble("mmd") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "paymmd"), rs.getString("mmd")); 
      if (!YZUtility.isNullorEmpty(rs.getString("bdfq")) && 
        rs.getDouble("bdfq") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "paybdfq"), rs.getString("bdfq")); 
      if (!YZUtility.isNullorEmpty(rs.getString("other1")) && 
        rs.getDouble("other1") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "payother1"), rs.getString("other1")); 
      if (!YZUtility.isNullorEmpty(rs.getString("djq")) && 
        rs.getDouble("djq") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "paydjq"), rs.getString("djq")); 
      if (!YZUtility.isNullorEmpty(rs.getString("integral")) && 
        rs.getDouble("integral") != 0.0D)
        obj.put(SysParaUtil.getFkfsNameByCostField(request, "payintegral"), rs.getString("integral")); 
      if (!YZUtility.isNullorEmpty(rs.getString("yjj")) && 
        rs.getDouble("yjj") != 0.0D)
        obj.put(ConstUtil.FKFS_YJJ, rs.getString("yjj")); 
      if (!YZUtility.isNullorEmpty(rs.getString("zs")) && 
        rs.getDouble("zs") != 0.0D)
        obj.put(ConstUtil.FKFS_ZS, rs.getString("zs")); 
    } 
    return obj;
  }
  
  public BigDecimal selectQfAll(String usercode, String sftime) throws Exception {
    BigDecimal zqf = BigDecimal.ZERO;
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(usercode))
      map.put("usercode", usercode); 
    if (!YZUtility.isNullorEmpty(sftime))
      map.put("sftime", sftime); 
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectQfAll", map);
    if (list != null && list.size() > 0) {
      String y2 = ((JSONObject)list.get(0)).getString("y2");
      if (!YZUtility.isNullorEmpty(y2))
        zqf = KqdsBigDecimal.round(new BigDecimal(y2), 2); 
    } 
    return zqf;
  }
  
  public Object selectOneByQfbh(String qfbh, String seqId) throws Exception {
    Map<String, String> filter = new HashMap<>();
    filter.put("qfbh", qfbh);
    filter.put("seqId", seqId);
    return this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectOneByQfbh", filter);
  }
  
  public Object selectOneByQfbh2(String qfbh) throws Exception {
    return this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectOneByQfbh2", qfbh);
  }
  
  public List<JSONObject> selectListByQfbh(String qfbh) throws Exception {
    return (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectListByQfbh", qfbh);
  }
  
  public List<JSONObject> findCostOrderDetailByUsercodes(String usercodes) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findCostOrderDetailByUsercodes", usercodes);
    return list;
  }
  
  public List<JSONObject> findCostOrderDetailTuidanByUsercodes(String usercodes) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL_TUIDAN) + ".findCostOrderDetailTuidanByUsercodes", usercodes);
    return list;
  }
  
  public List<KqdsCostorderDetail> selectCostorderDetail(String costno) throws Exception {
    List<KqdsCostorderDetail> list = (List<KqdsCostorderDetail>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".selectCostorderDetail", costno);
    return list;
  }
  
  public void updateCostorderDetailBySeqId(KqdsCostorderDetail dp) throws Exception {
    this.dao.update(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".updateCostorderDetailBySeqId", dp);
  }
  
  public List<JSONObject> findZperformance(Map<String, String> map) throws Exception {
    return (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_COSTORDER_DETAIL) + ".findZperformance", map);
  }
}
