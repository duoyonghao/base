package com.kqds.service.sys.fkfs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service("fkfsLogic")
public class YZFkfsLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	@SuppressWarnings("rawtypes")
	public JSONObject selectWithPage(String table, BootStrapPage bp, String organization) throws Exception {
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FKFS + ".selectWithPage", organization);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	// 不分页
	public List<JSONObject> selectList(String table, Map<String, String> filter) throws Exception {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		map.put("params", filter);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FKFS + ".selectList", map);
		return list;
	}

	public List<JSONObject> getMemberFkfsList(String table) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FKFS + ".getMemberFkfsList", null);
		return list;
	}

	public String selectSeqId4costfield(String costfield) throws Exception {
		String result = "";
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FKFS + ".selectSeqId4costfield", costfield);
		if (list != null && list.size() > 0) {
			result = list.get(0).getString("seq_id");
		}
		return result;
	}

	public String selectSeqId4Memberfield(String memberfield) throws Exception {
		String result = "";
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FKFS + ".selectSeqId4Memberfield", memberfield);
		if (list != null && list.size() > 0) {
			result = list.get(0).getString("seq_id");
		}
		return result;
	}

	public JSONObject getCostField() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FKFS + ".getCostField", null);
		JSONObject json = new JSONObject();
		for (JSONObject job : list) {
			String value = YZUtility.parseString(job.getString("payname"));
			String name = YZUtility.parseString(job.getString("costfield"));
			json.put(name, value);
		}
		return json;
	}

	public JSONObject getMemberField() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_FKFS + ".getMemberField", null);
		JSONObject json = new JSONObject();
		for (JSONObject job : list) {
			String value = YZUtility.parseString(job.getString("payname"));
			String name = YZUtility.parseString(job.getString("memberfield"));
			json.put(name, value);
		}
		return json;
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
		int count = (int) dao.update(TableNameUtil.SYS_FKFS + ".updateFlagBySeqIds", json);
		// 记录日志
		SysLogUtil.log(SysLogUtil.UPDATE_STATUS, SysLogUtil.SYS_DICT, seqids, TableNameUtil.SYS_DICT, request);
		return count;
	}
}