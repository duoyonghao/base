package com.kqds.service.base.outProcessingUnit;

//合并测试
import java.sql.SQLException;
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
import com.kqds.entity.base.KqdsOutprocessingUnit;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.outProcessing.KQDS_OutProcessingLogic;
import com.kqds.service.base.outProcessing.KQDS_OutProcessing_TypeLogic;
import com.kqds.service.base.outProcessingSheetDetail.KQDS_OutProcessingSheet_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.ChineseCharToEn;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class KQDS_outProcessing_UnitLogic extends BaseLogic {
	private static Logger logger = LoggerFactory.getLogger(KQDS_outProcessing_UnitLogic.class);
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_OutProcessingSheet_DetailLogic detailLogic;
	@Autowired
	private KQDS_OutProcessing_TypeLogic typeLogic;
	@Autowired
	private KQDS_OutProcessingLogic itemLogic;

	/**
	 * 根据主键删除
	 * 
	 * @param conn
	 * @param seqId
	 * @throws Exception
	 */
	public int delelteBySeqIds(String seqIds, HttpServletRequest request) throws Exception {
		boolean isSingleDel = true; // 默认单个删除
		List<String> codeList = getUnitCodeListBySeqIds(seqIds);
		if (codeList.size() > 1) {
			isSingleDel = false; // 批量删除
		}

		int count = 0;
		for (String code : codeList) {
			// 检查该加工厂下面的加工项目，是否存在加工单
			int count1 = detailLogic.getCount(code);
			if (count1 > 0) {
				if (isSingleDel) {
					throw new Exception("该加工厂存在加工单，无法删除！");
				}
				logger.error("该收费项目存在消费记录，无法删除！");
			} else {

				// 删除加工厂
				dao.delete(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".deletecode", code);
				// 删除加工分类
				typeLogic.deleteByUnitCode(code, request);

				// 删除加工项目
				itemLogic.deleteByUnitCode(code, request);

				count++;
			}
		}

		return count;
	}

	@SuppressWarnings("unchecked")
	public List<String> getUnitCodeListBySeqIds(String seqIds) throws Exception {
		List<String> codelist = new ArrayList<String>();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getUnitCodeListBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
		for (JSONObject json : list) {
			String code = json.getString("code");
			if (YZUtility.isNullorEmpty(code)) {
				continue;
			}
			codelist.add(code);
		}
		return codelist;
	}

	public int countByCode(KqdsOutprocessingUnit dp) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".countByCode", dp);
		return count;
	}

	/**
	 * 加工厂编号验证
	 * 
	 * @param dbConn
	 * @param seqId
	 * @param phonenumber1
	 * @param phonenumber2
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public int checkCode(String seqId, String code, String table) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("seqId", seqId);
		map.put("code", code);
		int num = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".checkCode", map);
		return num;
	}

	public int countByOrgAndName(KqdsOutprocessingUnit dp) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".countByOrgAndName", dp);
		return count;
	}

	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 查询加工厂，后台管理中心调用
	 * 
	 * @param conn
	 * @param isAdd
	 *            如果isAdd有值，说明是新增页面； 目前后台的患者来源有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getUnitList4Back(String isAdd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(isAdd)) {
			map.put("isadd", isAdd);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getUnitList4Back", map);
		return list;
	}

	/**
	 * 查询加工厂
	 * 
	 * @param conn
	 * @param isAdd
	 *            如果isAdd有值，说明是新增页面； 目前后台的患者来源有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getUnitList(String isAdd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(isAdd)) {
			map.put("isadd", isAdd);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getUnitList", map);
		return list;
	}

	/**
	 * 验证加工厂是否存在--根据名称
	 * 
	 * @param dbConn
	 * @param typename
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public KqdsOutprocessingUnit getJgcByName(String name, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(name)) {
			map.put("name", name);
		}
		List<KqdsOutprocessingUnit> list = (List<KqdsOutprocessingUnit>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getJgcByName", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 验证加工厂是否存在--根据编号
	 * 
	 * @param dbConn
	 * @param typename
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public KqdsOutprocessingUnit getJgcByCode(String code, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organization", organization);
		map.put("code", code);
		List<KqdsOutprocessingUnit> list = (List<KqdsOutprocessingUnit>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getJgcByCode", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取唯一的编号
	 * 
	 * @param conn
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String getUniCodeByName(String name) throws Exception {
		String code = ChineseCharToEn.getAllFirstLetter_RandNum(name);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getUniCodeByName", code);
		if (count == 0) {
			return code;
		} else {
			return getUniCodeByName(name);
		}
	}

	/**
	 * 获取加工厂列表，后台管理中心使用
	 * 
	 * @param dbConn
	 * @param search
	 * @param mrjgc
	 * @param isAdd
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsOutprocessingUnit> getJgcDictList4Back(String search, String mrjgc, String isAdd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(mrjgc)) {
			map.put("mrjgc", mrjgc);
		}
		if (!YZUtility.isNullorEmpty(isAdd)) {
			map.put("isadd", isAdd);
		}
		if (!YZUtility.isNullorEmpty(search)) {
			map.put("search", search);
		}
		List<KqdsOutprocessingUnit> list = (List<KqdsOutprocessingUnit>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getJgcDictList4Back", map);
		return list;
	}

	/**
	 * 获取加工厂列表，以dict形式返回
	 * 
	 * @param dbConn
	 * @param search
	 * @param mrjgc
	 * @param isAdd
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsOutprocessingUnit> getJgcDictList(String search, String mrjgc, String isAdd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(mrjgc)) {
			map.put("mrjgc", mrjgc);
		}
		if (!YZUtility.isNullorEmpty(isAdd)) {
			map.put("isadd", isAdd);
		}
		if (!YZUtility.isNullorEmpty(search)) {
			map.put("search", search);
		}
		List<KqdsOutprocessingUnit> list = (List<KqdsOutprocessingUnit>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_UNIT + ".getJgcDictList", map);
		return list;
	}
}