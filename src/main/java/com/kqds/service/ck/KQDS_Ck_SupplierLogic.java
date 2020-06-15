package com.kqds.service.ck;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkSupplier;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Ck_SupplierLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public int countBySupplierCode(KqdsCkSupplier dp)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_CK_SUPPLIER + ".countBySupplierCode", dp)).intValue();
    return count;
  }
  
  public List<JSONObject> selectList(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_CK_SUPPLIER + ".selectList", map);
    return list;
  }
}
