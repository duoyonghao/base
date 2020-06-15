package com.kqds.service.base.memberRecord;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Member_Record_ShLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public List selectListByType(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_MEMBER_RECORD_SH) + ".selectListByType", map);
    return list;
  }
}
