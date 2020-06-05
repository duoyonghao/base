package com.kqds.service.base.integralRecord;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
@Service
public class KQDS_Integral_RecordLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(BootStrapPage bp, String table, YZPerson person, Map<String, String> map, String visualstaff) throws Exception {
		if (!YZUtility.isNullorEmpty(map.get("usercodeorname"))) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("usercodeorname")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("usercodeorname")));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_INTEGRAL_RECORD + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
}