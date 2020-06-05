package com.kqds.service.base.paiban;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_PaibanLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	// 不分页
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(String table, Map<String, String> map, String organization) throws Exception {
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_PAIBAN + ".selectList", map);
		return list;
	}

	/**
	 * 判断当前时间是否排过班 排过班的 不可新建 只能修改
	 */
	public int checkPaiban(Map<String, String> map, String organization) throws Exception {
		map.put("starttime", map.get("starttime").substring(0, 10));
		map.put("organization", organization);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_PAIBAN + ".checkPaiban", map);
		return count;
	}

}