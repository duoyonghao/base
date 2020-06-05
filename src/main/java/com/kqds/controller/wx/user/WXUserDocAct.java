package com.kqds.controller.wx.user;

import java.math.BigDecimal;
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
import com.kqds.core.global.YZSysProps;
import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXOrder;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.netOrder.KQDS_Net_OrderLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.wx.core.WXUtil4ChatLogic;
import com.kqds.service.wx.core.WXUtil4TemplateMsgLogic;
import com.kqds.service.wx.core.WXUtilLogic;
import com.kqds.service.wx.fans.WXFansLogic;
import com.kqds.service.wx.user.WXUserDocLogic;
import com.kqds.util.base.code.UserCodeUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.DictUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import com.kqds.util.wx.MessageUtil;
import com.kqds.util.wx.WXConst;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("WXUserDocAct")
public class WXUserDocAct {
	private static Logger logger = LoggerFactory.getLogger(WXUserDocAct.class);
	@Autowired
	private WXUserDocLogic logic;
	@Autowired
	private KQDS_UserDocumentLogic userDocLogic;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private KQDS_Net_OrderLogic netOrderlogic;
	@Autowired
	private WXFansLogic fansLogic;
	@Autowired
	private WXUtilLogic wxUtilLogic;
	@Autowired
	private WXUtil4TemplateMsgLogic wxUtil4TemplateMsgLogic;
	@Autowired
	private WXUtil4ChatLogic wxUtil4ChatLogic;

