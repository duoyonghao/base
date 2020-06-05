package com.kqds.service.base.blk;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsBlk;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
@Service
public class KQDS_BLKLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BLK + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 前台调用
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectWithPage4Front(String table, Map<String, String> filter, String organization) throws Exception {
		if (filter.containsKey("mtype")) {
			String tmpMtype = YZUtility.ConvertStringIds4Query(filter.get("mtype"));
			filter.put("mtype", tmpMtype);
		} else {
			String tmpMtype = "'2','3','4','5'";// 种植病历相关的4个表
			filter.put("mtype", tmpMtype);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BLK + ".selectWithPage4Front", filter);
		return list;
	}

	/**
	 * 医疗中心-初复诊病历库管理
	 * 
	 * @param conn
	 * @param table
	 * @param filter
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> blkManager(String table, Map<String, String> filter, String organization) throws Exception {
		if (filter.containsKey("mtype")) {
			String tmpMtype = YZUtility.ConvertStringIds4Query(filter.get("mtype"));
			filter.put("mtype", tmpMtype);
		} else {
			String tmpMtype = "'0','1'";// 种植病历相关的4个表
			filter.put("mtype", tmpMtype);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BLK + ".blkManager", filter);
		return list;
	}

	/**
	 * 前台调用
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectWithNoPage4Front(String table, BootStrapPage bp, Map<String, String> filter, String organization) throws Exception {
		String search = bp.getSearch();
		if (!YZUtility.isNullorEmpty(search)) {
			filter.put("blname", search);
		}
		if (filter.containsKey("mtype")) {
			String tmpMtype = YZUtility.ConvertStringIds4Query(filter.get("mtype"));
			filter.put("mtype", tmpMtype);
		} else {
			String tmpMtype = "'2','3','4','5'";// 种植病历相关的4个表
			filter.put("mtype", tmpMtype);
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BLK + ".selectWithPage4Front", filter);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 
	 * @param dbConn
	 * @param class_no
	 * @param type
	 * @param person
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsBlk> getTreatItemBlk(String dictseqId, String type, String search, YZPerson person, String organization) throws Exception {
		List<KqdsBlk> list = new ArrayList<KqdsBlk>();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("blkfl", dictseqId);
		filter.put("type", type);
		if (!YZUtility.isNullorEmpty(organization)) {
			filter.put("organization", organization);
		}
		if (type.equals("1")) {
			filter.put("createuser", person.getSeqId());
		}
		if (!YZUtility.isNullorEmpty(search)) {
			filter.put("blname", search);
		}
		List<JSONObject> listJson = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_BLK + ".getTreatItemBlk", filter);
		for (JSONObject json : listJson) {
			KqdsBlk dict = new KqdsBlk();
			dict.setSeqId(json.getString("seq_id"));
			dict.setBlname(json.getString("blname"));
			dict.setMtype(json.getString("mtype"));
			list.add(dict);
		}
		return list;
	}

	public Object getBlkCzByblkid(String blkid) throws Exception {
		return dao.findForObject(TableNameUtil.KQDS_BLK + ".getBlkCzByblkid", blkid);
	}

	public Object getBlkFzByblkid(String blkid) throws Exception {
		return dao.findForObject(TableNameUtil.KQDS_BLK + ".getBlkFzByblkid", blkid);
	}

	public Object getMcz(String meid) throws Exception {
		return dao.findForObject(TableNameUtil.KQDS_BLK + ".getMcz", meid);
	}

	public Object getMfz(String meid) throws Exception {
		return dao.findForObject(TableNameUtil.KQDS_BLK + ".getMfz", meid);
	}

	public Object deleteMS(String tableName, String seqIdName, String meid) throws Exception {
		JSONObject jobj = new JSONObject();
		jobj.put("tableName", tableName);
		jobj.put("seqIdName", seqIdName);
		jobj.put("meid", meid);
		return dao.findForObject(TableNameUtil.KQDS_BLK + ".deleteMS", jobj);
	}
}