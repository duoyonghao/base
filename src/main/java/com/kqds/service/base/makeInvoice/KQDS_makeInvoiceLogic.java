package com.kqds.service.base.makeInvoice;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_makeInvoiceLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public void deleteUserDoc(String usercode, String table) throws Exception {
    JSONObject json = new JSONObject();
    json.put("tablename", table);
    json.put("usercode", usercode);
    this.dao.update(String.valueOf(TableNameUtil.KQDS_USERDOCUMENT) + ".deleteuser", json);
  }
}
