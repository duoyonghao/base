package com.kqds.service.base.paycost;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kqds.controller.base.paycost.Kqds_PayCostAct;
import com.kqds.core.adapter.DealAdapter;
import com.kqds.core.adapter.impl.ItemDealAdapter;
import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsHzLabelAssociated;
import com.kqds.entity.base.KqdsIntegralRecord;
import com.kqds.entity.base.KqdsJzqk;
import com.kqds.entity.base.KqdsLabel;
import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsMemberRecord;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsPaycost;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.base.KqdsVisit;
import com.kqds.entity.base.kqdsHzLabellabeAndPatient;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzLabel.KQDS_HzLabelAssociatedLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.base.member.KQDS_MemberLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@Service
public class Kqds_PayCostLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private YZFkfsLogic fkfsLogic;
	@Autowired
	private KQDS_MemberLogic memberlogic;
	@Autowired
	private KQDS_CostOrder_DetailLogic dlogic;
	@Autowired
	private KQDS_UserDocumentLogic userLogic;
	@Autowired
	private KQDS_REGLogic reglogic;
	@Autowired
	private KQDS_hz_labelLogic hzLabelLogic;
	@Autowired
	private KQDS_HzLabelAssociatedLogic hzLabelAssociatedLogic;
	@Autowired
	private KQDS_CostOrderLogic logic;
	@Autowired
	private KQDS_CostOrder_DetailLogic logicd;
	@Autowired
	private KQDS_UserDocumentLogic userDocumentlogic;
	@Autowired
	private KQDS_TreatItemLogic treatItemLogic;//添加收费项目logic依赖，用于成交状态判断
	
	private static Logger logger = LoggerFactory.getLogger(Kqds_PayCostAct.class);

	// 首页查询条数
	public int getCountIndex(String table, YZPerson person, String querytype, String searchValue, String visualstaff, String organization) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (YZUtility.isNullorEmpty(querytype) || querytype.equals("null") || querytype.equals("undefined")) {
			if (!visualstaff.equals("")) {
				map.put("querytype", visualstaff);
			}
		}
		if (!YZUtility.isNullorEmpty(searchValue)) {
			map.put("searchValue", searchValue);
			map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", searchValue));
			map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", searchValue));
		}
		map.put("organization", organization);

		int count = (int) dao.findForObject(TableNameUtil.KQDS_PAYCOST + ".getCountIndex", map);
		return count;
	}

	/**
	 * 就诊情况
	 * 
	 * @param regno
	 * @param doctor
	 * @param olddoctor
	 * @return
	 * @throws Exception
	 */
	public void editJzqk(String regno, String doctor, String olddoctor) throws Exception {
		// ### 对就诊情况数据进行处理 ### ----------------------------start
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("regno", regno);
		// 判断是否存在同一个号，医生对应的就诊情况出现2个
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("regno", regno);
		map2.put("doctor", doctor);
		dao.delete(TableNameUtil.KQDS_PAYCOST + ".deletebyregno2", map2);
		// ### 如果之前的医生已接诊了 就不删了
		if (doctor.equals(olddoctor)) {
			dao.delete(TableNameUtil.KQDS_PAYCOST + ".deletebyregno1old", map1);
		} else {
			dao.delete(TableNameUtil.KQDS_PAYCOST + ".deletebyregno1", map1);
		}
	}

	/**
	 * 首页收费项目分类 【通过配置 配置文件中的三大成交项目】【不做门诊条件过滤】
	 * 
	 * @param table
	 * @param itemno
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public KqdsTreatitem getItem(String table, String itemno) throws Exception {
		List<KqdsTreatitem> list = (List<KqdsTreatitem>) dao.findForList(TableNameUtil.KQDS_PAYCOST + ".getItem", itemno);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询欠费项目
	 * 
	 * @param detail
	 * @return
	 * @throws Exception
	 */
	public Object getQfItem(KqdsCostorderDetail detail) throws Exception {
		Object list = dao.findForList(TableNameUtil.KQDS_PAYCOST + ".getQfItem", detail);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public void payMoney(KqdsPaycost dp, HttpServletRequest request) throws Exception {
			 YZPerson person = SessionUtil.getLoginPerson(request);
				/** Kqds_paycost对象的大多数属性，都通过缴费页面的表单自动赋值 **/
				String seqId = request.getParameter("seqId");
				String userSeqId=request.getParameter("userSeqId");
				String remark = request.getParameter("remark");// 类型集合
				String types = request.getParameter("types");// 类型集合
				String moneys = request.getParameter("moneys");// 金额结合
				String member = request.getParameter("member");// 会员卡号
				String typestrs = request.getParameter("typestrs");// 类型 (汉字) 集合
				String usercode=request.getParameter("usercode");
				String username=request.getParameter("username");
				String access=request.getParameter("access");
				BigDecimal money11 = BigDecimal.ZERO;// 本金使用
				BigDecimal money22 = BigDecimal.ZERO;// 总赠送使用
				// 循环类型集合 分类型保存单子和金额
				String[] typess = types.split(",");
				String[] moneyss = moneys.split(",");
				// 以下2个变量目的是为了计算 预交金扣款比例（这里不提前算好比例，而是计算金额的时候一起计算，如：
				// money11 = xfmoney.multiply(qmmoney).divide(qmye);
				// 这样是为了防止出现，预交金金额剩下0.1元，赠送金额为-0.1的情况）
				BigDecimal qmmoney = BigDecimal.ZERO;// 会员卡总预交金使用金额
				BigDecimal qmye = BigDecimal.ZERO;// 会员卡总赠送金额
				KqdsReg reg = (KqdsReg) dao.loadObjSingleUUID(TableNameUtil.KQDS_REG, dp.getRegno());
				if (reg == null) {
					throw new Exception("该费用单对应的挂号记录不存在！");
				}
				// 验证费用单是否已结账
				KqdsCostorder cost = (KqdsCostorder) dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, dp.getCostno());
				if (cost.getStatus().equals(ConstUtil.COST_ORDER_STATUS_2)) {// 一结账不能删除
					throw new Exception("该费用单已结账，操作无效！");
				}
				// 验证费用单的缴费金额 是否等于 收费金额
				BigDecimal costmoney = cost.getActualmoney();
				BigDecimal paymoney = dp.getActualmoney();
				if (KqdsBigDecimal.compareTo(paymoney, costmoney) != 0) {
					throw new Exception("该费用单已出现变化，操作无效！");
				}
				// 查询 患者
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("usercode", dp.getUsercode());
				List<KqdsUserdocument> userList = (List<KqdsUserdocument>) dao.loadList(TableNameUtil.KQDS_USERDOCUMENT, map1);
				KqdsUserdocument u = new KqdsUserdocument();
				if (userList.size() > 0) {
					u = userList.get(0);
				} else {
					throw new Exception("患者信息不存在！");
				}
				// 查询明细
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("costno", dp.getCostno());
				List<KqdsCostorderDetail> Detaillist = (List<KqdsCostorderDetail>) dao.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map2);

				for (KqdsCostorderDetail detail : Detaillist) {
					String qfbh = detail.getQfbh();
					String createtime = detail.getCreatetime();
					int expireflag = dlogic.judgeIFExpire(createtime, qfbh);
					if (expireflag == 1) {
						throw new Exception("该费用单已过期，无法结账！");
					}
				}

				if (!YZUtility.isNullorEmpty(seqId)) {
					/** 存在seqId不为空的情况不？ **/
					dao.updateSingleUUID(TableNameUtil.KQDS_PAYCOST, dp);

					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_PAYCOST, dp, dp.getUsercode(), TableNameUtil.KQDS_PAYCOST, request);

				} else {
					KqdsMember en = null;
					String uuid = YZUtility.getUUID();
					dp.setSeqId(uuid);
					// 验证该费用单是否为退单
					if (cost.getIsback() == 1) {
						// 退单 收费单时间获取
						dp.setCreatetime(cost.getSftime());
					} else {
						dp.setCreatetime(YZUtility.getCurDateTimeStr());
						cost.setSftime(dp.getCreatetime());
					}
					dp.setCreateuser(person.getSeqId());
					dp.setPaytype1(types);
					dp.setPaytype2(typestrs);
					dp.setMoney1(BigDecimal.ZERO);
					dp.setMoney2(BigDecimal.ZERO);
					for (int i = 0; i < typess.length; i++) {
						// 预交金 结算扣除会员卡的余额
						if (typess[i].equals(fkfsLogic.selectSeqId4costfield("payyjj"))) {
							// 会员卡消费 根据公式计算应扣的余额和赠送金额
							// 查询会员余额等信息
							en = (KqdsMember) dao.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, member);

							BigDecimal xfmoney = new BigDecimal(moneyss[i]);
							/** 此次付款的预交金使用金额 **/
							BigDecimal money1 = en.getMoney();
							BigDecimal money2 = en.getGivemoney();
							if (KqdsBigDecimal.compareTo(money1, BigDecimal.ZERO) == 0 && KqdsBigDecimal.compareTo(money2, BigDecimal.ZERO) == 0) {
								throw new Exception("会员卡余额不足！");
							}
							// 操作记录总额
							JSONObject jobjnow = memberlogic.selectAllQm(en.getMemberno(), YZUtility.getCurDateTimeStr(), "qmmoney", "qmgivemoney", "qmtotal");
							// 无操作记录(会员卡余额不等于操作记录总额)
							boolean norecord = KqdsBigDecimal.compareTo(money1.add(money2), new BigDecimal(jobjnow.getString("qmtotal"))) != 0;
							if (norecord) {
								qmmoney = money1;
								qmye = money1.add(money2);
							} else {
								// 验证收费时间节点，预交金余额是否足够
								JSONObject jobj = memberlogic.selectAllQm(en.getMemberno(), dp.getCreatetime(), "qmmoney", "qmgivemoney", "qmtotal");
								qmmoney = new BigDecimal(jobj.getString("qmmoney"));
								qmye = new BigDecimal(jobj.getString("qmtotal"));
							}
							if (KqdsBigDecimal.compareTo(qmye, xfmoney) < 0) {
								// 退单
								if (cost.getIsback() == 1) {
									throw new Exception("原收费时间节点，会员卡余额不足！");
								} else {
									throw new Exception("会员卡余额不足！");
								}
							}
							// hl = KqdsBigDecimal.div(qmmoney, qmye, 4);
							// money11 = KqdsBigDecimal.mul(xfmoney, hl);
							// 比如预交金缴费11，该会员卡的充值金额是 100，赠送金额是 10，那么本金消费金额 = 11 *
							// (100 / (100 + 10)) ，取小数点后两位
							money11 = xfmoney.multiply(qmmoney).divide(qmye, 2, BigDecimal.ROUND_HALF_UP);
							/** multiply 乘法 divide 除法 **/
							money22 = KqdsBigDecimal.sub(xfmoney, money11);

							dp.setMoney1(money11);
							dp.setMoney2(money22);
						}
						if (typess[i].equals(fkfsLogic.selectSeqId4costfield("payintegral"))) {
							BigDecimal xfmoney = new BigDecimal(moneyss[i]);
							if (KqdsBigDecimal.compareTo(u.getIntegral(), xfmoney) < 0) {
								throw new Exception("积分不足！");
							}
						}
					}
					// 保存就诊情况，判断开单的医生是否与挂号的医生不一样，如果不一样，则覆盖挂号表的医生，并修改就诊情况记录的医生
					editJzqk(dp.getUsercode(), dp.getDoctor(), reg.getDoctor(), dp.getRegno(), person, request);
					// 根据付款信息，设置每个消费项目的具体付款方式金额
					setTypeMoney(typess, moneyss, Detaillist, qmmoney, qmye, money11, money22, person, request);
					// 新增付款信息入库
					dp.setOrganization(ChainUtil.getCurrentOrganization(request));
					dao.saveSingleUUID(TableNameUtil.KQDS_PAYCOST, dp);
					
					/**
					 * 根据费用单号查询收费明细数据  2019-9-6  syp start
					 */
					//更改到setTypeMoney同时更改治疗数据
