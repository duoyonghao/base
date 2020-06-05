package com.kqds.service.sys.blct;

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
@Service
public class YZBlctLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	@SuppressWarnings({ "rawtypes" })
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map, String organization) throws Exception {
		JSONObject json = new JSONObject();
		json.put("organization", organization);
		json.put("querymap", map);

		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BLCT + ".selectWithPage", json);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	// 不分页
	public List<JSONObject> selectList(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BLCT + ".selectList", map);
		return list;
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
		JSONObject json = new JSONObject();
		json.put("idList", YZUtility.ConvertString2List(seqids));
		json.put("useflag", useflag);

		int count = (int) dao.update(TableNameUtil.KQDS_BLCT + ".updateFlagBySeqIds", json);

		// 记录日志
		SysLogUtil.log(SysLogUtil.UPDATE_STATUS, SysLogUtil.SYS_DICT, seqids, TableNameUtil.SYS_DICT, request);
		return count;
	}
}