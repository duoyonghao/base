package com.kqds.service.base.medicalRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordCz;
import com.kqds.entity.base.KqdsMedicalrecordFz;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.bus.BLUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
@Service
public class KQDS_MedicalRecordLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/*************************** 从yzutility类中整理过来 ****************************/

	/**
	 * 根据病历号获取初诊信息
	 * 
	 * @param usercode
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public KqdsMedicalrecordCz getCZBingLiByMeid(String meid) throws Exception {

		Map map = new HashMap();
		map.put("meid", meid); // 病历号
		List<KqdsMedicalrecordCz> list = (List<KqdsMedicalrecordCz>) dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_CZ, map);

		if (list != null && list.size() > 1) { // 确保只能查出来一条记录
			throw new Exception("数据异常，一个编号对应多个初诊记录");
		}
		return list.get(0);
	}

	/**
	 * 根据病历号获取复诊信息
	 * 
	 * @param usercode
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public KqdsMedicalrecordFz getFZBingLiByMeid(String meid) throws Exception {

		Map map = new HashMap();
		map.put("meid", meid); // 病历号
		List<KqdsMedicalrecordFz> list = (List<KqdsMedicalrecordFz>) dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_FZ, map);

		if (list != null && list.size() > 1) { // 确保只能查出来一条记录
			throw new Exception("数据异常，一个编号对应多个初诊记录");
		}
		return list.get(0);
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectWithPageByUsercodeNopage(String table, Map<String, String> map, String visualstaff, String organization,BootStrapPage bp) throws Exception {
		map.put("starttime", map.get("starttime") + ConstUtil.TIME_START);
		map.put("endtime", map.get("endtime") + ConstUtil.TIME_END);
		if (map.containsKey("searchvalue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("userd.PhoneNumber1", map.get("searchvalue")));
			map.put("p2", YZAuthenticator.phonenumberLike("userd.PhoneNumber2", map.get("searchvalue")));
		}
		if (map != null && map.size() > 0 && map.containsKey("m.createuser")) {

		} else {
			map.put("visualstaff", visualstaff);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if(map.get("sortName")!=null&&!map.get("sortName").equals("")){
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "m.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "userd.username");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "userd.phonenumber1");
			}
			if(map.get("sortName").equals("docdept")){
				map.put("sortName", "d.dept_name");
			}
			if(map.get("sortName").equals("createuser")){
				map.put("sortName", "p.user_name");
			}
			if(map.get("sortName").equals("mtypename")){
				map.put("sortName", "m.mtype");
			}
			if(map.get("sortName").equals("blfl")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("bc")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "m.createtime");
			}
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEDICALRECORD + ".selectWithPageByUsercodeNopage", map);
		for (JSONObject job : list) {
			String status = job.getString("status");
			if ("1".equals(status)) {
				status = "暂存";
			} else {
				status = "已确认";
			}
			job.put("status", status);

			String mtype = job.getString("mtype");
			if (BLUtil.MTYPE_0.equals(mtype)) {
				mtype = "初诊";
			}
			if (BLUtil.MTYPE_1.equals(mtype)) {
				mtype = "复诊";
			}
			if (BLUtil.MTYPE_2.equals(mtype)) {
				mtype = "种植一期";
			}
			if (BLUtil.MTYPE_3.equals(mtype)) {
				mtype = "种植复查";
			}
			if (BLUtil.MTYPE_4.equals(mtype)) {
				mtype = "种植二期";
			}
			if (BLUtil.MTYPE_5.equals(mtype)) {
				mtype = "种植修复";
			}
			job.put("mtypename", mtype);
		}
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();	
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);	
		return jobj;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectWithPageByUsercode(String table, Map<String, String> map) throws Exception {
		if (map.containsKey("status")) {
			map.put("status", YZUtility.ConvertStringIds4Query(map.get("status")));
		}
		if (map.containsKey("mtype")) {
			map.put("mtype", YZUtility.ConvertStringIds4Query(map.get("mtype")));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEDICALRECORD + ".selectWithPageByUsercode", map);
		return list;
	}

	/**
	 * 获取病例【轻松牙医 修改病历的医生】
	 * 
	 * @param dbConn
	 * @param dictCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public KqdsMedicalrecord getMedital(String usercode, String sftime) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		map.put("sftime", sftime);
		KqdsMedicalrecord medical = null;
		List<KqdsMedicalrecord> list = (List<KqdsMedicalrecord>) dao.findForList(TableNameUtil.KQDS_MEDICALRECORD + ".getMedital", map);
		if (list.size() > 1) {
			medical = list.get(0);
		}

		return medical;
	}

	public String getMaxBLCode(String table, String organization) throws Exception {
		String maxblcode = "";
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_MEDICALRECORD + ".getMaxBLCode", organization);
		if (json != null && json.containsKey("maxblcode")) {
			maxblcode = json.getString("maxblcode");
		}
		return maxblcode;
	}
}