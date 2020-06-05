package com.kqds.service.base.costOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.ykzz.dao.IYkzzDrugsDao;
import com.hudh.ykzz.dao.YkzzDrugsInDao;
import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsChufangDetail;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsIntegralRecord;
import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsMemberRecord;
import com.kqds.entity.base.KqdsPaycost;
import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.entity.base.KqdsRefund;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.memberRecord.KQDS_Member_RecordLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class KQDS_CostOrderLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_Member_RecordLogic recLogic;
	@Autowired
	private KQDS_UserDocumentLogic userLogic;
	@Autowired
	private KQDS_CostOrder_DetailLogic logicd;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private KQDS_REGLogic logicreg;
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private KQDS_TreatItemLogic treatItemLogic;
	@Autowired
	private IYkzzDrugsDao ykzzDrugsDao;
	@Autowired
	private YkzzDrugsInDao kzzDrugsInDao;
	/**
	 * 根据患者编号，获取上一次开单信息
	 * 
	 * @param conn
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getLastestCostOrderInfo(String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getLastestCostOrderInfo", usercode);
		return list;
	}

	/**
	 * 验证改费用单是否是当天结账
	 * 
	 * @param conn
	 * @param costno
	 * @return
	 * @throws Exception
	 */
	public int isTodayCost(String costno) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".isTodayCost", costno);
		return count;
	}

	/**
	 * 根据qfbh 和 项目编号 查询 当天最新的 记录的costno
	 * 
	 * @param conn
	 * @param qfbh
	 * @param itemno
	 * @return
	 * @throws Exception
	 */
	public String getCostnoNew(String qfbh, String itemno) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("qfbh", qfbh);
		map.put("itemno", itemno);
		JSONObject jobj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".getCostnoNew", map);
		if (jobj != null && jobj.containsKey("costno")) {
			return jobj.getString("costno");
		}
		return "";
	}

	// 等待结账
	@SuppressWarnings("unchecked")
	public List<JSONObject> getListByStatus(Map<String, String> map, YZPerson person, String querytype, String visualstaff, HttpServletRequest request) throws Exception {
		String sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID); // 获取收费角色
		if (map.containsKey("searchvalue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("searchvalue")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("searchvalue")));
		}
		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			if (!YZUtility.isStrInArrayEach(person.getUserPriv(), sfpriv)) {
				map.put("visualstaff", visualstaff);
			}
		}
		map.put("createtime", SQLUtil.dateDiffDay("c.createtime"));
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getListByStatus", map);
		return list;
	}

	// 首页查询条数
	public int getCountIndex(YZPerson person, String querytype, String searchValue, String visualstaff, String organization, HttpServletRequest request) throws Exception {
		String sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID); // 获取收费角色
		Map<String, String> map = new HashMap<String, String>();
		map.put("createtime", SQLUtil.dateDiffDay("c.createtime"));
		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			if (!YZUtility.isStrInArrayEach(person.getUserPriv(), sfpriv)) {
				map.put("visualstaff", visualstaff);
			}
		}
		if (!YZUtility.isNullorEmpty(searchValue)) {
			map.put("searchValue", searchValue);
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
		}
		map.put("organization", organization);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".getCountIndex", map);
		return count;
	}

	/**
	 * 费用列表 【查询所有，不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param map
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List selectWithPageNopage(String table, Map<String, String> map, String visualstaff) throws Exception {
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".selectWithPageNopage", map);
		return list;
	}
	/**
	 * 费用列表 【查询所有，不做门诊条件过滤】
	 * 2019-08-23 lwg
	 * @param conn
	 * @param table
	 * @param map
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List selectWithPageNopageForLabel(String table, Map<String, String> map, String visualstaff) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".selectWithPageNopageForLabel", map);
		return list;
	}
	/**
	 * 已结账列表
	 * 
	 * @param conn
	 * @param table
	 * @param person
	 * @param map
	 * @param visualstaff
	 * @param querytype
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectNoPageYjz(BootStrapPage bp, YZPerson person, Map<String, String> map, String visualstaff, String querytype) throws Exception {
		int firstIndex = bp.getOffset();
		if (map.containsKey("searchValue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("searchValue")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("searchValue")));
		}
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}

		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			if (!visualstaff.equals("")) {
				map.put("visualstaff", visualstaff);
			}
		}
		if (map.containsKey("ispaging")) {
			if (map.get("ispaging").equals("1")) {
				// 分页插件
				PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
			}
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".selectNoPage", map);
		for (JSONObject obj : list) {
			String cjstatus = obj.getString("cjstatus");
			if ("0".equals(cjstatus)) {
				obj.put("cjstatus", "未成交");
			} else {
				obj.put("cjstatus", "已成交");
			}
		}
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		// 第一次加载数量，分页查询时不加载
		if (firstIndex == 0) {
			if (list.size() > 0) {
				// 统计 费用表中 小计 应收 免除 欠款 缴费 等
				Map<String, String> wheremap = new HashMap<>();
				wheremap.putAll(map);
				wheremap.put("cost.totalcost", "totalcost");
				wheremap.put("cost.voidmoney", "voidmoney");
				wheremap.put("cost.shouldmoney", "shouldmoney");
				wheremap.put("cost.actualmoney", "actualmoney");
				wheremap.put("cost.y2", "y2");
				List<JSONObject> listmoney = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".getTotalNumFields", wheremap);
				for (JSONObject obj : listmoney) {
					jobj.put("totalcost", obj.getString("totalcost"));
					jobj.put("voidmoney", obj.getString("voidmoney"));
					jobj.put("shouldmoney", obj.getString("shouldmoney"));
					jobj.put("actualmoney", obj.getString("actualmoney"));
					jobj.put("y2", obj.getString("y2"));
				}
			}
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 已结账列表
	 * 
	 * @param conn
	 * @param table
	 * @param person
	 * @param map
	 * @param visualstaff
	 * @param querytype
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectNoPage(BootStrapPage bp, YZPerson person, Map<String, String> map, String visualstaff, String querytype) throws Exception {
		int firstIndex = bp.getOffset();
		if (map.containsKey("searchValue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("searchValue")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("searchValue")));
		}
		if (map.containsKey("queryinput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
		}

		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			if (!visualstaff.equals("")) {
				map.put("visualstaff", visualstaff);
			}
		}
//		if (map.containsKey("ispaging")) {
//			if (map.get("ispaging").equals("1")) {
//				// 分页插件
//			}
//		}
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("organizationname")){
				map.put("sortName", "dept.DEPT_NAME");
			}
			if(map.get("sortName").equals("sftime")){
				map.put("sortName", "cost.sftime");
			}
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "cost.remark");
			}
			if(map.get("sortName").equals("sfuser")){
				map.put("sortName", "per8.USER_NAME");
			}
			if(map.get("sortName").equals("jdtime")){
				map.put("sortName", "u.CreateTime");
			}
			if(map.get("sortName").equals("jddy")){
				map.put("sortName", "per7.USER_NAME");
			}
			if(map.get("sortName").equals("jduser")){
				map.put("sortName", "per5.USER_NAME");
			}
			if(map.get("sortName").equals("developer")){
				map.put("sortName", "per6.USER_NAME");
			}
			if(map.get("sortName").equals("introducer")){
				map.put("sortName", "u2.username");
			}
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "cost.createtime");
			}
			if(map.get("sortName").equals("createuser")){
				map.put("sortName", "per4.USER_NAME");
			}
			if(map.get("sortName").equals("nexttype")){
				map.put("sortName", "hzly.dict_name");
			}
			if(map.get("sortName").equals("devchannel")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(map.get("sortName").equals("techperson")){
				map.put("sortName", "per11.USER_NAME");
			}
			if(map.get("sortName").equals("nurse")){
				map.put("sortName", "per3.USER_NAME");
			}
			if(map.get("sortName").equals("doctorname")){
				map.put("sortName", "per2.USER_NAME");
			}
			if(map.get("sortName").equals("regdept")){
				map.put("sortName", "dept1.DEPT_NAME");
			}
			if(map.get("sortName").equals("askperson")){
				map.put("sortName", "per1.USER_NAME");
			}
			if(map.get("sortName").equals("faskperson")){
				map.put("sortName", "per12.USER_NAME");
			}
			if(map.get("sortName").equals("cjstatus")){
				map.put("sortName", "cost.cjstatus");
			}
			if(map.get("sortName").equals("regsort")){
				map.put("sortName", "kcit2.dict_name");
			}
			if(map.get("sortName").equals("recesort")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("actualmoney")){
				map.put("sortName", "cost.actualmoney");
			}
			if(map.get("sortName").equals("y2")){
				map.put("sortName", "cost.y2");
			}
			if(map.get("sortName").equals("shouldmoney")){
				map.put("sortName", "cost.shouldmoney");
			}
			if(map.get("sortName").equals("voidmoney")){
				map.put("sortName", "cost.voidmoney");
			}
			if(map.get("sortName").equals("totalcost")){
				map.put("sortName", "cost.totalcost");
			}
			if(map.get("sortName").equals("phonenumber1")){
				map.put("sortName", "u.phonenumber1");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "cost.username");
			}
			if(map.get("sortName").equals("blcode")){
				map.put("sortName", "u.blcode");
			}
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "cost.usercode");
			}
		}
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".selectNoPage", map);
		for (JSONObject obj : list) {
			String cjstatus = obj.getString("cjstatus");
			if ("0".equals(cjstatus)) {
				obj.put("cjstatus", "未成交");
			} else {
				obj.put("cjstatus", "已成交");
			}
		}
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		// 第一次加载数量，分页查询时不加载
		if (firstIndex == 0) {
			if (list.size() > 0) {
				// 统计 费用表中 小计 应收 免除 欠款 缴费 等
				Map<String, String> wheremap = new HashMap<>();
				wheremap.putAll(map);
				wheremap.put("cost.totalcost", "totalcost");
				wheremap.put("cost.voidmoney", "voidmoney");
				wheremap.put("cost.shouldmoney", "shouldmoney");
				wheremap.put("cost.actualmoney", "actualmoney");
				wheremap.put("cost.y2", "y2");
				JSONObject obj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".getTotalNumFields", wheremap);
				JSONObject obj1 = (JSONObject) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".getTotalNumFieldsByYjj", wheremap);
				JSONObject obj2 = (JSONObject) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".getTotalNumFieldsByYjjchongzhi", wheremap);
				if(YZUtility.isNullorEmpty(obj.getString("totalcost"))&&YZUtility.isNullorEmpty(obj1.getString("paymoney"))){
					jobj.put("totalcost", new BigDecimal("0"));
				}else if(!YZUtility.isNullorEmpty(obj.getString("totalcost"))&&YZUtility.isNullorEmpty(obj1.getString("paymoney"))){
					jobj.put("totalcost", new BigDecimal(obj.getString("totalcost")));
				}else if(YZUtility.isNullorEmpty(obj.getString("totalcost"))&&!YZUtility.isNullorEmpty(obj1.getString("paymoney"))){
					jobj.put("totalcost", new BigDecimal("0").subtract(new BigDecimal(obj1.getString("paymoney"))));
				}else{
					jobj.put("totalcost", new BigDecimal(obj.getString("totalcost")).subtract(new BigDecimal(obj1.getString("paymoney"))));
				}
				if(YZUtility.isNullorEmpty(obj.getString("shouldmoney"))&&YZUtility.isNullorEmpty(obj1.getString("paymoney"))){
					jobj.put("shouldmoney", new BigDecimal("0"));
				}else if(!YZUtility.isNullorEmpty(obj.getString("shouldmoney"))&&YZUtility.isNullorEmpty(obj1.getString("paymoney"))){
					jobj.put("shouldmoney", new BigDecimal(obj.getString("shouldmoney")));
				}else if(YZUtility.isNullorEmpty(obj.getString("shouldmoney"))&&!YZUtility.isNullorEmpty(obj1.getString("paymoney"))){
					jobj.put("shouldmoney", new BigDecimal("0").subtract(new BigDecimal(obj1.getString("paymoney"))));
				}else{
					jobj.put("shouldmoney", new BigDecimal(obj.getString("shouldmoney")).subtract(new BigDecimal(obj1.getString("paymoney"))));
				}
				if(YZUtility.isNullorEmpty(obj.getString("actualmoney"))&&YZUtility.isNullorEmpty(obj1.getString("paymoney"))){
					jobj.put("actualmoney", new BigDecimal("0"));
				}else if(!YZUtility.isNullorEmpty(obj.getString("actualmoney"))&&YZUtility.isNullorEmpty(obj1.getString("paymoney"))){
					jobj.put("actualmoney", new BigDecimal(obj.getString("actualmoney")));
				}else if(YZUtility.isNullorEmpty(obj.getString("actualmoney"))&&!YZUtility.isNullorEmpty(obj1.getString("paymoney"))){
					jobj.put("actualmoney", new BigDecimal("0").subtract(new BigDecimal(obj1.getString("paymoney"))));
				}else{
					jobj.put("actualmoney", new BigDecimal(obj.getString("actualmoney")).subtract(new BigDecimal(obj1.getString("paymoney"))));
				}
				jobj.put("voidmoney", obj.getString("voidmoney"));
				jobj.put("y2", obj.getString("y2"));
				jobj.put("yjjshiyong", obj1.getString("paymoney"));
				jobj.put("yjjchongzhi", obj2.getString("paymoney"));
			}
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 已结账列表-首页工作量
	 * 
	 * @param conn
	 * @param table
	 * @param person
	 * @param map
	 * @param visualstaff
	 * @param querytype
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectNoPage4IndexGzl(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".selectNoPage4IndexGzl", map);
		for (JSONObject obj : list) {
			String cjstatus = obj.getString("cjstatus");
			if ("0".equals(cjstatus)) {
				obj.put("cjstatus", "未成交");
			} else {
				obj.put("cjstatus", "已成交");
			}
		}
		return list;
	}
	/**
	 * 已结账的显示数量
	 * <p>Title: selectNoPage4IndexGzlNum</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月30日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int selectNoPage4IndexGzlNum(Map<String, String> map) throws Exception {
		int list = (int) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".selectNoPage4IndexGzlNum", map);
		return list;
	}
	/**
	 * 患者总消费查询（首页工作量，报表中心欠费查询） 【查询所有，不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param person
	 * @param map
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject qfSearch(BootStrapPage bp, YZPerson person, Map<String, String> map, HttpServletRequest request) throws Exception {
		JSONObject jobj = new JSONObject();
		int firstIndex = bp.getOffset();
		List<JSONObject> subList = selectRealQfUserListArr(map, request);
		if (subList.size() == 0) { // 如果查询不到欠费记录，直接返回一个空list
			jobj.put("rows", subList);
			return jobj;
		}
		StringBuffer usercodeBf = new StringBuffer(); // 患者编号集合
		for (JSONObject obj : subList) {
			usercodeBf.append("'" + obj.getString("usercode") + "',");
		}
		if (usercodeBf.toString().endsWith(",")) {
			usercodeBf.deleteCharAt(usercodeBf.length() - 1); // 患者编号集合 end...
		}
		map.put("usercodeBf", usercodeBf.toString());
		if (map.containsKey("ispaging")) {
			if (map.get("ispaging").equals("1")) {
				// 分页插件
				PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
			}
		}
//		if(map.get("sortName")!=null){
//			if(map.get("sortName").equals("usercode")){
//				map.put("sortName", "u.usercode");
//			}
//			if(map.get("sortName").equals("username")){
//				map.put("sortName", "u.username");
//			}
//			if(map.get("sortName").equals("phonenumber1")){
//				map.put("sortName", "u.phonenumber1");
//			}
//			if(map.get("sortName").equals("askperson")){
//				map.put("sortName", "per1.USER_NAME");
//			}
//			if(map.get("sortName").equals("devchannel")){
//				map.put("sortName", "kcit3.dict_name");
//			}
//			if(map.get("sortName").equals("nexttype")){
//				map.put("sortName", "hzly.dict_name");
//			}
//			if(map.get("sortName").equals("introducer")){
//				map.put("sortName", "u2.username");
//			}
//			if(map.get("sortName").equals("developer")){
//				map.put("sortName", "per6.USER_NAME");
//			}
//			if(map.get("sortName").equals("jduser")){
//				map.put("sortName", "per5.USER_NAME");
//			}
//		}
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".qfSearch", map);
		for (JSONObject mainObj : list) { // 两个list的结果集整合到一起
			for (JSONObject obj : subList) {
				if (mainObj.getString("usercode").equals(obj.getString("usercode"))) {
					mainObj.putAll(obj);
					break; // 匹配到后，终止本次循环，继续下一个匹配
				}
			}
		}
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		// 第一次加载数量，分页查询时不加载
		if (firstIndex == 0) {
			if (list.size() > 0) {
				// 统计 费用表中 小计 应收 免除 欠款 缴费 等
				BigDecimal totalcost = BigDecimal.ZERO;
				BigDecimal voidmoney = BigDecimal.ZERO;
				BigDecimal shouldmoney = BigDecimal.ZERO;
				BigDecimal actualmoney = BigDecimal.ZERO;
				BigDecimal y2 = BigDecimal.ZERO;
				for (JSONObject obj : subList) {
					totalcost = totalcost.add(new BigDecimal(obj.getString("totalcost")));
					voidmoney = voidmoney.add(new BigDecimal(obj.getString("voidmoney")));
					shouldmoney = shouldmoney.add(new BigDecimal(obj.getString("shouldmoney")));
					actualmoney = actualmoney.add(new BigDecimal(obj.getString("actualmoney")));
					y2 = y2.add(new BigDecimal(obj.getString("y2")));
				}
				jobj.put("totalcost", KqdsBigDecimal.round(totalcost, 2).toString());
				jobj.put("voidmoney", KqdsBigDecimal.round(voidmoney, 2).toString());
				jobj.put("shouldmoney", KqdsBigDecimal.round(shouldmoney, 2).toString());
				jobj.put("actualmoney", KqdsBigDecimal.round(actualmoney, 2).toString());
				jobj.put("y2", KqdsBigDecimal.round(y2, 2).toString());
			}
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 查询欠费的患者 【排除 已经还款完成的患者】
	 * 
	 * @param conn
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<JSONObject> selectRealQfUserListArr(Map<String, String> map, HttpServletRequest request) throws Exception {
		String usercodeArr = selectQfUserListArr(map.get("visualstaff"), request);
		String usercode4Query = YZUtility.ConvertStringIds4Query(usercodeArr);
		map.put("usercode4Query", usercode4Query);
		if (map.containsKey("searchvalue")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("searchvalue")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("searchvalue")));
		}

		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".selectRealQfUserListArr", map);
		return list;
	}

	/**
	 * 查询欠费的患者 【查询所有，不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String selectQfUserListArr(String visualstaff, HttpServletRequest request) throws Exception {
		List<JSONObject> jsonList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".selectQfUserListArr", visualstaff);
		StringBuffer list = new StringBuffer();
		for (JSONObject json : jsonList) {
			list.append(json.getString("usercode") + ",");
		}
		return list.toString();
	}

	// 清除costno为空 且 不是还款项目的记录（多次还款删除时 导致的垃圾数据）
	public void deleteLaji(HttpServletRequest request) throws Exception {
		dao.delete(TableNameUtil.KQDS_COSTORDER_DETAIL + ".deleteDetailLj", null);
	}

	/**
	 * 验证费用单是否能退
	 * 
	 * @param costno
	 * @param dbConn
	 * @return true 可以退 false 不能退
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean ifCanTuiDan(String costno) throws Exception {
		boolean flag = true; // true 表示可以退费
		// 当天的费用单才可以退单
		// 当天费用单存在一下几种情况可以退回到等待结账
		// 1、无欠款
		// 2、最新的还款或欠费
		// 当天的费用单包含退费项目的，不可以使用退单功能

		// 验证改费用单是否是当天结账（去掉当天的过滤条件）
		int isTodayCost = isTodayCost(costno);
		if (isTodayCost == 0) {
			// throw new Exception("只能对当天已结账的费用单进行退单！");
			throw new Exception("只能对已结账的费用单进行退单！");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("costno", costno);
		// 验证是否存在退费操作
		List<KqdsRefund> tklist = (List<KqdsRefund>) dao.loadList(TableNameUtil.KQDS_REFUND, map);
		if (tklist != null && tklist.size() > 0) {
			throw new Exception("该费用单存在退费操作，无法退单！");
		}
		// 验证是否是退费单
		List<KqdsCostorderDetail> detallist = (List<KqdsCostorderDetail>) dao.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
		if (detallist == null || detallist.size() == 0) {
			throw new Exception("该费用单不存在费用明细！");
		}

		for (KqdsCostorderDetail detail : detallist) {
			if (1 == detail.getIstk()) {
				throw new Exception("退费单，无法退单！");
			}

			String qfbh = detail.getQfbh();
			if (YZUtility.isNullorEmpty(qfbh)) {
				continue;
			}
			// 欠费编号存在时，判断在这之后，有没有相同欠费编号的结账单
			int count = (int) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".ifCanTuiDan", detail);
			if (count > 0) {
				throw new Exception("该费用单存在欠费项目，且不是最新的还款单，无法退单！");
			}
			// 继续判断，避免连续退单的情况出现
			int count2 = (int) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".ifCanTuiDan2", detail);
			if (count2 > 0) {
				throw new Exception("该单之后存在退费单，请先处理！");
			}
		}

		return flag;
	}

	/**
	 * 验证费用单退单积分情况
	 * 
	 * @param costno
	 * @param dbConn
	 * @return true 可以退 false 不能退
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean ifCanTuiDanJF(String costno, String usercode, HttpServletRequest request) throws Exception {
		boolean flag = true; // true 表示可以退费
		// 档案表
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("usercode", usercode);
		List<KqdsUserdocument> userlist = (List<KqdsUserdocument>) dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
		if (userlist == null) {
			throw new Exception("患者不存在！");
		}
		KqdsUserdocument user = userlist.get(0);
		BigDecimal ssmoney = userLogic.getSsjeOne(costno);
		BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, SysParaUtil.COST_INTEGRAL));
		if (KqdsBigDecimal.compareTo(costIntegral, BigDecimal.ZERO) > 0) {
			BigDecimal integral = ssmoney.divide(costIntegral, 0, RoundingMode.DOWN);
			if (KqdsBigDecimal.compareTo(user.getIntegral(), integral) < 0) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 验证预交金费用单是否可以退
	 * 
	 * @param costno
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	public boolean ifCanTuiDanYJJ(String costno) throws Exception {
		boolean flag = true;
		// 验证该费用单结账后，该患者是否使用卡号为usercode的会员卡 结过账
		KqdsCostorder cost = (KqdsCostorder) dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
		if (cost == null) { // 新增
			throw new Exception("请选择费用单！");
		}
		// 验证改费用单是否结账
		int isTodayCost = isTodayCost(costno);
		if (isTodayCost == 0) {
			throw new Exception("只能对已结账的费用单进行退单！");
		}
		// 查询预交金单结账时间节点之后，是否存在会员卡的 缴费和转赠记录
		int count = recLogic.yjjTuidan(cost.getUsercode(), cost.getSftime());
		if (count > 0) {
			throw new Exception("预交金中途存在转赠、缴费、退费记录,不能退单！");
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public void tuiDan(List<KqdsCostorderDetail> detallist, String backid, BigDecimal yjj, BigDecimal zs, KqdsCostorder cost, String backremark, YZPerson person, String costno,
			int isyjjitem, Map<String, String> map, HttpServletRequest request) throws Exception {
		for (KqdsCostorderDetail detail : detallist) {
			detail.setBackid(backid);
			String detailSeqId = detail.getSeqId();
			detail.setSeqId(YZUtility.getUUID()); // 主键重新赋值，避免重复操作退单、结账时，报错！
			dao.saveSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL_TUIDAN, detail);
			detail.setSeqId(detailSeqId); // 把之前的主键还原回来
			/** 保存退单之前的数据 **/
