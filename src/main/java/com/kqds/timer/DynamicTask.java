package com.kqds.timer;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;
import com.kqds.entity.sys.YZTimeTask;
import com.kqds.service.sys.timer.TimeTaskLogic;
import com.kqds.util.base.util.HttpRequestUtils;
import com.kqds.util.sys.IpUtil;
import com.kqds.util.sys.TableNameUtil;

/**
 * 动态任务,用以动态调整Spring的任务
 * 
 * @author JueYue
 * @date 2013-9-20
 * @version 1.0
 */
@Service(value = "dynamicTask")
public class DynamicTask {
	private Logger log = LoggerFactory.getLogger(DynamicTask.class);
	@Autowired
	private Scheduler schedulerFactory;

	@Autowired
	private TimeTaskLogic timeTaskService;

	/**
	 * 启动定时任务
	 * 
	 * @param task
	 */
	private boolean startTask(YZTimeTask task) {
		try {
			JobDetail jobDetail = new JobDetail();
			jobDetail.setName(task.getSeqId());
			jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
			jobDetail.setJobClass(MyClassLoader.getClassByScn(task.getClassName()));
			CronTrigger cronTrigger = new CronTrigger("cron_" + task.getSeqId(), Scheduler.DEFAULT_GROUP, jobDetail.getName(), Scheduler.DEFAULT_GROUP);
			cronTrigger.setCronExpression(task.getCronExpression());
			schedulerFactory.scheduleJob(jobDetail, cronTrigger);
			return true;
		} catch (ParseException e) {
			log.error("startTask ParseException" + e.getMessage());
		} catch (SchedulerException e) {
			log.error("startTask SchedulerException" + " cron_" + task.getSeqId() + e.getMessage());
		}
		return false;
	}

	/**
	 * 结束计划任务
	 * 
	 * @param task
	 * @throws SchedulerException
	 */
	private boolean endTask(YZTimeTask task) {

		try {
			schedulerFactory.unscheduleJob("cron_" + task.getSeqId(), Scheduler.DEFAULT_GROUP);
			return true;
		} catch (SchedulerException e) {
			log.error("endTask SchedulerException" + " cron_" + task.getSeqId() + e.getMessage());
		}
		return false;
	}

	/**
	 * 开关定时任务
	 * 
	 * @param task
	 * @param start
	 * @return
	 * @throws SchedulerException
	 */
	public boolean startOrStop(YZTimeTask task, boolean start) throws Exception {
		boolean isSuccess = start ? startTask(task) : endTask(task);
		if (isSuccess) {
			task.setIsStart(start ? "1" : "0");

			task.setIsEffect("1");

			timeTaskService.updateSingleUUID(TableNameUtil.SYS_TIMETASK, task);
			// systemService.addLog((start?"开启任务":"停止任务")+task.getTaskId(),
			// Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			log.info((start ? "开启任务" : "停止任务") + "-------TaskId:" + task.getTaskId() + "-------Describe:" + task.getTaskDescribe() + "-----ClassName:" + task.getClassName());
		}
		return isSuccess;
	}

