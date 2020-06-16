package com.kqds.service.base.activityRecord;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_Activity_RecordLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private YZPersonLogic personLogic;

	/**
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @param starttime
	 * @param endtime
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectNoPage(String table, Map<String, String> map, String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_ACTIVITY_RECORD + ".selectNoPage", map);
		for (JSONObject job : list) {
			String activitycontacts = job.getString("activitycontacts");
			String data = personLogic.getNameStrBySeqIds(activitycontacts);
			job.put("activitycontactsname", data);
		}
		return list;
	}

	/**
	 * 投入产出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectTrscColumn(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_ACTIVITY_RECORD + ".selectTrscColumn", map);
		return list;
	}
}