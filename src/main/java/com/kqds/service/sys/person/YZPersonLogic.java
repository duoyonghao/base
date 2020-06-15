package com.kqds.service.sys.person;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZPassEncrypt;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.QueryFirstLog;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZPersonLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private YZDeptLogic deptLogic;
  
  public List<JSONObject> getListByVisualstaff(String visualstaffQuery) throws Exception {
    List<String> list = YZUtility.ConvertString2List(visualstaffQuery);
    List<JSONObject> plist = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getListByVisualstaff", list);
    return plist;
  }
  
  public String getMaxOrderno(String deptId) throws Exception {
    String result = (String)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_PERSON) + ".getMaxOrderno", deptId);
    return (result == null) ? "0" : result;
  }
  
  public YZPerson queryPerson(String userName, HttpServletRequest request) throws Exception {
    try {
      YZPerson person = null;
      if (ChainUtil.isOpenChain()) {
        String organization = ChainUtil.getOrganizationFromUrl(request);
        JSONObject json = new JSONObject();
        json.put("USER_NAME", userName);
        json.put("DEPT_CODE", organization);
        List<YZPerson> list = (List<YZPerson>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonByUserNameAndOrg", json);
        if (list != null && list.size() == 1) {
          person = list.get(0);
          return person;
        } 
      } 
      Map<String, String> filter = new HashMap<>();
      filter.put("USER_ID", userName);
      List<YZPerson> list2 = (List<YZPerson>)this.dao.loadList(TableNameUtil.SYS_PERSON, filter);
      if (list2.size() > 0)
        person = list2.get(0); 
      return person;
    } catch (Exception ex) {
      throw ex;
    } 
  }
  
  public String getPerIdsByDeptTypeNoOrg(String deptType) throws Exception {
    String ids = "";
    List<String> list = (List<String>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPerIdsByDeptTypeNoOrg", deptType);
    for (String seq_id : list)
      ids = String.valueOf(ids) + seq_id + ","; 
    if (ids.endsWith(","))
      ids = ids.substring(0, ids.length() - 1); 
    return ids;
  }
  
  public String getPerIdsByDeptTypeAndVisualNoOrg(String deptType, String visualstaff) throws Exception {
    StringBuffer sql = new StringBuffer(" select p.seq_id from " + TableNameUtil.SYS_PERSON + " p ");
    sql.append(" left join " + TableNameUtil.SYS_DEPT + " d on p.dept_id = d.seq_id ");
    sql.append(" where 1=1 ");
    if (!YZUtility.isNullorEmpty(deptType))
      sql.append(" and d.DEPT_TYPE like '%" + deptType + "%' "); 
    sql.append(" and p.seq_id in (" + visualstaff + ") ");
    List<String> visualstaffList = YZUtility.ConvertString2List(visualstaff);
    JSONObject json = new JSONObject();
    json.put("deptType", deptType);
    json.put("visualstaffList", visualstaffList);
    String ids = "";
    List<String> list = (List<String>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPerIdsByDeptTypeAndVisualNoOrg", json);
    for (String seq_id : list)
      ids = String.valueOf(ids) + seq_id + ","; 
    if (ids.endsWith(","))
      ids = ids.substring(0, ids.length() - 1); 
    return YZUtility.ConvertStringIds4Query(ids);
  }
  
  public String getPerIdsByPersonList(List<JSONObject> list) throws Exception {
    String ids = "";
    for (JSONObject rs : list)
      ids = String.valueOf(ids) + rs.getString("seq_id") + ","; 
    if (ids.endsWith(","))
      ids = ids.substring(0, ids.length() - 1); 
    return YZUtility.ConvertStringIds4Query(ids);
  }
  
  public List<YZPerson> getPersonListByDeptIds(String deptIds, String showleave, String organization) throws Exception {
    List<String> list = YZUtility.ConvertString2List(deptIds);
    JSONObject json = new JSONObject();
    json.put("idlist", list);
    json.put("showleave", showleave);
    json.put("organization", organization);
    List<YZPerson> plist = (List<YZPerson>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonListByDeptIds", json);
    return plist;
  }
  
  public List<JSONObject> getPersonIdListByDeptIds(String deptIds) throws Exception {
    List<String> list = YZUtility.ConvertString2List(deptIds);
    List<JSONObject> plist = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonIdListByDeptIds", list);
    return plist;
  }
  
  public int clearPassword(String seqIdStrs, HttpServletRequest request) throws Exception {
    List<String> listId = YZUtility.ConvertString2List(seqIdStrs);
    JSONObject json = new JSONObject();
    json.put("listId", listId);
    json.put("password", YZPassEncrypt.encryptPass(ConstUtil.EMPTY));
    int count = ((Integer)this.dao.update(String.valueOf(TableNameUtil.SYS_PERSON) + ".clearPassword", json)).intValue();
    SysLogUtil.log("清空密码", SysLogUtil.SYS_PERSON, seqIdStrs, TableNameUtil.SYS_PERSON, request);
    return count;
  }
  
  public int deleteBySeqIds(String seqids, HttpServletRequest request) throws Exception {
    List<String> listId = YZUtility.ConvertString2List(seqids);
    int count = ((Integer)this.dao.delete(String.valueOf(TableNameUtil.SYS_PERSON) + ".deleteBySeqIds", listId)).intValue();
    SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_PERSON, seqids, TableNameUtil.SYS_PERSON, request);
    return count;
  }
  
  public List<YZPerson> getUserListByDeptSeqId(String deptId) throws Exception {
    JSONObject json = new JSONObject();
    json.put("deptId", deptId);
    json.put("findInSetDeptIdOther", SQLUtil.findInSet(String.valueOf(deptId), "DEPT_ID_OTHER"));
    List<YZPerson> persons = (List<YZPerson>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getUserListByDeptSeqId", json);
    return persons;
  }
  
  public int countByUserDocument(Map<String, String> map, String visualstaff) throws Exception {
    map.put("visualstaff", visualstaff);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_PERSON) + ".countByUserDocument", map)).intValue();
    return count;
  }
  
  public int countByDeptId(String deptId, String username) throws Exception {
    List<String> deptIdList = YZUtility.ConvertString2List(deptId);
    JSONObject json = new JSONObject();
    json.put("deptIdList", deptIdList);
    json.put("username", username);
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_PERSON) + ".countByDeptId", json)).intValue();
    return count;
  }
  
  public int countByUserId(String userId) throws Exception {
    int num = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_PERSON) + ".countByUserId", userId)).intValue();
    return num;
  }
  
  public int countByUserNameCurrHos(String userName, String deptId, String personSeqId) throws Exception {
    JSONObject json = new JSONObject();
    json.put("userName", userName);
    json.put("personSeqId", personSeqId);
    json.put("deptId", deptId);
    int num = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_PERSON) + ".countByUserNameCurrHos", json)).intValue();
    return num;
  }
  
  public JSONObject selectPage(BootStrapPage bp, String deptId, String username) throws Exception {
    List<String> idList = YZUtility.ConvertString2List(deptId);
    JSONObject json = new JSONObject();
    json.put("idList", idList);
    json.put("username", username);
    json.put("search", bp.getSearch());
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".selectPage", json);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    for (JSONObject p : list) {
      YZDept d = this.deptLogic.getTopDept(p.getString("dept_id"));
      p.put("organizationname", d.getDeptName());
      String pass = p.getString("password");
      if (YZPassEncrypt.isValidPas("", pass)) {
        p.put("isEmptyPass", "1");
        continue;
      } 
      p.put("isEmptyPass", "0");
    } 
    pageInfo.setList(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectNoPage(String deptId, String seqId, String showleave) throws Exception {
    JSONObject json = new JSONObject();
    json.put("showleave", showleave);
    json.put("seqId", seqId);
    json.put("deptId", deptId);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".selectNoPage", json);
    return list;
  }
  
  public List<JSONObject> getDeptNodeList(String parentId, List<JSONObject> treeList, String personIds) throws Exception {
    List<YZDept> deptList = this.deptLogic.getSubDeptListBySeqId(parentId, null);
    for (YZDept dept : deptList) {
      boolean haveChild = this.deptLogic.IsHaveChildPerson(dept.getSeqId());
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
      List<YZPerson> persons = getUserListByDeptSeqId(dept.getSeqId());
      for (YZPerson p : persons) {
        JSONObject nodeP = new JSONObject();
        nodeP.put("id", "person" + p.getSeqId());
        nodeP.put("pId", dept.getSeqId());
        nodeP.put("name", p.getUserId());
        nodeP.put("isParent", Boolean.valueOf(false));
        nodeP.put("iconSkin", "person");
        nodeP.put("nocheck", Boolean.valueOf(false));
        nodeP.put("personseqid", p.getSeqId());
        boolean isCheck = YZUtility.isStrInArrayEach(p.getSeqId(), personIds);
        nodeP.put("checked", Boolean.valueOf(isCheck));
        treeList.add(nodeP);
      } 
      haveChild = this.deptLogic.IsHaveChild(dept.getSeqId());
      if (haveChild)
        getDeptNodeList(dept.getSeqId(), treeList, personIds); 
    } 
    return treeList;
  }
  
  public List<JSONObject> getPersonListByDeptTypeAndVisual(String depttype, String visualstaff, String organization) throws Exception {
    List<String> idList = YZUtility.ConvertString2List(visualstaff);
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("depttype", depttype);
    json.put("idList", idList);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonListByDeptTypeAndVisual", json);
    return list;
  }
  
  public List<JSONObject> getPersonListByDeptType(String depttype, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("depttype", depttype);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonListByDeptType", json);
    return list;
  }
  
  public List<JSONObject> getPersonListByDeptType1(String depttype, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("depttype", depttype);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonListByDeptType1", json);
    return list;
  }
  
  public String getPersonsByDeptType(String depttype, String organization) throws Exception {
    String seqids = "";
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("depttype", depttype);
    List<String> list = (List<String>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonsByDeptType", json);
    for (String seqId : list)
      seqids = String.valueOf(seqids) + seqId + ","; 
    return seqids;
  }
  
  public List<YZPerson> getPersonListByRole(String roleId, String organization) throws Exception {
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("roleId", roleId);
    List<YZPerson> list = (List<YZPerson>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonListByRole", json);
    return list;
  }
  
  public List<YZPerson> getPersonListByRolesNoOrg(String rolesId) throws Exception {
    List<String> idList = YZUtility.ConvertString2List(rolesId);
    List<YZPerson> list = (List<YZPerson>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonListByRolesNoOrg", idList);
    return list;
  }
  
  public List<JSONObject> getPersonListByRoleAndVisual(String roleId, String visualStaff, String organization) throws Exception {
    List<String> roleIdList = YZUtility.ConvertString2List(roleId);
    List<String> visualIdList = YZUtility.ConvertString2List(visualStaff);
    JSONObject json = new JSONObject();
    json.put("roleIdList", roleIdList);
    json.put("visualIdList", visualIdList);
    json.put("organization", organization);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonListByRoleAndVisual", json);
    return list;
  }
  
  public boolean yzUserBySeqIds(String ids, String organization) throws Exception, Exception {
    boolean flag = true;
    List<JSONObject> list = getPersonOrgListBySeqIds(ids);
    for (JSONObject job : list) {
      String dept_code = job.getString("dept_code");
      if (!dept_code.equals(organization))
        flag = false; 
    } 
    return flag;
  }
  
  public String getNameStrBySeqIds(String ids) throws Exception, Exception {
    String namesStr = "";
    StringBuffer namesBf = new StringBuffer();
    List<JSONObject> list = getPersonListBySeqIds(ids);
    for (JSONObject job : list) {
      String uname = job.getString("user_name");
      if (YZUtility.isNullorEmpty(uname))
        continue; 
      namesBf.append(uname).append(",");
    } 
    namesStr = namesBf.toString();
    if (namesStr.endsWith(","))
      namesStr = namesStr.substring(0, namesStr.length() - 1); 
    return namesStr;
  }
  
  public List<JSONObject> getPersonListBySeqIds(String ids) throws Exception, Exception {
    List<String> idList = YZUtility.ConvertString2List(ids);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonListBySeqIds", idList);
    return list;
  }
  
  public List<JSONObject> getPersonOrgListBySeqIds(String ids) throws Exception, Exception {
    List<String> idList = YZUtility.ConvertString2List(ids);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonOrgListBySeqIds", idList);
    return list;
  }
  
  public List<JSONObject> getPersonListBySeqIds4Online(String ids, String organization) throws Exception, Exception {
    List<String> idList = YZUtility.ConvertString2List(ids);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonListBySeqIds4Online", idList);
    return list;
  }
  
  public YZPerson getPersonByUserId(String userId) throws Exception {
    List<YZPerson> plist = (List<YZPerson>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getPersonByUserId", userId);
    if (plist != null && plist.size() > 0)
      return plist.get(0); 
    return null;
  }
  
  public YZPerson getPersonBySeqId(String seqId) throws Exception {
    YZPerson person = (YZPerson)this.dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, seqId);
    return person;
  }
  
  public List<JSONObject> getPersonList(HttpServletRequest request, String deptId, String seqId) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    String organzation = ChainUtil.getCurrentOrganization(request);
    if (YZUtility.isNotNullOrEmpty(organzation))
      map.put("organzation", organzation); 
    String visualstaff = SessionUtil.getVisualstaff(request);
    if (YZUtility.isNullorEmpty(deptId) && (YZUtility.isNullorEmpty(seqId) || seqId.equals("null")))
      map.put("visualstaff", visualstaff); 
    if (YZUtility.isNullorEmpty(deptId)) {
      String deptTreeData = this.deptLogic.getSelectDeptTreePaiban("0", ChainUtil.getCurrentOrganization(request));
      JSONArray deptTreeOArray = JSONArray.fromObject(deptTreeData);
      for (Object object : deptTreeOArray) {
        JSONObject dept = (JSONObject)object;
        String tmpDeptId = dept.get("value").toString();
        if (YZUtility.isNullorEmpty(tmpDeptId))
          continue; 
        map.put("dept_id", tmpDeptId);
        List<JSONObject> tmpList = selectPersonPaiban(map);
        list.addAll(tmpList);
      } 
    } else {
      map.put("dept_id", deptId);
      if (!YZUtility.isNullorEmpty(seqId) && !seqId.equals("null"))
        map.put("seq_id", seqId); 
      list = selectPersonPaiban(map);
    } 
    return list;
  }
  
  public List<JSONObject> selectPersonPaiban(Map map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".selectPersonPaiban", map);
    return list;
  }
  
  public String filterVisualPersons(String deptType, String visualPersonStr, String otherpriv) throws Exception {
    List<String> privList = YZUtility.ConvertString2List(otherpriv);
    List<String> visualList = YZUtility.ConvertString2List(visualPersonStr);
    JSONObject json = new JSONObject();
    json.put("deptType", deptType);
    json.put("privList", privList);
    json.put("visualList", visualList);
    List<JSONObject> plist = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".filterVisualPersons", json);
    StringBuffer pIdBf = new StringBuffer();
    for (int i = 0; i < plist.size(); i++) {
      String pid = ((JSONObject)plist.get(i)).getString("seq_id");
      if (!YZUtility.isNullorEmpty(pid))
        pIdBf.append(pid).append(","); 
    } 
    if (pIdBf.length() > 0)
      pIdBf.deleteCharAt(pIdBf.length() - 1); 
    String queryIdsTmp = YZUtility.ConvertStringIds4Query(pIdBf.toString());
    return queryIdsTmp;
  }
  
  public List<JSONObject> getAllShowfeiPerson(String organization, HttpServletRequest request) throws Exception {
    String sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID);
    List<String> privList = YZUtility.ConvertString2List(sfpriv);
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("privList", privList);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getAllShowfeiPerson", json);
    return list;
  }
  
  public List<JSONObject> getAllShowZjlPerson(String organization, HttpServletRequest request) throws Exception {
    String zjlpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_ZONGJINGLI_SEQID);
    List<String> privList = YZUtility.ConvertString2List(zjlpriv);
    JSONObject json = new JSONObject();
    json.put("organization", organization);
    json.put("privList", privList);
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".getAllShowZjlPerson", json);
    return list;
  }
  
  public QueryFirstLog queryFirstLog(String userName) throws Exception {
    QueryFirstLog query = (QueryFirstLog)this.dao.findForObject("SYS_FIRSTLOG.queryFirstLog", userName);
    return query;
  }
  
  public void saveQueryLog(QueryFirstLog queryFirstLog) throws Exception {
    this.dao.save("SYS_FIRSTLOG.saveQueryLog", queryFirstLog);
  }
  
  public void updatePwd(YZPerson yzPerson) throws Exception {
    this.dao.update("SYS_FIRSTLOG.updatePwd", yzPerson);
  }
  
  public List<JSONObject> findVisualPersonnel(String deptId) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("deptId", deptId);
    return (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".findVisualPersonnel", map);
  }
  
  public String findPersonIsleaveBySeqId(String seqId) throws Exception {
    return (String)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_PERSON) + ".findPersonIsleaveBySeqId", seqId);
  }
  
  public List<JSONObject> findPersonalDetails(String userId) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("userId", userId);
    return (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_PERSON) + ".findPersonalDetails", map);
  }
  
  public int updateOrganization(Map<String, String> map) throws Exception {
    int i = ((Integer)this.dao.update(String.valueOf(TableNameUtil.SYS_PERSON) + ".updateOrganization", map)).intValue();
    return i;
  }
}
