package com.kqds.service.sys.system;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZMenu;
import com.kqds.entity.sys.YZMenuCompartor;
import com.kqds.entity.sys.YZMenuModel;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.func.YZFuncLogic;
import com.kqds.service.sys.menu.YZMenuLogic;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@Service
public class YZSystemLogic extends BaseLogic {

	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private YZOnlineLogic onLineLogic;
	@Autowired
	private YZMenuLogic menuLogic;
	@Autowired
	private YZFuncLogic funcLogic;

	@Autowired
	private DaoSupport dao;

	public List<YZMenuModel> lazyLoadJsonModel4zTree(List<String> menuSet, List<YZMenuModel> menulist) throws Exception {
		for (int i = 0; i < menulist.size(); i++) {
			YZMenuModel currmenu = menulist.get(i); // 当前菜单
			// 获取当前菜单的子菜单列表
			List<YZMenuModel> childmlist = getMenuList(menuSet, currmenu);

			// 设置 parentid
			for (YZMenuModel yhMenuModel : childmlist) {
				yhMenuModel.setParentid(currmenu.getMenuid());
			}

			menulist.addAll(childmlist);

			if (childmlist != null && childmlist.size() > 0) { // 判断是否有此菜单权限
				this.lazyLoadJsonModel4zTree(menuSet, childmlist);
			}
		}
		return menulist;
	}

	/**
	 * 获取sys_menu表的数据
	 * 
	 * @param dbConn
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public List<YZMenuModel> getMainMenu(YZPerson person, List<String> funclist) throws Exception {
		List<YZMenuModel> menulist = new ArrayList<YZMenuModel>();
		for (String id : funclist) {
			if (id.length() == 2) {
				YZMenu menu = menuLogic.queryMenu(id); // 查询 sys_menu表
				if (menu == null) {
					continue;
				}
				YZMenuModel medel = new YZMenuModel();
				medel.setSeqId(menu.getSeqId());
				medel.setMenuid(menu.getMenuId());
				medel.setMenuname(menu.getMenuName());
				medel.setIcon(menu.getImage());
				String menuStr = funclist.toString();
				if (!menuStr.matches(".*[ ,\\[]" + id + "\\d+.*")) {
					// 没有下级菜单的情况,设置菜单为叶子节点
					medel.setLeaf(0);
				} else {
					medel.setLeaf(1);
				}
				menulist.add(medel);
			}
		}
		return menulist;
	}

	/**
	 * 生成菜单json数据的递归函数
	 * 
	 * @param menuSet
	 *            存放 该用户的权限菜单list，该权限菜单满足树形菜单的数据格式要求
	 * @param dbConn
	 * @param parent
	 * @param contextPath
	 * @return
	 * @throws Exception
	 */
	public List<YZMenuModel> lazyLoadJsonModel(List<String> funclist, List<YZMenuModel> menulist) throws Exception {
		for (int i = 0; i < menulist.size(); i++) {
			YZMenuModel currmenu = menulist.get(i); // 当前一级菜单
			List<YZMenuModel> childmlist = getMenuList(funclist, currmenu);// 获取当前一级菜单下的二级菜单
			currmenu.setHasmenu(childmlist); // 赋值
			if (currmenu.getHasmenu() != null && currmenu.getHasmenu().size() > 0) { // 判断是否有二级菜单
				this.lazyLoadJsonModel(funclist, currmenu.getHasmenu()); // 递归，二级菜单获取三级菜单，三级菜单获取四级菜单...
			}
		}
		return menulist;
	}

