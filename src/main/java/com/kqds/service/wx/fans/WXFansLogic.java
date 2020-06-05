package com.kqds.service.wx.fans;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.wx.WXFans;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.wx.core.WXUtil4ChatLogic;
import com.kqds.service.wx.core.WXUtilLogic;
import com.kqds.service.wx.user.WXUserDocLogic;
import com.kqds.util.base.WebSocketUtil;
import com.kqds.util.base.websocket.WeChat_UserBind_Monitor;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class WXFansLogic extends BaseLogic {
	private static Logger logger = LoggerFactory.getLogger(WXFansLogic.class);
	@Autowired
	private DaoSupport dao;
	@Autowired
	private KQDS_UserDocumentLogic userDocLogic;
	@Autowired
	private WXUserDocLogic wxUserLogic;

	@Autowired
	private WXUtil4ChatLogic wxUtil4ChatLogic;

	@Autowired
	private WXUtilLogic wxUtilLogic;

	@SuppressWarnings("unchecked")
	public List<JSONObject> selectList4Chat(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".selectList4Chat", map);
		/** 导出的时候转换 **/
		for (JSONObject fans : list) {
			fans.put("talkstatus", wxUtil4ChatLogic.getTalkStatusByOpenid(fans.getString("openid")));
		}

		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject selectPage(BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".selectPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		;
		if (map.containsKey("flag")) {
			/** 导出的时候转换 **/
			for (JSONObject fans : list) {
				String carestatus = fans.getString("carestatus");
				if ("0".equals(carestatus)) {
					fans.put("carestatus", "正常");
				}
				if ("1".equals(carestatus)) {
					fans.put("carestatus", "取消关注");
				}
			}
		}

		jobj.put("rows", list);
		return jobj;
	}

	public JSONObject selectPageObjByOpenid(String openid) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject(TableNameUtil.WX_RECEIVETEXT + ".selectPageObjByOpenid", openid);

		return json;
	}

	/**
	 * 更新最后接收到信息的时间
	 * 
	 * @param openid
	 * @param request
	 * @throws Exception
	 */
	public void updateLastTimeByOpenid(String openid, HttpServletRequest request) throws Exception {
		WXFans fans = getFanByOpenid(openid, request);
		if (fans != null) {
			fans.setLastmsgtime(YZUtility.getCurDateTimeStr());
			dao.updateSingleUUID(TableNameUtil.WX_FANS, fans);
		}
	}

	/**
	 * 根据微信Id获取对象
	 * 
	 * @param openid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public WXFans getFanByOpenid(String openid, HttpServletRequest request) throws Exception {
		WXFans fans = (WXFans) dao.findForObject(TableNameUtil.WX_RECEIVETEXT + ".getFanByOpenid", openid);
		return fans;
	}

	/**
	 * 普通关注
	 * 
	 * @param eventkey
	 * @param openid
	 * @param conn
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void normalCare(String openid, HttpServletRequest request) throws Exception {
		List<WXFans> list = (List<WXFans>) dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".getFanByOpenid", openid);
		if (list != null && list.size() > 1) {
			throw new Exception("数据异常：根据微信ID匹配到多条记录【wx_fans】");
		}

		if (list == null || list.size() == 0) {
			WXFans user = new WXFans();
			user.setSeqId(YZUtility.getUUID());
			user.setOpenid(openid);
			user.setBindtime(YZUtility.getCurDateTimeStr());
			user.setCreatetime(YZUtility.getCurDateTimeStr());
			user.setCarestatus(0); // 0关注 1取消关注
			wxUserLogic.saveSingleUUID(TableNameUtil.WX_FANS, user);
		} else {
			WXFans user = list.get(0);
			user.setBindtime(YZUtility.getCurDateTimeStr());
			user.setCreatetime(YZUtility.getCurDateTimeStr());
			user.setCarestatus(0); // 0关注 1取消关注
			wxUserLogic.updateSingleUUID(TableNameUtil.WX_FANS, user);
		}
	}

	/**
	 * 绑定微信号
	 * 
	 * @param eventkey
	 * @param openid
	 * @param conn
	 * @param request
	 * @throws Exception
	 */
	public void bindWXUser(String eventkey, String openid, HttpServletRequest request) throws Exception {
		String qrscene = eventkey.replaceAll("qrscene_", "");

		String usercode = wxUtilLogic.getUserCodeFromQrCodeMap(qrscene);

		KqdsUserdocument doc = userDocLogic.getSingleUserByUsercode(usercode);
		if (doc == null) {
			throw new Exception("根据usercode匹配不到患者档案信息，usercode值：" + usercode);
		}

		// 当前扫码的患者，微信处于绑定状态
		if ((!YZUtility.isNullorEmpty(doc.getOpenid()) && "0".equals(doc.getBindstatus()))) {
			if (!doc.getOpenid().equals(openid)) { // 如果扫码的微信，不是之前绑定的那个微信
				logger.error("该患者已绑定微信号，不能再次绑定，患者姓名：" + doc.getUsername() + "，患者编号：" + doc.getUsercode() + ",微信Openid：" + doc.getOpenid());

				JSONObject msg = new JSONObject();
				msg.put("bindalother", openid);
				Session session = WeChat_UserBind_Monitor.WeChat_UserBind_Monitor.get(doc.getUsercode()); // 这里变量注意，别引用成userA
				WebSocketUtil.sendMsg2Page(session, msg.toString());
			} else {
				logger.error("您已绑定该微信号，无需重复绑定，患者姓名：" + doc.getUsername() + "，患者编号：" + doc.getUsercode() + ",微信Openid：" + doc.getOpenid());

				JSONObject msg = new JSONObject();
				msg.put("bindalready", openid);
				Session session = WeChat_UserBind_Monitor.WeChat_UserBind_Monitor.get(doc.getUsercode()); // 这里变量注意，别引用成userA
				WebSocketUtil.sendMsg2Page(session, msg.toString());
			}
			// 不绑定用户，但是该用户已成为粉丝
			dealSubcribeByScan(openid, null, request);
		} else { // 当前扫码的患者，微信处于未绑定状态
			/**
			 * 由于一个微信号只能绑定一个患者档案， 所以当该微信号之前已经绑定A档案的情况下， 这时，再绑定档案B，需要给予确认提示
			 */
			KqdsUserdocument userA = wxUserLogic.getBindUserDocByOpenId(openid, request);
			if (userA != null) {
				JSONObject msg = new JSONObject();
				msg.put("usercode", userA.getUsercode());
				msg.put("username", userA.getUsername());
				msg.put("openid", openid);

				Session session = WeChat_UserBind_Monitor.WeChat_UserBind_Monitor.get(doc.getUsercode()); // 这里变量注意，别引用成userA
				WebSocketUtil.sendMsg2Page(session, msg.toString());
			} else {
				bindWXUser(openid, doc, request);

				JSONObject msg = new JSONObject();
				msg.put("bindok", openid);
				Session session = WeChat_UserBind_Monitor.WeChat_UserBind_Monitor.get(doc.getUsercode()); // 这里变量注意，别引用成userA
				WebSocketUtil.sendMsg2Page(session, msg.toString());
			}
		}
	}

	/**
	 * 绑定微信
	 * 
	 * @param openid
	 * @param doc
	 * @param conn
	 * @param request
	 * @throws Exception
	 */
	public void bindWXUser(String openid, KqdsUserdocument doc, HttpServletRequest request) throws Exception {
		// 处理扫码关注公众号
		dealSubcribeByScan(openid, doc, request);

		// 先解绑
		unBindWXUser(openid, request);

		// 再绑定
		wxUserLogic.bindWxOpenId(doc.getSeqId(), openid, request);
	}

	/**
	 * 处理扫码关注公众号
	 * 
	 * @param openid
	 * @param doc
	 * @param conn
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void dealSubcribeByScan(String openid, KqdsUserdocument doc, HttpServletRequest request) throws Exception {
		List<WXFans> list = (List<WXFans>) dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".getFanByOpenid", openid);
		if (list != null && list.size() > 1) {
			throw new Exception("数据异常：根据患者编号匹配到多条记录【wx_fans】");
		}

		if (list == null || list.size() == 0) {
			WXFans user = new WXFans();
			user.setSeqId(YZUtility.getUUID());
			if (doc != null) { // 传入doc，才可以更新患者编号，否则不允许更新
				user.setUsercode(doc.getUsercode());
			}
			user.setOpenid(openid);
			user.setBindtime(YZUtility.getCurDateTimeStr());
			user.setCreatetime(YZUtility.getCurDateTimeStr());
			user.setCarestatus(0); // 0关注 1取消关注
			wxUserLogic.saveSingleUUID(TableNameUtil.WX_FANS, user);
		} else {
			WXFans user = list.get(0);
			if (doc != null) { // 传入doc，才可以更新患者编号，否则不允许更新
				user.setUsercode(doc.getUsercode());
			}
			user.setBindtime(YZUtility.getCurDateTimeStr());
			user.setCarestatus(0); // 0关注 1取消关注
			wxUserLogic.updateSingleUUID(TableNameUtil.WX_FANS, user);
		}
	}

	/**
	 * 解绑微信号
	 * 
	 * @param eventkey
	 * @param openid
	 * @param conn
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void unBindWXUser(String openid, HttpServletRequest request) throws Exception {
		List<WXFans> list = (List<WXFans>) dao.findForList(TableNameUtil.WX_RECEIVETEXT + ".getFanByOpenid", openid);
		if (list != null && list.size() > 1) {
			throw new Exception("数据异常：根据微信ID匹配到多条记录【wx_fans】");
		}

		if (list == null || list.size() == 0) {
			// throw new Exception("数据异常：根据微信ID匹没有匹配到记录【wx_fans】");
		} else {
			WXFans user = list.get(0);
			user.setUnbindtime(YZUtility.getCurDateTimeStr());
			user.setCarestatus(1); // 0关注 1取消关注
			user.setUsercode("");
			wxUserLogic.updateSingleUUID(TableNameUtil.WX_FANS, user);
		}

		KqdsUserdocument user = wxUserLogic.getBindUserDocByOpenId(openid, request);
		if (user != null) {
			user.setBindstatus("");
			user.setOpenid("");
			wxUserLogic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, user);
		}
	}

}
