package com.kqds.timer.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsSms;
import com.kqds.util.sms.SmsSender;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**
 * 在线人员检测
 * 
 * @author Administrator
 * 
 */
@Service
public class CheckSmsTask implements Job {
	@Autowired
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public void run() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> map = new HashMap<String, String>();
		map.put("smsstate", "1");// 定时
		map.put("sendstate", "0");// 未发送
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_SMS + ".getListsql", map);
		if (list != null && list.size() > 0) {
			for (JSONObject jobj : list) {
				KqdsSms dp = (KqdsSms) JSONObject.toBean(jobj, KqdsSms.class);
				Date sendtime = sdf.parse(dp.getSendtime());
				long sendtimeLog = sendtime.getTime();
				long nowtimeLog = new Date().getTime();
				if (nowtimeLog > sendtimeLog) {
					String returnString = SmsSender.getSenderTx(dp.getSendphone(), dp.getSmscontent());
					dp.setSendstate(Integer.parseInt(returnString));
					dp.setSendtime(YZUtility.getCurDateTimeStr());
					dao.updateSingleUUID(TableNameUtil.KQDS_SMS, dp);
				}
			}
		}
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
