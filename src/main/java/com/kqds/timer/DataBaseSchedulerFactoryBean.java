package com.kqds.timer;

import com.kqds.entity.sys.YZTimeTask;
import com.kqds.service.sys.timer.TimeTaskLogic;
import java.util.HashMap;
import java.util.Map;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class DataBaseSchedulerFactoryBean
  extends SchedulerFactoryBean
{
  @Autowired
  private TimeTaskLogic timeTaskService;
  
  public void afterPropertiesSet()
    throws Exception
  {
    super.afterPropertiesSet();
    String[] trigerrNames = getScheduler().getTriggerNames("DEFAULT");
    for (String trigerrName : trigerrNames)
    {
      Map map = new HashMap();
      map.put("TASK_ID", trigerrName);
      YZTimeTask task = (YZTimeTask)this.timeTaskService.loadList4One("SYS_TIMETASK", map);
      if ((task == null) || (!"1".equals(task.getIsStart()))) {
        getScheduler().pauseTrigger(trigerrName, "DEFAULT");
      }
    }
  }
}
