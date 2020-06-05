package com.kqds.service.base.visit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.base.VisitDataCount;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.UserPrivUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_VisitLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 不分页
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList(String table, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT + ".selectList", map);
		return list;
	}
	/**
	 * 分页
	 * <p>Title: selectList</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月9日 
	 * @param bp
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectList(BootStrapPage bp,String table, Map<String, String> map) throws Exception {
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("status")){
				map.put("sortName", "t.status");
			}
			if(map.get("sortName").equals("visittime")){
				map.put("sortName", "t.visittime");
			}
			if(map.get("sortName").equals("visitorname")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "t.usercode");
			}
			if(map.get("sortName").equals("telphone")){
				map.put("sortName", "t.telphone");
			}
			if(map.get("sortName").equals("sex")){
				map.put("sortName", "t.sex");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "t.username");
			}
			if(map.get("sortName").equals("hfflname")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("hfyd")){
				map.put("sortName", "t.hfyd");
			}
			if(map.get("sortName").equals("postpersonname")){
				map.put("sortName", "per2.user_name");
			}
			if(map.get("sortName").equals("hfresult")){
				map.put("sortName", "t.hfresult");
			}
			if(map.get("sortName").equals("finishtime")){
				map.put("sortName", "t.finishtime");
			}
			if(map.get("sortName").equals("mydname")){
				map.put("sortName", "kcit2.dict_name");
			}
		}
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT + ".selectList1", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
	// 首页-今日回访
	@SuppressWarnings("unchecked")
	public JSONObject selectListMyToday(String table, String visualstaff, String organization,BootStrapPage bp,Map<String,String> map) throws Exception {
		//Map<String, String> map = new HashMap<String, String>();
		JSONObject json = new JSONObject();
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", visualstaff);
		}
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("status")){
				map.put("sortName", "t.status");
			}
			if(map.get("sortName").equals("visittime")){
				map.put("sortName", "t.visittime");
			}
			if(map.get("sortName").equals("visitorname")){
				map.put("sortName", "per1.user_name");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "t.usercode");
			}
			if(map.get("sortName").equals("hfflname")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("hfyd")){
				map.put("sortName", "t.hfyd");
			}
			if(map.get("sortName").equals("postpersonname")){
				map.put("sortName", "per2.user_name");
			}
			if(map.get("sortName").equals("hfresult")){
				map.put("sortName", "t.hfresult");
			}
			if(map.get("sortName").equals("mydname")){
				map.put("sortName", "kcit2.dict_name");
			}
		}
		map.put("organization", organization);
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		@SuppressWarnings("unchecked")
//---------------------------------------------------------------
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT + ".selectListMyToday", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();	
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
//-----------------------------------------------------------------------------------------------------------------------
		return jobj;
	}

	/**
	 * 不分页查询，只在客服中心-回访中心页面被调用一次
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject selectList4returnVisit(String table,BootStrapPage bp, Map<String, String> map, String visualstaff, String organization,JSONObject json,String flag) throws Exception {
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", visualstaff);
		}
		if (map.containsKey("phonenumber1")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("phonenumber1")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("phonenumber1")));
		}
		if (!YZUtility.isNullorEmpty(flag)){
			map.put("flag", flag);
		}
//-------------------------------------------------------------------------------------------------------------------------------
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		if(map.get("sortName")!=null){
			String sortName=map.get("sortName");
			if(sortName.equals("username")){
				map.put("sortName", "u.username");
			}
			if(sortName.equals("sex")){
				map.put("sortName", "u.sex");
			}
			if(sortName.equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			if(sortName.equals("phonenumber2")){
				map.put("sortName", "u.phonenumber2");
			}
			if(sortName.equals("memberno")){
				map.put("sortName", "(select count(1) from KQDS_Member where usercode =v.usercode ) ");
			}
			if(sortName.equals("status")){
				map.put("sortName", "v.status");
			}
			if(sortName.equals("visittime")){
				map.put("sortName", "v.visittime");
			}
			if(sortName.equals("myd")){
				map.put("sortName", "okdi1.dict_name");
			}
			if(sortName.equals("visitdeptname")){
				map.put("sortName", "dept2.DEPT_NAME");
			}
			if(sortName.equals("visitor1")){
				map.put("sortName", "ps.user_name");
			}
			if(sortName.equals("hffl1")){
				map.put("sortName", "v.hffl");
			}
			if(sortName.equals("hfyd")){
				map.put("sortName", "v.hfyd");
			}
			if(sortName.equals("hfresult")){
				map.put("sortName", "v.hfresult");
			}
			if(sortName.equals("finishtime")){
				map.put("sortName", "v.finishtime");
			}
			if(sortName.equals("usercode")){
				map.put("sortName", "v.usercode");
			}
			if(sortName.equals("jdr")){
				map.put("sortName", "ps2.user_name");
			}
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		// 分页插件后的查询，会自动进行分页
		@SuppressWarnings("unchecked")
//--------------------------------------------------------------------------------------------------------------------------------
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT + ".selectList4returnVisit", map);
//--------------------------------------------------------------------------------------------------------------------------------
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();		
//-------------------------------------------------------------------------------------------------------------------------
		if (YZUtility.isNullorEmpty(flag)){
			StringBuilder str= new StringBuilder();
			StringBuilder usercodes= new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				String doorstatus = list.get(i).getString("doorstatus");
				if (ConstUtil.DOOR_STATUS_1.equals(doorstatus)) {
					doorstatus = ConstUtil.DOOR_STATUS_1_DESC;
				} else {
					doorstatus = ConstUtil.DOOR_STATUS_0_DESC;
				}
				list.get(i).put("doorstatus", doorstatus);

				String status = list.get(i).getString("status");
				if ("0".equals(status)) {
					status = "未回访";
				} else {
					status = "已回访";
				}
				list.get(i).put("status", status);
				if(i==list.size()-1){
					str.append("\'"+list.get(i).getString("seq_id")+"\'");
					usercodes.append("\'"+list.get(i).getString("usercode")+"\'");
				}else{
					str.append("\'"+list.get(i).getString("seq_id")+"\',");
					usercodes.append("\'"+list.get(i).getString("usercode")+"\',");
				}
				list.get(i).put("myd", "");
				list.get(i).put("hffl1", "");
				list.get(i).put("visitdeptname", "");
				list.get(i).put("visitor1", "");
			}
			//查询名称
			if(usercodes.length()>0){
				map.put("usercode", usercodes.toString());
				List<JSONObject> membernoList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT + ".selectMembernoByUsercode", map);
				if(membernoList.size()>0){
					for (JSONObject jsonObject : list) {
						for (JSONObject jsonObject1 : membernoList) {
							if(jsonObject.getString("usercode").equals(jsonObject1.getString("usercode"))){
								if(!jsonObject1.getString("num").equals("0")){
									jsonObject.put("memberno", "是");
								}else{
									jsonObject.put("memberno", "否");
								}
							}
						}
					}
				}
			}
			//查询名称
			if(str.length()>0){
				map.put("seqid", str.toString());
				List<JSONObject> nameList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT + ".selectNameBySeqid", map);
				if(nameList.size()>0){
					for (JSONObject jsonObject : list) {
						for (JSONObject jsonObject1 : nameList) {
							if(jsonObject.getString("seq_id").equals(jsonObject1.getString("vseqid"))){
								if(jsonObject1.getString("myd")!=null){
									jsonObject.put("myd", jsonObject1.getString("myd"));
								}
								if(jsonObject1.getString("hffl1")!=null){
									jsonObject.put("hffl1", jsonObject1.getString("hffl1"));
								}
								if(jsonObject1.getString("visitdeptname")!=null){
									jsonObject.put("visitdeptname", jsonObject1.getString("visitdeptname"));
								}
								if(jsonObject1.getString("visitor1")!=null){
									jsonObject.put("visitor1", jsonObject1.getString("visitor1"));
								}
							}
						}
					}
				}
			}
		}else{
			for (int i = 0; i < list.size(); i++) {
				String memberno = list.get(i).getString("memberno");
				if (!"0".equals(memberno)) {
					memberno = "是";
				} else {
					memberno = "否";
				}
				list.get(i).put("memberno", memberno);
				String doorstatus = list.get(i).getString("doorstatus");
				if (ConstUtil.DOOR_STATUS_1.equals(doorstatus)) {
					doorstatus = ConstUtil.DOOR_STATUS_1_DESC;
				} else {
					doorstatus = ConstUtil.DOOR_STATUS_0_DESC;
				}
				list.get(i).put("doorstatus", doorstatus);

				String status = list.get(i).getString("status");
				if ("0".equals(status)) {
					status = "未回访";
				} else {
					status = "已回访";
				}
				list.get(i).put("status", status);
			}
		}
//-----------------------------------------------------------------------------------------------------------
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 查询各类条数
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<VisitDataCount> selectCountByQuery(String table, Map<String, String> map, String visualstaff, String organization) throws Exception {
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", visualstaff);
		}
		if (map.containsKey("phonenumber1")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("phonenumber1")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("phonenumber1")));
		}
		List<JSONObject> jsonList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT + ".selectCountByQuery", map);
		List<VisitDataCount> list = new ArrayList<VisitDataCount>();
		for (JSONObject json : jsonList) {
			VisitDataCount d = new VisitDataCount();
			d.setName(json.getString("dict_name"));
			d.setCount(json.getString("flcount"));
			d.setHffl(json.getString("hffl"));
			list.add(d);
		}
		return list;
	}

	/**
	 * 查询各类条数
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getCountByQuery(String table, Map<String, String> map, String visualstaff, String organization) throws Exception {
		int sum = 0;
		map.put("organization", organization);
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			map.put("visualstaff", visualstaff);
		}
		if (map.containsKey("phonenumber1")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("phonenumber1")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("phonenumber1")));
		}
		sum = (int) dao.findForObject(TableNameUtil.KQDS_VISIT + ".getCountByQuery", map);
		return sum;
	}

	// 首页查询网电预约总条数
	public int getCountIndex(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		int count = 0;
		String currDate = YZUtility.getDateStr(new Date()); // 当天的日期 yyyy-MM-dd
		if (person.getUserPriv().equals(UserPrivUtil.ADMIN_SEQ_ID)) {// 总经理看所有
			visualstaff = "";
		}
		if (!YZUtility.isNullorEmpty(visualstaff)) {
			if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
				map.put("visualstaff", visualstaff);
			}
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		map.put("visittime", currDate);
		if (!YZUtility.isNullorEmpty(searchValue)) {
			map.put("searchValue", searchValue);
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
		}
		count = (int) dao.findForObject(TableNameUtil.KQDS_VISIT + ".getCountIndex", map);
		return count;
	}

	// 推广计划超时情况
	@SuppressWarnings("unchecked")
	public void visitTimeOut() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_VISIT + ".visitTimeOut", SQLUtil.getCurrDate());
		if (list != null && list.size() > 0) {
			Map<String, String> map = new HashMap<String, String>();
			for (JSONObject visit : list) {
				// 删除重复的提醒记录
				map.put("visitor", visit.getString("visitor"));
				map.put("usercode", visit.getString("usercode"));
				dao.delete(TableNameUtil.KQDS_VISIT + ".deletepush", map);
				// 超时任务 添加超时提醒
				PushUtil.saveTx4VisitTimeOut(visit);
			}
		}
	}

	/**
	 * 根据挂号编号，删除就诊情况
	 * 
	 * @param dbConn
	 * @param regseqId
	 * @return
	 * @throws Exception
	 */
	public int deleteBySeqId(String seqId, HttpServletRequest request) throws Exception {
		// ### 删掉之前未填写就诊情况的数据，填写了的就不删了，根据就诊目的是否有值进行判断
		return (int) dao.delete(TableNameUtil.KQDS_VISIT + ".deletevisit", seqId);
	}
	
/**
 * 保存回访数据
 */
	public void saveList(List<KqdsVisit> visitList) throws Exception {
		dao.batchUpdate(TableNameUtil.KQDS_VISIT + ".saveList", visitList);
	}
}