package com.kqds.service.sys.blct;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZBlctLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("querymap", map);
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_BLCT) + ".selectWithPage", json);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectList(String table, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_BLCT) + ".selectList", map);
    return list;
  }
  
  public int updateFlagBySeqIds(String seqids, String useflag, HttpServletRequest request) throws Exception {
    JSONObject json = new JSONObject();
    json.put("idList", YZUtility.ConvertString2List(seqids));
    json.put("useflag", useflag);
    int count = ((Integer)this.dao.update(String.valueOf(TableNameUtil.KQDS_BLCT) + ".updateFlagBySeqIds", json)).intValue();
    SysLogUtil.log(SysLogUtil.UPDATE_STATUS, SysLogUtil.SYS_DICT, seqids, TableNameUtil.SYS_DICT, request);
    return count;
  }
}