//			if (KqdsBigDecimal.compareTo(detail.getPayyjj(), BigDecimal.ZERO) > 0) {
				yjj = KqdsBigDecimal.add(yjj, detail.getPayyjj()); // 累加
//			}
			if (KqdsBigDecimal.compareTo(detail.getPayother2(), BigDecimal.ZERO) > 0) {
				zs = KqdsBigDecimal.add(zs, detail.getPayother2()); // 累加
			}
			detail.setPayxj(BigDecimal.ZERO);
			detail.setPayyjj(BigDecimal.ZERO);
			detail.setPaybank(BigDecimal.ZERO);
			detail.setPayyb(BigDecimal.ZERO);
			detail.setPayother1(BigDecimal.ZERO);
			detail.setPayother2(BigDecimal.ZERO);
			detail.setPayother3(BigDecimal.ZERO);
			detail.setPayzfb(BigDecimal.ZERO);
			detail.setPaywx(BigDecimal.ZERO);
			detail.setPaymmd(BigDecimal.ZERO);
			detail.setPaybdfq(BigDecimal.ZERO);
			detail.setPaydjq(BigDecimal.ZERO);
			detail.setPayintegral(BigDecimal.ZERO);

			String qfbh = detail.getQfbh();
			if (YZUtility.isNotNullOrEmpty(detail.getQfbh())) { // 如果欠费编号存在，则
				detail.setStatus("1");
			}
			/** 不管之前是否欠费，isqfreal的值都设置为0，这个值是结账时决定的 **/
			detail.setIsqfreal(0);
			if (KqdsBigDecimal.compareTo(detail.getY2(), BigDecimal.ZERO) > 0) { // 最初的第一条欠费，才清qfbh
				detail.setQfbh("");
			}
			dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
			if (YZUtility.isNotNullOrEmpty(qfbh)) {
				List<KqdsCostorderDetail> list = (List<KqdsCostorderDetail>) logicd.selectOneByQfbh(qfbh, detail.getSeqId());
				if (list != null && list.size() > 0) {
					KqdsCostorderDetail cdetail = list.get(0);
					cdetail.setIsqfreal(1);
					dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, cdetail);
				}
			}
		}
		// 费用单 还原
		cost.setIsback(1);// 退单标识
		cost.setBackremark(backremark);
		cost.setBacktime(YZUtility.getCurDateTimeStr());
		cost.setBackuser(person.getSeqId());
		cost.setRecvinfono(backid);
		/**
		 * ............................... 这个值用于与KQDS_COSTORDER_DETAIL_TUIDAN的backid进行关联
		 **/
		// 保存退单记录
		String costSeqId = cost.getSeqId();
		cost.setSeqId(YZUtility.getUUID()); // 主键重新赋值，避免重复操作退单、结账时，报错！
		dao.saveSingleUUID(TableNameUtil.KQDS_COSTORDER_TUIDAN, cost);

		// 记录日志
		BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_COSTORDER_TUIDAN, cost, cost.getUsercode(), TableNameUtil.KQDS_COSTORDER_TUIDAN, request);
		cost.setSeqId(costSeqId); // 把之前的主键还原回来
		cost.setStatus(ConstUtil.COST_ORDER_STATUS_1); // 0 费用计划 1 未结账 2已结账
		cost.setCjstatus(0);
		dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER, cost);
		// 删除收费记录
		List<KqdsPaycost> paylist = (List<KqdsPaycost>) dao.loadList(TableNameUtil.KQDS_PAYCOST, map);
		if ((paylist == null || paylist.size() == 0)) {
			throw new Exception("收费记录不存在！");
		}
		if (paylist.size() > 1) {
			throw new Exception("该费用单存在多条收费记录！");
		}
		KqdsPaycost pay = paylist.get(0);
		dao.deleteSingleUUID(TableNameUtil.KQDS_PAYCOST, pay.getSeqId());

		// 1.正常收费单
		if (isyjjitem == 0) {
			// 会员卡加钱
			if (KqdsBigDecimal.compareTo(yjj, BigDecimal.ZERO) > 0 || KqdsBigDecimal.compareTo(zs, BigDecimal.ZERO) > 0) {
				// 查询退单结账用的会员卡
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("usercode", cost.getUsercode());
				map3.put("type", "缴费");
				map3.put("costno", costno);
				List<KqdsMemberRecord> reclist = (List<KqdsMemberRecord>) dao.loadList(TableNameUtil.KQDS_MEMBER_RECORD, map3);
				if (reclist == null) {
					throw new Exception("会员卡充值记录不存在！");
				}
				KqdsMemberRecord jfrecord = reclist.get(0);
				// 获取缴费会员卡 卡号
				Map<String, String> map4 = new HashMap<String, String>();
				map4.put("memberno", jfrecord.getCardno());
				List<KqdsMember> memberList = (List<KqdsMember>) dao.loadList(TableNameUtil.KQDS_MEMBER, map4);
				if (memberList == null) {
					throw new Exception("会员卡不存在！");
				}
				KqdsMember member = memberList.get(0);
				BigDecimal nowyjj = BigDecimal.ZERO;
				BigDecimal nowzs = BigDecimal.ZERO;
				// 会员卡剩余金额 还原
				if (KqdsBigDecimal.compareTo(yjj, BigDecimal.ZERO) > 0) {
					nowyjj = KqdsBigDecimal.add(member.getMoney(), yjj);
					member.setMoney(nowyjj);
					map3.put("cmoney", KqdsBigDecimal.sub(BigDecimal.ZERO, yjj).toString());
				}
				if (KqdsBigDecimal.compareTo(zs, BigDecimal.ZERO) > 0) {
					nowzs = KqdsBigDecimal.add(member.getGivemoney(), zs);
					member.setGivemoney(nowzs);
					map3.put("cgivemoney", KqdsBigDecimal.sub(BigDecimal.ZERO, zs).toString());
				}
				tuiDanMember(member, jfrecord.getSeqId());
			}
		} else {// 2.预交金单（会员卡减钱）
				// 查询退单结账用的会员卡
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("usercode", cost.getUsercode());
			map3.put("type", "充值");
			map3.put("costno", costno);
			List<KqdsMemberRecord> reclist = (List<KqdsMemberRecord>) dao.loadList(TableNameUtil.KQDS_MEMBER_RECORD, map3);
			if (reclist == null) {
				throw new Exception("会员卡缴费记录不存在！");
			}
			KqdsMemberRecord jfrecord = reclist.get(0);
			// 获取缴费会员卡 卡号
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("memberno", cost.getUsercode());
			List<KqdsMember> memberList = (List<KqdsMember>) dao.loadList(TableNameUtil.KQDS_MEMBER, map4);
			if (memberList == null) {
				throw new Exception("会员卡不存在！");
			}
			KqdsMember member = memberList.get(0);
			BigDecimal nowyjj = KqdsBigDecimal.sub(member.getMoney(), cost.getTotalcost());
			/** 简单粗暴 **/
			member.setMoney(nowyjj);
			tuiDanMember(member, jfrecord.getSeqId());
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public void tuiDanMember(KqdsMember member, String jfrecordSeqId) throws Exception {
		dao.updateSingleUUID(TableNameUtil.KQDS_MEMBER, member);
		// 会员卡缴费的操作记录删除
		dao.deleteSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, jfrecordSeqId);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void setIntegralMoney(BigDecimal integralback, BigDecimal costIntegral, BigDecimal ssmoney, KqdsUserdocument u, String perId, HttpServletRequest request)
			throws Exception {
		// 参数值大于0 积分功能正常
		if (KqdsBigDecimal.compareTo(costIntegral, BigDecimal.ZERO) > 0) {
			BigDecimal integral = ssmoney.divide(costIntegral, 0, RoundingMode.DOWN);
			u.setIntegral(u.getIntegral().subtract(integral));
			// 保存积分记录
			String jfjs = dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "退单减少");
			String tfjs = dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "退单返回");
			if (YZUtility.isNullorEmpty(jfjs) || YZUtility.isNullorEmpty(tfjs)) {
				throw new Exception("积分类型不存在！");
			}
			// 退单减少
			KqdsIntegralRecord record = new KqdsIntegralRecord();
			record.setSeqId(UUID.randomUUID().toString());
			record.setCreatetime(YZUtility.getCurDateTimeStr());
			record.setOrganization(ChainUtil.getCurrentOrganization(request));
			record.setUsercode(u.getUsercode());
			record.setJfmoney(BigDecimal.ZERO.subtract(integral));
			record.setJftype(jfjs);
			record.setSyjfmoney(u.getIntegral());
			record.setCreateuser(perId);
			dao.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);

			// 退单回退
			record = new KqdsIntegralRecord();
			record.setSeqId(UUID.randomUUID().toString());
			record.setCreatetime(YZUtility.getCurDateTimeStr());
			record.setOrganization(ChainUtil.getCurrentOrganization(request));
			record.setUsercode(u.getUsercode());
			record.setJfmoney(integralback);
			record.setJftype(tfjs);
			record.setSyjfmoney(u.getIntegral().add(integralback));
			record.setCreateuser(perId);
			dao.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);
			u.setIntegral(u.getIntegral().add(integralback));
		}
		dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Transactional(rollbackFor = { Exception.class })
	public String insertOrUpdate(KqdsCostorder dp, YZPerson person, String askperson, String deleteitem, String seqId, HttpServletRequest request, List<YZPerson> listPserson, String repairdoctor) throws Exception {
		String uuid = "";
		// 收费明细对应的咨询 如果存在原始咨询统一存原始咨询
		Map<String, String> mapuser = new HashMap<String, String>();
		mapuser.put("usercode", dp.getUsercode());
		List<KqdsUserdocument> userlist = (List<KqdsUserdocument>) dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, mapuser);

		if (userlist == null || userlist.size() == 0) {
			throw new Exception("此患者不存在，患者编号为：" + dp.getUsercode());
		}

		if (userlist.size() > 1) {
			throw new Exception("数据异常，同一个编号对应多个患者，患者编号为：" + dp.getUsercode());
		}
		KqdsUserdocument user = userlist.get(0);
		if (YZUtility.isNullorEmpty(askperson)) {
			/** 目前页面咨询必选，不存在此情况 **/
			askperson = user.getAskperson();
		}
		
		// 保存的对象集合 json格式
		String listdata = request.getParameter("listDetail");
		JSONArray jArray = JSONArray.fromObject(listdata);
		Collection collection = JSONArray.toCollection(jArray, KqdsCostorderDetail.class);
		Iterator it = collection.iterator();

		List<KqdsTreatitem> list = treatItemLogic.getTreatItemInfroList(collection);//调用方法treatItemLogic中的方法
		/**
		 * //添加开单校验（检查项目单开） start syp 2019-11-14
		 */
		List<KqdsTreatitem> treatitemsList = list.stream().collect(
		    Collectors.collectingAndThen(
		    Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(KqdsTreatitem :: getBasetype))), ArrayList::new)
		);//按照指定对象属性去重
