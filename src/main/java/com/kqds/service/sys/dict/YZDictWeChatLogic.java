package com.kqds.service.sys.dict;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDict;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.sys.DictUtil;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZDictWeChatLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<YZDict> getLeve1SortList(String search, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("parentCode", DictUtil.WECHAT_KEYWORD);
    
    json.put("organization", organization);
    List<YZDict> list = (List)this.dao.findForList(TableNameUtil.SYS_DICT + ".getLeve1SortList", json);
    return list;
  }
}
