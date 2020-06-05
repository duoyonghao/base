package com.kqds.service.base.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;

import net.sf.json.JSONObject;

@Service("utilLogic")
public class UtilLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	public int updateUseFlag(String tableName, String useFlag, String seqId) throws Exception {

		JSONObject json = new JSONObject();
		json.put("tableName", tableName);
		json.put("useFlag", useFlag);
		json.put("seqId", seqId);

		int flag = (int) dao.update("UtilMapper.updateUseFlag", json);
		return flag;
	}

	public int selectCount(String tableName, String fieldName, String fieldValule) throws Exception {
		Map<String, String> json = new HashMap<String, String>();
		json.put("tableName", tableName);
		json.put("fieldName", fieldName);
		json.put("fieldValue", fieldValule);

		int flag = (int) dao.update("UtilMapper.selectCount", json);
		return flag;
	}

}
