package com.kqds.timer;

import com.kqds.entity.sys.YZTimeTask;
import com.kqds.service.sys.timer.TimeTaskLogic;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;

public class DataBaseCronTriggerBean
  extends CronTriggerBean
{
  private static final long serialVersionUID = 1L;
  @Autowired
  private TimeTaskLogic timeTaskService;
  
  public void afterPropertiesSet()
  {
    super.afterPropertiesSet();
    YZTimeTask task = null;
    Map map = new HashMap();
    map.put("TASK_ID", getName());
    try
    {
      task = (YZTimeTask)this.timeTaskService.loadList4One("SYS_TIMETASK", map);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if ((task != null) && (task.getIsEffect().equals("1")) && (!task.getCronExpression().equals(getCronExpression()))) {
      try
      {
        setCronExpression(task.getCronExpression());
      }
      catch (ParseException e)
      {
        e.printStackTrace();
      }
    }
  }
}
