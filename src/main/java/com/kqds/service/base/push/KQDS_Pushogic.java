package com.kqds.service.base.push;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsPush;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.YZUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_Pushogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map, JSONObject json) throws Exception {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("KQDS_PUSH.selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public int selectPageNum(String userId) throws Exception {
    int count = ((Integer)this.dao.findForObject("KQDS_PUSH.selectPageNum", userId)).intValue();
    return count;
  }
  
  public List<JSONObject> selectNoPageWithUserId(String table, String userId) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("KQDS_PUSH.selectNoPageWithUserId", userId);
    List<JSONObject> listNew = new ArrayList<>();
    for (JSONObject jsonObject : list) {
      String isnowpush = jsonObject.getString("isnowpush");
      if ("1".equals(isnowpush)) {
        String targetPushTime = jsonObject.getString("targetpushtime");
        if (!YZUtility.isNullorEmpty(targetPushTime)) {
          int flag = YZUtility.compare_date(targetPushTime, YZUtility.getCurDateTimeStr());
          if ("1".equals(Integer.valueOf(flag)))
            continue; 
        } 
      } 
      listNew.add(jsonObject);
    } 
    return listNew;
  }
  
  public void updateReadStatus(KqdsPush dp) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("pcpushreadedtime", dp.getPcpushedtime());
    if (!YZUtility.isNullorEmpty(dp.getSeqId())) {
      map.put("seqId", YZUtility.ConvertStringIds4Query(dp.getSeqId()));
    } else if (!YZUtility.isNullorEmpty(dp.getReciveuser())) {
      map.put("reciveuser", dp.getReciveuser());
    } 
    this.dao.update("KQDS_PUSH.updateReadStatus", map);
  }
  
  public void updatePushStatus(KqdsPush dp) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("pcpushedtime", dp.getPcpushreadedtime());
    if (!YZUtility.isNullorEmpty(dp.getSeqId()))
      map.put("seqId", YZUtility.ConvertStringIds4Query(dp.getSeqId())); 
    this.dao.update("KQDS_PUSH.updatePushStatus", map);
  }
  
  public void deleteVistPushInfo(String usercode, String reciveuser) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("reciveuser", reciveuser);
    map.put("usercode", usercode);
    this.dao.delete("KQDS_PUSH.deleteVistPushInfo", map);
  }
  
  public List<JSONObject> selectTop5ByTime(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("KQDS_PUSH.selectTop5ByTime", map);
    return list;
  }
  
  public String selectPushSeqid(Map<String, String> map) throws Exception {
    String list = (String)this.dao.findForObject("KQDS_PUSH.selectPushSeqid", map);
    return list;
  }
}
