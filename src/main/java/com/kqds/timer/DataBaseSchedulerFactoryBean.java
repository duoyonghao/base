package com.kqds.timer;

import com.kqds.entity.sys.YZTimeTask;
import com.kqds.service.sys.timer.TimeTaskLogic;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class DataBaseSchedulerFactoryBean extends SchedulerFactoryBean {
  @Autowired
  private TimeTaskLogic timeTaskService;
  
  public void afterPropertiesSet() throws Exception {
    super.afterPropertiesSet();
    String[] trigerrNames = getScheduler().getTriggerNames("DEFAULT");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = trigerrNames).length, b = 0; b < i; ) {
      String trigerrName = arrayOfString1[b];
      Map<Object, Object> map = new HashMap<>();
      map.put("TASK_ID", trigerrName);
      YZTimeTask task = (YZTimeTask)this.timeTaskService.loadList4One("SYS_TIMETASK", map);
      if (task == null || !"1".equals(task.getIsStart()))
        getScheduler().pauseTrigger(trigerrName, "DEFAULT"); 
      b++;
    } 
  }
}
