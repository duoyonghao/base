package com.kqds.service.base.scbb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.dept.dao.SysDeptPrivDao;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;

@Service
public class KQDS_ScbbLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private SysDeptPrivDao sysDeptPrivDao;
	@Autowired
	private YZDictLogic dictLogic;
	/**
	 * 院长查询（科室 营业收入消费分类占比）
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountTjDept(String table, Map<String, String> map, String organization, String costdept, String deptname) throws Exception {
		if (!YZUtility.isNullorEmpty(costdept)) {
			map.put("costdept", costdept);
		}
		map.put("organization", organization);
		List<JSONObject> jsonlist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getCountTjDept", map);
		for (JSONObject rs : jsonlist) {
			if (!YZUtility.isNullorEmpty(rs.getString("ys"))) {
				BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
				BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("ys")) ? "0" : rs.getString("ys"));
				BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
				BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
				BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
				BigDecimal realmoney = skze.add(qf).subtract(zs).subtract(djq).subtract(integral);
				rs.put("ys", realmoney);
				rs.put("xflx", rs.getString("xflx"));
				rs.put("deptname", deptname);
			}
		}
		return jsonlist;
	}

	// 营业收入
	@SuppressWarnings("unchecked")
	public String getYysr(String table, Map<String, String> map, String organization) throws Exception {
		String yysr = "0";
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysr", map);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
			BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("skze")) ? "0" : rs.getString("skze"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));
			BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
			skze = skze.add(qf).subtract(zs).subtract(djq).subtract(integral);
			// 营业收入（项目收费） = 实收金额 + 欠费金额
			yysr = KqdsBigDecimal.round(skze, 2).toString();
		}
		return yysr;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getYysrList(Map<String, String> map, String organization) throws Exception {
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrList", map);
		return list;
	}

	/**
	 * ------------------------------------------------------------------------
	 * ---------------------------
	 */
	/**
	 * 挂号分类 统计挂号人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountRegByGhfl(Map<String, String> map, String cjstatus, String recesort) throws Exception {
		if (!YZUtility.isNullorEmpty(cjstatus)) {
			map.put("cjstatus", cjstatus);
		}
		if (!YZUtility.isNullorEmpty(recesort)) {
			map.put("recesort", recesort);
		}
		if (map.containsKey("depttype")) {
			// （医生情况统计，查询总数 排除 挂号表医生为空的情况）
			if (map.get("depttype").equals("1")) {
				map.put("isdoctor", recesort);
			}
		}
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_REG + ".getCountRegByGhfl", map);
		return nums;
	}

	/**
	 * 代码优化
	 * 
	 * @param conn
	 * @param map
	 * @param cjstatus
	 * @param recesort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getListRegByGhfl(Map<String, String> map, String cjstatus, String recesort) throws Exception {
		if (!YZUtility.isNullorEmpty(cjstatus)) {
			map.put("cjstatus", cjstatus);
		}
		if (!YZUtility.isNullorEmpty(recesort)) {
			map.put("recesort", recesort);
		}
		if (map.containsKey("depttype")) {
			// （医生情况统计，查询总数 排除 挂号表医生为空的情况）
			if (map.get("depttype").equals("1")) {
				map.put("isdoctor", recesort);
			}
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getListRegByGhfl", map);
		return list;
	}

	/**
	 * 患者来源 来源人次
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountHzly(Map<String, String> map, String visualstaff, String cjstatus) throws Exception {
		if (!YZUtility.isNullorEmpty(cjstatus)) {
			map.put("cjstatus", cjstatus);
		}
		map.put("visualstaff", visualstaff);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getCountHzly", map);
		return list;
	}

	/**
	 * ------------------------------------------------------------------------
	 * ---------------------------
	 */
	/**
	 * 录单人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountJd(Map<String, String> map) throws Exception {
		map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJd", map);
		return nums;
	}

	/**
	 * 根据建档时间范围和门诊编号，统计录单人数
	 * 2019-08-15 lwg
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountJdList(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
		if(map.get("jdtime1")!=null&&map.get("jdtime2")==null){
			String date = YZUtility.getDateStr(new Date());
			String jdtime2 = date + ConstUtil.TIME_END;
			map.put("jdtime2", jdtime2);
		}
		if(map.get("yewu")!=null){
			map.put("seqId", map.get("yewu"));
		}
		if((map.get("askitem")!=null&&map.get("yytime1")==null&&map.get("yytime2")==null)&&map.get("jdtime1")!=null&&map.get("jdtime2")!=null){
			map.put("yytime1", map.get("jdtime1"));
			map.put("yytime2", map.get("jdtime2"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJdList", map);
		//Map<String, String> datamap = new HashMap<String, String>();
		//for (int i = 0; i < list.size(); i++) {
		//	JSONObject json = list.get(i);
		//	String createuser = json.getString("createuser");
		//	String num = json.getString("num");
		//	datamap.put(createuser, num);
		//}

		return list;
	}

	/**
	 * 录单人数(咨询项目)
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountJdItem(Map<String, String> map) throws Exception {
		map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(map.get("visualstaffwd")));
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJdItem", map);
		return nums;
	}

	/**
	 * 2019-08-15 lwg
	 * 录单人数(咨询项目情况统计)
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountJdItemList(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
		if(map.get("jdtime1")!=null&&map.get("jdtime2")==null){
			String date = YZUtility.getDateStr(new Date());
			String jdtime2 = date + ConstUtil.TIME_END;
			map.put("jdtime2", jdtime2);
		}
		if(map.get("yewu")!=null){
			map.put("seqId", map.get("yewu"));
		}
		if((map.get("askitem")!=null&&map.get("yytime1")==null&&map.get("yytime2")==null)&&map.get("jdtime1")!=null&&map.get("jdtime2")!=null){
			map.put("yytime1", map.get("jdtime1"));
			map.put("yytime2", map.get("jdtime2"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJdItemList", map);
		return list;
	}
	/**
	 * 2019-08-15 lwg
	 * 咨询项目统计表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountJdItemStatisticsList(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
		if(map.get("jdtime1")!=null&&map.get("jdtime2")==null){
			String date = YZUtility.getDateStr(new Date());
			String jdtime2 = date + ConstUtil.TIME_END;
			map.put("jdtime2", jdtime2);
		}
		if(map.get("yewu")!=null){
			map.put("seqId", map.get("yewu"));
		}
		if((map.get("askitem")!=null&&map.get("yytime1")==null&&map.get("yytime2")==null)&&map.get("jdtime1")!=null&&map.get("jdtime2")!=null){
			map.put("yytime1", map.get("jdtime1"));
			map.put("yytime2", map.get("jdtime2"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_USERDOCUMENT + ".getCountJdItemStatisticsList", map);
		return list;
	}
	/**
	 * 2019-08-15 lwg
	 * 咨询项目情况表 预约
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountYyItemList(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
//		if(map.get("jdtime1")!=null&&map.get("jdtime2")!=null&&map.get("yytime1")==null&&map.get("yytime2")==null){
//			if(map.get("yytime1")==null){
//				String yytime1 = map.get("jdtime1");
//				map.put("yytime1", yytime1);
//			}
//			if(map.get("yytime2")==null){
//				String yytime2 = map.get("jdtime2");
//				map.put("yytime2", yytime2);
//			}
//		}
		if(map.get("yytime1")!=null&&map.get("yytime2")==null){
			String date = YZUtility.getDateStr(new Date());
			String yytime2 = date + ConstUtil.TIME_END;
			map.put("yytime2", yytime2);
		}
		if(map.get("yewu")!=null){
			map.put("seqId", map.get("yewu"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountYyItemList", map);
		
		return list;
	}
	/**
	 * 2019-08-15 lwg
	 * 咨询项目统计表 预约
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountYyItemStatisticsList(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
//		if(map.get("jdtime1")!=null&&map.get("jdtime2")!=null&&map.get("yytime1")==null&&map.get("yytime2")==null){
//			if(map.get("yytime1")==null){
//				String yytime1 = map.get("jdtime1");
//				map.put("yytime1", yytime1);
//			}
//			if(map.get("yytime2")==null){
//				String yytime2 = map.get("jdtime2");
//				map.put("yytime2", yytime2);
//			}
//		}
		if(map.get("yytime1")!=null&&map.get("yytime2")==null){
			String date = YZUtility.getDateStr(new Date());
			String yytime2 = date + ConstUtil.TIME_END;
			map.put("yytime2", yytime2);
		}
		if(map.get("yewu")!=null){
			map.put("seqId", map.get("yewu"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountYyItemStatisticsList", map);
		
		return list;
	}
	/**
	 * 统计预约人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountYy(Map<String, String> map) throws Exception {
		if (map.containsKey("visualstaffwd")) {
			map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(map.get("visualstaffwd")));
		}
		map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountYy", map);
		return nums;
	}

	/**
	 * 统计预约人数，预约人数和到院人数没有必然联系，比如存在提前到院的情况
	 * 2019-08-15 lwg
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountYyList(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
//		if(map.get("jdtime1")!=null&&map.get("jdtime2")!=null&&map.get("yytime1")==null&&map.get("yytime2")==null){
//			if(map.get("yytime1")==null){
//				String yytime1 = map.get("jdtime1");
//				map.put("yytime1", yytime1);
//			}
//			if(map.get("yytime2")==null){
//				String yytime2 = map.get("jdtime2");
//				map.put("yytime2", yytime2);
//			}
//		}
		if(map.get("yytime1")!=null&&map.get("yytime2")==null){
			String date = YZUtility.getDateStr(new Date());
			String yytime2 = date + ConstUtil.TIME_END;
			map.put("yytime2", yytime2);
		}
		if(map.get("yewu")!=null){
			map.put("seqId", map.get("yewu"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountYyList", map);
		
		return list;
	}

	/**
	 * 2019-08-15 lwg
	 * 统计到院人数
	 * 到诊量
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountDoorstatus(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
		if(map.get("dztime1")!=null&&map.get("dztime2")==null){
			//查询区间到诊量
			String date = YZUtility.getDateStr(new Date());
			String dztime2 = date + ConstUtil.TIME_END;
			map.put("dztime2", dztime2);
		}
		if(map.get("yewu")!=null){
			map.put("seqId", map.get("yewu"));
		}
		if(map.get("dztime1")==null&&map.get("dztime2")==null&&map.get("jdtime1")!=null&&map.get("jdtime2")!=null
		||map.get("dztime1")==null&&map.get("dztime2")==null&&map.get("jdtime1")!=null&&map.get("jdtime2")==null
		||map.get("dztime1")==null&&map.get("dztime2")==null&&map.get("jdtime1")==null&&map.get("jdtime2")!=null){
			map.put("dztime1", map.get("jdtime1"));
			map.put("dztime2", map.get("jdtime2"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountDoorstatus", map);
		return list;
	}
	/**
	 * 2019-08-15 lwg
	 * 咨询项目情况统计表  统计到院人数
	 * 到诊量
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountDoorstatusItemList(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
		if(map.get("dztime1")!=null&&map.get("dztime2")==null){
			//查询区间到诊量
			String date = YZUtility.getDateStr(new Date());
			String dztime2 = date + ConstUtil.TIME_END;
			map.put("dztime2", dztime2);
		}
		if(map.get("yewu")!=null){
			map.put("seqId", map.get("yewu"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountDoorstatusItemList", map);
		return list;
	}
	
	/**
	 * 2019-08-15 lwg
	 * 咨询项目统计表  统计到院人数
	 * 到诊量
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountDoorstatusItemStatisticsList(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
		if(map.get("dztime1")!=null&&map.get("dztime2")==null){
			//查询区间到诊量
			String date = YZUtility.getDateStr(new Date());
			String dztime2 = date + ConstUtil.TIME_END;
			map.put("dztime2", dztime2);
		}
		if(map.get("yewu")!=null){
			map.put("seqId", map.get("yewu"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountDoorstatusItemStatisticsList", map);
		return list;
	}
	
	/**
	 * 预约上门人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountYysm(Map<String, String> map) throws Exception {
		if (map.containsKey("visualstaffwd")) {
			map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(map.get("visualstaffwd")));
		}

		map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountYysm2", map);
		return nums;
	}

	/**
	 * 收款（项目）
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getYysr(Map<String, String> map) throws Exception {
		map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		String yysr = "0";
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysr2", map);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0" : rs.getString("paymoney"));
			BigDecimal yjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0" : rs.getString("yjj"));
			BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0" : rs.getString("zs"));
			BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0" : rs.getString("djq"));
			BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0" : rs.getString("integral"));

			paymoney = paymoney.subtract(zs).subtract(yjj).subtract(djq).subtract(integral);
			yysr = paymoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}

		return yysr;
	}

	/**
	 * 查询优化
	 * 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getYysrList(Map<String, String> map) throws Exception {
		map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrList2", map);
		return list;
	}

	/**
	 * 网电工作量统计，不做门诊条件过滤，统计预约人数对应的消费收入
	 * 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getYysrListNoOrg(Map<String, String> map) throws Exception {
		if(map.containsKey("usercodes")){
			map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		}
		map.remove("organization");
		List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrList3", map);
		List<JSONObject> list2 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrList4", map);
		for (JSONObject jsonObject : list2) {
			for (JSONObject jsonObject1 : list1) {
				if(jsonObject.getString("seq_id").equals(jsonObject1.getString("seq_id"))){
					jsonObject.put("paymoney", new BigDecimal(jsonObject.getString("paymoney")).add(new BigDecimal(jsonObject1.getString("paymoney"))));
				}
			}
		}
		return list2;
	}
	/**
	 * 收款（预交金）
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getYysrYjjList(Map<String, String> map) throws Exception {
		map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".getYysrYjjList", map);
		return list;
	}

	/**
	 * 网电工作量统计，不做门诊条件过滤
	 * 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getYysrYjjListNoOrg(Map<String, String> map) throws Exception {
		map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		map.remove("organization");
		List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".getYysrYjjList1", map);
		List<JSONObject> list2 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".getYysrYjjList2", map);
		for (JSONObject jsonObject : list2) {
			for (JSONObject jsonObject1 : list1) {
				if(jsonObject.getString("seq_id").equals(jsonObject1.getString("seq_id"))){
					jsonObject.put("cmoney", new BigDecimal(jsonObject.getString("cmoney")).add(new BigDecimal(jsonObject1.getString("cmoney"))));
				}
			}
		}
		return list2;
	}

	/**
	 * 收款（预交金）
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getYysrYjj(Map<String, String> map) throws Exception {
		String yysr = "0";
		map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD + ".getYysrYjj", map);
		if (list != null && list.size() > 0) {
			JSONObject rs = list.get(0);
			BigDecimal paymoney = BigDecimal.ZERO;
			if (!YZUtility.isNullorEmpty(rs.getString("cmoney"))) {
				paymoney = new BigDecimal(rs.getString("cmoney"));
			}
			yysr = KqdsBigDecimal.round(paymoney, 2).toString();
		}
		return yysr;
	}

	/**
	 * 业绩查询(咨询)
	 * 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getYysrDetailList(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrDetailList", map);
		return list;
	}

	/**
	 * 业绩查询(医生)
	 * 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getYysrDetailList(Map<String, String> map, String organization) throws Exception {
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getYysrDetailList2", map);
		return list;
	}

	/**
	 * 加工金额(医生)
	 * 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getJgdetailList(Map<String, String> map, String organization) throws Exception {
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL + ".getJgdetailList", map);
		return list;
	}

	/**
	 * 加工金额(医生)
	 * 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCkDetailList(Map<String, String> map, String organization) throws Exception {
		map.put("organization", organization);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_CK_GOODS_OUT_DETAIL + ".getCkDetailList", map);
		return list;
	}

	/**
	 * 预约上门人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int getCountCj(Map<String, String> map) throws Exception {
		map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(map.get("visualstaffwd")));
		map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
		int nums = (int) dao.findForObject(TableNameUtil.KQDS_NET_ORDER + ".getCountCj2", map);
		return nums;
	}

	/**
	 * 预约上门人数
	 * 
	 * @param conn
	 * @param table
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getCountCjList(Map<String, String> map) throws Exception {
		if (YZUtility.isNotNullOrEmpty(map.get("organization"))) {
			/** 下拉框选请选择的时候，门诊编号是空值 **/
			map.put("organization2", map.get("organization"));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_NET_ORDER + ".getCountCjList", map);
		return list;
	}

	/**
	 * 咨询项目情况统计表getWdOrderPerItemtj的子方法
	 * 2019-08-15 lwg
	 * @param ldnumList
	 * @param yynumsList
	 * @param doorstatusList
	 * @param yycjnumsList
	 * @param objper
	 * @param dict
	 * @return
	 */
	public JSONObject getJSONObject4getWdOrderPerItemtj(List<JSONObject> ldnumList, List<JSONObject> yynumsList, List<JSONObject> doorstatusList, List<JSONObject> yycjnumsList,
			JSONObject objper, YZDict dict) {
		int ldnums = 0;
		int yynums = 0;
		int yysmnums = 0;
		//int cjnums = 0;

		if (objper == null && dict == null) { // 合计
			if(ldnumList!=null){
				for (JSONObject jsonObject : ldnumList) {
					ldnums+=jsonObject.getInt("ldnums");
				}
			}
			if(yynumsList!=null){
				for (JSONObject jsonObject : yynumsList) {
					yynums+=jsonObject.getInt("yynums");
				}
			}
			for (JSONObject jsonObject : doorstatusList) {
				yysmnums+=jsonObject.getInt("yysmnums");
			}
			//cjnums = yycjnumsList.size();
		} else {
			if(ldnumList!=null){
				for (int j = 0; j < ldnumList.size(); j++) {
					JSONObject jobj = ldnumList.get(j);
					if (jobj.getString("seq_id").equals(objper.getString("seqId"))) {
						if (dict == null) { // 小计
							ldnums+=jobj.getInt("ldnums");
						} else if (jobj.getString("askitem").equals(dict.getSeqId())) {
							ldnums+=jobj.getInt("ldnums");
						}
					}
				}
			}
			if(yynumsList!=null){
				for (int j = 0; j < yynumsList.size(); j++) {
					JSONObject jobj = yynumsList.get(j);
					if (jobj.getString("seq_id").equals(objper.getString("seqId"))) {
						if (dict == null) { // 小计
							yynums+=jobj.getInt("yynums");
						} else if (jobj.getString("askitem").equals(dict.getSeqId())) {
							yynums+=jobj.getInt("yynums");
						}
					}
				}
			}

			// 上门人数
			for (int j = 0; j < doorstatusList.size(); j++) {
				JSONObject jobj = doorstatusList.get(j);
				if (jobj.getString("seq_id").equals(objper.getString("seqId"))) {
					if (dict == null) { // 小计
						yysmnums+=jobj.getInt("yysmnums");
					} else if (jobj.getString("askitem").equals(dict.getSeqId())) {
						yysmnums+=jobj.getInt("yysmnums");
					}
				}
			}

			// 如果 咨询人次、预约人数、到院人数为0，不展示
			if (ldnums == 0 && yynums == 0 && yysmnums == 0) {
				return null; // 直接返回，不继续执行
			}

			// 成交人数
//			for (int j = 0; j < yycjnumsList.size(); j++) {
//				JSONObject jobj = yycjnumsList.get(j);
//				if (jobj.getString("createuser").equals(objper.getString("seqId"))) {
//					if (dict == null) { // 小计
//						cjnums++;
//					} else if (jobj.getString("askitem").equals(dict.getSeqId())) {
//						cjnums++;
//					}
//				}
//			}
		}

		Float dzl = (float) 0;// 预约成功率 = 到院人数/预约人数
		if (yynums != 0) {
			dzl = (float) yysmnums * 100 / yynums;
		}

//		Float cjl = (float) 0; // 到诊成交率 = 成交人数/到院人数
//		if (yysmnums != 0) {
//			cjl = (float) cjnums * 100 / yysmnums;
//		}

//		Float cjlzb = (float) 0; // 成交率占比 = 成交人数/总成交人数
//		if (yycjnumsList.size() != 0) {
//			cjlzb = (float) cjnums * 100 / yycjnumsList.size();
//		}

		JSONObject obj = new JSONObject();
		obj.put("ldnums", ldnums);// 录单量
		obj.put("yynums", yynums); // 预约人数
		obj.put("yysmnums", yysmnums); // 到院人数
		obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
		//obj.put("cjnums", cjnums); // 成交人数
		//obj.put("cjl", YZUtility.FloatToFixed2(cjl) + "%");
		//obj.put("cjlzb", YZUtility.FloatToFixed2(cjlzb) + "%");

		if (dict != null) {
			obj.put("username", objper.getString("userName")); // 咨询人员
			obj.put("zxxm", dict.getDictName()); // 咨询项目
		}

		return obj;
	}

	/**
	 * 预交金查询——期初金额
	 * 
	 * @param conn
	 * @param table
	 * @param costno
	 * @return
	 * @throws Exception
	 */
	public JSONObject getQcje(String starttime, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		sb.append(" sum(" + SQLUtil.convertDecimal("cmoney", 18, 2) + ") as cmoney,");
		sb.append(" sum(" + SQLUtil.convertDecimal("cgivemoney", 18, 2) + ") as cgivemoney,");
		sb.append(" sum(" + SQLUtil.convertDecimal("ctotal", 18, 2) + ") as ctotal,");
		sb.append(" sum(" + SQLUtil.convertDecimal("zzmoney", 18, 2) + ") as zzmoney,");
		sb.append(" sum(" + SQLUtil.convertDecimal("zzgivemoney", 18, 2) + ") as zzgivemoney");
		map.put("moneysql", sb.toString());
		map.put("starttime", starttime);
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}

		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".getQcje", map);
		BigDecimal cmoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" : json.getString("cmoney"));
		BigDecimal zzmoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney")) ? "0.00" : json.getString("zzmoney"));
		BigDecimal cgivemoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00" : json.getString("cgivemoney"));
		BigDecimal zzgivemoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney")) ? "0.00" : json.getString("zzgivemoney"));
		BigDecimal ctotal1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" : json.getString("ctotal"));
		JSONObject jobj = new JSONObject();
		jobj.put("money", cmoney1.add(zzmoney1));
		jobj.put("givemoney", cgivemoney1.add(zzgivemoney1));
		jobj.put("total", ctotal1.add(zzmoney1).add(zzgivemoney1));
		return jobj;
	}

	/**
	 * 预交金查询——操作金额
	 * 
	 * @param conn
	 * @param table
	 * @param costno
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCaozuoje(String type, String starttime, String endtime, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		sb.append(" sum(" + SQLUtil.convertDecimal("cmoney", 18, 2) + ") as cmoney,");
		sb.append(" sum(" + SQLUtil.convertDecimal("cgivemoney", 18, 2) + ") as cgivemoney,");
		sb.append(" sum(" + SQLUtil.convertDecimal("ctotal", 18, 2) + ") as ctotal");
		map.put("moneysql", sb.toString());
		if (!YZUtility.isNullorEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		map.put("type", type);

		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".getCaozuoje", map);
		BigDecimal cmoney2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" : json.getString("cmoney"));
		BigDecimal cgivemoney2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00" : json.getString("cgivemoney"));
		BigDecimal ctotal2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" : json.getString("ctotal"));
		JSONObject jobj = new JSONObject();
		jobj.put("money", cmoney2);
		jobj.put("givemoney", cgivemoney2);
		jobj.put("total", ctotal2);
		return jobj;
	}

	/**
	 * 预交金查询——期末金额
	 * 
	 * @param conn
	 * @param table
	 * @param costno
	 * @return
	 * @throws Exception
	 */
	public JSONObject getQmje(String endtime, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		sb.append(" sum(" + SQLUtil.convertDecimal("cmoney", 18, 2) + ") as cmoney,");
		sb.append(" sum(" + SQLUtil.convertDecimal("cgivemoney", 18, 2) + ") as cgivemoney,");
		sb.append(" sum(" + SQLUtil.convertDecimal("ctotal", 18, 2) + ") as ctotal,");
		sb.append(" sum(" + SQLUtil.convertDecimal("zzmoney", 18, 2) + ") as zzmoney,");
		sb.append(" sum(" + SQLUtil.convertDecimal("zzgivemoney", 18, 2) + ") as zzgivemoney");
		map.put("moneysql", sb.toString());
		map.put("starttime", endtime);
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}

		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_MEMBER_RECORD + ".getQmje", map);
		BigDecimal cmoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" : json.getString("cmoney"));
		BigDecimal zzmoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney")) ? "0.00" : json.getString("zzmoney"));
		BigDecimal cgivemoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00" : json.getString("cgivemoney"));
		BigDecimal zzgivemoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney")) ? "0.00" : json.getString("zzgivemoney"));
		BigDecimal ctotal7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" : json.getString("ctotal"));
		JSONObject jobj = new JSONObject();
		jobj.put("money", cmoney7.add(zzmoney7));
		jobj.put("givemoney", cgivemoney7.add(zzgivemoney7));
		jobj.put("total", ctotal7.add(zzmoney7).add(zzgivemoney7));
		return jobj;
	}

	/**
	 * 预交金查询——期初金额
	 * 
	 * @param conn
	 * @param table
	 * @param costno
	 * @return
	 * @throws Exception
	 */
	public JSONObject getHjzlqk(String starttime, String endtime, int iszl, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		if (iszl == 1) {
			map.put("iszl", "1");
		}

		JSONObject obj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".getHjzlqk", map);
		BigDecimal subtotal = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("subtotal")) ? "0.00" : obj.getString("subtotal"));
		BigDecimal voidmoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("voidmoney")) ? "0.00" : obj.getString("voidmoney"));
		JSONObject jobj = new JSONObject();
		jobj.put("ys", subtotal.subtract(voidmoney).toString());
		String payother2str = "0";
		if (!YZUtility.isNullorEmpty(obj.getString("payother2"))) {
			payother2str = obj.getString("payother2");
		}
		BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney"));
		BigDecimal payother2 = new BigDecimal(payother2str);
		BigDecimal paydjq = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq"));
		BigDecimal payintegral = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral"));
		jobj.put("paymoney", paymoney.subtract(payother2).subtract(paydjq).subtract(payintegral).toString());
		jobj.put("y2", YZUtility.isNullorEmpty(obj.getString("y2")) ? "0.00" : obj.getString("y2"));
		return jobj;
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> getUsercodes(Map map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG + ".getUserList", map);
		return list;
	}
	public BigDecimal getPerAdvance(Map perMap) throws Exception {
		BigDecimal  perAdvance = (BigDecimal ) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".getPerAdvance", perMap);
		 if(perAdvance==null){
			 perAdvance=BigDecimal.ZERO;
		 }	
		return perAdvance;
	}
	/**
	 * 查询是否是管理层
	 * @param map
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public String getSeqIdIdentifying(Map map) throws Exception {
		String list = (String) dao.findForObject(TableNameUtil.SYS_PERSON+ ".getIdentifying", map);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public String getDeptIdIdentifying(Map map) throws Exception {
		String list = (String) dao.findForObject(TableNameUtil.SYS_DEPT+ ".getDeptIdIdentifying", map);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> getDeptIdByIdentifying(Map map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.SYS_DEPT+ ".getDeptIdByIdentifying", map);
		return list;
	}
	/**
	 * 查询医生情况的数据，数据报表
	 * <p>Title: findDoctorSituationByTime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月15日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public List<JSONObject> findDoctorSituationByTime(HttpServletRequest request,Map<String,String> map) throws Exception {
		//有按钮标识和all 查询所有部门人员id
		List<JSONObject> personlist = new ArrayList<JSONObject>();
		String name="";
		String doctorId="";
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			personlist=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personlist.get(i).getString("seqid")+"\',");
				}
			}
			map.put("doctor", str.toString());
			//传入部门id 查询部门内所有人员
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			//根据部门id查询所有人员数据
			map.put("deptId", "\'"+map.get("deptCategory")+"\'");
			personlist = sysDeptPrivDao.findPersonByDeptId(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personlist.get(i).getString("seqid")+"\',");
				}
			}
			map.put("doctor", str.toString());
			//传入人员id 查询个人数据
		}else if(!map.get("personId").equals("all")){
			//查询部门内个人数据
			if(map.get("personId").equals("personage")){
				YZPerson person = SessionUtil.getLoginPerson(request);
				name=person.getUserName();
				doctorId=person.getSeqId();
				map.put("doctor", "\'"+person.getSeqId()+"\'");
			}else{
				Map<String, String> map1=new HashMap<String,String>();
				map1.put("seqId", map.get("personId"));
				YZPerson yzPerson = sysDeptPrivDao.findPersonBySeqId(map1);
				name=yzPerson.getUserName();
				doctorId=map.get("personId");
				map.put("doctor", "\'"+map.get("personId")+"\'");
			}
		}
		//查询总数量
		List<JSONObject> doctorSituationNumslist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG+ ".selectDoctorSituationNums", map);
		//查询成交数量
		List<JSONObject> doctorSituationCJNumslist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_REG+ ".selectDoctorSituationCJNums", map);
		//查询项目业绩
		List<JSONObject> doctorSituationCJMoneylist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER+ ".selectDoctorSituationMoney", map);
		//查询预交金
		List<JSONObject> doctorSituationPrepayMoneylist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD+ ".selectDoctorSituationPrepayMoney", map);
		//部门
		// 获取初诊编码
		String czseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_CZ_DESC);
		// 获取复诊编码
		String fzseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FZ_DESC);
		// 获取再消费编码
		String zxfseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_ZXF_DESC);
		// 获取复查编码
		String fcseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_FC_DESC);
		// 获取其他编码
		String qtseqId = dictLogic.getDictIdByNameAndParentCode(DictUtil.JZFL, DictUtil.JZFL_QT_DESC);
		// 获取咨询项目
		List<YZDict> listDict = dictLogic.getListByParentCodeALL("GHFL", ChainUtil.getCurrentOrganization(request));

		List<JSONObject> list=new ArrayList<JSONObject>();
		//先计算总人次数据 
		//总计 咨询人次
		int zxnums=0;
		//总计 成交人数
		int cjnums=0;
		//总计 未成交人次
		int wcjnums=0;
		//总计 初诊总人次
		int czallnums=0;
		//总计 初诊成交人次
		int czcjnums=0;
		//总计 复诊总人次
	    int fzallnums=0;
	    //总计 复诊成交人次
	    int fzcjnums=0;
	    //总计 再消费总人数
	    int zxfallnums=0;
	    //总计 再消费成交人次
	    int zxfcjnums=0;
	    //总计 复查总人次
	    int fcallnums=0;
	    //总计 复查成交人次
	    int fccjnums=0;
	    //总计 其他总人次
	    int qtallnums=0;
	    //总计 其他成交人次
	    int qtcjnums=0;
	    //总计 项目金额
	    String skje="0.00";
	    //总计 预交金
	    String totalAdvance="0.00";
	    //占总咨询数 
	    String zzlzb="100.00%";
		//成功率 成交除以总人数
	    String cgl="0.00%";
		//占总成交人数
	    String cglzb="100.00%";
	      if(doctorSituationNumslist.size()>0){
	    	  for (JSONObject jsonObject1 : doctorSituationNumslist) {
	    		zxnums+=Integer.parseInt(jsonObject1.getString("nums"));
				if(czseqId.equals(jsonObject1.getString("recesort"))){
					czallnums+=Integer.parseInt(jsonObject1.getString("nums"));
				}else if(fzseqId.equals(jsonObject1.getString("recesort"))){
					fzallnums+=Integer.parseInt(jsonObject1.getString("nums"));
				}else if(zxfseqId.equals(jsonObject1.getString("recesort"))){
					zxfallnums+=Integer.parseInt(jsonObject1.getString("nums"));
				}else if(fcseqId.equals(jsonObject1.getString("recesort"))){
					fcallnums+=Integer.parseInt(jsonObject1.getString("nums"));
				}else if(qtseqId.equals(jsonObject1.getString("recesort"))){
					qtallnums+=Integer.parseInt(jsonObject1.getString("nums"));
				}
	    	  }
	    	  
	      }
	      if(doctorSituationCJNumslist.size()>0){
	    	for(JSONObject jsonObject2 : doctorSituationCJNumslist) {
	    		cjnums+=Integer.parseInt(jsonObject2.getString("nums"));
				if(czseqId.equals(jsonObject2.getString("recesort"))){
					czcjnums+=Integer.parseInt(jsonObject2.getString("nums"));
				}else if(fzseqId.equals(jsonObject2.getString("recesort"))){
					fzcjnums+=Integer.parseInt(jsonObject2.getString("nums"));
				}else if(zxfseqId.equals(jsonObject2.getString("recesort"))){
					zxfcjnums+=Integer.parseInt(jsonObject2.getString("nums"));
				}else if(fcseqId.equals(jsonObject2.getString("recesort"))){
					fccjnums+=Integer.parseInt(jsonObject2.getString("nums"));
				}else if(qtseqId.equals(jsonObject2.getString("recesort"))){
					qtcjnums+=Integer.parseInt(jsonObject2.getString("nums"));
				}
			}
	      }
		
	      if(doctorSituationCJMoneylist.size()>0){
	    	  for (JSONObject jsonObject3 : doctorSituationCJMoneylist) {
	    		  skje=new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje)).toString();
	    	  }
	    	  
	      }
	      
	      if(doctorSituationPrepayMoneylist.size()>0){
	    	  for (JSONObject jsonObject4 : doctorSituationPrepayMoneylist) {
	    		  totalAdvance=new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance)).toString();
	    	  }
	    	  
	      }
	      //总计
	      wcjnums=zxnums-cjnums;
	      if(zxnums>0){
	    	  cgl = String.format("%.2f", Double.parseDouble(((float)cjnums/(float)zxnums)*100+""))+"%";
	      }
		if(personlist.size()>0){
			for (JSONObject jsonObject : personlist) {
				//判断当前医生是否存在数据
				//合计 咨询人次
				int zxnums1=0;
				//合计成交人数
				int cjnums1=0;
				//合计 未成交人次
				int wcjnums1=0;
				//合计 初诊总人次
				int czallnums1=0;
				//合计 初诊成交人次
				int czcjnums1=0;
				//合计 复诊总人次
			    int fzallnums1=0;
			    //合计 复诊成交人次
			    int fzcjnums1=0;
			    //合计 再消费总人数
			    int zxfallnums1=0;
			    //合计再消费成交人次
			    int zxfcjnums1=0;
			    //合计复查总人次
			    int fcallnums1=0;
			    //合计 复查成交人次
			    int fccjnums1=0;
			    //合计 其他总人次
			    int qtallnums1=0;
			    //合计 其他成交人次
			    int qtcjnums1=0;
			    //合计 项目金额
			    String skje1="0.00";
			    //合计预交金
			    String totalAdvance1="0.00";
			    //占总咨询数 
			    String zzlzb1="0.00%";
				//成功率 成交除以总人数
			    String cgl1="0.00%";
				//占总成交人数
			    String cglzb1="0.00%";
				List<JSONObject> list1=new ArrayList<JSONObject>();
				List<JSONObject> list2=new ArrayList<JSONObject>();
				List<JSONObject> list3=new ArrayList<JSONObject>();
				List<JSONObject> list4=new ArrayList<JSONObject>();
				for (JSONObject jsonObject1 : doctorSituationNumslist) {
					if(jsonObject.getString("seqid").equals(jsonObject1.getString("seqid"))){
						zxnums1+=Integer.parseInt(jsonObject1.getString("nums"));
						list1.add(jsonObject1);
						if(czseqId.equals(jsonObject1.getString("recesort"))){
							czallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
						}else if(fzseqId.equals(jsonObject1.getString("recesort"))){
							fzallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
						}else if(zxfseqId.equals(jsonObject1.getString("recesort"))){
							zxfallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
						}else if(fcseqId.equals(jsonObject1.getString("recesort"))){
							fcallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
						}else if(qtseqId.equals(jsonObject1.getString("recesort"))){
							qtallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
						}
					}
				}

				for (JSONObject jsonObject2 : doctorSituationCJNumslist) {
					if(jsonObject.getString("seqid").equals(jsonObject2.getString("seqid"))){
						cjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
						list2.add(jsonObject2);
						if(czseqId.equals(jsonObject2.getString("recesort"))){
							czcjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
						}else if(fzseqId.equals(jsonObject2.getString("recesort"))){
							fzcjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
						}else if(zxfseqId.equals(jsonObject2.getString("recesort"))){
							zxfcjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
						}else if(fcseqId.equals(jsonObject2.getString("recesort"))){
							fccjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
						}else if(qtseqId.equals(jsonObject2.getString("recesort"))){
							qtcjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
						}
					}
				}
				wcjnums1=zxnums1-cjnums1;
				for (JSONObject jsonObject3 : doctorSituationCJMoneylist) {
					if(jsonObject.getString("seqid").equals(jsonObject3.getString("seqid"))){
						skje1=new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje1)).toString();
						list3.add(jsonObject3);
					}
				}
				for (JSONObject jsonObject4 : doctorSituationPrepayMoneylist) {
					if(jsonObject.getString("seqid").equals(jsonObject4.getString("seqid"))){
						totalAdvance1=new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance1)).toString();
						list4.add(jsonObject4);
					}
				}
				if(zxnums1>0){
					cgl1 = String.format("%.2f", Double.parseDouble(""+((float)cjnums1/(float)zxnums1)*100))+"%";
				}
				if(zxnums>0){
					zzlzb1=String.format("%.2f", Double.parseDouble(""+((float)zxnums1/(float)zxnums)*100))+"%";
				}
				if(cjnums>0){
					cglzb1=String.format("%.2f", Double.parseDouble(""+((float)cjnums1/(float)cjnums)*100))+"%";
				}
				int a=0;
				for (YZDict yZDict : listDict) {
					//医生姓名
					String username=jsonObject.getString("username");
					//项目名称
				    String zxxm="";
					//咨询人次
					int zxnums2=0;
					//成交人数
					int cjnums2=0;
					//未成交人次
					int wcjnums2=0;
					//初诊总人次
					int czallnums2=0;
					//初诊成交人次
					int czcjnums2=0;
					//复诊总人次
				    int fzallnums2=0;
				    //复诊成交人次
				    int fzcjnums2=0;
				    //再消费总人数
				    int zxfallnums2=0;
				    //再消费成交人次
				    int zxfcjnums2=0;
				    //复查总人次
				    int fcallnums2=0;
				    //复查成交人次
				    int fccjnums2=0;
				    //其他总人次
				    int qtallnums2=0;
				    //其他成交人次
				    int qtcjnums2=0;
				    //项目金额
				    String skje2="0.00";
				    //预交金
				    String totalAdvance2="0.00";
				    //占总咨询数 
				    String zzlzb2="0.00%";
					//成功率 成交除以总人数
				    String cgl2="0.00%";
					//占总成交人数
				    String cglzb2="0.00%";
					if(list1.size()>0){
						for (JSONObject jsonObject1 : list1) {
							if(yZDict.getSeqId().equals(jsonObject1.getString("regsort"))){
								a=1;
								zxxm=jsonObject1.getString("regsortname");
								zxnums2+=Integer.parseInt(jsonObject1.getString("nums"));
								if(czseqId.equals(jsonObject1.getString("recesort"))){
									czallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
								}else if(fzseqId.equals(jsonObject1.getString("recesort"))){
									fzallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
								}else if(zxfseqId.equals(jsonObject1.getString("recesort"))){
									zxfallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
								}else if(fcseqId.equals(jsonObject1.getString("recesort"))){
									fcallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
								}else if(qtseqId.equals(jsonObject1.getString("recesort"))){
									qtallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
								}
							}
						}
					}
					if(list2.size()>0){
						for (JSONObject jsonObject2 : list2) {
							if(jsonObject2.getString("regsortname").equals(zxxm)&&!zxxm.equals("")){
								cjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
								if(czseqId.equals(jsonObject2.getString("recesort"))){
									czcjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
								}else if(fzseqId.equals(jsonObject2.getString("recesort"))){
									fzcjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
								}else if(zxfseqId.equals(jsonObject2.getString("recesort"))){
									zxfcjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
								}else if(fcseqId.equals(jsonObject2.getString("recesort"))){
									fccjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
								}else if(qtseqId.equals(jsonObject2.getString("recesort"))){
									qtcjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
								}
							}
						}
					}
					if(list3.size()>0){
						for (JSONObject jsonObject3 : list3) {
							if(yZDict.getSeqId().equals(jsonObject3.getString("regsort"))){
								a=1;
								zxxm=jsonObject3.getString("regsortname");
								skje2=new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje2)).toString();
							}
						}
					}
					if(list4.size()>0){
						for (JSONObject jsonObject4 : list4) {
							if(yZDict.getSeqId().equals(jsonObject4.getString("regsort"))){
								a=1;
								zxxm=jsonObject4.getString("regsortname");
								totalAdvance2=new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance2)).toString();
							}
						}
					}
					if(zxnums2>0){
						cgl2 = String.format("%.2f", Double.parseDouble(""+((float)cjnums2/(float)zxnums2)*100))+"%";
					}
					if(zxnums>0){
						zzlzb2=String.format("%.2f", Double.parseDouble(""+((float)zxnums2/(float)zxnums)*100))+"%";
					}
					if(cjnums>0){
						cglzb2=String.format("%.2f", Double.parseDouble(""+((float)cjnums2/(float)cjnums)*100))+"%";
					}
					wcjnums2=zxnums2-cjnums2;
					if(!zxxm.equals("")){
						JSONObject json = new JSONObject();
						json.put("username", username);
						json.put("zxxm", zxxm);
						json.put("zxnums", zxnums2+"");
						json.put("zzlzb", zzlzb2);
						json.put("cjnums", cjnums2+"");
						json.put("cgl", cgl2+"");
						json.put("cglzb", cglzb2+"");
						json.put("wcjnums", wcjnums2+"");
						json.put("czallnums", czallnums2+"");
						json.put("czcjnums", czcjnums2+"");
						json.put("fzallnums", fzallnums2+"");
						json.put("fzcjnums", fzcjnums2+"");
						json.put("zxfallnums", zxfallnums2+"");
						json.put("zxfcjnums", zxfcjnums2+"");
						json.put("fcallnums", fcallnums2+"");
						json.put("fccjnums", fccjnums2+"");
						json.put("qtallnums", qtallnums2+"");
						json.put("qtcjnums", qtcjnums2+"");
						json.put("skje", skje2);
						json.put("totalAdvance", totalAdvance2);
						list.add(json);
					}
				}
				
				//保存合计
				if(a>0){
					JSONObject json = new JSONObject();
					json.put("zxnums", zxnums1+"");
					json.put("zzlzb", zzlzb1);
					json.put("cjnums", cjnums1+"");
					json.put("cgl", cgl1+"");
					json.put("cglzb", cglzb1+"");
					json.put("wcjnums", wcjnums1+"");
					json.put("czallnums", czallnums1+"");
					json.put("czcjnums", czcjnums1+"");
					json.put("fzallnums", fzallnums1+"");
					json.put("fzcjnums", fzcjnums1+"");
					json.put("zxfallnums", zxfallnums1+"");
					json.put("zxfcjnums", zxfcjnums1+"");
					json.put("fcallnums", fcallnums1+"");
					json.put("fccjnums", fccjnums1+"");
					json.put("qtallnums", qtallnums1+"");
					json.put("qtcjnums", qtcjnums1+"");
					json.put("skje", skje1+"");
					json.put("totalAdvance", totalAdvance1+"");
					list.add(json);
				}
			}
		//个人
		}else{
			//判断当前医生是否存在数据
			//合计 咨询人次
			int zxnums1=0;
			//合计成交人数
			int cjnums1=0;
			//合计 未成交人次
			int wcjnums1=0;
			//合计 初诊总人次
			int czallnums1=0;
			//合计 初诊成交人次
			int czcjnums1=0;
			//合计 复诊总人次
		    int fzallnums1=0;
		    //合计 复诊成交人次
		    int fzcjnums1=0;
		    //合计 再消费总人数
		    int zxfallnums1=0;
		    //合计再消费成交人次
		    int zxfcjnums1=0;
		    //合计复查总人次
		    int fcallnums1=0;
		    //合计 复查成交人次
		    int fccjnums1=0;
		    //合计 其他总人次
		    int qtallnums1=0;
		    //合计 其他成交人次
		    int qtcjnums1=0;
		    //合计 项目金额
		    String skje1="0.00";
		    //合计预交金
		    String totalAdvance1="0.00";
		    //占总咨询数 
		    String zzlzb1="0.00%";
			//成功率 成交除以总人数
		    String cgl1="0.00%";
			//占总成交人数
		    String cglzb1="0.00%";
			List<JSONObject> list1=new ArrayList<JSONObject>();
			List<JSONObject> list2=new ArrayList<JSONObject>();
			List<JSONObject> list3=new ArrayList<JSONObject>();
			List<JSONObject> list4=new ArrayList<JSONObject>();
			for (JSONObject jsonObject1 : doctorSituationNumslist) {
				if(doctorId.equals(jsonObject1.getString("seqid"))){
					zxnums1+=Integer.parseInt(jsonObject1.getString("nums"));
					list1.add(jsonObject1);
					if(czseqId.equals(jsonObject1.getString("recesort"))){
						czallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
					}else if(fzseqId.equals(jsonObject1.getString("recesort"))){
						fzallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
					}else if(zxfseqId.equals(jsonObject1.getString("recesort"))){
						zxfallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
					}else if(fcseqId.equals(jsonObject1.getString("recesort"))){
						fcallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
					}else if(qtseqId.equals(jsonObject1.getString("recesort"))){
						qtallnums1+=Integer.parseInt(jsonObject1.getString("nums"));
					}
				}
			}

			for (JSONObject jsonObject2 : doctorSituationCJNumslist) {
				if(doctorId.equals(jsonObject2.getString("seqid"))){
					cjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
					list2.add(jsonObject2);
					if(czseqId.equals(jsonObject2.getString("recesort"))){
						czcjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
					}else if(fzseqId.equals(jsonObject2.getString("recesort"))){
						fzcjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
					}else if(zxfseqId.equals(jsonObject2.getString("recesort"))){
						zxfcjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
					}else if(fcseqId.equals(jsonObject2.getString("recesort"))){
						fccjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
					}else if(qtseqId.equals(jsonObject2.getString("recesort"))){
						qtcjnums1+=Integer.parseInt(jsonObject2.getString("nums"));
					}
				}
			}
			wcjnums1=zxnums1-cjnums1;
			for (JSONObject jsonObject3 : doctorSituationCJMoneylist) {
				if(doctorId.equals(jsonObject3.getString("seqid"))){
					skje1=new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje1)).toString();
					list3.add(jsonObject3);
				}
			}
			for (JSONObject jsonObject4 : doctorSituationPrepayMoneylist) {
				if(doctorId.equals(jsonObject4.getString("seqid"))){
					totalAdvance1=new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance1)).toString();
					list4.add(jsonObject4);
				}
			}
			if(zxnums1>0){
				cgl1 = String.format("%.2f", Double.parseDouble(""+((float)cjnums1/(float)zxnums1)*100))+"%";
			}
			if(zxnums>0){
				zzlzb1=String.format("%.2f", Double.parseDouble(""+((float)zxnums1/(float)zxnums)*100))+"%";
			}
			if(cjnums>0){
				cglzb1=String.format("%.2f", Double.parseDouble(""+((float)cjnums1/(float)cjnums)*100))+"%";
			}
			int a=0;
			for (YZDict yZDict : listDict) {
				//医生姓名
				String username=name;
				//项目名称
			    String zxxm="";
				//咨询人次
				int zxnums2=0;
				//成交人数
				int cjnums2=0;
				//未成交人次
				int wcjnums2=0;
				//初诊总人次
				int czallnums2=0;
				//初诊成交人次
				int czcjnums2=0;
				//复诊总人次
			    int fzallnums2=0;
			    //复诊成交人次
			    int fzcjnums2=0;
			    //再消费总人数
			    int zxfallnums2=0;
			    //再消费成交人次
			    int zxfcjnums2=0;
			    //复查总人次
			    int fcallnums2=0;
			    //复查成交人次
			    int fccjnums2=0;
			    //其他总人次
			    int qtallnums2=0;
			    //其他成交人次
			    int qtcjnums2=0;
			    //项目金额
			    String skje2="0.00";
			    //预交金
			    String totalAdvance2="0.00";
			    //占总咨询数 
			    String zzlzb2="0.00%";
				//成功率 成交除以总人数
			    String cgl2="0.00%";
				//占总成交人数
			    String cglzb2="0.00%";
				if(list1.size()>0){
					for (JSONObject jsonObject1 : list1) {
						if(yZDict.getSeqId().equals(jsonObject1.getString("regsort"))){
							a=1;
							zxxm=jsonObject1.getString("regsortname");
							zxnums2+=Integer.parseInt(jsonObject1.getString("nums"));
							if(czseqId.equals(jsonObject1.getString("recesort"))){
								czallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
							}else if(fzseqId.equals(jsonObject1.getString("recesort"))){
								fzallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
							}else if(zxfseqId.equals(jsonObject1.getString("recesort"))){
								zxfallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
							}else if(fcseqId.equals(jsonObject1.getString("recesort"))){
								fcallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
							}else if(qtseqId.equals(jsonObject1.getString("recesort"))){
								qtallnums2+=Integer.parseInt(jsonObject1.getString("nums"));
							}
						}
					}
				}
				if(list2.size()>0){
					for (JSONObject jsonObject2 : list2) {
						if(jsonObject2.getString("regsortname").equals(zxxm)&&!zxxm.equals("")){
							cjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
							if(czseqId.equals(jsonObject2.getString("recesort"))){
								czcjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
							}else if(fzseqId.equals(jsonObject2.getString("recesort"))){
								fzcjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
							}else if(zxfseqId.equals(jsonObject2.getString("recesort"))){
								zxfcjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
							}else if(fcseqId.equals(jsonObject2.getString("recesort"))){
								fccjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
							}else if(qtseqId.equals(jsonObject2.getString("recesort"))){
								qtcjnums2+=Integer.parseInt(jsonObject2.getString("nums"));
							}
						}
					}
				}
				if(list3.size()>0){
					for (JSONObject jsonObject3 : list3) {
						if(yZDict.getSeqId().equals(jsonObject3.getString("regsort"))){
							a=1;
							zxxm=jsonObject3.getString("regsortname");
							skje2=new BigDecimal(jsonObject3.getString("paymoney")).add(new BigDecimal(skje2)).toString();
						}
					}
				}
				if(list4.size()>0){
					for (JSONObject jsonObject4 : list4) {
						if(yZDict.getSeqId().equals(jsonObject4.getString("regsort"))){
							a=1;
							zxxm=jsonObject4.getString("regsortname");
							totalAdvance2=new BigDecimal(jsonObject4.getString("cmoney")).add(new BigDecimal(totalAdvance2)).toString();
						}
					}
				}
				if(zxnums2>0){
					cgl2 = String.format("%.2f", Double.parseDouble(""+((float)cjnums2/(float)zxnums2)*100))+"%";
				}
				if(zxnums>0){
					zzlzb2=String.format("%.2f", Double.parseDouble(""+((float)zxnums2/(float)zxnums)*100))+"%";
				}
				if(cjnums>0){
					cglzb2=String.format("%.2f", Double.parseDouble(""+((float)cjnums2/(float)cjnums)*100))+"%";
				}
				wcjnums2=zxnums2-cjnums2;
				if(!zxxm.equals("")){
					JSONObject json = new JSONObject();
					json.put("username", username);
					json.put("zxxm", zxxm);
					json.put("zxnums", zxnums2+"");
					json.put("zzlzb", zzlzb2);
					json.put("cjnums", cjnums2+"");
					json.put("cgl", cgl2+"");
					json.put("cglzb", cglzb2+"");
					json.put("wcjnums", wcjnums2+"");
					json.put("czallnums", czallnums2+"");
					json.put("czcjnums", czcjnums2+"");
					json.put("fzallnums", fzallnums2+"");
					json.put("fzcjnums", fzcjnums2+"");
					json.put("zxfallnums", zxfallnums2+"");
					json.put("zxfcjnums", zxfcjnums2+"");
					json.put("fcallnums", fcallnums2+"");
					json.put("fccjnums", fccjnums2+"");
					json.put("qtallnums", qtallnums2+"");
					json.put("qtcjnums", qtcjnums2+"");
					json.put("skje", skje2);
					json.put("totalAdvance", totalAdvance2);
					list.add(json);
				}
			}
			
			//保存合计
			if(a>0){
				JSONObject json = new JSONObject();
				json.put("zxnums", zxnums1+"");
				json.put("zzlzb", zzlzb1);
				json.put("cjnums", cjnums1+"");
				json.put("cgl", cgl1+"");
				json.put("cglzb", cglzb1+"");
				json.put("wcjnums", wcjnums1+"");
				json.put("czallnums", czallnums1+"");
				json.put("czcjnums", czcjnums1+"");
				json.put("fzallnums", fzallnums1+"");
				json.put("fzcjnums", fzcjnums1+"");
				json.put("zxfallnums", zxfallnums1+"");
				json.put("zxfcjnums", zxfcjnums1+"");
				json.put("fcallnums", fcallnums1+"");
				json.put("fccjnums", fccjnums1+"");
				json.put("qtallnums", qtallnums1+"");
				json.put("qtcjnums", qtcjnums1+"");
				json.put("skje", skje1+"");
				json.put("totalAdvance", totalAdvance1+"");
				list.add(json);
			}
		}
		if(list.size()==0){
			JSONObject json = new JSONObject();
			json.put("username", "");
			json.put("zxxm", "");
			json.put("zxnums", zxnums+"");
			json.put("zzlzb", zzlzb);
			json.put("cjnums", cjnums+"");
			json.put("cgl", cgl+"");
			json.put("cglzb", cglzb+"");
			json.put("wcjnums", wcjnums+"");
			json.put("czallnums", czallnums+"");
			json.put("czcjnums", czcjnums+"");
			json.put("fzallnums", fzallnums+"");
			json.put("fzcjnums", fzcjnums+"");
			json.put("zxfallnums", zxfallnums+"");
			json.put("zxfcjnums", zxfcjnums+"");
			json.put("fcallnums", fcallnums+"");
			json.put("fccjnums", fccjnums+"");
			json.put("qtallnums", qtallnums+"");
			json.put("qtcjnums", qtcjnums+"");
			json.put("skje", skje+"");
			json.put("totalAdvance", totalAdvance+"");
			list.add(json);
		}else{
			//保存总计
			JSONObject json = new JSONObject();
			json.put("zxnums", zxnums+"");
			json.put("zzlzb", zzlzb);
			json.put("cjnums", cjnums+"");
			json.put("cgl", cgl+"");
			json.put("cglzb", cglzb+"");
			json.put("wcjnums", wcjnums+"");
			json.put("czallnums", czallnums+"");
			json.put("czcjnums", czcjnums+"");
			json.put("fzallnums", fzallnums+"");
			json.put("fzcjnums", fzcjnums+"");
			json.put("zxfallnums", zxfallnums+"");
			json.put("zxfcjnums", zxfcjnums+"");
			json.put("fcallnums", fcallnums+"");
			json.put("fccjnums", fccjnums+"");
			json.put("qtallnums", qtallnums+"");
			json.put("qtcjnums", qtcjnums+"");
			json.put("skje", skje+"");
			json.put("totalAdvance", totalAdvance+"");
			list.add(json);
		}
		return list;
	}
	/**
	 * 根据时间查询医生的业绩情况
	 * <p>Title: findDoctorPerformanceByTime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月16日 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findDoctorPerformanceByTime(HttpServletRequest request,Map<String,String> map) throws Exception {
		//有按钮标识和all 查询所有部门人员id
		List<JSONObject> personlist = new ArrayList<JSONObject>();
		String name="";
		String doctorId="";
		if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			personlist=sysDeptPrivDao.findPersonSeqIdByButtonName(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personlist.get(i).getString("seqid")+"\',");
				}
			}
			map.put("doctor", str.toString());
			//传入部门id 查询部门内所有人员
		}else if(!YZUtility.isNullorEmpty(map.get("buttonName"))&&!map.get("deptCategory").equals("all")&&map.get("personId").equals("all")){
			//根据部门id查询所有人员数据
			map.put("deptId", "\'"+map.get("deptCategory")+"\'");
			personlist = sysDeptPrivDao.findPersonByDeptId(map);
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < personlist.size(); i++) {
				if(i==personlist.size()-1){
					str.append("\'"+personlist.get(i).getString("seqid")+"\'");
				}else{
					str.append("\'"+personlist.get(i).getString("seqid")+"\',");
				}
			}
			map.put("doctor", str.toString());
			//传入人员id 查询个人数据
		}else if(!map.get("personId").equals("all")){
			//查询部门内个人数据
			if(map.get("personId").equals("personage")){
				YZPerson person = SessionUtil.getLoginPerson(request);
				name=person.getUserName();
				doctorId=person.getSeqId();
				map.put("doctor", "\'"+person.getSeqId()+"\'");
			}else{
				Map<String, String> map1=new HashMap<String,String>();
				map1.put("seqId", map.get("personId"));
				YZPerson yzPerson = sysDeptPrivDao.findPersonBySeqId(map1);
				name=yzPerson.getUserName();
				doctorId=map.get("personId");
				map.put("doctor", "\'"+map.get("personId")+"\'");
			}
		}
		//查询项目业绩
		List<JSONObject> doctorSituationCJMoneylist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER+ ".selectDoctorPerformanceMoney", map);
		//查询预交金
		//List<JSONObject> doctorSituationPrepayMoneylist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER_RECORD+ ".selectDoctorPerformancePrepayMoney", map);
		List<JSONObject> list=new ArrayList<JSONObject>();
		//总计
		String ssje="0.00";
		for (JSONObject jsonObject : doctorSituationCJMoneylist) {
			ssje=new BigDecimal(jsonObject.getString("ssje")).add(new BigDecimal(ssje)).toString();
		}
		//加合计
		if(personlist.size()>0){
			for (JSONObject jsonObject : personlist) {
				if(doctorSituationCJMoneylist.size()>0){
					String ssje1="0.00";
					int a=0;
					for (JSONObject jsonObject1: doctorSituationCJMoneylist) {
						if(jsonObject.getString("seqid").equals(jsonObject1.getString("seqid"))){
							ssje1=new BigDecimal(jsonObject1.getString("ssje")).add(new BigDecimal(ssje1)).toString();
							list.add(jsonObject1);
							a=1;
						}
					}
					if(a==1){
						//总计
						JSONObject json = new JSONObject();
						json.put("ssje", ssje1);
						list.add(json);
						
					}
				}
			}
		}else{
			if(doctorSituationCJMoneylist.size()>0){
				String ssje1="0.00";
				int a=0;
				for (JSONObject jsonObject1: doctorSituationCJMoneylist) {
					if(doctorId.equals(jsonObject1.getString("seqid"))){
						ssje1=new BigDecimal(jsonObject1.getString("ssje")).add(new BigDecimal(ssje1)).toString();
						list.add(jsonObject1);
						a=1;
					}
				}
				//总计
				if(a==1){
					JSONObject json = new JSONObject();
					json.put("ssje", ssje1);
					list.add(json);
				}
			}
		}
		if(list.size()==0){
			//总计
			JSONObject json = new JSONObject();
			json.put("username", "");
			json.put("basename", "");
			json.put("nextname", "");
			json.put("ssje", ssje);
			list.add(json);
		}else{
			//总计
			JSONObject json = new JSONObject();
			json.put("ssje", ssje);
			list.add(json);
			
		}
		return list;
	}
	
}