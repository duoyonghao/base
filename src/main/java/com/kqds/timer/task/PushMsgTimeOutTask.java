package com.kqds.timer.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.service.base.extension.KQDS_ExtensionLogic;
import com.kqds.service.base.visit.KQDS_VisitLogic;

/**
 * 超时提醒任务
 * 
 * @author Administrator
 * 
 */
@Component
@Controller
public class PushMsgTimeOutTask implements Job {
	@Autowired
	private KQDS_ExtensionLogic tgLogic;
	@Autowired
	private KQDS_VisitLogic hfLogic;

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
		// 查询超时提醒（回访、推广计划是否超时）
		// 查询回访超时
		hfLogic.visitTimeOut();
		// 查询推广计划超时
		tgLogic.JhTimeOut();

	}

}
