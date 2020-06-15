package com.kqds.service.base.outProcessingSheetDetail;

import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_OutProcessingSheet_DetailLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public int getCountByItemCodes(String wjgxmbhs)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL + ".getCountByItemCodes", YZUtility.ConvertStringIds4Query(wjgxmbhs))).intValue();
    return count;
  }
  
  public int getCount(String factoryCode)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL + ".getCount", factoryCode)).intValue();
    return count;
  }
  
  public List<JSONObject> selectByitem(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL + ".selectByitem", map);
    return list;
  }
  
  public List<JSONObject> selectListByQuery(String table, Map<String, String> map, String num)
    throws Exception
  {
    if (num.equals("0")) {
      map.put("num", "0");
    }
    if (map.containsKey("queryInput"))
    {
      map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", (String)map.get("queryInput")));
      map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", (String)map.get("queryInput")));
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL + ".selectListByQuery", map);
    for (JSONObject job : list)
    {
      String status = job.getString("status");
      String value = "";
      if (ConstUtil.JG_STATUS_0.equals(status)) {
        value = "未发件";
      } else if (ConstUtil.JG_STATUS_1.equals(status)) {
        value = "已发件";
      } else if (ConstUtil.JG_STATUS_2.equals(status)) {
        value = "已回件";
      } else if (ConstUtil.JG_STATUS_3.equals(status)) {
        value = "戴走";
      } else if (ConstUtil.JG_STATUS_4.equals(status)) {
        value = "返工";
      } else if (ConstUtil.JG_STATUS_5.equals(status)) {
        value = "作废";
      }
      job.put("statusname", value);
    }
    return list;
  }
}
