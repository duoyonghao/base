package com.kqds.service.sys.menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZMenu;
import com.kqds.entity.sys.YZMenuCompartorJson;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.func.YZFuncLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service
public class YZMenuLogic extends BaseLogic {
	@Autowired
	private YZFuncLogic funcLogic;

	@Autowired
	private DaoSupport dao;

	/**
	 * 根据菜单id查询菜单信息(一级菜单)
	 * 
	 * @param conn
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	public YZMenu queryMenu(String menuId) throws Exception {
		List<YZMenu> list = (List<YZMenu>) dao.findForList(TableNameUtil.SYS_MENU + ".queryMenu", menuId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
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
		int count = (int) dao.delete(TableNameUtil.SYS_MENU + ".deleteBySeqIds", idList);
		// 记录日志
		SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_MENU, seqids, TableNameUtil.SYS_MENU, request);
		return count;
	}

	/**
	 * 根据menuid查询
	 * 
	 * @param conn
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	public JSONObject getMenuById(String menuId) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_MENU + ".getMenuById", menuId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询所有菜单
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<JSONObject> getAllMenuList() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_MENU + ".getAllMenuList", null);
		return list;
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
	public List<JSONObject> getMenuList(List<String> menuSet, JSONObject model) throws NumberFormatException, Exception {
		// set 转成 string
		String menuStr = menuSet.toString();
		// 获取menuid
		String parent = model.getString("menuId");
		List<JSONObject> clist = new ArrayList<JSONObject>();

		for (String id : menuSet) {
			// 如果不是二级菜单，则跳过
			if (!id.matches(parent + "\\d{2}")) {
				continue;
			}

			// 如果是二级菜单，即menuid的值为4位数
			JSONObject menu = funcLogic.getFuncById(id);
			// 菜单不存在，则跳过
			if (menu == null) {
				continue;
			}
			menu.remove("icon");
			// 判断是否有下级节点
			if (!menuStr.matches(".*[ ,\\[]" + menu.getString("menuId") + "\\d+.*")) {
				// 没有则设置菜单为叶子节点
				menu.put("isParent", false);
			} else {
				menu.put("isParent", true);
			}

			clist.add(menu);
		}

		// 根据排序号 进行升序排序
		YZMenuCompartorJson mcompare = new YZMenuCompartorJson();
		Collections.sort(clist, mcompare); // 升序

		return clist;
	}

	public List<JSONObject> lazyLoadJsonTree4Manage(List<String> funcSet, List<JSONObject> menulist) throws Exception {
		for (int i = 0; i < menulist.size(); i++) {
			JSONObject currmenu = menulist.get(i); // 当前菜单
			// 获取当前菜单的子菜单列表
			List<JSONObject> childmlist = getMenuList(funcSet, currmenu);

			for (JSONObject menuM : childmlist) {
				menuM.put("parentid", currmenu.getString("menuId"));
				menuM.put("menuName", menuM.getString("funcName"));
			}

			menulist.addAll(childmlist);

			if (childmlist != null && childmlist.size() > 0) { // 判断是否有此菜单权限
				this.lazyLoadJsonTree4Manage(funcSet, childmlist);
			}
		}
		return menulist;
	}

	/**
	 * 用于后台的菜单管理
	 * 
	 * @param dbConn
	 * @param person
	 * @param funclist
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getMainMenu4Manage(YZPerson person, List<String> funclist) throws Exception {
		List<JSONObject> menulist = getAllMenuList();

		String menuStr = funclist.toString();
		for (JSONObject menu : menulist) {
			if (!menuStr.matches(".*[ ,\\[]" + menu.getString("menuId") + "\\d+.*")) {
				// 没有下级菜单的情况,设置菜单为叶子节点
				menu.put("isParent", false);
			} else {
				menu.put("isParent", true);
			}
			menu.put("parentid", "0");
		}

		return menulist;
	}

	public int countByMenuName(YZMenu dp) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.SYS_MENU + ".countByMenuName", dp);
		return count;
	}

	public int countByMenuId(YZMenu dp) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.SYS_MENU + ".countByMenuId", dp);
		return count;
	}

	/**
	 * 菜单树下拉框
	 * 
	 * @param deptId
	 * @param conn
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public String getSelectMenuTree(String menuId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		getSelectMenuTreeJson(menuId, sb, 0);
		if (sb.charAt(sb.length() - 1) == ',') {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes" })
	public void getSelectMenuTreeJson(String parentId, StringBuffer sb, int level) throws Exception {
		List<JSONObject> list = null;
		if (0 == level) {
			list = getAllMenuList();
		} else {
			list = funcLogic.getListByParentId(parentId);
		}
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
			String menuId = (String) dp.get("menuId");
			String menuName = (String) dp.get("menuName");
			sb.append("{");
			sb.append("text:'" + flag + YZUtility.encodeSpecial(menuName) + "',");
			sb.append("value:'" + menuId + "'");
			sb.append("},");
			getSelectMenuTreeJson(menuId, sb, level + 1);
		}

	}

}
