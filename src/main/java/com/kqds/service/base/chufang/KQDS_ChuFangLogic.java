package com.kqds.service.base.chufang;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsChufang;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class KQDS_ChuFangLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	/**
	 * 根据费用单号查询处方数量
	 * 
	 * @param conn
	 * @param costno
	 * @return
	 * @throws Exception
	 */
	public int countCfByCostno(String costno) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_CHUFANG + ".countCfByCostno", costno);
		return count;
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
	public JSONObject searchChuFang(String table, Map<String, String> map, String visualstaff, String organization,BootStrapPage bp) throws Exception {
		map.put("starttime", map.get("starttime") + ConstUtil.TIME_START);
		map.put("endtime", map.get("endtime") + ConstUtil.TIME_END);
		if (map.containsKey("searchvalue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("userd.PhoneNumber1", map.get("searchvalue")));
			map.put("p2", YZAuthenticator.phonenumberLike("userd.PhoneNumber2", map.get("searchvalue")));
		}
		if (!map.containsKey("m.createuser")) {
			map.put("visualstaff", visualstaff);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if(!YZUtility.isNullorEmpty(map.get("sortName"))){
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "m.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "userd.username");
			}
			if(map.get("sortName").equals("itemname")){
				map.put("sortName", "m.itemname");
			}
			if(map.get("sortName").equals("cfusagename")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("cfuselevel")){
				map.put("sortName", "m.cfuselevel");
			}
			if(map.get("sortName").equals("cfusemethodname")){
				map.put("sortName", "kcit2.dict_name");
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
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "m.createtime");
			}  
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CHUFANG_DETAIL + ".searchChuFang", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj1 = new JSONObject();
		jobj1.put("total", pageInfo.getTotal());
		jobj1.put("rows", list);
		return jobj1;
	}
	
	/**
	 * 查找最大的处方单号(新增方法)
	 * @return
	 * @throws Exception
	 */
	public KqdsChufang findNextOrderNumber() throws Exception{
		KqdsChufang kqdsChufang = (KqdsChufang) dao.findForObject("KQDS_CHUFANG.findNextOrderNumber", null);
		return kqdsChufang;
	}
	
	/**
	 * 新增sql，根据主键seqid查找处方单编号
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject getRecipeCodeById(String costno) throws Exception{
		JSONObject json = (JSONObject) dao.findForObject("KQDS_CHUFANG.getRecipeCodeById", costno);
		return json;
	}
	
	/**
	 * 查询药品处方信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findChuFangInfor(Map<String, String> map) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CHUFANG_DETAIL + ".findChuFangInfor", map);
		return list;
	}
	/**
	 * 查找患者处方
	 * @param usercodes
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findChuFangByUsercodes(String usercodes) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CHUFANG+".findChuFangByUsercodes", usercodes);
		return list;
	}
	/**
	 * 查找患者处方明细
	 * @param usercodes
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findChuFangDetailByUsercodes(String usercodes) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CHUFANG_DETAIL+".findChuFangDetailByUsercodes", usercodes);
		return list;
	}
}