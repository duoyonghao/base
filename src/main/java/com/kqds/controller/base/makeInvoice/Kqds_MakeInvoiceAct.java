package com.kqds.controller.base.makeInvoice;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.service.base.makeInvoice.KQDS_makeInvoiceLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.connection.DataSourceSwitch;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"Kqds_MakeInvoiceAct"})
public class Kqds_MakeInvoiceAct {
  private static Logger logger = LoggerFactory.getLogger(Kqds_MakeInvoiceAct.class);
  
  @Autowired
  private KQDS_makeInvoiceLogic logic;
  
  @RequestMapping({"/makeInvoice.act"})
  public String makeInvoice(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercodes = request.getParameter("usercodes");
      if (usercodes.length() == 0)
        throw new Exception("请选择开票患者！"); 
      String[] usercodesArr = usercodes.split(",");
      byte b;
      int i;
      String[] arrayOfString1;
      for (i = (arrayOfString1 = usercodesArr).length, b = 0; b < i; ) {
        String usercode = arrayOfString1[b];
        Map<Object, Object> map = new HashMap<>();
        map.put("usercode", usercode);
        DataSourceSwitch.setDataSourceType("KQDSKP");
        List<KqdsUserdocument> listDoc = (List<KqdsUserdocument>)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
        if (listDoc != null && listDoc.size() > 0) {
          if (!((KqdsUserdocument)listDoc.get(0)).getCostlevel().equals("1"))
            throw new Exception("患者编号：" + ((KqdsUserdocument)listDoc.get(0)).getUsercode() + "已在开票系统中新增数据，不能同步！"); 
          delete(usercode, request);
        } 
        add(usercode, request);
        b++;
      } 
      DataSourceSwitch.reset();
      JSONObject jobj = new JSONObject();
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  public void delete(String usercode, HttpServletRequest request) throws Exception {
    String kaipiaoTable = YZSysProps.getProp("KAIPIAO_TABLE");
    String[] kaipiaoTableArr = kaipiaoTable.split(",");
    DataSourceSwitch.setDataSourceType("KQDSKP");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = kaipiaoTableArr).length, b = 0; b < i; ) {
      String table = arrayOfString1[b];
      this.logic.deleteUserDoc(usercode, table);
      b++;
    } 
    DataSourceSwitch.reset();
  }
  
  public void add(String usercode, HttpServletRequest request) throws Exception {
    String kaipiaoTable = YZSysProps.getProp("KAIPIAO_TABLE");
    String[] kaipiaoTableArr = kaipiaoTable.split(",");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = kaipiaoTableArr).length, b = 0; b < i; ) {
      String table = arrayOfString1[b];
      Map<Object, Object> map = new HashMap<>();
      map.put("usercode", usercode);
      if (table.equals(TableNameUtil.KQDS_USERDOCUMENT)) {
        DataSourceSwitch.reset();
        List<KqdsUserdocument> listUserKqds = (List<KqdsUserdocument>)this.logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
        if (listUserKqds == null)
          throw new Exception("患者档案信息不存在"); 
        ((KqdsUserdocument)listUserKqds.get(0)).setCostlevel("1");
        this.logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, listUserKqds.get(0));
        DataSourceSwitch.setDataSourceType("KQDSKP");
        this.logic.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, listUserKqds.get(0));
      } else {
        DataSourceSwitch.reset();
        List<Object> listKqds = (List<Object>)this.logic.loadList(table, map);
        if (listKqds != null && listKqds.size() > 0) {
          DataSourceSwitch.setDataSourceType("KQDSKP");
          for (Object reg : listKqds)
            this.logic.saveSingleUUID(table, reg); 
        } 
      } 
      b++;
    } 
    DataSourceSwitch.reset();
  }
}
