package com.kqds.service.sys.dept;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deptLogic")
public class YZDeptLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public JSONObject getOneByNameAndCode(String deptName, String deptCode)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("deptName", deptName);
    json.put("deptCode", deptCode);
    
    return (JSONObject)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".getOneByNameAndCode", json);
  }
  
  public List<JSONObject> getDeptSeqIdByTypeAndOrg(String organization, String dept_types)
    throws Exception
  {
    List<String> typeList = YZUtility.ConvertString2List(dept_types);
    JSONObject json = new JSONObject();
    json.put("typeList", typeList);
    json.put("organization", organization);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptSeqIdByTypeAndOrg", json);
    return list;
  }
  
  public List<YZDept> getHosList(String organization)
    throws Exception
  {
    List<YZDept> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getHosList", organization);
    return list;
  }
  
  public String getAllDeptIds()
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getAllDeptIds", null);
    
    StringBuffer bf = new StringBuffer();
    for (JSONObject dp : list) {
      bf.append(dp.getString("seq_id")).append(",");
    }
    return bf.toString();
  }
  
  public String getCurrHosDeptIds(YZDept dept)
    throws Exception
  {
    String organization = dept.getDeptCode();
    if (YZUtility.isNullorEmpty(organization)) {
      throw new Exception("门诊编号为空");
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getCurrHosDeptIds", organization);
    
    StringBuffer bf = new StringBuffer();
    for (JSONObject dp : list) {
      bf.append(dp.getString("seq_id")).append(",");
    }
    return bf.toString();
  }
  
  public int deleteBySeqIds(String seqids, HttpServletRequest request)
    throws Exception
  {
    List<String> idList = YZUtility.ConvertString2List(seqids);
    int count = ((Integer)this.dao.delete(TableNameUtil.SYS_DEPT + ".deleteBySeqIds", idList)).intValue();
    
    SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_DEPT, seqids, TableNameUtil.SYS_DEPT, request);
    return count;
  }
  
  public int countByDeptCode(YZDept dept)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("deptCode", dept.getDeptCode());
    json.put("seqId", dept.getSeqId());
    int num = ((Integer)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".countByDeptCode", json)).intValue();
    return num;
  }
  
  public int countByDeptNameCurrHos(YZDept dept)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("deptCode", dept.getDeptCode());
    json.put("deptName", dept.getDeptName());
    json.put("seqId", dept.getSeqId());
    int num = ((Integer)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".countByDeptNameCurrHos", json)).intValue();
    return num;
  }
  
  public List<YZDept> getDeptListBySeqIds(String seqIds)
    throws Exception
  {
    List<String> idList = YZUtility.ConvertString2List(seqIds);
    List<YZDept> deptlist = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptListBySeqIds", idList);
    return deptlist;
  }
  
  public List<YZDept> getSubDeptListBySeqId(String deptParent, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("deptParent", deptParent);
    json.put("organization", organization);
    List<YZDept> deptlist = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getSubDeptListBySeqId", json);
    return deptlist;
  }
  
  public List<YZDept> getSubOrgDeptListBySeqId(String deptParent, String organization, String depttype)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("deptParent", deptParent);
    json.put("organization", organization);
    json.put("depttype", depttype);
    if (YZUtility.isNotNullOrEmpty(depttype))
    {
      List<String> deptTypeList = YZUtility.ConvertString2List(depttype);
      json.put("deptTypeList", deptTypeList);
    }
    List<YZDept> deptlist = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getSubOrgDeptListBySeqId", json);
    return deptlist;
  }
  
  public boolean IsHaveChild(String deptParent)
    throws Exception
  {
    int count1 = ((Integer)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".IsHaveChild", deptParent)).intValue();
    return count1 > 0;
  }
  
  public boolean IsHaveChildPerson(String id)
    throws Exception
  {
    int count1 = ((Integer)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".IsHaveChild", id)).intValue();
    int count2 = ((Integer)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".IsHaveChildPerson", id)).intValue();
    return count1 + count2 > 0;
  }
  
  public String getSelectDeptTree(String deptId, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    getSelectDeptTreeJson(deptId, sb, 0, organization);
    if (sb.charAt(sb.length() - 1) == ',') {
      sb.deleteCharAt(sb.length() - 1);
    }
    sb.append("]");
    return sb.toString();
  }
  
  public String getSelectDeptTreePaiban(String deptId, String organization)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    getSelectDeptTreeJsonPaiban(deptId, sb, 0, organization);
    if (sb.charAt(sb.length() - 1) == ',') {
      sb.deleteCharAt(sb.length() - 1);
    }
    sb.append("]");
    return sb.toString();
  }
  
  public void getSelectDeptTreeJson(String deptParent, StringBuffer sb, int level, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("deptParent", deptParent);
    json.put("organization", organization);
    












    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getSelectDeptTreeJson", json);
    for (int i = 0; i < list.size(); i++)
    {
      String flag = "&nbsp;├";
      if (i == list.size() - 1) {
        flag = "&nbsp;└";
      }
      String tmp = "";
      for (int j = 0; j < level; j++) {
        tmp = tmp + "&nbsp;│";
      }
      flag = tmp + flag;
      
      Map dp = (Map)list.get(i);
      String seqId = (String)dp.get("seqId");
      String deptName = (String)dp.get("deptName");
      sb.append("{");
      sb.append("text:'" + flag + YZUtility.encodeSpecial(deptName) + "',");
      sb.append("value:'" + seqId + "'");
      sb.append("},");
      getSelectDeptTreeJson(seqId, sb, level + 1, organization);
    }
  }
  
  public void getSelectDeptTreeJsonPaiban(String deptParent, StringBuffer sb, int level, String organization)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("deptParent", deptParent);
    json.put("organization", organization);
    



    deptParent = json.getString("deptParent");
    String deptType = "1";
    if (deptParent.length() == 36) {
      json.put("deptType", deptType);
    }
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getSelectDeptTreeJsonPaiban", json);
    for (int i = 0; i < list.size(); i++)
    {
      String flag = "&nbsp;├";
      if (i == list.size() - 1) {
        flag = "&nbsp;└";
      }
      String tmp = "";
      for (int j = 0; j < level; j++) {
        tmp = tmp + "&nbsp;│";
      }
      flag = tmp + flag;
      
      Map dp = (Map)list.get(i);
      String seqId = (String)dp.get("seqId");
      String deptName = (String)dp.get("deptName");
      sb.append("{");
      sb.append("text:'" + flag + YZUtility.encodeSpecial(deptName) + "',");
      sb.append("value:'" + seqId + "'");
      sb.append("},");
      getSelectDeptTreeJsonPaiban(seqId, sb, level + 1, organization);
    }
  }
  
  public List<JSONObject> getDeptNodeList(String parentId, List<JSONObject> treeList, String personIds)
    throws Exception
  {
    List<YZDept> deptList = getSubDeptListBySeqId(parentId, null);
    for (YZDept dept : deptList)
    {
      boolean haveChild = IsHaveChild(dept.getSeqId());
      JSONObject node = new JSONObject();
      node.put("id", dept.getSeqId());
      if ("0".equals(parentId)) {
        node.put("pId", "orgId");
      } else {
        node.put("pId", dept.getDeptParent());
      }
      node.put("name", dept.getDeptName());
      node.put("isParent", Boolean.valueOf(haveChild));
      node.put("nocheck", Boolean.valueOf(false));
      treeList.add(node);
      if (haveChild) {
        getDeptNodeList(dept.getSeqId(), treeList, personIds);
      }
    }
    return treeList;
  }
  
  public String getTopDeptName(String deptcode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getTopDeptName", deptcode);
    if ((list != null) && (list.size() > 0)) {
      return ((JSONObject)list.get(0)).getString("dept_name");
    }
    return "";
  }
  
  public YZDept getTopDept(String seqId)
    throws Exception
  {
    YZDept dept = (YZDept)this.dao.loadObjSingleUUID(TableNameUtil.SYS_DEPT, seqId);
    if (dept != null)
    {
      String parent = dept.getDeptParent();
      if ("0".equals(parent)) {
        return dept;
      }
      return getTopDept(parent);
    }
    return null;
  }
  
  public YZDept getDeptParentIdByPerson(YZPerson person)
    throws Exception
  {
    return getTopDept(person.getDeptId());
  }
  
  public List<YZDept> getDeptListByDeptType(String dept_types, String organization)
    throws Exception
  {
    Set<String> seqIdSet = new HashSet();
    String[] typeArr = dept_types.split(",");
    for (String deptType : typeArr) {
      if (!YZUtility.isNullorEmpty(deptType))
      {
        JSONObject json = new JSONObject();
        json.put("deptType", deptType);
        json.put("organization", organization);
        
        List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptListByDeptType", json);
        for (JSONObject job : list)
        {
          String tmpId = job.getString("seq_id");
          if (!YZUtility.isNullorEmpty(tmpId)) {
            seqIdSet.add(tmpId);
          }
        }
      }
    }
    StringBuffer seqIdBf = new StringBuffer();
    Object idlist = new ArrayList(seqIdSet);
    for (??? = ((List)idlist).iterator(); ((Iterator)???).hasNext();)
    {
      String seqid = (String)((Iterator)???).next();
      if (!YZUtility.isNullorEmpty(seqid)) {
        seqIdBf.append(seqid).append(",");
      }
    }
    Object deptList = getDeptListBySeqIds(seqIdBf.toString());
    return deptList;
  }
  
  public List<YZDept> getAllDeptByOrganization(String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    if (!YZUtility.isNullorEmpty(organization)) {
      map.put("organization", organization);
    }
    List<YZDept> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getAllDeptByOrganization", map);
    return list;
  }
  
  public String getDeptNamesBySeqIds(String ids)
    throws Exception, Exception
  {
    StringBuffer namesBf = new StringBuffer();
    
    List<String> idList = YZUtility.ConvertString2List(ids);
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptNamesBySeqIds", idList);
    for (JSONObject job : list)
    {
      String deptName = job.getString("dept_name");
      if (!YZUtility.isNullorEmpty(deptName)) {
        namesBf.append(deptName).append(",");
      }
    }
    String namesStr = namesBf.toString();
    if (namesStr.endsWith(",")) {
      namesStr = namesStr.substring(0, namesStr.length() - 1);
    }
    return namesStr;
  }
  
  public String getDeptSeqIdByUserSeqId(String userSeqId)
    throws Exception
  {
    YZPerson person = (YZPerson)this.dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, userSeqId);
    if (person != null) {
      return person.getDeptId();
    }
    throw new Exception("用户不存在");
  }
  
  public YZDept getTopDeptByCode(String deptcode)
    throws Exception
  {
    List<YZDept> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getTopDeptByCode", deptcode);
    if ((list == null) || (list.size() == 0)) {
      return new YZDept();
    }
    return (YZDept)list.get(0);
  }
  
  public YZDept getDeptByTypeAndName(String deptType, String deptName)
    throws Exception
  {
    JSONObject json = new JSONObject();
    json.put("deptType", deptType);
    json.put("deptName", deptName);
    
    List<YZDept> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptByTypeAndName", json);
    if ((list == null) || (list.size() == 0)) {
      return new YZDept();
    }
    return (YZDept)list.get(0);
  }
  
  public String getMaxDeptNO(String organization)
    throws Exception
  {
    JSONObject json = (JSONObject)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".getMaxDeptNO", organization);
    if (json == null) {
      return "1";
    }
    String maxno = json.getString("dept_no");
    return maxno;
  }
  
  public List<JSONObject> findMarket(String marketing)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("marketing", marketing);
    return (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".findMarket", map);
  }
  
  public List<JSONObject> selectBeanListByMap(String s)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("s", s);
    return (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getSelectByseqId", map);
  }
  
  public YZDept findmarketing(String isyx, String depttype, String shouli)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("isyx", isyx);
    map.put("shouli", shouli);
    map.put("depttype", depttype);
    return (YZDept)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".findmarketing", map);
  }
  
  public List<JSONObject> findDeptByDeptType(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".findDeptByDeptType", map);
    return list;
  }
  
  public List<JSONObject> findAllDeptByDeptType(String organization)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".findAllDeptByDeptType", organization);
    return list;
  }
  
  public JSONObject findDeptNameBySeqId(String seqId)
    throws Exception
  {
    JSONObject deptName = (JSONObject)this.dao.findForObject(TableNameUtil.SYS_DEPT + ".findDeptNameBySeqId", seqId);
    return deptName;
  }
  
  public List<YZDept> findDeptList(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".findDeptList", map);
  }
  
  public List<YZDict> getListALL(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".findDeptListAll", map);
  }
  
  public List<YZDept> getSelectOrganization()
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.SYS_DEPT + ".getSelectOrganization", null);
  }
  
  public String getDeptSeqIdByUserSeqIdAndOrganization(String askperson, String organization)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("askperson", askperson);
    map.put("organization", organization);
    JSONObject person = (JSONObject)this.dao.findForObject(TableNameUtil.SYS_PERSON + ".getDeptSeqIdByUserSeqIdAndOrganization", map);
    return person.getString("seq_id");
  }
}
