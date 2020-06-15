package com.kqds.service.wx.order;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXOrderLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectList(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.WX_ORDER) + ".selectList", map);
    return list;
  }
  
  public JSONObject selectPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.WX_ORDER) + ".selectPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectDetail(String table, String seqId) throws Exception {
    JSONObject jobj = (JSONObject)this.dao.findForObject(String.valueOf(TableNameUtil.WX_ORDER) + ".selectDetail", seqId);
    return jobj;
  }
  
  public int countToday(String openid, String orderdate, HttpServletRequest request) throws SQLException, Exception {
    Map<String, String> map = new HashMap<>();
    map.put("openid", openid);
    map.put("orderdate", orderdate.trim());
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.WX_ORDER) + ".countToday", map)).intValue();
    return count;
  }
}
