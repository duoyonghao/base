package com.hudh.dept.service.Impl;

import com.hudh.dept.dao.SysDeptPrivDao;
import com.hudh.dept.entity.SysDeptPriv;
import com.hudh.dept.service.ISysDeptPrivService;
import com.kqds.util.sys.YZUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysDeptPrivServiceImpl implements ISysDeptPrivService {
  @Autowired
  private SysDeptPrivDao sysDeptPrivDao;
  
  @Transactional(rollbackFor = {Exception.class})
  public List<JSONObject> findDeptNameByButtonName(Map<String, String> map) throws Exception {
    List<JSONObject> list = this.sysDeptPrivDao.findDeptNameByButtonName(map);
    return list;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public List<JSONObject> findDeptPrivByDeptPrivId(String deptprivid) throws Exception {
    List<JSONObject> list = this.sysDeptPrivDao.findDeptPrivByDeptPrivId(deptprivid);
    return list;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public void insertSysDeptPriv(Map<String, String> map) throws Exception {
    SysDeptPriv sysDeptPriv = new SysDeptPriv();
    sysDeptPriv.setSeqId(YZUtility.getUUID());
    sysDeptPriv.setButtonName(map.get("buttonname"));
    sysDeptPriv.setCreatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
    sysDeptPriv.setCreateuser(map.get("createuser"));
    sysDeptPriv.setDeptNoCompilations(map.get("deptNoCompilations"));
    sysDeptPriv.setDeptName(map.get("deptName"));
    sysDeptPriv.setDeptType(map.get("deptType"));
    sysDeptPriv.setOrganization(map.get("organization"));
    this.sysDeptPrivDao.insertSysDeptPriv(sysDeptPriv);
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public void deleteSysDeptPriv(String deptprivid) throws Exception {
    this.sysDeptPrivDao.deleteSysDeptPriv(deptprivid);
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public int updateSysDeptPriv(Map<String, String> map) throws Exception {
    SysDeptPriv sysDeptPriv = new SysDeptPriv();
    sysDeptPriv.setSeqId(map.get("deptprivid"));
    sysDeptPriv.setUpdatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
    sysDeptPriv.setUpdateuser(map.get("createuser"));
    sysDeptPriv.setDeptNoCompilations(map.get("deptNoCompilations"));
    sysDeptPriv.setDeptName(map.get("deptName"));
    sysDeptPriv.setDeptType(map.get("deptType"));
    int i = this.sysDeptPrivDao.updateSysDeptPriv(sysDeptPriv);
    return i;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public List<JSONObject> findPersonByDeptId(Map<String, String> map) throws Exception {
    List<JSONObject> personlist = new ArrayList<>();
    if (((String)map.get("deptid")).equals("all")) {
      if (!YZUtility.isNullorEmpty(map.get("buttonname"))) {
        List<JSONObject> list = this.sysDeptPrivDao.findDeptNameByButtonName(map);
        if (list.size() > 0) {
          StringBuffer strs = new StringBuffer();
          for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
              strs.append("'" + ((JSONObject)list.get(i)).getString("id") + "'");
            } else {
              strs.append("'" + ((JSONObject)list.get(i)).getString("id") + "',");
            } 
          } 
          map.put("deptId", strs.toString());
          personlist = this.sysDeptPrivDao.findPersonByDeptId(map);
        } 
      } else {
        personlist = null;
      } 
    } else if (!YZUtility.isNullorEmpty(map.get("buttonname"))) {
      map.put("deptId", "'" + (String)map.get("deptid") + "'");
      personlist = this.sysDeptPrivDao.findPersonByDeptId(map);
    } else {
      personlist = null;
    } 
    return personlist;
  }
}
