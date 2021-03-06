package com.kqds.service.base.jzmdType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsJzmdType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class KQDS_Jzmd_TypeLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;

	public int selectCountLocal(String dictSeqId) throws Exception {
		return (int) dao.findForObject(TableNameUtil.KQDS_JZMD_TYPE + ".selectCount", dictSeqId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject selectWithPage(BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_JZMD_TYPE + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	@SuppressWarnings("unchecked")
	public List<KqdsJzmdType> getJzmdChildTypeSelect(String jzmd, String isAdd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("jzmd", jzmd);
		if (!YZUtility.isNullorEmpty(isAdd)) {
			map.put("isAdd", isAdd);
		}
		map.put("organization", organization);
		List<KqdsJzmdType> list = (List<KqdsJzmdType>) dao.findForList(TableNameUtil.KQDS_JZMD_TYPE + ".getJzmdChildTypeSelect", map);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<KqdsJzmdType> selectTxList(String reggoal, String jzmd, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("reggoal", reggoal);
		map.put("jzmd", jzmd);
		map.put("organization", organization);
		List<KqdsJzmdType> list = (List<KqdsJzmdType>) dao.findForList(TableNameUtil.KQDS_JZMD_TYPE + ".selectTxList", map);
		return list;
	}
}