package com.kqds.timer;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.kqds.entity.sys.YZTimeTask;
import com.kqds.service.sys.timer.TimeTaskLogic;
import com.kqds.util.sys.TableNameUtil;

/**
 * 读取数据库 然后判断是否启动任务
 * 
 * @author JueYue
 * @date 2013-9-22
 * @version 1.0
 */
public class DataBaseSchedulerFactoryBean extends SchedulerFactoryBean {

	@Autowired
	private TimeTaskLogic timeTaskService;

	/**
	 * 读取数据库判断是否开始定时任务
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		String[] trigerrNames = this.getScheduler().getTriggerNames(Scheduler.DEFAULT_GROUP);
		YZTimeTask task;

		for (String trigerrName : trigerrNames) {
			Map map = new HashMap();
			map.put("TASK_ID", trigerrName);
			task = (YZTimeTask) timeTaskService.loadList4One(TableNameUtil.SYS_TIMETASK, map);
			// 数据库查询不到的定时任务或者定时任务的运行状态不为1时，都停止
			// TASK #327 定时器任务默认未启动
			if (task == null || !"1".equals(task.getIsStart())) {
				this.getScheduler().pauseTrigger(trigerrName, Scheduler.DEFAULT_GROUP);
			}
		}
	}

}
