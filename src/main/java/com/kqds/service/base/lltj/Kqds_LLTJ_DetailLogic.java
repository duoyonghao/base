package com.kqds.service.base.lltj;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Kqds_LLTJ_DetailLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List selectList(String table, Map<String, String> map, String organization)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_LLTJ_DETAIL + ".selectList", map);
    for (JSONObject json : list)
    {
      json.put("classname", json.getString("dict_name"));
      if (YZUtility.isNullorEmpty(json.getString("goods"))) {
        json.put("goodsname", "【材料不存在】");
      }
    }
    return list;
  }
}
