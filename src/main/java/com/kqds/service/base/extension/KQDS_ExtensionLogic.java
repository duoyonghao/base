package com.kqds.service.base.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsExtensionType;
import com.kqds.entity.base.VisitDataCount;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
@Service
public class KQDS_ExtensionLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	// 不分页 客服中心-回访中心使用
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList2(String table, Map<String, String> map, String visualstaff) throws Exception {
		map.put("visualstaff", visualstaff);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_EXTENSION + ".selectList2", map);
		for (JSONObject job : list) {
			String memberno = job.getString("memberno");
			if (!"0".equals(memberno)) {
				memberno = "是";
			} else {
				memberno = "否";
			}
			job.put("memberno", memberno);

			String status = job.getString("status");
			if ("".equals(status) || "0".equals(status)) {
				status = "未完成";
			} else {
				status = "已完成";
			}
			job.put("status", status);

		}
		return list;
	}

	// 查询各类条数
	@SuppressWarnings("unchecked")
	public List<VisitDataCount> selectCountByQuery(String table, Map<String, String> map, String visualstaff) throws Exception {
		List<VisitDataCount> list = new ArrayList<VisitDataCount>();
		map.put("visualstaff", visualstaff);
		List<JSONObject> jsonList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_EXTENSION + ".selectCountByQuery", map);
		for (JSONObject rs : jsonList) {
			VisitDataCount d = new VisitDataCount();
			d.setJhseqId(rs.getString("extension"));
			d.setJhname(rs.getString("jhname"));
			d.setCount(String.valueOf(rs.getInt("flcount")));
			list.add(d);
		}
		return list;
	}

	// 条件查询总条数
	public int selectZongCount(String table, Map<String, String> map, String visualstaff) throws Exception {
		map.put("visualstaff", visualstaff);
		int sum = (int) dao.findForObject(TableNameUtil.KQDS_EXTENSION + ".selectZongCount", map);
		return sum;
	}

	// 根据条件查询推广计划列表
	@SuppressWarnings("unchecked")
	public List getExtensoionList(String organization, Map<String, String> map, String visualstaff) throws Exception {
		map.put("visualstaff", visualstaff);
		map.put("organization", organization);
		List<KqdsExtensionType> list = new ArrayList<KqdsExtensionType>();
		List<JSONObject> jsonlist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_EXTENSION + ".getExtensoionList", map);
		for (JSONObject rs : jsonlist) {
			KqdsExtensionType d = new KqdsExtensionType();
			d.setSeqId(rs.getString("seq_id"));
			d.setJhname(rs.getString("jhname"));
			list.add(d);
		}
		return list;
	}

	/**
	 * 查询该计划下是否还有未填写计划内容的记录 有则不改变状态 没有则把状态改为1(已完成)
	 * 
	 * @param conn
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	public int checkIsFinish(String seqId) throws Exception {
		int status = (int) dao.findForObject(TableNameUtil.KQDS_EXTENSION + ".checkIsFinish", seqId);
		return status;
	}

	// 不分页 客服中心-回访中心使用
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectDetail(String seqId) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_EXTENSION + ".selectDetail", seqId);
		for (JSONObject job : list) {
			String memberno = job.getString("memberno");
			if (!"0".equals(memberno)) {
				memberno = "是";
			} else {
				memberno = "否";
			}
			job.put("memberno", memberno);
			String status = job.getString("status");
			if ("".equals(status) || "0".equals(status)) {
				status = "未完成";
			} else {
				status = "已完成";
			}
			job.put("status", status);
		}
		return list;
	}

	// 首页 推广计划
	@SuppressWarnings("unchecked")
	public List<JSONObject> getTodayList(String currentOrganization, String visualstaff) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("visualstaff", visualstaff);
		map.put("organization", currentOrganization);
		map.put("visittime", SQLUtil.getCurrDate());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_EXTENSION + ".getTodayList", map);
		return list;
	}

	// 首页 推广计划
	public int getCountIndex(String visualstaff, String organization) throws Exception {
		int count = 0;
		List<JSONObject> list = getTodayList(organization, visualstaff);
		if (list != null && list.size() > 0) {
			count = list.size();
		}
		return count;
	}

	// 推广计划超时情况
	@SuppressWarnings("unchecked")
	public void JhTimeOut() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("visittime", SQLUtil.getCurrDate());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_EXTENSION + ".JhTimeOut", map);
		if (list != null && list.size() > 0) {
			for (JSONObject json : list) {
				// 删除重复的提醒记录
				map.put("visitor", json.getString("visitor"));
				map.put("usercode", json.getString("usercode"));
				dao.delete(TableNameUtil.KQDS_EXTENSION + ".deletepush", map);
				// 超时任务 添加超时提醒
				PushUtil.saveTx4ExtensionTimeOut(json);
			}
		}
	}
}