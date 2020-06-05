package com.kqds.service.sys.func;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZFunc;
import com.kqds.entity.sys.YZMenuModel;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service
public class YZFuncLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	/**
	 * 查询所有菜单
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<JSONObject> getListByParentId(String parentId) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FUNC + ".getListByParentId", parentId);
		return list;
	}

	/**
	 * 根据菜单id查询菜单信息(二级菜单,三级菜单)
	 * 
	 * @param conn
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	public YZMenuModel queryFuncMoel(String id) throws Exception {
		List<YZMenuModel> list = (List<YZMenuModel>) dao.findForList(TableNameUtil.SYS_FUNC + ".queryFuncMoel", id);
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
		int count = (int) dao.delete(TableNameUtil.SYS_FUNC + ".deleteBySeqIds", idList);
		// 记录日志
		SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_FUNC, seqids, TableNameUtil.SYS_FUNC, request);
		return count;
	}

	/**
	 * 判断当前是否有子菜单
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean IsHaveChild(String id) throws Exception {
		int count1 = (int) dao.findForObject(TableNameUtil.SYS_FUNC + ".IsHaveChild", id);
		return count1 > 0;
	}

	/**
	 * 判断菜单是否已经存在
	 * 
	 * @param conn
	 * @param dp
	 * @param menuparent
	 * @param menuCode
	 * @return
	 * @throws Exception
	 */
	public int countByMenuId(YZFunc dp, String menuparent, String menuCode) throws Exception {
		JSONObject json = new JSONObject();
		json.put("menuId", menuparent + menuCode);
		json.put("seqId", dp.getSeqId());
		int count = (int) dao.findForObject(TableNameUtil.SYS_FUNC + ".countByMenuId", json);
		return count;
	}

	/**
	 * 同一级菜单，菜单名不允许重复
	 * 
	 * @param conn
	 * @param dp
	 * @param menuparent
	 * @param menuName
	 * @return
	 * @throws Exception
	 */
	public int countByMenuName(YZFunc dp, String menuparent, String funcName) throws Exception {
		JSONObject json = new JSONObject();
		json.put("menuparent", menuparent);
		json.put("funcName", funcName);
		json.put("seqId", dp.getSeqId());
		int count = (int) dao.findForObject(TableNameUtil.SYS_FUNC + ".countByMenuName", json);
		return count;
	}

	public JSONObject getFuncById(String menuId) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FUNC + ".getFuncById", menuId);
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
	public List<JSONObject> getAllFuncList() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FUNC + ".getAllFuncList", null);
		return list;
	}

	public List<String> listAllMenu4Tree() throws Exception {
		HashSet<String> addSet = new HashSet<String>();

		List<JSONObject> mlist = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FUNC + ".listAllMenu4Tree", null);
		for (JSONObject func : mlist) {
			String menuid = func.getString("menu_id");
			menuid = menuid.trim();

			addSet.add(menuid);

			if (menuid.length() > 4) {
				addSet.add(menuid.substring(0, 4));
			} else if (menuid.length() > 2) {
				addSet.add(menuid.substring(0, 2));
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

}
