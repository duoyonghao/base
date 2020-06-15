package com.kqds.timer;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

@Component("jQuartzJobFactory")
public class JQuartzJobFactory
  extends SpringBeanJobFactory
{
  @Autowired
  private AutowireCapableBeanFactory beanFactory;
  
  protected Object createJobInstance(TriggerFiredBundle bundle)
    throws Exception
  {
    Object jobInstance = super.createJobInstance(bundle);
    
    this.beanFactory.autowireBean(jobInstance);
    
    return jobInstance;
  }
}