//		KqdsTreatitem treatitem = treatitemsList.stream().filter(KqdsTreatitem -> KqdsTreatitem.getBasetype() == "jyk671").findAny().orElse(null);//从集合中获取符合条件的元素
		// 存放过滤结果的列表
        List<KqdsTreatitem> result = null;
        // 要被找出的项目类型的标识
        ArrayList<String> baseTypes = new ArrayList<String>();
        baseTypes.add("jyk671");
        // 使用lambda表达式过滤出结果并放到result集合列表里
        result = treatitemsList.stream().filter((KqdsTreatitem item) -> baseTypes.contains(item.getBasetype())).collect(Collectors.toList());
		if(result != null && result.size() > 0) {
			for (KqdsTreatitem kqdsTreatitem : treatitemsList) {
				String baseType = kqdsTreatitem.getBasetype();
				if(!baseType.equals("jyk671")) {
					throw new Exception("检查项目只能单开！");
				}
			}
		}
		/**
		 * //end
		 */
		//获取处方单类型 药品或者检查项目
		String type = checkFyType(list);
		checkIsClassType(type, list);
		//检查库存是否充足
		this.orderDrugStock(collection);
		int sat = Integer.parseInt(type);
		
		if (!YZUtility.isNullorEmpty(seqId)) {
			// 后台验证改费用单如果已结账，抛出异常
			KqdsCostorder cost = (KqdsCostorder) dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, seqId);
			if (cost.getStatus().equals(ConstUtil.COST_ORDER_STATUS_2)) {
				throw new Exception("该费用单已结账，操作无效！");
			}
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setIsback(cost.getIsback());
			/** 编辑时，保留退单的这两个字段 **/
			dp.setBackremark(cost.getBackremark());
			dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER, dp);

			// 修改费用单时删除的 收费项目 ，数据库删除
			if (!YZUtility.isNullorEmpty(deleteitem)) {
				if (deleteitem.endsWith(",")) {
					deleteitem = deleteitem.substring(0, deleteitem.length());
				}
				String[] itemidArr = deleteitem.split(",");
				for (String delseqId : itemidArr) {
					KqdsCostorderDetail detailDel = (KqdsCostorderDetail) dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, delseqId);
					if (ConstUtil.QF_STATUS_1 != detailDel.getIsqfreal()) {
						/** 前后台双重校验 **/
						dao.deleteSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detailDel.getSeqId());
						BcjlUtil.LogBcjlWithUserCodeAndRegno(BcjlUtil.UPDATE, BcjlUtil.KQDS_COSTORDER, "删除费用单明细", detailDel.getUsercode(), detailDel.getRegno(), false,
								TableNameUtil.KQDS_COSTORDER, request);
					}
				}
			}

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_COSTORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_COSTORDER, request);
		} else {
			uuid = YZUtility.getUUID();
			dp.setSeqId(uuid);
			dp.setCostno(uuid);
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			dp.setIsdrugs(sat);
			dp.setIssend(0);
			dp.setRepairdoctor(repairdoctor);//保存修复医生
			//###########设置咨询不能开药品费用单###########
			Integer isDrugs = dp.getIsdrugs();
			String drugs = isDrugs.toString();
			if (drugs.equals("1")) {
				for (int i = 0; i < listPserson.size(); i++) {
					if ((listPserson.get(i).getUserName()).equals(person.getUserName())) {
						throw new Exception("对不起，您没有权限开药品费用单！");
					}
				}
			}
			dao.saveSingleUUID(TableNameUtil.KQDS_COSTORDER, dp);

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_COSTORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_COSTORDER, request);
		}
		
		
		/**
		 * 开单时，判断该值和消费项目的累加值是否相等
		 */
		BigDecimal acutalMoney = dp.getActualmoney();
		BigDecimal detailPayMoney = BigDecimal.ZERO;

		// 保存收费项目
		KqdsCostorderDetail detail = new KqdsCostorderDetail();
		while (it.hasNext()) {
			detail = (KqdsCostorderDetail) it.next();
			detailPayMoney = KqdsBigDecimal.add(detailPayMoney, detail.getPaymoney()); // 累加

			
			// 查询还款项目所属医生 咨询，防止还欠款时 该项目会计算到其他人下
			if (ConstUtil.QF_STATUS_1 == detail.getIsqfreal()) {
				if (YZUtility.isNullorEmpty(detail.getQfbh())) {
					throw new Exception("数据异常：真实欠费项目的欠费编号值为空");
				}
				/** 根据欠费编号查询，可能会查出多条这里应该查询最近一次的记录 **/
				List<KqdsCostorderDetail> detailJson = (List<KqdsCostorderDetail>) logicd.selectOneByQfbh2(detail.getQfbh());
				if (detailJson == null || detailJson.size() == 0) {
					throw new Exception("数据异常：根据欠费编号查询不到之前的收费项目");
				}
				/*
				 * String doctor = detailJson.getString("doctor"); if
				 * (YZUtility.isNullorEmpty(doctor)) { throw new
				 * Exception("数据异常：根据欠费编号查询到之前的收费项目，医生值为空"); }
				 */
				// detail.setDoctor(doctor);
				// // detail.setAskperson(list.get(0).getAskperson());
				// Map<String, String> map2 = new HashMap<String, String>();
				// map2.put("qfbh", detail.getQfbh());
				// List<KQDS_CostOrder_Detail> list =
				// (List<KQDS_CostOrder_Detail>) logic.loadList(dbConn,
				// KQDS_CostOrder_Detail.class, map2,
				// TableNameUtil.KQDS_COSTORDER_DETAIL);
				// if (list != null && list.size() > 0) {
				// detail.setDoctor(list.get(0).getDoctor());
				// // detail.setAskperson(list.get(0).getAskperson());
				// }
			} else {
				// detail.setDoctor(dp.getDoctor());
				// detail.setAskperson(askperson);
			}
			// 咨询 还是算给 本次的接诊咨询
			detail.setAskperson(askperson);
			/**
			 * 不管是新增消费项目还是修改，isqfreal字段的值，都只能在收费的时候更新 add by yangsen 17-12-29
			 */
			detail.setIsqfreal(0);
			if (!YZUtility.isNullorEmpty(detail.getSeqId())) {
				/** 这里要考虑一个费用单，开两个页面同时修改的情况 **/
				KqdsCostorderDetail olddetail = (KqdsCostorderDetail) dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail.getSeqId());
				if (olddetail == null) {
					throw new Exception("该费用单中的收费项目已被删除，项目编号：" + detail.getItemno() + "，请刷新页面重新操作！");
				}
				if (YZUtility.isNullorEmpty(detail.getCreatetime())) {
					throw new Exception("数据异常：修改消费项目时，创建时间为空值");
				}
				if (YZUtility.isNullorEmpty(olddetail.getCreatetime())) {
					throw new Exception("数据异常：消费项目创建时间为空值");
				}
				if (!detail.getCreatetime().equals(olddetail.getCreatetime())) {
					throw new Exception("该费用单已超时，请刷新页面再进行操作！");
				}

				/** 这里要更新时间，以便判断是否同时开两个页面时，一个页面已经提交修改，而另一个页面无法察觉 **/
				detail.setCreatetime(YZUtility.getCurDateTimeStr()); // 前台要把createtime传回来！！！
				detail.setCreateuser(person.getSeqId());
				
				KqdsChufangDetail kqdsChufangDetail = new KqdsChufangDetail();//###########同步修改处方明细表的信息#################
				kqdsChufangDetail.setNum(detail.getNum());
				kqdsChufangDetail.setItemno(detail.getItemno());
				dao.update("KQDS_CHUFANG_DETAIL.updateChuFangDetail", kqdsChufangDetail);
				
				dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_COSTORDER_DETAIL, detail, detail.getUsercode(), TableNameUtil.KQDS_COSTORDER_DETAIL, request);
			} else {
				detail.setCostno(dp.getSeqId());
				/** costno 和主键值 一致 **/
				detail.setRegno(dp.getRegno());
				/** 挂号表主键 **/
				detail.setSeqId(YZUtility.getUUID());
				detail.setCreatetime(YZUtility.getCurDateTimeStr());
				detail.setCreateuser(person.getSeqId());
				detail.setOrganization(ChainUtil.getCurrentOrganization(request));// 【前端页面调用，以所在门诊为准】
				dao.saveSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_COSTORDER_DETAIL, detail, detail.getUsercode(), TableNameUtil.KQDS_COSTORDER_DETAIL, request);
			}
		}

		if (KqdsBigDecimal.compareTo(acutalMoney, detailPayMoney) != 0) {
			throw new Exception("明细缴费总金额与费用单缴费金额不等！");
		}

		/** 这里的状态值，只能在结账的时候改，不然是个BUG **/
		// 保存时把原欠款项目的标识改为0
		// if (!YZUtility.isNullorEmpty(qfId)) {
		// String[] arr = qfId.split(",");
		// for (String yid : arr) {
		// KQDS_CostOrder_Detail detailcost = (KQDS_CostOrder_Detail)
		// logic.loadObjSingleUUID(KQDS_CostOrder_Detail.class,
		// yid,
		// TableNameUtil.KQDS_COSTORDER_DETAIL);
		// detailcost.setStatus(ConstUtil.COST_DETAIL_STATUS_0); // 无欠费(结账前)
		// detailcost.setIsqfreal(ConstUtil.QF_STATUS_0);
		// logic.updateSingleUUID(detailcost,
		// TableNameUtil.KQDS_COSTORDER_DETAIL, request);
		// }
		// }
		/******** 为了避免出错，建议由欠费的时候，不允许添加费用计划，或者只允许添加一个费用计划，否则容易逻辑混乱，导致出错！！！ ******/
		if (dp.getStatus().equals(ConstUtil.COST_ORDER_STATUS_0)) { // 0表示添加的是费用计划
			// 更新欠费项目 状态
			editQf(dp.getSeqId(), request); // type,
		} else if (dp.getStatus().equals(ConstUtil.COST_ORDER_STATUS_1)) {
			/** 这里的status为1 ，表示划价，通过此值，更改挂号记录的状态为 1,即等待结账 **/
			// 更新欠费项目 状态
			editQf(dp.getSeqId(), request); // type,
			// 判断开单的医生是否与挂号的医生不一样，如果不一样，则覆盖挂号表的医生
			logicreg.editStatus(dp.getDoctor(), dp.getRegno(), Integer.parseInt(dp.getStatus()), request);
			// 并修改就诊情况记录的医生
			// editJzqk(dp.getUsercode(),dbConn,dp.getDoctor(),reg.getDoctor(),dp.getRegno(),person);

			// 修改接诊状态为已接诊
			Map<String, String> map = new HashMap<String, String>();
			map.put("regno", dp.getRegno());
			List<KqdsReceiveinfo> en = (List<KqdsReceiveinfo>) dao.loadList(TableNameUtil.KQDS_RECEIVEINFO, map);
			if (en != null && en.size() > 0) {
				KqdsReceiveinfo receive = en.get(0);
				receive.setAskstatus(1); // 1 表示已接诊
				dao.updateSingleUUID(TableNameUtil.KQDS_RECEIVEINFO, receive);
			}
			// 添加提示消息 已开单
			List<JSONObject> personlist = new ArrayList<JSONObject>();
			personlist = personLogic.getAllShowfeiPerson(ChainUtil.getCurrentOrganization(request), request);
			// 添加提示信息 收费
			for (int i = 0; i < personlist.size(); i++) {
				PushUtil.saveTx4NewCostOrder(personlist.get(i).getString("seq_id"), request);
			}
		}

		// 开单的时候，判断是否需要设定患者档表的咨询人员
		KqdsReg reg = (KqdsReg) dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, dp.getRegno());
		if (!YZUtility.isNullorEmpty(askperson) && !YZUtility.isNullorEmpty(reg.getAskperson())) {
			/** 这里判断askperson不为空，是为了避免添加的消费项目类型为 预交金、挂号费等，而导致错误设置第一咨询？ **/
			userLogic.setUserDocAskPerson(request, dp.getUsercode(), reg.getAskperson());
		}
		return uuid;
	}

	/**
	 * 修改欠费
	 * 
	 * @param dbConn
	 * @param person
	 * @param costno
	 * @param type
	 *            0 新增费用单 1修改费用单
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void editQf(String costno, HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("costno", costno);
		// 获取该单下的项目列表
		List<KqdsCostorderDetail> listdetail = (List<KqdsCostorderDetail>) dao.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
		KqdsCostorder order = (KqdsCostorder) dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
		BigDecimal qf = BigDecimal.ZERO;
		for (KqdsCostorderDetail detail : listdetail) {
			// 新增、修改费用单时，更新消费项目的相关信息
			BigDecimal itemQF = dealCostItem(detail, request);
			// 更新
			dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
			// 累加
			qf = KqdsBigDecimal.add(qf, itemQF);
			/**************************************
			 * 新增和修改收费单的处理逻辑是一致的，新单中可能存在欠费，修改单的时候也可以添加新的收费项目
			 ***/
			// if (type.equals("0")) {// 新增
			// if (detail.getIsqfreal() == ConstUtil.QF_STATUS_1) {//
			// 存在旧的欠费单
			// detail.setY2(KqdsBigDecimal.sub(BigDecimal.ZERO,
			// detail.getPaymoney())); /** y2存负值，表示还款 **/
			// qf = KqdsBigDecimal.add(qf, detail.getY2());
			// } else {
			// if (flag) {// 新单新增，有欠费
			// detail.setQfbh(YZUtility.getUUID()); /** 欠费编号，是一个uuid随机数 **/
			// detail.setY2(detail.getArrearmoney());
			// detail.setStatus(ConstUtil.COST_DETAIL_STATUS_1); // 有欠费(结账前)
			// qf = KqdsBigDecimal.add(qf, detail.getY2());
			// }
			// if (flagarr) {// 新单新增 不欠费
			// detail.setQfbh("");
			// detail.setY2(BigDecimal.ZERO);
			// detail.setStatus(ConstUtil.COST_DETAIL_STATUS_0); // 无欠费(结账前)
			// // qf = KqdsBigDecimal.add(qf, detail.getY2()); /**
			// 这行代码没作用，因为y2值为0 **/
			// }
			// }
			// // order.setY2(qf);
			// // logic.updateSingleUUID(order,
			// TableNameUtil.KQDS_COSTORDER, request); /**
			// 对费用单的y2字段更新，放到循环体外面，避免多次更新 **/
			// logic.updateSingleUUID(detail,
			// TableNameUtil.KQDS_COSTORDER_DETAIL, request);
			// } else { // 修改费用单
			// if (detail.getIsqfreal() == 1) {// 存在旧的欠费单
			// detail.setY2(KqdsBigDecimal.sub(BigDecimal.ZERO,
			// detail.getPaymoney()));
			// qf = KqdsBigDecimal.add(qf, detail.getY2());
			// } else {
			// if (flag) {// 欠费单
			// detail.setQfbh(YZUtility.getUUID());
			// detail.setY2(detail.getArrearmoney());
			// detail.setStatus(ConstUtil.COST_DETAIL_STATUS_1); // 欠费
			// qf = KqdsBigDecimal.add(qf, detail.getY2());
			// }
			// if (flagarr) {// 不欠费单
			// detail.setQfbh("");
			// detail.setY2(BigDecimal.ZERO);
			// detail.setStatus(ConstUtil.COST_DETAIL_STATUS_0); // 不欠费
			// // qf = KqdsBigDecimal.add(qf, detail.getY2()); /**
			// 这行代码没作用，因为y2值为0 **/
			// }
			// }
			// // order.setY2(qf);
			// // logic.updateSingleUUID(order,
			// TableNameUtil.KQDS_COSTORDER, request); /**
			// 对费用单的y2字段更新，放到循环体外面，避免多次更新 **/
			// logic.updateSingleUUID(detail,
			// TableNameUtil.KQDS_COSTORDER_DETAIL, request);
			// }
		}
		order.setY2(qf); // 更改当前费用单的欠费金额
		dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER, order);
	}

	/**
	 * 新增、修改费用单时，更新消费项目的相关信息
	 * 
	 * @param dbConn
	 * @param detail
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public BigDecimal dealCostItem(KqdsCostorderDetail detail, HttpServletRequest request) throws Exception {
		BigDecimal arrearmoney = detail.getArrearmoney();
		if (arrearmoney == null) {
			throw new Exception("数据异常：消费项目的欠费金额值为Null");
		}
		// 确认划价
		// 如果欠费编号为空 为新单 ：存储y2的值 欠款情况 累计（第一次如果是1000 则存 1000）；
		// 如果欠费编号不为空 为旧单 ：存储y2的值 欠款情况 累计（第一次如果是1000 第二次 还款200 则存 -200）；

		// if (detail.getIsqfreal() == ConstUtil.QF_STATUS_1) {// 存在旧的欠费单
		if (YZUtility.isNotNullOrEmpty(detail.getQfbh())) { // 如果欠费编号有值，说明是还款
			/** 存在欠费编号时，判断这个欠费编号是不是对应一个数据，如果是，说明是新开的欠费项目还没结账，在进行修改操作 **/
			List<JSONObject> qflist = logicd.selectListByQfbh(detail.getQfbh());
			if (qflist == null || qflist.size() == 0) {
				throw new Exception("数据异常：根据欠费编号查询不到消费项目");
			}
			if (qflist.size() == 1) {
				detail.setY2(detail.getArrearmoney());
				/** 正数，表示欠费 **/
			} else { // 大于1条，说明该欠费编号之前已存在真实欠费
				detail.setY2(KqdsBigDecimal.sub(BigDecimal.ZERO, detail.getPaymoney()));
				/** y2存负值，表示还款 **/
			}
		} else {
			// 欠费金额不为空 大于0
			int arrearflag = KqdsBigDecimal.compareTo(arrearmoney, BigDecimal.ZERO);
			if (arrearflag > 0) {// 新单新增，有欠费
				// detail.setQfbh(YZUtility.getUUID()); /** 欠费编号在结账时设定 **/
				detail.setY2(detail.getArrearmoney());
				detail.setStatus(ConstUtil.COST_DETAIL_STATUS_1); // 有欠费(结账前)
			} else if (arrearflag == 0) {// 新单新增 不欠费
				// detail.setQfbh("");
				detail.setY2(BigDecimal.ZERO);
				detail.setStatus(ConstUtil.COST_DETAIL_STATUS_0); // 无欠费(结账前)
			} else {
				throw new Exception("数据异常：消费项目的欠费金额字段值为负数");
			}
		}
		return detail.getY2(); // 返回欠费金额，用于更新费用单
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public void deleteObj(String seqId, HttpServletRequest request) throws Exception {
		KqdsCostorder en = (KqdsCostorder) dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, seqId);
		String costno = en.getCostno();
		if (en.getStatus().equals(ConstUtil.COST_ORDER_STATUS_2)) {// 一结账不能删除
			throw new Exception("该费用单已结账，操作无效！");
		}
		// ResultSetTool.savebcjl(dbConn,en,person,en.getUsercode(),"删除","删除费用单",en.getRegno());
		dao.deleteSingleUUID(TableNameUtil.KQDS_COSTORDER, seqId);

		// 记录日志
		BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_COSTORDER, en, en.getUsercode(), TableNameUtil.KQDS_COSTORDER, request);

		// 删除费用单中项目
		Map<String, String> map = new HashMap<String, String>();
		map.put("costno", costno);
		List<KqdsCostorderDetail> list = (List<KqdsCostorderDetail>) dao.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
		for (KqdsCostorderDetail detail : list) {
			/** 什么逻辑都不要判断，直接删 **/
			dao.deleteSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail.getSeqId());
			BcjlUtil.LogBcjlWithUserCodeAndRegno(BcjlUtil.UPDATE, BcjlUtil.KQDS_COSTORDER, "删除费用单明细", en.getUsercode(), detail.getRegno(), false, TableNameUtil.KQDS_COSTORDER,
					request);
			// 清除costno为空 且 不是还款项目的记录（多次还款删除时 导致的垃圾数据）
			deleteLaji(request);
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public void editPaymoney(KqdsCostorder cost, KqdsCostorderDetail detail) throws Exception {
		dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER, cost);
		dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
	}
	
	/**
	 * 判断开处方单类型
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private String checkFyType(List<KqdsTreatitem> list) throws Exception{
		String type = "";
		for( int i = 0; i < list.size(); i++ ) {
			String status = "1".equals(list.get(i).getStatus()) ? list.get(i).getStatus() : "0";
			if(i == 0) {
				type = status;
			}else {
				if(!type.equals(status)) {
					throw new Exception("添加项目不能同时为药品和化验项目！");
				}
			}
		}
		return type;
	}
	
	/**
	 * ####################根据费用单号查询费用信息##################
	 * @param costno
	 * @return
	 * @throws Exception
	 */
	public KqdsCostorder findCostOrderByCostno(String costno) throws Exception {
		KqdsCostorder kCostorder = (KqdsCostorder) dao.findForObject("KQDS_COSTORDER.findCostOrderByCostno", costno);
		return kCostorder;
	}
	
	/**
	 * 判断当开单项目中包含药品时校验库存是否充足   2019-2-18添加
	 * @param list
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	private void orderDrugStock(Collection list) throws Exception{
		if(null != list && list.size() > 0) {
			List<String> paraList = new ArrayList<String>();
			KqdsCostorderDetail costDetail = null;
			for(Object obj : list) {
				costDetail = (KqdsCostorderDetail)obj;
				if((costDetail.getItemno()).indexOf("DRUG") != -1) {
					paraList.add(costDetail.getItemno());
				}
			}
			if(paraList.size() > 0) {
				List<YkzzDrugs> drugList =  ykzzDrugsDao.selectDrugsByOrderNoStr(paraList);
				//获取当前已结账但是药房未发的的药品数量
				List<KqdsCostorderDetail> noSendGrugList = kzzDrugsInDao.findCostOrderDetailNoSend();
				for(Object obj : list) {
					for(YkzzDrugs drug : drugList) {
						costDetail = (KqdsCostorderDetail)obj;
						if(costDetail.getItemno().equals(drug.getOrder_no())) {
							int noSendGrug = findNoSendGrug(noSendGrugList,costDetail.getItemno());
							if(Integer.valueOf(costDetail.getNum()) > (drug.getDrug_total_num()-noSendGrug)) {
								throw new Exception(costDetail.getItemname() + "库存不足");
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取当前待发的药品数量
	 * @param noSendGrug
	 * @param itemno
	 * @return
	 */
	private int findNoSendGrug(List<KqdsCostorderDetail> noSendGrugList,String itemno){
		int result = 0;
		if(null != noSendGrugList && noSendGrugList.size() > 0) {
			for(KqdsCostorderDetail kqdsCostorderDetail : noSendGrugList) {
				if(kqdsCostorderDetail.getItemno().equals(itemno)) {
					result += Integer.valueOf(kqdsCostorderDetail.getNum());
				}
			}
		}
		return result;
	}
	
	/**
	 * 如果当前收费单为药品单则需要判断classify字段是否相同
	 * @param type
	 * @throws Exception 
	 */
	private void checkIsClassType(String type,List<KqdsTreatitem> list) throws Exception{
		if(type.equals("1") || null != list) {
			//根据收费项目编号获取药品列表
			List<YkzzDrugsInDetail> idsStr = new ArrayList<YkzzDrugsInDetail>();
			YkzzDrugsInDetail tempObj = null;
			for(KqdsTreatitem item : list) {
				tempObj = new YkzzDrugsInDetail();
				tempObj.setDrugsId(item.getSeqId());
				idsStr.add(tempObj);
			}
			List<YkzzDrugs> drugsList = ykzzDrugsDao.selectDrugsByIdStr(idsStr);
			String classify = null;
			for(YkzzDrugs drug : drugsList) {
				if(YZUtility.isNotNullOrEmpty(classify)) {
					if(!classify.equals(drug.getClassify())) {
						throw new Exception("同一收费单只能包含一种分类的药品");
					}
				}else {
					classify = drug.getClassify();
				}
			}
		}
	}
	
	/**
	 * 返回患者缴费信息，便于前台建档筛选介绍人  2019-8-23 syp start
	  * @Title: findCostOrderByUsercode   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param usercode
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2019年8月23日 下午5:00:37
	 */
	public JSONObject findCostOrderByUsercode(String usercode) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.KQDS_COSTORDER + ".findCostOrderByUsercode", usercode);
		return json;
	}
	/**
	 * 查找患者所有缴费信息
	 * @param usercodes
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCostOrderByUsercodes(String usercodes) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER+".findCostOrderByUsercodes", usercodes);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCostOrderTuidanByUsercodes(String usercodes) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_TUIDAN+".findCostOrderTuidanByUsercodes", usercodes);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCostByUsercode(String usercode) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER+".findCostByUsercode", usercode);
		return list;
	}
	
	/**
	 * 折扣后也改变成交状态为已成交 syp 2019-10-07
	  * @Title: updateCostOrderCjstatus   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param seqId
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年10月7日 下午4:11:42
	 */
	public void updateCostOrderCjstatus(String seqId) throws Exception {
		dao.update(TableNameUtil.KQDS_COSTORDER + ".updateCostOrderCjstatus", seqId);
	}
	
	/**
	 * 根据患者编号和挂号分类为种植查询该患者所有的消费  syp
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findCostOrderByRegsortUsercode(String usercode) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("usercode", usercode);
//		dataMap.put("regsort", "18");
		dataMap.put("iscreateLclj", "1");
		dataMap.put("cjstatus", "1");
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderByRegsortUsercode", dataMap);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject findCostOrderByUserdocument(Map<String,String> map,BootStrapPage bp) throws Exception {
		List<JSONObject> list=new ArrayList<JSONObject>();
		if(!YZUtility.isNullorEmpty(map.get("czstarttime"))){
			map.put("czstarttime", map.get("czstarttime")+ ConstUtil.TIME_START);
		}
		if(!YZUtility.isNullorEmpty(map.get("czendtime"))){
			map.put("czendtime", map.get("czendtime")+ ConstUtil.TIME_END);
		}
		String status="";
			//日
		if(map.get("starttime").length()==10){
			map.put("startyear", map.get("starttime").substring(0,4));
			map.put("startmonth",map.get("starttime").substring(5,7));
			map.put("starttime", map.get("starttime") + ConstUtil.TIME_START);
			status="日";
			//月	
		}else if(map.get("starttime").length()==7){
			
			map.put("startyear", map.get("starttime").substring(0,4));
			map.put("startmonth",map.get("starttime").substring(5,7));
			map.put("starttime", map.get("starttime")+"-01"+ ConstUtil.TIME_START);
			status="月";
			//年
		}else if(map.get("starttime").length()==4){
			map.put("startyear", map.get("starttime"));
			map.put("starttime", map.get("starttime")+"-01-01"+ ConstUtil.TIME_START);
			status="年";
		}
			//日
		if(map.get("endtime").length()==10){
			map.put("endyear", map.get("endtime").substring(0,4));
			map.put("endmonth",map.get("endtime").substring(5,7));
			map.put("endtime", map.get("endtime") + ConstUtil.TIME_END);
			status="日";
			//月	
		}else if(map.get("endtime").length()==7){
			
			map.put("endyear", map.get("endtime").substring(0,4));
			map.put("endmonth",map.get("endtime").substring(5,7));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Calendar calendar = Calendar.getInstance();
			//一个月的天数
			calendar.setTime(sdf.parse(map.get("endtime")));
			int	s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			map.put("endtime", map.get("endtime")+"-"+s+ ConstUtil.TIME_END);
			status="月";
			//年
		}else if(map.get("endtime").length()==4){
			map.put("endyear", map.get("endtime"));
			map.put("endtime", map.get("endtime")+"-12-31"+ ConstUtil.TIME_END);
			status="年";
		}
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> alist = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderByUserdocument", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(alist);
		JSONObject jobj = new JSONObject();
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < alist.size(); i++) {
			if(i==alist.size()-1){
				str.append("\'"+alist.get(i).getString("usercode")+"\'");
			}else{
				str.append("\'"+alist.get(i).getString("usercode")+"\',");
			}
		}
		if(str.length()>0){
			map.put("usercodes", str.toString());
			//日
			if(status.equals("日")){
				list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderByUserdocumentDay", map);
				//月	
			}else if(status.equals("月")){
				//判断是不是同一年
				if(map.get("startyear").equals(map.get("endyear"))){
					//同一年
					list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderByUserdocumentMonth", map);
				}else{
					//不是同一年
					for (int i = Integer.parseInt(map.get("startyear")); i <= Integer.parseInt(map.get("endyear")); i++) {
						if(i == Integer.parseInt(map.get("startyear"))){
							map.put("startmonth",map.get("starttime").substring(5,7));
							map.put("endmonth","12");
							List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderByUserdocumentMonth", map);
							list.addAll(list1);
						}else if(i == Integer.parseInt(map.get("endyear"))){
							map.put("startmonth","1");
							map.put("endmonth", map.get("endtime").substring(5,7));
							List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderByUserdocumentMonth", map);
							list.addAll(list1);
						}else{
							map.put("startmonth","1");
							map.put("endmonth", "12");
							List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderByUserdocumentMonth", map);
							list.addAll(list1);
						}
					}
				}
				//年
			}else if(status.equals("年")){
				list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderByUserdocumentYear", map);
			}
		}
		List<Map<String,List<JSONObject>>> list1=new ArrayList<Map<String,List<JSONObject>>>();
		//患者信息
		if(alist.size()>0){
			//充值预交金
			List<JSONObject> yjjList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderYjj", map);
			//修复医生
			List<JSONObject> repairdoctorList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER + ".findCostOrderRepairdoctor", map);
			for (JSONObject jsonObject : alist) {
				for (JSONObject json: repairdoctorList) {
					if(jsonObject.getString("usercode").equals(json.getString("usercode"))){
						jsonObject.put("repairdoctorname", json.getString("repairdoctorname"));
					}
				}
				int a=0;
				for (JSONObject json : yjjList) {
					if(jsonObject.getString("usercode").equals(json.getString("usercode"))){
						a=1;
						jsonObject.put("paymoney", json.getString("paymoney"));
					}
				}
				if(a==0){
					jsonObject.put("paymoney","0");
				}
				Map<String,List<JSONObject>> map1= new HashMap<String,List<JSONObject>>();
				List<JSONObject> list3=new ArrayList<JSONObject>();
				list3.add(jsonObject);
				map1.put("usercode", list3);
				List<JSONObject> list4=new ArrayList<JSONObject>();
				//费用情况
				for (JSONObject json : list) {
					if(jsonObject.getString("usercode").equals(json.getString("usercode"))){
						list4.add(json);
					}
				}
				map1.put("money", list4);
				list1.add(map1);
			}
		}
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list1);
		return jobj;
	}
	
}