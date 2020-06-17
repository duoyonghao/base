package com.kqds.service.sys.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;

@Service
public class YZRegisterLogic extends BaseLogic {

	@Autowired
	private DaoSupport dao;
	/**
	 * 手机号码验证
	 * 
	 * @param dbConn
	 * @param seqId
	 * @param phonenumber1
	 * @param phonenumber2
	 * @param table
	 * @return
	 * @throws Exception
	 */
	/*
	 * public int checksjhm(String sjhm, String table) throws Exception { String sb
	 * = "select count(1) as num from " + table + " where 1=1 "; if
	 * (!YZUtility.isNullorEmpty(sjhm)) { sb += " and sjhm = '" + sjhm + "' "; } int
	 * num = logic.selectCountBySql(sb); return num; }
	 *//**
		 * 初始化 下级部门人员
		 * 
		 * @param conn
		 * @param dept
		 * @return
		 * @throws Exception
		 */
	/*
	 * @SuppressWarnings("unchecked") public void
	 * registerNextInit(HttpServletRequest request, YZDept dept, String sjhm) throws
	 * Exception { Map<String, String> maps = new HashMap<String, String>(); String
	 * ORGANIZATION = YZSysProps.getProp(SysParaUtil.ORGANIZATION); Map<String,
	 * String> map = new HashMap<String, String>(); map.put("dept_code",
	 * ORGANIZATION); map.put("orgflag", "0"); List<YZDept> deptlist =
	 * (List<YZDept>) logic.loadList( YZDept.class, map, TableNameUtil.SYS_DEPT);
	 * JSONObject jobj = getpaibantime( ORGANIZATION); for (YZDept nextdept :
	 * deptlist) { // 部门初始化 String deptId = YZUtility.getUUID(); String olddeptId =
	 * nextdept.getSeqId(); nextdept.setSeqId(deptId);
	 * nextdept.setDeptParent(dept.getSeqId());
	 * nextdept.setDeptCode(dept.getDeptCode());
	 * nextdept.setCreatetime(dept.getCreatetime()); logic.saveSingleUUID( nextdept,
	 * TableNameUtil.SYS_DEPT, request);
	 * 
	 * maps.put(nextdept.getDeptName(), deptId);
	 * 
	 * // 人员初始化 Map<String, String> mapuser = new HashMap<String, String>();
	 * mapuser.put("dept_id", olddeptId); List<YZPerson> perlist = (List<YZPerson>)
	 * logic.loadList( YZPerson.class, mapuser, TableNameUtil.SYS_PERSON); for
	 * (YZPerson per : perlist) { if (per.getUserName().contains("admin")) {
	 * continue; } per.setSeqId(YZUtility.getUUID()); per.setUserId(per.getUserId()
	 * + nextdept.getDeptCode().substring(4)); per.setUserName(per.getUserName() +
	 * nextdept.getDeptCode().substring(4)); String pass =
	 * HttpSenderTest.getRandNum(1, 999999);
	 * per.setPassword(YZPassEncrypt.encryptPass(pass)); per.setDeptId(deptId);
	 * per.setCreatetime(nextdept.getCreatetime()); logic.saveSingleUUID( per,
	 * TableNameUtil.SYS_PERSON, request); if (per.getUserName().contains("管理员")) {
	 * // 短信告知 String content = "感谢使用口腔大师，用户名：" + per.getUserId() + "，密码：" + pass +
	 * "。欢迎来电咨询：4000256668，期待与您的合作。"; HttpSenderTest.getSenderTx(sjhm, content); }
	 * // 默认排一个月的班 setpaiban( per, dept, jobj); } } // 设置角色 setuserpriv( maps, dept,
	 * request); }
	 *//**
		 * 默认排一个月的班
		 * 
		 * @param request
		 * @param fileitem
		 * @param mtype
		 * @return
		 * @throws Exception
		 */
	/*
	 * public void setpaiban(Connection YZPerson per, YZDept dept, JSONObject jobj)
	 * throws Exception { String startdate = YZUtility.getCurDateTimeStr(); String
	 * enddate = YZUtility.getDateTimeStr(YZUtility.getDayAfter(new Date(), 30));
	 * List<String> datelist = YZUtility.getAllDate(startdate, enddate); for (String
	 * date : datelist) { KQDS_Paiban dp = new KQDS_Paiban();
	 * dp.setSeqId(YZUtility.getUUID()); dp.setPersonid(per.getSeqId());
	 * dp.setStartdate(date + " " + jobj.getString("startdate") + ":00");
	 * dp.setEnddate(date + " " + jobj.getString("enddate") + ":00");
	 * dp.setOrderstatus(jobj.getString("typename")); dp.setDeptid(per.getDeptId());
	 * dp.setOrganization(dept.getDeptCode()); logic.saveSingleUUID(dp,
	 * TableNameUtil.KQDS_PAIBAN, null); } }
	 *//**
		 * 查询值班时间
		 * 
		 * @param request
		 * @param fileitem
		 * @param mtype
		 * @return
		 * @throws Exception
		 */
	/*
	 * public JSONObject getpaibantime(String organization) throws Exception {
	 * JSONObject jobj = new JSONObject(); Map<String, String> map = new
	 * HashMap<String, String>(); map.put("typename", "值班"); map.put("organization",
	 * organization); StringBuffer sb = new StringBuffer(); sb.append(
	 * " select seq_id,typename,startdate,enddate,createtime,createuser  from  " +
	 * TableNameUtil.KQDS_PAIBAN_TYPE); sb.append(" where 1=1 "); if (map.size() >
	 * 0) { for (Object o : map.keySet()) { sb.append(" and " + o + " = '" +
	 * map.get(o) + "' "); } } sb.append(" order by createtime desc");
	 * List<JSONObject> list = logic.getJsonListByResultSet(sb.toString()); if (list
	 * != null && list.size() > 0) { jobj = list.get(0); } return jobj; }
	 *//**
		 * 导入数据
		 * 
		 * @param request
		 * @param fileitem
		 * @param mtype
		 * @return
		 * @throws Exception
		 */
	/*
	 * public String setck(HttpServletRequest request, String organization) throws
	 * Exception { List<List<String>> list = null; try { list = ExcelTool.read(new
	 * FileInputStream(YZSysProps.getWebPath() + "\\model\\商品导入模板.xls"), true); }
	 * catch (Exception e) { e.printStackTrace(); }
	 * 
	 * for (int i = 1; i < list.size(); i++) { // 行数 根据抬头算 否则容易漏值为空的列 int rowslength
	 * = list.get(0).size(); // 遍历行 List<String> rows = list.get(i); if
	 * (YZUtility.isNullorEmpty(rows.get(0))) { break; } String value = ""; for (int
	 * j = 0; j < rowslength; j++) { // 遍历列 String li = rows.get(j); // 防止漏值为空的列 if
	 * (YZUtility.isNullorEmpty(li)) { value += li + " |||"; } else { value += li +
	 * "|||"; } } String[] val = value.split("\\|\\|\\|"); try { importCKGoods(val,
	 * organization, request); } catch (Exception e) { throw new Exception("导入数据：第"
	 * + (i + 1) + "行数据格式有误：" + e.getMessage() + "，请重新导入！"); }
	 * 
	 * } return "成功导入数据" + (list.size() - 1) + "条！"; }
	 *//**
		 * 商品导入
		 * 
		 * @param dbConn
		 * @param val
		 * @param person
		 * @param request
		 * @throws Exception
		 */
	/*
	 * @SuppressWarnings("unchecked") public void importCKGoods(String[] val, String
	 * organization, HttpServletRequest request) throws Exception { // 商品编号不能为空 if
	 * (YZUtility.isNullorEmpty(val[2])) { throw new Exception("商品编号不能为空"); } //
	 * 商品名称不能为空 if (YZUtility.isNullorEmpty(val[3])) { throw new
	 * Exception("商品名称不能为空"); } // 数量格式合法性校验 if (YZUtility.isNullorEmpty(val[6])) {
	 * throw new Exception("数量不能为空"); } // 仓库不能为空 if
	 * (YZUtility.isNullorEmpty(val[9])) { throw new Exception("仓库不能为空"); } int nums
	 * = 0; try { nums = (int) Float.parseFloat(val[6].trim()); } catch
	 * (NumberFormatException e) { throw new Exception("数量格式有误"); }
	 * 
	 * // 上限告警数 int maxnums = 0; if (!YZUtility.isNullorEmpty(val[14])) { try {
	 * maxnums = (int) Float.parseFloat(val[14].trim()); } catch
	 * (NumberFormatException e) { throw new Exception("上限告警数格式有误"); } } // 上限告警
	 * 开启/关闭 int maxgj = 0; if (!YZUtility.isNullorEmpty(val[15])) { if
	 * (val[15].equals("是")) { maxgj = 1; } } // 下限告警数 int minnums = 0; if
	 * (!YZUtility.isNullorEmpty(val[16])) { try { minnums = (int)
	 * Float.parseFloat(val[16].trim()); } catch (NumberFormatException e) { throw
	 * new Exception("下限告警数格式有误"); } } // 下限告警 开启/关闭 int mingj = 0; if
	 * (!YZUtility.isNullorEmpty(val[17])) { if (val[15].equals("是")) { mingj = 1; }
	 * } // 查询商品一级分类 Map<String, String> map = new HashMap<String, String>();
	 * map.put("goodstype", val[0]); map.put("perid", "0"); map.put("ORGANIZATION",
	 * organization); List<KQDS_Ck_Goodstype> en = (List<KQDS_Ck_Goodstype>)
	 * logic.loadList(KQDS_Ck_Goodstype.class, map,
	 * TableNameUtil.KQDS_CK_GOODS_TYPE); KQDS_Ck_Goodstype goodstype = null;
	 * KQDS_Ck_Goodstype nextgoodstype = null; if (en == null || en.size() == 0) {
	 * // 一级类别不存在，创建该一级分类 goodstype = saveGoodsType(val[0], "0", organization,
	 * request); // 创建二级分类 nextgoodstype = saveGoodsType(val[1],
	 * goodstype.getSeqId(), organization, request); } else { // 存在一级分类 goodstype =
	 * en.get(0); // 判断是否存在二级分类 map.put("goodstype", val[1]); map.put("perid",
	 * goodstype.getSeqId()); List<KQDS_Ck_Goodstype> nexten =
	 * (List<KQDS_Ck_Goodstype>) logic.loadList(KQDS_Ck_Goodstype.class, map,
	 * TableNameUtil.KQDS_CK_GOODS_TYPE); if (nexten == null || nexten.size() == 0)
	 * {// 不存在二级分类 // 创建二级分类 nextgoodstype = saveGoodsType(val[1],
	 * goodstype.getSeqId(), organization, request); } else { nextgoodstype =
	 * nexten.get(0); } } // 验证商品编号 Map<String, String> map2 = new HashMap<String,
	 * String>(); map2.put("goodscode", val[2]); map2.put("organization",
	 * organization); // 当前所在门诊 List<KQDS_Ck_Goods_Detail> listDetail =
	 * (List<KQDS_Ck_Goods_Detail>) logic.loadList(KQDS_Ck_Goods_Detail.class, map2,
	 * TableNameUtil.KQDS_CK_GOODS_DETAIL); if (listDetail != null &&
	 * listDetail.size() > 0) { throw new Exception("商品编号已存在！"); } // 验证仓库是否存在
	 * KQDS_Ck_House house = null; if (!YZUtility.isNullorEmpty(val[9])) {
	 * Map<String, String> map3 = new HashMap<String, String>();
	 * map3.put("housename", val[9]); map3.put("organization", organization); //
	 * 当前所在门诊 List<KQDS_Ck_House> houselist = (List<KQDS_Ck_House>)
	 * logic.loadList(KQDS_Ck_House.class, map3, TableNameUtil.KQDS_CK_HOUSE); if
	 * (houselist == null || houselist.size() == 0) { // 所属仓库不存在 house =
	 * saveHouse(val[9], organization, request); } else { house = houselist.get(0);
	 * } } if (!YZUtility.isNullorEmpty(val[10])) { if
	 * (YZUtility.isNullorEmpty(val[11])) { throw new Exception("供应商编号不能为空！"); } //
	 * 验证供应商是否存在 Map<String, String> map4 = new HashMap<String, String>();
	 * map4.put("suppliercode", val[11]); map4.put("organization", organization); //
	 * 当前所在门诊 List<KQDS_Ck_Supplier> supplierlist = (List<KQDS_Ck_Supplier>)
	 * logic.loadList(KQDS_Ck_Supplier.class, map4, TableNameUtil.KQDS_CK_SUPPLIER);
	 * if (supplierlist == null || supplierlist.size() == 0) { // 供应商不存在
	 * saveSupplier(val[10], val[11], organization, request); } else { // 验证 供应商编号
	 * 是否对应该供应商 if (!supplierlist.get(0).getSuppliername().equals(val[10])) { throw
	 * new Exception("供应商编号重复！"); } } } // 校验 库存*单价 = 金额 BigDecimal goodsprice = new
	 * BigDecimal(Float.parseFloat(val[7].trim())).setScale(2,
	 * BigDecimal.ROUND_HALF_DOWN); BigDecimal goodspricereal = BigDecimal.ZERO; if
	 * (nums != 0) { goodspricereal = new BigDecimal(val[8].trim()).divide(new
	 * BigDecimal(nums), 2, RoundingMode.HALF_EVEN).setScale(2,
	 * BigDecimal.ROUND_HALF_DOWN); if (KqdsBigDecimal.compareTo(goodsprice,
	 * goodspricereal) != 0) { throw new Exception("单价有误"); } } else { // 数量是0 不做判断
	 * } String kuwei = "", remark = ""; // 库位 if
	 * (!YZUtility.isNullorEmpty(val[12])) { kuwei = val[12]; } // 备注 if
	 * (!YZUtility.isNullorEmpty(val[13])) { remark = val[13]; } // 将数据插入到商品基础表
	 * KQDS_Ck_Goods_Detail detail = new KQDS_Ck_Goods_Detail();
	 * detail.setSeqId(YZUtility.getUUID());
	 * detail.setGoodstype(nextgoodstype.getSeqId());
	 * detail.setOrganization(organization); // ### // 【前端页面调用，当前所在门诊为准】
	 * detail.setGoodscode(val[2]); detail.setGoodsname(val[3]);
	 * detail.setGoodsnorms(val[4] == null ? "" : val[4]);
	 * detail.setGoodsunit(val[5] == null ? "" : val[5]); detail.setKuwei(kuwei);
	 * detail.setRemark(remark);
	 * detail.setCreatetime(YZUtility.getCurDateTimeStr()); detail.setSortno(1);
	 * detail.setMinnums(minnums); detail.setMaxnums(maxnums);
	 * detail.setMingj(mingj); detail.setMaxgj(maxgj);
	 * detail.setOrganization(organization); // ### // 【前端页面调用，当前所在门诊为准】
	 * logic.saveSingleUUID(detail, "KQDS_Ck_Goods_Detail", request);
	 * 
	 * // 将数据插入到商品库存表 KQDS_Ck_Goods entity = new KQDS_Ck_Goods();
	 * entity.setSeqId(YZUtility.getUUID());
	 * entity.setGoodsdetailid(detail.getSeqId());
	 * entity.setSshouse(house.getSeqId()); entity.setGoodsprice(goodspricereal);
	 * entity.setKcmoney(new BigDecimal(val[8].trim()).setScale(2,
	 * BigDecimal.ROUND_HALF_DOWN)); entity.setNums(nums);
	 * entity.setNumsexport(nums);// 导入数量 entity.setGoodspriceexport(new
	 * BigDecimal(val[8].trim()).setScale(2, BigDecimal.ROUND_HALF_DOWN));// 导入金额
	 * entity.setOrganization(organization); // ### // 【前端页面调用，当前所在门诊为准】
	 * logic.saveSingleUUID(entity, TableNameUtil.KQDS_CK_GOODS, request); }
	 *//**
		 * 添加商品类别
		 * 
		 * @param dbConn
		 * @param goodstypename
		 * @param perid
		 * @param person
		 * @param request
		 * @return
		 * @throws Exception
		 */
	/*
	 * private KQDS_Ck_Goodstype saveGoodsType(String goodstypename, String perid,
	 * String organization, HttpServletRequest request) throws Exception { //
	 * 一级类别不存在，创建该一级分类 KQDS_Ck_Goodstype goodstype = new KQDS_Ck_Goodstype();
	 * goodstype.setSeqId(YZUtility.getUUID()); // 根据中文 对应拼音首字母 String typeno =
	 * ChineseCharToEn.getAllFirstLetter(goodstype.getGoodstype());
	 * goodstype.setTypeno(typeno); goodstype.setGoodstype(goodstypename);
	 * goodstype.setSortno(1); goodstype.setPerid(perid);
	 * goodstype.setCreatetime(YZUtility.getCurDateTimeStr());
	 * goodstype.setOrganization(organization); // ### // 【前端页面调用，当前所在门诊为准】
	 * logic.saveSingleUUID(goodstype, TableNameUtil.KQDS_CK_GOODS_TYPE, request);
	 * return goodstype; }
	 *//**
		 * 添加仓库
		 * 
		 * @param dbConn
		 * @param housename
		 * @param person
		 * @param request
		 * @return
		 * @throws Exception
		 */
	/*
	 * private static KQDS_Ck_House saveHouse(String housename, String organization,
	 * HttpServletRequest request) throws Exception { KQDS_Ck_House house = new
	 * KQDS_Ck_House(); house.setSeqId(YZUtility.getUUID());
	 * house.setHousename(housename); house.setSortno(1);
	 * house.setCreatetime(YZUtility.getCurDateTimeStr());
	 * house.setOrganization(organization); // ### // 【前端页面调用，当前所在门诊为准】
	 * logic.saveSingleUUID(house, TableNameUtil.KQDS_CK_HOUSE, request); return
	 * house; }
	 *//**
		 * 添加供应商
		 * 
		 * @param dbConn
		 * @param suppliername
		 * @param suppliercode
		 * @param person
		 * @param request
		 * @return
		 * @throws Exception
		 */
	/*
	 * private KQDS_Ck_Supplier saveSupplier(String suppliername, String
	 * suppliercode, String organization, HttpServletRequest request) throws
	 * Exception { KQDS_Ck_Supplier supplier = new KQDS_Ck_Supplier();
	 * supplier.setSeqId(YZUtility.getUUID());
	 * supplier.setSuppliername(suppliername);
	 * supplier.setSuppliercode(suppliercode); supplier.setSortno(1);
	 * supplier.setCreatetime(YZUtility.getCurDateTimeStr());
	 * supplier.setOrganization(organization); // ### // 【前端页面调用，当前所在门诊为准】
	 * logic.saveSingleUUID(supplier, TableNameUtil.KQDS_CK_SUPPLIER, request);
	 * return supplier; }
	 * 
	 * @SuppressWarnings("unchecked") public void setuserpriv(Map<String, String>
	 * maps, YZDept dept, HttpServletRequest request) throws Exception { String
	 * ORGANIZATION = YZSysProps.getProp(SysParaUtil.ORGANIZATION); Map<String,
	 * String> map = new HashMap<String, String>(); map.put("organization",
	 * ORGANIZATION); List<YZPriv> privlist = (List<YZPriv>) logic.loadList(
	 * YZPriv.class, map, TableNameUtil.USER_PRIV); for (YZPriv userpriv : privlist)
	 * { String oldpriv = userpriv.getSeqId(); String newSeqId =
	 * YZUtility.getUUID(); // System.out.println(dept.getDeptName() + "_" + //
	 * userpriv.getPrivName() + "###" + newSeqId); userpriv.setSeqId(newSeqId);
	 * userpriv.setPrivName(dept.getDeptName() + "_" + userpriv.getPrivName());
	 * userpriv.setCreatetime(dept.getCreatetime()); userpriv.setCreateuser("");
	 * userpriv.setVisualPerson(""); // 设置预约可见部门 if
	 * (!YZUtility.isNullorEmpty(userpriv.getVisualDept())) { // 获取预约可见部门 String
	 * visualDeptStr = getVisDeptStr( userpriv.getVisualDept(), maps);
	 * userpriv.setVisualDept(visualDeptStr); } userpriv.setOrderVisualPerson("");
	 * // 设置预约可见部门 if (!YZUtility.isNullorEmpty(userpriv.getOrderVisualDept())) { //
	 * 获取预约可见部门 String orderVisualDeptStr = getVisDeptStr(
	 * userpriv.getOrderVisualDept(), maps);
	 * userpriv.setOrderVisualDept(orderVisualDeptStr); }
	 * userpriv.setOrganization(dept.getDeptCode()); logic.saveSingleUUID( userpriv,
	 * TableNameUtil.USER_PRIV, request);
	 * 
	 * // 设置用户角色值 根据原角色id 及 新门诊编号查询 患者 List<JSONObject> usetlist = getBaseUserInfo(
	 * oldpriv, dept.getDeptCode()); if (usetlist != null && usetlist.size() > 0) {
	 * for (JSONObject jboj : usetlist) { YZPerson per = (YZPerson)
	 * JSONObject.toBean(jboj, YZPerson.class); per.setUserPriv(newSeqId); //
	 * System.out.println(dept.getDeptName() + "_" + // userpriv.getPrivName() +
	 * "###" + newSeqId + "###" + // per.getUserName()); logic.updateSingleUUID(
	 * per, TableNameUtil.SYS_PERSON, request); } } List<JSONObject> usetlist2 =
	 * getBaseUserInfo2( oldpriv, dept.getDeptCode()); if (usetlist2 != null &&
	 * usetlist2.size() > 0) { for (JSONObject jboj : usetlist2) { YZPerson per =
	 * (YZPerson) JSONObject.toBean(jboj, YZPerson.class); // 删除 oldpriv 值 替换为 新角色ID
	 * userpriv.getSeqId() String[] oldprivArr = per.getUserPrivOther().split(",");
	 * String newpriv = ""; for (String priv : oldprivArr) { if
	 * (!priv.equals(oldpriv)) { newpriv += priv + ","; } else { newpriv +=
	 * userpriv.getSeqId() + ","; } } if (newpriv.endsWith(",")) { newpriv =
	 * newpriv.substring(0, newpriv.length() - 1); } per.setUserPrivOther(newpriv);
	 * logic.updateSingleUUID( per, TableNameUtil.SYS_PERSON, request); } } //
	 * 更新配置文件 角色值 Map<String, String> mapPara = new HashMap<String, String>(); if
	 * (userpriv.getPrivName().contains("现场咨询") ||
	 * userpriv.getPrivName().contains("咨询主管")) { mapPara.put("para_name",
	 * "PRIV_ASK_SEQID"); } else if (userpriv.getPrivName().contains("收费")) {
	 * mapPara.put("para_name", "PRIV_SHOUFEI_SEQID"); } else if
	 * (userpriv.getPrivName().contains("院长")) { mapPara.put("para_name",
	 * "PRIV_YUANZHANG_SEQID"); } else if (userpriv.getPrivName().contains("医生")) {
	 * mapPara.put("para_name", "PRIV_DOCTOR_SEQID"); } else if
	 * (userpriv.getPrivName().contains("仓库")) { mapPara.put("para_name",
	 * "PRIV_CK_SEQID"); } else if (userpriv.getPrivName().contains("总经理")) {
	 * mapPara.put("para_name", "PRIV_ZONGJINGLI_SEQID"); } // else if
	 * (userpriv.getPrivName().contains("主管")) { // mapPara.put("para_name",
	 * "PRIV_ZHUGUAN_SEQID"); // } else if (userpriv.getPrivName().contains("市场")) {
	 * mapPara.put("para_name", "PRIV_SHICHANG_SEQID"); } else if
	 * (userpriv.getPrivName().contains("网络咨询")) { mapPara.put("para_name",
	 * "PRIV_WANGDIAN_SEQID"); }
	 * 
	 * List<YZPara> paralist = (List<YZPara>) logic.loadList( YZPara.class, mapPara,
	 * TableNameUtil.SYS_PARA); setParaValue( paralist, userpriv.getSeqId());
	 * 
	 * } }
	 * 
	 * public void setParaValue(List<YZPara> paralist, String addvalue) throws
	 * Exception { if (paralist != null && paralist.size() > 0) { YZPara para =
	 * paralist.get(0); para.setParaValue(para.getParaValue() + "," + addvalue);
	 * logic.updateSingleUUID( para, TableNameUtil.SYS_PARA, null); } }
	 * 
	 * public String getVisDeptStr(String visualDeptStrs, Map<String, String> map)
	 * throws Exception { String visDeptStr = ""; String[] visualDeptArrs =
	 * visualDeptStrs.split(","); for (String deptid : visualDeptArrs) { if
	 * (deptid.equals("orgId")) { visDeptStr += deptid + ","; continue; } YZDept en
	 * = (YZDept) logic.loadObjSingleUUID( YZDept.class, deptid,
	 * TableNameUtil.SYS_DEPT); for (String newdeptname : map.keySet()) { String
	 * newdeptId = map.get(newdeptname); if (en.getDeptName().equals(newdeptname)) {
	 * visDeptStr += newdeptId + ","; } } } if (visDeptStr.endsWith(",")) {
	 * visDeptStr = visDeptStr.substring(0, visDeptStr.length()); } return
	 * visDeptStr; }
	 *//**
		 * 根据患者角色 门诊查询
		 * 
		 * @param dbConn
		 * @param userCode
		 * @return
		 * @throws Exception
		 */
	/*
	 * public List<JSONObject> getBaseUserInfo(String userpriv, String deptcode)
	 * throws Exception { StringBuffer sb = new StringBuffer();
	 * sb.append("select "); sb.append("p.* "); sb.append(" from  " +
	 * TableNameUtil.SYS_PERSON + " as p "); sb.append(" left join " +
	 * TableNameUtil.SYS_DEPT + " d on  d.seq_id = p.dept_id ");
	 * sb.append(" where 1=1 "); sb.append(" and P.user_priv = '" + userpriv +
	 * "' "); sb.append(" and d.dept_code = '" + deptcode + "' ");
	 * 
	 * List<JSONObject> list = logic.getJsonListByResultSet(sb.toString());
	 * 
	 * return list; }
	 *//**
		 * 根据患者辅助角色 门诊查询
		 * 
		 * @param dbConn
		 * @param userCode
		 * @return
		 * @throws Exception
		 */
	/*
	 * public List<JSONObject> getBaseUserInfo2(String userpriv, String deptcode)
	 * throws Exception { StringBuffer sb = new StringBuffer();
	 * sb.append("select "); sb.append("p.* "); sb.append(" from  " +
	 * TableNameUtil.SYS_PERSON + " as p "); sb.append(" left join " +
	 * TableNameUtil.SYS_DEPT + " d on  d.seq_id = p.dept_id ");
	 * sb.append(" where 1=1 "); sb.append(" and (P.user_priv_other like '%" +
	 * userpriv + ",%' or P.user_priv_other like '%," + userpriv + "%') ");
	 * sb.append(" and d.dept_code = '" + deptcode + "' ");
	 * 
	 * List<JSONObject> list = logic.getJsonListByResultSet(sb.toString());
	 * 
	 * return list; }
	 *//**
		 * 获取患者编号【线程同步】
		 * 
		 * @param conn
		 * @param organization
		 * @return
		 * @throws Exception
		 */
	/*
	 * public static String getDeptcodeNumstr() throws Exception { int num = 0;
	 * String numstr = ""; String maxusercode = getMaxDeptcode(
	 * TableNameUtil.SYS_DEPT); if (YZUtility.isNullorEmpty(maxusercode)) { // 空表时
	 * // 不需要做任何处理 } else { if (maxusercode.length() > 4) { String codeNumStr =
	 * maxusercode.substring(ConstUtil.DEPT_CODE_NUM_LEN); if
	 * (!YZUtility.isNullorEmpty(codeNumStr)) { num = Integer.parseInt(codeNumStr);
	 * } } } num++;// 记录加1 String deptcode = maxusercode.substring(0,
	 * ConstUtil.DEPT_CODE_NUM_LEN); if (num < 10) { numstr = deptcode + "00" + num;
	 * } else if (num > 9 && num < 100) { numstr = deptcode + "0" + num; } else if
	 * (num > 99) { numstr = deptcode + num; } return numstr;
	 * 
	 * }
	 *//**
		 * 查询当前门诊编号
		 * 
		 * @param conn
		 * @param table
		 * @return
		 * @throws Exception
		 */
	/*
	 * private static String getMaxDeptcode(String table) throws Exception { String
	 * maxusercode = null; StringBuffer sb = new StringBuffer();
	 * sb.append(" select max(dept_code) as maxusercode from  " + table); //
	 * 用户编号固定长度为6
	 * 
	 * List<JSONObject> list = logic.getJsonListByResultSet( sb.toString()); if
	 * (list != null && list.size() > 0) { maxusercode =
	 * list.get(0).getString("maxusercode"); } return maxusercode; }
	 * 
	 * @SuppressWarnings("rawtypes") public JSONObject selectWithPage(String table,
	 * BootStrapPage bp, Map<String, String> map) throws Exception { int firstIndex
	 * = bp.getOffset(); int pageSize = bp.getLimit(); JSONObject jobj = new
	 * JSONObject(); StringBuffer sb = new StringBuffer(); sb.append(" select ");
	 * sb.append(" tc.seq_id, "); sb.append(" tc.createtime, ");
	 * sb.append(" tc.dwmc, "); sb.append(" tc.sjhm, "); sb.append(" tc.lxr, ");
	 * sb.append(" tc.organization, "); sb.append(" tc.status "); sb.append(" from "
	 * + table + " tc "); sb.append(" where 1=1 "); if (map.containsKey("dwmc")) {
	 * sb.append(" and  tc.dwmc like '%" + map.get("dwmc") + "%' "); } if
	 * (map.containsKey("lxr")) { sb.append(" and  tc.lxr like '%" + map.get("lxr")
	 * + "%' "); } if (map.containsKey("sjhm")) { sb.append(" and  tc.sjhm like '%"
	 * + map.get("sjhm") + "%' "); } if (map.containsKey("starttime")) {
	 * sb.append(" and  tc.createtime >= '" + map.get("starttime") + "' "); }
	 * 
	 * if (map.containsKey("endtime")) { sb.append(" and  tc.createtime <= '" +
	 * map.get("endtime") + "' "); }
	 * 
	 * sb.append(" ORDER BY tc.createtime"); String sql = sb.toString(); // 是否是导出 if
	 * (!map.containsKey("flag")) { sql = PageUtil.getPaging(sb.toString(), "tc",
	 * pageSize, firstIndex); String totalsql = PageUtil.getTotalNums(sb.toString(),
	 * table, null); int count = logic.selectCountBySql( totalsql);
	 * jobj.put("total", count); } List<JSONObject> list =
	 * logic.getJsonListByResultSet( sql); jobj.put("rows", list); return jobj; }
	 */
}
