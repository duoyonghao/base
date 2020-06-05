package com.kqds.timer.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.push.KQDS_Pushogic;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.util.base.WebSocketUtil;
import com.kqds.util.base.websocket.Online_User_List;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Component
@Controller
public class MsgPushTask implements Job {
	private static Logger logger = LoggerFactory.getLogger(MsgPushTask.class);
	@Autowired
	private KQDS_Pushogic logic;
	@Autowired
	private YZOnlineLogic onlineLogic;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			doTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doTask() throws Exception {
		// 在线用户数
		int count = onlineLogic.queryUserCount();
		StringBuffer userIdBf = new StringBuffer();
		for (Map.Entry<String, Session> entry : Online_User_List.Websockt_Session_MAP.entrySet()) {
			String userId = entry.getKey();
			// 根据USERID获取登录的用户信息
			HttpSession session = SessionUtil.Session_MAP.get(userId);
			if (session == null) {
				continue;
			}
			YZPerson person = (YZPerson) session.getAttribute(ConstUtil.LOGIN_USER);
			if (person == null) {
				continue;
			}

			List<JSONObject> list = null;
			int pushnum = logic.selectPageNum(person.getSeqId()); // 获取PC端总的未推送消息数
			if (pushnum == 0) {
				list = new ArrayList<JSONObject>();
			} else {
				list = logic.selectNoPageWithUserId(TableNameUtil.KQDS_PUSH, person.getSeqId()); // 获取最新的5条推送消息
			}

			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, list);
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			jobj.put("retDataPushNum", pushnum);
			jobj.put("retDataOnline", count); // 推送在线人数
			String pushText = jobj.toString();
			Session sessionSocket = entry.getValue();
			WebSocketUtil.sendMsg2Page(sessionSocket, pushText);
			logger.debug(userId + "推送成功，推送内容" + pushText);

			userIdBf.append(userId).append(",");
		}
	}
}
