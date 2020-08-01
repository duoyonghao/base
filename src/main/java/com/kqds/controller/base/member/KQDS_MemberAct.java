package com.kqds.controller.base.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsMemberRecord;
import com.kqds.entity.base.KqdsMemberRecordSh;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.member.KQDS_MemberLogic;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_MemberAct")
public class KQDS_MemberAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_MemberAct.class);
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private KQDS_MemberLogic logic;
	@Autowired
	private YZFkfsLogic fkfsLogic;

	@RequestMapping(value = "/toMemberTkSq.act")
	public ModelAndView toMemberTkSq(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/member/member_tk_sq.jsp");
		return mv;
	}

	@RequestMapping(value = "/toHyzxIndex.act")
	public ModelAndView toCkIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/member/hyzx_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMethodModify.act")
	public ModelAndView toMethodModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/methodModify.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMethodModifyList.act")
	public ModelAndView toMethodModifyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/methodModifyList.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberAdd.act")
	public ModelAndView toMemberAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.setViewName("/kqdsFront/member/member_add.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberChongzhi.act")
	public ModelAndView toMemberChongzhi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/member_chongzhi.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberZhuanzeng.act")
	public ModelAndView toMemberZhuanzeng(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/member/member_zhuanzeng.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberZengSong.act")
	public ModelAndView toMemberZengSong(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		String memberno = request.getParameter("memberno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.addObject("memberno", memberno);
		mv.setViewName("/kqdsFront/member/member_zengsong.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberRecord.act")
	public ModelAndView toMemberRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/member/member_record.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberOpencard.act")
	public ModelAndView toMemberOpencard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.setViewName("/kqdsFront/member/opencard.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberRecharge.act")
	public ModelAndView toMemberRecharge(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.setViewName("/kqdsFront/member/recharge.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberTuifei.act")
	public ModelAndView toMemberTuifei(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.setViewName("/kqdsFront/member/tuifei.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberPay.act")
	public ModelAndView toMemberPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.setViewName("/kqdsFront/member/pay.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberQuery.act")
	public ModelAndView toMemberQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.setViewName("/kqdsFront/member/member_query.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZengsong_Give_Record.act")
	public ModelAndView toZengsong_Give_Record(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		String memberno = request.getParameter("memberno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.addObject("memberno", memberno);
		mv.setViewName("/kqdsFront/member/zengsong_give_record.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZengsong_User_Record.act")
	public ModelAndView toZengsong_User_Record(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		String memberno = request.getParameter("memberno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.addObject("memberno", memberno);
		mv.setViewName("/kqdsFront/member/zengsong_use_record.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZengsongQuery.act")
	public ModelAndView toZengsongQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.setViewName("/kqdsFront/member/zengsong_query.jsp");
		return mv;
	}

	@RequestMapping(value = "/toZhuanzengQuery.act")
	public ModelAndView toZhuanzengQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.setViewName("/kqdsFront/member/zhuanzeng_query.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberBangding.act")
	public ModelAndView toMemberBangding(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/kqds_member_binding.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberEdit.act")
	public ModelAndView toMemberEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/member_edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberEditpassIndex.act")
	public ModelAndView toMemberEditpassIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/kqds_member_editpass_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberEditpass.act")
	public ModelAndView toMemberEditpass(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/kqds_member_editpass.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberSetAskperson.act")
	public ModelAndView toMemberSetAskperson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/askperson_set.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberAskpersonList.act")
	public ModelAndView toMemberAskpersonList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/askperson_list.jsp");
		return mv;
	}

	@RequestMapping(value = "/toember_TkList.act")
	public ModelAndView toCost_TkList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/member/member_tklist.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMemberTuifeiPage.act")
	public ModelAndView toMemberTuifeiPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/member/member_tuifei.jsp");
		return mv;
	}

	@RequestMapping(value = "/toember_Yujiaojin.act")
	public ModelAndView toember_Yujiaojin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/member/member_yujiaojin.jsp");
		return mv;
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editPass.act")
	public String editPass(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			KqdsMember dp = new KqdsMember();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("请选择会员卡");
			}
			dp.setIsbinding(1);// 1 已绑定卡号
			logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
			BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEMBER_MODIFY_PWD, dp, TableNameUtil.KQDS_MEMBER, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 验证旧密码是否正确
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPassword.act")
	public String checkPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean result = true;
		try {
			String password = request.getParameter("password");
			String seqId = request.getParameter("seqId");
			KqdsMember en = (KqdsMember) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
			if (en == null) {
				throw new Exception("数据不存在");
			}
			if (!password.equals(en.getPassword())) {
				result = false;
			}
			YZUtility.DEAL_SUCCESS_VALID(result, response);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 绑定IC卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bindingAdd.act")
	public String bindingAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			KqdsMember dp = new KqdsMember();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("请选择需要绑定IC卡的会员卡");
			}
			if (YZUtility.isNullorEmpty(dp.getIcno())) {
				throw new Exception("无绑定的IC卡");
			}
			// 验证该IC卡是否已绑定过
			Map<String, String> map = new HashMap<String, String>();
			map.put("icno", dp.getIcno());
			List<KqdsMember> iclist = (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
			if (iclist != null && iclist.size() > 0) {
				throw new Exception("该IC卡已绑定过其他会员卡");
			}
			dp.setIsbinding(1);
			logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
			BcjlUtil.LogBcjl(BcjlUtil.UPDATE, BcjlUtil.KQDS_MEMBER_BIND, dp, TableNameUtil.KQDS_MEMBER, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 验证患者会员卡是否存在未绑定的IC卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/YzUsercodeIsbinding.act")
	public String YzUsercodeIsbinding(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");

			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", usercode);
			map.put("isbinding", ConstUtil.MEMBER_ISBINDING_0);// 未绑定
			List<KqdsMember> en = (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 入库，修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public synchronized String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			JSONObject czfs = new JSONObject();

			KqdsMember dp = new KqdsMember();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			String askperson = request.getParameter("askperson");
			String regsort = request.getParameter("regsort");
			if (!YZUtility.isNullorEmpty(seqId)) {
				String type = request.getParameter("type");
				if (!YZUtility.isNullorEmpty(type)) {
					String moneys = request.getParameter("moneys");// 充值金额
					String types = request.getParameter("types");// 充值方式 集合
					String content = request.getParameter("content");
					if (type.equals("chongzhi")) {// 充值
						KqdsMember member = (KqdsMember) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
						if (YZUtility.isNullorEmpty(member.getIcno())) {
							throw new Exception("该患者存在会员卡未绑定IC卡，请先绑定");
						}
						// 充值备注 临时存在member中 不会入库
						if (!YZUtility.isNullorEmpty(content)) {
							dp.setRemark(content);
						}
						czfs = logic.saveChongzhi(moneys, types, askperson, regsort, seqId, dp, person, czfs, "充值", "", request);

						// 记录日志
						BcjlUtil.LogBcjlWithUserCode(BcjlUtil.RECHARGE, BcjlUtil.KQDS_MEMBER, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER, request);
					} else if (type.equals("tuifei")) {// 退费
						// 退费原因
						String tfremark = request.getParameter("tfremark");
						czfs = logic.saveChongzhi(moneys, types, askperson, regsort, seqId, dp, person, czfs, "退费", tfremark, request);
						// 记录日志
						BcjlUtil.LogBcjlWithUserCode(BcjlUtil.REFUND, BcjlUtil.KQDS_MEMBER, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER, request);
					}
				} else {
					logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, dp);

					// 记录日志
					BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_MEMBER, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER, request);

					// 记录在操作记录表中
					Map<String, String> map = new HashMap<String, String>();
					map.put("usercode", dp.getUsercode());
					List<KqdsUserdocument> user = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);

					KqdsMemberRecord r = new KqdsMemberRecord();
					r.setSeqId(YZUtility.getUUID());
					r.setUsercode(dp.getUsercode());
					r.setUsername(user.get(0).getUsername());
					r.setCardno(dp.getMemberno());
					r.setCreatetime(YZUtility.getCurDateTimeStr());
					r.setCreateuser(person.getSeqId());
					r.setType(ConstUtil.MEMBER_RECORD_TYPE_MODIFY);
					r.setContent(ConstUtil.MEMBER_RECORD_TYPE_MODIFY_USERINFO);
					r.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
					logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
				}
			} else {
				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				String password = dp.getPassword();
				if (YZUtility.isNullorEmpty(password)) {
					// 如果密码为空 则初始化密码 默认为会员卡号后6位
					dp.setPassword(dp.getMemberno().substring(dp.getMemberno().length() - 6, dp.getMemberno().length()));
				}
				if (YZUtility.isNullorEmpty(dp.getIcno())) {
					throw new Exception("无绑定的IC卡");
				}
				// 验证该IC卡是否已绑定过
				Map<String, String> map = new HashMap<String, String>();
				map.put("icno", dp.getIcno());
				List<KqdsMember> iclist = (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
				if (iclist != null && iclist.size() > 0) {
					throw new Exception("该IC卡已绑定过其他会员卡");
				}
				dp.setIsbinding(1);
				String username = request.getParameter("usercodeDesc");
				dp.setUsername(username);
				String moneys = request.getParameter("moneys");// 充值金额 集合
				String types = request.getParameter("types");// 充值方式 集合
				czfs = logic.saveChongzhi(moneys, types, askperson, regsort, "", dp, person, czfs, "开卡", "", request);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_MEMBER, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER, request);

			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, czfs);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 预交金退款申请
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tksqMember.act")
	public String tksqMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			KqdsMember dp = new KqdsMember();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			String askperson = request.getParameter("askperson");
			String regsort = request.getParameter("regsort");
			String moneys = request.getParameter("moneys");// 充值金额
			String types = request.getParameter("types");// 充值方式 集合
			String doctor = request.getParameter("doctor");//退款选择医生
			// 退费原因
			String tfremark = request.getParameter("tfremark");
			BigDecimal xjmoney = BigDecimal.ZERO;
			BigDecimal yhkmoney = BigDecimal.ZERO;
			BigDecimal qtmoney = BigDecimal.ZERO;
			BigDecimal zfbmoney = BigDecimal.ZERO;
			BigDecimal wxmoney = BigDecimal.ZERO;
			BigDecimal mmdmoney = BigDecimal.ZERO;
			BigDecimal bdfqmoney = BigDecimal.ZERO;
			BigDecimal totalmoney = BigDecimal.ZERO;

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
				en = (KqdsMember) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
				/*
				 * en.setMoney(new BigDecimal(new BigDecimal(en.getMoney()) +
				 * totalmoney).setScale(1, BigDecimal.ROUND_HALF_DOWN).toString());
				 * en.setGivemoney(new BigDecimal(new BigDecimal(en.getGivemoney()) + new
				 * BigDecimal(dp.getGivemoney())).setScale(1, BigDecimal.ROUND_HALF_DOWN)
				 * .toString()); logic.updateSingleUUID(en, TableNameUtil.KQDS_MEMBER);
				 */
			}
			// 记录在操作记录表中
			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", en.getUsercode());
			List<KqdsUserdocument> user = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);

			KqdsMemberRecordSh r = new KqdsMemberRecordSh();
			r.setSeqId(YZUtility.getUUID());
			r.setUsercode(user.get(0).getUsercode());
			r.setUsername(user.get(0).getUsername());
			r.setCardno(en.getMemberno());
			String ctime = YZUtility.getCurDateTimeStr();
			r.setCreatetime(ctime);
			r.setCreateuser(person.getSeqId());
			// r.setType("退费申请");
			r.setSqstatus(1);
			r.setTfremark(tfremark);
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
			r.setDoctor(doctor);
			logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, r);

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.APPLY, BcjlUtil.KQDS_MEMBER_RECORD_SH, r, r.getUsercode(), TableNameUtil.KQDS_MEMBER_RECORD_SH, request);

			// 给总经理添加提示信息 收费申请
			List<JSONObject> personlist = personLogic.getAllShowZjlPerson(ChainUtil.getCurrentOrganization(request), request);
			for (int i = 0; i < personlist.size(); i++) {
				PushUtil.saveTx4MemberRefund(personlist.get(i), person, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 修改会员卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editMember.act")
	public String editMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String memberno = request.getParameter("memberno");
			String memberlevel = request.getParameter("memberlevel");
			String discount = request.getParameter("discount");
			String discountdate = request.getParameter("discountdate");
			String remark = request.getParameter("remark");
			KqdsMember en = (KqdsMember) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
			if (en == null) {
				throw new Exception("数据不存在");
			}
			List<JSONObject> list2 = logic.getMemberListByMember(memberno, seqId);
			if (list2.size() > 0) {
				throw new Exception("该卡号已被使用");
			}
			logic.setCardno(memberno, en.getMemberno());
			en.setMemberlevel(memberlevel);
			en.setRemark(remark);
			en.setDiscount(Integer.parseInt(discount));
			en.setDiscountdate(discountdate);
			en.setMemberno(memberno);
			en.setIcno(memberno);
			logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);

			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 详情返回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			KqdsMember en = (KqdsMember) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);
			if (en == null) {
				throw new Exception("数据不存在");
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据usercode查询详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMemberCardListByUsercode.act")
	public String getMemberCardListByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");

			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", usercode);
			map.put("memberstatus", ConstUtil.MEMBER_STATUS_1);// 已激活可用的会员卡
			List<KqdsMember> en = (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据usercode查询详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailByMemberno.act")
	public String selectDetailByMemberno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String memberno = request.getParameter("memberno");

			Map<String, String> map = new HashMap<String, String>();
			map.put("memberno", memberno);
			List<KqdsMember> en = (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据usercode查询总余额
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSymoneyByUsercode.act")
	public String getSymoneyByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String sftime = request.getParameter("sftime");
			JSONObject jobj = new JSONObject();
			JSONObject FKFS = logic.getSymoneyByUsercode(usercode, sftime);
			jobj.put("member", FKFS);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 挂失
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guashi.act")
	public String guashi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			KqdsMember en = (KqdsMember) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER, seqId);

			en.setMemberstatus(ConstUtil.MEMBER_STATUS_2);// 修改会员卡状态为挂失
			logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.LOSS, BcjlUtil.KQDS_MEMBER, en, en.getUsercode(), TableNameUtil.KQDS_MEMBER, request);

			// 记录在操作记录表中
			YZPerson person = SessionUtil.getLoginPerson(request);
			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", en.getUsercode());
			List<KqdsUserdocument> user = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);

			KqdsMemberRecord r = new KqdsMemberRecord();
			r.setSeqId(YZUtility.getUUID());
			r.setUsercode(en.getUsercode());
			r.setUsername(user.get(0).getUsername());
			r.setCardno(en.getMemberno());
			r.setCreatetime(YZUtility.getCurDateTimeStr());
			r.setCreateuser(person.getSeqId());
			r.setType(ConstUtil.MEMBER_RECORD_TYPE_GS);
			r.setContent("");
			r.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);

			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 不分页查询-会员中心 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			Map<String, String> map = new HashMap<String, String>();
			String queryInput = request.getParameter("queryInput");
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("queryInput", queryInput);
			}
			String createtime = request.getParameter("createtime");
			if (!YZUtility.isNullorEmpty(createtime)) {
				map.put("createtime", createtime + ConstUtil.TIME_START);
			}
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			String memberno = request.getParameter("memberno");
			if (!YZUtility.isNullorEmpty(memberno)) {
				map.put("memberno", memberno);
			}
			String usercode = request.getParameter("usercode");
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			String username = request.getParameter("username");
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			String type = request.getParameter("type");
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);
			}
			String memberlevel = request.getParameter("memberlevel");
			if (!YZUtility.isNullorEmpty(memberlevel)) {
				map.put("memberlevel", memberlevel);
			}
			// map是空的 默认查询当天开卡的
			if (map.isEmpty()) {
				map.put("starttime", YZUtility.getDateStr(new Date()) + ConstUtil.TIME_START);
				map.put("endtime", YZUtility.getDateStr(new Date()) + ConstUtil.TIME_END);
			}
			String pagenum = request.getParameter("pagenum");
			if (!YZUtility.isNullorEmpty(pagenum)) {
				map.put("pagenum", pagenum);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap()); 
			JSONObject json = new JSONObject();
			
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				// System.out.println("start............");
				// long st1 = System.currentTimeMillis();
				json = logic.selectList(TableNameUtil.KQDS_MEMBER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request),json,bp);
				if (json != null) {
					ExportBean bean = ExportTable.initExcel4RsWrite("会员中心", fieldArr, fieldnameArr, response, request);
					bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List<JSONObject>)json.get("rows"), "member_center");
					ExportTable.writeExcel4DownLoad("会员中心", bean.getWorkbook(), response);
				}
				// System.out.println("end............" +
				// (System.currentTimeMillis() - st1));
				return null;
			}
			if(sortName != null){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
				 json = logic.selectList(TableNameUtil.KQDS_MEMBER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request),json,bp);	
			}else{				
				 json = logic.selectList(TableNameUtil.KQDS_MEMBER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request),json,bp);	
			}
			/*-------导出excel---------*/
			// if (flag != null && flag.equals("exportTable")) {
			// ExportTable.exportBootStrapTable2Excel("会员中心", fieldArr,
			// fieldnameArr, list, response, request);
			// return null;
			// }
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 验证会员卡是否已存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkMemberno.act")
	public String checkMemberno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String memberno = request.getParameter("memberno");

			Map<String, String> map = new HashMap<String, String>();
			map.put("memberno", memberno);
			List<KqdsMember> en = (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en.size());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据usercode查询用户是否为会员 如果是 则返回会员等级
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkIsMemberByUsercode.act")
	public String checkIsMemberByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			int num = logic.checkIsMemberByUsercode(usercode);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put(YZActionKeys.JSON_RET_MSRGS, "操作成功");
			if (num > 0) {
				jobj.put("data", 1);
			} else {
				jobj.put("data", 0);
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 会员卡查询(统计) 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectMemberTongji.act")
	public String selectMemberTongji(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			Map<String, String> map = new HashMap<String, String>();
			String queryInput = request.getParameter("queryInput");
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("queryInput", queryInput);
			}
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			String askperson = request.getParameter("askperson");
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			JSONObject result = logic.selectMemberTongji(TableNameUtil.KQDS_MEMBER, bp, map, flag, person);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("会员卡查询列表", fieldArr, fieldnameArr, (List<JSONObject>) result.get("list"), response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(result, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 转赠
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/zhuanzeng.act")
	public String zhuanzeng(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String usercode = request.getParameter("usercode");
			String username = request.getParameter("username");
			String usercode1 = request.getParameter("usercode1");
			String username1 = request.getParameter("username1");
			String memberno = request.getParameter("memberno");
			String memberno1 = request.getParameter("memberno1");
			String zzremark = request.getParameter("zzremark");
			String money1 = request.getParameter("money1");
			String money2 = request.getParameter("money2");
			BigDecimal money11 = new BigDecimal(money1);
			BigDecimal money22 = new BigDecimal(money2);

			// ------------------- 转赠相关操作 --------------------------
			// 减去转赠金额
			BigDecimal zzmoney = BigDecimal.ZERO;// 减去后的余额
			BigDecimal zzgivemoney = BigDecimal.ZERO;// 减去后的赠送余额
			Map<String, String> map = new HashMap<String, String>();
			map.put("memberno", memberno);
			List<KqdsMember> en = (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
			KqdsMember member = en.get(0);
			zzmoney = KqdsBigDecimal.sub(member.getMoney(), money11);
			zzgivemoney = KqdsBigDecimal.sub(member.getGivemoney(), money22);
			// 保存转赠会员卡操作记录
			KqdsMemberRecord r = new KqdsMemberRecord();
			String uuid = YZUtility.getUUID();
			r.setSeqId(uuid);
			r.setUsercode(usercode);
			r.setUsername(username);
			r.setSzrusercode(usercode1);
			r.setSzrusername(username1);
			r.setSzcardno(memberno1);
			r.setCardno(memberno);
			r.setCreatetime(YZUtility.getCurDateTimeStr());
			r.setCreateuser(person.getSeqId());
			r.setTfremark(zzremark);
			r.setYmoney(member.getMoney());// 卡内余额
			r.setYgivemoney(member.getGivemoney());// 卡内赠送余额
			BigDecimal ytotal = KqdsBigDecimal.add(member.getMoney(), member.getGivemoney());
			r.setYtotal(ytotal);// 余额小计 (余额+赠送余额)
			r.setType(ConstUtil.MEMBER_RECORD_TYPE_ZZ);
			if (KqdsBigDecimal.compareTo(money11, BigDecimal.ZERO) > 0) {
				r.setZzmoney(KqdsBigDecimal.sub(BigDecimal.ZERO, money11));// 转赠金额
																			// 保存负数
			} else {
				r.setZzmoney(money11);// 转赠金额 保存负数
			}
			if (KqdsBigDecimal.compareTo(money22, BigDecimal.ZERO) > 0) {
				r.setZzgivemoney(KqdsBigDecimal.sub(BigDecimal.ZERO, money22));// 转赠赠送金额
																				// 保存负数
			} else {
				r.setZzgivemoney(money22);// 转赠赠送金额 保存负数
			}
			r.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);

			member.setMoney(zzmoney);
			member.setGivemoney(zzgivemoney);
			logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, member);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DONATION, BcjlUtil.KQDS_MEMBER, member, member.getUsercode(), TableNameUtil.KQDS_MEMBER, request);

			// ------------------- 受赠相关操作 --------------------------
			// 加上受赠金额
			BigDecimal szmoney = BigDecimal.ZERO;// 减去后的余额
			BigDecimal szgivemoney = BigDecimal.ZERO;// 减去后的赠送余额
			map.put("memberno", memberno1);
			List<KqdsMember> en1 = (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
			KqdsMember member1 = en1.get(0);
			szmoney = KqdsBigDecimal.add(member1.getMoney(), money11);
			szgivemoney = KqdsBigDecimal.add(member1.getGivemoney(), money22);
			// 保存受赠会员卡操作记录
			KqdsMemberRecord r1 = new KqdsMemberRecord();
			String uuid1 = YZUtility.getUUID();
			r1.setSeqId(uuid1);
			r1.setUsercode(usercode);
			r1.setUsername(username);
			r1.setSzrusercode(usercode1);
			r1.setSzrusername(username1);
			r1.setCardno(memberno1);
			r1.setSzcardno(memberno);
			r1.setCreatetime(YZUtility.getCurDateTimeStr());
			r1.setCreateuser(person.getSeqId());
			r1.setTfremark(zzremark);
			r1.setType(ConstUtil.MEMBER_RECORD_TYPE_SZ);
			r1.setZzmoney(money11);// 受赠金额 保存正数
			r1.setZzgivemoney(money22);// 受赠赠送金额 保存正数
			r1.setYmoney(member1.getMoney());// 卡内余额
			r1.setYgivemoney(member1.getGivemoney());// 卡内赠送余额
			BigDecimal ytotal1 = KqdsBigDecimal.add(member1.getMoney(), member1.getGivemoney());
			r1.setYtotal(ytotal1);// 余额小计 (余额+赠送余额)
			r1.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r1);

			member1.setMoney(szmoney);
			member1.setGivemoney(szgivemoney);
			logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, member1);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

}
