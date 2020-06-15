package com.kqds.timer.task;

import com.kqds.service.sys.register.YZRegisterLogic;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class RegisterYzTask
  implements Job
{
  @Autowired
  private YZRegisterLogic logic;
  
  public void execute(JobExecutionContext arg0)
    throws JobExecutionException
  {
    try
    {
      doTask();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void doTask()
    throws Exception
  {}
}
