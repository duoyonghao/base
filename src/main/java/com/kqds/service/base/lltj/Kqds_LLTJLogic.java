package com.kqds.service.base.lltj;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class Kqds_LLTJLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_CostOrder_DetailLogic detailLogic;

	/*******************************************************************
	 * 领料统计相关 start
	 ********************************************************************************/

	/**
	 * 查询治疗明细，供领料统计使用
	 * 
	 * @param conn
	 * @param person
	 * @param map
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List selectTreatDetailListNopage(YZPerson person, Map<String, String> map, String visualstaff, String organization) throws Exception {
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}
		map.put("visualstaff", visualstaff);
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_LLTJ + ".selectTreatDetailListNopage", map);
		for (JSONObject json : list) {
			Map<String, String> clsNameMap = detailLogic.getClassName(json.getString("itemno"), json.getString("itemname"), organization);
			// 治疗项目一、二级分类
			json.put("classname", clsNameMap.get("basetypeName"));
			json.put("nextname", clsNameMap.get("nexttypeName"));

			json.put("cjstatus", json.getString("cjstatus").equals("0") ? "未成交" : "已成交");
			json.put("type", json.getString("type").equals("0") ? "否" : "是");
		}
		return list;

	}

	/**
	 * 查询未拆分的项目供拆分
	 * 
	 * @param conn
	 * @param table
	 * @param person
	 * @param map
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCostDetailList4LltjChaifen(String table, YZPerson person, Map<String, String> map, String organization) throws Exception {
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_LLTJ + ".getCostDetailList4LltjChaifen", map);
		return list;
	}

	/*******************************************************************
	 * 领料统计相关 END
	 ********************************************************************************/

}