	/**
	 * 该方法作用是获取一级菜单下的所有二级菜单 model 为传入的一级菜单对象
	 * 
	 * @param menuSet
	 * @param model
	 * @param dbConn
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public List<YZMenuModel> getMenuList(List<String> funclist, YZMenuModel model) throws NumberFormatException, Exception {
		// set 转成 string
		String funcStr = funclist.toString();
		// 获取menuid
		String parent = model.getMenuid();
		List<YZMenuModel> clist = new ArrayList<YZMenuModel>();

		for (String id : funclist) {
			// 如果不是二级菜单，则跳过
			if (!id.matches(parent + "\\d{2}")) {
				continue;
			}

			// 如果是二级菜单，即menuid的值为4位数
			YZMenuModel menu = funcLogic.queryFuncMoel(id);
			// 菜单不存在，则跳过
			if (menu == null) {
				continue;
			}

			// 判断是否有下级节点
			if (!funcStr.matches(".*[ ,\\[]" + menu.getMenuid() + "\\d+.*")) {
				// 没有则设置菜单为叶子节点
				menu.setLeaf(0);
			} else {
				menu.setLeaf(1);
			}

			clist.add(menu);
		}

		// 根据排序号 进行升序排序
		YZMenuCompartor mcompare = new YZMenuCompartor();
		Collections.sort(clist, mcompare); // 升序

		return clist;
	}

	/**
	 * 返回所有用户有权限的菜单List 其中不包括用户辅助角色有权限的菜单
	 * 
	 * @param dbConn
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public List<String> listUserMenu(YZPerson person) throws Exception {
		List<YZPriv> priv = new ArrayList<YZPriv>();
		String userPriv = "";
		if (person != null) {
			userPriv = person.getUserPriv();
		}

		if (userPriv == null || "".equals(userPriv.trim())) {
			userPriv = "";
		}

		// 获取该用户的主角色
		YZPriv up = (YZPriv) dao.loadObjSingleUUID(TableNameUtil.SYS_PRIV, userPriv.trim());

		if (up != null) {
			priv.add(up);
		}

		// 用户主角色菜单去除重复
		// func_id_str为user_priv表的字段，存放角色对应的权限菜单，多个菜单以逗号分隔
		HashSet<String> menuSet = new HashSet<String>();

		for (YZPriv p : priv) {
			menuSet.addAll(Arrays.asList(YZUtility.null2Empty(p.getFuncIdStr()).split(",")));
		}

		HashSet<String> addSet = new HashSet<String>(menuSet);
		// 这个方法的作用是，让所存储的菜单有级联关系
		// 比如，如果管理员这个角色对应的权限菜单只有一个，值为 602010，那么这个方法的作用是让
		// addset存的值由602010变为60,6020,602010
		// 这样的目的是为是前台可以作为树形菜单进行展现 注释by ys 2017-3-2
		for (String s : menuSet) {
			s = s.trim();
			if (s.length() > 4) {
				addSet.add(s.substring(0, 4));
			} else if (s.length() > 2) {
				addSet.add(s.substring(0, 2));
			}
		}

		List<String> list = new ArrayList<String>(addSet);
		// 根据菜单的排序字段[即menu_id]把菜单进行升序排序
		Collections.sort(list, new Comparator<String>() {
			public int compare(String arg0, String arg1) {
				if (YZUtility.isNullorEmpty(arg0)) {
					arg0 = "";
				}
				if (YZUtility.isNullorEmpty(arg1)) {
					arg1 = "";
				}
				return arg0.compareTo(arg1);
			}
		});

		return list;
	}

	/**
	 * 登录成功的处理
	 * 
	 * @param conn
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void loginSuccess(YZPerson person, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 获取用户当前的session,如果不存在就生成一个新的session
		HttpSession session = request.getSession(true);

		// 用户登录成功后，在session中设置门诊相关信息
		SessionUtil.setHosInfo4UserLogin(request, session, person);

		// 判断用户是否已经登录
		if (session.getAttribute("LOGIN_USER") == null) {
			// 添加登录成功的系统日志
			SysLogUtil.logNoRequest(SysLogUtil.LOGIN, SysLogUtil.SYS_PERSON, "登录成功", TableNameUtil.SYS_PERSON, person, YZUtility.getIpAddr(request), session);

			// 在session中设置用户信息
			SessionUtil.setUserInfo4UserLogin(person, session, YZUtility.getIpAddr(request), request);

			// 将用户在线数据，存到session中
			onLineLogic.addOnline(person, String.valueOf(session.getAttribute(SessionUtil.SESSION_TOKEN)), session, request);

			// 存入全局在线用户 Map
			SessionUtil.Session_MAP.put(person.getUserId(), session);

			// 更新登录时间
			person.setLastVisitTime(new Date());
			dao.updateSingleUUID(TableNameUtil.SYS_PERSON, person);

			// 可见人员设置、预约可见人员设置
			visualStaffSet(person, session);

			// 将系统配置放到session中
			SessionUtil.setSysPara4UserLogin(request);

			// 付款方式放到session中
			SessionUtil.getCostField(request);
			SessionUtil.getMemberField(request);

		} else {
			YZPerson loginPerson = (YZPerson) session.getAttribute("LOGIN_USER");
			// 如果是新用户登录时,销毁原有的session
			if (loginPerson.getSeqId() != person.getSeqId()) {
				SessionUtil.invalidateSession(session);// 销毁session
				// 重新调用登录成功的处理
				loginSuccess(person, request, response);
			}
		}
	}

	/**
	 * 可见人员设置
	 * 
	 * @param dbConn
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void visualStaffSet(YZPerson person, HttpSession session) throws Exception {

		YZPriv privObj = (YZPriv) dao.loadObjSingleUUID(TableNameUtil.SYS_PRIV, person.getUserPriv());

		if (privObj == null) {
			throw new Exception("该用户没有配置角色");
		}

		// 当前登录人的用户角色
		session.setAttribute(ConstUtil.LOGIN_USER_PRIV, privObj);

		// 预约可见人员
		session.setAttribute(SessionUtil.visualstaffYyrl, getVisualstaffYyrl(privObj, person));

		// 可见人员
		session.setAttribute(SessionUtil.visualstaff, getVisualstaff(privObj, person));
	}

	/**
	 * 获取预约可见人员
	 * 
	 * @param privObj
	 * @param person
	 * @param dbConn
	 * @return
	 * @throws SQLException
	 */
	public String getVisualstaffYyrl(YZPriv privObj, YZPerson person) throws Exception {

		// 预约可见人员
		String orderVisualPersons = privObj.getOrderVisualPerson();
		String orderVisualDepts = privObj.getOrderVisualDept();

		Set<String> orderPersonIdSet = new HashSet<String>();
		if (person != null) {
			orderPersonIdSet.add(person.getSeqId()); // 1、先把自己加进去
		}

		if (!YZUtility.isNullorEmpty(orderVisualPersons)) {
			String[] pIdArray = orderVisualPersons.split(",");// 根据逗号进行分割
			for (String personId : pIdArray) {// 循环
				if (!YZUtility.isNullorEmpty(personId)) {// 如果部门ID有值
					orderPersonIdSet.add(personId);
				}
			}
		}

		if (!YZUtility.isNullorEmpty(orderVisualDepts)) {
			List<JSONObject> plist = personLogic.getPersonIdListByDeptIds(orderVisualDepts);
			for (JSONObject p : plist) {
				orderPersonIdSet.add(p.getString("seq_id"));
			}
		}

		// set 转字符串
		Iterator<String> orderIt = orderPersonIdSet.iterator();
		StringBuffer orderVisualPersonIdBf = new StringBuffer();
		while (orderIt.hasNext()) {
			String pid = orderIt.next();
			orderVisualPersonIdBf.append(pid).append(",");
		}

		if (orderVisualPersonIdBf.length() > 0) {
			orderVisualPersonIdBf.deleteCharAt(orderVisualPersonIdBf.length() - 1);
		}
		// 格式转换，具体进入转换方法查看
		String orderVisualPersonIds4Query = YZUtility.ConvertStringIds4Query(orderVisualPersonIdBf.toString());

		return orderVisualPersonIds4Query;

	}

