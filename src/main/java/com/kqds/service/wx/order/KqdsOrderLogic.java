package com.kqds.service.wx.order;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KqdsOrderLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject selectPageMz(String table, BootStrapPage bp, Map<String, String> map, String nopage)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_HOSPITAL_ORDER + ".selectPageMz", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    for (JSONObject job : list)
    {
      String cjstatus = job.getString("cjstatus");
      if (ConstUtil.CJ_STATUS_1.equals(cjstatus)) {
        cjstatus = ConstUtil.CJ_STATUS_1_DESC;
      } else {
        cjstatus = ConstUtil.CJ_STATUS_0_DESC;
      }
      job.put("cjstatus", cjstatus);
      
      String zdoorstatus = job.getString("zdoorstatus");
      if (ConstUtil.DOOR_STATUS_1.equals(zdoorstatus)) {
        zdoorstatus = ConstUtil.DOOR_STATUS_1_DESC;
      } else {
        zdoorstatus = ConstUtil.DOOR_STATUS_0_DESC;
      }
      job.put("zdoorstatus", zdoorstatus);
      
      String orderstatus = job.getString("orderstatus");
      if (ConstUtil.DOOR_STATUS_1.equals(orderstatus)) {
        orderstatus = ConstUtil.DOOR_STATUS_1_DESC;
      } else {
        orderstatus = ConstUtil.DOOR_STATUS_0_DESC;
      }
      job.put("orderstatus", orderstatus);
      
      String isdelete = job.getString("isdelete");
      if (ConstUtil.YY_ORDER_STATUS_1.equals(isdelete)) {
        isdelete = ConstUtil.YY_ORDER_STATUS_1_DESC;
      } else {
        isdelete = ConstUtil.YY_ORDER_STATUS_0_DESC;
      }
      job.put("isdelete", isdelete);
    }
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectPageWd(String table, BootStrapPage bp, Map<String, String> map, String nopage)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".selectPageWd", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    for (JSONObject job : list)
    {
      String cjstatus = job.getString("cjstatus");
      if ("1".equals(cjstatus)) {
        cjstatus = "已成交";
      } else {
        cjstatus = "未成交";
      }
      job.put("cjstatus", cjstatus);
      
      String zdoorstatus = job.getString("zdoorstatus");
      if ("1".equals(zdoorstatus)) {
        zdoorstatus = "已上门";
      } else {
        zdoorstatus = "未上门";
      }
      job.put("zdoorstatus", zdoorstatus);
      
      String doorstatus = job.getString("doorstatus");
      if ("1".equals(doorstatus)) {
        doorstatus = "已上门";
      } else {
        doorstatus = "未上门";
      }
      job.put("doorstatus", doorstatus);
    }
    jobj.put("rows", list);
    return jobj;
  }
}
