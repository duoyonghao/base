package com.kqds.timer;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;
import com.kqds.entity.sys.YZTimeTask;
import com.kqds.service.sys.timer.TimeTaskLogic;
import com.kqds.util.base.util.HttpRequestUtils;
import com.kqds.util.sys.IpUtil;
import java.io.IOException;
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
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Service;

@Service("dynamicTask")
public class DynamicTask {
  private Logger log = LoggerFactory.getLogger(DynamicTask.class);
  
  @Autowired
  private Scheduler schedulerFactory;
  
  @Autowired
  private TimeTaskLogic timeTaskService;
  
  private boolean startTask(YZTimeTask task) {
    try {
      JobDetail jobDetail = new JobDetail();
      jobDetail.setName(task.getSeqId());
      jobDetail.setGroup("DEFAULT");
      jobDetail.setJobClass(MyClassLoader.getClassByScn(task.getClassName()));
      CronTrigger cronTrigger = new CronTrigger("cron_" + task.getSeqId(), "DEFAULT", jobDetail.getName(), "DEFAULT");
      cronTrigger.setCronExpression(task.getCronExpression());
      this.schedulerFactory.scheduleJob(jobDetail, (Trigger)cronTrigger);
      return true;
    } catch (ParseException e) {
      this.log.error("startTask ParseException" + e.getMessage());
    } catch (SchedulerException e) {
      this.log.error("startTask SchedulerException cron_" + task.getSeqId() + e.getMessage());
    } 
    return false;
  }
  
  private boolean endTask(YZTimeTask task) {
    try {
      this.schedulerFactory.unscheduleJob("cron_" + task.getSeqId(), "DEFAULT");
      return true;
    } catch (SchedulerException e) {
      this.log.error("endTask SchedulerException cron_" + task.getSeqId() + e.getMessage());
      return false;
    } 
  }
  
  public boolean startOrStop(YZTimeTask task, boolean start) throws Exception {
    boolean isSuccess = start ? startTask(task) : endTask(task);
    if (isSuccess) {
      task.setIsStart(start ? "1" : "0");
      task.setIsEffect("1");
      this.timeTaskService.updateSingleUUID("SYS_TIMETASK", task);
      this.log.info(String.valueOf(start ? "开启任务" : "停止任务") + "-------TaskId:" + task.getTaskId() + "-------Describe:" + task.getTaskDescribe() + "-----ClassName:" + task.getClassName());
    } 
    return isSuccess;
  }
  
  public boolean updateCronExpression(YZTimeTask task) throws Exception {
    try {
      String newExpression = task.getCronExpression();
      task = (YZTimeTask)this.timeTaskService.loadObjSingleUUID("SYS_TIMETASK", task.getSeqId());
      if ("1".equals(task.getIsStart())) {
        CronTriggerBean trigger = (CronTriggerBean)this.schedulerFactory.getTrigger("cron_" + task.getSeqId(), "DEFAULT");
        String originExpression = trigger.getCronExpression();
        if (!originExpression.equalsIgnoreCase(newExpression)) {
          trigger.setCronExpression(newExpression);
          this.schedulerFactory.rescheduleJob("cron_" + task.getSeqId(), "DEFAULT", (Trigger)trigger);
        } 
      } else {
        List<String> ipList = IpUtil.getLocalIPList();
        String runServerIp = task.getRunServerIp();
        boolean isStart = task.getIsStart().equals("0");
        boolean isSuccess = false;
        if (ipList.contains(runServerIp) || StringUtil.isEmpty(runServerIp) || "本地".equals(runServerIp)) {
          isSuccess = startOrStop(task, isStart);
        } else {
          try {
            String url = "http://" + task.getRunServer() + "/timeTaskController.do?remoteTask";
            String param = "id=" + task.getSeqId() + "&isStart=" + (isStart ? "1" : "0");
            String jsonstr = HttpRequestUtils.httpPost(url, param, false);
            if (jsonstr != null && jsonstr.length() > 0) {
              JSONObject json = (JSONObject)JSONObject.parse(jsonstr);
              isSuccess = json.getBooleanValue("success");
            } 
          } catch (ClientProtocolException e) {
            this.log.info("远程主机‘" + task.getRunServer() + "’响应超时");
            return false;
          } catch (IOException e) {
            this.log.info("远程主机‘" + task.getRunServer() + "’响应超时");
            return false;
          } 
        } 
        if (isSuccess) {
          this.log.info("立即生效开启任务成功，任务ID:-------TaskId:" + task.getTaskId() + "-------Describe:" + task.getTaskDescribe() + "-----ClassName:" + task.getClassName());
          return true;
        } 
        this.log.info("立即生效开启任务失败，任务ID:-------TaskId:" + task.getTaskId() + "-------Describe:" + task.getTaskDescribe() + "-----ClassName:" + task.getClassName());
        return false;
      } 
    } catch (SchedulerException e) {
      this.log.error("updateCronExpression SchedulerException cron_" + task.getSeqId() + e.getMessage());
    } catch (ParseException e) {
      this.log.error("updateCronExpression ParseException cron_" + task.getSeqId() + e.getMessage());
    } 
    return false;
  }
  
  public void loadTask() {
    List<String> ipList = IpUtil.getLocalIPList();
    Map<String, String> filterMap = new HashMap<>();
    filterMap.put("IS_EFFECT", "1");
    filterMap.put("IS_START", "1");
    List<YZTimeTask> tasks = new ArrayList<>();
    try {
      tasks = (List<YZTimeTask>)this.timeTaskService.loadList("SYS_TIMETASK", filterMap);
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error("startTask Exception" + e.getMessage());
    } 
    this.log.info(" register time task class num is [" + tasks.size() + "] ");
    if (tasks.size() > 0)
      for (YZTimeTask task : tasks) {
        try {
          String runServerIp = task.getRunServerIp();
          if (ipList.contains(runServerIp) || StringUtil.isEmpty(runServerIp) || "本地".equals(runServerIp)) {
            JobDetail jobDetail = new JobDetail();
            jobDetail.setName(task.getSeqId());
            jobDetail.setGroup("DEFAULT");
            jobDetail.setJobClass(MyClassLoader.getClassByScn(task.getClassName()));
            CronTrigger cronTrigger = new CronTrigger("cron_" + task.getSeqId(), "DEFAULT", jobDetail.getName(), "DEFAULT");
            cronTrigger.setCronExpression(task.getCronExpression());
            this.schedulerFactory.scheduleJob(jobDetail, (Trigger)cronTrigger);
            this.log.info(" register time task class is { " + task.getClassName() + " } ");
          } 
        } catch (ParseException e) {
          this.log.error("startTask ParseException" + e.getMessage());
        } catch (SchedulerException e) {
          this.log.error("startTask SchedulerException cron_" + task.getSeqId() + e.getMessage());
        } catch (Exception e) {
          this.log.error("startTask Exception cron_" + task.getSeqId() + e.getMessage());
        } 
      }  
  }
}
