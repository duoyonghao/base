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
import com.kqds.entity.base.KqdsOutprocessingType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.outProcessingSheetDetail.KQDS_OutProcessingSheet_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.other.ChineseCharToEn;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class KQDS_OutProcessing_TypeLogic extends BaseLogic {
	private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessing_TypeLogic.class);
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_OutProcessingLogic itemLogic;
	@Autowired
	private KQDS_OutProcessingSheet_DetailLogic detailLogic;

	/**
	 * 根据主键删除
	 * 
	 * @param conn
	 * @param seqId
	 * @throws Exception
	 */
	public int delelteBySeqIds(String seqIds, HttpServletRequest request) throws Exception {
		boolean isSingleDel = true; // 默认单个删除
		List<String> codeList = getTypeCodeListBySeqIds(seqIds);
		if (codeList.size() > 1) {
			isSingleDel = false; // 批量删除
		}

		int count = 0;
		for (String code : codeList) {
			// 根据分类，查询下面的加工项目
			List<JSONObject> itemList = itemLogic.getItemListByTypeCodes(code, ChainUtil.getOrganizationFromUrlCanNull(request));
			StringBuffer itemCodesStr = new StringBuffer();
			for (JSONObject item : itemList) {
				String wjgxmbh = item.getString("wjgxmbh");
				itemCodesStr.append(wjgxmbh).append(",");
			}
			// 根据外加工项目编号，查询外加工单项目
			int count1 = detailLogic.getCountByItemCodes(itemCodesStr.toString());
			if (count1 > 0) {
				if (isSingleDel) {
					throw new Exception("该加工分类存在加工单，无法删除！");
				}
				logger.error("该加工分类存在加工单，无法删除！");
			} else {
				// 删除加工厂
				dao.delete(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".deletecode", code);
				// 删除加工项目
				itemLogic.deleteByTypeCode(code, request);

				count++;
			}
		}

		return count;
	}

	/**
	 * 根据主键获取编码
	 * 
	 * @param conn
	 * @param seqIds
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> getTypeCodeListBySeqIds(String seqIds) throws Exception {
		List<String> codelist = new ArrayList<String>();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".getTypeCodeListBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
		for (JSONObject json : list) {
			String code = json.getString("typeno");
			if (YZUtility.isNullorEmpty(code)) {
				continue;
			}
			codelist.add(code);
		}
		return codelist;
	}

	public int deleteByUnitCode(String unitCode, HttpServletRequest request) throws Exception {
		int count = (int) dao.delete(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".deleteByUnitCode", unitCode);
		return count;
	}

	public int countByCode(KqdsOutprocessingType dp) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".countByCode", dp);
		return count;
	}

	/**
	 * 同一个门诊，同一个加工厂下，分类不允许重复
	 * 
	 * @param conn
	 * @param dp
	 * @return
	 * @throws Exception
	 */
	public int countByOrgAndName(KqdsOutprocessingType dp) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".countByOrgAndName", dp);
		return count;
	}

	/**
	 * 根据加工厂编号，获取对应的加工类别列表
	 * 
	 * @param dbConn
	 * @param class_no
	 * @param search
	 * @param isAdd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsOutprocessingType> getjgTypeList(String class_no, String search, String isAdd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("processingfactory", class_no);
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if (!YZUtility.isNullorEmpty(isAdd)) {
			map.put("isadd", isAdd);
		}
		if (!YZUtility.isNullorEmpty(search)) {
			map.put("search", search);
		}
		List<KqdsOutprocessingType> list = (List<KqdsOutprocessingType>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".getjgTypeList", map);
		return list;
	}

	/**
	 * 根据加工厂编号，统计加工类别
	 * 
	 * @param dbConn
	 * @param class_no
	 * @param search
	 * @param isAdd
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public int countJgTypeList(String code, String search, String isAdd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("processingfactory", code);
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if (!YZUtility.isNullorEmpty(isAdd)) {
			map.put("isadd", isAdd);
		}
		if (!YZUtility.isNullorEmpty(search)) {
			map.put("search", search);
		}

		int count = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".countJgTypeList", map);
		return count;
	}

	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 验证加工项目分类是否存在--根据名称，存在则返回typeno
	 * 
	 * @param dbConn
	 * @param typename
	 * @param jgc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String checkJgcType(String typename, String jgc, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("typename", typename);
		map.put("jgc", jgc);
		map.put("organization", organization);
		String class_no = null;
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".checkJgcType", map);
		if (list != null && list.size() > 0) {
			class_no = list.get(0).getString("typeno");
		}
		return class_no;
	}

	/**
	 * 根据分类名称查询
	 * 
	 * @param dbConn
	 * @param jgcCode
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public KqdsOutprocessingType getJgcTypeByName(String jgcCode, String name) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("typename", name);
		map.put("jgc", jgcCode);
		List<KqdsOutprocessingType> list = (List<KqdsOutprocessingType>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".getJgcTypeByName", map);
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
	public String getUniCodeByName(String typename, String unitCode) throws Exception {
		String code = unitCode + "_" + ChineseCharToEn.getAllFirstLetter_RandNum(typename);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_OUTPROCESSING_TYPE + ".getUniCodeByName", code);
		if (count == 0) {
			return code;
		} else {
			return getUniCodeByName(typename, unitCode);
		}
	}
}