package com.kqds.service.sys.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.base.KqdsCkGoodsInDetail;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.other.CacheUtil;

import net.sf.json.JSONObject;


@Service
public class BaseLogic {

	@Autowired
	private DaoSupport dao;

	public Object loadObjSingleUUID(String tableName, String seqId) throws Exception {
		return dao.loadObjSingleUUID(tableName, seqId);
	}
	public Object deleteSingleUUID(String tableName, String seqId) throws Exception {
		int obj = dao.deleteSingleUUID(tableName, seqId);
		return obj;
	}
	@SuppressWarnings("unchecked")
	public void saveSingleUUID(String tableName, Object obj) throws Exception {
		dao.saveSingleUUID(tableName, obj);
	}

	public Object loadList(String tableName, Map<String, String> filter) throws Exception {
		return dao.loadList(tableName, filter);
	}

	@SuppressWarnings("rawtypes")
	public int selectCount(String tableName, Map map) throws Exception {
		int count = (int) dao.selectCount(tableName, map);
		return count;
	}
	public void updateSingleUUID(String tableName, Object dp) throws Exception {
		dao.updateSingleUUID(tableName, dp);
	}

	public Object loadList4One(String tableName, Map<String, String> filter) throws Exception {
		return dao.loadList4One(tableName, filter);
	}
}
