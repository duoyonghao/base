package com.hudh.dept.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.dept.entity.SysDeptPriv;
import com.hudh.doctorpick.entity.GoodsDoctorPickIn;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;

import net.sf.json.JSONObject;

@Service
public class SysDeptPrivDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 
	 * <p>Title: findDeptNameByButtonName</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月28日 
	 * @param buttonName
	 * @return 查询部门名称
	 * @throws Exception
	 */
	public List<JSONObject> findDeptNameByButtonName(Map<String,String> map) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList("SYS_DEPT_PRIV.findDeptNameByButtonName", map);
		return list;
	}
	/**
	 * 根据id查询详情
	 * <p>Title: findDeptPrivByDeptPrivId</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param deptprivid
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findDeptPrivByDeptPrivId(String deptprivid) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList("SYS_DEPT_PRIV.findDeptPrivByDeptPrivId", deptprivid);
		return list;
	}
	/**
	 * 根据按钮查询部门人员
	 * <p>Title: findPersonSeqIdByButtonName</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月29日 
	 * @param buttonName
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findPersonSeqIdByButtonName(Map<String,String> map) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList("SYS_DEPT_PRIV.findPersonSeqIdByButtonName", map);
		return list;
	}
	/**
	 * 根据部门id查询人员
	 * <p>Title: findPersonSeqIdByDeptId</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月29日 
	 * @param deptCategory
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findPersonSeqIdByDeptId(String deptCategory) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList("SYS_PERSON.findVisualPersonnel", deptCategory);
		return list;
	}
	/**
	 * 添加部门和权限关联表数据
	 * <p>Title: insertSysDeptPriv</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月28日 
	 * @param sysDeptPriv
	 * @throws Exception
	 */
	public void insertSysDeptPriv(SysDeptPriv sysDeptPriv) throws Exception {
		dao.save("SYS_DEPT_PRIV.insertSysDeptPriv", sysDeptPriv);
	}
	/**
	 * 根据id修改数据
	 * <p>Title: updateSysDeptPriv</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param sysDeptPriv
	 * @return
	 * @throws Exception
	 */
	public int updateSysDeptPriv(SysDeptPriv sysDeptPriv) throws Exception {
		int i=(int) dao.update("SYS_DEPT_PRIV.updateSysDeptPriv", sysDeptPriv);
		return i;
	}
	/**
	 * 根据按钮id删除部门关联数据
	 * <p>Title: deleteSysDeptPriv</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月29日 
	 * @param buttonSeqId
	 * @throws Exception
	 */
	public void deleteSysDeptPriv(String seqId) throws Exception {
		dao.delete("SYS_DEPT_PRIV.deleteSysDeptPriv", seqId);
	}
	/**
	 * 多部门查询人员数据
	 * <p>Title: findPersonByDeptId</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findPersonByDeptId(Map<String,String> map) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList("SYS_PERSON.findPersonByDeptId", map);
		return list;
	}
	public YZPerson findPersonBySeqId(Map<String,String> map) throws Exception {
		YZPerson list=(YZPerson) dao.findForObject("SYS_PERSON.selectByPrimaryKey", map);
		return list;
	}
}
