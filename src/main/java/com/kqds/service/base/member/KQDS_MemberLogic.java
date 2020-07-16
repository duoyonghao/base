package com.kqds.service.base.member;

//合并测试
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.kqds.entity.base.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.core.util.db.DBUtil;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;

@Service
public class KQDS_MemberLogic extends BaseLogic {
	private static Logger logger = LoggerFactory.getLogger(KQDS_MemberLogic.class);
	@Autowired
	private DaoSupport dao;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private YZFkfsLogic fkfsLogic;

	@SuppressWarnings("unchecked")
	public List<JSONObject> getMemberListByUserCode(String usercode) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER + ".getMemberListByUserCode", usercode);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getMemberListByMember(String memberno, String seqId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberno", memberno);
		map.put("seqId", seqId);
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER + ".getMemberListByMember", map);
		return list;
	}

	public void setCardno(String memberno1, String memberno2) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberno1", memberno1);
		map.put("memberno2", memberno2);
		dao.update(TableNameUtil.KQDS_MEMBER + ".updateCardno", map);
		dao.update(TableNameUtil.KQDS_MEMBER + ".updateShCardno", map);
	}

	public void yjjSubMoney(KqdsCostorder costlist, List<KqdsCostorderDetail> cost, KqdsMember en, HttpServletRequest request) throws Exception {
		// 查询收费明细表中 预交金 赠送
		BigDecimal money1 = BigDecimal.ZERO;
		BigDecimal money2 = BigDecimal.ZERO;

		for (KqdsCostorderDetail detail : cost) {
			if (detail.getPayyjj() != null) {
				money1 = KqdsBigDecimal.add(money1, detail.getPayyjj());
				money2 = KqdsBigDecimal.add(money2, detail.getPayother2());
			}
		}
		// 预交金扣款100 50 150
		en.setMoney(KqdsBigDecimal.sub(en.getMoney(), money1));
		en.setGivemoney(KqdsBigDecimal.sub(en.getGivemoney(), money2));
		dao.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);

		// 记录日志
		BcjlUtil.LogBcjlWithUserCode(BcjlUtil.PAY, BcjlUtil.KQDS_MEMBER, en, en.getUsercode(), TableNameUtil.KQDS_MEMBER, request);

		KqdsMemberRecord r = new KqdsMemberRecord();
		r.setSeqId(YZUtility.getUUID());
		r.setUsercode(costlist.getUsercode());
		r.setUsername(costlist.getUsername());
		r.setCardno(en.getMemberno());
		r.setCreatetime(costlist.getSftime());
		r.setCreateuser(costlist.getSfuser());
		r.setType("缴费");
		r.setCmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, money1));// 充值金额
																	// 如果是消费则保存负数
		r.setCgivemoney(KqdsBigDecimal.sub(BigDecimal.ZERO, money2));// 充值赠送金额
		BigDecimal ctotal = KqdsBigDecimal.add(money1, money2);
		r.setCtotal(KqdsBigDecimal.sub(BigDecimal.ZERO, ctotal));// 充值小计
		r.setYmoney(en.getMoney());// 本金余额
		r.setYgivemoney(en.getGivemoney());// 赠送余额
		r.setYtotal(KqdsBigDecimal.add(en.getMoney(), en.getGivemoney()));// 余额小计
		r.setCostno(costlist.getCostno());
		r.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
		dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
	}

	/**
	 * 根据usercode查询用户是否为会员 如果是 则返回会员等级
	 * 
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkIsMemberByUsercode(String usercode) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		// 该方法返回的list 不会为null，不需要做判断
		List<KqdsMember> en = (List<KqdsMember>) dao.loadList(TableNameUtil.KQDS_MEMBER, map);
		return en.size();
	}

	/**
	 * 【不做门诊条件过滤】 目前有三处调用此方法，分别为： 1、会员中心 2、预交金充值 3、退款申请 ，其中2、3不需要可见人员设置；且不需要门诊条件过滤
	 * 
	 * @param table
	 * @param map
	 * @param visualstaff
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectList(String table, Map<String, String> map, String visualstaff, String organization,JSONObject json,BootStrapPage bp) throws Exception {
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		map.put("visualstaff", visualstaff);
		if (map.containsKey("queryInput")) {
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryInput")));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryInput")));
		}
		if (map.containsKey("pagenum")) {
			if (map.get("pagenum").contains("3")) {// 3、退款申请使用
				map.put("pagenumsql", "3");
			}
			if (map.get("pagenum").contains("1")) {// 1、会员中心
				map.put("pagenumsql", "1");
			}
		}
		if(map.get("sortName")!=null){
			if(map.get("sortName").equals("usercode")){
				map.put("sortName", "m.usercode");
			}
			if(map.get("sortName").equals("username")){
				map.put("sortName", "m.username");
			}
			if(map.get("sortName").equals("askpersonname")){
				map.put("sortName", "p.user_name");
			}
			if(map.get("sortName").equals("createtime")){
				map.put("sortName", "m.createtime");
			}
			if(map.get("sortName").equals("memberno")){
				map.put("sortName", "m.memberno");
			}
			if(map.get("sortName").equals("icno")){
				map.put("sortName", "m.icno");
			}
			if(map.get("sortName").equals("memberlevel")){
				map.put("sortName", "kcit1.dict_name");
			}
			if(map.get("sortName").equals("discount")){
				map.put("sortName", "m.discount");
			}
			if(map.get("sortName").equals("discountdate")){
				map.put("sortName", "m.discountdate");
			}
			if(map.get("sortName").equals("money")){
				map.put("sortName", "m.money");
			}
			if(map.get("sortName").equals("jdr")){
					map.put("sortName", "p2.user_id");
			}
			if(map.get("sortName").equals("kfr")){
				map.put("sortName", "p3.user_id");
			}
			if(map.get("sortName").equals("remark")){
				map.put("sortName", "m.remark");
			}
			if(map.get("sortName").equals("devchannel")){
				map.put("sortName", "kcit3.dict_name");
			}
			if(map.get("sortName").equals("totalmoney")){
				map.put("sortName", "m.money+m.givemoney");
			}
		}
		List<JSONObject> list1 = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectListMoney", map);
		
		String search = bp.getSearch();
		String sort = bp.getSort();
		json.put("search", search);
		json.put("sort", sort);
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectList", map);
		
		// 去除重复记录
		List<JSONObject> list2 = new ArrayList<JSONObject>();
		for (JSONObject obj1 : list) {
			if (list2 == null || list2.size() == 0) {
				list2.add(obj1);
			} else {
				boolean flag = true;
				for (JSONObject obj2 : list2) {
					if (obj1.getString("memberno").equals(obj2.getString("memberno"))) {
						flag = false;
						break;
					}
				}
				if (flag) {
					list2.add(obj1);
				}
			}
		}
		for (JSONObject job : list2) {
			String memberstatus = job.getString("memberstatus");
			if (ConstUtil.MEMBER_STATUS_0.equals(memberstatus)) {
				memberstatus = ConstUtil.MEMBER_STATUS_0_DESC;
			} else if (ConstUtil.MEMBER_STATUS_1.equals(memberstatus)) {
				memberstatus = ConstUtil.MEMBER_STATUS_1_DESC;
			} else if (ConstUtil.MEMBER_STATUS_2.equals(memberstatus)) {
				memberstatus = ConstUtil.MEMBER_STATUS_2_DESC;
			} else if (ConstUtil.MEMBER_STATUS_3.equals(memberstatus)) {
				memberstatus = ConstUtil.MEMBER_STATUS_3_DESC;
			}
			job.put("memberstatus", memberstatus);
			float money = 0;
			if (!YZUtility.isNullorEmpty(job.getString("money"))) {
				money = Float.parseFloat(job.getString("money"));
			}
			float givemoney = 0;
			if (!YZUtility.isNullorEmpty(job.getString("givemoney"))) {
				givemoney = Float.parseFloat(job.getString("givemoney"));
			}
			job.put("totalmoney", givemoney + money);
		}
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();	
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list2);
		jobj.put("mon", list1);
		return jobj;
	}

	/**
	 * 预交金扣款
	 */
	@SuppressWarnings({ "unchecked" })
	@Transactional(rollbackFor = { Exception.class })
	public JSONObject saveChongzhi(String moneys, String types, String askperson, String regsort, String seqId, KqdsMember dp, YZPerson person, JSONObject czfs, String recordname,
			String tfremark, HttpServletRequest request) throws Exception {
		BigDecimal xjmoney = BigDecimal.ZERO;
		BigDecimal yhkmoney = BigDecimal.ZERO;
		BigDecimal qtmoney = BigDecimal.ZERO;
		BigDecimal zfbmoney = BigDecimal.ZERO;
		BigDecimal wxmoney = BigDecimal.ZERO;
		BigDecimal mmdmoney = BigDecimal.ZERO;
		BigDecimal bdfqmoney = BigDecimal.ZERO;
		BigDecimal totalmoney = BigDecimal.ZERO;// 总钱数

		String[] typesstrs = types.split(",");
		if (types.indexOf(",") != -1) {
			String[] moneyss = moneys.split(",");
			// 多种充值方式
			for (int i = 0; i < typesstrs.length; i++) {
				if (typesstrs[i].equals(fkfsLogic.selectSeqId4Memberfield("xjmoney"))) {
					xjmoney = new BigDecimal(moneyss[i]);
					totalmoney = KqdsBigDecimal.add(totalmoney, xjmoney);
				} else if (typesstrs[i].equals(fkfsLogic.selectSeqId4Memberfield("yhkmoney"))) {
					yhkmoney = new BigDecimal(moneyss[i]);
					totalmoney = KqdsBigDecimal.add(totalmoney, yhkmoney);
				} else if (typesstrs[i].equals(fkfsLogic.selectSeqId4Memberfield("zfbmoney"))) {
					zfbmoney = new BigDecimal(moneyss[i]);
					totalmoney = KqdsBigDecimal.add(totalmoney, zfbmoney);
				} else if (typesstrs[i].equals(fkfsLogic.selectSeqId4Memberfield("wxmoney"))) {
					wxmoney = new BigDecimal(moneyss[i]);
					totalmoney = KqdsBigDecimal.add(totalmoney, wxmoney);
				} else if (typesstrs[i].equals(fkfsLogic.selectSeqId4Memberfield("mmdmoney"))) {
					mmdmoney = new BigDecimal(moneyss[i]);
					totalmoney = KqdsBigDecimal.add(totalmoney, mmdmoney);
				} else if (typesstrs[i].equals(fkfsLogic.selectSeqId4Memberfield("bdfqmoney"))) {
					bdfqmoney = new BigDecimal(moneyss[i]);
					totalmoney = KqdsBigDecimal.add(totalmoney, bdfqmoney);
				} else if (typesstrs[i].equals(fkfsLogic.selectSeqId4Memberfield("qtmoney"))) {
					qtmoney = new BigDecimal(moneyss[i]);
					totalmoney = KqdsBigDecimal.add(totalmoney, qtmoney);
				}
			}
		} else {
			// 一种充值方式
			if (types.equals(fkfsLogic.selectSeqId4Memberfield("xjmoney"))) {
				xjmoney = new BigDecimal(moneys);
				totalmoney = KqdsBigDecimal.add(totalmoney, xjmoney);
			} else if (types.equals(fkfsLogic.selectSeqId4Memberfield("yhkmoney"))) {
				yhkmoney = new BigDecimal(moneys);
				totalmoney = KqdsBigDecimal.add(totalmoney, yhkmoney);
			} else if (types.equals(fkfsLogic.selectSeqId4Memberfield("zfbmoney"))) {
				zfbmoney = new BigDecimal(moneys);
				totalmoney = KqdsBigDecimal.add(totalmoney, zfbmoney);
			} else if (types.equals(fkfsLogic.selectSeqId4Memberfield("wxmoney"))) {
				wxmoney = new BigDecimal(moneys);
				totalmoney = KqdsBigDecimal.add(totalmoney, wxmoney);
			} else if (types.equals(fkfsLogic.selectSeqId4Memberfield("mmdmoney"))) {
				mmdmoney = new BigDecimal(moneys);
				totalmoney = KqdsBigDecimal.add(totalmoney, mmdmoney);
			} else if (types.equals(fkfsLogic.selectSeqId4Memberfield("bdfqmoney"))) {
				bdfqmoney = new BigDecimal(moneys);
				totalmoney = KqdsBigDecimal.add(totalmoney, bdfqmoney);
			} else if (types.equals(fkfsLogic.selectSeqId4Memberfield("qtmoney"))) {
				qtmoney = new BigDecimal(moneys);
				totalmoney = KqdsBigDecimal.add(totalmoney, qtmoney);
			}
		}
		KqdsMember en = new KqdsMember();
		if (!YZUtility.isNullorEmpty(seqId)) {
			en = (KqdsMember) dao.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
			en.setMoney(KqdsBigDecimal.add(en.getMoney(), totalmoney));
			en.setGivemoney(KqdsBigDecimal.add(en.getGivemoney(), dp.getGivemoney()));
			dao.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);
		} else {
			dp.setMoney(totalmoney);
			en = dp;
			dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			String HYK_BINDING = SysParaUtil.getSysValueByName(request, SysParaUtil.HYK_BINDING);
			if (HYK_BINDING.equals("0")) {
				dp.setIcno(dp.getMemberno());
				dp.setIsbinding(1);
			}
			dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
		}
		// 记录在操作记录表中
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", en.getUsercode());
		List<KqdsUserdocument> user = (List<KqdsUserdocument>) dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
		if (user == null || user.size() == 0) {
			throw new Exception("患者数据不存在");
		}
		/*
		 * else { KQDS_UserDocumentEntity enty = user.get(0); if
		 * (KqdsBigDecimal.compareTo(enty.getTotalpay(), BigDecimal.ZERO) > 0) {
		 * enty.setTotalpay(KqdsBigDecimal.add(enty.getTotalpay(), totalmoney)); } else
		 * { enty.setTotalpay(totalmoney); } }
		 */
		KqdsMemberRecord r = new KqdsMemberRecord();
		r.setSeqId(YZUtility.getUUID());
		r.setUsercode(user.get(0).getUsercode());
		r.setUsername(user.get(0).getUsername());
		r.setCardno(en.getMemberno());
		String ctime = YZUtility.getCurDateTimeStr();
		r.setCreatetime(ctime);
		r.setCreateuser(person.getSeqId());
		r.setType(recordname);
		r.setTfremark(tfremark);
		r.setContent(dp.getRemark());
		// 保存接诊咨询 挂号分类
		r.setAskperson(askperson);
		r.setRegsort(regsort);
		r.setXjmoney(xjmoney);
		r.setYhkmoney(yhkmoney);
		r.setQtmoney(qtmoney);
		r.setZfbmoney(zfbmoney);
		r.setWxmoney(wxmoney);
		r.setMmdmoney(mmdmoney);
		r.setBdfqmoney(bdfqmoney);
		r.setCmoney(totalmoney);// 充值金额
		r.setCgivemoney(dp.getGivemoney());// 充值赠送金额
		r.setCtotal(KqdsBigDecimal.add(totalmoney, dp.getGivemoney()));// 充值小计
		r.setYmoney(en.getMoney());// 本金余额
		r.setYgivemoney(en.getGivemoney());// 赠送余额
		r.setYtotal(KqdsBigDecimal.add(en.getMoney(), en.getGivemoney()));// 余额小计
		r.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
		dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
		// 记录日志
		BcjlUtil.LogBcjl(r.getSeqId(), BcjlUtil.KQDS_MEMBER_RECORD, r, TableNameUtil.KQDS_MEMBER_RECORD, request);
		czfs = new JSONObject();
		czfs.put("xjmoney", xjmoney);
		czfs.put("yhkmoney", yhkmoney);
		czfs.put("qtmoney", qtmoney);
		czfs.put("zfbmoney", zfbmoney);
		czfs.put("wxmoney", wxmoney);
		czfs.put("mmdmoney", mmdmoney);
		czfs.put("bdfqmoney", bdfqmoney);
		czfs.put("creatTime", ctime);
		czfs.put("recordId", r.getSeqId());
		return czfs;
	}

	/**
	 * 会员卡查询(统计) 【不做门诊条件过滤】
	 * 
	 * @param table
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectMemberTongji(String table, BootStrapPage bp, Map<String, String> map, String exportFlag, YZPerson person) throws Exception {

		String nameFlag = person.getUserId(); // YZUtility.getCurDateTimeStr(YZUtility.DATE_FORMAT_NOSPLIT_yyyyMMddHHmmss);
		String temp_member1 = "temp_member1_" + nameFlag;
		String temp_member2 = "temp_member2_" + nameFlag;
		String temp_member3 = "temp_member3_" + nameFlag;
		String temp_member4 = "temp_member4_" + nameFlag;
		String temp_member5 = "temp_member5_" + nameFlag;
		String temp_member6 = "temp_member6_" + nameFlag;
		String temp_member7 = "temp_member7_" + nameFlag;

		int firstIndex = bp.getOffset();
		int pageSize = bp.getLimit();
		int pageNum = firstIndex / pageSize; // 转换成页数

		JSONObject result = new JSONObject();
		String starttime = "";
		String endtime = "";
		if (!map.isEmpty()) {
			if (map.containsKey("starttime")) {
				starttime = map.get("starttime");
			}
			if (map.containsKey("endtime")) {
				endtime = map.get("endtime");
			}
		}
		// 执行时间统计
		// long start = System.currentTimeMillis();
		String check1 = null;
		String check2 = null;
		String check3 = null;
		String check4 = null;
		String check5 = null;
		String check6 = null;
		String check7 = null;
		if (DBUtil.isMysql()) {
			check1 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member1 + ";";
			check2 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member2 + ";";
			check3 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member3 + ";";
			check4 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member4 + ";";
			check5 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member5 + ";";
			check6 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member6 + ";";
			check7 = "DROP TEMPORARY TABLE IF EXISTS " + temp_member7 + ";";
		} else {
			check1 = "IF OBJECT_ID('tempdb..##" + temp_member1 + "') is not null drop table ##" + temp_member1 + "";
			check2 = "IF OBJECT_ID('tempdb..##" + temp_member2 + "') is not null drop table ##" + temp_member2 + "";
			check3 = "IF OBJECT_ID('tempdb..##" + temp_member3 + "') is not null drop table ##" + temp_member3 + "";
			check4 = "IF OBJECT_ID('tempdb..##" + temp_member4 + "') is not null drop table ##" + temp_member4 + "";
			check5 = "IF OBJECT_ID('tempdb..##" + temp_member5 + "') is not null drop table ##" + temp_member5 + "";
			check6 = "IF OBJECT_ID('tempdb..##" + temp_member6 + "') is not null drop table ##" + temp_member6 + "";
			check7 = "IF OBJECT_ID('tempdb..##" + temp_member7 + "') is not null drop table ##" + temp_member7 + "";
		}
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check1);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check2);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check3);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check4);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check5);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check6);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check7);

		String create1 = null;
		String create2 = null;
		String create3 = null;
		String create4 = null;
		String create5 = null;
		String create6 = null;
		String create7 = null;

		if (DBUtil.isMysql()) {
			create1 = "CREATE temporary table " + temp_member1
					+ "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50),zzmoney VARCHAR(50),zzgivemoney VARCHAR(50))";
			create2 = "CREATE temporary table " + temp_member2 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
			create3 = "CREATE temporary table " + temp_member3 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
			create4 = "CREATE temporary table " + temp_member4 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
			create5 = "CREATE temporary table " + temp_member5 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
			create6 = "CREATE temporary table " + temp_member6 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50))";
			create7 = "CREATE temporary table " + temp_member7
					+ "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50),zzmoney VARCHAR(50),zzgivemoney VARCHAR(50))";

		} else {
			create1 = "CREATE TABLE ##" + temp_member1
					+ "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50),zzmoney VARCHAR(50),zzgivemoney VARCHAR(50))";
			create2 = "CREATE TABLE ##" + temp_member2 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
			create3 = "CREATE TABLE ##" + temp_member3 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
			create4 = "CREATE TABLE ##" + temp_member4 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
			create5 = "CREATE TABLE ##" + temp_member5 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50))";
			create6 = "CREATE TABLE ##" + temp_member6 + "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50))";
			create7 = "CREATE TABLE ##" + temp_member7
					+ "(memberno VARCHAR(50),cmoney VARCHAR(50),cgivemoney VARCHAR(50) ,ctotal VARCHAR(50),zzmoney VARCHAR(50),zzgivemoney VARCHAR(50))";
		}

		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create1);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create2);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create3);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create4);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create5);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create6);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", create7);

		String insert1 = "";
		if (DBUtil.isMysql()) {
			insert1 += "INSERT INTO " + temp_member1 + "(memberno,cmoney, cgivemoney,ctotal,zzmoney,zzgivemoney)";
		} else {
			insert1 += "INSERT INTO ##" + temp_member1 + "(memberno,cmoney, cgivemoney,ctotal,zzmoney,zzgivemoney)";
		}
		insert1 += " SELECT ";
		insert1 += " cardno,";
		insert1 += " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
		insert1 += " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
		insert1 += " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal,";
		insert1 += " sum(" + SQLUtil.castAsFloat("zzmoney") + ") as zzmoney,";
		insert1 += " sum(" + SQLUtil.castAsFloat("zzgivemoney") + ") as zzgivemoney";
		insert1 += " FROM KQDS_MEMBER_RECORD dd";
		insert1 += " where  createtime <='" + starttime + "'";
		insert1 += " GROUP BY dd.cardno";

		String insert2 = "";
		if (DBUtil.isMysql()) {
			insert2 += "INSERT INTO " + temp_member2 + "(memberno,cmoney, cgivemoney,ctotal)";
		} else {
			insert2 += "INSERT INTO ##" + temp_member2 + "(memberno,cmoney, cgivemoney,ctotal)";
		}

		insert2 += " SELECT ";
		insert2 += " cardno,";
		insert2 += " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
		insert2 += " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
		insert2 += " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal";
		insert2 += " FROM KQDS_MEMBER_RECORD dd";
		insert2 += " where  type = '开卡' ";
		if (!YZUtility.isNullorEmpty(starttime)) {
			insert2 += " and createtime >='" + starttime + "' ";
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			insert2 += " and createtime <='" + endtime + "' ";
		}
		insert2 += " GROUP BY dd.cardno";

		String insert3 = "";
		if (DBUtil.isMysql()) {
			insert3 += "INSERT INTO " + temp_member3 + "(memberno,cmoney,cgivemoney,ctotal)";
		} else {
			insert3 += "INSERT INTO ##" + temp_member3 + "(memberno,cmoney,cgivemoney,ctotal)";
		}

		insert3 += " SELECT ";
		insert3 += " cardno,";
		insert3 += " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
		insert3 += " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
		insert3 += " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal";
		insert3 += " FROM KQDS_MEMBER_RECORD dd";
		insert3 += " where type = '充值' ";
		if (!YZUtility.isNullorEmpty(starttime)) {
			insert3 += " and createtime >='" + starttime + "' ";
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			insert3 += " and createtime <='" + endtime + "' ";
		}
		insert3 += " GROUP BY dd.cardno";

		String insert4 = "";
		if (DBUtil.isMysql()) {
			insert4 += "INSERT INTO " + temp_member4 + "(memberno,cmoney,cgivemoney,ctotal)";
		} else {
			insert4 += "INSERT INTO ##" + temp_member4 + "(memberno,cmoney,cgivemoney,ctotal)";
		}

		insert4 += " SELECT ";
		insert4 += " cardno,";
		insert4 += " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
		insert4 += " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
		insert4 += " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal";
		insert4 += " FROM KQDS_MEMBER_RECORD dd";
		insert4 += " where type = '缴费' ";
		if (!YZUtility.isNullorEmpty(starttime)) {
			insert4 += " and createtime >='" + starttime + "' ";
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			insert4 += " and createtime <='" + endtime + "' ";
		}
		insert4 += " GROUP BY dd.cardno";

		String insert5 = "";

		if (DBUtil.isMysql()) {
			insert5 += "INSERT INTO " + temp_member5 + "(memberno,cmoney,cgivemoney,ctotal)";
		} else {
			insert5 += "INSERT INTO ##" + temp_member5 + "(memberno,cmoney,cgivemoney,ctotal)";
		}
		insert5 += " SELECT ";
		insert5 += " cardno,";
		insert5 += " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
		insert5 += " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
		insert5 += " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal";
		insert5 += " FROM KQDS_MEMBER_RECORD dd";
		insert5 += " where type = '退费' ";
		if (!YZUtility.isNullorEmpty(starttime)) {
			insert5 += " and createtime >='" + starttime + "' ";
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			insert5 += " and createtime <='" + endtime + "' ";
		}
		insert5 += " GROUP BY dd.cardno";

		String insert6 = "";
		if (DBUtil.isMysql()) {
			insert6 += "INSERT INTO " + temp_member6 + "(memberno,cmoney,cgivemoney)";
		} else {
			insert6 += "INSERT INTO ##" + temp_member6 + "(memberno,cmoney,cgivemoney)";
		}

		insert6 += " SELECT ";
		insert6 += " cardno,";
		insert6 += " sum(" + SQLUtil.castAsFloat("zzmoney") + ") as cmoney,";
		insert6 += " sum(" + SQLUtil.castAsFloat("zzgivemoney") + ") as cgivemoney";
		// insert6 += " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal";
		// /** 小计= zzmoney + zzgivemoney **/
		insert6 += " FROM KQDS_MEMBER_RECORD dd";
		insert6 += " where (type = '转赠' or type = '受赠') ";
		if (!YZUtility.isNullorEmpty(starttime)) {
			insert6 += " and createtime >='" + starttime + "' ";
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			insert6 += " and createtime <='" + endtime + "' ";
		}
		insert6 += " GROUP BY dd.cardno";

		String insert7 = "";
		if (DBUtil.isMysql()) {
			insert7 += "INSERT INTO " + temp_member7 + "(memberno,cmoney,cgivemoney,ctotal,zzmoney,zzgivemoney)";
		} else {
			insert7 += "INSERT INTO ##" + temp_member7 + "(memberno,cmoney,cgivemoney,ctotal,zzmoney,zzgivemoney)";
		}
		insert7 += " SELECT ";
		insert7 += " cardno,";
		insert7 += " sum(" + SQLUtil.castAsFloat("cmoney") + ") as cmoney,";
		insert7 += " sum(" + SQLUtil.castAsFloat("cgivemoney") + ") as cgivemoney,";
		insert7 += " sum(" + SQLUtil.castAsFloat("ctotal") + ") as ctotal,";
		insert7 += " sum(" + SQLUtil.castAsFloat("zzmoney") + ") as zzmoney,";
		insert7 += " sum(" + SQLUtil.castAsFloat("zzgivemoney") + ") as zzgivemoney";
		insert7 += " FROM KQDS_MEMBER_RECORD dd";
		insert7 += " where  createtime <='" + endtime + "'";
		insert7 += " GROUP BY dd.cardno";

		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert1);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert2);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert3);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert4);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert5);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert6);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", insert7);

		StringBuffer sb = new StringBuffer();
		if (DBUtil.isMysql()) {
			sb.append(" LEFT JOIN " + temp_member1 + " tp1 on tp1.memberno =  m.memberno "); // 临时表1
			sb.append(" LEFT JOIN " + temp_member2 + " tp2 on tp2.memberno =  m.memberno "); // 临时表2
			sb.append(" LEFT JOIN " + temp_member3 + " tp3 on tp3.memberno =  m.memberno "); // 临时表3
			sb.append(" LEFT JOIN " + temp_member4 + " tp4 on tp4.memberno =  m.memberno "); // 临时表4
			sb.append(" LEFT JOIN " + temp_member5 + " tp5 on tp5.memberno =  m.memberno "); // 临时表5
			sb.append(" LEFT JOIN " + temp_member6 + " tp6 on tp6.memberno =  m.memberno "); // 临时表6
			sb.append(" LEFT JOIN " + temp_member7 + " tp7 on tp7.memberno =  m.memberno "); // 临时表7
		} else {
			sb.append(" LEFT JOIN ##" + temp_member1 + " tp1 on tp1.memberno =  m.memberno "); // 临时表1
			sb.append(" LEFT JOIN ##" + temp_member2 + " tp2 on tp2.memberno =  m.memberno "); // 临时表2
			sb.append(" LEFT JOIN ##" + temp_member3 + " tp3 on tp3.memberno =  m.memberno "); // 临时表3
			sb.append(" LEFT JOIN ##" + temp_member4 + " tp4 on tp4.memberno =  m.memberno "); // 临时表4
			sb.append(" LEFT JOIN ##" + temp_member5 + " tp5 on tp5.memberno =  m.memberno "); // 临时表5
			sb.append(" LEFT JOIN ##" + temp_member6 + " tp6 on tp6.memberno =  m.memberno "); // 临时表6
			sb.append(" LEFT JOIN ##" + temp_member7 + " tp7 on tp7.memberno =  m.memberno "); // 临时表7
		}
		sb.append(" where 1=1 ");
		map.put("joinsql", sb.toString());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectList2", map);

		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check1);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check2);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check3);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check4);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check5);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check6);
		dao.update(TableNameUtil.KQDS_MEMBER + ".EXECUTE_UPDATE", check7);

		BigDecimal tdqcmoney = BigDecimal.ZERO, tdqcgivemoney = BigDecimal.ZERO, tdqctotal = BigDecimal.ZERO, tdkkmoney = BigDecimal.ZERO, tdkkgivemoney = BigDecimal.ZERO,
				tdkktotal = BigDecimal.ZERO, tdczmoney = BigDecimal.ZERO, tdczgivemoney = BigDecimal.ZERO, tdcztotal = BigDecimal.ZERO, tdjfmoney = BigDecimal.ZERO,
				tdjfgivemoney = BigDecimal.ZERO, tdjftotal = BigDecimal.ZERO, tdtfmoney = BigDecimal.ZERO, tdtfgivemoney = BigDecimal.ZERO, tdtftotal = BigDecimal.ZERO,
				tdzzmoney = BigDecimal.ZERO, tdzzgivemoney = BigDecimal.ZERO, tdzztotal = BigDecimal.ZERO, tdmoney = BigDecimal.ZERO, tdgivemoney = BigDecimal.ZERO,
				tdtotal = BigDecimal.ZERO;
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = list.get(i);

			String memberno = json.getString("memberno");
			json.put("memberno", memberno);
			String field = json.getString("memberstatus");
			String value = "";
			if (field.equals(ConstUtil.MEMBER_STATUS_0_DESC)) {
				value = "未激活";
			} else if (field.equals(ConstUtil.MEMBER_STATUS_1_DESC)) {
				value = "正常";
			} else if (field.equals(ConstUtil.MEMBER_STATUS_2_DESC)) {
				value = "已挂失";
			} else if (field.equals(ConstUtil.MEMBER_STATUS_3_DESC)) {
				value = "已补办";
			}
			json.put("memberstatus", value);

			// 期初充值余额，期初赠送余额，期初余额小计
			// json.putAll(selectAllQc( memberno, starttime, "qcmoney",
			// "qcgivemoney", "qctotal"));
			// deal 1
			BigDecimal cmoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney1")) ? "0.00" : json.getString("cmoney1"));
			BigDecimal zzmoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney1")) ? "0.00" : json.getString("zzmoney1"));
			BigDecimal cgivemoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney1")) ? "0.00" : json.getString("cgivemoney1"));
			BigDecimal zzgivemoney1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney1")) ? "0.00" : json.getString("zzgivemoney1"));
			BigDecimal ctotal1 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal1")) ? "0.00" : json.getString("ctotal1"));
			json.put("qcmoney", cmoney1.add(zzmoney1));
			json.put("qcgivemoney", cgivemoney1.add(zzgivemoney1));
			json.put("qctotal", ctotal1.add(zzmoney1).add(zzgivemoney1));

			// 查询开卡时充值的总金额、总赠送、总余额
			// json.putAll(selectAll( memberno, starttime, endtime, "开卡",
			// "kkmoney", "kkgivemoney", "kktotal"));
			// deal 2
			BigDecimal cmoney2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney2")) ? "0.00" : json.getString("cmoney2"));
			BigDecimal cgivemoney2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney2")) ? "0.00" : json.getString("cgivemoney2"));
			BigDecimal ctotal2 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal2")) ? "0.00" : json.getString("ctotal2"));
			json.put("kkmoney", cmoney2);
			json.put("kkgivemoney", cgivemoney2);
			json.put("kktotal", ctotal2);

			// 充值的统计
			// json.putAll(selectAll( memberno, starttime, endtime, "充值",
			// "czmoney", "czgivemoney", "cztotal"));
			// deal 3
			BigDecimal cmoney3 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney3")) ? "0.00" : json.getString("cmoney3"));
			BigDecimal cgivemoney3 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney3")) ? "0.00" : json.getString("cgivemoney3"));
			BigDecimal ctotal3 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal3")) ? "0.00" : json.getString("ctotal3"));
			json.put("czmoney", cmoney3);
			json.put("czgivemoney", cgivemoney3);
			json.put("cztotal", ctotal3);

			// 缴费的统计
			// json.putAll(selectAll( memberno, starttime, endtime,
			// ConstUtil.MEMBER_RECORD_TYPE_JF, "jfmoney", "jfgivemoney",
			// "jftotal"));
			// deal 4
			BigDecimal cmoney4 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney4")) ? "0.00" : json.getString("cmoney4"));
			BigDecimal cgivemoney4 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney4")) ? "0.00" : json.getString("cgivemoney4"));
			BigDecimal ctotal4 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal4")) ? "0.00" : json.getString("ctotal4"));
			json.put("jfmoney", cmoney4);
			json.put("jfgivemoney", cgivemoney4);
			json.put("jftotal", ctotal4);

			// 退费的统计
			// json.putAll(selectAll( memberno, starttime, endtime, "退费",
			// "tfmoney", "tfgivemoney", "tftotal"));
			// deal 5
			BigDecimal cmoney5 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney5")) ? "0.00" : json.getString("cmoney5"));
			BigDecimal cgivemoney5 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney5")) ? "0.00" : json.getString("cgivemoney5"));
			BigDecimal ctotal5 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal5")) ? "0.00" : json.getString("ctotal5"));
			json.put("tfmoney", cmoney5);
			json.put("tfgivemoney", cgivemoney5);
			json.put("tftotal", ctotal5);

			// 转赠的统计
			// json.putAll(selectAllZz( memberno, starttime, endtime, "转赠",
			// "zzmoney", "zzgivemoney", "zztotal"));
			// deal 6
			BigDecimal ctotal6 = BigDecimal.ZERO;
			BigDecimal cmoney6 = BigDecimal.ZERO;
			BigDecimal cgivemoney6 = BigDecimal.ZERO;
			if (!YZUtility.isNullorEmpty(json.getString("cmoney6"))) {
				cmoney6 = new BigDecimal(json.getString("cmoney6"));
				ctotal6 = ctotal6.add(new BigDecimal(json.getString("cmoney6")));
			}
			if (!YZUtility.isNullorEmpty(json.getString("cgivemoney6"))) {
				cgivemoney6 = new BigDecimal(json.getString("cgivemoney6"));
				ctotal6 = ctotal6.add(new BigDecimal(json.getString("cgivemoney6")));
			}
			json.put("zzmoney", cmoney6);
			json.put("zzgivemoney", cgivemoney6);
			json.put("zztotal", ctotal6);

			// 期初充值余额，期初赠送余额，期初余额小计
			// json.putAll(selectAllQm( memberno, endtime, "qmmoney",
			// "qmgivemoney", "qmtotal"));
			// deal 7
			BigDecimal cmoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney7")) ? "0.00" : json.getString("cmoney7"));
			BigDecimal zzmoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney7")) ? "0.00" : json.getString("zzmoney7"));
			BigDecimal cgivemoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney7")) ? "0.00" : json.getString("cgivemoney7"));
			BigDecimal zzgivemoney7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney7")) ? "0.00" : json.getString("zzgivemoney7"));
			BigDecimal ctotal7 = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal7")) ? "0.00" : json.getString("ctotal7"));
			json.put("qmmoney", cmoney7.add(zzmoney7));
			json.put("qmgivemoney", cgivemoney7.add(zzgivemoney7));
			json.put("qmtotal", ctotal7.add(zzmoney7).add(zzgivemoney7));

			if (firstIndex == 0) { // 第一次加载统计
				tdqcmoney = tdqcmoney.add(new BigDecimal(json.getString("qcmoney")));
				tdqcgivemoney = tdqcgivemoney.add(new BigDecimal(json.getString("qcgivemoney")));
				tdqctotal = tdqctotal.add(new BigDecimal(json.getString("qctotal")));

				tdkkmoney = tdkkmoney.add(new BigDecimal(json.getString("kkmoney")));
				tdkkgivemoney = tdkkgivemoney.add(new BigDecimal(json.getString("kkgivemoney")));
				tdkktotal = tdkktotal.add(new BigDecimal(json.getString("kktotal")));

				tdczmoney = tdczmoney.add(new BigDecimal(json.getString("czmoney")));
				tdczgivemoney = tdczgivemoney.add(new BigDecimal(json.getString("czgivemoney")));
				tdcztotal = tdcztotal.add(new BigDecimal(json.getString("cztotal")));

				tdjfmoney = tdjfmoney.add(new BigDecimal(json.getString("jfmoney")));
				tdjfgivemoney = tdjfgivemoney.add(new BigDecimal(json.getString("jfgivemoney")));
				tdjftotal = tdjftotal.add(new BigDecimal(json.getString("jftotal")));

				tdtfmoney = tdtfmoney.add(new BigDecimal(json.getString("tfmoney")));
				tdtfgivemoney = tdtfgivemoney.add(new BigDecimal(json.getString("tfgivemoney")));
				tdtftotal = tdtftotal.add(new BigDecimal(json.getString("tftotal")));

				tdzzmoney = tdzzmoney.add(new BigDecimal(json.getString("zzmoney")));
				tdzzgivemoney = tdzzgivemoney.add(new BigDecimal(json.getString("zzgivemoney")));
				tdzztotal = tdzztotal.add(new BigDecimal(json.getString("zztotal")));

				tdmoney = tdmoney.add(new BigDecimal(json.getString("qmmoney")));
				tdgivemoney = tdgivemoney.add(new BigDecimal(json.getString("qmgivemoney")));
				tdtotal = tdtotal.add(new BigDecimal(json.getString("qmtotal")));
			}
		}

		if (firstIndex == 0) { // 第一次加载统计
			result.put("tdqcmoney", tdqcmoney);
			result.put("tdqcgivemoney", tdqcgivemoney);
			result.put("tdqctotal", tdqctotal);
			result.put("tdkkmoney", tdkkmoney);
			result.put("tdkkgivemoney", tdkkgivemoney);
			result.put("tdkktotal", tdkktotal);
			result.put("tdczmoney", tdczmoney);
			result.put("tdczgivemoney", tdczgivemoney);
			result.put("tdcztotal", tdcztotal);
			result.put("tdjfmoney", tdjfmoney);
			result.put("tdjfgivemoney", tdjfgivemoney);
			result.put("tdjftotal", tdjftotal);
			result.put("tdtfmoney", tdtfmoney);
			result.put("tdtfgivemoney", tdtfgivemoney);
			result.put("tdtftotal", tdtftotal);
			result.put("tdzzmoney", tdzzmoney);
			result.put("tdzzgivemoney", tdzzgivemoney);
			result.put("tdzztotal", tdzztotal);
			result.put("tdmoney", tdmoney);
			result.put("tdgivemoney", tdgivemoney);
			result.put("tdtotal", tdtotal);
		}

		// 导出操作
		if (exportFlag != null && exportFlag.equals("exportTable")) {
			result.put("list", list);
		} else {
			List<JSONObject> pageList = pagingByList(list, pageSize, pageNum);
			for (JSONObject json : pageList) {
				firstIndex++;
				json.put("rownumber", firstIndex);
			}

			result.put("total", list.size());
			// list 要分页处理
			result.put("rows", pageList);
		}

		// System.out.println(System.currentTimeMillis() - start);

		return result;
	}

	/**
	 * 对list进行分页
	 * 
	 * @param list
	 * @param pagesize
	 * @param pageNo
	 *            第一页从0开始
	 */
	@SuppressWarnings("rawtypes")
	public List pagingByList(List list, int pagesize, int pageNo) {

		int totalcount = list.size();
		int pagecount = 0;
		int m = totalcount % pagesize;
		if (m > 0) {
			pagecount = totalcount / pagesize + 1;
		} else {
			pagecount = totalcount / pagesize;
		}

		List subList = null;
		if (m == 0) { // 记录数是 PageSize的整倍数
			subList = list.subList(pageNo * pagesize, pagesize * (pageNo + 1));
		} else {
			if ((pageNo + 1) == pagecount) {
				subList = list.subList(pageNo * pagesize, totalcount);
			} else {
				subList = list.subList(pageNo * pagesize, pagesize * (pageNo + 1));
			}
		}
		return subList;

	}

	// 查询期初余额
	// private JSONObject selectAllQc(String memberno, String
	// starttime, String key1, String key2, String key3) throws Exception {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select ");
	// sb.append("sum(cast(cmoney as float)) as cmoney,");
	// sb.append("sum(cast(cgivemoney as float)) as cgivemoney,");
	// sb.append("sum(cast(ctotal as float)) as ctotal,");
	// sb.append("sum(cast(zzmoney as float)) as zzmoney,");
	// sb.append("sum(cast(zzgivemoney as float)) as zzgivemoney ");
	// sb.append(" from KQDS_MEMBER_RECORD ");
	// sb.append(" where cardno = '" + memberno + "' ");
	// if (!YZUtility.isNullorEmpty(starttime)) {
	// sb.append(" and createtime <='" + starttime + "' ");
	// }
	// JSONObject json = new JSONObject();
	// List<JSONObject> list = logic.getJsonListByResultSet(
	// sb.toString());
	// if (list != null && list.size() > 0) {
	// json = list.get(0);
	//
	// BigDecimal cmoney = new
	// BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" :
	// json.getString("cmoney"));
	// BigDecimal zzmoney = new
	// BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney")) ? "0.00" :
	// json.getString("zzmoney"));
	// BigDecimal cgivemoney = new
	// BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00"
	// : json.getString("cgivemoney"));
	// BigDecimal zzgivemoney = new
	// BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney")) ?
	// "0.00" : json.getString("zzgivemoney"));
	// BigDecimal ctotal = new
	// BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" :
	// json.getString("ctotal"));
	//
	// json.put(key1, cmoney.add(zzmoney));
	// json.put(key2, cgivemoney.add(zzgivemoney));
	// json.put(key3, ctotal.add(zzmoney).add(zzgivemoney));
	// }
	// return json;
	// }

	/**
	 * 查询期末余额
	 * 
	 * @param memberno
	 * @param endtime
	 * @param key1
	 *            qmmoney
	 * @param key2
	 *            qmgivemoney
	 * @param key3
	 *            qmtotal
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectAllQm(String memberno, String endtime, String key1, String key2, String key3) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberno", memberno);
		if (!YZUtility.isNullorEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		JSONObject json = new JSONObject();
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER + ".selectAllQm", map);
		if (list != null && list.size() > 0) {
			json = list.get(0);

			BigDecimal cmoney = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" : json.getString("cmoney"));
			BigDecimal zzmoney = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzmoney")) ? "0.00" : json.getString("zzmoney")); // 本金赠送的金额
			BigDecimal cgivemoney = new BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00" : json.getString("cgivemoney"));
			BigDecimal zzgivemoney = new BigDecimal(YZUtility.isNullorEmpty(json.getString("zzgivemoney")) ? "0.00" : json.getString("zzgivemoney")); // 赠送，赠送的金额
			BigDecimal ctotal = new BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" : json.getString("ctotal"));

			json.put(key1, cmoney.add(zzmoney)); // 本金余额
			json.put(key2, cgivemoney.add(zzgivemoney)); // 赠送余额
			json.put(key3, ctotal.add(zzmoney).add(zzgivemoney));

		}
		return json;
	}

	/**
	 * 根据usercode查询总余额
	 * 
	 * @param usercode
	 * @param sftime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getSymoneyByUsercode(String usercode, String sftime) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		if (!YZUtility.isNullorEmpty(sftime)) {
			map.put("sftime", sftime);
		}
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_MEMBER + ".getSymoneyByUsercode", map);
		JSONObject obj = new JSONObject();
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal givemoney = BigDecimal.ZERO;
		for (JSONObject rs : list) {
			if (!YZUtility.isNullorEmpty(rs.getString("cmoney"))) {
				if (KqdsBigDecimal.compareTo(new BigDecimal(rs.getString("cmoney")), BigDecimal.ZERO) != 0) {
					money = money.add(new BigDecimal(rs.getString("cmoney"))).add(new BigDecimal(rs.getString("zzmoney")));
					obj.put(ConstUtil.MEMBER_MONEY, money);
				}
			}
			if (!YZUtility.isNullorEmpty(rs.getString("cgivemoney"))) {
				if (KqdsBigDecimal.compareTo(new BigDecimal(rs.getString("cgivemoney")), BigDecimal.ZERO) != 0) {
					givemoney = givemoney.add(new BigDecimal(rs.getString("cgivemoney"))).add(new BigDecimal(rs.getString("zzgivemoney")));
					obj.put(ConstUtil.MEMBER_GIVEMONEY, givemoney);
				}
			}
		}

		return obj;
	}

	// 查询总金额、总赠送、总余额
	// private JSONObject selectAll(String memberno, String
	// starttime, String endtime, String type, String key1, String key2, String
	// key3) throws Exception {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select ");
	// sb.append("sum(cast(cmoney as float)) as cmoney,");
	// sb.append("sum(cast(cgivemoney as float)) as cgivemoney,");
	// sb.append("sum(cast(ctotal as float)) as ctotal ");
	// sb.append(" from KQDS_MEMBER_RECORD ");
	// sb.append(" where type = '" + type + "' and cardno = '" + memberno +
	// "' ");
	// if (!YZUtility.isNullorEmpty(starttime)) {
	// sb.append(" and createtime >='" + starttime + "' ");
	// }
	// if (!YZUtility.isNullorEmpty(endtime)) {
	// sb.append(" and createtime <='" + endtime + "' ");
	// }
	// JSONObject json = new JSONObject();
	// List<JSONObject> list = logic.getJsonListByResultSet(
	// sb.toString());
	// if (list != null && list.size() > 0) {
	// json = list.get(0);
	//
	// BigDecimal cmoney = new
	// BigDecimal(YZUtility.isNullorEmpty(json.getString("cmoney")) ? "0.00" :
	// json.getString("cmoney"));
	// BigDecimal cgivemoney = new
	// BigDecimal(YZUtility.isNullorEmpty(json.getString("cgivemoney")) ? "0.00"
	// : json.getString("cgivemoney"));
	// BigDecimal ctotal = new
	// BigDecimal(YZUtility.isNullorEmpty(json.getString("ctotal")) ? "0.00" :
	// json.getString("ctotal"));
	//
	// json.put(key1, cmoney);
	// json.put(key2, cgivemoney);
	// json.put(key3, ctotal);
	// }
	// return json;
	//
	// }

	// 查询转赠
	// private JSONObject selectAllZz(String memberno, String
	// starttime, String endtime, String type, String key1, String key2, String
	// key3) throws Exception {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select ");
	// sb.append("sum(zzmoney) as cmoney,");
	// sb.append("sum(zzgivemoney) as cgivemoney,");
	// sb.append("sum(ctotal) as ctotal ");
	// sb.append(" from KQDS_MEMBER_RECORD ");
	// sb.append("where (type = '" + ConstUtil.MEMBER_RECORD_TYPE_ZZ +
	// "' or type = '" + ConstUtil.MEMBER_RECORD_TYPE_SZ + "') and cardno = '" +
	// memberno + "' ");
	// if (!YZUtility.isNullorEmpty(starttime)) {
	// sb.append(" and createtime >='" + starttime + "' ");
	// }
	// if (!YZUtility.isNullorEmpty(endtime)) {
	// sb.append(" and createtime <='" + endtime + "' ");
	// }
	// JSONObject json = new JSONObject();
	// List<JSONObject> list = logic.getJsonListByResultSet(
	// sb.toString());
	// if (list != null && list.size() > 0) {
	// json = list.get(0);
	// BigDecimal ctotal = BigDecimal.ZERO;
	// BigDecimal cmoney = BigDecimal.ZERO;
	// BigDecimal cgivemoney = BigDecimal.ZERO;
	// if (!YZUtility.isNullorEmpty(json.getString("cmoney"))) {
	// cmoney = new BigDecimal(json.getString("cmoney"));
	// ctotal = ctotal.add(new BigDecimal(json.getString("cmoney")));
	// }
	// if (!YZUtility.isNullorEmpty(json.getString("cgivemoney"))) {
	// cgivemoney = new BigDecimal(json.getString("cgivemoney"));
	// ctotal = ctotal.add(new BigDecimal(json.getString("cgivemoney")));
	// }
	// json.put(key1, cmoney);
	// json.put(key2, cgivemoney);
	// json.put(key3, ctotal);
	// }
	// return json;
	// }

	/**********************************************************
	 * 整合过来的方法
	 *********************************************************************/

	/**
	 * 建档时，自动创建预交金卡
	 * 
	 * @param user
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void addMember4CreateUserDocument(KqdsUserdocument user, YZPerson person, HttpServletRequest request) throws Exception {
		KqdsMember dp = new KqdsMember();

		dp.setSeqId(YZUtility.getUUID());
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		dp.setCreateuser(person.getSeqId());
		// 默认卡号为usercode
		// String HYK_BINDING = SysParaUtil.getSysValueByName(request,
		// SysParaUtil.HYK_BINDING);
		// if (HYK_BINDING.equals("0")) {
		dp.setIcno(user.getUsercode());
		dp.setIsbinding(1);
		// }
		dp.setMemberno(user.getUsercode());
		// 默认为会员卡号后6位
		dp.setPassword(dp.getMemberno().substring(dp.getMemberno().length() - 6, dp.getMemberno().length()));
		// 预交金卡
		String level = dictLogic.getDictIdByNameAndParentCode(DictUtil.HYKFL, "预交金卡");
		dp.setMemberlevel(level);
		dp.setMemberstatus("1");
		dp.setUsername(user.getUsername());
		dp.setUsercode(user.getUsercode());
		dp.setMoney(BigDecimal.ZERO);
		dp.setGivemoney(BigDecimal.ZERO);
		dp.setDiscount(100);
		dp.setRemark("预交金卡");
		dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
		dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
	}

	/**
	 * 设置IC卡号 等于 会员卡号
	 *
	 * @param table
	 * @throws Exception
	 */
	public void editIcno(String table) throws Exception {
		dao.update(TableNameUtil.KQDS_MEMBER + ".editIcno", null);
	}

	/**
	 * 清空IC卡号
	 *
	 * @param table
	 * @throws Exception
	 */
	public void emptyIcno(String table) throws Exception {
		dao.update(TableNameUtil.KQDS_MEMBER + ".emptyIcno", null);
	}

	/**
	 * 退款转换预交金
	 * @param newcostno 新生成的编号
	 * @param regno 挂号编号
	 * @param listdata 退款明细
	 * @param person
	 * @param request
	 * @param addyjj 转换预交金金额
	 * @throws Exception
	 */
	public void addChongzhi(String newcostno, String regno, String listdata, YZPerson person, HttpServletRequest request ,BigDecimal addyjj ,String usercode) throws Exception {
		JSONArray jArray = JSONArray.fromObject(listdata);
		List<?> list2 = JSONArray.toList(jArray, new KqdsRefundDetail(), new JsonConfig());
		KqdsRefundDetail detail = (KqdsRefundDetail) list2.get(0);
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberno", usercode);

		KqdsMemberRecord r = new KqdsMemberRecord();
		try {
			List<KqdsMember> list = (List<KqdsMember>) dao.loadList(TableNameUtil.KQDS_MEMBER, map);

			KqdsMember en = list.get(0);
			en.setMoney(KqdsBigDecimal.add(en.getMoney(), addyjj));
			dao.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);

			r.setSeqId(YZUtility.getUUID());
			r.setUsercode(detail.getUsercode());
			r.setUsername(en.getUsername());
			r.setCardno(en.getMemberno());
			r.setCreatetime(YZUtility.getCurDateTimeStr());
			r.setCreateuser(person.getSeqId());
			r.setType(ConstUtil.MEMBER_RECORD_TYPE_CZ);
			r.setAskperson(detail.getAskperson());
			r.setRegsort(regno);
			r.setXjmoney(detail.getPayxj());
			r.setYhkmoney(detail.getPaybank());
			r.setQtmoney(detail.getPayother1());
			r.setZfbmoney(detail.getPayzfb());
			r.setWxmoney(detail.getPaywx());
			r.setMmdmoney(detail.getPaymmd());
			r.setBdfqmoney(detail.getPaybdfq());
			r.setCmoney(detail.getPaymoney());// 充值金额
			r.setCtotal(detail.getPaymoney());// 充值小计
			r.setYmoney(en.getMoney());// 本金余额
			r.setYgivemoney(en.getGivemoney());// 赠送余额
			r.setYtotal(KqdsBigDecimal.add(en.getMoney(), en.getGivemoney()));// 余额小计
			r.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			r.setCostno(newcostno);
			dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);

			KqdsCostorder en1 =new KqdsCostorder();
			en1.setSeqId(newcostno);
			en1.setSftime(YZUtility.getCurDateTimeStr());
			en1.setSfuser(person.getSeqId());
			en1.setStatus(ConstUtil.COST_ORDER_STATUS_2);
			dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER, en1);

		} catch (Exception e) {
			logger.error("此患者预交金卡不存在，请先进行开户操作! 谢谢合作");
		}
		// 记录日志
		BcjlUtil.LogBcjl(r.getSeqId(), BcjlUtil.KQDS_MEMBER_RECORD, r, TableNameUtil.KQDS_MEMBER_RECORD, request);

	}
}