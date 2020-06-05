package com.kqds.timer.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.core.global.YZSysProps;
import com.kqds.service.base.soundRecord.KQDS_SoundRecordLogic;
import com.kqds.util.sys.sys.SysParaUtil;

/**
 * 扫描指定目录下的录音文件
 * 
 * @author Administrator
 * 
 */
@Component
@Controller
public class RecordTask implements Job {
	@Autowired
	private KQDS_SoundRecordLogic logic;

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

		// 录音文件存放路径
		String record_file_dir = YZSysProps.getProp(SysParaUtil.RECORD_FILE_DIR);
		logic.updateRecordDataByDate(record_file_dir, null);

	}

}
