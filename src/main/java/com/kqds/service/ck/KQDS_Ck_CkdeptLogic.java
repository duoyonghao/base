package com.kqds.service.ck;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_Ck_CkdeptLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_DEPT + ".selectList", map);
		return list;
	}
}