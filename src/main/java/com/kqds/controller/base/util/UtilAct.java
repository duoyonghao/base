package com.kqds.controller.base.util;

import com.kqds.entity.sys.YZArea;
import com.kqds.service.base.util.UtilLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"UtilAct"})
public class UtilAct {
  private static Logger logger = LoggerFactory.getLogger(UtilAct.class);
  
  @Autowired
  private UtilLogic utilLogic;
  
  @RequestMapping({"/importArea.act"})
  public String importArea(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String param = request.getParameter("param");
      JSONObject json = JSONObject.fromObject(param);
      Iterator<String> it = json.keys();
      while (it.hasNext()) {
        String key2 = it.next();
        String[] key2Array = key2.split(",");
        String parentid = key2Array[key2Array.length - 1];
        JSONObject json2 = json.getJSONObject(key2);
        Iterator<String> it2 = json2.keys();
        int sortno = 0;
        while (it2.hasNext()) {
          sortno++;
          String key3 = it2.next();
          String val3 = json2.getString(key3);
          YZArea area2 = new YZArea();
          area2.setSeqId(key3);
          area2.setAreaName(val3);
          area2.setSortno(Integer.valueOf(sortno));
          area2.setParentId(parentid);
          this.utilLogic.saveSingleUUID(TableNameUtil.SYS_AREA, area2);
        } 
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/intTableHeader.act"})
  public String intTableHeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
    JSONArray json = new JSONArray();
    PrintWriter pw = response.getWriter();
    pw.println(json.toString());
    pw.flush();
    return null;
  }
  
  @RequestMapping({"/changeUseFlag.act"})
  public String changeUseFlag(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String tableName = request.getParameter("tableName");
      String seqId = request.getParameter("seqId");
      String useFlag = request.getParameter("useFlag");
      if (YZUtility.isNullorEmpty(tableName))
        throw new Exception("表名为空或者null"); 
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      if (YZUtility.isNullorEmpty(useFlag))
        throw new Exception("useFlag为空或者null"); 
      int flag = this.utilLogic.updateUseFlag(tableName, useFlag, seqId);
      if (flag <= 0)
        throw new Exception("更新失败！"); 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    } 
    return null;
  }
}