	@RequestMapping(value = "/toHzjd4kefu.act")
	public ModelAndView toActive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String openid = request.getParameter("openid");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/wechat/user_manage/hzjd4kefu.jsp");
		mv.addObject("openid", openid);
		return mv;
	}

	/**
	 * 在微信预约的审核建档页面，进行绑定
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bindUserByWXOrder.act")
	public String bindUserByWXOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String openid = request.getParameter("openid");
			String phonenumber = request.getParameter("phonenumber");

			if (YZUtility.isNullorEmpty(openid)) {
				throw new Exception("openid不能为空");
			}

			if (YZUtility.isNullorEmpty(phonenumber)) {
				throw new Exception("phonenumber不能为空");
			}

			KqdsUserdocument doc = userDocLogic.getSingUserByPhoneNumber(phonenumber);

			if (doc == null) {
				throw new Exception("患者不存在，手机号码为：" + phonenumber);
			}

			fansLogic.bindWXUser(openid, doc, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 确认绑定
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmBind.act")
	public String confirmBind(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String openid = request.getParameter("openid");
			String usercode = request.getParameter("usercode");

			if (YZUtility.isNullorEmpty(openid)) {
				throw new Exception("openid不能为空");
			}

			if (YZUtility.isNullorEmpty(usercode)) {
				throw new Exception("usercode不能为空");
			}

			KqdsUserdocument doc = userDocLogic.getSingleUserByUsercode(usercode);

			if (doc == null) {
				throw new Exception("患者不存在，编号为：" + usercode);
			}

			fansLogic.bindWXUser(openid, doc, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 检查是否绑定微信
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserDocByOpenId.act")
	public String getUserDocByOpenId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String openid = request.getParameter("openid");
			KqdsUserdocument json = logic.getBindUserDocByOpenId(openid, request);
			JSONObject result = new JSONObject();
			result.put(YZActionKeys.JSON_RET_DATA, json);
			YZUtility.DEAL_SUCCESS(result, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据姓名 手机号 获取 患者信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserDocByNameAndPhonenumber.act")
	public String getUserDocByNameAndPhonenumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String username = request.getParameter("username");
			String phonenumber = request.getParameter("phonenumber");
			JSONObject json = logic.getUserDocByNameAndPhonenumber(username, phonenumber, request);
			JSONObject result = new JSONObject();
			result.put(YZActionKeys.JSON_RET_DATA, json);
			YZUtility.DEAL_SUCCESS(result, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 绑定微信号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bindWxOpenId.act")
	public String bindWxOpenId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("userSeqId");
			String openid = request.getParameter("openid");
			int count = logic.bindWxOpenId(seqId, openid, request);
			JSONObject result = new JSONObject();
			result.put(YZActionKeys.JSON_RET_DATA, count);
			YZUtility.DEAL_SUCCESS(result, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 创建二维码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createQRCodeByUserCode.act")
	public String createQRCodeByUserCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String accountid = YZSysProps.getProp("WEIXIN_ACCOUNTID");
			String qrcode_create_url = YZSysProps.getString("QRCODE_CREATE_URL");

			if (YZUtility.isNullorEmpty(usercode)) {
				throw new Exception("usercode不能为空");
			}

			if (YZUtility.isNullorEmpty(accountid)) {
				throw new Exception("accountid不能为空");
			}

			if (YZUtility.isNullorEmpty(qrcode_create_url)) {
				throw new Exception("qrcode_create_url不能为空");
			}

			int scene_id = wxUtilLogic.getRandom4QrCode(usercode);
			// 场景值ID，临时二维码时为32位非0整型，最大值是2的32次方减1也就是4294967295
			qrcode_create_url += "&scene_id=" + scene_id + "&wxaccountid=" + accountid;

			JSONObject rtData = WXUtilLogic.httpRequest(qrcode_create_url, wxUtilLogic.GET_METHOD, null);

			if (rtData.containsKey("errcode")) {
				String msg = "错误编号：" + rtData.getString("errcode") + "，错误信息：" + rtData.getString("errmsg");
				throw new Exception(msg);
			}

			YZUtility.DEAL_SUCCESS(rtData, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 新增或修改或者信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert4kefu.act")
	public String insert4kefu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			KqdsUserdocument dp = new KqdsUserdocument();
			KqdsNetOrder netorder = new KqdsNetOrder();
			BeanUtils.populate(dp, request.getParameterMap());
			BeanUtils.populate(netorder, request.getParameterMap());
			/** 微信预约后台审核 专用参数 **/
			String openid = request.getParameter("openid");

			if (YZUtility.isNullorEmpty(openid)) {
				throw new Exception("openid不能为空");
			}

			KqdsUserdocument json = logic.getBindUserDocByOpenId(openid, request);
			if (json != null) {
				throw new Exception("该微信已绑定患者，无法再次建档，已绑定患者姓名：" + json.getUsername() + ",编号：" + json.getUsercode());
			}

			// ############### 【重要，后面的患者编号重复判断，有漏洞，在此修复】
			// 2018.3.11 改为如果开两个页面建档，编号重复，则直接获取新的usercode
			String usercode = dp.getUsercode();
			String jdOrganization = ChainUtil.getCurrentOrganization(request);
			if (ConstUtil.USER_TYPE_1 == dp.getType()) {
				jdOrganization = ChainUtil.getOrganizationFromUrl(request);
			}
			String realusercode = UserCodeUtil.getUserCode4Insert(jdOrganization, usercode);
			if (!usercode.equals(realusercode)) {
				dp.setUsercode(realusercode);
				logger.error("----页面患者编号：" + usercode + ",新编号：" + realusercode);
			}

			// 验证手机号码是否重复
			if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber1())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("phonenumber1", dp.getPhonenumber1());
				int count = userDocLogic.checkphonenumber(null, map, TableNameUtil.KQDS_USERDOCUMENT);
				if (count > 0) {
					throw new Exception("手机号码1已存在：" + dp.getPhonenumber1());
				}
			}

			if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber2())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("phonenumber2", dp.getPhonenumber2());
				int count = userDocLogic.checkphonenumber(null, map, TableNameUtil.KQDS_USERDOCUMENT);
				if (count > 0) {
					throw new Exception("手机号码2已存在：" + dp.getPhonenumber2());
				}
			}
			// 验证手机号码是否重复 结束...

			String uuid = YZUtility.getUUID();
			dp.setSeqId(uuid);
			dp.setIsdelete(0);
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			/*************************************
			 * 生成条形码信息start
			 **************************************************/
			// 生成条形码
			// dp.setBarcode(BarcodeUtil.generateBarCode128(dp.getUsercode(),
			// "0.5", "15"));
			/*************************************
			 * 生成条形码信息end
			 **************************************************/
			if (1 == dp.getType()) {
				/** 新建网电信息 **/
				netorder.setSeqId(YZUtility.getUUID());
				netorder.setCreatetime(YZUtility.getCurDateTimeStr());
				netorder.setCreateuser(person.getSeqId());
				netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request)); // 【网电建档，以页面传入为准】
				/** #### 置空 **/
				netorder.setDoorstatus(0); // 上门状态 0 未上门 1已上门
				netorder.setCjstatus(0); // 成交状态 0 未成交 1已成交
				netorder.setUsercode(dp.getUsercode());
				netorder.setUsername(dp.getUsername());
				netorder.setAcceptdate(dp.getCreatetime()); // 受理日期
				netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request)); // 【网电建档，以页面传入为准】
				logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
			}

			// ### 入库
			dp.setBindstatus("0");
			logic.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, dp);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_USERDOCUMENT, dp, dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);

			/** 新建档案默认创建一张预交金卡 **/
			addMember4CreateUserDocument(dp, person, request);

			/*************** 微信回复文本信息 ***********************/
			logic.setFansUserCode(dp.getOpenid(), dp.getUsercode(), request);
			// 建档通知
			wxUtil4TemplateMsgLogic.createDocSuccessNotify(dp, netorder, request);

			/** 建档后更新 **/
			wxUtil4ChatLogic.updateChatUserListMsg(dp.getOpenid(), MessageUtil.EVENT_TYPE_CREATE_DOC, dp, null);

			if (!YZUtility.isNullorEmpty(netorder.getOrdertime())) {
				wxUtil4TemplateMsgLogic.orderSuccessNotify(dp.getUsercode(), dp.getOpenid(), netorder, request);
			}

			/** 更改未读消息数 **/

			JSONObject jobj = new JSONObject();
			jobj.put("username", dp.getUsername());
			jobj.put("usercode", dp.getUsercode());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);

		}
		return null;
	}

	/**
	 * 新增或修改或者信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert.act")
	public String insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			KqdsUserdocument dp = new KqdsUserdocument();
			KqdsNetOrder netorder = new KqdsNetOrder();
			BeanUtils.populate(dp, request.getParameterMap());
			BeanUtils.populate(netorder, request.getParameterMap());
			/** 微信预约后台审核 专用参数 **/
			String wxOrderSeqId = request.getParameter("wxOrderSeqId");
			// ############### 【重要，后面的患者编号重复判断，有漏洞，在此修复】
			// 2018.3.11 改为如果开两个页面建档，编号重复，则直接获取新的usercode
			String usercode = dp.getUsercode();
			String jdOrganization = ChainUtil.getCurrentOrganization(request);
			if (ConstUtil.USER_TYPE_1 == dp.getType()) {
				jdOrganization = ChainUtil.getOrganizationFromUrl(request);
			}
			String realusercode = UserCodeUtil.getUserCode4Insert(jdOrganization, usercode);
			if (!usercode.equals(realusercode)) {
				dp.setUsercode(realusercode);
				logger.error("----页面患者编号：" + usercode + ",新编号：" + realusercode);
			}

			// 验证手机号码是否重复
			if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber1())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("phonenumber1", dp.getPhonenumber1());
				int count = userDocLogic.checkphonenumber(null, map, TableNameUtil.KQDS_USERDOCUMENT);
				if (count > 0) {
					throw new Exception("手机号码1已存在：" + dp.getPhonenumber1());
				}
			}

			if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber2())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("phonenumber2", dp.getPhonenumber2());
				int count = userDocLogic.checkphonenumber(null, map, TableNameUtil.KQDS_USERDOCUMENT);
				if (count > 0) {
					throw new Exception("手机号码2已存在：" + dp.getPhonenumber2());
				}
			}
			// 验证手机号码是否重复 结束...

			String uuid = YZUtility.getUUID();
			dp.setSeqId(uuid);
			dp.setIsdelete(0);
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			/*************************************
			 * 生成条形码信息start
			 **************************************************/
			// 生成条形码
			// dp.setBarcode(BarcodeUtil.generateBarCode128(dp.getUsercode(),
			// "0.5", "15"));
			/*************************************
			 * 生成条形码信息end
			 **************************************************/
			if (1 == dp.getType()) {
				/** 新建网电信息 **/
				netorder.setSeqId(YZUtility.getUUID());
				netorder.setCreatetime(YZUtility.getCurDateTimeStr());
				netorder.setCreateuser(person.getSeqId());
				netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request)); // 【网电建档，以页面传入为准】
				netorder.setAskno(wxOrderSeqId);
				netorder.setDoorstatus(0); // 上门状态 0 未上门 1已上门
				netorder.setCjstatus(0); // 成交状态 0 未成交 1已成交
				netorder.setUsercode(dp.getUsercode());
				netorder.setUsername(dp.getUsername());
				netorder.setAcceptdate(dp.getCreatetime()); // 受理日期
				netorder.setOrganization(ChainUtil.getOrganizationFromUrl(request)); // 【网电建档，以页面传入为准】
				logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
			}

			/** 微信预约的审核操作 **/
			if (!YZUtility.isNullorEmpty(wxOrderSeqId)) {
				WXOrder wxorder = (WXOrder) logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, wxOrderSeqId);
				if (wxorder == null) {
					throw new Exception("数据不存在");
				}
				dp.setOpenid(wxorder.getOpenid()); // 患者档案赋值
				dp.setBindstatus("0");

				wxorder.setAuditor(person.getSeqId());
				wxorder.setOrderstatus(WXConst.ORDER_STATUS_1);
				wxorder.setConfirmtime(netorder.getOrdertime());
				logic.updateSingleUUID(TableNameUtil.WX_ORDER, wxorder);
			}

			// ### 入库
			dp.setBindstatus("0");
			logic.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, dp);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_USERDOCUMENT, dp, dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);

			/** 新建档案默认创建一张预交金卡 **/
			addMember4CreateUserDocument(dp, person, request);

			/*************** 微信回复文本信息 ***********************/
			logic.setFansUserCode(dp.getOpenid(), dp.getUsercode(), request);
			// 建档通知
			wxUtil4TemplateMsgLogic.createDocSuccessNotify(dp, netorder, request);

			if (!YZUtility.isNullorEmpty(netorder.getOrdertime())) {
				wxUtil4TemplateMsgLogic.orderSuccessNotify(dp.getUsercode(), dp.getOpenid(), netorder, request);
			}

			JSONObject jobj = new JSONObject();
			jobj.put("username", dp.getUsername());
			jobj.put("usercode", dp.getUsercode());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);

		}
		return null;
	}

	/**
	 * 网店预约审核-新增网电记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertNetOrder.act")
	public String insertNetOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			KqdsNetOrder netorder = new KqdsNetOrder();
			BeanUtils.populate(netorder, request.getParameterMap());

			if (YZUtility.isNullorEmpty(netorder.getUsercode())) {
				throw new Exception("usercode不能为空");
			}

			if (YZUtility.isNullorEmpty(netorder.getOrdertime())) {
				throw new Exception("ordertime不能为空");
			}

			KqdsUserdocument dp = userDocLogic.getSingleUserByUsercode(netorder.getUsercode());
			if (dp == null) {
				throw new Exception("患者不存在，usercode：" + netorder.getUsercode());
			}

			/** 微信预约后台审核 专用参数 **/
			String wxOrderSeqId = request.getParameter("wxOrderSeqId");

			/** 微信预约的审核操作 **/
			if (YZUtility.isNullorEmpty(wxOrderSeqId)) {
				throw new Exception("wxOrderSeqId不能为空");
			}
			WXOrder wxorder = (WXOrder) logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, wxOrderSeqId);
			if (wxorder == null) {
				throw new Exception("数据不存在");
			}
			// ############### 【重要，后面的患者编号重复判断，有漏洞，在此修复】
			String ordertime4check = (netorder.getOrdertime()).substring(0, 10); // 取到日期
																					// 2017-04-09
			List<JSONObject> list = netOrderlogic.checkNetOrderCount(null, dp.getUsercode(), ordertime4check);
			if (list.size() >= 1) {
				throw new Exception("同一个患者当天只能网电预约一次，患者编号：" + dp.getUsercode() + "，预约日期：" + netorder.getOrdertime() + "。");
			}
			/** 新建网电信息 **/
			netorder.setSeqId(YZUtility.getUUID());
			netorder.setCreatetime(YZUtility.getCurDateTimeStr());
			netorder.setCreateuser(person.getSeqId());
			netorder.setOrganization(wxorder.getOrganization()); // 【网电建档，以页面传入为准】
			netorder.setAskno(wxOrderSeqId);
			netorder.setDoorstatus(0); // 上门状态 0 未上门 1已上门
			netorder.setCjstatus(0); // 成交状态 0 未成交 1已成交
			netorder.setUsercode(netorder.getUsercode());
			netorder.setUsername(dp.getUsername());
			netorder.setAcceptdate(YZUtility.getCurDateTimeStr()); // 受理日期
			logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, netorder);
			/** 更新微信预约表 **/
			wxorder.setAuditor(person.getSeqId());
			wxorder.setOrderstatus(WXConst.ORDER_STATUS_1);
			wxorder.setConfirmtime(netorder.getOrdertime());
			logic.updateSingleUUID(TableNameUtil.WX_ORDER, wxorder);
			/*************** 微信回复文本信息 ***********************/
			wxUtil4TemplateMsgLogic.orderSuccessNotify(dp.getUsercode(), dp.getOpenid(), netorder, request);

			JSONObject jobj = new JSONObject();
			jobj.put("username", dp.getUsername());
			jobj.put("usercode", dp.getUsercode());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);

		}
		return null;
	}

	/**
	 * 建档时，自动创建预交金卡
	 * 
	 * @param user
	 * @param person
	 * @param dbConn
	 * @param request
	 * @throws Exception
	 */
	private void addMember4CreateUserDocument(KqdsUserdocument user, YZPerson person, HttpServletRequest request) throws Exception {
		KqdsMember dp = new KqdsMember();

		dp.setSeqId(YZUtility.getUUID());
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		dp.setCreateuser(person.getSeqId());
		// 默认卡号为usercode
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
		String HYK_BINDING = SysParaUtil.getSysValueByName(request, SysParaUtil.HYK_BINDING);
		if (HYK_BINDING.equals("0")) {
			dp.setIcno(dp.getMemberno());
			dp.setIsbinding(1);
		}
		logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER, dp);
	}

}
