package com.kqds.service.sys.dept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service("deptLogic")
public class YZDeptLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	public JSONObject getOneByNameAndCode(String deptName, String deptCode) throws Exception {
		JSONObject json = new JSONObject();
		json.put("deptName", deptName);
		json.put("deptCode", deptCode);

		return (JSONObject) dao.findForObject(TableNameUtil.SYS_DEPT + ".getOneByNameAndCode", json);
	}

	public List<JSONObject> getDeptSeqIdByTypeAndOrg(String organization, String dept_types) throws Exception {
		List<String> typeList = YZUtility.ConvertString2List(dept_types);
		JSONObject json = new JSONObject();
		json.put("typeList", typeList);
		json.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptSeqIdByTypeAndOrg", json);
		return list;

	}

	/**
	 * 获取门诊列表
	 * 
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	public List<YZDept> getHosList(String organization) throws Exception {
		List<YZDept> list = (List<YZDept>) dao.findForList(TableNameUtil.SYS_DEPT + ".getHosList", organization);
		return list;
	}

	/**
	 * 查询所有部门ID
	 * 
	 * @param conn
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	public String getAllDeptIds() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT + ".getAllDeptIds", null);

		StringBuffer bf = new StringBuffer();
		for (JSONObject dp : list) {
			bf.append(dp.getString("seq_id")).append(",");
		}

		return bf.toString();
	}

	/**
	 * 获取当前门诊的所有部门ID
	 * 
	 * @param conn
	 * @param topParentId
	 * @return
	 * @throws Exception
	 */
	public String getCurrHosDeptIds(YZDept dept) throws Exception {

		String organization = dept.getDeptCode();
		if (YZUtility.isNullorEmpty(organization)) {
			throw new Exception("门诊编号为空");
		}

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT + ".getCurrHosDeptIds", organization);

		StringBuffer bf = new StringBuffer();
		for (JSONObject dp : list) {
			bf.append(dp.getString("seq_id")).append(",");
		}

		return bf.toString();
	}

	/**
	 * 根据主键进行删除
	 * 
	 * @param conn
	 * @param seqids
	 * @return
	 * @throws Exception
	 */
	public int deleteBySeqIds(String seqids, HttpServletRequest request) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(seqids);
		int count = (int) dao.delete(TableNameUtil.SYS_DEPT + ".deleteBySeqIds", idList);
		// 记录日志
		SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_DEPT, seqids, TableNameUtil.SYS_DEPT, request);
		return count;
	}

	/**
	 * 新建、编辑门诊时（即一级部门），编号不能重复
	 * 
	 * @param conn
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	public int countByDeptCode(YZDept dept) throws Exception {
		JSONObject json = new JSONObject();
		json.put("deptCode", dept.getDeptCode());
		json.put("seqId", dept.getSeqId());
		int num = (int) dao.findForObject(TableNameUtil.SYS_DEPT + ".countByDeptCode", json);
		return num;
	}

	/**
	 * 统计同一个门诊下，某个部门名称出现的次数
	 * 
	 * @param conn
	 * @param userName
	 * @param deptId
	 * @param personSeqId
	 * @return
	 * @throws Exception
	 */
	public int countByDeptNameCurrHos(YZDept dept) throws Exception {
		JSONObject json = new JSONObject();
		json.put("deptCode", dept.getDeptCode());
		json.put("deptName", dept.getDeptName());
		json.put("seqId", dept.getSeqId());
		int num = (int) dao.findForObject(TableNameUtil.SYS_DEPT + ".countByDeptNameCurrHos", json);
		return num;
	}

	/**
	 * 根据主键数组，获取部门列表
	 * 
	 * @param dept_parent
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<YZDept> getDeptListBySeqIds(String seqIds) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(seqIds);
		List<YZDept> deptlist = (List<YZDept>) dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptListBySeqIds", idList);
		return deptlist;
	}

	/**
	 * 根据上级部门ID，获取子部门列表
	 * 
	 * @param dept_parent
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<YZDept> getSubDeptListBySeqId(String deptParent, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("deptParent", deptParent);
		json.put("organization", organization);
		List<YZDept> deptlist = (List<YZDept>) dao.findForList(TableNameUtil.SYS_DEPT + ".getSubDeptListBySeqId", json);
		return deptlist;
	}

	/**
	 * 只展示是门诊的
	 * 
	 * @param dept_parent
	 * @param conn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public List<YZDept> getSubOrgDeptListBySeqId(String deptParent, String organization, String depttype) throws Exception {
		JSONObject json = new JSONObject();
		json.put("deptParent", deptParent);
		json.put("organization", organization);
		json.put("depttype", depttype);

		if (YZUtility.isNotNullOrEmpty(depttype)) {
			List<String> deptTypeList = YZUtility.ConvertString2List(depttype);
			json.put("deptTypeList", deptTypeList);
		}
		List<YZDept> deptlist = (List<YZDept>) dao.findForList(TableNameUtil.SYS_DEPT + ".getSubOrgDeptListBySeqId", json);
		return deptlist;
	}

	public boolean IsHaveChild(String deptParent) throws Exception {
		int count1 = (int) dao.findForObject(TableNameUtil.SYS_DEPT + ".IsHaveChild", deptParent);
		return count1 > 0;
	}

	public boolean IsHaveChildPerson(String id) throws Exception {
		int count1 = (int) dao.findForObject(TableNameUtil.SYS_DEPT + ".IsHaveChild", id);
		int count2 = (int) dao.findForObject(TableNameUtil.SYS_DEPT + ".IsHaveChildPerson", id);
		return (count1 + count2) > 0;
	}

	/**
	 * 获取下拉部门树【适用于下拉框】
	 * 
	 * @param deptId
	 * @param conn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public String getSelectDeptTree(String deptId, String organization) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		getSelectDeptTreeJson(deptId, sb, 0, organization);
		if (sb.charAt(sb.length() - 1) == ',') {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 行政中心排班专用初始化查询  syp 2019-12-03
	 * @param deptId
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public String getSelectDeptTreePaiban(String deptId, String organization) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		getSelectDeptTreeJsonPaiban(deptId, sb, 0, organization);
		if (sb.charAt(sb.length() - 1) == ',') {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 获取下拉部门树【适用于下拉框】
	 * 
	 * @param deptId
	 * @param sb
	 * @param level
	 * @param conn
	 * @param organization
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public void getSelectDeptTreeJson(String deptParent, StringBuffer sb, int level, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("deptParent", deptParent);
		json.put("organization", organization);

		/**
		 * start syp 2019-11-22 （添加排班人员筛选、只查询医生）
		 */
