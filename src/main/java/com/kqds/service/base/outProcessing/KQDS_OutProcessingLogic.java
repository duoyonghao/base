package com.kqds.service.base.outProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsOutprocessing;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.outProcessingSheetDetail.KQDS_OutProcessingSheet_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class KQDS_OutProcessingLogic extends BaseLogic {
	private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessingLogic.class);
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_OutProcessingSheet_DetailLogic detailLogic;

	/**
	 * 删除所有
	 * 
	 * @param conn
	 * @param seqIds
	 * @param request
	 * @throws Exception
	 */
	public int delelteAll(String organization, HttpServletRequest request) throws Exception {
		List<JSONObject> listall = getItemListByTypeCodes(null, organization); // 查所有加工项目
		StringBuffer seqIdBf = new StringBuffer();
		for (JSONObject json : listall) {
			String seq_id = json.getString("seq_id");
			if (YZUtility.isNullorEmpty(seq_id)) {
				continue;
			}
			seqIdBf.append(seq_id).append(",");
		}

		return delelteBySeqIds(seqIdBf.toString(), request);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param conn
	 * @param seqId
	 * @throws Exception
	 */
	public int delelteBySeqIds(String seqIds, HttpServletRequest request) throws Exception {
		boolean isSingleDel = true; // 默认单个删除
		List<String> itemnoList = getItemnosBySeqIds(seqIds);
		if (itemnoList.size() > 1) {
			isSingleDel = false; // 批量删除
		}

		int count = 0;
		for (String wjgxmbh : itemnoList) {
			// 根据外加工项目编号，查询外加工单项目
			int count1 = detailLogic.getCountByItemCodes(wjgxmbh);
			if (count1 > 0) {
				if (isSingleDel) {
					throw new Exception("该加工项目存在加工单，无法删除！");
				}
				logger.error("该加工项目存在加工单，无法删除！");
			} else {
				// 删除加工厂
				dao.delete(TableNameUtil.KQDS_OUTPROCESSING + ".deletewjgxmbh", wjgxmbh);
				count++;
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<String> getItemnosBySeqIds(String seqIds) throws Exception {
		List<String> itemnoList = new ArrayList<String>();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING + ".getItemnosBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
		for (JSONObject json : list) {
			String wjgxmbh = json.getString("wjgxmbh");
			if (YZUtility.isNullorEmpty(wjgxmbh)) {
				continue;
			}
			itemnoList.add(wjgxmbh);
		}
		return itemnoList;
	}

	/**
	 * 根据外加工分类编号，查询外加工项目
	 * 
	 * @param conn
	 * @param seqIds
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getItemListByTypeCodes(String codes, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNotNullOrEmpty(codes)) {
			map.put("wjgfl", YZUtility.ConvertStringIds4Query(codes));
		}
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING + ".getItemListByTypeCodes", map);
		return list;
	}

	/**
	 * 根据加工厂，删除加工项目
	 * 
	 * @param conn
	 * @param unitCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteByUnitCode(String unitCode, HttpServletRequest request) throws Exception {
		int count = (int) dao.delete(TableNameUtil.KQDS_OUTPROCESSING + ".deleteByUnitCode", unitCode);
		return count;
	}

	/**
	 * 根据加工分类，删除加工项目
	 * 
	 * @param conn
	 * @param unitCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteByTypeCode(String typecode, HttpServletRequest request) throws Exception {
		int count = (int) dao.delete(TableNameUtil.KQDS_OUTPROCESSING + ".deleteByUnitCode", typecode);
		return count;
	}

	/**
	 * 根据加工分类，获取加工项目，用于树形展示
	 * 
	 * @param dbConn
	 * @param class_no
	 * @param search
	 * @param isAdd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsOutprocessing> getjgItemListByType(String wjgfl, String search, String isAdd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("wjgfl", wjgfl);
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if (!YZUtility.isNullorEmpty(isAdd)) {
			map.put("isadd", isAdd);
		}
		if (!YZUtility.isNullorEmpty(search)) {
			map.put("search", search);
		}
		List<KqdsOutprocessing> list = (List<KqdsOutprocessing>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING + ".getjgItemListByType", map);
		return list;
	}

	/**
	 * 根据加工分类，统计加工项目
	 * 
	 * @param dbConn
	 * @param wjgfl
	 * @param search
	 * @param isAdd
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public int countjgItemListByType(String wjgfl, String search, String isAdd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("wjgfl", wjgfl);
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if (!YZUtility.isNullorEmpty(isAdd)) {
			map.put("isadd", isAdd);
		}
		if (!YZUtility.isNullorEmpty(search)) {
			map.put("search", search);
		}
		int count = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING + ".countjgItemListByType", map);
		return count;
	}

	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectByitem(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING + ".selectByitem", map);
		return list;
	}

	/**
	 * 根据编号查询
	 * 
	 * @param conn
	 * @param itemno
	 * @return
	 * @throws Exception
	 */
	public int countByCode(String wjgxmbh) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING + ".countByCode", wjgxmbh);
		return count;
	}
}