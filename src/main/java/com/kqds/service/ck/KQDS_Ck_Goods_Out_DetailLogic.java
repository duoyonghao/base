package com.kqds.service.ck;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Ck_Goods_Out_DetailLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public int getOutNumByGoodsuuid(String goodsuuid) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL) + ".getOutNumByGoodsuuid", goodsuuid);
    int count = 0;
    for (JSONObject json : list) {
      int outnum = json.getInt("outnum");
      count += outnum;
    } 
    return count;
  }
  
  public List<JSONObject> inSearchList(String table, Map<String, String> map) throws Exception {
    map.put("selectKcmoney", SQLUtil.convertDecimal("goutd.ckmoney", 18, 3));
    map.put("selectGoodsprice", SQLUtil.convertDecimal("goutd.outprice", 18, 3));
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL) + ".inSearchList", map);
    for (JSONObject job : list) {
      String outtype = job.getString("outtype");
      if (ConstUtil.CK_OUT_1.equals(outtype)) {
        outtype = "领用出库";
      } else if (ConstUtil.CK_OUT_3.equals(outtype)) {
        outtype = "换货出库";
      } else if (ConstUtil.CK_OUT_5.equals(outtype)) {
        outtype = "退货出库";
      } else if (ConstUtil.CK_OUT_7.equals(outtype)) {
        outtype = "其他出库";
      } 
      job.put("outtype", outtype);
    } 
    return list;
  }
  
  public int findOutNumsByAll() throws Exception {
    Map<String, String> map = new HashMap<>();
    String date = YZUtility.getDateStr(new Date());
    map.put("starttime", String.valueOf(date) + ConstUtil.TIME_START);
    map.put("endtime", String.valueOf(date) + ConstUtil.TIME_END);
    int list = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL) + ".findOutNumsByAll", map)).intValue();
    return list;
  }
  
  public List<JSONObject> findPhOutNumByGoodsuuid(String goodsuuid) throws Exception {
    Map<String, String> map = new HashMap<>();
    String date = YZUtility.getDateStr(new Date());
    map.put("starttime", String.valueOf(date) + ConstUtil.TIME_START);
    map.put("endtime", String.valueOf(date) + ConstUtil.TIME_END);
    map.put("goodsuuid", goodsuuid);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL) + ".findPhOutNumByGoodsuuid", map);
    return list;
  }
}
