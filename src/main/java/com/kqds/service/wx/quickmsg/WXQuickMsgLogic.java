package com.kqds.service.wx.quickmsg;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXQuickMsgLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public int getCount(HttpServletRequest request, String table, Map<String, String> map)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.WX_QUICKMSG + ".getCount", map)).intValue();
    return count;
  }
  
  public List<JSONObject> noSelectWithPage(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.WX_QUICKMSG + ".getListsql", map);
    return list;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.WX_QUICKMSG + ".getListsql", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
}
