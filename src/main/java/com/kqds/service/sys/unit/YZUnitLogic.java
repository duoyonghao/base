package com.kqds.service.sys.unit;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZUnitLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public JSONObject getUnitDetail() throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_ORGANIZATION) + ".getUnitDetail", null);
    if (list == null || list.size() == 0)
      throw new Exception("单位记录不存在"); 
    if (list.size() > 1)
      throw new Exception("数据异常，单位记录数大于1"); 
    return list.get(0);
  }
}
