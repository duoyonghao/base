package com.kqds.timer.task;

import com.kqds.service.base.extension.KQDS_ExtensionLogic;
import com.kqds.service.base.visit.KQDS_VisitLogic;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class PushMsgTimeOutTask implements Job {
  @Autowired
  private KQDS_ExtensionLogic tgLogic;
  
  @Autowired
  private KQDS_VisitLogic hfLogic;
  
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    try {
      doTask();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void doTask() throws Exception {
    this.hfLogic.visitTimeOut();
    this.tgLogic.JhTimeOut();
  }
}
