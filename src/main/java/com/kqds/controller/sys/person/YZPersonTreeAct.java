package com.kqds.controller.sys.person;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.sys.YZDept;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("YZPersonTreeAct")
public class YZPersonTreeAct {

	private Logger logger = LoggerFactory.getLogger(YZPersonAct.class);

	@Autowired
	private YZPersonLogic personLogic;

	@Autowired
	private YZDeptLogic deptLogic;

	/**
	 * 获取部门列表，用于可见人员树的展示
	 */
	private List<String> getDeptIdList(String deptId, List<String> list) throws Exception {
		YZDept dept = (YZDept) personLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, deptId);

		if (dept != null) {
			String did = dept.getSeqId();
			String pid = dept.getDeptParent();

			boolean flag = true;
			for (String tmpId : list) {
				if (tmpId.equals(did)) {
					flag = false;
				}
			}

			if (flag) {
				list.add(did);
			}

			if (!"0".equals(pid)) {
				getDeptIdList(pid, list);
			}
		}

		return list;
	}

	/**
	 * 一次性查出所有部门、人员树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPersonTree4All.act")
	public String getPersonTree4All(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String personIds = request.getParameter("personIds"); // 传参已被选中的人员
		String isFilterByVisualStaff = request.getParameter("isFilterByVisualStaff"); // 如果传值，表示需要过滤

		HttpSession session = request.getSession(false); // 不存在就报错
		List<JSONObject> treeList = new ArrayList<JSONObject>();

		try {

			// 根据可见人员进行条件过滤
			if (!YZUtility.isNullorEmpty(isFilterByVisualStaff)) { // --------------------------------通过可见人员进行过滤
				String visualstaff = SessionUtil.getVisualstaff(request);
				if (!visualstaff.startsWith(",")) {
					visualstaff = "," + visualstaff;
				}
				if (!visualstaff.endsWith(",")) {
					visualstaff = visualstaff + ",";
				}
				// 可见人员查询条件
				List<JSONObject> personList = personLogic.getListByVisualstaff(visualstaff);

				List<String> deptIdList = new ArrayList<String>();
				for (int i = 0; i < personList.size(); i++) {
					JSONObject pjson = personList.get(i);
					String seq_id = pjson.getString("seq_id");
					String user_id = pjson.getString("user_id");
					String dept_id = pjson.getString("dept_id");

					getDeptIdList(dept_id, deptIdList); // 获取部门ID列表

					JSONObject nodeP = new JSONObject();
					nodeP.put("id", "person" + seq_id);
					nodeP.put("pId", dept_id);
					nodeP.put("name", user_id); // 这样做的目的是，同名被选中时，树形菜单反过来可以准确赋值
												// + "[" + p.getUserId() +
												// "]"
					nodeP.put("isParent", false);
					nodeP.put("iconSkin", "person");
					nodeP.put("nocheck", false);
					nodeP.put("personseqid", seq_id); // 用于和可见人员做比较，显示人员树时，进行条件过滤
					boolean isCheck = YZUtility.isStrInArrayEach(seq_id, personIds);
					nodeP.put("checked", isCheck);
					treeList.add(nodeP);
				}

				StringBuffer deptBf = new StringBuffer();
				for (String deptId : deptIdList) {
					deptBf.append(deptId).append(",");
				}

				List<YZDept> deptList = deptLogic.getDeptListBySeqIds(deptBf.toString());

				for (YZDept dept : deptList) {
					JSONObject node = new JSONObject();
					node.put("id", dept.getSeqId());
					if ("0".equals(dept.getDeptParent())) {
						node.put("pId", "orgId");
					} else {
						node.put("pId", dept.getDeptParent());
					}
					node.put("name", dept.getDeptName());
					node.put("isParent", true);
					node.put("nocheck", false);
					treeList.add(node);
				}

			} else { // -------------------------------------------------------------------------------不通过可见人员进行过滤
				List<JSONObject> list = (List<JSONObject>) session.getAttribute("PERSON_TREE_DATA");
				if (list == null) {
					treeList = personLogic.getDeptNodeList("0", treeList, personIds);
				} else {
					treeList = list;
				}
			}

			for (JSONObject job : treeList) {
				String id = job.getString("id");
				if (id.startsWith("person")) {
					String tmpId = id.replace("person", "");
					boolean check = YZUtility.isStrInArrayEach(tmpId, personIds);
					job.put("checked", check);
				}
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, treeList);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}
