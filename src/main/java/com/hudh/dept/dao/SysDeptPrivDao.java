package com.hudh.dept.dao;

import com.hudh.dept.entity.SysDeptPriv;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDeptPrivDao {
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> findDeptNameByButtonName(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("SYS_DEPT_PRIV.findDeptNameByButtonName", map);
    return list;
  }
  
  public List<JSONObject> findDeptPrivByDeptPrivId(String deptprivid) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("SYS_DEPT_PRIV.findDeptPrivByDeptPrivId", deptprivid);
    return list;
  }
  
  public List<JSONObject> findPersonSeqIdByButtonName(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("SYS_DEPT_PRIV.findPersonSeqIdByButtonName", map);
    return list;
  }
  
  public List<JSONObject> findPersonSeqIdByDeptId(String deptCategory) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("SYS_PERSON.findVisualPersonnel", deptCategory);
    return list;
  }
  
  public void insertSysDeptPriv(SysDeptPriv sysDeptPriv) throws Exception {
    this.dao.save("SYS_DEPT_PRIV.insertSysDeptPriv", sysDeptPriv);
  }
  
  public int updateSysDeptPriv(SysDeptPriv sysDeptPriv) throws Exception {
    int i = ((Integer)this.dao.update("SYS_DEPT_PRIV.updateSysDeptPriv", sysDeptPriv)).intValue();
    return i;
  }
  
  public void deleteSysDeptPriv(String seqId) throws Exception {
    this.dao.delete("SYS_DEPT_PRIV.deleteSysDeptPriv", seqId);
  }
  
  public List<JSONObject> findPersonByDeptId(Map<String, String> map) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList("SYS_PERSON.findPersonByDeptId", map);
    return list;
  }
  
  public YZPerson findPersonBySeqId(Map<String, String> map) throws Exception {
    YZPerson list = (YZPerson)this.dao.findForObject("SYS_PERSON.selectByPrimaryKey", map);
    return list;
  }
}
