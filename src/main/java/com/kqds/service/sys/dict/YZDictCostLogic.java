package com.kqds.service.sys.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDict;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

/**
 * 收费项目相关的
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
@Service
public class YZDictCostLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	/**
	 * 获取收费项目一级分类，用于后台收费项目管理
	 * 
	 * @param dbConn
	 * @param search
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getLeve1SortList4Manager(String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getLeve1SortList4Manager", organization);
		return list;
	}

	/**
	 * 根据收费项目一级分类，获取收费项目二级分类列表，用于后台收费项目管理
	 * 
	 * @param dbConn
	 * @param class_no
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getLeve2SortList4Manager(String parentCode, String search, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("parentCode", parentCode);
		json.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getLeve2SortList4Manager", json);
		return list;
	}

	/**
	 * 获取收费项目一级分类，根据门诊编号
	 * 
	 * @param dbConn
	 * @param search
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public List<YZDict> getLeve1SortListOrg(String search, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("search", search);
		json.put("organization", organization);
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getLeve1SortListOrg", json);
		return list;
	}

	/**
	 * 根据收费项目一级分类，获取收费项目二级分类列表
	 * 
	 * @param dbConn
	 * @param class_no
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public List<YZDict> getLeve2SortListOrg(String parentCode, String search, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("search", search);
		json.put("parentCode", parentCode);
		json.put("organization", organization);
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getLeve2SortListOrg", json);
		return list;
	}

	/**
	 * 获取收费项目一级分类
	 * 
	 * @param dbConn
	 * @param search
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public List<YZDict> getLeve1SortList(String search, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("parentCode", DictUtil.COSTITEM_SORT);
		json.put("search", search);
		json.put("organization", organization);
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getLeve1SortList", json);
		return list;
	}

	/**
	 * 根据收费项目一级分类，获取收费项目二级分类列表
	 * 
	 * @param dbConn
	 * @param class_no
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public List<YZDict> getLeve2SortList(String parentCode, String search, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("search", search);
		json.put("parentCode", parentCode);
		json.put("organization", organization);
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getLeve2SortList", json);
		return list;
	}
	
	/**
	 * 获取药品一、二级分类
	 * 
	 * @param dbConn
	 * @param class_no
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public String getBaseType(String iscustomer, String parentCode, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("iscustomer", iscustomer);
		json.put("parentCode", parentCode);
		json.put("organization", organization);
		return (String) dao.findForObject(TableNameUtil.SYS_DICT + ".getBaseType", json);
	}
	
	/**
	 * 验证基础分类是否存在--根据名称 【不做门诊条件过滤】
	 * 
	 * @param dbConn
	 * @param typename
	 * @return
	 * @throws Exception
	 */
	public int checkBaseType(String dictName) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".checkBaseType", dictName);
		return count;
	}

	/**
	 * 验证基础分类是否存在--根据名称
	 * 
	 * @param dbConn
	 * @param typename
	 * @return
	 * @throws Exception
	 */
	public String checkBaseTypeGetSeqId(String dictName) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".checkBaseTypeGetSeqId", dictName);
		if (list.size() > 0) {
			return list.get(0).getString("seq_id");
		} else {
			return "";
		}
	}

	/**
	 * 验证二级分类是否存在--根据名称 【不做门诊条件过滤】
	 * 
	 * @param dbConn
	 * @param typename
	 * @param baseno
	 * @return
	 * @throws Exception
	 */
	public String checkNextType(String dictName, String parentCode) throws Exception {
		JSONObject json = new JSONObject();
		json.put("dictName", dictName);
		json.put("parentCode", parentCode);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".checkNextType", json);
		if (list.size() > 0) {
			return list.get(0).getString("dictCode");
		} else {
			return null;
		}
	}

}
