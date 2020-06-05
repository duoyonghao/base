package com.kqds.service.base.treatItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.giveItem.KQDS_Give_ItemLogic;
import com.kqds.service.base.treatItemTc.KQDS_TreatItem_TcLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class KQDS_TreatItemLogic extends BaseLogic {
	private static Logger logger = LoggerFactory.getLogger(KQDS_TreatItemLogic.class);
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_CostOrder_DetailLogic costOrderDetailLogic;
	@Autowired
	private KQDS_Give_ItemLogic giveItemLogic;
	@Autowired
	private KQDS_TreatItem_TcLogic tcLogic;

	/**
	 * 自动获取编号，用于新增
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public String getAutoCode4Add(String organization) throws Exception {
		JSONObject topJson = (JSONObject) dao.findForObject(TableNameUtil.KQDS_TREATITEM + ".getAutoCode4Add", organization);
		if (topJson == null) {
			return null;
		}

		String treatitemno = topJson.getString("treatitemno");
		if (YZUtility.isNullorEmpty(treatitemno)) {
			return null;
		}

		String lastStr = treatitemno.substring(treatitemno.length() - 1, treatitemno.length());
		try {
			int lastInt = Integer.parseInt(lastStr);
			String preStr = treatitemno.substring(0, treatitemno.length() - 1);
			return preStr + (lastInt + 1);
		} catch (Exception e) {
			logger.error("字符转数据失败：" + e.getMessage());
			return null;
		}

	}

	/**
	 * 删除所有
	 * 
	 * @param conn
	 * @param seqIds
	 * @param request
	 * @throws Exception
	 */
	public int delelteAll(String organization, HttpServletRequest request) throws Exception {
		List<JSONObject> listall = getAllsfxmSelectOrg(organization);
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

		StringBuffer delItemnoBf = new StringBuffer();
		for (String itemno : itemnoList) {
			// 检查消费记录
			int count1 = costOrderDetailLogic.getCountByItemnos(itemno);
			if (count1 > 0) {
				if (isSingleDel) {
					throw new Exception("该收费项目存在消费记录，无法删除，项目编号为：" + itemno);
				}
				logger.error("该收费项目存在消费记录，无法删除，项目编号为：" + itemno);
			}
			// 退单记录不做检查，因为包含在消费记录中

			// 检查赠送项目
			int count2 = giveItemLogic.getCountByItemnos(itemno);
			if (count2 > 0) {
				if (isSingleDel) {
					throw new Exception("该收费项目作为赠送项目，无法删除，项目编号为：" + itemno);
				}
				logger.error("该收费项目作为赠送项目，无法删除，项目编号为：" + itemno);
			}
			// 赠送套餐不做检查，因为包含在赠送项目中
			// 赠送记录不做检查，因为包含在赠送项目中
			// 赠送使用记录不做检查，因为包含在赠送项目中

			// 收费套餐检查
			int count3 = tcLogic.getCountByItemnos(itemno);
			if (count3 > 0) {
				if (isSingleDel) {
					throw new Exception("该收费项目存在收费套餐中，无法删除，项目编号为：" + itemno);
				}
				logger.error("该收费项目存在收费套餐中，无法删除，项目编号为：" + itemno);
			}

			if (count1 == 0 && count2 == 0 && count3 == 0) { // 这种情况下可以删除项目
				delItemnoBf.append(itemno).append(",");
			}
		}
		// 删除消费项目
		int count = deleteByItemno(delItemnoBf.toString());
		// 记录日志
		BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_TREATITEM, delItemnoBf.toString(), TableNameUtil.KQDS_TREATITEM, request);

		return count;
	}

	/**
	 * 根据编号删除消费项目
	 * 
	 * @param conn
	 * @param itemnos
	 * @throws Exception
	 */
	public int deleteByItemno(String itemnos) throws Exception {
		return (int) dao.delete(TableNameUtil.KQDS_TREATITEM + ".deleteByItemno", YZUtility.ConvertStringIds4Query(itemnos));
	}

	@SuppressWarnings("unchecked")
	public List<String> getItemnosBySeqIds(String seqIds) throws Exception {
		List<String> itemnoList = new ArrayList<String>();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".getItemnosBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
		for (JSONObject json : list) {
			String treatitemno = json.getString("treatitemno");
			if (YZUtility.isNullorEmpty(treatitemno)) {
				continue;
			}
			itemnoList.add(treatitemno);
		}
		return itemnoList;
	}

	/**
	 * 获取所有收费项目列表
	 * 
	 * @param dbConn
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getAllsfxmSelect(String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".getAllsfxmSelect", organization);
		return list;
	}

	/**
	 * 获取所有收费项目列表，根据门诊编号
	 * 
	 * @param dbConn
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getAllsfxmSelectOrg(String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".getAllsfxmSelectOrg", organization);
		return list;
	}

	/**
	 * 根据收费项目一、二级分类，获取收费项目列表
	 * 
	 * @param dbConn
	 * @param basetype
	 * @param nexttype
	 * @param organization
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getSfxmSelectParam(String basetype, String nexttype, String organization) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("basetype", basetype);
		map1.put("nexttype", nexttype);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".getSfxmSelectParam", map1);
		return list;
	}

	/**
	 * 根据二级分类，获取收费项目列表
	 * 
	 * @param dbConn
	 * @param nexttype
	 * @param search
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsTreatitem> getTreatItemListByNextType(String nexttype, String search, String noyjj) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(search)) {
			map1.put("treatitemname", search);
		}
		if (!YZUtility.isNullorEmpty(noyjj)) {
			map1.put("isyjjitem", noyjj);
		}
		map1.put("nexttype", nexttype);

		List<KqdsTreatitem> list = (List<KqdsTreatitem>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".getTreatItemListByNextType", map1);
		for (KqdsTreatitem kqds_TreatItem : list) {
			kqds_TreatItem.setTreatitemname(kqds_TreatItem.getTreatitemname() + "【￥" + kqds_TreatItem.getUnitprice() + "】");
		}
		return list;
	}

	/**
	 * 根据一二级分类及名称，查询消费项目
	 * 
	 * @param dbConn
	 * @param basetype
	 * @param nexttype
	 * @param itemname
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public KqdsTreatitem getTreatItem(String basetype, String nexttype, String itemname) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(itemname)) {
			map1.put("itemname", itemname);
		}
		map1.put("basetype", basetype);
		map1.put("nexttype", nexttype);

		List<KqdsTreatitem> list = (List<KqdsTreatitem>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".getTreatItem", map1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据二级分类，统计收费项目数量
	 * 
	 * @param dbConn
	 * @param nexttype
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public int getCountByNextType(String nexttype, String search) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(search)) {
			map1.put("treatitemname", search);
		}
		map1.put("nexttype", nexttype);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_TREATITEM + ".getCountByNextType", map1);
		return count;
	}

	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		for (JSONObject obj : list) {
			String istsxm = obj.getString("istsxm");
			if (istsxm != null && istsxm.equals("1")) {
				obj.put("istsxm", "是");
			} else {
				obj.put("istsxm", "否");
			}
			String issplit = obj.getString("issplit");
			if (issplit != null && issplit.equals("1")) {
				obj.put("issplit", "是");
			} else {
				obj.put("issplit", "否");
			}
			String useflag = obj.getString("useflag");
			if (useflag != null && useflag.equals("1")) {
				obj.put("useflag", "是");
			} else {
				obj.put("useflag", "否");
			}
			String isyjjitem = obj.getString("isyjjitem");
			if (!YZUtility.isNullorEmpty(isyjjitem)) {
				if (isyjjitem.equals(ConstUtil.COST_ITEM_1)) {
					obj.put("isyjjitem", "预交金");
				} else if (isyjjitem.equals(ConstUtil.COST_ITEM_2)) {
					obj.put("isyjjitem", "挂号");
				} else if (isyjjitem.equals(ConstUtil.COST_ITEM_3)) {
					obj.put("isyjjitem", "治疗费");
				} else if (isyjjitem.equals(ConstUtil.COST_ITEM_4)) {
					obj.put("isyjjitem", "生成回访");
				} else {
					obj.put("isyjjitem", "");
				}
			}
		}
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 新增赠送页面调用
	 * 
	 * @param dbConn
	 * @param querydata
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getItemList(String querydata, String organization) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(querydata)) {
			map1.put("querydata", querydata);
		}
		map1.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".getItemList", map1);
		return list;
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
	@SuppressWarnings("unchecked")
	public String checkNextType(String typename, String baseno) throws Exception {
		String seq_id = "";
		Map<String, String> map1 = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(typename)) {
			map1.put("typename", typename);
		}
		if (!YZUtility.isNullorEmpty(baseno)) {
			map1.put("baseno", baseno);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".checkNextType", map1);
		if (list != null && list.size() > 0) {
			seq_id = list.get(0).getString("seq_id");
		}
		return seq_id;
	}

	/**
	 * 根据消费项目编号查询
	 * 
	 * @param conn
	 * @param itemno
	 * @return
	 * @throws Exception
	 */
	public int countByTreatItemno(String itemno) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_TREATITEM + ".countByTreatItemno", itemno);
		return count;
	}

	/**
	 * 根据编号查询收费项目
	 * 
	 * @param conn
	 * @param itemno
	 * @return
	 * @throws Exception
	 */
	public KqdsTreatitem getByTreatItemno(String itemno) throws Exception {
		KqdsTreatitem item = (KqdsTreatitem) dao.findForObject(TableNameUtil.KQDS_TREATITEM + ".getByTreatItemno", itemno);
		return item;
	}
	
	/**
	 * ############################查询收费项目表的所有项目信息###############################
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsTreatitem> getTreatItemInfroList(Collection collection) throws Exception{
		List<KqdsTreatitem> list = (List<KqdsTreatitem>) dao.findForList(TableNameUtil.KQDS_TREATITEM + ".getTreatItemInfroList", collection);
		return list;
	}
	
	/**
	 * 药品停用
	 * @param id
	 * @throws Exception
	 */
	public void changeDrugsUserflag(String id) throws Exception {
		dao.update("KQDS_TREATITEM.changeDrugsUserflag", id);
	}
	
	/**
	 * 恢复药品使用
	 * @param id
	 * @throws Exception
	 */
	public void recoverDrugsUserflag(String id) throws Exception {
		dao.update("KQDS_TREATITEM.recoverDrugsUserflag", id);
	}
}