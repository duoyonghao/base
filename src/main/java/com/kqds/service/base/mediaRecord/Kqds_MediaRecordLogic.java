package com.kqds.service.base.mediaRecord;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class Kqds_MediaRecordLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<JSONObject> selectNoPage(String table, Map<String, String> map, String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEDIA_RECORD + ".selectNoPage", map);
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
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEDIA_RECORD + ".selectTrscColumn", map);
		return list;
	}
}