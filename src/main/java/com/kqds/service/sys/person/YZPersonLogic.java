package com.kqds.service.sys.person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service
public class YZPersonLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	@Autowired
	private YZDeptLogic deptLogic;

	/**
	 * 根据可见人员获取List
	 * 
	 * @param visualstaffQuery
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getListByVisualstaff(String visualstaffQuery) throws Exception {
		List<String> list = YZUtility.ConvertString2List(visualstaffQuery);
		List<JSONObject> plist = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getListByVisualstaff", list);
		return plist;
	}

	// 根据deptId 查询 最大排序号
	public String getMaxOrderno(String deptId) throws Exception {
		String result = (String) dao.findForObject(TableNameUtil.SYS_PERSON + ".getMaxOrderno", deptId);
		return result == null ? "0" : result;
	}

	/**
	 * 通过USER_ID和BYNAME查询用户(使用别名登录)
	 * 
	 * @param conn
	 * @param userName
	 * @return
	 * @throws Exception
	 */

	public YZPerson queryPerson(String userName, HttpServletRequest request) throws Exception {

		try {
			YZPerson person = null;
			if (ChainUtil.isOpenChain()) { // 连锁功能开启的情况下，通过用户姓名进行查询，这样做的目的是假设一个医生分别在三家医院有3个user_id，那么通过此处理，可以用姓名登录，不需要记住3个user_id
				String organization = ChainUtil.getOrganizationFromUrl(request);
				JSONObject json = new JSONObject();
				json.put("USER_NAME", userName);
				json.put("DEPT_CODE", organization);

				List<YZPerson> list = (List<YZPerson>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonByUserNameAndOrg", json);

				if (list != null && list.size() == 1) { // 只有当前门诊的用户姓名不存在重复的情况下，才直接返回；否则会有问题。
					person = list.get(0);
					return person; // 则直接返回，程序终止
				}
			}

			// 否则，仍然用用户ID进行匹配
			Map<String, String> filter = new HashMap<String, String>();
			filter.put("USER_ID", userName);
			List<YZPerson> list2 = (List<YZPerson>) dao.loadList(TableNameUtil.SYS_PERSON, filter);
			if (list2.size() > 0) {
				person = list2.get(0);
			}

			return person;
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * 根据部门类型获取用户seqIds，不做门诊编号过滤
	 * 
	 * @param conn
	 * @param organization
	 * @param deptType
	 * @return
	 * @throws Exception
	 */
	public String getPerIdsByDeptTypeNoOrg(String deptType) throws Exception {
		String ids = "";
		List<String> list = (List<String>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPerIdsByDeptTypeNoOrg", deptType);
		for (String seq_id : list) {
			ids += seq_id + ",";
		}
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}

		return ids;
	}

	/**
	 * 根据部门类型和可见人员进行过滤
	 * 
	 * @param conn
	 * @param deptType
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	public String getPerIdsByDeptTypeAndVisualNoOrg(String deptType, String visualstaff) throws Exception {
		StringBuffer sql = new StringBuffer(" select p.seq_id from " + TableNameUtil.SYS_PERSON + " p ");
		sql.append(" left join " + TableNameUtil.SYS_DEPT + " d on p.dept_id = d.seq_id ");
		sql.append(" where 1=1 ");

		if (!YZUtility.isNullorEmpty(deptType)) {
			sql.append(" and d.DEPT_TYPE like '%" + deptType + "%' ");
		}

		sql.append(" and p.seq_id in (" + visualstaff + ") ");

		List<String> visualstaffList = YZUtility.ConvertString2List(visualstaff);

		JSONObject json = new JSONObject();
		json.put("deptType", deptType);
		json.put("visualstaffList", visualstaffList);

		String ids = "";
		List<String> list = (List<String>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPerIdsByDeptTypeAndVisualNoOrg", json);
		for (String seq_id : list) {
			ids += seq_id + ",";
		}
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}

		return YZUtility.ConvertStringIds4Query(ids);
	}

	/**
	 * 根据系统用户列表，获取系统用户的主键
	 * 
	 * @param conn
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String getPerIdsByPersonList(List<JSONObject> list) throws Exception {
		String ids = "";
		for (JSONObject rs : list) {
			ids += rs.getString("seq_id") + ",";
		}
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}

		return YZUtility.ConvertStringIds4Query(ids);
	}

	/**
	 * 根据部门ID获取人员列表【用于挂号页面的就诊科室人员选择】【离职的不展示】
	 * 
	 * @param dbConn
	 * @param deptIds
	 * @return
	 * @throws Exception
	 */

	public List<YZPerson> getPersonListByDeptIds(String deptIds, String showleave, String organization) throws Exception {
		List<String> list = YZUtility.ConvertString2List(deptIds);
		JSONObject json = new JSONObject();
		json.put("idlist", list);
		json.put("showleave", showleave);
		json.put("organization", organization);
		List<YZPerson> plist = (List<YZPerson>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonListByDeptIds", json);
		return plist;
	}

	/**
	 * 根据部门SEQ_ID获取人员SEQ_ID
	 * 
	 * @param dbConn
	 * @param deptIds
	 * @return
	 * @throws SQLException
	 */
	public List<JSONObject> getPersonIdListByDeptIds(String deptIds) throws Exception {
		List<String> list = YZUtility.ConvertString2List(deptIds);
		List<JSONObject> plist = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonIdListByDeptIds", list);
		return plist;
	}

	/**
	 * 清空密码
	 * 
	 * @param conn
	 * @param seqIdStrs
	 * @return
	 * @throws Exception
	 */
	public int clearPassword(String seqIdStrs, HttpServletRequest request) throws Exception {
		List<String> listId = YZUtility.ConvertString2List(seqIdStrs);
		JSONObject json = new JSONObject();
		json.put("listId", listId);
		json.put("password", YZPassEncrypt.encryptPass(ConstUtil.EMPTY));

		int count = (int) dao.update(TableNameUtil.SYS_PERSON + ".clearPassword", json);

		// 记录日志
		SysLogUtil.log(SysLogUtil.EMPTY_PASSWORD, SysLogUtil.SYS_PERSON, seqIdStrs, TableNameUtil.SYS_PERSON, request);
		return count;
	}

	/**
	 * 删除系统用户
	 * 
	 * @param conn
	 * @param seqids
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteBySeqIds(String seqids, HttpServletRequest request) throws Exception {
		List<String> listId = YZUtility.ConvertString2List(seqids);
		int count = (int) dao.delete(TableNameUtil.SYS_PERSON + ".deleteBySeqIds", listId);
		// 记录日志
		SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_PERSON, seqids, TableNameUtil.SYS_PERSON, request);
		return count;
	}

	/**
	 * 取得当前部门的所有用户(不对禁止登录的用户进行控制)
	 * 
	 * @param conn
	 * @param deptId
	 * @return
	 * @throws Exception
	 */

	public List<YZPerson> getUserListByDeptSeqId(String deptId) throws Exception {
		JSONObject json = new JSONObject();
		json.put("deptId", deptId);
		json.put("findInSetDeptIdOther", SQLUtil.findInSet(String.valueOf(deptId), "DEPT_ID_OTHER"));
		List<YZPerson> persons = (List<YZPerson>) dao.findForList(TableNameUtil.SYS_PERSON + ".getUserListByDeptSeqId", json);
		return persons;
	}

	/**
	 * 根据可见人员过滤树
	 * 
	 * @param conn
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public int countByUserDocument(Map<String, String> map, String visualstaff) throws Exception {
		map.put("visualstaff", visualstaff);
		int count = (int) dao.findForObject(TableNameUtil.SYS_PERSON + ".countByUserDocument", map);
		return count;
	}

	/**
	 * 根据部门ID，查询该部门下的人员数量
	 * 
	 * @param conn
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public int countByDeptId(String deptId, String username) throws Exception {
		List<String> deptIdList = YZUtility.ConvertString2List(deptId);
		JSONObject json = new JSONObject();
		json.put("deptIdList", deptIdList);
		json.put("username", username);

		int count = (int) dao.findForObject(TableNameUtil.SYS_PERSON + ".countByDeptId", json);
		return count;
	}

	/**
	 * 根据用户名统计数量
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int countByUserId(String userId) throws Exception {
		int num = (int) dao.findForObject(TableNameUtil.SYS_PERSON + ".countByUserId", userId);
		return num;
	}

	/**
	 * 统计同一个门诊下，某个姓名出现的次数
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int countByUserNameCurrHos(String userName, String deptId, String personSeqId) throws Exception {
		JSONObject json = new JSONObject();
		json.put("userName", userName);
		json.put("personSeqId", personSeqId);
		json.put("deptId", deptId);
		int num = (int) dao.findForObject(TableNameUtil.SYS_PERSON + ".countByUserNameCurrHos", json);
		return num;
	}

	/**
	 * 根据部门ID进行分页查询
	 * 
	 * @param conn
	 * @param bp
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public JSONObject selectPage(BootStrapPage bp, String deptId, String username) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(deptId);
		JSONObject json = new JSONObject();
		json.put("idList", idList);
		json.put("username", username);
		json.put("search", bp.getSearch());

		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".selectPage", json);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		for (JSONObject p : list) {
			YZDept d = deptLogic.getTopDept(p.getString("dept_id"));
			p.put("organizationname", d.getDeptName()); // 所属门诊
			String pass = p.getString("password");
			if (YZPassEncrypt.isValidPas("", pass)) {
				p.put("isEmptyPass", "1");
			} else {
				p.put("isEmptyPass", "0");
			}
		}
		pageInfo.setList(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 不分页
	 * 
	 * @param conn
	 * @param deptId
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> selectNoPage(String deptId, String seqId, String showleave) throws Exception {
		JSONObject json = new JSONObject();
		json.put("showleave", showleave);
		json.put("seqId", seqId);
		json.put("deptId", deptId);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".selectNoPage", json);
		return list;
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
		List<YZDept> deptList = deptLogic.getSubDeptListBySeqId(parentId, null);
		for (YZDept dept : deptList) {
			boolean haveChild = deptLogic.IsHaveChildPerson(dept.getSeqId());
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

			List<YZPerson> persons = getUserListByDeptSeqId(dept.getSeqId());
			for (YZPerson p : persons) {
				JSONObject nodeP = new JSONObject();
				nodeP.put("id", "person" + p.getSeqId());
				nodeP.put("pId", dept.getSeqId());
				nodeP.put("name", p.getUserId()); // 这样做的目的是，同名被选中时，树形菜单反过来可以准确赋值
													// + "[" + p.getUserId() +
													// "]"
				nodeP.put("isParent", false);
				nodeP.put("iconSkin", "person");
				nodeP.put("nocheck", false);
				nodeP.put("personseqid", p.getSeqId()); // 用于和可见人员做比较，显示人员树时，进行条件过滤
				boolean isCheck = YZUtility.isStrInArrayEach(p.getSeqId(), personIds);
				nodeP.put("checked", isCheck);
				treeList.add(nodeP);
			}

			haveChild = deptLogic.IsHaveChild(dept.getSeqId());
			if (haveChild) {
				getDeptNodeList(dept.getSeqId(), treeList, personIds);
			}
		}
		return treeList;
	}

	public List<JSONObject> getPersonListByDeptTypeAndVisual(String depttype, String visualstaff, String organization) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(visualstaff);
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("depttype", depttype);
		json.put("idList", idList);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonListByDeptTypeAndVisual", json);
		return list;
	}

	public List<JSONObject> getPersonListByDeptType(String depttype, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("depttype", depttype);

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonListByDeptType", json);
		return list;
	}
	public List<JSONObject> getPersonListByDeptType1(String depttype, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("depttype", depttype);

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonListByDeptType1", json);
		return list;
	}

	public String getPersonsByDeptType(String depttype, String organization) throws Exception {
		String seqids = "";
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("depttype", depttype);
		List<String> list = (List<String>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonsByDeptType", json);
		for (String seqId : list) {
			seqids += seqId + ",";
		}
		return seqids;
	}

	public List<YZPerson> getPersonListByRole(String roleId, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("roleId", roleId);

		List<YZPerson> list = (List<YZPerson>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonListByRole", json);
		return list;
	}

	public List<YZPerson> getPersonListByRolesNoOrg(String rolesId) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(rolesId);
		List<YZPerson> list = (List<YZPerson>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonListByRolesNoOrg", idList);
		return list;
	}

	/**
	 * 根据角色和可见人员进行查询
	 * 
	 * @param roleId
	 * @param conn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getPersonListByRoleAndVisual(String roleId, String visualStaff, String organization) throws Exception {
		List<String> roleIdList = YZUtility.ConvertString2List(roleId);
		List<String> visualIdList = YZUtility.ConvertString2List(visualStaff);

		JSONObject json = new JSONObject();
		json.put("roleIdList", roleIdList);
		json.put("visualIdList", visualIdList);
		json.put("organization", organization);

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonListByRoleAndVisual", json);
		return list;
	}

	/**
	 * 根据seqid串返回一个名字串
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public boolean yzUserBySeqIds(String ids, String organization) throws Exception, Exception {
		boolean flag = true;
		List<JSONObject> list = getPersonOrgListBySeqIds(ids);
		for (JSONObject job : list) {
			String dept_code = job.getString("dept_code");
			if (!dept_code.equals(organization)) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 根据seqid串返回一个名字串
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public String getNameStrBySeqIds(String ids) throws Exception, Exception {
		String namesStr = "";
		StringBuffer namesBf = new StringBuffer();
		List<JSONObject> list = getPersonListBySeqIds(ids);
		for (JSONObject job : list) {
			String uname = job.getString("user_name");
			if (YZUtility.isNullorEmpty(uname)) {
				continue;
			}
			namesBf.append(uname).append(",");
		}
		namesStr = namesBf.toString();
		if (namesStr.endsWith(",")) {
			namesStr = namesStr.substring(0, namesStr.length() - 1);
		}
		return namesStr;
	}

	public List<JSONObject> getPersonListBySeqIds(String ids) throws Exception, Exception {
		List<String> idList = YZUtility.ConvertString2List(ids);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonListBySeqIds", idList);
		return list;
	}

	public List<JSONObject> getPersonOrgListBySeqIds(String ids) throws Exception, Exception {
		List<String> idList = YZUtility.ConvertString2List(ids);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonOrgListBySeqIds", idList);
		return list;
	}

	public List<JSONObject> getPersonListBySeqIds4Online(String ids, String organization) throws Exception, Exception {
		List<String> idList = YZUtility.ConvertString2List(ids);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonListBySeqIds4Online", idList);
		return list;
	}

	public YZPerson getPersonByUserId(String userId) throws Exception {
		List<YZPerson> plist = (List<YZPerson>) dao.findForList(TableNameUtil.SYS_PERSON + ".getPersonByUserId", userId);
		if (plist != null && plist.size() > 0) {
			return plist.get(0);
		}
		return null;
	}

	public YZPerson getPersonBySeqId(String seqId) throws Exception {
		YZPerson person = (YZPerson) dao.loadObjSingleUUID(TableNameUtil.SYS_PERSON, seqId);
		return person;
	}

	/**
	 * 排班的人员展示 和 模板导出 共用 该方法抽取出来共用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getPersonList(HttpServletRequest request, String deptId, String seqId) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>(); // 人员列表集合
		Map<String, String> map = new HashMap<String, String>();
		String organzation = ChainUtil.getCurrentOrganization(request);
		if(YZUtility.isNotNullOrEmpty(organzation)) {//按门诊区分人员
			map.put("organzation", organzation);
		}

		String visualstaff = SessionUtil.getVisualstaff(request);
		if (YZUtility.isNullorEmpty(deptId) && (YZUtility.isNullorEmpty(seqId) || seqId.equals("null"))) {
			map.put("visualstaff", visualstaff);
		}

		if (YZUtility.isNullorEmpty(deptId)) { // 部门ID为空的情况下，人员ID肯定也为空

			// 查询部门树List 此方法和 后台的 部门管理调用同一个方法
//			String deptTreeData = deptLogic.getSelectDeptTree("0", ChainUtil.getCurrentOrganization(request));
			String deptTreeData = deptLogic.getSelectDeptTreePaiban("0", ChainUtil.getCurrentOrganization(request));//行政中心排班专用初始化查询  syp 2019-12-03
			net.sf.json.JSONArray deptTreeOArray = net.sf.json.JSONArray.fromObject(deptTreeData);

			for (Object object : deptTreeOArray) {
				JSONObject dept = (JSONObject) object;
				String tmpDeptId = dept.get("value").toString();

				if (YZUtility.isNullorEmpty(tmpDeptId)) {
					continue;
				}
				map.put("dept_id", tmpDeptId);

				List<JSONObject> tmpList = selectPersonPaiban(map);
				list.addAll(tmpList);
			}

		} else {
			map.put("dept_id", deptId); // 部门ID为空 人员ID肯定也没值

			if (!YZUtility.isNullorEmpty(seqId) && !seqId.equals("null")) {
				map.put("seq_id", seqId);
			}

			list = selectPersonPaiban(map);
		}

		return list;
	}

	/**
	 * 根据部门ID，获取人员列表 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param map
	 * @param deptTreeOArray
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<JSONObject> selectPersonPaiban(Map map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".selectPersonPaiban", map);
		return list;
	}

	/**
	 * 根据部门类型，进行可见人员过滤
	 * 
	 * @param deptType
	 * @param visualPersonStr
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public String filterVisualPersons(String deptType, String visualPersonStr, String otherpriv) throws Exception {
		List<String> privList = YZUtility.ConvertString2List(otherpriv);
		List<String> visualList = YZUtility.ConvertString2List(visualPersonStr);

		JSONObject json = new JSONObject();
		json.put("deptType", deptType);
		json.put("privList", privList);
		json.put("visualList", visualList);
		List<JSONObject> plist = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".filterVisualPersons", json);

		StringBuffer pIdBf = new StringBuffer();
		for (int i = 0; i < plist.size(); i++) {
			String pid = plist.get(i).getString("seq_id");
			if (YZUtility.isNullorEmpty(pid)) {
				continue;
			}
			pIdBf.append(pid).append(",");
		}
		if (pIdBf.length() > 0) {
			pIdBf.deleteCharAt(pIdBf.length() - 1);
		}

		// 格式转换，具体进入转换方法查看
		String queryIdsTmp = YZUtility.ConvertStringIds4Query(pIdBf.toString());

		return queryIdsTmp;
	}

	/**
	 * 查询所有收费人员
	 * 
	 * @param conn
	 * @param organization
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getAllShowfeiPerson(String organization, HttpServletRequest request) throws Exception {
		String sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID); // 获取收费角色
		List<String> privList = YZUtility.ConvertString2List(sfpriv);

		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("privList", privList);

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getAllShowfeiPerson", json);
		return list;
	}

	/**
	 * 查询所有总经理
	 * 
	 * @param conn
	 * @param organization
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getAllShowZjlPerson(String organization, HttpServletRequest request) throws Exception {
		String zjlpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_ZONGJINGLI_SEQID);
		List<String> privList = YZUtility.ConvertString2List(zjlpriv);

		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("privList", privList);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_PERSON + ".getAllShowZjlPerson", json);
		return list;
	}

	/**   
	  * @Title: queryFirst   
	  * @Description: TODO(确认是否为首次登录)   
	  * @param: @param userName      
	  * @return: void
	 * @throws Exception 
	  */  
	public QueryFirstLog queryFirstLog(String userName) throws Exception {
		// TODO Auto-generated method stub
		QueryFirstLog query = (QueryFirstLog)dao.findForObject(TableNameUtil.SYS_FIRSTLOG + ".queryFirstLog", userName);
		return query;
	}
	/**
	  * @Title: saveQueryLog   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param person
	  * @param: @throws Exception      
	  * @return: void
	 */
	public void saveQueryLog(QueryFirstLog queryFirstLog) throws Exception{
		 dao.save(TableNameUtil.SYS_FIRSTLOG + ".saveQueryLog", queryFirstLog);
	}

	/**   
	  * @Title: updatePwd   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param yzPerson      
	  * @return: void
	 * @throws Exception 
	  */  
	public void updatePwd(YZPerson yzPerson) throws Exception {
		// TODO Auto-generated method stub
		dao.update(TableNameUtil.SYS_FIRSTLOG + ".updatePwd", yzPerson);
	}

	/**   
	  * @Title: findVisualPersonnel   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param deptId
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年8月5日 下午5:45:01
	  */  
	public List<JSONObject> findVisualPersonnel(String deptId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String,String>();
		map.put("deptId", deptId);
		return (List<JSONObject>)dao.findForList(TableNameUtil.SYS_PERSON+".findVisualPersonnel", map);
	}
	
	public String findPersonIsleaveBySeqId(String seqId) throws Exception {
		return (String)dao.findForObject(TableNameUtil.SYS_PERSON+".findPersonIsleaveBySeqId", seqId);
	}
	/**   
	  * @Title: findPersonalDetails   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param userId      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年8月6日 下午3:06:05
	  */  
	public List<JSONObject> findPersonalDetails(String userId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("userId", userId);
		return (List<JSONObject>)dao.findForList(TableNameUtil.SYS_PERSON+".findPersonalDetails", map);	
	}
	/**
	 * 根据人员id修改门诊
	 * <p>Title: updateOrganization</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年12月7日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateOrganization(Map<String,String> map) throws Exception {
		int i=(int) dao.update(TableNameUtil.SYS_PERSON+".updateOrganization", map);
		return i;
	}
}
