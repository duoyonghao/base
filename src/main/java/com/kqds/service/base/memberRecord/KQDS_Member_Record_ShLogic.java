package com.kqds.service.base.memberRecord;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class KQDS_Member_Record_ShLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	// 根据选择操作类型查询
	@SuppressWarnings("unchecked")
	public List selectListByType(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD_SH + ".selectListByType", map);
		return list;
	}
}