//		deptParent = json.getString("deptParent");
//		String deptType = "1";
//		if(deptParent.length() == 36) {
//			json.put("deptType", deptType);
//		}
		/**
		 * end
		 */
		// 首选分级，然后记录级数，是否为最后一个。。。
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT + ".getSelectDeptTreeJson", json);
		for (int i = 0; i < list.size(); i++) {
			String flag = "&nbsp;├";
			if (i == list.size() - 1) {
				flag = "&nbsp;└";
			}
			String tmp = "";
			for (int j = 0; j < level; j++) {
				tmp += "&nbsp;│";
			}
			flag = tmp + flag;

			Map dp = list.get(i);
			String seqId = (String) dp.get("seqId");
			String deptName = (String) dp.get("deptName");
			sb.append("{");
			sb.append("text:'" + flag + YZUtility.encodeSpecial(deptName) + "',");
			sb.append("value:'" + seqId + "'");
			sb.append("},");
			getSelectDeptTreeJson(seqId, sb, level + 1, organization);
		}
	}
	
	/**
	 * 行政中心排班专用初始化查询  syp 2019-12-03
	 * @param deptParent
	 * @param sb
	 * @param level
	 * @param organization
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public void getSelectDeptTreeJsonPaiban(String deptParent, StringBuffer sb, int level, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("deptParent", deptParent);
		json.put("organization", organization);
		
		/**
		 * start syp 2019-11-22 （添加排班人员筛选、只查询医生）
		 */
		deptParent = json.getString("deptParent");
		String deptType = "1";
		if(deptParent.length() == 36) {
			json.put("deptType", deptType);
		}
		/**
		 * end
		 */
		// 首选分级，然后记录级数，是否为最后一个。。。
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT + ".getSelectDeptTreeJsonPaiban", json);
		for (int i = 0; i < list.size(); i++) {
			String flag = "&nbsp;├";
			if (i == list.size() - 1) {
				flag = "&nbsp;└";
			}
			String tmp = "";
			for (int j = 0; j < level; j++) {
				tmp += "&nbsp;│";
			}
			flag = tmp + flag;
			
			Map dp = list.get(i);
			String seqId = (String) dp.get("seqId");
			String deptName = (String) dp.get("deptName");
			sb.append("{");
			sb.append("text:'" + flag + YZUtility.encodeSpecial(deptName) + "',");
			sb.append("value:'" + seqId + "'");
			sb.append("},");
			getSelectDeptTreeJsonPaiban(seqId, sb, level + 1, organization);
		}
	}

	/**
	 * 一次性加载部门、人员树
	 * 
	 * @param dbConn
	 * @param parentId
	 * @param treeList
	 * @param personIds
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getDeptNodeList(String parentId, List<JSONObject> treeList, String personIds) throws Exception {
		List<YZDept> deptList = getSubDeptListBySeqId(parentId, null);
		for (YZDept dept : deptList) {
			boolean haveChild = IsHaveChild(dept.getSeqId());
			JSONObject node = new JSONObject();
			node.put("id", dept.getSeqId());
			if ("0".equals(parentId)) {
				node.put("pId", "orgId");
			} else {
				node.put("pId", dept.getDeptParent());
			}
			node.put("name", dept.getDeptName());
			node.put("isParent", haveChild);
			node.put("nocheck", false);
			treeList.add(node);
			if (haveChild) {
				getDeptNodeList(dept.getSeqId(), treeList, personIds);
			}
		}
		return treeList;
	}

	/***********************
	 * ###### 合并过来的代码 start #####
	 ***********************************/

	/**
	 * 这个方法需要注意的是 dept_parent = 0 目前的目录结构是 单位下一级 ，放一个 一级菜单， 比如好大夫口腔
	 * 
	 * @param conn
	 * @param deptcode
	 * @return
	 * @throws Exception
	 */
	public String getTopDeptName(String deptcode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT + ".getTopDeptName", deptcode);
		if (list != null && list.size() > 0) {
			return list.get(0).getString("dept_name");
		}
		return "";
	}

	/**
	 * 递归 读取顶级部门
	 * 
	 * @param dbConn
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public YZDept getTopDept(String seqId) throws Exception {

		YZDept dept = (YZDept) dao.loadObjSingleUUID(TableNameUtil.SYS_DEPT, seqId);

		if (dept != null) {
			String parent = dept.getDeptParent();
			if ("0".equals(parent)) {
				return dept;
			} else {
				return getTopDept(parent);
			}
		}
		return null;
	}

	/**
	 * 根据人员 查询顶级部门（门诊）
	 * 
	 * @param dbConn
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public YZDept getDeptParentIdByPerson(YZPerson person) throws Exception {
		return getTopDept(person.getDeptId());
	}

	/**
	 * 根据部门类型，获取部门列表
	 * 
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	public List<YZDept> getDeptListByDeptType(String dept_types, String organization) throws Exception {
		Set<String> seqIdSet = new HashSet<String>();
		String[] typeArr = dept_types.split(",");
		for (String deptType : typeArr) {
			if (YZUtility.isNullorEmpty(deptType)) {
				continue;
			}

			JSONObject json = new JSONObject();
			json.put("deptType", deptType);
			json.put("organization", organization);

			List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptListByDeptType", json);
			for (JSONObject job : list) {
				String tmpId = job.getString("seq_id");
				if (YZUtility.isNullorEmpty(tmpId)) {
					continue;
				}
				seqIdSet.add(tmpId);
			}
		}

		StringBuffer seqIdBf = new StringBuffer();
		List<String> idlist = new ArrayList<String>(seqIdSet);
		for (String seqid : idlist) {
			if (YZUtility.isNullorEmpty(seqid)) {
				continue;
			}
			seqIdBf.append(seqid).append(",");
		}

		List<YZDept> deptList = getDeptListBySeqIds(seqIdBf.toString());
		return deptList;
	}

	/**
	 * 获取当前门诊下的所有部门
	 * 
	 * @param dbConn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public List<YZDept> getAllDeptByOrganization(String organization) throws Exception {
		Map<String,String> map=new HashMap<String,String>();
		if(!YZUtility.isNullorEmpty(organization)){
			map.put("organization", organization);
		}
		List<YZDept> list = (List<YZDept>) dao.findForList(TableNameUtil.SYS_DEPT + ".getAllDeptByOrganization", map);
		return list;
	}

	/**
	 * 根据部门主键，获取部门名称
	 * 
	 * @param ids
	 * @param conn
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public String getDeptNamesBySeqIds(String ids) throws Exception, Exception {
		StringBuffer namesBf = new StringBuffer();
		// 多个字符串型id 处理
		List<String> idList = YZUtility.ConvertString2List(ids);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptNamesBySeqIds", idList);
		for (JSONObject job : list) {
			String deptName = job.getString("dept_name");
			if (YZUtility.isNullorEmpty(deptName)) {
				continue;
			}
			namesBf.append(deptName).append(",");
		}

		String namesStr = namesBf.toString();
		if (namesStr.endsWith(",")) {
			namesStr = namesStr.substring(0, namesStr.length() - 1);
		}

		return namesStr;
	}

	/**
	 * 根据用户id查询部门id
	 * 
	 * @param conn
	 * @param userSeqId
	 * @return
	 * @throws Exception
	 */
	public String getDeptSeqIdByUserSeqId(String userSeqId) throws Exception {
		YZPerson person = (YZPerson) dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, userSeqId);
		if (person != null) {
			return person.getDeptId();
		} else {
			throw new Exception("用户不存在");
		}
	}
	/**
	 * 根据门诊编号，获取当前门诊的对象
	 * 
	 * @param conn
	 * @param deptcode
	 * @return
	 * @throws Exception
	 */
	public YZDept getTopDeptByCode(String deptcode) throws Exception {
		List<YZDept> list = (List<YZDept>) dao.findForList(TableNameUtil.SYS_DEPT + ".getTopDeptByCode", deptcode);
		if (list == null || list.size() == 0) {
			return new YZDept();
		}
		return list.get(0);
	}

	/**
	 * 根据部门类型及部门名称 获取部门对象
	 * 
	 * @param conn
	 * @param deptcode
	 * @return
	 * @throws Exception
	 */
	public YZDept getDeptByTypeAndName(String deptType, String deptName) throws Exception {
		JSONObject json = new JSONObject();
		json.put("deptType", deptType);
		json.put("deptName", deptName);

		List<YZDept> list = (List<YZDept>) dao.findForList(TableNameUtil.SYS_DEPT + ".getDeptByTypeAndName", json);
		if (list == null || list.size() == 0) {
			return new YZDept();
		}
		return list.get(0);
	}

	/**
	 * 查询当前门诊编号下的最大排序号
	 * 
	 * @param conn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public String getMaxDeptNO(String organization) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.SYS_DEPT + ".getMaxDeptNO", organization);
		if (json == null) {
			return "1";
		}
		String maxno = json.getString("dept_no");
		return maxno;
	}

	/**   
	  * @Title: findMarket   
	  * @Description: TODO(查询业务部门)   
	  * @param: @param string
	  * @param: @return      
	  * @return: YZDept
	 * @throws Exception 
	  * @dateTime:2019年8月2日 上午9:17:24
	  */  
	public List<JSONObject> findMarket(String marketing) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("marketing", marketing);
		return (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT+".findMarket", map);
	}

	public List<JSONObject> selectBeanListByMap(String s) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("s", s);
		return (List<JSONObject>)dao.findForList(TableNameUtil.SYS_DEPT+".getSelectByseqId", map);
	}
	
	/**   
	  * @Title: findmarketing   
	  * @Description: TODO(查询/网电或市场)   
	  * @param: @param isyx      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月2日 上午11:07:12
	  */  
	public YZDept findmarketing(String isyx,String depttype,String shouli) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("isyx", isyx);
		map.put("shouli", shouli);
		map.put("depttype", depttype);
		 return (YZDept)dao.findForObject(TableNameUtil.SYS_DEPT+".findmarketing", map);
	}
	
	/**
	 * 根据部门类别查询部门
	 * <p>Title: findDeptByDeptType</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月29日 
	 * @param deptType
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findDeptByDeptType(Map<String,String> map) throws Exception {
		 List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT+".findDeptByDeptType", map);
		 return list;
	}
	public List<JSONObject> findAllDeptByDeptType(String organization) throws Exception {
		 List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT+".findAllDeptByDeptType", organization);
		 return list;
	}
	
	/**
	 * 
	  * @Title: findDeptNameBySeqId   
	  * @Description: TODO(根据登录人查询所在部门)   
	  * @param: @param seqId
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年10月9日 下午2:43:11
	 */
	public JSONObject findDeptNameBySeqId(String seqId) throws Exception {
		JSONObject deptName = (JSONObject) dao.findForObject(TableNameUtil.SYS_DEPT + ".findDeptNameBySeqId", seqId);
		return deptName;
	}

	/**   
	  * @Title: findDeptList   
	  * @Description: TODO(查询回访部门)   
	  * @param:       
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年11月8日 下午3:41:38
	  */  
	public List<YZDept> findDeptList(Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<YZDept>) dao.findForList(TableNameUtil.SYS_DEPT+".findDeptList", map);
	}

	/**   
	  * @param map 
	 * @Title: getListALL   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: List<YZDict>
	 * @throws Exception 
	  * @dateTime:2019年11月9日 下午7:00:08
	  */  
	public List<YZDict> getListALL(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<YZDict>) dao.findForList(TableNameUtil.SYS_DEPT+".findDeptListAll", map);
	}

	/**   
	  * @Title: getSelectOrganization   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年11月16日 上午11:00:36
	  */  
	public List<YZDept> getSelectOrganization() throws Exception {
		// TODO Auto-generated method stub
		return (List<YZDept>) dao.findForList(TableNameUtil.SYS_DEPT+".getSelectOrganization", null);
	}

	public String getDeptSeqIdByUserSeqIdAndOrganization(String askperson, String organization) throws Exception {
		Map<String,String> map= new HashMap<String,String>();
		map.put("askperson", askperson);
		map.put("organization", organization);
		JSONObject person = (JSONObject)dao.findForObject(TableNameUtil.SYS_PERSON+".getDeptSeqIdByUserSeqIdAndOrganization", map);
		return person.getString("seq_id");
	}
}
