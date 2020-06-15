package com.kqds.controller.wx.order;

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

import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.wx.WXOrder;
import com.kqds.service.base.netOrder.KQDS_Net_OrderLogic;
import com.kqds.service.wx.core.WXUtilLogic;
import com.kqds.service.wx.order.WXOrderLogic;
import com.kqds.service.wx.receivetext.WXReceiveTextLogic;
import com.kqds.service.wx.user.WXUserDocLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.wx.WXConst;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("WXOrderAct")
public class WXOrderAct {

	private static Logger logger = LoggerFactory.getLogger(WXOrderAct.class);
	@Autowired
	private WXOrderLogic logic;
	@Autowired
	private WXUserDocLogic wxUserLogic;
	@Autowired
	private KQDS_Net_OrderLogic netOrderLogic;
	@Autowired
	private WXReceiveTextLogic textLogic;

	@Autowired
	private WXUtilLogic wxUtilLogic;

	/**
	 * 新增、编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			WXOrder dp = new WXOrder();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			String openid = request.getParameter("openid");
			String phonenumber = request.getParameter("phonenumber");
			String orderdate = request.getParameter("orderdate");
			String ordertime = request.getParameter("ordertime");
			String organization = request.getParameter("organization");
			if (YZUtility.isNullorEmpty(openid)) {
				throw new Exception("openid不能为空");
			}
			if (YZUtility.isNullorEmpty(orderdate)) {
				throw new Exception("orderdate不能为空");
			}
			if (YZUtility.isNullorEmpty(ordertime)) {
				throw new Exception("ordertime不能为空");
			}
			if (YZUtility.isNullorEmpty(organization)) {
				throw new Exception("organization不能为空");
			}

			if (YZUtility.isNullorEmpty(phonenumber)) {
				/** 此时，手机号码从 kqds_userdocument中取值 **/
			} else {
				int count = wxUserLogic.phoneIsExist(phonenumber, openid);
				if (count > 0) {
					throw new Exception("该手机号码已被他人使用，请确认输入信息正确!");
				}
			}

			int count = logic.countToday(openid, orderdate, request);
			if (count > 0) {
				throw new Exception("一天只能预约一次!");
			}

			if (!YZUtility.isNullorEmpty(seqId)) {
				/** 只有新增操作 **/
			} else {
				dp.setSeqId(YZUtility.getUUID());
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				logic.saveSingleUUID(TableNameUtil.WX_ORDER, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.WX_ORDER, dp, TableNameUtil.WX_ORDER, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 取消预约
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancel.act")
	public String cancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String reason = request.getParameter("reason");
			WXOrder order = (WXOrder) logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, seqId);
			if (order == null) {
				throw new Exception("预约记录不存在！");
			}
			order.setCancelreason(reason);
			order.setCanceltime(YZUtility.getCurDateTimeStr());
			order.setOrderstatus(WXConst.ORDER_STATUS_3);

			// 修改
			logic.updateSingleUUID(TableNameUtil.WX_ORDER, order);

			/** 如果存在网电预约，则更改状态为删除 **/
			KqdsNetOrder netOrder = netOrderLogic.getNetOrderByWXOrderSeqId(seqId);
			if (netOrder != null) {
				netOrder.setIsdelete(1); // 1代表已删除
				logic.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, seqId);
			}

			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.CANCEL, BcjlUtil.WX_ORDER, order, TableNameUtil.WX_ORDER, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditor.act")
	public String auditor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			String seqId = request.getParameter("seqId");
			String auditorremark = request.getParameter("auditorremark");
			String orderstatus = request.getParameter("orderstatus");
			// String ordertime = request.getParameter("ordertime");

			if (YZUtility.isNullorEmpty(auditorremark)) {
				throw new Exception("auditorremark不能为空");
			}
			if (YZUtility.isNullorEmpty(orderstatus)) {
				throw new Exception("orderstatus不能为空");
			}

			WXOrder order = (WXOrder) logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, seqId);
			if (order == null) {
				throw new Exception("预约记录不存在！");
			}

			// JSONObject userdoc =
			// wxUserLogic.getUserDocByOpenId(order.getOpenid(),
			// request);

			/** 审核通过 **/
			if (WXConst.ORDER_STATUS_1 == Integer.parseInt(orderstatus)) {
				/**
				 * if (YZUtility.isNullorEmpty(ordertime)) { throw new
				 * Exception("ordertime不能为空"); }
				 * 
				 * YZDict sslx = dictLogic.getDetailByNameAndParentCode(dbConn,"其他", "SLLX");
				 * YZDict ssgj = dictLogic.getDetailByNameAndParentCode(dbConn,"公众微信号", "SLGJ");
				 * 
				 * KQDS_Net_Order netorder = new KQDS_Net_Order();
				 * netorder.setSeqId(YZUtility.getUUID());
				 * netorder.setCreateuser(person.getSeqId());
				 * netorder.setCreatetime(YZUtility.getCurDateTimeStr());
				 * netorder.setAcceptdate(netorder.getCreatetime()); // 受理日期
				 * 
				 * netorder.setAskno(order.getSeqId()); // 微信预约主键
				 * netorder.setUsercode(order.getUsercode());
				 * netorder.setOrganization(order.getOrganization());
				 * netorder.setAskitem(order.getAskitem());
				 * netorder.setAskcontent(order.getAskcontent());
				 * netorder.setOrdertime(ordertime);// 预约时间
				 * 
				 * netorder.setDoorstatus(0); // 上门状态 0 未上门 1已上门 netorder.setCjstatus(0); //
				 * 成交状态 0 未成交 1已成交
				 * 
				 * netorder.setUsername(userdoc.getUsername());
				 * 
				 * netorder.setAccepttool(ssgj.getSeqId());
				 * netorder.setAccepttype(sslx.getSeqId());
				 * 
				 * logic.saveSingleUUID(netorder, TableNameUtil.KQDS_NET_ORDER, request);
				 **/
			}

			order.setAuditor(person.getSeqId());
			order.setAuditorremark(auditorremark);
			order.setOrderstatus(Integer.parseInt(orderstatus));
			order.setAuditortime(YZUtility.getCurDateTimeStr());

			// 修改
			logic.updateSingleUUID(TableNameUtil.WX_ORDER, order);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.AUDITOR, BcjlUtil.WX_ORDER, order, TableNameUtil.WX_ORDER, request);

			String sendContent = "对不起，预约失败：" + auditorremark;
			String sendMsgUrl = textLogic.getUrl4Send(sendContent);
			wxUtilLogic.sendTextMsg(sendMsgUrl, null, order.getOpenid(), sendContent, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			WXOrder order = (WXOrder) logic.loadObjSingleUUID(TableNameUtil.WX_ORDER, seqId);
			logic.deleteSingleUUID(TableNameUtil.WX_ORDER, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.WX_ORDER, order, TableNameUtil.WX_ORDER, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String openid = request.getParameter("openid");
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(openid)) {
				map.put("openid", openid);
			}
			JSONObject data = logic.selectPage(TableNameUtil.WX_ORDER, bp, map);
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			String orderdate = request.getParameter("orderdate");
			String confirmdateStart = request.getParameter("confirmdateStart");
			String confirmdateEnd = request.getParameter("confirmdateEnd");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();

			if (!YZUtility.isNullorEmpty(orderdate)) {
				map.put("orderdate", orderdate);
			}
			if (!YZUtility.isNullorEmpty(confirmdateStart)) {
				map.put("confirmdateStart", confirmdateStart + ConstUtil.HOUR_START);
			}
			if (!YZUtility.isNullorEmpty(confirmdateEnd)) {
				map.put("confirmdateEnd", confirmdateEnd + ConstUtil.HOUR_END);
			}

			List<JSONObject> list = logic.selectList(TableNameUtil.WX_ORDER, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("seqId不能为空");
			}

			JSONObject en = logic.selectDetail(TableNameUtil.WX_ORDER, seqId);
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

}
