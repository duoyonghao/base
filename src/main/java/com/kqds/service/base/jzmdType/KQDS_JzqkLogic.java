package com.kqds.service.base.jzmdType;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsJzqk;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_JzqkLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 保存就诊情况
	 * 
	 * @param usercode
	 * @param dbConn
	 * @param doctor
	 * @param regno
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void saveJzqk(String doctor, String regno, YZPerson person, HttpServletRequest request) throws Exception {
		// ### 对就诊情况数据进行处理 ### ----------------------------start
		// ### 删掉之前该医生未填写就诊情况的数据，填写了的就不删了，根据就诊目的是否有值进行判断
		Map<String, String> map = new HashMap<String, String>();
		map.put("regno", regno);
		map.put("doctor", doctor);
		dao.delete(TableNameUtil.KQDS_JZQK + ".deletejzqk", map);

		KqdsJzqk jzqk = new KqdsJzqk();
		jzqk.setSeqId(YZUtility.getUUID());
		jzqk.setRegno(regno);
		jzqk.setDoctor(doctor);
		jzqk.setCreatetime(YZUtility.getCurDateTimeStr());
		jzqk.setCreateuser(person.getSeqId());
		jzqk.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
																		// 【前端调用，以当前所在门诊为主】
		dao.saveSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
	}

	/**
	 * 判断该挂号对应的就诊情况是否已经填写
	 * 
	 * @param dbConn
	 * @param regseqId
	 * @return
	 * @throws Exception
	 */
	public int countJzqkByRegNo(String regseqId) throws Exception {
		int num = (int) dao.findForObject(TableNameUtil.KQDS_JZQK + ".countJzqkByRegNo", regseqId);
		return num;
	}

	/**
	 * 根据挂号编号，删除就诊情况
	 * 
	 * @param dbConn
	 * @param regseqId
	 * @return
	 * @throws Exception
	 */
	public int deleteByRegNo(String regseqId, HttpServletRequest request) throws Exception {
		// ### 删掉之前未填写就诊情况的数据，填写了的就不删了，根据就诊目的是否有值进行判断
		return (int) dao.delete(TableNameUtil.KQDS_JZQK + ".deletejzqkregno", regseqId);
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param usercode
	 * @param doctor
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> jzFz(String usercode, String doctor) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("doctor", doctor);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_JZQK + ".jzFz", map);
		return list;
	}

	// 就诊情况
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectJzqk(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization) throws Exception {
		if (map.containsKey("usercodeorname")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("usercodeorname")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("usercodeorname")));
		}
		if (YZUtility.isNotNullOrEmpty(organization)) {
			map.put("organization", organization);
		}
		map.put("visualstaff", visualstaff);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_JZQK + ".selectJzqk", map);
		return list;
	}
	
	// 就诊情况
		@SuppressWarnings("unchecked")
		public List<JSONObject> selectJzqkByUsercodes(String usercodes) throws Exception {
			List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_JZQK + ".selectJzqkByUsercodes", usercodes);
			return list;
		}
}