	/**
	 * 获取可见人员
	 * 
	 * @param privObj
	 * @param person
	 * @param dbConn
	 * @return
	 * @throws SQLException
	 */
	public String getVisualstaff(YZPriv privObj, YZPerson person) throws Exception {

		// 可见人员
		String visualPersons = privObj.getVisualPerson();
		String visualDepts = privObj.getVisualDept();

		Set<String> personIdSet = new HashSet<String>();
		if (person != null) {
			personIdSet.add(person.getSeqId()); // 1、先把自己加进去
		}

		if (!YZUtility.isNullorEmpty(visualPersons)) {
			String[] pIdArray = visualPersons.split(",");// 根据逗号进行分割
			for (String personId : pIdArray) {// 循环
				if (!YZUtility.isNullorEmpty(personId)) {// 如果部门ID有值
					personIdSet.add(personId);
				}
			}
		}

		if (!YZUtility.isNullorEmpty(visualDepts)) {
			List<JSONObject> plist = personLogic.getPersonIdListByDeptIds(visualDepts);
			for (JSONObject p : plist) {
				personIdSet.add(p.getString("seq_id"));
			}
		}

		// set 转字符串
		Iterator<String> it = personIdSet.iterator();
		StringBuffer visualPersonIdBf = new StringBuffer();
		while (it.hasNext()) {
			String pid = it.next();
			visualPersonIdBf.append(pid).append(",");
		}

		if (visualPersonIdBf.length() > 0) {
			visualPersonIdBf.deleteCharAt(visualPersonIdBf.length() - 1);
		}
		// 格式转换，具体进入转换方法查看
		String visualPersonIds4Query = YZUtility.ConvertStringIds4Query(visualPersonIdBf.toString());

		return visualPersonIds4Query;

	}

}
