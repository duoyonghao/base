package com.kqds.timer.task;

import com.kqds.core.global.YZSysProps;
import com.kqds.service.base.soundRecord.KQDS_SoundRecordLogic;
import com.kqds.util.sys.sys.SysParaUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class RecordTask implements Job {
  @Autowired
  private KQDS_SoundRecordLogic logic;
  
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    try {
      doTask();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void doTask() throws Exception {
    String record_file_dir = YZSysProps.getProp(SysParaUtil.RECORD_FILE_DIR);
    this.logic.updateRecordDataByDate(record_file_dir, null);
  }
}
