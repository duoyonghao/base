package com.hudh.dept.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudh.dept.dao.SysDeptPrivDao;
import com.hudh.dept.entity.SysDeptPriv;
import com.hudh.dept.service.ISysDeptPrivService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class SysDeptPrivServiceImpl implements ISysDeptPrivService {
	@Autowired
	private SysDeptPrivDao sysDeptPrivDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<JSONObject> findDeptNameByButtonName(Map<String,String> map) throws Exception {
		List<JSONObject> list=sysDeptPrivDao.findDeptNameByButtonName(map);
		return list;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<JSONObject> findDeptPrivByDeptPrivId(String deptprivid) throws Exception {
		List<JSONObject> list=sysDeptPrivDao.findDeptPrivByDeptPrivId(deptprivid);
		return list;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertSysDeptPriv(Map<String, String> map) throws Exception {
		SysDeptPriv sysDeptPriv=new SysDeptPriv();
		sysDeptPriv.setSeqId(YZUtility.getUUID());
		sysDeptPriv.setButtonName(map.get("buttonname"));
		sysDeptPriv.setCreatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
		sysDeptPriv.setCreateuser(map.get("createuser"));
		sysDeptPriv.setDeptNoCompilations(map.get("deptNoCompilations"));
		sysDeptPriv.setDeptName(map.get("deptName"));
		sysDeptPriv.setDeptType(map.get("deptType"));
		sysDeptPriv.setOrganization(map.get("organization"));
		sysDeptPrivDao.insertSysDeptPriv(sysDeptPriv);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteSysDeptPriv(String deptprivid) throws Exception {
		sysDeptPrivDao.deleteSysDeptPriv(deptprivid);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateSysDeptPriv(Map<String, String> map) throws Exception {
		SysDeptPriv sysDeptPriv=new SysDeptPriv();
		sysDeptPriv.setSeqId(map.get("deptprivid"));
		sysDeptPriv.setUpdatetime(YZUtility.getCurDateTimeStr("yyyy-MM-dd HH:mm:ss"));
		sysDeptPriv.setUpdateuser(map.get("createuser"));
		sysDeptPriv.setDeptNoCompilations(map.get("deptNoCompilations"));
		sysDeptPriv.setDeptName(map.get("deptName"));
		sysDeptPriv.setDeptType(map.get("deptType"));
		int i=sysDeptPrivDao.updateSysDeptPriv(sysDeptPriv);
		return i;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<JSONObject> findPersonByDeptId(Map<String,String> map) throws Exception {
		//查询所有员工

		List<JSONObject> personlist=new ArrayList<JSONObject>();
		if(map.get("deptid").equals("all")){
			if(!YZUtility.isNullorEmpty(map.get("buttonname"))){
				//通过按钮查询部门id
				List<JSONObject> list = sysDeptPrivDao.findDeptNameByButtonName(map);
				if(list.size()>0){
					StringBuffer strs = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						if(i==list.size()-1){
							strs.append("\'"+list.get(i).getString("id")+"\'");
						}else{
							strs.append("\'"+list.get(i).getString("id")+"\',");
						}
					}
					map.put("deptId", strs.toString());
					personlist=sysDeptPrivDao.findPersonByDeptId(map);
				}
			}else{
				//没有权限
				personlist=null;
			}
		}else{
			//查询单部门员工
			if(!YZUtility.isNullorEmpty(map.get("buttonname"))){
				//通过按钮查询部门id
				map.put("deptId", "\'"+map.get("deptid")+"\'");
				personlist=sysDeptPrivDao.findPersonByDeptId(map);
			}else{
				//没有权限
				personlist=null;
			}
		}
		return personlist;
	}
}
