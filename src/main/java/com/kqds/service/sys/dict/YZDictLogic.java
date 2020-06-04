package com.kqds.service.sys.dict;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service
public class YZDictLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	/**
	 * 根据主键，获取字典编号
	 */
	public String getDictCodesBySeqIds(String seq_ids) throws Exception {
		String dictCodes = "";
		List<String> idList = YZUtility.ConvertString2List(seq_ids);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getDictCodesBySeqIds", idList);
		for (JSONObject rs : list) {
			dictCodes += rs.getString("dict_code") + ",";
		}
		if (dictCodes.endsWith(",")) {
			dictCodes = dictCodes.substring(0, dictCodes.length() - 1);
		}
		return dictCodes;
	}

	/**
	 * 根据dict_code获取seq_ids
	 * 
	 * @param conn
	 * @param dictCodes
	 * @return
	 * @throws Exception
	 */
	public String getDictSeqIdsByDictCodes(String dictCodes) throws Exception {
		String dictSeqIds = "";
		List<String> codeList = YZUtility.ConvertString2List(dictCodes);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getDictSeqIdsByDictCodes", codeList);
		for (JSONObject rs : list) {
			dictSeqIds += rs.getString("seq_id") + ",";
		}
		if (dictSeqIds.endsWith(",")) {
			dictSeqIds = dictSeqIds.substring(0, dictSeqIds.length() - 1);
		}
		return dictSeqIds;
	}

	public String getDictNamesByDictCodes(String ids) throws Exception {
		String dictNames = "";
		List<String> codeList = YZUtility.ConvertString2List(ids);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getDictNamesByDictCodes", codeList);
		for (JSONObject rs : list) {
			dictNames += rs.getString("dict_name") + ",";
		}
		if (dictNames.endsWith(",")) {
			dictNames = dictNames.substring(0, dictNames.length() - 1);
		}
		return dictNames;
	}

	/**
	 * 根据ID集合 查询标签中文集合 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public String getDictNamesBySeqIds(String ids) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(ids);
		String dictNames = "";
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getDictNamesBySeqIds", idList);
		for (JSONObject rs : list) {
			dictNames += rs.getString("dict_name") + ",";
		}
		if (dictNames.endsWith(",")) {
			dictNames = dictNames.substring(0, dictNames.length() - 1);
		}
		return dictNames;
	}

	public int countByName(YZDict dp) throws Exception {
		JSONObject json = new JSONObject(); // 这样写的目的是为了通过动态sql实现Java中的重载
		json.put("dictName", dp.getDictName());
		json.put("seqId", dp.getSeqId());
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".countByName", json);
		return count;
	}

	public int countByName(YZDict dp, String organization) throws Exception {
		JSONObject json = new JSONObject(); // 这样写的目的是为了通过动态sql实现Java中的重载
		json.put("parentCode", dp.getParentCode());
		json.put("seqId", dp.getSeqId());
		json.put("organization", organization);
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".countByName", json);
		return count;
	}

	public int countByCode(YZDict dp) throws Exception {
		JSONObject json = new JSONObject(); // 这样写的目的是为了通过动态sql实现Java中的重载
		json.put("dictCode", dp.getDictCode());
		json.put("seqId", dp.getSeqId());
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".countByName", json);
		return count;
	}

	public YZDict getByNameAndParntCodeOrg(String dictName, String parentCode, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("parentCode", parentCode);
		json.put("dictName", dictName);
		json.put("organization", organization);
		YZDict dict = (YZDict) dao.findForObject(TableNameUtil.SYS_DICT + ".getByNameAndParntCodeOrg", json);
		return dict;
	}

	public YZDict getUniDictByCode(String dictCode) throws Exception {
		YZDict dict = (YZDict) dao.findForObject(TableNameUtil.SYS_DICT + ".getUniDictByCode", dictCode);
		return dict;
	}

	/**
	 * 根据编号，获取唯一的数据字典编码
	 * 
	 * @param conn
	 * @param dictCode
	 * @return
	 * @throws Exception
	 */
	public String getUniDictCodeByName(String dictname) throws Exception {
		String dictCode = ChineseCharToEn.getAllFirstLetter_RandNum(dictname);
		JSONObject json = new JSONObject(); // 这样写的目的是为了通过动态sql实现Java中的重载
		json.put("dictCode", dictCode);
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".countByName", json);

		if (count == 0) {
			return dictCode;
		} else {
			return getUniDictCodeByName(dictname);
		}
	}

	/**
	 * 根据父级编号和门诊编号，获取子级列表 目前：收费项目分类管理使用此方法
	 * 
	 * @param conn
	 * @param parentCode
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getSubListByParentCodeAndOrg(String parentCode, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("parentCode", parentCode);
		json.put("organization", organization);
		List<JSONObject> deptlist = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getSubListByParentCodeAndOrg", json);
		return deptlist;
	}

	/**
	 * 该方法用于数据字典管理，不做门诊条件过滤
	 * 
	 * @param parentCode
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<JSONObject> getSubListByParentCode(String parentCode, HttpServletRequest request) throws Exception {
		String noShowDict = SysParaUtil.getSysValueByName(request, SysParaUtil.NOT_SHOW_KIND_DICTS);
		List<String> idList = YZUtility.ConvertString2List(noShowDict);
		JSONObject json = new JSONObject();
		json.put("idList", idList);
		json.put("parentCode", parentCode);
		List<JSONObject> deptlist = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getSubListByParentCodeJson", json);
		return deptlist;
	}

	public boolean IsHaveChild(String parentCode) throws Exception {
		JSONObject json = new JSONObject(); // 这样写的目的是为了通过动态sql实现Java中的重载
		json.put("parentCode", parentCode);
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".countByName", json);
		return count > 0;
	}

	public boolean IsHaveChild(String parentCode, String organization) throws Exception {
		JSONObject json = new JSONObject(); // 这样写的目的是为了通过动态sql实现Java中的重载
		json.put("parentCode", parentCode);
		json.put("organization", organization);
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".countByName", json);
		return count > 0;
	}

	/**
	 * 专供字典树使用
	 * 
	 * @param conn
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean IsHaveChild4DictTree(String parentCode) throws Exception {
		JSONObject json = new JSONObject();
		json.put("parentCode", parentCode);
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".IsHaveChild4DictTree", json);
		return count > 0;
	}

	public int countByParentCode(String parentCode) throws Exception {
		JSONObject json = new JSONObject(); // 这样写的目的是为了通过动态sql实现Java中的重载
		json.put("parentCode", parentCode);
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".countByName", json);
		return count;
	}

	public int countByParentCode(String parentCode, String organization) throws Exception {
		JSONObject json = new JSONObject(); // 这样写的目的是为了通过动态sql实现Java中的重载
		json.put("parentCode", parentCode);
		json.put("organization", organization);
		int count = (int) dao.findForObject(TableNameUtil.SYS_DICT + ".countByName", json);
		return count;
	}

	@SuppressWarnings("rawtypes")
	public JSONObject selectPage(BootStrapPage bp, String parentCode, String organization) throws Exception {
		String search = bp.getSearch();
		String sort = bp.getSort();
		if (YZUtility.isNullorEmpty(organization)) {
			organization = "";
		}
		JSONObject json = new JSONObject();
		json.put("parentCode", parentCode);
		json.put("organization", organization);
		json.put("search", search);
		json.put("sort", sort);

		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".selectPage", json);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 更新状态
	 * 
	 * @param conn
	 * @param seqids
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public int updateFlagBySeqIds(String seqids, String useflag, HttpServletRequest request) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(seqids);
		JSONObject json = new JSONObject();
		json.put("useflag", useflag);
		json.put("idList", idList);
		int count = (int) dao.update(TableNameUtil.SYS_DICT + ".updateFlagBySeqIds", json);
		// 记录日志
		SysLogUtil.log(SysLogUtil.UPDATE_STATUS, SysLogUtil.SYS_DICT, seqids, TableNameUtil.SYS_DICT, request);
		return count;
	}

	/**
	 * 删除
	 * 
	 * @param conn
	 * @param seqids
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public int deleteBySeqIds(String seqids, HttpServletRequest request) throws Exception {
		List<String> idList = YZUtility.ConvertString2List(seqids);
		int count = (int) dao.delete(TableNameUtil.SYS_DICT + ".deleteBySeqIds", idList);
		// 记录日志
		SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_DICT, seqids, TableNameUtil.SYS_DICT, request);
		return count;
	}

	public void initData(HttpServletRequest request) throws Exception {
		dao.delete(TableNameUtil.SYS_DICT + ".initData", null);
	}

	/**
	 * 根据父级编号获取子项目列表
	 * 
	 * @param dbConn
	 * @param itemType
	 * @return
	 * @throws Exception
	 */
	public List<YZDict> getListByParentCode(String parentCode, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("parentCode", parentCode);
		json.put("orderBy", SQLUtil.castAsInt("orderno"));
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getListByParentCode", json);
		return list;
	}
	
	/**
	 * 初始化患者来源 （专属客服人员） 2019-11-30 syp
	 * @param parentCode
	 * @param organization
	 * @param iscustomer
	 * @return
	 * @throws Exception
	 */
	public List<YZDict> getListByParentCodeIscustomer(String parentCode, String organization, String iscustomer) throws Exception {
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("parentCode", parentCode);
		json.put("iscustomer", iscustomer);
		json.put("orderBy", SQLUtil.castAsInt("orderno"));
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getListByParentCodeIscustomer", json);
		return list;
	}

	public List<YZDict> getListByParentCodeALL(String parentCode, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("parentCode", parentCode);
		json.put("orderBy", SQLUtil.castAsInt("orderno"));
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getListByParentCodeALL", json);
		return list;
	}

	/**
	 * 获取子项目列表【如：来源子分类】
	 * 
	 * @param dbConn
	 * @param parentCode
	 * @param organization
	 * @param isAdd
	 * @return
	 * @throws Exception
	 */
	public List<YZDict> getSubListByParentCode(String parentCode, String organization, String isAdd) throws Exception {
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("parentCode", parentCode);
		json.put("isAdd", isAdd);
		json.put("orderBy", SQLUtil.castAsInt("orderno"));
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getSubListByParentCode", json);
		return list;
	}

	/**
	 * 不做门诊条件过滤
	 * 
	 * @param dbConn
	 * @param parentCode
	 * @param organization
	 * @param isAdd
	 * @return
	 * @throws Exception
	 */
	public List<YZDict> getSubListByParentCodeNoOrg(String parentCode, String isAdd) throws Exception {
		JSONObject json = new JSONObject();
		json.put("parentCode", parentCode);
		json.put("isAdd", isAdd);
		json.put("orderBy", SQLUtil.castAsInt("orderno"));
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getSubListByParentCode", json);
		return list;
	}

	/**
	 * 根据编号获取详情【字典编号全局唯一】
	 * 
	 * @param dbConn
	 * @param dictCode
	 * @return
	 * @throws Exception
	 */
	public YZDict getDetailByDictCode(String dictCode) throws Exception {
		StringBuffer sql = new StringBuffer(" select * from " + TableNameUtil.SYS_DICT + " ");
		sql.append(" where 1=1 and dict_code = '" + dictCode + "' ");
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getDetailByDictCode", dictCode);
		if (list.size() == 0) {
			throw new Exception("记录不存在");
		}
		if (list.size() > 1) {
			throw new Exception("数据异常，一个编号不存在多个记录");
		}

		return list.get(0);
	}

	/**
	 * 根据名称和上级编号，查询详情
	 * 
	 * @param dbConn
	 * @param dictCode
	 * @return
	 * @throws Exception
	 */
	public YZDict getDetailByNameAndParentCode(String dictName, String parentCode) throws Exception {
		JSONObject json = new JSONObject();
		json.put("dictName", dictName);
		json.put("parentCode", parentCode);
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getDetailByNameAndParentCode", json);
		if (list.size() == 0) {
			throw new Exception("数据字典不存在，字典名称为：" + dictName);
		}
		if (list.size() > 1) {
			throw new Exception("字典数据异常，一个编号不存在多个记录，字典名称为：" + dictName);
		}

		return list.get(0);
	}

	/**
	 * 根据名称和上级编号，查询详情，不抛异常
	 * 
	 * @param dbConn
	 * @param dictCode
	 * @return
	 * @throws Exception
	 */
	public YZDict getDetailByNameAndParentCodeCanNull(String dictName, String parentCode) throws Exception {
		JSONObject json = new JSONObject();
		json.put("dictName", dictName);
		json.put("parentCode", parentCode);
		YZDict dict = new YZDict();
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getDetailByNameAndParentCodeCanNull", json);
		if (list != null && list.size() > 0) {
			dict = list.get(0);
		}
		return dict;
	}

	// 查询付款方式
	public String getDictIdByNameAndParentCode(String parentCode, String dictName) throws Exception {
		String result = "";
		JSONObject json = new JSONObject();
		json.put("dictName", dictName);
		json.put("parentCode", parentCode);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getDictIdByNameAndParentCode", json);
		if (list != null && list.size() > 0) {
			result = list.get(0).getString("seq_id");
		}
		return result;
	}

	// 根据parentCode 查询 最大排序号
	public String getMaxOrderno(String parentCode) throws Exception {
		String result = "";
		JSONObject json = new JSONObject();
		json.put("parentCode", parentCode);
		json.put("orderBy", SQLUtil.castAsInt("orderno"));
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DICT + ".getMaxOrderno", json);
		if (list != null && list.size() > 0) {
			result = list.get(0).getString("orderno");
		}
		return result;
	}

	/**
	 * 根据主键获取字典名称
	 * 
	 * @param seqId
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	public String getDictNameBySeqId(String seqId) throws Exception {
		String dictName = "";

		YZDict dict = (YZDict) dao.loadObjSingleUUID(TableNameUtil.SYS_DICT, seqId);
		if (dict != null) {
			dictName = dict.getDictName();
		}
		return dictName;
	}

	/**
	 * 获取树节点
	 */
	public List<JSONObject> getDictNodeList(String parentCode, List<JSONObject> treeList, String dictIds,String organization) throws Exception {
		List<YZDict> dictList = getListByParentCode(parentCode, organization);
		for (YZDict dict : dictList) {
			boolean haveChild = IsHaveChild(dict.getDictCode());
			JSONObject node = new JSONObject();
			node.put("id", dict.getDictCode());
			node.put("pId", dict.getParentCode());
			node.put("name", dict.getDictName());
			node.put("isParent", haveChild);
			node.put("nocheck", false);
			treeList.add(node);
			if (haveChild) {
				getDictNodeList(dict.getDictCode(), treeList, dictIds,organization);
			}
		}
		return treeList;
	}

	/**
	 * 获取顶级数据字典
	 * 
	 * @param conn
	 * @param dict
	 * @return
	 * @throws Exception
	 */
	public YZDict getTopDict(YZDict dict) throws Exception {
		if ("0".equals(dict.getParentCode())) {
			return dict;
		} else {
			List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getTopDict", dict.getParentCode());
			if (list.size() == 0) {
				throw new Exception("根据编号查询不到字典记录，编号为：" + dict.getParentCode());
			}
			if (list.size() > 1) {
				throw new Exception("数据异常：一个编号对应多条记录，编号为：" + dict.getParentCode());
			}
			YZDict parent = list.get(0);
			return getTopDict(parent);
		}

	}
	
	/**
	 * ##############根据药品库名称查询药库##############
	 * @param storeName
	 * @return
	 * @throws Exception
	 */
	public List<YZDict> getDrugsStoreByName(String storeName) throws Exception {
		List<YZDict> list = (List<YZDict>) dao.findForList(TableNameUtil.SYS_DICT + ".getDrugsStoreByName", storeName);
		return list;
	}
	
	/**
	 * 根据字典code查询爱好名称  syp 2019-8-20
	  * @Title: findDictByDictCode   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dictCode
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2019年8月20日 下午5:50:58
	 */
	public JSONObject findDictByDictCode(String dictCode) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.SYS_DICT + ".findDictByDictCode", dictCode);
		return json;
	}
	
	public String findDictNameBySeqId(String seqId) throws Exception{
		String json = (String) dao.findForObject(TableNameUtil.SYS_DICT + ".findDictNameBySeqId", seqId);
		return json;
	}
}