//					List<KqdsCostorderDetail> list = dlogic.selectCostorderDetail(dp.getCostno());
//					for (KqdsCostorderDetail kqdsCostorderDetail : list) {
//						kqdsCostorderDetail.setKaifa("已治疗");
//						kqdsCostorderDetail.setZltime(YZUtility.getThirtyMinutesLater());
//						dlogic.updateCostorderDetailBySeqId(kqdsCostorderDetail);
//					}
					//更改为传一次costno更改治疗状态
//					KqdsCostorderDetail kqdsCostorderDetail= new KqdsCostorderDetail();
//					kqdsCostorderDetail.setKaifa("已治疗");
//					kqdsCostorderDetail.setZltime(YZUtility.getThirtyMinutesLater());
//					kqdsCostorderDetail.setCostno(dp.getCostno());
//					dlogic.updateCostorderDetailByCostno(kqdsCostorderDetail);
					/**
					 * end
					 */
					
					// 预交金扣款
					if (en != null) {
						memberlogic.yjjSubMoney(cost, Detaillist, en, request);
					}
					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_PAYCOST, dp, dp.getUsercode(), TableNameUtil.KQDS_PAYCOST, request);
					// 添加提醒
					PushUtil.saveTx4PayCost(dp, request);
				}
				/** 患者档案表的医生，每次结账时，都更新一次 **/
				setUserAllmoney(u, dp.getDoctor(), request);
				// 如果消费项目中，存在需要提醒医生设置回访的项目，则生成一个提醒设置回访记录
				addVisit(Detaillist, dp, u, request);

				// 判定是否为预交金充值单
				boolean isyjj = false;
				for (KqdsCostorderDetail detail : Detaillist) {
					if (1 == detail.getIsyjjitem()) {// 预交金项目，需要把缴费的钱，进行会员卡充值
						addChongzhi(detail, reg, dp.getCreatetime(), person, request);
						/** 此时，不存在赠送情况 **/
						isyjj = true;
					}
				}

				/**
				 * 成交定义： 1）根据三大项目判断 2）缴费金额判断 3）实收金额判断 根据客户实际需求进行选择
				 */
				DealAdapter dealAdapter = new ItemDealAdapter();
				// DealAdapter dealAdapter = new PayMoneyDealAdapter();
				// DealAdapter dealAdapter = new ActualMoneyDealAdapter();

				boolean dealStatus = dealAdapter.isDeal(reg, cost, Detaillist, request, this, dictLogic, userLogic);

				int cjStatus = 0; // 默认未成交
				if (dealStatus) { // 当flag为ture时，表示消费项目中，至少有一个不是特殊项目
					cjStatus = 1; // 已成交
					boolean isOpenBlCode = SysParaUtil.isOpenBLCodeFunc(request); // 判断是否开启病历号功能
					if (isOpenBlCode && YZUtility.isNullorEmpty(u.getBlcode())) {
						String blcode_start = SysParaUtil.getSysValueByName(request, SysParaUtil.BLCODE_START);
						String blcode = userLogic.getBlcode(blcode_start); // 生成病历号
						u.setBlcode(blcode);
						dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
					}
				}
				
				/**
				 * 如果前台挂号分类为其他时，三大项设置为已成交，药品、预交金、检查项目为未成交  syp 2019-11-08 start
				 */
				List<KqdsTreatitem> listTreatitem = treatItemLogic.getTreatItemInfroList(Detaillist);//调用方法treatItemLogic中的方法，查询具体开单项目
				//判断是否有检查项目，有则改变为1
				int jyk=0;
				if(listTreatitem.size()>0){
					for (KqdsTreatitem kqdsTreatitem : listTreatitem) {
						if(("jyk671").equals(kqdsTreatitem.getBasetype())){
							jyk=1;
							break;
						}
					}
				}
				//判断是否有预交金项目，有则改变为1
				int yjj=0;
				int istsxm=0;
				for (KqdsCostorderDetail detail : Detaillist) {
					if (1 == detail.getIsyjjitem()) {
						yjj=1;
					}
					if (1 == detail.getIstsxm()) {
						istsxm=1;
					}
				}
				String regsort = reg.getRegsort();
				if(regsort.equals("234")) {
					if(cost.getIsdrugs() == 1) {//判断药品
						cjStatus = 0;
					} else if (yjj== 1) {//判断预交金
						cjStatus = 0;
					} else if (jyk==1) {//判断检查项目
						cjStatus = 0;
					}
				}
				if(istsxm == 1 && jyk==1) {
					cjStatus = 0;//收银修改检查项目套餐单不做任何修改时保存再结账、成交状态判断
				}
				//如果开单有欠款结完账也为已成交（排除药品、检查项目、预交金）
				BigDecimal arrearmoney = cost.getArrearmoney();
				String arrearMoney = arrearmoney.toString();
				BigDecimal voidmoney = cost.getVoidmoney();
				String voidMoney = voidmoney.toString();
				BigDecimal y2 = cost.getY2();
				if(!arrearMoney.equals("0.000")) {
					if(cost.getIsdrugs() == 1 || yjj== 1 || jyk==1) {
						cjStatus = 0;
					} else {
						cjStatus = 1;
					}
				}
				if(regsort.equals("234")) {
					if(!arrearMoney.equals("0.000")) {
						if(cost.getIsdrugs() == 1 || yjj == 1 || jyk==1) {
							cjStatus = 0;
						} else {
							cjStatus = 1;
						}
					}
				}
				if(!voidMoney.equals("0.000")) {
					if(cost.getIsdrugs() == 1 || yjj== 1 || jyk==1) {
						cjStatus = 0;
					} else {
						cjStatus = 1;
					}
				}
				if(regsort.equals("234")) {
					if(!voidMoney.equals("0.000")) {
						if(cost.getIsdrugs() == 1 || yjj== 1 || jyk==1) {
							cjStatus = 0;
						} else {
							cjStatus = 1;
						}
					}
				}
				if((y2.compareTo(new BigDecimal(0))) == -1) {
					if(cost.getIsdrugs() == 1 || yjj == 1 || jyk==1) {
						cjStatus = 0;
					} else {
						cjStatus = 1;
					}
				}
				if(regsort.equals("234")) {
					if((y2.compareTo(new BigDecimal(0))) == -1) {
						if(cost.getIsdrugs() == 1 || yjj == 1 || jyk==1) {
							cjStatus = 0;
						} else {
							cjStatus = 1;
						}
					}
				}
				/**
				 * end
				 */

				/** 更新成交状态 **/
				reglogic.editCjstatus(dp.getDoctor(), dp.getRegno(), cjStatus, request);
				/**
				 * 
				 */
				JSONObject object = reglogic.findRegByregNo(TableNameUtil.KQDS_REG,dp.getRegno());
				if(object.get("recesort").equals("22") && object.get("cjstatus").equals("1")){
					List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_PAYCOST+".isArrearage", dp.getUsercode());
					if(list.size()==0){
						dao.update(TableNameUtil.KQDS_REG+".updateRecesort", dp.getRegno());
					}
				}
				// 更新门诊预约的状态为已成交
				editCjStatusHospital(dp.getRegno(), cjStatus, request);
				// 判断该患者今天是否有网电预约 如果有 则修改状态为已上门 并且保存安排医生科室等信息
				editCjStatusNetOrder(dp, cjStatus, request);
				// 跟新费用单状态
				editCjStatusOrder(cost, dp, cjStatus, remark, request);
				/**
				 * 如果开单打折，也改变成交状态为已成交  syp 2019-10-07
				 */
				BigDecimal discountMoney = cost.getDiscountmoney();
				String disMoney = discountMoney.toString();
				if (cost.getIsdrugs() != 1) {//discountMoney还需要判断
					if(!disMoney.equals("0.000") && jyk==0) {
						logic.updateCostOrderCjstatus(cost.getSeqId());
						reglogic.updateRegCjstatus(reg.getSeqId());
					}
				}
				if(regsort.equals("234")) {
					if (cost.getIsdrugs() != 1) {
						if(!disMoney.equals("0.000") && jyk==0) {
							logic.updateCostOrderCjstatus(cost.getSeqId());
							reglogic.updateRegCjstatus(reg.getSeqId());
						}
					}
				}
				/**
				 * end
				 */
				/** 更新成交状态 END ... **/
				BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, SysParaUtil.COST_INTEGRAL));
				// 参数值大于0 积分功能正常
				if (KqdsBigDecimal.compareTo(costIntegral, BigDecimal.ZERO) > 0) {
					if (!isyjj) {
						// 积分计算
						setIntegralMoney(typess, moneyss, dp, u, person.getSeqId(), request);
					}
				}
				// 实收金额计算
				String ssje = userLogic.getSsje(dp.getUsercode());
				u.setTotalpay(new BigDecimal(ssje));
				userLogic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
				// 判断 收费项目是否 还款结束 如果还款结束 设置status isqfreal 为0
				editOrderDetail(Detaillist, request);
				// 结账后费用单号为空删除
				dlogic.deleteDetail(dp.getUsercode(), request);
				
