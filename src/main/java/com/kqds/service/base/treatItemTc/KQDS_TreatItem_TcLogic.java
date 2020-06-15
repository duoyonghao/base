package com.kqds.service.base.treatItemTc;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsTreatitemTc;
import com.kqds.entity.base.KqdsTreatitemTcType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_TreatItem_TcLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private KQDS_TreatItemLogic treatItemLogic;
  
  @Transactional(rollbackFor = {Exception.class})
  public void newAddTc(String tctype, String tcname, Map<String, String> map, HttpServletRequest request) throws Exception {
    KqdsTreatitemTcType dptype = new KqdsTreatitemTcType();
    KqdsTreatitemTcType dpname = new KqdsTreatitemTcType();
    String organization = map.get("organization");
    YZPerson person = SessionUtil.getLoginPerson(request);
    List<KqdsTreatitemTcType> list = (List<KqdsTreatitemTcType>)loadList(TableNameUtil.KQDS_TREATITEM_TC_TYPE, map);
    if (list != null && list.size() > 0) {
      dptype = list.get(0);
      dpname.setSeqId(YZUtility.getUUID());
      dpname.setName(tcname);
      dpname.setIsopen(Integer.valueOf(0));
      dpname.setParentid(dptype.getSeqId());
      dpname.setOrganization(organization);
      dpname.setCreatetime(YZUtility.getCurDateTimeStr());
      dpname.setCreateuser(person.getSeqId());
      saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dpname);
    } else {
      dptype.setSeqId(YZUtility.getUUID());
      dptype.setName(tctype);
      dptype.setIsopen(Integer.valueOf(0));
      dptype.setParentid("0");
      dptype.setOrganization(organization);
      dptype.setCreatetime(YZUtility.getCurDateTimeStr());
      dptype.setCreateuser(person.getSeqId());
      saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dptype);
      dpname.setSeqId(YZUtility.getUUID());
      dpname.setName(tcname);
      dpname.setIsopen(Integer.valueOf(0));
      dpname.setParentid(dptype.getSeqId());
      dpname.setOrganization(organization);
      dpname.setCreatetime(dptype.getCreatetime());
      dpname.setCreateuser(person.getSeqId());
      saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dpname);
    } 
    String listdata = request.getParameter("params");
    JSONArray jArray = JSONArray.fromObject(listdata);
    Collection collection = JSONArray.toCollection(jArray, KqdsTreatitemTc.class);
    Iterator<KqdsTreatitemTc> it = collection.iterator();
    KqdsTreatitemTc detail = new KqdsTreatitemTc();
    while (it.hasNext()) {
      detail = it.next();
      detail.setTcnameid(dpname.getSeqId());
      detail.setSeqId(YZUtility.getUUID());
      detail.setArrearmoney("0");
      detail.setVoidmoney("0");
      detail.setPaymoney(detail.getSubtotal());
      detail.setCreatetime(YZUtility.getCurDateTimeStr());
      detail.setCreateuser(person.getSeqId());
      detail.setOrganization(organization);
      KqdsTreatitem treatitem = this.treatItemLogic.getByTreatItemno(detail.getItemno());
      if (treatitem == null)
        throw new Exception("收费编号对应的收费项目不存在"); 
      if (1 == treatitem.getIsyjjitem().intValue())
        throw new Exception("预交金不能作为收费套餐项目"); 
      saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, detail);
    } 
  }
  
  public int getCountByItemnos(String itemnos) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_TREATITEM_TC) + ".getCountByItemnos", YZUtility.ConvertStringIds4Query(itemnos))).intValue();
    return count;
  }
  
  public int checkTc(String parentid, String tcname, String seqid) throws Exception {
    Map<String, String> map1 = new HashMap<>();
    map1.put("parentid", parentid);
    map1.put("name", tcname);
    map1.put("seqId", seqid);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_TREATITEM_TC) + ".checkTc", map1)).intValue();
    return count;
  }
  
  public int selectTcxmCount(String table, String tcnameid) throws Exception {
    int num = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_TREATITEM_TC) + ".selectTcxmCount", tcnameid)).intValue();
    return num;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM_TC) + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public JSONObject selectWithPageBytctypeAndname(String table, BootStrapPage bp, String tcnameid) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM_TC) + ".selectWithPageBytctypeAndname", tcnameid);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectNoPageBytctypeAndname(String table, BootStrapPage bp, String tcnameid) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM_TC) + ".selectNoPageBytctypeAndname", tcnameid);
    return list;
  }
  
  public List<KqdsTreatitemTcType> getTcNameListByTcType(String tctypeid, String createuser, String organization) throws Exception {
    Map<String, String> map1 = new HashMap<>();
    map1.put("createuser", createuser);
    map1.put("parentid", tctypeid);
    map1.put("organization", organization);
    List<KqdsTreatitemTcType> list = (List<KqdsTreatitemTcType>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM_TC) + ".getTcNameListByTcType", map1);
    return list;
  }
  
  public int countTcNameByTcType(String tctypeid, String createuser, String organization) throws Exception {
    Map<String, String> map1 = new HashMap<>();
    map1.put("createuser", createuser);
    map1.put("parentid", tctypeid);
    map1.put("organization", organization);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_TREATITEM_TC) + ".countTcNameByTcType", map1)).intValue();
    return count;
  }
  
  public List<KqdsTreatitemTcType> getTcTypeList(String organization) throws Exception {
    List<KqdsTreatitemTcType> list = (List<KqdsTreatitemTcType>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_TREATITEM_TC) + ".getTcTypeList", organization);
    return list;
  }
}
