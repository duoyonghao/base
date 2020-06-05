package com.kqds.service.sys.online;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.kqds.entity.sys.YZOnline;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

@Service("onlineLogic")
public class YZOnlineLogic {

	/**
	 * 根据用户主键，获取该用户对应的在线信息
	 * 
	 * @param userSeqId
	 * @return
	 */
	public YZOnline getOnlineByUserSeqId(String userTmpId) {
		for (Map.Entry<String, HttpSession> entry : SessionUtil.Session_MAP.entrySet()) {
			String userId = entry.getKey();
			HttpSession session = entry.getValue();
			if (session == null) {
				continue;
			}
			if (userTmpId.equals(userId)) {
				YZOnline online = (YZOnline) session.getAttribute(SessionUtil.ONLINE_INFO);
				return online;
			}
		}
		return null;
	}

	/**
	 * 更新在线信息
	 * 
	 * @param userSeqId
	 * @param online
	 */
	public void updateOnlineInfoByUserSeqId(YZOnline online) {
		String userIdTmp = online.getUserId();
		for (Map.Entry<String, HttpSession> entry : SessionUtil.Session_MAP.entrySet()) {
			String userId = entry.getKey();
			HttpSession session = entry.getValue();
			if (session == null) {
				continue;
			}
			if (userIdTmp.equals(userId)) {
				session.setAttribute(SessionUtil.ONLINE_INFO, online);
			}
		}
	}

	public List<YZOnline> getList() throws SQLException {
		List<YZOnline> list = new ArrayList<YZOnline>();
		for (Map.Entry<String, HttpSession> entry : SessionUtil.Session_MAP.entrySet()) {
			HttpSession session = entry.getValue();
			if (session == null) {
				continue;
			}
			try {
				YZOnline online = (YZOnline) session.getAttribute(SessionUtil.ONLINE_INFO);
				if (online == null) {
					continue;
				}
				list.add(online);
			} catch (Exception e) {
				// TODO: handle exception
			} 
		}

		return list;
	}

	/**
	 * 判断指定用户是否已经登录
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isLogin(String userTmpId) throws Exception {
		int count = 0;
		for (Map.Entry<String, HttpSession> entry : SessionUtil.Session_MAP.entrySet()) {
			String userId = entry.getKey();
			if (userTmpId.equals(userId)) {
				count++;
			}
		}
		return count > 0;
	}

	public int queryUserCount() throws Exception {
		int count = SessionUtil.Session_MAP.size();
		return count;
	}

	/**
	 * 设置用户在线 --- ### 增加同步，否则会死锁
	 * 
	 * @param conn
	 * @param person
	 * @param sessionToken
	 * @throws Exception
	 */
	public void addOnline(YZPerson person, String sessionToken, HttpSession session, HttpServletRequest request) throws Exception {
		YZOnline online = new YZOnline();
		online.setSeqId(YZUtility.getUUID());
		online.setSessionToken(sessionToken);
		online.setLoginTime(new Date());
		online.setUserId(person.getSeqId());
		online.setUserState("0");
		online.setOrganization(ChainUtil.getCurrentOrganization(session)); // 当前所在门诊
		online.setUpdateTime(System.currentTimeMillis());
		// 登录成功后将用户信息加入USER_ONLINE表中 其中SESSION_TOKEN代替USER_ID作为标识
		session.setAttribute(SessionUtil.ONLINE_INFO, online);
	}

}
