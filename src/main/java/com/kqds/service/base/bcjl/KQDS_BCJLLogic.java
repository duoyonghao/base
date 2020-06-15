package com.kqds.service.base.bcjl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bcjlLogic")
public class KQDS_BCJLLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_BCJL + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectWithPageLzjl(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_BCJL + ".selectWithPageLzjl", map);
    return list;
  }
  
  public List<JSONObject> getListByJlName(String jlNmae)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_BCJL + ".getListByJlName", jlNmae);
    return list;
  }
}