//				PayCostRunnableUtil runnableUtil = new PayCostRunnableUtil();
//				
//				Thread thread = new Thread(runnableUtil); 
//				thread.start();
				
				//2019-08-21 lwg 生成标签
				Map<String, String> map = new HashMap<String, String>();
				if (!YZUtility.isNullorEmpty(usercode)) {
					map.put("usercode", usercode);
				}
				if (!YZUtility.isNullorEmpty(access) && !access.equals("1")) {// 不需要可见人员过滤，查询全部费用
					// access=1
					map.put("access", access);
				}
				String visualstaff = SessionUtil.getVisualstaff(request);
				//查询实际消费项目
				List<JSONObject> list = logic.selectWithPageNopageForLabel(TableNameUtil.KQDS_COSTORDER, map, visualstaff);
				String itemname="";
				int nums=0;
				BigDecimal ys=new BigDecimal("0");
				List<JSONObject> itemlist1=new ArrayList<JSONObject>();
				List<JSONObject> itemlist2=new ArrayList<JSONObject>();
				for (JSONObject jsonObject : list) {
					if(jsonObject.getInt("status")== 2 && jsonObject.getInt("isyjjitem") == 0){//预交金单不算
		    			ys =ys.add(new BigDecimal(jsonObject.getString("shouldmoney")));
		    		}
					String costno=jsonObject.getString("costno");
					map.put("costno", costno);
					List<JSONObject> list1 = logicd.selectWithPageLzjlForLabel(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
					//判断项目是否包含种植体
					for (JSONObject jsonObject2 : list1) {
						if(jsonObject2.getString("itemname").endsWith("种植体")&&jsonObject2.getString("unit").equals("颗")){
							//种植体项目添加到itemlist
							if(jsonObject2.getInt("num")>0){
								itemlist1.add(jsonObject2);
							}else if(jsonObject2.getInt("num")<0){
								itemlist2.add(jsonObject2);
							}
						}
					}
					
				}
				List<JSONObject> itemlist3=new ArrayList<JSONObject>();
				List<JSONObject> itemlist4=new ArrayList<JSONObject>();
				//遍历itemlist查询种植体项目
				if(itemlist1.size()>0){
					StringBuffer str= new StringBuffer();
					if(itemlist1.size()==1){
						itemlist3.addAll(itemlist1);
					}else{
						for (JSONObject jsonObject2 : itemlist1) {
							if(!str.toString().contains(jsonObject2.getString("itemname"))){
								int m=0;
								for (int i = 0; i < itemlist1.size(); i++) {
									if(!jsonObject2.getString("seqid").equals(itemlist1.get(i).getString("seqid"))&&jsonObject2.getString("itemname").equals(itemlist1.get(i).getString("itemname"))){
										jsonObject2.put("num", (jsonObject2.getInt("num")+itemlist1.get(i).getInt("num"))+"");
										m=1;
									}
								}
								if(m==1){
									itemlist3.add(jsonObject2);
									str.append(jsonObject2.getString("itemname")+",");
								}
							}
						}
					}
					if(str.length()>0){
						for (JSONObject jsonObject2 : itemlist1) {
							if(!str.toString().contains(jsonObject2.getString("itemname"))){
								itemlist3.add(jsonObject2);
							}
						}
					}else if(str.length()==0&&itemlist1.size()>1){
						itemlist3.addAll(itemlist1);
					}
					
				}
				if(itemlist2.size()>0){
					StringBuffer str= new StringBuffer();
					if(itemlist2.size()==1){
						itemlist4.addAll(itemlist2);
					}else{
						for (JSONObject jsonObject2 : itemlist2) {
							if(!str.toString().contains(jsonObject2.getString("itemname"))){
								int m=0;
								for (int i = 0; i < itemlist2.size(); i++) {
									if(!jsonObject2.getString("seqid").equals(itemlist2.get(i).getString("seqid"))&&jsonObject2.getString("itemname").equals(itemlist2.get(i).getString("itemname"))){
										jsonObject2.put("num", (jsonObject2.getInt("num")+itemlist2.get(i).getInt("num"))+"");
										m=1;
									}
								}
								if(m==1){
									itemlist4.add(jsonObject2);
									str.append(jsonObject2.getString("itemname")+",");
								}
							}
						}
					}
					if(str.length()>0){
						for (JSONObject jsonObject2 : itemlist2) {
							if(!str.toString().contains(jsonObject2.getString("itemname"))){
								itemlist4.add(jsonObject2);
							}
						}
					}else if(str.length()==0&&itemlist2.size()>1){
						itemlist4.addAll(itemlist2);
					}
				}
				if(itemlist3.size()>0&&itemlist4.size()>0){
					StringBuffer str= new StringBuffer();
					for (JSONObject jsonObject3 : itemlist3) {
						for (JSONObject jsonObject4 : itemlist4) {
							if(jsonObject3.getString("itemname").equals(jsonObject4.getString("itemname"))){
								nums+=jsonObject3.getInt("num")+jsonObject4.getInt("num");
								if(jsonObject3.getInt("num")+jsonObject4.getInt("num")>0){
									str.append(jsonObject3.getString("itemname")+",");
									if(itemname.equals("")){
										itemname=jsonObject3.getString("itemname");
									}else if(!itemname.equals(jsonObject3.getString("itemname"))){
										itemname=itemname+"+"+jsonObject3.getString("itemname");
									}
								}else if(jsonObject3.getInt("num")+jsonObject4.getInt("num")==0){
									jsonObject3.put("num", "0");
								}
							}
						}
					}
					if(str.length()>0){
						for (JSONObject jsonObject3 : itemlist3) {
							if(!str.toString().contains(jsonObject3.getString("itemname"))&&!jsonObject3.getString("num").equals("0")){
								nums+=jsonObject3.getInt("num");
								if(itemname.equals("")){
									itemname=jsonObject3.getString("itemname");
								}else if(!itemname.equals(jsonObject3.getString("itemname"))){
									itemname=itemname+"+"+jsonObject3.getString("itemname");
								}
							}
						}
					}else if(str.length()==0&&itemlist3.size()>1){
						for (JSONObject jsonObject3 : itemlist3) {
							if(jsonObject3.getInt("num")>0){
								nums+=jsonObject3.getInt("num");
								if(itemname.equals("")){
									itemname=jsonObject3.getString("itemname");
								}else if(!itemname.equals(jsonObject3.getString("itemname"))){
									itemname=itemname+"+"+jsonObject3.getString("itemname");
								}
							}
						}
					}
				}else if(itemlist3.size()>0&&itemlist4.size()==0){
					for (JSONObject jsonObject3 : itemlist3) {
						nums+=jsonObject3.getInt("num");
						if(itemname.equals("")){
							itemname=jsonObject3.getString("itemname");
						}else if(!itemname.equals(jsonObject3.getString("itemname"))){
							itemname=itemname+"+"+jsonObject3.getString("itemname");
						}
					}
				}
				if(ys.compareTo(new BigDecimal("0"))==1){
					//查询消费金额
					map.put("status", "4");
					int status=4;
					//判断患者是否已经关联标签
					String hzLabelAssciatedSeqId=hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map);
					//患者已经关联标签
					String labelname="";
					//查询父类标签id
					String parentName="消费区间";
					KqdsLabel kqdsLabel=hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
					if(!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)){
						//查询患者标签是否和实际相符
						labelname=hzLabelAssociatedLogic.selectKqdsHzLabelBySeqId(hzLabelAssciatedSeqId);
						String start="";
						String end="";
						if(!YZUtility.isNullorEmpty(labelname)){
							//相符
							if(labelname.endsWith("以下")&&labelname.length()==6){
								start=labelname.substring(0,4);
							}else if(labelname.length()==11){
								start=labelname.substring(0,4);
								end=labelname.substring(5,10);
							}else if(labelname.length()==12){
								start=labelname.substring(0,5);
								end=labelname.substring(6,11);
							}else if(labelname.length()==6){
								start=labelname.substring(0,2);
								end=labelname.substring(3,5);
							}else{
								end="200000.9";
							}
						}
						if(!start.equals("")&&!end.equals("")){
							if(ys.compareTo(new BigDecimal(start))==-1||ys.compareTo(new BigDecimal(end))==1){
								//修改关联表,生成新标签名,保存新的关联表数据
								String ys1=ys.toString();
								labelname=cloudOfTags(ys1,null,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
							}
						}else if(!start.equals("")&&end.equals("")){
							if(ys.compareTo(new BigDecimal(start))==1){
								//修改关联表,生成新标签名,保存新的关联表数据
								String ys1=ys.toString();
								labelname=cloudOfTags(ys1,null,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
							}
						}else if(start.equals("")&&!end.equals("")){
								//修改关联表,生成新标签名,保存新的关联表数据
								String ys1=ys.toString();
								labelname=cloudOfTags(ys1,null,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
						}else{
								String ys1=ys.toString();
								labelname=cloudOfTags(ys1,null,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
						}
					}else{
						//直接生成存储标签
						//修改关联表,生成新标签名,保存新的关联表数据
						String ys1=ys.toString();
						labelname=cloudOfTags(ys1,null,null,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(),request);
					}
				}
				if(!YZUtility.isNullorEmpty(itemname)&&nums>0){
					//查询消费项目
					map.put("status", "3");
					int status=3;
					String xfxmLabelname="";
					//判断患者是否已经关联标签
					String hzLabelAssciatedSeqId=hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map);
					//患者已经关联标签
					String parentName="消费项目(种植体品牌)";
					KqdsLabel kqdsLabel=hzLabelLogic.findKqdsHzLabelParentIdByParentName(parentName);
					if(!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)){
						//查询患者标签是否和实际相符
						xfxmLabelname=hzLabelAssociatedLogic.selectKqdsHzLabelBySeqId(hzLabelAssciatedSeqId);
						if(xfxmLabelname!=null&&!xfxmLabelname.equals(itemname)){
							xfxmLabelname=cloudOfTags(null,itemname,hzLabelAssciatedSeqId,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
						}
					}else{
						//直接生成存储标签
						//修改关联表,生成新标签名,保存新的关联表数据
						xfxmLabelname=cloudOfTags(null,itemname,null,person,usercode,username,status,kqdsLabel.getSeqId(),parentName,kqdsLabel.getParentId(),kqdsLabel.getParentName(), request);
					}
				}else{
					KqdsHzLabelAssociated kqdsHzLabelAssociated=new KqdsHzLabelAssociated();
					kqdsHzLabelAssociated.setModifier(person.getSeqId());
					kqdsHzLabelAssociated.setUpdateTime(YZUtility.getCurDateTimeStr());
					kqdsHzLabelAssociated.setUsercode(usercode);
					kqdsHzLabelAssociated.setStatus(3);
					int j=hzLabelAssociatedLogic.updateKqdsHzLabelAssociatedByStatus(kqdsHzLabelAssociated);
				}

	}
	
	private String cloudOfTags(String ys,String itemname,String hzLabelAssciatedSeqId,YZPerson person,String usercode,String username,int status,String parentId,String parentName,String labelOneId,String labelOneName,HttpServletRequest request) throws Exception {
		if(!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)){
			KqdsHzLabelAssociated kqdsHzLabelAssociated=new KqdsHzLabelAssociated();
			kqdsHzLabelAssociated.setSeqId(hzLabelAssciatedSeqId);
			kqdsHzLabelAssociated.setModifier(person.getSeqId());
			kqdsHzLabelAssociated.setUpdateTime(YZUtility.getCurDateTimeStr());
			int i = hzLabelAssociatedLogic.updateKqdsHzLabelAssociated(kqdsHzLabelAssociated);
		}
//		if(!YZUtility.isNullorEmpty(usercode)&&status==3){
//			KqdsHzLabelAssociated kqdsHzLabelAssociated=new KqdsHzLabelAssociated();
//			kqdsHzLabelAssociated.setModifier(person.getSeqId());
//			kqdsHzLabelAssociated.setUpdateTime(YZUtility.getCurDateTimeStr());
//			kqdsHzLabelAssociated.setUsercode(usercode);
//			kqdsHzLabelAssociated.setStatus(status);
//			int j=hzLabelAssociatedLogic.updateKqdsHzLabelAssociatedByStatus(kqdsHzLabelAssociated);
//		}
		String labelname="";
		if(!YZUtility.isNullorEmpty(ys)){
			double d = Double.valueOf(ys);
			if(d>=0&&d<=5000.9){
				labelname="5000以下";
			}else if(d>=5001&&d<=20000.9){
				labelname="5001-20000元";
			}else if(d>=20001&&d<=40000.9){
				labelname="20001-40000元";
			}else if(d>=40001&&d<=60000.9){
				labelname="40001-60000元";
			}else if(d>=60001&&d<=80000.9){
				labelname="60001-80000元";
			}else if(d>=80001&&d<=100000.9){
				labelname="80001-100000元";
			}else if(d>=100001&&d<=150000.9){
				labelname="10-15万";
			}else if(d>=150001&&d<=200000.9){
				labelname="15-20万";
			}else if(d>=200001){
				labelname="20万以上";
			}
		}else if(!YZUtility.isNullorEmpty(itemname)){
			labelname=itemname;
		}
		//查询标签名有无，有则使用，无则新增
		Map<String,String> map1= new HashMap<String,String>();
		if(labelname!=null &&!labelname.equals("")){
			map1.put("leveLabel", labelname);
		}
		if(!parentId.equals("")&&parentId!=null){
			map1.put("parentId", parentId);
		}
		String labelId = hzLabelLogic.selectKqdsHzLabelByLeveLabel(map1);
		if(YZUtility.isNullorEmpty(labelId)){
			//新增标签和关联表数据
			KqdsLabel kqdsHzLabel = new KqdsLabel();
			String seqid=YZUtility.getUUID();
			labelId=seqid;
			kqdsHzLabel.setSeqId(seqid);
			kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
			kqdsHzLabel.setCreateUser(person.getSeqId());
			kqdsHzLabel.setLeveLabel(labelname);
			kqdsHzLabel.setParentId(parentId);
			kqdsHzLabel.setParentName(parentName);
			kqdsHzLabel.setStatus("0");
			kqdsHzLabel.setRemark("三级");
			int j = hzLabelLogic.insertKqdsHzLabel(kqdsHzLabel);
			KqdsHzLabelAssociated kqdsHzLabelAssociated1=new KqdsHzLabelAssociated();
			hzLabelAssciatedSeqId=YZUtility.getUUID();
			kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
			kqdsHzLabelAssociated1.setLabeId(seqid);
			kqdsHzLabelAssociated1.setUsercode(usercode);//患者id
			kqdsHzLabelAssociated1.setUserName(username);//患者姓名
			kqdsHzLabelAssociated1.setCreateTime(YZUtility.getCurDateTimeStr());
			kqdsHzLabelAssociated1.setCreateUser(person.getSeqId());
			kqdsHzLabelAssociated1.setRemark("");
			kqdsHzLabelAssociated1.setStatus(status);
			kqdsHzLabelAssociated1.setIsdelete(0);
			int t=hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
		}else{
			KqdsHzLabelAssociated kqdsHzLabelAssociated1=new KqdsHzLabelAssociated();
			hzLabelAssciatedSeqId=YZUtility.getUUID();
			kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
			kqdsHzLabelAssociated1.setLabeId(labelId);
			kqdsHzLabelAssociated1.setUsercode(usercode);//患者id
			kqdsHzLabelAssociated1.setUserName(username);//患者姓名
			kqdsHzLabelAssociated1.setCreateTime(YZUtility.getCurDateTimeStr());
			kqdsHzLabelAssociated1.setCreateUser(person.getSeqId());
			kqdsHzLabelAssociated1.setRemark("");
			kqdsHzLabelAssociated1.setStatus(status);
			kqdsHzLabelAssociated1.setIsdelete(0);
			int t=hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
			
		}
		Map<String,String> map =new  HashMap<String,String>();
		map.put("userCode", usercode);
		map.put("labelTwoId", parentId);
		String threeid=userDocumentlogic.selectLabelByUsercode(map);
		userDocumentlogic.deleteLabelByUsercode(map);
		kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
		String id = YZUtility.getUUID();
		kPatient.setSeqId(id);
		kPatient.setUserSeqId("");
		kPatient.setUserId(usercode);
		kPatient.setUserName(username);
		kPatient.setLabelOneId(labelOneId);
		kPatient.setLabelOneName(labelOneName);
		kPatient.setLabelTwoId(parentId);
		kPatient.setLabelTwoName(parentName);
		kPatient.setLabelThreeId(labelId);
		kPatient.setLabelThreeName(labelname);
		kPatient.setCreateUser(person.getSeqId());
		kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
		kPatient.setOrganization(ChainUtil.getCurrentOrganization(request));
		userDocumentlogic.saveKpatient(kPatient);
		BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_Hz_LabellabeAndPatient, kPatient, usercode, TableNameUtil.KQDS_Hz_LabellabeAndPatient, request);
		return labelname;
	}
	/**
	 * 如果消费项目中，存在需要提醒医生设置回访的项目，则生成一个提醒设置回访记录
	 * 
	 * @param dp
	 * @param u
	 * @param list
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void addVisit(List<KqdsCostorderDetail> list, KqdsPaycost dp, KqdsUserdocument u, HttpServletRequest request) throws Exception {
		boolean flag = false;
		for (KqdsCostorderDetail detail : list) {
			if (4 == detail.getIsyjjitem()) {
				flag = true;
				break;
			}
		}
		String hffl = dictLogic.getDictIdByNameAndParentCode("HFFL", "回访提醒");
		if (flag && !YZUtility.isNullorEmpty(hffl)) {
			KqdsVisit visit = new KqdsVisit();
			visit.setSeqId(YZUtility.getUUID());
			visit.setCreatetime(YZUtility.getCurDateTimeStr());
			visit.setCreateuser(dp.getDoctor());
			visit.setOrganization(dp.getOrganization()); // ###
			visit.setUsercode(dp.getUsercode());
			visit.setUsername(dp.getUsername());
			visit.setSex(u.getSex());
			visit.setTelphone(u.getPhonenumber1());
			visit.setVisittime(dp.getCreatetime().substring(0, dp.getCreatetime().length() - 3));
			visit.setHffl(hffl);
			visit.setVisitor(dp.getDoctor());
			visit.setHfyd("提醒设置回访！");
			dao.saveSingleUUID(TableNameUtil.KQDS_VISIT, visit);
			// 添加提醒
			PushUtil.saveTx4NewVisit(visit, request);
		}
	}

	/**
	 * 【内部调用】
	 * 
	 * @param u
	 * @param request
	 * @param doctor
	 * @throws Exception
	 */
	public void setUserAllmoney(KqdsUserdocument u, String doctor, HttpServletRequest request) throws Exception {
		u.setDoctor(doctor);
		String iskaipiao = YZSysProps.getProp("IS_KAIPIAO");
		if ("IS_KAIPIAO_TRUE".equals(iskaipiao)) {
			u.setCostlevel("0");// 开票系统才设置，口腔大师系统不设置
		}
		dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
	}

	/**
	 * 修改就诊情况 ################## 这个方法具体是什么逻辑，已经看不懂了... 17-12-29 ys
	 * 
	 * @param usercode
	 * @param doctor
	 *            开单医生
	 * @param olddoctor
	 *            挂号医生
	 * @param regno
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void editJzqk(String usercode, String doctor, String olddoctor, String regno, YZPerson person, HttpServletRequest request) throws Exception {
		editJzqk(regno, doctor, olddoctor);
		// 生成一条新的就诊情况，可能存在 复诊的患者 不需要开单 结账
		KqdsJzqk jzqk = new KqdsJzqk();
		jzqk.setSeqId(UUID.randomUUID().toString());
		jzqk.setRegno(regno);
		jzqk.setDoctor(doctor);
		jzqk.setCreatetime(YZUtility.getCurDateTimeStr());
		jzqk.setCreateuser(person.getSeqId() + "");
		jzqk.setOrganization(ChainUtil.getCurrentOrganization(request));
		dao.saveSingleUUID(TableNameUtil.KQDS_JZQK, jzqk);
	}

	/**
	 * 根据付款方式，消费项目信息等，进行业务逻辑处理
	 * 
	 * @param typess
	 *            付款方式，对应sys_dict表的主键
	 * @param moneyss
	 *            缴费金额数组
	 * @param listDetail
	 *            消费项目列表
	 * @param qmmoney
	 *            会员卡的本金余额
	 * @param qmye
	 *            会员卡总余额
	 * @param ssall
	 *            本次会员卡的本金使用金额
	 * @param zsall
	 *            本次会员卡的赠送使用金额
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	private void setTypeMoney(String[] typess, String[] moneyss, List<KqdsCostorderDetail> listDetail, BigDecimal qmmoney, BigDecimal qmye, BigDecimal ssall, BigDecimal zsall,
			YZPerson person, HttpServletRequest request) throws Exception {
		BigDecimal zss = BigDecimal.ZERO; // 预交金赠送小计
		BigDecimal sss = BigDecimal.ZERO; // 预交金本金小计
		Map<String, String> map = new HashMap<String, String>();
		map.put("useflag", "0");
		List<JSONObject> en = fkfsLogic.selectList(TableNameUtil.SYS_FKFS, map);
		if(en.size()>0){
			for (JSONObject jsonObject : en) {
				map.put(jsonObject.getString("costfield"),jsonObject.getString("seq_id"));
			}
		}
		for (int i = 0; i < typess.length; i++) {
			// 付款金额
			BigDecimal xfmoney = new BigDecimal(moneyss[i]);
			for (KqdsCostorderDetail detail : listDetail) {
				BigDecimal paymoney = detail.getPaymoney(); // 应缴金额
				BigDecimal paymoneyed = BigDecimal.ZERO; // 已交金额
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaybank());
				/** 什么情况下，这里存在已交金额？ **/
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayyjj());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayyb());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayxj());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaywx());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayzfb());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaymmd());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaybdfq());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPaydjq());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayintegral());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayother1());
				paymoneyed = KqdsBigDecimal.add(paymoneyed, detail.getPayother2());
				// 剩余应缴金额 = 应交金额 - 已交金额
				paymoney = paymoney.subtract(paymoneyed);
				if (KqdsBigDecimal.compareTo(paymoney, BigDecimal.ZERO) == 0) {
					continue;
				}
				if (KqdsBigDecimal.compareTo(xfmoney, BigDecimal.ZERO) == 1) {
					if (typess[i].equals(map.get("payyjj"))) {// 预交金，此时xfmoney=本金+赠送
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) { // 如果预交金>应付金额
							xfmoney = xfmoney.subtract(paymoney); // 预交金付款金额 =
																	// 预交金付款金额 -
																	// 应付金额
							BigDecimal pay = paymoney.multiply(qmmoney).divide(qmye, 2, BigDecimal.ROUND_HALF_UP);
							BigDecimal zs = paymoney.subtract(pay);
							detail.setPayyjj(pay); // 设置预交金本金
							detail.setPayother2(zs); // 设置预交金赠送
							sss = sss.add(pay);
							zss = zss.add(zs);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPayother2(zsall.subtract(zss));
							detail.setPayyjj(ssall.subtract(sss));
							xfmoney = xfmoney.subtract(xfmoney);
							/** 此时 xfmoney 的值为 0 **/
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("payyb"))) {
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {// 如果医保>应付金额
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPayyb(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPayyb(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("payxj"))) {
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPayxj(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPayxj(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("paybank"))) {
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPaybank(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPaybank(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("paywx"))) {
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPaywx(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPaywx(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("payzfb"))) {
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPayzfb(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPayzfb(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("paymmd"))) {
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPaymmd(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPaymmd(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("paybdfq"))) {
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPaybdfq(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPaybdfq(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("payother1"))) {
						// 其他
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {// 如果预交金>应付金额
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPayother1(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPayother1(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("paydjq"))) {
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {// 如果预交金>应付金额
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPaydjq(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {// 如果预交金<应付金额
							detail.setPaydjq(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
					if (typess[i].equals(map.get("payintegral"))) {
						// 设置项目明细中缴费金额
						if (KqdsBigDecimal.compareTo(xfmoney, paymoney) >= 0) {// 如果预交金>应付金额
							xfmoney = xfmoney.subtract(paymoney);
							detail.setPayintegral(paymoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						} else {
							detail.setPayintegral(xfmoney);
							xfmoney = xfmoney.subtract(xfmoney);
							//dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
						}
					}
				}
				detail.setKaifa("已治疗");
				detail.setZltime(YZUtility.getThirtyMinutesLater());
			}
			//批量修改费用明细
			dlogic.batchUpdateCostorderDetailBySeqid(listDetail);
		}
	}

	/**
	 * 预交金充值-通过划价方式
	 */
	@SuppressWarnings("unchecked")
	private void addChongzhi(KqdsCostorderDetail detail, KqdsReg reg, String createtime, YZPerson person, HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberno", detail.getUsercode());
		
		KqdsMemberRecord r = new KqdsMemberRecord();
		try {
			List<KqdsMember> list = (List<KqdsMember>) dao.loadList(TableNameUtil.KQDS_MEMBER, map);
			
			KqdsMember en = list.get(0);
			en.setMoney(KqdsBigDecimal.add(en.getMoney(), detail.getPaymoney()));
			dao.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);

			r.setSeqId(YZUtility.getUUID());
			r.setUsercode(detail.getUsercode());
			r.setUsername(en.getUsername());
			r.setCardno(en.getMemberno());
			r.setCreatetime(createtime);
			r.setCreateuser(person.getSeqId());
			r.setType(ConstUtil.MEMBER_RECORD_TYPE_CZ);
			r.setAskperson(detail.getAskperson());
			r.setRegsort(reg.getRegsort());
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
			r.setCostno(detail.getCostno());
			dao.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("此患者预交金卡不存在，请先进行开户操作! 谢谢合作");
		}
		
		
		// 记录日志
		BcjlUtil.LogBcjl(r.getSeqId(), BcjlUtil.KQDS_MEMBER_RECORD, r, TableNameUtil.KQDS_MEMBER_RECORD, request);

	}

	/**
	 * 【内部调用】
	 * 
	 * @param regno
	 * @param cjstatus
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void editCjStatusHospital(String regno, int cjstatus, HttpServletRequest request) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("regno", regno);
		List<KqdsHospitalOrder> hos = (List<KqdsHospitalOrder>) dao.loadList(TableNameUtil.KQDS_HOSPITAL_ORDER, map);
		if (hos != null && hos.size() > 0) {
			KqdsHospitalOrder net = hos.get(0);
			net.setCjstatus(cjstatus);// 已成交
			dao.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, net);
		}
	}

	/**
	 * 【内部调用】
	 * 
	 * @param dp
	 * @param cjstatus
	 * @throws Exception
	 */
	private void editCjStatusOrder(KqdsCostorder en, KqdsPaycost dp, int cjstatus, String remark, HttpServletRequest request) throws Exception {
		en.setDoctor(dp.getDoctor());
		en.setNurse(dp.getNurse());
		en.setTechperson(dp.getTechperson());
		if (en.getIsback() != 1) {// 不是退单
			en.setSftime(dp.getCreatetime());
		}
		en.setSfuser(dp.getCreateuser());
		en.setCjstatus(cjstatus);
		en.setRemark(remark);
		en.setStatus(ConstUtil.COST_ORDER_STATUS_2);
		// 判断 收费项目是否还款结束
		dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER, en);
	}

	/**
	 * 【内部调用】
	 * 
	 * @param listdetail
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void editOrderDetail(List<KqdsCostorderDetail> listdetail, HttpServletRequest request) throws Exception {
		for (KqdsCostorderDetail detail : listdetail) {
			BigDecimal zss = detail.getArrearmoney();
			if (KqdsBigDecimal.compareTo(zss, BigDecimal.ZERO) == 0) {
				detail.setStatus(ConstUtil.COST_DETAIL_STATUS_0);// 把原欠费项目status设为0，0代表结账前无欠费
				detail.setIsqfreal(ConstUtil.QF_STATUS_0);
				dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
			} else {
				detail.setIsqfreal(ConstUtil.QF_STATUS_1);
				/** 这里只所以没有设置status值为1，是因为开单时已经设置了 **/
				if (YZUtility.isNullorEmpty(detail.getQfbh())) {
					detail.setQfbh(YZUtility.getUUID());
					/** 欠费编号不轻易改变 **/
				}
				dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
			}

			/** 把开单时，更改状态的代码，移到这里 **/
			// 这里是结账，结账时把当前的消费项目根据实际情况设置isqfreal，把之前的真实欠费记录状态改为0
			if (YZUtility.isNotNullOrEmpty(detail.getQfbh())) {
				List<KqdsCostorderDetail> oldQfList = (List<KqdsCostorderDetail>) getQfItem(detail);
				for (KqdsCostorderDetail qfDetail : oldQfList) {
					// qfDetail.setStatus("0"); /**
					// 这里不更新status，只更新isqfreal，因为更新status会破坏原始数据 **/
					qfDetail.setIsqfreal(0);
					dao.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, qfDetail);
				}

				// System.out.println(sql.toString());
			}
		}
	}

	/**
	 * 【内部调用】
	 * 
	 * @param dp
	 * @param cjstatus
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void editCjStatusNetOrder(KqdsPaycost dp, int cjstatus, HttpServletRequest request) throws Exception {

		Map<String, String> mapjz = new HashMap<String, String>();
		mapjz.put("regno", dp.getRegno());
		List<KqdsNetOrder> en = (List<KqdsNetOrder>) dao.loadList(TableNameUtil.KQDS_NET_ORDER, mapjz);
		if (en != null && en.size() > 0) {
			KqdsNetOrder net = en.get(0);
			net.setCjstatus(cjstatus);// 已成交
			net.setOrderperson(dp.getDoctor());// 安排医生
			dao.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, net);
		}
	}

	/**
	 * 积分增加
	 * 
	 * @param typess
	 * @param moneyss
	 * @param dp
	 * @param u
	 * @param perId
	 * @param request
	 * @throws Exception
	 */
	private void setIntegralMoney(String[] typess, String[] moneyss, KqdsPaycost dp, KqdsUserdocument u, String perId, HttpServletRequest request) throws Exception {
		BigDecimal ssmoney = BigDecimal.ZERO;
		// 代金券+积分支付
		BigDecimal zsmoney = BigDecimal.ZERO;
		BigDecimal jfmoney = BigDecimal.ZERO;
		String jfzj = dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "消费增加");
		String jfjs = dictLogic.getDictIdByNameAndParentCode(DictUtil.JFLX, "消费减少");
		if (YZUtility.isNullorEmpty(jfzj) || YZUtility.isNullorEmpty(jfjs)) {
			throw new Exception("积分类型不存在！");
		}
		for (int i = 0; i < typess.length; i++) {
			if (typess[i].equals(fkfsLogic.selectSeqId4costfield("payintegral"))) {
				zsmoney = zsmoney.add(new BigDecimal(moneyss[i]));
				jfmoney = new BigDecimal(moneyss[i]);
				// 保存积分记录
				KqdsIntegralRecord record = new KqdsIntegralRecord();
				record.setSeqId(UUID.randomUUID().toString());
				record.setCreatetime(YZUtility.getCurDateTimeStr());
				record.setOrganization(ChainUtil.getCurrentOrganization(request));
				record.setUsercode(u.getUsercode());
				record.setJfmoney(BigDecimal.ZERO.subtract(jfmoney));
				record.setJftype(jfjs);
				record.setSyjfmoney(u.getIntegral().subtract(jfmoney));
				record.setCreateuser(perId);
				dao.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);
				// 扣除的积分
				u.setIntegral(u.getIntegral().subtract(jfmoney));
			}
			if (typess[i].equals(fkfsLogic.selectSeqId4costfield("paydjq"))) {
				zsmoney = zsmoney.add(new BigDecimal(moneyss[i]));
			}
		}
		// 赠送金额 = 代金券+积分支付+赠送
		if (dp.getMoney2() != null && KqdsBigDecimal.compareTo(dp.getMoney2(), BigDecimal.ZERO) > 0) {
			zsmoney = zsmoney.add(dp.getMoney2());
		}

		// 实收金额 = 缴费金额 - 赠送金额
		ssmoney = dp.getActualmoney().subtract(zsmoney);
		BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, SysParaUtil.COST_INTEGRAL));
		BigDecimal integral = ssmoney.divide(costIntegral, 0, RoundingMode.DOWN);
		if (u.getIntegral() == null) {
			u.setIntegral(BigDecimal.ZERO);
		}
		// 产生的积分
		u.setIntegral(u.getIntegral().add(integral));
		dao.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, u);
		// 保存积分记录
		KqdsIntegralRecord record = new KqdsIntegralRecord();
		record.setSeqId(UUID.randomUUID().toString());
		record.setCreatetime(YZUtility.getCurDateTimeStr());
		record.setOrganization(ChainUtil.getCurrentOrganization(request));
		record.setUsercode(u.getUsercode());
		record.setJfmoney(integral);
		record.setJftype(jfzj);
		record.setSyjfmoney(u.getIntegral());
		record.setCreateuser(perId);
		dao.saveSingleUUID(TableNameUtil.KQDS_INTEGRAL_RECORD, record);
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> findPayCostByUsercodes(String usercodes) throws Exception {
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_PAYCOST+".findPayCostByUsercodes", usercodes);
		return list;
	}

	/**   
	  * @Title: discount   
	  * @Description: TODO(查询折扣及免除)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年9月26日 下午4:44:11
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> discount(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>)dao.findForList(TableNameUtil.KQDS_COSTORDER+".discount", map);		
	}

	/**
	 * 根据挂号id查询结账条数
	 * @param regno
	 * @return
	 * @throws Exception
	 */
	public int findCountByRegno(String regno) throws Exception {
		int i= (int) dao.findForObject(TableNameUtil.KQDS_PAYCOST+".findCountByRegno", regno);
		return i;
	}
}