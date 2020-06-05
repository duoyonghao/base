package com.kqds.service.base.giveItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.VisitDataCount;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.giveItemGiveRecord.KQDS_GiveItem_GiveRecordLogic;
import com.kqds.service.base.giveItemTc.KQDS_GiveItem_TCLogic;
import com.kqds.service.base.giveItemUseRecord.KQDS_GiveItem_UseRecordLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service
public class KQDS_Give_ItemLogic extends BaseLogic {
	private static Logger logger = LoggerFactory.getLogger(KQDS_Give_ItemLogic.class);

	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_GiveItem_TCLogic tcLogic;
	@Autowired
	private KQDS_GiveItem_GiveRecordLogic giveRecordLogic;
	@Autowired
	private KQDS_GiveItem_UseRecordLogic useRecordLogic;

	public List<JSONObject> getAllGiveItem(String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_GIVEITEM + ".getAllGiveItem", organization);
		return list;
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
		List<JSONObject> listall = getAllGiveItem(organization);
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

		List<JSONObject> itemList = getItemObjBySeqIds(seqIds);

		if (itemList.size() > 1) {
			isSingleDel = false; // 批量删除
		}

		StringBuffer delItemnoBf = new StringBuffer();
		for (JSONObject itemObj : itemList) {

			String itemno = itemObj.getString("itemno");
			String itemSeqId = itemObj.getString("seq_id");

			// 赠送套餐检查
			int count3 = tcLogic.getCountByItemnos(itemSeqId); // 这里传入的是赠送项目主键
			if (count3 > 0) {
				if (isSingleDel) {
					throw new Exception("该赠送项目存在赠送套餐中，无法删除，项目编号为：" + itemno);
				}
				logger.error("该赠送项目存在赠送套餐中，无法删除，项目编号为：" + itemno);
			}

			// 检查赠送记录
			int count1 = giveRecordLogic.getCountByItemnos(itemno);
			if (count1 > 0) {
				if (isSingleDel) {
					throw new Exception("该赠送项目存在赠送记录，无法删除，项目编号为：" + itemno);
				}
				logger.error("该赠送项目存在赠送记录，无法删除，项目编号为：" + itemno);
			}

			// 检查赠送使用记录
			int count2 = useRecordLogic.getCountByItemnos(itemno);
			if (count2 > 0) {
				if (isSingleDel) {
					throw new Exception("该赠送项目存在使用记录，无法删除，项目编号为：" + itemno);
				}
				logger.error("该赠送项目存在使用记录，无法删除，项目编号为：" + itemno);
			}

			if (count1 == 0 && count2 == 0 && count3 == 0) { // 这种情况下可以删除项目
				delItemnoBf.append(itemno).append(",");
			}
		}
		// 删除赠送项目
		int count = deleteByItemno(delItemnoBf.toString());
		// 记录日志
		BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_GIVEITEM, delItemnoBf.toString(), TableNameUtil.KQDS_GIVEITEM, request);

		return count;
	}

	/**
	 * 根据编号删除赠送项目
	 * 
	 * @param conn
	 * @param itemnos
	 * @throws Exception
	 */
	public int deleteByItemno(String itemnos) throws Exception {
		int count = (int) dao.delete(TableNameUtil.KQDS_GIVEITEM + ".deleteByItemno", YZUtility.ConvertStringIds4Query(itemnos));
		return count;
	}

	/**
	 * 根据主键查询赠送项目编号
	 * 
	 * @param conn
	 * @param seqIds
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getItemObjBySeqIds(String seqIds) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_GIVEITEM + ".getItemObjBySeqIds", YZUtility.ConvertStringIds4Query(seqIds));
		return list;
	}

	/**
	 * 根据消费项目编号统计
	 * 
	 * @param conn
	 * @param itemnos
	 * @return
	 * @throws Exception
	 */
	public int getCountByItemnos(String itemnos) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_GIVEITEM + ".getCountByItemnos", YZUtility.ConvertStringIds4Query(itemnos));
		return count;
	}

	/**
	 * 查询赠送项目基础信息表
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getGiveItemList(String organization) throws Exception {
		List<VisitDataCount> list = new ArrayList<VisitDataCount>();
		List<JSONObject> listJson = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_GIVEITEM + ".getGiveItemList", organization);
		for (JSONObject rs : listJson) {
			VisitDataCount d = new VisitDataCount();
			d.setHffl(rs.getString("seq_id"));
			d.setName(rs.getString("itemname"));
			list.add(d);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_GIVEITEM + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

}