	/**
	 * 更新触发规则
	 * 
	 * @param task
	 * @return
	 */
	public boolean updateCronExpression(YZTimeTask task) throws Exception {

		try {
			String newExpression = task.getCronExpression();
			task = (YZTimeTask) timeTaskService.loadObjSingleUUID(TableNameUtil.SYS_TIMETASK, task.getSeqId());

			// 任务运行中
			if ("1".equals(task.getIsStart())) {
				CronTriggerBean trigger = (CronTriggerBean) schedulerFactory.getTrigger("cron_" + task.getSeqId(), Scheduler.DEFAULT_GROUP);
				String originExpression = trigger.getCronExpression();
				// 检查运行中的任务触发规则是否与新规则一致
				if (!originExpression.equalsIgnoreCase(newExpression)) {
					trigger.setCronExpression(newExpression);
					schedulerFactory.rescheduleJob("cron_" + task.getSeqId(), Scheduler.DEFAULT_GROUP, trigger);
				}
			} else {
				// 立即生效
				List<String> ipList = IpUtil.getLocalIPList();
				String runServerIp = task.getRunServerIp();
				boolean isStart = task.getIsStart().equals("0");
				boolean isSuccess = false;
				if (ipList.contains(runServerIp) || StringUtil.isEmpty(runServerIp) || "本地".equals(runServerIp)) {// 当前服务器IP匹配成功
					isSuccess = this.startOrStop(task, isStart);
				} else {
					try {
						String url = "http://" + task.getRunServer() + "/timeTaskController.do?remoteTask";// spring-mvc.xml
						String param = "id=" + task.getSeqId() + "&isStart=" + (isStart ? "1" : "0");
						String jsonstr = HttpRequestUtils.httpPost(url, param, false);
						if (null != jsonstr && jsonstr.length() > 0) {
							JSONObject json = (JSONObject) JSONObject.parse(jsonstr);
							isSuccess = json.getBooleanValue("success");
						}
					} catch (ClientProtocolException e) {
						log.info("远程主机‘" + task.getRunServer() + "’响应超时");
						return false;
					} catch (IOException e) {
						log.info("远程主机‘" + task.getRunServer() + "’响应超时");
						return false;
					}
				}
				if (isSuccess) {
					/*
					 * task.setIsEffect("1"); task.setIsStart("1");
					 * timeTaskService.updateEntitie(task);
					 */
					log.info(("立即生效开启任务成功，任务ID:") + "-------TaskId:" + task.getTaskId() + "-------Describe:" + task.getTaskDescribe() + "-----ClassName:" + task.getClassName());
					return true;
				} else {
					log.info(("立即生效开启任务失败，任务ID:") + "-------TaskId:" + task.getTaskId() + "-------Describe:" + task.getTaskDescribe() + "-----ClassName:" + task.getClassName());
					return false;
				}
			}
		} catch (SchedulerException e) {
			log.error("updateCronExpression SchedulerException" + " cron_" + task.getSeqId() + e.getMessage());
		} catch (ParseException e) {
			log.error("updateCronExpression ParseException" + " cron_" + task.getSeqId() + e.getMessage());
		}

		return false;

	}

	/**
	 * 系统初始加载任务
	 * 
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("unchecked")
	public void loadTask() {
		// String serverIp = InetAddress.getLocalHost().getHostAddress();
		List<String> ipList = IpUtil.getLocalIPList();
		Map<String, String> filterMap = new HashMap<String, String>();
		// TODO 租户id 未实现
		filterMap.put("IS_EFFECT", "1");
		filterMap.put("IS_START", "1");
		List<YZTimeTask> tasks = new ArrayList<YZTimeTask>();
		try {
			tasks = (List<YZTimeTask>) timeTaskService.loadList(TableNameUtil.SYS_TIMETASK, filterMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("startTask Exception" + e.getMessage());
		}
		log.info(" register time task class num is [" + tasks.size() + "] ");
		if (tasks.size() > 0) {
			for (YZTimeTask task : tasks) {
				// startTask(task);
				try {

					String runServerIp = task.getRunServerIp();
					if (ipList.contains(runServerIp) || StringUtil.isEmpty(runServerIp) || "本地".equals(runServerIp)) {// 当前服务器IP匹配成功

						// quartz 1.6
						JobDetail jobDetail = new JobDetail();
						jobDetail.setName(task.getSeqId());
						jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
						jobDetail.setJobClass(MyClassLoader.getClassByScn(task.getClassName()));
						CronTrigger cronTrigger = new CronTrigger("cron_" + task.getSeqId(), Scheduler.DEFAULT_GROUP, jobDetail.getName(), Scheduler.DEFAULT_GROUP);
						cronTrigger.setCronExpression(task.getCronExpression());
						schedulerFactory.scheduleJob(jobDetail, cronTrigger);
						log.info(" register time task class is { " + task.getClassName() + " } ");
					}
				} catch (ParseException e) {
					log.error("startTask ParseException" + e.getMessage());
				} catch (SchedulerException e) {
					log.error("startTask SchedulerException" + " cron_" + task.getSeqId() + e.getMessage());
				} catch (Exception e) {
					log.error("startTask Exception" + " cron_" + task.getSeqId() + e.getMessage());
				}
			}
		}
	}

}
